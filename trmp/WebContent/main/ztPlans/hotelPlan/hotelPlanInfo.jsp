<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@include file="/common.jsp"%>

			<table class="datas" style="text-align: center">
				<tr id="first-tr">
					<td>��������</td>
					<td>�۸�</td>
					<td>������</td>
					<td>�ϼ�</td>
				</tr>
				<%
					String hotel_id=rd.getStringByDI("hotelInfo","hotel_id",0);
					int priceRows=rd.getTableRowsCount("hotelPrice");
					for(int p=0;p<priceRows;p++){
				%>
				<tr>
					<td><%=rd.getStringByDI("hotelPrice","price_name",p)%><input type="hidden" name="hotel<%=hotel_id%>/priceName[<%=p %>]" value="<%=rd.getStringByDI("hotelPrice","dmz",p)%>" onkeydown="checkNum()"></td>
					<td><input type="text" value="<%=rd.getStringByDI("hotelPrice","hprice",p)%>" name="hotel<%=hotel_id%>/price[<%=p %>]" class="hotelPrice" onchange="SumPrice()" onkeydown="checkNum()">Ԫ/��</td>
					<td><input type="text" value="0" class="hotelNum" onchange="SumPrice()" name="hotel<%=hotel_id%>/roomNum[<%=p %>]" onkeydown="checkNum()"/>/��</td>
					<td><input type="text" value="0" class="xiaoji" readonly="readonly"/>Ԫ</td>
				</tr>
				<%} %>
			</table>
