/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.web;

import com.dream.bizsdk.common.SysVar;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Description:
 * Security filter. This filter will block thoseq requests
 * that have no correct session data object;
 * <p/>
 * Author: divine
 * Date: 2004-2-27
 */
public class AuthFilter implements Filter {

    /**
     * initialize this fiter object;
     *
     * @param fc
     * @throws ServletException
     */
    public void init(FilterConfig fc) throws ServletException {

    }

    /**
     * do fileter on a reuqeest;
     *
     * @param req
     * @param res
     * @param fc
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc) throws IOException,
            ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpSession session = httpReq.getSession();
        //if the session data is not valid, then access is denied;
        if (session.getAttribute(SysVar.SS_DATA) == null) {
            HttpServletResponse httpRes = (HttpServletResponse) res;
            httpRes.sendError(403);
        } else {
            fc.doFilter(req, res);
        }
    }

    public void destroy() {

    }
}
