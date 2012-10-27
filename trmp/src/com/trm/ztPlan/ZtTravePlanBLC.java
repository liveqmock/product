package com.trm.ztPlan;

import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import java.sql.Connection;

public class ZtTravePlanBLC extends DBBLC {
	public ZtTravePlanBLC() {
		this.entityName = "TA_ZT_JHDJ";
	}

	public int initGroundTrave(BizData rd, BizData sd) {
		String groupID = rd.getStringByDI("TA_ZT_JHDJ", "TID", 0);
		String sqlGroundList = "select d.id,d.tid,d.sfid,d.csid,d.DJSID,d.YFZK,d.bz,b.cmpny_name,b.TRAVEL_AGC_ID\n"
				+ "  from TA_ZT_JHDJ d ,ta_travelagency b \n "
				+ " where  d.djsid=b.travel_agc_id "
				+ " and  d.tid='"
				+ groupID + "'";
		try {
			coreDAO.executeQuery(sqlGroundList, "sqlGroundList", rd);// 查询所有地接社信息
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
	}
	public int generalGroundTrave(BizData rd, BizData sd) {
		Connection conn = coreDAO.getConnection();
		String groupID = rd.getStringByDI("TA_ZT_JHDJ", "TID", 0);
		rd.add("groupId", groupID);
		String[] traveRows = rd.getRowIDs("TA_ZT_JHDJ");
		try {
			coreDAO.beginTrasct(conn);
			if ("Edit".equals(rd.getString("state"))) {
				BizData del1 = new BizData();
				del1.add("TA_ZT_JHDJ", "TID", groupID);
				coreDAO.delete(del1, conn);
				del1.remove("TA_ZT_JHDJ");
			}
			for (int i = 0; i < traveRows.length; i++) {
				int traveID = this.queryMaxIDByPara("TA_ZT_JHDJ", "ID",
						null);
				rd.add("TA_ZT_JHDJ", "TID", i, groupID);
				rd.add("TA_ZT_JHDJ", "ID", i, traveID);
			}
			coreDAO.insert("TA_ZT_JHDJ", rd, conn);
			rd.remove("TA_ZT_JHDJ");
			updateTDJDXXZJB(rd, conn, groupID);
			BizData dtt = new BizData();
			dtt.addAttr("TA_ZT_GROUP", "ID", 0, "oldValue", groupID);
			dtt.add("TA_ZT_GROUP", "STATE", "2");//修改团状态为 2  实施中
			coreDAO.update("TA_ZT_GROUP", dtt, conn);
			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (null != conn) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 999;
	}

	public void updateTDJDXXZJB(BizData rd, Connection conn, String groupID)
			throws SQLException {
		BizData data = new BizData();
		data.add("TA_TDJDXXZJB_ZT", "TID", groupID);
		coreDAO.select(data);
		int TDJDrows = data.getTableRowsCount("TA_TDJDXXZJB_ZTs");
		data.remove("TA_TDJDXXZJB_ZT");
		data.remove("TA_TDJDXXZJB_ZTs");
		if (TDJDrows > 0) {
			rd.addAttr("TA_TDJDXXZJB_ZT", "TID", 0, "oldValue", groupID);
			coreDAO.update("TA_TDJDXXZJB_ZT", rd, conn);
		} else {
			rd.add("TA_TDJDXXZJB_ZT", "TID", groupID);
			coreDAO.insert("TA_TDJDXXZJB_ZT", rd, conn);
		}
	}

}
