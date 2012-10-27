<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
String groupID = rd.getStringByDI("TA_ZT_JHJIADs","TID",0).equals("")?rd.getString("tid"):rd.getStringByDI("TA_ZT_JHJIADs","TID",0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<script type="text/javascript">

var rows;
jQuery(document).ready(function(){
	
	rows = jQuery("#sceneryDiv table").size();
});

function addPlus(){

	jQuery("#sceneryDiv").append(
		'<table class="datas" id="sceneryTab'+rows+'">'+
		  '<tr class="sceneryType">'+
			'<td>请选择行政区划：</td>'+
			'<td>'+
			  '<select name="TA_ZT_JHJIAD/SF['+rows+']" id="pro'+rows+'" USEDATA="dataSrc'+rows+'" SUBCLASS="1"></select>'+
			  '<select name="TA_ZT_JHJIAD/CS['+rows+']" id="city'+rows+'" USEDATA="dataSrc'+rows+'" SUBCLASS="2" onchange="queryScenery4AJAX(\'plus'+rows+'\',this.value);"></select>'+
			  '<input type="hidden" name="TA_ZT_JHJIAD/ZDR['+rows+']" value="<%=sd.getString("userno")%>"/>'+
			  '<input type="hidden" name="TA_ZT_JHJIAD/TID['+rows+']" value="<%=groupID %>"/>'+
			  '<input type="hidden" name="TA_ZT_JHJIAD/JHZT['+rows+']" value="Y"/>'+
			'</td>'+
		  '</tr>'+
		  '<tr>'+
		  	'<td id="plus'+rows+'" colspan="2"></td>'+
		  '</tr>'+
		'</table>');
	var linkage = new Linkage("dataSrc"+rows, "<%=request.getContextPath()%>/main/data/Sceneryz.xml");
	linkage.init();
	rows++;
}

function delPlus() {
	if(rows > 0) {
		var idx = parseInt(rows-1);
		jQuery("#sceneryDiv table:eq("+idx+")").remove();
		rows--;
	}
}

function queryScenery4AJAX(itemTab,city) {

	var obj=jQuery.ajax({url:"ztQueryScenery4AJAX.?ta_scenery_point/city_id="+city+"&ta_scenery_point/ywlb=z",
		//同步
		async:true,
		dataType:"HTML",
		cache:false,
		success:function(){
		
			jQuery("#"+itemTab).html(obj.responseText);
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) { 
            alert(errorThrown); 
   	 	}
	});
}
function doSub(){

	var check = false;

	jQuery(".datas").each(function (i){

		var msg = jQuery("#plus"+i).find("input").val();
		if(undefined == msg) {
			check = true;
			return false;
		}else{
			check = false;
			return true;
		}
	});

	if(check == true){
		alert("请删除掉无购物点的地区，再提交！");
		return false;
	}else{
		document.ztAddPlusPlans.submit();
		reload();
	}
}
function reload(){
	parent.parent.location.reload(); 
	parent.parent.GB_hide(); 
}
</script>
<title>加点计划</title>
</head>

<body>
<form action="ztAddPlusPlan." name="ztAddPlusPlans" method="post">

<div id="lable">&nbsp;&nbsp;<span class="showPointer" onclick="addPlus();" id="addScenery">添加</span>&nbsp;&nbsp;<span class="showPointer" onclick="delPlus();">删除</span></div>
<div id="sceneryDiv">
<table class="datas" id="sceneryTab0">
  <tr class="sceneryType">
	<td>请选择行政区划：</td>
	<td>
	  <select name="TA_ZT_JHJIAD/SF[0]" id="pro0" USEDATA="dataSrc0" SUBCLASS="1"></select>
	  <select name="TA_ZT_JHJIAD/CS[0]" id="city0" USEDATA="dataSrc0" SUBCLASS="2" onchange="queryScenery4AJAX('plus0',this.value);"></select>
	  <input name="TA_ZT_JHJIAD/ZDR[0]" type="hidden" value="<%=sd.getString("userno")%>"/>
	  <input name="TA_ZT_JHJIAD/TID[0]" type="hidden" value="<%=groupID %>"/>
	  <input name="TA_ZT_JHJIAD/JHZT[0]" type="hidden" value="Y"/>
	</td>
  </tr>
  <tr>
  	<td id="plus0" colspan="2"></td>
  </tr>
</table>
</div>
<div align="center" id="last-sub">
	<input type="button" id="button" value="提交" onclick="doSub();"/>
	<input type="button" id="button" value="关闭" onclick="return reload();"/>
</div>
</form>
</body>
</html>
<script type="text/javascript">
		var linkage = new Linkage("dataSrc0", "<%=request.getContextPath()%>/main/data/Sceneryz.xml");
		linkage.init();
</script>