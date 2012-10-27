package com.trmdj.businessPlan.plan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.string.PyUtil;

/**
 * ClassName:DjSeneryPlanBLC
 * Function:   ADD FUNCTION
 * Reason:	   ADD REASON
 *
 * @author   Kale
 * @version  
 * @since    Ver 1.1
 * @Date	 2011	2011-7-27		����08:24:09
 *
 * @see 	 
 * @deprecated 
 */
public class DjSeneryPlanBLC extends DBBLC{
	public DjSeneryPlanBLC(){
		this.entityName = "TA_DJ_JHJD";
	}
	/**
	 * initSceneryPrice:(AJAX���ؾ���۸�)
	 *
	 * @param  @param rd
	 * @param  @param sd
	 * @param  @return    �趨�ļ�
	 * @return int    DOM����
	 * @throws 
	 * @since  CodingExample��Ver 1.1
	*/
	public int initSceneryPrice(BizData rd,BizData sd){
		String JDID = rd.getString("JDID");
		String dwj = rd.getString("dwj");
		String sqlListAddPrice = "select s.scenery_id,p.price_type dwj,p.price,d.dmsm1 priceName,p.price_name priceNMC\n" +
				"from ta_scenery_point s,ta_scenery_point_price p,dmsm d\n" +
				"where s.scenery_id=p.scenery_id and d.dmz=p.price_name and d.dmlb=12\n" +
				"and p.scenery_id='"+JDID+"' and p.price_type='"+dwj+"'";
		try {
			coreDAO.executeQuery(sqlListAddPrice, "sqlListAddPrice", rd);//AJAX���� ��ѯ����۸���Ϣ
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	public int initSceneryEdit(BizData rd,BizData sd){
		String groupID = rd.getStringByDI("TA_DJ_JHJD", "TID", 0);
		String sqlSceneryInfo ="select j.id,j.tid,j.sfid,j.csid,j.jdid,j.jgqj,j.sfxz,j.qdxj,j.xfxj,j.hj,p.scenery_id,p.cmpny_name,p.city_id from ta_scenery_point p,ta_dj_jhjd j \n"+
								"where j.jdid = p.scenery_id \n"+
								"and j.csid = p.city_id \n"+
								"and j.tid = '"+ groupID +"' \n"+
								"order by j.jdid";
		String sqlSceneryPrice ="select j.* from ta_dj_jhjdjg j \n" +
								"where j.tid= '"+ groupID +"'";
		String sqlCityDis = "select distinct j.csid,j.sfid  from ta_dj_jhjd j \n"+
							"where j.tid = '"+ groupID +"' \n"+
							"order by j.csid";
		try {
			coreDAO.executeQuery(sqlSceneryInfo, "sqlSceneryInfo", rd);// ��ѯ������Ϣ
			coreDAO.executeQuery(sqlSceneryPrice, "sqlSceneryPrice", rd);// ��ѯ����۸���Ϣ
			coreDAO.executeQuery(sqlCityDis, "sqlCityDis", rd);//����ȥ��
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
	}
	
	/**
	 * scenryGeneral:(���� ��� �޸� ����)
	 *
	 * @param  @param rd
	 * @param  @param sd
	 * @param  @return    �趨�ļ�
	 * @return int    DOM����
	 * @throws 
	 * @since  CodingExample��Ver 1.1
	*/
	public int scenryGeneral(BizData rd,BizData sd){
		Connection conn = coreDAO.getConnection();
		String groupID = rd.getStringByDI("TA_DJ_JHJD","TID",0);
		String STATE = rd.getString("state");
		String STATEPLAN = "Y";
		try {
			coreDAO.beginTrasct(conn);
			//��� ״̬ Ϊ Edit   �޸� ��ɾ�������
			if("Edit".equals(STATE)){
				BizData del1 = new BizData();
				del1.add("TA_DJ_JHJDJG", "TID", groupID);
				coreDAO.delete(del1, conn);//ɾ������ƻ��۸�
				del1.remove("TA_DJ_JHJDJG");
				del1.add("TA_DJ_JHJD", "TID", groupID);
				coreDAO.delete(del1, conn);//ɾ������ƻ�
				del1.remove("TA_DJ_JHJD");
			}
			
			String[] rows = rd.getRowIDs("TA_DJ_JHJD");
			for(int i=0;i<rows.length;i++){
				String CSID = rd.getString("TA_DJ_JHJD", "CSID", rows[i]);//�ƻ�����
				String SFID = rd.getString("TA_DJ_JHJD", "SFID", rows[i]);//�ƻ�ʡ��
				String ZDR = rd.getString("userno");//�ƶ���
				String[] SceneryRows = rd.getRowIDs("JDID"+CSID);//��ȡ��������
					BizData data1 = new BizData();
					BizData data2 = new BizData();
					for(int j = 0; j < SceneryRows.length; j++){
						int JHID = this.queryMaxIDByPara("TA_DJ_JHJD","ID",null);//�ƻ�ID
						String JDID = rd.getString("JDID"+CSID, "JDID", SceneryRows[j]);//����ID
						String SFXZ = rd.getString("SFXZ"+JDID);//�Ƿ�ѡ��
						if(!"".equals(SFXZ)){
							String JGQJ = rd.getString("dwj"+JDID);//�۸����� ������
							String QDXJ = rd.getString("QDXJ"+JDID);//ǩ��С��
							if("".equals(QDXJ)){
								QDXJ = "0";
							}
							String XFXJ = rd.getString("XFXJ"+JDID);//�ָ�С��
							if("".equals(XFXJ)){
								XFXJ = "0";
							}
							String HJ = rd.getString("HJ"+JDID);//�ϼ�
							if("".equals(HJ)){
								HJ = "0";
							}
							rd.add("TA_DJ_JHJD"+CSID, "ID", SceneryRows[j], JHID);
							rd.add("TA_DJ_JHJD"+CSID, "TID", SceneryRows[j], groupID);//�ź�
							rd.add("TA_DJ_JHJD"+CSID, "SFID", SceneryRows[j], SFID);
							rd.add("TA_DJ_JHJD"+CSID, "CSID", SceneryRows[j], CSID);
							rd.add("TA_DJ_JHJD"+CSID, "JDID", SceneryRows[j], JDID);
							rd.add("TA_DJ_JHJD"+CSID, "JGQJ", SceneryRows[j], JGQJ);
							rd.add("TA_DJ_JHJD"+CSID, "SFXZ", SceneryRows[j], SFXZ);
							rd.add("TA_DJ_JHJD"+CSID, "QDXJ", SceneryRows[j], QDXJ);
							rd.add("TA_DJ_JHJD"+CSID, "XFXJ", SceneryRows[j], XFXJ);
							rd.add("TA_DJ_JHJD"+CSID, "HJ", SceneryRows[j], HJ);
							rd.add("TA_DJ_JHJD"+CSID, "JHZT", SceneryRows[j], STATEPLAN);//�ƻ�״̬
							rd.add("TA_DJ_JHJD"+CSID, "ZDR", SceneryRows[j], ZDR);//ָ����
							data1.copyEntity(rd,"TA_DJ_JHJD"+CSID,"TA_DJ_JHJD");
							
							String[] SceneryPriceRows = rd.getRowIDs("TA_DJ_JHJDJG"+JDID);
								
								for(int k = 0; k < SceneryPriceRows.length; k++){
									int priceID = queryMaxIDByPara("TA_DJ_JHJDJG", "ID", null);//����۸�ID
									String priceNameID = rd.getString("TA_DJ_JHJDJG"+JDID, "JGMCID", SceneryPriceRows[k]);//�۸�����ID
									String priceName = rd.getString("TA_DJ_JHJDJG"+JDID, "JGMC", SceneryPriceRows[k]);//�۸�����
									String price = rd.getString("TA_DJ_JHJDJG"+JDID, "JG", SceneryPriceRows[k]);//�۸�
									String pCount = rd.getString("TA_DJ_JHJDJG"+JDID, "RS", SceneryPriceRows[k]);//����
									rd.add("TA_DJ_JHJDJG"+JDID, "ID", SceneryPriceRows[k], priceID);
									rd.add("TA_DJ_JHJDJG"+JDID, "JHID", SceneryPriceRows[k], JHID);
									rd.add("TA_DJ_JHJDJG"+JDID, "TID", SceneryPriceRows[k], groupID);
									rd.add("TA_DJ_JHJDJG"+JDID, "JGMCID", SceneryPriceRows[k], priceNameID);
									rd.add("TA_DJ_JHJDJG"+JDID, "JGMC", SceneryPriceRows[k], priceName);
									rd.add("TA_DJ_JHJDJG"+JDID, "JG", SceneryPriceRows[k], price);
									rd.add("TA_DJ_JHJDJG"+JDID, "RS", SceneryPriceRows[k], pCount);
									data2.copyEntity(rd,"TA_DJ_JHJDJG"+JDID,"TA_DJ_JHJDJG");
								}
								//�徰��۸��
								coreDAO.insert("TA_DJ_JHJDJG", data2, conn);
								data2.remove("TA_DJ_JHJDJG");
							}
					}
					//���뾰��ƻ���
					coreDAO.insert("TA_DJ_JHJD", data1, conn);
					data1.remove("TA_DJ_JHJD");
					
			}
			updateTDJDXXZJB(rd, conn, groupID);
			BizData dtt = new BizData();
			dtt.addAttr("TA_DJ_GROUP", "ID", 0, "oldValue", groupID);
			dtt.add("TA_DJ_GROUP", "STATE", "2");//�޸���״̬Ϊ 2  ʵʩ��
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



	/**�����ű��еķ����ܼ�
	 * @param rd
	 * @param conn
	 * @param groupID
	 * @throws SQLException
	 */
	public void updateTDJDXXZJB(BizData rd, Connection conn, String groupID)
			throws SQLException {
		BizData data = new BizData();
		data.add("TA_TDJDXXZJB", "TID", groupID);
		coreDAO.select(data);
		int TDJDrows = data.getTableRowsCount("TA_TDJDXXZJBs");
		data.remove("TA_TDJDXXZJB");
		data.remove("TA_TDJDXXZJBs");
		if(TDJDrows > 0){
			rd.addAttr("TA_TDJDXXZJB", "TID", 0, "oldValue", groupID);
			coreDAO.update("TA_TDJDXXZJB", rd, conn);
		}else{
			rd.add("TA_TDJDXXZJB", "TID", groupID);
			coreDAO.insert("TA_TDJDXXZJB",rd, conn);
		}
	}
	
	public int insert(BizData rd,BizData sd){

		int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_JHJD");
		String[] otherRow = rd.getRowIDs("TA_DJ_JHJD");//��ȡ��¼��
		String groupId = rd.getString("groupId");//��ȡ�ź�
		String temp = rd.getString("temp");//��ȡ�ƻ�״̬
		String zDr = sd.getString("userno");//��ȡָ����
		
		//�����ź�ɾ�����μƻ�
		BizData data = new BizData();
		data.add("TA_DJ_JHJD", "TID", groupId);
		data.add("TA_DJ_JHJD", "orgid", sd.getString("orgid"));
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			coreDAO.delete(data, conn);
			data.remove("TA_DJ_JHJD");
			StringBuffer theNewSecenery = new StringBuffer().append("[");
			
			for(int i = 0; i < otherRow.length; i++){
				int id = queryMaxIDByPara("TA_DJ_JHJD", "ID", null);
				//�ճ�
				String rc = rd.getString("TA_DJ_JHJD", "days", otherRow[i]);
				String[] daysStr = rc.split(",");
				rd.add("TA_DJ_JHJD","rc",String.valueOf(fieldIndex[i]),daysStr[0]);//�ճ�
				rd.add("TA_DJ_JHJD","rq",String.valueOf(fieldIndex[i]),daysStr[1]);//����
				rd.add("TA_DJ_JHJD","ID",String.valueOf(fieldIndex[i]),id);
				rd.add("TA_DJ_JHJD","TID",String.valueOf(fieldIndex[i]),groupId);
				rd.add("TA_DJ_JHJD","JHZT",String.valueOf(fieldIndex[i]),temp);
				rd.add("TA_DJ_JHJD","ZDR",String.valueOf(fieldIndex[i]),zDr);
				rd.add("TA_DJ_JHJD","orgid",String.valueOf(fieldIndex[i]), sd.getString("orgid"));
				
				// ��������
				String sceneryName = rd.getString("TA_DJ_JHJD", "JDMC", String.valueOf(fieldIndex[i]));
				// ����ID��������
				String sceneryId = rd.getString("TA_DJ_JHJD", "JDID", String.valueOf(fieldIndex[i]));
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
					rd.add("TA_DJ_JHJD", "jdid", String.valueOf(fieldIndex[i]), sId);
					//���ظ������Ļ�����Ϣ����������ֵ�͵�ǰ������
					theNewSecenery.append("{\"indexNm\":"+String.valueOf(fieldIndex[i])+",\"id\":"+sId+"},");
				}
			}
			
			//�����ľ�����Ϣ��ID
			if(theNewSecenery.lastIndexOf(",") > 0){
				
				String newSecenery = theNewSecenery.substring(0, theNewSecenery.length()-1);
				newSecenery = newSecenery+"]";
				rd.add("newBaseInfo", newSecenery);
			}
			
			coreDAO.insert("TA_DJ_JHJD", rd, conn);//�������
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
	
	public int djAjaxSceneryInfo(BizData rd,BizData sd) throws SQLException{
		
		String groupId = rd.getString("groupId");
		String sql = "select * from TA_DJ_JHJD where tid='"+groupId+"' and orgid="+sd.getString("orgid");
		coreDAO.executeQuery(sql, "TA_DJ_JHJD", rd);
		
		int tRow = rd.getTableRowsCount("TA_DJ_JHJD");//��ȡ��ѯ��¼����
		
		int random = 0;//װ�����
		for(int i = 0; i < tRow; i++){
			random = (int) (Math.floor(Math.random() * 123450));
			rd.add("TA_DJ_JHJD","Random", i, random);//װ���漴��
		}
		
		//�Ŷ���������ʼʱ��
		rd.add("TA_DJ_GROUP", "id", groupId);
		rd.add("TA_DJ_GROUP","orgid",sd.getString("orgid"));
		coreDAO.select("TA_DJ_GROUP", rd);
		return 999;
	}
}
