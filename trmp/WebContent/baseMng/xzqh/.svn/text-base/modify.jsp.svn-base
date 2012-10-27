<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryui/jqueryui.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css"/>
<title>修改行政区划</title>
<script type="text/javascript">

$(function(){
	$("input:submit,input:button").button();
});
function doSUB(){
	if(document.getElementById("name").value==""){
		alert("请输入地区名称");
		return false;
		}
	document.xzqh_from.action="modifyLandkreise.?";	
	document.xzqh_from.submit();
	parent.TB_remove_refresh();
}


</script>

</head>
<%
String gnw = rd.getStringByDI("XZQHs","gnw",0);
%>
<body>
<form name="xzqh_from" method="post">
<div>
	<table class="add-table" width="99%">
		<tr>
			<td align="right" >地区名称：</td>
			<td >
			<input type="hidden" name="XZQH/ID" value="<%=rd.getStringByDI("XZQHs","ID",0)%>"/>
			<input type="hidden" name="XZQH/ID[0]@oldValue" value="<%=rd.getStringByDI("XZQHs","ID",0)%>"/>
			<input type="hidden" name="XZQH/PID" value="<%=rd.getStringByDI("XZQHs","PID",0)%>"/>
			<input type="text" name="XZQH/NAME" id="name" value="<%=rd.getStringByDI("XZQHs","name",0)%>"></input><span>*</span>
			</td>
		</tr>
		<tr>
	  	  <td align="right">地区性质：</td>
	  	  <td><input type="radio" name="XZQH/gnw" value="1" <%if(gnw.equals("1")) out.print("checked=\"checked\""); %>/>国内&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="XZQH/gnw" value="0" <%if(gnw.equals("0")) out.print("checked=\"checked\""); %>/>国外</td>
		</tr>
	</table>
</div>	
<div>
	<table class="datas">
		<tr>
			<td align="center">
				<input type="button" value="提    交" onclick="doSUB();"/>&nbsp;&nbsp;
				<input type="button" value="重    置" onclick="reset();"/>
			</td>
		</tr>				
	</table>
</div>
</form>	
</body>
</html>
