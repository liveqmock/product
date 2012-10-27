package com.trmdj.financeMng;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


public class JdQiandanMngBLC extends DBBLC {
	public JdQiandanMngBLC(){
		this.entityName="TA_DJ_QDQKJL4HOTEL";
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
		String sql= "select bv.qkzt,g.id,g.begin_date,g.end_date, sum(nvl(bv.qdje, 0)) qdje,bv.jdid, bv.jdmc, nvl(v.hkje,0) hkje \n" +
					" from (select v.tid,v.jdid,sum(nvl(v.hkje,0)) hkje \n" +
					"from ta_dj_qdqkjl4hotel v where v.orgid='"+sd.getString("orgid")+"'\n" +
					"group by v.tid,v.jdid) v, ta_dj_bxhotel bv,ta_dj_group g \n" +
					"where bv.tid=g.id \n" +
					"and v.tid(+)=bv.tid \n" +
					"and v.jdid(+)=bv.jdid \n" +
					"and g.state='6' \n" +
					"and  bv.qdje>0 and g.orgid='"+sd.getString("orgid")+"'\n";
		  if (!"".equals(state)) { 
				sql += "and bv.qkzt='"+state+"' \n";
			}
		  if (!"".equals(tid)) { // ����źŲ�Ϊ��
				sql += " and g.id like '%" + tid + "%' \n";
			}
		  if (!"".equals(jdmc)) { // ����Ƶ�����Ϊ��
				sql += " and bv.jdmc like '%" + jdmc + "%' \n";
			}
		  if (!"".equals(bdate)) { // �������ʱ�䲻Ϊ��
				sql += "and g.BEGIN_DATE >=to_date('" + bdate + "','yyyy-mm-dd') \n";
			}
		  if (!"".equals(edate)) { // �������ʱ�䲻Ϊ��
				sql += "and g.end_DATE <= to_date('" + edate + "','yyyy-mm-dd') \n";
			}

			    sql +=" group by g.id,bv.qkzt,g.begin_date,g.end_date,bv.jdid,bv.jdmc,v.hkje order by bv.qkzt";
		try {
			coreDAO.executeQueryPage(sql, "rsQingDanHotelList", pageNO, pageSize, rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
		}
	

	public int getBXhotelByTid(BizData rd,BizData sd){
		String jdid=rd.getStringByDI("ta_dj_qdqkjl4hotel","JDID", 0);
		String tid=rd.getStringByDI("ta_dj_qdqkjl4hotel","TID", 0);
		String sql= "select bv.qkzt,g.id,g.begin_date,g.end_date,bv.jdid,bv.xzqhid, sum(nvl(bv.qdje,0)) qdje, nvl(v.hkje,0) hkje, nvl((sum(nvl(bv.qdje,0))- nvl(v.hkje,0)),0) ye, bv.jdmc \n" + 
					" from (select v.tid,v.jdid,sum(nvl(v.hkje,0)) hkje from ta_dj_qdqkjl4hotel v  where v.orgid='"+sd.getString("orgid")+"' group by v.tid,v.jdid) v, ta_dj_bxhotel bv,ta_dj_group g \n" +
					"where bv.tid=g.id \n" +
					"and v.tid(+)=bv.tid \n" +
					"and v.jdid(+)=bv.jdid \n" +
					"and g.state='6' \n" +
					"and bv.QDJE>0 \n" +
					"and g.id='"+tid+"' \n" +
					"and bv.jdid='"+jdid+"' and g.orgid='"+sd.getString("orgid")+"'\n" +
					" group by g.id,bv.qkzt,g.begin_date,g.end_date,bv.xzqhid,v.hkje,bv.jdid,bv.jdmc order by bv.qkzt";
		try {
			coreDAO.executeQuery(sql, "selectBxHotel", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	
	public int addJDqingdan(BizData rd, BizData sd) throws SQLException {
		Connection conn = coreDAO.getConnection();
		String tid = rd.getStringByDI("TA_DJ_QDQKJL4HOTEL", "TID", 0);
		String jdid = rd.getStringByDI("TA_DJ_QDQKJL4HOTEL", "JDID", 0);
		String ye = rd.getStringByDI("TA_DJ_QDQKJL4HOTEL", "YE", 0);
		String date = rd.sdfDate.format(new Date());
		try {
			coreDAO.beginTrasct(conn);
			
			if ("0".equals(ye)) {
				int id = queryMaxIDByPara("TA_DJ_QDQKJL4HOTEL", "ID", null);
				rd.add("TA_DJ_QDQKJL4HOTEL", "ID", id);
				rd.add("TA_DJ_QDQKJL4HOTEL", "qkrq", date);
				rd.add("TA_DJ_QDQKJL4HOTEL", "fkrq", date);
				rd.add("TA_DJ_QDQKJL4HOTEL", "czrq", date);
				rd.add("TA_DJ_QDQKJL4HOTEL", "orgid",sd.getString("orgid"));
				BizData data1 = new BizData();
				data1.addAttr("TA_DJ_BXHOTEL", "TID", 0, "oldValue", tid);
				data1.addAttr("TA_DJ_BXHOTEL", "JDID", 0, "oldValue", jdid);
				data1.addAttr("TA_DJ_BXHOTEL", "orgid", 0, "oldValue", sd.getString("orgid"));
				data1.add("TA_DJ_BXHOTEL", "QKZT", "Y");
				coreDAO.insert("TA_DJ_QDQKJL4HOTEL", rd, conn);
				coreDAO.update("TA_DJ_BXHOTEL", data1, conn);
			}

			else {
				int id = queryMaxIDByPara("TA_DJ_QDQKJL4HOTEL", "ID", null);
				rd.add("TA_DJ_QDQKJL4HOTEL", "ID", id);
				rd.add("TA_DJ_QDQKJL4HOTEL", "fkrq", date);
				rd.add("TA_DJ_QDQKJL4HOTEL", "czrq", date);
				rd.add("TA_DJ_QDQKJL4HOTEL", "orgid",sd.getString("orgid"));
				coreDAO.insert("TA_DJ_QDQKJL4HOTEL", rd, conn);
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

//�鿴����¼
		public int queryQDlist(BizData rd,BizData sd){
			String jdid=rd.getStringByDI("ta_dj_qdqkjl4hotel","JDID", 0);
			String tid=rd.getStringByDI("ta_dj_qdqkjl4hotel","TID", 0);
			String sql= "select a.tid,a.hkje,a.ye,a.tid,a.jdid,a.czRQ, b.qdje,b.jdmc  \n" +
						" from (select b.tid,sum(b.qdje) qdje,b.jdmc,b.jdid  from ta_dj_bxhotel b group by b.tid,b.jdmc,b.jdid) b,ta_dj_qdqkjl4hotel a \n" +
						"where a.tid=b.tid \n" +
						"and b.jdid='"+jdid+"' \n" +
						"and a.tid='"+tid+"' and a.orgid='"+sd.getString("orgid")+"'\n" +
						"order by a.id";
			try {
				coreDAO.executeQuery(sql, "hklist", rd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 1;
		}

}
