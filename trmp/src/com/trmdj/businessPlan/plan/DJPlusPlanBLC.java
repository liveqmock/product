package com.trmdj.businessPlan.plan;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.string.PyUtil;

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

public class DJPlusPlanBLC extends DBBLC {
	
	public DJPlusPlanBLC(){
		this.entityName="TA_DJ_JHJIAD";
	}
	/** �����ӵ���Ϣ
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012��2��17��16:27:01
	 * @throws SQLException
	 */
	public int insertPlus(BizData rd, BizData sd) throws SQLException{
		
		String[] plusRow = rd.getRowIDs("TA_DJ_JHJIAD");//��ȡ��¼��
		String groupId = rd.getString("groupId");//��ȡ�ź�
		String temp = rd.getString("temp");//��ȡ�ƻ�״̬
		String zDr = sd.getString("userno");//��ȡָ����
		
		//�����ź�ɾ���ӵ�ƻ�
		BizData data = new BizData();
		
		Connection conn=coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			data.add("TA_DJ_JHJIAD", "TID", groupId);
			data.add("TA_DJ_JHJIAD", "orgid", sd.getString("orgid"));
			coreDAO.delete(data, conn);// ɾ����ʷ����
			data.remove(entityName);

			StringBuffer theNewPlus = new StringBuffer().append("[");
			int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_JHJIAD");
			
			for (int i = 0; i < plusRow.length; i++) {

				int plusId = queryMaxIDByPara("TA_DJ_JHJIAD", "ID", null);
				// ��������
				String sceneryName = rd.getString("TA_DJ_JHJIAD", "JDMC", String.valueOf(fieldIndex[i]));
				// ����ID��������
				String sceneryId = rd.getString("TA_DJ_JHJIAD", "JDID", String.valueOf(fieldIndex[i]));
				// �����ڻ������в����ڣ����
				if (sceneryId.equals("")) {
					int sId = queryMaxIDByPara("TA_SCENERY_POINT","SCENERY_ID", null);
					data.add("TA_SCENERY_POINT", "CMPNY_NAME", sceneryName);
					String nameCode = PyUtil.get1stLetterOf4Chars(sceneryName);
					data.add("TA_SCENERY_POINT", "nameCode", nameCode);
					data.add("TA_SCENERY_POINT", "SCENERY_ID", sId);
					data.add("TA_SCENERY_POINT", "orgid", sd.getString("orgid"));
					data.add("TA_SCENERY_POINT", "CHIEF_NAME", rd.getString("TA_DJ_JHJIAD", "LXR", String.valueOf(fieldIndex[i])));
					data.add("TA_SCENERY_POINT", "CHIEF_MOBILE", rd.getString("TA_DJ_JHJIAD", "LXFS", String.valueOf(fieldIndex[i])));
					coreDAO.insert(data, conn);
					data.remove("TA_SCENERY_POINT");
					rd.add("TA_DJ_JHJIAD", "jdid", String.valueOf(fieldIndex[i]), sId);
					theNewPlus.append("{\"indexNm\":"+String.valueOf(fieldIndex[i])+",\"id\":"+sId+"},");
				}
				rd.add("TA_DJ_JHJIAD", "ID", String.valueOf(fieldIndex[i]), plusId);
				rd.add("TA_DJ_JHJIAD", "TID", String.valueOf(fieldIndex[i]), groupId);
				rd.add("TA_DJ_JHJIAD", "JHZT", String.valueOf(fieldIndex[i]), temp);
				rd.add("TA_DJ_JHJIAD", "ZDR", String.valueOf(fieldIndex[i]), zDr);
				rd.add("TA_DJ_JHJIAD", "orgid", String.valueOf(fieldIndex[i]), sd.getString("orgid"));
				
			}
			
			//�����ľƵ���Ϣ��ID
			if(theNewPlus.lastIndexOf(",") > 0){
				
				String newPlus = theNewPlus.substring(0, theNewPlus.length()-1);
				newPlus = newPlus+"]";
				rd.add("newBaseInfo", newPlus);
			}

			coreDAO.insert("TA_DJ_JHJIAD", rd, conn);// �������
			coreDAO.commitTrasct(conn);
		} catch (Exception e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return -100;
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
	/**Ajax��ʼ���ӵ�ҳ��
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012��2��17��16:27:01
	 * @throws SQLException
	 */
	public int djAjaxPlusInfo(BizData rd, BizData sd) throws SQLException{
		String groupId = rd.getString("groupId");
		String sql = "select * from TA_DJ_JHJIAD where tid='"+groupId+"' and orgid="+sd.getString("orgid");
		coreDAO.executeQuery(sql, "TA_DJ_JHJIAD", rd);
		
		int tRow = rd.getTableRowsCount("TA_DJ_JHJIAD");//��ȡ��ѯ��¼����
		
		int random = 0;//װ�����
		for(int i = 0; i < tRow; i++){
			random = (int) (Math.floor(Math.random() * 123450));
			rd.add("TA_DJ_JHJIAD","Random", i, random);//װ���漴��
		}
		return 999;
	}
	
	public int queryJDByTID(BizData rd, BizData sd){
		rd.add("flag", rd.getString("flag"));
		rd.add("tid", rd.getStringByDI("TA_DJ_JHJIAD", "TID", 0));
		try {
			query(rd, sd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 92;
	}
	/**  
	 * insertPlusPanInfo(���뾰��ƻ��;�����Ϣ)<br/>  
	 * @param rd
	 * @param sd   
	 * @throws SQLException 
	 * @return_type (void)
	 * @exception   
	 * @since  1.0.0  
	*/
	public int insertPlusPanInfo(BizData rd,BizData sd) {
		Connection conn = coreDAO.getConnection();
		String groupID=rd.getStringByDI("TA_DJ_JHJIAD","TID",0);//��ȡ�ź�
		try {
			coreDAO.beginTrasct(conn);
			//�����ź�ɾ���ӵ�ƻ�
			BizData data = new BizData();
			data.add("TA_DJ_JHJIAD", "TID", groupID);
			coreDAO.delete(data, conn);
			data.remove("TA_DJ_JHJIAD");
			data.add("TA_DJ_JHJIADJD", "TID", groupID);
			coreDAO.delete(data, conn);
			data.remove("TA_DJ_JHJIADJD");
			
			addPlusPlanInfo(groupID,rd,conn);//���뾰����Ϣ
			
			BizData dtt = new BizData();
			dtt.addAttr("TA_DJ_GROUP", "ID", 0, "oldValue", groupID);
			dtt.add("TA_DJ_GROUP", "STATE", "2");//�޸���״̬Ϊ 2  ʵʩ��
			coreDAO.update("TA_DJ_GROUP", dtt, conn);
			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (null != conn) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 999;
	}
	/**  
	 * addPlusPlanInfo(���뾰��ƻ�)<br/>  
	 * @param rd
	 * @param sd
	 * @return   
	 * @return_type (int)
	 * @exception   
	 * @since  1.0.0  
	*/
	private void addPlusPlanInfo(String groupID, BizData rd, Connection conn) throws SQLException {

		String[] plusRows = rd.getRowIDs("TA_DJ_JHJIAD");

		for (int i = 0; i < plusRows.length; i++) {
				int plusID = queryMaxIDByPara("TA_DJ_JHJIAD", "ID", null);
				String csId = rd.getString("TA_DJ_JHJIAD", "CS", plusRows[i]);//ȡ����ID��������ID�ӵ���Ӧ�ľ���������棬����ȡ����
				rd.add("TA_DJ_JHJIAD", "ID", plusRows[i], plusID);
				addPlusScr(plusID, rd, conn, csId, groupID);//���뾰����Ϣ
		}
		coreDAO.insert("TA_DJ_JHJIAD", rd, conn);
	}
	/**  
	 * addPlusScr(���뾰����Ϣ)<br/>  
	 * @param plusID
	 * @param rd
	 * @param conn
	 * @return
	 * @throws SQLException   
	 * @return_type (int)
	 * @exception   
	 * @since  1.0.0  
	*/
	private void addPlusScr(int plusID,BizData rd, Connection conn, String cityID, String groupId) throws SQLException {
		
		String[] plusScrRows=rd.getRowIDs("TA_DJ_JHJIADJD"+cityID);
		for(int i = 0; i < plusScrRows.length; i++){
			String isCk = rd.getString("TA_DJ_JHJIADJD"+cityID,"ISCHECK",plusScrRows[i]);
			if(!"".equals(isCk)){
				int plusScrID=queryMaxIDByPara("TA_DJ_JHJIADJD", "ID", null);
				String jdId = rd.getString("TA_DJ_JHJIADJD"+cityID,"JDID",plusScrRows[i]);
				String jdMc = rd.getString("TA_DJ_JHJIADJD"+cityID,"JDMC",plusScrRows[i]);
				rd.add("TA_DJ_JHJIADJD", "ID", plusScrRows[i], plusScrID);
				rd.add("TA_DJ_JHJIADJD", "JHID", plusScrRows[i], plusID);
				rd.add("TA_DJ_JHJIADJD", "TID", plusScrRows[i], groupId);
				rd.add("TA_DJ_JHJIADJD", "JDID", plusScrRows[i], jdId);
				rd.add("TA_DJ_JHJIADJD", "JDMC", plusScrRows[i], jdMc);
				rd.add("TA_DJ_JHJIADJD", "ISCHECK", plusScrRows[i], isCk);
			}
		}
		coreDAO.insert("TA_DJ_JHJIADJD", rd, conn);
	}
	
	/**
	 * ���ؽ�����жϼƵ��Ƿ���ʵʩ��������ʵ��ʵʩ�ľ���
	 * @param rd
	 * @param sd
	 * @return
	 */
	
	
}
