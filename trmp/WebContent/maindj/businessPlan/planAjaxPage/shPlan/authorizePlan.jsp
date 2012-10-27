<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Blob"%>
<%@page import="java.io.InputStreamReader"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Calendar calendar = Calendar.getInstance();
	java.util.Date date=null;
	date = (java.util.Date)rd.get("TA_DJ_GROUPs", "BEGIN_DATE", 0);
	calendar.setTime(date);
	String hdate = sdf.format(calendar.getTime());
	String groupId = rd.getStringByDI("TA_DJ_GROUPs", "id", 0);
	int days = Integer.parseInt(("".equals(rd.getStringByDI("TA_DJ_GROUPs", "ts", 0))) ? "0" : rd.getStringByDI("TA_DJ_GROUPs", "ts", 0));
	String roleID = sd.getString("USERROLEST");
	boolean isTrue = false;
	if (!"".equals(roleID)) {
		roleID = roleID.substring(1, roleID.length() - 1);
		String[] roleIDs = roleID.split(",");
		for (int i = 0; i < roleIDs.length; i++) {
			if ("business".equals(roleIDs[i].trim())) {
				isTrue = true;
				break;
			}
		}
	}
	
	// 车型数目
	int cxRow = rd.getTableRowsCount("CLLX");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
<title>业务计调</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css" type="text/css">
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/menuList.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/menuList.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryui/jqueryui.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
<!-- dwr begin -->
<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/engine.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/util.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/interface/PYAjaxService.js" ></script>
<!-- dwr end -->
<style type="text/css">
* { margin:0; padding:0; word-wrap:break-word; word-break:break-all; }
.ui-button .ui-button-text { display: block; line-height: 0.6; }
td { vertical-align : middle; }
#tbNone {
  display:none;
}
</style>

<script type="text/javascript">

/**
*	checkBox的全选和全中选
*/
$(document).ready(function() {	
	$('[type=checkbox]').click(function (){
		var checkClass = $(this).attr("class");
		if(checkClass){
			if($("."+checkClass).is(":checked")){
				$("#"+checkClass).attr("checked","checked");
			}else{
				$("#"+checkClass).removeAttr("checked");
			}
		}
		var checkID = $(this).attr("id");
		if(checkID){
			if($("#"+checkID).is(":checked")){
				$("."+checkID).each(function (){
					$(this).attr("checked","checked");
				});
			}else{
				$("."+checkID).each(function (){
					$(this).removeAttr("checked");
				});
			}
		}
	});

	//初始化button submit
	$("input:submit,input:button").button();
	$(".addBtn").button({
		disabled : false,
		icons : {
			primary : "ui-icon-circle-plus"
		},
		text : true
	});
	$(".delBtn").button({
		disabled : false,
		icons : {
			primary : "ui-icon-circle-minus"
		},
		text : true
	});
	$(".saveBtn").button({
		disabled : false,
		icons : {
			primary : "ui-icon-circle-arrow-s"
		},
		text : true
	});
	$(".allSaveBtn").button({
		disabled : false,
		icons : {
			primary : "ui-icon-circle-check"
		},
		text : true
	});
	var md =$("#mdIndex").val();
	if(md){
		var mdName =$("#mdName").val();
		var mdUrl =$("#mdUrl").val();
		var obj=$.ajax({
			url:mdUrl,
			async:true,
			dataType:"HTML",
			cache:false,
			success:function()
			{
			  	$("#"+mdName).append(obj.responseText);
			}
		}); 
		$(".ckeditorli > a:eq("+md+")").next("ul").slideToggle();
		var mdTemp = parseInt(md)+1;
		$(".xcmx" + mdTemp + "").val(2);
	}
});


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
				if('plus' == itemName)
					PYAjaxService.plusExecute(filter.charAt(0), fillTable);//向后台取数据
				else if('guide' == itemName){
					PYAjaxService.guideExecute(filter.charAt(0), fillTable);//向后台取数据
				}else if('hotel' == itemName){
					PYAjaxService.hotelExecute(filter.charAt(0), fillTable);//向后台取数据
				}else if ("car" == itemName){
					PYAjaxService.carFuzzySearch(filter.charAt(0), fillTable); //向后台取数据
				}else if('scenery' == itemName){
					PYAjaxService.sceneryExecute(filter.charAt(0), fillTable);//向后台取数据
				}else if('dinner' == itemName){
					PYAjaxService.dinnerExecute(filter.charAt(0), fillTable);//向后台取数据
				}else if('travel' == itemName){
					PYAjaxService.travelExecute(filter.charAt(0), fillTable);//向后台取数据
				}else if('ticket' == itemName){
					PYAjaxService.ticketExecute(filter.charAt(0), fillTable);//向后台取数据
				}else if('shop' == itemName){
					PYAjaxService.shopExecute(filter.charAt(0), fillTable);//向后台取数据
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
	    function __dropheight(l){var h;if(l>10 || l<1){h = 10 * 15;}else{h = l * 15; h += 2;}if('hotel'!=itemName){return h+15;}else{return (h+15)*2;}}   //计算下拉option显示高度
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
				if(itemName=='plus'){
					options = ["plus" ,"plusLinkman","plusLinkmanTel","plusId","plusSf","plusCs"];//要更input域的className对应
					for(var i = 0;i < data.length; i++){//for循环填充option   
						div_html += "<div style='text-align:left;padding-left:5px;padding-top:2px;cursor:pointer;'>";
				    	div_html += "<label class='option"+itemName+"'>"+ data[i].sName.replace(filter,"<font color='red'>"+filter+"</font>") +"</label>";
				    	div_html += "<label class='option"+options[1]+"' style='display:none'>"+ data[i].linkman+"</label>";
				    	div_html += "<label class='option"+options[2]+"' style='display:none'>"+ data[i].linkmanTel+"</label>";
				    	div_html += "<label class='option"+options[3]+"' style='display:none'>"+ data[i].sID+"</label>";
				    	div_html += "<label class='option"+options[4]+"' style='display:none'>"+ data[i].province+"</label>";
				    	div_html += "<label class='option"+options[5]+"' style='display:none'>"+ data[i].city+"</label>";
				    	div_html += "</div>";
					}
				}else if(itemName == 'car'){
					options = ["car","cdid","SJXM","carLinkmanTel",];

					for(var i = 0;i < data.length; i++)
					{
						div_html += "<div style='text-align:left;padding-left:5px;padding-top:2px;cursor:pointer;'>";
				    	div_html += "<label class='option"+itemName+"'>"+ data[i].sName.replace(filter,"<font color='red'>"+filter+"</font>") +"</label>";
				    	div_html += "<label class='option"+options[1]+"' style='display:none'>"+ data[i].sID+"</label>";
				    	div_html += "</div>";
					}
				}else if(itemName == 'guide'){
					options = ["guide", "guideID", "guideTel", "guideCode"];
					for(var i = 0;i < data.length; i++){//for循环填充option   
						div_html += "<div style='text-align:left;padding-left:5px;padding-top:2px;cursor:pointer;'>";
				    	div_html += "<label class='option"+itemName+"'>"+ data[i].sName.replace(filter,"<font color='red'>"+filter+"</font>") +"</label>";
				    	div_html += "<label class='option"+options[1]+"' style='display:none'>"+ data[i].sID+"</label>";
				    	div_html += "<label class='option"+options[2]+"' style='display:none'>"+ data[i].linkman+"</label>";
				    	div_html += "<label class='option"+options[3]+"' style='display:none'>"+ data[i].guideCode+"</label>";
				    	div_html += "</div>";
					}
				}else if(itemName == 'hotel'){
					options = ["hotel","hotelLxr","hotelLxrDh", "hotelId", "hotelSf", "hotelCs","hotelXj"];
					for(var i = 0;i < data.length; i++){//for循环填充option   
						div_html += "<div style='text-align:left;padding-left:5px;padding-top:2px;cursor:pointer;'>";
				    	div_html += "<label class='option"+itemName+"'>"+ data[i].sName.replace(filter,"<font color='red'>"+filter+"</font>") +"</label>";
				    	div_html += "<label class='option"+options[1]+"' style='display:none'>"+ data[i].linkman+"</label>";
				    	div_html += "<label class='option"+options[2]+"' style='display:none'>"+ data[i].linkmanTel+"</label>";
				    	div_html += "<label class='option"+options[3]+"' style='display:none'>"+ data[i].sID+"</label>";
				    	div_html += "<label class='option"+options[4]+"' style='display:none'>"+ data[i].province+"</label>";
				    	div_html += "<label class='option"+options[5]+"' style='display:none'>"+ data[i].city+"</label>";
				    	div_html += "<label class='option"+options[6]+"' style='display:none'>"+ data[i].area+"</label>";
				    	div_html += "<br><label style='display:block'><font color='green'>"+ data[i].areaName+" &nbsp;&nbsp;"+ data[i].csName+"</font></label>";
				    	div_html += "</div>";
					}
				}else if(itemName == 'scenery'){
					options = ["scenery", "sceneryID", "provinceID", "cityID", "lxr", "lxfs"];
					for(var i = 0;i < data.length; i++){//for循环填充option   
						div_html += "<div style='text-align:left;padding-left:5px;cursor:pointer;'>";
				    	div_html += "<label class='option"+itemName+"'>"+ data[i].sName.replace(filter,"<font color='red'>"+filter+"</font>") +"</label>";
				    	div_html += "<label class='option"+options[1]+"' style='display:none'>"+ data[i].sID+"</label>";
				    	div_html += "<label class='option"+options[2]+"' style='display:none'>"+ data[i].province+"</label>";
				    	div_html += "<label class='option"+options[3]+"' style='display:none'>"+ data[i].city+"</label>";
				    	div_html += "<label class='option"+options[4]+"' style='display:none'>"+ data[i].linkman+"</label>";
				    	div_html += "<label class='option"+options[5]+"' style='display:none'>"+ data[i].linkmanTel+"</label>";
				    	div_html += "<br><label style='display:block'><font color='green'>"+ data[i].sfName+" &nbsp;&nbsp;"+ data[i].csName+"</font></label>";
				    	div_html += "</div>";
					}
				}else if(itemName == 'dinner'){
					options = ["dinner","dinnerLxr","dinnerLxrDh", "dinnerId", "dinnerSf", "dinnerCs"];
					for(var i = 0;i < data.length; i++){//for循环填充option   
						div_html += "<div style='text-align:left;padding-left:5px;padding-top:2px;cursor:pointer;'>";
				    	div_html += "<label class='option"+itemName+"'>"+ data[i].sName.replace(filter,"<font color='red'>"+filter+"</font>") +"</label>";
				    	div_html += "<label class='option"+options[1]+"' style='display:none'>"+ data[i].linkman+"</label>";
				    	div_html += "<label class='option"+options[2]+"' style='display:none'>"+ data[i].linkmanTel+"</label>";
				    	div_html += "<label class='option"+options[3]+"' style='display:none'>"+ data[i].sID+"</label>";
				    	div_html += "<label class='option"+options[4]+"' style='display:none'>"+ data[i].province+"</label>";
				    	div_html += "<label class='option"+options[5]+"' style='display:none'>"+ data[i].city+"</label>";
				    	div_html += "</div>";
					}
				}else if(itemName == 'travel'){
					options = ["travel","travelLxr","travelLxfs", "travelID", "travelSF", "travelCS"];
					for(var i = 0;i < data.length; i++){//for循环填充option   
						div_html += "<div style='text-align:left;padding-left:5px;padding-top:2px;cursor:pointer;'>";
				    	div_html += "<label class='option"+itemName+"'>"+ data[i].sName.replace(filter,"<font color='red'>"+filter+"</font>") +"</label>";
				    	div_html += "<label class='option"+options[1]+"' style='display:none'>"+ data[i].linkman+"</label>";
				    	div_html += "<label class='option"+options[2]+"' style='display:none'>"+ data[i].linkmanTel+"</label>";
				    	div_html += "<label class='option"+options[3]+"' style='display:none'>"+ data[i].sID+"</label>";
				    	div_html += "<label class='option"+options[4]+"' style='display:none'>"+ data[i].province+"</label>";
				    	div_html += "<label class='option"+options[5]+"' style='display:none'>"+ data[i].city+"</label>";
				    	div_html += "<br><label style='display:block'><font color='green'>"+ data[i].sfName+" &nbsp;&nbsp;"+ data[i].csName+"</font></label>";
				    	div_html += "</div>";
					}
				}else if(itemName == 'ticket'){
					options = ["ticket","ticketLxr","ticketLxDh", "ticketId", "ticketSf", "ticketCs"];
					for(var i = 0;i < data.length; i++){//for循环填充option   
						div_html += "<div style='text-align:left;padding-left:5px;padding-top:2px;cursor:pointer;'>";
				    	div_html += "<label class='option"+itemName+"'>"+ data[i].sName.replace(filter,"<font color='red'>"+filter+"</font>") +"</label>";
				    	div_html += "<label class='option"+options[1]+"' style='display:none'>"+ data[i].linkman+"</label>";
				    	div_html += "<label class='option"+options[2]+"' style='display:none'>"+ data[i].linkmanTel+"</label>";
				    	div_html += "<label class='option"+options[3]+"' style='display:none'>"+ data[i].sID+"</label>";
				    	div_html += "<label class='option"+options[4]+"' style='display:none'>"+ data[i].province+"</label>";
				    	div_html += "<label class='option"+options[5]+"' style='display:none'>"+ data[i].city+"</label>";
				    	div_html += "</div>";
					}
				}else if(itemName == 'shop'){
					options = ["shop","shopLxr","shopLxDh", "shopId", "shopSf", "shopCs"];
					for(var i = 0;i < data.length; i++){//for循环填充option   
						div_html += "<div style='text-align:left;padding-left:5px;padding-top:2px;cursor:pointer;'>";
				    	div_html += "<label class='option"+itemName+"'>"+ data[i].sName.replace(filter,"<font color='red'>"+filter+"</font>") +"</label>";
				    	div_html += "<label class='option"+options[1]+"' style='display:none'>"+ data[i].linkman+"</label>";
				    	div_html += "<label class='option"+options[2]+"' style='display:none'>"+ data[i].linkmanTel+"</label>";
				    	div_html += "<label class='option"+options[3]+"' style='display:none'>"+ data[i].sID+"</label>";
				    	div_html += "<label class='option"+options[4]+"' style='display:none'>"+ data[i].province+"</label>";
				    	div_html += "<label class='option"+options[5]+"' style='display:none'>"+ data[i].city+"</label>";
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
	
	
	
	/**
	*	加点模块动态添加行 optiong(模块名称****Md 即table的id(mdName),  模糊查询输入框class Name(csName))
	*/
		function addTrRow(mdName,csName){
			var Random = Math.floor(Math.random() * 123450);
			if('plusMd' == mdName){
				var trRows = $(".plusRow").size();
				$("#plusMd").append('<tr class="plusRow" style="display: block;">' +
				  '<td><input type="checkbox" class="plusCheckbox" /></td>'+
				  '<td><input type="text" name="TA_DJ_JHJIAD/JDMC['+trRows+']" class="plus" id="'+Random+'"/></td>'+
				  '<td><input type="text" name="TA_DJ_JHJIAD/LXR['+trRows+']" class="plusLinkman"/></td>'+
				  '<td>'+
			  	  '<input type="text" name="TA_DJ_JHJIAD/LXFS['+trRows+']" class="plusLinkmanTel"/>'+
			  	  '<input type="hidden" name="TA_DJ_JHJIAD/JDID['+trRows+']" class="plusId"/>'+
			  	  '<input type="hidden" name="TA_DJ_JHJIAD/SF['+trRows+']" class="plusSf"/>'+
			  	  '<input type="hidden" name="TA_DJ_JHJIAD/cs['+trRows+']" class="plusCs"/>'+
				  '</td>'+
				  '<td><textarea rows="1" name="TA_DJ_JHJIAD/BZ['+trRows+']"></textarea></td>'+
				'</tr>');
			}else if('otherMd' == mdName){
				var trRows = $(".otherRow").size();
				$("#otherMd").append('<tr class="otherRow">'+
						  '<td><input type="checkbox" class="otherCheckbox"></td>'+
						  '<td><input type="text" name="TA_DJ_JHQT/APMC['+trRows+']" /></td>'+
						  '<td><input type="text" name="TA_DJ_JHQT/DJ['+trRows+']" /></td>'+
						  '<td><input type="text" name="TA_DJ_JHQT/YGCB['+trRows+']" onkeydown="checkNumX()" class="otherYgcb"/></td>'+
						  '<td><input type="text" name="TA_DJ_JHQT/QDJE['+trRows+']" onkeydown="checkNumX()" class="otherCbqd" /></td>'+
						  '<td><input type="text" name="TA_DJ_JHQT/XFJE['+trRows+']" onkeydown="checkNumX()" class="otherCbxf" /></td>'+
						  '<td><textarea rows="1" name="TA_DJ_JHQT/BZ['+trRows+']"></textarea></td>'+
						  '</tr>');
			}else if('hotelMd' == mdName){
				var trRows = $(".hotelRow").size();
				$("#hotelMd").append('<tr class="hotelRow">'+
						'<td><input type="checkbox" class="hotelCheckbox"></td>'+
						'<td>'+
						'<select name="TA_DJ_JHHOTEL/RC['+trRows+']">'+
						<%calendar.setTime(date);
						for (int i = 0; i < days; i++) {
							String dateStr = sdf.format(calendar.getTime());
							int dt =1+i;
							%>
							  '<option value="<%=dateStr%>" ><%= "【D"+dt+"】"+ dateStr%></option>'+
							<%
									calendar.add(Calendar.DAY_OF_MONTH, 1);
							}
						%>
						'</select><input type="hidden" name="TA_DJ_JHHOTEL/RZRQ['+trRows+']" value="<%=hdate%>" class="rzrq"/>'+
						'<input type="hidden" name="TA_DJ_JHHOTEL/SF['+trRows+']" class="hotelSf"/>'+
						'<input type="hidden" name="TA_DJ_JHHOTEL/CITY['+trRows+']" class="hotelCs"/>'+
						'<input type="hidden" name="TA_DJ_JHHOTEL/XJ['+trRows+']" class="hotelXj"/>'+
						'<input type="hidden" name="TA_DJ_JHHOTEL/JDID['+trRows+']"  class="hotelId"/>'+
						'</td>'+
						'<td><input type="text" name="TA_DJ_JHHOTEL/JDMC['+trRows+']" class="hotel"  id="'+Random+'"/></td>'+
						'<td><input type="text" name="TA_DJ_JHHOTEL/LXR['+trRows+']"  class="smallInput hotelLxr"/></td>'+
						'<td><input type="text" name="TA_DJ_JHHOTEL/LXRDH['+trRows+']"  class="smallInput hotelLxrDh"/></td>'+
						'<td><input type="text" name="TA_DJ_JHHOTEL/RS['+trRows+']" class="input40"/></td>'+
						'<td><input type="text" name="TA_DJ_JHHOTEL/TS['+trRows+']" class="input40"/></td>'+
						'<td><a>编辑</a></td>'+
						'<td><input type="text" name="TA_DJ_JHHOTEL/DJ['+trRows+']" class="input40"/></td>'+
						'<td><input type="text" name="TA_DJ_JHHOTEL/YGCB['+trRows+']" onkeydown="checkNumX()" class="smallInput hotelYgcb"/></td>'+
						'<td><input type="text" name="TA_DJ_JHHOTEL/QDJE['+trRows+']" onkeydown="checkNumX()" class="smallInput hotelCbqd"/></td>'+
						'<td><input type="text" name="TA_DJ_JHHOTEL/XFJE['+trRows+']" onkeydown="checkNumX()" class="smallInput hotelCbxf"/></td>'+
						'<td>'+
						'<select name="TA_DJ_JHHOTEL/SFHZ['+trRows+']">'+
						'<option value="-1">不含早</option>'+
						'<option value="1">卓早</option>'+
						'<option value="2">自助早</option>'+
						'<option value="3">打包早</option>'+
						'</select>'+
						'</td>'+
						'<td><textarea rows="1" style="width:80%" name="TA_DJ_JHHOTEL/BZ['+trRows+']"></textarea></td>'+
					'</tr>');
				$("select").bind('change',function(){//模糊查询名称绑定keyup事件
					$(this).next("input").val($(this).val());
				});
			}else if('dinnerMd' == mdName){
				var trRows = $(".dinnerRow").size();
				$("#dinnerMd").append('<tr class="dinnerRow">'+
						'<td><input type="checkbox" class="dinnerCheckbox"></td>'+
						'<td>'+
						'<select name="TA_DJ_JHCT/RC['+trRows+']" class="rc">'+
						<%
						calendar.setTime(date);
						for (int i = 0; i < days; i++) {
							int dt =1+i;
							String dateStr = sdf.format(calendar.getTime());
							%>
							  '<option value="<%=dateStr%>:1" ><%= "【D"+dt+"】"+ dateStr+" 早"%></option>'+
							  '<option value="<%=dateStr%>:2" ><%= "【D"+dt+"】"+ dateStr+" 中"%></option>'+
							  '<option value="<%=dateStr%>:3" ><%= "【D"+dt+"】"+ dateStr+" 晚"%></option>'+
							<%
									calendar.add(Calendar.DAY_OF_MONTH, 1);
							}
						%>
						'</select><input type="hidden" name="TA_DJ_JHCT/YCRQ['+trRows+']" value="<%=hdate%>" class="ycrq"/><input type="hidden" name="TA_DJ_JHCT/CC['+trRows+']" value="1" class="cc"/>'+
						'<input type="hidden" name="TA_DJ_JHCT/SF['+trRows+']" class="dinnerSf"/>'+
						'<input type="hidden" name="TA_DJ_JHCT/CITYID['+trRows+']" class="dinnerCs"/>'+
						'<input type="hidden" name="TA_DJ_JHCT/CT['+trRows+']"  class="dinnerId"/>'+
						'</td>'+
						'<td><input type="text" name="TA_DJ_JHCT/CTMC['+trRows+']" class="dinner"  id="'+Random+'"/></td>'+
						'<td><input type="text" name="TA_DJ_JHCT/LXR['+trRows+']"  class="smallInput dinnerLxr"/></td>'+
						'<td><input type="text" name="TA_DJ_JHCT/LXFS['+trRows+']"  class="smallInput dinnerLxrDh"/></td>'+
						'<td><input type="text" name="TA_DJ_JHCT/CS['+trRows+']" class="input40"/></td>'+
						'<td><input type="text" name="TA_DJ_JHCT/RS['+trRows+']" class="input40"/></td>'+
						'<td><input type="text" name="TA_DJ_JHCT/JG['+trRows+']" class="input40"/></td>'+
						'<td><input type="text" name="TA_DJ_JHCT/YGCB['+trRows+']" onkeydown="checkNumX()" class="smallInput dinnerYgcb"/></td>'+
						'<td><input type="text" name="TA_DJ_JHCT/QDJE['+trRows+']" onkeydown="checkNumX()" class="smallInput dinnerCbqd"/></td>'+
						'<td><input type="text" name="TA_DJ_JHCT/XFJE['+trRows+']" onkeydown="checkNumX()" class="smallInput dinnerCbxf"/></td>'+
						'<td><textarea rows="1" style="width:80%" name="TA_DJ_JHCT/BZ['+trRows+']"></textarea></td>'+
					'</tr>');
				$("select").bind('change',function(){//模糊查询名称绑定keyup事件
					var temp = $(this).val().split(':',2);
					$(this).next().val(temp[0]);
					$(this).next().next().val(temp[1]);
				});
			}else if('guideMd' == mdName){
				var trRows = $(".guideRow").size();
				$("#guideMd").append('<tr class="guideRow">'+
						  '<td><input type="checkbox" class="guideCheckbox"></td>'+
						  '<td>'+
						  '<select name="TA_DJ_JHDY/CFRQ['+trRows+']">'+
						  <%calendar.setTime(date);
							for (int j = 0; j < days; j++) {
								int dt = 1+j;
								String dateStr = sdf.format(calendar.getTime());
						  %>
						  '<option value="<%=dateStr%>"><%= "【D"+dt+"】"+ dateStr%></option>'+
						  <%
								calendar.add(Calendar.DAY_OF_MONTH, 1);
							}
						  %>
						  '</select></td>'+
						  '<td>'+
						  '<select name="TA_DJ_JHDY/fhrq['+trRows+']">'+
						  <%
						  	calendar.setTime(date);
							for (int j = 0; j < days; j++) {
								int dt = 1+j;
								String dateStr = sdf.format(calendar.getTime());
						  %>
						  '<option value="<%=dateStr%>"><%= "【D"+dt+"】"+ dateStr%></option>'+
						  <%
								calendar.add(Calendar.DAY_OF_MONTH, 1);
							}
						  %>
						  '</select>'+
						  '</td>'+
						  '<td><input type="text" name="TA_DJ_JHDY/DYXM['+trRows+']" class="guide smallInput" id="'+Random+'"/><input type="hidden" name="TA_DJ_JHDY/DYID['+trRows+']" class="guideID"/></td>'+
						  '<td><input type="text" name="TA_DJ_JHDY/SJHM['+trRows+']" class="guideTel smallerInput"/></td>'+
						  '<td><input type="text" name="TA_DJ_JHDY/DYZH['+trRows+']" class="guideCode smallerInput"/></td>'+
						  '<td><input type="text" name="TA_DJ_JHDY/DYLK['+trRows+']" onkeydown="checkNumX()" class="smallInput"/></td>'+
						  '<td><input type="text" name="TA_DJ_JHDY/DFF['+trRows+']" onkeydown="checkNumX()" class="smallInput"/></td>'+
						  '<td><textarea rows="1" name="TA_DJ_JHDY/BZ['+trRows+']"></textarea></td>'+
						  '</tr>');
			}else if('sceneryMd' == mdName){
				var trRows = $(".sceneryRow").size();
				$("#sceneryMd").append('<tr class="sceneryRow">'+
						  '<td><input type="checkbox" class="sceneryCheckbox"></td>'+
						  '<td>'+
							  '<select name="TA_DJ_JHJD/days['+trRows+']">'+
							  <%
							  	calendar.setTime(date);
								for (int j = 0; j < days; j++) {
									int dt = 1+j;
									String dateStr = sdf.format(calendar.getTime());
							  %>
							  '<option value="<%= "【D"+dt+"】:"+ dateStr%>"><%= "【D"+dt+"】"+ dateStr%></option>'+
							  <%
									calendar.add(Calendar.DAY_OF_MONTH, 1);
								}
							  %>
							  '</select>'+
						  '</td>'+
						  '<td>'+
							  '<input type="hidden" name="TA_DJ_JHJD/SFID['+trRows+']" class="provinceID"/>'+
							  '<input type="hidden" name="TA_DJ_JHJD/CSID['+trRows+']" class="cityID"/>'+
							  '<input type="hidden" name="TA_DJ_JHJD/JDID['+trRows+']" class="sceneryID"/>'+
							  '<input type="text" name="TA_DJ_JHJD/JDMC['+trRows+']" class="scenery" id="'+Random+'"/>'+
						  '</td>'+
						  '<td><input type="text" name="TA_DJ_JHJD/lxr['+trRows+']" class="smallInput lxr"/></td>'+
						  '<td><input type="text" name="TA_DJ_JHJD/lxfs['+trRows+']" class="smallInput lxfs"/></td>'+
						  '<td><input type="text" name="TA_DJ_JHJD/rs['+trRows+']" class="smallInput"/></td>'+
						  '<td><input type="text" name="TA_DJ_JHJD/dj['+trRows+']" class="smallInput"/></td>'+
						  '<td><input type="text" name="TA_DJ_JHJD/TGCB['+trRows+']" onkeydown="checkNumX()" class="smallInput sceneryYgcb"/></td>'+
						  '<td><input type="text" name="TA_DJ_JHJD/QDJE['+trRows+']" onkeydown="checkNumX()" class="smallInput sceneryCbqd"/></td>'+
						  '<td><input type="text" name="TA_DJ_JHJD/xfje['+trRows+']" onkeydown="checkNumX()" class="smallInput sceneryCbxf"/></td>'+
						  '<td><textarea rows="1" name="TA_DJ_JHJD/BZ['+trRows+']"></textarea></td>'+
						  '</tr>');
			}else if('travelMd' == mdName){
				var trRows = $(".travelRow").size();
				$("#travelMd").append('<tr class="travelRow">'+
					    '<td><input type="checkbox" class="travelCheckbox"></td>'+
					    '<td>'+
					    	'<select name="TA_DJ_JHDJ/ksrq['+trRows+']">'+
							  <%
							  	calendar.setTime(date);
								for (int j = 0; j < days; j++) {
									int dt = 1+j;
									String dateStr = sdf.format(calendar.getTime());
							  %>
							  '<option value="<%=dateStr %>"><%= "【D"+dt+"】"+ dateStr%></option>'+
							  <%
									calendar.add(Calendar.DAY_OF_MONTH, 1);
								}
							  %>
							  '</select>'+
						'</td>'+
					    '<td>'+
					    	'<select name="TA_DJ_JHDJ/JSRQ['+trRows+']">'+
							  <%
							  	calendar.setTime(date);
								for (int j = 0; j < days; j++) {
									int dt = 1+j;
									String dateStr = sdf.format(calendar.getTime());
							  %>
							  '<option value="<%=dateStr %>"><%= "【D"+dt+"】"+ dateStr%></option>'+
							  <%
									calendar.add(Calendar.DAY_OF_MONTH, 1);
								}
							  %>
							  '</select>'+
					    '</td>'+
					    '<td>'+
					      '<input type="text" name="TA_DJ_JHDJ/DJSMC['+trRows+']" class="travel" id="'+Random+'"/>'+
					      '<input type="hidden" name="TA_DJ_JHDJ/DJSID['+trRows+']" class="travelID"/>'+
					      '<input type="hidden" name="TA_DJ_JHDJ/sfid['+trRows+']" class="travelSF"/>'+
					      '<input type="hidden" name="TA_DJ_JHDJ/csid['+trRows+']" class="travelCS"/>'+
					    '</td>'+
					    '<td><input type="text" name="TA_DJ_JHDJ/lxr['+trRows+']" class="travelLxr smallInput"/></td>'+
					    '<td><input type="text" name="TA_DJ_JHDJ/lxfs['+trRows+']" class="travelLxfs smallerInput"/></td>'+
					    '<td><input type="text" name="TA_DJ_JHDJ/crrs['+trRows+']" class="smallInput"/></td>'+
					    '<td><input type="text" name="TA_DJ_JHDJ/YFZK['+trRows+']" onkeydown="checkNumX()" class="smallInput travelYgcb"/></td>'+					    
					    '<td><input type="text" name="TA_DJ_JHDJ/QDJE['+trRows+']" onkeydown="checkNumX()" class="smallInput travelCbqd"/></td>'+
					    '<td><input type="text" name="TA_DJ_JHDJ/XFJE['+trRows+']" onkeydown="checkNumX()" class="smallInput travelCbxf"/></td>'+
					    '<td><textarea rows="1" name="TA_DJ_JHdj/BZ['+trRows+']"></textarea></td>'+
					  '</tr>');
			}
			else if ('carMd' == mdName)
			{
				var trRows = $(".carRow").size();
				$("#carMd").append(
						'<tr class="carRow">' +
							'<td>' +
		  						'<input type="checkbox" class="carCheckbox" />' +
		  					'</td>' +
		  					'<td>' +
		  					'<select name="TA_DJ_JHCL/KSRQ['+trRows+']" class="ksrq">'+
							<%calendar.setTime(date);
							for (int i = 0; i < days; i++) {
								String dateStr = sdf.format(calendar.getTime());
								int dt =1+i;
								%>
								  '<option value="<%=dateStr%>" ><%= "【D"+dt+"】"+ dateStr%></option>'+
								<%
										calendar.add(Calendar.DAY_OF_MONTH, 1);
								}
							%>
							'</select>'+
		  					'</td>' +
		 					'<td>' +
		 					'<select name="TA_DJ_JHCL/JSRQ['+trRows+']" class="jsrq">'+
								<%calendar.setTime(date);
								for (int i = 0; i < days; i++) {
									String dateStr = sdf.format(calendar.getTime());
									int dt =1+i;
									%>
									  '<option value="<%=dateStr%>" ><%= "【D"+dt+"】"+ dateStr%></option>'+
									<%
											calendar.add(Calendar.DAY_OF_MONTH, 1);
									}
								%>
							'</select>'+
		 					'</td>' +
		  					'<td>' +
		 	  					'<input type="hidden" name="TA_DJ_JHCL/CD['+trRows+']"  class="cdid" />' +
		 	  					'<input type="text" name="CDNAME['+trRows+']" class="car" id="'+Random+'"/>' +
							'</td>' +
							'<td>' +
							 	'<select name="TA_DJ_JHCL/CXid['+trRows+']">'+
		 								<%for (int k =0; k < cxRow; k++)
		 									{
		 										String cucx = rd.getStringByDI("CLLX","DMSM1",k);
		 										%>
		 										'<option value="<%=rd.getStringByDI("CLLX","DMZ",k)%>"><%=cucx %></option>'+
		 										<%
		 									}
		 								%>
		 							'</select>' +
							'</td>' +
							'<td>' +
								'<input type="text" name="TA_DJ_JHCL/SJXM['+trRows+']"  class="smallInput sjxm"/>' +
							'</td>' +
							'<td>' +
							 	'<input type="text" name="TA_DJ_JHCL/SJDH['+trRows+']"  class="smallerInput sjdh"/>' +
						 	'</td>' +
						 	'<td>' +
							 	'<input type="text" name="TA_DJ_JHCL/CP['+trRows+']"  class="smallInput cp"/>' +
						 	'</td>' +
						 	'<td>' +
							 	'<input type="text" name="TA_DJ_JHCL/JG['+trRows+']" onkeydown="checkNumX()" class="smallInput carYgcb"/>' +
						 	'</td>' +
						 	'<td>' +
							 	'<input type="text" name="TA_DJ_JHCL/QDJE['+trRows+']" onkeydown="checkNumX()" class="smallInput carCbqd"/>' +
						 	'</td>' +
						 	'<td>' +	
							 	'<input type="text" name="TA_DJ_JHCL/XFJE['+trRows+']" onkeydown="checkNumX()" class="smallInput carCbxf"/>' +
						 	'</td>' +
						  	'<td>' +
						  		'<textarea rows="1" style="width:80%" name="TA_DJ_JHCL/BZ['+trRows+']"></textarea>' +
						  	'</td>' +
						'</tr>');
			}else if('ticketMd' == mdName){
				var trRows = $(".ticketRow").size();
				$("#ticketMd").append('<tr class="ticketRow">'+
						'<td><input type="checkbox" class="ticketCheckbox"></td>'+
						'<td>'+
						'<select class="cfRq">'+
						<%calendar.setTime(date);
						for (int i = 0; i < days; i++) {
							String dateStr = sdf.format(calendar.getTime());
							int dt =1+i;
							%>
							  '<option value="<%=dateStr%>" ><%= "【D"+dt+"】"+ dateStr%></option>'+
							<%
									calendar.add(Calendar.DAY_OF_MONTH, 1);
							}
						%>
						'</select><input type="hidden"  name="TA_DJ_JHPW/CFRQ['+trRows+']" value="<%=hdate%>" />'+
						'<input type="hidden" name="TA_DJ_JHPW/SF['+trRows+']" class="ticketSf"/>'+
						'<input type="hidden" name="TA_DJ_JHPW/CITY['+trRows+']" class="ticketCs"/>'+
						'<input type="hidden" name="TA_DJ_JHPW/DGD['+trRows+']"  class="ticketId"/>'+
						'</td>'+
						'<td>'+
						'<input type="text"name="TA_DJ_JHPW/CFSJ['+trRows+']" class="smallerInput"/>' +
						'</td>'+
						'<td>'+
						'<select name="TA_DJ_JHPW/JTLX['+trRows+']">'+
						'<option value="1">火车</option>'+
						'<option value="2">飞机</option>'+
						'<option value="3">邮轮</option>'+
						'</select>'+
						'</td>'+
						'<td><input type="text" name="TA_DJ_JHPW/DGDMC['+trRows+']" class="smallerInput ticket"  id="'+Random+'"/></td>'+
						'<td><label>姓名：</label><input type="text" name="TA_DJ_JHPW/LXR['+trRows+']"  class="smallInput ticketLxr"/><p><label>电话：</label><input type="text" name="TA_DJ_JHPW/LXRDH['+trRows+']"  class="smallInput ticketLxrDh"/></td>'+
						'<td><input type="text" name="TA_DJ_JHPW/HBCC['+trRows+']" class="input40"/></td>'+
						'<td><label>出发：</label><input type="text" name="TA_DJ_JHPW/QSZ['+trRows+']" class="input40"/><p><label>返回：</label><input type="text" name="TA_DJ_JHPW/ZDZ['+trRows+']" class="input40"/></td>'+
						'<td><input type="text" name="TA_DJ_JHPW/ZS['+trRows+']" class="input40"/></td>'+
						'<td><input type="text" name="TA_DJ_JHPW/DJ['+trRows+']" class="input40"/></td>'+
						'<td><input type="text" name="TA_DJ_JHPW/SXF['+trRows+']" class="input40"/></td>'+
						'<td><input type="text" name="TA_DJ_JHPW/YGCB['+trRows+']" onkeydown="checkNumX()" class="input40 ticketYgcb"/></td>'+
						'<td><input type="text" name="TA_DJ_JHPW/QD['+trRows+']" onkeydown="checkNumX()" class="input40 ticketCbqd"/></td>'+
						'<td><input type="text" name="TA_DJ_JHPW/XF['+trRows+']" onkeydown="checkNumX()" class="input40 ticketCbxf"/></td>'+
						'<td><textarea rows="1" style="width:80%" name="TA_DJ_JHPW/BZ['+trRows+']"></textarea></td>'+
					'</tr>');
				$(".cfRq").bind('change',function(){//模糊查询名称绑定keyup事件
					$(this).next("input").val($(this).val());
				});
			}else if('shopMd' == mdName){
				var trRows = $(".shopRow").size();
				$("#shopMd").append('<tr class="shopRow">'+
						'<td><input type="checkbox" class="shopCheckbox"></td>'+
						'<td>'+
						'<select name="TA_DJ_JHGW/RC['+trRows+']">'+
						<%calendar.setTime(date);
						for (int i = 0; i < days; i++) {
							String dateStr = sdf.format(calendar.getTime());
							int dt =1+i;
							%>
							  '<option value="<%=dateStr%>" ><%= "【D"+dt+"】"+ dateStr%></option>'+
							<%
									calendar.add(Calendar.DAY_OF_MONTH, 1);
							}
						%>
						'</select><input type="hidden" name="TA_DJ_JHGW/RZRQ['+trRows+']" value="<%=hdate%>" class="rzrq"/>'+
						'<input type="hidden" name="TA_DJ_JHGW/SFID['+trRows+']" class="shopSf"/>'+
						'<input type="hidden" name="TA_DJ_JHGW/CSID['+trRows+']" class="shopCs"/>'+
						'<input type="hidden" name="TA_DJ_JHGW/GWDID['+trRows+']"  class="shopId"/>'+
						'</td>'+
						'<td><input type="text" name="TA_DJ_JHGW/GWDMC['+trRows+']" class="shop"  id="'+Random+'"/></td>'+
						'<td><input type="text" name="TA_DJ_JHGW/LXR['+trRows+']"  class="smallInput shopLxr"/></td>'+
						'<td><input type="text" name="TA_DJ_JHGW/LXFS['+trRows+']"  class="smallInput shopLxrDh"/></td>'+
						'<td><input type="text" name="TA_DJ_JHGW/FDBL['+trRows+']" class="input40"/></td>'+
						'<td><textarea rows="1" style="width:80%" name="TA_DJ_JHGW/BZ['+trRows+']"></textarea></td>'+
					'</tr>');
				$("select").bind('change',function(){//模糊查询名称绑定keyup事件
					$(this).next("input").val($(this).val());
				});
			}
			
			$('[type=checkbox]').bind('click', function() {//绑定单选按钮事件
				var checkClass = $(this).attr("class");
				var checkNum=0;
				$("."+checkClass).each(function(){
					if($(this).is(":checked")){
						
					}else{
						checkNum++;
					}
					if(checkNum == 0){
						$("#"+checkClass).attr("checked","checked");
					}else{
						$("#"+checkClass).removeAttr("checked");
					}
				});
			});
			
			$("."+csName).bind('keyup',function(){//模糊查询名称绑定keyup事件
				$('#itemRow').val($("."+csName).index(this));
				filterChanged(""+csName);
			});
	}
 	
	function frmSubmit(mdName,url,md){//form表单域ajax异步 提交数据入库  option (模块名:即table的id, url:请求的url)
	    var ygcb=0;
		var cbqd=0;
		var cbxf=0;
		var temp=0;
		$("." + md + "Ygcb").each(function(){
			if(this.value==null||this.value==''){temp = 0;}else{temp =  this.value;}
			ygcb+=parseFloat(temp);
		});
		$("." + md + "Cbqd").each(function(){
			if(this.value==null||this.value==''){temp = 0;}else{temp =  this.value;}
			cbqd+=parseFloat(temp);
		});
		$("." + md + "Cbxf").each(function(){
			if(this.value==null||this.value==''){temp = 0;}else{temp =  this.value;}
			cbxf+=parseFloat(temp);
		});
		if(ygcb==null||ygcb=='')ygcb=0;
		if(cbqd==null||cbqd=='')cbqd=0;
		if(cbxf==null||cbxf=='')cbxf=0;
		$("#" + md + "Row").text($("." + md + "Row").size());
		$("#" + md + "Ygcb").text(ygcb);
		$("#" + md + "Cbqd").text(cbqd);
		$("#" + md + "Cbxf").text(cbxf);
		
		var str = null;
		showValues();
		$.ajax({
			dateType:'text',
			url:url,
			data:str,
			cache:false,
			success:function(data){
				alert(data);
			},
			error:function(){
				alert("系统异常，请稍后再试！");
			}
		});
		function showValues() {//序列化form表单数据
			str = $("#"+mdName).serialize();
		}
	}
	/*根据 checkbox:checked 删除模块下的tr option(删除行tr的className,以及删除行的checkbox的className)*/
	function delTr(trCsName,checkCsName){
		$("."+checkCsName+":checked").each(function(){
				$("."+trCsName+":eq("+$("."+checkCsName).index(this)+")").remove();
		});
	}

	function xcmx1(nums,mdName,url) {//隐藏域值改变 option(第几个模块,模块名称,请求的url)
		if ($(".xcmx" + nums).val() == 1) 
		{
			$(".xcmx" + nums).val(2);
			var obj=$.ajax(
			{
				url:url,
				async:true,
				dataType:"HTML",
				cache:false,
				success:function()
				{
				  	$("#"+mdName).append(obj.responseText);
				}
			}); 
		}
		
	}
	function show(divID) {//团队基本信息模块的层隐藏改变 
		if ($("#" + divID).is(":hidden")) {
			$("#" + divID).css({
				display : "block"
			});
		} else {
			$("#" + divID).css({
				display : "none"
			});
		}
	}

</script>

</head>

<body>
<div >
<div id="lable"><span >团队计调安排</span></div>
<div id="jd-table">
<div >
   <table class="datas">
	<tr id="first-tr">
	  <td colspan="8"><span>&nbsp;&nbsp;基本信息</span></td>
	</tr>
	<tr>
	  <td align="right">线路名称：</td>
	  <td colspan="2"><%=rd.getStringByDI("TA_DJ_GROUPs","XLMC",0) %></td>
	  <td align="right">团&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
	  <td  colspan="4"><font color="red"><%=groupId %></font></td>
	</tr>
	<tr>
	  <td align="right">出发时间：</td>
	  <td colspan="2"><%=rd.getStringByDI("TA_DJ_GROUPs","BEGIN_DATE",0) %></td>
	  <td align="right">返程时间：</td>
	  <td colspan="4"><%=rd.getStringByDI("TA_DJ_GROUPs","END_DATE",0) %></td>
	</tr>
	<tr>
	  <td align="right">组&nbsp;团&nbsp;社：</td>
	  <td colspan="2"></td>
	  <td align="right">天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数：</td>
	  <td colspan="4"><%=rd.getStringByDI("TA_DJ_GROUPs","ts",0) %>天<%=rd.getStringByDI("TA_DJ_GROUPs","NIGHT",0).equals("")?"0":rd.getStringByDI("TA_DJ_GROUPs","NIGHT",0) %>晚</td>
	</tr>
	<tr>
	  <td align="right">加点购物：</td>
	  <td><%if("1".equals(rd.getStringByDI("TA_DJ_GROUPs","gw",0))){ %>√<%} %>加点	<%if("1".equals(rd.getStringByDI("TA_DJ_GROUPs","jd",0))){ %>√<%} %>购物</td>
	  <td align="right">进店个数：</td>
	  <td><%=rd.getStringByDI("TA_DJ_GROUPs","GWDS",0) %> 个</td>
	  <td align="right">是否自备车：</td>
	  <td><%=rd.getStringByDI("TA_DJ_GROUPs","SFZBC",0).equals("1")?"是":"否" %></td>
	  <td align="right">用车类型：</td>
	  <td>
	  	<%
	  	  String cllx = rd.getStringByDI("TA_DJ_GROUPs","VEHTYPE",0);
	  	  for(int i=0;i<rd.getTableRowsCount("cllx");i++) {
	  		  
	  		  String dmz = rd.getStringByDI("CLLX","dmz",i);
	  		  String dmsm = rd.getStringByDI("CLLX","dmsm1",i);
	  		  if(cllx.equals(dmz))
	  			  out.print(dmsm);
	  	  }
	  	%>
	  </td>
	</tr>
  </table>
</div>

<div id="add-table">	
   <table class="datas">
	<tr id="first-tr" onclick="show('xcmx');">
	  <td colspan="8"><span>&nbsp;&nbsp;行程明细（显示/隐藏）</span></td>
	</tr>
  </table>
</div>
<div style="display: none" id="xcmx">
  <table class="datas">
	<tr id="first-tr" >
	  <td align="center" width="10%">天数</td>
	  <td align="center" width="50%">行程明细</td>
	  <td align="center" width="10%">含餐</td>
	  <td align="center" width="10%">餐标</td>
	  <td align="center" width="10%">住宿</td>
	  <td align="center" width="10%">住宿标准</td>
	</tr>
<%
	for(int i=0;i<rd.getTableRowsCount("TA_DJ_LINEDETAI4Gs");i++) {
		String zc = rd.getString("TA_DJ_LINEDETAI4Gs","BREAKFAST",i);
		String zhenc = rd.getString("TA_DJ_LINEDETAI4Gs","CMEAL",i);
		String wc = rd.getString("TA_DJ_LINEDETAI4Gs","SUPPER",i);
%>
	<tr>
	  <td align="center">D<%=rd.getStringByDI("TA_DJ_LINEDETAI4Gs","rq",i) %></td>
	  <td align="center">
	  <%
	  Object obj = rd.get("TA_DJ_LINEDETAI4Gs", "XCMX",i);
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
	  </td>
	  <td align="center"><%=zc.equals("Y")?"早餐":"" %> <%=zhenc.equals("Y")?"正餐":"" %> <%=wc.equals("Y")?"晚餐":"" %></td>
	  <td align="center"><%=rd.getString("TA_DJ_LINEDETAI4Gs","cb",i) %></td>
	  <td align="center"><%=rd.getString("TA_DJ_LINEDETAI4Gs","ZS",i) %></td>
	  <td align="center">
	  <%
	  String zsbz = rd.getString("TA_DJ_LINEDETAI4Gs","ZSBZ",i);
	  for(int j=0;j<rd.getTableRowsCount("JDXJ");j++) {
		  String dmz = rd.getStringByDI("JDXJ","dmz",j);
  		  String dmsm = rd.getStringByDI("JDXJ","dmsm1",j);
  		  if(zsbz.equals(dmz)){
  			  out.print(dmsm);
  		  }
	  }
	  %>
	  </td>
	</tr>
<%
	}
%>
  </table>
</div>

<div >
  <table class="datas">
	<tr id="first-tr" onclick="show('remark');">
	  <td colspan="4"><span>&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注（显示/隐藏）</span></td>
	</tr>
  </table>
</div>
<div style="display: none" id="remark">
  <table class="datas">
	<tr>
	  <td><%=rd.getStringByDI("TA_DJ_GROUPs","COMMENTS",0)%></td>
	</tr>
  </table>
</div>
<div >
  <table class="datas">
	<tr id="first-tr">
	  <td colspan="4"><span>&nbsp;&nbsp;计调安排</span></td>
	</tr>
  </table>
</div>

<div id="add-table">
<table class="datas">
<tr style="height: 6px;"><td></td></tr>
   <tr>
	   <td>
			<ul>
			<li class="ckeditorli">
					<a class="a" href="###" style="color:#006666;" onclick="xcmx1('1','hotelMd','djAjaxHotelInfo.?groupId=<%=groupId%>');">酒店安排&nbsp;&nbsp;&nbsp;&nbsp;<span>【<font color="red"><var id="hotelRow"><%=rd.getStringByDI("JHHOTEL","SUM",0) %></var></font>条信息】</span><span>【酒店成本签单：<font color="red"><var id="hotelCbqd"><%=rd.getStringByDI("JHHOTEL","QDJE",0) %> </var></font>元】</span><span>【酒店成本现付：<font color="red"><var id="hotelCbxf"><%=rd.getStringByDI("JHHOTEL","XFJE",0) %> </var></font>元】</span><span>【酒店预估成本：<font color="red"><var id="hotelYgcb"><%=rd.getStringByDI("JHHOTEL","YGCB",0) %></var> </font>元】</span></a>
				<ul>
					<li>
						<div id="list-table">
						<table id="tbNone" class="datas">
							<tr>
								<td colspan="12" align="left">
									  <button class="addBtn" value="添加"  onclick="addTrRow('hotelMd','hotel');">添加酒店安排</button>
									  <button class="delBtn" value="删除"  onclick="delTr('hotelRow','hotelCheckbox')">删除酒店安排</button>
									  <button class="saveBtn" value="临时保存"  onclick="frmSubmit('frmHotel','djFrmInsertHotel.?temp=N&groupId=<%=groupId%>','hotel')">临时保存</button>
									  <button class="allSaveBtn" value="完整性保存"  onclick="frmSubmit('frmHotel','djFrmInsertHotel.?temp=Y&groupId=<%=groupId%>','hotel')">完整性保存</button>
								</td>
							</tr>
						</table>
						<form action="" name="frmHotel" id="frmHotel" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
							<table class="datas" id="hotelMd">
								<tr id="first-tr">
									<td width="2%"><input type="checkbox" id="hotelCheckbox"/></td>
									<td width="11%">日程</td>
									<td width="14%">酒店名称</td>
									<td width="6%">联系人</td>
									<td width="7%">联系方式</td>
									<td width="6%">人数</td>
									<td width="6%">天数</td>
									<td width="5%">房型</td>
									<td width="6%">单价	</td>
									<td width="6%">预估成本</td>
									<td width="6%">签单</td>
									<td width="6%">现付</td>
									<td width="6%">含早</td>
									<td width="10%">备注</td>
								</tr>
							</table>
						</form>
					  </div>
					</li>
				</ul>
			</li>
		</ul>
	</td>
</tr>
<tr style="height: 6px;"><td></td></tr>
<tr>
	<td>
		<ul>
			<li class="ckeditorli">
				<a class="a" href="###" style="color:#006666;" onclick="xcmx1('2','dinnerMd','djAjaxDinnerInfo.?groupId=<%=groupId%>');">餐厅安排&nbsp;&nbsp;&nbsp;&nbsp;<span>【<font color="red"><var id="dinnerRow"><%=rd.getStringByDI("JHCT","SUM",0) %></var></font>条信息】</span><span>【餐厅成本签单：<font color="red"><var id="dinnerCbqd"><%=rd.getStringByDI("JHCT","QDJE",0) %> </var></font>元】</span><span>【餐厅成本现付：<font color="red"><var id="dinnerCbxf"><%=rd.getStringByDI("JHCT","XFJE",0) %> </var></font>元】</span><span>【餐厅预估成本：<font color="red"><var id="dinnerYgcb"><%=rd.getStringByDI("JHCT","YGCB",0) %></var> </font>元】</span></a>
				<ul>
					<li>
						<div id="list-table">
						<table id="tbNone" class="datas">
							<tr>
								<td colspan="12" align="left">
									  <button class="addBtn" value="添加"  onclick="addTrRow('dinnerMd','dinner');">添加餐厅安排</button>
									  <button class="delBtn" value="删除"  onclick="delTr('dinnerRow','dinnerCheckbox')">删除餐厅安排</button>
									  <button class="saveBtn" value="临时保存"  onclick="frmSubmit('frmDinner','djFrmInsertDinner.?temp=N&groupId=<%=groupId%>','dinner')">临时保存</button>
									  <button class="allSaveBtn" value="完整性保存"  onclick="frmSubmit('frmDinner','djFrmInsertDinner.?temp=Y&groupId=<%=groupId%>','dinner')">完整性保存</button>
								</td>
							</tr>
						</table>
						<form action="" name="frmDinner" id="frmDinner" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
							<table class="datas" id="dinnerMd">
								<tr id="first-tr">
									<td width="4%"><input type="checkbox" id="dinnerCheckbox"/></td>
									<td width="11%">日程</td>
									<td width="14%">餐馆名称</td>
									<td width="6%">联系人</td>
									<td width="7%">联系方式</td>
									<td width="6%">餐数</td>
									<td width="6%">人数</td>
									<td width="6%">单价</td>
									<td width="6%">预估成本</td>
									<td width="6%">签单</td>
									<td width="6%">现付</td>
									<td width="10%">备注</td>
								</tr>
							</table>
						</form>
					  </div>
					</li>
				</ul>
			</li>
			</ul>
	</td>
</tr>
<tr style="height: 6px;"><td></td></tr>
<tr>
	<td>
		<ul>
			<li class="ckeditorli">
				<a class="a" href="###" style="color:#006666;" onclick="xcmx1('3','ticketMd','djAjaxTicketInfo.?groupId=<%=groupId%>');">票务安排&nbsp;&nbsp;&nbsp;&nbsp;<span>【<font color="red"><var id="ticketRow"><%=rd.getStringByDI("JHPW","SUM",0) %></var></font>条信息】</span><span>【票务成本签单：<font color="red"><var id="ticketCbqd"><%=rd.getStringByDI("JHPW","QD",0) %> </var></font>元】</span><span>【票务成本现付：<font color="red"><var id="ticketCbxf"><%=rd.getStringByDI("JHPW","XF",0) %> </var></font>元】</span><span>【票务预估成本：<font color="red"><var id="ticketYgcb"><%=rd.getStringByDI("JHPW","YGCB",0) %></var> </font>元】</span></a>
				<ul>
					<li>
						<div id="list-table">
						<table id="tbNone" class="datas">
							<tr>
								<td colspan="12" align="left">
								  <div id="btn-span">
									  <button class="addBtn" value="添加"  onclick="addTrRow('ticketMd','ticket');">添加票务安排</button>
									  <button class="delBtn" value="删除"  onclick="delTr('ticketRow','ticketCheckbox')">删除票务安排</button>
									  <button class="saveBtn" value="临时保存"  onclick="frmSubmit('frmTicket','djFrmInsertTicket.?temp=N&groupId=<%=groupId%>','ticket')">临时保存</button>
									  <button class="allSaveBtn" value="完整性保存"  onclick="frmSubmit('frmTicket','djFrmInsertTicket.?temp=Y&groupId=<%=groupId%>','ticket')">完整性保存</button>
								  </div>
								</td>
							</tr>
						</table>
						<form action="" name="frmTicket" id="frmTicket" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
							<table class="datas" id="ticketMd">
								<tr id="first-tr">
									<td width="3%"><input type="checkbox" id="ticketCheckbox"/></td>
									<td width="10%">出发日期</td>
									<td width="9%">出发时间</td>
									<td width="6%">类型</td>
									<td width="6%">票务公司</td>
									<td width="10%">联系信息</td>
									<td width="7%">航班车次</td>
									<td width="10%">起止站</td>
									<td width="5%">张数</td>
									<td width="5%">单价</td>
									<td width="5%">手续费</td>
									<td width="7%">预估成本</td>
									<td width="5%">签单</td>
									<td width="5%">现付</td>
									<td width="10%">备注</td>
								</tr>
							</table>
						</form>
					  </div>
					</li>
				</ul>
			</li>
		</ul>
	</td>
</tr>

<tr style="height: 6px;"><td></td></tr>
<tr>
	<td>
		<ul>
			<li class="ckeditorli">
				<a class="a" href="###" style="color:#006666;" onclick="xcmx1('4','carMd','djAjaxCarInfo.?groupId=<%=groupId%>&dmsm/CLLX=13');">车辆安排&nbsp;&nbsp;&nbsp;&nbsp;<span>【<font color="red"><var id="carRow"><%=rd.getStringByDI("JHCL","SUM",0) %></var></font>条信息】</span><span>【车辆成本签单：<font color="red"><var id="carCbqd"><%=rd.getStringByDI("JHCL","QDJE",0) %> </var></font>元】</span><span>【车辆成本现付：<font color="red"><var id="carCbxf"><%=rd.getStringByDI("JHCL","XFJE",0) %> </var></font>元】</span><span>【车辆预估成本：<font color="red"><var id="carYgcb"><%=rd.getStringByDI("JHCL","JG",0) %></var> </font>元】</span></a>
				<ul>
					<li>
						<div id="list-table">
							<table id="tbNone" class="datas">
							<tr>
								<td colspan="12" align="left">
								  <div id="btn-span">
									  <button class="addBtn" value="添加"  onclick="addTrRow('carMd','car');">添加车辆安排</button>
									  <button class="delBtn" value="删除"  onclick="delTr('carRow','carCheckbox')">删除车辆安排</button>
									  <button class="saveBtn" value="临时保存"  onclick="frmSubmit('frmCar','djFrmInsertCar.?temp=N&groupId=<%=groupId%>','car')">临时保存</button>
									  <button class="allSaveBtn" value="完整性保存"  onclick="frmSubmit('frmCar','djFrmInsertCar.?temp=Y&groupId=<%=groupId%>','car')">完整性保存</button>
								  </div>
								</td>
							</tr>
						</table>
						 	<form action="" name="frmCar" id="frmCar" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
								  <table class="datas" id="carMd">
									<tr id="first-tr">
									  <td width="2%"><input type="checkbox" id="carCheckbox"></td>
									  <td width="7%">开始时间</td>
									  <td width="7%">返回时间</td>
									  <td width="9%">车队</td>
									  <td width="5%">车型</td>
									  <td width="5%">司机</td>
									  <td width="7%">联系方式</td>
									  <td width="5%">车牌号</td>
									  <td width="5%">预估成本</td>
									  <td width="5%">签单</td>
									  <td width="5%">现付</td>
									  <td width="10%">备注</td>
									</tr>
								  </table>
            				</form>
						</div>
					</li>
				</ul>
			</li>
		</ul>
	</td>
</tr>

<tr style="height: 6px;"><td></td></tr>

<tr>
  <td>
	<ul>
	  <li class="ckeditorli">
		<a class="a" href="###" style="color:#006666;" onclick="xcmx1('5','sceneryMd','djAjaxSceneryInfo.?groupId=<%=groupId%>');">景点安排&nbsp;&nbsp;&nbsp;&nbsp;<span>【<font color="red"><var id="sceneryRow"><%=rd.getStringByDI("JHJD","SUM",0) %></var></font>条信息】</span><span>【景点成本签单：<font color="red"><var id="sceneryCbqd"><%=rd.getStringByDI("JHJD","QDJE",0) %> </var></font>元】</span><span>【景点成本现付：<font color="red"><var id="sceneryCbxf"><%=rd.getStringByDI("JHJD","XFJE",0) %> </var></font>元】</span><span>【景点预估成本：<font color="red"><var id="sceneryYgcb"><%=rd.getStringByDI("JHJD","TGCB",0) %></var> </font>元】</span></a>
		<ul>
		  <li>
			<div id="list-table">
			  <table id="tbNone" class="datas">
				<tr>
				  <td colspan="12" align="left">
					<button class="addBtn" value="添加" onclick="addTrRow('sceneryMd','scenery');">添加景点安排</button>
					<button class="delBtn" value="删除" onclick="delTr('sceneryRow','sceneryCheckbox');">删除景点安排</button>
					<button class="saveBtn" value="临时保存" onclick="frmSubmit('frmScenery','djFrmInsertScenery.?temp=N&groupId=<%=groupId%>','scenery');">临时保存</button>
					<button class="allSaveBtn" value="完整性保存" onclick="frmSubmit('frmScenery','djFrmInsertScenery.?temp=Y&groupId=<%=groupId%>','scenery');">完整性保存</button>
				  </td>
				</tr>
			  </table>
		  		<form action="" name="frmScenery" id="frmScenery" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
				  <table class="datas" id="sceneryMd">
					<tr id="first-tr">
				  	<td width="5%"><input type="checkbox" id="sceneryCheckbox"></td>
					<td width="10%">日程</td>
				  	<td width="10%">景点名称</td>
				  	<td width="8%">联系人</td>
				  	<td width="8%">联系方式</td>
				  	<td width="5%">人数</td>
				  	<td width="5%">单价</td>
				  	<td width="8%">预估成本</td>
				  	<td width="5%">签单</td>
				  	<td width="5%">现付</td>
				  	<td width="35%">备注</td>
				  </tr>
				  </table>
	            </form>
	  		</div>
		  </li>
		</ul>
	  </li>
	</ul>
  </td>
</tr>
<tr style="height: 6px;"><td></td></tr>
<tr>
  <td>
	<ul>
	  <li class="ckeditorli">
		<a class="a" href="###" style="color:#006666;" onclick="xcmx1('6','travelMd','djAjaxTravelInfo.?groupId=<%=groupId%>');">地接安排&nbsp;&nbsp;&nbsp;&nbsp;<span>【<font color="red"><var id="travelRow"><%=rd.getStringByDI("JHDJ","SUM",0) %></var></font>条信息】</span><span>【地接成本签单：<font color="red"><var id="travelCbqd"><%=rd.getStringByDI("JHDJ","QDJE",0) %> </var></font>元】</span><span>【地接成本现付：<font color="red"><var id="travelCbxf"><%=rd.getStringByDI("JHDJ","XFJE",0) %> </var></font>元】</span><span>【地接预估成本：<font color="red"><var id="travelYgcb"><%=rd.getStringByDI("JHDJ","YFZK",0) %></var> </font>元】</span></a>
		<ul>
		  <li>
			<div id="list-table">
		  	  <table id="tbNone" class="datas">
				<tr>
				  <td colspan="12" align="left">
					<button class="addBtn" value="添加"  onclick="addTrRow('travelMd','travel');">添加地接安排</button>
					<button class="delBtn" value="删除"  onclick="delTr('travelRow','travelCheckbox')">删除地接安排</button>
					<button class="saveBtn" value="临时保存"  onclick="frmSubmit('frmTravel','djFrmInsertTravel.?temp=N&groupId=<%=groupId%>','travel')">临时保存</button>
					<button class="allSaveBtn" value="完整性保存"  onclick="frmSubmit('frmTravel','djFrmInsertTravel.?temp=Y&groupId=<%=groupId%>','travel')">完整性保存</button>
				  </td>
				</tr>
			  </table>
	  		  <form action="" name="frmTravel" id="frmTravel" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
			  	<table class="datas" id="travelMd">
				  <tr id="first-tr">
				    <td width="5%"><input type="checkbox" id="travelCheckbox"></td>
				    <td width="10%">开始时间</td>
				    <td width="10%">结束时间</td>
				    <td width="10%">地接名称</td>
				    <td width="8%">联系人</td>
				    <td width="10%">联系电话</td>
				    <td width="5%">人数</td>
				    <td width="8%">预估成本</td>
				    <td width="8%">签单</td>
				    <td width="8%">现付</td>
				    <td width="25%">备注</td>
				  </tr>
			  	</table>
              </form>
	  		</div>
		  </li>
		</ul>
	  </li>
	</ul>
  </td>
</tr>
<tr style="height: 6px;"><td></td></tr>
<tr>
	<td>
		<ul>
			<li class="ckeditorli">
				<a class="a" href="###" style="color:#006666;" onclick="xcmx1('7','shopMd','djAjaxShopInfo.?groupId=<%=groupId%>');">购物安排&nbsp;&nbsp;&nbsp;&nbsp;<span>【<font color="red"><var><%=rd.getStringByDI("JHGW","SUM",0) %></var></font>条信息】</span></a>
				<ul>
					<li>
						<div id="list-table">
				  		  <table id="tbNone" class="datas">
							<tr>
							  <td colspan="12" align="left">
								<button class="addBtn" value="添加"  onclick="addTrRow('shopMd','shop');">添加购物安排</button>
								<button class="delBtn" value="删除"  onclick="delTr('shopRow','shopCheckbox');">删除购物安排</button>
								<button class="saveBtn" value="临时保存"  onclick="frmSubmit('frmShop','djFrmInsertShop.?temp=N&groupId=<%=groupId%>','shop');">临时保存</button>
								<button class="allSaveBtn" value="完整性保存"  onclick="frmSubmit('frmShop','djFrmInsertShop.?temp=Y&groupId=<%=groupId%>','shop');">完整性保存</button>
							  </td>
							</tr>
						  </table>
					  		<form action="" name="frmShop" id="frmShop" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
							  <table class="datas" id="shopMd">
								<tr id="first-tr">
								  <td width="5%"><input type="checkbox" id="shopCheckbox"></td>
								  <td width="10%">日程</td>
								  <td width="15%">购物点名称</td>
								  <td width="10%">联系人</td>
								  <td width="10%">联系方式</td>
								  <td width="10%">返点比例</td>
								  <td width="45%">备注</td>
								</tr>
							  </table>
				            </form>
				  		</div>
					</li>
				</ul>
			</li>
		</ul>
	</td>
</tr>
<tr style="height: 6px;"><td></td></tr>
<tr>
  <td>
	<ul>
	  <li class="ckeditorli">
		<a class="a" href="###" style="color:#006666;" onclick="xcmx1('8','plusMd','djAjaxPlusInfo.?groupId=<%=groupId%>');">加点安排&nbsp;&nbsp;&nbsp;&nbsp;<span>【<font color="red"><var><%=rd.getStringByDI("JHJIAD","SUM",0) %></var></font>条信息】</span></a>
		<ul>
	  	  <li>
	  		<div id="list-table">
	  		  <table id="tbNone" class="datas">
				<tr>
				  <td colspan="12" align="left">
					<button class="addBtn" value="添加"  onclick="addTrRow('plusMd','plus');">添加加点安排</button>
					<button class="delBtn" value="删除"  onclick="delTr('plusRow','plusCheckbox');">删除加点安排</button>
					<button class="saveBtn" value="临时保存"  onclick="frmSubmit('frmPlus','djFrmInsertPlus.?temp=N&groupId=<%=groupId%>','plus');">临时保存</button>
					<button class="allSaveBtn" value="完整性保存"  onclick="frmSubmit('frmPlus','djFrmInsertPlus.?temp=Y&groupId=<%=groupId%>','plus');">完整性保存</button>
				  </td>
				</tr>
			  </table>
		  		<form action="" name="frmPlus" id="frmPlus" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
				  <table class="datas" id="plusMd">
					<tr id="first-tr">
					  <td width="5%"><input type="checkbox" id="plusCheckbox"></td>
					  <td width="15%">景点名称</td>
					  <td width="15%">联系人</td>
					  <td width="15%">联系方式</td>
					  <td width="55%">备注</td>
					</tr>
				  </table>
	            </form>
	  		</div>
	  	  </li>
	  	</ul>
	  </li>
	</ul>
  </td>
</tr>
<tr style="height: 6px;"><td></td></tr>
<tr>
  <td>
	<ul>
	  <li class="ckeditorli">
		<a class="a" href="###" style="color:#006666;" onclick="xcmx1('9','otherMd','djAjaxOtherInfo.?groupId=<%=groupId%>');">其他安排&nbsp;&nbsp;&nbsp;&nbsp;<span>【<font color="red"><var id="otherRow"><%=rd.getStringByDI("JHQT","SUM",0) %></var></font>条信息】</span><span>【其他成本签单：<font color="red"><var id="otherCbqd"><%=rd.getStringByDI("JHQT","QDJE",0) %> </var></font>元】</span><span>【其他成本现付：<font color="red"><var id="otherCbxf"><%=rd.getStringByDI("JHQT","XFJE",0) %> </var></font>元】</span><span>【其他预估成本：<font color="red"><var id="otherYgcb"><%=rd.getStringByDI("JHQT","YGCB",0) %></var> </font>元】</span></a>
	  	<ul>
	  	  <li>
			<div id="list-table">
			  <table id="tbNone" class="datas">
				<tr>
				  <td colspan="12" align="left">
					<button class="addBtn" value="添加"  onclick="addTrRow('otherMd','other');">添加其他安排</button>
					<button class="delBtn" value="删除"  onclick="delTr('otherRow','otherCheckbox')">删除其他安排</button>
					<button class="saveBtn" value="临时保存"  onclick="frmSubmit('frmOther','djFrmInsertOther.?temp=N&groupId=<%=groupId%>','other')">临时保存</button>
					<button class="allSaveBtn" value="完整性保存"  onclick="frmSubmit('frmOther','djFrmInsertOther.?temp=Y&groupId=<%=groupId%>','other');">完整性保存</button>
				  </td>
				</tr>
			  </table>
		  	  <form action="" name="frmOther" id="frmOther" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
				<table class="datas" id="otherMd">
				  <tr id="first-tr">
					<td width="5%"><input type="checkbox" id="otherCheckbox"></td>
					<td width="15%">安排内容</td>
					<td width="10%">单价</td>
					<td width="10%">预估成本</td>
					<td width="10%">签单</td>
					<td width="10%">现付</td>
					<td width="40%">备注</td>
				  </tr>
				</table>
	          </form>
	  		</div>
	  	  </li>
	  	</ul>
	  </li>
	</ul>
  </td>
</tr>
<tr style="height: 6px;"><td></td></tr>
<tr>
  <td>
	<ul>
	  <li class="ckeditorli">
		<a class="a" href="###" style="color:#006666;" onclick="xcmx1('10','guideMd','djAjaxGuideInfo.?groupId=<%=groupId%>');">导游安排&nbsp;&nbsp;&nbsp;&nbsp;<span>【<font color="red"><var id="guideRow"><%=rd.getStringByDI("JHDY","SUM",0) %></var></font>条信息】</span><span>【领团款：<font color="red"><var><%=rd.getStringByDI("JHDY","DYLK",0) %></var></font>元】</span><span>【导服费：<font color="red"><var><%=rd.getStringByDI("JHDY","DFF",0) %> </var></font>元】</span></a>
		<ul>
		  <li>
		  	<div id="list-table">
		  	  <table id="tbNone" class="datas">
				<tr>
				  <td colspan="12" align="left">
					<button class="addBtn" value="添加"  onclick="addTrRow('guideMd','guide');">添加导游安排</button>
					<button class="delBtn" value="删除"  onclick="delTr('guideRow','guideCheckbox')">删除导游安排</button>
					<button class="saveBtn" value="临时保存"  onclick="frmSubmit('frmGuide','djFrmInsertGuide.?temp=N&groupId=<%=groupId%>','guide')">临时保存</button>
					<button class="allSaveBtn" value="完整性保存"  onclick="frmSubmit('frmGuide','djFrmInsertGuide.?temp=Y&groupId=<%=groupId%>','guide')">完整性保存</button>
				  </td>
				</tr>
			  </table>
	  		  <form action="" name="frmGuide" id="frmGuide" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
			  	<table class="datas" id="guideMd">
				  <tr id="first-tr">
				    <td width="5%"><input type="checkbox" id="guideCheckbox"></td>
				    <td width="10%">出发时间</td>
				    <td width="10%">返回时间</td>
				    <td width="10%">导游姓名</td>
				    <td width="10%">导游手机</td>
				    <td width="10%">导游证号</td>
				    <td width="5%">领团款</td>
				    <td width="5%">导服费</td>
				    <td width="35%">备注</td>
				  </tr>
			  	</table>
            </form>
	  		</div>
		  </li>
	  	</ul>
	  </li>
	</ul>
  </td>
</tr>
</div>

<div>
<script type="text/javascript">
function p_editSpPlan(){	
	document.p_shplan_form.action="djAuthorizePlan.?STATE="+$("#STATE").val()+"&TID=<%=groupId %>";
	document.p_shplan_form.submit();
}
</script>
<form  name="p_shplan_form" method="post">
<table class="datas">
	<tr id="first-tr">
		<td colspan="3"><span>&nbsp;&nbsp;审批意见</span></td>
	</tr>
	<tr>
		<td colspan="3">
			<%
				int rows=rd.getTableRowsCount("SPYJ");
				if(rows>0){
					for(int i=0;i<rows;i++){
			%>
				<b><%=rd.getStringByDI("SPYJ","USERNAME",i)%>：</b>
				<%=rd.getStringByDI("SPYJ","SPYJ",i)%>;<br/>
			<%
					}
				}
			%>
		</td>
	</tr>
	<tr>
		<td align="right">审批意见：</td>
		<td align="center">
		  <input type="radio" name="ZT" onclick="$('#STATE').val('Y');" checked="checked"/>通过&nbsp;&nbsp;&nbsp;&nbsp;
		  <input type="radio" name="ZT" onclick="$('#STATE').val('N');"/>驳回 
		</td>
		<td>
			<input type="hidden" id="STATE" value="Y"/>
			<textarea cols="50" rows="5" name="TA_DJ_TSPB/SPYJ"></textarea>
		</td>
	</tr>

	<tr>
		<td colspan="3" align="center">
			<br>
			<input type="button"  value="提交" onclick="p_editSpPlan();"/>
			<input type="button"  value="返回" onclick="window.history.go(-1)"/>
			<br>
		</td>
	</tr>
</table>
</form>
</div>

</div>
</div>
<div>
<!-- 动画值隐藏域 -->
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
 <input type="hidden" id="itemRow" class=""  value=""/>
 <input type="hidden" id="mdIndex"  value="<%=rd.getString("md")%>"/>
 <input type="hidden" id="mdName"  value="<%=rd.getString("mdName")%>"/>
 <input type="hidden" id="mdUrl"  value="<%=rd.getString("mdUrl")%>"/>
</div>

 
</body>
</html>