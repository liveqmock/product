<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<title>�޸�VIP������Ϣ</title>
<script type="text/javascript">
function updateVip(){
	document.vip_form.action="updateVip.?";
	document.vip_form.submit();
	window.parent.parent.location.reload(); 
	window.parent.parent.GB_hide(); 
}
</script>
</head>
<body>
	<form name="vip_form" method="post">
<div id="top-select">
	<div class="select-div" onclick="updateVip();">
	  <span id="ok-icon"></span> 
	  <span class="text">�ύ</span>
	</div>
	<div class="select-div" onclick="javascript:reset();">
	  <span id="reset-icon"></span> 
	  <span	class="text">����</span>
	</div>
	<div class="select-div" onclick="javascript:window.parent.parent.GB_hide();">
	  <span id="close-icon"></span> 
	  <span	class="text">�ر�</span>
	</div>
	<span class="tishi">��<font color="red">*</font>��Ϊ������</span>
</div>
	<table class="add-table" >
		<tr>
			<td align="right" >��Ա���ţ�</td>
			<td >
				<input type="hidden" name="TA_VIP_INFO/ID@oldValue" value="<%=rd.getStringByDI("TA_VIP_INFOs","ID",0) %>">
				<input type="text" name="TA_VIP_INFO/VIP_NO" value="<%=rd.getStringByDI("TA_VIP_INFOs","VIP_NO",0) %>"></input><span>*</span>
			</td>
			<td align="right" >��Ա������</td>
			<td >
			<input type="text" name="TA_VIP_INFO/VIP_NAME"   value="<%=rd.getStringByDI("TA_VIP_INFOs","VIP_NAME",0) %>"/><span>*</span>
			</td>
		</tr>
		<tr>
			<td align="right" >��ϵ�绰��</td>
			<td >
				<input type="text" name="TA_VIP_INFO/VIP_TEL" value="<%=rd.getStringByDI("TA_VIP_INFOs","VIP_TEL",0) %>"></input>
			</td>
			<td align="right" >��ϵ��ַ��</td>
			<td >
			<input type="text" name="TA_VIP_INFO/ADDRESS" value="<%=rd.getStringByDI("TA_VIP_INFOs","ADDRESS",0) %>"/>
			</td>
		</tr>
		<tr>
			<td align="right" >��Ա���֣�</td>
			<td >
				<input type="text" name="TA_VIP_INFO/VIP_INTEGRAL" value="<%=rd.getStringByDI("TA_VIP_INFOs","VIP_INTEGRAL",0) %>" ></input>
			</td>
			<td align="right" >���֤���룺</td>
			<td >
			<input type="text" name="TA_VIP_INFO/ID_CARD"  value="<%=rd.getStringByDI("TA_VIP_INFOs","ID_CARD",0) %>" />
			</td>
		</tr>
		<tr>
			<td align="right" >�������ڣ�</td>
			<td colspan="3">
				<input type="text" readonly="readonly" name="TA_VIP_INFO/APPLY_DATE" value="<%=rd.getStringByDI("TA_VIP_INFOs","APPLY_DATE",0) %>" ></input>
			</td>
		</tr>
	</table>
</form>	
</body>
</html>