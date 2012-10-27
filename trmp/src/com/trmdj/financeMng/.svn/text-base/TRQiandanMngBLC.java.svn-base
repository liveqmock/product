package com.trmdj.financeMng;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


public class TRQiandanMngBLC extends DBBLC {
	public TRQiandanMngBLC(){
		this.entityName="TA_DJ_QKJL4TRA";
	}
//查询未清票务签单列表	
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
			
			sql="select bv.qkzt,g.id,g.begin_date,g.end_date, sum(nvl(bv.qdje, 0)) qdje,bv.djsid, bv.djsmc, nvl(v.hkje,0) hkje \n" +
				" from (select v.tid,v.djs,sum(nvl(v.hkje,0)) hkje \n" +
				"from TA_DJ_QKJL4TRA v where v.orgid='"+sd.getString("orgid")+"'\n" +
				"group by v.tid,v.djs) v, ta_dj_bxdj bv,ta_dj_group g \n" +
				"where bv.tid=g.id \n" +
				"and v.tid(+)=bv.tid \n" +
				"and v.djs(+)=bv.djsid \n" +
				"and g.state='6' \n" +
				"and  bv.qdje>0 and g.orgid='"+sd.getString("orgid")+"'\n";
			if (!"".equals(tid)) { // 如果团号不为空
				sql += " and g.id like '%" + tid + "%' ";
			}
			if (!"".equals(hzsmc)) { // 如果代购点名不为空
				sql += " and bv.djsmc like '%" + hzsmc + "%' ";
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
		    sql+=" group by g.id,bv.qkzt,g.begin_date,g.end_date,bv.djsid, bv.djsmc,v.hkje order by bv.qkzt";
			try {
				coreDAO.executeQueryPage(sql, "queryAllTRList", pageNO, pageSize, rd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 3;
		}
	

	//根据团ID，地接社ID得到票务签单信息
	public int getBXTRByTid(BizData rd,BizData sd){
		String djsid=rd.getStringByDI("TA_DJ_QKJL4TRA","DJS", 0);
		String tid=rd.getStringByDI("TA_DJ_QKJL4TRA","TID", 0);
		String sql= "select bv.qkzt,bv.xzqhid,g.id,g.begin_date,g.end_date, sum(nvl(bv.qdje, 0)) qdje,bv.djsid, bv.djsmc, nvl(v.hkje,0) hkje,nvl((sum(nvl(bv.qdje,0))- nvl(v.hkje,0)),0) ye \n" +
					" from (select v.tid,v.djs,sum(nvl(v.hkje,0)) hkje \n" +
					"from TA_DJ_QKJL4TRA v where v.orgid='"+sd.getString("orgid")+"'\n" +
					"group by v.tid,v.djs) v, ta_dj_bxdj bv,ta_dj_group g \n" +
					"where bv.tid=g.id \n" +
					"and v.tid(+)=bv.tid \n" +
					"and v.djs(+)=bv.djsid \n" +
					"and g.state='6' \n" +
					"and  bv.qdje>0 \n" +
					"and g.id='"+tid+"' \n" +
					"and bv.djsid='"+djsid+"' and g.orgid='"+sd.getString("orgid")+"'\n" +
					" group by g.id,bv.qkzt,bv.xzqhid,g.begin_date,g.end_date,bv.djsid, bv.djsmc,v.hkje \n" +
					"order by bv.qkzt";
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
		String tid = rd.getStringByDI("TA_DJ_QKJL4TRA", "TID", 0);
		String djsid = rd.getStringByDI("TA_DJ_QKJL4TRA", "DJS", 0);
		String ye = rd.getStringByDI("TA_DJ_QKJL4TRA", "YE", 0);
		try {
			coreDAO.beginTrasct(conn);

			if ("0".equals(ye)) {
				int id = queryMaxIDByPara("TA_DJ_QKJL4TRA", "ID", null);
				rd.add("TA_DJ_QKJL4TRA", "ID", id);
				rd.add("TA_DJ_QKJL4TRA", "qkrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QKJL4TRA", "fkrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QKJL4TRA", "czrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QKJL4TRA", "orgid",sd.getString("orgid"));
				BizData data1 = new BizData();
				data1.addAttr("TA_DJ_BXDJ", "TID", 0, "oldValue", tid);
				data1.addAttr("TA_DJ_BXDJ", "DJSID", 0, "oldValue", djsid);
				data1.addAttr("TA_DJ_BXDJ", "orgid", 0, "oldValue", sd.getString("orgid"));
				data1.add("TA_DJ_BXDJ", "QKZT", "Y");
				coreDAO.insert("TA_DJ_QKJL4TRA", rd, conn);
				coreDAO.update("TA_DJ_BXDJ", data1, conn);
			}

			else {
				int id = queryMaxIDByPara("TA_DJ_QKJL4TRA", "ID", null);
				rd.add("TA_DJ_QKJL4TRA", "ID", id);
				rd.add("TA_DJ_QKJL4TRA", "fkrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QKJL4TRA", "czrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QKJL4TRA", "orgid",sd.getString("orgid"));
				coreDAO.insert("TA_DJ_QKJL4TRA", rd, conn);
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
		String djs=rd.getStringByDI("TA_DJ_QKJL4TRA","DJS", 0);
		String tid=rd.getStringByDI("TA_DJ_QKJL4TRA","TID", 0);
		String sql="select a.tid,nvl(a.hkje,0) hkje,a.ye,a.djs,a.czRQ, b.qdje,b.djsmc"+
		      " from TA_DJ_QKJL4TRA a,TA_DJ_BXDJ b"+
		      " where a.tid=b.tid \n" + 
		      "and b.djsid='"+djs+"' \n" +
		      "and a.tid='"+tid+"' and a.orgid='"+sd.getString("orgid")+"'\n" +
		      "order by a.id";
		try {
			coreDAO.executeQuery(sql, "trlist", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
}