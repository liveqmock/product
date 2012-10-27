package com.trmdj.bx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
  * @ClassName: DjDjsbxBLC
  * @Description: TODO 地接 - 地接社报账信息处理类
  * @author KingStong - likai
  * @date 2012-4-13 上午3:24:17
  *
  */
public class DjDjsbxBLC extends DBBLC{
	public DjDjsbxBLC(){
		this.entityName="TA_DJ_BXDJ";
	}
	/** 新增地接社报账信息
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int insertTravel(BizData rd,BizData sd)throws SQLException{
		String[] hotelRow = rd.getRowIDs("TA_DJ_BXDJ");//获取记录数
		String groupId = rd.getString("groupId");//获取团号
		String temp = rd.getString("temp");//获取报账状态
		String zDr = sd.getString("userno");//获取指定人
		
		BizData data = new BizData();
		data.add("TA_DJ_BXDJ", "TID", groupId);
		data.add("TA_DJ_BXDJ", "orgid", sd.getString("orgid"));
		
		StringBuffer theNewTravel = new StringBuffer().append("[");
		
		// 旅行社基础数据
		BizData bsData = new BizData();

		int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_BXDJ");
		for(int i = 0; i < hotelRow.length; i++){
			int hotelId = queryMaxIDByPara("TA_DJ_BXDJ", "ID", null);
			rd.add("TA_DJ_BXDJ","ID",String.valueOf(fieldIndex[i]),hotelId);
			rd.add("TA_DJ_BXDJ","TID",String.valueOf(fieldIndex[i]),groupId);
			rd.add("TA_DJ_BXDJ","JHZT",String.valueOf(fieldIndex[i]),temp);
			rd.add("TA_DJ_BXDJ","ZDR",String.valueOf(fieldIndex[i]),zDr);
			rd.add("TA_DJ_BXDJ","orgid",String.valueOf(fieldIndex[i]),sd.getString("orgid"));
			
			//地接社ID（页面上选择的）
			String travelId = rd.getString("TA_DJ_BXDJ", "DJSID", String.valueOf(fieldIndex[i]));
			//地接社名称
			String travelName = rd.getString("TA_DJ_BXDJ", "DJSMC", String.valueOf(fieldIndex[i]));
			//基础表中（机构表）没有该地接社，
			if(travelId.equals("")){
				int baseTravelId = queryMaxIDByPara("TA_TRAVELAGENCY", "TRAVEL_AGC_ID", null);
				bsData.add("TA_TRAVELAGENCY", "TRAVEL_AGC_ID", baseTravelId);
				bsData.add("TA_TRAVELAGENCY", "CMPNY_NAME", travelName);
				bsData.add("TA_TRAVELAGENCY", "orgid", sd.getString("orgid"));
				bsData.add("TA_TRAVELAGENCY", "CHIEF_NAME", rd.getString("TA_DJ_BXDJ", "lxr", String.valueOf(fieldIndex[i])));
				bsData.add("TA_TRAVELAGENCY", "CHIEF_MOBILE", rd.getString("TA_DJ_BXDJ", "lxfs", String.valueOf(fieldIndex[i])));

				rd.add("TA_DJ_BXDJ", "DJSID", String.valueOf(fieldIndex[i]), baseTravelId);
				//返回该新增的基础信息的数组索引值和当前的主键
				theNewTravel.append("{\"indexNm\":"+String.valueOf(fieldIndex[i])+",\"id\":"+baseTravelId+"},");
			}
		}
		
		//新增的旅行社信息的ID
		if(theNewTravel.lastIndexOf(",") > 0){
			String newTravel = theNewTravel.substring(0, theNewTravel.length()-1);
			newTravel = newTravel+"]";
			rd.add("newBaseInfo", newTravel);
		}
		
		Connection conn=coreDAO.getConnection();
		try {
			//删除旅行社计划信息
			coreDAO.delete(data, conn);
			data.remove("TA_DJ_BXDJ");
			
			//插入旅行社计划信息
			coreDAO.insert("TA_DJ_BXDJ", rd, conn);
			rd.remove(entityName);
			
			//插入旅行社基础信息
			coreDAO.insert("TA_TRAVELAGENCY", bsData, conn);
			bsData.remove("TA_TRAVELAGENCY");
			
			//提交事务处理
			coreDAO.commitTrasct(conn);
		} catch (Exception e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 999;
	}
	
	/**Ajax初始化地接社报账页面
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int djAjaxTravelInfo(BizData rd, BizData sd) {
		int tRow = 0; //记录行数
		int random = 0;//随机数
		String groupId = rd.getString("groupId");//团号
		String sql = "";//SQL
		
		try {
			sql = "select * from TA_DJ_BXDJ where tid='"+groupId+"' and orgid="+sd.getString("orgid");
			coreDAO.executeQuery(sql, "TA_DJ_BXDJ", rd);
			tRow = rd.getTableRowsCount("TA_DJ_BXDJ");
			if(tRow < 1){//判断是否新增
				sql = "select * from TA_DJ_JHDJ  where tid='"+groupId+"' and orgid="+sd.getString("orgid");
				coreDAO.executeQuery(sql, "TA_DJ_BXDJ", rd);
			}
			sql="select begin_date,ts from ta_dj_group where id ='"+groupId+"' and orgid="+sd.getString("orgid");
			coreDAO.executeQuery(sql, "TA_DJ_GROUPs", rd);//团信息
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		tRow = rd.getTableRowsCount("TA_DJ_BXDJ");//获取行数
		for(int i = 0; i < tRow; i++){
			random = (int) (Math.floor(Math.random() * 123450));
			rd.add("TA_DJ_BXDJ","Random", i, random);//装入随即数
		}
		return 999;
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
		String groupID = rd.getStringByDI("TA_DJ_BXDJ", "TID", 0);
		String DjsNameInfo = "select t.cmpny_name from ta_dj_bxdj d,ta_travelagency t where d.djsid=t.travel_agc_id and d.tid='"+groupID+"'";
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
	String groupID= rd.getStringByDI("TA_DJ_BXDJ", "TID", 0);
	if("init".equals(FLAG)){
		String GuideInit = "select d.id,d.tid,d.sfid,d.csid,d.DJSID,d.YFZK,d.xfje,d.qdje,d.bz,b.cmpny_name,d.crrs,d.etrs,b.TRAVEL_AGC_ID \n" +
		" from TA_DJ_JHDJ d ,ta_travelagency b \n "+
		"where d.djsid=b.travel_agc_id "+
		"and d.tid='"+groupID+"'";
		String TravePriceInit = "select t.DJXFZJ XFZJ,t.DJQDZJ QDZJ,t.DJZJ DJZJ from TA_TDJDXXZJB t where t.tid='"+groupID+"'";
		coreDAO.executeQuery(GuideInit,"TraveInfo",rd);
		coreDAO.executeQuery(TravePriceInit,"TravePrice",rd);
	}
	if("edit".equals(FLAG)||"view".equals(FLAG)){
		String GuideEdit = "select d.id,d.tid,d.sfid,d.csid,d.DJSID,d.YFZK,d.QDJE,d.XFJE,d.bz,b.cmpny_name,d.crrs,d.etrs,b.TRAVEL_AGC_ID \n" +
		" from TA_DJ_BXDJ d,ta_travelagency b \n "+
		"where d.djsid=b.travel_agc_id "+
		"and d.tid='"+groupID+"'";
		String TravePriceEdit = "select t.DJXFZJ XFZJ,t.DJQDZJ QDZJ,t.DJHJ DJZJ from TA_TDBXZJXXB t where t.tid='"+groupID+"'";
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
		String groupID=rd.getStringByDI("TA_DJ_BXDJ","TID",0);
		String[] traveRows=rd.getRowIDs("TA_DJ_BXDJ");
		try{
		coreDAO.beginTrasct(conn);
		data.add("TA_DJ_BXDJ", "tid", groupID);
		coreDAO.delete(data, conn);
		data.remove("TA_DJ_BXDJ");
		for(int i = 0; i < traveRows.length; i++){
					int traveID=this.queryMaxIDByPara("TA_DJ_BXDJ","ID",null);
					rd.add("TA_DJ_BXDJ","TID",i,groupID);
					rd.add("TA_DJ_BXDJ","ID",i,traveID);
		}
		coreDAO.insert("TA_DJ_BXDJ",rd,conn);
		rd.remove("TA_DJ_BXDJ");
		updateTDBXZJXXB(groupID, rd, conn);//插入 团队报销 总计信息表
		BizData dtt = new BizData();
		dtt.addAttr("TA_DJ_GROUP", "ID", 0, "oldValue", groupID);
		dtt.add("TA_DJ_GROUP", "STATE", "5");//修改团状态为 5  实施中
		coreDAO.update("TA_DJ_GROUP", dtt, conn);
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
		data.add("TA_TDBXZJXXB", "TID", groupID);
		coreDAO.select(data);
		int TDBXrows = data.getTableRowsCount("TA_TDBXZJXXBs");
		data.remove("TA_TDBXZJXXB");
		data.remove("TA_TDBXZJXXBs");
		if(TDBXrows > 0){
			rd.addAttr("TA_TDBXZJXXB", "TID", 0, "oldValue", groupID);
			coreDAO.update("TA_TDBXZJXXB", rd, conn);
			rd.remove("TA_TDBXZJXXB");
		}else{
			rd.add("TA_TDBXZJXXB", "TID", groupID);
			coreDAO.insert("TA_TDBXZJXXB",rd, conn);
			rd.remove("TA_TDBXZJXXB");
		}
		return 999;
		
	}
}
