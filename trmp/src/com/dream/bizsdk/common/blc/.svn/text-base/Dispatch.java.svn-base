/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.blc;

import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.exp.Expression;

import java.util.Hashtable;
import java.io.Serializable;

/**
 * Description:
 * A dispatch represents a <dispatch /> element in the requests.xml config file.
 * User: chuguanghua
 * Date: 2003-12-27
 * Time: 14:45:20
 */
public class Dispatch implements Serializable {
    /**
     * forward type dispatch
     */
    public static final int FORWARD = 0;
    /**
     * redirect type dispatch
     */
    public static final int REDIRECT = 1;

    public static final int INCLUDE = 2;

    /**
     * type of this dispatch
     */
    protected int type = 0;
    /**
     * url of this dispatch
     */
    protected String url = null;
    /**
     * the condition of this dispatch to be run
     */
    protected String on = null;
    /**
     * whether this dispatch is default or not
     */
    protected boolean isDefault = false;

    /**
     * @param url
     */
    public Dispatch(String url) {
        this.url = url;
    }

    /**
     * @param type
     * @param url
     */
    public Dispatch(int type, String url) {
        this.type = type;
        this.url = url;
    }

    /**
     * @param type
     * @param url
     * @param isDefault
     */
    public Dispatch(int type, String url, boolean isDefault) {
        this.type = type;
        this.url = url;
        this.isDefault = isDefault;
    }

    /**
     * @param type
     * @param url
     * @param isDefault
     */
    public Dispatch(String url, String type, String on, String isDefault) {
        if (type != null) {
            if (type.equalsIgnoreCase("include")) {
                this.type = Dispatch.INCLUDE;
            } else if (type.equalsIgnoreCase("redirect")) {
                this.type = Dispatch.REDIRECT;
            } else {
                this.type = Dispatch.FORWARD;
            }
        }
        this.url = url;
        if (isDefault != null && "true".equalsIgnoreCase(isDefault)) {
            this.isDefault = true;
        } else {
            this.isDefault = false;
        }
        this.on = on;
    }


    /**
     * @param type
     * @param url
     * @param on
     */
    public Dispatch(int type, String url, String on) {
        this.type = type;
        this.url = url;
        this.on = on;
    }

    /**
     * @param type
     * @param url
     * @param isDefault
     * @param on
     */
    public Dispatch(int type, String url, boolean isDefault, String on) {
        this.type = type;
        this.url = url;
        this.isDefault = isDefault;
        this.on = on;
    }

    /**
     * @return
     */
    public int getType() {
        return this.type;
    }

    /**
     * @return
     */
    public String getURL() {
        return this.url;
    }

    /**
     * @return
     */
    public boolean isDefault() {
        return isDefault;
    }

    /**
     * @param def
     */
    public void setDefault(boolean def) {
        isDefault = def;
    }

    /**
     * @return
     */
    public String getOn() {
        return this.on;
    }

    /**
     * @param rd
     * @param sd
     * @return
     */
    public boolean needDispatch(BizData rd, BizData sd) {
        Hashtable bds = new Hashtable();
        if (rd != null) {
            bds.put("rd", rd);
            bds.put("default", rd);
        }
        if (sd != null) {
            bds.put("sd", sd);
        }

        if (on != null) {
            if (Expression.valueOf(on, bds)) {
                return true;
            } else {
                return false;
            }
        } else {
/*            if(type==Dispatch.INCLUDE){
                return true;
            }else{
                return false;
            }
*/
            return true;
        }
    }
}
