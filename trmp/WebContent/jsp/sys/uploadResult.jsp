<%@page contentType="text/html;charset=GBK"%>
<%@include file="/jsp/common.jsp"%>
<%
String uploadedFiles=request.getParameter("uploadedFiles");
String error=request.getParameter("error");
if(error!=null){%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>上传文件错误</title>
	</head>
	<body>
		<p>上传文件时发生I/O错误：<%=error%></p>
	</body>
</html>
<%}else{%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>上传文件成功</title>	
	</head>
	<body>
		<p>共<%=uploadedFiles%> 个文件被成功上传。</p>
	</body>
</html>
<%}%>