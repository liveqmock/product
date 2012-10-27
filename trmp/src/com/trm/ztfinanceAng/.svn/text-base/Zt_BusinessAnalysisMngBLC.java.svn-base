package com.trm.ztfinanceAng;

import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


public class Zt_BusinessAnalysisMngBLC extends DBBLC {
	
	public Zt_BusinessAnalysisMngBLC(){
		
		entityName="drmuser";
	}
	
	public int getztBusinessName(BizData rd,BizData sd){
		
		String sql="select u.username,u.userno from drmuser u,drmuserrole r where u.userid=r.userid and r.roleid='planAndAttemper'";
		try {
			coreDAO.executeQuery(sql, "getztBusinessName", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	

}