package com.dwr;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.blc.BLContext;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.databus.BizData;
import com.dwr.bean.AjaxObjBean;

public class DeptAjaxService {
	
	private Logger log = null;
	private final Map<Integer, AjaxObjBean> largeCrowd;
	public DeptAjaxService(){
	     largeCrowd = new HashMap<Integer, AjaxObjBean>();
	     // ������־
		 log = Logger.getLogger(PYAjaxService.class);
	}
	private DAO getDao(){
		WebContext webCtx = WebContextFactory.get(); 
		
		HttpServletRequest req = webCtx.getHttpServletRequest();
		
		HttpSession session = req.getSession();
		
		BLContext ctx = (BLContext)session.getServletContext().getAttribute(SysVar.APP_BLCONTEXT);
		
		return ctx.getDAO("core");
	}
	
	 /**
     * ɾ�����ż�����������
     * @param deptids;
     * @return
     */
    public boolean deleteDept(String deptids) throws SQLException {
    	boolean ret = false;
    	deptids = deptids.replaceAll(",", "','");
    	DAO coreDAO = getDao();
    	BizData data = new BizData();
		String sql = "delete from hrdepartment t where t.deptid in ('"+deptids+"')";
		// ��ȡ���ݿ�����
		Connection conn =  coreDAO.getConnection();
		try{
			// ��������
	        coreDAO.beginTrasct(conn);
	        coreDAO.executeUpdate(sql);
			// �ύ����
			coreDAO.commitTrasct(conn);
			ret = true;
        }catch (SQLException e){
	        log.error(e.getMessage(), e);
        }
		return ret;
    }
}
