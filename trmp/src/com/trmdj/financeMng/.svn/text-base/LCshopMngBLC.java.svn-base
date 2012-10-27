package com.trmdj.financeMng;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


public class LCshopMngBLC extends DBBLC {
	public LCshopMngBLC(){
		this.entityName="TA_DJ_LC4SHOP";
	}
	//查询购物点留存清款列表	
	public void allLCLshopList(BizData rd, BizData sd) {

		String tid = rd.getString("groupID");
		String gwdid = rd.getString("gwdid");
		String sfid = rd.getString("sfid");
		String csid = rd.getString("csid");
		String bd = rd.getString("dB");
		String ed = rd.getString("dE");
		String qkzt = rd.getString("qkzt");
		String pageNOStr = rd.getString("pageNO");
		String pageSizeStr = rd.getString("pageSize");
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		String sql = "select g.id,bv.gwdid,bv.qkzt_lc,bv.gslcxj, sum(case when bv.gwdid=v.gwdid then bcqkje else 0 end) as hkje, t.cmpny_name \n"
				+ " from TA_DJ_BXGW bv,ta_dj_group g,TA_DJ_LC4SHOP v,ta_shoppoint t \n"
				+ " where bv.tid=g.id and bv.tid=v.tid(+) and bv.gwdid=v.gwdid(+) \n"
				+ " and bv.gwdid=t.shop_point_id(+) and g.state='6' and bv.gslcxj>0 \n";
		if(!"".equals(qkzt))
			sql += "and bv.qkzt_lc='"+qkzt+"' \n";
		if (!"".equals(tid)) { // 如果团号不为空
			sql += " and g.id like '%" + tid + "%' \n";
		}
		if (!"".equals(sfid)) { // 如果省份不为空
			sql += " and bv.sfid = '" + sfid + "' \n";
		}
		if (!"".equals(csid)) { // 如果城市不为空
			sql += " and bv.csid = '" + csid + "' \n";
		}
		if (!"".equals(gwdid)) { // 如果代购点名不为空
			sql += " and bv.gwdid = '" + gwdid + "' \n";
		}
		if(!"".equals(bd) && !"".equals(ed))
			sql += "and g.begin_date >= to_date('"+bd+"','yyyy-mm-dd') and g.begin_date <= to_date('"+ed+"','yyyy-mm-dd') \n";
		sql += " group by g.id,bv.gwdid,bv.qkzt_lc,bv.gslcxj,t.cmpny_name order by bv.qkzt_lc";
		try {
			coreDAO.executeQueryPage(sql, "queryAllLCshopList", pageNO,
					pageSize, rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	

	//根据团ID，购物点ID得到留存清款信息
	public int getLcShopByTid(BizData rd,BizData sd){
		String gwdid=rd.getStringByDI("TA_DJ_LC4SHOP","GWDID", 0);
		String tid=rd.getStringByDI("TA_DJ_LC4SHOP","TID", 0);
		String sql="select bx.xzqhid,bx.gwdid,bx.tid,bx.gslcxj,t.cmpny_name,nvl((select sum(nvl(lc.BCQKJE,0))\n"+  
				" from TA_DJ_LC4SHOP lc\n"+
				" where lc.tid='"+tid+"' and lc.gwdid='"+gwdid+"' ),0) as hkje,bx.gslcxj-nvl((\n"+
				"select sum(nvl(lc.BCQKJE,0))  from TA_DJ_LC4SHOP lc\n"+
				"where lc.tid='"+tid+"' and lc.gwdid='"+gwdid+"' ),0) ye\n"+
				"from  TA_DJ_BXGW bx,TA_DJ_GROUP g,ta_shoppoint t,TA_DJ_LC4SHOP lc\n"+
				"where g.id=bx.tid  and bx.gwdid=t.shop_point_id(+) and bx.tid=lc.tid(+)\n" +
				"and bx.qkzt_lc='N' and g.state='6' and bx.gslcxj>0\n"+
				"and g.id='"+tid+"' and  bx.gwdid='"+gwdid+"'\n"+
				"group by  bx.xzqhid,bx.gslcxj,bx.gwdid,bx.tid,t.cmpny_name";
		try {
			coreDAO.executeQuery(sql, "selectlcShop", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
}
//添加合作社清款	
	public int addLCshop(BizData rd, BizData sd) throws SQLException {
		Connection conn = coreDAO.getConnection();
		String tid = rd.getStringByDI("TA_DJ_LC4SHOP", "TID", 0);
		String gwdid=rd.getStringByDI("TA_DJ_LC4SHOP", "GWDID", 0);
		String ye = rd.getStringByDI("TA_DJ_LC4SHOP", "YE", 0);
		try {
			coreDAO.beginTrasct(conn);

			if ("0".equals(ye)) {
				int id = queryMaxIDByPara("TA_DJ_LC4SHOP", "ID", null);
				rd.add("TA_DJ_LC4SHOP", "ID", id);
				rd.add("TA_DJ_LC4SHOP", "qkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_DJ_LC4SHOP", "fkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_DJ_LC4SHOP", "czrq", rd.sdfDate
						.format(new Date()));
				BizData data1 = new BizData();
				data1.addAttr("TA_DJ_BXGW", "TID", 0, "oldValue", tid);
				data1.addAttr("TA_DJ_BXGW", "GWDID", 0, "oldValue", gwdid);
				data1.add("TA_DJ_BXGW", "QKZT_LC", "Y");

						
				coreDAO.insert("TA_DJ_LC4SHOP", rd, conn);
				coreDAO.update("TA_DJ_BXGW", data1, conn);
			}

			else {
				int id = queryMaxIDByPara("TA_DJ_LC4SHOP", "ID", null);
				rd.add("TA_DJ_LC4SHOP", "ID", id);
				rd.add("TA_DJ_LC4SHOP", "fkrq", rd.sdfDate
						.format(new Date()));
				rd.add("TA_DJ_LC4SHOP", "czrq", rd.sdfDate
						.format(new Date()));
				coreDAO.insert("TA_DJ_LC4SHOP", rd, conn);
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
//查看合作社清款列表	
	public int queryLCshoplist(BizData rd,BizData sd){
		String gwdid=rd.getStringByDI("TA_DJ_LC4SHOP","GWDID", 0);
		String tid=rd.getStringByDI("TA_DJ_LC4SHOP","TID", 0);
		String sql="select a.id,a.tid,nvl(a.bcqkje,0) as bcqkje ,a.ye,a.czRQ, b.gslcxj,d.cmpny_name"+
		   " from TA_DJ_LC4SHOP a,TA_DJ_BXGW b,ta_shoppoint d"+
		    " where a.tid=b.tid and b.gwdid=d.shop_point_id(+)"+
		    " and a.gwdid='"+gwdid+"'  and a.tid='"+tid+"'"+
			" group by a.id,a.tid,a.bcqkje,a.ye,a.czrq,b.gslcxj,d.cmpny_name order by a.id";
		try {
			coreDAO.executeQuery(sql, "lcshoplist", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	public int selectAllshoppoint(BizData rd,BizData sd){
		
		String sql="select shop_point_id,cmpny_name from ta_shoppoint ";
			
		try {
			coreDAO.executeQuery(sql, "SelectAllshoppoint", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
}