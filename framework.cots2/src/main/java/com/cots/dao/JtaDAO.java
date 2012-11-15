package com.cots.dao;

import com.cots.dao.helper.ObjectHelperFactory;

import javax.sql.DataSource;
import javax.transaction.*;
import java.sql.*;

/**
 * Cots framework
 * <p/>
 * User: chugh
 * Date: 2004-12-28
 * Time: 14:38:08
 * Version: 1.0
 */
public class JtaDAO extends JdbcDAO {

    private UserTransaction userTransaction;

    public JtaDAO(String name, ObjectHelperFactory helperFactory) {
        super(name,helperFactory);
    }

    public JtaDAO(String name, String dbType, DataSource dataSource, ObjectHelperFactory helperFactory) {
        super(name,dbType,dataSource,helperFactory);
    }

    public UserTransaction getUserTransaction() {
        return userTransaction;
    }

    public void setUserTransaction(UserTransaction userTransaction) {
        this.userTransaction = userTransaction;
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
        try {
            userTransaction.begin();
        } catch (NotSupportedException e) {
            throw new SQLException("not supportd exception:"+e.getMessage());
        } catch (SystemException e) {
            throw new SQLException("system exception:"+e.errorCode);
        }
    }

    /**
     * commit current transaction;
     *
     * @throws SQLException
     */
    public void commit() throws SQLException {
        try {
            userTransaction.commit();
        } catch (RollbackException e) {
            throw new SQLException("RollbackException"+e.getMessage());
        } catch (HeuristicMixedException e) {
            throw new SQLException("HeuristicMixedException: "+e.getMessage());
        } catch (HeuristicRollbackException e) {
            throw new SQLException("HeuristicRollbackException: "+e.getMessage());
        } catch (SystemException e) {
            throw new SQLException("system exception:"+e.errorCode);
        }
    }

    /**
     * rollback current transaction;
     *
     * @throws SQLException
     */
    public void rollback() throws SQLException {
        try {
            userTransaction.rollback();
        } catch (SystemException e) {
            throw new SQLException("system exception:"+e.errorCode);
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
        return getConnection();
    }


    /**
     * close a connection used by current Thread;
     *
     * @param conn the Connection to be closed;
     * @throws SQLException
     */
    public void threadCloseConnection(Connection conn) throws SQLException {
        conn.close();
    }
}
