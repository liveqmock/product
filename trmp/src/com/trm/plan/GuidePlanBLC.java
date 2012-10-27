package com.trm.plan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class GuidePlanBLC extends DBBLC{
	public GuidePlanBLC(){
		this.entityName="";
	}
	public int p_getGuidePlan(BizData rd,BizData sd){
		String id=rd.getString("id");
		String sql="select u.userno empid,u.username empname,o.orgid\n" +
				"from DRMUSERROLE r,drmuser u,hremployee e,hrorganization o\n" +
				"where r.userid=u.userid and u.Empid=e.empid and e.orgid=o.orgid\n" +
				"and r.roleid='guide'\n" +
				"and u.usertype='E'";
			
		String sql2="select a.dmz,a.dmsm1 from dmsm a where a.dmlb=17";
		String sql3="select a.id,a.groupid,a.guide_id,a.dylk,a.dykk from ta_g_guide_do a where a.groupid="+id;
		String sql4="select b.id,b.ta_g_guide_id,b.cost_name_id,c.dmsm1,b.costs "+
		"from ta_g_guide_do a,ta_g_guide_other b,dmsm c "+
		"where b.cost_name_id=c.dmz and c.dmlb=17 and a.id=b.ta_g_guide_id and a.groupid="+id;
		try {
			coreDAO.executeQuery(sql, "guideList", rd);
			coreDAO.executeQuery(sql2, "guide_mon", rd);
			coreDAO.executeQuery(sql3, "p_guideList",rd);
			coreDAO.executeQuery(sql4, "p_monList",rd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	public int p_editGuide(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		BizData rd2=new BizData();
		try{
			coreDAO.beginTrasct(conn);
			if("up".equals(rd.getString("state"))){
				coreDAO.deleteEntity("TA_G_GUIDE_OTHER",rd,conn);
				String[] upRows=rd.getRowIDs("TA_G_GUIDE_OTHER");
				for(int d=0;d<upRows.length;d++){
					String id=rd.getString("TA_G_GUIDE_OTHER","TA_G_GUIDE_ID",upRows[d]);
					String sql="delete TA_G_GUIDE_DO a where a.id="+id;
					coreDAO.executeUpdate(sql, conn);
				}
			}
			String []guideRows=rd.getRowIDs("TA_G_GUIDE_DO");
			for(int g=0;g<guideRows.length;g++){
				int id=this.queryMaxIDByPara("TA_G_GUIDE_DO", "ID", null);
				//导游费用TA_G_GUIDE_OTHER
				String []monRows=rd.getRowIDs("TA_G_GUIDE_OTHER"+guideRows[g]);
				for(int m=0;m<monRows.length;m++){
					int m_id=this.queryMaxIDByPara("TA_G_GUIDE_OTHER", "ID", null);
					rd.add("TA_G_GUIDE_OTHER"+guideRows[g], "ID",monRows[m],m_id);
					rd.add("TA_G_GUIDE_OTHER"+guideRows[g], "TA_G_GUIDE_ID",monRows[m],id);
				}
				rd2.copyEntity(rd, "TA_G_GUIDE_OTHER"+guideRows[g],"TA_G_GUIDE_OTHER");
				coreDAO.insert("TA_G_GUIDE_OTHER",rd2,conn);
				rd2.remove("TA_G_GUIDE_OTHER");
				
				rd.add("TA_G_GUIDE_DO","ID",guideRows[g],id);
				rd.add("TA_G_GUIDE_DO","GROUPID",guideRows[g],rd.getString("TA_GROUP_BASE", "ID",0));
				rd.add("TA_G_GUIDE_DO", "user_no",guideRows[g],rd.getString("userno"));
			}
			coreDAO.insert("TA_G_GUIDE_DO",rd,conn);
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
