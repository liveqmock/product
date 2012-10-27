package com.trm.ztbx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class ZtDjsbxBLC extends DBBLC{
	public ZtDjsbxBLC(){
		this.entityName="TA_ZT_BXDJ";
	}
	
	/**
	 * queryDjsInfo:(初始化地接导游报销查询地接社名称)
	 *
	 * @param  @param rd
	 * @param  @param sd
	 * @param  @return    设定文件
	 * @return int    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	public int queryDjsInfo(BizData rd, BizData sd){
		String groupID = rd.getStringByDI("TA_ZT_BXDJ", "TID", 0);
		String DjsNameInfo = "select t.cmpny_name from ta_zt_bxdj d,ta_travelagency t where d.djsid=t.travel_agc_id and d.tid='"+groupID+"'";
		try {
			coreDAO.executeQuery(DjsNameInfo,"DjsNameInfo",rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
	}

	/**
	 * 
	 * @Title: queryDjsMng
	 * @Description: 查询地接社报销信息
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int queryDjsMng(BizData rd, BizData sd) throws SQLException{
	String FLAG = rd.getString("flag");
	String groupID= rd.getStringByDI("TA_ZT_BXDJ", "TID", 0);
	if("init".equals(FLAG)){
		String GuideInit = "select d.id,d.tid,d.sfid,d.csid,d.DJSID,d.YFZK,d.xfje,d.qdje,d.bz,b.cmpny_name,d.crrs,d.etrs,b.TRAVEL_AGC_ID \n" +
		" from TA_ZT_JHDJ d ,ta_travelagency b \n "+
		"where d.djsid=b.travel_agc_id "+
		"and d.tid='"+groupID+"'";
		String TravePriceInit = "select t.DJXFZJ XFZJ,t.DJQDZJ QDZJ,t.DJZJ DJZJ from TA_TDJDXXZJB_ZT t where t.tid='"+groupID+"'";
		coreDAO.executeQuery(GuideInit,"TraveInfo",rd);
		coreDAO.executeQuery(TravePriceInit,"TravePrice",rd);
	}
	if("edit".equals(FLAG)||"view".equals(FLAG)){
		String GuideEdit = "select d.id,d.tid,d.sfid,d.csid,d.DJSID,d.YFZK,d.QDJE,d.XFJE,d.bz,b.cmpny_name,d.crrs,d.etrs,b.TRAVEL_AGC_ID \n" +
		" from TA_ZT_BXDJ d,ta_travelagency b \n "+
		"where d.djsid=b.travel_agc_id "+
		"and d.tid='"+groupID+"'";
		String TravePriceEdit = "select t.DJXFZJ XFZJ,t.DJQDZJ QDZJ,t.DJHJ DJZJ from TA_TDBXZJXXB_ZT t where t.tid='"+groupID+"'";
		coreDAO.executeQuery(GuideEdit,"TraveInfo",rd);
		coreDAO.executeQuery(TravePriceEdit,"TravePrice",rd);
	}

	return 1;
}
	
	
	/**
	 * @throws SQLException 
	 * 
	 * @Title: updatDjsInfo
	 * @Description: 修改地接社报销信息
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int updatDjsInfo(BizData rd, BizData sd) throws SQLException{
		BizData data = new BizData();
		Connection conn = coreDAO.getConnection();
		String groupID=rd.getStringByDI("TA_ZT_BXDJ","TID",0);
		String[] traveRows=rd.getRowIDs("TA_ZT_BXDJ");
		try{
		coreDAO.beginTrasct(conn);
		data.add("TA_ZT_BXDJ", "tid", groupID);
		coreDAO.delete(data, conn);
		data.remove("TA_ZT_BXDJ");
		for(int i = 0; i < traveRows.length; i++){
					int traveID=this.queryMaxIDByPara("TA_ZT_BXDJ","ID",null);
					rd.add("TA_ZT_BXDJ","TID",i,groupID);
					rd.add("TA_ZT_BXDJ","ID",i,traveID);
		}
		coreDAO.insert("TA_ZT_BXDJ",rd,conn);
		rd.remove("TA_ZT_BXDJ");
		updateTDBXZJXXB(groupID, rd, conn);//插入 团队报销 总计信息表
		BizData dtt = new BizData();
		dtt.addAttr("TA_ZT_GROUP", "ID", 0, "oldValue", groupID);
		dtt.add("TA_ZT_GROUP", "STATE", "5");//修改团状态为 5  实施中
		coreDAO.update("TA_ZT_GROUP", dtt, conn);
		coreDAO.commitTrasct(conn);
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
	/**
	 * updateTDBXZJXXB:(//团队报销总计信息表)
	 *
	 * @param  @param groupID
	 * @param  @param rd
	 * @param  @param conn
	 * @param  @return
	 * @param  @throws SQLException    设定文件
	 * @return int    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	public int updateTDBXZJXXB(String groupID,BizData rd,Connection conn) throws SQLException{
		BizData data = new BizData();
		data.add("TA_TDBXZJXXB_ZT", "TID", groupID);
		coreDAO.select(data);
		int TDBXrows = data.getTableRowsCount("TA_TDBXZJXXB_ZTs");
		data.remove("TA_TDBXZJXXB_ZT");
		data.remove("TA_TDBXZJXXB_ZTs");
		if(TDBXrows > 0){
			rd.addAttr("TA_TDBXZJXXB_ZT", "TID", 0, "oldValue", groupID);
			coreDAO.update("TA_TDBXZJXXB_ZT", rd, conn);
			rd.remove("TA_TDBXZJXXB_ZT");
		}else{
			rd.add("TA_TDBXZJXXB_ZT", "TID", groupID);
			coreDAO.insert("TA_TDBXZJXXB_ZT",rd, conn);
			rd.remove("TA_TDBXZJXXB_ZT");
		}
		return 999;
		
	}
}
