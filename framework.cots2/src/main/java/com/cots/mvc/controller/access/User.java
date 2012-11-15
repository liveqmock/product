/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller.access;

import com.cots.dao.IDAOAccessor;
import com.cots.dao.DAO;

import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-26
 * Time: 13:27:35
 * Version: 1.0
 */
public class User extends Principal implements IDAOAccessor{

    public final static String TABLE = "cotsuser";

    public final static String KEYS = "userID";
    
    //users that are  enabled,can login to applications;
    public final static String ENABLED="enabled";

    //users that are disabled can't login to any applications.
    public final static String DISABLED="disabled";

    //type of the user;
    private String type;

    //status of the user, one of enabled or disabled;
    private String status;

    private String email;

    private String name;

    private String password;

    private String startIP;

    private String endIP;

    public User(){
        super();
        this.status=ENABLED;
    }

    public User(String id) {
        super(id);
        this.status=ENABLED;
    }

    public User(String id,String type) {
        super(id);
        this.status = ENABLED;
        this.type = type;
    }

    public User(String id,String type,String status){
        super(id);
        this.type = type;
        this.status = status;
    }

    private DAO dao;
    public void setDAO(DAO dao){
        this.dao = dao;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStartIP() {
        return startIP;
    }

    public void setStartIP(String startIP) {
        this.startIP = startIP;
    }

    public String getEndIP() {
        return endIP;
    }

    public void setEndIP(String endIP) {
        this.endIP = endIP;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List getRoles(){
        //return dao.q
        List roles = new ArrayList();
        try{
            dao.executeQuery(Role.class,"select b.* from cotsuser a,cotsuserrole ab, cotsrole b " +
                    "where a.userID = ab.userid and ab.roleID =b.roleid and a.userid='"+
                    this.id.replaceAll("'","''")+"'",roles);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return roles;
    }
}