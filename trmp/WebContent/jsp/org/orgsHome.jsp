<%@include file="../common.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<html>
<head>
<title>组织机构管理</title>
<link rel="stylesheet" href="../../css/menu.css" type="text/css">
<link rel="stylesheet" href="../../css/style.css" type="text/css">
</head>

<body>
<!-- show the menu bar-->
<jsp:include page="/jsp/head.jsp"/>
<menu:menuBar height="23" width="100%"/>


<!-- show the menu-->
<menu:menuItems/>

<DIV width="100%">
<iframe alignment="left" name="tree" src="getSubOrgs.?HRORGANIZATION/ORGID=&param=org&target=orgRight" frameborder="NO" height="480" width="20%"  style="z-index:-1">
<iframe alignment="right" name="orgRight" id="orgRight" src="" frameborder="NO" height="480" width="80%" style="z-index:-1">
</DIV>
</body>
</html>