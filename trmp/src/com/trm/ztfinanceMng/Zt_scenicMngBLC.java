package com.trm.ztfinanceMng;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


public class Zt_scenicMngBLC extends DBBLC {
	public Zt_scenicMngBLC(){
		this.entityName="TA_ZT_QDQKJL4JD";
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
			
			sql="select g.id,bv.qkzt,g.begin_date,g.end_date,bv.jdid,bv.qdxj, sum(case when bv.jdid=v.jdid then hkje else 0 end)as hkje, t.scenery_id, t.cmpny_name,t.cmpny_address"+
			   " from TA_ZT_BXjd bv,ta_zt_group g,TA_zt_QDQKJL4JD v,ta_scenery_point t"+
			   " where bv.tid=g.id and g.id=v.tid(+)   and bv.jdid=t.scenery_id"+
			   " and g.state='6' and bv.qdxj>'0'";
			   if (!"".equals(tid)) { // 如果团号不为空
					sql += " and g.id like '%" + tid + "%' ";
				}
			   if (!"".equals(jingdianmc)) { // 如果景点名不为空
					sql += " and t.cmpny_name like '%" + jingdianmc + "%' ";
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
			   sql+=" group by g.id,bv.qkzt,g.begin_date,g.end_date,bv.jdid,bv.qdxj,t.scenery_id,t.cmpny_name,t.cmpny_address order by bv.qkzt";
			String sql2="select sum(hkje) hkje from ta_dj_qdqkjl4jd v group by v.jdid";
			try {
				coreDAO.executeQueryPage(sql, "queryAlljingdList", pageNO, pageSize, rd);
				coreDAO.executeQuery(sql2, "hkje", rd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 5;
		}
	

	//根据团ID，景点ID得到景点签单信息
	public int getBXJingDByTid(BizData rd,BizData sd){
		String flag = rd.getString("state");
		String jdid=rd.getStringByDI("TA_ZT_QDQKJL4JD","jdID", 0);
		String tid=rd.getStringByDI("TA_ZT_QDQKJL4JD","TID", 0);
		String sql="select g.id,bv.jdid,bv.sfid,bv.csid,bv.qdxj, nvl((select sum(nvl(hkje,0))  from TA_ZT_QDQKJL4JD where tid='"+tid+"' and jdid='"+jdid+"' ),0) as hkje, bv.qdxj-nvl((select sum(nvl(hkje,0)) from TA_ZT_QDQKJL4JD where tid='"+tid+"' and jdid='"+jdid+"' ),0) ye,t.scenery_id, t.cmpny_name,t.cmpny_address"+
			   " from TA_ZT_BXjd bv,ta_ZT_group g,TA_ZT_QDQKJL4JD v,ta_scenery_point t"+
			   " where bv.tid=g.id and g.id=v.tid(+) and bv.jdid=t.scenery_id"+
			   " and g.state='6' and  bv.QKZT='N' and g.id='"+tid+"' and t.scenery_id='"+jdid+"'"+
			   " group by g.id,bv.jdid,bv.sfid,bv.csid,bv.qdxj,t.scenery_id,t.cmpny_name,t.cmpny_address";
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
		String tid = rd.getStringByDI("TA_ZT_QDQKJL4JD", "TID", 0);
		String jdid = rd.getStringByDI("TA_ZT_QDQKJL4JD", "JDID", 0);
		String ye = rd.getStringByDI("TA_ZT_QDQKJL4JD", "YE", 0);
		try {
			coreDAO.beginTrasct(conn);

			if ("0".equals(ye)) {
				int id = queryMaxIDByPara("TA_ZT_QDQKJL4JD", "ID", null);
				rd.add("TA_ZT_QDQKJL4JD", "ID", id);
				rd.add("TA_ZT_QDQKJL4JD", "qkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_ZT_QDQKJL4JD", "fkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_ZT_QDQKJL4JD", "czrq", rd.sdfDate
						.format(new Date()));
				BizData data1 = new BizData();
				data1.addAttr("TA_ZT_BXJD", "TID", 0, "oldValue", tid);
				data1.addAttr("TA_ZT_BXJD", "JDID", 0, "oldValue", jdid);
				data1.add("TA_ZT_BXJD", "QKZT", "Y");
				coreDAO.insert("TA_ZT_QDQKJL4JD", rd, conn);
				coreDAO.update("TA_ZT_BXJD", data1, conn);
			}

			else {
				int id = queryMaxIDByPara("TA_ZT_QDQKJL4JD", "ID", null);
				rd.add("TA_ZT_QDQKJL4JD", "ID", id);
				rd.add("TA_ZT_QDQKJL4JD", "fkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_ZT_QDQKJL4JD", "czrq", rd.sdfDate
						.format(new Date()));
				coreDAO.insert("TA_ZT_QDQKJL4JD", rd, conn);
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
		String jdid=rd.getStringByDI("TA_ZT_QDQKJL4JD","JDID", 0);
		String tid=rd.getStringByDI("TA_ZT_QDQKJL4JD","TID", 0);
		String sql="select a.tid,a.hkje,a.ye,a.jdid,a.czRQ, b.qdxj,c.cmpny_name,c.cmpny_address"+
		    " from TA_ZT_QDQKJL4JD a,TA_ZT_BXjd b,ta_scenery_point c"+
		    " where a.tid=b.tid and a.jdid=c.scenery_id and b.jdid='"+jdid+"' and c.scenery_id='"+jdid+"' and a.tid='"+tid+"' order by a.id";
		try {
			coreDAO.executeQuery(sql,"jingdlist", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
}