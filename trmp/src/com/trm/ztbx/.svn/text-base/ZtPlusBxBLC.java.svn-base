/**
 * DjPlusBxBLC.java
 * com.trmdj.bx
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2011-8-12 		Administrator
 *
 * Copyright (c) 2011, TNT All Rights Reserved.
*/

package com.trm.ztbx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * ClassName:DjPlusBxBLC
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Administrator
 * @version  
 * @since    Ver 1.1
 * @Date	 2011-8-12		上午03:53:05
 *
 * @see 	 
 * @deprecated 
 */
public class ZtPlusBxBLC extends DBBLC {
	public ZtPlusBxBLC(){
		this.entityName = "TA_ZT_BXJIADIAN";
	}
	public int queryPlusInfo(BizData rd,BizData sd){
		String groupID = rd.getString("TA_ZT_BXJIADIAN","TID",0);
		String queryPlusInfo="select s.cmpny_name from ta_scenery_point s, ta_zt_bxjiadian t where t.jdid=s.scenery_id and t.tid='"+groupID+"'";
		String queryShopInfo="select s.cmpny_name from ta_zt_bxgw w,ta_shoppoint s where w.gwdid=s.shop_point_id and w.tid='"+groupID+"'";
		String querySceneryInfo="select s.cmpny_name from ta_zt_bxjd w,ta_scenery_point s where w.jdid=s.scenery_id and w.tid='"+groupID+"'";
		try {
			coreDAO.executeQuery(queryPlusInfo, "queryPlusInfo", rd);
			coreDAO.executeQuery(queryShopInfo, "queryShopInfo", rd);
			coreDAO.executeQuery(querySceneryInfo, "querySceneryInfo", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}//加载加点信息
		return 999;
	}
	public int queryPlusMng(BizData rd,BizData sd){
		String groupID = rd.getString("TA_ZT_BXJIADIAN","TID",0);
		String STATE = rd.getString("flag");
		String TCCY = rd.getString("TCCY");
		String LCBL = rd.getString("LCBL");
		try {
			if("Edit".equals(STATE)||"view".equals(STATE)){
				String bxTCBL = "select d.* from dmsm d where d.dmlb = '"+TCCY+"' order by d.dmz";
				String bxLCBL = "select d.* from dmsm d where d.dmlb = '"+LCBL+"' order by d.dmz";
				String plusInfo = "select j.* from ta_zt_bxjiadian j where j.tid='"+groupID+"'";
				String bxInitCYHZ = "select * from TA_JDBXTCCYHZ_ZT t where t.tid='"+groupID+"'";
				coreDAO.executeQuery(bxInitCYHZ, "bxInitCYHZ", rd);//加载加点提成汇总信息
				coreDAO.executeQuery(bxTCBL, "bxTCBL", rd);//加载提成成员 以及 提成比例
				coreDAO.executeQuery(bxLCBL, "bxLCBL", rd);//加载留存比例
				coreDAO.executeQuery(plusInfo, "plusInfo",rd);
			}
			if("init".equals(STATE)){
				String bxTCBL = "select d.* from dmsm d where d.dmlb = '"+TCCY+"' order by d.dmz";
				String bxLCBL = "select d.* from dmsm d where d.dmlb = '"+LCBL+"' order by d.dmz";
				String plusInfo = "select j.sf sfid,j.cs csid,g.jdid,g.ischeck \n" +
								  " from ta_zt_jhjiad j,ta_zt_jhjiadjd g where j.id=g.jhid and g.ischeck='Y' and j.tid='"+groupID+"'";
				coreDAO.executeQuery(bxTCBL, "bxTCBL", rd);//加载提成成员 以及 提成比例
				coreDAO.executeQuery(bxLCBL, "bxLCBL", rd);//加载留存比例
				coreDAO.executeQuery(plusInfo, "plusInfo",rd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
	}
	public int plusGeneralMng(BizData rd,BizData sd){
		Connection conn = coreDAO.getConnection();
		String groupID = rd.getString("TID");
		String STATE = rd.getString("state");
		String BXR = rd.getString("BXR");
		String BXZT = "Y";

		try{
			coreDAO.beginTrasct(conn);
			deleteAndEdit(groupID, STATE, rd, conn);//判断 状态为Edit修改 则删除表
			insertJDBXTCCYHZ(groupID, rd, conn);//插入购物报销  提成成员汇总
			updateTDBXZJXXB(groupID, rd, conn);//插入 团队报销 总计信息表
			String[] plusRows = rd.getRowIDs("TA_ZT_BXJIADIAN");
			for(int i = 0; i < plusRows.length; i++){
				int plusID = this.queryMaxIDByPara("TA_ZT_BXJIADIAN", "ID", null);
				String SFID = rd.getString("TA_ZT_BXJIADIAN","SFID",plusRows[i]);
				String CSID = rd.getString("TA_ZT_BXJIADIAN","CSID",plusRows[i]);
				String JDID = rd.getString("TA_ZT_BXJIADIAN","JDID",plusRows[i]);
				String GPJ = rd.getString("TA_ZT_BXJIADIAN","GPJ",plusRows[i]);
				String CBDJ = rd.getString("TA_ZT_BXJIADIAN","CBDJ",plusRows[i]);
				String RS = rd.getString("TA_ZT_BXJIADIAN","RS",plusRows[i]);
				String JDQDXJ = rd.getString("TA_ZT_BXJIADIAN","JDQDXJ",plusRows[i]);
				String JDXFXJ = rd.getString("TA_ZT_BXJIADIAN","JDXFXJ",plusRows[i]);
				String CBJE = rd.getString("TA_ZT_BXJIADIAN","CBJE",plusRows[i]);
				String LR = rd.getString("TA_ZT_BXJIADIAN","LR",plusRows[i]);
				String TCFS = rd.getString("TA_ZT_BXJIADIAN","TCFS",plusRows[i]);
				String RTFXJ = rd.getString("TA_ZT_BXJIADIAN","RTFXJ",plusRows[i]);
				String YJGS = rd.getString("TA_ZT_BXJIADIAN","YJGS",plusRows[i]);
				rd.add("TA_ZT_BXJIADIAN", "ID", plusRows[i], plusID);
				rd.add("TA_ZT_BXJIADIAN", "TID", plusRows[i], groupID);
				rd.add("TA_ZT_BXJIADIAN", "SFID", plusRows[i], SFID);
				rd.add("TA_ZT_BXJIADIAN", "CSID", plusRows[i], CSID);
				rd.add("TA_ZT_BXJIADIAN", "JDID", plusRows[i], JDID);
				rd.add("TA_ZT_BXJIADIAN", "GPJ", plusRows[i], GPJ);
				rd.add("TA_ZT_BXJIADIAN", "CBDJ", plusRows[i], CBDJ);
				rd.add("TA_ZT_BXJIADIAN", "RS", plusRows[i], RS);
				rd.add("TA_ZT_BXJIADIAN", "JDQDXJ", plusRows[i], JDQDXJ);
				rd.add("TA_ZT_BXJIADIAN", "JDXFXJ", plusRows[i], JDXFXJ);
				rd.add("TA_ZT_BXJIADIAN", "CBJE", plusRows[i], CBJE);
				rd.add("TA_ZT_BXJIADIAN", "LR", plusRows[i], LR);
				rd.add("TA_ZT_BXJIADIAN", "TCFS", plusRows[i], TCFS);
				rd.add("TA_ZT_BXJIADIAN", "RTFXJ", plusRows[i], RTFXJ);
				rd.add("TA_ZT_BXJIADIAN", "YJGS", plusRows[i], YJGS);
				String[] bxTCBLRows = rd.getRowIDs("bxTCBL"+plusRows[i]);
				if(null!=bxTCBLRows){
					for(int k = 0; k < bxTCBLRows.length; k++){
						String TCMC = rd.getString("bxTCBL"+plusRows[i],"TCMC",bxTCBLRows[k]);
						String TCBL = rd.getString("bxTCBL"+plusRows[i],"TCBL",bxTCBLRows[k]);
						String TCJE = rd.getString("bxTCBL"+plusRows[i],"TCJE",bxTCBLRows[k]);
						if("导游".equals(TCMC)){//判断提成名称是否为导游
							String DYTC =TCJE;//提成金额
							String DYZB =TCBL;//提成比例
							rd.add("TA_ZT_BXJIADIAN", "DYZB", plusRows[i], DYZB);
							rd.add("TA_ZT_BXJIADIAN", "DYTC", plusRows[i], DYTC);
						}
						if("司机".equals(TCMC)){//判断提成名称是否为司机
							String SJTC =TCJE;
							String SJZB =TCBL;
							rd.add("TA_ZT_BXJIADIAN", "SJTC", plusRows[i], SJTC);
							rd.add("TA_ZT_BXJIADIAN", "SJZB", plusRows[i], SJZB);
						}
						if("全陪".equals(TCMC)){//判断提成名称是否为全陪
							String QPTC =TCJE;
							String QPZB =TCBL;
							rd.add("TA_ZT_BXJIADIAN", "QPTC", plusRows[i], QPTC);
							rd.add("TA_ZT_BXJIADIAN", "QPZB", plusRows[i], QPZB);
						}
						if("公司".equals(TCMC)){//判断提成名称是否为应交公司
							String GSTC =TCJE;
							rd.add("TA_ZT_BXJIADIAN", "GSTC", plusRows[i], GSTC);
						}
					}
				}
				rd.add("TA_ZT_BXJIADIAN", "BXR", plusRows[i], BXR);
				rd.add("TA_ZT_BXJIADIAN", "BXZT", plusRows[i], BXZT);
			}
			coreDAO.insert("TA_ZT_BXJIADIAN", rd, conn);//插加点报销表
			rd.remove("TA_ZT_BXJIADIAN");
			BizData dtt = new BizData();
			dtt.addAttr("TA_ZT_GROUP", "ID", 0, "oldValue", groupID);
			dtt.add("TA_ZT_GROUP", "STATE", "5");//修改团状态为 5  实施中
			coreDAO.update("TA_ZT_GROUP", dtt, conn);
			coreDAO.commitTrasct(conn);
		}catch (SQLException e) {
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
		return 999;
	}
	/**
	 * deleteAndEdit:(根据状态Edit判断 修改 先删除)
	 *
	 * @param  @param groupID
	 * @param  @param STATE
	 * @param  @param rd
	 * @param  @param conn
	 * @param  @return
	 * @param  @throws SQLException    设定文件
	 * @return int    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	public int deleteAndEdit(String groupID, String STATE, BizData rd, Connection conn) throws SQLException{
		if("Edit".equals(STATE)){
			BizData data = new BizData();
			data.add("TA_ZT_BXJIADIAN", "TID", groupID);
			coreDAO.delete(data, conn);
			data.remove("TA_ZT_BXJIADIAN");//删除购物明细表
			data.add("TA_JDBXTCCYHZ_ZT", "TID", groupID);
			coreDAO.delete(data, conn);
			data.remove("TA_JDBXTCCYHZ_ZT");//删除报销提成成员汇总表
		}
		return 999;
		
	}
	/**
	 * insertTDBXTCCYHZ:(//插入加点报销  提成成员汇总)
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
	public int insertJDBXTCCYHZ(String groupID,BizData rd,Connection conn) throws SQLException{
		String[] CYHZRows = rd.getRowIDs("TA_JDBXTCCYHZ_ZT");
		if(null != CYHZRows){
			for(int i = 0;i < CYHZRows.length; i++){
				int CYHZID = this.queryMaxIDByPara("TA_JDBXTCCYHZ_ZT","ID",null);
				String CYID = rd.getString("TA_JDBXTCCYHZ_ZT","CYID",CYHZRows[i]);
				String CYMC = rd.getString("TA_JDBXTCCYHZ_ZT","CYMC",CYHZRows[i]);
				String CYJEHZ = rd.getString("TA_JDBXTCCYHZ_ZT","CYJEHZ",CYHZRows[i]);
				rd.add("TA_JDBXTCCYHZ_ZT", "ID", CYHZRows[i], CYHZID);
				rd.add("TA_JDBXTCCYHZ_ZT", "TID", CYHZRows[i], groupID);
				rd.add("TA_JDBXTCCYHZ_ZT", "CYID", CYHZRows[i], CYID);
				rd.add("TA_JDBXTCCYHZ_ZT", "CYMC", CYHZRows[i], CYMC);
				rd.add("TA_JDBXTCCYHZ_ZT", "CYJEHZ", CYHZRows[i], CYJEHZ);
			}
			coreDAO.insert("TA_JDBXTCCYHZ_ZT", rd, conn);
			rd.remove("TA_JDBXTCCYHZ_ZT");
		}
		return 999;
		
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

