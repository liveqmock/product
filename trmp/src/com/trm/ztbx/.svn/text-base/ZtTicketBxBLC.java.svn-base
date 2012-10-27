package com.trm.ztbx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * @Title: DjTicketBxBLC.java
 * @Package com.trmdj.bx
 * @Description: (票务报销)
 * @author Kale ym_x@qq.com
 * @date 2011-8-10 上午09:58:04
 * @version V1.0
 */
public class ZtTicketBxBLC extends DBBLC {
	public ZtTicketBxBLC(){
		this.entityName="TA_ZT_BXPW";
	}
	
	/**
	 * 
	 * @Title: initTicketBxList
	 * @Description: (初始化票务信息)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int initTicketBxList(BizData rd,BizData sd){
		String groupId=rd.getStringByDI("TA_ZT_BXPW", "TID", 0);
		String flag=rd.getString("flag");
		try {
			if("init".equals(flag)){
				//查询报销信息
				String bxPwInit="select a.id,a.tid,a.sf,a.city,a.dgd,a.lxr,a.lxrdh,a.qdxj,a.xfxj,a.sxfhj,a.hj, \n" +
						"a.jhzt,a.zdr from ta_zt_jhpw a where a.tid='"+groupId+"'";
				//查询报销明细
				String bxPwmxInit="select a.id,b.jhid,b.cplx,b.cfz,b.mdz,b.begintime,b.zs,b.dj,b.sxf,b.cc, \n" +
						"b.qdje,b.xfje,b.tid from ta_zt_jhpw a,ta_zt_jhpwmx b where b.jhid=a.id and b.tid='"+groupId+"'";
				
				String bxzjxxbinit="select a.qdpwzj qdzj,a.xfpwzj xfzj,a.pwzj zj,a.sxfzj sxfzj from ta_tdjdxxzjb_zt a where a.tid='"+groupId+"'";
				
				coreDAO.executeQuery(bxPwInit, "bxPwList", rd);
				coreDAO.executeQuery(bxPwmxInit, "bxPwmxList", rd);
				coreDAO.executeQuery(bxzjxxbinit, "bxpwJDXXZJB", rd);
			}
			
			if("edit".equals(flag)||"view".equals(flag)){
				//查询报销信息
				String bxPwEdit="select a.id,a.tid,a.sf,a.city,a.dgd,a.lxr,a.lxrdh,a.qdxj,a.xfxj,a.sxfhj,a.hj, \n" +
						"a.bxzt,a.bxr,a.bz from ta_zt_bxpw a where a.tid='"+groupId+"'";
				//查询报销明细
				String bxPwmxEdit="select a.id,b.jhid,b.cplx,b.cfz,b.mdz,b.begintime,b.zs,b.dj,b.sxf,b.ct cc, \n" +
						"b.qdje,b.xfje,b.tid from ta_zt_bxpw a,ta_zt_bxpwmx b where b.jhid=a.id and a.tid='"+groupId+"'";
				
				String bxzjxxbEdit="select a.bxpwqd qdzj,a.bxpwxf xfzj,a.pwhj zj,a.sxfzj sxfzj from ta_tdbxzjxxb_zt a where a.tid='"+groupId+"'";
				
				coreDAO.executeQuery(bxPwEdit, "bxPwList", rd);
				coreDAO.executeQuery(bxPwmxEdit, "bxPwmxList", rd);
				coreDAO.executeQuery(bxzjxxbEdit, "bxpwJDXXZJB", rd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * 
	 * @Title: editTicketBx
	 * @Description: (编辑票务报销)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int editTicketBx(BizData rd, BizData sd) {

		BizData data = new BizData();
		Connection conn = coreDAO.getConnection();
		String[] ticketRows = rd.getRowIDs("TA_ZT_BXPW");
		String groupID = rd.getStringByDI("TA_ZT_BXPW", "tid", 0);
		try{
			coreDAO.beginTrasct(conn);
			//根据团ID查询对应的票务实施数据
			data.add("TA_ZT_BXPW", "tid", groupID);
			coreDAO.select(data);
			//根据团ID删除票务计划
			coreDAO.deleteEntity("TA_ZT_BXPW", data, conn);
			data.remove("TA_ZT_BXPW");
			//根据票务计划ID删除票务实施明细表
			for(int i=0;i<data.getTableRowsCount("TA_ZT_BXPWs");i++) {
				String planID = data.getStringByDI("TA_ZT_BXPWs", "id", i);
				data.add("TA_ZT_BXPWMX", "JHID", i, planID);
			}
			data.remove("TA_ZT_BXPWs");
			coreDAO.delete(data, conn);
			data.remove("TA_ZT_BXPWMX");
			//票务计划表主键
			for(int i=0;i<ticketRows.length;i++) {
				int id = queryMaxIDByPara("TA_ZT_BXPW", "ID", null);
				rd.add("TA_ZT_BXPW", "id", ticketRows[i], id);
				//插入票务明细表
				String[] trItemRows = rd.getRowIDs("TA_ZT_BXPWMX"+i);
				for(int j=0;j<trItemRows.length;j++) {
					int mxID = queryMaxIDByPara("TA_ZT_BXPWMX", "ID", null);
					rd.add("TA_ZT_BXPWMX"+i, "id", trItemRows[j], mxID);
					rd.add("TA_ZT_BXPWMX"+i, "JHID", trItemRows[j], id);
					rd.add("TA_ZT_BXPWMX"+i, "TID", trItemRows[j], groupID);
				}
				rd.renameEntity("TA_ZT_BXPWMX"+i, "TA_ZT_BXPWMX");
				coreDAO.insert("TA_ZT_BXPWMX", rd, conn);
				rd.remove("TA_ZT_BXPWMX");
			}
			coreDAO.insert("TA_ZT_BXPW", rd, conn);
			rd.remove("TA_ZT_BXPW");
			//更新团表中的费用总计
			updateTDBXZJXXB(rd, data, conn, groupID);
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
		return 38;
	}

	/**更新团表中的费用总计
	 * @param rd
	 * @param data
	 * @param conn
	 * @param groupID
	 * @throws SQLException
	 */
	public void updateTDBXZJXXB(BizData rd, BizData data, Connection conn,
			String groupID) throws SQLException {
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
