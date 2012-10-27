package com.trmdj.businessPlan.plan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**  
 * <b>��Ŀ����</b>�ʼҹ�����Դ����ϵͳ<br/>  
 * <b>������</b>com.trmdj.businessPlan.plan<br/>  
 * <b>�ļ�����</b>addPlusPlanBLC.java<br/>  
 * <b>�汾��Ϣ��1.0</b><br/>  
 * <b>���ڣ�</b>2011-7-19-����03:21:25<br/>  
 * <b>Copyright (c)</b> 2011����ͨ����Ƽ����޹�˾-��Ȩ����<br/>  
 *   
 */
/**  
 *   
 * <b>�����ƣ�</b>addPlusPlanBLC<br/>  
 * <b>��������</b><br/>  
 * <b>�����ˣ�</b>Kale<br/>  
 * <b>�޸��ˣ�</b>Kale<br/>  
 * <b>�޸�ʱ�䣺</b>2011-7-19 ����03:21:25<br/>  
 * <b>�޸ı�ע��</b><br/>  
 * @version 1.0.0<br/>  
 *   
 */

public class DjOtherPlanBLC extends DBBLC {
	
	public DjOtherPlanBLC(){
		this.entityName="TA_DJ_JHQT";
	}
	/** �����ӵ���Ϣ
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012��2��17��16:27:01
	 * @throws SQLException
	 */
	public int insert(BizData rd, BizData sd) {
		String[] otherRow = rd.getRowIDs("TA_DJ_JHQT");//��ȡ��¼��
		String groupId = rd.getString("groupId");//��ȡ�ź�
		String temp = rd.getString("temp");//��ȡ�ƻ�״̬
		String zDr = sd.getString("userno");//��ȡָ����
		
		//�����ź�ɾ���ӵ�ƻ�
		BizData data = new BizData();
		data.add("TA_DJ_JHQT", "TID", groupId);
		data.add("TA_DJ_JHQT", "orgid", sd.getString("orgid"));
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			coreDAO.delete(data, conn);
			data.remove("TA_DJ_JHQT");

			for(int i = 0; i < otherRow.length; i++){
				int plusId = queryMaxIDByPara("TA_DJ_JHQT", "ID", null);
				int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_JHQT");
				rd.add("TA_DJ_JHQT", "orgid",fieldIndex[i],sd.getString("orgid"));
				rd.add("TA_DJ_JHQT","ID",fieldIndex[i],plusId);
				rd.add("TA_DJ_JHQT","TID",fieldIndex[i],groupId);
				rd.add("TA_DJ_JHQT","JHZT",fieldIndex[i],temp);
				rd.add("TA_DJ_JHQT","ZDR",fieldIndex[i],zDr);
			}
			coreDAO.insert("TA_DJ_JHQT", rd, conn);//�������
			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally{
			try{
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return 999;
	}
	/**Ajax��ʼ���ӵ�ҳ��
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012��2��17��16:27:01
	 * @throws SQLException
	 */
	public int djAjaxOtherInfo(BizData rd, BizData sd) throws SQLException{
		String groupId = rd.getString("groupId");
		String sql = "select * from TA_DJ_JHQT where tid='"+groupId+"' and orgid="+sd.getString("orgid");
		coreDAO.executeQuery(sql, "TA_DJ_JHQT", rd);
		
		return 999;
	}
	
}
