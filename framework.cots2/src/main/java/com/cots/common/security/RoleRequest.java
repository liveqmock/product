/**
 *all rights reserved,@copyright 2003
 */
package com.cots.common.security;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-27
 * Time: 12:56:50
 * Version: 1.0
 */
public class RoleRequest {
    public final static String TABLE="cotsrolerequest";

    public final static String KEYS="roleID,requestID";

    private String roleID;
    private String requestID;

    public RoleRequest() {
    }

    public RoleRequest(String roleID, String requestID) {
        this.roleID = roleID;
        this.requestID = requestID;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }
}
