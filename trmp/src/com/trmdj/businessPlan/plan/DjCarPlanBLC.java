package com.trmdj.businessPlan.plan;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * ����ؽӳ��������������
 * 
 * @author [hl.Wei]
 * @date [Feb 20, 2012 11:28:59 AM]
 */
public class DjCarPlanBLC extends DBBLC
{
	/**
	 * ��ӡ��־
	 */
	private Logger log = null;

	/**
	 * Constructs
	 */
	public DjCarPlanBLC()
	{
		// ʵ����TA_DJ_JHCL
		this.entityName = "TA_DJ_JHCL";

		// ������־
		log = Logger.getLogger(DjCarPlanBLC.class);
	}

	/**
	 * ��ʼ����ѯ��ǰ���Ѱ��ŵĳ�����Ϣ
	 * 
	 * @param rd �������ݶ���
	 * @param sd ssesion���ݶ���
	 * @return �����
	 * @throws SQLException 
	 */
	public int query4Init(BizData rd, BizData sd) throws SQLException
	{
		// ��rd����������ΪTA_DJ_JHCL����ID
		rd.add("TA_DJ_JHCL", "TID", 0, rd.getString("GROUPID"));
		rd.add("TA_DJ_JHCL", "orgid", 0, sd.getString("orgid"));
		try
		{
			// ������ID����ѯ�Ƶ�-����������Ϣ
			this.query(rd,sd);
			
			String sql="select begin_date,ts from ta_dj_group where id ='"+rd.getString("GROUPID")+"' and orgid='"+sd.getString("orgid")+"'";
			
			coreDAO.executeQuery(sql, "TA_DJ_GROUPs", rd);

		}
		catch (SQLException e)
		{
			// ��ӡ��־
			log.error(e.getMessage(), e);

			return SysError.DB_ERROR;
		}

		// �����
		int random = 0;

		for (int i = 0; i < rd.getTableRowsCount("TA_DJ_JHCLs"); i++)
		{
			random = (int) (Math.floor(Math.random() * 123450));

			// װ���漴��
			rd.add("TA_DJ_JHCLs", "Random", i, random);
		}
		
		return 999;
	}

	/**
	 * ����Ƶ����ŵĳ�����Ϣ
	 * 
	 * @param rd �������ݶ���
	 * @param sd ssesion���ݶ���
	 * @return �����
	 * @throws SQLException 
	 */
	public int saveCarInfo(BizData rd, BizData sd) throws SQLException
	{
		// ��ȡ���ݿ�����
		Connection conn = coreDAO.getConnection();

		BizData data = new BizData();
		
		// ��ID
		String gId = rd.getString("GROUPID");

		// ����ɾ������
		data.add("TA_DJ_JHCL", "TID", 0, gId);
		data.add("TA_DJ_JHCL", "orgid", 0, sd.getString("orgid"));
		
		StringBuffer newVehTeam = new StringBuffer().append("[");
		// ����Ƶ�������Ϣ
		buildCarInfo(rd, sd, rd.getString("TEMP"), gId, newVehTeam);
		//�����ľƵ���Ϣ��ID
		if(newVehTeam.lastIndexOf(",") > 0){
					
			String newVeh = newVehTeam.substring(0, newVehTeam.length()-1);
			newVeh = newVeh+"]";
			rd.add("newBaseInfo", newVeh);
		}
		
		// ȡ���ӻ�����Ϣ
		BizData bd = (BizData) rd.get("baseVeh");

		try{
			// ��������
			coreDAO.beginTrasct(conn);

			// ɾ��ָ��������Ϣ
			this.delete(data, conn);

			// ����Ƶ�������Ϣ
			coreDAO.insert("TA_DJ_JHCL", rd, conn);
			
			// ���복�ӻ�����Ϣ
			coreDAO.insert("TA_CAR_TEAM", bd, conn);
			
			// �ύ����
			coreDAO.commitTrasct(conn);
		}
		catch (SQLException e)
		{
			// ��ӡ��־
			log.error(e.getMessage(), e);

			// ����ع�
			coreDAO.rollbackTrasct(conn);
			
			return SysError.DB_ERROR;
		}
		finally
		{
			if (null != conn)
			{
				conn.close();
				conn = null;
			}
		}

		return 999;
	}

	/**
	 * ����Ƶ�������Ϣ
	 * 
	 * @param rd �������ݶ���
	 * 
	 * 
	 */
	private void buildCarInfo(BizData rd, BizData sd,String state, String gId, StringBuffer newVehTeam)
	{
		// �ύ�ĳ�����Ŀ
		int rows = rd.getTableRowsCount("TA_DJ_JHCL");
		int[] fieldIndex = rd.getFieldIndex(rd, "TA_DJ_JHCL");
		
		// ���ӻ�������
		BizData bsData = new BizData();
		
		// �����ύ�ĳ�����Ϣ
		for (int i = 0; i < rows; i++)
		{
			// ���ID��IDΪ������ֵ����ʶΨһ
			rd.add(entityName, "ID", String.valueOf(fieldIndex[i]), this.queryMaxIDByPara(entityName, "ID", null));
			
			// �����ID
			rd.add(entityName, "TID", String.valueOf(fieldIndex[i]), rd.getString("groupId"));
			
			// ���״̬
			rd.add(entityName, "JHZT", String.valueOf(fieldIndex[i]) ,state);
			
			rd.add(entityName, "ZDR", String.valueOf(fieldIndex[i]) , sd.getString("userno"));
			
			// ��ӻ���ID
			rd.add(entityName, "ORGID", String.valueOf(fieldIndex[i]), sd.getString("orgid"));
			
			// �ж��Ƿ�Ϊ������������
			if ("".equals(rd.getString("entityName", "CD", String.valueOf(fieldIndex[i]))))
			{
				// �������ӻ�����Ϣ
				addBaseVehicle(bsData, rd, String.valueOf(fieldIndex[i]), sd .getString("orgid"), newVehTeam);
			}
		}

		// ���泵�ӻ�����Ϣ
		rd.add("baseVeh", bsData);
	}

	/**
	 * �������ӻ�����Ϣ
	 * 
	 * @param bsData
	 * @param rd
	 * @param valueOf
	 * @param string
	 */
	private void addBaseVehicle(BizData bsData, BizData rd, String index,
            String orgid, StringBuffer newVehTeam)
    {
		// ͳ�ƶ�����Ʊ�����������Ŀ
		int rows = bsData.getTableRowsCount("ta_car_team");
		
		// ȡ���ֵ
		int maxId = queryMaxIDByPara("ta_car_team", "TEAM_ID", null);
		
		// ��ӳ���ID������IDȡ���ֵ
		bsData.add("ta_car_team", "TEAM_ID", rows, maxId);
		
		// ��ӳ�������
		bsData.add("ta_car_team", "CMPNY_NAME", rows, rd.getString("TA_DJ_JHCL", "CDMC", index));
		
		// ��ӳ�����ϵ��
		bsData.add("ta_car_team", "CHIEF_NAME", rows, rd.getString("TA_DJ_JHCL", "LXR", index));
		
		// ��ӳ�����ϵ�˵绰
		bsData.add("ta_car_team", "CHIEF_MOBILE", rows, rd.getString("TA_DJ_JHCL", "LXRDH", index));
		
		// ��ӻ���ID
		bsData.add("ta_car_team", "orgid", rows, orgid);
		
		// �ƻ�����ӳ���ID
		rd.add(entityName, "CD", index, maxId);
		newVehTeam.append("{\"indexNm\":"+index+",\"id\":"+maxId+"},");
    }
}
