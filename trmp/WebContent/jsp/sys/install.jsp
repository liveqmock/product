<%@page contentType="text/html;charset=GBK"%>
<%
	String contextPath = request.getContextPath();
%>
<html>
	<head>
		<title>安装应用程序的数据库</title>
	</head>
	<body>		
		<center><p>当前应用程序的根路径为<font color="red"><%=contextPath%></font>;您现在可以<a href="<%=contextPath%>/jsp/sys/install.">安装该应用程序的数据库</a>.</p></center>
	</body>
</html>