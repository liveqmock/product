<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.dream.bizsdk.common.SysError"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%
	int retVal = 0;
	String userID	= null;
	String password	= null;
	String nextPage=null;
	com.dream.bizsdk.web.SecurityManager SM = (com.dream.bizsdk.web.SecurityManager)application.getAttribute(SysVar.APP_SM);
	com.dream.bizsdk.common.databus.BizData errors = (com.dream.bizsdk.common.databus.BizData)application.getAttribute(SysVar.APP_ERRORS);
	userID = request.getParameter("userID");
	password = request.getParameter("password");
	nextPage=request.getParameter("nextPage");
	if(userID != null){
		retVal = SM.login(request,userID,password);
		if(retVal == SysError.PASS){
			String contextPath=request.getContextPath();
			response.sendRedirect(nextPage);
			return;
		}
	}
%>
<html>
<head>
  <title>系统登陆</title>
  <link rel="stylesheet" href="../css/style.css" type="text/css">
  <script src="../js/forms.js" type="text/javascript"></script>
</head>
<body onload="init(document.forms(0))">
  <table width="100%" height="100%" >
    <tr >
      <td align="center" valign="middle" width="100%" height="100%"> 
        <form action="login.jsp" method="post" onsubmit="return validate(document.forms(0))"> 
          <input type="hidden" name="nextPage" value="<%=nextPage%>">
          <table  border="0" align="center" width="413" cellpadding="0" cellspacing="0" height="193">
            <tr bgcolor="#6699cc"> 
              <td valign="middle" align="center" colspan="2" height="28" nowrap > 
                <div align="left"><font color="#FFFFFF">系统登陆：请输入用户帐号和密码</font></div>
              </td>
            </tr>
            <tr bgcolor="#D7EFF8"> 
              <td valign="middle" align="center" width="86" height="44" bgcolor="#D7EFF8" > 
                <div align="right"><font size="3">账号</font></div>
              </td>
              <td valign="middle" align="center" width="280" height="44" > 
              <div align="left"><input name="userID" size="12" maxlength="12" class="m_focus"></div>
              </td>
            </tr>
            <tr bgcolor="#D7EFF8"> 
              <td valign="middle" align="center" width="86" height="44" > 
                <div align="right"><font size="3">密码</font></div>
              </td>
              <td valign="middle" align="center" width="280" height="44" > 
                <div align="left"> 
                  <input type="password" name="password" size="12" maxlength="12">
                  <input type="submit" value="Login" name="submit" class="button">
                  <input type="reset" name="Reset" value="Reset" class="button">
                </div>
              </td>
            </tr>
            <tr bgcolor="#D7EFF8"> 
              <td valign="middle" align="center" colspan="2" height="50" > <MARQUEE id=m1 direction=left  width=300 height=40 SCROLLDELAY=300><i><font color="#3300FF">新的一天新的开始！</font></i></MARQUEE></td>
            </tr>
            <tr bgcolor="#D7EFF8"> 
              <td valign="middle" colspan="2" height="64"> 
              	<%if(retVal <0){%>
                	<font color="#990000">错误编号：<%=retVal%>;错误信息:<%=errors.get("DRMError","errMessage",retVal)%></font>
            	<%}else{%>
				&nbsp;
		<%}%>
	      </td>		
            </tr>
          </table>
        </form>
      </td>
    </tr>
  </table>
</body>
</html>