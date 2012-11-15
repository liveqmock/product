/**
 *all rights reserved,@copyright 2003
 */
package com.cots.wf.execute;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-4-1
 * Time: 14:25:53
 */
public class ActivityInsEvent {
    public final static int AE_STARTED = 1;
    public final static int AE_FINISHED = 2;
    public final static int AE_CANCELED = 3;
    public final static int AE_SUSPENDED = 4;

    private ActivityIns ai;


    public ActivityInsEvent(ActivityIns ai) {
        this.ai = ai;
    }

    public ActivityIns getActivityIns() {
        return this.ai;
    }
}
