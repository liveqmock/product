<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
 <%@include file="/common.jsp" %>
<div >
	<table style="width:100%">
<%
int pRows = rd.getTableRowsCount("GroupPeopleList");
for(int i = 0; i < pRows; i++){
%>

		<tr>
				<td >
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������<%=rd.getStringByDI("GroupPeopleList", "visitor_nm", i) %>
				</td>
				<td >
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�Ա�
					<%
						String sex = rd.getStringByDI("GroupPeopleList", "sex", i);
					
						if ("1".equals(sex))
						{
							out.print("��");
						}
						else if ("2".equals(sex))
						{
							out.print("Ů");
						}
					%>
				</td>
				<td >
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�Ƿ��ͯ��
					<%
						String isChild = rd.getStringByDI("GroupPeopleList", "ischild", i);
					
						if ("1".equals(isChild))
						{
							out.print("��ͯ");
						}
						else
						{
							out.print("����");
						}
					%>
				</td>
				<td >
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��λ�ţ�<%=rd.getStringByDI("GroupPeopleList", "seat_num", i) %>
				</td>
				<td >
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�绰��<%=rd.getStringByDI("GroupPeopleList", "tel", i) %>
				</td>
		</tr>
	
<%}%>
	</table>
</div>