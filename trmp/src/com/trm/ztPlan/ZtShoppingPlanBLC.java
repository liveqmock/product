package com.trm.ztPlan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


/**
 * ClassName:DjShoppingPlanBLC
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Administrator
 * @version  
 * @since    Ver 1.1
 * @Date	 2011	2011-8-4		上午09:30:57
 *
 * @see 	 
 * @deprecated 
 */
public class ZtShoppingPlanBLC extends DBBLC{
	public ZtShoppingPlanBLC(){
		this.entityName="TA_ZT_JHGW";
	}
	/**  
	 * getAllShopAndRTF(获取该团的购物点信息 以及 根据客源地获取人头费信息)<br/>  
	 * @param rd
	 * @param sd
	 * @return   
	 * @return_type (int)
	 * @exception   
	 * @since  1.0.0  
	*/
	public int getAllShopAndRTF(BizData rd,BizData sd){

		String city_id=rd.getString("city");
		String ywlb = rd.getString("ywlb");
		String sqlAddGWD="select distinct a.shop_point_id,a.cmpny_name,a.city_id from ta_shoppoint a where a.city_id='"+city_id+"' and a.ywlb='"+ywlb+"' order by a.shop_point_id";
		String sqlAddRTF="select h.id, h.name,h.Pre_Pee from xzqh h where h.pid=0 and id=3";
		try {
			coreDAO.executeQuery(sqlAddGWD, "shopListAddGWD", rd);//AJAX加载 查询购物点信息
			coreDAO.executeQuery(sqlAddRTF, "shopListAddRTF", rd);//AJAX加载 查询该团客源地人头费
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**  
	 * djGetShopInfo(初始化修改购物计划信息时 加载该团的购物计划信息)<br/>  
	 * @param rd
	 * @param sd
	 * @return   
	 * @return_type (int)
	 * @exception   
	 * @since  1.0.0  
	*/
	public int ztGetShopInfo(BizData rd,BizData sd){
		String groupID=rd.getStringByDI("TA_ZT_JHGW","TID",0);//获取团号
		String sqlEditGWD="select distinct  w.SFID,w.csid,w.gwdid,w.gwdmc,w.sfxz  from TA_ZT_jhgw w where w.tid='"+groupID+"' order by w.gwdid";
		String sqlEditRTF="select distinct  f.csid,f.rtf,h.name,w.gwdid from TA_ZT_gwrtf f,TA_ZT_jhgw w,xzqh h \n"+
						  "where f.jhid=w.id \n" +
						  "and f.csid=h.id \n" +
						  "and w.tid='"+groupID+"'" +
						  "order by w.gwdid";//修改查询语句
		//String sqlEditRTF="select h.id, h.name,h.Pre_Pee from xzqh h where h.pid=0 and id=3";
		String sqlCityInfo="select distinct  w.SFID,w.csid from TA_ZT_jhgw w where w.tid='"+groupID+"' order by w.SFID";
		try {
			coreDAO.executeQuery(sqlCityInfo, "sqlCityInfo", rd);//查询该团购物点 省份ID 城市ID
			coreDAO.executeQuery(sqlEditGWD, "shopListEditGWD", rd);//修改页面加载购物点信息
			coreDAO.executeQuery(sqlEditRTF, "shopListEditRTF", rd);//修改该页面加载人头费信息
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	/**  
	 * djShopRTFGeneral(购物计划添加 修改 删除 综合操作)<br/>  
	 * @param rd
	 * @param sd
	 * @return   
	 * @return_type (int)
	 * @exception   
	 * @since  1.0.0  
	*/
	public int ztShopGeneral(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		String groupID = rd.getStringByDI("TA_ZT_JHGW","TID",0);//获取团号
		rd.add("groupId", groupID);
		String PLANSTATE = rd.getString("state");
		String JHZDR = rd.getString("JHZDR");
		String GWJHZT = "Y";
		try{
			coreDAO.beginTrasct(conn);
			if("Edit".equals(PLANSTATE)){//判断状态 Edit为修改 修改前先 删除 再 添加
				BizData data = new BizData();
				data.add("TA_ZT_GWRTF", "TID", groupID);
				coreDAO.delete(data, conn);//根据团号 删除购物人头费表
				data.remove("TA_ZT_GWRTF");
				data.add("TA_ZT_JHGW", "TID", groupID);
				coreDAO.delete(data, conn);//根据团号 删除购物计划表
				data.remove("TA_ZT_JHGW");
			}
			String[] rows = rd.getRowIDs("TA_ZT_JHGW");//获取购物计划表行数
			BizData rd2=new BizData();
			BizData rd3=new BizData();
			for(int i = 0;i < rows.length;i++){
				String CSID = rd.getString("TA_ZT_JHGW", "CSID", rows[i]);
				String SFID = rd.getString("TA_ZT_JHGW", "SFID", rows[i]);
				String[] rows_shop = rd.getRowIDs("TA_ZT_JHGW"+CSID);
					for(int j = 0;j < rows_shop.length; j++){
							String SFXZ=rd.getString("TA_ZT_JHGW"+CSID, "SFXZ", rows_shop[j]);
							if(!"".equals(SFXZ)){
								int gwjhid=this.queryMaxIDByPara("TA_ZT_JHGW", "ID", null);//获取购物计划ID
								String GWDID=rd.getString("TA_ZT_JHGW"+CSID, "GWDID", rows_shop[j]);
								String GWDMC=rd.getString("TA_ZT_JHGW"+CSID, "GWDMC", rows_shop[j]);
								rd2.add("TA_ZT_JHGW"+CSID, "ID", rows_shop[j], gwjhid);
								rd2.add("TA_ZT_JHGW"+CSID, "TID", rows_shop[j], groupID);
								rd2.add("TA_ZT_JHGW"+CSID, "SFID", rows_shop[j], SFID);
								rd2.add("TA_ZT_JHGW"+CSID, "CSID", rows_shop[j], CSID);
								rd2.add("TA_ZT_JHGW"+CSID, "GWDID", rows_shop[j], GWDID);
								rd2.add("TA_ZT_JHGW"+CSID, "GWDMC", rows_shop[j], GWDMC);
								rd2.add("TA_ZT_JHGW"+CSID, "SFXZ", rows_shop[j], SFXZ);
								rd2.add("TA_ZT_JHGW"+CSID, "JHZDR", rows_shop[j], JHZDR);
								rd2.add("TA_ZT_JHGW"+CSID, "JHZT", rows_shop[j], GWJHZT);
								
								
								int rtfid=this.queryMaxIDByPara("TA_ZT_GWRTF", "ID", null);//购物人头费ID
								String gwrtf = rd.getString("TA_ZT_GWRTF"+GWDID, "RTF", 0);//购物人头费
								String gwrtfCSID = rd.getString("TA_ZT_GWRTF"+GWDID, "CSID", 0);//购物人头费城市ID
								rd3.add("TA_ZT_GWRTF"+GWDID, "ID", 0, rtfid);//人头费ID
								rd3.add("TA_ZT_GWRTF"+GWDID, "JHID", 0, gwjhid);//计划ID
								rd3.add("TA_ZT_GWRTF"+GWDID, "TID", 0, groupID);//团ID
								rd3.add("TA_ZT_GWRTF"+GWDID, "RTF", 0, gwrtf);//RTF
								rd3.add("TA_ZT_GWRTF"+GWDID, "CSID", 0, gwrtfCSID);//城市ID
								rd3.copyEntity(rd3, "TA_ZT_GWRTF"+GWDID, "TA_ZT_GWRTF");
							}
							
					}
					rd2.copyEntity(rd2, "TA_ZT_JHGW"+CSID, "TA_ZT_JHGW");
					
				}
			rd.remove("TA_ZT_JHGW");
			coreDAO.insert("TA_ZT_GWRTF",rd3,conn);
			rd3.remove("TA_ZT_GWRTF");
			coreDAO.insert("TA_ZT_JHGW",rd2,conn);
			rd2.remove("TA_ZT_JHGW");
			
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
}