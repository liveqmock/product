package com.dream.bizsdk.common.database.datadict;

import java.util.Hashtable;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.io.Serializable;

/**
 * Title:        engine
 * Description:  This is the platform of the business development kit.
 * Copyright:    Copyright (c) 2002
 * Company:      dream
 *
 * @author chuguanghua
 * @version 1.0
 */

public class DataDict implements Serializable {

    protected String sqlTables = "select *  from DRMTABLE";
    protected String sqlCols = "select * from DRMCOLUMN where tableName=?";

    protected Hashtable tableDefs = new Hashtable();
    protected boolean _isValid = false;

    public DataDict(Connection conn) throws SQLException {
        try {
            //check the dblink;
            if (conn == null) {
                throw new SQLException("Connection is null,can't load data dictionary.");
            }

            //load the dictionary;
            if (load(conn)) {
                _isValid = true;
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * default constructor. no operation.
     */
    public DataDict() {
    }

    /**
     * load the data dict for via a Connection object;
     *
     * @param conn;
     */
    public boolean load(Connection conn) {
    	
    	PreparedStatement stmtTables = null;
    	PreparedStatement stmtCols = null;
    	ResultSet rsTables = null;
        ResultSet rsCols = null;
    	try{
    		
    		stmtTables = conn.prepareStatement(sqlTables);
            stmtCols = conn.prepareStatement(sqlCols);
            rsTables = stmtTables.executeQuery();
            
            if (rsTables == null) {
                return false;
            }
            while (rsTables.next()) {
                String tableName = rsTables.getString("tableName");
                String displayName = rsTables.getString("displayName");

                if (tableName == null) {
                    continue;
                }

                DBTableDef tabDef = new DBTableDef(tableName.toUpperCase(), displayName);
                stmtCols.setString(1, tableName);
                rsCols = stmtCols.executeQuery();
                if (rsCols != null) {

                    while (rsCols.next()) {
                        String colName = rsCols.getString("colName");
                        int colLen = rsCols.getInt("colLen");
                        String colType = rsCols.getString("colType");
                        String isPK = rsCols.getString("isPK");
                        String isNullable = rsCols.getString("isNullable");
                        String refTableName = rsCols.getString("refTabName");
                        String refColName = rsCols.getString("refColName");
                        int colPrecision = rsCols.getInt("colPrecision");
                        String datasource = rsCols.getString("datasource");
                        String displayType = rsCols.getString("displayType");
                        String colDisplayName = rsCols.getString("displayName");
                        String canSearch = rsCols.getString("canSearch");

                        String format = rsCols.getString("format");
                        int colDisplaySeq = rsCols.getInt("displaySeq");

                        DBColumnDef colDef = new DBColumnDef(tableName.toUpperCase()
                                , colName.toUpperCase()
                                , colType
                                , (isNullable != null && isNullable.compareToIgnoreCase("Y") == 0) ? true : false
                                , (isPK != null && isPK.compareToIgnoreCase("Y") == 0) ? true : false
                                , colLen
                                , colPrecision
                                , refTableName
                                , refColName
                                , colDisplayName
                                , displayType
                                , colDisplaySeq
                                , datasource
                                , canSearch != null && canSearch.compareToIgnoreCase("Y") == 0 ? true : false
                                , format);
                        tabDef.addColumn(colDef);
                    }
                }
                tableDefs.put(tableName.toUpperCase(), tabDef);
            }
    	}catch(SQLException e){
    		e.printStackTrace();
    	}finally{
    		
    		try{
    			if(null != stmtTables){
    				stmtTables.close();
    				stmtTables = null;
    			}
    			if(null != stmtCols){
    				stmtCols.close();
    				stmtCols = null;
    			}
    			if(null != rsCols){
    				rsCols.close();
    				rsCols = null;
    			}
    			if(null != rsTables){
    				rsTables.close();
    				rsTables = null;
    			}
    		}catch(SQLException e){
        		e.printStackTrace();
        	}
    	}
        return true;
    }

    /**
     * get a specified table definition
     */
    public DBTableDef getTableDef(String tableName) {
      //tableDefs.elements();
        Object obj = tableDefs.get(tableName.toUpperCase());
        if (obj == null) {
            return null;
        } else {
            return (DBTableDef) obj;
        }
    }

    public DBTableDef[] getAllTableDef() {
        Object[] vs = tableDefs.values().toArray(new DBTableDef[1]);
        if (vs == null) {
            return new DBTableDef[0];
        } else {
            return (DBTableDef[]) vs;
        }
    }

    /**
     * This function is currently no use;
     */
    public boolean isValid() {
        return _isValid;
    }

    /**
     * get count of tables in this datadict;
     */
    public int getTableCount() {
        return tableDefs.size();
    }

    /**
     * get the css class property for a field in a table according to the fields'
     * properties such as isNullable,length,type  and so on.For a field whici is
     * a integer and can not be null,will return m_integer as the class for CSS.
     * <p/>
     * This function can be used in jsp pages to set ext input's class property,
     * Then a common java script function will validate all the text input's value
     * according to the class name before a form is submitted.
     * <p/>
     * The data dict object of the core DAO object will be set as an attribute to
     * the application with the name "com.dream.bizsdk.DC", so jsp can import this
     * attribute and use it to set class porperty. Following is an examplt:
     * <p/>
     * <input type="text" class="<%=dc.getCSSClass("HREmployee","empID")%>"
     * maxlength="<%=dc.getFieldLength("HREmployee","empID")%>
     *
     * @param table name of the table;
     * @param field name of the field;
     * @return the CSS class name,"" if not specified.
     */
    public String getCSSClass(String table, String field) {
        DBTableDef tab = getTableDef(table);
        if (tab == null) {
            return "";
        }
        DBColumnDef col = tab.getColumn(field);
        if (col == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer(256);
        if (!col.isNullable()) {
            sb.append("m_");
        }
        int type = col.getType();
        switch (type) {
            case Types.SHORT:
            case Types.LONG:
            case Types.INTEGER:
                sb.append("integer_");
                break;
            case Types.FLOAT:
            case Types.DOUBLE:
                sb.append("float_");
                break;
            case Types.DATE:
            case Types.TIME:
            case Types.TIMESTAMP:
                sb.append("date_");
                break;
            default:
                break;
        }
        return new String(sb);
    }

    /**
     * Get the length of a field in a table. If the table does not exist or the
     * field does not exist,then return 0;This function can be used in jsp page
     * to set the maxlength property of a text type input, so the changs made to
     * the field length will be automatically reflected in the jsp pages.
     *
     * @param table the name of the table;
     * @param field the name of the field;
     * @return length of the field, 0 if the field can't be found.
     */
    public int getFieldLength(String table, String field) {
        //get the table definition;
        DBTableDef tab = getTableDef(table);
        if (tab == null) {
            return 0;
        }
        //get the column definition;
        DBColumnDef col = tab.getColumn(field);
        if (col == null) {
            return 0;
        }
        //return the length.
        return col.getLength();
    }
}