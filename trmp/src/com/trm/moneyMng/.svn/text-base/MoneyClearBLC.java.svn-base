package com.trm.moneyMng;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class MoneyClearBLC extends DBBLC{
	public MoneyClearBLC(){
		this.entityName="TA_ZT_DZJL_HIS";
	}
	public int moneyClearList(BizData rd,BizData sd){
		String tid=rd.getString("tid");
		String dateb=rd.getString("dateb");
		String datee=rd.getString("datee");
		String ddh=rd.getString("ddh");
		String pfs=rd.getString("pfs");
		String mdNm=rd.getString("mdNm");
		String qkzt=rd.getString("qkzt");
		
		String sql="select a.*,nvl(b.hj,0) hj,c.yfkhj,c.yfkhj-nvl(b.hj,0) as fk \n"+
					"from (\n"+
				    "select count(v.id) rs,o.tid,decode(o.jszt,'N','未清款','Y','已清款') js_zt,o.id ddh,g.xlmc,\n"+
				    "g.begin_date,g.end_date,to_char(g.begin_date,'mm-dd') bDate,to_char(g.end_date,'mm-dd') eDate\n"+
			     	",g.days,e.orgname,u2.userfrom\n"+
			    	"from ta_zt_ykorder o,ta_zt_visitor v,ta_zt_group g,ta_zt_linemng l,drmuser u,hremployee e,drmuser u2\n"+
			    	"where o.id=v.m_id and o.tid=g.id and o.creater=u.userno and u.empid=e.empid and g.line_id=l.line_id and l.userno=u2.userno\n"+
			    	"and o.dd_confirm=1\n"+
			    	"and u2.usertype='P'"+
			    	"group by o.tid,o.id,o.jszt,o.jszt,g.xlmc,g.begin_date,g.end_date,g.days,e.orgname,u2.userfrom\n"+
			    	") a,"+
			    	"(select nvl(sum(FK_THISTIME),0) hj,ORDERID from TA_ZT_DZJL_HIS group by orderid) b\n"+
			    	",("+
			    	"select sum(yfk) yfkhj,ddh,dzzt from (\n"+
			    	"select sum(p.person_count) rs,p.price_th,sum(p.person_count)*p.price_th yfk\n"+
			    	",d.dmsm1 pricetype,o.tid,o.id ddh,o.remark,o.dzzt\n"+
			    	"from ta_zt_ykorder o,ta_zt_gorderprice p,dmsm d\n"+
			    	"where o.id=p.orderid and p.price_type=d.dmz\n"+
			    	"and d.dmlb=4\n"+
			    	"and o.dd_confirm=1\n"+
			    	"group by p.price_th,d.dmsm1,o.tid,o.id,o.remark,o.dzzt)\n"+ 
			    	"group by ddh,dzzt) c\n"+
			    	"where a.ddh=b.orderid(+) and a.ddh=c.ddh\n";
			    	if (!"".equals(pfs)){
			    	sql+= "and a.userfrom like '%" + pfs + "%' \n";
			    	}
			    	if (!"".equals(tid)){
			    	sql += "and a.tid like '%" + tid + "%'\n";
			    	}
			    	if (!"".equals(ddh)){
			    	sql += "and a.ddh  like '%"+ ddh +"%'\n";
			    	}
			    	if (!"".equals(mdNm)){
			    	sql += "and a.orgname like '%" + mdNm +"%'\n";
			    	}
			    	if (!"".equals(dateb)){
			    	sql += "and a.begin_date>=to_date('"+dateb+"','yyyy-mm-dd')\n";
			    	}
			    	if (!"".equals(datee)){
			    	sql += "and a.end_date<=to_date('"+datee+"','yyyy-mm-dd')\n";
			    	}
			    	if("Y".equals(qkzt)){
			    		sql += "and a.js_zt = '已清款' \n";
			    	}
			    	if("N".equals(qkzt)){
			    		sql += "and a.js_zt = '未清款' \n";
			    	}
				    sql += "and c.dzzt='1' \n";
				    sql += "order by a.tid";
		try {
			coreDAO.executeQuery(sql, "moneyclear", rd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 999;
	}
	public int addMoneyClear(BizData rd, BizData sd) throws SQLException {
		Connection conn = coreDAO.getConnection();
		String groupID = rd.getString("groupID");
		String[]ykRows = rd.getRowIDs("DDH"+groupID);
		try {
			coreDAO.beginTrasct(conn);
			for(int i = 0; i < ykRows.length; i++){
				String ddhID = rd.getString("DDH"+groupID, "DDH", ykRows[i]);
				String YINGFU = rd.getString("DDH"+groupID, "YINGFU", ykRows[i]);
				String YIFUJE = rd.getString("DDH"+groupID, "YIFUJE", ykRows[i]);
				String BCFK = rd.getString("DDH"+groupID, "BCFK", ykRows[i]);
				//String BZ = rd.getString("DDH"+groupID, "BZ", ykRows[i]);
				String YE = rd.getString("DDH"+groupID, "YE", ykRows[i]);
				if ("0".equals(YE)) {
					BizData data1 = new BizData();
					data1.addAttr("TA_ZT_YKORDER", "TID", 0, "oldValue", groupID);
					data1.addAttr("TA_ZT_YKORDER", "ID", 0, "oldValue", ddhID);
					data1.add("TA_ZT_YKORDER", "YIN_SK", YINGFU);//应收
					data1.add("TA_ZT_YKORDER", "YI_SK", YIFUJE);//已收
					//data1.add("TA_ZT_YKORDER", "REMARK", BZ);//已收
					data1.add("TA_ZT_YKORDER", "JSZT", "Y");
					coreDAO.update("TA_ZT_YKORDER", data1, conn);
				}
				int id = queryMaxIDByPara("TA_ZT_DZJL_HIS", "ID", null);
				rd.add("TA_ZT_DZJL_HIS", "ID", id);
				rd.add("TA_ZT_DZJL_HIS", "ORDERID", ddhID);
				rd.add("TA_ZT_DZJL_HIS", "FK_ALREADY", YINGFU);
				rd.add("TA_ZT_DZJL_HIS", "FK_SHOULD", YIFUJE);
				rd.add("TA_ZT_DZJL_HIS", "FK_THISTIME", BCFK);
				coreDAO.insert("TA_ZT_DZJL_HIS", rd, conn);
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
}
