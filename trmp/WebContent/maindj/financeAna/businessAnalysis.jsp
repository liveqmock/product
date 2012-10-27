<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>业务量统计分析</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/treeAndAllCss.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script src="<%=request.getContextPath() %>/jqueryui/jqueryui.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>

<script type="text/javascript">
	$(function() {
		$("#tabs").tabs();
	});

	function reportsCount(){

		var dateB = jQuery("#dateB").val();
		var dateE = jQuery("#dateE").val();
		var userno = jQuery("#userno").val();
		if(dateB == '' || dateE == ''){
			alert("请选择一个有效的时间段！");
			return false;
		}
		window.open("<%=request.getContextPath() %>/frameset?__report=report/dj/profit/businessAnalysis.rptdesign"+
				"&tybd="+dateB+"&tyed="+dateE+"&userno="+userno,'obj','width=900, height=700, top=0, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
		
	}
</script>
</head>
<body>
<div id="tabs">
	<ul>
		<li><a href="#tabs-1">业务量统计分析</a></li>
	</ul>
	<div id="tabs-1">
	  <table class="datas" width="90%">
		<tr>
		  <td>团开始日期：</td>
		  <td><input type="text" id="dateB" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td>团结束日期：</td>
		  <td><input type="text" id="dateE" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		</tr>
		<tr>
		  <td>业务员名称：</td>
		  <td>
  	<select id="userno" name="" >
  	<option value="" >***请选择***</option>
  			<%
					for(int j=0;j<rd.getTableRowsCount("getBusinessName");j++){
					String no = rd.getString("getBusinessName","userno",j);
					String name = rd.getString("getBusinessName","username",j);				
			%>
	<option value="<%=no %>"  ><%=name %></option>
	<%
	} %>
	</select>
		  </td>
		  <td></td>
		  <td></td>
		</tr>
		<tr>
			<td colspan="4" align="center"><a href="###" onclick="reportsCount();"><img alt="搜索" src="<%=request.getContextPath()%>/images/search.gif"/></a></td>
		</tr>
	  </table>
	</div>
</div>

</body>
</html>