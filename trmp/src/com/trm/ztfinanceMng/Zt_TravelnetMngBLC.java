package com.trm.ztfinanceMng;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


public class Zt_TravelnetMngBLC extends DBBLC {
	public Zt_TravelnetMngBLC(){
		this.entityName="TA_ZT_QKJL4TRA";
	}
	
	public int AllTRList(BizData rd, BizData sd){
			
			String state = rd.getString("state");
			rd.add("state", state);
			String bdate=rd.getString("bdate");
			String edate=rd.getString("edate");
			String tid=rd.getString("groupID");
			String hzsmc=rd.getString("hzsmc");
			String pageNOStr = rd.getString("pageNO");
			String pageSizeStr = rd.getString("pageSize");
			int pageNO = Integer.parseInt(pageNOStr);
			int pageSize = Integer.parseInt(pageSizeStr);
			String sql="";
			
			sql="select bx.qkzt,g.begin_date,g.end_date,bx.tid,bx.djsid,bx.qdje,t.cmpny_name, sum(case when bx.djsid=qk.djs  then hkje else 0 end)as hkje"+
				" from ta_zt_bxdj bx,TA_ZT_GROUP g ,ta_travelagency t ,TA_zt_QKJL4TRA qk"+
				" where g.id=bx.tid and bx.djsid=t.travel_agc_id and g.id=qk.tid(+)  and  g.state='6'  and bx.qdje>'0'";
			if (!"".equals(tid)) { // 如果团号不为空
				sql += " and g.id like '%" + tid + "%' ";
			}
			if (!"".equals(hzsmc)) { 
				sql += " and t.cmpny_name like '%" + hzsmc + "%' ";
			}
			  if (!"".equals(bdate)) { // 如果开团时间不为空
					sql += "and g.BEGIN_DATE >= to_date('" + bdate + "','yyyy-mm-dd') \n";
				}
			  if (!"".equals(edate)) { // 如果开团时间不为空
					sql += "and g.end_DATE <= to_date('" + edate + "','yyyy-mm-dd') \n";
				}
				if (!"".equals(state)) { 
					sql += " and  bx.qkzt='"+state+"'\n";
				}
			    sql+=" group by  bx.qkzt,g.begin_date,g.end_date,bx.tid,bx.djsid,bx.qdje,t.cmpny_name order by bx.qkzt";
			try {
				coreDAO.executeQueryPage(sql, "queryAllTRList", pageNO, pageSize, rd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 3;
		}
	

	//根据团ID，地接社ID得到票务签单信息
	public int getBXTRByTid(BizData rd,BizData sd){
		String flag = rd.getString("state");
		String djsid=rd.getStringByDI("TA_ZT_QKJL4TRA","DJS", 0);
		String tid=rd.getStringByDI("TA_ZT_QKJL4TRA","TID", 0);
		String sql="select bx.tid,bx.sfid,bx.csid,bx.djsid,bx.qdje,t.cmpny_name,"+
			" nvl((select sum(nvl(hkje,0))  from TA_ZT_QKJL4TRA where tid='"+tid+"' and djs='"+djsid+"' ),0) as hkje,"+
			" bx.qdje-nvl((select sum(nvl(hkje,0))  from TA_ZT_QKJL4TRA where tid='"+tid+"' and djs='"+djsid+"' ),0) ye"+
			" from ta_zt_bxdj bx,TA_ZT_GROUP g ,ta_travelagency t ,TA_ZT_QKJL4TRA qk"+
			" where g.id=bx.tid and bx.djsid=t.travel_agc_id and g.id=qk.tid(+) and bx.qkzt='N' and  g.state='6'"+ 
			" and g.id='"+tid+"' and bx.djsid='"+djsid+"'"+
			" group by bx.tid,bx.djsid,bx.sfid,bx.csid,bx.qdje,t.cmpny_name";
		try {
			coreDAO.executeQuery(sql, "selectBxTR", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
}
//添加合作社清款	
	public int addTRqingdan(BizData rd, BizData sd) throws SQLException {
		Connection conn = coreDAO.getConnection();
		String tid = rd.getStringByDI("TA_ZT_QKJL4TRA", "TID", 0);
		String djsid = rd.getStringByDI("TA_ZT_QKJL4TRA", "DJS", 0);
		String ye = rd.getStringByDI("TA_ZT_QKJL4TRA", "YE", 0);
		try {
			coreDAO.beginTrasct(conn);

			if ("0".equals(ye)) {
				int id = queryMaxIDByPara("TA_ZT_QKJL4TRA", "ID", null);
				rd.add("TA_ZT_QKJL4TRA", "ID", id);
				rd.add("TA_ZT_QKJL4TRA", "qkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_ZT_QKJL4TRA", "fkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_ZT_QKJL4TRA", "czrq", rd.sdfDate
						.format(new Date()));
				BizData data1 = new BizData();
				data1.addAttr("TA_ZT_BXDJ", "TID", 0, "oldValue", tid);
				data1.addAttr("TA_ZT_BXDJ", "DJSID", 0, "oldValue", djsid);
				data1.add("TA_ZT_BXDJ", "QKZT", "Y");
				coreDAO.insert("TA_ZT_QKJL4TRA", rd, conn);
				coreDAO.update("TA_ZT_BXDJ", data1, conn);
			}

			else {
				int id = queryMaxIDByPara("TA_ZT_QKJL4TRA", "ID", null);
				rd.add("TA_ZT_QKJL4TRA", "ID", id);
				rd.add("TA_ZT_QKJL4TRA", "fkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_ZT_QKJL4TRA", "czrq", rd.sdfDate
						.format(new Date()));
				coreDAO.insert("TA_ZT_QKJL4TRA", rd, conn);
			}
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

		return 1;
	}
//查看合作社清款列表	
	public int queryTRlist(BizData rd,BizData sd){
		String djs=rd.getStringByDI("TA_ZT_QKJL4TRA","DJS", 0);
		String tid=rd.getStringByDI("TA_ZT_QKJL4TRA","TID", 0);
		String sql="select a.tid,nvl(a.hkje,0) hkje,a.ye,a.djs,a.czRQ, b.qdje,c.cmpny_name,c.cmpny_address"+
		      " from TA_ZT_QKJL4TRA a,TA_ZT_BXDJ b,ta_travelagency c"+
		      " where a.tid=b.tid and a.djs=c.travel_agc_id  and  c.travel_agc_id='"+djs+"' and b.djsid='"+djs+"' and a.tid='"+tid+"' order by a.id";
		try {
			coreDAO.executeQuery(sql, "trlist", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
}