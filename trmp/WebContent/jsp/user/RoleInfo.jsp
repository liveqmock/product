<%@include file="../common.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%
	String dispMode = request.getParameter("DispMode");
	if(dispMode==null){
		dispMode="N";
	}
%>
<html>
<head>
<link rel="stylesheet" href="../../css/style.css" type="text/css">
<link rel="stylesheet" href="../../css/menu.css" type="text/css">
<title>角色基本信息和角色的功能</title>
<script language="javascript">
<!--
	function deleteRoleFunctions(){
		RolesList.action="deleteFuncsFromRole.";
		RolesList.submit();
	}
	
	function queryRoleMenuForUpdate(){
		RolesList.action="queryRoleMenuForUpdate.";
		RolesList.submit();
	}

	function addRoleFunctions(){
		RolesList.action="queryFuncsNotAdded2Role.";
		RolesList.submit();
	}
	
	function validateForm(frm){
		if(frm.elements("DRMRole/roleID").value == "" || frm.elements("DRMRole/roleName").value == ""){
			alert("对不起,角色编号和角色名称不能为空.");
			return false;
		}else{
			return true;
		}
	}
	
//-->
</script>
</head>
<body>
<!-- show the menu bar-->
<jsp:include page="/jsp/head.jsp"/>
<menu:menuBar x="12" y="16" height="23" width="100%"/>

<form name="RolesList" method="post" action="<%=dispMode.compareToIgnoreCase("N")==0?"newRole.":"updateRole."%>" onsubmit="javascript:return validateForm(document.forms(0));">
<%if(dispMode.compareToIgnoreCase("N")!=0){%>
  <input type="hidden" name="DRMRole/roleID[0]@oldValue" value="<%=(rd==null)?"":rd.getString("DRMRole","roleID",0)%>">
<%}%>
  <p>该角色基本信息:</p>
 角色编号：
    <input type ="text" name ="DRMRole/roleID" value="<%=(rd==null)?"":rd.getString("DRMRole","roleID",0)%>" class="flat" <%=dispMode.compareToIgnoreCase("N")!=0?"readonly":""%> >
 角色名称：
    <input type ="text" name ="DRMRole/roleName" value="<%=(rd==null)?"":rd.getString("DRMRole","roleName",0)%>" class="flat">

	<input type ="hidden" name ="DRMRole/ORGID" value="<%if(sd.getString("userid").equals("admin")) out.print("-1"); else out.print(request.getParameter("orgid")); %>">
    <input type="submit" name="save" value="保存" class="button">
  <p>该角色拥有的功能:</p>
  <table width="100%" border="0" cellspacing="1" cellpadding="0" class="hci">
    <tr class="head"> 
      <td width="10%"> 
        <input type="checkbox" name="checkbox" value="checkbox" class="flat">
        全选</td>
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
  </table>
  
  <p> 
    <input type="button" name="delete" value="删除功能" onclick="javascript:deleteRoleFunctions();" class="button">
    <input type="button" name="btnAddFunction" value="添加功能" onClick="javascript:addRoleFunctions();" class="button">
    <input type="button" name="btnUpdateMenu" value="维护菜单" onClick="javascript:queryRoleMenuForUpdate();" class="button">
    <input type="button" name="return" value="返回" onClick="javascript:history.go(-1);" class="button">
  </p>
</form>

<!-- show the menu-->
<menu:menuItems/>
</body>
</html>	