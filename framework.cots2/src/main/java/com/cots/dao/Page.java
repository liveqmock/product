/**
 *all rights reserved,@copyright 2003
 */
package com.cots.dao;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-25
 * Time: 17:22:48
 * Version: 1.0
 */
public final class Page {
    private int current;
    private int total;
    private int rowsPerPage = 10;
    private int totalRows;

    private boolean last;

    /**
     * create to executeQuery the first page;
     */
    public Page(){
    }

    /**
     * create to executeQuery the last page;
     *
     * @param last
     */
    public Page(boolean last){
        this.last = last;
    }

    /**
     * create to executeQuery the specified page;
     *
     * @param current
     */
    public Page(int current){
        this.current = current;
    }

    /**
     * current page no to executeQuery;
     *
     * @return current page no;
     */
    public int getCurrent() {
        return current;
    }

    /**
     * set the current page no;
     *
     * @param current
     */
    public void setCurrent(int current) {
        this.current = current;
    }

    /**
     * get the count of total pages;
     *
     * @return
     */
    public int getTotal() {
        return total;
    }

    /**
     * set the count of total pages;
     *
     * @param total the count of total pages;
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * check if the current page is the first page;
     * @return
     */
    public boolean isFirst() {
        return current==1;
    }

    /**
     *
     * @param first
     */
    public void setFirst(boolean first) {

    }

    /**
     * check if the current page is the last page;
     * @return
     */
    public boolean isLast() {
        return last;
    }

    /**
     * check if the current page is the last page;
     * @param last
     */
    public void setLast(boolean last) {
        this.last = last;
    }

    /**
     * get rows per page;
     * @return
     */
    public int getRowsPerPage() {
        return rowsPerPage;
    }

    /**
     * set the rows per page;
     * @param rowsPerPage
     */
    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    /**
     * get the count of total rows;
     *
     * @return count of total rows;
     */
    public int getTotalRows() {
        return totalRows;
    }

    /**
     * total rows of current query;
     *
     * @param totalRows count of total rows;
     */
    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }
}