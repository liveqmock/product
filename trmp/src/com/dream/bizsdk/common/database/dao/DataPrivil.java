/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.database.dao;

import com.dream.bizsdk.common.databus.BizData;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;

import org.apache.log4j.Logger;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-6-11
 * Time: 9:36:42
 */
public class DataPrivil {
    private DAO dao;
    private BizData rowPrivil;
    private BizData colPrivil;
    private Logger log = Logger.getLogger(DataPrivil.class);

    public DataPrivil() {

    }

    public DataPrivil(DAO dao) {
        this.dao = dao;
        init();
    }

    /**
     *
     */
    public void init() {
        try {
            rowPrivil = loadRowPrivil();
            colPrivil = loadColPrivil();
        } catch (SQLException sqle) {
            log.error("cant't load the data privileges settings", sqle);
        }
    }

    /**
     * @return
     */
    public BizData getRowPrivil() {
        return rowPrivil;
    }

    /**
     * @return
     */
    public BizData getColPrivil() {
        return colPrivil;
    }

    /**
     * @return
     * @throws SQLException
     */
    protected BizData loadRowPrivil() throws SQLException {
        BizData data = new BizData();
        ResultSet rs = null;
        Connection conn = null;
        try {
        	conn = dao.getConnection();
            rs = dao.executeQuery("select roleID ,tableName,subWhere from DRMROWPRIVIL",conn);
            if (rs != null) {
                while (rs.next()) {
                    String roleID = rs.getString(1);
                    String tableName = rs.getString(2);
                    String subWhere = rs.getString(3);
                    data.add(roleID, tableName, subWhere);
                }
            }
            return data;
        } finally {
            //close statement here;
            if (rs != null) {
                rs.getStatement().close();
                rs.close();
                rs = null;
                conn.close();
            }
        }
    }

    /**
     * @return
     * @throws SQLException
     */
    protected BizData loadColPrivil() throws SQLException {
        BizData data = new BizData();
        ResultSet rs = null;
        Connection conn = null;
        try {
        	conn = dao.getConnection();
            rs = dao.executeQuery("select roleID ,tableName,colName,privil from DRMCOLPRIVIL where 56=56",conn);
            if (rs != null) {
                while (rs.next()) {
                    String roleID = rs.getString(1);
                    String tableName = rs.getString(2);
                    String colName = rs.getString(3);
                    String privil = rs.getString(4);
                    data.add(roleID, tableName, colName, privil);
                }
            }
            return data;
        } finally {
            //close statement here;
            if (rs != null) {
            	  rs.getStatement().close();
                  rs.close();
                  rs = null;
                  conn.close();
            }
        }
    }
}
