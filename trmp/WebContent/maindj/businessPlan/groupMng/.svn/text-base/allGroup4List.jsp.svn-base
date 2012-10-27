<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 0;
	if(rd.getTableRowsCount("allGroup4List")>0){
		
	   	pageNO=Integer.parseInt(rd.getString("pageNO"));
		pageSize=Integer.parseInt(rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("allGroup4List", "pagesCount");
		rowsCount = (Integer)rd.getAttr("allGroup4List", "rowsCount");
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>团队列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/js/thickbox/thickbox.css" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>
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
	document.djListGroup.action.name="djGroupLists.?pageNO=1&pageSize=10";
	document.djListGroup.submit();
}
function openUrl(url){
	window.location.href=url;
}
$(function(){
	   $("#navigation ul li:has(ul)").hover(function(){
			$(this).children("ul").stop(true,true).slideDown(400);
     },function(){
		    $(this).children("ul").stop(true,true).slideUp();
		});
})
</script>
</head>
<body>
<form name="djListGroup" method="post">
<div id="lable" >
  <div style="float:left;"><span><%=rd.getString("yw").equals("1")?"地接团队列表":"全国大散团列表" %>&nbsp;&nbsp;&nbsp;&nbsp;</span></div>
<%
if(rd.getString("yw").equals("1")){
%>
  <div style="float:left;">
	<span class="showPointer" onclick="openUrl('<%=request.getContextPath()%>/maindj/businessPlan/groupMng/djInitAddGroup.?dmsm/cllx=13&dmsm/JDXJ=6&dmsm/jtgj=2&dmsm/JGLX=4&xzqh/layer=1&yw=<%=rd.getString("yw") %>');">
	  <img class="showPointer" alt="创建新团" src="<%=request.getContextPath() %>/images/create-add.gif">创建新团&nbsp;&nbsp;&nbsp;
	</span>
  </div>
<%
} %>
</div>
<div id="thisSelect-table" >
	<table>
		<tr>
			<td rowspan="3" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td>团号：</td>
			<td><input type="text" name="groupID"></td>
			<td align="right">发团日期：</td>
			<td><input type="text" name="bDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" /></td>
			<td align="right">线路名称：</td>
			<td><input type="text" name="xlmc" />
				
			</td>
			<td align="right">团状态：</td>
			<td>
				<select name="state">
					<option value="">***请选择***</option>
					<option value="1">待计划</option>
					<option value="2">实施中</option>
					<option value="3">已实施</option>
					<option value="4">已审核/待报销</option>
					<option value="5">报销中</option>
					<option value="6">已报销</option>
				</select>
			</td>
			<td><a href="###" onclick="queryGroupList();"><img alt="搜索" src="<%=request.getContextPath()%>/images/search.gif"/></a></td>
		</tr>
	</table>
</div>
<div id="list-table" >
  <table class="datas" >
	<tr id="first-tr">
	  <td width="10%">团&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</td>
	  <td width="25%">线路名称</td>
  	  <td width="16%">发团日期</td>
	  <td width="8%">天数</td>
	  <td width="8%">成人人数</td>
	  <td width="8%">儿童人数</td>
	  <td width="15%">组团社确认件</td>
	  <td width="10%">操作</td>
	</tr>
	<%
	
		int rows = rd.getTableRowsCount("allGroup4List");
		for(int i=0;i<rows;i++) {
			String ID = rd.getString("allGroup4List","ID",i);
			String XLID = rd.getString("allGroup4List","XLID",i);
			String XLMC = rd.getString("allGroup4List","XLMC",i);
			String BEGIN_DATE = rd.getString("allGroup4List","BEGIN_DATE",i);
			String ts = rd.getString("allGroup4List","ts",i);
			String night = rd.getString("allGroup4List","night",i);
			String DTRQ = rd.getString("allGroup4List","DTRQ",i);
			String CRRS = rd.getString("allGroup4List","CRRS",i);
			String ETRS = rd.getString("allGroup4List","ETRS",i);
			String STATE = rd.getString("allGroup4List","STATE",i);
			String tztsKey = rd.getString("allGroup4List","tztsKey",i);
			

			String tztsKeyID = rd.getString("allGroup4List","tztsKey",i);
			String ztsID = rd.getString("allGroup4List","ztsid",i);
			String cmpnyName = rd.getString("allGroup4List","ztsmc",i);
			int state = Integer.parseInt(STATE);//团状态
			//团状态：1：待计划；2：实施中；3：已实施/待审核；4、已审核/待报销；5：报销中；6：已报销/待清款 清款状态：Y已清款；N未清款
	%>
	<tr>
	  <td><a href="###" onclick="TB_show('查看团基本信息','<%=request.getContextPath()%>/maindj/businessPlan/groupMng/djInitUpdateGroup.?dmsm/xlqy=20&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&DMSM/JTGJ=2&DMSM/JGLX=4&TA_DJ_TZTS/TID=<%=ID %>&TA_DJ_TZTS/orgid=<%=sd.getString("orgid") %>&TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&XZQH/PID=0&action=view*TB_iframe=true&height=400&width=800','')" title=""><%=ID %></a></td>
	  <td>
	  	<img alt="热点线路" src="<%=request.getContextPath()%>/images/hot3.gif"/>&nbsp;
	  	<a href="###" onclick="" title="<%=XLMC %>"><%=XLMC.length()<=20?XLMC:XLMC.substring(0,20)+"..."  %></a>
	  </td>
	  <td><%=BEGIN_DATE %> </td>
	  <td><%=ts %>天<%=night %>晚</td>
	  <td><font color="red"><%=CRRS %></font>人</td>
	  <td><font color="red"><%=ETRS %></font>人</td>
	  <td>
<%
int travelRows = rd.getTableRowsCount("travelNameOfGroup");
for(int j=0;j<travelRows;j++){
	String tztsid = rd.getStringByDI("travelNameOfGroup","id",j);//团组团社表主键
	String traName = rd.getStringByDI("travelNameOfGroup","ztsmc",j);
	String traId = rd.getStringByDI("travelNameOfGroup","ztsid",j);
	String tId = rd.getStringByDI("travelNameOfGroup","tid",j);
	if(tId.equals(ID)){
%>
	  <%=traName %><br>
	  <a href="###" onclick="TB_show('上传确认件','<%=request.getContextPath()%>/maindj/businessPlan/groupMng/initConfirmForZTS.?TA_TRAVELAGENCY/TRAVEL_AGC_ID=<%=traId %>&id=<%=tztsid %>*TB_iframe=true&height=300&width=500','');" >
	    <img alt="上传确认件" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[上传]</a>
	  <a href="###" onclick="TB_show('查看确认件','<%=request.getContextPath()%>/maindj/businessPlan/groupMng/viewConfirmByZTS.?TA_DJ_QRJ/TZTSID=<%=tztsid %>*TB_iframe=true&height=400&width=800','');">
		<img alt="查看确认件" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[查看]</a><br>
<%	}
}%>
	  </td>
	  <td>
	  <%if(state < 3){ %>
	  	<a href="###" onclick="openUrl('<%=request.getContextPath()%>/maindj/businessPlan/groupMng/djInitUpdateGroup.?xzqh/layer=1&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&yw=<%=rd.getString("yw") %>&DMSM/JTGJ=2&DMSM/JGLX=4&TA_DJ_TZTS/TID=<%=ID %>&TA_DJ_TZTS/orgid=<%=sd.getString("orgid") %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>')" >
		  <img alt="修改团信息" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[团  修  改]
		</a>
	  <%}else{ %>
		<a href="###" onclick="openUrl('<%=request.getContextPath()%>/maindj/businessPlan/groupMng/djInitUpdateGroup.?dmsm/xlqy=20&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&DMSM/JTGJ=2&DMSM/JGLX=4&TA_DJ_TZTS/TID=<%=ID %>&TA_DJ_TZTS/orgid=<%=sd.getString("orgid") %>&TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&XZQH/PID=0&action=view')" >
		  <img alt="修改团信息" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[查看团队信息]
		</a>
	  <%} %>
	  </td>
	</tr>
	<%} %>
  </table>
</div>	
<div id="page">
	<%String url=request.getContextPath()+"/maindj/businessPlan/groupMng/djGroupLists.?yw="+rd.getString("yw")+"pageSize=10&pageNO=";%>
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
