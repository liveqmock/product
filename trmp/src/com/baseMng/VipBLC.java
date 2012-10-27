package com.baseMng;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class VipBLC extends DBBLC{
	public VipBLC(){
		this.entityName="TA_VIP_INFO";
	}
	public int getAllVipInfo(BizData rd,BizData sd){
		try {
			this.queryPage(rd, sd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	public int getVipById(BizData rd,BizData sd){
		try {
			this.query(rd, sd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	public int addVip(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		try{
			coreDAO.beginTrasct(conn);
			int id=this.queryMaxIDByPara("TA_VIP_INFO", "ID", null);
			rd.add("TA_VIP_INFO", "ID",id);
			coreDAO.insert("TA_VIP_INFO",rd,conn);
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
	public int updateVip(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		try{
			coreDAO.beginTrasct(conn);
			coreDAO.update("TA_VIP_INFO",rd,conn);
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
	public int delVip(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		try{
			coreDAO.beginTrasct(conn);
			coreDAO.deleteEntity("TA_VIP_INFO", rd, conn);
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
	public int batchDelVip(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		try{
			coreDAO.deleteEntity("TA_VIP_INFO", rd,conn);
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
	public int getVipBylike(BizData rd,BizData sd){
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_VIP_INFO", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_VIP_INFO", "pageSize"));
		String vip_no=rd.getString("vip_no");
		String vip_name=rd.getString("vip_name");
		String sql="select a.id,a.vip_no,a.vip_name,a.id_card,a.vip_tel,"+
			"a.address,a.vip_integral,a.apply_date "+
			" from ta_vip_info a where a.vip_no like '%"+vip_no+"%' and a.vip_name like '%"+vip_name+"%'";
		try {
			coreDAO.executeQueryPage(sql, "TA_VIP_INFOs", pageNO, pageSize, rd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
}
