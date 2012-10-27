/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.blc;

import com.dream.bizsdk.common.exp.Expression;

import java.util.Hashtable;
import java.io.Serializable;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-3-2
 * Time: 11:17:10
 */
public class Validation implements Serializable {
    private String cond = "";
    private String msg = "";

    public Validation() {

    }

    public Validation(String cond, String msg) {
        this.cond = cond;
        this.msg = msg;
    }

    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void exec(Hashtable dds) throws ValidateException {
        if (Expression.valueOf(cond, dds) == false) {
            throw new ValidateException(msg);
        }
    }
}
