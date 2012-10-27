<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
    <%@include file="/common.jsp" %>
    <%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("TA_CARs")>0){
     pageNO=Integer.parseInt((String)rd.getAttr("TA_CAR", "pageNO"));
	 pageSize=Integer.parseInt((String)rd.getAttr("TA_CAR", "pageSize"));
		totalPage=(Integer)rd.getAttr("TA_CARs", "pagesCount");
		rowsCount = (Integer)rd.getAttr("TA_CARs", "rowsCount");
	}
%>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript">

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
function enableInput(){
	var rs = false;
	var objs = document.car_form.elements;
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
		}else if(confirm('此操作将无法回复,确定删除所勾选数据?')){
	document.car_form.action="batchDelCar.?";
	document.car_form.submit();
		}
}
function getCarByName(){
	document.car_form.action="getCarByName.?TA_CAR@pageNO=1&TA_CAR@pageSize=10";
	document.car_form.submit();
}

//添加车辆信息
function newVeh(){
	window.location.href="<%=request.getContextPath()%>/to_addCar.?DMSM/CLLX=13&TEAM_ID=<%=rd.getStringByDI("TA_CAR","TEAM_ID",0) %>";
}

//修改车辆信息
function editVeh(url){
	window.location.href=url;
}
</script>
<title>车辆基础信息列表</title>
</head>
<body>
<form  name="car_form" method="post">
<input type="hidden" name="TEAM_ID" value="<%=rd.getStringByDI("TA_CAR","TEAM_ID",0) %>" />
<div id="lable"><span>车辆基础信息列表</span></div>
<div id="top-select">
  <div class="select-div" >
	<span class="text" id="select-condition">查询条件</span>
  </div>
  <div class="select-div" onclick="newVeh();"><span id="add-icon"></span>
	<span class="text">添加</span>
  </div>
  <div class="select-div" onclick="batchDel()"><span id="del-icon"></span>
	<span class="text">删除</span>
  </div>
  <div class="select-div" onclick="javascript:history.go(-1);"><span id="goBack-icon"></span>
	<span class="text">返回</span>
  </div>
</div>
	
<div id="ex3a" class="jqmDialog">
	<div class="jqmdTL"><div class="jqmdTR"><div class="jqmdTC jqDrag">按条件查询</div></div></div>
	<div class="jqmdBL"><div class="jqmdBR"><div class="jqmdBC">
	<div class="jqmdMSG">
	<table>
		<tr>
			<td>司机姓名：</td>
			<td><input type="text" name="drive_name" /></td>
		</tr>
		<tr>
			<td>车牌号：</td>
			<td><input type="text" name="code" /></td>
		</tr>
	</table>
	      <input type="button" id="button" value="查询" onclick="getCarByName();"/>
			 
	</div>
	</div></div></div>
	<input type="image" src="<%=request.getContextPath() %>/images/if-select-img/close.gif" class="jqmdX jqmClose" />
</div>
	
	

<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td id="selectAll"><input type="checkbox" id="checkBoxCar"></input></td>
			<td>司机姓名-电话</td>
			<td>车牌号码</td>
			<td>车辆类型</td>
			<td>购车时间</td>
			<td>操作</td>
		</tr>
		<%int teamRows=rd.getTableRowsCount("TA_CARs");for(int h=0;h<teamRows;h++){%>
		<tr>
			<td><input type="checkbox" class="checkBoxCar" name="CAR/CHECKBOX[<%=h%>]" value="<%=rd.getStringByDI("TA_CARs","CAR_ID",h) %>"></input></td>
			<td><%=rd.getStringByDI("TA_CARs","DRIVER_NAME",h)%>-<%=rd.getStringByDI("TA_CARs","DRIVER_PHONE",h)%></td>
			<td><%=rd.getStringByDI("TA_CARs","CAR_CODE",h)%></td>
			<td><%=rd.getStringByDI("TA_CARs","CAR_TYPE",h)%></td>
			<td><%if("".equals(rd.getStringByDI("TA_CARs","BUY_DATE",h)))out.print(rd.getStringByDI("TA_CARs","BUY_DATE",h));else out.print(rd.getStringByDI("TA_CARs","BUY_DATE",h).substring(0,10));%></td>
			<td>
				<a href="###" onclick="editVeh('<%=request.getContextPath()%>/getCarById.?TA_CAR/CAR_ID=<%=rd.getStringByDI("TA_CARs","CAR_ID",h) %>&DMSM/CLLX=13');">
					<img alt="编辑" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[修改]
				</a>-
				<a href="<%=request.getContextPath() %>/delCar.?car_id=<%=rd.getStringByDI("TA_CARs","CAR_ID",h)%>&TEAM_ID=<%=rd.getStringByDI("TA_CARs","TEAM_ID",0) %>" onclick="return confirm('此操作将无法恢复,确定删除吗?')">
					<img alt="删除" src="<%=request.getContextPath()%>/images/del-icon.gif">&nbsp;[删除]
				</a>
			</td>
		</tr>
		<%} %>
	</table>
</div>
<div id="page">
	<%String url="getAllCar.?TA_CAR/TEAM_ID="+rd.getStringByDI("TA_CAR","TEAM_ID",0)+"&TA_CAR@pageSize=10&TA_CAR@pageNO=";%>
	<span title="首页" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="上一页" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
					共<%=rd.getAttr("TA_CARs","rowsCount")==null?"0":rd.getAttr("TA_CARs","rowsCount") %> 条记录，
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