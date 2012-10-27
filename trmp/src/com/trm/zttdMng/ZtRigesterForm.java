package com.trm.zttdMng;

/*
 * 1、集合地点改为可选 2、页面上去掉组团社和是否封团
 * 3、邮编改成签发地点 4、集合地点需加备注
 * 5/客人的姓名和联系方式，每一位客人的身份证号，护照号，有效期，签发日期，签发地
，出生地团队
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.databus.BizData;

/**
 * 单团登记表操作事件处理类
 * 
 * @author [hl.Wei]
 * @date [Dec 17, 2011 6:54:39 PM]
 */
public class ZtRigesterForm extends DBBLC
{
	/**
	 * 日志打印
	 */
	private static Logger log = Logger.getLogger(ZtRigesterForm.class);
	
	/**
	 * 时期格式化对象
	 */
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 处理新建单团登记表请求
	 * 
	 * @param rd 请求数据
	 * @param sd session
	 * @return 结果码
	 */
	public int rigesterForm(BizData rd, BizData sd)
	{
		try
		{
			// 加入线路信息
			rd.add("TA_ZT_LINEMNG", "LINE_ID", null);

			// 加入保险信息
			rd.add("TA_ZT_INSURANCE", "ID", null);
			
			// 加入所有代码说明信息
			rd.add("DMSM", "dmlb", null);
			
			// 加入集合地
			rd.add("TA_ZT_GATHER_HIS","GATHER",null);

			// 查询所有线路信息
			int rstCode = coreDAO.select("TA_ZT_LINEMNG", rd, true);

			// 查询所有保险信息
			rstCode = coreDAO.select("TA_ZT_INSURANCE", rd, true);
			
			// 查询所有代码说明
			rstCode = coreDAO.select("DMSM", rd, true);
			
			// 查询所有集合点
			rstCode = coreDAO.select("TA_ZT_GATHER_HIS", rd,true);

			if (0 > rstCode)
			{
				log.error("select fail to table with TA_ZT_LINEMNG!");

				return SysError.DB_ERROR;
			}
		}
		catch (SQLException e)
		{
			log.error("select fail to table with TA_ZT_LINEMNG!", e);

			return SysError.DB_ERROR;
		}

		return 999;
	}

	/**
	 * 处理单团信息提交请求
	 * 
	 * @param rd 请求数据
	 * @param sd session数据
	 * @return 结果码
	 * @throws SQLException 
	 * @throws SQLException
	 */
	public int submitForm(BizData rd, BizData sd) throws SQLException
	{
		// 打印日志，进入方法
		log.debug("enter into the method of submitForm for ZtRigesterForm");

		// 结果码;默认为1，当小于1则表明执行过程失败，大于或等于1则反之
		int rstCode = 1;

		// 创建辅助类
		ZtRigesterFormHelper helper = new ZtRigesterFormHelper();

		// 创建数据库连接
		Connection conn = coreDAO.getConnection();

		try
		{
			// 开启事务
			coreDAO.beginTrasct(conn);
			
			/********************团线路相关**********************************/
			// 补充团线路信息
			helper.addLineInfo(rd, sd, this);
			
			// 插入线路信息
			coreDAO.insert("TA_ZT_LINEMNG", rd, conn);
			
			// 插入失败则退出
			if (1 > rstCode)
			{
				// 事务回滚
				coreDAO.rollbackTrasct(conn);
				
				return rstCode;
			}
			
			// 取线路ID
			String lineId = rd.getStringByDI("TA_ZT_LINEMNG", "LINE_ID", 0);
			
			// 补充线路保险对应关系信息
			helper.addLine2Insura(rd, lineId);
			
			// 插入线路保险对应关系信息
			rstCode = coreDAO.insert("TA_ZT_LINEANDINSURANCE", rd, conn);

			// 插入失败则退出
			if (0 > rstCode)
			{
				// 事务回滚
				coreDAO.rollbackTrasct(conn);
				
				return rstCode;
			}
			
			// 补充线路集合地点
			helper.addGather(rd, lineId, this);
			
			// 插入线路集合地点
			rstCode = coreDAO.insert("TA_ZT_GATHER", rd, conn);
			
			// 插入失败则退出
			if (0 > rstCode)
			{
				// 事务回滚
				coreDAO.rollbackTrasct(conn);
				
				return rstCode;
			}
			
			// 插入线路管理BLOB信息
			rstCode = helper.insertLineMngBlob(rd, lineId, conn);
			
			// 插入失败则退出
			if (0 > rstCode)
			{
				// 事务回滚
				coreDAO.rollbackTrasct(conn);
				
				return rstCode;
			}
			
			/**************************团基本信息相关*********************************/
			// 生成团ID
			rd.add("TA_GROUPNUMROLE", "ORGID", sd.getString("orgid"));
			rd.add("TA_GROUPNUMROLE", "YWLB", "s");
			rd.add("TA_GROUPNUMROLE", "YWfl", "z");
			try {
				coreDAO.select("TA_GROUPNUMROLE", rd);
				rd.remove("TA_GROUPNUMROLE");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			String prefix = rd.getStringByDI("TA_GROUPNUMROLEs", "GROUPPREFIX", 0);
			rd.remove("TA_GROUPNUMROLEs");
			String groupID = createGroupNO(prefix, sdf.parse(rd.getStringByDI(
			        "TA_ZT_GROUP", "BEGIN_DATE", 0)), "ta_zt_group", "id", Integer.parseInt(sd.getString("orgid")), conn);

			// 补充团基本信息
			helper.addGroupInfo(rd, sd, groupID, this);

			// 插入团基本信息
			rstCode = coreDAO.insert("TA_ZT_GROUP", rd, conn);

			// 插入失败则退出
			if (1 > rstCode)
			{
				// 事务回滚
				coreDAO.rollbackTrasct(conn);
				
				return rstCode;
			}

			// 给每条行程设置团号和行程ID
			helper.addIdTid(rd, groupID, this);

			// 插入团行程明细信息
			rstCode = helper.insertRouteDetail(rd, conn, groupID);

			// 插入失败则退出
			if (1 > rstCode)
			{
				// 事务回滚
				coreDAO.rollbackTrasct(conn);
				
				return rstCode;
			}
			
			// 补充团价格信息
			helper.addGroupPrice(rd,groupID,this);
			
			// 插入团价格信息
			rstCode = coreDAO.insert("TA_ZT_GPRICE", rd, conn);
			
			// 插入失败则退出
			if (1 > rstCode)
			{
				// 事务回滚
				coreDAO.rollbackTrasct(conn);
				
				return rstCode;
			}

			/*********************团订单相关******************************/
			// 创建订单信息
			rstCode = helper.addOrderForm(rd, sd, groupID, this);
			
			// 创建订单信息失败则退出
			if (1 > rstCode)
			{
				// 事务回滚
				coreDAO.rollbackTrasct(conn);
				
				return rstCode;
			}

			// 插入团订单信息
			rstCode = coreDAO.insert("TA_ZT_YKORDER", rd, conn);

			// 插入失败则退出
			if (1 > rstCode)
			{
				// 事务回滚
				coreDAO.rollbackTrasct(conn);
				
				return rstCode;
			}
			
			// 取订单号,所有团订单相关表都需要
			String orderNo = rd.getStringByDI("TA_ZT_YKORDER", "ID", 0);
			
			// 补充团订单价格
			helper.addOrderprice4Grp(rd, orderNo, this);
			
			// 插入团订单价格信息
			rstCode = coreDAO.insert("TA_ZT_GORDERPRICE", rd, conn);
			
			// 插入失败则退出
			if (1 > rstCode)
			{
				// 事务回滚
				coreDAO.rollbackTrasct(conn);
				
				return rstCode;
			}
			
			// 补充团订单保险信息
			helper.addInsurance(rd, orderNo, this);
			
			// 插入团订单保险信息
			rstCode = coreDAO.insert("TA_ZT_GINSURANCE", rd, conn);
			
			// 插入失败则退出
			if (0 > rstCode)
			{
				// 事务回滚
				coreDAO.rollbackTrasct(conn);
				
				return rstCode;
			}

			// 补充游客信息，加入订单号
			helper.addtouristOrder(rd, orderNo, this);

			// 插入游客信息
			rstCode = coreDAO.insert("TA_ZT_VISITOR", rd, conn);
			
			// 插入失败则退出
			if (1 > rstCode)
			{
				// 事务回滚
				coreDAO.rollbackTrasct(conn);
				
				return rstCode;
			}
			
			// 补充结算历史记录
			helper.addAccountHis(rd, orderNo, this);
			
			// 插入结算历史记录
			rstCode = coreDAO.insert("TA_ZT_DZJL_HIS", rd, conn);
			
			// 插入失败则退出
			if (1 > rstCode)
			{
				// 事务回滚
				coreDAO.rollbackTrasct(conn);
				
				return rstCode;
			}
			
			/********************结束数据插入**********************************/

			// 提交事务
			coreDAO.commitTrasct(conn);
		}
		catch (SQLException e)
		{
			// 事务回滚
			coreDAO.rollbackTrasct(conn);

			log.debug("fail,exit the method of submitForm for ZtRigesterForm",
			        e);

			return SysError.INSERT_ERROR;
		}
		catch (ParseException e)
		{
			return SysError.PARAM_PARSE_ERROR;
		}

		// 打印成功退出方法
		log.debug("succeed,exit the method of submitForm for ZtRigesterForm");

		return 1;
	}
	
	/**
	 * 获取DB操作对象
	 * 
	 * @return DB操作对象
	 */
	public DAO getDao()
	{
		return this.coreDAO;
	}
}
