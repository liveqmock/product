/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.database.sql;

import com.dream.bizsdk.common.database.datadict.*;
import com.dream.bizsdk.common.database.dao.UserDataPrivil;
import com.dream.bizsdk.common.databus.*;

import java.util.*;

/**
 * <p>Title: engine</p>
 * <p>Description: This is the platform of the business development kit.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: dream</p>
 *
 * @author chuguanghua
 * @version 1.0
 */
public abstract class ISQLBuilder {

    public static final String SQL = "sql";
    public static final String ORA = "oracle";
    public static final String MYSQL = "mysql";

    protected String AND = " and ";
    protected String daoName;
    protected Hashtable funcs = new Hashtable();
    protected Hashtable funcsMap = new Hashtable();

    public abstract String buildInsertSQL(String name, BizData data, DataDict dc, Vector fieldsInDB, Vector types);

    public abstract String buildDeleteSQL(String name, String row, BizData data, BizData sd, DataDict dc);

    public abstract String buildUpdateSQL(String name, String row, boolean setNull, ArrayList al, BizData data, BizData sd, DataDict dc);

    public abstract String buildSelectSQL(String name, String row, boolean allFields, BizData data, BizData sd, DataDict dc);

    public abstract String buildSelectSQL(String name, String row, boolean allFields, BizData data, BizData sd, DataDict dc, StringBuffer where, int pageNO, int pageSize);

    public abstract SQLFunction checkFunc(String funcName);

    public ISQLBuilder() {

    }

    public ISQLBuilder(String daoName) {
        this.daoName = daoName;
    }


    /**
     * parse the field name;
     *
     * @param fieldName  the name of the field;
     * @param columnName the name of the column;
     */
    public String parseFieldName(String fieldName, String columnName) {
        if (fieldName == null || columnName == null) {
            return null;
        }
        fieldName = replaceFunc(fieldName);
        StringBuffer buffer = new StringBuffer(fieldName);
        int pos = -1;
        pos = buffer.indexOf("?");
        while (pos >= 0) {
            buffer.replace(pos, pos + 1, columnName);
            pos = buffer.indexOf("?");
        }
        return new String(buffer);
    }

    /**
     * generata a new Instance of this Class;
     * currently supports Oracle database,SQL Server database,MySQL database.???????
     *
     * @param dbType the database type;
     */
    public static ISQLBuilder newInstance(String dbType, String daoName) {
        if (dbType.compareToIgnoreCase(ISQLBuilder.SQL) == 0) {
            return new SqlSQLBuilder(daoName);
        } else if (dbType.compareToIgnoreCase(ISQLBuilder.ORA) == 0) {
            return new OraSQLBuilder(daoName);
        } else if (dbType.compareToIgnoreCase(ISQLBuilder.MYSQL) == 0) {
            return new MySqlSQLBuilder(daoName);
        }
        return null;
    }

    /**
     * replace ' by ''.
     */
    public String replaceQuota(String src) {
        if (src == null) {
            return null;
        } else {
            return src.replaceAll("'", "''");
        }
    }

    /**
     * replace function name;
     */
    public String replaceFunc(String src) {
        if (src == null) {
            return null;
        } else {
            Enumeration keys = funcsMap.keys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                String value = (String) funcsMap.get(key);
                src = src.replaceAll(key, value);
            }
        }
        return src;
    }

    /**
     * @param sd
     * @param table
     * @return
     */
    protected final String getSubWhere(BizData sd, String table) {
        String subWhere = null;
        if (sd != null) {
            HashMap ups = (HashMap) sd.get("_dataPrivil");
            if (ups != null) {
                UserDataPrivil udp = (UserDataPrivil) ups.get(daoName);  //should be daoName;
                if (udp != null) {
                    subWhere = udp.getRowPrivil(table);
                }
            }
        }
        return subWhere;
    }
}