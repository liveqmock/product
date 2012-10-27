<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<title>角色菜单更改</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<style>
* { margin:auto;}
</style>
<script type="text/javascript">
function gourl(){
	var url = "queryRoles.";
	location.href = url;
}

</script>
</head>
<body>
<form name="roleMenu" method="post" action="updateRoleMenu.">
  <input type="hidden" name="DRMRole/roleID" value="<%=rd.getString("DRMRole","roleID","0")%>" >
  <input type="hidden" name="DRMRole/orgID" value="<%=rd.getString("DRMRole","orgID","0")%>" >
  <div id="lable"><span>当前角色编号为:<%=rd.getString("DRMRole","roleID","0")%>;该角色的菜单如下,未被选中的菜单项表示该菜单尚未被赋给当前角色.</span></div>   
  <%=rd.getString("roleMenus")%>  
  <p> 
    <input type="submit" id="button" name="btnSubmit" value="保 存" >
    <input type="reset" id="button" name="btnReset" value="重 置" >
    <input type="button" id="button" name="back" value="返 回" onclick="gourl()">
  </p>
</form>
</body>
</html>	