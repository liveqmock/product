<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>查询旅行社信息</title>
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<!-- dwr begin -->
<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/engine.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/util.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/interface/PYAjaxService.js" ></script>
<!-- dwr end -->
<script type="text/javascript">
	 function init() {
	   dwr.util.useLoadingMessage();
	   dwr.util.setValue("filter", "");
	   addSingleRow("travelbody", "请输入一个查询条件！");
	   document.getElementById("filter").focus();
	 }

	 var travelCache = [ ];
	 var lastFilter = "";

	 function fillTable(travel) {
	   var filter = dwr.util.getValue("filter");
	   var pattern = new RegExp("(" + filter + ")", "i");
	   var filtered = [];
	   for (i = 0; i < travel.length; i++) {
	     if (pattern.test(travel[i].sName)) {
	       filtered.push(travel[i]);
	     }
	   }
	   dwr.util.removeAllRows("travelbody");
	   if (filtered.length == 0) {
	     addSingleRow("travelbody", "找不到匹配的！");
	   }
	   else {
	     dwr.util.addRows("travelbody", filtered, [
	       function(travel) { return travel.sName.replace(pattern, "<span class='highlight'>$1</span>"); },
	       function(travel) { return travel.csName; },
	       function(travel) { return '<input type="button" value="选取" class="sname" onclick="editClicked(\''+travel.sName+'\',\''+travel.province+'\',\''+travel.city+'\',\''+travel.sID+'\')"/>'; }
	     ], { escapeHtml:false });
	   }
	   travelCache = travel;
	 }
	
	 function filterChanged() {
	   var filter = dwr.util.getValue("filter");
	   if (filter.length == 0) {
	     dwr.util.removeAllRows("travelbody");
	     addSingleRow("travelbody", "请输入一个查询条件！");
	   }
	   else {
	     if (filter.charAt(0) == lastFilter.charAt(0)) {
	       fillTable(travelCache);
	     }
	     else {
	    	 PYAjaxService.travelExecute(filter.charAt(0), fillTable);//向后台取数据
	     }
	   }
	   lastFilter = filter;
	 }

	 function addSingleRow(id, message) {
	   dwr.util.addRows(id, [1], [
	     function(data) { return message; }
	   ], {
	     cellCreator:function() {
	       var td = document.createElement("td");
	       td.setAttribute("colspan", 5);
	       return td;
	     }
	   });
	 }
	 
	 function editClicked(n,s,l,g){
		 var itemRow = parent.document.getElementById("itemRow").value;
		 $(".travel:eq("+itemRow+")",window.parent.document).val(n);
		 $(".travelSF:eq("+itemRow+")",window.parent.document).val(s);
		 $(".travelCS:eq("+itemRow+")",window.parent.document).val(l);
		 $(".travelID:eq("+itemRow+")",window.parent.document).val(g);
		 parent.TB_remove();
	 }
</script>
<body onload="init();">
<div id="lable"><span>查询旅行社信息</span></div>
<div id="list-table">
<div  id="thisSelect-table" >
	<table class="datas" width="95%">
		<thead>
			<tr>
				<th colspan="4" align="left">&nbsp;查询： <input id="filter" onkeyup="filterChanged();" /></th>
			</tr>
			<tr id="first-tr">
				<th>组团社名称</th>
				<th>行政区划</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="travelbody">
		</tbody>
	</table>
</div>
</div>
</body>
</head>
</html>