<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
 <%@include file="/common.jsp" %>
   <%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("TA_SHOPPOINTs")>0){
     pageNO=Integer.parseInt((String)rd.getAttr("TA_SHOPPOINT", "pageNO"));
	 pageSize=Integer.parseInt((String)rd.getAttr("TA_SHOPPOINT", "pageSize"));
		totalPage=(Integer)rd.getAttr("TA_SHOPPOINTs", "pagesCount");
		rowsCount = (Integer)rd.getAttr("TA_SHOPPOINTs", "rowsCount");
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
<script type="text/javascript">
function enableInput(){
	var rs = false;
	var objs = document.shop_form.elements;
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
	var e=enableInput();
	if(e==false){
		return false;
	}else if(confirm('此操作将无法恢复,确认删除所勾选项?')){
			document.shop_form.action="batchDelShop.?";
			document.shop_form.submit();
	}
	
}
function getShopByName(){
	document.shop_form.action="getShopByName.?TA_SHOPPOINT@pageNO=1&TA_SHOPPOINT@pageSize=10";
	document.shop_form.submit();
}
//添加购物点信息
function newShop(url){
	window.location.href=url;
}
//修改购物点信息
function editShop(url){
	window.location.href=url;
}
</script>
<title></title>
</head>
<body>
<form  name="shop_form" method="post">
<div id="lable"><span>购物点基础信息维护</span></div>
	<div id="top-select">
	<div class="select-div" >
	  <span class="text" id="select-condition">查询条件</span>
	</div>
		<div class="select-div" onclick="newShop('<%=request.getContextPath()%>/to_addShop.?DMSM/KHYH=8&city_id=<%=rd.getStringByDI("TA_SHOPPOINT","city_id",0) %>')"><span id="add-icon"></span>
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
	购物点名称：<input type="text" name="name" />&nbsp;
		  <input type="button" value="查询" class="bnt" onClick="getShopByName();"/>
	</div>
	</div></div></div>
	<input type="image" src="<%=request.getContextPath() %>/images/if-select-img/close.gif" class="jqmdX jqmClose" />
</div>

<div id="list-table">
	<input type="hidden" value="<%=rd.getStringByDI("TA_SHOPPOINT","city_id",0)%>" name="city_id"/>
	<table class="datas" >
		<tr id="first-tr">
			<td id="selectAll"><input type="checkbox" id="checkboxTop"></input></td>
			<td>购物点名称</td>
			<td>地址</td>
			<td>总负责人-手机</td>
			<td>操作</td>
		</tr>
		<%int hotelRows=rd.getTableRowsCount("TA_SHOPPOINTs");for(int h=0;h<hotelRows;h++){%>
		<tr>
			<td><input type="checkbox" id="checkboxEle" name="shop/CHECKBOX[<%=h%>]" value="<%=rd.getStringByDI("TA_SHOPPOINTs","SHOP_POINT_ID",h) %>"></input></td>
			<td><%=rd.getStringByDI("TA_SHOPPOINTs","CMPNY_NAME",h)%></td>
			<td><%=rd.getStringByDI("TA_SHOPPOINTs","CMPNY_ADDRESS",h)%></td>
			<td><%=rd.getStringByDI("TA_SHOPPOINTs","CHIEF_NAME",h)%>-<%=rd.getStringByDI("TA_SHOPPOINTs","CHIEF_MOBILE",h)%></td>
			<td>
				<a href="###" onclick="editShop('<%=request.getContextPath()%>/getShopById.?TA_SHOPPOINT/SHOP_POINT_ID=<%=rd.getStringByDI("TA_SHOPPOINTs","SHOP_POINT_ID",h) %>&DMSM/KHYH=8&city_id=<%=rd.getStringByDI("TA_SHOPPOINT","city_id",0) %>')">
					<img alt="编辑" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[修改]
				</a>-
				<a href="<%=request.getContextPath() %>/delShop.?id=<%=rd.getStringByDI("TA_SHOPPOINTs","SHOP_POINT_ID",h) %>&city_id=<%=rd.getStringByDI("TA_SHOPPOINT","city_id",0) %>" onclick="return confirm('此操作将无法恢复,确定删除吗?')">
					<img alt="删除" src="<%=request.getContextPath()%>/images/del-icon.gif">&nbsp;[删除]
				</a>-
				<a href="###" onclick="javascript:GB_myShow('','<%=request.getContextPath()%>/showRTF.?id=<%=rd.getStringByDI("TA_SHOPPOINTs","SHOP_POINT_ID",h)%>')">维护人头费</a>
			</td>
		</tr>
		<%} %>
	</table>
</div>
<div id="page">
	<%String url=request.getContextPath()+"/baseMng/shop/getAllShop.?TA_SHOPPOINT@pageSize=10&TA_SHOPPOINT@pageNO=";%>
	<span class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")' title="首页"></span>		
	<span class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")' title="上一页"></span>
	<span class="pagination-sum">
					第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
					共<%=rd.getAttr("TA_SHOPPOINTs","rowsCount")==null?"0":rd.getAttr("TA_SHOPPOINTs","rowsCount") %> 条记录，
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