<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
 <%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.apache.axis.types.Month"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�ŶӼƵ���Ϣ�뱨����Ϣ�Ƚ�</title>
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

	if('g' == where) {//����
		window.open("<%=request.getContextPath() %>/main/ztPlans/guidePlan/ztInitEditGuidePlan.?role=guide&TA_ZT_JHDY/ID=&TID="+tid+"&action=view"
			,'���ݱȶ�','width=600, height=300, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
	if('h' == where) {//�Ƶ� 
		window.open("<%=request.getContextPath()%>/main/ztPlans/hotelPlan/ztSelectHotelPlanInfo.?TA_ZT_GROUP/ID="+tid+"&TA_ZT_JHHOTEL/TID="+tid+"&TA_TDJDXXZJB_ZT/TID="+tid+"&TID="+tid
			,'���ݱȶ�','width=900, height=500, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
	if('c' == where) {//���� 
		window.open("<%=request.getContextPath()%>/main/ztPlans/dinnerPlan/ztListDinnerPlan.?TA_ZT_GROUP/ID="+tid+"&TA_ZT_JHCT/ID=&TID="+tid
			,'���ݱȶ�','width=700, height=500, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
	if('t' == where) {//Ʊ��
		window.open("<%=request.getContextPath()%>/main/ztPlans/ticketPlan/ztInitTicketP.?TA_ZT_JHPW/tID="+tid+"&TID="+tid+"&dmsm/JTGJ=2&flag=view&TA_TDJDXXZJB_ZT/TID="+tid+"&TA_ZT_JHPWMX/tid="+tid
			,'���ݱȶ�','width=750, height=500, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
	if('v' == where) {//����
		window.open("<%=request.getContextPath()%>/main/ztPlans/vehPlan/ztInitVehPlan.?TA_ZT_JHCL/TID="+tid+"&TA_TDJDXXZJB_ZT/TID="+tid+"&flag=view"
			,'���ݱȶ�','width=800, height=500, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
	if('v' == where) {//�ؽ�
		window.open("<%=request.getContextPath()%>/main/ztPlans/groundTravePlan/ztInitGroundPlan.?TA_ZT_JHDJ/TID="+tid+"&TA_TDJDXXZJB_ZT/TID="+tid+"&flag=view"
			,'���ݱȶ�','width=700, height=500, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
	if('s' == where) {//����
		window.open("<%=request.getContextPath()%>/main/ztPlans/sceneryPlan/ztInitSceneryPlan.?TA_TDJDXXZJB_ZT/TID="+tid+"&TA_ZT_JHJD/TID="+tid+"&flag=view"
			,'���ݱȶ�','width=700, height=500, top=20, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
}
</script>
</head>
<body>
<form  name="bxBJ_form" method="post">
<div id="lable"><span>�ŶӼƵ���Ϣ�뱨����Ϣ�Ƚ�</span></div>
<div id="list-table">
<div  id="thisSelect-table" >
  <table class="datas" >
	<tr><td colspan="5" align="left">&nbsp;&nbsp;�źţ�<%=groupID %></td></tr>
	<tr class="first-tr">
			<td>&nbsp;&nbsp;�ȶ���Ŀ��&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;�Ƶ����ݡ�&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;�������ݡ�&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;�������á�&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;�ȶԽ����&nbsp;&nbsp;</td>
	</tr>
		<%
		String tID = rd.getString("tid");
		
		String JDDYLK = rd.getString("dyPlanInfo","DYLK",0);if("".equals(JDDYLK)){JDDYLK="0";}//���μƵ�����ܼ�
		String BXDYLK = rd.getString("dyBxInfo","DYLK",0);if("".equals(BXDYLK)){BXDYLK="0";}//���α�������ܼ�
		String JDHOTEL = rd.getString("bxBjInfo","ZSZJ",0);if("".equals(JDHOTEL)){JDHOTEL="0";}//�Ƶ�ס���ܼ�
		String BXHOTEL = rd.getString("bxBjInfo","JDHJ",0);if("".equals(BXHOTEL)){BXHOTEL="0";}//����ס���ܼ�
		Float HOTELZJ = Float.parseFloat(BXHOTEL)-Float.parseFloat(JDHOTEL);//�Ƶ��������� 
		String JDCT = rd.getString("bxBjInfo","CTZJ",0);if("".equals(JDCT)){JDCT="0";}//�Ƶ������ܼ�
		String BXCT = rd.getString("bxBjInfo","CTHJ",0);if("".equals(BXCT)){BXCT="0";}//���˲����ܼ�
		Float CTZJ = Float.parseFloat(BXCT)-Float.parseFloat(JDCT);//������������
		String JDPW = rd.getString("bxBjInfo","PWZJ",0);if("".equals(JDPW)){JDPW="0";}//�Ƶ�Ʊ���ܼ�
		String BXPW = rd.getString("bxBjInfo","PWHJ",0);if("".equals(BXPW)){BXPW="0";}//����Ʊ���ܼ�
		Float PWZJ = Float.parseFloat(BXPW)-Float.parseFloat(JDPW);//Ʊ����������
		String JDCL = rd.getString("bxBjInfo","CLZJ",0);if("".equals(JDCL)){JDCL="0";}//�Ƶ������ܼ�
		String BXCL = rd.getString("bxBjInfo","CLHJ",0);if("".equals(BXCL)){BXCL="0";}//���˳����ܼ�
		Float CLZJ = Float.parseFloat(BXCL)-Float.parseFloat(JDCL);//������������
		String JDDJ = rd.getString("bxBjInfo","DJZJ",0);if("".equals(JDDJ)){JDDJ="0";}//�Ƶ��ؽ��ܼ�
		String BXDJ = rd.getString("bxBjInfo","DJHJ",0);if("".equals(BXDJ)){BXDJ="0";}//���˵ؽ��ܼ�
		Float DJZJ = Float.parseFloat(BXDJ)-Float.parseFloat(JDDJ);//�ؽ���������
		String JDJIND = rd.getString("bxBjInfo","JDZJ",0);if("".equals(JDJIND)){JDJIND="0";}//�Ƶ������ܼ�
		String BXJIND = rd.getString("bxBjInfo","JINDHJ",0);if("".equals(BXJIND)){BXJIND="0";}//���˾����ܼ�
		Float JINDZJ = Float.parseFloat(BXJIND)-Float.parseFloat(JDJIND);//������������
		
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
			<td>���αȶԣ�</td><td><%=JDDYLK %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXDYLK %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>/&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(DY){out.print("<font color='blue'>��ͬ</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"g\",\""+tID+"\");'><font color='red'>����ͬ</font></a>");} %></td>
		</tr>
		<tr>
			<td>�Ƶ�ȶԣ�</td><td><%=JDHOTEL %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXHOTEL %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(HOTELZJ>0){out.print("���ӣ�"+Math.abs(HOTELZJ));}else if(HOTELZJ<0){out.print("���٣�"+Math.abs(HOTELZJ));}else{out.print(0);}%>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(HOTEL){out.print("<font color='blue'>��ͬ</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"h\",\""+tID+"\");'><font color='red'>����ͬ</font></a>");}%></td>
		</tr>
		<tr>
			<td>�����ȶԣ�</td><td><%=JDCT %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXCT %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(CTZJ>0){out.print("���ӣ�"+Math.abs(CTZJ));}else if(CTZJ<0){out.print("���٣�"+Math.abs(CTZJ));}else{out.print(0);}%>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(CT){out.print("<font color='blue'>��ͬ</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"c\",\""+tID+"\");'><font color='red'>����ͬ</font></a>");}%></td>
		</tr>
		<tr>
			<td>Ʊ��ȶԣ�</td><td><%=JDPW %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXPW %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(PWZJ>0){out.print("���ӣ�"+Math.abs(PWZJ));}else if(PWZJ<0){out.print("���٣�"+Math.abs(PWZJ));}else{out.print(0);} %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(PW){out.print("<font color='blue'>��ͬ</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"t\",\""+tID+"\");'><font color='red'>����ͬ</font></a>");}%></td>
		</tr>
		<tr>
			<td>�����ȶԣ�</td><td><%=JDCL %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXCL %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(CLZJ>0){out.print("���ӣ�"+Math.abs(CLZJ));}else if(CLZJ<0){out.print("���٣�"+Math.abs(CLZJ));}else{out.print(0);} %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(CL){out.print("<font color='blue'>��ͬ</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"v\",\""+tID+"\");'><font color='red'>����ͬ</font></a>");}%></td>
		</tr>
		<tr>
			<td>�ؽӱȶԣ�</td><td><%=JDDJ %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXDJ %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(DJZJ>0){out.print("���ӣ�"+Math.abs(DJZJ));}else if(DJZJ<0){out.print("���٣�"+Math.abs(DJZJ));}else{out.print(0);} %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(DJ){out.print("<font color='blue'>��ͬ</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"n\",\""+tID+"\");'><font color='red'>����ͬ</font></a>");}%></td>
		</tr>
		<tr>
			<td>����ȶԣ�</td><td><%=JDJIND %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%=BXJIND %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(JINDZJ>0){out.print("���ӣ�"+Math.abs(JINDZJ));}else if(JINDZJ<0){out.print("���٣�"+Math.abs(JINDZJ));}else{out.print(0);} %>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><%if(JIND){out.print("<font color='blue'>��ͬ</font>");}else{out.print("<a href='#' onclick='openCompareValue(\"s\",\""+tID+"\");'><font color='red'>����ͬ</font></a>");}%></td>
		</tr>
		<tr >
			<td colspan="5"><input type="button" id="button" value="�ر�" onclick="reload();"/></td>
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
