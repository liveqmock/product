<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
	int pageNo=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	int tRows = rd.getTableRowsCount("TA_ZT_INSURANCEs");
	if(tRows > 0){
		pageNo=Integer.parseInt((String)rd.getString("pageNo"));
	 	pageSize=Integer.parseInt((String)rd.getString("pageSize"));
	 	totalPage=(Integer)rd.getAttr("TA_ZT_INSURANCEs", "pagesCount");
		rowsCount = (Integer)rd.getAttr("TA_ZT_INSURANCEs", "rowsCount");
	}
%>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>保险档案列表</title>
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>

<script type="text/javascript">

function findByLike(){
	document.insuranceFileList.submit();
}

function newInsurance(){

	window.location.href="initInsuranceFile.";
}
function editInsurance(url){

	window.location.href=url;
}
</script>
</head>
<body>
<form name="insuranceFileList" action="initInsuranceFileList.?pageNo=1&pageSize=10"  method="post">
<div id="lable">
  <span>保险档案列表</span>
</div>
<div id="top-select">
  <div class="select-div" onclick="newInsurance();"><span id="add-icon"></span>
	<span class="text">添加保险</span>
  </div>
</div>
<div id="thisSelect-table">
	<table>
		<tr>
			<td rowspan="3" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td align="right">保险名称：</td>
			<td><input type="text" name="BXLBMC" /></td>
			<td><a href="###" onclick="findByLike();"><img alt="搜索" src="<%=request.getContextPath()%>/images/search.gif"/></a></td>
		</tr>
	</table>
</div>

<div id="list-table">
<table class="datas">
	<tr id="first-tr">
        <td>保险名称</td>
		<td>保险成本</td>
		<td>建议价格</td>
		<td>成本方式</td>
        <td >操作</td>
	</tr>
	<%
		
		for(int i = 0; i < tRows; i++){
			String insuranceID = rd.getStringByDI("TA_ZT_INSURANCEs", "ID", i);
	%>
		<tr>
	  		<td><%=rd.getStringByDI("TA_ZT_INSURANCEs", "BXLBMC", i) %></td>
			<td><%=rd.getStringByDI("TA_ZT_INSURANCEs", "BXCB", i) %></td>
			<td><%=rd.getStringByDI("TA_ZT_INSURANCEs", "JYJG", i) %></td>
			<td><%if("1".equals(rd.getStringByDI("TA_ZT_INSURANCEs", "CBFS", i))){out.print("按天计算");}else{out.print("按实际成本计算");} %></td>
			<td>
				<a href="###"onclick="editInsurance('<%=request.getContextPath()%>/baseMng/insuranceFile/initInsuranceFile.?TA_ZT_INSURANCE/ID=<%=insuranceID %>')" >
				<img alt="编辑" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[修改]</a>
				<a href="<%=request.getContextPath()%>/baseMng/insuranceFile/delInsuranceFile.?TA_ZT_INSURANCE/ID=<%=insuranceID %>" 
					onclick="return confirm('此操作将删除与此保险相关所有信息,并且无法恢复,确定删除吗?')">
				<img alt="删除" src="<%=request.getContextPath()%>/images/del-icon.gif"/>&nbsp;[删除]</a>
            </td>
         </tr>	
	<%} %>	
      
</table>
</div>
<div id="page">
	<%String url=request.getContextPath()+"/baseMng/insuranceFile/initInsuranceFileList.?pageSize=10&pageNo=";%>
	<span class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNo,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span class="prev-page" onclick='query("<%=pageNo-1<1?1:pageNo-1 %>","P","<%=(int)Math.min(pageNo,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					第<%=Math.min(pageNo,totalPage)%>/<%=totalPage%> 页，
					共<%=rowsCount %> 条记录，
					每页 <%=pageSize%>条
	</span>
	<span class="next-page" onclick='query("<%=pageNo+1>totalPage?totalPage:pageNo+1 %>","N","<%=(int)Math.min(pageNo,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="last-page" onclick='query("<%=totalPage%>","N","<%=(int)Math.min(pageNo,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="go-to-page" >
		跳转到第：<input type="text" id="gotopage"/> 页
	</span>
	<span class="go-to-page-icon" onclick='go("<%=totalPage%>","<%=(int)Math.min(pageNo,totalPage)%>","<%=url %>")'></span>
</div>
</form>
</body>
</html>
