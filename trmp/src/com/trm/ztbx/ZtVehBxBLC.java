package com.trm.ztbx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class ZtVehBxBLC extends DBBLC {
	public ZtVehBxBLC(){
		this.entityName="TA_ZT_BXCL";
	}
	
	/**
	 * 
	 * @Title: initBXPage
	 * @Description: (初始化车辆报销信息)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int initBXPage(BizData rd,BizData sd) {
		String groupId=rd.getStringByDI("TA_ZT_JHCL", "TID", 0);
		String flag=rd.getString("flag");
		try {
			if("init".equals(flag)){
				String bxVehInit="select j.tid,j.sf,j.city,j.cd,j.cx,j.cp,j.sjxm,j.sjdh,j.begintime,j.endtime, \n" +
						"j.jg,j.qdje,j.xfje,j.jhzt,j.zdr,j.jthbcc,j.sthbcc,j.jtdd,j.stdd,j.sjxm,j.sjdh from ta_zt_jhcl j where j.tid='"+groupId+"'";
				
				String bxzjxxbinit="select a.qdclzj qdzj,a.xfclzj xfzj,a.clzj zj from ta_tdjdxxzjb_zt a where a.tid='"+groupId+"'";
				
				coreDAO.executeQuery(bxVehInit, "bxVehList", rd);
				coreDAO.executeQuery(bxzjxxbinit, "bxclJDXXZJB", rd);
			}
			
			if("edit".equals(flag)||"view".equals(flag)){
				String bxVehEdit="select j.tid,j.sf,j.city,j.cd,j.cx,j.cp,j.ksrq begintime,j.jsrq endtime, \n" +
					"j.jg,j.qdje,j.xfje,j.bxzt,j.bxr,j.jthbcc,j.sthbcc,j.jtdd,j.stdd,j.sjxm,j.sjdh,j.bz from ta_zt_bxcl j where j.tid='"+groupId+"'";
				
				String bxzjxxbEdit="select a.bxclqd qdzj,a.bxclxf xfzj,a.clhj zj from ta_tdbxzjxxb_zt a where a.tid='"+groupId+"'";
				
				coreDAO.executeQuery(bxVehEdit, "bxVehList", rd);
				coreDAO.executeQuery(bxzjxxbEdit, "bxclJDXXZJB", rd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 92;
	}
	
	/**
	 * 
	 * @Title: djSaveVehPlan
	 * @Description: (编辑车辆报销)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int editVehBx(BizData rd, BizData sd) {
		
		Connection conn = coreDAO.getConnection();
		String[] rowIDs = rd.getRowIDs("TA_ZT_BXCL");
		try {
			
			coreDAO.beginTrasct(conn);
			//删除团队原来的车调计划
			String groupID = rd.getStringByDI("TA_ZT_BXCL", "TID", 0);
			BizData data = new BizData();
			data.add("TA_ZT_BXCL", "TID", groupID);
			coreDAO.delete(data, conn);
			data.remove("TA_ZT_BXCL");
			//添加新的车调计划
			for(int i=0;i<rowIDs.length;i++) {
				int id = queryMaxIDByPara("TA_ZT_BXCL", "ID", null);
				rd.add("TA_ZT_BXCL", "id", rowIDs[i], id);
			}
			coreDAO.insert("TA_ZT_BXCL", rd, conn);
			rd.remove("TA_ZT_BXCL");
			//更新团表中的费用总计
			updateTDBXZJXXB(rd, conn, groupID, data);
			BizData dtt = new BizData();
			dtt.addAttr("TA_ZT_GROUP", "ID", 0, "oldValue", groupID);
			dtt.add("TA_ZT_GROUP", "STATE", "5");//修改团状态为 5  实施中
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
		return 9;
	}

	/**更新团表中的费用总计
	 * @param rd
	 * @param conn
	 * @param groupID
	 * @param data
	 * @throws SQLException
	 */
	public void updateTDBXZJXXB(BizData rd, Connection conn, String groupID,
			BizData data) throws SQLException {
		data.add("TA_TDBXZJXXB_ZT", "TID", groupID);
		coreDAO.select(data);
		int rows = data.getTableRowsCount("TA_TDBXZJXXB_ZTs");
		data.remove("TA_TDBXZJXXB_ZTs");
		if(rows > 0){
			rd.addAttr("TA_TDBXZJXXB_ZT", "TID", 0, "oldValue", groupID);
			coreDAO.update("TA_TDBXZJXXB_ZT", rd, conn);
		}else{
			rd.add("TA_TDBXZJXXB_ZT", "TID", groupID);
			coreDAO.insert("TA_TDBXZJXXB_ZT", rd, conn);
		}
	}
}
