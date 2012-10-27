<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%
  String cp = request.getContextPath();
  BizData sd = (BizData)session.getAttribute(SysVar.SS_DATA);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title></title>
<link rel="stylesheet" href="<%=cp%>/css/style.css" type="text/css">		
<link rel="stylesheet" href="<%=cp%>/css/menu.css" type="text/css">	
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr bgcolor="#4682B4" height="40">
   <td width="60%">&nbsp;</td>
   <td width="40%" align="right"><font color="white" size="2"><i>您好,<%=sd.getString("userID")%>!</i></font>&nbsp;&nbsp;<a href="<%=cp%>/jsp/logout.jsp"><font color="#ffff" size="2">退出</font></a></td>
  </tr>
</table>
</body>
</html>
