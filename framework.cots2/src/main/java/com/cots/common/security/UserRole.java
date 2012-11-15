/**
 *all rights reserved,@copyright 2003
 */
package com.cots.common.security;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-27
 * Time: 12:54:59
 * Version: 1.0
 */
public class UserRole {
    public final static String TABLE="cotsuserrole";
    public final static String KEYS="userID,roleID";

    private String userID;
    private String roleID;

    public UserRole(){

    }

    public UserRole(String userID,String roleID){
        this.userID = userID;
        this.roleID = roleID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

}
