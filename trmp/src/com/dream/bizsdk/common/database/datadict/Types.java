/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.database.datadict;


/**
 * <p>Title: engine</p>
 * <p>Description: This is the platform of the business development kit.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: dream</p>
 *
 * @author chuguanghua
 * @version 1.0
 */

public class Types {
    public final static int UNKNOWN = -1;
    public final static int BYTE = 1;
    public final static int SHORT = 2;
    public final static int INTEGER = 3;
    public final static int INT = 3;
    public final static int LONG = 4;
    public final static int FLOAT = 5;
    public final static int DOUBLE = 6;
    public final static int CHAR = 7;
    public final static int STRING = 8;
    public final static int TEXT = 11;
    public final static int BINARY = 12;
    public final static int DATE = 9;
    public final static int TIME = 10;
    public final static int TIMESTAMP = 13;
    public final static int BINARYSTREAM = 14;
    public final static int CHARSTREAM = 15;

    public final static int SINGLEVALUE = 100;
    public final static int VECTOR = 200;
    public final static int HASHTABLE = 300;
    public final static int OBJECT = 500;
    public final static int TABLE = 600;

    /**
     * convert the above type to JDBC type.
     *
     * @param type
     * @return
     */
    public final static int toJDBCType(int type) {
        switch (type) {
            case BYTE:
                return java.sql.Types.TINYINT;
            case SHORT:
                return java.sql.Types.SMALLINT;
            case INTEGER:
                return java.sql.Types.INTEGER;
            case LONG:
                return java.sql.Types.BIGINT;
            case FLOAT:
                return java.sql.Types.FLOAT;
            case DOUBLE:
                return java.sql.Types.DOUBLE;
            case CHAR:
                return java.sql.Types.CHAR;
            case STRING:
                return java.sql.Types.VARCHAR;
            case DATE:
                return java.sql.Types.DATE;
            case TIME:
                return java.sql.Types.TIME;
            case TIMESTAMP:
                return java.sql.Types.TIMESTAMP;
            default:
                return java.sql.Types.OTHER;
        }
    }

    public static int typeFromName(String name) {
        int type = 0;

        name = name.toLowerCase();
        if (name.compareToIgnoreCase(StrType.STRING) == 0) {
            type = Types.STRING;
        } else if (name.compareToIgnoreCase(StrType.BYTE) == 0) {
            type = Types.BYTE;
        } else if (name.compareToIgnoreCase(StrType.CHAR) == 0) {
            type = Types.CHAR;
        } else if (name.compareToIgnoreCase(StrType.SHORT) == 0) {
            type = Types.SHORT;
        } else if (name.compareToIgnoreCase(StrType.INT) == 0) {
            type = Types.INT;
        } else if (name.compareToIgnoreCase(StrType.LONG) == 0) {
            type = Types.LONG;
        } else if (name.compareToIgnoreCase(StrType.DOUBLE) == 0) {
            type = Types.DOUBLE;
        } else if (name.compareToIgnoreCase(StrType.DATE) == 0) {
            type = Types.DATE;
        } else if (name.compareToIgnoreCase(StrType.TIME) == 0) {
            type = Types.TIME;
        } else if (name.compareToIgnoreCase(StrType.TIMESTAMP) == 0) {
            type = Types.TIMESTAMP;
        } else if (name.compareToIgnoreCase(StrType.BINARY) == 0) {
            type = Types.BINARY;
        } else if (name.compareToIgnoreCase(StrType.TEXT) == 0) {
            type = Types.TEXT;
        } else if (name.compareToIgnoreCase(StrType.OTHER) == 0) {
            type = Types.UNKNOWN;
        } else if (name.compareToIgnoreCase(StrType.BINARYSTREAM) == 0) {
            type = Types.BINARYSTREAM;
        } else if (name.compareToIgnoreCase(StrType.CHARSTREAM) == 0) {
            type = Types.CHARSTREAM;
        } else if (name.compareToIgnoreCase(StrType.FLOAT) == 0) {
            type = Types.FLOAT;
        } else {
            type = Types.UNKNOWN;
        }
        return type;
    }

    /**
     * convert a JDBC column type to a string;
     *
     * @param colName
     * @param jdbcType
     * @return
     */
    protected static String translateJDBCType(String colName, int jdbcType) {
        String realType = null;
        switch (jdbcType) {
            case java.sql.Types.BIGINT:
                realType = StrType.LONG;
                break;
            case java.sql.Types.BINARY:
                realType = StrType.BINARY;
                break;
            case java.sql.Types.BIT:
                realType = StrType.BYTE;
                break;
            case java.sql.Types.BLOB:
                realType = StrType.BINARYSTREAM;
                break;
            case java.sql.Types.LONGVARBINARY:
                realType = StrType.BINARYSTREAM;
                break;
            case java.sql.Types.LONGVARCHAR:
                realType = StrType.CHARSTREAM;
                break;
            case java.sql.Types.CHAR:
                realType = StrType.CHAR;
                break;
            case java.sql.Types.CLOB:
                realType = StrType.TEXT;
                break;
            case java.sql.Types.DATE:
                //if (colName.toLowerCase().endsWith("date")||colName.toLowerCase().endsWith("rq")) {
                    realType = StrType.DATE;
                //} else {
                //    realType = StrType.TIME;
                //}
                break;
            case java.sql.Types.DECIMAL:
                realType = StrType.DOUBLE;
                break;
            case java.sql.Types.DOUBLE:
                realType = StrType.DOUBLE;
                break;
            case java.sql.Types.FLOAT:
                realType = StrType.FLOAT;
                break;
            case java.sql.Types.INTEGER:
                realType = StrType.INT;
                break;
            case java.sql.Types.NUMERIC:
                realType = StrType.DOUBLE;
                break;
            case java.sql.Types.REAL:
                realType = StrType.FLOAT;
                break;
            case java.sql.Types.SMALLINT:
                realType = StrType.SHORT;
                break;
            case java.sql.Types.TIME:
                //if (colName.toLowerCase().endsWith("date")) {
                //   realType = StrType.DATE;
                //} else {
                    realType = StrType.TIME;
                //}
                break;
            case java.sql.Types.TIMESTAMP:
                /*if (colName.toLowerCase().endsWith("date")) {
                    realType = StrType.DATE;
                } else {
                    realType = StrType.TIME;
                }*/
            	realType = StrType.TIMESTAMP;
                break;
            case java.sql.Types.VARCHAR:
                realType = StrType.STRING;
                break;
            case java.sql.Types.TINYINT:
                realType = StrType.SHORT;
                break;
            case java.sql.Types.VARBINARY:
                realType = StrType.BINARY;
                break;
            default:
                realType = StrType.OTHER;
        }
        return realType;
    }
}