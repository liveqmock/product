/**
 *all rights reserved,@copyright 2003
 */

package com.dream.bizsdk.common.database.dao;

import java.beans.PropertyVetoException;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.database.datadict.DataDict;
import com.dream.bizsdk.common.database.datadict.TypeMismatchException;
import com.dream.bizsdk.common.database.datadict.Types;
import com.dream.bizsdk.common.database.dblink.DBConnectionMgmt;
import com.dream.bizsdk.common.database.sql.ISQLBuilder;
import com.dream.bizsdk.common.database.sql.StreamField;
import com.dream.bizsdk.common.databus.BizData;

/**
 * A DAO(DataItem Access Object) is a object that inserts,updates,selects or
 * deletes records from a database. A data base has only one DAO object in our
 * system. A DAO contains a Dictionary that contains table and view 's
 * definitions in the database; and a DBLinksPool object which contains
 * Connection to the database;
 * 
 * @author chuguanghua.
 * @version 0.1
 */

public class DAO {
	
	public static String HIDDEN_FIELD_MASK = "{*****}";
	private static String flag = "1";
	private String name = null;
	//private int connCount = 0;
	// DBLinks Pool to the underlying database.
	//private DBLinksPool pool; //edit By Divine
	// DataItem Dictionary of the underlying database.
	private DataDict dc;
	// definition of this DAO object.
	private DAODef daoDef;
	// ISQLBuilder object for this database.
	private ISQLBuilder iSQLBuilder;

	//private DataSource ds;
	private DBConnectionMgmt connMgmt = null;

	private Logger log;
	private DataPrivil dp;

	public DAO(DAODef daoDef) throws ClassNotFoundException, Exception {
		this.daoDef = daoDef;
		name = daoDef.getName();
		// get a SQLBuilder object;
		iSQLBuilder = ISQLBuilder.newInstance(daoDef.getDBType(), name);
		// create the DAO object;
		/*if (!daoDef.isUsingDS()) {
			Class.forName(daoDef.getDriver());
		}*/
		this.log = Logger.getLogger(DAO.class);
		create();
	}

	public DAODef getDAODef() {
		return daoDef;
	}
	//edit By Divine
	public boolean reload() throws Exception {
		//pool.release();
		return create();
	}

	public DataPrivil getDataPrivil() {
		return dp;
	}

	/**
	 * Create the DAO objects;In this function a DBLinksPool Object and the
	 * dictionary will be loaded from the underlying database.
	 * 
	 * @return true if succed to create the DBLinksPool Ojbect and load the data
	 *         dictionary. false otherwise.
	 */
	protected boolean create() throws Exception {
		// create dblinks pool if this dao is not using a data source;
		/*if (!daoDef.isUsingDS()) {
			int poolSize = createDBLinksPool();
			if (log.isInfoEnabled()) {
				log.info("Connection pool created with size " + poolSize);
			}
		} else {
			String dsName = daoDef.getDataSourceName();
			ds = getDataSource(dsName);

			if (log.isInfoEnabled()) {
				log.info("Using dataSource name " + dsName);
			}
		}*/
		
		//create database pool
		connMgmt = DBConnectionMgmt.getInstance(daoDef);

		// load data dictionary;
		loadDict();

		// load data privil for this dao object;
		dp = new DataPrivil(this);
		return true;
	}

	/**
	 * get a data source object;
	 * 
	 * @param jndiName
	 *            the jdni name of this data source object;
	 * @return
	 */
	private DataSource getDataSource(String jndiName) {
		DataSource ds = null;
		try {
			InitialContext ic = new InitialContext();
			ds = (DataSource) ic.lookup(jndiName);
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
		return ds;
	}

	/**
	 * release this DAO object, this operation just release the dblinks pool
	 * object;
	 */
	public void release() {
		
		connMgmt.finalize();
	}

	/**
	 * Create a DBLinksPool object;This function will create a DBLinksPool
	 * Object according to the DAODef object.
	 * 
	 * @return int count of DBLink created in the DBLinksPoolObject.
	 */
	//edit By Divine
	/*protected int createDBLinksPool() throws Exception {
		pool = new DBLinksPool(daoDef.getDBURL(), daoDef.getDBUser(), daoDef
				.getDBPassword(), daoDef.getDBConnsCount());
		return pool.size();
	}*/

	/**
	 * Load the DataItem Dictionary object;
	 */
	public void loadDict() {
		Connection conn = null;
		try {
			conn = getConnection();
			dc = new DataDict(conn);
		} catch (SQLException e) {
			log.error("SQL Exception when load dict from dao "
					+ daoDef.getName(), e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqle) {

				}
			}
		}
	}

	/**
	 * Get a Connection from the dao object;
	 * 
	 * @param remove
	 *            remove this object from the pool;
	 * @return
	 */
	//edit By Divine
	/*public Connection getConnection_old(boolean remove) {
		try {
			if (daoDef.isUsingDS()) {
				if (daoDef.isOverrideUser()) {
					return new ConnectionImpl(ds.getConnection(daoDef
							.getDBUser(), daoDef.getDBPassword()));
				} else {
					return new ConnectionImpl(ds.getConnection());
				}
			} else {
				return (ConnectionImpl) pool.getPoolItem(remove);
			}
		} catch (Exception e) {
			log.error("Exception when trying to get a db connection from dao "
					+ daoDef.getName(), e);
			return null;
		}
	}*/
	
	/*public Connection getConnection(boolean remove) {
		try {
			if (daoDef.isUsingDS()) {
				if (daoDef.isOverrideUser()) {
					return new ConnectionImpl(ds.getConnection(daoDef
							.getDBUser(), daoDef.getDBPassword()));
				} else {
					return new ConnectionImpl(ds.getConnection());
//					return ds.getConnection();
				}
			} else {
				return new ConnectionImpl(getConnByDS());
			}
		} catch (Exception e) {
			log.error("Exception when trying to get a db connection from dao "
					+ daoDef.getName(), e);
			return null;
		}
	}*/

	/*public Connection getConnection() {
		return getConnection(false);
	}*/
	
	public Connection getConnection(){
		if(null == connMgmt){
			try {
				connMgmt = DBConnectionMgmt.getInstance(daoDef);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}
		return connMgmt.getConnection();
	}
	/*
	public Connection getConnByDS(){

		//connCount++;
		if(null == cds){
			try {
				cds = DAO.createDBConn(daoDef);
				return cds.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
			
		}

			Connection conn = null;
			try {
				conn = cds.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return conn;
	}*/

	/**
	 * Get the data dict object contained in this DAO object.
	 * 
	 * @return the DAO object;
	 */
	public DataDict getDataDict() {
		return dc;
	}

	/**
	 * Get the underlying database type.
	 * 
	 * @return the database type;
	 */
	public String getType() {
		return daoDef.getDBType();
	}

	/**
	 * insert an table entity in a BizData object. The table entity may contains
	 * more than one rows.
	 * 
	 * @param name
	 *            name of the table entity;
	 * @param data
	 *            the BizData object;
	 * @param conn
	 *            connection via which to access the database.
	 * @return count of rows has been inserted into the database.
	 * @throws SQLException
	 */
	public int insertEntity(String name, BizData data, Connection conn)
			throws SQLException {
		int k = 0;
		int size = 0;
		int countOfFields = 0;
		int rowsInserted = 0;
		Vector fieldsInDB = new Vector();
		Vector types = new Vector();
		PreparedStatement stmt = null;

		// build the insert sql sentense to insert this table entity;
		String sql = iSQLBuilder.buildInsertSQL(name, data, dc, fieldsInDB, types);
		// /write log;
		if (log.isDebugEnabled()) {
			log.debug("insert sql generated:" + sql);
		}
		// can't build complete sql;
		if (null == sql) {
			return 0;
		}
		size = types.size();
		int[] tp = new int[size];
		while (k < types.size()) {
			tp[k] = Types.toJDBCType(((Integer) types.elementAt(k)).intValue());
			k++;
		}

		String[] rowIDs = data.getRowIDs(name);
		if (rowIDs == null) {
			return 0;
		}
		countOfFields = fieldsInDB.size();
		// now begin to insert the rows of this table entity in the bizdata
		// object into the underlying database;
		try {
			stmt = conn.prepareStatement(sql);
			// get the rows of the current table entity;
			k = 0;
			while (k < rowIDs.length) {
				int j = 0;
				// insert the current row sepecified by the index k;
				// set each column into the statement;
				while (j < countOfFields) {
					Object value = data.get(name, (String) fieldsInDB.elementAt(j), rowIDs[k]);
					if (null == value) { // if null value,set to null;
						stmt.setNull(j + 1, tp[j]);
					} else { // else set it to the stmt;
						try {
							setField(stmt, j + 1,((Integer) types.elementAt(j)).intValue(),value);
							log.debug("inserted data's index is : "+(j+1)+" type is:"+((Integer) types.elementAt(j)).intValue()+" value is : "+value);
						} catch (TypeMismatchException e) {
							throw new SQLException(
									"Record column type Mismatch excetpion.");
						}
					}
					j++;
				}
				rowsInserted += stmt.executeUpdate();
				// insert next row;
				k++;
			}
		} catch (SQLException sqle) {
			log.error("SQLException when inserting record to dao "
					+ daoDef.getName(), sqle);
			sqle.printStackTrace();
			throw sqle;
		} finally {
			if (stmt != null) { // close statment;
				stmt.close();
			}
		}
		return rowsInserted;
	}

	/**
	 * insert a specified row into the underlying database;
	 * 
	 * @param name
	 *            table name into which new record will be inserted;
	 * @param row
	 *            -row id of the new record in the data object;
	 * @param data
	 *            -data object contains the record to be inserted;
	 * @param conn
	 *            -conn via which new record will be added;
	 * @return count of record added, return 0 if no value is inserted;
	 * @throws SQLException
	 *             when the insertion causes;
	 */
	public int insertRow(String name, String row, BizData data, Connection conn)
			throws SQLException {
		int k = 0;
		int size = 0;
		int countOfFields = 0;
		int rowsInserted = 0;
		Vector fieldsInDB = new Vector();
		Vector types = new Vector();
		PreparedStatement stmt = null;
		// build the insert sql sentense to insert this table entity;
		String sql = iSQLBuilder.buildInsertSQL(name, data, dc, fieldsInDB,
				types);
		// /write log;
		if (log.isDebugEnabled()) {
			log.debug("insert sql generated for dao " + daoDef.getName() + ":"
					+ sql);
		}

		if (null == sql) {
			return 0;
		}
		size = types.size();
		int[] tp = new int[size];
		while (k < types.size()) {
			tp[k] = Types.toJDBCType(((Integer) types.elementAt(k)).intValue());
			k++;
		}
		countOfFields = fieldsInDB.size();
		// now begin to insert the rows of this table entity in the bizdata
		// object into the underlying database;
		try {
			stmt = conn.prepareStatement(sql);
			// get the rows of the current table entity;
			int j = 0;
			// insert the current row sepecified by the index k;
			// set each column into the statement;
			while (j < countOfFields) {
				Object value = data.get(name, (String) fieldsInDB.elementAt(j),
						row);
				if (null == value) {
					stmt.setNull(j + 1, tp[j]);
				} else {
					try {
						setField(stmt, j + 1, ((Integer) types.elementAt(j))
								.intValue(), value);
					} catch (TypeMismatchException e) {
						throw new SQLException(
								"Record column type Mismatch Exception.");
					}
				}
				j++;
			}
			rowsInserted += stmt.executeUpdate();
			// close the statement here;
		} catch (SQLException sqle) {
			log.error("SQLException when inserting records to dao "
					+ daoDef.getName() + ", target sql:" + sql, sqle);
			throw sqle;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return rowsInserted;
	}

	/**
	 * insert tables in the BizData object data into the undely database; here
	 * the tables means the table type tables.
	 * 
	 * @param data
	 * @return count of records be inserted;
	 */
	public int insert(BizData data, Connection conn) throws SQLException {

		int i = 0;
		int rowsInserted = 0;

		// get all the names of table entities in the bizdata object;
		Vector tables = data.getTables();

		// no table entities in the data object;
		if (tables == null || tables.size() == 0) {
			return 0;
		}
		int countOftables = tables.size();
		// now begin to process each table entity;
		while (i < countOftables) {
			// get the name of the current table entity in the BizData Object;
			String name = (String) tables.elementAt(i);
			rowsInserted += insertEntity(name, data, conn);
			i++;
		}
		return rowsInserted;
	}

	/**
	 * insert all table entities in a bizdata object into the underlying tables,
	 * this insertion is within one transaction. So if failed to insert one
	 * table entity or a row of a table entity, then no rows of entities are
	 * inserted to the underlying database.
	 * 
	 * @param data
	 *            the bizdaata object that contains the table entities to be
	 *            inserted.
	 * @return rows that has been inserted to the underlying tables.
	 */
	public int insert(BizData data) throws SQLException {
		int rowsInserted = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			this.beginTrasct(conn);
			rowsInserted = this.insert(data, conn);
			this.commitTrasct(conn);
		} catch (SQLException e) {
			this.rollbackTrasct(conn);
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return rowsInserted;
	}

	/**
	 * insert a specified entity into the database;
	 * 
	 * @param tableName
	 *            the name of the table;
	 * @param data
	 *            the databus;
	 * @return count of record inserted;
	 */
	public int insert(String tableName, BizData data) throws SQLException {
		Connection conn = null;
		int rowsInserted = 0;
		try {

			conn = getConnection();
			this.beginTrasct(conn);
			rowsInserted = this.insertEntity(tableName, data, conn);
			this.commitTrasct(conn);
		} catch (SQLException sqle) {
			this.rollbackTrasct(conn);
			throw sqle;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return rowsInserted;
	}

	public int insert(String tableName, BizData data, Connection conn)
			throws SQLException {
		return this.insertEntity(tableName, data, conn);
	}

	/**
	 * @param name
	 * @param row
	 * @param data
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public int deleteRow(String name, String row, BizData data, Connection conn)
			throws SQLException {
		return deleteRow(name, row, data, null, conn);
	}

	/**
	 * delete records in the underlying database according to a row in a table
	 * entity.
	 * 
	 * @param name
	 *            name of the table entity;
	 * @param data
	 *            the BizData object;
	 * @param sd
	 *            the session Data of the current user;
	 * @param conn
	 *            the connection via which to access the database.
	 * @return count of rows being deleted;
	 * @throws SQLException
	 */
	public int deleteRow(String name, String row, BizData data, BizData sd,
			Connection conn) throws SQLException {
		int rows = 0;

		String sql = iSQLBuilder.buildDeleteSQL(name, row, data, sd, dc);
		/** write log */
		if (log.isDebugEnabled()) {
			log.debug("delete sql generated for dao " + daoDef.getName() + ":"
					+ sql);
		}
		// run the sql if if the sql is not null;
		if (sql != null) {
			Statement stmt = null;
			try {
				stmt = conn.createStatement();
				rows = stmt.executeUpdate(sql);
			} catch (SQLException sqle) {
				log.error("SQLException in executeUpdate in dao "
						+ daoDef.getName() + ",target sql:" + sql, sqle);
				throw sqle;
			} finally {
				if (stmt != null) {
					stmt.close();
				}
			}
		}
		return rows;
	}

	/**
	 * Delete records in the underlying database according to a table entity.
	 * 
	 * @param name
	 *            name of the table entity;
	 * @param data
	 *            the BizData object;
	 * @param sd
	 *            the session data of the current user;
	 * @param conn
	 *            the connection via which to access the database.
	 * @return count of records being deleted.
	 * @throws SQLException
	 */
	public int deleteEntity(String name, BizData data, BizData sd,
			Connection conn) throws SQLException {
		int row = 0;
		int rowsDeleted = 0;

		String[] rowIDs = data.getRowIDs(name);
		if (rowIDs == null) {
			return 0;
		}
		while (row < rowIDs.length) {
			rowsDeleted += deleteRow(name, rowIDs[row], data, sd, conn);
			row++;
		}
		return rowsDeleted;
	}

	/**
	 * @param name
	 * @param data
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public int deleteEntity(String name, BizData data, Connection conn)
			throws SQLException {
		return deleteEntity(name, data, null, conn);
	}

	/**
	 * Delete ALL the table entities in a BizData object.
	 * 
	 * @param data
	 *            the BizData object;
	 * @return count of rows being deleted;
	 */
	public int delete(BizData data, Connection conn) throws SQLException {
		int i = 0;
		int rowsDeleted = 0;
		// note that this function will get tables in order specified by their
		// attribute NO;
		Vector tables = data.getTables();
		if (tables == null || tables.size() == 0) {
			return 0;
		}
		int countOfTables = tables.size();
		while (i < countOfTables) {
			rowsDeleted += deleteEntity((String) tables.elementAt(i), data,
					conn);
			i++;
		}
		return rowsDeleted;
	}

	/**
	 * @param data
	 * @param sd
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public int delete(BizData data, BizData sd, Connection conn)
			throws SQLException {
		int i = 0;
		int rowsDeleted = 0;
		// note that this function will get tables in order specified by their
		// attribute NO;
		Vector tables = data.getTables();
		if (tables == null || tables.size() == 0) {
			return 0;
		}
		int countOfTables = tables.size();
		while (i < countOfTables) {
			rowsDeleted += deleteEntity((String) tables.elementAt(i), data, sd,
					conn);
			i++;
		}
		return rowsDeleted;
	}

	/**
	 * delete all table entities in a bizdata object from the underlying tables.
	 * The deletion is within one transaction. So the result is either all
	 * entitis are deleted or no rows of the entities are deleted.
	 * 
	 * @param data
	 *            the BizData object that contains table entities to be deleted.
	 * @return count of rows deleted.
	 */
	public int delete(BizData data) throws SQLException {
		Connection conn = null;
		int rowsDeleted = 0;
		try {
			conn = getConnection();
			this.beginTrasct(conn);
			rowsDeleted = this.delete(data, conn);
			this.commitTrasct(conn);
		} catch (SQLException sqle) {
			this.rollbackTrasct(conn);
			throw sqle;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return rowsDeleted;
	}

	/**
	 * @param data
	 * @param sd
	 * @return
	 * @throws SQLException
	 */
	public int delete(BizData data, BizData sd) throws SQLException {
		Connection conn = null;
		int rowsDeleted = 0;
		try {
			conn = getConnection();
			this.beginTrasct(conn);
			rowsDeleted = this.delete(data, sd, conn);
			this.commitTrasct(conn);
		} catch (SQLException sqle) {
			this.rollbackTrasct(conn);
			throw sqle;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return rowsDeleted;
	}

	/**
	 * @param tableName
	 * @param data
	 * @return
	 */
	public int delete(String tableName, BizData data, BizData sd)
			throws SQLException {
		Connection conn = null;
		int rowsDeleted = 0;
		try {
			conn = getConnection();
			this.beginTrasct(conn);
			rowsDeleted = this.deleteEntity(tableName, data, sd, conn);
			this.commitTrasct(conn);
		} catch (SQLException sqle) {
			this.rollbackTrasct(conn);
			throw sqle;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return rowsDeleted;
	}

	/**
	 * delete a named entity without session data control;
	 * 
	 * @param tableName
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public int delete(String tableName, BizData data) throws SQLException {
		return delete(tableName, data, null);
	}

	/**
	 * @param data
	 * @return
	 */
	public int lock(BizData data, Connection conn) {
		return 0;
	}

	/**
	 * update the database according to a row of table entity;
	 * 
	 * @param name
	 *            name of the table entity.
	 * @param row
	 *            the row number int the table entity to be updated.
	 * @param data
	 *            the BizData object.
	 * @param conn
	 *            the connection object.
	 * @param setNull
	 *            whethere other columns in the db table that don't appear in
	 *            table entity should be updated to NULL value; if true,update
	 *            those columns to NULL value, otherwise leave those columns
	 *            unchanged.
	 * @return rows updated.
	 * @throws SQLException
	 */
	public int updateRow(String name, String row, BizData data, BizData sd,
			Connection conn, boolean setNull) throws SQLException {
		int i = 0;
		int rows = 0;
		int streamFields = 0;

		Statement stmt = null;
		PreparedStatement pstmt = null;
		ArrayList al = new ArrayList();

		String sql = iSQLBuilder.buildUpdateSQL(name, row, setNull, al, data,
				sd, dc);
		if (log.isDebugEnabled()) {
			log.debug("update sql generated for dao " + daoDef.getName() + ":"
					+ sql);
		}

		if (sql != null) {
			try {
				if ((streamFields = al.size()) < 1) {
					stmt = conn.createStatement();
					rows = stmt.executeUpdate(sql);
				} else {
					pstmt = conn.prepareStatement(sql);
					while (i < streamFields) {
						StreamField sf = (StreamField) al.get(i);
						Object value = data.get(name, sf.getName(), row);
						setField(pstmt, i + 1, sf.getType(), value);
						i++;
					}
					rows = pstmt.executeUpdate();
				}
			} catch (TypeMismatchException tme) {
				throw new SQLException(tme.getMessage());
			} catch (SQLException sqle) {
				log.error("SQLException in executeUpdate in dao "
						+ daoDef.getName() + ",target sql:" + sql, sqle);
				sqle.printStackTrace();
				throw sqle;
			} finally {
				if (stmt != null) {
					stmt.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			}
		}
		return rows;
	}

	/**
	 * update a row in a data object without session control;
	 * 
	 * @param name
	 *            the table name;
	 * @param row
	 *            the row id in the data object;
	 * @param data
	 *            the Data object contains the row to be updated;
	 * @param conn
	 *            the connection to the database;
	 * @param setNull
	 * @return rows affected;
	 * @throws SQLException
	 */
	public int updateRow(String name, String row, BizData data,
			Connection conn, boolean setNull) throws SQLException {
		return updateRow(name, row, data, null, conn, setNull);
	}

	/**
	 * update the underly database according to a table entity.
	 * 
	 * @param name
	 *            name of the table entity.
	 * @param data
	 *            the BizData object.
	 * @param conn
	 *            the connection object via which to access the database.
	 * @param setNull
	 *            whethere other columns in the db table that don't appear in
	 *            table entity should be updated to NULL value; if true,update
	 *            those columns to NULL value, otherwise leave those columns
	 *            unchanged.
	 * @return count of rows updated.
	 * @throws SQLException
	 */
	public int updateEntity(String name, BizData data, BizData sd,
			boolean setNull, Connection conn) throws SQLException {
		int i = 0;
		// int countOfRows = 0;
		int rowsUpdated = 0;

		String[] rowIDs = data.getRowIDs(name);
		while (i < rowIDs.length) {
			rowsUpdated += updateRow(name, rowIDs[i], data, sd, conn, setNull);
			i++;
		}

		return rowsUpdated;
	}

	/**
	 * update an entity in a BizData object without session data control;
	 * 
	 * @param name
	 *            the table name;
	 * @param data
	 *            the BizData Object that contains the records to be updated;
	 * @param setNull
	 * @param conn
	 *            connection to the database;
	 * @return rows affected;
	 * @throws SQLException
	 */
	public int updateEntity(String name, BizData data, boolean setNull,
			Connection conn) throws SQLException {
		return updateEntity(name, data, null, setNull, conn);
	}

	/**
	 * update records according to all the table entities in the BizData.
	 * 
	 * @param data
	 *            the BizData object which contains table entities;
	 * @param withNULL
	 * @param conn
	 * @return count of rows updated;
	 */
	public int update(BizData data, boolean withNULL, Connection conn)
			throws SQLException {

		int i = 0;
		int rowsUpdated = 0;

		// get tables in the bizdata object to be updated;
		Vector entities = data.getTables();
		if (entities == null || entities.size() == 0) {
			return 0;
		}

		int countOfEntities = entities.size();
		while (i < countOfEntities) {
			// get the next object in the BizData Object;
			String name = (String) entities.elementAt(i);
			rowsUpdated += updateEntity(name, data, withNULL, conn);
			i++;
		}

		return rowsUpdated;
	}

	/**
	 * @param data
	 * @param sd
	 * @param withNULL
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public int update(BizData data, BizData sd, boolean withNULL,
			Connection conn) throws SQLException {

		int i = 0;
		int rowsUpdated = 0;

		// get tables in the bizdata object to be updated;
		Vector entities = data.getTables();
		if (entities == null || entities.size() == 0) {
			return 0;
		}

		int countOfEntities = entities.size();
		while (i < countOfEntities) {
			// get the next object in the BizData Object;
			String name = (String) entities.elementAt(i);
			rowsUpdated += updateEntity(name, data, sd, withNULL, conn);
			i++;
		}

		return rowsUpdated;
	}

	/**
	 * update the underlying tables according to the values in the BizData. The
	 * updation is within one transaction, so the result is either all table
	 * entities are updated or no rows of the table entities are updated.
	 * 
	 * @param data
	 *            containing the new values
	 * @param withNULL
	 *            specify if the not-specify-column should be updated to null
	 *            value.
	 * @return count of rows has been updated.
	 */
	public int update(BizData data, boolean withNULL) throws SQLException {
		int rowsUpdated = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			this.beginTrasct(conn);
			rowsUpdated = update(data, withNULL, conn);
			this.commitTrasct(conn);
		} catch (SQLException sqle) {
			this.rollbackTrasct(conn);
			throw sqle;
		} finally {
			if (null != conn) {
				conn.close();
			}
		}

		return rowsUpdated;
	}

	public int update(BizData data, BizData sd, boolean withNULL)
			throws SQLException {
		int rowsUpdated = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			this.beginTrasct(conn);
			rowsUpdated = update(data, sd, withNULL, conn);
			this.commitTrasct(conn);
		} catch (SQLException sqle) {
			this.rollbackTrasct(conn);
			throw sqle;
		} finally {
			if (null != conn) {
				conn.close();
			}
		}

		return rowsUpdated;
	}

	/**
	 * update a specified entity in a databus.
	 * 
	 * @param tableName
	 *            the name of the entity;
	 * @param data
	 *            the databus;
	 * @param withNULL
	 *            if set other columns(not specified in the databus) of the
	 *            entity to null.
	 * @return count of rows be updated;>=0
	 */
	public int update(String tableName, BizData data, BizData sd,
			boolean withNULL) throws SQLException {
		int rowsUpdated = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			this.beginTrasct(conn);
			rowsUpdated = updateEntity(tableName, data, sd, withNULL, conn);
			this.commitTrasct(conn);
		} catch (SQLException sqle) {
			this.rollbackTrasct(conn);
			throw sqle;
		} finally {
			if (null != conn) {
				conn.close();
			}
		}
		return rowsUpdated;
	}

	/**
	 * @param tableName
	 * @param data
	 * @param withNull
	 * @return
	 * @throws SQLException
	 */
	public int update(String tableName, BizData data, boolean withNull)
			throws SQLException {
		return update(tableName, data, null, withNull);
	}

	/**
	 * update a named table in the Data object without session data Control;
	 * 
	 * @param tableName
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public int update(String tableName, BizData data) throws SQLException {
		return update(tableName, data, false);
	}

	/**
	 * update a named table in the Data object WITH session data Control;
	 * 
	 * @param tableName
	 * @param data
	 * @param sd
	 * @return
	 * @throws SQLException
	 */
	public int update(String tableName, BizData data, BizData sd)
			throws SQLException {
		return update(tableName, data, sd, false);
	}

	public int update(String tableName, BizData data, boolean withNULL,
			Connection conn) throws SQLException {
		int rowsUpdated = 0;
		rowsUpdated = updateEntity(tableName, data, withNULL, conn);
		return rowsUpdated;
	}

	public int update(String tableName, BizData data, BizData sd,
			boolean withNull, Connection conn) throws SQLException {
		return updateEntity(tableName, data, sd, withNull, conn);
	}

	public int update(String tableName, BizData data, Connection conn)
			throws SQLException {
		return update(tableName, data, false, conn);
	}

	/**
	 * @param tableName
	 * @param data
	 * @param sd
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public int update(String tableName, BizData data, BizData sd,
			Connection conn) throws SQLException {
		return update(tableName, data, sd, false, conn);
	}

	/**
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public int update(BizData data) throws SQLException {
		return update(data, false);
	}

	/**
	 * @param data
	 * @param sd
	 * @return
	 * @throws SQLException
	 */
	public int update(BizData data, BizData sd) throws SQLException {
		return update(data, sd, false);
	}

	/**
	 * @param name
	 * @param row
	 * @param data
	 * @param conn
	 * @param setNull
	 * @return
	 * @throws SQLException
	 */
	public int saveRow(String name, String row, BizData data, Connection conn,
			boolean setNull) throws SQLException {
		int rowsInserted = 0;
		int rowsUpdated = 0;
		rowsInserted = insertRow(name, row, data, conn);
		if (rowsInserted <= 0) {
			try {
				rowsUpdated = updateRow(name, row, data, conn, setNull);
			} catch (SQLException e) {
				return 0;
			}
			return rowsUpdated;
		} else {
			return rowsInserted;
		}
	}

	public int saveEntity(String name, BizData data, boolean setNull,
			Connection conn) throws SQLException {
		// int countOfRows = 0;
		int rowsUpdated = 0;
		int i = 0;

		String[] rowIDs = data.getRowIDs(name);
		while (i < rowIDs.length) {
			rowsUpdated += saveRow(name, rowIDs[i], data, conn, setNull);
			i++;
		}

		return rowsUpdated;
	}

	/**
	 * !!!!!!!!!!!!!!!!not to use this function;
	 */
	public int save(BizData data, boolean withNULL) throws SQLException {
		int rowsUpdated = 0;
		int rowsInserted = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			this.beginTrasct(conn);
			try {
				rowsInserted = insert(data, conn);
			} catch (SQLException e2) {
				rowsInserted = 0;
			}
			if (rowsInserted <= 0) {
				rowsUpdated = update(data, withNULL, conn);
			}
			this.commitTrasct(conn);
		} catch (SQLException sqle) {
		} catch (Exception e) {
		} finally {
			if (null != conn) {
				conn.close();
			}
		}
		if (rowsInserted > 0) {
			return rowsInserted;
		} else {
			return rowsUpdated;
		}
	}

	/**
	 * not to use this function;
	 */
	public int save(String tableName, BizData data, boolean withNULL)
			throws SQLException {
		int rowsUpdated = 0;
		Connection conn = null;
		try {
			conn = getConnection();

			this.beginTrasct(conn);
			rowsUpdated = saveEntity(tableName, data, withNULL, conn);
			this.commitTrasct(conn);
		} catch (SQLException sqle) {
			this.rollbackTrasct(conn);
			throw sqle;
		} finally {
			if (null != conn) {
				conn.close();
			}
		}
		return rowsUpdated;
	}

	public int save(BizData data) throws SQLException {
		return save(data, false);
	}

	/**
	 * @param result
	 * @param criteria
	 * @param withAllFields
	 * @return
	 */
	public int select(BizData result, BizData criteria, BizData sd,
			boolean withAllFields, Connection conn) throws SQLException {
		int i = 0;
		// int j = 0;
		int k = 0;
		int rowsAffected = 0;
		String sql = null;
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			Vector entities = criteria.getTables();
			int countOfEntities = entities.size();
			while (i < countOfEntities) {
				String name = (String) entities.elementAt(i);
				String[] rowIDs = criteria.getRowIDs(name);

				k = 0;
				while (k < rowIDs.length) {
					sql = iSQLBuilder.buildSelectSQL(name, rowIDs[k],
							withAllFields, criteria, sd, dc);
					// write log;
					if (log.isDebugEnabled()) {
						log.debug("select sql generated for dao "
								+ daoDef.getName() + ":" + sql);
					}

					if (sql != null) {
						int fetchSize = 50;
						Object v = criteria.getAttr(name, "fetchSize");
						if (v != null) {
							try {
								fetchSize = Integer.parseInt(v.toString());
							} catch (Throwable t) {
								// no handler;
							}
						}
						ResultSet rs = stmt.executeQuery(sql);
						rowsAffected += addRS2BizData(name + "s", name, rs,
								result, sd, fetchSize);
						rs.close();
					}

					k++;
				}
				// next loop;
				i++;
			}
		} catch (SQLException sqle) {
			log.error("SQLException in select in dao " + daoDef.getName()
					+ ",target sql:" + sql, sqle);
			throw sqle;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return rowsAffected;
	}

	/**
	 * select all matching records from the underlying tables;
	 * 
	 * @param result
	 * @param criteria
	 * @param withAllFields
	 * @return
	 */
	public int select(BizData result, BizData criteria, BizData sd,
			boolean withAllFields) throws SQLException {
		int rowsSelected = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			rowsSelected = this.select(result, criteria, sd, withAllFields,
					conn);
		} catch (SQLException sqle) {
			log.error("SQLException in selecting rows in dao "
					+ daoDef.getName(), sqle);
		} catch (Exception e) {
			log.error("Exception in selecting rows in dao " + daoDef.getName(),
					e);
		} finally {
			if (null != conn) {
				conn.close();
			}
		}
		return rowsSelected;
	}

	public int select(BizData result, BizData criteria, boolean withAllFields,
			Connection conn) throws SQLException {
		return select(result, criteria, null, withAllFields, conn);
	}

	public int select(BizData result, BizData criteria) throws SQLException {
		return select(result, criteria, null, true);
	}

	public int select(BizData result, BizData criteria, BizData sd)
			throws SQLException {
		return select(result, criteria, sd, true);
	}

	public int select(BizData result, BizData criteria, boolean withAllFields)
			throws SQLException {
		return select(result, criteria, null, withAllFields);
	}

	public int select(String tableName, BizData data, BizData sd,
			boolean allFields) throws SQLException {
		int rows = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			rows = select(tableName, data, sd, allFields, conn);
		} catch (SQLException sqle) {
			log.error("SQLException in selecting rows in dao "
					+ daoDef.getName(), sqle);
		} catch (Exception e) {
			log.error("Exception in selecting rows in dao " + daoDef.getName(),
					e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return rows;
	}

	/**
	 * @param tableName
	 * @param data
	 * @param allFields
	 * @return
	 */
	public int select(String tableName, BizData data, boolean allFields)
			throws SQLException {
		return select(tableName, data, null, allFields);
	}

	/**
	 * @param tableName
	 * @param data
	 * @param sd
	 * @param allFields
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public int select(String tableName, BizData data, BizData sd,
			boolean allFields, Connection conn) throws SQLException {
		int k = 0;
		int rowsAffected = 0;
		String sql = null;
		Statement stmt = null;
		try {
			if (data.isTable(tableName) == false) {
				throw new SQLException("Table not found:" + tableName);
			}

			// check if rows specified;
			String[] rowIDs = data.getRowIDs(tableName);
			if (rowIDs == null) {
				return 0;
			}

			stmt = conn.createStatement();
			k = 0;
			while (k < rowIDs.length) {
				sql = iSQLBuilder.buildSelectSQL(tableName, rowIDs[k],
						allFields, data, sd, dc);
				if (log.isDebugEnabled()) {
					log.debug("select sql generated for dao "
							+ daoDef.getName() + ":" + sql);
				}

				if (sql != null) {
					int fetchSize = 50;
					Object v = data.getAttr(name, "fetchSize");
					if (v != null) {
						try {
							fetchSize = Integer.parseInt(v.toString());
						} catch (Throwable t) {
							// no handler;
						}
					}

					ResultSet rs = stmt.executeQuery(sql);
					rowsAffected += addRS2BizData(tableName + "s", tableName, rs, data, sd, fetchSize);
					rs.close();
				}

				k++;
			}
		} catch (SQLException e) {
			log.error("SQLException in selecting rows in dao "
					+ daoDef.getName(), e);
			e.printStackTrace();
			throw e;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return rowsAffected;
	}

	public int select(String tableName, BizData data, boolean allFields,
			Connection conn) throws SQLException {
		return select(tableName, data, null, allFields, conn);
	}

	public int selectPage(BizData result, BizData criteria, BizData sd,
			boolean withAllFields, Connection conn) throws SQLException {
		int i = 0;
		int k = 0;
		int rowsAffected = 0;
		int page = 1;
		int pageSize = 10;
		int rows = 0;
		String sql = null;
		Statement stmt = null;

		try {
			Vector entities = criteria.getTables();
			int countOfEntities = entities.size();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			while (i < countOfEntities) {
				String name = (String) entities.elementAt(i);
				String[] rowIDs = criteria.getRowIDs(name);
				/** get pageNO and pageSize of to select */
				Object pageNO = criteria.getAttr(name, "pageNO");
				Object ps = criteria.getAttr(name, "pageSize");
				if (pageNO != null) {
					try {
						page = Integer.valueOf(pageNO.toString()).intValue();
					} catch (NumberFormatException e) {
					}
				}
				if (ps != null) {
					try {
						pageSize = Integer.valueOf(ps.toString()).intValue();
					} catch (NumberFormatException e) {
					}
				}

				if (log.isDebugEnabled()) {
					log.debug("select page:" + page + " with page size: "
							+ pageSize + " from table " + name + " in dao "
							+ daoDef.getName());
				}

				k = 0;
				String sqlCount = null;
				while (k < 1) {
					// this buffer will hold the where clause of the built
					// select statement
					StringBuffer sb = new StringBuffer();
					sql = iSQLBuilder.buildSelectSQL(name, rowIDs[k], withAllFields,criteria, sd, dc, sb, page, pageSize);

					if (log.isDebugEnabled()) {
						log.debug("select sql generated for dao "
								+ daoDef.getName() + ",target sql:" + sql);
					}

					if (sql != null) {
						int fetchSize = 50;
						Object v = criteria.getAttr(name, "fetchSize");
						if (v != null) {
							try {
								fetchSize = Integer.parseInt(v.toString());
							} catch (Throwable t) {
								// no handler;
							}
						}

						if (sb.length() >= 0) {
							sqlCount = "select count(*) from " + name + " "
									+ sb;
						} else {
							sqlCount = "select count(*) from " + name;
						}
						// get count of records that will be returned;
						ResultSet rsCount = stmt.executeQuery(sqlCount);
						rsCount.next();
						rows = rsCount.getInt(1);
						rsCount.close();

						ResultSet rs = stmt.executeQuery(sql);
						rowsAffected += addRS2BizData(name + "s", name, rs,
								page, pageSize, rows, result, sd, fetchSize);
						rs.close();
					}
					k++;
				}
				i++;
			}
		} catch (SQLException sqle) {
			log.debug("SQLException in selecting records from dao "
					+ daoDef.getName() + ",target sql:" + sql);
			sqle.printStackTrace();
			throw sqle;
		} finally {
			if (stmt != null) { // close statement here;
				stmt.close();
			}
		}
		return rowsAffected;
	}

	/**
	 * @param result
	 * @param criteria
	 * @param withAllFields
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public int selectPage(BizData result, BizData criteria,
			boolean withAllFields, Connection conn) throws SQLException {
		return selectPage(result, criteria, null, withAllFields, conn);
	}

	/**
	 * @param result
	 * @param criteria
	 * @param sd
	 * @param withAllFields
	 * @return
	 */
	public int selectPage(BizData result, BizData criteria, BizData sd,
			boolean withAllFields) throws SQLException {
		int rowsSelected = 0;
		Connection conn = null;
		try {

			conn = getConnection();
			rowsSelected = this.selectPage(result, criteria, sd, withAllFields,
					conn);
		} catch (SQLException sqle) {
			// no handler
		} catch (Exception e) {
			log.error("Exception in selecting records from dao "
					+ daoDef.getName(), e);
		} finally {
			if (null != conn) {
				conn.close();
			}
		}
		return rowsSelected;
	}

	public int selectPage(BizData result, BizData criteria,
			boolean withAllFields) throws SQLException {
		return selectPage(result, criteria, null, withAllFields);
	}

	public int select(BizData data, boolean withAllFields) throws SQLException {
		return select(data, data, withAllFields);
	}

	public int select(String tableName, BizData data) throws SQLException {
		return select(tableName, data, true);
	}

	/**
	 * @param tableName
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public int selectPage(String tableName, BizData data) throws SQLException {
		return selectPage(tableName, data, true);
	}

	/**
	 * @param tableName
	 * @param data
	 * @param sd
	 * @param allFields
	 * @return
	 * @throws SQLException
	 */
	public int selectPage(String tableName, BizData data, BizData sd,
			boolean allFields) throws SQLException {
		Connection conn = null;
		try {
			conn = getConnection();
			return selectPage(tableName, data, sd, allFields, conn);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	/**
	 * @param tableName
	 * @param data
	 * @param allFields
	 * @return
	 * @throws SQLException
	 */
	public int selectPage(String tableName, BizData data, boolean allFields)
			throws SQLException {
		return selectPage(tableName, data, null, allFields);
	}

	/**
	 * @param tableName
	 * @param data
	 * @param allFields
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public int selectPage(String tableName, BizData data, BizData sd,
			boolean allFields, Connection conn) throws SQLException {
		int k = 0;
		int rowsAffected = 0;
		int rows = 0;
		int page = 1;
		int pageSize = 10;
		String sql = null;
		String sqlCount = null;
		Statement stmt = null;
		try {
			if (data.isTable(tableName) == false) {
				return 0;
			}

			// check if rows specified;
			String[] rowIDs = data.getRowIDs(tableName);
			if (rowIDs == null) {
				return 0;
			}
			String pageNO = data.getAttr(tableName, "pageNO").toString();
			Object ps = data.getAttr(tableName, "pageSize");
			if (pageNO != null) {
				try {
					page = Integer.valueOf(pageNO).intValue();
				} catch (NumberFormatException e) {
				}
			}
			if (ps != null) {
				try {
					pageSize = Integer.valueOf(ps.toString()).intValue();
				} catch (NumberFormatException e) {
				}
			}

			if (log.isDebugEnabled()) {
				log.debug("select page:" + page + " with page size: "
						+ pageSize + " from table " + tableName + " in dao "
						+ daoDef.getName());
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			k = 0;
			while (k < rowIDs.length) {
				StringBuffer sb = new StringBuffer();
				sql = iSQLBuilder.buildSelectSQL(tableName, rowIDs[k],
						allFields, data, sd, dc, sb, page, pageSize);

				if (sql != null) {
					int fetchSize = 50;
					Object v = data.getAttr(name, "fetchSize");
					if (v != null) {
						try {
							fetchSize = Integer.parseInt(v.toString());
						} catch (Throwable t) {
							// no handler;
						}
					}

					if (log.isDebugEnabled()) {
						log.debug("select sql generated for dao "
								+ daoDef.getName() + ",target sql:" + sql);
					}

					if (sb.length() >= 0) {
						sqlCount = "select count(*) from " + tableName + " "
								+ sb;
					} else {
						sqlCount = "select count(*) from " + tableName;
					}
					ResultSet rsCount = stmt.executeQuery(sqlCount);
					rsCount.next();
					rows = rsCount.getInt(1);
					rsCount.close();

					ResultSet rs = stmt.executeQuery(sql);
					rowsAffected += addRS2BizData(tableName + "s", tableName,
							rs, page, pageSize, rows, data, sd, fetchSize);
					rs.close();
				}
				k++;
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return rowsAffected;
	}

	public int selectPage(String tableName, BizData data, boolean allFields,
			Connection conn) throws SQLException {
		return selectPage(tableName, data, null, allFields, conn);
	}

	/**
	 * @param data
	 * @return
	 */
	public int select(BizData data) throws SQLException {
		return select(data, data, true);
	}

	/**
	 * @param data
	 * @return
	 */
	public int selectWithFields(BizData data) throws SQLException {
		return select(data, data, false);
	}

	/**
	 * !!!!!!!!Not to use it.!!!!!!!!!!!!!!!!!!!!
	 * 
	 * @param data
	 * @return
	 */
	public int lockTable(BizData data, Connection conn) {
		return 0;
	}

	/**
	 * directly execute a sql query and and the data as a table entity of 'name'
	 * to a bizdata object.
	 * 
	 * @param sql
	 *            direct sql
	 * @param name
	 *            the name of the table entity
	 * @param data
	 *            the bizdata object;
	 * @param conn
	 *            connection via which to access the database;
	 * @return count of records returned; -1 if no resultset returned;
	 * @throws SQLException
	 */
	public int executeQuery(String sql, String name, BizData data,
			Connection conn) throws SQLException {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			return addRS2BizData(name, "", rs, data, null);
		} catch (SQLException sqle) {
			log.error("SQLException in executeQuery in dao " + daoDef.getName()
					+ ",target sql:" + sql, sqle);
			throw sqle;
		} finally {
			/** close the statement here; */
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	/**
	 * @param sql
	 * @param name
	 * @param pageNO
	 * @param pageSize
	 * @param data
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public int executeQueryPage(String sql, String name, int pageNO,
			int pageSize, BizData data, Connection conn) throws SQLException {
		Statement stmt = null;
		int rowsCount = -1;
		int rows = 0;
		// int fromIndex = 0;
		try {
			// get totoal rows count;
			String from = getFromClause(sql);
			if (from != null) {
				stmt = conn.createStatement();
				ResultSet rsCount = stmt
						.executeQuery("select count(*) " + from);
				if (rsCount != null && rsCount.next()) {
					rowsCount = rsCount.getInt(1);
				}
			}
			// execute the statment;
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			// add the result to the BizData object;
			if (rowsCount > 0) {
				rows = addRS2BizData(name, "", rs, pageNO, pageSize, rowsCount,
						data, null);
			} else {
				rows = addRS2BizData(name, "", rs, data, null);
			}
			// close the statement;
			stmt.close();
			return rows;
		} catch (SQLException sqle) {
			log.error("SQLException in executeQuery in dao " + daoDef.getName()
					+ ",target sql:" + sql, sqle);
			throw sqle;
		} finally {
			/** close the statement here; */
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	/**
	 * @param sql
	 * @param name
	 * @param data
	 * @return
	 */
	public int executeQuery(String sql, String name, BizData data)
			throws SQLException {
		int rowsSelected = 0;

		Connection conn = null;
		try {
			conn = getConnection();
			rowsSelected = this.executeQuery(sql, name, data, conn);
		} finally {
			if (null != conn) {
				conn.close();
			}
		}
		return rowsSelected;
	}

	/**
	 * @param sql
	 * @param name
	 * @param pageNO
	 * @param pageSize
	 * @param data
	 * @return
	 */
	public int executeQueryPage(String sql, String name, int pageNO,
			int pageSize, BizData data) throws SQLException {
		int rowsSelected = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			rowsSelected = this.executeQueryPage(sql, name, pageNO, pageSize,
					data, conn);
		} finally {
			if (null != conn) {
				conn.close();
			}
		}
		return rowsSelected;
	}

	/**
	 * Run a well formatted query sql and return the ResultSet Object.
	 * 
	 * @param sql
	 * @return ResultSet Object;
	 */
	private ResultSet executeQuery(String sql) throws SQLException {
		ResultSet rs = null;
		Connection conn = getConnection();
		
		Statement stmt = conn.createStatement();// can't close the stmt here;
		rs = stmt.executeQuery(sql);
		
		return rs;
	}

	/**
	 * Run a query sql on a connection; A statement is created here.The caller
	 * of the function should close returned ResultSet.
	 * 
	 * @param sql
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String sql, Connection conn)
			throws SQLException {
		Statement stmt = conn.createStatement();
		return stmt.executeQuery(sql);
	}

	/* execute oracle 9i storage process */

	/**
	 * directly execute a sql to update records in a table.
	 * 
	 * @param sql
	 * @param data
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public int executeUpdate(String sql, BizData data, Connection conn)
			throws SQLException {
		int rowsAffected = 0;
		Statement stmt = null;
		if (log.isDebugEnabled()) {
			log.debug("begin to execute update sql in dao " + daoDef.getName()
					+ ":" + sql);
		}

		try {
			stmt = conn.createStatement();
			rowsAffected = stmt.executeUpdate(sql);
			if (data != null) {
				data.add("rowsAffected", new Integer(rowsAffected));
			}
		} catch (SQLException e) {
			log.error("SQLException in executeUpdate() in dao "
					+ daoDef.getName() + ",target sql:" + sql, e);
			throw e;
		} finally {
			/** close the statement here */
			if (stmt != null) {
				stmt.close();
			}
		}

		return rowsAffected;
	}

	public int executeUpdate(String sql, Connection conn) throws SQLException {
		int rowsAffected = 0;
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			rowsAffected = stmt.executeUpdate(sql);
			log.debug("the SQL is: "+sql);
		} catch (SQLException e) {
			log.error("SQLException in executeUpdate in dao "
					+ daoDef.getName() + ",target sql:" + sql, e);
			throw e;
		} finally {
			/** close the statement here */
			if (stmt != null) {
				stmt.close();
			}
		}

		return rowsAffected;
	}

	/**
	 * execute a update sql and set the rowsAffected into the data object with
	 * the name of rowsAffected. This sql will be run on a connection geo from
	 * the connection pool.
	 * 
	 * @param sql
	 * @param data
	 * @return count of records updated.
	 */
	public int executeUpdate(String sql, BizData data) throws SQLException {
		int rowsAffected = 0;
		Connection conn = null;

		try {
			conn = getConnection();
			this.beginTrasct(conn);
			rowsAffected = this.executeUpdate(sql, data, conn);
			this.commitTrasct(conn);
		} catch (SQLException sqle) {
			this.rollbackTrasct(conn);
			throw sqle;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return rowsAffected;
	}

	public int executeUpdate(String sql) throws SQLException {
		int rowsAffected = 0;

		Connection conn = null;

		try {
			conn = getConnection();
			this.beginTrasct(conn);
			rowsAffected = this.executeUpdate(sql, null, conn);
			this.commitTrasct(conn);
		} catch (SQLException sqle) {
			this.rollbackTrasct(conn);
			throw sqle;
		} finally {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return rowsAffected;
	}

	/**
	 * expand a record in a data object. The record in the data object only
	 * contains part of the columns in the database. This function will find the
	 * record in the db tables and return all other columns. If multiple records
	 * in the db tables are found. then this function will return 0;
	 * 
	 * @param name
	 * @param data
	 * @return
	 */
	public int expand(String name, BizData data, BizData sd)
			throws SQLException {
		BizData d = new BizData();
		d.copyEntity(data, name);

		int count = select(name, d, sd, true);
		if (count != 1) {
			return 0;
		} else {
			data.copyEntity(d, name + "s", name);
			return 1;
		}
	}

	public int expand(String name, BizData data) throws SQLException {
		return expand(name, data, (BizData) null);
	}

	/**
	 * @param name
	 * @param data
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public int expand(String name, BizData data, BizData sd, Connection conn)
			throws SQLException {
		BizData d = new BizData();
		d.copyEntity(data, name);

		int count = select(name, d, sd, true, conn);
		if (count != 1) {
			return 0;
		} else {
			data.copyEntity(d, name + "s", name);
			return 1;
		}
	}

	/**
	 * @param name
	 * @param data
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public int expand(String name, BizData data, Connection conn)
			throws SQLException {
		return expand(name, data, null, conn);
	}

	/**
	 * Get a field value from a table in the database.If multiple results are
	 * returned, then the field of the first row is returned.
	 * 
	 * @param tableName
	 *            the name of the table;
	 * @param fieldName
	 *            the name of the field,it can be as the following:
	 *            count(field),max(field),min(field),avg(field) and so on.
	 * @param where
	 *            the where clause excluding the 'where' key word.
	 * @param type
	 *            the field value type,one of the
	 *            following:string,integer,float,long, double,Date.
	 * @return an Object according to the type.that is a String object for type
	 *         string, Integer object for the integer and so on. If no records
	 *         is found,then null is returned.
	 */
	public Object getFieldValue(String tableName, String fieldName,
			String where, String orderBy, int type) throws SQLException {
		StringBuffer sb = new StringBuffer(256);
		/**
		 * the tableName and the fieldName can't be null,if one of them is null,
		 * then return null directly;
		 */
		if (tableName == null || fieldName == null) {
			return null;
		}
		// generate the sql like select %fieldName from $tableName where $where
		sb.append("select ").append(fieldName).append(" from ").append(
				tableName);
		if (where != null) {
			sb.append(" where ").append(where);
		}
		if (orderBy != null) {
			sb.append(" order by ").append(orderBy);
		}

		ResultSet rs = null;
		try {

			rs = executeQuery(new String(sb));
			if (rs.next()) {
				switch (type) {
				case Types.INTEGER:
					int val = rs.getInt(1);
					return new Integer(val);
				case Types.STRING:
					return rs.getString(1);
				case Types.FLOAT:
					return new Float(rs.getFloat(1));
				case Types.DOUBLE:
					return new Double(rs.getDouble(1));
				case Types.LONG:
					return new Long(rs.getLong(1));
				case Types.DATE:
					return rs.getDate(1);
				default: // if the type is not unknow,return object directly;
					return rs.getObject(1);
				}
			} else {// resulet is null or has no records in the result.
				return null;
			}
		} catch (SQLException sqle) {
			log.error("SQLException in getFieldValue in dao "
					+ daoDef.getName(), sqle);
			// return null if sqlexception is thrown;
			throw sqle;
		} finally {
			try {
				// close the statement here to release database resources.
				if (rs != null) {
					Connection conn=rs.getStatement().getConnection();
					rs.getStatement().close();
					conn.close();
				}
			} catch (SQLException e2) {
			}
		}
	}

	/**
	 * @param tableName
	 * @param fieldName
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public Object getFieldValue(String tableName, String fieldName, int type)
			throws SQLException {
		return getFieldValue(tableName, fieldName, null, null, type);
	}

	/**
	 * @param tableName
	 * @param fieldName
	 * @param where
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public Object getFieldValue(String tableName, String fieldName,
			String where, int type) throws SQLException {
		return getFieldValue(tableName, fieldName, where, null, type);
	}

	/**
	 * get the count of rows in a table.
	 * 
	 * @param tableName
	 *            the name of the table;
	 * @param where
	 *            specify which rows to count.
	 * @return >=0 the count of rows. if there is no this table, or some other
	 *         sqlexception, then return -1;
	 */
	public int count(String tableName, String where) throws SQLException {
		int rows = -1;
		/** check the table name */
		if (tableName == null) {
			return rows;
		}

		/** make the sql */
		StringBuffer sb = new StringBuffer(256);
		sb.append("select count(*) from ").append(tableName);
		if (where != null) {
			sb.append(" where ").append(where);
		}

		/** execute the sql */
		ResultSet rs = null;
		try {
			rs = executeQuery(new String(sb));
			if (rs != null && rs.next()) {
				rows = rs.getInt(1);
			} else {
				rows = 0;
			}
		} catch (SQLException sqle) {
			log.error("SQLException in selecting records from dao "
					+ daoDef.getName(), sqle);
			throw sqle;
		} finally {
			// close the statment;
			try {
				if (rs != null) { // close the statement here.
					rs.getStatement().close();
				}
			} catch (SQLException e2) {
				// sql exception in close the statement.
			}
		}
		return rows;
	}

	/**
	 * return the count of rows in a table;
	 * 
	 * @param table
	 *            the name fo the table;
	 * @return count of rows in the table.
	 */
	public int count(String table) throws SQLException {
		return count(table, null);
	}

	/**
	 * Update a BLob column of a record(records) in a db table. This function
	 * can update multiple records at a time according to the d(BizData);
	 * 
	 * @param tableName
	 *            the name of the table;
	 * @param fieldName
	 *            the name of the column which is of BLOB type.
	 * @param d
	 *            the BizData object.
	 * @param is
	 *            the input stream contains the value for the column;
	 * @param len
	 *            the length of the value in the is object;
	 * @return count of records be updated;
	 */
	public int updateBLob(String tableName, String fieldName, BizData d,
			InputStream is, int len) throws SQLException {
		/** generate the where clause */
		StringBuffer where = new StringBuffer(512);
		BizData sd = (BizData) d.get(SysVar.SS_DATA);
		iSQLBuilder.buildSelectSQL(tableName, "0", false, d, sd, dc, where, 10,
				10);

		if (where.length() > 1) {
			return updateBLob(tableName, fieldName, new String(where), is, len);
		} else {
			return updateBLob(tableName, fieldName, (String) null, is, len);
		}
	}

	/**
	 * @param tableName
	 * @param fieldName
	 * @param where
	 * @param is
	 * @param len
	 * @return
	 */
	public int updateBLob(String tableName, String fieldName, String where,
			InputStream is, int len) throws SQLException {
		int rows = 0;

		PreparedStatement stmt = null;
		/** check the table name and field name */
		if (tableName == null || fieldName == null) {
			return -1;
		}

		/** make the sql */
		StringBuffer sb = new StringBuffer(512);
		sb.append("update ").append(tableName).append(" set ")
				.append(fieldName);
		sb.append("=? ");
		if (where != null) {
			sb.append(where);
		}
		/** execute the sql */
		Connection conn = getConnection();
		try {
			beginTrasct(conn);
			stmt = conn.prepareStatement(new String(sb));
			stmt.setBinaryStream(1, is, len);
			rows = stmt.executeUpdate();
			commitTrasct(conn);
		} catch (SQLException sqle) {
			log.error("SQLException in updating records in dao "
					+ daoDef.getName(), sqle);
			rollbackTrasct(conn);
			throw sqle;
		} finally {
			/** close the dblink if necessary */
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

		return rows;
	}

	/**
	 * Update a BLob column of a record(records) in a db table. This function
	 * can update multiple records at a time according to the d(BizData);
	 * 
	 * @param tableName
	 *            the name of the table;
	 * @param fieldName
	 *            the name of the column which is of BLOB type.
	 * @param d
	 *            the BizData object.
	 * @param r
	 *            the input stream contains the value for the column;
	 * @param len
	 *            the length of the value in the is object;
	 * @return count of records be updated;
	 */
	public int updateCLob(String tableName, String fieldName, BizData d,
			Reader r, int len) throws SQLException {
		/** generate the where clause according to the BizData object */
		StringBuffer where = new StringBuffer(512);
		// we doest not want build the select statement, just want to get the
		// {where}
		BizData sd = (BizData) d.get(SysVar.SS_DATA);
		iSQLBuilder.buildSelectSQL(tableName, "0", false, d, sd, dc, where, 10,
				10);

		/** make the sql */
		if (where.length() > 0) {
			return updateCLob(tableName, fieldName, new String(where), r, len);
		} else {
			return updateCLob(tableName, fieldName, (String) null, r, len);
		}
	}

	/**
	 * @param tableName
	 * @param fieldName
	 * @param where
	 * @param r
	 * @param len
	 * @return
	 */
	public int updateCLob(String tableName, String fieldName, String where,
			Reader r, int len) throws SQLException {
		int rows = 0;

		PreparedStatement stmt = null;
		/** check the table name and field name */
		if (tableName == null || fieldName == null) {
			return -1;
		}

		/** make the sql */
		StringBuffer sb = new StringBuffer(512);
		sb.append("update ").append(tableName).append(" set ")
				.append(fieldName);
		sb.append("=? ");
		if (where != null) {
			sb.append(where);
		}

		/** execute the sql */
		Connection conn = getConnection();
		try {
			beginTrasct(conn);
			stmt = conn.prepareStatement(new String(sb));
			stmt.setCharacterStream(1, r, len);
			rows = stmt.executeUpdate();
			commitTrasct(conn);
		} catch (SQLException sqle) {
			log.error("SQLException in updating records in dao "
					+ daoDef.getName(), sqle);
			rollbackTrasct(conn);
			throw sqle;
		} finally {
			/** close the statement if necessary */
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return rows;
	}

	/**
	 *
	 */
	public int dmTable(String tableName, BizData data, BizData sd,
			Connection conn) throws SQLException {
		int i = 0;
		int len = 0;
		int rowsAffected = 0;

		String[] rowIDs = data.getRowIDs(tableName);
		if (rowIDs == null || (len = rowIDs.length) == 0) {
			return 0;
		}
		BizData d = new BizData();
		while (i < len) {
			// get the dml of the entity row;
			String rowIndex = new Integer(i).toString();
			String dml = (String) data.getRowAttr(tableName, rowIndex, "DML");
			d.clear();
			d.copyEntityRow(data, tableName, rowIndex);
			if (dml != null) {
				if (dml.compareToIgnoreCase("select") == 0) {
					select(data, d, sd, true, conn);
				} else if (dml.compareToIgnoreCase("insert") == 0) {
					rowsAffected += insert(d, conn);
				} else if (dml.compareToIgnoreCase("update") == 0) {
					rowsAffected += update(d, sd, false, conn);
				} else if (dml.compareToIgnoreCase("delete") == 0) {
					rowsAffected += delete(d, sd, conn);
				}
			}
			i++;
		}

		return rowsAffected;
	}

	/**
	 * @param tableName
	 * @param data
	 * @return
	 */
	public int dmTable(String tableName, BizData data) throws SQLException {
		int rowsInserted = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			this.beginTrasct(conn);
			rowsInserted = dmTable(tableName, data, null, conn);
			this.commitTrasct(conn);
		} catch (SQLException sqle) {
			this.rollbackTrasct(conn);
			throw sqle;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return rowsInserted;
	}

	/**
	 * @param tableName
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public int dmTable(String tableName, BizData data, BizData sd)
			throws SQLException {
		int rowsInserted = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			this.beginTrasct(conn);
			rowsInserted = dmTable(tableName, data, sd, conn);
			this.commitTrasct(conn);
		} catch (SQLException sqle) {
			this.rollbackTrasct(conn);
			throw sqle;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return rowsInserted;
	}

	/**
	 * 
	 * @param conn
	 *            the Connection object;
	 */
	public int dm(BizData data, Connection conn) throws SQLException {
		int i = 0;
		int rowsUpdated = 0;

		// get tables in the bizdata object to be updated;
		Vector entities = data.getTables();
		if (entities == null || entities.size() == 0) {
			return 0;
		}

		int countOfEntities = entities.size();
		while (i < countOfEntities) {
			// get the next object in the BizData Object;
			String name = (String) entities.elementAt(i);
			rowsUpdated += dmTable(name, data, null, conn);
			i++;
		}
		return rowsUpdated;
	}

	public int dm(BizData data, BizData sd, Connection conn)
			throws SQLException {
		int i = 0;
		int rowsUpdated = 0;

		// get tables in the bizdata object to be updated;
		Vector entities = data.getTables();
		if (entities == null || entities.size() == 0) {
			return 0;
		}

		int countOfEntities = entities.size();
		while (i < countOfEntities) {
			// get the next object in the BizData Object;
			String name = (String) entities.elementAt(i);
			rowsUpdated += dmTable(name, data, sd, conn);
			i++;
		}
		return rowsUpdated;
	}

	/**
     *
     *
     */
	public int dm(BizData data) throws SQLException {
		int rowsInserted = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			this.beginTrasct(conn);
			rowsInserted = dm(data, conn);
			this.commitTrasct(conn);
		} catch (SQLException sqle) {
			this.rollbackTrasct(conn);
			throw sqle;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return rowsInserted;
	}

	/**
	 * @param data
	 * @param sd
	 * @return
	 * @throws SQLException
	 */
	public int dm(BizData data, BizData sd) throws SQLException {
		int rowsInserted = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			this.beginTrasct(conn);
			rowsInserted = dm(data, sd, conn);
			this.commitTrasct(conn);
		} catch (SQLException sqle) {
			this.rollbackTrasct(conn);
			throw sqle;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return rowsInserted;
	}

	/**
	 * begin a transaction on a specified Connection.
	 * 
	 * @param conn
	 *            Connection object.
	 * @return true if a transaction is really begined, false otherwise;
	 */
	public boolean beginTrasct(Connection conn) throws SQLException {
		try {
			if (conn != null && conn.getAutoCommit() == true) {
				//if (!daoDef.isUsingJTA()) {
					conn.setAutoCommit(false);
				//}
				return true;
			} else {
				return false;
			}
		} catch (SQLException sqle) {
			throw sqle;
		}
	}

	/**
	 * commit transaction on the specified Connection object;
	 * 
	 * @param conn
	 *            Connection object;
	 * @return true if a transaction is really commited. false otherwise;
	 */
	public boolean commitTrasct(Connection conn) throws SQLException {
		try {
			if (conn != null && conn.getAutoCommit() == false) {
				//if (!daoDef.isUsingJTA()) {
					conn.commit();
					conn.setAutoCommit(true);
				//}
				return true;
			}
			return false;
		} catch (SQLException sqle) {
			throw sqle;
		}
	}

	/**
	 * rollback a transaction on the specified connection;
	 * 
	 * @param conn
	 *            Connection object,if null, false is returned;
	 * @return true if a transaction is really rollbacked. false otherwise;
	 */
	public boolean rollbackTrasct(Connection conn) throws SQLException {
		try {
			if (conn != null && conn.getAutoCommit() == false) {
				if (!daoDef.isUsingJTA()) {
					conn.rollback();
					conn.setAutoCommit(true);
				}
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * @param name
	 * @param tableName
	 * @param rs
	 * @param data
	 * @param sd
	 * @return
	 * @throws SQLException
	 */
	protected int addRS2BizData(String name, String tableName, ResultSet rs,
			BizData data, BizData sd) throws SQLException {
		return addRS2BizData(name, tableName, rs, data, sd, 50);
	}

	/**
	 * add records in a resultset to a BizData object under a table type entity
	 * of name 'name'
	 * 
	 * @param name
	 *            the name of the table type entity;
	 * @param rs
	 *            the resultset in wchich records will be added to the bizdata
	 *            object;
	 * @param data
	 *            the bizdata object which will accept the new records;
	 * @return count of records has been added.
	 * @throws SQLException
	 */
	protected int addRS2BizData(String name, String tableName, ResultSet rs,
			BizData data, BizData sd, int fetchSize) throws SQLException {
		int index = 1;
		int rowsInData = 0;
		String p = null;
		UserDataPrivil colPrivil = null;
		if (rs == null || data == null || name == null) {
			return 0;
		}

		ResultSetMetaData rsmd = rs.getMetaData();
		int columns = rsmd.getColumnCount();

		if (sd != null) {
			HashMap ups = (HashMap) sd.get("_dataPrivil");
			if (ups != null) {
				colPrivil = (UserDataPrivil) ups.get(this.name);
			}
		}

		String cName = name.toUpperCase();

		// get names of all the columns;
		ArrayList colNames = new ArrayList();
		while (index <= columns) {
			colNames.add(rsmd.getColumnName(index).toUpperCase());
			index++;
		}

		rs.setFetchSize(fetchSize);

		/* add all the records in the resultset to the bizdata object */
		index = (rowsInData = data.getTableRowsCount(name));
		while (rs.next()) {
			int colIndex = 1;
			while (colIndex <= columns) {
				Object fieldVal = rs.getObject(colIndex);
				if(null != fieldVal && fieldVal.getClass().getName().equals("oracle.sql.TIMESTAMP")){
					
					fieldVal = rs.getTimestamp(colIndex);
				}
				if(null != fieldVal && fieldVal.getClass().getName().equals("java.sql.Date")){
					/*fieldVal = rs.getTimestamp(colIndex);
					if((fieldVal.toString()).indexOf("00:00:00") < 0)
						fieldVal = rs.getTimestamp(colIndex);
					else*/
						fieldVal = rs.getDate(colIndex);
				}
				p = null;
				if (colPrivil != null) {
					p = (String) colPrivil.getColPrivil(tableName, (String) colNames.get(colIndex - 1));
				}
				// if p!=null && p.compareToIgnoreCase("N")==0),then this field
				// value will not be added;
				if (p == null || p.compareToIgnoreCase("N") != 0) {
					data.add2(cName, (String) colNames.get(colIndex - 1), index, fieldVal);
				} else { // add marked string instead;
					data.add2(cName, (String) colNames.get(colIndex - 1),
							index, DAO.HIDDEN_FIELD_MASK);
				}

				colIndex++;
			}
			index++;
		}
		return index - rowsInData;
	}

	/**
	 * @param name
	 * @param tableName
	 * @param rs
	 * @param page
	 * @param PAGE_SIZE
	 * @param rows
	 * @param data
	 * @param sd
	 * @return
	 * @throws SQLException
	 */
	protected int addRS2BizData(String name, String tableName, ResultSet rs,
			int page, int PAGE_SIZE, int rows, BizData data, BizData sd)
			throws SQLException {
		return addRS2BizData(name, tableName, rs, page, PAGE_SIZE, rows, data,
				sd, 50);
	}

	/**
	 * add a specified page of the resultset to the BizData;
	 */
	protected int addRS2BizData(String name, String tableName, ResultSet rs,
			int page, int PAGE_SIZE, int rows, BizData data, BizData sd,
			int fectchSize) throws SQLException {
		int index = 1;
		int pages = 0;
		UserDataPrivil colPrivil = null;

		if (page < 1) {
			page = 1;
		}
		String p = null;
		String cName = name.toUpperCase();

		if (rs == null || data == null) {
			return 0;
		}
		if (sd != null) {
			HashMap ups = (HashMap) sd.get("_dataPrivil");
			if (ups != null) {
				colPrivil = (UserDataPrivil) ups.get(this.name);
			}
		}

		ResultSetMetaData rsmd = rs.getMetaData();
		int columns = rsmd.getColumnCount();

		// get names of all the columns;
		ArrayList colNames = new ArrayList();
		while (index <= columns) {
			colNames.add(rsmd.getColumnName(index).toUpperCase());
			index++;
		}

		// compute count of pages;
		pages = (rows / PAGE_SIZE) + (rows % PAGE_SIZE == 0 ? 0 : 1);
		data.addAttr(name, "pagesCount", pages);
		/* add all the records in the resultset to the bizdata object */
		data.addAttr(name, "rowsCount", rows);

		index = 0;
		if (page > 1)
			rs.absolute((page - 1) * PAGE_SIZE);
		while (rs.next() && index < PAGE_SIZE) {
			int colIndex = 1;
			while (colIndex <= columns) {
				Object fieldVal = rs.getObject(colIndex);
				if(null != fieldVal && fieldVal.getClass().getName().equals("java.sql.Timestamp")){
					fieldVal = rs.getTimestamp(colIndex);
					if((fieldVal.toString()).indexOf("00:00:00") < 0)
						fieldVal = rs.getTimestamp(colIndex);
					else
						fieldVal = rs.getDate(colIndex);
				}
				p = null;
				if (colPrivil != null) {
					p = (String) colPrivil.getColPrivil(tableName,
							(String) colNames.get(colIndex - 1));
				}
				// if p!=null && p.compareToIgnoreCase("N")==0),then this field
				// value will not be added;
				if (p == null || p.compareToIgnoreCase("N") != 0) {
					data.add2(cName, (String) colNames.get(colIndex - 1),
							index, fieldVal);
				} else { // add marked string instead;
					data.add2(cName, (String) colNames.get(colIndex - 1),
							index, DAO.HIDDEN_FIELD_MASK);
				}

				colIndex++;
			}
			index++;
		}
		return index;
	}

	/**
	 * get the {from clause} from a select sql statement; This function will
	 * take the first " from " as the begining of the from clause.
	 */
	protected String getFromClause(String sql) {
		String sql2 = sql.toLowerCase();
		int index = sql2.indexOf(" from ");
		if (index < 0) {
			return null;
		} else {
			int i1 = sql2.lastIndexOf(" order by ");
			int i2 = sql2.lastIndexOf(" group by ");

			if (i1 >= 0 && i2 >= 0) {
				return sql.substring(index, i1 > i2 ? i2 : i1);
			} else if (i1 >= 0) {
				return sql.substring(index, i1);
			} else if (i2 >= 0) {
				return sql.substring(index, i2);
			} else {
				return sql.substring(index);
			}
		}
	}

	/**
	 * @param stmt
	 * @param index
	 * @param type
	 * @param value
	 * @throws SQLException
	 */
	private void setField(PreparedStatement stmt, int index, int type,
			Object value) throws SQLException, TypeMismatchException {
		if (null == value || "".equals(value)) {
			stmt.setNull(index, Types.toJDBCType(type));
			return;
		}

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		int len = 0;
		String name = value.getClass().getName();
		Object valueReal = null;
		// integer field;
		switch (type) {
		case Types.BYTE:
			if (value instanceof java.lang.String) {
				valueReal = Byte.valueOf((String) value);
			} else {
				throw new TypeMismatchException("{field}", "Byte", name);
			}
			stmt.setByte(index, ((Byte) valueReal).byteValue());
			break;
		case Types.CHAR:
			stmt.setString(index, (String) value);
			break;
		case Types.DATE:
		case Types.TIME:
		case Types.TIMESTAMP:
			if (value instanceof java.lang.String) {
				try {
					if (type == Types.DATE) {
						stmt.setDate(index, new Date(sdfDate.parse((String) value).getTime()));
					} else if (type == Types.TIME){

						stmt.setTime(index, Time.valueOf((String) value));
					} else {
						stmt.setTimestamp(index, Timestamp.valueOf((String) value));
					}
				} catch (ParseException ps) {
					throw new SQLException("Error:Date or time format error.");
				}
			} else if (value instanceof java.util.Date) {
				stmt.setDate(index, new Date(((java.util.Date) value).getTime()));
			} else {
				throw new TypeMismatchException("{field}", "Byte", name);
			}
			break;
		case Types.DOUBLE:
			if (value instanceof java.lang.String) {
				valueReal = Double.valueOf((String) value);
			} else if (value instanceof java.lang.Double) {
				valueReal = value;
			} else if (value instanceof java.lang.Integer) {
				valueReal = new Double(((Integer) value).doubleValue());
			} else if (value instanceof java.lang.Float) {
				valueReal = new Double(((Float) value).doubleValue());
			} else {
				throw new TypeMismatchException("{field}", "Double", name);
			}
			stmt.setDouble(index, ((Double) valueReal).doubleValue());
			break;
		case Types.INTEGER:
			if (value instanceof java.lang.String) {
				valueReal = Integer.valueOf((String) value);
			} else if (value instanceof java.lang.Double) {
				valueReal = new Integer(((Double) value).intValue());
			} else if (value instanceof java.lang.Integer) {
				valueReal = value;
			} else if (value instanceof java.lang.Float) {
				valueReal = new Integer(((Float) value).intValue());
			} else {
				throw new TypeMismatchException("{field}", "Double", name);
			}
			stmt.setInt(index, ((Integer) valueReal).intValue());
			break;
		case Types.FLOAT:
			if (value instanceof java.lang.String) {
				valueReal = Float.valueOf((String) value);
			} else if (value instanceof java.lang.Float) {
				valueReal = value;
			} else if (value instanceof java.lang.Integer) {
				valueReal = new Float(((Integer) value).floatValue());
			} else if (value instanceof java.lang.Double) {
				valueReal = new Float(((Double) value).floatValue());
			} else {
				throw new TypeMismatchException("{field}", "Double", name);
			}
			break;
		case Types.LONG:
			if (value instanceof java.lang.String) {
				valueReal = Long.valueOf((String) value);
			} else if (value instanceof java.lang.Float) {
				valueReal = new Long(((Float) value).longValue());
			} else if (value instanceof java.lang.Integer) {
				valueReal = new Long(((Integer) value).longValue());
			} else if (value instanceof java.lang.Double) {
				valueReal = new Long(((Double) value).longValue());
			} else {
				throw new TypeMismatchException("{field}", "Long", name);
			}
			stmt.setLong(index, ((Long) valueReal).longValue());
			break;
		case Types.SHORT:
			if (value instanceof java.lang.String) {
				valueReal = Short.valueOf((String) value);
			} else if (value instanceof java.lang.Float) {
				valueReal = new Short(((Float) value).shortValue());
			} else if (value instanceof java.lang.Integer) {
				valueReal = new Short(((Integer) value).shortValue());
			} else if (value instanceof java.lang.Double) {
				valueReal = new Short(((Double) value).shortValue());
			} else {
				throw new TypeMismatchException("{field}", "Short", name);
			}
			stmt.setShort(index, ((Short) valueReal).shortValue());
			break;
		case Types.STRING:
			if (value instanceof java.lang.String) {
				valueReal = (String) value;
			} else if (value instanceof java.lang.Float) {
				valueReal = (value).toString();
			} else if (value instanceof java.lang.Integer) {
				valueReal = (value).toString();
			} else if (value instanceof java.util.Date) {
				valueReal = value.toString();
			} else {
				// throw new TypeMismatchException("{field}", "String", name);
				valueReal = (value).toString();
			}
			stmt.setString(index, (String) valueReal);
			break;
		case Types.CHARSTREAM:
			CharArrayReader r = null;
			len = 0;
			if (value instanceof byte[]) {
				byte[] b = (byte[]) value;
				char[] chs = new String(b).toCharArray();
				r = new CharArrayReader(chs);
				len = chs.length;
			} else if (value instanceof char[]) {
				char[] v = (char[]) value;
				r = new CharArrayReader(v);
				len = v.length;
			} else {
				r = new CharArrayReader(new char[0]);
			}
			// stmt.setBinaryStream(index,is,len);
			stmt.setCharacterStream(index, r, len);
			break;
		case Types.BINARY:
		case Types.BINARYSTREAM:
			InputStream is = null;
			if (value instanceof byte[]) {
				byte[] b = (byte[]) value;
				is = new ByteArrayInputStream(b);
				len = b.length;
			} else if (value instanceof ByteArrayInputStream) {
				is = (ByteArrayInputStream) value;
				len = ((ByteArrayInputStream) value).available();
			} else {
				is = new ByteArrayInputStream(new byte[0]);
			}
			stmt.setBinaryStream(index, is, len);
			break;
		default:
			stmt.setObject(index, value);
			break;
		}
	}
}
