<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@include file="../common.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title><%=sd.getString("simpleName") %>--天游资源管理平台(TRMP)</title>
<style>
body {margin:0 auto; padding:0; border:0;}
td { text-align:center; background-color:#D0EAFB;}
a { color:#004C7E; text-decoration:none;}
a:hover { color:#FFF; text-decoration:underline;}
.style1 { font:12px "宋体"; color:#004C7E; }
</style>
<link rel="stylesheet" type="text/css" href="style/main.css" />
</head>
<body>
<table width="166" height="100%" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" valign="top">
      <table width="162" height="100%" cellpadding="0" cellspacing="0">
        <tr>
          <td background="images/list_top.gif" height="28"></td>
        </tr>
        
        <tr>
          <td height="25" background="images/list_task.jpg"> <span class="style1">菜单管理</span> </td>
        </tr>
		<tr><td height="150">
		<iframe name="v_menu" src="<%=request.getContextPath() %>/frame/mat/getMenu.?menuLevel=1" width="100%" height="700" frameborder="0"></iframe>
		</td></tr>
		<tr><td>&nbsp;</td></tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
