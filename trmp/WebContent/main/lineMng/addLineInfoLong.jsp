<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import=" java.sql.Blob"%>
<%@page import="java.io.InputStreamReader"%>
<%@include file="../../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
<%
int iRows = rd.getTableRowsCount("TA_ZT_INSURANCEs"); 
int weekRows = rd.getTableRowsCount("TA_ZT_FBJH_TMPS");
String roleID = sd.getString("USERROLEST");
String canEdit = rd.getString("canEdit");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>添加线路</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/menuList.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/menuList.js"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
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


	function xcmx1(nums){
		if($(".xcmx"+nums).val() == 1){
			CKEDITOR.config.toolbarStartupExpanded = false;
			CKEDITOR.config.resize_enabled = true;
			$(".xcmx"+nums).val(2);
			CKEDITOR.replace('XCMX'+nums);
		}
	}
	
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

	function checkChoiceDate(d1,d2,plan){
		var bDate = d1;
		var eDate = d2;
		if(bDate=='' || eDate==''){
			alert("开始时间和结束时间不能为空");
			return false;
		}
		var bday = new Date(Date.parse(bDate.replace(/-/g, '/'))); //将日期值格式化
		var eday = new Date(Date.parse(eDate.replace(/-/g, '/')));
		if(bday > eday){
			alert("开始时间不能大于结束时间");
			return false;
		}
		if(plan == 2){
			return true;
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
		return true;
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
		newWindow('showAllLine.?TA_ZT_LINEMNG/line_state=1&TA_ZT_INSURANCE/ORGID=<%=sd.getString("orgid") %>&TA_ZT_LINEMNG@pageNO=1&TA_ZT_LINEMNG@pageSize=10','800', '600');
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

	function reload(){
		parent.parent.location.reload(); 
		parent.parent.GB_hide(); 
	}

	var row = 0;
	$(document).ready(function(){
		$("#DAY_COUNTS").change(function(){
			var rows = parseInt($("tr.XCMXType").size());
			var days = $("#DAY_COUNTS").val();
			var addRows = (days - rows) > 0?(days - rows):Math.abs(days - rows);
			if((days - rows) > 0){
				for(var i=0;i<addRows;i++){
					row += 1;
					var QJBL = Math.floor(Math.random()*1024);
					$("#xlmc").append('<tr class="XCMXType">'+
						'<td><font size="6">D'+row+'</font><input type="hidden" name="TA_ZT_LINEDETAIL/RQ['+parseInt(row-1)+']" value="'+row+'"/></td>'+
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
					CKEDITOR.config.toolbarStartupExpanded = false;
					CKEDITOR.config.resize_enabled = true;
					CKEDITOR.replace('XCMX'+QJBL);
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
	
	function addTraAgcCheck(){
		if($("#line_name").val()==""){
			alert("线路名称不能为空");
			$("#line_name").focus();
			return false;
		}
		var selIdx = $("#select-condition").val();
	    if(selIdx == ''){
			alert("请选择发班计划！");
			return false;
	    }
	<%
		if("E".equals(userType)){
	%>
	    var jhdd = $("#gatherID0").val();
	    if(jhdd == 0){
			alert("请选择集合地点！");
			return false;
	    }
	<%
	}
	%>
		var bd = $("#bDate").val();
		var ed = $("#eDate").val();
	    if(checkChoiceDate(bd,ed,selIdx)){
	    	document.lineInfoForm.submit();
	    }
	//	reload();
	}
	function changes(){
		var sdate = $("#bDate").val();
		var days = $("#DAY_COUNTS").val();
		if(sdate == '' || days == ''){
			return false;
		}
		var translateDate = "";
	    translateDate = sdate.replace("-", "/").replace("-", "/");;   
	  
	    var newDate = new Date(translateDate);   
	    newDate = newDate.valueOf();   
	    newDate = newDate + parseInt(days) * 24 * 60 * 60 * 1000;   
	    newDate = new Date(newDate);   
	  
	    //如果月份长度少于2，则前加 0 补位   
	    if ((newDate.getMonth() + 1).toString().length == 1) {   
	  
	        monthString = 0 + "" + (newDate.getMonth() + 1).toString();   
	    } else {   
	  
	        monthString = (newDate.getMonth() + 1).toString();   
	    }   
	  
	    //如果天数长度少于2，则前加 0 补位   
	    if (newDate.getDate().toString().length == 1) {   
	  
	        dayString = 0 + "" + newDate.getDate().toString();   
	    } else {   
	  
	        dayString = newDate.getDate().toString();   
	    }   
	  
	    dateString = newDate.getFullYear() + "-" + monthString + "-" + dayString;   
	    $("#eDate").val(dateString);
	}
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

String travelName = rd.getStringByDI("rsTraAgcInfo","name",0);

%>
<body>
<form action="insertLine." name="lineInfoForm" method="post">
<input type="hidden" name="TA_ZT_LINEMNG/userno" value="<%=sd.getString("userno") %>"/>
<input type="hidden" name="TA_ZT_LINEMNG/XLXZ" value="1"/>
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
		  	<input type="text" name="TA_ZT_LINEMNG/line_name" id="line_name" style="width:500px;" value="<%=lineName %>" maxlength="166"/>
		  	<span>*</span>
		  	<span onclick="showAllLine();" class="showPointer" style="text-decoration: underline">选择与历史相似线路</span>
	  	</td>
	  </tr>
	  <tr>
      	<td align="right">组团社名称：</td>
       	<td>
       	  <input type="text" name="TA_ZT_LINEMNG/SZLXSMC" value="<%=travelName %>" readonly="readonly" />
       	  <input type="hidden" name="TA_ZT_LINEMNG/orgid" value="<%=sd.getString("orgid") %>"/>
       	</td>
		<td align="right">国内联系电话：</td>
		<td ><input type="text" name="TA_ZT_LINEMNG/GNLXDH" value=""   maxlength="18"/></td>
		<td align="right">机场联系电话：</td>
		<td><input type="text" name="TA_ZT_LINEMNG/JCLXDH" value="" maxlength="18"/></td>
	  </tr>
	  <tr>
	  	<td align="right">线路状态：</td>
       	<td>
       	  <select name="TA_ZT_LINEMNG/LINE_STATE">
		  	<%
			for(int i=0;i<rd.getTableRowsCount("XLZT");i++) {
			%>
			  <option value="<%=rd.getStringByDI("XLZT","dmz",i) %>" <%if(rd.getStringByDI("XLZT","dmz",i).equals("1")){ out.print("selected");} %>><%=rd.getStringByDI("XLZT","dmsm1",i) %></option>
			<%
			}%>
	   	  </select>
        </td>
	    <td align="right">线路类型：</td>
        <td>
		  <select name="TA_ZT_LINEMNG/LINE_TYPE">
	  		<%
  			for(int i=0;i<rd.getTableRowsCount("xllx");i++){
	    		String dmz = rd.getStringByDI("xllx","dmz",i);
	    	%>
	    		<option value="<%=dmz %>" <%if("1".equals(dmz)){ %>selected="selected" <%} %>><%=rd.getStringByDI("xllx","dmsm1",i) %></option>
	    	<%
  			}%>
	  	  </select>
		</td>
	  	<td  align="right">线路区域：</td>
        <td>
		  <select name="TA_ZT_LINEMNG/XLQY">
	  		<%
					for (int i = 0; i < rd.getTableRowsCount("xzqhs"); i++) {
						String dmz = rd.getStringByDI("xzqhs", "id", i);
				%>
					<option value="<%=dmz%>" <%if (dmz.equals("1")) {%>selected="selected" <%}%>>
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
   		  <input type="checkbox" name="TA_ZT_LINEMNG/JD" value="1" <%if("1".equals(jd)){ %> checked="checked" <%} %>/>加点
   		  <input type="checkbox" name="TA_ZT_LINEMNG/GW" value="1" <%if("1".equals(gw)){ %> checked="checked" <%} %>/>购物
   		</td>
	 	 <td  align="right"><!-- 是否自备车： --></td>
        <td>
        	<%-- <%
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
        	}%> --%>
		</td>
      
	  	<td  align="right"><!-- 游客类型： --></td>
        <td >
        <%-- 	<%
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
        	}%> --%>
        </td>
        
	  </tr>
	  <tr>
	  	<td  align="right">退改规则：</td>
        <td >
   		  <input type="text" name="TA_ZT_LINEMNG/RETURN_ROLE" id="RETURN_ROLE" value="<%=RETURN_ROLE %>"/>
     	</td>
      	<td  align="right">保险：</td>
       	<td >
   		  <input type="text" name="TA_ZT_LINEMNG/INSURANCE" id="INSURANCE" value="<%=INSURANCE %>" />
   		</td>
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
	  
	  <tr>
	  	<td align="right">可收客人数：</td>
	    <td >
	     	<input type="text" name="TA_ZT_LINEMNG/maxPersonCount" maxlength="11" id="maxPersonCount" value="" onkeydown="checkNum();" maxlength="3"/>
	     </td>
		<td align="right">可成团人数：</td>
	   	<td>
	   		<input type="text" name="TA_ZT_LINEMNG/minPersonCount" id="minPersonCount" value="" onkeydown="checkNum();" maxlength="3"/>
	   	</td>
		<td  align="right">天数：</td>
	    <td >
	     <input type="text" name="TA_ZT_LINEMNG/DAY_COUNTS" id="DAY_COUNTS" value="" onkeydown="checkNum();" maxlength="2" />
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
	  	<td  align="right" class="cfjtTran" style="display:block">
	    航班号/车次： 
	  </td>
	  <td class="cfjtTran" style="display:block">
	    <input type="text" name="TA_ZT_LINEMNG/CFHBCC" value="" class="cfjtTranInput"/>
	</td>
	<td  align="right" class="cfjtTime" style="display:block">
	   出发时间： 
	  </td>
	  <td class="cfjtTime" style="display:block">
	    <input type="text" name="TA_ZT_LINEMNG/CFSJ" value="" class="cfjtTimeInput" onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});"/>
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
   		<td align="right" class="fhjtTran" style="display:block">
	   航班号/车次： 
	  </td>
	  <td class="fhjtTran" style="display:block">
	    <input type="text" name="TA_ZT_LINEMNG/FHHBCC" value="" class="fhjtTranInput"/>
	</td>
	<td  align="right" class="fhjtTime" style="display:block">
	   返回时间： 
	  </td>
	  <td class="fhjtTime" style="display:block">
	    <input type="text" name="TA_ZT_LINEMNG/FHSJ" value="" class="fhjtTimeInput" onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});"/>
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
  	<td colspan="3"><input type="text" name="b_date" onFocus="WdatePicker({});" id="bDate"/></td>
  	<td align="right">结束日期：</td>
  	<td colspan="3"><input type="text" name="e_date" onFocus="WdatePicker({});" id="eDate"/></td>
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
		<a href="###" class="insuranceAdd"><img alt="添加保险" src="<%=request.getContextPath() %>/images/add.gif"/> 添加</a>&nbsp;&nbsp;
       <a href="###" id="insuranceDel"><img alt="添加保险" src="<%=request.getContextPath() %>/images/del.gif"/> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     </div>
	<table width="100%" class="datas" style="text-align: center" id="insuranceList">
		<tr id="first-tr">
			<td width="26%">保险名称</td>
			<td width="26%">保险成本</td>
			<td width="24%">建议价格</td>
			<td width="24%">成本方式</td>
		</tr>
		<tr class="insurances" id="insurances0">
			<td>
			<img src="<%=request.getContextPath()%>/images/printClose.gif" class="delInsurances" onclick="delInsurances(0);"/>
			<select class="changeInsurance" id="select-0-">
			 <option value="9999">--未指定--</option>
				<%
				
				for(int i = 0; i< iRows; i++){
					String bxId = rd.getStringByDI("TA_ZT_INSURANCEs", "ID", i);
					String bxMc = rd.getStringByDI("TA_ZT_INSURANCEs", "BXLBMC", i);
					String bxCb = rd.getStringByDI("TA_ZT_INSURANCEs", "BXCB", i);
					String jyJg = rd.getStringByDI("TA_ZT_INSURANCEs", "JYJG", i);
					String cbFs = rd.getStringByDI("TA_ZT_INSURANCEs", "CBFS", i);
				%>
					<option value="<%=bxId%>-<%=bxMc%>-<%=bxCb%>-<%=jyJg%>-<%=cbFs%>"><%=bxMc %></option>
				<%
				}
				%>
			</select>
			<input type="hidden" name="TA_ZT_INSURANCE/ID[0]" id="bxid0" /></td>
			<td><input type="text"  id="bxcb0"  readonly="readonly"/></td>
			<td><input type="text"  id="jyjg0"  readonly="readonly"/></td>
			<td id="cbfs0"></td>
		</tr>
	</table> 





<div style="text-align: right"> 
		<a href="###" id="jhddAddBtn"><img alt="添加集合地点" src="<%=request.getContextPath() %>/images/add.gif"/> 添加</a>&nbsp;&nbsp;
       <a href="###" id="jhddAddBtnDel"><img alt="删除集合地点" src="<%=request.getContextPath() %>/images/del.gif"/> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     </div>
	<table width="100%" class="datas" style="text-align: center" id="gather">
		<tr id="first-tr">
			<td width="52%">集合地点</td>
			<td width="28%">集合时间</td>
			<td width="20%">加价</td>
		</tr>
		<tr class="gather">
			<td>
			<select name="TA_ZT_GATHER/GATHERHIS_ID[0]" id="gatherID0" class="gatherID" onchange="selectChange('0');">
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
 <a id="dataAddBtn" href="###"><img alt="添加" src="<%=request.getContextPath() %>/images/add.gif"/> 添加</a>&nbsp;&nbsp;
 <a id="dataDelBtn" href="###"><img alt="删除" src="<%=request.getContextPath() %>/images/del.gif"/> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div> 
<div id="list-table">
<table class="datas" id="store">
<%
if("E".equals(userType)){
%>
  <tr id="first-tr">
	<td width="22%">价格类型</td>
    <td width="10%">同行价</td>
    <td width="10%">门市价</td>
    <td width="10%">最低价</td>
    <td width="10%">单房差</td>
    <td width="38%">备注</td>
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
	<td><input name="TA_ZT_LINE_PRICES/PRICE_TH[0]" maxlength="12" id="price_th0" /></td>
	<td><input name="TA_ZT_LINE_PRICES/SINGLE_ROOM[0]" class="smallerInput" onkeydown="checkNum()"/></td>
	<td colspan="2">
	  <textarea name="TA_ZT_LINE_PRICES/REMARK[0]" rows="2" cols="40"></textarea>
	</td>
  </tr>
<%	} %>
</table>
</div>
<div id="add-table">
<table class="datas">
   <tr>
	   <td colspan="8">
			<ul>
			<li class="ckeditorli">
					<a class="a" href="###" style="color:#006666;" onclick="xcmx1('1');">特色介绍</a>
				<ul>
					<li>
						<div>
							<textarea id="XCMX1" name="TA_ZT_LINEMNGBLOB/TSJS" cols="103" rows="20"></textarea>
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
									<td align="center" width="7%">天数</td>
									<td align="center" width="70%">行程明细</td>
									<td align="center" width="8%">用餐</td>
									<td align="center" width="15%">住宿</td>
								</tr>
							</table>	
						</div>
					</li>
				</ul>
			</li>
			<li class="ckeditorli">
				<a class="a" href="###" style="color:#006666;" onclick="xcmx1('3');">费用包含</a>
				<ul>
					<li>
						<div>
							<textarea id="XCMX3" name="TA_ZT_LINEMNGBLOB/FYBH" cols="103" rows="10"></textarea>
						</div>
					</li>
				</ul>
				</li>
					<li class="ckeditorli">
					<a class="a" href="###" style="color:#006666;" onclick="xcmx1('4');">费用不包含</a>
				<ul>
					<li>
					<div>
						<textarea id="XCMX4" name="TA_ZT_LINEMNGBLOB/FYBBH" cols="103" rows="10"></textarea>
					</div>
					</li>
				</ul>
				</li>
					<li class="ckeditorli">
					<a class="a" href="###" style="color:#006666;" onclick="xcmx1('5');">预订须知</a>
				<ul>
					<li>
					<div>
						<textarea id="XCMX5" name="TA_ZT_LINEMNGBLOB/YDXZ" cols="103" rows="10"></textarea>
					</div>
					</li>
				</ul>
				</li>
					<li class="ckeditorli">
					<a class="a" href="###" style="color:#006666;" onclick="xcmx1('6');">自费项目</a>
				<ul>
					<li>
					<div>
						<textarea id="XCMX6" name="TA_ZT_LINEMNGBLOB/ZFXM" cols="103" rows="10"></textarea>
					</div>
					</li>
				</ul>
				</li>
					<li class="ckeditorli">
					<a class="a" href="###" style="color:#006666;" onclick="xcmx1('2');">购&nbsp;&nbsp;&nbsp;&nbsp;物&nbsp;&nbsp;&nbsp;&nbsp;店</a>
				<ul>
					<li>
					<div>
						<textarea id="XCMX2" name="TA_ZT_LINEMNGBLOB/GWD" cols="103" rows="10"></textarea>
					</div>
					</li>
				</ul>
				</li>
					<li class="ckeditorli">
					<a class="a" href="###" style="color:#006666;" onclick="xcmx1('7');">温&nbsp;&nbsp;馨&nbsp;&nbsp;提&nbsp;&nbsp;示</a>
				<ul>
					<li>
					<div>
						<textarea id="XCMX7" name="TA_ZT_LINEMNGBLOB/WXTS" cols="103" rows="10"></textarea>
					</div>
					</li>
				</ul>
				</li>
					<li class="ckeditorli">
					<a class="a" href="###" style="color:#006666;" onclick="xcmx1('8');">出境旅游须知</a>
				<ul>
					<li>
					<div>
						<textarea id="XCMX8" name="TA_ZT_LINEMNGBLOB/CJLYXZ" cols="103" rows="10"></textarea>
					</div>
					</li>
				</ul>
				</li>
				<li class="ckeditorli">
					<a class="a" href="###" style="color:#006666;" onclick="xcmx1('9');">旅游注意事项</a>
				<ul>
					<li>
					<div>
						<textarea id="XCMX9" name="TA_ZT_LINEMNGBLOB/LYZYSX" cols="103" rows="10"></textarea>
					</div>
					</li>
				</ul>
				</li>
		</ul>
	</td>
	</tr>
   
</table>
</div>
<div>
	<table>
		<tr>
	 		<td align="right" width="10%">备注：</td>
			<td colspan="7"  width="80%"><textarea name="TA_ZT_LINEMNG/COMMENTS" cols="90" rows="4"></textarea></td>
		 </tr>
	 </table>
	 <input type="hidden" class="xcmx1"  value="1"/>
	 <input type="hidden" class="xcmx2"  value="1"/>
	 <input type="hidden" class="xcmx3"  value="1"/>
	 <input type="hidden" class="xcmx4"  value="1"/>
	 <input type="hidden" class="xcmx5"  value="1"/>
	 <input type="hidden" class="xcmx6"  value="1"/>
	 <input type="hidden" class="xcmx7"  value="1"/>
	 <input type="hidden" class="xcmx8"  value="1"/>
	 <input type="hidden" class="xcmx9"  value="1"/>
</div>
<div>
<!-- <input type="submit" value="提交" name="sub" /> -->
</div>
</form>
</body>
</html>