/**
 *all rights reserved,@copyright 2003
 */
package com.cots.wf.event;

import com.cots.wf.execute.ProcessInsEvent;
import com.cots.wf.execute.ActivityInsEvent;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-5-12
 * Time: 11:13:35
 */
public class WFEventListenerImpl implements WFEventListener {

    private ECAList eventList = new ECAList();

    public void onWorkflowEvent(ProcessInsEvent pise) {

    }

    public void onActivityEvent(ActivityInsEvent ae) {

    }

    public void onTimerEvent() {

    }

    public synchronized void addECAList(ECAList list) {
        eventList.addECAList(list);
    }

    public void onActivityStarted(ActivityInsEvent event) {

    }

    public void onActivityFinished(ActivityInsEvent event) {

    }

    public void onActivityCanceled(ActivityInsEvent event) {

    }

    public void onActivitySuspended(ActivityInsEvent event) {

    }

    public void onActivityResumed(ActivityInsEvent event) {

    }

    public void onProcessInsStarted(ProcessInsEvent pie) {

    }

    public void onProcessInsFinished(ProcessInsEvent pie) {

    }

    public void onProcessInsCanceled(ProcessInsEvent pie) {

    }

    public void onProcessInsSuspended(ProcessInsEvent pie) {

    }
}
