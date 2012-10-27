<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
    
<%@include file="/common.jsp"%>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("TA_DJ_LINEMNGs")>0){
		
	   	pageNO=Integer.parseInt((String)rd.getAttr("TA_DJ_LINEMNG", "pageNO"));
		pageSize=Integer.parseInt((String)rd.getAttr("TA_DJ_LINEMNG", "pageSize"));
		totalPage=(Integer)rd.getAttr("TA_DJ_LINEMNGs", "pagesCount");
		rowsCount = (Integer)rd.getAttr("TA_DJ_LINEMNGs", "rowsCount");
	}

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Random"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>线路报价单管理</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>



<script type="text/javascript">
        var GB_ROOT_DIR = "<%=request.getContextPath()%>/greybox/";
    </script>

    <script type="text/javascript" src="<%=request.getContextPath()%>/greybox/AJS.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/greybox/AJS_fx.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/greybox/gb_scripts.js"></script>
    <link href="<%=request.getContextPath()%>/greybox/gb_styles.css" rel="stylesheet" type="text/css" media="all" />


<script type="text/javascript">
GB_myShow = function(caption, url, /* optional */ height, width, callback_fn) {
    var options = {
        caption: caption,
        height: 500,// || 800,
        width: width || 800,
        fullscreen: true,
        show_loading: false,
        callback_fn: callback_fn
    }
    var win = new GB_Window(options);
    return win.show(url);
}
</script>




<script type="text/javascript">
function djFindByLike(){

	document.myForm.submit();
}

function openWindow(url){
	window.open(url,'obj','width=900, height=700, top=0, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
}

function checkMsg(){
	<%if(!"".equals(rd.getString("msg"))){%>
	alert("此条线路已有游客报名,无法删除");
	<%}%>
}
</script>

</head>
<body onkeydown="if(event.keyCode==13)  {event.keyCode=9};"  onload="checkMsg()">
<form name="myForm" method="post" action="djPrintLineXCDList.?DMSM/XLQY=20&TA_DJ_LINEMNG@pageNO=1&TA_DJ_LINEMNG@pageSize=10&TA_DJ_LINEMNG@orderBy=XLID desc">
<div id="ex3a" class="jqmDialog">
	<div class="jqmdTL"><div class="jqmdTR"><div class="jqmdTC jqDrag">按条件查询</div></div></div>
	<div class="jqmdBL"><div class="jqmdBR"><div class="jqmdBC">
	
	<div class="jqmdMSG">
	  线路名称：<input type="text" name="TA_DJ_LINEMNG/XLMC" class="text_input" id="XLMC"/>&nbsp;<br>
		  	  <input type="button" value="查询" class="bnt" onClick="djFindByLike();"/>
	</div>
	
	</div></div></div>
	<input type="image" src="<%=request.getContextPath() %>/images/if-select-img/close.gif" class="jqmdX jqmClose" />
</div>


<div id="lable" ><b>线路报价单管理</b></div>
<div id="top-select">
<div class="select-div" >
	  <span class="text" id="select-condition">查询条件</span>
</div>

</div>

<div id="list-table" >
<table class="datas">
	<tr id="first-tr">
        <td width="15%">行程/天数</td>
        <td width="6%">发团</td>
        <td width="10%">门票</td>
        <td width="11%">用车</td>
        <td width="11%">导游</td>
        <td width="12%">购物安排</td>
        <td width="15%">操作</td>
	</tr>
</table>
	<%
		int rows = rd.getTableRowsCount("TA_DJ_LINEMNGs");
		for(int i=0;i<rows;i++) {
			String XLID = rd.getStringByDI("TA_DJ_LINEMNGs","XLID",i);//线路ID
			String XLMC = rd.getStringByDI("TA_DJ_LINEMNGs","XLMC",i);//线路名称
			String TS = rd.getStringByDI("TA_DJ_LINEMNGs","TS",i);//天数
			String FBJH = rd.getStringByDI("TA_DJ_LINEMNGs","FBJH",i);//发班时间
			String MP = rd.getStringByDI("TA_DJ_LINEMNGs","MP",i);//门票
			String YC = rd.getStringByDI("TA_DJ_LINEMNGs","YC",i);//用车
			String DY = rd.getStringByDI("TA_DJ_LINEMNGs","DY",i);//导游
			String GW = rd.getStringByDI("TA_DJ_LINEMNGs","GW",i);//购物安排
	%>
<div>
	
	<table class="datas">
		<tr >
	  		<td width="15%">&nbsp;
	  			<img alt="热点线路" src="<%=request.getContextPath()%>/images/hot3.gif"/>&nbsp;
	  			<a href="###" onclick="" title="<%=XLMC %>"><%=XLMC.length()<=20?XLMC:XLMC.substring(0,20)+"..."  %>/<%=TS %>天</a>
	  		&nbsp;</td>
	  		<td width="6%" title="<%=FBJH %>">&nbsp;<%=FBJH.length()<=20?XLMC:XLMC.substring(0,20)+"..."   %>&nbsp;</td>
			<td width="10%" title="<%=MP %>">&nbsp;<%=MP.length()<=20?MP:MP.substring(0,20)+"..."   %>&nbsp;</td>
            <td width="11%" title="<%=YC %>">&nbsp;<%=YC.length()<=20?YC:YC.substring(0,20)+"..."   %>&nbsp;</td>
            <td width="11%" title="<%=DY %>">&nbsp;<%=DY.length()<=20?DY:DY.substring(0,20)+"..."   %>&nbsp;</td>
            <td width="12%" title="<%=GW %>">&nbsp;<%=GW.length()<=20?GW:GW.substring(0,20)+"..."   %>&nbsp;</td>
            <td width="15%">
            	<a href="###" onclick="javascript:GB_myShow('','<%=request.getContextPath() %>/maindj/lineRelease/djPrintLineXCD.?TA_DJ_LINEMNG/XLID=<%=XLID %>&TA_DJ_XLJG/XLID=<%=XLID %>&TA_DJ_LINEDETAIL/XLID=<%=XLID %>&dmsm/cllx=13&DMSM/JDXJ=6&DMSM/XLQY=20&DMSM/JGLX=4','800','820','')"><img alt="" src="<%=request.getContextPath() %>/images/Print.gif">&nbsp;[查看详细行程并打印]</a>
            </td>
         </tr>
	</table>
	</div>
     <%} %> 
</div>
<div id="page">
	<%//String url=request.getContextPath()+"/maindj/lineRelease/djListLineByUser.?TA_DJ_LINEMNG/XLMC=&TA_DJ_LINEMNG@orderBy=XLID desc&TA_DJ_LINEMNG@pageSize=10&TA_DJ_LINEMNG@pageNO=";%>
	<%String url=request.getContextPath()+"/maindj/lineRelease/djListLineByUser.?TA_DJ_LINEMNG/xlid=&DMSM/XLQY=20&TA_DJ_LINEMNG@pageSize=10&TA_DJ_LINEMNG@orderBy=XLID desc&TA_DJ_LINEMNG@pageNO=";%>
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
