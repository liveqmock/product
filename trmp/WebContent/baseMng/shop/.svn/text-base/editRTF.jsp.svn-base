<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
    <%@ include file="/common.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<title>人头费维护</title>
<script type="text/javascript">
function addRTF(){
	document.rtf_form.action="addRTF.";
	document.rtf_form.submit();
	window.parent.parent.location.reload(); 
	window.parent.parent.GB_hide(); 
	}
</script>
</head>
<body>
<form name="rtf_form" method="post">
<input type="hidden" value="<%=rd.getString("id")%>" name="id">
<div id="top-select">
	<div class="select-div" onclick="addRTF();">
	  <span id="ok-icon"></span> 
	  <span class="text">提交</span>
	</div>
	<div class="select-div" >
	  <span id="reset-icon"></span> 
	  <span	class="text">重置</span>
	</div>
	<div class="select-div" onclick="reload();">
	  <span id="close-icon"></span> 
	  <span	class="text">关闭</span>
	</div>
	<span class="tishi">带<font color="red">*</font>号为必填项</span>
</div>
	<table class="add-table" width="99%">
	<%
	if(rd.getTableRowsCount("rtfInfo")>0){
	int rtfRows=rd.getTableRowsCount("rtfInfo");for(int r=0;r<rtfRows;r++){ 
	%>
		<tr>
			<td align="right" ><%=rd.getStringByDI("rtfInfo","name",r) %>：</td>
			<td >
				<input type="hidden" name="rtf/xzqh_id[<%=r %>]"  value="<%=rd.getStringByDI("rtfInfo","id",r) %>"></input>
				<input type="text" name="rtf/price[<%=r %>]" value="<%=rd.getStringByDI("rtfInfo","SINGLE_MONEY",r) %>"></input>
			</td>
		</tr>
		<%} 
		}else{
			int cityRows=rd.getTableRowsCount("cityInfo");for(int c=0;c<cityRows;c++){ 
		%>
			<tr>
			<td align="right" ><%=rd.getStringByDI("cityInfo","name",c) %>：</td>
			<td >
				<input type="hidden" name="rtf/xzqh_id[<%=c %>]"  value="<%=rd.getStringByDI("cityInfo","id",c) %>"></input>
				<input type="text" name="rtf/price[<%=c %>]" ></input>
			</td>
		</tr>
		<%}
			}%>
	</table>
</form>	
</body>
</html>