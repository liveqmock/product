package com.trm.ztfinanceMng;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


public class Zt_RestaurantMngBLC extends DBBLC {
	public Zt_RestaurantMngBLC(){
		this.entityName="TA_ZT_QDQKJL4CT";
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
			
			sql="select g.id,bv.qkzt,g.begin_date,g.end_date,bv.ct,bv.qdxj, sum(case when bv.ct=v.ctid then hkje else 0 end) as hkje, t.dining_room_id, t.cmpny_name,t.cmpny_address"+
			  " from TA_ZT_BXCT bv,ta_ZT_group g,TA_ZT_QDQKJL4CT v,ta_dining_room t"+
			  " where bv.tid=g.id and g.id=v.tid(+) and bv.ct=t.dining_room_id"+
			  " and g.state='6' and  bv.qdxj>'0'";
			  if (!"".equals(tid)) { // 如果团号不为空
					sql += " and g.id like '%" + tid + "%' ";
				}
				if (!"".equals(ctmc)) { // 如果餐厅名不为空
					sql += " and t.cmpny_name like '%" + ctmc + "%' ";
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
			  sql += " group by g.id,bv.qkzt,g.begin_date,g.end_date,bv.ct,bv.qdxj,t.dining_room_id,t.cmpny_name,t.cmpny_address order by bv.qkzt";
			try {
				coreDAO.executeQueryPage(sql, "queryAllctList", pageNO, pageSize, rd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return 4;
		}
	

	public int getBXCTByTid(BizData rd,BizData sd){
		
		String flag = rd.getString("state");
		String ctid=rd.getStringByDI("TA_ZT_QDQKJL4CT","CTID", 0);
		String tid=rd.getStringByDI("TA_ZT_QDQKJL4CT","TID", 0);
		String sql="select g.id,bv.ct,bv.sf,bv.cityid,bv.qdxj,nvl((select sum(nvl(hkje,0))  from ta_zt_qdqkjl4ct where ctid='"+ctid+"' and tid='"+tid+"'),0) as hkje,"+
         " bv.qdxj-nvl((select sum(nvl(hkje,0))  from ta_zt_qdqkjl4ct where ctid='"+ctid+"' and tid='"+tid+"'),0) ye,t.dining_room_id, t.cmpny_name,t.cmpny_address "+
	     " from TA_ZT_BXCT bv,ta_zt_group g,TA_ZT_QDQKJL4CT v,ta_dining_room t"+
	     " where bv.tid=g.id and g.id=v.tid(+) and bv.ct=t.dining_room_id"+
	     " and g.state='6' and  bv.QKZT='N' and  bv.qdxj>'0' and g.id='"+tid+"' and bv.ct='"+ctid+"'"+
	     " group by g.id,bv.ct,bv.sf,bv.cityid,bv.qdxj,t.dining_room_id,t.cmpny_name,t.cmpny_address";
		try {
			coreDAO.executeQuery(sql, "selectBxCT", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
}
	
	public int addCTqingdan(BizData rd, BizData sd) throws SQLException {
		Connection conn = coreDAO.getConnection();
		String tid = rd.getStringByDI("TA_ZT_QDQKJL4CT", "TID", 0);
		String ctid = rd.getStringByDI("TA_ZT_QDQKJL4CT", "CTID", 0);
		String ye = rd.getStringByDI("TA_ZT_QDQKJL4CT", "YE", 0);

		try {
			coreDAO.beginTrasct(conn);

			if ("0".equals(ye)) {
				int id = queryMaxIDByPara("TA_ZT_QDQKJL4CT", "ID", null);
				rd.add("TA_ZT_QDQKJL4CT", "ID", id);
				rd.add("TA_ZT_QDQKJL4CT", "qkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_ZT_QDQKJL4CT", "fkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_ZT_QDQKJL4CT", "czrq", rd.sdfDate
						.format(new Date()));
				BizData data1 = new BizData();
				data1.addAttr("TA_ZT_BXCT", "TID", 0, "oldValue", tid);
				data1.addAttr("TA_ZT_BXCT", "CT", 0, "oldValue", ctid);
				data1.add("TA_ZT_BXCT", "QKZT", "Y");
				coreDAO.insert("TA_ZT_QDQKJL4CT", rd, conn);
				coreDAO.update("TA_ZT_BXCT", data1, conn);
			}

			else {
				int id = queryMaxIDByPara("TA_ZT_QDQKJL4CT", "ID", null);
				rd.add("TA_ZT_QDQKJL4CT", "ID", id);
				rd.add("TA_ZT_QDQKJL4CT", "TID", tid);

				rd.add("TA_ZT_QDQKJL4CT", "fkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_ZT_QDQKJL4CT", "czrq", rd.sdfDate
						.format(new Date()));
				coreDAO.insert("TA_ZT_QDQKJL4CT", rd, conn);
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
		String ctid=rd.getStringByDI("TA_ZT_QDQKJL4CT","CTID", 0);
		String tid=rd.getStringByDI("TA_ZT_QDQKJL4CT","TID", 0);
		String sql="select a.tid,a.hkje,a.ye,a.ctid,a.ye,a.czRQ, b.qdxj,c.cmpny_name,c.cmpny_address"+
		   " from TA_ZT_QDQKJL4CT a,TA_ZT_BXCT b,ta_dining_room c"+
		   " where a.tid=b.tid and a.ctid=c.dining_room_id and b.ct='"+ctid+"' and c.dining_room_id='"+ctid+"' and a.tid='"+tid+"' and  b.qdxj>'0' order by a.id";
		try {
			coreDAO.executeQuery(sql, "ctlist", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
}