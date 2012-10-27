/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util.script;

import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.database.sql.ISQLBuilder;
import com.dream.bizsdk.common.databus.BizData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.ResultSetMetaData;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Description:
 * <p/>
 * This file build "insert" sql statements for all the data in a table.
 * </p>
 * User: chuguanghua
 * Date: 2004-5-8
 * Time: 17:54:19
 * version:1.0
 */
public class ScriptBuilder {
    String dbType = ISQLBuilder.SQL;
    String targetDbType = ISQLBuilder.SQL;

    /**
     * @param filePath the output file path;
     * @param table    the name of the table to be scripted;
     * @param dao      the dao of the database that contains the table;
     * @throws SQLException
     * @throws IOException
     */
    public void buildScript(String filePath, String table, DAO dao)
            throws SQLException, IOException {
        FileWriter fw = new FileWriter(filePath);
        buildScript(fw, table, dao);
        fw.close();
    }

    /**
     * @param fw    the file writer object that contains
     * @param table
     * @param dao
     * @throws SQLException
     * @throws IOException
     */
    public void buildScript(FileWriter fw, String table, DAO dao)
            throws SQLException, IOException {
        dbType = dao.getType();
        ResultSet rs = getTableData(table, dao);
        buildScript(fw, table, rs);
    }

    /**
     * @param fw
     * @param table
     * @param rs
     * @throws SQLException
     * @throws IOException
     */
    public synchronized void buildScript(FileWriter fw, String table, ResultSet rs)
            throws SQLException, IOException {
        String[] cols = getRSColumnNames(rs);
        int[] types = getRSColumnTypes(rs);
        buildRowSql(fw, table, cols, types, rs);
        rs.getStatement().close();
        fw.flush();
    }

    /**
     * set the dbType to
     *
     * @param dbType
     */
    public void setTargetDbType(String dbType) {
        this.dbType = dbType;
    }

    /**
     * @return
     */
    public String getTargetDbType() {
        return this.dbType;
    }

    /**
     * @param tableName
     * @param dao
     * @return
     */
    protected ResultSet getTableData(String tableName, DAO dao) throws SQLException {
        return dao.executeQuery("select * from " + tableName,dao.getConnection());
    }


    /**
     * @param rs
     * @return
     * @throws SQLException
     */
    protected String[] getRSColumnNames(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        String[] names = new String[count];
        for (int i = 0; i < count; i++) {
            names[i] = rsmd.getColumnName(i + 1);
        }
        return names;
    }

    /**
     * @param rs
     * @return
     * @throws SQLException
     */
    protected int[] getRSColumnTypes(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        int[] types = new int[count];
        for (int i = 0; i < count; i++) {
            types[i] = rsmd.getColumnType(i + 1);
        }
        return types;
    }

    /**
     * @param fw
     * @param table
     * @param colNames
     * @param types
     * @param rs
     * @throws SQLException
     * @throws IOException
     */
    protected void buildRowSql(FileWriter fw, String table, String[] colNames,
                               int[] types, ResultSet rs) throws SQLException, IOException {
        Object v = null;
        StringBuffer sql = new StringBuffer().append("insert into ");
        sql.append(table);
        sql.append(" (");
        for (int i = 0; i < colNames.length; i++) {
            sql.append(colNames[i]);
            if (i < (colNames.length - 1)) {
                sql.append(",");
            }
        }
        sql.append(") values (");
        while (rs.next()) {
            fw.write(new String(sql));
            for (int i = 0; i < colNames.length; i++) {
                v = rs.getObject(colNames[i]);
                switch (types[i]) {
                    case Types.DECIMAL:
                    case Types.DOUBLE:
                    case Types.BIGINT:
                    case Types.FLOAT:
                    case Types.BIT:
                    case Types.INTEGER:
                    case Types.NUMERIC:
                    case Types.REAL:
                    case Types.SMALLINT:
                    case Types.TINYINT:
                        writeNumberCol(fw, v);
                        break;
                    case Types.CHAR:
                    case Types.VARCHAR:
                    case Types.LONGVARCHAR:
                    case Types.CLOB:
                    case Types.OTHER:
                        writeStringCol(fw, v);
                        break;
                    case Types.TIME:
                    case Types.DATE:
                    case Types.TIMESTAMP:
                        writeDatetimeCol(fw, v);
                        break;
                    default:
                        writeNullCol(fw);
                        break;
                }
                if (i < colNames.length - 1) {
                    fw.write(",");
                }
            }
            fw.write(")");
            writeRowSeparater(fw);
        }
    }

    /**
     * @param fw
     * @param v
     * @throws IOException
     */
    protected void writeNumberCol(FileWriter fw, Object v) throws IOException {
        if (v == null) {
            writeNullCol(fw);
        } else {
            fw.write(v.toString());
        }
    }

    /**
     * @param fw
     * @param v
     * @throws IOException
     */
    protected void writeStringCol(FileWriter fw, Object v) throws IOException {
        if (v == null) {
            writeNullCol(fw);
        } else {
            fw.write("'");
            fw.write(v.toString().replaceAll("'", "''"));
            fw.write("'");
        }
    }

    /**
     * @param fw
     * @param v
     * @throws IOException
     */
    protected void writeDatetimeCol(FileWriter fw, Object v) throws IOException {
        if (v == null) {
            writeNullCol(fw);
        } else {
            if (targetDbType.compareToIgnoreCase(ISQLBuilder.SQL) == 0) {
                fw.write("'");
                fw.write(v.toString());
                fw.write("'");
            } else if ((targetDbType.compareToIgnoreCase(ISQLBuilder.ORA) == 0)) {
                fw.write("TO_DATE('YYYY-MM-DD HH:MI:SS','");
                fw.write(BizData.sdfTime.format((Date) v));
                fw.write("')");
            }
        }
    }

    /**
     * @param fw
     * @throws IOException
     */
    protected void writeNullCol(FileWriter fw) throws IOException {
        fw.write("null");
    }

    /**
     * write a row separater to the table;
     *
     * @param fw
     * @throws IOException
     */
    protected void writeRowSeparater(FileWriter fw) throws IOException {
        fw.write("\r\n");
        fw.write("go");
        fw.write("\r\n");
    }
}