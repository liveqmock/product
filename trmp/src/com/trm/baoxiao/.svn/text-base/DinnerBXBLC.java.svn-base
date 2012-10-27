package com.trm.baoxiao;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class DinnerBXBLC extends DBBLC{
	public DinnerBXBLC(){
		this.entityName="";
	}
	public int bx_getBXDinner(BizData rd,BizData sd){
		String id=rd.getString("ID");
		String sql="";
		try {
			 sql="select a.id,a.groupid,a.privince_id,a.city_id,a.dining_room_id, \n"+
			"a.bf_price,a.bf_count,a.bf_persons,a.dinner_price,a.dinner_fact_price, \n"+
			"a.dinner_count,a.dinner_persons,a.xfje,a.qdje,a.xfje+a.qdje drgj,c.dmsm1 breakfast_level,d.dmsm1 dinner_level \n"+
			" from ta_bx_dinner a,ta_dining_room b,dmsm c,dmsm d \n"+
			"where a.dining_room_id=b.dining_room_id and c.dmlb=9 and b.breakfast_level=c.dmz \n"+
			"and d.dmlb=9 and b.dinner_level=d.dmz and a.groupid="+id;
			coreDAO.executeQuery(sql, "p_dr_list", rd);
			if(rd.getTableRowsCount("p_dr_list")==0){
				 sql="select a.id,a.groupid,a.privince_id,a.city_id,a.dining_room_id, \n"+
					"a.bf_price,a.bf_count,a.bf_persons,a.dinner_price,a.dinner_fact_price, \n"+
					"a.dinner_count,a.dinner_persons,a.xfje,a.qdje,a.xfje+a.qdje drgj,c.dmsm1 breakfast_level,d.dmsm1 dinner_level \n"+
					" from ta_g_restaurant_do a,ta_dining_room b,dmsm c,dmsm d \n"+
					"where a.dining_room_id=b.dining_room_id and c.dmlb=9 and b.breakfast_level=c.dmz \n"+
					"and d.dmlb=9 and b.dinner_level=d.dmz and a.groupid="+id;
				 coreDAO.executeQuery(sql, "p_dr_list", rd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	public int bx_edit_dinner(BizData rd,BizData sd){ 
		Connection conn=coreDAO.getConnection();
		BizData rd2=new BizData();
		try{
			coreDAO.beginTrasct(conn);
			if("up".equals(rd.getString("state"))){
				rd2.copyEntity(rd, "p_dinner", "TA_BX_DINNER");
				coreDAO.deleteEntity("TA_BX_DINNER", rd2, conn);
				rd2.remove("TA_BX_DINNER");
			}
			String[] drRows=rd.getRowIDs("TA_BX_DINNER");
			for(int d=0;d<drRows.length;d++){
				int p_d_id=this.queryMaxIDByPara("TA_BX_DINNER", "ID", null);
				String dr_id=rd.getString("TA_BX_DINNER", "DINING_ROOM_ID", drRows[d]);
				rd.add("scprice"+dr_id, "ID",p_d_id);
				rd.add("scprice"+dr_id, "GROUPID",rd.getStringByDI("TA_GROUP_BASE", "ID", 0));
				rd.add("scprice"+dr_id, "USER_NO",rd.getString("userno"));
				rd.add("scprice"+dr_id, "PRIVINCE_ID",rd.getString("TA_BX_DINNER", "PRIVINCE_ID", drRows[d]));
				rd.add("scprice"+dr_id, "CITY_ID",rd.getString("TA_BX_DINNER", "CITY_ID", drRows[d]));
				rd.add("scprice"+dr_id, "DINING_ROOM_ID",dr_id);
				rd2.copyEntity(rd,"scprice"+dr_id,"TA_BX_DINNER");
				coreDAO.insert("TA_BX_DINNER",rd2, conn);
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
