/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller.access;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-26
 * Time: 13:31:45
 * Version: 1.0
 */
public class Request extends Subject{
    
    public static final String TABLE="cotsrequest";
    public static final String KEYS="requestID";

    private String description;

    public Request() {
        super();
    }

    public Request(String id) {
        super(id);
    }

    public Request(String id,String name){
        super(id,name);
    }

    public Request(String name, String descritpion, String status) {
        super(name,status);
        this.description = descritpion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
