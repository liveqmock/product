<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>

<script type="text/javascript">
function openPeople(iName,ddh){
	if( $("#"+iName+" .groupTd div").size()==0){
		 var obj=$.ajax({
		 	 url:"showPeopleList.?ddh="+ddh,
			 async:true,
			 dataType:"HTML",
			 cache:false,
			 success:function(){
				 $("#"+iName).attr("style","");
		  		 $("#"+iName+" .groupTd").html(obj.responseText);
			 }
		});
	}else{
		if($("#"+iName).is(":hidden")){
			 $("#"+iName).css({display:"block"});
		}else{
			 $("#"+iName).css({display:"none"});
		}
	}
}
</script>
</head>
<body>
<table class="datas">
<tr id="first-tr">
	<td align="center" width="10%">订单号</td>
	<td align="center" width="20%">线路名称</td>
	<td align="center" width="25%">价格</td>
	<td align="center" width="6%">应收款</td>
	<td align="center" width="6%">未收款</td>
	<td align="center" width="15%">订单来源</td>
</tr>
<%
int dRows = rd.getTableRowsCount("GroupDetailList");
for(int i = 0; i < dRows; i++){
	String ddh = rd.getStringByDI("GroupDetailList", "id", i);
%>

<tr class="groupList<%=i%>"  onclick="openPeople('groupList<%=i%>','<%=ddh%>');" style="color: green;">
	<td  align="center" ><%=rd.getStringByDI("GroupDetailList", "id", i) %></td>
	<td  align="center" ><%=rd.getStringByDI("GroupDetailList", "xlmc", i) %></td>
	<td  align="center" >
	<%
	int row2 = rd.getTableRowsCount("GroupPriceList");
	  for(int j=0;j<row2;j++) {
		  String ddhID = rd.getStringByDI("GroupPriceList","id",j);
		  String personCount = rd.getStringByDI("GroupPriceList","person_count",j);
		  String pth= rd.getStringByDI("GroupPriceList", "price_th", j);
		  String pms= rd.getStringByDI("GroupPriceList", "price_ms", j);
		  String th = rd.getStringByDI("GroupPriceList","th",j);
		  String ms = rd.getStringByDI("GroupPriceList","ms",j);
		  if(ddhID.equals(ddh) && !"0".equals(personCount)){
			 
	  %> 
	  <font color="red"><%=rd.getStringByDI("GroupPriceList","dmsm1",j)%></font>:	门市价：<%=pms%>*<%=personCount%>(总价格：<font color="red"><%=ms %></font>) <br><%for(int ii=0;ii<rd.getStringByDI("GroupPriceList","dmsm1",j).length();ii++){ %>&nbsp;<%} %>同行价：<%=pth%>*<%=personCount%>(结算价：<font color="red"><%=th %></font>)<br>
	 <%	}
	  }%>
	</td>
	<td  align="center" ><%=rd.getStringByDI("GroupDetailList", "yin_sk", i) %></td>
	<td  align="center" ><%=rd.getStringByDI("GroupDetailList", "wsk", i) %></td>
	<td  align="center" ><%=rd.getStringByDI("GroupDetailList", "cmpny_name", i) %></td>
</tr>
<tr id="groupList<%=i%>" style="display: none;">
<td class="groupTd" colspan="6">
</td>
</tr>
<%} %>
</table>
</body>
</html>