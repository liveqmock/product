<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import=" java.sql.Blob"%>
<%@page import="java.io.InputStreamReader"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>团队信息修改</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
function showInfo(index,csName){//判断交通工具显示 航班号,车次 接送时间
	if(index!=1&&index!=2){
		if(csName=='cfjt'){
			$(".cfjtTran").first().css('display','none');
			$(".cfjtTran").last().css('display','none');
			$(".cfjtTime").first().css('display','none');
			$(".cfjtTime").last().css('display','none');
		}else{
			$(".fhjtTran").first().css('display','none');
			$(".fhjtTran").last().css('display','none');
			$(".fhjtTime").first().css('display','none');
			$(".fhjtTime").last().css('display','none');
		}
	}else{
		if(csName=='cfjt'){
			$(".cfjtTran").first().css('display','block');
			$(".cfjtTran").last().css('display','block');
			$(".cfjtTime").first().css('display','block');
			$(".cfjtTime").last().css('display','block');
		}else{
			$(".fhjtTran").first().css('display','block');
			$(".fhjtTran").last().css('display','block');
			$(".fhjtTime").first().css('display','block');
			$(".fhjtTime").last().css('display','block');
		}
	}
}
window.onload = function(){
	<%for(int i=0;i<rd.getTableRowsCount("TA_ZT_LINEDETAI4Gs");i++){%>
		CKEDITOR.replace('XCMX'+<%=i%>);
	<%}%>
	CKEDITOR.config.toolbarStartupExpanded = false;
	CKEDITOR.config.resize_enabled = true;
};
<%
int rows1 = rd.getTableRowsCount("TA_ZT_GATHER_HISs");
%>
var arrays = new Array(<%=rows1%>);
<%
//线路的集合地点
for(int i=0;i<rd.getTableRowsCount("TA_ZT_GATHER_HISs");i++) {
	String gatherID = rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER_ID",i);
	String gather = rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER",i);
	String gatherTime = rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER_TIME",i);
	String addMCount = rd.getStringByDI("TA_ZT_GATHER_HISs","ADD_M_COUNT",i);
%>
	arrays[<%=i %>] = new Array(3);
	arrays[<%=i %>][0] = "<%=gatherID %>";
	arrays[<%=i %>][1] = "<%=gatherTime %>";
	arrays[<%=i %>][2] = "<%=addMCount %>";
	arrays[<%=i %>][3] = "<%=gather %>";
<%
}
%>
function selectChange(id){
	var value = $("#gatherID"+id).val();
	var len = arrays.length;
	for(var k=0;k<len;k++){
		if(arrays[k][0] == value){
			$("#gatherTime"+id).val(arrays[k][1]);
			$("#addMCount"+id).val(arrays[k][2]);
			$("#gather"+id).val(arrays[k][3]);
		}
	}
}

$(document).ready(function(){
	$("#DAY_COUNTS").change(function(){
		var rows = parseInt($(".XCMXType").size());
		var days = $("#DAY_COUNTS").val();
		var addRows = (days - rows) > 0 ? (days - rows) : Math.abs(days - rows);
		if((days - rows) > 0){
			for(var i=0;i<addRows;i++){

				var row = parseInt($(".XCMXType").size());
				var QJBL = Math.floor(Math.random()*1024);
				$("#xlmc").append('<tr class="XCMXType">'+
					'<td><font size="6">D'+parseInt(row+1)+'</font><input type="hidden" name="TA_ZT_LINEDETAI4G/RQ['+parseInt(row)+']" value="D'+parseInt(row+1)+'"/></td>'+
					'<td>'+
					'<textarea id="XCMX'+QJBL+'" name="TA_ZT_LINEDETAI4G/XCMX['+parseInt(row)+']" rows="0" cols="0">'+
					'</textarea>'+ 
					'</td>'+
					'<td align="center">'+
					'<input type="checkbox" name="TA_ZT_LINEDETAI4G/BREAKFAST['+parseInt(row)+']" value="Y"/>早<br>'+
					'<input type="checkbox" name="TA_ZT_LINEDETAI4G/CMEAL['+parseInt(row)+']" value="Y"/>中<br>'+
					'<input type="checkbox" name="TA_ZT_LINEDETAI4G/SUPPER['+parseInt(row)+']" value="Y"/>晚<br>'+
					'</td>'+
					'<td align="center"><input type="text" name="TA_ZT_LINEDETAI4G/ZSBZ['+parseInt(row)+']" value="" class="smallerInput"/></td>'+
					'</tr>');
				CKEDITOR.replace('XCMX'+QJBL);
				CKEDITOR.config.height =80;
				CKEDITOR.config.width =550;
				CKEDITOR.config.toolbarStartupExpanded = false;
				CKEDITOR.config.resize_enabled = false;
			}
		}else{
			for(var d=addRows;d>0;d--){
				var idx=parseInt($("tr.XCMXType").size()-1);
				$("tr.XCMXType:eq("+idx+")").remove();
			}
		}
	});


	$("#dataAddBtn").click(function(){
		var rowsSize = $(".addPriceRows").size();
		$("#store").append('<tr class="addPriceRows">'+
		'<td>'+
		  '<select name="TA_ZT_GPRICE/price_type['+parseInt(rowsSize)+']">'+
		  <%
		    for(int i=0;i<rd.getTableRowsCount("jglx");i++) {
		    	String dmz = rd.getStringByDI("jglx","dmz",i);
		  %>
		    '<option value="<%=dmz %>" <%if(i == 0){ %>selected="selected"<%} %>><%=rd.getStringByDI("jglx","dmsm1",i) %></option>'+
		  <%
		    }
		  %>
		  '</select>'+
		'</td>'+
		'<td><input name="TA_ZT_GPRICE/PRICE_TH['+parseInt(rowsSize)+']" value="" maxlength="12" class="smallInput"/></td>'+
		'<td><input name="TA_ZT_GPRICE/PRICE_ms['+parseInt(rowsSize)+']" value="" maxlength="12" class="smallInput"/></td>'+
		'<td><input name="TA_ZT_GPRICE/PRICE_zd['+parseInt(rowsSize)+']" value="" maxlength="12" class="smallInput"/></td>'+
		'<td><input name="TA_ZT_GPRICE/SINGLE_ROOM['+parseInt(rowsSize)+']" value="" maxlength="12" class="smallInput"/></td>'+
		'<td colspan="2">'+
		  '<textarea name="TA_ZT_GPRICE/REMARK['+parseInt(rowsSize)+']" rows="2" cols="40"></textarea>'+
		'</td>'+
	  '</tr>');
	});


	$("#dataDelBtn").click(function(){
		var rowsSize2 = $(".addPriceRows").size();
		rowsSize2 = rowsSize2 - 1;
		$("tr.addPriceRows:eq("+parseInt(rowsSize2)+")").remove();
	});
});

function setDates(){
	var bDate = $("#bd").val();
	var eDate = $("#ed").val();
	var dayCounts = $("#dayCounts").val();
	if(bDate == '' || eDate == '' || dayCounts == '')
		return false;
	var bd = new Date("'"+bDate+"'");
	var ed = new Date("'"+eDate+"'");
}
	
$(document).ready(function(){
	$("#jhddAddBtn").click(function(){
	var num=$(".gather").size();
	$("#gather").append('<tr class="gather"><td>'+
			'<select name="TA_ZT_GATHER/GATHERHIS_ID['+num+']" id="gatherID'+num+'" onchange="selectChange('+num+');">'+
			'<option value="-1">***请选择***</option>'+
			<%
			for(int i=0;i<rd.getTableRowsCount("TA_ZT_GATHER_HISs");i++) {
				String gather = rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER",i);
				String gatherTime = rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER_TIME",i);
			%>
			'<option value="<%=rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER_ID",i) %>"><%=gather %>---<%=gatherTime %></option>'+
			<%
			}
			%>
			'</select>'+
			'</td>'+
			'<td><input type="text" name="TA_ZT_GATHER/GATHER_TIME['+num+']" id="gatherTime'+num+'"/></td>'+
			'<td><input type="text" name="TA_ZT_GATHER/ADD_M_COUNT['+num+']" id="addMCount'+num+'" onkeydown="checkNum()"/>'+
			'<input type="hidden" name="TA_ZT_GATHER/GATHER['+num+']" id="gather'+num+'"/>'+
			'</td></tr>');
	});
	$("#jhddAddBtnDel").click(function(){
		var rowsSize2 = $(".gather").size();
		rowsSize2 = rowsSize2 - 1;
		$("tr.gather:eq("+parseInt(rowsSize2)+")").remove();
	});
});

function addTraAgcCheck(){
	var canDo = false;
	//如果门市价里有空值，则不允许提交
	$(".msj").each(function (i){
		var msj = $(".msj:eq("+i+")").val();
		if('' == msj || '0' == msj){
			canDo = true;
		}
	});
	if(canDo == true){
		alert("请输入有效的门市价格！");
		return false;
	}else{
		document.lineInfoForm.submit();
		window.parent.parent.location.reload(); 
		window.parent.parent.GB_hide();
	}
}
</script>
</head>
<body>
<form action="editGroup." name="lineInfoForm" method="post">
<input type="hidden" name="TA_ZT_GROUP/ID" value="<%=rd.getStringByDI("TA_ZT_GROUPs","id",0) %>"/>
<input type="hidden" name="TA_ZT_GROUP/ID[0]@oldValue" value="<%=rd.getStringByDI("TA_ZT_GROUPs","id",0) %>"/>

 <div class="add-table"> 
	<table border="0">
	  <tr >
		<td align="right">线路名称：</td>
  		<td colspan="3">
		  	<input type="text" style="width:370px;" value="<%=rd.getStringByDI("TA_ZT_GROUPs","XLMC",0) %>" readonly="readonly"/>
		  	<span>*</span>
	  	</td>
      	<td align="right">团号：</td>
   	  	<td>
   	  	  <input type="text" value="<%=rd.getStringByDI("TA_ZT_GROUPs","id",0) %>" readonly="readonly" />
  	    </td>
	  </tr>
	  <tr>
	   <td  align="right">国内联系电话：</td>
        <td >
        <input type="text" name="TA_ZT_GROUP/GNLXDH" value="<%=rd.getStringByDI("TA_ZT_GROUPs","GNLXDH",0) %>"/>
        </td>
        <td  align="right">机场联系电话：</td>
        <td >
        <input type="text" name="TA_ZT_GROUP/JCLXDH" value="<%=rd.getStringByDI("TA_ZT_GROUPs","JCLXDH",0) %>"/>
        </td>
  	  	<td  align="right">天数：</td>
        <td >
        <input type="text" name="TA_ZT_GROUP/DAYS" value="<%=rd.getStringByDI("TA_ZT_GROUPs","DAYS",0) %>" id="DAY_COUNTS" />
        </td>
	  </tr>
	  <tr>
		 <td  align="right">开始时间：</td>
        <td >
        	<input type="text" name="TA_ZT_GROUP/BEGIN_DATE" value="<%=rd.getStringByDI("TA_ZT_GROUPs","begin_date",0) %>" 
        		onFocus="WdatePicker({isShowClear:false,readOnly:true});" onblur="setDates();" id="bd"/>
        </td>
        <td align="right">结束时间：</td>
        <td >
        	<input type="text" name="TA_ZT_GROUP/END_DATE" value="<%=rd.getStringByDI("TA_ZT_GROUPs","end_DATE",0) %>" 
        		onFocus="WdatePicker({isShowClear:false,readOnly:true});" onblur="setDates();" id="ed"/>
        </td>
        <td align="right">退改规则：</td>
        <td >
   		  <input type="text" name="TA_ZT_GROUP/RETURN_ROLE" id="RETURN_ROLE" value="<%=rd.getStringByDI("TA_ZT_GROUPs","RETURN_ROLE",0) %>" />
     	</td>	 
	  </tr>
	  <tr>
	  	 <td  align="right">是否自备车：</td>
       	 <td>
        	<%
        	String VEHICLE_TYPE = rd.getStringByDI("TA_ZT_GROUPs","VEHICLE_TYPE",0);
        	if("1".equals(VEHICLE_TYPE)){
        	%>
        		<input type="radio" name="TA_ZT_GROUP/VEHICLE_TYPE" value="1" checked="checked"/>是
	        	<input type="radio" name="TA_ZT_GROUP/VEHICLE_TYPE" value="2" />否
        	<%
        	}else if("2".equals(VEHICLE_TYPE)){%>
			  	<input type="radio" name="TA_ZT_GROUP/VEHICLE_TYPE" value="1" />是
	        	<input type="radio" name="TA_ZT_GROUP/VEHICLE_TYPE" value="2" checked="checked"/>否
        	<%
        	}else{ %>
        		<input type="radio" name="TA_ZT_GROUP/VEHICLE_TYPE" value="1" />是
	        	<input type="radio" name="TA_ZT_GROUP/VEHICLE_TYPE" value="2" checked="checked"/>否
        	<%
        	}%>
		</td>
		  <td align="right">加点购物：</td>
	       	<td>
	       	<%
	       	String jd = rd.getStringByDI("TA_ZT_GROUPs","jd",0);
	       	String gw = rd.getStringByDI("TA_ZT_GROUPs","gw",0);
	       	%>
	   		  <input type="checkbox" name="TA_ZT_GROUP/JD" value="1" <%if("1".equals(jd)){ %> checked="checked" <%} %>/>加点
	   		  <input type="checkbox" name="TA_ZT_GROUP/GW" value="1" <%if("1".equals(gw)){ %> checked="checked" <%} %>/>购物
   		</td>
	  	<td  align="right">游客类型：</td>
        <td >
        	<%
        	String yklx =rd.getStringByDI("TA_ZT_GROUPs","TOURIST_TYPE",0);
        	if("1".equals(yklx)){
        	%>
        	<input type="radio" name="TA_ZT_GROUP/TOURIST_TYPE" value="1" checked="checked"/>团队
        	<input type="radio" name="TA_ZT_GROUP/TOURIST_TYPE" value="2" />散客
        	<%
        	}else if("2".equals(yklx)){
        	%>
        	<input type="radio" name="TA_ZT_GROUP/TOURIST_TYPE" value="1" />团队
        	<input type="radio" name="TA_ZT_GROUP/TOURIST_TYPE" value="2" checked="checked"/>散客
        	<%
        	}else{%>
        	<input type="radio" name="TA_ZT_GROUP/TOURIST_TYPE" value="1" checked="checked"/>团队
        	<input type="radio" name="TA_ZT_GROUP/TOURIST_TYPE" value="2" />散客
        	<%
        	}%>
        </td>
	  </tr>
	   <tr>
   		<td align="right">可收客人数：</td>
        <td >
        	<input type="text" name="TA_ZT_GROUP/maxPersonCount" value="<%=rd.getStringByDI("TA_ZT_GROUPs","maxPersonCount",0) %>" maxlength="11"/>
        </td>
   	    <td align="right">可成团人数：</td>
      	<td><input type="text" name="TA_ZT_GROUP/minPersonCount" value="<%=rd.getStringByDI("TA_ZT_GROUPs","minPersonCount",0) %>"/></td>
		<td  align="right">保险：</td>
       	<td >
   		  <input type="text" name="TA_ZT_GROUP/INSURANCE" id="INSURANCE" value="<%=rd.getStringByDI("TA_ZT_GROUPs","INSURANCE",0) %>"/>
   		</td>
	  </tr>
	  <tr>
	  	<td  align="right">出发交通：</td>
        <td class="cfjt">
	  		<select name="TA_ZT_GROUP/B_TRAFFIC" onchange="showInfo(this.value,'cfjt');">
	  		<%
	  		String BT = rd.getStringByDI("TA_ZT_GROUPs","B_TRAFFIC",0);
	  		if("".equals(BT)){
	  			for(int i=0;i<rd.getTableRowsCount("JTGJ");i++){
	  		%>
	  			<option value="<%=rd.getStringByDI("JTGJ","dmz",i) %>"><%=rd.getStringByDI("JTGJ","dmsm1",i) %></option>
	  		<%		
	  			}
	  		}else{
	  			for(int i=0;i<rd.getTableRowsCount("JTGJ");i++){
		    		String dmz = rd.getStringByDI("JTGJ","dmz",i);
		  		%>
		    	  <option value="<%=dmz %>" <%if(dmz.equals(BT)){ %> selected="selected" <%} %>><%=rd.getStringByDI("JTGJ","dmsm1",i) %></option>
		  		<%
		    	}
	  		}
	  		%>
	  	  </select>
	  	</td>
	  	<td  align="right" class="cfjtTran" style="display:<%if("1".equals(BT)||"2".equals(BT)) {%>block<%}else{ %>none<%} %>">
	    航班号/车次： 
	  </td>
	  <td class="cfjtTran" style="display:<%if("1".equals(BT)||"2".equals(BT)) {%>block<%}else{ %>none<%} %>">
	    <input type="text" name="TA_ZT_GROUP/CFHBCC" value="<%=rd.getString("TA_ZT_GROUPs","CFHBCC",0) %>" />
	</td>
	<td  align="right" class="cfjtTime" style="display:<%if("1".equals(BT)||"2".equals(BT)) {%>block<%}else{ %>none<%} %>">
	   出发时间： 
	  </td>
	  <td class="cfjtTime" style="display:<%if("1".equals(BT)||"2".equals(BT)) {%>block<%}else{ %>none<%} %>">
	    <input type="text" name="TA_ZT_GROUP/CFSJ" value="<%=rd.getString("TA_ZT_GROUPs","CFSJ",0) %>" onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});"/>
	</td>
	  </tr>
	  <tr>
	  	<td align="right">返回交通：</td>
       	 <td class="fhjt">
   		  <select name="TA_ZT_GROUP/E_TRAFFIC" onchange="showInfo(this.value,'fhjt');">
	  		<%
	  		String ET = rd.getStringByDI("TA_ZT_GROUPs","E_TRAFFIC",0);
   		    if("".equals(ET)){
   		    	
   		    	for(int i=0;i<rd.getTableRowsCount("JTGJ");i++){
   		  		%>
   		    	  <option value="<%=rd.getStringByDI("JTGJ","dmz",i) %>"><%=rd.getStringByDI("JTGJ","dmsm1",i) %></option>
   		  		<%
   		    	}
   		    }else{
   		  		
		    	for(int i=0;i<rd.getTableRowsCount("JTGJ");i++){
		    		String dmz = rd.getStringByDI("JTGJ","dmz",i);
		  		%>
		    	  <option value="<%=dmz %>" <%if(dmz.equals(ET)){ %> selected="selected" <%} %>><%=rd.getStringByDI("JTGJ","dmsm1",i) %></option>
		  		<%
		    	}
   		    }
	  		%>
	  	  </select>
   		</td>
   		<td align="right" class="fhjtTran" style="display:<%if("1".equals(ET)||"2".equals(ET)) {%>block<%}else{ %>none<%} %>">
	   航班号/车次： 
	  </td>
	  <td class="fhjtTran" style="display:<%if("1".equals(ET)||"2".equals(ET)) {%>block<%}else{ %>none<%} %>">
	    <input type="text" name="TA_ZT_GROUP/FHHBCC" value="<%=rd.getString("TA_ZT_GROUPs","FHHBCC",0) %>" />
	</td>
	<td  align="right" class="fhjtTime" style="display:<%if("1".equals(ET)||"2".equals(ET)) {%>block<%}else{ %>none<%} %>">
	   返回时间： 
	  </td>
	  <td class="fhjtTime" style="display:<%if("1".equals(ET)||"2".equals(ET)) {%>block<%}else{ %>none<%} %>">
	    <input type="text" name="TA_ZT_GROUP/FHSJ" value="<%=rd.getString("TA_ZT_GROUPs","FHSJ",0) %>" onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});"/>
	</td>
	  </tr>
	</table>
</div>
<!-- 
<div style="text-align: right"> 
	<a href="###" id="jhddAddBtn"><img alt="添加集合地点" src="<%=request.getContextPath() %>/images/add.gif"> 添加</a>&nbsp;&nbsp;
    <a href="###" id="jhddAddBtnDel"><img alt="删除集合地点" src="<%=request.getContextPath() %>/images/del.gif"> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div> 
-->
<div id="list-table">
	<table width="100%" class="datas" style="text-align: center" id="gather">
		<tr id="first-tr">
			<td width="70%">集合地点</td>
			<td width="18%">集合时间</td>
			<td width="9%">加价</td>
		</tr>
	<%
	//线路的集合地点
	for(int i=0;i<rd.getTableRowsCount("TA_ZT_GATHERs");i++) {
		String gatherID = rd.getStringByDI("TA_ZT_GATHERs","GATHER_ID",i);
		String gather = rd.getStringByDI("TA_ZT_GATHERs","gather",i);
		String gatherTime = rd.getStringByDI("TA_ZT_GATHERs","GATHER_TIME",i);
		String addMCount = rd.getStringByDI("TA_ZT_GATHERs","ADD_M_COUNT",i);
		String hisID = rd.getStringByDI("TA_ZT_GATHERs","GATHERHIS_ID",i);
	%>
		<tr class="gather">
			<td  >
			<select name="TA_ZT_GATHER/GATHERHIS_ID[<%=i %>]" id="gatherID<%=i %>" onchange="selectChange('<%=i %>');">
				<option value="-1">***请选择***</option>
			<%
			//所有集合地点
			for(int j=0;j<rd.getTableRowsCount("TA_ZT_GATHER_HISs");j++) {
				String gatherH = rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER",j);
				String gatherTimeH = rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER_TIME",j);
				String gatherIdOfAll = rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER_ID",j);
				
			%>
			  <option value="<%=gatherIdOfAll %>" <%if(hisID.equals(gatherIdOfAll)){ %>selected="selected" <%} %> ><%=gatherH %>---<%=gatherTimeH %></option>
			<%	
			}
			%>
			</select>
			</td>
			<td  ><input type="text" name="TA_ZT_GATHER/GATHER_TIME[<%=i %>]" value="<%=gatherTime %>" id="gatherTime<%=i %>" readonly="readonly"/></td>
			<td  >
			<input type="text" name="TA_ZT_GATHER/ADD_M_COUNT[<%=i %>]" value="<%=addMCount.equals("")?"0":addMCount %>" id="addMCount<%=i %>" onkeydown="checkNum()"/>
			<input type="hidden" name="TA_ZT_GATHER/GATHER[<%=i %>]" id="gather<%=i %>" value="<%=gather %>"/>
			</td>
		</tr>
	<%
	}%>
	</table>
</div>
<div style="text-align: right"> 
  <a id="dataAddBtn" href="###"><img alt="添加" src="<%=request.getContextPath() %>/images/add.gif"> 添加</a>&nbsp;&nbsp;
 <a id="dataDelBtn" href="###"><img alt="删除" src="<%=request.getContextPath() %>/images/del.gif"> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div> 
<div id="list-table">
<table class="datas" id="store">
  <tr id="first-tr">
	<td width="15%">价格类型</td>
    <td width="10%">同行价</td>
    <td width="10%">门市价</td>
    <td width="10%">最低价</td>
    <td width="10%">单房差</td>
    <td width="45%">备注</td>
  </tr>
<%
for(int k=0;k<rd.getTableRowsCount("TA_ZT_GPRICEs");k++){
	
	String id = rd.getStringByDI("TA_ZT_GPRICEs","LINE_PRICE_ID",k);
	String priceTypes = rd.getStringByDI("TA_ZT_GPRICEs","price_type",k);
	String priceTH = rd.getStringByDI("TA_ZT_GPRICEs","price_th",k);
	String priceMS = rd.getStringByDI("TA_ZT_GPRICEs","price_ms",k);
	String priceZD = rd.getStringByDI("TA_ZT_GPRICEs","price_zd",k);
	String REMARK = rd.getStringByDI("TA_ZT_GPRICEs","REMARK",k);
	String SINGLE_ROOM = rd.getStringByDI("TA_ZT_GPRICEs","SINGLE_ROOM",k);
%>
  <tr class="addPriceRows">
	<td id="price_th">
	  <select name="TA_ZT_GPRICE/price_type[<%=k %>]">
	  <%
	    for(int i=0;i<rd.getTableRowsCount("jglx");i++){
	    	String dmz = rd.getStringByDI("jglx","dmz",i);
	  %>
	    <option value="<%=dmz %>" <%if(priceTypes.equals(dmz)){ %>selected="selected"<%} %>><%=rd.getStringByDI("jglx","dmsm1",i) %></option>
	  <%
	    }
	  %>
	  </select>
	  
	</td>
	<td><input name="TA_ZT_GPRICE/PRICE_TH[<%=k %>]" value="<%=priceTH %>" maxlength="12" id="price_th[<%=k %>]" class="smallInput" ></input></td>
	<td><input name="TA_ZT_GPRICE/PRICE_ms[<%=k %>]" value="<%=priceMS %>" maxlength="12" id="msj<%=k %>" class="smallInput msj"></input></td>
	<td><input name="TA_ZT_GPRICE/PRICE_zd[<%=k %>]" value="<%=priceZD %>" maxlength="12" class="smallInput"></input></td>
	<td><input name="TA_ZT_GPRICE/SINGLE_ROOM[<%=k %>]" class="smallInput" onkeydown="checkNum()" value='<%=SINGLE_ROOM %>'></td>
	<td >
	  <textarea name="TA_ZT_GPRICE/REMARK[<%=k %>]" rows="2" cols="40"><%=REMARK %></textarea>
	</td>
  </tr>
<%

}%>
</table>
</div>
<div class="add-table">
<table class="datas" id="xlmc">
  <tr id="first-tr">
  	<td align="center" width="8%">天数</td>
	<td align="center" width="74%">行程明细</td>
	<td align="center" width="7%">用餐</td>
	<td align="center" width="10%">住宿</td>
  </tr>
<%
for(int i=0;i<rd.getTableRowsCount("TA_ZT_LINEDETAI4Gs");i++){
	
	String id = rd.getString("TA_ZT_LINEDETAI4Gs","tid",String.valueOf(i));
	String rq = rd.getString("TA_ZT_LINEDETAI4Gs","rq",String.valueOf(i));
	String breakfast = rd.getString("TA_ZT_LINEDETAI4Gs","BREAKFAST",String.valueOf(i));
	String cmeal = rd.getString("TA_ZT_LINEDETAI4Gs","CMEAL",String.valueOf(i));
	String supper = rd.getString("TA_ZT_LINEDETAI4Gs","SUPPER",String.valueOf(i));
	String zsbz = rd.getString("TA_ZT_LINEDETAI4Gs","ZSBZ",String.valueOf(i));
	
%>
  <tr class="XCMXType">
	<td><font size="6"><%=rq %></font>
	  <input type="hidden" name="TA_ZT_LINEDETAI4G/RQ[<%=i %>]" value="<%=rq %>"/>
	  <input type="hidden" name="TA_ZT_LINEDETAI4G/id[<%=i %>]" value="<%=id %>"/>
	</td>
	<td>
	  <textarea id="XCMX<%=i %>" name="TA_ZT_LINEDETAI4G/XCMX[<%=i %>]" rows="0" cols="0">
		<%
		Object obj = rd.get("TA_ZT_LINEDETAI4Gs", "XCMX",String.valueOf(i));
		Blob blob = null;
		int read=0;
		if(null != obj){
			blob = (Blob) obj;
			java.io.InputStream in=blob.getBinaryStream();
			InputStreamReader isr = new InputStreamReader(in, "GBK");
			char[] chars = new char[1];
			read = 0;
			while ((read = isr.read(chars)) != -1) {
				out.print(new String(chars));
			}
		}
		%>
	  </textarea> 
	</td>
	<td align="center">
	  <input type="checkbox" name="TA_ZT_LINEDETAI4G/BREAKFAST[<%=i %>]" value="Y" <%if(breakfast.equals("Y")){ %>checked="checked"<%} %>/>早<br>
	  <input type="checkbox" name="TA_ZT_LINEDETAI4G/CMEAL[<%=i %>]" value="Y" <%if(cmeal.equals("Y")){ %>checked="checked"<%} %>/>中<br>
	  <input type="checkbox" name="TA_ZT_LINEDETAI4G/SUPPER[<%=i %>]" value="Y" <%if(supper.equals("Y")){ %>checked="checked"<%} %>/>晚<br>
	</td>
	<td align="center">
	  <input type="text" name="TA_ZT_LINEDETAI4G/ZSBZ[<%=i %>]" value="<%=zsbz %>" class="smallerInput"/>
	</td>
  </tr>
  <%}%>
</table>
</div>
<div>
<table>
 <tr>
  	<td align="center">备注：</td>
  	<td colspan="3">
  		<textarea name="TA_ZT_GROUP/COMMENTS" rows="" cols="110"><%=rd.getStringByDI("TA_ZT_GROUPs","COMMENTS",0) %></textarea>
  	</td>
  </tr>
</table>
</div>
<div align="center" id="last-sub">
<%
String isClose = rd.getStringByDI("TA_ZT_GROUPs","FTZT",0);
%>
	<input type="checkbox" name="isClose" value="0" <%if(isClose.equals("0")){ %>checked="checked"<%} %>/>是否封团
	<input type="button" id="button" value="提交" onclick="addTraAgcCheck();" />
	<input type="button" id="button" value="返 回" onclick="window.history.go(-1)"/>
	<input type="button" id="button" value="关闭" onclick="javascript:window.parent.parent.GB_hide();"/>
</div>
</form>
</body>
</html>