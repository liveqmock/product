<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
 <%@include file="/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>财务状况一览表</title>
<link href="<%=request.getContextPath() %>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<title>财务状况一览表</title>
<script type="text/javascript">


function findByLike(){

	var dateB = jQuery("#bDate").val();
	var dateE = jQuery("#eDate").val();

	if(dateB == '' || dateE == ''){
		alert("请选择一个有效的时间段！");
		return false;
	}
	window.open("<%=request.getContextPath() %>/frameset?__report=report/dj/profit/profitOfGroup.rptdesign"+
			"&bDate="+dateB+"&eDate="+dateE,'obj','width=900, height=700, top=0, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	
}
</script>
</head>

<body>
<form name="dj_guide_form" method="post">
<div id="lable"><span>财务状况一览表</span></div>
<div  id="thisSelect-table" >
	<table>
		<tr>
			<td rowspan="3" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td align="right">开始时间：</td>
			<td><input type="text" name="bDate" id="bDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>

			<td>结束时间：</td>
			<td><input type="text" name="eDate" id="eDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
			<td><a href="###" onclick="findByLike();"><img alt="搜索" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>

</form>
</body>
</html>