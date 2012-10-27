package com.trmdj.businessPlan.plan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class DjTicketPlanBLC extends DBBLC
{

	public DjTicketPlanBLC()
	{
		this.entityName = "TA_DJ_JHPW";
	}

	/**
	 * 新增票务信息
	 * 
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int insertTicket(BizData rd, BizData sd) throws SQLException
	{
		String[] hotelRow = rd.getRowIDs("TA_DJ_JHPW");// 获取记录数
		String groupId = rd.getString("groupId");// 获取团号
		String temp = rd.getString("temp");// 获取计划状态
		String zDr = sd.getString("userno");// 获取指定人

		// 根据团号删除加点计划
		BizData data = new BizData();
		data.add("TA_DJ_JHPW", "TID", groupId);
		data.add("TA_DJ_JHPW", "orgid", sd.getString("orgid"));

		// 票务代购点基础数据
		BizData bsData = new BizData();

		StringBuffer theNewTicket = new StringBuffer().append("[");
		for (int i = 0; i < hotelRow.length; i++)
		{
			int hotelId = queryMaxIDByPara("TA_DJ_JHPW", "ID", null);
			int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_JHPW");
			rd.add("TA_DJ_JHPW", "ID", String.valueOf(fieldIndex[i]), hotelId);
			rd.add("TA_DJ_JHPW", "TID", String.valueOf(fieldIndex[i]), groupId);
			rd.add("TA_DJ_JHPW", "JHZT", String.valueOf(fieldIndex[i]), temp);
			rd.add("TA_DJ_JHPW", "ZDR", String.valueOf(fieldIndex[i]), zDr);

			// 添加机构ID
			rd.add("TA_DJ_JHPW", "ORGID", String.valueOf(fieldIndex[i]), sd.getString("orgid"));

			// 判断是否为库中已有数据
			if ("".equals(rd.getString("TA_DJ_JHPW", "DGD", String.valueOf(fieldIndex[i]))))
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

			Connection conn = coreDAO.getConnection();
			try
			{
				coreDAO.beginTrasct(conn);
				coreDAO.delete(data, conn);// 删除历史数据
				data.remove(entityName);
				
				// 数据入库
				coreDAO.insert("TA_DJ_JHPW", rd, conn);

				// 插入票务基础信息
				coreDAO.insert("ta_ticket", bsData, conn);

				coreDAO.commitTrasct(conn);
			}
			catch (Exception e)
			{
				try
				{
					coreDAO.rollbackTrasct(conn);
				}
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			finally
			{
				if (null != conn)
				{
					try
					{
						conn.close();
					}
					catch (SQLException e)
					{
						e.printStackTrace();
					}
				}
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
		bsData.add("ta_ticket", "CMPNY_NAME", rows, rd.getString("TA_DJ_JHPW", "DGDMC", index));
		
		// 添加餐厅联系人
		bsData.add("ta_ticket", "CHIEF_NAME", rows, rd.getString("TA_DJ_JHPW", "LXR", index));
		
		// 添加餐厅联系人电话
		bsData.add("ta_ticket", "CHIEF_MOBILE", rows, rd.getString("TA_DJ_JHPW", "LXRDH", index));
		
		// 添加机构ID
		bsData.add("ta_ticket", "orgid", rows, orgid);
		
		// 计划中添加票务点ID
		rd.add("TA_DJ_JHPW", "DGD", index, maxId);
		//返回该新增的基础信息的数组索引值和当前的主键
		theNewTicket.append("{\"indexNm\":"+index+",\"id\":"+maxId+"},");
	}

	/**
	 * Ajax初始化票务页面
	 * 
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int djAjaxTicketInfo(BizData rd, BizData sd)
	{
		String groupId = rd.getString("groupId");
		String sql = "select * from TA_DJ_JHPW where tid='" + groupId + "' and orgid="+sd.getString("orgid");
		try
		{
			coreDAO.executeQuery(sql, "TA_DJ_JHPW", rd);
			sql = "select begin_date,ts from ta_dj_group where id ='" + groupId + "' and orgid='"+sd.getString("orgid")+"'";
			coreDAO.executeQuery(sql, "TA_DJ_GROUPs", rd);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		int tRow = rd.getTableRowsCount("TA_DJ_JHPW");// 获取查询记录行数
		int random = 0;// 装随机数

		for (int i = 0; i < tRow; i++)
		{
			random = (int) (Math.floor(Math.random() * 123450));
			rd.add("TA_DJ_JHPW", "Random", i, random);// 装入随即数
		}
		return 999;
	}

	public int queryTicketPlan(BizData rd, BizData sd)
	{
		rd.add("flag", rd.getString("flag"));
		String groupID = rd.getStringByDI("TA_DJ_JHPW", "tid", 0);
		try
		{
			int rows = query(rd, sd);
			if (rows == 0)
			{
				rd.add("tid", groupID);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 3;
	}

	public int saveTicket(BizData rd, BizData sd)
	{

		BizData data = new BizData();
		Connection conn = coreDAO.getConnection();
		String[] ticketRows = rd.getRowIDs("TA_DJ_JHPW");
		String groupID = rd.getStringByDI("TA_DJ_JHPW", "tid", 0);
		try
		{
			coreDAO.beginTrasct(conn);
			// 根据团ID查询对应的票务实施数据
			data.add("TA_DJ_JHPW", "tid", groupID);
			coreDAO.select(data);
			// 根据团ID删除票务计划
			coreDAO.deleteEntity("TA_DJ_JHPW", data, conn);
			data.remove("TA_DJ_JHPW");
			// 根据票务计划ID删除票务实施明细表
			for (int i = 0; i < data.getTableRowsCount("TA_DJ_JHPWs"); i++)
			{
				String planID = data.getStringByDI("TA_DJ_JHPWs", "id", i);
				data.add("TA_DJ_JHPWMX", "JHID", i, planID);
			}
			data.remove("TA_DJ_JHPWs");
			coreDAO.delete(data, conn);
			data.remove("TA_DJ_JHPWMX");
			// 票务计划表主键
			for (int i = 0; i < ticketRows.length; i++)
			{
				int id = queryMaxIDByPara("TA_DJ_JHPW", "ID", null);
				rd.add("TA_DJ_JHPW", "id", ticketRows[i], id);
				// 插入票务明细表
				String[] trItemRows = rd.getRowIDs("TA_DJ_JHPWMX" + i);
				for (int j = 0; j < trItemRows.length; j++)
				{
					int mxID = queryMaxIDByPara("TA_DJ_JHPWMX", "ID", null);
					rd.add("TA_DJ_JHPWMX" + i, "id", trItemRows[j], mxID);
					rd.add("TA_DJ_JHPWMX" + i, "JHID", trItemRows[j], id);
					rd.add("TA_DJ_JHPWMX" + i, "TID", trItemRows[j], groupID);
				}
				rd.renameEntity("TA_DJ_JHPWMX" + i, "TA_DJ_JHPWMX");
				coreDAO.insert("TA_DJ_JHPWMX", rd, conn);
				rd.remove("TA_DJ_JHPWMX");
			}
			coreDAO.insert("TA_DJ_JHPW", rd, conn);
			rd.remove("TA_DJ_JHPW");
			// 更新团表中的费用总计
			updateTDJDXXZJB(rd, data, conn, groupID);
			BizData dtt = new BizData();
			dtt.addAttr("TA_DJ_GROUP", "ID", 0, "oldValue", groupID);
			dtt.add("TA_DJ_GROUP", "STATE", "2");// 修改团状态为 2 实施中
			coreDAO.update("TA_DJ_GROUP", dtt, conn);
			coreDAO.commitTrasct(conn);
		}
		catch (SQLException e)
		{
			try
			{
				coreDAO.rollbackTrasct(conn);
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (null != conn)
				{
					conn.close();
					conn = null;
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return 38;
	}

	/**
	 * 更新团表中的费用总计
	 * 
	 * @param rd
	 * @param data
	 * @param conn
	 * @param groupID
	 * @throws SQLException
	 */
	public void updateTDJDXXZJB(BizData rd, BizData data, Connection conn,
	        String groupID) throws SQLException
	{
		data.add("TA_TDJDXXZJB", "TID", groupID);
		coreDAO.select(data);
		int rows = data.getTableRowsCount("TA_TDJDXXZJBs");
		data.remove("TA_TDJDXXZJBs");
		if (rows > 0)
		{
			rd.addAttr("TA_TDJDXXZJB", "TID", 0, "oldValue", groupID);
			coreDAO.update("TA_TDJDXXZJB", rd, conn);
		}
		else
		{
			rd.add("TA_TDJDXXZJB", "TID", groupID);
			coreDAO.insert("TA_TDJDXXZJB", rd, conn);
		}
	}
}
