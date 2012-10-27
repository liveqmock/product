<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 0;
	if(rd.getTableRowsCount("rsDSPList")>0){
		
	   	pageNO=Integer.parseInt(rd.getString("pageNO"));
		pageSize=Integer.parseInt(rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("rsDSPList", "pagesCount");
		rowsCount = (Integer)rd.getAttr("rsDSPList", "rowsCount");
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link href="<%=request.getContextPath() %>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<title>业务审核</title>
<style type="text/css">
* { margin:0; padding:0; word-wrap:break-word; word-break:break-all; }
a { text-decoration: none; }

ul, li { list-style:none; }
html { overflow:-moz-scrollbars-vertical; } /* 在Firefox下始终显示滚动条*/

/*导航样式开始*/
#navigation { 
	width:140px;
	padding:0px; 
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
function queryGroupList(){
	document.djListGroup.action.name="djQueryShPlanList.?pageNO=1&pageSize=10";
	document.djListGroup.submit();
}
function showDjGroupID(ID){
	GB_myShow('审核','djInitAllSHPlan.?TA_DJ_GROUP/ID='+ID+'&TA_TDJDXXZJB/TID='+ID);
}
</script>
</head>

<body>
<form name="djListGroup" method="post">
<div id="lable"><span>业务审核列表</span></div>
<div  id="thisSelect-table" >
	<table>
		<tr>
			<td rowspan="3" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td align="right">团号：</td>
			<td><input type="text" name="groupID" /></td>
			<td align="right">发团日期：</td>
			<td><input type="text" name="bDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" /></td>
			<td align="right">线路名称：</td>
			<td><input type="text" name="xlmc" /></td>
			<td><a href="###" onclick="queryGroupList();"><img alt="搜索" src="<%=request.getContextPath()%>/images/search.gif"></a></td>
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
			<td>流程进度</td>
			<td>操作</td>
		</tr>
		<%
		String orgid = sd.getString("orgid");
			int rows=rd.getTableRowsCount("rsDSPList");
			for(int i=0;i<rows;i++){
				int index = 9999 - i;
				String ID=rd.getStringByDI("rsDSPList","tid",i);
				String XLID = rd.getStringByDI("rsDSPList","XLID",i);
				String XLMC=rd.getStringByDI("rsDSPList","XLMC",i);
				String BEGIN_DATE = rd.getStringByDI("rsDSPList","BEGIN_DATE",i);
				String END_DATE = rd.getStringByDI("rsDSPList","END_DATE",i);
				String flowState = rd.getStringByDI("rsDSPList","STATE",i);
				String isBack = rd.getStringByDI("rsDSPList","isback",i);
				String nextRoleID = rd.getStringByDI("rsDSPList","nextroleid",i);
				//发起人
				String creater = rd.getStringByDI("rsDSPList","creater",i);
				
				String roleID = sd.getString("USERROLEST");
				boolean isTrue = false;
				if(!"".equals(roleID)){
					
					roleID = roleID.substring(1,roleID.length()-1);
					String[] roleIDs = roleID.split(",");
					for(int j=0;j<roleIDs.length;j++){
						if(nextRoleID.equals(roleIDs[j].trim())){
							isTrue = true;
							break;
						}
					}
				}
		%>
		<tr>
			<td><%=ID %></td>
			<td>
			  <img alt="热点线路" src="<%=request.getContextPath()%>/images/hot3.gif"/>&nbsp;
			  <a href="###" onclick="" title="<%=XLMC %>"><%=XLMC.length()<=20?XLMC:XLMC.substring(0,20)+"..."  %></a>
			</td>
			<td><%=BEGIN_DATE %></td>
			<td><%=END_DATE %></td>
			<td><%="end".equals(flowState)?"流程结束":rd.getStringByDI("rsDSPList","nodename",i) %></td>
			<td style="vertical-align: middle;" align="center">
		  	  <div id="navigation">
		  	    <ul>
			  	  <%
			  		if("end".equals(flowState) || creater.equals(sd.getString("userno"))){
				  %>
			  		<li style="z-index:<%=index%>">
			  			<a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/shPlan/djInitAllSHPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=orgid %>&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=orgid %>&TA_DJ_TSPB/TID=<%=ID %>&TA_DJ_TSPB/orgid=<%=orgid %>&action=view&isCheck=Y&TA_DJ_LINEDETAI4G@orderBy=rq"><img alt="查看" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[查看]</a>
			  		</li>
			  	<%
			  	}
			  	if(isTrue == true && !"end".equals(flowState)&&isBack.equals("N")){
			  	%>
			  	<li style="z-index:<%=index%>">
			  			<a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/shPlan/djInitAllSHPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=orgid %>&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=orgid %>&TA_DJ_TSPB/TID=<%=ID %>&TA_DJ_TSPB/orgid=<%=orgid %>&action=authorize&isCheck=Y&TA_DJ_LINEDETAI4G@orderBy=rq"><img alt="审核" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[审核]</a>
			  		</li>
			  	<%
			  	}
			  	//if(creater.equals(sd.getString("userno"))&&isBack.equals("Y")){
			  	%>
			  	<!-- <li style="z-index:<%=index%>">
				  	<a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G@orderBy=RQ">
				  	   <img alt="业务计调" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[业务计调]
				  	 </a>
					 <ul>
						 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=0&mdName=hotelMd&mdUrl=djAjaxHotelInfo.?groupId=<%=ID%>">酒店安排</a></li>
						 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=1&mdName=dinnerMd&mdUrl=djAjaxDinnerInfo.?groupId=<%=ID%>">餐厅安排</a></li>
						 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=2&mdName=ticketMd&mdUrl=djAjaxTicketInfo.?groupId=<%=ID%>">票务安排</a></li>
						 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=3&mdName=carMd&mdUrl=djAjaxCarInfo.?groupId=<%=ID%>,dmsm/cllx=13">车辆安排</a></li>
						 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=4&mdName=sceneryMd&mdUrl=djAjaxSceneryInfo.?groupId=<%=ID%>">景点安排</a></li>
						 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=5&mdName=travelMd&mdUrl=djAjaxTravelInfo.?groupId=<%=ID%>">地接安排</a></li>
						 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=6&mdName=shopMd&mdUrl=djAjaxShopInfo.?groupId=<%=ID%>">购物安排</a></li>
						 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=7&mdName=plusMd&mdUrl=djAjaxPlusInfo.?groupId=<%=ID%>">加点安排</a></li>
						 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=8&mdName=otherMd&mdUrl=djAjaxOtherInfo.?groupId=<%=ID%>">其他安排</a></li>
						 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=9&mdName=guideMd&mdUrl=djAjaxGuideInfo.?groupId=<%=ID%>">导游安排</a></li>
					</ul>
				  </li> -->
				<%
				//}%>
			  </ul>
			</div>
	  </td>
		</tr>
		<%} %>
	</table>
</div>
<div id="page">
	<%String url=request.getContextPath()+"/maindj/businessPlan/plan/djQueryShPlanList.?pageSize=10&pageNO=";%>
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