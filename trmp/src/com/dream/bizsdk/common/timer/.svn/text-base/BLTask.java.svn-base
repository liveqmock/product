/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.timer;

import com.dream.bizsdk.common.blc.IBLContext;
import com.dream.bizsdk.common.blc.BLResult;
import com.dream.bizsdk.common.SysVar;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-12
 * Time: 16:45:25
 */
public class BLTask extends Task {

    protected String className;
    protected String methodName;

    public BLTask() {

    }

    public BLTask(TaskManager tm) {
        this.tm = tm;
    }

    public BLTask(String name) {
        this.name = name;
    }

    public BLTask(String name, TaskManager tm) {
        this.name = name;
        this.tm = tm;
    }


    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void run() {
        //save the run time;
        setLastRunTime(new Date());

        int retCode = SysVar.SUCCEED;

        IBLContext context = tm.getContext();
        try {
            BLResult br = context.execBL(className, methodName, params, null);
            retCode = br.retCode;
        } catch (Throwable e) {
            e.printStackTrace();
        }

        taskFinished(retCode);
    }
}