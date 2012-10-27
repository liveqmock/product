<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("groupList")>0){
     pageNO=Integer.parseInt((String)rd.getAttr("TA_GROUP_BASE", "pageNO"));
	 pageSize=Integer.parseInt((String)rd.getAttr("TA_GROUP_BASE", "pageSize"));
		totalPage=(Integer)rd.getAttr("groupList", "pagesCount");
		rowsCount = (Integer)rd.getAttr("groupList", "rowsCount");
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
        width: width || 800,
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
	document.zwfx_form.action="zwfx_getAllGroup.?TA_GROUP_BASE@pageNO=1&TA_GROUP_BASE@pageSize=10";
	document.zwfx_form.submit();
}
</script>
<title>团队账务分析列表</title>
</head>
<body>
	<form  name="zwfx_form" method="post">
<div id="lable"><span>团队账务分析</span></div>
<div  id="thisSelect-table" >
	<table>
		<tr>
			<td rowspan="3" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td align="right">团号：</td>
			<td><input type="text" name="th" /></td>
			<td><a href="###" onclick="findByLike();"><img alt="搜索" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td id="selectAll"><input type="checkbox" id="checkboxTop"></input></td>
			<td>团号</td>
			<td>线路名称</td>
			<td>起止日期</td>
			<td>天数</td>
			<td>人数</td>
			<td>操作</td>
		</tr>
		<%int rows=rd.getTableRowsCount("groupList");for(int h=0;h<rows;h++){%>
		<tr>
			<td><input type="checkbox" id="checkboxEle" name="HOTEL/CHECKBOX[<%=h%>]" value="<%=rd.getStringByDI("TA_HOTELs","HOTEL_ID",h) %>"></input></td>
			<td><%=rd.getStringByDI("groupList","g_id",h)%></td>
			<td><%=rd.getStringByDI("groupList","line_name",h)%></td>
			<td><%=rd.getStringByDI("groupList","begin_date",h).substring(0,10)%>至<%=rd.getStringByDI("groupList","end_date",h).substring(0,10)%></td>
			<td><%=rd.getStringByDI("groupList","days",h)%></td>
			<td>成人:<%=rd.getStringByDI("groupList","crrs",h)%>+儿童:<%=rd.getStringByDI("groupList","etrs",h)%></td>
			<td>
				<a href="###" onclick="javascript:GB_myShow('','to_showTDZW.?id=<%=rd.getStringByDI("groupList","id",h) %>')">
					<img alt="编辑" src="<%=request.getContextPath()%>/images/Print.gif">&nbsp;[查看明细并打印]
				</a>
			</td>
		</tr>
		<%} %>
	</table>
</div>
<div id="page">
	<%String url="zwfx_getAllGroup.?TA_GROUP_BASE@pageSize=10&TA_GROUP_BASE@pageNO=";%>
	<span title="首页" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="上一页" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
					共<%=rd.getAttr("groupList","rowsCount")==null?"0":rd.getAttr("groupList","rowsCount") %> 条记录，
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