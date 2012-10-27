package com.trm.ztPlan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class ZtVehPlanBLC extends DBBLC {
	
	public ZtVehPlanBLC() {
		this.entityName="TA_ZT_JHCL";
	}
	
	public int queryByGroupID(BizData rd, BizData sd) {
		
		rd.add("flag", rd.getString("flag"));
		String groupID = rd.getStringByDI("TA_ZT_JHCL", "tid", 0);
		try {
			int rows = query(rd, sd);
			if(rows == 0){
				rd.add("tid", groupID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 8;
	}
	
	/**
	 * 保存车调计划
	 * @return
	 */
	public int ztSaveVehPlan(BizData rd, BizData sd) {
		
		Connection conn = coreDAO.getConnection();
		String[] rowIDs = rd.getRowIDs("TA_ZT_JHCL");
		try {
			coreDAO.beginTrasct(conn);
			//删除团队原来的车调计划
			String groupID = rd.getStringByDI("TA_ZT_JHCL", "TID", 0);
			rd.add("groupId", groupID);
			BizData data = new BizData();
			data.add("TA_ZT_JHCL", "TID", groupID);
			coreDAO.delete(data, conn);
			data.remove("TA_ZT_JHCL");
			//添加新的车调计划
			for(int i=0;i<rowIDs.length;i++) {
				int id = queryMaxIDByPara("TA_ZT_JHCL", "ID", null);
				rd.add("TA_ZT_JHCL", "id", rowIDs[i], id);
				rd.add("TA_ZT_JHCL", "qdje", rowIDs[i], rd.getString("TA_ZT_JHCL","qdje",rowIDs[i]).equals("")?"0":rd.getString("TA_ZT_JHCL","qdje",rowIDs[i]));
			}
			coreDAO.insert("TA_ZT_JHCL", rd, conn);
			rd.remove("TA_ZT_JHCL");
			//更新团表中的费用总计
			updateTDJDXXZJB(rd, conn, groupID, data);
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
		return 9;
	}

	/**更新团表中的费用总计
	 * @param rd
	 * @param conn
	 * @param groupID
	 * @param data
	 * @throws SQLException
	 */
	public void updateTDJDXXZJB(BizData rd, Connection conn, String groupID,
			BizData data) throws SQLException {
		data.add("TA_TDJDXXZJB_ZT", "TID", groupID);
		coreDAO.select(data);
		int rows = data.getTableRowsCount("TA_TDJDXXZJB_ZTs");
		data.remove("TA_TDJDXXZJB_ZTs");
		if(rows > 0){
			rd.addAttr("TA_TDJDXXZJB_ZT", "TID", 0, "oldValue", groupID);
			coreDAO.update("TA_TDJDXXZJB_ZT", rd, conn);
		}else{
			rd.add("TA_TDJDXXZJB_ZT", "TID", groupID);
			coreDAO.insert("TA_TDJDXXZJB_ZT",rd, conn);
		}
	}
}
