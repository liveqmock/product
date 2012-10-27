<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("jdInfo")>0){
     pageNO=Integer.parseInt((String)rd.getAttr("TA_GROUP_BASE", "pageNO"));
	 pageSize=Integer.parseInt((String)rd.getAttr("TA_GROUP_BASE", "pageSize"));
		totalPage=(Integer)rd.getAttr("jdInfo", "pagesCount");
		rowsCount = (Integer)rd.getAttr("jdInfo", "rowsCount");
	}
%>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>

<script type="text/javascript">
	function findByLike(){
		document.jd_form.action="cw_jdfyfx.?TA_GROUP_BASE@pageNO=1&TA_GROUP_BASE@pageSize=10";
		document.jd_form.submit();
		}
</script>
<title>加点费用分析</title>
</head>
<body>
	<form  name="jd_form" method="post">
<div id="lable"><span>加点费用分析</span></div>
<div  id="thisSelect-table" >
	<table>
		<tr>
			<td rowspan="3" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td align="right">团号：</td>
			<td><input type="text" name="th" /></td>
			<td>起止日期：</td>
			<td><input type="text" name="bdate" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/>至<input type="text" name="edate" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
			<td><a href="###" onclick="findByLike();"><img alt="搜索" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td>团号</td>
			<td>加点名称</td>
			<td>消费额</td>
			<td>人数</td>
			<td>公司提成</td>
			<td>导游提成</td>
			<td>司机提成</td>
		</tr>
		<%
		int rows=rd.getTableRowsCount("jdInfo");
		int zj=0;
		for(int a=0;a<rows;a++){%>
		<tr>
			<td><%=rd.getStringByDI("jdInfo","g_id",a) %></td>
			<td><%=rd.getStringByDI("jdInfo","pro",a) %>&gt;&gt;<%=rd.getStringByDI("jdInfo","city",a) %>&gt;&gt;<%=rd.getStringByDI("jdInfo","cmpny_name",a) %></td>
			<td><%=rd.getStringByDI("jdInfo","xfe",a) %></td>
			<td><%=rd.getStringByDI("jdInfo","rs",a) %></td>
			<td><%=rd.getStringByDI("jdInfo","yjgs",a) %></td>
			<td><%=rd.getStringByDI("jdInfo","dytc",a) %></td>
			<td><%=rd.getStringByDI("jdInfo","sjtc",a) %></td>
		</tr>
		<%
		zj+=Integer.parseInt(rd.getStringByDI("jdInfo","xfe",a));
		}
		%>
	</table>
</div>
<div id="list-table" style=""><span style="font-size: 14px;">总计:<font color="red"><%=zj%></font>元</span></div>
<div id="page">
	<%String url="cw_jdfyfx.?TA_GROUP_BASE@pageSize=10&TA_GROUP_BASE@pageNO=";%>
	<span title="首页" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="上一页" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
					共<%=rd.getAttr("jdInfo","rowsCount")==null?"0":rd.getAttr("jdInfo","rowsCount") %> 条记录，
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
