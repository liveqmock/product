<%@page contentType="text/html;charset=GBK"%>
<%
	String to=request.getParameter("to");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>邮件发送成功</title>
	</head>
	<body border="0" cellpadding="0">
	  <p>邮件已经成功发送到：<%=to%></p>	
	</body>
</html>