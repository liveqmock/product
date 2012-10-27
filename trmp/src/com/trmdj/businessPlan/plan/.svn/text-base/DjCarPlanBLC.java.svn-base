package com.trmdj.businessPlan.plan;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * 处理地接车辆安排相关请求
 * 
 * @author [hl.Wei]
 * @date [Feb 20, 2012 11:28:59 AM]
 */
public class DjCarPlanBLC extends DBBLC
{
	/**
	 * 打印日志
	 */
	private Logger log = null;

	/**
	 * Constructs
	 */
	public DjCarPlanBLC()
	{
		// 实体名TA_DJ_JHCL
		this.entityName = "TA_DJ_JHCL";

		// 启动日志
		log = Logger.getLogger(DjCarPlanBLC.class);
	}

	/**
	 * 初始化查询当前团已安排的车辆信息
	 * 
	 * @param rd 请求数据对象
	 * @param sd ssesion数据对象
	 * @return 结果码
	 * @throws SQLException 
	 */
	public int query4Init(BizData rd, BizData sd) throws SQLException
	{
		// 向rd中新增表名为TA_DJ_JHCL及团ID
		rd.add("TA_DJ_JHCL", "TID", 0, rd.getString("GROUPID"));
		rd.add("TA_DJ_JHCL", "orgid", 0, sd.getString("orgid"));
		try
		{
			// 根据团ID，查询计调-车辆安排信息
			this.query(rd,sd);
			
			String sql="select begin_date,ts from ta_dj_group where id ='"+rd.getString("GROUPID")+"' and orgid='"+sd.getString("orgid")+"'";
			
			coreDAO.executeQuery(sql, "TA_DJ_GROUPs", rd);

		}
		catch (SQLException e)
		{
			// 打印日志
			log.error(e.getMessage(), e);

			return SysError.DB_ERROR;
		}

		// 随机数
		int random = 0;

		for (int i = 0; i < rd.getTableRowsCount("TA_DJ_JHCLs"); i++)
		{
			random = (int) (Math.floor(Math.random() * 123450));

			// 装入随即数
			rd.add("TA_DJ_JHCLs", "Random", i, random);
		}
		
		return 999;
	}

	/**
	 * 保存计调安排的车辆信息
	 * 
	 * @param rd 请求数据对象
	 * @param sd ssesion数据对象
	 * @return 结果码
	 * @throws SQLException 
	 */
	public int saveCarInfo(BizData rd, BizData sd) throws SQLException
	{
		// 获取数据库连接
		Connection conn = coreDAO.getConnection();

		BizData data = new BizData();
		
		// 团ID
		String gId = rd.getString("GROUPID");

		// 创建删除条件
		data.add("TA_DJ_JHCL", "TID", 0, gId);
		data.add("TA_DJ_JHCL", "orgid", 0, sd.getString("orgid"));
		
		StringBuffer newVehTeam = new StringBuffer().append("[");
		// 补充计调车辆信息
		buildCarInfo(rd, sd, rd.getString("TEMP"), gId, newVehTeam);
		//新增的酒店信息的ID
		if(newVehTeam.lastIndexOf(",") > 0){
					
			String newVeh = newVehTeam.substring(0, newVehTeam.length()-1);
			newVeh = newVeh+"]";
			rd.add("newBaseInfo", newVeh);
		}
		
		// 取车队基础信息
		BizData bd = (BizData) rd.get("baseVeh");

		try{
			// 开启事务
			coreDAO.beginTrasct(conn);

			// 删除指定车辆信息
			this.delete(data, conn);

			// 插入计调车辆信息
			coreDAO.insert("TA_DJ_JHCL", rd, conn);
			
			// 插入车队基础信息
			coreDAO.insert("TA_CAR_TEAM", bd, conn);
			
			// 提交事务
			coreDAO.commitTrasct(conn);
		}
		catch (SQLException e)
		{
			// 打印日志
			log.error(e.getMessage(), e);

			// 事务回滚
			coreDAO.rollbackTrasct(conn);
			
			return SysError.DB_ERROR;
		}
		finally
		{
			if (null != conn)
			{
				conn.close();
				conn = null;
			}
		}

		return 999;
	}

	/**
	 * 补充计调车辆信息
	 * 
	 * @param rd 请求数据对象
	 * 
	 * 
	 */
	private void buildCarInfo(BizData rd, BizData sd,String state, String gId, StringBuffer newVehTeam)
	{
		// 提交的车辆数目
		int rows = rd.getTableRowsCount("TA_DJ_JHCL");
		int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_JHCL");
		
		// 车队基础数据
		BizData bsData = new BizData();
		
		// 遍历提交的车辆信息
		for (int i = 0; i < rows; i++)
		{
			// 添加ID，ID为自增长值，标识唯一
			rd.add(entityName, "ID", String.valueOf(fieldIndex[i]), this.queryMaxIDByPara(entityName, "ID", null));
			
			// 添加团ID
			rd.add(entityName, "TID", String.valueOf(fieldIndex[i]), rd.getString("groupId"));
			
			// 添加状态
			rd.add(entityName, "JHZT", String.valueOf(fieldIndex[i]) ,state);
			
			rd.add(entityName, "ZDR", String.valueOf(fieldIndex[i]) , sd.getString("userno"));
			
			// 添加机构ID
			rd.add(entityName, "ORGID", String.valueOf(fieldIndex[i]), sd.getString("orgid"));
			
			// 判断是否为库中已有数据
			if ("".equals(rd.getString("entityName", "CD", String.valueOf(fieldIndex[i]))))
			{
				// 新增车队基础信息
				addBaseVehicle(bsData, rd, String.valueOf(fieldIndex[i]), sd .getString("orgid"), newVehTeam);
			}
		}

		// 保存车队基础信息
		rd.add("baseVeh", bsData);
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
		bsData.add("ta_car_team", "CMPNY_NAME", rows, rd.getString("TA_DJ_JHCL", "CDMC", index));
		
		// 添加车队联系人
		bsData.add("ta_car_team", "CHIEF_NAME", rows, rd.getString("TA_DJ_JHCL", "LXR", index));
		
		// 添加车队联系人电话
		bsData.add("ta_car_team", "CHIEF_MOBILE", rows, rd.getString("TA_DJ_JHCL", "LXRDH", index));
		
		// 添加机构ID
		bsData.add("ta_car_team", "orgid", rows, orgid);
		
		// 计划中添加车队ID
		rd.add(entityName, "CD", index, maxId);
		newVehTeam.append("{\"indexNm\":"+index+",\"id\":"+maxId+"},");
    }
}
