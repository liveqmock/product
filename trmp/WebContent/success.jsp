<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%
	String contextPath=request.getContextPath();
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
	<title>操作成功</title>
	<link rel="stylesheet" href="<%=contextPath%>/css/style.css" type="text/css">
</head>
<body>
	  <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" >
	   <tr>
	    <td align="center" valign="middle" bordercolor="#000000"> 
		<table width="413" height="193" cellpadding="0" cellspacing="0" border="0" >			
	        	<tr bgcolor="#6699cc" bordercolor="#CCCCCC"> 
	          		<td align="center" valign="middle" height="25" colspan="2"><font size="3" color="#FFFFFF"><b>您的请求已经成功执行完毕！</b></font></td>
			</tr>
			<tr bgcolor="#FFCC99"> 
				<td align="center" colspan="2" height="24" ><A href="javascript:history.go(-1);">返回</A></td>
			</tr>
		</table>
	    </td>
	   </tr>
	  </table>
</body>
</html>