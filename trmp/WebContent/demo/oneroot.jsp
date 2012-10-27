<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
	<TITLE> ZTREE DEMO - one root</TITLE>
	<meta http-equiv="content-type" content="text/html; charset=GBK">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/js/ztree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ztree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ztree/js/jquery.ztree.core-3.0.js"></script>
	<SCRIPT LANGUAGE="JavaScript">
	var setting = {
			view: {
				dblClickExpand: dblClickExpand
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: 0
				}
			}
		};
	
	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting, NodesAll);
	});
	
	
		
	var NodesAll =[
				{id:1,pId:0,name:"岗位管理"},
		 		<%
		 			int AllRows =  rd.getTableRowsCount("nodesAll");
		 			for(int k = 0; k < AllRows; k++){
		 				String AllID = rd.getString("nodesAll","id",k);
		 				String AllPID = rd.getString("nodesAll","pid",k);
		 				String AllName = rd.getString("nodesAll","jobname",k);

		 		%>
		 		{ id:<%=AllID%>, pId:<%=AllPID%>, name:"<%=AllName%>", open:false}<%if(k!=(AllRows-1)){out.print(",");}%>
		 		<%
		 		}
		 		%>
		 		];
	function dblClickExpand(treeId, treeNode) {
		return treeNode.level >= 0;
	}
	</SCRIPT>
	<style type="text/css">
.ztree li button.switch.level0 {visibility:hidden; width:1px;}
.ztree li ul.level0 {padding:0; background:none;}
	</style>
 </HEAD>

<BODY>
<h1>绑定一个根节点</h1>
<h6>[ 文件路径：demo/oneroot.jsp ]</h6>
<div class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div class="right">
		<ul class="info">
			<li class="title"><h2> </h2>
				<ul class="list">
				<li class="highlight_red"> </li>
				<li class="highlight_red"> </li>
				<li class="highlight_red"> </li>
				<li class="highlight_red"> </li>
				</ul>
			</li>
		</ul>
	</div>
</div>
</BODY>
</HTML>
