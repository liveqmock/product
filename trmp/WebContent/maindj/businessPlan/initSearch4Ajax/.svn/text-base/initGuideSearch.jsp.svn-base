<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>查询导游信息</title>
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
	   addSingleRow("peoplebody", "请输入一个查询条件！");
	 }

	 var peopleCache = [ ];
	 var lastFilter = "";

	 function fillTable(people) {
	   var filter = dwr.util.getValue("filter");
	   var pattern = new RegExp("(" + filter + ")", "i");
	   var filtered = [];
	   for (i = 0; i < people.length; i++) {
	     if (pattern.test(people[i].sName)) {
	       filtered.push(people[i]);
	     }
	   }
	   dwr.util.removeAllRows("peoplebody");
	   if (filtered.length == 0) {
	     addSingleRow("peoplebody", "找不到匹配的！");
	   }
	   else {
	     dwr.util.addRows("peoplebody", filtered, [
	       function(person) { return person.sName.replace(pattern, "<span class='highlight'>$1</span>"); },
	       function(person) { return person.sID; },
	       function(person) { return person.linkmanTel; },
	       function(person) { return person.guideCode; },
	       function(person) { return '<input type="button" value="选取" class="sname" onclick="editClicked(\''+person.sName+'\',\''+person.sID+'\',\''+person.linkmanTel+'\',\''+person.guideCode+'\')"/>'; }
	     ], { escapeHtml:false });
	   }
	   peopleCache = people;
	 }
	
	 function filterChanged() {
	   var filter = dwr.util.getValue("filter");
	   if (filter.length == 0) {
	     dwr.util.removeAllRows("peoplebody");
	     addSingleRow("peoplebody", "请输入一个查询条件！");
	   }
	   else {
	     if (filter.charAt(0) == lastFilter.charAt(0)) {
	       fillTable(peopleCache);
	     }
	     else {
	    	 PYAjaxService.guideExecute(filter.charAt(0), fillTable);//向后台取数据
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
		 $(".guide:eq("+itemRow+")",window.parent.document).val(n).next('input').val(s);
		 $(".guideTel:eq("+itemRow+")",window.parent.document).val(l);
		 $(".guideCode:eq("+itemRow+")",window.parent.document).val(g);
		 parent.TB_remove();
	 }
</script>
<body onload="init()">
<div id="lable"><span>查询导游信息</span></div>
<div id="list-table">
<div  id="thisSelect-table" >
	<table class="datas" width="95%">
		<thead>
			<tr>
				<th colspan="4" align="left">&nbsp;查询： <input id="filter" onkeyup="filterChanged();" /></th>
			</tr>
			<tr id="first-tr">
				<th>姓名</th>
				<th>ID</th>
				<th>联系方式</th>
				<th>导游证号</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="peoplebody">
		</tbody>
	</table>
</div>
</div>
</body>
</head>
</html>