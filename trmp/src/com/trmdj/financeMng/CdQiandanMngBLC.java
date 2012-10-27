package com.trmdj.financeMng;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


/**
  * @ClassName: CdQiandanMngBLC
  * @Description: TODO 车辆签单清款查询
  * @author Comsys-Administrator
  * @date 2012-3-30 下午12:30:01
  *
  */
public class CdQiandanMngBLC extends DBBLC {
	public CdQiandanMngBLC(){
		this.entityName="TA_DJ_QDQKJL4VEH";
	}

	public int allCDList(BizData rd, BizData sd) {

		String state = rd.getString("state");
		String bdate=rd.getString("bdate");
		String edate=rd.getString("edate");
		String tid = rd.getString("groupID");
		String cdmc = rd.getString("cdmc");
		String pageNOStr = rd.getString("pageNO");
		String pageSizeStr = rd.getString("pageSize");
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		String sql = "";
		sql="select c.qkzt,g.id tid,g.begin_date,g.end_date,sum(nvl(c.qdje,0)) qdje,c.cd,c.cdmc,nvl(a.hkje,0) hkje \n" +
		    " from (select v.cdid,v.tid,sum(nvl(v.hkje,0)) hkje \n" +
			"from ta_dj_qdqkjl4veh v where v.orgid='"+sd.getString("orgid")+"'\n" +
			"group by v.tid,v.cdid) a,ta_dj_bxcl c,ta_dj_group g \n" +
			"where a.tid(+)=c.tid \n" +
			"and a.cdid(+)=c.cd \n" +
			"and c.tid=g.id \n" +
			"and g.state='6' \n" +
			"and c.qdje>0 and g.orgid='"+sd.getString("orgid")+"'\n";
		if (!"".equals(tid)) { // 如果团号不为空
			sql += " and g.id like '%" + tid + "%' ";
		}
		if (!"".equals(cdmc)) { // 如果车队名不为空
			sql += " and c.cdmc like '%" + cdmc + "%' ";
		}
		if (!"".equals(bdate)) { // 如果开团时间不为空
			sql += "and g.BEGIN_DATE >= to_date('" + bdate + "','yyyy-mm-dd') \n";
		}
		if (!"".equals(edate)) { // 如果开团时间不为空
			sql += "and g.end_DATE <= to_date('" + edate + "','yyyy-mm-dd') \n";
		}
		if (!"".equals(state)) { 
			sql += " and c.qkzt='"+state+"'\n";
		}
		sql += " group by a.hkje,c.cd,g.begin_date,g.end_date,c.tid,c.qkzt,g.id,c.cdmc \n" +
			   "order by c.qkzt";
		try {
			coreDAO.executeQueryPage(sql, "queryAllCDList", pageNO, pageSize,
					rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 3;
	}
	

	//根据团ID，车队ID得到车队签单信息
	public int getBXCDByTid(BizData rd,BizData sd){
		String cd=rd.getStringByDI("TA_DJ_QDQKJL4VEH","CDID", 0);
		String tid=rd.getStringByDI("TA_DJ_QDQKJL4VEH","TID", 0);
		String sql= "select c.qkzt,g.id,g.begin_date,g.end_date,sum(nvl(c.qdje,0)) qdje,nvl(sum(nvl(c.qdje,0))- nvl(a.hkje,0),0) ye, c.cd,c.cdmc,nvl(a.hkje,0) hkje  \n" +
				    " from (select v.cdid,v.tid,sum(nvl(v.hkje,0)) hkje \n" +
					"from ta_dj_qdqkjl4veh v where v.orgid='"+sd.getString("orgid")+"'\n" +
					"group by v.tid,v.cdid) a,ta_dj_bxcl c,ta_dj_group g \n" +
					"where c.tid=g.id \n" +
					"and a.tid(+)=c.tid \n" +
					"and a.cdid(+)=c.cd \n" +
					"and g.state='6' \n" +
					"and c.qdje>0 \n" +
					"and g.id='"+tid+"' \n" +
					"and c.cd='"+cd+"' and g.orgid='"+sd.getString("orgid")+"'\n" +
					" group by a.hkje,c.cd,g.begin_date,g.end_date,c.tid,c.qkzt,g.id,c.cdmc \n" +
					"order by c.qkzt";
		try {
			coreDAO.executeQuery(sql, "selectBxCD", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
}
	//添加车队清款	
	public int addCDqingdan(BizData rd, BizData sd) throws SQLException {
		Connection conn = coreDAO.getConnection();
		String tid = rd.getStringByDI("TA_DJ_QDQKJL4VEH", "TID", 0);
		String cdid = rd.getStringByDI("TA_DJ_QDQKJL4VEH", "CDID", 0);		
		String ye = rd.getStringByDI("TA_DJ_QDQKJL4VEH", "YE", 0);
		try {
			coreDAO.beginTrasct(conn);

			if ("0".equals(ye)) {
				int id = queryMaxIDByPara("TA_DJ_QDQKJL4VEH", "ID", null);
				rd.add("TA_DJ_QDQKJL4VEH", "ID", id);
				rd.add("TA_DJ_QDQKJL4VEH", "qkrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4VEH", "fkrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4VEH", "czrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4VEH", "orgid",sd.getString("orgid"));
				BizData data1 = new BizData();
				data1.addAttr("TA_DJ_BXCL", "TID", 0, "oldValue", tid);
				data1.addAttr("TA_DJ_BXCL", "CD", 0, "oldValue", cdid);
				data1.addAttr("TA_DJ_BXCL", "orgid", 0, "oldValue", sd.getString("orgid"));
				data1.add("TA_DJ_BXCL", "QKZT", "Y");
				coreDAO.insert("TA_DJ_QDQKJL4VEH", rd, conn);
				coreDAO.update("TA_DJ_BXCL", data1, conn);
			}

			else {
				int id = queryMaxIDByPara("TA_DJ_QDQKJL4VEH", "ID", null);
				rd.add("TA_DJ_QDQKJL4VEH", "ID", id);
				rd.add("TA_DJ_QDQKJL4VEH", "fkrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4VEH", "czrq", rd.sdfDate.format(new Date()));
				rd.add("TA_DJ_QDQKJL4VEH", "orgid",sd.getString("orgid"));
				coreDAO.insert("TA_DJ_QDQKJL4VEH", rd, conn);
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
	//查看车队清款列表	
	public int queryCDlist(BizData rd,BizData sd){
		String cdid=rd.getStringByDI("TA_DJ_QDQKJL4VEH","CDID", 0);
		String tid=rd.getStringByDI("TA_DJ_QDQKJL4VEH","TID", 0);
		String sql="select a.id,a.tid,a.hkje,a.ye,a.cdid,a.czRQ, sum(b.qdje) qdje,b.cdmc \n" +
		    " from TA_DJ_QDQKJL4VEH a,TA_DJ_BXcl b\n" +
		    " where a.tid=b.tid \n" +
		    "and b.cd='"+cdid+"' \n" + 
		    "and a.tid='"+tid+"' and a.orgid='"+sd.getString("orgid")+"'\n" + 
		    " group by a.id,a.tid,a.hkje,a.ye,a.cdid,a.czRQ,b.cdmc"+
		    " order by a.id";
		try {
			coreDAO.executeQuery(sql, "cdlist", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
}