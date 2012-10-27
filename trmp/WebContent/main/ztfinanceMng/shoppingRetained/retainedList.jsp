<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
 <%@include file="/common.jsp" %>
 <%
	int pageNO=1;
	int pageSize=0;
	int totalPage =1;
	int rowsCount = 0;
	if(rd.getTableRowsCount("queryAllLCshopList")>0){
		pageNO=Integer.parseInt(rd.getString("pageNO"));
		pageSize=Integer.parseInt(rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("queryAllLCshopList", "pagesCount");
		rowsCount = (Integer)rd.getAttr("queryAllLCshopList", "rowsCount");
	}
%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>组团财务管理</title>
<link href="<%=request.getContextPath() %>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>


<script type="text/javascript">


function findByLike(){
	document.dj_guide_form.action="AllLCshopList.?pageSize=10&pageNO=1";
	document.dj_guide_form.submit();
}
function addLCshop(gwdid,tid)
{
	window.open("trm/main/ztfinanceMng/getLcShopByTid.?TA_ZT_LC4SHOP/GWDID="+gwdid+"&TA_ZT_LC4SHOP/TID="+tid,"newwindow","height=400,width=400,top=180,left=500,toolbar=no,menubar=no,scrollbars=no,resizablr=no,locatiog=no,status=no");
}
function viewLCshop(gwdid,tid)
{
	window.open("trm/main/ztfinanceMng/queryLCshoplist.?TA_ZT_LC4SHOP/GWDID="+gwdid+"&TA_ZT_LC4SHOP/TID="+tid,"newwindow2","height=400,width=600,top=180,left=400,toolbar=no,menubar=no,scrollbars=yes,resizablr=no,locatiog=no,status=no");
}
</script>
</head>

<body>
<form  name="dj_guide_form" method="post">
<div id="lable"><span>购物点留存清款列表</span></div>
<div  id="thisSelect-table" >
	<table>
		<tr>
			<td rowspan="2" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td align="right">团&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
			<td><input type="text" name="groupID" /></td>
			<td>清款状态：</td>
			<td>
				<select name="qkzt">
					<option value="">***请选择***</option>
					<option value="N">未清款</option>
					<option value="Y">已清款</option>
				</select>
			</td>
			<td>购物点名称：</td>
			<td>  	
			    <select name="sfid" id="sfid0" USEDATA="dataSrc0" SUBCLASS="1"></select>
			    <select name="csid" id="csid0" USEDATA="dataSrc0" SUBCLASS="2"></select>
			  	<select name="gwdid" id="gwdid0" USEDATA="dataSrc0" SUBCLASS="3" ></select>
			</td>
		</tr>
		<tr>
			<td>开始时间：</td>
			<td><input type="text" name="dB" onFocus="WdatePicker({isShowClear:false,readOnly:false});"/></td>
			<td>结束时间：</td>
			<td><input type="text" name="dE" onFocus="WdatePicker({isShowClear:false,readOnly:false});"/></td>
			<td><a href="###" onclick="findByLike()"><img alt="搜索" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td>团号</td>
			<td>购物点名称</td>
			<td>留存合计</td>
			<td>已清留存金额</td>
			<td>操作</td>
		</tr>
		<%
		int rows = rd.getTableRowsCount("queryAllLCshopList");
		for(int i=0;i<rows;i++) {
			String tid = rd.getStringByDI("queryAllLCshopList","id",i);//团号
			String gwdid = rd.getStringByDI("queryAllLCshopList","gwdid",i);//购物点ID
			String gwdmc = rd.getStringByDI("queryAllLCshopList","cmpny_name",i);//购物点名称
			String qdxj = rd.getStringByDI("queryAllLCshopList","gslcxj",i);//签单小计
			String hkje=rd.getStringByDI("queryAllLCshopList","hkje",i);
			String qkzt=rd.getStringByDI("queryAllLCshopList","qkzt_lc",i);
		%>
		<tr>
            <td><%=tid %></td>
	  		<td><%=gwdmc %></td>
	  		<td><%=qdxj %>元</td>
	  		<td><%=hkje %>元</td>	  
	  		<td><%if("N".equals(qkzt)) {%>
	  			<a href="###" onclick="addLCshop('<%=gwdid %>','<%=tid %>');"  >
           		<img alt="编辑" src="<%=request.getContextPath()%>/images/edit.gif">[结算留存款]&nbsp;</a>
           		<% } %>
           		<a href="###" onclick="viewLCshop('<%=gwdid %>','<%=tid %>');"  >
           		<img alt="查看" src="<%=request.getContextPath()%>/images/edit.gif">[查看留存清款单]&nbsp;</a>
            </td>
         </tr>	
	<%} %>		
			
	</table>
</div>
<div id="page">
	<%String url=request.getContextPath()+"/maindj/financeMng/AllLCshopList.?pageSize=10&pageNO=";%>
	<span title="首页" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="上一页" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
					共<%=rowsCount %> 条记录，
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

<script type="text/javascript">

	var linkage = new Linkage("dataSrc0", "<%=request.getContextPath()%>/main/data/Shopz.xml");
	linkage.init();
	
</script>