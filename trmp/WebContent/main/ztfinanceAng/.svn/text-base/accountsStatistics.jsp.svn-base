<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>应付账款统计</title>
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

	function reportsHotel(){

		var groupID = jQuery("#groupIDH").val();
		var dateB = jQuery("#dateBH").val();
		var dateE = jQuery("#dateEH").val();
		var hotelNm = jQuery("#hotelNm").val();
		//var qkzt = jQuery("input[@name=qkztH]:checked").val();取radio的取法
		var qkzt = jQuery("#qkztH").val();
		
		window.open("<%=request.getContextPath() %>/frameset?__report=report/zt/ztqdtj/ztqdHotel.rptdesign&groupID="+groupID+
			"&dateB="+dateB+"&dateE="+dateE+"&hotelNm="+hotelNm+"&qkzt="+qkzt,'obj','width=900, height=700, top=0, left=270, toolbar=no, '+
				'menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}

	function reportsVeh(){

		var groupID = jQuery("#groupIDV").val();
		var dateB = jQuery("#dateBV").val();
		var dateE = jQuery("#dateEV").val();
		var vehTeamNm = jQuery("#vehNm").val();
		//var qkzt = jQuery("input[@name=qkztV]:checked").val();
		var qkzt = jQuery("#qkztV").val();
		
		window.open("<%=request.getContextPath() %>/frameset?__report=report/zt/ztqdtj/ztqdVeh.rptdesign&groupID="+groupID+
			"&dateB="+dateB+"&dateE="+dateE+"&vehTeamNm="+vehTeamNm+"&qkzt="+qkzt,'obj','width=900, height=700, top=0, left=270, toolbar=no, '+
					'menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}

	function reportsTicket(){

		var groupID = jQuery("#groupIDT").val();
		var dateB = jQuery("#dateBT").val();
		var dateE = jQuery("#dateET").val();
		var ticketNm = jQuery("#ticketNm").val();
		//var qkzt = jQuery("input[@name=qkztT]:checked").val();
		var qkzt = jQuery("#qkztT").val();
		
		window.open("<%=request.getContextPath() %>/frameset?__report=report/zt/ztqdtj/ztqdTicket.rptdesign&groupID="+groupID+
			"&dateB="+dateB+"&dateE="+dateE+"&ticketNm="+ticketNm+"&qkzt="+qkzt,'obj','width=900, height=700, top=0, left=270, toolbar=no, '+
						'menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}

	function reportsCT(){

		var groupID = jQuery("#groupIDC").val();
		var dateB = jQuery("#dateBC").val();
		var dateE = jQuery("#dateEC").val();
		var ctNm = jQuery("#ctNm").val();
		//var qkzt = jQuery("input[@name=qkztC]:checked").val();
		var qkzt = jQuery("#qkztC").val();
		
		window.open("<%=request.getContextPath() %>/frameset?__report=report/zt/ztqdtj/ztqdCt.rptdesign&groupID="+groupID+
			"&dateB="+dateB+"&dateE="+dateE+"&ctNm="+ctNm+"&qkzt="+qkzt,'obj','width=900, height=700, top=0, left=270, toolbar=no, '+
							'menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}

	function reportsJD(){

		var groupID = jQuery("#groupIDS").val();
		var dateB = jQuery("#dateBS").val();
		var dateE = jQuery("#dateES").val();
		var sceneryNm = jQuery("#sceneryNm").val();
		//var qkzt = jQuery("input[@name=qkztS]:checked").val();
		var qkzt = jQuery("#qkztS").val();
		
		window.open("<%=request.getContextPath() %>/frameset?__report=report/zt/ztqdtj/ztqdScenery.rptdesign&groupID="+groupID+
			"&dateB="+dateB+"&dateE="+dateE+"&sceneryNm="+sceneryNm+"&qkzt="+qkzt,'obj','width=900, height=700, top=0, left=270, toolbar=no, '+
								'menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}

	function reportsNextTra(){

		var groupID = jQuery("#groupIDN").val();
		var dateB = jQuery("#dateBN").val();
		var dateE = jQuery("#dateEN").val();
		var name = jQuery("#nextTraNm").val();
		//var qkzt = jQuery("input[@name=qkztS]:checked").val();
		var qkzt = jQuery("#qkztN").val();
		
		window.open("<%=request.getContextPath() %>/frameset?__report=report/zt/ztqdtj/ztqdNextTra.rptdesign&groupID="+groupID+
			"&dateB="+dateB+"&dateE="+dateE+"&traNm="+name+"&qkzt="+qkzt,'obj','width=900, height=700, top=0, left=270, toolbar=no, '+
								'menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
</script>
</head>
<body>
<div id="tabs">
	<ul>
		<li><a href="#tabs-1">酒店应付账款统计</a></li>
		<li><a href="#tabs-2">车队应付账款统计</a></li>
		<li><a href="#tabs-3">票务应付账款统计</a></li>
		<li><a href="#tabs-4">餐厅应付账款统计</a></li>
		<li><a href="#tabs-5">景点应付账款统计</a></li>
		<li><a href="#tabs-6">地接社应付账款统计</a></li>
	</ul>

	<div id="tabs-1">
	  <table class="datas" >
		<tr>
		  <td>团开始日期：</td>
		  <td><input type="text" id="dateBH" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td>团结束日期：</td>
		  <td><input type="text" id="dateEH" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		</tr>
		<tr>
		  <td>团&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
		  <td><input type="text" id="groupIDH"></input></td>
		  <td>酒店名称：</td>
		  <td><input type="text" id="hotelNm"></input></td>
		</tr>
		<tr>
		  <td>清款状态：</td>
		  <td>
		  	<select id="qkztH">
		  	  <option value="">***请选择***</option>
		  	  <option value="Y">已清款</option>
		  	  <option value="N">未清款</option>
		  	</select>
		  </td>
		  <td></td>
		  <td></td>
		</tr>
		<tr>
			<td colspan="4" align="center"><a href="###" onclick="reportsHotel();"><img alt="搜索" src="<%=request.getContextPath()%>/images/search.gif"/></a></td>
		</tr>
	  </table>
	</div>
	
	
	<div id="tabs-2">
	  <table class="datas" width="90%">
		<tr>
		  <td>团开始日期：</td>
		  <td><input type="text" id="dateBV" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td>团结束日期：</td>
		  <td><input type="text" id="dateEV" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		</tr>
		<tr>
		  <td>团&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
		  <td><input type="text" id="groupIDV"></input></td>
		  <td>车队名称：</td>
		  <td><input type="text" id="vehNm"></input></td>
		</tr>
		<tr>
		  <td>清款状态：</td>
		  <td>
		  	<select id="qkztV">
		  	  <option value="">***请选择***</option>
		  	  <option value="Y">已清款</option>
		  	  <option value="N">未清款</option>
		  	</select>
		  </td>
		  <td></td>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" align="center"><a href="###" onclick="reportsVeh();"><img alt="搜索" src="<%=request.getContextPath()%>/images/search.gif"/></a></td>
		</tr>
	  </table>
	</div>
	
	
	<div id="tabs-3">
	  <table class="datas" width="90%">
		<tr>
		  <td>团开始日期：</td>
		  <td><input type="text" id="dateBT" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td>团结束日期：</td>
		  <td><input type="text" id="dateET" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		</tr>
		<tr>
		  <td>团&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
		  <td><input type="text" id="groupIDT"></input></td>
		  <td>代购点名称：</td>
		  <td><input type="text" id="ticketNm"></input></td>
		</tr>
		<tr>
		  <td>清款状态：</td>
		  <td>
			<select id="qkztT">
		  	  <option value="">***请选择***</option>
		  	  <option value="Y">已清款</option>
		  	  <option value="N">未清款</option>
		  	</select>
		  </td>
		  <td></td>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" align="center"><a href="###" onclick="reportsTicket();"><img alt="搜索" src="<%=request.getContextPath()%>/images/search.gif"/></a></td>
		</tr>
	  </table>
	</div>
	
	
	<div id="tabs-4">
	  <table class="datas" width="90%">
		<tr>
		  <td>团开始日期：</td>
		  <td><input type="text" id="dateBC" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td>团结束日期：</td>
		  <td><input type="text" id="dateEC" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		</tr>
		<tr>
		  <td>团&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
		  <td><input type="text" id="groupIDC"></input></td>
		  <td>餐厅名称：</td>
		  <td><input type="text" id="ctNm"></input></td>
		</tr>
		<tr>
		  <td>清款状态：</td>
		  <td>
		  	<select id="qkztC">
		  	  <option value="">***请选择***</option>
		  	  <option value="Y">已清款</option>
		  	  <option value="N">未清款</option>
		  	</select>
		  </td>
		  <td></td>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" align="center"><a href="###" onclick="reportsCT();"><img alt="搜索" src="<%=request.getContextPath()%>/images/search.gif"/></a></td>
		</tr>
	  </table>
	</div>
	
	
	<div id="tabs-5">
	  <table class="datas" width="90%">
		<tr>
		  <td>团开始日期：</td>
		  <td><input type="text" id="dateBS" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td>团结束日期：</td>
		  <td><input type="text" id="dateES" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		</tr>
		<tr>
		  <td>团&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
		  <td><input type="text" id="groupIDS"></input></td>
		  <td>景点名称：</td>
		  <td><input type="text" id="sceneryNm"></input></td>
		</tr>
		<tr>
		  <td>清款状态：</td>
		  <td>
		  	<select id="qkztS">
		  	  <option value="">***请选择***</option>
		  	  <option value="Y">已清款</option>
		  	  <option value="N">未清款</option>
		  	</select>
		  </td>
		  <td></td>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" align="center"><a href="###" onclick="reportsJD();"><img alt="搜索" src="<%=request.getContextPath()%>/images/search.gif"/></a></td>
		</tr>
	  </table>
	</div>
	<div id="tabs-6">
	  <table class="datas" width="90%">
		<tr>
		  <td>团开始日期：</td>
		  <td><input type="text" id="dateBN" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td>团结束日期：</td>
		  <td><input type="text" id="dateEN" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		</tr>
		<tr>
		  <td>团&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
		  <td><input type="text" id="groupIDN"></input></td>
		  <td>地接社名称：</td>
		  <td><input type="text" id="nextTraNm"></input></td>
		</tr>
		<tr>
		  <td>清款状态：</td>
		  <td>
		  	<select id="qkztN">
		  	  <option value="">***请选择***</option>
		  	  <option value="Y">已清款</option>
		  	  <option value="N">未清款</option>
		  	</select>
		  </td>
		  <td></td>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" align="center"><a href="###" onclick="reportsNextTra();"><img alt="搜索" src="<%=request.getContextPath()%>/images/search.gif"/></a></td>
		</tr>
	  </table>
	</div>
	</div>
<div class="showReport" id="reports" >

</div><!-- End demo-description -->
</body>
</html>