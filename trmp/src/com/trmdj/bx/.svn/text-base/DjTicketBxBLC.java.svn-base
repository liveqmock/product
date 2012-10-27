package com.trmdj.bx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * @Title: DjTicketBxBLC.java
 * @Package com.trmdj.bx
 * @Description: (票务报销)
 * @author Kale ym_x@qq.com
 * @date 2011-8-10 上午09:58:04
 * @version V1.0
 */
public class DjTicketBxBLC extends DBBLC {
	public DjTicketBxBLC(){
		this.entityName="TA_DJ_BXPW";
	}
	/** 新增票务报账信息
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int insertTicket(BizData rd,BizData sd)throws SQLException{
		String[] hotelRow = rd.getRowIDs("TA_DJ_BXPW");//获取记录数
		String groupId = rd.getString("groupId");//获取团号
		String temp = rd.getString("temp");//获取报账状态
		String zDr = sd.getString("userno");//获取指定人
		
		BizData data = new BizData();
		data.add("TA_DJ_BXPW", "TID", groupId);
		data.add("TA_DJ_BXPW", "orgid", sd.getString("orgid"));
		
		// 票务代购点基础数据
		BizData bsData = new BizData();
		StringBuffer theNewTicket = new StringBuffer().append("[");
		
		int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_BXPW");
		for(int i = 0; i < hotelRow.length; i++){
			int hotelId = queryMaxIDByPara("TA_DJ_BXPW", "ID", null);
			rd.add("TA_DJ_BXPW","ID",String.valueOf(fieldIndex[i]),hotelId);
			rd.add("TA_DJ_BXPW","TID",String.valueOf(fieldIndex[i]),groupId);
			rd.add("TA_DJ_BXPW","JHZT",String.valueOf(fieldIndex[i]),temp);
			rd.add("TA_DJ_BXPW","ZDR",String.valueOf(fieldIndex[i]),zDr);
			rd.add("TA_DJ_BXPW","orgid",String.valueOf(fieldIndex[i]),sd.getString("orgid"));
			
			// 判断是否为库中已有数据
			if ("".equals(rd.getString("TA_DJ_BXPW", "DGD", String.valueOf(fieldIndex[i]))))
			{
				// 新增餐厅基础信息
				addBaseTicket(bsData, rd, String.valueOf(fieldIndex[i]), sd .getString("orgid"), theNewTicket);
			}
		}
		
		//新增的酒店信息的ID
		if(theNewTicket.lastIndexOf(",") > 0){
			String newTicket = theNewTicket.substring(0, theNewTicket.length()-1);
			newTicket = newTicket+"]";
			rd.add("newBaseInfo", newTicket);
		}
		
		Connection conn=coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			
			// 删除票务报账信息
			coreDAO.delete(data, conn);
			data.remove(entityName);
			
			//插入票务报账信息
			coreDAO.insert("TA_DJ_BXPW", rd, conn);
			rd.remove(entityName);
			
			// 插入票务基础信息
			coreDAO.insert("ta_ticket", bsData, conn);
			bsData.remove("ta_ticket");
			
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
	
	/**Ajax初始化票务报账页面
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int djAjaxTicketInfo(BizData rd, BizData sd) {
		int tRow = 0; //记录行数
		int random = 0;//随机数
		String groupId = rd.getString("groupId");//团号
		String sql = "";//SQL
		
		try {
			sql = "select * from TA_DJ_BXPW where tid='"+groupId+"' and orgid="+sd.getString("orgid");
			coreDAO.executeQuery(sql, "TA_DJ_BXPW", rd);
			tRow = rd.getTableRowsCount("TA_DJ_BXPW");
			if(tRow < 1){//判断是否新增
				sql = "select * from TA_DJ_JHPW  where tid='"+groupId+"' and orgid="+sd.getString("orgid");
				coreDAO.executeQuery(sql, "TA_DJ_BXPW", rd);
			}
			sql="select begin_date,ts from ta_dj_group where id ='"+groupId+"' and orgid="+sd.getString("orgid");
			coreDAO.executeQuery(sql, "TA_DJ_GROUPs", rd);//团信息
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		tRow = rd.getTableRowsCount("TA_DJ_BXPW");//获取行数
		for(int i = 0; i < tRow; i++){
			random = (int) (Math.floor(Math.random() * 123450));
			rd.add("TA_DJ_BXPW","Random", i, random);//装入随即数
		}
		return 999;
	}
	
	/**
	 * 添加票务点基础信息
	 * 
	 * @param bsData
	 * @param rd
	 * @param valueOf
	 * @param string
	 */
	private void addBaseTicket(BizData bsData, BizData rd, String index,
	        String orgid, StringBuffer theNewTicket)
	{
		// 统计对象中票务基础数据数目
		int rows = bsData.getTableRowsCount("ta_ticket");
		
		// 取最大值
		int maxId = queryMaxIDByPara("ta_ticket", "TICKET_ID", null);
		
		// 添加票务ID，票务ID取最大值
		bsData.add("ta_ticket", "TICKET_ID", rows, maxId);
		
		// 添加票务点名称
		bsData.add("ta_ticket", "CMPNY_NAME", rows, rd.getString("TA_DJ_BXPW", "DGDMC", index));
		
		// 添加餐厅联系人
		bsData.add("ta_ticket", "CHIEF_NAME", rows, rd.getString("TA_DJ_BXPW", "LXR", index));
		
		// 添加餐厅联系人电话
		bsData.add("ta_ticket", "CHIEF_MOBILE", rows, rd.getString("TA_DJ_BXPW", "LXRDH", index));
		
		// 添加机构ID
		bsData.add("ta_ticket", "orgid", rows, orgid);
		
		// 计划中添加票务点ID
		rd.add("TA_DJ_BXPW", "DGD", index, maxId);
		//返回该新增的基础信息的数组索引值和当前的主键
		theNewTicket.append("{\"indexNm\":"+index+",\"id\":"+maxId+"},");
	}
	
	/**
	 * 
	 * @Title: initTicketBxList
	 * @Description: (初始化票务信息)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int initTicketBxList(BizData rd,BizData sd){
		String groupId=rd.getStringByDI("TA_DJ_BXPW", "TID", 0);
		String flag=rd.getString("flag");
		try {
			if("init".equals(flag)){
				//查询报销信息
				String bxPwInit="select a.id,a.tid,a.sf,a.city,a.dgd,a.lxr,a.lxrdh,a.qdxj,a.xfxj,a.sxfhj,a.hj, \n" +
						"a.jhzt,a.zdr from ta_dj_jhpw a where a.tid='"+groupId+"'";
				//查询报销明细
				String bxPwmxInit="select a.id,b.jhid,b.cplx,b.cfz,b.mdz,b.begintime,b.zs,b.dj,b.sxf,b.cc, \n" +
						"b.qdje,b.xfje,b.tid from ta_dj_jhpw a,ta_dj_jhpwmx b where b.jhid=a.id and b.tid='"+groupId+"'";
				
				String bxzjxxbinit="select a.qdpwzj qdzj,a.xfpwzj xfzj,a.pwzj zj,a.sxfzj sxfzj from ta_tdjdxxzjb a where a.tid='"+groupId+"'";
				
				coreDAO.executeQuery(bxPwInit, "bxPwList", rd);
				coreDAO.executeQuery(bxPwmxInit, "bxPwmxList", rd);
				coreDAO.executeQuery(bxzjxxbinit, "bxpwJDXXZJB", rd);
			}
			
			if("edit".equals(flag)||"view".equals(flag)){
				//查询报销信息
				String bxPwEdit="select a.id,a.tid,a.sf,a.city,a.dgd,a.lxr,a.lxrdh,a.qdxj,a.xfxj,a.sxfhj,a.hj, \n" +
						"a.bxzt,a.bxr,a.bz from ta_dj_bxpw a where a.tid='"+groupId+"'";
				//查询报销明细
				String bxPwmxEdit="select a.id,b.jhid,b.cplx,b.cfz,b.mdz,b.begintime,b.zs,b.dj,b.sxf,b.ct cc, \n" +
						"b.qdje,b.xfje,b.tid from ta_dj_bxpw a,ta_dj_bxpwmx b where b.jhid=a.id and b.tid='"+groupId+"'";
				
				String bxzjxxbEdit="select a.bxpwqd qdzj,a.bxpwxf xfzj,a.pwhj zj,a.sxfzj sxfzj from ta_tdbxzjxxb a where a.tid='"+groupId+"'";
				
				coreDAO.executeQuery(bxPwEdit, "bxPwList", rd);
				coreDAO.executeQuery(bxPwmxEdit, "bxPwmxList", rd);
				coreDAO.executeQuery(bxzjxxbEdit, "bxpwJDXXZJB", rd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * 
	 * @Title: editTicketBx
	 * @Description: (编辑票务报销)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int editTicketBx(BizData rd, BizData sd) {

		BizData data = new BizData();
		Connection conn = coreDAO.getConnection();
		String[] ticketRows = rd.getRowIDs("TA_DJ_BXPW");
		String groupID = rd.getStringByDI("TA_DJ_BXPW", "tid", 0);
		try{
			coreDAO.beginTrasct(conn);
			//根据团ID查询对应的票务实施数据
			data.add("TA_DJ_BXPW", "tid", groupID);
			coreDAO.select(data);
			//根据团ID删除票务计划
			coreDAO.deleteEntity("TA_DJ_BXPW", data, conn);
			data.remove("TA_DJ_BXPW");
			//根据票务计划ID删除票务实施明细表
			for(int i=0;i<data.getTableRowsCount("TA_DJ_BXPWs");i++) {
				String planID = data.getStringByDI("TA_DJ_BXPWs", "id", i);
				data.add("TA_DJ_BXPWMX", "JHID", i, planID);
			}
			data.remove("TA_DJ_BXPWs");
			coreDAO.delete(data, conn);
			data.remove("TA_DJ_BXPWMX");
			//票务计划表主键
			for(int i=0;i<ticketRows.length;i++) {
				int id = queryMaxIDByPara("TA_DJ_BXPW", "ID", null);
				rd.add("TA_DJ_BXPW", "id", ticketRows[i], id);
				//插入票务明细表
				String[] trItemRows = rd.getRowIDs("TA_DJ_BXPWMX"+i);
				for(int j=0;j<trItemRows.length;j++) {
					int mxID = queryMaxIDByPara("TA_DJ_BXPWMX", "ID", null);
					rd.add("TA_DJ_BXPWMX"+i, "id", trItemRows[j], mxID);
					rd.add("TA_DJ_BXPWMX"+i, "JHID", trItemRows[j], id);
					rd.add("TA_DJ_BXPWMX"+i, "TID", trItemRows[j], groupID);
				}
				rd.renameEntity("TA_DJ_BXPWMX"+i, "TA_DJ_BXPWMX");
				coreDAO.insert("TA_DJ_BXPWMX", rd, conn);
				rd.remove("TA_DJ_BXPWMX");
			}
			coreDAO.insert("TA_DJ_BXPW", rd, conn);
			rd.remove("TA_DJ_BXPW");
			//更新团表中的费用总计
			updateTDBXZJXXB(rd, data, conn, groupID);
			BizData dtt = new BizData();
			dtt.addAttr("TA_DJ_GROUP", "ID", 0, "oldValue", groupID);
			dtt.add("TA_DJ_GROUP", "STATE", "5");//修改团状态为 5  实施中
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
		return 38;
	}

	/**更新团表中的费用总计
	 * @param rd
	 * @param data
	 * @param conn
	 * @param groupID
	 * @throws SQLException
	 */
	public void updateTDBXZJXXB(BizData rd, BizData data, Connection conn,
			String groupID) throws SQLException {
		data.add("TA_TDBXZJXXB", "TID", groupID);
		coreDAO.select(data);
		int rows = data.getTableRowsCount("TA_TDBXZJXXBs");
		data.remove("TA_TDBXZJXXBs");
		if(rows > 0){
			rd.addAttr("TA_TDBXZJXXB", "TID", 0, "oldValue", groupID);
			coreDAO.update("TA_TDBXZJXXB", rd, conn);
		}else{
			rd.add("TA_TDBXZJXXB", "TID", groupID);
			coreDAO.insert("TA_TDBXZJXXB", rd, conn);
		}
	}
}
