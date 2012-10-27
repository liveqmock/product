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
String lineType = rd.getString("lineType");
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
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
<script language="javascript">
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

	var value = document.getElementById("gatherID"+id).value;
	var len = arrays.length;
	for(var k=0;k<len;k++){
		if(arrays[k][0] == value){
			document.getElementById("gatherTime"+id).value=arrays[k][1];
			document.getElementById("addMCount"+id).value=arrays[k][2];
			document.getElementById("gather"+id).value=arrays[k][3];
		}
	}
}

	function addTraAgcCheck(){
		if($("#line_name").val()==""){
			alert("线路名称不能为空");
			$("#line_name").focus();
			return false;
		}
		
	    if(document.getElementById("select-condition").selectedIndex == ''){
			alert("请选择发班计划！");
			return false;
	    }
		document.lineInfoForm.submit();
		reload(); 
	}
<%
if(isTrue == false){
%>
	var lineNo;
	function addNewRow(){
		var store = document.getElementById("store");
		lineNo = store.rows.length -1;
		var newRow=store.insertRow();
		var td1=document.createElement("td");
		var td2=document.createElement("td");
		var td3=document.createElement("td");
		td3.setAttribute("colSpan","2");
		td1.innerHTML="<select name='TA_ZT_LINE_PRICES/price_type["+lineNo+"]'>"+
		               <%
		               for(int i=0;i<rd.getTableRowsCount("jglx");i++){
		               %>
		                 "<option value='<%=rd.getStringByDI("jglx","dmz",i) %>' <%if(i == 0){ %>selected='selected'<%} %>><%=rd.getStringByDI("jglx","dmsm1",i) %></option>"+
		               <%
		               }
		               %>
		               "</select>";
		td2.innerHTML="<input name='TA_ZT_LINE_PRICES/PRICE_TH["+lineNo+"]' value='0' maxlength='12' id='price_th"+lineNo+"'/>";
		td3.innerHTML="<textarea name='TA_ZT_LINE_PRICES/REMARK["+lineNo+"]' rows='2' cols='52'></textarea>";
		newRow.appendChild(td1);
		newRow.appendChild(td2);
		newRow.appendChild(td3);
		document.getElementById("price_th"+lineNo).focus();
		lineNo += 1;
	}
<%
}else{
%>
	var lineNo;
	function addNewRow(){
		var store = document.getElementById("store");
		lineNo = store.rows.length -1;
		var newRow=store.insertRow();
		var td1=document.createElement("td");
		var td2=document.createElement("td");
		var td3=document.createElement("td");
		var td4=document.createElement("td");
		var td5=document.createElement("td");
		var td6=document.createElement("td");
		td1.innerHTML="<select name='TA_ZT_LINE_PRICES/price_type["+lineNo+"]'>"+
		               <%
		               for(int i=0;i<rd.getTableRowsCount("jglx");i++){
		               %>
		                 "<option value='<%=rd.getStringByDI("jglx","dmz",i) %>' <%if(i == 0){ %>selected='selected'<%} %>><%=rd.getStringByDI("jglx","dmsm1",i) %></option>"+
		               <%
		               }
		               %>
		               "</select>";
		td2.innerHTML="<input name='TA_ZT_LINE_PRICES/PRICE_TH["+lineNo+"]' value='0' maxlength='12' id='price_th"+lineNo+"' class='smallInput'/>";
		td3.innerHTML="<input name='TA_ZT_LINE_PRICES/PRICE_ms["+lineNo+"]' value='0' maxlength='12' id='price_th"+lineNo+"' class='smallInput'/>";
		td4.innerHTML="<input name='TA_ZT_LINE_PRICES/PRICE_zd["+lineNo+"]' value='0' maxlength='12' id='price_th"+lineNo+"' class='smallInput'/>";
		td5.innerHTML="<input name='TA_ZT_LINE_PRICES/SINGLE_ROOM["+lineNo+"]' value='0' class='smallerInput' onkeyDown='checkNum()'/>"
		td6.innerHTML="<textarea name='TA_ZT_LINE_PRICES/REMARK["+lineNo+"]' rows='2' cols='40'></textarea>";
		newRow.appendChild(td1);
		newRow.appendChild(td2);
		newRow.appendChild(td3);
		newRow.appendChild(td4);
		newRow.appendChild(td5);
		newRow.appendChild(td6);
		document.getElementById("price_th"+lineNo).focus();
		lineNo += 1;
	}
<%
}%>

<!----------------------------------------------------->
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

			document.getElementById("plan-data1").style.display="none";
			document.getElementById("plan-data2").style.display="none";
			document.getElementById("cycle").name="other";
			document.getElementById("bDate").name="other";
			document.getElementById("eDate").name="other";
		}
		if(num==1){
			document.getElementById("plan-data1").style.display="block";
			document.getElementById("plan-data2").style.display="block";
		}
		if(num==2){
			document.getElementById("plan-data1").style.display="block";
			document.getElementById("plan-data2").style.display="none";
			document.getElementById("cycle").name="other";
			}
		}
	function addRow_jhdd(){
		
		var num=document.getElementById("jhdd_tab").rows.length-1;
		$("#jhdd_tab").append('<tr><td>'+
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
	}

	$(document).ready(function(){
		$("#DAY_COUNTS").change(function(){
			var rows = parseInt(jQuery(".XCMXType").size());
			var days = 0;//$("#DAY_COUNTS").val();
			var addRows = (days - rows) > 0 ? (days - rows) : Math.abs(days - rows);
			if((days - rows) > 0){
				for(var i=0;i<addRows;i++){

					var row = parseInt(jQuery(".XCMXType").size());
					var QJBL = Math.floor(Math.random()*1024);
					$("#xlmc").append('<tr class="XCMXType">'+
						'<td><font size="6">D'+parseInt(row+1)+'</font><input type="hidden" name="TA_ZT_LINEDETAIL/RQ['+parseInt(row)+']" value="D'+parseInt(row+1)+'"/></td>'+
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
	});

	$(document).ready(function(){
		CKEDITOR.replace('XCMX');
		CKEDITOR.config.height =880;
		CKEDITOR.config.width =878;
		CKEDITOR.config.toolbarStartupExpanded = false;
		CKEDITOR.config.resize_enabled = false;
		<%
		int XCMXRow = rd.getTableRowsCount("TA_ZT_LINEDETAILs");
		for(int i = 0; i<XCMXRow; i++){
		%>
	    <%
	    }
	    %>
	});
</script>
</head>
<body>
<form action="update.?lineType=<%=lineType %>" name="lineInfoForm" method="post">
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
  		<td colspan="5">
		  	<input type="text" name="TA_ZT_LINEMNG/line_name" value="<%=rd.getStringByDI("TA_ZT_LINEMNGs","LINE_NAME",0) %>" id="line_name" style="width:500px;"/>
		  	<span>*</span> 
	  	</td>
	  </tr>
	  <tr>
  	  	<td align="right">组团社名称：</td>
       	<td><input type="text" name="TA_ZT_LINEMNG/SZLXSMC" value="<%=rd.getStringByDI("TA_ZT_LINEMNGs","SZLXSMC",0) %>" readonly="readonly" />
       		<input type="hidden" name="TA_ZT_LINEMNG/orgid" value="<%=rd.getStringByDI("TA_ZT_LINEMNGs","orgid",0) %>" readonly="readonly" />
       	</td>
  	  	<td  align="right" style="display: none">出发交通：
        
	  		<select name="TA_ZT_LINEMNG/B_TRAFFIC">
	  		<%
	  		String BT = rd.getStringByDI("TA_ZT_LINEMNGs","B_TRAFFIC",0);
	    	for(int i=0;i<rd.getTableRowsCount("JTGJ");i++){
	    		String dmz = rd.getStringByDI("JTGJ","dmz",i);
	  		%>
	    	  <option value="<%=dmz %>" <%if(dmz.equals(BT)){ %> selected="selected" <%} %>><%=rd.getStringByDI("JTGJ","dmsm1",i) %></option>
	  		<%
	    	}
	  		%>
	  	  </select>
	  	返回交通：
       	
   		  <select name="TA_ZT_LINEMNG/E_TRAFFIC">
	  		<%
	  		String ET = rd.getStringByDI("TA_ZT_LINEMNGs","E_TRAFFIC",0);
	    	for(int i=0;i<rd.getTableRowsCount("JTGJ");i++){
	    		String dmz = rd.getStringByDI("JTGJ","dmz",i);
	  		%>
	    	  <option value="<%=dmz %>" <%if(dmz.equals(ET)){ %> selected="selected" <%} %> ><%=rd.getStringByDI("JTGJ","dmsm1",i) %></option>
	  		<%
	    	}
	  		%>
	  	  </select>
   		</td>
	  </tr>
	  
	  <tr>
	  		<td align="right">紧急联系电话：</td>
   	  	<td>
   	  	  <input type="text" name="TA_ZT_LINEMNG/SZLXSLXDH" value="<%=rd.getStringByDI("TA_ZT_LINEMNGs","SZLXSLXDH",0) %>" readonly="readonly" />
  	    </td>
       <td align="right">线路状态：</td>
       <td>
       <select name="TA_ZT_LINEMNG/LINE_STATE" >
	  		<%
	  		String lineState = rd.getStringByDI("TA_ZT_LINEMNGs","LINE_STATE",0);
	    	for(int i=0;i<rd.getTableRowsCount("XLZT");i++){
	    		String dmz = rd.getStringByDI("XLZT","dmz",i);
	  		%>
	    	  <option value="<%=dmz %>" <%if(dmz.equals(lineState)){ %>selected="selected"<%} %>><%=rd.getStringByDI("XLZT","dmsm1",i) %></option>
	  		<%
	    	}
	  		%>
	   </select>
       </td>
       <td  align="right">退改规则：</td>
       <td><input type="text" name="TA_ZT_LINEMNG/RETURN_ROLE" value="<%=rd.getStringByDI("TA_ZT_LINEMNGs","RETURN_ROLE",0) %>" id="RETURN_ROLE" /></td>
	  </tr>
	  
	  <tr>
	  	<td  align="right">天数：</td>
        <td><input type="text" name="TA_ZT_LINEMNG/DAY_COUNTS" value="<%=rd.getStringByDI("TA_ZT_LINEMNGs","DAY_COUNTS",0) %>" id="DAY_COUNTS" /></td>
        <td  align="right">线路类型：</td>
        <td >
		  <select name="TA_ZT_LINEMNG/LINE_TYPE">
	  		<%
	  		String lineType2 = rd.getStringByDI("TA_ZT_LINEMNGs","LINE_TYPE",0);
	    	for(int i=0;i<rd.getTableRowsCount("xllx");i++){
	    		String dmz = rd.getStringByDI("xllx","dmz",i);
	    		if("2".equals(dmz))
	    			continue;
	  		%>
	    	  <option value="<%=dmz %>" <%if(lineType2.equals(dmz)){ %>selected="selected" <%} %>><%=rd.getStringByDI("xllx","dmsm1",i) %></option>
	  		<%
	    	}
	  		%>
	  	  </select>
		</td>
      	<td  align="right">保险：</td>
       	<td >
   		  <input type="text" name="TA_ZT_LINEMNG/INSURANCE" value="<%=rd.getStringByDI("TA_ZT_LINEMNGs","INSURANCE",0) %>" id="INSURANCE" />
   		</td>
	  </tr>
	  <tr>
	  	<td  align="right">游客类型：</td>
        <td >
        	<%
        	if("1".equals(rd.getStringByDI("TA_ZT_LINEMNGs","YKLX",0))){
        	%>
        	<input type="radio" name="TA_ZT_LINEMNG/YKLX" value="1" checked="checked"/>团队
        	<input type="radio" name="TA_ZT_LINEMNG/YKLX" value="2" />散客
        	<%
        	}else if("2".equals(rd.getStringByDI("TA_ZT_LINEMNGs","YKLX",0))){
        	%>
        	<input type="radio" name="TA_ZT_LINEMNG/YKLX" value="1" />团队
        	<input type="radio" name="TA_ZT_LINEMNG/YKLX" value="2" checked="checked"/>散客
        	<%
        	}else{%>
        	<input type="radio" name="TA_ZT_LINEMNG/YKLX" value="1" checked="checked"/>团队
        	<input type="radio" name="TA_ZT_LINEMNG/YKLX" value="2" />散客
        	<%
        	}%>
        </td>
        <td  align="right">是否自备车：</td>
        <td>
        	<%
        	if("1".equals(rd.getStringByDI("TA_ZT_LINEMNGs","SFZBC",0))){
        	%>
        		<input type="radio" name="TA_ZT_LINEMNG/SFZBC" value="1" checked="checked"/>是
	        	<input type="radio" name="TA_ZT_LINEMNG/SFZBC" value="2" />否
        	<%
        	}else if("2".equals(rd.getStringByDI("TA_ZT_LINEMNGs","SFZBC",0))){%>
			  	<input type="radio" name="TA_ZT_LINEMNG/SFZBC" value="1" />是
	        	<input type="radio" name="TA_ZT_LINEMNG/SFZBC" value="2" checked="checked"/>否
        	<%
        	}else{ %>
        		<input type="radio" name="TA_ZT_LINEMNG/SFZBC" value="1" />是
	        	<input type="radio" name="TA_ZT_LINEMNG/SFZBC" value="2" checked="checked"/>否
        	<%
        	}%>
		</td>
      	<td  align="right">加点购物：</td>
       	<td>
   		  <input type="checkbox" name="TA_ZT_LINEMNG/JD" value="1" <%if("1".equals(rd.getStringByDI("TA_ZT_LINEMNGs","jd",0))){ %> checked="checked" <%} %>/>加点
   		  <input type="checkbox" name="TA_ZT_LINEMNG/GW" value="1" <%if("1".equals(rd.getStringByDI("TA_ZT_LINEMNGs","gw",0))){ %> checked="checked" <%} %>/>购物
   		</td>
	  </tr>
	  <tr>
      	<td align="right">可收客人数：</td>
        <td >
        	<input type="text" name="TA_ZT_LINEMNG/maxPersonCount" value="<%=rd.getStringByDI("TA_ZT_LINEMNGs","maxPersonCount",0) %>" maxlength="11" id="maxPersonCount" /></td>
   	    <td align="right">可成团人数：</td>
      	<td><input type="text" name="TA_ZT_LINEMNG/minPersonCount" value="<%=rd.getStringByDI("TA_ZT_LINEMNGs","minPersonCount",0) %>" id="minPersonCount" /></td>
      	<td align="right">发班计划：</td>
  		<td>
	  	  <select name="TA_ZT_LINEMNG/plan" id="select-condition" onchange="showplan(this.value)" <%if("0".equals(canEdit)){ %> disabled="disabled" <%} %> >
	  		<option value="">***请选择***</option>
	  		<%
	  		String plan = rd.getStringByDI("TA_ZT_LINEMNGs","plan",0);
	    	for(int i=0;i<rd.getTableRowsCount("fbjh");i++){
	    		String dmz = rd.getStringByDI("fbjh","dmz",i);
	  		%>
	    	  <option value="<%=dmz %>"<%if(dmz.equals(plan)){ %>selected="selected"<%} %>><%=rd.getStringByDI("fbjh","dmsm1",i) %></option>
	  		<%
	    	}
	  		%>
	  	  </select><span>*</span>
	  	</td>
     </tr>
	</table>
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
  <tr id="plan-data2" <%if(plan.equals("1") && weekRows > 0){ %>style="display: block;" <%}else{ %>style="display: none"<%} %>>
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
	</div>
	<div style="text-align: right"> 
		<a href="###" onclick="addRow_jhdd();"><img alt="添加集合地点" src="<%=request.getContextPath() %>/images/add.gif"> 添加</a>&nbsp;&nbsp;
       <a href="###" onclick="delTabRow('jhdd_tab',1,'');"><img alt="删除集合地点" src="<%=request.getContextPath() %>/images/del.gif"> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     </div>
	<table width="100%" class="datas" style="text-align: center" id="jhdd_tab">
		<tr id="first-tr">
			<td width="70%">集合地点</td>
			<td width="20%">集合时间</td>
			<td width="10%">加价</td>
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
	
		<tr>
			<td>
			<select name="TA_ZT_GATHER/GATHERHIS_ID[<%=i %>]" id="gatherID0" onchange="selectChange('<%=i %>');">
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
if("E".equals(userType)){
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
<div class="add-table">
<table class="datas" id="xlmc">
  <tr id="first-tr">
  	<td width="70%" align="center">行程明细</td>

  </tr>
<%
for(int i=0;i<rd.getTableRowsCount("TA_ZT_LINEDETAILs");i++){
	String id = rd.getStringByDI("TA_ZT_LINEDETAILs","id",i);
	String rq = rd.getStringByDI("TA_ZT_LINEDETAILs","rq",i);
	String breakfast = rd.getStringByDI("TA_ZT_LINEDETAILs","BREAKFAST",i);
	String cmeal = rd.getStringByDI("TA_ZT_LINEDETAILs","CMEAL",i);
	String supper = rd.getStringByDI("TA_ZT_LINEDETAILs","SUPPER",i);
	String zsbz = rd.getStringByDI("TA_ZT_LINEDETAILs","ZSBZ",i);
}
%>
  <tr class="XCMXType">
	<td>
	  <textarea id="XCMX" name="TA_ZT_LINEDETAIL/XCMX[0]" rows="0" cols="0">
		<%
		Object obj = rd.get("TA_ZT_LINEDETAILs", "XCMX",0);
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
  </tr>

</table>
</div>

</form>
</body>
</html>