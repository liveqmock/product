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
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
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
<%String groupID = rd.getString("TID"); %>
<script type="text/javascript">
function Sub(){
	document.bx_group_form.action="ztBxBJ.?TID=<%=groupID %>";
	document.bx_group_form.submit();
}
function reload(){
	parent.parent.location.reload(); 
	parent.parent.GB_hide(); 
}

function openCompareValue(where,tid){

	if('g' == where) {//导游
		window.open("<%=request.getContextPath() %>/main/ztPlans/guidePlan/ztInitEditGuidePlan.?role=guide&TA_ZT_JHDY/ID=&TID="+tid+"&action=view"
			,'数据比对','width=600, height=300, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
	if('h' == where) {//酒店 
		window.open("<%=request.getContextPath()%>/main/ztPlans/hotelPlan/ztSelectHotelPlanInfo.?TA_ZT_GROUP/ID="+tid+"&TA_ZT_JHHOTEL/TID="+tid+"&TA_TDJDXXZJB_ZT/TID="+tid+"&TID="+tid
			,'数据比对','width=900, height=500, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
	if('c' == where) {//餐厅 
		window.open("<%=request.getContextPath()%>/main/ztPlans/dinnerPlan/ztListDinnerPlan.?TA_ZT_GROUP/ID="+tid+"&TA_ZT_JHCT/ID=&TID="+tid
			,'数据比对','width=700, height=500, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
	if('t' == where) {//票务
		window.open("<%=request.getContextPath()%>/main/ztPlans/ticketPlan/ztInitTicketP.?TA_ZT_JHPW/tID="+tid+"&TID="+tid+"&dmsm/JTGJ=2&flag=view&TA_TDJDXXZJB_ZT/TID="+tid+"&TA_ZT_JHPWMX/tid="+tid
			,'数据比对','width=750, height=500, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
	if('v' == where) {//车辆
		window.open("<%=request.getContextPath()%>/main/ztPlans/vehPlan/ztInitVehPlan.?TA_ZT_JHCL/TID="+tid+"&TA_TDJDXXZJB_ZT/TID="+tid+"&flag=view"
			,'数据比对','width=800, height=500, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
	if('v' == where) {//地接
		window.open("<%=request.getContextPath()%>/main/ztPlans/groundTravePlan/ztInitGroundPlan.?TA_ZT_JHDJ/TID="+tid+"&TA_TDJDXXZJB_ZT/TID="+tid+"&flag=view"
			,'数据比对','width=700, height=500, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
	if('s' == where) {//景点
		window.open("<%=request.getContextPath()%>/main/ztPlans/sceneryPlan/ztInitSceneryPlan.?TA_TDJDXXZJB_ZT/TID="+tid+"&TA_ZT_JHJD/TID="+tid+"&flag=view"
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
	<tr><td colspan="5" align="left">&nbsp;&nbsp;团号：<%=groupID %></td></tr>
	<tr class="first-tr">
			<td>&nbsp;&nbsp;比对项目：&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;计调内容↓&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;报账内容↓&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;增减费用↓&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;比对结果↓&nbsp;&nbsp;</td>
	</tr>
		<%
		String tID = rd.getString("tid");
		
		String JDDYLK = rd.getString("dyPlanInfo","DYLK",0);if("".equals(JDDYLK)){JDDYLK="0";}//导游计调领款总计
		String BXDYLK = rd.getString("dyBxInfo","DYLK",0);if("".equals(BXDYLK)){BXDYLK="0";}//导游报账领款总计
		String JDHOTEL = rd.getString("bxBjInfo","ZSZJ",0);if("".equals(JDHOTEL)){JDHOTEL="0";}//计调住宿总计
		String BXHOTEL = rd.getString("bxBjInfo","JDHJ",0);if("".equals(BXHOTEL)){BXHOTEL="0";}//报账住宿总计
		Float HOTELZJ = Float.parseFloat(BXHOTEL)-Float.parseFloat(JDHOTEL);//酒店增减费用 
		String JDCT = rd.getString("bxBjInfo","CTZJ",0);if("".equals(JDCT)){JDCT="0";}//计调餐厅总计
		String BXCT = rd.getString("bxBjInfo","CTHJ",0);if("".equals(BXCT)){BXCT="0";}//报账餐厅总计
		Float CTZJ = Float.parseFloat(BXCT)-Float.parseFloat(JDCT);//餐厅增减费用
		String JDPW = rd.getString("bxBjInfo","PWZJ",0);if("".equals(JDPW)){JDPW="0";}//计调票务总计
		String BXPW = rd.getString("bxBjInfo","PWHJ",0);if("".equals(BXPW)){BXPW="0";}//报账票务总计
		Float PWZJ = Float.parseFloat(BXPW)-Float.parseFloat(JDPW);//票务增减费用
		String JDCL = rd.getString("bxBjInfo","CLZJ",0);if("".equals(JDCL)){JDCL="0";}//计调车辆总计
		String BXCL = rd.getString("bxBjInfo","CLHJ",0);if("".equals(BXCL)){BXCL="0";}//报账车辆总计
		Float CLZJ = Float.parseFloat(BXCL)-Float.parseFloat(JDCL);//车辆增减费用
		String JDDJ = rd.getString("bxBjInfo","DJZJ",0);if("".equals(JDDJ)){JDDJ="0";}//计调地接总计
		String BXDJ = rd.getString("bxBjInfo","DJHJ",0);if("".equals(BXDJ)){BXDJ="0";}//报账地接总计
		Float DJZJ = Float.parseFloat(BXDJ)-Float.parseFloat(JDDJ);//地接增减费用
		String JDJIND = rd.getString("bxBjInfo","JDZJ",0);if("".equals(JDJIND)){JDJIND="0";}//计调景点总计
		String BXJIND = rd.getString("bxBjInfo","JINDHJ",0);if("".equals(BXJIND)){BXJIND="0";}//报账景点总计
		Float JINDZJ = Float.parseFloat(BXJIND)-Float.parseFloat(JDJIND);//景点增减费用
		
		boolean DY = false;
		boolean HOTEL = false;
		boolean CT = false;
		boolean PW = false;
		boolean CL = false;
		boolean DJ = false;
		boolean JIND = false;
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
		
		%>
		<tr>
			<td>导游比对：</td><td><%=JDDYLK %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXDYLK %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>/&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(DY){out.print("<font color='blue'>相同</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"g\",\""+tID+"\");'><font color='red'>不相同</font></a>");} %></td>
		</tr>
		<tr>
			<td>酒店比对：</td><td><%=JDHOTEL %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXHOTEL %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(HOTELZJ>0){out.print("增加："+Math.abs(HOTELZJ));}else if(HOTELZJ<0){out.print("减少："+Math.abs(HOTELZJ));}else{out.print(0);}%>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(HOTEL){out.print("<font color='blue'>相同</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"h\",\""+tID+"\");'><font color='red'>不相同</font></a>");}%></td>
		</tr>
		<tr>
			<td>餐厅比对：</td><td><%=JDCT %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXCT %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(CTZJ>0){out.print("增加："+Math.abs(CTZJ));}else if(CTZJ<0){out.print("减少："+Math.abs(CTZJ));}else{out.print(0);}%>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(CT){out.print("<font color='blue'>相同</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"c\",\""+tID+"\");'><font color='red'>不相同</font></a>");}%></td>
		</tr>
		<tr>
			<td>票务比对：</td><td><%=JDPW %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXPW %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(PWZJ>0){out.print("增加："+Math.abs(PWZJ));}else if(PWZJ<0){out.print("减少："+Math.abs(PWZJ));}else{out.print(0);} %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(PW){out.print("<font color='blue'>相同</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"t\",\""+tID+"\");'><font color='red'>不相同</font></a>");}%></td>
		</tr>
		<tr>
			<td>车辆比对：</td><td><%=JDCL %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXCL %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(CLZJ>0){out.print("增加："+Math.abs(CLZJ));}else if(CLZJ<0){out.print("减少："+Math.abs(CLZJ));}else{out.print(0);} %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(CL){out.print("<font color='blue'>相同</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"v\",\""+tID+"\");'><font color='red'>不相同</font></a>");}%></td>
		</tr>
		<tr>
			<td>地接比对：</td><td><%=JDDJ %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXDJ %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(DJZJ>0){out.print("增加："+Math.abs(DJZJ));}else if(DJZJ<0){out.print("减少："+Math.abs(DJZJ));}else{out.print(0);} %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(DJ){out.print("<font color='blue'>相同</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"n\",\""+tID+"\");'><font color='red'>不相同</font></a>");}%></td>
		</tr>
		<tr>
			<td>景点比对：</td><td><%=JDJIND %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXJIND %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(JINDZJ>0){out.print("增加："+Math.abs(JINDZJ));}else if(JINDZJ<0){out.print("减少："+Math.abs(JINDZJ));}else{out.print(0);} %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(JIND){out.print("<font color='blue'>相同</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"s\",\""+tID+"\");'><font color='red'>不相同</font></a>");}%></td>
		</tr>
		<tr >
			<td colspan="5"><input type="button" id="button" value="关闭" onclick="reload();"/></td>
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
