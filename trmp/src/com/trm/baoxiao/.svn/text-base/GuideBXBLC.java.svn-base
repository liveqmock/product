package com.trm.baoxiao;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class GuideBXBLC extends DBBLC{
	public GuideBXBLC(){
		this.entityName="";
	}
	public int bx_getGuide(BizData rd,BizData sd){
		String id=rd.getString("id");
		String sql="";
		String sql2="";
		sql="select a.id,a.groupid,a.guide_id,b.empname,a.dylk,a.dykk from ta_bx_guide a,hremployee b where a.guide_id=b.empid and a.groupid="+id;
		 sql2="select b.id,b.ta_g_guide_id,b.cost_name_id,c.dmsm1,b.costs "+
		"from ta_bx_guide a,ta_bx_guide_other b,dmsm c "+
		"where b.cost_name_id=c.dmz and c.dmlb=17 and a.id=b.ta_g_guide_id and a.groupid="+id;
		 try {
			coreDAO.executeQuery(sql, "bx_guideList", rd);
			coreDAO.executeQuery(sql2, "mnyList", rd);
			if(rd.getTableRowsCount("bx_guideList")==0){
				sql="select a.id,a.groupid,a.guide_id,b.empname,a.dylk,a.dykk from ta_g_guide_do a,hremployee b where a.guide_id=b.empid and a.groupid="+id;
				 sql2="select b.id,b.ta_g_guide_id,b.cost_name_id,c.dmsm1,b.costs "+
				"from ta_g_guide_do a,ta_g_guide_other b,dmsm c "+
				"where b.cost_name_id=c.dmz and c.dmlb=17 and a.id=b.ta_g_guide_id and a.groupid="+id;
				 coreDAO.executeQuery(sql, "bx_guideList", rd);
				 coreDAO.executeQuery(sql2, "mnyList", rd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return 1;
	}
	public int bx_editGuide(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		BizData rd2=new BizData();
		try{
			coreDAO.beginTrasct(conn);
			if("up".equals(rd.getString("state"))){
				coreDAO.deleteEntity("TA_BX_GUIDE_OTHER", rd, conn);
				String sql="delete from TA_BX_GUIDE a where a.groupid="+rd.getString("groupid");
				coreDAO.executeQuery(sql, conn);
			}
			String []guideRows=rd.getRowIDs("TA_BX_GUIDE");
			for(int g=0;g<guideRows.length;g++){
				int id=this.queryMaxIDByPara("TA_BX_GUIDE", "ID", null);
				//导游费用TA_G_GUIDE_OTHER
				String guide_id=rd.getString("TA_BX_GUIDE", "GUIDE_ID", guideRows[g]);
				String []monRows=rd.getRowIDs("TA_BX_GUIDE_OTHER"+guide_id);
				if(null!=monRows){
					for(int m=0;m<monRows.length;m++){
						int m_id=this.queryMaxIDByPara("TA_BX_GUIDE_OTHER", "ID", null);
						rd.add("TA_BX_GUIDE_OTHER"+guide_id, "ID",monRows[m],m_id);
						rd.add("TA_BX_GUIDE_OTHER"+guide_id, "TA_G_GUIDE_ID",monRows[m],id);
					}
					rd2.copyEntity(rd, "TA_BX_GUIDE_OTHER"+guide_id,"TA_BX_GUIDE_OTHER");
					coreDAO.insert("TA_BX_GUIDE_OTHER",rd2,conn);
					rd2.remove("TA_BX_GUIDE_OTHER");
				}
				rd.add("TA_BX_GUIDE","ID",guideRows[g],id);
				rd.add("TA_BX_GUIDE","GROUPID",guideRows[g],rd.getString("groupid"));
				rd.add("TA_BX_GUIDE", "user_no",guideRows[g],rd.getString("userno"));
			}
			coreDAO.insert("TA_BX_GUIDE",rd,conn);
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
