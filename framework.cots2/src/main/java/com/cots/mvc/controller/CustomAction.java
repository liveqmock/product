/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller;

import com.cots.util.XMLDocument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import java.io.IOException;

import org.w3c.dom.Element;
import org.apache.log4j.Logger;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-11-6
 * Time: 15:49:14
 * Version: 1.0
 */
public final class CustomAction extends AbstractAction{

    private String className;

    /**
     * get the displayName of this action;
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * set the displayName of this action;
     *
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * get the class displayName of this action;
     *
     * @return the qualified displayName the class.
     */
    public String getClassName() {
        return className;
    }

    /**
     * set the class displayName of this action.
     *
     * @param className the displayName of the class.
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * run this action;
     *
     * @param request the HttpServeltRequest object;
     * @param response the HttpServletResponse object;
     * @throws ServletException
     * @throws JspException
     * @throws IOException
     */
    public void run(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, JspException,IOException {
        try{
            Class clz = Thread.currentThread().getContextClassLoader().loadClass(className);
            Object obj = clz.newInstance();
            if(!(obj instanceof Action)){
                Logger.getLogger(CustomAction.class).error("class "+className+" is not a subclass of com.cots.mvc.controller.Action");
                throw new ClassNotFoundException(className);
            }else{
                Action action = (Action)obj;
                action.run(request,response);
            }
        }catch(ClassNotFoundException e){
            throw new ServletException("custom action "+className+" not found or not a subclass of Action");
        }catch (IllegalAccessException e){
            throw new ServletException("can't access custom action "+className);
        }catch (InstantiationException e){
            throw new ServletException("can't create instance of custom action class "+className);
        }
    }

    public void save(){

    }

    /**
     *
     * @param xml
     * @param actionNode
     */
    void init(XMLDocument xml,Element actionNode){
        String name  = actionNode.getAttribute("name");
        String className  = actionNode.getAttribute("class");
        if(name.length()>0 && className.length()>0){   //displayName can't be null or empty;
            setName(name);
            setClassName(className);
        }
    }
}