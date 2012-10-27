/**
 * All rights reserved.
 */
package com.dream.bizsdk.common.asyn;

import com.dream.bizsdk.common.blc.BLResult;

/**
 * An ACEvent is created when an asyn bl call is finished. This event contains
 * the name of the class that privod this bl method, the bl method name, and the bl
 * call result object(BLResult) which contains the bl call return code and the request
 * data and session data.
 * <p/>
 * User: divine
 * Date: 2004-3-2
 * Time: 21:10:06
 */
public class ACEvent {
    /**
     * the class name of the BLC that contains the BL method
     */
    protected String className = null;
    /**
     * the bl method name
     */
    protected String methodName = null;
    /**
     * the bl call result object
     */
    protected BLResult br = null;

    /**
     * construct an ACEvent.
     *
     * @param className
     * @param methodName
     * @param br
     */
    public ACEvent(String className, String methodName, BLResult br) {
        this.className = className;
        this.methodName = methodName;
        this.br = br;

    }

    /**
     * get the class name associated with this event;
     *
     * @return the class name;
     */
    public String getClassName() {
        return this.className;
    }

    /**
     * get the method name assoicated with the event;
     *
     * @return the method name;
     */
    public String getMethodName() {
        return this.methodName;
    }

    /**
     * Get the result of bl call assoicated with this event.
     *
     * @return the BLResult object;
     */
    public BLResult getBLResult() {
        return this.br;
    }
}
