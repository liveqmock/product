<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
     pageNO=Integer.parseInt((String)rd.getString("pageNO"));
	 pageSize=Integer.parseInt((String)rd.getString("pageSize"));
	 Object objTotalP = rd.getAttr("rsAllDDH", "pagesCount");
	 if(null == objTotalP)
		 totalPage = 0;
	 else
		totalPage=(Integer)objTotalP;
	 Object objRowsC = rd.getAttr("rsAllDDH", "rowsCount");
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

<script type="text/javascript">
  function doSub(ddh,gKey,action,xlid){
	window.location.href="signUpEditIint.?ddh="+ddh+"&groupKey="+gKey+"&TA_ZT_GATHER/ORGID=<%=sd.getString("orgid")%>&TA_ZT_GATHER/LINE_ID="+xlid+"&dmsm/jglx=4&action="+action;
  }
  function postData(){
	document.myForm.submit();
  }
  function queryByState(v){

	window.location.href="signUpOfLines.?pageNO=1&pageSize=10&ddState="+v;
  }

  function cancleOrder(){

	  window.location.href="";
  }
</script>
</head>
<body>
<form action="signUpOfLines.?pageNO=1&pageSize=10" name="myForm" method="post">
<div id="lable">
<%
int ddRows = rd.getTableRowsCount("allOrderState");
%>
<div style="float: left"><span >我的订单</span></div>
<div class="tag" onclick="queryByState('0');"><span class="showPointer" title="查询未确认订单">未确定订单(<%for(int i=0;i<ddRows;i++){if(rd.getStringByDI("allOrderState","dmz",i).equals("0")){ out.print(rd.getStringByDI("allOrderState","c",i)); break;}} %>条)</span></div>
<div class="tag" onclick="queryByState('1');"><span class="showPointer" title="查询已确认订单">已成功订单(<%for(int i=0;i<ddRows;i++){if(rd.getStringByDI("allOrderState","dmz",i).equals("1")){ out.print(rd.getStringByDI("allOrderState","c",i)); break;}} %>条)</span></div>
<div class="tag" onclick="queryByState('2');"><span class="showPointer" title="查询已退团订单">已退团订单(<%for(int i=0;i<ddRows;i++){if(rd.getStringByDI("allOrderState","dmz",i).equals("2")){ out.print(rd.getStringByDI("allOrderState","c",i)); break;}} %>条)</span></div>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
</div>
<div id="thisSelect-table">
	<table>
		<tr>
			<td rowspan="2" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td align="right">发团日期：<input type="text" name="bDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" style="width: 80px;"/>
			</td>
			<td>到<input type="text" name="eDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" style="width: 80px;"/></td>
			<td align="right">&nbsp;&nbsp;线路名称：</td>
			<td><input type="text" name="lineName" /></td>
			<td>&nbsp;&nbsp;团号：</td>
			<td><input type="text" name="groupID" /></td>
		  <td>&nbsp;&nbsp;订 单 号：<input type="text" name="djh" style="width: 80px;"/></td>
		  <td><a href="###" onclick="postData();"><img alt="搜索" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table">	
  <table class="datas" >
	<tr id="first-tr">
	  <td width="13%">订单号/报名人数</td>
	  <td width="23%">线路名称</td>
	  <td width="8">游客</td>
  	  <td width="25%">价格</td>
	  <td width="10%">游客收款</td>
	  <td width="6%">状态</td>
	  <td width="8%">操作</td>
	</tr>
	<%
		int rows = rd.getTableRowsCount("rsAllDDH");
		for(int i=0;i<rows;i++) {
			
			String ddh = rd.getStringByDI("rsAllDDH","ddh",i);
			String groupID = rd.getStringByDI("rsAllDDH","th",i);
			String bDate = rd.getStringByDI("rsAllDDH","begin_date",i);
			String days = rd.getStringByDI("rsAllDDH","days",i);
			String isconfirm = rd.getStringByDI("rsAllDDH","ddconfirm",i);
			String yi_sk = rd.getStringByDI("rsAllDDH","yi_sk",i);
			String yin_sk = rd.getStringByDI("rsAllDDH","yin_sk",i);
			String spare = rd.getStringByDI("rsAllDDH","spare",i);
			String line_name = rd.getStringByDI("rsAllDDH","line_name",i);
			String ysrs = rd.getStringByDI("rsAllDDH","ysrs",i);
			String xlid = rd.getStringByDI("rsAllDDH","line_id",i);
			String visiNm = rd.getStringByDI("rsAllDDH","visitor_nm",i);
			String visiTel = rd.getStringByDI("rsAllDDH","tel",i);
			
	%>
	<tr>
	  <td>
	  <%
	  out.println(ddh);
	  out.println("<br>");
	  %>
	  <a href="queryByDDH.?TA_ZT_VISITOR/M_ID=<%=ddh %>">
	  <%
	  int row2 = rd.getTableRowsCount("rsPriceOfAllDDH");
	  for(int j=0;j<row2;j++){
		  String ddhID = rd.getStringByDI("rsPriceOfAllDDH","id",j);
		  String personCount = rd.getStringByDI("rsPriceOfAllDDH","person_count",j);
		  String priceTpNm = rd.getStringByDI("rsPriceOfAllDDH","dmsm1",j);
		  if(ddhID.equals(ddh) && !"0".equals(personCount)){
	%>
			 <%=priceTpNm %>:(<%=personCount%>人)<br>
			  
	  <%
		  }
	  }
	  %>
	  </a>
	  </td>
	  <td><img alt="热点线路" src="<%=request.getContextPath() %>/images/hot3.gif">&nbsp;
	  	<a href="viewTravelList.?groupId=<%=groupID %>&lineId=<%=xlid %>" id="select-condition" ><%=line_name  %></a><br>
	  	团号：<%=groupID %><br>
	  	发团日期：<%=bDate.substring(0,10) %>(<%=days %>天)
	  </td>
	  <td>
	 	 姓名:<%=visiNm %><br>电话:<%=visiTel %><br>
	  </td>
	  <td>
	 <%
	  for(int j=0;j<row2;j++) {
		  String ddhID = rd.getStringByDI("rsPriceOfAllDDH","id",j);
		  String personCount = rd.getStringByDI("rsPriceOfAllDDH","person_count",j);
		  String pth= rd.getStringByDI("rsPriceOfAllDDH", "price_th", j);
		  String pms= rd.getStringByDI("rsPriceOfAllDDH", "price_ms", j);
		  String th = rd.getStringByDI("rsPriceOfAllDDH","th",j);
		  String ms = rd.getStringByDI("rsPriceOfAllDDH","ms",j);
		  if(ddhID.equals(ddh) && !"0".equals(personCount)){
			 
	  %> 
	  <font color="red"><%=rd.getStringByDI("rsPriceOfAllDDH","dmsm1",j)%></font>:	门市价：<%=pms%>*<%=personCount%>(总价格：<font color="red"><%=ms %></font>) <br><%for(int ii=0;ii<rd.getStringByDI("rsPriceOfAllDDH","dmsm1",j).length();ii++){ %>&nbsp;&nbsp;&nbsp;<%} %>同行价：<%=pth%>*<%=personCount%>(结算价：<font color="red"><%=th %></font>)<br>
	 <%	}
	  }%>
	  </td>
	  <td>
	  	应收款：<font color="red"><%=yin_sk %></font><br>
	  	已收款：<font color="red"><%=yi_sk %></font><br>
	  	待收款：<font color="red"><%=spare %></font><br>
	  </td>
	  <td><%=isconfirm %></td>
	  <td>
	  	<a href="queryForView.?ddh=<%=ddh %>">查看 </a>
<%if(!"2".equals(rd.getStringByDI("rsAllDDH","DMZ",i))){ %>
		<a href="###" onclick="doSub('<%=ddh %>','<%=groupID %>','edit','<%=xlid %>');"> | 修改</a>
<%} %>
<%
String ddState = rd.getStringByDI("rsAllDDH","DD_CONFIRM",0);
if("2".equals(rd.getStringByDI("rsAllDDH","DMZ",i))){
%>
	 <br><a href="printBackMoeny.?ddh=<%=ddh %>">打印退款单</a><br>
<%
}%>
<%if(!"2".equals(rd.getStringByDI("rsAllDDH","DMZ",i))){ %><br>
	
	<!--<a href="###" onclick="return GB_myShow('','viewJourney.?groupid=<%=groupID %>&ddh=<%=ddh %>&DMSM/JTGJ=2');">打印行程单</a><br>
	-->
	<a href="viewTravelList.?groupId=<%=groupID %>&lineId=<%=xlid %>">打印行程单</a><br>
	<!--<a href="###" onclick="return GB_myShow('','viewInform.?groupid=<%=groupID %>&ddh=<%=ddh %>&DMSM/JTGJ=2');">打印出团任务单</a><br>
	<a href="###" onclick="return GB_myShow('','printYkmd.?groupID=<%=groupID %>');">打印游客名单</a><br>
-->
	<a href="OpMoneyInit.?ddh=<%=ddh %>&tid=<%=groupID %>&action=back">退款(取消订单)</a><br>
<%} %>
<%
	if("1".equals(rd.getStringByDI("rsAllDDH","DMZ",i))){ %>
<a href="<%=request.getContextPath() %>/main/sellerMng/viewConfirmRegedit.?groupid=<%=groupID %>&ddh=<%=ddh %>&DMSM/JTGJ=2">
	打印确认单</a><br>
<%} %>
	  </td>
	</tr>
	<%
} %> 
  </table>
</div>	
<div id="page">
	<%String url=request.getContextPath()+"/main/sellerMng/signUpOfLines.?pageSize=10&pageNO=";%>
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