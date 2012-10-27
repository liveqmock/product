<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
    <%@include file="/common.jsp" %>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("groupListRs")>0){
     pageNO=Integer.parseInt((String)rd.getString("pageNO"));
	 pageSize=Integer.parseInt((String)rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("groupListRs", "pagesCount");
		rowsCount = (Integer)rd.getAttr("groupListRs", "rowsCount");
	}
	String days = rd.getString("days");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>团队列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/common.js"></script>



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
        height: 500,// 1200,
        width: width || 900,
        fullscreen: true,
        show_loading: false,
        callback_fn: callback_fn
    }
    var win = new GB_Window(options);
    return win.show(url);
}
GB_myShow1 = function(caption, url, /* optional */ height, width, callback_fn) {
    var options = {
        caption: caption,
        height: 500,// 1200,
        width: width || 800,
        fullscreen: true,
        show_loading: false,
        callback_fn: callback_fn
    }
    var win = new GB_Window(options);
    return win.show(url);
}

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

function showLine(lineid) {

	var url = "showLineDetail.?TA_ZT_LINEMNG/LINE_ID="+lineid;
	GB_myShow('线路行程明细',url);
}
function look(tid,bDate){
	var url = "showGroupDetail.?groupId="+tid;
	GB_myShow('团队订单人员明细/团号：'+tid+'/开始时间：'+bDate+'',url,'1200','1000');
}
</script>
</head>
<body  >
<form action="groupLists.?DMSM/XLQY=20&pageSize=10&pageNO=1" name="myForm" method="post">
<div id="lable">
<div style="float: left"><span >团队管理</span></div>
<div class="select-div" style="float: left">
	<a href="<%=request.getContextPath()%>/zt/group/organizeRegisterForm.?dmsm/cllx=13&dmsm/JDXJ=6&dmsm/jtgj=2&dmsm/JGLX=4&XZQH/PID=0">
	  	<span id="add-icon"></span> 
	  	<span class="text">
	  	添加
	  	</span>
  	</a>
 </div>
</div>
<div id="thisSelect-table"  >
	<table  >
		<tr>
			<td rowspan="3" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td align="right">
			线路区域：
			</td>
			<td colspan="4">
			<a href="<%=request.getContextPath()%>/main/group/groupLineAreaLists.?LineArea=&DMSM/XLQY=20&pageNO=1&pageSize=10">全部</a>/
			<%
				int dRows = rd.getTableRowsCount("XLQY"); 
				for(int i = 0; i < dRows; i++){
					String dmz = rd.getStringByDI("XLQY", "DMZ", i);
					String dmsm = rd.getStringByDI("XLQY", "DMSM1", i);
			%>
			<a href="<%=request.getContextPath()%>/main/group/groupLineAreaLists.?LineArea=<%=dmz %>&DMSM/XLQY=20&pageNO=1&pageSize=10"><%=dmsm %></a>/
			<%} %>
			</td>
			<td align="right">&nbsp;&nbsp;发团日期：</td>
			<td><input type="text" name="bDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" style="width: 80px;"/></td>
			<td align="right">线路名称：</td>
			<td><input type="text" name="lineName" /></td>
			<td align="right">团号：</td>
			<td><input type="text" name="tid" /></td>
			<td><a href="###" onclick="postData();"><img alt="搜索" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table" >	
  <table class="datas" >
	<tr id="first-tr">
	  <td >团号</td>
	  <td >组团社名称</td>
	  <td >线路名称</td>
  	  <td >出团日期</td>
	  <td >发团人数详细</td>
	  <td >门市价/同行结算价</td>
	  <td >团状态</td>
	  <td >操作</td>
	</tr>
	<%
		int rows = rd.getTableRowsCount("groupListRs");
		for(int i=0;i<rows;i++) {
			String groupid = rd.getStringByDI("groupListRs","id",i);
			String line_id = rd.getStringByDI("groupListRs","line_id",i);
			String cmpny_name = rd.getStringByDI("groupListRs","szlxsmc",i);
			String line_name = rd.getStringByDI("groupListRs","xlmc",i);
			String bDate = rd.getStringByDI("groupListRs","begin_date",i);
			String maxpersoncount = rd.getStringByDI("groupListRs","maxpersoncount",i);
			String spare = rd.getStringByDI("groupListRs","syrs",i);
			String state = rd.getStringByDI("groupListRs","ftzt",i);
			String gstate = rd.getStringByDI("groupListRs","state",i);
			
	%>
	<tr>
	  <td ><a href="###" onclick="look('<%=groupid%>','<%=bDate%>')"><%=groupid %></a></td>
	  <td><%=cmpny_name %></td>
	  <td><img alt="热点线路" src="<%=request.getContextPath()%>/images/hot3.gif">&nbsp;
	  	<a href="###" onclick="return GB_myShow1('','viewTravelList.?groupId=<%=groupid %>&lineId=<%=line_id %>');" title="<%=line_name %>"><%=line_name.length()<=20?line_name:line_name.substring(0,20)+"..."  %></a>
	  </td>
	  <td><%=bDate %></td>
	  <td>	总数：<%=maxpersoncount %> <br>
	  		剩余：<%=spare %></td>
	  <td>
		<%
            int rows2 = rd.getTableRowsCount("groupPrices");
            for(int j=0;j<rows2;j++){
            	String groupID = rd.getStringByDI("groupPrices","g_id",j);
            	if(groupid.equals(groupID)){

                	String priceType = rd.getStringByDI("groupPrices","dmsm1",j);
                	String PRICE_MS = rd.getStringByDI("groupPrices","PRICE_MS",j);
                	String price_th = rd.getStringByDI("groupPrices","price_th",j);
            %>
            <%=priceType %>：<font color="red"><%out.println(PRICE_MS);%></font>元/
            <%=priceType %>：<font color="red"><%out.println(price_th);%></font>元<%out.println("<br>"); %>
            <%	}
           	}%>
	  </td>
	  <td><%if("1".equals(state)){ %>正常<%}else{ %>已封团<% } %></td>
	  <td>
<%
String nextUrl = request.getContextPath()+"/main/group/editGroupInit.?TA_ZT_GROUP/id="
		+groupid+"&TA_ZT_LINEMNG/LINE_ID="+line_id+"&TA_ZT_GPRICE/G_ID="
		+groupid+"&dmsm/JTGJ=2&dmsm/jglx=4&TA_ZT_GROUP/begin_date="+bDate+"&TA_ZT_LINEDETAI4G/TID="
		+groupid+"&TA_ZT_GATHER/LINE_ID="+line_id+"&TA_ZT_GATHER_HIS/GATHER_ID=&TA_ZT_GPRICE@orderBy=ID&TA_ZT_LINEDETAI4G@orderBy=rq";
%>
	  	<a href="###" onclick="return GB_myShow('团修改','<%=nextUrl %>','3000','920')">
	  	  <img alt="修改团队信息" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[团队修改]
	  	</a>
	  	<br>
	  	<%if("1".equals(gstate)||"2".equals(gstate)){ %>
	  	<a href="<%=request.getContextPath() %>/main/ztPlans/ztInitMasterPlan.?TA_ZT_GROUP/ID=<%=groupid %>&TA_ZT_JHHOTEL/TID=<%=groupid %>&TA_TDJDXXZJB_ZT/TID=<%=groupid %>&TA_ZT_JHCT/TID=<%=groupid %>&TA_ZT_JH/TID=<%=groupid %>&TA_ZT_JHPW/TID=<%=groupid %>&TA_ZT_JHCL/TID=<%=groupid %>&TA_ZT_JHJD/TID=<%=groupid %>&TA_ZT_JHDJ/TID=<%=groupid %>&TA_ZT_JHGW/TID=<%=groupid %>&TA_ZT_JHJIAD/TID=<%=groupid %>&TA_ZT_JHDY/TID=<%=groupid %>&isCheck=Y&mklb=1">
	  	  <img alt="制定团队计划" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[业务计调]
	  	</a>
	  	<%} %>
	  </td>
	</tr>
	<%} %> 
  </table>
</div>	
<div id="page">
	<%String url=request.getContextPath()+"/main/group/groupLists.?pageSize=10&pageNO=";%>
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