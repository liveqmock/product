/**
 * 
 */
package com.dream.bizsdk.common.database.dblink;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.database.dao.DAODef;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
/**
 * @author Divine
 *
 */
public final class DBConnectionMgmt {

	private static DBConnectionMgmt connMgmt = null;
	private ComboPooledDataSource cds = null;
	private Logger log;
	
	private DBConnectionMgmt(DAODef daoDef) throws PropertyVetoException {
		
		this.log = Logger.getLogger(DAO.class);
		
		cds = new ComboPooledDataSource();
		cds.setDriverClass(daoDef.getDriver());
		cds.setUser(daoDef.getDBUser());
		cds.setPassword(daoDef.getDBPassword());
		cds.setJdbcUrl(daoDef.getDBURL());
		cds.setInitialPoolSize(daoDef.get_initialSize()); // ��ʼ����������
		cds.setMinPoolSize(daoDef.get_minPoolSize());// ����С������
		cds.setMaxPoolSize(daoDef.get_maxPoolSize());
		cds.setMaxStatements(daoDef.get_maxStatements());
		cds.setMaxIdleTime(daoDef.get_maxIdleTime());
		cds.setTestConnectionOnCheckout(true);
		cds.setMaxStatements(0);
	}
	
	public static final synchronized DBConnectionMgmt getInstance(DAODef daoDef) throws PropertyVetoException {
		
		if(null == connMgmt)
			connMgmt = new DBConnectionMgmt(daoDef);
		return connMgmt;
	}
	
	public final Connection getConnection() {
		
		try {
			//log.debug("�����ӳ��л�ȡ���ӿ�ʼ��"+connMgmt.getClass().toString()+" �ڴ�飺"+connMgmt.toString()+"��������"+cds.getNumConnections());
			Connection conn = cds.getConnection();
			//log.debug("�����ӳ��л�ȡ�������ӣ�"+connMgmt.getClass().toString()+" �ڴ�飺"+connMgmt.toString()+"��������"+cds.getNumConnections());
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void finalize() {
		
		try {
			DataSources.destroy(cds);
			super.finalize();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	
}
