/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-5-22
 * Time: 12:30:23
 * Version: 1.0
 */
public class BaseActionListener implements ActionListener{

    public void actionBegin(String action,  HttpServletRequest request) {

    }

    public void actionDone(String action, HttpServletRequest request) {

    }

    public void actionFailed(String action, HttpServletRequest request,Throwable t) {

    }
}
