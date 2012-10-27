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
<title>酒店费用明细</title>

</head>

<body>
<form  name="p_hotelplan_form" method="post">
<input type="hidden" name="groupid" value="159"/>
<input type="hidden" name="userno" value="1">
<div id="lable"><span >酒店费用明细</span></div>
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
							<td>房间类型</td>
							<td>价格</td>
							<td>房间数</td>
						</tr>
						<%
							int hotelPlanJgPriceRows=rd.getTableRowsCount("hotelPlanJgList");
							for(int p=0;p<hotelPlanJgPriceRows;p++){
								String jdjhid=rd.getStringByDI("hotelPlanJgList","JDJHID",p);
								if(id.equals(jdjhid)){
						%>
						<tr>
							<td><%=rd.getStringByDI("hotelPlanJgList","PRICENAME",p)%></td>
							<td><%=rd.getStringByDI("hotelPlanJgList","JG",p)%>元/间</td>
							<td><%=rd.getStringByDI("hotelPlanJgList","FJS",p)%>/间</td>
						</tr>
						<%}} %>
						<tr>
							<td align="right" colspan="3">
							<font color="red">签单小计金额：</font><%=rd.getStringByDI("hotelPlanList","QDXJ",i) %>/元&nbsp;&nbsp;&nbsp;
							<font color="red">现付小计金额：</font><%=rd.getStringByDI("hotelPlanList","XFXJ",i) %>/元&nbsp;&nbsp;&nbsp;
							<font color="red">共计：</font><%=rd.getStringByDI("hotelPlanList","HJ",i) %>/元
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="right">联系人：</td>
				<td>
					<%=rd.getStringByDI("hotelPlanList","LXR",i)%>
				</td>
				<td align="right">联系电话:</td>
				<td>
					<%=rd.getStringByDI("hotelPlanList","LXRDH",i)%>
				</td>
			</tr>
			<tr>
				<td  align="right">入住时间：</td>
				<td  ><%=rd.getStringByDI("hotelPlanList","RZ_TIME",i) %></td>
				<td align="right">入住天数：</td>

				<td><%=rd.getStringByDI("hotelPlanList","TS",i) %>天</td>
			</tr>
			<tr>
				<td align="right">司陪住宿：</td>
				<td><%=rd.getStringByDI("hotelPlanList","SFZSF",i) %></td>
				<td align="right">备注：</td>
				<td><%=rd.getStringByDI("hotelPlanList","BZ",i) %></td>

			</tr>

  </table>
  <%} %>

</div>
<table class="datas" style="margin-top: 3px;">
	<tr>
		<td ><span>酒店费用合计</span></td>
	</tr>
	<tr>

		<td align="right">
			<font color="red">签单金额总计：<%="".equals(qdzj)?"0":qdzj %></font>/元&nbsp;&nbsp;&nbsp;
			<font color="red">现付金额总计：<%="".equals(xfzj)?"0":xfzj %></font>/元&nbsp;&nbsp;&nbsp;
			<font color="red">总计：<%="".equals(zj)?"0":zj %></font>/元
		</td>

	</tr>
</table>
</div>
<div align="center" id="last-sub">
	<input type="button" id="button" value="返回" onclick="window.history.go(-1);"/>
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

