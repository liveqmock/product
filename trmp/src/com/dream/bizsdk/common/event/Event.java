/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.event;

import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.blc.IBLContext;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-24
 * Time: 14:49:00
 */
public class Event {
    protected IBLContext context;
    /**
     * the name of the event;
     */
    protected String name;

    /**
     * the main(request) data associated of the event;
     */
    protected BizData data;

    /**
     * the session data of the event;
     */
    protected BizData sessionData;

    public Event(String name) {
        this.name = name;
    }

    public Event(String name, BizData data) {
        this.name = name;
        this.data = data;
    }

    public Event(String name, BizData data, BizData sessionData) {
        this.name = name;
        this.data = data;
        this.sessionData = sessionData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BizData getData() {
        return data;
    }

    public void setData(BizData data) {
        this.data = data;
    }

    public BizData getSessionData() {
        return this.sessionData;
    }

    public void setSessionData(BizData sessionData) {
        this.sessionData = sessionData;
    }

    public IBLContext getContext() {
        return context;
    }

    public void setContext(IBLContext context) {
        this.context = context;
    }
}
