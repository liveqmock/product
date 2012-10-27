package com.trmdj.businessPlan.plan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * @Title: DjHotelPlanBLC.java
 * @Package com.trmdj.businessPlan.plan
 * @Description: (�Ƶ�ƻ���Ϣ)
 * @author Kale ym_x@qq.com
 * @date 2011-7-18 ����01:59:34
 * @version V1.0
 */
public class DjHotelPlanBLC extends DBBLC {
	public DjHotelPlanBLC(){
		this.entityName="TA_DJ_JHHOTEL";
	}
	
	/** �����Ƶ���Ϣ
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012��2��17��16:27:01
	 * @throws SQLException
	 */
	public int insertHotel(BizData rd, BizData sd) {
		String[] hotelRow = rd.getRowIDs("TA_DJ_JHHOTEL");//��ȡ��¼��
		String groupId = rd.getString("groupId");//��ȡ�ź�
		String temp = rd.getString("temp");//��ȡ�ƻ�״̬
		String zDr = sd.getString("userno");//��ȡָ����
		
		//�����ź�ɾ���ӵ�ƻ�
		BizData data = new BizData();
		data.add("TA_DJ_JHHOTEL", "TID", groupId);
		data.add("TA_DJ_JHHOTEL", "orgid", sd.getString("orgid"));
		
		// �Ƶ��������
		BizData bsData = new BizData();
		
		StringBuffer theNewHotel = new StringBuffer().append("[");
		// �Ƶ�ƻ���Ϣ
		for(int i = 0; i < hotelRow.length; i++)
		{
			int hotelId = queryMaxIDByPara("TA_DJ_JHHOTEL", "ID", null);
			int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_JHHOTEL");
			rd.add("TA_DJ_JHHOTEL", "ID", String.valueOf(fieldIndex[i]), hotelId);
			rd.add("TA_DJ_JHHOTEL", "TID", String.valueOf(fieldIndex[i]), groupId);
			rd.add("TA_DJ_JHHOTEL", "JHZT", String.valueOf(fieldIndex[i]), temp);
			rd.add("TA_DJ_JHHOTEL", "JHZDR", String.valueOf(fieldIndex[i]), zDr);
			
			// ��ӻ���ID
			rd.add("TA_DJ_JHHOTEL", "ORGID", String.valueOf(fieldIndex[i]), sd.getString("orgid"));
			
			// �ж��Ƿ�Ϊ������������
			if ("".equals(rd.getString("TA_DJ_JHHOTEL","JDID",String.valueOf(fieldIndex[i]))))
			{
				// �����Ƶ������Ϣ
				addBaseHotel(bsData, rd, String.valueOf(fieldIndex[i]), sd.getString("orgid"), theNewHotel);
			}
		}
		//�����ľƵ���Ϣ��ID
		if(theNewHotel.lastIndexOf(",") > 0){
			
			String newHotel = theNewHotel.substring(0, theNewHotel.length()-1);
			newHotel = newHotel+"]";
			rd.add("newBaseInfo", newHotel);
		}
		
		Connection conn=coreDAO.getConnection();
		
		try {
			// ��������
			coreDAO.beginTrasct(conn);
			
			coreDAO.delete(data, conn);//ɾ����ʷ����
			data.remove(entityName);
			
			// ����Ƶ�ƻ���Ϣ
			coreDAO.insert("TA_DJ_JHHOTEL", rd, conn);
			
			// ����Ƶ������Ϣ
			coreDAO.insert("ta_hotel", bsData, conn);
			
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
	
	/**
	 * �����Ƶ������Ϣ
	 * 
	 * @param bsData �Ƶ��������
	 * @param rd �������ݶ���
	 * @param index ����
	 * @param orgid ��������ID
	 */
	private void addBaseHotel(BizData bsData, BizData rd, String index, String orgid, StringBuffer theNewHotel)
    {
		// ͳ�ƶ����оƵ����������Ŀ
		int rows = bsData.getTableRowsCount("ta_hotel");
		
		// ȡ���ֵ
		int maxId = queryMaxIDByPara("ta_hotel", "HOTEL_ID", null);
		
		// ��ӾƵ�ID���Ƶ�IDȡ���ֵ
		bsData.add("ta_hotel", "HOTEL_ID", rows, maxId);
		
		// ��ӾƵ�����
		bsData.add("ta_hotel", "HOTEL_NAME", rows, rd.getString("TA_DJ_JHHOTEL", "JDMC", index));
		
		// ��ӾƵ���ϵ��
		bsData.add("ta_hotel", "HOTEL_BUSSINESS", rows, rd.getString("TA_DJ_JHHOTEL", "LXR", index));
		
		// ��ӾƵ���ϵ�˵绰
		bsData.add("ta_hotel", "HOTEL_BUSSINESS_TEL", rows, rd.getString("TA_DJ_JHHOTEL", "LXRDH", index));
		bsData.add("ta_hotel", "HOTEL_LEVEL", rows, "1");
		
		// ��ӻ���ID
		bsData.add("ta_hotel", "orgid", rows, orgid);
		
		// �ƻ�����Ӳ���ID
		rd.add("TA_DJ_JHHOTEL", "JDID", index, maxId);
		
		//���ظ������Ļ�����Ϣ����������ֵ�͵�ǰ������
		theNewHotel.append("{\"indexNm\":"+index+",\"id\":"+maxId+"},");
    }

	/**Ajax��ʼ���Ƶ�ҳ��
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012��2��17��16:27:01
	 * @throws SQLException
	 */
	public int djAjaxHotelInfo(BizData rd, BizData sd) {
		String groupId = rd.getString("groupId");
		String sql = "select * from TA_DJ_JHHOTEL  where tid='"+groupId+"' and orgid='"+sd.getString("orgid")+"'";
		try {
			coreDAO.executeQuery(sql, "TA_DJ_JHHOTEL", rd);
			sql="select begin_date,ts from ta_dj_group where id ='"+groupId+"' and orgid='"+sd.getString("orgid")+"'";
			coreDAO.executeQuery(sql, "TA_DJ_GROUPs", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int tRow = rd.getTableRowsCount("TA_DJ_JHHOTEL");//��ȡ��ѯ��¼����
		int random = 0;//װ�����
		
		for(int i = 0; i < tRow; i++){
			random = (int) (Math.floor(Math.random() * 123450));
			rd.add("TA_DJ_JHHOTEL","Random", i, random);//װ���漴��
		}
		return 999;
	}
	
	
	/**
	 * 
	 * @Title: queryHotelInfo
	 * @Description: (������ID�鿴�Ƶ�ƻ��Ƿ����ƶ�)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    �趨�ļ�
	 * @return int    ��������
	 * @throws
	 */
	public int queryHotelInfo(BizData rd,BizData sd){
		String groupID=rd.getStringByDI("TA_DJ_GROUP", "ID", 0);
		String sql="select count(d.id) isNull \n"+
					" from ta_dj_group g,ta_dj_jhhotel d \n" + 
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
	 * @Description: (��ѯ�Ƶ�ƻ���Ϣ)
	 * @param @param rd
	 * @param @param sd
	 * @param @return
	 * @param @throws SQLException    �趨�ļ�
	 * @return int    ��������
	 * @throws
	 */
	public int queryHotelPlanInfo(BizData rd,BizData sd)
		throws SQLException {
		String groupId=rd.getString("TID");
		//�ƻ��Ƶ���Ϣ
		String sql="select a.id,a.tid,a.sf,a.city,a.xq,a.xj level_id, \n"+
			"a.jdid,a.sfhz,a.rz_time,a.ts,a.zfjs,a.sfzsf,a.bz,a.qdxj,a.xfxj,a.hj,a.jhzt,a.lxr,a.lxrdh,a.jhzdr,b.hotel_name, \n"+
			"b.hotel_level,b.hotel_bussiness_phone,b.hotel_bussiness, \n"+
			"b.hotel_dinner_type,c.dmsm1 hotel_level,d.dmsm1 zclx \n"+
			" from ta_dj_jhhotel a,ta_hotel b,dmsm c,dmsm d where c.dmlb=6 and d.dmlb=7 and b.hotel_level=c.dmz and \n"+
			" b.hotel_dinner_type=d.dmz and \n"+
			"a.jdid=b.hotel_id and a.tid='"+groupId+"' order by a.id";
		coreDAO.executeQuery(sql,"hotelPlanList",rd);
		
		//�Ƶ�۸�
		String sql2="select a.id,a.jdjhid,a.jglx,a.jg,a.fjs,b.dmsm1 pricename,b.dmz"+
			" from ta_dj_jhhoteljg a ,dmsm b,ta_dj_jhhotel c where "+
			" a.jglx=b.dmz and b.dmlb=10 and a.jdjhid=c.id and c.tid='"+groupId+"' order by a.id";
		coreDAO.executeQuery(sql2, "hotelPlanJgList",rd);
		return 1;
	}
	
	/**
	 * 
	 * @Title: editHotelPlan
	 * @Description: (��ӾƵ�ƻ�)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    �趨�ļ�
	 * @return int    ��������
	 * @throws
	 */
	public int editHotelPlan(BizData rd,BizData sd){
		BizData data=new BizData();
		Connection conn=coreDAO.getConnection();
		String[] hotelRows=rd.getRowIDs("TA_DJ_JHHOTEL");
		try {
			coreDAO.beginTrasct(conn);
			
			String groupId=rd.getStringByDI("TA_DJ_JHHOTEL","TID",0);
			//ɾ��ԭ�Ƶ�ƻ�
			data.add("TA_DJ_JHHOTEL", "TID", groupId);
			coreDAO.select(data);
			coreDAO.deleteEntity("TA_DJ_JHHOTEL",data,conn);
			data.remove("TA_DJ_JHHOTEL");
			
			//ɾ��ԭ�Ƶ�۸���Ϣ
			for(int k=0;k<data.getTableRowsCount("TA_DJ_JHHOTELs");k++){
				String jhid=data.getStringByDI("TA_DJ_JHHOTELs", "ID", k);
				data.add("TA_DJ_JHHOTELJG", "JDJHID", k, jhid);
			}
			data.remove("TA_DJ_JHHOTELs");
			coreDAO.delete(data,conn);
			data.remove("TA_DJ_JHHOTELJG");
			
			for(int i=0;i<hotelRows.length;i++){
				int id=this.queryMaxIDByPara("TA_DJ_JHHOTEL", "ID", null);
				//����Ƶ�۸�,������
				String hotelId=rd.getStringByDI("TA_DJ_JHHOTEL", "JDID", i);
				//��ӾƵ�۸���Ϣ
				addHotelPrice(hotelId,id,rd,conn);
				rd.add("TA_DJ_JHHOTEL", "ID",hotelRows[i], id);
				rd.add("TA_DJ_JHHOTEL", "TID",hotelRows[i], groupId);
				rd.add("TA_DJ_JHHOTEL", "JHZT", hotelRows[i], "Y");
				rd.add("TA_DJ_JHHOTEL", "JHZDR", hotelRows[i], sd.getString("userno"));
			}
			coreDAO.insert("TA_DJ_JHHOTEL", rd, conn);
			rd.remove("TA_DJ_JHHOTEL");
			
			//�����ű��еķ����ܼ�
			updateTDJDXXZJB(rd, data, conn, groupId);
			BizData dtt = new BizData();
			dtt.addAttr("TA_DJ_GROUP", "ID", 0, "oldValue", groupId);
			dtt.add("TA_DJ_GROUP", "STATE", "2");//�޸���״̬Ϊ 2  ʵʩ��
			coreDAO.update("TA_DJ_GROUP", dtt, conn);
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

	/**�����ű��еķ����ܼ�
	 * @param rd
	 * @param data
	 * @param conn
	 * @param groupId
	 * @throws SQLException
	 */
	public void updateTDJDXXZJB(BizData rd, BizData data, Connection conn,
			String groupId) throws SQLException {
		data.add("TA_TDJDXXZJB", "TID", groupId);
		coreDAO.select(data);
		int rows = data.getTableRowsCount("TA_TDJDXXZJBs");
		data.remove("TA_TDJDXXZJBs");
		if(rows > 0){
			rd.addAttr("TA_TDJDXXZJB", "TID", 0, "oldValue", groupId);
			coreDAO.update("TA_TDJDXXZJB", rd, conn);
		}else{
			rd.add("TA_TDJDXXZJB", "TID", groupId);
			coreDAO.insert("TA_TDJDXXZJB", rd, conn);
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
			int jgId=this.queryMaxIDByPara("TA_DJ_JHHOTELJG", "ID", null);
			rd.add("TA_DJ_JHHOTELJG", "ID", priceRows[j],jgId);
			rd.add("TA_DJ_JHHOTELJG", "JDJHID",priceRows[j], jdjhid);
			rd.add("TA_DJ_JHHOTELJG", "JGLX",priceRows[j], rd.getString("hotel"+hotelId, "priceName", priceRows[j]));
			rd.add("TA_DJ_JHHOTELJG", "JG", priceRows[j],rd.getString("hotel"+hotelId, "price", priceRows[j]));
			rd.add("TA_DJ_JHHOTELJG", "FJS", priceRows[j],rd.getString("hotel"+hotelId, "roomNum", priceRows[j]));
		}
		coreDAO.insert("TA_DJ_JHHOTELJG",rd, conn);
		rd.remove("TA_DJ_JHHOTELJG");
	}
	
}
