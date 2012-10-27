package com.dwr;

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

public class CantonAjaxService {

	private Logger log = null;
	private final Map<Integer, AjaxObjBean> largeCrowd;
	public CantonAjaxService(){
		largeCrowd = new HashMap<Integer, AjaxObjBean>();
	     //this.entityName="TA_SCENERY_POINT";
	        
	     // ∆Ù∂Ø»’÷æ
		 log = Logger.getLogger(CantonAjaxService.class);
	}
	
	private DAO getDao()
	{
		WebContext webCtx = WebContextFactory.get(); 
		
		HttpServletRequest req = webCtx.getHttpServletRequest();
		
		HttpSession session = req.getSession();
		
		BLContext ctx = (BLContext)session.getServletContext().getAttribute(SysVar.APP_BLCONTEXT);
		
		return ctx.getDAO("core");
	}
	
	public List<AjaxObjBean> queryCanton(String filter) {
		
		if("".equals(filter)){
			return new ArrayList<AjaxObjBean>();
		}
		DAO coreDAO = getDao();
		BizData data = new BizData();
		String sql = "select x2.id city,x2.name cityname,a.id province, a.name provincename\n" +
				"from (\n" +
				"select x.id,x.pid,x.name from xzqh x where x.pid=0\n" +
				") a, xzqh x2\n" +
				"where a.id=x2.pid\n" +
				"and x2.name like %"+filter+"%'";
		try {
			coreDAO.executeQuery(sql, "rsCanton", data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(int i=0;i<data.getTableRowsCount("rsCanton");i++){
			
	        AjaxObjBean bean = new AjaxObjBean();
	        bean.setProvince(Integer.parseInt(data.getStringByDI("rsCanton", "province", i)));
	        bean.setSfName(data.getStringByDI("rsCanton", "provincename", i));
	        bean.setCity(Integer.parseInt(data.getStringByDI("rsCanton", "city", i)));
	        bean.setCsName(data.getStringByDI("rsCanton", "cityname", i));
	        largeCrowd.put(Integer.parseInt(data.getStringByDI("rsCanton", "city", i)), bean);
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
}
