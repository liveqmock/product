<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%String ID = rd.getStringByDI("bx_GuideInfo","ID",0); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>���α���</title>
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
<div id="lable"><span >���α���</span></div>
<div id="bm-table">
<div id="groupDiv">
<table class="datas" >
		<tr>
			<td colspan="4" ><span>��&nbsp;>>>&nbsp;<%=rd.getStringByDI("bx_GuideInfo","ID",0) %>&nbsp;������Ϣ</span></td>
		</tr>
		<tr>
		  <td align="right">&nbsp;&nbsp;�������ڣ�</td>
		  <td>
		  	<font color="red">&nbsp;<%=rd.getStringByDI("bx_GuideInfo","BEGIN_DATE",0) %></font>&nbsp;&nbsp;&nbsp;&nbsp;
		  </td>
		  <td>
		   	�������ڣ�&nbsp;&nbsp;&nbsp;<font color="red"><%=rd.getStringByDI("bx_GuideInfo","END_DATE",0) %></font>
		  </td>
		</tr>
		<tr>
			<td align="right">��·���ƣ�</td>
			<td  >
				<img alt="�ȵ���·" src="<%=request.getContextPath()%>/images/hot3.gif"/>&nbsp;
				<%String XLMC = rd.getStringByDI("bx_GuideInfo","XLMC",0); %>
				<font color="red"><%=XLMC.length()<=30?XLMC:XLMC.substring(0,30)+"..."  %></font></td>
			<td colspan="2"> �źű�ţ�
				<input type="hidden" value="28723" name="group_fkId"/>
				<%=rd.getStringByDI("bx_GuideInfo","ID",0) %>
			</td>
		</tr>
		<tr>
			<td align="right">������</td>
			<td >��ͯ:<%=rd.getStringByDI("bx_GuideInfo","ETRS",0) %>��&nbsp;����:<%=rd.getStringByDI("bx_GuideInfo","CRRS",0) %>��</td>
			<td >���������� <%=rd.getStringByDI("bx_GuideInfo","DYXM",0) %></td>
		</tr>
		<tr>
			<td align="right">��ע��</td>
			<%String TSYQ = rd.getStringByDI("bx_GuideInfo","TSYQ",0); %>
			<td> <%=TSYQ.length()<=70?TSYQ:TSYQ.substring(0,70)+"..."  %></td>
			<td>
				ȫ������:<%=rd.getStringByDI("TA_DJ_GROUPs","QPRS",0) %>&nbsp;
				����:<%=rd.getStringByDI("TA_DJ_GROUPs","QPXM",0) %>&nbsp;
				�绰:<%=rd.getStringByDI("TA_DJ_GROUPs","QPDH",0) %>
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
				<td><span>���η�����Ϣ</span></td>
			</tr>
			<tr>
			<td>������δ����(<span class="showPointer" style="text-decoration:underline;color:red;font-weight:100" onclick="return GB_myShow('���η��ñ���','<%=request.getContextPath()%>/maindj/bx/bxGuide/djInitGuideRbt.?TA_DJ_BXDY/TID=<%=ID %>&TA_TDBXZJXXB/TID=<%=ID %>&flag=init','800','650','')">���뱨��</span>)</td>
			</tr>
		</table>
		<%
		}}else{
		%>
		<table class="datas">
			<tr>
			<%if(isTrue){ %>
				<td><span>���η�����Ϣ</span>&nbsp;&nbsp;<span class="showPointer" onclick="return GB_myShow('���η��ñ���','<%=request.getContextPath() %>/maindj/bx/bxGuide/djInitGuideRbt.?TA_DJ_BXDY/TID=<%=ID %>&TA_TDBXZJXXB/TID=<%=ID %>&flag=view','800','650','')">�鿴������ϸ</span></td>
			<%}else{ %>
				<td><span>���η�����Ϣ</span>(<span class="showPointer" style="text-decoration:underline; color:blue;font-weight:100" onclick="return GB_myShow('���η��ñ���','<%=request.getContextPath() %>/maindj/bx/bxGuide/djInitGuideRbt.?TA_DJ_BXDY/TID=<%=ID %>&TA_TDBXZJXXB/TID=<%=ID %>&flag=edit','800','650','')">�޸ı���</span>)</td>
			<%} %>
			</tr>
			<tr>
				<td align="right">
					���η��úϼ�:�������ϼ�:<font color="red"><%=dylk %></font>Ԫ    �����Ѻϼ�:<font color="red"><%=dff %></font>Ԫ ���ν�ͨ�Ѻϼ�:<font color="red"><%=dyjtf %></font>Ԫ&nbsp;��������:<font color="red"><%=qt %></font>Ԫ&nbsp;���η��úϼ�:<font color="red"><%=rd.getStringByDI("TA_TDBXZJXXBs","DYFYHJ",0) %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;
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
				<td><span>�Ƶ걨����Ϣ</span></td>
			</tr>
			<tr>
				<td>�Ƶ���δ����(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('�Ƶ���ñ���','<%=request.getContextPath()%>/maindj/bx/bxHotel/initDjHotelBx.?TA_DJ_GROUP/ID=<%=ID%>&TA_DJ_BXHOTEL/TID=<%=ID %>&TID=<%=ID %>&flag=init','800','750','')">���뱨��</span>)</td>
			</tr>
		</table>
	<%}}else{ %>
		<table class="datas">
			<tr>
			<%if(isTrue){ %>
				<td><span>�Ƶ걨����Ϣ</span>&nbsp;&nbsp;<span class="showPointer" onclick="return GB_myShow('�Ƶ���ñ���','<%=request.getContextPath()%>/maindj/bx/bxHotel/initDjHotelBx.?TA_DJ_GROUP/ID=<%=ID%>&TA_DJ_BXHOTEL/TID=<%=ID %>&TID=<%=ID %>&flag=view','800','750','')">�鿴������ϸ</span></td>
			<%}else{ %>
				<td><span>�Ƶ걨����Ϣ</span>(<span class="showPointer" style="text-decoration:underline; color:blue;font-weight:100" onclick="return GB_myShow('�Ƶ���ñ���','<%=request.getContextPath()%>/maindj/bx/bxHotel/initDjHotelBx.?TA_DJ_GROUP/ID=<%=ID%>&TA_DJ_BXHOTEL/TID=<%=ID %>&TID=<%=ID %>&flag=edit','800','750','')">�޸ı���</span>)</td>
			<%} %>
			</tr>
			<tr>
				<td align="right">
					�Ƶ���úϼƣ�ǩ�����ϼƣ�<font color="red"><%="".equals(qdzszj)?0:qdzszj %></font>Ԫ  �ָ����ϼƣ�<font color="red"><%="".equals(xfzszj)?0:xfzszj %></font>Ԫ  �ϼƣ�<font color="red"><%="".equals(zszj)?0:zszj %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;
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
				<td><span>����������Ϣ</span></td>
			</tr>
			<tr>
				<td>������δ����(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('�������ñ���','<%=request.getContextPath()%>/maindj/bx/bxRestaurant/initDjDinnerBx.?TA_DJ_GROUP/ID=<%=ID%>&TA_DJ_BXCT/TID=<%=ID %>&TID=<%=ID %>&flag=init','800','680','')">���뱨��</span>)</td>
			</tr>
		</table>
		<%}}else{ %>
		<table class="datas">
			<tr>
			<%if(isTrue){ %>
			<td><span>����������Ϣ</span>&nbsp;&nbsp;<span class="showPointer" onclick="return GB_myShow('�������ñ���','<%=request.getContextPath()%>/maindj/bx/bxRestaurant/initDjDinnerBx.?TA_DJ_GROUP/ID=<%=ID%>&TA_DJ_BXCT/TID=<%=ID %>&TID=<%=ID %>&flag=view','800','680','')">�鿴������ϸ</span></td>
			<%}else{ %>
			<td><span>����������Ϣ</span>(<span class="showPointer" style="text-decoration:underline; color:blue;font-weight:100" onclick="return GB_myShow('�������ñ���','<%=request.getContextPath()%>/maindj/bx/bxRestaurant/initDjDinnerBx.?TA_DJ_GROUP/ID=<%=ID%>&TA_DJ_BXCT/TID=<%=ID %>&TID=<%=ID %>&flag=edit','800','680','')">�޸ı���</span>)</td>
			<%} %>
			</tr>
			<tr>
				<td align="right">
					�������úϼƣ�ǩ�����ϼƣ�<font color="red"><%="".equals(qdctzj)?0:qdctzj %></font>Ԫ  �ָ����ϼƣ�<font color="red"><%="".equals(xfctzj)?0:xfctzj %></font>Ԫ  �ϼƣ�<font color="red"><%="".equals(ctzj)?0:ctzj %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;
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
				<td><span>Ʊ������Ϣ</span></td>
			</tr>
			<tr>
				<td>Ʊ����δ����(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('Ʊ����','<%=request.getContextPath()%>/maindj/bx/bxTicket/djInitTicketBx.?TA_DJ_BXPW/TID=<%=ID %>&TA_DJ_JHPW/TID=<%=ID %>&dmsm/JTGJ=2&flag=init','800','850','')">���뱨��</span>)</td>
			</tr>
		</table>
	<%}}else{ %>
		<table class="datas">
			<tr>
			<%if(isTrue){ %>
			<td><span>Ʊ������Ϣ</span>&nbsp;&nbsp;<span class="showPointer"  onclick="return GB_myShow('Ʊ����','<%=request.getContextPath()%>/maindj/bx/bxTicket/djInitTicketBx.?TA_DJ_BXPW/TID=<%=ID %>&TA_DJ_JHPW/TID=<%=ID %>&dmsm/JTGJ=2&flag=view','800','850','')">�鿴������ϸ</span></td>
			<%}else{ %>
			<td><span>Ʊ������Ϣ</span>(<span class="showPointer" style="text-decoration:underline; color:blue;font-weight:100" onclick="return GB_myShow('Ʊ����','<%=request.getContextPath()%>/maindj/bx/bxTicket/djInitTicketBx.?TA_DJ_BXPW/TID=<%=ID %>&TA_DJ_JHPW/TID=<%=ID %>&dmsm/JTGJ=2&flag=edit','800','850','')">�޸ı���</span>)</td>
			<%} %>
			</tr>
			<tr>
				<td align="right">
					Ʊ����úϼƣ�ǩ�����ϼƣ�<font color="red"><%="".equals(qdpwzj)?0:qdpwzj %></font>Ԫ  �ָ����ϼƣ�<font color="red"><%="".equals(xfpwzj)?0:xfpwzj %></font>Ԫ  �ϼƣ�<font color="red"><%="".equals(pwzj)?0:pwzj %></font>Ԫ  �������ܼƣ�<font color="red"><%="".equals(sxfzj)?0:sxfzj %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;
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
				<td><span>����������Ϣ</span></td>
			</tr>
			<tr>
				<td>������δ����(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('��������','<%=request.getContextPath()%>/maindj/bx/bxCar/djInitVehBx.?TA_DJ_JHCL/TID=<%=ID %>&TA_DJ_BXCL/TID=<%=ID %>&flag=init','800','850','')">���뱨��</span>)</td>
			</tr>
		</table>
	<%}}else{ %>
		<table class="datas">
			<tr>
			<%if(isTrue){ %>
			<td><span>����������Ϣ</span>&nbsp;&nbsp;<span class="showPointer" onclick="return GB_myShow('��������','<%=request.getContextPath()%>/maindj/bx/bxCar/djInitVehBx.?TA_DJ_JHCL/TID=<%=ID %>&TA_DJ_BXCL/TID=<%=ID %>&flag=view','800','850','')">�鿴������ϸ</span></td>
			<%}else{ %>
			<td><span>����������Ϣ</span>(<span class="showPointer" style="text-decoration:underline; color:blue;font-weight:100" onclick="return GB_myShow('��������','<%=request.getContextPath()%>/maindj/bx/bxCar/djInitVehBx.?TA_DJ_JHCL/TID=<%=ID %>&TA_DJ_BXCL/TID=<%=ID %>&flag=edit','800','850','')">�޸ı���</span>)</td>
			<%} %>
			</tr>
			<tr>
				<td  align="right">�������úϼƣ�ǩ�����ϼƣ�<font color="red"><%="".equals(qdclzj)?0:qdclzj %></font>Ԫ  �ָ����ϼƣ�<font color="red"><%="".equals(xfclzj)?0:xfclzj %></font>Ԫ  �ϼƣ�<font color="red"><%="".equals(clzj)?0:clzj %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;</td>	
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
				<td><span>�ؽӱ�����Ϣ</span></td>
			</tr>
			<tr>
				<td>�ؽ���δ����(<span class="showPointer" style="text-decoration:underline;color:red;font-weight:100" onclick="return GB_myShow('�ؽ��籨��','<%=request.getContextPath()%>/maindj/bx/bxGroundTrave/djInitdjsInfo.?TA_DJ_BXDJ/TID=<%=ID %>&flag=init','800','600')">���뱨��</span>)</td>
			</tr>
		</table>
	<%
	}}else{
	%>
	<table class="datas">
			<tr>
			<%if(isTrue){ %>
			<td><span>�ؽӱ�����Ϣ</span>&nbsp;&nbsp;<span class="showPointer"  onclick="return GB_myShow('�ؽ��籨��','<%=request.getContextPath()%>/maindj/bx/bxGroundTrave/djInitdjsInfo.?TA_DJ_BXDJ/TID=<%=ID %>&flag=view','800','600')">�鿴������ϸ</span></td>
			<%}else{ %>
			<td><span>�ؽӱ�����Ϣ</span>(<span class="showPointer" style="text-decoration:underline; color:blue;font-weight:100" onclick="return GB_myShow('�ؽ��籨��','<%=request.getContextPath()%>/maindj/bx/bxGroundTrave/djInitdjsInfo.?TA_DJ_BXDJ/TID=<%=ID %>&flag=edit','800','600')">�޸ı���</span>)</td>
			<%} %>
			</tr>
			<tr>
				<td  align="right">�ؽ�������: <font color="red"><%int TraveRows= rd.getTableRowsCount("DjsNameInfo"); for(int i = 0; i< TraveRows; i++){String TraveName = rd.getStringByDI("DjsNameInfo","cmpny_name",i);%><%=TraveName %> <%} %></font>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td  align="right">�ؽ�����úϼƣ�ǩ�����ϼƣ�<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","DJQDZJ",0))?0:rd.getString("TA_TDBXZJXXBs","DJQDZJ",0) %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;�ָ����ϼƣ�<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","DJXFZJ",0))?0:rd.getString("TA_TDBXZJXXBs","DJXFZJ",0) %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;Ӧ���ſ�ϼƣ�<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","DJHJ",0))?0:rd.getString("TA_TDBXZJXXBs","DJHJ",0) %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
				<td><span>���㱨����Ϣ</span></td>
			</tr>
			<tr>
				<td>������δ����(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('�鿴���㱨����Ϣ','<%=request.getContextPath() %>/maindj/businessPlan/plan/sceneryPlan/djInitSceneryRbt.?TA_DJ_BXJD/TID=<%=ID %>&flag=init','800','700','')">���뱨��</span>)</td>
			</tr>
		</table>
		<%}}else{ %>
		<table class="datas">
			<tr>
			<%if(isTrue){ %>
			<td><span>���㱨����Ϣ</span>&nbsp;&nbsp;<span class="showPointer" onclick="return GB_myShow('�鿴���㱨����Ϣ','<%=request.getContextPath() %>/maindj/businessPlan/plan/sceneryPlan/djInitSceneryRbt.?TA_DJ_BXJD/TID=<%=ID %>&flag=view','800','700','')">�鿴������ϸ</span></td>
			<%}else{ %>
			<td><span>���㱨����Ϣ</span>(<span class="showPointer" style="text-decoration:underline; color:blue;font-weight:100" onclick="return GB_myShow('�鿴���㱨����Ϣ','<%=request.getContextPath() %>/maindj/businessPlan/plan/sceneryPlan/djInitSceneryRbt.?TA_DJ_BXJD/TID=<%=ID %>&flag=edit','800','700','')">�޸ı���</span>)</td>
			<%} %>
			</tr>
			<tr>
				<td  align="right">�������ݣ�<font color="red"><%for(int i=0;i<rd.getTableRowsCount("querySceneryInfo");i++){out.print(rd.getStringByDI("querySceneryInfo","cmpny_name",i)+" ");} %></font>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td  align="right">������úϼƣ�ǩ�����ϼƣ�<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","BX_JDQD",0))?0:rd.getString("TA_TDBXZJXXBs","BX_JDQD",0) %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;�ָ����ϼƣ�<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","BX_JDXF",0))?0:rd.getString("TA_TDBXZJXXBs","BX_JDXF",0) %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;�ϼƣ�<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","JINDHJ",0))?0:rd.getString("TA_TDBXZJXXBs","JINDHJ",0) %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
				<td><span>���ﱨ����Ϣ</span>(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('�鿴���ﱨ����Ϣ','<%=request.getContextPath() %>/maindj/businessPlan/plan/sceneryPlan/djInitShopRbt.?TCCY=18&LCBL=21&TA_DJ_BXGW/TID=<%=ID %>&TA_TDBXZJXXB/TID=<%=ID %>&flag=init&qprszt=<%=QPRSZT %>&CRRS=<%=CRRS %>','800','700','')">���뱨��</span>)</td>
			</tr>
			<tr>
				<td></td>
			</tr>
		</table>
		<%}}else{ %>
		<table class="datas">
			<tr>
			<%if(isTrue){ %>
			<td><span>���ﱨ����Ϣ</span><span class="showPointer"  onclick="return GB_myShow('�鿴���ﱨ����Ϣ','<%=request.getContextPath() %>/maindj/businessPlan/plan/sceneryPlan/djInitShopRbt.?TCCY=18&LCBL=21&TA_DJ_BXGW/TID=<%=ID %>&TA_TDBXZJXXB/TID=<%=ID %>&flag=view&qprszt=<%=QPRSZT %>&CRRS=<%=CRRS %>','800','700','')">&nbsp;&nbsp;�鿴������ϸ</span></td>
			<%}else{ %>
			<td><span>���ﱨ����Ϣ</span>(<span class="showPointer" style="text-decoration:underline; color:blue;font-weight:100" onclick="return GB_myShow('�鿴���ﱨ����Ϣ','<%=request.getContextPath() %>/maindj/businessPlan/plan/sceneryPlan/djInitShopRbt.?TCCY=18&LCBL=21&TA_DJ_BXGW/TID=<%=ID %>&TA_TDBXZJXXB/TID=<%=ID %>&flag=edit&qprszt=<%=QPRSZT %>&CRRS=<%=CRRS %>','800','700','')">�޸ı���</span>)</td>
			<%} %>
			</tr>
			<tr>
				<td  align="right">�������ݣ�<font color="red"><%for(int i=0;i<rd.getTableRowsCount("queryShopInfo");i++){out.print(rd.getStringByDI("queryShopInfo","cmpny_name",i)+" ");} %></font>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td  align="right">������úϼƣ����������ϼƣ�<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","GWRSHJ",0))?0:rd.getString("TA_TDBXZJXXBs","GWRSHJ",0) %></font>��&nbsp;&nbsp;&nbsp;&nbsp;������ͷ�Ѻϼƣ�<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","GWRTFHJ",0))?0:rd.getString("TA_TDBXZJXXBs","GWRTFHJ",0) %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;�������Ѷ�ϼƣ�<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","GWXFEHJ",0))?0:rd.getString("TA_TDBXZJXXBs","GWXFEHJ",0) %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;��������ϼƣ�<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","GWGSLCHJ",0))?0:rd.getString("TA_TDBXZJXXBs","GWGSLCHJ",0) %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;����Ӧ����˾�ֽ�ϼƣ�<font color="red"><%=rd.getString("TA_TDBXZJXXBs","GWYJGSHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXBs","GWYJGSHJ",0) %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;���﹫˾����ϼƣ�<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","GWLRHJ",0))?0:rd.getString("TA_TDBXZJXXBs","GWLRHJ",0) %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
				<td><span>�ӵ㱨����Ϣ</span>(<span class="showPointer" style=" text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('�鿴�ӵ㱨����Ϣ','<%=request.getContextPath() %>/maindj/businessPlan/plan/sceneryPlan/djInitPlusRbt.?TCCY=18&LCBL=21&TA_DJ_BXJIADIAN/TID=<%=ID %>&TA_TDBXZJXXB/TID=<%=ID %>&flag=init&qprszt=<%=QPRSZT %>','800','700','')">���뱨��</span>)</td>
			</tr>
			<tr>
				<td></td>
			</tr>
		</table>
		<%}}else{ %>
		<table class="datas">
			<tr>
			<%if(isTrue){ %>
			<td><span>�ӵ㱨����Ϣ</span>&nbsp;&nbsp;<span class="showPointer" onclick="return GB_myShow('�鿴�ӵ㱨����Ϣ','<%=request.getContextPath() %>/maindj/businessPlan/plan/sceneryPlan/djInitPlusRbt.?TCCY=18&LCBL=21&TA_DJ_BXJIADIAN/TID=<%=ID %>&TA_TDBXZJXXB/TID=<%=ID %>&flag=view&qprszt=<%=QPRSZT %>','800','700','')">�鿴������ϸ</span></td>
			<%}else{ %>
			<td><span>�ӵ㱨����Ϣ</span>(<span class="showPointer" style=" text-decoration:underline; color:blue;font-weight:100" onclick="return GB_myShow('�鿴�ӵ㱨����Ϣ','<%=request.getContextPath() %>/maindj/businessPlan/plan/sceneryPlan/djInitPlusRbt.?TCCY=18&LCBL=21&TA_DJ_BXJIADIAN/TID=<%=ID %>&TA_TDBXZJXXB/TID=<%=ID %>&flag=Edit&qprszt=<%=QPRSZT %>','800','700','')">�޸ı���</span>)</td>
			<%} %>
			</tr>
			<tr>
				<td  align="right">�ӵ����ݣ�<font color="red"><%for(int i=0;i<rd.getTableRowsCount("queryPlusInfo");i++){out.print(rd.getStringByDI("queryPlusInfo","cmpny_name",i)+" ");} %></font>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td  align="right">���úϼƣ�ǩ�����ϼƣ�<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","JDQDHJ",0))?0:rd.getString("TA_TDBXZJXXBs","JDQDHJ",0) %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;�ָ����ϼƣ�<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","JDXFHJ",0))?0:rd.getString("TA_TDBXZJXXBs","JDXFHJ",0) %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;�����ϼƣ�<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","JDRSHJ",0))?0:rd.getString("TA_TDBXZJXXBs","JDRSHJ",0) %></font>��&nbsp;&nbsp;&nbsp;&nbsp;��ͷ�Ѻϼƣ�<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","JDRTFHJ",0))?0:rd.getString("TA_TDBXZJXXBs","JDRTFHJ",0) %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;�ɱ��ϼƣ�<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","JDCBHJ",0))?0:rd.getString("TA_TDBXZJXXBs","JDCBHJ",0) %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;�����ϼƣ�<font color="red"><%="".equals(rd.getString("TA_TDBXZJXXBs","JDLRHJ",0))?0:rd.getString("TA_TDBXZJXXBs","JDLRHJ",0) %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
		</table>
		<%}} %>
	</div>
	<div id="SumPrice">
		<table class="datas">
			<tr>
				<td ><span>���úϼ�</span></td>
			</tr>
			<%
				String YSKZJ = rd.getString("bx_GuideInfo","YSKZJ",0);//Ӫ��
				Float yskzjs=YSKZJ.length()>0?Float.parseFloat(YSKZJ):0;
				String JDHJ = rd.getString("TA_TDBXZJXXBs","JDHJ",0);//�Ƶ�ϼ�
				Float jdhjs=JDHJ.length()>0?Float.parseFloat(JDHJ):0;
				String CTHJ = rd.getString("TA_TDBXZJXXBs","CTHJ",0);//�����ϼ�
				Float cthjs=CTHJ.length()>0?Float.parseFloat(CTHJ):0;
				String PWHJ = rd.getString("TA_TDBXZJXXBs","PWHJ",0);//Ʊ��ϼ�
				Float pwhjs=PWHJ.length()>0?Float.parseFloat(PWHJ):0;
				String CLHJ = rd.getString("TA_TDBXZJXXBs","CLHJ",0);//�����ϼ�
				Float clhjs=CLHJ.length()>0?Float.parseFloat(CLHJ):0;
				String JINDHJ = rd.getString("TA_TDBXZJXXBs","JINDHJ",0);//����ϼ�
				Float jindhjs=JINDHJ.length()>0?Float.parseFloat(JINDHJ):0;
				String DJHJ = rd.getString("TA_TDBXZJXXBs","DJHJ",0);//�ؽӺϼ�
				Float djhjs=DJHJ.length()>0?Float.parseFloat(DJHJ):0;
				String GWGSLCHJ = rd.getString("TA_TDBXZJXXBs","GWGSLCHJ",0);//���﹫˾����ϼ�
				Float gwgslchjs=GWGSLCHJ.length()>0?Float.parseFloat(GWGSLCHJ):0;
				String JDRTFHJ = rd.getString("TA_TDBXZJXXBs","JDRTFHJ",0);//�ӵ���ͷ�Ѻϼ�
				Float jdrtfhjs=JDRTFHJ.length()>0?Float.parseFloat(JDRTFHJ):0;
				
				String JDQD = rd.getString("TA_TDBXZJXXBs","BXJDQD",0);//�Ƶ�ǩ��
				Float jdqds=JDQD.length()>0?Float.parseFloat(JDQD):0;
				String CTQD = rd.getString("TA_TDBXZJXXBs","BXCTQD",0);//����ǩ��
				Float ctqds=CTQD.length()>0?Float.parseFloat(CTQD):0;
				String PWQD = rd.getString("TA_TDBXZJXXBs","BXPWQD",0);//Ʊ��ǩ��
				Float pwqds=PWQD.length()>0?Float.parseFloat(PWQD):0;
				String CLQD = rd.getString("TA_TDBXZJXXBs","BXCLQD",0);//����ǩ��
				Float clqds=CLQD.length()>0?Float.parseFloat(CLQD):0;
				String SceneryQD = rd.getString("TA_TDBXZJXXBs","BX_JDQD",0);//����ǩ��
				Float sceneryqds=SceneryQD.length()>0?Float.parseFloat(SceneryQD):0;
				String DJSQD = rd.getString("TA_TDBXZJXXBs","DJQDZJ",0);//�ؽ���ǩ��
				Float djsqds=DJSQD.length()>0?Float.parseFloat(DJSQD):0;
				
				String JDLRHJ =rd.getString("TA_TDBXZJXXBs","JDLRHJ",0);//�ӵ�����ϼ�
				Float jdlrhjs=JDLRHJ.length()>0?Float.parseFloat(JDLRHJ):0;
				String DYLK = rd.getString("allGuideFy","DYLK",0);//�������
				Float dylks=DYLK.length()>0?Float.parseFloat(DYLK):0;
				String JDXF = rd.getString("TA_TDBXZJXXBs","BXJDXF",0);//�Ƶ��ָ�
				Float jdxfs=JDXF.length()>0?Float.parseFloat(JDXF):0;
				String CTXF = rd.getString("TA_TDBXZJXXBs","BXCTXF",0);//�����ָ�
				Float ctxfs=CTXF.length()>0?Float.parseFloat(CTXF):0;
				String PWXF = rd.getString("TA_TDBXZJXXBs","BXPWXF",0);//Ʊ���ָ�
				Float pwxfs=PWXF.length()>0?Float.parseFloat(PWXF):0;
				String CLXF = rd.getString("TA_TDBXZJXXBs","BXCLXF",0);//�����ָ�
				Float clxfs=CLXF.length()>0?Float.parseFloat(CLXF):0;
				String SceneryXF = rd.getString("TA_TDBXZJXXBs","BX_JDXF",0);//�����ָ�
				Float sceneryxfs=SceneryXF.length()>0?Float.parseFloat(SceneryXF):0;
				String  DJSXF = rd.getString("TA_TDBXZJXXBs","DJXFZJ",0);//�ؽ����ָ�
				Float djxf=DJSXF.length()>0?Float.parseFloat(DJSXF):0;
				String PLUSXF = rd.getString("TA_TDBXZJXXBs","JDXFHJ",0);//�ӵ��ָ�
				Float plusxfs=PLUSXF.length()>0?Float.parseFloat(PLUSXF):0;
				String GWLRHJ = rd.getString("TA_TDBXZJXXBs","GWLRHJ",0);//��������ϼ�
				Float gwlrhjs=GWLRHJ.length()>0?Float.parseFloat(GWLRHJ):0;
				String GWYJGSHJ = rd.getString("TA_TDBXZJXXBs","GWYJGSHJ",0);//����Ӧ����˾
				Float gwyjgshjs=GWYJGSHJ.length()>0?Float.parseFloat(GWYJGSHJ):0;
				String JDYJGSHJ = rd.getString("TA_TDBXZJXXBs","JDYJGSHJ",0);//�ӵ�Ӧ����˾
				Float jdyjgshjs=JDYJGSHJ.length()>0?Float.parseFloat(JDYJGSHJ):0;
				
				//˾��ס�޷�
				String spzsf=rd.getStringByDI("TA_DJ_BXHOTELs","SFZSF",0);
	  			float sp=spzsf.length()>0?Float.parseFloat(spzsf):0;
	  			//Ӫ��
	  			String yskzj=rd.getStringByDI("TA_DJ_GROUPs","YSKZJ",0);
	  			float ys=yskzj.length()>0?Float.parseFloat(yskzj):0;
				
				//Ӧ�������ֽ�ϼ� = ������� - �ָ��ϼ� + ����Ӧ����˾ + �ӵ�Ӧ����˾
				Float YJCWXJHJ = dylks-jdxfs-ctxfs-pwxfs-clxfs-sceneryxfs-djxf+gwyjgshjs+jdyjgshjs;
				
				//��������ϼ� = ������ͷ�Ѻϼ� + Ӧ����˾�ܼ� + �����ܼ�
				//����Ӧ����˾�ܼ�=(Ӧ����˾�ϼ�-��ȫ��Ϊ0,��ȫ����Ϊȫ�����)
				//�ӵ�Ӧ����˾�ܼ�=(Ӧ����˾�ϼ�-��ȫ��Ϊ0,��ȫ����Ϊȫ�����)+��ͷ�Ѻϼ�+����ϼƵ�10%
				
				//�ɱ�֧��=�Ƶ�ϼ�+�����ϼ�+Ʊ��ϼ�+�����ϼ�+����ϼ�+�ؽӺϼ�+˾��ס�޷�
				Float cbzc= jdhjs+cthjs+pwhjs+clhjs+jindhjs+djhjs;
				Float cbqd= jdqds+ctqds+pwqds+clqds+sceneryqds+djsqds;
				Float cbxf= jdxfs+ctxfs+pwxfs+clxfs+sceneryxfs+djxf;
				//�Ŷ����� = Ӫ�� - �ϼ� + ��˾���� + ��������ϼ�  + ������ͷ�� + �ӵ���ͷ�� + �ӵ�����ϼ�  
				Float TDLR = yskzjs - cbzc  + gwlrhjs+ gwgslchjs + jdlrhjs +jdrtfhjs;
				
				Float zmml = yskzjs - cbzc;
			%>
			<tr>
				<td>
					Ӫ��:<font color="red"><%=ys %></font>&nbsp;&nbsp;&nbsp;&nbsp;
					ǩ������ܼƣ�<font color="red"><%=cbqd %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="hidden" name="TA_TDBXZJXXB/ZCQDXJ" value="<%=cbqd %>"/>
					�ָ�����ܼƣ�<font color="red"><%=cbxf %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="hidden" name="TA_TDBXZJXXB/ZCXFXJ" value="<%=cbxf %>"/>
					֧���ϼƣ�<font color="red"><%=cbzc %></font>Ԫ
					<input type="hidden" name="TA_TDBXZJXXB/ZCFYHJ" value="<%=cbzc %>"/>
					����ë����<font color="red"><%=zmml %></font>Ԫ
				</td>
			</tr>
			<tr>
				<td>
					���﹫˾����<font color="red"><%=gwlrhjs %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;
					����Ӧ����˾�ֽ�<font color="red"><%=gwyjgshjs %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;
					�ӵ㾻����<font color="red"><%="".equals(JDLRHJ)?0:JDLRHJ %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;
					�ӵ�Ӧ����˾�ֽ�<font color="red"><%=jdyjgshjs %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td>
				Ӧ�������ֽ�ϼƣ�<font color="red"><%=YJCWXJHJ %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;
				�Ŷ�����<font color="red"><%=TDLR %></font>Ԫ&nbsp;&nbsp;&nbsp;&nbsp;
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
				<td  colspan="2"><span>�������</span>&nbsp;&nbsp;&nbsp;&nbsp;<%String USERID = sd.getString("USERID"); if("admin".equals(USERID)||"ywbzg".equals(USERID)||"lkj".equals(USERID)||"ywy".equals(USERID)){%><span class="showPointer" style=" text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('�鿴���ݶԱ���Ϣ','<%=request.getContextPath()%>/maindj/bx/bxSh/djBxBJ.?TID=<%=rd.getStringByDI("bx_GuideInfo","ID",0) %>'),'500','400',''">���ݶԱ�&nbsp;</span><img src="<%=request.getContextPath()%>/images/Contrast.gif" height="16" width="16"  class="showPointer" onclick="return GB_myShow('�鿴�ӵ㱨����Ϣ','<%=request.getContextPath()%>/maindj/bx/bxSh/djBxBJ.?TID=<%=rd.getStringByDI("bx_GuideInfo","ID",0) %>'),'500','400',''"/><%} %></td>
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
			alert("���α���δ��д����,����д�������ύ!");
		}
	}*/
	function bxShSub(){
		document.bx_group_form.action="djInitShBxList.?pageNO=1&pageSize=10&definitionid=djbxsh&TID=<%=ID %>";
		document.bx_group_form.submit();
	}
</script>
<div align="center" id="last-sub">
<%if(!isTrue){ %>
	<input type="button" id="button" value="�ύ" onclick="bxShSub();"/>
<%} %>
	<input type="button" id="button" value="����" onclick="window.history.go(-1);"/>
</div>
</form>
</body>
</html>
