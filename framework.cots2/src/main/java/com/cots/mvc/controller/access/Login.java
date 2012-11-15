/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller.access;

import javax.servlet.ServletContext;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-11-16
 * Time: 11:41:23
 * Version: 1.0
 */
public class Login {
    Principal   principal;
    String      ip;
    Date        loginTime;
    HashSet     apps = new HashSet();

    public Login(Principal principal){
        this.principal = principal;
        loginTime = new Date();
    }

    public Login(Principal principal,String ip){
        this.principal = principal;
        this.ip = ip;
        this.loginTime = new Date();
    }


    /**
     * get the userID of this Login;
     *
     * @return userID;
     */
    public String getPrincipalID() {
        return principal.getId();
    }

    /**
     *
     * @return
     */
    public Principal getPrincipal(){
        return principal;
    }

    /**
     *
     *
     * @return
     */
    public String getIp() {
        return ip;
    }

    /**
     * get the login time;
     *
     * @return the login time;
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * login this user to a ServletContext (Web Application);
     *
     * @param sc the ServletContext represents a web Application;
     */
    public void loginApp(ServletContext sc){
        apps.add(sc);
    }

    /**
     * logout the current user from a ServletContext object;
     *
     * @param sc
     */
    public void logoutApp(ServletContext sc){
        apps.remove(sc);
    }

    /**
     * get the count of ServeltContext object that this user has currently loged in;
     *
     * @return count of ServletContext;
     */
    public int getAppsCount(){
        return apps.size();        
    }

    /**
     * get the set of ServletContext that the user has currrently logged in.
     *
     * @return the set log logged in ServletContext;
     */
    public Set getLoginedApps(){
        return apps;
    }
}
