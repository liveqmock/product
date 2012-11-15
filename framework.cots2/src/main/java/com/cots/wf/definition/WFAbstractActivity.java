/**
 * copyright 2004,primton ltd.All rights reserved.
 */

package com.cots.wf.definition;

import java.util.HashMap;

/**
 * Module:  WFAbstractActivity.java
 * Description:
 * <p> This is the super class of all other kinds of activities.</p>
 * <p/>
 * Author:  chuguanghua
 * Created: 2004Äê3ÔÂ31ÈÕ 10:16:29
 * Purpose: Defines the Class WFAbstractActivity
 */

public abstract class WFAbstractActivity {

    public final static String JOIN_AND = "and";
    public final static String JOIN_XOR = "xor";
    public final static String JOIN_NOFM = "nOFm";
    public final static String JOIN_SYNMERGE = "synmerge";

    public final static String SPLIT_ALL = "all";
    public final static String SPLIT_XOR = "xor";
    public final static String SPLIT_NOFM = "nOFm";
    public final static String SPLIT_MULTICHOICE = "multichoice";

    protected String id;
    protected String name;
    protected String joinMode = WFAbstractActivity.JOIN_AND;
    protected String splitMode = WFAbstractActivity.SPLIT_ALL;
    protected String startMode;
    protected int workitemCount = 1;
    protected int lifecycle;
    protected boolean isStart = false;
    protected boolean isEnd;
    /**
     * not used currently
     */
    protected HashMap attributes = new HashMap();
    protected WFProcess process = null;

    public WFAbstractActivity() {

    }

    public WFAbstractActivity(WFProcess process) {
        this.process = process;
    }

    /**
     * get the id of this activity;
     *
     * @return the activity id;
     */
    public String getID() {
        return this.id;
    }

    /**
     * get the displayName of this activity;
     *
     * @return the activity displayName;
     */
    public String getName() {
        return this.name;
    }

    /**
     * get the join mode;
     *
     * @return the join mode of this activity;
     */
    public String getJoinMode() {
        return this.joinMode;
    }

    /**
     * set the split mode of this activity;
     *
     * @return the split mode of this activity;
     */
    public String getSplitMode() {
        return this.splitMode;
    }

    /**
     * get the count of workitems should be created for this activity;
     *
     * @return the count of the workitems count;
     */
    public int getWorkitemCount() {
        return this.workitemCount;
    }

    /**
     * get the life cycle of this activity;
     *
     * @return the life cycle of this activity;
     */
    public int getLifeCycle() {
        return this.lifecycle;
    }

    /**
     * get the process that contains this activity;
     *
     * @return the WFProcess object that contains this object;
     */
    public WFProcess getProcess() {
        return this.process;
    }

    /**
     * get the isStart flag of this activity;
     *
     * @return the isStart flag of this activity;
     */
    public boolean isStart() {
        return this.isStart;
    }

    /**
     * get the isEnd flag of this activity;
     *
     * @return the isEnd flag of this activity;
     */
    public boolean isEnd() {
        return this.isEnd;
    }

    /**
     * @param id
     */
    public WFExtendedAttribute getEntendedAttr(String id) {
        return (WFExtendedAttribute) attributes.get(id);
    }

    /**
     * get the incoming transitions to this activtiy;
     *
     * @return the array of all the incoming transitions to this activity;
     */
    public WFTransition[] getIncomingTransitions() {
        return process.getIncomingTransitions(this.name);
    }

    /**
     * get the outcoming transitions from this activity;
     *
     * @return the array of all the outcoming transitions from this activity;
     */
    public WFTransition[] getOutcomingTransitions() {
        return process.getOutcomingTransitions(this.name);
    }

    /**
     * set the id of this activity;
     *
     * @param id
     */
    void setID(String id) {
        this.id = id;
    }

    /**
     * set the displayName of this activity;
     *
     * @param name
     */
    void setName(String name) {
        this.name = name;
    }

    /**
     * set the join mode of this activity;
     *
     * @param joinMode
     */
    void setJoinMode(String joinMode) {
        if (joinMode != null && joinMode.length() > 0) {
            this.joinMode = joinMode;
        }
    }

    /**
     * set the split mode of this activity;
     *
     * @param splitMode
     */
    void setSplitMode(String splitMode) {
        if (splitMode != null && splitMode.length() > 0) {
            this.splitMode = splitMode;
        }
    }

    /**
     * set the count of the workitems should be create for this activity;
     *
     * @param workitemCount
     */
    void setWorkitemCount(int workitemCount) {
        if (workitemCount > 0) {
            this.workitemCount = workitemCount;
        }
    }

    /**
     * set the life cycle of this activiyt;
     *
     * @param lifeCycle
     */
    void setLifeCycle(int lifeCycle) {
        this.lifecycle = lifeCycle;
    }

    /**
     * set the isStart flag of this activity;
     *
     * @param start
     */
    void setStart(boolean start) {
        this.isStart = start;
    }

    /**
     * set the attribute of this activyt;
     *
     * @param attr
     */
    void setAttribute(WFExtendedAttribute attr) {
        attributes.put(attr.getID(), attr);
    }

    /**
     * set the parent process of this activity;
     *
     * @param process
     */
    void setProcess(WFProcess process) {
        this.process = process;
    }

    /**
     * set the isEnd flag of this activity;
     *
     * @param value
     */
    void setEnd(boolean value) {
        isEnd = value;
    }
}