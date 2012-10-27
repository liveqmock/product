<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
String state = rd.getString("state");
%>
<div >
	<table style="width:100%">
		<tr id="first-tr">
				<td align="center" width="15%">营业部名称</td>
				<td align="center" width="12%">订单号</td>
				<td align="center" width="10%">游客姓名</td>
				<td align="center" width="8%">保险</td>
				<td align="center" width="10%">接送加价</td>
				<td align="center" width="25%">价格类型 - 人数 - 单价 - 小计</td>
				<td align="center" width="10%">合计</td>
			<%
			if("0".equals(state)){
			%>
				<td align="center" width="20%">确认收款</td>
			<%
			}%>
		</tr>
<%
int yRows = rd.getTableRowsCount("rsCwdzYkorderInfo");
for(int i = 0; i < yRows; i++){
	String DDH = rd.getStringByDI("rsCwdzYkorderInfo", "ID", i);
	String insurance = rd.getStringByDI("rsCwdzYkorderInfo", "bx", i);
	String jj = rd.getStringByDI("rsCwdzYkorderInfo", "add_m_count", i);
	double total = (insurance.equals("")?0:Double.parseDouble(insurance))+(jj.equals("")?0:Double.parseDouble(jj));
%>

		<tr>
			<td align="center"><%=rd.getStringByDI("rsCwdzYkorderInfo", "cmpny_name", i) %></td>
			<td align="center"><%=rd.getStringByDI("rsCwdzYkorderInfo", "ID", i) %></td>
			<td align="center"><%=rd.getStringByDI("rsCwdzYkorderInfo", "visitor_nm", i) %></td>
			<td align="center"><%=insurance %></td>
			<td align="center"><%=jj %></td>
			<td align="center">
				<div>
					<%
					int pRows = rd.getTableRowsCount("rsCwdzPriceInfo");
					for(int j = 0; j < pRows; j++){
						String orderId = rd.getStringByDI("rsCwdzPriceInfo", "orderId", j);
						String personCount = rd.getStringByDI("rsCwdzPriceInfo", "person_count", j);
						String hj = rd.getStringByDI("rsCwdzPriceInfo", "hj", j);
						if(orderId.equals(DDH)){
							total += (hj.equals("")?0:Double.parseDouble(hj));
						%>
						
							<%=rd.getStringByDI("rsCwdzPriceInfo", "dmsm1", j) %> -
							<%=personCount %>人 -
							<font color="red"><%=rd.getStringByDI("rsCwdzPriceInfo", "price_th", j) %></font>元 -
							<font color="red"><%=hj %></font>元<br>
							
						<%
						}
					}
					%>
				</div>
			</td>
			<td align="center"><%=total %>元</td>
			<%
			if("0".equals(state)){
			%>
			<td align="center">
         		<input type="button" value="提交" onclick="updateConfirmState('<%=DDH %>');" class="button"/>
			</td>
			<%
			}%>
		</tr>
	
<%}%>
	</table>
</div>