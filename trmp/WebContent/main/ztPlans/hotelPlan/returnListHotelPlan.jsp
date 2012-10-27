<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>
<title>�Ƶ������ϸ</title>

</head>

<body>
<form  name="p_hotelplan_form" method="post">
<input type="hidden" name="groupid" value="159"/>
<input type="hidden" name="userno" value="1">
<div id="lable"><span >�Ƶ������ϸ</span></div>
<div id="bm-table">

	<div id="hotelDiv">
	<input type='hidden' value='up' name='state'>	
	<%
		int hotelPlanRows=rd.getTableRowsCount("hotelPlanList");
		String qdzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","QDZSZJ",0);
		String xfzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","XFZSZJ",0);
		String zj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","ZSZJ",0);
		for(int i=0;i<hotelPlanRows;i++){
			String id=rd.getStringByDI("hotelPlanList","ID",i);
	%>
	<table class="datas select-hotel" width="95%" id="hotel[<%=i %>]" >
			<tr>
			  <td colspan="4">
			 	<select name="TA_ZT_JHHOTEL/SF[<%=i %>]" id="pro<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="1"></select>
			  	<select name="TA_ZT_JHHOTEL/CITY[<%=i %>]" id="city<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="2"></select>
			  	<select name="TA_ZT_JHHOTEL/XQ[<%=i %>]" id="area<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="3"></select>
			  	<select name="TA_ZT_JHHOTEL/XJ[<%=i %>]" id="level<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="4"></select>
			  	<select name="TA_ZT_JHHOTEL/JDID[<%=i %>]" class="hotelid" id="id_hotel<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="5"></select>
			  </td>
			</tr>
			<tr >
				<td colspan="4" id="hotelInfo[<%=i%>]">
					<table class="datas" style="text-align: center">
						<tr id="first-tr">
							<td>��������</td>
							<td>�۸�</td>
							<td>������</td>
						</tr>
						<%
							int hotelPlanJgPriceRows=rd.getTableRowsCount("hotelPlanJgList");
							for(int p=0;p<hotelPlanJgPriceRows;p++){
								String jdjhid=rd.getStringByDI("hotelPlanJgList","JDJHID",p);
								if(id.equals(jdjhid)){
						%>
						<tr>
							<td><%=rd.getStringByDI("hotelPlanJgList","PRICENAME",p)%></td>
							<td><%=rd.getStringByDI("hotelPlanJgList","JG",p)%>Ԫ/��</td>
							<td><%=rd.getStringByDI("hotelPlanJgList","FJS",p)%>/��</td>
						</tr>
						<%}} %>
						<tr>
							<td align="right" colspan="3">
							<font color="red">ǩ��С�ƽ�</font><%=rd.getStringByDI("hotelPlanList","QDXJ",i) %>/Ԫ&nbsp;&nbsp;&nbsp;
							<font color="red">�ָ�С�ƽ�</font><%=rd.getStringByDI("hotelPlanList","XFXJ",i) %>/Ԫ&nbsp;&nbsp;&nbsp;
							<font color="red">���ƣ�</font><%=rd.getStringByDI("hotelPlanList","HJ",i) %>/Ԫ
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="right">��ϵ�ˣ�</td>
				<td>
					<%=rd.getStringByDI("hotelPlanList","LXR",i)%>
				</td>
				<td align="right">��ϵ�绰:</td>
				<td>
					<%=rd.getStringByDI("hotelPlanList","LXRDH",i)%>
				</td>
			</tr>
			<tr>
				<td  align="right">��סʱ�䣺</td>
				<td  ><%=rd.getStringByDI("hotelPlanList","RZ_TIME",i) %></td>
				<td align="right">��ס������</td>

				<td><%=rd.getStringByDI("hotelPlanList","TS",i) %>��</td>
			</tr>
			<tr>
				<td align="right">˾��ס�ޣ�</td>
				<td><%=rd.getStringByDI("hotelPlanList","SFZSF",i) %></td>
				<td align="right">��ע��</td>
				<td><%=rd.getStringByDI("hotelPlanList","BZ",i) %></td>

			</tr>

  </table>
  <%} %>

</div>
<table class="datas" style="margin-top: 3px;">
	<tr>
		<td ><span>�Ƶ���úϼ�</span></td>
	</tr>
	<tr>

		<td align="right">
			<font color="red">ǩ������ܼƣ�<%="".equals(qdzj)?"0":qdzj %></font>/Ԫ&nbsp;&nbsp;&nbsp;
			<font color="red">�ָ�����ܼƣ�<%="".equals(xfzj)?"0":xfzj %></font>/Ԫ&nbsp;&nbsp;&nbsp;
			<font color="red">�ܼƣ�<%="".equals(zj)?"0":zj %></font>/Ԫ
		</td>

	</tr>
</table>
</div>
<div align="center" id="last-sub">
	<input type="button" id="button" value="����" onclick="window.history.go(-1);"/>
</div>

</form>
</body>
</html>
<script type="text/javascript">
	<%
	for(int v=0;v<hotelPlanRows;v++){
	%>
	var linkage = new Linkage("dataSrc<%=v%>", "<%=request.getContextPath()%>/main/data/Hotelz.xml");
	linkage.init();
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("hotelPlanList","SF",v)%>",1);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("hotelPlanList","CITY",v)%>",2);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("hotelPlanList","XQ",v)%>",3);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("hotelPlanList","LEVEL_ID",v)%>",4);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("hotelPlanList","JDID",v)%>",5);
	<%}%>
</script>

