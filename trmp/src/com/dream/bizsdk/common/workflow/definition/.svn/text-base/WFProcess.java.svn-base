/**
 * copyright 2004,primton ltd.All rights reserved.
 */

package com.dream.bizsdk.common.workflow.definition;

import java.util.HashMap;
import java.util.Vector;

/**
 * Module:  WFProcess.java
 * Description:
 * <p/>
 * <p/>
 * Author:  chuguanghua
 * Created: 2004Äê3ÔÂ31ÈÕ 10:11:41
 * Purpose: Defines the Class WFProcess
 */

public class WFProcess {

    private String id;
    private String name;
    private String accessLevel;
    private long lifeCycle;
    private String desc;

    private HashMap dataFields = new HashMap();
    private HashMap transitions = new HashMap();
    private HashMap participants = new HashMap();
    private HashMap applications = new HashMap();
    private HashMap activities = new HashMap();
    private HashMap incomingTrst = new HashMap();
    private HashMap outcomingTrst = new HashMap();

    private Vector startActivities = new Vector();

    public static String HOME_PATH = "d:\\workflow\\definition\\";

    public WFProcess() {

    }

    public WFProcess(String id, String name, String accessLevel) {
        this.id = id;
        this.name = name;
        this.accessLevel = accessLevel;
    }

    /**
     * get ID of the process;
     *
     * @return the process ID;
     */
    public String getID() {
        return this.id;
    }

    /**
     * Get the name of the process;
     *
     * @return the process name;
     */
    public String getName() {
        return this.name;
    }

    public long getLifeCycle() {
        return this.lifeCycle;
    }

    /**
     * Get the access level of the processs;
     *
     * @return the access level;
     */
    public String getAccessLevel() {
        return this.accessLevel;
    }

    public String getDescription() {
        return this.desc;
    }

    /**
     * Get the start activities of the process.
     *
     * @return the array of the startActivities;
     */
    public WFAbstractActivity[] getStartActivities() {
        return (WFAbstractActivity[]) startActivities.toArray(new WFAbstractActivity[startActivities.size()]);
    }

    /**
     * Get all the incoming transitions to an activity, if this activity doest exist,
     * or this activity has no incoming transitions, then array of 0 elements will be
     * returned;
     *
     * @param activityName
     * @return WFTransition[] obect, never null;
     */
    public WFTransition[] getIncomingTransitions(String activityName) {
        Vector activities = (Vector) incomingTrst.get(activityName);
        if (activities == null) {
            return new WFTransition[0];
        } else {
            return (WFTransition[]) activities.toArray(new WFTransition[activities.size()]);
        }
    }

    /**
     * Get all the outcoming transitions from an activity, if this activity doest exist,
     * or this activity has no outcoming transitions, then array of 0 elements will be
     * returned;
     *
     * @param activityName
     * @return WFTransition[] obect, never null;
     */
    public WFTransition[] getOutcomingTransitions(String activityName) {
        Vector activities = (Vector) outcomingTrst.get(activityName);
        if (activities == null) {
            return new WFTransition[0];
        } else {
            return (WFTransition[]) activities.toArray(new WFTransition[activities.size()]);
        }
    }

    /**
     * Get an activity by id; if there is no this activity in this
     * process, then null will be reutrned.
     *
     * @param activityID the target activity, null if no this activity;
     */
    public WFAbstractActivity getActivity(String activityID) {
        return (WFAbstractActivity) activities.get(activityID);
    }

    /**
     * Get the To activity of a transition, if this Transition does not exist,
     * then null is returned.
     *
     * @param trstID the transition ID;
     * @return WFAbstractActivity  object that the transition moves to;
     */
    public WFAbstractActivity getToActivity(String trstID) {
        WFAbstractActivity acti = null;
        WFTransition trst = getTransition(trstID);
        if (trst != null) {
            acti = getActivity(trst.getToActivityName());
        }
        return acti;
    }

    /**
     * Get the From activity Object of a transition, if the Transition does not exist,
     * then null is returned.
     *
     * @param trstID the transition ID;
     * @return WFAbstractActivity  object from which the transition comes out;
     */
    public WFAbstractActivity getFromActivity(String trstID) {
        WFAbstractActivity acti = null;
        WFTransition trst = getTransition(trstID);
        if (trst != null) {
            acti = getActivity(trst.getFromActivityName());
        }
        return acti;
    }

    /**
     * Get a Data field by ID;
     *
     * @param dataID
     * @return DataField object;
     */
    public WFDataField getDataField(String dataID) {
        return (WFDataField) dataFields.get(dataID);
    }

    /**
     * Get a participant object by ID;
     *
     * @param pID
     * @return
     */
    public WFParticipant getParticipant(String pID) {
        return (WFParticipant) participants.get(pID);
    }

    /**
     * get All particiants;
     *
     * @return
     */
    public WFParticipant[] getAllParticipants() {
        return (WFParticipant[]) participants.values().toArray(new WFParticipant[participants.size()]);
    }

    /**
     * Get an Application object by ID;
     *
     * @param appID
     * @return WFApplicaiton object;
     */
    public WFApplication getApplication(String appID) {
        return (WFApplication) applications.get(appID);
    }

    /**
     * Get a transition object by ID;
     *
     * @param trstID the id of the Transition;
     * @return Transition object;
     */
    public WFTransition getTransition(String trstID) {
        return (WFTransition) transitions.get(trstID);
    }

    /**
     * set the Process ID;
     *
     * @param id the new Process ID;
     */
    void setID(String id) {
        this.id = id;
    }

    /**
     * Set the Process Name;
     *
     * @param name the new Process Name;
     */
    void setName(String name) {
        this.name = name;
    }

    /**
     * Set the Process AccessLevel;
     *
     * @param accessLevel the new Access Level;
     */
    void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    void setLifeCycle(long cycle) {
        this.lifeCycle = cycle;
    }

    void setDescription(String desc) {
        this.desc = desc;
    }

    void addActivity(WFAbstractActivity act) {
        this.activities.put(act.getID(), act);
    }

    void addTransition(WFTransition trst) {
        this.transitions.put(trst.getID(), trst);
    }

    void addApplication(WFApplication app) {
        this.applications.put(app.getID(), app);
    }

    void addParticipant(WFParticipant pt) {
        this.participants.put(pt.getID(), pt);
    }

    void addEvent(WFEvent event) {

    }

    void addDataField(WFDataField wf) {
        this.dataFields.put(wf.getID(), wf);
    }
}