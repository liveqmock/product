<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
 <%@include file="/common.jsp" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript">
function del(url){
	
	if(confirm('�˲������޷��ָ�,ȷ��ɾ��������?')){
		window.location.href=url;
	}
	
}

function newGroupPrefix(){
	
	window.location.href="<%=request.getContextPath() %>/baseMng/groupPrefix/addGroupPrefix.jsp";
}

function edit(url){
	
	window.location.href=url;
}
</script>
<title></title>
</head>
<body>
<form  name="groupPrefix" method="post">
<div id="lable">
  <span>�ź�ǰ׺�����б�</span>
</div>
<div id="top-select">
  <div class="select-div" onclick="newGroupPrefix();">
  	<span id="add-icon"></span>
  	<span class="text">���</span>
  </div>
</div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td>�ź�ǰ׺</td>
			<td>ҵ�����</td>
			<td>ҵ�����</td>
			<td>����</td>
		</tr>
		<%	
			int rows=rd.getTableRowsCount("TA_GROUPNUMROLEs");
			for(int h=0;h<rows;h++){
				String ywlb = rd.getStringByDI("TA_GROUPNUMROLEs","YWLB",h);
				if(!"".equals(ywlb) && ywlb.trim().equals("s"))
					ywlb = "ɢ��";
				else if(!"".equals(ywlb) && ywlb.trim().equals("t"))
					ywlb = "�Ŷ�";
				String ywfl = rd.getStringByDI("TA_GROUPNUMROLEs","ywfl",h);
				if(!"".equals(ywfl) && ywfl.trim().equals("d"))
					ywfl = "�ؽ�";
				else if(!"".equals(ywfl) && ywfl.trim().equals("z"))
					ywfl = "����";
		%>
		<tr>
			<td><%=rd.getStringByDI("TA_GROUPNUMROLEs","GROUPPREFIX",h)%></td>
			<td><%=ywfl %></td>
			<td><%=ywlb %></td>
			<td>
				<a href="###" onclick="edit('<%=request.getContextPath()%>/baseMng/groupPrefix/initPrefix4Update.?TA_GROUPNUMROLE/GROUPPREFIX=<%=rd.getStringByDI("TA_GROUPNUMROLEs","GROUPPREFIX",h) %>&TA_GROUPNUMROLE/orgid=<%=sd.getString("orgid") %>');">
					<img alt="�༭" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[�޸�]
				</a>-
				<a href="###" onclick="del('<%=request.getContextPath() %>/baseMng/groupPrefix/deletePrefix4Group.?TA_GROUPNUMROLE/GROUPPREFIX=<%=rd.getStringByDI("TA_GROUPNUMROLEs","GROUPPREFIX",h) %>&TA_GROUPNUMROLE/orgid=<%=sd.getString("orgid") %>');">
					<img alt="ɾ��" src="<%=request.getContextPath()%>/images/del-icon.gif">&nbsp;[ɾ��]
				</a>
			</td>
		</tr>
		<%} %>
	</table>
</div>
</form>
</body>
</html>