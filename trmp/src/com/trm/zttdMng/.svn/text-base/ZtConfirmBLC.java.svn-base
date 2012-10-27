package com.trm.zttdMng;

import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class ZtConfirmBLC extends DBBLC {

	public ZtConfirmBLC() {
		this.entityName = "TA_ZT_QRJ";
	}

	/**
	 * 查询组团社
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int queryZTS(BizData rd, BizData sd) {
		
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("select z.tid,z.id,z.ztsid,t.cmpny_name\n").
		append("from TA_ZT_TZTS z,ta_travelagency t\n").
		append("where z.ztsid=t.travel_agc_id\n");
		/*if(!groupID.equals(""))
			sqlBuff.append("and z.tid='").append(groupID).append("'");*/
		try {
			coreDAO.executeQuery(sqlBuff.toString(), "rsTZTS", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 920;
	}
	
	/**
	 * 初始化单个组团社的确认件管理页面
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int queryConfirm(BizData rd, BizData sd) {
		
		rd.add("tztsID",rd.getString("id"));
		return 328;
	}
	
	/**
	 * 上传确认件
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int addConfirm(BizData rd, BizData sd) {
		
		int qrjID = queryMaxIDByPara("TA_ZT_QRJ", "ID", null);
		String tztsID = rd.getString("tztsID");
		byte[] pic = (byte[])rd.get("FILEDATA");
		rd.add("TA_ZT_QRJ", "id", 0, qrjID);
		rd.add("TA_ZT_QRJ", "TZTSID", 0, tztsID);
		rd.add("TA_ZT_QRJ", "CONFIRM_PIC", 0, pic);
		try {
			coreDAO.insert("TA_ZT_QRJ", rd);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return 20;
	}
	
}
