<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="java.sql.Blob"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>单团登记</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/menuList.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/menuList.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
<%
	String roleID = sd.getString("USERROLEST");
	boolean isTrue = false;
	if (!"".equals(roleID)) {

		roleID = roleID.substring(1, roleID.length() - 1);
		String[] roleIDs = roleID.split(",");
		for (int i = 0; i < roleIDs.length; i++) {
			if ("admin".equals(roleIDs[i].trim())
					|| "transferForDj".equals(roleIDs[i].trim())) {
				isTrue = true;
				break;
			}
		}
	}
	String canEdit = rd.getString("canEdit");
	int iRows = rd.getTableRowsCount("TA_ZT_INSURANCEs");
%>
<script type="text/javascript">
function xcmx1(nums){
	if(nums=='10'){
		
	}else if($(".xcmx"+nums).val() == 1){
		CKEDITOR.config.toolbarStartupExpanded = false;
		CKEDITOR.config.resize_enabled = true;
		$(".xcmx"+nums).val(2);
		CKEDITOR.replace('XCMX'+nums);
	}
}
</script>
<script type="text/javascript">
	var row = 0;
	function personNum(){
		var tmps = 0;
		$("input.personNum").each(function(){
			var thisValue = this.value;
			if('' == thisValue)
				thisValue = 0;
			tmps+=parseInt(thisValue);
		});
		
		var a=tmps-$("div.personInfo").size()>0?tmps-$("div.personInfo").size():Math.abs(tmps-$("div.personInfo").size());
		if(tmps-$("div.personInfo").size()>0){
		  for(var i=0;i<a;i++){
			row += 1;
			$("#tourist").append('<div class="personInfo">姓名：<input name="TA_ZT_VISITOR/VISITOR_NM['+parseInt(row-1)+']" class="smallInput"/>'+
					'&nbsp;性别：<select name="TA_ZT_VISITOR/SEX['+parseInt(row-1)+']"> '+
					'<option value="0">不详</option>'+
					'<option value="1" selected="selected">男</option>'+
					'<option value="2">女</option>'+
					'</select>'+
					'&nbsp;证件号：<input name="TA_ZT_VISITOR/LICENSE_NO['+parseInt(row-1)+']"/>'+
					'&nbsp;有效期：<input name="TA_ZT_VISITOR/ZJYXQ['+parseInt(row-1)+']" class="smallInput"/>'+
					'&nbsp;签证地：'+
					'<input name="TA_ZT_VISITOR/PASSPORTPS['+parseInt(row-1)+']" type="text" class="smallInput"/>'+
					'&nbsp;出生地：'+
					'<input name="TA_ZT_VISITOR/BORNSITE['+parseInt(row-1)+']" type="text" class="smallInput"/>'+
					'&nbsp;电话：<input name="TA_ZT_VISITOR/TEL['+parseInt(row-1)+']" class="smallerInput"/>'+
					'&nbsp;地址：'+
					'<input name="TA_ZT_VISITOR/ADDRESS['+parseInt(row-1)+']" type="text" class="smallerInput"/>'+
					'&nbsp;<input name="TA_ZT_VISITOR/ISLEADER['+parseInt(row-1)+']" type="checkbox" value="1" class="zylxr" onclick="zylxr('+parseInt(row-1)+')"/>'+
					'主'+
					'<input name="TA_ZT_VISITOR/ISCHILD['+parseInt(row-1)+']" value="1" type="checkbox" onclick="addM5();" class="ertong"/>童'+
					'<input name="TA_ZT_VISITOR/ISINSURANCE['+parseInt(row-1)+']" type="checkbox" class="bxbox" value="1"/>保'+
					'</div>');
		  }
		  $(".zylxr:eq("+0+")").attr("checked","checked");
		}else{
			row = row - a;
			for(var d=a;d>0;d--){
				var idx=parseInt($("div.personInfo").size()-1);
				$("div.personInfo:eq("+idx+")").remove();
			}
		}
		addM();
		addM3();
		addM5();
	}
	

	$("#showVisitor").hover(function(){
		$(this).css({cursor:'pointer'});
		});
	$("#showVisitor").click(function(){
		$("div.personInfo").toggle();
	});

function zylxr(num){
	$(".zylxr").each(function(i){
		if(num!=i){
			$(".zylxr:eq("+i+")").removeAttr("checked");
		}
	});
}


	
</script>

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
	<%for (int i = 0; i < rd.getTableRowsCount("TA_ZT_LINEDETAI4Gs"); i++) {%>
		CKEDITOR.replace('XCMX'+<%=i%>);
	<%}%>
	CKEDITOR.config.toolbarStartupExpanded = false;
	CKEDITOR.config.resize_enabled = true;
};
<%int rows1 = rd.getTableRowsCount("TA_ZT_GATHER_HISs");%>
var arrays = new Array(<%=rows1%>);
<%//线路的集合地点
			for (int i = 0; i < rd.getTableRowsCount("TA_ZT_GATHER_HISs"); i++) {
				String gatherID = rd.getStringByDI("TA_ZT_GATHER_HISs",
						"GATHER_ID", i);
				String gather = rd.getStringByDI("TA_ZT_GATHER_HISs", "GATHER",
						i);
				String gatherTime = rd.getStringByDI("TA_ZT_GATHER_HISs",
						"GATHER_TIME", i);
				String addMCount = rd.getStringByDI("TA_ZT_GATHER_HISs",
						"ADD_M_COUNT", i);%>
	arrays[<%=i%>] = new Array(3);
	arrays[<%=i%>][0] = "<%=gatherID%>";
	arrays[<%=i%>][1] = "<%=gatherTime%>";
	arrays[<%=i%>][2] = "<%=addMCount%>";
	arrays[<%=i%>][3] = "<%=gather%>";
<%}%>
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

$(function(){
	$("#DAY_COUNTS").change(function(){
		var rows = parseInt($(".XCMXType").size());
		var days = $("#DAY_COUNTS").val();
		var addRows = (days - rows) > 0 ? (days - rows) : Math.abs(days - rows);
		if((days - rows) > 0){
			for(var i=0;i<addRows;i++){

				var row = parseInt($(".XCMXType").size());
				var QJBL = Math.floor(Math.random()*1024);
				$("#xlmc").append('<tr class="XCMXType">'+
					'<td><font size="6">D'+parseInt(row+1)+'</font><input type="hidden" name="TA_ZT_LINEDETAI4G/RQ['+parseInt(row)+']" value="'+parseInt(row+1)+'"/></td>'+
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
				
				CKEDITOR.config.toolbarStartupExpanded = false;
				CKEDITOR.config.resize_enabled = false;
			}
		}else{
			for(var d=addRows;d>0;d--){
				var idx=parseInt($("tr.XCMXType").size()-1);
				$("tr.XCMXType:eq("+idx+")").remove();
			}
		}
		addM();
	});


	<%-- $("#dataAddBtn").click(function(){
		var rowsSize = $(".addPriceRows").size();
		$("#store").append('<tr class="addPriceRows">'+
		'<td>'+
		  '<select name="TA_ZT_GPRICE/price_type['+parseInt(rowsSize)+']">'+
		  <%for (int i = 0; i < rd.getTableRowsCount("jglx"); i++) {
				String dmz = rd.getStringByDI("jglx", "dmz", i);%>
		    '<option value="<%=dmz%>" <%if (i == 0) {%>selected="selected"<%}%>><%=rd.getStringByDI("jglx", "dmsm1", i)%></option>'+
		  <%}%>
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
	}); --%>


	$("#dataDelBtn").click(function(){
		var rowsSize2 = $(".addPriceRows").size();
		rowsSize2 = rowsSize2 - 1;
		$("tr.addPriceRows:eq("+parseInt(rowsSize2)+")").remove();
		addM();
	});
});


	
$(function(){
	$("#jhddAddBtn").click(function(){
	var num=$(".gather").size();
	$("#gather").append('<tr class="gather"><td>'+
			'<select name="TA_ZT_GATHER/GATHERHIS_ID['+num+']" id="gatherID'+num+'" onchange="selectChange('+num+');addM();" class="gatherID">'+
			'<option value="-1">***请选择***</option>'+
			<%for (int i = 0; i < rd.getTableRowsCount("TA_ZT_GATHER_HISs"); i++) {
				String gather = rd.getStringByDI("TA_ZT_GATHER_HISs", "GATHER",
						i);
				String gatherTime = rd.getStringByDI("TA_ZT_GATHER_HISs",
						"GATHER_TIME", i);%>
			'<option value="<%=rd.getStringByDI("TA_ZT_GATHER_HISs", "GATHER_ID", i)%>"><%=gather%>---<%=gatherTime%></option>'+
			<%}%>
			'</select>'+
			'</td>'+
			'<td><input type="text" name="TA_ZT_GATHER/GATHER_TIME['+num+']" id="gatherTime'+num+'"/></td>'+
			'<td><input type="text" name="TA_ZT_GATHER/ADD_M_COUNT['+num+']" id="addMCount'+num+'" class="addMCount" onchange="addM();" onkeydown="checkNum()" size="10"/>'+
			'<input type="hidden" name="TA_ZT_GATHER/GATHER['+num+']" id="gather'+num+'"/>'+
			'</td>'+
			'<td colspan="1"> <textarea name="TA_ZT_GATHER/REMARK[0]" rows="2" cols="32"></textarea>'+
			'</td></tr>');
	});
	$("#jhddAddBtnDel").click(function(){
		var rowsSize2 = $(".gather").size();
		rowsSize2 = rowsSize2 - 1;
		$("tr.gather:eq("+parseInt(rowsSize2)+")").remove();
		addM();
	});
});
$(function(){
	$("#insuranceList select").change(function(){
	    var val=$(this).val();
	    var val1 = $(this).attr("id").split('-')[1];
	    $("#bxid"+val1).val(val.split('-')[0]);
        //$("#bxmc"+val1).val(val.split('-')[1]);
        $("#bxcb"+val1).val(val.split('-')[2]);
        $("#jyjg"+val1).val(val.split('-')[3]);
        $("#cbfs1"+val1).val(val.split('-')[4]);
        if(val.split('-')[4]==1){
        	$("#cbfs"+val1).text("按天数计算");
        }else if(val.split('-')[4]==0){
        	$("#cbfs"+val1).text("按实际成本计算");
        }
	    $("#insuranceList select[id!="+$(this).attr("id")+"]").each(function(){
	          if(val==$(this).val()&&'9999'!=val){
	                alert("该保险已经存在！请重新选择");
	                $("#select-"+val1+"-").val('9999');
	                $("#bxid"+val1).val('');
	                //$("#bxmc"+val1).val('');
	                $("#bxcb"+val1).val('');
	                $("#jyjg"+val1).val('');
	                $("#cbfs"+val1).text('');
	                $("#cbfs1"+val1).val('');
	                $("#bxrs"+val1).val('');
	          }
	    });
	    addM();
	});

	$(".insuranceAdd").click(function(){
		var dRows = $(".insurances").size();
		$("#insuranceList").append('<tr class="insurances" id="insurances'+dRows+'">'+
				'<td>'+
				'<img src="<%=request.getContextPath()%>/images/printClose.gif" class="delInsurances" onclick="delInsurances('+dRows+');"/>&nbsp;'+
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
				'<input type="hidden" id="cbfs1'+dRows+'"  class="cbfs" >'+
				'<input type="hidden" name="TA_ZT_GINSURANCE/INSURANCETYPE['+dRows+']" id="bxid'+dRows+'" /></td>'+
				'<td><input type="text"  id="jyjg'+dRows+'" onchange="addM()" name="TA_ZT_GINSURANCE/INSURANCEPRICE['+dRows+']" class="bxjg" onkeydown="checkNumX();" readonly="readonly"/></td>'+
				'<td class="thjsj" style="display: none;"><input type="text" id="bxcb'+dRows+'" class="bxcb" name="TA_ZT_GINSURANCE/INSURANCECOST['+dRows+']" readonly="readonly"/></td>'+
				'<td id="cbfs'+dRows+'"></td>'+
				'<td><input type="text" onchange="addM();addM3();" class="bxrs" onkeydown="checkNum()" name="TA_ZT_GINSURANCE/PERSONS['+dRows+']" id="bxrs'+dRows+'"/></td>'+
			'</tr>');
			$("#insuranceList select").bind("change", function(){
			 	var val=$(this).val();
			    var val1 = $(this).attr("id").split('-')[1];
			    $("#bxid"+val1).val(val.split('-')[0]);
		        //$("#bxmc"+val1).val(val.split('-')[1]);
		        $("#bxcb"+val1).val(val.split('-')[2]);
		        $("#jyjg"+val1).val(val.split('-')[3]);
		        $("#cbfs1"+val1).val(val.split('-')[4]);
		        if(val.split('-')[4]==1){
		        	$("#cbfs"+val1).text("按天数计算");
		        }else if(val.split('-')[4]==0){
		        	$("#cbfs"+val1).text("按实际成本计算");
		        }
			$("#insuranceList select[id!="+$(this).attr("id")+"]").each(function(){
		          if(val==$(this).val()&&'9999'!=val){
		                alert("该保险已经存在！请重新选择");
		                $("#select-"+val1+"-").val('9999');
		                $("#bxid"+val1).val('');
		                //$("#bxmc"+val1).val('');
		                $("#bxcb"+val1).val('');
		                $("#jyjg"+val1).val('');
		                $("#cbfs"+val1).text('');
		                $("#cbfs1"+val1).val('');
		                $("#bxrs"+val1).val('');
		          }
		    });
			addM();
		});
		
	});

		$("#insuranceDel").click(function(){
			var iRows = $(".insurances").size()-1;
			$(".insurances:eq("+parseInt(iRows)+")").remove();
			addM();
		});
});

// 价格类型
$(function(){
	var row = 0;
	// 添加价格类型
	$(".priceTypeAdd").click(function(){
		// 当前已存在的价格类型数量
		var aRows = $(".traveType").size();
		// 在指定的TABEL里加入新的价格类型信息
		$("#listStore").append('<tr class="traveType">'+
				'<td>'+
				'<select name="TA_ZT_GPRICE/PRICE_TYPE['+aRows+']">'+
					<%for (int i = 0; i < rd.getTableRowsCount("JGLX"); i++) {%>
								'<option value="<%=rd.getStringByDI("JGLX", "dmz", i)%>" ><%=rd.getStringByDI("JGLX", "dmsm1", i)%></option>'+
							<%}%>
				'</select>'+
			'</td>'+
			'<td><input type="text" name="TA_ZT_GPRICE/PRICE_MS['+aRows+']" size="10" class="priceMs"  onchange="addM();" onkeydown="checkNumX();"/>'+
			'</td>'+
			'<td><input type="text" name="TA_ZT_GORDERPRICE/PERSON_COUNT['+aRows+']" value="" '+
				'class="smallInput personNum" onchange="addM();personNum()" '+
				'onkeydown="checkNum()" maxlength="3" /><br>'+
			'<td colspan="1">'+
		  		'<textarea name="TA_ZT_GORDERPRICE/REMARK['+aRows+']" rows="2" cols="32"></textarea>'+
			'</td>'+
		'</tr>');
			addM();
			addM3();
			addM5();
		});

	// 删除价格类型
	$("#priceTypeDel").click(function(){
		// 当前已存在的价格类型数量
		var aRows = $(".traveType").size();
		// 删除最后的价格类型
		$(".traveType:eq("+(parseInt(aRows)-1)+")").remove();
		personNum();
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
	var canBx = false;
	$(".bxrs").each(function (i){
		if('' == $(this).val() || '0' == $(this).val()){
			canBx = true;
		}
	});
	
	var canJh = false;
	$(".gatherID").each(function (i){
		if('-1' == $(this).val()){
			canJh = true;
		}
	});
	
	if(canDo == true){
		alert("请输入 有效的门市价格！");
		return false;
	}else if(canBx == true){
		alert("请输入 保险人数！");
		return false;
	}else if(canJh == true){
		alert("没选择的集合地点 请删除！");
		return false;
	}else if($("#bDate").val()==''||$("#eDate").val()==''){
		alert("请选择 开始时间 和 结束时间 ！");
		return false;
	}else if($(".days").val()==''){
		alert("请输入天数 ！");
		return false;
	}else{
		document.lineInfoForm.submit();
		reload(); 
	}
}

function addM(){	
	var addMCount=0;//集合加价统计
	$(".addMCount").each(function(){
		if($(this).val()==''){
			addMCount+=0;
		}else{
			addMCount+=parseFloat($(this).val());
		}
	});
	var priceCount=0;//价格类型统计
	$(".personNum").each(function(i){
		if($(this).val()==''||$(".priceMs:eq("+i+")").val()==''){
			priceCount+=0;
		}else{
			priceCount+=parseFloat($(this).val())*parseFloat($(".priceMs:eq("+i+")").val());
		}
	});
	//保险类型价格 bxjg bxrs cbfs days
	var bxCount=0;
	$(".bxrs").each(function(i){
		
			if($(".cbfs:eq("+i+")").val()=="0"){
				if($(this).val()==''||$(".bxjg:eq("+i+")").val()==''){
					bxCount+=0;
				}else{
					bxCount+=Math.round(parseFloat($(this).val())*parseFloat($(".bxjg:eq("+i+")").val())*100)/100;
				}
			}else if($(".cbfs:eq("+i+")").val()=="1"){
				if($(this).val()==''||$(".bxjg:eq("+i+")").val()==''||$(".days").val()==''){
					bxCount+=0;
				}else{
					bxCount+=Math.round(parseFloat($(this).val())*parseFloat($(".bxjg:eq("+i+")").val())*parseFloat($(".days").val())*100)/100;
				}
			}
	});
	var yskzj=parseFloat(addMCount)+parseFloat(priceCount)+parseFloat(bxCount);//预算款总计
	$(".yskzj").val(yskzj);
	if(yskzj=='')yskzj='0';
	var yskxf = $(".yskxf").val();//预算款现付
	if(yskxf=='')yskxf='0';
	if(parseFloat(yskzj)<parseFloat(yskxf)){
	    $(".yskxf").val(0);//预算款签单
	    $(".yskqd").val(yskzj);
	}else{
		$(".yskqd").val(parseFloat(yskzj)-parseFloat(yskxf));//预算款签单
	}
}
function addM2(){
	var yskzj = $(".yskzj").val();
	if(yskzj=='')yskzj='0';
	var yskxf = $(".yskxf").val();//预算款现付
	if(yskxf=='')yskxf='0';
	if(parseFloat(yskzj)<parseFloat(yskxf)){
	    $(".yskxf").val(0);//预算款签单
	    $(".yskqd").val(yskzj);
	}else{
		$(".yskqd").val(parseFloat(yskzj)-parseFloat(yskxf));//预算款签单
	}
}
function addM3(){
	$(".bxbox").each(function(){//移除保险checkbox
		$(this).removeAttr("checked");	
	});
	var maxBx = 0;
	var bxLen = $(".bxbox").length;
	$(".bxrs").each(function(i){
		if($(".bxrs:eq("+i+")").val()>=$(".bxrs:eq("+(i-1)+")").val()){
			maxBx=$(".bxrs:eq("+i+")").val();
			if(maxBx>bxLen){
				$(".bxrs:eq("+i+")").val(0);
			}
		}
	});
	for(var i = 0; i < maxBx; i++){
		$(".bxbox:eq("+i+")").attr("checked","checked");
	}
}
function addM4(){
	$(".ertong").each(function(){//移除保险checkbox
		$(this).removeAttr("checked");	
	});
	var personNum=$(".ertong").length; //总人数
	var etrs=$(".etrs").val();
	if(etrs!=''&&etrs!='0'&&parseFloat(etrs)<=parseFloat(personNum)){
		for(var i = 0; i < etrs; i++){
			$(".ertong:eq("+i+")").attr("checked","checked");
		}
		$(".crrs").val(parseFloat(personNum)-parseFloat(etrs));
	}else{
		addM5();
	}
}
function addM5(){
	//ertong etrs crrs personNum
	var personNum=$(".ertong").length; //总人数
	if(personNum=='')
		personNum='0';
	var erTong=$(".ertong:checked").length;//儿童人数
	if(erTong=='')
		erTong='0';
	
	
	$(".crrs").val(parseFloat(personNum)-parseFloat(erTong));
	$(".etrs").val(erTong);
}
</script>
</head>
<body>
	<form action="ztAddNewGroup." name="lineInfoForm" method="post">
	
<div class="width-99">
		<div class="add-table">
			<table border="0">
				<tr>
					<td align="right">线路名称：</td>
					<td colspan="5">
						<input type="text" name="TA_ZT_GROUP/XLMC" style="width: 500px;">&nbsp;<span>*</span>
						&nbsp;&nbsp;
						<a href="###">选取相似的线路创建</a>
					</td>
					
				</tr>
				<tr>
					<td align="right">国内联系电话：</td>
					<td><input type="text" name="TA_ZT_GROUP/GNLXDH" /></td>
					<td align="right">机场联系电话：</td>
					<td><input type="text" name="TA_ZT_GROUP/JCLXDH" /></td>
					<td align="right">退改规则：</td>
					<td><input type="text" name="TA_ZT_GROUP/RETURN_ROLE" id="RETURN_ROLE" /></td>
				</tr>
				<tr>
					<td align="right">开始时间：</td>
					<td><input type="text" name="TA_ZT_GROUP/BEGIN_DATE" onFocus="WdatePicker({isShowClear:false,readOnly:true});" id="bDate" /></td>
					<td align="right">结束时间：</td>
					<td><input type="text" name="TA_ZT_GROUP/END_DATE" onFocus="WdatePicker({isShowClear:false,readOnly:true});" id="eDate" /></td>
					<td align="right">天数：</td>
					<td><input type="text" name="TA_ZT_GROUP/DAYS" id="DAY_COUNTS" class="days" onchange="addM();" onkeydown="checkNum();"/></td>
				</tr>
				
				<tr>
					<td align="right">线路类型：</td>
					<td><select name="TA_ZT_LINEMNG/LINE_TYPE">
							<%
								for (int i = 0; i < rd.getTableRowsCount("DMSMS"); i++) {
									if ("3".equals(rd.getStringByDI("DMSMS", "dmlb", i))) {
										String dmz = rd.getStringByDI("DMSMS", "dmz", i);
							%>
							<option value="<%=dmz%>"><%=rd.getStringByDI("DMSMS", "dmsm1", i)%></option>
							<%
								}
								}
							%>
					</select></td>
					<td align="right">线路区域：</td>
					<td><select name="TA_ZT_LINEMNG/XLQY">
							<%
								for (int i = 0; i < rd.getTableRowsCount("DMSMS"); i++) {
									if ("20".equals(rd.getStringByDI("DMSMS", "dmlb", i))) {
										String dmz = rd.getStringByDI("DMSMS", "dmz", i);
							%>
							<option value="<%=dmz%>"><%=rd.getStringByDI("DMSMS", "dmsm1", i)%></option>
							<%
								}
								}
							%>
					</select></td>
					<td align="right">保险：</td>
					<td><input type="text" name="TA_ZT_GROUP/INSURANCE"
						id="INSURANCE" /></td>
				</tr>
				
				<tr>
					<td align="right">是否自备车：</td>
					<td><input type="radio" name="TA_ZT_GROUP/VEHICLE_TYPE"
						value="1" />是 <input type="radio" name="TA_ZT_GROUP/VEHICLE_TYPE"
						value="2" checked="checked" />否</td>
						<td align="right">游客类型：</td>
					<td><input type="radio" name="TA_ZT_GROUP/TOURIST_TYPE"
						value="1" checked="checked" />团队 <input type="radio"
						name="TA_ZT_GROUP/TOURIST_TYPE" value="2" />散客</td>
					<td align="right">加点购物：</td>
					<td><input type="checkbox" name="TA_ZT_GROUP/JD" value="1" />加点
						<input type="checkbox" name="TA_ZT_GROUP/GW" value="1" />购物</td>
					
				</tr>
				
				<tr>
					<td align="right">出发交通：</td>
					<td class="cfjt"><select name="TA_ZT_GROUP/B_TRAFFIC"
						onchange="showInfo(this.value,'cfjt');">
							<%
								String BT = rd.getStringByDI("TA_ZT_GROUPs", "B_TRAFFIC", 0);
								if ("".equals(BT)) {
									for (int i = 0; i < rd.getTableRowsCount("JTGJ"); i++) {
							%>
							<option value="<%=rd.getStringByDI("JTGJ", "dmz", i)%>"><%=rd.getStringByDI("JTGJ", "dmsm1", i)%></option>
							<%
								}
								} else {
									for (int i = 0; i < rd.getTableRowsCount("JTGJ"); i++) {
										String dmz = rd.getStringByDI("JTGJ", "dmz", i);
							%>
							<option value="<%=dmz%>" <%if (dmz.equals(BT)) {%>
								selected="selected" <%}%>><%=rd.getStringByDI("JTGJ", "dmsm1", i)%></option>
							<%
								}
								}
							%>
					</select></td>
					<td align="right" class="cfjtTran"
						style="display:block">
						航班号/车次：</td>
					<td class="cfjtTran"
						style="display:block">
						<input type="text" name="TA_ZT_GROUP/CFHBCC"
						value="<%=rd.getString("TA_ZT_GROUPs", "CFHBCC", 0)%>" />
					</td>
					<td align="right" class="cfjtTime"
						style="display:block">
						出发时间：</td>
					<td class="cfjtTime"
						style="display:block">
						<input type="text" name="TA_ZT_GROUP/CFSJ"
						value="<%=rd.getString("TA_ZT_GROUPs", "CFSJ", 0)%>"
						onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});" />
					</td>
				</tr>
				<tr>
					<td align="right">返回交通：</td>
					<td class="fhjt"><select name="TA_ZT_GROUP/E_TRAFFIC"
						onchange="showInfo(this.value,'fhjt');">
							<%
								String ET = rd.getStringByDI("TA_ZT_GROUPs", "E_TRAFFIC", 0);
								if ("".equals(ET)) {

									for (int i = 0; i < rd.getTableRowsCount("JTGJ"); i++) {
							%>
							<option value="<%=rd.getStringByDI("JTGJ", "dmz", i)%>"><%=rd.getStringByDI("JTGJ", "dmsm1", i)%></option>
							<%
								}
								} else {

									for (int i = 0; i < rd.getTableRowsCount("JTGJ"); i++) {
										String dmz = rd.getStringByDI("JTGJ", "dmz", i);
							%>
							<option value="<%=dmz%>" <%if (dmz.equals(ET)) {%>
								selected="selected" <%}%>><%=rd.getStringByDI("JTGJ", "dmsm1", i)%></option>
							<%
								}
								}
							%>
					</select></td>
					<td align="right" class="fhjtTran"
						style="display:block">
						航班号/车次：</td>
					<td class="fhjtTran"
						style="display:block">
						<input type="text" name="TA_ZT_GROUP/FHHBCC"
						value="<%=rd.getString("TA_ZT_GROUPs", "FHHBCC", 0)%>" />
					</td>
					<td align="right" class="fhjtTime"
						style="display:block">
						返回时间：</td>
					<td class="fhjtTime"
						style="display:block">
						<input type="text" name="TA_ZT_GROUP/FHSJ"
						value="<%=rd.getString("TA_ZT_GROUPs", "FHSJ", 0)%>"
						onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});" />
					</td>
				</tr>
			</table>
		</div>






<!------------ 集合地点 ------------>


		<div style="text-align: right">
			<a href="###" id="jhddAddBtn"><img alt="添加集合地点"
				src="<%=request.getContextPath()%>/images/add.gif" /> 添加</a>&nbsp;&nbsp;
			<a href="###" id="jhddAddBtnDel"><img alt="删除集合地点"
				src="<%=request.getContextPath()%>/images/del.gif" /> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
		<div id="list-table">
		<table width="100%" class="datas" style="text-align: center"
			id="gather">
			<tr id="first-tr">
				<td width="25%">集合地点</td>
				<td width="25%">集合时间</td>
				<td width="25%">加价</td>
				<td width="25%">备注</td>
			</tr>
			<tr class="gather">
				<td><select name="TA_ZT_GATHER/GATHERHIS_ID[0]" id="gatherID0"
					class="gatherID" onchange="selectChange('0'); addM();" >
						<option value="-1">***请选择***</option>
						<%
							for (int i = 0; i < rd.getTableRowsCount("TA_ZT_GATHER_HISs"); i++) {
								String gather = rd.getStringByDI("TA_ZT_GATHER_HISs", "GATHER",
										i);
								String gatherTime = rd.getStringByDI("TA_ZT_GATHER_HISs",
										"GATHER_TIME", i);
						%>
						<option
							value="<%=rd.getStringByDI("TA_ZT_GATHER_HISs", "GATHER_ID", i)%>"><%=gather%>---<%=gatherTime%></option>
						<%
							}
						%>
				</select></td>
				<td><input type="text" name="TA_ZT_GATHER/GATHER_TIME[0]"
					id="gatherTime0" size="20" /></td>
				<td><input type="text" name="TA_ZT_GATHER/ADD_M_COUNT[0]" id="addMCount0" onchange="addM();" class="addMCount" size="10"  onkeydown="checkNum()"/> <input
					type="hidden" name="TA_ZT_GATHER/GATHER[0]" id="gather0" /></td>
				<td><textarea name="TA_ZT_GATHER/REMARK[0]"
						rows="2" cols="32"></textarea></td>
			</tr>
		</table>
		</div>
		
		
	
	<!------------ 价格类型 ------------>
	
	
		<div style="text-align: right">
			<a href="###" class="priceTypeAdd"><img alt="添加价格类型"
				src="<%=request.getContextPath()%>/images/add.gif" /> 添加</a>&nbsp;&nbsp;
			<a href="###" id="priceTypeDel"><img alt="添加价格类型"
				src="<%=request.getContextPath()%>/images/del.gif" /> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
		<div id="list-table">
			<table class="datas" id="listStore">
				<tr id="first-tr">
					<td width="25%">价格类型</td>
					<td width="25%">单价</td>
					<td width="25%">人&nbsp;&nbsp;&nbsp;&nbsp;数</td>
					<td width="25%">备注</td>
				</tr>
				<tr class="traveType">
					<td><select name="TA_ZT_GPRICE/PRICE_TYPE">
							<%
								for (int i = 0; i < rd.getTableRowsCount("JGLX"); i++) {
							%>
							<option value="<%=rd.getStringByDI("JGLX", "dmz", i)%>"><%=rd.getStringByDI("JGLX", "dmsm1", i)%></option>
							<%
								}
							%>
					</select></td>
					<td><input type="text" name="TA_ZT_GPRICE/PRICE_MS[0]" size="10" class="priceMs" onchange="addM();" onkeydown="checkNumX();"/></td>
					<td><input type="text" name="TA_ZT_GORDERPRICE/PERSON_COUNT[0]" value=""  onchange="addM();personNum();" class="smallInput personNum" onkeydown="checkNum()" maxlength="3"/><br>
					<td><textarea name="TA_ZT_GORDERPRICE/REMARK[0]"
							rows="2" cols="32"></textarea></td>
				</tr>
			</table>
		</div>
		
		
		
		
		<!------------ 保险类型 ------------>
		
	<div style="text-align: right">
		<a href="###" class="insuranceAdd"><img alt="添加保险" src="<%=request.getContextPath() %>/images/add.gif"/> 添加</a>&nbsp;&nbsp;
       <a href="###" id="insuranceDel"><img alt="添加保险" src="<%=request.getContextPath() %>/images/del.gif"/> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	<div id="list-table">
	<table class="datas" id="insuranceList"  style="text-align: center">
	<tr id="first-tr" >
		<td width="25%">保险类型</td>
		<td width="25%">保险价格</td>
		<td  width="25%" class="thjsj" style="display: none;">保险成本</td>
		<td width="25%">成本方式</td>
		<td width="25%">人数</td>
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
			<select class="changeInsurance" id="select-<%=i%>-"  onchange="addM();">
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
			</select><input type="hidden" id="cbfs1<%=i%>"  class="cbfs" value="<%=rd.getStringByDI("TA_ZT_INSURANCEs", "cbfs", sId)%>">
				<input type="hidden" name="TA_ZT_GINSURANCE/INSURANCETYPE[<%=i%>]" value="<%=rd.getStringByDI("TA_ZT_INSURANCEs", "ID", sId)%>" id="bxid<%=i%>" /></td>
				<td><input type="text"  id="jyjg<%=i%>" value="<%=rd.getStringByDI("TA_ZT_INSURANCEs", "jyjg", sId) %>"  onchange="addM()" onkeydown="checkNumX();" name="TA_ZT_GINSURANCE/INSURANCEPRICE[<%=i %>]" readonly="readonly"/></td>
				<td class="thjsj" style="display: none;"><input type="text"  id="bxcb<%=i%>" class="bxcb" onkeydown="checkNum1(this)" name="TA_ZT_GINSURANCE/INSURANCECOST[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_INSURANCEs", "bxCb", sId) %>" readonly="readonly"/></td>
				<td id="cbfs<%=i%>"><%if("1".equals(rd.getStringByDI("s", "cbfs", sId))){out.print("按天计算");}else{out.print("按实际成本计算");} %></td>
				<td><input  type="text" onchange="addM();addM3();" class="bxrs" onkeydown="checkNum()" name="TA_ZT_GINSURANCE/PERSONS[<%=i %>]" id="bxrs<%=i %>"/></td>
		</tr>
		<% }%>
	</table> 
	</div>
	
	
		
		
		<!-- ----------预算款---------- -->
		
		<table class="datas">
				<tr>
					<td colspan="11" align="right">
					<font color="red">房间数:</font>
					<input name="TA_ZT_YKORDER/ROOMS" type="text" onkeydown="checkNum()" value="0" size="6"/>间&nbsp;&nbsp;
					<font color="red">人数:</font>
					<input name="TA_ZT_GROUP/ADULT_COUNT" class="smallInput crrs" type="text" onkeydown="checkNum();" value="0"  readonly="readonly"/>人&nbsp;&nbsp; 
				<!--
					<font color="red">儿童人数:</font>-->	
					<input name="TA_ZT_GROUP/CHILDREN_COUNT" class="smallInput etrs" type="hidden" value="0" onkeydown="checkNum();"  onchange="addM4();" /> 
				
					<font color="red">预算款现付金额:</font>
					<input name="TA_ZT_YKORDER/YI_SK" class="smallInput yskxf" type="text" onkeydown="checkNum();"value="0" onchange="addM2();"/>元&nbsp;&nbsp; 
					<font color="red">预算款签单金额:</font>
					<input name="TA_ZT_GROUP/YSKQD" class="smallInput yskqd" type="text" value="0" readonly="readonly" />元&nbsp;&nbsp; 
					<font color="red">预算款总计:</font><input name="TA_ZT_YKORDER/YIN_SK" class="smallInput yskzj" type="text" value="0"  onchange="addM2();"/>元&nbsp;&nbsp; &nbsp;&nbsp;</td>
				</tr>
			</table>
		
		
		
		
		

		<div id="tourist" style="width: 99.8%">
			<div><span>游客信息    <span id="showVisitor">显示</span></span></div>
			<div><font color="red">注：证件号码出境或飞机团游客必填，需要是护照或身份证等有效证件；主要联系人必须至少选择一个</font></div>
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
				<li class="ckeditorli">
					<a class="a" href="###" style="color:#006666;" onclick="xcmx1('10');">备注</a>
				<ul>
					<li>
					<div>
						<table>
						<tr>
					 		<td align="right" width="10%">线路备注：</td>
							<td ><textarea name="TA_ZT_LINEMNG/COMMENTS" cols="90" rows="2"></textarea></td>
						 </tr>
						 <tr>
							<td align="right" width="10%">团备注：</td>
							<td><textarea name="TA_ZT_GROUP/COMMENTS" rows="2"
									cols="90"></textarea></td>
						</tr>
					 </table>
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
	
	 <input type="hidden" class="xcmx1"  value="1"/>
	 <input type="hidden" class="xcmx2"  value="1"/>
	 <input type="hidden" class="xcmx3"  value="1"/>
	 <input type="hidden" class="xcmx4"  value="1"/>
	 <input type="hidden" class="xcmx5"  value="1"/>
	 <input type="hidden" class="xcmx6"  value="1"/>
	 <input type="hidden" class="xcmx7"  value="1"/>
	 <input type="hidden" class="xcmx8"  value="1"/>
	 <input type="hidden" class="xcmx9"  value="1"/>
	 <input type="hidden" class="xcmx10"  value="1"/>
</div>

		<div align="center" id="last-sub">
			<%
				String isClose = rd.getStringByDI("TA_ZT_GROUPs", "FTZT", 0);
			%>
			<input type="checkbox" name="TA_ZT_YKORDER/ISCONFIRM" value="1" checked="checked"/>是否确定收款
			<input type="button" id="button" value="提交"
				onclick="addTraAgcCheck();" /> <input type="button" id="button"
				value="返回" onclick="window.history.go(-1)" /> <input type="button"
				id="button" value="关闭"
				onclick="reload()" />
		</div>
		</div>
		

	</form>
</body>
</html>