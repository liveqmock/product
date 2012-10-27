package com.trm.plan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class TicketPlanBLC extends DBBLC{
	public TicketPlanBLC(){
		this.entityName="";
	} 
	public int getTicketplan(BizData rd,BizData sd){
		String id=rd.getString("id");
		try{
		String sql="select x.id,x.g_id,x.line_id,x.line_name,x.begin_date,x.end_date,x.leader_name, \n"+
		"x.leader_tel,x.count_ticket_qd,x.count_ticket_xf,x.count_ticket_sxf,x.count_ticket_qd+x.count_ticket_xf+x.count_ticket_sxf count_ticket_zj,x.comments,x.children_count,x.adult_count,x.zdrs,x.kctrs,nvl(y.ysrs,0)ysrs,zdrs-nvl(y.ysrs,0) syrs from \n"+
		"(select a.id,a.g_id,a.line_id,b.line_name,a.begin_date,a.end_date,a.leader_name, \n"+
		" a.leader_tel,a.children_count,a.count_ticket_qd,a.count_ticket_xf,a.count_ticket_sxf,a.adult_count,a.comments,b.maxpersoncount zdrs,b.minpersoncount kctrs \n"+
		"from ta_group_base a,ta_line_mng b where a.line_id=b.line_id and a.id="+id+") x,\n"+
		"(select b.g_id,nvl(sum(b.ysrs),0) ysrs \n" +
		"from ta_group_base a,ta_groupmoney b where \n" +
		"a.id=b.g_id and b.dd_confirm=1 \n" +
		"group by b.g_id) y where x.id=y.g_id(+)";
		coreDAO.executeQuery(sql, "groupInfo", rd);
		String sql2="select a.dmz,a.dmsm1 from dmsm a where a.dmlb="+15;
		coreDAO.executeQuery(sql2, "ticketType", rd);
		
		//查询计调信息
		String sql3="select a.id,a.groupid,a.privince_id,a.city_id,a.ticket_id,a.xfje,a.qdje, \n"+
				"a.sxf,a.xfje+a.qdje+a.sxf gj,a.comments from ta_g_ticket_do a where a.groupid="+id;
		coreDAO.executeQuery(sql3, "p_ticket_list", rd);
		//票务明细
		String sql4="select a.id,b.id,b.ticketid,b.tickettype,b.cchb,b.qd,b.xf,b.sxf,\n"+
				"b.zs,b.cfz,b.zdz,b.tickettime,b.price from ta_g_ticket_do a,TA_G_TICKETDETAIL b \n"+
				"where  a.id=b.ticketid and a.groupid="+id;
		coreDAO.executeQuery(sql4, "p_detail_list", rd);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 1;
	}
	public int getTicketInfo(BizData rd,BizData sd){
		String id=rd.getString("id");
		try{
		String sql="select a.ticket_id,a.city_id,a.cmpny_name,a.cmpny_address,a.business_name,\n"+
				"a.business_phone,a.business_mobile from ta_ticket a where a.ticket_id="+id;
		coreDAO.executeQuery(sql, "ticketInfo", rd);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 1;
	}
	public int p_edit_Ticket(BizData rd,BizData sd){
		String group_id=rd.getString("group_fkId");
		String[] ticketRows=rd.getRowIDs("TA_G_TICKET_DO");
		Connection conn=coreDAO.getConnection();
		//团票务信息
		BizData rd2=new BizData();
		try{
			coreDAO.beginTrasct(conn);
			if("up".equals(rd.getString("state"))){
				//删除票务明细
				coreDAO.deleteEntity("ta_g_ticketdetail", rd, conn);
				String[] rowsId=rd.getRowIDs("ta_g_ticketdetail");
				for(int r=0;r<rowsId.length;r++){
					String id=rd.getString("ta_g_ticketdetail", "TICKETID", rowsId[r]);
					String sql="delete from TA_G_TICKET_DO a where a.id="+id;
					coreDAO.executeUpdate(sql, conn);
				}
				rd.remove("ta_g_ticketdetail");
			}
			for(int t=0;t<ticketRows.length;t++){
				int p_tid=this.queryMaxIDByPara("TA_G_TICKET_DO", "ID", null);
				//票务明细
				String []t_d_rows=rd.getRowIDs("TA_G_TICKETDETAIL"+t);
				for(int d=0;d<t_d_rows.length;d++){
					int d_id=this.queryMaxIDByPara("TA_G_TICKETDETAIL", "ID", null);
					rd.add("TA_G_TICKETDETAIL"+t,"ID",t_d_rows[d],d_id);
					rd.add("TA_G_TICKETDETAIL"+t,"TICKETID",t_d_rows[d],p_tid);
				}
				rd2.copyEntity(rd, "TA_G_TICKETDETAIL"+t, "TA_G_TICKETDETAIL");
				coreDAO.insert("TA_G_TICKETDETAIL",rd2,conn);
				
				rd.add("TA_G_TICKET_DO", "ID",t,p_tid);
				rd.add("TA_G_TICKET_DO", "GROUPID",t,group_id);
				rd.add("TA_G_TICKET_DO", "USER_NO",t,rd.getString("userno"));
			}
			coreDAO.insert("TA_G_TICKET_DO",rd,conn);
			String sql4="update TA_GROUP_BASE set COUNT_TICKET_XF="+rd.getString("xfzj")+", \n" +
					"COUNT_TICKET_QD="+rd.getString("qdzj")+",COUNT_TICKET_SXF="+rd.getString("sxfzj");
			coreDAO.executeUpdate(sql4, conn);
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			try {
				coreDAO.rollbackTrasct(conn);
				e.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
