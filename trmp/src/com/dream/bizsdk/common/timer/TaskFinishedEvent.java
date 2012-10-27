/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.timer;

import java.util.Date;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-21
 * Time: 9:46:49
 */
public class TaskFinishedEvent {
    private String taskName;
    private Date finishTime;
    private int retCode;

    public TaskFinishedEvent(String taskName, Date finishTime, int retCode) {
        this.taskName = taskName;
        this.finishTime = finishTime;
        this.retCode = retCode;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public Date getFinishTime() {
        return this.finishTime;
    }

    public int getRetCode() {
        return this.retCode;
    }
}
