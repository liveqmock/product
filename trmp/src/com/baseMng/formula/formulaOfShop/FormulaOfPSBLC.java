package com.baseMng.formula.formulaOfShop;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class FormulaOfPSBLC extends DBBLC {
	public FormulaOfPSBLC() {
		this.entityName = "TA_SHOPFORMULA";
	}

	public int query(BizData rd, BizData sd) {
		rd.add("TA_SHOPFORMULA", "orgid", sd.getString("orgid"));
		try {
			coreDAO.select(rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 8;
	}
	
	public int insert(BizData rd, BizData sd){
		
		Connection conn = coreDAO.getConnection();
		try{
			coreDAO.beginTrasct(conn);
			BizData data = new BizData();
			data.copyEntityField(rd, "TA_SHOPFORMULA", "orgid");
			data.copyEntityField(rd, "TA_SHOPFORMULA", "id");
			coreDAO.delete(data, conn);
			data.remove("TA_SHOPFORMULA");
			data = null;
			String rowIds[] = rd.getRowIDs("TA_SHOPFORMULA");
			for(int i=0;i<rowIds.length;i++){
				
				int newID = queryMaxIDByPara("TA_SHOPFORMULA", "id", null);
				rd.add("TA_SHOPFORMULA", "id", rowIds[i], newID);
			}
			coreDAO.insert(rd, conn);
			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try{
				if(null != conn)
					conn.close();
			}catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return 9;
	}
}
