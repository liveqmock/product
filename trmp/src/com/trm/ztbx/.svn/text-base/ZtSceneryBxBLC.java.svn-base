/**
 * DjSceneryBxBLC.java
 * com.trmdj.bx
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2011-8-2 		Administrator
 *
 * Copyright (c) 2011, TNT All Rights Reserved.
*/

package com.trm.ztbx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * ClassName:DjSceneryBxBLC
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Administrator
 * @version  
 * @since    Ver 1.1
 * @Date	 2011-8-2		上午09:26:42
 *
 * @see 	 
 * @deprecated 
 */
public class ZtSceneryBxBLC extends DBBLC {
	public ZtSceneryBxBLC(){
		this.entityName = "TA_ZT_BXJD";
	}
	/**
	 * querySceneryMng:(判断景点报销 flag 为edit 则为修改   init则为初始化)
	 *
	 * @param  @param rd
	 * @param  @param sd    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	public int querySceneryMng(BizData rd,BizData sd){
		String RbtState = rd.getString("flag");
		
		try {
			if("edit".equals(RbtState)||"view".equals(RbtState)){//报销 修改状态
				String groupID = rd.getStringByDI("TA_ZT_BXJD", "TID", 0);
				String rbtSceneryInfoEdit = "select j.id,j.tid,j.sfid,j.csid,j.jdid,j.jgqj,j.sfxz,j.qdxj,j.xfxj,j.hj,j.bz,p.scenery_id,p.cmpny_name,p.city_id \n" +
											" from ta_scenery_point p,ta_zt_bxjd j \n"+
											"where j.jdid = p.scenery_id \n"+
											"and j.csid = p.city_id \n"+
											"and j.tid = '"+ groupID +"' \n"+
											"order by j.csid";
				String rbtSceneryPriceEdit = "select j.* from ta_zt_bxjdjg j \n" +
											 "where j.tid= '"+ groupID +"'";
				String rbtCityDisEdit = "select distinct j.csid,j.sfid  from ta_zt_bxjd j \n"+
										"where j.tid = '"+ groupID +"' \n"+
										"order by j.csid";
				String rbtJDXXZJBEdit = "select BX_JDXF XF,BX_JDQD QD,JINDHJ ZJ from TA_TDBXZJXXB_ZT where tid='"+ groupID +"'";
				coreDAO.executeQuery(rbtJDXXZJBEdit,"rbtJDXXZJB",rd);//团队报销总计信息表
				coreDAO.executeQuery(rbtSceneryInfoEdit,"rbtSceneryInfo",rd);//团报销景点 表
				coreDAO.executeQuery(rbtSceneryPriceEdit,"rbtSceneryPrice",rd);//团报销价格表
				coreDAO.executeQuery(rbtCityDisEdit,"rbtCityDis",rd);//团报销 去 重复 城市
			}
			
			if("init".equals(RbtState)){//报销 初始化 状态
				String groupID = rd.getStringByDI("TA_ZT_BXJD", "TID", 0);
				String rbtSceneryInfoInit = "select j.id,j.tid,j.sfid,j.csid,j.jdid,j.jgqj,j.sfxz,j.qdxj,j.xfxj,j.hj,p.scenery_id,p.cmpny_name,p.city_id \n" +
										" from ta_scenery_point p,ta_zt_jhjd j \n" +
										"where j.jdid = p.scenery_id \n"+
										"and j.csid = p.city_id \n"+
										"and j.sfxz = 'Y' \n" +
										"and j.tid = '"+ groupID +"' \n"+
										"order by j.csid";
				String rbtSceneryPriceInit ="select  j.id,j.JHID BXID,j.JGMCID,j.JGMC,j.JG,j.RS from ta_zt_jhjdjg j \n" +
										"where j.tid= '"+ groupID +"'";
				String rbtCityDisInit = "select distinct j.csid,j.sfid  from ta_zt_jhjd j \n"+
									"where j.tid = '"+ groupID +"' \n"+
									"order by j.csid";
				String rbtJDXXZJBInit = "select XFJDZJ XF,QDJDZJ QD,JDZJ ZJ from TA_TDJDXXZJB_zt where tid='"+ groupID +"'";
				coreDAO.executeQuery(rbtJDXXZJBInit,"rbtJDXXZJB",rd);//团队计调信息总计表
				coreDAO.executeQuery(rbtSceneryInfoInit,"rbtSceneryInfo",rd);//团计划景点 表
				coreDAO.executeQuery(rbtSceneryPriceInit,"rbtSceneryPrice",rd);//团计划价格表
				coreDAO.executeQuery(rbtCityDisInit,"rbtCityDis",rd);//团计划 去重复 城市
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return 999;
	}
	/**
	 * initSceneryPrice:(AJAX加载景点价格)
	 *
	 * @param  @param rd
	 * @param  @param sd
	 * @param  @return    设定文件
	 * @return int    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	public int initSceneryPriceRbt(BizData rd,BizData sd){
		String JDID = rd.getString("JDID");
		String dwj = rd.getString("dwj");
		String sqlListAddPrice = "select s.scenery_id,p.price_type dwj,p.price,d.dmsm1 priceName,p.price_name priceNMC\n" +
				"from ta_scenery_point s,ta_scenery_point_price p,dmsm d\n" +
				"where s.scenery_id=p.scenery_id and d.dmz=p.price_name and d.dmlb=12\n" +
				"and p.scenery_id='"+JDID+"' and p.price_type='"+dwj+"'";
		try {
			coreDAO.executeQuery(sqlListAddPrice, "sqlListAddPrice", rd);//AJAX加载 查询景点价格信息
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * scenryGeneral:(景点报销  添加 修改 操作)
	 *
	 * @param  @param rd
	 * @param  @param sd
	 * @param  @return    设定文件
	 * @return int    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	public int scenryGeneralRbt(BizData rd,BizData sd){
		Connection conn = coreDAO.getConnection();
		String groupID = rd.getStringByDI("TA_ZT_BXJD","TID",0);//团号
		String BXR = rd.getString("userno");//制定人
		String STATE = rd.getString("state");//状态
		String STATEBX = "Y";//报销状态
		try {
			coreDAO.beginTrasct(conn);
			//如果 状态 为 Edit   修改 先删除再添加
			if("Edit".equals(STATE)){
				BizData del1 = new BizData();
				del1.add("TA_ZT_BXJD", "TID", groupID);
				coreDAO.delete(del1, conn);
				del1.remove("TA_ZT_BXJD");
				del1.add("TA_ZT_BXJDJG", "TID", groupID);
				coreDAO.delete(del1, conn);
				del1.remove("TA_ZT_BXJDJG");
			}
			String[] rows = rd.getRowIDs("TA_ZT_BXJD");
			for(int i=0;i<rows.length;i++){
				String CSID = rd.getString("TA_ZT_BXJD", "CSID", rows[i]);//计划城市
				String SFID = rd.getString("TA_ZT_BXJD", "SFID", rows[i]);//计划省份
				String[] SceneryRows = rd.getRowIDs("JDID"+CSID);//获取景点行数
				if(null != SceneryRows){
					BizData data1 = new BizData();
					for(int j = 0; j < SceneryRows.length; j++){
						int BXID = this.queryMaxIDByPara("TA_ZT_BXJD","ID",null);//计划ID
						String JDID = rd.getString("JDID"+CSID, "JDID", SceneryRows[j]);//景点ID
						String BZ = rd.getString("BZ"+JDID);//景点ID
						String JGQJ = rd.getString("dwj"+JDID);//价格区间 淡旺季
						String SFXZ = rd.getString("SFXZ"+JDID);//是否选中
						String QDXJ = rd.getString("QDXJ"+JDID);//签单小计
						String XFXJ = rd.getString("XFXJ"+JDID);//现付小计
						String HJ = rd.getString("HJ"+JDID);//合计
						rd.add("TA_ZT_BXJD"+CSID, "ID", SceneryRows[j], BXID);
						rd.add("TA_ZT_BXJD"+CSID, "TID", SceneryRows[j], groupID);//团号
						rd.add("TA_ZT_BXJD"+CSID, "SFID", SceneryRows[j], SFID);
						rd.add("TA_ZT_BXJD"+CSID, "CSID", SceneryRows[j], CSID);
						rd.add("TA_ZT_BXJD"+CSID, "JDID", SceneryRows[j], JDID);
						rd.add("TA_ZT_BXJD"+CSID, "JGQJ", SceneryRows[j], JGQJ);
						rd.add("TA_ZT_BXJD"+CSID, "SFXZ", SceneryRows[j], SFXZ);
						rd.add("TA_ZT_BXJD"+CSID, "QDXJ", SceneryRows[j], QDXJ);
						rd.add("TA_ZT_BXJD"+CSID, "XFXJ", SceneryRows[j], XFXJ);
						rd.add("TA_ZT_BXJD"+CSID, "HJ", SceneryRows[j], HJ);
						rd.add("TA_ZT_BXJD"+CSID, "BZ", SceneryRows[j], BZ);
						rd.add("TA_ZT_BXJD"+CSID, "BXZT", SceneryRows[j], STATEBX);//计划状态
						rd.add("TA_ZT_BXJD"+CSID, "BXR", SceneryRows[j], BXR);//报销人
						data1.copyEntity(rd,"TA_ZT_BXJD"+CSID,"TA_ZT_BXJD");
						
						String[] SceneryPriceRows = rd.getRowIDs("TA_ZT_BXJDJG"+JDID);
						if(null!=SceneryPriceRows){
							BizData data2 = new BizData();
							for(int k = 0; k < SceneryPriceRows.length; k++){
								int priceID = queryMaxIDByPara("TA_ZT_BXJDJG", "ID", null);//景点价格ID
								String priceNameID = rd.getString("TA_ZT_BXJDJG"+JDID, "JGMCID", SceneryPriceRows[k]);//价格名称ID
								String priceName = rd.getString("TA_ZT_BXJDJG"+JDID, "JGMC", SceneryPriceRows[k]);//价格名称
								String price = rd.getString("TA_ZT_BXJDJG"+JDID, "JG", SceneryPriceRows[k]);//价格
								String pCount = rd.getString("TA_ZT_BXJDJG"+JDID, "RS", SceneryPriceRows[k]);//人数
								rd.add("TA_ZT_BXJDJG"+JDID, "ID", SceneryPriceRows[k], priceID);
								rd.add("TA_ZT_BXJDJG"+JDID, "BXID", SceneryPriceRows[k], BXID);
								rd.add("TA_ZT_BXJDJG"+JDID, "TID", SceneryPriceRows[k], groupID);
								rd.add("TA_ZT_BXJDJG"+JDID, "JGMCID", SceneryPriceRows[k], priceNameID);
								rd.add("TA_ZT_BXJDJG"+JDID, "JGMC", SceneryPriceRows[k], priceName);
								rd.add("TA_ZT_BXJDJG"+JDID, "JG", SceneryPriceRows[k], price);
								rd.add("TA_ZT_BXJDJG"+JDID, "RS", SceneryPriceRows[k], pCount);
								data2.copyEntity(rd,"TA_ZT_BXJDJG"+JDID,"TA_ZT_BXJDJG");
							}
							//插景点价格表
							coreDAO.insert("TA_ZT_BXJDJG", data2, conn);
							data2.remove("TA_ZT_BXJDJG");
						}
					}
					//插入景点计划表
					coreDAO.insert("TA_ZT_BXJD", data1, conn);
					data1.remove("TA_ZT_BXJD");
				}
			}
			//更新团队报销信息总表
			updateTDBXZJXXB(rd, conn, groupID);
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
	/**更新团队报销信息总表
	 * @param rd
	 * @param conn
	 * @param groupID
	 * @throws SQLException
	 */
	public void updateTDBXZJXXB(BizData rd, Connection conn, String groupID)
			throws SQLException {
		BizData data = new BizData();
		data.add("TA_TDBXZJXXB_ZT", "TID", groupID);
		coreDAO.select(data);
		int TDBXrows = data.getTableRowsCount("TA_TDBXZJXXB_ZTs");
		data.remove("TA_TDBXZJXXB_ZT");
		data.remove("TA_TDBXZJXXB_ZTs");
		if(TDBXrows > 0){
			rd.addAttr("TA_TDBXZJXXB_ZT", "TID", 0, "oldValue", groupID);
			coreDAO.update("TA_TDBXZJXXB_ZT", rd, conn);
		}else{
			rd.add("TA_TDBXZJXXB_ZT", "TID", groupID);
			coreDAO.insert("TA_TDBXZJXXB_ZT",rd, conn);
		}
	}
}

