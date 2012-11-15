/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-5-17
 * Time: 17:54:56
 * Version: 1.0
 */
public class DOSFilter implements Filter{
    long interval = 500;
    HashMap lastAccess = new HashMap();

    /**
     *
     * @param filterConfig
     */
    public void init(FilterConfig filterConfig){
        String interval = filterConfig.getInitParameter("interval");
        try{
            this.interval = Long.parseLong(interval);
        }catch(Exception e){

        }

        if(this.interval<0){
            this.interval = 500;
        }

    }

    /**
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String ip = request.getRemoteHost();
        Long lastTime = (Long)lastAccess.get(ip);
        long current = System.currentTimeMillis();
        if(lastTime==null){
            lastAccess.put(ip,new Long(current));
        }else{
            if(current-lastTime.longValue()<=500){
                ((HttpServletResponse)servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN,
                        "too many requests from you");
            }else{
                lastAccess.put(ip,new Long(current));
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }
    }

    /**
     *
     */
    public void destroy() {

    }
}
