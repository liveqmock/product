package com.trmdj.businessPlan.plan;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.string.PyUtil;

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

public class DJPlusPlanBLC extends DBBLC {
	
	public DJPlusPlanBLC(){
		this.entityName="TA_DJ_JHJIAD";
	}
	/** 新增加点信息
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int insertPlus(BizData rd, BizData sd) throws SQLException{
		
		String[] plusRow = rd.getRowIDs("TA_DJ_JHJIAD");//获取记录数
		String groupId = rd.getString("groupId");//获取团号
		String temp = rd.getString("temp");//获取计划状态
		String zDr = sd.getString("userno");//获取指定人
		
		//根据团号删除加点计划
		BizData data = new BizData();
		
		Connection conn=coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			data.add("TA_DJ_JHJIAD", "TID", groupId);
			data.add("TA_DJ_JHJIAD", "orgid", sd.getString("orgid"));
			coreDAO.delete(data, conn);// 删除历史数据
			data.remove(entityName);

			StringBuffer theNewPlus = new StringBuffer().append("[");
			int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_JHJIAD");
			
			for (int i = 0; i < plusRow.length; i++) {

				int plusId = queryMaxIDByPara("TA_DJ_JHJIAD", "ID", null);
				// 景点名称
				String sceneryName = rd.getString("TA_DJ_JHJIAD", "JDMC", String.valueOf(fieldIndex[i]));
				// 景点ID（基础表）
				String sceneryId = rd.getString("TA_DJ_JHJIAD", "JDID", String.valueOf(fieldIndex[i]));
				// 景点在基础表中不存在，添加
				if (sceneryId.equals("")) {
					int sId = queryMaxIDByPara("TA_SCENERY_POINT","SCENERY_ID", null);
					data.add("TA_SCENERY_POINT", "CMPNY_NAME", sceneryName);
					String nameCode = PyUtil.get1stLetterOf4Chars(sceneryName);
					data.add("TA_SCENERY_POINT", "nameCode", nameCode);
					data.add("TA_SCENERY_POINT", "SCENERY_ID", sId);
					data.add("TA_SCENERY_POINT", "orgid", sd.getString("orgid"));
					data.add("TA_SCENERY_POINT", "CHIEF_NAME", rd.getString("TA_DJ_JHJIAD", "LXR", String.valueOf(fieldIndex[i])));
					data.add("TA_SCENERY_POINT", "CHIEF_MOBILE", rd.getString("TA_DJ_JHJIAD", "LXFS", String.valueOf(fieldIndex[i])));
					coreDAO.insert(data, conn);
					data.remove("TA_SCENERY_POINT");
					rd.add("TA_DJ_JHJIAD", "jdid", String.valueOf(fieldIndex[i]), sId);
					theNewPlus.append("{\"indexNm\":"+String.valueOf(fieldIndex[i])+",\"id\":"+sId+"},");
				}
				rd.add("TA_DJ_JHJIAD", "ID", String.valueOf(fieldIndex[i]), plusId);
				rd.add("TA_DJ_JHJIAD", "TID", String.valueOf(fieldIndex[i]), groupId);
				rd.add("TA_DJ_JHJIAD", "JHZT", String.valueOf(fieldIndex[i]), temp);
				rd.add("TA_DJ_JHJIAD", "ZDR", String.valueOf(fieldIndex[i]), zDr);
				rd.add("TA_DJ_JHJIAD", "orgid", String.valueOf(fieldIndex[i]), sd.getString("orgid"));
				
			}
			
			//新增的酒店信息的ID
			if(theNewPlus.lastIndexOf(",") > 0){
				
				String newPlus = theNewPlus.substring(0, theNewPlus.length()-1);
				newPlus = newPlus+"]";
				rd.add("newBaseInfo", newPlus);
			}

			coreDAO.insert("TA_DJ_JHJIAD", rd, conn);// 数据入库
			coreDAO.commitTrasct(conn);
		} catch (Exception e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return -100;
		}finally{
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 999;
	}
	/**Ajax初始化加点页面
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int djAjaxPlusInfo(BizData rd, BizData sd) throws SQLException{
		String groupId = rd.getString("groupId");
		String sql = "select * from TA_DJ_JHJIAD where tid='"+groupId+"' and orgid="+sd.getString("orgid");
		coreDAO.executeQuery(sql, "TA_DJ_JHJIAD", rd);
		
		int tRow = rd.getTableRowsCount("TA_DJ_JHJIAD");//获取查询记录行数
		
		int random = 0;//装随机数
		for(int i = 0; i < tRow; i++){
			random = (int) (Math.floor(Math.random() * 123450));
			rd.add("TA_DJ_JHJIAD","Random", i, random);//装入随即数
		}
		return 999;
	}
	
	public int queryJDByTID(BizData rd, BizData sd){
		rd.add("flag", rd.getString("flag"));
		rd.add("tid", rd.getStringByDI("TA_DJ_JHJIAD", "TID", 0));
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
		String groupID=rd.getStringByDI("TA_DJ_JHJIAD","TID",0);//获取团号
		try {
			coreDAO.beginTrasct(conn);
			//根据团号删除加点计划
			BizData data = new BizData();
			data.add("TA_DJ_JHJIAD", "TID", groupID);
			coreDAO.delete(data, conn);
			data.remove("TA_DJ_JHJIAD");
			data.add("TA_DJ_JHJIADJD", "TID", groupID);
			coreDAO.delete(data, conn);
			data.remove("TA_DJ_JHJIADJD");
			
			addPlusPlanInfo(groupID,rd,conn);//插入景点信息
			
			BizData dtt = new BizData();
			dtt.addAttr("TA_DJ_GROUP", "ID", 0, "oldValue", groupID);
			dtt.add("TA_DJ_GROUP", "STATE", "2");//修改团状态为 2  实施中
			coreDAO.update("TA_DJ_GROUP", dtt, conn);
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

		String[] plusRows = rd.getRowIDs("TA_DJ_JHJIAD");

		for (int i = 0; i < plusRows.length; i++) {
				int plusID = queryMaxIDByPara("TA_DJ_JHJIAD", "ID", null);
				String csId = rd.getString("TA_DJ_JHJIAD", "CS", plusRows[i]);//取城市ID，将城市ID加到对应的景点表名后面，方便取数据
				rd.add("TA_DJ_JHJIAD", "ID", plusRows[i], plusID);
				addPlusScr(plusID, rd, conn, csId, groupID);//插入景点信息
		}
		coreDAO.insert("TA_DJ_JHJIAD", rd, conn);
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
	private void addPlusScr(int plusID,BizData rd, Connection conn, String cityID, String groupId) throws SQLException {
		
		String[] plusScrRows=rd.getRowIDs("TA_DJ_JHJIADJD"+cityID);
		for(int i = 0; i < plusScrRows.length; i++){
			String isCk = rd.getString("TA_DJ_JHJIADJD"+cityID,"ISCHECK",plusScrRows[i]);
			if(!"".equals(isCk)){
				int plusScrID=queryMaxIDByPara("TA_DJ_JHJIADJD", "ID", null);
				String jdId = rd.getString("TA_DJ_JHJIADJD"+cityID,"JDID",plusScrRows[i]);
				String jdMc = rd.getString("TA_DJ_JHJIADJD"+cityID,"JDMC",plusScrRows[i]);
				rd.add("TA_DJ_JHJIADJD", "ID", plusScrRows[i], plusScrID);
				rd.add("TA_DJ_JHJIADJD", "JHID", plusScrRows[i], plusID);
				rd.add("TA_DJ_JHJIADJD", "TID", plusScrRows[i], groupId);
				rd.add("TA_DJ_JHJIADJD", "JDID", plusScrRows[i], jdId);
				rd.add("TA_DJ_JHJIADJD", "JDMC", plusScrRows[i], jdMc);
				rd.add("TA_DJ_JHJIADJD", "ISCHECK", plusScrRows[i], isCk);
			}
		}
		coreDAO.insert("TA_DJ_JHJIADJD", rd, conn);
	}
	
	/**
	 * 返回结果以判断计调是否已实施，并返回实际实施的景点
	 * @param rd
	 * @param sd
	 * @return
	 */
	
	
}
