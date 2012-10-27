<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
String groupID = rd.getStringByDI("TA_ZT_JHJIADs","tid",0);
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

function queryScenery4AJAX(itemTab,city){

	var obj=jQuery.ajax({url:"ztQueryScenery4AJAX.?ta_scenery_point/ywlb=z&ta_scenery_point/city_id="+city,
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
	document.ztAddPlusPlans.submit();
	reload();
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
<%
int sceneryRow = rd.getTableRowsCount("TA_ZT_JHJIADs");
for(int i=0;i<sceneryRow;i++){
	String cityID = rd.getStringByDI("TA_ZT_JHJIADs","cs",i);
	String planID = rd.getStringByDI("TA_ZT_JHJIADs","id",i);
%>
<table class="datas" id="sceneryTab<%=i %>">
  <tr class="sceneryType">
	<td>请选择行政区划：</td>
	<td>
	  <select name="TA_ZT_JHJIAD/SF[<%=i %>]" id="pro<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="1"></select>
	  <select name="TA_ZT_JHJIAD/CS[<%=i %>]" id="city<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="2" onchange="queryScenery4AJAX('plus<%=i %>',this.value);"></select>
	  <input name="TA_ZT_JHJIAD/ZDR[<%=i %>]" type="hidden" value="<%=sd.getString("userno")%>"/>
	  <input name="TA_ZT_JHJIAD/TID[<%=i %>]" type="hidden" value="<%=groupID %>"/>
	  <input name="TA_ZT_JHJIAD/JHZT[<%=i %>]" type="hidden" value="Y"/>
	</td>
  </tr>
    <tr>
	  <td id="plus<%=i %>" colspan="2">
<%
int pointRow = rd.getTableRowsCount("rsScenerys");
for(int j=0;j<pointRow;j++){
	String id = rd.getStringByDI("rsScenerys","id",j);
	String sceneryID = rd.getStringByDI("rsScenerys","jdid",j);
	String sceneryName = rd.getStringByDI("rsScenerys","jdmc",j);
	String is = rd.getStringByDI("rsScenerys","ISCHECK",j);
	if(planID.equals(id)){
%>
景点名称：
	  	 <input type="hidden" name="TA_ZT_JHJIADJD<%=cityID %>/JDID[<%=j%>]" value="<%=sceneryID %>"/>
	  	 <input type="hidden" name="TA_ZT_JHJIADJD<%=cityID %>/JDMC[<%=j%>]" value="<%=sceneryName %>"/>
	  	 <input type="checkbox" name="TA_ZT_JHJIADJD<%=cityID %>/ISCHECK[<%=j%>]" value="Y" <%if(is.equals("Y")){ %>checked="checked" <%} %>/><font color="red"><%=sceneryName %></font>
	  <%	}
} %>
	  </td>
	</tr>
</table>
<%
}%>
</div>
<div align="center" id="last-sub">
<%
if(!"view".equals(rd.getString("flag"))){
%>
	<input type="button" id="button" value="提交" onclick="doSub();"/>
<%
} %>
	<input type="button" id="button" value="关闭" onclick="return reload();"/>
</div>
</form>
</body>
</html>
<script type="text/javascript">
<%
for(int i=0;i<sceneryRow;i++){
%>
	var linkage = new Linkage("dataSrc<%=i %>", "<%=request.getContextPath()%>/main/data/Sceneryz.xml");
	linkage.init();
	linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("TA_ZT_JHJIADs","SF",i) %>",1);
	linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("TA_ZT_JHJIADs","cs",i)%>",2);
<%
}%>
</script>