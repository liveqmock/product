<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.mysql.jdbc.Blob"%>
<%@page import="java.io.InputStreamReader"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�ο��տ�</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<script type="text/javascript">
	function doSub(){
		//ԭ�������տ���Ҫ���ϱ��������
		var alreadyOld = document.getElementById("alreadyOld").value;
		var thisTime = document.getElementById("thisTime").value;
		var alreadyNew = parseInt(alreadyOld)+parseInt(thisTime);
		document.getElementById("alreadyNew").value=alreadyNew;
		document.myForm.submit();
		window.parent.parent.location.reload(); 
		window.parent.parent.GB_hide(); 
	}
</script>
</head>
<body>
<form action="updateMoney.?action=new" method="post" name="myForm">
<input type="hidden"  name="TA_ZT_YKORDER/ID" value="<%=rd.getStringByDI("rsMoneyInit","id",0) %>"/>
<input type="hidden"  name="TA_ZT_YKORDER/ID[0]@oldValue" value="<%=rd.getStringByDI("rsMoneyInit","id",0) %>"/>
<input type="hidden"  id="alreadyOld" value="<%=rd.getStringByDI("rsMoneyInit","yi_sk",0) %>"/>
<input type="hidden"  id="alreadyNew" name="TA_ZT_YKORDER/YI_SK" value="<%=rd.getStringByDI("rsMoneyInit","yi_sk",0) %>"/>
<div class="add-table">
  <table class="datas">
	<tr class="first-tr">
	  <td colspan="4"><span>�ο��տ�</span></td>
	</tr>
	<tr>
	  <td width="20%" align="right">�����ţ�</td>
	  <td width="30%"><%=rd.getStringByDI("rsMoneyInit","id",0) %></td>
	  <td width="20%" align="right">��������</td>
	  <td width="30%"><%=rd.getStringByDI("rsMoneyInit","ysrs",0) %></td>
	</tr>
	<tr>
	  <td align="right">�ο���ϵ�ˣ�</td>
	  <td><%=rd.getStringByDI("rsMoneyInit","visitor_nm",0) %></td>
	  <td align="right">�ο���ϵ��ʽ��</td>
	  <td><%=rd.getStringByDI("rsMoneyInit","tel",0) %></td>
	</tr>
	<tr>
	  <td align="right">����ʱ�䣺</td>
	  <td colspan="3"><%=rd.getStringByDI("rsMoneyInit","regedit_time",0) %></td>
	</tr>
	<tr height="30">
	  <td align="right">������ע��</td>
	  <td colspan="3"><%=rd.getStringByDI("rsMoneyInit","remark",0) %></td>
	</tr>
  </table>
</div>
<div class="add-table"> 
  <table class="datas">
	<tr class="first-tr">
	  <td colspan="4"><span>�տ�ȷ�� </span></td>
	</tr>
	<tr>
	  <td width="20%" align="right">Ӧ�տ</td>
	  <td width="30%" ><%=rd.getStringByDI("rsMoneyInit","yin_sk",0) %>Ԫ�����գ�<%=rd.getStringByDI("rsMoneyInit","yin_sk",0) %>���գ�<%=rd.getStringByDI("rsMoneyInit","yi_sk",0) %>��</td>
	  <td width="20%" align="right">�����տ</td>
	  <td width="30%"><input type="text" class="smallInput" id="thisTime" value="<%=Integer.parseInt(rd.getStringByDI("rsMoneyInit","yin_sk",0))-Integer.parseInt(rd.getStringByDI("rsMoneyInit","yi_sk",0)) %>"/> </td>
	</tr>
  </table>
</div>

<div align="center" id="last-sub">
	<input type="button" id="button" value="ȷ ��" onclick="doSub();"/>
	<input type="button" id="button" value="�� ��" onclick="window.history.go(-1)"/>
</div>
</form>
</body>
</html>