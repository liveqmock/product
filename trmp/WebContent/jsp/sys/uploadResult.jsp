<%@page contentType="text/html;charset=GBK"%>
<%@include file="/jsp/common.jsp"%>
<%
String uploadedFiles=request.getParameter("uploadedFiles");
String error=request.getParameter("error");
if(error!=null){%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>�ϴ��ļ�����</title>
	</head>
	<body>
		<p>�ϴ��ļ�ʱ����I/O����<%=error%></p>
	</body>
</html>
<%}else{%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>�ϴ��ļ��ɹ�</title>	
	</head>
	<body>
		<p>��<%=uploadedFiles%> ���ļ����ɹ��ϴ���</p>
	</body>
</html>
<%}%>