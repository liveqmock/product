package com.trmdj.businessPlan.groupMng;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;



public class DJConfirmBLC extends DBBLC {
	private static Logger log = Logger.getLogger(DJConfirmBLC.class);

	public DJConfirmBLC() {
		this.entityName = "TA_DJ_QRJ";
	}
	
	/**
	 * 初始化单个组团社的确认件管理页面
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int queryConfirm(BizData rd, BizData sd) {
		
		String ztsid = rd.getString("TA_TRAVELAGENCY", "TRAVEL_AGC_ID", 0);
		String id = rd.getString("id");
		StringBuffer sql = new StringBuffer();
		sql.append("select t.id tztsKey,t.ztsmc,t.ztsid\n");
		sql.append("from ta_dj_group g,ta_dj_tzts t\n");
		sql.append("where g.id=t.tid(+) and g.orgid=t.orgid(+)\n");
		sql.append("and g.orgid=").append(sd.getString("orgid")).append("\n");
		sql.append("and t.id=").append(id).append("\n");
		sql.append("and t.ztsid=").append(ztsid).append("\n");
		try {
			coreDAO.executeQuery(sql.toString(), "rsTravels", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 328;
	}
	
	/**
	 * 上传确认件
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int addConfirm(BizData rd, BizData sd) {
		
		int qrjID = queryMaxIDByPara("TA_DJ_QRJ", "ID", null);
		String tztsID = rd.getString("tztsID");
		byte[] pic = (byte[])rd.get("FILEDATA");
		rd.add("TA_DJ_QRJ", "id", 0, qrjID);
		rd.add("TA_DJ_QRJ", "TZTSID", 0, tztsID);
		rd.add("TA_DJ_QRJ", "CONFIRM_PIC", 0, pic);
		rd.add("TA_DJ_QRJ", "orgid", 0, sd.getString("orgid"));
		try {
			coreDAO.insert("TA_DJ_QRJ", rd);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return 20;
	}
	
}
