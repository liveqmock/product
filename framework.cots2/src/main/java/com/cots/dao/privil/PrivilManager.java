/**
 *All rights reserved by cots co. ltd.
 */
package com.cots.dao.privil;

import com.cots.dao.DAO;
import com.cots.util.FileUtil;

import java.util.HashMap;
import java.util.Map;
import java.sql.*;

/**
 * User: chugh
 * Date: 2005-6-13
 * Time: 21:26:11
 */
public class PrivilManager {
    private Map roleSelectPrivil = new HashMap();
    private Map roleUpdatePrivil = new HashMap();
    private Map roleDeletePrivil = new HashMap();
    private Map roleInsertPrivil = new HashMap();

    private Map userSelectPrivil= new HashMap();
    private Map userUpdatePrivil= new HashMap();
    private Map userDeletePrivil= new HashMap();

    public void init(DAO dao){
        clear();
        try{
            load(dao);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void clear(){
        roleSelectPrivil.clear();
        roleUpdatePrivil.clear();
        roleDeletePrivil.clear();
        roleInsertPrivil.clear();

        userSelectPrivil.clear();
        userUpdatePrivil.clear();
        userDeletePrivil.clear();
    }

    private void load(DAO dao) throws SQLException{
        Connection conn = dao.getConnection();
        Statement stmt = null;
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select tableName,roleID,opType,privil from cotsdataprivil");
            while(rs.next()){
                String tableName = rs.getString("tableName");
                String roleID = rs.getString("roleID");
                String opType = rs.getString("opType");
                String privil = rs.getString("privil");

                Map ps = null;
                if(opType.equalsIgnoreCase("delete")){
                    ps = (Map)roleDeletePrivil.get(roleID);
                    if(ps == null){
                        ps = new HashMap();
                        roleDeletePrivil.put(roleID,ps);
                    }
                }else if(opType.equalsIgnoreCase("update")){
                    ps = (Map)roleUpdatePrivil.get(roleID);
                    if(ps == null){
                        ps = new HashMap();
                        roleUpdatePrivil.put(roleID,ps);
                    }
                }else if(opType.equalsIgnoreCase("select")){
                    ps = (Map)roleSelectPrivil.get(roleID);
                    if(ps == null){
                        ps = new HashMap();
                        roleSelectPrivil.put(roleID,ps);
                    }
                }
                if(ps!=null){
                    ps.put(tableName,privil);
                }
            }
        }finally{
            conn.close();
        }
    }

    public String getSelectPrivil(String userID,String[] roleIDs,Map values,String table){
        Map p = (Map)userSelectPrivil.get(userID);
        if(p == null){
            synchronized(userSelectPrivil){
                p = (Map)userSelectPrivil.get(userID);
                if(p == null){
                    p = new HashMap();
                    userSelectPrivil.put(userID,p);
                }
            }
        }
        String addWhere = (String)p.get(table);
        if(addWhere == null){
            addWhere = buildUserPrivil(roleSelectPrivil,roleIDs,table);
            synchronized(p){
                p.put(table,addWhere);
            }
        }
       return parsePrivil(addWhere,values);
    }

    public String getDeletePrivil(String userID,String[] roleIDs,Map values,String table){
        Map p = (Map)userDeletePrivil.get(userID);
        if(p == null){
            synchronized(userDeletePrivil){
                p = (Map)userDeletePrivil.get(userID);
                if(p == null){
                    p = new HashMap();
                    userDeletePrivil.put(userID,p);
                }
            }
        }
        String addWhere = (String)p.get(table);
        if(addWhere == null){
            addWhere = buildUserPrivil(roleDeletePrivil,roleIDs,table);
            synchronized(p){
                p.put(table,addWhere);
            }
        }
       return parsePrivil(addWhere,values);
    }

    public String getUpdatePrivil(String userID,String[] roleIDs,Map values,String table){
        Map p = (Map)userUpdatePrivil.get(userID);
        if(p == null){
            synchronized(userUpdatePrivil){
                p = (Map)userUpdatePrivil.get(userID);
                if(p == null){
                    p = new HashMap();
                    userUpdatePrivil.put(userID,p);
                }
            }
        }
        String addWhere = (String)p.get(table);
        if(addWhere == null){
            addWhere = buildUserPrivil(userUpdatePrivil,roleIDs,table);
            synchronized(p){
                p.put(table,addWhere);
            }
        }
       return parsePrivil(addWhere,values);
    }

    /**
     * return the additionla where clause.
     *
     * @param ps
     * @param roleIDs
     * @return
     */
    private String buildUserPrivil(Map ps,String[] roleIDs,String table){
        StringBuffer sb = new StringBuffer(32);
        for(int i=0;i<roleIDs.length;i++){
            Map p = (Map)ps.get(roleIDs[i]);
            if(p!=null){
                String addWhere = (String)p.get(table);
                if(addWhere != null){
                    if(sb.length()>0){
                        sb.append(" or ");
                    }
                    sb.append(addWhere);
                }
            }
        }
        if(sb.length()>0){
            return new String(sb);
        }else{
            return "";
        }
    }

    private String parsePrivil(String addWhere,Map values){
        if(addWhere == null || addWhere.length()<1){
            return null;
        }else{
            return FileUtil.replace(addWhere,"${","}",values);
        }
    }
}
