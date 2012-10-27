package com.trmdj.bx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class DjVehBxBLC extends DBBLC {
	public DjVehBxBLC(){
		this.entityName="TA_DJ_BXCL";
	}
	/** 新增餐厅报账信息
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int insertCar(BizData rd,BizData sd)throws SQLException{
		String[] hotelRow = rd.getRowIDs("TA_DJ_BXCL");//获取记录数
		String groupId = rd.getString("groupId");//获取团号
		String temp = rd.getString("temp");//获取报账状态
		String zDr = sd.getString("userno");//获取指定人
		
		BizData data = new BizData();
		data.add("TA_DJ_BXCL", "TID", groupId);
		data.add("TA_DJ_BXCL", "orgid", sd.getString("orgid"));
		

		// 车队基础信息
		BizData bsData = new BizData();
		StringBuffer newVehTeam = new StringBuffer().append("[");
		
		int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_BXCL");
		for(int i = 0; i < hotelRow.length; i++){
			int hotelId = queryMaxIDByPara("TA_DJ_BXCL", "ID", null);
			rd.add("TA_DJ_BXCL","ID",String.valueOf(fieldIndex[i]),hotelId);
			rd.add("TA_DJ_BXCL","TID",String.valueOf(fieldIndex[i]),groupId);
			rd.add("TA_DJ_BXCL","JHZT",String.valueOf(fieldIndex[i]),temp);
			rd.add("TA_DJ_BXCL","ZDR",String.valueOf(fieldIndex[i]),zDr);
			rd.add("TA_DJ_BXCL","orgid",String.valueOf(fieldIndex[i]),sd.getString("orgid"));
			
			// 判断是否为库中已有数据
			if ("".equals(rd.getString("TA_DJ_BXCL", "CD", String.valueOf(fieldIndex[i]))))
			{
				// 新增车队基础信息
				addBaseVehicle(bsData, rd, String.valueOf(fieldIndex[i]), sd .getString("orgid"), newVehTeam);
			}
		}
		
		//新增的酒店信息的ID
		if(newVehTeam.lastIndexOf(",") > 0){
					
			String newVeh = newVehTeam.substring(0, newVehTeam.length()-1);
			newVeh = newVeh+"]";
			rd.add("newBaseInfo", newVeh);
		}
		

		Connection conn=coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			
			//删除车队报账信息
			coreDAO.delete(data, conn);
			data.remove(entityName);
			
			//插入车队报账信息
			coreDAO.insert("TA_DJ_BXCL", rd, conn);
			rd.remove(entityName);
			
			//插入车队基础信息
			coreDAO.insert("ta_car_team", bsData, conn);
			bsData.remove("ta_car_team");
			
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
	
	/**Ajax初始化餐厅报账页面
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int djAjaxCarInfo(BizData rd, BizData sd) {
		int tRow = 0; //记录行数
		int random = 0;//随机数
		String groupId = rd.getString("groupId");//团号
		String sql = "";//SQL
		
		try {
			sql = "select * from TA_DJ_BXCL where tid='"+groupId+"' and orgid="+sd.getString("orgid");
			coreDAO.executeQuery(sql, "TA_DJ_BXCL", rd);
			tRow = rd.getTableRowsCount("TA_DJ_BXCL");
			if(tRow < 1){//判断是否新增
				sql = "select * from TA_DJ_JHCL  where tid='"+groupId+"' and orgid="+sd.getString("orgid");
				coreDAO.executeQuery(sql, "TA_DJ_BXCL", rd);
			}
			sql="select begin_date,ts from ta_dj_group where id ='"+groupId+"' and orgid="+sd.getString("orgid");
			coreDAO.executeQuery(sql, "TA_DJ_GROUPs", rd);//团信息
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		tRow = rd.getTableRowsCount("TA_DJ_BXCL");//获取行数
		for(int i = 0; i < tRow; i++){
			random = (int) (Math.floor(Math.random() * 123450));
			rd.add("TA_DJ_BXCL","Random", i, random);//装入随即数
		}
		return 999;
	}
	
	/**
	 * 新增车队基础信息
	 * 
	 * @param bsData
	 * @param rd
	 * @param valueOf
	 * @param string
	 */
	private void addBaseVehicle(BizData bsData, BizData rd, String index,
            String orgid, StringBuffer newVehTeam)
    {
		// 统计对象中票务基础数据数目
		int rows = bsData.getTableRowsCount("ta_car_team");
		
		// 取最大值
		int maxId = queryMaxIDByPara("ta_car_team", "TEAM_ID", null);
		
		// 添加车队ID，车队ID取最大值
		bsData.add("ta_car_team", "TEAM_ID", rows, maxId);
		
		// 添加车队名称
		bsData.add("ta_car_team", "CMPNY_NAME", rows, rd.getString("TA_DJ_BXCL", "CDMC", index));
		
		// 添加车队联系人
		bsData.add("ta_car_team", "CHIEF_NAME", rows, rd.getString("TA_DJ_BXCL", "LXR", index));
		
		// 添加车队联系人电话
		bsData.add("ta_car_team", "CHIEF_MOBILE", rows, rd.getString("TA_DJ_BXCL", "LXRDH", index));
		
		// 添加机构ID
		bsData.add("ta_car_team", "orgid", rows, orgid);
		
		// 计划中添加车队ID
		rd.add("TA_DJ_BXCL", "CD", index, maxId);
		newVehTeam.append("{\"indexNm\":"+index+",\"id\":"+maxId+"},");
    }
	
	/**
	 * 
	 * @Title: initBXPage
	 * @Description: (初始化车辆报销信息)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int initBXPage(BizData rd,BizData sd) {
		String groupId=rd.getStringByDI("TA_DJ_JHCL", "TID", 0);
		String flag=rd.getString("flag");
		try {
			if("init".equals(flag)){
				String bxVehInit="select j.tid,j.sf,j.city,j.cd,j.cx,j.cp,j.sjxm,j.sjdh,j.KSRQ,j.JSRQ, \n" +
						"j.jg,j.qdje,j.xfje,j.jhzt,j.zdr,j.jthbcc,j.sthbcc,j.jtdd,j.stdd,j.sjxm,j.sjdh from ta_dj_jhcl j where j.tid='"+groupId+"'";
				
				String bxzjxxbinit="select a.qdclzj qdzj,a.xfclzj xfzj,a.clzj zj from ta_tdjdxxzjb a where a.tid='"+groupId+"'";
				
				coreDAO.executeQuery(bxVehInit, "bxVehList", rd);
				coreDAO.executeQuery(bxzjxxbinit, "bxclJDXXZJB", rd);
			}
			
			if("edit".equals(flag)||"view".equals(flag)){
				String bxVehEdit="select j.tid,j.sf,j.city,j.cd,j.cx,j.cp,j.ksrq,j.jsrq, \n" +
					"j.jg,j.qdje,j.xfje,j.bxzt,j.bxr,j.jthbcc,j.sthbcc,j.jtdd,j.stdd,j.sjxm,j.sjdh,j.bz from ta_dj_bxcl j where j.tid='"+groupId+"'";
				
				String bxzjxxbEdit="select a.bxclqd qdzj,a.bxclxf xfzj,a.clhj zj from ta_tdbxzjxxb a where a.tid='"+groupId+"'";
				
				coreDAO.executeQuery(bxVehEdit, "bxVehList", rd);
				coreDAO.executeQuery(bxzjxxbEdit, "bxclJDXXZJB", rd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 92;
	}
	
	/**
	 * 
	 * @Title: djSaveVehPlan
	 * @Description: (编辑车辆报销)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int editVehBx(BizData rd, BizData sd) {
		
		Connection conn = coreDAO.getConnection();
		String[] rowIDs = rd.getRowIDs("TA_DJ_BXCL");
		try {
			
			coreDAO.beginTrasct(conn);
			//删除团队原来的车调计划
			String groupID = rd.getStringByDI("TA_DJ_BXCL", "TID", 0);
			BizData data = new BizData();
			data.add("TA_DJ_BXCL", "TID", groupID);
			coreDAO.delete(data, conn);
			data.remove("TA_DJ_BXCL");
			//添加新的车调计划
			for(int i=0;i<rowIDs.length;i++) {
				int id = queryMaxIDByPara("TA_DJ_BXCL", "ID", null);
				rd.add("TA_DJ_BXCL", "id", rowIDs[i], id);
			}
			coreDAO.insert("TA_DJ_BXCL", rd, conn);
			rd.remove("TA_DJ_BXCL");
			//更新团表中的费用总计
			updateTDBXZJXXB(rd, conn, groupID, data);
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
		return 9;
	}

	/**更新团表中的费用总计
	 * @param rd
	 * @param conn
	 * @param groupID
	 * @param data
	 * @throws SQLException
	 */
	public void updateTDBXZJXXB(BizData rd, Connection conn, String groupID,
			BizData data) throws SQLException {
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
