/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller;

import com.cots.mvc.controller.access.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.net.URLEncoder;

/**
 * Description:
 *  Authentication and Authrization filter.
 *  This filter may have a init-parameter named "token" which is the displayName of
 * an attribute in a session, the existense of the attribute will decide the
 * validaty of a HttpSession. 
 *
 * User: chugh
 * Date: 2004-11-16
 * Time: 9:32:46
 * Version: 1.0
 */
public class AAFilter implements Filter{

//    private LoginControl       loginControl;
//    private AccessControl      accessControl;

    //the displayName of the attribute whose existense will determine the validty of a session;
    public static String flagAttribute= LoginControl.USERID;

//    private boolean           oneSessionPerUser;

    /**
     * initialize this filter object; The main task is to create a AccessControl object;
     *
     * @param filterConfig
     */
    public void init(FilterConfig filterConfig){

        //get the displayName of the flag attribute, if not specified, use the default;
        String name = filterConfig.getInitParameter("token");
        if(name!=null && name.length()>0){
            flagAttribute = name;
        } else {
            //use the default displayName of the flag attribute;
            flagAttribute = LoginControl.USERID;
        }
    }

    /**
     * filter requests to the target resource;
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        /**get the action displayName**/
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String uri = request.getRequestURI();
        int lastSlash = uri.lastIndexOf('/');
        uri = uri.substring(lastSlash+1);
        int lastPeriod = uri.lastIndexOf('.');
        if(lastPeriod<0){
            lastPeriod = uri.length();
        }
        String action = uri.substring(0,lastPeriod);

        //if the requested action is free, then no session check;
        if(AccessControlImpl.getInstance().isFreeAction(action)){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        /** validate the current user and his/her access to the requested action*/
        String userID;
        userID = (String)request.getSession(true).getAttribute(flagAttribute);

        if(userID==null){    //has not logined in explictily.
            /*get userID from cookies;*/
            Cookie[] cookies = request.getCookies();
            if(cookies!=null){
                for(int i=0;i<cookies.length;i++){
                    if(flagAttribute.equalsIgnoreCase(cookies[i].getName())){
                        userID = cookies[i].getValue();
                        request.getSession().setAttribute(flagAttribute,userID);
                        break;
                    }
                }
            }
        }

        if(userID == null || !LoginControlImpl.getInstance().hasLogined(userID)){   //not logined in
            StringBuffer url = request.getRequestURL();
            String queryString = request.getQueryString();

            //if the current request is of POST method, then contruct the query string;
            //NOTE: the contructed query String may be too long for a GET method;
            String method = request.getMethod();
            if("post".equalsIgnoreCase(method)){
                StringBuffer sb = new StringBuffer(128);

                Enumeration names = request.getParameterNames();
                while(names.hasMoreElements()){
                    String name = (String)names.nextElement();
                    String[] values = request.getParameterValues(name);
                    if(values!=null){
                        for(int i=0;i<values.length;i++){
                            sb.append(name).append("=").append(URLEncoder.encode(values[i],"GBK")).append("&");
                        }
                    }
                }

                //delete the ending char '&';
                if(sb.length()>0){
                    sb.deleteCharAt(sb.length()-1);
                }

                queryString = new String(sb);
            }

            if(queryString!=null){
                url.append("?").append(queryString);
            }

            servletRequest.getRequestDispatcher(ControllerServlet.LOGIN_FORWARD+"?nextPage="+url).forward(servletRequest,
                    servletResponse);
        }else if(!AccessControlImpl.getInstance().canDo(userID,action)){    //no access to do the requested action;
            //send the HttpServletResponse.SC_UNAUTHORIZED code to the client;
            ((HttpServletResponse)servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }else{  //has logined in and has access to the target action;
            Cookie cookie = new Cookie(flagAttribute,userID);
            cookie.setMaxAge(request.getSession().getMaxInactiveInterval());
            ((HttpServletResponse)servletResponse).addCookie(cookie);
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    /**
     * notfied when the filter was destroied;
     */
    public void destroy() {
    }
}