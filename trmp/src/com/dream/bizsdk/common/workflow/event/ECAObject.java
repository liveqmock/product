/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.workflow.event;

/**
 * Description:
 * An ECAObject is a combination of eventName, cond and actionUrl.
 * <p/>
 * User: chuguanghua
 * Date: 2004-5-12
 * Time: 10:45:32
 * version:1.0
 */
public class ECAObject {
    private String eventName;
    private String cond;
    private String actionUrl;
    private int processID;

    public ECAObject() {

    }

    public ECAObject(int processID, String eventName, String cond, String actionUrl) {
        this.processID = processID;
        this.eventName = eventName;
        this.cond = cond;
        this.actionUrl = actionUrl;
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCond() {
        return this.cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }

    public String getActionUrl() {
        return this.actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }
}
