<%@include file="../common.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<html>
<head>
<link rel="stylesheet" href="../../css/style.css" type="text/css">
<link rel="stylesheet" href="../../css/menu.css" type="text/css">
<title>角色菜单更改</title>
</head>
<body>
<jsp:include page="/jsp/head.jsp"/>
<menu:menuBar x="12" y="16" height="23" width="100%"/>
<form name="roleMenu" method="post" action="updateRoleMenu.">
  <input type="hidden" name="DRMRole/roleID" value="<%=rd.getString("DRMRole","roleID","0")%>" >
  <p>当前角色编号为:<%=rd.getString("DRMRole","roleID","0")%>;该角色的菜单如下,未被选中的菜单项表示该菜单尚未被赋给当前角色.</p>    
  <%=rd.getString("roleMenus")%>  
  <p> 
    <input type="submit" name="btnSubmit" value="保存" >
    <input type="reset" name="btnReset" value="重置" >
  </p>
</form>
<menu:menuItems/>
</body>
</html>	