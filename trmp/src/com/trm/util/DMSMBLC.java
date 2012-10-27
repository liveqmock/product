package com.trm.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/*
 * ���ñ�������
 * */
public class DMSMBLC extends DBBLC {
	private Logger log = null;
	
	public DMSMBLC(){		
		this.log = Logger.getLogger(DMSMBLC.class);
		this.entityName = "DMSM";		
	}
	
	private int loadDMSM(BizData rd) {
		
		Connection conn = coreDAO.getConnection();
		try{
			this.loadDMSM(rd, "FBJH", rd.getString("DMSM", "FBJH", 0), conn);//����ƻ�
			this.loadDMSM(rd, "JTGJ", rd.getString("DMSM", "JTGJ", 0), conn);//��ͨ����
			this.loadDMSM(rd, "XLLX", rd.getString("DMSM", "XLLX", 0), conn);//��·����
			this.loadDMSM(rd, "JGLX", rd.getString("DMSM", "JGLX", 0), conn);//�۸�����
			this.loadDMSM(rd, "XLZT", rd.getString("DMSM", "XLZT", 0), conn);//��·״̬
			this.loadDMSM(rd, "JDXJ", rd.getString("DMSM", "JDXJ", 0), conn);//�Ƶ��Ǽ�
			this.loadDMSM(rd, "ZCLX", rd.getString("DMSM", "ZCLX", 0), conn);//�������
			this.loadDMSM(rd, "KHYH", rd.getString("DMSM", "KHYH", 0), conn);//��������
			this.loadDMSM(rd, "CTCB", rd.getString("DMSM", "CTCB", 0), conn);//�����ͱ�
			this.loadDMSM(rd, "HOTELJGLX", rd.getString("DMSM", "HOTELJGLX", 0), conn);//�Ƶ�۸�����
			this.loadDMSM(rd, "SCJGLX", rd.getString("DMSM", "SCJGLX", 0), conn);//�Ƶ�۸�����
			this.loadDMSM(rd, "CLLX", rd.getString("DMSM", "CLLX", 0), conn);//��������
			this.loadDMSM(rd, "BXLX", rd.getString("DMSM", "BXLX", 0), conn);//��������
			this.loadDMSM(rd, "XLQY", rd.getString("DMSM", "XLQY", 0), conn);//��·����
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try{
				if(conn != null){
					conn.close();
					conn = null;
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		return 98;
	}
	public int loadDMSM(BizData rd, BizData sd) throws SQLException {
		return loadDMSM(rd);
	}
	
	//����˵��
	private int loadDMSM(BizData rd, java.lang.String rsName, java.lang.String param, Connection conn) throws SQLException {
		if(!param.equals("")){
			String sqlStr = "select DMSM1,DMLB,DMZ,DMSM2,YXBZ from DMSM where DMLB='"
				+ param + "' and YXBZ='1' order by dmlb,dmz";
			return coreDAO.executeQuery(sqlStr, rsName, rd, conn);
		}
		return 11;
	}
	
	//������Ŀ
	private int loadDMSM2(BizData rd, java.lang.String rsName, java.lang.String param,String dmsm2, Connection conn) throws SQLException {
		if(!param.equals("")){
			String sqlStr = "select DMSM1,DMLB,DMZ,DMSM2,YXBZ from DMSM where DMLB='"
				+ param + "' and dmsm2 = '" + dmsm2 + "' and YXBZ='1' order by dmlb,dmz";
			return coreDAO.executeQuery(sqlStr, rsName, rd, conn);
		}
		return 11;
	}
	
}
