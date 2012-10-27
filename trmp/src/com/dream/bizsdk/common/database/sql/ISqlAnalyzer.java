/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.database.sql;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-6-10
 * Time: 12:02:43
 */
public interface ISqlAnalyzer {

    public TableAlias[] getTableAlias(String select);

    public ColumnAlias[] getColumnAlias(String select);

}
