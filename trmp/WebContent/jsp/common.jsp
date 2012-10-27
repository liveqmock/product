<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%@page import="com.dream.bizsdk.common.database.datadict.DataDict"%>
<%@taglib prefix="menu" uri="/WEB-INF/tld/menu.tld"%>
<%
	//request data object
	BizData rd = (BizData)request.getAttribute(SysVar.REQ_DATA);
	//the session data object of the current session;
	BizData sd = (BizData)session.getAttribute(SysVar.SS_DATA);
	// if session data is null,redirect current response to the LOGINPAGE;
//DEBUG
if(sd==null){
	sd = new BizData();
}
	if(sd==null){
	        String loginPage=SysVar.LOGINPAGE.replaceAll(SysVar.CONTEXT_PATH,request.getContextPath());
		response.sendRedirect(loginPage);
	}
//debug
if(rd==null){
	rd= new BizData();	
}
	//business dictionary object of the application( a db table);
	BizData bdc = (BizData)application.getAttribute(SysVar.APP_BDC);
	//the data dict object in the core data object;
	DataDict dc= (DataDict)application.getAttribute(SysVar.APP_SDC);	
%>