<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>   
 <%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("ta_g_next_agencys")>0){
     pageNO=Integer.parseInt((String)rd.getAttr("ta_g_next_agency", "pageNO"));
	 pageSize=Integer.parseInt((String)rd.getAttr("ta_g_next_agency", "pageSize"));
		totalPage=(Integer)rd.getAttr("ta_g_next_agencys", "pagesCount");
		rowsCount = (Integer)rd.getAttr("ta_g_next_agencys", "rowsCount");
	}
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>



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
        height: 500,// 500,
        width: width || 580,
        fullscreen: true,
        show_loading: false,
        callback_fn: callback_fn
    }
    var win = new GB_Window(options);
    return win.show(url);
}
</script>




<title></title>
</head>
<body>
<form  name="hotel_form" method="post">
<div id="lable"><span>应付账款列表</span></div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td id="selectAll"><input type="checkbox" id="checkboxTop"></input></td>
			<td>编号</td>
			<td>团号</td>
			<td>地接社名称</td>
			<td>应付账款</td>
			<td>已付账款</td>
			<td>操作</td>
		</tr>
	<%int monRows=rd.getTableRowsCount("ta_g_next_agencys");for(int a=0;a<monRows;a++){ %>	
		<tr>
			<td><input type="checkbox" id="checkboxEle"  ></input></td>
			<td><%=rd.getStringByDI("ta_g_next_agencys","id",a) %></td>
			<td><%=rd.getStringByDI("ta_g_next_agencys","groupid",a) %></td>
				<td><%=rd.getStringByDI("ta_g_next_agencys","cmpny_name",a) %></td>
			<td>￥<%=rd.getStringByDI("ta_g_next_agencys","PAY_MONEY",a)%></td>
			<td>￥<%=rd.getStringByDI("ta_g_next_agencys","YFZK",a)%></td>
			<td>
				<a href="###" onclick="javascript:GB_myShow('','<%=request.getContextPath() %>/to_payMoney.?id=<%=rd.getStringByDI("ta_g_next_agencys","id",a) %>')"><img alt="" src="<%=request.getContextPath() %>/images/edit.gif">&nbsp;[付款]</a>
			</td>
		</tr>
		<%} %>
	</table>
</div>
<div id="page">
	<%String url="getNeedMon.?TA_G_NEXT_AGENCY@pageSize=10&TA_G_NEXT_AGENCY@pageNO=";%>
	<span title="首页" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="上一页" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
					共<%=rd.getAttr("ta_g_next_agencys","rowsCount")==null?"0":rd.getAttr("ta_g_next_agencys","rowsCount") %> 条记录，
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