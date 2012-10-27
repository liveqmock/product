<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
String layer = request.getParameter("layer");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryui/jqueryui.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css"/>
<script type="text/javascript"> 
$(function(){
	$("input:submit,input:button").button();
});

	function addXZQH(){
		if(document.getElementById("name").value==""){
			alert("请输入地区名称");
			document.getElementById("name").focus();
			return false;
		}
		document.xzqh_from.action="addLandkreise.";
		document.xzqh_from.submit();
		parent.TB_remove_refresh();
	}
</script>
<title>添加行政区划</title>
</head>

<body >
<form name="xzqh_from" method="post">

<div>
  <table class="add-table" width="99%">
	<tr>
	  <td align="right" >地区名称：</td>
	  <td >
		<input type="hidden" name="pid" value="<%=request.getParameter("pid")%>"/>
		<input type="hidden" name="layer" value="<%=layer %>"/>
		<input type="text" name="name" id="name"></input><span>*</span>
	  </td>
	</tr>
	<tr>
	  <td align="right">地区性质：</td>
	  <td><input type="radio" name="gnw" value="1" checked="checked"/>国内&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="gnw" value="0"/>国外</td>
	</tr>
  </table>
</div>
<div>
	<table class="datas">
		<tr>
			<td align="center">
				<input type="button" value="提    交" onclick="addXZQH();"/>&nbsp;&nbsp;
				<input type="button" value="重    置" onclick="reset();"/>
			</td>
		</tr>				
	</table>
</div>
</form>		
</body>
</html>
