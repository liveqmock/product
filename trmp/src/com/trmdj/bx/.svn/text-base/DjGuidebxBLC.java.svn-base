package com.trmdj.bx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class DjGuidebxBLC extends DBBLC{
	public DjGuidebxBLC(){
		this.entityName=("TA_DJ_BXDY");
	}

	/** 新增导游报账信息
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int insertGuide(BizData rd,BizData sd)throws SQLException{
		String[] hotelRow = rd.getRowIDs("TA_DJ_BXDY");//获取记录数
		String groupId = rd.getString("groupId");//获取团号
		String temp = rd.getString("temp");//获取报账状态
		String zDr = sd.getString("userno");//获取指定人
		
		BizData data = new BizData();
		data.add("TA_DJ_BXDY", "TID", groupId);
		data.add("TA_DJ_BXDY", "orgid", sd.getString("orgid"));

		int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_BXDY");
		for(int i = 0; i < hotelRow.length; i++){
			int hotelId = queryMaxIDByPara("TA_DJ_BXDY", "ID", null);
			rd.add("TA_DJ_BXDY","ID",String.valueOf(fieldIndex[i]),hotelId);
			rd.add("TA_DJ_BXDY","TID",String.valueOf(fieldIndex[i]),groupId);
			rd.add("TA_DJ_BXDY","JHZT",String.valueOf(fieldIndex[i]),temp);
			rd.add("TA_DJ_BXDY","ZDR",String.valueOf(fieldIndex[i]),zDr);
			rd.add("TA_DJ_BXDY","orgid",String.valueOf(fieldIndex[i]),sd.getString("orgid"));
		}
		
		Connection conn=coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			coreDAO.delete(data, conn);//删除历史数据
			data.remove(entityName);
			
			coreDAO.insert("TA_DJ_BXDY", rd, conn);//数据入库
			rd.remove(entityName);
			
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
	
	/**Ajax初始化导游报账页面
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int djAjaxGuideInfo(BizData rd, BizData sd) {
		int tRow = 0; //记录行数
		int random = 0;//随机数
		String groupId = rd.getString("groupId");//团号
		String sql = "";//SQL
		
		try {
			sql = "select * from TA_DJ_BXDY where tid='"+groupId+"' and orgid="+sd.getString("orgid");
			coreDAO.executeQuery(sql, "TA_DJ_BXDY", rd);
			tRow = rd.getTableRowsCount("TA_DJ_BXDY");
			if(tRow < 1){//判断是否新增
				sql = "select * from TA_DJ_JHDY  where tid='"+groupId+"' and orgid="+sd.getString("orgid");
				coreDAO.executeQuery(sql, "TA_DJ_BXDY", rd);
			}
			sql="select begin_date,ts from ta_dj_group where id ='"+groupId+"' and orgid="+sd.getString("orgid");
			coreDAO.executeQuery(sql, "TA_DJ_GROUPs", rd);//团信息
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		tRow = rd.getTableRowsCount("TA_DJ_BXDY");//获取行数
		for(int i = 0; i < tRow; i++){
			random = (int) (Math.floor(Math.random() * 123450));
			rd.add("TA_DJ_BXDY","Random", i, random);//装入随即数
		}
		return 999;
	}

	public int queryGuideMng(BizData rd, BizData sd) throws SQLException{
		String FLAG = rd.getString("flag");
		String groupID= rd.getStringByDI("TA_DJ_BXDY", "TID", 0);
		if("init".equals(FLAG)){
			String GuideInit = "select ID,TID,DYID,DYXM,DYLK,DFF from TA_DJ_JHDY where TID = '"+groupID+"'";
			coreDAO.executeQuery(GuideInit,"GuideInfo",rd);
		}
		if("edit".equals(FLAG)||"view".equals(FLAG)){
			String GuideEdit = "select ID,TID,DYID,DYXM,DYLK,DYJTF,DFF,QT from TA_DJ_BXDY where TID = '"+groupID+"'";
			coreDAO.executeQuery(GuideEdit,"GuideInfo",rd);	
		}
	
		return 1;
	}
	
	public int updateGuideBx(BizData rd, BizData sd) {
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			String groupID = rd.getStringByDI("TA_DJ_BXDY","TID",0);
			String BXR = rd.getString("TA_DJ_BXDY","BXR",0);
			String BXZT = "Y";
			String STATE = rd.getString("state");
			if("Edit".equals(STATE)){
				BizData data = new BizData();
				data.add("TA_DJ_BXDY", "TID", groupID);
				coreDAO.deleteEntity("TA_DJ_BXDY",data,conn);//先根据团号删除对应导游报销
				data.remove("TA_DJ_BXDY");
			}
			String[]rowIds = rd.getRowIDs("TA_DJ_BXDY");		
			for(int i = 0;i < rowIds.length; i++){
				int BXID = this.queryMaxIDByPara("TA_DJ_BXDY", "ID", null);
				String DYID = rd.getString("TA_DJ_BXDY","DYID",rowIds[i]);
				String DYXM = rd.getString("TA_DJ_BXDY","DYXM",rowIds[i]);
				String DYLK = rd.getString("TA_DJ_BXDY","DYLK",rowIds[i]);
				String DYJTF = rd.getString("TA_DJ_BXDY","DYJTF",rowIds[i]);
				String DFF = rd.getString("TA_DJ_BXDY","DFF",rowIds[i]);
				String QT = rd.getString("TA_DJ_BXDY","QT",rowIds[i]);
				String BZ = rd.getString("TA_DJ_BXDY","BZ",rowIds[i]);
				rd.add("TA_DJ_BXDY", "ID", rowIds[i], BXID);
				rd.add("TA_DJ_BXDY", "TID", rowIds[i], groupID);
				rd.add("TA_DJ_BXDY", "DYID", rowIds[i],DYID);
				rd.add("TA_DJ_BXDY", "DYXM", rowIds[i], DYXM);
				rd.add("TA_DJ_BXDY", "DYLK", rowIds[i], DYLK);
				rd.add("TA_DJ_BXDY", "DYJTF", rowIds[i], DYJTF);
				rd.add("TA_DJ_BXDY", "DFF", rowIds[i], DFF);
				rd.add("TA_DJ_BXDY", "BXR", rowIds[i], BXR);
				rd.add("TA_DJ_BXDY", "BXZT", rowIds[i], BXZT);
				rd.add("TA_DJ_BXDY", "QT", rowIds[i], QT);
				rd.add("TA_DJ_BXDY", "BZ", rowIds[i], BZ);
			}
			coreDAO.insert("TA_DJ_BXDY", rd, conn);
			rd.remove("TA_DJ_BXDY");
			updateTDBXZJXXB(groupID, rd, conn);
			BizData dtt = new BizData();
			dtt.addAttr("TA_DJ_GROUP", "ID", 0, "oldValue", groupID);
			dtt.add("TA_DJ_GROUP", "STATE", "5");//修改团状态为 5  实施中
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
		return 1;
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
