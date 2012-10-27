package com.trm.zttdMng;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.databus.BizData;
/**
 * 单团登记表操作事件（ZtRigesterForm）辅助类
 * 
 * @author [hl.Wei]
 * @date [Dec 20, 2011 7:08:45 PM]
 */
public class ZtRigesterFormHelper
{
	/**
	 * 日志打印
	 */
	private static Logger log = Logger.getLogger(ZtRigesterFormHelper.class);
	
	/**
	 * 添加团基本信息
	 * 
	 * @param rd 请求数据对象
	 * @param groupId 团ID
	 * @throws SQLException 
	 */
	public void addGroupInfo(BizData rd,BizData sd,String groupID,ZtRigesterForm ztRigest) throws SQLException
	{
		// 团编号
		rd.add("TA_ZT_GROUP", "ID", 0, groupID);
		
		// 团线路编号
		rd.add("TA_ZT_GROUP", "LINE_ID", 0, rd.getStringByDI("TA_ZT_LINEMNG", "LINE_ID", 0));
		
		// 团状态
		rd.add("TA_ZT_GROUP", "STATE", "1");
		
		// 团类型
		rd.add("TA_ZT_GROUP", "TLX", "1");
		
		// 默认为0-封团
		rd.add("TA_ZT_GROUP", "FTZT", 0, "0");
		
		// 总人数
		int persons = Integer.parseInt(rd.getStringByDI("TA_ZT_GROUP", "ADULT_COUNT", 0))
					+ Integer.parseInt(rd.getStringByDI("TA_ZT_GROUP", "CHILDREN_COUNT", 0));
		
		// 成团人数
		rd.add("TA_ZT_GROUP", "MINPERSONCOUNT", 0, persons);
		
		// 可收客人数
		rd.add("TA_ZT_GROUP", "MAXPERSONCOUNT", 0, persons);
		
		// 查询当前机构ID
		String orgId = selectCurrOrgId(rd,sd,ztRigest.getDao());
		
		// 查询当前用户所在机构的顶级机构ID
		String topId = ztRigest.recursion4ID("HRORGANIZATION", "PARENT_ORGID", "ORGID", orgId, "0", rd);
		
		// 查询当前用户所在机构的顶级机构的名称和联系方式
		selectOrgNameTel(rd,topId,ztRigest.getDao());
		
		// 当前用户NO
		rd.add("TA_ZT_GROUP", "USER_NO", 0, sd.getString("USERNO"));
		
		// 所在旅行社ID
		rd.add("TA_ZT_GROUP", "orgid", 0, topId);
		
		// 所在旅行社名称
		rd.add("TA_ZT_GROUP", "SZLXSMC", 0, rd.getStringByDI("name2tel", "NAME", 0));
		
		// 所在旅行社联系方式
		rd.add("TA_ZT_GROUP", "SZLXSLXDH", 0, rd.getStringByDI("name2tel", "TEL", 0));
	}

	/**
	 * 查询当前用户所在机构的顶级机构的名称和联系方式
	 * 
	 * @param rd 请求数据对象
	 * @param topId 顶层机构ID
	 * @param dao 数据库操作对象
	 */
	private void selectOrgNameTel(BizData rd, String topId, DAO coreDao)
    {
		// SQL语句
		String sql = "select NAME,TEL from HRORGANIZATION where orgid="+topId;
		
		try
        {
			// 执行查询SQL,将结果存入"orgInfo"
	        coreDao.executeQuery(sql, "name2tel", rd);
        }
        catch (SQLException e)
        {
	       e.printStackTrace();
        }
    }

	/**
	 * 查询当前用户所在机构ID
	 * 
	 * @param rd 请求数据对象
	 * @param sd session数据
	 */
	private String selectCurrOrgId(BizData rd, BizData sd,DAO coreDao)
    {
		// 当前用户编号
		String userNo = sd.getString("USERNO");
		
		// 查询当前用户所在机构ID的SQL语句
		String sql = "select ORGNAME,ORGID,EMPTEL,BUSINESS_FAX,BUSINESS_MOBILE,QQ from DRMUSER u ,HREMPLOYEE e where u.userno="+userNo+" and u.EMPID=e.empid";
		
		try
        {
			// 执行查询SQL,将结果存入"orgInfo"
	        coreDao.executeQuery(sql, "orgInfo", rd);
        }
        catch (SQLException e)
        {
	       e.printStackTrace();
	       
	       return "";
        }
        
        // 当前机构ID
        return rd.getStringByDI("orgInfo", "ORGID", 0);
    }

	/**
	 * 给每条行程设置团号和行程ID
	 * 
	 * @param rd 请求数据对象
	 * @param groupID 团ID
	 * @param ztGroup 单团登记表操作事件处理类
	 */
	public void addIdTid(BizData rd, String groupID,
            ZtRigesterForm ztGroup)
    {
		// 遍历每天的线程明细
		for (int i = 0;i < rd.getTableRowsCount("TA_ZT_LINEDETAI4G"); i++)
		{
			// 设置当前线程明细的ID
			rd.add("TA_ZT_LINEDETAI4G", "ID", i, ztGroup.queryMaxIDByPara("TA_ZT_LINEDETAI4G", "ID", null));
			
			// 设置当前线程明细的团ID
			rd.add("TA_ZT_LINEDETAI4G", "TID", i, groupID);
		}
    }

	/**
	 * 创建订单信息
	 * 
	 * @param rd 请求数据对象
	 * @throws SQLException 
	 */
	public int addOrderForm(BizData rd, BizData sd,String groupId, ZtRigesterForm ztGroup) throws SQLException
    {
		// 取发团日期
		String bDate = rd.getStringByDI("TA_ZT_GROUP", "BEGIN_DATE", 0);
		
		// 清洗日期数据
		bDate = bDate.replaceAll("-", "");
		
		// 日期格式化标准
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		// 订单号
        String gMoneyID = "";
		
		try
        {
			// 将发团日期转换成格式化的日期类型数据
	        bDate = sdf.format(sdf.parse(bDate));
	        
	        // 获取最大订单编号
			int gID = ztGroup.queryMaxIDByPara("TA_ZT_YKORDER", "substr(id,11,4)", "substr(id,3,8) = '"+sdf.format(sdf.parse(bDate))+"'");
			
			if(gID < 10) 
			{
				// 创建默认订单号
				gMoneyID = "HJ"+sdf.format(sdf.parse(bDate))+"1001";
				
				ztGroup.clearCacheObj("TA_ZT_YKORDER", "substr(id,11,4)");
			}
			else
			{
				// 自动生成新的订单号
				gMoneyID = "HJ"+sdf.format(sdf.parse(bDate))+gID;
			}
			
        }
        catch (ParseException e)
        {
        	// 打印错误日志
        	log.error("error occurs in method of addOrderForm! ",e);
        	
	       return SysError.BL_EXEC_ERROR;
        }
        
        // 设置订单号
        rd.add("TA_ZT_YKORDER", "ID", 0, gMoneyID);
        
        // 设置团号
        rd.add("TA_ZT_YKORDER", "TID", 0, groupId);
        
        // 设置订单人数
        rd.add("TA_ZT_YKORDER", "YSRS", 0, (Integer.parseInt(rd.getStringByDI("TA_ZT_GROUP", "ADULT_COUNT", 0)) + Integer.parseInt(rd.getStringByDI("TA_ZT_GROUP", "CHILDREN_COUNT", 0))));
		
        // 设置订单状态，默认为0：未确认
        rd.add("TA_ZT_YKORDER", "DD_CONFIRM", 0, "0");
        
        // 制定人
        rd.add("TA_ZT_YKORDER", "CREATER", 0, sd.getString("USERNO"));
        
        // 联系人
        rd.add("TA_ZT_YKORDER", "BUSINESS_NAME", 0, sd.getString("USERNAME"));
        
        // 订单确认
        if ("".equals(rd.getStringByDI("TA_ZT_YKORDER", "ISCONFIRM", 0)))
        {
        	rd.add("TA_ZT_YKORDER", "ISCONFIRM", 0, 0);
        }
        
        // 对账状态; 0：未对账；1：对账中；2：通过；3：不通过
        rd.add("TA_ZT_YKORDER", "DZZT", 0, "0");
        
        // 批发商结算状态;Y/N
        rd.add("TA_ZT_YKORDER", "JSZT", 0, "N");
        
    	// 查询当前机构ID
		String orgId = selectCurrOrgId(rd,sd,ztGroup.getDao());
		
		// 部门ID
		rd.add("TA_ZT_YKORDER", "ORGID", 0, orgId);
		
		// 查询当前用户所在机构的顶级机构ID
		String topId = ztGroup.recursion4ID("HRORGANIZATION", "PARENT_ORGID", "ORGID", orgId, "0", rd);
		
		// 机构ID 
		rd.add("TA_ZT_YKORDER", "PORGID", 0, topId);
		
		// 查询当前用户所在机构的顶级机构的名称
		selectOrgNameTel(rd,topId,ztGroup.getDao());
		
		// 所在旅行社名称
		rd.add("TA_ZT_YKORDER", "CMPNY_NAME", 0, rd.getStringByDI("name2tel", "NAME", 0) + rd.getStringByDI("orgInfo", "ORGNAME", 0));
		
		// 联系人电话
		rd.add("TA_ZT_YKORDER", "BUSINESS_TEL", 0 , rd.getStringByDI("orgInfo", "EMPTEL", 0));
		
		// 传真
		rd.add("TA_ZT_YKORDER", "BUSINESS_FAX", 0, rd.getStringByDI("orgInfo", "BUSINESS_FAX", 0));
		
		// 联系人手机
		rd.add("TA_ZT_YKORDER", "BUSINESS_MOBILE", 0, rd.getStringByDI("orgInfo", "BUSINESS_MOBILE", 0));
		
		// QQ_MSN
		rd.add("TA_ZT_YKORDER", "QQ_MSN", 0, rd.getStringByDI("orgInfo", "QQ", 0));
		
		// 定义事件格式
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// 报名时间
		rd.add("TA_ZT_YKORDER", "REGEDIT_TIME", 0, sdf2.format(new Date()));
        
		return 1;
    }

	/**
	 * 给每条游客信息加入订单号(一个团订单对应1到多个游客信息)
	 * 
	 * @param rd 请求数据对象
	 * @param orderNo 订单号
	 * @param ztRigesterForm 单团登记表操作事件处理类
	 * @return 结果码
	 */
	public void addtouristOrder(BizData rd, String orderNo,
            ZtRigesterForm ztGroup)
    {
		// 遍历每条游客信息
		for (int i = 0; i < rd.getTableRowsCount("TA_ZT_VISITOR"); i++)
		{
			// 订单号
			rd.add("TA_ZT_VISITOR", "M_ID", i, orderNo);
			
			// 编号
			rd.add("TA_ZT_VISITOR","ID", i, ztGroup.queryMaxIDByPara("TA_ZT_VISITOR", "ID", null));
		}
    }

	/**
	 * 补充团价格信息(一个团对应1到多个团价格信息)
	 * 
	 * @param rd 请求数据对象
	 * @param groupID 团ID
	 * @param ztRigesterForm 单团登记表操作事件处理类
	 * @return 结果码
	 */
	public void addGroupPrice(BizData rd, String groupID,
            ZtRigesterForm ztGroup)
    {
		// 遍历所有团价格类型
		for (int i = 0; i < rd.getTableRowsCount("TA_ZT_GPRICE"); i++)
		{
			// 同行价
			rd.add("TA_ZT_GPRICE", "PRICE_TH", i, rd.getStringByDI("TA_ZT_GPRICE", "PRICE_MS", i));
			
			// 团号
			rd.add("TA_ZT_GPRICE", "G_ID", i, groupID);
			
			// 主键
			rd.add("TA_ZT_GPRICE", "ID", i, ztGroup.queryMaxIDByPara("TA_ZT_GPRICE", "ID", null));
		}
    }

	/**
	 * 补充团订单价格（一个订单对应1到多个订单价格）
	 * 
	 * @param rd 请求数据对象
	 * @param orderNo 订单号
	 * @param ztRigesterForm 单团登记表操作事件处理类
	 */
	public int addOrderprice4Grp(BizData rd, String orderNo,
            ZtRigesterForm ztGroup)
    {
	    // 遍历所有团订单价格
	    for (int i = 0; i < rd.getTableRowsCount("TA_ZT_GORDERPRICE"); i++)
	    {
	    	// 主键
	    	rd.add("TA_ZT_GORDERPRICE", "ID", i, ztGroup.queryMaxIDByPara("TA_ZT_GORDERPRICE", "ID", null));
	    	
	    	// 订单号
	    	rd.add("TA_ZT_GORDERPRICE", "ORDERID", i, orderNo);
	    	
	    	// 价格类型
	    	rd.add("TA_ZT_GORDERPRICE", "PRICE_TYPE", i, rd.getStringByDI("TA_ZT_GPRICE", "PRICE_TYPE", i));
	    	
	    	// 门市价
	    	rd.add("TA_ZT_GORDERPRICE", "PRICE_MS", i, rd.getStringByDI("TA_ZT_GPRICE", "PRICE_MS", i));
	    	
	    	// 同行价
	    	rd.add("TA_ZT_GORDERPRICE", "PRICE_TH", i, rd.getStringByDI("TA_ZT_GPRICE", "PRICE_TH", i));
	    }
	    
	    return 1;
    }

	/**
	 * 补充团订单保险信息(一个团订单对应0到多个保险信息)
	 * 
	 * @param rd 请求数据对象
	 * @param orderNo 订单号
	 * @param ztRigesterForm 单团登记表操作事件处理类
	 */
	public void addInsurance(BizData rd, String orderNo,
            ZtRigesterForm ztGroup)
    {
		// 遍历所有团订单保险信息
		for (int i = 0; i < rd.getTableRowsCount("TA_ZT_GINSURANCE"); i++)
		{
			// 主键
	    	rd.add("TA_ZT_GINSURANCE", "ID", i, ztGroup.queryMaxIDByPara("TA_ZT_GINSURANCE", "ID", null));
	    	
	    	// 订单号
	    	rd.add("TA_ZT_GINSURANCE", "ORDERID", i, orderNo);
		}
    }

	/**
	 * 补充结算历史记录
	 * 
	 * @param rd 请求数据对象
	 * @param orderNo 订单号
	 * @param ztRigesterForm 单团登记表操作事件处理类
	 */
	public void addAccountHis(BizData rd, String orderNo,
            ZtRigesterForm ztGroup)
    {
		// 主键
    	rd.add("TA_ZT_DZJL_HIS", "ID", 0, ztGroup.queryMaxIDByPara("TA_ZT_DZJL_HIS", "ID", null));
    	
    	// 订单号
    	rd.add("TA_ZT_DZJL_HIS", "ORDERID", 0, orderNo);
    	
    	// 应付款
    	rd.add("TA_ZT_DZJL_HIS", "FK_ALREADY", 0, rd.getStringByDI("TA_ZT_YKORDER", "YIN_SK", 0));
    	
    	// 已付款
    	rd.add("TA_ZT_DZJL_HIS", "FK_SHOULD", 0, rd.getStringByDI("TA_ZT_YKORDER", "YI_SK", 0));
    	
    	// 本次付款
    	rd.add("TA_ZT_DZJL_HIS", "FK_THISTIME", 0, rd.getStringByDI("TA_ZT_YKORDER", "YI_SK", 0));
    }
	
	/**
	 * 补充线路信息
	 * 
	 * @param rd
	 * @param ztRigesterForm
	 * @throws SQLException 
	 */
	public void addLineInfo(BizData rd, BizData sd, ZtRigesterForm ztRigest) throws SQLException 
	{
		// 线路ID,需最大值
		rd.add("TA_ZT_LINEMNG", "LINE_ID", 0,ztRigest.queryMaxIDByPara("TA_ZT_LINEMNG", "LINE_ID", null));
		
		// 线路名称
		rd.add("TA_ZT_LINEMNG", "LINE_NAME", 0, rd.getStringByDI("TA_ZT_GROUP", "XLMC", 0));
		
		// 发布人ID
		rd.add("TA_ZT_LINEMNG", "USERNO", 0, sd.getString("USERNO"));
		
		// 线路性质,2:单团
		rd.add("TA_ZT_LINEMNG", "XLXZ", 0, "2");
		
		// 查询当前机构ID
		String orgId = selectCurrOrgId(rd,sd,ztRigest.getDao());
		
		// 查询当前用户所在机构的顶级机构ID
		String topId = ztRigest.recursion4ID("HRORGANIZATION", "PARENT_ORGID", "ORGID", orgId, "0", rd);
		
		// 查询当前用户所在机构的顶级机构的名称和联系方式
		selectOrgNameTel(rd,topId,ztRigest.getDao());
		
		// 当前用户NO
		rd.add("TA_ZT_LINEMNG", "USERNO", 0, sd.getString("USERNO"));
		
		// 所在旅行社ID
		rd.add("TA_ZT_LINEMNG", "orgid", 0, topId);
		
		// 所在旅行社名称
		rd.add("TA_ZT_LINEMNG", "SZLXSMC", 0, rd.getStringByDI("name2tel", "NAME", 0));
		
		// 所在旅行社联系方式
		rd.add("TA_ZT_LINEMNG", "SZLXSLXDH", 0, rd.getStringByDI("name2tel", "TEL", 0));
		
	}
	
	/**
	 * 补充线路保险对应关系信息
	 * 
	 * @param rd 请求数据对象
	 * @param lineId 线路ID
	 */
	public void addLine2Insura(BizData rd, String lineId)
	{
		// 遍历所有保险信息
		for (int i = 0; i < rd.getTableRowsCount("TA_ZT_GINSURANCE"); i++)
		{
			// 线路ID
			rd.add("TA_ZT_LINEANDINSURANCE", "LINE_ID", i, lineId);
			
			// 保险类型
			rd.add("TA_ZT_LINEANDINSURANCE", "INSURANCEID", i , Integer.parseInt(rd.getStringByDI("TA_ZT_GINSURANCE", "INSURANCETYPE", i)));
		}
	}
	
	/**
	 * 补充线路集合地点
	 * 
	 * @param rd 请求数据对象
	 * @param lineId 线路ID
	 */
	public void addGather(BizData rd, String lineId,ZtRigesterForm ztGroup)
	{
		// 遍历当前团的所有线路
		for (int i = 0; i < rd.getTableRowsCount("TA_ZT_GATHER"); i++)
		{
			// 线路ID
			rd.add("TA_ZT_GATHER", "LINE_ID", i, lineId);
			
			// 集合地点ID
			rd.add("TA_ZT_GATHER", "GATHER_ID", i, ztGroup.queryMaxIDByPara("TA_ZT_GATHER", "GATHER_ID", null));
		}
	}

	/**
	 * 插入团行程明细信息(1个团对应1到多个行程明细，行程明细以天为单位)
	 * 
	 * @param rd 请求数据对象
	 * @param conn 数据库连接
	 * @param groupId 团ID
	 * @return 结果码
	 * @throws SQLException 数据库执行过程错误
	 */
	public int insertRouteDetail(BizData rd, Connection conn, String groupId) throws SQLException 
	{
		// 插入团行程明细SQL语句
		String sql = "insert into TA_ZT_LINEDETAI4G(ID,TID,RQ,XCMX,BREAKFAST,CMEAL,SUPPER,ZSBZ) values (?,?,?,?,?,?,?,?)";
		
		// 定义DB操作对象
		PreparedStatement preStmt = null;
		
        try
        {
        	// DB操作对象
    		preStmt = conn.prepareStatement(sql);
	        
    		// 遍历所有的行程明细
			for(int i = 0; i < rd.getTableRowsCount("TA_ZT_LINEDETAI4G"); i++)
			{
				// 设置主键
				preStmt.setInt(1, Integer.parseInt(rd.getStringByDI("TA_ZT_LINEDETAI4G", "ID", i)));
				
				// 设置团ID
				preStmt.setString(2, rd.getStringByDI("TA_ZT_LINEDETAI4G", "TID", i));
				
				// 设置日期
				preStmt.setString(3, rd.getStringByDI("TA_ZT_LINEDETAI4G", "RQ", i));
				
				// 设置行程明细
				preStmt.setBytes(4, rd.getStringByDI("TA_ZT_LINEDETAI4G", "XCMX", i).getBytes("GBK"));
				
				// 设置早餐
				preStmt.setString(5, rd.getStringByDI("TA_ZT_LINEDETAI4G", "BREAKFAST", i));
				
				// 设置中餐
				preStmt.setString(6, rd.getStringByDI("TA_ZT_LINEDETAI4G", "CMEAL", i));
				
				// 设置晚餐
				preStmt.setString(7, rd.getStringByDI("TA_ZT_LINEDETAI4G", "SUPPER", i));
				
				// 设置住宿标准
				preStmt.setString(8, rd.getStringByDI("TA_ZT_LINEDETAI4G", "ZSBZ", i));
				
				// 执行当前SQL语句
				preStmt.executeUpdate();
			}
			
			preStmt.close();
        }
        catch (SQLException e)
        {
	        e.printStackTrace();
	        
	        // 空判断
	        if(null != preStmt)
	        {
	        	// 关闭DB操作对象
	        	preStmt.close();
	        }
	        
	        return SysError.INSERT_ERROR;
        }
        catch (UnsupportedEncodingException e)
        {
	        e.printStackTrace();
	        
	        // 空判断
	        if (null != preStmt)
	        {
	        	// 关闭DB操作对象
	        	preStmt.close();
	        }
	        
	        return SysError.PARAM_PARSE_ERROR;
        }
		
		// DEBUG模式，打印SQL
		log.debug("团行程明细表插入SQL："+sql);
		
		return 1;
	}
	
	/**
	 * 插入线路管理BLOB信息
	 * 
	 * @param rd 请求数据对象
	 * @param lineId 线路ID
	 * @param conn 数据库连接
	 * @return 结果码
	 * @throws SQLException 
	 */
	public int insertLineMngBlob(BizData rd, String lineId, Connection conn) throws SQLException
    {
		// 插入线路管理BLOB信息的SQL
		String sql = "insert into TA_ZT_LINEMNGBLOB(XLID,TSJS,FYBH,FYBBH,YDXZ,ZFXM,GWD,WXTS,CJLYXZ,LYZYSX) values (?,?,?,?,?,?,?,?,?,?)";
		
		// 定义DB操作对象
		PreparedStatement preStmt = null;
		
		try
        {
			// DB操作对象
	        preStmt = conn.prepareStatement(sql);
	        
	        // 设置线路ID
	        preStmt.setInt(1, Integer.parseInt(lineId));
	        
		    // 设置特色介绍
	        preStmt.setBytes(2, rd.getStringByDI("TA_ZT_LINEMNGBLOB", "TSJS", 0).getBytes("GBK"));
		        
		    // 设置费用包含
	        preStmt.setBytes(3, rd.getStringByDI("TA_ZT_LINEMNGBLOB", "FYBH", 0).getBytes("GBK"));
		        
		    // 设置费用不包含
	        preStmt.setBytes(4, rd.getStringByDI("TA_ZT_LINEMNGBLOB", "FYBBH", 0).getBytes("GBK"));
		        
		    // 设置预订须知
	        preStmt.setBytes(5, rd.getStringByDI("TA_ZT_LINEMNGBLOB", "YDXZ", 0).getBytes("GBK"));
		        
		    // 设置自费项目
	        preStmt.setBytes(6, rd.getStringByDI("TA_ZT_LINEMNGBLOB", "ZFXM", 0).getBytes("GBK"));
		        
		    // 设置购物店
	        preStmt.setBytes(7, rd.getStringByDI("TA_ZT_LINEMNGBLOB", "GWD", 0).getBytes("GBK"));
		        
		    // 设置温馨提示
	        preStmt.setBytes(8, rd.getStringByDI("TA_ZT_LINEMNGBLOB", "WXTS", 0).getBytes("GBK"));
		        
		    // 设置出境旅游须知
	        preStmt.setBytes(9, rd.getStringByDI("TA_ZT_LINEMNGBLOB", "CJLYXZ", 0).getBytes("GBK"));
		        
		    // 设置旅游注意事项
	        preStmt.setBytes(10, rd.getStringByDI("TA_ZT_LINEMNGBLOB", "LYZYSX", 0).getBytes("GBK"));
	        
	        // 执行SQL
	        preStmt.executeUpdate();
		        
        }
		catch (SQLException e)
	    {
			e.printStackTrace();
		        
		    // 空判断
		    if(null != preStmt)
		    {
		    	// 关闭DB操作对象
		        preStmt.close();
		    }
		        
		    return SysError.INSERT_ERROR;
	     }
	     catch (UnsupportedEncodingException e)
	     {
		     e.printStackTrace();
		        
		     // 空判断
		     if (null != preStmt)
		     {
		        // 关闭DB操作对象
		        preStmt.close();
		     }
		        
		     return SysError.PARAM_PARSE_ERROR;
	      }
		
		return 1;
    }

}
