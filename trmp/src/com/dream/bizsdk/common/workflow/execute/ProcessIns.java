/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.workflow.execute;

import com.dream.bizsdk.common.workflow.definition.*;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.exp.Expression;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-4-1
 * Time: 14:29:54
 */
public class ProcessIns {

    public final static int PROCESS_CREATED = 0;
    public final static int PROCESS_STARTED = 1;
    public final static int PROCESS_FINISHED = 2;
    public final static int PROCESS_CANCELED = 3;
    public final static int PROCESS_SUSPENDED = 4;

    private static long NEXTID = 1;

    private long id;
    private String wfName;
    private int status = ProcessIns.PROCESS_CREATED;
    private Date createTime = new Date();
    private Date endTime;
    private long parentActivityID = -1;
    private WFProcess processDef;
    private BizData data;
    private boolean _isNew = true;

    private HashMap actInstances = new HashMap();
    private HashMap trstInstances = new HashMap();

    private ExecuteEngine engine;

    public ProcessIns(ExecuteEngine engine, WFProcess processDef) {
        this.engine = engine;
        this.processDef = processDef;
        this.id = getNextID();
    }

    /**
     * get nextID for this instance;
     *
     * @return
     */
    synchronized private static long getNextID() {
        return ProcessIns.NEXTID++;
    }

    public WFProcess getProcessDef() {
        return processDef;
    }

    public long getID() {
        return this.id;
    }

    public int getStatus() {
        return status;
    }

    public String getWfName() {
        return wfName;
    }

    public void setWfName(String name) {
        this.wfName = name;
    }

    public void setStatus(int status) {
        //if the workflow is ended, then its status can't changed;
        if (this.status != ProcessIns.PROCESS_FINISHED && this.status != ProcessIns.PROCESS_CANCELED) {
            this.status = status;
            //set the end time;
            if (status == ProcessIns.PROCESS_FINISHED || status == ProcessIns.PROCESS_CANCELED) {
                endTime = new Date();
            }
        }
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public long getParentActivityID() {
        return this.parentActivityID;
    }

    /**
     * set the parent activity ID;
     *
     * @param parentID
     */
    public void setParentActivityID(long parentID) {
        this.parentActivityID = parentID;
    }

    /**
     * get the create time of this process instance;
     *
     * @return
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * get the relevant Data object;
     *
     * @return
     */
    public BizData getData() {
        return data;
    }

    /**
     * set the relvant Data object;
     *
     * @param data
     */
    public void setData(BizData data) {
        this.data = data;
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
     * start this process intance.
     */
    public void start() {
        int count = 0;
        //set the status of the process to be started and
        //fire the event;
        setStatus(ProcessIns.PROCESS_STARTED);
        engine.fireProcessInsEvent(this, ProcessInsEvent.PI_STARTED);

        WFAbstractActivity[] acts = processDef.getStartActivities();
        count = acts.length;
        for (int i = 0; i < count; i++) {
            startActivity(acts[i]);
        }
    }

    /**
     * cancel this process instance;
     */
    public void cancel() {
        setStatus(ProcessIns.PROCESS_CANCELED);

        //cancel all the running and suspended activities;
        Iterator it = actInstances.values().iterator();
        while (it.hasNext()) {
            ActivityIns ai = (ActivityIns) it.next();
            int status = ai.getStatus();
            if (status == ActivityIns.ACTIVITY_STARTED || status == ActivityIns.ACTIVITY_SUSPENDED) {
                ai.setStatus(ActivityIns.ACTIVITY_CANCELED);
                engine.fireActivityInsEvent(ai, ActivityIns.ACTIVITY_CANCELED);
            }
        }

        //fire the process event
        engine.fireProcessInsEvent(this, ProcessInsEvent.PI_CANCELED);
    }

    /**
     * suspend this process instance;
     */
    public void suspend() {
        setStatus(ProcessIns.PROCESS_SUSPENDED);

        //suspend all the running activities;
        Iterator it = actInstances.values().iterator();
        while (it.hasNext()) {
            ActivityIns ai = (ActivityIns) it.next();
            int status = ai.getStatus();
            if (status == ActivityIns.ACTIVITY_STARTED) {
                ai.setStatus(ActivityIns.ACTIVITY_SUSPENDED);
                engine.fireActivityInsEvent(ai, ActivityIns.ACTIVITY_SUSPENDED);
            }
        }

        engine.fireProcessInsEvent(this, ProcessInsEvent.PI_SUSPENDED);
    }

    /**
     * finish this process instance;
     */
    public void finish() {
        /**finish all the activities that are still running*/
        Iterator it = actInstances.values().iterator();
        while (it.hasNext()) {
            ActivityIns ai = (ActivityIns) it.next();
            internalFinishActivity(ai);
        }

        setStatus(ProcessIns.PROCESS_FINISHED);
        //if this is a subflow, then finish the parent Activity instance;
        if (this.parentActivityID > 0) {
            ProcessIns pi = engine.getProcessInsByActivtyID(this.parentActivityID);
            if (pi != null) {
                ActivityIns ai = pi.getActivytInsByID(this.parentActivityID);
                pi.finishActivity(ai);
            }
        }
        //fire the event that the current process is finished;
        engine.fireProcessInsEvent(this, ProcessInsEvent.PI_FINISHED);
    }

    /**
     * resume the current process instance if it is in suspended state;
     */
    public void resume() {
        //if a suspended instance is resumed, then its status will set to started that means it is running;
        setStatus(ProcessIns.PROCESS_STARTED);

        Iterator it = actInstances.values().iterator();
        while (it.hasNext()) {
            ActivityIns ai = (ActivityIns) it.next();
            int status = ai.getStatus();
            if (status == ActivityIns.ACTIVITY_SUSPENDED) {
                ai.setStatus(ActivityIns.ACTIVITY_STARTED);
                engine.fireActivityInsEvent(ai, ActivityIns.ACTIVITY_STARTED);
            }
        }

        engine.fireProcessInsEvent(this, ProcessInsEvent.PI_RESUMED);
    }

    /**
     * start an activity, this will cause the activity to be executed by the engine,
     * applicaiton, or performers;
     *
     * @param activity
     */
    public void startActivity(WFAbstractActivity activity) {
        ActivityIns ai = new ActivityIns(this, activity);
        if (activity instanceof WFSubFlow) {
            startSubflowActivity(ai);
        } else if (activity instanceof WFAbstractRoute) {
            startRouteActivity(ai);
        } else if (activity instanceof WFGenericActivity) {
            startGenericActivity(ai);
        } else if (activity instanceof WFApplicationActivity) {
            startApplicationActivity(ai);
        } else {
            //Unsupported Activity type;
        }
        actInstances.put(new Long(ai.getID()), ai);
    }

    /**
     * finish an activity instance;
     *
     * @param ai
     */
    public void finishActivity(ActivityIns ai) {
        /**split to next activities*/
        String splitMode = ai.getActivityDef().getSplitMode();
        if (splitMode.compareToIgnoreCase("all") == 0) {    //all split mode;
            splitAll(ai);
        } else if (splitMode.compareToIgnoreCase("xor") == 0) {
            splitXOr(ai);
        }
        internalFinishActivity(ai);
    }

    /**
     * finish an activity by the id of the activity;
     *
     * @param aiid the id of the activity instance;
     */
    public void finishActivity(long aiid) {
        Long id = new Long(aiid);
        ActivityIns ai = (ActivityIns) actInstances.get(id);
        if (ai != null) {
            finishActivity(ai);
        }
    }

    /**
     * finish an activity instance directly;
     *
     * @param ai the activity instance that will be finished;
     */
    protected void internalFinishActivity(ActivityIns ai) {
        WFAbstractActivity a = ai.getActivityDef();
        //fire activitent here, NOTE only  WFGenericActivity and WFApplicationActivity
        //need to fire AE_FINISHED event;
        if (a instanceof WFGenericActivity || a instanceof WFApplicationActivity) {
            engine.fireActivityInsEvent(ai, ActivityInsEvent.AE_FINISHED);
        }
        //set the status to be finished;
        ai.setStatus(ActivityIns.ACTIVITY_FINISHED);
    }

    /**
     * get a activity instance in this process instance by id
     *
     * @param activityInsID
     * @return
     */
    public ActivityIns getActivytInsByID(long activityInsID) {
        return (ActivityIns) actInstances.get(new Long(activityInsID));
    }

    /**
     * create a transition object;
     *
     * @param trst transition def;
     */
    public void createTransition(WFTransition trst) {
        /**create the trst instance and put it into cahce*/
        TransitionIns ti = new TransitionIns(trst);
        trstInstances.put(trst.getID(), ti);   //NOTE: if the transition has been created, then it will be overwrited
        /**check if need to start the next activity, if so,start it*/
        WFAbstractActivity act = trst.getToActivity();
        if (needStartActivity(act)) {
            startActivity(act);
        }
    }

    /**
     * check if a transition needs to be created;
     *
     * @param trst
     * @return true if the transition needs to be created, false doesn't need to be created;
     */
    protected boolean needCreateTransition(WFTransition trst) {
        String cond = trst.getCondition();
        if (cond != null && cond.length() > 0) {
            if (Expression.valueOf(cond, new BizData())) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * check if need to start an activity;
     *
     * @param activity
     * @return
     */
    protected boolean needStartActivity(WFAbstractActivity activity) {
        boolean need = false;
        String joinMode = activity.getJoinMode();
        if (joinMode.compareToIgnoreCase("and") == 0) {
            need = joinAnd(activity);
        } else if (joinMode.compareToIgnoreCase("xor") == 0) {
            need = joinXOr(activity);
        }
        return need;
    }

    /**
     * @param ai
     */
    protected void splitAll(ActivityIns ai) {
        WFTransition[] trsts = ai.getActivityDef().getOutcomingTransitions();
        int count = trsts.length;
        for (int i = 0; i < count; i++) {
            if (needCreateTransition(trsts[i])) {
                createTransition(trsts[i]);
            }
        }
    }

    /**
     * @param ai
     */
    protected void splitXOr(ActivityIns ai) {
        ArrayList creatable = new ArrayList();
        WFTransition[] trsts = ai.getActivityDef().getOutcomingTransitions();
        int count = trsts.length;
        for (int i = 0; i < count; i++) {
            if (needCreateTransition(trsts[i])) {
                creatable.add(new Integer(i));
            }
        }
        //NOTE:more then one can be create, then select the first one;
        //can be enhanced to support selection on Priority of transition;
        if (creatable.size() >= 1) {
            int index = ((Integer) creatable.get(0)).intValue();
            createTransition(trsts[index]);
        }
    }

    /**
     * start a route activtiy, this will finish the activty directly;
     *
     * @param route
     */
    protected void startRouteActivity(ActivityIns route) {
        //finish the route activity;
        finishActivity(route);
    }

    /**
     * start a subworkflow activity;
     *
     * @param subFlow
     */
    protected void startSubflowActivity(ActivityIns subFlow) {
        WFSubFlow sf = (WFSubFlow) subFlow.getActivityDef();
        ProcessIns pi = engine.newProcessIns(sf.getProcess().getName());
        pi.setParentActivityID(subFlow.getID());
        //if the sub workflow is executed asyn, then finsih this activity,
        //after the sub workflow instance is finished, this activity will be
        //finished automatically;
        if (sf.isAsynExec()) {
            finishActivity(subFlow);
        }
    }

    /**
     * start a generic activity, this will cause the engine fire an event
     * to listeners;
     *
     * @param genericAct
     */
    protected void startGenericActivity(ActivityIns genericAct) {
        engine.fireActivityInsEvent(genericAct, ActivityInsEvent.AE_STARTED);
    }

    /**
     * start a application activity, this will cause the engine fire an event
     * to listeners;
     *
     * @param applicationAct
     */
    protected void startApplicationActivity(ActivityIns applicationAct) {
        engine.fireActivityInsEvent(applicationAct, ActivityInsEvent.AE_STARTED);
    }

    /**
     * @param activity
     * @return
     */
    protected boolean joinAnd(WFAbstractActivity activity) {
        int count = 0;
        WFTransition[] trsts = activity.getIncomingTransitions();
        for (int i = 0; i < count; i++) {
            if (trstInstances.get(trsts[i].getID()) != null) {
                return false;
            }
        }

        //remove throws transition instances;
        for (int i = 0; i < count; i++) {
            trstInstances.remove(trsts[i].getID());
        }
        return true;
    }

    /**
     * @param activity
     * @return
     */
    protected boolean joinXOr(WFAbstractActivity activity) {
        int count = 0;
        boolean succeed = false;
        WFTransition[] trsts = activity.getIncomingTransitions();
        for (int i = 0; i < count; i++) {
            if (trstInstances.get(trsts[i].getID()) != null) {
                succeed = true;
                break;
            }
        }

        //remove throws transition instances if join succeeds;
        if (succeed) {
            for (int i = 0; i < count; i++) {
                trstInstances.remove(trsts[i].getID());
            }
        }
        return succeed;
    }
}