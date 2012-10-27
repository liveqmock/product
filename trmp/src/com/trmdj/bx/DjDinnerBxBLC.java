package com.trmdj.bx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


/**
  * @ClassName: DjDinnerBxBLC
  * @Description: TODO �ؽӲ���������Ϣ������
  * @author KingStong - likai
  * @date 2012-4-13 ����3:22:55
  *
  */
public class DjDinnerBxBLC extends DBBLC {
	public DjDinnerBxBLC(){
		this.entityName="TA_DJ_BXCT";
	}
	
	/** ��������������Ϣ
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012��2��17��16:27:01
	 * @throws SQLException
	 */
	public int insertDinner(BizData rd,BizData sd)throws SQLException{
		String[] hotelRow = rd.getRowIDs("TA_DJ_BXCT");//��ȡ��¼��
		String groupId = rd.getString("groupId");//��ȡ�ź�
		String temp = rd.getString("temp");//��ȡ����״̬
		String zDr = sd.getString("userno");//��ȡָ����
		
		//�����ź�ɾ������������Ϣ
		BizData data = new BizData();
		data.add("TA_DJ_BXCT", "TID", groupId);
		data.add("TA_DJ_BXCT", "orgid", sd.getString("orgid"));
		
		// ������������
		BizData bsData = new BizData();
		StringBuffer theNewDr = new StringBuffer().append("[");
				
		int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_BXCT");
		for(int i = 0; i < hotelRow.length; i++){
			int hotelId = queryMaxIDByPara("TA_DJ_BXCT", "ID", null);
			rd.add("TA_DJ_BXCT","ID",String.valueOf(fieldIndex[i]),hotelId);
			rd.add("TA_DJ_BXCT","TID",String.valueOf(fieldIndex[i]),groupId);
			rd.add("TA_DJ_BXCT","JHZT",String.valueOf(fieldIndex[i]),temp);
			rd.add("TA_DJ_BXCT","ZDR",String.valueOf(fieldIndex[i]),zDr);
			// ��ӻ���ID
			rd.add("TA_DJ_BXCT","orgid",String.valueOf(fieldIndex[i]),sd.getString("orgid"));
			// �ж��Ƿ�Ϊ������������
			if ("".equals(rd.getString("TA_DJ_BXCT","CT",String.valueOf(fieldIndex[i]))))
			{
				// ��������������Ϣ
				addBaseDinner(bsData, rd, String.valueOf(fieldIndex[i]), sd.getString("orgid"), theNewDr);
			}
		}
		
		//�����Ĳ�����Ϣ��ID
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
			coreDAO.insert("TA_DJ_BXCT", rd, conn);
			rd.remove(entityName);
			
			// �������������Ϣ
			coreDAO.insert("ta_dining_room", bsData, conn);
			bsData.remove("ta_dining_room");
			
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
	
	/**Ajax��ʼ����������ҳ��
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012��2��17��16:27:01
	 * @throws SQLException
	 */
	public int djAjaxDinnerInfo(BizData rd, BizData sd) {
		int tRow = 0; //��¼����
		int random = 0;//�����
		String groupId = rd.getString("groupId");//�ź�
		String sql = "";//SQL
		
		try {
			sql = "select * from TA_DJ_BXCT where tid='"+groupId+"' and orgid="+sd.getString("orgid");
			coreDAO.executeQuery(sql, "TA_DJ_BXCT", rd);
			tRow = rd.getTableRowsCount("TA_DJ_BXCT");
			if(tRow < 1){//�ж��Ƿ�����
				sql = "select * from TA_DJ_JHCT  where tid='"+groupId+"' and orgid="+sd.getString("orgid");
				coreDAO.executeQuery(sql, "TA_DJ_BXCT", rd);
			}
			sql="select begin_date,ts from ta_dj_group where id ='"+groupId+"' and orgid="+sd.getString("orgid");
			coreDAO.executeQuery(sql, "TA_DJ_GROUPs", rd);//����Ϣ
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		tRow = rd.getTableRowsCount("TA_DJ_BXCT");//��ȡ����
		for(int i = 0; i < tRow; i++){
			random = (int) (Math.floor(Math.random() * 123450));
			rd.add("TA_DJ_BXCT","Random", i, random);//װ���漴��
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
		bsData.add("ta_dining_room", "CMPNY_NAME", rows, rd.getString("ta_dj_bxct", "CTMC", index));
		
		// ��Ӳ�����ϵ��
		bsData.add("ta_dining_room", "CHIEF_NAME", rows, rd.getString("ta_dj_bxct", "LXR", index));
		
		// ��Ӳ�����ϵ�˵绰
		bsData.add("ta_dining_room", "CHIEF_MOBILE", rows, rd.getString("ta_dj_bxct", "LXFS", index));
		
		// ��ӻ���ID
		bsData.add("ta_dining_room", "orgid", rows, orgid);
		
		// �ƻ�����Ӳ���ID
		rd.add("TA_DJ_BXCT", "CT", index, maxId);
		
		theNewDr.append("{\"indexNm\":"+index+",\"id\":"+maxId+"},");
    }
	
	/**
	 * 
	 * @Title: queryDinnerBxList
	 * @Description: (�鿴�����Ƿ��ѱ���)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    �趨�ļ�
	 * @return int    ��������
	 * @throws
	 */
	public int queryDinnerBxList(BizData rd,BizData sd){
		String groupId=rd.getStringByDI("TA_DJ_BXCT","TID",0);
		String flag=rd.getString("flag");
		try {
			if("init".equals(flag)){
				String bxdinnerinit="select d.* from ta_dj_group g,ta_dj_jhct d \n" +
				" where d.tid=g.id and g.id='"+groupId+"' order by d.ycrq asc";
				String hzsinit="select (nvl(sum(d.ts),0)) hzs from ta_dj_bxhotel d where d.sfhz>0 and d.tid='"+groupId+"'";
				String bxzjxxbinit="select a.qdctzj qdzj,a.xfctzj xfzj,a.ctzj zj from ta_tdjdxxzjb a where a.tid='"+groupId+"'";
				coreDAO.executeQuery(bxdinnerinit, "dinnerBxList", rd);
				coreDAO.executeQuery(hzsinit, "bxHzs", rd);
				coreDAO.executeQuery(bxzjxxbinit, "bxctJDXXZJB", rd);
			}
			if("edit".equals(flag)||"view".equals(flag)){
				String bxdinneredit="select d.tid,d.sf,d.cityid,d.ct,d.zcjg,d.zcrs,d.zccs,d.zhcjg,d.zhcrs,d.zhccs,d.qdxj qdxjje,d.xfxj xfxjje,d.hj,d.bxr,d.bxzt,d.ycrq,d.bz \n" +
				" from ta_dj_group g,ta_dj_bxct d \n" +
				" where d.tid=g.id and g.id='"+groupId+"' order by d.ycrq asc";
				String hzsedit="select (nvl(sum(d.ts),0)) hzs from ta_dj_bxhotel d where d.sfhz>0 and d.tid='"+groupId+"'";
				String bxzjxxbEdit="select a.bxctqd qdzj,a.bxctxf xfzj,a.cthj zj from ta_tdbxzjxxb a where a.tid='"+groupId+"'";
				coreDAO.executeQuery(bxdinneredit, "dinnerBxList", rd);
				coreDAO.executeQuery(hzsedit, "bxHzs", rd);
				coreDAO.executeQuery(bxzjxxbEdit, "bxctJDXXZJB", rd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	

	/**
	 * 
	 * @Title: editDinnerBx
	 * @Description: (�༭����������Ϣ)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    �趨�ļ�
	 * @return int    ��������
	 * @throws
	 */
	public int editDinnerBx(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		String[] dinnerRows=rd.getRowIDs("TA_DJ_BXCT");
		try {
			coreDAO.beginTrasct(conn);
			String groupId=rd.getStringByDI("TA_DJ_BXCT", "TID", 0);
			//ɾ��ԭ����������Ϣ
			BizData ct=new BizData();
			ct.add("TA_DJ_BXCT", "TID", groupId);
			coreDAO.delete(ct,conn);
			ct.remove("TA_DJ_BXCT");
			//��Ӳ���������Ϣ
			for(int i=0;i<dinnerRows.length;i++){
				int id=this.queryMaxIDByPara("TA_DJ_BXCT", "ID", null);
				rd.add("TA_DJ_BXCT", "ID", dinnerRows[i],id);
				rd.add("TA_DJ_BXCT", "TID", dinnerRows[i], groupId);
				rd.add("TA_DJ_BXCT", "BXZT", dinnerRows[i], "Y");
				rd.add("TA_DJ_BXCT", "BXR", dinnerRows[i], sd.getString("userno"));
			}
			coreDAO.insert("TA_DJ_BXCT", rd, conn);
			rd.remove("TA_DJ_BXCT");
			//�����ű��еķ����ܼ�
			updateTDBXZJXXB(rd, conn, groupId);
			BizData dtt = new BizData();
			dtt.addAttr("TA_DJ_GROUP", "ID", 0, "oldValue", groupId);
			dtt.add("TA_DJ_GROUP", "STATE", "5");//�޸���״̬Ϊ 5  ʵʩ��
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
	public void updateTDBXZJXXB(BizData rd, Connection conn, String groupId)
			throws SQLException {
		BizData data=new BizData();
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
}
