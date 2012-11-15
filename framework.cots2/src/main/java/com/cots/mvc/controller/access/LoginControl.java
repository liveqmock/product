/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller.access;

import com.cots.blc.BLContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import java.util.Properties;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-11-16
 * Time: 10:34:12
 */
public interface LoginControl {

    String USERID ="__cotsUserID__";

    String DB_EXCEPTION = "DB_EXCEPTION";
    String USER_PASSWORD_MISMATCH = "USER_PASSWORD_MISMATCH";
    String USER_LOCKED = "USER_LOCKED";


    /**
     *
     * @param context
     */
    void init(BLContext context);

    /**
     * login a user to this application;
     *
     * @param request
     * @param response
     * @param userID the userID;
     * @param passowrd the password of the user;
     * @param ip the ip of the user;
     * @return if the user has logined in previously;
     * @throws UserAccessException if login failed;
     */
    Login login(HttpServletRequest request,HttpServletResponse response,String userID,
               String passowrd,String ip)throws UserAccessException;

    /**
     * login a user to this application;
     *
     * @param userID the userID;
     * @param passowrd the password;
     * @return if the user has logined in previously;
     * @throws UserAccessException if login failed;
     */
    Login login(HttpServletRequest request,HttpServletResponse response,String userID,
               String passowrd)throws UserAccessException;

    /**
     * logout a user;
     *
     * @param userID the userID;
     */
    void logout(String userID);

    /**
     *
     * @param userID
     */
    void loginApp(String userID,ServletContext sc);

    /**
     *
     *
     * @param userID
     */
    void logoutApp(String userID,ServletContext sc);

    /**
     *
     * @param userID
     * @return
     */
    boolean hasLogined(String userID);

    /**
     * get a specified Login object;
     *
     * @param userID
     * @return
     */
    Login getLogin(String userID);


    /**
     * get all the logins;
     * 
     * @return
     */
    Login[] getLogins();

    /**
     * close this control object;
     */
    void close();
}
