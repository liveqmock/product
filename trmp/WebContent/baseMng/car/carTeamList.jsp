<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %> 
 <%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("TA_CAR_TEAMs")>0){
     pageNO=Integer.parseInt((String)rd.getAttr("TA_CAR_TEAM", "pageNO"));
	 pageSize=Integer.parseInt((String)rd.getAttr("TA_CAR_TEAM", "pageSize"));
		totalPage=(Integer)rd.getAttr("TA_CAR_TEAMs", "pagesCount");
		rowsCount = (Integer)rd.getAttr("TA_CAR_TEAMs", "rowsCount");
	}
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />

<script type="text/javascript">
function enableInput(){
	var rs = false;
	var objs = document.team_form.elements;
	if(objs==null){
		return false;
	}
	var len=objs.length;
	var checkbox;
	for(var i=0;i<len;i++){
		if(objs.item(i).tagName=="INPUT" && objs.item(i).type=="checkbox" && objs.item(i).name!=''){
			checkbox=objs.item(i); 
			if(checkbox.checked){
				rs = true;
			}
		}
	}
	return rs;
}
	function batchDel(){
		var e=enableInput();
		if(e==false){
			return false;
		}else if(confirm('此操作将无法回复,确认删除所勾选项?')){
			document.team_form.action="batchDelCarTeam.";
			document.team_form.submit();
		}
	}
	function getCarTeamByName(){
		document.team_form.action="getCarTeamByName.?TA_CAR_TEAM@pageNO=1&TA_CAR_TEAM@pageSize=10";
		document.team_form.submit();
	}

	/**
	*	checkBox的全选和全中选
	*/
	$(document).ready(function() {	
		$('[type=checkbox]').click(function (){
			var checkClass = $(this).attr("class");
			if(checkClass){
				if($("."+checkClass).is(":checked")){
					$("#"+checkClass).attr("checked","checked");
				}else{
					$("#"+checkClass).removeAttr("checked");
				}
			}
			var checkID = $(this).attr("id");
			if(checkID){
				if($("#"+checkID).is(":checked")){
					$("."+checkID).each(function (){
						$(this).attr("checked","checked");
					});
				}else{
					$("."+checkID).each(function (){
						$(this).removeAttr("checked");
					});
				}
			}
		});
	});
	/*
	 *end
	*/

	//添加车队信息
	function newVehTeam(){
		window.location.href="<%=request.getContextPath()%>/to_addTeam.?DMSM/KHYH=8&city_id=<%=rd.getStringByDI("TA_CAR_TEAM","CITY_ID",0) %>";
	}

	//修改车队信息
	function editVehTeam(url){
		window.location.href=url;
	}
	
</script>
<title>车队列表</title>
</head>
<body>
<form  name="team_form" method="post">
<div id="lable"><span>车队基础信息列表</span></div>
	<div id="top-select">
	<div class="select-div" >
	  <span class="text" id="select-condition">查询条件</span>
	</div>
		<div class="select-div" onclick="newVehTeam();"><span id="add-icon"></span>
		 <span class="text">添加车队</span>
		</div>
		<div class="select-div" onclick="batchDel();"><span id="del-icon"></span> <span
			class="text">删除</span>
		</div>
	</div>
	
<div id="ex3a" class="jqmDialog">
	<div class="jqmdTL"><div class="jqmdTR"><div class="jqmdTC jqDrag">按条件查询</div></div></div>
	<div class="jqmdBL"><div class="jqmdBR"><div class="jqmdBC">
	<div class="jqmdMSG">
	车队名称：<input type="text" name="name"  class="text_input"/>&nbsp;
			  <input type="button" value="查询"  onClick="getCarTeamByName();"/>
	</div>
	</div></div></div>
	<input type="image" src="<%=request.getContextPath() %>/images/if-select-img/close.gif" class="jqmdX jqmClose" />
</div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td id="selectAll"><input type="checkbox" id="checkBoxVeh"></input></td>
			<td>车队名称</td>
			<td>公司地址</td>
			<td>总负责人-手机</td>
			<td>业务员名字-手机</td>
			<td>操作</td>
		</tr>
		<%int teamRows=rd.getTableRowsCount("TA_CAR_TEAMs");for(int h=0;h<teamRows;h++){%>
		<tr>
			<td><input type="checkbox" class="checkBoxVeh" name="CAR_TEAM/CHECKBOX[<%=h%>]" value="<%=rd.getStringByDI("TA_CAR_TEAMs","TEAM_ID",h) %>"></input></td>
			<td><%=rd.getStringByDI("TA_CAR_TEAMs","CMPNY_NAME",h)%></td>
			<td><%=rd.getStringByDI("TA_CAR_TEAMs","CMPNY_ADDRESS",h)%></td>
			<td><%=rd.getStringByDI("TA_CAR_TEAMs","CHIEF_NAME",h)%>-<%=rd.getStringByDI("TA_CAR_TEAMs","CHIEF_MOBILE",h)%></td>
			<td><%=rd.getStringByDI("TA_CAR_TEAMs","BUSINESS_NAME",h)%>-<%=rd.getStringByDI("TA_CAR_TEAMs","BUSINESS_MOBILE",h)%></td>
			<td>
				<a href="###" onclick="editVehTeam('<%=request.getContextPath()%>/getCarTeamById.?TA_CAR_TEAM/TEAM_ID=<%=rd.getStringByDI("TA_CAR_TEAMs","TEAM_ID",h) %>&DMSM/KHYH=8')">
					<img alt="编辑" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[修改]
				</a>-
				<a href="<%=request.getContextPath() %>/delCarTeam.?TEAM_ID=<%=rd.getStringByDI("TA_CAR_TEAMs","TEAM_ID",h)%>&CITY_ID=<%=rd.getStringByDI("TA_CAR_TEAMs","CITY_ID",h) %>" onclick="return confirm('此操作将无法恢复,确定删除吗?')">
					<img alt="删除" src="<%=request.getContextPath()%>/images/del-icon.gif">&nbsp;[删除]
				</a>-
				<a href="<%=request.getContextPath()%>/getAllCar.?TA_CAR@pageNO=1&TA_CAR@pageSize=10&TA_CAR/TEAM_ID=<%=rd.getStringByDI("TA_CAR_TEAMs","TEAM_ID",h) %>">[车辆信息维护]</a>
			</td>
		</tr>
		<%} %>
	</table>
</div>
<div id="page">
	<%String url=request.getContextPath()+"/baseMng/car/getAllCarTeam.?TA_CAR_TEAM@pageSize=10&TA_CAR_TEAM@pageNO=";%>
	<span title="首页" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="上一页" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
					共<%=rd.getAttr("TA_CAR_TEAMs","rowsCount")==null?"0":rd.getAttr("TA_CAR_TEAMs","rowsCount") %> 条记录，
					每页 <%=pageSize%>条
	</span>
	<span title="下一页" class="next-page" onclick='query("<%=pageNO+1>totalPage?totalPage:pageNO+1 %>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span title="尾页" class="last-page" onclick='query("<%=totalPage%>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="go-to-page" >
		跳转到第：<input type="text" id="gotopage"/> 页
	</span>
	<span title="跳转" class="go-to-page-icon" onclick='go("<%=totalPage%>","<%=(int)Math.min(pageNO,totalPage)%>","<%=url %>")'></span>
</div>
</form>
</body>
</html>