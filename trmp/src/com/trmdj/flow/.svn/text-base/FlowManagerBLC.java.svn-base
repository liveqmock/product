package com.trmdj.flow;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.databus.BizData;


public class FlowManagerBLC extends DBBLC {
	public FlowManagerBLC(){
		this.entityName="TA_FLOWDEFINITION";
	}
	public int getAllFlowInfo(BizData rd,BizData sd){
		try {
			this.queryPage(rd, sd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	
	public int getFlowById(BizData rd,BizData sd){
		try {
			this.query(rd, sd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	
	public int updateFlow(BizData rd,BizData sd){
		
		try{
			coreDAO.update(rd);
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		return 1;
	}
	public int delFlow(BizData rd,BizData sd) {
		
		Connection conn = coreDAO.getConnection();
		try {
			String[] rowIDs = rd.getRowIDs("TA_FLOWDEFINITION");
			
			coreDAO.beginTrasct(conn);
			for (int i = 0; i < rowIDs.length; i++) {
				
				String defintion = rd.getString("TA_FLOWDEFINITION", "DEFINITIONID", rowIDs[i]);
				rd.add("TA_FLOWNODE", "DEFINITIONID", defintion);
				coreDAO.deleteRow("TA_FLOWDEFINITION", rowIDs[i], rd, conn);
				coreDAO.deleteEntity("TA_FLOWNODE", rd, conn);
				rd.remove("TA_FLOWNODE");
			}
			coreDAO.commitTrasct(conn);

		}catch(Exception e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 1;
	}
	
	public int getFlowBylike(BizData rd,BizData sd){
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_FLOWDEFINITION", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_FLOWDEFINITION", "pageSize"));
		String flow_name=rd.getString("flow_name");
		String orgid = sd.getString("orgid");
		String sql="select * from TA_FLOWDEFINITION a where a.FLOWNAME like '%"+flow_name+"%' and a.orgid='"+orgid+"'";
		try {
			coreDAO.executeQueryPage(sql, "TA_FLOWDEFINITIONS", pageNO, pageSize, rd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	
	public int updateNode(BizData rd, BizData sd) {
		Connection conn = coreDAO.getConnection();
		String rowIds[] = rd.getRowIDs("TA_FLOWNODE");
		String name = rd.getStringByDI("TA_FLOWNODE", "NODENAME", 0);
		String pid = rd.getStringByDI("TA_FLOWNODE", "PID", 0);
		String rolid = rd.getStringByDI("TA_FLOWNODE", "ROLEID", 0);
		String flid = rd.getStringByDI("TA_FLOWNODE", "DEFINITIONID", 0);
		String nodeid = rd.getStringByDI("TA_FLOWNODE", "NODEID", 0);
		String desc = rd.getStringByDI("TA_FLOWNODE", "NODEDESC", 0);
		String state = rd.getStringByDI("TA_FLOWNODE", "NODESTATE", 0);
			try {
			coreDAO.beginTrasct(conn);					
			for(int i=0;i<rowIds.length;i++){
				//È¡Ö÷¼ü
				int id=this.queryMaxIDByPara("TA_FLOWNODE", "NODEID", null);
				
				rd.add("TA_FLOWNODE", "NODENAME",rowIds[i],name);
				rd.add("TA_FLOWNODE", "NODEDESC",rowIds[i],desc);
				rd.add("TA_FLOWNODE", "PID",rowIds[i],pid);
				rd.add("TA_FLOWNODE", "ROLEID",rowIds[i],rolid);
				rd.add("TA_FLOWNODE", "DEFINITIONID",rowIds[i],flid);
				rd.add("TA_FLOWNODE", "NODEID",rowIds[i],nodeid);
				rd.add("TA_FLOWNODE", "NODESTATE",rowIds[i],state);
			}

			coreDAO.insert("TA_FLOWNODE", rd,conn);
			coreDAO.commitTrasct(conn);
			
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (null != conn) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 1;
	}
}