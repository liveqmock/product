package com.dwr;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.blc.BLContext;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.databus.BizData;
import com.dwr.bean.AjaxObjBean;

public class PYAjaxService{

	private final Map<Integer, AjaxObjBean> largeCrowd;
	
	/**
	 * ��־��ӡ����
	 */
	private Logger log = null;
	
	public PYAjaxService() 
	{
	     largeCrowd = new HashMap<Integer, AjaxObjBean>();
	     //this.entityName="TA_SCENERY_POINT";
	        
	     // ������־
		 log = Logger.getLogger(PYAjaxService.class);
	}

	public List<AjaxObjBean> plusExecute(String filter) {
		
		if("".equals(filter)){
			return new ArrayList<AjaxObjBean>();
		}
		DAO coreDAO = getDao();
		HttpSession session = getSession();
		BizData data = (BizData)session.getAttribute("SessionData");
		data.remove("rsScenery");
		String sql = "select s.CITY_ID,s.SCENERY_ID,s.CMPNY_NAME,s.CHIEF_NAME,s.CHIEF_MOBILE \n" +
				"from ta_scenery_point s where s.orgid="+data.getString("orgid") +
				" and s.CMPNY_NAME like '%"+filter+"%'";
		try {
			coreDAO.executeQuery(sql, "rsScenery", data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(int i=0;i<data.getTableRowsCount("rsScenery");i++){
			
			int cityID =  data.getInt("rsScenery", "CITY_ID", i);
			int sceneryID =  data.getInt("rsScenery", "SCENERY_ID", i);
			String cmpnyName =  data.getStringByDI("rsScenery", "cmpny_name", i);
			String chiefName =  data.getStringByDI("rsScenery", "CHIEF_NAME", i);
			String chiefPhone =  data.getStringByDI("rsScenery", "CHIEF_MOBILE", i);

	        AjaxObjBean bean = new AjaxObjBean();
	        bean.setsID(sceneryID);
	        bean.setCity(cityID);
	        bean.setsName(cmpnyName);
	        bean.setLinkman(chiefName);
	        bean.setLinkmanTel(chiefPhone);
	        largeCrowd.put(sceneryID, bean);
		}
		List<AjaxObjBean> list = new ArrayList<AjaxObjBean>();
        Pattern regex = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
        for (AjaxObjBean scenery : largeCrowd.values()) {
            if (regex.matcher(scenery.getsName()).find()) {
            	list.add(scenery);
            }
        }
        return list;

	}
	
	/**
	 * ���β�ѯ
	 * @param filter
	 * @return
	 */
	public List<AjaxObjBean> guideExecute(String filter) {
		
		if("".equals(filter)){
			return new ArrayList<AjaxObjBean>();
		}
		HttpSession session = getSession();
		BizData data = (BizData)session.getAttribute("SessionData");
		data.remove("rsGuide");
		DAO coreDAO = getDao();
		String sql = "select u.username,u.userno,u.documentno dyzh,u.mobieltel sjhm \n" +
				"from drmuser u,drmuserrole r\n" +
				"where u.userid=r.userid and r.roleid='2' \n" +
				"and u.orgid="+ data.getString("orgid") +
				"and u.username like '%"+filter+"%'";
		try {
			coreDAO.executeQuery(sql, "rsGuide", data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(int i=0;i<data.getTableRowsCount("rsGuide");i++){
			
			int sID =  data.getInt("rsGuide", "userno", i);
			String sName =  data.getStringByDI("rsGuide", "username", i);
			String sGuideCode =  data.getStringByDI("rsGuide", "dyzh", i);
			String sGuideTel =  data.getStringByDI("rsGuide", "sjhm", i);

	        AjaxObjBean bean = new AjaxObjBean();
	        bean.setsID(sID);
	        bean.setsName(sName);
	        bean.setGuideCode(sGuideCode);
	        bean.setLinkmanTel(sGuideTel);
	        largeCrowd.put(sID, bean);
		}
		List<AjaxObjBean> list = new ArrayList<AjaxObjBean>();
        Pattern regex = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
        for (AjaxObjBean bean : largeCrowd.values()) {
            if (regex.matcher(bean.getsName()).find()) {
            	list.add(bean);
            }
        }
        return list;
	}
	
	/**
	 * �Ƶ��ѯ
	 * @param filter
	 * @return
	 */
	public List<AjaxObjBean> hotelExecute(String filter) {
		
		if("".equals(filter)){
			return new ArrayList<AjaxObjBean>();
		}
		
		DAO coreDAO = getDao();
		HttpSession session = getSession();
		BizData data = (BizData)session.getAttribute("SessionData");
		data.remove("rsHotel");
		String sql = "select t.hotel_id,t.area_id,t.hotel_level,t.hotel_name,t.hotel_bussiness,t.hotel_bussiness_tel,h.name,d.dmsm1 \n" +
					 " from ta_hotel t ,xzqh h,dmsm d \n" +
					 "where t.area_id=h.id(+) \n" +
					 "and d.dmz=t.hotel_level \n" +
					 "and d.dmlb=6 \n" +
					 "and t.orgid= " + data.getString("orgid") +
					 " and t.hotel_name like '%"+filter+"%'";
		try {
			coreDAO.executeQuery(sql, "rsHotel", data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(int i=0;i<data.getTableRowsCount("rsHotel");i++){
			
			int level = data.getInt("rsHotel", "hotel_level", i);
			int hotelId = data.getInt("rsHotel", "hotel_id", i);
			int areaId = data.getInt("rsHotel", "area_id", i);
			String areaName = data.getStringByDI("rsHotel", "name", i);
			String levelName = data.getStringByDI("rsHotel", "dmsm1", i);
			String cmpnyName =  data.getStringByDI("rsHotel", "hotel_name", i);
			String chiefName =  data.getStringByDI("rsHotel", "hotel_bussiness", i);
			String chiefPhone =  data.getStringByDI("rsHotel", "hotel_bussiness_tel", i);
			
	        AjaxObjBean bean = new AjaxObjBean();
	        bean.setsID(hotelId);//�Ƶ�ID
	        bean.setArea(level);//�Ƶ��Ǽ�
	        bean.setAreaName(levelName);//�Ǽ�����
	        bean.setCity(areaId);//����ID
	        bean.setCsName(areaName);
	        bean.setsName(cmpnyName);//�Ƶ�����
	        bean.setLinkman(chiefName);//��ϵ��
	        bean.setLinkmanTel(chiefPhone);//��ϵ�绰
	        largeCrowd.put(hotelId, bean);
		}
		List<AjaxObjBean> list = new ArrayList<AjaxObjBean>();
        Pattern regex = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
        for (AjaxObjBean bean : largeCrowd.values()) {
            if (regex.matcher(bean.getsName()).find()) {
            	list.add(bean);
            }
        }
        return list;
	}
	
	/**
	 * �����ѯ
	 * @param filter
	 * @return
	 */
	public List<AjaxObjBean> sceneryExecute(String filter) {
		
		if("".equals(filter)){
			return new ArrayList<AjaxObjBean>();
		}
		DAO coreDAO = getDao();
		HttpSession session = getSession();
		BizData data = (BizData)session.getAttribute("SessionData");
		data.remove("rsScenery");
		String sql = "select a.*,b.name sfName from ( \n" +
					 "select s.scenery_id,s.cmpny_name,x.name cityName,s.city_id,x.pid sfid,\n" +
					 "s.CHIEF_NAME,s.CHIEF_MOBILE\n" +
					 "from ta_scenery_point s,xzqh x\n" +
					 "where s.city_id=x.id(+)\n" +
					 "and s.orgid=" + data.getString("orgid") +
					 ") a,(\n" +
					 "select x.id,x.name from xzqh x\n" +
					 ") b\n" +
					 "where a.sfid=b.id(+)\n" +
					 "and a.cmpny_name like '%"+filter+"%'";
		try {
			coreDAO.executeQuery(sql, "rsScenery", data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(int i=0;i<data.getTableRowsCount("rsScenery");i++){
			
	        AjaxObjBean bean = new AjaxObjBean();
	        bean.setsID(data.getInt("rsScenery", "scenery_id", i));
	        bean.setsName(data.getString("rsScenery", "cmpny_name", i));
	        bean.setProvince(data.getInt("rsScenery", "sfid", i));
	        bean.setCity(data.getInt("rsScenery", "city_id", i));
	        bean.setSfName(data.getString("rsScenery", "sfname", i));
	        bean.setCsName(data.getString("rsScenery", "cityname", i));
	        bean.setLinkman(data.getString("rsScenery", "CHIEF_NAME", i));
	        bean.setLinkmanTel(data.getString("rsScenery", "CHIEF_MOBILE", i));
	        largeCrowd.put(data.getInt("rsScenery", "scenery_id", i), bean);
		}
		List<AjaxObjBean> list = new ArrayList<AjaxObjBean>();
        Pattern regex = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
        for (AjaxObjBean bean : largeCrowd.values()) {
            if (regex.matcher(bean.getsName()).find()) {
            	list.add(bean);
            }
        }
        return list;
	}
	
	/**
	 * �����Զ�����
	 * 
	 * @param condition �ؼ���
	 * @return ƥ�����ƽ��
	 */
	public List<AjaxObjBean> carFuzzySearch(String condition)
	{
		// �Կ�ֵ����
		if("".equals(condition))
		{
			return new ArrayList<AjaxObjBean>();
			
		}
		
		//  ��ȡDAO����
		DAO coreDAO = getDao();
		
		BizData rd = new BizData();
		
		HttpSession session = getSession();
		BizData sd = (BizData)session.getAttribute("SessionData");
		sd.remove("TA_CAR_TEAM");
		// ��ѯ���
		/*String sql = "select distinct(t.cmpny_name) from TA_CAR_TEAM  t,TA_CAR  c where t.team_id = " +
					"c.team_id and t.cmpny_name like '%"+ condition +"%'";*/
		String sql = "select t.team_id,t.city_id,t.cmpny_name from TA_CAR_TEAM t where t.orgid = '" + sd.getString("orgid") + "' and t.cmpny_name like '%"+condition+"%'";
		
		// ��ȡ���ݿ�����
		Connection conn =  coreDAO.getConnection();
		
		try
        {
			// ��������
	        coreDAO.beginTrasct(conn);
	      
	        // ������ID����ѯ�Ƶ�-����������
	        coreDAO.executeQuery(sql, "TA_CAR_TEAM", rd, conn);
	        
			// �ύ����
			coreDAO.commitTrasct(conn);
        }
        catch (SQLException e)
        {
	        log.error(e.getMessage(), e);
	        
	        return new ArrayList<AjaxObjBean>();
        }
        
        // ������ϴ�������ص��������
        for(int i=0;i<rd.getTableRowsCount("TA_CAR_TEAM");i++) {
	        AjaxObjBean bean = new AjaxObjBean();
	        // ����
	        String cityId = rd.getString("TA_CAR_TEAM", "city_id", i);
	        bean.setsName(rd.getString("TA_CAR_TEAM", "cmpny_name", i));
	        bean.setCity("".equals(cityId)?-1:Integer.parseInt(cityId));
	        bean.setsID(Integer.parseInt(rd.getString("TA_CAR_TEAM", "team_id", i)));
	        largeCrowd.put(i, bean);
		}
		List<AjaxObjBean> list = new ArrayList<AjaxObjBean>();
		
        Pattern regex = Pattern.compile(condition, Pattern.CASE_INSENSITIVE);
        
        // �ӽ������ƥ��ؼ���
        for (AjaxObjBean bean : largeCrowd.values())
        {
            if (regex.matcher(bean.getsName()).find())
            {
            	list.add(bean);
            }
        }
		
		return list;
	}
	
	/**�����Զ�����
	 * @param filter
	 * @return
	 */
	public List<AjaxObjBean> dinnerExecute(String filter) {
		
		if("".equals(filter)){
			return new ArrayList<AjaxObjBean>();
		}
		DAO coreDAO = getDao();
		
		HttpSession session = getSession();
		BizData data = (BizData)session.getAttribute("SessionData");
		data.remove("rsDinner");
		String sql = "select d.*,h.pid from ta_dining_room d ,xzqh h \n" +
					 "where d.city_id=h.id(+) \n" +
					 " and d.orgid=" + data.getString("orgid") +
					 " and d.CMPNY_NAME like '%"+filter+"%'";
		try {
			coreDAO.executeQuery(sql, "rsDinner", data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < data.getTableRowsCount("rsDinner"); i++){
			int province = data.getInt("rsDinner", "PID", i);
			int cityID =  data.getInt("rsDinner", "CITY_ID", i);
			int ctId =  data.getInt("rsDinner", "DINING_ROOM_ID", i);
			String cmpnyName =  data.getStringByDI("rsDinner", "CMPNY_NAME", i);
			String chiefName =  data.getStringByDI("rsDinner", "CHIEF_NAME", i);
			String chiefPhone =  data.getStringByDI("rsDinner", "CHIEF_MOBILE", i);

	        AjaxObjBean bean = new AjaxObjBean();
	        bean.setsID(ctId);
	        bean.setProvince(province);
	        bean.setCity(cityID);
	        bean.setsName(cmpnyName);
	        bean.setLinkman(chiefName);
	        bean.setLinkmanTel(chiefPhone);
	        largeCrowd.put(ctId, bean);
		}
		List<AjaxObjBean> list = new ArrayList<AjaxObjBean>();
        Pattern regex = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
        for (AjaxObjBean scenery : largeCrowd.values()) {
            if (regex.matcher(scenery.getsName()).find()) {
            	list.add(scenery);
            }
        }
        return list;

	}
	
	/**�ؽ����Զ�����
	 * @param filter
	 * @return
	 */
	public List<AjaxObjBean> travelExecute(String filter) {
		
		if("".equals(filter)){
			return new ArrayList<AjaxObjBean>();
		}
		DAO coreDAO = getDao();
		HttpSession session = getSession();
		BizData data = (BizData)session.getAttribute("SessionData");
		data.remove("rsTravel");
		String sql = "select * from (\n" +
					 "select t.city_id,t.travel_agc_id,t.cmpny_name,t.business_name,t.business_mobile,\n" +
					 "x.name cityName,x.pid\n" +
					 "from ta_travelagency t, xzqh x\n" +
					 "where t.city_id=x.id(+)\n" +
					 "and t.orgid=" + data.getString("orgid") +
					 ") a,(\n" +
					 "select x.id provinceid,x.name provincenam\n" +
					 "from xzqh x\n" +
					 ") b\n" +
					 "where a.pid=b.provinceid(+)\n" +
					 "and a.CMPNY_NAME like '%"+filter+"%'";
		try {
			coreDAO.executeQuery(sql, "rsTravel", data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < data.getTableRowsCount("rsTravel"); i++){
			
			int province = data.getInt("rsTravel", "PID", i);
			int cityID =  data.getInt("rsTravel", "CITY_ID", i);
			int id =  data.getInt("rsTravel", "travel_agc_id", i);

	        AjaxObjBean bean = new AjaxObjBean();
	        bean.setsID(id);
	        bean.setProvince(province);
	        bean.setSfName(data.getStringByDI("rsTravel", "provincenam", i));
	        bean.setCity(cityID);
	        bean.setCsName(data.getStringByDI("rsTravel", "cityname", i));
	        bean.setsName(data.getStringByDI("rsTravel", "CMPNY_NAME", i));
	        bean.setLinkman(data.getStringByDI("rsTravel", "CHIEF_NAME", i));
	        bean.setLinkmanTel(data.getStringByDI("rsTravel", "CHIEF_MOBILE", i));
	        largeCrowd.put(id, bean);
		}
		List<AjaxObjBean> list = new ArrayList<AjaxObjBean>();
        Pattern regex = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
        for (AjaxObjBean bean : largeCrowd.values()) {
            if (regex.matcher(bean.getsName()).find()) {
            	list.add(bean);
            }
        }
        return list;

	}
	

	/**Ʊ���Զ�����
	 * @param filter
	 * @return
	 */
	public List<AjaxObjBean> ticketExecute(String filter) {
		
		if("".equals(filter)){
			return new ArrayList<AjaxObjBean>();
		}
		
		HttpSession session = getSession();
		BizData sd = (BizData)session.getAttribute("SessionData");
		
		DAO coreDAO = getDao();
		BizData data = new BizData();
		String sql = "select t.* from ta_ticket t \n" +
					 "where t.orgid = " + sd.getString("orgid") +
					 " and t.cmpny_name like '%"+filter+"%'";
		try {
			coreDAO.executeQuery(sql, "rsShop", data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < data.getTableRowsCount("rsShop"); i++){
			
			int cityID =  data.getInt("rsShop", "CITY_ID", i);
			int id =  data.getInt("rsShop", "ticket_id", i);
			String cmpnyName =  data.getStringByDI("rsShop", "CMPNY_NAME", i);
			String chiefName =  data.getStringByDI("rsShop", "CHIEF_NAME", i);
			String chiefPhone =  data.getStringByDI("rsShop", "CHIEF_MOBILE", i);

	        AjaxObjBean bean = new AjaxObjBean();
	        bean.setsID(id);
	        bean.setCity(cityID);
	        bean.setsName(cmpnyName);
	        bean.setLinkman(chiefName);
	        bean.setLinkmanTel(chiefPhone);
	        largeCrowd.put(id, bean);
		}
		List<AjaxObjBean> list = new ArrayList<AjaxObjBean>();
        Pattern regex = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
        for (AjaxObjBean bean : largeCrowd.values()) {
            if (regex.matcher(bean.getsName()).find()) {
            	list.add(bean);
            }
        }
        return list;

	}
	
	/**�����Զ�����
	 * @param filter
	 * @return
	 */
	public List<AjaxObjBean> shopExecute(String filter) {
		
		if("".equals(filter)){
			return new ArrayList<AjaxObjBean>();
		}
		DAO coreDAO = getDao();
		HttpSession session = getSession();
		BizData data = (BizData)session.getAttribute("SessionData");
		data.remove("rsShop");
		String sql = "select t.* from ta_shoppoint t \n" +
					 "where t.orgid="+data.getString("orgid")+"\n" +
					 "and t.cmpny_name like '%"+filter+"%'";
		try {
			coreDAO.executeQuery(sql, "rsShop", data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < data.getTableRowsCount("rsShop"); i++){
			int cityID =  data.getInt("rsShop", "CITY_ID", i);
			int id =  data.getInt("rsShop", "shop_point_id", i);
			String cmpnyName =  data.getStringByDI("rsShop", "CMPNY_NAME", i);
			String chiefName =  data.getStringByDI("rsShop", "CHIEF_NAME", i);
			String chiefPhone =  data.getStringByDI("rsShop", "CHIEF_MOBILE", i);

	        AjaxObjBean bean = new AjaxObjBean();
	        bean.setsID(id);
	        bean.setCity(cityID);
	        bean.setsName(cmpnyName);
	        bean.setLinkman(chiefName);
	        bean.setLinkmanTel(chiefPhone);
	        largeCrowd.put(id, bean);
		}
		List<AjaxObjBean> list = new ArrayList<AjaxObjBean>();
        Pattern regex = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
        for (AjaxObjBean bean : largeCrowd.values()) {
            if (regex.matcher(bean.getsName()).find()) {
            	list.add(bean);
            }
        }
        return list;

	}
	
	/**
	 * ��ȡDAO����
	 * 
	 * @return DAO
	 */
    private DAO getDao()
	{
    	HttpSession session = getSession();
		BLContext ctx = (BLContext)session.getServletContext().getAttribute(SysVar.APP_BLCONTEXT);
		
		return ctx.getDAO("core");
	}
    
    private HttpSession getSession(){
    	WebContext webCtx = WebContextFactory.get(); 
		HttpServletRequest req = webCtx.getHttpServletRequest();
		HttpSession session = req.getSession();
		return session;
    }
}
