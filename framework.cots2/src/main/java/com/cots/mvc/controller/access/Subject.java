/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller.access;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-26
 * Time: 13:51:13
 * Version: 1.0
 */
public class Subject {
    //only authorized user can access the request;
    public final static String AUTHORIZED = "auth";

    //only admin's access;
    public final static String ADMIN = "admin";

    //not available;
    public final static String DISABLED = "disabled";

    //every one can access the request;
    public final static String FREE = "free";

    protected String id;
    protected String name;
    protected String status;

    public Subject() {
    }

    public Subject(String id) {
        this.id = id;
    }

    public Subject(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Subject(String id,String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
