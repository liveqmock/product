package com.trm.ztbx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * @Title: DjDinnerBxBLC.java
 * @Package com.trmdj.bx
 * @Description: TODO(描述)
 * @author Kale ym_x@qq.com
 * @date 2011-8-6 下午12:03:18
 * @version V1.0
 */
public class ZtDinnerBxBLC extends DBBLC {
	public ZtDinnerBxBLC(){
		this.entityName="TA_ZT_BXCT";
	}
	
	/**
	 * 
	 * @Title: queryDinnerBxList
	 * @Description: (查看餐厅是否已报销)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int queryDinnerBxList(BizData rd,BizData sd){
		String groupId=rd.getStringByDI("TA_ZT_BXCT","TID",0);
		String flag=rd.getString("flag");
		try {
			if("init".equals(flag)){
				String bxdinnerinit="select d.* from ta_zt_group g,ta_zt_jhct d \n" +
				" where d.tid=g.id and g.id='"+groupId+"' order by d.ycrq asc";
				String hzsinit="select (nvl(sum(d.ts),0)) hzs from ta_zt_bxhotel d where d.sfhz>0 and d.tid='"+groupId+"'";
				String bxzjxxbinit="select a.qdctzj qdzj,a.xfctzj xfzj,a.ctzj zj from ta_tdjdxxzjb_zt a where a.tid='"+groupId+"'";
				coreDAO.executeQuery(bxdinnerinit, "dinnerBxList", rd);
				coreDAO.executeQuery(hzsinit, "bxHzs", rd);
				coreDAO.executeQuery(bxzjxxbinit, "bxctJDXXZJB", rd);
			}
			if("edit".equals(flag)||"view".equals(flag)){
				String bxdinneredit="select d.tid,d.sf,d.cityid,d.ct,d.zcjg,d.zcrs,d.zccs,d.zhcjg,d.zhcrs,d.zhccs,d.qdxj qdxjje,d.xfxj xfxjje,d.hj,d.bxr,d.bxzt,d.ycrq,d.bz \n" +
				" from ta_zt_group g,ta_zt_bxct d \n" +
				" where d.tid=g.id and g.id='"+groupId+"' order by d.ycrq asc";
				String hzsedit="select (nvl(sum(d.ts),0)) hzs from ta_zt_bxhotel d where d.sfhz>0 and d.tid='"+groupId+"'";
				String bxzjxxbEdit="select a.bxctqd qdzj,a.bxctxf xfzj,a.cthj zj from ta_tdbxzjxxb_zt a where a.tid='"+groupId+"'";
				coreDAO.executeQuery(bxdinneredit, "dinnerBxList", rd);
				coreDAO.executeQuery(hzsedit, "bxHzs", rd);
				coreDAO.executeQuery(bxzjxxbEdit, "bxctJDXXZJB", rd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	

	/**
	 * 
	 * @Title: editDinnerBx
	 * @Description: (编辑餐厅报销信息)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int editDinnerBx(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		String[] dinnerRows=rd.getRowIDs("TA_ZT_BXCT");
		try {
			coreDAO.beginTrasct(conn);
			String groupId=rd.getStringByDI("TA_ZT_BXCT", "TID", 0);
			//删除原餐厅报销信息
			BizData ct=new BizData();
			ct.add("TA_ZT_BXCT", "TID", groupId);
			coreDAO.delete(ct,conn);
			ct.remove("TA_ZT_BXCT");
			//添加餐厅报销信息
			for(int i=0;i<dinnerRows.length;i++){
				int id=this.queryMaxIDByPara("TA_ZT_BXCT", "ID", null);
				rd.add("TA_ZT_BXCT", "ID", dinnerRows[i],id);
				rd.add("TA_ZT_BXCT", "TID", dinnerRows[i], groupId);
				rd.add("TA_ZT_BXCT", "BXZT", dinnerRows[i], "Y");
				rd.add("TA_ZT_BXCT", "BXR", dinnerRows[i], sd.getString("userno"));
			}
			coreDAO.insert("TA_ZT_BXCT", rd, conn);
			rd.remove("TA_ZT_BXCT");
			//更新团表中的费用总计
			updateTDBXZJXXB(rd, conn, groupId);
			BizData dtt = new BizData();
			dtt.addAttr("TA_ZT_GROUP", "ID", 0, "oldValue", groupId);
			dtt.add("TA_ZT_GROUP", "STATE", "5");//修改团状态为 5  实施中
			coreDAO.update("TA_ZT_GROUP", dtt, conn);
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
		return 1;
	}

	/**更新团表中的费用总计
	 * @param rd
	 * @param conn
	 * @param groupId
	 * @throws SQLException
	 */
	public void updateTDBXZJXXB(BizData rd, Connection conn, String groupId)
			throws SQLException {
		BizData data=new BizData();
		data.add("TA_TDBXZJXXB_ZT", "TID", groupId);
		coreDAO.select(data);
		int rows = data.getTableRowsCount("TA_TDBXZJXXB_ZTs");
		data.remove("TA_TDBXZJXXB_ZTs");
		if(rows > 0){
			rd.addAttr("TA_TDBXZJXXB_ZT", "TID", 0, "oldValue", groupId);
			coreDAO.update("TA_TDBXZJXXB_ZT", rd, conn);
		}else{
			rd.add("TA_TDBXZJXXB_ZT", "TID", groupId);
			coreDAO.insert("TA_TDBXZJXXB_ZT", rd, conn);
		}
	}
}
