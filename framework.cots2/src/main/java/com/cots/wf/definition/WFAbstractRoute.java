/**
 * copyright 2004,primton ltd.All rights reserved.
 */

package com.cots.wf.definition;

/**
 * Module:  WFAbstractRoute.java
 * Description:
 * <p/>
 * <p/>
 * Author:  chuguanghua
 * Created: 2004Äê3ÔÂ31ÈÕ 10:17:25
 * Purpose: Defines the Class WFAbstractRoute
 */

public abstract class WFAbstractRoute extends WFAbstractActivity {
    public WFAbstractRoute() {
    }

    public WFAbstractRoute(WFProcess process) {
        super(process);
    }

    public int getWorkitemCount() {
        return 1;
    }

    /**
     * default split mode to "all", means that all the outcoming transitions
     * should be created;
     *
     * @return the joinMode String;
     */
    public String getSplitMode() {
        return "all";
    }

    /**
     * Read only joinmode attribute;
     *
     * @param joinMode
     */
    void setJoinMode(String joinMode) {
    }

    /**
     * readonly splitmode attribute;
     *
     * @param splitMode
     */
    void setSplitMode(String splitMode) {
    }

    /**
     * readonly workitem count;
     */
    void setWorkitemCount(int workitemCount) {

    }
}