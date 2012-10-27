package com.trm.zttdMng;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.trm.zttdMng.GroupLineDetail;

public class ZtGroupMngBLC extends DBBLC {
 public ZtGroupMngBLC(){
	 this.entityName="TA_ZT_GROUP";
 }

	public int queryByTID(BizData rd, BizData sd){
		
		rd.add("", rd.getStringByDI("TA_ZT_JHJIAD", "TID", 0));
		try {
			query(rd, sd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 92;
	}
	
	/**
	 * queryTrave(查询旅行社信息实现3级省市旅行社联动)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * 
	 * @param rd
	 * @param sd
	 * @return
	 * @return_type (int)
	 * @exception
	 * @since 1.0.0
	 */
	public int queryTrave(BizData rd, BizData sd) {
		String sql = "select t.travel_agc_id,x.pid,t.city_id,t.cmpny_name,x.name \n"
				+ "from ta_travelagency t,xzqh x \n" + "where x.id=t.city_id";

		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		StringBuffer sqlBuff = new StringBuffer();  //获取系统当前时间 - 格式化日期格式  - 拼接 4位升序序列 - 生成新团号
		sqlBuff
				.append(
						"select nvl(max(substr(id,11,4)),1000)+1 id from TA_ZT_GROUP b where substr(id,3,8) = '")
				.append(sdf.format(dt)).append("'");
		String xlid=rd.getStringByDI("TA_ZT_LINEMNG", "XLID", 0);
		String sql2 = "select p.b_date,p.e_date from TA_ZT_fbjhtmp p,TA_ZT_linemng g \n"+
					  "where p.xlid=g.xlid \n" +
					  "and g.xlid = '"+xlid+"'";
		try {
			coreDAO.executeQuery(sqlBuff.toString(), "TraveList", rd);
			coreDAO.executeQuery(sql, "TraveList", rd);
			coreDAO.executeQuery(sql2, "TraveList2", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * 
	 * @Title: queryGuideInfo
	 * @Description: (根据团ID查询导游是否已业务计调)
	 * @param @param rd
	 * @param @param sd
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	public int queryGuideInfo(BizData rd, BizData sd) {
		String sql = "select count(d.id) isNull\n"
				+ "from TA_ZT_jhdy d,TA_ZT_group g\n" + "where d.tid=g.id\n"
				+ "and g.id='" + rd.getStringByDI("TA_ZT_GROUP", "ID", 0) + "'";
		
		String sql2="select count(d.id) isNull \n"+
		" from TA_ZT_jhhotel d,TA_ZT_group g \n" + 
		"where d.tid=g.id \n" +
		"and g.id='" + rd.getStringByDI("TA_ZT_GROUP", "ID", 0) + "'";
		
		String sql3="select sum(d.dylk) dylk,sum(d.dff) dff,d.dyxm from TA_ZT_jhdy d \n"+
		"where d.TID='"+rd.getStringByDI("TA_ZT_GROUP", "ID", 0)+"' group by d.dyxm";
		
		String sql4 = "select g.id,l.LINE_ID,LINE_NAME from TA_ZT_group g,TA_ZT_linemng l,drmuser u\n"
			+ "where g.LINE_ID=l.LINE_ID and u.userno=g.user_no and g.id='" + rd.getStringByDI("TA_ZT_GROUP", "ID", 0) + "'";
		String sql5 = "select nvl(sum(t.yin_sk),0) ddysk \n"+
		  " from TA_ZT_YKORDER t where t.DD_CONFIRM<>2 and t.tid='"+rd.getStringByDI("TA_ZT_GROUP", "ID", 0)+"'";
		try {
			coreDAO.executeQuery(sql, "GuideInfo", rd);
			coreDAO.executeQuery(sql2, "hotelPlanInfo2", rd);
			coreDAO.executeQuery(sql3, "selectGuideFY2", rd);
			coreDAO.executeQuery(sql4,"selectLineInfo",rd);
			coreDAO.executeQuery(sql5,"selectYsk",rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}


	/**
	 * insertGroup(插入新建团基本信息,以及团队价格,团组团社信息)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * 
	 * @param rd
	 * @param sd
	 * @return
	 * @throws SQLException
	 * @return_type (int)
	 * @exception
	 * @since 1.0.0
	 */
	public int insertGroup(BizData rd, BizData sd) throws SQLException {
		Connection conn = coreDAO.getConnection();
		String groupID = rd.getStringByDI("TA_ZT_GROUP", "ID", 0);//获取登团团号
		String groupClass = rd.getString("action");
		if(null == groupID || "".equals(groupID))
     	return -100;
		try {
			coreDAO.beginTrasct(conn);
			rd.add("groupID", groupID);//供确认件页面使用
			Date dt = new Date();//生成登团时间,即系统当前时间
			rd.add("TA_ZT_GROUP", "ID", 0, groupID);
			rd.add("TA_ZT_GROUP", "DTRQ", 0, dt);
			coreDAO.insert("TA_ZT_GROUP", rd, conn);//添加团基本信息
			rd.remove("TA_ZT_GROUP");
			if("td".equals(groupClass)){
				insertGroupBlob(String.valueOf(groupID), conn, rd);//插入团队团行程明细
			}else{
				addGroupDetail(groupID, rd, conn);//添加散客团行程明细
			}
			addGroupPrice(groupID, rd, conn);//添加团队价格
			addGroupTrave(groupID, rd, conn);//添加组团社
			coreDAO.commitTrasct(conn);
		} catch (Exception e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 1;
	}
	
	//添加团行程明细
	public void addGroupDetail(String TID, BizData rd, Connection conn) throws SQLException{
		String[] rowIDs=rd.getRowIDs("TA_ZT_LINEDETAI4G");
		for(int i=0;i<rowIDs.length;i++){
			int id=this.queryMaxIDByPara("TA_ZT_LINEDETAI4G", "ID", null);
			rd.add("TA_ZT_LINEDETAI4G", "ID", rowIDs[i], id);
			rd.add("TA_ZT_LINEDETAI4G", "TID", rowIDs[i], TID);
		}
		coreDAO.insert("TA_ZT_LINEDETAI4G", rd, conn);
		rd.remove("TA_ZT_LINEDETAI4G");
	}
	
	//删除行程明细
	public void delGroupDetail(String TID, Connection conn) throws SQLException{
		BizData data=new BizData();
		data.add("TA_ZT_LINEDETAI4G", "TID", TID);
		coreDAO.delete(data, conn);
		data.remove("TA_ZT_LINEDETAI4G");
	}
	
	//查询团行程明细
	public int queryGroupDetail(BizData rd, BizData sd) throws SQLException{
		String groupId=rd.getStringByDI("TA_ZT_LINEDETAI4G", "TID", 0);
		String sql="select d.* from TA_ZT_linedetai4g d where d.tid='"+groupId+"'";
		sql+=" order by d.rq desc";
		coreDAO.executeQuery(sql, "groupDetailInfo", rd);
		
		List list=new ArrayList();
		for(int i=0;i<rd.getTableRowsCount("groupDetailInfo");i++){
			GroupLineDetail groupDetail=new GroupLineDetail();
			groupDetail.setRq(rd.getStringByDI("groupDetailInfo", "rq", i));
			groupDetail.setXcmx(rd.getStringByDI("groupDetailInfo", "xcmx", i));
			groupDetail.setBreakfast(rd.getStringByDI("groupDetailInfo", "breakfast", i));
			groupDetail.setCmeal(rd.getStringByDI("groupDetailInfo", "cmeal", i));
			groupDetail.setSupper(rd.getStringByDI("groupDetailInfo", "supper", i));
			groupDetail.setZs(rd.getStringByDI("groupDetailInfo", "zs", i));
			list.add(groupDetail);
		}
		rd.add("groupDetail", list);
		return 1;
	}

	/**插入团队团明细BLOB
	 * @param groupID
	 * @param conn
	 * @param rd
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	public int insertGroupBlob(String groupID, Connection conn, BizData rd) throws SQLException, UnsupportedEncodingException {
		String lineDetail = rd.getString("TA_ZT_LINEDETAILTD","XCMX",0);
		String sql = "INSERT INTO TA_ZT_LINEDETAILTD (TID,XCMX) VALUES(?,?)";
		PreparedStatement preStmt = conn.prepareStatement(sql);
		preStmt.setString(1, groupID);
		preStmt.setBytes(2, lineDetail.getBytes("GBK"));
		preStmt.executeUpdate();
		preStmt.close();
		preStmt = null;
		return 999;
	}
	/**修改团队团明细
	 * @param groupID
	 * @param conn
	 * @param rd
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	public int updateGroupBlob(String groupID, Connection conn, BizData rd) throws SQLException, UnsupportedEncodingException {
		String lineDetail = rd.getString("TA_ZT_LINEDETAILTD","XCMX",0);
		String sql = "UPDATE TA_ZT_LINEDETAILTD SET XCMX = ? WHERE TID = '"+groupID+"'";
		PreparedStatement preStmt = conn.prepareStatement(sql);
		preStmt.setBytes(1, lineDetail.getBytes("GBK"));
		preStmt.executeUpdate();
		preStmt.close();
		preStmt = null;
		return 999;
	}

	/**
	 * updateGroupInfo(修改团基本信息)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * 
	 * @return
	 * @return_type (int)
	 * @exception
	 * @since 1.0.0
	 */
	public int updateGroupInfo(BizData rd, BizData sd) {
		Connection conn = coreDAO.getConnection();
		String groupID = rd.getStringByDI("TA_ZT_GROUP","ID",0);
		String groupClass = rd.getString("action");
		try {
			coreDAO.beginTrasct(conn);
			rd.addAttr("TA_ZT_GROUP","ID","0","oldValue",groupID);
			String jd=rd.getStringByDI("TA_ZT_GROUP", "JD", 0);
			if("".equals(jd)){
				rd.add("TA_ZT_GROUP", "JD", "0");
			}
			String gw=rd.getStringByDI("TA_ZT_GROUP", "GW", 0);
			if("".equals(gw)){
				rd.add("TA_ZT_GROUP", "GW", "0");
			}
			coreDAO.update("TA_ZT_GROUP", rd, conn);
			delGroupPrice(groupID, conn);// 删除团队价格
			addGroupPrice(groupID, rd, conn);// 添加团队价格
			delGroupTrave(groupID, conn);// 删除组团社
			addGroupTrave(groupID, rd, conn);// 添加组团社
			if("td".equals(groupClass)){
				updateGroupBlob(String.valueOf(groupID), conn, rd);//行程明细BLOB
			}else{
				//删除散客团行程明细
				delGroupDetail(groupID, conn);
				//添加团行程明细
				addGroupDetail(groupID, rd, conn);
			}
			coreDAO.commitTrasct(conn);
		} catch (Exception e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 999;
	}
	
	/**
	 * queryTrave(查询旅行社信息实现3级省市旅行社联动)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * 
	 * @param rd
	 * @param sd
	 * @return
	 * @return_type (int)
	 * @exception
	 * @since 1.0.0
	 */
	public int queryAllTrave(BizData rd, BizData sd) {
		String sql = "select t.travel_agc_id,x.pid,t.city_id,t.cmpny_name,x.name \n"
				+ "from ta_travelagency t,xzqh x \n" + "where x.id=t.city_id";

		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		StringBuffer sqlBuff = new StringBuffer();  //获取系统当前时间 - 格式化日期格式  - 拼接 4位升序序列 - 生成新团号
		sqlBuff
				.append(
						"select nvl(max(substr(id,11,4)),1000)+1 id from TA_ZT_GROUP b where substr(id,3,8) = '")
				.append(sdf.format(dt)).append("'");
		try {
			coreDAO.executeQuery(sqlBuff.toString(), "TraveList", rd);
			coreDAO.executeQuery(sql, "TraveList", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * 团队列表（非全国大散）
	 */
	public void initAllGroupList(BizData rd, BizData sd) {
		
		String groupID = rd.getString("groupID");// 获取查询条件:团号
		String bDate = rd.getString("bDate");// 获取查询条件:开始时间
		String xlmc = rd.getString("xlmc");// 获取查询条件:线路名称
		String state = rd.getString("state");
		String isBack = rd.getString("isBack");
		String pageNOStr = rd.getString("pageNO");// 获取分页
		String pageSizeStr = rd.getString("pageSize");// 获取分页
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		String sql = "select * from ( \n" +
				"select a.id,a.xlmc,a.begin_date,a.end_date,a.dtrq,a.ADULT_COUNT,a.CHILDREN_COUNT,a.state,nvl(b.isback,'N') isBack \n"+
				" from ( \n"+
				"select g.id,g.xlmc,g.BEGIN_DATE,g.END_DATE,g.dtrq,g.ADULT_COUNT,g.CHILDREN_COUNT,g.state \n"+
				"from TA_ZT_group g\n"+
				"where g.xlid is null ";
		if(!"".equals(state))
			sql += "and g.state = "+state;
		sql += " and g.user_no="+sd.getString("userno")+"  \n";
		if (!"".equals(groupID)) { // 如果团号不为空
			sql += "and g.id like '%" + groupID + "%' \n";
		}
		if (!"".equals(bDate)) { // 如果开团时间不为空
			sql += "and g.BEGIN_DATE=to_date('" + bDate + "','yyyy-mm-dd') \n";
		}
		if (!"".equals(xlmc))
			sql += "and g.xlmc like '%"+xlmc+"%' \n";
		sql += ") a, (select f.tid,f.isback \n"+
				"from ta_flow f,TA_FLOWDEFINITION d \n"+
				"where f.definitionid=d.definitionid \n";
		sql += "and d.definitionid='ztywsh') b \n"+
				"where a.id=b.tid(+) \n";
		sql +="ORDER BY a.DTRQ desc \n" +
				") c \n";
		if(!"".equals(isBack))
			sql += "where c.isback ='"+isBack+"'";
		try {
			coreDAO.executeQueryPage(sql, "rsAllGroupList", pageNO, pageSize, rd);
			sql = "select g.id,j.jglx,j.jg,d.dmsm1 from TA_ZT_group g,TA_ZT_tdjg j,dmsm d\n"
					+ "where g.id=j.tid \n"
					+ "and d.dmz=j.jglx \n"
					+ "and d.dmlb=4";
			coreDAO.executeQuery(sql, "rsAllGroupPrice", rd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *全国大散团队列表
	 */
	public void scattered4GroupList(BizData rd, BizData sd) {
		
		String groupID = rd.getString("groupID");// 获取查询条件:团号
		String bDate = rd.getString("bDate");// 获取查询条件:开始时间
		String xlmc = rd.getString("xlmc");// 获取查询条件:线路名称
		String state = rd.getString("state");
		String isBack = rd.getString("isBack");
		String pageNOStr = rd.getString("pageNO");// 获取分页
		String pageSizeStr = rd.getString("pageSize");// 获取分页
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		String sql = "select * from ( \n" +
				"select a.id,a.xlid,a.xlmc,a.begin_date,a.end_date,a.dtrq,a.ADULT_COUNT,a.CHILDREN_COUNT,a.state,nvl(b.isback,'N') isBack \n"+
				" from ( \n"+
				"select g.id,g.xlid,g.xlmc,g.BEGIN_DATE,g.END_DATE,g.dtrq,g.ADULT_COUNT,g.CHILDREN_COUNT,g.state \n"+
				"from TA_ZT_group g\n"+
				"where g.user_no="+sd.getString("userno")+"  \n";
		if(!"".equals(state))
			sql += "and g.state = "+state;
		if (!"".equals(groupID)) { // 如果团号不为空
			sql += "and g.id like '%" + groupID + "%' \n";
		}
		if (!"".equals(bDate)) { // 如果开团时间不为空
			sql += "and g.BEGIN_DATE=to_date('" + bDate + "','yyyy-mm-dd') \n";
		}
		if (!"".equals(xlmc))
			sql += "and g.xlmc like '%"+xlmc+"%' \n";
		sql += ") a, (select f.tid,f.isback \n"+
				"from ta_flow f,TA_FLOWDEFINITION d \n"+
				"where f.definitionid=d.definitionid \n";
		sql += "and d.definitionid='ztywsh') b,TA_ZT_linemng l \n"+
				"where a.id=b.tid(+) and l.xlid=a.xlid\n";
		sql +="ORDER BY a.DTRQ desc \n" +
				") c \n";
		if(!"".equals(isBack))
			sql += "where c.isback ='"+isBack+"'";
		try {
			coreDAO.executeQueryPage(sql, "rsAllGroupList", pageNO, pageSize, rd);
			sql = "select g.id,j.jglx,j.jg,d.dmsm1 from TA_ZT_group g,TA_ZT_tdjg j,dmsm d\n"
					+ "where g.id=j.tid \n"
					+ "and d.dmz=j.jglx \n"
					+ "and d.dmlb=4";
			coreDAO.executeQuery(sql, "rsAllGroupPrice", rd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * addGroupPrice(//添加团队价格)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * 
	 * @param groupID
	 * @param rd
	 * @param conn
	 * @throws SQLException
	 * @return_type (void)
	 * @exception
	 * @since 1.0.0
	 */
	public void addGroupPrice(String groupID, BizData rd, Connection conn) throws SQLException {
		String[] rowIDs = rd.getRowIDs("TA_ZT_TDJG");
		for (int i = 0; i < rowIDs.length; i++) {
			int priceID = queryMaxIDByPara("TA_ZT_TDJG", "ID", null);
			rd.add("TA_ZT_TDJG", "ID", i, priceID);
			rd.add("TA_ZT_TDJG", "TID", i, groupID);
		}
		coreDAO.insert("TA_ZT_TDJG", rd, conn);
		rd.remove("TA_ZT_TDJG");
	}

	/**
	 * addGroupTrave(//添加组团社)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * 
	 * @param groupID
	 * @param rd
	 * @param conn
	 * @throws SQLException
	 * @throws ParseException 
	 * @return_type (void)
	 * @exception
	 * @since 1.0.0
	 */
	public void addGroupTrave(String groupID, BizData rd, Connection conn) throws SQLException, ParseException {
		
		String[] rowIDs = rd.getRowIDs("TA_ZT_TZTS");
		for (int i = 0; i < rowIDs.length; i++) {
			int ztsID = queryMaxIDByPara("TA_ZT_TZTS", "ID", null);
			rd.add("TA_ZT_TZTS", "ID", rowIDs[i], ztsID);
			rd.add("TA_ZT_TZTS", "TID", rowIDs[i], groupID);
		}
		coreDAO.insert("TA_ZT_TZTS", rd, conn);
		rd.remove("TA_ZT_TZTS");
	}

	/**
	 * delGroupPrice(//删除团队价格)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * 
	 * @param groupID
	 * @param conn
	 * @throws SQLException
	 * @return_type (void)
	 * @exception
	 * @since 1.0.0
	 */
	private void delGroupPrice(String groupID, Connection conn)
			throws SQLException {

		BizData data = new BizData();
		data.add("TA_ZT_TDJG", "TID", groupID);
		coreDAO.delete(data, conn);
	}

	/**
	 * delGroupTrave(//删除组团社)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * 
	 * @param groupID
	 * @param conn
	 * @throws SQLException
	 * @return_type (void)
	 * @exception
	 * @since 1.0.0
	 */
	private void delGroupTrave(String groupID, Connection conn)
			throws SQLException {

		BizData data = new BizData();
		data.add("TA_ZT_TZTS", "TID", groupID);
		coreDAO.delete(data, conn);
	}
	
	/**
	 * 
	 * @Title: print_getPlanInfo
	 * @Description: (查询团任务单)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int print_getPlanInfo(BizData rd,BizData sd){
		String id=rd.getString("id");
		//团基本信息
		String GroupInfo="select g.id,nvl(g.ADULT_COUNT,0) ADULT_COUNT,nvl(g.CHILDREN_COUNT,0) CHILDREN_COUNT,g.dtrq, \n"+
			"g.COMMENTS from TA_ZT_group g where g.id='"+id+"'";
		
		//导游信息
		String GuideInfo = "select a.userno,a.empname,a.emptel \n"+
						   " from (select r.userno,e.empname,e.emptel from drmuser r,hremployee e where r.empid=e.empid) a,ta_zt_jhdy d \n"+
						   "where d.dyid=a.userno";
		//导游费用合计
		String Guidefy="select sum(d.dylk) dylk,sum(d.dff) dff from TA_ZT_jhdy d where d.tid='"+id+"'";
		
		//车辆信息
		String CarInfo="select a.qdje,a.xfje,a.sjxm,a.sjdh,a.begintime,a.endtime,a.jtdd,a.stdd,b.car_code,b.car_type,b.driver_name,b.driver_phone,c.cmpny_name,d.dmsm1 \n" +
				" from TA_ZT_jhcl a,ta_car b,ta_car_team c,dmsm d \n" +
				" where d.dmlb=13 and a.cd=c.team_id and a.cp=b.car_id and b.car_type=d.dmz and a.tid='"+id+"'";
		
		//酒店信息
		String HotelInfo="select a.id,a.qdxj,a.xfxj,a.rz_time,a.sfzsf,b.hotel_name,b.hotel_bussiness_phone,c.dmsm1,d.name city \n" +
				" from TA_ZT_jhhotel a,ta_hotel b,dmsm c,xzqh d \n" +
				" where c.dmlb=6 and a.jdid=b.hotel_id and a.city=d.id and substr(a.xj,instr(a.xj,'-')+1,length(a.xj))=c.dmz and a.tid='"+id+"'";

		//酒店价格
		String HotelPrice="select c.dmsm1,b.jdjhid,b.jg,b.fjs,a.ts from TA_ZT_jhhotel a,TA_ZT_jhhoteljg b,dmsm c \n" +
				"where c.dmlb=10 and b.fjs>0 and a.id=b.jdjhid and b.jglx=c.dmz and a.tid='"+id+"'";
		
		//餐厅信息
		String DinnerInfo="select a.ycrq,a.zcjg,a.zcrs,a.zccs,a.zhcjg,a.zhcrs,a.zhccs,a.qdxjje,a.xfxjje,b.cmpny_name,d.name city \n" +
				" from TA_ZT_jhct a,ta_dining_room b,xzqh d where a.cityid=d.id and a.ct=b.dining_room_id and a.tid='"+id+"' order by a.ycrq";
		
		//景点信息
		String SceneryInfo="select a.id,a.jdid,a.qdxj,a.xfxj,c.cmpny_name,d.name city \n" +
				" from TA_ZT_jhjd a,ta_scenery_point c,xzqh d where a.sfxz='Y' and a.jdid=c.scenery_id and a.csid=d.id and a.tid='"+id+"'";

		//景点价格
		String SceneryPrice="select b.jhid,b.id,b.jgmc,b.jg,b.rs from TA_ZT_jhjd a,TA_ZT_jhjdjg b,dmsm c \n" +
				"where c.dmlb=12 and b.rs>0 and a.id=b.jhid and b.jgmc=c.dmsm1 and a.tid='"+id+"'";
		
		//票务信息
		String TicketInfo="select a.qdxj,a.xfxj,a.lxrdh,b.cc,b.cfz,b.mdz,b.begintime,b.zs,b.dj,b.sxf,c.cmpny_name,c.business_mobile, \n" +
				"d.dmsm1 from TA_ZT_jhpw a,TA_ZT_jhpwmx b,ta_ticket c,dmsm d \n"+
				" where d.dmlb=2 and a.id=b.jhid and a.dgd=c.ticket_id and b.cplx=d.dmz and a.tid='"+id+"'";
		
		//地接社信息
		String TraveInfo="select a.xfje,a.qdje,a.yfzk,a.bz,nvl(a.crrs,0) crrs,nvl(a.etrs,0) etrs,b.cmpny_name,b.person_name from TA_ZT_jhdj a,ta_travelagency b where a.djsid=b.travel_agc_id \n" +
				" and a.tid='"+id+"'";
		
		//购物点
		String ShopInfo="select b.cmpny_name from TA_ZT_jhgw a,ta_shoppoint b where a.sfxz='Y' and a.gwdid=b.shop_point_id and a.tid='"+id+"'";
		
		//加点
		String PlusInfo="select b.jdmc from TA_ZT_jhjiad a,TA_ZT_jhjiadjd b where b.ischeck='Y' and a.id=b.jhid and a.tid='"+id+"'";
		
		//签单与现付总计
		String qdxf="select sum(nvl(qdpwzj,0)+nvl(qdclzj,0)+nvl(qdzszj,0)+nvl(qdctzj,0)+nvl(qdjdzj,0)+nvl(djqdzj,0)) qdzj," +
				" sum(nvl(xfpwzj,0)+nvl(xfclzj,0)+nvl(xfzszj,0)+nvl(xfctzj,0)+nvl(xfjdzj,0)+nvl(djxfzj,0)) xfzj, " +
				" sum(nvl(pwzj,0)+nvl(clzj,0)+nvl(zszj,0)+nvl(ctzj,0)+nvl(jdzj,0)+nvl(djzj,0)) zj from ta_tdjdxxzjb_zt where tid='"+id+"'";
		
		//制定人与审核人信息
		String creatermsg="select u.username,r.rolename,f.creater from drmuser u,drmuserrole l,drmrole r,ta_flow f where f.definitionid='ztywsh'and u.userno=f.creater \n"+
				" and u.userid=l.userid and l.roleid=r.roleid and rownum=1 and f.tid='"+id+"'";
		String ywbzgmsg="select u.username,r.rolename from drmuser u,drmuserrole l,drmrole r,TA_DJ_TSPB d where d.mklb=1 and d.spr=u.userno \n"+
				" and u.userid=l.userid and l.roleid=r.roleid and r.roleid='djbzg' and rownum=1 and d.tid='"+id+"'";
		String kjmsg="select u.username,r.rolename from drmuser u,drmuserrole l,drmrole r,TA_DJ_TSPB d where d.mklb=1 and d.spr=u.userno \n"+
		" and u.userid=l.userid and l.roleid=r.roleid and r.roleid='kuaiji' and rownum=1 and d.tid='"+id+"'";
		try {
			coreDAO.executeQuery(GroupInfo, "GroupInfo", rd);//团信息
			coreDAO.executeQuery(GuideInfo, "GuideInfo", rd);//导游信息
			coreDAO.executeQuery(Guidefy, "Guidefy", rd);//导游费用合计
			coreDAO.executeQuery(CarInfo, "CarInfo", rd);//司机信息
			coreDAO.executeQuery(HotelInfo, "HotelInfo", rd);
			coreDAO.executeQuery(HotelPrice, "HotelPrice", rd);
			coreDAO.executeQuery(DinnerInfo, "DinnerInfo", rd);
			coreDAO.executeQuery(SceneryInfo, "SceneryInfo", rd);
			coreDAO.executeQuery(SceneryPrice, "SceneryPrice", rd);
			coreDAO.executeQuery(TicketInfo, "TicketInfo", rd);
			coreDAO.executeQuery(TraveInfo, "TraveInfo", rd);
			coreDAO.executeQuery(ShopInfo, "ShopInfo", rd);
			coreDAO.executeQuery(qdxf, "qdxfzj", rd);
			coreDAO.executeQuery(creatermsg, "creatermsg", rd);
			coreDAO.executeQuery(ywbzgmsg, "ywbzgmsg", rd);
			coreDAO.executeQuery(kjmsg, "kjmsg", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	//初始化团任务单打印信息列表
	public void initGroupPrintList(BizData rd, BizData sd) {
		String groupID = rd.getString("groupID");// 获取查询条件:团号
		String bDate = rd.getString("bDate");// 获取查询条件:开始时间
		String pageNOStr = rd.getString("pageNO");// 获取分页
		String pageSizeStr = rd.getString("pageSize");// 获取分页
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		String sql = "";

		sql = "select g.id,g.line_id,g.xlmc,g.BEGIN_DATE,g.END_DATE,g.dtrq,g.ADULT_COUNT,g.CHILDREN_COUNT,g.state,u.userno \n"
				+ " from TA_ZT_group g,drmuser u\n"
				+ "where u.userno=g.user_no and g.state>=4 \n";
		if (!"".equals(groupID)) { // 如果团号不为空
			sql += "and g.id like '%" + groupID + "%' \n";
		}
		if (!"".equals(bDate)) { // 如果开团时间不为空
			sql += "and g.BEGIN_DATE=to_date('" + bDate + "','yyyy-mm-dd') \n";
		}
		sql +="ORDER BY g.DTRQ desc";
		try {
			coreDAO.executeQueryPage(sql, "rsGroupPrintList", pageNO, pageSize, rd);
			sql = "select g.id,j.jglx,j.jg,d.dmsm1 from TA_ZT_group g,TA_ZT_tdjg j,dmsm d\n"
					+ "where g.id=j.tid \n"
					+ "and d.dmz=j.jglx \n"
					+ "and d.dmlb=4";
			coreDAO.executeQuery(sql, "rsGroupPrintPrice", rd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//执行打印,修改团状态
	public int doGroupPrint(BizData rd,BizData sd){
		
		String id=rd.getString("id");
		try {
			String sql="update TA_ZT_group g set g.state=4 where g.id='"+id+"'";
			coreDAO.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return 1;
	}
	
	public void rbtGuideAllList(BizData rd, BizData sd) {

			String groupID=rd.getString("groupID");
			String bDate=rd.getString("bDate");
			String xlmc=rd.getString("xlmc");
			String pageNOStr = rd.getString("pageNO");
			String pageSizeStr = rd.getString("pageSize");
			int pageNO = Integer.parseInt(pageNOStr);
			int pageSize = Integer.parseInt(pageSizeStr);
			String sql="";
			//计划指定的那个导游才能报账
			sql="select g.id,g.xlmc,g.begin_date,g.end_date,g.state,d.dyxm \n" +
					" from TA_ZT_group g,TA_ZT_jhdy d\n"+
					" where g.id=d.tid and g.state>=4 \n" +
					" and d.bxr='Y'\n";
			if(!"".equals(groupID)){
				sql+=  " and g.id like '%"+groupID+"%'";
			}
			if(!"".equals(bDate)){
				sql+= " and g.begin_date=to_date ('"+bDate+"','yyyy-mm-dd')";
			}
			if(!"".equals(xlmc)){
				sql+= " and g.xlmc like '%"+xlmc+"%'";
			}
			sql += "order by g.DTRQ desc";
			try {
				coreDAO.executeQueryPage(sql, "queryGuideAllList", pageNO, pageSize, rd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	public int queryBxGuideInfo(BizData rd, BizData sd) {
		String groupID= rd.getStringByDI("TA_ZT_GROUP", "ID", 0);
		String sql="";
		sql="select g.id,g.xlmc,g.begin_date,g.end_date,g.state ,d.dyxm,g.ADULT_COUNT,g.COMMENTS,g.CHILDREN_COUNT \n" 
		+	"  from  TA_ZT_group g,TA_ZT_jhdy d,(select *  from  TA_DJ_TSPB where mklb='2') b \n"
		+	"  where g.id=b.tid(+) \n"
		+	"  and g.id=d.tid(+) \n"
		+	"  and g.id='" + groupID + "'";
		
		String sql2="select sum(d.dylk)dylk,sum(d.dff)dff,sum(d.dyjtf)dyjtf,sum(d.qt)qt \n "+
		"   from TA_ZT_bxdy d \n" +
		" where d.tid='"+groupID+"'";
		
		String sql3 = "select nvl(sum(t.yin_sk),0) ddysk \n"+
					  " from TA_ZT_YKORDER t where t.DD_CONFIRM<>2 and t.tid='"+groupID+"'";
		try {
			coreDAO.executeQuery(sql, "bx_GuideInfo", rd);
			coreDAO.executeQuery(sql2,"allGuideFy",rd);
			coreDAO.executeQuery(sql3,"ykddysk",rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	//查询审批意见
	public int querySPYJInfo(BizData rd,BizData sd) throws SQLException{
		String tid=rd.getStringByDI("TA_ZT_GROUP", "ID", 0);
		String mklb = rd.getString("mklb");
		String shspyj="select t.spyj,u.username from ta_dj_tspb t,drmuser u where t.spr=u.userno and t.mklb='"+mklb+"' and t.tid='"+tid+"'";
		coreDAO.executeQuery(shspyj, "SPYJ", rd);
		return 1;
	}
	
	public int shareParameter(BizData rd,BizData sd){
		
		rd.add("action", rd.getString("action"));
		return 88;
	}
	public void showGroupDetailList(BizData rd,BizData sd) throws SQLException{
		String groupId = rd.getString("groupId");
		String sql = "select g.id tid,y.id,g.begin_date,g.xlmc,nvl(y.yin_sk,0) yin_sk,nvl(y.yi_sk,0) yi_sk,nvl(y.yin_sk,0)-nvl(y.yi_sk,0) wsk,y.cmpny_name \n" +
				     " from ta_zt_ykorder y,ta_zt_group g where 1=1 and y.tid=g.id and y.tid='"+groupId+"'";
		coreDAO.executeQuery(sql, "GroupDetailList", rd);
		 sql= "select m.id,d.dmsm1,p.price_ms,p.price_ms*nvl(p.person_count,0) ms,p.price_th,p.price_th*nvl(p.person_count,0) th,nvl(p.person_count,0) person_count \n"+
					" from TA_ZT_YKORDER m,TA_ZT_GROUP b,TA_ZT_GORDERPRICE p,dmsm d \n"+
					"where m.id=p.orderid and p.price_type=d.dmz and d.dmlb=4  \n"+
					"and m.tid=b.id  \n"+
					"and m.tid='"+ groupId +"'";
     coreDAO.executeQuery(sql, "GroupPriceList", rd);
	}
	
	public void showPeopleList(BizData rd,BizData sd) throws SQLException
	{
		String ddh = rd.getString("ddh");
		
		// *** BEGIN *** SEEV200R003C01B010/111 2012.02.06 hlwei Modify
		// 修改原因: 添加游客性别和是否儿童
//		String sql = "select v.seat_num,v.visitor_nm,y.cmpny_name \n"+
//					 " from ta_zt_visitor v,ta_zt_ykorder y \n"+
//					 "where v.m_id=y.id and v.m_id='"+ ddh +"'";
		 
		String sql = "select v.seat_num,v.visitor_nm,v.sex, v.ischild,y.cmpny_name \n"+
		 			 " from ta_zt_visitor v,ta_zt_ykorder y \n"+
		 			 "where v.m_id=y.id and v.m_id='"+ ddh +"'";
		// ***  END  *** SEEV200R003C01B010/111 2012.02.06 hlwei Modify
		
		coreDAO.executeQuery(sql, "GroupPeopleList", rd);
		      
	}
}
