package com.trmdj.businessPlan.plan;

import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import java.sql.Connection;

public class DjTravePlanBLC extends DBBLC {
	public DjTravePlanBLC() {
		this.entityName = "TA_DJ_JHDJ";
	}

	
	public int generalGroundTrave(BizData rd, BizData sd) {
		Connection conn = coreDAO.getConnection();
		String groupID = rd.getStringByDI("TA_DJ_JHDJ", "TID", 0);
		String[] traveRows = rd.getRowIDs("TA_DJ_JHDJ");
		try {
			coreDAO.beginTrasct(conn);
			if ("Edit".equals(rd.getString("state"))) {
				BizData del1 = new BizData();
				del1.add("TA_DJ_JHDJ", "TID", groupID);
				coreDAO.delete(del1, conn);
				del1.remove("TA_DJ_JHDJ");
			}
			for (int i = 0; i < traveRows.length; i++) {
				int traveID = this.queryMaxIDByPara("TA_DJ_JHDJ", "ID",
						null);
				rd.add("TA_DJ_JHDJ", "TID", i, groupID);
				rd.add("TA_DJ_JHDJ", "ID", i, traveID);
			}
			coreDAO.insert("TA_DJ_JHDJ", rd, conn);
			rd.remove("TA_DJ_JHDJ");
			updateTDJDXXZJB(rd, conn, groupID);
			BizData dtt = new BizData();
			dtt.addAttr("TA_DJ_GROUP", "ID", 0, "oldValue", groupID);
			dtt.add("TA_DJ_GROUP", "STATE", "2");//修改团状态为 2  实施中
			coreDAO.update("TA_DJ_GROUP", dtt, conn);
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
		data.add("TA_TDJDXXZJB", "TID", groupID);
		coreDAO.select(data);
		int TDJDrows = data.getTableRowsCount("TA_TDJDXXZJBs");
		data.remove("TA_TDJDXXZJB");
		data.remove("TA_TDJDXXZJBs");
		if (TDJDrows > 0) {
			rd.addAttr("TA_TDJDXXZJB", "TID", 0, "oldValue", groupID);
			coreDAO.update("TA_TDJDXXZJB", rd, conn);
		} else {
			rd.add("TA_TDJDXXZJB", "TID", groupID);
			coreDAO.insert("TA_TDJDXXZJB", rd, conn);
		}
	}
	
	
	/**
	 * 地接计划保存数据
	 */
	public int insert(BizData rd, BizData sd) {

		String[] otherRow = rd.getRowIDs("TA_DJ_JHDJ");//获取记录数
		String groupId = rd.getString("groupId");//获取团号
		String temp = rd.getString("temp");//获取计划状态
		String zDr = sd.getString("userno");//获取指定人
		
		//根据团号删除导游计划
		BizData data = new BizData();
		data.add("TA_DJ_JHDJ", "TID", groupId);
		data.add("TA_DJ_JHDJ", "orgid", sd.getString("orgid"));
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			coreDAO.delete(data, conn);
			data.remove("TA_DJ_JHDJ");
			StringBuffer theNewTravel = new StringBuffer().append("[");
			
			int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_JHDJ");
			for(int i = 0; i < otherRow.length; i++){
				
				int traPlanId = queryMaxIDByPara("TA_DJ_JHDJ", "ID", null);
				rd.add("TA_DJ_JHDJ","ID",String.valueOf(fieldIndex[i]),traPlanId);
				rd.add("TA_DJ_JHDJ","TID",String.valueOf(fieldIndex[i]),groupId);
				rd.add("TA_DJ_JHDJ","JHZT",String.valueOf(fieldIndex[i]),temp);
				rd.add("TA_DJ_JHDJ","ZDR",String.valueOf(fieldIndex[i]),zDr);
				rd.add("TA_DJ_JHDJ", "orgid", String.valueOf(fieldIndex[i]), sd.getString("orgid"));
				//地接社ID（页面上选择的）
				String travelId = rd.getString("TA_DJ_JHDJ", "DJSID", String.valueOf(fieldIndex[i]));
				//地接社名称
				String travelName = rd.getString("TA_DJ_JHDJ", "DJSMC", String.valueOf(fieldIndex[i]));
				//基础表中（机构表）没有该地接社，
				if(travelId.equals("")){
					int baseTravelId = queryMaxIDByPara("TA_TRAVELAGENCY", "TRAVEL_AGC_ID", null);
					data.add("TA_TRAVELAGENCY", "TRAVEL_AGC_ID", baseTravelId);
					data.add("TA_TRAVELAGENCY", "CMPNY_NAME", travelName);
					data.add("TA_TRAVELAGENCY", "orgid", sd.getString("orgid"));
					data.add("TA_TRAVELAGENCY", "CHIEF_NAME", rd.getString("TA_DJ_JHDJ", "lxr", String.valueOf(fieldIndex[i])));
					data.add("TA_TRAVELAGENCY", "CHIEF_MOBILE", rd.getString("TA_DJ_JHDJ", "lxfs", String.valueOf(fieldIndex[i])));
					coreDAO.insert(data, conn);
					data.remove("TA_TRAVELAGENCY");
					rd.add("TA_DJ_JHDJ", "DJSID", String.valueOf(fieldIndex[i]), baseTravelId);
					//返回该新增的基础信息的数组索引值和当前的主键
					theNewTravel.append("{\"indexNm\":"+String.valueOf(fieldIndex[i])+",\"id\":"+baseTravelId+"},");
				}
			}
			
			//新增的酒店信息的ID
			if(theNewTravel.lastIndexOf(",") > 0){
				
				String newTravel = theNewTravel.substring(0, theNewTravel.length()-1);
				newTravel = newTravel+"]";
				rd.add("newBaseInfo", newTravel);
			}
			
			coreDAO.insert("TA_DJ_JHDJ", rd, conn);//数据入库
			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally{
			try{
				conn.close();
				conn = null;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return 999;
	}

	public int djAjaxTravelInfo(BizData rd, BizData sd) throws SQLException{
		String groupId = rd.getString("groupId");
		String sql = "select * from TA_DJ_JHDJ where tid='"+groupId+"' and orgid="+sd.getString("orgid");
		coreDAO.executeQuery(sql, "TA_DJ_JHDJ", rd);
		
		int tRow = rd.getTableRowsCount("TA_DJ_JHDJ");//获取查询记录行数
		
		int random = 0;//装随机数
		for(int i = 0; i < tRow; i++){
			random = (int) (Math.floor(Math.random() * 123450));
			rd.add("TA_DJ_JHDJ","Random", i, random);//装入随即数
		}
		
		//团队天数及开始时间
		rd.add("TA_DJ_GROUP", "id", groupId);
		rd.add("TA_DJ_GROUP", "orgid",sd.getString("orgid"));
		coreDAO.select("TA_DJ_GROUP", rd);
		return 999;
	}
}
