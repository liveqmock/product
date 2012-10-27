<%@ page contentType="text/html; charset=GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<title>查看导游计划</title>
</head>

<body>
<form name="p_guide_form" method="post">
<div id="lable"><span >查看导游计划</span></div>
<div id="bm-table">
<div id="guideDiv">
	<table class="datas" width="98%"  style="text-align:center">
  <tr id="first-tr">
	<td>导游</td>
	<td>导游实际领款</td>
	<td>导服费</td>
  </tr>
  <%
  	for(int i=0;i<rd.getTableRowsCount("selectGuideInfo");i++){
  		String DYXM=rd.getString("selectGuideInfo","DYXM",i);
  		String DYid=rd.getString("selectGuideInfo","DYid",i);
  		String DYLK=rd.getString("selectGuideInfo","DYLK",i);
  		String DFF=rd.getString("selectGuideInfo","DFF",i);
  %>
  <tr>
  	<td>
			<%
				for(int j=0;j<rd.getTableRowsCount("SelectAllGuide");j++){
					String userNO = rd.getStringByDI("SelectAllGuide","USERno",j);
			%>
			<%if(DYid.equals(userNO)){ out.print(rd.getStringByDI("SelectAllGuide","USERNAME",j));}%>
			<%} %>
	</td>
	<td><%=DYLK %></td>
	<td><%=DFF %></td>
  </tr>
  <%} %>
</table>

</div>
</div>
<input type="hidden" value="1" name="userno" />
</form>
</body>
</html>