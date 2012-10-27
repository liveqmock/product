<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@include file="../common.jsp" %>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.dream.bizsdk.common.util.string.StringConvertor"%>

<%
int rows=rd.getTableRowsCount("DRMRoleMenus");
List<String> sub = new ArrayList<String>();
List<String> top = new ArrayList<String>();
for(int i=0;i<rows;i++){
	
	String parentMenuName =  rd.getStringByDI("DRMRoleMenus","PARENTMENUNAME",i);
	if(parentMenuName.equals("")){
		
		String menuLabel = rd.getStringByDI("DRMRoleMenus","menuLabel",i);
		String menuNameEn = rd.getStringByDI("DRMRoleMenus","menuName",i);
		top.add(menuLabel+"|"+menuNameEn);
	}else{
		
		String menuLabel = rd.getStringByDI("DRMRoleMenus","menuLabel",i);
		String menuParentName = rd.getStringByDI("DRMRoleMenus","parentMenuName",i);
		String funcNo = rd.getStringByDI("DRMRoleMenus","funcNo",i);
		sub.add(menuLabel+"|"+menuParentName+"|"+funcNo);
	}
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title><%=sd.getString("simpleName") %>--天游资源管理平台(TRMP)</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/index.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath() %>/js/dtreeForMenu.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ztree/js/jquery.ztree.core-3.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ztree/js/jquery.ztree.exedit-3.1.js"></script>
<script type="text/javascript">
	function openChild(obj,i){   //打开二级子菜单
		var id = obj.id+"_c_ztree";
		var ul_innerHTML = document.getElementById(id).innerHTML;
		if(ul_innerHTML==''){
			openTree(obj,i);
		}else{
			changeChild(obj)
		}
	}

	var oldObj=null;
	function changeChild(obj){  //一级菜单显示/隐藏
		var id = obj.id+"_c";
		var old_id;
		if(oldObj!=null && oldObj!=obj){
			old_id = oldObj.id+"_c";
		//	document.getElementById(old_id).style.display = "none";
		//	document.getElementById(id).style.display = "";
			if(document.getElementById(id).style.display == ""){
				document.getElementById(id).style.display = "none";
			}else{
				document.getElementById(id).style.display = "";
			}
		}else if(oldObj!=null && oldObj==obj){
			if(document.getElementById(id).style.display == ""){
				document.getElementById(id).style.display = "none";
			}else{
				document.getElementById(id).style.display = "";
			}
		}else{
			document.getElementById(id).style.display = "";
		}
		oldObj = obj;
	}
	<%
	int level1Rows = rd.getTableRowsCount("menuLevel1");
	for(int i=0;i<level1Rows;i++) {
	%>
	var NodesAll_<%=i%> = [
	<%
		int menuOthersRow = rd.getTableRowsCount("DRMRoleMenus");
		String menuName = rd.getString("menuLevel1","menuName",String.valueOf(i));
		String datas = "";
		for(int j=0;j<menuOthersRow;j++){
			String moduleType = rd.getStringByDI("DRMRoleMenus","MODULETYPE",j);
			if(moduleType.equals(menuName)){
				String menuName2 = rd.getStringByDI("DRMRoleMenus","menuName",j);
				String menuLabel = rd.getStringByDI("DRMRoleMenus","menuLabel",j);
				String func = rd.getStringByDI("DRMRoleMenus","funcNO",j);
				String url = StringConvertor.replaceAndEncode(func.replaceAll("%ContextPath%",request.getContextPath()),sd);
				String pMenuName = rd.getStringByDI("DRMRoleMenus","parentMenuName",j);
				datas += "{id:"+menuName2+", pId:"+pMenuName+", name:\""+menuLabel+"\",url_no:\""+url+"\", open:false},";
			}
		}
		if(!datas.equals("")){
			datas = datas.substring(0,datas.length()-1);
			out.print(datas);
		}
	%>
	 ];
	<%
	}%>
	var setting = {
			view: {
				dblClickExpand: true,
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
	
	
	function openTree(obj,i){   //给各个子菜单赋值
		<%
		for(int i=0;i<level1Rows;i++){
		%>
		if(i==<%=i%>){
			var id = "#"+obj.id+"_c_ztree";
			$.fn.zTree.init($(id), setting, NodesAll_<%=i%>);
		}
		<%
		}
		%>
		changeChild(obj);
	};

	function onClick(e, treeId, treeNode) {
		var url = treeNode.url_no;
		if(url != null && url !='') 
			parent.window.middelRight.location.href=url;
		
	}
</script>



</head>
<body>
<div id="left-tree">
	<div id="left-tree-top">
		<div style=" width: 80px;float: left">功能导航</div><div id="left-tree-icon"  ></div>
	</div>
	
	<%
	for(int i=0;i<level1Rows;i++){
		String menuLabel = rd.getStringByDI("menuLevel1","menuLabel",i);
	%>
	<div id="tree_f_<%=i %>" onclick="openChild(this,<%=i %>)" class="left-tree-f">
		<div style="float: left;cursor: pointer;color: black;"><%=menuLabel %></div>
	</div>
	<div id="tree_f_<%=i %>_c" style="display: none">
		<ul id="tree_f_<%=i %>_c_ztree" class="ztree"></ul>
	</div>
	<%
	}
	%>
</div>
</body>
</html>
