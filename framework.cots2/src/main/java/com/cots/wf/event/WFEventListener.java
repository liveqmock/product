/**
 *all rights reserved,@copyright 2003
 */
package com.cots.wf.event;

import com.cots.wf.execute.ActivityInsEventListener;
import com.cots.wf.execute.ProcessInsEventListener;

/**
 * Description:
 * listener to workflow and activity event;
 * User: chuguanghua
 * Date: 2004-5-12
 * Time: 10:09:46
 */
public interface WFEventListener extends ActivityInsEventListener, ProcessInsEventListener {

    /**
     * called when a timer event is fired;
     */
    public void onTimerEvent();

    public void addECAList(ECAList list);

}
