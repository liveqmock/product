<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>批发商财务统计</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/treeAndAllCss.css" type="text/css" /> 
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script src="<%=request.getContextPath() %>/jqueryui/jqueryui.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>


<style type="text/css">
	body{margin: 0;padding: 0;font-size: 12px;}
	#top{background: url(<%=request.getContextPath()%>/images/ToolBarBg.gif) repeat-x;height: 35px;text-align: center;line-height: 35px;}
	#print{background: url(<%=request.getContextPath()%>/images/Print.gif) no-repeat 0 7px;width: 55px;text-indent: 15px;float: left;cursor: pointer;}
	#word{background: url(<%=request.getContextPath()%>/images/word.gif) no-repeat 0 7px;width: 82px;text-indent: 15px;float: left;cursor: pointer;}
	#close{background: url(<%=request.getContextPath()%>/images/printClose.gif) no-repeat 0 11px;width: 49px;text-indent: 10px;float: left;cursor: pointer;}
	#center-top{background: url(<%=request.getContextPath()%>/images/body_04.jpg) no-repeat;width: 793px;height: 33px;text-align: center}
	#center-cen{background: url(<%=request.getContextPath()%>/images/body_05.jpg) repeat-y;width: 793px;}
	#button{background: url(<%=request.getContextPath()%>/images/body_06.jpg) no-repeat;width: 793px;}
</style> 
<script type="text/javascript">
	$(function() {
		$("#tabs").tabs();
	});

	function reportsOrder(){

		var groupID = jQuery("#groupID").val();
		var dateB = jQuery("#dateB").val();
		var dateE = jQuery("#dateE").val();
		var lineNm = jQuery("#lineNm").val();
		var qkzt = jQuery("#qkzt").val();
		var dzzt = jQuery("#dzzt").val();
		var mdNm = jQuery("#mdNm").val();
		var ddh = jQuery("#ddh").val();
		var pfsNm = jQuery("#pfsNm").val();
		
		
		window.open("<%=request.getContextPath() %>/frameset?__report=report/zt/ztorder/ztwholesalersSettlement.rptdesign"+
				"&groupID="+groupID+"&dateB="+dateB+"&dateE="+dateE+"&dzzt="+dzzt+"&qkzt="+qkzt+"&pfsNm="+pfsNm+"&ddh="+ddh+"&mdNm="+mdNm+"&lineNm="+lineNm,'obj','width=900, height=700, top=0, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
		
	}
</script>
</head>
<body>
<div id="tabs">
	<ul>
		<li><a href="#tabs-1">供应商结算统计</a></li>
	</ul>
	<div id="tabs-1">
	  <table class="datas" width="90%">
		<tr>
		  <td>开始日期：</td>
		  <td><input type="text" id="dateB" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td>结束日期：</td>
		  <td><input type="text" id="dateE" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		</tr>
		<tr>
		  <td>团&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
		  <td><input type="text" id="groupID"></input></td>
		  		  <td>订&nbsp;单&nbsp;号：</td>
		  <td><input type="text" id="ddh"></input></td>

		</tr>
		<tr>
		<td>对账状态：</td>
		  <td>
		  	<select id="dzzt">
		  	  <option value="">***请选择***</option>
		  	  <option value="0">未对帐</option>
		  	  <option value="1">已对帐</option>
		  	  <option value="2">对帐未通过</option>
		  	</select>
		  </td>
		  <td>门&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;店：</td>
		  <td>
		  <input type="text" id="mdNm"></input>
		  </td>
			
		</tr>
		<tr>
				  		<td>结算状态：</td>
		  <td>
		  	<select id="qkzt">
		  	  <option value="">***请选择***</option>
		  	  <option value="Y">已结算</option>
		  	  <option value="N">未结算</option>
		  	</select>
		  	 </td>
		  			  <td>线路名称：</td>
		  <td><input type="text" id="lineNm"></input></td>

		</tr>
		<tr>
			<td>供&nbsp;应&nbsp;商：</td>
			<td><input type="text" id="pfsNm"></td>
					 
		<td colspan="2" align="center">
				<a href="###" onclick="reportsOrder();"><img alt="搜索" src="<%=request.getContextPath()%>/images/search.gif"/></a>
			</td>
		</tr>
	  </table>
	</div>
</div>

<div class="showReport" id="reports" >

</div><!-- End demo-description -->
</body>
</html>