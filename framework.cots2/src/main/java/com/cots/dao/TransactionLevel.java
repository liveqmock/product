/**
 *all rights reserved,@copyright 2003
 */
package com.cots.dao;

import java.sql.Connection;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-13
 * Time: 10:39:09
 * Version: 1.0
 */
public class TransactionLevel {
    public final static String NONE = "none";
    public final static String READ_COMMITED = "read_commited";
    public final static String READ_UNCOMMITED = "read_uncommited";
    public final static String REPEATABLE_READ = "repeatable_read";
    public final static String SERIALIZABLE = "serializable";

    /**
     * convert String leven to int level;
     *
     * @param level
     * @return
     */
    public static int strToInt(String level) {
        if (SERIALIZABLE.equalsIgnoreCase(level)) {
            return Connection.TRANSACTION_SERIALIZABLE;
        } else if (READ_COMMITED.equalsIgnoreCase(level)) {
            return Connection.TRANSACTION_READ_COMMITTED;
        } else if (READ_UNCOMMITED.equalsIgnoreCase(level)) {
            return Connection.TRANSACTION_READ_UNCOMMITTED;
        } else if (REPEATABLE_READ.equalsIgnoreCase(level)) {
            return Connection.TRANSACTION_REPEATABLE_READ;
        } else {
            return Connection.TRANSACTION_NONE;
        }
    }
}
