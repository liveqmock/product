package com.trmdj.businessPlan.plan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.string.PyUtil;


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
public class DjShoppingPlanBLC extends DBBLC{
	public DjShoppingPlanBLC(){
		this.entityName="TA_DJ_JHGW";
	}
	

	/** 新增购物信息
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int insertShop(BizData rd, BizData sd) throws SQLException{
		

		Connection conn=coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			String[] shopRow = rd.getRowIDs("TA_DJ_JHGW");// 获取记录数
			String groupId = rd.getString("groupId");// 获取团号
			String temp = rd.getString("temp");// 获取计划状态
			String zDr = sd.getString("userno");// 获取指定人

			// 根据团号删除加点计划
			BizData data = new BizData();
			data.add("TA_DJ_JHGW", "TID", groupId);
			data.add("TA_DJ_JHGW", "orgid", sd.getString("orgid"));
			coreDAO.delete(data, conn);// 删除历史数据
			data.remove("TA_DJ_JHGW");

			StringBuffer theNewShop = new StringBuffer().append("[");
			for (int i = 0; i < shopRow.length; i++) {
				
				int shopId = queryMaxIDByPara("TA_DJ_JHGW", "ID", null);
				int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_JHGW");
				rd.add("TA_DJ_JHGW", "ID", String.valueOf(fieldIndex[i]), shopId);
				rd.add("TA_DJ_JHGW", "TID", String.valueOf(fieldIndex[i]), groupId);
				rd.add("TA_DJ_JHGW", "JHZT", String.valueOf(fieldIndex[i]), temp);
				rd.add("TA_DJ_JHGW", "JHZDR", String.valueOf(fieldIndex[i]), zDr);
				rd.add("TA_DJ_JHGW", "orgid", String.valueOf(fieldIndex[i]), sd.getString("orgid"));

				// 购物点名称
				String gwdMc = rd.getString("TA_DJ_JHGW", "GWDMC",String.valueOf(fieldIndex[i]));
				// 购物点ID
				String gwdID = rd.getString("TA_DJ_JHGW", "GWDID",String.valueOf(fieldIndex[i]));
				// 基础表中没有此购物点信息，添加
				if ("".equals(gwdID)) {
					
					int baseShopID = queryMaxIDByPara("TA_SHOPPOINT", "SHOP_POINT_ID", null);
					data.add("TA_SHOPPOINT", "CMPNY_NAME", gwdMc);
					String nameCode = PyUtil.get1stLetterOf4Chars(gwdMc);
					data.add("TA_SHOPPOINT", "NAMECODE", nameCode);
					data.add("TA_SHOPPOINT", "SHOP_POINT_ID", baseShopID);
					data.add("TA_SHOPPOINT", "CHIEF_NAME", rd.getString("TA_DJ_JHGW", "LXR", String.valueOf(fieldIndex[i])));
					data.add("TA_SHOPPOINT", "CHIEF_MOBILE", rd.getString("TA_DJ_JHGW", "LXFS", String.valueOf(fieldIndex[i])));
					data.add("TA_SHOPPOINT", "orgid", sd.getString("orgid"));
					coreDAO.insert(data, conn);
					data.remove("TA_SHOPPOINT");
					rd.add("TA_DJ_JHGW", "GWDID", String.valueOf(fieldIndex[i]), baseShopID);
					
					//返回该新增的基础信息的数组索引值和当前的主键
					theNewShop.append("{\"indexNm\":"+String.valueOf(fieldIndex[i])+",\"id\":"+baseShopID+"},");
				}
			}
			
			if(theNewShop.lastIndexOf(",") > 0){
				
				String newShop = theNewShop.substring(0, theNewShop.length()-1);
				newShop = newShop+"]";
				rd.add("newBaseInfo", newShop);
			}
			coreDAO.insert("TA_DJ_JHGW", rd, conn);// 数据入库
			coreDAO.commitTrasct(conn);
		} catch (Exception e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 999;
	}
	/**Ajax初始化购物页面
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int djAjaxShopInfo(BizData rd, BizData sd) {
		String groupId = rd.getString("groupId");
		String sql = "select * from TA_DJ_JHGW where tid='"+groupId+"' and orgid="+sd.getString("orgid");
		try {
			coreDAO.executeQuery(sql, "TA_DJ_JHGW", rd);
			sql="select begin_date,ts from ta_dj_group where id ='"+groupId+"' and orgid='"+sd.getString("orgid")+"'";
			coreDAO.executeQuery(sql, "TA_DJ_GROUP", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int tRow = rd.getTableRowsCount("TA_DJ_JHGW");//获取查询记录行数
		int random = 0;//装随机数
		
		for(int i = 0; i < tRow; i++){
			random = (int) (Math.floor(Math.random() * 123450));
			rd.add("TA_DJ_JHGW","Random", i, random);//装入随即数
		}
		return 999;
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
		String groupID=rd.getString("TID");//获取团号
		String city_id=rd.getString("city");
		String sqlAddGWD="select distinct  a.shop_point_id,a.cmpny_name,a.city_id from ta_shoppoint a where a.city_id='"+city_id+"' order by a.shop_point_id";
		String sqlAddRTF="select distinct  h.id, h.name,h.Pre_Pee from xzqh h,ta_dj_tzts t \n"+
					"where t.kyd=h.id \n"+
					"and t.tid='"+groupID+"' order by h.id";
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
	public int djGetShopInfo(BizData rd,BizData sd){
		String groupID=rd.getStringByDI("TA_DJ_JHGW","TID",0);//获取团号
		String sqlEditGWD="select distinct  w.SFID,w.csid,w.gwdid,w.gwdmc,w.sfxz  from ta_dj_jhgw w where w.tid='"+groupID+"' order by w.gwdid";
		String sqlEditRTF="select distinct  f.csid,f.rtf,h.name,w.gwdid from ta_dj_gwrtf f,ta_dj_jhgw w,xzqh h \n"+
						  "where f.jhid=w.id \n" +
						  "and f.csid=h.id \n" +
						  "and w.tid='"+groupID+"'" +
						  "order by w.gwdid";//修改查询语句
		String sqlCityInfo="select distinct  w.SFID,w.csid from ta_dj_jhgw w where w.tid='"+groupID+"' order by w.SFID";
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
	public int djShopGeneral(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		String groupID = rd.getStringByDI("TA_DJ_JHGW","TID",0);//获取团号
		String PLANSTATE = rd.getString("state");
		String JHZDR = rd.getString("JHZDR");
		String GWJHZT = "Y";
		try{
			coreDAO.beginTrasct(conn);
			if("Edit".equals(PLANSTATE)){//判断状态 Edit为修改 修改前先 删除 再 添加
				BizData data = new BizData();
				data.add("TA_DJ_GWRTF", "TID", groupID);
				coreDAO.delete(data, conn);//根据团号 删除购物人头费表
				data.remove("TA_DJ_GWRTF");
				data.add("TA_DJ_JHGW", "TID", groupID);
				coreDAO.delete(data, conn);//根据团号 删除购物计划表
				data.remove("TA_DJ_JHGW");
			}
			String[] rows = rd.getRowIDs("TA_DJ_JHGW");//获取购物计划表行数
				BizData rd2=new BizData();
				BizData rd3=new BizData();
				for(int i = 0;i < rows.length;i++){
					String CSID = rd.getString("TA_DJ_JHGW", "CSID", rows[i]);
					String SFID = rd.getString("TA_DJ_JHGW", "SFID", rows[i]);
					String[] rows_shop = rd.getRowIDs("TA_DJ_JHGW"+CSID);
						for(int j = 0;j < rows_shop.length; j++){
								String SFXZ=rd.getString("TA_DJ_JHGW"+CSID, "SFXZ", rows_shop[j]);
								if(!"".equals(SFXZ)){
									int gwjhid=this.queryMaxIDByPara("TA_DJ_JHGW", "ID", null);//获取购物计划ID
									String GWDID=rd.getString("TA_DJ_JHGW"+CSID, "GWDID", rows_shop[j]);
									String GWDMC=rd.getString("TA_DJ_JHGW"+CSID, "GWDMC", rows_shop[j]);
									rd2.add("TA_DJ_JHGW"+CSID, "ID", rows_shop[j], gwjhid);
									rd2.add("TA_DJ_JHGW"+CSID, "TID", rows_shop[j], groupID);
									rd2.add("TA_DJ_JHGW"+CSID, "SFID", rows_shop[j], SFID);
									rd2.add("TA_DJ_JHGW"+CSID, "CSID", rows_shop[j], CSID);
									rd2.add("TA_DJ_JHGW"+CSID, "GWDID", rows_shop[j], GWDID);
									rd2.add("TA_DJ_JHGW"+CSID, "GWDMC", rows_shop[j], GWDMC);
									rd2.add("TA_DJ_JHGW"+CSID, "SFXZ", rows_shop[j], SFXZ);
									rd2.add("TA_DJ_JHGW"+CSID, "JHZDR", rows_shop[j], JHZDR);
									rd2.add("TA_DJ_JHGW"+CSID, "JHZT", rows_shop[j], GWJHZT);
									
									
									int rtfid=this.queryMaxIDByPara("TA_DJ_GWRTF", "ID", null);//购物人头费ID
									String gwrtf = rd.getString("TA_DJ_GWRTF"+GWDID, "RTF", 0);//购物人头费
									String gwrtfCSID = rd.getString("TA_DJ_GWRTF"+GWDID, "CSID", 0);//购物人头费城市ID
									rd3.add("TA_DJ_GWRTF"+GWDID, "ID", 0, rtfid);//人头费ID
									rd3.add("TA_DJ_GWRTF"+GWDID, "JHID", 0, gwjhid);//计划ID
									rd3.add("TA_DJ_GWRTF"+GWDID, "TID", 0, groupID);//团ID
									rd3.add("TA_DJ_GWRTF"+GWDID, "RTF", 0, gwrtf);//RTF
									rd3.add("TA_DJ_GWRTF"+GWDID, "CSID", 0, gwrtfCSID);//城市ID
									rd3.copyEntity(rd3, "TA_DJ_GWRTF"+GWDID, "TA_DJ_GWRTF");
								}
								
						}
						rd2.copyEntity(rd2, "TA_DJ_JHGW"+CSID, "TA_DJ_JHGW");
						
					}
				rd.remove("TA_DJ_JHGW");
				coreDAO.insert("TA_DJ_GWRTF",rd3,conn);
				rd3.remove("TA_DJ_GWRTF");
				coreDAO.insert("TA_DJ_JHGW",rd2,conn);
				rd2.remove("TA_DJ_JHGW");
				
				BizData dtt = new BizData();
				dtt.addAttr("TA_DJ_GROUP", "ID", 0, "oldValue", groupID);
				dtt.add("TA_DJ_GROUP", "STATE", "2");//修改团状态为 2  实施中
				coreDAO.update("TA_DJ_GROUP", dtt, conn);
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