<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
    
<%@include file="/common.jsp"%>
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

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Random"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>线路信息管理</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>

<script type="text/javascript">
	function findByLike(){
		var beginDate = document.getElementById("beginDate").value;
		var lineName = document.getElementById("lineName").value;
		window.location.href="listLineByCreater.?lineName="+lineName+"&beginDate="+beginDate+"&pageNO=1&pageSize=10";
	}

	function openUrl(url){
		window.location.href=url;
	}
	function findBylike(){
		document.myForm.action="line_findByLike.?";
		document.myForm.submit();
		}
	function checkMsg(){
		<%if(!"".equals(rd.getString("msg"))){%>
		alert("此条线路已有游客报名,无法删除");
		<%}%>
	}

	
</script>
</head>

<body onkeydown="if(event.keyCode==13)  {event.keyCode=9};"  onload="checkMsg()">
<form name="myForm" method="post">
<div id="ex3a" class="jqmDialog">
	<div class="jqmdTL"><div class="jqmdTR"><div class="jqmdTC jqDrag">按条件查询</div></div></div>
	<div class="jqmdBL"><div class="jqmdBR"><div class="jqmdBC">
	
	<div class="jqmdMSG">
	  线路名称：<input type="text" name="lineName" class="text_input" id="lineName"/>&nbsp;<br>
	 发团日期：<input type="text" name="beginDate" class="text_input" id="beginDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/>&nbsp;
		  <input type="button" value="查询" class="bnt" onClick="findByLike();"/>
	</div>
	
	</div></div></div>
	<input type="image" src="<%=request.getContextPath() %>/images/if-select-img/close.gif" class="jqmdX jqmClose" />
</div>
<div id="lable" ><b>线路信息管理</b></div>
<div id="top-select">
<div class="select-div" >
<span class="text" id="select-condition">查询条件</span>
</div>
<div class="select-div" onclick="openUrl('<%=request.getContextPath()%>/main/lineMng/initLineInfo.?DMSM/JGLX=4&dmsm/fbjh=1&dmsm/jtgj=2&dmsm/xlzt=5&dmsm/xllx=3&xzqh/layer=1&userno=<%=sd.getString("userNo") %>&TA_ZT_GATHER_HIS/GATHER_ID=&TA_ZT_GATHER_HIS/ORGID=<%=sd.getString("orgid") %>&TA_ZT_INSURANCE/ID=&random=<%=Math.random() %>&TA_ZT_INSURANCE/ORGID=<%=sd.getString("orgid") %>')">
  <span id="add-icon"></span> 
  <span class="text">添加</span></div>
</div>

<div id="list-table">
<table class="datas">
	<tr id="first-tr">
		<td >组团社名称</td>
        <td >线路名称</td>
        <td >最近发团日期</td>
        <td >发团人数详细</td>
        <td >门市价/同行结算价</td>
        <td >操作</td>
	</tr>
	<%
		int rows = rd.getTableRowsCount("rsLineBase");
		for(int i=0;i<rows;i++) {
			
			String line_id = rd.getStringByDI("rsLineBase","line_id",i);
			String cmpny_name = rd.getStringByDI("rsLineBase","cmpny_name",i);
			String line_name = rd.getStringByDI("rsLineBase","line_name",i);
			String planoflinedate = rd.getStringByDI("rsLineBase","planoflinedate",i);
			if(!"".equals(planoflinedate))
			{
				planoflinedate = planoflinedate.substring(0,10);
			}
			String maxpersoncount = rd.getStringByDI("rsLineBase","maxpersoncount",i);
			if("".equals(maxpersoncount)){maxpersoncount="0";}
			String spare = rd.getStringByDI("rsLineBase","spare",i);
			if("".equals(spare)){spare="0";}
	%>
		<tr>
            <td><%=cmpny_name %></td>
	  		<td><img alt="热点线路" src="<%=request.getContextPath()%>/images/hot3.gif">&nbsp;<span title="<%=line_name%>"><%=line_name.length()<=20?line_name:line_name.substring(0,20)+"..."  %></span></td>
	  		<td><%=planoflinedate %></td>
			<td>总数：<%=maxpersoncount %> 
				剩余：<%=spare %>
			</td>
            <td>
            <%
            int rows2 = rd.getTableRowsCount("rsLinePrice");
            for(int j=0;j<rows2;j++){
            	String line_id2 = rd.getStringByDI("rsLinePrice","line_id",j);
            	if(line_id.equals(line_id2)){

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
            <td><a href="###" onclick="openUrl('<%=request.getContextPath()%>/main/lineMng/editInit.?DMSM/JGLX=4&dmsm/fbjh=1&dmsm/jtgj=2&dmsm/xlzt=5&dmsm/xllx=3&xzqh/layer=1&TA_ZT_LINEMNG/LINE_ID=<%=line_id %>&TA_ZT_LINEMNG/ORGID=<%=sd.getString("orgid") %>&TA_ZT_FBJH_TMP/line_id=<%=line_id %>&TA_ZT_FBJH_TMP/ORGID=<%=sd.getString("orgid") %>&TA_ZT_LINE_PRICES/LINE_ID=<%=line_id %>&TA_ZT_LINE_PRICES/ORGID=<%=sd.getString("orgid") %>&LINE_ID=<%=line_id %>&TA_ZT_GATHER/LINE_ID=<%=line_id %>&TA_ZT_GATHER/ORGID=<%=sd.getString("orgid") %>&TA_ZT_LINEMNGBLOB/XLID=<%=line_id %>&TA_ZT_LINEMNGBLOB/ORGID=<%=sd.getString("orgid") %>&TA_ZT_GATHER_HIS/GATHER_ID=&TA_ZT_GATHER_HIS/ORGID=<%=sd.getString("orgid") %>&TA_ZT_LINEANDINSURANCE/LINE_ID=<%=line_id %>&TA_ZT_LINEANDINSURANCE/ORGID=<%=sd.getString("orgid") %>&TA_ZT_INSURANCE/ID=&TA_ZT_INSURANCE/ORGID=<%=sd.getString("orgid") %>')" ><img alt="编辑" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[修改]</a>
           -	<a href="<%=request.getContextPath()%>/delLine.?id=<%=line_id %>&type=<%=rd.getString("type") %>" onclick="return confirm('此操作将删除与此条线路相关所有信息,并且无法回复,确定删除吗?')">
					<img alt="删除" src="<%=request.getContextPath()%>/images/del-icon.gif">&nbsp;[删除]
				</a>
            </td>
         </tr>	
     <%} %> 
</table>
</div>
<div id="page">
	<%String url="listLineByCreater.?pageSize=10&pageNO=";%>
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
