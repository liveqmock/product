package com.cots.dao;

import com.cots.dao.helper.ObjectHelper;
import com.cots.dao.helper.ObjectHelperFactory;
import com.cots.bean.Bean;

import javax.sql.DataSource;
import javax.naming.NamingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.sql.*;
import java.util.Properties;
import java.util.List;
import java.io.*;

/**
 * Cots framework
 * <p/>
 * User: chugh
 * Date: 2004-12-28
 * Time: 15:02:27
 * Version: 1.0
 */
public abstract class AbstractDAO implements DAO {
    //displayName of the dao;
    protected String name;

    //the target dao type, see DBType.
    protected String dbType = DBType.ORACLE_DB;

    //the jndi displayName of the datasource;
    protected String jndiName;

    //jndi factory used to lookup the datasource;
    protected String initialFactory;

    //the jndi provider url used to lookup the datasource;
    protected String providerUrl;

    //the underlying datasource;
    protected DataSource dataSource;

    //the user displayName of the datasource;
    protected String user;

    //the password of the datasource;
    protected String password;

    //the database jdbc url;
    protected String url;

    //the database jdbc driver;
    protected String driver;

    //whether to manage the transaction isolation level;
    protected boolean usingIsolation;

    //the default transaction isolation level;
    protected int defaultIsolation = Connection.TRANSACTION_READ_COMMITTED;

    //the object helper factory, will automatically generate
    //helper class and compile it.
    protected ObjectHelperFactory helperFactory;

    public AbstractDAO() {

    }

    public AbstractDAO(ObjectHelperFactory factory) {
        this.helperFactory = factory;
    }

    /**
     * get the displayName of this LocalDAO object;
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * get the db type of the LocalDAO;
     *
     * @return
     */
    public String getDBType() {
        return dbType;
    }
                                                
    /**
     * set the db type;
     *
     * @param dbType
     */
    public void setDBType(String dbType) {
        this.dbType = dbType;
    }

    /**
     * get the datasource jndi displayName;
     *
     * @return
     */
    public String getDSJndiName() {
        return jndiName;
    }

    /**
     * set the jndi displayName of the datasouce;
     *
     * @param jndiName the jndi displayName of the underlying datasource;
     */
    public void setDSJndiName(String jndiName) {
        this.jndiName = jndiName;
    }

    /**
     * get the jdbc ur;
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * set the jdbc url;
     *
     * @param url the new jdbc url; 
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * get the jdbc driver;
     *
     * @return the jdbc driver;
     */
    public String getDriver() {
        return driver;
    }

    /**
     * set the jdbc driver;
     *
     * @param driver the new jdbc driver;
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * return wether isolation is using;
     *
     * @return true if the isolation is using, false otherwise;
     */
    public boolean isUsingIsolation() {
        return usingIsolation;
    }

    /**
     * get the default transaction isolation level;
     *
     * @return the default isolation;
     */
    public int getDefaultIsolation() {
        return defaultIsolation;
    }

    /**
     * set the default isolation;
     *
     * @param defaultIsolation the new isolation;
     */
    public void setDefaultIsolation(int defaultIsolation) {
        this.defaultIsolation = defaultIsolation;
    }

    /**
     * set if the isolation is used.
     *
     * @param usingIsolation
     */
    public void setUsingIsolation(boolean usingIsolation) {
        this.usingIsolation = usingIsolation;
    }

    /**
     * get the initial factory to lookup the DataSource.
     *
     * @return
     */
    public String getInitialFactory() {
        return initialFactory;
    }

    /**
     * set the initial Factory used to lookup the target Datasouce. Generally, if the datasource
     * exists in the same JVM, the factory does not need to be set;
     *
     * @param initialFactory the initial factory needed to look up the datasouce;
     */
    public void setInitialFactory(String initialFactory) {
        this.initialFactory = initialFactory;
    }

    /**
     * get the provider url.
     *
     * @return
     */
    public String getProviderUrl() {
        return providerUrl;
    }

    /**
     * set the provider url to lookup the datasource;
     *
     * @param providerUrl the provider url used to lookup the datasource;
     */
    public void setProviderUrl(String providerUrl) {
        this.providerUrl = providerUrl;
    }

    /**
     * get the username used to get a connection from the underlying datasource;
     *
     * @return the user displayName.
     */
    public String getUser() {
        return user;
    }

    /**
     * set the username used to get a connection from the underlying datasouce;
     *
     * @param user the user displayName to be set;
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * get the password used to get a connection from the underlying datasouce;
     *
     * @return the password string;
     */
    public String getPassword() {
        return password;
    }

    /**
     * set the password used to get a connection from the underlying datasource;
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * lookup the datasource;
     *
     * @throws javax.naming.NamingException
     */
    public void lookup() throws NamingException {
        Properties props = new Properties();
        if (initialFactory != null && initialFactory.length() > 0) {
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY, initialFactory);
        }
        if (providerUrl != null && providerUrl.length() > 0) {
            props.setProperty(Context.PROVIDER_URL, providerUrl);
        }

        if(jndiName!=null && jndiName.length()>0){
            InitialContext ic = new InitialContext(props);
            dataSource = (DataSource) ic.lookup(jndiName);
        }else{
            CotsDataSource ds = new CotsDataSource();
            ds.setUrl(url);
            ds.setDriver(driver);
            ds.setUser(user);
            ds.setPassword(password);
            ds.setMaxSize(50);
            dataSource = ds;
        }
    }

    /**
     * get the object helper factory associated with this DAO object;
     *
     * @return  an ObjectHelperFactory object
     */
    public ObjectHelperFactory getObjectHelperFactory(){
        return this.helperFactory;
    }

    /**
     * the datasource assoicated with this LocalDAO object.
     *
     * @return javax.sql.DataSource object.
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * get a connection from the undelying datasource;
     *
     * @return java.sql.Connection;
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        if (user != null) {
            return dataSource.getConnection(user, password);
        } else {
            return dataSource.getConnection();
        }
    }



    /**
     * Execute a executeQuery on a POJO object.
     *
     * @param beanClz the class of the POJO
     * @param where   the executeQuery criteria,should not contains the "where" keyword;
     * @param list    the list to store the result POJOs
     * @param conn    the Connection used to executeQuery pojos;
     * @return count of POJO object have been met in this executeQuery;
     * @throws SQLException
     */
    public int executeQuery(Class beanClz, String where, List list,
                            Connection conn) throws SQLException {
        Bean bean = helperFactory.getBeanFactory().getByClass(beanClz);
        if (bean == null) {
            throw new SQLException("not cots managed class " + beanClz);
        }

        ObjectHelper oh = helperFactory.getObjectHelper(beanClz);
        String tableName = bean.getTableName();
        String sql = "select "+bean.getColumnNamesString()+" from " + DBType.buildName(tableName, dbType);
        if (where != null && where.length() > 0) {
            sql += " where " + where;
        }

        Statement stmt = conn.createStatement();
        try {
            ResultSet rs = stmt.executeQuery(sql);
            return oh.populateBatch(list, rs, this);
        } finally {
            stmt.close();
        }
    }

    /**
     *
     *
     * @param beanClz
     * @param where
     * @param orderBy
     * @param list
     * @param conn
     * @return
     * @throws SQLException
     */
    public int executeQuery(Class beanClz, String where, String orderBy,
                            List list, Connection conn) throws SQLException {
        Bean bean = helperFactory.getBeanFactory().getByClass(beanClz);
        if (bean == null) {
            throw new SQLException("not cots managed class " + beanClz);
        }

        ObjectHelper oh = helperFactory.getObjectHelper(beanClz);
        String tableName = bean.getTableName();
        String sql = "select "+bean.getColumnNamesString()+" from " + DBType.buildName(tableName, dbType);
        if (where != null && where.length() > 0) {
            sql += " where " + where;
        }

        if (orderBy != null && orderBy.length() > 0) {
            sql += " order by " + orderBy;
        }

        Statement stmt = conn.createStatement();;
        try {

            ResultSet rs = stmt.executeQuery(sql);
            int count = oh.populateBatch(list, rs, this);

            if (IDAOAccessor.class.isAssignableFrom(beanClz)) {
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    ((IDAOAccessor) list.get(i)).setDAO(this);
                }
            }
            return count;
        } finally {
            stmt.close();
        }
    }

    /**
     * execute a sql and return a RowSet object.
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public RowSet queryRowSet(String sql, Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        try {
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();

            int colCount = rsmd.getColumnCount();
            String[] cols = new String[rsmd.getColumnCount()];
            for (int i = 0; i < colCount; i++) {
                cols[i] = rsmd.getColumnName(i+1).toLowerCase();
            }

            RowSet rowSet = new RowSet();
            while (rs.next()) {
                Row row = new Row();
                for (int i = 0; i < colCount; i++) {
                    Object colValue = rs.getObject(i + 1);

                    //check if Clob object or Blob object;
                    if (colValue instanceof Clob) {
                        colValue = ((Clob) colValue).getCharacterStream();
                    } else if (colValue instanceof Blob) {
                        colValue = ((Blob) colValue).getBinaryStream();
                    }

                    if (colValue instanceof InputStream) {
                        int readCount = 0;
                        byte[] buf = new byte[4096];
                        InputStream is = (InputStream) colValue;
                        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
                        while ((readCount = is.read(buf)) >= 0) {
                            bos.write(buf, 0, readCount);
                        }
                        colValue = bos.toByteArray();
                        bos.close();
                    } else if (colValue instanceof Reader) {
                        int readCount = 0;
                        char[] buf = new char[4096];
                        Reader is = (Reader) colValue;
                        CharArrayWriter bos = new CharArrayWriter(1024);
                        while ((readCount = is.read(buf)) >= 0) {
                            bos.write(buf, 0, readCount);
                        }
                        colValue = bos.toString();
                        bos.close();
                    }

                    row.setColumn(cols[i], colValue);
                }
                rowSet.add(row);
            }
            return rowSet;
        } catch (IOException e) {
            throw new SQLException(e.getMessage());
        } finally {
            stmt.close();
        }
    }

    /**
     * inser a POJO object via a Connection;
     *
     * @param obj
     * @param conn
     * @return
     * @throws SQLException
     */
    protected int insert(Object obj, Connection conn) throws SQLException {
        ObjectHelper helper = helperFactory.getObjectHelper(obj);
        return helper.insert(obj, conn);
    }

    /**
     * delete a POJO object via a Connection;
     *
     * @param obj
     * @param conn
     * @return
     * @throws SQLException
     */
    protected int delete(Object obj, Connection conn) throws SQLException {
        ObjectHelper helper = helperFactory.getObjectHelper(obj);
        return helper.delete(obj, conn,null);
    }

    /**
     * update a POJO object via a Connection;
     *
     * @param obj  the POJO object to be updated, the POJO class must have been registered to cots;
     * @param conn the Connection used to update the POJO object;
     * @return rows affected;
     * @throws SQLException
     */
    protected int update(Object obj, Connection conn) throws SQLException {
        ObjectHelper helper = helperFactory.getObjectHelper(obj);
        return helper.delete(obj, conn,null);
    }

    /**
     * query a page from a ResultSet.
     *
     * @param conn
     * @param sql         the sql must like "select a.* from Table1 a,Table2 b where...."
     * @param start
     * @param rowsPerPage
     * @param beanCls
     * @param results
     * @return
     * @throws SQLException
     */
    final int queryPage(Connection conn, String sql, int start, int rowsPerPage,
                        Class beanCls, List results)throws SQLException {
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        try {
            ResultSet rs = stmt.executeQuery(new String(sql));

            rs.setFetchSize(50);    //set the fetch size; maybe the 50 is not the best value.

            int count = 0;
            //rs.absolute(start) may return false, if the Result Set have be changed since the
            //previous countRows(...) operation;
            if (rs.absolute(start)) {
                ObjectHelper oh = helperFactory.getObjectHelper(beanCls);
                do {
                    Object obj = oh.populate(rs, this);
                    results.add(obj);
                } while ((rs.next() && (++count) < rowsPerPage));
            }
            rs.close();
            return count;
        } finally {
            stmt.close();
        }
    }

    /**
     * @param conn
     * @param sql  must be a sql like "select count(*|column) from ...."
     * @return count of the results;
     * @throws SQLException
     */
    final int countRows(Connection conn, String sql) throws SQLException {
        Statement stmt = conn.createStatement();
        try {
            ResultSet rs = stmt.executeQuery(sql);
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            return count;
        } finally {
            stmt.close();
        }
    }

    /**
     * compute which record to start;
     *
     * @param rowsCount total rows that current query hits;
     * @param page the Page condition;
     * @return the starting position to fetch records;
     */
    final int computePage(int rowsCount, Page page) {

        int rowsPerPage = page.getRowsPerPage();
        int pages = rowsCount / rowsPerPage + ((rowsCount % rowsPerPage) == 0 ? 0 : 1);
        int start = 1;


        if (page.isLast()) { //goto the last page directly;
            start = (pages - 1) * rowsPerPage + 1;
            page.setLast(true);
            page.setCurrent(pages);
        } else {  //goto a specific page;
            int current = page.getCurrent();
            if (current < 1) {
                page.setCurrent(current = 1);
            }
            start = (current - 1) * rowsPerPage + 1;
            if (start > rowsCount) {
                start = (pages - 1) * rowsPerPage + 1;
                page.setLast(true);
                page.setCurrent(pages);
            } else {
                page.setLast(false);
            }
        }
        return start;
    }

    /**
     * get from clause;
     *
     * @param sql
     * @return
     */
    final String getFromClause(String sql) {
        if(sql == null || sql.length()<1){
            return "";
        }else{
            int index = sql.toLowerCase().indexOf(" from ");
            if (index >= 0) {
                return sql.substring(index);
            } else {
                return "";
            }
        }
    }
}