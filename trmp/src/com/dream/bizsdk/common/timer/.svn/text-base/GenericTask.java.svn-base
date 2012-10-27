/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.timer;

import com.dream.bizsdk.common.blc.IBLContext;

import javax.servlet.ServletContext;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-28
 * Time: 9:23:06
 */
public abstract class GenericTask extends Task {

    protected IBLContext context;
    protected String className;
    protected ServletContext sc;

    /**
     *
     *
     */
    public GenericTask() {

    }

    /**
     * @param context
     */
    public GenericTask(IBLContext context) {
        this.context = context;
    }

    /**
     * @param context
     */
    public void setContext(IBLContext context) {
        this.context = context;
    }

    /**
     * @return
     */
    public IBLContext getContext() {
        return context;
    }

    /**
     * @param sc
     */
    public void setServletContext(ServletContext sc) {
        this.sc = sc;
    }

    /**
     * @return
     */
    public ServletContext getServletContext() {
        return this.sc;
    }

    /**
     * @param className
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return
     */
    public String getClassName() {
        return this.className;
    }
}