/**
 * DjCWJSBLC.java
 * com.trmdj.bx
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2011-8-22 		Administrator
 *
 * Copyright (c) 2011, TNT All Rights Reserved.
*/

package com.trmdj.bx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * ClassName:DjCWJSBLC 地接财务结算
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Administrator
 * @version  
 * @since    Ver 1.1
 * @Date	 2011-8-22		上午02:37:34
 *
 * @see 	 
 * @deprecated 
 */
public class DjCWJSBLC extends DBBLC {
	//初始化团任务单打印信息列表
	public void initGroupPrintList(BizData rd, BizData sd) {
		String groupID = rd.getString("groupID");// 获取查询条件:团号
		String bDate = rd.getString("bDate");// 获取查询条件:开始时间
		String pageNOStr = rd.getString("pageNO");// 获取分页
		String pageSizeStr = rd.getString("pageSize");// 获取分页
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		String sql = "";

		sql = "select g.id,g.xlid,g.xlmc,g.BEGIN_DATE,g.END_DATE,g.dtrq,g.crrs,g.etrs,g.state,u.userno \n"
				+ " from ta_dj_group g,drmuser u\n"
				+ "where u.userno=g.user_no and g.state=6 and g.orgid="+sd.getString("orgid")+"\n";
		if (!"".equals(groupID)) { // 如果团号不为空
			sql += "and g.id like '%" + groupID + "%' \n";
		}
		if (!"".equals(bDate)) { // 如果开团时间不为空
			sql += "and g.dtrq=to_date('" + bDate + "','yyyy-mm-dd') \n";
		}
		sql +="ORDER BY g.DTRQ desc";
		try {
			coreDAO.executeQueryPage(sql, "rsGroupPrintList", pageNO, pageSize, rd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int initGroupPrint(BizData rd, BizData sd) {
		
		Connection conn = coreDAO.getConnection();
		String groupID=rd.getString("groupID");
		String groupList =  "select g.id,g.crrs,g.etrs,g.tkzj,g.ts,g.begin_date,g.end_date,g.xlmc,z.* \n" +
							" from ta_dj_group g,ta_tdbxzjxxb z \n"+
							"where z.tid=g.id and g.orgid=z.orgid and g.id='"+groupID+"'";
		String jiandianList ="select * from ta_dj_bxjiadian j where j.tid='"+groupID+"' and orgid="+sd.getString("orgid");
		String gwhzList ="select * \n" +
						 " from ta_dj_bxgw x \n" +
						 "where x.tid ='"+groupID+"' and orgid="+sd.getString("orgid");
		/*String createrMsg="select u.username,r.rolename,f.creater from drmuser u,drmuserrole l,drmrole r,ta_flow f where f.definitionid='djywsh'and u.userno=f.creater \n"+
		" and u.userid=l.userid and l.roleid=r.roleid and f.tid='"+groupID+"'";
		String ywbzgMsg="select u.username,r.rolename from drmuser u,drmuserrole l,drmrole r,ta_dj_tspb d where d.mklb=2 and d.spr=u.userno \n"+
		" and u.userid=l.userid and l.roleid=r.roleid and r.roleid='djbzg' and rownum=1 and d.tid='"+groupID+"'";
		String kjMsg="select u.username,r.rolename from drmuser u,drmuserrole l,drmrole r,ta_dj_tspb d where d.mklb=2 and d.spr=u.userno \n"+
		" and u.userid=l.userid and l.roleid=r.roleid and r.roleid='kuaiji' and d.tid='"+groupID+"'";*/
		try {
			coreDAO.executeQuery(groupList, "groupList", rd, conn);
			coreDAO.executeQuery(jiandianList, "jiandianList", rd, conn);
			coreDAO.executeQuery(gwhzList, "gwhzList", rd, conn);
			/*coreDAO.executeQuery(createrMsg, "createrMsg", rd);
			coreDAO.executeQuery(ywbzgMsg, "ywbzgMsg", rd);
			coreDAO.executeQuery(kjMsg, "kjMsg", rd);*/
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(null != conn)
					conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 999;
	}
}

