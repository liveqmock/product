/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.dispatch;

import com.cots.util.XMLFile;
import com.cots.mvc.controller.Action;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import org.w3c.dom.Element;

/**
 * Description:
 *      forward type dispatch;
 *
 * User: chuguanghua
 * Date: 2004-10-10
 * Time: 13:32:41
 * Version: 1.0
 */
public final class ForwardDispatch extends AbstractDispatch{

    public ForwardDispatch(String uri){
        this.uri = uri;
    }

    /**
     * if a forward dispatch is executed, then no more dispatches will be executed.
     * 
     * @param request
     * @param response
     * @param data
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public boolean go(HttpServletRequest request,HttpServletResponse response,HashMap data)
            throws ServletException,IOException{
        RequestDispatcher disp = request.getRequestDispatcher(uri);
        disp.forward(request,response);
        return false;
    }

    public void save(XMLFile holder, Element parent) {
        Element disp = holder.appendChild(parent,"forward");
        if(this.on!=null && this.on.length()>0){
            disp.setAttribute(Action.ON,this.on);
        }
        disp.setAttribute(Action.URI,this.uri);
    }
}
