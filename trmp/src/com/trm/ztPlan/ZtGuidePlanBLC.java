package com.trm.ztPlan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * @Title: DjPlanBLC.java
 * @Package com.trmdj.businessPlan.plan
 * @Description: TODO(����)
 * @author Kale ym_x@qq.com
 * @date 2011-7-13 ����02:37:32
 * @version V1.0
 */
public class ZtGuidePlanBLC extends DBBLC {
	
	public ZtGuidePlanBLC(){
		this.entityName="TA_ZT_JHDY";
	}
	
	/**
	 * 
	 * @Title: selectGuideFY
	 * @Description: (�����źŲ�ѯ��Ӧ���η���)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    �趨�ļ�
	 * @return int    ��������
	 * @throws
	 */
	public int selectGuideFY(BizData rd,BizData sd){
		String groupId=rd.getString("TID");
		String sql="select sum(d.dylk) dylk,sum(d.dff) dff from TA_ZT_jhdy d \n"+
		"where d.TID='"+groupId+"'";
		try {
			coreDAO.executeQuery(sql, "selectGuideFY", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * 
	 * @Title: searchGuide
	 * @Description: TODO(��ѯ���е�������)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    �趨�ļ�
	 * @return int    ��������
	 * @throws
	 */
	public int selectAllGuide(BizData rd,BizData sd){
		
		String role = rd.getString("role");
		String sql="select *\n"+
		"from drmuser u,drmuserrole o\n" +
		"where u.userid=o.userid\n" +
		"and o.roleid='"+role+"'";
		
		String groupId=rd.getString("TID");
		String sql2="select h.* from TA_ZT_jhdy h where h.tid='"+groupId+"' order by h.id asc";
		try {
			coreDAO.executeQuery(sql, "SelectAllGuide", rd);
			coreDAO.executeQuery(sql2, "selectGuideInfo", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		rd.add("action", rd.getString("action"));
		return 1;
	}
	
	/**
	 * 
	 * @Title: updateGuidePlan
	 * @Description: TODO(�޸ĵ��μƻ�)
	 * @param @param rd
	 * @param @param sd
	 * @param @return    �趨�ļ�
	 * @return int    ��������
	 * @throws
	 */
	public int updateGuidePlan(BizData rd, BizData sd) {
		Connection conn = coreDAO.getConnection();
		String rowIds[] = rd.getRowIDs("TA_ZT_JHDY");
		String ZDR = sd.getString("userno");
		String JHZT = "Y";
		try {
			coreDAO.beginTrasct(conn);
			
			//�ȸ����ź�ɾ����Ӧ���μƻ�
			String groupID = rd.getStringByDI("TA_ZT_JHDY", "TID", 0);
			BizData data = new BizData();
			data.add("TA_ZT_JHDY", "TID", groupID);
			coreDAO.delete(data,conn);
			data.remove("TA_ZT_JHDY");
			
			for(int i=0;i<rowIds.length;i++){
				//ȡ����
				int id=this.queryMaxIDByPara("TA_ZT_JHDY", "ID", null);
				rd.add("TA_ZT_JHDY", "ID",rowIds[i],id);
				rd.add("TA_ZT_JHDY", "TID",rowIds[i],groupID);
				rd.add("TA_ZT_JHDY", "JHZT",rowIds[i],JHZT);
				rd.add("TA_ZT_JHDY", "ZDR",rowIds[i],ZDR);
				String bxr = rd.getString("TA_ZT_JHDY", "bxr", rowIds[i]);
				if(bxr.equals(""))
					rd.add("TA_ZT_JHDY", "bxr",rowIds[i],"N");
			}
			coreDAO.insert("TA_ZT_JHDY", rd,conn);
			rd.remove("TA_ZT_JHDY");
			BizData dtt = new BizData();
			dtt.addAttr("TA_ZT_GROUP", "ID", 0, "oldValue", groupID);
			dtt.add("TA_ZT_GROUP", "STATE", "2");//�޸���״̬Ϊ 2  ʵʩ��
			coreDAO.update("TA_ZT_GROUP", dtt, conn);
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
		rd.remove("TA_ZT_JHDY");
		return 1;
	}
	public int queryBxFyList(BizData rd, BizData sd){
		String groupID= rd.getStringByDI("TA_ZT_JHDYs", "TID", 0);
		String sql="";
		sql=" select d.tid,d.dyid,d.dyxm,d.dylk,d.dff \n" +  
		"  from TA_ZT_group t,TA_ZT_jhdy d \n"+ 
		"  where d.tid=t.id \n" +
		" and t.id='"+groupID+"'";
		try{
			coreDAO.executeQuery(sql, "GuideFyInfo", rd);
		}catch(SQLException e){
			e.fillInStackTrace();
		}
		return 1;
	}
}
