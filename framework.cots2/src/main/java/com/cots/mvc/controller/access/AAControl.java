/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller.access;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import java.util.Properties;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-26
 * Time: 14:27:18
 * Version: 1.0
 * @deprecated 
 */
public interface AAControl {
    public static final String PRINCIPALID ="__cotsPrincipalID__";

    public static final String DB_EXCEPTION = "DB_EXCEPTION";
    public static final String USER_PASSWORD_MISMATCH = "USER_PASSWORD_MISMATCH";
    public static final String USER_LOCKED = "USER_LOCKED";

    /**
     * initialize current object;
     *
     * @param props
     */
    void init(Properties props,ServletContext sc);

    /**
     * login a principal;
     *
     * @param principalID
     * @param password
     * @param ip
     * @throws UserAccessException
     */
    void login(String principalID,String password,String ip) throws UserAccessException;

    /**
     * login a user to this application;
     *
     * @param request
     * @param response
     * @param principalID the principalID;
     * @param passowrd the password of the user;
     * @param ip the ip of the user;
     * @throws UserAccessException if login failed;
     */
    void login(HttpServletRequest request,HttpServletResponse response,String principalID,
               String passowrd,String ip)throws UserAccessException;

    /**
     * login a user to this application;
     *
     * @param principalID the principalID;
     * @param passowrd the password;
     * @throws UserAccessException if login failed;
     */
    void login(HttpServletRequest request,HttpServletResponse response,String principalID,
               String passowrd)throws UserAccessException;

    /**
     * logout a principal from the applicationS;
     *
     * @param principalID the id of the principal;
     */
    void logout(String principalID);

    /**
     *
     * login a principal to an application.
     *
     * @param principalID
     */
    void loginApp(String principalID,ServletContext sc);

    /**
     * logout a principal from an application.
     *
     * @param principalID
     */
    void logoutApp(String principalID,ServletContext sc);

    /**
     * check if a principal id has logined.
     *
     * @param principalID
     * @return
     */
    boolean hasLogined(String principalID);

    /**
     * check if a principal can talk on subject;
     *
     * @param principalID the userID;
     * @param subject the target subject the principal is trying to do;
     */
    boolean canTalk(String principalID,String subject);


    /**
     * check if a Principal can talk on a specified subject;
     *
     * @param principal the Principal object;
     * @param subject the action that the user is trying to acess;
     * @return true if the Principal can talk on this subject,false otherwise;
     */
    boolean canTalk(Principal principal,String subject);

    /**
     * close this object;
     *
     */
    void close();
}