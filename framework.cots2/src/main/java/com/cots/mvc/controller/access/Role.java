/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller.access;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-26
 * Time: 13:30:27
 * Version: 1.0
 */
public class Role extends Principal{
    public final static String TABLE="cotsrole";
    public final static String KEYS="roleID";

    private String description;

    private String name;

    public Role() {
    }

    public Role(String id, String description) {
        super(id);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
