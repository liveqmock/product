package com.trmdj.businessPlan.groupMng;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**  
 * <b>包名：</b>com.trmdj.businessPlan.groupMng<br/>  
 * <b>文件名：</b>DjgroupMngBLC.java<br/>  
 * <b>版本信息：1.0</b><br/>  
 * <b>日期：</b>2011-7-11-下午01:29:36<br/>  
 * <b>Copyright (c)</b> 2011金索通软件科技有限公司-版权所有<br/>  
 *   
 */
/**
 * 
 * <b>类名称：</b>DjgroupMngBLC<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>Kale<br/>
 * <b>修改人：</b>Kale<br/>
 * <b>修改时间：</b>2011-7-11 下午01:29:36<br/>
 * <b>修改备注：</b><br/>
 * 
 * @version 1.0.0<br/>
 * 
 */

public class DjGroupMngBLC extends DBBLC {
	public DjGroupMngBLC() {
		this.entityName = "TA_DJ_GROUP";
	}
	
	public int djInitAddGroup(BizData rd, BizData sd){
		
		Connection conn = coreDAO.getConnection();
		try{
			createGroupId(rd, sd, conn);
			//行政区划
			coreDAO.select("xzqh", rd, true, conn);
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			try{
				if(null != conn)
					conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 98;
	}
	

	public void createGroupId(BizData rd, BizData sd, Connection conn) throws SQLException {

		String ywlb = rd.getString("yw");
		ywlb = ywlb.equals("1")?"t":"s";
		rd.add("yw", rd.getString("yw"));//1,团队;2,散客
		Date dt = new Date();
		//团号前缀
		rd.add("TA_GROUPNUMROLE", "ORGID", sd.getString("orgid"));
		rd.add("TA_GROUPNUMROLE", "YWLB", ywlb);
		rd.add("TA_GROUPNUMROLE", "YWfl", "d");
		coreDAO.select("TA_GROUPNUMROLE", rd, true, conn);
		rd.remove("TA_GROUPNUMROLE");	
		String prefix = rd.getStringByDI("TA_GROUPNUMROLEs", "GROUPPREFIX", 0);
		String groupId = createGroupNO(prefix, dt, "TA_DJ_GROUP", "ID", Integer.parseInt(sd.getString("orgid")), conn);
		rd.add("TA_DJ_GROUPs", "ID", 0, groupId);
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
		
		rd.add("yw", rd.getString("yw"));
		Connection conn = coreDAO.getConnection();
		String groupID = rd.getStringByDI("TA_DJ_GROUP", "ID", 0);//获取登团团号
		if(null == groupID || "".equals(groupID))
        	return -100;
		try {
			coreDAO.beginTrasct(conn);
			rd.add("groupID", groupID);//供确认件页面使用
			Date dt = new Date();//生成登团时间,即系统当前时间
			rd.add("TA_DJ_GROUP", "ID", 0, groupID);
			rd.add("TA_DJ_GROUP", "DTRQ", 0, dt);
			rd.add("TA_DJ_GROUP", "STATE", 0, "1");
			rd.add("TA_DJ_GROUP", "USERNO", 0, sd.getString("userno"));
			rd.add("TA_DJ_GROUP", "orgid", 0, sd.getString("orgid"));
			rd.add("TA_DJ_GROUP", "userno", 0, sd.getString("userno"));
			coreDAO.insert("TA_DJ_GROUP", rd, conn);//添加团基本信息
			rd.remove("TA_DJ_GROUP");
			
			addGroupTrave(groupID, rd, sd, conn);//添加组团社
			addGroupBlob(groupID, rd, conn, sd);//添加团行程明细
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
	
	// 查询团队明细
	public void queryGroupDetail(BizData rd, BizData sd, Connection conn) throws SQLException {
		String groupId = rd.getStringByDI("TA_DJ_LINEDETAI4G", "TID", 0);
		String sql = "select d.* from TA_DJ_LINEDETAI4G d where d.tid='" + groupId + "' and d.orgid="+sd.getString("orgid");
		sql += " order by d.rq";
		coreDAO.executeQuery(sql, "groupDetailInfo", rd);

		List<GroupLineDetail> list = new ArrayList<GroupLineDetail>();
		for (int i = 0; i < rd.getTableRowsCount("groupDetailInfo"); i++) {
			GroupLineDetail groupDetail = new GroupLineDetail();
			groupDetail.setRq(rd.getStringByDI("groupDetailInfo", "rq", i));
			groupDetail.setXcmx(rd.getStringByDI("groupDetailInfo", "xcmx", i));
			groupDetail.setBreakfast(rd.getStringByDI("groupDetailInfo", "breakfast", i));
			groupDetail.setCmeal(rd.getStringByDI("groupDetailInfo", "cmeal", i));
			groupDetail.setSupper(rd.getStringByDI("groupDetailInfo", "supper", i));
			groupDetail.setZs(rd.getStringByDI("groupDetailInfo", "zs", i));
			groupDetail.setCb(rd.getStringByDI("groupDetailInfo", "cb", i));
			groupDetail.setZsbz(rd.getStringByDI("groupDetailInfo", "zsbz", i));
			list.add(groupDetail);
		}

		rd.add("groupDetail", list);
	}

	/**
	 * 添加团队行程明细
	 * 
	 * @param XLID
	 * @param rd
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	public int addGroupBlob(String groupID, BizData rd, Connection conn, BizData sd)
			throws SQLException, UnsupportedEncodingException {
		String[] bRows = rd.getRowIDs("TA_DJ_LINEDETAI4G");
		for(int i = 0; i < bRows.length; i++){
			int Id = queryMaxIDByPara("TA_DJ_LINEDETAI4G", "ID", null);
			String XCMX = rd.getString("TA_DJ_LINEDETAI4G", "XCMX", bRows[i]);
			String rq = rd.getString("TA_DJ_LINEDETAI4G", "rq", bRows[i]);
			String breakfast = rd.getString("TA_DJ_LINEDETAI4G", "breakfast", bRows[i]);
			String cmeal = rd.getString("TA_DJ_LINEDETAI4G", "cmeal", bRows[i]);
			String supper = rd.getString("TA_DJ_LINEDETAI4G", "supper", bRows[i]);
			String zs = rd.getString("TA_DJ_LINEDETAI4G", "zs", bRows[i]);
			String cb = rd.getString("TA_DJ_LINEDETAI4G", "cb", bRows[i]);
			String zsbz = rd.getString("TA_DJ_LINEDETAI4G", "zsbz", bRows[i]);
			
			String sql = "INSERT INTO TA_DJ_LINEDETAI4G (ID,TID,XCMX,RQ,BREAKFAST,CMEAL,SUPPER,ZS,CB,ZSBZ,orgid) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, Id);
			preStmt.setString(2, groupID);
			preStmt.setBytes(3, XCMX.getBytes("GBK"));
			preStmt.setString(4, rq);
			preStmt.setString(5, breakfast);
			preStmt.setString(6, cmeal);
			preStmt.setString(7, supper);
			preStmt.setString(8, zs);
			preStmt.setString(9, cb);
			preStmt.setInt(10, Integer.parseInt(zsbz));
			preStmt.setString(11, sd.getString("orgid"));
			preStmt.executeUpdate();
			preStmt.close();
			preStmt = null;
		}
		return 999;
	}
	
	/**
	 * (修改团基本信息)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * 
	 * @return
	 * @return_type (int)
	 * @exception
	 * @since 1.0.0
	 */
	public int updateGroupInfo(BizData rd, BizData sd) {
		
		rd.add("yw", rd.getString("yw"));
		Connection conn = coreDAO.getConnection();
		String groupID = rd.getStringByDI("TA_DJ_GROUP","ID",0);
		try {
			coreDAO.beginTrasct(conn);
			rd.addAttr("TA_DJ_GROUP","ID","0","oldValue",groupID);
			rd.addAttr("TA_DJ_GROUP","orgid","0","oldValue",sd.getString("orgid"));
			String jd=rd.getStringByDI("TA_DJ_GROUP", "JD", 0);
			if("".equals(jd)){
				rd.add("TA_DJ_GROUP", "JD", "0");
			}
			String gw=rd.getStringByDI("TA_DJ_GROUP", "GW", 0);
			if("".equals(gw)){
				rd.add("TA_DJ_GROUP", "GW", "0");
			}
			coreDAO.update("TA_DJ_GROUP", rd, conn);
			delGroupTrave(groupID, sd.getString("orgid"), conn);// 删除组团社
			addGroupTrave(groupID, rd, sd, conn);// 添加组团社
			delGroupBlob(groupID, sd.getString("orgid"), conn);//删除团行程明细
			addGroupBlob(groupID, rd, conn, sd);
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
	
	//团列表
	public int djGroupLists(BizData rd, BizData sd){
		
		rd.add("yw", rd.getString("yw"));
		Connection conn = coreDAO.getConnection();
		String groupID = rd.getString("groupID");// 获取查询条件:团号
		String state = rd.getString("state");
		String bDate = rd.getString("bDate");
		String xlmc = rd.getString("xlmc");
		
		String pageNOStr = rd.getString("pageNO");
		int pageNO = Integer.parseInt(pageNOStr);
		String pageSizeStr = rd.getString("pageSize");
		int pageSize = Integer.parseInt(pageSizeStr);
		StringBuffer sql = new StringBuffer();
		sql.append("select g.id,g.xlid,g.xlmc,g.begin_date,g.ts,g.night,g.state,sum(g.crrs) crrs,sum(g.etrs) etrs\n");
		sql.append(" from ta_dj_group g\n");
		sql.append("where g.orgid=");
		sql.append(sd.getString("orgid")).append("\n");
		sql.append("and g.yklx=").append(rd.getString("yw")).append("\n");
		if(!"".equals(state))
			sql.append("and g.state = ").append(state);
		if (!"".equals(groupID))
			sql.append("and g.id like '%").append(groupID).append("%'\n");
		if (!"".equals(bDate))
			sql.append("and to_date(to_char(g.BEGIN_DATE,'yyyy-mm-dd'),'yyyy-mm-dd')=to_date('").append(bDate).append("','yyyy-mm-dd') \n");
		if (!"".equals(xlmc))
			sql.append("and g.xlmc like '%").append(xlmc).append("%'\n");
		sql.append("group by g.id,g.xlid,g.xlmc,g.begin_date,g.ts,g.night,g.state\n");
		sql.append("order by g.begin_date desc");
		try {
			coreDAO.executeQueryPage(sql.toString(), "allGroup4List", pageNO, pageSize, rd);
			
			//团组团社
			sql = null;
			sql = new StringBuffer();
			sql.append("select t.ztsid,t.ztsmc,g.id tid,t.id\n");
			sql.append(" from ta_dj_group g,ta_dj_tzts t\n");
			sql.append("where g.id=t.tid and g.orgid=t.orgid\n");
			sql.append("and g.orgid=").append(sd.getString("orgid")).append("\n");
			sql.append("and g.yklx=").append(rd.getString("yw"));
			coreDAO.executeQuery(sql.toString(), "travelNameOfGroup", rd, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try{
				if(null != conn)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return 8;
	}
	/**
	 * 计调团队列表（非全国大散）
	 */
	public int initAllGroupList(BizData rd, BizData sd) {
		
		Connection conn = coreDAO.getConnection();
		String groupID = rd.getString("groupID");// 获取查询条件:团号
		String bDate = rd.getString("bDate");// 获取查询条件:开始时间
		String xlmc = rd.getString("xlmc");// 获取查询条件:线路名称
		String state = rd.getString("state");
		String isBack = rd.getString("isBack");
		String pageNOStr = rd.getString("pageNO");// 获取分页
		String pageSizeStr = rd.getString("pageSize");// 获取分页
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		
		String sql ="select g.id,g.xlmc,g.BEGIN_DATE,g.END_DATE,g.dtrq,g.crrs,g.etrs,g.state,f.isback\n" +
				",a.jhzt hotel,b.jhzt dinnerroom,c.jhzt veh,d.jhzt travel,e.jhzt guide,ff.jhzt shop\n" +
				",gg.jhzt scenery,h.jhzt jiad,i.jhzt ticket,j.jhzt other\n" +
				" from ta_dj_group g\n" +
				",(select count(flowid),orgid,tid,isback \n"+
				"from ta_flow where DEFINITIONID='djywsh' and orgid="+sd.getString("orgid")+"\n"+
				"group by orgid,tid,isback) f\n" +
				",(select count(hotel.id) hotels,hotel.tid,hotel.jhzt \n"+
				"from ta_dj_jhhotel hotel where hotel.orgid="+sd.getString("orgid")+"\n"+
				"group by hotel.tid,hotel.jhzt) a\n"+
				",(select count(ct.id) cts, ct.tid,ct.jhzt\n" +
				"from ta_dj_jhct ct where ct.orgid="+sd.getString("orgid")+"\n" +
				"group by ct.tid,ct.jhzt) b\n" +
				",(select count(cl.id) cls, cl.tid,cl.jhzt\n" +
				"from ta_dj_jhcl cl where cl.orgid="+sd.getString("orgid")+"\n" +
				"group by cl.tid,cl.jhzt) c\n" +
				",(select count(dj.id) djs, dj.tid,dj.jhzt\n" +
				"from ta_dj_jhdj dj where dj.orgid="+sd.getString("orgid")+"\n" +
				"group by dj.tid,dj.jhzt) d\n" +
				",(select count(dy.id) dys, dy.tid,dy.jhzt\n" +
				"from ta_dj_jhdy dy where dy.orgid="+sd.getString("orgid")+"\n" +
				"group by dy.tid,dy.jhzt) e\n" +
				",(select count(gw.id) gws, gw.tid,gw.jhzt\n" +
				"from ta_dj_jhgw gw where gw.orgid="+sd.getString("orgid")+"\n" +
				"group by gw.tid,gw.jhzt) ff\n" +
				",(select count(jd.id) jds, jd.tid,jd.jhzt\n" +
				"from ta_dj_jhjd jd where jd.orgid="+sd.getString("orgid")+"\n" +
				"group by jd.tid,jd.jhzt) gg\n" +
				",(select count(jd.id) jiad, jd.tid,jd.jhzt\n" +
				"from ta_dj_jhjiad jd where jd.orgid="+sd.getString("orgid")+"\n" +
				"group by jd.tid,jd.jhzt) h\n" +
				",(select count(pw.id) pws, pw.tid,pw.jhzt\n" +
				"from ta_dj_jhpw pw where pw.orgid="+sd.getString("orgid")+"\n" +
				"group by pw.tid,pw.jhzt) i\n" +
				",(select count(qt.id) qts, qt.tid,qt.jhzt\n" +
				"from ta_dj_jhqt qt where qt.orgid="+sd.getString("orgid")+"\n" +
				"group by qt.tid,qt.jhzt) j\n" +
				"where g.id=f.tid(+)\n" +
				"and g.id=a.tid(+) and g.id=b.tid(+) and g.id=c.tid(+) and g.id=d.tid(+) and g.id=e.tid(+) and g.id=ff.tid(+)\n" +
				"and g.id=gg.tid(+) and g.id=h.tid(+) and g.id=i.tid(+) and g.id=j.tid(+)\n" +
				"and g.orgid="+sd.getString("orgid");
		if(!"".equals(state))
			sql += "and g.state = "+state;
		if (!"".equals(groupID)) // 如果团号不为空
			sql += "and g.id like '%" + groupID + "%' \n";
		if (!"".equals(bDate)) // 如果开团时间不为空
			sql += "and to_date(to_char(g.BEGIN_DATE,'yyyy-mm-dd'),'yyyy-mm-dd')=to_date('" + bDate + "','yyyy-mm-dd') \n";
		if (!"".equals(xlmc))
			sql += "and g.xlmc like '%"+xlmc+"%' \n";
		if(!"".equals(isBack))
			sql += "and f.isback ='"+isBack+"' \n";
		sql +=" order by g.BEGIN_DATE desc";
		try {
			coreDAO.executeQueryPage(sql, "rsAllGroupList", pageNO, pageSize, rd, conn);
			queryZTS(rd, sd, conn);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 8;
	}
	
	/**
	 *全国大散团队列表
	 */
	public void scattered4GroupList(BizData rd, BizData sd) {
		
		Connection conn = coreDAO.getConnection();
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
				"select a.id,a.xlid,a.xlmc,a.begin_date,a.end_date,a.dtrq,a.crrs,a.etrs,a.state,a.ycbz,a.zsbz,nvl(b.isback,'N') isBack \n"+
				" from ( \n"+
				"select g.id,g.xlid,g.xlmc,g.BEGIN_DATE,g.END_DATE,g.dtrq,g.crrs,g.etrs,g.state,g.ycbz,g.zsbz \n"+
				"from ta_dj_group g\n";
	//			"where g.user_no="+sd.getString("userno")+"  \n";
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
		sql += "and d.definitionid='djywsh') b,ta_dj_linemng l \n"+
				"where a.id=b.tid(+) and l.xlid=a.xlid\n";
		sql +="ORDER BY a.DTRQ desc \n" +
				") c \n";
		if(!"".equals(isBack))
			sql += "where c.isback ='"+isBack+"'";
		try {
			coreDAO.executeQueryPage(sql, "rsAllGroupList", pageNO, pageSize, rd, conn);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public void addGroupTrave(String groupID, BizData rd, BizData sd, Connection conn) throws SQLException, ParseException {
		
		String[] rowIDs = rd.getRowIDs("TA_DJ_TZTS");
		for (int i = 0; i < rowIDs.length; i++) {
			
			int ztsID = queryMaxIDByPara("TA_DJ_TZTS", "ID", null);
			rd.add("TA_DJ_TZTS", "ID", rowIDs[i], ztsID);
			rd.add("TA_DJ_TZTS", "TID", rowIDs[i], groupID);
			rd.add("TA_DJ_TZTS", "orgid", rowIDs[i], sd.getString("orgid"));
			String ztsIdFromPage = rd.getString("ta_dj_tzts","ztsid",rowIDs[i]);
			//没有取到页面上的组团社ID，认为该组团社为新的，需要登记到旅行社基础表中
			if("".equals(ztsIdFromPage)){
				int baseTravelId = queryMaxIDByPara("TA_TRAVELAGENCY", "TRAVEL_AGC_ID", null);
				rd.add("TA_TRAVELAGENCY", "TRAVEL_AGC_ID", baseTravelId);
				rd.add("TA_TRAVELAGENCY", "CMPNY_NAME", rd.getString("ta_dj_tzts","ZTSMC",rowIDs[i]));
				rd.add("TA_TRAVELAGENCY", "orgid", sd.getString("orgid"));
				coreDAO.insert("TA_TRAVELAGENCY", rd, conn);
				rd.remove("TA_TRAVELAGENCY");
			}
				
		}
		coreDAO.insert("TA_DJ_TZTS", rd, conn);
		rd.remove("TA_DJ_TZTS");
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
	private void delGroupTrave(String groupID, String orgid, Connection conn)
			throws SQLException {

		BizData data = new BizData();
		data.add("TA_DJ_TZTS", "TID", groupID);
		data.add("TA_DJ_TZTS", "orgid", orgid);
		coreDAO.delete(data, conn);
	}
	/**
	 * delGroupBlob(//删除团行程明细)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * 
	 * @param groupID
	 * @param conn
	 * @throws SQLException
	 * @return_type (void)
	 * @exception
	 * @since 1.0.0
	 */
	private void delGroupBlob(String groupID, String orgid, Connection conn)
			throws SQLException {
		BizData data = new BizData();
		data.add("TA_DJ_LINEDETAI4G", "TID", groupID);
		data.add("TA_DJ_LINEDETAI4G", "orgid", orgid);
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
	public int printPlanInfo(BizData rd,BizData sd){
		
		Connection conn = coreDAO.getConnection();
		String id=rd.getString("id");
		//团基本信息
		String sql="select g.id,nvl(g.crrs,0)crrs,nvl(g.etrs,0)etrs,g.dtrq, \n"+
			"g.qpxm,g.qpdh,g.COMMENTS from ta_dj_group g where g.id='"+id+"' and g.orgid="+sd.getString("orgid");
		
		//导游信息
		String sql2="select sum(d.dylk) dylk,sum(d.dff) dff,d.dyxm from ta_dj_jhdy d \n" +
				"where d.tid='"+id+"' and d.orgid="+sd.getString("orgid")+" group by d.dyxm";
		String dyfy="select sum(d.dylk) dylk,sum(d.dff) dff from ta_dj_jhdy d where d.tid='"+id+"' and d.orgid="+sd.getString("orgid");
		
		//车辆信息
		String sql3="select a.*,d.dmsm1 \n" +
				" from ta_dj_jhcl a, dmsm d \n" +
				"where d.dmlb=13 and a.cx=d.dmz(+) and a.tid='"+id+"' and a.orgid="+sd.getString("orgid");
		
		//酒店信息
		String sql4="select a.*,d.name city \n" +
					" from ta_dj_jhhotel a,xzqh d \n" +
					"where a.tid='"+id+"' and a.xzqhid=d.id(+)  and a.orgid="+sd.getString("orgid");
		
		//餐厅信息
		String sql5="select a.*,d.name city \n" +
					" from ta_dj_jhct a, xzqh d \n" +
					"where a.xzqhid=d.id(+)  and a.tid='"+id+"' and a.orgid="+sd.getString("orgid");
		
		//景点信息
		String sql6="select a.* ,d.name city \n" +
				" from ta_dj_jhjd a, xzqh d where a.xzqhid=d.id(+) and a.tid='"+id+"' and a.orgid="+sd.getString("orgid");
		
		//票务
		String sql7="select a.*, d.dmsm1 \n" +
				" from ta_dj_jhpw a,dmsm d \n"+
				"where d.dmlb=2 and d.dmz=a.jtlx and a.tid='"+id+"' and a.orgid="+sd.getString("orgid");
		
		//地接社
		String sql8="select a.* from ta_dj_jhdj a where a.tid='"+id+"' and a.orgid="+sd.getString("orgid");
		
		//购物点
		String sql9="select a.gwdmc from ta_dj_jhgw a where a.tid='"+id+"' and a.orgid="+sd.getString("orgid");
		
		//加点
		String sql10="select a.jdmc from ta_dj_jhjiad a where a.tid='"+id+"' and a.orgid="+sd.getString("orgid");
		
		String sql11="select * from ta_dj_jhqt where tid='"+id+"' and orgid="+sd.getString("orgid");
		//签单与现付总计
		String qdxf="select sum(nvl(qdpwzj,0)+nvl(qdclzj,0)+nvl(qdzszj,0)+nvl(qdctzj,0)+nvl(qdjdzj,0)+nvl(qddjzj,0)) qdzj," +
				" sum(nvl(xfpwzj,0)+nvl(xfclzj,0)+nvl(xfzszj,0)+nvl(xfctzj,0)+nvl(xfjdzj,0)+nvl(xfdjzj,0)) xfzj, " +
				" sum(nvl(pwzj,0)+nvl(clzj,0)+nvl(zszj,0)+nvl(ctzj,0)+nvl(jdzj,0)+nvl(djzj,0)) zj from ta_tdjdxxzjb where tid='"+id+"'\n" +
				" and orgid="+sd.getString("orgid");
		//团组团社
		String tztsSQL = "select t.ztsmc\n" +
				"from ta_dj_group g,ta_dj_tzts t\n" +
				"where g.orgid=t.orgid and g.id=t.tid\n" +
				"and g.id='"+id+"'\n" +
						"and g.orgid="+sd.getString("orgid");
		//制定人与审核人信息
		/*String creatermsg="select u.username,r.rolename,f.creater \n" +
				"from drmuser u,drmuserrole l,drmrole r,ta_flow f \n" +
				"where u.userno=f.creater \n"+
				"and u.userid=l.userid and u.orgid=l.orgid and l.roleid=r.roleid and f.tid='"+id+"'\n" +
				"and f.definitionid='djywsh'";
		String ywbzgmsg="select u.username,r.rolename \n" +
				"from drmuser u,drmuserrole l,drmrole r,ta_dj_tspb d \n" +
				"where d.spr=u.userno \n"+
				"and u.userid=l.userid and u.orgid=l.orgid and l.roleid=r.roleid and l.orgid=r.orgid \n" +
				"and r.roleid='djbzg' and d.mklb=1 and rownum=1 and d.tid='"+id+"'";
		String kjmsg="select u.username,r.rolename from drmuser u,drmuserrole l,drmrole r,ta_dj_tspb d where d.mklb=1 and d.spr=u.userno \n"+
		" and u.userid=l.userid and l.roleid=r.roleid and r.roleid='kuaiji' and d.tid='"+id+"'";*/
		try {
			coreDAO.executeQuery(sql, "groupList", rd, conn);
			coreDAO.executeQuery(sql2, "guideList", rd, conn);
			coreDAO.executeQuery(dyfy, "guideFYList", rd, conn);
			coreDAO.executeQuery(sql3, "carList", rd, conn);
			coreDAO.executeQuery(sql4, "hotelList", rd, conn);
			coreDAO.executeQuery(sql5, "dinnerList", rd, conn);
			coreDAO.executeQuery(sql6, "sceneryList", rd, conn);
			coreDAO.executeQuery(sql7, "ticketList", rd, conn);
			coreDAO.executeQuery(sql8, "traveList", rd, conn);
			coreDAO.executeQuery(sql9, "shopList", rd, conn);
			coreDAO.executeQuery(sql10, "jiadList", rd, conn);
			coreDAO.executeQuery(sql11, "otherList", rd, conn);
			coreDAO.executeQuery(qdxf, "qdxfzj", rd, conn);
			coreDAO.executeQuery(tztsSQL, "tzts", rd, conn);
			/*coreDAO.executeQuery(creatermsg, "creatermsg", rd);
			coreDAO.executeQuery(ywbzgmsg, "ywbzgmsg", rd);
			coreDAO.executeQuery(kjmsg, "kjmsg", rd);*/
			
			conn.close();
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

		sql = "select g.id,g.xlid,g.xlmc,g.BEGIN_DATE,g.END_DATE,g.dtrq,g.crrs,g.etrs,g.state,u.userno \n"
				+ " from ta_dj_group g,drmuser u\n"
				+ "where u.userno=g.user_no and g.state=4 and g.orgid='"+sd.getString("orgid")+"'\n";
		if (!"".equals(groupID)) { // 如果团号不为空
			sql += "and g.id like '%" + groupID + "%' \n";
		}
		if (!"".equals(bDate)) { // 如果开团时间不为空
			sql += "and g.BEGIN_DATE=to_date('" + bDate + "','yyyy-mm-dd') \n";
		}
		sql +="ORDER BY g.DTRQ desc";
		try {
			coreDAO.executeQueryPage(sql, "rsGroupPrintList", pageNO, pageSize, rd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//执行打印,修改团状态
	public int doGroupPrint(BizData rd,BizData sd){
		String id=rd.getString("id");
		try {
			String sql="update ta_dj_group g set g.state=4 where g.id='"+id+"'";
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
					" from ta_dj_group g,ta_dj_jhdy d\n"+
					" where g.id=d.tid and g.orgid=d.orgid and g.state in(4,5) \n" +
					" and d.bxr='Y' and g.orgid="+sd.getString("orgid")+" and d.dyid="+sd.getString("userno")+"\n";
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
	
	/**
	 * 查询团组团社信息
	 * @param rd
	 * @param sd
	 * @param conn
	 */
	private void queryZTS(BizData rd, BizData sd, Connection conn){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("select z.tid,z.id,z.ztsid,t.cmpny_name\n").
		append("from TA_DJ_TZTS z,ta_travelagency t\n").
		append("where z.ztsid=t.travel_agc_id and z.orgid=t.orgid and t.orgid=").append(sd.getString("orgid"));
		try {
			coreDAO.executeQuery(sqlBuff.toString(), "rsTZTS", rd, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int queryBxGuideInfo(BizData rd, BizData sd) {
		
		Connection conn = coreDAO.getConnection();
		String groupID= rd.getStringByDI("TA_DJ_GROUP", "ID", 0);
		String sql="";
		sql="select g.id,g.qprs,g.YSKZJ,g.xlmc,g.begin_date,g.end_date,g.state ,d.dyxm,g.crrs,g.COMMENTS,g.etrs \n" 
		+	"  from  ta_dj_group g,Ta_Dj_jhdy d,(select *  from  TA_DJ_TSPB where mklb='2') b \n"
		+	"  where g.id=b.tid(+) \n"
		+	"  and g.id=d.tid(+) \n"
		+	"  and g.id='" + groupID + "'";
		
		String sql2="select sum(d.dylk)dylk,sum(d.dff)dff,sum(d.dyjtf)dyjtf,sum(d.qt)qt \n "+
		"   from ta_dj_bxdy d \n" +
		" where d.tid='"+groupID+"'";
		try {
			coreDAO.executeQuery(sql, "bx_GuideInfo", rd, conn);
			coreDAO.executeQuery(sql2,"allGuideFy",rd, conn);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * 初始化计调页面的团队基础信息
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int djInitPlan(BizData rd,BizData sd) {
		
		Connection conn = coreDAO.getConnection();
		try {
			//团队基础信息
			String tid = rd.getStringByDI("TA_DJ_GROUP", "ID", 0);
			StringBuffer sql = new StringBuffer();
			sql.append("select g.*,z.ztsmc\n");
			sql.append("from ta_dj_group g, TA_DJ_TZTS z\n");
			sql.append("where g.orgid=z.orgid(+) and g.id=z.tid(+)\n");
			sql.append("and g.id='"+tid+"' and g.orgid="+sd.getString("orgid")+"\n");
			coreDAO.executeQuery(sql.toString(), "TA_DJ_GROUPs", rd, conn);
			//团队行程明细
			super.entityName = "TA_DJ_LINEDETAI4G";
			query(rd, conn);
			//审批情况
			spyj(rd, sd, "1", conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(conn != null){
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return 98;
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
	 * 修改团，初始化页面
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int djInitUpdateGroup(BizData rd,BizData sd){
		
		rd.add("yw", rd.getString("yw"));
		Connection conn = coreDAO.getConnection();
		try {
			//团基本信息
			coreDAO.select("ta_dj_group", rd, true, conn);
			//团组团社
			coreDAO.select("TA_DJ_TZTS", rd, true, conn);
			//行政区划
			coreDAO.select("XZQH", rd, true, conn);
			//查询团明细
			queryGroupDetail(rd, sd, conn);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 8;
	}
	
	/**
	 * 利用线路初始化新增团队的页面
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int djInitNewGroupByLine(BizData rd,BizData sd){
		
		Connection conn = coreDAO.getConnection();
		//
		try {
			//线路
			coreDAO.select("TA_DJ_LINEMNG", rd, true, conn);
			//线路价格
			coreDAO.select("TA_DJ_XLJG", rd, true, conn);
			//行政区划
			coreDAO.select("xzqh", rd, true, conn);
			//查询团号
			createGroupId(rd, sd, conn);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 8;
	}
	
	/**
	 * 初始化报销页面
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int djInitBxPage(BizData rd,BizData sd){
		
		Connection conn = coreDAO.getConnection();
		try {
			//团队信息
			coreDAO.select("TA_DJ_GROUP", rd, true, conn);
			//行程明细
			coreDAO.select("TA_DJ_LINEDETAI4G", rd, true, conn);
			//审批意见
			spyj(rd, sd, "2", conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try{
				if(null != conn)
					conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 8;
	}
}
