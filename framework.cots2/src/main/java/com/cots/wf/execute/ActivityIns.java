/**
 *all rights reserved,@copyright 2003
 */
package com.cots.wf.execute;


import com.cots.wf.definition.WFAbstractActivity;

import java.util.Date;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-4-1
 * Time: 14:30:02
 */
public class ActivityIns {

    public final static int ACTIVITY_STARTED = 1;
    public final static int ACTIVITY_FINISHED = 2;
    public final static int ACTIVITY_CANCELED = 3;
    public final static int ACTIVITY_SUSPENDED = 4;

    private static long NEXTID = 1;

    private WFAbstractActivity activityDef;
    private ProcessIns processIns;

    private long id;
    private long processInsID;
    private int status = ActivityIns.ACTIVITY_STARTED;
    private Date startTime = new Date();
    private Date endTime;

    private boolean _isNew = true;


    public ActivityIns(WFAbstractActivity activityDef) {
        this.activityDef = activityDef;
        this.id = getNextID();
    }

    public ActivityIns(ProcessIns processIns, WFAbstractActivity activityDef) {
        this.processIns = processIns;
        this.processInsID = processIns.getID();
        this.activityDef = activityDef;
        this.id = getNextID();
    }

    /**
     * get the next ID of new activity;
     *
     * @return
     */
    synchronized private static long getNextID() {
        return NEXTID++;
    }

    /**
     * get the def of this activity;
     *
     * @return
     */
    public WFAbstractActivity getActivityDef() {
        return activityDef;
    }

    /**
     * get the id of this activity;
     *
     * @return id of this activity instance;
     */
    public long getID() {
        return this.id;
    }

    /**
     * get the id of the process instance;
     *
     * @return the ID of the parent process;
     */
    public long getProcessInsID() {
        return this.processInsID;
    }

    /**
     * get the status of this activity;
     *
     * @return
     */
    public int getStatus() {
        return status;
    }

    /**
     * @return
     */
    public ProcessIns getProcessIns() {
        return this.processIns;
    }

    /**
     * set the process instance;
     *
     * @param pi
     */
    public void setProcessIns(ProcessIns pi) {
        this.processIns = pi;
        this.processInsID = pi.getID();
    }

    /**
     * set the status of this activity;
     *
     * @param status
     */
    public void setStatus(int status) {
        //if the workflow is ended, then its status can't changed;
        if (this.status != ActivityIns.ACTIVITY_FINISHED && this.status != ActivityIns.ACTIVITY_CANCELED) {
            this.status = status;
            //set the end time;
            if (status == ActivityIns.ACTIVITY_FINISHED || status == ActivityIns.ACTIVITY_CANCELED) {
                endTime = new Date();
            }
        }
    }

    /**
     * set the end time of this activity;
     *
     * @param endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * get the end time of this activity;
     *
     * @return
     */
    public Date getEndTime() {
        return this.endTime;
    }

    /**
     * @return
     */
    public boolean isNew() {
        return _isNew;
    }

    /**
     * @param isNew
     */
    public void setNew(boolean isNew) {
        this._isNew = isNew;
    }

    /**
     * get the start time of this actvity;
     *
     * @return
     */
    public Date getStarteTime() {
        return this.startTime;
    }

    public String getStringStatus() {
        if (status == ActivityIns.ACTIVITY_CANCELED) {
            return "cancel";
        } else if (status == ActivityIns.ACTIVITY_FINISHED) {
            return "finish";
        } else if (status == ActivityIns.ACTIVITY_STARTED) {
            return "start";
        } else if (status == ActivityIns.ACTIVITY_SUSPENDED) {
            return "suspnd";
        } else {
            return "n/a";
        }
    }
}
