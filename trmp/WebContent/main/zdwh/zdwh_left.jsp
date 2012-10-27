<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>字典维护</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ztree/js/jquery.ztree.core-3.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ztree/js/jquery.ztree.exedit-3.1.js"></script>
	
	<SCRIPT type="text/javascript">
	
	var setting = {
		view: {
				dblClickExpand: false,
				showLine : true
		},
		edit: {
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
	
    int AllRows =  rd.getTableRowsCount("DMLBs");
    for(int k = 0; k < AllRows; k++){
    	String ID = rd.getString("DMLBs","DMLB",k);
       	String Name = rd.getString("DMLBs","LBSM",k);
%>
       	{id:<%=ID %>, name:"<%=k+1 %>.<%=Name %>", open:false}<%if(k!=(AllRows-1)){out.print(",");}%>
<%	}%>
     ];
	function onClick(e,treeId, treeNode) {

		var id = treeNode.id;
		var url = "queryById.?DMLB/DMLB="+id+"&DMSM/DMLB="+id+"&DMSM@orderBy=DMZ";
		
		parent.window.zd_right.location.href=url;
	}
	
	$(document).ready(function(){
		$.fn.zTree.init($("#drictTree"), setting, NodesAll);
	});
	</SCRIPT>
</head>
<body>
		
	
	<div class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul id="drictTree" class="ztree"></ul>
	</div>
</div>
</body>
</html>