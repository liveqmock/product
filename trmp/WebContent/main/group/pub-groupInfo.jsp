<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
 <%@include file="/common.jsp" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<title>�Ż�����Ϣ</title>
</head>
<body>
<table class="datas" width="95%">
		<tr>
			<td colspan="4" ><span>��-<%=rd.getStringByDI("groupInfo","g_id",0) %>&nbsp;������Ϣ</span></td>
		</tr>
		<tr>
		  <td colspan="2" >�������ڣ�<font color="red"><%=rd.getStringByDI("groupInfo","BEGIN_DATE",0) %></font>&nbsp;&nbsp;
		   </td>
		   <td colspan="2">�������ڣ�<font color="red"><%=rd.getStringByDI("groupInfo","END_DATE",0) %></font></td>
		</tr>
		<tr>
			<td width="10%" align="right">��·���ƣ�</td>
			<td width="35%" ><font color="red"><%=rd.getStringByDI("groupInfo","line_name",0) %></font></td>
			<td width="10%" align="right"> �źű�ţ�</td>
			<td >
				<input type="hidden" value="<%=rd.getStringByDI("groupInfo","id",0) %>" name="group_fkId"/>
				<%=rd.getStringByDI("groupInfo","g_id",0) %>
			</td>
		</tr>
		<tr>
			<td align="right">������</td>
			<td >��ͯ:<%=rd.getStringByDI("groupInfo","children_count",0) %>��&nbsp;����:<%=rd.getStringByDI("groupInfo","adult_count",0) %>��</td>
			<td align="right">������</td>
			<td >
			�������:<%=rd.getStringByDI("groupInfo","zdrs",0) %>��&nbsp;�ɳ�������:<%=rd.getStringByDI("groupInfo","kctrs",0) %>��
			��������:<%=rd.getStringByDI("groupInfo","ysrs",0) %>��&nbsp;ʣ������:<%=rd.getStringByDI("groupInfo","syrs",0) %>��
			</td>
		</tr>
		<tr>
			<td align="right">���������</td>
			<td><%=rd.getStringByDI("groupInfo","LEADER_NAME",0) %></td>
			<td align="right">��ӵ绰��</td>
			<td><%=rd.getStringByDI("groupInfo","LEADER_TEL",0) %></td>
		</tr>
		<tr>
			<td align="right">��ע��</td>
			<td colspan="3"> <textarea rows="3" cols="70" readonly="readonly"><%=rd.getStringByDI("groupInfo","COMMENTS",0) %></textarea> </td>
		</tr>
	</table>
</body>
</html>