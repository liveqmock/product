package com.trm.baoxiao;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class TicketBXBLC extends DBBLC{
	public TicketBXBLC(){
		this.entityName="";
	}
	public int bx_getBXTicket(BizData rd, BizData sd){
		String id=rd.getString("id");
		String sql="";
		String sql2="";
		String sql3="";
		sql="select a.dmz,a.dmsm1 from dmsm a where a.dmlb="+15;
		//查询报销信息
		 sql2="select a.id,a.groupid,a.privince_id,a.city_id,a.ticket_id,a.xfje,a.qdje, \n"+
				"a.sxf,a.xfje+a.qdje+a.sxf gj,a.comments from ta_bx_ticket a where a.groupid="+id;
		//票务明细
		 sql3="select a.id,b.id,b.ticketid,b.tickettype,b.cchb,b.qd,b.xf,b.sxf,\n"+
				"b.zs,b.cfz,b.zdz,b.tickettime,b.price from ta_bx_ticket a,TA_BX_TICKETDETAIL b \n"+
				"where  a.id=b.ticketid and a.groupid="+id;
		 try {
			 coreDAO.executeQuery(sql, "ticketType", rd);
			coreDAO.executeQuery(sql2, "p_ticket_list", rd);
			coreDAO.executeQuery(sql3, "p_detail_list", rd);
			if(rd.getTableRowsCount("p_ticket_list")==0){
				//查询计调信息
				 sql2="select a.id,a.groupid,a.privince_id,a.city_id,a.ticket_id,a.xfje,a.qdje, \n"+
						"a.sxf,a.xfje+a.qdje+a.sxf gj,a.comments from ta_g_ticket_do a where a.groupid="+id;
				//票务明细
				 sql3="select a.id,b.id,b.ticketid,b.tickettype,b.cchb,b.qd,b.xf,b.sxf,\n"+
						"b.zs,b.cfz,b.zdz,b.tickettime,b.price from ta_g_ticket_do a,TA_G_TICKETDETAIL b \n"+
						"where  a.id=b.ticketid and a.groupid="+id;
				 coreDAO.executeQuery(sql2, "p_ticket_list", rd);
				 coreDAO.executeQuery(sql3, "p_detail_list", rd);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 1;
	}
	public int bx_editTicket(BizData rd,BizData sd){
		String group_id=rd.getString("groupid");
		String[] ticketRows=rd.getRowIDs("TA_BX_TICKET");
		Connection conn=coreDAO.getConnection();
		//团票务信息
		BizData rd2=new BizData();
		try{
			coreDAO.beginTrasct(conn);
			if("up".equals(rd.getString("state"))){
				//删除票务明细
				coreDAO.deleteEntity("ta_bx_ticketdetail", rd, conn);
				String[] rowsId=rd.getRowIDs("ta_bx_ticketdetail");
				for(int r=0;r<rowsId.length;r++){
					String id=rd.getString("ta_bx_ticketdetail", "TICKETID", rowsId[r]);
					String sql="delete from TA_BX_TICKET a where a.id="+id;
					coreDAO.executeUpdate(sql, conn);
				}
				rd.remove("ta_bx_ticketdetail");
			}
			for(int t=0;t<ticketRows.length;t++){
				int p_tid=this.queryMaxIDByPara("TA_BX_TICKET", "ID", null);
				//票务明细
				String []t_d_rows=rd.getRowIDs("ta_bx_ticketdetail"+t);
				for(int d=0;d<t_d_rows.length;d++){
					int d_id=this.queryMaxIDByPara("ta_bx_ticketdetail", "ID", null);
					rd.add("ta_bx_ticketdetail"+t,"ID",t_d_rows[d],d_id);
					rd.add("ta_bx_ticketdetail"+t,"TICKETID",t_d_rows[d],p_tid);
				}
				rd2.copyEntity(rd, "ta_bx_ticketdetail"+t, "ta_bx_ticketdetail");
				coreDAO.insert("ta_bx_ticketdetail",rd2,conn);
				
				rd.add("TA_BX_TICKET", "ID",t,p_tid);
				rd.add("TA_BX_TICKET", "GROUPID",t,group_id);
				rd.add("TA_BX_TICKET", "USER_NO",t,rd.getString("userno"));
			}
			coreDAO.insert("TA_BX_TICKET",rd,conn);
			String sql4="update TA_GROUP_BASE set BX_TICKET_XF="+rd.getString("xfzj")+", \n" +
					"BX_TICKET_QD="+rd.getString("qdzj")+",BX_TICKET_SXF="+rd.getString("sxfzj");
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
