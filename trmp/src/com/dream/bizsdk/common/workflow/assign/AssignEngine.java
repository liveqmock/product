/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.workflow.assign;

import com.dream.bizsdk.common.workflow.execute.ActivityInsEventListener;
import com.dream.bizsdk.common.workflow.execute.ActivityInsEvent;
import com.dream.bizsdk.common.workflow.execute.ActivityIns;
import com.dream.bizsdk.common.workflow.definition.WFAbstractActivity;
import com.dream.bizsdk.common.workflow.definition.WFGenericActivity;
import com.dream.bizsdk.common.workflow.definition.WFApplicationActivity;
import com.dream.bizsdk.common.workflow.definition.WFApplication;

import java.util.ArrayList;
import java.util.Date;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Description:
 * AssignEngine accepts notifications(ActivityIns Events) from the ExecuteEngine,
 * and sends Workitem Events to ExecuteEngine.
 * When an ActivityIns is started, Assign will get this event and create workitems
 * for this activity. When a performet finishes the newly created workitem(s), then Assign
 * engine will send a Workitem finished event to the Execute Engine; then the ExecuteEngine
 * will check if the Activities can be finished, if that is the case, then the activity will
 * be finished and next activities will be processed.
 * <p/>
 * User: chuguanghua
 * Date: 2004-4-1
 * Time: 15:21:27
 */
public class AssignEngine implements ActivityInsEventListener {
    //dao to persitent workitems;
    private WorkitemDAO widao;

    private PerformerResolver pr;

    private ArrayList itemEventListener = new ArrayList();

    private Logger log = Logger.getLogger(AssignEngine.class);

    /**
     * notified by ExecuteEngine when an activity(GenericActivity or ApplicationActivity)
     * was started.
     *
     * @param event
     */
    public void onActivityStarted(ActivityInsEvent event) {
        ActivityIns ai = event.getActivityIns();

        WFAbstractActivity ad = ai.getActivityDef();
        if (ad instanceof WFGenericActivity) {    //this activity is a Generic Activity;
            int workitemCount = ad.getWorkitemCount();
            WFGenericActivity ga = (WFGenericActivity) ad;

            for (int i = 0; i < workitemCount; i++) {
                //create a new workitem for this activities;
                Workitem wi = new Workitem();
                wi.setProcessID(ai.getProcessInsID());
                wi.setActivityID(ai.getID());
                wi.setPerformerID(pr.resolve(ga.getParticipant())); //solve the participant to a specific Performer;
                wi.setCreateTime(new Date());
                wi.setLifeCycle(ga.getLifeCycle());
                wi.setAction(ga.getAction());

                //inser this workitem to the storage;
                try {
                    widao.insertWorkitem(wi);
                } catch (SQLException e) {
                    log.error("can't insert the workitem to database", e);
                }
            }
        } else if (ad instanceof WFApplicationActivity) {  //this is an application activity
            //execute this application via the ApplicationCaller;
            WFApplicationActivity appa = (WFApplicationActivity) ad;
            WFApplication app = appa.getApplication();
            ApplicationCaller.call(app, ai.getProcessIns().getData());
        }
    }

    /**
     * notified by ExecuteEngine when a activity was finished;
     * if activity was finished, all the workitems associated with
     * this activity should be set "finished".
     *
     * @param event
     */
    public void onActivityFinished(ActivityInsEvent event) {
        long activityID = event.getActivityIns().getID();
        try {
            widao.updateAIWorkitemStatus(activityID, Workitem.WORKITEM_FINISHED);
        } catch (SQLException e) {
            log.error("can't update the workitems' status to FINISHED", e);
        }
    }

    /**
     * notified by the ExecuteEngine when an acitivity was canceled.
     * The running and suspended workitems associated with this
     * activity should be set cancled;
     *
     * @param event
     */
    public void onActivityCanceled(ActivityInsEvent event) {
        long activityID = event.getActivityIns().getID();
        try {
            widao.updateAIWorkitemStatus(activityID, Workitem.WORKITEM_RUNNING, Workitem.WORKITEM_FINISHED);
            widao.updateAIWorkitemStatus(activityID, Workitem.WORKITEM_SUSPENDED, Workitem.WORKITEM_FINISHED);
        } catch (SQLException e) {
            log.error("can't update the workitems' status to be canceled", e);
        }
    }

    /**
     * notified by ExecuteEngine when an activity was Suspended.
     *
     * @param event
     */
    public void onActivitySuspended(ActivityInsEvent event) {
        long activityID = event.getActivityIns().getID();
        try {
            widao.updateAIWorkitemStatus(activityID, Workitem.WORKITEM_RUNNING, Workitem.WORKITEM_SUSPENDED);
        } catch (SQLException e) {
            log.error("can't update the workitems' status to be suspended", e);
        }
    }

    /**
     * Notified by ExecuteEngine when an Activity was Resumed.
     *
     * @param event
     */
    public void onActivityResumed(ActivityInsEvent event) {
        long activityID = event.getActivityIns().getID();
        try {
            widao.updateAIWorkitemStatus(activityID, Workitem.WORKITEM_SUSPENDED, Workitem.WORKITEM_RUNNING);
        } catch (SQLException e) {
            log.error("can't update the workitems' status to be resumed", e);
        }
    }

    /**
     * add a workitem event listener.
     *
     * @param l the listener object;
     */
    public void addWorkitemEventListener(WorkitemEventListener l) {
        itemEventListener.add(l);
    }

    /**
     * @param processID
     * @param activityID
     * @param workitemID
     * @param eventType
     */
    protected void fireWorkitemEvent(long processID, long activityID, long workitemID, int eventType) {
        int count = itemEventListener.size();
        WorkitemEvent we = new WorkitemEvent(processID, activityID, workitemID);
        for (int i = 0; i < count; i++) {
            WorkitemEventListener el = (WorkitemEventListener) itemEventListener.get(i);
            switch (eventType) {
                case Workitem.WORKITEM_RUNNING:
                    el.onWorkitemRunning(we);
                    break;
                case Workitem.WORKITEM_FINISHED:
                    el.onWorkitemFinished(we);
                    break;
                case Workitem.WORKITEM_SUSPENDED:
                    el.onWorkitemSuspended(we);
                    break;
                case Workitem.WORKITEM_CANCELED:
                    el.onWorkitemCanceled(we);
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * fire workitem event;
     *
     * @param wi        the Workitem Object which the current event is associated;
     * @param eventType the event type;
     */
    protected void fireWorkitemEvent(Workitem wi, int eventType) {
        fireWorkitemEvent(wi.getProcessID(), wi.getActivityID(), wi.getWorkitemID(), eventType);
    }

    /**
     * get the dao for the workitem;
     *
     * @return
     */
    public WorkitemDAO getWorkitemDAO() {
        return widao;
    }

    /**
     * set the dao for workitem dao;
     *
     * @param widao the target dao object
     */
    public void setWorkitemDAO(WorkitemDAO widao) {
        this.widao = widao;
    }

    /**
     * Get the performer resolver;
     *
     * @return
     */
    public PerformerResolver getPerformerResolver() {
        return pr;
    }

    /**
     * set the performer resolver;
     *
     * @param pr
     */
    public void setPerformerResolver(PerformerResolver pr) {
        this.pr = pr;
    }

    /**
     * finish a workitem. this will notify to all the worktitem event listeners.
     *
     * @param workitemID the id of the workitem that will be finished.
     */
    public void finishWorkitem(long workitemID) {
        int rows = 0;
        try {
            rows = widao.updateWorkitemStatus(workitemID, Workitem.WORKITEM_FINISHED);
            if (rows > 0) {
                Workitem wi = widao.getWorkitem(workitemID);
                fireWorkitemEvent(wi, Workitem.WORKITEM_FINISHED);
            }
        } catch (SQLException e) {
            log.error("can't update the workitems' status to FINISHED", e);
        }
    }
}