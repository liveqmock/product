/**
 *all rights reserved,@copyright 2003
 */
package com.cots.dao;

import com.cots.dao.helper.ObjectHelperFactory;
import com.cots.dao.helper.ObjectHelper;
import com.cots.dao.helper.ResultSetHelper;
import com.cots.dao.sql.BaseStatement;
import com.cots.bean.Bean;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * Description:
 *      A DAO is responsible to insert,update,delete,select records to and from a database.
 * Typically, there is one DAO object for each database. There is only one DataSource associated
 * with a DAO.
 *
 * User: chuguanghua
 * Date: 2004-12-3
 * Time: 10:41:40
 * Version: 1.0
 */
public class JdbcDAO extends AbstractDAO {

    //Connection used in transaction;
    private static ThreadLocal transConnection;

    protected Logger log = Logger.getLogger(JdbcDAO.class);

    protected HashMap predifinedStmts = new HashMap();

    public JdbcDAO(String name, ObjectHelperFactory helperFactory) {
        super(helperFactory);
        this.name = name;
    }

    public JdbcDAO(String name, String dbType, DataSource dataSource,
                   ObjectHelperFactory helperFactory) {
        super(helperFactory);
        this.name = name;
        this.dbType = dbType;
        this.dataSource = dataSource;
    }


    /**
     * insert an object; If there is a transaction active currently, this operation will
     * join this transaction automatically.
     *
     * @param obj
     * @return
     * @throws SQLException
     */
    public int insert(Object obj) throws SQLException {
        ObjectHelper helper = helperFactory.getObjectHelper(obj);
        if(helper==null){
            throw new SQLException("not cots managed Bean:"
                    +obj.getClass().getName());
        }

        Connection conn = threadGetConnection();
        try {
            return helper.insert(obj, conn);
        } finally {
            threadCloseConnection(conn);
        }
    }

    /**
     * insert multiple objects. Those objects must be instances of the same class.
     *
     * @param objs Objects to be inserted.
     * @return count of rows affected.
     * @throws SQLException
     */
    public int insert(Object[] objs) throws SQLException {
        ObjectHelper helper = helperFactory.getObjectHelper(objs[0]);
        if(helper==null){
            throw new SQLException("not cots managed Bean:"
                    +objs[0].getClass().getName());
        }

        Connection conn = threadGetConnection();
        try {
            return helper.insertBatch(objs, conn);
        } finally {
            threadCloseConnection(conn);
        }
    }


    /**
     * insert a list of Objects, these objects must be instances of class specified by parameter beanCls;
     *
     * @param beanCls the Class object of the objects to be inserted;
     * @param objs the List that contains objects to be inserted;
     * @return count of rows affected;
     * @throws SQLException
     */
    public int insert(Class beanCls,List objs) throws SQLException {
        if(objs == null || objs.size()<1){
            return 0;
        }
        
        ObjectHelper helper = helperFactory.getObjectHelper(beanCls);
        if(helper==null){
            throw new SQLException("not cots managed Bean:"
                    +beanCls.getName());
        }

        Connection conn = threadGetConnection();
        try {
            int affected = 0;
            int count = objs.size();
            for(int i=0;i<count;i++){
                affected += helper.insert(objs.get(i), conn);
            }
            return affected;
        } finally {
            threadCloseConnection(conn);
        }
    }


    /**
     * insert different types objects into the underlying database.
     *
     * @param objs Objects array;
     * @return  count of rows affected;
     * @throws SQLException
     */
    public int insertMix(Object[] objs) throws SQLException {
        int count = 0;

        Connection conn = threadGetConnection();
        try {
            for (int i = 0; i < count; i++) {
                ObjectHelper helper = helperFactory.getObjectHelper(objs[i]);
                if(helper==null){
                    throw new SQLException("not cots managed Bean:"
                            +objs[i].getClass().getName());
                }

                count += helper.insert(objs[i], conn);
            }
            return count;
        } finally {
            threadCloseConnection(conn);
        }
    }

    /**
     * delete an object;
     *
     * @param obj
     * @return
     * @throws SQLException
     */
    public int delete(Object obj) throws SQLException {
        ObjectHelper helper = helperFactory.getObjectHelper(obj);
        if(helper==null){
            throw new SQLException("not cots managed Bean:"+obj.getClass().getName());
        }

        Connection conn = threadGetConnection();
        try {
            return helper.delete(obj, conn,null);
        } finally {
            threadCloseConnection(conn);
        }
    }

    public int delete(Object obj,String addWhere) throws SQLException {
        ObjectHelper helper = helperFactory.getObjectHelper(obj);
        if(helper==null){
            throw new SQLException("not cots managed Bean:"+obj.getClass().getName());
        }

        Connection conn = threadGetConnection();
        try {
            return helper.delete(obj, conn,addWhere);
        } finally {
            threadCloseConnection(conn);
        }
    }

    /**
     * insert multiple objects.
     *
     * @param obj
     * @return
     * @throws SQLException
     */
    public int delete(Object[] obj) throws SQLException {
        ObjectHelper helper = helperFactory.getObjectHelper(obj[0]);
        if(helper==null){
            throw new SQLException("not cots managed Bean:"+obj.getClass().getName());
        }

        Connection conn = threadGetConnection();
        try {
            return helper.deleteBatch(obj, conn,null);
        } finally {
            threadCloseConnection(conn);
        }
    }

    public int delete(Object[] obj,String addWhere) throws SQLException {
        ObjectHelper helper = helperFactory.getObjectHelper(obj[0]);
        if(helper==null){
            throw new SQLException("not cots managed Bean:"+obj.getClass().getName());
        }

        Connection conn = threadGetConnection();
        try {
            return helper.deleteBatch(obj, conn,addWhere);
        } finally {
            threadCloseConnection(conn);
        }
    }

    /**
     * delete records from a table.
     * 
     * @param table  the table displayName.
     * @param column the column displayName.
     * @param values records that matching items in this array will be deleted.
     * @return count of rows affected.
     * @throws SQLException
     */
    public int delete(String table,String column,Object[] values) throws SQLException{
        int count = 0;
        Connection conn = threadGetConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("delete from "+DBType.buildName(table,this.dbType)+" where "
                    +DBType.buildName(column,this.dbType)+"=?");

            for (int i = 0; i < values.length; i++) {
                stmt.setObject(1,values[i]);

                count += stmt.executeUpdate();
            }
            return count;
        } finally {
            if(stmt!=null){
                stmt.close();
            }
            threadCloseConnection(conn);
        }
    }

    /**
     * delete multiple object that are not instances of the same class;
     *
     * @param objs
     * @return
     * @throws SQLException
     */
    public int deleteMix(Object[] objs) throws SQLException {
        int count = 0;
        Connection conn = threadGetConnection();
        try {
            for (int i = 0; i < objs.length; i++) {
                ObjectHelper helper = helperFactory.getObjectHelper(objs[i]);
                if(helper==null){
                    throw new SQLException("not cots managed Bean:"
                            +objs[i].getClass().getName());
                }

                count += helper.delete(objs[i], conn,null);
            }
            return count;
        } finally {
            threadCloseConnection(conn);
        }
    }

    /**
     * delete records from the underlying database;
     *
     * @param table  the displayName of the table;
     * @param cols   columns in the where Clause;
     * @param values
     * @return
     * @throws SQLException
     */
    public int delete(String table, String[] cols, Object[] values)
            throws SQLException {
        Connection conn = threadGetConnection();

        StringBuffer sql = new StringBuffer("delete from ").
                append(DBType.buildName(table, this.dbType));
        if (cols.length > 0) {
            sql.append(" where ");
        }
        int i;
        for (i = 0; i < cols.length-1; i++) {
            sql.append(DBType.buildName(cols[i],this.dbType)).append("=?");
            sql.append(" and ");
        }
        if(cols.length>0){
            sql.append(DBType.buildName(cols[i],this.dbType)).append("=?");
        }

        PreparedStatement stmt = conn.prepareStatement(new String(sql));

        try {
            for (i = 0; i < values.length; i++) {
                stmt.setObject(i + 1, values[i]);
            }
            return stmt.executeUpdate();
        } finally {
            try {
                stmt.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
            threadCloseConnection(conn);
        }
    }

    /**
     * delete records from the underlying database;
     *
     * @param table  the displayName of the table;
     * @param cols   columns in the where clause;
     * @param values the column values;
     * @return count of rows affected;
     * @throws SQLException
     */
    public int delete(String table, String[] cols, Object[][] values)
            throws SQLException {
        int count = 0;

        Connection conn = threadGetConnection();

        PreparedStatement stmt;

        StringBuffer sql = new StringBuffer("delete from ")
                .append(DBType.buildName(table, this.dbType));
        if (cols.length > 0) {
            sql.append(" where ");
        }
        int i;
        for (i = 0; i < cols.length-1; i++) {
            sql.append(DBType.buildName(cols[i],this.dbType)).append("=?");
            sql.append(" and ");
        }
        if(cols.length>0){
            sql.append(DBType.buildName(cols[i],this.dbType)).append("=?");
        }

        stmt = conn.prepareStatement(new String(sql));

        try {
            for (i = 0; i < values.length; i++) {
                for (int j = 0; j < values[i].length; j++) {
                    stmt.setObject(j + 1, values[j]);
                }
                count += stmt.executeUpdate();
            }
            return count;
        } finally {
            try {
                stmt.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
            threadCloseConnection(conn);
        }
    }


    /**
     * update an Object into the underlying database, this object must have the primary
     * key set.
     *
     * @param obj the object to be updated into the database;
     * @return count of rows affected;
     * @throws SQLException
     */
    public int update(Object obj) throws SQLException {
        ObjectHelper helper = helperFactory.getObjectHelper(obj);
        if(helper==null){
            throw new SQLException("not cots managed Bean:"
                    +obj.getClass().getName());
        }

        Connection conn = threadGetConnection();
        try {
            return helper.update(obj, conn,null);
        } finally {
            threadCloseConnection(conn);
        }
    }

    public int update(Object obj,String addWhere) throws SQLException {
        ObjectHelper helper = helperFactory.getObjectHelper(obj);
        if(helper==null){
            throw new SQLException("not cots managed Bean:"
                    +obj.getClass().getName());
        }

        Connection conn = threadGetConnection();
        try {
            return helper.update(obj, conn,addWhere);
        } finally {
            threadCloseConnection(conn);
        }
    }

    /**
     * update multiple objects that are instances of the same class to
     * the underlying database.
     *
     * @param objs the objecs array;
     * @return count of rows afftected;
     * @throws SQLException
     */
    public int update(Object[] objs,String addWhere) throws SQLException {
        ObjectHelper helper = helperFactory.getObjectHelper(objs[0]);
        if(helper==null){
            throw new SQLException("not cots managed Bean:"
                    +objs[0].getClass().getName());
        }

        Connection conn = threadGetConnection();
        try {
            return helper.updateBatch(objs, conn,addWhere);
        } finally {
            threadCloseConnection(conn);
        }
    }

    /**
     *
     *
     * @param objs
     * @return
     * @throws SQLException
     */
    public int update(Object[] objs) throws SQLException {
        return update(objs,(String)null);
    }

    /**
     * update specified columns in a table.
     *
     * @param table  the displayName of the table to update;
     * @param cols   the columns to be updated;
     * @param values the new values of those columns;
     * @return count of rows affected;
     * @throws SQLException
     */
    public int update(String table, String[] cols, Object[] values)
            throws SQLException {
        Connection conn = threadGetConnection();
        PreparedStatement stmt = null;
        try {
            if (cols.length != values.length) {
                log.error("update(String table,String[] cols,Object[] values) failed "
                        + "due to cols.length!=values.length");
            }
            StringBuffer sql = new StringBuffer("update ")
                    .append(DBType.buildName(table, this.dbType))
                    .append(" set ");
            int i;
            for (i = 0; i < cols.length-1; i++) {
                sql.append(DBType.buildName(cols[i],this.dbType)).append("=?");
                sql.append(",");
            }
            sql.append(DBType.buildName(cols[i],this.dbType)).append("=?");

            if (log.isDebugEnabled()) {
                log.debug("built sql: " + new String(sql));
            }
            stmt = conn.prepareStatement(new String(sql));
            for (i = 0; i < values.length; i++) {
                stmt.setObject(i + 1, values[i]);
            }
            return stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            threadCloseConnection(conn);
        }
    }

    /**
     * update specified columns of matching rows in a table;
     *
     * @param table
     * @param cols
     * @param values
     * @param whereCols
     * @param whereValues
     * @return
     * @throws SQLException
     */
    public int update(String table, String[] cols, Object[] values,
                      String[] whereCols, Object[] whereValues)
            throws SQLException {
        Connection conn = threadGetConnection();
        StringBuffer sql = new StringBuffer("update ")
                .append(DBType.buildName(table, this.dbType))
                .append(" set ");
        int i;
        for (i = 0; i < cols.length-1; i++) {
            sql.append(DBType.buildName(cols[i],this.dbType)).append("=?");
            sql.append(",");
        }
        sql.append(DBType.buildName(cols[i],this.dbType)).append("=?");

        if (whereCols.length > 0) {
            sql.append(" where ");
        }
        for (i = 0; i < whereCols.length; i++) {
            sql.append(DBType.buildName(whereCols[i],this.dbType)).append("=?");
            if (i < whereCols.length - 1) {
                sql.append(" and ");
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("built sql: " + new String(sql));
        }

        PreparedStatement stmt = conn.prepareStatement(new String(sql));

        try {
            for (i=0; i < values.length; i++) {
                stmt.setObject(i + 1, values[i]);
            }
            for (int j = 0; j < whereValues.length; j++) {
                stmt.setObject(i + j + 1, whereValues[j]);
            }

            return stmt.executeUpdate();
        } finally {
            try {
                stmt.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
            threadCloseConnection(conn);
        }
    }


    /**
     *
     * @param table
     * @param cols
     * @param values
     * @param whereCols
     * @param whereValues
     * @return
     * @throws SQLException
     */
    public int update(String table, String[] cols, Object[] values,
                      String[] whereCols, Object[][] whereValues)
            throws SQLException {
        int count = 0;
        Connection conn = threadGetConnection();

        StringBuffer sql = new StringBuffer("update ")
                .append(DBType.buildName(table, this.dbType)).append(" set ");

        int i;
        for ( i = 0; i < cols.length-1; i++) {
            sql.append(DBType.buildName(cols[i],this.dbType)).append("=?");
            sql.append(",");
        }
        sql.append(DBType.buildName(cols[i],this.dbType)).append("=?");

        if (whereCols.length > 0) {
            sql.append(" where ");
        }
        for (i = 0; i < whereCols.length; i++) {
            sql.append(DBType.buildName(whereCols[i],this.dbType)).append("=?");
            if (i < whereCols.length - 1) {
                sql.append(" and ");
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("built sql: " + new String(sql));
        }

        PreparedStatement stmt = conn.prepareStatement(new String(sql));

        try {
            for (i=0; i < values.length; i++) {
                stmt.setObject(i + 1, values[i]);
            }
            for (int j = 0; j < whereValues.length; j++) {
                for (int k = 0; k < whereValues.length; k++) {
                    stmt.setObject(i + k + 1, whereValues[j][k]);
                }
                count += stmt.executeUpdate();
            }
            return count;
        } finally {
            try {
                stmt.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
            threadCloseConnection(conn);
        }
    }

    /**
     * update multiple ojbects which are not instances of the same class;
     *
     * @param objs
     * @return
     * @throws SQLException
     */
    public int updateMix(Object[] objs) throws SQLException {
        int count = 0;
        Connection conn = threadGetConnection();
        try {
            for (int i = 0; i < count; i++) {
                ObjectHelper helper = helperFactory.getObjectHelper(objs[i]);
                if(helper == null){
                    throw new SQLException("not cots managed Bean:"
                            +objs[0].getClass().getName());
                }

                count += helper.update(objs[i], conn,null);
            }
            return count;
        } finally {
            threadCloseConnection(conn);
        }
    }

    /**
     * @param objs
     * @param dmTypes
     * @return
     * @throws SQLException
     */
    public int dmMix(Object[] objs, int[] dmTypes) throws SQLException {
        int count = 0;
        if (objs.length != dmTypes.length) {
            throw new IllegalArgumentException("length not equal");
        }

        Connection conn = threadGetConnection();
        try {
            for (int i = 0; i < objs.length; i++) {
                switch (dmTypes[i]) {
                    case DM_INSERT:
                        count += insert(objs[i], conn);
                        break;
                    case DM_DELETE:
                        count += delete(objs[i], conn);
                        break;
                    case DM_UPDATE:
                        count += update(objs[i], conn);
                        break;
                    default:
                        count += insert(objs[i], conn);
                        break;
                }
            }
            return count;
        } finally {
            threadCloseConnection(conn);
        }
    }

    /**
     * execute a dml sql, like insert,delete,update or other DMLs.
     *
     * @param sql the dml sql;
     * @return count of rows affected;
     */
    public int executeUpdate(String sql) throws SQLException {
        Statement stmt = null;

        Connection conn = threadGetConnection();
        try {
            stmt = conn.createStatement();
            int count = stmt.executeUpdate(sql);
            return count;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            threadCloseConnection(conn);
        }
    }

    /**
     * execute a update, delete, insert sql with parameters;
     *
     * @param sql the dml sql to be executed;
     * @param params the parameters for the sql;
     * @return the count of rows affected.
     * @throws SQLException
     */
    public int executeUpdate(String sql, Object[] params) throws SQLException {
        PreparedStatement stmt = null;

        Connection conn = threadGetConnection();
        try {
            stmt = conn.prepareStatement(sql);
            if(params!=null){
                for(int i=0;i<params.length;i++){
                    stmt.setObject(i+1,params[i]);
                }
            }
            int count = stmt.executeUpdate();
            return count;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            threadCloseConnection(conn);
        }
    }

    /**
     *
     *
     * @param stmtID
     * @param params
     * @return
     * @throws SQLException
     */
    public List execute(String stmtID,Map params) throws SQLException{
        BaseStatement bs = (BaseStatement)this.predifinedStmts.get(stmtID);
        if(bs==null){
            throw new SQLException("no this predefined statement:"+stmtID);
        }
        String rowClass = bs.getRowClass();

        Class row;
        try{
            row = Class.forName(rowClass);
        }catch(ClassNotFoundException e){
            throw new SQLException("class not found for:"+rowClass);
        }

        ObjectHelper oh = helperFactory.getObjectHelper(row);

        Connection conn = threadGetConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt = bs.prepare(conn);
            bs.setParamValues(stmt,params);
            boolean result = stmt.execute();
            if(result){
                rs = stmt.getResultSet();
                List resultsList = oh.populateBatch(rs,this);
                bs.getParamValues(stmt,params);
                return resultsList;
            }else{
                return null;
            }
        }finally{
            if(stmt!=null){
                try{
                    stmt.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(rs!=null){
                try{
                    rs.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }

            threadCloseConnection(conn);
        }
    }

    /**
     * the caller should release the associated connection; otherwise
     * the connection will never reutrn to the connections pool.
     *
     * @param sql sql to run;
     * @return a ResultSet Object;
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        Connection conn = threadGetConnection();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }

    /**
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public RowSet queryRowSet(String sql) throws SQLException {
        Connection conn = threadGetConnection();

        if(conn instanceof PooledConnection){
            ((PooledConnection)conn).setAlone();
        }

        try {
            return queryRowSet(sql, conn);
        } finally {
            threadCloseConnection(conn);
        }
    }

    /**
     *
     * @param obj
     * @throws SQLException
     */
    public int expand(Object obj,String addWhere) throws SQLException {
        ObjectHelper helper = helperFactory.getObjectHelper(obj);
        if(helper == null){
            throw new SQLException("not cots managed Bean:"
                    +obj.getClass().getName());
        }

        Connection conn = threadGetConnection();
        try {
            return helper.expand(obj, conn, this,addWhere);
        } finally {
            threadCloseConnection(conn);
        }
    }

    public int expand(Object obj) throws SQLException {
        return expand(obj,null);
    }

    /**
     *
     * @param beanClz
     * @param where
     * @return
     * @throws SQLException
     */
    public List query(Class beanClz, String where) throws SQLException {
        ArrayList al = new ArrayList(64);
        query(beanClz, where, al);
        return al;
    }

    /**
     *
     * @param beanCls
     * @param where
     * @param page
     * @return
     * @throws SQLException
     */
    public List query(Class beanCls, String where, Page page)
            throws SQLException {
        List results = new ArrayList();
        query(beanCls,where,null,page,results);
        return results;
    }

    /**
     *
     * @param beanCls
     * @param where
     * @param orderBy
     * @return
     * @throws SQLException
     */
    public List query(Class beanCls, String where, String orderBy)
            throws SQLException {
        ArrayList al = new ArrayList(64);
        query(beanCls, where, orderBy, al);
        return al;
    }

    /**
     *
     *
     * @param beanCls
     * @param where
     * @param orderBy
     * @param page
     * @return
     * @throws SQLException
     */
    public List query(Class beanCls, String where, String orderBy, Page page)
            throws SQLException {
        List results = new ArrayList();
        query(beanCls,where,orderBy,page,results);
        return results;
    }

    /**
     * executeQuery specified object into a list.
     *
     * @param beanClz the target Object to be executed.
     * @param where   specify which objects to executeQuery;
     * @param list    save the returned objects. must not be null.
     * @return count of objects found.
     * @throws SQLException
     */
    public int query(Class beanClz, String where, List list)
            throws SQLException {
        Connection conn = threadGetConnection();
        try {
            return executeQuery(beanClz, where, list, conn);
        } finally {
            threadCloseConnection(conn);
        }
    }

    /**
     *
     *
     * @param beanCls
     * @param where
     * @param page
     * @param results
     * @return
     * @throws SQLException
     */
    public int query(Class beanCls, String where, Page page, List results)
            throws SQLException {
        return query(beanCls,where,null,page,results);
    }

    /**
     *
     * @param beanCls
     * @param where
     * @param orderBy
     * @param results
     * @return
     * @throws SQLException
     */
    public int query(Class beanCls, String where, String orderBy, List results)
            throws SQLException {
        Connection conn = threadGetConnection();
        try {
            return executeQuery(beanCls, where, orderBy, results, conn);
        } finally {
            threadCloseConnection(conn);
        }
    }

    /**
     *
     *
     * @param beanCls the Class object of the Bean
     * @param where  the where clause not including the "where" keyword;
     * @param orderBy the order by clause not including the "order by" keyword;
     * @param page the page condition;
     * @param results a List object contains the returned Object;
     * @return count of objects in the list;
     * @throws SQLException
     */
    public int query(Class beanCls, String where, String orderBy, Page page, List results)
            throws SQLException {
        Connection conn = threadGetConnection();
        try {
            //first decide how many records there are;
            Bean bean = helperFactory.getBeanFactory().getByClass(beanCls);
            StringBuffer sql = new StringBuffer("select count(*) from ")
                    .append(DBType.buildName(bean.getTableName(), dbType));
            if (where != null && where.length() > 0) {
                sql.append(" where ").append(where);
            }

            int count = countRows(conn,new String(sql));

            page.setTotalRows(count);

            if (count <= 0) {   //no records to list;
                page.setCurrent(0);
                page.setLast(true);
                page.setTotal(0);
                return 0;                
            } else {
                int start = computePage(count,page);

                sql = new StringBuffer("select ").append(bean.getColumnNamesString()).append(" from ")
                        .append(DBType.buildName(bean.getTableName(), dbType));
                if (where != null && where.length() > 0) {
                    sql.append(" where ").append(where);
                }
                if(orderBy!=null && orderBy.length()>0){
                    sql.append(" order by ").append(orderBy);
                }

                return queryPage(conn,new String(sql),start,
                        page.getRowsPerPage(),beanCls,results);
            }
        } finally {
            threadCloseConnection(conn);
        }
    }

    /**
     * execute a join query, the sql must like "select a.* from table1 a,table b.*",
     * that is to say the returned ResultSet must and only contains all columns of
     * one table.
     *
     * @param beanCls the Class object of the returned Object.
     * @param sql the "select" sql statement.
     * @param results the List to store the returned Objects;
     * @return count of Objects returned.
     * @throws SQLException
     */
    public int executeQuery(Class beanCls, String sql, List results)
            throws SQLException {
        Connection conn = threadGetConnection();
        Statement stmt = conn.createStatement();
        try {
            ResultSetHelper rsh = helperFactory.getResultSetHelper(beanCls);
            ResultSet rs = stmt.executeQuery(sql);
            return rsh.populateBatch(results, rs, this);
        } finally {
            stmt.close();
            threadCloseConnection(conn);
        }
    }

    /**
     *
     *
     * @param beanCls the Class of the objects to be queried;
     * @param sql  the sql should be like "select a.* from Table1 a,Table2 b where ...."
     *              and the parameter beanCls must be the Class the object mapped to Table1;
     * @return count of objects queried;
     * @throws SQLException
     */
    public List executeQuery(Class beanCls, String sql)
            throws SQLException {
        List results = new ArrayList();
        executeQuery(beanCls,sql,results);
        return results;
    }

    /**
     * execute a query with parameters. 
     *
     * @param beanCls
     * @param sql
     * @param params
     * @param results
     * @return
     * @throws SQLException
     */
    public int executeQuery(Class beanCls, String sql, Object[] params,List results) throws SQLException {
        Connection conn = threadGetConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        try {
            if(params!=null){
                for(int i=0;i<params.length;i++){
                    stmt.setObject(i+1,params[i]);
                }
            }
            ResultSetHelper rsh = helperFactory.getResultSetHelper(beanCls);
            ResultSet rs = stmt.executeQuery();
            return rsh.populateBatch(results,rs, this);
        } finally {
            stmt.close();
            threadCloseConnection(conn);
        }
    }


    public List executeQuery(Class beanCls, String sql, Object[] params) throws SQLException {
        List results = new ArrayList(32);
        executeQuery(beanCls,sql,params,results);
        return results;
    }

    /**
     *
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public ResultSet executeQuery(String sql, Object[] params) throws SQLException {
        PreparedStatement pstmt=null;
        Connection conn = threadGetConnection();

        if(conn instanceof PooledConnection){
            ((PooledConnection)conn).setAlone();
        }

        pstmt = conn.prepareStatement(sql);
        if(params!=null){
            for(int i=0;i<params.length;i++){
                pstmt.setObject(i+1,params[i]);
            }
        }
        return pstmt.executeQuery();
    }

    /**
     * add a predefined statements to this dao object;
     *
     * @param bs the new predefined statements to be added.
     */
    public void addPredefinedStatement(BaseStatement bs){
        this.predifinedStmts.put(bs.getId(),bs);
    }

    /**
     * begin a transaction explicitly;
     *
     * @throws SQLException
     */
    public void beginTrans() throws SQLException {
        beginTrans(this.defaultIsolation);
    }


    /**
     * begin a new transaction. if there is still a transaction active, then this transaction
     * will be rollback.
     *
     * @throws SQLException
     */
    public void beginTrans(int level) throws SQLException {
        //need to check if there is any transaction in progress??
        if (transConnection != null) {
            Connection conn = (Connection) transConnection.get();
            if (conn != null) {
                conn.rollback();
                conn.setAutoCommit(false);
                if (this.usingIsolation) {
                    conn.setTransactionIsolation(this.defaultIsolation);
                }
                conn.close();
            }
        }

        //create a new Transaction Connection;
        transConnection = new ThreadLocal() {
            protected Object initialValue() {
                try {
                    return getConnection();
                } catch (SQLException e) {
                    log.error("failed to get Connection from DataSource",e);
                    return null;
                }
            }
        };
        Object connection;
        if ((connection = transConnection.get()) == null) {
            throw new SQLException("no connection available");
        } else {
            Connection conn = (Connection) connection;

            //set the transaction isolation level;
            if (this.isUsingIsolation()) {
                conn.setTransactionIsolation(level);
            }

            conn.setAutoCommit(false);
        }
    }

    /**
     * commit current transaction;
     *
     * @throws SQLException
     */
    public void commit() throws SQLException {
        if (transConnection == null) {
            throw new SQLException("no transaction active");
        }

        Connection conn = (Connection) transConnection.get();
        if (conn == null || conn.getAutoCommit()) {
            throw new SQLException("no transaction active");
        } else {
            try {
                conn.commit();
                conn.setAutoCommit(true);
            } finally {
                transConnection = null;

                //set the default transaction isolation level;
                if (this.usingIsolation) {
                    conn.setTransactionIsolation(this.defaultIsolation);
                }
                conn.close();
            }
        }
    }

    /**
     * rollback current transaction;
     *
     * @throws SQLException
     */
    public void rollback() throws SQLException {
        if (transConnection == null) {
            throw new SQLException("no transaction active");
        }

        Connection conn = (Connection) transConnection.get();
        if (conn == null || conn.getAutoCommit()) {
            throw new SQLException("no transaction active");
        } else {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } finally {
                transConnection = null;

                //set the default transaction isolation level;
                if (this.usingIsolation) {
                    conn.setTransactionIsolation(this.defaultIsolation);
                }
                conn.close();
            }
        }
    }

    /**
     * get a connection to operate on a DB; If the user has begun a transaction, then
     * the prefetched connection is returned. otherwise a new Connection from the datasouce
     * is returned;
     *
     * @return java.sql.Connection object;
     * @throws SQLException
     */
    public Connection threadGetConnection() throws SQLException {
        if (transConnection == null) {
            return getConnection();
        } else {
            return (Connection) transConnection.get();
        }
    }


    /**
     * close a connection used by current Thread;
     *
     * @param conn the Connection to be closed;
     * @throws SQLException
     */
    public void threadCloseConnection(Connection conn)
            throws SQLException {
        //check if the connection is a transaction connection,
        //if not, close it.
        if (transConnection != conn) {
            conn.close();
        }
    }
}