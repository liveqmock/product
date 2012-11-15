/**
 *all rights reserved,@copyright 2003
 */
package com.cots.wf.execute;

import com.cots.wf.assign.WorkitemEventListener;
import com.cots.wf.assign.WorkitemEvent;
import com.cots.wf.definition.WFProcess;
import com.cots.wf.definition.Util;
import com.cots.wf.data.BizData;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Description:
 * This class represents a Workflow execution engine. An engine is associated with
 * some workflow instances and responsible for running these workflow instances.
 * Clients can create, finish or cancel workflow instances via this engine. They
 * can also add ActivityEventListeners to this engine;
 * Currently, there is only one execution engine in the workflow management system.
 * <p/>
 * User: chuguanghua
 * Date: 2004-4-1
 * Time: 14:29:05
 */
public class ExecuteEngine implements WorkitemEventListener {
    /**
     * process instances
     */
    protected HashMap instances = new HashMap();
    /**
     * actvity events listeners
     */
    protected ArrayList activityEventListeners = new ArrayList();
    /**
     * process instance event listeners
     */
    protected ArrayList processInsEventListeners = new ArrayList();

    protected ProcessInsDAO pdao;
    protected ActivityInsDAO adao;

    private ExecuteEngine() {

    }

    /**
     * get the dao object for this engine to load and save process instance;
     *
     * @return
     */
    public ProcessInsDAO getProcessInsDAO() {
        return pdao;
    }

    /**
     * set the dao object for this engine to save and load porcess instance;
     *
     * @param dao the target dao object;
     */
    public void setProcessInsDAO(ProcessInsDAO dao) {
        this.pdao = dao;
    }

    /**
     * get the ActivityInsDAO object used by this engine;
     *
     * @return
     */
    public ActivityInsDAO getActivityInsDAO() {
        return adao;
    }

    /**
     * set the ActivityInsDAO object to be used by this engine;
     *
     * @param dao the ActivityInsDAO object to be used.
     */
    public void setActivityInsDAO(ActivityInsDAO dao) {
        this.adao = dao;
    }

    /**
     * create a new Workflow instance and start it at the same time.
     *
     * @param wfName the displayName of the workflow;
     * @param data   the workflow relevant Data object;
     * @return the newly created workflow instance;
     */
    public ProcessIns newProcessIns(String wfName, BizData data) {
        //get process definition;
        WFProcess wf = Util.getProcess(wfName);
        //create a new process instance;
        ProcessIns pi = new ProcessIns(this, wf);
        //set the relevant data object;
        pi.setData(data);
        //add this new process instance to cache;
        instances.put(new Long(pi.getID()), pi);
        //start this porcess;
        pi.start();

        //save the process instance to the storage;
        pdao.insert(pi);
        return pi;
    }

    /**
     * create a workflow instance without a BizData object.
     *
     * @param wfName the workflow displayName;
     * @return the newly create workflow instance;
     */
    public ProcessIns newProcessIns(String wfName) {
        return newProcessIns(wfName, null);
    }

    /**
     * @param pid
     */
    public void cancelProcessIns(long pid) {
        ProcessIns pi = getProcessIns(pid);
        if (pi != null) {
            pi.cancel();
            //update the process instance
            pdao.update(pi);
        }
    }

    /**
     * @param pid
     */
    public void suspendProcessIns(long pid) {
        ProcessIns pi = getProcessIns(pid);
        if (pi != null) {
            pi.suspend();

            //update the process instance
            pdao.update(pi);
        }
    }

    /**
     * @param pid
     */
    public void resumeProcessIns(long pid) {
        ProcessIns pi = getProcessIns(pid);
        if (pi != null) {
            pi.resume();

            //update the process instance
            pdao.update(pi);
        }
    }

    /**
     * Get a process instance by id of an activity instance;
     *
     * @param activityInsID
     * @return the target ProcessIns, may be null;
     */
    public ProcessIns getProcessInsByActivtyID(long activityInsID) {
        Iterator it = instances.values().iterator();
        while (it.hasNext()) {
            ProcessIns pi = (ProcessIns) it.next();
            if (pi.getActivytInsByID(activityInsID) != null) {
                return pi;
            }
        }
        return null;
    }

    protected synchronized ProcessIns getProcessIns(long pid) {
        Long id = new Long(pid);
        ProcessIns pi = (ProcessIns) instances.get(id);
        if (pi == null) {
            pi = pdao.getProcessIns(pid);
            if (pi != null) {
                instances.put(id, pi);
            }
        }
        return pi;
    }

    /**
     * add an activity event listener to this engine;
     *
     * @param ael the listener to be added;
     */
    public void addActivityInsEventListener(ActivityInsEventListener ael) {
        activityEventListeners.add(ael);
    }

    /**
     * @param piel
     */
    public void addProcessInsEventListener(ProcessInsEventListener piel) {
        processInsEventListeners.add(piel);
    }


    /**
     * fire an activity event to all the listeners;
     *
     * @param ai        the associated ActivityIns.
     * @param eventType the event type.
     */
    public void fireActivityInsEvent(ActivityIns ai, int eventType) {
        ActivityInsEvent ae = new ActivityInsEvent(ai);
        switch (eventType) {
            case ActivityInsEvent.AE_CANCELED:
                fireActivityInsCanceled(ae);
                break;
            case ActivityInsEvent.AE_FINISHED:
                fireActivityInsFinished(ae);
                break;
            case ActivityInsEvent.AE_STARTED:
                fireActivityInsStarted(ae);
                break;
            case ActivityInsEvent.AE_SUSPENDED:
                fireActivityInsSuspended(ae);
                break;
            default:
                break;
        }
    }

    /**
     * fire a process instance event to all the process event listeners;
     *
     * @param pi        the process event that this event is associated with;
     * @param eventType the event type;
     */
    public void fireProcessInsEvent(ProcessIns pi, int eventType) {
        ProcessInsEvent pie = new ProcessInsEvent(pi);
        switch (eventType) {
            case ProcessInsEvent.PI_CANCELED:
                fireProcessInsCanceled(pie);
                break;
            case ProcessInsEvent.PI_FINISHED:
                fireProcessInsFinished(pie);
                break;
            case ProcessInsEvent.PI_STARTED:
                fireProcessInsStarted(pie);
                break;
            case ProcessInsEvent.PI_SUSPENDED:
                fireProcessInsSuspended(pie);
                break;
            default:
                break;
        }
    }

    /**
     * fire an activity canceled event to all the listeners;
     *
     * @param ae the event to be fired;
     */
    private void fireActivityInsCanceled(ActivityInsEvent ae) {
        int count = activityEventListeners.size();
        for (int i = 0; i < count; i++) {
            ((ActivityInsEventListener) activityEventListeners.get(i)).onActivityCanceled(ae);
        }
    }

    /**
     * fire an activity started event to all the listeners;
     *
     * @param ae the event to be fired;
     */
    private void fireActivityInsStarted(ActivityInsEvent ae) {
        int count = activityEventListeners.size();
        for (int i = 0; i < count; i++) {
            ((ActivityInsEventListener) activityEventListeners.get(i)).onActivityStarted(ae);
        }
    }

    /**
     * fire an activity finished event to all the listeners.
     *
     * @param ae the event to be fired;
     */
    private void fireActivityInsFinished(ActivityInsEvent ae) {
        int count = activityEventListeners.size();
        for (int i = 0; i < count; i++) {
            ((ActivityInsEventListener) activityEventListeners.get(i)).onActivityFinished(ae);
        }
    }

    private void fireActivityInsSuspended(ActivityInsEvent ae) {
        int count = activityEventListeners.size();
        for (int i = 0; i < count; i++) {
            ((ActivityInsEventListener) activityEventListeners.get(i)).onActivitySuspended(ae);
        }
    }

    /**
     * fire an activity canceled event to all the listeners;
     *
     * @param pie the event to be fired;
     */
    private void fireProcessInsCanceled(ProcessInsEvent pie) {
        int count = processInsEventListeners.size();
        for (int i = 0; i < count; i++) {
            ((ProcessInsEventListener) processInsEventListeners.get(i)).onProcessInsCanceled(pie);
        }
    }

    /**
     * fire an activity started event to all the listeners;
     *
     * @param pie the event to be fired;
     */
    private void fireProcessInsStarted(ProcessInsEvent pie) {
        int count = processInsEventListeners.size();
        for (int i = 0; i < count; i++) {
            ((ProcessInsEventListener) processInsEventListeners.get(i)).onProcessInsStarted(pie);
        }
    }

    /**
     * fire an activity finished event to all the listeners.
     *
     * @param pie the event to be fired;
     */
    private void fireProcessInsFinished(ProcessInsEvent pie) {
        int count = processInsEventListeners.size();
        for (int i = 0; i < count; i++) {
            ((ProcessInsEventListener) processInsEventListeners.get(i)).onProcessInsFinished(pie);
        }
    }

    /**
     * fire a process instance suspended event to all the listeners;
     *
     * @param pie the event to be fired;
     */
    private void fireProcessInsSuspended(ProcessInsEvent pie) {
        int count = processInsEventListeners.size();
        for (int i = 0; i < count; i++) {
            ((ProcessInsEventListener) processInsEventListeners.get(i)).onProcessInsSuspended(pie);
        }
    }

    /**
     * notified when a workitem is finished;
     *
     * @param e
     */
    public void onWorkitemFinished(WorkitemEvent e) {
        long processInsID = e.getProcessID();
        long activityInsID = e.getActivityID();
        ProcessIns p = (ProcessIns) instances.get(new Long(processInsID));
        if (p == null) {
            //load from the storage;
            p = pdao.getProcessIns(processInsID);
            if (p == null) {
                //no this process instance;
                return;
            } else {
                instances.put(new Long(processInsID), p);
            }
        }

        p.finishActivity(activityInsID);
    }

    /**
     * not implemented;
     *
     * @param e
     */
    public void onWorkitemCanceled(WorkitemEvent e) {
        //not supported;
    }

    /**
     * not implemented;
     *
     * @param e
     */
    public void onWorkitemSuspended(WorkitemEvent e) {
        //not supported;
    }

    /**
     * not implemented;
     *
     * @param e
     */
    public void onWorkitemRunning(WorkitemEvent e) {
        //not supported;
    }
}