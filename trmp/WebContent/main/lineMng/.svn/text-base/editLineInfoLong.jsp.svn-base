<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="java.sql.Blob"%>
<%@page import="java.io.InputStreamReader"%>
<%@include file="../../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
<html>
<head>
<%
int iRows = rd.getTableRowsCount("TA_ZT_INSURANCEs"); 
int weekRows = rd.getTableRowsCount("TA_ZT_FBJH_TMPs");
int priceRows = rd.getTableRowsCount("TA_ZT_LINE_PRICESs");
String roleID = sd.getString("USERROLEST");
boolean isTrue = false;
if(!"".equals(roleID)){
	
	roleID = roleID.substring(1,roleID.length()-1);
	String[] roleIDs = roleID.split(",");
	for(int i=0;i<roleIDs.length;i++){
		if("admin".equals(roleIDs[i].trim()) || "transfer".equals(roleIDs[i].trim())){
			isTrue = true;
			break;
		}
	}
}

String canEdit = rd.getString("canEdit");
%>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>修改长线线路信息</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/menuList.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/menuList.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
<script language="javascript">
$(function(){
	$("#insuranceList select").change(function(){
	    var val=$(this).val();
	    var val1 = $(this).attr("id").split('-')[1];
	    $("#bxid"+val1).val(val.split('-')[0]);
        $("#bxmc"+val1).val(val.split('-')[1]);
        $("#bxcb"+val1).val(val.split('-')[2]);
        $("#jyjg"+val1).val(val.split('-')[3]);
        if(val.split('-')[4]==1){
        	$("#cbfs"+val1).text("按天数计算");
        }else{
        	$("#cbfs"+val1).text("按实际成本计算");
        }
	    $("#insuranceList select[id!="+$(this).attr("id")+"]").each(function(){
	          if(val==$(this).val()&&'9999'!=val)
	          {
	                alert("该保险已经存在！请重新选择");
	                $("#select-"+val1+"-").val('9999');
	                $("#bxid"+val1).val('');
	                $("#bxmc"+val1).val('');
	                $("#bxcb"+val1).val('');
	                $("#jyjg"+val1).val('');
	                $("#cbfs"+val1).text('');
	          }
	    });
	});

	$(".insuranceAdd").click(function(){
		var dRows = $(".insurances").size();
		$("#insuranceList").append('<tr class="insurances" id="insurances'+dRows+'">'+
				'<td>'+
				'<img src="<%=request.getContextPath()%>/images/printClose.gif" class="delInsurances" onclick="delInsurances('+dRows+');"/>'+
				'<select class="changeInsurance" id="select-'+dRows+'-">'+
				 '<option value="9999">--未指定--</option>'+
					<%
					for(int i = 0; i< iRows; i++){
						String bxId = rd.getStringByDI("TA_ZT_INSURANCEs", "ID", i);
						String bxMc = rd.getStringByDI("TA_ZT_INSURANCEs", "BXLBMC", i);
						String bxCb = rd.getStringByDI("TA_ZT_INSURANCEs", "BXCB", i);
						String jyJg = rd.getStringByDI("TA_ZT_INSURANCEs", "JYJG", i);
						String cbFs = rd.getStringByDI("TA_ZT_INSURANCEs", "CBFS", i);
					%>
						'<option value="<%=bxId%>-<%=bxMc%>-<%=bxCb%>-<%=jyJg%>-<%=cbFs%>"><%=bxMc %></option>'+
					<%
					}
					%>
				'</select>'+
				'<input type="hidden" name="TA_ZT_INSURANCE/ID['+dRows+']" id="bxid'+dRows+'" /></td>'+
				'<td><input type="text"  id="bxcb'+dRows+'"  readonly="readonly"/></td>'+
				'<td><input type="text"  id="jyjg'+dRows+'"  readonly="readonly"/></td>'+
				'<td id="cbfs'+dRows+'"></td>'+
			'</tr>');
		$("#select-"+dRows+"-").bind("change", function(){
		 	var val=$(this).val();
		    var val1 = $(this).attr("id").split('-')[1];
		    $("#bxid"+val1).val(val.split('-')[0]);
	        $("#bxmc"+val1).val(val.split('-')[1]);
	        $("#bxcb"+val1).val(val.split('-')[2]);
	        $("#jyjg"+val1).val(val.split('-')[3]);
	        if(val.split('-')[4]==1){
	        	$("#cbfs"+val1).text("按天数计算");
	        }else{
	        	$("#cbfs"+val1).text("按实际成本计算");
	        }
	        $("#insuranceList select[id!="+$(this).attr("id")+"]").each(function(){
		          if(val==$(this).val()&&'9999'!=val)
		          {
		                alert("该保险已经存在！请重新选择");
		                $("#select-"+val1+"-").val('9999');
		                $("#bxid"+val1).val('');
		                $("#bxmc"+val1).val('');
		                $("#bxcb"+val1).val('');
		                $("#jyjg"+val1).val('');
		                $("#cbfs"+val1).text('');
		          }
		    });
	});
	
});
	$("#insuranceDel").click(function(){
		var iRows = $(".insurances").size()-1;
		$(".insurances:eq("+parseInt(iRows)+")").remove();
	});
});
	function delInsurances(nRows){
		$("#insurances"+nRows).remove();	
	}
	
$(document).ready(function(){//初始化装载所有富文本域
	CKEDITOR.replace('XCMX1');
	CKEDITOR.replace('XCMX2');
	CKEDITOR.replace('XCMX3');
	CKEDITOR.replace('XCMX4');
	CKEDITOR.replace('XCMX5');
	CKEDITOR.replace('XCMX6');
	CKEDITOR.replace('XCMX7');
	CKEDITOR.replace('XCMX8');
	CKEDITOR.replace('XCMX9');
	

	<%
	int XCMXRow = rd.getTableRowsCount("TA_ZT_LINEDETAILs");
	for(int i = 0; i < XCMXRow; i++){
	%>
	CKEDITOR.replace('XCMX<%=i+10%>');
	<%
	}
	%>
	
	//CKEDITOR.config.height = 80;
	//CKEDITOR.config.width = 690;
	CKEDITOR.config.toolbarStartupExpanded = false;
	CKEDITOR.config.resize_enabled = true;
});
function showInfo(index,csName){//判断交通工具显示 航班号,车次 接送时间
	if(index!=1&&index!=2){
		if(csName=='cfjt'){
			$(".cfjtTran").first().css('display','none');
			$(".cfjtTran").last().css('display','none');
			$(".cfjtTime").first().css('display','none');
			$(".cfjtTime").last().css('display','none');
			$(".cfjtTranInput").val('');
			$(".cfjtTimeInput").val('');
		}else{
			$(".fhjtTran").first().css('display','none');
			$(".fhjtTran").last().css('display','none');
			$(".fhjtTime").first().css('display','none');
			$(".fhjtTime").last().css('display','none');
			$(".fhjtTimeInput").val('');
			$(".fhjtTranInput").val('');
		}
	}else{
		if(csName=='cfjt'){
			$(".cfjtTran").first().css('display','block');
			$(".cfjtTran").last().css('display','block');
			$(".cfjtTime").first().css('display','block');
			$(".cfjtTime").last().css('display','block');
			$(".cfjtTranInput").val('');
			$(".cfjtTimeInput").val('');
		}else{
			$(".fhjtTran").first().css('display','block');
			$(".fhjtTran").last().css('display','block');
			$(".fhjtTime").first().css('display','block');
			$(".fhjtTime").last().css('display','block');
			$(".fhjtTimeInput").val('');
			$(".fhjtTranInput").val('');
		}
	}
}

<%
int rows1 = rd.getTableRowsCount("TA_ZT_GATHER_HISs");
%>
var arrays = new Array(<%=rows1%>);
<%
for(int i=0;i<rows1;i++) {
	
	String gatherID = rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER_ID",i);
	String gahter = rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER",i);
	String gatherTime = rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER_TIME",i);
	String addMC = rd.getStringByDI("TA_ZT_GATHER_HISs","ADD_M_COUNT",i);
%>
	arrays[<%=i %>] = new Array(4);
	arrays[<%=i %>][0] = "<%=gatherID %>";
	arrays[<%=i %>][1] = "<%=gatherTime %>";
	arrays[<%=i %>][2] = "<%=addMC %>";
	arrays[<%=i %>][3] = "<%=gahter %>";
				
<%
}%>
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

	function addTraAgcCheck(){
		if($("#line_name").val()==''){
			alert("线路名称不能为空");
			$("#line_name").focus();
			return false;
		}
		
	    if($("#select-condition").val() == ''){
			alert("请选择发班计划！");
			return false;
	    }
		document.lineInfoForm.submit();
//		reload(); 
	}
<%
String userType = sd.getString("userType");
if("E".equals(userType)){
%>
$(document).ready(function(){
	$("#dataAddBtn").click(function(){
		var rowsSize = $(".addPriceRows").size();
		$("#store").append('<tr class="addPriceRows">'+
		'<td>'+
		  '<select name="TA_ZT_LINE_PRICES/price_type['+parseInt(rowsSize)+']">'+
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
		'<td><input name="TA_ZT_LINE_PRICES/PRICE_TH['+parseInt(rowsSize)+']" value="" maxlength="12" class="smallInput"/></td>'+
		'<td><input name="TA_ZT_LINE_PRICES/PRICE_ms['+parseInt(rowsSize)+']" value="" maxlength="12" class="smallInput"/></td>'+
		'<td><input name="TA_ZT_LINE_PRICES/PRICE_zd['+parseInt(rowsSize)+']" value="" maxlength="12" class="smallInput"/></td>'+
		'<td><input name="TA_ZT_LINE_PRICES/SINGLE_ROOM['+parseInt(rowsSize)+']" value="" maxlength="12" class="smallInput"/></td>'+
		'<td colspan="2">'+
		  '<textarea name="TA_ZT_LINE_PRICES/REMARK['+parseInt(rowsSize)+']" rows="2" cols="40"></textarea>'+
		'</td>'+
	  '</tr>');
	});
	

	$("#dataDelBtn").click(function(){
		var rowsSize2 = $(".addPriceRows").size();
		rowsSize2 = rowsSize2 - 1;
		$("tr.addPriceRows:eq("+parseInt(rowsSize2)+")").remove();
	});
});
<%
}else{ %>
$(document).ready(function(){
	$("#dataAddBtn").click(function(){
		var rowsSize = $(".addPriceRows").size();
		$("#store").append('<tr class="addPriceRows">'+
				'<td>'+
				  '<select name="TA_ZT_LINE_PRICES/price_type['+rowsSize+']">'+
				  <%
				    for(int i=0;i<rd.getTableRowsCount("jglx");i++){
				  %>
				    '<option value="<%=rd.getStringByDI("jglx","dmz",i) %>" <%if(i == 2){ %>selected="selected"<%} %>><%=rd.getStringByDI("jglx","dmsm1",i) %></option>'+
				  <%
				    }
				  %>
				  '</select>'+
				'</td>'+
				'<td><input name="TA_ZT_LINE_PRICES/PRICE_TH['+rowsSize+']" maxlength="12"></input></td>'+
				'<td><input name="TA_ZT_LINE_PRICES/SINGLE_ROOM['+rowsSize+']" class="smallInput" onkeydown="checkNum()"></td>'+
				'<td colspan="2">'+
				  '<textarea name="TA_ZT_LINE_PRICES/REMARK['+rowsSize+']" rows="2" cols="40"></textarea>'+
				'</td>'+
			  '</tr>');
	});
	$("#dataDelBtn").click(function(){
		var rowsSize2 = $(".addPriceRows").size();
		rowsSize2 = rowsSize2 - 1;
		$("tr.addPriceRows:eq("+parseInt(rowsSize2)+")").remove();
	});
});
<%
}
%>
	
	function showplan(num){
		if(num == ''){
			$("#plan-data1").css('display','none');
			$("#plan-data2").css('display','none');
			$("#cycle").attr("name","other");
			$("#bDate").attr("name","other");
			$("#eDate").attr("name","other");
		}else if(num==1){
			$("#plan-data1").css('display','block');
			$("#plan-data2").css('display','block');
			$("#bDate").attr("name","b_date");
			$("#eDate").attr("name","e_date");
			$("#cycle").each(function(i){
				$(this).attr("name","cycle/week["+i+"]");
			});
		}else if(num==2){
			$("#plan-data1").css('display','block');
			$("#plan-data2").css('display','none');
			$("#cycle").attr("name","other");
			$("#bDate").attr("name","b_date");
			$("#eDate").attr("name","e_date");
			
		}
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
						'<td><font size="6">D'+parseInt(row+1)+'</font><input type="hidden" name="TA_ZT_LINEDETAIL/RQ['+parseInt(row)+']" value="'+parseInt(row+1)+'"/></td>'+
						'<td>'+
						'<textarea id="XCMX'+QJBL+'" name="TA_ZT_LINEDETAIL/XCMX['+parseInt(row)+']" rows="0" cols="0">'+
						'</textarea>'+ 
						'</td>'+
						'<td align="center">'+
						'<input type="checkbox" name="TA_ZT_LINEDETAIL/BREAKFAST['+parseInt(row)+']" value="Y"/>早<br>'+
						'<input type="checkbox" name="TA_ZT_LINEDETAIL/CMEAL['+parseInt(row)+']" value="Y"/>中<br>'+
						'<input type="checkbox" name="TA_ZT_LINEDETAIL/SUPPER['+parseInt(row)+']" value="Y"/>晚<br>'+
						'</td>'+
						'<td align="center"><input type="text" name="TA_ZT_LINEDETAIL/ZSBZ['+parseInt(row)+']" value="" class="smallerInput"/></td>'+
						'</tr>');
					CKEDITOR.config.height =80;
					CKEDITOR.config.width =690;
					CKEDITOR.config.toolbarStartupExpanded = false;
					CKEDITOR.config.resize_enabled = true;
					CKEDITOR.replace('XCMX'+QJBL);
				}
			}else{
				for(var d=addRows;d>0;d--){
					var idx=parseInt($("tr.XCMXType").size()-1);
					$("tr.XCMXType:eq("+idx+")").remove();
				}
			}
		});
	});
</script>
</head>
<body>
<form action="update." name="lineInfoForm" method="post">
<input type="hidden" name="TA_ZT_LINEMNG/LINE_ID" value="<%=rd.getStringByDI("TA_ZT_LINEMNGs","LINE_ID",0) %>"/>
<input type="hidden" name="TA_ZT_LINEMNG/LINE_ID[0]@oldValue" value="<%=rd.getStringByDI("TA_ZT_LINEMNGs","LINE_ID",0) %>"/>
<input type="hidden" name="canEdit" value="<%=canEdit %>"/>
<div id="top-select">
<div class="select-div" onclick="addTraAgcCheck();">
  <span id="ok-icon"></span> 
  <span class="text">提交</span>
</div>
<div class="select-div" onclick="javascript:reset();">
  <span id="reset-icon"></span> 
  <span	class="text">重置</span>
</div>
<div class="select-div" onclick="reload();">
  <span id="close-icon"></span> 
  <span	class="text">关闭</span>
</div>
	<span class="tishi">带<font color="red">*</font>号为必填项</span>
</div>
 <div class="add-table"> 
	<table border="0">
	  <tr >
		<td align="right">线路名称：</td>
  		<td colspan="6">
		  	<input type="text" name="TA_ZT_LINEMNG/line_name" id="line_name" style="width:500px;" value="<%=rd.getString("TA_ZT_LINEMNGs","LINE_NAME",0) %>"/>
		  	<span>*</span>
		  	<!-- <span onclick="showAllLine();" class="showPointer" style="text-decoration: underline">选择与历史相似线路</span> -->
	  	</td>
	  </tr>
	  <tr>
      	<td align="right">组团社名称：</td>
       	<td>
       	  <input type="text" name="TA_ZT_LINEMNG/SZLXSMC" value="<%=rd.getString("TA_ZT_LINEMNGs","SZLXSMC",0) %>" readonly="readonly" />
       	  <input type="hidden" name="TA_ZT_LINEMNG/orgid" value="<%=rd.getString("TA_ZT_LINEMNGs","orgid",0) %>" readonly="readonly" />
       	</td>
		<td align="right">国内联系电话：</td>
		<td ><input type="text" name="TA_ZT_LINEMNG/GNLXDH" value="<%=rd.getString("TA_ZT_LINEMNGs","GNLXDH",0) %>"/></td>
		<td align="right">机场联系电话：</td>
		<td><input type="text" name="TA_ZT_LINEMNG/JCLXDH" value="<%=rd.getString("TA_ZT_LINEMNGs","JCLXDH",0) %>"/></td>
	  </tr>
	  <tr>
	  	<td align="right">线路状态：</td>
       	<td>
       		<select name="TA_ZT_LINEMNG/LINE_STATE" >
		  		<%
		  		String lineState = rd.getStringByDI("TA_ZT_LINEMNGs","LINE_STATE",0);
		  		if(!"".equals(lineState)){
			    	for(int i=0;i<rd.getTableRowsCount("XLZT");i++){
			  		%>
			    	  <option value="<%=rd.getStringByDI("XLZT","dmz",i) %>" <%if(rd.getStringByDI("XLZT","dmz",i).equals("1")){ out.print("selected");} %>><%=rd.getStringByDI("XLZT","dmsm1",i) %></option>
			  		<%
			    	}
		  		}else{
		  			for(int i=0;i<rd.getTableRowsCount("XLZT");i++) {
			    		String dmz = rd.getStringByDI("XLZT","dmz",i);
		  		%>
		  		<option value="<%=dmz %>" <%if(dmz.equals("1")){ %>selected="selected"<%} %>><%=rd.getStringByDI("XLZT","dmsm1",i) %></option>
		  		<%  }
		  		}
		  		%>
	   		</select>
       </td>
	  <td  align="right">线路类型：</td>
        <td >
		  <select name="TA_ZT_LINEMNG/LINE_TYPE">
	  		<%
	  		String lineType2 = rd.getStringByDI("TA_ZT_LINEMNGs","LINE_TYPE",0);
		    for(int i=0;i<rd.getTableRowsCount("xllx");i++){
		    		String dmz = rd.getStringByDI("xllx","dmz",i);
		  		%>
		    	  	<option value="<%=dmz %>" <%if(lineType2.equals(dmz)){ %>selected="selected" <%} %>><%=rd.getStringByDI("xllx","dmsm1",i) %></option>
		    	<%
		    }
	  		%>
	  	  </select>
		</td>
	  	<td  align="right">线路区域：</td>
        <td >
		  <select name="TA_ZT_LINEMNG/XLQY">
	  		<%
	  		String XLQY = rd.getStringByDI("TA_ZT_LINEMNGs","XLQY",0);
				for (int i = 0; i < rd.getTableRowsCount("xzqhs"); i++) {
					String dmz = rd.getStringByDI("xzqhs", "id", i);
			%>
				<option value="<%=dmz%>" <%if(XLQY.equals(dmz)){ %>selected="selected" <%} %>>
					<%=rd.getStringByDI("Xzqhs", "name", i)%>
				</option>
			<%
				}
			%>
	  	  </select>
		</td>
	  </tr>
	  <tr>
	 	 <td  align="right">加点购物：</td>
       	 <td>
   		  <input type="checkbox" name="TA_ZT_LINEMNG/JD" value="1" <%if("1".equals(rd.getString("TA_ZT_LINEMNGs","JD",0))){ %> checked="checked" <%} %>/>加点
   		  <input type="checkbox" name="TA_ZT_LINEMNG/GW" value="1" <%if("1".equals(rd.getString("TA_ZT_LINEMNGs","GW",0))){ %> checked="checked" <%} %>/>购物
   		 </td>
	 	 <td  align="right"><!-- 是否自备车： --></td>
         <td>
        	<%-- <%
        	if("1".equals(rd.getString("TA_ZT_LINEMNGs","SFZBC",0))){
        	%>
        		<input type="radio" name="TA_ZT_LINEMNG/SFZBC" value="1" checked="checked"/>是
	        	<input type="radio" name="TA_ZT_LINEMNG/SFZBC" value="2" />否
        	<%
        	}else if("2".equals(rd.getString("TA_ZT_LINEMNGs","SFZBC",0))){%>
			  	<input type="radio" name="TA_ZT_LINEMNG/SFZBC" value="1" />是
	        	<input type="radio" name="TA_ZT_LINEMNG/SFZBC" value="2" checked="checked"/>否
        	<%
        	}else{ %>
        		<input type="radio" name="TA_ZT_LINEMNG/SFZBC" value="1" />是
	        	<input type="radio" name="TA_ZT_LINEMNG/SFZBC" value="2" checked="checked"/>否
        	<%
        	}%> --%>
		 </td>
      	
	  	<td  align="right"><!-- 游客类型： --></td>
        <td >
        	<%-- <%
        	if("1".equals(rd.getString("TA_ZT_LINEMNGs","YKLX",0))){
        	%>
        	<input type="radio" name="TA_ZT_LINEMNG/YKLX" value="1" checked="checked"/>团队
        	<input type="radio" name="TA_ZT_LINEMNG/YKLX" value="2" />散客
        	<%
        	}else if("2".equals(rd.getString("TA_ZT_LINEMNGs","YKLX",0))){
        	%>
        	<input type="radio" name="TA_ZT_LINEMNG/YKLX" value="1" />团队
        	<input type="radio" name="TA_ZT_LINEMNG/YKLX" value="2" checked="checked"/>散客
        	<%
        	}else{%>
        	<input type="radio" name="TA_ZT_LINEMNG/YKLX" value="1" checked="checked"/>团队
        	<input type="radio" name="TA_ZT_LINEMNG/YKLX" value="2" />散客
        	<%
        	}%> --%>
        </td>
        
	  </tr>
	  <tr>
	  	<td  align="right">退改规则：</td>
        <td >
   		  <input type="text" name="TA_ZT_LINEMNG/RETURN_ROLE" id="RETURN_ROLE" value="<%=rd.getString("TA_ZT_LINEMNGs","RETURN_ROLE",0) %>"/>
     	</td>
      	<td  align="right">保险：</td>
       	<td >
   		  <input type="text" name="TA_ZT_LINEMNG/INSURANCE" id="INSURANCE" value="<%=rd.getString("TA_ZT_LINEMNGs","INSURANCE",0) %>"/>
   		</td>
   		<td align="right">发班计划：</td>
  		<td>
	  	  <select name="TA_ZT_LINEMNG/PLAN" id="select-condition" onchange="showplan(this.value)">
	  		<option value="" selected="selected">***请选择***</option>
	  		<%
	  		String PLAN = rd.getStringByDI("TA_ZT_LINEMNGs","PLAN",0);
	  		if("".equals(PLAN)){
	  			for(int i=0;i<rd.getTableRowsCount("FBJH");i++){
	  				String dmz = rd.getStringByDI("FBJH","dmz",i);
	  		%>
	  			<option value="<%=rd.getStringByDI("FBJH","dmz",i) %>" <%if(dmz.equals("1")){ %> selected="selected" <%} %>><%=rd.getStringByDI("FBJH","dmsm1",i) %></option>
	  		<%		
	  			}
	  		}else{
	  			for(int i=0;i<rd.getTableRowsCount("FBJH");i++){
		    		String dmz = rd.getStringByDI("FBJH","dmz",i);
		  		%>
		    	  <option value="<%=dmz %>" <%if(dmz.equals(PLAN)){ %> selected="selected" <%} %>><%=rd.getStringByDI("FBJH","dmsm1",i) %></option>
		  		<%
		    	}
	  		}
	  		%>
	  	  </select><span>*</span>
	  	</td>
	  </tr>
	  
	  <tr>
	  	<td align="right">可收客人数：</td>
	    <td >
	     	<input type="text" name="TA_ZT_LINEMNG/maxPersonCount" maxlength="11" id="maxPersonCount" value="<%=rd.getString("TA_ZT_LINEMNGs","MAXPERSONCOUNT",0) %>"/>
	     </td>
		<td align="right">可成团人数：</td>
	   	<td>
	   		<input type="text" name="TA_ZT_LINEMNG/minPersonCount" id="minPersonCount" value="<%=rd.getString("TA_ZT_LINEMNGs","MINPERSONCOUNT",0) %>"/>
	   	</td>
		<td  align="right">天数：</td>
	    <td >
	     <input type="text" name="TA_ZT_LINEMNG/DAY_COUNTS" id="DAY_COUNTS" value="<%=rd.getString("TA_ZT_LINEMNGs","DAY_COUNTS",0) %>"/>
	    </td>
	  </tr>
	  
	  
	  
	  <tr id="cfjt">
  	  	<td  align="right" >出发交通：</td>
        <td class="cfjt">
	  		<select name="TA_ZT_LINEMNG/B_TRAFFIC" onchange="showInfo(this.value,'cfjt');">
	  		<%
	  		String BT = rd.getStringByDI("TA_ZT_LINEMNGs","B_TRAFFIC",0);
	  		if("".equals(BT)){
	  			for(int i=0;i<rd.getTableRowsCount("JTGJ");i++){
	  				String dmz = rd.getStringByDI("JTGJ","dmz",i);
	  		%>
	  			<option value="<%=rd.getStringByDI("JTGJ","dmz",i) %>" <%if(dmz.equals("1")){ %> selected="selected" <%} %>><%=rd.getStringByDI("JTGJ","dmsm1",i) %></option>
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
	    <input type="text" name="TA_ZT_LINEMNG/CFHBCC" class="cfjtTranInput" value="<%=rd.getString("TA_ZT_LINEMNGs","CFHBCC",0) %>" />
	</td>
	<td  align="right" class="cfjtTime" style="display:<%if("1".equals(BT)||"2".equals(BT)) {%>block<%}else{ %>none<%} %>">
	   出发时间： 
	  </td>
	  <td class="cfjtTime" style="display:<%if("1".equals(BT)||"2".equals(BT)) {%>block<%}else{ %>none<%} %>">
	    <input type="text" name="TA_ZT_LINEMNG/CFSJ" class="cfjtTimeInput" value="<%=rd.getString("TA_ZT_LINEMNGs","CFSJ",0) %>" onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});"/>
	</td>
	  </tr>
	  <tr id="fhjt">
	  	<td align="right" >返回交通：</td>
       	 <td class="fhjt">
   		  <select name="TA_ZT_LINEMNG/E_TRAFFIC"  onchange="showInfo(this.value,'fhjt');">
   		    <%
	  		String ET = rd.getStringByDI("TA_ZT_LINEMNGs","E_TRAFFIC",0);
   		    if("".equals(BT)){
   		    	
   		    	for(int i=0;i<rd.getTableRowsCount("JTGJ");i++){
   		    		String dmz = rd.getStringByDI("JTGJ","dmz",i);
   		  		%>
   		    	  <option value="<%=dmz %>" <%if(dmz.equals("1")){ %> selected="selected" <%} %>><%=rd.getStringByDI("JTGJ","dmsm1",i) %></option>
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
	    <input type="text" name="TA_ZT_LINEMNG/FHHBCC" class="fhjtTranInput" value="<%=rd.getString("TA_ZT_LINEMNGs","FHHBCC",0) %>" />
	</td>
	<td  align="right" class="fhjtTime" style="display:<%if("1".equals(ET)||"2".equals(ET)) {%>block<%}else{ %>none<%} %>">
	   返回时间： 
	  </td>
	  <td class="fhjtTime" style="display:<%if("1".equals(ET)||"2".equals(ET)) {%>block<%}else{ %>none<%} %>">
	    <input type="text" name="TA_ZT_LINEMNG/FHSJ" class="fhjtTimeInput" value="<%=rd.getString("TA_ZT_LINEMNGs","FHSJ",0) %>" onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});"/>
	</td>
	  </tr>
	</table>
</div>
<div class="add-table"> 
<table class="datas" width="100%">
  <tr id="first-tr">
  	<td colspan="8">发班计划时间详细</td>
  </tr>
  <tr id="plan-data1" <%if(weekRows > 0){ %>style="display: block;" <%}else{ %>style="display: none"<%} %>>
  	<td align="right">开始日期：</td>
  	<td colspan="3"><input type="text" name="b_date" value="<%=rd.getStringByDI("TA_ZT_FBJH_TMPs","b_date",0) %>" onFocus="WdatePicker({isShowClear:false,readOnly:true});" <%if("0".equals(canEdit)){ %> readonly="readonly" <%} %> id="bDate"/></td>
  	<td align="right">结束日期：</td>
  	<td colspan="3"><input type="text" name="e_date" value="<%=rd.getStringByDI("TA_ZT_FBJH_TMPs","e_date",0) %>" onFocus="WdatePicker({isShowClear:false,readOnly:true});" <%if("0".equals(canEdit)){ %> readonly="readonly" <%} %> id="eDate"/></td>
  </tr>
  <tr id="plan-data2" <%if("1".equals(rd.getString("TA_ZT_LINEMNGs","PLAN",0)) && weekRows > 0){ %>style="display: block;" <%}else{ %>style="display: none"<%} %>>
  	<td align="right">周期：</td>
  	<td><input type="checkbox" name="cycle/week[0]" value="1" <%for(int i=0;i<weekRows;i++){ String weeks = rd.getStringByDI("TA_ZT_FBJH_TMPs","weeks",i);if("1".equals(weeks)){%> checked="checked" <% break;} } %>id="cycle" <%if("0".equals(canEdit)){ %> readonly="readonly" <%} %>/>星期日</td>
  	<td><input type="checkbox" name="cycle/week[1]" value="2" <%for(int i=0;i<weekRows;i++){ String weeks = rd.getStringByDI("TA_ZT_FBJH_TMPs","weeks",i);if("2".equals(weeks)){%> checked="checked" <% break;} } %>id="cycle" <%if("0".equals(canEdit)){ %> readonly="readonly" <%} %>/>星期一</td>
  	<td><input type="checkbox" name="cycle/week[2]" value="3" <%for(int i=0;i<weekRows;i++){ String weeks = rd.getStringByDI("TA_ZT_FBJH_TMPs","weeks",i);if("3".equals(weeks)){%> checked="checked" <% break;} } %>id="cycle" <%if("0".equals(canEdit)){ %> readonly="readonly" <%} %>/>星期二</td>
  	<td><input type="checkbox" name="cycle/week[3]" value="4" <%for(int i=0;i<weekRows;i++){ String weeks = rd.getStringByDI("TA_ZT_FBJH_TMPs","weeks",i);if("4".equals(weeks)){%> checked="checked" <% break;} } %>id="cycle" <%if("0".equals(canEdit)){ %> readonly="readonly" <%} %>/>星期三</td>
  	<td><input type="checkbox" name="cycle/week[4]" value="5" <%for(int i=0;i<weekRows;i++){ String weeks = rd.getStringByDI("TA_ZT_FBJH_TMPs","weeks",i);if("5".equals(weeks)){%> checked="checked" <% break;} } %>id="cycle" <%if("0".equals(canEdit)){ %> readonly="readonly" <%} %>/>星期四</td>
  	<td><input type="checkbox" name="cycle/week[5]" value="6" <%for(int i=0;i<weekRows;i++){ String weeks = rd.getStringByDI("TA_ZT_FBJH_TMPs","weeks",i);if("6".equals(weeks)){%> checked="checked" <% break;} } %>id="cycle" <%if("0".equals(canEdit)){ %> readonly="readonly" <%} %>/>星期五</td>
  	<td><input type="checkbox" name="cycle/week[6]" value="7" <%for(int i=0;i<weekRows;i++){ String weeks = rd.getStringByDI("TA_ZT_FBJH_TMPs","weeks",i);if("7".equals(weeks)){%> checked="checked" <% break;} } %>id="cycle" <%if("0".equals(canEdit)){ %> readonly="readonly" <%} %>/>星期六</td>
  </tr>
</table>
</div>


<div style="text-align: right"> 
		<a href="###" class="insuranceAdd"><img alt="添加保险" src="<%=request.getContextPath() %>/images/add.gif"/> 添加</a>&nbsp;&nbsp;
       <a href="###" id="insuranceDel"><img alt="添加保险" src="<%=request.getContextPath() %>/images/del.gif"/> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     </div>
	<table width="100%" class="datas" style="text-align: center" id="insuranceList">
		<tr id="first-tr">
			<td width="25%">保险名称</td>
			<td width="25%">保险成本</td>
			<td width="25%">建议价格</td>
			<td width="25%">成本方式</td>
		</tr>
		<%
		int lRows = rd.getTableRowsCount("TA_ZT_LINEANDINSURANCEs");
		for(int i = 0; i < lRows; i++){
			int sId = 9999;
			String inId = rd.getStringByDI("TA_ZT_LINEANDINSURANCEs", "INSURANCEID", i);
		%>
		<tr class="insurances" id="insurances<%=i%>">
			<td>
			<img src="<%=request.getContextPath()%>/images/printClose.gif" class="delInsurances" onclick="delInsurances(<%=i%>);"/>
			<select class="changeInsurance" id="select-<%=i%>-">
			 <option value="9999">--未指定--</option>
				<%
				for(int j = 0; j< iRows; j++){
					String bxId = rd.getStringByDI("TA_ZT_INSURANCEs", "ID", j);
					String bxMc = rd.getStringByDI("TA_ZT_INSURANCEs", "BXLBMC", j);
					String bxCb = rd.getStringByDI("TA_ZT_INSURANCEs", "BXCB", j);
					String jyJg = rd.getStringByDI("TA_ZT_INSURANCEs", "JYJG", j);
					String cbFs = rd.getStringByDI("TA_ZT_INSURANCEs", "CBFS", j);
					if(bxId.equals(inId)){
						sId = j;
				%>
					<option value="<%=bxId%>-<%=bxMc%>-<%=bxCb%>-<%=jyJg%>-<%=cbFs%>" selected="selected"><%=bxMc %></option>
				<%
					}else{
				%>
					<option value="<%=bxId%>-<%=bxMc%>-<%=bxCb%>-<%=jyJg%>-<%=cbFs%>"><%=bxMc %></option>
				<%} }%>
			</select>
				<input type="hidden" name="TA_ZT_INSURANCE/ID[<%=i%>]" value="<%=rd.getStringByDI("TA_ZT_INSURANCEs", "ID", sId)%>" id="bxid<%=i%>" /></td>
				<td><input type="text"  id="bxcb<%=i%>" value="<%=rd.getStringByDI("TA_ZT_INSURANCEs", "bxCb", sId) %>" readonly="readonly"/></td>
				<td><input type="text"  id="jyjg<%=i%>" value="<%=rd.getStringByDI("TA_ZT_INSURANCEs", "jyjg", sId) %>" readonly="readonly"/></td>
				<td id="cbfs<%=i%>"><%if("1".equals(rd.getStringByDI("TA_ZT_INSURANCEs", "cbfs", sId))){out.print("按天计算");}else{out.print("按实际成本计算");} %></td>
		</tr>
		<% }%>
	</table> 




	<div style="text-align: right"> 
		<a href="###" id="jhddAddBtn"><img alt="添加集合地点" src="<%=request.getContextPath() %>/images/add.gif"> 添加</a>&nbsp;&nbsp;
       <a href="###" id="jhddAddBtnDel"><img alt="删除集合地点" src="<%=request.getContextPath() %>/images/del.gif"> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     </div>
	<table width="100%" class="datas" style="text-align: center" id="gather">
		<tr id="first-tr">
			<td>集合地点</td>
			<td>集合时间</td>
			<td>加价</td>
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
			<td>
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
			<td><input type="text" name="TA_ZT_GATHER/GATHER_TIME[<%=i %>]" value="<%=gatherTime %>" id="gatherTime<%=i %>" /></td>
			<td>
			<input type="text" name="TA_ZT_GATHER/ADD_M_COUNT[<%=i %>]" value="<%=addMCount.equals("")?"0":addMCount %>" id="addMCount<%=i %>" onkeydown="checkNum()"/>
			<input type="hidden" name="TA_ZT_GATHER/GATHER[<%=i %>]" id="gather<%=i %>" value="<%=gather %>"/>
			</td>
		</tr>
	<%
	}%>
	</table>

<div style="text-align: right"> 
 <!-- <a href="###" onclick="addNewRow();"><img alt="添加" src="<%=request.getContextPath() %>/images/add.gif"> 添加</a>&nbsp;&nbsp;
 <a href="###" onclick="delTabRow('store',1,'price_th');"><img alt="删除" src="<%=request.getContextPath() %>/images/del.gif"> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
 <a id="dataAddBtn" href="###"><img alt="添加" src="<%=request.getContextPath() %>/images/add.gif"> 添加</a>&nbsp;&nbsp;
 <a id="dataDelBtn" href="###"><img alt="删除" src="<%=request.getContextPath() %>/images/del.gif"> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div> 
<div id="list-table">
<table class="datas" id="store">
<%
if(true){
%>
  <tr id="first-tr">
	<td width="15%">价格类型</td>
    <td width="10%">同行价</td>
    <td width="10%">门市价</td>
    <td width="10%">最低价</td>
    <td width="10%">单房差</td>
    <td width="40%">备注</td>
  </tr>
<%
for(int k=0;k<priceRows;k++){
	
	String id = rd.getStringByDI("TA_ZT_LINE_PRICESs","LINE_PRICE_ID",k);
	String priceTypes = rd.getStringByDI("TA_ZT_LINE_PRICESs","price_type",k);
	String priceTH = rd.getStringByDI("TA_ZT_LINE_PRICESs","price_th",k);
	String priceMS = rd.getStringByDI("TA_ZT_LINE_PRICESs","price_ms",k);
	if("".equals(priceMS))	priceMS = "0";
	String priceZD = rd.getStringByDI("TA_ZT_LINE_PRICESs","price_zd",k);
	if("".equals(priceZD))	priceZD = "0";
	String REMARK = rd.getStringByDI("TA_ZT_LINE_PRICESs","REMARK",k);
	String singleRoom = rd.getStringByDI("TA_ZT_LINE_PRICESs","SINGLE_ROOM",k);
%>
  <tr class="addPriceRows">
	<td>
	  <select name="TA_ZT_LINE_PRICES/price_type[<%=k %>]">
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
	<td><input name="TA_ZT_LINE_PRICES/PRICE_TH[<%=k %>]" value="<%=priceTH %>" maxlength="12" id="price_th<%=priceTH %>" class="smallInput"/></td>
	<td><input name="TA_ZT_LINE_PRICES/PRICE_ms[<%=k %>]" value="<%=priceMS %>" maxlength="12" class="smallInput"/></td>
	<td><input name="TA_ZT_LINE_PRICES/PRICE_zd[<%=k %>]" value="<%=priceZD %>" maxlength="12" class="smallInput"/></td>
	<td><input name="TA_ZT_LINE_PRICES/SINGLE_ROOM[<%=k %>]" value="<%=singleRoom.equals("")?"0":singleRoom %>" class="smallInput" onkeydown="checkNum()"></td>
	<td colspan="2">
	  <textarea name="TA_ZT_LINE_PRICES/REMARK[<%=k %>]" rows="2" cols="40"><%=REMARK %></textarea>
	</td>
  </tr>
<%	}
}else{
%>
  <tr id="first-tr">
	<td width="20%">价格类型</td>
    <td width="15%">同行价</td>
    <td width="15%">单房差</td>
    <td width="50%">备注</td>
  </tr>
<%
for(int k=0;k<priceRows;k++){
	String id = rd.getStringByDI("TA_ZT_LINE_PRICESs","LINE_PRICE_ID",k);
	String priceTypes = rd.getStringByDI("TA_ZT_LINE_PRICESs","price_type",k);
	String priceTH = rd.getStringByDI("TA_ZT_LINE_PRICESs","price_th",k);
	String priceMS = rd.getStringByDI("TA_ZT_LINE_PRICESs","price_ms",k);
	if("".equals(priceMS))	priceMS = "0";
	String priceZD = rd.getStringByDI("TA_ZT_LINE_PRICESs","price_zd",k);
	if("".equals(priceZD))	priceZD = "0";
	String REMARK = rd.getStringByDI("TA_ZT_LINE_PRICESs","REMARK",k);
	String SINGLE_ROOM = rd.getStringByDI("TA_ZT_LINE_PRICESs","SINGLE_ROOM",k);
%>
  <tr class="addPriceRows">
	<td>
	  <select name="TA_ZT_LINE_PRICES/price_type[<%=k %>]">
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
	<td><input name="TA_ZT_LINE_PRICES/PRICE_TH[<%=k %>]" value="<%=priceTH %>" maxlength="12" id="price_th<%=priceTH %>"></input></td>
	<td><input name="TA_ZT_LINE_PRICES/SINGLE_ROOM[<%=k %>]" class="smallInput" onkeydown="checkNum()" value="<%=SINGLE_ROOM.equals("0")?"":SINGLE_ROOM %>"></td>
	<td colspan="2">
	  <textarea name="TA_ZT_LINE_PRICES/REMARK[<%=k %>]" rows="2" cols="40"><%=REMARK %></textarea>
	</td>
  </tr>
<%	}
} %>
</table>
</div>
<div id="add-table">
<table class="datas" width="100%">
   <tr>
	   <td colspan="8">
			<ul>
			<li class="ckeditorli">
				<a class="a" href="###" style="color:#006666;">特色介绍</a>
				<ul>
					<li>
						<div>
						<table class="datas"><tr><td>
						<textarea id="XCMX1" name="TA_ZT_LINEMNGBLOB/TSJS">
							<%
								Object obj1 = rd.get("TA_ZT_LINEMNGBLOBs", "TSJS",0);
								Blob blob1 = null;
								int read1 = 0;
								if(null != obj1){
									blob1 = (Blob) obj1;
									java.io.InputStream in=blob1.getBinaryStream();
									InputStreamReader isr = new InputStreamReader(in, "GBK");
									char[] chars = new char[1];
									read1 = 0;
									while ((read1 = isr.read(chars)) != -1) {
										out.print(new String(chars));
									}
								}
							%>
						</textarea>
						</td></tr></table>
					</div>
					</li>
				</ul>
			</li>
			<li class="ckeditorli">
					<a class="a" href="###" style="color:#006666;">线路行程</a>
				<ul>
					<li>
					<div class="">
							<table class="datas" id="xlmc">
								<tr id="first-tr">
									<td align="center" width="8%">天数</td>
									<td align="center" width="75%">行程明细</td>
									<td align="center" width="7%">用餐</td>
									<td align="center" width="10%">住宿</td>
								</tr>
								<%
									for(int i=0;i<rd.getTableRowsCount("TA_ZT_LINEDETAILs");i++){
										String id = rd.getString("TA_ZT_LINEDETAILs","id",String.valueOf(i));
										String rq = rd.getString("TA_ZT_LINEDETAILs","rq",String.valueOf(i));
										String breakfast = rd.getString("TA_ZT_LINEDETAILs","BREAKFAST",String.valueOf(i));
										String cmeal = rd.getString("TA_ZT_LINEDETAILs","CMEAL",String.valueOf(i));
										String supper = rd.getString("TA_ZT_LINEDETAILs","SUPPER",String.valueOf(i));
										String zsbz = rd.getString("TA_ZT_LINEDETAILs","ZSBZ",String.valueOf(i));
										
									%>
									  <tr class="XCMXType">
										<td><font size="6">D<%=rq %></font>
										  <input type="hidden" name="TA_ZT_LINEDETAIL/RQ[<%=i %>]" value="<%=rq %>"/>
										  <input type="hidden" name="TA_ZT_LINEDETAIL/id[<%=i %>]" value="<%=id %>"/>
										</td>
										<td>
										  <textarea id="XCMX<%=i+10 %>" name="TA_ZT_LINEDETAIL/XCMX[<%=i %>]" rows="0" cols="0">
											<%
											Object obj = rd.get("TA_ZT_LINEDETAILs", "XCMX",String.valueOf(i));
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
										  <input type="checkbox" name="TA_ZT_LINEDETAIL/BREAKFAST[<%=i %>]" value="Y" <%if(breakfast.equals("Y")){ %>checked="checked"<%} %>/>早<br>
										  <input type="checkbox" name="TA_ZT_LINEDETAIL/CMEAL[<%=i %>]" value="Y" <%if(cmeal.equals("Y")){ %>checked="checked"<%} %>/>中<br>
										  <input type="checkbox" name="TA_ZT_LINEDETAIL/SUPPER[<%=i %>]" value="Y" <%if(supper.equals("Y")){ %>checked="checked"<%} %>/>晚<br>
										</td>
										<td align="center">
										  <input type="text" name="TA_ZT_LINEDETAIL/ZSBZ[<%=i %>]" value="<%=zsbz %>" class="smallerInput"/>
										</td>
									  </tr>
									<%
									}
									%>
							</table>	
						</div>
					</li>
				</ul>
			</li>
			
			<li class="ckeditorli">
				<a class="a" href="###" style="color:#006666;">费用包含</a>
				<ul>
					<li>
						<div>
						<table class="datas"><tr><td>
							<textarea id="XCMX3" name="TA_ZT_LINEMNGBLOB/FYBH" cols="103" rows="10">
							<%
								Object obj2 = rd.get("TA_ZT_LINEMNGBLOBs", "FYBH",0);
								Blob blob2 = null;
								int read2 = 0;
								if(null != obj2){
									blob2 = (Blob) obj2;
									java.io.InputStream in=blob2.getBinaryStream();
									InputStreamReader isr = new InputStreamReader(in, "GBK");
									char[] chars = new char[1];
									read2 = 0;
									while ((read2 = isr.read(chars)) != -1) {
										out.print(new String(chars));
									}
								}
							%>
							</textarea>
							</td></tr></table>
						</div>
					</li>
				</ul>
				</li>
					<li class="ckeditorli">
					<a class="a" href="###" style="color:#006666;">费用不包含</a>
				<ul>
					<li>
					<div><table class="datas"><tr><td>
						<textarea id="XCMX4" name="TA_ZT_LINEMNGBLOB/FYBBH" cols="103" rows="10">
							<%
								Object obj3 = rd.get("TA_ZT_LINEMNGBLOBs", "FYBBH",0);
								Blob blob3 = null;
								int read3 = 0;
								if(null != obj3){
									blob3 = (Blob) obj3;
									java.io.InputStream in=blob3.getBinaryStream();
									InputStreamReader isr = new InputStreamReader(in, "GBK");
									char[] chars = new char[1];
									read3 = 0;
									while ((read3 = isr.read(chars)) != -1) {
										out.print(new String(chars));
									}
								}
							%>
						</textarea></td></tr></table>
					</div>
					</li>
				</ul>
				</li>
					<li class="ckeditorli">
					<a class="a" href="###" style="color:#006666;">预订须知</a>
				<ul>
					<li>
					<div><table class="datas"><tr><td>
						<textarea id="XCMX5" name="TA_ZT_LINEMNGBLOB/YDXZ" cols="103" rows="10">
						<%
								Object obj4 = rd.get("TA_ZT_LINEMNGBLOBs", "YDXZ",0);
								Blob blob4 = null;
								int read4 = 0;
								if(null != obj4){
									blob4 = (Blob) obj4;
									java.io.InputStream in=blob4.getBinaryStream();
									InputStreamReader isr = new InputStreamReader(in, "GBK");
									char[] chars = new char[1];
									read4 = 0;
									while ((read4 = isr.read(chars)) != -1) {
										out.print(new String(chars));
									}
								}
							%>
						</textarea></td></tr></table>
					</div>
					</li>
				</ul>
				</li>
					<li class="ckeditorli">
					<a class="a" href="###" style="color:#006666;">自费项目</a>
				<ul>
					<li>
					<div><table class="datas"><tr><td>
						<textarea id="XCMX6" name="TA_ZT_LINEMNGBLOB/ZFXM" cols="103" rows="10">
						<%
								Object obj5 = rd.get("TA_ZT_LINEMNGBLOBs", "ZFXM",0);
								Blob blob5 = null;
								int read5 = 0;
								if(null != obj5){
									blob5 = (Blob) obj5;
									java.io.InputStream in=blob5.getBinaryStream();
									InputStreamReader isr = new InputStreamReader(in, "GBK");
									char[] chars = new char[1];
									read5 = 0;
									while ((read5 = isr.read(chars)) != -1) {
										out.print(new String(chars));
									}
								}
							%>
						</textarea></td></tr></table>
					</div>
					</li>
				</ul>
				</li>
					<li class="ckeditorli">
					<a class="a" href="###" style="color:#006666;">购物店</a>
				<ul>
					<li>
					<div><table class="datas"><tr><td>
						<textarea id="XCMX2" name="TA_ZT_LINEMNGBLOB/GWD" cols="103" rows="10">
						<%
								Object obj6 = rd.get("TA_ZT_LINEMNGBLOBs", "GWD",0);
								Blob blob6 = null;
								int read6 = 0;
								if(null != obj6){
									blob6 = (Blob) obj6;
									java.io.InputStream in=blob6.getBinaryStream();
									InputStreamReader isr = new InputStreamReader(in, "GBK");
									char[] chars = new char[1];
									read6 = 0;
									while ((read6 = isr.read(chars)) != -1) {
										out.print(new String(chars));
									}
								}
							%>
						</textarea></td></tr></table>
					</div>
					</li>
				</ul>
				</li>
					<li class="ckeditorli">
					<a class="a" href="###" style="color:#006666;">温馨提示</a>
				<ul>
					<li>
					<div><table class="datas"><tr><td>
						<textarea id="XCMX7" name="TA_ZT_LINEMNGBLOB/WXTS" cols="103" rows="10">
						<%
								Object obj7 = rd.get("TA_ZT_LINEMNGBLOBs", "WXTS",0);
								Blob blob7 = null;
								int read7 = 0;
								if(null != obj7){
									blob7 = (Blob) obj7;
									java.io.InputStream in=blob7.getBinaryStream();
									InputStreamReader isr = new InputStreamReader(in, "GBK");
									char[] chars = new char[1];
									read7 = 0;
									while ((read7 = isr.read(chars)) != -1) {
										out.print(new String(chars));
									}
								}
							%>
						</textarea></td></tr></table>
					</div>
					</li>
				</ul>
				</li>
					<li class="ckeditorli">
					<a class="a" href="###" style="color:#006666;">出境旅游须知</a>
				<ul>
					<li>
					<div><table class="datas"><tr><td>
						<textarea id="XCMX8" name="TA_ZT_LINEMNGBLOB/CJLYXZ" cols="103" rows="10">
						<%
								Object obj8 = rd.get("TA_ZT_LINEMNGBLOBs", "CJLYXZ",0);
								Blob blob8 = null;
								int read8 = 0;
								if(null != obj8){
									blob8 = (Blob) obj8;
									java.io.InputStream in=blob8.getBinaryStream();
									InputStreamReader isr = new InputStreamReader(in, "GBK");
									char[] chars = new char[1];
									read8 = 0;
									while ((read8 = isr.read(chars)) != -1) {
										out.print(new String(chars));
									}
								}
							%>
						</textarea></td></tr></table>
					</div>
					</li>
				</ul>
				</li>
				<li class="ckeditorli">
					<a class="a" href="###" style="color:#006666;">旅游注意事项</a>
				<ul>
					<li>
					<div><table class="datas"><tr><td>
						<textarea id="XCMX9" name="TA_ZT_LINEMNGBLOB/LYZYSX" cols="103" rows="10">
						<%
								Object obj9 = rd.get("TA_ZT_LINEMNGBLOBs", "LYZYSX",0);
								Blob blob9 = null;
								int read9 = 0;
								if(null != obj9){
									blob9 = (Blob) obj9;
									java.io.InputStream in=blob9.getBinaryStream();
									InputStreamReader isr = new InputStreamReader(in, "GBK");
									char[] chars = new char[1];
									read9 = 0;
									while ((read9 = isr.read(chars)) != -1) {
										out.print(new String(chars));
									}
								}
							%>
						</textarea></td></tr></table>
					</div>
					</li>
				</ul>
				</li>
		</ul>
	</td>
	</tr>
   <tr>
	 	<td align="center" width="10%">备注：</td>
		<td colspan="7"  width="90%"><textarea name="TA_ZT_LINEMNG/COMMENTS" cols="110" rows="4"><%=rd.getString("TA_ZT_LINEMNGs", "COMMENTS",0) %></textarea></td>
	 </tr>
</table>
</div>
</form>
</body>
</html>