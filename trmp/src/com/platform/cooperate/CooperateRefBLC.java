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
			log.debug("查询当前用户的所有合作社："+sql.toString());
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
		//行政区划，根据一个行政区划取所有子行政区划
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
			log.debug("查询所有旅行社："+sql.toString());
			coreDAO.executeQueryPage(sql.toString(), "rsAllOrgs", pageNO, pageSize, rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 98;
	}
	
	/**
	 * 查询当前机构是否关注了指定机构信息
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int queryRelation(BizData rd,BizData sd){
		
		String thisOrg = sd.getString("orgid");
		String relOrg = rd.getStringByDI("hrorganization", "orgid", 0);
		String sql = "select * from TA_TRAVELAGENCYREF t where t.orgid="+thisOrg+" and t.relation_orgid="+relOrg;
		try {
			log.debug("查询当前机构是否关注了指定机构："+sql);
			coreDAO.executeQuery(sql, "orgReltions", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 99;
	}
	
	/**
	 * 添加关注
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
			// 查询被关注机构是否关注了当前机构
			rd.add("TA_TRAVELAGENCYREF", "ORGID", relOrg);
			rd.add("TA_TRAVELAGENCYREF", "RELATION_ORGID", thisOrg);

			coreDAO.select("TA_TRAVELAGENCYREF", rd, true, conn);
			// 查询该机构是否已经被关注，如已被关注，则标识flag=Y，还返回到企业信息页面。
			int relRos = rd.getTableRowsCount("orgReltions");
			if (relRos > 0)
				rd.add("flag", "Y");
			else {
				// 关注，返回到成功页面
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
	 * 取消关注
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int cancelAttention(BizData rd,BizData sd){
		
		String relOrg = rd.getStringByDI("hrorganization", "orgid", 0);
		String thisOrg = sd.getString("orgid");
		//1，查询被关注机构是否关注了自己，如果是，则设置此记录状态为N
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
