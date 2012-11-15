/**
 *all rights reserved,@copyright 2003
 */
package com.cots.security;

import java.util.List;
import java.util.HashMap;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-4-10
 * Time: 15:45:17
 * Version: 1.0
 */
public class SecurityObject {
    protected String id;

    protected HashMap allowedUsers = new HashMap();

    protected HashMap allowedRoles = new HashMap();

    public SecurityObject() {
    }

    public SecurityObject(String id) {
        this.id = id;
    }

    public void addUser(String user,String access){
        allowedUsers.put(user,access);
    }

    public void addRole(String role,String access){
        allowedRoles.put(role,access);
    }

    public boolean isUserAllowed(String user,String access){
        return false;
    }

    public boolean isRoleAllowed(String role,String access){
        return false;
    }

    public boolean isRoleAllowed(List roles,String access){
        return false;
    }

}
