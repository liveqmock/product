package com.trm.moneyMng;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class ZtCWDZBLC extends DBBLC{
	
	/**查询财务对账信息
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int searchCwdz(BizData rd,BizData sd){
		String state=rd.getString("state");
		String bdate=rd.getString("bdate");
		String edate=rd.getString("edate");
		String tid=rd.getString("groupID");
		String ddh=rd.getString("ddh");
		String xlmc=rd.getString("xlmc");
		String dzzt=rd.getString("dzzt");
		String pageNOStr = rd.getString("pageNO");
		String pageSizeStr = rd.getString("pageSize");
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		String sql= "select g.id,g.begin_date,g.end_date,g.xlmc,g.szlxsmc,g.adult_count,g.children_count,a.bx,b.thj,c.dds,d.jj \n"+
					" from (select sum(case when i.cbfs=1 then (c.insurancecost*c.persons*g.days)else(c.insurancecost*c.persons) end) bx,y.tid from ta_zt_ginsurance c,ta_zt_insurance i,ta_zt_linemng l,ta_zt_lineandinsurance li,ta_zt_group g,ta_zt_ykorder y \n"+
					"where c.insurancetype=i.id and c.insurancetype=li.insuranceid and c.orderid=y.id and y.tid=g.id and g.line_id=l.line_id \n" +
					"group by y.tid) \n"+
					"a, (select sum(p.price_th*p.person_count) thj,y.tid from ta_zt_gorderprice p,ta_zt_ykorder y where y.id=p.orderid \n" +
					"group by y.tid)  \n"+
					"b, (select count(*) dds,y.tid from ta_zt_ykorder y,ta_flow f where y.id=f.tid(+) \n";
					if (!"".equals(state)) { 
						sql += "and y.dzzt='"+state+"' \n";
					}
					if (!"".equals(ddh)) { // 如果团号不为空
						sql += "and y.id like '%" + ddh + "%' \n";
					}
					if("wdz".equals(dzzt)){
					  	sql+="and f.state is null and y.creater='"+sd.getString("userno")+"' \n";
					}
					if("dzz".equals(dzzt)){
					  	sql+="and f.state in('start','activity') \n";
					  	String[] roleList = sd.getString("USERROLEST").substring(1, sd.getString("USERROLEST").length()-1).split(",");
						  String sqlPlus ="";
						  for(int i = 0; i < roleList.length; i++){
							  sqlPlus += "'"+roleList[i].trim()+"',";
						  }
						  String sqlPlus1 = sqlPlus.substring(0,sqlPlus.length()-1);
						 sql+="and f.nextroleid in (" + sqlPlus1 + ") \n";
					}
					if("yczd".equals(dzzt)){
					  	sql+="and f.state ='end' \n";
					}
			 sql += "and y.dd_confirm=1 \n" +
			 		"group by y.tid) \n"+
			 		"c, (select sum(y.add_m_count) jj,y.tid from ta_zt_ykorder y \n" +
			 		"group by y.tid)  \n"+
			 		"d, ta_zt_group g \n"+
					"where d.tid=g.id and d.tid=a.tid and d.tid=b.tid and d.tid=c.tid \n";
			    if (!"".equals(tid)) { // 如果团号不为空
					sql += "and g.id like '%" + tid + "%' \n";
				}
			    if (!"".equals(xlmc)) { // 如果线路名称不为空
					sql += "and g.xlmc like '%" + xlmc + "%' \n";
		  	    }
			    if (!"".equals(bdate)) { // 如果开团时间不为空
					sql += "and g.begin_date >=to_date('" + bdate + "','yyyy-mm-dd') \n";
				}
			    if (!"".equals(edate)) { // 如果开团时间不为空
					sql += "and g.end_date <= to_date('" + edate + "','yyyy-mm-dd') \n";
				}
				  
		  try {
				coreDAO.executeQueryPage(sql, "rsCwdzInfo", pageNO, pageSize, rd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return 999;
	}
	
	/**
	 * 未对账账单
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int notConfirmBill(BizData rd,BizData sd) {
		
		rd.add("target", rd.getString("target"));
		String state=rd.getString("state");
		String bdate=rd.getString("bdate");
		String edate=rd.getString("edate");
		String tid=rd.getString("groupID");
		String ddh=rd.getString("ddh");
		String xlmc=rd.getString("xlmc");
		String pageNOStr = rd.getString("pageNO");
		String pageSizeStr = rd.getString("pageSize");
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		String sql= "select g.id,g.begin_date,g.end_date,g.xlmc,g.szlxsmc,g.adult_count,g.children_count,a.bx,b.thj,c.dds,d.jj \n"+
					" from (select sum(case when i.cbfs=1 then (c.insurancecost*c.persons*g.days)else(c.insurancecost*c.persons) end) bx,y.tid from ta_zt_ginsurance c,ta_zt_insurance i,ta_zt_linemng l,ta_zt_lineandinsurance li,ta_zt_group g,ta_zt_ykorder y \n"+
					"where c.insurancetype=i.id and c.insurancetype=li.insuranceid and c.orderid=y.id and y.tid=g.id and g.line_id=l.line_id \n";
					if (!"".equals(state)) { 
						sql += "and y.dzzt='"+state+"' \n";
					}
			 sql += "and y.dd_confirm=1 \n" +
					"group by y.tid) \n"+
					"a, (select sum(p.price_th*p.person_count) thj,y.tid from ta_zt_gorderprice p,ta_zt_ykorder y where y.id=p.orderid \n" ;
					if (!"".equals(state)) { 
						sql += "and y.dzzt='"+state+"' \n";
					}
			 sql += "and y.dd_confirm=1 \n" +
					"group by y.tid)  \n"+
					"b, (select count(*) dds,y.tid from ta_zt_ykorder y where 1=1 \n";
					if (!"".equals(state)) { 
						sql += "and y.dzzt='"+state+"' \n";
					}
			 sql += "and y.dd_confirm=1 \n";
					if (!"".equals(ddh)) { // 如果团号不为空
						sql += "and y.id like '%" + ddh + "%' \n";
					}
			 sql += "group by y.tid) \n"+
			 		"c, (select sum(y.add_m_count) jj,y.tid from ta_zt_ykorder y where 1=1 \n";
			 		if (!"".equals(state)) { 
						sql += "and y.dzzt='"+state+"' \n";
					}
			 sql += "and y.dd_confirm=1 \n" +
			 		"group by y.tid) \n"+
			 		"d, ta_zt_group g \n"+
					"where d.tid=g.id and d.tid=a.tid and d.tid=b.tid and d.tid=c.tid \n" +
					"and g.orgid<>1 \n";
			    if (!"".equals(tid)) { // 如果团号不为空
					sql += "and g.id like '%" + tid + "%' \n";
				}
			    if (!"".equals(xlmc)) { // 如果线路名称不为空
					sql += "and g.xlmc like '%" + xlmc + "%' \n";
		  	    }
			    if (!"".equals(bdate)) { // 如果开团时间不为空
					sql += "and g.begin_date >=to_date('" + bdate + "','yyyy-mm-dd') \n";
				}
			    if (!"".equals(edate)) { // 如果开团时间不为空
					sql += "and g.end_date <= to_date('" + edate + "','yyyy-mm-dd') \n";
				}
				  
		  try {
				coreDAO.executeQueryPage(sql, "rsCwdzInfo", pageNO, pageSize, rd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return 999;
	}
	
	/**
	 * 未出账单明细
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int notConfirmBillDetail(BizData rd,BizData sd){
		
		String groupId = rd.getString("groupId");
		String sql ="select sum(case when i.cbfs=1 then (c.insurancecost*c.persons*g.days)else(c.insurancecost*c.persons)end) bx,y.id,y.cmpny_name,v.visitor_nm \n"+
					" from ta_zt_ginsurance c,ta_zt_insurance i,ta_zt_linemng l,ta_zt_lineandinsurance li,ta_zt_group g,ta_zt_ykorder y,ta_zt_visitor v \n"+
					"where c.insurancetype=i.id \n"+
					"and c.insurancetype=li.insuranceid \n"+
					"and c.orderid=y.id \n"+
					"and y.tid=g.id \n"+
					"and g.line_id=l.line_id \n"+
					"and y.id=v.m_id \n"+
					"and v.isleader=1 \n"+
					"and y.dzzt='0' and y.dd_confirm=1 and y.tid='"+groupId+"' \n"+
					"group by y.id,y.cmpny_name,v.visitor_nm\n";

		String sql1 ="select b.* from (\n";
		sql1 += sql;
		sql1 += ") a,(\n";
		sql1 += "select p.orderid,d.dmsm1,p.person_count,p.price_th,p.person_count*p.price_th hj \n";
		sql1 += " from ta_zt_gorderprice p,dmsm d,ta_zt_ykorder y \n"+
					 "where d.dmlb=4 \n"+
					 "and p.orderid=y.id \n"+
					 "and d.dmz=p.price_type \n" +
					 "and p.person_count<>0\n"+
					 "and y.tid='"+groupId+"'\n" +
					 ") b\n" +
					 "where a.id=b.orderid\n";
		try {
			coreDAO.executeQuery(sql, "rsCwdzYkorderInfo", rd);
			coreDAO.executeQuery(sql1, "rsCwdzPriceInfo", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
	}
	
	
	/**
	 * 对账中账单列表
	 * @return
	 */
	public int confirmingBillList(BizData rd,BizData sd){
		String bdate=rd.getString("bdate");
		String edate=rd.getString("edate");
		String tid=rd.getString("groupID");
		String ddh=rd.getString("ddh");
		String xlmc=rd.getString("xlmc");
		String pageNOStr = rd.getString("pageNO");
		String pageSizeStr = rd.getString("pageSize");
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		String sql= "select g.id,g.begin_date,g.end_date,g.xlmc,g.szlxsmc,g.adult_count,g.children_count,a.bx,b.thj,c.dds,d.jj \n"+
					" from (select sum(case when i.cbfs=1 then (c.insurancecost*c.persons*g.days)else(c.insurancecost*c.persons) end) bx,y.tid from ta_zt_ginsurance c,ta_zt_insurance i,ta_zt_linemng l,ta_zt_lineandinsurance li,ta_zt_group g,ta_zt_ykorder y \n"+
					"where c.insurancetype=i.id and c.insurancetype=li.insuranceid and c.orderid=y.id and y.tid=g.id and g.line_id=l.line_id \n" +
					"and y.dzzt='1' \n"+
					"and y.dd_confirm=1 \n"+
					"group by y.tid) \n"+
					"a, (select sum(p.price_th*p.person_count) thj,y.tid from ta_zt_gorderprice p,ta_zt_ykorder y where y.id=p.orderid \n" +
					"and y.dzzt='1' \n"+
					"and y.dd_confirm=1 \n"+
					"group by y.tid)  \n"+
					"b, (select count(*) dds,y.tid from ta_zt_ykorder y \n" +
					"where y.dzzt='1' \n"+
					"and y.dd_confirm=1 \n";
					if (!"".equals(ddh)) { // 如果团号不为空
						sql += "and y.id like '%" + ddh + "%' \n";
					}
			 sql += "group by y.tid) \n"+
			 		"c, (select sum(y.add_m_count) jj,y.tid from ta_zt_ykorder y \n" +
			 		"where y.dzzt='1' \n"+
					"and y.dd_confirm=1 \n"+
			 		"group by y.tid)  \n"+
			 		"d, ta_zt_group g, ta_zt_ykorder y \n"+
					"where d.tid=g.id and d.tid=a.tid and d.tid=b.tid and d.tid=c.tid \n" +
					"and g.orgid<>1 \n" +
					"and g.id=y.tid \n" +
					"and y.dzzt='1' \n" +
					"and y.dd_confirm=1 \n";
			    if (!"".equals(tid)) { // 如果团号不为空
					sql += "and g.id like '%" + tid + "%' \n";
				}
			    if (!"".equals(xlmc)) { // 如果线路名称不为空
					sql += "and g.xlmc like '%" + xlmc + "%' \n";
		  	    }
			    if (!"".equals(bdate)) { // 如果开团时间不为空
					sql += "and g.begin_date >=to_date('" + bdate + "','yyyy-mm-dd') \n";
				}
			    if (!"".equals(edate)) { // 如果开团时间不为空
					sql += "and g.end_date <= to_date('" + edate + "','yyyy-mm-dd') \n";
				}
				  
		  try {
				coreDAO.executeQueryPage(sql, "rsCwdzInfo", pageNO, pageSize, rd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return 999;
	}
	
	/**
	 * 对账中账单明细
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int confirmingBillDetail(BizData rd,BizData sd){
		
		String groupId = rd.getString("groupId");
		String state = rd.getString("state");
		String sql ="select sum(case when i.cbfs=1 then (c.insurancecost*c.persons*g.days)else(c.insurancecost*c.persons)end) bx\n" +
				",y.id,y.cmpny_name,v.visitor_nm,y.add_m_count \n"+
					" from ta_zt_ginsurance c,ta_zt_insurance i,ta_zt_linemng l,ta_zt_lineandinsurance li,ta_zt_group g,ta_zt_ykorder y,ta_zt_visitor v \n"+
					"where c.insurancetype=i.id \n"+
					"and c.insurancetype=li.insuranceid \n"+
					"and c.orderid=y.id \n"+
					"and y.tid=g.id \n"+
					"and g.line_id=l.line_id \n"+
					"and y.id=v.m_id \n"+
					"and v.isleader=1 \n"+
					"and y.dzzt='"+state+"' and y.dd_confirm=1 and y.tid='"+groupId+"' \n"+
					"group by y.id,y.cmpny_name,v.visitor_nm,y.add_m_count\n";

		String sql1 ="select a.bx,a.add_m_count,b.* from (\n";
		sql1 += sql;
		sql1 += ") a,(\n";
		sql1 += "select p.orderid,d.dmsm1,p.person_count,p.price_th,p.person_count*p.price_th hj \n";
		sql1 += " from ta_zt_gorderprice p,dmsm d,ta_zt_ykorder y \n"+
					 "where d.dmlb=4 \n"+
					 "and p.orderid=y.id \n"+
					 "and d.dmz=p.price_type \n" +
					 "and p.person_count<>0\n"+
					 "and y.tid='"+groupId+"'\n" +
					 "and y.dzzt='"+state+"'\n" +
					 ") b\n" +
					 "where a.id=b.orderid\n";
		try {
			coreDAO.executeQuery(sql, "rsCwdzYkorderInfo", rd);
			coreDAO.executeQuery(sql1, "rsCwdzPriceInfo", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
	}
	
	/**
	 * 已出账单列表
	 * @return
	 */
	public int successBillList(BizData rd,BizData sd){
		
		String bdate=rd.getString("bdate");
		String edate=rd.getString("edate");
		String tid=rd.getString("groupID");
		String ddh=rd.getString("ddh");
		String xlmc=rd.getString("xlmc");
		String pageNOStr = rd.getString("pageNO");
		String pageSizeStr = rd.getString("pageSize");
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		String sql= "select g.id,g.begin_date,g.end_date,g.xlmc,g.szlxsmc,g.adult_count,g.children_count,a.bx,b.thj,c.dds,d.jj \n"+
					" from (select sum(case when i.cbfs=1 then (c.insurancecost*c.persons*g.days)else(c.insurancecost*c.persons) end) bx,y.tid from ta_zt_ginsurance c,ta_zt_insurance i,ta_zt_linemng l,ta_zt_lineandinsurance li,ta_zt_group g,ta_zt_ykorder y \n"+
					"where c.insurancetype=i.id and c.insurancetype=li.insuranceid and c.orderid=y.id and y.tid=g.id and g.line_id=l.line_id \n" +
					"group by y.tid) \n"+
					"a, (select sum(p.price_th*p.person_count) thj,y.tid from ta_zt_gorderprice p,ta_zt_ykorder y where y.id=p.orderid \n" +
					"group by y.tid)  \n"+
					"b, (select count(*) dds,y.tid from ta_zt_ykorder y where 1=1 \n" +
					"and y.dzzt='2' \n";
					if (!"".equals(ddh)) { // 如果团号不为空
						sql += "and y.id like '%" + ddh + "%' \n";
					}
					
			 sql += "and y.dd_confirm=1 \n" +
			 		"group by y.tid) \n"+
			 		"c, (select sum(y.add_m_count) jj,y.tid from ta_zt_ykorder y \n" +
			 		"group by y.tid)  \n"+
			 		"d, ta_zt_group g \n"+
					"where d.tid=g.id and d.tid=a.tid and d.tid=b.tid and d.tid=c.tid \n" +
					"and g.orgid<>1 \n";
			    if (!"".equals(tid)) { // 如果团号不为空
					sql += "and g.id like '%" + tid + "%' \n";
				}
			    if (!"".equals(xlmc)) { // 如果线路名称不为空
					sql += "and g.xlmc like '%" + xlmc + "%' \n";
		  	    }
			    if (!"".equals(bdate)) { // 如果开团时间不为空
					sql += "and g.begin_date >=to_date('" + bdate + "','yyyy-mm-dd') \n";
				}
			    if (!"".equals(edate)) { // 如果开团时间不为空
					sql += "and g.end_date <= to_date('" + edate + "','yyyy-mm-dd') \n";
				}
				  
		  try {
				coreDAO.executeQueryPage(sql, "rsCwdzInfo", pageNO, pageSize, rd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return 999;
	}
	
	/**
	 * 已出账单明细
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int successBillDetail(BizData rd,BizData sd){
		
		String groupId = rd.getString("groupId");
		String state = rd.getString("state");
		String sql ="select sum(case when i.cbfs=1 then (c.insurancecost*c.persons*g.days)else(c.insurancecost*c.persons)end) bx\n" +
				",y.id,y.cmpny_name,v.visitor_nm,y.add_m_count \n"+
					" from ta_zt_ginsurance c,ta_zt_insurance i,ta_zt_linemng l,ta_zt_lineandinsurance li,ta_zt_group g,ta_zt_ykorder y,ta_zt_visitor v \n"+
					"where c.insurancetype=i.id \n"+
					"and c.insurancetype=li.insuranceid \n"+
					"and c.orderid=y.id \n"+
					"and y.tid=g.id \n"+
					"and g.line_id=l.line_id \n"+
					"and y.id=v.m_id \n"+
					"and v.isleader=1 \n"+
					"and y.dzzt='"+state+"' and y.dd_confirm=1 and y.tid='"+groupId+"' \n"+
					"group by y.id,y.cmpny_name,v.visitor_nm,y.add_m_count\n";

		String sql1 ="select a.bx,a.add_m_count,b.* from (\n";
		sql1 += sql;
		sql1 += ") a,(\n";
		sql1 += "select p.orderid,d.dmsm1,p.person_count,p.price_th,p.person_count*p.price_th hj \n";
		sql1 += " from ta_zt_gorderprice p,dmsm d,ta_zt_ykorder y \n"+
					 "where d.dmlb=4 \n"+
					 "and p.orderid=y.id \n"+
					 "and d.dmz=p.price_type \n" +
					 "and p.person_count<>0\n"+
					 "and y.tid='"+groupId+"'\n" +
					 "and y.dzzt='"+state+"'\n" +
					 ") b\n" +
					 "where a.id=b.orderid\n";
		try {
			coreDAO.executeQuery(sql, "rsCwdzYkorderInfo", rd);
			coreDAO.executeQuery(sql1, "rsCwdzPriceInfo", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
	}
	
	public int showYkorderList(BizData rd,BizData sd) throws SQLException {
		String groupId = rd.getString("groupId");
		String state = rd.getString("state");
		String sql ="select sum(case when i.cbfs=1 then (c.insurancecost*c.persons*g.days)else(c.insurancecost*c.persons)end) bx\n" +
				",y.id,y.cmpny_name,v.visitor_nm,y.add_m_count \n"+
					" from ta_zt_ginsurance c,ta_zt_insurance i,ta_zt_linemng l,ta_zt_lineandinsurance li,ta_zt_group g,ta_zt_ykorder y,ta_zt_visitor v \n"+
					"where c.insurancetype=i.id \n"+
					"and c.insurancetype=li.insuranceid \n"+
					"and c.orderid=y.id \n"+
					"and y.tid=g.id \n"+
					"and g.line_id=l.line_id \n"+
					"and y.id=v.m_id \n"+
					"and v.isleader=1 \n"+
					"and y.dzzt='"+state+"' and y.dd_confirm=1 and y.tid='"+groupId+"' \n"+
					"group by y.id,y.cmpny_name,v.visitor_nm,y.add_m_count\n";

		String sql1 ="select a.bx,a.add_m_count,b.* from (\n";
		sql1 += sql;
		sql1 += ") a,(\n";
		sql1 += "select p.orderid,d.dmsm1,p.person_count,p.price_th,p.person_count*p.price_th hj \n";
		sql1 += " from ta_zt_gorderprice p,dmsm d,ta_zt_ykorder y \n"+
					 "where d.dmlb=4 \n"+
					 "and p.orderid=y.id \n"+
					 "and d.dmz=p.price_type \n" +
					 "and p.person_count<>0\n"+
					 "and y.tid='"+groupId+"'\n" +
					 "and y.dzzt='"+state+"'\n" +
					 ") b\n" +
					 "where a.id=b.orderid\n";
		coreDAO.executeQuery(sql, "rsCwdzYkorderInfo", rd);
		coreDAO.executeQuery(sql1, "rsCwdzPriceInfo", rd);
		return 999;
	}
	
	
	//查询审批意见
	public int querySPYJInfo(BizData rd,BizData sd) throws SQLException{
		String groupID = rd.getString("groupId");
		String shspyj="select t.tid,t.spyj,u.username from TA_ZT_YKORDER r,ta_dj_tspb t,drmuser u,ta_flow f where r.id=t.tid and t.spr=u.userno and f.tid=t.tid and f.isgroup='A' and f.state in('start','activity','end')";
		if(!"".equals(groupID))
			shspyj+="and r.tid='"+groupID+"'";
		coreDAO.executeQuery(shspyj, "SPYJ", rd);
		return 999;
	}
	/**
	 * @Description: 审批提交
	 * 1、查询出当前节点相关信息
	 * 2、结果流程定义，查询出下级节点相关信息
	 * 3、更新流程数据表
	 * 4、添加团审批意见表
	 * @param @param rd
	 * @param @param sd
	 * @return int    返回类型
	 * @throws
	 */
	public int authorizeBx(BizData rd,BizData sd) {
		Connection conn=coreDAO.getConnection();
		try{
			coreDAO.beginTrasct(conn);
			String DDH=rd.getString("DDH");
			//String[] ddhRows = rd.getRowIDs("DDH"+DDH);//获取订单数量
			//for(int i = 0; i < ddhRows.length; i++){
				//String SFXZ = rd.getString("DDH"+DDH, "SFXZ", ddhRows[i]);//该订单是否选中Y为选中 ""为不选中
				//if(!"".equals(SFXZ)){
					//String ddhID = rd.getString("DDH"+DDH, "DDH", ddhRows[i]);
					String SPYJ = rd.getString("DDH"+DDH, "SPYJ", 0);//审批意见为text文本
					String SPZT = rd.getString("SPZT"+DDH, "TGZT", 0);//审批通过状态 Y为通过 N为不通过
					BizData data = new BizData();
					//审批之前的下级节点做为审批后的当前节点
					data.add("TA_FLOW", "TID", DDH);
					data.add("TA_FLOW", "DEFINITIONID", "ztcwdz");
					data.add("TA_FLOW", "ISGROUP", "A");
					coreDAO.select(data);
					String thisNodeID = data.getStringByDI("ta_flows", "nextnodeid", 0);
					String thisRoleID = data.getStringByDI("ta_flows", "NEXTROLEID", 0);
					//计算下级节点
					queryNextNodeInfoByGroupID(data,DDH);
					String nextNodeID = data.getStringByDI("rsNextNode", "nextid", 0);
					String nextRoleID = data.getStringByDI("rsNextNode", "nextroleid", 0);
					String state = "activity";
					String isBack = "N";
					//无下级节点，流程将结束
					if(null == nextNodeID || "".equals(nextNodeID)){
						queryCreateRole(rd, DDH);
						state = "end";
						nextRoleID = rd.getStringByDI("rsCreateRole", "roleid", 0);
						nextNodeID = rd.getStringByDI("rsCreateRole", "nodeid", 0);
						BizData data1 = new BizData();
						data1.addAttr("TA_ZT_YKORDER", "ID", 0, "oldValue", DDH);
						data1.add("TA_ZT_YKORDER", "DZZT", "2");//审核结束DZZT对账状态值 修改为 2 通过
						coreDAO.update("TA_ZT_YKORDER", data1, conn);
					}
					if("N".equals(SPZT)){//回退。查询该流程的上一个节点参与者，并赋值给流程数据表
						//queryCreateRole(rd, groupId);
						queryOnARole(rd, DDH);
						nextRoleID = rd.getStringByDI("rsOnARole", "roleid", 0);
						nextNodeID = rd.getStringByDI("rsOnARole", "nodeid", 0);
						isBack = "Y";
						BizData data1 = new BizData();
						data1.addAttr("TA_ZT_YKORDER", "ID", 0, "oldValue", DDH);
						data1.add("TA_ZT_YKORDER", "DZZT", "3");//审核回退 DZZT 对账状态值 修改为 3 不通过
						coreDAO.update("TA_ZT_YKORDER", data1, conn);
					}
					//更新流程数据表
					updateFLowInfo(conn, DDH, thisRoleID, state, thisNodeID, nextRoleID, nextNodeID, isBack);
					//添加审批意见到审批表
					addAuthorizeMsg(DDH, conn, thisNodeID, SPZT, SPYJ,sd.getString("userno"));
				//}
			//}
			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 999;
	}
	
	/**
	 * 1、根据流程定义ID查询流程节点信息。包括当前节点及下级节点
	 * 2、维护流程数据表
	 * 3、变更订单对账状态为1，即对账中
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int submitBill(BizData rd,BizData sd) {
		Connection conn = coreDAO.getConnection();
		//流程定义名字
		String definitionID = "ztcwdz";
		//团号
		String DDH = rd.getString("DDH");
		//String[] ddhRows = rd.getRowIDs("DDH"+DDH);//获取订单数量
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,n2.nodeid cnodeid,n2.nodename cnodename,n2.roleid croleid\n");
		sql.append("from (\n").append("select n.nodeid,n.nodename,n.nodedesc,n.roleid\n")
			.append("from ta_flownode n,ta_flowdefinition f\n")
			.append("where n.definitionid=f.definitionid\n")
			.append("and f.definitionid='"+definitionID+"'\n")
			.append("and n.pid=-1 \n")
			.append(") a,ta_flownode n2\n")
			.append("where a.nodeid=n2.pid\n")
			.append("and n2.definitionid ='"+definitionID+"'");
		try {
			coreDAO.executeQuery(sql.toString(), "rsNodes", rd);
			coreDAO.beginTrasct(conn);
			//for(int i = 0; i < ddhRows.length; i++){
				//String ddhID = rd.getString("DDH"+DDH, "DDH", ddhRows[i]);
				//2，先查询是否已存在
				BizData data = new BizData();
				data.add("TA_FLOW", "TID", DDH);
				data.add("TA_FLOW", "DEFINITIONID",definitionID);
				data.add("TA_FLOW", "ISGROUP", "A");
				int rows = coreDAO.select(data);
				data.remove("TA_FLOW");
				if(rows == 0)
					add2FlowInfo(conn, data, sd.getString("userno"), rd, definitionID, DDH);
				else
					updateFLowInfo(conn, DDH, rd.getStringByDI("rsNodes", "roleid", 0), 
							"start", rd.getStringByDI("rsNodes", "nodeid", 0), 
							rd.getStringByDI("rsNodes", "croleid", 0), 
							rd.getStringByDI("rsNodes", "cnodeid", 0), "N");
				//改变订单的对账状态为对账中
				data.add("TA_ZT_YKORDER", "id", DDH);
				data.addAttr("TA_ZT_YKORDER", "id", "0", "oldValue", DDH);
				data.add("TA_ZT_YKORDER", "DZZT", "1");//对账中
				coreDAO.update("TA_ZT_YKORDER", data, conn);
			//}
			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 999;
	}
	
	/**
	 * 未对账的应收账单列表
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int myBillList(BizData rd,BizData sd){
		
		rd.add("target", rd.getString("target"));
		String state=rd.getString("state");
		String bdate=rd.getString("bdate");
		String edate=rd.getString("edate");
		String tid=rd.getString("groupID");
		String ddh=rd.getString("ddh");
		String xlmc=rd.getString("xlmc");
		String pageNOStr = rd.getString("pageNO");
		String pageSizeStr = rd.getString("pageSize");
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		String sql= "select g.id,g.begin_date,g.end_date,g.xlmc,g.szlxsmc,g.adult_count,g.children_count,a.bx,b.thj,c.dds,d.jj \n"+
					" from (select sum(case when i.cbfs=1 then (c.insurancecost*c.persons*g.days)else(c.insurancecost*c.persons) end) bx,y.tid from ta_zt_ginsurance c,ta_zt_insurance i,ta_zt_linemng l,ta_zt_lineandinsurance li,ta_zt_group g,ta_zt_ykorder y \n"+
					"where c.insurancetype=i.id and c.insurancetype=li.insuranceid and c.orderid=y.id and y.tid=g.id and g.line_id=l.line_id \n";
					if (!"".equals(state)) { 
						sql += "and y.dzzt='"+state+"' \n";
					}
			 sql += "and y.dd_confirm=1 \n" +
					"group by y.tid) \n"+
					"a, (select sum(p.price_th*p.person_count) thj,y.tid from ta_zt_gorderprice p,ta_zt_ykorder y where y.id=p.orderid \n" ;
					if (!"".equals(state)) { 
						sql += "and y.dzzt='"+state+"' \n";
					}
			 sql += "and y.dd_confirm=1 \n" +
					"group by y.tid)  \n"+
					"b, (select count(*) dds,y.tid from ta_zt_ykorder y where 1=1 \n";
					if (!"".equals(state)) { 
						sql += "and y.dzzt='"+state+"' \n";
					}
			 sql += "and y.dd_confirm=1 \n";
					if (!"".equals(ddh)) { // 如果团号不为空
						sql += "and y.id like '%" + ddh + "%' \n";
					}
			 sql += "group by y.tid) \n"+
			 		"c, (select sum(y.add_m_count) jj,y.tid from ta_zt_ykorder y where 1=1 \n";
			 		if (!"".equals(state)) { 
						sql += "and y.dzzt='"+state+"' \n";
					}
			 sql += "and y.dd_confirm=1 \n" +
			 		"group by y.tid)  \n"+
			 		"d, ta_zt_group g \n"+
					"where d.tid=g.id and d.tid=a.tid and d.tid=b.tid and d.tid=c.tid \n" +
					"and g.orgid=1 \n";
			    if (!"".equals(tid)) { // 如果团号不为空
					sql += "and g.id like '%" + tid + "%' \n";
				}
			    if (!"".equals(xlmc)) { // 如果线路名称不为空
					sql += "and g.xlmc like '%" + xlmc + "%' \n";
		  	    }
			    if (!"".equals(bdate)) { // 如果开团时间不为空
					sql += "and g.begin_date >=to_date('" + bdate + "','yyyy-mm-dd') \n";
				}
			    if (!"".equals(edate)) { // 如果开团时间不为空
					sql += "and g.end_date <= to_date('" + edate + "','yyyy-mm-dd') \n";
				}
				  
		  try {
				coreDAO.executeQueryPage(sql, "rsCwdzInfo", pageNO, pageSize, rd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		return 98;
	}
	
	/**
	 * 应收款未对账明细
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int myBillListDetail(BizData rd,BizData sd){
		
		String groupId = rd.getString("groupId");
		String state = rd.getString("state");
		String sql ="select sum(case when i.cbfs=1 then (c.insurancecost*c.persons*g.days)else(c.insurancecost*c.persons)end) bx\n" +
				",y.id,y.cmpny_name,v.visitor_nm,y.add_m_count \n"+
					" from ta_zt_ginsurance c,ta_zt_insurance i,ta_zt_linemng l,ta_zt_lineandinsurance li,ta_zt_group g,ta_zt_ykorder y,ta_zt_visitor v \n"+
					"where c.insurancetype=i.id \n"+
					"and c.insurancetype=li.insuranceid \n"+
					"and c.orderid=y.id \n"+
					"and y.tid=g.id \n"+
					"and g.line_id=l.line_id \n"+
					"and y.id=v.m_id \n"+
					"and v.isleader=1 \n"+
					"and y.dzzt='"+state+"' and y.dd_confirm=1 and y.tid='"+groupId+"' \n"+
					"group by y.id,y.cmpny_name,v.visitor_nm,y.add_m_count\n";

		String sql1 ="select a.bx,a.add_m_count,b.* from (\n";
		sql1 += sql;
		sql1 += ") a,(\n";
		sql1 += "select p.orderid,d.dmsm1,p.person_count,p.price_th,p.person_count*p.price_th hj \n";
		sql1 += " from ta_zt_gorderprice p,dmsm d,ta_zt_ykorder y \n"+
					 "where d.dmlb=4 \n"+
					 "and p.orderid=y.id \n"+
					 "and d.dmz=p.price_type \n" +
					 "and p.person_count<>0\n"+
					 "and y.tid='"+groupId+"'\n" +
					 "and y.dzzt='"+state+"'\n" +
					 ") b\n" +
					 "where a.id=b.orderid\n";
		try {
			coreDAO.executeQuery(sql, "rsCwdzYkorderInfo", rd);
			coreDAO.executeQuery(sql1, "rsCwdzPriceInfo", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
	}
	
	/**
	 * 更新订单对账状态
	 */
	public int update(BizData rd,BizData sd){
		
		String orderNo = rd.getString("ddh");
		rd.add("TA_ZT_YKORDER", "id", orderNo);
		rd.addAttr("TA_ZT_YKORDER", "id", 0, "oldValue", orderNo);
		rd.add("TA_ZT_YKORDER", "dzzt", "1");
		try {
			coreDAO.update(rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 88;
	}
	
	/**
	 * 流程引擎调用
	 * 更新流程数据表
	 * @param conn
	 * @param groupID
	 * @param currentRole
	 * @param state
	 * @param currentNode
	 * @param nextRole
	 * @param nextNode
	 * @param isBack
	 * @throws SQLException 
	 */
	private void updateFLowInfo(Connection conn, String ddhID, String currentRole, String state,
		String currentNode, String nextRole, String nextNode, String isBack) throws SQLException {
		String LCID = "ztcwdz";
		BizData data3 = new BizData();
		data3.addAttr("TA_FLOW", "TID", 0, "oldValue", ddhID);
		data3.addAttr("TA_FLOW", "DEFINITIONID", 0, "oldValue", LCID);
		data3.add("TA_FLOW", "THISROLEID", currentRole);
		data3.add("TA_FLOW", "THISNODEID", currentNode);
		data3.add("TA_FLOW", "NEXTROLEID", nextRole);
		data3.add("TA_FLOW", "NEXTNODEID", nextNode);
		data3.add("TA_FLOW", "STATE", state);
		data3.add("TA_FLOW", "ISBACK", isBack);
		data3.add("TA_FLOW", "ISGROUP", "A");
		coreDAO.update("TA_FLOW", data3, conn);
	}
	
	/**
	 * 添加数据到流程数据表
	 * @param conn
	 * @param data
	 * @param userno
	 * @param rd
	 * @param definitionID
	 * @param groupID
	 * @throws SQLException
	 */
	private void add2FlowInfo(Connection conn, BizData data, String userno, BizData rd,String definitionID, String ddhID) throws SQLException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int flowID = queryMaxIDByPara("TA_FLOW", "FLOWID", null);
		data.add("TA_FLOW", "FLOWID", flowID);
		data.add("TA_FLOW", "DEFINITIONID", definitionID);
		data.add("TA_FLOW", "TID", ddhID);
		data.add("TA_FLOW", "CREATER", userno);
		data.add("TA_FLOW", "CREATEDATE", sdf.format(new Date()));
		data.add("TA_FLOW", "THISROLEID", rd.getStringByDI("rsNodes", "roleid", 0));
		data.add("TA_FLOW", "THISNODEID", rd.getStringByDI("rsNodes", "nodeid", 0));
		data.add("TA_FLOW", "NEXTROLEID", rd.getStringByDI("rsNodes", "croleid", 0));
		data.add("TA_FLOW", "STATE", "start");
		data.add("TA_FLOW", "NEXTNODEID", rd.getStringByDI("rsNodes", "cnodeid", 0));
		data.add("TA_FLOW", "ISBACK", "N");
		data.add("TA_FLOW", "ISGROUP", "A");
		coreDAO.insert(data, conn);
		data.remove("TA_FLOW");
	}
	
	/**
	 * 添加到审批意见表
	 * @param rd
	 * @param conn
	 * @throws SQLException 
	 */
	private void addAuthorizeMsg(String ddhID, Connection conn, String nodeID,String YJZT,String SPYJ,String userno) throws SQLException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		BizData data1 = new BizData();
		int id = queryMaxIDByPara("TA_DJ_TSPB", "ID", null);
		data1.add("TA_DJ_TSPB", "ID", id);
		data1.add("TA_DJ_TSPB", "TID", ddhID);
		data1.add("TA_DJ_TSPB", "SPR", userno);
		data1.add("TA_DJ_TSPB", "YJZT", YJZT);
		data1.add("TA_DJ_TSPB", "SPYJ", SPYJ);
		data1.add("TA_DJ_TSPB", "MKLB", "3");
		data1.add("TA_DJ_TSPB", "SHRQ", sdf.format(new Date()));
		data1.add("TA_DJ_TSPB", "NODEID", nodeID);
		coreDAO.insert("TA_DJ_TSPB", data1, conn);
		data1.remove("TA_DJ_TSPB");
	}
	
	/**
	 * 根据订单号查询审批后下级节点信息
	 * @param rd
	 * @return
	 * @throws SQLException
	 */
	private void queryNextNodeInfoByGroupID(BizData rd, String ddhID) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,n2.nodeid nextid,n2.nodename nextname,n2.roleid nextroleid\n")
			.append("from (\n")
			.append("select n.nodeid,n.nodename,n.roleid,f.flowid\n")
			.append("from ta_flow f,TA_FLOWNODE n\n")
			.append("where f.nextnodeid=n.nodeid\n")
			.append("and f.tid='"+ddhID+"' and f.definitionid='ztcwdz' and f.isgroup='A'\n")
			.append(") a,ta_flownode n2\n")
			.append("where a.nodeid=n2.pid(+)");
		coreDAO.executeQuery(sql.toString(), "rsNextNode", rd);
	}
	
	/**
	 * 根据订单号号查询流程发起人角色
	 * @param groupID
	 * @throws SQLException 
	 */
	private void queryCreateRole(BizData rd, String ddhID) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select n.roleid,n.nodeid\n")
			.append("from ta_flow f, ta_flownode n, ta_flowdefinition ff\n")
			.append("where ff.definitionid=n.definitionid and f.definitionid=ff.definitionid\n")
			.append("and n.pid=-1 and f.definitionid='ztcwdz'\n")
			.append("and f.isgroup='A'")
			.append("and f.tid='"+ddhID+"'");
		coreDAO.executeQuery(sql.toString(), "rsCreateRole", rd);
	}
	/**根据订单号查询上一级节点和角色
	 * @param rd
	 * @param groupID
	 * @throws SQLException
	 */
	private void queryOnARole(BizData rd, String ddhID) throws SQLException {
		StringBuffer sql = new StringBuffer();
		 sql.append("select f.roleid,f.nodeid from TA_FLOWNODE f where f.nodeid in( \n")
			.append("select f.pid from TA_FLOWNODE f where f.nodeid in(\n")
			.append("select t.thisnodeid from ta_flow t \n")
			.append("where t.tid='"+ddhID+"'\n")
			.append("and t.isgroup='A'\n")
			.append("and t.definitionid='ztcwdz'))");
		coreDAO.executeQuery(sql.toString(), "rsOnARole", rd);
	}

}
