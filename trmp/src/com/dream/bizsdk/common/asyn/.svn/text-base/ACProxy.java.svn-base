/**
 * All rights reserved.
 */
package com.dream.bizsdk.common.asyn;

import com.dream.bizsdk.common.blc.IBLContext;
import com.dream.bizsdk.common.databus.BizData;

/**
 * An ACProxy object acts as a proxy of asyn bl call to client applications.
 * Client application should create( or share) such an object and call the
 * method: asynCall(String className,String methodName,BizData rd,BizData sd);
 * this call is an asyn call and will immediatly return to the caller without
 * the results of the BL method call. Client can also call:
 * asynCall(String className,String methodName,BizData rd,BizData sd,ACListener l)
 * to provide a listener to be notfiyed of the BLCall result.
 * <p/>
 * User: divine
 * Date: 2004-3-2
 * Time: 21:41:37
 */
public final class ACProxy {

    /**
     * workers thread pool
     */
    protected WorkersPool workers = null;

    /**
     * @param context
     */
    public ACProxy(IBLContext context) {
        workers = new WorkersPool(context);
    }

    /**
     * make an asyn call without a listener of the result;
     *
     * @param className
     * @param methodName
     * @param rd
     * @param sd
     */
    public void asynCall(String className, String methodName, BizData rd, BizData sd) {
        Worker w = (Worker) workers.getPoolItem();
        AsynCall ac = new AsynCall(className, methodName, rd, sd);
        w.setAsynCall(ac);
        Thread t = new Thread(w);
        t.start();
    }

    /**
     * make an asyn call with a listener of the result;
     *
     * @param className  the call name;
     * @param methodName the class name;
     * @param rd         the request data;
     * @param sd         the session data;
     * @param l          the listener;
     */
    public void asynCall(String className, String methodName, BizData rd, BizData sd, ACListener l) {
        Worker w = (Worker) workers.getPoolItem();
        AsynCall ac = new AsynCall(className, methodName, rd, sd);
        w.setAsynCall(ac);
        w.addListenter(l);
        Thread t = new Thread(w);
        t.start();
    }
}
