<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.dream.bizsdk.common.SysError"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%
	int retVal = 0;
	String userID	= null;
	String password	= null;
	com.dream.bizsdk.web.SecurityManager SM = (com.dream.bizsdk.web.SecurityManager)application.getAttribute(SysVar.APP_SM);
	com.dream.bizsdk.common.databus.BizData errors = (com.dream.bizsdk.common.databus.BizData)application.getAttribute(SysVar.APP_ERRORS);
	userID = request.getParameter("userID");
	password = request.getParameter("password");
	if(userID != null){
		retVal = SM.login(request,userID,password);
		if(retVal == SysError.PASS){
			String contextPath=request.getContextPath();
			response.sendRedirect(contextPath+"/jsp/login.");
			return;
		}
	}	
%>
<html>
<head>
  <title>欢迎进入“管理信息系统”</title>
  <link rel="stylesheet" href="../css/style.css" type="text/css">
  <script language="javascript">
  <!--
  	var ns = document.layers?true:false;
  	var ie = document.all?true:false;
  	
  	function validate(frm){
  		var obj = frm.elements("userID");
  		if(obj.value==""){
  			alert("对不起，用户名不能为空！请输入您的用户名。");
  			obj.focus();  			
  			return false;
  		}else{
  			return true;
  		}  		
  	}
  	
  	function init(frm){
  		frm.elements("userID").focus();
  	}
  	
  	function rst(frm){
  		frm.reset();
  		if(ns==true){
  			document.errMsg.visibility="hide";
  		}else if(ie==true){
  			errMsg.style.visibility="hidden";
  		}
  		frm.elements("userID").focus();
  	}
  		
  //-->
  </script>  	
</head>
<body onload="init(document.forms(0))">
  <table width="100%" height="100%" >
    <tr>
      <td align="center" valign="middle" width="100%" height="100%"> 
        <form action="login.jsp" method="post" onsubmit="return validate(document.forms(0))"> 
          <div width="413" align="center">&nbsp;</div>
          <table  border="0" align="center" width="413" cellpadding="0" cellspacing="0" height="180" class="login">
	    <tr> 
	      <td bgcolor="#336699" valign="middle" align="center" colspan="2" height="28" nowrap > 
	        <div align="center"><font color="#FFFFFF"><b>欢迎进入管理信息系统</b></font></div>
	      </td>
	    </tr>
            <tr> 
              <td valign="middle" align="center" colspan="2" height="28" nowrap > 
                <div align="left">请输入您的用户名和密码:</div>
              </td>
            </tr>
            <tr> 
              <td valign="middle" align="center" width="86" height="28" > 
                <div align="right">用户名</div>
              </td>
              <td valign="middle" align="center" width="280" height="28" > 
              <div align="left"><input name="userID" size="12" maxlength="12" class="flat"></div>
              </td>
            </tr>
            <tr> 
              <td valign="middle" align="center" width="86" height="28" > 
                <div align="right">密码</div>
              </td>
              <td valign="middle" align="center" width="280" height="28" > 
                <div align="left"> 
                  <input type="password" name="password" size="12" maxlength="12" class="flat">
                  <input type="submit" value="Login" name="submit" class="button">
                  <input type="button" name="Reset" value="Reset" class="button" onclick="javascript:rst(document.forms(0));">
                </div>
              </td>
            </tr>
            <tr> 
              <td valign="middle" align="center" colspan="2" height="64"> 
              	<div ID="errMsg">
              	<%if(retVal <0){%>
                	<font color="#990000">错误编号：<%=retVal%>;错误信息:<%=errors.get("DRMError","errMessage",retVal)%></font>
            	<%}else{%>
			&nbsp;
		<%}%>
		</div>
	      </td>		
            </tr>
          </table>
          <p/>
          <hr width="413" color="#336699"/>
          <small>@Copyright 2003, Dream ltd. All rights reserved.</small>
        </form>
      </td>
    </tr>
  </table>
</body>
</html>