<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
     pageNO=Integer.parseInt((String)rd.getString("pageNO"));
	 pageSize=Integer.parseInt((String)rd.getString("pageSize"));
	 Object objTotalP = rd.getAttr("rsDDList", "pagesCount");
	 if(null == objTotalP)
		 totalPage = 0;
	 else
		totalPage=(Integer)objTotalP;
	 Object objRowsC = rd.getAttr("rsDDList", "rowsCount");
	 if(null == objRowsC)
		 rowsCount = 0;
	 else
		rowsCount = (Integer)objRowsC;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>订单统计</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/common.js"></script>

<script type="text/javascript">
  function doSub(ddh,gKey,action){
	window.location.href="signUpEditIint.?ddh="+ddh+"&groupKey="+gKey+"&TA_ZT_GATHER/GATHER_ID=&dmsm/jglx=4&action="+action;
  }
  function postData(){

	if(jQuery("#orgName").val() != '' && jQuery("#orgid").val() == ''){
		alert("请先选择旅行社名称！");
		return false;
	}  
	document.myForm.submit();
  }
  
 // function query(v){

//		window.location.href="countOrders.?pageNO=1&pageSize=10&ddState="+v;
 // }
</script>
</head>
<body>
<form action="countOrders.?HRORGANIZATION/PARENT_ORGID=0&pageNO=1&pageSize=10" name="myForm" method="post">
<div id="lable">
<%
int ddRows = rd.getTableRowsCount("allOrderState");
%>
<div style="float: left"><span >订单统计</span></div>
</div>
<div id="thisSelect-table">
	<table >
		<tr>
			<td rowspan="2" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td align="right">团起止日期：</td>
			<td><input type="text" name="bDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" style="width: 80px;"/>
			到<input type="text" name="eDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" style="width: 80px;"/></td>
			<td align="right">线路名称：</td>
			<td><input type="text" name="lineName" /></td>
			<td align="right">&nbsp;&nbsp;团号：</td>
			<td><input type="text" name="groupID" /></td>
		    <td align="right">&nbsp;&nbsp;订单号：</td>
		    <td><input type="text" name="ddh" style="width: 80px;"/></td>
		</tr>
		<tr>
		  	<td align="right">&nbsp;&nbsp;代销商名称：</td>
		  	<td>
			  	<select name="orgid" id="orgid">
			  	  <option value="" selected="selected">***请选择***</option>
			  	  <%
			  	  for(int i=0;i<rd.getTableRowsCount("hrOrganizations");i++){
			  	  %>
			  	  <option value="<%=rd.getStringByDI("hrOrganizations","orgid",i) %>"><%=rd.getStringByDI("hrOrganizations","name",i) %></option>
			  	  <%
			  	  }%>
			  	</select>
		  	</td>
		  	<td align="right">&nbsp;&nbsp;门店名称：</td>
		  	<td><input type="text" name="orgName" id="orgName" /> </td>
		  <td><a href="###" onclick="postData();"><img alt="搜索" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="top-select">
	<table >
		<tr>
			<td align="right" width="8%">订单数：</td>
			<td width="8%"><%=rd.getStringByDI("rsAllDDLR","dds",0) %></td>
			<td align="right" width="8%">门市总价：</td>
			<td width="8%"><%=rd.getStringByDI("rsAllDDLR","msj",0) %></td>
			<td align="right" width="8%">结算总价：</td>
			<td width="8%"><%=rd.getStringByDI("rsAllDDLR","thj",0) %></td>
			<td align="right" width="8%">利润：</td>
			<td width="8%"><%=rd.getStringByDI("rsAllDDLR","lr",0) %></td>
			<td align="right" width="8%">已收款：</td>
			<td width="8%"><%=rd.getStringByDI("rsAllDDLR","yisk",0) %></td>
			<td align="right" width="8%">待收款：</td>
			<td width="8%"><%=rd.getStringByDI("rsAllDDLR","spare",0) %></td>
		</tr>
	</table>
</div>
<div id="list-table">	
  <table class="datas" >
	<tr id="first-tr">
	  <td width="15%">订单号/报名人数</td>
	  <td width="15%">代销商</td>
	  <td width="30%">线路名称</td>
  	  <td width="15%">价格</td>
	  <td width="12%">游客收款</td>
	  <td width="8%">状态</td>
	  <td width="5%">操作</td>
	</tr>
	<%
		int rows = rd.getTableRowsCount("rsDDList");
		for(int i=0;i<rows;i++) {
			
			String ddh = rd.getStringByDI("rsDDList","id",i);
			String groupKey = rd.getStringByDI("rsDDList","th",i);
			String bDate = rd.getStringByDI("rsDDList","begin_date",i);
			String days = rd.getStringByDI("rsDDList","day_counts",i);
			String isconfirm = rd.getStringByDI("rsDDList","zt",i);
			String yi_sk = rd.getStringByDI("rsDDList","yisk",i);
			String line_name = rd.getStringByDI("rsDDList","line_name",i);
			String msj = rd.getStringByDI("rsDDList","msj",i);
			String thj = rd.getStringByDI("rsDDList","thj",i);
			String cmpnyName = rd.getStringByDI("rsDDList","cmpny_name",i);
			String spare = rd.getStringByDI("rsDDList","spare",i);
			
	%>
	<tr>
	  <td>
		  <%=ddh %>
		  <a href="queryByDDH.?TA_ZT_VISITOR/M_ID=<%=ddh %>">
		  <%
		  out.println("<br>");
		  int row2 = rd.getTableRowsCount("rsPersonPrice");
		  for(int j=0;j<row2;j++){
			  String ddhID = rd.getStringByDI("rsPersonPrice","id",j);
			  String personCount = rd.getStringByDI("rsPersonPrice","person_count",j);
			  String priceTpNm = rd.getStringByDI("rsPersonPrice","pt",j);
			  if(ddhID.equals(ddh) && !"0".equals(personCount)){
		  %>
				 <%=priceTpNm %>1:(<%=personCount%>个)<br>
				  
		  <%
			  }
		  }
		  %>
	  	  </a>
	  </td>
	  <td><%=cmpnyName %></td>
	  <td><img alt="热点线路" src="<%=request.getContextPath() %>/images/hot3.gif">&nbsp;
	  	<a id="select-condition" href="viewTravelList.?groupId=<%=groupKey %>&lineId="><%=line_name  %></a><br>
	  	团号：<%=groupKey %><br>
	  	<%=bDate.substring(0,10) %>(<%=days %>天)
	  </td>
	  <td align="left">
	  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  	门市价：<font color="red"><%=msj %></font><br>
	  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 	同行价：<font color="red"><%=thj %></font>
	  </td>
	  <td align="left">
	  	&nbsp;&nbsp;&nbsp;
	  	已收款：<font color="red"><%=yi_sk %></font><br>
	  	&nbsp;&nbsp;&nbsp;
	  	 剩&nbsp;&nbsp;&nbsp;余：<font color="red"><%=spare %></font>
	  </td>
	  <td><%=isconfirm %></td>
	  <td>
	    <a href="queryForView.?ddh=<%=ddh %>">查看 </a>
	  </td>
	</tr>
	<%} %> 
  </table>
</div>	
<div id="page">
	<%String url=request.getContextPath()+"/main/sellerMng/countOrders/countOrders.?HRORGANIZATION/PARENT_ORGID=0&pageSize=10&pageNO=";%>
	<span class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
					共<%=rowsCount %> 条记录，
					每页 <%=pageSize%>条
	</span>
	<span class="next-page" onclick='query("<%=pageNO+1>totalPage?totalPage:pageNO+1 %>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="last-page" onclick='query("<%=totalPage%>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="go-to-page" >
		跳转到第：<input type="text" id="gotopage"/> 页
	</span>
	<span class="go-to-page-icon" onclick='go("<%=totalPage%>","<%=(int)Math.min(pageNO,totalPage)%>","<%=url %>")'></span>
</div>

</form>
</body>
</html>