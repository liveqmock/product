<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="/trmp/jquery/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="/trmp/css/treeAndAllCss.css"/>
<script type="text/javascript">
function addFlow() {
	if(document.getElementById("flow_no").value==""){
		alert("请输入流程ID");
		document.getElementById("flow_no").focus();
		return false;
		}
	if(document.getElementById("flow_name").value==""){
		alert("请输入流程名称");
		document.getElementById("flow_name").focus();
		return false;
		}
	if(document.getElementById("flow_exc").value==""){
		alert("请输入流程描述 ");
		document.getElementById("flow_exc").focus();
		return false;
		}
	document.flow_form.action="addFlow.?";
	document.flow_form.submit();
}
</script>
<title>添加流程定义信息</title>
</head>
<body>
<form name="flow_form" method="post">
<div id="top-select">
	<!-- <div class="select-div" onclick="addFlow();">
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
				<input type="text" name="TA_FLOWDEFINITION/DEFINITIONID" id="flow_no" ></input><span>*</span>
				<input type="hidden" name="TA_FLOWDEFINITION/orgid" id="orgid" value=<%=sd.getString("orgid") %>>
				</td>
				</tr>
				<tr>
			<td align="right" >流程定义名称：</td>
			<td >
				<input type="text" name="TA_FLOWDEFINITION/FLOWNAME"  id="flow_name" ></input><span>*</span>
			</td>
			</tr>
			<tr>
			<td align="right" >流程描述：</td>
			<td >
			<input type="text" name="TA_FLOWDEFINITION/FLOWDESC" id="flow_exc"  />
			</td>

		</tr>
			
	</table>
	<div align="center" id="last-sub">
		<input type="button" id="button" value="提交" onclick="addFlow()"/>
		<input type="button" id="button" value="返回" onclick="history.go(-1);"/>
	</div>
</form>	
</body>
</html>