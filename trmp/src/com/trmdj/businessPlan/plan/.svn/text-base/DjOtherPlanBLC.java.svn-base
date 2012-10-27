package com.trmdj.businessPlan.plan;

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

public class DjOtherPlanBLC extends DBBLC {
	
	public DjOtherPlanBLC(){
		this.entityName="TA_DJ_JHQT";
	}
	/** 新增加点信息
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int insert(BizData rd, BizData sd) {
		String[] otherRow = rd.getRowIDs("TA_DJ_JHQT");//获取记录数
		String groupId = rd.getString("groupId");//获取团号
		String temp = rd.getString("temp");//获取计划状态
		String zDr = sd.getString("userno");//获取指定人
		
		//根据团号删除加点计划
		BizData data = new BizData();
		data.add("TA_DJ_JHQT", "TID", groupId);
		data.add("TA_DJ_JHQT", "orgid", sd.getString("orgid"));
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			coreDAO.delete(data, conn);
			data.remove("TA_DJ_JHQT");

			for(int i = 0; i < otherRow.length; i++){
				int plusId = queryMaxIDByPara("TA_DJ_JHQT", "ID", null);
				int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_JHQT");
				rd.add("TA_DJ_JHQT", "orgid",fieldIndex[i],sd.getString("orgid"));
				rd.add("TA_DJ_JHQT","ID",fieldIndex[i],plusId);
				rd.add("TA_DJ_JHQT","TID",fieldIndex[i],groupId);
				rd.add("TA_DJ_JHQT","JHZT",fieldIndex[i],temp);
				rd.add("TA_DJ_JHQT","ZDR",fieldIndex[i],zDr);
			}
			coreDAO.insert("TA_DJ_JHQT", rd, conn);//数据入库
			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally{
			try{
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
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
	public int djAjaxOtherInfo(BizData rd, BizData sd) throws SQLException{
		String groupId = rd.getString("groupId");
		String sql = "select * from TA_DJ_JHQT where tid='"+groupId+"' and orgid="+sd.getString("orgid");
		coreDAO.executeQuery(sql, "TA_DJ_JHQT", rd);
		
		return 999;
	}
	
}
