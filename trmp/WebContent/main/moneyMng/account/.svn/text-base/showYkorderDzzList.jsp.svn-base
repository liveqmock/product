<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>

<div >
	<table style="width:100%">
		<tr id="first-tr">
				<td align="center" width="15%">营业部名称</td>
				<td align="center" width="11%">订单号</td>
				<td align="center" width="9%">游客姓名</td>
				<td align="center" width="25%">价格类型 - 人数 - 单价 - 合计</td>
				<td align="center" width="25%">审批意见</td>
		</tr>
<%
int yRows = rd.getTableRowsCount("rsCwdzYkorderInfo");
for(int i = 0; i < yRows; i++){
	String DDH = rd.getStringByDI("rsCwdzYkorderInfo", "ID", i);
%>

		<tr>
			<td align="center"><%=rd.getStringByDI("rsCwdzYkorderInfo", "cmpny_name", i) %></td>
			<td align="center"><%=rd.getStringByDI("rsCwdzYkorderInfo", "ID", i) %></td>
			<td align="center"><%=rd.getStringByDI("rsCwdzYkorderInfo", "visitor_nm", i) %></td>
			<td align="center">
		<div>
			
					<%
					int pRows = rd.getTableRowsCount("rsCwdzPriceInfo");
					for(int j = 0; j < pRows; j++){
						String orderId = rd.getStringByDI("rsCwdzPriceInfo", "orderId", j);
						String personCount = rd.getStringByDI("rsCwdzPriceInfo", "person_count", j);
						if(orderId.equals(DDH)){
						%>
						
							<%=rd.getStringByDI("rsCwdzPriceInfo", "dmsm1", j) %>-
							<%=personCount %>人-
							<font color="red"><%=rd.getStringByDI("rsCwdzPriceInfo", "price_th", j) %></font>元-
							<font color="red"><%=rd.getStringByDI("rsCwdzPriceInfo", "hj", j) %></font>元<br>
							
						<%
						}
					}
					%>
		</div>
				</td>
				<td>
				意见：<br>
				<%
						int yjRows = rd.getTableRowsCount("SPYJ");
						for(int a=0;a<yjRows;a++){
							String groupID5=rd.getStringByDI("SPYJ","TID",a);
							if(DDH.equals(groupID5)){
						%>
								<b><%=rd.getStringByDI("SPYJ","USERNAME",a)%>:</b>
								   <%=rd.getStringByDI("SPYJ","SPYJ",a)%>;<br>
						<%
							}
						}
					%>
				</td>
		</tr>
	
<%}%>
	</table>
</div>