package com.trm.plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class TravePlanBLC extends DBBLC{
	public TravePlanBLC(){
		this.entityName="TA_G_NEXT_AGENCY";
	}
	public int p_traveInfo(BizData rd,BizData sd){
		String id=rd.getString("id"); 
		String sql="select a.id,a.groupid,a.privince_id,a.city_id,a.travel_agc_id,"+
			"a.pay_money,a.details,nvl(a.yfzk,0)yfzk,a.confirm_file from ta_g_next_agency a where a.groupid="+id;
		try {
			coreDAO.executeQuery(sql, "planTraveInfo", rd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	public int p_editTrave(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		try{
			coreDAO.beginTrasct(conn);
			if("up".equals(rd.getString("state"))){
				String sql="";
				String id=rd.getString("id");
				byte[] confirm=(byte[]) rd.get("confirm");
				
				if(null!=confirm){
					 sql="update ta_g_next_agency a set a.privince_id=?,a.city_id=?,a.travel_agc_id=?,a.pay_money=?,a.details=?," +
						"a.user_no=?,a.confirm_file=? where a.id="+id;
				}else {
					 sql="update ta_g_next_agency a set a.privince_id=?,a.city_id=?,a.travel_agc_id=?,a.pay_money=?,a.details=?," +
						"a.user_no=? where a.id="+id;
				}
				String proid=rd.getStringByDI("ta_g_next_agency", "privince_id", 0);
				String city_id=rd.getStringByDI("ta_g_next_agency", "city_id", 0);
				String traveid=rd.getStringByDI("ta_g_next_agency", "TRAVEL_AGC_ID", 0);
				String pay_money=rd.getStringByDI("ta_g_next_agency", "pay_money", 0);
				String user_no=rd.getString("userno");
				String details=rd.getString("TRAVE_DETAILS");
				PreparedStatement ps=conn.prepareStatement(sql);
				ps.setString(1, proid);
				ps.setString(2, city_id);
				ps.setString(3, traveid);
				ps.setString(4, pay_money);
				ps.setBytes(5, details.getBytes("GBK"));
				ps.setString(6, user_no);
				if(null!=confirm){
				ps.setBytes(7, confirm);
				}
				ps.executeUpdate();
				ps.close();
				ps=null;
				coreDAO.commitTrasct(conn);
			}else{
			String sql="insert into ta_g_next_agency(ID,groupid,privince_id,city_id,travel_agc_id,pay_money,details,user_no,CONFIRM_FILE) " +
					" values(?,?,?,?,?,?,?,?,?)";
			int id=this.queryMaxIDByPara("ta_g_next_agency", "ID", null);
			String groupid=rd.getStringByDI("TA_GROUP_BASE", "ID", 0);
			String proid=rd.getStringByDI("ta_g_next_agency", "privince_id", 0);
			String city_id=rd.getStringByDI("ta_g_next_agency", "city_id", 0);
			String traveid=rd.getStringByDI("ta_g_next_agency", "TRAVEL_AGC_ID", 0);
			String pay_money=rd.getStringByDI("ta_g_next_agency", "pay_money", 0);
			String details=rd.getString("TRAVE_DETAILS");
			String user_no=rd.getString("userno");
			byte[] confirm=(byte[]) rd.get("confirm");
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, groupid);
			ps.setString(3, proid);
			ps.setString(4, city_id);
			ps.setString(5, traveid);
			ps.setString(6, pay_money);
			ps.setBytes(7, details.getBytes("GBK"));
			ps.setString(8, user_no);
			ps.setBytes(9, confirm);
			ps.executeUpdate();
			ps.close();
			ps=null;
			coreDAO.commitTrasct(conn);
			}
		}catch(Exception e){
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return 1;
	}
	public int getConfirm(BizData rd,BizData sd){
		String id=rd.getString("id");
		String sql="select t.confirm_file from ta_g_next_agency t where t.id="+id;
		try {
			coreDAO.executeQuery(sql, "confirmInfo", rd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	public int getNeedMon(BizData rd,BizData sd){
		int pageNO=Integer.parseInt(rd.getStringAttr("ta_g_next_agency", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("ta_g_next_agency", "pageSize"));
		String sql="select b.cmpny_name,a.id,c.g_id groupid,a.pay_money,nvl(a.yfzk,0)yfzk " +
		" from ta_g_next_agency a ,ta_travelagency b,ta_group_base c  where a.travel_agc_id=b.travel_agc_id and a.groupid=c.id";
		try {
			coreDAO.executeQueryPage(sql, "ta_g_next_agencys", pageNO, pageSize, rd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	public int getPayMoneyInfo(BizData rd,BizData sd){
		String id=rd.getString("id");
		String sql="select a.id,a.djsid,a.fkje,a.kxmc,a.fkrq,a.fkr from  ta_ztfk_paydetail a where a.djsid="+id;
		try {
			coreDAO.executeQuery(sql, "payMoenyList", rd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	public int addPayMoney(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		String[] rows=rd.getRowIDs("TA_ZTFK_PAYDETAIL");
		String p_djsid=rd.getStringByDI("TA_G_NEXT_AGENCY", "ID", 0);
		try{
			coreDAO.beginTrasct(conn);
			if("up".equals(rd.getString("state"))){
				String sql="delete from TA_ZTFK_PAYDETAIL a where a.djsid="+p_djsid;
				coreDAO.executeUpdate(sql, conn);
			}
			if(null!=rows){
				for(int r=0;r<rows.length;r++){
					int id=this.queryMaxIDByPara("TA_ZTFK_PAYDETAIL", "ID", null);
					rd.add("TA_ZTFK_PAYDETAIL", "ID",r,id);
					rd.add("TA_ZTFK_PAYDETAIL", "DJSID",r,p_djsid);
					rd.add("TA_ZTFK_PAYDETAIL", "FKR",r,rd.getString("userno"));
				}
			}
			coreDAO.insert("TA_ZTFK_PAYDETAIL",rd,conn);
			coreDAO.update("TA_G_NEXT_AGENCY",rd,conn);
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return 1;
	}
}
