<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
    <%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript">
	function editGroup(){
		document.group_form.action="editGroup.?";
		document.group_form.submit();
		}
</script>
<title></title>
</head>
<body>
<form  name="group_form" method="post">
<div id="lable"><span>���ŵ���</span></div>
<div id="bm-table">
	<table class="datas" width="98%">
		<tr>
			<td colspan="4"><span>��·������Ϣ</span></td>
		</tr>
		<tr>
		  <td colspan="4">�������ڣ�<font color="red"><%=rd.getStringByDI("rsDateOfLinePlan","planoflinedate",0) %></font>
		   </td>
		</tr>
		<tr>
			<td width="10%">��·���ƣ�</td>
			<td width="35%"><font color="red"><%=rd.getStringByDI("rsDateOfLinePlan","line_name",0) %></font></td>
			<td width="10%"> �źű�ţ�</td>
			<td><%=rd.getStringByDI("rsDateOfLinePlan","g_id",0) %></td>
		</tr>
		<tr>
			<td >��������</td>
			<td ><%=rd.getStringByDI("rsDateOfLinePlan","maxpersoncount",0) %> ���ɳ���������<%=rd.getStringByDI("rsDateOfLinePlan","minpersoncount",0) %>����</td>
			<td >����������</td>
			<td ><%=rd.getStringByDI("rsDateOfLinePlan","spare",0) %>��</td>
		</tr>
	</table>
<table class="datas">
	<tr>
		<td colspan="9"><span>������Ϣ</span></td>
	</tr>
	<tr>
		<td align="right">�������ڣ�</td>
		<td >
			<input type="hidden" name="TA_GROUP_BASE/ID" value="<%=rd.getStringByDI("groupInfo","ID",0) %>" />
			<input type="hidden" name="TA_GROUP_BASE/ID[0]@oldValue" value="<%=rd.getStringByDI("groupInfo","ID",0) %>" />
			<%=rd.getStringByDI("groupInfo","BEGIN_DATE",0) %>
		</td>
		<td align="right">�������ڣ�</td>
		<td ><%=rd.getStringByDI("groupInfo","END_DATE",0) %></td>
		<td align="right">������</td>
		<td ><%=rd.getStringByDI("groupInfo","DAYS",0) %></td>
	</tr>
		<tr>
		<td align="right">�ο����ͣ�</td>
		<td >
		 <%if("1".equals(rd.getStringByDI("groupInfo","TOURIST_TYPE",0))) {%>�Ŷ�<%} %>
		 <%if("2".equals(rd.getStringByDI("groupInfo","TOURIST_TYPE",0))||"".equals(rd.getStringByDI("groupInfo","TOURIST_TYPE",0))) {%>ɢ��<%} %>
		</td>
		<td align="right">�Ƿ��Ա�����</td>
		<td >
		 <%if("1".equals(rd.getStringByDI("groupInfo","VEHICLE_TYPE",0))) {%>�� <%} %>
		 <%if("2".equals(rd.getStringByDI("groupInfo","VEHICLE_TYPE",0))||"".equals(rd.getStringByDI("groupInfo","VEHICLE_TYPE",0))) {%>��<%} %>
		</td>
		<td align="right">�ӵ㹺�</td>
		<td ><input type="checkbox" >�ɼӵ�<input type="checkbox">�ɹ���</td>
	</tr>
	<tr>
		<td align="right">ҵ��Ա��</td>
		<td>
		  <input type="hidden" name="TA_GROUP_BASE/USER_NO" value="<%="".equals(rd.getStringByDI("groupInfo","USER_NO",0))?sd.getString("userno"):rd.getStringByDI("groupInfo","USER_NO",0) %>"/>
		  <input type="text" value="<%="".equals(rd.getStringByDI("groupInfo","USER_NO",0))?sd.getString("username"):rd.getStringByDI("groupInfo","USER_NO",0) %>" readonly="readonly"/>
		</td>
		<td align="right">ȫ��������</td>
		<td><input type="text" name="TA_GROUP_BASE/OTHERSIDE_GC" value="<%=rd.getStringByDI("groupInfo","OTHERSIDE_GC",0) %>"/></td>
		<td align="right">���������</td>
		<td><input type="text" name="TA_GROUP_BASE/LEADER_NAME" value="<%=rd.getStringByDI("groupInfo","LEADER_NAME",0) %>"/></td>
	</tr>
	<tr>
		<td align="right">����ֻ���</td>
		<td><input type="text" name="TA_GROUP_BASE/LEADER_TEL" value="<%=rd.getStringByDI("groupInfo","LEADER_TEL",0) %>"/></td>
		<td align="right">����������</td>
		<td><input type="text" name="TA_GROUP_BASE/ADULT_COUNT" value="<%=rd.getStringByDI("pesNum","cr",0) %>"/></td>
		<td align="right">��ͯ������</td>
		<td><input type="text" name="TA_GROUP_BASE/CHILDREN_COUNT" value="<%=rd.getStringByDI("pesNum","rt",0) %>"/></td>
	</tr>
	<tr>
		<td align="right">��ע��</td>
		<td colspan="5"> <textarea rows="3" cols="73" name="TA_GROUP_BASE/COMMENTS"><%=rd.getStringByDI("groupInfo","COMMENTS",0) %></textarea>&nbsp;<font color="red">��100������</font> </td>
	</tr>
</table>
<table class="datas" style="text-align: center;">
	<tr>
		<td colspan="7" align="left" style="color: #006666;font-weight: bold;margin-left: 3px;">�۸��嵥</td>
	</tr>
	<tr id="first-tr" >
		<td width="15%">�۸�����</td>
		<td width="10%">���м�</td>
		<td width="10%">ͬ�н����</td>
		<td >��ͼ�</td>
		<td >��ע</td>
	</tr>
	<%int priRows=rd.getTableRowsCount("linePrice");for(int b=0;b<priRows;b++){ %>
	<tr>
		<td><%=rd.getStringByDI("linePrice","price_type",b) %></td>
		<td>��<%=rd.getStringByDI("linePrice","PRICE_MS",b) %></td>
		<td>��<%=rd.getStringByDI("linePrice","PRICE_TH",b) %></td>
		<td>��<%=rd.getStringByDI("linePrice","PRICE_ZD",b) %></td>
		<td><%=rd.getStringByDI("linePrice","REMARK",b) %></td>
	</tr>
	<%} %>
</table>
</div>
<div align="center" id="last-sub">
	<input type="button" id="button" value="�ύ" onclick="editGroup();"/>
	<input type="button" id="button" value="�� ��" onclick="window.history.go(-1)"/>
</div>
</form>
</body>
</html>