/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.workflow.execute;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-4-2
 * Time: 9:38:40
 */
public class ProcessInsEvent {
    public final static int PI_STARTED = 1;
    public final static int PI_FINISHED = 2;
    public final static int PI_CANCELED = 3;
    public final static int PI_SUSPENDED = 4;
    public final static int PI_RESUMED = 5;

    private ProcessIns pi;

    public ProcessInsEvent(ProcessIns pi) {
        this.pi = pi;
    }

    public ProcessIns getProcessIns() {
        return this.pi;
    }
}
