package com.trmdj.financeMng;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


public class JingdQiandanMngBLC extends DBBLC {
	public JingdQiandanMngBLC(){
		this.entityName="TA_DJ_QDQKJL4JD";
	}
	public int AllJingDList(BizData rd, BizData sd){
			String state = rd.getString("state");
			String bdate=rd.getString("bdate");
			String edate=rd.getString("edate");
			String tid=rd.getString("groupID");
			String jingdianmc=rd.getString("jingdianmc");
			String pageNOStr = rd.getString("pageNO");
			String pageSizeStr = rd.getString("pageSize");
			int pageNO = Integer.parseInt(pageNOStr);
			int pageSize = Integer.parseInt(pageSizeStr);
			String sql="";
			sql="select bv.qkzt,g.id,g.begin_date,g.end_date, sum(nvl(bv.qdje, 0)) qdje,bv.jdid, bv.jdmc, nvl(v.hkje,0) hkje \n" +
				" from (select v.tid,v.jdid,sum(nvl(v.hkje,0)) hkje \n" +
				"from TA_DJ_QDQKJL4JD v where v.orgid='"+sd.getString("orgid")+"'\n" +
				"group by v.tid,v.jdid) v, ta_dj_bxjd bv,ta_dj_group g \n" +
				"where bv.tid=g.id \n" +
				"and v.tid(+)=bv.tid \n" +
				"and v.jdid(+)=bv.jdid \n" +
				"and g.state='6' \n" +
				"and  bv.qdje>0 and g.orgid='"+sd.getString("orgid")+"'\n";
			   if (!"".equals(tid)) { // 如果团号不为空
					sql += " and g.id like '%" + tid + "%' ";
				}
			   if (!"".equals(jingdianmc)) { // 如果景点名不为空
					sql += " and bv.jdmc like '%" + jingdianmc + "%' ";
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
			   sql+=" group by g.id,bv.qkzt,g.begin_date,g.end_date,bv.jdid, bv.jdmc,v.hkje \n"+
					"order by bv.qkzt";
			try {
				coreDAO.executeQueryPage(sql, "queryAlljingdList", pageNO, pageSize, rd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 5;
		}
	

	//根据团ID，景点ID得到景点签单信息
	public int getBXJingDByTid(BizData rd,BizData sd){
		String jdid=rd.getStringByDI("TA_DJ_QDQKJL4JD","jdID", 0);
		String tid=rd.getStringByDI("TA_DJ_QDQKJL4JD","TID", 0);
		String sql= "select bv.qkzt,g.id,g.begin_date,g.end_date,bv.jdid,bv.xzqhid, sum(nvl(bv.qdje,0)) qdje, nvl(v.hkje,0) hkje, nvl((sum(nvl(bv.qdje,0))- nvl(v.hkje,0)),0) ye, bv.jdmc \n" + 
					" from (select v.tid,v.jdid,sum(nvl(v.hkje,0)) hkje from TA_DJ_QDQKJL4JD v where v.orgid='"+sd.getString("orgid")+"' group by v.tid,v.jdid) v, TA_DJ_BXjd bv,ta_dj_group g \n" +
					"where bv.tid=g.id \n" +
					"and v.tid(+)=bv.tid \n" +
					"and v.jdid(+)=bv.jdid \n" +
					"and g.state='6' \n" +
					"and bv.QDJE>0 \n" +
					"and g.id='"+tid+"' \n" +
					"and bv.jdid='"+jdid+"' and g.orgid='"+sd.getString("orgid")+"'\n" +
					" group by g.id,bv.qkzt,g.begin_date,g.end_date,bv.jdid,bv.xzqhid,v.hkje,bv.jdmc order by bv.qkzt";
		try {
			coreDAO.executeQuery(sql, "selectBxJingD", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
}
//添加清款	
	public int addJingDqingdan(BizData rd, BizData sd) throws SQLException {
		Connection conn = coreDAO.getConnection();
		String tid = rd.getStringByDI("TA_DJ_QDQKJL4JD", "TID", 0);
		String jdid = rd.getStringByDI("TA_DJ_QDQKJL4JD", "JDID", 0);
		String ye = rd.getStringByDI("TA_DJ_QDQKJL4JD", "YE", 0);
		try {
			coreDAO.beginTrasct(conn);

			if ("0".equals(ye)) {
				int id = queryMaxIDByPara("TA_DJ_QDQKJL4JD", "ID", null);
				rd.add("TA_DJ_QDQKJL4JD", "ID", id);
				rd.add("TA_DJ_QDQKJL4JD", "qkrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4JD", "fkrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4JD", "czrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4JD", "orgid",sd.getString("orgid"));
				BizData data1 = new BizData();
				data1.addAttr("TA_DJ_BXJD", "TID", 0, "oldValue", tid);
				data1.addAttr("TA_DJ_BXJD", "JDID", 0, "oldValue", jdid);
				data1.addAttr("TA_DJ_BXJD", "orgid", 0, "oldValue", sd.getString("orgid"));
				data1.add("TA_DJ_BXJD", "QKZT", "Y");
				coreDAO.insert("TA_DJ_QDQKJL4JD", rd, conn);
				coreDAO.update("TA_DJ_BXJD", data1, conn);
			}

			else {
				int id = queryMaxIDByPara("TA_DJ_QDQKJL4JD", "ID", null);
				rd.add("TA_DJ_QDQKJL4JD", "ID", id);
				rd.add("TA_DJ_QDQKJL4JD", "fkrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4JD", "czrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4JD", "orgid",sd.getString("orgid"));
				coreDAO.insert("TA_DJ_QDQKJL4JD", rd, conn);
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
//查看景点清款列表	
	public int queryJingDlist(BizData rd,BizData sd){
		String jdid=rd.getStringByDI("TA_DJ_QDQKJL4JD","JDID", 0);
		String tid=rd.getStringByDI("TA_DJ_QDQKJL4JD","TID", 0);
		String sql="select a.tid,a.hkje,a.ye,a.jdid,a.czRQ, b.qdje,b.jdmc \n" +
		    " from TA_DJ_QDQKJL4JD a,TA_DJ_BXjd b \n" +
		    "where a.tid=b.tid \n" +
		    "and b.jdid='"+jdid+"' \n" +
		    "and a.tid='"+tid+"' and a.orgid='"+sd.getString("orgid")+"'\n" +
		    "order by a.id";
		try {
			coreDAO.executeQuery(sql,"jingdlist", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
}