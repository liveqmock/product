<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
 <%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.apache.axis.types.Month"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>团队计调信息与报账信息比较</title>
<link href="<%=request.getContextPath() %>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>

<%String groupID = rd.getString("TID"); %>
<script type="text/javascript">
function Sub(){
	document.bx_group_form.action="djBxBJ.?TID=<%=groupID %>";
	document.bx_group_form.submit();
}
function reload(){
	parent.parent.location.reload(); 
	parent.parent.GB_hide(); 
}

function openCompareValue(where,tid){

	if('g' == where) {//导游
		window.open("<%=request.getContextPath() %>/maindj/businessPlan/plan/guidePlan/djInitEditGuidePlan.?role=guide&TA_DJ_JHDY/ID=&TID="+tid+"&action=view"
			,'数据比对','width=600, height=300, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
	if('h' == where) {//酒店 
		window.open("<%=request.getContextPath()%>/maindj/businessPlan/plan/hotelPlan/djSelectHotelPlanInfo.?TA_DJ_GROUP/ID="+tid+"&TA_DJ_JHHOTEL/TID="+tid+"&TA_TDJDXXZJB/TID="+tid+"&TID="+tid
			,'数据比对','width=900, height=500, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
	if('c' == where) {//餐厅 
		window.open("<%=request.getContextPath()%>/maindj/businessPlan/plan/dinnerPlan/djListDinnerPlan.?TA_DJ_GROUP/ID="+tid+"&TA_DJ_JHCT/ID=&TID="+tid
			,'数据比对','width=700, height=500, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
	if('t' == where) {//票务
		window.open("<%=request.getContextPath()%>/maindj/businessPlan/plan/ticketPlan/djInitTicketP.?TA_DJ_JHPW/tID="+tid+"&TID="+tid+"&dmsm/JTGJ=2&flag=view&TA_TDJDXXZJB/TID="+tid+"&TA_DJ_JHPWMX/tid="+tid
			,'数据比对','width=750, height=500, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
	if('v' == where) {//车辆
		window.open("<%=request.getContextPath()%>/maindj/businessPlan/plan/vehPlan/djInitVehPlan.?TA_DJ_JHCL/TID="+tid+"&TA_TDJDXXZJB/TID="+tid+"&flag=view"
			,'数据比对','width=800, height=500, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
	if('v' == where) {//地接
		window.open("<%=request.getContextPath()%>/maindj/businessPlan/plan/groundTravePlan/djInitGroundPlan.?TA_DJ_JHDJ/TID="+tid+"&TA_TDJDXXZJB/TID="+tid+"&flag=view"
			,'数据比对','width=700, height=500, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
	if('s' == where) {//景点
		window.open("<%=request.getContextPath()%>/maindj/businessPlan/plan/sceneryPlan/djInitSceneryPlan.?TA_TDJDXXZJB/TID="+tid+"&TA_DJ_JHJD/TID="+tid+"&flag=view"
			,'数据比对','width=700, height=500, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
}
</script>
</head>
<body>
<form  name="bxBJ_form" method="post">
<div id="lable"><span>团队计调信息与报账信息比较</span></div>
<div id="list-table">
<div  id="thisSelect-table" >
	<table class="datas" >
		<tr><td colspan="5" align="left">&nbsp;&nbsp;<span>团号：</span><%=groupID %></td></tr>
		<tr id="first-tr">
		  <td>&nbsp;&nbsp;比对项目：&nbsp;&nbsp;</td><td>&nbsp;&nbsp;计调内容↓&nbsp;&nbsp;</td><td>报账内容↓&nbsp;&nbsp;</td><td>增减费用↓&nbsp;&nbsp;</td><td>&nbsp;&nbsp;比对结果↓&nbsp;&nbsp;</td>
		</tr>
		<%
		String tID = rd.getStringByDI("bxBjInfo","tid",0);
		
		String JDDYLK = rd.getString("planBjInfo","LTKZJ",0);if("".equals(JDDYLK)){JDDYLK="0";}//导游计调领款总计
		String BXDYLK = rd.getString("bxBjInfo","LTKZJ",0);if("".equals(BXDYLK)){BXDYLK="0";}//导游报账领款总计
		String JDHOTEL = rd.getString("planBjInfo","ZSZJ",0);if("".equals(JDHOTEL)){JDHOTEL="0";}//计调住宿总计
		String BXHOTEL = rd.getString("bxBjInfo","ZSZJ",0);if("".equals(BXHOTEL)){BXHOTEL="0";}//报账住宿总计
		Float HOTELZJ = Float.parseFloat(BXHOTEL)-Float.parseFloat(JDHOTEL);//酒店增减费用 
		String JDCT = rd.getString("planBjInfo","CTZJ",0);if("".equals(JDCT)){JDCT="0";}//计调餐厅总计
		String BXCT = rd.getString("bxBjInfo","CTZJ",0);if("".equals(BXCT)){BXCT="0";}//报账餐厅总计
		Float CTZJ = Float.parseFloat(BXCT)-Float.parseFloat(JDCT);//餐厅增减费用
		String JDPW = rd.getString("planBjInfo","PWZJ",0);if("".equals(JDPW)){JDPW="0";}//计调票务总计
		String BXPW = rd.getString("bxBjInfo","PWZJ",0);if("".equals(BXPW)){BXPW="0";}//报账票务总计
		Float PWZJ = Float.parseFloat(BXPW)-Float.parseFloat(JDPW);//票务增减费用
		String JDCL = rd.getString("planBjInfo","CLZJ",0);if("".equals(JDCL)){JDCL="0";}//计调车辆总计
		String BXCL = rd.getString("bxBjInfo","CLZJ",0);if("".equals(BXCL)){BXCL="0";}//报账车辆总计
		Float CLZJ = Float.parseFloat(BXCL)-Float.parseFloat(JDCL);//车辆增减费用
		String JDDJ = rd.getString("planBjInfo","DJZJ",0);if("".equals(JDDJ)){JDDJ="0";}//计调地接总计
		String BXDJ = rd.getString("bxBjInfo","DJZJ",0);if("".equals(BXDJ)){BXDJ="0";}//报账地接总计
		Float DJZJ = Float.parseFloat(BXDJ)-Float.parseFloat(JDDJ);//地接增减费用
		String JDJIND = rd.getString("planBjInfo","JDZJ",0);if("".equals(JDJIND)){JDJIND="0";}//计调景点总计
		String BXJIND = rd.getString("bxBjInfo","JDZJ",0);if("".equals(BXJIND)){BXJIND="0";}//报账景点总计
		Float JINDZJ = Float.parseFloat(BXJIND)-Float.parseFloat(JDJIND);//景点增减费用
		String JDQT = rd.getString("planBjInfo","QTZJ",0);if("".equals(JDQT)){JDQT="0";}//计调其他总计
		String BXQT = rd.getString("bxBjInfo","QTZJ",0);if("".equals(BXQT)){BXQT="0";}//报账其他总计
		Float QTZJ1 = Float.parseFloat(BXQT)-Float.parseFloat(JDQT);//其他增减费用

		boolean DY = false;
		boolean HOTEL = false;
		boolean CT = false;
		boolean PW = false;
		boolean CL = false;
		boolean DJ = false;
		boolean JIND = false;
		boolean QT = false;
		if(JDDYLK.equals(BXDYLK)){
			DY = true;
		}
		if(JDHOTEL.equals(BXHOTEL)){
			HOTEL = true;
		}
		if(JDCT.equals(BXCT)){
			CT = true;
		}
		if(JDPW.equals(BXPW)){
			PW = true;
		}
		if(JDCL.equals(BXCL)){
			CL = true;
		}
		if(JDDJ.equals(BXDJ)){
			DJ = true;
		}
		if(JDJIND.equals(BXJIND)){
			JIND = true;
		}
		if(JDQT.equals(BXQT)){
			QT = true;
		}
		
		%>
		<tr>
			<td>导游比对：</td><td><%=JDDYLK %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXDYLK %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>/&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(DY){out.print("<font color='blue'>相同</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"g\",\""+tID+"\");'><font color='red'>不相同</font></a>");} %></td>
		</tr>
		<tr>
			<td>酒店比对：</td><td><%=JDHOTEL %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXHOTEL %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(HOTELZJ>0){out.print("-"+Math.abs(HOTELZJ));}else if(HOTELZJ<0){out.print("+"+Math.abs(HOTELZJ));}else{out.print(0);}%>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(HOTEL){out.print("<font color='blue'>相同</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"h\",\""+tID+"\");'><font color='red'>不相同</font></a>");}%></td>
		</tr>
		<tr>
			<td>餐厅比对：</td><td><%=JDCT %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXCT %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(CTZJ>0){out.print("-"+Math.abs(CTZJ));}else if(CTZJ<0){out.print("+"+Math.abs(CTZJ));}else{out.print(0);}%>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(CT){out.print("<font color='blue'>相同</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"c\",\""+tID+"\");'><font color='red'>不相同</font></a>");}%></td>
		</tr>
		<tr>
			<td>票务比对：</td><td><%=JDPW %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXPW %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(PWZJ>0){out.print("-"+Math.abs(PWZJ));}else if(PWZJ<0){out.print("+"+Math.abs(PWZJ));}else{out.print(0);} %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(PW){out.print("<font color='blue'>相同</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"t\",\""+tID+"\");'><font color='red'>不相同</font></a>");}%></td>
		</tr>
		<tr>
			<td>车辆比对：</td><td><%=JDCL %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXCL %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(CLZJ>0){out.print("-"+Math.abs(CLZJ));}else if(CLZJ<0){out.print("+"+Math.abs(CLZJ));}else{out.print(0);} %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(CL){out.print("<font color='blue'>相同</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"v\",\""+tID+"\");'><font color='red'>不相同</font></a>");}%></td>
		</tr>
		<tr>
			<td>地接比对：</td><td><%=JDDJ %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXDJ %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(DJZJ>0){out.print("-"+Math.abs(DJZJ));}else if(DJZJ<0){out.print("+"+Math.abs(DJZJ));}else{out.print(0);} %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(DJ){out.print("<font color='blue'>相同</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"n\",\""+tID+"\");'><font color='red'>不相同</font></a>");}%></td>
		</tr>
		<tr>
			<td>景点比对：</td><td><%=JDJIND %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXJIND %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(JINDZJ>0){out.print("-"+Math.abs(JINDZJ));}else if(JINDZJ<0){out.print("+"+Math.abs(JINDZJ));}else{out.print(0);} %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(JIND){out.print("<font color='blue'>相同</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"s\",\""+tID+"\");'><font color='red'>不相同</font></a>");}%></td>
		</tr>
		<tr>
			<td>其他比对：</td><td><%=JDQT %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXQT %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(QTZJ1>0){out.print("-"+Math.abs(QTZJ1));}else if(JINDZJ<0){out.print("+"+Math.abs(QTZJ1));}else{out.print(0);} %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(QT){out.print("<font color='blue'>相同</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"s\",\""+tID+"\");'><font color='red'>不相同</font></a>");}%></td>
		</tr>
	</table>
</div>
</div>
<div>
	<table>
		
	</table>
</div>
</form>
</body>
</html>
