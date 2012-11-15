/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Properties;
import java.util.HashMap;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-5-12
 * Time: 12:53:30
 * Version: 1.0
 */
public interface ActionFilter {

    /**
     * initialize this filter;
     *
     * @param prop initialization objects.
     */
    void init(Properties prop);

    /**
     * do filter on a Action request(a http request for a cots action).
     *
     * @param request the HttpServletRequest object.
     * @param action the displayName of the requested action.
     * @return true if the action should be continued.
     */
    boolean filter(HttpServletRequest request,String action);
}
