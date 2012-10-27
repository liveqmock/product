package com.trm.baoxiao;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class SceneryBXBLC extends DBBLC{
	public SceneryBXBLC(){
		this.entityName="";
	}
	public int bx_getBXScenery(BizData rd,BizData sd){
		String id=rd.getString("id");
		String sql2="";
		String sql3="";
		sql2="select a.id,a.groupid,a.scenery_id,a.xfje,a.qdje,a.xfje+a.qdje gj,a.pro_id PRIVINCE_ID,"+	
		" a.city_id areaid,a.sjyyrs,a.ywydh,a.ywyxm,a.comments from ta_bx_scenery a where a.groupid="+id;
		sql3="select b.* from ta_bx_scenery a,ta_bx_scenery_mny b where b.g_sceneryid=a.id and a.groupid="+id;
		try {
			coreDAO.executeQuery(sql2, "p_sceneryList", rd);
			coreDAO.executeQuery(sql3, "mnyList", rd);
			if(rd.getTableRowsCount("p_sceneryList")==0){
				 sql2="select a.privince_id,a.city_id areaid,b.id,b.scenery_id,b.xfje,b.qdje,b.sjyyrs,b.ywydh,b.ywyxm,b.comments,b.dwj,b.xfje+b.qdje gj \n"+
					 " from ta_g_scenryxzqh a,ta_g_scenery b where a.id=b.areaid and a.groupid="+id;
				 sql3="select c.*,dmsm1 from ta_g_scenryxzqh a,ta_g_scenery b,ta_g_scenery_mny c,dmsm d " +
						"where a.id=b.areaid and b.id=c.g_sceneryid and c.pricenameid=d.dmz and d.dmlb=12 and a.groupid="+id;
				 coreDAO.executeQuery(sql2, "p_sceneryList", rd);
				 coreDAO.executeQuery(sql3, "mnyList", rd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	public int bx_editScenery(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		try{
			BizData rd2=new BizData();
			coreDAO.beginTrasct(conn);
			if("up".equals(rd.getString("state"))){
				String groupid=rd.getString("groupid");
				String sql="delete from TA_BX_SCENERY a where a.groupid="+groupid;
				coreDAO.executeUpdate(sql, conn);
				coreDAO.deleteEntity("TA_BX_SCENERY_MNY", rd, conn);
			}
			String[] rows=rd.getRowIDs("TA_BX_SCENERY");
			if(null!=rows){
				for(int a=0;a<rows.length;a++){
				int id=this.queryMaxIDByPara("TA_BX_SCENERY", "ID", null);
				rd.add("TA_BX_SCENERY", "ID",rows[a],id);
				String scenery_id=rd.getString("TA_BX_SCENERY","SCENERY_ID",rows[a]);
				String []priceRows=rd.getRowIDs("TA_BX_SCENERY_MNY"+scenery_id);
				if(null!=priceRows){
					for(int p=0;p<priceRows.length;p++){
						int m_id=this.queryMaxIDByPara("TA_BX_SCENERY_MNY", "ID", null);
						rd.add("TA_BX_SCENERY_MNY"+scenery_id, "ID",priceRows[p],m_id);
						rd.add("TA_BX_SCENERY_MNY"+scenery_id, "G_SCENERYID",priceRows[p],id);
					}
					rd2.copyEntity(rd, "TA_BX_SCENERY_MNY"+scenery_id, "TA_BX_SCENERY_MNY");
					coreDAO.insert("TA_BX_SCENERY_MNY", rd2, conn);
					
				}
				rd.add("TA_BX_SCENERY", "GROUPID",rows[a],rd.getString("groupid"));
				rd.add("TA_BX_SCENERY", "SQR",rows[a],rd.getString("userno"));
				}
			}
			coreDAO.insert("TA_BX_SCENERY", rd, conn);
			coreDAO.update("TA_GROUP_BASE", rd,conn);
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
