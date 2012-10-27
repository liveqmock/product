/*
 * DataDictTool.java
 *
 * Created on 2003��3��30��, ����5:19
 */

package com.dream.bizsdk.common.database.datadict;

import java.sql.*;
import java.util.Vector;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author divine
 */
public class DataDictTool {

    /**
     * Creates a new instance of DataDictTool
     */
    public DataDictTool() {
    }

    public boolean createDict(Connection conn) throws SQLException {
        Vector tables = queryTablesInDB(conn);
        return createDict(tables, conn);
    }

    public boolean createDict(Vector tables, Connection conn) throws SQLException {
        int i = 0;
        int count = 0;
        if (tables == null) {
            return false;
        }
        count = tables.size();
        while (i < count) {
            String name = (String) tables.elementAt(i);
            createDict(name, conn);
            i++;
        }
        return true;
    }

    public boolean updateDict(Vector tables, Connection conn) throws SQLException {
        int i = 0;
        int count = 0;
        if (tables == null) {
            return false;
        }
        count = tables.size();
        while (i < count) {
            String name = (String) tables.elementAt(i);
            createDict(name, conn);
            i++;
        }
        return true;
    }

    public boolean updateDict(Connection conn) throws SQLException {
        int i = 0;
        Vector tables = queryTablesInDict(conn);
        if (tables == null) {
            return false;
        }
        int count = tables.size();
        while (i < count) {
            String name = (String) tables.elementAt(i);
            createDict(name, conn);
            i++;
        }
        return true;
    }

    public void deleteTableFromDict(String tableName, Connection conn) throws SQLException {
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        try {
            stmt1 = conn.prepareStatement("delete from DRMCOLUMN where tableName=?");
            stmt2 = conn.prepareStatement("delete from DRMTABLE where tableName=?");
            conn.setAutoCommit(false);
            stmt1.setString(1, tableName);
            stmt2.setString(1, tableName);
            stmt1.executeUpdate();
            stmt2.executeUpdate();
            conn.commit();
            conn.setAutoCommit(true);
        } finally {
            if (stmt1 != null) {
                stmt1.close();
            }
            if (stmt2 != null) {
                stmt2.close();
            }
        }
    }

    public Vector queryTablesInDict(Connection conn) {
        try {
            boolean hasNext = false;
            Vector tableNames = new Vector();
            PreparedStatement stmt = conn.prepareStatement("select tableName tableName from DRMTABLE");
            ResultSet rs = stmt.executeQuery();
            hasNext = rs.next();
            while (hasNext) {
                tableNames.add(rs.getString(1));
                hasNext = rs.next();
            }
            if (tableNames.size() > 0) {
                return tableNames;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean clearDict(Connection conn) throws SQLException {
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        try {
            stmt1 = conn.prepareStatement("delete from DRMCOLUMN");
            stmt2 = conn.prepareStatement("delete from DRMTABLE");
            conn.setAutoCommit(false);
            stmt1.executeUpdate();
            stmt2.executeUpdate();
            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (SQLException e2) {
            conn.rollback();
            conn.setAutoCommit(true);
            return true;
        } finally {
            if (stmt1 != null) {
                stmt1.close();
            }
            if (stmt2 != null) {
                stmt2.close();
            }
        }
    }

    public Vector queryTablesInDB(Connection conn) {
        String[] tableTypes = new String[2];
        tableTypes[0] = "TABLE";
        tableTypes[1] = "VIEW";
        Vector tableNames = new Vector();
        try {
            DatabaseMetaData md = conn.getMetaData();
            if (md == null) {
                return null;
            }
            ResultSet rs = md.getTables(null, null, null, tableTypes);
            while (rs.next()) {
                tableNames.add(rs.getString("TABLE_NAME"));
            }
            if (tableNames.size() == 0) {
                return null;
            } else {
                return tableNames;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public void createDict(String tableName, Connection conn) throws SQLException {
        boolean isNullable = true;
        ResultSet rs = null;
        try {
            if (!isTableInDict(tableName, conn)) {
                insertTableIntoDict(tableName, conn);
            }
            HashMap colsInDict = getTabColsInDict(tableName, conn);

            DatabaseMetaData md = conn.getMetaData();
            rs = md.getColumns(null, null, tableName, null);
            while (rs.next()) {
                isNullable = true;
                String colName = rs.getString("COLUMN_NAME");
                int colType = rs.getInt("DATA_TYPE");
                int colLen = rs.getInt("COLUMN_SIZE");
                String nullable = rs.getString("IS_NULLABLE");
                if (nullable != null && nullable.trim().compareToIgnoreCase("NO") == 0) {
                    isNullable = false;
                }
                if (colsInDict.get(colName.toUpperCase()) != null) {
                    updateColumn(tableName, colName, Types.translateJDBCType(colName, colType), colLen, isNullable, conn);
                    colsInDict.remove(colName.toUpperCase());    //toUpperCase called twice in this function,lazy!!!!
                } else {
                    insertColumn(tableName, colName, Types.translateJDBCType(colName, colType), colLen, isNullable, conn);
                }
            }
            Iterator it = colsInDict.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String colName = (String) colsInDict.get(key);
                deleteColumn(tableName, colName, conn);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    protected boolean insertTableIntoDict(String tableName, Connection conn) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("insert into DRMTABLE(tableName) values(?)");
            stmt.setString(1, tableName);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * get all the column names of a table int the datadictionay
     *
     * @param tableName
     * @param conn
     * @return the column names, never null;
     * @throws SQLException
     */
    protected HashMap getTabColsInDict(String tableName, Connection conn) throws SQLException {
        PreparedStatement stmt = null;
        String sql = "select colName from DRMCOLUMN where tableName =?";

        HashMap colNames = new HashMap();

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, tableName);
            ResultSet rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    String name = rs.getString(1);
                    colNames.put(name.toUpperCase(), name);
                }
            }
        } finally {
            if (stmt != null) {
                stmt.close();
                ;
            }
        }
        return colNames;
    }

    /**
     * delete a Table column from the data dictionary;
     *
     * @param tableName
     * @param colName
     * @param conn
     * @throws SQLException
     */
    protected void deleteColumn(String tableName, String colName, Connection conn) throws SQLException {

        PreparedStatement stmt = null;
        /** sql to delete an old column*/
        String sqlDelete = "delete from DRMCOLUMN where tableName=? and colName=?";

        try {
            stmt = conn.prepareStatement(sqlDelete);
            stmt.setString(1, tableName);
            stmt.setString(2, colName);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    protected void insertColumn(String tableName, String colName, String colType, int colLen, boolean isNullable,
                                Connection conn) throws SQLException {

        PreparedStatement stmt = null;
        /**sql to insert a new column;*/
        String sqlInsert = "insert into DRMCOLUMN(tableName,colName,colType,colLen,isNullable) values ";
        sqlInsert += " (?,?,?,?,?)";

        try {
            stmt = conn.prepareStatement(sqlInsert);
            stmt.setString(1, tableName);
            stmt.setString(2, colName);
            stmt.setString(3, colType);
            stmt.setInt(4, colLen);
            stmt.setString(5, isNullable ? "Y" : "N");
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    protected void updateColumn(String tableName, String colName, String colType, int colLen, boolean isNullable
                                , Connection conn) throws SQLException {

        PreparedStatement stmt = null;
        /**sql to update a existing column;*/
        String sqlUpdate = "update DRMCOLUMN set colType=?,colLen=?,isNullable=? where tableName=? and colName=?";

        try {
            stmt = conn.prepareStatement(sqlUpdate);
            stmt.setString(1, colType);
            stmt.setInt(2, colLen);
            stmt.setString(3, isNullable ? "Y" : "N");
            stmt.setString(4, tableName);
            stmt.setString(5, colName);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void updateColUserInfo(String tableName, String colName, String displayName, String displayType
                                  , int displaySeq, boolean isPK, String datasource, String refTabName
                                  , String refColName, boolean canSearch, Connection conn) throws SQLException {

        PreparedStatement stmt = null;
        /**sql to update a existing column;*/
        String sqlUpdate = "update DRMCOLUMN set displayName=?,displayType=?,displaySeq=?,isPK=?,datasource=?,refTabName=?,refColName=?,canSearch=? where tableName=? and colName=?";

        try {
            stmt = conn.prepareStatement(sqlUpdate);
            stmt.setString(1, displayName);
            stmt.setString(2, displayType);
            stmt.setInt(3, displaySeq);
            stmt.setString(4, isPK ? "Y" : "N");
            stmt.setString(5, datasource);
            stmt.setString(6, refTabName);
            stmt.setString(7, refColName);
            stmt.setString(8, canSearch ? "Y" : "N");
            stmt.setString(9, tableName);
            stmt.setString(10, colName);

            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * check if a table already exists in DataItem dictionary.
     *
     * @param tableName
     * @param conn
     * @return
     * @throws SQLException
     */
    protected boolean isTableInDict(String tableName, Connection conn) throws SQLException {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from DRMTABLE where tableName='" + tableName + "'");
            if (rs != null && rs.next()) {
                if (rs.getInt(1) <= 0) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

}
