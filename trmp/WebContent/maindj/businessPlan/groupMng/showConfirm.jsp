<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
String TZTSID = rd.getString("TZTSID");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>


<title>确认件查看</title>
</head>

<body >
  <div class="add-table"> 
	<table border="0">
	<%
	for(int i=0;i<rd.getTableRowsCount("TA_DJ_QRJs");i++){
		String id = rd.getStringByDI("TA_DJ_QRJs","ID",i);
	%>
	  <tr>
	  	<td><img src="<%=request.getContextPath() %>/maindj/businessPlan/groupMng/queryConfirm<%=i %>.?TA_DJ_QRJ/ID=<%=id %>" width="800"></img></td>
	  </tr>
<%
	}%>
	</table>
  </div>
</body>
</html>