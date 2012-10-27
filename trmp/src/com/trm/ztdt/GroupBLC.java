package com.trm.ztdt;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
  * @ClassName: GroupBLC
  * @Description: TODO 组团- 团队信息事务处理类
  * @author KingStong - likai
  * @date 2012-4-19 上午6:11:09
  *
  */
public class GroupBLC extends DBBLC{
	public GroupBLC(){
		this.entityName="TA_ZT_GROUP";
	}
	/**
	  * @Title: editGroup
	  * @Description: TODO  修改团信息
	  */	
	public int editGroup(BizData rd,BizData sd) {
		
		String groupID = rd.getStringByDI("TA_ZT_GROUP", "id", 0);
		String isClose = rd.getString("isClose");
		Connection conn = coreDAO.getConnection();
		try{
			coreDAO.beginTrasct(conn);
			if("".equals(isClose)) {
				isClose = "1";//不封团
			}else if("0".equals(isClose)){
				//封团，更改团队成人人数和儿童人数
				queryPersonCounts(groupID, rd);
				rd.add("TA_ZT_GROUP", "ADULT_COUNT", rd.getStringByDI("personsCount", "cr", 0));
				rd.add("TA_ZT_GROUP", "CHILDREN_COUNT", rd.getStringByDI("personsCount", "childs", 0));
			}
			rd.add("TA_ZT_GROUP", "FTZT", isClose);//封团状态
			coreDAO.update("TA_ZT_GROUP",rd,conn);
			updateGroupBlob(groupID, conn, rd);
			
			//团价格表，先删除后增加
			String sql = "delete from TA_ZT_GPRICE p where p.g_id='"+groupID+"'";
			coreDAO.executeUpdate(sql, conn);
			String[] rowIDs = rd.getRowIDs("TA_ZT_GPRICE");
			for(int i=0;i<rowIDs.length;i++) {
				
				int priceID = queryMaxIDByPara("TA_ZT_GPRICE", "ID", null);
				String priceTH = rd.getStringByDI("TA_ZT_GPRICE","price_th",i);
				if("".equals(priceTH)) priceTH="0";
				String priceMS = rd.getStringByDI("TA_ZT_GPRICE","price_ms",i);
				if("".equals(priceMS)) priceMS="0";
				String priceZD = rd.getStringByDI("TA_ZT_GPRICE","price_zd",i);
				if("".equals(priceZD)) priceZD="0";
				String SINGLE_ROOM = rd.getStringByDI("TA_ZT_GPRICE","SINGLE_ROOM",i);
				if("".equals(SINGLE_ROOM)) SINGLE_ROOM="0";
				rd.add("TA_ZT_GPRICE", "ID", i, priceID);
				rd.add("TA_ZT_GPRICE", "g_id", i, groupID);
				rd.add("TA_ZT_GPRICE", "price_th", i, priceTH);
				rd.add("TA_ZT_GPRICE", "price_ms", i, priceMS);
				rd.add("TA_ZT_GPRICE", "price_zd", i, priceZD);
				rd.add("TA_ZT_GPRICE", "SINGLE_ROOM", i, SINGLE_ROOM);
			}
			coreDAO.insert("TA_ZT_GPRICE", rd, conn);
			coreDAO.commitTrasct(conn);
		}catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
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
		return 1;
	}
	
	/**
	  * @Title: queryPersonCounts
	  * @Description: TODO 根据团号查询当前所有订单总共收了多少人
	  */
	private void queryPersonCounts(String tid, BizData data){
		
		String sql = "select nvl(a.cr,0) cr,nvl(b.childs,0) childs,g.id\n" +
				"from (\n" +
				"select count(v.id) cr,o.tid\n" +
				"from ta_zt_visitor v, ta_zt_ykorder o\n" +
				"where v.m_id=o.id\n" +
				"and v.ischild=2\n" +
				"group by o.tid\n" +
				") a,(\n" +
				"select count(v.id) childs,o.tid\n" +
				"from ta_zt_visitor v, ta_zt_ykorder o\n" +
				"where v.m_id=o.id\n" +
				"and v.ischild=1\n" +
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
	
	/**
	  * @Title: updateGroupBlob
	  * @Description: TODO 更新团信息表的线路行程明细
	  */
	private void updateGroupBlob(String groupID, Connection conn, BizData rd) throws SQLException, UnsupportedEncodingException, NullPointerException{
		
		//删除旧的团行程明细
		BizData data = new BizData();
		data.add("TA_ZT_LINEDETAI4G", "tid", groupID);
		coreDAO.delete(data, conn);
		//添加团行程明细
		String sql = "insert into TA_ZT_LINEDETAI4G(id,Tid,Rq,Xcmx,Breakfast,Cmeal,Supper,ZSBZ) VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement preStmt = conn.prepareStatement(sql);
		String[] rowIDs = rd.getRowIDs("TA_ZT_LINEDETAI4G");
		for(int i=0;i<rowIDs.length;i++){
			
			int pkID = queryMaxIDByPara("TA_ZT_LINEDETAI4G", "id", null);
			preStmt.setInt(1, pkID);
			preStmt.setString(2,groupID);
			preStmt.setString(3, rd.getString("TA_ZT_LINEDETAI4G", "rq", rowIDs[i]));
			preStmt.setBytes(4, rd.getString("TA_ZT_LINEDETAI4G", "xcmx", rowIDs[i]).getBytes("GBK"));
			preStmt.setString(5, rd.getString("TA_ZT_LINEDETAI4G", "breakfast", rowIDs[i]).equals("")?"N":"Y");
			preStmt.setString(6, rd.getString("TA_ZT_LINEDETAI4G", "cmeal", rowIDs[i]).equals("")?"N":"Y");
			preStmt.setString(7, rd.getString("TA_ZT_LINEDETAI4G", "Supper", rowIDs[i]).equals("")?"N":"Y");
			preStmt.setString(8, rd.getString("TA_ZT_LINEDETAI4G", "ZSBZ", rowIDs[i]));
			preStmt.executeUpdate();
		}
		preStmt.close();
		preStmt = null;
	}
	
	
	/**
	  * @Title: getAllPlan
	  * @Description: TODO 获得需要计调的团
	  */
	public int getAllPlan(BizData rd,BizData sd)throws SQLException{
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageSize"));
		String sql="select x.id,x.g_id,x.line_id,x.line_name,x.begin_date,x.end_date,x.leader_name, \n"+
		"x.leader_tel,x.comments,x.zdrs,x.kctrs,nvl(y.ysrs,0)ysrs,zdrs-nvl(y.ysrs,0) syrs from \n"+
		"(select a.id,a.g_id,a.line_id,b.line_name,a.begin_date,a.end_date,a.leader_name, \n"+
		" a.leader_tel,a.comments,b.maxpersoncount zdrs,b.minpersoncount kctrs \n"+
		"from TA_ZT_GROUP a,TA_ZT_LINEMNG b where a.line_id=b.line_id and a.g_id>'0' and a.state=1) x,\n"+
		"(select b.g_id,nvl(sum(b.ysrs),0) ysrs \n" +
		"from TA_ZT_GROUP a,TA_ZT_YKORDER b where \n" +
		"a.id=b.g_id and b.dd_confirm=1 \n" +
		"group by b.g_id) y where x.id=y.g_id(+)";
		coreDAO.executeQueryPage(sql, "groupList", pageNO, pageSize, rd);
		return 1;
	}

	/**
	  * @Title: plan_findByLike
	  * @Description: TODO 模糊查询,所有团
	  */
	public int plan_findByLike(BizData rd,BizData sd){
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageSize"));
		String th=rd.getString("th");
		String ftrq=rd.getString("ftrq");
		String sql="";
		if(!"".equals(ftrq)){
		 sql="select x.id,x.g_id,x.line_id,x.line_name,x.begin_date,x.end_date,x.leader_name, \n"+
		"x.leader_tel,x.comments,x.zdrs,x.kctrs,nvl(y.ysrs,0)ysrs,zdrs-nvl(y.ysrs,0) syrs from \n"+
		"(select a.id,a.g_id,a.line_id,b.line_name,a.begin_date,a.end_date,a.leader_name, \n"+
		" a.leader_tel,a.comments,a.state,b.maxpersoncount zdrs,b.minpersoncount kctrs \n"+
		" from TA_ZT_GROUP a,TA_ZT_LINEMNG b where a.line_id=b.line_id and a.g_id>'0' and a.g_id like '%"+th+"%' \n" +
			"and a.begin_date=to_date('"+ftrq+"','yyyy-mm-dd') and a.state=1) x,\n"+
		"(select b.g_id,nvl(sum(b.ysrs),0) ysrs \n" +
		" from TA_ZT_GROUP a,TA_ZT_YKORDER b where \n" +
		"a.id=b.g_id and b.dd_confirm=1 \n" +
		"group by b.g_id) y where x.id=y.g_id(+)";
		}else{
			 sql="select x.id,x.g_id,x.line_id,x.line_name,x.begin_date,x.end_date,x.leader_name, \n"+
			"x.leader_tel,x.comments,x.zdrs,x.kctrs,nvl(y.ysrs,0)ysrs,zdrs-nvl(y.ysrs,0) syrs from \n"+
			"(select a.id,a.g_id,a.line_id,b.line_name,a.begin_date,a.end_date,a.leader_name, \n"+
			" a.leader_tel,a.comments,a.state,b.maxpersoncount zdrs,b.minpersoncount kctrs \n"+
			" from TA_ZT_GROUP a,TA_ZT_LINEMNG b where a.line_id=b.line_id and a.g_id>'0' and a.g_id like '%"+th+"%' \n" +
				"and a.state=1) x,\n"+
			"(select b.g_id,nvl(sum(b.ysrs),0) ysrs \n" +
			" from TA_ZT_GROUP a,TA_ZT_YKORDER b where \n" +
			"a.id=b.g_id and b.dd_confirm=1 \n" +
			"group by b.g_id) y where x.id=y.g_id(+)";
		}
		try {
			coreDAO.executeQueryPage(sql, "groupList", pageNO, pageSize, rd);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	  * @Title: print_getPlanInfo
	  * @Description: TODO 查询团计调信息。打印页面
	  */
	public int print_getPlanInfo(BizData rd,BizData sd){
		String id=rd.getString("id");
		//团基本信息
		String sql="select a.id,a.g_id,nvl(a.adult_count,0)adult_count," +
				"nvl(a.children_count,0)children_count,nvl(a.otherside_gc,0)otherside_gc,a.COMMENTS " +
				"from TA_ZT_GROUP a where a.id="+id;
		//导游
		String sql2="select a.id,a.groupid,a.guide_id,b.username " +
				"from ta_g_guide_do a,drmuser b where a.guide_id=b.userno and a.groupid="+id;
		//车辆
		String sql3="select a.id,a.groupid,a.xfje,a.qdje,b.car_code,b.driver_name,b.driver_phone,c.cmpny_name,d.dmsm1"+
					" from ta_g_vehicle_do a,ta_car b,ta_car_team c,dmsm d "+
					"where a.veh_team_id=c.team_id and a.vehicle_id=b.car_id and b.car_type=d.dmz and d.dmlb=13 and a.groupid="+id;	
		//餐厅
		String sql4="select a.id,a.groupid,b.cmpny_name,a.bf_price,a.bf_count,a.bf_persons,a.dinner_price,"+
				"a.dinner_fact_price,a.dinner_count,a.dinner_persons,a.qdje,a.xfje "+
				" from ta_g_restaurant_do a,ta_dining_room b where a.dining_room_id=b.dining_room_id and a.groupid="+id;
		//酒店
		String sql5="select a.id,a.groupid,b.hotel_name,b.hotel_tel,a.pay_qd,a.pay_xf,a.in_date ,c.dmsm1,d.name city"+
					" from ta_g_hotel_do a,ta_hotel b,dmsm c,xzqh d "+
					"where a.hotel_id=b.hotel_id and a.city_id=d.id and a.hotel_level=c.dmz and c.dmlb=6 and a.groupid="+id;
		//酒店价格
		String ht_sql="select c.dmsm1,b.price,b.rooms,a.id \n"+
			"from ta_g_hotel_do a,ta_g_hotel_do_price b,dmsm c \n"+
			"where a.id=b.g_hotel_id and b.price_typeid=c.dmz and c.dmlb=10 and b.rooms>0 and a.groupid="+id;
		//景点 
		String sql6="select b.id,c.cmpny_name,b.xfje,b.qdje"+
			" from ta_g_scenryxzqh a,ta_g_scenery b,ta_scenery_point c"+
			" where b.areaid=a.id and b.scenery_id=c.scenery_id and a.groupid="+id;
		//景点价格
		String sc_sql="select d.dmsm1,c.prices,c.persons,b.id \n"+
			"from ta_g_scenryxzqh a,ta_g_scenery b,ta_g_scenery_mny c,dmsm d \n"+
			"where b.id=c.g_sceneryid and d.dmz=c.pricenameid and d.dmlb=12 and a.id=b.areaid and c.persons>0 and  a.groupid="+id;
		//地接社
		String sql7="select a.id,a.groupid,b.cmpny_name,a.pay_money,nvl(a.yfzk,0)yfzk,"+
					"b.business_name,b.business_mobile from ta_g_next_agency a,ta_travelagency b "+
					" where a.travel_agc_id=b.travel_agc_id and a.groupid="+id;
		//票务
		String sql8="select a.ticket_id,c.cmpny_name,c.business_mobile,b.tickettime,b.cchb,b.sxf, \n"+
		"b.cfz,b.zdz,b.price,b.zs,b.xf,b.qd,d.dmsm1 \n"+
		" from ta_g_ticket_do a,ta_g_ticketdetail b,ta_ticket c,dmsm d \n"+ 
		" where a.id=b.ticketid and a.ticket_id=c.ticket_id and \n"+
		"b.tickettype=d.dmz and d.dmlb=15 and a.groupid="+id;
		//购物点
		String sql9="select a.id,a.groupid,b.cmpny_name " +
				" from ta_g_gw a,ta_shoppoint b where a.shoppointid=b.shop_point_id and a.groupid="+id;
		//查询成本,签单,现付明细
		String sql10="select car.*,hotel.*,dinner.*,scenery.*,ticket.*, \n"+
			"car.car_xf+hotel.hotel_xf+dinner.dinner_xf+scenery.scenery_xf+ticket.ticket_xf xfzj,\n"+
			"car.car_qd+hotel.hotel_qd+dinner.dinner_qd+scenery.scenery_qd+ticket.ticket_qd qdzj, \n"+
			"car.car_zj+hotel.hotel_zj+dinner.dinner_zj+scenery.scenery_zj+ticket.ticket_zj zj from \n"+
			"(select nvl(sum(a.qdje),0) car_qd,nvl(sum(a.xfje),0)car_xf, \n"+
				"	nvl(sum(a.qdje),0)+nvl(sum(a.xfje),0) car_zj \n"+
					"from ta_g_vehicle_do a where a.groupid="+id+") car, \n"+
					"(select nvl(sum(b.pay_qd),0)hotel_qd,nvl(sum(b.pay_xf),0)hotel_xf, \n"+
					"nvl(sum(b.pay_qd),0)+nvl(sum(b.pay_xf),0)hotel_zj \n"+
					"from ta_g_hotel_do b where b.groupid="+id+") hotel, \n"+
					"(select nvl(sum(c.qdje),0)dinner_qd,nvl(sum(c.xfje),0)dinner_xf, \n"+
					"nvl(sum(c.qdje),0)+nvl(sum(c.xfje),0) dinner_zj \n"+
					"from ta_g_restaurant_do c where c.groupid="+id+") dinner, \n"+
					"(select nvl(sum(e.qdje),0)scenery_qd,nvl(sum(e.xfje),0)scenery_xf, \n"+
				    "nvl(sum(e.qdje),0)+nvl(sum(e.xfje),0) scenery_zj \n"+
					" from ta_g_scenryxzqh d,ta_g_scenery e where d.id=e.areaid and d.groupid="+id+") scenery, \n"+
					"(select nvl(sum(f.qdje),0)ticket_qd,nvl(sum(f.xfje),0)ticket_xf, \n"+
					"nvl(sum(f.qdje),0)+nvl(sum(f.xfje),0)ticket_zj \n"+
					"from ta_g_ticket_do f where f.groupid="+id+") ticket";
		String sql11="select x.dyfyhj,y.dff,z.dyjtf,x.dyfyhj-y.dff-z.dyjtf qtfy,m.dylk,m.dykk from \n"+
			"(select sum(b.costs)dyfyhj from ta_g_guide_do a,ta_g_guide_other b where b.ta_g_guide_id=a.id and a.groupid="+id+") x, \n"+
			"(select sum(b.costs)dff from ta_g_guide_do a,ta_g_guide_other b where b.ta_g_guide_id=a.id and b.cost_name_id=1 and a.groupid="+id+")y, \n"+
			"(select sum(b.costs)dyjtf from ta_g_guide_do a,ta_g_guide_other b where b.ta_g_guide_id=a.id and b.cost_name_id=2 and a.groupid="+id+")z, \n"+
			"(select nvl(sum(a.dylk),0) dylk,nvl(sum(a.dykk),0) dykk from ta_g_guide_do a where a.groupid="+id+") m";
		try {
			coreDAO.executeQuery(sql, "groupInfo", rd);
			coreDAO.executeQuery(sql2, "guideList", rd);
			coreDAO.executeQuery(sql3, "carList", rd);
			coreDAO.executeQuery(sql4, "dinnerList", rd);
			coreDAO.executeQuery(sql5, "hotelList", rd);
			coreDAO.executeQuery(ht_sql, "hotelPrice", rd);
			coreDAO.executeQuery(sql6, "sceneryList", rd);
			coreDAO.executeQuery(sc_sql, "sceneryPrice", rd);
			coreDAO.executeQuery(sql7, "traveList", rd);
			coreDAO.executeQuery(sql8, "ticketList", rd);
			coreDAO.executeQuery(sql9, "shopList", rd);
			coreDAO.executeQuery(sql10, "moneyInfo",rd);
			coreDAO.executeQuery(sql11, "guideMon", rd);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 1;
	}

	/**
	  * @Title: doPrint
	  * @Description: TODO 执行打印,修改团状态
	  */
	public int doPrint(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		try{
			coreDAO.beginTrasct(conn);
			String id=rd.getString("id");
			String sql="update TA_ZT_GROUP a set a.state=2 where a.id="+id;
			coreDAO.executeUpdate(sql,conn);
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
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
	  * @Title: getAllBx
	  * @Description: TODO 查询所有需要报销的团
	  */
	public int getAllBx(BizData rd,BizData sd){
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageSize"));
		String sql="select a.id,a.g_id,a.begin_date,a.end_date,b.line_name " +
				" from TA_ZT_GROUP a,TA_ZT_LINEMNG b where a.line_id=b.line_id and a.state=2";
		try {
			coreDAO.executeQueryPage(sql, "bxList", pageNO, pageSize, rd);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	  * @Title: bx_getAllBylike
	  * @Description: TODO 模糊查询所有报销团
	  */
	public int bx_getAllBylike(BizData rd,BizData sd){
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageSize"));
		String th=rd.getString("th");
		String ftrq=rd.getString("ftrq");
		String sql="";
		if("".equals(ftrq)){
			sql="select a.id,a.g_id,a.begin_date,a.end_date,b.line_name \n" +
			" from TA_ZT_GROUP a,TA_ZT_LINEMNG b where a.line_id=b.line_id \n" +
			"and a.g_id like '%"+th+"%' and a.state=2";
		}else{
			sql="select a.id,a.g_id,a.begin_date,a.end_date,b.line_name \n" +
			" from TA_ZT_GROUP a,TA_ZT_LINEMNG b where a.line_id=b.line_id \n" +
			"and a.g_id like '%"+th+"%' and a.begin_date=to_date('"+ftrq+"','yyyy-mm-dd') and a.state=2";	
		}
		try {
			coreDAO.executeQueryPage(sql, "bxList", pageNO, pageSize, rd);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 1;
	}
 
	/**
	  * @Title: zwfx_getAllGroup
	  * @Description: TODO 团结账务分析列表
	  */
	public int zwfx_getAllGroup(BizData rd,BizData sd){
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageSize"));
		String sql="select a.id,a.g_id,a.begin_date,a.end_date,nvl(a.children_count,0)etrs, \n" +
				"nvl(a.adult_count,0)crrs,a.days,b.line_name \n"+
			" from TA_ZT_GROUP a,TA_ZT_LINEMNG b where a.line_id=b.line_id and a.state=2"+
			" and a.g_id like '%"+rd.getString("th")+"%'";
		try {
			coreDAO.executeQueryPage(sql, "groupList", pageNO, pageSize, rd);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 1;
	}

	/**
	  * @Title: to_showTDZW
	  * @Description: TODO 显示团队账务明细
	  */
	public int to_showTDZW(BizData rd,BizData sd){
		String id=rd.getString("id");
		//查询成本,签单,现付明细
		String sql="select x.*,y.bxzj from "+
		"(select a.id,a.g_id,a.begin_date,a.end_date,a.days,b.line_name, \n"+
				"a.adult_count,a.children_count,a.bx_ticket_xf,a.bx_ticket_qd,a.bx_ticket_sxf, \n"+
				"a.bx_ticket_xf+a.bx_ticket_qd+a.bx_ticket_sxf ticket_zj, a.bx_hotel_qd, \n"+
				"a.bx_hotel_xf,a.bx_hotel_qd+a.bx_hotel_xf hotel_zj,a.bx_veh_qd, \n"+
				"a.bx_veh_xf,a.bx_veh_qd+a.bx_veh_xf veh_zj,a.bx_dinnerroom_xf, \n"+
				"a.bx_dinnerroom_qd,a.bx_dinnerroom_xf+a.bx_dinnerroom_qd dinner_zj, \n"+
				"a.bx_scenery_xf,a.bx_scenery_qd,a.bx_scenery_xf+a.bx_scenery_qd scenery_zj,\n"+
				"nvl(a.ought_money_count,0)ought_money_count,nvl(a.sign_money_count,0)sign_money_count, \n"+
				"nvl(a.net_profit,0)net_profit \n"+
				"from TA_ZT_GROUP a,TA_ZT_LINEMNG b where a.line_id=b.line_id and a.id="+id+") x, \n"+
				"(select nvl(sum(c.insuranceprice*c.persons ),0) bxzj \n"+
				"from TA_ZT_GROUP a,TA_ZT_YKORDER b,TA_ZT_GINSURANCE c,dmsm d \n"+
				"where c.insurancetype=d.dmz and b.id=c.orderid and a.id=b.g_id and d.dmlb=19 and a.id="+id+") y";
		//导游费用
		String sql2="select c.dmsm1,b.costs from ta_g_guide_do a ,ta_g_guide_other b,dmsm c "+
				"where  a.id=b.ta_g_guide_id "+
				"and b.cost_name_id=c.dmz  and c.dmlb=17 and a.groupid="+id;
		//加点费用
		String sql3="select b.cmpny_name,a.rs,a.rtf,a.cbdj,a.cbje,a.lr,a.zffs,a.xfe/a.rs rjxfe \n"+
				" from ta_bx_jd a,ta_scenery_point b \n"+
				" where a.scenery_id=b.scenery_id and a.groupid="+id;
		//加点提成
		String sql4="select c.dmsm1,b.tcbl,b.tcje " +
				" from ta_bx_jd a,ta_g_jdtc b,dmsm c "+
		" where a.id=b.jdid and b.cyid=c.dmz and c.dmlb=18 and a.groupid="+id;
		//购物点
		String sql5="select a.id,b.cmpny_name from ta_bx_shop a,ta_shoppoint b "+
			" where a.shopid=b.shop_point_id and a.groupid="+id;
		//购物点商品信息
		String sql6="select b.bx_shopid,c.goods_name,b.xfe,b.p_num,b.xfe/b.p_num rjxfe "+
			" from ta_bx_shop a,ta_bx_shop_goods b,ta_shop_goods c "+
			" where a.id=b.bx_shopid and b.goodsid=c.goods_id and a.groupid="+id;
		//购物提成
		String sql7="select c.dmsm1,b.tcbl,b.tcje,b.bx_shopid from ta_bx_shop a,ta_bx_shop_jdtc b,dmsm c "+
		" where a.id=b.bx_shopid and b.cyid=c.dmz and c.dmlb=18 and a.groupid="+id;
		try {
			coreDAO.executeQuery(sql, "moneyInfo", rd);
			coreDAO.executeQuery(sql2, "guideMoney", rd);
			coreDAO.executeQuery(sql3, "jdMoney", rd);
			coreDAO.executeQuery(sql4, "jdtcInfo", rd);
			coreDAO.executeQuery(sql5, "shopList", rd);
			coreDAO.executeQuery(sql6, "goodsMoney",rd);
			coreDAO.executeQuery(sql7, "shoptcInfo", rd);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 1;
	}

	/**
	  * @Title: print_showXCMX
	  * @Description: TODO 行程明细
	  */
	public int print_showXCMX(BizData rd,BizData sd){
		String id=rd.getString("id");
		String sql="select a.id,a.g_id,a.begin_date,a.end_date,a.b_traffic,a.e_traffic, \n"+
			"nvl(a.adult_count,0)adult_count,nvl(a.children_count,0)children_count, \n"+
			"b.line_name,b.instancy_phone,a.line_detail,c.dmsm1 jtjt,d.dmsm1 stjt\n"+
			" from TA_ZT_GROUP a,TA_ZT_LINEMNG b,dmsm c ,dmsm d \n"+
			"where a.line_id=b.line_id and b.b_traffic=c.dmz and b.e_traffic=d.dmz and c.dmlb=2 and d.dmlb=2 and a.id="+id;
		try {
			coreDAO.executeQuery(sql, "xcmxInfo", rd);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 1;
	}

	/**
	  * @Title: djyfzktj
	  * @Description: TODO 地接应付账款统计
	  */
	public int djyfzktj(BizData rd,BizData sd){
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageSize"));
		String th=rd.getString("th");
		String traveName=rd.getString("traveName");
		String bdate=rd.getString("bdate");
		String edate=rd.getString("edate");
		String sql="select c.cmpny_name,a.g_id,nvl(b.pay_money,0)pay_money,nvl(b.yfzk,0)yfzk \n"+
			" from TA_ZT_GROUP a,ta_g_next_agency b,ta_travelagency c \n"+
			"where a.id=b.groupid and b.travel_agc_id=c.travel_agc_id and a.state=2 \n";
		if(!th.equals("")){
			sql+=" and a.g_id like '%"+th+"%'";
		}
		if(!traveName.equals("")){
			sql+=" and c.cmpny_name like '%"+traveName+"%'";
		}
		if(!bdate.equals("")&&!edate.equals("")){
			sql+="and a.begin_date>to_date('"+bdate+"','yyyy-mm-dd') and a.end_date<to_date('"+edate+"','yyyy-mm-dd')";
		}
		try {
			coreDAO.executeQueryPage(sql, "djList",pageNO,pageSize,rd);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 1;
	}

	/**
	  * @Title: hotel_yfzk
	  * @Description: TODO 酒店应付账款统计
	  */
	public int hotel_yfzk(BizData rd,BizData sd){
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageSize"));
		String th=rd.getString("th");
		String bdate=rd.getString("bdate");
		String edate=rd.getString("edate");
		String pro_id=rd.getString("pro_id");
		String city_id=rd.getString("city_id");
		String area_id=rd.getString("area_id");
		String hotelName=rd.getString("hotelName");
		String sql="select x.*,y.rooms from "+
			"(select d.name proName,e.name city,f.name area,c.hotel_name,a.g_id,nvl(b.pay_qd,0)pay_qd,b.pay_xf,b.id \n"+
			"from TA_ZT_GROUP a,ta_bx_hotel b,ta_hotel c,xzqh d,xzqh e,xzqh f \n"+
			"where a.id=b.groupid and b.hotel_id=c.hotel_id and b.privince_id=d.id \n"+
			"and b.city_id=e.id and b.area_id=f.id and a.state=2";
		if(!th.equals("")){
			sql+=" and a.g_id like '%"+th+"%'";
		}
		if(!bdate.equals("")&&!edate.equals("")){
			sql+="and a.begin_date>to_date('"+bdate+"','yyyy-mm-dd') and a.end_date<to_date('"+edate+"','yyyy-mm-dd')";
		}
		if(!"".equals(pro_id)){
		   sql+=" and b.privince_id="+pro_id;	
		}
		if(!"".equals(city_id)){
			sql+=" and b.city_id="+city_id;
		}
		if(!"".equals(area_id)){
			sql+=" and b.area_id="+area_id;
		}
		if(!"".equals(hotelName)){
			sql+=" and c.hotel_name like '%"+hotelName+"%'";
		}
		sql+=")x,(select b.g_hotel_id,sum(b.rooms) rooms \n" +
			"from ta_bx_hotel a,ta_bx_hotel_price b where a.id=b.g_hotel_id and b.rooms>0 \n"+
			"group by b.g_hotel_id,b.rooms)y \n"+
			"where x.id=y.g_hotel_id";
		try {
			coreDAO.executeQueryPage(sql, "hotelYFZK", pageNO, pageSize, rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	  * @Title: scenery_yfzk
	  * @Description: TODO 景点应付账款
	  */
	public int scenery_yfzk(BizData rd,BizData sd){
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageSize"));
		String th=rd.getString("th");
		String bdate=rd.getString("bdate");
		String edate=rd.getString("edate");
		String sceneryName=rd.getString("sceneryName");
		String pro_id=rd.getString("pro_id");
		String city_id=rd.getString("city_id");
		String sql="select x.*,y.persons from \n"+
		"(select d.name city,e.name pro,a.g_id,c.cmpny_name,b.qdje,b.id "+
		"from TA_ZT_GROUP a,ta_bx_scenery b,ta_scenery_point c,xzqh d,xzqh e \n"+
		" where a.id=b.groupid and b.scenery_id=c.scenery_id and c.city_id=d.id and d.pid=e.id \n"+
		" and a.state=2 ";
		if(!"".equals(th)){
			sql+=" and a.g_id like '%"+th+"%'";
		}if(!bdate.equals("")&&!edate.equals("")){
			sql+="and a.begin_date>to_date('"+bdate+"','yyyy-mm-dd') and a.end_date<to_date('"+edate+"','yyyy-mm-dd')";
		}if(!"".equals(sceneryName)){
			sql+=" and c.cmpny_name like '%"+sceneryName+"%'";
		}
		if(!"".equals(pro_id)){
			   sql+=" and d.pid="+pro_id;	
			}
			if(!"".equals(city_id)){
				sql+=" and c.city_id="+city_id;
			}
		sql+=") x,(select b.g_sceneryid,sum(b.persons) persons \n" +
		"from ta_bx_scenery a,ta_bx_scenery_mny b \n"+
		" where a.id=b.g_sceneryid and b.persons>0 \n" +
		"group by b.g_sceneryid,b.persons) y \n"+
		" where x.id=y.g_sceneryid";
		try {
			coreDAO.executeQueryPage(sql, "sceneryYFZK", pageNO, pageSize, rd);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 1;
	}

	/**
	  * @Title: dinner_yfzk
	  * @Description: TODO 餐厅应付账款
	  */
	public int dinner_yfzk(BizData rd,BizData sd){
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageSize"));
		String th=rd.getString("th");
		String bdate=rd.getString("bdate");
		String edate=rd.getString("edate");
		String dinnerName=rd.getString("dinnerName");
		String pro_id=rd.getString("pro_id");
		String city_id=rd.getString("city_id");
		String sql="select d.name pro,e.name city,a.g_id,c.cmpny_name,b.bf_persons+b.dinner_persons persons,nvl(b.qdje,0)qdje"+
			" from TA_ZT_GROUP a,ta_bx_dinner b,ta_dining_room c,xzqh d,xzqh e"+
			" where a.id=b.groupid and b.dining_room_id=c.dining_room_id and b.privince_id=d.id and b.city_id=e.id"+
			" and a.state=2";
		if(!"".equals(th)){
			sql+=" and a.g_id like '%"+th+"%'";
		}if(!bdate.equals("")&&!edate.equals("")){
			sql+=" and a.begin_date>to_date('"+bdate+"','yyyy-mm-dd') and a.end_date<to_date('"+edate+"','yyyy-mm-dd')";
		}if(!"".equals(dinnerName)){
			sql+=" and c.cmpny_name like '%"+dinnerName+"%'";
		}
		if(!"".equals(pro_id)){
			   sql+=" and b.privince_id="+pro_id;	
			}
			if(!"".equals(city_id)){
				sql+=" and b.city_id="+city_id;
			}
		try {
			coreDAO.executeQueryPage(sql, "dinnerYFZK", pageNO, pageSize, rd);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 1;
	}

	/**
	  * @Title: ticket_yfzk
	  * @Description: TODO 票务应付账款
	  */
	public int ticket_yfzk(BizData rd,BizData sd){
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageSize"));
		String th=rd.getString("th");
		String bdate=rd.getString("bdate");
		String edate=rd.getString("edate");
		String ticketName=rd.getString("ticketName");
		String pro_id=rd.getString("pro_id");
		String city_id=rd.getString("city_id");
		String sql="select x.*,y.zs from \n"+
			"(select d.name pro,e.name city,a.g_id,c.cmpny_name,b.qdje,b.id \n"+
			"from TA_ZT_GROUP a,ta_bx_ticket b,ta_ticket c,xzqh d,xzqh e \n"+
			"where a.id=b.groupid and b.ticket_id=c.ticket_id and b.privince_id=d.id \n"+
			"and b.city_id=e.id and a.state=2 \n";
		if(!"".equals(th)){
			sql+=" and a.g_id like '%"+th+"%'";
		}if(!bdate.equals("")&&!edate.equals("")){
			sql+=" and a.begin_date>to_date('"+bdate+"','yyyy-mm-dd') and a.end_date<to_date('"+edate+"','yyyy-mm-dd')";
		}if(!"".equals(ticketName)){
			sql+=" and c.cmpny_name like '%"+ticketName+"%'";
		}
		if(!"".equals(pro_id)){
			   sql+=" and b.privince_id="+pro_id;	
			}
			if(!"".equals(city_id)){
				sql+=" and b.city_id="+city_id;
			}
		sql+=")x,(select b.ticketid,sum(b.zs)zs \n" +
			"from ta_bx_ticket a,ta_bx_ticketdetail  b where a.id=b.ticketid \n"+
			"group by b.ticketid,b.zs)y \n"+
			"where x.id=y.ticketid";
		try {
			coreDAO.executeQueryPage(sql, "ticketYFZK", pageNO, pageSize, rd);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 1;
	}

	/**
	  * @Title: jdfyfx
	  * @Description: TODO 加点分析
	  */
	public int jdfyfx(BizData rd,BizData sd){
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageSize"));
		String sql="select a.g_id,g.name pro,h.name city,f.cmpny_name, \n"+
		"b.xfe,b.cbje,b.rtf*b.rs rtf,b.rs,c.tcje dytc,d.tcje sjtc,e.tcje yjgs \n"+
		" from TA_ZT_GROUP a,ta_bx_jd b,ta_g_jdtc c,ta_g_jdtc d,ta_g_jdtc e,ta_scenery_point f,xzqh g,xzqh h \n"+
		"where a.id=b.groupid and b.id=c.jdid and c.cyid=1 \n"+
		"and b.id=d.jdid and d.cyid=2 and b.id=e.jdid and e.cyid=3 \n"+
		"and b.privince_id=g.id and b.city_id=h.id and b.scenery_id=f.scenery_id";
		String th=rd.getString("th");
		String bdate=rd.getString("bdate");
		String edate=rd.getString("edate");
		if(!"".equals(th)){
			sql+=" and a.g_id like '%"+th+"%'";
		}
		if(!bdate.equals("")&&!edate.equals("")){
			sql+=" and a.begin_date>to_date('"+bdate+"','yyyy-mm-dd') and a.end_date<to_date('"+edate+"','yyyy-mm-dd')";
		}
		sql+=" and a.state=2";
		try {
			coreDAO.executeQueryPage(sql, "jdInfo", pageNO, pageSize, rd);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	  * @Title: cw_gwdfyfx
	  * @Description: TODO 购物费用分析
	  */
	public int cw_gwdfyfx(BizData rd,BizData sd){
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_ZT_GROUP", "pageSize"));
		String sql="select a.g_id,g.name pro,h.name city,f.cmpny_name, \n" +
				"b.p_num,b.xfe,c.tcje dytc,d.tcje sjtc,e.tcje gstc \n"+
			" from TA_ZT_GROUP a,ta_bx_shop b,ta_bx_shop_jdtc c, \n"+
			"ta_bx_shop_jdtc d,ta_bx_shop_jdtc e,ta_shoppoint f, \n"+
			"xzqh g,xzqh h where a.id=b.groupid and b.id=c.bx_shopid \n"+
			"and c.cyid=1 and b.id=d.bx_shopid and d.cyid=2 and b.id=e.bx_shopid \n"+
			"and e.cyid=3 and b.shopid=f.shop_point_id and b.pro_id=g.id \n"+
			"and b.city_id=h.id";
		String th=rd.getString("th");
		String bdate=rd.getString("bdate");
		String edate=rd.getString("edate");
		if(!"".equals(th)){
			sql+=" and a.g_id like '%"+th+"%'";
		}
		if(!bdate.equals("")&&!edate.equals("")){
			sql+=" and a.begin_date>to_date('"+bdate+"','yyyy-mm-dd') and a.end_date<to_date('"+edate+"','yyyy-mm-dd')";
		}
		sql+=" and a.state=2";
		try {
			coreDAO.executeQueryPage(sql, "gwdInfo", pageNO, pageSize, rd);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 1;
	}

	/**
	  * @Title: print_showYKXX
	  * @Description: TODO 打印游客信息
	  */
	public int print_showYKXX(BizData rd,BizData sd){
		String id=rd.getString("id");
		String sql="select a.id,a.g_id,a.begin_date,a.end_date,a.b_traffic, \n"+
			"a.e_traffic,nvl(a.adult_count,0) adult_count,nvl(a.children_count,0)children_count, \n"+
			"b.dmsm1 jtjt,c.dmsm1 stjt,d.line_name \n"+
			" from TA_ZT_GROUP a,dmsm b,dmsm c,TA_ZT_LINEMNG d  \n"+
			" where a.b_traffic=b.dmz and a.e_traffic=c.dmz and b.dmlb=2 and c.dmlb=2 and" +
			" a.line_id=d.line_id and a.id="+id;
		String sql2="select c.* from TA_ZT_GROUP a,TA_ZT_YKORDER b,TA_ZT_VISITOR c \n" +
				" where b.id=c.m_id and a.id=b.g_id and a.id="+id;
		try {
			coreDAO.executeQuery(sql, "groupInfo", rd);
			coreDAO.executeQuery(sql2, "ykxxInfo", rd);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	  * @Title: groupLists
	  * @Description: TODO 返回团队列表
	  */
	public int groupLists(BizData rd,BizData sd){
		
		String tBdate = rd.getString("bDate");
		String lineName = rd.getString("lineName");
		String tid = rd.getString("tid");
		String pageNOStr = rd.getString("pageNO");
		String pageSizeStr = rd.getString("pageSize");
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		String sql ="select a.*,nvl(b.ysrs,0) ysrs,a.maxpersoncount-nvl(b.ysrs,0) syrs\n"+
					" from ( \n"+ 
					"select sum(o.ysrs) ysrs,o.tid \n"+ 
					"from TA_ZT_YKORDER o \n"+ 
					"group by o.tid \n"+ 
					") b,ta_zt_group a \n"+
					"where a.id=b.tid(+)\n" +
					"and a.orgid='"+sd.getString("orgid")+"'\n";
			if(!lineName.equals(""))
				sql += "and a.xlmc like '%"+lineName+"%'\n";
			if(!tBdate.equals(""))
				sql += "and a.begin_date = to_date('"+tBdate+"','yyyy-mm-dd')\n";
			if(!tid.equals(""))
				sql += "and a.id like '%"+tid+"%'\n";
			sql += "order by a.begin_date\n";
		try {
			coreDAO.executeQueryPage(sql, "groupListRs", pageNO, pageSize, rd);
			String sql2 = "select y.* from (\n" + sql + ") x,(\n" +
					"select p.g_id,p.price_ms,p.price_th,d.dmsm1\n" +
					"from ta_zt_gprice p,dmsm d\n" +
					"where p.price_type=d.dmz\n" +
					"and d.dmlb='4'\n" +
					") y where x.id=y.g_id\n";
			coreDAO.executeQuery(sql2, "groupPrices", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 98;
	}
	
	/**
	  * @Title: groupLineAreaLists
	  * @Description: TODO 团队列表 查询
	  */
	public int groupLineAreaLists(BizData rd,BizData sd){
		String tBdate = rd.getString("bDate");
		String lineName = rd.getString("lineName");
		String tid = rd.getString("tid");
		String pageNOStr = rd.getString("pageNO");
		String pageSizeStr = rd.getString("pageSize");
		String lineArea = rd.getString("lineArea");
		int pageNO = Integer.parseInt(pageNOStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		String sql ="select a.*,nvl(b.ysrs,0) ysrs,a.maxpersoncount-nvl(b.ysrs,0) syrs\n"+
					" from ( \n"+ 
					"select sum(o.ysrs) ysrs,o.tid \n"+ 
					"from TA_ZT_YKORDER o \n"+ 
					"group by o.tid \n"+ 
					") b,ta_zt_group a \n"+
					"where a.id=b.tid(+)\n" +
					"and a.orgid=1 \n ";
			if(!"".equals(lineArea))
					sql += "and a.xlqy='"+lineArea+"'\n";
			if(!lineName.equals(""))
				sql += "and a.xlmc like '%"+lineName+"%'\n";
			if(!tBdate.equals(""))
				sql += "and a.begin_date = to_date('"+tBdate+"','yyyy-mm-dd')\n";
			if(!tid.equals(""))
				sql += "and a.id like '%"+tid+"%'\n";
			sql += "order by a.begin_date\n";
		try {
			coreDAO.executeQueryPage(sql, "groupListRs", pageNO, pageSize, rd);
			String sql2 = "select y.* from (\n" + sql + ") x,(\n" +
					"select p.g_id,p.price_ms,p.price_th,d.dmsm1\n" +
					"from ta_zt_gprice p,dmsm d\n" +
					"where p.price_type=d.dmz\n" +
					"and d.dmlb='4'\n" +
					") y where x.id=y.g_id\n";
			coreDAO.executeQuery(sql2, "groupPrices", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 98;
	}
}
