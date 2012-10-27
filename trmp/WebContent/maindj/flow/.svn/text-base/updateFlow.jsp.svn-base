<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<%-- <script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script> --%>
<title>修改流程定义信息</title>
<script type="text/javascript">
function updateFlow(){
	document.flow_form.action="updateFlow.?";
	document.flow_form.submit();
}
</script>
</head>
<body>
	<form name="flow_form" method="post">
<div id="top-select">
	<!-- <div class="select-div" onclick="updateFlow();">
	  <span id="ok-icon"></span> 
	  <span class="text">提交</span>
	</div>
	<div class="select-div" onclick="javascript:reset();">
	  <span id="reset-icon"></span> 
	  <span	class="text">重置</span>
	</div>
	<div class="select-div" onclick="javascript:window.parent.parent.GB_hide();">
	  <span id="close-icon"></span> 
	  <span	class="text">关闭</span>
	</div> -->
	<span class="tishi">带<font color="red">*</font>号为必填项</span>
</div>
	<table class="add-table" >
		<tr>
			<td align="right" >流程定义ID：</td>
			<td >
			<input type="text" name="TA_FLOWDEFINITION/DEFINITIONID[0]@oldValue" value="<%=rd.getStringByDI("TA_FLOWDEFINITIONs","DEFINITIONID",0) %>" readonly="readonly">
				</input>
				<input type="hidden" name="TA_FLOWDEFINITION/orgid" id="orgid" value=<%=sd.getString("orgid") %>>
				<span>*</span>
			</td>
			</tr>
			<tr>
			<td align="right" >流程定义名称：</td>
			<td>
			<input type="text" name="TA_FLOWDEFINITION/FLOWNAME"   value="<%=rd.getStringByDI("TA_FLOWDEFINITIONs","FLOWNAME",0) %>"/><span>*</span>
			</td>
		</tr>
		<tr>
			<td align="right" >流程描述：</td>
			<td >
				<input type="text" name="TA_FLOWDEFINITION/FLOWDESC" value="<%=rd.getStringByDI("TA_FLOWDEFINITIONs","FLOWDESC",0) %>"></input>
			</td>
		
		</tr>
		
	</table>
	<div align="center" id="last-sub">
		<input type="button" id="button" value="提交" onclick="updateFlow()"/>
		<input type="button" id="button" value="返回" onclick="history.go(-1);"/>
	</div>
</form>	
</body>
</html>