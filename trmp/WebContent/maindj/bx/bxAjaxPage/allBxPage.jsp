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
	
	// ������Ŀ
	int cxRow = rd.getTableRowsCount("CLLX");
	
	String action = rd.getString("action");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
<title>���α���</title>
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
* { margin:0; padding:0; word-wrap:break-word; word-break:break-all;list-style-type:none;}
.ui-button .ui-button-text { display: block; line-height: 0.6; }
td { vertical-align : middle; }
<%if("view".equals(action)){%>
#tbNone {
  display: none;
}
<%}%>
 
.divxs{position:absolute;width:300px;left:50%;height:auto;z-index:100;}
#divbg{background-color:#666;position:absolute;z-index:99;left:0;top:0;display:none;width:100%;height:100%;opacity:0.5;filter: alpha(opacity=50);-moz-opacity: 0.5;}	
</style>

<script type="text/javascript">
//���ֲ����
function divbg(){
	$("#divbg").css({
		display:"block",height:$(document).height()
	});
	$(".divxs").css({
		left:($("body").width()-$(".divxs").width())/2-20+"px",
		top:($(window).height()-$(".divxs").height())/2+$(window).scrollTop()+"px",
		display:"block"
	});
}
function divExit(){
	$("#divbg").css("display","none");
	$(".divxs ").css("display","none");
}

//����Ԥ���ɱ���Ǯ
var tempMdName ='';
function cPri(mdName){
	tempMdName=mdName;
}
function cPriChange(mdName){
	$("."+tempMdName+"Ygcb").each(function(i){
		if(isN(parseFloat($(this).val()))<isN(parseFloat($("."+tempMdName+"Cbqd:eq("+i+")").val()))){
			$("."+tempMdName+"Cbxf:eq("+i+")").val(0);
			$("."+tempMdName+"Cbqd:eq("+i+")").val($(this).val());
		}else{
			$("."+tempMdName+"Cbxf:eq("+i+")").val((isN(parseFloat($(this).val()))-isN(parseFloat($("."+tempMdName+"Cbqd:eq("+i+")").val()))).toFixed(2));
		}
 	});
}


//���㵥��֮��
var tempSnName='';
function cSn(mdName){
	tempSnName=mdName;
}
function cSnChange(mdName){
	if('shop'==mdName){
		$("."+tempSnName+"Temp2").each(function(i){
			$("."+tempSnName+"Temp2:eq("+i+")").val((isN(parseFloat($("."+tempSnName+"Temp3:eq("+i+")").val()))/10).toFixed(2));
		});
		$("."+tempSnName+"SnTemp1").each(function(i){
			//edit by divine 2012-07-10
			/*$(this).val(isN(parseFloat(
					(isN(parseFloat($("."+tempSnName+"Temp3:eq("+i+")").val()))/10*0.5)-
					isN(parseFloat($("."+tempSnName+"dytc:eq("+i+")").val()))-
					isN(parseFloat($("."+tempSnName+"sjtc:eq("+i+")").val()))-
					isN(parseFloat($("."+tempSnName+"qptc:eq("+i+")").val()))
					)));
					*/
			$("."+tempSnName+"Temp5:eq("+i+")").val($(this).val());
		});
	}else if('plus'==mdName){
		$("."+tempSnName+"SnTemp5").each(function(i){
			$("."+tempSnName+"Ygcb:eq("+i+")").val(
					isN(parseFloat($("."+tempSnName+"SnTemp1:eq("+i+")").val()))*
					isN(parseFloat($("."+tempSnName+"SnTemp2:eq("+i+")").val()))
			);			
			$("."+tempSnName+"Temp2:eq("+i+")").val(
					((isN(parseFloat($(this).val())))-(isN(parseFloat($("."+tempSnName+"SnTemp1:eq("+i+")").val()))))*
					isN(parseFloat($("."+tempSnName+"SnTemp2:eq("+i+")").val())));
			//Ӧ����˾
			$("."+tempSnName+"Temp4:eq("+i+")").val(isN(parseFloat($("."+tempSnName+"Temp2:eq("+i+")").val()))-isN(parseFloat($("."+tempSnName+"dytc:eq("+i+")").val()))-isN(parseFloat($("."+tempSnName+"sjtc:eq("+i+")").val()))-isN(parseFloat($("."+tempSnName+"qptc:eq("+i+")").val())));
			//�ӵ�ǩ�����Ӧ���ع�˾
			$("."+tempSnName+"Temp3:eq("+i+")").val(isN(parseFloat($("."+tempSnName+"Cbqd:eq("+i+")").val()))+isN(parseFloat($("."+tempSnName+"Temp4:eq("+i+")").val())));
		});
	}else{
		$("."+tempSnName+"SnTemp1").each(function(i){
			$("."+tempSnName+"Ygcb:eq("+i+")").val(
					((isN(parseFloat($(this).val()))*isN(parseFloat($("."+tempSnName+"SnTemp2:eq("+i+")").val()))*isN(parseFloat($("."+tempSnName+"SnTemp3:eq("+i+")").val())))+
					(isN(parseFloat($(this).val()))*isN(parseFloat($("."+tempSnName+"SnTemp4:eq("+i+")").val())))).toFixed(2)
			);
			$("."+tempSnName+"Cbxf:eq("+i+")").val((isN(parseFloat($("."+tempSnName+"Ygcb:eq("+i+")").val()))).toFixed(2));
		});
		
	}
}
/**
*	checkBox��ȫѡ��ȫ��ѡ
*/
$(function() {
	//���س��Զ����㵱ǰ�۸�
	/*$('body').keydown(function(event) {
		if (event.keyCode == '13') {
			$("."+tempMdName+"Ygcb").each(function(i){
				if(isN(parseFloat($(this).val()))<isN(parseFloat($("."+tempMdName+"Cbqd:eq("+i+")").val()))){
					$("."+tempMdName+"Cbxf:eq("+i+")").val(0);
					$("."+tempMdName+"Cbqd:eq("+i+")").val($(this).val());
				}else{
					$("."+tempMdName+"Cbxf:eq("+i+")").val((isN(parseFloat($(this).val()))-isN(parseFloat($("."+tempMdName+"Cbqd:eq("+i+")").val()))).toFixed(2));
					$("."+tempMdName+"Cbqd:eq("+i+")").val((isN(parseFloat($(this).val()))-isN(parseFloat($("."+tempMdName+"Cbxf:eq("+i+")").val()))).toFixed(0));
				}
		 	});
		 	$("."+tempSnName+"SnTemp1").each(function(i){
				$("."+tempSnName+"Ygcb:eq("+i+")").val(isN(parseFloat($(this).val()))*isN(parseFloat($("."+tempSnName+"SnTemp2:eq("+i+")").val()))*isN(parseFloat($("."+tempSnName+"SnTemp3:eq("+i+")").val()))+isN(parseFloat($("."+tempSnName+"SnTemp4:eq("+i+")").val())).toFixed(2));
			}); 
		}
	});*/
	
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
	$("var[id$='Row']").css("width","25px");
	
	//��ʼ��button submit
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
 * ģ����ѯJSģ��  Start
 *===================================================
 */
	var itemName=0;
	var itemRow=0;
	var beanCache = [ ];
	var lastFilter = "";
	var lastItemName = "";
	function fillTable(bean) {//option �����

	  var filter = $("."+itemName).eq(itemRow).val();
	  var pattern = new RegExp("(" + filter + ")", "i");
	  var filtered = [];
 
	  for (var i = 0; i < bean.length; i++) {
	    if (pattern.test(bean[i].sName)) {
	      filtered.push(bean[i]);
	    }
	  }
	  //��ձ�������У���ɾ�������У�
	  //dwr.util.removeAllRows("plusBody");
	  if (filtered.length == 0) {//�����Ϊ�� �������� ���� ._li_�Ĳ�
		  $("div[id^='_li_']").hide();
	  }else {
		  dmSelect(itemName, itemRow, filtered, filter);//option (itemName:input��className,row:input�±�ֵ,data:���ݼ�,filter:��������)
	  }
	  beanCache = bean;
	}

	/**
	*	item��ģ��
	*/
	
	function filterChanged(item) {
		itemRow = $("#itemRow").val();
		itemName = item;
		var filter = $("."+itemName).eq(itemRow).val();
		$("."+itemName+"Id").eq(itemRow).val('');
		$("."+itemName+"XZQH").eq(itemRow).val('');
		if (filter.length == 0)
		{
			$("div[id^='_li_']").hide();
		}
		else{
			if (filter.charAt(0) == lastFilter.charAt(0) && lastItemName == itemName)
			{
				fillTable(beanCache);
			} 
			else 
			{
				if('plus' == itemName)
					PYAjaxService.plusExecute(filter.charAt(0), fillTable);//���̨ȡ����
				else if('guide' == itemName){
					PYAjaxService.guideExecute(filter.charAt(0), fillTable);//���̨ȡ����
				}else if('hotel' == itemName){
					PYAjaxService.hotelExecute(filter.charAt(0), fillTable);//���̨ȡ����
				}else if ("car" == itemName){
					PYAjaxService.carFuzzySearch(filter.charAt(0), fillTable); //���̨ȡ����
				}else if('scenery' == itemName){
					PYAjaxService.sceneryExecute(filter.charAt(0), fillTable);//���̨ȡ����
				}else if('dinner' == itemName){
					PYAjaxService.dinnerExecute(filter.charAt(0), fillTable);//���̨ȡ����
				}else if('travel' == itemName){
					PYAjaxService.travelExecute(filter.charAt(0), fillTable);//���̨ȡ����
				}else if('ticket' == itemName){
					PYAjaxService.ticketExecute(filter.charAt(0), fillTable);//���̨ȡ����
				}else if('shop' == itemName){
					PYAjaxService.shopExecute(filter.charAt(0), fillTable);//���̨ȡ����
				}
				lastItemName = itemName;
			}
		}
		lastFilter = filter;
	}

	function dmSelect(itemName, row ,data, filter){//option (itemName:input��className,row:input�±�ֵ,data:���ݼ�,filter:��������)
	    //jQuery("#" + name).attr("readonly","readonly");   //���ÿؼ�ֻ��  
	    var Random = $("."+itemName).eq(row).attr("id");
	    var top;
	    var left;  
	    var height;  
	    var width;
	    var option_open = false; //����Ƿ������option
	    var options= [ ];
	    function __dropheight(l){var h;if(l>10 || l<1){h = 10 * 15;}else{h = l * 15; h += 2;}if('hotel'!=itemName){return h+15;}else{return (h+15)*2;}}   //��������option��ʾ�߶�
	    	 top = $("#" + Random).offset().top;    //��ȡ�ؼ�top��leftλ�ú�width��height  
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
					options = ["plus" ,"plusLinkman","plusLinkmanTel","plusId","plusXZQH"];//Ҫ��input���className��Ӧ
					for(var i = 0;i < data.length; i++){//forѭ�����option   
						div_html += "<div style='text-align:left;padding-left:5px;padding-top:2px;cursor:pointer;'>";
				    	div_html += "<label class='option"+itemName+"'>"+ data[i].sName.replace(filter,"<font color='red'>"+filter+"</font>") +"</label>";
				    	div_html += "<label class='option"+options[1]+"' style='display:none'>"+ data[i].linkman+"</label>";
				    	div_html += "<label class='option"+options[2]+"' style='display:none'>"+ data[i].linkmanTel+"</label>";
				    	div_html += "<label class='option"+options[3]+"' style='display:none'>"+ data[i].sID+"</label>";
				    	div_html += "<label class='option"+options[4]+"' style='display:none'>"+ data[i].city+"</label>";
				    	div_html += "</div>";
					}
				}else if(itemName == 'car'){
					options = ["car","carId","carXZQH"];

					for(var i = 0;i < data.length; i++)
					{
						div_html += "<div style='text-align:left;padding-left:5px;padding-top:2px;cursor:pointer;'>";
				    	div_html += "<label class='option"+itemName+"'>"+ data[i].sName.replace(filter,"<font color='red'>"+filter+"</font>") +"</label>";
				    	div_html += "<label class='option"+options[1]+"' style='display:none'>"+ data[i].sID+"</label>";
				    	div_html += "<label class='option"+options[2]+"' style='display:none'>"+ data[i].city+"</label>";
				    	div_html += "</div>";
					}
				}else if(itemName == 'guide'){
					options = ["guide", "guideId", "guideTel", "guideCode"];
					for(var i = 0;i < data.length; i++){//forѭ�����option   
						div_html += "<div style='text-align:left;padding-left:5px;padding-top:2px;cursor:pointer;'>";
				    	div_html += "<label class='option"+itemName+"'>"+ data[i].sName.replace(filter,"<font color='red'>"+filter+"</font>") +"</label>";
				    	div_html += "<label class='option"+options[1]+"' style='display:none'>"+ data[i].sID+"</label>";
				    	div_html += "<label class='option"+options[2]+"' style='display:none'>"+ data[i].linkman+"</label>";
				    	div_html += "<label class='option"+options[3]+"' style='display:none'>"+ data[i].guideCode+"</label>";
				    	div_html += "</div>";
					}
				}else if(itemName == 'hotel'){
					options = ["hotel","hotelLxr","hotelLxrDh", "hotelId", "hotelXZQH","hotelXj"];
					for(var i = 0;i < data.length; i++){//forѭ�����option   
						div_html += "<div style='text-align:left;padding-left:5px;padding-top:2px;cursor:pointer;'>";
				    	div_html += "<label class='option"+itemName+"'>"+ data[i].sName.replace(filter,"<font color='red'>"+filter+"</font>") +"</label>";
				    	div_html += "<label class='option"+options[1]+"' style='display:none'>"+ data[i].linkman+"</label>";
				    	div_html += "<label class='option"+options[2]+"' style='display:none'>"+ data[i].linkmanTel+"</label>";
				    	div_html += "<label class='option"+options[3]+"' style='display:none'>"+ data[i].sID+"</label>";
				    	div_html += "<label class='option"+options[4]+"' style='display:none'>"+ data[i].city+"</label>";
				    	div_html += "<label class='option"+options[5]+"' style='display:none'>"+ data[i].area+"</label>";
				    	div_html += "<br><label style='display:block'><font color='green'>"+ data[i].areaName+" &nbsp;&nbsp;"+ data[i].csName+"</font></label>";
				    	div_html += "</div>";
					}
				}else if(itemName == 'scenery'){
					options = ["scenery", "sceneryId", "sceneryXZQH", "lxr", "lxfs"];
					for(var i = 0;i < data.length; i++){//forѭ�����option   
						div_html += "<div style='text-align:left;padding-left:5px;cursor:pointer;'>";
				    	div_html += "<label class='option"+itemName+"'>"+ data[i].sName.replace(filter,"<font color='red'>"+filter+"</font>") +"</label>";
				    	div_html += "<label class='option"+options[1]+"' style='display:none'>"+ data[i].sID+"</label>";
				    	div_html += "<label class='option"+options[2]+"' style='display:none'>"+ data[i].city+"</label>";
				    	div_html += "<label class='option"+options[3]+"' style='display:none'>"+ data[i].linkman+"</label>";
				    	div_html += "<label class='option"+options[4]+"' style='display:none'>"+ data[i].linkmanTel+"</label>";
				    	div_html += "<br><label style='display:block'><font color='green'>"+ data[i].sfName+" &nbsp;&nbsp;"+ data[i].csName+"</font></label>";
				    	div_html += "</div>";
					}
				}else if(itemName == 'dinner'){
					options = ["dinner","dinnerLxr","dinnerLxrDh", "dinnerId", "dinnerXZQH"];
					for(var i = 0;i < data.length; i++){//forѭ�����option   
						div_html += "<div style='text-align:left;padding-left:5px;padding-top:2px;cursor:pointer;'>";
				    	div_html += "<label class='option"+itemName+"'>"+ data[i].sName.replace(filter,"<font color='red'>"+filter+"</font>") +"</label>";
				    	div_html += "<label class='option"+options[1]+"' style='display:none'>"+ data[i].linkman+"</label>";
				    	div_html += "<label class='option"+options[2]+"' style='display:none'>"+ data[i].linkmanTel+"</label>";
				    	div_html += "<label class='option"+options[3]+"' style='display:none'>"+ data[i].sID+"</label>";
				    	div_html += "<label class='option"+options[4]+"' style='display:none'>"+ data[i].city+"</label>";
				    	div_html += "</div>";
					}
				}else if(itemName == 'travel'){
					options = ["travel","travelLxr","travelLxfs", "travelId", "travelXZQH"];
					for(var i = 0;i < data.length; i++){//forѭ�����option   
						div_html += "<div style='text-align:left;padding-left:5px;padding-top:2px;cursor:pointer;'>";
				    	div_html += "<label class='option"+itemName+"'>"+ data[i].sName.replace(filter,"<font color='red'>"+filter+"</font>") +"</label>";
				    	div_html += "<label class='option"+options[1]+"' style='display:none'>"+ data[i].linkman+"</label>";
				    	div_html += "<label class='option"+options[2]+"' style='display:none'>"+ data[i].linkmanTel+"</label>";
				    	div_html += "<label class='option"+options[3]+"' style='display:none'>"+ data[i].sID+"</label>";
				    	div_html += "<label class='option"+options[4]+"' style='display:none'>"+ data[i].city+"</label>";
				    	div_html += "<br><label style='display:block'><font color='green'>"+ data[i].sfName+" &nbsp;&nbsp;"+ data[i].csName+"</font></label>";
				    	div_html += "</div>";
					}
				}else if(itemName == 'ticket'){
					options = ["ticket","ticketLxr","ticketLxDh", "ticketId", "ticketXZQH"];
					for(var i = 0;i < data.length; i++){//forѭ�����option   
						div_html += "<div style='text-align:left;padding-left:5px;padding-top:2px;cursor:pointer;'>";
				    	div_html += "<label class='option"+itemName+"'>"+ data[i].sName.replace(filter,"<font color='red'>"+filter+"</font>") +"</label>";
				    	div_html += "<label class='option"+options[1]+"' style='display:none'>"+ data[i].linkman+"</label>";
				    	div_html += "<label class='option"+options[2]+"' style='display:none'>"+ data[i].linkmanTel+"</label>";
				    	div_html += "<label class='option"+options[3]+"' style='display:none'>"+ data[i].sID+"</label>";
				    	div_html += "<label class='option"+options[4]+"' style='display:none'>"+ data[i].city+"</label>";
				    	div_html += "</div>";
					}
				}else if(itemName == 'shop'){
					options = ["shop","shopLxr","shopLxDh", "shopId", "shopXZQH"];
					for(var i = 0;i < data.length; i++){//forѭ�����option   
						div_html += "<div style='text-align:left;padding-left:5px;padding-top:2px;cursor:pointer;'>";
				    	div_html += "<label class='option"+itemName+"'>"+ data[i].sName.replace(filter,"<font color='red'>"+filter+"</font>") +"</label>";
				    	div_html += "<label class='option"+options[1]+"' style='display:none'>"+ data[i].linkman+"</label>";
				    	div_html += "<label class='option"+options[2]+"' style='display:none'>"+ data[i].linkmanTel+"</label>";
				    	div_html += "<label class='option"+options[3]+"' style='display:none'>"+ data[i].sID+"</label>";
				    	div_html += "<label class='option"+options[4]+"' style='display:none'>"+ data[i].city+"</label>";
				    	div_html += "</div>";
					}
				}
				div_html += "</div>";
			$("#_li_" + Random).remove();
			$("#" + Random).after();
			$("#" + Random).after(div_html);   //��ӵ�input�ؼ�����   
	  
	    //alert("ns:"+ns+"-row:"+row);
	    __open_option();
	  	  
	    function __open_option(){//��ʾ����option 
	    	$("div[id^='_li_']").hide();
	    	$("#_li_" + Random).show();
	    	option_open=true;
	    }   
	    function __hide_option(){//��������option
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

	    $("#_li_" + Random + " > div").hover(//����ƶ���Ч �Լ����click��ֵ�¼�
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
	 * ģ����ѯJSģ��  End
	 *===================================================
	 */
	
	
	
	/**
	*	�ӵ�ģ�鶯̬����� optiong(ģ������****Md ��table��id(mdName),  ģ����ѯ�����class Name(csName))
	*/
		function addTrRow(mdName,csName){
			var Random = Math.floor(Math.random() * 123450);
			if('plusMd' == mdName){
				var trRows = $(".plusRow").size();
				$("#plusMd").append('<tr class="plusRow" style="display: block;">' +
				  '<td><input type="checkbox" class="plusCheckbox" /></td>'+
				  '<td><input type="text" name="TA_DJ_BXJIADIAN/JDMC['+trRows+']" class="plus" id="'+Random+'"/>'+
				  '<input type="hidden" name="TA_DJ_BXJIADIAN/JDID['+trRows+']" class="plusId"/>'+
			  	  '<input type="hidden" name="TA_DJ_BXJIADIAN/XZQHID['+trRows+']" class="plusXZQH"/>'+
				  '</td>'+
				  '<td><input type="text" name="TA_DJ_BXJIADIAN/GPJ['+trRows+']" onkeydown="checkNumX()" title="���Ƶ���/��λ��Ԫ" onkeyup="cSn(\'plus\');" onchange="cSnChange(\'plus\')" class="input40 plusSnTemp5"/></td>'+
				  '<td><input type="text" name="TA_DJ_BXJIADIAN/CBJ['+trRows+']" onkeydown="checkNumX()" title="�ɱ�����/��λ��Ԫ" onkeyup="cSn(\'plus\');" onchange="cSnChange(\'plus\')" class="input40 plusSnTemp1"/></td>'+
				  '<td><input type="text" name="TA_DJ_BXJIADIAN/RS['+trRows+']" onkeydown="checkNum()" title="����" onkeyup="cSn(\'plus\');" onchange="cSnChange(\'plus\')" class="input40 plusSnTemp2"/><input type="hidden" value="1" class="plusSnTemp3"/></td>'+
				  '<td><input type="text" name="TA_DJ_BXJIADIAN/CBJE['+trRows+']" title="��ǰ����ɱ��ܼ�/��λ��Ԫ" onkeydown="checkNumX()" onkeyup="cPri(\'plus\');" onchange="cPriChange(\'plus\')" class="input40 plusYgcb"/></td>'+
				  '<td><input type="text" name="TA_DJ_BXJIADIAN/CBQD['+trRows+']" title="�ɱ�ǩ��/��λ��Ԫ" onkeydown="checkNumX()" onkeyup="cPri(\'plus\');cSn(\'plus\');" onchange="cPriChange(\'plus\');cSnChange(\'plus\');" class="input40 plusCbqd"/></td>'+
				  '<td><input type="text" name="TA_DJ_BXJIADIAN/CBXF['+trRows+']" title="�ɱ��ָ�/��λ��Ԫ" readonly="readonly" class="input40 plusCbxf"/></td>'+
				  '<td><input type="text" name="TA_DJ_BXJIADIAN/LR['+trRows+']" onkeydown="checkNumX()" title="�ӵ�����/��λ��Ԫ" onkeyup="cSn(\'plus\');" onchange="cSnChange(\'plus\')" class="input40 plusTemp2"/></td>'+
				  '<td>'+
					'���Σ�<input type="text" name="TA_DJ_BXJIADIAN/DYTC['+trRows+']" onkeydown="checkNumX()" title="���������/��λ��Ԫ" onkeyup="cSn(\'plus\');" onchange="cSnChange(\'plus\')" class="input40 plusdytc"/><p>'+
					'˾����<input type="text" name="TA_DJ_BXJIADIAN/SJTC['+trRows+']" onkeydown="checkNumX()" title="˾�������/��λ��Ԫ" onkeyup="cSn(\'plus\');" onchange="cSnChange(\'plus\')" class="input40 plussjtc"/><p>'+
					'ȫ�㣺<input type="text" name="TA_DJ_BXJIADIAN/QPTC['+trRows+']" onkeydown="checkNumX()" title="ȫ�������/��λ��Ԫ" onkeyup="cSn(\'plus\');" onchange="cSnChange(\'plus\')" class="input40 plusqptc"/><p>'+
					'��˾��<input type="text" name="TA_DJ_BXJIADIAN/GSTC['+trRows+']" onkeydown="checkNumX()" title="��˾�����/��λ��Ԫ" onkeyup="cSn(\'plus\');" onchange="cSnChange(\'plus\')" class="input40 plusTemp4"/><p>'+
				  '</td>'+
				  '<td><input type="text" name="TA_DJ_BXJIADIAN/YJGS['+trRows+']" onkeydown="checkNumX()" title="Ӧ����˾/��λ��Ԫ" class="input40 plusTemp3"/></td>'+
				  '<td><textarea rows="1" style="width:85%" name="TA_DJ_BXJIADIAN/BZ['+trRows+']"></textarea></td>'+
				'</tr>');
			}else if('otherMd' == mdName){
				var trRows = $(".otherRow").size();
				$("#otherMd").append('<tr class="otherRow">'+
						  '<td><input type="checkbox" class="otherCheckbox"></td>'+
						  '<td><input type="text" name="TA_DJ_BXQT/APMC['+trRows+']" /></td>'+
						  '<td><input type="text" name="TA_DJ_BXQT/DJ['+trRows+']" onkeydown="checkNumX()" /></td>'+
						  '<td><input type="text" name="TA_DJ_BXQT/YGCB['+trRows+']" onkeydown="checkNumX()" onkeyup="cPri(\'other\');" onchange="cPriChange(\'other\')" class="otherYgcb"/></td>'+
						  '<td><input type="text" name="TA_DJ_BXQT/QDJE['+trRows+']" onkeydown="checkNumX()" onkeyup="cPri(\'other\');" onchange="cPriChange(\'other\')" class="otherCbqd" /></td>'+
						  '<td><input type="text" name="TA_DJ_BXQT/XFJE['+trRows+']" onkeydown="checkNumX()" readonly="readonly" class="otherCbxf" /></td>'+
						  '<td><textarea rows="1" style="width:85%" name="TA_DJ_BXQT/BZ['+trRows+']"></textarea></td>'+
						  '</tr>');
			}else if('hotelMd' == mdName){
				var trRows = $(".hotelRow").size();
				$("#hotelMd").append('<tr class="hotelRow">'+
						'<td><input type="checkbox" class="hotelCheckbox"></td>'+
						'<td>'+
						'<select name="TA_DJ_BXHOTEL/RC['+trRows+']">'+
						<%calendar.setTime(date);
						for (int i = 0; i < days; i++) {
							String dateStr = sdf.format(calendar.getTime());
							int dt =1+i;
							%>
							  '<option value="<%=dateStr%>" ><%= "��D"+dt+"��"+ dateStr%></option>'+
							<%
									calendar.add(Calendar.DAY_OF_MONTH, 1);
							}
						%>
						'</select><input type="hidden" name="TA_DJ_BXHOTEL/RZRQ['+trRows+']" value="<%=hdate%>" class="rzrq"/>'+
						'<input type="hidden" name="TA_DJ_BXHOTEL/xzqhid['+trRows+']" class="hotelXZQH"/>'+
						'<input type="hidden" name="TA_DJ_BXHOTEL/XJ['+trRows+']" class="hotelXj"/>'+
						'<input type="hidden" name="TA_DJ_BXHOTEL/JDID['+trRows+']"  class="hotelId"/>'+
						'</td>'+
						'<td><input type="text" name="TA_DJ_BXHOTEL/JDMC['+trRows+']" class="hotel"  id="'+Random+'"/></td>'+
						'<td><input type="text" name="TA_DJ_BXHOTEL/LXR['+trRows+']"  class="smallInput hotelLxr"/></td>'+
						'<td><input type="text" name="TA_DJ_BXHOTEL/LXRDH['+trRows+']"  class="smallInput hotelLxrDh"/></td>'+
						'<td><input type="text" name="TA_DJ_BXHOTEL/RZRS['+trRows+']" onkeydown="checkNum()" onkeyup="cSn(\'hotel\');" onchange="cSnChange(\'hotel\')" class="input40 hotelSnTemp1"/></td>'+
						'<td><input type="text" name="TA_DJ_BXHOTEL/TS['+trRows+']" onkeydown="checkNum()" onkeyup="cSn(\'hotel\');" onchange="cSnChange(\'hotel\')" class="input40 hotelSnTemp2"/></td>'+
						'<td><a>�༭</a></td>'+
						'<td><input type="text" name="TA_DJ_BXHOTEL/DJ['+trRows+']" onkeydown="checkNumX()" onkeyup="cSn(\'hotel\');" onchange="cSnChange(\'hotel\')" class="input40 hotelSnTemp3"/></td>'+
						'<td><input type="text" name="TA_DJ_BXHOTEL/YGCB['+trRows+']" onkeydown="checkNumX()" onkeyup="cPri(\'hotel\');" onchange="cPriChange(\'hotel\')" class="smallInput hotelYgcb"/></td>'+
						'<td><input type="text" name="TA_DJ_BXHOTEL/QDJE['+trRows+']" onkeydown="checkNumX()" onkeyup="cPri(\'hotel\');" onchange="cPriChange(\'hotel\')" class="smallInput hotelCbqd"/></td>'+
						'<td><input type="text" name="TA_DJ_BXHOTEL/XFJE['+trRows+']" onkeydown="checkNumX()" readonly="readonly" class="smallInput hotelCbxf"/></td>'+
						'<td>'+
						'<select name="TA_DJ_BXHOTEL/SFHZ['+trRows+']">'+
						'<option value="-1">������</option>'+
						'<option value="1">׿��</option>'+
						'<option value="2">������</option>'+
						'<option value="3">�����</option>'+
						'</select>'+
						'</td>'+
						'<td><textarea rows="1" style="width:85%" name="TA_DJ_BXHOTEL/BZ['+trRows+']"></textarea></td>'+
					'</tr>');
				$("select").bind('change',function(){//ģ����ѯ���ư�keyup�¼�
					$(this).next("input").val($(this).val());
				});
			}else if('dinnerMd' == mdName){
				var trRows = $(".dinnerRow").size();
				$("#dinnerMd").append('<tr class="dinnerRow">'+
						'<td><input type="checkbox" class="dinnerCheckbox"></td>'+
						'<td>'+
						'<select name="TA_DJ_BXCT/RC['+trRows+']" class="rc">'+
						<%
						calendar.setTime(date);
						for (int i = 0; i < days; i++) {
							int dt =1+i;
							String dateStr = sdf.format(calendar.getTime());
							%>
							  '<option value="<%=dateStr%>:1" ><%= "��D"+dt+"��"+ dateStr+" ��"%></option>'+
							  '<option value="<%=dateStr%>:2" ><%= "��D"+dt+"��"+ dateStr+" ��"%></option>'+
							  '<option value="<%=dateStr%>:3" ><%= "��D"+dt+"��"+ dateStr+" ��"%></option>'+
							<%
									calendar.add(Calendar.DAY_OF_MONTH, 1);
							}
						%>
						'</select><input type="hidden" name="TA_DJ_BXCT/YCRQ['+trRows+']" value="<%=hdate%>" class="ycrq"/><input type="hidden" name="TA_DJ_BXCT/CC['+trRows+']" value="1" class="cc"/>'+
						'<input type="hidden" name="TA_DJ_BXCT/XZQHID['+trRows+']" class="dinnerXZQH"/>'+
						'<input type="hidden" name="TA_DJ_BXCT/CT['+trRows+']"  class="dinnerId"/>'+
						'</td>'+
						'<td><input type="text" name="TA_DJ_BXCT/CTMC['+trRows+']" class="dinner"  id="'+Random+'"/></td>'+
						'<td><input type="text" name="TA_DJ_BXCT/LXR['+trRows+']"  class="smallInput dinnerLxr"/></td>'+
						'<td><input type="text" name="TA_DJ_BXCT/LXFS['+trRows+']"  class="smallInput dinnerLxrDh"/></td>'+
						'<td><input type="text" name="TA_DJ_BXCT/CS['+trRows+']" onkeydown="checkNum()" onkeyup="cSn(\'dinner\');" onchange="cSnChange(\'dinner\')" class="input40 dinnerSnTemp1"/></td>'+
						'<td><input type="text" name="TA_DJ_BXCT/RS['+trRows+']" onkeydown="checkNum()" onkeyup="cSn(\'dinner\');" onchange="cSnChange(\'dinner\')" class="input40 dinnerSnTemp2"/></td>'+
						'<td><input type="text" name="TA_DJ_BXCT/JG['+trRows+']" onkeydown="checkNumX()" onkeyup="cSn(\'dinner\');" onchange="cSnChange(\'dinner\')" class="input40 dinnerSnTemp3"/></td>'+
						'<td><input type="text" name="TA_DJ_BXCT/YGCB['+trRows+']" onkeydown="checkNumX()" onkeyup="cPri(\'dinner\');" onchange="cPriChange(\'dinner\')" class="smallInput dinnerYgcb"/></td>'+
						'<td><input type="text" name="TA_DJ_BXCT/QDJE['+trRows+']" onkeydown="checkNumX()" onkeyup="cPri(\'dinner\');" onchange="cPriChange(\'dinner\')" class="smallInput dinnerCbqd"/></td>'+
						'<td><input type="text" name="TA_DJ_BXCT/XFJE['+trRows+']" onkeydown="checkNumX()" readonly="readonly" class="smallInput dinnerCbxf"/></td>'+
						'<td><textarea rows="1" style="width:85%" name="TA_DJ_BXCT/BZ['+trRows+']"></textarea></td>'+
					'</tr>');
				$("select").bind('change',function(){//ģ����ѯ���ư�keyup�¼�
					var temp = $(this).val().split(':',2);
					$(this).next().val(temp[0]);
					$(this).next().next().val(temp[1]);
				});
			}else if('guideMd' == mdName){
				var trRows = $(".guideRow").size();
				$("#guideMd").append('<tr class="guideRow">'+
						  '<td><input type="checkbox" class="guideCheckbox"></td>'+
						  '<td>'+
						  '<select name="TA_DJ_BXDY/CFRQ['+trRows+']">'+
						  <%calendar.setTime(date);
							for (int j = 0; j < days; j++) {
								int dt = 1+j;
								String dateStr = sdf.format(calendar.getTime());
						  %>
						  '<option value="<%=dateStr%>"><%= "��D"+dt+"��"+ dateStr%></option>'+
						  <%
								calendar.add(Calendar.DAY_OF_MONTH, 1);
							}
						  %>
						  '</select></td>'+
						  '<td>'+
						  '<select name="TA_DJ_BXDY/fhrq['+trRows+']">'+
						  <%
						  	calendar.setTime(date);
							for (int j = 0; j < days; j++) {
								int dt = 1+j;
								String dateStr = sdf.format(calendar.getTime());
						  %>
						  '<option value="<%=dateStr%>"><%= "��D"+dt+"��"+ dateStr%></option>'+
						  <%
								calendar.add(Calendar.DAY_OF_MONTH, 1);
							}
						  %>
						  '</select>'+
						  '</td>'+
						  '<td><input type="text" name="TA_DJ_BXDY/DYXM['+trRows+']" class="guide smallInput" id="'+Random+'"/><input type="hidden" name="TA_DJ_BXDY/DYID['+trRows+']" class="guideId"/></td>'+
						  '<td><input type="text" name="TA_DJ_BXDY/SJHM['+trRows+']" class="guideTel smallerInput"/></td>'+
						  '<td><input type="text" name="TA_DJ_BXDY/DYZH['+trRows+']" class="guideCode smallerInput"/></td>'+
						  '<td><input type="text" name="TA_DJ_BXDY/DYLK['+trRows+']" onkeydown="checkNumX()" class="smallInput guideTemp1"/></td>'+
						  '<td><input type="text" name="TA_DJ_BXDY/DFF['+trRows+']" onkeydown="checkNumX()" class="smallInput guideTemp2"/></td>'+
						  '<td><textarea rows="1" style="width:85%" name="TA_DJ_BXDY/BZ['+trRows+']"></textarea></td>'+
						  '</tr>');
			}else if('sceneryMd' == mdName){
				var trRows = $(".sceneryRow").size();
				$("#sceneryMd").append('<tr class="sceneryRow">'+
						  '<td><input type="checkbox" class="sceneryCheckbox"></td>'+
						  '<td>'+
							  '<select name="TA_DJ_BXJD/days['+trRows+']">'+
							  <%
							  	calendar.setTime(date);
								for (int j = 0; j < days; j++) {
									int dt = 1+j;
									String dateStr = sdf.format(calendar.getTime());
							  %>
							  '<option value="<%= "��D"+dt+"��,"+ dateStr%>"><%= "��D"+dt+"��"+ dateStr%></option>'+
							  <%
									calendar.add(Calendar.DAY_OF_MONTH, 1);
								}
							  %>
							  '</select>'+
						  '</td>'+
						  '<td>'+
							  '<input type="hidden" name="TA_DJ_BXJD/xzqhid['+trRows+']" class="sceneryXZQH"/>'+
							  '<input type="hidden" name="TA_DJ_BXJD/JDID['+trRows+']" class="sceneryId"/>'+
							  '<input type="text" name="TA_DJ_BXJD/JDMC['+trRows+']" class="scenery" id="'+Random+'"/>'+
						  '</td>'+
						  '<td><input type="text" name="TA_DJ_BXJD/lxr['+trRows+']" class="smallInput lxr"/></td>'+
						  '<td><input type="text" name="TA_DJ_BXJD/lxfs['+trRows+']" class="smallInput lxfs"/></td>'+
						  '<td><input type="text" name="TA_DJ_BXJD/rs['+trRows+']" onkeydown="checkNum()" onkeyup="cSn(\'scenery\');" onchange="cSnChange(\'scenery\')" class="smallInput scenerySnTemp1"/></td>'+
						  '<td><input type="text" name="TA_DJ_BXJD/dj['+trRows+']" onkeydown="checkNumX()" onkeyup="cSn(\'scenery\');" onchange="cSnChange(\'scenery\')" class="smallInput scenerySnTemp2"/><input type="hidden" value="1" onkeydown="checkNumX()" onkeyup="cSn(\'scenery\');" onchange="cSnChange(\'scenery\')" class="input40 scenerySnTemp3"/></td>'+
						  '<td><input type="text" name="TA_DJ_BXJD/TGCB['+trRows+']" onkeydown="checkNumX()" onkeyup="cPri(\'scenery\');" onchange="cPriChange(\'scenery\')" class="smallInput sceneryYgcb"/></td>'+
						  '<td><input type="text" name="TA_DJ_BXJD/QDJE['+trRows+']" onkeydown="checkNumX()" onkeyup="cPri(\'scenery\');" onchange="cPriChange(\'scenery\')" class="smallInput sceneryCbqd"/></td>'+
						  '<td><input type="text" name="TA_DJ_BXJD/xfje['+trRows+']" onkeydown="checkNumX()" readonly="readonly" class="smallInput sceneryCbxf"/></td>'+
						  '<td><textarea rows="1" style="width:85%" name="TA_DJ_BXJD/BZ['+trRows+']"></textarea></td>'+
						  '</tr>');
			}else if('travelMd' == mdName){
				var trRows = $(".travelRow").size();
				$("#travelMd").append('<tr class="travelRow">'+
					    '<td><input type="checkbox" class="travelCheckbox"></td>'+
					    '<td>'+
					    	'<select name="TA_DJ_BXDJ/ksrq['+trRows+']">'+
							  <%
							  	calendar.setTime(date);
								for (int j = 0; j < days; j++) {
									int dt = 1+j;
									String dateStr = sdf.format(calendar.getTime());
							  %>
							  '<option value="<%=dateStr %>"><%= "��D"+dt+"��"+ dateStr%></option>'+
							  <%
									calendar.add(Calendar.DAY_OF_MONTH, 1);
								}
							  %>
							  '</select>'+
						'</td>'+
					    '<td>'+
					    	'<select name="TA_DJ_BXDJ/JSRQ['+trRows+']">'+
							  <%
							  	calendar.setTime(date);
								for (int j = 0; j < days; j++) {
									int dt = 1+j;
									String dateStr = sdf.format(calendar.getTime());
							  %>
							  '<option value="<%=dateStr %>"><%= "��D"+dt+"��"+ dateStr%></option>'+
							  <%
									calendar.add(Calendar.DAY_OF_MONTH, 1);
								}
							  %>
							  '</select>'+
					    '</td>'+
					    '<td>'+
					      '<input type="text" name="TA_DJ_BXDJ/DJSMC['+trRows+']" class="travel" id="'+Random+'"/>'+
					      '<input type="hidden" name="TA_DJ_BXDJ/DJSID['+trRows+']" class="travelId"/>'+
					      '<input type="hidden" name="TA_DJ_BXDJ/xzqhid['+trRows+']" class="travelXZQH"/>'+
					    '</td>'+
					    '<td><input type="text" name="TA_DJ_BXDJ/lxr['+trRows+']" class="travelLxr smallInput"/></td>'+
					    '<td><input type="text" name="TA_DJ_BXDJ/lxfs['+trRows+']" class="travelLxfs smallerInput"/></td>'+
					    '<td><input type="text" name="TA_DJ_BXDJ/crrs['+trRows+']" onkeydown="checkNum()" class="smallInput"/></td>'+
					    '<td><input type="text" name="TA_DJ_BXDJ/YFZK['+trRows+']" onkeydown="checkNumX()" onkeyup="cPri(\'travel\');" onchange="cPriChange(\'travel\')" class="smallInput travelYgcb"/></td>'+					    
					    '<td><input type="text" name="TA_DJ_BXDJ/QDJE['+trRows+']" onkeydown="checkNumX()" onkeyup="cPri(\'travel\');" onchange="cPriChange(\'travel\')" class="smallInput travelCbqd"/></td>'+
					    '<td><input type="text" name="TA_DJ_BXDJ/XFJE['+trRows+']" onkeydown="checkNumX()" readonly="readonly" class="smallInput travelCbxf"/></td>'+
					    '<td><textarea rows="1" style="width:85%" name="TA_DJ_BXDJ/BZ['+trRows+']"></textarea></td>'+
					  '</tr>');
			}else if ('carMd' == mdName){
				var trRows = $(".carRow").size();
				$("#carMd").append(
					'<tr class="carRow">' +
						'<td><input type="checkbox" class="carCheckbox" /></td>' +
	  					'<td><select name="TA_DJ_BXCL/KSRQ['+trRows+']" class="ksrq">'+
						<%calendar.setTime(date);
						for (int i = 0; i < days; i++) {
							String dateStr = sdf.format(calendar.getTime());
							int dt =1+i;
							%>
							  '<option value="<%=dateStr%>" ><%= "��D"+dt+"��"+ dateStr%></option>'+
							<%
									calendar.add(Calendar.DAY_OF_MONTH, 1);
							}
						%>
						'</select></td>' +
	 					'<td><select name="TA_DJ_BXCL/JSRQ['+trRows+']" class="jsrq">'+
							<%	calendar.setTime(date);
								for (int i = 0; i < days; i++) {
								String dateStr = sdf.format(calendar.getTime());
								int dt =1+i;%>
								  '<option value="<%=dateStr%>" ><%= "��D"+dt+"��"+ dateStr%></option>'+
								<%calendar.add(Calendar.DAY_OF_MONTH, 1);}%>
						'</select></td>' +
	  					'<td><input type="hidden" name="TA_DJ_BXCL/CD['+trRows+']"  class="carId" />' +
	 	  					'<input type="hidden" name="TA_DJ_BXCL/xzqhid['+trRows+']"  class="carXZQH" />' +
	 	  					'<input type="text" name="TA_DJ_BXCL/CDMC['+trRows+']" class="car" id="'+Random+'"/></td>' +
						'<td>' +
						 	'<select name="TA_DJ_BXCL/CX['+trRows+']">'+
	 								<%for (int k =0; k < cxRow; k++){
	 									String cucx = rd.getStringByDI("CLLX","DMSM1",k);%>
	 										'<option value="<%=rd.getStringByDI("CLLX","DMZ",k)%>"><%=cucx %></option>'+
	 								<%}%>
	 							'</select>' +
						'</td>' +
						'<td><input type="text" name="TA_DJ_BXCL/SJXM['+trRows+']"  class="smallInput sjxm"/></td>' +
						'<td><input type="text" name="TA_DJ_BXCL/SJDH['+trRows+']"  class="smallerInput sjdh"/></td>' +
					 	'<td><input type="text" name="TA_DJ_BXCL/CP['+trRows+']"  class="smallInput cp"/></td>' +
					 	'<td><input type="text" name="TA_DJ_BXCL/JG['+trRows+']" onkeydown="checkNumX()" onkeyup="cPri(\'car\');" onchange="cPriChange(\'car\')" class="smallInput carYgcb"/></td>' +
					 	'<td><input type="text" name="TA_DJ_BXCL/QDJE['+trRows+']" onkeydown="checkNumX()" onkeyup="cPri(\'car\');" onchange="cPriChange(\'car\')" class="smallInput carCbqd"/></td>' +
					 	'<td><input type="text" name="TA_DJ_BXCL/XFJE['+trRows+']" onkeydown="checkNumX()" readonly="readonly" class="smallInput carCbxf"/></td>' +
					  	'<td><textarea rows="1" style="width:85%" name="TA_DJ_BXCL/BZ['+trRows+']"></textarea>' +
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
							  '<option value="<%=dateStr%>" ><%= "��D"+dt+"��"+ dateStr%></option>'+
							<%
									calendar.add(Calendar.DAY_OF_MONTH, 1);
							}
						%>
						'</select><input type="hidden"  name="TA_DJ_BXPW/CFRQ['+trRows+']" value="<%=hdate%>" />'+
						'<input type="hidden" name="TA_DJ_BXPW/xzqhid['+trRows+']" class="ticketXZQH"/>'+
						'<input type="hidden" name="TA_DJ_BXPW/DGD['+trRows+']"  class="ticketId"/>'+
						'</td>'+
						'<td>'+
						'<input type="text"name="TA_DJ_BXPW/CFSJ['+trRows+']" class="smallerInput"/>' +
						'</td>'+
						'<td>'+
						'<select name="TA_DJ_BXPW/JTLX['+trRows+']">'+
						'<option value="1">��</option>'+
						'<option value="2">�ɻ�</option>'+
						'<option value="3">����</option>'+
						'</select>'+
						'</td>'+
						'<td><input type="text" name="TA_DJ_BXPW/DGDMC['+trRows+']" class="smallerInput ticket"  id="'+Random+'"/></td>'+
						'<td><label>������</label><input type="text" name="TA_DJ_BXPW/LXR['+trRows+']"  class="smallInput ticketLxr"/><p><label>�绰��</label><input type="text" name="TA_DJ_BXPW/LXRDH['+trRows+']"  class="smallInput ticketLxrDh"/></td>'+
						'<td><input type="text" name="TA_DJ_BXPW/HBCC['+trRows+']" class="input40"/></td>'+
						'<td><label>������</label><input type="text" name="TA_DJ_BXPW/QSZ['+trRows+']" class="input40"/><p><label>���أ�</label><input type="text" name="TA_DJ_BXPW/ZDZ['+trRows+']" class="input40"/></td>'+
						'<td><input type="text" name="TA_DJ_BXPW/ZS['+trRows+']" onkeydown="checkNum()" onkeyup="cSn(\'ticket\');" onchange="cSnChange(\'ticket\')" class="input40 ticketSnTemp1"/></td>'+
						'<td><input type="text" name="TA_DJ_BXPW/DJ['+trRows+']" onkeydown="checkNumX()" onkeyup="cSn(\'ticket\');" onchange="cSnChange(\'ticket\')" class="input40 ticketSnTemp2"/><input type="hidden" value="1" onkeydown="checkNumX()" onkeyup="cSn(\'ticket\');" onchange="cSnChange(\'ticket\')" class="input40 ticketSnTemp3"/></td>'+
						'<td><input type="text" name="TA_DJ_BXPW/SXF['+trRows+']" onkeydown="checkNumX()" onkeyup="cSn(\'ticket\');" onchange="cSnChange(\'ticket\')" class="input40 ticketSnTemp4"/></td>'+
						'<td><input type="text" name="TA_DJ_BXPW/YGCB['+trRows+']" onkeydown="checkNumX()" onkeyup="cPri(\'ticket\');" onchange="cPriChange(\'ticket\')" class="input40 ticketYgcb"/></td>'+
						'<td><input type="text" name="TA_DJ_BXPW/QD['+trRows+']" onkeydown="checkNumX()" onkeyup="cPri(\'ticket\');" onchange="cPriChange(\'ticket\')" class="input40 ticketCbqd"/></td>'+
						'<td><input type="text" name="TA_DJ_BXPW/XF['+trRows+']" onkeydown="checkNumX()" readonly="readonly" class="input40 ticketCbxf"/></td>'+
						'<td><textarea rows="1" style="width:85%" name="TA_DJ_BXPW/BZ['+trRows+']"></textarea></td>'+
					'</tr>');
				$(".cfRq").bind('change',function(){//ģ����ѯ���ư�keyup�¼�
					$(this).next("input").val($(this).val());
				});
			}else if('shopMd' == mdName){
				var trRows = $(".shopRow").size();
				$("#shopMd").append('<tr class="shopRow">'+
						'<td><input type="checkbox" class="shopCheckbox"></td>'+
						'<td>'+
						'<select name="TA_DJ_BXGW/RC['+trRows+']">'+
						<%calendar.setTime(date);
						for (int i = 0; i < days; i++) {
							String dateStr = sdf.format(calendar.getTime());
							int dt =1+i;
							%>
							  '<option value="<%=dateStr%>" ><%= "��D"+dt+"��"+ dateStr%></option>'+
							<%
									calendar.add(Calendar.DAY_OF_MONTH, 1);
							}
						%>
						'</select>'+
						'<input type="hidden" name="TA_DJ_BXGW/xzqhid['+trRows+']" class="shopXZQH"/>'+
						'<input type="hidden" name="TA_DJ_BXGW/GWDID['+trRows+']"  class="shopId"/>'+
						'</td>'+
						'<td><input type="text" name="TA_DJ_BXGW/GWDMC['+trRows+']" class="shop"  id="'+Random+'"/></td>'+
						'<td><input type="text" name="TA_DJ_BXGW/JDRS['+trRows+']" onkeydown="checkNum()" class="input40 shopTemp4"/></td>'+
						'<td><input type="text" name="TA_DJ_BXGW/GWRS['+trRows+']" onkeydown="checkNum()" class="input40"/></td>'+
						'<td><input type="text" name="TA_DJ_BXGW/RTF['+trRows+']" onkeydown="checkNumX()" class="input40 shopTemp1"/></td>'+
						'<td><input type="text" name="TA_DJ_BXGW/XFE['+trRows+']" onkeydown="checkNumX()" onkeyup="cSn(\'shop\');" onchange="cSnChange(\'shop\')" class="input40 shopTemp3"/></td>'+
						'<td><input type="text" name="TA_DJ_BXGW/GSLC['+trRows+']" onkeydown="checkNumX()" class="input40 shopTemp2"/></td>'+
						'<td>'+
							'���Σ�<input type="text" name="TA_DJ_BXGW/DYTC['+trRows+']" onkeydown="checkNumX()" onkeyup="cSn(\'shop\');" onchange="cSnChange(\'shop\')" class="input40 shopdytc"/><p>'+
							'˾����<input type="text" name="TA_DJ_BXGW/SJTC['+trRows+']" onkeydown="checkNumX()" onkeyup="cSn(\'shop\');" onchange="cSnChange(\'shop\')" class="input40 shopsjtc"/><p>'+
							'ȫ�㣺<input type="text" name="TA_DJ_BXGW/QPTC['+trRows+']" onkeydown="checkNumX()" onkeyup="cSn(\'shop\');" onchange="cSnChange(\'shop\')" class="input40 shopqptc"/><p>'+
							'��˾��<input type="text" name="TA_DJ_BXGW/GSTC['+trRows+']" onkeydown="checkNumX()" onkeyup="cSn(\'shop\');" onchange="cSnChange(\'shop\')" class="input40 shopSnTemp1"/><p>'+
						'</td>'+
						'<td><input type="text" name="TA_DJ_BXGW/YJGS['+trRows+']" onkeydown="checkNumX()" class="input40 shopTemp5"/></td>'+
						'<td><textarea rows="1" style="width:85%" name="TA_DJ_BXGW/BZ['+trRows+']"></textarea></td>'+
					'</tr>');
				$("select").bind('change',function(){//ģ����ѯ���ư�keyup�¼�
					$(this).next("input").val($(this).val());
				});
			}
			
			$('[type=checkbox]').bind('click', function() {//�󶨵�ѡ��ť�¼�
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
			
			$("."+csName).bind('keyup',function(){//ģ����ѯ���ư�keyup�¼�
				$('#itemRow').val($("."+csName).index(this));
				filterChanged(""+csName);
			});
	}
 	
	function frmSubmit(mdName,url,md){//form����ajax�첽 �ύ�������  option (ģ����:��table��id, url:�����url)
		//�ύ��֮ǰ�ȼ���۸�
		$("."+mdName+"Ygcb").each(function(i){
			if(isN(parseFloat($(this).val()))<isN(parseFloat($("."+mdName+"Cbqd:eq("+i+")").val()))){
				$("."+mdName+"Cbxf:eq("+i+")").val(0);
				$("."+mdName+"Cbqd:eq("+i+")").val((isN(parseFloat($(this).val()))).toFixed(2));
			}else{
				$("."+mdName+"Cbxf:eq("+i+")").val((isN(parseFloat($(this).val()))-isN(parseFloat($("."+mdName+"Cbqd:eq("+i+")").val()))).toFixed(2));
			}
	 	});
		/* $("."+tempSnName+"SnTemp1").each(function(i){
			$("."+tempSnName+"Ygcb:eq("+i+")").val((isN(parseFloat($(this).val()))*isN(parseFloat($("."+tempSnName+"SnTemp2:eq("+i+")").val()))*isN(parseFloat($("."+tempSnName+"SnTemp3:eq("+i+")").val()))+isN(parseFloat($("."+tempSnName+"SnTemp4:eq("+i+")").val()))).toFixed(2));
		}); */
		
	    var ygcb=0;
		var cbqd=0;
		var cbxf=0;
		var temp1=0;
		var temp2=0;
		var temp3=0;
		var temp4=0;
		var temp5=0;
		var SnTemp2=0;
		$("." + md + "Ygcb").each(function(){
			ygcb+=isN(parseFloat($(this).val()));
		});
		$("." + md + "Cbqd").each(function(){
			cbqd+=isN(parseFloat($(this).val()));
		});
		$("." + md + "Cbxf").each(function(){
			cbxf+=isN(parseFloat($(this).val()));
		});
		$("." + md + "Temp1").each(function(){
			temp1+=isN(parseFloat($(this).val()));
		});
		$("." + md + "Temp2").each(function(){
			temp2+=isN(parseFloat($(this).val()));
		});
		$("." + md + "Temp3").each(function(){
			temp3+=isN(parseFloat($(this).val()));
		});
		$("." + md + "Temp4").each(function(){
			temp4+=isN(parseFloat($(this).val()));
		});
		$("." + md + "Temp5").each(function(){
			temp5+=isN(parseFloat($(this).val()));
		});
		$("." + md + "SnTemp2").each(function(){
			SnTemp2+=isN(parseFloat($(this).val()));
		});
		$("#" + md + "Row").text($("." + md + "Row").size());
		$("#" + md + "Ygcb").text(ygcb);
		$("#" + md + "Cbqd").text(cbqd);
		$("#" + md + "Cbxf").text(cbxf);
		$("#" + md + "Temp1").text(temp1);
		$("#" + md + "Temp2").text(temp2);
		$("#" + md + "Temp3").text(temp3);
		$("#" + md + "Temp4").text(temp4);
		$("#" + md + "Temp5").text(temp5);
		$("#" + md + "SnTemp2").text(SnTemp2);
		
		var str = null;
		showValues();
		$.ajax({
			dateType:'text',
			url:url,
			data:str,
			cache:false,
			success:function(data){
				var json = eval(data);
				if(json){
					for(var i = 0; i < json.length; i++){
						if(mdName='hotel')
						 	$(".hotelId").eq(json[i].indexNm).val(json[i].id);
						else if(mdName='dinner')
							$(".dinnerId").eq(json[i].indexNm).val(json[i].id);
						else if(mdName='ticket')
							$(".ticketId").eq(json[i].indexNm).val(json[i].id);
						else if(mdName='car')
							$(".carId").eq(json[i].indexNm).val(json[i].id);
						else if(mdName='scenery')
							$(".sceneryId").eq(json[i].indexNm).val(json[i].id);
						else if(mdName='travel')
							$(".travelId").eq(json[i].indexNm).val(json[i].id);
						else if(mdName='shop')
							$(".shopId").eq(json[i].indexNm).val(json[i].id);
						else if(mdName='plus')
							$(".plusId").eq(json[i].indexNm).val(json[i].id);
					}
				}
				divExit();//�ر����ֲ�
			},
			error:function(){
				alert("ϵͳ�쳣�����Ժ����ԣ�");
			}
		});
		function showValues() {//���л�form������
			str = $("#"+mdName).serialize();
		}
		autoCount();
	}
	/*���� checkbox:checked ɾ��ģ���µ�tr option(ɾ����tr��className,�Լ�ɾ���е�checkbox��className)*/
	function delTr(trCsName,checkCsName){
		$("."+checkCsName+":checked").each(function(){
				$("."+trCsName+":eq("+$("."+checkCsName).index(this)+")").remove();
		});
	}

	function xcmx1(nums,mdName,url) {//������ֵ�ı� option(�ڼ���ģ��,ģ������,�����url)
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
	function show(divID) {//�Ŷӻ�����Ϣģ��Ĳ����ظı� 
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
<div id="lable"><span >���α���</span></div>
<div id="jd-table">
<div onclick="show('base');" style="display: none">
   <table class="datas">
	 <tr id="first-tr">
	   <td colspan="4"><span>&nbsp;&nbsp;������Ϣ</span></td>
	 </tr>
   </table>
</div>
<div style="display: none" id="base">
<table class="datas">
	<tr>
	  <td align="right">��·���ƣ�</td>
	  <td><%=rd.getStringByDI("TA_DJ_GROUPs","XLMC",0) %></td>
	  <td align="right">��&nbsp;&nbsp;&nbsp;�ţ�</td>
	  <td><font color="red"><%=groupId %></font></td>
	</tr>
	<tr>
	  <td align="right">����ʱ�䣺</td>
	  <td><%=rd.getStringByDI("TA_DJ_GROUPs","BEGIN_DATE",0) %></td>
	  <td align="right">����ʱ�䣺</td>
	  <td><%=rd.getStringByDI("TA_DJ_GROUPs","END_DATE",0) %></td>
	</tr>
	<tr>
	  <td align="right">���̽�ͨ��</td>
	  <td><%
	  	  String lcjt = rd.getStringByDI("TA_DJ_GROUPs","B_TRAFFIC",0);
	  	  for(int i=0;i<rd.getTableRowsCount("JTGJ");i++) {
	  		  String dmz1 = rd.getStringByDI("JTGJ","dmz",i);
	  		  String dmsm = rd.getStringByDI("JTGJ","dmsm1",i);
	  		  if(lcjt.equals(dmz1))
	  			  out.print(dmsm);
	  	  }
	  	%>
	  	</td>
	  <td align="right">�س̽�ͨ��</td>
	  <td>
		<%
	  	  String hcjt = rd.getStringByDI("TA_DJ_GROUPs","E_TRAFFIC",0);
	  	  for(int i=0;i<rd.getTableRowsCount("JTGJ");i++) {
	  		  String dmz2 = rd.getStringByDI("JTGJ","dmz",i);
	  		  String dmsm = rd.getStringByDI("JTGJ","dmsm1",i);
	  		  if(hcjt.equals(dmz2))
	  			  out.print(dmsm);
	  	  }
	  	%>
	  </td>
	</tr>
	<tr>
	  <td align="right">���̱�ע��</td>
	  <td><%=rd.getStringByDI("TA_DJ_GROUPs","B_REMARK",0) %></td>
	  <td align="right">�س̱�ע��</td>
	  <td><%=rd.getStringByDI("TA_DJ_GROUPs","E_REMARK",0) %></td>
	</tr>
	<tr>
	  <td align="right">��&nbsp;��&nbsp;�磺</td>
	  <td>�Ͼ��ʼҹ���������</td>
	  <td align="right">��&nbsp;&nbsp;&nbsp;����</td>
	  <td><%=rd.getStringByDI("TA_DJ_GROUPs","ts",0) %>��<%=rd.getStringByDI("TA_DJ_GROUPs","NIGHT",0).equals("")?"0":rd.getStringByDI("TA_DJ_GROUPs","NIGHT",0) %>��</td>
	</tr>
	<tr>
	  <td align="right">�ӵ㹺�</td>
	  <td><%if("1".equals(rd.getStringByDI("TA_DJ_GROUPs","gw",0))){ %>��<%} %>�ӵ�	<%if("1".equals(rd.getStringByDI("TA_DJ_GROUPs","jd",0))){ %>��<%} %>���� 
	  
	  <td align="right">�Ƿ��Ա�����</td>
	  <td><%=rd.getStringByDI("TA_DJ_GROUPs","SFZBC",0).equals("1")?"��":"��" %> 
	</tr>
	<tr>
	  <td align="right">��������� </td>
	  <td><%=rd.getStringByDI("TA_DJ_GROUPs","GWDS",0) %> ��</td>
	  <td align="right">�ó����ͣ� </td>
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

<div id="add-table" style="display: none">	
   <table class="datas">
	<tr id="first-tr" onclick="show('xcmx');">
	  <td colspan="8"><span>&nbsp;&nbsp;�г���ϸ����ʾ/���أ�</span></td>
	</tr>
  </table>
</div>
<div style="display: none" id="xcmx">
  <table class="datas">
	<tr id="first-tr" >
	  <td align="center" width="10%">����</td>
	  <td align="center" width="50%">�г���ϸ</td>
	  <td align="center" width="10%">����</td>
	  <td align="center" width="10%">�ͱ�</td>
	  <td align="center" width="10%">ס��</td>
	  <td align="center" width="10%">ס�ޱ�׼</td>
	</tr>
<%
	for(int i=0;i<rd.getTableRowsCount("TA_DJ_LINEDETAI4Gs");i++) {
		String zc = rd.getStringByDI("TA_DJ_LINEDETAI4Gs","BREAKFAST",i);
		String zhenc = rd.getStringByDI("TA_DJ_LINEDETAI4Gs","CMEAL",i);
		String wc = rd.getStringByDI("TA_DJ_LINEDETAI4Gs","SUPPER",i);
%>
	<tr>
	  <td align="center">D<%=rd.getStringByDI("TA_DJ_LINEDETAI4Gs","rq",i) %></td>
	  <td align="center">
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
	  </td>
	  <td align="center"><%=zc.equals("Y")?"���":"" %> <%=zhenc.equals("Y")?"����":"" %> <%=wc.equals("Y")?"���":"" %></td>
	  <td align="center"><%=rd.getStringByDI("TA_DJ_LINEDETAI4Gs","cb",i) %></td>
	  <td align="center"><%=rd.getStringByDI("TA_DJ_LINEDETAI4Gs","ZS",i) %></td>
	  <td align="center">
	  <%
	  String zsbz = rd.getStringByDI("TA_DJ_LINEDETAI4Gs","ZSBZ",i);
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

<div style="display: none">
  <table class="datas">
	<tr id="first-tr" onclick="show('remark');">
	  <td colspan="4"><span>&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;ע����ʾ/���أ�</span></td>
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
  
</div>

<div id="add-table">
<table class="datas">
<tr id="first-tr" style="height:11px;"><td></td></tr>
   <tr>
	   <td>
			<ul>
			<li class="ckeditorli">
					<a class="a" href="###" style="color:#006666;" onclick="xcmx1('1','hotelMd','djAjaxHotelPlanInfo.?groupId=<%=groupId%>');">�Ƶ���ϸ&nbsp;<span>��<font color="red"><var id="hotelRow"><%=rd.getStringByDI("JHHOTEL","SUM",0) %></var></font>����Ϣ��</span><span>���Ƶ�ɱ�ǩ����<font color="red"><var id="hotelCbqd"><%=rd.getStringByDI("JHHOTEL","QDJE",0) %> </var></font>Ԫ��</span><span>���Ƶ�ɱ��ָ���<font color="red"><var id="hotelCbxf"><%=rd.getStringByDI("JHHOTEL","XFJE",0) %> </var></font>Ԫ��</span><span>���Ƶ�Ԥ���ɱ���<font color="red"><var id="hotelYgcb"><%=rd.getStringByDI("JHHOTEL","YGCB",0) %></var></font>Ԫ��</span></a>
				<ul>
					<li>
						<div id="list-table">
						<table id="tbNone" class="datas">
							<tr>
								<td colspan="12" align="left">
									  <button class="addBtn" value="���"  onclick="addTrRow('hotelMd','hotel');">��ӾƵ���ϸ</button>
									  <button class="delBtn" value="ɾ��"  onclick="delTr('hotelRow','hotelCheckbox')">ɾ���Ƶ���ϸ</button>
									  <button class="saveBtn" value="��ʱ����"  onclick="frmSubmit('frmHotel','djFrmInsertBxHotel.?temp=N&groupId=<%=groupId%>','hotel')">��ʱ����</button>
									  <button class="allSaveBtn" value="�����Ա���"  onclick="frmSubmit('frmHotel','djFrmInsertBxHotel.?temp=Y&groupId=<%=groupId%>','hotel')">�����Ա���</button>
								</td>
							</tr>
						</table>
						<form action="" name="frmHotel" id="frmHotel" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
							<table class="datas" id="hotelMd">
								<tr id="first-tr">
									<td width="2%"><input type="checkbox" id="hotelCheckbox"/></td>
									<td width="11%">�ճ�</td>
									<td width="14%">�Ƶ�����</td>
									<td width="6%">��ϵ��</td>
									<td width="7%">��ϵ��ʽ</td>
									<td width="6%">����</td>
									<td width="6%">����</td>
									<td width="5%">����</td>
									<td width="6%">����	</td>
									<td width="6%">Ԥ���ɱ�</td>
									<td width="6%">ǩ��</td>
									<td width="6%">�ָ�</td>
									<td width="6%">����</td>
									<td width="10%">��ע</td>
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
<tr id="first-tr" style="height:11px;"><td></td></tr>
<tr>
	<td>
		<ul>
			<li class="ckeditorli">
				<a class="a" href="###" style="color:#006666;" onclick="xcmx1('2','dinnerMd','djAjaxDinnerPlanInfo.?groupId=<%=groupId%>');">������ϸ&nbsp;<span>��<font color="red"><var id="dinnerRow"><%=rd.getStringByDI("JHCT","SUM",0) %></var></font>����Ϣ��</span><span>�������ɱ�ǩ����<font color="red"><var id="dinnerCbqd"><%=rd.getStringByDI("JHCT","QDJE",0) %> </var></font>Ԫ��</span><span>�������ɱ��ָ���<font color="red"><var id="dinnerCbxf"><%=rd.getStringByDI("JHCT","XFJE",0) %> </var></font>Ԫ��</span><span>������Ԥ���ɱ���<font color="red"><var id="dinnerYgcb"><%=rd.getStringByDI("JHCT","YGCB",0) %></var></font>Ԫ��</span></a>
				<ul>
					<li>
						<div id="list-table">
						<table id="tbNone" class="datas">
							<tr>
								<td colspan="12" align="left">
									  <button class="addBtn" value="���"  onclick="addTrRow('dinnerMd','dinner');">��Ӳ�����ϸ</button>
									  <button class="delBtn" value="ɾ��"  onclick="delTr('dinnerRow','dinnerCheckbox')">ɾ��������ϸ</button>
									  <button class="saveBtn" value="��ʱ����"  onclick="frmSubmit('frmDinner','djFrmInsertBxDinner.?temp=N&groupId=<%=groupId%>','dinner')">��ʱ����</button>
									  <button class="allSaveBtn" value="�����Ա���"  onclick="frmSubmit('frmDinner','djFrmInsertBxDinner.?temp=Y&groupId=<%=groupId%>','dinner')">�����Ա���</button>
								</td>
							</tr>
						</table>
						<form action="" name="frmDinner" id="frmDinner" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
							<table class="datas" id="dinnerMd">
								<tr id="first-tr">
									<td width="2%"><input type="checkbox" id="dinnerCheckbox"/></td>
									<td width="11%">�ճ�</td>
									<td width="14%">�͹�����</td>
									<td width="6%">��ϵ��</td>
									<td width="7%">��ϵ��ʽ</td>
									<td width="6%">����</td>
									<td width="6%">����</td>
									<td width="6%">����</td>
									<td width="6%">Ԥ���ɱ�</td>
									<td width="6%">ǩ��</td>
									<td width="6%">�ָ�</td>
									<td width="10%">��ע</td>
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
<tr id="first-tr" style="height:11px;"><td></td></tr>
<tr>
	<td>
		<ul>
			<li class="ckeditorli">
				<a class="a" href="###" style="color:#006666;" onclick="xcmx1('3','ticketMd','djAjaxTicketPlanInfo.?groupId=<%=groupId%>');">Ʊ����ϸ&nbsp;<span>��<font color="red"><var id="ticketRow"><%=rd.getStringByDI("JHPW","SUM",0) %></var></font>����Ϣ��</span><span>��Ʊ��ɱ�ǩ����<font color="red"><var id="ticketCbqd"><%=rd.getStringByDI("JHPW","QD",0) %> </var></font>Ԫ��</span><span>��Ʊ��ɱ��ָ���<font color="red"><var id="ticketCbxf"><%=rd.getStringByDI("JHPW","XF",0) %> </var></font>Ԫ��</span><span>��Ʊ��Ԥ���ɱ���<font color="red"><var id="ticketYgcb"><%=rd.getStringByDI("JHPW","YGCB",0) %></var></font>Ԫ��</span></a>
				<ul>
					<li>
						<div id="list-table">
						<table id="tbNone" class="datas">
							<tr>
								<td colspan="12" align="left">
								  <div id="btn-span">
									  <button class="addBtn" value="���"  onclick="addTrRow('ticketMd','ticket');">���Ʊ����ϸ</button>
									  <button class="delBtn" value="ɾ��"  onclick="delTr('ticketRow','ticketCheckbox')">ɾ��Ʊ����ϸ</button>
									  <button class="saveBtn" value="��ʱ����"  onclick="frmSubmit('frmTicket','djFrmInsertBxTicket.?temp=N&groupId=<%=groupId%>','ticket')">��ʱ����</button>
									  <button class="allSaveBtn" value="�����Ա���"  onclick="frmSubmit('frmTicket','djFrmInsertBxTicket.?temp=Y&groupId=<%=groupId%>','ticket')">�����Ա���</button>
								  </div>
								</td>
							</tr>
						</table>
						<form action="" name="frmTicket" id="frmTicket" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
							<table class="datas" id="ticketMd">
								<tr id="first-tr">
									<td width="2%"><input type="checkbox" id="ticketCheckbox"/></td>
									<td width="10%">��������</td>
									<td width="9%">����ʱ��</td>
									<td width="6%">����</td>
									<td width="6%">Ʊ��˾</td>
									<td width="10%">��ϵ��Ϣ</td>
									<td width="7%">���೵��</td>
									<td width="10%">��ֹվ</td>
									<td width="5%">����</td>
									<td width="5%">����</td>
									<td width="5%">������</td>
									<td width="7%">Ԥ���ɱ�</td>
									<td width="5%">ǩ��</td>
									<td width="5%">�ָ�</td>
									<td width="10%">��ע</td>
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

<tr id="first-tr" style="height:11px;"><td></td></tr>
<tr>
	<td>
		<ul>
			<li class="ckeditorli">
				<a class="a" href="###" style="color:#006666;" onclick="xcmx1('4','carMd','djAjaxCarPlanInfo.?dmsm/cllx=13&groupId=<%=groupId%>');">������ϸ&nbsp;<span>��<font color="red"><var id="carRow"><%=rd.getStringByDI("JHCL","SUM",0) %></var></font>����Ϣ��</span><span>�������ɱ�ǩ����<font color="red"><var id="carCbqd"><%=rd.getStringByDI("JHCL","QDJE",0) %> </var></font>Ԫ��</span><span>�������ɱ��ָ���<font color="red"><var id="carCbxf"><%=rd.getStringByDI("JHCL","XFJE",0) %> </var></font>Ԫ��</span><span>������Ԥ���ɱ���<font color="red"><var id="carYgcb"><%=rd.getStringByDI("JHCL","JG",0) %></var></font>Ԫ��</span></a>
				<ul>
					<li>
						<div id="list-table">
							<table id="tbNone" class="datas">
							<tr>
								<td colspan="12" align="left">
								  <div id="btn-span">
									  <button class="addBtn" value="���"  onclick="addTrRow('carMd','car');">��ӳ�����ϸ</button>
									  <button class="delBtn" value="ɾ��"  onclick="delTr('carRow','carCheckbox')">ɾ��������ϸ</button>
									  <button class="saveBtn" value="��ʱ����"  onclick="frmSubmit('frmCar','djFrmInsertBxCar.?temp=N&groupId=<%=groupId%>','car')">��ʱ����</button>
									  <button class="allSaveBtn" value="�����Ա���"  onclick="frmSubmit('frmCar','djFrmInsertBxCar.?temp=Y&groupId=<%=groupId%>','car')">�����Ա���</button>
								  </div>
								</td>
							</tr>
						</table>
						<form action="" name="frmCar" id="frmCar" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
						  <table class="datas" id="carMd">
							<tr id="first-tr">
							  <td width="3%"><input type="checkbox" id="carCheckbox"></td>
							  <td width="8%">��ʼʱ��</td>
							  <td width="8%">����ʱ��</td>
							  <td width="10%">����</td>
							  <td width="5%">����</td>
							  <td width="5%">˾��</td>
							  <td width="7%">��ϵ��ʽ</td>
							  <td width="5%">���ƺ�</td>
							  <td width="5%">Ԥ���ɱ�</td>
							  <td width="5%">ǩ��</td>
							  <td width="5%">�ָ�</td>
							  <td width="34%">��ע</td>
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

<tr id="first-tr" style="height:11px;"><td></td></tr>

<tr>
  <td>
	<ul>
	  <li class="ckeditorli">
		<a class="a" href="###" style="color:#006666;" onclick="xcmx1('5','sceneryMd','djAjaxSceneryPlanInfo.?groupId=<%=groupId%>');">������ϸ&nbsp;<span>��<font color="red"><var id="sceneryRow"><%=rd.getStringByDI("JHJD","SUM",0) %></var></font>����Ϣ��</span><span>������ɱ�ǩ����<font color="red"><var id="sceneryCbqd"><%=rd.getStringByDI("JHJD","QDJE",0) %> </var></font>Ԫ��</span><span>������ɱ��ָ���<font color="red"><var id="sceneryCbxf"><%=rd.getStringByDI("JHJD","XFJE",0) %> </var></font>Ԫ��</span><span>������Ԥ���ɱ���<font color="red"><var id="sceneryYgcb"><%=rd.getStringByDI("JHJD","TGCB",0) %></var></font>Ԫ��</span></a>
		<ul>
		  <li>
			<div id="list-table">
			  <table id="tbNone" class="datas">
				<tr>
				  <td colspan="12" align="left">
					<button class="addBtn" value="���" onclick="addTrRow('sceneryMd','scenery');">��Ӿ�����ϸ</button>
					<button class="delBtn" value="ɾ��" onclick="delTr('sceneryRow','sceneryCheckbox');">ɾ��������ϸ</button>
					<button class="saveBtn" value="��ʱ����" onclick="frmSubmit('frmScenery','djFrmInsertBxScenery.?temp=N&groupId=<%=groupId%>','scenery');">��ʱ����</button>
					<button class="allSaveBtn" value="�����Ա���" onclick="frmSubmit('frmScenery','djFrmInsertBxScenery.?temp=Y&groupId=<%=groupId%>','scenery');">�����Ա���</button>
				  </td>
				</tr>
			  </table>
		  		<form action="" name="frmScenery" id="frmScenery" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
				  <table class="datas" id="sceneryMd">
					<tr id="first-tr">
				  	<td width="2%"><input type="checkbox" id="sceneryCheckbox"></td>
					<td width="10%">�ճ�</td>
				  	<td width="10%">��������</td>
				  	<td width="8%">��ϵ��</td>
				  	<td width="8%">��ϵ��ʽ</td>
				  	<td width="5%">����</td>
				  	<td width="5%">����</td>
				  	<td width="8%">Ԥ���ɱ�</td>
				  	<td width="5%">ǩ��</td>
				  	<td width="5%">�ָ�</td>
				  	<td width="35%">��ע</td>
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
<tr id="first-tr" style="height:11px;"><td></td></tr>
<tr>
  <td>
	<ul>
	  <li class="ckeditorli">
		<a class="a" href="###" style="color:#006666;" onclick="xcmx1('6','travelMd','djAjaxTravelPlanInfo.?groupId=<%=groupId%>');">�ؽ���ϸ&nbsp;<span>��<font color="red"><var id="travelRow"><%=rd.getStringByDI("JHDJ","SUM",0) %></var></font>����Ϣ��</span><span>���ؽӳɱ�ǩ����<font color="red"><var id="travelCbqd"><%=rd.getStringByDI("JHDJ","QDJE",0) %> </var></font>Ԫ��</span><span>���ؽӳɱ��ָ���<font color="red"><var id="travelCbxf"><%=rd.getStringByDI("JHDJ","XFJE",0) %> </var></font>Ԫ��</span><span>���ؽ�Ԥ���ɱ���<font color="red"><var id="travelYgcb"><%=rd.getStringByDI("JHDJ","YFZK",0) %></var></font>Ԫ��</span></a>
		<ul>
		  <li>
			<div id="list-table">
		  	  <table id="tbNone" class="datas">
				<tr>
				  <td colspan="12" align="left">
					<button class="addBtn" value="���"  onclick="addTrRow('travelMd','travel');">��ӵؽ���ϸ</button>
					<button class="delBtn" value="ɾ��"  onclick="delTr('travelRow','travelCheckbox')">ɾ���ؽ���ϸ</button>
					<button class="saveBtn" value="��ʱ����"  onclick="frmSubmit('frmTravel','djFrmInsertBxTravel.?temp=N&groupId=<%=groupId%>','travel')">��ʱ����</button>
					<button class="allSaveBtn" value="�����Ա���"  onclick="frmSubmit('frmTravel','djFrmInsertBxTravel.?temp=Y&groupId=<%=groupId%>','travel')">�����Ա���</button>
				  </td>
				</tr>
			  </table>
	  		  <form action="" name="frmTravel" id="frmTravel" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
			  	<table class="datas" id="travelMd">
				  <tr id="first-tr">
				    <td width="2%"><input type="checkbox" id="travelCheckbox"></td>
				    <td width="10%">��ʼʱ��</td>
				    <td width="10%">����ʱ��</td>
				    <td width="10%">�ؽ�����</td>
				    <td width="8%">��ϵ��</td>
				    <td width="10%">��ϵ�绰</td>
				    <td width="5%">����</td>
				    <td width="8%">Ԥ���ɱ�</td>
				    <td width="8%">ǩ��</td>
				    <td width="8%">�ָ�</td>
				    <td width="25%">��ע</td>
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
<tr id="first-tr" style="height:11px;"><td></td></tr>
<tr>
	<td>
		<ul>
			<li class="ckeditorli">
				<a class="a" href="###" style="color:#006666;" onclick="xcmx1('7','shopMd','djAjaxShopPlanInfo.?groupId=<%=groupId%>');">������ϸ&nbsp;<span>��<font color="red"><var id="shopRow"><%=rd.getStringByDI("JHGW","SUM",0) %></var></font>����Ϣ��</span><span>����������˴Σ�<font color="red"><var id="shopTemp4"><%=rd.getStringByDI("JHGW","JDRS",0) %> </var></font>�˴Ρ�</span><span>���������ѽ�<font color="red"><var id="shopTemp3"><%=rd.getStringByDI("JHGW","XFE",0) %> </var></font>Ԫ��</span><span>������Ӧ����˾��<font color="red"><var id="shopTemp5"><%=rd.getStringByDI("JHGW","YJGS",0) %> </var></font>Ԫ��</span><span>��������ͷ�ѣ�<font color="red"><var id="shopTemp1"><%=rd.getStringByDI("JHGW","RTF",0) %> </var></font>Ԫ��</span><span>�����﹫˾���棺<font color="red"><var id="shopTemp2"><%=rd.getStringByDI("JHGW","GSLC",0) %> </var></font>Ԫ��</span></a>
				<ul>
					<li>
						<div id="list-table">
				  		  <table id="tbNone" class="datas">
							<tr>
							  <td colspan="12" align="left">
								<button class="addBtn" value="���"  onclick="addTrRow('shopMd','shop');">��ӹ�����ϸ</button>
								<button class="delBtn" value="ɾ��"  onclick="delTr('shopRow','shopCheckbox');">ɾ��������ϸ</button>
								<button class="saveBtn" value="��ʱ����"  onclick="frmSubmit('frmShop','djFrmInsertBxShop.?temp=N&groupId=<%=groupId%>','shop');">��ʱ����</button>
								<button class="allSaveBtn" value="�����Ա���"  onclick="frmSubmit('frmShop','djFrmInsertBxShop.?temp=Y&groupId=<%=groupId%>','shop');">�����Ա���</button>
							  </td>
							</tr>
						  </table>
					  		<form action="" name="frmShop" id="frmShop" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
							  <table class="datas" id="shopMd">
								<tr id="first-tr">
								  <td width="3%"><input type="checkbox" id="shopCheckbox"></td>
								  <td width="12%">�ճ�</td>
								  <td width="15%">���������</td>
								  <td width="6%">��������</td>
								  <td width="6%">��������</td>
								  <td width="6%">����ͷ��</td>
								  <td width="6%">���Ѷ�</td>
								  <td width="6%">��˾����</td>
								  <td width="12%">���</td>
								  <td width="6%">Ӧ����˾</td>
								  <td width="22%">��ע</td>
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
<tr id="first-tr" style="height:11px;"><td></td></tr>
<tr>
  <td>
	<ul>
	  <li class="ckeditorli" style="background: url(<%=request.getContextPath()%>/images/header-bg.gif) repeat">
		<a class="a" href="###" style="color:#006666;" onclick="xcmx1('8','plusMd','djAjaxPlusPlanInfo.?groupId=<%=groupId%>');">�ӵ���ϸ&nbsp;<span>��<font color="red"><var id="plusRow"><%=rd.getStringByDI("JHJIAD","SUM",0) %></var></font>����Ϣ��</span><span>���ӵ�ɱ�ǩ����<font color="red"><%=rd.getStringByDI("JHJIAD","CBQD",0) %></font>Ԫ��</span><span>���ӵ�ɱ��ָ���<font color="red"><%=rd.getStringByDI("JHJIAD","CBXF",0) %></font>Ԫ��</span><span>���ӵ�Ԥ���ɱ���<font color="red"><%=rd.getStringByDI("JHJIAD","CBJE",0) %></font>Ԫ��</span><span>���ӵ������ϼƣ�<font color="red"><%=rd.getStringByDI("JHJIAD","RS",0) %></font>�ˡ�</span><span>���ӵ�����ϼƣ�<font color="red"><var id="plusTemp2"><%=rd.getStringByDI("JHJIAD","LR",0) %></var></font>Ԫ��</span><span>���ӵ�Ӧ����˾��<font color="red"><var id="plusTemp3"><%=rd.getStringByDI("JHJIAD","YJGS",0) %></var></font>Ԫ��</span></a>
		<ul>
	  	  <li>
	  		<div id="list-table">
	  		  <table id="tbNone" class="datas">
				<tr>
				  <td colspan="12" align="left">
					<button class="addBtn" value="���"  onclick="addTrRow('plusMd','plus');">��Ӽӵ���ϸ</button>
					<button class="delBtn" value="ɾ��"  onclick="delTr('plusRow','plusCheckbox');">ɾ���ӵ���ϸ</button>
					<button class="saveBtn" value="��ʱ����"  onclick="frmSubmit('frmPlus','djFrmInsertBxPlus.?temp=N&groupId=<%=groupId%>','plus');">��ʱ����</button>
					<button class="allSaveBtn" value="�����Ա���"  onclick="frmSubmit('frmPlus','djFrmInsertBxPlus.?temp=Y&groupId=<%=groupId%>','plus');">�����Ա���</button>
				  </td>
				</tr>
			  </table>
		  		<form action="" name="frmPlus" id="frmPlus" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
				  <table class="datas" id="plusMd">
					<tr id="first-tr">
					  <td width="3%"><input type="checkbox" id="plusCheckbox"></td>
					  <td width="15%">��������</td>
					  <td width="6%">���Ƽ�</td>
					  <td width="6%">�ɱ���</td>
					  <td width="6%">����</td>
					  <td width="6%">�ɱ����</td>
					  <td width="6%">�ɱ�ǩ��</td>
					  <td width="6%">�ɱ��ָ�</td>
					  <td width="6%">�ӵ�����</td>
					  <td width="12%">�����</td>
					  <td width="6%">Ӧ����˾</td>
					  <td width="16%">��ע</td>
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
<tr id="first-tr" style="height:11px;"><td></td></tr>
<tr>
  <td>
	<ul>
	  <li class="ckeditorli">
		<a class="a" href="###" style="color:#006666;" onclick="xcmx1('9','otherMd','djAjaxOtherPlanInfo.?groupId=<%=groupId%>');">������ϸ&nbsp;<span>��<font color="red"><var id="otherRow"><%=rd.getStringByDI("JHQT","SUM",0) %></var></font>����Ϣ��</span><span>�������ɱ�ǩ����<font color="red"><var id="otherCbqd"><%=rd.getStringByDI("JHQT","QDJE",0) %> </var></font>Ԫ��</span><span>�������ɱ��ָ���<font color="red"><var id="otherCbxf"><%=rd.getStringByDI("JHQT","XFJE",0) %> </var></font>Ԫ��</span><span>������Ԥ���ɱ���<font color="red"><var id="otherYgcb"><%=rd.getStringByDI("JHQT","YGCB",0) %></var></font>Ԫ��</span></a>
	  	<ul>
	  	  <li>
			<div id="list-table">
			  <table id="tbNone" class="datas">
				<tr>
				  <td colspan="12" align="left">
					<button class="addBtn" value="���"  onclick="addTrRow('otherMd','other');">���������ϸ</button>
					<button class="delBtn" value="ɾ��"  onclick="delTr('otherRow','otherCheckbox')">ɾ��������ϸ</button>
					<button class="saveBtn" value="��ʱ����"  onclick="frmSubmit('frmOther','djFrmInsertBxOther.?temp=N&groupId=<%=groupId%>','other')">��ʱ����</button>
					<button class="allSaveBtn" value="�����Ա���"  onclick="frmSubmit('frmOther','djFrmInsertBxOther.?temp=Y&groupId=<%=groupId%>','other');">�����Ա���</button>
				  </td>
				</tr>
			  </table>
		  	  <form action="" name="frmOther" id="frmOther" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
				<table class="datas" id="otherMd">
				  <tr id="first-tr">
					<td width="2%"><input type="checkbox" id="otherCheckbox"></td>
					<td width="15%">��ϸ����</td>
					<td width="10%">����</td>
					<td width="10%">Ԥ���ɱ�</td>
					<td width="10%">ǩ��</td>
					<td width="10%">�ָ�</td>
					<td width="40%">��ע</td>
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
<tr id="first-tr" style="height:11px;"><td></td></tr>
<tr>
  <td>
	<ul>
	  <li class="ckeditorli">
		<a class="a" href="###" style="color:#006666;" onclick="xcmx1('10','guideMd','djAjaxGuidePlanInfo.?groupId=<%=groupId%>');">������ϸ&nbsp;<span>��<font color="red"><var id="guideRow"><%=rd.getStringByDI("JHDY","SUM",0) %></var></font>����Ϣ��</span><span>��������ȡ�ſ<font color="red"><var id="guideTemp1"><%=rd.getStringByDI("JHDY","DYLK",0) %></var></font>Ԫ��</span><span>�����η�����ã�<font color="red"><var id="guideTemp2"><%=rd.getStringByDI("JHDY","DFF",0) %> </var></font>Ԫ��</span></a>
		<ul>
		  <li>
		  	<div id="list-table">
		  	  <table id="tbNone" class="datas">
				<tr>
				  <td colspan="12" align="left">
					<button class="addBtn" value="���"  onclick="addTrRow('guideMd','guide');" style="display: none">��ӵ�����ϸ</button>
					<button class="delBtn" value="ɾ��"  onclick="delTr('guideRow','guideCheckbox')" style="display: none">ɾ��������ϸ</button>
					<button class="saveBtn" value="��ʱ����"  onclick="frmSubmit('frmGuide','djFrmInsertBxGuide.?temp=N&groupId=<%=groupId%>','guide')">��ʱ����</button>
					<button class="allSaveBtn" value="�����Ա���"  onclick="frmSubmit('frmGuide','djFrmInsertBxGuide.?temp=Y&groupId=<%=groupId%>','guide')">�����Ա���</button>
				  </td>
				</tr>
			  </table>
	  		  <form action="" name="frmGuide" id="frmGuide" enctype="application/x-www-form-urlencoded;charset=GBK;" method="post" autocomplete="off">
			  	<table class="datas" id="guideMd">
				  <tr id="first-tr">
				    <td width="2%"><input type="checkbox" id="guideCheckbox"></td>
				    <td width="10%">����ʱ��</td>
				    <td width="10%">����ʱ��</td>
				    <td width="10%">��������</td>
				    <td width="10%">�����ֻ�</td>
				    <td width="10%">����֤��</td>
				    <td width="5%">���ſ�</td>
				    <td width="5%">������</td>
				    <td width="35%">��ע</td>
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
<tr id="first-tr" style="height:11px;"><td></td></tr>
</table>
<%
int rows=rd.getTableRowsCount("SPYJ");
if(rows > 0){
%>
<table class="datas">
  <tr id="first-tr">
	<td  colspan="2"><span>�������</span></td>
  </tr>
  <tr>
	<td  colspan="2">
	<%
	for(int i=0;i<rows;i++){
	%>
	  <b><%=rd.getStringByDI("SPYJ","USERNAME",i)%>:</b>
	  <%=rd.getStringByDI("SPYJ","SPYJ",i)%>;<p>
	<%
	}
	%>
	</td>
  </tr>
</table>
<%
}
%>
</div>
<script type="text/javascript">
$(function(){
	autoCount();
});
var xfhj=0;
var cbzc=0;
var yjcwxjhj=0;
var gwlrhj=0;
var jdlrhj=0;
var tdlr=0;
var zmml=0;
var qdhj=0;

function isN(temp){
	if(isNaN(temp)){
		return 0;
	}else{
		return temp;
	}
}

function autoCount(){
	/*�ָ��ϼ�=�Ƶ��ָ�+�����ָ�+Ʊ���ָ�+�����ָ�+�����ָ�*/
	xfhj=isN(parseFloat($("#ticketCbxf").text()))+isN(parseFloat($("#carCbxf").text()))+isN(parseFloat($("#hotelCbxf").text()))+
	isN(parseFloat($("#dinnerCbxf").text()))+isN(parseFloat($("#sceneryCbxf").text()))+isN(parseFloat($("#travelCbxf").text()))+
	/* isN(parseFloat($("#plusCbxf").text()))+ */isN(parseFloat($("#otherCbxf").text()));

	/*�ɱ�֧��=�Ƶ�ϼ�+�����ϼ�+Ʊ��ϼ�+�����ϼ�+����ϼ�+�ؽӺϼ�+�����ϼ�+������*/
	cbzc=isN(parseFloat($("#ticketYgcb").text()))+isN(parseFloat($("#carYgcb").text()))+isN(parseFloat($("#hotelYgcb").text()))+
	isN(parseFloat($("#dinnerYgcb").text()))+isN(parseFloat($("#sceneryYgcb").text()))+isN(parseFloat($("#travelYgcb").text()))+
	/* isN(parseFloat($("#plusYgcb").text()))+ */isN(parseFloat($("#otherYgcb").text()))+isN(parseFloat($("#guideTemp2").text()));

	/*Ӧ�������ֽ�ϼ� = ������� - �ָ��ϼ� + ����Ӧ����˾ + �ӵ�Ӧ����˾*/
	yjcwxjhj=isN(parseFloat($("#guideTemp1").text()))-xfhj+isN(parseFloat($("#shopTemp5").text()))+isN(parseFloat($("#plusTemp3").text()));

	/*��������ϼ�=��ͷ��+����+Ӧ���ֽ�*/
	gwlrhj=isN(parseFloat($("#shopTemp1").text()))+isN(parseFloat($("#shopTemp2").text()))+isN(parseFloat($("#shopTemp5").text()));

	/*�ӵ�����ϼ�=��ͷ��+����*/
	jdlrhj=isN(parseFloat($("#plusTemp2").text()));
	
	/*�Ŷ����� = Ӫ�� - ����֧���ϼ�  + ��������ϼ�  + �ӵ�����ϼ� */
	tdlr=isN(parseFloat($("#groupTkzj").val()))-cbzc+gwlrhj+jdlrhj;

	/*����ë��=Ӫ��-�ɱ��ϼ�*/
	zmml = isN(parseFloat($("#groupTkzj").val()))-cbzc;

	/*ǩ���ϼ�=�ɱ�֧��-�ָ��ϼ�-������*/
	qdhj=cbzc-xfhj-isN(parseFloat($("#guideTemp2").text()));
	//alert(" ��������ϼ�:"+gwlrhj+"�ӵ�����ϼ�:"+jdlrhj+" �ָ��ϼ�:"+xfhj+" ǩ���ϼ�:"+qdhj+" �ɱ�֧���ϼ�:"+cbzc+" Ӧ�������ֽ�ϼ�:"+yjcwxjhj+" �Ŷ�����:"+tdlr+" ����ë��:"+zmml);
	$("#gwlrhj").text(gwlrhj);$("#jdlrhj").text(jdlrhj);$("#xfhj").text(xfhj);$("#qdhj").text(qdhj);$("#cbzc").text(cbzc);$("#yjcwxjhj").text(yjcwxjhj);$("#tdlr").text(tdlr);$("#zmml").text(zmml);
}

function jdSubmit(){
	if(confirm("��ȷ��Ҫ�ύ��")){
	$("[name='TA_TDBXZJXXB/TID']").val('<%=groupId%>');
	
	//$("[name=TA_TDBXZJXXB/SXFZJ]").val();
	$("[name='TA_TDBXZJXXB/XFPWZJ']").val(isN(parseFloat($("#ticketCbxf").text())));
	$("[name='TA_TDBXZJXXB/QDPWZJ']").val(isN(parseFloat($("#ticketCbqd").text())));
	$("[name='TA_TDBXZJXXB/PWZJ']").val(isN(parseFloat($("#ticketYgcb").text())));
	
	$("[name='TA_TDBXZJXXB/XFCLZJ']").val(isN(parseFloat($("#carCbxf").text())));
	$("[name='TA_TDBXZJXXB/QDCLZJ']").val(isN(parseFloat($("#carCbqd").text())));
	$("[name='TA_TDBXZJXXB/CLZJ']").val(isN(parseFloat($("#carYgcb").text())));
	
	$("[name='TA_TDBXZJXXB/XFZSZJ']").val(isN(parseFloat($("#hotelCbxf").text())));
	$("[name='TA_TDBXZJXXB/QDZSZJ']").val(isN(parseFloat($("#hotelCbqd").text())));
	$("[name='TA_TDBXZJXXB/ZSZJ']").val(isN(parseFloat($("#hotelYgcb").text())));
	
	$("[name='TA_TDBXZJXXB/XFCTZJ']").val(isN(parseFloat($("#dinnerCbxf").text())));
	$("[name='TA_TDBXZJXXB/QDCTZJ']").val(isN(parseFloat($("#dinnerCbqd").text())));
	$("[name='TA_TDBXZJXXB/CTZJ']").val(isN(parseFloat($("#dinnerYgcb").text())));
	
	$("[name='TA_TDBXZJXXB/XFJDZJ']").val(isN(parseFloat($("#sceneryCbxf").text())));
	$("[name='TA_TDBXZJXXB/QDJDZJ']").val(isN(parseFloat($("#sceneryCbqd").text())));
	$("[name='TA_TDBXZJXXB/JDZJ']").val(isN(parseFloat($("#sceneryYgcb").text())));
	
	$("[name='TA_TDBXZJXXB/XFDJZJ']").val(isN(parseFloat($("#travelCbxf").text())));
	$("[name='TA_TDBXZJXXB/QDDJZJ']").val(isN(parseFloat($("#travelCbqd").text())));
	$("[name='TA_TDBXZJXXB/DJZJ']").val(isN(parseFloat($("#travelYgcb").text())));
	
	$("[name='TA_TDBXZJXXB/GWJDRSHJ']").val(isN(parseFloat($("#shopTemp3").text())));
	$("[name='TA_TDBXZJXXB/GWXFEHJ']").val(isN(parseFloat($("#shopTemp4").text())));
	$("[name='TA_TDBXZJXXB/GWYJGSHJ']").val(isN(parseFloat($("#shopTemp5").text())));
	
	$("[name='TA_TDBXZJXXB/JDRSHJ']").val(isN(parseFloat($("#plusTemp4").text())));
	$("[name='TA_TDBXZJXXB/JDJL']").val(isN(parseFloat($("#plusTemp2").text())));
	$("[name='TA_TDBXZJXXB/JDYJGSHJ']").val(isN(parseFloat($("#plusTemp3").text())));
	$("[name='TA_TDBXZJXXB/JDXFHJ']").val(isN(parseFloat($("#plusCbxf").text())));
	$("[name='TA_TDBXZJXXB/JDQDHJ']").val(isN(parseFloat($("#plusCbqd").text())));
	$("[name='TA_TDBXZJXXB/JDCBHJ']").val(isN(parseFloat($("#plusYgcb").text())));
	
	$("[name='TA_TDBXZJXXB/XFQTZJ']").val(isN(parseFloat($("#otherCbxf").text())));
	$("[name='TA_TDBXZJXXB/QDQTZJ']").val(isN(parseFloat($("#otherCbqd").text())));
	$("[name='TA_TDBXZJXXB/QTZJ']").val(isN(parseFloat($("#otherYgcb").text())));
	
	$("[name='TA_TDBXZJXXB/DFFZJ']").val(isN(parseFloat($("#guideTemp2").text())));
	$("[name='TA_TDBXZJXXB/LTKZJ']").val(isN(parseFloat($("#guideTemp1").text())));
	
	$("[name='TA_TDBXZJXXB/TDLR']").val(tdlr);
	$("[name='TA_TDBXZJXXB/YJCWXJHJ']").val(yjcwxjhj);
	$("[name='TA_TDBXZJXXB/GWLRHJ']").val(gwlrhj);
	$("[name='TA_TDBXZJXXB/JDLRHJ']").val(jdlrhj);
	$("[name='TA_TDBXZJXXB/ZCFYHJ']").val(cbzc);
	$("[name='TA_TDBXZJXXB/ZCXFHJ']").val(xfhj);
	$("[name='TA_TDBXZJXXB/ZCQDHJ']").val(qdhj);
	$("[name='TA_TDBXZJXXB/ZMML']").val(zmml);
	 
	document.planSubmit.submit();
	}
}
</script>
<div>
<form name="planSubmit" action="djInitShBxList.?pageNO=1&pageSize=10&definitionid=djbxsh&TID=<%=groupId%>" method="post">
  <table class="datas">
    <tr>
    	<td>
    	  <span>���Ŷ�����<font color="red"><var id="tdlr"></var></font>Ԫ��</span>
    	  <span>��Ӧ�������ֽ�ϼƣ�<font color="red"><var id="yjcwxjhj"></var></font>Ԫ��</span>
    	  <span>����������ϼƣ�<font color="red"><var id="gwlrhj"></var></font>Ԫ��</span>
    	  <span>���ӵ�����ϼƣ�<font color="red"><var id="jdlrhj"></var></font>Ԫ��</span>
    	</td>
    </tr>
    <tr>
    	<td>
    	  <span>������ë����<font color="red"><var id="zmml"></var></font>Ԫ��</span>
    	  <span>���ɱ�֧���ϼƣ�<font color="red"><var id="cbzc"></var></font>Ԫ��</span>
    	  <span>���ָ��ϼƣ�<font color="red"><var id="xfhj"></var></font>Ԫ��</span>
    	  <span>��ǩ���ϼƣ�<font color="red"><var id="qdhj"></var></font>Ԫ��</span>
    	</td>
    </tr>
  	<tr style="display: none">
	  <td align="center">
 	    <br><input type="button"  id="tbNone" value="�Զ�����" onclick="autoCount();"/><br><br>
	  </td>
  	</tr>
	<tr>
	  <td align="center">
	    <br><input type="button"  id="tbNone" value="�ύ" onclick="jdSubmit();"/><input type="button" value="����" onclick="window.history.go(-1)"/><br><br>
	    
	    <input type="hidden" name="TA_TDBXZJXXB/TID" />
	  
 <input type="hidden" name="TA_TDBXZJXXB/XFPWZJ" />
 <input type="hidden" name="TA_TDBXZJXXB/QDPWZJ" />
 <input type="hidden" name="TA_TDBXZJXXB/PWZJ" />
 
 <input type="hidden" name="TA_TDBXZJXXB/QDCLZJ" />
 <input type="hidden" name="TA_TDBXZJXXB/XFCLZJ" />
 <input type="hidden" name="TA_TDBXZJXXB/CLZJ" />
 
 <input type="hidden" name="TA_TDBXZJXXB/QDZSZJ" />
 <input type="hidden" name="TA_TDBXZJXXB/XFZSZJ" />
 <input type="hidden" name="TA_TDBXZJXXB/ZSZJ" />
 
 <input type="hidden" name="TA_TDBXZJXXB/XFCTZJ" />
 <input type="hidden" name="TA_TDBXZJXXB/QDCTZJ" />
 <input type="hidden" name="TA_TDBXZJXXB/CTZJ" />
 
 <input type="hidden" name="TA_TDBXZJXXB/XFJDZJ" />
 <input type="hidden" name="TA_TDBXZJXXB/QDJDZJ" />
 <input type="hidden" name="TA_TDBXZJXXB/JDZJ" />
 
 <input type="hidden" name="TA_TDBXZJXXB/XFDJZJ" />
 <input type="hidden" name="TA_TDBXZJXXB/QDDJZJ" />
 <input type="hidden" name="TA_TDBXZJXXB/DJZJ" />
 
 <input type="hidden" name="TA_TDBXZJXXB/GWJDRSHJ" />
 <input type="hidden" name="TA_TDBXZJXXB/GWXFEHJ" />
 <input type="hidden" name="TA_TDBXZJXXB/GWYJGSHJ" />
 
 <input type="hidden" name="TA_TDBXZJXXB/JDRSHJ" />
 <input type="hidden" name="TA_TDBXZJXXB/JDJL" />
 <input type="hidden" name="TA_TDBXZJXXB/JDYJGSHJ" />
 <input type="hidden" name="TA_TDBXZJXXB/JDXFHJ" />
 <input type="hidden" name="TA_TDBXZJXXB/JDQDHJ" />
 <input type="hidden" name="TA_TDBXZJXXB/JDCBHJ" />
 
 <input type="hidden" name="TA_TDBXZJXXB/XFQTZJ" />
 <input type="hidden" name="TA_TDBXZJXXB/QDQTZJ" />
 <input type="hidden" name="TA_TDBXZJXXB/QTZJ" />
 
 <input type="hidden" name="TA_TDBXZJXXB/DFFZJ" />
 <input type="hidden" name="TA_TDBXZJXXB/LTKZJ" />
 
 <input type="hidden" name="TA_TDBXZJXXB/TDLR" />
 <input type="hidden" name="TA_TDBXZJXXB/YJCWXJHJ" />
 <input type="hidden" name="TA_TDBXZJXXB/GWLRHJ" />
 <input type="hidden" name="TA_TDBXZJXXB/JDLRHJ" />
 <input type="hidden" name="TA_TDBXZJXXB/ZCFYHJ" />
 <input type="hidden" name="TA_TDBXZJXXB/ZCXFHJ" />
 <input type="hidden" name="TA_TDBXZJXXB/ZCQDHJ" />
 <input type="hidden" name="TA_TDBXZJXXB/ZMML" />
 
	  </td>
	</tr>
  </table>
</form>
</div>
</div>
</div>
<div>
<!-- ����ֵ������ -->
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
 <input type="hidden" id="groupTkzj" value="<%=rd.getString("TA_DJ_GROUPs","TKZJ",0)%>" />
</div>
<div id="divbg"></div>
<div class="divxs" style="display:none">
	<ul>
		<li><span>�������ڲ����У����Ե� <a href="#" class="close">�ر�</a></span></li>
		<li><span><img src="<%=request.getContextPath()%>/images/loading.gif"></span></li>
	</ul>
</div>
</body>
</html>