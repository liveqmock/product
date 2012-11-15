/**
 *all rights reserved,@copyright 2003
 */
package com.cots.uploader;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import java.io.*;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-4-10
 * Time: 14:45:05
 * Version: 1.0
 */
public class DownloadServlet extends HttpServlet{
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        String fileName = request.getParameter("file");
        if(fileName == null || fileName.length()<1){
            ResponseUtil.writeHtml(response,"no file specified to download.");
        }else{
            ServletContext sc = request.getSession().getServletContext();
            InputStream is = sc.getResourceAsStream(fileName);
            if(is!=null){
                String name;
                int index = fileName.lastIndexOf("/");
                if(index>=0){
                    name = fileName.substring(index+1);
                }else{
                    name = fileName;
                }

                ResponseUtil.writeStream(response,is,name);
            }else{
                ResponseUtil.writeHtml(response,"sorry, file not found.");
            }
        }
    }
}
