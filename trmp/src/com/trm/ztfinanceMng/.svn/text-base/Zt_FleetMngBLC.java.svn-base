package com.trm.ztfinanceMng;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


public class Zt_FleetMngBLC extends DBBLC {
	public Zt_FleetMngBLC(){
		this.entityName="TA_ZT_QDQKJL4VEH";
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

		sql = " select nvl(a.hkje,0) hkje,c.cd,sum(c.qdje) qdje,c.qdje-nvl(a.hkje,0) ye, c.tid,c.qkzt,car.cmpny_name\n"+ 
			  "	 from (\n"+
			  " select sum(veh.hkje) hkje,veh.tid,veh.cdid\n"+
			  "	from ta_zt_qdqkjl4veh veh \n"+
			  "	group by veh.tid,veh.cdid ) a,ta_zt_bxcl c,ta_zt_group g,ta_car_team car\n"+
			  "	where a.tid(+)=c.tid and a.cdid(+)=c.cd and car.team_id=c.cd and c.tid=g.id \n"+
			  "	and g.state='6' and c.qdje > 0 ";
			
		if (!"".equals(tid)) { // 如果团号不为空
			sql += " and g.id like '%" + tid + "%' ";
		}
		if (!"".equals(cdmc)) { // 如果车队名不为空
			sql += " and car.cmpny_name like '%" + cdmc + "%' ";
		}
		if (!"".equals(bdate)) { // 如果开团时间不为空
			sql += "and g.BEGIN_DATE >= to_date('" + bdate + "','yyyy-mm-dd') \n";
		}
		if (!"".equals(edate)) { // 如果开团时间不为空
			sql += "and g.end_DATE <= to_date('" + edate + "','yyyy-mm-dd') \n";
		}
		if (!"".equals(state)) { 
			sql += " and  c.qkzt='"+state+"'\n";
		}
		
		sql += " group by a.hkje,c.cd,c.qdje,c.tid,c.qkzt,car.cmpny_name order by c.tid";
		try {
			coreDAO.executeQueryPage(sql, "queryAllCDList", pageNO, pageSize,rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 3;
	}
	

	//根据团ID，车队ID得到车队签单信息
	public int getBXCDByTid(BizData rd,BizData sd){
		
		String flag = rd.getString("state");
		String cd=rd.getStringByDI("TA_ZT_QDQKJL4VEH","CDID", 0);
		String tid=rd.getStringByDI("TA_ZT_QDQKJL4VEH","TID", 0);
		String sql="select g.id,bv.sf,bv.city,bv.cd,(select sum(qdje) from TA_ZT_BXcl where tid='"+tid+"'"+
				" and cd='"+cd+"') as qdje,nvl((select sum(nvl(hkje,0))  from "+
				" TA_ZT_QDQKJL4VEH where tid='"+tid+"' and cdid='"+cd+"' ),0) as hkje,"+
				" (select sum(qdje) from TA_ZT_BXcl where tid='"+tid+"' and cd='"+cd+"')-nvl((select sum(nvl(hkje,0))"+  
				" from TA_ZT_QDQKJL4VEH where tid='"+tid+"' and cdid='"+cd+"' ),0) ye,"+
				" t.cmpny_name,t.cmpny_address"+
			    " from TA_ZT_BXcl bv,ta_ZT_group g,TA_ZT_QDQKJL4VEH v,TA_CAR_TEAM t"+
			    " where bv.tid=g.id and g.id=v.tid(+)   and  bv.cd=t.team_id"+
			    " and g.state='6' and  bv.qkzt='N' and g.id='"+tid+"' and bv.cd='"+cd+"'"+
			    " group by g.id,bv.sf,bv.city,bv.cd,t.cmpny_name,t.cmpny_address";
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
		String tid = rd.getStringByDI("TA_ZT_QDQKJL4VEH", "TID", 0);
		String cdid = rd.getStringByDI("TA_ZT_QDQKJL4VEH", "CDID", 0);		
		String ye = rd.getStringByDI("TA_ZT_QDQKJL4VEH", "YE", 0);
		try {
			coreDAO.beginTrasct(conn);

			if ("0".equals(ye)) {
				int id = queryMaxIDByPara("TA_ZT_QDQKJL4VEH", "ID", null);
				rd.add("TA_ZT_QDQKJL4VEH", "ID", id);
				rd.add("TA_ZT_QDQKJL4VEH", "qkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_ZT_QDQKJL4VEH", "fkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_ZT_QDQKJL4VEH", "czrq", rd.sdfDate
						.format(new Date()));
				BizData data1 = new BizData();
				data1.addAttr("TA_ZT_BXCL", "TID", 0, "oldValue", tid);
				data1.addAttr("TA_ZT_BXCL", "CD", 0, "oldValue", cdid);
				data1.add("TA_ZT_BXCL", "QKZT", "Y");
				coreDAO.insert("TA_ZT_QDQKJL4VEH", rd, conn);
				coreDAO.update("TA_ZT_BXCL", data1, conn);
			}

			else {
				int id = queryMaxIDByPara("TA_ZT_QDQKJL4VEH", "ID", null);
				rd.add("TA_ZT_QDQKJL4VEH", "ID", id);
				rd.add("TA_ZT_QDQKJL4VEH", "fkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_ZT_QDQKJL4VEH", "czrq", rd.sdfDate
						.format(new Date()));
				coreDAO.insert("TA_ZT_QDQKJL4VEH", rd, conn);
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
		String cdid=rd.getStringByDI("TA_ZT_QDQKJL4VEH","CDID", 0);
		String tid=rd.getStringByDI("TA_ZT_QDQKJL4VEH","TID", 0);
		String sql="select a.id,a.tid,a.hkje,a.ye,a.cdid,a.czRQ, sum(b.qdje) qdje,c.cmpny_name,c.cmpny_address"+
		    " from TA_ZT_QDQKJL4VEH a,TA_ZT_BXcl b,TA_CAR_TEAM c"+
		    " where a.tid=b.tid and a.cdid=c.team_id and c.team_id='"+cdid+"' and b.cd='"+cdid+"' and a.tid='"+tid+"'"+ 
		    " group by a.id,a.tid,a.hkje,a.ye,a.cdid,a.czRQ,c.cmpny_name,c.cmpny_address"+
		    " order by a.id";
		try {
			coreDAO.executeQuery(sql, "cdlist", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
}