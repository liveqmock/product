<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common.jsp"%>
<%
	String contextPath = request.getContextPath();
	String daoName=request.getParameter("daoName");
	String action = request.getParameter("action");
%>
<html>
<head>
<title>数据字典管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<link rel="stylesheet" href="../../css/style.css" type="text/css">
	<link rel="stylesheet" href="../../css/menu.css" type="text/css">
	<script src="/js/forms.js" type="text/javascript"></script>
</head>
<body>
  <jsp:include page="/jsp/head.jsp"/>
  <menu:showBar height="23" width="100%"/>
  <menu:show/>
  <div>&nbsp;</div>
  <div align="center">当前DAO组件名称：<%=daoName%>&nbsp; <a href="makeDict.?daoName=<%=daoName%>" target="_top" title="如果您增加了新的表，请选择此操作">重新创建</a>&nbsp;<a href="updateDict.?daoName=<%=daoName%>" target="_top" title="如果您仅仅更改了表的结构，则可以选择此操作">刷新</a>&nbsp;<a target="list" href="queryAllTables.?daoName=<%=daoName%>" title="在自动生成JSP之前，需要设置表的自定义信息">设置自定义信息</a>&nbsp;<a target="list" href="queryAllTables.?action=jspgen&daoName=<%=daoName%>" title="为指定的表生成增加记录，显示记录，修改记录，列表记录的JSP">生成JSP</a>&nbsp;<a target="_top" href="queryAllTablesInDAO.?daoName=<%=daoName%>"" title="为指定表中的记录生成insert SQL脚本">生成SQL脚本</a>&nbsp;<a target="list" href="queryAllTables.?daoName=<%=daoName%>&action=dataPrivil" title="为每个表设置角色的数据权限">设置数据权限</a></div>
  <iframe name="list" src="about:blank" align="left" width="25%" height="80%" frameborder="0"/>
  <iframe name="main" src="about:blank" align="right" width="75%" height="80%" frameborder="0"/>
</body>
</html>
