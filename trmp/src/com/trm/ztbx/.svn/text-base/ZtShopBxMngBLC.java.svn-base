/**
 * DjShopBxMngBLC.java
 * com.trmdj.bxGuide
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2011-8-5 		Administrator
 *
 * Copyright (c) 2011, TNT All Rights Reserved.
*/

package com.trm.ztbx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * ClassName:DjShopBxMngBLC
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Administrator
 * @version  
 * @since    Ver 1.1
 * @Date	 2011-8-5		上午06:31:51
 *
 * @see 	 
 * @deprecated 购物报销事务处理模块
 */
public class ZtShopBxMngBLC extends DBBLC {
	public ZtShopBxMngBLC(){
		this.entityName = "TA_ZT_BXGW";
	}
	
	public int queryShopMng(BizData rd,BizData sd){
		String FLAG = rd.getString("flag");
		try {
			if("edit".equals(FLAG)||"view".equals(FLAG)){
				String groupID=rd.getStringByDI("TA_ZT_BXGW","TID",0);//获取团号
				String TCCY = rd.getString("TCCY");
				String LCBL = rd.getString("LCBL");
				String bxTCBL = "select d.* from dmsm d where d.dmlb = '"+TCCY+"' order by d.dmz";
				String bxLCBL = "select d.* from dmsm d where d.dmlb = '"+LCBL+"' order by d.dmz";
				String bxInitGWD =  "select w.*,t.CMPNY_NAME gwdmc,t.tcbl tcblall \n" +
				   					" from ta_zt_bxgw w ,ta_shoppoint t \n" +
				   					"where w.tid='"+groupID+"' \n" +
				   					"and w.gwdid = t.shop_point_id \n" +
				   					"order by w.gwdid";
				String bxInitRTF = "select f.csid,f.rtf,h.name,w.gwdid from ta_zt_gwbxrtf f,ta_zt_bxgw w,xzqh h \n"+
								   "where f.bxid=w.id \n" +
								   "and f.csid=h.id \n" +
								   "and w.tid='"+groupID+"'" +
								   "order by w.gwdid";//修改查询语句
				//goods_id商品ID shop_point_id购物点ID
				String bxInitShop = "select g.goods_id,g.goods_name,g.shop_point_id,mx.rs,mx.xfe,mx.lc,mx.jdrs  from ta_zt_bxgw t, ta_shop_goods g, ta_zt_bxgwmx mx \n" +
									"where mx.bxid = t.id \n" +
									"and mx.spid = g.goods_id \n"+
									"and t.tid='"+groupID+"' \n" +
									"order by g.shop_point_id";
				String bxInitCYHZ = "select * from TA_TDBXTCCYHZ_ZT t where t.tid='"+groupID+"'";
				coreDAO.executeQuery(bxInitCYHZ, "bxInitCYHZ", rd);//加载购物点信息
				coreDAO.executeQuery(bxInitGWD, "shopBxGWD", rd);//加载购物点信息
				coreDAO.executeQuery(bxInitRTF, "shopBxRTF", rd);//加载人头费信息
				coreDAO.executeQuery(bxInitShop, "bxShopInfo", rd);//该购物点下的商品名称
				coreDAO.executeQuery(bxTCBL, "bxTCBL", rd);//加载提成成员 以及 提成比例
				coreDAO.executeQuery(bxLCBL, "bxLCBL", rd);//加载留存比例
			}
			if("init".equals(FLAG)){
				String groupID=rd.getStringByDI("TA_ZT_BXGW","TID",0);//获取团号
				String TCCY = rd.getString("TCCY");
				String LCBL = rd.getString("LCBL");
				String bxTCBL = "select d.* from dmsm d where d.dmlb = '"+TCCY+"' order by d.dmz";
				String bxLCBL = "select d.* from dmsm d where d.dmlb = '"+LCBL+"' order by d.dmz";
				String bxInitGWD = "select distinct  w.SFID,w.csid,w.gwdid,w.gwdmc,w.sfxz,p.tcbl tcblall \n" +
								   "  from ta_zt_jhgw w,ta_shoppoint p where w.tid='"+groupID+"' and w.gwdid=p.shop_point_id and w.sfxz='Y' order by w.gwdid";
				String bxInitRTF="select f.csid,f.rtf,h.name,w.gwdid from ta_zt_gwrtf f,ta_zt_jhgw w,xzqh h \n"+
								  "where f.jhid=w.id \n" +
								  "and f.csid=h.id \n" +
								  "and w.tid='"+groupID+"'" +
								  "order by w.gwdid";//修改查询语句
				String bxInitShop = "select b.goods_id,b.goods_name,b.shop_point_id \n" +
									" from ta_zt_jhgw a,ta_shop_goods b \n" +
									"where a.gwdid=b.shop_point_id and a.tid='"+groupID+"'";
				coreDAO.executeQuery(bxInitGWD, "shopBxGWD", rd);//加载购物点信息
				coreDAO.executeQuery(bxInitRTF, "shopBxRTF", rd);//加载人头费信息
				coreDAO.executeQuery(bxInitShop, "bxShopInfo", rd);//该购物点下的商品名称
				coreDAO.executeQuery(bxTCBL, "bxTCBL", rd);//加载提成成员 以及 提成比例
				coreDAO.executeQuery(bxLCBL, "bxLCBL", rd);//加载留存比例
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
	}
	
	public int shopGeneralRbt(BizData rd,BizData sd){
		Connection conn = coreDAO.getConnection();
		String groupID = rd.getStringByDI("TA_ZT_BXGW","TID",0);
		String STATE = rd.getString("state");
		String BXR = rd.getString("BXR");
		String BXSTATE = "Y";
		
		try{
			coreDAO.beginTrasct(conn);
			deleteAndEdit(groupID, STATE, rd, conn);//判断 状态为Edit修改 则删除表
			insertTDBXTCCYHZ(groupID, rd, conn);//插入购物报销  提成成员汇总
			updateTDBXZJXXB(groupID, rd, conn);//插入 团队报销 总计信息表
			String[] gwdRows = rd.getRowIDs("TA_ZT_BXGW");
			if(null!=gwdRows){
				BizData rd2 = new BizData();
				for(int i = 0; i < gwdRows.length; i++){
					int BXID = this.queryMaxIDByPara("TA_ZT_BXGW","ID",null);//获取购物报销表ID
					String SFID = rd.getString("TA_ZT_BXGW","SFID",gwdRows[i]);//省份ID
					String CSID = rd.getString("TA_ZT_BXGW","CSID",gwdRows[i]);//城市ID
					String GWDID = rd.getString("TA_ZT_BXGW","GWDID",gwdRows[i]);//购物点ID
					String RSXJ = rd.getString("TA_ZT_BXGW"+GWDID,"RSXJ",gwdRows[i]);//购物人数小计
					String JDRSXJ = rd.getString("TA_ZT_BXGW"+GWDID,"JDRSXJ",gwdRows[i]);//进店人数小计
					String XFEXJ = rd.getString("TA_ZT_BXGW"+GWDID,"XFEXJ",gwdRows[i]);//消费额小计
					String GSLCXJ = rd.getString("TA_ZT_BXGW"+GWDID,"GSLCXJ",gwdRows[i]);//公司留存小计
					String TCFS = rd.getString("TA_ZT_BXGW"+GWDID,"TCFS",gwdRows[i]);//提成方式
					String TCJEZJ = rd.getString("TA_ZT_BXGW"+GWDID,"TCJEZJ",gwdRows[i]);//提成金额总计
					String YJGSXJ = rd.getString("TA_ZT_BXGW"+GWDID,"YJGSXJ",gwdRows[i]);//应交公司小计
					String RTFXJ = rd.getString("TA_ZT_BXGW"+GWDID,"RTFXJ",gwdRows[i]);//人头费小计
					String REMARK = rd.getString("TA_ZT_BXGW"+GWDID,"REMARK",gwdRows[i]);//备注
					getBXGW(rd, gwdRows, i, GWDID);//获取提成方式
					rd.add("TA_ZT_BXGW"+GWDID, "ID", gwdRows[i], BXID);
					rd.add("TA_ZT_BXGW"+GWDID, "TID", gwdRows[i], groupID);
					rd.add("TA_ZT_BXGW"+GWDID, "GWDID", gwdRows[i], GWDID);
					rd.add("TA_ZT_BXGW"+GWDID, "SFID", gwdRows[i], SFID);
					rd.add("TA_ZT_BXGW"+GWDID, "CSID", gwdRows[i], CSID);
					rd.add("TA_ZT_BXGW"+GWDID, "RSXJ", gwdRows[i], RSXJ);
					rd.add("TA_ZT_BXGW"+GWDID, "YJGSXJ", gwdRows[i], YJGSXJ);
					rd.add("TA_ZT_BXGW"+GWDID, "RTFXJ", gwdRows[i], RTFXJ);
					rd.add("TA_ZT_BXGW"+GWDID, "XFEXJ", gwdRows[i], XFEXJ);
					rd.add("TA_ZT_BXGW"+GWDID, "GSLCXJ", gwdRows[i], GSLCXJ);
					rd.add("TA_ZT_BXGW"+GWDID, "TCFS", gwdRows[i], TCFS);
					rd.add("TA_ZT_BXGW"+GWDID, "TCJE", gwdRows[i], TCJEZJ);
					rd.add("TA_ZT_BXGW"+GWDID, "BXR", gwdRows[i], BXR);
					rd.add("TA_ZT_BXGW"+GWDID, "BXZT", gwdRows[i], BXSTATE);
					rd.add("TA_ZT_BXGW"+GWDID, "JDRSXJ", gwdRows[i], JDRSXJ);
					rd.add("TA_ZT_BXGW"+GWDID, "REMARK", gwdRows[i], REMARK);
					rd2.copyEntity(rd, "TA_ZT_BXGW"+GWDID, "TA_ZT_BXGW");
					insertGWBXRTF(groupID, GWDID, BXID, rd, conn);//插入购物报销  报销人头费表
					insertBXGWMX(groupID, GWDID, BXID, rd, conn);//插入购物报销  购物明细表
					coreDAO.insert("TA_ZT_BXGW", rd2, conn);//插购物报销表
					rd2.remove("TA_ZT_BXGW");
				}
			}
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
	 * getBXGW:(//获取提成方式,根据判断一一传值)
	 *
	 * @param  @param rd
	 * @param  @param gwdRows
	 * @param  @param i
	 * @param  @param GWDID    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	public void getBXGW(BizData rd, String[] gwdRows, int i, String GWDID) {
		String[] bxTCBLRows = rd.getRowIDs("bxTCBL"+GWDID);
		if(null!=bxTCBLRows){
			for(int k = 0; k < bxTCBLRows.length; k++){
				String TCMC = rd.getString("bxTCBL"+GWDID,"TCMC",bxTCBLRows[k]);
				String TCBL = rd.getString("bxTCBL"+GWDID,"TCBL",bxTCBLRows[k]);
				String TCJE = rd.getString("bxTCBL"+GWDID,"TCJE",bxTCBLRows[k]);
				if("导游".equals(TCMC)){//判断提成名称是否为导游
					String DYTC =TCJE;//提成金额
					String DYZB =TCBL;//提成比例
					rd.add("TA_ZT_BXGW"+GWDID, "DYZB", gwdRows[i], DYZB);
					rd.add("TA_ZT_BXGW"+GWDID, "DYTC", gwdRows[i], DYTC);
				}
				if("司机".equals(TCMC)){//判断提成名称是否为司机
					String SJTC =TCJE;
					String SJZB =TCBL;
					rd.add("TA_ZT_BXGW"+GWDID, "SJTC", gwdRows[i], SJTC);
					rd.add("TA_ZT_BXGW"+GWDID, "SJZB", gwdRows[i], SJZB);
				}
				if("全陪".equals(TCMC)){//判断提成名称是否为全陪
					String QPTC =TCJE;
					String QPZB =TCBL;
					rd.add("TA_ZT_BXGW"+GWDID, "QPTC", gwdRows[i], QPTC);
					rd.add("TA_ZT_BXGW"+GWDID, "QPZB", gwdRows[i], QPZB);
				}
				if("公司".equals(TCMC)){//判断提成名称是否为应交公司
					String GSTC =TCJE;
					rd.add("TA_ZT_BXGW"+GWDID, "GSTC", gwdRows[i], GSTC);
				}
			}
		}
	}
	
	public int insertGWBXRTF(String groupID,String GWDID,int BXID,BizData rd,Connection conn) throws SQLException{
		String[] rtfRows = rd.getRowIDs("TA_ZT_GWBXRTF"+GWDID);
		if(null!=rtfRows){
			BizData rd3 = new BizData();
			for(int j = 0;j < rtfRows.length; j++){
				int rtfID = this.queryMaxIDByPara("TA_ZT_GWBXRTF","ID",null);
				String RTF =rd.getString("TA_ZT_GWBXRTF"+GWDID, "RTF",  rtfRows[j]);//人头费
				String RTFSFID =rd.getString("TA_ZT_GWBXRTF"+GWDID, "SFID",  rtfRows[j]);//人头费省份ID
				String RTFSFMC =rd.getString("TA_ZT_GWBXRTF"+GWDID, "SFMC",  rtfRows[j]);//人头费省份名称
				rd.add("TA_ZT_GWBXRTF"+GWDID, "ID", rtfRows[j], rtfID);//获取人头费表ID
				rd.add("TA_ZT_GWBXRTF"+GWDID, "BXID", rtfRows[j], BXID);
				rd.add("TA_ZT_GWBXRTF"+GWDID, "TID", rtfRows[j], groupID);
				rd.add("TA_ZT_GWBXRTF"+GWDID, "RTF", rtfRows[j], RTF);
				rd.add("TA_ZT_GWBXRTF"+GWDID, "CSID", rtfRows[j], RTFSFID);
				rd.add("TA_ZT_GWBXRTF"+GWDID, "SFMC", rtfRows[j], RTFSFMC);
				rd3.copyEntity(rd,"TA_ZT_GWBXRTF"+GWDID,"TA_ZT_GWBXRTF");
			}
			//插入购物报销     报销人头费表
			coreDAO.insert("TA_ZT_GWBXRTF", rd3, conn);
			rd3.remove("TA_ZT_GWBXRTF");
		}
		return 999;
		
	}
	
	/**
	 * insertBXGWMX:(//插入购物报销 购物明细表)
	 *
	 * @param  @param groupID
	 * @param  @param GWDID
	 * @param  @param BXID
	 * @param  @param rd
	 * @param  @param conn
	 * @param  @return
	 * @param  @throws SQLException    设定文件
	 * @return int    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	public int insertBXGWMX(String groupID,String GWDID,int BXID,BizData rd,Connection conn) throws SQLException{
		String[] MerRows = rd.getRowIDs("TA_ZT_BXGWMX"+GWDID);
		if(null!=MerRows){
			BizData rd4 = new BizData();
			for(int k = 0; k < MerRows.length; k++){
				int GWMXID = this.queryMaxIDByPara("TA_ZT_BXGWMX","ID",null);//报销 购物明细ID
				String SPID = rd.getString("TA_ZT_BXGWMX"+GWDID, "GOODSID", MerRows[k]);//商品ID
				String XFE = rd.getString("TA_ZT_BXGWMX"+GWDID, "XFE", MerRows[k]);//消费额
				String RS = rd.getString("TA_ZT_BXGWMX"+GWDID, "RS", MerRows[k]);//购物人数
				String JDRS = rd.getString("TA_ZT_BXGWMX"+GWDID, "JDRS", MerRows[k]);//进店人数
				String LC = rd.getString("TA_ZT_BXGWMX"+GWDID, "LC", MerRows[k]);//留存
				rd.add("TA_ZT_BXGWMX"+GWDID, "ID", MerRows[k], GWMXID);
				rd.add("TA_ZT_BXGWMX"+GWDID, "BXID", MerRows[k], BXID);
				rd.add("TA_ZT_BXGWMX"+GWDID, "TID", MerRows[k], groupID);
				rd.add("TA_ZT_BXGWMX"+GWDID, "SPID", MerRows[k], SPID);//商品ID
				rd.add("TA_ZT_BXGWMX"+GWDID, "XFE", MerRows[k], XFE);
				rd.add("TA_ZT_BXGWMX"+GWDID, "RS", MerRows[k], RS);
				rd.add("TA_ZT_BXGWMX"+GWDID, "JDRS", MerRows[k], JDRS);
				rd.add("TA_ZT_BXGWMX"+GWDID, "LC", MerRows[k], LC);
				rd4.copyEntity(rd,"TA_ZT_BXGWMX"+GWDID,"TA_ZT_BXGWMX");
			}
			coreDAO.insert("TA_ZT_BXGWMX", rd4, conn);
			rd4.remove("TA_ZT_BXGWMX");
		}
		return 999;
	}
	
	/**
	 * insertTDBXTCCYHZ:(//插入购物报销  提成成员汇总)
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
	public int insertTDBXTCCYHZ(String groupID,BizData rd,Connection conn) throws SQLException{
		String[] CYHZRows = rd.getRowIDs("TA_TDBXTCCYHZ_ZT");
		if(null != CYHZRows){
			for(int i = 0;i < CYHZRows.length; i++){
				int CYHZID = this.queryMaxIDByPara("TA_TDBXTCCYHZ_ZT","ID",null);
				String CYID = rd.getString("TA_TDBXTCCYHZ_ZT","CYID",CYHZRows[i]);
				String CYMC = rd.getString("TA_TDBXTCCYHZ_ZT","CYMC",CYHZRows[i]);
				String CYJEHZ = rd.getString("TA_TDBXTCCYHZ_ZT","CYJEHZ",CYHZRows[i]);
				rd.add("TA_TDBXTCCYHZ_ZT", "ID", CYHZRows[i], CYHZID);
				rd.add("TA_TDBXTCCYHZ_ZT", "TID", CYHZRows[i], groupID);
				rd.add("TA_TDBXTCCYHZ_ZT", "CYID", CYHZRows[i], CYID);
				rd.add("TA_TDBXTCCYHZ_ZT", "CYMC", CYHZRows[i], CYMC);
				rd.add("TA_TDBXTCCYHZ_ZT", "CYJEHZ", CYHZRows[i], CYJEHZ);
			}
			coreDAO.insert("TA_TDBXTCCYHZ_ZT", rd, conn);
			rd.remove("TA_TDBXTCCYHZ_ZT");
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
			data.add("TA_ZT_BXGW", "TID", groupID);
			coreDAO.delete(data, conn);
			data.remove("TA_ZT_BXGW");//删除购物明细表
			
			data.add("TA_TDBXTCCYHZ_ZT", "TID", groupID);
			coreDAO.delete(data, conn);
			data.remove("TA_TDBXTCCYHZ_ZT");//删除报销提成成员汇总表
			
			data.add("TA_ZT_BXGWMX", "TID", groupID);
			coreDAO.delete(data, conn);
			data.remove("TA_ZT_BXGWMX");//删除报销购物明细表
			
			data.add("TA_ZT_GWBXRTF", "TID", groupID);
			coreDAO.delete(data, conn);
			data.remove("TA_ZT_GWBXRTF");//删除报销购物人头费表
		}
		return 999;
		
	}
}
