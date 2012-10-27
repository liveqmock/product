package com.trm.ztPlan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**  
 * <b>项目名：</b>皇家国际资源管理系统<br/>  
 * <b>包名：</b>com.trmdj.businessPlan.plan<br/>  
 * <b>文件名：</b>addPlusPlanBLC.java<br/>  
 * <b>版本信息：1.0</b><br/>  
 * <b>日期：</b>2011-7-19-上午03:21:25<br/>  
 * <b>Copyright (c)</b> 2011金索通软件科技有限公司-版权所有<br/>  
 *   
 */
/**  
 *   
 * <b>类名称：</b>addPlusPlanBLC<br/>  
 * <b>类描述：</b><br/>  
 * <b>创建人：</b>Kale<br/>  
 * <b>修改人：</b>Kale<br/>  
 * <b>修改时间：</b>2011-7-19 上午03:21:25<br/>  
 * <b>修改备注：</b><br/>  
 * @version 1.0.0<br/>  
 *   
 */

public class ZtPlusPlanBLC extends DBBLC {
	
	public ZtPlusPlanBLC(){
		this.entityName="TA_ZT_JHJIAD";
	}
	
	public int queryJDByTID(BizData rd, BizData sd){
		rd.add("flag", rd.getString("flag"));
		rd.add("tid", rd.getStringByDI("TA_ZT_JHJIAD", "TID", 0));
		try {
			query(rd, sd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 92;
	}
	/**  
	 * insertPlusPanInfo(插入景点计划和景点信息)<br/>  
	 * @param rd
	 * @param sd   
	 * @throws SQLException 
	 * @return_type (void)
	 * @exception   
	 * @since  1.0.0  
	*/
	public int insertPlusPanInfo(BizData rd,BizData sd) {
		Connection conn = coreDAO.getConnection();
		String groupID=rd.getStringByDI("TA_ZT_JHJIAD","TID",0);//获取团号
		rd.add("groupId",groupID);
		try {
			coreDAO.beginTrasct(conn);
			//根据团号删除加点计划
			BizData data = new BizData();
			data.add("TA_ZT_JHJIAD", "TID", groupID);
			coreDAO.delete(data, conn);
			data.remove("TA_ZT_JHJIAD");
			data.add("TA_ZT_JHJIADJD", "TID", groupID);
			coreDAO.delete(data, conn);
			data.remove("TA_ZT_JHJIADJD");
			
			addPlusPlanInfo(groupID,rd,conn);//插入景点信息
			
			BizData dtt = new BizData();
			dtt.addAttr("TA_ZT_GROUP", "ID", 0, "oldValue", groupID);
			dtt.add("TA_ZT_GROUP", "STATE", "2");//修改团状态为 2  实施中
			coreDAO.update("TA_ZT_GROUP", dtt, conn);
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
		return 999;
	}
	/**  
	 * addPlusPlanInfo(插入景点计划)<br/>  
	 * @param rd
	 * @param sd
	 * @return   
	 * @return_type (int)
	 * @exception   
	 * @since  1.0.0  
	*/
	private void addPlusPlanInfo(String groupID, BizData rd, Connection conn) throws SQLException {

		String[] plusRows = rd.getRowIDs("TA_ZT_JHJIAD");

		for (int i = 0; i < plusRows.length; i++) {
				int plusID = queryMaxIDByPara("TA_ZT_JHJIAD", "ID", null);
				String csId = rd.getString("TA_ZT_JHJIAD", "CS", plusRows[i]);//取城市ID，将城市ID加到对应的景点表名后面，方便取数据
				rd.add("TA_ZT_JHJIAD", "ID", plusRows[i], plusID);
				addPlusScr(plusID, rd, conn, csId, groupID);//插入景点信息
		}
		coreDAO.insert("TA_ZT_JHJIAD", rd, conn);
	}
	/**  
	 * addPlusScr(插入景点信息)<br/>  
	 * @param plusID
	 * @param rd
	 * @param conn
	 * @return
	 * @throws SQLException   
	 * @return_type (int)
	 * @exception   
	 * @since  1.0.0  
	*/
	private void addPlusScr(int plusID,BizData rd, Connection conn, String cityID,String groupId) throws SQLException {
		
		String[] plusScrRows=rd.getRowIDs("TA_ZT_JHJIADJD"+cityID);
		for(int i = 0; i < plusScrRows.length; i++){
			String isCk = rd.getString("TA_ZT_JHJIADJD"+cityID,"ISCHECK",plusScrRows[i]);
			if(!"".equals(isCk)){
				int plusScrID=queryMaxIDByPara("TA_ZT_JHJIADJD", "ID", null);
				String jdId = rd.getString("TA_ZT_JHJIADJD"+cityID,"JDID",plusScrRows[i]);
				String jdMc = rd.getString("TA_ZT_JHJIADJD"+cityID,"JDMC",plusScrRows[i]);
				rd.add("TA_ZT_JHJIADJD", "ID", plusScrRows[i], plusScrID);
				rd.add("TA_ZT_JHJIADJD", "JHID", plusScrRows[i], plusID);
				rd.add("TA_ZT_JHJIADJD", "TID", plusScrRows[i], groupId);
				rd.add("TA_ZT_JHJIADJD", "JDID", plusScrRows[i], jdId);
				rd.add("TA_ZT_JHJIADJD", "JDMC", plusScrRows[i], jdMc);
				rd.add("TA_ZT_JHJIADJD", "ISCHECK", plusScrRows[i], isCk);
			}
		}
		coreDAO.insert("TA_ZT_JHJIADJD", rd, conn);
	}
	
	/**
	 * 返回结果以判断计调是否已实施，并返回实际实施的景点
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int query4Init(BizData rd, BizData sd){
		
		String isCheck = rd.getString("isCheck");
		try {
			query(rd, sd);
			StringBuffer sqlBuff = new StringBuffer();
			sqlBuff.append("select a.id,a.tid,a.sf,a.cs,a.jhzt,a.zdr,b.id pointID,b.jdid,b.jdmc,b.ischeck\n");
			sqlBuff.append("from TA_ZT_jhjiad a,TA_ZT_jhjiadjd b\n");
			sqlBuff.append("where a.id=b.jhid\n");
			if("Y".equals(isCheck))
				sqlBuff.append("and b.ischeck='Y'\n");
			sqlBuff.append("and a.tid='");
			sqlBuff.append(rd.getStringByDI("TA_ZT_JHJIAD", "TID", 0));
			sqlBuff.append("'");
			coreDAO.executeQuery(sqlBuff.toString(), "rsScenerys", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 8;
	}
	
}
