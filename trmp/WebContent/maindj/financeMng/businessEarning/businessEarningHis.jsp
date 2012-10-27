<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>

<%@page import="java.util.Date"%>

<%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/treeAndAllCss.css"/>
<title>应收款清款记录</title>
</head>
<body>
<form name="myForm" method="post">
<div id="lable"><span>应收款情况</span></div>
<div id="bm-table">
  <div id="hotelDiv">	
	<table width="100%" class="datas">  
	  <tr>
	  	<td>团号：</td>
	  	<td><%=rd.getStringByDI("rsHistory","tid",0) %></td>
	  </tr>	
	  <tr>
	  	<td>组团社名称：</td>
		<td><%=rd.getStringByDI("rsHistory","ztsmc",0) %></td>
	  </tr>		
	  <tr>
	    <td>预算款：</td>
	    <td><%=rd.getStringByDI("rsHistory","YSZK",0) %> 元</td>
	  </tr>
	  <tr>
	    <td>预算款现付金额：</td>
	    <td><%=rd.getStringByDI("rsHistory","yfk",0) %> 元</td>
	  </tr>
	  <tr>
	  	<td>预算款状态：</td>
	  	<td><%=rd.getStringByDI("rsHistory","qkzt",0).equals("Y")?"已清款":"未清款" %></td>
	  </tr>
  	</table>
  </div>
</div>

<div id="lable"><span >应收款清款记录</span></div>
<div id="list-table">
	<table width="100%" class="datas">  
	  <tr id="first-tr">
	  	<td>收款日期</td>
	  	<td>收款金额</td>
	  	<td>收款人</td>
	  </tr>
<%
int rows = rd.getTableRowsCount("rsHistory");
for(int i=0;i<rows;i++){
%>	
	  <tr>
	  	<td><%=rd.getStringByDI("rsHistory","bcqkrq",i) %></td>
	  	<td><%=rd.getStringByDI("rsHistory","bcqkje",i) %></td>
	  	<td><%=rd.getStringByDI("rsHistory","username",i) %></td>
	  </tr>	
<%
}
%>
  	</table>
</div>

</form>
</body>
</html>
