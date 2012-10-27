package com.trm.ztPlan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * ClassName:DjSeneryPlanBLC
 * Function:   ADD FUNCTION
 * Reason:	   ADD REASON
 *
 * @author   Kale
 * @version  
 * @since    Ver 1.1
 * @Date	 2011	2011-7-27		上午08:24:09
 *
 * @see 	 
 * @deprecated 
 */
public class ZtSeneryPlanBLC extends DBBLC{
	public ZtSeneryPlanBLC(){
		this.entityName = "TA_ZT_JHJD";
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
	public int initSceneryPrice(BizData rd,BizData sd){
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
	
	public int initSceneryEdit(BizData rd,BizData sd){
		String groupID = rd.getStringByDI("TA_ZT_JHJD", "TID", 0);
		String sqlSceneryInfo ="select j.id,j.tid,j.sfid,j.csid,j.jdid,j.jgqj,j.sfxz,j.qdxj,j.xfxj,j.hj,p.scenery_id,p.cmpny_name,p.city_id from ta_scenery_point p,TA_ZT_jhjd j \n"+
								"where j.jdid = p.scenery_id \n"+
								"and j.csid = p.city_id \n"+
								"and j.tid = '"+ groupID +"' \n" +
								"and p.ywlb='z'\n"+
								"order by j.jdid";
		String sqlSceneryPrice ="select j.* from TA_ZT_jhjdjg j \n" +
								"where j.tid= '"+ groupID +"'";
		String sqlCityDis = "select distinct j.csid,j.sfid  from TA_ZT_jhjd j \n"+
							"where j.tid = '"+ groupID +"' \n"+
							"order by j.csid";
		try {
			coreDAO.executeQuery(sqlSceneryInfo, "sqlSceneryInfo", rd);// 查询景点信息
			coreDAO.executeQuery(sqlSceneryPrice, "sqlSceneryPrice", rd);// 查询景点价格信息
			coreDAO.executeQuery(sqlCityDis, "sqlCityDis", rd);//城市去重
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
	}
	
	/**
	 * scenryGeneral:(景点 添加 修改 操作)
	 *
	 * @param  @param rd
	 * @param  @param sd
	 * @param  @return    设定文件
	 * @return int    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	public int scenryGeneral(BizData rd,BizData sd){
		Connection conn = coreDAO.getConnection();
		String groupID = rd.getStringByDI("TA_ZT_JHJD","TID",0);
		rd.add("groupId", groupID);
		String STATE = rd.getString("state");
		String STATEPLAN = "Y";
		try {
			coreDAO.beginTrasct(conn);
			//如果 状态 为 Edit   修改 先删除再添加
			if("Edit".equals(STATE)){
				BizData del1 = new BizData();
				del1.add("TA_ZT_JHJDJG", "TID", groupID);
				coreDAO.delete(del1, conn);//删除景点计划价格
				del1.remove("TA_ZT_JHJDJG");
				del1.add("TA_ZT_JHJD", "TID", groupID);
				coreDAO.delete(del1, conn);//删除景点计划
				del1.remove("TA_ZT_JHJD");
			}
			
			String[] rows = rd.getRowIDs("TA_ZT_JHJD");
			for(int i=0;i<rows.length;i++){
				String CSID = rd.getString("TA_ZT_JHJD", "CSID", rows[i]);//计划城市
				String SFID = rd.getString("TA_ZT_JHJD", "SFID", rows[i]);//计划省份
				String ZDR = rd.getString("userno");//制定人
				String[] SceneryRows = rd.getRowIDs("JDID"+CSID);//获取景点行数
					BizData data1 = new BizData();
					BizData data2 = new BizData();
					for(int j = 0; j < SceneryRows.length; j++){
						int JHID = this.queryMaxIDByPara("TA_ZT_JHJD","ID",null);//计划ID
						String JDID = rd.getString("JDID"+CSID, "JDID", SceneryRows[j]);//景点ID
						String SFXZ = rd.getString("SFXZ"+JDID);//是否选中
						if(!"".equals(SFXZ)){
							String JGQJ = rd.getString("dwj"+JDID);//价格区间 淡旺季
							String QDXJ = rd.getString("QDXJ"+JDID);//签单小计
							if("".equals(QDXJ)){
								QDXJ = "0";
							}
							String XFXJ = rd.getString("XFXJ"+JDID);//现付小计
							if("".equals(XFXJ)){
								XFXJ = "0";
							}
							String HJ = rd.getString("HJ"+JDID);//合计
							if("".equals(HJ)){
								HJ = "0";
							}
							rd.add("TA_ZT_JHJD"+CSID, "ID", SceneryRows[j], JHID);
							rd.add("TA_ZT_JHJD"+CSID, "TID", SceneryRows[j], groupID);//团号
							rd.add("TA_ZT_JHJD"+CSID, "SFID", SceneryRows[j], SFID);
							rd.add("TA_ZT_JHJD"+CSID, "CSID", SceneryRows[j], CSID);
							rd.add("TA_ZT_JHJD"+CSID, "JDID", SceneryRows[j], JDID);
							rd.add("TA_ZT_JHJD"+CSID, "JGQJ", SceneryRows[j], JGQJ);
							rd.add("TA_ZT_JHJD"+CSID, "SFXZ", SceneryRows[j], SFXZ);
							rd.add("TA_ZT_JHJD"+CSID, "QDXJ", SceneryRows[j], QDXJ);
							rd.add("TA_ZT_JHJD"+CSID, "XFXJ", SceneryRows[j], XFXJ);
							rd.add("TA_ZT_JHJD"+CSID, "HJ", SceneryRows[j], HJ);
							rd.add("TA_ZT_JHJD"+CSID, "JHZT", SceneryRows[j], STATEPLAN);//计划状态
							rd.add("TA_ZT_JHJD"+CSID, "ZDR", SceneryRows[j], ZDR);//指定人
							data1.copyEntity(rd,"TA_ZT_JHJD"+CSID,"TA_ZT_JHJD");
							
							String[] SceneryPriceRows = rd.getRowIDs("TA_ZT_JHJDJG"+JDID);
								
								for(int k = 0; k < SceneryPriceRows.length; k++){
									int priceID = queryMaxIDByPara("TA_ZT_JHJDJG", "ID", null);//景点价格ID
									String priceNameID = rd.getString("TA_ZT_JHJDJG"+JDID, "JGMCID", SceneryPriceRows[k]);//价格名称ID
									String priceName = rd.getString("TA_ZT_JHJDJG"+JDID, "JGMC", SceneryPriceRows[k]);//价格名称
									String price = rd.getString("TA_ZT_JHJDJG"+JDID, "JG", SceneryPriceRows[k]);//价格
									String pCount = rd.getString("TA_ZT_JHJDJG"+JDID, "RS", SceneryPriceRows[k]);//人数
									rd.add("TA_ZT_JHJDJG"+JDID, "ID", SceneryPriceRows[k], priceID);
									rd.add("TA_ZT_JHJDJG"+JDID, "JHID", SceneryPriceRows[k], JHID);
									rd.add("TA_ZT_JHJDJG"+JDID, "TID", SceneryPriceRows[k], groupID);
									rd.add("TA_ZT_JHJDJG"+JDID, "JGMCID", SceneryPriceRows[k], priceNameID);
									rd.add("TA_ZT_JHJDJG"+JDID, "JGMC", SceneryPriceRows[k], priceName);
									rd.add("TA_ZT_JHJDJG"+JDID, "JG", SceneryPriceRows[k], price);
									rd.add("TA_ZT_JHJDJG"+JDID, "RS", SceneryPriceRows[k], pCount);
									data2.copyEntity(rd,"TA_ZT_JHJDJG"+JDID,"TA_ZT_JHJDJG");
								}
								//插景点价格表
								coreDAO.insert("TA_ZT_JHJDJG", data2, conn);
								data2.remove("TA_ZT_JHJDJG");
							}
					}
					//插入景点计划表
					coreDAO.insert("TA_ZT_JHJD", data1, conn);
					data1.remove("TA_ZT_JHJD");
					
			}
			updateTDJDXXZJB(rd, conn, groupID);
			BizData dtt = new BizData();
			dtt.addAttr("TA_ZT_GROUP", "ID", 0, "oldValue", groupID);
			dtt.add("TA_ZT_GROUP", "STATE", "2");//修改团状态为 2  实施中
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



	/**更新团表中的费用总计
	 * @param rd
	 * @param conn
	 * @param groupID
	 * @throws SQLException
	 */
	public void updateTDJDXXZJB(BizData rd, Connection conn, String groupID)
			throws SQLException {
		BizData data = new BizData();
		data.add("TA_TDJDXXZJB_ZT", "TID", groupID);
		coreDAO.select(data);
		int TDJDrows = data.getTableRowsCount("TA_TDJDXXZJB_ZTs");
		data.remove("TA_TDJDXXZJB_ZT");
		data.remove("TA_TDJDXXZJB_ZTs");
		if(TDJDrows > 0){
			rd.addAttr("TA_TDJDXXZJB_ZT", "TID", 0, "oldValue", groupID);
			coreDAO.update("TA_TDJDXXZJB_ZT", rd, conn);
		}else{
			rd.add("TA_TDJDXXZJB_ZT", "TID", groupID);
			coreDAO.insert("TA_TDJDXXZJB_ZT",rd, conn);
		}
	}
}
