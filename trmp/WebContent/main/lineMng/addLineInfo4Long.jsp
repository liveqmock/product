<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import=" java.sql.Blob"%>
<%@page import="java.io.InputStreamReader"%>
<%@include file="../../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
<%
String lineType = rd.getString("lineType");
int weekRows = rd.getTableRowsCount("TA_ZT_FBJH_TMPS");
String roleID = sd.getString("USERROLEST");
String canEdit = rd.getString("canEdit");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>添加长线线路</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
<script language="javascript">
$(document).ready(function(){
	CKEDITOR.replace('XCMX');
	CKEDITOR.config.height =880;
	CKEDITOR.config.width =878;
	CKEDITOR.config.toolbarStartupExpanded = false;
	CKEDITOR.config.resize_enabled = false;
});
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
		  '<textarea name="TA_ZT_LINE_PRICES/REMARK['+parseInt(rowsSize)+']" rows="2" cols="52"></textarea>'+
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
				'<td><input name="TA_ZT_LINE_PRICES/SINGLE_ROOM['+rowsSize+']" class="smallerInput" onkeydown="checkNum()"></td>'+
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

		if(document.getElementById("line_name").value==""){
			alert("线路名称不能为空");
			document.getElementById("line_name").focus();
			return false;
		}
		var selIdx = document.getElementById("select-condition").selectedIndex;
	    if(selIdx == ''){
			alert("请选择发班计划！");
			return false;
	    }
<%
		if("E".equals(userType)){
%>
	    var jhdd = document.getElementById("gatherID0").selectedIndex;
	    if(jhdd == 0){
			alert("请选择集合地点！");
			return false;
	    }
<%
}%>
		
		var bd = document.getElementById("bDate").value;
		var ed = document.getElementById("eDate").value;
	    if(checkChoiceDate(bd,ed,selIdx) != false){
			document.lineInfoForm.submit();
			reload();
	    }
	}

	function showplan(num){
		
		if(num==1){
			document.getElementById("plan-data1").style.display="block";
			document.getElementById("plan-data2").style.display="block";
			setName2GiveBack();
		}
		if(num==2){
			document.getElementById("plan-data1").style.display="block";
			document.getElementById("plan-data2").style.display="none";
			setName2Other();
		}
	}

	function checkChoiceDate(d1,d2,plan){
		
		var bDate = d1;
		var eDate = d2;
		var bday = new Date(Date.parse(bDate.replace(/-/g, '/'))); //将日期值格式化
		var eday = new Date(Date.parse(eDate.replace(/-/g, '/')));

		if(bday > eday){
			alert("开始时间不能大于结束时间");
			return false;
		}
		if(plan == 2){
			return;
		}
		var booleanVar = false;
		while(bday<=eday){
			//一周中的星期几
			var dayOfWeek = bday.getDay();
			dayOfWeek = parseInt(dayOfWeek)+1;
			var objs = document.lineInfoForm.elements;
			if(objs==null){
				return false;
			}
			var len=objs.length;
			var checkbox;
			for(var i=0;i<len;i++){
				if(objs.item(i).tagName=="INPUT" && objs.item(i).type=="checkbox" && objs.item(i).name!=''){
					
					checkbox=objs.item(i);
					if(checkbox.checked){
						if(checkbox.value == dayOfWeek){
							booleanVar = true;
						}
					}
				}
			}
			bday = dateAdd("d ",1,bday);
		}
		if(booleanVar == false){
			alert("选择的星期不在日期范围内，请重新选择！");
			return false;
		}
	}

	function setName2GiveBack(){

		$("#plan-data2 [type='checkBox']").each(function(i){
			this.attr("name","cycle/week["+i+"]");
		});
	}

	function setName2Other(){

		$("#plan-data2 [type='checkBox']").each(function(){
			this.attr("name","other");
		});
	}
	
	function showAllLine(){

		newWindow('showAllLine.?TA_ZT_LINEMNG/line_state=1&TA_ZT_LINEMNG@pageNO=1&TA_ZT_LINEMNG@pageSize=10','800', '600');

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

	function reload(){
		parent.parent.location.reload(); 
		parent.parent.GB_hide(); 
	}

	var row = 0;
	$(document).ready(function(){
		$("#DAY_COUNTS").change(function(){
			var rows = parseInt(jQuery(".XCMXType").size());
			var days = 0;//$("#DAY_COUNTS").val();
			var addRows = (days - rows) > 0?(days - rows):Math.abs(days - rows);
			if((days - rows) > 0){
				for(var i=0;i<addRows;i++){

					row += 1;
					var QJBL = Math.floor(Math.random()*1024);
					$("#xlmc").append('<tr class="XCMXType">'+
						'<td><font size="6">D'+row+'</font><input type="hidden" name="TA_ZT_LINEDETAIL/RQ['+parseInt(row-1)+']" value="D'+row+'"/></td>'+
						'<td>'+
						'<textarea id="XCMX'+QJBL+'" name="TA_ZT_LINEDETAIL/XCMX['+parseInt(row-1)+']" rows="0" cols="0">'+
						'</textarea>'+ 
						'</td>'+
						'<td align="center">'+
						'<input type="checkbox" name="TA_ZT_LINEDETAIL/BREAKFAST['+parseInt(row-1)+']" value="Y"/>早<br>'+
						'<input type="checkbox" name="TA_ZT_LINEDETAIL/CMEAL['+parseInt(row-1)+']" value="Y"/>中<br>'+
						'<input type="checkbox" name="TA_ZT_LINEDETAIL/SUPPER['+parseInt(row-1)+']" value="Y"/>晚<br>'+
						'</td>'+
						'<td align="center"><input type="text" name="TA_ZT_LINEDETAIL/ZSBZ['+parseInt(row-1)+']" value="" class="smallerInput"/></td>'+
						'</tr>');
					CKEDITOR.replace('XCMX'+QJBL);
					CKEDITOR.config.height =80;
					CKEDITOR.config.width =550;
					CKEDITOR.config.toolbarStartupExpanded = false;
					CKEDITOR.config.resize_enabled = false;
				}
			}else{
				row = row - addRows;
				for(var d=addRows;d>0;d--){
					var idx=parseInt($("tr.XCMXType").size()-1);
					$("tr.XCMXType:eq("+idx+")").remove();
				}
			}
		});
	});
</script>
</head>
<%
String lineName = rd.getStringByDI("TA_ZT_LINEMNGs","LINE_NAME",0);
String traAgcNm = rd.getStringByDI("TA_ZT_LINEMNGs","SZLXSMC",0);
String instancyPhone = rd.getStringByDI("TA_ZT_LINEMNGs","SZLXSLXDH",0);
String RETURN_ROLE = rd.getStringByDI("TA_ZT_LINEMNGs","RETURN_ROLE",0);
String DAY_COUNTS = rd.getStringByDI("TA_ZT_LINEMNGs","DAY_COUNTS",0);
String INSURANCE = rd.getStringByDI("TA_ZT_LINEMNGs","INSURANCE",0);
String maxPersonCount = rd.getStringByDI("TA_ZT_LINEMNGs","maxPersonCount",0);
String minPersonCount = rd.getStringByDI("TA_ZT_LINEMNGs","minPersonCount",0);


String jd = rd.getStringByDI("TA_ZT_LINEMNGs","JD",0);
String gw = rd.getStringByDI("TA_ZT_LINEMNGs","GW",0);
String yklx = rd.getStringByDI("TA_ZT_LINEMNGs","yklx",0);
String sfzbc = rd.getStringByDI("TA_ZT_LINEMNGs","SFZBC",0);

String orgid = rd.getStringByDI("rsTraAgcInfo","orgid",0);
String cmnyName = rd.getStringByDI("rsTraAgcInfo","CMPNY_NAME",0);
String ph = rd.getStringByDI("rsTraAgcInfo","CHIEF_MOBILE",0);

%>
<body>
<form action="insert.?lineType=<%=lineType %>" name="lineInfoForm" method="post">
<input type="hidden" name="TA_ZT_LINEMNG/userno" value="<%=sd.getString("userno") %>"/>
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
		  	<input type="text" name="TA_ZT_LINEMNG/line_name" id="line_name" style="width:370px;" value="<%=lineName %>"/>
		  	<span>*</span>
		  	<span onclick="showAllLine();" class="showPointer" style="text-decoration: underline">选择与历史相似线路</span>
	  	</td>
	  </tr>
	  <tr>
      	<td align="right">组团社名称：</td>
       	<td>
       	  <input type="text" name="TA_ZT_LINEMNG/SZLXSMC" value="<%=cmnyName %>" readonly="readonly" />
       	  <input type="hidden" name="TA_ZT_LINEMNG/orgid" value="<%=orgid %>" readonly="readonly" />
       	</td>
  	  	<td  align="right" style="display: none;">出发交通：</td>
        <td style="display: none;">
	  		<select name="TA_ZT_LINEMNG/B_TRAFFIC">
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
	  	<td align="right" style="display: none;">返回交通：</td>
       	 <td style="display: none;">
   		  <select name="TA_ZT_LINEMNG/E_TRAFFIC">
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
	  </tr>
	  
	  <tr>
  	  	<td align="right">紧急联系电话：</td>
   	  	<td>
   	  	  <input type="text" name="TA_ZT_LINEMNG/SZLXSLXDH" value="<%="".equals(ph)?instancyPhone:ph %>" readonly="readonly" />
  	    </td>
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
        <td  align="right">退改规则：</td>
        <td >
   		  <input type="text" name="TA_ZT_LINEMNG/RETURN_ROLE" id="RETURN_ROLE" value="<%=RETURN_ROLE %>"/>
     	</td>
	  </tr>
	  
	  <tr>
	  	 <td  align="right">天数：</td>
        <td >
        <input type="text" name="TA_ZT_LINEMNG/DAY_COUNTS" id="DAY_COUNTS" value=""/>
         </td>
        <td  align="right">线路类型：</td>
        <td >
		  <select name="TA_ZT_LINEMNG/LINE_TYPE">
	  		<%
	  		String lineType2 = rd.getStringByDI("TA_ZT_LINEMNGs","LINE_TYPE",0);
	  		if(!"".equals(lineType2)){
		    	for(int i=0;i<rd.getTableRowsCount("xllx");i++){
		    		String dmz = rd.getStringByDI("xllx","dmz",i);
		    		if("2".equals(dmz))
		    			continue;
		  		%>
		    	  <option value="<%=dmz %>"><%=rd.getStringByDI("xllx","dmsm1",i) %></option>
		  		<%
		    	}
	  		}else{
	  			for(int i=0;i<rd.getTableRowsCount("xllx");i++){
		    		String dmz = rd.getStringByDI("xllx","dmz",i);
		    		if("2".equals(dmz))
		    			continue;
		  		%>
		    	  <option value="<%=dmz %>" <%if(lineType2.equals(dmz)){ %>selected="selected" <%} %>><%=rd.getStringByDI("xllx","dmsm1",i) %></option>
		  		<%
		    	}
		    }
	  		%>
	  	  </select>
		</td>
      	<td  align="right">保险：</td>
       	<td >
   		  <input type="text" name="TA_ZT_LINEMNG/INSURANCE" id="INSURANCE" value="<%=INSURANCE %>"/>
   		</td>
	  </tr>
	  
	  <tr>
	  	<td  align="right">游客类型：</td>
        <td >
        	<%
        	if("1".equals(yklx)){
        	%>
        	<input type="radio" name="TA_ZT_LINEMNG/YKLX" value="1" checked="checked"/>团队
        	<input type="radio" name="TA_ZT_LINEMNG/YKLX" value="2" />散客
        	<%
        	}else if("2".equals(yklx)){
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
        	if("1".equals(sfzbc)){
        	%>
        		<input type="radio" name="TA_ZT_LINEMNG/SFZBC" value="1" checked="checked"/>是
	        	<input type="radio" name="TA_ZT_LINEMNG/SFZBC" value="2" />否
        	<%
        	}else if("2".equals(sfzbc)){%>
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
   		  <input type="checkbox" name="TA_ZT_LINEMNG/JD" value="1" <%if("1".equals(jd)){ %> checked="checked" <%} %>/>加点
   		  <input type="checkbox" name="TA_ZT_LINEMNG/GW" value="1" <%if("1".equals(gw)){ %> checked="checked" <%} %>/>购物
   		</td>
	  </tr>
	  
	  <tr>
      	<td align="right">可收客人数：</td>
        <td >
        	<input type="text" name="TA_ZT_LINEMNG/maxPersonCount" maxlength="11" id="maxPersonCount" value=""/></td>
   	    <td align="right">可成团人数：</td>
      	<td><input type="text" name="TA_ZT_LINEMNG/minPersonCount" id="minPersonCount" value=""/></td>
      	<td align="right">发班计划：</td>
  		<td>
	  	  <select name="TA_ZT_LINEMNG/plan" id="select-condition" onchange="showplan(this.value)">
	  		<option value="" selected="selected">***请选择***</option>
	  		<%
	    	for(int i=0;i<rd.getTableRowsCount("fbjh");i++){
	  		%>
	    	  <option value="<%=rd.getStringByDI("fbjh","dmz",i) %>"><%=rd.getStringByDI("fbjh","dmsm1",i) %></option>
	  		<%
	    	}
	  		%>
	  	  </select><span>*</span>
	  	</td>
     </tr>
	</table>
	
</div>
<div class="add-table">
<table class="datas" width="98%">
  <tr id="first-tr">
  	<td colspan="8">发班计划时间详细</td>
  </tr>
  <tr id="plan-data1" style="display: none">
  	<td align="right">开始日期：</td>
  	<td colspan="3"><input type="text" name="b_date" onFocus="WdatePicker({isShowClear:false,readOnly:true});" id="bDate"/></td>
  	<td align="right">结束日期：</td>
  	<td colspan="3"><input type="text" name="e_date" onFocus="WdatePicker({isShowClear:false,readOnly:true});" id="eDate"/></td>
  </tr>
  <tr id="plan-data2" style="display: none">
  	<td align="right">周期：</td>
  	<td><input type="checkbox" name="cycle/week[0]" value="1" id="cycle"/>星期日</td>
  	<td><input type="checkbox" name="cycle/week[1]" value="2" id="cycle"/>星期一</td>
  	<td><input type="checkbox" name="cycle/week[2]" value="3" id="cycle"/>星期二</td>
  	<td><input type="checkbox" name="cycle/week[3]" value="4" id="cycle"/>星期三</td>
  	<td><input type="checkbox" name="cycle/week[4]" value="5" id="cycle"/>星期四</td>
  	<td><input type="checkbox" name="cycle/week[5]" value="6" id="cycle"/>星期五</td>
  	<td><input type="checkbox" name="cycle/week[6]" value="7" id="cycle"/>星期六</td>
  </tr>
</table>
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
		<tr>
			<td>
			<select name="TA_ZT_GATHER/GATHERHIS_ID[0]" id="gatherID0" onchange="selectChange('<%=0 %>');">
				<option value="-1">***请选择***</option>
			<%
			for(int i=0;i<rd.getTableRowsCount("TA_ZT_GATHER_HISs");i++) {
				String gather = rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER",i);
				String gatherTime = rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER_TIME",i);
			%>
			  <option value="<%=rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER_ID",i) %>"><%=gather %>---<%=gatherTime %></option>
			<%
			}
			%>
			</select>
			</td>
			<td><input type="text" name="TA_ZT_GATHER/GATHER_TIME[0]" id="gatherTime0"/></td>
			<td>
			<input type="text" name="TA_ZT_GATHER/ADD_M_COUNT[0]" id="addMCount0" onkeydown="checkNum()"/>
			<input type="hidden" name="TA_ZT_GATHER/GATHER[0]" id="gather0"/>
			</td>
		</tr>
	</table> 
<div style="text-align: right"> 
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
  <tr class="addPriceRows">
	<td>
	  <select name="TA_ZT_LINE_PRICES/price_type[0]">
	  <%
	    for(int i=0;i<rd.getTableRowsCount("jglx");i++) {
	    	String dmz = rd.getStringByDI("jglx","dmz",i);
	  %>
	    <option value="<%=dmz %>" <%if(i == 0){ %>selected="selected"<%} %>><%=rd.getStringByDI("jglx","dmsm1",i) %></option>
	  <%
	    }
	  %>
	  </select>
	</td>
	<td><input name="TA_ZT_LINE_PRICES/PRICE_TH[0]" value="" maxlength="12" id="price_th0" class="smallInput"/></td>
	<td><input name="TA_ZT_LINE_PRICES/PRICE_ms[0]" value="" maxlength="12" class="smallInput"/></td>
	<td><input name="TA_ZT_LINE_PRICES/PRICE_zd[0]" value="" maxlength="12" class="smallInput"/></td>
	<td><input name="TA_ZT_LINE_PRICES/SINGLE_ROOM[0]" value="" maxlength="12" class="smallInput"/></td>
	<td colspan="2">
	  <textarea name="TA_ZT_LINE_PRICES/REMARK[0]" rows="2" cols="52"></textarea>
	</td>
  </tr>
<%
}else{
%>
  <tr id="first-tr">
	<td width="15%">价格类型</td>
    <td width="15%">同行价</td>
    <td width="20%">单房差</td>
    <td width="50%">备注</td>
  </tr>
  <tr class="addPriceRows">
	<td>
	  <select name="TA_ZT_LINE_PRICES/price_type[0]">
	  <%
	    for(int i=0;i<rd.getTableRowsCount("jglx");i++){
	  %>
	    <option value="<%=rd.getStringByDI("jglx","dmz",i) %>" <%if(i == 0){ %>selected="selected"<%} %>><%=rd.getStringByDI("jglx","dmsm1",i) %></option>
	  <%
	    }
	  %>
	  </select>
	</td>
	<td><input name="TA_ZT_LINE_PRICES/PRICE_TH[0]" maxlength="12" id="price_th0"></input></td>
	<td><input name="TA_ZT_LINE_PRICES/SINGLE_ROOM[0]" class="smallerInput" onkeydown="checkNum()"></td>
	<td colspan="2">
	  <textarea name="TA_ZT_LINE_PRICES/REMARK[0]" rows="2" cols="40"></textarea>
	</td>
  </tr>
<%	} %>
</table>
</div>
<div class="add-table">
<table class="datas" id="xlmc">
  <tr id="first-tr">
  	<td width="70%" align="center">行程明细</td>
  </tr>
  <tr>
  	<td>
  		<textarea id="XCMX" name="TA_ZT_LINEDETAIL/XCMX[0]" rows="0" cols="0">
		</textarea>
  	</td>
  </tr>
</table>
</div>
</form>
</body>
</html>