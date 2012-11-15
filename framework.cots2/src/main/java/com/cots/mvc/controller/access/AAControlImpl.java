/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller.access;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.ServletContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;
import javax.sql.DataSource;
import java.util.Properties;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.sql.*;

/**
 * Description:
 *      This Class provides  Login and Access control of users to cots based
 * Applications. Applcation develops will have the source code of this class
 * so that they can customized the AA Behaviour.
 *
 * User: chuguanghua
 * Date: 2004-12-26
 * Time: 14:33:23
 * Version: 1.0
 * @deprecated 
 */
public class AAControlImpl implements AAControl {
    private Properties props;

    private HashMap logins = new HashMap();

    private Connection conn;
    private HashMap roleRequests = new HashMap(20);
    private HashSet freeRequests = new HashSet(64);

    private AAControlImpl() {

    }

    /**
     *
     * init Connection and the cacahe for AA;
     *
     * @param props initial properties,provide a null value will reinit this object's cache;
     */
    public void init(Properties props,ServletContext sc) {
        if(props!=null){
            this.props = props;
        }

        String driver = this.props.getProperty("driver");
        String user = this.props.getProperty("user");
        String password = this.props.getProperty("password");
        String url = this.props.getProperty("url");
        String dsjndi = this.props.getProperty("dsjndi");
        String providerURL = this.props.getProperty("providerURL");
        String initialFactory = this.props.getProperty("initialFactory");

        Statement stmt = null;
        ResultSet rs = null;
        ArrayList al = null;
        String prevRoleID = null;

        //close the previous Connection object;
        try{
            if(conn!=null){
                conn.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        //clear the cache;
        roleRequests.clear();
        freeRequests.clear();

        //begin to initialize this object;
        try{
            //create a java.sql.Driver object. NOTE: can't use DriverManager.getConnection(....);
            Properties p = new Properties();
            if(dsjndi!=null){
                if(providerURL!=null){
                    props.setProperty(Context.PROVIDER_URL, providerURL);
                }
                if(initialFactory!=null){
                    props.setProperty(Context.INITIAL_CONTEXT_FACTORY, initialFactory);
                }
                InitialContext ic = new InitialContext(p);
                DataSource ds = (DataSource)ic.lookup(dsjndi);

                if(user!=null){
                    conn = ds.getConnection(user,password);
                }else{
                    conn = ds.getConnection();
                }
            }else{
                Driver drv = (Driver)Class.forName(driver).newInstance();

                p.setProperty("user",user);
                p.setProperty("password",password);
                conn = drv.connect(url,p);
            }

            /**begin to initialize role-request map*/
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select roleID,requestID from cotsrolerequest order by roleID");
            while(rs.next()){
                String roleID = rs.getString(1);
                String requestID = rs.getString(2);
                if(roleID!=prevRoleID){     //check if a new Role encountered
                    if(al!=null){
                        roleRequests.put(prevRoleID,al.toArray(new String[al.size()]));
                    }
                    al = new ArrayList();
                    prevRoleID = roleID;
                }

                al.add(requestID);
            }
            if(al!=null){
                roleRequests.put(prevRoleID,al.toArray(new String[al.size()]));
            }
            rs.close();

            /**begin to initialize free requests*/
            rs = stmt.executeQuery("select requestID from cotsrequest where status='free'");
            while(rs.next()){
                String requestID = rs.getString(1);
                freeRequests.add(requestID);
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } finally{
            try{
                if(rs!=null){
                    rs.close();
                }
                if(stmt!=null){
                    stmt.close();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     *
     *
     * @param principalID
     * @param password
     * @param ip
     * @throws UserAccessException
     */
    public void login(String principalID, String password, String ip) throws UserAccessException {
        checkPrincipalPassword(principalID,password);
        logins.put(principalID, new Login(new User(principalID), ip));
    }

    /**
     * login a Principal to applications.
     *
     * @param request
     * @param response
     * @param principalID
     * @param passwrd
     * @param ip
     * @throws UserAccessException
     */
    public void login(HttpServletRequest request, HttpServletResponse response, String principalID,
                      String passwrd, String ip) throws UserAccessException {
        checkPrincipalPassword(principalID,passwrd);
        //add the cookie to the response;
        Cookie userIDCookie = new Cookie(AAControl.PRINCIPALID, principalID);
        userIDCookie.setPath("/");
        response.addCookie(userIDCookie);

        logins.put(principalID, new Login(new User(principalID), ip));
    }

    /**
     * login a Principal to applications.
     *
     * @param request
     * @param response
     * @param principalID
     * @param passwrd
     * @throws UserAccessException
     */
    public void login(HttpServletRequest request, HttpServletResponse response,
                      String principalID, String passwrd) throws UserAccessException {
        checkPrincipalPassword(principalID,passwrd);
        //add the cookie to the response;
        Cookie userIDCookie = new Cookie(AAControl.PRINCIPALID, principalID);
        userIDCookie.setPath("/");
        response.addCookie(userIDCookie);

        logins.put(principalID, new Login(new User(principalID)));
    }

    /**
     * logout a Principal from the applications.
     *
     * @param principalID the id of the principal;
     */
    public void logout(String principalID) {
        logins.remove(principalID);
    }

    /**
     * login a Principal to a Application;
     *
     * @param principalID the id of the principal.
     * @param sc          the target Application to login.
     */
    public void loginApp(String principalID, ServletContext sc) {
        Login login = (Login) logins.get(principalID);
        if (login != null) {
            login.loginApp(sc);
        }
    }

    /**
     * login a Pricipal to a Application.
     *
     * @param principalID the id of the Principal.
     * @param sc          the taget Applicaiton;
     */
    public void logoutApp(String principalID, ServletContext sc) {
        Login login = (Login) logins.get(principalID);
        if (login != null) {
            login.logoutApp(sc);
            if (login.getAppsCount() < 1) {
                logins.remove(principalID);
            }
        }
    }

    /**
     * check if there is a login with a specific principal id.
     *
     * @param principalID the taget id.
     * @return true if there is a principal with the id. false otherwise.
     */
    public boolean hasLogined(String principalID) {
        return logins.containsKey(principalID);
    }

    /**
     * check if a principal can talk on a specific subject;
     *
     * @param principalID the id of the principal;
     * @param subject     the id of the taget subject.
     * @return true if the principal can talk on this subject. false otherwise.
     */
    public boolean canTalk(String principalID, String subject) {
        if(freeRequests.contains(subject)){
            return true;
        }

        Login login = (Login) logins.get(principalID);
        Principal principal = login.getPrincipal();
        if (!principal.isSubjectsInitialized()) {
            initPrincipalSubjects(principal);
        }
        return principal.containsSubject(subject);
    }


    /**
     *
     *
     * @param principal
     * @param subject
     * @return
     */
    public boolean canTalk(Principal principal, String subject) {
        if(freeRequests.contains(subject)){
            return true;
        }
        if (!principal.isSubjectsInitialized()) {
            initPrincipalSubjects(principal);
        }
        return principal.containsSubject(subject);
    }

    /**
     * close this object;
     */
    public void close() {
        if(conn!=null){
            try{
                conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     *
     *
     * @param principal
     */
    protected void initPrincipalSubjects(final Principal principal) {

        String id = principal.getId();
        ArrayList subjects = new ArrayList(100);

        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = conn.createStatement();
            StringBuffer sb = new StringBuffer("select roleID from cotsuserrole where userID='");
            sb.append(id.replaceAll("'","''"));
            sb.append("'");

            rs = stmt.executeQuery(new String(sb));
            while(rs.next()){
                String roleID = rs.getString(1);
                String[] reqs = (String[])roleRequests.get(roleID);
                for(int i=0;i<reqs.length;i++){
                    subjects.add(reqs[i]);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(rs!=null){
                    rs.close();
                }
                if(stmt!=null){
                    stmt.close();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

        principal.initSubjects((String[])subjects.toArray(new String[subjects.size()]));
    }

    /**
     * check if a user/password matches;
     *
     * @param principalID
     * @param password
     * @throws UserAccessException
     */
    protected void checkPrincipalPassword(String principalID, String password)
            throws UserAccessException {
        Statement stmt = null;
        ResultSet rs = null;
        try{
            if(password==null){
                password="";
            }
            stmt = conn.createStatement();
            StringBuffer sb = new StringBuffer("select status from cotsuser where userID='");
            sb.append(principalID.replaceAll("'","''"));
            sb.append("' and password='");
            sb.append(password.replaceAll("'","''"));
            sb.append("'");

            rs = stmt.executeQuery(new String(sb));
            if(rs.next()){
                String status = rs.getString(1);
                if(User.DISABLED.equalsIgnoreCase(status)){
                    throw new UserAccessException(AAControl.USER_LOCKED);
                }
            }else{
                throw new UserAccessException(AAControl.USER_PASSWORD_MISMATCH);
            }
        }catch(SQLException e){
            throw new UserAccessException(AAControl.DB_EXCEPTION,e.getMessage());
        }finally{
            try{
                if(rs!=null){
                    rs.close();
                }
                if(stmt!=null){
                    stmt.close();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    private static AAControlImpl instance = new AAControlImpl();

    /**
     * get named instance;
     *
     * @return
     */
    public static AAControl getInstance() {
       return instance;
    }
}