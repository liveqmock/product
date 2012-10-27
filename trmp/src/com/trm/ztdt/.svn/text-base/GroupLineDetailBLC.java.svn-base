package com.trm.ztdt;

import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class GroupLineDetailBLC extends DBBLC{
	public GroupLineDetailBLC(){
		this.entityName="TA_ZT_LINEDETAI4G";
	}
	public void queryLineGroupBlobs(BizData rd,BizData sd) throws SQLException{
		String groupId = rd.getString("groupId");
		String lineId = rd.getString("lineId");
		String sql ="select to_number(g.rq) rqp, g.* \n" +
					" from ta_zt_linedetai4g g \n" +
					"where g.tid='"+groupId+"' \n" +
					"order by rqp";
		coreDAO.executeQuery(sql, "rsGroupBlobs", rd);
		sql = "select  a.xlid,a.tsjs,a.gwd,a.zfxm,a.fybh,a.fybbh,a.cjlyxz,a.lyzysx,a.wxts,a.ydxz,b.comments \n" +
			  " from ta_zt_linemngblob a,ta_zt_linemng b \n" +
			  "where a.xlid=b.line_id \n" +
			  "and a.xlid='"+lineId+"'";
		coreDAO.executeQuery(sql, "rsLineBlobs", rd);
		sql = "select line_name from ta_zt_linemng where line_id ='"+lineId+"'";
		coreDAO.executeQuery(sql, "rsLineName", rd);
	}
}
