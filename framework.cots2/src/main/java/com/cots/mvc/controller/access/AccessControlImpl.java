/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller.access;

import com.cots.blc.BLContext;
import com.cots.dao.DAO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.sql.*;

import org.apache.log4j.Logger;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-11-16
 * Time: 10:03:56
 */
public final class AccessControlImpl implements AccessControl{

    private DAO dao;

    private HashMap roleRequests = new HashMap(20);
    private HashSet freeRequests = new HashSet(64);
    private HashSet allUserRequests = new HashSet(64);

    private AccessControlImpl(){
        
    }

    /**
     * initialize this object: cache user-action map;
     */
    public synchronized void init(BLContext context){

        Statement stmt = null;
        ResultSet rs = null;
        ArrayList al = null;
        String prevRoleID = null;
        Connection conn = null;

        //clear the cache;
        roleRequests.clear();
        freeRequests.clear();

        this.dao = context.getDAO();

        //begin to initialize this object;
        try{
            //create a java.sql.Driver object. NOTE: can't use DriverManager.getConnection(....);
            conn = this.dao.getConnection();

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
            rs = stmt.executeQuery("select requestID,status from cotsrequest where " +
                    "status='free' or status='alluser'");
            while(rs.next()){
                String requestID = rs.getString(1);
                String status = rs.getString(2);
                if("free".equalsIgnoreCase(status)){
                    freeRequests.add(requestID);
                }else{
                    allUserRequests.add(requestID);
                }
            }
        }catch(SQLException e){
            Logger.getLogger(this.getClass()).error(e);
            e.printStackTrace();
        } finally{
            try{
                if(rs!=null){
                    rs.close();
                }
                if(stmt!=null){
                    stmt.close();
                }
                if(conn != null){
                    conn.close();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param userID
     * @param action
     * @return
     */
    public boolean canDo(String userID, String action) {
        if(allUserRequests.contains(action)){
            return true;
        }

        Login login = (Login) LoginControlImpl.getInstance().getLogin(userID);
        if(login == null){
            return false;
        }
        Principal principal = login.getPrincipal();
        if (!principal.isSubjectsInitialized()) {
            initPrincipalSubjects(principal);
        }
        return principal.containsSubject(action);
    }

    /**
     * check if a Login User Can access an action;
     *
     * @param login the Login to the system;
     * @param action the action this user is trying to access;
     * @return true if the user can access this action,false otherwise;
     */
    public boolean canDo(Login login,String action){
        if(freeRequests.contains(action)){
            return true;
        }

        if(login == null){
            return false;
        }
        Principal principal = login.getPrincipal();
        if (!principal.isSubjectsInitialized()) {
            initPrincipalSubjects(principal);
        }
        return principal.containsSubject(action);
    }

    /**
     *
     * @param action
     * @return
     */
    public boolean isFreeAction(String action) {
        return this.freeRequests.contains(action);
    }

    /**
     *
     * @param action
     * @return
     */
    public boolean isAllUserAction(String action) {
        return this.allUserRequests.contains(action);
    }

    /**
     *
     */
    public void close(){
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
        Connection conn = null;
        try{
            conn = dao.getConnection();

            stmt = conn.createStatement();
            StringBuffer sb = new StringBuffer("select roleID from cotsuserrole where userID='");
            sb.append(id.replaceAll("'","''"));
            sb.append("'");

            rs = stmt.executeQuery(new String(sb));
            while(rs.next()){
                String roleID = rs.getString(1);
                String[] reqs = (String[])roleRequests.get(roleID);
                if(reqs!=null){
                    for(int i=0;i<reqs.length;i++){
                        subjects.add(reqs[i]);
                    }
                }
            }
        }catch(SQLException e){
            Logger.getLogger(this.getClass()).error(e);
            e.printStackTrace();
        }finally{
            try{
                if(rs!=null){
                    rs.close();
                }
                if(stmt!=null){
                    stmt.close();
                }
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

        principal.initSubjects((String[])subjects.toArray(new String[subjects.size()]));
    }

    //the only one instance;
    private static AccessControl ins;

    /**
     * set the implementation class, if this class is not valid, then the default is used;
     *
     * @param clsName
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static void setImplClass(String clsName)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if(clsName != null && clsName.length()>0){
            ins = (AccessControl)Class.forName(clsName).newInstance();
        }
    }

    /**
     * get the only one instance
     *
     * @return
     */
    public static AccessControl getInstance(){
        if(ins==null){
            ins = new AccessControlImpl();
        }
        return ins;
    }
}