<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common.jsp"%>
<html>
<head>
<link rel="stylesheet" href="../../css/style.css" type="text/css">
<link rel="stylesheet" href="../../css/menu.css" type="text/css">
<title>�޸��û�����</title>
<script language="javascript">
<!--
	function validate(){
		if(document.forms(0).elements("userNO").value=="" && document.forms(0).elements("userID").value==""){
			alert("����������'�û����'����'�û��ʺ�'��ȷ�����û�!");
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
  <p>����Ҫ�޸�ĳ���û������룬������<i>���û������ͺͱ��</i><br><b>����</b><i>�û��ʺ�</i>��ȷ�����û�:</p>
  <table border="0" cellspacing="1" cellpadding="0" class="hci" width="420">
    <tr> 
      <td width="17%" class="head">�û�����</td>
      <td width="16%"class="content"> 
        <select name="userType">
          <option value="E" selected>Ա��</option>
          <option value="C">�ͻ�</option>
          <option value="S">��Ӧ��</option>
          <option value="P">�������</option>
        </select>
      </td>
      <td width="18%"class="head">�û����</td>
      <td width="49%"class="content">

        <input type="text" name="userNO" size="12" maxlength="12" class="flat">
      </td>
    </tr>
    <tr> 
      <td width="17%" class="head">�û��ʺ�</td>
      <td class="content" colspan="3"> 
        <input type="text" name="userID" class="flat" size="30">
      </td>
    </tr>
    <tr> 
      <td width="17%" class="head">������</td>
      <td class="content" colspan="3"> 
        <input type="password" name="newPass" class="flat" size="24">
      </td>
    </tr>
    <tr> 
      <td width="17%" class="head">ȷ��������</td>
      <td class="content" colspan="3"> 
        <input type="password" name="confirmPass" class="flat" size="24">
      </td>
    </tr>
  </table>
  <p> 
    <input type="submit" name="Submit" value="�ύ" class="button">
    <input type="button" name="return" value="����" class="button" onclick="javascript:zi();">
  </p>
</form>
<menu:menuItems/>
</body>
</html>