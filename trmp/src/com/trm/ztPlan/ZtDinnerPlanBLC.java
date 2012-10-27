package com.trm.ztPlan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class ZtDinnerPlanBLC extends DBBLC {
	public ZtDinnerPlanBLC(){
		this.entityName="TA_ZT_JHCT";
	}
	/**
	 * 
	 * @Title: queryDinnerPlanList
	 * @Description: (查看餐厅是否已业务计调)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int queryDinnerPlanList(BizData rd,BizData sd){
		String groupId=rd.getStringByDI("TA_ZT_GROUP","ID",0);
		String sql="select count(d.id) isNull from TA_ZT_jhct d,TA_ZT_group g \n" +
				" where d.tid=g.id and g.id='"+groupId+"'";
		
		try {
			coreDAO.executeQuery(sql, "dinnerPlanInfo", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * 
	 * @Title: listDinnerPlan
	 * @Description: (根据团号查询餐厅计划信息)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int listDinnerPlan(BizData rd,BizData sd){
		String groupId=rd.getString("TID");
		String sql="select d.*,z.xfctzj,z.qdctzj,z.ctzj from TA_ZT_group g,TA_ZT_jhct d,TA_TDJDXXZJB_ZT z \n" +
		" where d.tid=g.id and z.tid=g.id and g.id='"+groupId+"' order by d.ycrq asc";
		
		String hzsinit="select (nvl(sum(d.ts),0)) hzs from TA_ZT_jhhotel d where d.sfhz>0 and d.tid='"+groupId+"'";
		try {
			coreDAO.executeQuery(sql, "dinnerPlanList", rd);
			coreDAO.executeQuery(hzsinit, "jhHzs", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * 
	 * @Title: editDinnerPlan
	 * @Description: (编辑餐厅信息)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int editDinnerPlan(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		String[] dinnerRows=rd.getRowIDs("TA_ZT_JHCT");
		try {
			coreDAO.beginTrasct(conn);
			String ZDR = rd.getString("TA_ZT_JHCT","ZDR",0);
			String JHZT = rd.getString("TA_ZT_JHCT","JHZT",0);
			String groupId=rd.getStringByDI("TA_ZT_JHCT", "TID", 0);
			rd.add("groupId",groupId);
			//删除原餐厅计划信息
			BizData ct=new BizData();
			ct.add("TA_ZT_JHCT", "TID", groupId);
			coreDAO.delete(ct,conn);
			ct.remove("TA_ZT_JHCT");
			
			//添加餐厅计划信息
			for(int i=0;i<dinnerRows.length;i++){
				int id=this.queryMaxIDByPara("TA_ZT_JHCT", "ID", null);
				rd.add("TA_ZT_JHCT", "ID", dinnerRows[i],id);
				rd.add("TA_ZT_JHCT", "TID", dinnerRows[i], groupId);
				rd.add("TA_ZT_JHCT", "JHZT", dinnerRows[i], JHZT);
				rd.add("TA_ZT_JHCT", "ZDR", dinnerRows[i], ZDR);
			}
			coreDAO.insert("TA_ZT_JHCT", rd, conn);
			rd.remove("TA_ZT_JHCT");
			BizData dtt = new BizData();
			dtt.addAttr("TA_ZT_GROUP", "ID", 0, "oldValue", groupId);
			dtt.add("TA_ZT_GROUP", "STATE", "2");//修改团状态为 2  实施中
			coreDAO.update("TA_ZT_GROUP", dtt, conn);
			//更新团表中的费用总计
			updateTDJDXXZJB(rd, conn, groupId);			
			coreDAO.commitTrasct(conn);
		} catch (Exception e) {
			try {
				coreDAO.rollbackTrasct(conn);
				e.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally{
			if(null!=conn){
				try {
					conn.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return 999;
	}

	/**更新团表中的费用总计
	 * @param rd
	 * @param conn
	 * @param groupId
	 * @throws SQLException
	 */
	public void updateTDJDXXZJB(BizData rd, Connection conn, String groupId)
			throws SQLException {
		BizData data=new BizData();
		data.add("TA_TDJDXXZJB_ZT", "TID", groupId);
		coreDAO.select(data);
		int rows = data.getTableRowsCount("TA_TDJDXXZJB_ZTs");
		data.remove("TA_TDJDXXZJB_ZTs");
		if(rows > 0){
			rd.addAttr("TA_TDJDXXZJB_ZT", "TID", 0, "oldValue", groupId);
			coreDAO.update("TA_TDJDXXZJB_ZT", rd, conn);
		}else{
			rd.add("TA_TDJDXXZJB_ZT", "TID", groupId);
			coreDAO.insert("TA_TDJDXXZJB_ZT", rd, conn);
		}
	}
}
