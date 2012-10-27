package com.trm.ztPlan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class ZtTicketPlanBLC extends DBBLC {
	
	public ZtTicketPlanBLC() {
		this.entityName="TA_ZT_JHPW";
	}
	
	public int queryTicketPlan(BizData rd, BizData sd){
		rd.add("flag", rd.getString("flag"));
		String groupID = rd.getStringByDI("TA_ZT_JHPW", "tid", 0);
		try {
			int rows = query(rd, sd);
			if(rows == 0){
				rd.add("tid", groupID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 3;
	}
	
	public int saveTicket(BizData rd, BizData sd) {

		BizData data = new BizData();
		Connection conn = coreDAO.getConnection();
		String[] ticketRows = rd.getRowIDs("TA_ZT_JHPW");
		String groupID = rd.getStringByDI("TA_ZT_JHPW", "tid", 0);
		rd.add("groupId", groupID);
		try{
			coreDAO.beginTrasct(conn);
			//根据团ID查询对应的票务实施数据
			data.add("TA_ZT_JHPW", "tid", groupID);
			coreDAO.select(data);
			//根据团ID删除票务计划
			coreDAO.deleteEntity("TA_ZT_JHPW", data, conn);
			data.remove("TA_ZT_JHPW");
			//根据票务计划ID删除票务实施明细表
			for(int i=0;i<data.getTableRowsCount("TA_ZT_JHPWs");i++) {
				String planID = data.getStringByDI("TA_ZT_JHPWs", "id", i);
				data.add("TA_ZT_JHPWMX", "JHID", i, planID);
			}
			data.remove("TA_ZT_JHPWs");
			coreDAO.delete(data, conn);
			data.remove("TA_ZT_JHPWMX");
			//票务计划表主键
			for(int i=0;i<ticketRows.length;i++) {
				int id = queryMaxIDByPara("TA_ZT_JHPW", "ID", null);
				rd.add("TA_ZT_JHPW", "id", ticketRows[i], id);
				//插入票务明细表
				String[] trItemRows = rd.getRowIDs("TA_ZT_JHPWMX"+i);
				for(int j=0;j<trItemRows.length;j++) {
					int mxID = queryMaxIDByPara("TA_ZT_JHPWMX", "ID", null);
					rd.add("TA_ZT_JHPWMX"+i, "id", trItemRows[j], mxID);
					rd.add("TA_ZT_JHPWMX"+i, "JHID", trItemRows[j], id);
					rd.add("TA_ZT_JHPWMX"+i, "TID", trItemRows[j], groupID);
				}
				rd.renameEntity("TA_ZT_JHPWMX"+i, "TA_ZT_JHPWMX");
				coreDAO.insert("TA_ZT_JHPWMX", rd, conn);
				rd.remove("TA_ZT_JHPWMX");
			}
			coreDAO.insert("TA_ZT_JHPW", rd, conn);
			rd.remove("TA_ZT_JHPW");
			//更新团表中的费用总计
			updateTDJDXXZJB(rd, data, conn, groupID);
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
		return 38;
	}

	/**更新团表中的费用总计
	 * @param rd
	 * @param data
	 * @param conn
	 * @param groupID
	 * @throws SQLException
	 */
	public void updateTDJDXXZJB(BizData rd, BizData data, Connection conn,
			String groupID) throws SQLException {
		data.add("TA_TDJDXXZJB_ZT", "TID", groupID);
		coreDAO.select(data);
		int rows = data.getTableRowsCount("TA_TDJDXXZJB_ZTs");
		data.remove("TA_TDJDXXZJB_ZTs");
		if(rows > 0){
			rd.addAttr("TA_TDJDXXZJB_ZT", "TID", 0, "oldValue", groupID);
			coreDAO.update("TA_TDJDXXZJB_ZT", rd, conn);
		}else{
			rd.add("TA_TDJDXXZJB_ZT", "TID", groupID);
			coreDAO.insert("TA_TDJDXXZJB_ZT", rd, conn);
		}
	}
}
