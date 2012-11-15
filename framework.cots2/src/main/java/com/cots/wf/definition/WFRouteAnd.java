/**
 * copyright 2004,primton ltd.All rights reserved.
 */


package com.cots.wf.definition;

/**
 * Module:  WFRouteAnd.java
 * Author:  chuguanghua
 * Created: 2004Äê3ÔÂ31ÈÕ 10:33:53
 * Purpose: Defines the Class WFRouteAnd
 */

public class WFRouteAnd extends WFAbstractRoute {
    public WFRouteAnd() {

    }

    /**
     * @param process
     */
    public WFRouteAnd(WFProcess process) {
        super(process);
    }

    /**
     * return the join mode of this activity;
     *
     * @return "and" as the join mode;
     */
    public String getJoinMode() {
        return "and";
    }
}