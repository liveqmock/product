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

package com.trm.ztbx;

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
public class ZtCWJSBLC extends DBBLC {
	//初始化团任务单打印信息列表
	public void initGroupPrintList(BizData rd, BizData sd) {
		String groupID = rd.getString("groupID");// 获取查询条件:团号
		String bDate = rd.getString("bDate");// 获取查询条件:开始时间
		String pageNOStr = rd.getString("pageNO");// 获取分页
		String pageSizeStr = rd.getString("pageSize");// 获取分页
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		String sql = "";

		sql = "select g.id,g.line_id,g.xlmc,g.BEGIN_DATE,g.END_DATE,g.dtrq,g.ADULT_COUNT,g.CHILDREN_COUNT,g.state,u.userno \n"
				+ " from TA_ZT_group g,drmuser u\n"
				+ "where u.userno=g.user_no and g.state=6 \n";
		if (!"".equals(groupID)) { // 如果团号不为空
			sql += "and g.id like '%" + groupID + "%' \n";
		}
		if (!"".equals(bDate)) { // 如果开团时间不为空
			sql += "and g.dtrq=to_date('" + bDate + "','yyyy-mm-dd') \n";
		}
		sql +="ORDER BY g.DTRQ desc";
		try {
			coreDAO.executeQueryPage(sql, "rsGroupPrintList", pageNO, pageSize, rd);
			sql = "select g.id,j.jglx,j.jg,d.dmsm1 from TA_ZT_group g,TA_ZT_tdjg j,dmsm d\n"
					+ "where g.id=j.tid \n"
					+ "and d.dmz=j.jglx \n"
					+ "and d.dmlb=4";
			coreDAO.executeQuery(sql, "rsGroupPrintPrice", rd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int initGroupPrint(BizData rd, BizData sd){
		String groupID=rd.getString("groupID");
		String groupList =  "select g.id,g.ADULT_COUNT,g.CHILDREN_COUNT,g.days,g.begin_date,g.end_date,g.xlmc,z.sxfzj,z.bxclqd,z.bxclxf,z.clhj, \n"+
							"z.bxjdqd,z.bxjdxf,z.jdhj,z.bxctxf,z.bxctqd,z.cthj,z.bxpwxf,z.bxpwqd,z.pwhj,z.jdrshj,z.jdcbhj,z.jdqdhj,z.jdxfhj,z.yjcwxjhj,z.jdlrhj,z.gwlrhj,z.tdlr, \n"+
							"z.djqdzj,z.djxfzj,z.djhj,z.bx_jdxf,z.bx_jdqd,z.jindhj \n" +
							" from TA_ZT_group g,ta_tdbxzjxxb_zt z \n"+
							"where z.tid=g.id and g.id='"+groupID+"'";
		String jiandianList ="select j.rs,j.cbdj,j.jdqdxj,j.jdxfxj,s.cmpny_name from TA_ZT_bxjiadian j,ta_scenery_point s where s.scenery_id=j.jdid and j.tid='"+groupID+"'";
		String gwhzList ="select p.cmpny_name,s.goods_name,x.rs,x.lc \n" +
						 " from TA_ZT_bxgwmx x,ta_shop_goods s,ta_shoppoint p \n" +
						 "where s.shop_point_id=p.shop_point_id  and x.spid=s.goods_id and  x.tid ='"+groupID+"'";
		String GuideList ="select sum(DFF) DFF,sum(DYJTF) DYJTF,sum(DYLK) DYLK,sum(qt) DYQT from TA_ZT_bxdy d where d.tid='"+groupID+"'";
		String createrMsg="select u.username,r.rolename,f.creater from drmuser u,drmuserrole l,drmrole r,ta_flow f where f.definitionid='ztywsh'and u.userno=f.creater \n"+
		" and u.userid=l.userid and l.roleid=r.roleid and f.tid='"+groupID+"'";
		String ywbzgMsg="select u.username,r.rolename from drmuser u,drmuserrole l,drmrole r,TA_DJ_TSPB d where d.mklb=2 and d.spr=u.userno \n"+
		" and u.userid=l.userid and l.roleid=r.roleid and r.roleid='djbzg' and rownum=1 and d.tid='"+groupID+"'";
		String kjMsg="select u.username,r.rolename from drmuser u,drmuserrole l,drmrole r,TA_DJ_TSPB d where d.mklb=2 and d.spr=u.userno \n"+
		" and u.userid=l.userid and l.roleid=r.roleid and r.roleid='kuaiji' and d.tid='"+groupID+"'";
		String sql3 = "select nvl(sum(t.yin_sk),0) ddysk \n"+
		  " from TA_ZT_YKORDER t where t.tid='"+groupID+"'";
		try {
			coreDAO.executeQuery(groupList, "groupList", rd);
			coreDAO.executeQuery(jiandianList, "jiandianList", rd);
			coreDAO.executeQuery(gwhzList, "gwhzList", rd);
			coreDAO.executeQuery(GuideList, "GuideList", rd);
			coreDAO.executeQuery(createrMsg, "createrMsg", rd);
			coreDAO.executeQuery(ywbzgMsg, "ywbzgMsg", rd);
			coreDAO.executeQuery(kjMsg, "kjMsg", rd);
			coreDAO.executeQuery(sql3, "ykddysk", rd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 999;
	}
}

