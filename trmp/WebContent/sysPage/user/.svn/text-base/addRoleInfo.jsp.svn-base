<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<title>创建角色</title>
<script type="text/javascript">
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
<form name="RolesList" method="post" action="newRole.">
<div id="lable"><span>新增角色</span></div>

<div id="list-table">
  <table class="add-table">
	<tr>
	  <td width="10%">角色名称：</td>
	  <td width="10%">
	    <input type ="text" id="roleName" name ="DRMRole/roleName">
	    <input type="hidden" name="drmrole/orgid" value="<%=sd.getString("orgid") %>"/>
	  </td>
	  <td width="60%" align="left"><input type="button" id="button" value=" 提  交 " onclick="validateForm();"/></td>
	</tr>
  </table>
</div>
</form>	
</body>
</html>