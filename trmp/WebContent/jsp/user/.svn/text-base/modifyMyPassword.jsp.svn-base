<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common.jsp"%>
<html>
<head>
<link rel="stylesheet" href="../../css/style.css" type="text/css">
<link rel="stylesheet" href="../../css/menu.css" type="text/css">	
<title>修改我的密码</title>
</head>
<body>
<!-- show the menu bar-->
<jsp:include page="/jsp/head.jsp"/>
<menu:menuBar x="12" y="16" height="23" width="100%"/>
<form name="form1" method="post" action="alterMyPassword.">
  <p>您现在可以修改您的密码:</p>
  <table width="380" border="0" cellspacing="1" cellpadding="0" class="hci">
    <tr> 
      <td width="25%" class="head">请输入新密码</td>
      <td width="75%" class="content"> 
        <input type="password" name="newPass" class="flat">
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">请确认新密码</td>
      <td width="78%" class="content"> 
        <input type="password" name="confirmPass" class="flat">
      </td>
    </tr>
  </table>
    <input type="submit" name="Submit" value="提交" class="button">
    <input type="button" name="return" value="返回" class="button" onclick="javascript:history.go(-1);">
</form>
<!-- show the menu-->
<menu:menuItems/>
</body>
</html>