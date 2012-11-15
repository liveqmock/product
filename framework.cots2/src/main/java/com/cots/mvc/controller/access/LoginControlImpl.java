/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller.access;

import com.cots.util.IPString;
import com.cots.blc.BLContext;
import com.cots.dao.DAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.sql.*;

import org.apache.log4j.Logger;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-11-16
 * Time: 10:35:42
 */
public final class LoginControlImpl implements LoginControl{

    private HashMap logins = new HashMap();
    private LoginListener loginListener;
    private DAO dao;

    private LoginControlImpl(){

    }

    /**
     * initialize this LoginControl object;
     *
     * @param context
     */
    public synchronized void init(BLContext context) {

        this.dao = context.getDAO();

        String loginListener = context.getLoginListener();

        if(loginListener != null && loginListener.length()>0){
            try{
                Object o = Class.forName(loginListener).newInstance();
                if(o instanceof LoginListener){
                    this.loginListener = (LoginListener)o;
                }else{
                    Logger.getLogger(LoginControl.class).error("Login Listener class \""+loginListener
                            +"\" must implements interface \"com.cots.mvc.controller.access.LoginListener\".");
                }
            } catch (ClassNotFoundException e){
                e.printStackTrace();
                Logger.getLogger(LoginControl.class).error("Login Listener class \""+loginListener+"\" not found");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Logger.getLogger(LoginControl.class).error("failed to access Login Listener class \""
                        +loginListener+"\"",e);
            } catch (InstantiationException e) {
                e.printStackTrace();
                Logger.getLogger(LoginControl.class).error("failed to create instance of Login Listener class \""
                        +loginListener+"\"",e);
            }
        }
    }

    /**
     *
     *
     * @param userID
     * @param password
     * @param ip
     * @throws UserAccessException
     */
    public Login login(HttpServletRequest request,HttpServletResponse response,String userID,
                      String password, String ip) throws UserAccessException {
        return login(request,response,userID,password);
    }

    /**
     *
     *
     * @param userID
     * @param password
     * @throws UserAccessException
     */
    public Login login(HttpServletRequest request,HttpServletResponse response,String userID,
                      String password) throws UserAccessException {
        loginPrincipal(userID,password,request.getRemoteHost());
        //add the cookie to the response;
        Cookie userIDCookie = new Cookie(LoginControl.USERID, userID);
        userIDCookie.setPath("/");
        response.addCookie(userIDCookie);

        Login login = new Login(new User(userID));
        login.ip = request.getRemoteHost();

        if(loginListener != null){
            loginListener.userLogin(userID,request);
        }

        return (Login)logins.put(userID, login);
    }

    /**
     *
     * @param userID
     */
    public void logout(String userID) {
        logins.remove(userID);
        if(loginListener != null){
            loginListener.userLogout(userID);
        }
    }


    /**
     * logout from a application;
     *
     * @param userID
     */
    public void logoutApp(String userID,ServletContext sc) {
        Login login = (Login) logins.get(userID);
        if (login != null) {
            login.logoutApp(sc);
            if (login.getAppsCount() < 1) {
                logins.remove(userID);
            }
        }
    }

    /**
     * login to a application;
     *
     * @param userID
     */
    public void loginApp(String userID,ServletContext sc) {
        Login login = (Login) logins.get(userID);
        if (login != null) {
            login.loginApp(sc);
        }
    }

    /**
     * check if a user has logined;
     *
     * @param userID the userID;
     * @return true if the user has logined in the application; false otherwise;
     */
    public boolean hasLogined(String userID) {
        return logins.containsKey(userID);
    }

    /**
     * get the specified Login objects;
     *
     * @param userID
     * @return
     */
    public Login getLogin(String userID){
        return (Login)logins.get(userID);
    }

    /**
     * get all the Logins to the applications; 
     *
     * @return
     */
    public Login[] getLogins(){
        return (Login[])logins.values().toArray(new Login[logins.size()]);
    }

    /**
     * shutdown this Contorl object;
     */
    public void close(){
    }

    /**
     * check if a user/password matches;
     *
     * @param principalID
     * @param password
     * @throws UserAccessException
     */
    protected void loginPrincipal(String principalID, String password,String ip)
            throws UserAccessException {
        Connection conn = null;
        try{
            conn = dao.getConnection();
        }catch(SQLException e){
            throw new UserAccessException(DB_EXCEPTION,e.getMessage());
        }

        Statement stmt = null;
        ResultSet rs = null;
        try{
            if(password==null){
                password="";
            }
            stmt = conn.createStatement();
            StringBuffer sb = new StringBuffer("select status,startIP,endIP from cotsuser where userID='");
            sb.append(principalID.replaceAll("'","''"));
            sb.append("' and password='");
            sb.append(password.replaceAll("'","''"));
            sb.append("'");

            rs = stmt.executeQuery(new String(sb));
            if(rs.next()){
                String status = rs.getString(1);
                if(User.DISABLED.equalsIgnoreCase(status)){
                    throw new UserAccessException(LoginControl.USER_LOCKED);
                }

                String startIP =rs.getString(2);
                String endIP = rs.getString(3);

                //check principal's ip;
                if(!IPString.within(ip,startIP,endIP)){
                    throw new UserAccessException("you can't login from the ip: "+ip);

                }
            }else{
                throw new UserAccessException(LoginControl.USER_PASSWORD_MISMATCH);
            }
        }catch(SQLException e){
            throw new UserAccessException(LoginControl.DB_EXCEPTION,e.getMessage());
        }finally{
            try{
                if(rs!=null){
                    rs.close();
                }
                if(stmt!=null){
                    stmt.close();
                }
                conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }


    //the only one instance;
    private static LoginControl ins;

    /**
     * set the actually impl class of the interface LoginControl
     *
     * @param clsName the impl class displayName;
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static void setImplClass(String clsName)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if(clsName != null && clsName.length()>0){
            ins = (LoginControl)Class.forName(clsName).newInstance();
        }
    }
    /**
     * get the only one instance
     *
     * @return
     */
    public static LoginControl getInstance(){
        if(ins==null){
            ins = new LoginControlImpl();
        }
        return ins;
    }
}