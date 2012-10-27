<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 0;
	if(rd.getTableRowsCount("rsAllGroupList")>0){
		
	   	pageNO=Integer.parseInt(rd.getString("pageNO"));
		pageSize=Integer.parseInt(rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("rsAllGroupList", "pagesCount");
		rowsCount = (Integer)rd.getAttr("rsAllGroupList", "rowsCount");
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
	document.djListGroup.action.name="djQueryGroupList.?pageNO=1&pageSize=10";
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
<div style="float:left;"><span>地接登团&nbsp;&nbsp;&nbsp;&nbsp;</span></div>
  <div style="float:left;">
	
	<font color="red">■</font>未实施&nbsp;&nbsp;<font color="green">■</font>已实施
  </div>
</div>
<div  id="thisSelect-table" >
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
			<td align="right">是否驳回：</td>
			<td>
				<select name="isBack">
					<option value="">***请选择***</option>
					<option value="Y">是</option>
					<option value="N">否</option>
				</select>
			</td>
			<td><a href="###" onclick="queryGroupList();"><img alt="搜索" src="<%=request.getContextPath()%>/images/search.gif"/></a></td>
		</tr>
	</table>
</div>
<div id="list-table" >
  <table class="datas" >
	<tr id="first-tr">
	  <td width="12%">团&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</td>
	  <td width="20%">线路名称</td>
  	  <td width="15%">发团日期</td>
	  <td width="8%">人数</td>
	<%--   <td width="12%">价格</td> --%>
	  <td width="20%">计调状态</td>
	  <td width="18%">组团社确认件</td>
	  <td width="7%">操作</td>
	</tr>
	<%
	String roleID = sd.getString("USERROLEST");
	boolean isTrue = false;
	if(!"".equals(roleID)){
		
		roleID = roleID.substring(1,roleID.length()-1);
		String[] roleIDs = roleID.split(",");
		for(int i=0;i<roleIDs.length;i++){
			if("business".equals(roleIDs[i].trim()) ){
				isTrue = true;
				break;
			}
		}
	}
		int rows = rd.getTableRowsCount("rsAllGroupList");
		for(int i=0;i<rows;i++) {
			int index = 9999 - i;
			String ID = rd.getString("rsAllGroupList","ID",i);
			String XLID = rd.getString("rsAllGroupList","XLID",i);
			String XLMC = rd.getString("rsAllGroupList","XLMC",i);
			String BEGIN_DATE = rd.getString("rsAllGroupList","BEGIN_DATE",i);
			String DTRQ = rd.getString("rsAllGroupList","DTRQ",i);
			String CRRS = rd.getString("rsAllGroupList","CRRS",i);
			String ETRS = rd.getString("rsAllGroupList","ETRS",i);
			String STATE = rd.getString("rsAllGroupList","STATE",i);
			String isBack = rd.getString("rsAllGroupList","isback",i);
			int state = Integer.parseInt(STATE);//团状态
			String gw = rd.getString("rsAllGroupList","gw",i);//购物
			String jd = rd.getString("rsAllGroupList","jd",i);//加点
			String hoteltid = rd.getString("rsAllGroupList","hotel",i);//酒店
			String dinnerroom = rd.getString("rsAllGroupList","dinnerroom",i);//餐厅
			String veh = rd.getString("rsAllGroupList","veh",i);//车辆
			String travel = rd.getString("rsAllGroupList","travel",i);//地接
			String guide = rd.getString("rsAllGroupList","guide",i);//导游
			String shop = rd.getString("rsAllGroupList","shop",i);//购物
			String scenery = rd.getString("rsAllGroupList","scenery",i);//景点
			String jiad = rd.getString("rsAllGroupList","jiad",i);//加点
			String ticket = rd.getString("rsAllGroupList","ticket",i);//票务
			String other = rd.getString("rsAllGroupList","other",i);//其他
			//团状态：1：待计划；2：实施中；3：已实施/待审核；4、已审核/待报销；5：报销中；6：已报销/待清款 清款状态：Y已清款；N未清款
	%>
	<tr>
	  <td><a href="###" onclick="TB_show('查看团基本信息','<%=request.getContextPath()%>/maindj/businessPlan/groupMng/djInitUpdateGroup.?dmsm/xlqy=20&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&DMSM/JTGJ=2&DMSM/JGLX=4&TA_DJ_TZTS/TID=<%=ID %>&TA_DJ_TZTS/orgid=<%=sd.getString("orgid") %>&TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&XZQH/PID=0&action=view*TB_iframe=true&height=400&width=800','')" title=""><%=ID %></a></td>
	  <td>
	  	<img alt="热点线路" src="<%=request.getContextPath()%>/images/hot3.gif"/>&nbsp;
	  	<a href="###" onclick="" title="<%=XLMC %>"><%=XLMC.length()<=20?XLMC:XLMC.substring(0,20)+"..."  %></a>
	  </td>
	  <td>
	  	<%=BEGIN_DATE %>
	  </td>
	  <td>
		  成人:<font color="red"><%=CRRS %></font>人<br>
		  儿童:<font color="red"><%=ETRS %></font>人
	  </td>
	  <td>
		&nbsp;<%if("Y".equals(hoteltid)){%><font color="green">■</font><%}else{%><font color="red">■</font><%} %>酒店
		&nbsp;<%if("Y".equals(dinnerroom)){%><font color="green">■</font><%}else{%><font color="red">■</font><%} %>餐厅
		&nbsp;<%if("Y".equals(veh)){%><font color="green">■</font><%}else{%><font color="red">■</font><%} %>车辆
		&nbsp;<%if("Y".equals(travel)){%><font color="green">■</font><%}else{%><font color="red">■</font><%} %>地接
		&nbsp;<%if("Y".equals(guide)){%><font color="green">■</font><%}else{%><font color="red">■</font><%} %>导游<p>
		&nbsp;<%if("Y".equals(scenery)){%><font color="green">■</font><%}else{%><font color="red">■</font><%} %>景点
		&nbsp;<%if("Y".equals(ticket)){%><font color="green">■</font><%}else{%><font color="red">■</font><%} %>票务
		&nbsp;<%if("Y".equals(shop)){%><font color="green">■</font><%}else{%><font color="red">■</font><%} %>购物
		&nbsp;<%if("Y".equals(jiad)){%><font color="green">■</font><%}else{%><font color="red">■</font><%} %>加点
		&nbsp;<%if("Y".equals(other)){%><font color="green">■</font><%}else{%><font color="red">■</font><%} %>其他
		
	  	<%if(isBack.equals("Y")){out.print("<p>申请被驳回");} %>
	  </td>
	  <td>
	  <%
	  
	  int rowTzts = rd.getTableRowsCount("rsTZTS");
	  for(int j=0;j<rowTzts;j++) {
		  String tztsKeyID = rd.getString("rsTZTS","id",j);
		  String groupID = rd.getString("rsTZTS","tid",j);
		  String ztsID = rd.getString("rsTZTS","ztsid",j);
		  String cmpnyName = rd.getString("rsTZTS","cmpny_name",j);
		  if(groupID.equals(ID)) {
	  %>
	  <%=cmpnyName %><br>
	  <a href="###" onclick="TB_show('上传确认件','<%=request.getContextPath()%>/maindj/businessPlan/groupMng/initConfirmForZTS.?TA_TRAVELAGENCY/TRAVEL_AGC_ID=<%=ztsID %>&id=<%=tztsKeyID %>*TB_iframe=true&height=300&width=500','');" >
	    <img alt="上传确认件" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[上传]</a>
	  <a href="###" onclick="TB_show('查看确认件','<%=request.getContextPath()%>/maindj/businessPlan/groupMng/viewConfirmByZTS.?TA_DJ_QRJ/TZTSID=<%=tztsKeyID %>*TB_iframe=true&height=400&width=800','');">
		<img alt="查看确认件" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[查看]</a><br>
		  
	  <%  }
	  }
	  %>
	  </td>
	  <td style="vertical-align: middle;" align="center">
	  	<div id="navigation">
	  	  <ul>
	  	  <%if(state < 3 || isBack.equals("Y")){ %>
		  	<li style="z-index:<%=index%>">
			  	<a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ">
			  	   <img alt="业务计调" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[业务计调]
			  	 </a>
				<ul>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=0&mdName=hotelMd&mdUrl=djAjaxHotelInfo.?groupId=<%=ID%>">酒店安排</a></li>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=1&mdName=dinnerMd&mdUrl=djAjaxDinnerInfo.?groupId=<%=ID%>">餐厅安排</a></li>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=2&mdName=ticketMd&mdUrl=djAjaxTicketInfo.?groupId=<%=ID%>">票务安排</a></li>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=3&mdName=carMd&mdUrl=djAjaxCarInfo.?groupId=<%=ID%>,dmsm/cllx=13">车辆安排</a></li>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=4&mdName=sceneryMd&mdUrl=djAjaxSceneryInfo.?groupId=<%=ID%>">景点安排</a></li>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=5&mdName=travelMd&mdUrl=djAjaxTravelInfo.?groupId=<%=ID%>">地接安排</a></li>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=6&mdName=shopMd&mdUrl=djAjaxShopInfo.?groupId=<%=ID%>">购物安排</a></li>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=7&mdName=plusMd&mdUrl=djAjaxPlusInfo.?groupId=<%=ID%>">加点安排</a></li>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=8&mdName=otherMd&mdUrl=djAjaxOtherInfo.?groupId=<%=ID%>">其他安排</a></li>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=9&mdName=guideMd&mdUrl=djAjaxGuideInfo.?groupId=<%=ID%>">导游安排</a></li>
				</ul>
			</li>
			<%}else{ %>
				<li style="z-index:<%=index%>">
			  	  <a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&action=view">
			  	    <img alt="查看业务计调" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[查看计调信息]
			  	  </a>
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
	<%String url=request.getContextPath()+"/maindj/businessPlan/groupMng/djQueryAllGroupList.?DMSM/JDXJ=6&pageSize=10&pageNO=";%>
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
