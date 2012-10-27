/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.workflow.event;

import com.dream.bizsdk.common.workflow.definition.WFProcess;
import com.dream.bizsdk.common.workflow.execute.ExecuteEngine;
import com.dream.bizsdk.common.workflow.execute.ProcessIns;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-5-12
 * Time: 11:06:41
 */
public class ECAManager {

    private WFEventListener wfel = new WFEventListenerImpl();

    public ECAManager() {

    }

    /**
     * load all the defined events from the a process definition;
     *
     * @param process the target Process definition;
     * @return the ECAList object that contains all the events in the process;
     */
    public ECAList loadEventsForProcessIns(ProcessIns process) {
        return null;
    }

    /**
     * register all the events to the listeners or the timer;
     *
     * @param list the list of eca list to be regitered;
     */
    public void registerECAList(ECAList list) {
        wfel.addECAList(list);
    }

    /**
     * register listener to execute engine.
     *
     * @param ee
     */
    public void registerToEngine(ExecuteEngine ee) {
        ee.addActivityInsEventListener(wfel);
    }
}
