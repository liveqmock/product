<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common.jsp"%>
<html>
<head>
<link rel="stylesheet" href="../../css/style.css" type="text/css">
<link rel="stylesheet" href="../../css/menu.css" type="text/css">
<title>修改用户密码</title>
<script language="javascript">
<!--
	function validate(){
		if(document.forms(0).elements("userNO").value=="" && document.forms(0).elements("userID").value==""){
			alert("您必须输入'用户编号'或者'用户帐号'来确定该用户!");
			return false;
		}else{
			return true;
		}
	}
	function zi(){
		alert(document.forms(0).elements("userType").style("z-index"));
	}
//-->

</script>
</head>
<body>
<jsp:include page="/jsp/head.jsp"/>
<menu:menuBar x="12" y="16" height="23" width="100%"/>
<form name="form1" method="post" action="alterUserPassword." onsubmit="return validate();">
  <p>您将要修改某个用户的密码，请输入<i>该用户的类型和编号</i><br><b>或者</b><i>用户帐号</i>来确定该用户:</p>
  <table border="0" cellspacing="1" cellpadding="0" class="hci" width="420">
    <tr> 
      <td width="17%" class="head">用户类型</td>
      <td width="16%"class="content"> 
        <select name="userType">
          <option value="E" selected>员工</option>
          <option value="C">客户</option>
          <option value="S">供应商</option>
          <option value="P">合作伙伴</option>
        </select>
      </td>
      <td width="18%"class="head">用户编号</td>
      <td width="49%"class="content">

        <input type="text" name="userNO" size="12" maxlength="12" class="flat">
      </td>
    </tr>
    <tr> 
      <td width="17%" class="head">用户帐号</td>
      <td class="content" colspan="3"> 
        <input type="text" name="userID" class="flat" size="30">
      </td>
    </tr>
    <tr> 
      <td width="17%" class="head">新密码</td>
      <td class="content" colspan="3"> 
        <input type="password" name="newPass" class="flat" size="24">
      </td>
    </tr>
    <tr> 
      <td width="17%" class="head">确认新密码</td>
      <td class="content" colspan="3"> 
        <input type="password" name="confirmPass" class="flat" size="24">
      </td>
    </tr>
  </table>
  <p> 
    <input type="submit" name="Submit" value="提交" class="button">
    <input type="button" name="return" value="返回" class="button" onclick="javascript:zi();">
  </p>
</form>
<menu:menuItems/>
</body>
</html>