package com.baseMng;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class GatherHisBLC extends DBBLC{
	public GatherHisBLC(){
		this.entityName="TA_ZT_GATHER_HIS";
	}
	public int getAllGather(BizData rd,BizData sd)throws SQLException{
		rd.add("TA_ZT_GATHER_HIS","orgid", sd.getString("orgid"));
		this.queryPage(rd, sd);
		return 1;
	}
	public int getGatherById(BizData rd,BizData sd)throws SQLException{
		rd.add("TA_ZT_GATHER_HIS","orgid", sd.getString("orgid"));
		this.query(rd, sd);
		return 1;
	}
	public int getGatherByName(BizData rd,BizData sd)throws SQLException{
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_ZT_GATHER_HIS", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_ZT_GATHER_HIS", "pageSize"));
		String name=rd.getString("name");
		String sql="select * from TA_ZT_GATHER_HIS a where a.GATHER like '%"+name+"%' and a.orgid='"+sd.getString("orgid")+"'";
		coreDAO.executeQueryPage(sql, "TA_ZT_GATHER_HISs", pageNO, pageSize, rd);
		return 1;
	}
	public int upGather(BizData rd,BizData sd)throws SQLException{
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			rd.add("TA_ZT_GATHER_HIS", "ADD_M_COUNT", rd.getStringByDI("TA_ZT_GATHER_HIS", "ADD_M_COUNT", 0).equals("")?"0":rd.getStringByDI("TA_ZT_GATHER_HIS", "ADD_M_COUNT", 0));
			this.update(rd, conn);
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
	public int addGather(BizData rd,BizData sd)throws SQLException{
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			int id=this.queryMaxIDByPara("TA_ZT_GATHER_HIS", "GATHER_ID", null);
			rd.add("TA_ZT_GATHER_HIS", "GATHER_ID",id);
			rd.add("TA_ZT_GATHER_HIS","orgid", sd.getString("orgid"));
			rd.add("TA_ZT_GATHER_HIS", "ADD_M_COUNT",rd.getStringByDI("TA_ZT_GATHER_HIS", "ADD_M_COUNT", 0).equals("")?"0":rd.getStringByDI("TA_ZT_GATHER_HIS", "ADD_M_COUNT", 0));
			coreDAO.insert("TA_ZT_GATHER_HIS",rd,conn);
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
	public int delGather(BizData rd,BizData sd)throws SQLException{
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			String id=rd.getString("id");
			String sql="delete from TA_ZT_GATHER_HIS a where a.GATHER_ID='"+id+"' and a.orgid='"+sd.getString("orgid")+"'";
			coreDAO.executeUpdate(sql, conn);
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
	public int batchDelGather(BizData rd,BizData sd)throws SQLException{
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			String[] rowsId=rd.getRowIDs("GATHER");
			for(int a=0;a<rowsId.length;a++){
				String sql="delete from TA_ZT_GATHER_HIS a where a.GATHER_ID='"+rd.getString("GATHER","CHECKBOX",rowsId[a])+"' and a.orgid='"+sd.getString("orgid")+"'";
				coreDAO.executeUpdate(sql, conn);
			}
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			coreDAO.rollbackTrasct(conn);
		}finally{
			conn.close();
		}
		return 1;
	}
}
