/**
 *all rights reserved,@copyright 2003
 */
package com.cots.dao;

import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-15
 * Time: 13:35:16
 * Version: 1.0
 */
public class Row {
    //displayName of the row object;
    protected String name;
    //columns values of the row;
    protected HashMap values;

    protected ArrayList valuesList;

    public Row() {
        values = new HashMap();
        valuesList = new ArrayList();
    }

    public Row(String name) {
        this.name = name;
        values = new HashMap();
        valuesList = new ArrayList();
    }

    /**
     * get the displayName of the row;
     *
     * @return the row's displayName;
     */
    public String getName() {
        return name;
    }

    /**
     * set the displayName of the row;
     *
     * @param name the new displayName of the row;
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the value of a column of the specified displayName;
     *
     * @param col the displayName of the column;
     * @return the value of the column;
     */
    public Object getColumn(String col) {
        return values.get(col);
    }

    /**
     * get the value of a column at the specified index;
     * @param col
     * @return
     */
    public Object getColumn(int col) {
        return valuesList.get(col);
    }

    /**
     * set the column value;
     *
     * @param col
     * @param value
     * @return
     */
    public Object setColumn(String col, Object value) {
        valuesList.add(value);
        return values.put(col, value);
    }

    /**
     * get the count of columns of this row;
     *
     * @return
     */
    public int countCols() {
        return values.size();
    }

    /**
     * remove a column.
     *
     * @param col
     * @return
     */
    public Object remove(String col) {
        return values.remove(col);
    }

    /**
     * return the set of the column values;
     *
     * @return
     */
    public Set columns() {
        return values.keySet();
    }
}