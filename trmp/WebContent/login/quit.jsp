<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="com.dream.bizsdk.common.SysError"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%
	String contextPath = request.getContextPath();

    // ���SESSIONֵ
	session.invalidate();
		
    // �ض�����ҳ
	response.sendRedirect(contextPath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>
<body>
</body>
</html>