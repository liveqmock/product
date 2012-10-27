<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
int rows = rd.getTableRowsCount("orgReltions");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css" >
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/cooperate/enterprise.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryui/jqueryui.js" ></script>
<title>企业名片</title>
<script type="text/javascript">
$(function(){
	$( "input:submit,input:button").button();

	$(".addBtn").button({
		disabled : false,
		icons : {
			primary : "ui-icon-circle-plus"
		},
		text : true
	});
	$(".mailBtn").button({
		disabled : false,
		icons : {
			primary : "ui-icon-mail-closed"
		},
		text : true
	});
	$("#add").click(function (){
		window.location.href="addAttention.?hrorganization/orgid=<%=rd.getStringByDI("hrorganizations","orgid",0) %>";
	});
	$("#cancel").click(function (){
		window.location.href="cancelAttention.?hrorganization/orgid=<%=rd.getStringByDI("hrorganizations","orgid",0) %>";
	});
});
</script>
</head>
<body>
<div id="lable"><span>公司介绍</span></div>
<div class="box">
<div class="boxCont boxText">
<p class="companyInf">
&#12288;&#12288;<%=rd.getStringByDI("HRORGANIZATIONs","CMP_INTRO",0) %>
</p>
</div>
</div>

<div id="lable"><span>联系方式</span></div>
<div class="boxCont">
<ul class="contactInfo">
<li><%=rd.getStringByDI("hrorganizations","name",0) %></li>
<li>法人：<strong><%=rd.getStringByDI("hrorganizations","corporatename",0) %></strong>&nbsp;</li>
<li>手机：<%=rd.getStringByDI("hrorganizations","corporatetel",0) %></li>
<li>电话：<%=rd.getStringByDI("hrorganizations","CMP_TEL",0) %></li>
<li>传真：<%=rd.getStringByDI("hrorganizations","ORGFAX",0) %></li>
<li>地址：<%=rd.getStringByDI("hrorganizations","CITYNAME",0) %><%=rd.getStringByDI("hrorganizations","COMPYADD",0) %></li>
</ul>

</div>


<div class="boxCont">
<button class="mailBtn" value="发联系信"  onclick="addTrRow('hotelMd','hotel');">发联系信</button>
<%
if(rows<=0){
%>
<button class="addBtn" id="add" value="添加关注" >添加关注</button>
<%
}else{
%>
<button class="addBtn" id="cancel" value="取消关注" >取消关注</button>
<%
}
%>
<button class="addBtn" value="返&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;回"  onclick="javasrcipt:history.go(-1);">返&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;回</button>
</div>
</body>
</html>