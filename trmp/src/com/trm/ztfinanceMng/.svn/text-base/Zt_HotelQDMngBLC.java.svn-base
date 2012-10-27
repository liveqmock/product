package com.trm.ztfinanceMng;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


public class Zt_HotelQDMngBLC extends DBBLC {
	public Zt_HotelQDMngBLC(){
		this.entityName="TA_zt_QDQKJL4HOTEL";
	}
	public int AllQdList(BizData rd, BizData sd){
		
		String state=rd.getString("state");
		String bdate=rd.getString("bdate");
		String edate=rd.getString("edate");
		String tid=rd.getString("groupID");
		String jdmc=rd.getString("jdmc");
		String pageNOStr = rd.getString("pageNO");
		String pageSizeStr = rd.getString("pageSize");
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		String sql="select bv.qkzt,g.id,g.begin_date,g.end_date,bv.jdid, bv.qdxj, sum(case when bv.jdid=v.jdid then hkje else 0 end)as hkje,t.hotel_id, t.hotel_name,t.hotel_address"+
		 " from ta_zt_bxhotel bv,ta_zt_group g,ta_zt_qdqkjl4hotel v,ta_hotel t"+
		 " where bv.tid=g.id and g.id=v.tid(+) and bv.jdid=t.hotel_id"+
		 " and g.state='6' and  bv.qdxj>'0'";
		  if (!"".equals(state)) { 
				sql += "and bv.qkzt='"+state+"' \n";
			}
		  if (!"".equals(tid)) { // 如果团号不为空
				sql += " and g.id like '%" + tid + "%' ";
			}
		  if (!"".equals(jdmc)) { // 如果酒店名不为空
				sql += " and t.hotel_name like '%" + jdmc + "%' ";
			}
		  if (!"".equals(bdate)) { // 如果开团时间不为空
				sql += "and g.BEGIN_DATE >=to_date('" + bdate + "','yyyy-mm-dd') \n";
			}
		  if (!"".equals(edate)) { // 如果开团时间不为空
				sql += "and g.end_DATE <= to_date('" + edate + "','yyyy-mm-dd') \n";
			}

			    sql +=" group by g.id,bv.qkzt,g.begin_date,g.end_date,bv.jdid,bv.qdxj,t.hotel_id,t.hotel_name,t.hotel_address order by bv.qkzt";
		try {
			coreDAO.executeQueryPage(sql, "rsQingDanHotelList", pageNO, pageSize, rd);
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 2;
	
			
		}
	

	public int getBXhotelByTid(BizData rd,BizData sd){
		String flag = rd.getString("state");
		String jdid=rd.getStringByDI("ta_zt_qdqkjl4hotel","JDID", 0);
		String tid=rd.getStringByDI("ta_zt_qdqkjl4hotel","TID", 0);
		String sql="select g.id,bv.sf,bv.city,bv.jdid,bv.qdxj, nvl((select sum(nvl(hkje,0))  from ta_zt_qdqkjl4hotel where tid='"+tid+"' and jdid='"+jdid+"' ),0) as hkje, bv.qdxj-nvl((select sum(nvl(hkje,0))  from ta_zt_qdqkjl4hotel where tid='"+tid+"' and jdid='"+jdid+"' ),0) ye,t.hotel_id, t.hotel_name,t.hotel_address"+
		  " from ta_zt_bxhotel bv,ta_zt_group g,ta_zt_qdqkjl4hotel v,ta_hotel t"+
		  " where bv.tid=g.id and g.id=v.tid(+) and bv.jdid=t.hotel_id"+
		  " and g.state='6' and  bv.qkzt='N' and t.hotel_id='"+jdid+"' and g.id='"+tid+"' "+
		  " group by g.id,bv.sf,bv.city,bv.jdid,bv.qdxj,bv.jdid,t.hotel_id,t.hotel_name,t.hotel_address";
		try {
			coreDAO.executeQuery(sql, "selectBxHotel", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	
	public int addJDqingdan(BizData rd, BizData sd) throws SQLException {
		Connection conn = coreDAO.getConnection();
		String tid = rd.getStringByDI("TA_zt_QDQKJL4HOTEL", "TID", 0);
		String jdid = rd.getStringByDI("TA_zt_QDQKJL4HOTEL", "JDID", 0);
		String ye = rd.getStringByDI("TA_zt_QDQKJL4HOTEL", "YE", 0);
		try {
			coreDAO.beginTrasct(conn);

			if ("0".equals(ye)) {
				int id = queryMaxIDByPara("TA_zt_QDQKJL4HOTEL", "ID", null);
				rd.add("TA_zt_QDQKJL4HOTEL", "ID", id);
				rd.add("TA_zt_QDQKJL4HOTEL", "qkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_zt_QDQKJL4HOTEL", "fkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_zt_QDQKJL4HOTEL", "czrq", rd.sdfDate
						.format(new Date()));
				BizData data1 = new BizData();
				data1.addAttr("TA_zt_BXHOTEL", "TID", 0, "oldValue", tid);
				data1.addAttr("TA_zt_BXHOTEL", "JDID", 0, "oldValue", jdid);
				data1.add("TA_zt_BXHOTEL", "QKZT", "Y");
				coreDAO.insert("TA_zt_QDQKJL4HOTEL", rd, conn);
				coreDAO.update("TA_zt_BXHOTEL", data1, conn);
			}

			else {
				int id = queryMaxIDByPara("TA_zt_QDQKJL4HOTEL", "ID", null);
				rd.add("TA_zt_QDQKJL4HOTEL", "ID", id);
				rd.add("TA_zt_QDQKJL4HOTEL", "fkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_zt_QDQKJL4HOTEL", "czrq", rd.sdfDate
						.format(new Date()));
				coreDAO.insert("TA_zt_QDQKJL4HOTEL", rd, conn);
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

//查看清款记录
		public int queryQDlist(BizData rd,BizData sd){
			String jdid=rd.getStringByDI("ta_zt_qdqkjl4hotel","JDID", 0);
			String tid=rd.getStringByDI("ta_zt_qdqkjl4hotel","TID", 0);
			String sql="select a.tid,a.hkje,a.ye,a.tid,a.jdid,a.ye,a.czRQ, b.qdxj,c.hotel_name,c.hotel_address"+
			 " from ta_zt_qdqkjl4hotel a,ta_zt_bxhotel b,ta_hotel c "+
			 " where a.tid=b.tid and a.jdid=c.hotel_id and c.hotel_id='"+jdid+"' and b.jdid='"+jdid+"'  and a.tid='"+tid+"' order by a.id";
			try {
				coreDAO.executeQuery(sql, "hklist", rd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 1;
		}

}
