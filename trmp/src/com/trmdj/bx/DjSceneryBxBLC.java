package com.trmdj.bx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.string.PyUtil;


/**
  * @ClassName: DjSceneryBxBLC
  * @Description: TODO �ؽ� - ���㱨����Ϣ������
  * @author KingStong - likai
  * @date 2012-4-13 ����3:48:51
  *
  */
public class DjSceneryBxBLC extends DBBLC {
	public DjSceneryBxBLC(){
		this.entityName = "TA_DJ_BXJD";
	}
	/** �������㱨����Ϣ
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012��2��17��16:27:01
	 * @throws SQLException
	 */
	public int insertScenery(BizData rd,BizData sd)throws SQLException{
		String[] hotelRow = rd.getRowIDs("TA_DJ_BXJD");//��ȡ��¼��
		String groupId = rd.getString("groupId");//��ȡ�ź�
		String temp = rd.getString("temp");//��ȡ����״̬
		String zDr = sd.getString("userno");//��ȡָ����
		
		BizData data = new BizData();
		data.add("TA_DJ_BXJD", "TID", groupId);
		data.add("TA_DJ_BXJD", "orgid", sd.getString("orgid"));
		
		//���������Ϣ
		BizData bsData = new BizData();
		
		StringBuffer theNewSecenery = new StringBuffer().append("[");
		
		for(int i = 0; i < hotelRow.length; i++){
			int hotelId = queryMaxIDByPara("TA_DJ_BXJD", "ID", null);
			int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_BXJD");
			rd.add("TA_DJ_BXJD","ID",String.valueOf(fieldIndex[i]),hotelId);
			rd.add("TA_DJ_BXJD","TID",String.valueOf(fieldIndex[i]),groupId);
			rd.add("TA_DJ_BXJD","JHZT",String.valueOf(fieldIndex[i]),temp);
			rd.add("TA_DJ_BXJD","ZDR",String.valueOf(fieldIndex[i]),zDr);
			rd.add("TA_DJ_BXJD","orgid",String.valueOf(fieldIndex[i]),sd.getString("orgid"));
			
			// ��������
			String sceneryName = rd.getString("TA_DJ_BXJD", "JDMC", String.valueOf(fieldIndex[i]));
			// ����ID��������
			String sceneryId = rd.getString("TA_DJ_BXJD", "JDID", String.valueOf(fieldIndex[i]));
			// �����ڻ������в����ڣ����
			if (sceneryId.equals("")) {
				int sId = queryMaxIDByPara("TA_SCENERY_POINT","SCENERY_ID", null);
				bsData.add("TA_SCENERY_POINT", "CMPNY_NAME", sceneryName);
				String nameCode = PyUtil.get1stLetterOf4Chars(sceneryName);
				bsData.add("TA_SCENERY_POINT", "nameCode", nameCode);
				bsData.add("TA_SCENERY_POINT", "SCENERY_ID", sId);
				bsData.add("TA_SCENERY_POINT", "orgid", sd.getString("orgid"));
				bsData.add("TA_SCENERY_POINT", "CHIEF_NAME", rd.getString("TA_DJ_BXJD", "LXR", String.valueOf(fieldIndex[i])));
				bsData.add("TA_SCENERY_POINT", "CHIEF_MOBILE", rd.getString("TA_DJ_BXJD", "LXFS", String.valueOf(fieldIndex[i])));
				
				rd.add("TA_DJ_BXJD", "jdid", String.valueOf(fieldIndex[i]), sId);
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
		
		Connection conn=coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			
			//ɾ�����㱨����Ϣ
			coreDAO.delete(data, conn);
			data.remove(entityName);
			
			//���뾰�㱨����Ϣ
			coreDAO.insert("TA_DJ_BXJD", rd, conn);
			rd.remove(entityName);
			
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
	
	/**Ajax��ʼ�����㱨��ҳ��
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012��2��17��16:27:01
	 * @throws SQLException
	 */
	public int djAjaxSceneryInfo(BizData rd, BizData sd) {
		int tRow = 0; //��¼����
		int random = 0;//�����
		String groupId = rd.getString("groupId");//�ź�
		String sql = "";//SQL
		
		try {
			sql = "select * from TA_DJ_BXJD where tid='"+groupId+"' and orgid="+sd.getString("orgid");
			coreDAO.executeQuery(sql, "TA_DJ_BXJD", rd);
			tRow = rd.getTableRowsCount("TA_DJ_BXJD");
			if(tRow < 1){//�ж��Ƿ�����
				sql = "select * from TA_DJ_JHJD  where tid='"+groupId+"' and orgid="+sd.getString("orgid");
				coreDAO.executeQuery(sql, "TA_DJ_BXJD", rd);
			}
			sql="select begin_date,ts from ta_dj_group where id ='"+groupId+"' and orgid="+sd.getString("orgid");
			coreDAO.executeQuery(sql, "TA_DJ_GROUPs", rd);//����Ϣ
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		tRow = rd.getTableRowsCount("TA_DJ_BXJD");//��ȡ����
		for(int i = 0; i < tRow; i++){
			random = (int) (Math.floor(Math.random() * 123450));
			rd.add("TA_DJ_BXJD","Random", i, random);//װ���漴��
		}
		return 999;
	}
	/**
	 * querySceneryMng:(�жϾ��㱨�� flag Ϊedit ��Ϊ�޸�   init��Ϊ��ʼ��)
	 *
	 * @param  @param rd
	 * @param  @param sd    �趨�ļ�
	 * @return void    DOM����
	 * @throws 
	 * @since  CodingExample��Ver 1.1
	*/
	public int querySceneryMng(BizData rd,BizData sd){
		String RbtState = rd.getString("flag");
		
		try {
			if("edit".equals(RbtState)||"view".equals(RbtState)){//���� �޸�״̬
				String groupID = rd.getStringByDI("TA_DJ_BXJD", "TID", 0);
				String rbtSceneryInfoEdit = "select j.id,j.tid,j.sfid,j.csid,j.jdid,j.jgqj,j.sfxz,j.qdxj,j.xfxj,j.hj,j.bz,p.scenery_id,p.cmpny_name,p.city_id \n" +
											" from ta_scenery_point p,ta_dj_bxjd j \n"+
											"where j.jdid = p.scenery_id \n"+
											"and j.csid = p.city_id \n"+
											"and j.tid = '"+ groupID +"' \n"+
											"order by j.csid";
				String rbtSceneryPriceEdit = "select j.* from ta_dj_bxjdjg j \n" +
											 "where j.tid= '"+ groupID +"'";
				String rbtCityDisEdit = "select distinct j.csid,j.sfid  from ta_dj_bxjd j \n"+
										"where j.tid = '"+ groupID +"' \n"+
										"order by j.csid";
				String rbtJDXXZJBEdit = "select BX_JDXF XF,BX_JDQD QD,JINDHJ ZJ from TA_TDBXZJXXB where tid='"+ groupID +"'";
				coreDAO.executeQuery(rbtJDXXZJBEdit,"rbtJDXXZJB",rd);//�Ŷӱ����ܼ���Ϣ��
				coreDAO.executeQuery(rbtSceneryInfoEdit,"rbtSceneryInfo",rd);//�ű������� ��
				coreDAO.executeQuery(rbtSceneryPriceEdit,"rbtSceneryPrice",rd);//�ű����۸��
				coreDAO.executeQuery(rbtCityDisEdit,"rbtCityDis",rd);//�ű��� ȥ �ظ� ����
			}
			
			if("init".equals(RbtState)){//���� ��ʼ�� ״̬
				String groupID = rd.getStringByDI("TA_DJ_BXJD", "TID", 0);
				String rbtSceneryInfoInit = "select j.id,j.tid,j.sfid,j.csid,j.jdid,j.jgqj,j.sfxz,j.qdxj,j.xfxj,j.hj,p.scenery_id,p.cmpny_name,p.city_id \n" +
										" from ta_scenery_point p,ta_dj_jhjd j \n" +
										"where j.jdid = p.scenery_id \n"+
										"and j.csid = p.city_id \n"+
										"and j.sfxz = 'Y' \n" +
										"and j.tid = '"+ groupID +"' \n"+
										"order by j.csid";
				String rbtSceneryPriceInit ="select  j.id,j.JHID BXID,j.JGMCID,j.JGMC,j.JG,j.RS from ta_dj_jhjdjg j \n" +
										"where j.tid= '"+ groupID +"'";
				String rbtCityDisInit = "select distinct j.csid,j.sfid  from ta_dj_jhjd j \n"+
									"where j.tid = '"+ groupID +"' \n"+
									"order by j.csid";
				String rbtJDXXZJBInit = "select XFJDZJ XF,QDJDZJ QD,JDZJ ZJ from TA_TDJDXXZJB where tid='"+ groupID +"'";
				coreDAO.executeQuery(rbtJDXXZJBInit,"rbtJDXXZJB",rd);//�ŶӼƵ���Ϣ�ܼƱ�
				coreDAO.executeQuery(rbtSceneryInfoInit,"rbtSceneryInfo",rd);//�żƻ����� ��
				coreDAO.executeQuery(rbtSceneryPriceInit,"rbtSceneryPrice",rd);//�żƻ��۸��
				coreDAO.executeQuery(rbtCityDisInit,"rbtCityDis",rd);//�żƻ� ȥ�ظ� ����
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return 999;
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
	public int initSceneryPriceRbt(BizData rd,BizData sd){
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
	
	/**
	 * scenryGeneral:(���㱨��  ��� �޸� ����)
	 *
	 * @param  @param rd
	 * @param  @param sd
	 * @param  @return    �趨�ļ�
	 * @return int    DOM����
	 * @throws 
	 * @since  CodingExample��Ver 1.1
	*/
	public int scenryGeneralRbt(BizData rd,BizData sd){
		Connection conn = coreDAO.getConnection();
		String groupID = rd.getStringByDI("TA_DJ_BXJD","TID",0);//�ź�
		String BXR = rd.getString("userno");//�ƶ���
		String STATE = rd.getString("state");//״̬
		String STATEBX = "Y";//����״̬
		try {
			coreDAO.beginTrasct(conn);
			//��� ״̬ Ϊ Edit   �޸� ��ɾ�������
			if("Edit".equals(STATE)){
				BizData del1 = new BizData();
				del1.add("TA_DJ_BXJD", "TID", groupID);
				coreDAO.delete(del1, conn);
				del1.remove("TA_DJ_BXJD");
				del1.add("TA_DJ_BXJDJG", "TID", groupID);
				coreDAO.delete(del1, conn);
				del1.remove("TA_DJ_BXJDJG");
			}
			String[] rows = rd.getRowIDs("TA_DJ_BXJD");
			for(int i=0;i<rows.length;i++){
				String CSID = rd.getString("TA_DJ_BXJD", "CSID", rows[i]);//�ƻ�����
				String SFID = rd.getString("TA_DJ_BXJD", "SFID", rows[i]);//�ƻ�ʡ��
				String[] SceneryRows = rd.getRowIDs("JDID"+CSID);//��ȡ��������
				if(null != SceneryRows){
					BizData data1 = new BizData();
					for(int j = 0; j < SceneryRows.length; j++){
						int BXID = this.queryMaxIDByPara("TA_DJ_BXJD","ID",null);//�ƻ�ID
						String JDID = rd.getString("JDID"+CSID, "JDID", SceneryRows[j]);//����ID
						String BZ = rd.getString("BZ"+JDID);//����ID
						String JGQJ = rd.getString("dwj"+JDID);//�۸����� ������
						String SFXZ = rd.getString("SFXZ"+JDID);//�Ƿ�ѡ��
						String QDXJ = rd.getString("QDXJ"+JDID);//ǩ��С��
						String XFXJ = rd.getString("XFXJ"+JDID);//�ָ�С��
						String HJ = rd.getString("HJ"+JDID);//�ϼ�
						rd.add("TA_DJ_BXJD"+CSID, "ID", SceneryRows[j], BXID);
						rd.add("TA_DJ_BXJD"+CSID, "TID", SceneryRows[j], groupID);//�ź�
						rd.add("TA_DJ_BXJD"+CSID, "SFID", SceneryRows[j], SFID);
						rd.add("TA_DJ_BXJD"+CSID, "CSID", SceneryRows[j], CSID);
						rd.add("TA_DJ_BXJD"+CSID, "JDID", SceneryRows[j], JDID);
						rd.add("TA_DJ_BXJD"+CSID, "JGQJ", SceneryRows[j], JGQJ);
						rd.add("TA_DJ_BXJD"+CSID, "SFXZ", SceneryRows[j], SFXZ);
						rd.add("TA_DJ_BXJD"+CSID, "QDXJ", SceneryRows[j], QDXJ);
						rd.add("TA_DJ_BXJD"+CSID, "XFXJ", SceneryRows[j], XFXJ);
						rd.add("TA_DJ_BXJD"+CSID, "HJ", SceneryRows[j], HJ);
						rd.add("TA_DJ_BXJD"+CSID, "BZ", SceneryRows[j], BZ);
						rd.add("TA_DJ_BXJD"+CSID, "BXZT", SceneryRows[j], STATEBX);//�ƻ�״̬
						rd.add("TA_DJ_BXJD"+CSID, "BXR", SceneryRows[j], BXR);//������
						data1.copyEntity(rd,"TA_DJ_BXJD"+CSID,"TA_DJ_BXJD");
						
						String[] SceneryPriceRows = rd.getRowIDs("TA_DJ_BXJDJG"+JDID);
						if(null!=SceneryPriceRows){
							BizData data2 = new BizData();
							for(int k = 0; k < SceneryPriceRows.length; k++){
								int priceID = queryMaxIDByPara("TA_DJ_BXJDJG", "ID", null);//����۸�ID
								String priceNameID = rd.getString("TA_DJ_BXJDJG"+JDID, "JGMCID", SceneryPriceRows[k]);//�۸�����ID
								String priceName = rd.getString("TA_DJ_BXJDJG"+JDID, "JGMC", SceneryPriceRows[k]);//�۸�����
								String price = rd.getString("TA_DJ_BXJDJG"+JDID, "JG", SceneryPriceRows[k]);//�۸�
								String pCount = rd.getString("TA_DJ_BXJDJG"+JDID, "RS", SceneryPriceRows[k]);//����
								rd.add("TA_DJ_BXJDJG"+JDID, "ID", SceneryPriceRows[k], priceID);
								rd.add("TA_DJ_BXJDJG"+JDID, "BXID", SceneryPriceRows[k], BXID);
								rd.add("TA_DJ_BXJDJG"+JDID, "TID", SceneryPriceRows[k], groupID);
								rd.add("TA_DJ_BXJDJG"+JDID, "JGMCID", SceneryPriceRows[k], priceNameID);
								rd.add("TA_DJ_BXJDJG"+JDID, "JGMC", SceneryPriceRows[k], priceName);
								rd.add("TA_DJ_BXJDJG"+JDID, "JG", SceneryPriceRows[k], price);
								rd.add("TA_DJ_BXJDJG"+JDID, "RS", SceneryPriceRows[k], pCount);
								data2.copyEntity(rd,"TA_DJ_BXJDJG"+JDID,"TA_DJ_BXJDJG");
							}
							//�徰��۸��
							coreDAO.insert("TA_DJ_BXJDJG", data2, conn);
							data2.remove("TA_DJ_BXJDJG");
						}
					}
					//���뾰��ƻ���
					coreDAO.insert("TA_DJ_BXJD", data1, conn);
					data1.remove("TA_DJ_BXJD");
				}
			}
			//�����Ŷӱ�����Ϣ�ܱ�
			updateTDBXZJXXB(rd, conn, groupID);
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
	/**�����Ŷӱ�����Ϣ�ܱ�
	 * @param rd
	 * @param conn
	 * @param groupID
	 * @throws SQLException
	 */
	public void updateTDBXZJXXB(BizData rd, Connection conn, String groupID)
			throws SQLException {
		BizData data = new BizData();
		data.add("TA_TDBXZJXXB", "TID", groupID);
		coreDAO.select(data);
		int TDBXrows = data.getTableRowsCount("TA_TDBXZJXXBs");
		data.remove("TA_TDBXZJXXB");
		data.remove("TA_TDBXZJXXBs");
		if(TDBXrows > 0){
			rd.addAttr("TA_TDBXZJXXB", "TID", 0, "oldValue", groupID);
			coreDAO.update("TA_TDBXZJXXB", rd, conn);
		}else{
			rd.add("TA_TDBXZJXXB", "TID", groupID);
			coreDAO.insert("TA_TDBXZJXXB",rd, conn);
		}
	}
}

