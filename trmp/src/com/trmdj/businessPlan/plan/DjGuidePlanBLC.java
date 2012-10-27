package com.trmdj.businessPlan.plan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * @Title: DjPlanBLC.java
 * @Package com.trmdj.businessPlan.plan
 * @Description: TODO(描述)
 * @author Kale ym_x@qq.com
 * @date 2011-7-13 下午02:37:32
 * @version V1.0
 */
public class DjGuidePlanBLC extends DBBLC {
	
	public DjGuidePlanBLC(){
		this.entityName="TA_DJ_JHDY";
	}
	
	/**
	 * 
	 * @Title: searchGuide
	 * @Description: TODO(查询所有导游姓名)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int selectAllGuide(BizData rd,BizData sd){
		
		String role = rd.getString("role");
		String sql="select *\n"+
		"from drmuser u,drmuserrole o\n" +
		"where u.userid=o.userid\n" +
		"and o.roleid='"+role+"'";
		
		String groupId=rd.getString("TID");
		String sql2="select h.* from ta_dj_jhdy h where h.tid='"+groupId+"' order by h.id asc";
		try {
			coreDAO.executeQuery(sql, "SelectAllGuide", rd);
			coreDAO.executeQuery(sql2, "selectGuideInfo", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		rd.add("action", rd.getString("action"));
		return 1;
	}
	
	/**
	 * 
	 * @Title: updateGuidePlan
	 * @Description: TODO(修改导游计划)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int updateGuidePlan(BizData rd, BizData sd) {
		Connection conn = coreDAO.getConnection();
		String rowIds[] = rd.getRowIDs("TA_DJ_JHDY");
		String ZDR = sd.getString("userno");
		String JHZT = "Y";
		try {
			coreDAO.beginTrasct(conn);
			
			//先根据团号删除对应导游计划
			String groupID = rd.getStringByDI("TA_DJ_JHDY", "TID", 0);
			BizData data = new BizData();
			data.add("TA_DJ_JHDY", "TID", groupID);
			coreDAO.delete(data,conn);
			data.remove("TA_DJ_JHDY");
			
			for(int i=0;i<rowIds.length;i++){
				//取主键
				int id=this.queryMaxIDByPara("TA_DJ_JHDY", "ID", null);
				rd.add("TA_DJ_JHDY", "ID",rowIds[i],id);
				rd.add("TA_DJ_JHDY", "TID",rowIds[i],groupID);
				rd.add("TA_DJ_JHDY", "JHZT",rowIds[i],JHZT);
				rd.add("TA_DJ_JHDY", "ZDR",rowIds[i],ZDR);
				String bxr = rd.getString("TA_DJ_JHDY", "bxr", rowIds[i]);
				if(bxr.equals(""))
					rd.add("TA_DJ_JHDY", "bxr",rowIds[i],"N");
			}
			coreDAO.insert("TA_DJ_JHDY", rd,conn);
			rd.remove("TA_DJ_JHDY");
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
		rd.remove("TA_DJ_JHDY");
		return 1;
	}
	public int queryBxFyList(BizData rd, BizData sd){
		String groupID= rd.getStringByDI("TA_DJ_JHDYs", "TID", 0);
		String sql="";
		sql=" select d.tid,d.dyid,d.dyxm,d.dylk,d.dff \n" +  
		"  from ta_dj_group t,ta_dj_jhdy d \n"+ 
		"  where d.tid=t.id \n" +
		" and t.id='"+groupID+"'";
		try{
			coreDAO.executeQuery(sql, "GuideFyInfo", rd);
		}catch(SQLException e){
			e.fillInStackTrace();
		}
		return 1;
	}
	
	/**
	 * 导游计划保存数据
	 */
	public int insert(BizData rd, BizData sd) {

		String[] otherRow = rd.getRowIDs("TA_DJ_JHDY");//获取记录数
		String groupId = rd.getString("groupId");//获取团号
		String temp = rd.getString("temp");//获取计划状态
		String zDr = sd.getString("userno");//获取指定人
		
		//根据团号删除导游计划
		BizData data = new BizData();
		data.add("TA_DJ_JHDY", "TID", groupId);
		data.add("TA_DJ_JHDY", "orgid", sd.getString("orgid"));
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			coreDAO.delete(data, conn);
			data.remove("TA_DJ_JHDY");

			for(int i = 0; i < otherRow.length; i++){
				int plusId = queryMaxIDByPara("TA_DJ_JHDY", "ID", null);
				int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_JHDY");
				rd.add("TA_DJ_JHDY","ID",fieldIndex[i],plusId);
				rd.add("TA_DJ_JHDY","TID",fieldIndex[i],groupId);
				rd.add("TA_DJ_JHDY","JHZT",fieldIndex[i],temp);
				rd.add("TA_DJ_JHDY","ZDR",fieldIndex[i],zDr);
				rd.add("TA_DJ_JHDY","orgid",fieldIndex[i],sd.getString("orgid"));
				if(i==0)
					rd.add("TA_DJ_JHDY","bxr",fieldIndex[i],"Y");
			}
			coreDAO.insert("TA_DJ_JHDY", rd, conn);//数据入库
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
	
	public int djAjaxGuideInfo(BizData rd, BizData sd) throws SQLException{
		String groupId = rd.getString("groupId");
		String sql = "select * from TA_DJ_JHDY where tid='"+groupId+"' and orgid="+sd.getString("orgid");
		coreDAO.executeQuery(sql, "TA_DJ_JHDY", rd);
		
		int tRow = rd.getTableRowsCount("TA_DJ_JHDY");//获取查询记录行数
		
		int random = 0;//装随机数
		for(int i = 0; i < tRow; i++){
			random = (int) (Math.floor(Math.random() * 123450));
			rd.add("TA_DJ_JHDY","Random", i, random);//装入随即数
		}
		
		//团队天数及开始时间
		rd.add("TA_DJ_GROUP", "id", groupId);
		rd.add("TA_DJ_GROUP","orgid",sd.getString("orgid"));
		coreDAO.select("TA_DJ_GROUP", rd);
		return 999;
	}
	
	public int djAjaxGuideWin (BizData rd, BizData sd) throws SQLException{
		return 999;
	}
}
