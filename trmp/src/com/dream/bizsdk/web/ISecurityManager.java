package com.dream.bizsdk.web;

import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.blc.BLResult;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;

/**
 * Author: divine
 * Date: 2004-6-4
 */
public interface ISecurityManager {

    /**
     * @param sc
     */
    public void init(ServletContext sc);

    /**
     * check if a session is valid;
     *
     * @param session
     * @return
     */
    public BizData checkSession(HttpSession session);

    /**
     * check if a user respresented by a session object has a access to a request;
     *
     * @param session
     * @param request
     * @return
     */
    public int checkAccess(HttpSession session, String request);

    /**
     * @param sessionData
     * @param request
     * @return
     */
    public int checkAccess(BizData sessionData, String request);


    /**
     * login a User to the system;
     *
     * @param req
     * @param userID
     * @param password
     * @return
     */
    public int login(HttpServletRequest req, String userID, String password);


    /**
     * @param userID
     * @param password
     * @param remoteIP
     * @param sessionData
     * @return
     */
    public BLResult login(String userID, String password, String remoteIP, BizData sessionData);
}
