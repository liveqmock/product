package com.trmdj.businessPlan.groupMng;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * @Title: DjGroupDetailBLC.java
 * @Package com.trmdj.businessPlan.groupMng
 * @Description: TODO(描述)
 * @author Kale ym_x@qq.com
 * @date 2011-9-7 下午05:53:50
 * @version V1.0
 */
public class DjGroupDetailBLC extends DBBLC
{
	/**
	 * 日志对象
	 */
	Logger log = null;
	
	/**
	 * Constructs
	 */
	public DjGroupDetailBLC()
	{
		this.entityName = "TA_DJ_LINEDETAI4G";
		
		// 启动日志
		log = Logger.getLogger(DjGroupDetailBLC.class);
	}
	
	/**
	 * 地接审核初始化页面内容
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int djInitAllSHPlan(BizData rd, BizData sd) {
		
		Connection conn = coreDAO.getConnection();
		try {
			//团计调安排情况
			queryAllState(rd, sd, conn);
			//团队基础信息
			coreDAO.select("TA_DJ_GROUP", rd, true, conn);
			//团行程明细
			coreDAO.select("TA_DJ_LINEDETAI4G", rd, true, conn);
			//审批意见表
			viewSpyj(rd, sd, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try{
				if(null != conn)
					conn.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		return 98;
	}
	
	private void queryAllState(BizData rd, BizData sd, Connection conn)
	{
		rd.add("md",rd.getString("md"));//模块index
		rd.add("mdName", rd.getString("mdName"));//模块id名称
		rd.add("mdUrl", rd.getString("mdUrl").replace(",", "&"));//模块url
		try
        {
			// 酒店计划TA_DJ_JHHOTEL
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHHOTEL","JHHOTEL", sd.getString("orgid"), rd, conn);
			
			// 餐厅安排TA_DJ_JHCT
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHCT","JHCT", sd.getString("orgid"),rd, conn);
			
			// 票务安排 TA_DJ_JHPW
			queryState("sum(QD) QD,sum(XF) XF,sum(YGCB) YGCB","TA_DJ_JHPW","JHPW", sd.getString("orgid"),rd, conn);
			
			// 车辆安排 TA_DJ_jHCL
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(JG) JG","TA_DJ_jHCL","jHCL", sd.getString("orgid"),rd, conn);
			
			// 景点安排TA_DJ_JHJD
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(TGCB) TGCB","TA_DJ_JHJD","JHJD", sd.getString("orgid"),rd, conn);
			
			// 地接安排TA_DJ_JHDJ
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YFZK) YFZK","TA_DJ_JHDJ","JHDJ", sd.getString("orgid"),rd, conn);
			
			// 购物安排TA_DJ_JHGW
			queryState("count(id)","TA_DJ_JHGW","JHGW", sd.getString("orgid"),rd, conn);
			
			// 加点安排TA_DJ_JHJIAD
			queryState("count(id)","TA_DJ_JHJIAD","JHJIAD", sd.getString("orgid"),rd, conn);
			
			// 其他安排TA_DJ_JHQT
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHQT","JHQT", sd.getString("orgid"),rd, conn);
			
			// 导游安排TA_DJ_JHDY
			queryState("sum(DYLK) DYLK,sum(DFF) DFF","TA_DJ_JHDY","JHDY", sd.getString("orgid"),rd, conn);
        } catch (Exception e) {
	        // 打印错误日志
        	log.error("查询失败", e);
        }
	}
	
	private void viewSpyj(BizData rd, BizData sd, Connection conn) {
		
		String groupID = rd.getString("TA_DJ_TSPB", "TID", 0);// 获取查询条件:团号
		String spyj="select t.spyj,u.username from ta_dj_tspb t,drmuser u where t.spr=u.userno and t.mklb='1' and t.tid='"+groupID+"' and t.orgid='"+sd.getString("orgid")+"'";
		try {
			coreDAO.executeQuery(spyj, "SPYJ", rd, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询所有计调安排情况
	 * 
	 * @param rd 请求数据对象
	 * @param sd Session数据对象
	 * @return 结果码
	 */
	public int queryAllState(BizData rd, BizData sd)
	{
		rd.add("md",rd.getString("md"));//模块index
		rd.add("mdName", rd.getString("mdName"));//模块id名称
		rd.add("mdUrl", rd.getString("mdUrl").replace(",", "&"));//模块url
		Connection conn = coreDAO.getConnection();
		try
        {
			// 酒店计划TA_DJ_JHHOTEL
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHHOTEL","JHHOTEL", sd.getString("orgid"), rd, conn);
			
			// 餐厅安排TA_DJ_JHCT
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHCT","JHCT", sd.getString("orgid"),rd, conn);
			
			// 票务安排 TA_DJ_JHPW
			queryState("sum(QD) QD,sum(XF) XF,sum(YGCB) YGCB","TA_DJ_JHPW","JHPW", sd.getString("orgid"),rd, conn);
			
			// 车辆安排 TA_DJ_jHCL
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(JG) JG","TA_DJ_jHCL","jHCL", sd.getString("orgid"),rd, conn);
			
			// 景点安排TA_DJ_JHJD
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(TGCB) TGCB","TA_DJ_JHJD","JHJD", sd.getString("orgid"),rd, conn);
			
			// 地接安排TA_DJ_JHDJ
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YFZK) YFZK","TA_DJ_JHDJ","JHDJ", sd.getString("orgid"),rd, conn);
			
			// 购物安排TA_DJ_JHGW
			queryState("count(id)","TA_DJ_JHGW","JHGW", sd.getString("orgid"),rd, conn);
			
			// 加点安排TA_DJ_JHJIAD
			queryState("count(id)","TA_DJ_JHJIAD","JHJIAD", sd.getString("orgid"),rd, conn);
			
			// 其他安排TA_DJ_JHQT
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHQT","JHQT", sd.getString("orgid"),rd, conn);
			
			// 导游安排TA_DJ_JHDY
			queryState("sum(DYLK) DYLK,sum(DFF) DFF","TA_DJ_JHDY","JHDY", sd.getString("orgid"),rd, conn);
        } catch (Exception e) {
	        // 打印错误日志
        	log.error("查询失败", e);
        	return SysError.DB_ERROR;
        }finally {
        	try{
        		if(conn != null){
        			conn.close();
        			conn = null;
        		}
        	}catch(SQLException e){
        		log.error("关闭conn失败：", e);
        		return SysError.DB_ERROR;
        	}
        }
        
		return 999;
	}
	
	/**
	 * 查询统计计划安排
	 * 
	 * @param str 参数
	 * @param table 表名
 	 * @param rd 请求数据对象
	 * @return 结果码
	 * @throws SQLException 
	 */
	private int queryState(String str, String table,String alias, String orgid,BizData rd, Connection conn) throws SQLException
	{
		// 初始化SQL
		StringBuffer sql = new StringBuffer("select count(*) sum,");
		
		// 构造查询SQL
		sql.append(str);
		sql.append(" from ");
		sql.append(table);
		sql.append(" where TID='");
		sql.append(rd.getStringByDI("TA_DJ_GROUP", "ID", 0));
		sql.append("' and orgid=");
		sql.append(orgid);
		
		// debug模式
		log.debug("执行查询：" + sql);
		
		// 执行SQL
	    coreDAO.executeQuery(sql.toString(), alias, rd, conn);
       
		return 999;
	}
	
	/**
	 * 初始化团报账审核
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int djInitAllSHBx(BizData rd, BizData sd){
		
		Connection conn = coreDAO.getConnection();
		try{
			//团基本信息
			coreDAO.select("TA_DJ_GROUP", rd, true, conn);
			//团行程明细
			coreDAO.select("TA_DJ_LINEDETAI4G", rd, true, conn);
			//审批意见
			spyj(rd, sd, "2", conn);
			//团计调安排情况 
			queryAllBxState(rd, sd, conn);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try{
				if(null != conn)
					conn.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		return 8;
	}
	
	/**
	 * 审批意见查询
	 * @param rd
	 * @param sd
	 * @param conn
	 * @throws SQLException
	 */
	private void spyj(BizData rd, BizData sd, String mklb, Connection conn) throws SQLException {
		String groupID = rd.getStringByDI("TA_DJ_GROUP", "ID", 0);// 获取查询条件:团号
		String spyj="select t.spyj,u.username from ta_dj_tspb t,drmuser u where t.spr=u.userno and t.mklb='"+mklb+"' and t.tid='"+groupID+"' and t.orgid='"+sd.getString("orgid")+"'";
		
		coreDAO.executeQuery(spyj, "SPYJ", rd, conn);
	}
	
	/**
	 * 查询所有报账费用情况
	 * 
	 * @param rd 请求数据对象
	 * @param sd Session数据对象
	 * @return 结果码
	 */
	public int queryAllBxState(BizData rd, BizData sd)
	{
		rd.add("md",rd.getString("md"));//模块index
		rd.add("mdName", rd.getString("mdName"));//模块id名称
		rd.add("mdUrl", rd.getString("mdUrl").replace(",", "&"));//模块URL
		
		Connection conn = coreDAO.getConnection();
		
		String sql ="select distinct gr.id,gr.state,gr.gw,gr.jd,a.hoteltid,b.cttid,c.cltid,d.djtid,e.dytid,f.gwtid,g.jdtid,h.jiadtid,i.pwtid,j.qttid \n" +
					" from \n" +
					"(select tid hoteltid,orgid from ta_dj_bxhotel) a, \n" +
					"(select tid cttid,orgid from ta_dj_bxct) b, \n" +
					"(select tid cltid,orgid from ta_dj_bxcl) c, \n" +
					"(select tid djtid,orgid from ta_dj_bxdj) d, \n" +
					"(select tid dytid,orgid from ta_dj_bxdy) e, \n" +
					"(select tid gwtid,orgid from ta_dj_bxgw) f, \n" +
					"(select tid jdtid,orgid from ta_dj_bxjd) g, \n" +
					"(select tid jiadtid,orgid from ta_dj_bxjiadian) h, \n" + 
					"(select tid pwtid,orgid from ta_dj_bxpw) i, \n" +
					"(select tid qttid,orgid from ta_dj_bxqt) j, \n" +
					"ta_dj_group gr \n" +
					"where gr.orgid=" + sd.getString("orgid") + "\n" +
					"and gr.id=a.hoteltid(+) and gr.orgid=a.orgid(+)\n" + 
					"and gr.id=b.cttid(+) and gr.orgid=b.orgid(+)\n" +
					"and gr.id=c.cltid(+) and gr.orgid=c.orgid(+)\n" +
					"and gr.id=d.djtid(+) and gr.orgid=d.orgid(+)\n" +
					"and gr.id=e.dytid(+) and gr.orgid=e.orgid(+)\n" +
					"and gr.id=f.gwtid(+) and gr.orgid=f.orgid(+)\n" +
					"and gr.id=g.jdtid(+) and gr.orgid=g.orgid(+)\n" +
					"and gr.id=h.jiadtid(+) and gr.orgid=h.orgid(+)\n" +
					"and gr.id=i.pwtid(+) and gr.orgid=i.orgid(+)\n" +
					"and gr.id=j.qttid(+) and gr.orgid=j.orgid(+)\n" +
					"and gr.id='"+rd.getString("TA_DJ_GROUP","ID",0)+"'"; 
			
		try
        {
			coreDAO.executeQuery(sql, "allState", rd, conn);
			// 酒店计划TA_DJ_JHHOTEL
			if(null!=rd.getString("allState","hoteltid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_BXHOTEL","JHHOTEL", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHHOTEL","JHHOTEL", sd.getString("orgid"),rd, conn);
			}
			
			// 餐厅安排TA_DJ_JHCT
			if(null!=rd.getString("allState","cttid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_BXCT","JHCT", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHCT","JHCT", sd.getString("orgid"),rd, conn);
			}
			// 票务安排 TA_DJ_JHPW
			if(null!=rd.getString("allState","pwtid",0)){
				queryState("sum(QD) QD,sum(XF) XF,sum(YGCB) YGCB","TA_DJ_BXPW","JHPW", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QD) QD,sum(XF) XF,sum(YGCB) YGCB","TA_DJ_JHPW","JHPW", sd.getString("orgid"),rd, conn);
			}
			// 车辆安排 TA_DJ_jHCL
			if(null!=rd.getString("allState","cltid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(JG) JG","TA_DJ_BXCL","JHCL", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(JG) JG","TA_DJ_JHCL","JHCL", sd.getString("orgid"),rd, conn);
			}
			// 景点安排TA_DJ_JHJD
			if(null!=rd.getString("allState","jdtid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(TGCB) TGCB","TA_DJ_BXJD","JHJD", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(TGCB) TGCB","TA_DJ_JHJD","JHJD", sd.getString("orgid"),rd, conn);
			}
			// 地接安排TA_DJ_JHDJ
			if(null!=rd.getString("allState","djtid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YFZK) YFZK","TA_DJ_BXDJ","JHDJ", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YFZK) YFZK","TA_DJ_JHDJ","JHDJ", sd.getString("orgid"),rd, conn);
			}
			// 购物安排TA_DJ_JHGW
			if(null!=rd.getString("allState","gwtid",0)){
				queryState("sum(JDRS) JDRS,sum(XFE) XFE,sum(YJGS) YJGS,sum(RTF) RTF,sum(GSLC) GSLC","TA_DJ_BXGW","JHGW", sd.getString("orgid"),rd, conn);
			}else{
				queryState("count(id)","TA_DJ_JHGW","JHGW", sd.getString("orgid"),rd, conn);
			}
			// 加点安排TA_DJ_JHJIAD
			if(null!=rd.getString("allState","jiadtid",0)){
				queryState("sum(CBJE) CBJE,sum(CBQD) CBQD,sum(CBXF) CBXF,sum(RS) RS,sum(LR) LR,sum(YJGS) YJGS,sum(RTF) RTF","TA_DJ_BXJIADIAN","JHJIAD", sd.getString("orgid"),rd, conn);
			}else{
				queryState("count(id)","TA_DJ_JHJIAD","JHJIAD", sd.getString("orgid"),rd, conn);
			}
			// 其他安排TA_DJ_JHQT
			if(null!=rd.getString("allState","qttid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_BXQT","JHQT", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHQT","JHQT", sd.getString("orgid"),rd, conn);
			}
			// 导游安排TA_DJ_JHDY
			if(null!=rd.getString("allState","dytid",0)){
				queryState("sum(DYLK) DYLK,sum(DFF) DFF","TA_DJ_BXDY","JHDY", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(DYLK) DYLK,sum(DFF) DFF","TA_DJ_JHDY","JHDY", sd.getString("orgid"),rd, conn);
			}
        }
        catch (Exception e)
        {
	        // 打印错误日志
        	log.error("查询失败", e);
        	
        	return SysError.DB_ERROR;
        }finally {
        	try{
        		if(conn != null){
        			conn.close();
        			conn = null;
        		}
        	}catch(SQLException e){
        		log.error("关闭conn失败：", e);
        		return SysError.DB_ERROR;
        	}
        }
        
		return 999;
	}
	/**
	 * 查询所有报账费用情况
	 * 
	 * @param rd 请求数据对象
	 * @param sd Session数据对象
	 * @return 结果码
	 */
	public void queryAllBxState(BizData rd, BizData sd, Connection conn)
	{
		rd.add("md",rd.getString("md"));//模块index
		rd.add("mdName", rd.getString("mdName"));//模块id名称
		rd.add("mdUrl", rd.getString("mdUrl").replace(",", "&"));//模块URL
		
		String sql ="select distinct gr.id,gr.state,gr.gw,gr.jd,a.hoteltid,b.cttid,c.cltid,d.djtid,e.dytid,f.gwtid,g.jdtid,h.jiadtid,i.pwtid,j.qttid \n" +
					" from \n" +
					"(select tid hoteltid,orgid from ta_dj_bxhotel) a, \n" +
					"(select tid cttid,orgid from ta_dj_bxct) b, \n" +
					"(select tid cltid,orgid from ta_dj_bxcl) c, \n" +
					"(select tid djtid,orgid from ta_dj_bxdj) d, \n" +
					"(select tid dytid,orgid from ta_dj_bxdy) e, \n" +
					"(select tid gwtid,orgid from ta_dj_bxgw) f, \n" +
					"(select tid jdtid,orgid from ta_dj_bxjd) g, \n" +
					"(select tid jiadtid,orgid from ta_dj_bxjiadian) h, \n" + 
					"(select tid pwtid,orgid from ta_dj_bxpw) i, \n" +
					"(select tid qttid,orgid from ta_dj_bxqt) j, \n" +
					"ta_dj_group gr \n" +
					"where gr.orgid=" + sd.getString("orgid") + "\n" +
					"and gr.id=a.hoteltid(+) and gr.orgid=a.orgid(+)\n" + 
					"and gr.id=b.cttid(+) and gr.orgid=b.orgid(+)\n" +
					"and gr.id=c.cltid(+) and gr.orgid=c.orgid(+)\n" +
					"and gr.id=d.djtid(+) and gr.orgid=d.orgid(+)\n" +
					"and gr.id=e.dytid(+) and gr.orgid=e.orgid(+)\n" +
					"and gr.id=f.gwtid(+) and gr.orgid=f.orgid(+)\n" +
					"and gr.id=g.jdtid(+) and gr.orgid=g.orgid(+)\n" +
					"and gr.id=h.jiadtid(+) and gr.orgid=h.orgid(+)\n" +
					"and gr.id=i.pwtid(+) and gr.orgid=i.orgid(+)\n" +
					"and gr.id=j.qttid(+) and gr.orgid=j.orgid(+)\n" +
					"and gr.id='"+rd.getString("TA_DJ_GROUP","ID",0)+"'"; 
			
		try
        {
			coreDAO.executeQuery(sql, "allState", rd, conn);
			// 酒店计划TA_DJ_JHHOTEL
			if(null!=rd.getString("allState","hoteltid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_BXHOTEL","JHHOTEL", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHHOTEL","JHHOTEL", sd.getString("orgid"),rd, conn);
			}
			
			// 餐厅安排TA_DJ_JHCT
			if(null!=rd.getString("allState","cttid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_BXCT","JHCT", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHCT","JHCT", sd.getString("orgid"),rd, conn);
			}
			// 票务安排 TA_DJ_JHPW
			if(null!=rd.getString("allState","pwtid",0)){
				queryState("sum(QD) QD,sum(XF) XF,sum(YGCB) YGCB","TA_DJ_BXPW","JHPW", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QD) QD,sum(XF) XF,sum(YGCB) YGCB","TA_DJ_JHPW","JHPW", sd.getString("orgid"),rd, conn);
			}
			// 车辆安排 TA_DJ_jHCL
			if(null!=rd.getString("allState","cltid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(JG) JG","TA_DJ_BXCL","JHCL", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(JG) JG","TA_DJ_JHCL","JHCL", sd.getString("orgid"),rd, conn);
			}
			// 景点安排TA_DJ_JHJD
			if(null!=rd.getString("allState","jdtid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(TGCB) TGCB","TA_DJ_BXJD","JHJD", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(TGCB) TGCB","TA_DJ_JHJD","JHJD", sd.getString("orgid"),rd, conn);
			}
			// 地接安排TA_DJ_JHDJ
			if(null!=rd.getString("allState","djtid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YFZK) YFZK","TA_DJ_BXDJ","JHDJ", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YFZK) YFZK","TA_DJ_JHDJ","JHDJ", sd.getString("orgid"),rd, conn);
			}
			// 购物安排TA_DJ_JHGW
			if(null!=rd.getString("allState","gwtid",0)){
				queryState("sum(JDRS) JDRS,sum(XFE) XFE,sum(YJGS) YJGS,sum(RTF) RTF,sum(GSLC) GSLC","TA_DJ_BXGW","JHGW", sd.getString("orgid"),rd, conn);
			}else{
				queryState("count(id)","TA_DJ_JHGW","JHGW", sd.getString("orgid"),rd, conn);
			}
			// 加点安排TA_DJ_JHJIAD
			if(null!=rd.getString("allState","jiadtid",0)){
				queryState("sum(CBJE) CBJE,sum(CBQD) CBQD,sum(CBXF) CBXF,sum(RS) RS,sum(LR) LR,sum(YJGS) YJGS,sum(RTF) RTF","TA_DJ_BXJIADIAN","JHJIAD", sd.getString("orgid"),rd, conn);
			}else{
				queryState("count(id)","TA_DJ_JHJIAD","JHJIAD", sd.getString("orgid"),rd, conn);
			}
			// 其他安排TA_DJ_JHQT
			if(null!=rd.getString("allState","qttid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_BXQT","JHQT", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHQT","JHQT", sd.getString("orgid"),rd, conn);
			}
			// 导游安排TA_DJ_JHDY
			if(null!=rd.getString("allState","dytid",0)){
				queryState("sum(DYLK) DYLK,sum(DFF) DFF","TA_DJ_BXDY","JHDY", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(DYLK) DYLK,sum(DFF) DFF","TA_DJ_JHDY","JHDY", sd.getString("orgid"),rd, conn);
			}
        }
        catch (Exception e)
        {
	        // 打印错误日志
        	log.error("查询失败", e);
        	
        }
	}
}
