<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>
<%
	String groupPrefix = rd.getStringByDI("TA_GROUPNUMROLEs", "GROUPPREFIX", 0);
	String ywlb = rd.getStringByDI("TA_GROUPNUMROLEs", "ywlb", 0);
	String ywfl = rd.getStringByDI("TA_GROUPNUMROLEs", "ywfl", 0);
	int rows = rd.getTableRowsCount("TA_GROUPNUMROLEs");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryui/jqueryui.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<title><%=rows>0?"�޸�":"����" %>�ź�ǰ׺����</title>
<script type="text/javascript">

	$(function(){
		$("input:submit,input:button").button();
	});
	function addPrefix(){
		
		document.groupPrefixForm.submit();
	}
</script>
</head>
<body>
<form name="groupPrefixForm" method="post" action="<%=rows>0?"upDatePrefix4Group.":"saveGroupPre." %>">
<%
if(rows>0){
%>
<input type="hidden" name="TA_GROUPNUMROLE/orgid[0]@oldValue" value="<%=sd.getString("orgid") %>"/>
<input type="hidden" name="TA_GROUPNUMROLE/ywlb[0]@oldValue" value="<%=ywlb %>"/>
<input type="hidden" name="TA_GROUPNUMROLE/ywfl[0]@oldValue" value="<%=ywfl %>"/>
<%
}else{
%>
<input type="hidden" name="TA_GROUPNUMROLE/orgid" value="<%=sd.getString("orgid") %>"/>
<%
} %>
<div id="lable"><span><%=rows>0?"�޸�":"����" %>�ź�ǰ׺����   ��<font color="red">*</font>��Ϊ������</span></div>
	<table class="add-table" >
		<tr>
			<td align="right" width="40%">�źŹ���ǰ׺��</td>
			<td>
				<input type="text" name="TA_GROUPNUMROLE/GROUPPREFIX" maxlength="9" 
						value="<%=groupPrefix %>"></input><span>*</span> 
			</td>
		</tr>
		<tr>
			<td align="right" width="40%">����˵����</td>
			<td>�ź�ǰ׺��ϵͳ���źŵ�ǰ�벿�֣��ɴ�Сд��ĸ��������ɣ���ʶ��ҵ��ݼ��ɣ�����󲻿ɳ���9���ַ�</td>
		</tr>
		<tr>
			<td align="right" >ҵ����ࣺ</td>
			<td>
				<select name="TA_GROUPNUMROLE/YWfl">
					<option value="z" <%="z".equals(ywfl)?"selected=\"selected\"":"" %>>����</option>
					<option value="d" <%="d".equals(ywfl)?"selected=\"selected\"":"" %>>�ؽ�</option>
				</select> ע��ҵ�������Ҫ�����������ɵ��������źŻ��ǵؽ��ź�
			</td>
		</tr>
		<tr>
			<td align="right" >ҵ�����</td>
			<td>
				<select name="TA_GROUPNUMROLE/YWLB">
					<option value="s" <%="s".equals(ywlb)?"selected=\"selected\"":"" %>>ɢ��</option>
					<option value="t" <%="t".equals(ywlb)?"selected=\"selected\"":"" %>>�Ŷ�</option>
				</select> ע��ҵ�������Ҫ���������Ŷӻ�ɢ��
			</td>
		</tr>
	</table>
  <div>
	<table class="datas">
	  <tr>
		<td align="center">
		  <input type="button" value="��    ��" onclick="addPrefix();"/>&nbsp;&nbsp;
		  <input type="button" value="��    ��" onclick="window.history.go(-1)"/>
		</td>
	  </tr>
	</table>
  </div>	
</form>	
</body>
</html>