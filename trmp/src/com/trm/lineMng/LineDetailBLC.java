package com.trm.lineMng;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class LineDetailBLC extends DBBLC {
	
	private Logger log = null;
	public LineDetailBLC(){		
		this.log = Logger.getLogger(LineDetailBLC.class);
		this.entityName = "TA_ZT_LINEDETAIL";		
	}
	public int queryLineBlob(BizData rd,BizData sd) throws SQLException{
		String lineId = rd.getString("LINE_ID");
		String sql = "select to_number(a.rq) rqp,a.* from TA_ZT_LINEDETAIL a  \n" +
					 "where a.lineid='"+lineId+"' \n" +
					 "order by rqp ";
		coreDAO.executeQuery(sql, "TA_ZT_LINEDETAILs", rd);
		return 999;
	}
}
