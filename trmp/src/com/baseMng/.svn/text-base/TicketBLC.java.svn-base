package com.baseMng;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.string.PyUtil;

public class TicketBLC extends DBBLC{
	public TicketBLC(){
		this.entityName="TA_TICKET";
	}
	public int getAllTicket(BizData rd,BizData sd)throws SQLException{
		String orgid = sd.getString("orgid");
		rd.add("TA_TICKET", "orgid", 0, orgid);
		this.queryPage(rd, sd);
		return 1;
	}
	public int addTicket(BizData rd,BizData sd)throws SQLException{
		
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			int id=this.queryMaxIDByPara("TA_TICKET", "TICKET_ID", null);
			rd.add("TA_TICKET", "TICKET_ID",id);
			//生成拼音码
			String cmpName = rd.getString("TA_TICKET", "CMPNY_NAME", 0);
			String nameCode = PyUtil.get1stLetterOf4Chars(cmpName);
			rd.add("TA_TICKET", "NAMECODE", 0, nameCode);
			rd.add("TA_TICKET", "ORGID", 0, sd.getString("orgid"));
			coreDAO.insert("TA_TICKET", rd, conn);
			rd.add("city_id", rd.getString("TA_TICKET", "CITY_ID", 0));
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			coreDAO.rollbackTrasct(conn);
		}finally{
			if(null!=conn){
				conn.close();
			}
		}
		return 1;
	}
	public int getTicketById(BizData rd,BizData sd)throws SQLException{
		String teamId = rd.getStringByDI("TA_TICKET", "TICKET_ID", 0);
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*,x.name,x.pid,x.layer\n").append(
				"from ta_ticket t,xzqh x\n");
		sql.append("where t.city_id=x.id(+)\n").append("and t.ticket_id=")
				.append(teamId);
		coreDAO.executeQuery(sql.toString(), "TA_TICKETs", rd);
		return 1;
	}
	public int updateTicket(BizData rd,BizData sd)throws SQLException{
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			//生成拼音码
			String cmpName = rd.getString("TA_TICKET", "CMPNY_NAME", 0);
			String nameCode = PyUtil.get1stLetterOf4Chars(cmpName);
			rd.add("TA_TICKET", "NAMECODE", 0, nameCode);
			
			coreDAO.update("TA_TICKET",rd,conn);
			rd.add("city_id", rd.getString("TA_TICKET", "CITY_ID", 0));
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			coreDAO.rollbackTrasct(conn);
		}finally{
			if(null!=conn){
				conn.close();
			}
		}
		return 1;
	}
	public int delTicket(BizData rd,BizData sd)throws SQLException{
		
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			String id=rd.getString("id");
			String sql="delete from TA_TICKET a where a.TICKET_ID="+id;
			coreDAO.executeUpdate(sql, conn);
			rd.add("city_id", rd.getString("city_id"));
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			coreDAO.rollbackTrasct(conn);
		}finally{
			if(null!=conn){
				conn.close();
			}
		}
		return 1;
	}
	public int batchDelTicket(BizData rd,BizData sd)throws SQLException{
		rd.add("ywlb", rd.getString("ywlb"));
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			String rows[]=rd.getRowIDs("TICKET");
			for(int a=0;a<rows.length;a++){
				String id=rd.getString("TICKET", "CHECKBOX", rows[a]);
				String sql="delete from TA_TICKET a where a.TICKET_ID="+id;
				coreDAO.executeUpdate(sql, conn);
			}
			rd.add("city_id", rd.getString("city_id"));
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			coreDAO.rollbackTrasct(conn);
		}finally{
			if(null!=conn){
				conn.close();
			}
		}
		return 1;
	}
	public int getTicketByName(BizData rd,BizData sd)throws SQLException{
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_TICKET", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_TICKET", "pageSize"));
		String name=rd.getString("name");
		String orgid = sd.getString("orgid");
		String sql="select * from TA_TICKET a where a.CMPNY_NAME like '%"+name+"%' and a.orgid='"+orgid+"'";
		coreDAO.executeQueryPage(sql, "TA_TICKETs", pageNO, pageSize, rd);
		return 1;
	}
	
}