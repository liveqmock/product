package com.dream.bizsdk.common.databus;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * User: Administrator
 * Date: 2004-10-3
 * Time: 22:45:42
 */
public class Bizdata2 extends BaseData {
    Map tables = new HashMap();

    public Bizdata2() {
    }

    /**
     * @return
     */
    public int countTables() {
        return tables.size();
    }

    /**
     * get an existing table;
     *
     * @param name the name of the table;
     * @return the Table object, null if not exists;
     */
    public Table getTable(String name) {
        return (Table) tables.get(name);
    }

    /**
     * fetch a table from this bizdata object, create it if not exists;
     *
     * @param name the name of the table to be fetched;
     * @return
     */
    public Table fetchTable(String name) {
        Table t = (Table) tables.get(name);
        if (t == null) {
            t = new Table(name);
            tables.put(name, t);
        }
        return t;
    }

    /**
     * create a new Table will overwride existing one;
     *
     * @param name the name of the table;
     * @return  Table object;
     */
    public Table newTable(String name) {
        Table t = new Table(name);
        tables.put(name, tables);
        return t;
    }

    /**
     *
     * @return
     */
    public Table[] getTables() {
        return (Table[]) tables.values().toArray(new Table[tables.size()]);
    }

    /**
     *
     * @param table
     * @param row
     * @param field
     * @return
     */
    public String getString(String table, Object row, String field) {
        Object t = get(table);
        if (t instanceof Table) {
            Row r = ((Table) t).getRow(row);
            if (r != null) {
                return r.getString(field);
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * @param name
     * @param index
     * @return
     */
    public String getString(String name, int index) {
        Object t = get(name);
        if (t instanceof List) {
            return ((List) t).get(index).toString();
        } else {
            return "";
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public int getArrayLength(String name) {
        Object t = get(name);
        if (t instanceof List) {
            return ((List) t).size();
        } else {
            return 0;
        }
    }
}
