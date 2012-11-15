/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.dispatch;

import com.cots.util.XMLFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.util.HashMap;
import java.io.IOException;

import org.w3c.dom.Element;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-4-5
 * Time: 14:33:26
 * Version: 1.0
 */
public final class CustomDispatch extends AbstractDispatch{
    private String dispatcherClass;

    private Dispatch dispatcher;

    public CustomDispatch() {
    }

    public CustomDispatch(String dispatcherClass) {
        this.dispatcherClass = dispatcherClass;
    }

    public String getDispatcherClass() {
        return dispatcherClass;
    }

    public void setDispatcherClass(String dispatcherClass) {
        this.dispatcherClass = dispatcherClass;
    }

    public boolean go(HttpServletRequest request, HttpServletResponse response, HashMap data)
            throws ServletException,IOException{
        if(dispatcher==null){
            try{
                dispatcher = (Dispatch)Class.forName(dispatcherClass).newInstance();
            } catch(ClassNotFoundException e){
                throw new ServletException("Custom dispatch class \""+this.dispatcherClass+"\" not found.");
            } catch (IllegalAccessException e) {
                throw new ServletException("Can't access dispatch the defalt constructor of class \""+
                        this.dispatcherClass+"\".");
            } catch (InstantiationException e) {
                throw new ServletException("Can't create instance of clsss \""+this.dispatcherClass+"\"");
            } catch(ClassCastException e){
                throw new ServletException("Dispatcher class \""+this.dispatcherClass+"\" must implement interface " +
                        "com.cots.mvc.dispatch.Dispatch");
            }
        }
        return dispatcher.go(request,response,data);
    }

    public void save(XMLFile holder, Element parent) {
        Element dispatch = holder.appendChild(parent,"custom");
        dispatch.setAttribute("class",this.dispatcherClass);
    }
}
