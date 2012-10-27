<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
    <%@ include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript">
function upGather(){
	document.gather_form.action="upGather.";
	document.gather_form.submit();
	window.parent.parent.location.reload(); 
	window.parent.parent.GB_hide(); 
}
</script>
<title>修改集合地点</title>
</head>
<body>
<form name="gather_form" method="post">
<div id="top-select">
	<div class="select-div" onclick="upGather();">
	  <span id="ok-icon"></span> 
	  <span class="text">提交</span>
	</div>
	<div class="select-div" >
	  <span id="reset-icon"></span> 
	  <span	class="text">重置</span>
	</div>
	<div class="select-div" onclick="javascript:window.parent.parent.GB_hide();">
	  <span id="close-icon"></span> 
	  <span	class="text">关闭</span>
	</div>
	<span class="tishi">带<font color="red">*</font>号为必填项</span>
</div>
	<table class="add-table" width="100%">
		<tr>
			<td align="right" width="20%">集合地点：</td>
			<td >
				<input type="hidden" name="TA_ZT_GATHER_HIS/GATHER_ID"  value="<%=rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER_ID",0) %>"></input>
				<input type="hidden" name="TA_ZT_GATHER_HIS/GATHER_ID[0]@oldValue"  value="<%=rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER_ID",0) %>"></input>
				<input type="text" name="TA_ZT_GATHER_HIS/GATHER" id="GATHER" value="<%=rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER",0) %>"></input><span>*</span>
			</td>
		</tr>
		<tr>
			<td align="right" >集合时间：</td>
			<td >
				<input type="text" name="TA_ZT_GATHER_HIS/GATHER_TIME" id="GATHER_TIME" value="<%=rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER_TIME",0) %>" ></input>
			</td>
		</tr>
		<tr>
			<td align="right" >加价：</td>
			 <td >
				<input type="text" name="TA_ZT_GATHER_HIS/ADD_M_COUNT" id="ADD_M_COUNT" value="<%=rd.getStringByDI("TA_ZT_GATHER_HISs","ADD_M_COUNT",0) %>"></input>
			</td>
		</tr>
	</table>
</form>	
</body>
</html>