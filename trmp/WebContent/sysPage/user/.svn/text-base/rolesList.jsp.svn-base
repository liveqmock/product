<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %> 
<%

String dispMode = request.getParameter("dispMode");
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/selectAll.js"></script>
<title>角色管理</title>
<script type="text/javascript">

function deleteRoles(){
	RolesList.action="deleteRoles.";
	RolesList.submit();
}
function addRole(){
	RolesList.action="<%=request.getContextPath()%>/sysPage/user/initRoleInfo.?dispMode=Create";
	RolesList.submit();
}
function addRoles2User(){
	RolesList.action="addRoles2User.";
	RolesList.submit();
}
</script>
</head>
<body>
<form name="RolesList" method="post">
<div id="lable"><span>角色管理</span></div>
<div id="top-select">
		<div class="select-div" onclick="addRole();"><span id="add-icon"></span>
		 <span class="text">添加</span>
		</div>
		<div class="select-div" onclick="deleteRoles();"><span id="del-icon"></span> <span
			class="text">删除</span>
		</div>
	</div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td><input type="checkbox" id="checkboxTop" value=""></td>
			<td>角色名称</td>
			<td>操作</td>
		</tr>
		<%int rows=rd.getTableRowsCount("DRMRoles");
		  for(int i=0;i<rows;i++){
			  String isAdmin = rd.getString("DRMRoles","isadmin",String.valueOf(i));
			  String roleid = rd.getString("DRMRoles","ROLEID",String.valueOf(i));
		%>
		<tr>
			<td>
			  <%if(!isAdmin.equals("admin") && !roleid.equals("2")){ %>
			  <input type="checkbox" id="checkboxEle" name="<%="DRMRole/roleID["+i+"]"%>" 
					value="<%=rd.getString("DRMRoles","roleID",String.valueOf(i))%>">
			  <%} %>
			</td>
			<td><%=rd.getString("DRMRoles","roleName",String.valueOf(i))%></td>
			
			<td>
				<a href="queryRole.?DispMode=Update&DRMRole/roleID=<%=rd.getString("DRMRoles","roleID",String.valueOf(i)) %>&DRMRole/rolename=<%=rd.getString("DRMRoles","roleName",String.valueOf(i))%>">
					<img alt="编辑" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[修改]</a>
		<%
			String href = "#";
			if(!isAdmin.equals("admin") && !roleid.equals("2")){
				href = "deleteRoles.?DRMRole/roleid=" + roleid;
			}
		%>
			<%if(!isAdmin.equals("admin") && !roleid.equals("2")){ %>	
				-<a href="<%=href %>" onclick="return confirm('此操作将无法恢复,确定删除吗?')">
					<img alt="删除" src="<%=request.getContextPath()%>/images/del-icon.gif">&nbsp;[删除]
				</a>
			<%
			}%>
			</td>
		</tr>
		<%} %>
	</table>
</div>
</form>
</body>
</html>