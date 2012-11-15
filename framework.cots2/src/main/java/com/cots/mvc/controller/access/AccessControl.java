/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller.access;

import com.cots.blc.BLContext;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-11-16
 * Time: 9:18:10
 */
public interface AccessControl {

    /**
     * initialize the access control data that is shared by different users;
     *
     * @param context
     */
    void init(BLContext context);

    /**
     * check if a user can do an action;
     *
     * @param userID the userID;
     * @param action the target action the user is trying to do;
     */
    boolean canDo(String userID,String action);

    /**
     * check if a Login can access a specified action;
     *
     * @param user the Login object;
     * @param action the action that the user is trying to acess;
     * @return true if the user can access this action,false otherwise;
     */
    boolean canDo(Login user,String action);

    /**
     * every one include those have not logined in can access a action.
     *
     * @param action
     * @return true if everyone including those have not logined can access the action.
     */
    boolean isFreeAction(String action);

    /**
     * every logined user can access the action.
     *
     * @param action the displayName of the action;
     * @return true if all logined user can access this action
     */
    boolean isAllUserAction(String action);

    /**
     * close this object;
     */
    void close();
}