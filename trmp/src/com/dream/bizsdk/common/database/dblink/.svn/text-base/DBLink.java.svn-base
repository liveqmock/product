/**
 *all rights reserved,@copyright 2003
 */

package com.dream.bizsdk.common.database.dblink;

import com.dream.bizsdk.common.pool.PoolItem;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Title:        engine
 * Description:
 * Generally, a DBLink object exits in a pool. But it can also be created without a
 * Pool, in this case, the DBlink object is called isolated DBLink.
 * <p/>
 * Copyright:    Copyright (c) 2002
 * Company:      dream
 *
 * @author chuguanghua
 * @version 1.0
 */

public class DBLink extends PoolItem {
    private Connection conn;
    private boolean isolated;

    public DBLink(Connection conn) {
        this.conn = conn;
        isolated = true;
    }

    public DBLink(DBLinksPool pool, Connection conn) {
        super(pool);
        this.conn = conn;
    }

    /**
     * set the connection to the object
     *
     * @param newConn the Connection object contained in this DBLink;
     */
    public void setConn(Connection newConn) {
        //close the previous connection if necessary;
        if (null != conn) {
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        conn = newConn;
    }

    /**
     * get the connection object;
     *
     * @return the Connection object in this DBLink object;
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * check if this dblink is isolated;
     *
     * @return
     */
    public boolean isIsoloated() {
        return isolated;
    }

    /**
     * release this DBLlink object and close the underlying Connection
     * object at the same time.
     */
    public void release() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }
    }

    /**
     * free this dblink object;
     */
    public void free() {
        if (isolated) {
            //free the connection object associated with this DBLink object;
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        } else {
            super.free();
        }
    }
}