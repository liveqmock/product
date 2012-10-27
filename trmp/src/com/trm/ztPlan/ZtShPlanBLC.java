package com.trm.ztPlan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * @Title: ZtShPlanBLC.java
 * @Package com.trm.ztPlan;
 * @Description: (地接业务审核)
 * @author Kale ym_x@qq.com
 * @date 2011-7-26 下午06:03:43
 * @version V1.0
 */
public class ZtShPlanBLC extends DBBLC {
	public ZtShPlanBLC(){
		this.entityName="TA_DJ_TSPB";
	}
	public int querySPYJInfo(BizData rd,BizData sd) {
		String tid=rd.getStringByDI("TA_ZT_GROUP", "ID", 0);
		String spyj="select t.spyj,u.username from TA_DJ_TSPB t,drmuser u where t.spr=u.userno and t.mklb='1' and t.tid='"+tid+"'";
		try {
			coreDAO.executeQuery(spyj, "SPYJ", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
	}
	/**
	 * 
	 * 查询待审批团列表
	 */
	public int querySpPlanInfo(BizData rd,BizData sd) {
		String groupID = rd.getString("groupID");// 获取查询条件:团号
		String bDate = rd.getString("bDate");// 获取查询条件:开始时间
		String xlmc = rd.getString("xlmc");// 获取查询条件:线路名称
		
		String pageNOStr = rd.getString("pageNO");
		String pageSizeStr = rd.getString("pageSize");
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		String roles = "";
		String roleID = sd.getString("USERROLEST");
		if(!"".equals(roleID)) {
			roleID = roleID.substring(1,roleID.length()-1);
			String[] roleIDs = roleID.split(",");
			for(int i=0;i<roleIDs.length;i++){
				roles += "'"+roleIDs[i].trim()+"',";
			}
			roles = roles.substring(0, roles.length()-1);
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("select f.*,g.xlmc,g.begin_date,g.end_date,n.nodename,f.DEFINITIONID\n")
			.append(" from TA_ZT_group g,ta_flownode n,ta_flow f,TA_FLOWDEFINITION d\n")
			.append("where f.tid=g.id and f.nextnodeid=n.nodeid and f.isgroup='Z'\n")
			.append("and (f.nextroleid in ("+roles+") or f.creater = "+sd.getString("userno")
					+") and d.DEFINITIONID=f.DEFINITIONID and d.DEFINITIONID='ztywsh'");
		if (!"".equals(groupID)) { // 如果团号不为空
			sql.append("and g.id = '" + groupID + "' \n");
		}else if(!"".equals(groupID))
			sql.append("and g.id like '%" + groupID + "%' \n");
		if (!"".equals(bDate)) { // 如果开团时间不为空
			sql.append("and g.BEGIN_DATE=to_date('" + bDate + "','yyyy-mm-dd') \n");
		}
		if (!"".equals(xlmc)) { // 如果线路名称不为空
			sql.append("and g.xlmc like '%" + xlmc + "%'");
		}
		sql.append(" order by g.DTRQ desc");
		
		String tid=rd.getStringByDI("TA_ZT_GROUP", "ID", 0);
		String spyj="select t.spyj,u.username from TA_DJ_TSPB t,drmuser u where t.spr=u.userno and t.mklb='1' and t.tid='"+tid+"'";
		try {
			coreDAO.executeQueryPage(sql.toString(), "rsDSPList", pageNO, pageSize, rd);
			coreDAO.executeQuery(spyj, "SPYJ", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 9;
	}
	
	/**
	 * @Description: 审批提交
	 * 1、查询出当前节点相关信息
	 * 2、结果流程定义，查询出下级节点相关信息
	 * 3、更新流程数据表
	 * 4、添加团审批意见表
	 * @param @param rd
	 * @param @param sd
	 * @return int    返回类型
	 * @throws
	 */
	public int authorizePlan(BizData rd,BizData sd) {
		
		Connection conn=coreDAO.getConnection();
		String groupId=rd.getString("TID");
		String flowID = rd.getString("flowid");
		try{
			coreDAO.beginTrasct(conn);
			BizData data = new BizData();
			//审批之前的下级节点做为审批后的当前节点
			data.add("TA_FLOW", "TID", groupId);
			data.add("TA_FLOW", "DEFINITIONID", flowID);
			data.add("TA_FLOW", "ISGROUP", "Z");
			coreDAO.select(data);
			String thisNodeID = data.getStringByDI("ta_flows", "nextnodeid", 0);
			String thisRoleID = data.getStringByDI("ta_flows", "NEXTROLEID", 0);
			String yjzt = rd.getString("YJZT");//从页面取
			//计算下级节点
			queryNextNodeInfoByGroupID(data,groupId);
			String nextNodeID = data.getStringByDI("rsNextNode", "nextid", 0);
			String nextRoleID = data.getStringByDI("rsNextNode", "nextroleid", 0);
			String state = "activity";
			String isBack = "N";
			//无下级节点，流程将结束
			if(null == nextNodeID || "".equals(nextNodeID)){
				queryCreateRole(rd, groupId);
				state = "end";
				nextRoleID = rd.getStringByDI("rsCreateRole", "roleid", 0);
				nextNodeID = rd.getStringByDI("rsCreateRole", "nodeid", 0);
				//流程结束 团状态 4:审核结束
				data.addAttr("TA_ZT_GROUP", "ID", "0", "oldVAlue", groupId);
				data.add("TA_ZT_GROUP", "STATE", "4");
				coreDAO.update("TA_ZT_GROUP", data, conn);
			}
			//回退。查询该流程的第一个节点参与者，并赋值给流程数据表
			if("N".equals(yjzt)){
				
				queryCreateRole(rd, groupId);
				nextRoleID = rd.getStringByDI("rsCreateRole", "roleid", 0);
				nextNodeID = rd.getStringByDI("rsCreateRole", "nodeid", 0);
				isBack = "Y";
				state = "activity";
			}
			//更新流程数据表
			updateFLowInfo(conn, groupId, thisRoleID, state, thisNodeID, nextRoleID, nextNodeID, isBack);
			//添加审批意见到审批表
			addAuthorizeMsg(rd, conn, thisNodeID);
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
		return 11;
	}
	
	/**
	 * 1、根据流程定义ID查询流程节点信息。包括当前节点及下级节点
	 * 2、维护流程数据表
	 * 3、变更团状态为已实施（3）
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int submitPlan(BizData rd,BizData sd) {
		Connection conn = coreDAO.getConnection();
		//流程定义名字
		String definitionID = rd.getString("definitionid");
		//团号
		String groupID = rd.getString("tid");
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,n2.nodeid cnodeid,n2.nodename cnodename,n2.roleid croleid\n");
		sql.append("from (\n").append("select n.nodeid,n.nodename,n.nodedesc,n.roleid\n")
			.append("from ta_flownode n,ta_flowdefinition f\n")
			.append("where n.definitionid=f.definitionid\n")
			.append("and f.definitionid='"+definitionID+"'\n")
			.append("and n.pid=-1\n")
			.append(") a,ta_flownode n2\n")
			.append("where a.nodeid=n2.pid\n")
			.append("and n2.definitionid ='"+definitionID+"'");
		try {
			coreDAO.executeQuery(sql.toString(), "rsNodes", rd);

			coreDAO.beginTrasct(conn);
			//2，先查询是否已存在
			BizData data = new BizData();
			data.add("TA_FLOW", "TID", groupID);
			data.add("TA_FLOW", "DEFINITIONID",definitionID);
			data.add("TA_FLOW", "ISGROUP", "Z");
			int rows = coreDAO.select(data);
			data.remove("TA_FLOW");
			if(rows == 0)
				add2FlowInfo(conn, data, sd.getString("userno"), rd, definitionID, groupID);
			else
				updateFLowInfo(conn, groupID, rd.getStringByDI("rsNodes", "roleid", 0), 
						"start", rd.getStringByDI("rsNodes", "nodeid", 0), 
						rd.getStringByDI("rsNodes", "croleid", 0), 
						rd.getStringByDI("rsNodes", "cnodeid", 0), "N");
			//变更团状态为已实施(3)
			data.addAttr("TA_ZT_GROUP", "ID", "0", "oldVAlue", groupID);
			data.add("TA_ZT_GROUP", "STATE", "3");
			coreDAO.update("TA_ZT_GROUP", data, conn);
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
		return 3;
	}
	
	/**
	 * 流程引擎调用
	 * 更新流程数据表
	 * @param conn
	 * @param groupID
	 * @param currentRole
	 * @param state
	 * @param currentNode
	 * @param nextRole
	 * @param nextNode
	 * @param isBack
	 * @throws SQLException 
	 */
	private void updateFLowInfo(Connection conn, String groupID, String currentRole, String state,
		String currentNode, String nextRole, String nextNode, String isBack) throws SQLException {
		String LCID = "ztywsh";
		BizData data = new BizData();
		data.addAttr("TA_FLOW", "TID", 0, "oldValue", groupID);
		data.addAttr("TA_FLOW", "DEFINITIONID", 0, "oldValue", LCID);
		data.add("TA_FLOW", "THISROLEID", currentRole);
		data.add("TA_FLOW", "THISNODEID", currentNode);
		data.add("TA_FLOW", "NEXTROLEID", nextRole);
		data.add("TA_FLOW", "NEXTNODEID", nextNode);
		data.add("TA_FLOW", "STATE", state);
		data.add("TA_FLOW", "ISBACK", isBack);
		data.add("TA_FLOW", "ISGROUP", "Z");
		coreDAO.update("TA_FLOW", data, conn);
	}
	
	/**
	 * 添加数据到流程数据表
	 * @param conn
	 * @param data
	 * @param userno
	 * @param rd
	 * @param definitionID
	 * @param groupID
	 * @throws SQLException
	 */
	private void add2FlowInfo(Connection conn, BizData data, String userno, BizData rd,String definitionID, String groupID) throws SQLException{
		
		int flowID = queryMaxIDByPara("TA_FLOW", "FLOWID", null);
		data.add("TA_FLOW", "FLOWID", flowID);
		data.add("TA_FLOW", "DEFINITIONID", definitionID);
		data.add("TA_FLOW", "TID", groupID);
		data.add("TA_FLOW", "CREATER", userno);
		data.add("TA_FLOW", "CREATEDATE", rd.sdfDate.format(new Date()));
		data.add("TA_FLOW", "THISROLEID", rd.getStringByDI("rsNodes", "roleid", 0));
		data.add("TA_FLOW", "THISNODEID", rd.getStringByDI("rsNodes", "nodeid", 0));
		data.add("TA_FLOW", "NEXTROLEID", rd.getStringByDI("rsNodes", "croleid", 0));
		data.add("TA_FLOW", "STATE", "start");
		data.add("TA_FLOW", "NEXTNODEID", rd.getStringByDI("rsNodes", "cnodeid", 0));
		data.add("TA_FLOW", "ISBACK", "N");
		data.add("TA_FLOW", "ISGROUP", "Z");
		coreDAO.insert(data, conn);
		data.remove("TA_FLOW");
	}
	
	/**
	 * 添加到审批意见表
	 * @param rd
	 * @param conn
	 * @throws SQLException 
	 */
	private void addAuthorizeMsg(BizData rd, Connection conn, String nodeID) throws SQLException {
		int id = queryMaxIDByPara("TA_DJ_TSPB", "ID", null);
		rd.add("TA_DJ_TSPB", "id", id);
		rd.add("TA_DJ_TSPB", "NODEID", nodeID);
		rd.add("TA_DJ_TSPB", "yjzt", rd.getString("yjzt"));
		rd.add("TA_DJ_TSPB", "SHRQ", rd.sdfDate.format(new Date()));
		coreDAO.insert("TA_DJ_TSPB", rd, conn);
	}
	
	/**
	 * 根据团ID查询审批后下级节点信息
	 * @param rd
	 * @return
	 * @throws SQLException
	 */
	private void queryNextNodeInfoByGroupID(BizData rd, String groupId) throws SQLException {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,n2.nodeid nextid,n2.nodename nextname,n2.roleid nextroleid\n")
			.append("from (\n")
			.append("select n.nodeid,n.nodename,n.roleid,f.flowid\n")
			.append("from ta_flow f,TA_FLOWNODE n\n")
			.append("where f.nextnodeid=n.nodeid\n")
			.append("and f.tid='"+groupId+"' and f.definitionid='ztywsh' and f.isgroup='Z'\n")
			.append(") a,ta_flownode n2\n")
			.append("where a.nodeid=n2.pid(+)");
		coreDAO.executeQuery(sql.toString(), "rsNextNode", rd);
	}
	
	/**
	 * 根据团号查询流程发起人角色
	 * @param groupID
	 * @throws SQLException 
	 */
	private void queryCreateRole(BizData rd, String groupID) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select n.roleid,n.nodeid\n")
			.append("from ta_flow f, ta_flownode n, ta_flowdefinition ff\n")
			.append("where ff.definitionid=n.definitionid and f.definitionid=ff.definitionid\n")
			.append("and n.pid=-1  and f.definitionid='ztywsh'\n")
			.append("and f.isgroup='Z'")
			.append("and f.tid='"+groupID+"'");
		coreDAO.executeQuery(sql.toString(), "rsCreateRole", rd);
	}
}
