/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.workflow.execute;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-4-1
 * Time: 14:25:39
 */
public interface ActivityInsEventListener {
    /**
     * notified when an activity was created;
     *
     * @param event
     */
    public void onActivityStarted(ActivityInsEvent event);

    /**
     * notified when an activity was finished;
     *
     * @param event
     */
    public void onActivityFinished(ActivityInsEvent event);

    /**
     * notified when an activity was canceled;
     *
     * @param event
     */
    public void onActivityCanceled(ActivityInsEvent event);

    /**
     * notified when an activity was suspended;
     *
     * @param event
     */
    public void onActivitySuspended(ActivityInsEvent event);

    /**
     * @param event
     */
    public void onActivityResumed(ActivityInsEvent event);
}
