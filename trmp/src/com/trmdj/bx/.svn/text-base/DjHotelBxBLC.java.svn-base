package com.trmdj.bx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * @Title: DjHotelBxBLC.java
 * @Package com.trmdj.rbtGuide
 * @Description: (地接酒店报销)
 * @author Kale ym_x@qq.com
 * @date 2011-8-4 下午02:58:11
 * @version V1.0
 */
public class DjHotelBxBLC extends DBBLC {
	public DjHotelBxBLC(){
		this.entityName="TA_DJ_BXHOTEL";
	}
	
	/** 新增酒店报账信息
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int insertHotel(BizData rd,BizData sd)throws SQLException{
		String[] hotelRow = rd.getRowIDs("TA_DJ_BXHOTEL");//获取记录数
		String groupId = rd.getString("groupId");//获取团号
		String temp = rd.getString("temp");//获取报账状态
		String zDr = sd.getString("userno");//获取指定人
		
		BizData data = new BizData();
		data.add("TA_DJ_BXHOTEL", "TID", groupId);
		data.add("TA_DJ_BXHOTEL", "orgid", sd.getString("orgid"));
		
		// 酒店基础数据
		BizData bsData = new BizData();
		
		StringBuffer theNewHotel = new StringBuffer().append("[");
		
		//酒店报账信息
		int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_BXHOTEL");
		for(int i = 0; i < hotelRow.length; i++){
			int hotelId = queryMaxIDByPara("TA_DJ_BXHOTEL", "ID", null);
			rd.add("TA_DJ_BXHOTEL","ID",String.valueOf(fieldIndex[i]),hotelId);
			rd.add("TA_DJ_BXHOTEL","TID",String.valueOf(fieldIndex[i]),groupId);
			rd.add("TA_DJ_BXHOTEL","JHZT",String.valueOf(fieldIndex[i]),temp);
			rd.add("TA_DJ_BXHOTEL","JHZDR",String.valueOf(fieldIndex[i]),zDr);
			rd.add("TA_DJ_BXHOTEL","TID",String.valueOf(fieldIndex[i]),groupId);
			rd.add("TA_DJ_BXHOTEL","orgid",String.valueOf(fieldIndex[i]),sd.getString("orgid"));
			// 判断是否为库中已有数据
			if ("".equals(rd.getString("TA_DJ_BXHOTEL","JDID",String.valueOf(fieldIndex[i]))))
			{
				// 新增酒店基础信息
				addBaseHotel(bsData, rd, String.valueOf(fieldIndex[i]), sd.getString("orgid"), theNewHotel);
			}
		}
		
		//新增的酒店信息的ID
		if(theNewHotel.lastIndexOf(",") > 0){
			
			String newHotel = theNewHotel.substring(0, theNewHotel.length()-1);
			newHotel = newHotel+"]";
			rd.add("newBaseInfo", newHotel);
		}
		
		Connection conn=coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			
			coreDAO.delete(data, conn);//删除历史数据
			data.remove(entityName);
			
			//插入酒店报账信息
			coreDAO.insert("TA_DJ_BXHOTEL", rd, conn);
			rd.remove(entityName);
			
			// 插入酒店基础信息
			coreDAO.insert("ta_hotel", bsData, conn);
			bsData.remove("ta_hotel");
			
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
		return 999;
	}
	
	/**Ajax初始化酒店报账页面
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012年2月17日16:27:01
	 * @throws SQLException
	 */
	public int djAjaxHotelInfo(BizData rd, BizData sd) {
		int tRow = 0; //记录行数
		int random = 0;//随机数
		String groupId = rd.getString("groupId");//团号
		String sql = "";//SQL
		
		try {
			sql = "select * from TA_DJ_BXHOTEL where tid='"+groupId+"' and orgid="+sd.getString("orgid");
			coreDAO.executeQuery(sql, "TA_DJ_BXHOTEL", rd);
			tRow = rd.getTableRowsCount("TA_DJ_BXHOTEL");
			if(tRow < 1){//判断是否新增
				sql = "select * from TA_DJ_JHHOTEL  where tid='"+groupId+"' and orgid="+sd.getString("orgid");
				coreDAO.executeQuery(sql, "TA_DJ_BXHOTEL", rd);
			}
			sql="select begin_date,ts from ta_dj_group where id ='"+groupId+"' and orgid="+sd.getString("orgid");
			coreDAO.executeQuery(sql, "TA_DJ_GROUPs", rd);//团信息
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		tRow = rd.getTableRowsCount("TA_DJ_BXHOTEL");//获取行数
		for(int i = 0; i < tRow; i++){
			random = (int) (Math.floor(Math.random() * 123450));
			rd.add("TA_DJ_BXHOTEL","Random", i, random);//装入随即数
		}
		return 999;
	}
	
	/**
	 * 新增酒店基础信息
	 * 
	 * @param bsData 酒店基础数据
	 * @param rd 请求数据对象
	 * @param index 索引
	 * @param orgid 所属机构ID
	 */
	private void addBaseHotel(BizData bsData, BizData rd, String index, String orgid, StringBuffer theNewHotel)
    {
		// 统计对象中酒店基础数据数目
		int rows = bsData.getTableRowsCount("ta_hotel");
		
		// 取最大值
		int maxId = queryMaxIDByPara("ta_hotel", "HOTEL_ID", null);
		
		// 添加酒店ID，酒店ID取最大值
		bsData.add("ta_hotel", "HOTEL_ID", rows, maxId);
		
		// 添加酒店名称
		bsData.add("ta_hotel", "HOTEL_NAME", rows, rd.getString("TA_DJ_BXHOTEL", "JDMC", index));
		
		// 添加酒店联系人
		bsData.add("ta_hotel", "HOTEL_BUSSINESS", rows, rd.getString("TA_DJ_BXHOTEL", "LXR", index));
		
		// 添加酒店联系人电话
		bsData.add("ta_hotel", "HOTEL_BUSSINESS_TEL", rows, rd.getString("TA_DJ_BXHOTEL", "LXRDH", index));
		bsData.add("ta_hotel", "HOTEL_LEVEL", rows, "1");
		
		// 添加机构ID
		bsData.add("ta_hotel", "orgid", rows, orgid);
		
		// 计划中添加餐厅ID
		rd.add("TA_DJ_BXHOTEL", "JDID", index, maxId);
		
		//返回该新增的基础信息的数组索引值和当前的主键
		theNewHotel.append("{\"indexNm\":"+index+",\"id\":"+maxId+"},");
    }
	
	
	public int getHotelInfo(BizData rd,BizData sd)throws SQLException{
		String id=rd.getString("id");
		//酒店基本信息
		String sql1="select a.hotel_id,a.hotel_name,a.hotel_address,a.hotel_tel, a.hotel_bussiness, \n"+
			"a.hotel_bussiness_phone,c.dmsm1 hotel_dinner_type,b.dmsm1 hotel_level \n"+
			"from ta_hotel a,dmsm b,dmsm c where b.dmlb=6 and a.hotel_level=b.dmz \n"+
			"and c.dmlb=7 and  a.hotel_dinner_type=c.dmz and a.hotel_id="+id;
		coreDAO.executeQuery(sql1, "hotelInfo", rd);
		//酒店价格
		String sql2="select b.dmz,b.dmsm1 price_name,a.hprice from ta_hotelprice a,dmsm b \n"+
				"where a.pricename=b.dmz and b.dmlb=10 and a.hotelid="+id;
		coreDAO.executeQuery(sql2, "hotelPrice", rd);
		return 1;
	}
	
	/**
	 * 
	 * @Title: queryHotelBxList
	 * @Description: (查询酒店报销信息)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int queryHotelBxList(BizData rd,BizData sd){
		String groupId=rd.getStringByDI("TA_DJ_BXHOTEL","TID",0);
		String flag=rd.getString("flag");
		try {
			if("init".equals(flag)){
				//计划酒店信息
				String bxhotelinit="select a.id,a.tid,a.sf,a.city,a.xq,a.xj level_id, \n"+
					"a.jdid,a.sfhz,a.rz_time,a.ts,a.zfjs,a.sfzsf,a.bz,a.qdxj,a.xfxj,a.hj,a.jhzt,a.lxr,a.lxrdh,a.jhzdr,b.hotel_name, \n"+
					"b.hotel_level,b.hotel_bussiness_phone,b.hotel_bussiness, \n"+
					"b.hotel_dinner_type,c.dmsm1 hotel_level,d.dmsm1 zclx \n"+
					" from ta_dj_jhhotel a,ta_hotel b,dmsm c,dmsm d where c.dmlb=6 and d.dmlb=7 and b.hotel_level=c.dmz \n"+
					" and b.hotel_dinner_type=d.dmz and \n"+
					"a.jdid=b.hotel_id and a.tid='"+groupId+"' order by a.id";
				
				//计划酒店价格
				String bxhoteljginit="select a.id,a.jdjhid,a.jglx,a.jg,a.fjs,b.dmsm1 pricename,b.dmz"+
					" from ta_dj_jhhoteljg a ,dmsm b,ta_dj_jhhotel c where "+
					" a.jglx=b.dmz and b.dmlb=10 and a.jdjhid=c.id and c.tid='"+groupId+"' order by a.id";
				String bxzjxxbinit="select a.qdzszj qdzj,a.xfzszj xfzj,a.zszj zj from ta_tdjdxxzjb a where a.tid='"+groupId+"'";
				coreDAO.executeQuery(bxhotelinit,"hotelBxList",rd);
				coreDAO.executeQuery(bxhoteljginit,"hotelJgBxList",rd);
				coreDAO.executeQuery(bxzjxxbinit, "bxjdJDXXZJB", rd);
			}
			
			if("edit".equals(flag)||"view".equals(flag)){
				//报销酒店信息
				String bxhoteledit="select a.id,a.tid,a.sf,a.city,a.xq,a.xj level_id, \n"+
					"a.jdid,a.sfhz,a.rz_time,a.ts,a.zfjs,a.sfzsf,a.bz,a.qdxj,a.xfxj,a.hj,a.bxr,a.bxzt,b.hotel_name, \n"+
					"b.hotel_level,b.hotel_bussiness_phone,b.hotel_bussiness, \n"+
					"b.hotel_dinner_type,c.dmsm1 hotel_level,d.dmsm1 zclx \n"+
					" from ta_dj_bxhotel a,ta_hotel b,dmsm c,dmsm d where c.dmlb=6 and d.dmlb=7 and b.hotel_level=c.dmz and \n"+
					" b.hotel_dinner_type=d.dmz and \n"+
					"a.jdid=b.hotel_id and a.tid='"+groupId+"' order by a.id";
				
				//报销酒店价格
				String bxhoteljgedit="select a.id,a.jdjhid,a.jglx,a.jg,a.fjs,b.dmsm1 pricename,b.dmz"+
					" from ta_dj_bxhoteljg a ,dmsm b,ta_dj_bxhotel c where "+
					" a.jglx=b.dmz and b.dmlb=10 and a.jdjhid=c.id and c.tid='"+groupId+"' order by a.id";
				String bxzjxxbEdit="select a.bxjdqd qdzj,a.bxjdxf xfzj,a.jdhj zj from ta_tdbxzjxxb a where a.tid='"+groupId+"'";
				coreDAO.executeQuery(bxhoteledit,"hotelBxList",rd);
				coreDAO.executeQuery(bxhoteljgedit,"hotelJgBxList",rd);
				coreDAO.executeQuery(bxzjxxbEdit, "bxjdJDXXZJB", rd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * 
	 * @Title: editHotelBx
	 * @Description: (编辑酒店报销信息)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int editHotelBx(BizData rd,BizData sd){
		BizData data=new BizData();
		Connection conn=coreDAO.getConnection();
		String[] hotelBxRows=rd.getRowIDs("TA_DJ_BXHOTEL");
		try {
			coreDAO.beginTrasct(conn);
			String groupId=rd.getStringByDI("TA_DJ_BXHOTEL","TID",0);
			//删除原酒店报销
			data.add("TA_DJ_BXHOTEL", "TID", groupId);
			coreDAO.select(data);
			coreDAO.deleteEntity("TA_DJ_BXHOTEL",data,conn);
			data.remove("TA_DJ_BXHOTEL");
			//删除原酒店价格信息
			for(int k=0;k<data.getTableRowsCount("TA_DJ_BXHOTELs");k++){
				String jhid=data.getStringByDI("TA_DJ_BXHOTELs", "ID", k);
				data.add("TA_DJ_BXHOTELJG", "JDJHID", k, jhid);
			}
			data.remove("TA_DJ_BXHOTELs");
			coreDAO.delete(data,conn);
			data.remove("TA_DJ_BXHOTELJG");
			
			//添加酒店报销数据
			for(int i=0;i<hotelBxRows.length;i++){
				int id=this.queryMaxIDByPara("TA_DJ_BXHOTEL", "ID", null);
				//插入酒店价格信息
				String hotelId=rd.getStringByDI("TA_DJ_BXHOTEL", "JDID", i);
				//添加酒店价格信息
				addHotelPrice(hotelId,id,rd,conn);
				rd.add("TA_DJ_BXHOTEL", "ID", hotelBxRows[i],id);
				rd.add("TA_DJ_BXHOTEL", "TID", hotelBxRows[i],groupId);
				rd.add("TA_DJ_BXHOTEL", "BXZT", hotelBxRows[i],"Y");
				rd.add("TA_DJ_BXHOTEL", "BXR", hotelBxRows[i],sd.getString("userno"));
			}
			coreDAO.insert("TA_DJ_BXHOTEL", rd, conn);
			rd.remove("TA_DJ_BXHOTEL");
			
			//更新团表中的费用总计
			updateTDBXZJXXB(rd, data, conn, groupId);
			BizData dtt = new BizData();
			dtt.addAttr("TA_DJ_GROUP", "ID", 0, "oldValue", groupId);
			dtt.add("TA_DJ_GROUP", "STATE", "5");//修改团状态为 5  实施中
			coreDAO.update("TA_DJ_GROUP", dtt, conn);
			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		} finally{
			try {
				if(null!=conn){
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
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
	public void updateTDBXZJXXB(BizData rd, BizData data, Connection conn,
			String groupId) throws SQLException {
		data.add("TA_TDBXZJXXB", "TID", groupId);
		coreDAO.select(data);
		int rows = data.getTableRowsCount("TA_TDBXZJXXBs");
		data.remove("TA_TDBXZJXXBs");
		if(rows > 0){
			rd.addAttr("TA_TDBXZJXXB", "TID", 0, "oldValue", groupId);
			coreDAO.update("TA_TDBXZJXXB", rd, conn);
		}else{
			rd.add("TA_TDBXZJXXB", "TID", groupId);
			coreDAO.insert("TA_TDBXZJXXB", rd, conn);
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
			int jgId=this.queryMaxIDByPara("TA_DJ_BXHOTELJG", "ID", null);
			rd.add("TA_DJ_BXHOTELJG", "ID", priceRows[j], jgId);
			rd.add("TA_DJ_BXHOTELJG", "JDJHID", priceRows[j], jdjhid);
			rd.add("TA_DJ_BXHOTELJG", "JGLX", priceRows[j], rd.getString("hotel"+hotelId, "priceName", priceRows[j]));
			rd.add("TA_DJ_BXHOTELJG", "JG", priceRows[j], rd.getString("hotel"+hotelId, "price", priceRows[j]));
			rd.add("TA_DJ_BXHOTELJG", "FJS", priceRows[j], rd.getString("hotel"+hotelId, "roomNum", priceRows[j]));
		}
		coreDAO.insert("TA_DJ_BXHOTELJG",rd, conn);
		rd.remove("TA_DJ_BXHOTELJG");
	}
}
