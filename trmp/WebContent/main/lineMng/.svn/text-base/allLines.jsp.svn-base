<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
    
<%@include file="/common.jsp"%>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("TA_ZT_LINEMNGs")>0){
     pageNO=Integer.parseInt((String)rd.getAttr("TA_ZT_LINEMNG","pageNO"));
	 pageSize=Integer.parseInt((String)rd.getAttr("TA_ZT_LINEMNG","pageSize"));
		totalPage=(Integer)rd.getAttr("TA_ZT_LINEMNGs", "pagesCount");
		rowsCount = (Integer)rd.getAttr("TA_ZT_LINEMNGs", "rowsCount");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>线路发布历史</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>

</head>
<script language="javascript">
	
	//根据条件查询
	function findTravelagency() {
		
		var line_name = document.getElementById("line_name").value;
		document.getElementById("line_name").value = "%"+line_name+"%";
		document.myForm.action="showAllLine.?TA_ZT_LINEMNG/line_state=1&TA_ZT_LINEMNG@pageNO=1&TA_ZT_LINEMNG@pageSize=10";
		document.myForm.submit();
	}

	function openWindow(url) {
		window.open(url,'obj','width=800, height=600, top=50, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}

	function selecedItem(lineID,type){

		window.opener.document.location.href="editInit.?DMSM/JGLX=4&dmsm/fbjh=1&dmsm/jtgj=2&dmsm/xlzt=5&dmsm/xllx=3&DMSM/XLQY=20&TA_ZT_LINEMNG/LINE_ID="+lineID+"&TA_ZT_FBJH_TMP/line_id="+lineID+"&TA_ZT_LINE_PRICES/LINE_ID="+lineID+"&TA_ZT_LINEDETAIL/LINEID="+lineID+"&TA_ZT_GATHER/LINE_ID="+lineID+"&TA_ZT_LINEMNGBLOB/XLID="+lineID+"&TA_ZT_GATHER_HIS/GATHER_ID=&TA_ZT_LINEANDINSURANCE/LINE_ID="+lineID+"&TA_ZT_INSURANCE/ID=&TA_ZT_LINEDETAIL@orderBy=rq";
		window.close();
	}

</script>
<body onkeydown="if(event.keyCode==13)  {event.keyCode=9};" >
<form name="myForm" method="post">
<div id="ex3a" class="jqmDialog">
	<div class="jqmdTL"><div class="jqmdTR"><div class="jqmdTC jqDrag">按条件查询</div></div></div>
	<div class="jqmdBL"><div class="jqmdBR"><div class="jqmdBC">
	
	<div class="jqmdMSG">
	 线路名称：<input type="text" name="TA_ZT_LINEMNG/line_name" id="line_name" class="text_input"/>&nbsp;
		 <input type="hidden" name="TA_ZT_LINEMNG/line_name[0]@relation" class="text_input" value="like"/>
		  <input type="button" value="查询" class="bnt" onClick="findTravelagency();"/>
	</div>
	
	</div></div></div>
	<input type="image" src="<%=request.getContextPath() %>/images/if-select-img/close.gif" class="jqmdX jqmClose" />
</div>


<div id="lable"><b>线路发布历史</b></div>
<div id="top-select">
<div class="select-div" >
	  <span class="text" id="select-condition">查询条件</span>
</div>
</div>

<div id="list-table">
<table class="datas">
	<tr id="first-tr">
        <td >线路名称</td>
        <td >天数</td>
        <td >线路类型</td>
        <td >最大收客人数</td>
        <td >可成团人数</td>
	</tr>
	<%
		int rows = rd.getTableRowsCount("TA_ZT_LINEMNGs");
		for(int i=0;i<rows;i++) {
			
			String line_id = rd.getStringByDI("TA_ZT_LINEMNGs","line_id",i);
			String lineName = rd.getStringByDI("TA_ZT_LINEMNGs","line_name",i);
			String days = rd.getStringByDI("TA_ZT_LINEMNGs","day_counts",i);
			String max = rd.getStringByDI("TA_ZT_LINEMNGs","maxpersoncount",i);
			String min = rd.getStringByDI("TA_ZT_LINEMNGs","minpersoncount",i);
			String lineType = rd.getStringByDI("TA_ZT_LINEMNGs","line_type",i);
			String lineTy = "";
			String type = "";
			if("1".equals(lineType)){
				lineTy = "长线";
				type = "long";
			}
			else if("2".equals(lineType)){
				lineTy = "短线";
				type = "short";
			}
			else if("3".equals(lineType)){
				lineTy = "出境";
				type = "long";
			}
			
	%>
		<tr>
            <td><a href="###" onclick="selecedItem('<%=line_id %>','<%=type %>');"><%=lineName %></a>
            	<input type="hidden" name="TA_ZT_LINEMNG/line_id" value="line_id"/></td>
	  		<td><%=days %></td>
	  		<td><%=lineTy %></td>
	  		<td><%=max %></td>
			<td><%=min %></td>
         </tr>	
     <%} %> 
</table>
</div>
<div id="page">
	<%String url="showAllLine.?TA_ZT_LINEMNG/line_state=1&TA_ZT_LINEMNG@pageSize=10&TA_ZT_LINEMNG@pageNO=";%>
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
