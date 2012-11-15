/**
 *all rights reserved,@copyright 2003
 */
package com.cots.dao;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-10-29
 * Time: 9:04:48
 * Version:1.0
 */
public class DBType {
    /**
     * db identifer;
     */
    public static final int ORACLE = 1;
    public static final int SQLSERVER = 2;
    public static final int MYSQL = 3;
    public static final int DB2 = 4;
    public static final int UNKNOWN = -1;

    /**
     * db displayName;
     */
    public static final String ORACLE_DB = "oracle";
    public static final String SQLSERVER_DB = "sqlserver";
    public static final String MYSQL_DB = "mysql";
    public static final String DB2_DB = "db2";
    public static final String UNKNOWN_DB = "unknown";


    /**
     *
     * @param name
     * @param type
     * @return
     */
    public static String buildName(String name, String type) {
        if (SQLSERVER_DB.equalsIgnoreCase(type)) {
            return "[" + name + "]";
        } else {
            return name;
        }
    }

    /**
     * convert db Name to db ID;
     *
     * @param dbType
     * @return
     */
    public static int getDBID(String dbType) {
        if (ORACLE_DB.equalsIgnoreCase(dbType)) {
            return ORACLE;
        } else if (SQLSERVER_DB.equalsIgnoreCase(dbType)) {
            return SQLSERVER;
        } else if (MYSQL_DB.equalsIgnoreCase(dbType)) {
            return MYSQL;
        } else if (DB2_DB.equalsIgnoreCase(dbType)) {
            return DB2;
        } else {
            return UNKNOWN;
        }
    }

    /**
     * convert db ID to db Name;
     *
     * @param id
     * @return
     */
    public static String getDBName(int id) {
        switch (id) {
            case ORACLE:
                return ORACLE_DB;
            case SQLSERVER:
                return SQLSERVER_DB;
            case MYSQL:
                return MYSQL_DB;
            case DB2:
                return DB2_DB;
            default:
                return UNKNOWN_DB;
        }
    }
}