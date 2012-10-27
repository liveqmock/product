<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>

<%
	//request data object
	BizData rd = (BizData) request.getAttribute(SysVar.REQ_DATA) ;
	//the session data object of the current session;
	BizData sd = (BizData) session.getAttribute(SysVar.SS_DATA);
	// if session data is null,redirect current response to the LOGINPAGE;
	//DEBUG

	if (sd == null) {
		String loginPage = SysVar.LOGINPAGE.replaceAll(
				SysVar.CONTEXT_PATH, request.getContextPath());
		response.sendRedirect(loginPage);
	}
	//debug
	if (rd == null) {
		rd = new BizData();
	}
%>
