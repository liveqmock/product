<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>
<%@page import="java.util.Vector"%>
<%
	// ҳ�� ���� ; ���/�޸�ҳ��
	String dispMode = rd.getString("dispMode");
	String roleid = "";
	
	// �Ǵ�����ɫ ����ȡ��ɫID
	if(!dispMode.equals("Create"))
		roleid = rd.getString("DRMRole","roleID",0);

	// ȡ����ID
	String orgid = sd.getString("orgid");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<title>�޸Ľ�ɫ</title>
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
		alert("�Բ���,��ɫ���Ʋ���Ϊ��.");
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

<div id="lable"><span>�޸Ľ�ɫ</span></div>

<div id="list-table">
  <table class="add-table">
	<tr>
	  <td width="10%">��ɫ���ƣ�</td>
	  <td width="10%">
	    <input type ="text" id="roleName" name ="DRMRole/roleName" value="<%=rd.getString("DRMRole","roleName",0)%>">
	  	<input type="hidden" name="drmrole/orgid" value="<%=orgid %>"/>
	  	<input type ="hidden" name ="DRMRole/roleID" value="<%=rd.getString("DRMRole","roleID",0)%>">
	  </td>
	  <td width="60%" align="left"><input type="button" id="button" value=" ��  �� " onclick="validateForm();"/></td>
	</tr>
  </table>
</div>
<div id="lable"><span>�ý�ɫӵ�еĹ���</span></div>
<div id="list-table">
<table width="100%" class="datas">
    <tr id="first-tr"> 
      <td width="10%"> 
        <input type="checkbox" name="checkbox" value="checkbox">ȫѡ</td>
      <td width="35%"> 
        <div align="center">��������</div>
      </td>
      <td width="55%"> 
        <div align="center">˵��</div>
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
			&nbsp;<input type="button" id="button" name="return" value="����" onClick="javascript:history.go(-1);">
    		<input type="button" id="button" name="btnUpdateMenu" value="ά���˵�" onClick="queryRoleMenuForUpdate();">
		</td>
	</tr>	
  </table>
</div>
</form>	
</body>
</html>