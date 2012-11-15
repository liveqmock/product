/**
 *all rights reserved,@copyright 2003
 */
package com.cots.dao.helper;

import com.cots.dao.DAO;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * Description:
 * the cots frame work will implement this interface for each
 * cots managed beans to make those beans be persisted to and from
 * a underlying database.
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-2
 * Time: 18:09:59
 */
public interface ObjectHelper extends ResultSetHelper{
    /**
     * insert an object to the underlying store;
     */
    int insert(Object obj, Connection conn) throws SQLException;

    /**
     * insert multiple objects to the underlying stores;
     *
     * @param objs
     */
    int insertBatch(Object[] objs, Connection conn) throws SQLException;


    /**
     * expand an Object;
     *
     * @param obj
     * @param conn
     * @return
     * @throws SQLException
     */
    int expand(Object obj, Connection conn,DAO dao,String addWhere) throws SQLException;

    /**
     * delete an object from the underlying store;
     *
     * @param obj
     */
    int delete(Object obj,Connection conn,String addWhere) throws SQLException;

    /**
     * delete multiple objects from the underlying store;
     *
     * @param objs
     */
    int deleteBatch(Object[] objs, Connection conn,String addWhere) throws SQLException;

    /**
     * upadte an object in the underlying store;
     *
     * @param obj
     */
    int update(Object obj, Connection conn,String addWhere) throws SQLException;

    /**
     * update multiple store in the underlying store;
     *
     * @param objs
     */
    int updateBatch(Object[] objs, Connection conn,String addWhere) throws SQLException;

    /**
     * get the owner factory of this helper object;
     *
     * @return
     */
    ObjectHelperFactory getOwnerFactory();

    /**
     *
     * @param ohf
     */
    void setOwnerFactory(ObjectHelperFactory ohf);
}