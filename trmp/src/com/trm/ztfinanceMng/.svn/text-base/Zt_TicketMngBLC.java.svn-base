package com.trm.ztfinanceMng;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


public class Zt_TicketMngBLC extends DBBLC {
	public Zt_TicketMngBLC(){
		this.entityName="TA_ZT_QDQKJL4PW";
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
			
			sql="select g.id,bv.qkzt,g.begin_date,g.end_date,bv.dgd,bv.qdxj, sum(case when bv.dgd=v.dgdid then hkje else 0 end)as hkje, t.cmpny_name,t.cmpny_address"+
			    " from TA_ZT_BXPW bv,ta_zt_group g,TA_ZT_QDQKJL4PW v,ta_ticket t"+
			    " where bv.tid=g.id and g.id=v.tid(+)   and  bv.dgd=t.ticket_id"+
			    " and g.state='6' and bv.qdxj>'0'";
			if (!"".equals(tid)) { // 如果团号不为空
				sql += " and g.id like '%" + tid + "%' ";
			}
			if (!"".equals(dgdmc)) { // 如果代购点名不为空
				sql += " and t.cmpny_name like '%" + dgdmc + "%' ";
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
			    sql+=" group by g.id,bv.qkzt,g.begin_date,g.end_date,bv.dgd,bv.qdxj,t.cmpny_name,t.cmpny_address order by bv.qkzt";
			try {
				coreDAO.executeQueryPage(sql, "queryAllPWList", pageNO, pageSize, rd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.print(sql);
			return 33;
		}
	
	public int getHqiandanByTID(BizData rd,BizData sd){
		
		rd.add("TID", rd.getStringByDI("TA_ZT_BXHOTEL", "TID", 0));
		try {
			 query(rd, sd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 1;
	}
	//根据团ID，票务点ID得到票务签单信息
	public int getBXPWByTid(BizData rd,BizData sd){
		String flag = rd.getString("state");
		String dgd=rd.getStringByDI("TA_ZT_QDQKJL4PW","DGDID", 0);
		String tid=rd.getStringByDI("TA_ZT_QDQKJL4PW","TID", 0);
		String sql="select g.id,bv.sf,bv.city,bv.dgd,bv.qdxj, nvl((select sum(nvl(hkje,0))  from TA_ZT_QDQKJL4PW where tid='"+tid+"' and dgdid='"+dgd+"' ),0) as hkje, bv.qdxj-nvl((select sum(nvl(hkje,0))  from TA_ZT_QDQKJL4PW where tid='"+tid+"' and dgdid='"+dgd+"' ),0) ye, t.cmpny_name,t.cmpny_address"+
			    " from TA_ZT_BXPW bv,ta_ZT_group g,TA_ZT_QDQKJL4PW v,ta_ticket t"+
			    " where bv.tid=g.id and g.id=v.tid(+)   and  bv.dgd=t.ticket_id"+ 
			    " and g.state='6' and  bv.QKZT='N' and g.id='"+tid+"' and bv.dgd='"+dgd+"'"+
			    " group by g.id,bv.sf,bv.city,bv.dgd,bv.qdxj,t.cmpny_name,t.cmpny_address";
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
		String tid = rd.getStringByDI("TA_ZT_QDQKJL4PW", "TID", 0);
		String dgdid = rd.getStringByDI("TA_ZT_QDQKJL4PW", "DGDID", 0);
		String ye = rd.getStringByDI("TA_ZT_QDQKJL4PW", "YE", 0);
		try {
			coreDAO.beginTrasct(conn);

			if ("0".equals(ye)) {
				int id = queryMaxIDByPara("TA_ZT_QDQKJL4PW", "ID", null);
				rd.add("TA_ZT_QDQKJL4PW", "ID", id);
				rd.add("TA_ZT_QDQKJL4PW", "qkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_ZT_QDQKJL4PW", "fkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_ZT_QDQKJL4PW", "czrq", rd.sdfDate
						.format(new Date()));
				BizData data1 = new BizData();
				data1.addAttr("TA_ZT_BXPW", "TID", 0, "oldValue", tid);
				data1.addAttr("TA_ZT_BXPW", "DGD", 0, "oldValue", dgdid);
				data1.add("TA_ZT_BXPW", "QKZT", "Y");
				coreDAO.insert("TA_ZT_QDQKJL4PW", rd, conn);
				coreDAO.update("TA_ZT_BXPW", data1, conn);
			}

			else {
				int id = queryMaxIDByPara("TA_ZT_QDQKJL4PW", "ID", null);
				rd.add("TA_ZT_QDQKJL4PW", "ID", id);
				rd.add("TA_ZT_QDQKJL4PW", "fkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_ZT_QDQKJL4PW", "czrq", rd.sdfDate
						.format(new Date()));
				coreDAO.insert("TA_ZT_QDQKJL4PW", rd, conn);
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
		String dgd=rd.getStringByDI("TA_ZT_QDQKJL4PW","DGDID", 0);
		String tid=rd.getStringByDI("TA_ZT_QDQKJL4PW","TID", 0);
		String sql="select a.tid,nvl(a.hkje,0) hkje,a.ye,a.dgdid,a.czRQ, b.qdxj,c.cmpny_name,c.cmpny_address"+
		     " from TA_ZT_QDQKJL4PW a,TA_ZT_BXPW b,ta_ticket c"+
		     " where a.tid=b.tid and a.dgdid=c.ticket_id and c.ticket_id='"+dgd+"' and b.dgd='"+dgd+"' and a.tid='"+tid+"' order by a.id";
		try {
			coreDAO.executeQuery(sql, "pwlist", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
}