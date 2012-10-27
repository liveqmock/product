<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
 <%@include file="/common.jsp" %>
 <%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("queryGuideAllList")>0){
		pageNO=Integer.parseInt(rd.getString("pageNO"));
		pageSize=Integer.parseInt(rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("queryGuideAllList", "pagesCount");
		rowsCount = (Integer)rd.getAttr("queryGuideAllList", "rowsCount");
	}
%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>导游报账列表</title>
<link href="<%=request.getContextPath() %>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<title>导游报账列表</title>
<style type="text/css">
* { margin:0; padding:0; word-wrap:break-word; word-break:break-all; }
a { text-decoration: none; }

ul, li { list-style:none; }
html { overflow:-moz-scrollbars-vertical; } /* 在Firefox下始终显示滚动条*/

/*导航样式开始*/
#navigation { 
	width:140px;
	padding:8px 0px 0px 0px; 
	margin:0px; 
	background:none;
	height:28px;	
}
#navigation ul li { 
	margin-left:5px;
	margin-right:14px;
	float:left; 
	
	position: relative ;
}
#navigation ul li a { 
	display:block;
	padding:2px 8px; 
	background:#EEEEEE; 
}
#navigation ul li a:hover { 
	background:none; 
	color:green;
	font-weight:700;
}
#navigation ul li ul{
	background-color: #88C366;
	position: absolute;
	width: 100px;
	overflow:hidden;
	display:none;
}
#navigation ul li:hover ul{
	background-color: #88C366;
	position: absolute;
	width: 100px;
	display:block;
	
}
#navigation ul li ul li{
	border-bottom: 2px solid #BBB;
	text-align: center;
	width: 100%;
}
/*导航样式结束*/
</style>
<script type="text/javascript">
function findByLike(){
	document.dj_guide_form.Action="findByLike.?&pageSize=10&pageNO=1 ";
	document.dj_guide_form.submit();
}
</script>
</head>

<body>
<form  name="dj_guide_form" method="post">
<div id="lable"><span>导游报账列表</span></div>
<div  id="thisSelect-table" >
	<table>
		<tr>
			<td rowspan="3" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td align="right">团号：</td>
			<td><input type="text" name="groupID" /></td>
			<td align="right">发团日期：</td>
			<td><input type="text" name="bDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" /></td>
			<td align="right">线路名称：</td>
			<td><input type="text"  name="xlmc"/></td>
			<td><a href="###" onclick="findByLike()"><img alt="搜索" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td>团号</td>
			<td>线路名称</td>
			<td>发团日期</td>
			<td>返团日期</td>
			<td>报销状态</td>
			<td>操作</td>
		</tr>
		<%
		int rows = rd.getTableRowsCount("queryGuideAllList");
		for(int i=0;i<rows;i++) {
			int index = 9999 - i;
			String ID = rd.getStringByDI("queryGuideAllList","ID",i);//团号
			String XLID = rd.getStringByDI("queryGuideAllList","XLID",i);//团号
			String XLMC = rd.getStringByDI("queryGuideAllList","xlmc",i);//线路名称
			String BEGIN_DATE = rd.getStringByDI("queryGuideAllList","BEGIN_DATE",i);//发团日期
			String END_DATE = rd.getStringByDI("queryGuideAllList","END_DATE",i);//返团日期
			String STATE = rd.getStringByDI("queryGuideAllList","STATE",i);//报销状态
			String YJZT = rd.getStringByDI("queryGuideAllList","YJZT",i);//审批状态
			int state = Integer.parseInt(STATE);//团状态
			String action = ""; 
		%>
		<tr>
            <td><%=ID %></td>
	  		<td>
	  			<img alt="热点线路" src="<%=request.getContextPath()%>/images/hot3.gif"/>&nbsp;
	  			<a href="###" onclick="" title="<%=XLMC %>"><%=XLMC.length()<=20?XLMC:XLMC.substring(0,20)+"..."  %></a>
	  		</td>
	  		<td><%=BEGIN_DATE %></td>
	  		<td><%=END_DATE %></td>
	  		<td><% if("1".equals(STATE)){out.print("待计划");}else if("4".equals(STATE)){out.print("未报销/修改");}else if("5".equals(STATE)){out.print("已报销/待审核");action="view";}else if("6".equals(STATE)){out.print("已报销");action="view";} %></td>
	  		<td>
	  			<div id="navigation">
			  	  <ul>
			  	  <%if("4".equals(STATE)){ %>
				  	<li style="z-index:<%=index%>">
					  	<a href="<%=request.getContextPath()%>/maindj/bx/djInitBxPage.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&action=<%=action%>&dmsm/jtgj=2&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>">
					  	   <img alt="导游报账" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[导游报账]
					  	 </a>
						<ul>
							 <li><a href="<%=request.getContextPath()%>/maindj/bx/djInitBxPage.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&action=<%=action%>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&md=0&mdName=hotelMd&mdUrl=djAjaxHotelPlanInfo.?groupId=<%=ID%>">酒店费用</a></li>
							 <li><a href="<%=request.getContextPath()%>/maindj/bx/djInitBxPage.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&action=<%=action%>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&md=1&mdName=dinnerMd&mdUrl=djAjaxDinnerPlanInfo.?groupId=<%=ID%>">餐厅费用</a></li>
							 <li><a href="<%=request.getContextPath()%>/maindj/bx/djInitBxPage.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&action=<%=action%>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&md=2&mdName=ticketMd&mdUrl=djAjaxTicketPlanInfo.?groupId=<%=ID%>">票务费用</a></li>
							 <li><a href="<%=request.getContextPath()%>/maindj/bx/djInitBxPage.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&action=<%=action%>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&md=3&mdName=carMd&mdUrl=djAjaxCarPlanInfo.?groupId=<%=ID%>,dmsm/cllx=13">车辆费用</a></li>
							 <li><a href="<%=request.getContextPath()%>/maindj/bx/djInitBxPage.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&action=<%=action%>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&md=4&mdName=sceneryMd&mdUrl=djAjaxSceneryPlanInfo.?groupId=<%=ID%>">景点费用</a></li>
							 <li><a href="<%=request.getContextPath()%>/maindj/bx/djInitBxPage.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&action=<%=action%>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&md=5&mdName=travelMd&mdUrl=djAjaxTravelPlanInfo.?groupId=<%=ID%>">地接费用</a></li>
							 <li><a href="<%=request.getContextPath()%>/maindj/bx/djInitBxPage.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&action=<%=action%>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&md=6&mdName=shopMd&mdUrl=djAjaxShopPlanInfo.?groupId=<%=ID%>">购物费用</a></li>
							 <li><a href="<%=request.getContextPath()%>/maindj/bx/djInitBxPage.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&action=<%=action%>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&md=7&mdName=plusMd&mdUrl=djAjaxPlusPlanInfo.?groupId=<%=ID%>">加点费用</a></li>
							 <li><a href="<%=request.getContextPath()%>/maindj/bx/djInitBxPage.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&action=<%=action%>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&md=8&mdName=otherMd&mdUrl=djAjaxOtherPlanInfo.?groupId=<%=ID%>">其他费用</a></li>
							 <li><a href="<%=request.getContextPath()%>/maindj/bx/djInitBxPage.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&action=<%=action%>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&md=9&mdName=guideMd&mdUrl=djAjaxGuidePlanInfo.?groupId=<%=ID%>">导游费用</a></li>
						</ul>
					</li>
					<%
					}
			  		if("5".equals(STATE)){
			  	  	%>
			  	  		<li style="z-index:<%=index%>">
					  		<a href="djInitAllSHBx.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&action=view"><img alt="查看" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[查看]</a>	
					  	</li>
					<%} %>
				  </ul>
				</div>
            </td>
         </tr>	
	<%} %>		
			
	</table>
</div>
<div id="page">
	<%String url=request.getContextPath()+"/maindj/bx/bxGuideAllList.?pageSize=10&pageNO=";%>
	<span title="首页" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="上一页" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
					共<%=rd.getAttr("queryGuideAllList","rowsCount")==null?"0":rd.getAttr("queryGuideAllList","rowsCount") %> 条记录，
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
