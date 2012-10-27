package com.trm.sellerMng;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
  * @ClassName: SellerMngBLC
  * @Description: TODO 组团 - 订单 - 管理类
  * @author KingStong - likai
  * @date 2012-4-16 上午6:08:06
  *
  */
public class SellerMngBLC extends DBBLC {

	private static final String a = "abc";
	private static final String f = "abc";
	private Logger log;
	public SellerMngBLC(){
		log = Logger.getLogger(SellerMngBLC.class);
		this.entityName = "TA_ZT_YKORDER";		
	}
	
	/**
	  * @Title: listAllLines
	  * @Description: TODO 根据条件查询所有可报名的线路
	  * @param @param rd
	  * @param @param sd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	  */
	public int listAllLines(BizData rd,BizData sd){

		String lineName = rd.getString("lineName");
		String bDate = rd.getString("bDate");
		String days = rd.getString("days");
		String pageNOStr = rd.getString("pageNO");
		String pageSizeStr = rd.getString("pageSize");
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		//查询基础信息
		String sql = "select * from (select b.line_id,b.line_name,b.planoflinedate,b.orgid,b.cmpny_name,b.maxpersoncount \n" +
				",b.maxpersoncount-nvl(sum(o.ysrs),0) spare,b.state,b.id,b.ftzt \n" +
				"from ( \n" +
				"select a.line_id,g.xlmc line_name,a.planoflinedate,g.orgid,g.szlxsmc cmpny_name, \n" +
				"g.maxpersoncount,g.id,g.state,g.FTZT \n" +
				"from ( \n" +
				"select min(x.begin_date) planoflinedate,x.line_id from ( \n" +
				"select g.begin_date,g.line_id,g.ftzt,g.state\n" +
				"from ta_zt_group g \n" +
				"where g.ftzt=1 and g.orgid='"+sd.getString("orgid")+"' \n" +
				"and g.begin_date >= to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') \n" +
				") x \n" +
				"group by x.line_id \n" +
				") a,ta_zt_group g \n" +
				"where a.line_id=g.line_id and a.planoflinedate=g.begin_date and g.orgid='"+sd.getString("orgid")+"' \n";
		if(!"".equals(days)){
			sql += "and g.days="+days+" \n";
		}
		if(!"".equals(bDate)){
			sql += "and a.planoflinedate=to_date('"+bDate+"','yyyy-mm-dd') \n";
		}
		if(!"".equals(lineName))
			sql += "and g.xlmc like '%"+lineName+"%'";
			sql += ") b, (select yk.tid,yk.ysrs from ta_zt_ykorder yk where dd_confirm<>2) o \n" +
				",(select sum(t.price_ms) msj,t.g_id \n" +
				"from ta_zt_gprice t where t.orgid='"+sd.getString("orgid")+"' \n" +
				"group by t.g_id) c \n" +
				"where b.id=o.tid(+)  and b.id=c.g_id \n" +
				"and c.msj<>0 \n" +
				"group by b.line_id,b.line_name,b.planoflinedate,b.orgid,b.cmpny_name,b.maxpersoncount,b.state,b.id,b.ftzt,c.msj) \n";
		
		try {
			coreDAO.executeQueryPage(sql, "rsLineBase", pageNO, pageSize, rd);
			
			String sql2 = "select n.* from (\n"+sql+") m,(\n"+
				"select g.line_id,g.begin_date planoflinedate,gp.price_type,gp.price_th,d.dmsm1 thj,gp.price_ms,d.dmsm1 msj \n" +
					"from ta_zt_gprice gp,ta_zt_group g,dmsm d  \n" +
					"where gp.g_id=g.id and gp.price_type=d.dmz and d.dmlb=4 and g.orgid='"+sd.getString("orgid")+"' \n" +
					") n\n" +
					"where m.line_id=n.line_id and m.planoflinedate=n.planoflinedate \n";
			coreDAO.executeQuery(sql2, "rsLinePrice", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		rd.add("days", days);
		rd.add("action", rd.getString("action"));
		return 909;
	}
	
	/**
	  * @Title: queryByLineID
	  * @Description: TODO 根据条件查询发班计划
	  * @param @param rd
	  * @param @param sd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	  */
	public int queryByLineID(BizData rd, BizData sd) {
		
		rd.add("action", rd.getString("action"));
		String bDate = rd.getString("bDate");
		String tid = rd.getString("gID");
		try {
			String lineID = rd.getString("lineID");
			queryLine4Order("", tid, lineID, bDate, "new", rd, sd.getString("orgid"));
			//查询是否做了车辆计划
			//querySpareSeatNum(rd, tid);
			//线路价格
			queryPrice4OrderInit(bDate, lineID, rd, sd.getString("orgid"));
			return 98;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -90;
	}
	
	/**
	  * @Title: queryPrice4OrderInit
	  * @Description: TODO 根据线路ID,开始时间,组织机构,查询该线路下团的团价格
	  * @param @param bDate
	  * @param @param lineID
	  * @param @param rd
	  * @param @param orgid
	  * @param @throws SQLException    设定文件
	  * @return void    返回类型
	  * @throws
	  */
	private void queryPrice4OrderInit(String bDate,String lineID,BizData rd, String orgid) throws SQLException{
		
		String sql = "select m.line_id,t.price_type,t.price_ms,t.price_th,t.price_zd,t.remark,nvl(t.single_room,'0') single_room,b.ID \n" +
			"from TA_ZT_LINEMNG m,TA_ZT_GPRICE t,TA_ZT_GROUP b  \n" +
			"where b.id=t.g_id\n" +
			"and m.line_id=b.line_id and b.orgid='"+orgid+"' \n" +
			"and m.LINE_STATE in('1','3') \n";
		if(!"".equals(bDate)){
			sql += "and b.begin_date=to_date('"+bDate+"','yyyy-mm-dd') \n";
		}
			sql += "and m.line_id="+lineID;
		coreDAO.executeQuery(sql, "rsPrices", rd);
	}
	
	public int initSignUp(BizData rd,BizData sd) {
		try {
			String date=rd.getString("bDate");
			String lineid=rd.getString("lineid");
			//查询集合地点
			coreDAO.select(rd);
			//查询报名社信息
			String sql= "select t.*,o.name \n" +
						" from hrdepartment t,drmuser u,hrorganization o \n" +
						"where u.deptid=t.deptid \n" +
						"and u.orgid=t.orgid \n" +
						"and u.orgid=o.orgid \n" +
						"and u.userno='"+sd.getString("userno")+"' \n" +
						"and o.orgid='"+sd.getString("orgid")+"'";
			coreDAO.executeQuery(sql, "rsTraAgcBussInfo", rd);
			
			sql = "select t.* \n" +
						 " from drmuser u, hrorganization o,hrdepartment t \n" +
						 "where u.orgid=o.orgid \n" +
						 "and t.pdeptid=(select t.pdeptid \n" +
						 "from hrdepartment t,drmuser u \n" +
						 "where u.deptid=t.deptid \n" +
						 "and u.userno='"+sd.getString("userno")+"' \n" +
						 "and u.orgid='"+sd.getString("orgid")+"') \n" +
						 "and o.orgid='"+sd.getString("orgid")+"' \n" +
						 "and u.userno='"+sd.getString("userno")+"'";
			coreDAO.executeQuery(sql, "allDeptNames", rd);
			
			if("ztdt".equals(rd.getString("state"))){
				String sql2="select a.id,a.begin_date,a.begin_date+a.days end_date,a.line_id,a.gw,a.jd, \n"+
				"a.tourist_type,a.vehicle_type,a.user_no,a.otherside_gc,a.leader_name, \n"+
				"a.leader_tel,a.adult_count,a.children_count,a.days,a.comments,a.state,a.flow_state \n"+
				" from TA_ZT_GROUP a where a.line_id="+lineid+" and a.begin_date=to_date('"+date+"','yyyy-mm-dd') and a.state=1 and a.orgid='"+sd.getString("orgid")+"'";
				coreDAO.executeQuery(sql2,"groupInfo",rd);
				
				String id=rd.getStringByDI("groupInfo", "id", 0);
				String sql3="select a.rt,b.cr,rt+cr zrs from (select count(b.id) rt from TA_ZT_YKORDER a,TA_ZT_VISITOR b "+
					"where a.id=b.m_id and b.ischild=1 and a.dd_confirm=0 and a.id='"+id+"' and a.orgid='"+sd.getString("orgid")+"') a,"+
					"(select count(b.id) cr from TA_ZT_YKORDER a,TA_ZT_VISITOR b "+
					"where a.id=b.m_id and b.ischild=2 and a.dd_confirm=1 and a.id='"+id+"' and a.orgid='"+sd.getString("orgid")+"') b";
				coreDAO.executeQuery(sql3, "pesNum", rd);
				if(rd.getTableRowsCount("groupInfo")==0){
					int fk_id=this.queryMaxIDByPara("TA_ZT_GROUP", "ID", null);
					rd.add("groupInfo", "ID",fk_id);
				}
				String sql4="select a.line_price_id,a.line_id,b.dmsm1 price_type,a.price_ms,a.price_th," +
						"a.price_zd,a.REMARK from TA_ZT_LINE_PRICES a,dmsm b where a.price_type=b.dmz and b.dmlb=4 and a.orgid='"+sd.getString("orgid")+"'" +
						"and a.line_id="+lineid;
				coreDAO.executeQuery(sql4, "linePrice", rd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 109;
	}
	 /**
	  * 保存游客报名信息
	  */
	public int insert(BizData rd,BizData sd) {
		
		String g_id = rd.getString("groupid");
		rd.add("groupid", g_id);
		rd.add("state", rd.getString("state"));
		Connection conn = coreDAO.getConnection();
		synchronized(f){
			try {
				List<String> seatsList = new ArrayList<String>();
				//查询订单号
				String bDate = rd.getString("bDate");
				bDate = bDate.replaceAll("-", "");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				bDate = sdf.format(sdf.parse(bDate));
				//订单号
				String gMoneyID = "";
				int gID = queryMaxIDByPara("TA_ZT_YKORDER", "substr(id,11,4)", "substr(id,3,8) = '"+sdf.format(sdf.parse(bDate))+"'");
				if(gID < 10) {
					gMoneyID = "HJ"+sdf.format(sdf.parse(bDate))+"1001";
					clearCacheObj("TA_ZT_YKORDER", "substr(id,11,4)");
				}
				else
					gMoneyID = "HJ"+sdf.format(sdf.parse(bDate))+gID;
				
				coreDAO.beginTrasct(conn);
				//插入实际团订单价格表人数
				insertGOrderPrice(rd, conn, gMoneyID, sd.getString("orgid"));
				
				//取座位号
				canUsedTheSeat(rd, g_id, seatsList, sd.getString("orgid"));
				//插入保险表
				insertInsurance(rd, gMoneyID, conn, sd.getString("orgid"));
				//报名人员信息表
				insertVisitor(rd, conn, gMoneyID, seatsList, sd.getString("orgid"));
				//添加订单表
				insertOrderInfo(rd, sd, conn, gMoneyID, sd.getString("orgid"));
				coreDAO.commitTrasct(conn);
				rd.add("ddh", gMoneyID);
			} catch (SQLException e) {
				try {
					coreDAO.rollbackTrasct(conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (ParseException e) {
				try {
					coreDAO.rollbackTrasct(conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} finally {
				try{
					if(null != conn){
						conn.close();
						conn = null;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 309;
	}
	
	/**
	 * 插入实际团订单价格表人数
	 * @param rd
	 * @param conn
	 * @param orderID
	 * @param isDoVehPlan
	 * @param tid
	 * @param seatsList
	 * @throws SQLException
	 */
	private void insertGOrderPrice(BizData rd, Connection conn, String orderID, String orgid) throws SQLException{
		
		String[] rowIDs = rd.getRowIDs("TA_ZT_GORDERPRICE");
		for(int i=0;i<rowIDs.length;i++) {
			
			int priceID = queryMaxIDByPara("TA_ZT_GORDERPRICE", "ID", null);
			rd.add("TA_ZT_GORDERPRICE", "ID", rowIDs[i], priceID);
			rd.add("TA_ZT_GORDERPRICE", "ORDERID", rowIDs[i], orderID);
			//人数
			String PERSON_COUNT = rd.getString("TA_ZT_GORDERPRICE", "PERSON_COUNT", rowIDs[i]).equals("")?"0":rd.getString("TA_ZT_GORDERPRICE", "PERSON_COUNT", rowIDs[i]);
			rd.add("TA_ZT_GORDERPRICE", "PERSON_COUNT", rowIDs[i], PERSON_COUNT);
			//单房差房间个数
			String singleRooms = rd.getString("TA_ZT_GORDERPRICE", "SINGLE_ROOMS", rowIDs[i]);
			if("".equals(singleRooms))
				rd.add("TA_ZT_GORDERPRICE", "SINGLE_ROOMS", rowIDs[i], "0");
			rd.add("TA_ZT_GORDERPRICE","orgid", orgid);
		}
		coreDAO.insert("TA_ZT_GORDERPRICE", rd, conn);
		rd.remove("TA_ZT_GORDERPRICE");
	}
	
	/**
	 * 添加保险信息
	 * @param rd
	 * @param tid
	 * @param conn
	 * @throws SQLException
	 */
	private void insertInsurance(BizData rd,String orderID,Connection conn, String orgid) throws SQLException{
		String[] rowIDs = rd.getRowIDs("TA_ZT_GINSURANCE");
		for(int i=0;i<rowIDs.length;i++){
			
			int insuranceID = queryMaxIDByPara("TA_ZT_GINSURANCE", "ID", null);
			rd.add("TA_ZT_GINSURANCE", "ID", rowIDs[i], insuranceID);
			rd.add("TA_ZT_GINSURANCE", "ORDERID", rowIDs[i], orderID);
			//保险
			String insuranceP = rd.getString("TA_ZT_GINSURANCE", "INSURANCEPRICE", rowIDs[i]);
			if("".equals(insuranceP))
				rd.add("TA_ZT_GINSURANCE", "INSURANCEPRICE", rowIDs[i], "0");
			//保险成本
			String insuranceCost = rd.getString("TA_ZT_GINSURANCE", "INSURANCECOST", rowIDs[i]);
			if("".equals(insuranceCost))
				rd.add("TA_ZT_GINSURANCE", "INSURANCECOST", rowIDs[i], "0");
			//人数
			String persons = rd.getString("TA_ZT_GINSURANCE", "PERSONS", rowIDs[i]);
			if("".equals(persons))
				rd.add("TA_ZT_GINSURANCE", "PERSONS", rowIDs[i], "0");
			rd.add("TA_ZT_GINSURANCE","orgid", rowIDs[i], orgid);
		}
		coreDAO.insert("TA_ZT_GINSURANCE", rd, conn);
		rd.remove("TA_ZT_GINSURANCE");
	}
	/**
	 * 添加游客信息
	 * @param rd
	 * @param conn
	 * @param orderID
	 * @param seatsList
	 * @throws SQLException
	 */
	private void insertVisitor(BizData rd,Connection conn, String orderID, List<String> seatsList, String orgid) throws SQLException{
		
		String[] rowIDs = rd.getRowIDs("TA_ZT_VISITOR");
		
		for(int i=0;i<rowIDs.length;i++) {
			
			int visitorID = queryMaxIDByPara("TA_ZT_VISITOR", "ID", null);
			String isChild = rd.getString("TA_ZT_VISITOR", "ISCHILD", rowIDs[i]);//是否儿童
			if("".equals(isChild)){
				rd.add("TA_ZT_VISITOR", "ISCHILD", rowIDs[i], "2");//成人
			}
			String ISINSURANCE = rd.getString("TA_ZT_VISITOR", "ISINSURANCE", rowIDs[i]);//是否需要保险
			if("".equals(ISINSURANCE))
				rd.add("TA_ZT_VISITOR", "ISINSURANCE", rowIDs[i], "0");
			String ISLEADER = rd.getString("TA_ZT_VISITOR", "ISLEADER", rowIDs[i]);//是否需要保险
			if("".equals(ISLEADER))
				rd.add("TA_ZT_VISITOR", "ISLEADER", rowIDs[i], "0");
			rd.add("TA_ZT_VISITOR", "ID", rowIDs[i], visitorID);
			rd.add("TA_ZT_VISITOR", "M_ID", rowIDs[i], orderID);
			
			String seat = seatsList.get(i);
			rd.add("TA_ZT_VISITOR", "SEAT_NUM", rowIDs[i], seat);
			rd.add("TA_ZT_VISITOR", "orgid", rowIDs[i], orgid);
		}
		coreDAO.insert("TA_ZT_VISITOR", rd, conn);
	}
	
	/**
	 * 添加订单信息
	 * @param rd
	 * @param sd
	 * @param conn
	 * @param orderNO
	 * @throws SQLException
	 */
	private void insertOrderInfo(BizData rd,BizData sd,Connection conn, String orderNO, String orgid) throws SQLException{

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] rowIDs = rd.getRowIDs("TA_ZT_VISITOR");
		String orgName1 = rd.getString("CMPNY_NAME");
		String orgName2 = rd.getString("CMPNY_NAME1");
		
		int ysrs = rowIDs.length;//报名人数
		String isConfirm = rd.getStringByDI("TA_ZT_YKORDER", "ISCONFIRM", 0);
		if("".equals(isConfirm)) {
			isConfirm = "0";
			rd.add("TA_ZT_YKORDER", "ISCONFIRM", 0, isConfirm);//是否确认收款
		}
		
		rd.add("TA_ZT_YKORDER", "dd_confirm", 0, "0");//订单状态：待确认
		rd.add("TA_ZT_YKORDER", "ysrs", 0, ysrs);
		rd.add("TA_ZT_YKORDER", "ORGID", 0, sd.getString("orgid"));
		rd.add("TA_ZT_YKORDER", "ID", 0, orderNO);
		rd.add("TA_ZT_YKORDER", "CMPNY_NAME", 0, orgName1+orgName2);
		rd.add("TA_ZT_YKORDER", "CREATER", 0, sd.getString("userno"));
		rd.add("TA_ZT_YKORDER", "REGEDIT_TIME", 0, sdf2.format(new Date()));
		coreDAO.insert("TA_ZT_YKORDER", rd, conn);
		rd.remove("TA_ZT_YKORDER");
	}
	
	/**
	 * 根据车辆ID和团ID查询该团队还有哪些座位可以使用
	 * @param tid
	 * @throws SQLException 
	 */
	private void canUsedTheSeat(BizData rd, String tid,List<String> seatsList,String orgid) throws SQLException {
		String sql = "select n.seatnum from ta_seatnum n where n.seatnum not in (\n" +
				"select nvl(v.seat_num,0) seatnum\n" +
				"from ta_zt_visitor v,ta_zt_ykorder o\n" +
				"where v.m_id=o.id\n" +
				"and o.dd_confirm<>2\n" +
				"and o.tid='"+tid+"' and o.orgid='"+orgid+"' \n" +
				") order by n.seatnum";
		coreDAO.executeQuery(sql, "canUsedZWH", rd);
		for(int i=0;i<rd.getTableRowsCount("canUsedZWH");i++)
			seatsList.add(rd.getStringByDI("canUsedZWH", "seatnum", i));
		rd.remove("canUsedZWH");
	}
	
	/**
	 * 我的订单
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int signUpOfLines(BizData rd,BizData sd) {
		
		String pageNOStr = rd.getString("pageNO");
		String pageSizeStr = rd.getString("pageSize");
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		//各线路所有报名的订单号及相关信息
		String sql = "select m.id ddh,b.id th,b.begin_date,b.days,d.dmsm1 ddconfirm,d.dmz,nvl(m.yi_sk,0) yi_sk,nvl(m.yin_sk,0) yin_sk, \n" +
				"m.yin_sk-m.yi_sk spare,b.xlmc line_name,b.line_id,m.ysrs,m.DD_CONFIRM,du.orgid,v.visitor_nm,v.tel \n" +
				" from TA_ZT_YKORDER m,TA_ZT_GROUP b,dmsm d,DRMUSER du ,(select *  from TA_ZT_VISITOR where isleader='1' ) v  \n" +
				"where m.TID=b.id and m.dd_confirm=d.dmz and d.dmlb=11 and m.id=v.m_id \n";
		sql += "and m.creater=du.userno  and m.creater='"+sd.getString("userno")+"' \n";
			sql += "and du.orgid = '"+sd.getString("orgid")+"' \n";
		if(!"".equals(rd.getString("ddState")))
			sql += "and m.dd_confirm="+rd.getString("ddState")+" \n";
		if(!"".equals(rd.getString("lineName")))
			sql += "and b.xlmc like '%"+rd.getString("lineName")+"%' \n";
		if(!"".equals(rd.getString("groupID")))
			sql += "and b.id like '%"+rd.getString("groupID")+"%' \n";
		if(!"".equals(rd.getString("bDate")) && !"".equals(rd.getString("eDate")))
			sql += "and b.begin_date >= to_date('"+rd.getString("bDate")+"','yyyy-mm-dd') and b.begin_date <= to_date('"+rd.getString("eDate")+"','yyyy-mm-dd') \n";
		if(!"".equals(rd.getString("djh")))
			sql += "and m.id like '%"+rd.getString("djh")+"%' \n";
		if(!"".equals(rd.getString("ddConfirm"))){
			sql += "and m.ddconfirm='"+rd.getString("ddConfirm")+"' \n";
		}
		if(!"".equals(rd.getString("dzzt"))){
			sql += "and m.dzzt='"+rd.getString("dzzt")+"' \n";
		}
		sql+=" order by m.id desc";
		try {
			coreDAO.executeQueryPage(sql, "rsAllDDH", pageNO, pageSize, rd);
			sql = "select m.id,d.dmsm1,p.price_ms,p.price_ms*nvl(p.person_count,0) ms,p.price_th,p.price_th*nvl(p.person_count,0) th,nvl(p.person_count,0) person_count,du.orgid \n" +
					"from TA_ZT_YKORDER m,TA_ZT_GROUP b,TA_ZT_GORDERPRICE p,dmsm d,DRMUSER du \n" +
					"where m.id=p.orderid and p.price_type=d.dmz and d.dmlb=4 \n" +
					"and m.tid=b.id \n" +
					//"and b.begin_date >= to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') \n" +
					"and m.creater=du.userno and m.creater = '"+sd.getString("userno")+"' and du.orgid = '"+sd.getString("orgid")+"' \n";
			if(!"".equals(rd.getString("lineName")))
				sql += "and b.xlmc like '%"+rd.getString("lineName")+"%' \n";
			if(!"".equals(rd.getString("groupID")))
				sql += "and b.id like '%"+rd.getString("groupID")+"%' \n";
			if(!"".equals(rd.getString("bDate")) && !"".equals(rd.getString("eDate")))
				sql += "and b.begin_date >= to_date('"+rd.getString("bDate")+"','yyyy-mm-dd') and b.begin_date <= to_date('"+rd.getString("eDate")+"','yyyy-mm-dd') \n";
			if(!"".equals(rd.getString("djh")))
				sql += "and m.id like '%"+rd.getString("djh")+"%' \n";
			if(!"".equals(rd.getString("ddConfirm")))
				sql += "and m.isconfirm='"+rd.getString("ddConfirm")+"' \n";
			if(!"".equals(rd.getString("dzzt")))
				sql += "and m.dzzt='"+rd.getString("dzzt")+"' \n";
			coreDAO.executeQuery(sql, "rsPriceOfAllDDH", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 82;
	}
	
	/**
	 * 订单确认--查询所有订单信息
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int queryAllOrderNOs(BizData rd,BizData sd){
		
		String pageNOStr = rd.getString("pageNO");
		String pageSizeStr = rd.getString("pageSize");
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		//订单信息
		String sql = "select m.id ddh,b.id th,b.begin_date,b.days,d.dmsm1 ddconfirm,d.dmz,nvl(m.yi_sk,0) yi_sk,nvl(m.yin_sk,0) yin_sk, \n" +
				"m.yin_sk-m.yi_sk spare,b.xlmc line_name,b.line_id,m.ysrs,m.DD_CONFIRM,v.visitor_nm,v.tel,m.isconfirm \n" +
				" from TA_ZT_YKORDER m,TA_ZT_GROUP b,dmsm d,(select *  from TA_ZT_VISITOR where isleader='1' ) v  \n" +
				"where m.TID=b.id and m.dd_confirm=d.dmz and d.dmlb=11 and m.id=v.m_id\n";
		if(!"".equals(rd.getString("ddState")))
			sql += "and m.dd_confirm="+rd.getString("ddState")+" \n";
		if(!"".equals(rd.getString("lineName")))
			sql += "and b.xlmc like '%"+rd.getString("lineName")+"%' \n";
		if(!"".equals(rd.getString("groupID")))
			sql += "and b.id like '%"+rd.getString("groupID")+"%' \n";
		if(!"".equals(rd.getString("bDate")) && !"".equals(rd.getString("eDate")))
			sql += "and b.begin_date >= to_date('"+rd.getString("bDate")+"','yyyy-mm-dd') and b.begin_date <= to_date('"+rd.getString("eDate")+"','yyyy-mm-dd') \n";
		if(!"".equals(rd.getString("djh")))
			sql += "and m.id like '%"+rd.getString("djh")+"%' \n";
		if(!"".equals(rd.getString("ddConfirm"))){
			sql += "and m.ddconfirm='"+rd.getString("ddConfirm")+"' \n";
		}
		if(!"".equals(rd.getString("dzzt"))){
			sql += "and m.dzzt='"+rd.getString("dzzt")+"' \n";
		}
		sql+=" order by m.id desc";
		try {
			coreDAO.executeQueryPage(sql, "rsAllDDH", pageNO, pageSize, rd);

			//订单价格信息
			sql = "select b.* from (\n"+
			"select m.id ddh,b.id th,b.begin_date,b.days,d.dmsm1 ddconfirm,d.dmz,nvl(m.yi_sk,0) yi_sk,nvl(m.yin_sk,0) yin_sk, \n" +
			"m.yin_sk-m.yi_sk spare,b.xlmc line_name,b.line_id,m.ysrs,m.DD_CONFIRM,v.visitor_nm,v.tel \n" +
			" from TA_ZT_YKORDER m,TA_ZT_GROUP b,dmsm d,(select *  from TA_ZT_VISITOR where isleader='1' ) v  \n" +
			"where m.TID=b.id and m.dd_confirm=d.dmz and d.dmlb=11 and m.id=v.m_id\n";
			if(!"".equals(rd.getString("ddState")))
				sql += "and m.dd_confirm="+rd.getString("ddState")+" \n";
			if(!"".equals(rd.getString("lineName")))
				sql += "and b.xlmc like '%"+rd.getString("lineName")+"%' \n";
			if(!"".equals(rd.getString("groupID")))
				sql += "and b.id like '%"+rd.getString("groupID")+"%' \n";
			if(!"".equals(rd.getString("bDate")) && !"".equals(rd.getString("eDate")))
				sql += "and b.begin_date >= to_date('"+rd.getString("bDate")+"','yyyy-mm-dd') and b.begin_date <= to_date('"+rd.getString("eDate")+"','yyyy-mm-dd') \n";
			if(!"".equals(rd.getString("djh")))
				sql += "and m.id like '%"+rd.getString("djh")+"%' \n";
			if(!"".equals(rd.getString("ddConfirm"))){
				sql += "and m.ddconfirm='"+rd.getString("ddConfirm")+"' \n";
			}
			if(!"".equals(rd.getString("dzzt"))){
				sql += "and m.dzzt='"+rd.getString("dzzt")+"' \n";
			}
			sql += ") a,(\n" +
					"select m.id,d.dmsm1,p.price_ms,p.price_ms*nvl(p.person_count,0) ms,p.price_th,p.price_th*nvl(p.person_count,0) th,nvl(p.person_count,0) person_count \n" +
					"from TA_ZT_YKORDER m,TA_ZT_GROUP b,TA_ZT_GORDERPRICE p,dmsm d\n" +
					"where m.id=p.orderid and p.price_type=d.dmz and d.dmlb=4\n" +
					"and m.tid=b.id \n";
			if(!"".equals(rd.getString("lineName")))
				sql += "and b.xlmc like '%"+rd.getString("lineName")+"%' \n";
			if(!"".equals(rd.getString("groupID")))
				sql += "and b.id like '%"+rd.getString("groupID")+"%' \n";
			if(!"".equals(rd.getString("bDate")) && !"".equals(rd.getString("eDate")))
				sql += "and b.begin_date >= to_date('"+rd.getString("bDate")+"','yyyy-mm-dd') and b.begin_date <= to_date('"+rd.getString("eDate")+"','yyyy-mm-dd') \n";
			if(!"".equals(rd.getString("djh")))
				sql += "and m.id like '%"+rd.getString("djh")+"%' \n";
			if(!"".equals(rd.getString("ddConfirm")))
				sql += "and m.isconfirm='"+rd.getString("ddConfirm")+"' \n";
			if(!"".equals(rd.getString("dzzt")))
				sql += "and m.dzzt='"+rd.getString("dzzt")+"' \n";
			sql += ") b\n" +
					"where a.ddh=b.id";
			coreDAO.executeQuery(sql, "rsPriceOfAllDDH", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 98;
	}
	
	public int queryByDDH(BizData rd,BizData sd) {
		
		
		return 248;
	}
	
	/**
	 * 查询未确认定单、未成功订单、已成功订单数
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int queryAllOrderState(BizData rd,BizData sd) {
		
		String sql = "select nvl(count(m.id),0) c,d.dmz \n" +
				"from (select id,dd_confirm from TA_ZT_YKORDER where creater="+sd.getString("userno")+") m,dmsm d \n" +
				"where m.dd_confirm(+) = d.dmz \n" +
				"and d.dmlb = 11 \n" +
				"group by d.dmz";
		try {
			coreDAO.executeQuery(sql, "allOrderState", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 473;
	}
	
	/**
	 * 根据订单号查询团、线路信息
	 * @param rd
	 * @param sd
	 * @return
	 */
	/*public int queryByDDHForAll(BizData rd,BizData sd) {
		
		//
		String sql = "select sum(m.ysrs) ysrs,l.line_name,b.id,b.days,b.begin_date \n"+
			",b.end_date,l.maxpersoncount,l.insurance,l.return_role, \n"+
			"l.b_traffic,l.e_traffic,u.username \n"+
			",o.name cmpny_name, m.business_mobile,m.business_fax,m.business_tel business_phone\n"+
			",d.dmsm1 priceType,p.price_ms,m.remark \n"+
			",gather.gather gather,m.gather_time,m.add_m_count \n"+
			"from TA_ZT_YKORDER m,TA_ZT_GROUP b,TA_ZT_LINEMNG l,hremployee e,hrorganization o,drmuser u,TA_ZT_GORDERPRICE p,dmsm d,ta_zt_gather gather \n"+
			"where m.tid=b.id and b.line_id=l.line_id and m.creater=u.userno \n"+
			"and u.empid=e.empid and e.orgid=o.orgid and p.price_type=d.dmz and d.dmlb=4 and gather.gather_id=m.gather\n"+
			"and m.id=p.ORDERID \n"+
			"and m.id='"+rd.getString("ddh")+"' \n"+
				"group by l.line_name,b.id,b.days,b.begin_date,b.end_date,l.maxpersoncount,l.insurance,l.return_role, \n"+
				"l.b_traffic,l.e_traffic,u.username,o.name,m.business_mobile,m.business_fax,m.business_tel,d.dmsm1, \n"+
				"p.price_ms,gather.gather,m.gather_time,m.add_m_count,m.remark";
		try {
			coreDAO.executeQuery(sql, "rsViewAllData", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 549;
	}*/
	
	/**
	 * 根据订单号查询线路明细(blob)
	 */
	public int queryLineDetail(BizData rd,BizData sd){
		
		String sql = "select l.* from TA_ZT_GROUP b,ta_zt_linedetai4g l where b.id=l.tid and b.id='"+rd.getString("tid")+"' order by l.rq"; // and rownum =1";
		try {
			coreDAO.executeQuery(sql, "rsLineDetail", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 398;
	}
	
	/**
	 * 线路订单列表中的查看
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int queryForView(BizData rd,BizData sd){
		
		String sql = "select sum(p.price_th*p.person_count) thj,sum(p.price_ms*p.person_count) msj,m.regedit_time,b.xlmc line_name, \n" +
				"b.begin_date,b.end_date,m.remark,v.visitor_nm,v.tel,m.ysrs,b.return_role,g.gather,g.gather_time,\n" +
				"u.username,m.id,b.b_traffic,b.e_traffic,m.cmpny_name,m.business_tel business_phone,m.business_mobile,m.business_fax,m.qq_msn business_qq,f.dmsm1 jtjt,g.dmsm1 stjt\n" +
				"from TA_ZT_YKORDER m,TA_ZT_GROUP b,TA_ZT_GORDERPRICE p,TA_ZT_VISITOR v,TA_ZT_GATHER g,hrorganization t,drmuser u,dmsm f,dmsm g\n" +
				"where m.tid=b.id and m.id=p.ORDERID and m.id=v.m_id and m.gather=g.gather_id(+) \n" +
				"and m.creater=u.userno and f.dmlb=2 and g.dmlb=2\n" +
				"and b.b_traffic=f.dmz(+) and b.e_traffic=g.dmz(+) and v.isleader=1 \n" +
				"and m.id='"+rd.getString("ddh")+"' \n" +
				"and m.orgid='"+sd.getString("orgid")+"' \n" +
				"group by m.regedit_time,b.xlmc,\n" +
				"b.begin_date,b.end_date,m.remark,v.visitor_nm,v.tel,m.ysrs,b.return_role,g.gather,g.gather_time, \n" +
				"u.username,m.id,b.b_traffic,b.e_traffic,m.cmpny_name,m.business_tel,m.business_mobile,m.business_fax,m.qq_msn,f.dmsm1,g.dmsm1";
		try {
			coreDAO.executeQuery(sql, "rsView", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 781;
	}
	
	/**
	 * 初始化修改订单页面
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int signUpEditIint(BizData rd,BizData sd) {
		
		String ddh = rd.getString("ddh");
		String groupKey = rd.getString("groupKey");
		try {
			queryLine4Order(ddh, groupKey, "", "", "edit", rd, sd.getString("orgid"));
			//订单价格
			queryOrderPrice4EditInit(ddh, rd, sd.getString("orgid"));
			//报名游客信息
			queryVisitor4EditInit(ddh, rd, sd.getString("orgid"));
			//保险
			queryInsurance4EditInit(ddh, rd, sd.getString("orgid"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 842;
	}
	
	/**
	  * @Title: queryVisitor4EditInit
	  * @Description: TODO 根据订单号 机构号 查询当前订单下的游客信息
	  * @param @param ddh
	  * @param @param rd
	  * @param @throws SQLException    设定文件
	  * @return void    返回类型
	  * @throws
	  */
	
	private void queryVisitor4EditInit(String ddh,BizData rd, String orgid) throws SQLException{
		
		//报名人员信息
		String sql = "select v.m_id,v.visitor_nm,v.sex,v.license_no,v.zjyxq,v.tel,v.ischild,v.isinsurance\n" +
				",v.isleader,v.address, v.PASSPORTPS, v.BORNSITE \n" +
				"from TA_ZT_VISITOR v \n" +
				"where v.m_id='"+ddh+"' and v.orgid='"+ orgid +"'";
		coreDAO.executeQuery(sql, "rsVisitor", rd);
	}
	
	/**
	 * 初始化订单中价格信息
	 * @param ddh
	 * @param rd
	 * @throws SQLException
	 */
	private void queryOrderPrice4EditInit(String ddh,BizData rd, String orgid) throws SQLException{
		
		//价格信息
		String sql = "select m.id,p.price_type,d.dmsm1 priceType,p.price_ms,p.price_th,p.price_zd,p.person_count\n" +
				",p.remark,p.single_room,p.single_rooms \n" +
				"from TA_ZT_YKORDER m,TA_ZT_GORDERPRICE p,dmsm d \n" +
				"where m.id=p.ORDERID and p.price_type=d.dmz and d.dmlb=4 \n" +
				"and m.id='"+ddh+"' and m.orgid='"+ orgid +"'";
		coreDAO.executeQuery(sql, "rsOrderPrice", rd);
	}
	
	/**
	 * 订单保险
	 * @param ddh
	 * @param rd
	 * @throws SQLException
	 */
	private void queryInsurance4EditInit(String ddh,BizData rd, String orgid) throws SQLException{
		String onInsuranceSql = "select s.orderid,s.insurancetype,s.insuranceprice,s.insurancecost,s.persons,c.cbfs,c.bxlbmc \n"+
								" from  ta_zt_ginsurance s,ta_zt_group g,ta_zt_linemng l,ta_zt_ykorder y,ta_zt_insurance c \n"+
								"where y.orgid='"+ orgid +"' \n"+
								"and g.line_id=l.line_id \n"+ 
								"and g.id=y.tid \n"+
								"and y.id=s.orderid \n"+
								"and l.line_id=(select l.line_id from  ta_zt_linemng l,ta_zt_group g,ta_zt_ykorder y \n"+
								"where y.orgid='"+ orgid +"' and l.line_id=g.line_id and g.id=y.tid and y.id='"+ddh+"') \n"+
								"and s.insurancetype=c.id \n"+ 
								"and y.id='"+ddh+"'";
		String offInsuranceSql = "select c.insuranceid,i.bxlbmc,i.bxcb,i.jyjg,i.cbfs \n"+ 
								 " from ta_zt_lineandinsurance c,ta_zt_insurance i \n"+ 
								 "where i.orgid='"+ orgid +"' \n"+ 
								 "and c.line_id=(select l.line_id from  ta_zt_linemng l,ta_zt_group g,ta_zt_ykorder y \n"+
								 "where y.orgid='"+ orgid +"' and l.line_id=g.line_id and g.id=y.tid and y.id='"+ddh+"') \n"+ 
								 "and c.insuranceid = i.id \n"+ 
								 "and c.insuranceid not in( \n"+ 
								 "select s.insurancetype \n"+ 
								 " from ta_zt_ginsurance s,ta_zt_group g,ta_zt_linemng l,ta_zt_ykorder y \n"+ 
								 "where y.orgid='"+ orgid +"' \n"+  
								 "and g.line_id=l.line_id \n"+  
								 "and g.id=y.tid \n"+  
								 "and y.id=s.orderid \n"+ 
								 "and l.line_id=(select l.line_id from  ta_zt_linemng l,ta_zt_group g,ta_zt_ykorder y \n"+
								 "where y.orgid='"+ orgid +"' and l.line_id=g.line_id and g.id=y.tid and y.id='"+ddh+"') \n"+
								 "and y.id='"+ddh+"')";
		coreDAO.executeQuery(onInsuranceSql, "rsOnInsurance", rd);
		coreDAO.executeQuery(offInsuranceSql, "rsOffInsurance", rd);
		int onRows = rd.getTableRowsCount("rsOnInsurance");
		int offRows = rd.getTableRowsCount("rsOffInsurance");
		for(int i = 0; i < onRows; i++){
			String bxId = rd.getStringByDI("rsOnInsurance", "insurancetype", i);
			String bxMc = rd.getStringByDI("rsOnInsurance", "bxlbmc", i);
			String bxJyjg = rd.getStringByDI("rsOnInsurance", "insuranceprice", i);
			String bxCb = rd.getStringByDI("rsOnInsurance", "insurancecost", i);
			String bxFs = rd.getStringByDI("rsOnInsurance", "cbfs", i);
			String bxRs = rd.getStringByDI("rsOnInsurance", "persons", i);
			rd.add("rsInsurance","bxId" ,i,bxId);
			rd.add("rsInsurance","bxMc" ,i,bxMc);
			rd.add("rsInsurance","bxJyjg" ,i,bxJyjg);
			rd.add("rsInsurance","bxCb" ,i,bxCb);
			rd.add("rsInsurance","bxFs" ,i,bxFs);
			rd.add("rsInsurance","bxRs" ,i,bxRs);
		}
		int RowsCounts= onRows+offRows;
		int iF = 0;
		for(int i = onRows; i < RowsCounts ; i++){
			String bxId = rd.getStringByDI("rsOffInsurance", "insuranceid", iF);
			String bxMc = rd.getStringByDI("rsOffInsurance", "bxlbmc", iF);
			String bxJyjg = rd.getStringByDI("rsOffInsurance", "jyjg", iF);
			String bxCb = rd.getStringByDI("rsOffInsurance", "bxcb", iF);
			String bxFs = rd.getStringByDI("rsOffInsurance", "cbfs", iF);
			String bxRs = "0";
			rd.add("rsInsurance","bxId" ,i,bxId);
			rd.add("rsInsurance","bxMc" ,i,bxMc);
			rd.add("rsInsurance","bxJyjg" ,i,bxJyjg);
			rd.add("rsInsurance","bxCb" ,i,bxCb);
			rd.add("rsInsurance","bxFs" ,i,bxFs);
			rd.add("rsInsurance","bxRs" ,i,bxRs);
			iF++;
		}
		String sql = "select * from ta_zt_insurance t";
		coreDAO.executeQuery(sql, "TA_ZT_INSURANCEs", rd);
	}
	
	/**
	 * 查询线路基本信息
	 * @param ddh
	 * @param groupID
	 * @param rd
	 * @throws SQLException
	 */
	private void queryLine4Order(String ddh,String groupID,String lineID,String bDate,String where, BizData rd, String orgid) throws SQLException{
		
		String sql = "";
		if("edit".equals(where)){
			//线路基本信息
			sql = "select a.ddh,a.id,a.line_id,a.line_name,a.days,a.maxpersoncount,a.minpersoncount,a.maxpersoncount - b.ysrs spare, \n" +
				"a.gather_id,a.gather_time,a.add_m_count,a.remark,a.QQ_MSN, \n" +
				"a.cmpny_name,a.business_name,a.business_tel,a.business_fax,a.business_mobile,a.rooms,a.yin_sk,a.yi_sk,a.isconfirm \n" +
				"from ( \n" +
				"select m.id ddh,b.id,b.days,b.line_id,b.xlmc line_name,b.maxpersoncount,b.minpersoncount,m.yin_sk,m.yi_sk,m.isconfirm, \n" +
				"g.gather_id,g.gather_time,g.add_m_count,m.QQ_MSN, \n" +
				"m.remark,m.cmpny_name,m.business_name,m.business_tel,m.business_fax,m.business_mobile,m.rooms \n" +
				"from TA_ZT_YKORDER m,TA_ZT_GROUP b,TA_ZT_GATHER g \n" +
				"where m.tid=b.id and m.gather=g.gather_id(+) and b.orgid='"+orgid+"' \n" +
				"and m.id='"+ddh+"' \n" +
				") a,( \n" +
				"select sum(m.ysrs) ysrs \n" +
				"from TA_ZT_YKORDER m \n" +
				"where m.tid='"+groupID+"' and m.orgid='"+orgid+"' \n" +
				"and m.dd_confirm<>2\n" +
				") b";
			coreDAO.executeQuery(sql, "rsOrderBase", rd);
		}else{

			//日期为空时，查询一条线路所有发班计划每天最多可报名人数和剩余报名人数
			//日期不为空时，查询该日期最大可报名人数和剩余可报名人数
			sql = "select a.line_id,a.planoflinedate,a.days,a.maxpersoncount,a.minpersoncount,a.maxPersonCount - nvl(sum(a.ysrs),0) spare\n" +
					",a.line_name,a.id groupID,a.FTZT,a.usertype\n"+
					"from ( \n" +
					"select b.begin_date planoflinedate,b.line_id,b.days,b.maxpersoncount,b.minpersoncount,b.id,b.xlmc line_name,\n" +
					"b.FTZT,o.ysrs,o.dd_confirm,u.usertype \n"+
					"from TA_ZT_GROUP b,(select * from ta_zt_ykorder where dd_confirm<>2 and orgid='"+orgid+"') o,ta_zt_linemng l,drmuser u \n" +
					"where b.id=o.tid(+) \n" +
					"and b.line_id=l.line_id and l.userno=u.userno and b.orgid='"+orgid+"'\n" +
					"and b.line_id="+lineID+" \n" +
					") a \n" +
					"where 1=1 \n";
			if(!"".equals(bDate)){
				sql += "and a.planoflinedate=to_date('"+bDate+"','yyyy-mm-dd') \n";
			}
			sql += "group by a.line_id,a.planoflinedate,a.days,a.maxpersoncount,a.minpersoncount,a.line_name,a.id,a.FTZT,a.usertype";
			coreDAO.executeQuery(sql, "rsDateOfLinePlan", rd);
		}
	}
	
	/**
	 * 订单修改
	 * @param rd
	 * @param sd
	 * @return
	 */	
	public int updateOrder(BizData rd,BizData sd){
		
		rd.add("flag", rd.getString("flag"));
		synchronized (a) {
			
			String tid = rd.getString("tid");
			List<String> seatsList = new ArrayList<String>();
			String ddh = rd.getStringByDI("TA_ZT_YKORDER", "id", 0);
			if("".equals(ddh))
				return -100;
			BizData rd2 = new BizData();
			Connection conn = coreDAO.getConnection();
			try {
				coreDAO.beginTrasct(conn);
				
				//根据订单号删除实际订单价格
				rd2.add("TA_ZT_GORDERPRICE", "ORDERID", ddh);
				rd2.add("TA_ZT_GORDERPRICE", "orgid", sd.getString("orgid"));
				coreDAO.delete(rd2, sd, conn);
				rd2.remove("TA_ZT_GORDERPRICE");
				
				//根据订单号新生成实际团价格
				String[] rowIDs = rd.getRowIDs("TA_ZT_GORDERPRICE");
				for(int i=0;i<rowIDs.length;i++) {
					int priceID = queryMaxIDByPara("TA_ZT_GORDERPRICE", "ID", null);
					rd.add("TA_ZT_GORDERPRICE", "ID", rowIDs[i], priceID);
					rd.add("TA_ZT_GORDERPRICE", "ORDERID", rowIDs[i], ddh);
					String PERSON_COUNT = rd.getString("TA_ZT_GORDERPRICE", "PERSON_COUNT", rowIDs[i]).equals("")?"0":rd.getString("TA_ZT_GORDERPRICE", "PERSON_COUNT", rowIDs[i]);
					rd.add("TA_ZT_GORDERPRICE", "PERSON_COUNT", rowIDs[i], PERSON_COUNT);
					//单房差房间个数
					String singleRooms = rd.getString("TA_ZT_GORDERPRICE", "SINGLE_ROOMS", rowIDs[i]);
					if("".equals(singleRooms))
						rd.add("TA_ZT_GORDERPRICE", "SINGLE_ROOMS", rowIDs[i], "0");
					rd.add("TA_ZT_GORDERPRICE", "orgid", rowIDs[i], sd.getString("orgid"));
				}
				
				//安排座位
				canUsedTheSeat(rd, tid, seatsList,  sd.getString("orgid"));
				coreDAO.insert("TA_ZT_GORDERPRICE", rd, conn);
				
				//根据订单号删除游客信息
				rd2.add("TA_ZT_VISITOR", "M_ID", ddh);
				rd2.add("TA_ZT_VISITOR", "orgid", sd.getString("orgid"));
				coreDAO.delete(rd2, sd, conn);
				rd2.remove("TA_ZT_VISITOR");
				
				//删除订单保险表
				rd2.add("TA_ZT_GINSURANCE", "ORDERID", ddh);
				rd2.add("TA_ZT_GINSURANCE", "orgid", sd.getString("orgid"));
				coreDAO.delete(rd2, conn);
				rd2.remove("TA_ZT_GINSURANCE");
				
				//插入保险表
				insertInsurance(rd, ddh, conn, sd.getString("orgid"));

				//根据订单号新生成游客信息
				rowIDs = rd.getRowIDs("TA_ZT_VISITOR");
				for(int i=0;i<rowIDs.length;i++){
					
					int visitorID = queryMaxIDByPara("TA_ZT_VISITOR", "ID", null);
					String isChild = rd.getString("TA_ZT_VISITOR", "ISCHILD", rowIDs[i]);//是否儿童
					if("".equals(isChild))
						rd.add("TA_ZT_VISITOR", "ISCHILD", rowIDs[i], "2");//成人
					String ISINSURANCE = rd.getString("TA_ZT_VISITOR", "ISINSURANCE", rowIDs[i]);//是否需要保险
					if("".equals(ISINSURANCE))
						rd.add("TA_ZT_VISITOR", "ISINSURANCE", rowIDs[i], "0");
					String ISLEADER = rd.getString("TA_ZT_VISITOR", "ISLEADER", rowIDs[i]);//是否需要保险
					if("".equals(ISLEADER))
						rd.add("TA_ZT_VISITOR", "ISLEADER", rowIDs[i], "0");
					rd.add("TA_ZT_VISITOR", "ID", rowIDs[i], visitorID);
					rd.add("TA_ZT_VISITOR", "M_ID", rowIDs[i], ddh);
					
					String seatNum = seatsList.get(i);
					rd.add("TA_ZT_VISITOR", "SEAT_NUM", rowIDs[i], seatNum);
					rd.add("TA_ZT_VISITOR", "orgid", rowIDs[i], sd.getString("orgid"));
				}
				coreDAO.insert("TA_ZT_VISITOR", rd, conn);
				//更新游客报名订单表
				rd.add("TA_ZT_YKORDER", "ysrs", 0, rowIDs.length);
				rd.add("TA_ZT_YKORDER", "DZZT",0, "0");
				rd.add("TA_ZT_YKORDER", "orgid",0, sd.getString("orgid"));
				coreDAO.update("TA_ZT_YKORDER", rd, conn);
				
				coreDAO.commitTrasct(conn);
			} catch (SQLException e) {
				try {
					coreDAO.rollbackTrasct(conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} finally {
				try{
					if(null != conn){
						conn.close();
						conn = null;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return 582;
	}
	
	/**
	 * 订单确认
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int confirmOrderNo(BizData rd,BizData sd){
		
		//更新订单表订单状态
		String orderNO = rd.getString("ddh");
		String groupId = rd.getString("tid");
		BizData data = new BizData();
		data.add("TA_ZT_YKORDER", "ID", orderNO);
		data.addAttr("TA_ZT_YKORDER", "id", 0, "oldValue", orderNO);
		data.add("TA_ZT_YKORDER", "DD_CONFIRM", "1");
		try {

			coreDAO.update("TA_ZT_YKORDER", data);
			
			//查询游客信息表中成人儿童人数
			String sql = "select a.crrs,b.etrs\n" +
					"from (\n" +
					"select count(v.id) crrs\n" +
					"from ta_zt_visitor v,ta_zt_ykorder o,ta_zt_group g\n" +
					"where v.m_id=o.id and o.tid=g.id\n" +
					"and o.dd_confirm='1'\n" +
					"and v.ischild=2\n" +
					"and g.id='"+groupId+"' and g.orgid='"+ sd.getString("orgid") +"' \n" +
					") a,(\n" +
					"select count(v.id) etrs\n" +
					"from ta_zt_visitor v,ta_zt_ykorder o,ta_zt_group g\n" +
					"where v.m_id=o.id and o.tid=g.id and g.orgid='"+ sd.getString("orgid") +"' \n" +
					"and o.dd_confirm='1'\n" +
					"and v.ischild=1\n" +
					"and g.id='"+groupId+"'\n" +
					") b";
			coreDAO.executeQuery(sql, "rsRs", rd);
			log.debug("查询游客信息表中成人儿童人数："+sql);
			
			String chiled = rd.getStringByDI("rsRs", "etrs", 0);
			String man = rd.getStringByDI("rsRs", "crrs", 0);
			//修改团队成人儿童人数
			sql = "update TA_ZT_GROUP b set b.ADULT_COUNT="+man+",CHILDREN_COUNT="+chiled+" where b.id='"+groupId+"' and b.orgid='"+ sd.getString("orgid") +"'";
			log.debug("更新团队成人和儿童人数："+sql);
			coreDAO.executeUpdate(sql);

		} catch (SQLException e) {
			
			e.printStackTrace();
		} 
		return 582;
	}
	
	/*
	 *
	 * 报名确认单&行程单
	 */
	public int viewConfirmRegedit(BizData rd,BizData sd){
		
		String ddh = rd.getString("ddh");
		String groupid = rd.getString("groupid");
		//报名单基础信息
		String sql = "select b.id,b.days, m.id ddh,m.regedit_time,b.xlmc line_name,b.begin_date,b.end_date,v.visitor_nm,v.tel,m.ysrs,g.gather,g.gather_time,g.add_m_count,\n" +
				"b.b_traffic,b.CFHBCC,b.CFSJ,b.e_traffic,b.FHHBCC,b.FHSJ,m.yi_sk,m.yin_sk,m.remark,m.business_mobile,\n" +
				"m.business_name,m.CMPNY_NAME bmsmc,m.business_tel,m.business_fax,\n" +
				"o.chief_name,o.chief_mobile,o.chief_fax,o.qq chief_qq,o.bank_name cmpny_bank_name,o.bank_code cmpny_bank_code,o.name cmpny_name\n" +
				"from TA_ZT_YKORDER m,TA_ZT_GROUP b,TA_ZT_VISITOR v,TA_ZT_GATHER g,drmuser u,hremployee e,hrorganization o\n" +
				"where m.tid=b.id and m.id=v.m_id and m.gather=g.gather_id \n" +
				"and m.creater=u.userno and u.empid=e.empid and e.orgid=o.orgid \n" +
				"and v.isleader='1' \n" +
				"and m.id='"+ddh+"'";
		String sql2="select b.bxlbmc,a.insuranceprice,a.persons,a.insuranceprice*a.persons bxfy,b.cbfs \n" +
					" from TA_ZT_GINSURANCE a,TA_ZT_INSURANCE b \n" +
					"where a.insurancetype=b.id and a.orderid='"+ddh+"'";
		String sqlmx="select d.* from TA_ZT_linedetai4g d where d.tid='"+groupid+"'order by d.rq ";
		String sqlzc="select zcjg,zhcjg from TA_ZT_JHCT where tid='"+groupid+"'";
		try {
			coreDAO.executeQuery(sql, "rsOBForView", rd);
			coreDAO.executeQuery(sql2, "bxList", rd);
			coreDAO.executeQuery(sqlmx, "xlmxview", rd);
			coreDAO.executeQuery(sqlzc, "zcview", rd);
			//价格类型及人数
			pubQueryPersonPrice(rd, ddh);
			//游客信息
			pubQueryVisitor(rd, ddh);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 992;
	}
	
	/**
	 * 价格类型及人数
	 * @param rd
	 * @param orderNO
	 * @throws SQLException
	 */
	private void pubQueryPersonPrice(BizData rd,String orderNO) throws SQLException{
		String sql = "select m.id,p.price_type,d.dmsm1 pt,p.person_count,p.price_ms,p.person_count*p.price_ms he\n" +
				",p.single_room,p.single_rooms,p.single_room*p.single_rooms q \n" +
		"from TA_ZT_YKORDER m,TA_ZT_GORDERPRICE p,dmsm d \n" +
		"where m.id=p.orderid and p.price_type=d.dmz \n" +
		"and d.dmlb=4 and p.person_count>0 \n";
		if(!"".equals(orderNO))
			sql += "and m.id like '%"+orderNO+"%' \n";
		coreDAO.executeQuery(sql, "rsPersonPrice", rd);
	}
	
	/**
	 * 游客信息
	 * @param rd
	 * @param orderNO
	 * @throws SQLException
	 */
	private void pubQueryVisitor(BizData rd,String orderNO) throws SQLException{
		String sql = "select m.id,v.visitor_nm,d.dmsm1 xb,decode(v.ischild,'1','儿童','2','成人') ischild,v.license_no \n" +
		",v.tel,v.zjyxq,decode(v.isinsurance,'1','是','0','否') isinsurance,v.seat_num,v.isleader,v.tel \n" +
		"from TA_ZT_YKORDER m,TA_ZT_VISITOR v,dmsm d \n" +
		"where m.id=v.m_id and v.sex=d.dmz and d.dmlb=14 \n";
		if(!"".equals(orderNO))
			sql += "and m.id='"+orderNO+"' \n";
		sql += "order by v.id";
		coreDAO.executeQuery(sql, "rsVisitors", rd);
	}
	
	/**
	 * 游客收款
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int listVisitorMoney(BizData rd,BizData sd) {
		
		String lineNm = rd.getString("lineName");
		String bDate = rd.getString("bDate");
		String eDate = rd.getString("eDate");
		String orderNO = rd.getString("ddh");
		String pageNOStr = rd.getString("pageNO");
		String pageSizeStr = rd.getString("pageSize");
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		String sql = "select l.line_name,m.id ddh,b.id groupKey,b.id,b.begin_date,b.end_date,m.dd_confirm,m.yin_sk,\n" +
				"m.ysrs,m.yi_sk,m.yin_sk-m.yi_sk spare \n" +
				",v.visitor_nm,v.tel,b.days,j.js*m.ysrs jsj \n" +
				" from TA_ZT_YKORDER m,TA_ZT_GROUP b,TA_ZT_LINEMNG l,(select * \n" +
				"from TA_ZT_VISITOR where isleader='1' ) v \n" +
				",(select sum(p.price_th) js,p.orderid \n" +
				"from TA_ZT_GORDERPRICE p \n" +
				"group by p.orderid) j \n" +
				"where m.tid=b.id and b.line_id=l.line_id and m.id=v.m_id \n" +
				"and j.orderid=m.id and m.creater='"+sd.getString("userno")+"' and m.orgid='"+sd.getString("orgid")+"' \n";
		if(!"".equals(orderNO))
			sql += "and m.id like '%"+orderNO+"%' \n";
		if(!"".equals(lineNm))
			sql += "and l.line_name like '%"+lineNm+"%' \n";
		if(!"".equals(bDate) && !"".equals(eDate)){
			sql += "and b.begin_date >= to_date('"+bDate+"','yyyy-mm-dd') and a.end_date <= to_date('"+eDate+"','yyyy-mm-dd') \n";
		}
			sql+=" order by m.id desc";
		try {
			coreDAO.executeQueryPage(sql, "rsVisitorMoney", pageNO, pageSize, rd);
			//价格类型及人数
			pubQueryPersonPrice(rd, orderNO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 390;
	}
	
	public int OpMoneyInit(BizData rd,BizData sd) {
		
		rd.add("action", rd.getString("action"));
		rd.add("tid", rd.getString("tid"));
		String sql = "select m.ysrs,m.id,m.regedit_time,v.visitor_nm,v.tel,m.yin_sk,nvl(m.yi_sk,0) yi_sk,m.remark,m.dd_confirm,m.TKSXF\n" +
				"from TA_ZT_YKORDER m,(select * from TA_ZT_VISITOR where isleader='1' ) v \n" +
				"where m.id=v.m_id \n" +
				"and m.id='"+rd.getString("ddh")+"'";
		try {
			coreDAO.executeQuery(sql, "rsMoneyInit", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 332;
	}
	
	/**
	 * 订单统计
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int countOrders(BizData rd,BizData sd) {
		
		String pageNOStr = rd.getString("pageNO");
		String pageSizeStr = rd.getString("pageSize");
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		
		String lineName = rd.getString("lineName");
		String groupID = rd.getString("groupID");
		String bDate = rd.getString("bDate");
		String eDate = rd.getString("eDate");
		String orgId = rd.getString("orgid");
		String orgName = rd.getString("orgName");
		
		String orderNO = rd.getString("ddh");
		try {
			//订单统计--订单号、报名人数、线路、价格、收款状态等
			String sql = "select x.msj,x.thj,x.id,y.dds,y.yisk,y.yinsk,y.spare,y.line_name,y.begin_date,y.day_counts,y.zt,y.th,y.cmpny_name from  \n" +
					"(select sum(p.price_ms*p.person_count) msj,sum(p.price_th*p.person_count) thj,p.orderid id\n" +
					"from TA_ZT_GORDERPRICE p\n" +
					"group by p.orderid\n" +
					") x,(select count(m.id) dds,sum(m.yi_sk) yisk,sum(m.yin_sk) yinsk,sum(m.yin_sk)-sum(m.yi_sk) spare\n" +
					",m.id,b.xlmc line_name,b.begin_date,b.days day_counts,d.dmsm1 zt,b.id th,m.cmpny_name\n" +
					"from TA_ZT_YKORDER m,TA_ZT_GROUP b,dmsm d\n" +
					"where m.tid=b.id and m.dd_confirm=d.dmz and d.dmlb=11 and m.dd_confirm<>2\n";
			if(!"".equals(lineName))
				sql += "and b.xlmc like '%"+lineName+"%'\n";
			if(!"".equals(groupID))
				sql += "and b.id like '%"+groupID+"%'\n";
			if(!"".equals(bDate) && !"".equals(eDate))
				sql += "and b.begin_date >= to_date('"+bDate+"','yyyy-mm-dd') and b.end_date <= to_date('"+eDate+"','yyyy-mm-dd')\n";
			if(!"".equals(orderNO))
				sql += "and m.id like '%"+orderNO+"%'\n";
			if(!"".equals(orgId))
				sql += "and m.porgid="+orgId+"\n";
			if(!"".equals(orgName))
				sql += "and m.cmpny_name like '%"+orgName+"%' \n";
			sql += "group by m.id,b.xlmc,b.begin_date,b.days,d.dmsm1,b.id,m.cmpny_name\n" +
					") y where x.id=y.id";
			sql+=" order by y.id desc";
		
			coreDAO.executeQueryPage(sql, "rsDDList", pageNO, pageSize, rd);
			//订单统计--总体统计利润、订单数等
			sql = "select sum(x.dds) dds,sum(x.yisk) yisk,sum(x.yinsk) yinsk,sum(x.spare) spare,sum(y.msj) msj,sum(y.thj) thj,sum(y.jlr) lr\n" +
					"from (\n" +
					"select count(m.id) dds,sum(m.yi_sk) yisk,sum(m.yin_sk) yinsk,sum(m.yin_sk)-sum(m.yi_sk) spare,m.id \n" +
					"from TA_ZT_YKORDER m,TA_ZT_GROUP b\n" +
					"where m.tid=b.id and m.dd_confirm<>2\n" +
					"group by m.id\n" +
					") x,(\n" +
					"select sum(msj) msj,sum(thj) thj,sum(mlr) mlr,sum(jlr) jlr,id from (\n"+
					//"--自己的产品   自己卖\n" +
					"select sum(p.price_ms*p.person_count) msj,sum(p.price_th*p.person_count) thj\n" +
					",sum(p.price_th*p.person_count) mlr,case o.isconfirm when '1' then o.yi_sk else o.yin_sk end jlr,p.orderid id\n" +
					"from TA_ZT_GORDERPRICE p,ta_zt_ykorder o,ta_zt_group g\n" +
					"where p.orderid=o.id and o.tid=g.id\n" +
					"and g.orgid='"+sd.getString("orgid")+"'\n";
			if(!"".equals(lineName))
				sql += "and g.xlmc like '%"+lineName+"%'\n";
			if(!"".equals(groupID))
				sql += "and b.id like '%"+groupID+"%'\n";
			if(!"".equals(bDate) && !"".equals(eDate))
				sql += "and b.begin_date >= to_date('"+bDate+"','yyyy-mm-dd') and b.end_date <= to_date('"+eDate+"','yyyy-mm-dd')\n";
			if(!"".equals(orderNO))
				sql += "and m.id like '%"+orderNO+"%'\n";
			sql += "group by p.orderid,o.isconfirm,o.yin_sk,o.yi_sk\n" +
					"union all\n" +
					//"--自己的产品   别人卖 \n" +
					"select sum(p.price_ms*p.person_count) msj,sum(p.price_th*p.person_count) thj\n" +
					",sum(p.price_th*p.person_count) mlr,sum(p.price_th*p.person_count) jlr,p.orderid id\n" +
					"from TA_ZT_GORDERPRICE p,ta_zt_ykorder o,ta_zt_group g\n" +
					"where p.orderid=o.id and o.tid=g.id\n" +
					"and g.orgid='"+sd.getString("orgid")+"'\n";
			if(!"".equals(lineName))
				sql += "and g.xlmc like '%"+lineName+"%'\n";
			if(!"".equals(groupID))
				sql += "and b.id like '%"+groupID+"%'\n";
			if(!"".equals(bDate) && !"".equals(eDate))
				sql += "and b.begin_date >= to_date('"+bDate+"','yyyy-mm-dd') and b.end_date <= to_date('"+eDate+"','yyyy-mm-dd')\n";
			if(!"".equals(orderNO))
				sql += "and m.id like '%"+orderNO+"%'\n";
				sql += "group by p.orderid,o.isconfirm,o.yin_sk,o.yi_sk\n" +
					"union all\n" +
					//"--代别人卖\n" +
					"select sum(p.price_ms*p.person_count) msj,sum(p.price_th*p.person_count) thj\n" +
					",sum(p.price_th*p.person_count) mlr,(case o.isconfirm when '1' then o.yi_sk else o.yin_sk end)-sum(p.price_th*p.person_count) jlr\n" +
					",p.orderid id\n" +
					"from TA_ZT_GORDERPRICE p,ta_zt_ykorder o,ta_zt_group g\n" +
					"where p.orderid=o.id and o.tid=g.id\n" +
					"and g.orgid='"+sd.getString("orgid")+"'\n";
				if(!"".equals(lineName))
					sql += "and g.xlmc like '%"+lineName+"%'\n";
				if(!"".equals(groupID))
					sql += "and b.id like '%"+groupID+"%'\n";
				if(!"".equals(bDate) && !"".equals(eDate))
					sql += "and b.begin_date >= to_date('"+bDate+"','yyyy-mm-dd') and b.end_date <= to_date('"+eDate+"','yyyy-mm-dd')\n";
				if(!"".equals(orderNO))
					sql += "and m.id like '%"+orderNO+"%'\n";
					sql += "group by p.orderid,o.isconfirm,o.yin_sk,o.yi_sk\n" +
					")\n" +
					"group by id\n" +
					") y where x.id=y.id\n";
			
			coreDAO.executeQuery(sql, "rsAllDDLR", rd);
			pubQueryPersonPrice(rd, orderNO);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 382;
	}
	
	/**
	 * 已封团的线路生成新的团信息
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int initNewGroup(BizData rd,BizData sd) {
		
		String lineID = rd.getString("lineID");
		String bDate = rd.getString("bDate");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			bDate = bDate.replaceAll("-", "");
			int gID = queryMaxIDByPara("TA_ZT_GROUP", "substr(id,11,4)", "substr(id,3,8) = '"+sdf.format(sdf.parse(bDate))+"'");
			String gIDStr = "";
			if(gID < 10) {
				gIDStr = "HJ"+sdf.format(sdf.parse(bDate))+"1001";
				clearCacheObj("TA_ZT_GROUP", "substr(id,11,4)");
			}
			else
				gIDStr = "HJ"+sdf.format(sdf.parse(bDate))+gID;
			String sql = "select t.*,'"+gIDStr+"' g_id,to_date('"+bDate+"','yyyy-mm-dd') bDate\n" +
					",case when t.day_counts = 1 then to_date('"+bDate+"','yyyy-mm-dd') else to_date('"+bDate+"','yyyy-mm-dd')+t.day_counts end endDate\n" +
					"from TA_ZT_LINEMNG t\n" +
					"where t.line_id="+lineID;
			coreDAO.executeQuery(sql, "rsLineBase", rd);
			sql="select a.line_price_id,a.line_id,b.dmsm1 price_type,a.price_ms,a.price_th,\n" +
				"a.price_zd,a.REMARK,a.price_type,a.SINGLE_ROOM \n" +
				"from TA_ZT_LINE_PRICES a,dmsm b \n" +
				"where a.price_type=b.dmz and b.dmlb=4 \n" +
				"and a.line_id="+lineID;
			coreDAO.executeQuery(sql, "rsLinePrice", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 843;
	}
	
	public void updateGroupBlob(String groupID, Connection conn, BizData rd) throws SQLException, UnsupportedEncodingException{
		
		String lineDetail = rd.getString("LINE_DETAIL");
		String sql = "update TA_ZT_GROUP b set b.LINE_DETAIL=? where b.id="+groupID;
		PreparedStatement preStmt = conn.prepareStatement(sql);
		preStmt.setBytes(1, lineDetail.getBytes("GBK"));
		preStmt.executeUpdate();
		preStmt.close();
		preStmt = null;
	}
	
	/*
	 * 散客登记表
	 */
	public int getAllSKDJ(BizData rd,BizData sd){
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageSize"));
		String th=rd.getString("th");
		String ddh=rd.getString("ddh");
		String bDate=rd.getString("bDate");
		String eDate=rd.getString("eDate");
		String lineN=rd.getString("lineN");
		String gys=rd.getString("gys");
		
		String sql="SELECT G.ID,O.ID DDH,g.xlmc,O.YI_SK,g.begin_date,o.cmpny_name,ii.bx,ii.cb,o.ysrs\n" +
				",v.visitor_nm,v.tel,o.yi_sk-tcb.thj+ii.bxlr tlr,o.regedit_time\n"+
			" FROM TA_ZT_YKORDER O,TA_ZT_GROUP G \n"+
			",(\n" +
			"select sum(i.insuranceprice*i.persons) bx,sum(i.insurancecost*i.persons) cb\n" +
			",sum(i.insuranceprice*i.persons) - sum(i.insurancecost*i.persons) bxlr,i.orderid\n" +
			"from TA_ZT_GINSURANCE i\n" +
			"group by i.orderid\n" +
			") ii,(select visitor_nm,tel,m_id ddh from ta_zt_visitor where isleader=1) v\n" +
			",(select sum(op.price_th*op.person_count) thj,op.orderid\n" +
			"from TA_ZT_GORDERPRICE op\n" +
			"group by op.orderid\n" +
			") tcb\n" +
			",ta_zt_linemng l,drmuser u\n" +
			"WHERE O.TID=G.ID and ii.orderid=o.id and l.line_id=g.line_id\n" +
			"and u.userno=l.userno and o.id=v.ddh and tcb.orderid=o.id and  O.DD_CONFIRM = 1\n" ;

			if(!"".equals(eDate) &&  !"".equals(bDate))				
				sql += " and to_char(o.regedit_time,'yyyy-mm-dd') >= '"+bDate+"' and to_char(o.regedit_time,'yyyy-mm-dd') <= '"+eDate+"'\n" ;
			if(!"".equals(lineN))	
				sql += "and g.xlmc like '%"+lineN+"%' \n";
			if(!"".equals(gys))
				sql += "and g.szlxsmc like '%"+gys+"%' \n";
			if(!"".equals(th))
				sql += "and g.id like '%"+th+"%'\n";
			if(!"".equals(ddh))
				sql += "and o.id like '%"+ddh+"%'\n";
		String sql2="select b.*,a.id,a.xlmc,a.regedit_time from (\n" +
				"SELECT G.ID,O.ID DDH,g.xlmc,O.YI_SK,g.begin_date,o.cmpny_name,ii.bx,ii.cb,o.ysrs\n" +
			",v.visitor_nm,v.tel,o.yi_sk-tcb.thj+ii.bxlr tlr,o.regedit_time,g.szlxsmc\n"+
			" FROM TA_ZT_YKORDER O,TA_ZT_GROUP G \n"+
			",(\n" +
			"select sum(i.insuranceprice*i.persons) bx,sum(i.insurancecost*i.persons) cb\n" +
			",sum(i.insuranceprice*i.persons) - sum(i.insurancecost*i.persons) bxlr,i.orderid\n" +
			"from TA_ZT_GINSURANCE i\n" +
			"group by i.orderid\n" +
			") ii,(select visitor_nm,tel,m_id ddh from ta_zt_visitor where isleader=1) v\n" +
			",(select sum(op.price_th*op.person_count) thj,op.orderid\n" +
			"from TA_ZT_GORDERPRICE op\n" +
			"group by op.orderid\n" +
			") tcb\n" +
			",ta_zt_linemng l,drmuser u\n" +
			"WHERE O.TID=G.ID and ii.orderid=o.id and l.line_id=g.line_id\n" +
			"and u.userno=l.userno and o.id=v.ddh and tcb.orderid=o.id AND O.DD_CONFIRM = 1\n" +
			
			") a,(\n" +
			"select op.price_th,op.price_ms,op.price_zd,d.dmsm1,op.person_count,op.orderid\n" +
			"from TA_ZT_GORDERPRICE op,dmsm d\n" +
			"where op.price_type=d.dmz\n" +
			"and d.dmlb=4\n" +
			"and op.person_count<>0\n" +
			") b\n" +
			"where a.ddh=b.orderid\n" ;
		if(!"".equals(th))
			sql2 += "and a.id like '%"+th+"%'\n";
		if(!"".equals(ddh))
			sql2 += "and a.ddh like '%"+ddh+"%'\n";
		
		if(!"".equals(eDate) &&  !"".equals(bDate))				
				sql2 += " and to_char(a.regedit_time,'yyyy-mm-dd') >= '"+bDate+"' and to_char(a.regedit_time,'yyyy-mm-dd') <= '"+eDate+"'\n" ;
		if(!"".equals(lineN))	
				sql2 += "and a.xlmc like '%"+lineN+"%' \n";
		if(!"".equals(gys))
				sql2 += "and a.szlxsmc like '%"+gys+"%' \n";
		try {
			coreDAO.executeQueryPage(sql, "skList", pageNO, pageSize, rd);
			coreDAO.executeQuery(sql2, "priceList", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/*
	 * 打印退款单
	 * 
	 */
	public int printBackMoeny(BizData rd,BizData sd){
		String ddh=rd.getString("ddh");
		String sql="select a.id,a.yin_sk,(a.tkje+a.tksxf) yi_sk,a.tkje,a.tksxf from TA_ZT_YKORDER a where a.id='"+ddh+"'";
		try {
			coreDAO.executeQuery(sql, "ddInfo", rd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * 订单退款
	 * 将订单状态置为2--取消订单；
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int cancelOrderAndMoney(BizData rd,BizData sd) {

		Connection conn = coreDAO.getConnection();
		try {
			String ddh = rd.getStringByDI("TA_ZT_YKORDER", "id", 0);
			String tid = rd.getString("tid");
			String action = rd.getString("action");
			coreDAO.beginTrasct(conn);
			// 查询游客儿童/成人人数
			queryPersonCounts(tid, ddh, rd);
			String childStr = rd.getStringByDI("personsCount", "childs", 0);
			String adultStr = rd.getStringByDI("personsCount", "cr", 1);
			// 查询团的成人/儿童人数
			String sql = "select b.children_count,b.adult_count from TA_ZT_GROUP b where b.id='" + tid + "'";
			coreDAO.executeQuery(sql, "rsPersonCountOfGroup", rd);
			String groupAdult = rd.getStringByDI("rsPersonCountOfGroup", "adult_count", 0);
			String groupChild = rd.getStringByDI("rsPersonCountOfGroup", "children_count", 0);
			if(!"".equals(groupAdult)){
				
				int gAdult = Integer.parseInt(groupAdult);
				int gChild = Integer.parseInt(groupChild);
				// 用团队的成人和儿童人数减退款的成人和儿童人数
				int adult = gAdult - Integer.parseInt(adultStr.equals("") ? "0" : adultStr);
				int child = gChild - Integer.parseInt(childStr.equals("") ? "0" : childStr);
				sql = "update TA_ZT_GROUP b set b.children_count=" + child
						+ ",b.adult_count=" + adult + " where b.id='" + tid + "'";
				coreDAO.executeUpdate(sql, conn);
			}
			if ("back".equals(action)) {
				// 更新订单表
				String orderConfirm = rd.getString("DD_CONFIRM");
				if (!"".equals(orderConfirm))
					rd.add("TA_ZT_YKORDER", "DD_CONFIRM", orderConfirm);
				rd.add("TA_ZT_YKORDER", "TKSXF", rd.getStringByDI("TA_ZT_YKORDER", "TKSXF", 0).equals("")?"0":rd.getStringByDI("TA_ZT_YKORDER", "TKSXF", 0));
			}
			update(rd, conn);
			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try{
				if(null != conn){
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return 909;
	}
	
	/**
	 * 根据订单号查询一个团的成人/儿童人数
	 * @param tid
	 * @param orderNO
	 * @param data
	 */
	private void queryPersonCounts(String tid, String orderNO, BizData data){
		
		String sql = "select nvl(a.cr,0) cr,nvl(b.childs,0) childs,g.id\n" +
				"from (\n" +
				"select count(v.id) cr,o.tid\n" +
				"from ta_zt_visitor v, ta_zt_ykorder o\n" +
				"where v.m_id=o.id\n" +
				"and v.ischild=2\n" +
				"and o.id='"+orderNO+"'\n" +
				"group by o.tid\n" +
				") a,(\n" +
				"select count(v.id) childs,o.tid\n" +
				"from ta_zt_visitor v, ta_zt_ykorder o\n" +
				"where v.m_id=o.id\n" +
				"and v.ischild=1\n" +
				"and o.id='"+orderNO+"'\n" +
				"group by o.tid\n" +
				") b,ta_zt_group g\n" +
				"where a.tid(+)=g.id\n" +
				"and b.tid(+)=g.id\n" +
				"and g.id='"+tid+"'";
		try {
			coreDAO.executeQuery(sql, "personsCount", data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//打印所有游客信息名单
	public int printYkmd(BizData rd,BizData sd){
		String groupID = rd.getString("groupID");
		String sql = "select v.* from ta_zt_visitor v,ta_zt_ykorder y where v.m_id=y.id and y.tid='"+groupID+"'";
		try {
			coreDAO.executeQuery(sql, "ykmds", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
	}
}
