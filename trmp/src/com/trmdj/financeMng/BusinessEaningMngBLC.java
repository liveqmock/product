package com.trmdj.financeMng;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


/**
  * @ClassName: BusinessEaningMngBLC
  * @Description: TODO 团组团社  签单清款
  * @author Comsys-Administrator
  * @date 2012-3-30 下午12:55:04
  *
  */
public class BusinessEaningMngBLC extends DBBLC {
	
	public BusinessEaningMngBLC(){
		
		entityName="TA_DJ_TZTS";
	}
	
	public int businessEarningList(BizData rd, BizData sd) {
		
		String bDate = rd.getString("bDate");
		String eDate = rd.getString("eDate");
		String groupID = rd.getString("groupID");
		String state = rd.getString("state");
		
		StringBuffer sql4Earning = new StringBuffer();
		sql4Earning.append("select g.id tid,t.id,t.ztsid,tr.cmpny_name,nvl(t.yfk,0) yfk,decode(t.qkzt,'N','未清款','Y','已清款') qkzt_yfk \n");
		sql4Earning.append(" from ta_dj_group g, ta_dj_tzts t,ta_travelagency tr \n");
		sql4Earning.append("where g.id=t.tid and g.orgid=t.orgid and t.ztsid=tr.travel_agc_id and t.orgid=tr.orgid\n");
		if(!bDate.equals("") && !eDate.equals("")){
			sql4Earning.append("and g.begin_date >= to_date('");
			sql4Earning.append(bDate);
			sql4Earning.append("','yyyy-mm-dd') and g.begin_date <= to_date('");
			sql4Earning.append(eDate);
			sql4Earning.append("','yyyy-mm-dd') \n");
		}
		if(!state.equals("")){
			sql4Earning.append("and t.qkzt='");
			sql4Earning.append(state);
			sql4Earning.append("'");
		}
		sql4Earning.append("and g.state='6' and g.orgid='"+sd.getString("orgid")+"'\n");
		sql4Earning.append("and t.yk>'0' \n");

		
		if(!groupID.equals("")){
			sql4Earning.append("and g.id='");
			sql4Earning.append(groupID);
			sql4Earning.append("'");
		}
		
		StringBuffer sql4Count = new StringBuffer();
		sql4Count.append("select count(t.id) sl,t.tid \n");
		sql4Count.append(" from ta_dj_group g, ta_dj_tzts t \n");
		sql4Count.append("where g.id=t.tid and g.orgid=t.orgid\n");
		if(!bDate.equals("") && !eDate.equals("")){
			sql4Count.append("and g.begin_date >= to_date('");
			sql4Count.append(bDate);
			sql4Count.append("','yyyy-mm-dd') and g.begin_date <= to_date('");
			sql4Count.append(eDate);
			sql4Count.append("','yyyy-mm-dd') \n");
		}
		if(!state.equals("")){
			sql4Earning.append("and t.qkzt='");
			sql4Earning.append(state);
			sql4Earning.append("'");
		}
		sql4Count.append("and g.state='6' and g.orgid='"+sd.getString("orgid")+"'\n");
		sql4Count.append("and t.yk>'0' \n");

		if(!groupID.equals("")) {
			sql4Count.append("and g.id='");
			sql4Count.append(groupID);
			sql4Count.append("' \n");
		}
		sql4Count.append("group by t.tid");
		try {
			coreDAO.executeQuery(sql4Earning.toString(), "rsEarning", rd);
			coreDAO.executeQuery(sql4Count.toString(), "rsEaning4Count", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		rd.add("bDate", bDate);
		rd.add("eDate", eDate);
		rd.add("groupID", groupID);
		rd.add("state", state);
		return 9;
	}
	
	public int beInit4Update(BizData rd, BizData sd) {
		
		String id = rd.getString("id");
		StringBuffer sql = new StringBuffer();
		sql.append("select t.id,t.orgid,t.tid,t.ztsmc,t.ztsid,t.csid,t.crrs,t.etrs\n");
		sql.append(",t.crjg,t.etjg,t.yszk,t.yfk,t.yk,t.qkzt,sum(nvl(k.bcqkje,0)) qkjehj\n");
		sql.append("from ta_dj_tzts t, TA_DJ_TZTSQKJL k\n");
		sql.append("where t.id=k.tztsid(+) and t.orgid=k.orgid(+) and t.orgid='"+sd.getString("orgid")+"'\n");
		sql.append("and t.id="+id).append("\n");
		sql.append("group by t.id,t.orgid,t.tid,t.ztsmc,t.ztsid,t.csid,t.crrs,t.etrs,t.crjg,t.etjg,t.yszk,t.yfk,t.yk,t.qkzt\n");
		try {
			coreDAO.executeQuery(sql.toString(), "rsBE4Update", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 3;
	}
	
	public int showHistory(BizData rd, BizData sd){
		
		String id = rd.getString("id");
		StringBuffer sql = new StringBuffer();
		sql.append("select t.id,t.orgid,t.tid,t.ztsmc,t.ztsid,t.csid,t.yszk,t.yfk,t.qkzt,k.bcqkje,u.username,k.bcqkrq\n")
			.append("from ta_dj_tzts t, TA_DJ_TZTSQKJL k,drmuser u \n")
			.append("where t.id=k.tztsid(+) and t.orgid=k.orgid(+) and k.userno=u.userno\n")
			.append("and t.orgid='").append(sd.getString("orgid")).append("'\n")
			.append("and t.id=").append(id);
		try {
			coreDAO.executeQuery(sql.toString(), "rsHistory", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 9;
	}
	
	public int update(BizData rd, BizData sd) {

		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			String qkzt = rd.getStringByDI("TA_DJ_TZTS", "qkzt", 0);
			if(qkzt.equals("Y"))
				coreDAO.update("TA_DJ_TZTS", rd, conn);
			int clearGroupMoney = queryMaxIdByOrg("TA_DJ_TZTSQKJL", "id", sd.getString("orgid"), null);
			rd.add("TA_DJ_TZTSQKJL", "id", clearGroupMoney);
			rd.add("TA_DJ_TZTSQKJL", "BCQKRQ", rd.sdfDate.format(new Date()));
			coreDAO.insert("TA_DJ_TZTSQKJL", rd, conn);
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
		return 8;
	}
}