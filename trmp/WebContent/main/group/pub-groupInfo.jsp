<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
 <%@include file="/common.jsp" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<title>团基本信息</title>
</head>
<body>
<table class="datas" width="95%">
		<tr>
			<td colspan="4" ><span>团-<%=rd.getStringByDI("groupInfo","g_id",0) %>&nbsp;基本信息</span></td>
		</tr>
		<tr>
		  <td colspan="2" >发团日期：<font color="red"><%=rd.getStringByDI("groupInfo","BEGIN_DATE",0) %></font>&nbsp;&nbsp;
		   </td>
		   <td colspan="2">返团日期：<font color="red"><%=rd.getStringByDI("groupInfo","END_DATE",0) %></font></td>
		</tr>
		<tr>
			<td width="10%" align="right">线路名称：</td>
			<td width="35%" ><font color="red"><%=rd.getStringByDI("groupInfo","line_name",0) %></font></td>
			<td width="10%" align="right"> 团号编号：</td>
			<td >
				<input type="hidden" value="<%=rd.getStringByDI("groupInfo","id",0) %>" name="group_fkId"/>
				<%=rd.getStringByDI("groupInfo","g_id",0) %>
			</td>
		</tr>
		<tr>
			<td align="right">人数：</td>
			<td >儿童:<%=rd.getStringByDI("groupInfo","children_count",0) %>人&nbsp;成人:<%=rd.getStringByDI("groupInfo","adult_count",0) %>人</td>
			<td align="right">人数：</td>
			<td >
			最大人数:<%=rd.getStringByDI("groupInfo","zdrs",0) %>人&nbsp;可成团人数:<%=rd.getStringByDI("groupInfo","kctrs",0) %>人
			已收人数:<%=rd.getStringByDI("groupInfo","ysrs",0) %>人&nbsp;剩余人数:<%=rd.getStringByDI("groupInfo","syrs",0) %>人
			</td>
		</tr>
		<tr>
			<td align="right">领队姓名：</td>
			<td><%=rd.getStringByDI("groupInfo","LEADER_NAME",0) %></td>
			<td align="right">领队电话：</td>
			<td><%=rd.getStringByDI("groupInfo","LEADER_TEL",0) %></td>
		</tr>
		<tr>
			<td align="right">备注：</td>
			<td colspan="3"> <textarea rows="3" cols="70" readonly="readonly"><%=rd.getStringByDI("groupInfo","COMMENTS",0) %></textarea> </td>
		</tr>
	</table>
</body>
</html>