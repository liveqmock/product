<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<title>��Ӽ��ϵص�</title>
<script type="text/javascript">
function addGather(){
	document.gather_form.action="addGather.";
	document.gather_form.submit();
	window.parent.parent.location.reload(); 
	window.parent.parent.GB_hide(); 
}
</script>
</head>
<body>
<form name="gather_form" method="post">
<div id="top-select">
	<div class="select-div" onclick="addGather();">
	  <span id="ok-icon"></span> 
	  <span class="text">�ύ</span>
	</div>
	<div class="select-div" >
	  <span id="reset-icon"></span> 
	  <span	class="text">����</span>
	</div>
	<div class="select-div" onclick="javascript:window.parent.parent.GB_hide();">
	  <span id="close-icon"></span> 
	  <span	class="text">�ر�</span>
	</div>
	<span class="tishi">��<font color="red">*</font>��Ϊ������</span>
</div>
	<table class="add-table" width="100%">
		<tr>
			<td align="right" width="20%">���ϵص㣺</td>
			<td >
				<input type="text" name="TA_ZT_GATHER_HIS/GATHER" id="GATHER" ></input><span>*</span>
			</td>
		</tr>
		<tr>
			<td align="right" >����ʱ�䣺</td>
			<td >
				<input type="text" name="TA_ZT_GATHER_HIS/GATHER_TIME" id="GATHER_TIME" ></input>
			</td>
		</tr>
		<tr>
			<td align="right" >�Ӽۣ�</td>
			 <td >
				<input type="text" name="TA_ZT_GATHER_HIS/ADD_M_COUNT" id="ADD_M_COUNT" ></input>
			</td>
		</tr>
	</table>
</form>	

</body>
</html>