<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ztree/js/jquery.ztree.core-3.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ztree/js/jquery.ztree.exedit-3.1.js"></script>
	<script type="text/javascript" ><!--
	
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
	       		
       	<%
       		int AllRows =  rd.getTableRowsCount("hrdepartments");
       		for(int k = 0; k < AllRows; k++){
       			String orgID = rd.getString("hrdepartments","orgid",k);
       			String ID = rd.getString("hrdepartments","deptid",k);
       		 	String PID = rd.getString("hrdepartments","pdeptid",k);
       		 	String Name = rd.getString("hrdepartments","deptname",k);
       	%>
       			{id:<%=ID%>, pId:<%=PID%>, name:"<%=Name%>", orgid:<%=orgID %>, open:false}<%if(k!=(AllRows-1)){out.print(",");}%>
       	<%	}%>
     ];
	
	function onClick(e,treeId, treeNode) {

		var deptid = treeNode.id;
		var name = treeNode.name;
		if(parent.document.getElementById("userFromNm")){
			parent.document.getElementById("userFromNm").value = name;
		}
		if(parent.document.getElementById("deptID")){
			parent.document.getElementById("deptID").value = deptid;
		}
		parent.TB_remove();//关闭层，TB_remove_refresh()关闭刷新。
	}
	
	$(document).ready(function(){
		$.fn.zTree.init($("#orgTree"), setting, NodesAll);
	});
	</SCRIPT>
	<style type="text/css">
	.ztree li button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
</head>

<BODY>
<div class="content_wrap" style="overflow:scroll; width:400px; height:400px;">
	<div class="zTreeDemoBackground left">
		<ul id="orgTree" class="ztree"></ul>
	</div>
</div>
</BODY>
</html>

  