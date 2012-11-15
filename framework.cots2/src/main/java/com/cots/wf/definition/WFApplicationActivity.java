/**
 * copyright 2004,primton ltd.All rights reserved.
 */


package com.cots.wf.definition;

/**
 * Module:  WFApplicationActivity.java
 * Author:  chuguanghua
 * Created: 2004Äê3ÔÂ31ÈÕ 10:16:44
 * Purpose: Defines the Class WFApplicationActivity
 */

public class WFApplicationActivity extends WFAbstractActivity {
    protected String applicationID;

    public WFApplicationActivity() {

    }

    public WFApplicationActivity(WFProcess process) {
        super(process);
    }

    public WFApplication getApplication() {
        return this.getProcess().getApplication(applicationID);
    }

    void setApplicationID(String appID) {
        this.applicationID = appID;
    }
}