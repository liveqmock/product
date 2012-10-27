/**
 * All rights reserved.
 */
package com.dream.bizsdk.common.asyn;

import com.dream.bizsdk.common.pool.PoolItem;
import com.dream.bizsdk.common.blc.IBLContext;
import com.dream.bizsdk.common.blc.BLResult;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.SysVar;

import java.util.Vector;
import java.rmi.RemoteException;

/**
 * Worker object is a Worker to make asyn bl call to the target IBLContext interface.
 * This call implements Runnable interface, so it will be wrapped in a thread and this
 * thread will make call to the IBLContext, after the bl call finished, the worker will
 * dispatch an ACEvent to all the listeners. At last, the worker will singal its container
 * (an WorkersPool object) that it is ready for another work.
 * <p/>
 * User: divine
 * Date: 2004-3-2
 * Time: 20:59:36
 */
public class Worker extends PoolItem implements Runnable {
    /**
     * the BLContext that this worker thead will make asyn call to
     */
    private IBLContext context = null;
    /**
     * listeners to this worker
     */
    private Vector listeners = new Vector();
    /**
     * the asyn call this worker is currently processing
     */
    private AsynCall ac = null;

    /**
     * constructor;
     *
     * @param context
     */
    public Worker(IBLContext context) {
        this.context = context;
    }

    /**
     * run this worker as a thread;
     */
    public void run() {
        String className = ac.getClassName();
        String methodName = ac.getMethodName();
        BizData rd = ac.getReqData();
        BizData sd = ac.getSessionData();
        try {
            BLResult br = this.context.execBL(className, methodName, rd, sd);
            ACEvent ace = new ACEvent(className, methodName, br);
            asynCallFinished(ace);
        } catch (Throwable re) {
            rd.add(SysVar.ERROR_MESSAGE, re.getMessage());
            BLResult br = new BLResult(SysError.REMOTE_ERROR, rd, sd);
            ACEvent ace = new ACEvent(className, methodName, br);
            asynCallFinished(ace);
        } finally {
            ac = null;
            listeners.clear();
            free();
        }
    }

    /**
     * add a listener to this worker;
     *
     * @param l the listener
     */
    public void addListenter(ACListener l) {
        listeners.add(l);
    }

    /**
     * set the AsynCall that this worker will processed;
     *
     * @param ac
     */
    public void setAsynCall(AsynCall ac) {
        this.ac = ac;
    }

    /**
     * dispatch the event to all listeners;
     *
     * @param ace the AsynCall event;
     */
    private void asynCallFinished(ACEvent ace) {
        int size = listeners.size();
        for (int i = 0; i < size; i++) {
            ((ACListener) listeners.elementAt(i)).asynCallPerformed(ace);
        }
    }
}
