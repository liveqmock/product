package com.baseMng;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/** 保险档案事务处理类
 * @author Kale
 * 2011-11-23 18:08
 */
public class InsuranceFileBLC extends DBBLC {
	public InsuranceFileBLC(){
		this.entityName="TA_ZT_INSURANCE";
	}
	
	public int queryInsurance(BizData rd,BizData sd) throws SQLException {
		String sql = "select * from ta_zt_insurance t";
		coreDAO.executeQuery(sql,"TA_ZT_INSURANCEs", rd);
		String lineId = rd.getString("lineID");
		sql = "select * from ta_zt_lineandinsurance t where 1=1 and t.line_id='"+lineId+"'";
		coreDAO.executeQuery(sql,"TA_ZT_LINEANDINSURANCEs", rd);
		return 999;
	}
	/** 初始化保险档案列表信息 (包括查询保险档案信息)
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int initInsuranceFileList(BizData rd,BizData sd){
		String pageNOStr = rd.getString("pageNO");
		String pageSizeStr = rd.getString("pageSize");
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		
		//获取查询条件
		String BXLBMC=rd.getString("BXLBMC");
		
		String sql =  "select * from TA_ZT_INSURANCE a where a.orgid="+sd.getString("orgid")+" \n";
		if(!"".equals(BXLBMC))
			   sql += "and a.BXLBMC like '%"+BXLBMC+"%'";
	
		try {
			coreDAO.executeQueryPage(sql, "TA_ZT_INSURANCEs", pageNO, pageSize, rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
	}
	/** 添加(修改)保险档案
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int submitInsuranceFile(BizData rd,BizData sd){
		Connection conn = coreDAO.getConnection();
		String insuranceID = rd.getString("TA_ZT_INSURANCE","ID",0);//获取保险编号
		String cbFs = rd.getString("TA_ZT_INSURANCE","CBFS",0);
		if("".equals(cbFs)) cbFs = "0";
			try {
				coreDAO.beginTrasct(conn);
				if(!"".equals(insuranceID)){
					rd.addAttr("TA_ZT_INSURANCE","ID","0","oldValue",insuranceID);
					rd.add("TA_ZT_INSURANCE","CBFS",cbFs);
					coreDAO.update("TA_ZT_INSURANCE", rd, conn);
				}else{
					int dID = queryMaxIDByPara("TA_ZT_INSURANCE", "ID", null);
					rd.add("TA_ZT_INSURANCE","ID",dID);
					rd.add("TA_ZT_INSURANCE","CBFS",cbFs);
					rd.add("TA_ZT_INSURANCE","ORGID",sd.getString("orgid"));
					coreDAO.insert("TA_ZT_INSURANCE", rd, conn);
				}
				coreDAO.commitTrasct(conn);
			} catch (SQLException e) {
				try {
					coreDAO.rollbackTrasct(conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}finally{
				try{
					if(null != conn){
						conn.close();
						conn = null;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		return 999;
	}
	/** 初始化保险档案信息
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int initInsuranceFile(BizData rd,BizData sd){
		String insuranceID = rd.getString("TA_ZT_INSURANCE","ID",0);//获取保险编号
		if(!"".equals(insuranceID)){
			try {
				rd.add("TA_ZT_INSURANCE","ID", insuranceID);
				coreDAO.select(rd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 999;
	}
	/** 删除保险档案信息
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int delInsuranceFile(BizData rd,BizData sd){
		Connection conn = coreDAO.getConnection();
		String insuranceID = rd.getString("TA_ZT_INSURANCE","ID",0);//获取保险编号
		if(!"".equals(insuranceID)){
			try {
				coreDAO.beginTrasct(conn);
				BizData del1 = new BizData();
				del1.add("TA_ZT_INSURANCE", "ID", insuranceID);
				coreDAO.delete(del1, conn);
				del1.remove("TA_ZT_INSURANCE");
				BizData del2 = new BizData();
				del2.add("TA_ZT_LINEANDINSURANCE", "INSURANCEID", insuranceID);
				coreDAO.delete(del2, conn);//删除线路绑定保险的档案信息
				del2.remove("TA_ZT_LINEANDINSURANCE");
				coreDAO.commitTrasct(conn);
			} catch (SQLException e) {
				try {
					coreDAO.rollbackTrasct(conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}finally{
				try{
					if(null != conn){
						conn.close();
						conn = null;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
		}
		return 999;
	}
}
