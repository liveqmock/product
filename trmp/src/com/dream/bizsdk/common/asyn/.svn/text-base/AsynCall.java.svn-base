/**
 * All rights reserved.
 */
package com.dream.bizsdk.common.asyn;

import com.dream.bizsdk.common.databus.BizData;

/**
 * An wrapper of an asyn BL method call;
 * User: divine
 * Date: 2004-3-2
 * Time: 21:21:31
 */
public final class AsynCall {
    /**
     * the class name of this call
     */
    private String className = null;
    /**
     * the bl method name of this call
     */
    private String methodName = null;
    /**
     * the request data of this call
     */
    private BizData rd = null;
    /**
     * the session data of this call
     */
    private BizData sd = null;

    /**
     * @param className  the class name of this call;
     * @param methodName the method name of this call;
     * @param rd         the request data of this call;
     * @param sd         the session data of this call;
     */
    public AsynCall(String className, String methodName, BizData rd, BizData sd) {
        this.className = className;
        this.methodName = methodName;
        this.rd = rd;
        this.sd = sd;
    }

    /**
     * get the class name of this call;
     *
     * @return the name of the claa
     */
    public String getClassName() {
        return this.className;
    }

    /**
     * get the Method Name;
     *
     * @return the method name;
     */
    public String getMethodName() {
        return this.methodName;
    }

    /**
     * get the request DataItem;
     *
     * @return the request DataItem;
     */
    public BizData getReqData() {
        return this.rd;
    }

    /**
     * get the Session DataItem of this call;
     *
     * @return the Session DataItem;
     */
    public BizData getSessionData() {
        return this.sd;
    }
}
