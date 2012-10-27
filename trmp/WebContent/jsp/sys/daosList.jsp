<%@page contentType="text/html;charset=GBK"%>
<%@include file="/jsp/common.jsp"%>
<%
	String action=null;
	int rows=0;
	if(rd!=null){
		rows=rd.getArrayLength("daoNames");
		action=rd.getString("action");
	}
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
	<title>DAO列表</title>
	<link rel="stylesheet" href="../../css/style.css" type="text/css">
	<link rel="stylesheet" href="../../css/menu.css" type="text/css">
	<script src="../../js/forms.js" type="text/javascript"></script>
</head>

<body>
  <jsp:include page="/jsp/head.jsp"/>
  <menu:showBar x="12" y="16" height="23" width="100%"/>
	<div>
		<p>系统中共&nbsp;<%=rows%>&nbsp;个DAO组件：</p>
		<ul>
		<%for(int i=0;i<rows;i++){%>
			<li><a href="dcAdmin.jsp?daoName=<%=rd.get("daoNames",i)%>&action=<%=action%>"><%=rd.get("daoNames",i)%></a></li>
		<%}%>
		</ul>
		<P>您可以从选择上面列表中的一个组件进行数据字典管理或者自动生成JSP代码。</p>
	</div>
  <menu:show/>	
</body>
</html>