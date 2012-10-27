package com.trmdj.bx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.string.PyUtil;


/**
  * @ClassName: DjPlusBxBLC
  * @Description: TODO �ؽ� - �ӵ㱨����Ϣ������
  * @author KingStong - likai
  * @date 2012-4-13 ����3:40:12
  *
  */
public class DjPlusBxBLC extends DBBLC {
	public DjPlusBxBLC(){
		this.entityName = "TA_DJ_BXJIADIAN";
	}
	
	/** �����ӵ㱨����Ϣ
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012��2��17��16:27:01
	 * @throws SQLException
	 */
	public int insertPlus(BizData rd,BizData sd) {
		String[] hotelRow = rd.getRowIDs("TA_DJ_BXJIADIAN");//��ȡ��¼��
		String groupId = rd.getString("groupId");//��ȡ�ź�
		String temp = rd.getString("temp");//��ȡ����״̬
		String zDr = sd.getString("userno");//��ȡָ����
		
		BizData data = new BizData();
		data.add("TA_DJ_BXJIADIAN", "TID", groupId);
		data.add("TA_DJ_BXJIADIAN", "orgid", sd.getString("orgid"));
		
		BizData bsData = new BizData();
		StringBuffer theNewPlus = new StringBuffer().append("[");
		
		for(int i = 0; i < hotelRow.length; i++){
			int hotelId = queryMaxIDByPara("TA_DJ_BXJIADIAN", "ID", null);
			int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_BXJIADIAN");
			rd.add("TA_DJ_BXJIADIAN","ID",String.valueOf(fieldIndex[i]),hotelId);
			rd.add("TA_DJ_BXJIADIAN","TID",String.valueOf(fieldIndex[i]),groupId);
			rd.add("TA_DJ_BXJIADIAN","JHZT",String.valueOf(fieldIndex[i]),temp);
			rd.add("TA_DJ_BXJIADIAN","ZDR",String.valueOf(fieldIndex[i]),zDr);
			rd.add("TA_DJ_BXJIADIAN","orgid",String.valueOf(fieldIndex[i]),sd.getString("orgid"));
			
			// ��������
			String sceneryName = rd.getString("TA_DJ_BXJIADIAN", "JDMC", String.valueOf(fieldIndex[i]));
			// ����ID��������
			String sceneryId = rd.getString("TA_DJ_BXJIADIAN", "JDID", String.valueOf(fieldIndex[i]));
			// �����ڻ������в����ڣ����
			if (sceneryId.equals("")) {
				int sId = queryMaxIDByPara("TA_SCENERY_POINT","SCENERY_ID", null);
				bsData.add("TA_SCENERY_POINT", "CMPNY_NAME", sceneryName);
				String nameCode = PyUtil.get1stLetterOf4Chars(sceneryName);
				bsData.add("TA_SCENERY_POINT", "nameCode", nameCode);
				bsData.add("TA_SCENERY_POINT", "SCENERY_ID", sId);
				bsData.add("TA_SCENERY_POINT", "orgid", sd.getString("orgid"));
				bsData.add("TA_SCENERY_POINT", "CHIEF_NAME", rd.getString("TA_DJ_BXJIADIAN", "LXR", String.valueOf(fieldIndex[i])));
				bsData.add("TA_SCENERY_POINT", "CHIEF_MOBILE", rd.getString("TA_DJ_BXJIADIAN", "LXFS", String.valueOf(fieldIndex[i])));

				rd.add("TA_DJ_BXJIADIAN", "jdid", String.valueOf(fieldIndex[i]), sId);
				theNewPlus.append("{\"indexNm\":"+String.valueOf(fieldIndex[i])+",\"id\":"+sId+"},");
			}
		}
		
		Connection conn=coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			
			//ɾ���ӵ㱨����Ϣ
			coreDAO.delete(data, conn);
			data.remove(entityName);
			
			//����ӵ㱨����Ϣ
			coreDAO.insert("TA_DJ_BXJIADIAN", rd, conn);
			rd.remove("TA_DJ_BXJIADIAN");
			
			//���뾰�������Ϣ
			coreDAO.insert("TA_SCENERY_POINT", bsData, conn);
			bsData.remove("TA_SCENERY_POINT");
			
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
	
	/**Ajax��ʼ���ӵ㱨��ҳ��
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012��2��17��16:27:01
	 * @throws SQLException
	 */
	public int djAjaxPlusInfo(BizData rd, BizData sd) {
		int tRow = 0; //��¼����
		int random = 0;//�����
		String groupId = rd.getString("groupId");//�ź�
		String sql = "";//SQL
		
		try {
			sql = "select * from TA_DJ_BXJIADIAN where tid='"+groupId+"' and orgid="+sd.getString("orgid");
			coreDAO.executeQuery(sql, "TA_DJ_BXJIADIAN", rd);
			tRow = rd.getTableRowsCount("TA_DJ_BXJIADIAN");
			if(tRow < 1){//�ж��Ƿ�����
				sql = "select * from TA_DJ_JHJIAD  where tid='"+groupId+"' and orgid="+sd.getString("orgid");
				coreDAO.executeQuery(sql, "TA_DJ_BXJIADIAN", rd);
			}
			sql="select begin_date,ts from ta_dj_group where id ='"+groupId+"' and orgid="+sd.getString("orgid");
			coreDAO.executeQuery(sql, "TA_DJ_GROUPs", rd);//����Ϣ
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		tRow = rd.getTableRowsCount("TA_DJ_BXJIADIAN");//��ȡ����
		for(int i = 0; i < tRow; i++){
			random = (int) (Math.floor(Math.random() * 123450));
			rd.add("TA_DJ_BXJIADIAN","Random", i, random);//װ���漴��
		}
		return 999;
	}
	
	public int queryPlusInfo(BizData rd,BizData sd){
		String groupID = rd.getString("TA_DJ_BXJIADIAN","TID",0);
		String queryPlusInfo="select s.cmpny_name from ta_scenery_point s, ta_dj_bxjiadian t where t.jdid=s.scenery_id and t.tid='"+groupID+"'";
		String queryShopInfo="select s.cmpny_name from ta_dj_bxgw w,ta_shoppoint s where w.gwdid=s.shop_point_id and w.tid='"+groupID+"'";
		String querySceneryInfo="select s.cmpny_name from ta_dj_bxjd w,ta_scenery_point s where w.jdid=s.scenery_id and w.tid='"+groupID+"'";
		try {
			coreDAO.executeQuery(queryPlusInfo, "queryPlusInfo", rd);
			coreDAO.executeQuery(queryShopInfo, "queryShopInfo", rd);
			coreDAO.executeQuery(querySceneryInfo, "querySceneryInfo", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}//���ؼӵ���Ϣ
		return 999;
	}
	public int queryPlusMng(BizData rd,BizData sd){
		String groupID = rd.getString("TA_DJ_BXJIADIAN","TID",0);
		String STATE = rd.getString("flag");
		String TCCY = rd.getString("TCCY");
		String LCBL = rd.getString("LCBL");
		try {
			if("Edit".equals(STATE)||"view".equals(STATE)){
				String bxTCBL = "select d.* from dmsm d where d.dmlb = '"+TCCY+"' order by d.dmz";
				String bxLCBL = "select d.* from dmsm d where d.dmlb = '"+LCBL+"' order by d.dmz";
				String plusInfo = "select j.* from ta_dj_bxjiadian j where j.tid='"+groupID+"'";
				String bxInitCYHZ = "select * from TA_JDBXTCCYHZ t where t.tid='"+groupID+"'";
				coreDAO.executeQuery(bxInitCYHZ, "bxInitCYHZ", rd);//���ؼӵ���ɻ�����Ϣ
				coreDAO.executeQuery(bxTCBL, "bxTCBL", rd);//������ɳ�Ա �Լ� ��ɱ���
				coreDAO.executeQuery(bxLCBL, "bxLCBL", rd);//�����������
				coreDAO.executeQuery(plusInfo, "plusInfo",rd);
			}
			if("init".equals(STATE)){
				String bxTCBL = "select d.* from dmsm d where d.dmlb = '"+TCCY+"' order by d.dmz";
				String bxLCBL = "select d.* from dmsm d where d.dmlb = '"+LCBL+"' order by d.dmz";
				String plusInfo = "select j.sf sfid,j.cs csid,g.jdid,g.ischeck \n" +
								  " from ta_dj_jhjiad j,ta_dj_jhjiadjd g where j.id=g.jhid and g.ischeck='Y' and j.tid='"+groupID+"'";
				coreDAO.executeQuery(bxTCBL, "bxTCBL", rd);//������ɳ�Ա �Լ� ��ɱ���
				coreDAO.executeQuery(bxLCBL, "bxLCBL", rd);//�����������
				coreDAO.executeQuery(plusInfo, "plusInfo",rd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
	}
	public int plusGeneralMng(BizData rd,BizData sd){
		Connection conn = coreDAO.getConnection();
		String groupID = rd.getString("TID");
		String STATE = rd.getString("state");
		String BXR = rd.getString("BXR");
		String BXZT = "Y";

		try{
			coreDAO.beginTrasct(conn);
			deleteAndEdit(groupID, STATE, rd, conn);//�ж� ״̬ΪEdit�޸� ��ɾ����
			insertJDBXTCCYHZ(groupID, rd, conn);//���빺�ﱨ��  ��ɳ�Ա����
			updateTDBXZJXXB(groupID, rd, conn);//���� �Ŷӱ��� �ܼ���Ϣ��
			String[] plusRows = rd.getRowIDs("TA_DJ_BXJIADIAN");
			for(int i = 0; i < plusRows.length; i++){
				int plusID = this.queryMaxIDByPara("TA_DJ_BXJIADIAN", "ID", null);
				String SFID = rd.getString("TA_DJ_BXJIADIAN","SFID",plusRows[i]);
				String CSID = rd.getString("TA_DJ_BXJIADIAN","CSID",plusRows[i]);
				String JDID = rd.getString("TA_DJ_BXJIADIAN","JDID",plusRows[i]);
				String GPJ = rd.getString("TA_DJ_BXJIADIAN","GPJ",plusRows[i]);
				String CBDJ = rd.getString("TA_DJ_BXJIADIAN","CBDJ",plusRows[i]);
				String RS = rd.getString("TA_DJ_BXJIADIAN","RS",plusRows[i]);
				String JDQDXJ = rd.getString("TA_DJ_BXJIADIAN","JDQDXJ",plusRows[i]);
				String JDXFXJ = rd.getString("TA_DJ_BXJIADIAN","JDXFXJ",plusRows[i]);
				String CBJE = rd.getString("TA_DJ_BXJIADIAN","CBJE",plusRows[i]);
				String LR = rd.getString("TA_DJ_BXJIADIAN","LR",plusRows[i]);
				String TCFS = rd.getString("TA_DJ_BXJIADIAN","TCFS",plusRows[i]);
				String RTFXJ = rd.getString("TA_DJ_BXJIADIAN","RTFXJ",plusRows[i]);
				String YJGS = rd.getString("TA_DJ_BXJIADIAN","YJGS",plusRows[i]);
				rd.add("TA_DJ_BXJIADIAN", "ID", plusRows[i], plusID);
				rd.add("TA_DJ_BXJIADIAN", "TID", plusRows[i], groupID);
				rd.add("TA_DJ_BXJIADIAN", "SFID", plusRows[i], SFID);
				rd.add("TA_DJ_BXJIADIAN", "CSID", plusRows[i], CSID);
				rd.add("TA_DJ_BXJIADIAN", "JDID", plusRows[i], JDID);
				rd.add("TA_DJ_BXJIADIAN", "GPJ", plusRows[i], GPJ);
				rd.add("TA_DJ_BXJIADIAN", "CBDJ", plusRows[i], CBDJ);
				rd.add("TA_DJ_BXJIADIAN", "RS", plusRows[i], RS);
				rd.add("TA_DJ_BXJIADIAN", "JDQDXJ", plusRows[i], JDQDXJ);
				rd.add("TA_DJ_BXJIADIAN", "JDXFXJ", plusRows[i], JDXFXJ);
				rd.add("TA_DJ_BXJIADIAN", "CBJE", plusRows[i], CBJE);
				rd.add("TA_DJ_BXJIADIAN", "LR", plusRows[i], LR);
				rd.add("TA_DJ_BXJIADIAN", "TCFS", plusRows[i], TCFS);
				rd.add("TA_DJ_BXJIADIAN", "RTFXJ", plusRows[i], RTFXJ);
				rd.add("TA_DJ_BXJIADIAN", "YJGS", plusRows[i], YJGS);
				String[] bxTCBLRows = rd.getRowIDs("bxTCBL"+plusRows[i]);
				if(null!=bxTCBLRows){
					for(int k = 0; k < bxTCBLRows.length; k++){
						String TCMC = rd.getString("bxTCBL"+plusRows[i],"TCMC",bxTCBLRows[k]);
						String TCBL = rd.getString("bxTCBL"+plusRows[i],"TCBL",bxTCBLRows[k]);
						String TCJE = rd.getString("bxTCBL"+plusRows[i],"TCJE",bxTCBLRows[k]);
						if("����".equals(TCMC)){//�ж���������Ƿ�Ϊ����
							String DYTC =TCJE;//��ɽ��
							String DYZB =TCBL;//��ɱ���
							rd.add("TA_DJ_BXJIADIAN", "DYZB", plusRows[i], DYZB);
							rd.add("TA_DJ_BXJIADIAN", "DYTC", plusRows[i], DYTC);
						}
						if("˾��".equals(TCMC)){//�ж���������Ƿ�Ϊ˾��
							String SJTC =TCJE;
							String SJZB =TCBL;
							rd.add("TA_DJ_BXJIADIAN", "SJTC", plusRows[i], SJTC);
							rd.add("TA_DJ_BXJIADIAN", "SJZB", plusRows[i], SJZB);
						}
						if("ȫ��".equals(TCMC)){//�ж���������Ƿ�Ϊȫ��
							String QPTC =TCJE;
							String QPZB =TCBL;
							rd.add("TA_DJ_BXJIADIAN", "QPTC", plusRows[i], QPTC);
							rd.add("TA_DJ_BXJIADIAN", "QPZB", plusRows[i], QPZB);
						}
						if("��˾".equals(TCMC)){//�ж���������Ƿ�ΪӦ����˾
							String GSTC =TCJE;
							rd.add("TA_DJ_BXJIADIAN", "GSTC", plusRows[i], GSTC);
						}
					}
				}
				rd.add("TA_DJ_BXJIADIAN", "BXR", plusRows[i], BXR);
				rd.add("TA_DJ_BXJIADIAN", "BXZT", plusRows[i], BXZT);
			}
			coreDAO.insert("TA_DJ_BXJIADIAN", rd, conn);//��ӵ㱨����
			rd.remove("TA_DJ_BXJIADIAN");
			BizData dtt = new BizData();
			dtt.addAttr("TA_DJ_GROUP", "ID", 0, "oldValue", groupID);
			dtt.add("TA_DJ_GROUP", "STATE", "5");//�޸���״̬Ϊ 5  ʵʩ��
			coreDAO.update("TA_DJ_GROUP", dtt, conn);
			coreDAO.commitTrasct(conn);
		}catch (SQLException e) {
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
	 * deleteAndEdit:(����״̬Edit�ж� �޸� ��ɾ��)
	 *
	 * @param  @param groupID
	 * @param  @param STATE
	 * @param  @param rd
	 * @param  @param conn
	 * @param  @return
	 * @param  @throws SQLException    �趨�ļ�
	 * @return int    DOM����
	 * @throws 
	 * @since  CodingExample��Ver 1.1
	*/
	public int deleteAndEdit(String groupID, String STATE, BizData rd, Connection conn) throws SQLException{
		if("Edit".equals(STATE)){
			BizData data = new BizData();
			data.add("TA_DJ_BXJIADIAN", "TID", groupID);
			coreDAO.delete(data, conn);
			data.remove("TA_DJ_BXJIADIAN");//ɾ��������ϸ��
			data.add("TA_JDBXTCCYHZ", "TID", groupID);
			coreDAO.delete(data, conn);
			data.remove("TA_JDBXTCCYHZ");//ɾ��������ɳ�Ա���ܱ�
		}
		return 999;
		
	}
	/**
	 * insertTDBXTCCYHZ:(//����ӵ㱨��  ��ɳ�Ա����)
	 *
	 * @param  @param groupID
	 * @param  @param rd
	 * @param  @param conn
	 * @param  @return
	 * @param  @throws SQLException    �趨�ļ�
	 * @return int    DOM����
	 * @throws 
	 * @since  CodingExample��Ver 1.1
	*/
	public int insertJDBXTCCYHZ(String groupID,BizData rd,Connection conn) throws SQLException{
		String[] CYHZRows = rd.getRowIDs("TA_JDBXTCCYHZ");
		if(null != CYHZRows){
			for(int i = 0;i < CYHZRows.length; i++){
				int CYHZID = this.queryMaxIDByPara("TA_JDBXTCCYHZ","ID",null);
				String CYID = rd.getString("TA_JDBXTCCYHZ","CYID",CYHZRows[i]);
				String CYMC = rd.getString("TA_JDBXTCCYHZ","CYMC",CYHZRows[i]);
				String CYJEHZ = rd.getString("TA_JDBXTCCYHZ","CYJEHZ",CYHZRows[i]);
				rd.add("TA_JDBXTCCYHZ", "ID", CYHZRows[i], CYHZID);
				rd.add("TA_JDBXTCCYHZ", "TID", CYHZRows[i], groupID);
				rd.add("TA_JDBXTCCYHZ", "CYID", CYHZRows[i], CYID);
				rd.add("TA_JDBXTCCYHZ", "CYMC", CYHZRows[i], CYMC);
				rd.add("TA_JDBXTCCYHZ", "CYJEHZ", CYHZRows[i], CYJEHZ);
			}
			coreDAO.insert("TA_JDBXTCCYHZ", rd, conn);
			rd.remove("TA_JDBXTCCYHZ");
		}
		return 999;
		
	}
	
	/**
	 * updateTDBXZJXXB:(//�Ŷӱ����ܼ���Ϣ��)
	 *
	 * @param  @param groupID
	 * @param  @param rd
	 * @param  @param conn
	 * @param  @return
	 * @param  @throws SQLException    �趨�ļ�
	 * @return int    DOM����
	 * @throws 
	 * @since  CodingExample��Ver 1.1
	*/
	public int updateTDBXZJXXB(String groupID,BizData rd,Connection conn) throws SQLException{
		BizData data = new BizData();
		data.add("TA_TDBXZJXXB", "TID", groupID);
		coreDAO.select(data);
		int TDBXrows = data.getTableRowsCount("TA_TDBXZJXXBs");
		data.remove("TA_TDBXZJXXB");
		data.remove("TA_TDBXZJXXBs");
		if(TDBXrows > 0){
			rd.addAttr("TA_TDBXZJXXB", "TID", 0, "oldValue", groupID);
			coreDAO.update("TA_TDBXZJXXB", rd, conn);
			rd.remove("TA_TDBXZJXXB");
		}else{
			rd.add("TA_TDBXZJXXB", "TID", groupID);
			coreDAO.insert("TA_TDBXZJXXB",rd, conn);
			rd.remove("TA_TDBXZJXXB");
		}
		return 999;
		
	}
}

