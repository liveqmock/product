package com.cots.mvc.controller;

/**
 * Cots framework
 * <p/>
 * User: chugh
 * Date: 2005-4-8
 * Time: 9:15:38
 * Version: 1.0
 */
public class HTTPRequest {
    private String userID;

    private long startTime = System.currentTimeMillis();

    private String remoteHost;

    private String action;

    public HTTPRequest() {
    }

    public HTTPRequest(String userID, String remoteHost) {
        this.userID = userID;
        this.remoteHost = remoteHost;
    }

    public HTTPRequest(String userID, String remoteHost, String action) {
        this.userID = userID;
        this.remoteHost = remoteHost;
        this.action = action;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public long getStartTime() {
        return startTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
