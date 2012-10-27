/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.event;

import com.dream.bizsdk.common.blc.IBLContext;
import com.dream.bizsdk.common.blc.BLResult;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-24
 * Time: 15:22:04
 */
public class EventListenerImpl implements EventListener {
    protected String name;
    protected String className;
    protected String methodName;
    private Logger log = Logger.getLogger("com.dream.event");

    protected IBLContext context;

    public EventListenerImpl(String name) {
        this.name = name;
    }

    public EventListenerImpl(IBLContext context) {
        this.context = context;
    }

    public EventListenerImpl(String name, IBLContext context, String className, String methodName) {
        this.name = name;
        this.context = context;
        this.className = className;
        this.methodName = methodName;
    }

    public void onEvent(Event e) {

        if (log.isInfoEnabled()) {
            log.info("caught event:" + e.getName() + ",bl method:" + className + "." + methodName + " executing");
        }

        try {
            BLResult br = context.execBL(className, methodName, e.getData(), e.getSessionData());

            if (log.isInfoEnabled()) {
                log.info("event handler bl method:" + className + "." + methodName + " finished with retCode:" + br.retCode);
            }

        } catch (Throwable re) {
            log.info("exception when executing event handler bl method:" + className + "." + methodName + " with retCode:", re);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public IBLContext getContext() {
        return context;
    }

    public void setContext(IBLContext context) {
        this.context = context;
    }
}
