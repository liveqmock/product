package com.trm.comm;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dream.app.district.DistrictBean;
import com.dream.app.district.DistrictImp;
import com.dream.app.district.DistrictItfc;
import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.blc.IBLContext;
import com.dream.bizsdk.common.databus.BizData;

/*
 * 流程管理
 * */
public class XZQHBLC extends DBBLC{

	/**
	 * 两层行政区划需要定义下面的表态变量，最后一级的链接
	 * method=showTreeLayer2();
	 * 三层行政区划不用定义下面的表态变量，其URL在方法showTreeLayer3()内直接定义
	 */
	private static String travelAgcUrl = "listTravelAgc.?TA_TRAVELAGENCY@pageNO=1&TA_TRAVELAGENCY@pageSize=10&TA_TRAVELAGENCY/city_id=";
	private static String dinningRoom = "getAlldinningRoom.?TA_DINING_ROOM@pageNO=1&TA_DINING_ROOM@pageSize=10&TA_DINING_ROOM/city_id=";
	private static String sceneryUrl = "getAllScenery.?TA_SCENERY_POINT@pageNO=1&TA_SCENERY_POINT@pageSize=10&TA_SCENERY_POINT/city_id=";
	private static String shopUrl = "getAllShop.?TA_SHOPPOINT@pageNO=1&TA_SHOPPOINT@pageSize=10&TA_SHOPPOINT/city_id=";
	private static String ticketUrl = "getAllTicket.?TA_TICKET@pageNO=1&TA_TICKET@pageSize=10&TA_TICKET/city_id=";
	private static String carUrl = "getAllCarTeam.?TA_CAR_TEAM@pageNO=1&TA_CAR_TEAM@pageSize=10&TA_CAR_TEAM/city_id=";
	private static String f = "a";
	
	public XZQHBLC(){		
		this.entityName = "XZQH";		
	}
	
	public int showTree(BizData rd,BizData sd) {
		
		List<Xzqh> xzqhList = new ArrayList<Xzqh>();//全部行政区划
		String ywlb = rd.getString("ywlb");
		StringBuffer js = new StringBuffer();
		String ff = rd.getString("ff");
		if("xzqh".equals(ff))
			
			js.append("busiProcTree.add('0',-1,'行政区划','','listLandkreise.?XZQH@pageNO=1&XZQH@pageSize=10&XZQH/PID=0\" target=\"region_right');\n");
		else
			js.append("busiProcTree.add('0',-1,'行政区划','','');\n");
		try {
			query(rd, sd);
			int xzqhRows = rd.getTableRowsCount("xzqhs");
			for(int i=0;i<xzqhRows;i++) {
				
				String currentID = rd.getStringByDI("xzqhs", "id", i);
				String pidFromDB = rd.getStringByDI("xzqhs", "pid", i);
				String nodeName = rd.getStringByDI("xzqhs", "name", i);
				String layer = rd.getStringByDI("xzqhs", "layer", i);
				
				Xzqh xzqh = new Xzqh();
				xzqh.setId(currentID);
				xzqh.setName(nodeName);
				xzqh.setPid(pidFromDB);
				xzqh.setLayer(layer);
				xzqhList.add(xzqh);
			}
			if("xzqh".equals(ff))
				iteratorTreeNodeFromDB(xzqhList, "0", js);
			else if("hotel".equals(ff))
				iteratorTreeNodeFromDB4Hotel(xzqhList, "0", js, 1, ywlb);
			else
				iteratorTreeNodeFromDB4Other(xzqhList, "0", js, 1, ff, ywlb);
			rd.add("tree", js);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 8;
	}
	
	/**
	 * 递归调用，生成TREE节点
	 * @param rd
	 * @param nextPid
	 * @param uri
	 */
	private void iteratorTreeNodeFromDB(List<Xzqh> xzqhList, String pid, StringBuffer js) {
		
		for(int i=0;i<xzqhList.size();i++) {
			
			Xzqh xzqh = xzqhList.get(i);
			if(pid.equals(xzqh.getPid())) {
				js.append("busiProcTree.add('").append(xzqh.getId()).append("',").append(pid).append(",'").append(xzqh.getName()).append("','',");
				js.append("'listLandkreise.?XZQH@pageNO=1&XZQH@pageSize=10&layer=");
				js.append(xzqh.getLayer());
				js.append("&XZQH/PID=").append(xzqh.getId()).append("\" target=\"region_right');\n");
				iteratorTreeNodeFromDB(xzqhList, xzqh.getId(), js);
			}
		}
	}
	
	/**
	 * 递归调用，生成酒店tree，layer=3级
	 * @param rd
	 * @param nextPid
	 * @param uri
	 */
	private void iteratorTreeNodeFromDB4Hotel(List<Xzqh> xzqhList, String pid, StringBuffer js, int layer, String ywlb) {
		
		for(int i=0;i<xzqhList.size();i++) {
			
			Xzqh xzqh = xzqhList.get(i);
			if(pid.equals(xzqh.getPid())) {

				String id = xzqh.getId();
				String name = xzqh.getName();
				String layerStr = xzqh.getLayer();
				if("3".equals(layerStr)){
					
					js.append("busiProcTree.add('"+id+"',"+pid+",'"+name+"','','getAllHotel.?TA_HOTEL@pageNO=1&TA_HOTEL@pageSize=10&TA_HOTEL/AREA_ID="+id+"&ywlb="+ywlb+"\" target=\"hotel');\n");
				}else
					js.append("busiProcTree.add('"+id+"',"+pid+",'"+name+"','','');\n");
				iteratorTreeNodeFromDB4Hotel(xzqhList, xzqh.getId(), js, Integer.parseInt(layerStr), ywlb);
			}
		}
	}
	
	/**
	 * 酒店行政区划外的请求的递归算法layer=2层
	 * @param xzqhList
	 * @param pid
	 * @param js
	 * @param layer
	 */
	private void iteratorTreeNodeFromDB4Other(List<Xzqh> xzqhList, String pid, StringBuffer js, int layer, String ff, String ywlb) {
		
		for(int i=0;i<xzqhList.size();i++) {
			
			Xzqh xzqh = xzqhList.get(i);
			if(pid.equals(xzqh.getPid())) {

				String url = "";
				String id = xzqh.getId();
				String name = xzqh.getName();
				String layerStr = xzqh.getLayer();

				if(ff.equals("travelAgc")){
					url = travelAgcUrl+id+"&TA_TRAVELAGENCY/ywlb="+ywlb;
				}
				if(ff.equals("dinningRoom")){
					url = dinningRoom+id+"&TA_DINING_ROOM/ywlb="+ywlb;
				}
				if(ff.equals("scenery")){
					url = sceneryUrl+id+"&TA_SCENERY_POINT/ywlb="+ywlb;
				}
				if(ff.equals("ticket")){
					url = ticketUrl+id+"&TA_TICKET/ywlb="+ywlb;
				}
				if(ff.equals("shop")){
					url = shopUrl+id+"&TA_SHOPPOINT/ywlb="+ywlb;
				}
				if(ff.equals("car")){
					url = carUrl+id+"&TA_CAR_TEAM/ywlb="+ywlb;
				}
				
				if("2".equals(layerStr)){
					
					js.append("busiProcTree.add('"+id+"',"+pid+",'"+name+"','','"+url+"\" target=\""+ff+"');\n");
					//break;
				}else
					js.append("busiProcTree.add('"+id+"',"+pid+",'"+name+"','','');\n");
				iteratorTreeNodeFromDB4Other(xzqhList, xzqh.getId(), js, Integer.parseInt(layerStr), ff, ywlb);
			}
		}
	}
	
	/**
	 * 基础信息使用的省市级树型结构
	 * @param rd
	 * @param sd
	 * @return
	 * @throws SQLException
	 */
	public int showTreeLayer2(BizData rd,BizData sd) throws SQLException {
		
		String fromTarget = rd.getString("ff");
		String url = "";
		if(fromTarget.equals("travelAgc")){
			url = travelAgcUrl;
		}
		if(fromTarget.equals("dinningRoom")){
			url = dinningRoom;
		}
		if(fromTarget.equals("scenery")){
			url = sceneryUrl;
		}
		if(fromTarget.equals("ticket")){
			url = ticketUrl;
		}
		if(fromTarget.equals("shop")){
			url = shopUrl;
		}
		if(fromTarget.equals("car")){
			url = carUrl;
		}
		String js = "busiProcTree.add('0',-1,'行政区划','','');\n";
		String treeTop = "";
		String treeSecond = "";
		//用来放置根节点，即PID=0的
		Map mapTop = new HashMap();
		//存放除PID不等于0的
		List listSparePID = new ArrayList();
		//存放所有PID等于0的
		List listTop = new ArrayList();
		query(rd, sd);
		
		//缓存所有PID等于0的
		for(int i=0;i<rd.getTableRowsCount("XZQHS");i++) {
			
			String pid = rd.getStringByDI("XZQHs", "pid", i);
			String id = rd.getStringByDI("XZQHs", "id", i);
			String name = rd.getStringByDI("XZQHs", "name", i);
			if("0".equals(pid)){
				listTop.add(id+":"+pid+":"+name);
				treeTop += "busiProcTree.add('"+id+"',"+pid+",'"+name+"','','');\n";
			}
		}
		
		//缓存所有PID不等于0的。
		for(int i=0;i<rd.getTableRowsCount("XZQHS");i++){
			
			String pid = rd.getStringByDI("XZQHs", "pid", i);
			String id = rd.getStringByDI("XZQHs", "id", i);
			String name = rd.getStringByDI("XZQHs", "name", i);
			
			if(!"0".equals(pid)){
				listSparePID.add(pid+":"+id+":"+name);
			}
		}
		
		for(int i=0;i<listTop.size();i++){
			String aRs =  (String)listTop.get(i);
			String[] tmp = aRs.split(":");
			String id = tmp[0];
			for(int j=0;j<listSparePID.size();j++){
				
				String aRs2 =  (String)listSparePID.get(j);
				String[] tmp2 = aRs2.split(":");
				String pid2 = tmp2[0];
				String id2 = tmp2[1];
				String name2 = tmp2[2];
				if(id.equals(pid2)){
					treeTop += "busiProcTree.add('"+id2+"',"+pid2+",'"+name2+"','','"+url+id2+"\" target=\""+fromTarget+"');\n";
					continue;
				}
			}
		}
		js = js + treeTop;
		rd.add("tree", js);
		return 102;	
	}
	
	public int queryPage(BizData rd, BizData sd) throws SQLException {
		//this.entityName = "XZQH";	
		String pid = rd.getStringByDI("XZQH","PID",0);
		int ok =super.queryPage(rd, sd);
		rd.add("PID",pid);
		return ok;
	}
	
	public int initID(BizData rd, BizData sd)  throws SQLException{
		
		String fieldName = rd.getString("fldName");
		synchronized (f) {
		
			Connection conn = coreDAO.getConnection();
			String pid = rd.getStringByDI("XZQH", "pid", 0);
			String sql = "";
			ResultSet rs = null;
			//add city
			if ("0000".equals(pid.substring(2, 6))) {

				sql = "select (max(substr(x.id,1,4))+1)||'00' id from xzqh x where substr(x.id,1,2)='"
						+ pid.substring(0, 2) + "'";

				rs = coreDAO.executeQuery(sql, conn);
				rs.next();
				int id = rs.getInt("id");
				rd.add(entityName, fieldName, 0, id);

				// add area
			} else if (!"00".equals(pid.substring(2, 4))
					&& "00".equals(pid.substring(4, 6))) {

				sql = "select max(id)+1 id from xzqh x where substr(x.id,1,4)='"
						+ pid.substring(0, 4) + "'";

				rs = coreDAO.executeQuery(sql, conn);
				rs.next();
				int id = rs.getInt("id");
				rd.add(entityName, fieldName, 0, id);

			}
			rs.getStatement().close();
			rs.close();
			rs = null;
			conn.close();
			conn = null;
		}
		return 209;
	}
	
	public int add(BizData rd, BizData sd) throws SQLException {
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			String layer = rd.getString("layer");
			String pId = rd.getString("PID");
			String gnw = rd.getString("gnw");
			if("0".equals(pId))
				layer = "1";
			else
				layer = String.valueOf(Integer.parseInt(layer)+1);
			int n_id=this.queryMaxIDByPara("XZQH", "ID", null);
			rd.add("XZQH", "ID",n_id);
			rd.add("XZQH", "NAME",rd.getString("name"));
			rd.add("XZQH", "PID",pId);
			rd.add("XZQH", "layer",layer);
			rd.add("XZQH", "gnw",gnw);
			coreDAO.insert("XZQH",rd, conn);
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			coreDAO.rollbackTrasct(conn);
		}finally{
			if(null!=conn){
				conn.close();
			}
		}
		rd.add("PID", rd.getString("PID"));
		return 1;
	}
	public int addPerFee(BizData rd, BizData sd){
		Connection conn=coreDAO.getConnection();
		try{
			coreDAO.beginTrasct(conn);
			String perFee = rd.getString("PRE_PEE");
			String sql = "update xzqh set PRE_PEE = '" + perFee + "' where id ="+rd.getString("id");
			coreDAO.executeUpdate(sql);
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return 0;
	}
	
	public int modify(BizData rd, BizData sd) throws SQLException{
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			this.coreDAO.update(rd);
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			coreDAO.rollbackTrasct(conn);
		}finally{
			if(null!=conn){
				conn.close();
			}
		}
		rd.add("PID", rd.getStringByDI("XZQH","PID",0));
		return 1;
		
	}
	public int del(BizData rd, BizData sd)throws SQLException{
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			this.delete(rd, sd);
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			coreDAO.rollbackTrasct(conn);
		}finally{
			if(null!=conn){
				conn.close();
				}
			}
		return 1;
	}
	public int getById(BizData rd, BizData sd) throws SQLException{
		this.coreDAO.select(rd);
		return 1;
	}
	public int editRTS(BizData rd, BizData sd) throws SQLException{
		return 1;
	}
	//批量删除行政区划
	public int selectDel(BizData rd, BizData sd)throws SQLException{
		String pid=rd.getString("PID");
		String sql="";
		String sql2="";
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			String[] rows=rd.getRowIDs("XZQH");
			for(int a=0;a<rows.length;a++){
				String id=rd.getString("XZQH","CHECKBOX",rows[a]);
				sql="delete from xzqh  where pid="+id;
				this.coreDAO.executeUpdate(sql, conn);
				sql2="delete from xzqh  where id="+id;
				this.coreDAO.executeUpdate(sql2, conn);
			}
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			coreDAO.rollbackTrasct(conn);
		}finally{
			if(null!=conn){
				conn.close();
			}
		}
		rd.add("PID", rd.getString("PID"));
		return 1;
	}
	public int queryXZQH(BizData rd,BizData sd){
		String sql = "select id,pid,name jobname from xzqh where pid<>0";
		String sql1 = "select id,pid,name jobname from xzqh where pid=0";
		String sql2 = "select id,pid,name jobname from xzqh";
		try {
			coreDAO.executeQuery(sql1, "root", rd);
			coreDAO.executeQuery(sql, "child", rd);
			coreDAO.executeQuery(sql2, "nodesAll", rd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 999;
		
	}
	
	public int showXZQH4Base(BizData rd,BizData sd){
		rd.addAttr("xzqh", "layer", 0, "relation", ">=");
		rd.add("xzqh", "layer", "2");
		try {
			coreDAO.select(rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 8;
	}
	
	public int showXZQH4Platform(BizData rd,BizData sd) {
		
		HttpServletRequest request = (HttpServletRequest)rd.get(SysVar.HTTP_REQ);
		IBLContext context = (IBLContext)request.getSession().getServletContext().getAttribute(SysVar.APP_BLCONTEXT);
		List<DistrictBean> provinceBeans = context.getProvinceBeans();
		List<DistrictBean> cityBeans = context.getCityBeans();
		List<DistrictBean> allAreaBeans = context.getDistrictBeans();
		String province = "[";
		String hbdb = "['华北-东北',[";
		String hd   = "['华东   地区',[";
		String hnhz = "['华南-东中',[";
		String xbxn = "['西北-西南',[";
		String gat  = "['港澳台',[";
		
		String proAndCity = "";
		//所有省份
		findProvinceByArea(rd, allAreaBeans, provinceBeans, cityBeans, hbdb, hd, hnhz, xbxn, gat, province, proAndCity);
		System.out.println(proAndCity);
		return 88;
	}
	
	/**
	 * 根据区域划分省分
	 * @param provinceaBeans
	 * @param hbdb
	 * @param hd
	 * @param hnhz
	 * @param hbhn
	 * @param xbxn
	 * @param gat
	 * @param province
	 */
	private void findProvinceByArea(BizData rd, List<DistrictBean> allAreaBeans, List<DistrictBean> provinceBeans, List<DistrictBean> cityBeans, 
			String hbdb, String hd,
			String hnhz, String xbxn, String gat, String province, String proAndCity) {

		DistrictItfc district = new DistrictImp();
		
		List<DistrictBean> copyCityBeans =  new ArrayList<DistrictBean>();
		for(int i=0; i< cityBeans.size(); i++){
			copyCityBeans.add(cityBeans.get(i));
		}
		
		
		
		String hbdb4MC = hbdb;
		String hd4MC   = hd;
		String hnhz4MC = hnhz;
		String xbxn4MC = xbxn;
		String mainCitys = "[";
		
		for(int i=0;i<provinceBeans.size();i++){
			
			DistrictBean bean = (DistrictBean)provinceBeans.get(i);
			String id = bean.getId();
			String name = bean.getName();
			String pId = bean.getpId();
			//华北-东北
			if("4".equals(pId) || "3".equals(pId)){
				hbdb += "'"+id+"',";
			}
			//华东地区
			if("1".equals(pId)){
				hd += "'"+id+"',";
			}
			//华南华中
			if("2".equals(pId) || "5".equals(pId)){
				hnhz += "'"+id+"',";
			}
			//西北-西南
			if("6".equals(pId) || "7".equals(pId)){
				xbxn += "'"+id+"',";
			}
			//港澳台
			if("8".equals(pId)){
				gat += "'"+id+"',";
			}
			
			proAndCity += "ja['"+id+"']='"+name+"';";
			//地点键值匹配数组
			for(int j=0;j<cityBeans.size();j++) {
				DistrictBean cityBean = (DistrictBean)cityBeans.get(j);
				String cityPid = cityBean.getpId();
				String cityId = cityBean.getId();
				String cityName = cityBean.getName();
				if(id.equals(cityPid)) {
					proAndCity += "ja['"+cityId+"']='"+cityName+"';";
				}
				
			}
			proAndCity += "\n";
			
			for(int j=0; j<copyCityBeans.size(); j++){
				DistrictBean cityBean = (DistrictBean)copyCityBeans.get(j);
				String cityPid = cityBean.getpId();
				String cityId = cityBean.getId();
				String mainCity = cityBean.getMainCity();
				String topId = district.upDistrict(cityPid, "0", allAreaBeans);
				//根据城市ID计算出区域ID，再根据区域划分出主要城市
				//华北-东北
				if(mainCity.equals("Y") && (("4".equals(topId) || "3".equals(topId)))) {
					hbdb4MC += "'"+cityId+"',";
					copyCityBeans.remove(cityBean);
				}
				//华东地区
				if(mainCity.equals("Y") && "1".equals(topId)) {
					hd4MC += "'"+cityId+"',";
					copyCityBeans.remove(cityBean);
				}
				//华南华中
				if(mainCity.equals("Y") && ("2".equals(topId) || "5".equals(topId))) {
					hnhz4MC += "'"+cityId+"',";
					copyCityBeans.remove(cityBean);
				}
				//西北-西南
				if(mainCity.equals("Y") && ("6".equals(topId) || "7".equals(topId))) {
					xbxn4MC += "'"+cityId+"',";
					copyCityBeans.remove(cityBean);
				}
			}
		}
		
		hbdb = hbdb.substring(0,hbdb.length()-1);
		hbdb += "]]";
		
		hd = hd.substring(0,hd.length()-1);
		hd += "]]";
		
		hnhz = hnhz.substring(0,hnhz.length()-1);
		hnhz += "]]";
		
		xbxn = xbxn.substring(0,xbxn.length()-1);
		xbxn += "]]";
		
		gat = gat.substring(0,gat.length()-1);
		gat += "]]";
		
		province += hbdb + "," + hd + "," + hnhz + "," + xbxn + "," + gat + "]";
		
		hbdb4MC = hbdb4MC.substring(0,hbdb4MC.length()-1);
		hbdb4MC += "]]";
		
		hd4MC = hd4MC.substring(0,hd4MC.length()-1);
		hd4MC += "]]";
		
		hnhz4MC = hnhz4MC.substring(0,hnhz4MC.length()-1);
		hnhz4MC += "]]";
		
		xbxn4MC = xbxn4MC.substring(0,xbxn4MC.length()-1);
		xbxn4MC += "]]";
		
		mainCitys += hbdb4MC + "," + hd4MC + "," + hnhz4MC + "," + xbxn4MC + "]";
		rd.add("province", province);
		rd.add("proAndCity", proAndCity);
		rd.add("mainCity", mainCitys);
	}

}
