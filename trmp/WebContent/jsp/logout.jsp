<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%
	//remove the SessionData attribute;
	session.removeAttribute(SysVar.SS_DATA);
	String contextPath=request.getContextPath();
	//the LOGINPAGE maybe contains macro variable %ContextPath%;
	String redirectURL=SysVar.LOGINPAGE.replaceAll("%ContextPath%",contextPath);
	//invalidate so that the current user have to relogin to system.
	session.invalidate();	
	response.sendRedirect(redirectURL);
%>