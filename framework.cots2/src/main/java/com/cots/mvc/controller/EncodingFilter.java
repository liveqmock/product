/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller;

import com.cots.uploader.HttpUploaderRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Description:
 *      This filter is used to set Character Encoding of a request. And also
 * make transformation to the parameters if the contentType of the request is
 * "multipart/form-data".
 *      this filter can have a init-param named "encoding" whose value defaults
 * to "GBK", its value specify what encoding this filter will impose on the
 * matched requests.
 *
 * User: chuguanghua
 * Date: 2004-12-15
 * Time: 12:55:40
 * Version: 1.0
 */
public class EncodingFilter implements Filter{
    private String encoding = "GBK";

    /**
     * called by the Web Container.
     *
     * @param filterConfig
     * @throws ServletException
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        String enc = filterConfig.getInitParameter("encoding");
        if(enc!=null){
            this.encoding = enc;
        }
    }

    /**
     * filter a request.
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String contentType = servletRequest.getContentType();
        if(contentType!=null && contentType.toLowerCase().startsWith("multipart/form-data")){
            HttpUploaderRequest req = new HttpUploaderRequest((HttpServletRequest)servletRequest);
            filterChain.doFilter(req,servletResponse);
        }else{
            servletRequest.setCharacterEncoding(this.encoding);
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    /**
     * nodified when the filter should be destroied.
     */
    public void destroy() {

    }
}