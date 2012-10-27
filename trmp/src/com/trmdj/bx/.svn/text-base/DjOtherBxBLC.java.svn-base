package com.trmdj.bx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class DjOtherBxBLC extends DBBLC {
	public DjOtherBxBLC(){
		this.entityName="TA_DJ_BXQT";
	}
	/** 新增其他报账信息
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int insertOther(BizData rd,BizData sd)throws SQLException{
		String[] hotelRow = rd.getRowIDs("TA_DJ_BXQT");//获取记录数
		String groupId = rd.getString("groupId");//获取团号
		String temp = rd.getString("temp");//获取报账状态
		String zDr = sd.getString("userno");//获取指定人
		
		BizData data = new BizData();
		data.add("TA_DJ_BXQT", "TID", groupId);
		data.add("TA_DJ_BXQT", "orgid", sd.getString("orgid"));
		
		for(int i = 0; i < hotelRow.length; i++){
			int hotelId = queryMaxIDByPara("TA_DJ_BXQT", "ID", null);
			int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_BXQT");
			rd.add("TA_DJ_BXQT","ID",String.valueOf(fieldIndex[i]),hotelId);
			rd.add("TA_DJ_BXQT","TID",String.valueOf(fieldIndex[i]),groupId);
			rd.add("TA_DJ_BXQT","JHZT",String.valueOf(fieldIndex[i]),temp);
			rd.add("TA_DJ_BXQT","ZDR",String.valueOf(fieldIndex[i]),zDr);
			rd.add("TA_DJ_BXQT","orgid",String.valueOf(fieldIndex[i]),sd.getString("orgid"));
		}
		
		Connection conn=coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			coreDAO.delete(data, conn);//删除历史数据
			data.remove(entityName);
			
			coreDAO.insert("TA_DJ_BXQT", rd, conn);//数据入库
			rd.remove(entityName);
			
			coreDAO.commitTrasct(conn);
		} catch (Exception e) {
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
		return 999;
	}
	
	/**Ajax初始化其他报账页面
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int djAjaxOtherInfo(BizData rd, BizData sd) {
		int tRow = 0; //记录行数
		String groupId = rd.getString("groupId");//团号
		String sql = "";//SQL
		
		try {
			sql = "select * from TA_DJ_BXQT where tid='"+groupId+"' and orgid="+sd.getString("orgid");
			coreDAO.executeQuery(sql, "TA_DJ_BXQT", rd);
			tRow = rd.getTableRowsCount("TA_DJ_BXQT");
			if(tRow < 1){//判断是否新增
				sql = "select * from TA_DJ_JHQT  where tid='"+groupId+"' and orgid="+sd.getString("orgid");
				coreDAO.executeQuery(sql, "TA_DJ_BXQT", rd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
	}
}
