package com.trm.lineMng;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.databus.BizData;

/**
  * @ClassName: LineMngBLC
  * @Description: TODO 组团 - 线路管理 事务处理类
  * @author KingStong - likai
  * @date 2012-4-19 上午5:56:22
  *
  */
public class LineMngBLC extends DBBLC {
	
	private static Logger log;
	public LineMngBLC(){
		log = Logger.getLogger(LineMngBLC.class);
		this.entityName = "TA_ZT_LINEMNG";		
	}
	
	/**
	  *
	  * @Title: listLineByCreater
	  * @Description: TODO 线路信息列表查询
	  */
	public int listLineByCreater(BizData rd,BizData sd){

		String lineName = rd.getString("lineName");
		String beginDate = rd.getString("beginDate");
		String pageNOStr = rd.getString("pageNO");
		String pageSizeStr = rd.getString("pageSize");
		String orgid = sd.getString("orgid");
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		//查询基础信息
		String sql = "select d.line_id,d.line_name,d.planoflinedate,d.cmpny_name,d.maxpersoncount,d.line_state,b.spare  \n" +
				" from ( \n" +
				"select l.line_id,l.line_name,c.planOfLineDate,l.maxPersonCount,l.line_state,l.SZLXSMC CMPNY_NAME  \n" +
				"from TA_ZT_LINEMNG l,drmuser u,(\n" +
				"   select min(g.begin_date) planoflinedate,line_id \n" +
				"from ta_zt_group g where g.begin_date >= to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') and g.orgid='"+orgid+"' \n" + 
				"group by line_id \n" +
				") c\n" +
				"where l.userno=u.userno and l.line_id=c.line_id and l.xlxz<>'2'  and l.orgid='"+orgid+"' and u.orgid='"+orgid+"' \n";
		if("P".equals(sd.getString("userType"))){

			sql += "and u.userno='"+sd.getString("userno")+"' \n";
		}
			sql += ") d, ( \n" +
				"select l.maxPersonCount- sum(nvl(m.YSRS,0)) spare,b.line_id,b.begin_date \n" +
				"from TA_ZT_GROUP b left join (select * from TA_ZT_YKORDER where dd_confirm<>2) m on b.id=m.tid \n" +
				"join TA_ZT_LINEMNG l on l.line_id=b.line_id \n" +
				"where l.xlxz<>'2'  and l.orgid='"+orgid+"' and b.orgid='"+orgid+"' \n" +
				"group by l.maxPersonCount,b.line_id,b.begin_date \n" +
				") b \n" +
				"where d.line_id=b.line_id and d.planOflinedate=b.begin_date  \n";
		if(!"".equals(lineName))
			sql += "and d.line_name like '%"+lineName+"%'\n";
		if(!"".equals(beginDate))
			sql += "and b.begin_date = to_date('"+beginDate+"','yyyy-mm-dd')";
		sql += "order by d.planoflinedate desc" ;
		try {
			coreDAO.executeQueryPage(sql, "rsLineBase", pageNO, pageSize, rd);
			String sql2 = "select n.* from (\n" + sql + ") m,(\n" + 
				"select l.line_id,lp.price_type,lp.price_th,d.dmsm1 thj,lp.price_ms, d.dmsm1 msj \n" +
					"from TA_ZT_LINEMNG l,TA_ZT_LINE_PRICES lp,dmsm d  \n" +
					"where l.line_id=lp.line_id and d.dmlb=4 and d.dmz=lp.price_type  and l.orgid='"+orgid+"' and lp.orgid='"+orgid+"' \n" +
					") n where m.line_id=n.line_id \n";
			coreDAO.executeQuery(sql2, "rsLinePrice", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 909;
	}
	
	/**
	  *
	  * @Title: delLine
	  * @Description: TODO 删除线路信息
	  */
	public int delLine(BizData rd,BizData sd){
	String id=rd.getString("id");
	String orgid = sd.getString("orgid");
	int dd_num=0;
	Connection conn=coreDAO.getConnection();
	try {
		coreDAO.beginTrasct(conn);
		String sql = "select a.id from TA_ZT_GROUP a,TA_ZT_LINEMNG b where a.line_id=b.line_id and b.line_id="
				+ id + " and a.orgid=b.orgid and b.orgid='"+orgid+"'"; // 查询此线路下的所有团
		int rows = coreDAO.executeQuery(sql, "groupList", rd);
		for (int d = 0; d < rows; d++) {
			String dd_sql = "select a.id from  TA_ZT_YKORDER a where a.tid='"
					+ rd.getStringByDI("groupList", "id", d)+"'";
			int dd_rows = coreDAO.executeQuery(dd_sql, "ddList", rd);
			if (dd_rows > 0) {
				dd_num += 1;
				rd.add("msg", "ok");
			}
		}
		if (dd_num == 0) {
			sql = "delete from TA_ZT_LINEMNG a where a.line_id=" + id +" and a.orgid='"+orgid+"'"; // 删除线路
			coreDAO.executeUpdate(sql, conn);
			sql = "delete from TA_ZT_LINEMNGBLOB a where a.xlid=" + id +" and a.orgid='"+orgid+"'"; // 删除线路
			coreDAO.executeUpdate(sql, conn);
			sql = "delete from TA_ZT_FBJH_TMP a where a.line_id=" + id +" and a.orgid='"+orgid+"'"; // 删除线路发班计划日期表
			coreDAO.executeUpdate(sql, conn);
			sql = "delete from TA_ZT_LINE_PRICES a where a.line_id=" + id +" and a.orgid='"+orgid+"'"; // 删除线路价格表
			coreDAO.executeUpdate(sql, conn);
			sql = "delete from TA_ZT_GATHER a where a.line_id=" + id +" and a.orgid='"+orgid+"'"; // 删除线路集合地点
			coreDAO.executeUpdate(sql, conn);
			sql = "delete from TA_ZT_LINEDETAIL a where a.LINEID=" + id +" and a.orgid='"+orgid+"'";//线路行程明细 
			coreDAO.executeUpdate(sql, conn);
			for (int a = 0; a < rows; a++) {
				sql = "delete from TA_ZT_GPRICE a where a.G_ID='" + rd.getStringByDI("groupList", "id", a)+"' and a.orgid='"+orgid+"'"; // 删除所有团价格
				coreDAO.executeUpdate(sql, conn);
			}
			sql = "delete from TA_ZT_GROUP a where a.line_id=" + id +" and a.orgid='"+orgid+"'"; // 删除线路下所有团
			coreDAO.executeUpdate(sql, conn);
			coreDAO.commitTrasct(conn);
		}
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
		return 1;
	}
	
	/**
	  * @Title: initLineInfo
	  * @Description: TODO 获取当前旅行社名称
	  */
	public int initLineInfo(BizData rd, BizData sd) {

		String sql = "select t.orgid,t.name from HRORGANIZATION t where t.orgid='"+sd.getString("orgid")+"'";
		try {
			coreDAO.executeQuery(sql, "rsTraAgcInfo", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		} 

		return 999;
	}
	
	/**
	  *
	  * @Title: queryTopOrgByOrgParam
	  * @Description: TODO 递归查询当前机构的最高机构信息
	  */
	
	public void queryTopOrgByOrgParam(int orgParam, BizData rd, DAO coreDAO) throws SQLException{
    	BizData data = new BizData();
    	data.add("hrorganization", "orgid", orgParam);
    	coreDAO.select(data);
    	int pid = data.getInt("hrorganizations", "parent_orgid", 0);
    	if(pid == 0){
    		rd.add("rsTraAgcInfo", "orgid", data.getStringByDI("hrorganizations","orgid", 0));
			rd.add("rsTraAgcInfo", "cmpny_name", data.getStringByDI("hrorganizations", "name", 0));
			rd.add("rsTraAgcInfo", "CHIEF_MOBILE", data.getStringByDI("hrorganizations", "tel", 0));
    	}
    	else
    	{
    		queryTopOrgByOrgParam(pid, rd, coreDAO);
    	}
    }
    public void queryOrgInfoByOrgID(BizData rd){
    	
    	try {
			coreDAO.select("hrorganization", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
	/* (非 Javadoc) 
	* <p>Title: insert</p> 
	* <p>Description: 添加线路信息</p> 
	*/ 
	public int insert(BizData rd, BizData sd) {
		
		String bDate = rd.getString("b_date");
		String eDate = rd.getString("e_date");
		String dayCounts = rd.getStringByDI("TA_ZT_LINEMNG", "DAY_COUNTS", 0);
		if ("".equals(dayCounts))
			dayCounts = "0";

		String plan = rd.getStringByDI("TA_ZT_LINEMNG", "plan", 0);
		int lineBaseID = queryMaxIDByPara("TA_ZT_LINEMNG", "line_id", null);
		String orgid = sd.getString("orgid");
		rd.add("lineID", lineBaseID);
		rd.add("orgid", orgid);
		Connection conn = coreDAO.getConnection();
	
		try {
			coreDAO.beginTrasct(conn);
			// 线路基础信息表
			rd.add("TA_ZT_LINEMNG","orgid", orgid);
			rd.add("TA_ZT_LINEMNG", "LINE_ID", lineBaseID);
			coreDAO.insert("TA_ZT_LINEMNG", rd, conn);
			// 保险价格表
			addInsurancePrice(rd, lineBaseID, conn, orgid);
			// 线路价格表
			addLinePrice(rd, lineBaseID, conn, orgid);
			//线路行程明细表
			addLineDetail(rd, conn, lineBaseID, orgid);
			//线路大字段存储
			addLineBlob(rd, conn, lineBaseID, orgid);
			// 线路发班计划表
			List<Date> daysList = new ArrayList<Date>();
			daysList = compareDate(plan, rd);

			Calendar c = Calendar.getInstance();
			for (int i = 0; i < daysList.size(); i++) {

				Date selectedDate = (Date) daysList.get(i);

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
				// 生成团基本信息
				String gIDStr = createGroupNO(prefix, selectedDate, "ta_zt_group", "id", Integer.parseInt(sd.getString("orgid")), conn);
				c.setTime(selectedDate);
				c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(dayCounts));
				Date e = c.getTime();

				// 添加数据到BizData对象中
				addDate2BizData(rd, sd, gIDStr, selectedDate, e, i, lineBaseID, dayCounts);
				// 更新团线路明细
				updateGroupBlob(gIDStr, conn, rd, sd.getString("orgid"));
				// 团价格
				addGroupPrice(rd, gIDStr,orgid, conn);
			}
			// 保存团基本信息表
			coreDAO.insert("TA_ZT_GROUP", rd, conn);
			rd.remove("TA_ZT_GROUP");
			
			// 临时表，保存后用于修改查看
			insertLineTmp(conn, rd, lineBaseID, bDate, eDate, orgid);

			// 集合地点
			addGather(rd, conn, lineBaseID, orgid);
			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
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
		
		return 943;
	}
	
	/**
	 * @Description: TODO 更新团信息表的线路行程明细
	 */
	private void updateGroupBlob(String groupID, Connection conn, BizData rd, String orgid) throws SQLException, UnsupportedEncodingException, NullPointerException{
		
		//添加团行程明细
		String sql = "insert into TA_ZT_LINEDETAI4G(id,Tid,Rq,Xcmx,Breakfast,Cmeal,Supper,ZSBZ,ORGID) VALUES (?,?,?,?,?,?,?,?,?)";
		PreparedStatement preStmt = conn.prepareStatement(sql);
		String[] rowIDs = rd.getRowIDs("TA_ZT_LINEDETAIL");
		for(int i=0;i<rowIDs.length;i++){
			
			int pkID = queryMaxIDByPara("TA_ZT_LINEDETAI4G", "id", null);
			preStmt.setInt(1, pkID);
			preStmt.setString(2,groupID);
			preStmt.setString(3, rd.getString("TA_ZT_LINEDETAIL", "rq", rowIDs[i]));
			preStmt.setBytes(4, rd.getString("TA_ZT_LINEDETAIL", "xcmx", rowIDs[i]).getBytes("GBK"));
			preStmt.setString(5, rd.getString("TA_ZT_LINEDETAIL", "breakfast", rowIDs[i]).equals("")?"N":"Y");
			preStmt.setString(6, rd.getString("TA_ZT_LINEDETAIL", "cmeal", rowIDs[i]).equals("")?"N":"Y");
			preStmt.setString(7, rd.getString("TA_ZT_LINEDETAIL", "Supper", rowIDs[i]).equals("")?"N":"Y");
			preStmt.setString(8, rd.getString("TA_ZT_LINEDETAIL", "ZSBZ", rowIDs[i]));
			preStmt.setString(9, orgid);
			preStmt.executeUpdate();
		}
		preStmt.close();
		preStmt = null;
	}
	
	/**
	 * @Description: TODO 添加线路的集合地点 
	 */
	private void addGather(BizData rd,Connection conn,int lineID, String orgid) throws SQLException {
		
		String[] rowIDs = rd.getRowIDs("TA_ZT_GATHER");
		for(int i=0;i<rowIDs.length;i++){
			//集合地点ID
			int gatherID = queryMaxIDByPara("TA_ZT_GATHER", "GATHER_ID", null);
			String jj = rd.getString("TA_ZT_GATHER", "ADD_M_COUNT", rowIDs[i]);
			rd.add("TA_ZT_GATHER", "GATHER_ID", i, gatherID);
			rd.add("TA_ZT_GATHER", "ADD_M_COUNT", i, jj.equals("")?"0":jj);
			rd.add("TA_ZT_GATHER", "LINE_ID", i, lineID);
			rd.add("TA_ZT_GATHER", "orgid", i, orgid);
		}
		coreDAO.insert("TA_ZT_GATHER", rd, conn);
		rd.remove("TA_ZT_GATHER");
	}
	
	/**
	  * @Title: addLineDetail
	  * @Description: TODO 添加线路行程明细
	  */
	private void addLineDetail(BizData rd, Connection conn, int lineBaseID,  String orgid) throws UnsupportedEncodingException, SQLException{
		
		String[] rowIDs = rd.getRowIDs("TA_ZT_LINEDETAIL");
		String sql = "insert into TA_ZT_LINEDETAIL(ID,LINeID,RQ,BREAKFAST,CMEAL,SUPPER,ZSBZ,XCMX,ORGID) values (?,?,?,?,?,?,?,?,?)";
		PreparedStatement preStmt = conn.prepareStatement(sql);
		for(int i=0;i<rowIDs.length;i++){
			
			int id = queryMaxIDByPara("TA_ZT_LINEDETAIL", "id", null);
			String b = rd.getString("TA_ZT_LINEDETAIL", "BREAKFAST", rowIDs[i]);
			if("".equals(b))
				b = "N";
			String c = rd.getString("TA_ZT_LINEDETAIL", "CMEAL", rowIDs[i]);
			if("".equals(c))
				c = "N";
			String s = rd.getString("TA_ZT_LINEDETAIL", "SUPPER", rowIDs[i]);
			if("".equals(s))
				s = "N";
			preStmt.setInt(1, id);
			preStmt.setInt(2, lineBaseID);
			preStmt.setString(3, rd.getString("TA_ZT_LINEDETAIL", "rq", rowIDs[i]));
			preStmt.setString(4, b);
			preStmt.setString(5, c);
			preStmt.setString(6, s);
			preStmt.setString(7, rd.getString("TA_ZT_LINEDETAIL", "ZSBZ", rowIDs[i]));
			preStmt.setBytes(8, rd.getString("TA_ZT_LINEDETAIL", "XCMX", rowIDs[i]).getBytes("GBK"));
			preStmt.setString(9, orgid);
			preStmt.executeUpdate();
		}
		preStmt.close();
		preStmt = null;
		log.debug("行程明细表插入SQL："+sql);
	}
	
	/**
	  *
	  * @Title: addLineBlob
	  * @Description: TODO 添加线路其他信息(BLOB)
	  */
	private void addLineBlob(BizData rd, Connection conn, int lineBaseID, String orgid) throws UnsupportedEncodingException, SQLException {
		String[] rowIDs = rd.getRowIDs("TA_ZT_LINEMNGBLOB");
		String sql = "insert into TA_ZT_LINEMNGBLOB(XLID,TSJS,FYBH,FYBBH,YDXZ,ZFXM,GWD,WXTS,CJLYXZ,LYZYSX,ORGID) values (?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preStmt = conn.prepareStatement(sql);
		for(int i=0;i<rowIDs.length;i++){
			
			preStmt.setInt(1, lineBaseID);
			preStmt.setBytes(2, rd.getString("TA_ZT_LINEMNGBLOB", "TSJS", rowIDs[i]).getBytes("GBK"));
			preStmt.setBytes(3, rd.getString("TA_ZT_LINEMNGBLOB", "FYBH", rowIDs[i]).getBytes("GBK"));
			preStmt.setBytes(4, rd.getString("TA_ZT_LINEMNGBLOB", "FYBBH", rowIDs[i]).getBytes("GBK"));
			preStmt.setBytes(5, rd.getString("TA_ZT_LINEMNGBLOB", "YDXZ", rowIDs[i]).getBytes("GBK"));
			preStmt.setBytes(6, rd.getString("TA_ZT_LINEMNGBLOB", "ZFXM", rowIDs[i]).getBytes("GBK"));
			preStmt.setBytes(7, rd.getString("TA_ZT_LINEMNGBLOB", "GWD", rowIDs[i]).getBytes("GBK"));
			preStmt.setBytes(8, rd.getString("TA_ZT_LINEMNGBLOB", "WXTS", rowIDs[i]).getBytes("GBK"));
			preStmt.setBytes(9, rd.getString("TA_ZT_LINEMNGBLOB", "CJLYXZ", rowIDs[i]).getBytes("GBK"));
			preStmt.setBytes(10, rd.getString("TA_ZT_LINEMNGBLOB", "LYZYSX", rowIDs[i]).getBytes("GBK"));
			preStmt.setInt(11, Integer.valueOf(orgid));
			preStmt.executeUpdate();
		}
		preStmt.close();
		preStmt = null;
		log.debug("线路BLOB大字段插入SQL："+sql);
	}
	
	/**
	 * @Description: TODO 将线路信息添加为团基础信息
	 */
	private void addDate2BizData(BizData rd,BizData sd,String gIDStr,Date b,Date e,int i,int lineBaseID,String dayCounts){
		
		String jd = rd.getStringByDI("TA_ZT_LINEMNG", "JD", 0);
		String gw = rd.getStringByDI("TA_ZT_LINEMNG", "gw", 0);
		String min = rd.getStringByDI("TA_ZT_LINEMNG", "MINPERSONCOUNT", 0);
		String max = rd.getStringByDI("TA_ZT_LINEMNG", "MAXPERSONCOUNT", 0);
		
		rd.add("TA_ZT_GROUP", "id",String.valueOf(i), gIDStr);
		rd.add("TA_ZT_GROUP", "XLMC",String.valueOf(i), rd.getStringByDI("TA_ZT_LINEMNG", "line_name", 0));
		rd.add("TA_ZT_GROUP", "ORGID",String.valueOf(i), rd.getStringByDI("TA_ZT_LINEMNG", "ORGID", 0));//所在组团社ID
		rd.add("TA_ZT_GROUP", "SZLXSMC",String.valueOf(i), rd.getStringByDI("TA_ZT_LINEMNG", "SZLXSMC", 0));//所在组团社名称
		rd.add("TA_ZT_GROUP", "SZLXSLXDH",String.valueOf(i), rd.getStringByDI("TA_ZT_LINEMNG", "SZLXSLXDH", 0));//所在组团社联系电话
		rd.add("TA_ZT_GROUP", "begin_date",String.valueOf(i), BizData.sdfDate.format(b));
		rd.add("TA_ZT_GROUP", "end_date",String.valueOf(i), BizData.sdfDate.format(e));
		rd.add("TA_ZT_GROUP", "line_id",String.valueOf(i), lineBaseID);
		rd.add("TA_ZT_GROUP", "state",String.valueOf(i), "1");//待计划
		rd.add("TA_ZT_GROUP", "FTZT",String.valueOf(i), "1");//封团状态
		rd.add("TA_ZT_GROUP", "DAYS",String.valueOf(i), "".equals(dayCounts)?"0":Integer.parseInt(dayCounts));
		
		rd.add("TA_ZT_GROUP", "JD",String.valueOf(i), "".equals(jd)?"0":jd);
		rd.add("TA_ZT_GROUP", "GW",String.valueOf(i), "".equals(gw)?"0":gw);
		rd.add("TA_ZT_GROUP", "SFZBC",String.valueOf(i), rd.getStringByDI("TA_ZT_LINEMNG", "SFZBC", 0));
		rd.add("TA_ZT_GROUP", "YKLX",String.valueOf(i), rd.getStringByDI("TA_ZT_LINEMNG", "YKLX", 0));
		
		rd.add("TA_ZT_GROUP", "PLAN",String.valueOf(i), rd.getStringByDI("TA_ZT_LINEMNG", "PLAN", 0));
		rd.add("TA_ZT_GROUP", "MINPERSONCOUNT",String.valueOf(i), "".equals(min)?"0":min);
		rd.add("TA_ZT_GROUP", "MAXPERSONCOUNT",String.valueOf(i), "".equals(max)?"0":max);
		rd.add("TA_ZT_GROUP", "INSURANCE",String.valueOf(i), rd.getStringByDI("TA_ZT_LINEMNG", "INSURANCE", 0));
		
		rd.add("TA_ZT_GROUP", "RETURN_ROLE",String.valueOf(i), rd.getStringByDI("TA_ZT_LINEMNG", "RETURN_ROLE", 0));
		rd.add("TA_ZT_GROUP", "E_TRAFFIC",String.valueOf(i), rd.getStringByDI("TA_ZT_LINEMNG", "E_TRAFFIC", 0));
		rd.add("TA_ZT_GROUP", "B_TRAFFIC",String.valueOf(i), rd.getStringByDI("TA_ZT_LINEMNG", "B_TRAFFIC", 0));
		rd.add("TA_ZT_GROUP", "DTRQ",String.valueOf(i), BizData.sdfDate.format(b));
		rd.add("TA_ZT_GROUP", "user_no",String.valueOf(i), sd.getString("userno"));
		rd.add("TA_ZT_GROUP", "XLQY", String.valueOf(i), rd.getStringByDI("TA_ZT_LINEMNG", "XLQY", 0));
		rd.add("TA_ZT_GROUP", "CFHBCC", String.valueOf(i), rd.getStringByDI("TA_ZT_LINEMNG", "CFHBCC", 0));
		rd.add("TA_ZT_GROUP", "FHHBCC", String.valueOf(i), rd.getStringByDI("TA_ZT_LINEMNG", "FHHBCC", 0));
		rd.add("TA_ZT_GROUP", "CFSJ", String.valueOf(i), rd.getStringByDI("TA_ZT_LINEMNG", "CFSJ", 0));
		rd.add("TA_ZT_GROUP", "FHSJ", String.valueOf(i), rd.getStringByDI("TA_ZT_LINEMNG", "FHSJ", 0));
		rd.add("TA_ZT_GROUP", "GNLXDH", String.valueOf(i), rd.getStringByDI("TA_ZT_LINEMNG", "GNLXDH", 0));
		rd.add("TA_ZT_GROUP", "JCLXDH", String.valueOf(i), rd.getStringByDI("TA_ZT_LINEMNG", "JCLXDH", 0));
		rd.add("TA_ZT_GROUP", "COMMENTS", String.valueOf(i), rd.getStringByDI("TA_ZT_LINEMNG", "COMMENTS", 0));
	}
	
	/**
	 * @Description: TODO 生成团价格
	 */
	private void addGroupPrice(BizData rd, String groupID,String orgid, Connection conn) throws SQLException {
		
		BizData rd3 = new BizData();
		String[] rowIDs = rd.getRowIDs("TA_ZT_LINE_PRICES");
		for(int j=0;j<rowIDs.length;j++) {
			
			int priceID = queryMaxIDByPara("TA_ZT_GPRICE", "ID", null);
			String ms = rd.getString("TA_ZT_LINE_PRICES", "PRICE_MS", rowIDs[j]);
			String th = rd.getString("TA_ZT_LINE_PRICES", "PRICE_TH", rowIDs[j]);
			String zd = rd.getString("TA_ZT_LINE_PRICES", "PRICE_ZD", rowIDs[j]);
			String remark = rd.getString("TA_ZT_LINE_PRICES", "remark", rowIDs[j]);
			String SINGLE_ROOM = rd.getString("TA_ZT_LINE_PRICES", "SINGLE_ROOM", rowIDs[j]);
			rd3.add("TA_ZT_GPRICE", "ID", j, priceID);
			rd3.add("TA_ZT_GPRICE", "G_ID", j, groupID);
			rd3.add("TA_ZT_GPRICE", "PRICE_TYPE", j, rd.getString("TA_ZT_LINE_PRICES", "PRICE_TYPE", rowIDs[j]));
			rd3.add("TA_ZT_GPRICE", "PRICE_MS", j, "".equals(ms)?"0":ms);
			rd3.add("TA_ZT_GPRICE", "PRICE_TH", j, "".equals(th)?"0":th);
			rd3.add("TA_ZT_GPRICE", "PRICE_ZD", j, "".equals(zd)?"0":zd);
			rd3.add("TA_ZT_GPRICE", "SINGLE_ROOM", j, "".equals(SINGLE_ROOM)?"0":SINGLE_ROOM);
			rd3.add("TA_ZT_GPRICE", "remark", j, remark);
			rd3.add("TA_ZT_GPRICE", "ORGID", j, orgid);
		}
		//团价格表
		coreDAO.insert(rd3,conn);
		rd3.remove("TA_ZT_GPRICE");
		rd3 = null;
	}

	/**
	 * @Description: TODO 保存线路价格信息
	 */
	public void addLinePrice(BizData rd, int lineBaseID, Connection conn, String orgid) throws SQLException {
		
		BizData rd2 = new BizData();
		String[] rowIDs = rd.getRowIDs("TA_ZT_LINE_PRICES");
		for(int i=0;i<rowIDs.length;i++) {
			
			int priceID = queryMaxIDByPara("TA_ZT_LINE_PRICES", "LINE_PRICE_ID", null);
			String ms = rd.getString("TA_ZT_LINE_PRICES", "PRICE_MS", rowIDs[i]);
			String zd = rd.getString("TA_ZT_LINE_PRICES", "PRICE_ZD", rowIDs[i]);
			String th = rd.getString("TA_ZT_LINE_PRICES", "PRICE_TH", rowIDs[i]);
			String dfc = rd.getString("TA_ZT_LINE_PRICES", "SINGLE_ROOM", rowIDs[i]);
			
			rd2.add("TA_ZT_LINE_PRICES", "LINE_PRICE_ID", rowIDs[i], priceID);
			rd2.add("TA_ZT_LINE_PRICES", "LINE_ID", rowIDs[i], lineBaseID);
			rd2.add("TA_ZT_LINE_PRICES", "PRICE_TYPE", rowIDs[i], rd.getString("TA_ZT_LINE_PRICES", "PRICE_TYPE", rowIDs[i]));
			
			rd2.add("TA_ZT_LINE_PRICES", "PRICE_MS", rowIDs[i], "".equals(ms)?"0":ms);
			rd2.add("TA_ZT_LINE_PRICES", "PRICE_ZD", rowIDs[i], "".equals(zd)?"0":zd);
			rd2.add("TA_ZT_LINE_PRICES", "PRICE_TH", rowIDs[i], "".equals(th)?"0":th);
			rd2.add("TA_ZT_LINE_PRICES", "REMARK", rowIDs[i], rd.getString("TA_ZT_LINE_PRICES", "REMARK", rowIDs[i]));
			rd2.add("TA_ZT_LINE_PRICES", "SINGLE_ROOM", rowIDs[i], dfc.equals("")?"0":dfc);
			rd2.add("TA_ZT_LINE_PRICES", "ORGID", rowIDs[i], orgid);
		}
		
		coreDAO.insert(rd2, conn);
		rd2.remove("TA_ZT_LINE_PRICES");
	}
	
	/**
	  *
	  * @Title: addInsurancePrice
	  * @Description: TODO 线路保险信息关联
	  */
	public void addInsurancePrice(BizData rd, int lineBaseID, Connection conn, String orgid) throws SQLException {
		BizData rd2 = new BizData();
		String[] rowIDs = rd.getRowIDs("TA_ZT_INSURANCE");
		for(int i=0;i<rowIDs.length;i++) {
			String insuranceId = rd.getString("TA_ZT_INSURANCE", "ID", rowIDs[i]);
			if(!"".equals(insuranceId)){
				rd2.add("TA_ZT_LINEANDINSURANCE", "LINE_ID", rowIDs[i], lineBaseID);
				rd2.add("TA_ZT_LINEANDINSURANCE", "INSURANCEID", rowIDs[i], insuranceId);
				rd2.add("TA_ZT_LINEANDINSURANCE","orgid", rowIDs[i], orgid);
			}
		}
		coreDAO.insert(rd2, conn);
		rd2.remove("TA_ZT_LINEANDINSURANCE");
	}
	
	/**
	 * @Description: TODO plan:1是定期发班；2是每天发班
	 */
	private List<Date> compareDate(String plan,BizData rd) {
		List<Date> daysList = new ArrayList<Date>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if("1".equals(plan)){
			
			//定期发班,计算日期
			String bDate = rd.getString("b_date");
			String eDate = rd.getString("e_date");
			String[] weekRows = rd.getRowIDs("cycle");
			Date dateBegin = new Date();
			Date dateEnd = new Date();
			try {
				dateBegin = sdf.parse(bDate);
				dateEnd = sdf.parse(eDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Calendar calendar = Calendar.getInstance();
			//开始日期、结束日期之间的每个日期是否是页面上选中的日期
			while(dateBegin.before(dateEnd) || dateBegin.equals(dateEnd)){
				
				calendar.setTime(dateBegin);
				for(int i=0;i<weekRows.length;i++){
					
					String weekDay = rd.getString("cycle", "week", weekRows[i]);
					int dayOfWeekFromPage = Integer.parseInt(weekDay);//页面上传过来的星期几
					int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);//开始日期后的某个日期在一周中是星期几。
					if(dayOfWeek == dayOfWeekFromPage) {
						
						daysList.add(dateBegin);
					}
				}
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				dateBegin = calendar.getTime();
			}
		}else{
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			//每天发班
			String bDate = rd.getString("b_date");
			String eDate = rd.getString("e_date");
			Date dateBegin = new Date();
			Date dateEnd = new Date();
			try {
				dateBegin = sdf2.parse(bDate);
				dateEnd = sdf2.parse(eDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Calendar calendar = Calendar.getInstance();
			//开始日期、结束日期之间的每个日期是否是页面上选中的日期
			while(dateBegin.before(dateEnd) || dateBegin.equals(dateEnd)){
				calendar.setTime(dateBegin);
				daysList.add(dateBegin);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				dateBegin = calendar.getTime();
			}
		}
		
		return daysList;
	}
	
	/**
	 * @Description: TODO 插入供用户修改时查看发班计划详细的数据
	 * @Description: TODO 供修改时使用，没有业务意义
	 */
	private void insertLineTmp(Connection conn, BizData rd, int lineID, String b, String e, String orgid) throws SQLException {
		
		BizData rd2 = new BizData();
		String[] rowIDs = rd.getRowIDs("cycle");
		
		int rows = rowIDs.length;
		if(rows != 0) {
			for (int i = 0; i < rows; i++) {

				String weekDay = rd.getString("cycle", "week", rowIDs[i]);
				rd2.add("TA_ZT_FBJH_TMP", "line_id", i, lineID);
				rd2.add("TA_ZT_FBJH_TMP", "b_date", i, b);
				rd2.add("TA_ZT_FBJH_TMP", "e_date", i, e);
				rd2.add("TA_ZT_FBJH_TMP", "weeks", i, weekDay);
				rd2.add("TA_ZT_FBJH_TMP", "orgid", i, orgid);
			}
		}else{
			rd2.add("TA_ZT_FBJH_TMP", "line_id", 0 ,lineID);
			rd2.add("TA_ZT_FBJH_TMP", "b_date", 0, b);
			rd2.add("TA_ZT_FBJH_TMP", "e_date", 0, e);
			rd2.add("TA_ZT_FBJH_TMP", "weeks", 0, -1);
			rd2.add("TA_ZT_FBJH_TMP", "orgid", 0, orgid);
		}
		coreDAO.insert(rd2, conn);
		rd2.remove("ta_linePlanDataTmp");
	}
	
	/**
	 * @Description: TODO 线路修改
	 */
	public int update(BizData rd,BizData sd) {

		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String dayCounts = rd.getStringByDI("TA_ZT_LINEMNG", "DAY_COUNTS", 0);
		if("".equals(dayCounts)){dayCounts="0";}
		String plan = rd.getStringByDI("TA_ZT_LINEMNG", "plan", 0);
		String orgid =sd.getString("orgid");
		Connection conn = coreDAO.getConnection();
		try {

			coreDAO.beginTrasct(conn);
			String lineID = rd.getStringByDI("TA_ZT_LINEMNG", "LINE_ID", 0);
			rd.add("lineID", lineID);
			//delete aside of line
			deleteLineInfo4LineUpdate(lineID, conn, orgid);
			//add aside of line
			addLineInfo4LineUpdate(rd, conn, lineID, orgid);
			
			queryNotOrderNo4LineGroup(lineID, rd, sd.getString("orgid"));//have orderNO
			queryHasOrderNo4LineGroup(lineID, rd, sd.getString("orgid"));//not orderNO
			
			// 团队及其周边信息的删除
			deleteGroupInfo(rd.getString("groupWhereCase"), conn, orgid);
			List<Date> daysList = new ArrayList<Date>();
			daysList = compareDate(plan, rd);
			for (int i = 0; i < daysList.size(); i++) {
				
				Date selectedDate = (Date) daysList.get(i);
				String selectDateStr = sdf2.format(selectedDate);
				for(int j=0;j<rd.getTableRowsCount("hasOrderNO");j++){
					String planDate = rd.getStringByDI("hasOrderNO", "begin_date", j);
					String lineid = rd.getStringByDI("hasOrderNO", "line_id", j);
					if(lineid.equals(lineID) && planDate.equals(selectDateStr))
						daysList.remove(i);
				}
			}
			for (int i = 0; i < daysList.size(); i++) {

				Date selectedDate = (Date) daysList.get(i);
				
				//团号前缀
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
				String gIDStr = createGroupNO(prefix, selectedDate, "ta_zt_group", "id", Integer.parseInt(sd.getString("orgid")), conn);
				c.setTime(selectedDate);
				c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(dayCounts));
				Date e = c.getTime();

				// 添加数据到BizData对象(团信息)中
				addDate2BizData(rd, sd, gIDStr, selectedDate, e, i, Integer .parseInt(lineID), dayCounts);

				// 更新团线路明细
				updateGroupBlob(gIDStr, conn, rd, orgid);

				// 添加团价格
				addGroupPrice(rd, gIDStr,orgid, conn);
			}
			// 添加团基本信息
			coreDAO.insert("TA_ZT_GROUP", rd, conn);

			// 线路基本信息表更新
			String jd = rd.getStringByDI("TA_ZT_LINEMNG", "jd", 0);
			if("".equals(jd))
				rd.add("TA_ZT_LINEMNG", "jd", "0");
			String gw = rd.getStringByDI("TA_ZT_LINEMNG", "gw", 0);
			if("".equals(gw))
				rd.add("TA_ZT_LINEMNG", "gw", "0");
			coreDAO.update("TA_ZT_LINEMNG", rd, conn);
			
			coreDAO.commitTrasct(conn);
			
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (NumberFormatException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
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
		
		return 872;
	}
	
	/**
	 * @Description: TODO 查询一条线路所有没有下过订单的团队信息
	 */
	private void queryNotOrderNo4LineGroup(String lineID, BizData rd, String orgid) throws SQLException {
		
		String sql = "select b.id,b.begin_date,b.line_id\n" +
				"from ta_zt_group b where b.id not in (\n" +
				"select g.id\n" +
				"from TA_ZT_GROUP g,ta_zt_ykorder o\n" +
				"where g.id=o.tid\n" +
				"and g.line_id="+lineID+" and g.orgid='"+orgid+"' \n" +
				") and b.line_id="+lineID+" and b.orgid='"+orgid+"'";
		coreDAO.executeQuery(sql, "rsGroup", rd);
		String dateWhereCase = "";
		String groupWhereCase = "";
		for(int i=0;i<rd.getTableRowsCount("rsGroup");i++) {
			
			dateWhereCase += "to_date('"+rd.getStringByDI("rsGroup", "begin_date", i)+"','yyyy-mm-dd'),";
			groupWhereCase += "'"+rd.getStringByDI("rsGroup", "id", i)+"',";
		}
		if(!"".equals(dateWhereCase))
			dateWhereCase = dateWhereCase.substring(0, dateWhereCase.length()-1);
		rd.add("dateWhereCase", dateWhereCase);
		
		if(!"".equals(groupWhereCase))
			groupWhereCase = groupWhereCase.substring(0, groupWhereCase.length()-1);
		rd.add("groupWhereCase", groupWhereCase);
	}
	
	/**
	 * @Description: TODO 删除线路相关的边缘表
	 */
	private void deleteLineInfo4LineUpdate(String lineID, Connection conn, String orgid) throws SQLException, NumberFormatException, UnsupportedEncodingException {
		
		BizData data = new BizData();
		//线路表大字段删除
		data.add("TA_ZT_LINEMNGBLOB", "XLID", lineID);
		data.add("TA_ZT_LINEMNGBLOB", "orgid", orgid);
		coreDAO.delete(data, conn);
		data.remove("TA_ZT_LINEMNGBLOB");
		
		//删除发班计划日期(仅缓存数据用)
		data.add("TA_ZT_FBJH_TMP", "LINE_ID", lineID);
		data.add("TA_ZT_FBJH_TMP", "orgid", orgid);
		coreDAO.delete(data, conn);
		data.remove("TA_ZT_FBJH_TMP");
		
		//删除线路行程明细
		data.add("TA_ZT_LINEDETAIL", "LINEID", lineID);
		data.add("TA_ZT_LINEDETAIL", "orgid", orgid);
		coreDAO.delete(data, conn);
		data.remove("TA_ZT_LINEDETAIL");
		
		//删除线路价格
		data.add("TA_ZT_LINE_PRICES", "LINE_ID", lineID);
		data.add("TA_ZT_LINE_PRICES", "orgid", orgid);
		coreDAO.delete(data, conn);
		data.remove("TA_ZT_LINE_PRICES");
		
		//删除线路集合地点
		data.add("TA_ZT_GATHER", "LINE_ID", lineID);
		data.add("TA_ZT_GATHER", "orgid", orgid);
		coreDAO.delete(data, conn);
		data.remove("TA_ZT_GATHER");
		
		//删除线路保险信息
		data.add("TA_ZT_LINEANDINSURANCE", "LINE_ID", lineID);
		data.add("TA_ZT_LINEANDINSURANCE", "orgid", orgid);
		coreDAO.delete(data, conn);
		data.remove("TA_ZT_LINEANDINSURANCE");
		
		data = null;
	}
	
	/**
	 * @Description: TODO 线路周边相关信息的添加
	 */
	private void addLineInfo4LineUpdate(BizData rd, Connection conn, String lineID, String orgid) throws NumberFormatException, UnsupportedEncodingException, SQLException{

		String bDate = rd.getString("b_date");
		String eDate = rd.getString("e_date");
		//线路表大字段添加
		addLineBlob(rd, conn, Integer.parseInt(lineID),orgid);
		//线路发班计划缓存表
		insertLineTmp(conn, rd, Integer.parseInt(lineID), bDate, eDate, orgid);
		//添加线路明细表
		addLineDetail(rd, conn, Integer.parseInt(lineID), orgid);
		// 保险价格表
		addInsurancePrice(rd, Integer.parseInt(lineID), conn, orgid);
		//添加线路价格信息
		addLinePrice(rd, Integer.parseInt(lineID), conn, orgid);
		//添加线路集合地点
		addGather(rd, conn, Integer.parseInt(lineID), orgid);
	}
	
	/**
	 * @Description: TODO 根据团号删除团价格
	 */
	private void deleteGroupPrice(String groupCase, Connection conn, String orgid) throws SQLException{
		String sql = "delete from TA_ZT_GPRICE where G_ID in (" + groupCase + ") and orgid='"+orgid+"'";
		coreDAO.executeUpdate(sql, conn);
	}
	
	/**
	 * @Description: TODO 根据团号删除团队行程明细
	 */
	private void deleteGroupDetail(String groupCase, Connection conn, String orgid) throws SQLException {
		String sql = "delete from TA_ZT_LINEDETAI4G d where d.TID in (" + groupCase + ") and d.orgid='"+orgid+"'";
		coreDAO.executeUpdate(sql, conn);
	}
	
	/**
	 * @Description: TODO 根据团号删除团队信息
	 */
	private void deleteGroup(String groupCase, Connection conn, String orgid) throws SQLException {
		String sql = "delete from TA_ZT_GROUP where id in (" + groupCase +") and orgid='"+orgid+"'";
		coreDAO.executeUpdate(sql, conn);
	}
	
	/**
	 * @Description: TODO 删除团队周边相关信息，如团行程明细、团队价格及团队信息
	 */
	private void deleteGroupInfo(String groupCase, Connection conn, String orgid) throws SQLException{
		
		deleteGroupPrice(groupCase, conn, orgid);
		deleteGroupDetail(groupCase, conn, orgid);
		deleteGroup(groupCase, conn, orgid);
	}
	
	/**
	 * @Description: TODO 根据线路ID查询所有产生了订单的团队信息
	 */
	private void queryHasOrderNo4LineGroup(String lineID,BizData rd, String orgid) throws SQLException{
		
		String sql = "select g.id,g.line_id,g.begin_date\n" +
				"from TA_ZT_GROUP g,ta_zt_ykorder o\n" +
				"where g.id=o.tid\n" +
				"and g.line_id="+lineID+" and g.orgid='"+orgid+"'";
		coreDAO.executeQuery(sql, "hasOrderNO", rd);
	}
}
