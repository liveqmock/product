package com.trmdj.flow;



import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


public class FlowNodeMNGBLC extends DBBLC {
	public FlowNodeMNGBLC(){
		this.entityName="TA_FLOWNODE";
	}
	public int getAllFlowInfo(BizData rd,BizData sd){
		try {
			this.queryPage(rd, sd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	public int creatMaxNodeId(BizData rd,BizData sd){
		int maxID = queryMaxIDByPara("TA_FLOWNODE", "NODEID", null);
		rd.add("maxid",maxID);
		return 1;
	}
	public int getNodeByFlowID(BizData rd,BizData sd){
		List<BizData> list = new ArrayList<BizData>();
		try {
			int rows = query(rd, sd);
			if(rows == 0){
				rd.add("DEFINITIONID", rd.getStringByDI("TA_FLOWNODE", "DEFINITIONID", 0));		
			}		
			int i=0;
			sortData(rd, "-1", rd.getTableRowsCount("TA_FLOWNODEs"), list, i);
			rd.add("list",list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	public int updateNode(BizData rd,BizData sd){
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			BizData data = new BizData();
			data.add("ta_flownode", "definitionid", rd.getStringByDI("ta_flownode", "definitionid", 0));
			data.add("ta_flownode", "orgid", sd.getString("orgid"));
			coreDAO.delete(data, conn);
			data.remove("ta_flownode");
			insert(rd, conn);
			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (null != conn) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return 39;
		
	}
	public int selectAllrole(BizData rd,BizData sd){
		
		String sql="select * from DRMROLE where orgid="+sd.getString("orgid");
			
		try {
			coreDAO.executeQuery(sql, "SelectAllrole", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * 排序
	 * @param rd
	 * @param pid
	 */
	private void sortData(BizData rd,String pid, int rows, List<BizData> list, int ii) {
		
		for(int i=0;i<rows;i++) {

			BizData rd2 = new BizData();
			String pidFromData=rd.getString("TA_FLOWNODEs","pid",i);
			if(pidFromData.equals(pid)) {
				
				//相等再取nodeid，并把值存进RD2
				String nodeID=rd.getString("TA_FLOWNODEs","nodeID",i);
				//取出该nodeID所在的行号
				String currentRow = "0";
				String[] rowIDs = rd.getRowIDs("TA_FLOWNODEs");
				for(int j=0;j<rowIDs.length;j++){
					String nodeid = rd.getString("TA_FLOWNODEs", "nodeid", rowIDs[j]);
					if(nodeID.equals(nodeid)){
						currentRow = rowIDs[j];
						break;
					}
				}
				rd2.copyEntityRow(rd, "TA_FLOWNODEs", currentRow);
				list.add(ii, rd2);
				ii++;
				sortData(rd, nodeID, rows, list, ii);
				
			}
		}
	}
	
	public static void main(String[] a){
		
		List<BizData> list = new ArrayList<BizData>();
		FlowNodeMNGBLC f = new FlowNodeMNGBLC();
		BizData rd = new BizData();
		rd.add("TA_FLOWNODEs", "nodeid", 0, "1");
		rd.add("TA_FLOWNODEs", "pid", 0, "-1");
		rd.add("TA_FLOWNODEs", "nodename", 0, "业务员填写报销单");
		rd.add("TA_FLOWNODEs", "nodeid", 1, "4");
		rd.add("TA_FLOWNODEs", "pid", 1, "3");
		rd.add("TA_FLOWNODEs", "nodename", 1, "业务员查看");
		rd.add("TA_FLOWNODEs", "nodeid", 2, "2");
		rd.add("TA_FLOWNODEs", "pid", 2, "1");
		rd.add("TA_FLOWNODEs", "nodename", 2, "地接主管审核");
		rd.add("TA_FLOWNODEs", "nodeid", 3, "3");
		rd.add("TA_FLOWNODEs", "pid", 3, "2");
		rd.add("TA_FLOWNODEs", "nodename", 3, "会计审核");
		int i=0;
		f.sortData(rd, "-1", rd.getTableRowsCount("TA_FLOWNODEs"), list, i);
		String rowsStr[] = rd.getRowIDs("TA_FLOWNODEs");
		
		System.out.println(list);
		
	}
}