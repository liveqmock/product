package com.trmdj.bx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.string.PyUtil;


/**
  * @ClassName: DjShopBxMngBLC
  * @Description: TODO 地接 - 购物报账信息处理类
  * @author KingStong - likai
  * @date 2012-4-13 上午3:55:58
  *
  */
public class DjShopBxMngBLC extends DBBLC {
	public DjShopBxMngBLC(){
		this.entityName = "TA_DJ_BXGW";
	}
	/** 新增购物报账信息
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int insertShop(BizData rd,BizData sd)throws SQLException{
		String[] hotelRow = rd.getRowIDs("TA_DJ_BXGW");//获取记录数
		String groupId = rd.getString("groupId");//获取团号
		String temp = rd.getString("temp");//获取报账状态
		String zDr = sd.getString("userno");//获取指定人
		
		BizData data = new BizData();
		data.add("TA_DJ_BXGW", "TID", groupId);
		data.add("TA_DJ_BXGW", "orgid", sd.getString("orgid"));
			
		BizData bsData = new BizData();
		StringBuffer theNewShop = new StringBuffer().append("[");
		
		for(int i = 0; i < hotelRow.length; i++){
			int hotelId = queryMaxIDByPara("TA_DJ_BXGW", "ID", null);
			int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_BXGW");
			rd.add("TA_DJ_BXGW","ID",String.valueOf(fieldIndex[i]),hotelId);
			rd.add("TA_DJ_BXGW","TID",String.valueOf(fieldIndex[i]),groupId);
			rd.add("TA_DJ_BXGW","JHZT",String.valueOf(fieldIndex[i]),temp);
			rd.add("TA_DJ_BXGW","ZDR",String.valueOf(fieldIndex[i]),zDr);
			rd.add("TA_DJ_BXGW","orgid",String.valueOf(fieldIndex[i]),sd.getString("orgid"));
			
			// 购物点名称
			String gwdMc = rd.getString("TA_DJ_BXGW", "GWDMC",String.valueOf(fieldIndex[i]));
			// 购物点ID
			String gwdID = rd.getString("TA_DJ_BXGW", "GWDID",String.valueOf(fieldIndex[i]));
			// 基础表中没有此购物点信息，添加
			if ("".equals(gwdID)) {
				
				int baseShopID = queryMaxIDByPara("TA_SHOPPOINT", "SHOP_POINT_ID", null);
				bsData.add("TA_SHOPPOINT", "CMPNY_NAME", gwdMc);
				String nameCode = PyUtil.get1stLetterOf4Chars(gwdMc);
				bsData.add("TA_SHOPPOINT", "NAMECODE", nameCode);
				bsData.add("TA_SHOPPOINT", "SHOP_POINT_ID", baseShopID);
				bsData.add("TA_SHOPPOINT", "CHIEF_NAME", rd.getString("TA_DJ_BXGW", "LXR", String.valueOf(fieldIndex[i])));
				bsData.add("TA_SHOPPOINT", "CHIEF_MOBILE", rd.getString("TA_DJ_BXGW", "LXFS", String.valueOf(fieldIndex[i])));
				bsData.add("TA_SHOPPOINT", "orgid", sd.getString("orgid"));
				
				rd.add("TA_DJ_BXGW", "GWDID", String.valueOf(fieldIndex[i]), baseShopID);
				
				//返回该新增的基础信息的数组索引值和当前的主键
				theNewShop.append("{\"indexNm\":"+String.valueOf(fieldIndex[i])+",\"id\":"+baseShopID+"},");
			}
		}
		
		if(theNewShop.lastIndexOf(",") > 0){
			String newShop = theNewShop.substring(0, theNewShop.length()-1);
			newShop = newShop+"]";
			rd.add("newBaseInfo", newShop);
		}
		
		Connection conn=coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			
			//删除购物报账信息
			coreDAO.delete(data, conn);
			data.remove(entityName);
			
			//插入购物报账信息
			coreDAO.insert("TA_DJ_BXGW", rd, conn);
			rd.remove(entityName);
			
			//插入购物点基础信息表
			coreDAO.insert("TA_SHOPPOINT", bsData, conn);
			bsData.remove("TA_SHOPPOINT");
			
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
	
	/**Ajax初始化购物报账页面
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int djAjaxShopInfo(BizData rd, BizData sd) {
		int tRow = 0; //记录行数
		int random = 0;//随机数
		String groupId = rd.getString("groupId");//团号
		String sql = "";//SQL
		
		try {
			sql = "select * from TA_DJ_BXGW where tid='"+groupId+"' and orgid="+sd.getString("orgid");
			coreDAO.executeQuery(sql, "TA_DJ_BXGW", rd);
			tRow = rd.getTableRowsCount("TA_DJ_BXGW");
			if(tRow < 1){//判断是否新增
				sql = "select * from TA_DJ_JHGW  where tid='"+groupId+"' and orgid="+sd.getString("orgid");
				coreDAO.executeQuery(sql, "TA_DJ_BXGW", rd);
			}
			sql="select begin_date,ts from ta_dj_group where id ='"+groupId+"' and orgid="+sd.getString("orgid");
			coreDAO.executeQuery(sql, "TA_DJ_GROUPs", rd);//团信息
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		tRow = rd.getTableRowsCount("TA_DJ_BXGW");//获取行数
		for(int i = 0; i < tRow; i++){
			random = (int) (Math.floor(Math.random() * 123450));
			rd.add("TA_DJ_BXGW","Random", i, random);//装入随即数
		}
		return 999;
	}
	
}
