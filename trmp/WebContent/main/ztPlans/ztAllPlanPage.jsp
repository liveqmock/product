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
<title>����ҵ��Ƶ�</title>
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
	if(confirm("��ȷ��ɾ���üƻ���")){
		window.location.href="<%=request.getContextPath()%>/main/ztPlans/deleteTabName.?tabName="+tabName+"&groupId="+groupId;
		<%-- jQuery.ajax({
			url:"<%=request.getContextPath()%>/main/ztPlans/deleteTabName.?tabName="+tabName+"&groupId="+groupId,
			async:true,
			cache:false,
			success:function(){
				alert("ɾ���ɹ���");
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
<div id="lable"><span >�żƻ���Ϣ</span></div>
<div id="bm-table">
<div >
	<table id="groupDiv" class="datas">
			<tr>
				<td colspan="4"><span>&nbsp;&nbsp;������Ϣ&nbsp;>>>&nbsp;�źţ�<%=groupID %></span></td>
			</tr>
			<tr>
				<td align="left" colspan="4">&nbsp;&nbsp;��·���ƣ�
				<%String XLMC = rd.getStringByDI("TA_ZT_GROUPs","XLMC",0); %>
				<font color="red"><%=XLMC.length()<=50?XLMC:XLMC.substring(0,50)+"..."  %></font>
				
				</td>
			</tr>
			<tr>
			   <td align="left" colspan="4">
			   &nbsp;&nbsp;
			(ȥ�̣�<font color="red"><%=rd.getStringByDI("TA_ZT_GROUPs","BEGIN_DATE",0) %></font>&nbsp;-&nbsp;�س̣�<font color="red"><%=rd.getStringByDI("TA_ZT_GROUPs","END_DATE",0) %></font>)
			
			</td>
			</tr>
			<tr>
				<td align="left" colspan="4">
			   &nbsp;&nbsp;
			(�ο����ͣ�<font color="red"><%if("2".equals(rd.getStringByDI("TA_ZT_GROUPs","YKLX",0))){out.print("ɢ��");}else{out.print("�Ŷ�");} %></font>)
			&nbsp;&nbsp;
			(������<font color="red"><%=rd.getStringByDI("TA_ZT_GROUPs","DAYS",0) %>��
			  <%=rd.getStringByDI("TA_ZT_GROUPs","NIGHT",0) %>��</font>)
			 &nbsp;&nbsp;
			(������<font color="red">���ˣ�<%=rd.getStringByDI("TA_ZT_GROUPs","ADULT_COUNT",0) %>��&nbsp;
			��ͯ��<%=rd.getStringByDI("TA_ZT_GROUPs","CHILDREN_COUNT",0) %>��</font>)
			 &nbsp;&nbsp;
			(����ӵ��������<font color="red">���<%if("".equals(rd.getStringByDI("TA_ZT_GROUPs","GW",0))){out.print("��");}else{out.print("��");} %>
			�ӵ㣺<%if("".equals(rd.getStringByDI("TA_ZT_GROUPs","JD",0))){out.print("��");}else{out.print("��");} %>
			</font>)
			   </td>
			</tr>
			
			<tr>
				<td align="left" colspan="4">&nbsp;&nbsp;��ע��
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
				<td><span>�Ƶ�ƻ���Ϣ</span></td>
			</tr>
			<tr>
			<td>�Ƶ���δ���ƻ�(<span class="showPointer" style="text-decoration:underline;color:red;font-weight:100" onclick="return GB_myShow('�Ƶ�ƻ�','<%=request.getContextPath()%>/main/ztPlans/hotelPlan/ztInitAddHotelPlan.?TA_ZT_GROUP/ID=<%=groupID %>&TA_ZT_JHHOTEL/TID=<%=groupID %>&TID=<%=groupID %>','800','750','')">ҵ��Ƶ�</span>)</td>
			</tr>
		</table>
	<%}else{ %>
		<table class="datas">
			<tr>
			  <td><span>�Ƶ�ƻ���Ϣ</span>
			  	  <span class="showPointer" title="�鿴�ƻ���Ϣ" onclick="return GB_myShow('�Ƶ�ƻ�','<%=request.getContextPath()%>/main/ztPlans/hotelPlan/ztSelectHotelPlanInfo.?TA_ZT_GROUP/ID=<%=groupID %>&TA_ZT_JHHOTEL/TID=<%=groupID %>&TA_TDJDXXZJB_ZT/TID=<%=groupID %>&TID=<%=groupID %>','800','750','')">�鿴</span>
			  
			  <%
				if(!action.equals("v")) {
			  %>
			 	  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="�޸ľƵ�ƻ���Ϣ" onclick="return GB_myShow('�Ƶ�ƻ�','<%=request.getContextPath()%>/main/ztPlans/hotelPlan/ztInitUpdateHotelPlan.?TA_ZT_GROUP/ID=<%=groupID %>&TA_ZT_JHHOTEL/TID=<%=groupID %>&TA_TDJDXXZJB_ZT/TID=<%=groupID %>&TID=<%=groupID %>','800','750','')">�޸�</span>
			 	  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="ɾ���Ƶ�ƻ���Ϣ" onclick="deleteTable('ztPlanHotel','<%=groupID%>')">ɾ��</span>
			  <%
			  }%>
			  </td>
			</tr>
			<tr>
				<td align="right">
					�Ƶ���úϼƣ�ǩ�����ϼƣ�<font color="red"><%=qdzszj %></font>Ԫ  �ָ����ϼƣ�<font color="red"><%=xfzszj %></font>Ԫ  �ϼƣ�<font color="red"><%=zszj %></font>Ԫ
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
				<td><span>�����ƻ���Ϣ</span></td>
			</tr>
			<tr>
				<td>������δ���ƻ�(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('�����ƻ�','<%=request.getContextPath()%>/main/ztPlans/dinnerPlan/ztInitAddDinnerPlan.?TA_ZT_GROUP/ID=<%=groupID %>&TA_ZT_JHCT/ID=&TID=<%=groupID %>','800','850','')">ҵ��Ƶ�</span>)</td>
			</tr>
		</table>
		<%}else{ %>
			<table class="datas">
			  <tr>
				<td>
				  <span>�����ƻ���Ϣ</span> <span class="showPointer" title="�鿴�ƻ���Ϣ" onclick="return GB_myShow('�����ƻ�','<%=request.getContextPath()%>/main/ztPlans/dinnerPlan/ztListDinnerPlan.?TA_ZT_GROUP/ID=<%=groupID %>&TA_ZT_JHCT/ID=&TID=<%=groupID %>','800','850','')">�鿴</span>
				<%
				if(!action.equals("v")) {
			  	%>	
				  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="�޸Ĳ����ƻ���Ϣ" onclick="return GB_myShow('�����ƻ�','<%=request.getContextPath()%>/main/ztPlans/dinnerPlan/ztInitUpdateDinnerPlan.?TA_ZT_GROUP/ID=<%=groupID %>&TA_ZT_JHCT/ID=&TID=<%=groupID %>','800','850','')">�޸�</span>
				  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="ɾ�������ƻ���Ϣ" onclick="deleteTable('ztPlanDinner','<%=groupID%>')">ɾ��</span>
				<%
				}%>
				</td>
				</tr>
				<tr>
					<td align="right">
						�������úϼƣ�ǩ�����ϼƣ�<font color="red"><%=qdctzj %></font>Ԫ  �ָ����ϼƣ�<font color="red"><%=xfctzj %></font>Ԫ  �ϼƣ�<font color="red"><%=ctzj %></font>Ԫ
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
				<td><span>Ʊ��ƻ���Ϣ</span></td>
			</tr>
			<tr>
				<td>Ʊ����δ���ƻ�(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('Ʊ��Ƶ�','<%=request.getContextPath()%>/main/ztPlans/ticketPlan/ztInitTicketP.?TA_ZT_JHPW/TID=<%=groupID %>&TID=<%=groupID %>&dmsm/JTGJ=2&TA_TDJDXXZJB_ZT/TID=<%=groupID %>&TA_ZT_JHPWMX/TID=<%=groupID %>','800','850','')">ҵ��Ƶ�</span>)</td>
			</tr>
		</table>
<%
	}else{%>
		<table class="datas">
		  <tr>
			<td>
			  <span>Ʊ��ƻ���Ϣ</span> <span class="showPointer" title="�鿴�ƻ���Ϣ" onclick="return GB_myShow('Ʊ��Ƶ�','<%=request.getContextPath()%>/main/ztPlans/ticketPlan/ztInitTicketP.?TA_ZT_JHPW/TID=<%=groupID %>&TID=<%=groupID %>&dmsm/JTGJ=2&flag=view&TA_TDJDXXZJB_ZT/TID=<%=groupID %>&TA_ZT_JHPWMX/TID=<%=groupID %>','800','850','')">�鿴</span>
			<%
				if(!action.equals("v")) {
			  	%>
			  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="�޸�Ʊ��ƻ���Ϣ" onclick="return GB_myShow('Ʊ��Ƶ�','<%=request.getContextPath()%>/main/ztPlans/ticketPlan/ticketPlan/ztInitTicketP.?TA_ZT_JHPW/TID=<%=groupID %>&TID=<%=groupID %>&dmsm/JTGJ=2&TA_TDJDXXZJB_ZT/TID=<%=groupID %>&TA_ZT_JHPWMX/TID=<%=groupID %>','800','850','')">�޸�</span>
			  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="ɾ��Ʊ��ƻ���Ϣ" onclick="deleteTable('ztPlanTicket','<%=groupID%>')">ɾ��</span>
			<%
			}%>
			</td>
			</tr>
			<tr>
				<td align="right">
					Ʊ����úϼƣ�ǩ�����ϼƣ�<font color="red"><%=tqd %></font>Ԫ  �ָ����ϼƣ�<font color="red"><%=txf %></font>Ԫ  �ϼƣ�<font color="red"><%=tzj %></font>Ԫ  �������ܼƣ�<font color="red"><%=sxf %></font>Ԫ
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
				<td><span>�����ƻ���Ϣ</span></td>
			</tr>
			<tr>
				<td>������δ���ƻ�(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('�����ƻ���ϸ','<%=request.getContextPath() %>/main/ztPlans/vehPlan/ztInitVehPlan.?TA_ZT_JHCL/TID=<%=groupID %>&TA_TDJDXXZJB_ZT/TID=<%=groupID %>','800','800','')">ҵ��Ƶ�</span>)</td>
			</tr>
		</table>
<%
}else{
	
%>	
		<table class="datas">
		  <tr>
			<td>
			  <span>�����ƻ���Ϣ</span> <span class="showPointer" title="�鿴�����ƻ���ϸ" onclick="return GB_myShow('�����ƻ���ϸ','<%=request.getContextPath() %>/main/ztPlans/vehPlan/ztInitVehPlan.?TA_ZT_JHCL/TID=<%=groupID %>&TA_TDJDXXZJB_ZT/TID=<%=groupID %>&flag=view','800','800','')">�鿴</span>
			<%
			if(!action.equals("v")) {
			%>
			  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="�޸ĳ����ƻ���Ϣ" onclick="return GB_myShow('�޸ĳ����ƻ���Ϣ','<%=request.getContextPath() %>/main/ztPlans/vehPlan/ztInitVehPlan.?TA_ZT_JHCL/TID=<%=groupID %>&TA_TDJDXXZJB_ZT/TID=<%=groupID %>','800','800','')">�޸�</span>
			  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="ɾ�������ƻ���Ϣ" onclick="deleteTable('ztPlanCar','<%=groupID%>')">ɾ��</span>
			<%
			}%>
			</td>
			</tr>
			<tr>
				<td  align="right">�������úϼƣ�ǩ�����ϼƣ�<font color="red"><%=qd %></font>Ԫ  �ָ����ϼƣ�<font color="red"><%=xf %></font>Ԫ  �ϼƣ�<font color="red"><%=zj %></font>Ԫ</td>
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
				<td><span>����ƻ���Ϣ</span></td>
			</tr>
			<tr>
				<td>������δ���ƻ�(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="return GB_myShow('�鿴����ƻ���Ϣ','<%=request.getContextPath() %>/main/ztPlans/sceneryPlan/ztInitSceneryPlan.?TA_ZT_JHJD/TID=<%=groupID %>&TA_TDJDXXZJB_ZT/TID=<%=groupID %>','800','700','')">ҵ��Ƶ�</span>)</td>
			</tr>
		</table>
		<%}else{ %>
		<table class="datas">
		  <tr>
			<td>
				  <span>����ƻ���Ϣ</span> <span title="�鿴����ƻ���Ϣ"  class="showPointer"  onclick="return GB_myShow('�鿴����ƻ���Ϣ','<%=request.getContextPath() %>/main/ztPlans/sceneryPlan/ztInitSceneryPlan.?TA_TDJDXXZJB_ZT/TID=<%=groupID %>&TA_ZT_JHJD/TID=<%=groupID %>&flag=view','800','700','')"> �鿴</span>
					<%
					if(!action.equals("v")) {
				  	%>
					  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="�޸ľ���ƻ���Ϣ" onclick="return GB_myShow('�޸ľ���ƻ���Ϣ','<%=request.getContextPath() %>/main/ztPlans/sceneryPlan/ztInitSceneryPlan.?TA_TDJDXXZJB_ZT/TID=<%=groupID %>&TA_ZT_JHJD/TID=<%=groupID %>','800','700','')">�޸�</span>
					  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="ɾ������ƻ���Ϣ" onclick="deleteTable('ztPlanScenery','<%=groupID%>')">ɾ��</span>
					<%
					}
					%>
				</td>
			</tr>
			<tr>
				<td  align="right">������úϼƣ�ǩ�����ϼƣ�<font color="red"><%=QDJDZJ %></font>Ԫ  �ָ����ϼƣ�<font color="red"><%=XFJDZJ %></font>Ԫ  �ϼƣ�<font color="red"><%=JDZJ %></font>Ԫ</td>
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
				<td><span>�ؽӼƻ���Ϣ</span></td>
			</tr>
			<tr>
				<td>�ؽ���δ���ƻ�(<span class="showPointer" style="text-decoration:underline;color:red;font-weight:100" onclick="return GB_myShow('�ؽ���ƻ�','<%=request.getContextPath()%>/main/ztPlans/groundTravePlan/ztInitGroundPlan.?TA_ZT_JHDJ/TID=<%=groupID %>&TA_TDJDXXZJB_ZT/TID=<%=groupID %>','800','600')">ҵ��Ƶ�</span>)</td>
			</tr>
		</table>
		<%}else{ %>	
		<table class="datas">
		  <tr>
			<td><span>�ؽӼƻ���Ϣ</span> 
				<span title="�鿴�ؽӼƻ���Ϣ"  class="showPointer" title="�鿴�ؽӼƻ���Ϣ" onclick="return GB_myShow('�鿴�ؽ���ƻ���Ϣ','<%=request.getContextPath() %>/main/ztPlans/groundTravePlan/ztInitGroundPlan.?TA_ZT_JHDJ/TID=<%=groupID %>&TA_TDJDXXZJB_ZT/TID=<%=groupID %>&flag=view','800','600','')">�鿴</span>
				<%
				if(!action.equals("v")) {
			  	%>
				<span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="�޸ĵؽӼƻ���Ϣ" onclick="return GB_myShow('�޸ĵؽ���ƻ���Ϣ','<%=request.getContextPath() %>/main/ztPlans/groundTravePlan/ztInitGroundPlan.?TA_ZT_JHDJ/TID=<%=groupID %>&TA_TDJDXXZJB_ZT/TID=<%=groupID %>','800','600','')">�޸�</span>
				<span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="ɾ���ؽӼƻ���Ϣ" onclick="deleteTable('ztPlanTrave','<%=groupID%>')">ɾ��</span>
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
				         �ؽ���:
				   <font color="red"><%=cmpnyName%></font>
						<a href="###" onclick="return GB_myShow('�ϴ�ȷ�ϼ�','<%=request.getContextPath()%>/main/ztGroupMng/ztinitConfirmForZTS.?TA_TRAVELAGENCY/TRAVEL_AGC_ID=<%=ztsID %>&id=<%=tztsKeyID %>','400','800','')" >
						    <img alt="�ϴ�ȷ�ϼ�" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[�ϴ�]</a>
						<%
				if(!action.equals("v")) {
			  	%>
						<a href="###" onclick="return GB_myShow('�鿴ȷ�ϼ�','<%=request.getContextPath()%>/main/ztGroupMng/ztviewConfirmByZTS.?TA_ZT_QRJ/TZTSID=<%=tztsKeyID %>','800','800','')">
							<img alt="�鿴ȷ�ϼ�" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[�鿴]</a>
				  <%} %>
				 <%} } %>
	 			</td>
	 		</tr>
	 		<tr>
				<td  align="right">�ؽӷ��úϼƣ�ǩ�����ϼƣ�<font color="red"><%=DJQDZJ %></font>Ԫ  �ָ����ϼƣ�<font color="red"><%=DJXFZJ %></font>Ԫ  �ϼƣ�<font color="red"><%=DJZJ %></font>Ԫ</td>
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
				<td><span>����ƻ���Ϣ</span></td>
			</tr>
			<tr>
				<td>������δ���ƻ�(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="GB_myShow('����ƻ���Ϣ','<%=request.getContextPath()%>/main/ztPlans/ShoppingPlan/ztInitShopPlan.?TA_ZT_JHGW/TID=<%=groupID %>','800','600')">ҵ��Ƶ�</span>)</td>
			</tr>
		</table>
	<%}else{ %>
		<table class="datas">
		  <tr>
			<td><span>����ƻ���Ϣ</span> <span class="showPointer" title="�鿴����ƻ���Ϣ" onclick="return GB_myShow('����ƻ���Ϣ','<%=request.getContextPath() %>/main/ztPlans/ShoppingPlan/ztInitShopPlan.?TA_ZT_JHGW/TID=<%=groupID %>&flag=view','800','800','')">�鿴</span>
			<%
				if(!action.equals("v")) {
			  	%>
				<span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="�޸Ĺ���ƻ���Ϣ" onclick="return GB_myShow('�޸Ĺ���ƻ���Ϣ','<%=request.getContextPath() %>/main/ztPlans/plusPlan/ztInitShopPlan.?TA_ZT_JHGW/TID=<%=groupID %>','800','800','')">�޸�</span>
				<span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="ɾ������ƻ���Ϣ" onclick="deleteTable('ztPlanShop','<%=groupID%>')">ɾ��</span>
				<%
				}
				%>
				</td>
		  </tr>
		  <tr>
			<td  align="right">�������ݣ�<font color="red"><%for(int i = 0; i < newShopRows; i++){String SFXZ= rd.getStringByDI("TA_ZT_JHGWs","SFXZ",i); if("Y".equals(SFXZ)){out.print(rd.getStringByDI("TA_ZT_JHGWs","GWDMC",i)+"&nbsp;");}} %></font></td>
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
				<td><span>�ӵ�ƻ���Ϣ</span></td>
			</tr>
			<tr>
				<td>�ӵ���δ���ƻ�(<span class="showPointer" style="text-decoration:underline; color:red;font-weight:100" onclick="GB_myShow('�ӵ�ƻ�','<%=request.getContextPath()%>/main/ztPlans/plusPlan/ztInitPlusPlan.?TA_ZT_JHJIAD/TID=<%=groupID %>&TID=<%=groupID %>','800','600')">ҵ��Ƶ�</span>)</td>
			</tr>
		</table>
<%
}else{%>
		<table class="datas">
		<tr>
		  <td><span>�ӵ�ƻ���Ϣ</span> <span class="showPointer" title="�鿴�ӵ�ƻ���Ϣ" onclick="return GB_myShow('�ӵ�ƻ���Ϣ','<%=request.getContextPath() %>/main/ztPlans/plusPlan/ztInitNewScenery4Edit.?TA_ZT_JHJIAD/TID=<%=groupID %>&flag=view','800','800','')">�鿴</span>
			<%
			if(!action.equals("v")) {
			%>
			  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="�޸ļӵ�ƻ���Ϣ" onclick="return GB_myShow('�޸ļӵ�ƻ���Ϣ','<%=request.getContextPath() %>/main/ztPlans/plusPlan/ztInitNewScenery4Edit.?TA_ZT_JHJIAD/TID=<%=groupID %>','800','800','')">�޸�</span>
			  <span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="ɾ���ӵ�ƻ���Ϣ" onclick="deleteTable('ztPlanPlus','<%=groupID%>')">ɾ��</span>
			<%
			}
			%>
			</td>
		</tr>
		<tr>
		  <td  align="right">�ӵ����ݣ�<font color="red"><%for(int i=0;i<rd.getTableRowsCount("rsScenerys");i++){out.print(rd.getStringByDI("rsScenerys","jdmc",i)+" ");} %></font></td>
		</tr>
		</table>
<%
}}%>
	</div>
	<div id="SumPrice">
		<table class="datas">
			<tr>
				<td ><span>���������</span></td>
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
				<td align="right">���������:<font color="red"><%=dylkjyj %></font>Ԫ</td>
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
				<td><span>���μƻ���Ϣ</span></td>
			</tr>
			<tr>
			<td>������δ���ƻ�(<span class="showPointer" style="text-decoration:underline;color:red;font-weight:100" onclick="return GB_myShow('���μƻ�','<%=request.getContextPath()%>/main/ztPlans/guidePlan/ztInitGuidePlan.?TA_ZT_JHDY/ID=&TID=<%=groupID %>&role=guide','800','600','')">ҵ��Ƶ�</span>)</td>
			</tr>
		</table>
		<%}else{ %>
		<table class="datas">
		  <tr>
			<td><span>���μƻ���Ϣ</span> <span class="showPointer" title="�鿴���η�����ϸ" onclick="return GB_myShow('���η�����ϸ','<%=request.getContextPath() %>/main/ztPlans/guidePlan/ztInitEditGuidePlan.?role=guide&TA_ZT_JHDY/ID=&TID=<%=groupID %>&action=view','800','600','')">�鿴</span>
			 <%
				if(!action.equals("v")) {
			  	%>
			 	<span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="�޸ĵ��μƻ���Ϣ" onclick="return GB_myShow('�޸ĵ��μƻ�','<%=request.getContextPath() %>/main/ztPlans/guidePlan/ztInitEditGuidePlan.?role=guide&TA_ZT_JHDY/ID=&TID=<%=groupID %>&action=edit','800','600','')">�޸�</span>
			 	<span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="ɾ�����μƻ���Ϣ" onclick="deleteTable('ztPlanGuide','<%=groupID%>')">ɾ��</span>
			<%} %>
			</td>
			</tr>
			<tr>
				<td  align="right">
					���η��úϼƣ������ϼƣ�<font color="red"><%=dylk %></font>Ԫ  �����ѽ��ϼƣ�<font color="red"><%=dff %></font>Ԫ  
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
				<td  colspan="2"><span>�������</span></td>
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
			alert("�мƻ�û���ƶ�,���ƶ���ؼƻ�!");
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
	<input type="button" id="button" value="�ύ" onclick="p_editPlan();"/>
<%
	}%>
	<input type="button" id="button" value="�� ��" onclick="window.history.go(-1)"/>
</div>
</div>
</form>
</body>
</html>