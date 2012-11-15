/**
 * copyright 2004,primton ltd.All rights reserved.
 */


package com.cots.wf.definition;


/**
 * Module:  WFGenericActivity.java
 * Description:
 * <p/>
 * <p/>
 * Author:  chuguanghua
 * Created: 2004Äê3ÔÂ31ÈÕ 10:20:45
 * Purpose: Defines the Class WFGenericActivity
 */

public class WFGenericActivity extends WFAbstractActivity {
    private String performerID;

    private WFParticipant participant;

    private String action;

    public WFGenericActivity() {

    }

    public WFGenericActivity(WFProcess process) {
        super(process);
    }

    public String getPerformerID() {
        return this.performerID;
    }

    public WFParticipant getParticipant() {
        return participant;
    }

    public String getAction() {
        return this.action;
    }

    void setPerformerID(String performerID) {
        this.performerID = performerID;
    }

    public void setParticipant(WFParticipant p) {
        this.participant = p;
    }

    void setAction(String action) {
        this.action = action;
    }
}