/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller.access;


import com.cots.blc.BLContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-5-17
 * Time: 16:47:06
 * Version: 1.0
 */
public interface LoginListener {
    /**
     *
     *
     * @param context
     */
    void init(BLContext context);

    /**
     * called when a User login;
     *
     * @param userID
     * @param request
     */
    void userLogin(String userID, HttpServletRequest request);

    /**
     * called when a user logout;
     *
     * @param userID
     */
    void userLogout(String userID);
}
