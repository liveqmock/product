<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="com.dream.bizsdk.common.SysError"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%@page import="com.trm.login.Login"%>
<%
	int retVal = 0;
	String jxdm = null;
	String pass = null;
	String user = null;
	Login SM = new Login();
	SM.init(application);
	com.dream.bizsdk.common.databus.BizData errors = (com.dream.bizsdk.common.databus.BizData) application
			.getAttribute(SysVar.APP_ERRORS);
	pass = request.getParameter("password");
	user = request.getParameter("userid");
	if (user != null) {
		retVal = SM.login(request, user, pass);
		if (retVal == SysError.PASS) {
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/main/index.jsp");
			//response.sendRedirect(contextPath + "/frame/mat/getMenu.?menuLevel=1");
			return;
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<style>
* { margin:0; padding:0; word-wrap:break-word; word-break:break-all; }
</style>
<script type="text/javascript">
    document.onkeydown = function(e){
        if(!e) e = window.event;
        if((e.keyCode || e.which) == 13){
        	document.myform.submit();
        }
    }

    function errorMsgs(){
		alert("用户名或密码错误或联系管理员！");
		return false;
    }
</script>
<title>天游资源管理平台(TRMP)  用户登录</title>
<style type="text/css">
img,li,td,tr,table,input{
	border:0px;
	margin:0px;
	padding:0px;
	
}
table#container{
	margin:auto auto;
}
input#input_name{
	margin-top:0px;
	margin-bottom:0px;
	border-top:0px;
	border-bottom:0px;
	padding-top:0px;
	padding-bottom:0px;
	width:108px;
	height:13px;
}
input#input_pw{
	width:108px;
	height:14px;
}
</style>
</head>
<body id=userlogin_body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" 
	  marginwidth="0" marginheight="0" 
	  onload="<%if(retVal == SysError.PASSWORD_ERROR){ %>errorMsgs();<%} %>">
	  
<FORM name="myform" action="<%=request.getContextPath() %>/login/login.jsp" method=post>
<table id="container" width="997" height="613" border="0" cellpadding="0" cellspacing="0">
  <tr>
		<td rowspan="10">
			<img src="<%=request.getContextPath() %>/login/images/login_01.gif" width="255" height="613" alt=""></td>
		<td colspan="8">
			<img src="<%=request.getContextPath() %>/login/images/login_02.gif" width="493" height="243" alt=""></td>
		<td rowspan="10">
			<img src="<%=request.getContextPath() %>/login/images/login_03.gif" width="248" height="613" alt=""></td>
		<td>
			<img src="<%=request.getContextPath() %>/login/images/&#x5206;&#x9694;&#x7b26;.gif" width="1" height="243" alt=""></td>
	</tr>
	<tr>
		<td rowspan="7">
			<img src="<%=request.getContextPath() %>/login/images/login_04.gif" width="144" height="142" alt=""></td>
		<td colspan="7">
			<img src="<%=request.getContextPath() %>/login/images/login_05.gif" width="349" height="66" alt=""></td>
		<td>
			<img src="images/&#x5206;&#x9694;&#x7b26;.gif" width="1" height="66" alt=""></td>
	</tr>
	<tr>
		<td rowspan="7">
			<img src="<%=request.getContextPath() %>/login/images/login_06.gif" width="116" height="77" alt=""></td>
		<td id="nameArea" colspan="5"><input type="text" id="input_pw" maxLength=20 name="userid" id=name/></td>
 		<td rowspan="7">
			<img src="<%=request.getContextPath() %>/login/images/login_08.gif" width="125" height="77" alt=""></td>
		<td>
			<img src="<%=request.getContextPath() %>/login/images/&#x5206;&#x9694;&#x7b26;.gif" width="1" height="16" alt=""></td>
	</tr>
	<tr>
		<td colspan="5">
			<img src="<%=request.getContextPath() %>/login/images/login_09.gif" width="108" height="11" alt=""></td>
		<td>
			<img src="<%=request.getContextPath() %>/login/images/&#x5206;&#x9694;&#x7b26;.gif" width="1" height="11" alt=""></td>
	</tr>
	<tr>
		<td id="pwArea" colspan="5"><input id="input_name" name=password id=pass type="password"/></td>
		<td>
			<img src="<%=request.getContextPath() %>/login/images/&#x5206;&#x9694;&#x7b26;.gif" width="1" height="15" alt=""></td>
	</tr>
	<tr>
		<td colspan="5">
			<img src="<%=request.getContextPath() %>/login/images/login_11.gif" width="108" height="8" alt=""></td>
		<td>
			<img src="images/&#x5206;&#x9694;&#x7b26;.gif" width="1" height="8" alt=""></td>
	</tr>
	<tr>
		<td rowspan="3">
			<img src="<%=request.getContextPath() %>/login/images/login_12.gif" width="24" height="27" alt=""></td>
		<td>
			<img src="<%=request.getContextPath() %>/login/images/login_13.gif" width="39" height="17" alt=""></td>
		<td rowspan="3">
			<img src="<%=request.getContextPath() %>/login/images/login_14.gif" width="5" height="27" alt=""></td>
		<td>
			<a href="###" onClick="javascript:document.myform.submit();" ><img src="<%=request.getContextPath() %>/login/images/login_15.gif" width="39" height="17" alt=""></a></td>
		<td rowspan="3">
			<img src="<%=request.getContextPath() %>/login/images/login_16.gif" width="1" height="27" alt=""></td>
		<td>
			<img src="images/&#x5206;&#x9694;&#x7b26;.gif" width="1" height="17" alt=""></td>
	</tr>
	<tr>
		<td rowspan="2">
			<img src="<%=request.getContextPath() %>/login/images/login_17.gif" width="39" height="10" alt=""></td>
		<td rowspan="2">
			<img src="<%=request.getContextPath() %>/login/images/login_18.gif" width="39" height="10" alt=""></td>
		<td>
			<img src="images/&#x5206;&#x9694;&#x7b26;.gif" width="1" height="9" alt=""></td>
	</tr>
	<tr>
		<td>
			<img src="<%=request.getContextPath() %>/login/images/login_19.gif" width="144" height="1" alt=""></td>
		<td>
			<img src="images/&#x5206;&#x9694;&#x7b26;.gif" width="1" height="1" alt=""></td>
	</tr>
	<tr>
		<td colspan="8">
			<img src="<%=request.getContextPath() %>/login/images/login_20.gif" width="493" height="227" alt=""></td>
		<td>
			<img src="images/&#x5206;&#x9694;&#x7b26;.gif" width="1" height="227" alt=""></td>
	</tr>
</table>
</form>
</body>
</html>