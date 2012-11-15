package com.cots.dao.privil;

/**
 * User: chugh
 * Date: 2005-6-13
 * Time: 19:48:36
 */
public interface DataPrivil {    
    boolean canInsert(String userid,String table);

    String getSelectPrivil(String userid,String table);

    String getDeletePrivil(String userid,String table);

    String getUpdatePrivil(String userid,String table);
}
