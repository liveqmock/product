/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.blc;

import com.dream.bizsdk.common.databus.BizData;


/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-9-15
 * Time: 11:27:14
 */
public class GroupDispatch extends Dispatch {
    protected String startsWith;
    protected String endsWith;
    protected String contains;

    public GroupDispatch(String url) {
        super(url);
    }

    public GroupDispatch(String url, String on) {
        super(Dispatch.INCLUDE, url, on);
    }

    /**
     * @param startsWith
     */
    public void setStartsWith(String startsWith) {
        this.startsWith = startsWith;
    }

    /**
     * @return
     */
    public String getStartsWith() {
        return this.startsWith;
    }

    /**
     * @param endsWith
     */
    public void setEndsWith(String endsWith) {
        this.endsWith = endsWith;
    }

    /**
     * @return
     */
    public String getEndsWith() {
        return this.endsWith;
    }

    /**
     * @param contains
     */
    public void setContains(String contains) {
        this.contains = contains;
    }

    /**
     * @return
     */
    public String getContains() {
        return this.contains;
    }

    /**
     * @param requestName
     * @param rd
     * @param sd
     * @return
     */
    public boolean needDispatch(String requestName, BizData rd, BizData sd) {
        boolean retVal = false;
        if (startsWith != null && (startsWith.equals("*") || requestName.startsWith(startsWith))) {
            retVal = true;
        } else if (endsWith != null && (endsWith.equals("*") || requestName.endsWith(endsWith))) {
            retVal = true;
        } else if (contains != null && (contains.equals("*") || requestName.indexOf(contains) >= 0)) {
            retVal = true;
        }
        if (retVal) {
            return super.needDispatch(rd, sd);
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
    public boolean needDispatch(String requestName) {
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
