/**
 * DjSceneryBxBLC.java
 * com.trmdj.bx
 *
 * Function�� TODO 
 *
 *   ver     date      		author
 * ��������������������������������������������������������������������
 *   		 2011-8-2 		Administrator
 *
 * Copyright (c) 2011, TNT All Rights Reserved.
*/

package com.trm.ztbx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * ClassName:DjSceneryBxBLC
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Administrator
 * @version  
 * @since    Ver 1.1
 * @Date	 2011-8-2		����09:26:42
 *
 * @see 	 
 * @deprecated 
 */
public class ZtSceneryBxBLC extends DBBLC {
	public ZtSceneryBxBLC(){
		this.entityName = "TA_ZT_BXJD";
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
				String groupID = rd.getStringByDI("TA_ZT_BXJD", "TID", 0);
				String rbtSceneryInfoEdit = "select j.id,j.tid,j.sfid,j.csid,j.jdid,j.jgqj,j.sfxz,j.qdxj,j.xfxj,j.hj,j.bz,p.scenery_id,p.cmpny_name,p.city_id \n" +
											" from ta_scenery_point p,ta_zt_bxjd j \n"+
											"where j.jdid = p.scenery_id \n"+
											"and j.csid = p.city_id \n"+
											"and j.tid = '"+ groupID +"' \n"+
											"order by j.csid";
				String rbtSceneryPriceEdit = "select j.* from ta_zt_bxjdjg j \n" +
											 "where j.tid= '"+ groupID +"'";
				String rbtCityDisEdit = "select distinct j.csid,j.sfid  from ta_zt_bxjd j \n"+
										"where j.tid = '"+ groupID +"' \n"+
										"order by j.csid";
				String rbtJDXXZJBEdit = "select BX_JDXF XF,BX_JDQD QD,JINDHJ ZJ from TA_TDBXZJXXB_ZT where tid='"+ groupID +"'";
				coreDAO.executeQuery(rbtJDXXZJBEdit,"rbtJDXXZJB",rd);//�Ŷӱ����ܼ���Ϣ��
				coreDAO.executeQuery(rbtSceneryInfoEdit,"rbtSceneryInfo",rd);//�ű������� ��
				coreDAO.executeQuery(rbtSceneryPriceEdit,"rbtSceneryPrice",rd);//�ű����۸��
				coreDAO.executeQuery(rbtCityDisEdit,"rbtCityDis",rd);//�ű��� ȥ �ظ� ����
			}
			
			if("init".equals(RbtState)){//���� ��ʼ�� ״̬
				String groupID = rd.getStringByDI("TA_ZT_BXJD", "TID", 0);
				String rbtSceneryInfoInit = "select j.id,j.tid,j.sfid,j.csid,j.jdid,j.jgqj,j.sfxz,j.qdxj,j.xfxj,j.hj,p.scenery_id,p.cmpny_name,p.city_id \n" +
										" from ta_scenery_point p,ta_zt_jhjd j \n" +
										"where j.jdid = p.scenery_id \n"+
										"and j.csid = p.city_id \n"+
										"and j.sfxz = 'Y' \n" +
										"and j.tid = '"+ groupID +"' \n"+
										"order by j.csid";
				String rbtSceneryPriceInit ="select  j.id,j.JHID BXID,j.JGMCID,j.JGMC,j.JG,j.RS from ta_zt_jhjdjg j \n" +
										"where j.tid= '"+ groupID +"'";
				String rbtCityDisInit = "select distinct j.csid,j.sfid  from ta_zt_jhjd j \n"+
									"where j.tid = '"+ groupID +"' \n"+
									"order by j.csid";
				String rbtJDXXZJBInit = "select XFJDZJ XF,QDJDZJ QD,JDZJ ZJ from TA_TDJDXXZJB_zt where tid='"+ groupID +"'";
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
		String groupID = rd.getStringByDI("TA_ZT_BXJD","TID",0);//�ź�
		String BXR = rd.getString("userno");//�ƶ���
		String STATE = rd.getString("state");//״̬
		String STATEBX = "Y";//����״̬
		try {
			coreDAO.beginTrasct(conn);
			//��� ״̬ Ϊ Edit   �޸� ��ɾ�������
			if("Edit".equals(STATE)){
				BizData del1 = new BizData();
				del1.add("TA_ZT_BXJD", "TID", groupID);
				coreDAO.delete(del1, conn);
				del1.remove("TA_ZT_BXJD");
				del1.add("TA_ZT_BXJDJG", "TID", groupID);
				coreDAO.delete(del1, conn);
				del1.remove("TA_ZT_BXJDJG");
			}
			String[] rows = rd.getRowIDs("TA_ZT_BXJD");
			for(int i=0;i<rows.length;i++){
				String CSID = rd.getString("TA_ZT_BXJD", "CSID", rows[i]);//�ƻ�����
				String SFID = rd.getString("TA_ZT_BXJD", "SFID", rows[i]);//�ƻ�ʡ��
				String[] SceneryRows = rd.getRowIDs("JDID"+CSID);//��ȡ��������
				if(null != SceneryRows){
					BizData data1 = new BizData();
					for(int j = 0; j < SceneryRows.length; j++){
						int BXID = this.queryMaxIDByPara("TA_ZT_BXJD","ID",null);//�ƻ�ID
						String JDID = rd.getString("JDID"+CSID, "JDID", SceneryRows[j]);//����ID
						String BZ = rd.getString("BZ"+JDID);//����ID
						String JGQJ = rd.getString("dwj"+JDID);//�۸����� ������
						String SFXZ = rd.getString("SFXZ"+JDID);//�Ƿ�ѡ��
						String QDXJ = rd.getString("QDXJ"+JDID);//ǩ��С��
						String XFXJ = rd.getString("XFXJ"+JDID);//�ָ�С��
						String HJ = rd.getString("HJ"+JDID);//�ϼ�
						rd.add("TA_ZT_BXJD"+CSID, "ID", SceneryRows[j], BXID);
						rd.add("TA_ZT_BXJD"+CSID, "TID", SceneryRows[j], groupID);//�ź�
						rd.add("TA_ZT_BXJD"+CSID, "SFID", SceneryRows[j], SFID);
						rd.add("TA_ZT_BXJD"+CSID, "CSID", SceneryRows[j], CSID);
						rd.add("TA_ZT_BXJD"+CSID, "JDID", SceneryRows[j], JDID);
						rd.add("TA_ZT_BXJD"+CSID, "JGQJ", SceneryRows[j], JGQJ);
						rd.add("TA_ZT_BXJD"+CSID, "SFXZ", SceneryRows[j], SFXZ);
						rd.add("TA_ZT_BXJD"+CSID, "QDXJ", SceneryRows[j], QDXJ);
						rd.add("TA_ZT_BXJD"+CSID, "XFXJ", SceneryRows[j], XFXJ);
						rd.add("TA_ZT_BXJD"+CSID, "HJ", SceneryRows[j], HJ);
						rd.add("TA_ZT_BXJD"+CSID, "BZ", SceneryRows[j], BZ);
						rd.add("TA_ZT_BXJD"+CSID, "BXZT", SceneryRows[j], STATEBX);//�ƻ�״̬
						rd.add("TA_ZT_BXJD"+CSID, "BXR", SceneryRows[j], BXR);//������
						data1.copyEntity(rd,"TA_ZT_BXJD"+CSID,"TA_ZT_BXJD");
						
						String[] SceneryPriceRows = rd.getRowIDs("TA_ZT_BXJDJG"+JDID);
						if(null!=SceneryPriceRows){
							BizData data2 = new BizData();
							for(int k = 0; k < SceneryPriceRows.length; k++){
								int priceID = queryMaxIDByPara("TA_ZT_BXJDJG", "ID", null);//����۸�ID
								String priceNameID = rd.getString("TA_ZT_BXJDJG"+JDID, "JGMCID", SceneryPriceRows[k]);//�۸�����ID
								String priceName = rd.getString("TA_ZT_BXJDJG"+JDID, "JGMC", SceneryPriceRows[k]);//�۸�����
								String price = rd.getString("TA_ZT_BXJDJG"+JDID, "JG", SceneryPriceRows[k]);//�۸�
								String pCount = rd.getString("TA_ZT_BXJDJG"+JDID, "RS", SceneryPriceRows[k]);//����
								rd.add("TA_ZT_BXJDJG"+JDID, "ID", SceneryPriceRows[k], priceID);
								rd.add("TA_ZT_BXJDJG"+JDID, "BXID", SceneryPriceRows[k], BXID);
								rd.add("TA_ZT_BXJDJG"+JDID, "TID", SceneryPriceRows[k], groupID);
								rd.add("TA_ZT_BXJDJG"+JDID, "JGMCID", SceneryPriceRows[k], priceNameID);
								rd.add("TA_ZT_BXJDJG"+JDID, "JGMC", SceneryPriceRows[k], priceName);
								rd.add("TA_ZT_BXJDJG"+JDID, "JG", SceneryPriceRows[k], price);
								rd.add("TA_ZT_BXJDJG"+JDID, "RS", SceneryPriceRows[k], pCount);
								data2.copyEntity(rd,"TA_ZT_BXJDJG"+JDID,"TA_ZT_BXJDJG");
							}
							//�徰��۸��
							coreDAO.insert("TA_ZT_BXJDJG", data2, conn);
							data2.remove("TA_ZT_BXJDJG");
						}
					}
					//���뾰��ƻ���
					coreDAO.insert("TA_ZT_BXJD", data1, conn);
					data1.remove("TA_ZT_BXJD");
				}
			}
			//�����Ŷӱ�����Ϣ�ܱ�
			updateTDBXZJXXB(rd, conn, groupID);
			BizData dtt = new BizData();
			dtt.addAttr("TA_ZT_GROUP", "ID", 0, "oldValue", groupID);
			dtt.add("TA_ZT_GROUP", "STATE", "5");//�޸���״̬Ϊ 5  ʵʩ��
			coreDAO.update("TA_ZT_GROUP", dtt, conn);
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
		data.add("TA_TDBXZJXXB_ZT", "TID", groupID);
		coreDAO.select(data);
		int TDBXrows = data.getTableRowsCount("TA_TDBXZJXXB_ZTs");
		data.remove("TA_TDBXZJXXB_ZT");
		data.remove("TA_TDBXZJXXB_ZTs");
		if(TDBXrows > 0){
			rd.addAttr("TA_TDBXZJXXB_ZT", "TID", 0, "oldValue", groupID);
			coreDAO.update("TA_TDBXZJXXB_ZT", rd, conn);
		}else{
			rd.add("TA_TDBXZJXXB_ZT", "TID", groupID);
			coreDAO.insert("TA_TDBXZJXXB_ZT",rd, conn);
		}
	}
}

