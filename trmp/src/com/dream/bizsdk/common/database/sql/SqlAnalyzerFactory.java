/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.database.sql;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-6-10
 * Time: 12:04:32
 */
public class SqlAnalyzerFactory {
    public static ISqlAnalyzer getAnalyzer(String dbType) {
        if (dbType == null) {
            return null;
        } else {
            if (dbType.compareToIgnoreCase(ISQLBuilder.ORA) == 0) {
                return null;
            } else if (dbType.compareToIgnoreCase(ISQLBuilder.SQL) == 0) {
                return null;
            } else if (dbType.compareToIgnoreCase(ISQLBuilder.MYSQL) == 0) {
                return null;
            } else {
                return null;
            }
        }
    }
}
