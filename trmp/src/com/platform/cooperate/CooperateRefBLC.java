package com.platform.cooperate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.dream.app.district.DistrictBean;
import com.dream.app.district.DistrictImp;
import com.dream.app.district.DistrictItfc;
import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.blc.IBLContext;
import com.dream.bizsdk.common.databus.BizData;



public class CooperateRefBLC extends DBBLC {
	private static Logger log = Logger.getLogger(CooperateRefBLC.class);

	public CooperateRefBLC() {
		this.entityName = "TA_DJ_QRJ";
	}
	
	public int cooperateList(BizData rd,BizData sd){
		
		int pageNO = Integer.parseInt(rd.getString("pageNO"));
		int pageSize = Integer.parseInt(rd.getString("pageSize"));
		StringBuffer sql = new StringBuffer();
		sql.append("select o.orgid curr,o.name curr_name,o2.orgid ref_org, o2.name ref_name,o2.CMP_INTRO,o2.REGISTERDATE,x.name provincename\n");
		sql.append(" from ta_travelagencyref t,hrorganization o, hrorganization o2,xzqh x\n");
		sql.append("where o.orgid=t.orgid and t.relation_orgid=o2.orgid and o2.province=x.id\n");
		sql.append("and o.orgid=").append(sd.getString("orgid"));
		try {
			log.debug("��ѯ��ǰ�û������к����磺"+sql.toString());
			coreDAO.executeQueryPage(sql.toString(), "rsTravelAgencys", pageNO, pageSize, rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 98;
	}
	
	public int trasPlatformList(BizData rd,BizData sd) {
		
		int pageNO = Integer.parseInt(rd.getString("pageNO"));
		int pageSize = Integer.parseInt(rd.getString("pageSize"));
		String orgName = rd.getString("orgName");
		String xzqh = rd.getString("xzqh");
		HttpServletRequest request = (HttpServletRequest)rd.get(SysVar.HTTP_REQ);
		IBLContext context = (IBLContext)request.getSession().getServletContext().getAttribute(SysVar.APP_BLCONTEXT);
		List<DistrictBean> allAreaBeans = context.getDistrictBeans();
		StringBuffer sql = new StringBuffer();
		sql.append("select o.orgid curr,o.name curr_name,o.cmp_intro,o.compyadd,o.cityname,x.name privinceName,o.registerdate\n");
		sql.append(" from hrorganization o,xzqh x\n");
		sql.append("where o.province=x.id \n");
		if(!orgName.equals(""))
			sql.append("and o.name like '%").append(orgName).append("%'\n");
		//��������������һ����������ȡ��������������
		DistrictItfc district = new DistrictImp();
		List<DistrictBean> beans = district.adownDistrict(xzqh, allAreaBeans);
		StringBuffer xzqhs = new StringBuffer();
		xzqhs.append(xzqh).append(",");
		for(int i=0;i<beans.size();i++){
			DistrictBean bean = beans.get(i);
			xzqhs.append(bean.getId()).append(",");
		}
		
		if(!"".equals(xzqh))
			sql.append("and city in (").append(xzqhs.toString().substring(0,xzqhs.length()-1)).append(")");
		try {
			log.debug("��ѯ���������磺"+sql.toString());
			coreDAO.executeQueryPage(sql.toString(), "rsAllOrgs", pageNO, pageSize, rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 98;
	}
	
	/**
	 * ��ѯ��ǰ�����Ƿ��ע��ָ��������Ϣ
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int queryRelation(BizData rd,BizData sd){
		
		String thisOrg = sd.getString("orgid");
		String relOrg = rd.getStringByDI("hrorganization", "orgid", 0);
		String sql = "select * from TA_TRAVELAGENCYREF t where t.orgid="+thisOrg+" and t.relation_orgid="+relOrg;
		try {
			log.debug("��ѯ��ǰ�����Ƿ��ע��ָ��������"+sql);
			coreDAO.executeQuery(sql, "orgReltions", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 99;
	}
	
	/**
	 * ��ӹ�ע
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int addAttention(BizData rd,BizData sd) {
		
		Connection conn = coreDAO.getConnection();
		try {
			
			coreDAO.beginTrasct(conn);
			String relOrg = rd.getStringByDI("hrorganization", "orgid", 0);
			String thisOrg = sd.getString("orgid");
			// ��ѯ����ע�����Ƿ��ע�˵�ǰ����
			rd.add("TA_TRAVELAGENCYREF", "ORGID", relOrg);
			rd.add("TA_TRAVELAGENCYREF", "RELATION_ORGID", thisOrg);

			coreDAO.select("TA_TRAVELAGENCYREF", rd, true, conn);
			// ��ѯ�û����Ƿ��Ѿ�����ע�����ѱ���ע�����ʶflag=Y�������ص���ҵ��Ϣҳ�档
			int relRos = rd.getTableRowsCount("orgReltions");
			if (relRos > 0)
				rd.add("flag", "Y");
			else {
				// ��ע�����ص��ɹ�ҳ��
				int rows = rd.getTableRowsCount("TA_TRAVELAGENCYREFs");
				if (rows > 0){
					rd.add("TA_TRAVELAGENCYREF", "STATE", "Y");
					rd.addAttr("TA_TRAVELAGENCYREF", "ORGID", 0, "oldValue", relOrg);
					rd.addAttr("TA_TRAVELAGENCYREF", "RELATION_ORGID", 0, "oldValue", thisOrg);
					coreDAO.update("TA_TRAVELAGENCYREF", rd, conn);
				}
				else
					rd.add("TA_TRAVELAGENCYREF", "STATE", "N");
				rd.add("TA_TRAVELAGENCYREF", "ORGID", thisOrg);
				rd.add("TA_TRAVELAGENCYREF", "RELATION_ORGID", relOrg);
				coreDAO.insert("TA_TRAVELAGENCYREF", rd, conn);
				rd.add("hrorganization", "orgid", relOrg);
				rd.add("flag", "N");
			}
			coreDAO.commitTrasct(conn);
		}catch (SQLException e){
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				if(null != conn)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 999;
	}
	
	/**
	 * ȡ����ע
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int cancelAttention(BizData rd,BizData sd){
		
		String relOrg = rd.getStringByDI("hrorganization", "orgid", 0);
		String thisOrg = sd.getString("orgid");
		//1����ѯ����ע�����Ƿ��ע���Լ�������ǣ������ô˼�¼״̬ΪN
		rd.add("TA_TRAVELAGENCYREF", "ORGID", relOrg);
		rd.add("TA_TRAVELAGENCYREF", "RELATION_ORGID", thisOrg);
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			int rwos = coreDAO.select("TA_TRAVELAGENCYREF", rd, false, conn);
			if(rwos > 0){
				rd.remove("TA_TRAVELAGENCYREF");
				rd.add("TA_TRAVELAGENCYREF", "STATE", "N");
				rd.addAttr("TA_TRAVELAGENCYREF", "ORGID", 0, "oldValue", relOrg);
				rd.addAttr("TA_TRAVELAGENCYREF", "RELATION_ORGID", 0, "oldValue", thisOrg);
				coreDAO.update("TA_TRAVELAGENCYREF", rd, conn);
			}
			rd.remove("TA_TRAVELAGENCYREF");
			rd.add("TA_TRAVELAGENCYREF", "ORGID", thisOrg);
			rd.add("TA_TRAVELAGENCYREF", "RELATION_ORGID", relOrg);
			coreDAO.deleteEntity("TA_TRAVELAGENCYREF", rd, conn);
			coreDAO.commitTrasct(conn);
		} catch (SQLException e){
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally{
			try {
				if(null != conn)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 9;
	}
}
