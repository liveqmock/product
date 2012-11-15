/**
 *all rights reserved,@copyright 2003
 */
package com.cots.common;

import com.cots.blc.AbstractBLC;
import com.cots.blc.BLException;
import com.cots.bean.BeanFactory;
import com.cots.bean.Bean;
import com.cots.dao.RowSet;
import com.cots.dao.DAO;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-17
 * Time: 13:20:52
 * Version: 1.0
 */
public class DBBLC extends AbstractBLC{

    protected String daoName="default";

    public DBBLC(){

    }

    /**
     * insert a new object into the undelying database.
     *
     * @param obj  the Object to be inserted;
     * @return count of rows affected;
     * @throws SQLException
     */
    public int insert(Object obj) throws SQLException{
        return context.getDAO(daoName).insert(obj);
    }

    /**
     * insert multiple objects that are instances of the same class
     * into the underlying database.
     *
     * @param objs
     * @return
     * @throws SQLException
     */
    public int insert(Object[] objs) throws SQLException{
        return context.getDAO(daoName).insert(objs);
    }

    /**
     * delete an object from the underlying database. The object must have
     * the primary key set beforehand.
     *
     * @param o the object to be deleted.
     * @return count of rows affected;
     * @throws SQLException
     */
    public int delete(Object o) throws SQLException{
        return context.getDAO(daoName).delete(o);
    }

    /**
     * delete multiple objects that are instances of the same class from the underlying
     * database. All of the objects must have their primary keys set before hand.
     *
     * @param objs the objects to be deleted;
     * @return count of objects to be deleted.
     * @throws SQLException
     */
    public int delete(Object[] objs) throws SQLException{
        return context.getDAO(daoName).delete(objs);
    }

    /**
     * update an Object that already in the database. The Object to be updated must
     * have primary key set beforehand.
     *
     * @param o the object to be updated;
     * @return count of rows to be updated;
     * @throws SQLException
     */
    public int update(Object o) throws SQLException{
        return context.getDAO(daoName).update(o);
    }

    /**
     * update multiple records in the database. The objects to be udpated must have
     * their primary keys set before hand.
     *
     * @param objs the objects to be updated;
     * @return count of rows affected.
     * @throws SQLException
     */
    public int update(Object[] objs) throws SQLException{
        return context.getDAO(daoName).update(objs);
    }

    /**
     * expand an object. that is to retrieve all the fields of an object that only have primary key
     * set from the database.
     *
     * @param o the Object to be expaned. It must have the primary key set.
     * @return count of rows affected.
     * @throws SQLException
     */
    public int expand(Object o) throws SQLException{
        return context.getDAO(daoName).expand(o);
    }

    /**
     * exeucte a DML sql
     *
     * @param sql a DML sql, like, insert, delete,update,create ....
     * @return rows afftected;
     * @throws SQLException
     */
    public int executeUpdate(String sql) throws SQLException{
        return context.getDAO(daoName).executeUpdate(sql);
    }

    /**
     * query objects from a table that matches a given condition.
     *
     * @param clz the class object of the Class
     * @param where the condition to specify which rows to query;
     * @return count of rows returned.
     * @throws SQLException
     */
    public List executeQuery(Class clz,String where) throws SQLException{
        return context.getDAO(daoName).query(clz,where);
    }

    /**
     * execute a select sql;
     *
     * @param clz
     * @param where
     * @param result
     * @return
     * @throws SQLException
     */
    public int executeQuery(Class clz,String where,List result) throws SQLException{
        return context.getDAO(daoName).query(clz,where,result);
    }

    /**
     * query all the beans in the target dao;
     * 
     * @param beanName the displayName of the cots managed bean;
     * @return  java.util.List object;
     */
    public List queryAll(String beanName) throws SQLException{
        BeanFactory factory = context.getBeanFactory();
        Bean bean = factory.getByName(beanName);
        if(bean == null){
            throw new BLException("not cots managed Bean: "+beanName);
        }

        return context.getDAO(daoName).query(bean.getBeanClass(),null);
    }

    /**
     * query a row set;
     * 
     * @param sql an arbitary SELECT sql.
     * @return com.cots.dao.RowSet object, never null;
     * @throws SQLException
     */
    public RowSet queryRowSet(String sql) throws SQLException{
        return context.getDAO(daoName).queryRowSet(sql);
    }

    /**
     * execute a query sql, and fetch the values of the first column into a Set object which is
     * returned to the caller. The values of other columns will be just ignored. so it is better to
     * have the sql query only one column, like "select col1 from table......";
     *
     * @param sql an arbitary SELECT sql.
     * @return java.util.Set object, never null;
     * @throws SQLException
     */
    public Set querySet(String sql) throws SQLException{
        DAO dao = context.getDAO(daoName);
        Connection conn = dao.threadGetConnection();
        Statement stmt = null;
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            Set results = new HashSet();
            while(rs.next()){
                results.add(rs.getObject(1));
            }
            rs.close();
            return results;
        }finally{
            try{
                if(stmt!=null){
                    stmt.close();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }

            dao.threadCloseConnection(conn);
        }
    }
}