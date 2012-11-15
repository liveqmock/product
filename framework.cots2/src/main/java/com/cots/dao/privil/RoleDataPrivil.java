/**
 *all rights reserved,@copyright 2003
 */
package com.cots.dao.privil;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-17
 * Time: 16:31:47
 * Version: 1.0
 */
public class RoleDataPrivil {

    /**
     * check if a role can access a table;
     *
     * @param roleID
     * @param table
     * @return
     */
    public boolean canAccess(String roleID, String table) {
        return false;
    }

    /**
     * get a role's privil string on a table;
     *
     * @param roleID
     * @param table
     * @return
     */
    public String getUpdatePrivil(String roleID, String table) {
        return null;
    }

    public String getDeletePrivil(String roleID, String table) {
        return null;
    }

    public String getSelectPrivil(String roleID, String table) {
        return null;
    }
}
