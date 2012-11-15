/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller;

import com.cots.mvc.controller.access.LoginControl;
import com.cots.mvc.controller.access.LoginControlImpl;

import javax.servlet.http.*;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-11-16
 * Time: 11:45:52
 * Version: 1.0
 */
public class SessionListener implements HttpSessionAttributeListener{
    /**
     * notified when an attribute added to the session;
     *
     * @param event
     */
    public void attributeAdded(HttpSessionBindingEvent event) {
        String name = event.getName();
        if(LoginControl.USERID.equalsIgnoreCase(name)){
            String value = (String)event.getValue();
            LoginControl lc = LoginControlImpl.getInstance();
            lc.loginApp(value,event.getSession().getServletContext());
        }
    }

    /**
     * notified when an attribute removed from the session;
     *
     * @param event
     */
    public void attributeRemoved(HttpSessionBindingEvent event) {
        String name = event.getName();
        if(LoginControl.USERID.equalsIgnoreCase(name)){
            String userID = (String)event.getValue();
            LoginControl lc = LoginControlImpl.getInstance();
            lc.logoutApp(userID,event.getSession().getServletContext());
        }
    }

    /**
     * notified when an attribute was resplaced.
     *
     * @param event
     */
    public void attributeReplaced(HttpSessionBindingEvent event) {
        String name = event.getName();
        if(LoginControl.USERID.equalsIgnoreCase(name)){
            String value = (String)event.getValue();
            LoginControl lc = LoginControlImpl.getInstance();
            lc.loginApp(value,event.getSession().getServletContext());
        }
    }
}