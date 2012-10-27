/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.database.sql;

/**
 * Description:
 * <p/>
 * Author: divine
 * Date: 2004-6-10
 */
public class TableAlias {
    private String table;

    private String alias;

    public TableAlias() {

    }

    public TableAlias(String table, String alias) {
        this.table = table;
        this.alias = alias;
    }

    public String getTable() {
        return this.table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
