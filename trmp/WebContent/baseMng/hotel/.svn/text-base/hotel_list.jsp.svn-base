<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("TA_HOTELs")>0){
     pageNO=Integer.parseInt((String)rd.getAttr("TA_HOTEL", "pageNO"));
	 pageSize=Integer.parseInt((String)rd.getAttr("TA_HOTEL", "pageSize"));
		totalPage=(Integer)rd.getAttr("TA_HOTELs", "pagesCount");
		rowsCount = (Integer)rd.getAttr("TA_HOTELs", "rowsCount");
	}
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/selectAll.js"></script>
<title>酒店维护</title>
<script type="text/javascript">
function enableInput(){
	var rs = false;
	var objs = document.hotel_form.elements;
	if(objs==null){
		return false;
	}
	var len=objs.length;
	var checkbox;
	for(var i=0;i<len;i++){
		if(objs.item(i).tagName=="INPUT" && objs.item(i).type=="checkbox" && objs.item(i).name!=''){
			checkbox=objs.item(i); 
			//var ts=checkbox.name.split('[')[1];
			//var index=(ts.substring(0,ts.length-1));
			if(checkbox.checked){
				//document.getElementById("XYJBXXB/SFZMHM["+index+"]").disabled=false;
				rs = true;
			}
		}
	}
	return rs;
}
	function del_select(){
		var rst= enableInput();
		if(rst==false){
		return false;
			}
		if(confirm('此操作将无法恢复,确定删除所勾选项?')){
		document.hotel_form.action="batchDelHotel.?";
		document.hotel_form.submit();
		}
		}
	function getHotelByName(){
		document.hotel_form.action="getHotelByName.?TA_HOTEL@pageNO=1&TA_HOTEL@pageSize=10";
		document.hotel_form.submit();
		}
	function editHotel(url){
		window.location.href=url;
		}
	//添加酒店信息
	function newHotel(){
		window.location.href="<%=request.getContextPath()%>/to_addJsp.?DMSM/JDXJ=6&DMSM/ZCLX=7&DMSM/HOTELJGLX=10";
	}
	//修改酒店信息
	function editHotel(url){
		window.location.href=url;
	}
</script>
</head>
<body>
<form  name="hotel_form" method="post">
<div id="lable"><span>酒店基础信息维护</span></div>
	<div id="top-select">
	<div class="select-div" >
	  <span class="text" id="select-condition">查询条件</span>
	</div>
		<div class="select-div" onclick="newHotel()"><span id="add-icon"></span>
		 <span class="text">添加</span>
		</div>
		<div class="select-div" onclick="del_select()"><span id="del-icon"></span> <span
			class="text">删除</span>
		</div>
	</div>
	
<div id="ex3a" class="jqmDialog">
	<div class="jqmdTL"><div class="jqmdTR"><div class="jqmdTC jqDrag">按条件查询</div></div></div>
	<div class="jqmdBL"><div class="jqmdBR"><div class="jqmdBC">
	<div class="jqmdMSG">
	酒店名称：<input type="text" name="CMPNY_NAME" id="CMPNY_NAME" class="text_input"/>&nbsp;
			  <input type="button" value="查询"  onClick="getHotelByName();"/>
	</div>
	</div></div></div>
	<input type="image" src="<%=request.getContextPath() %>/images/if-select-img/close.gif" class="jqmdX jqmClose" />
</div>
	
	

<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td id="selectAll"><input type="checkbox" id="checkboxTop"></input></td>
			<td>酒店名称</td>
			<td>星级</td>
			<td>地址</td>
			<td>总台电话</td>
			<td>联系人-电话</td>
			<td>操作</td>
		</tr>
		<%int hotelRows=rd.getTableRowsCount("TA_HOTELs");for(int h=0;h<hotelRows;h++){%>
		<tr>
			<td><input type="checkbox" id="checkboxEle" name="HOTEL/CHECKBOX[<%=h%>]" value="<%=rd.getStringByDI("TA_HOTELs","HOTEL_ID",h) %>"></input></td>
			<td><%=rd.getStringByDI("TA_HOTELs","HOTEL_NAME",h)%></td>
			<td><%=rd.getStringByDI("TA_HOTELs","HOTEL_LEVEL",h)%></td>
			<td><%=rd.getStringByDI("TA_HOTELs","HOTEL_ADDRESS",h)%></td>
			<td><%=rd.getStringByDI("TA_HOTELs","HOTEL_TEL",h)%></td>
			<td><%=rd.getStringByDI("TA_HOTELs","HOTEL_BUSSINESS",h)%>-<%=rd.getStringByDI("TA_HOTELs","HOTEL_BUSSINESS_TEL",h)%></td>
			<td>
				<a href="###" onclick="editHotel('<%=request.getContextPath()%>/getHotelById.?id=<%=rd.getStringByDI("TA_HOTELs","HOTEL_ID",h) %>&DMSM/JDXJ=6&DMSM/ZCLX=7&DMSM/HOTELJGLX=10')">
					<img alt="编辑" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[修改]
				</a>-
				<a href="<%=request.getContextPath() %>/delHotel.?hotel_id=<%=rd.getStringByDI("TA_HOTELs","HOTEL_ID",h)%>" onclick="return confirm('此操作将无法恢复,确定删除吗?')">
					<img alt="删除" src="<%=request.getContextPath()%>/images/del-icon.gif">&nbsp;[删除]
				</a>
			</td>
		</tr>
		<%} %>
	</table>
</div>
<div id="page">
	<%String url=request.getContextPath()+"/baseMng/hotel/getAllHotel.?TA_HOTEL@pageSize=10&TA_HOTEL@pageNO=";%>
	<span title="首页" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="上一页" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
					共<%=rd.getAttr("TA_HOTELs","rowsCount")==null?"0":rd.getAttr("TA_HOTELs","rowsCount") %> 条记录，
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