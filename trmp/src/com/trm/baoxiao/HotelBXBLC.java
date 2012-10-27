package com.trm.baoxiao;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class HotelBXBLC extends DBBLC{
	public HotelBXBLC(){
		this.entityName="";
	}
	public int getBXHotel(BizData rd,BizData sd){
		String id=rd.getString("id");
		String sql="";
		String sql2="";
		 sql="select a.id,a.groupid,a.privince_id,a.city_id,a.area_id,a.hotel_level level_id, \n"+
		"a.hotel_id,a.in_date,a.pay_qd,a.pay_xf,a.pay_qd+a.pay_xf gj,a.driver_native_mny,a.user_no,a.comments,b.hotel_name, \n"+
		"b.hotel_address,b.hotel_level,b.hotel_tel,b.hotel_bussiness_phone,b.hotel_bussiness, \n"+
		"b.hotel_dinner_type,c.dmsm1 hotel_level,d.dmsm1 zclx \n"+
		" from ta_bx_hotel a,ta_hotel b,dmsm c,dmsm d where c.dmlb=6 and b.hotel_level=c.dmz and \n"+
		"d.dmlb=7 and b.hotel_dinner_type=d.dmz and \n"+
		"a.hotel_id=b.hotel_id and a.groupid="+id;
		 sql2="select a.g_hotel_id,a.price_id,a.price_typeid,b.dmsm1 pricename,"+
		"a.price,a.rooms from ta_bx_hotel_price a ,dmsm b where "+
		"a.price_typeid=b.dmz and b.dmlb=10 ";
		try {
			coreDAO.executeQuery(sql,"p_hotelList",rd);
			coreDAO.executeQuery(sql2, "priceList",rd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(rd.getTableRowsCount("p_hotelList")==0){
		//团实施餐厅信息
		 sql="select a.id,a.groupid,a.privince_id,a.city_id,a.area_id,a.hotel_level level_id, \n"+
			"a.hotel_id,a.in_date,a.pay_qd,a.pay_xf,a.pay_qd+a.pay_xf gj,a.driver_native_mny,a.user_no,a.comments,b.hotel_name, \n"+
			"b.hotel_address,b.hotel_level,b.hotel_tel,b.hotel_bussiness_phone,b.hotel_bussiness, \n"+
			"b.hotel_dinner_type,c.dmsm1 hotel_level,d.dmsm1 zclx \n"+
			" from ta_g_hotel_do a,ta_hotel b,dmsm c,dmsm d where c.dmlb=6 and b.hotel_level=c.dmz and \n"+
			"d.dmlb=7 and b.hotel_dinner_type=d.dmz and \n"+
			"a.hotel_id=b.hotel_id and a.groupid="+id;
		//价格
		 sql2="select a.g_hotel_id,a.price_id,a.price_typeid,b.dmsm1 pricename,"+
			"a.price,a.rooms from ta_g_hotel_do_price a ,dmsm b where "+
			"a.price_typeid=b.dmz and b.dmlb=10 ";
		try {
			coreDAO.executeQuery(sql,"p_hotelList",rd);
			coreDAO.executeQuery(sql2, "priceList",rd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return 1;
	}
	public int bx_editHotel(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		String[] hotelRows=rd.getRowIDs("TA_BX_HOTEL");
		try{
			coreDAO.beginTrasct(conn);
			/*如果state=up ==修改  先删除 再插入*/
			if("up".equals(rd.getString("state"))){
				coreDAO.deleteEntity("TA_BX_HOTEL_PRICE", rd, conn);
				String sql2="delete from TA_BX_HOTEL a where a.groupid="+rd.getString("groupid");
				coreDAO.executeUpdate(sql2,conn);
				rd.remove("TA_BX_HOTEL_PRICE");
			}
			/*插入酒店实施表数据*/
			for(int h=0;h<hotelRows.length;h++){
				int id=this.queryMaxIDByPara("TA_BX_HOTEL", "ID", null);
				/*插入酒店实施价格,房间数*/
				String hotel_id=rd.getString("TA_BX_HOTEL", "HOTEL_ID", hotelRows[h]);
				String[] priRows=rd.getRowIDs("hotel"+hotel_id);
				for(int p=0;p<priRows.length;p++){
					int p_priceId=this.queryMaxIDByPara("TA_BX_HOTEL_PRICE", "PRICE_ID", null);
					rd.add("TA_BX_HOTEL_PRICE", "PRICE_ID",p_priceId);//主键ID
					rd.add("TA_BX_HOTEL_PRICE", "G_HOTEL_ID",id);//实施表ID
					rd.add("TA_BX_HOTEL_PRICE", "PRICE_TYPEID",rd.getString("hotel"+hotel_id, "priceName", priRows[p]));//价格名称,代码值
					rd.add("TA_BX_HOTEL_PRICE", "PRICE",rd.getString("hotel"+hotel_id, "price", priRows[p]));//价格
					rd.add("TA_BX_HOTEL_PRICE", "ROOMS",rd.getString("hotel"+hotel_id, "roomNum", priRows[p]));//房间数
					coreDAO.insert("TA_BX_HOTEL_PRICE",rd,conn);
				}
				String level=rd.getString("TA_BX_HOTEL","HOTEL_LEVEL",hotelRows[h]); //截出酒店星级--页面 地区-星级
				level=level.substring(level.length()-1);
				rd.add("TA_BX_HOTEL", "HOTEL_LEVEL",h,level);
				rd.add("TA_BX_HOTEL", "user_no",h,rd.getString("userno"));
				rd.add("TA_BX_HOTEL", "GROUPID",h,rd.getString("groupid"));
				rd.add("TA_BX_HOTEL", "ID",h,id);
			}
			
			coreDAO.insert("TA_BX_HOTEL",rd,conn);
			String sql4="update ta_group_base a set a.BX_HOTEL_QD='"+rd.getString("qdzj")+"'," +
					"a.BX_HOTEL_XF='"+rd.getString("xfzj")+"' where a.ID="+rd.getString("groupid");
			coreDAO.executeUpdate(sql4, conn);
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return 1;
	}
}
