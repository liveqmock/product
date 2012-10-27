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
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;姓名：<%=rd.getStringByDI("GroupPeopleList", "visitor_nm", i) %>
				</td>
				<td >
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性别：
					<%
						String sex = rd.getStringByDI("GroupPeopleList", "sex", i);
					
						if ("1".equals(sex))
						{
							out.print("男");
						}
						else if ("2".equals(sex))
						{
							out.print("女");
						}
					%>
				</td>
				<td >
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;是否儿童：
					<%
						String isChild = rd.getStringByDI("GroupPeopleList", "ischild", i);
					
						if ("1".equals(isChild))
						{
							out.print("儿童");
						}
						else
						{
							out.print("成人");
						}
					%>
				</td>
				<td >
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;座位号：<%=rd.getStringByDI("GroupPeopleList", "seat_num", i) %>
				</td>
				<td >
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;电话：<%=rd.getStringByDI("GroupPeopleList", "tel", i) %>
				</td>
		</tr>
	
<%}%>
	</table>
</div>