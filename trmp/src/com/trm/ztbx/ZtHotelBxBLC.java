package com.trm.ztbx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * @Title: DjHotelBxBLC.java
 * @Package com.trmdj.rbtGuide
 * @Description: (�ؽӾƵ걨��)
 * @author Kale ym_x@qq.com
 * @date 2011-8-4 ����02:58:11
 * @version V1.0
 */
public class ZtHotelBxBLC extends DBBLC {
	public ZtHotelBxBLC(){
		this.entityName="TA_ZT_BXHOTEL";
	}
	
	
	public int getHotelInfo(BizData rd,BizData sd)throws SQLException{
		String id=rd.getString("id");
		//�Ƶ������Ϣ
		String sql1="select a.hotel_id,a.hotel_name,a.hotel_address,a.hotel_tel, a.hotel_bussiness, \n"+
			"a.hotel_bussiness_phone,c.dmsm1 hotel_dinner_type,b.dmsm1 hotel_level \n"+
			"from ta_hotel a,dmsm b,dmsm c where b.dmlb=6 and a.hotel_level=b.dmz \n"+
			"and c.dmlb=7 and  a.hotel_dinner_type=c.dmz and a.hotel_id="+id;
		coreDAO.executeQuery(sql1, "hotelInfo", rd);
		//�Ƶ�۸�
		String sql2="select b.dmz,b.dmsm1 price_name,a.hprice from ta_hotelprice a,dmsm b \n"+
				"where a.pricename=b.dmz and b.dmlb=10 and a.hotelid="+id;
		coreDAO.executeQuery(sql2, "hotelPrice", rd);
		return 1;
	}
	
	/**
	 * 
	 * @Title: queryHotelBxList
	 * @Description: (��ѯ�Ƶ걨����Ϣ)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    �趨�ļ�
	 * @return int    ��������
	 * @throws
	 */
	public int queryHotelBxList(BizData rd,BizData sd){
		String groupId=rd.getStringByDI("TA_ZT_BXHOTEL","TID",0);
		String flag=rd.getString("flag");
		try {
			if("init".equals(flag)){
				//�ƻ��Ƶ���Ϣ
				String bxhotelinit="select a.id,a.tid,a.sf,a.city,a.xq,a.xj level_id, \n"+
					"a.jdid,a.sfhz,a.rz_time,a.ts,a.zfjs,a.sfzsf,a.bz,a.qdxj,a.xfxj,a.hj,a.jhzt,a.lxr,a.lxrdh,a.jhzdr,b.hotel_name, \n"+
					"b.hotel_level,b.hotel_bussiness_phone,b.hotel_bussiness, \n"+
					"b.hotel_dinner_type,c.dmsm1 hotel_level,d.dmsm1 zclx \n"+
					" from ta_zt_jhhotel a,ta_hotel b,dmsm c,dmsm d where c.dmlb=6 and d.dmlb=7 and b.hotel_level=c.dmz \n"+
					" and b.hotel_dinner_type=d.dmz and \n"+
					"a.jdid=b.hotel_id and a.tid='"+groupId+"' order by a.id";
				
				//�ƻ��Ƶ�۸�
				String bxhoteljginit="select a.id,a.jdjhid,a.jglx,a.jg,a.fjs,b.dmsm1 pricename,b.dmz"+
					" from ta_zt_jhhoteljg a ,dmsm b,ta_zt_jhhotel c where "+
					" a.jglx=b.dmz and b.dmlb=10 and a.jdjhid=c.id and c.tid='"+groupId+"' order by a.id";
				String bxzjxxbinit="select a.qdzszj qdzj,a.xfzszj xfzj,a.zszj zj from ta_tdjdxxzjb_zt a where a.tid='"+groupId+"'";
				coreDAO.executeQuery(bxhotelinit,"hotelBxList",rd);
				coreDAO.executeQuery(bxhoteljginit,"hotelJgBxList",rd);
				coreDAO.executeQuery(bxzjxxbinit, "bxjdJDXXZJB", rd);
			}
			
			if("edit".equals(flag)||"view".equals(flag)){
				//�����Ƶ���Ϣ
				String bxhoteledit="select a.id,a.tid,a.sf,a.city,a.xq,a.xj level_id, \n"+
					"a.jdid,a.sfhz,a.rz_time,a.ts,a.zfjs,a.sfzsf,a.bz,a.qdxj,a.xfxj,a.hj,a.bxr,a.bxzt,b.hotel_name, \n"+
					"b.hotel_level,b.hotel_bussiness_phone,b.hotel_bussiness, \n"+
					"b.hotel_dinner_type,c.dmsm1 hotel_level,d.dmsm1 zclx \n"+
					" from ta_zt_bxhotel a,ta_hotel b,dmsm c,dmsm d where c.dmlb=6 and d.dmlb=7 and b.hotel_level=c.dmz and \n"+
					" b.hotel_dinner_type=d.dmz and \n"+
					"a.jdid=b.hotel_id and a.tid='"+groupId+"' order by a.id";
				
				//�����Ƶ�۸�
				String bxhoteljgedit="select a.id,a.jdjhid,a.jglx,a.jg,a.fjs,b.dmsm1 pricename,b.dmz"+
					" from ta_zt_bxhoteljg a ,dmsm b,ta_zt_bxhotel c where "+
					" a.jglx=b.dmz and b.dmlb=10 and a.jdjhid=c.id and c.tid='"+groupId+"' order by a.id";
				String bxzjxxbEdit="select a.bxjdqd qdzj,a.bxjdxf xfzj,a.jdhj zj from ta_tdbxzjxxb_zt a where a.tid='"+groupId+"'";
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
	 * @Description: (�༭�Ƶ걨����Ϣ)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    �趨�ļ�
	 * @return int    ��������
	 * @throws
	 */
	public int editHotelBx(BizData rd,BizData sd){
		BizData data=new BizData();
		Connection conn=coreDAO.getConnection();
		String[] hotelBxRows=rd.getRowIDs("TA_ZT_BXHOTEL");
		try {
			coreDAO.beginTrasct(conn);
			String groupId=rd.getStringByDI("TA_ZT_BXHOTEL","TID",0);
			//ɾ��ԭ�Ƶ걨��
			data.add("TA_ZT_BXHOTEL", "TID", groupId);
			coreDAO.select(data);
			coreDAO.deleteEntity("TA_ZT_BXHOTEL",data,conn);
			data.remove("TA_ZT_BXHOTEL");
			//ɾ��ԭ�Ƶ�۸���Ϣ
			for(int k=0;k<data.getTableRowsCount("TA_ZT_BXHOTELs");k++){
				String jhid=data.getStringByDI("TA_ZT_BXHOTELs", "ID", k);
				data.add("TA_ZT_BXHOTELJG", "JDJHID", k, jhid);
			}
			data.remove("TA_ZT_BXHOTELs");
			coreDAO.delete(data,conn);
			data.remove("TA_ZT_BXHOTELJG");
			
			//��ӾƵ걨������
			for(int i=0;i<hotelBxRows.length;i++){
				int id=this.queryMaxIDByPara("TA_ZT_BXHOTEL", "ID", null);
				//����Ƶ�۸���Ϣ
				String hotelId=rd.getStringByDI("TA_ZT_BXHOTEL", "JDID", i);
				//��ӾƵ�۸���Ϣ
				addHotelPrice(hotelId,id,rd,conn);
				rd.add("TA_ZT_BXHOTEL", "ID", hotelBxRows[i],id);
				rd.add("TA_ZT_BXHOTEL", "TID", hotelBxRows[i],groupId);
				rd.add("TA_ZT_BXHOTEL", "BXZT", hotelBxRows[i],"Y");
				rd.add("TA_ZT_BXHOTEL", "BXR", hotelBxRows[i],sd.getString("userno"));
			}
			coreDAO.insert("TA_ZT_BXHOTEL", rd, conn);
			rd.remove("TA_ZT_BXHOTEL");
			
			//�����ű��еķ����ܼ�
			updateTDBXZJXXB(rd, data, conn, groupId);
			BizData dtt = new BizData();
			dtt.addAttr("TA_ZT_GROUP", "ID", 0, "oldValue", groupId);
			dtt.add("TA_ZT_GROUP", "STATE", "5");//�޸���״̬Ϊ 5  ʵʩ��
			coreDAO.update("TA_ZT_GROUP", dtt, conn);
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

	/**�����ű��еķ����ܼ�
	 * @param rd
	 * @param data
	 * @param conn
	 * @param groupId
	 * @throws SQLException
	 */
	public void updateTDBXZJXXB(BizData rd, BizData data, Connection conn,
			String groupId) throws SQLException {
		data.add("TA_TDBXZJXXB_ZT", "TID", groupId);
		coreDAO.select(data);
		int rows = data.getTableRowsCount("TA_TDBXZJXXB_ZTs");
		data.remove("TA_TDBXZJXXB_ZTs");
		if(rows > 0){
			rd.addAttr("TA_TDBXZJXXB_ZT", "TID", 0, "oldValue", groupId);
			coreDAO.update("TA_TDBXZJXXB_ZT", rd, conn);
		}else{
			rd.add("TA_TDBXZJXXB_ZT", "TID", groupId);
			coreDAO.insert("TA_TDBXZJXXB_ZT", rd, conn);
		}
	}
	
	/**
	 * 
	 * @Title: addHotelPrice
	 * @Description: (��ӾƵ�۸�)
	 * @param @param hotelId
	 * @param @param jdjhid
	 * @param @param rd
	 * @param @param conn
	 * @param @throws SQLException    �趨�ļ�
	 * @return void    ��������
	 * @throws
	 */
	public void addHotelPrice(String hotelId,int jdjhid,BizData rd, Connection conn)
		throws SQLException{
		String[] priceRows=rd.getRowIDs("hotel"+hotelId);
		for(int j=0;j<priceRows.length;j++){
			int jgId=this.queryMaxIDByPara("TA_ZT_BXHOTELJG", "ID", null);
			rd.add("TA_ZT_BXHOTELJG", "ID", priceRows[j], jgId);
			rd.add("TA_ZT_BXHOTELJG", "JDJHID", priceRows[j], jdjhid);
			rd.add("TA_ZT_BXHOTELJG", "JGLX", priceRows[j], rd.getString("hotel"+hotelId, "priceName", priceRows[j]));
			rd.add("TA_ZT_BXHOTELJG", "JG", priceRows[j], rd.getString("hotel"+hotelId, "price", priceRows[j]));
			rd.add("TA_ZT_BXHOTELJG", "FJS", priceRows[j], rd.getString("hotel"+hotelId, "roomNum", priceRows[j]));
		}
		coreDAO.insert("TA_ZT_BXHOTELJG",rd, conn);
		rd.remove("TA_ZT_BXHOTELJG");
	}
}
