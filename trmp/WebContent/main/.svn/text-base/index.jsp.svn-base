<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title><%if(sd != null){out.print(sd.getString("simpleName"));} %>--天游资源管理平台(TRMP)</title>
<style>
* { margin:0 auto; padding:0; border:0;}
</style>
</head>
<frameset rows="95,*,20" frameborder="0" border="0" framespacing="0">
  <frame src="top.jsp" name="top" scrolling="no" noresize="noresize" id="top" />
  <frame src="middel.jsp" name="center" id="center" scrolling="no"/>
  <frame src="bottom.jsp" name="bottom" id="bottom" scrolling="no"/>
</frameset>
</html>