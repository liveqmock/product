<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%@page import="com.dream.bizsdk.common.database.datadict.DataDict"%>
<%@page import="com.dream.bizsdk.common.database.datadict.DBTableDef"%>
<%@page import="com.dream.bizsdk.common.database.datadict.DBColumnDef"%>
<%@page import="java.util.HashMap"%>

<%@include file="../common.jsp"%>
<%
  String[] rowIDs = null;
  String daoName=request.getParameter("daoName");
  String tableName=request.getParameter("tableName");
  String roleID=request.getParameter("roleID");
  if(roleID==null){
  		roleID="";
  }
  String update = request.getParameter("update"); 
  HashMap dicts=(HashMap)application.getAttribute(SysVar.APP_SDCS);
  dc=(DataDict)dicts.get(daoName);
  DBTableDef tabDef = dc.getTableDef(tableName);
  DBColumnDef[] colDefs=tabDef.getColumns();
  int count =colDefs.length;
%>
<html>
<head>
<title>角色数据权限设定</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" href="../../css/style.css" type="text/css">
<link rel="stylesheet" href="../../css/menu.css" type="text/css">    
<script src="../../js/forms.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
	function query(){
		var frm = document.forms[0];
		frm.action="queryRoleDataPrivil.";
		frm.submit();
	}		
-->
</script>
</head>

<body>
<form action="saveRoleDataPrivil." name="ui" method="post">
<input type="hidden" name="daoName" value="<%=daoName%>">
<input type="hidden" name="tableName" value="<%=tableName%>">
<p>&nbsp;</p>
<p>角色<select name="roleID">
 	<option value="" <%="".compareTo(roleID)==0?"selected":""%>>请选择...</option>
  <%rowIDs=rd.getRowIDs("DRMRoles");
    for(int i=0;i<rowIDs.length;i++){%>
    <option value="<%=rd.getString("DRMRoles","roleID",rowIDs[i])%>" <%=rd.getString("DRMRoles","roleID",rowIDs[i]).compareTo(roleID)==0?"selected":""%>><%=rd.getString("DRMRoles","roleName",rowIDs[i])%></option>
  <%}%>
  </select>对表&nbsp;<%=tableName%>&nbsp;的数据权限<input type="button" name="btnQuery" value="查询" onclick="javascript:query();" class="button"></p>
1. 行权限
<table width="100%" border="0" class="hci" cellspacing="1">
  <tr class="content"> 
    <td width="20%">限定语句</td>
    <td width="80%"> 
      <input type="text" name="DRMRowPrivil/subWhere" size="40" value="<%=rd.getString("DRMRowPrivils","subWhere","0")%>">
    </td>
  </tr>
</table>
2. 列权限
<table width="100%" border="0" class="hci" cellspacing="1">
  <tr class="head"> 
    <td width="23%">字段名</td>
    <td width="29%">字段显示名称</td>
    <td width="48%">访问权限</td>
  </tr><%for(int i=0;i<count;i++){%>
  <tr class="content"> 
    <td width="23%"><%=colDefs[i].getName()%></td>
    <td width="29%"><%=colDefs[i].getDisplayName()!=null?colDefs[i].getDisplayName():""%></td>
    <td width="48%"> 
      <input type="radio" name="<%="DRMColPrivils/privil["+colDefs[i].getName()+"]"%>" value="N" <%=rd.getString("DRMColPrivil","privil",colDefs[i].getName()).compareTo("N")==0?"checked":""%>>隐藏&nbsp;&nbsp;<input type="radio" name="<%="DRMColPrivils/privil["+colDefs[i].getName()+"]"%>" value="R" <%=rd.getString("DRMColPrivil","privil",colDefs[i].getName()).compareTo("R")==0?"checked":""%>>只读&nbsp;&nbsp;<input type="radio" name="<%="DRMColPrivils/privil["+colDefs[i].getName()+"]"%>" value="W" <%=rd.getString("DRMColPrivil","privil",colDefs[i].getName()).compareTo("W")==0?"checked":""%>>完全</td>
  </tr><%}%>
</table>
<input type="submit" name="btnSubmit" value="提交" class="button">&nbsp;<input type="reset" name="btnSubmit" value="重置" class="button">
</form>
</body>
</html>
