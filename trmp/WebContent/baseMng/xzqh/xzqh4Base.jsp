<%@ page language="java" contentType="text/html; charset=GBK"  pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Cache-Control" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
	
	<link rel="stylesheet" href="<%=request.getContextPath()%>/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ztree/js/jquery.ztree.core-3.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ztree/js/jquery.ztree.exedit-3.1.js"></script>
	
	<SCRIPT type="text/javascript"><!--
	
	var setting = {
			view: {
				dblClickExpand: false,
				showLine : true
			},
			edit: {
				editNameSelectAll: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick
			}
		};
	var NodesAll =[
		//{id:0,pId:-1,name:"行政区划"}<%if(rd.getTableRowsCount("xzqhs") > 0){out.print(",");}%>
       	<%
       		int AllRows =  rd.getTableRowsCount("xzqhs");
       		for(int k = 0; k < AllRows; k++){
       			String ID = rd.getString("xzqhs","id",k);
       		 	String PID = rd.getString("xzqhs","pid",k);
       		 	String Name = rd.getString("xzqhs","name",k);
       		 	String layer = rd.getString("xzqhs","layer",k);
       	%>
       			{id:<%=ID%>, pId:<%=PID%>, name:"<%=Name%>", open:false}<%if(k!=(AllRows-1)){out.print(",");}%>
       	<%	}%>
     ];
    
	function onClick(e,treeId, treeNode) {

		var deptid = treeNode.id;
		var name = treeNode.name;
		if(parent.document.getElementById("cityName")){
			parent.document.getElementById("cityName").value = name;
		}
		if(parent.document.getElementById("cityId")){
			parent.document.getElementById("cityId").value = deptid;
		}
		parent.TB_remove();//关闭层，TB_remove_refresh()关闭刷新。
	}
	
	$(document).ready(function(){
		$.fn.zTree.init($("#xzqhTree"), setting, NodesAll);
	});
	</SCRIPT>
	<style type="text/css">
	.ztree li button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
  </head>
	
  <BODY>
<div class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul id="xzqhTree" class="ztree"></ul>
	</div>
</div>
</BODY>
</html>