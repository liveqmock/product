/**
 *all rights reserved,@copyright 2003
 */

package com.dream.bizsdk.common.database.dblink;

import com.dream.bizsdk.common.pool.*;

import java.sql.*;

/**
 * Title:        engine
 * Description:  This is the platform of the business development kit.
 * Copyright:    Copyright (c) 2002
 * Company:      dream
 *
 * @author chuguanghua
 * @version 1.0
 */

public class DBLinksPool extends GenericPool {

    /**
     * Load all the JDBC drivers here.
     * <p/>
     * Because this pool will support serveral
     * kinds of database system(but all the connections in a pool can only be
     * connected to one database), so here we will load all the drivers we
     * support, if some of these drivers can't be load,that is Exception is
     * thrown, we will catch this Exception and continue to load the other
     * drivers.
     * <p/>
     * If the drvier for the target database is load, then the POOL can
     * establish connections to the database. But if the driver is not loaded for
     * the target databse, then no connection to the database can be established
     * in the pool.
     */
    public final static int DEFAULT_CONNECTIONS = 10;

    //private member variables;
    //default url user and pwd.

    private String url = "jdbc:microsoft:sqlserver://127.0.0.1:1433;DatabaseName=DEVDB;selectmethod=cursor";
    private String user = "sa";
    private String pwd = "erp";

    /**
     * custom consturctor 1
     */
    public DBLinksPool(String url, String user, String password) throws Exception {
        this.url = url;
        this.user = user;
        this.pwd = password;
        create();
        if (size() < 1) {
            throw new SQLException("Can't establish connection to the database.");
        }
    }

    /**
     * Construct a new DBLinksPool object to a specified database and establish
     * a specific count of Connections to the database.
     *
     * @param url
     * @param user
     * @param password
     * @param size
     * @throws SQLException
     */
    public DBLinksPool(String url, String user,
                       String password, int size) throws Exception {
        this.url = url;
        this.user = user;
        this.pwd = password;
        this.initialSize = size;

        create();
        if (size() < 1) {
            throw new SQLException("Can't establish connection to the database.");
        }
    }

    /**
     * Override parent's method to establish a Connection object and
     * create a DBLink object.
     *
     * @return PoolItem reference to a DBLink Object;
     */
    public PoolItem newPoolItem() throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(url, user, pwd);
        return new ConnectionImpl(this, conn);
    }

    /**
     * Test if the database is down.??????
     *
     * @return true if yes.
     */
    protected boolean isDBDown() {
        return false;
    }
}