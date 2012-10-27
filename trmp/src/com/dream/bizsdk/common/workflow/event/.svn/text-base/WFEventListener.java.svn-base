/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.workflow.event;

import com.dream.bizsdk.common.workflow.execute.ProcessInsEvent;
import com.dream.bizsdk.common.workflow.execute.ActivityInsEvent;
import com.dream.bizsdk.common.workflow.execute.ActivityInsEventListener;
import com.dream.bizsdk.common.workflow.execute.ProcessInsEventListener;

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
