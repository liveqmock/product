package com.trm.plan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class HotelPlanBLC extends DBBLC{
	public HotelPlanBLC(){
		this.entityName="";
	}
	public int getHotelInfo(BizData rd,BizData sd)throws SQLException{
		String id=rd.getString("id");
		//酒店基本信息
		String sql1="select a.hotel_id,a.hotel_name,a.hotel_address,a.hotel_tel, a.hotel_bussiness, \n"+
			"a.hotel_bussiness_phone,c.dmsm1 hotel_dinner_type,b.dmsm1 hotel_level \n"+
			"from ta_hotel a,dmsm b,dmsm c where b.dmlb=6 and a.hotel_level=b.dmz \n"+
			"and c.dmlb=7 and  a.hotel_dinner_type=c.dmz and a.hotel_id="+id;
		coreDAO.executeQuery(sql1, "hotelInfo", rd);
		//酒店价格
		String sql2="select b.dmz,b.dmsm1 price_name,a.hprice from ta_hotelprice a,dmsm b \n"+
				"where a.pricename=b.dmz and b.dmlb=10 and a.hotelid="+id;
		coreDAO.executeQuery(sql2, "hotelPrice", rd);
		return 1;
	}
	public int getPlanHotel(BizData rd,BizData sd)throws SQLException{
		String id=rd.getString("id");
		//团基础信息
		String sql="select x.id,x.g_id,x.line_id,x.line_name,x.begin_date,x.end_date,x.leader_name, \n"+
		"x.leader_tel,x.count_hotel_qd,x.count_hotel_xf,x.count_hotel_qd+x.count_hotel_xf count_hotel_zj,x.comments,x.children_count,x.adult_count,x.zdrs,x.kctrs,nvl(y.ysrs,0)ysrs,zdrs-nvl(y.ysrs,0) syrs from \n"+
		"(select a.id,a.g_id,a.line_id,b.line_name,a.begin_date,a.end_date,a.leader_name, \n"+
		" a.leader_tel,a.children_count,a.count_hotel_qd,a.count_hotel_xf,a.adult_count,a.comments,b.maxpersoncount zdrs,b.minpersoncount kctrs \n"+
		"from ta_group_base a,ta_line_mng b where a.line_id=b.line_id and a.id="+id+") x,\n"+
		"(select b.g_id,nvl(sum(b.ysrs),0) ysrs \n" +
		"from ta_group_base a,ta_groupmoney b where \n" +
		"a.id=b.g_id and b.dd_confirm=1 \n" +
		"group by b.g_id) y where x.id=y.g_id(+)";
		coreDAO.executeQuery(sql, "groupInfo", rd);
		//团实施餐厅信息
		String sql2="select a.id,a.groupid,a.privince_id,a.city_id,a.area_id,a.hotel_level level_id, \n"+
			"a.hotel_id,a.in_date,a.pay_qd,a.pay_xf,a.pay_qd+a.pay_xf gj,a.driver_native_mny,a.user_no,a.comments,b.hotel_name, \n"+
			"b.hotel_address,b.hotel_level,b.hotel_tel,b.hotel_bussiness_phone,b.hotel_bussiness, \n"+
			"b.hotel_dinner_type,c.dmsm1 hotel_level,d.dmsm1 zclx \n"+
			" from ta_g_hotel_do a,ta_hotel b,dmsm c,dmsm d where c.dmlb=6 and b.hotel_level=c.dmz and \n"+
			"d.dmlb=7 and b.hotel_dinner_type=d.dmz and \n"+
			"a.hotel_id=b.hotel_id and a.groupid="+id;
		coreDAO.executeQuery(sql2,"p_hotelList",rd);
		//价格
		String sql3="select a.g_hotel_id,a.price_id,a.price_typeid,b.dmsm1 pricename,"+
			"a.price,a.rooms from ta_g_hotel_do_price a ,dmsm b where "+
			"a.price_typeid=b.dmz and b.dmlb=10 ";
		coreDAO.executeQuery(sql3, "priceList",rd);
		return 1;
	}
	public int p_editHotel(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		String[] hotelRows=rd.getRowIDs("TA_G_HOTEL_DO");
		try{
			coreDAO.beginTrasct(conn);
			/*如果state=up ==修改  先删除 再插入*/
			if("up".equals(rd.getString("state"))){
				coreDAO.deleteEntity("TA_G_HOTEL_DO_PRICE", rd, conn);
				String sql2="delete from TA_G_HOTEL_DO a where a.groupid="+rd.getString("group_fkId");
				coreDAO.executeUpdate(sql2,conn);
				rd.remove("TA_G_HOTEL_DO_PRICE");
			}
			/*插入酒店实施表数据*/
			for(int h=0;h<hotelRows.length;h++){
				int id=this.queryMaxIDByPara("TA_G_HOTEL_DO", "ID", null);
				/*插入酒店实施价格,房间数*/
				String hotel_id=rd.getString("TA_G_HOTEL_DO", "HOTEL_ID", hotelRows[h]);
				String[] priRows=rd.getRowIDs("hotel"+hotel_id);
				for(int p=0;p<priRows.length;p++){
					int p_priceId=this.queryMaxIDByPara("TA_G_HOTEL_DO_PRICE", "PRICE_ID", null);
					rd.add("TA_G_HOTEL_DO_PRICE", "PRICE_ID",p_priceId);//主键ID
					rd.add("TA_G_HOTEL_DO_PRICE", "G_HOTEL_ID",id);//实施表ID
					rd.add("TA_G_HOTEL_DO_PRICE", "PRICE_TYPEID",rd.getString("hotel"+hotel_id, "priceName", priRows[p]));//价格名称,代码值
					rd.add("TA_G_HOTEL_DO_PRICE", "PRICE",rd.getString("hotel"+hotel_id, "price", priRows[p]));//价格
					rd.add("TA_G_HOTEL_DO_PRICE", "ROOMS",rd.getString("hotel"+hotel_id, "roomNum", priRows[p]));//房间数
					coreDAO.insert("TA_G_HOTEL_DO_PRICE",rd,conn);
				}
				String level=rd.getString("TA_G_HOTEL_DO","HOTEL_LEVEL",hotelRows[h]); //截出酒店星级--页面 地区-星级
				level=level.substring(level.length()-1);
				rd.add("TA_G_HOTEL_DO", "HOTEL_LEVEL",h,level);
				rd.add("TA_G_HOTEL_DO", "user_no",h,rd.getString("userno"));
				rd.add("TA_G_HOTEL_DO", "GROUPID",h,rd.getString("group_fkId"));
				rd.add("TA_G_HOTEL_DO", "ID",h,id);
			}
			
			coreDAO.insert("TA_G_HOTEL_DO",rd,conn);
			String sql4="update ta_group_base a set a.COUNT_HOTEL_QD='"+rd.getString("qdzj")+"'," +
					"a.COUNT_HOTEL_XF='"+rd.getString("xfzj")+"' where a.ID="+rd.getString("group_fkId");
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
	public int getGBase4Attemper(BizData rd,BizData sd){
		String id=rd.getString("id");
		String sql="select x.id,x.g_id,x.line_id,x.line_name,x.begin_date,x.end_date,x.leader_name, \n"+
		"x.leader_tel,x.count_dinnerroom_xf,x.count_dinnerroom_qd,x.count_dinnerroom_xf+x.count_dinnerroom_qd drzj, \n" +
		"x.count_veh_qd,x.count_veh_xf,x.count_veh_qd+x.count_veh_xf vehzj,x.COUNT_SCENERY_XF,x.COUNT_SCENERY_qd,x.COUNT_SCENERY_XF+x.COUNT_SCENERY_qd scenery_zj,\n"+
		"x.comments,x.zdrs,x.kctrs,nvl(y.ysrs,0)ysrs,zdrs-nvl(y.ysrs,0) syrs from \n"+
		"(select a.id,a.g_id,a.line_id,b.line_name,a.begin_date,a.end_date,a.leader_name, \n"+
		" a.leader_tel,nvl(a.count_dinnerroom_xf,0) count_dinnerroom_xf,nvl(a.count_dinnerroom_qd,0)count_dinnerroom_qd,\n" +
		"nvl(a.count_veh_qd,0) count_veh_qd,nvl(a.count_veh_xf,0) count_veh_xf,nvl(a.COUNT_SCENERY_XF,0) COUNT_SCENERY_XF,nvl(a.COUNT_SCENERY_qd,0)COUNT_SCENERY_qd, \n" +
		"a.comments,b.maxpersoncount zdrs,b.minpersoncount kctrs \n"+
		"from ta_group_base a,ta_line_mng b where a.line_id=b.line_id and a.id="+id+") x,\n"+
		"(select b.g_id,nvl(sum(b.ysrs),0) ysrs \n" +
		"from ta_group_base a,ta_groupmoney b where \n" +
		"a.id=b.g_id and b.dd_confirm=1 \n" +
		"group by b.g_id) y where x.id=y.g_id(+)";
		try {
			coreDAO.executeQuery(sql, "groupInfo", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
}
