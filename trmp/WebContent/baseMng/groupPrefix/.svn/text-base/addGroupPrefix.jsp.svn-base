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
<title><%=rows>0?"修改":"新增" %>团号前缀规则</title>
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
<div id="lable"><span><%=rows>0?"修改":"新增" %>团号前缀规则   带<font color="red">*</font>号为必填项</span></div>
	<table class="add-table" >
		<tr>
			<td align="right" width="40%">团号规则前缀：</td>
			<td>
				<input type="text" name="TA_GROUPNUMROLE/GROUPPREFIX" maxlength="9" 
						value="<%=groupPrefix %>"></input><span>*</span> 
			</td>
		</tr>
		<tr>
			<td align="right" width="40%">规则说明：</td>
			<td>团号前缀是系统中团号的前半部分，由大小写字母和数字组成（标识企业身份即可），最大不可超过9个字符</td>
		</tr>
		<tr>
			<td align="right" >业务分类：</td>
			<td>
				<select name="TA_GROUPNUMROLE/YWfl">
					<option value="z" <%="z".equals(ywfl)?"selected=\"selected\"":"" %>>组团</option>
					<option value="d" <%="d".equals(ywfl)?"selected=\"selected\"":"" %>>地接</option>
				</select> 注：业务分类主要用于区分生成的是组团团号还是地接团号
			</td>
		</tr>
		<tr>
			<td align="right" >业务类别：</td>
			<td>
				<select name="TA_GROUPNUMROLE/YWLB">
					<option value="s" <%="s".equals(ywlb)?"selected=\"selected\"":"" %>>散客</option>
					<option value="t" <%="t".equals(ywlb)?"selected=\"selected\"":"" %>>团队</option>
				</select> 注：业务类别主要用于区别团队或散客
			</td>
		</tr>
	</table>
  <div>
	<table class="datas">
	  <tr>
		<td align="center">
		  <input type="button" value="提    交" onclick="addPrefix();"/>&nbsp;&nbsp;
		  <input type="button" value="返    回" onclick="window.history.go(-1)"/>
		</td>
	  </tr>
	</table>
  </div>	
</form>	
</body>
</html>