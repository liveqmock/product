package com.baseMng;

import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class GroupPrefixBLC extends DBBLC {
	public GroupPrefixBLC() {
		this.entityName = "TA_GROUPNUMROLE";
	}
	
	public int query(BizData rd,BizData sd){
		
		rd.add("TA_GROUPNUMROLE", "orgid", sd.getString("orgid"));
		try {
			super.query(rd, sd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 88;
	}

}
