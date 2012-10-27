<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%String ID = rd.getStringByDI("bx_GuideInfo","ID",0); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>导游报账</title>
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
    }
    var win = new GB_Window(options);
    return win.show(url);
}
</script>

</head>

<body>
<form  name="bx_group_form" method="post">
<input type="hidden" name="userno" value="<%sd.getString("userno"); %>"></input>
<%
String viewState = rd.getString("flag");
boolean isTrue = false;
if("view".equals(viewState)){
	isTrue = true;
}
%>
<div id="lable"><span >导游报账</span></div>
<div id="bm-table">
<div id="groupDiv">
<table class="datas" >
		<tr>
			<td colspan="4" ><span>团&nbsp;>>>&nbsp;<%=rd.getStringByDI("bx_GuideInfo","ID",0) %>&nbsp;基本信息</span></td>
		</tr>
		<tr>
		  <td align="right">&nbsp;&nbsp;发团日期：</td>
		  <td>
		  	<font color="red">&nbsp;<%=rd.getStringByDI("bx_GuideInfo","BEGIN_DATE",0) %></font>&nbsp;&nbsp;&nbsp;&nbsp;
		  </td>
		  <td>
		   	返团日期：&nbsp;&nbsp;&nbsp;<font color="red"><%=rd.getStringByDI("bx_GuideInfo","END_DATE",0) %></font>
		  </td>
		</tr>
		<tr>
			<td align="right">线路名称：</td>
			<td  >
				<img alt="热点线路" src="<%=request.getContextPath()%>/images/hot3.gif"/>&nbsp;
				<%String XLMC = rd.getStringByDI("bx_GuideInfo","XLMC",0); %>
				<font color="red"><%=XLMC.length()<=30?XLMC:XLMC.substring(0,30)+"..."  %></font></td>
			<td colspan="2"> 团号编号：
				<input type="hidden" value="28723" name="group_fkId"/>
				<%=rd.getStringByDI("bx_GuideInfo","ID",0) %>
			</td>
		</tr>
		<tr>
			<td align="right">人数：</td>
			<td >儿童:<%=rd.getStringByDI("bx_GuideInfo","ETRS",0) %>人&nbsp;成人:<%=rd.getStringByDI("bx_GuideInfo","CRRS",0) %>人</td>
			<td >导游姓名： <%=rd.getStringByDI("bx_GuideInfo","DYXM",0) %></td>
		</tr>
		<tr>
			<td align="right">备注：</td>
			<%String TSYQ = rd.getStringByDI("bx_GuideInfo","TSYQ",0); %>
			<td> <%=TSYQ.length()<=70?TSYQ:TSYQ.substring(0,70)+"..."  %></td>
			<td>
				全陪人数:<%=rd.getStringByDI("TA_DJ_GROUPs","QPRS",0) %>&nbsp;
				姓名:<%=rd.getStringByDI("TA_DJ_GROUPs","QPXM",0) %>&nbsp;
				电话:<%=rd.getStringByDI("TA_DJ_GROUPs","QPDH",0) %>
			</td>
		</tr>
</table>
</div>
		<div id="guideDiv">
		<%
		int newTraverows = rd.getTableRowsCount("TA_DJ_BXDYs");
		String dylk = rd.getStringByDI("allGuideFy","DYLK",0);
		String dff = rd.getStringByDI("allGuideFy","DFF",0);
		String dyjtf = rd.getStringByDI("allGuideFy","DYJTF",0);
		String qt=rd.getStringByDI("allGuideFy","QT",0);
		if(0 >= newTraverows){
			if(!isTrue){
		%>
		<table class="datas">
			<tr>
				<td><span>导游费用信息</span></td>
			</tr>
			<tr>
			<td>导游尚未报账(<span class="showPointer" style="text-decoration:underline;color:red;font-weight:100" onclick="return GB_myShow('导游费用报账','<%=request.getContextPath()%>/maindj/bx/bxGuide/djInitGuideRbt.?TA_DJ_BXDY/TID=<%=ID %>&TA_TDBXZJXXB/TID=<%=ID %>&flag=init','800','650','')">申请报账</span>)</td>
			</tr>
		</table>
		<%
		}}else{
		%>
		<table class="datas">
			<tr>
			<%if(isTrue){ %>
				<td><span>导游费用信息</span>&nbsp;&nbsp;<span class="showPointer" onclick="return GB_myShow('导游费用报账','<%=request.getContextPath() %>/maindj/bx/bxGuide/djInitGuideRbt.?TA_DJ_BXDY/TID=<%=ID %>&TA_TDBXZJXXB/TID=<%=ID %>&flag=view','800','650','')">查看报账明细</span></td>
			<%}else{ %>
				<td><span>导游费用信息</span>(<span class="showPointer" style="text-decoration:underline; color:blue;font-weight:100" onclick="return GB_myShow('导游费用报账','<%=request.getContextPath() %>/maindj/bx/bxGuide/djInitGuideRbt.?TA_DJ_BXDY/TID=<%=ID %>&TA_TDBXZJXXB/TID=<%=ID %>&flag=edit','800','650','')">修改报账</span>)</td>
			<%} %>
			</tr>
			<tr>
				<td align="right">
					导游费用合计:导游领款合计:<font color="red"><%=dylk %></font>元    导服费合计:<font color="red"><%=dff %></font>元 导游交通费合计:<font color="red"><%=dyjtf %></font>元&nbsp;其它费用:<font color="red"><%=qt %></font>元&nbsp;导游费用合计:<font color="red"><%=rd.getStringByDI("TA_TDBXZJXXBs","DYFYHJ",0) %></font>元&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		
		</table>
		<%} %>	
	</div>
	
	<div id="hotelDiv">
	<%
		int bxHotelRows=rd.getTableRowsCount("TA_DJ_BXHOTELs");
		String qdzszj=rd.getStringByDI("TA_TDBXZJXXBs","BXJDQD",0);
		String xfzszj=rd.getStringByDI("TA_TDBXZJXXBs","BXJDXF",0);
		String zszj=rd.getStringByDI("TA_TDBXZJXXBs","JDHJ",0);
		if(bxHotelRows<=0){
			if(!isTrue){
	%>
		<table class="datas">
			<tr>
				<td><span>酒店报帐信息</span></td>
			</tr>
			<tr>
				<td>酒店尚未报账(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('酒店费用报账','<%=request.getContextPath()%>/maindj/bx/bxHotel/initDjHotelBx.?TA_DJ_GROUP/ID=<%=ID%>&TA_DJ_BXHOTEL/TID=<%=ID %>&TID=<%=ID %>&flag=init','800','750','')">申请报账</span>)</td>
			</tr>
		</table>
	<%}}else{ %>
		<table class="datas">
			<tr>
			<%if(isTrue){ %>
				<td><span>酒店报帐信息</span>&nbsp;&nbsp;<span class="showPointer" onclick="return GB_myShow('酒店费用报账','<%=request.getContextPath()%>/maindj/bx/bxHotel/initDjHotelBx.?TA_DJ_GROUP/ID=<%=ID%>&TA_DJ_BXHOTEL/TID=<%=ID %>&TID=<%=ID %>&flag=view','800','750','')">查看报账明细</span></td>
			<%}else{ %>
				<td><span>酒店报帐信息</span>(<span class="showPointer" style="text-decoration:underline; color:blue;font-weight:100" onclick="return GB_myShow('酒店费用报账','<%=request.getContextPath()%>/maindj/bx/bxHotel/initDjHotelBx.?TA_DJ_GROUP/ID=<%=ID%>&TA_DJ_BXHOTEL/TID=<%=ID %>&TID=<%=ID %>&flag=edit','800','750','')">修改报账</span>)</td>
			<%} %>
			</tr>
			<tr>
				<td align="right">
					酒店费用合计：签单金额合计：<font color="red"><%="".equals(qdzszj)?0:qdzszj %></font>元  现付金额合计：<font color="red"><%="".equals(xfzszj)?0:xfzszj %></font>元  合计：<font color="red"><%="".equals(zszj)?0:zszj %></font>元&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		</table>
	<%} %>
	</div>

	<div id="dinnerDiv">
		<%
			int bxDinnerRows=rd.getTableRowsCount("TA_DJ_BXCTs");
			String qdctzj=rd.getStringByDI("TA_TDBXZJXXBs","BXCTQD",0);
			String xfctzj=rd.getStringByDI("TA_TDBXZJXXBs","BXCTXF",0);
			String ctzj=rd.getStringByDI("TA_TDBXZJXXBs","CTHJ",0);
			if(0>=bxDinnerRows){
				if(!isTrue){
		%>
		<table class="datas">
			<tr>
				<td><span>餐厅报帐信息</span></td>
			</tr>
			<tr>
				<td>餐厅尚未报账(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('餐厅费用报账','<%=request.getContextPath()%>/maindj/bx/bxRestaurant/initDjDinnerBx.?TA_DJ_GROUP/ID=<%=ID%>&TA_DJ_BXCT/TID=<%=ID %>&TID=<%=ID %>&flag=init','800','680','')">申请报账</span>)</td>
			</tr>
		</table>
		<%}}else{ %>
		<table class="datas">
			<tr>
			<%if(isTrue){ %>
			<td><span>餐厅报帐信息</span>&nbsp;&nbsp;<span class="showPointer" onclick="return GB_myShow('餐厅费用报账','<%=request.getContextPath()%>/maindj/bx/bxRestaurant/initDjDinnerBx.?TA_DJ_GROUP/ID=<%=ID%>&TA_DJ_BXCT/TID=<%=ID %>&TID=<%=ID %>&flag=view','800','680','')">查看报账明细</span></td>
			<%}else{ %>
			<td><span>餐厅报帐信息</span>(<span class="showPointer" style="text-decoration:underline; color:blue;font-weight:100" onclick="return GB_myShow('餐厅费用报账','<%=request.getContextPath()%>/maindj/bx/bxRestaurant/initDjDinnerBx.?TA_DJ_GROUP/ID=<%=ID%>&TA_DJ_BXCT/TID=<%=ID %>&TID=<%=ID %>&flag=edit','800','680','')">修改报账</span>)</td>
			<%} %>
			</tr>
			<tr>
				<td align="right">
					餐厅费用合计：签单金额合计：<font color="red"><%="".equals(qdctzj)?0:qdctzj %></font>元  现付金额合计：<font color="red"><%="".equals(xfctzj)?0:xfctzj %></font>元  合计：<font color="red"><%="".equals(ctzj)?0:ctzj %></font>元&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		<%} %>
	</div>
	<div id="ticketDiv">
	<%
		int bxTicketRows=rd.getTableRowsCount("TA_DJ_BXPWs");
		String qdpwzj=rd.getStringByDI("TA_TDBXZJXXBs","BXPWQD",0);
		String xfpwzj=rd.getStringByDI("TA_TDBXZJXXBs","BXPWXF",0);
		String pwzj=rd.getStringByDI("TA_TDBXZJXXBs","PWHJ",0);
		String sxfzj=rd.getStringByDI("TA_TDBXZJXXBs","SXFZJ",0);
		if(0>=bxTicketRows){
			if(!isTrue){
	%>
		<table class="datas">
			<tr>
				<td><span>票务报帐信息</span></td>
			</tr>
			<tr>
				<td>票务尚未报帐(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('票务报账','<%=request.getContextPath()%>/maindj/bx/bxTicket/djInitTicketBx.?TA_DJ_BXPW/TID=<%=ID %>&TA_DJ_JHPW/TID=<%=ID %>&dmsm/JTGJ=2&flag=init','800','850','')">申请报账</span>)</td>
			</tr>
		</table>
	<%}}else{ %>
		<table class="datas">
			<tr>
			<%if(isTrue){ %>
			<td><span>票务报帐信息</span>&nbsp;&nbsp;<span class="showPointer"  onclick="return GB_myShow('票务报账','<%=request.getContextPath()%>/maindj/bx/bxTicket/djInitTicketBx.?TA_DJ_BXPW/TID=<%=ID %>&TA_DJ_JHPW/TID=<%=ID %>&dmsm/JTGJ=2&flag=view','800','850','')">查看报账明细</span></td>
			<%}else{ %>
			<td><span>票务报帐信息</span>(<span class="showPointer" style="text-decoration:underline; color:blue;font-weight:100" onclick="return GB_myShow('票务报账','<%=request.getContextPath()%>/maindj/bx/bxTicket/djInitTicketBx.?TA_DJ_BXPW/TID=<%=ID %>&TA_DJ_JHPW/TID=<%=ID %>&dmsm/JTGJ=2&flag=edit','800','850','')">修改报账</span>)</td>
			<%} %>
			</tr>
			<tr>
				<td align="right">
					票务费用合计：签单金额合计：<font color="red"><%="".equals(qdpwzj)?0:qdpwzj %></font>元  现付金额合计：<font color="red"><%="".equals(xfpwzj)?0:xfpwzj %></font>元  合计：<font color="red"><%="".equals(pwzj)?0:pwzj %></font>元  手续费总计：<font color="red"><%="".equals(sxfzj)?0:sxfzj %></font>元&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		</table>
	<%} %>
	</div>
	<div id="carDiv">
	<%
		int bxCarRows=rd.getTableRowsCount("TA_DJ_BXCLs");
		String qdclzj=rd.getStringByDI("TA_TDBXZJXXBs","BXCLQD",0);
		String xfclzj=rd.getStringByDI("TA_TDBXZJXXBs","BXCLXF",0);
		String clzj=rd.getStringByDI("TA_TDBXZJXXBs","CLHJ",0);
		if(0>=bxCarRows){
			if(!isTrue){
	%>
		<table class="datas">
			<tr>
				<td><span>车辆报账信息</span></td>
			</tr>
			<tr>
				<td>车辆尚未报账(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('车辆报账','<%=request.getContextPath()%>/maindj/bx/bxCar/djInitVehBx.?TA_DJ_JHCL/TID=<%=ID %>&TA_DJ_BXCL/TID=<%=ID %>&flag=init','800','850','')">申请报账</span>)</td>
			</tr>
		</table>
	<%}}else{ %>
		<table class="datas">
			<tr>
			<%if(isTrue){ %>
			<td><span>车辆报账信息</span>&nbsp;&nbsp;<span class="showPointer" onclick="return GB_myShow('车辆报账','<%=request.getContextPath()%>/maindj/bx/bxCar/djInitVehBx.?TA_DJ_JHCL/TID=<%=ID %>&TA_DJ_BXCL/TID=<%=ID %>&flag=view','800','850','')">查看报账明细</span></td>
			<%}else{ %>
			<td><span>车辆报账信息</span>(<span class="showPointer" style="text-decoration:underline; color:blue;font-weight:100" onclick="return GB_myShow('车辆报账','<%=request.getContextPath()%>/maindj/bx/bxCar/djInitVehBx.?TA_DJ_JHCL/TID=<%=ID %>&TA_DJ_BXCL/TID=<%=ID %>&flag=edit','800','850','')">修改报账</span>)</td>
			<%} %>
			</tr>
			<tr>
				<td  align="right">车辆费用合计：签单金额合计：<font color="red"><%="".equals(qdclzj)?0:qdclzj %></font>元  现付金额合计：<font color="red"><%="".equals(xfclzj)?0:xfclzj %></font>元  合计：<font color="red"><%="".equals(clzj)?0:clzj %></font>元&nbsp;&nbsp;&nbsp;&nbsp;</td>	
			</tr>
		</table>
	<%} %>
	</div>
	
	<div id="travelDiv">
	<%
		int djsRows = rd.getTableRowsCount("TA_DJ_BXDJs");
		if(0 >=djsRows){
			if(!isTrue){
	%>
		<table class="datas">
			<tr>
				<td><span>地接报账信息</span></td>
			</tr>
			<tr>
				<td>地接尚未报账(<span class="showPointer" style="text-decoration:underline;color:red;font-weight:100" onclick="return GB_myShow('地接社报账','<%=request.getContextPath()%>/maindj/bx/bxGroundTrave/djInitdjsInfo.?TA_DJ_BXDJ/TID=<%=ID %>&flag=init','800','600')">申请报账</span>)</td>
			</tr>
		</table>
	<%
	}}else{
	%>
	<table class="datas">
			<tr>
			<%if(isTrue){ %>
			<td><span>地接报账信息</span>&nbsp;&nbsp;<span class="showPointer"  onclick="return GB_myShow('地接社报账','<%=request.getContextPath()%>/maindj/bx/bxGroundTrave/djInitdjsInfo.?TA_DJ_BXDJ/TID=<%=ID %>&flag=view','800','600')">查看报账明细</span></td>
			<%}else{ %>
			<td><span>地接报账信息</span>(<span class="showPointer" style="text-decoration:underline; color:blue;font-weight:100" onclick="return GB_myShow('地接社报账','<%=request.getContextPath()%>/maindj/bx/bxGroundTrave/djInitdjsInfo.?TA_DJ_BXDJ/TID=<%=ID %>&flag=edit','800','600')">修改报账</span>)</td>
			<%} %>
			</tr>
			<tr>
				<td  align="right">地接社名称: <font color="red"><%int TraveRows= rd.getTableRowsCount("DjsNameInfo"); for(int i = 0; i< TraveRows; i++){String TraveName = rd.getStringByDI("DjsNameInfo","cmpny_name",i);%><%=TraveName %> <%} %></font>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td  align="right">地接社费用合计：签单金额合计：<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","DJQDZJ",0))?0:rd.getString("TA_TDBXZJXXBs","DJQDZJ",0) %></font>元&nbsp;&nbsp;&nbsp;&nbsp;现付金额合计：<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","DJXFZJ",0))?0:rd.getString("TA_TDBXZJXXBs","DJXFZJ",0) %></font>元&nbsp;&nbsp;&nbsp;&nbsp;应付团款合计：<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","DJHJ",0))?0:rd.getString("TA_TDBXZJXXBs","DJHJ",0) %></font>元&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
		</table>
		<% }%>
	</div>
	
	<div id="scenerylDiv">
	<%
		int sceneryRows = rd.getTableRowsCount("TA_DJ_BXJDs");
		if(0 >= sceneryRows){
			if(!isTrue){
	%>
		<table class="datas">
			<tr>
				<td><span>景点报账信息</span></td>
			</tr>
			<tr>
				<td>景点尚未报账(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('查看景点报账信息','<%=request.getContextPath() %>/maindj/businessPlan/plan/sceneryPlan/djInitSceneryRbt.?TA_DJ_BXJD/TID=<%=ID %>&flag=init','800','700','')">申请报账</span>)</td>
			</tr>
		</table>
		<%}}else{ %>
		<table class="datas">
			<tr>
			<%if(isTrue){ %>
			<td><span>景点报账信息</span>&nbsp;&nbsp;<span class="showPointer" onclick="return GB_myShow('查看景点报账信息','<%=request.getContextPath() %>/maindj/businessPlan/plan/sceneryPlan/djInitSceneryRbt.?TA_DJ_BXJD/TID=<%=ID %>&flag=view','800','700','')">查看报账明细</span></td>
			<%}else{ %>
			<td><span>景点报账信息</span>(<span class="showPointer" style="text-decoration:underline; color:blue;font-weight:100" onclick="return GB_myShow('查看景点报账信息','<%=request.getContextPath() %>/maindj/businessPlan/plan/sceneryPlan/djInitSceneryRbt.?TA_DJ_BXJD/TID=<%=ID %>&flag=edit','800','700','')">修改报账</span>)</td>
			<%} %>
			</tr>
			<tr>
				<td  align="right">景点内容：<font color="red"><%for(int i=0;i<rd.getTableRowsCount("querySceneryInfo");i++){out.print(rd.getStringByDI("querySceneryInfo","cmpny_name",i)+" ");} %></font>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td  align="right">景点费用合计：签单金额合计：<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","BX_JDQD",0))?0:rd.getString("TA_TDBXZJXXBs","BX_JDQD",0) %></font>元&nbsp;&nbsp;&nbsp;&nbsp;现付金额合计：<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","BX_JDXF",0))?0:rd.getString("TA_TDBXZJXXBs","BX_JDXF",0) %></font>元&nbsp;&nbsp;&nbsp;&nbsp;合计：<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","JINDHJ",0))?0:rd.getString("TA_TDBXZJXXBs","JINDHJ",0) %></font>元&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
		</table>
		<%} %>
	</div>
	
	<div id="shoplDiv">
	<%
		String QPRSZT ="Y";
		String QPRS= rd.getString("bx_GuideInfo","QPRS",0);
		if("".equals(QPRS)){QPRS="0";}
		int numRs = Integer.parseInt(QPRS);
		if(numRs>0){
			 QPRSZT = "Y"; 
		}else{
			 QPRSZT = "N"; 
		}
		String CRRS = rd.getStringByDI("bx_GuideInfo","CRRS",0);
		int shopRows = rd.getTableRowsCount("TA_DJ_BXGWs"); 
		String gw=rd.getStringByDI("TA_DJ_GROUPs","GW",0);
		if("1".equals(gw)){
		if(0 >= shopRows){
			if(!isTrue){
	%>
		<table class="datas">
			<tr>
				<td><span>购物报账信息</span>(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('查看购物报账信息','<%=request.getContextPath() %>/maindj/businessPlan/plan/sceneryPlan/djInitShopRbt.?TCCY=18&LCBL=21&TA_DJ_BXGW/TID=<%=ID %>&TA_TDBXZJXXB/TID=<%=ID %>&flag=init&qprszt=<%=QPRSZT %>&CRRS=<%=CRRS %>','800','700','')">申请报账</span>)</td>
			</tr>
			<tr>
				<td></td>
			</tr>
		</table>
		<%}}else{ %>
		<table class="datas">
			<tr>
			<%if(isTrue){ %>
			<td><span>购物报账信息</span><span class="showPointer"  onclick="return GB_myShow('查看购物报账信息','<%=request.getContextPath() %>/maindj/businessPlan/plan/sceneryPlan/djInitShopRbt.?TCCY=18&LCBL=21&TA_DJ_BXGW/TID=<%=ID %>&TA_TDBXZJXXB/TID=<%=ID %>&flag=view&qprszt=<%=QPRSZT %>&CRRS=<%=CRRS %>','800','700','')">&nbsp;&nbsp;查看报账明细</span></td>
			<%}else{ %>
			<td><span>购物报账信息</span>(<span class="showPointer" style="text-decoration:underline; color:blue;font-weight:100" onclick="return GB_myShow('查看购物报账信息','<%=request.getContextPath() %>/maindj/businessPlan/plan/sceneryPlan/djInitShopRbt.?TCCY=18&LCBL=21&TA_DJ_BXGW/TID=<%=ID %>&TA_TDBXZJXXB/TID=<%=ID %>&flag=edit&qprszt=<%=QPRSZT %>&CRRS=<%=CRRS %>','800','700','')">修改报账</span>)</td>
			<%} %>
			</tr>
			<tr>
				<td  align="right">购物内容：<font color="red"><%for(int i=0;i<rd.getTableRowsCount("queryShopInfo");i++){out.print(rd.getStringByDI("queryShopInfo","cmpny_name",i)+" ");} %></font>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td  align="right">购物费用合计：购物人数合计：<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","GWRSHJ",0))?0:rd.getString("TA_TDBXZJXXBs","GWRSHJ",0) %></font>人&nbsp;&nbsp;&nbsp;&nbsp;购物人头费合计：<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","GWRTFHJ",0))?0:rd.getString("TA_TDBXZJXXBs","GWRTFHJ",0) %></font>元&nbsp;&nbsp;&nbsp;&nbsp;购物消费额合计：<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","GWXFEHJ",0))?0:rd.getString("TA_TDBXZJXXBs","GWXFEHJ",0) %></font>元&nbsp;&nbsp;&nbsp;&nbsp;购物留存合计：<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","GWGSLCHJ",0))?0:rd.getString("TA_TDBXZJXXBs","GWGSLCHJ",0) %></font>元&nbsp;&nbsp;&nbsp;&nbsp;购物应交公司现金合计：<font color="red"><%=rd.getString("TA_TDBXZJXXBs","GWYJGSHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXBs","GWYJGSHJ",0) %></font>元&nbsp;&nbsp;&nbsp;&nbsp;购物公司利润合计：<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","GWLRHJ",0))?0:rd.getString("TA_TDBXZJXXBs","GWLRHJ",0) %></font>元&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
		</table>
		<%}} %>
	</div>
	
	<div id="plusDiv">
	<%
		int plusRows = rd.getTableRowsCount("TA_DJ_BXJIADIANs");
		String jd=rd.getStringByDI("TA_DJ_GROUPs","JD",0);
		if("1".equals(jd)){
		if(0 >= plusRows){
			if(!isTrue){
	%>
		<table class="datas">
			<tr>
				<td><span>加点报账信息</span>(<span class="showPointer" style=" text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('查看加点报账信息','<%=request.getContextPath() %>/maindj/businessPlan/plan/sceneryPlan/djInitPlusRbt.?TCCY=18&LCBL=21&TA_DJ_BXJIADIAN/TID=<%=ID %>&TA_TDBXZJXXB/TID=<%=ID %>&flag=init&qprszt=<%=QPRSZT %>','800','700','')">申请报账</span>)</td>
			</tr>
			<tr>
				<td></td>
			</tr>
		</table>
		<%}}else{ %>
		<table class="datas">
			<tr>
			<%if(isTrue){ %>
			<td><span>加点报账信息</span>&nbsp;&nbsp;<span class="showPointer" onclick="return GB_myShow('查看加点报账信息','<%=request.getContextPath() %>/maindj/businessPlan/plan/sceneryPlan/djInitPlusRbt.?TCCY=18&LCBL=21&TA_DJ_BXJIADIAN/TID=<%=ID %>&TA_TDBXZJXXB/TID=<%=ID %>&flag=view&qprszt=<%=QPRSZT %>','800','700','')">查看报账明细</span></td>
			<%}else{ %>
			<td><span>加点报账信息</span>(<span class="showPointer" style=" text-decoration:underline; color:blue;font-weight:100" onclick="return GB_myShow('查看加点报账信息','<%=request.getContextPath() %>/maindj/businessPlan/plan/sceneryPlan/djInitPlusRbt.?TCCY=18&LCBL=21&TA_DJ_BXJIADIAN/TID=<%=ID %>&TA_TDBXZJXXB/TID=<%=ID %>&flag=Edit&qprszt=<%=QPRSZT %>','800','700','')">修改报账</span>)</td>
			<%} %>
			</tr>
			<tr>
				<td  align="right">加点内容：<font color="red"><%for(int i=0;i<rd.getTableRowsCount("queryPlusInfo");i++){out.print(rd.getStringByDI("queryPlusInfo","cmpny_name",i)+" ");} %></font>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td  align="right">费用合计：签单金额合计：<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","JDQDHJ",0))?0:rd.getString("TA_TDBXZJXXBs","JDQDHJ",0) %></font>元&nbsp;&nbsp;&nbsp;&nbsp;现付金额合计：<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","JDXFHJ",0))?0:rd.getString("TA_TDBXZJXXBs","JDXFHJ",0) %></font>元&nbsp;&nbsp;&nbsp;&nbsp;人数合计：<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","JDRSHJ",0))?0:rd.getString("TA_TDBXZJXXBs","JDRSHJ",0) %></font>人&nbsp;&nbsp;&nbsp;&nbsp;人头费合计：<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","JDRTFHJ",0))?0:rd.getString("TA_TDBXZJXXBs","JDRTFHJ",0) %></font>元&nbsp;&nbsp;&nbsp;&nbsp;成本合计：<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","JDCBHJ",0))?0:rd.getString("TA_TDBXZJXXBs","JDCBHJ",0) %></font>元&nbsp;&nbsp;&nbsp;&nbsp;净利合计：<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","JDLRHJ",0))?0:rd.getString("TA_TDBXZJXXBs","JDLRHJ",0) %></font>元&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
		</table>
		<%}} %>
	</div>
	<div id="SumPrice">
		<table class="datas">
			<tr>
				<td ><span>费用合计</span></td>
			</tr>
			<%
				String YSKZJ = rd.getString("bx_GuideInfo","YSKZJ",0);//营收
				Float yskzjs=YSKZJ.length()>0?Float.parseFloat(YSKZJ):0;
				String JDHJ = rd.getString("TA_TDBXZJXXBs","JDHJ",0);//酒店合计
				Float jdhjs=JDHJ.length()>0?Float.parseFloat(JDHJ):0;
				String CTHJ = rd.getString("TA_TDBXZJXXBs","CTHJ",0);//餐厅合计
				Float cthjs=CTHJ.length()>0?Float.parseFloat(CTHJ):0;
				String PWHJ = rd.getString("TA_TDBXZJXXBs","PWHJ",0);//票务合计
				Float pwhjs=PWHJ.length()>0?Float.parseFloat(PWHJ):0;
				String CLHJ = rd.getString("TA_TDBXZJXXBs","CLHJ",0);//车辆合计
				Float clhjs=CLHJ.length()>0?Float.parseFloat(CLHJ):0;
				String JINDHJ = rd.getString("TA_TDBXZJXXBs","JINDHJ",0);//景点合计
				Float jindhjs=JINDHJ.length()>0?Float.parseFloat(JINDHJ):0;
				String DJHJ = rd.getString("TA_TDBXZJXXBs","DJHJ",0);//地接合计
				Float djhjs=DJHJ.length()>0?Float.parseFloat(DJHJ):0;
				String GWGSLCHJ = rd.getString("TA_TDBXZJXXBs","GWGSLCHJ",0);//购物公司留存合计
				Float gwgslchjs=GWGSLCHJ.length()>0?Float.parseFloat(GWGSLCHJ):0;
				String JDRTFHJ = rd.getString("TA_TDBXZJXXBs","JDRTFHJ",0);//加点人头费合计
				Float jdrtfhjs=JDRTFHJ.length()>0?Float.parseFloat(JDRTFHJ):0;
				
				String JDQD = rd.getString("TA_TDBXZJXXBs","BXJDQD",0);//酒店签单
				Float jdqds=JDQD.length()>0?Float.parseFloat(JDQD):0;
				String CTQD = rd.getString("TA_TDBXZJXXBs","BXCTQD",0);//餐厅签单
				Float ctqds=CTQD.length()>0?Float.parseFloat(CTQD):0;
				String PWQD = rd.getString("TA_TDBXZJXXBs","BXPWQD",0);//票务签单
				Float pwqds=PWQD.length()>0?Float.parseFloat(PWQD):0;
				String CLQD = rd.getString("TA_TDBXZJXXBs","BXCLQD",0);//车辆签单
				Float clqds=CLQD.length()>0?Float.parseFloat(CLQD):0;
				String SceneryQD = rd.getString("TA_TDBXZJXXBs","BX_JDQD",0);//景点签单
				Float sceneryqds=SceneryQD.length()>0?Float.parseFloat(SceneryQD):0;
				String DJSQD = rd.getString("TA_TDBXZJXXBs","DJQDZJ",0);//地接社签单
				Float djsqds=DJSQD.length()>0?Float.parseFloat(DJSQD):0;
				
				String JDLRHJ =rd.getString("TA_TDBXZJXXBs","JDLRHJ",0);//加点利润合计
				Float jdlrhjs=JDLRHJ.length()>0?Float.parseFloat(JDLRHJ):0;
				String DYLK = rd.getString("allGuideFy","DYLK",0);//导游领款
				Float dylks=DYLK.length()>0?Float.parseFloat(DYLK):0;
				String JDXF = rd.getString("TA_TDBXZJXXBs","BXJDXF",0);//酒店现付
				Float jdxfs=JDXF.length()>0?Float.parseFloat(JDXF):0;
				String CTXF = rd.getString("TA_TDBXZJXXBs","BXCTXF",0);//餐厅现付
				Float ctxfs=CTXF.length()>0?Float.parseFloat(CTXF):0;
				String PWXF = rd.getString("TA_TDBXZJXXBs","BXPWXF",0);//票务现付
				Float pwxfs=PWXF.length()>0?Float.parseFloat(PWXF):0;
				String CLXF = rd.getString("TA_TDBXZJXXBs","BXCLXF",0);//车辆现付
				Float clxfs=CLXF.length()>0?Float.parseFloat(CLXF):0;
				String SceneryXF = rd.getString("TA_TDBXZJXXBs","BX_JDXF",0);//景点现付
				Float sceneryxfs=SceneryXF.length()>0?Float.parseFloat(SceneryXF):0;
				String  DJSXF = rd.getString("TA_TDBXZJXXBs","DJXFZJ",0);//地接社现付
				Float djxf=DJSXF.length()>0?Float.parseFloat(DJSXF):0;
				String PLUSXF = rd.getString("TA_TDBXZJXXBs","JDXFHJ",0);//加点现付
				Float plusxfs=PLUSXF.length()>0?Float.parseFloat(PLUSXF):0;
				String GWLRHJ = rd.getString("TA_TDBXZJXXBs","GWLRHJ",0);//购物利润合计
				Float gwlrhjs=GWLRHJ.length()>0?Float.parseFloat(GWLRHJ):0;
				String GWYJGSHJ = rd.getString("TA_TDBXZJXXBs","GWYJGSHJ",0);//购物应交公司
				Float gwyjgshjs=GWYJGSHJ.length()>0?Float.parseFloat(GWYJGSHJ):0;
				String JDYJGSHJ = rd.getString("TA_TDBXZJXXBs","JDYJGSHJ",0);//加点应交公司
				Float jdyjgshjs=JDYJGSHJ.length()>0?Float.parseFloat(JDYJGSHJ):0;
				
				//司陪住宿费
				String spzsf=rd.getStringByDI("TA_DJ_BXHOTELs","SFZSF",0);
	  			float sp=spzsf.length()>0?Float.parseFloat(spzsf):0;
	  			//营收
	  			String yskzj=rd.getStringByDI("TA_DJ_GROUPs","YSKZJ",0);
	  			float ys=yskzj.length()>0?Float.parseFloat(yskzj):0;
				
				//应交财务现金合计 = 导游领款 - 现付合计 + 购物应交公司 + 加点应交公司
				Float YJCWXJHJ = dylks-jdxfs-ctxfs-pwxfs-clxfs-sceneryxfs-djxf+gwyjgshjs+jdyjgshjs;
				
				//购物利润合计 = 购物人头费合计 + 应交公司总计 + 留存总计
				//购物应交公司总计=(应交公司合计-有全陪为0,无全陪则为全陪提成)
				//加点应交公司总计=(应交公司合计-有全陪为0,无全陪则为全陪提成)+人头费合计+利润合计的10%
				
				//成本支出=酒店合计+餐厅合计+票务合计+车辆合计+景点合计+地接合计+司陪住宿费
				Float cbzc= jdhjs+cthjs+pwhjs+clhjs+jindhjs+djhjs;
				Float cbqd= jdqds+ctqds+pwqds+clqds+sceneryqds+djsqds;
				Float cbxf= jdxfs+ctxfs+pwxfs+clxfs+sceneryxfs+djxf;
				//团队利润 = 营收 - 合计 + 公司留存 + 购物利润合计  + 购物人头费 + 加点人头费 + 加点利润合计  
				Float TDLR = yskzjs - cbzc  + gwlrhjs+ gwgslchjs + jdlrhjs +jdrtfhjs;
				
				Float zmml = yskzjs - cbzc;
			%>
			<tr>
				<td>
					营收:<font color="red"><%=ys %></font>&nbsp;&nbsp;&nbsp;&nbsp;
					签单金额总计：<font color="red"><%=cbqd %></font>元&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="hidden" name="TA_TDBXZJXXB/ZCQDXJ" value="<%=cbqd %>"/>
					现付金额总计：<font color="red"><%=cbxf %></font>元&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="hidden" name="TA_TDBXZJXXB/ZCXFXJ" value="<%=cbxf %>"/>
					支出合计：<font color="red"><%=cbzc %></font>元
					<input type="hidden" name="TA_TDBXZJXXB/ZCFYHJ" value="<%=cbzc %>"/>
					账面毛利：<font color="red"><%=zmml %></font>元
				</td>
			</tr>
			<tr>
				<td>
					购物公司利润：<font color="red"><%=gwlrhjs %></font>元&nbsp;&nbsp;&nbsp;&nbsp;
					购物应交公司现金：<font color="red"><%=gwyjgshjs %></font>元&nbsp;&nbsp;&nbsp;&nbsp;
					加点净利：<font color="red"><%="".equals(JDLRHJ)?0:JDLRHJ %></font>元&nbsp;&nbsp;&nbsp;&nbsp;
					加点应交公司现金：<font color="red"><%=jdyjgshjs %></font>元&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td>
				应交财务现金合计：<font color="red"><%=YJCWXJHJ %></font>元&nbsp;&nbsp;&nbsp;&nbsp;
				团队利润：<font color="red"><%=TDLR %></font>元&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="hidden" name="TA_TDBXZJXXB/YJCWXJHJ" value="<%=YJCWXJHJ %>"/>
				<input type="hidden" name="TA_TDBXZJXXB/TDLR" value="<%=TDLR %>"/>
				</td>
			</tr>
		</table>
</div>

	<%
		int yjRows=rd.getTableRowsCount("SPYJ");
		if(yjRows>0){
	%>
	<div>
		<table class="datas">
			<tr>
				<td  colspan="2"><span>审批意见</span>&nbsp;&nbsp;&nbsp;&nbsp;<%String USERID = sd.getString("USERID"); if("admin".equals(USERID)||"ywbzg".equals(USERID)||"lkj".equals(USERID)||"ywy".equals(USERID)){%><span class="showPointer" style=" text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('查看数据对比信息','<%=request.getContextPath()%>/maindj/bx/bxSh/djBxBJ.?TID=<%=rd.getStringByDI("bx_GuideInfo","ID",0) %>'),'500','400',''">数据对比&nbsp;</span><img src="<%=request.getContextPath()%>/images/Contrast.gif" height="16" width="16"  class="showPointer" onclick="return GB_myShow('查看加点报账信息','<%=request.getContextPath()%>/maindj/bx/bxSh/djBxBJ.?TID=<%=rd.getStringByDI("bx_GuideInfo","ID",0) %>'),'500','400',''"/><%} %></td>
			</tr>
			
					<%
						
							for(int i=0;i<yjRows;i++){
					%>
					<tr>
						<td  colspan="2">
							<b><%=rd.getStringByDI("SPYJ","USERNAME",i)%>:</b>
							<%=rd.getStringByDI("SPYJ","SPYJ",i)%>;
						</td>
					</tr>
					<%
							}
					%>
				
			
		</table>
	</div>
	<%} %>
</div>
<script type="text/javascript">
	/**
	function bxShSub(){
		if(<%=newTraverows%>>0 && <%=bxHotelRows%>>0 && <%=bxDinnerRows%>>0 && <%=bxTicketRows%>>0 && <%=bxCarRows%>>0 && <%=djsRows%>>0 && <%=sceneryRows%>>0 && <%=shopRows%>>0 && <%=plusRows%>>0){
			document.bxBJ_form.action="djInitShBxList.?pageNO=1&pageSize=10&definitionid=bxsh&TID=<%=ID %>";
			document.bxBJ_form.submit();
			
		}else{
			alert("导游报账未填写完整,请填写完整再提交!");
		}
	}*/
	function bxShSub(){
		document.bx_group_form.action="djInitShBxList.?pageNO=1&pageSize=10&definitionid=djbxsh&TID=<%=ID %>";
		document.bx_group_form.submit();
	}
</script>
<div align="center" id="last-sub">
<%if(!isTrue){ %>
	<input type="button" id="button" value="提交" onclick="bxShSub();"/>
<%} %>
	<input type="button" id="button" value="返回" onclick="window.history.go(-1);"/>
</div>
</form>
</body>
</html>
