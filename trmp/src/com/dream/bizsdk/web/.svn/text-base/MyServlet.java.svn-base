/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-29
 * Time: 9:40:33
 */
public class MyServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String targetJsp = req.getParameter("jsp");
        RequestDispatcher rd = req.getRequestDispatcher(targetJsp);
        if (targetJsp == null) {
            res.getWriter().println("NO jsp specified");
            return;
        }
        rd.include(req, res);
    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse res){
    	
    }
}
