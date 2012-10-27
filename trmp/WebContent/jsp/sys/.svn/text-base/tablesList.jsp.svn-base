<%@page contentType="text/html;charset=gbk"%>
<%@include file="/jsp/common.jsp"%>
<%
	String daoName= request.getParameter("daoName");
	String action=request.getParameter("action");
	String url=null;
	if(action!=null && action.compareToIgnoreCase("jspGen")==0){
		url="genJSP.jsp?";
	}else if(action!=null && action.compareToIgnoreCase("dataPrivil")==0){
		url="queryRoleDataPrivil.?roleID=&";
	}
	else{
		url="queryTable.?";
	}
%>
<head>
<title>数据实体列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" href="../../css/style.css" type="text/css">
<script src="../../js/forms.js" type="text/javascript"></script>
</head>

<body>
<p><b>数据字典中的表定义：</b></p>
<form name="list" action="deleteTableFromDict." method="post">
<input type="hidden" name="daoName" value="<%=daoName%>">
<ol>
<%
   int rows=rd.getTableRowsCount("DRMTables");   
  for(int i=0;i<rows;i++){
  	String tableName=rd.getStringByDI("DRMTables","tableName",i);%>
  	<li><input type="checkbox" name="tableName[]" value="<%=tableName%>"><A target="main" href="<%=url%>tableName=<%=tableName%>&daoName=<%=daoName%>"><%=tableName%></A></li>
<%}%>
</ol>
<a href="javascript:list.submit();">删除该定义</a>
</form>
</body>