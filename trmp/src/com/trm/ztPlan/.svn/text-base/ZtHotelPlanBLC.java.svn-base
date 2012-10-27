package com.trm.ztPlan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class ZtHotelPlanBLC extends DBBLC{
 public ZtHotelPlanBLC(){
	 this.entityName="TA_ZT_JHHOTEL";
 }
 	public int getHotelInfo(BizData rd,BizData sd)throws SQLException{
		String id=rd.getString("id");
		//酒店基本信息
		String sql1="select a.hotel_id,a.hotel_name,a.hotel_address,a.hotel_tel, a.hotel_bussiness, \n"+
			"a.hotel_bussiness_phone,c.dmsm1 hotel_dinner_type,b.dmsm1 hotel_level \n"+
			"from ta_hotel a,dmsm b,dmsm c where b.dmlb=6 and a.hotel_level=b.dmz \n"+
			"and c.dmlb=7 and  a.hotel_dinner_type=c.dmz and a.ywlb='z' and a.hotel_id="+id;
		coreDAO.executeQuery(sql1, "hotelInfo", rd);
		//酒店价格
		String sql2="select b.dmz,b.dmsm1 price_name,a.hprice from ta_hotelprice a,dmsm b \n"+
				"where a.pricename=b.dmz and b.dmlb=10 and a.hotelid="+id;
		coreDAO.executeQuery(sql2, "hotelPrice", rd);
		return 1;
	}

	/**
	 * 
	 * @Title: queryHotelInfo
	 * @Description: (根据团ID查看酒店计划是否已制定)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int queryHotelInfo(BizData rd,BizData sd){
		String groupID=rd.getStringByDI("TA_ZT_GROUP", "ID", 0);
		String sql="select count(d.id) isNull \n"+
					" from TA_ZT_GROUP g,TA_ZT_JHHOTEL d \n" + 
					"where d.tid=g.id \n" +
					"and g.id='" + groupID + "'";
		try {
			coreDAO.executeQuery(sql, "hotelPlanInfo", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * 
	 * @Title: queryHotelPlanInfo
	 * @Description: (查询酒店计划信息)
	 * @param @param rd
	 * @param @param sd
	 * @param @return
	 * @param @throws SQLException    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int queryHotelPlanInfo(BizData rd,BizData sd)
		throws SQLException {
		String groupId=rd.getString("TID");
		//计划酒店信息
		String sql="select a.id,a.tid,a.sf,a.city,a.xq,a.xj level_id, \n"+
			"a.jdid,a.sfhz,a.rz_time,a.ts,a.zfjs,a.sfzsf,a.bz,a.qdxj,a.xfxj,a.hj,a.jhzt,a.lxr,a.lxrdh,a.jhzdr,b.hotel_name, \n"+
			"b.hotel_level,b.hotel_bussiness_phone,b.hotel_bussiness, \n"+
			"b.hotel_dinner_type,c.dmsm1 hotel_level,d.dmsm1 zclx \n"+
			" from TA_ZT_JHHOTEL a,ta_hotel b,dmsm c,dmsm d where c.dmlb=6 and d.dmlb=7 and b.hotel_level=c.dmz and \n"+
			" b.hotel_dinner_type=d.dmz and \n"+
			"a.jdid=b.hotel_id and a.tid='"+groupId+"' order by a.id";
		coreDAO.executeQuery(sql,"hotelPlanList",rd);
		
		//酒店价格
		String sql2="select a.id,a.jdjhid,a.jglx,a.jg,a.fjs,b.dmsm1 pricename,b.dmz"+
			" from TA_ZT_JHHOTELjg a ,dmsm b,TA_ZT_JHHOTEL c where "+
			" a.jglx=b.dmz and b.dmlb=10 and a.jdjhid=c.id and c.tid='"+groupId+"' order by a.id";
		coreDAO.executeQuery(sql2, "hotelPlanJgList",rd);
		return 1;
	}
	
	/**
	 * 
	 * @Title: editHotelPlan
	 * @Description: (添加酒店计划)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int editHotelPlan(BizData rd,BizData sd){
		BizData data=new BizData();
		Connection conn=coreDAO.getConnection();
		String[] hotelRows=rd.getRowIDs("TA_ZT_JHHOTEL");
		try {
			coreDAO.beginTrasct(conn);
			
			String groupId=rd.getStringByDI("TA_ZT_JHHOTEL","TID",0);
			rd.add("groupId", groupId);
			
			//删除原酒店计划
			data.add("TA_ZT_JHHOTEL", "TID", groupId);
			coreDAO.select(data);
			coreDAO.deleteEntity("TA_ZT_JHHOTEL",data,conn);
			data.remove("TA_ZT_JHHOTEL");
			
			//删除原酒店价格信息
			for(int k=0;k<data.getTableRowsCount("TA_ZT_JHHOTELs");k++){
				String jhid=data.getStringByDI("TA_ZT_JHHOTELs", "ID", k);
				data.add("TA_ZT_JHHOTELJG", "JDJHID", k, jhid);
			}
			data.remove("TA_ZT_JHHOTELs");
			coreDAO.delete(data,conn);
			data.remove("TA_ZT_JHHOTELJG");
			
			for(int i=0;i<hotelRows.length;i++){
				int id=this.queryMaxIDByPara("TA_ZT_JHHOTEL", "ID", null);
				//插入酒店价格,房间数
				String hotelId=rd.getStringByDI("TA_ZT_JHHOTEL", "JDID", i);
				//添加酒店价格信息
				addHotelPrice(hotelId,id,rd,conn);
				rd.add("TA_ZT_JHHOTEL", "ID",hotelRows[i], id);
				rd.add("TA_ZT_JHHOTEL", "TID",hotelRows[i], groupId);
				rd.add("TA_ZT_JHHOTEL", "JHZT", hotelRows[i], "Y");
				rd.add("TA_ZT_JHHOTEL", "JHZDR", hotelRows[i], sd.getString("userno"));
			}
			coreDAO.insert("TA_ZT_JHHOTEL", rd, conn);
			rd.remove("TA_ZT_JHHOTEL");
			
			//更新团表中的费用总计
			updateTDJDXXZJB(rd, data, conn, groupId);
			BizData dtt = new BizData();
			dtt.addAttr("TA_ZT_GROUP", "ID", 0, "oldValue", groupId);
			dtt.add("TA_ZT_GROUP", "STATE", "2");//修改团状态为 2  实施中
			coreDAO.update("TA_ZT_GROUP", dtt, conn);
			coreDAO.commitTrasct(conn);
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

	/**更新团表中的费用总计
	 * @param rd
	 * @param data
	 * @param conn
	 * @param groupId
	 * @throws SQLException
	 */
	public void updateTDJDXXZJB(BizData rd, BizData data, Connection conn,
			String groupId) throws SQLException {
		data.add("TA_TDJDXXZJB_ZT", "TID", groupId);
		coreDAO.select(data);
		int rows = data.getTableRowsCount("TA_TDJDXXZJB_ZTs");
		data.remove("TA_TDJDXXZJB_ZTs");
		if(rows > 0){
			rd.addAttr("TA_TDJDXXZJB_ZT", "TID", 0, "oldValue", groupId);
			coreDAO.update("TA_TDJDXXZJB_ZT", rd, conn);
		}else{
			rd.add("TA_TDJDXXZJB_ZT", "TID", groupId);
			coreDAO.insert("TA_TDJDXXZJB_ZT", rd, conn);
		}
	}
	
	/**
	 * 
	 * @Title: addHotelPrice
	 * @Description: (添加酒店价格)
	 * @param @param hotelId
	 * @param @param jdjhid
	 * @param @param rd
	 * @param @param conn
	 * @param @throws SQLException    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public void addHotelPrice(String hotelId,int jdjhid,BizData rd, Connection conn)
		throws SQLException{
		String[] priceRows=rd.getRowIDs("hotel"+hotelId);
		for(int j=0;j<priceRows.length;j++){
			int jgId=this.queryMaxIDByPara("TA_ZT_JHHOTELJG", "ID", null);
			rd.add("TA_ZT_JHHOTELJG", "ID", priceRows[j],jgId);
			rd.add("TA_ZT_JHHOTELJG", "JDJHID",priceRows[j], jdjhid);
			rd.add("TA_ZT_JHHOTELJG", "JGLX",priceRows[j], rd.getString("hotel"+hotelId, "priceName", priceRows[j]));
			rd.add("TA_ZT_JHHOTELJG", "JG", priceRows[j],rd.getString("hotel"+hotelId, "price", priceRows[j]));
			rd.add("TA_ZT_JHHOTELJG", "FJS", priceRows[j],rd.getString("hotel"+hotelId, "roomNum", priceRows[j]));
		}
		coreDAO.insert("TA_ZT_JHHOTELJG",rd, conn);
		rd.remove("TA_ZT_JHHOTELJG");
	}
	
}
