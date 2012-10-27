package com.baseMng;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.string.PyUtil;

public class TravelAgcBLC extends DBBLC {
	
	private Logger log = null;
	public TravelAgcBLC(){		
		this.log = Logger.getLogger(TravelAgcBLC.class);
		this.entityName = "TA_TRAVELAGENCY";		
	}
	
	/**
	 * 查询
	 * @param rd
	 * @param sd
	 * @return
	 * @throws SQLException 
	 */
	public int listTravelAgc(BizData rd,BizData sd) throws SQLException {
		queryPage(rd, sd);
		return 109;
	}
	
	public int queryPage(BizData rd,BizData sd){
		String orgid = sd.getString("orgid");
		rd.add("TA_TRAVELAGENCY", "orgid", 0, orgid);
		try {
			coreDAO.selectPage("TA_TRAVELAGENCY", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1001;
	}
	
	/**
	 * 添加组团社信息
	 * 
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int addTravelagencyInfo(BizData rd, BizData sd) 
	{
		// 添加当前操作员所在机构ID
		rd.add("TA_TRAVELAGENCY", "orgid", 0, sd.getString("orgid"));
		
		int traAgcID = queryMaxIDByPara("TA_TRAVELAGENCY", "TRAVEL_AGC_ID", null);
		
		//添加旅行社
		rd.add("TA_TRAVELAGENCY", "TRAVEL_AGC_ID", traAgcID);
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			//生成拼音码
			String cmpName = rd.getString("TA_TRAVELAGENCY", "CMPNY_NAME", 0);
			String nameCode = PyUtil.get1stLetterOf4Chars(cmpName);
			rd.add("TA_TRAVELAGENCY", "NAMECODE", 0, nameCode);
			
			coreDAO.insert("TA_TRAVELAGENCY", rd, conn);
			String[] rowIDs = rd.getRowIDs("TA_STORE");
			BizData rd2 = new BizData();
			for(int i=0;i<rowIDs.length;i++) {
				int storeID = queryMaxIDByPara("TA_STORE", "STORE_ID", null);
				rd2.add("TA_STORE", "STORE_ID", storeID);
				rd2.add("TA_STORE", "TRAVEL_AGC_ID", traAgcID);
				rd2.add("TA_STORE", "Store_name", rd.getString("TA_STORE", "Store_name", rowIDs[i]));
				rd2.add("TA_STORE", "store_address", rd.getString("TA_STORE", "store_address", rowIDs[i]));
				coreDAO.insert(rd2, conn);
			}
			
			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try{
				if(null != conn){
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 1003;
	}
	
	public int update(BizData rd, BizData sd){
		
		Connection conn = coreDAO.getConnection();
		try {
			String traAgcID = rd.getStringByDI("TA_TRAVELAGENCY", "TRAVEL_AGC_ID", 0);
			coreDAO.beginTrasct(conn);
			//生成拼音码
			String cmpName = rd.getString("TA_TRAVELAGENCY", "CMPNY_NAME", 0);
			String nameCode = PyUtil.get1stLetterOf4Chars(cmpName);
			rd.add("TA_TRAVELAGENCY", "NAMECODE", 0, nameCode);
			
			//删除旅行社表
			coreDAO.update("TA_TRAVELAGENCY", rd, conn);
			//删除跟旅行社表关联的门店表
			BizData rd2 = new BizData();
			rd2.add("ta_store", "TRAVEL_AGC_ID", traAgcID);
			coreDAO.delete(rd2, conn);
			rd2.remove("ta_store");
			//添加门店表
			String[] rowIDs = rd.getRowIDs("ta_store");
			for(int i=0;i<rowIDs.length;i++){
				
				int storeID = queryMaxIDByPara("TA_STORE", "STORE_ID", null);
				rd2.add("TA_STORE", "STORE_ID", storeID);
				rd2.add("TA_STORE", "TRAVEL_AGC_ID", traAgcID);
				rd2.add("TA_STORE", "Store_name", rd.getString("TA_STORE", "Store_name", rowIDs[i]));
				rd2.add("TA_STORE", "store_address", rd.getString("TA_STORE", "store_address", rowIDs[i]));
				coreDAO.insert(rd2, conn);
			}
			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try{
				if(null != conn){
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 93;
	}
	
	public int deleteAll(BizData rd, BizData sd) {
		
		rd.add("ywlb", rd.getString("ywlb"));
		String cityID = rd.getString("cityID");
		String priID = rd.getString("priID");
		rd.add("cityID", cityID);
		rd.add("priID", priID);
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			//删除门店表
			BizData rd2 = new BizData();
			String[] rowIDs = rd.getRowIDs("ta_travelagency");
			
			//直接根据ID删除
			if(rowIDs.length == 0){
				String traAgcID = rd.getString("ta_travelagency", "TRAVEL_AGC_ID", "0");
				rd2.add("ta_store", "TRAVEL_AGC_ID", traAgcID);
				coreDAO.deleteEntity("ta_store", rd2, conn);
			}else{
				//全选删除
				for(int i=0;i<rowIDs.length;i++){
					String traAgcID = rd.getString("ta_travelagency", "TRAVEL_AGC_ID", rowIDs[i]);
					rd2.add("ta_store", "TRAVEL_AGC_ID", traAgcID);
					coreDAO.deleteEntity("ta_store", rd2, conn);
				}
			}
			
			coreDAO.delete(rd, conn);
			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try{
				if(null != conn){
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 298;
	}
	
	public int query(BizData rd, BizData sd){
		
		String traId = rd.getStringByDI("ta_travelagency", "TRAVEL_AGC_ID", 0);
		StringBuffer sql = new StringBuffer();
		sql.append("select d.*,x.name\n");
		sql.append("from TA_TRAVELAGENCY d,xzqh x\n");
		sql.append("where d.city_id=x.id(+)\n");
		sql.append("and d.orgid=").append(sd.getString("orgid"));
		sql.append(" and d.travel_agc_id=").append(traId);
		try {
			coreDAO.executeQuery(sql.toString(), "TA_TRAVELAGENCYs", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 98;
	}
}
