/**
 *all rights reserved,@copyright 2003
 */
package com.cots.common.security;

import com.cots.blc.AbstractBLC;
import com.cots.blc.BLContext;
import com.cots.blc.BLException;
import com.cots.mvc.controller.access.User;
import com.cots.mvc.controller.access.Role;
import com.cots.mvc.controller.access.Request;
import com.cots.dao.Page;
import com.cots.dao.DAO;

import java.util.*;
import java.sql.*;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-25
 * Time: 15:21:53
 * Version: 1.0
 */
public final class Manager extends AbstractBLC{

    protected DAO dao;

    public void init(BLContext context) {
        super.init(context);
        dao = context.getDAO();
    }

    /**********************************
     * Operations on User object;
     *
     *
     **********************************/

    /**
     * create a new user and associate the specified roles with the new user.
     *
     * @param user the new User
     * @param roleIDs the roles that will be associated with the new User;
     */
    public void createUser(User user,String[] roleIDs) throws SQLException{
        UserRole[] urs = new UserRole[roleIDs.length];
        for(int i =0;i<roleIDs.length;i++){
            urs[i] = new UserRole(user.getId(),roleIDs[i]);
        }
        dao.insert(user);
        dao.insert(urs);
    }

    /**
     * delete one or more users;
     *
     * @param userIDs the ID(s) of the users to be deleted;
     */
    public int deleteUsers(String[] userIDs) throws SQLException{
        String[][] values = new String[userIDs.length][1];
        for(int i=0;i<userIDs.length;i++){
            values[i][0] = userIDs[i];
        }
        return dao.delete(UserRole.TABLE,new String[]{"userID"},values);
    }

    /**
     * update a user information;
     *
     * @param user the new User Information;
     * @param roleIDs the roles that should be associated with this user;
     */
    public void updateUser(User user,String[] roleIDs) throws SQLException{
        //delete the previous recordsd;
        dao.delete(UserRole.TABLE,new String[]{"userID"},new String[]{user.getId()});
        dao.delete(User.TABLE,new String[]{"userID"},new String[]{user.getId()});

        //insert new recordsd;
        createUser(user,roleIDs);
    }

    /**
     * lock one or more users; this will update the users' status to be
     * LOCKED;
     *
     * @param userIDs
     */
    public void lockUsers(String[] userIDs)  throws SQLException{
        Connection conn = dao.threadGetConnection();
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement("update "+User.TABLE+" set status='disabled' where userID=?");
            for(int i=0;i<userIDs.length;i++){
                stmt.setString(1,userIDs[i]);
                stmt.executeUpdate();
            }
        }finally{
            if(stmt!=null){
                stmt.close();
            }
            dao.threadCloseConnection(conn);
        }
    }

    /**
     * update one more users' status to specified new status;
     *
     * @param userIDs the Users' ID
     * @param newStatus the new status to update;
     * @throws SQLException
     */
    public void updateUsersStatus(String[] userIDs,String newStatus)  throws SQLException{
        Connection conn = dao.threadGetConnection();
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement("update "+User.TABLE+" set status=? where userID=?");
            stmt.setString(1,newStatus);
            for(int i=0;i<userIDs.length;i++){
                stmt.setString(2,userIDs[i]);
                stmt.executeUpdate();
            }
        }finally{
            if(stmt!=null){
                stmt.close();
            }
            dao.threadCloseConnection(conn);
        }
    }

    /**
     * unlock one or more users that are locked previously;
     *
     * @param userIDs
     */
    public void unlockUsers(String[] userIDs) throws SQLException{
        Connection conn = dao.threadGetConnection();
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement("update "+User.TABLE+" set status='enabled' where userID=?");
            for(int i=0;i<userIDs.length;i++){
                stmt.setString(1,userIDs[i]);
                stmt.executeUpdate();
            }
        }finally{
            if(stmt!=null){
                stmt.close();
            }
            dao.threadCloseConnection(conn);
        }
    }

    /**
     *
     * @param where
     * @param page
     * @return
     */
    public List queryUsers(String where,Page page){
        return null;
    }

    public List queryUsers(String where,int page){
        return null;
    }

    public List queryUsers(User user,int page){
        return null;
    }

    /**
     * retunr all the roles of a user;
     *
     * @param userID the id of the user;
     * @return the user's role connection;
     * @throws SQLException
     */
    public Set queryUserRoles(String userID) throws SQLException{
        Connection conn = dao.threadGetConnection();
        Statement stmt =null;
        try{
            Set userRoles = new HashSet();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select roleID from cotsuserrole where userID = '"
                    +userID.replaceAll("'","''")+"'");
            while(rs.next()){
                String roleID = rs.getString(1);
                userRoles.add(roleID);
            }
            return userRoles;
        }finally{
            if(stmt!=null){
                stmt.close();
            }
            dao.threadCloseConnection(conn);
        }
    }

    /**
     * change a user's password;
     *
     * @param userID
     * @param oldPassword
     * @param newPass
     * @param confirmPass
     */
    public void changeUserPassword(String userID,String oldPassword,String newPass,String confirmPass)
            throws SQLException{
        if(newPass!=null){
            if(!newPass.equals(confirmPass)){
                throw new BLException("new/confirm password not match");
            }
        }else if(confirmPass != null){
            throw new BLException("new/confirm password not match");
        }
        if(newPass==null){
            newPass="";
        }
        Connection conn = dao.threadGetConnection();
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement("update "+User.TABLE+" set password=? where userID=?");
            stmt.setString(1,newPass);
            stmt.setString(2,userID);
            stmt.executeUpdate();
        }finally{
            if(stmt!=null){
                stmt.close();
            }
            dao.threadCloseConnection(conn);
        }
    }

    /**********************************
     * Operations on Role object;
     *
     *
     **********************************/

    /**
     * create a new Role object;
     *
     * @param role the new Role Object;
     * @throws SQLException
     */
    public void createRole(Role role) throws SQLException{
        dao.insert(role);
    }

    /**
     * 
     * @param page
     * @return
     * @throws SQLException
     */
    public List queryRoles(Page page) throws SQLException{
        return dao.query(Role.class,null,page);
    }

    /**
     * delete one or more roles;
     * @param roleIDs the array of id of roles to be deleted;
     * @throws SQLException
     */
    public void deleteRoles(String[] roleIDs) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        try{
            conn = dao.threadGetConnection();
            stmt1 = conn.prepareStatement("delete from "+Role.TABLE+" where userID=?");
            stmt2 = conn.prepareStatement("delete from "+RoleRequest.TABLE+" where userID=?");

            for(int i =0;i<roleIDs.length;i++){
                stmt2.setString(1,roleIDs[i]);
                stmt2.executeUpdate();

                stmt1.setString(1,roleIDs[i]);
                stmt1.executeUpdate();
            }
        }finally{
            try{
                if(stmt1!=null){
                    stmt1.close();
                }
                if(stmt2 != null){
                    stmt2.close();
                }
                dao.threadCloseConnection(conn);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

    }

    /**
     * query users that are associated with a role;
     *
     * @param roleID the target role id;
     * @return the list of matching users;
     * @throws SQLException
     */
    public List queryUsersOfRole(String roleID) throws SQLException{
        return dao.query(User.class,"roleID ='"+roleID.replaceAll("'","''")+"'");
    }

    /**
     * add one or more request to a role;
     *
     * @param roleID
     * @param requestIDs
     */
    public void addRequestsToRole(String roleID,String[] requestIDs){
        try{
            RoleRequest[] rr = new RoleRequest[requestIDs.length];
            for(int i =0;i<requestIDs.length;i++){
                rr[i] = new RoleRequest(roleID,requestIDs[i]);
            }
            dao.insert(rr);
        }catch(SQLException e){
            throw new BLException("can't insert the target requests",e);
        }

    }

    public List queryRequestsNotAddedToRole(String roleID,String orderBy){
        return null;
    }


    /**********************************
     * Operations on Request object;
     *
     *
     **********************************/

    /**
     * insert a new request;
     *
     * @param request the request Object;
     * @throws SQLException
     */
    public void createRequest(Request request) throws SQLException{
        dao.insert(request);
    }

    /**
     * update a request;
     *
     * @param request
     * @throws SQLException
     */
    public void updateRequest(Request request) throws SQLException{
        dao.update(request);
    }

    /**
     * set one or more requests free;
     *
     * @param requestsID the array of request ids;
     * @throws SQLException
     */
    public void makeRequestsFree(String[] requestsID) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            conn = dao.threadGetConnection();
            stmt = conn.prepareStatement("update "+Request.TABLE+" set status='free' where requestID=?");

            for(int i =0;i<requestsID.length;i++){
                stmt.setString(1,requestsID[i]);
                stmt.executeUpdate();
            }
        }finally{
            if(stmt!=null){
                stmt.close();
            }
            dao.threadCloseConnection(conn);
        }
    }

    /**
     * delete one or more requests;
     *
     * @param requestIDs
     */
    public void deleteRequests(String[] requestIDs){
        try{
            Request[] rr = new Request[requestIDs.length];
            for(int i =0;i<requestIDs.length;i++){
                rr[i] = new Request(requestIDs[i]);
            }
            dao.delete(rr);
        }catch(SQLException e){
            throw new BLException("can't delete one or more requests",e);
        }
    }

    /**
     * query request with a page number;
     *
     * @param where
     * @param orderBy
     * @param page
     * @return
     */
    public List queryRequests(String where,String orderBy,Page page){
        return null;
    }
}