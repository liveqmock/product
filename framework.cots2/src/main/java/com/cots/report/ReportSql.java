/**
 *all rights reserved,@copyright 2003
 */
package com.cots.report;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-4
 * Time: 10:06:35
 */
public class ReportSql {
    //the sql statement;
    private String sql;
    //the result displayName;
    private String resultName;
    //the dao in which to execute the sql;
    private String daoName;

    public ReportSql() {

    }

    public ReportSql(String sql, String daoName, String resultName) {
        this.sql = sql;
        this.daoName = daoName;
        this.resultName = resultName;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getResultName() {
        return this.resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public String getDaoName() {
        return this.daoName;
    }

    public void setDaoName(String daoName) {
        this.daoName = daoName;
    }
}
