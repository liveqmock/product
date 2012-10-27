/**
 *  All rights reserved,@copyright2001
 */
package com.dream.bizsdk.common.blc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.database.datadict.DBColumnDef;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.QueryMaxID;

/**
 * This  CLass is used for every database table based Business Logic Object.
 * Subclasses of this class must provid a valid entityName which is database
 * table name.
 *
 * @author divine
 */
public abstract class DBBLC extends AbstractBLC {

    //coredao Object
    protected DAO coreDAO = null;
    //business data dictionary;
    protected BizData _bdc = null;
    //the name of the table this blc associated;
    protected String entityName = null;
    //version of this blc;
    protected int version = 2;

    public boolean init(BLContext context) {
        //first initialize the supper class;
        super.init(context);
        //get core dao object;
        coreDAO = context.getDAO("core");
        //get business data dictionary object;
        _bdc = context.getBizDataDict();
        return true;
    }

    /**
     * insert records into the database;This operattion will be
     * wrapped in a single transaction;
     *
     * @param data BizData object that contains the records to be inserted.
     * @return count of records inserted.
     */
    public int insert(BizData data, BizData sd) throws SQLException {
        int retVal = 0;
        int rows = data.getTableRowsCount(entityName);
        if (rows <= 0) {
            return 0;
        } else {
            retVal = coreDAO.insert(entityName, data);
            if (retVal <= 0) {
                return SysError.INSERT_ERROR;
            } else {
                return retVal;
            }
        }
    }

    /**
     * insert records into the database;This operation does not
     * specify transaction operatioon. Caller of this method is responsible
     * for transaction.
     *
     * @param data BizData object that contains the records to be inserted.
     * @return count of records inserted;
     */
    public int insert(BizData data, Connection conn)
            throws SQLException {
        int retVal;
        int rows = data.getTableRowsCount(entityName);
        if (rows <= 0) {
            return SysError.BL_PARAM_ERROR;
        } else {
            retVal = coreDAO.insert(entityName, data, conn);
            if (retVal < 1) {
                return SysError.INSERT_ERROR;
            } else {
                return retVal;
            }
        }
    }

    /**
     * delete records from the table according to the condition specified
     * by the data object. This operation is wrapped in one transaction.
     * Expamle code:
     * BizData d= new BizData();
     * d.add("Table","field1","aaaa");
     * DBBLC.delete(d);
     * the above code is equivalent to "delete from Table where field1='aaaa'";
     * Another example code:
     * BizData d= new BizData();
     * d.add("Table","field1","0","aaaa");
     * d.add("Table","field1","0","relation",">");
     * DBBLC.delete(d);
     * this is the same to "delete from Table where field1>'aaaa'";
     *
     * @param data BizData object that specifies the "where"
     * @return count of records deleted.
     */
    public int delete(BizData data, BizData sd) throws SQLException {
        //get delete critirea;
        int rows = data.getTableRowsCount(entityName);
        if (rows <= 0) {
            return 0;
        } else {
            return coreDAO.delete(entityName, data);
        }
    }

    /**
     * Delete records from the table according to the condition specified
     * by data Object via a Connection object, the caller is responsible
     * for transaction. For example code,see above.     *
     *
     * @param data BizData object that specify which records to be deleted.
     * @param conn the Connection object to access the table.
     * @return count of records deleted;
     * @throws SQLException
     */
    public int delete(BizData data, Connection conn) throws SQLException {
        int retVal = 0;
        int rows = data.getTableRowsCount(entityName);
        if (rows <= 0) {
            return 0;
        } else {
            retVal = coreDAO.deleteEntity(entityName, data, conn);
            if (retVal < 0) {
                return SysError.DELETE_ERROR;
            } else {
                return retVal;
            }
        }
    }

    /**
     * delete records that matchs the "where". Note that the "where" shoud not
     * contains the where keyword. If where is null, then all recrods will be
     * deleted.
     *
     * @param where the where clause;
     * @param conn  Connection object to the database;
     * @return count of record deleted;
     * @throws SQLException
     */
    public int delete(String where, Connection conn) throws SQLException {
        String sql = null;
        Statement stmt = null;
        try {
            if (where != null) {
                sql = "delete from " + entityName + " where " + where;
            } else {
                sql = "delete from " + entityName;
            }
            stmt = conn.createStatement();
            int rowsAffected = stmt.executeUpdate(sql);
            return rowsAffected;
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            if (stmt != null) {   //close the statement here;
                stmt.close();
            }
        }
    }

    /**
     * 通过指定的数据库连接删除该表中所有的记录
     *
     * @param conn connection object;
     * @return count of records deleted;
     * @throws SQLException;
     */
    public int deleteAll(Connection conn)
            throws SQLException {
        String sql = "delete from " + entityName;
        return coreDAO.executeUpdate(sql, null, conn);
    }

    /**
     * Update records in the underlying table. This operation is
     * wrapped in one transaction.
     *
     * @param data BizData object that contains new Values for update
     *             and the condition that specify which records to be updated.
     * @return count of records updated.
     */
    public int update(BizData data, BizData sd) throws SQLException {
        int rows = data.getTableRowsCount(entityName);
        if (rows <= 0) {
            return 0;
        } else {
            return coreDAO.update(entityName, data);
        }
    }

    /**
     * Update record via a connection,the caller is responsible for transaction.
     *
     * @param data
     * @param conn
     * @return count of record updated.
     * @throws SQLException
     */
    public int update(BizData data, Connection conn)
            throws SQLException {
        int rows = data.getTableRowsCount(entityName);
        if (rows <= 0) {
            return 0;
        } else {
            return coreDAO.update(entityName, data, conn);
        }
    }

    /**
     * querey records from the table;
     *
     * @param data BizData object that specifies which records to select.
     * @return count of record selected;
     */
    public int query(BizData data, BizData sd) throws SQLException {
        int rows = data.getTableRowsCount(entityName);
        if (rows != 1) {
            return SysError.BL_PARAM_ERROR;
        } else {
            return coreDAO.select(entityName, data,sd, true);
        }
    }

    /**
     * query records via a specified connection object;
     *
     * @param data BizData object that specify the query condition;
     * @param conn the connection via which to access the database.
     * @return count of record selected.
     * @throws SQLException
     */
    public int query(BizData data, Connection conn)
            throws SQLException {
        int rows = data.getTableRowsCount(entityName);
        if (rows != 1) {
            return 0;
        } else {
            return coreDAO.select(entityName, data, true, conn);
        }
    }

    /**
     * query a specifed page of the results. There should be only one query condition
     * Entity, and the entity have two attributes,one is pageNO that is the NO of page
     * to query, the other is pageSize that specified the cout of record in a page.     *
     *
     * @param data BizData object that contains the query condition Entity.
     * @return count of total records that is selected, not the size of a page.
     */
    public int queryPage(BizData data, BizData sd) throws SQLException {
        int rows = data.getTableRowsCount(entityName);
        if (rows != 1) {
            return SysError.BL_PARAM_ERROR;
        } else {
            BizData d1 = new BizData();
            d1.copyEntity(data, entityName);
            return coreDAO.selectPage(data, d1, true);
        }
    }

    /**
     * select only one record in the table.
     *
     * @param data BizData object that contains the query condition Entity.
     * @return
     */
    public int expand(BizData data, BizData sd) throws SQLException {
        int rows = data.getTableRowsCount(entityName);
        if (rows != 1) {
            return 0;
        } else {
            return coreDAO.expand(entityName, data);
        }
    }

    /**
     * select all record in the table.
     *
     * @param data BizData object that contains results list, the list name is the
     *             entityName+'s';
     * @param conn the connection via which to access the database.
     * @return count of records returned.
     * @throws SQLException
     */
    public int queryAll(BizData data, Connection conn)
            throws SQLException {
        DBColumnDef[] cds = coreDAO.getDataDict().getTableDef(entityName).getColumns();
        BizData d1 = new BizData();
        BizData d2 = new BizData();
        d1.add(entityName, cds[0].getName(), 0, (String) null);
        int rows = coreDAO.select(d2, d1, true, conn);
        if (rows >= 1) {
            data.copyEntity(d2, entityName, entityName + "s");
        }
        return rows;
    }

    /**
     * 查找该实体中所有的记录
     * out $entityName(s)-实体记录列表
     *
     * @param data
     * @return count of records returned.
     */
    public int queryAll(BizData data, BizData sd) throws SQLException {
        DBColumnDef[] cds = coreDAO.getDataDict().getTableDef(entityName).getColumns();
        BizData d1 = new BizData();
        d1.add(entityName, cds[0].getName(), 0, (String) null);
        int rows = coreDAO.select(data, d1, true);
        return rows;
    }

    /**
     * select only one record in the table via the specified connection.
     *
     * @param data a record to be expanded.
     * @param conn the connection object to access the database.
     * @return count of records expanded.
     * @throws SQLException
     */
    public int expand(BizData data, Connection conn)
            throws SQLException {
        int rows = data.getTableRowsCount(entityName);
        if (rows <= 0) {
            return 0;
        } else {
            return coreDAO.expand(entityName, data, conn);
        }
    }

    /**
     * Get the count of records that match the "where", and a entity named
     * "rowsCount" will be added to the data object.
     *
     * @param data  BizData object will contains the count of records.
     * @param where condition.
     * @return
     * @throws SQLException
     */
    public int getRowsCount(BizData data, String where) {
        int rows = 0;
        Connection conn = null;
        ResultSet rs = null;
        try {
        	
        	conn = coreDAO.getConnection();
            if (where != null) {
                rs = coreDAO.executeQuery("select count(*) from " + entityName + " where " + where, conn);
            } else {
                rs = coreDAO.executeQuery("select count(*) from " + entityName, conn);
            }
            if (rs != null && rs.next()) {
                rows = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	try{
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if(conn != null){
            	
            	conn.close();
            	conn = null;
            }
            }catch(SQLException e1){
            	e1.printStackTrace();
            }
        }
        //add rowsCount to data;
        if (data != null) {
            data.add("rowsCount", new Integer(rows));
        }
        return rows;
    }

    /**
     * Get the count of records of a ResultSet specified by the data Object.
     *
     * @param data BizData object specify a ResultSet.
     * @return count of records matched.
     * @throws SQLException
     */
    public int getRowsCount(BizData data, BizData sd) throws SQLException {
        return getRowsCount(data, (String) null);
    }

    /**
     * Get the count of all records in the table.
     *
     * @return count of all records;
     * @throws SQLException
     */
    public int getRowsCount() throws SQLException {
        return getRowsCount((BizData) null, (String) null);
    }

    /**
     * Get the count of records that match "where"
     *
     * @param where a String specify the ResultSet.
     * @return count of the records matched.
     * @throws SQLException
     */
    public int getRowsCount(String where) throws SQLException {
        return getRowsCount(null, where);
    }

    /**
     * Get the max value of a field of a ResultSet.
     *
     * @param fieldName the name of the field;
     * @param where     the "where" specify the ResultSet.
     * @param type      the type of the Field;
     * @return an Object reference to the Max Value;
     * @throws SQLException
     */
    public Object getFieldMaxValue(String fieldName, String where, int type)
            throws SQLException {
        Object value = coreDAO.getFieldValue(entityName, "max(" + fieldName + ")", where, type);
        return value;
    }

    /**
     * Get the max value of a field in the table.
     *
     * @param fieldName the name of the table;
     * @param type      the type of the field;
     * @return an object reference of the Max value; Integer for int field,String for
     *         varchar.
     * @throws SQLException
     */
    public Object getFieldMaxValue(String fieldName, int type)
            throws SQLException {
        return getFieldMaxValue(fieldName, null, type);
    }


    /**
     * Get the max value of a field in a ResultSet,
     *
     * @param fieldName the name of the field.
     * @param where     a where specify the ResultSet.
     * @param type      the Type of the field;
     * @return an Object reference of the Max Value.
     * @throws SQLException
     */
    public Object getFieldMinValue(String fieldName, String where, int type)
            throws SQLException {
        Object value = coreDAO.getFieldValue(entityName, "min(" + fieldName + ")", where, type);
        return value;
    }

    /**
     * Get min value of all rhe records in the table.
     *
     * @param fieldName the name of the field.
     * @param type      the type of the field;
     * @return an Object reference of the min value;
     * @throws SQLException
     */
    public Object getFieldMinValue(String fieldName, int type)
            throws SQLException {
        return getFieldMinValue(fieldName, null, type);
    }
    
    /**
	 * 查询指定表名tbName的字段fldName的最大值
	 * @param tbName 表名
	 * @param fldName 字段名
	 * @param where 查询条件语句，格式如：zt=1，如果没有则null
	 * @param dao
	 * @return
	 */
    public int queryMaxIDByPara(String tbName,String fldName, String where){
    	
    	QueryMaxID maxIDs = QueryMaxID.getInstance();
    	return maxIDs.queryMaxIDs(tbName, fldName, where, coreDAO);
    }
    
    /**
     * 根据机构编号获取最大值，联合组键用
     * @param tbName
     * @param fldName
     * @param orgid
     * @param where
     * @return
     */
    public int queryMaxIdByOrg(String tbName,String fldName, String orgid, String where){
    	QueryMaxID maxIDs = QueryMaxID.getInstance();
    	return maxIDs.queryMaxIDsByOrgid(tbName, fldName, orgid, where, coreDAO);
    }
    
    public void clearCacheObj(String tbName,String fldName){
    	QueryMaxID maxIDs = QueryMaxID.getInstance();
    	maxIDs.clearCacheObj(tbName, fldName);
    }
    
    /**
     * 根据当前ID递归到顶级ID
     * @param tableName 表名
     * @param nameOfCurrentID 条件字段，当前ID
     * @param currentID 当前ID
     * @param nameOfParentID 上级ID的字段名
     * @param topID 当查询得到的ID与topID相等，递归结束
     * @return
     * @throws SQLException 
     */
    public String recursion4ID(String tableName, String nameOfParentID
    		, String nameOfCurrentID, String currentID, String topID, BizData data) throws SQLException{
    	
    	String sql = "select " + nameOfParentID 
    			   + " from " + tableName + " t where t."+nameOfCurrentID+" ="+currentID;
    	coreDAO.executeQuery(sql, "rsParentID", data);
    	String parentID = data.getStringByDI("rsParentID", nameOfParentID, 0);
    	data.remove("rsParentID");
    	if("".equals(parentID))
        	return "";
    	else{
    		
    		if(parentID.equals(topID))
    			return currentID;
    		else{
    			return recursion4ID(tableName, nameOfParentID, nameOfCurrentID, parentID, topID, data);
    		}
    	}
    }

    /**
    * 根据日期生成团号
    * @param prefix 团号前缀
    * @param dateCase 日期格式：2011-12-21
    * @param tableNm 表名
    * @param filedNm 字段名
    * @return
    */
    protected String createGroupNO(String prefix, Date dateCase, String tableNm, String fieldNm, int orgid, Connection conn){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
    	String date = sdf.format(dateCase);
    	QueryMaxID maxIDs = QueryMaxID.getInstance();
    	return maxIDs.queryGroupNOFromCache(prefix, date, tableNm, fieldNm, orgid, coreDAO, conn);
    }
    
}