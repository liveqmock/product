<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
 <%@include file="/common.jsp" %>
 <%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("rsQingDanHotelList")>0){
		pageNO=Integer.parseInt(rd.getString("pageNO"));
		pageSize=Integer.parseInt(rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("rsQingDanHotelList", "pagesCount");
		rowsCount = (Integer)rd.getTableRowsCount("rsQingDanHotelList");
	}
%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>地接财务管理</title>
<link href="<%=request.getContextPath() %>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<title>酒店签单列表</title>


<script type="text/javascript">
        var GB_ROOT_DIR = "<%=request.getContextPath()%>/greybox/";
</script>

    <script type="text/javascript" src="<%=request.getContextPath()%>/greybox/AJS.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/greybox/AJS_fx.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/greybox/gb_scripts.js"></script>
    <link href="<%=request.getContextPath()%>/greybox/gb_styles.css" rel="stylesheet" type="text/css" media="all" />


<script type="text/javascript">
GB_myShow = function(caption, url, /* optional */ height, width, callback_fn) {
    var options = {
        caption: caption,
        height: 500,// || 500,
        width: width || 700,
        fullscreen: true,
        show_loading: false,
        callback_fn: callback_fn
    }
    var win = new GB_Window(options);
    return win.show(url);
}
</script>





<script type="text/javascript">

function findByLike(){
	document.dj_guide_form.Action="getQingdanBylike.?&pageSize=10&pageNO=1";
	document.dj_guide_form.submit();
}
</script>
</head>

<body>
<form  name="dj_guide_form" method="post">
<div id="lable"><span>酒店签单列表</span></div>
<div  id="thisSelect-table" >
	<table>
		<tr>
			<td rowspan="3" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td align="right">团号：</td>
			<td><input type="text" name="groupID" /></td>
			<td align="right">酒店名称：</td>
			<td><input type="text"  name="jdmc"/></td>
			<td><a href="###" onclick="findByLike()"><img alt="搜索" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td>团号</td>
			<td>酒店名称</td>
			<td>酒店地址</td>
			<td>签单合计</td>
			<td>已清款额</td>
			<td>操作</td>
		</tr>
		<%
		int rows = rd.getTableRowsCount("rsQingDanHotelList");
		for(int i=0;i<rows;i++) {
			String tid = rd.getStringByDI("rsQingDanHotelList","id",i);//团号
			String id = rd.getStringByDI("rsQingDanHotelList","hotel_id",i);//酒店号号
			String city = rd.getStringByDI("rsQingDanHotelList","hotel_name",i);//城市
			String jd = rd.getStringByDI("rsQingDanHotelList","hotel_address",i);//酒店
			String qdxj = rd.getStringByDI("rsQingDanHotelList","qdxj",i);//签单小计
			String hkje=rd.getStringByDI("rsQingDanHotelList","hkje",i);
		%>
		<tr>
            <td><%=tid %></td>
	  		<td><%=city %></td>
	  		<td><%=jd %></td>
	  		<td><%=qdxj %>元</td>
	  		<td><%=hkje %>元</td>	  
	  		<td>
	  			<a href="###" onclick="javascript:GB_myShow('','/trm/maindj/financeMng/jsJDqingdan.?ta_dj_qdqkjl4hotel/JDID=<%=id %>&ta_dj_qdqkjl4hotel/TID=<%=tid %>','400','400','')" >
           		<img alt="编辑" src="<%=request.getContextPath()%>/images/edit.gif">[结算签单]&nbsp;</a>
           		<a href="###" onclick="javascript:GB_myShow('','/trm/maindj/financeMng/queryQDlist.?ta_dj_qdqkjl4hotel/JDID=<%=id %>&ta_dj_qdqkjl4hotel/TID=<%=tid %>','400','600','')" >
           		<img alt="查看" src="<%=request.getContextPath()%>/images/edit.gif">[查看清款清单]&nbsp;</a>
            </td>
         </tr>	
	<%} %>		
			
	</table>
</div>
<div id="page">
	<%String url=request.getContextPath()+"/maindj/financeMng/allQiandanList.?pageSize=10&pageNO=";%>
	<span title="首页" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="上一页" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
					共<%=(Integer)rd.getTableRowsCount("rsQingDanHotelList")>0?(Integer)rd.getTableRowsCount("rsQingDanHotelList"):0 %> 条记录，
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