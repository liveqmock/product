/**
 * copyright 2004,primton ltd.All rights reserved.
 */


package com.dream.bizsdk.common.workflow.definition;

import java.util.HashMap;

/**
 * Module:  WFTransition.java
 * Description:
 * <p/>
 * <p/>
 * Author:  chuguanghua
 * Created: 2004Äê3ÔÂ31ÈÕ 10:12:09
 * Purpose: Defines the Class WFTransition
 */

public class WFTransition {
    /**
     * ID of this transition
     */
    private String id;

    /**
     * the activity name from which this transition goes out;
     */
    private String fromActivityName;

    /**
     * the activity name to wchich this transition goest into;
     */
    private String toActivityName;

    /**
     * the condition of this transition;
     */
    private String condition;

    private HashMap extendedAttributes = new HashMap();

    /**
     * the process to which this activity belongs to;
     */
    private WFProcess process = null;

    public WFTransition() {

    }

    public WFTransition(WFProcess process, String id, String fromActivityName
                        , String toActivityName, String condition) {
        this.process = process;
        this.id = id;
        this.fromActivityName = fromActivityName;
        this.toActivityName = toActivityName;
        this.condition = condition;
    }

    /**
     * Get the ID of this transition;
     *
     * @return
     */
    public String getID() {
        return this.id;
    }

    /**
     * Get the condition of this transition;
     *
     * @return
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Get the process assoiciated with the process;
     *
     * @return
     */
    public WFProcess getProcess() {
        return this.process;
    }

    /**
     * Get the From Activity Name of this activity;
     *
     * @return
     */
    public String getFromActivityName() {
        return this.fromActivityName;
    }

    /**
     * Get to Activity Name of this activity;
     *
     * @return
     */
    public String getToActivityName() {
        return this.toActivityName;
    }

    public String getExtendedAttribute(String name) {
        return (String) extendedAttributes.get(name);
    }

    /**
     * Get the Name of the to activity;
     *
     * @return
     */
    public WFAbstractActivity getToActivity() {
        return this.process.getToActivity(this.id);
    }

    /**
     * Get the from activity;
     *
     * @return
     */
    public WFAbstractActivity getFromActivity() {
        return this.process.getFromActivity(this.id);
    }

    /**
     * set the condition;
     *
     * @param condition
     */
    void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * set the process;
     *
     * @param process
     */
    void setProcess(WFProcess process) {
        this.process = process;
    }

    /**
     * set the ID of this activity;
     *
     * @param id
     */
    void setID(String id) {
        this.id = id;
    }

    /**
     * se the from activity name;
     *
     * @param from
     */
    void setFromActivityName(String from) {
        this.fromActivityName = from;
    }

    /**
     * set the to activity name;
     *
     * @param to
     */
    void setToActivityName(String to) {
        this.toActivityName = to;
    }

    void setExtendedAttribute(String name, String value) {
        this.extendedAttributes.put(name, value);
    }
}