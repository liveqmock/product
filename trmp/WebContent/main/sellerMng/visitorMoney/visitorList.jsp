<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
     pageNO=Integer.parseInt((String)rd.getString("pageNO"));
	 pageSize=Integer.parseInt((String)rd.getString("pageSize"));
	 Object objTotalP = rd.getAttr("rsVisitorMoney", "pagesCount");
	 if(null == objTotalP)
		 totalPage = 0;
	 else
		totalPage=(Integer)objTotalP;
	 Object objRowsC = rd.getAttr("rsVisitorMoney", "rowsCount");
	 if(null == objRowsC)
		 rowsCount = 0;
	 else
		rowsCount = (Integer)objRowsC;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>我的订单</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/common.js"></script>

<script type="text/javascript"> 
function close() { 
parent.parent.location.reload(); 
parent.parent.GB_hide(); 
} 
</script> 





<script type="text/javascript">
  function doSub(ddh,gKey,action){
	window.location.href="listVisitorMoney.?ddh="+ddh+"&groupKey="+gKey+"&TA_GATHER/GATHER_ID=&dmsm/jglx=4";
  }
  function postData(){
	document.myForm.submit();
  }
  
  //function query(v){

	//	window.location.href="listVisitorMoney.?pageNO=1&pageSize=10&ddState="+v;
  //}
</script>
</head>
<body>
<form action="listVisitorMoney.?pageNO=1&pageSize=10" name="myForm" method="post">
<div id="lable">
<div style="float: left"><span >游客收款</span></div>
</div>
<div id="thisSelect-table">
	<table >
		<tr>
			<td rowspan="2" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td align="right">发团日期：<input type="text" name="bDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" style="width: 80px;"/>
			</td>
			<td>到<input type="text" name="eDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" style="width: 80px;"/></td>
			<td align="right">&nbsp;&nbsp;线路名称：</td>
			<td><input type="text" name="lineName" /></td>
		  <td>&nbsp;&nbsp;订 单 号：<input type="text" name="ddh" style="width: 80px;"/></td>
		  <td><a href="###" onclick="postData();"><img alt="搜索" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table">	
  <table class="datas" >
	<tr id="first-tr">
	  <td width="13%">订单号</td>
	  <td width="32%">线路名称</td>
	  <td width="15%">游客</td>
  	  <td width="10%">价格</td>
	  <td width="10%">游客收款</td>
	  <td width="7%">状态</td>
	  <td width="10%">操作</td>
	</tr>
	<%
		int rows = rd.getTableRowsCount("rsVisitorMoney");
		for(int i=0;i<rows;i++) {
			
			String ddh = rd.getStringByDI("rsVisitorMoney","ddh",i);
			String groupKey = rd.getStringByDI("rsVisitorMoney","groupKey",i);
			String bDate = rd.getStringByDI("rsVisitorMoney","begin_date",i);
			String days = rd.getStringByDI("rsVisitorMoney","days",i);
			String ddConfirm = rd.getStringByDI("rsVisitorMoney","dd_confirm",i);
			String yi_sk = rd.getStringByDI("rsVisitorMoney","yi_sk",i);
			String yin_sk = rd.getStringByDI("rsVisitorMoney","yin_sk",i);
			String spare = rd.getStringByDI("rsVisitorMoney","spare",i);
			String line_name = rd.getStringByDI("rsVisitorMoney","line_name",i);
			String ysrs = rd.getStringByDI("rsVisitorMoney","ysrs",i);
			String visiNm = rd.getStringByDI("rsVisitorMoney","visitor_nm",i);
			String visiTel = rd.getStringByDI("rsVisitorMoney","tel",i);
			
			String jsj = rd.getStringByDI("rsVisitorMoney","jsj",i);
			
	%>
	<tr>
	  <td>
	  <%=ddh %>
	  </td>
	  <td><img alt="热点线路" src="<%=request.getContextPath() %>/images/hot3.gif">&nbsp;
	  	<a id="select-condition" href="viewTravelList.?groupId=<%=groupKey %>&lineId="><%=line_name  %></a><br>
	  	团号：<%=groupKey %><br>
	  	发团日期：<%=bDate.substring(0,10) %>(<%=days %>天)
	  </td>
	  <td>
	    姓名:<%=visiNm %>/电话:<%=visiTel %><br>
	<a href="queryByDDH.?TA_ZT_VISITOR/M_ID=<%=ddh %>">
	    人数： <%
	  int row2 = rd.getTableRowsCount("rsPersonPrice");
	  for(int j=0;j<row2;j++) {
		  String ddhID = rd.getStringByDI("rsPersonPrice","id",j);
		  String personCount = rd.getStringByDI("rsPersonPrice","person_count",j);
		  String priceType = rd.getStringByDI("rsPersonPrice","pt",j);
		  if(ddhID.equals(ddh) && !"0".equals(personCount)){
			 
	  %> 
	  	<%=priceType %>:<%=personCount %>人<br>
	 <%	}
	  }%></a>
	  </td>
	  <td>
	  	总价格：<font color="red"><%=yin_sk %></font><br>
	  	结算价：<font color="red"><%=jsj %></font><br>
	  </td>
	  <td>
	  	应收款：<font color="red"><%=yin_sk %></font><br>
	  	已收款：<font color="red"><%=yi_sk %></font><br>
	  	待收款：<font color="red"><%=spare %></font><br>
	  </td>
	  <td>
<%
if("0".equals(ddConfirm))out.print("待确认");
if("1".equals(ddConfirm))out.print("已确认");
if("2".equals(ddConfirm))out.print("已取消");
%>
	  </td>
	  <td>
	  <a href="queryForView.?ddh=<%=ddh %>&tid=<%=groupKey %>">查看 </a> 
	<%
	if(!spare.equals("0")){
	%>
	  <br>
	  <a href="OpMoneyInit.?ddh=<%=ddh %>&tid=<%=groupKey %>&action=add">收款</a>
	<%
	}
	if(!"2".equals(ddConfirm)){
	%>
	  <br>
	  <a href="OpMoneyInit.?ddh=<%=ddh %>&tid=<%=groupKey %>&action=back">退款(取消订单)</a><br>
	<%
	}%>
	  </td>
	</tr>
	<%} %> 
  </table>
</div>	
<div id="page">
	<%String url=request.getContextPath()+"/main/sellerMng/listVisitorMoney.?pageSize=10&pageNO=";%>
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