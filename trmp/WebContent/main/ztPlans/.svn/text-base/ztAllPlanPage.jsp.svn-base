<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String action = rd.getString("action");
String groupID = rd.getStringByDI("TA_ZT_GROUPs","ID",0);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>组团业务计调</title>
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
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
        width: width || 700,
        fullscreen: true,
        show_loading: false,
        callback_fn: callback_fn
    };
    var win = new GB_Window(options);
    return win.show(url);
};
</script>

<script type="text/javascript">
function deleteTable(tabName,groupId){
	if(confirm("你确定删除该计划吗？")){
		window.location.href="<%=request.getContextPath()%>/main/ztPlans/deleteTabName.?tabName="+tabName+"&groupId="+groupId;
		<%-- jQuery.ajax({
			url:"<%=request.getContextPath()%>/main/ztPlans/deleteTabName.?tabName="+tabName+"&groupId="+groupId,
			async:true,
			cache:false,
			success:function(){
				alert("删除成功！");
			}
		}); --%>
	}
}
</script>

</head>

<body>
<form name="p_plan_form" method="post">
<div >
<input type="hidden" name="userno" value="<%=sd.getString("userno") %>"/>
<div id="lable"><span >团计划信息</span></div>
<div id="bm-table">
<div >
	<table id="groupDiv" class="datas">
			<tr>
				<td colspan="4"><span>&nbsp;&nbsp;基本信息&nbsp;>>>&nbsp;团号：<%=groupID %></span></td>
			</tr>
			<tr>
				<td align="left" colspan="4">&nbsp;&nbsp;线路名称：
				<%String XLMC = rd.getStringByDI("TA_ZT_GROUPs","XLMC",0); %>
				<font color="red"><%=XLMC.length()<=50?XLMC:XLMC.substring(0,50)+"..."  %></font>
				
				</td>
			</tr>
			<tr>
			   <td align="left" colspan="4">
			   &nbsp;&nbsp;
			(去程：<font color="red"><%=rd.getStringByDI("TA_ZT_GROUPs","BEGIN_DATE",0) %></font>&nbsp;-&nbsp;回程：<font color="red"><%=rd.getStringByDI("TA_ZT_GROUPs","END_DATE",0) %></font>)
			
			</td>
			</tr>
			<tr>
				<td align="left" colspan="4">
			   &nbsp;&nbsp;
			(游客类型：<font color="red"><%if("2".equals(rd.getStringByDI("TA_ZT_GROUPs","YKLX",0))){out.print("散客");}else{out.print("团队");} %></font>)
			&nbsp;&nbsp;
			(天数：<font color="red"><%=rd.getStringByDI("TA_ZT_GROUPs","DAYS",0) %>天
			  <%=rd.getStringByDI("TA_ZT_GROUPs","NIGHT",0) %>晚</font>)
			 &nbsp;&nbsp;
			(人数：<font color="red">成人：<%=rd.getStringByDI("TA_ZT_GROUPs","ADULT_COUNT",0) %>人&nbsp;
			儿童：<%=rd.getStringByDI("TA_ZT_GROUPs","CHILDREN_COUNT",0) %>人</font>)
			 &nbsp;&nbsp;
			(购物加点进店数：<font color="red">购物：<%if("".equals(rd.getStringByDI("TA_ZT_GROUPs","GW",0))){out.print("×");}else{out.print("√");} %>
			加点：<%if("".equals(rd.getStringByDI("TA_ZT_GROUPs","JD",0))){out.print("×");}else{out.print("√");} %>
			</font>)
			   </td>
			</tr>
			
			<tr>
				<td align="left" colspan="4">&nbsp;&nbsp;备注：
				<%String TSYQ = rd.getStringByDI("TA_ZT_GROUPs","TSYQ",0); %>
				<%=TSYQ.length()<=70?TSYQ:TSYQ.substring(0,70)+"..."  %></td>
			</tr>
	</table>
</div>
	<div id="hotelDiv">
	<%
	int hotelRows = rd.getTableRowsCount("TA_ZT_JHHOTELs");
	String qdzszj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","QDZSZJ",0);
	String xfzszj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","XFZSZJ",0);
	String zszj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","ZSZJ",0);
	if(hotelRows <= 0){
		
	%>
		<table class="datas">
			<tr>
				<td><span>酒店计划信息</span></td>
			</tr>
			<tr>
			<td>酒店尚未做计划(<span class="showPointer" style="text-decoration:underline;color:red;font-weight:100" onclick="return GB_myShow('酒店计划','<%=request.getContextPath()%>/main/ztPlans/hotelPlan/ztInitAddHotelPlan.?TA_ZT_GROUP/ID=<%=groupID %>&TA_ZT_JHHOTEL/TID=<%=groupID %>&TID=<%=groupID %>','800','750','')">业务计调</span>)</td>
			</tr>
		</table>
	<%}else{ %>
		<table class="datas">
			<tr>
			  <td><span>酒店计划信息</span>
			  	  <span class="showPointer" title="查看计划信息" onclick="return GB_myShow('酒店计划','<%=request.getContextPath()%>/main/ztPlans/hotelPlan/ztSelectHotelPlanInfo.?TA_ZT_GROUP/ID=<%=groupID %>&TA_ZT_JHHOTEL/TID=<%=groupID %>&TA_TDJDXXZJB_ZT/TID=<%=groupID %>&TID=<%=groupID %>','800','750','')">查看</span>
			  
			  <%
				if(!action.equals("v")) {
			  %>
			 	  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="修改酒店计划信息" onclick="return GB_myShow('酒店计划','<%=request.getContextPath()%>/main/ztPlans/hotelPlan/ztInitUpdateHotelPlan.?TA_ZT_GROUP/ID=<%=groupID %>&TA_ZT_JHHOTEL/TID=<%=groupID %>&TA_TDJDXXZJB_ZT/TID=<%=groupID %>&TID=<%=groupID %>','800','750','')">修改</span>
			 	  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="删除酒店计划信息" onclick="deleteTable('ztPlanHotel','<%=groupID%>')">删除</span>
			  <%
			  }%>
			  </td>
			</tr>
			<tr>
				<td align="right">
					酒店费用合计：签单金额合计：<font color="red"><%=qdzszj %></font>元  现付金额合计：<font color="red"><%=xfzszj %></font>元  合计：<font color="red"><%=zszj %></font>元
				</td>
			</tr>
		</table>
		<%} %>
	</div>

	<div id="dinnerDiv">
		<%
			int dinnerPlanRows=rd.getTableRowsCount("TA_ZT_JHCTs");
			String qdctzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","QDCTZJ",0);
			String xfctzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","XFCTZJ",0);
			String ctzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","CTZJ",0);
			if(dinnerPlanRows <= 0){
		%>
		<table class="datas">
			<tr>
				<td><span>餐厅计划信息</span></td>
			</tr>
			<tr>
				<td>餐厅尚未做计划(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('餐厅计划','<%=request.getContextPath()%>/main/ztPlans/dinnerPlan/ztInitAddDinnerPlan.?TA_ZT_GROUP/ID=<%=groupID %>&TA_ZT_JHCT/ID=&TID=<%=groupID %>','800','850','')">业务计调</span>)</td>
			</tr>
		</table>
		<%}else{ %>
			<table class="datas">
			  <tr>
				<td>
				  <span>餐厅计划信息</span> <span class="showPointer" title="查看计划信息" onclick="return GB_myShow('餐厅计划','<%=request.getContextPath()%>/main/ztPlans/dinnerPlan/ztListDinnerPlan.?TA_ZT_GROUP/ID=<%=groupID %>&TA_ZT_JHCT/ID=&TID=<%=groupID %>','800','850','')">查看</span>
				<%
				if(!action.equals("v")) {
			  	%>	
				  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="修改餐厅计划信息" onclick="return GB_myShow('餐厅计划','<%=request.getContextPath()%>/main/ztPlans/dinnerPlan/ztInitUpdateDinnerPlan.?TA_ZT_GROUP/ID=<%=groupID %>&TA_ZT_JHCT/ID=&TID=<%=groupID %>','800','850','')">修改</span>
				  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="删除餐厅计划信息" onclick="deleteTable('ztPlanDinner','<%=groupID%>')">删除</span>
				<%
				}%>
				</td>
				</tr>
				<tr>
					<td align="right">
						餐厅费用合计：签单金额合计：<font color="red"><%=qdctzj %></font>元  现付金额合计：<font color="red"><%=xfctzj %></font>元  合计：<font color="red"><%=ctzj %></font>元
					</td>
				</tr>
			</table>
		<%} %>
		
	</div>
	<div id="ticketDiv">
<%
	int ticketRows = rd.getTableRowsCount("TA_ZT_JHPWs");
	String tqd = rd.getStringByDI("TA_TDJDXXZJB_ZTs","QDPWZJ",0);
	String txf = rd.getStringByDI("TA_TDJDXXZJB_ZTs","XFPWZJ",0);
	String tzj = rd.getStringByDI("TA_TDJDXXZJB_ZTs","PWZJ",0);
	String sxf = rd.getStringByDI("TA_TDJDXXZJB_ZTs","SXFZJ",0);
	if(0 >= ticketRows){
		
%>
		<table class="datas">
			<tr>
				<td><span>票务计划信息</span></td>
			</tr>
			<tr>
				<td>票务尚未做计划(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('票务计调','<%=request.getContextPath()%>/main/ztPlans/ticketPlan/ztInitTicketP.?TA_ZT_JHPW/TID=<%=groupID %>&TID=<%=groupID %>&dmsm/JTGJ=2&TA_TDJDXXZJB_ZT/TID=<%=groupID %>&TA_ZT_JHPWMX/TID=<%=groupID %>','800','850','')">业务计调</span>)</td>
			</tr>
		</table>
<%
	}else{%>
		<table class="datas">
		  <tr>
			<td>
			  <span>票务计划信息</span> <span class="showPointer" title="查看计划信息" onclick="return GB_myShow('票务计调','<%=request.getContextPath()%>/main/ztPlans/ticketPlan/ztInitTicketP.?TA_ZT_JHPW/TID=<%=groupID %>&TID=<%=groupID %>&dmsm/JTGJ=2&flag=view&TA_TDJDXXZJB_ZT/TID=<%=groupID %>&TA_ZT_JHPWMX/TID=<%=groupID %>','800','850','')">查看</span>
			<%
				if(!action.equals("v")) {
			  	%>
			  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="修改票务计划信息" onclick="return GB_myShow('票务计调','<%=request.getContextPath()%>/main/ztPlans/ticketPlan/ticketPlan/ztInitTicketP.?TA_ZT_JHPW/TID=<%=groupID %>&TID=<%=groupID %>&dmsm/JTGJ=2&TA_TDJDXXZJB_ZT/TID=<%=groupID %>&TA_ZT_JHPWMX/TID=<%=groupID %>','800','850','')">修改</span>
			  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="删除票务计划信息" onclick="deleteTable('ztPlanTicket','<%=groupID%>')">删除</span>
			<%
			}%>
			</td>
			</tr>
			<tr>
				<td align="right">
					票务费用合计：签单金额合计：<font color="red"><%=tqd %></font>元  现付金额合计：<font color="red"><%=txf %></font>元  合计：<font color="red"><%=tzj %></font>元  手续费总计：<font color="red"><%=sxf %></font>元
				</td>
			</tr>
		</table>
<%	} %>
	</div>
	<div id="vehDiv">
<%
	int vehRows = rd.getTableRowsCount("TA_ZT_JHCLs");
	String qd = rd.getStringByDI("TA_TDJDXXZJB_ZTs","QDCLZJ",0);
	String xf = rd.getStringByDI("TA_TDJDXXZJB_ZTs","XFCLZJ",0);
	String zj = rd.getStringByDI("TA_TDJDXXZJB_ZTs","CLZJ",0);
	if(0 >= vehRows){
		
%>
		<table class="datas">
			<tr>
				<td><span>车调计划信息</span></td>
			</tr>
			<tr>
				<td>车辆尚未做计划(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('车调计划明细','<%=request.getContextPath() %>/main/ztPlans/vehPlan/ztInitVehPlan.?TA_ZT_JHCL/TID=<%=groupID %>&TA_TDJDXXZJB_ZT/TID=<%=groupID %>','800','800','')">业务计调</span>)</td>
			</tr>
		</table>
<%
}else{
	
%>	
		<table class="datas">
		  <tr>
			<td>
			  <span>车调计划信息</span> <span class="showPointer" title="查看车调计划明细" onclick="return GB_myShow('车调计划明细','<%=request.getContextPath() %>/main/ztPlans/vehPlan/ztInitVehPlan.?TA_ZT_JHCL/TID=<%=groupID %>&TA_TDJDXXZJB_ZT/TID=<%=groupID %>&flag=view','800','800','')">查看</span>
			<%
			if(!action.equals("v")) {
			%>
			  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="修改车调计划信息" onclick="return GB_myShow('修改车调计划信息','<%=request.getContextPath() %>/main/ztPlans/vehPlan/ztInitVehPlan.?TA_ZT_JHCL/TID=<%=groupID %>&TA_TDJDXXZJB_ZT/TID=<%=groupID %>','800','800','')">修改</span>
			  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="删除车调计划信息" onclick="deleteTable('ztPlanCar','<%=groupID%>')">删除</span>
			<%
			}%>
			</td>
			</tr>
			<tr>
				<td  align="right">车辆费用合计：签单金额合计：<font color="red"><%=qd %></font>元  现付金额合计：<font color="red"><%=xf %></font>元  合计：<font color="red"><%=zj %></font>元</td>
			</tr>
		</table>	
<%
}%>	
	</div>
	
	<div id="sceneryDiv">
	<%
		int SceneryRows = rd.getTableRowsCount("TA_ZT_JHJDs");
		String QDJDZJ = rd.getStringByDI("TA_TDJDXXZJB_ZTs","QDJDZJ",0);
		String XFJDZJ = rd.getStringByDI("TA_TDJDXXZJB_ZTs","XFJDZJ",0);
		String JDZJ = rd.getStringByDI("TA_TDJDXXZJB_ZTs","JDZJ",0);
		if(0 >= SceneryRows){
			
	%>
		<table class="datas">
			<tr>
				<td><span>景点计划信息</span></td>
			</tr>
			<tr>
				<td>景点尚未做计划(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('查看景点计划信息','<%=request.getContextPath() %>/main/ztPlans/sceneryPlan/ztInitSceneryPlan.?TA_ZT_JHJD/TID=<%=groupID %>&TA_TDJDXXZJB_ZT/TID=<%=groupID %>','800','700','')">业务计调</span>)</td>
			</tr>
		</table>
		<%}else{ %>
		<table class="datas">
		  <tr>
			<td>
				  <span>景点计划信息</span> <span title="查看景点计划信息"  class="showPointer"  onclick="return GB_myShow('查看景点计划信息','<%=request.getContextPath() %>/main/ztPlans/sceneryPlan/ztInitSceneryPlan.?TA_TDJDXXZJB_ZT/TID=<%=groupID %>&TA_ZT_JHJD/TID=<%=groupID %>&flag=view','800','700','')"> 查看</span>
					<%
					if(!action.equals("v")) {
				  	%>
					  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="修改景点计划信息" onclick="return GB_myShow('修改景点计划信息','<%=request.getContextPath() %>/main/ztPlans/sceneryPlan/ztInitSceneryPlan.?TA_TDJDXXZJB_ZT/TID=<%=groupID %>&TA_ZT_JHJD/TID=<%=groupID %>','800','700','')">修改</span>
					  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="删除景点计划信息" onclick="deleteTable('ztPlanScenery','<%=groupID%>')">删除</span>
					<%
					}
					%>
				</td>
			</tr>
			<tr>
				<td  align="right">景点费用合计：签单金额合计：<font color="red"><%=QDJDZJ %></font>元  现付金额合计：<font color="red"><%=XFJDZJ %></font>元  合计：<font color="red"><%=JDZJ %></font>元</td>
			</tr>
		</table>	
		
		<%} %>
	</div>
	
	<div id="traveDiv">
		<%
			int newTraverows = rd.getTableRowsCount("TA_ZT_JHDJs");
			String ID = groupID;
			String DJXFZJ = rd.getStringByDI("TA_TDJDXXZJB_ZTs","DJXFZJ",0);
			String DJQDZJ = rd.getStringByDI("TA_TDJDXXZJB_ZTs","DJQDZJ",0);
			String DJZJ = rd.getStringByDI("TA_TDJDXXZJB_ZTs","DJZJ",0);
		    if(0 >= newTraverows){
		    	
		%>
		<table class="datas">
			<tr>
				<td><span>地接计划信息</span></td>
			</tr>
			<tr>
				<td>地接尚未做计划(<span class="showPointer" style="text-decoration:underline;color:red;font-weight:100" onclick="return GB_myShow('地接社计划','<%=request.getContextPath()%>/main/ztPlans/groundTravePlan/ztInitGroundPlan.?TA_ZT_JHDJ/TID=<%=groupID %>&TA_TDJDXXZJB_ZT/TID=<%=groupID %>','800','600')">业务计调</span>)</td>
			</tr>
		</table>
		<%}else{ %>	
		<table class="datas">
		  <tr>
			<td><span>地接计划信息</span> 
				<span title="查看地接计划信息"  class="showPointer" title="查看地接计划信息" onclick="return GB_myShow('查看地接社计划信息','<%=request.getContextPath() %>/main/ztPlans/groundTravePlan/ztInitGroundPlan.?TA_ZT_JHDJ/TID=<%=groupID %>&TA_TDJDXXZJB_ZT/TID=<%=groupID %>&flag=view','800','600','')">查看</span>
				<%
				if(!action.equals("v")) {
			  	%>
				<span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="修改地接计划信息" onclick="return GB_myShow('修改地接社计划信息','<%=request.getContextPath() %>/main/ztPlans/groundTravePlan/ztInitGroundPlan.?TA_ZT_JHDJ/TID=<%=groupID %>&TA_TDJDXXZJB_ZT/TID=<%=groupID %>','800','600','')">修改</span>
				<span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="删除地接计划信息" onclick="deleteTable('ztPlanTrave','<%=groupID%>')">删除</span>
				<%
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="right">
				<% for(int j=0;j<newTraverows;j++){
					 String tztsKeyID = rd.getStringByDI("sqlGroundList","id",j);
					  String ztsID = rd.getStringByDI("sqlGroundList","djsid",j);
					  String cmpnyName = rd.getStringByDI("sqlGroundList","cmpny_name",j);
					  if(groupID.equals(ID)) {
				 %>
				         地接社:
				   <font color="red"><%=cmpnyName%></font>
						<a href="###" onclick="return GB_myShow('上传确认件','<%=request.getContextPath()%>/main/ztGroupMng/ztinitConfirmForZTS.?TA_TRAVELAGENCY/TRAVEL_AGC_ID=<%=ztsID %>&id=<%=tztsKeyID %>','400','800','')" >
						    <img alt="上传确认件" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[上传]</a>
						<%
				if(!action.equals("v")) {
			  	%>
						<a href="###" onclick="return GB_myShow('查看确认件','<%=request.getContextPath()%>/main/ztGroupMng/ztviewConfirmByZTS.?TA_ZT_QRJ/TZTSID=<%=tztsKeyID %>','800','800','')">
							<img alt="查看确认件" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[查看]</a>
				  <%} %>
				 <%} } %>
	 			</td>
	 		</tr>
	 		<tr>
				<td  align="right">地接费用合计：签单金额合计：<font color="red"><%=DJQDZJ %></font>元  现付金额合计：<font color="red"><%=DJXFZJ %></font>元  合计：<font color="red"><%=DJZJ %></font>元</td>
			</tr>
		</table>	
		<%
		}
		%>
		</div>
	
	
	
	<div id="shopDiv">
	<%
	int newShopRows=rd.getTableRowsCount("TA_ZT_JHGWs"); 
	String gw=rd.getStringByDI("TA_ZT_GROUPs","GW",0);
	if("1".equals(gw)){
	if(0 >= newShopRows ){
	%>
		<table class="datas">
			<tr>
				<td><span>购物计划信息</span></td>
			</tr>
			<tr>
				<td>购物尚未做计划(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="GB_myShow('购物计划信息','<%=request.getContextPath()%>/main/ztPlans/ShoppingPlan/ztInitShopPlan.?TA_ZT_JHGW/TID=<%=groupID %>','800','600')">业务计调</span>)</td>
			</tr>
		</table>
	<%}else{ %>
		<table class="datas">
		  <tr>
			<td><span>购物计划信息</span> <span class="showPointer" title="查看购物计划信息" onclick="return GB_myShow('购物计划信息','<%=request.getContextPath() %>/main/ztPlans/ShoppingPlan/ztInitShopPlan.?TA_ZT_JHGW/TID=<%=groupID %>&flag=view','800','800','')">查看</span>
			<%
				if(!action.equals("v")) {
			  	%>
				<span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="修改购物计划信息" onclick="return GB_myShow('修改购物计划信息','<%=request.getContextPath() %>/main/ztPlans/plusPlan/ztInitShopPlan.?TA_ZT_JHGW/TID=<%=groupID %>','800','800','')">修改</span>
				<span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="删除购物计划信息" onclick="deleteTable('ztPlanShop','<%=groupID%>')">删除</span>
				<%
				}
				%>
				</td>
		  </tr>
		  <tr>
			<td  align="right">购物内容：<font color="red"><%for(int i = 0; i < newShopRows; i++){String SFXZ= rd.getStringByDI("TA_ZT_JHGWs","SFXZ",i); if("Y".equals(SFXZ)){out.print(rd.getStringByDI("TA_ZT_JHGWs","GWDMC",i)+"&nbsp;");}} %></font></td>
		  </tr>
		</table>
	<%}} %>
	</div>
	
	
	
	<div id="newSceneryDiv">
<%
	int newSceneryRows = rd.getTableRowsCount("TA_ZT_JHJIADs");
	String jd=rd.getStringByDI("TA_ZT_GROUPs","JD",0);
	if("1".equals(jd)){
	if(0 >= newSceneryRows){
%>
		<table class="datas">
			<tr>
				<td><span>加点计划信息</span></td>
			</tr>
			<tr>
				<td>加点尚未做计划(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="GB_myShow('加点计划','<%=request.getContextPath()%>/main/ztPlans/plusPlan/ztInitPlusPlan.?TA_ZT_JHJIAD/TID=<%=groupID %>&TID=<%=groupID %>','800','600')">业务计调</span>)</td>
			</tr>
		</table>
<%
}else{%>
		<table class="datas">
		<tr>
		  <td><span>加点计划信息</span> <span class="showPointer" title="查看加点计划信息" onclick="return GB_myShow('加点计划信息','<%=request.getContextPath() %>/main/ztPlans/plusPlan/ztInitNewScenery4Edit.?TA_ZT_JHJIAD/TID=<%=groupID %>&flag=view','800','800','')">查看</span>
			<%
			if(!action.equals("v")) {
			%>
			  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="修改加点计划信息" onclick="return GB_myShow('修改加点计划信息','<%=request.getContextPath() %>/main/ztPlans/plusPlan/ztInitNewScenery4Edit.?TA_ZT_JHJIAD/TID=<%=groupID %>','800','800','')">修改</span>
			  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="删除加点计划信息" onclick="deleteTable('ztPlanPlus','<%=groupID%>')">删除</span>
			<%
			}
			%>
			</td>
		</tr>
		<tr>
		  <td  align="right">加点内容：<font color="red"><%for(int i=0;i<rd.getTableRowsCount("rsScenerys");i++){out.print(rd.getStringByDI("rsScenerys","jdmc",i)+" ");} %></font></td>
		</tr>
		</table>
<%
}}%>
	</div>
	<div id="SumPrice">
		<table class="datas">
			<tr>
				<td ><span>导游领款建议价</span></td>
			</tr>
			<%
				float hotelXF=xfzszj.length()>0?Float.parseFloat(xfzszj):0;
				float dinnerXF=xfctzj.length()>0?Float.parseFloat(xfctzj):0;
				float ticketXF=txf.length()>0?Float.parseFloat(txf):0;
				float carXF=xf.length()>0?Float.parseFloat(xf):0;
				float sceneryXF=XFJDZJ.length()>0?Float.parseFloat(XFJDZJ):0;
				float djXF=DJXFZJ.length()>0?Float.parseFloat(DJXFZJ):0;
				float dylkjyj=hotelXF+dinnerXF+ticketXF+carXF+sceneryXF+djXF;
			%>
			<tr>
				<td align="right">导游领款建议价:<font color="red"><%=dylkjyj %></font>元</td>
			</tr>
		</table>
	</div>
	<div id="guideDiv">
		<%
		int dylk=0;
		int dff=0;
		int GuideRows = rd.getTableRowsCount("TA_ZT_JHDYs");
		for(int i = 0; i < GuideRows; i++){
			String guidelk = rd.getStringByDI("TA_ZT_JHDYs","DYLK",i);
			if("".equals(guidelk)){guidelk="0";}
			dylk+=Integer.parseInt(guidelk);
			String guideff = rd.getStringByDI("TA_ZT_JHDYs","DFF",i);
			if("".equals(guideff)){guideff="0";}
			dff+=Integer.parseInt(guideff);
		}
		
		if(GuideRows <= 0){ %>
		<table class="datas">
			<tr>
				<td><span>导游计划信息</span></td>
			</tr>
			<tr>
			<td>导游尚未做计划(<span class="showPointer" style="text-decoration:underline;color:red;font-weight:100" onclick="return GB_myShow('导游计划','<%=request.getContextPath()%>/main/ztPlans/guidePlan/ztInitGuidePlan.?TA_ZT_JHDY/ID=&TID=<%=groupID %>&role=guide','800','600','')">业务计调</span>)</td>
			</tr>
		</table>
		<%}else{ %>
		<table class="datas">
		  <tr>
			<td><span>导游计划信息</span> <span class="showPointer" title="查看导游费用明细" onclick="return GB_myShow('导游费用明细','<%=request.getContextPath() %>/main/ztPlans/guidePlan/ztInitEditGuidePlan.?role=guide&TA_ZT_JHDY/ID=&TID=<%=groupID %>&action=view','800','600','')">查看</span>
			 <%
				if(!action.equals("v")) {
			  	%>
			 	<span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="修改导游计划信息" onclick="return GB_myShow('修改导游计划','<%=request.getContextPath() %>/main/ztPlans/guidePlan/ztInitEditGuidePlan.?role=guide&TA_ZT_JHDY/ID=&TID=<%=groupID %>&action=edit','800','600','')">修改</span>
			 	<span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="删除导游计划信息" onclick="deleteTable('ztPlanGuide','<%=groupID%>')">删除</span>
			<%} %>
			</td>
			</tr>
			<tr>
				<td  align="right">
					导游费用合计：领款金额合计：<font color="red"><%=dylk %></font>元  导服费金额合计：<font color="red"><%=dff %></font>元  
				</td>
			</tr>
		</table>	
		<%} %>
		
	</div>
	
	<%
		int yjRows=rd.getTableRowsCount("SPYJ");
		if(yjRows>0){
	%>
	<div>
		<table class="datas">
			<tr>
				<td  colspan="2"><span>审批意见</span></td>
			</tr>
			<tr>
				<td  colspan="2">
					<%
						
						for(int i=0;i<yjRows;i++){
					%>
						<%=rd.getStringByDI("SPYJ","USERNAME",i)%>:
						<%=rd.getStringByDI("SPYJ","SPYJ",i)%>;<br/>
					<%
							}
					%>
				</td>
			</tr>
		</table>
	</div>
	<%} %>
</div>

<script type="text/javascript">
	/**
	function p_editPlan(){
		if(<%=GuideRows%>>0 && <%=hotelRows%>>0 && <%=dinnerPlanRows%>>0 && <%=ticketRows%>>0 && <%=vehRows%>>0 && <%=SceneryRows%>>0 && <%=newTraverows%>>0 && <%=newShopRows%>>0 && <%=newSceneryRows%>>0){
			document.p_plan_form.action="ztInitShPlanList.?pageNO=1&pageSize=10&definitionid=ztywsh&TID=<%=groupID%>";
			document.p_plan_form.submit();
		}else{
			alert("有计划没有制定,请制定相关计划!");
		}
	}*/
	
	function p_editPlan(){
		document.p_plan_form.action="ztInitShPlanList.?pageNO=1&pageSize=10&definitionid=ztywsh&TID=<%=groupID%>";
		document.p_plan_form.submit();
	}
</script>
<div align="center" id="last-sub">
<%
	if(!action.equals("v")) {
%>
	<input type="button" id="button" value="提交" onclick="p_editPlan();"/>
<%
	}%>
	<input type="button" id="button" value="返 回" onclick="window.history.go(-1)"/>
</div>
</div>
</form>
</body>
</html>