<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@include file="../../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.util.Calendar"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title><%=sd.getString("simpleName") %>--������Դ����ƽ̨(TRMP)</title>
<style>
.showPointer{cursor: pointer;}
</style>
<script type="text/javascript">
function getClientTime(){
	var localtime=new Date();
	var year=localtime.getFullYear();
	var month=localtime.getMonth()+1;
	var date=localtime.getDate();
	var hour=localtime.getHours();
	var minu=localtime.getMinutes();
	var seco=localtime.getSeconds();

	logindata="��¼ʱ�䣺";
	document.getElementById("login-time").innerHTML=logindata+year+"-"+month+"-"+date+"  "+hour+":"+minu;
	}

function quit()
{
	// ������ҳ�������session
	top.location='/trmp/login/quit.jsp';
}
</script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/index.css" />
</head>
<body onload="getClientTime()">
		<div id="top">
			<div id="top_bg">
				<div id="self-logo"><img src="<%=request.getContextPath() %>/images/login.gif"/></div>
				<div id="global-logo"></div>
			</div>
			<div id="login-info">
			    <div onclick="quit();" class="showPointer"><span class="text">�˳�</span></div>
				<div id="login-time"><%=request.getContextPath() %></div>
				<div class="login-user">�û�����<%=sd.getString("username") %></div>
				<div>�汾��1.00</div>
			</div>
		</div>
</body>
</html>
