package com.trmdj.businessPlan.plan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * @Title: DjDinnerPlanBLC.java
 * @Package com.trmdj.businessPlan.plan
 * @Description: (�����ƻ���Ϣ)
 * @author Kale ym_x@qq.com
 * @date 2011-7-22 ����10:52:58
 * @version V1.0
 */
public class DjDinnerPlanBLC extends DBBLC {
	public DjDinnerPlanBLC(){
		this.entityName="TA_DJ_JHCT";		
	}
	
	/** ����������Ϣ
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012��2��17��16:27:01
	 * @throws SQLException
	 */
	public int insertDinner(BizData rd, BizData sd) throws SQLException{
		String[] hotelRow = rd.getRowIDs("TA_DJ_JHCT");//��ȡ��¼��
		String groupId = rd.getString("groupId");//��ȡ�ź�
		String temp = rd.getString("temp");//��ȡ�ƻ�״̬
		String zDr = sd.getString("userno");//��ȡָ����
		
		//�����ź�ɾ���ӵ�ƻ�
		BizData data = new BizData();
		data.add("TA_DJ_JHCT", "TID", groupId);
		data.add("TA_DJ_JHCT", "orgid", sd.getString("orgid"));
		
		// ������������
		BizData bsData = new BizData();
		StringBuffer theNewDr = new StringBuffer().append("[");
		
		for(int i = 0; i < hotelRow.length; i++){
			int hotelId = queryMaxIDByPara("TA_DJ_JHCT", "ID", null);
			int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_JHCT");
			rd.add("TA_DJ_JHCT", "ID", String.valueOf(fieldIndex[i]), hotelId);
			rd.add("TA_DJ_JHCT", "TID", String.valueOf(fieldIndex[i]), groupId);
			rd.add("TA_DJ_JHCT", "JHZT", String.valueOf(fieldIndex[i]), temp);
			rd.add("TA_DJ_JHCT", "ZDR", String.valueOf(fieldIndex[i]), zDr);
			
			// ��ӻ���ID
			rd.add("TA_DJ_JHCT", "ORGID", String.valueOf(fieldIndex[i]), sd.getString("orgid"));
			
			// �ж��Ƿ�Ϊ������������
			if ("".equals(rd.getString("TA_DJ_JHCT","CT",String.valueOf(fieldIndex[i]))))
			{
				// ��������������Ϣ
				addBaseDinner(bsData, rd, String.valueOf(fieldIndex[i]), sd.getString("orgid"), theNewDr);
			}
		}
		
		//�����ľƵ���Ϣ��ID
		if(theNewDr.lastIndexOf(",") > 0){
					
			String newDr = theNewDr.substring(0, theNewDr.length()-1);
			newDr = newDr+"]";
			rd.add("newBaseInfo", newDr);
		}
		
		Connection conn=coreDAO.getConnection();
		
		try {
			// ��������
			coreDAO.beginTrasct(conn);
			
			//ɾ����ʷ����
			coreDAO.delete(data, conn);
			data.remove(entityName);
			
			// ��������ƻ���Ϣ
			coreDAO.insert("TA_DJ_JHCT", rd, conn);
			
			// �������������Ϣ
			coreDAO.insert("ta_dining_room", bsData, conn);
			
			// �ύ����
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
	 * ��������������Ϣ
	 * 
	 * @param bsData ������������
	 * @param rd �������ݶ���
	 * @param index ����
	 * @param orgid ��������ID
	 */
	private void addBaseDinner(BizData bsData, BizData rd, String index, String orgid, StringBuffer theNewDr)
    {
		// ͳ�ƶ����оƵ����������Ŀ
		int rows = bsData.getTableRowsCount("ta_dining_room");
		
		// ȡ���ֵ
		int maxId = queryMaxIDByPara("ta_dining_room", "DINING_ROOM_ID", null);
		
		// ��Ӳ���ID������IDȡ���ֵ
		bsData.add("ta_dining_room", "DINING_ROOM_ID", rows, maxId);
		
		// ��Ӳ�������
		bsData.add("ta_dining_room", "CMPNY_NAME", rows, rd.getString("ta_dj_jhct", "CTMC", index));
		
		// ��Ӳ�����ϵ��
		bsData.add("ta_dining_room", "CHIEF_NAME", rows, rd.getString("ta_dj_jhct", "LXR", index));
		
		// ��Ӳ�����ϵ�˵绰
		bsData.add("ta_dining_room", "CHIEF_MOBILE", rows, rd.getString("ta_dj_jhct", "LXFS", index));
		
		// ��ӻ���ID
		bsData.add("ta_dining_room", "orgid", rows, orgid);
		
		// �ƻ�����Ӳ���ID
		rd.add("TA_DJ_JHCT", "CT", index, maxId);
		
		theNewDr.append("{\"indexNm\":"+index+",\"id\":"+maxId+"},");
    }

	/**Ajax��ʼ������ҳ��
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012��2��17��16:27:01
	 * @throws SQLException
	 */
	public int djAjaxDinnerInfo(BizData rd, BizData sd) {
		String groupId = rd.getString("groupId");
		String sql = "select * from TA_DJ_JHCT where tid='"+groupId+"'  and orgid='"+sd.getString("orgid")+"'";
		try {
			coreDAO.executeQuery(sql, "TA_DJ_JHCT", rd);
			sql="select begin_date,ts from ta_dj_group where id ='"+groupId+"' and orgid='"+sd.getString("orgid")+"'";
			coreDAO.executeQuery(sql, "TA_DJ_GROUPs", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int tRow = rd.getTableRowsCount("TA_DJ_JHCT");//��ȡ��ѯ��¼����
		int random = 0;//װ�����
		
		for(int i = 0; i < tRow; i++){
			random = (int) (Math.floor(Math.random() * 123450));
			rd.add("TA_DJ_JHCT","Random", i, random);//װ���漴��
		}
		return 999;
	}
	
	/**
	 * 
	 * @Title: queryDinnerPlanList
	 * @Description: (�鿴�����Ƿ���ҵ��Ƶ�)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    �趨�ļ�
	 * @return int    ��������
	 * @throws
	 */
	public int queryDinnerPlanList(BizData rd,BizData sd){
		String groupId=rd.getStringByDI("TA_DJ_GROUP","ID",0);
		String sql="select count(d.id) isNull from ta_dj_jhct d,ta_dj_group g \n" +
				" where d.tid=g.id and g.id='"+groupId+"'";
		
		try {
			coreDAO.executeQuery(sql, "dinnerPlanInfo", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * 
	 * @Title: listDinnerPlan
	 * @Description: (�����źŲ�ѯ�����ƻ���Ϣ)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    �趨�ļ�
	 * @return int    ��������
	 * @throws
	 */
	public int listDinnerPlan(BizData rd,BizData sd){
		String groupId=rd.getString("TID");
		String sql="select d.*,z.xfctzj,z.qdctzj,z.ctzj from ta_dj_group g,ta_dj_jhct d,ta_tdjdxxzjb z \n" +
		" where d.tid=g.id and z.tid=g.id and g.id='"+groupId+"' order by d.ycrq asc";
		
		String hzsinit="select (nvl(sum(d.ts),0)) hzs from ta_dj_jhhotel d where d.sfhz>0 and d.tid='"+groupId+"'";
		try {
			coreDAO.executeQuery(sql, "dinnerPlanList", rd);
			coreDAO.executeQuery(hzsinit, "jhHzs", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * 
	 * @Title: editDinnerPlan
	 * @Description: (�༭������Ϣ)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    �趨�ļ�
	 * @return int    ��������
	 * @throws
	 */
	public int editDinnerPlan(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		String[] dinnerRows=rd.getRowIDs("TA_DJ_JHCT");
		try {
			coreDAO.beginTrasct(conn);
			
			String groupId=rd.getStringByDI("TA_DJ_JHCT", "TID", 0);
			//ɾ��ԭ�����ƻ���Ϣ
			BizData ct=new BizData();
			ct.add("TA_DJ_JHCT", "TID", groupId);
			coreDAO.delete(ct,conn);
			ct.remove("TA_DJ_JHCT");
			
			//��Ӳ����ƻ���Ϣ
			for(int i=0;i<dinnerRows.length;i++){
				int id=this.queryMaxIDByPara("TA_DJ_JHCT", "ID", null);
				rd.add("TA_DJ_JHCT", "ID", dinnerRows[i],id);
				rd.add("TA_DJ_JHCT", "TID", dinnerRows[i], groupId);
				rd.add("TA_DJ_JHCT", "JHZT", dinnerRows[i], "Y");
				rd.add("TA_DJ_JHCT", "ZDR", dinnerRows[i], sd.getString("userno"));
			}
			coreDAO.insert("TA_DJ_JHCT", rd, conn);
			rd.remove("TA_DJ_JHCT");
			//�����ű��еķ����ܼ�
			updateTDJDXXZJB(rd, conn, groupId);
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
		} finally{
			if(null!=conn){
				try {
					conn.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return 1;
	}

	/**�����ű��еķ����ܼ�
	 * @param rd
	 * @param conn
	 * @param groupId
	 * @throws SQLException
	 */
	public void updateTDJDXXZJB(BizData rd, Connection conn, String groupId)
			throws SQLException {
		BizData data=new BizData();
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
}
