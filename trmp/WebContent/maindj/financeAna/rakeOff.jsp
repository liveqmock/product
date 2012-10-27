<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>回佣统计</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css" type="text/css">

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/treeAndAllCss.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script src="<%=request.getContextPath()%>/jqueryui/ui/jquery.ui.core.js"></script>
<script src="<%=request.getContextPath()%>/jqueryui/ui/jquery.ui.widget.js"></script>
<script src="<%=request.getContextPath()%>/jqueryui/ui/jquery.ui.tabs.js"></script>

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
		var qkzt = jQuery("input[@name=qkztH]:checked").val();

		var obj=jQuery.ajax({url:"<%=request.getContextPath() %>/preview?__report=report/dj/qdtj/qdHotel.rptdesign&groupID="+groupID+
			"&dateB="+dateB+"&dateE="+dateE+"&hotelNm="+hotelNm+"&qkzt="+qkzt+"&__isnull=groupID&__isnull=dateB&__isnull=dateE&__isnull=hotelNm",
			async:true,
			dataType:"html",
			cache:false,
			success:function(data){
				jQuery("#reports").html(data);
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
	            alert(errorThrown); 
	   	 	}
		});
	}

	function reportsVeh(){

		var groupID = jQuery("#groupIDV").val();
		var dateB = jQuery("#dateBV").val();
		var dateE = jQuery("#dateEV").val();
		var vehTeamNm = jQuery("#vehNm").val();
		var qkzt = jQuery("input[@name=qkztV]:checked").val();
		
		var obj=jQuery.ajax({url:"<%=request.getContextPath() %>/preview?__report=report/dj/qdtj/qdVeh.rptdesign&groupID="+groupID+
			"&dateB="+dateB+"&dateE="+dateE+"&vehTeamNm="+vehTeamNm+"&qkzt="+qkzt+"&__isnull=groupID&__isnull=dateB&__isnull=dateE&__isnull=vehTeamNm",
			async:true,
			dataType:"html",
			cache:false,
			success:function(data){
				jQuery("#reports").html(data);
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
	            alert(errorThrown); 
	   	 	}
		});
	}

	function reportsTicket(){

		var groupID = jQuery("#groupIDT").val();
		var dateB = jQuery("#dateBT").val();
		var dateE = jQuery("#dateET").val();
		var ticketNm = jQuery("#ticketNm").val();
		var qkzt = jQuery("input[@name=qkztT]:checked").val();
		
		var obj=jQuery.ajax({url:"<%=request.getContextPath() %>/preview?__report=report/dj/qdtj/qdTicket.rptdesign&groupID="+groupID+
			"&dateB="+dateB+"&dateE="+dateE+"&ticketNm="+ticketNm+"&qkzt="+qkzt+"&__isnull=groupID&__isnull=dateB&__isnull=dateE&__isnull=ticketNm",
			async:true,
			dataType:"html",
			cache:false,
			success:function(data){
				jQuery("#reports").html(data);
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
	            alert(errorThrown); 
	   	 	}
		});
	}

</script>
</head>
<body>
<div id="tabs" class="add-table">
	<ul>
		<li><a href="#tabs-1">景点返佣统计</a></li>
		<li><a href="#tabs-2">机票返佣统计</a></li>
		<li><a href="#tabs-3">购物返佣统计</a></li>
	</ul>
	<div id="tabs-1">
	  <table class="datas" width="90%">
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
		  <td><input type="radio" name="qkztH" value="Y"></input>已清款&nbsp;&nbsp;&nbsp;&nbsp;
		  	  <input type="radio" name="qkztH" value="N" checked="checked"></input>未清款
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
		  <td><input type="radio" name="qkztV" value="Y"></input>已清款&nbsp;&nbsp;&nbsp;&nbsp;
		  	  <input type="radio" name="qkztV" value="N" checked="checked"></input>未清款
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
		  <td><input type="radio" name="qkztT" value="Y"></input>已清款&nbsp;&nbsp;&nbsp;&nbsp;
		  	  <input type="radio" name="qkztT" value="N" checked="checked"></input>未清款
		  </td>
		  <td></td>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" align="center"><a href="###" onclick="reportsTicket();"><img alt="搜索" src="<%=request.getContextPath()%>/images/search.gif"/></a></td>
		</tr>
	  </table>
	</div>
</div>

<div class="showReport" id="reports" >

</div><!-- End demo-description -->
</body>
</html>