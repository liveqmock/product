/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 * Listener is object whose methods will be called by cots framework
 * when an action was begun,done or failed.
 * An action wad done measn the action was completed successfully, no
 * exception was thrown. On the contrary, an action was failed means uncaught
 * exception was throw during the executing of the action.
 * <p/>
 * User: chuguanghua
 * Date: 2005-5-22
 * Time: 12:18:09
 * Version: 1.0
 */
public interface ActionListener {
    /**
     * called by cots framework when an action will begin.
     *
     * @param action  the displayName of the action.
     * @param request the HttpServletRequest object.
     */
    public void actionBegin(String action, HttpServletRequest request);

    /**
     * called by the cots framework when an action was successfully completed;
     *
     * @param action
     * @param request
     */
    public void actionDone(String action, HttpServletRequest request);

    /**
     * called by the framework when uncaught exception was thrown
     *
     * @param action
     * @param request
     */
    public void actionFailed(String action, HttpServletRequest request,Throwable t);

}
