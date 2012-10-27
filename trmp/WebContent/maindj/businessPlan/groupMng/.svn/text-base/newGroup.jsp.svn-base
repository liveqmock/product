<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.Blob"%>
<%@page import="java.io.InputStreamReader"%>

<%@page import="com.trmdj.businessPlan.groupMng.GroupLineDetail"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>

<%
String roleID = sd.getString("USERROLEST");
boolean isTrue = false;
if(!"".equals(roleID)){
	
	roleID = roleID.substring(1,roleID.length()-1);
	String[] roleIDs = roleID.split(",");
	for(int i=0;i<roleIDs.length;i++){
		if("admin".equals(roleIDs[i].trim()) || "transferForDj".equals(roleIDs[i].trim())){
			isTrue = true;
			break;
		}
	}
}
String canEdit = rd.getString("canEdit");
String gIDStr = rd.getStringByDI("TA_DJ_GROUPs", "ID", 0);

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jqueryui/jqueryui.js" ></script>
<!-- thickbox begin -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>
<link href="<%=request.getContextPath()%>/js/thickbox/thickbox.css" rel="stylesheet" type="text/css"/>
<!-- thickbox end -->
<!-- dwr begin -->
<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/engine.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/util.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/interface/PYAjaxService.js" ></script>
<!-- dwr end -->
<script type="text/javascript">
$(function(){
	$( "input:submit,input:button").button();
	<%
		int XCMXRow = rd.getTableRowsCount("TA_DJ_LINEDETAILs");
		for(int i = 0; i<XCMXRow; i++){
	%>
    	CKEDITOR.replace('XCMX<%=i%>');
        CKEDITOR.config.toolbarStartupExpanded = false;
        CKEDITOR.config.resize_enabled = true;
    <%
		}
    %>
});

	function add_XCMX(){
		var QJBL = Math.floor(Math.random() * 1024);
		var XCMXRows = parseInt(jQuery(".XCMXType").size());
		jQuery("#store1").append(
			  '<tr class="XCMXType">'
			+ '<td>'
			+ '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="6">D'+ (XCMXRows + 1)+ '</font><input type="hidden" name="TA_DJ_LINEDETAI4G/RQ['+ XCMXRows+ ']" value="'+ (XCMXRows + 1)+ '"/>'
			+ '<br>'
			+ '<br>'
			+ '&nbsp;&nbsp;&nbsp;&nbsp;含餐：<input type="checkbox" name="TA_DJ_LINEDETAI4G/BREAKFAST['+XCMXRows+']" value="Y"/>早'
			+ '<input type="checkbox" name="TA_DJ_LINEDETAI4G/CMEAL['+XCMXRows+']" value="Y"/>中'
			+ '<input type="checkbox" name="TA_DJ_LINEDETAI4G/SUPPER['+XCMXRows+']" value="Y"/>晚'
			+ '<br>'
			+ '<br>&nbsp;&nbsp;&nbsp;&nbsp;'
			+ '餐标：'
			+ '<input type="text" name="TA_DJ_LINEDETAI4G/CB['+XCMXRows+']" value="" class="smallerInput1"/>'
			+ '<br>'
			+ '<br>&nbsp;&nbsp;&nbsp;&nbsp;'
			+ '住宿：'
			+ '<input type="text" name="TA_DJ_LINEDETAI4G/ZS['+XCMXRows+']" value="" class="smallerInput1"/>'
			+ '<br>'
			+ '<br>&nbsp;&nbsp;&nbsp;&nbsp;'
			+ '房标：'
			+ '<select name="TA_DJ_LINEDETAI4G/ZSBZ['+XCMXRows+']" style="width: 100px;">'
				<%for (int i = 0; i < rd.getTableRowsCount("JDXJ"); i++) {%>
			+ '<option value="<%=rd.getStringByDI("JDXJ", "dmz", i)%>" <%if (i == 0) {%> selected="selected" <%}%>><%=rd.getStringByDI("JDXJ", "dmsm1", i)%></option>'
				<%}%>
			+ '</select>'
			+ '</td>'
			+ '<td>'
			+ '<textarea id="XCMX'+QJBL+'" name="TA_DJ_LINEDETAI4G/XCMX['+XCMXRows+']" rows="0" cols="0">'
			+ '</textarea>'
			+ '</td>'
			+ '</tr>');
		CKEDITOR.replace('XCMX' + QJBL + '');
		CKEDITOR.config.toolbarStartupExpanded = false;
		CKEDITOR.config.resize_enabled = false;
	}
	function del_XCMX(){
		var tableRows = jQuery(".XCMXType").size();
		if(tableRows -= 1 > 1){
			var idx=parseInt(jQuery("tr.XCMXType").size()-1);
			jQuery("tr.XCMXType:eq("+idx+")").remove();
		}
	}
 
	function addTrave(){
		var Random = Math.floor(Math.random() * 123450);
		var tztsRows = jQuery(".traveType").size();
		jQuery("#listStore").append(
			'<tr class="traveType">'+
			'<td>'+
			'<input type="text" name="TA_DJ_TZTS/ZTSMC['+tztsRows+']" class="travel" id="'+Random+'"/>'+
			'<input type="hidden" name="TA_DJ_TZTS/XZQHID['+tztsRows+']" class="travelXZQH"/>'+
			'<input type="hidden" name="TA_DJ_TZTS/ZTSID['+tztsRows+']" class="travelId"/>'+
			'</td>'+
			'<td>成人：<input type="text" name="TA_DJ_TZTS/CRRS['+tztsRows+']" value="0" class="smallInput crrs" onchange="sumPrice()"  onkeydown="checkNum()" maxlength="3"/><br>'+
			'儿童：<input type="text" name="TA_DJ_TZTS/ETRS['+tztsRows+']" value="0" class="smallInput etrs" onchange="sumPrice()"  onkeydown="checkNum()" maxlength="3"/></td>'+
			'<td><input type="text" name="TA_DJ_TZTS/CRJG['+tztsRows+']" value="0" class="smallInput crjg" onchange="sumPrice()"  onkeydown="checkNum()" maxlength="6"/><br><input type="text" name="TA_DJ_TZTS/ETJG['+tztsRows+']" value="0" class="smallInput etjg" onchange="sumPrice()"  onkeydown="checkNum()" maxlength="6"/></td>'+
			'<td ><input type="text" name="TA_DJ_TZTS/YSZK['+tztsRows+']" value="0" class="smallInput tk" onchange="sumPrice()" onkeydown="checkNum()" maxlength="6"/></td>'+
			'<td ><input type="text" name="TA_DJ_TZTS/YFK['+tztsRows+']" value="0" class="smallInput yfk" onchange="sumPrice()"  onkeydown="checkNum()" maxlength="6"/></td>'+
			'<td ><input type="text" name="TA_DJ_TZTS/YK['+tztsRows+']" value="0" class="smallInput yk" onkeydown="checkNum()" readonly="readonly"/></td>'+
		 '</tr>');
		$(".travel").bind('keyup',function(){//模糊查询名称绑定keyup事件
			$('#itemRow').val($(".travel").index(this));
			filterChanged("travel");
		});
		tztsRows++;
	}
function delTraveRow(){
	var tztsRows = jQuery(".traveType").size();
	if(tztsRows > 0){
		var idx = tztsRows-1;
		jQuery("tr.traveType:eq("+idx+")").remove();
		sumPrice();
		tztsRows --;
	}
}	

function tableMove(){
	var tableRows = jQuery(".valueType").size();
	jQuery("#store").append('<tr class="valueType">'+
			'<td>'+
			  '<select name="TA_DJ_TDJG/JGLX['+tableRows+']">'+
			  <%
			  int typeRows = rd.getTableRowsCount("JGLX");
			  for(int j=0;j<typeRows;j++){
				  String dmz = rd.getStringByDI("JGLX","dmz",j);
				  String dmsm = rd.getStringByDI("JGLX","dmsm1",j);
			  %>
			    '<option value="<%=dmz %>"><%=dmsm %></option>'+
			  <%
			  }%>
			  '</select>'+
			'</td>'+
			'<td><input name="TA_DJ_TDJG/JG['+tableRows+']" value="0" class="smallerInput" id="price_th[0]" maxlength="5"></input></td>'+
			'<td><input name="TA_DJ_TDJG/DFC['+tableRows+']" value="0" class="smallerInput" maxlength="5"/></td>'+
			'<td >'+
			  '<textarea name="TA_DJ_TDJG/bz['+tableRows+']" rows="2" cols="53" onpropertychange="if(this.value.length>100){this.value=this.value.slice(0,100)}"> </textarea>'+
			'</td>'+
		  '</tr>');
	tableRows ++;
}

function delTabRow(){
	var tableRows = jQuery(".valueType").size();
	if(tableRows > 0){
		var idx = parseInt(tableRows-1);
		jQuery("tr.valueType:eq("+idx+")").remove();
		tableRows --;
	}
}

function sumPrice(){
	var ztk = 0;
	var zyfk = 0;
	var zyk = 0;
	var zcrrs = 0;
	var zetrs = 0;
	jQuery(".crrs").each(function(i){
		var tk = 0;
		tk += parseInt(this.value,10)*parseInt(jQuery(".crjg:eq("+i+")").val(),10);
		tk += parseInt(jQuery(".etrs:eq("+i+")").val(),10)*parseInt(jQuery(".etjg:eq("+i+")").val(),10);
		jQuery(".tk:eq("+i+")").val(tk);
		ztk += parseInt(jQuery(".tk:eq("+i+")").val(),10);
		zyfk += parseInt(jQuery(".yfk:eq("+i+")").val(),10);
		jQuery(".yk:eq("+i+")").val(parseInt(jQuery(".tk:eq("+i+")").val(),10)-parseInt(jQuery(".yfk:eq("+i+")").val(),10));
		zyk += parseInt(jQuery(".yk:eq("+i+")").val(),10);
		zcrrs += parseInt(this.value,10);
		zetrs += parseInt(jQuery(".etrs:eq("+i+")").val(),10);
	});
	jQuery("#ztk").val(ztk);
	jQuery("#zyfk").val(zyfk);
	jQuery("#zyk").val(zyk);
	jQuery("#zcrrs").val(zcrrs);
	jQuery("#zetrs").val(zetrs);
}

function submitAddGroup(){
	var xlmc=jQuery("#xlmc").val();
	if(''==xlmc){
		alert("请输入线路名称");
		return false;
	}
	
	var ts=jQuery("#DAY_COUNTS").val();
	if(''==ts || 0==ts){
		alert("请输入旅游天数");
		return false;
	}

	var bDate = jQuery("#bDate").val();
	var eDate = jQuery("#eDate").val();
	if(bDate == '' || eDate == ''){
		alert("请输入团开始时间或结束时间！");
		return false;
	}

	var ztsSize = jQuery(".traveType").size();
	for(var i=0;i<ztsSize;i++){

		var zts = jQuery("#ZTSID"+parseInt(ztsSize-1)).val();
		if('' == zts){
			alert("请选择组团社！");
			return false;
		}
	}
	document.groupInfoForm.submit();
}
 
function chageKYD(tab,tad){
	jQuery(".kyd").get(0).value=tab;
}



/*===================================================
 * 模糊查询JS模块  Start
 *===================================================
 */
	var itemName=0;
	var itemRow=0;
	var beanCache = [ ];
	var lastFilter = "";

	function fillTable(bean) {//option 结果集

	  var filter = $("."+itemName).eq(itemRow).val();
	  var pattern = new RegExp("(" + filter + ")", "i");
	  var filtered = [];
 
	  for (var i = 0; i < bean.length; i++) {
	    if (pattern.test(bean[i].sName)) {
	      filtered.push(bean[i]);
	    }
	  }
	  //清空表格所有行（仅删除所有行）
	  //dwr.util.removeAllRows("plusBody");
	  if (filtered.length == 0) {//结果集为空 隐藏所有 包含 ._li_的层
		  $("div[id^='_li_']").hide();
	  }else {
		  dmSelect(itemName, itemRow, filtered, filter);//option (itemName:input的className,row:input下标值,data:数据集,filter:搜索条件)
	  }
	  beanCache = bean;
	}

	/**
	*	item：模块
	*/
	function filterChanged(item) {
		itemRow = $("#itemRow").val();
		itemName = item;
		var filter = $("."+itemName).eq(itemRow).val();
		if (filter.length == 0)
		{
			$("div[id^='_li_']").hide();
		}
		else{
			if (filter.charAt(0) == lastFilter.charAt(0))
			{
				fillTable(beanCache);
			} 
			else 
			{
				if('travel' == itemName){
					PYAjaxService.travelExecute(filter.charAt(0), fillTable);//向后台取数据
				}
			}
		}
		lastFilter = filter;
	}

	function dmSelect(itemName, row ,data, filter){//option (itemName:input的className,row:input下标值,data:数据集,filter:搜索条件)
	    //jQuery("#" + name).attr("readonly","readonly");   //设置控件只读  
	    var Random = $("."+itemName).eq(row).attr("id");
	    var top;
	    var left;  
	    var height;  
	    var width;
	    var option_open = false; //标记是否打开下拉option
	    var options= [ ];
	    function __dropheight(l){var h;if(l>10 || l<1){h = 10 * 15;}else{h = l * 15; h += 2;}return h+30;}   //计算下拉option显示高度
	    	 top = $("#" + Random).offset().top;    //获取控件top、left位置和width、height  
	 	     left = $("#" + Random).offset().left; 
	 	     height = $("#" + Random).height();
	 	     width = $("#" + Random).width()+3;
	    	 var div_html = "<div id='_li_"+Random +"' style='position:absolute;"
				+" top:"+(top+height+5)+"px;"
				+" left:"+left+"px;"
				+" width:"+((width<30)?30:width)+"px;"
				+" height:"+__dropheight(data.length)+"px;"
				+" border:1px #666666 solid;"
				+" background-color:#FFFFFF;" 
				+" overflow-x:hidden;"
				+" overflow-y:auto;"
				+" display:none;"
				+" z-index:"+(1000-row)+";'>";  
				if(itemName == 'travel'){
					options = ["travel","travelLxr","travelLxfs", "travelId", "travelXZQH"];
					for(var i = 0;i < data.length; i++){//for循环填充option   
						div_html += "<div style='text-align:left;padding-left:5px;padding-top:2px;cursor:pointer;'>";
				    	div_html += "<label class='option"+itemName+"'>"+ data[i].sName.replace(filter,"<font color='red'>"+filter+"</font>") +"</label>";
				    	div_html += "<label class='option"+options[1]+"' style='display:none'>"+ data[i].linkman+"</label>";
				    	div_html += "<label class='option"+options[2]+"' style='display:none'>"+ data[i].linkmanTel+"</label>";
				    	div_html += "<label class='option"+options[3]+"' style='display:none'>"+ data[i].sID+"</label>";
				    	div_html += "<label class='option"+options[4]+"' style='display:none'>"+ data[i].city+"</label>";
				    	div_html += "<br><label style='display:block'><font color='green'>"+ data[i].sfName+" &nbsp;&nbsp;"+ data[i].csName+"</font></label>";
				    	div_html += "</div>";
					}
				}
				div_html += "</div>";
			$("#_li_" + Random).remove();
			$("#" + Random).after();
			$("#" + Random).after(div_html);   //添加到input控件后面   
	  
	    //alert("ns:"+ns+"-row:"+row);
	    __open_option();
	  	  
	    function __open_option(){//显示下拉option 
	    	$("div[id^='_li_']").hide();
	    	$("#_li_" + Random).show();
	    	option_open=true;
	    }   
	    function __hide_option(){//隐藏下拉option
	    	$("div[id^='_li_']").hide();
	    	option_open=false;
	    	//$(document).unbind("click",__hide_option);
	    }   
	    $("#" + Random).click(function(event){
	    	if(option_open){
	    		__hide_option();
	    	}else{
	    		__open_option();
	    		$(document).one("click",__hide_option);
	    	}
	    	event.stopPropagation();
	    });

	    $("#_li_" + Random + " > div").hover(//鼠标移动特效 以及层的click赋值事件
	    	function(){
	    		$(this).css({"background-color":"#CCCCCC"});
	    	},function(){
	    		$(this).css({"background-color":"#FFFFFF"});
	    	}).click(function(){
		    	for(var j = 0; j <= options.length; j++){
		    		$("." + options[j]+":eq("+row+")").val($(this).children(".option"+options[j]).text());
		    	};
		    	__hide_option();
	    });  
	};
	/*===================================================
	 * 模糊查询JS模块  End
	 *===================================================
	 */
	function testWin(){
		TB_show('查询旅行社信息','<%=request.getContextPath()%>/maindj/businessPlan/initSearch4Ajax/initTravelAagencySearch.jsp*TB_iframe=true&height=400&width=400','');
	 }
</script>

<title>登团</title>
</head>

<body>

<form action="djAddNewGroup.?yw=<%=rd.getString("yw") %>" name="groupInfoForm" method="post">
<div style="width: 99.5%">
	<input type="hidden" name="TA_DJ_GROUP/USER_NO" value="<%=sd.getString("userno") %>"/>
<table class="datas">
  <tr id="first-tr">
  	<td>&nbsp;&nbsp;创建新团&nbsp;&nbsp;<span>带<font color="red">*</font>号为必填项</span></td>
  </tr>
 </table>
<div style="text-align:right;" > 
 <a href="###" onclick="addTrave();"><img alt="添加" src="<%=request.getContextPath()%>/images/add.gif"> 添加</a>&nbsp;&nbsp;
 <a href="###" onclick="delTraveRow('listStore',1,'price_th');"><img alt="删除" src="<%=request.getContextPath()%>/images/del.gif"> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div> 
<div id="list-table">
<table class="datas"  id="listStore">
  <tr id="first-tr">
	<td width="25%">组&nbsp;&nbsp;团&nbsp;&nbsp;社</td>
	<td width="15%">人&nbsp;&nbsp;&nbsp;&nbsp;数</td>
	<td width="15%">单&nbsp;&nbsp;&nbsp;&nbsp;价</td>
	<td width="15%">团&nbsp;&nbsp;&nbsp;&nbsp;款</td>
	<td width="15%">预&nbsp;&nbsp;付&nbsp;&nbsp;款</td>
	<td width="15%">余&nbsp;&nbsp;&nbsp;&nbsp;款</td>
  </tr>
  <tr class="traveType">
	<td>
	  <input type="text" name="TA_DJ_TZTS/ZTSMC[0]" class="travel" id="2423423abc"/>
	  <input type="hidden" name="TA_DJ_TZTS/XZQHID[0]" class="travelXZQH"/>
	  <input type="hidden" name="TA_DJ_TZTS/ZTSID[0]" class="travelId"/>
	</td>
	<td>
		成人：<input type="text" name="TA_DJ_TZTS/CRRS[0]" value="0" class="smallInput crrs" onchange="sumPrice()"  onkeydown="checkNum()" maxlength="3"/><br>
		儿童：<input type="text" name="TA_DJ_TZTS/ETRS[0]" value="0" class="smallInput etrs" onchange="sumPrice()"  onkeydown="checkNum()" maxlength="3"/></td>
	<td>
		<input type="text" name="TA_DJ_TZTS/CRJG[0]" value="0" class="smallInput crjg" onchange="sumPrice()"  onkeydown="checkNum()" maxlength="6"/><br>
		<input type="text" name="TA_DJ_TZTS/ETJG[0]" value="0" class="smallInput etjg" onchange="sumPrice()"  onkeydown="checkNum()" maxlength="6"/>
	</td>
	<td ><input type="text" name="TA_DJ_TZTS/YSZK[0]" value="0" class="smallInput tk" onkeydown="checkNum()" maxlength="6"/></td>
	<td ><input type="text" name="TA_DJ_TZTS/YFK[0]" value="0" class="smallInput yfk" onchange="sumPrice()" onkeydown="checkNum()" maxlength="6"/></td>
	<td ><input type="text" name="TA_DJ_TZTS/YK[0]" value="0" class="smallInput yk" onkeydown="checkNum()" maxlength="6"/></td>
	</tr>
</table>
</div>
<script type="text/javascript">
$(".travel").bind('keyup',function(){//模糊查询名称绑定keyup事件
	$('#itemRow').val($(".travel").index(this));
	filterChanged('travel');
});
</script>
<div id="list-table">
<table class="datas">
  <tr>
  	<td colspan="11" align="right">
  		<font color="red">总团款：</font><input name="TA_DJ_GROUP/TKZJ" class="smallInput" type="text" value="0"   id="ztk" />元&nbsp;&nbsp;
  		<font color="red">总预付款：</font><input name="TA_DJ_GROUP/YFKZJ" class="smallInput" type="text" value="0"   id="zyfk" />元&nbsp;&nbsp;
  		<font color="red">总余款：</font><input name="TA_DJ_GROUP/YKZJ" class="smallInput" type="text" value="0"   id="zyk" />元&nbsp;&nbsp;
	  	<font color="red">总成人人数：</font><input name="TA_DJ_GROUP/CRRS" class="smallInput" type="text" value="0"  readonly="readonly"  id="zcrrs" />人&nbsp;&nbsp;
	  	<font color="red">总儿童人数：</font><input name="TA_DJ_GROUP/ETRS" class="smallInput" type="text" value="0"  readonly="readonly"  id="zetrs"/>人&nbsp;&nbsp;
	  	
	  &nbsp;&nbsp;</td>
  </tr>
</table>
</div>



<div class="add-table" > 
	<table class="datas">
	<tr id="first-tr">
		<td colspan="8">&nbsp;&nbsp;团队信息</td>
	</tr>
	 <tr >
	  	<td align="right" colspan="1">线路名称：</td>
	  	<td colspan="5">
	  	<input type="text" id="xlmc" name="TA_DJ_GROUP/XLMC" value="<%=rd.getString("TA_DJ_LINEMNGs","XLMC",0) %>"  maxlength="75" style="width: 85%"/>
	  	<input type="hidden" name="TA_DJ_GROUP/XLID" value="<%=rd.getString("TA_DJ_LINEMNGs","XLID",0) %>"/>
		  	<span>*</span>&nbsp;
	  	</td>

	  	<td align="right">
			人头费：
		</td>
	 	<td>
			<input type="text" name="TA_DJ_GROUP/RTF"  class="smallInput"/>
		</td>

	  </tr>
	<tr>
		<td align="right">团&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
		<td>
		<input type="text" name="TA_DJ_GROUP/ID" value="<%=gIDStr %>" maxlength="14" class="smallerInput1"/>
		</td>
		<td align="right">
		游客类型：
		</td>
		<td>    	
        	<%
        		String yklx = rd.getStringByDI("TA_DJ_LINEMNGs","YKLX",0);
        	%>
        	<input type="radio" name="TA_DJ_GROUP/YKLX" value="1" <%if("1".equals(yklx)){ %>checked="checked"<%} %>/>团队
        	<input type="radio" name="TA_DJ_GROUP/YKLX" value="2" <%if("2".equals(yklx)){ %>checked="checked"<%} %>/>散客
        </td>
		<td align="right">
			线路区域：
		</td>
		<td>
			<select name="TA_DJ_GROUP/XLQY">
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
		<td align="right">
		天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数：
		</td>
		<td>
	 	<input type="text" name="TA_DJ_GROUP/TS" value="<%=rd.getStringByDI("TA_DJ_LINEMNGs","TS",0) %>" id="DAY_COUNTS" onkeydown="checkNum()" size="2" maxlength="2"/>&nbsp;天&nbsp;
	 	<input type="text" name="TA_DJ_GROUP/NIGHT" value="<%=rd.getStringByDI("TA_DJ_LINEMNGs","NIGHT",0) %>"   onkeydown="checkNum()"   size="2" maxlength="2"/>&nbsp;晚&nbsp;
  	    </td>
	</tr>
	 
	  <tr>    	
	    <td align="right">
	    加点购物：
	    </td>
		<td>
		<input type="checkbox" name="TA_DJ_GROUP/JD" value="1"/>加点
		<input type="checkbox" name="TA_DJ_GROUP/GW" value="1"/>购物
		</td>
		<td align="right">
	 进店个数：
	 </td>
		<td>
		<input type="text" name="TA_DJ_GROUP/GWDS" value="" id="GWD_COUNTS" onkeydown="checkNum()" onchange="sumRs()"   maxlength="2"/>/个
        </td>
        <td align="right">是否自备车：
        </td>
		<td>
		  <input type="radio" name="TA_DJ_GROUP/SFZBC" value="1"/>是
	      <input type="radio" name="TA_DJ_GROUP/SFZBC" value="2" checked="checked"/>否
	      </td>
		<td align="right">
	     用车类型：
	     </td>
		<td>
		<select name="TA_DJ_GROUP/VEHTYPE"  >
		<%
		for(int i=0;i<rd.getTableRowsCount("CLLX");i++) {
		    	String dmz = rd.getStringByDI("CLLX","dmz",i);
		%>
		<option value="<%=dmz %>"><%=rd.getStringByDI("CLLX","dmsm1",i) %></option>
		<%
		}%>
		</select>
		
		</td>
		 
	  </tr>

	  <tr>
	  <td align="right">来程交通：
	  </td>
		<td>
	  <select name="TA_DJ_GROUP/B_TRAFFIC"  >
	  		<%
	  		for(int i=0;i<rd.getTableRowsCount("JTGJ");i++) {
   		    	String dmz = rd.getStringByDI("JTGJ","dmz",i);
	  		%>
	  		<option value="<%=dmz %>"><%=rd.getStringByDI("JTGJ","dmsm1",i) %></option>
	  		<%
	  		}%>
	  	  </select>
	  	  </td>
		<td align="right">
		接团时间：
		</td>
		<td>
	  	  <input type="text" name="TA_DJ_GROUP/BEGIN_DATE" value="" onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});"  id="bDate"/>
	  	  </td>
	  	<td align="right">
	  	回程交通：
	  	</td>
		<td>
	  	<select name="TA_DJ_GROUP/E_TRAFFIC" >
	  		<%
	  		for(int i=0;i<rd.getTableRowsCount("JTGJ");i++) {
   		    	String dmz = rd.getStringByDI("JTGJ","dmz",i);
	  		%>
	  		<option value="<%=dmz %>"><%=rd.getStringByDI("JTGJ","dmsm1",i) %></option>
	  		<%
	  		}
	  		%>
	  	  </select>
	  	  </td>
		<td align="right">
		送团时间：
		</td>
		<td>
	  	  <input type="text" name="TA_DJ_GROUP/END_DATE" value="" onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});"  id="eDate"/></td>
   		
	  </tr>	 
	  
	  <tr>
		<td align="right">
		来程备注：
		</td>
		<td colspan="3"><input type="text" name="TA_DJ_GROUP/B_REMARK" style="width: 95%"/></td>
		<td align="right">
		回程备注：
		</td>
	  	<td colspan="3"><input type="text" name="TA_DJ_GROUP/E_REMARK" style="width: 95%"/></td>
	  </tr>
	  
	  <tr id="qp">	
	  	<td align="right">全陪姓名：
	  	</td>
		<td colspan="3">
	  	<input type="text" name="TA_DJ_GROUP/QPXM" maxlength="10"  style="width: 95%"></td>
		<td align="right">全陪电话：
		</td>
		<td colspan="3">
		<input type="text" name="TA_DJ_GROUP/QPDH" id="checkMobile" onkeydown="checkNum()" maxlength="11"  style="width: 95%"/></td>
	  </tr>
	</table>
</div>



<div style="text-align: right">
<a href="###" onclick="add_XCMX();"><img alt="添加" src="<%=request.getContextPath()%>/images/add.gif"> 添加</a>&nbsp;&nbsp;
<a href="###" onclick="del_XCMX();"><img alt="删除" src="<%=request.getContextPath()%>/images/del.gif"> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
<div class="add-table">
	<table class="datas" id="store1">
		<tr id="first-tr">
			<td align="center" width="15%">标准</td>
			<td align="center" width="70%">行程</td>
			
		</tr>
		<%
				List<GroupLineDetail> list = (List) rd.get("lineDetail");
				for (int i = 0; i < list.size(); i++) {
					GroupLineDetail gd = list.get(i);
					String RQ = gd.getRq();//日期
					String XCMX = gd.getXcmx();//线路明细
					String BREAKFAST = gd.getBreakfast();//早
					String CMEAL = gd.getCmeal();//中
					String SUPPER = gd.getSupper();//晚
					String ZSBZ = gd.getZsbz();//住宿标准
					String CB = gd.getCb();//餐标
					String ZS = gd.getZs();//住宿
		%>
		<tr class="XCMXType">
			<td>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="6">D<%=RQ%></font>
			<input type="hidden" name="TA_DJ_LINEDETAI4G/RQ[<%=i%>]" value="<%=RQ%>" />
			<br><br>
			&nbsp;&nbsp;&nbsp;&nbsp;
			含餐：<input type="checkbox" name="TA_DJ_LINEDETAI4G/BREAKFAST[<%=i%>]" value="Y"
				<%if ("Y".equals(BREAKFAST)) {%> checked="checked" <%}%> />
			早 <input type="checkbox" name="TA_DJ_LINEDETAI4G/CMEAL[<%=i%>]" value="Y"
				<%if ("Y".equals(CMEAL)) {%> checked="checked" <%}%> />
			中 <input type="checkbox" name="TA_DJ_LINEDETAI4G/SUPPER[<%=i%>]" value="Y"
				<%if ("Y".equals(SUPPER)) {%> checked="checked" <%}%> />
			晚<br><br>
				&nbsp;&nbsp;&nbsp;&nbsp;餐标：<input type="text" name="TA_DJ_LINEDETAI4G/CB[<%=i%>]" value="<%=CB %>" class="smallerInput1" /> <br><br>
				&nbsp;&nbsp;&nbsp;&nbsp;住宿：<input type="text" name="TA_DJ_LINEDETAI4G/ZS[<%=i%>]" value="<%=ZS %>" class="smallerInput1" /> <br><br>
				&nbsp;&nbsp;&nbsp;&nbsp;房标：<select name="TA_DJ_LINEDETAI4G/ZSBZ[<%=i%>]" style="width: 100px;">
					<%
						for (int j = 0; j < rd.getTableRowsCount("JDXJ"); j++) {
							String dmz = rd.getString("JDXJ", "dmz", j);
							String dmsm = rd.getString("JDXJ", "dmsm1", j);
					%>
					<option value="<%=dmz%>" <%if (ZSBZ.equals(dmz)) {%> selected="selected" <%}%>><%=dmsm%></option>
					<%
						}
					%>
				</select>
			</td>
			<td>
				<textarea id="XCMX<%=i%>" name="TA_DJ_LINEDETAI4G/XCMX[<%=i%>]" rows="0" cols="0">
		   	 			<%
							Object obj = rd.get("TA_DJ_LINEDETAILs", "XCMX", i);
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
		<%} %>
</table>

<br>
<div>
	<table class="datas">
		<tr id="first-tr">
			<td>&nbsp;&nbsp;服务标准</td>
		</tr>
		<tr>
			<td>
				<textarea name="TA_DJ_GROUP/FWBZ" rows="3"><%=rd.getString("TA_DJ_LINEMNGs","FWBZ",0) %></textarea>
			</td>
		</tr>
		
	</table>
</div>
<div>
	<table class="datas">
		<tr id="first-tr">
			<td>&nbsp;&nbsp;特别提醒</td>
		</tr>
		<tr>
			<td>
				<textarea name="TA_DJ_GROUP/TBTX" rows="3"><%=rd.getString("TA_DJ_LINEMNGs","TBTX",0) %></textarea>
			</td>
		</tr>
		
	</table>
</div>
<div>
	<table class="datas">
		<tr id="first-tr">
			<td>&nbsp;&nbsp;备注</td>
		</tr>
		<tr>
			<td>
				<textarea name="TA_DJ_GROUP/COMMENTS" rows="3"><%=rd.getString("TA_DJ_LINEMNGs","BZ",0) %></textarea>
			</td>
		</tr>
		
	</table>
</div>

<div>
	<table class="datas">
		<tr>
			<td align="center">
				<br> 
				<input type="hidden" name="TA_DJ_GROUP/STATE" value="1"/>
					<input type="button" value="提    交" onclick="submitAddGroup();"/>&nbsp;&nbsp;
					<input type="button" value="返    回" onclick="window.history.go(-1)"/>
				<br> 
				
				<input type="hidden" name="TA_DJ_GROUP/STATE" value="1"/>
				<input type="hidden" id="itemRow" class=""  value=""/>
		
			</td>
		</tr>
		
	</table>
</div>
</div>
</div>	
</form>
</body>
</html>