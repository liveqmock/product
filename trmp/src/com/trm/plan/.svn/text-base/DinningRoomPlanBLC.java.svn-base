package com.trm.plan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class DinningRoomPlanBLC extends DBBLC{
	public DinningRoomPlanBLC(){
		
	}
	public int getDrInfo(BizData rd,BizData sd){
		String id=rd.getString("id");
		String sql="select a.dining_room_id,a.city_id,a.cmpny_name,a.cmpny_address,"+
			"a.business_name,a.business_phone,a.business_mobile,a.dining_room_type,"+
			"b.dmsm1 dinner_level,nvl(a.dinner_price,0) dinner_price,c.dmsm1 breakfast_level,nvl(a.breakfast_price,0) breakfast_price"+
			" from ta_dining_room a,dmsm b,dmsm c  where b.dmlb=9 "+
			"and a.dinner_level=b.dmz and c.dmlb=9 and a.breakfast_level=c.dmz and a.dining_room_id="+id;
		try {
			coreDAO.executeQuery(sql, "drInfo", rd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	//计调信息
	public int getDrPlanInfo(BizData rd,BizData sd){
		String id=rd.getString("ID");
		String sql="select a.id,a.groupid,a.privince_id,a.city_id,a.dining_room_id, \n"+
			"a.bf_price,a.bf_count,a.bf_persons,a.dinner_price,a.dinner_fact_price, \n"+
			"a.dinner_count,a.dinner_persons,a.xfje,a.qdje,a.xfje+a.qdje drgj,c.dmsm1 breakfast_level,d.dmsm1 dinner_level \n"+
			" from ta_g_restaurant_do a,ta_dining_room b,dmsm c,dmsm d \n"+
			"where a.dining_room_id=b.dining_room_id and c.dmlb=9 and b.breakfast_level=c.dmz \n"+
			"and d.dmlb=9 and b.dinner_level=d.dmz and a.groupid="+id;
		try{
			coreDAO.executeQuery(sql, "p_dr_list", rd);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 1;
	}
	public int p_edit_dinner(BizData rd,BizData sd){ 
		Connection conn=coreDAO.getConnection();
		BizData rd2=new BizData();
		try{
			coreDAO.beginTrasct(conn);
			if("up".equals(rd.getString("state"))){
				rd2.copyEntity(rd, "p_dinner", "TA_G_RESTAURANT_DO");
				coreDAO.deleteEntity("TA_G_RESTAURANT_DO", rd2, conn);
				rd2.remove("TA_G_RESTAURANT_DO");
			}
			String[] drRows=rd.getRowIDs("TA_G_RESTAURANT_DO");
			for(int d=0;d<drRows.length;d++){
				int p_d_id=this.queryMaxIDByPara("TA_G_RESTAURANT_DO", "ID", null);
				String dr_id=rd.getString("TA_G_RESTAURANT_DO", "DINING_ROOM_ID", drRows[d]);
				rd.add("scprice"+dr_id, "ID",p_d_id);
				rd.add("scprice"+dr_id, "GROUPID",rd.getStringByDI("TA_GROUP_BASE", "ID", 0));
				rd.add("scprice"+dr_id, "USER_NO",rd.getString("userno"));
				rd.add("scprice"+dr_id, "PRIVINCE_ID",rd.getString("TA_G_RESTAURANT_DO", "PRIVINCE_ID", drRows[d]));
				rd.add("scprice"+dr_id, "CITY_ID",rd.getString("TA_G_RESTAURANT_DO", "CITY_ID", drRows[d]));
				rd.add("scprice"+dr_id, "DINING_ROOM_ID",dr_id);
				rd2.copyEntity(rd,"scprice"+dr_id,"TA_G_RESTAURANT_DO");
				coreDAO.insert("TA_G_RESTAURANT_DO",rd2, conn);
			}
			coreDAO.update("TA_GROUP_BASE",rd,conn);
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
