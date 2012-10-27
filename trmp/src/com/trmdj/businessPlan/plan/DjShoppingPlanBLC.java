package com.trmdj.businessPlan.plan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.string.PyUtil;


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
public class DjShoppingPlanBLC extends DBBLC{
	public DjShoppingPlanBLC(){
		this.entityName="TA_DJ_JHGW";
	}
	

	/** ����������Ϣ
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012��2��17��16:27:01
	 * @throws SQLException
	 */
	public int insertShop(BizData rd, BizData sd) throws SQLException{
		

		Connection conn=coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			String[] shopRow = rd.getRowIDs("TA_DJ_JHGW");// ��ȡ��¼��
			String groupId = rd.getString("groupId");// ��ȡ�ź�
			String temp = rd.getString("temp");// ��ȡ�ƻ�״̬
			String zDr = sd.getString("userno");// ��ȡָ����

			// �����ź�ɾ���ӵ�ƻ�
			BizData data = new BizData();
			data.add("TA_DJ_JHGW", "TID", groupId);
			data.add("TA_DJ_JHGW", "orgid", sd.getString("orgid"));
			coreDAO.delete(data, conn);// ɾ����ʷ����
			data.remove("TA_DJ_JHGW");

			StringBuffer theNewShop = new StringBuffer().append("[");
			for (int i = 0; i < shopRow.length; i++) {
				
				int shopId = queryMaxIDByPara("TA_DJ_JHGW", "ID", null);
				int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_JHGW");
				rd.add("TA_DJ_JHGW", "ID", String.valueOf(fieldIndex[i]), shopId);
				rd.add("TA_DJ_JHGW", "TID", String.valueOf(fieldIndex[i]), groupId);
				rd.add("TA_DJ_JHGW", "JHZT", String.valueOf(fieldIndex[i]), temp);
				rd.add("TA_DJ_JHGW", "JHZDR", String.valueOf(fieldIndex[i]), zDr);
				rd.add("TA_DJ_JHGW", "orgid", String.valueOf(fieldIndex[i]), sd.getString("orgid"));

				// ���������
				String gwdMc = rd.getString("TA_DJ_JHGW", "GWDMC",String.valueOf(fieldIndex[i]));
				// �����ID
				String gwdID = rd.getString("TA_DJ_JHGW", "GWDID",String.valueOf(fieldIndex[i]));
				// ��������û�д˹������Ϣ�����
				if ("".equals(gwdID)) {
					
					int baseShopID = queryMaxIDByPara("TA_SHOPPOINT", "SHOP_POINT_ID", null);
					data.add("TA_SHOPPOINT", "CMPNY_NAME", gwdMc);
					String nameCode = PyUtil.get1stLetterOf4Chars(gwdMc);
					data.add("TA_SHOPPOINT", "NAMECODE", nameCode);
					data.add("TA_SHOPPOINT", "SHOP_POINT_ID", baseShopID);
					data.add("TA_SHOPPOINT", "CHIEF_NAME", rd.getString("TA_DJ_JHGW", "LXR", String.valueOf(fieldIndex[i])));
					data.add("TA_SHOPPOINT", "CHIEF_MOBILE", rd.getString("TA_DJ_JHGW", "LXFS", String.valueOf(fieldIndex[i])));
					data.add("TA_SHOPPOINT", "orgid", sd.getString("orgid"));
					coreDAO.insert(data, conn);
					data.remove("TA_SHOPPOINT");
					rd.add("TA_DJ_JHGW", "GWDID", String.valueOf(fieldIndex[i]), baseShopID);
					
					//���ظ������Ļ�����Ϣ����������ֵ�͵�ǰ������
					theNewShop.append("{\"indexNm\":"+String.valueOf(fieldIndex[i])+",\"id\":"+baseShopID+"},");
				}
			}
			
			if(theNewShop.lastIndexOf(",") > 0){
				
				String newShop = theNewShop.substring(0, theNewShop.length()-1);
				newShop = newShop+"]";
				rd.add("newBaseInfo", newShop);
			}
			coreDAO.insert("TA_DJ_JHGW", rd, conn);// �������
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
	/**Ajax��ʼ������ҳ��
	 * @param rd
	 * @param sd
	 * @return
	 * @time 2012��2��17��16:27:01
	 * @throws SQLException
	 */
	public int djAjaxShopInfo(BizData rd, BizData sd) {
		String groupId = rd.getString("groupId");
		String sql = "select * from TA_DJ_JHGW where tid='"+groupId+"' and orgid="+sd.getString("orgid");
		try {
			coreDAO.executeQuery(sql, "TA_DJ_JHGW", rd);
			sql="select begin_date,ts from ta_dj_group where id ='"+groupId+"' and orgid='"+sd.getString("orgid")+"'";
			coreDAO.executeQuery(sql, "TA_DJ_GROUP", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int tRow = rd.getTableRowsCount("TA_DJ_JHGW");//��ȡ��ѯ��¼����
		int random = 0;//װ�����
		
		for(int i = 0; i < tRow; i++){
			random = (int) (Math.floor(Math.random() * 123450));
			rd.add("TA_DJ_JHGW","Random", i, random);//װ���漴��
		}
		return 999;
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
		String groupID=rd.getString("TID");//��ȡ�ź�
		String city_id=rd.getString("city");
		String sqlAddGWD="select distinct  a.shop_point_id,a.cmpny_name,a.city_id from ta_shoppoint a where a.city_id='"+city_id+"' order by a.shop_point_id";
		String sqlAddRTF="select distinct  h.id, h.name,h.Pre_Pee from xzqh h,ta_dj_tzts t \n"+
					"where t.kyd=h.id \n"+
					"and t.tid='"+groupID+"' order by h.id";
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
	public int djGetShopInfo(BizData rd,BizData sd){
		String groupID=rd.getStringByDI("TA_DJ_JHGW","TID",0);//��ȡ�ź�
		String sqlEditGWD="select distinct  w.SFID,w.csid,w.gwdid,w.gwdmc,w.sfxz  from ta_dj_jhgw w where w.tid='"+groupID+"' order by w.gwdid";
		String sqlEditRTF="select distinct  f.csid,f.rtf,h.name,w.gwdid from ta_dj_gwrtf f,ta_dj_jhgw w,xzqh h \n"+
						  "where f.jhid=w.id \n" +
						  "and f.csid=h.id \n" +
						  "and w.tid='"+groupID+"'" +
						  "order by w.gwdid";//�޸Ĳ�ѯ���
		String sqlCityInfo="select distinct  w.SFID,w.csid from ta_dj_jhgw w where w.tid='"+groupID+"' order by w.SFID";
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
	public int djShopGeneral(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		String groupID = rd.getStringByDI("TA_DJ_JHGW","TID",0);//��ȡ�ź�
		String PLANSTATE = rd.getString("state");
		String JHZDR = rd.getString("JHZDR");
		String GWJHZT = "Y";
		try{
			coreDAO.beginTrasct(conn);
			if("Edit".equals(PLANSTATE)){//�ж�״̬ EditΪ�޸� �޸�ǰ�� ɾ�� �� ���
				BizData data = new BizData();
				data.add("TA_DJ_GWRTF", "TID", groupID);
				coreDAO.delete(data, conn);//�����ź� ɾ��������ͷ�ѱ�
				data.remove("TA_DJ_GWRTF");
				data.add("TA_DJ_JHGW", "TID", groupID);
				coreDAO.delete(data, conn);//�����ź� ɾ������ƻ���
				data.remove("TA_DJ_JHGW");
			}
			String[] rows = rd.getRowIDs("TA_DJ_JHGW");//��ȡ����ƻ�������
				BizData rd2=new BizData();
				BizData rd3=new BizData();
				for(int i = 0;i < rows.length;i++){
					String CSID = rd.getString("TA_DJ_JHGW", "CSID", rows[i]);
					String SFID = rd.getString("TA_DJ_JHGW", "SFID", rows[i]);
					String[] rows_shop = rd.getRowIDs("TA_DJ_JHGW"+CSID);
						for(int j = 0;j < rows_shop.length; j++){
								String SFXZ=rd.getString("TA_DJ_JHGW"+CSID, "SFXZ", rows_shop[j]);
								if(!"".equals(SFXZ)){
									int gwjhid=this.queryMaxIDByPara("TA_DJ_JHGW", "ID", null);//��ȡ����ƻ�ID
									String GWDID=rd.getString("TA_DJ_JHGW"+CSID, "GWDID", rows_shop[j]);
									String GWDMC=rd.getString("TA_DJ_JHGW"+CSID, "GWDMC", rows_shop[j]);
									rd2.add("TA_DJ_JHGW"+CSID, "ID", rows_shop[j], gwjhid);
									rd2.add("TA_DJ_JHGW"+CSID, "TID", rows_shop[j], groupID);
									rd2.add("TA_DJ_JHGW"+CSID, "SFID", rows_shop[j], SFID);
									rd2.add("TA_DJ_JHGW"+CSID, "CSID", rows_shop[j], CSID);
									rd2.add("TA_DJ_JHGW"+CSID, "GWDID", rows_shop[j], GWDID);
									rd2.add("TA_DJ_JHGW"+CSID, "GWDMC", rows_shop[j], GWDMC);
									rd2.add("TA_DJ_JHGW"+CSID, "SFXZ", rows_shop[j], SFXZ);
									rd2.add("TA_DJ_JHGW"+CSID, "JHZDR", rows_shop[j], JHZDR);
									rd2.add("TA_DJ_JHGW"+CSID, "JHZT", rows_shop[j], GWJHZT);
									
									
									int rtfid=this.queryMaxIDByPara("TA_DJ_GWRTF", "ID", null);//������ͷ��ID
									String gwrtf = rd.getString("TA_DJ_GWRTF"+GWDID, "RTF", 0);//������ͷ��
									String gwrtfCSID = rd.getString("TA_DJ_GWRTF"+GWDID, "CSID", 0);//������ͷ�ѳ���ID
									rd3.add("TA_DJ_GWRTF"+GWDID, "ID", 0, rtfid);//��ͷ��ID
									rd3.add("TA_DJ_GWRTF"+GWDID, "JHID", 0, gwjhid);//�ƻ�ID
									rd3.add("TA_DJ_GWRTF"+GWDID, "TID", 0, groupID);//��ID
									rd3.add("TA_DJ_GWRTF"+GWDID, "RTF", 0, gwrtf);//RTF
									rd3.add("TA_DJ_GWRTF"+GWDID, "CSID", 0, gwrtfCSID);//����ID
									rd3.copyEntity(rd3, "TA_DJ_GWRTF"+GWDID, "TA_DJ_GWRTF");
								}
								
						}
						rd2.copyEntity(rd2, "TA_DJ_JHGW"+CSID, "TA_DJ_JHGW");
						
					}
				rd.remove("TA_DJ_JHGW");
				coreDAO.insert("TA_DJ_GWRTF",rd3,conn);
				rd3.remove("TA_DJ_GWRTF");
				coreDAO.insert("TA_DJ_JHGW",rd2,conn);
				rd2.remove("TA_DJ_JHGW");
				
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
}