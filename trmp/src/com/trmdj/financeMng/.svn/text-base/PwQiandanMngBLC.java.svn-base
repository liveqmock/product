package com.trmdj.financeMng;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


public class PwQiandanMngBLC extends DBBLC {
	public PwQiandanMngBLC(){
		this.entityName="TA_DJ_QDQKJL4PW";
	}
//查询未清票务签单列表	
	public int AllPWList(BizData rd, BizData sd){
				
			String state = rd.getString("state");
			String bdate=rd.getString("bdate");
			String edate=rd.getString("edate");
			String tid=rd.getString("groupID");
			String dgdmc=rd.getString("dgdmc");
			String pageNOStr = rd.getString("pageNO");
			String pageSizeStr = rd.getString("pageSize");
			int pageNO = Integer.parseInt(pageNOStr);
			int pageSize = Integer.parseInt(pageSizeStr);
			String sql="";
			
			sql="select bv.qkzt,g.id,g.begin_date,g.end_date, sum(nvl(bv.qd, 0)) qd,bv.dgd, bv.dgdmc, nvl(v.hkje,0) hkje \n" +
				" from (select v.tid,v.dgdid,sum(nvl(v.hkje,0)) hkje \n" +
				"from TA_DJ_QDQKJL4PW v where v.orgid='"+sd.getString("orgid")+"'\n" +
				"group by v.tid,v.dgdid) v, TA_DJ_BXPW bv,ta_dj_group g \n" +
				"where bv.tid=g.id \n" +
				"and v.tid(+)=bv.tid \n" +
				"and v.dgdid(+)=bv.dgd \n" +
				"and g.state='6' \n" +
				"and  bv.qd>0 and g.orgid='"+sd.getString("orgid")+"'\n";
			if (!"".equals(tid)) { // 如果团号不为空
				sql += " and g.id like '%" + tid + "%' ";
			}
			if (!"".equals(dgdmc)) { // 如果代购点名不为空
				sql += " and bv.dgdmc like '%" + dgdmc + "%' ";
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
		    	sql+=" group by g.id,bv.qkzt,g.begin_date,g.end_date,bv.dgd,bv.qd,bv.dgdmc,v.hkje \n" +
		    		 "order by bv.qkzt";
			try {
				coreDAO.executeQueryPage(sql, "queryAllPWList", pageNO, pageSize, rd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 33;
		}
	
	public int getHqiandanByTID(BizData rd,BizData sd){
		
		rd.add("TID", rd.getStringByDI("TA_DJ_BXHOTEL", "TID", 0));
		try {
			 query(rd, sd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 1;
	}
	//根据团ID，票务点ID得到票务签单信息
	public int getBXPWByTid(BizData rd,BizData sd){
		String dgd=rd.getStringByDI("TA_DJ_QDQKJL4PW","DGDID", 0);
		String tid=rd.getStringByDI("TA_DJ_QDQKJL4PW","TID", 0);
		String sql="select bv.qkzt,g.id,g.begin_date,g.end_date, sum(nvl(bv.qd, 0)) qd,bv.dgd, bv.dgdmc, nvl(v.hkje,0) hkje,nvl((sum(nvl(bv.qd,0))- nvl(v.hkje,0)),0) ye \n" +
					" from (select v.tid,v.dgdid,sum(nvl(v.hkje,0)) hkje \n" +
					"from TA_DJ_QDQKJL4PW v where v.orgid='"+sd.getString("orgid")+"'\n" +
					"group by v.tid,v.dgdid) v, TA_DJ_BXPW bv,ta_dj_group g \n" +
					"where bv.tid=g.id \n" +
					"and v.tid(+)=bv.tid \n" +
					"and v.dgdid(+)=bv.dgd \n" +
					"and g.state='6' \n" +
					"and  bv.qd>0 \n" +
					"and g.id='"+tid+"' \n" +
					"and bv.dgd='"+dgd+"' and g.orgid='"+sd.getString("orgid")+"'\n" +
					" group by g.id,bv.qkzt,g.begin_date,g.end_date,bv.dgd,bv.qd,bv.dgdmc,v.hkje \n" +
					"order by bv.qkzt";
		try {
			coreDAO.executeQuery(sql, "selectBxPW", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
}
//添加票务清款	
	public int addPWqingdan(BizData rd, BizData sd) throws SQLException {
		Connection conn = coreDAO.getConnection();
		String tid = rd.getStringByDI("TA_DJ_QDQKJL4PW", "TID", 0);
		String dgdid = rd.getStringByDI("TA_DJ_QDQKJL4PW", "DGDID", 0);
		String ye = rd.getStringByDI("TA_DJ_QDQKJL4PW", "YE", 0);
		try {
			coreDAO.beginTrasct(conn);

			if ("0".equals(ye)) {
				int id = queryMaxIDByPara("TA_DJ_QDQKJL4PW", "ID", null);
				rd.add("TA_DJ_QDQKJL4PW", "ID", id);
				rd.add("TA_DJ_QDQKJL4PW", "qkrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4PW", "fkrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4PW", "czrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4PW", "orgid",sd.getString("orgid"));
				BizData data1 = new BizData();
				data1.addAttr("TA_DJ_BXPW", "TID", 0, "oldValue", tid);
				data1.addAttr("TA_DJ_BXPW", "DGD", 0, "oldValue", dgdid);
				data1.addAttr("TA_DJ_BXPW", "orgid", 0, "oldValue", sd.getString("orgid"));
				data1.add("TA_DJ_BXPW", "QKZT", "Y");
				coreDAO.insert("TA_DJ_QDQKJL4PW", rd, conn);
				coreDAO.update("TA_DJ_BXPW", data1, conn);
			}

			else {
				int id = queryMaxIDByPara("TA_DJ_QDQKJL4PW", "ID", null);
				rd.add("TA_DJ_QDQKJL4PW", "ID", id);
				rd.add("TA_DJ_QDQKJL4PW", "fkrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4PW", "czrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4PW", "orgid",sd.getString("orgid"));
				coreDAO.insert("TA_DJ_QDQKJL4PW", rd, conn);
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
//查看票务清款列表	
	public int queryPWlist(BizData rd,BizData sd){
		String dgd=rd.getStringByDI("TA_DJ_QDQKJL4PW","DGDID", 0);
		String tid=rd.getStringByDI("TA_DJ_QDQKJL4PW","TID", 0);
		String sql=  "select a.tid,nvl(a.hkje,0) hkje,a.ye,a.dgdid,a.czRQ, b.qd,b.dgdmc \n" +
				     " from TA_DJ_QDQKJL4PW a,TA_DJ_BXPW b \n" +
				     "where a.tid=b.tid \n" +
				     "and b.dgd='"+dgd+"' \n" +
				     "and a.tid='"+tid+"' and a.orgid='"+sd.getString("orgid")+"'\n" +
				     "order by a.id";
		try {
			coreDAO.executeQuery(sql, "pwlist", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
}