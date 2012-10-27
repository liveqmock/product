<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%
	//remove the SessionData attribute;
	BizData d =(BizData)session.getAttribute(SysVar.SS_DATA);
	String req = new String(request.getParameter("aa").getBytes("iso-8859-1"),"GBK");
%>
<%=req%>