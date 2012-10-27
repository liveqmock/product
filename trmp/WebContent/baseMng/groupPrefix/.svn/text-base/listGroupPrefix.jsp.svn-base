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
	
	if(confirm('此操作将无法恢复,确认删除操作吗?')){
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
  <span>团号前缀规则列表</span>
</div>
<div id="top-select">
  <div class="select-div" onclick="newGroupPrefix();">
  	<span id="add-icon"></span>
  	<span class="text">添加</span>
  </div>
</div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td>团号前缀</td>
			<td>业务分类</td>
			<td>业务类别</td>
			<td>操作</td>
		</tr>
		<%	
			int rows=rd.getTableRowsCount("TA_GROUPNUMROLEs");
			for(int h=0;h<rows;h++){
				String ywlb = rd.getStringByDI("TA_GROUPNUMROLEs","YWLB",h);
				if(!"".equals(ywlb) && ywlb.trim().equals("s"))
					ywlb = "散客";
				else if(!"".equals(ywlb) && ywlb.trim().equals("t"))
					ywlb = "团队";
				String ywfl = rd.getStringByDI("TA_GROUPNUMROLEs","ywfl",h);
				if(!"".equals(ywfl) && ywfl.trim().equals("d"))
					ywfl = "地接";
				else if(!"".equals(ywfl) && ywfl.trim().equals("z"))
					ywfl = "组团";
		%>
		<tr>
			<td><%=rd.getStringByDI("TA_GROUPNUMROLEs","GROUPPREFIX",h)%></td>
			<td><%=ywfl %></td>
			<td><%=ywlb %></td>
			<td>
				<a href="###" onclick="edit('<%=request.getContextPath()%>/baseMng/groupPrefix/initPrefix4Update.?TA_GROUPNUMROLE/GROUPPREFIX=<%=rd.getStringByDI("TA_GROUPNUMROLEs","GROUPPREFIX",h) %>&TA_GROUPNUMROLE/orgid=<%=sd.getString("orgid") %>');">
					<img alt="编辑" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[修改]
				</a>-
				<a href="###" onclick="del('<%=request.getContextPath() %>/baseMng/groupPrefix/deletePrefix4Group.?TA_GROUPNUMROLE/GROUPPREFIX=<%=rd.getStringByDI("TA_GROUPNUMROLEs","GROUPPREFIX",h) %>&TA_GROUPNUMROLE/orgid=<%=sd.getString("orgid") %>');">
					<img alt="删除" src="<%=request.getContextPath()%>/images/del-icon.gif">&nbsp;[删除]
				</a>
			</td>
		</tr>
		<%} %>
	</table>
</div>
</form>
</body>
</html>