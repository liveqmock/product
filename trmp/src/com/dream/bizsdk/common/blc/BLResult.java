/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.blc;

import com.dream.bizsdk.common.databus.BizData;

import java.io.Serializable;

/**
 * Description:
 * BLResult contains the data returned by a BL method call.
 * A BLResult object contains three memebers:
 * the BLMethod call return code;
 * the request BizData object;
 * the session BizData object;
 * <p/>
 * User: chuguanghua
 * Date: 2003-12-25
 * Time: 14:05:48
 */
public class BLResult implements Serializable {
    /**
     * the BL Method call return value
     */
    public int retCode = 0;
    /**
     * the request BizData object after the BL method call
     */
    public BizData rd = null;
    /**
     * the session BizData object after the BL method call
     */
    public BizData sd = null;

    /**
     * contruct a default object;
     */
    public BLResult() {
    }

    /**
     * Contruct a BLResult object only with a reurn code;
     *
     * @param code the return code of the target BL method;
     */
    public BLResult(int code) {
        retCode = code;
        retCode = code;
    }

    /**
     * contruct a BLResult Object with a return code and request data object;
     *
     * @param code the return code of the BL method call.
     * @param data the request data object;
     */
    public BLResult(int code, BizData data) {
        rd = data;
        retCode = code;
    }

    /**
     * construct a BLResult object with the return code, request data object
     * and the session data object.
     *
     * @param code the return code
     * @param rd   the request data object;
     * @param sd   the session data object;
     */
    public BLResult(int code, BizData rd, BizData sd) {
        retCode = code;
        this.rd = rd;
        this.sd = sd;
    }
}
