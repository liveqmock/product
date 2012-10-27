/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.blc;

import com.dream.bizsdk.common.databus.BizData;

import java.util.Hashtable;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-9-2
 * Time: 11:14:46
 */
public class GroupModel extends Model {
    protected String startsWith;
    protected String endsWith;
    protected String contains;

    /**
     * @param name
     */
    public GroupModel(String name) {
        super(name);
    }

    /**
     * @param name
     * @param on
     */
    public GroupModel(String name, String on) {
        super(name, on);
    }

    /**
     * @param name
     * @param on
     * @param isAsyn
     */
    public GroupModel(String name, String on, boolean isAsyn) {
        super(name, on, isAsyn);
    }

    /**
     * @param startsWith
     */
    public void setStartsWith(String startsWith) {
        this.startsWith = startsWith;
    }

    /**
     * @param endsWith
     */
    public void setEndsWith(String endsWith) {
        this.endsWith = endsWith;
    }

    /**
     * @param contains
     */
    public void setContains(String contains) {
        this.contains = contains;
    }

    /**
     * @param requestName
     * @param rd
     * @param sd
     * @return
     */
    public boolean needRun(String requestName, BizData rd, BizData sd) {
        boolean retVal = false;
        if (startsWith != null && (startsWith.equals("*") || requestName.startsWith(startsWith))) {
            retVal = true;
        } else if (endsWith != null && (endsWith.equals("*") || requestName.endsWith(endsWith))) {
            retVal = true;
        } else if (contains != null && (contains.equals("*") || requestName.indexOf(contains) >= 0)) {
            retVal = true;
        }
        if (retVal) {
            return super.needRun(rd, sd);
        } else {
            return false;
        }
    }

    /**
     * @param requestName
     * @param bds
     * @return
     */
    public boolean needRun(String requestName, Hashtable bds) {
        boolean retVal = true;
        if (startsWith != null && !startsWith.equals("*") && !requestName.startsWith(startsWith)) {
            retVal = false;
        } else if (endsWith != null && !endsWith.equals("*") && !requestName.endsWith(endsWith)) {
            retVal = false;
        } else if (contains != null && !contains.equals("*") && requestName.indexOf(contains) < 0) {
            retVal = false;
        }
        if (retVal) {
            return super.needRun(bds);
        } else {
            return false;
        }
    }

    /**
     * check if this GroupModel need run for a specific request;
     *
     * @param requestName the target request name;
     * @return true if this request need run for this request, false otherwise;
     */
    public boolean needRun(String requestName) {
        boolean retVal = true;
        if (startsWith != null && !startsWith.equals("*") && !requestName.startsWith(startsWith)) {
            retVal = false;
        } else if (endsWith != null && !endsWith.equals("*") && !requestName.endsWith(endsWith)) {
            retVal = false;
        } else if (contains != null && !contains.equals("*") && requestName.indexOf(contains) < 0) {
            retVal = false;
        }
        return retVal;
    }
}