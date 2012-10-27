package com.trm.baoxiao;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class CarBXBLC extends DBBLC{
	public  CarBXBLC(){
		this.entityName="";
	}
	public int bx_getBXCar(BizData rd,BizData sd){
		String id=rd.getString("id");
		String sql="";
		 sql="select a.id,a.groupid,a.privince_id,a.city_id,a.veh_team_id,"+
			"a.veh_type,a.vehicle_id,a.b_date,a.e_date,a.price,a.qdje,a.xfje"+
			" from  ta_bx_vehicle a where a.groupid="+id;
		try {
			coreDAO.executeQuery(sql, "p_carList", rd);
			if(rd.getTableRowsCount("p_carList")==0){
				 sql="select a.id,a.groupid,a.privince_id,a.city_id,a.veh_team_id,"+
				"a.veh_type,a.vehicle_id,a.b_date,a.e_date,a.price,a.qdje,a.xfje"+
				" from  ta_g_vehicle_do a where a.groupid="+id;
				coreDAO.executeQuery(sql, "p_carList", rd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	public int bx_editCar(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		try{
			coreDAO.beginTrasct(conn);
			if("up".equals(rd.getString("state"))){
					BizData rd2=new BizData();
					rd2.copyEntity(rd,"plan_veh","TA_BX_VEHICLE");
					coreDAO.deleteEntity("TA_BX_VEHICLE",rd2,conn);
			}
			String []rows=rd.getRowIDs("TA_BX_VEHICLE");
			for(int c=0;c<rows.length;c++){
				int id=this.queryMaxIDByPara("TA_BX_VEHICLE", "ID", null);
				rd.add("TA_BX_VEHICLE", "ID",rows[c],id);
				String car_type=rd.getString("TA_BX_VEHICLE", "VEH_TYPE",rows[c]);
				rd.add("TA_BX_VEHICLE", "VEH_TYPE",rows[c],car_type.substring(car_type.length()-1));
				rd.add("TA_BX_VEHICLE", "USER_NO",rows[c],rd.getString("userno"));
				rd.add("TA_BX_VEHICLE", "GROUPID",rows[c],rd.getStringByDI("TA_GROUP_BASE","ID",0));
			}
			coreDAO.insert("TA_BX_VEHICLE",rd,conn);
			coreDAO.update("TA_GROUP_BASE",rd,conn);
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			try {
				coreDAO.rollbackTrasct(conn);
				e.printStackTrace();
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
