<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 0;
	if(rd.getTableRowsCount("ztGroupList")>0){
		
	   	pageNO=Integer.parseInt(rd.getString("pageNO"));
		pageSize=Integer.parseInt(rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("ztGroupList", "pagesCount");
		rowsCount = (Integer)rd.getAttr("ztGroupList", "rowsCount");
	}
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	function findByLike(){
		document.guide_form.action="ztQueryAllGroupList.?pageSize=10&pageNO=1";
		document.guide_form.submit();
		}
</script>
<title></title>
</head>
<body>
<form  name="guide_form" method="post">
<div id="lable"><span>组团业务列表</span></div>
<div  id="thisSelect-table" >
	<table>
		<tr>
			<td rowspan="3" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td align="right">团号：</td>
			<td><input type="text" name="th" /></td>
			<td align="right">发团日期：</td>
			<td><input type="text" name="ftrq" onFocus="WdatePicker({isShowClear:false,readOnly:true});" /></td>
			<td><a href="###" onclick="findByLike()"><img alt="搜索" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td id="selectAll"><input type="checkbox" id="checkboxTop"></input></td>
			<td>团号</td>
			<td>线路名称</td>
			<td>发团日期</td>
			<td>返团日期</td>
			<td>团人数</td>
			<td>操作</td>
		</tr>
	<%int groupRows=rd.getTableRowsCount("ztGroupList");for(int a=0;a<groupRows;a++){ String groupID = rd.getStringByDI("ztGroupList","id",a); %>	
		<tr>
			<td><input type="checkbox" id="checkboxEle"  ></input></td>
			<td><%=groupID %></td>
			<td><%=rd.getStringByDI("ztGroupList","xlmc",a) %></td>
			<td><%=rd.getStringByDI("ztGroupList","begin_date",a).substring(0,10)%></td>
			<td><%=rd.getStringByDI("ztGroupList","end_date",a).substring(0,10) %></td>
			<td>最大人数：人&nbsp;可成团人数：人</br>
				已收人数：人&nbsp;剩余人数：人
			</td>
			<td>
				<a href="<%=request.getContextPath() %>/main/ztPlans/ztInitMasterPlan.?TA_ZT_GROUP/ID=<%=groupID %>&TA_ZT_JHHOTEL/TID=<%=groupID %>&TA_TDJDXXZJB_ZT/TID=<%=groupID %>&TA_ZT_JHCT/TID=<%=groupID %>&TA_ZT_JH/TID=<%=groupID %>&TA_ZT_JHPW/TID=<%=groupID %>&TA_ZT_JHCL/TID=<%=groupID %>&TA_ZT_JHJD/TID=<%=groupID %>&TA_ZT_JHDJ/TID=<%=groupID %>&TA_ZT_JHGW/TID=<%=groupID %>&TA_ZT_JHJIAD/TID=<%=groupID %>&TA_ZT_JHDY/TID=<%=groupID %>&isCheck=Y"><img alt="业务计调" src="<%=request.getContextPath() %>/images/edit.gif">&nbsp;[业务计调]</a>
			</td>
		</tr>
		<%} %>
	</table>
</div>
<div id="page">
	<%String url="ztQueryAllGroupList.?pageSize=10&pageNO=";%>
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