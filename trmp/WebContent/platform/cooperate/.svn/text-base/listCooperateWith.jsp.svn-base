<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("rsTravelAgencys")>0){
		
	   	pageNO=Integer.parseInt(rd.getString("pageNO"));
		pageSize=Integer.parseInt(rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("rsTravelAgencys", "pagesCount");
		rowsCount = (Integer)rd.getAttr("rsTravelAgencys", "rowsCount");
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/cooperate/enterprise.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<title>合伙伙伴列表</title>
<script type="text/javascript">
$(function(){
	$(".prolist:odd").css("background-color", "#fafafa");
	
});
function findByLike(){
	document.djGroupPrint_form.action.name="<%=request.getContextPath()%>/platform/cooperate/cooperateList.?pageNO=1&pageSize=10";
	document.djGroupPrint_form.submit();
}
</script>

</head>

<body>
<form  name="djGroupPrint_form" method="post">
<div id="lable"><span>合作伙伴列表</span></div>
<div  id="thisSelect-table" >
	<table>
		<tr>
			<td rowspan="3" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td align="right">合作社名称：</td>
			<td><input type="text" name="groupId" /></td>
			<td align="right">发团日期：</td>
			<td><input type="text" name="bDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" /></td>
			<td><a href="###" onclick="findByLike();"><img alt="搜索" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<%
	int rows=rd.getTableRowsCount("rsTravelAgencys");
	for(int i=0;i<rows;i++){
		String curr=rd.getStringByDI("rsTravelAgencys","curr",i);
		String ref_org = rd.getStringByDI("rsTravelAgencys","ref_org",i);
		String ref_name=rd.getStringByDI("rsTravelAgencys","ref_name",i);
		String CMP_INTRO = rd.getStringByDI("rsTravelAgencys","CMP_INTRO",i);
		String registerdate = rd.getStringByDI("rsTravelAgencys","registerdate",i);
		String provincename = rd.getStringByDI("rsTravelAgencys","provincename",i);
%>
<div class="prolist">
	<div class="act">
		<a href="#"
			target="_blank" rel="nofollow" class="inquire"><img
			src="<%=request.getContextPath() %>/images/contact_now_s_cn.gif" alt="发联系信" /></a>
	</div>
	<div class="cntC">
		<p>
			<strong><a href="<%=request.getContextPath() %>/platform/cooperate/traPlatQuery.?hrorganization/orgid=<%=ref_org %>&flag=c"><%=ref_name %></a></strong>
			<span class="gray">[<%=registerdate %>]</span>
		</p>
		<p><%=CMP_INTRO.length()<=100?CMP_INTRO:CMP_INTRO.substring(0,100)+"..."  %></p>
		<p>
			<img src="<%=request.getContextPath() %>/images/gold_member_s_cn.gif" alt="金牌会员" border="0" class="valign" />
			<img src="<%=request.getContextPath() %>/images/audited_cn.gif" alt="认证供应商" border="0" class="valign" />
			<img src="<%=request.getContextPath() %>/images/certification_member_s_cn.gif" alt="实名会员" border="0" class="valign" />
		</p>
	</div>
	<div class="cgray"><%=provincename %></div>
</div>
<%	} %>

<div id="page">
	<%String url=request.getContextPath()+"/platform/cooperate/cooperateList.?pageSize=10&pageNO=";%>
	<span class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
					共<%=rowsCount %> 条记录，
					每页 <%=pageSize%>条
	</span>
	<span class="next-page" onclick='query("<%=pageNO+1>totalPage?totalPage:pageNO+1 %>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="last-page" onclick='query("<%=totalPage%>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="go-to-page" >
		跳转到第：<input type="text" id="gotopage"/> 页
	</span>
	<span class="go-to-page-icon" onclick='go("<%=totalPage%>","<%=(int)Math.min(pageNO,totalPage)%>","<%=url %>")'></span>
</div>
</form>
</body>
</html>