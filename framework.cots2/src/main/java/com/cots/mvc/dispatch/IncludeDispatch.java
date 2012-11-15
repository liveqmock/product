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
 *      include type dispatch
 * 
 * User: chuguanghua
 * Date: 2004-10-10
 * Time: 13:34:02
 * Version: 1.0
 */
public final class IncludeDispatch extends AbstractDispatch{
    public IncludeDispatch(String uri){
        this.uri = uri;
    }

    /**
     * when an include dispatch is executed, the left dispatch(es) can also be executed.
     *
     * @param request
     * @param response
     * @param data
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public boolean go(HttpServletRequest request,HttpServletResponse response,HashMap data)
            throws IOException, ServletException {
        RequestDispatcher disp = request.getRequestDispatcher(uri);
        disp.include(request,response);
        return true;
    }

    /**
     * 
     *
     * @param holder
     * @param parent
     */
    public void save(XMLFile holder, Element parent) {
        Element disp = holder.appendChild(parent,"include");
        if(this.on!=null && this.on.length()>0){
            disp.setAttribute(Action.ON,this.on);
        }
        disp.setAttribute(Action.URI,this.uri);
    }
}