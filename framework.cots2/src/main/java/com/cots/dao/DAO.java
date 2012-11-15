package com.cots.dao;

import com.cots.dao.helper.ObjectHelperFactory;
import com.cots.dao.sql.BaseStatement;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**                       
 * Cots framework.
 * <p/>
 * User: chugh
 * Date: 2004-12-28
 * Time: 14:38:18
 * Version: 1.0
 */
public interface DAO {
    public final int DM_INSERT = 0;
    public final int DM_DELETE = 1;
    public final int DM_UPDATE = 2;

    DataSource getDataSource();
    /**
     * get a new Connection from the datasource;
     *
     * @return a new Connection;
     * @throws SQLException
     */
    Connection getConnection() throws SQLException;

    /**
     * get a Connection that will be used as a single connection to
     * perform transaction in the context of the current thread.
     *
     * @return java.sql.Connection object;
     * @throws SQLException
     */
    public Connection threadGetConnection() throws SQLException;

    /**
     * close the connection that is used to peform transaction by the current
     * thread;
     *
     * @param conn the Connection is is currently used for transaction in the context
     * of current thread;
     * @throws SQLException
     */
    public void threadCloseConnection(Connection conn) throws SQLException;


    /**
     * delete a Object(record) from the database;
     * @param obj the Object represents a record;
     * @return count of records affected;
     * @throws SQLException
     */
    int delete(Object obj) throws SQLException;

    int delete(Object obj,String addWhere) throws SQLException;

    /**
     * delete multiple records at a time.
     *
     * @param objs
     * @return
     * @throws SQLException
     */
    int delete(Object[] objs) throws SQLException;

    int delete(Object[] objs,String addWhere) throws SQLException;

    /**
     * delete multiple rows;
     *
     * @param table
     * @param column
     * @param values
     * @return
     */
    int delete(String table,String column,Object[] values)throws SQLException;

    /**
     * delete records that matching "criteria" from the underly database.
     *
     * @param table the displayName of the table to delete;
     * @param cols the column names to be as the criteria.
     * @param values the values of these columns.
     * @return
     * @throws SQLException
     */
    int delete(String table,String[] cols,Object[] values) throws SQLException;

    /**
     * delete records from the database.
     *
     * @param table
     * @param cols
     * @param values
     * @return
     * @throws SQLException
     */
    int delete(String table,String[] cols,Object[][] values) throws SQLException;

    /**
     * execute insert,delete, update operations at a time.
     *
     * @param objs
     * @param ops
     * @return
     * @throws SQLException
     */
    int dmMix(Object[] objs, int[] ops) throws SQLException;

    /**
     * execute a select sql directly;
     *
     * @param sql the sql
     * @return a ResultSet object, neven null;
     * @throws SQLException
     */
    ResultSet executeQuery(String sql) throws SQLException;

    /**
     * query records from the database.
     *
     * @param beanCls
     * @param where  specify which records to query. contains the real column names.
     * @return
     * @throws SQLException
     */
    List query(Class beanCls, String where) throws SQLException;


    /**
     * 
     * @param beanCls
     * @param where
     * @param page
     * @return
     * @throws SQLException
     */
    List query(Class beanCls, String where,Page page) throws SQLException;


    /**
     *
     * 
     * @param beanCls
     * @param where
     * @param orderBy
     * @return
     * @throws SQLException
     */
    List query(Class beanCls, String where,String orderBy) throws SQLException;


    /**
     *
     * @param beanCls
     * @param where
     * @param orderBy
     * @param page
     * @return
     * @throws SQLException
     */
    List query(Class beanCls, String where,String orderBy,Page page) throws SQLException;

    /**
     *
     *
     * @param beanCls
     * @param where
     * @param results
     * @return
     * @throws SQLException
     */
    int query(Class beanCls, String where, List results) throws SQLException;

    /**
     *
     * @param beanCls
     * @param where
     * @param page
     * @param results
     * @return
     * @throws SQLException
     */
    int query(Class beanCls, String where, Page page,List results) throws SQLException;


    /**
     * query beans with a condition. and save those beans to a List;
     *
     * @param beanCls the Class object of the bean to query
     * @param where specify what beans to query;
     * @param orderBy specify the order by clause;
     * @param results the java.util.List object to save the returned Beans;
     * @return rows affected;
     * @throws SQLException
     */
    int query(Class beanCls, String where,String orderBy,List results) throws SQLException;


    /**
     *
     * @param beanCls
     * @param where
     * @param orderBy
     * @param page
     * @param results
     * @return
     * @throws SQLException
     */
    int query(Class beanCls, String where,String orderBy,Page page,List results) throws SQLException;

    /**
     * execute a select sql directly. the returned resultset must contais all the columns
     * for the registered fields of the beanCls.
     *
     * @param beanCls the bean class to generated from the resultset;
     * @param sql the sql to be executed,should be select sql;
     * @param results the List to store the returned objects;
     * @return count of rows affected;
     * @throws SQLException
     */
    int executeQuery(Class beanCls, String sql, List results) throws SQLException;

    /**
     * execute a select sql directly.
     *
     * @param beanCls the Bean to be mapped to the ResultSet.
     * @param sql the sql to be executed, should be a select sql;
     * @return a List object contained all the objects built on the results set.
     * @throws SQLException
     */
    List executeQuery(Class beanCls, String sql) throws SQLException;

    /**
     * execute a query  
     *
     * @param beanCls
     * @param sql
     * @param params
     * @param results
     * @return
     * @throws SQLException
     */
    int executeQuery(Class beanCls,String sql,Object[] params,List results) throws SQLException;

    /**
     * execute a select sql with parameters.
     *
     * @param beanCls
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    List executeQuery(Class beanCls, String sql,Object[] params) throws SQLException;


    ResultSet executeQuery(String sql,Object[] params) throws SQLException;

    /**
     * execute a update,delete,or insert sql statement directly;
     *
     * @param sql the sql to be exeucted;
     * @return count of rows affected;
     * @throws SQLException
     */
    int executeUpdate(String sql) throws SQLException;

    /**
     * execute a update,delete,insert sql with parameters;
     *
     * @param sql the dml sqls;
     * @param params the parameters for the sql;
     * @return rows affected.
     * @throws SQLException
     */
    int executeUpdate(String sql,Object[] params) throws SQLException;

    /**
     * execute a predefined statement.
     *
     * @param stmtID
     * @param params
     * @return
     * @throws SQLException
     */
    List execute(String stmtID,Map params) throws SQLException;

    /**
     * expand an object's properties, the primary key must be set beforehand.
     *
     * @param obj the object to be expanded.
     * @return rows affected;
     * @throws SQLException
     */
    int expand(Object obj) throws SQLException;

    /**
     * insert an Object into the underlying database.
     *
     * @param obj the Object to be inserted;
     * @return count of rows affected;
     * @throws SQLException
     */
    int insert(Object obj) throws SQLException;

    /**
     * insert multiple objects into the undelying database. Those obejcts must be instances of
     * same Class.
     *
     * @param objs Objects array to be inserted;
     * @return count of objects to be inserted.
     * @throws SQLException
     */
    int insert(Object[] objs) throws SQLException;

    /**
     * insert List of objects;
     *
      * @param beanCls the shared Class of all the objects in the list;
     * @param objs the objects list;
     * @return rows of object affected;
     * @throws SQLException
     */
    int insert(Class beanCls,List objs) throws SQLException;

    /**
     *
     * insert multiple objects into the underlying database. Those objects can be instances of
     * differerent classes.
     *
     * @param objs Objects array to be inserted.
     * @return count of Objects to be inserted.
     * @throws SQLException
     */
    int insertMix(Object[] objs) throws SQLException;

    /**
     *
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    RowSet queryRowSet(String sql) throws SQLException;


    /**
     * update an object into the underlying database, this object must have
     * a primary key;
     *
     * @param obj
     * @return
     * @throws SQLException
     */
    int update(Object obj) throws SQLException;

    int update(Object obj,String addWhere) throws SQLException;

    /**
     * update multiple objects into the underlying database, these objects must be
     * instances of the same class. And each object must have a primary key.
     *
     * @param objs the array of objects to be udpated;
     * @return count of rows updated;
     * @throws SQLException
     */
    int update(Object[] objs) throws SQLException;

    /**
     *
     * 
     * @param objs
     * @param addWhere
     * @return
     * @throws SQLException
     */
    int update(Object[] objs,String addWhere) throws SQLException;

    /**
     * update multiple objects in to the underlying database, these objects may
     * not be instances of the same class. But each object must have a primary
     * key;
     *
     * @param objs the array of objects to be updated;
     * @return total count of object affected;
     * @throws SQLException
     */
    int updateMix(Object[] objs) throws SQLException;

    /**
     * update a table, set specified columns to specified new values;
     *
     * @param table the displayName of the table to be updated;
     * @param cols the columns to be udpated;
     * @param values the new values for each column.
     * @return count of rows affected;
     * @throws SQLException
     */
    int update(String table,String[] cols,Object[] values) throws SQLException;

    /**
     * update a table, set specified columns of specified rows to specified new values;
     *
     *
     * @param table the displayName of the table;
     * @param cols the columns whose values will be udpated;
     * @param values the new values for each columns to be updated;
     * @param whereCols columns that will specify which rows will be updated;
     * @param whereValues the values of each column in whereCols,to specify which rows to be udpated
     * @return count of rows will be updated;
     * @throws SQLException
     */
    int update(String table,String[] cols,Object[] values,String[] whereCols,Object[] whereValues)
            throws SQLException;

    /**
     * update a table, set specified columns of specified rows to specified new values;
     * the whereValues is a tow-dim array, let the length of the first dim is X, the length
     * of the second is Y. then this method will perform a prepared statement X times,each
     * time will set the values from whereValues[X-1][0] to whereValues[X-1][Y-1] to the pre-
     * pared statement.
     *
     * @param table the displayName of the table;
     * @param cols the columns whose values will be udpated;
     * @param values the new values for the columns to be updated;
     * @param whereCols columns that will specify which rows to be updated;
     * @param whereValues the values of WhereCols
     * @return the total count of rows affected;
     * @throws SQLException
     */
    int update(String table,String[] cols,Object[] values,String[] whereCols,Object[][] whereValues)
            throws SQLException;


    void addPredefinedStatement(BaseStatement bs);

    /**
     * begin a transaction with the specified isolation level;
     *
     * @throws Exception
     */
    void beginTrans(int level) throws Exception;


    /**
     * begin a transaction with the default isolation level;
     *
     * @throws Exception
     */
    void beginTrans() throws Exception;

    /**
     * commit the transaction;
     *
     * @throws Exception
     */
    void commit() throws Exception;

    /**
     * rollback the current transaction;
     *
     * @throws Exception
     */
    void rollback() throws Exception;

    /**
     * get the displayName of this DAO object;
     * @return
     */
    String getName();

    /**
     * get the type of the underlying database;
     * @see DBType
     * @return
     */
    String getDBType();

    /**
     * set the type of the underlying database;
     * @see DBType;
     * @param dbType
     */
    void setDBType(String dbType);

    /**
     * get the jndi displayName of the underly datasource;
     * @return the jndi displayName of the datasource;
     */
    String getDSJndiName();

    /**
     * set the jndi displayName of the undelying datasource;
     *
     * @param dsjnidName the new jndi displayName of the datasource;
     */
    void setDSJndiName(String dsjnidName);

    /**
     * get the initial context factory to lookup the datasource;
     * @return
     */
    String getInitialFactory();

    /**
     * set the initial context factory to loolup the datasource;
     * @param factory
     */
    void setInitialFactory(String factory);

    /**
     * get the provider url to lookup the datasource;
     * @return
     */
    String getProviderUrl();

    /**
     * set the provider url to loookup the datasource;
     * @param url
     */
    void setProviderUrl(String url);

    /**
     * get the user displayName to get connection from the datasouce;
     * @return the userName;
     */
    String getUser();

    /**
     * set the usernm to get connections from the datasouce;
     * @param user the new username;
     */
    void setUser(String user);

    /**
     * get the password of the user to get connections from the database;
     * @return the password;
     */
    String getPassword();

    /**
     * set the password to get from the Connections;
     *
     * @param password
     */
    void setPassword(String password);

    /**
     * lookup the datasource;
     * @throws NamingException
     */
    void lookup() throws NamingException;


    /**
     * get the object helper factory that is assoicated with this DAO object;
     *
     * @return
     */
    ObjectHelperFactory getObjectHelperFactory();
}