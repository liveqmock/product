<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
    <%@include file="/common.jsp" %>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("rsLineBase")>0){
     pageNO=Integer.parseInt((String)rd.getString("pageNO"));
	 pageSize=Integer.parseInt((String)rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("rsLineBase", "pagesCount");
		rowsCount = (Integer)rd.getAttr("rsLineBase", "rowsCount");
	}
	String days = rd.getString("days");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>客人信息</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/common.js"></script>

</head>
<body>
<form action="listAllLines.?pageNO=1&pageSize=10" name="myForm" method="post">


<div id="list-table">
  <table class="datas" >
	<tr id="first-tr">
	  <td width="10%">姓名</td>
	  <td width="5%">性别</td>
  	  <td width="5%">类型</td>
	  <td width="20%">证件号码</td>
	  <td width="10%">证件有效期</td>
	  <td width="10%">签证地</td>
	  <td width="15%">出生地</td>
	  <td width="15%">电话</td>
	  <td width="8%">需要保险</td>
	</tr>
	<%
		int rows = rd.getTableRowsCount("TA_ZT_VISITORs");
		for(int i=0;i<rows;i++) {
			
			String VISITOR_NM = rd.getStringByDI("TA_ZT_VISITORs","VISITOR_NM",i);
			String SEX = rd.getStringByDI("TA_ZT_VISITORs","SEX",i);
			String ISCHILD = rd.getStringByDI("TA_ZT_VISITORs","ISCHILD",i);
			String LICENSE_NO = rd.getStringByDI("TA_ZT_VISITORs","LICENSE_NO",i);
			String TEL = rd.getStringByDI("TA_ZT_VISITORs","TEL",i);
			String ISINSURANCE = rd.getStringByDI("TA_ZT_VISITORs","ISINSURANCE",i);
			String ZJYXQ = rd.getStringByDI("TA_ZT_VISITORs","ZJYXQ",i);
			String PASSPORTPS = rd.getStringByDI("TA_ZT_VISITORs","PASSPORTPS",i);
			String BORNSITE = rd.getStringByDI("TA_ZT_VISITORs","BORNSITE",i);
			
	%>
	<tr>
	  <td><%=VISITOR_NM %></td>
	  <td><%if("1".equals(SEX)) out.print("男"); else if("2".equals(SEX)) out.print("女"); else out.print("不详"); %></td>
	  <td><%if("1".equals(ISCHILD)) out.print("儿童"); else out.print("成人"); %></td>
	  <td><%=LICENSE_NO %></td>
	  <td><%=ZJYXQ %></td>
	  <td><%=PASSPORTPS %></td>
	  <td><%=BORNSITE %></td>
	  <td><%=TEL %></td>
	  <td><%if("0".equals(ISINSURANCE)) out.print("否"); else out.print("是"); %></td>
	</tr>
	<%} %> 
	<tr>
		<td colspan="9"><input type="button" id="button" value="返 回" onclick="window.history.go(-1)"/></td>
	</tr>
  </table>
</div>
</form>
</body>
</html>