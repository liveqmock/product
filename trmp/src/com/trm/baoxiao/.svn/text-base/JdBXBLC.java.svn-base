package com.trm.baoxiao;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class JdBXBLC extends DBBLC{
	public JdBXBLC(){
		this.entityName="";
	}
	public int bx_getJdInfo(BizData rd,BizData sd){
		String id=rd.getString("id");
		String sql="select hotel.hotel_cb,ticket.ticket_cb,dinner.dinner_cb,veh.veh_cb,scenery.scenery_cb, "+
			"hotel.hotel_cb+ticket.ticket_cb+dinner.dinner_cb+veh.veh_cb+scenery.scenery_cb zcb,lk.dylk,sk.ysk,gp.* from "+
			"(select nvl(sum(a.pay_qd+a.pay_xf),0) hotel_cb from ta_g_hotel_do a where a.groupid="+id+") hotel, "+
			"(select nvl(sum(b.xfje+b.qdje),0)ticket_cb from ta_g_ticket_do b where b.groupid="+id+") ticket, "+
			"(select nvl(sum(c.qdje+c.xfje),0)dinner_cb  from ta_g_restaurant_do c where c.groupid="+id+")dinner, "+
			"(select nvl(sum(d.qdje+d.xfje),0)veh_cb from ta_g_vehicle_do d where d.groupid="+id+")veh, "+
			"(select nvl(sum(y.xfje+y.qdje),0)scenery_cb from ta_g_scenryxzqh x,ta_g_scenery y where x.city_id=y.areaid and x.groupid="+id+")scenery, "+
			"(select nvl(sum(dylk),0) dylk from TA_G_GUIDE_DO a where a.groupid="+id+")lk, "+
			"(select nvl(sum(yi_sk),0)ysk from TA_GROUPMONEY a where a.g_id="+id+")sk,"+
			"(select nvl(a.OUGHT_MONEY_COUNT,0)OUGHT_MONEY_COUNT,nvl(a.sign_money_count,0)sign_money_count,"+
			"nvl(a.group_margin,0)group_margin,nvl(a.net_profit,0)net_profit from ta_group_base a where a.id="+id+") gp";
		String sql2="select a.dmz,a.dmsm1,a.dmsm2 from dmsm a where a.dmlb=18 order by dmz";
		String sql3="select a.*,a.xfe/a.rs gpj from ta_bx_jd a where a.groupid="+id;
		String sql4="select b.id,b.cyid,c.dmsm1,c.dmz,b.tcbl,b. tcje,b.jdid "+
				"from ta_bx_jd a ,ta_g_jdtc b,dmsm c"+
				" where a.id=b.jdid and b.cyid=c.dmz and c.dmlb=18 and a.groupid="+id+" order by dmz";
		try {
			coreDAO.executeQuery(sql, "groupMon", rd);
			coreDAO.executeQuery(sql2, "tcList", rd);
			coreDAO.executeQuery(sql3, "jdList", rd);
			coreDAO.executeQuery(sql4, "tcInfo", rd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	public int bx_editJd(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		String groupid=rd.getString("groupid");
		try{
			coreDAO.beginTrasct(conn);
			if("up".equals(rd.getString("state"))){
				coreDAO.deleteEntity("TA_G_JDTC", rd, conn);
				rd.remove("TA_G_JDTC");
				String sql="delete from TA_BX_JD a where a.GROUPID="+groupid;
				coreDAO.executeUpdate(sql, conn);
			}
			String []rows=rd.getRowIDs("TA_BX_JD");
			if(null!=rows){
				for(int a=0;a<rows.length;a++){
					int id=this.queryMaxIDByPara("TA_BX_JD", "ID", null);
					String tcRows[]=rd.getRowIDs("scenery"+a);
					if(null!=tcRows){
						BizData rd2=new BizData();
						for(int t=0;t<tcRows.length;t++){
							int tcid=this.queryMaxIDByPara("TA_G_JDTC", "ID", null);
							rd.add("scenery"+a, "JDID",tcRows[t],id);
							rd.add("scenery"+a, "ID",tcRows[t],tcid);
						}
						rd2.copyEntity(rd, "scenery"+a, "TA_G_JDTC");
						coreDAO.insert("TA_G_JDTC", rd2, conn);
						rd2.remove("TA_G_JDTC");
					}
					rd.add("TA_BX_JD", "ID",rows[a],id);
					rd.add("TA_BX_JD", "GROUPID",rows[a],groupid);
				}
				coreDAO.insert("TA_BX_JD",rd,conn);
			}
			coreDAO.update("TA_GROUP_BASE", rd, conn);
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
