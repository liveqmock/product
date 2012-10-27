package com.trm.ztPlan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


/**
 * ClassName:DjShoppingPlanBLC
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Administrator
 * @version  
 * @since    Ver 1.1
 * @Date	 2011	2011-8-4		����09:30:57
 *
 * @see 	 
 * @deprecated 
 */
public class ZtShoppingPlanBLC extends DBBLC{
	public ZtShoppingPlanBLC(){
		this.entityName="TA_ZT_JHGW";
	}
	/**  
	 * getAllShopAndRTF(��ȡ���ŵĹ������Ϣ �Լ� ���ݿ�Դ�ػ�ȡ��ͷ����Ϣ)<br/>  
	 * @param rd
	 * @param sd
	 * @return   
	 * @return_type (int)
	 * @exception   
	 * @since  1.0.0  
	*/
	public int getAllShopAndRTF(BizData rd,BizData sd){

		String city_id=rd.getString("city");
		String ywlb = rd.getString("ywlb");
		String sqlAddGWD="select distinct a.shop_point_id,a.cmpny_name,a.city_id from ta_shoppoint a where a.city_id='"+city_id+"' and a.ywlb='"+ywlb+"' order by a.shop_point_id";
		String sqlAddRTF="select h.id, h.name,h.Pre_Pee from xzqh h where h.pid=0 and id=3";
		try {
			coreDAO.executeQuery(sqlAddGWD, "shopListAddGWD", rd);//AJAX���� ��ѯ�������Ϣ
			coreDAO.executeQuery(sqlAddRTF, "shopListAddRTF", rd);//AJAX���� ��ѯ���ſ�Դ����ͷ��
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**  
	 * djGetShopInfo(��ʼ���޸Ĺ���ƻ���Ϣʱ ���ظ��ŵĹ���ƻ���Ϣ)<br/>  
	 * @param rd
	 * @param sd
	 * @return   
	 * @return_type (int)
	 * @exception   
	 * @since  1.0.0  
	*/
	public int ztGetShopInfo(BizData rd,BizData sd){
		String groupID=rd.getStringByDI("TA_ZT_JHGW","TID",0);//��ȡ�ź�
		String sqlEditGWD="select distinct  w.SFID,w.csid,w.gwdid,w.gwdmc,w.sfxz  from TA_ZT_jhgw w where w.tid='"+groupID+"' order by w.gwdid";
		String sqlEditRTF="select distinct  f.csid,f.rtf,h.name,w.gwdid from TA_ZT_gwrtf f,TA_ZT_jhgw w,xzqh h \n"+
						  "where f.jhid=w.id \n" +
						  "and f.csid=h.id \n" +
						  "and w.tid='"+groupID+"'" +
						  "order by w.gwdid";//�޸Ĳ�ѯ���
		//String sqlEditRTF="select h.id, h.name,h.Pre_Pee from xzqh h where h.pid=0 and id=3";
		String sqlCityInfo="select distinct  w.SFID,w.csid from TA_ZT_jhgw w where w.tid='"+groupID+"' order by w.SFID";
		try {
			coreDAO.executeQuery(sqlCityInfo, "sqlCityInfo", rd);//��ѯ���Ź���� ʡ��ID ����ID
			coreDAO.executeQuery(sqlEditGWD, "shopListEditGWD", rd);//�޸�ҳ����ع������Ϣ
			coreDAO.executeQuery(sqlEditRTF, "shopListEditRTF", rd);//�޸ĸ�ҳ�������ͷ����Ϣ
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	/**  
	 * djShopRTFGeneral(����ƻ���� �޸� ɾ�� �ۺϲ���)<br/>  
	 * @param rd
	 * @param sd
	 * @return   
	 * @return_type (int)
	 * @exception   
	 * @since  1.0.0  
	*/
	public int ztShopGeneral(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		String groupID = rd.getStringByDI("TA_ZT_JHGW","TID",0);//��ȡ�ź�
		rd.add("groupId", groupID);
		String PLANSTATE = rd.getString("state");
		String JHZDR = rd.getString("JHZDR");
		String GWJHZT = "Y";
		try{
			coreDAO.beginTrasct(conn);
			if("Edit".equals(PLANSTATE)){//�ж�״̬ EditΪ�޸� �޸�ǰ�� ɾ�� �� ���
				BizData data = new BizData();
				data.add("TA_ZT_GWRTF", "TID", groupID);
				coreDAO.delete(data, conn);//�����ź� ɾ��������ͷ�ѱ�
				data.remove("TA_ZT_GWRTF");
				data.add("TA_ZT_JHGW", "TID", groupID);
				coreDAO.delete(data, conn);//�����ź� ɾ������ƻ���
				data.remove("TA_ZT_JHGW");
			}
			String[] rows = rd.getRowIDs("TA_ZT_JHGW");//��ȡ����ƻ�������
			BizData rd2=new BizData();
			BizData rd3=new BizData();
			for(int i = 0;i < rows.length;i++){
				String CSID = rd.getString("TA_ZT_JHGW", "CSID", rows[i]);
				String SFID = rd.getString("TA_ZT_JHGW", "SFID", rows[i]);
				String[] rows_shop = rd.getRowIDs("TA_ZT_JHGW"+CSID);
					for(int j = 0;j < rows_shop.length; j++){
							String SFXZ=rd.getString("TA_ZT_JHGW"+CSID, "SFXZ", rows_shop[j]);
							if(!"".equals(SFXZ)){
								int gwjhid=this.queryMaxIDByPara("TA_ZT_JHGW", "ID", null);//��ȡ����ƻ�ID
								String GWDID=rd.getString("TA_ZT_JHGW"+CSID, "GWDID", rows_shop[j]);
								String GWDMC=rd.getString("TA_ZT_JHGW"+CSID, "GWDMC", rows_shop[j]);
								rd2.add("TA_ZT_JHGW"+CSID, "ID", rows_shop[j], gwjhid);
								rd2.add("TA_ZT_JHGW"+CSID, "TID", rows_shop[j], groupID);
								rd2.add("TA_ZT_JHGW"+CSID, "SFID", rows_shop[j], SFID);
								rd2.add("TA_ZT_JHGW"+CSID, "CSID", rows_shop[j], CSID);
								rd2.add("TA_ZT_JHGW"+CSID, "GWDID", rows_shop[j], GWDID);
								rd2.add("TA_ZT_JHGW"+CSID, "GWDMC", rows_shop[j], GWDMC);
								rd2.add("TA_ZT_JHGW"+CSID, "SFXZ", rows_shop[j], SFXZ);
								rd2.add("TA_ZT_JHGW"+CSID, "JHZDR", rows_shop[j], JHZDR);
								rd2.add("TA_ZT_JHGW"+CSID, "JHZT", rows_shop[j], GWJHZT);
								
								
								int rtfid=this.queryMaxIDByPara("TA_ZT_GWRTF", "ID", null);//������ͷ��ID
								String gwrtf = rd.getString("TA_ZT_GWRTF"+GWDID, "RTF", 0);//������ͷ��
								String gwrtfCSID = rd.getString("TA_ZT_GWRTF"+GWDID, "CSID", 0);//������ͷ�ѳ���ID
								rd3.add("TA_ZT_GWRTF"+GWDID, "ID", 0, rtfid);//��ͷ��ID
								rd3.add("TA_ZT_GWRTF"+GWDID, "JHID", 0, gwjhid);//�ƻ�ID
								rd3.add("TA_ZT_GWRTF"+GWDID, "TID", 0, groupID);//��ID
								rd3.add("TA_ZT_GWRTF"+GWDID, "RTF", 0, gwrtf);//RTF
								rd3.add("TA_ZT_GWRTF"+GWDID, "CSID", 0, gwrtfCSID);//����ID
								rd3.copyEntity(rd3, "TA_ZT_GWRTF"+GWDID, "TA_ZT_GWRTF");
							}
							
					}
					rd2.copyEntity(rd2, "TA_ZT_JHGW"+CSID, "TA_ZT_JHGW");
					
				}
			rd.remove("TA_ZT_JHGW");
			coreDAO.insert("TA_ZT_GWRTF",rd3,conn);
			rd3.remove("TA_ZT_GWRTF");
			coreDAO.insert("TA_ZT_JHGW",rd2,conn);
			rd2.remove("TA_ZT_JHGW");
			
			BizData dtt = new BizData();
			dtt.addAttr("TA_ZT_GROUP", "ID", 0, "oldValue", groupID);
			dtt.add("TA_ZT_GROUP", "STATE", "2");//�޸���״̬Ϊ 2  ʵʩ��
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
}