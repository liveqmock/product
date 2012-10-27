<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>
<%@page import="java.util.Vector"%>
<%
	// 页面 类型 ; 添加/修改页面
	String dispMode = rd.getString("dispMode");
	String roleid = "";
	
	// 非创建角色 ，则取角色ID
	if(!dispMode.equals("Create"))
		roleid = rd.getString("DRMRole","roleID",0);

	// 取机构ID
	String orgid = sd.getString("orgid");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<title>修改角色</title>
<script type="text/javascript">
function deleteRoleFunctions(){
	document.RolesList.action="deleteFuncsFromRole.";
	document.RolesList.submit();
}

function queryRoleMenuForUpdate(){
	document.RolesList.action="queryRoleMenuForUpdate.";
	document.RolesList.submit();
}

function addRoleFunctions(){
	RolesList.action="queryFuncsNotAdded2Role.";
	RolesList.submit();
}

function validateForm(){
	if($("#roleName").val() == ''){
		alert("对不起,角色名称不能为空.");
		return false;
	}else{

		document.RolesList.submit();
	}
}
</script>
</head>
<body>
<form name="RolesList" method="post" action="updateRole.">

  <input type="hidden" name="DRMRole/roleID[0]@oldValue" value="<%=rd.getString("DRMRole","roleID",0)%>">
  <input type="hidden" name="DRMRole/orgid[0]@oldValue" value="<%=orgid %>">

<div id="lable"><span>修改角色</span></div>

<div id="list-table">
  <table class="add-table">
	<tr>
	  <td width="10%">角色名称：</td>
	  <td width="10%">
	    <input type ="text" id="roleName" name ="DRMRole/roleName" value="<%=rd.getString("DRMRole","roleName",0)%>">
	  	<input type="hidden" name="drmrole/orgid" value="<%=orgid %>"/>
	  	<input type ="hidden" name ="DRMRole/roleID" value="<%=rd.getString("DRMRole","roleID",0)%>">
	  </td>
	  <td width="60%" align="left"><input type="button" id="button" value=" 提  交 " onclick="validateForm();"/></td>
	</tr>
  </table>
</div>
<div id="lable"><span>该角色拥有的功能</span></div>
<div id="list-table">
<table width="100%" class="datas">
    <tr id="first-tr"> 
      <td width="10%"> 
        <input type="checkbox" name="checkbox" value="checkbox">全选</td>
      <td width="35%"> 
        <div align="center">功能名称</div>
      </td>
      <td width="55%"> 
        <div align="center">说明</div>
      </td>
    </tr>
    <%
	if(rd!= null){
	int rows=rd.getTableRowsCount("DRMRoleFunctions");
	  for(int i=0;i<rows;i++){%>
    <tr class="content"> 
      <td width="10%"> 
        <input type="checkbox" name="<%="DRMRoleFunction/funcNO["+i+"]"%>" value="<%=rd.getString("DRMRoleFunctions","funcNO",i)%>">
      </td>
      <td width="35%"><%=rd.getString("DRMRoleFunctions","funcNO",i)%>&nbsp;</td>
      <td width="55%"><%=rd.getString("DRMRoleFunctions","funcName",i)%>&nbsp;</td>
    </tr>
    <%}
	}%>
	<tr>
		<td colspan="3" align="left">
			&nbsp;<input type="button" id="button" name="return" value="返回" onClick="javascript:history.go(-1);">
    		<input type="button" id="button" name="btnUpdateMenu" value="维护菜单" onClick="queryRoleMenuForUpdate();">
		</td>
	</tr>	
  </table>
</div>
</form>	
</body>
</html>