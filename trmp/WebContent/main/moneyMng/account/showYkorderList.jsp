<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
 <%@include file="/common.jsp" %>
<div >
	<table style="width:100%">
		<tr id="first-tr">
				<td align="center" width="15%">Ӫҵ������</td>
				<td align="center" width="11%">������</td>
				<td align="center" width="9%">�ο�����</td>
				<td align="center" width="25%">�۸����� - ���� - ���� - �ϼ�</td>
				<td align="center" width="25%">�������</td>
				<td align="center" width="15%">����</td>
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
						%>
							<%=rd.getStringByDI("rsCwdzPriceInfo", "dmsm1", j) %>-
							<%=rd.getStringByDI("rsCwdzPriceInfo", "person_count", j) %>��-
							<font color="red"><%=rd.getStringByDI("rsCwdzPriceInfo", "price_th", j) %></font>Ԫ-
							<font color="red"><%=rd.getStringByDI("rsCwdzPriceInfo", "hj", j) %></font>Ԫ<br>
						<%
					}
					%>
					</div>
				</td>
				<td>
				�����<input type="text" name="DDH<%=DDH %>/SPYJ" size="30"/><br>
					<%
						int yjRows = rd.getTableRowsCount("SPYJ");
						for(int a=0;a<yjRows;a++){
						String groupID5=rd.getStringByDI("SPYJ","TID",a);
						if(DDH.equals(groupID5)){
					%>
							<b><%=rd.getStringByDI("SPYJ","USERNAME",a)%>:</b>
							<%=rd.getStringByDI("SPYJ","SPYJ",a)%>;<br>
					<%
						}}
					%>
				</td>
				<td align="center">
						<input type="radio" value="Y" name="SPZT<%=DDH %>/TGZT" checked="checked"/>ͨ��
         		&nbsp;<input type="radio" value="N" name="SPZT<%=DDH %>/TGZT"/>��ͨ��
         		<input type="button" value="�ύ" onclick="cwdzSubmit2('<%=DDH %>');" />
				</td>
		</tr>
	
<%}%>
	</table>
</div>