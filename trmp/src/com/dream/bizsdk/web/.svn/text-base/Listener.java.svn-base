/*
 * Listener.java
 *
 * Created on 2003年9月28日, 下午2:46
 */

package com.dream.bizsdk.web;

import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.databus.BizData;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextAttributeEvent;

/**
 * Example listener for context-related application events, which were
 * introduced in the 2.3 version of the Servlet API.  This listener
 * merely documents the occurrence of such events in the application log
 * associated with our servlet context.
 *
 * @author divine
 * @version 0.1
 */

public final class Listener
        implements ServletContextListener,
        HttpSessionAttributeListener, ServletContextAttributeListener
        , HttpSessionListener {

    /**
     * Record the fact that a servlet context attribute was added.
     *
     * @param event The session attribute event
     */
    public void attributeAdded(HttpSessionBindingEvent event) {
        String name = event.getName();
        if (name.equalsIgnoreCase(SysVar.SS_DATA)) {
            BizData sd = (BizData) event.getValue();
            String userID = sd.getString("userID");
        }
    }


    /**
     * Record the fact that a servlet context attribute was removed.
     *
     * @param event The session attribute event
     */
    public void attributeRemoved(HttpSessionBindingEvent event) {

    }


    /**
     * Record the fact that a servlet context attribute was replaced.
     *
     * @param event The session attribute event
     */
    public void attributeReplaced(HttpSessionBindingEvent event) {

    }


    /**
     * Record the fact that this web application has been destroyed.
     *
     * @param event The servlet context event
     */
    public void contextDestroyed(ServletContextEvent event) {
        String name = event.getServletContext().getServletContextName();
        BLServletMap.remove(name);
    }


    /**
     * Record the fact that this web application has been initialized.
     *
     * @param event The servlet context event
     */
    public void contextInitialized(ServletContextEvent event) {
    }

    /**
     * added the servlet context attribute to the Map if this attribute is
     * SysVar.APP_BLSERVLET;
     *
     * @param scab
     */
    public void attributeAdded(ServletContextAttributeEvent scab) {
        String attrName = scab.getName();
        String name = scab.getServletContext().getServletContextName();

        if (attrName.equals(SysVar.APP_BLSERVLET)) {
            BLServletMap.add(name, (BLServlet) scab.getValue());
        }
    }

    /**
     * attribute removed from context;
     */
    public void attributeRemoved(ServletContextAttributeEvent scab) {
        String attrName = scab.getName();
        String name = scab.getServletContext().getServletContextName();

        if (attrName.compareTo(SysVar.APP_BLSERVLET) == 0) {
            BLServletMap.remove(name);
        }
    }

    /**
     * @param scab
     */
    public void attributeReplaced(ServletContextAttributeEvent scab) {
    }

    /**
     * Record the fact that a session has been created.
     *
     * @param event The session event
     */
    public void sessionCreated(HttpSessionEvent event) {
    }

    /**
     * Record the fact that a session has been destroyed.
     *
     * @param event The session event
     */
    public void sessionDestroyed(HttpSessionEvent event) {
    }
}