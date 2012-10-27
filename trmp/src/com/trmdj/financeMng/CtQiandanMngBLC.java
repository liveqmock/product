package com.trmdj.financeMng;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


public class CtQiandanMngBLC extends DBBLC {
	public CtQiandanMngBLC(){
		this.entityName="TA_DJ_QDQKJL4CT";
	}
	public int allCTList(BizData rd, BizData sd){
		
		String state = rd.getString("state");
		String bdate=rd.getString("bdate");
		String edate=rd.getString("edate");
			String tid=rd.getString("groupID");
			String ctmc=rd.getString("ctmc");
			String pageNOStr = rd.getString("pageNO");
			String pageSizeStr = rd.getString("pageSize");
			int pageNO = Integer.parseInt(pageNOStr);
			int pageSize = Integer.parseInt(pageSizeStr);
			String sql="";
			
			sql="select bv.qkzt,g.id,g.begin_date,g.end_date, sum(nvl(bv.qdje, 0)) qdje,bv.ct, bv.ctmc, nvl(v.hkje,0) hkje \n" +
				" from (select v.tid,v.ctid,sum(nvl(v.hkje,0)) hkje \n" +
				"from TA_DJ_QDQKJL4CT v where v.orgid='"+sd.getString("orgid")+"'\n" +
				"group by v.tid,v.ctid) v, TA_DJ_BXCT bv,ta_dj_group g \n" +
				"where bv.tid=g.id \n" +
				"and v.tid(+)=bv.tid \n" +
				"and v.ctid(+)=bv.ct \n" +
				"and g.state='6' \n" +
				"and  bv.qdje>0 and g.orgid='"+sd.getString("orgid")+"'\n";
			  if (!"".equals(tid)) { // 如果团号不为空
				  sql += "and g.id like '%" + tid + "%' ";
			  }
			  if (!"".equals(ctmc)) { // 如果餐厅名不为空
				  sql += "and bv.ctmc like '%" + ctmc + "%' ";
			  }
			  if (!"".equals(bdate)) { // 如果开团时间不为空
				  sql += "and g.BEGIN_DATE >= to_date('" + bdate + "','yyyy-mm-dd') \n";
			  }
			  if (!"".equals(edate)) { // 如果开团时间不为空
				  sql += "and g.end_DATE <= to_date('" + edate + "','yyyy-mm-dd') \n";
			  }
			  if (!"".equals(state)) { 
				  sql += " and  bv.qkzt='"+state+"'\n";
			  }
			  sql += " group by g.id,bv.qkzt,g.begin_date,g.end_date,bv.ct,bv.ctmc,v.hkje \n" +
					 "order by bv.qkzt";
			try {
				coreDAO.executeQueryPage(sql, "queryAllctList", pageNO, pageSize, rd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 4;
		}
	

	public int getBXCTByTid(BizData rd,BizData sd){
		String ctid=rd.getStringByDI("TA_DJ_QDQKJL4CT","CTID", 0);
		String tid=rd.getStringByDI("TA_DJ_QDQKJL4CT","TID", 0);
		String sql="select bv.qkzt,g.id,g.begin_date,g.end_date,bv.xzqhid, sum(nvl(bv.qdje, 0)) qdje,bv.ct, bv.ctmc, nvl(v.hkje,0) hkje,nvl((sum(nvl(bv.qdje,0))- nvl(v.hkje,0)),0) ye \n" +
					" from (select v.tid,v.ctid,sum(nvl(v.hkje,0)) hkje \n" +
					"from TA_DJ_QDQKJL4CT v where v.orgid='"+sd.getString("orgid")+"'\n" +
					"group by v.tid,v.ctid) v, TA_DJ_BXCT bv,ta_dj_group g \n" +
					"where bv.tid=g.id \n" +
					"and v.tid(+)=bv.tid \n" +
					"and v.ctid(+)=bv.ct \n" +
					"and g.state='6' \n" +
					"and bv.qdje>0 \n" +
					"and g.id='"+tid+"' \n" +
					"and bv.ct='"+ctid+"' and g.orgid='"+sd.getString("orgid")+"'\n" +
					" group by g.id,bv.qkzt,g.begin_date,g.end_date,bv.xzqhid,v.hkje,bv.ct, bv.ctmc,v.hkje \n" +
					"order by bv.qkzt";
		try {
			coreDAO.executeQuery(sql, "selectBxCT", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
}
	
	public int addCTqingdan(BizData rd, BizData sd) throws SQLException {
		Connection conn = coreDAO.getConnection();
		String tid = rd.getStringByDI("TA_DJ_QDQKJL4CT", "TID", 0);
		String ctid = rd.getStringByDI("TA_DJ_QDQKJL4CT", "CTID", 0);
		String ye = rd.getStringByDI("TA_DJ_QDQKJL4CT", "YE", 0);

		try {
			coreDAO.beginTrasct(conn);

			if ("0".equals(ye)) {
				int id = queryMaxIDByPara("TA_DJ_QDQKJL4CT", "ID", null);
				rd.add("TA_DJ_QDQKJL4CT", "ID", id);
				rd.add("TA_DJ_QDQKJL4CT", "qkrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4CT", "fkrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4CT", "czrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4CT", "orgid",sd.getString("orgid"));
				BizData data1 = new BizData();
				data1.addAttr("TA_DJ_BXCT", "TID", 0, "oldValue", tid);
				data1.addAttr("TA_DJ_BXCT", "CT", 0, "oldValue", ctid);
				data1.addAttr("TA_DJ_BXCT", "orgid", 0, "oldValue", sd.getString("orgid"));
				data1.add("TA_DJ_BXCT", "QKZT", "Y");
				coreDAO.insert("TA_DJ_QDQKJL4CT", rd, conn);
				coreDAO.update("TA_DJ_BXCT", data1, conn);
			}

			else {
				int id = queryMaxIDByPara("TA_DJ_QDQKJL4CT", "ID", null);
				rd.add("TA_DJ_QDQKJL4CT", "ID", id);
				rd.add("TA_DJ_QDQKJL4CT", "TID", tid);
				rd.add("TA_DJ_QDQKJL4CT", "fkrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4CT", "czrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4CT", "orgid",sd.getString("orgid"));
				coreDAO.insert("TA_DJ_QDQKJL4CT", rd, conn);
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
	
	public int queryCTlist(BizData rd,BizData sd){
		String ctid=rd.getStringByDI("TA_DJ_QDQKJL4CT","CTID", 0);
		String tid=rd.getStringByDI("TA_DJ_QDQKJL4CT","TID", 0);
		String sql="select a.tid,a.hkje,a.ye,a.ctid,a.ye,a.czRQ, b.qdje,b.ctmc \n" +
				   " from TA_DJ_QDQKJL4CT a,TA_DJ_BXCT b \n" +
				   "where a.tid=b.tid \n" +
				   "and b.ct='"+ctid+"' \n" +
				   "and a.tid='"+tid+"' \n" +
				   "and  b.QDJE>0 and a.orgid='"+sd.getString("orgid")+"'\n" +
				   "order by a.id";
		try {
			coreDAO.executeQuery(sql, "ctlist", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
}