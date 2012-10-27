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
<title>前台收客-线路列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/common.js"></script>

<script type="text/javascript">
jQuery(document).ready(function() {
	jQuery(".tag").mouseover(function(){
		jQuery(this).css({background:"#A5E0DC",cursor:"pointer"});
		}).mouseout(function(){
			jQuery(this).css({background:''});
			});
	});

function postData() {
	document.myForm.submit();
}

function showLine(lineId,groupId) {
	var url = "viewTravelList.?groupId="+groupId+"&lineId="+lineId;
	GB_myShow1('线路行程明细',url);
}
</script>
</head>
<body  >
<form action="listAllLines.?pageNO=1&pageSize=10&action=<%=rd.getString("action") %>" name="myForm" method="post">
<div id="lable">
<div style="float: left"><span >线路报名</span></div>
</div>
<div id="thisSelect-table">
	<table>
		
		<tr>
			
			<td rowspan="1" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td align="right">
			游玩天数：
			</td>
			<td colspan="4">
			<a href="<%=request.getContextPath()%>/main/sellerMng/listAllLines.?pageNO=1&pageSize=10&action=<%=rd.getString("action") %>">全部</a>/
			<a href="<%=request.getContextPath()%>/main/sellerMng/listAllLines.?pageNO=1&pageSize=10&days=1&action=<%=rd.getString("action") %>">一日游</a>/
			<a href="<%=request.getContextPath()%>/main/sellerMng/listAllLines.?pageNO=1&pageSize=10&days=2&action=<%=rd.getString("action") %>">二日游</a>/
			<a href="<%=request.getContextPath()%>/main/sellerMng/listAllLines.?pageNO=1&pageSize=10&days=3&action=<%=rd.getString("action") %>">三日游</a>/
			<a href="<%=request.getContextPath()%>/main/sellerMng/listAllLines.?pageNO=1&pageSize=10&days=4&action=<%=rd.getString("action") %>">四日游</a>/
			<a href="<%=request.getContextPath()%>/main/sellerMng/listAllLines.?pageNO=1&pageSize=10&days=5&action=<%=rd.getString("action") %>">五日游</a>
			</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td align="right">发团日期：</td>
			<td><input type="text" name="bDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" style="width: 80px;"/></td>
			<td align="right">线路名称：</td>
			<td><input type="text" name="lineName" /></td>
			<td><a href="###" onclick="postData();"><img alt="搜索" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>

	</table>
</div>
<div id="list-table" >	
  <table class="datas" >
	<tr id="first-tr">
	  <td >团号</td>
	  <td >供应商名称</td>
	  <td >线路名称</td>
  	  <td >最近出团日期</td>
	  <td >出团人数详细</td>
	  <td >门市价/同行结算价</td>
	  <td >团状态</td>
	  <td >操作</td>
	</tr>
	<%
		int rows = rd.getTableRowsCount("rsLineBase");
		for(int i=0;i<rows;i++) {
			
			String groupid = rd.getStringByDI("rsLineBase","id",i);
			String line_id = rd.getStringByDI("rsLineBase","line_id",i);
			String cmpny_name = rd.getStringByDI("rsLineBase","cmpny_name",i);
			String line_name = rd.getStringByDI("rsLineBase","line_name",i);
			String planoflinedate = rd.getStringByDI("rsLineBase","planoflinedate",i);
			if(!"".equals(planoflinedate))
			{
				planoflinedate = planoflinedate.substring(0,10);
			}
			String maxpersoncount = rd.getStringByDI("rsLineBase","maxpersoncount",i);
			String spare = rd.getStringByDI("rsLineBase","spare",i);
			String FTZT = rd.getStringByDI("rsLineBase","FTZT",i);
	%>
	<tr>
	  <td><%=groupid %></td>
	  <td><%=cmpny_name %></td>
	  <td><img alt="热点线路" src="<%=request.getContextPath()%>/images/hot3.gif">&nbsp;
	  	<a href="viewTravelList.?groupId=<%=groupid%>&lineId=<%=line_id %>" title="<%=line_name %>"><%=line_name.length()<=20?line_name:line_name.substring(0,20)+"..."  %></a>
	  </td>
	  <td><%=planoflinedate %></td>
	  <td>	总数：<%=maxpersoncount %> <br>
	  		剩余：<%=spare %></td>
	  <td>
		<%
            int rows2 = rd.getTableRowsCount("rsLinePrice");
            for(int j=0;j<rows2;j++){
            	String line_id2 = rd.getStringByDI("rsLinePrice","line_id",j);
            	String planOfLineDate = rd.getStringByDI("rsLinePrice","planoflinedate",j);
            	planOfLineDate = planOfLineDate.substring(0,10);
            	if(line_id.equals(line_id2) && planoflinedate.equals(planOfLineDate)){

                	String msj = rd.getStringByDI("rsLinePrice","msj",j);
                	String PRICE_MS = rd.getStringByDI("rsLinePrice","PRICE_MS",j);
                	String thj = rd.getStringByDI("rsLinePrice","thj",j);
                	String price_th = rd.getStringByDI("rsLinePrice","price_th",j);
            %>
            <%=msj %>：<font color="red"><%out.println(PRICE_MS);%></font>元/
            <%=thj %>：<font color="red"><%out.println(price_th);%></font>元<%out.println("<br>"); %>
            <%	}
           	}%>
	  </td>
	  <td><%if("1".equals(FTZT)){ %>正常<%}else{ %>已封团<% } %></td>
	  <td>
	 	<%if("baoming".equals(rd.getString("action"))){ %>
	  	  <a href="<%=request.getContextPath()%>/main/sellerMng/queryByLineID.?lineID=<%=line_id %>&spareDate=<%=planoflinedate %>" >
	  	       <img alt="热点线路" src="<%=request.getContextPath()%>/images/baoming1.gif">&nbsp;[报名]
	  	  </a>
	 	<%} %>
	  </td>
	</tr>
	<%} %> 
  </table>
</div>	
<div id="page">
	<%String url=request.getContextPath()+"/main/sellerMng/listAllLines.?days="+days+"&action="+rd.getString("action")+"&pageSize=10&pageNO=";%>
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