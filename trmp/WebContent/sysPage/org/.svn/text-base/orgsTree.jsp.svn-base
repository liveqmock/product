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
	
	<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/DeptAjaxService.js'></script>
	<SCRIPT type="text/javascript"><!--
	
	var setting = {
			view: {
				dblClickExpand: false,
				showLine : true,
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom
			},
			edit: {
				enable: true,
				editNameSelectAll: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeDrag: beforeDrag,
				beforeEditName: beforeEditName,//修改
				beforeRemove: beforeRemove,
				beforeRename: beforeRename,
				onRemove: onRemove,
				onRename: onRename,
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
	var log, className = "dark";
	function beforeDrag(treeId, treeNodes) {
		return false;
	}
	function beforeEditName(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
		var zTree = $.fn.zTree.getZTreeObj("orgTree");
		zTree.selectNode(treeNode);
		return confirm("确认修改  -- " + treeNode.name + " 的名称吗？");
	}
	
	function beforeRemove(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
		var zTree = $.fn.zTree.getZTreeObj("orgTree");
		zTree.selectNode(treeNode);
		var pId = treeNode.pId;
		if(pId=='0' || pId==null){
			alert('机构不可被删除！');
			return false;
		}else{
			if(confirm("确认删除  -- " + treeNode.name + " 吗？")){
					var deptid = treeNode.id;
					var child_ids = getChildNodes(treeNode);
					DeptAjaxService.deleteDept(child_ids,setData);
			}else{
				return false;
			}
		}
	}
	function setData(data){
		var obj=data;
	}
	function getChildNodes(treeNode){ 
		var child_ids = "";
		var ztree = $.fn.zTree.getZTreeObj("orgTree");
        var childNodes = ztree.transformToArray(treeNode); 
        var nodes = new Array(); 
        for(i = 0; i < childNodes.length; i++) { 
        	child_ids = child_ids + childNodes[i].id + ","; 
        } 
        child_ids = child_ids.substring(0,child_ids.length-1);
        return child_ids; 
	} 
	function onRemove(e, treeId, treeNode) {
		parent.orgRight.location.href="queryOrg.?HROrganization/orgid=<%=sd.getString("orgid") %>&HRDEPARTMENT/orgid=<%=sd.getString("orgid")%>&HRDEPARTMENT/pdeptid=0&fwd=info";
		alert('删除成功！');
	}
	function beforeRename(treeId, treeNode, newName) {
		className = (className === "dark" ? "":"dark");
		showLog("[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
		if (newName.length == 0) {
			alert("节点名称不能为空.");
			var zTree = $.fn.zTree.getZTreeObj("orgTree");
			setTimeout(function(){zTree.editName(treeNode)}, 10);
			return false;
		}
		return true;
	}
	
	function onRename(e, treeId, treeNode) {
		
		var id = treeNode.id;
		var pID = treeNode.pId;
		var name = treeNode.name;
		var orgID = treeNode.orgid;
		if(pID == null){
			$("#orgId").val(orgID);
			$("#orgIdOldValue").val(orgID);
			$("#orgName").val(name);
			$("#orgId").val(orgID);
			$("#deptid4Org").val(id);
			$("#deptidOldValue4Org").val(id);
			document.orgTreeForm.submit();
		}else{
			$("#deptid4Dept").val(id);
			$("#deptidOldValue4Dept").val(id);
			$("#pDeptId").val(pID);
			$("#deptName").val(name);
			document.deptTreeForm.submit();
		}
	}
	function showLog(str) {
		if (!log) log = $("#log");
		log.append("<li class='"+className+"'>"+str+"</li>");
		if(log.children("li").length > 8) {
			log.get(0).removeChild(log.children("li")[0]);
		}
	}
	function getTime() {
		var now= new Date(),
		h=now.getHours(),
		m=now.getMinutes(),
		s=now.getSeconds(),
		ms=now.getMilliseconds();
		return (h+":"+m+":"+s+ " " +ms);
	}

	var newCount = 1;
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.id).length>0) return;
		var addStr = "<button type='button' class='add' id='addBtn_" + treeNode.id
			+ "' title='添加' onfocus='this.blur();'></button>";
		sObj.append(addStr);
		var btn = $("#addBtn_"+treeNode.id);
		if (btn) btn.bind("click", function(){
			var zTree = $.fn.zTree.getZTreeObj("orgTree");
			/*动态获取主键 开始*/
			$.ajax({
				dataType:"json",
				url:'queryNextDeptId.',
				cache:false,
				success:function(data){
					zTree.addNodes(treeNode, {id:data[0].deptid, pId:treeNode.id, name:"新部门" + (newCount++)});
				},
				error:function(){
					alert("系统异常，请稍后再试！");
				}
			});
			/*动态获取主键 结束*/
			return false;
		});
	};
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.id).unbind().remove();
	};
	
	function onClick(e,treeId, treeNode) {

		var deptid = treeNode.id;
		var orgid = treeNode.orgid;
		var pId = treeNode.pId;
		if(treeNode.pId == null){
			parent.orgRight.location.href="queryOrg.?HROrganization/orgid="+orgid+"&HRDEPARTMENT/deptid="+deptid+"&fwd=info";
		}else{
			parent.orgRight.location.href="queryDept.?HRDepartment/deptid="+deptid+"&HRDEPARTMENT/pdeptid="+pId;
		}
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
<div class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul id="orgTree" class="ztree"></ul>
	</div>
</div>
<form name="orgTreeForm" method="post" action="updateOrg4Ajax.">
	<input type="hidden" name="HROrganization/orgid" id="orgId"></input>
	<input type="hidden" name="HROrganization/orgID[0]@oldValue" id="orgIdOldValue"/>
	<input type="hidden" name="HROrganization/name" id="orgName"></input>
	
	<input type="hidden" name="HRDEPARTMENT/deptid" id="deptid4Org"/>
	<input type="hidden" name="HRDEPARTMENT/deptid[0]@oldValue" id="deptidOldValue4Org"/>
</form>

<form name="deptTreeForm" method="post" action="updateDept.">
	<input type="hidden" name="fwd" value="tree"/>
	<input type="hidden" name="HRDEPARTMENT/deptid" id="deptid4Dept"/>
	<input type="hidden" name="HRDEPARTMENT/deptid[0]@oldValue" id="deptidOldValue4Dept"/>
	<input type="hidden" name="HRDEPARTMENT/PDEPTID" id="pDeptId"/>
	<input type="hidden" name="HRDEPARTMENT/DEPTNAME" id="deptName"/>
</form>

</BODY>
</html>

  