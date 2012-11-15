/**
 *all rights reserved,@copyright 2003
 */
package com.cots.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-15
 * Time: 13:35:23
 * Version: 1.0
 */
public class RowSet {
    protected ArrayList rows;

    public RowSet() {
        rows = new ArrayList();
    }

    /**
     * get the count of rows in this set.
     *
     * @return count of rows in this set.
     */
    public int size() {
        return rows.size();
    }

    /**
     * get a row at a specified position.
     *
     * @param index the position to get the target row.
     * @return the target Row.
     */
    public Row rowAt(int index) {
        return (Row) rows.get(index);
    }

    /**
     * add a Row object to the end of this set.
     *
     * @param row the new Row to be added.
     */
    public void add(Row row) {
        rows.add(row);
    }

    /**
     * add a new Row at the specified index.
     *
     * @param index the position to add the new row;
     * @param row the new Row object to be added.
     */
    public void add(int index, Row row) {
        rows.add(index, row);
    }

    /**
     * get a column's value of a row.
     *
     * @param rowIndex the index of the row;
     * @param colName the displayName of the column.
     * @return the value of the column of the target row.
     */
    public Object getValue(int rowIndex, String colName) {
        Row row = rowAt(rowIndex);
        return row.getColumn(colName);
    }

    public Object getValue(int rowIndex,int colIndex){
        Row row = rowAt(rowIndex);
        return row.getColumn(colIndex);        
    }

    /**
     * set a column's value of a row.
     *
     * @param rowIndex the index of the target row;
     * @param colName the displayName of the column whose value will be set.
     * @param value the new value for the column of the row.
     */
    public void setValue(int rowIndex,String colName,Object value){
        Row row;
        if(rowIndex == rows.size()){
            row = new Row();
            rows.add(row);
        }else{
            row = (Row)rows.get(rowIndex);
        }

        row.setColumn(colName,value);
    }

    /**
     * get the list of all rows in this set;
     *
     * @return a List contains all the rows;
     */
    public List getRows() {
        return rows;
    }
}