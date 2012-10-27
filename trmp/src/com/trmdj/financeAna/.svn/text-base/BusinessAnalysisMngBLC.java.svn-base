package com.trmdj.financeAna;

import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


public class BusinessAnalysisMngBLC extends DBBLC {
	
	public BusinessAnalysisMngBLC(){
		
		entityName="drmuser";
	}
	
	public int getBusinessName(BizData rd,BizData sd){
		
		String sql="select u.username,u.userno from drmuser u,drmuserrole r where u.userid=r.userid and r.roleid='planAndAttemper'";
		try {
			coreDAO.executeQuery(sql, "getBusinessName", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	

}