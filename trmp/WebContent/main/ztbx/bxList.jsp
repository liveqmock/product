<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
 <%@include file="/common.jsp" %>
 <%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("queryGuideAllList")>0){
		pageNO=Integer.parseInt(rd.getString("pageNO"));
		pageSize=Integer.parseInt(rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("queryGuideAllList", "pagesCount");
		rowsCount = (Integer)rd.getAttr("queryGuideAllList", "rowsCount");
	}
%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>线路信息管理</title>
<link href="<%=request.getContextPath() %>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<title>导游报账列表</title>





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
        height: 500,// 500,
        width: width || 700,
        fullscreen: true,
        show_loading: false,
        callback_fn: callback_fn
    }
    var win = new GB_Window(options);
    return win.show(url);
}
</script>





<script type="text/javascript">
function showDjLineDetail(XLID) {
	GB_myShow('线路行程明细','showDjLineDetail.?TA_ZT_LINEMNG/XLID='+XLID);
}

function findByLike(){
	document.dj_guide_form.Action="findByLike.?&pageSize=10&pageNO=1 ";
	document.dj_guide_form.submit();
}
</script>
</head>

<body>
<form  name="dj_guide_form" method="post">
<div id="lable"><span>导游报账列表</span></div>
<div id="thisSelect-table" >
	<table >
		<tr>
			<td rowspan="3" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td align="right">团号：</td>
			<td><input type="text" name="groupID" /></td>
			<td align="right">发团日期：</td>
			<td><input type="text" name="bDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" /></td>
			<td align="right">线路名称：</td>
			<td><input type="text"  name="xlmc"/></td>
			<td><a href="###" onclick="findByLike()"><img alt="搜索" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td>团号</td>
			<td>线路名称</td>
			<td>发团日期</td>
			<td>返团日期</td>
			<td>报销状态</td>
			<td>操作</td>
		</tr>
		<%
		int rows = rd.getTableRowsCount("queryGuideAllList");
		for(int i=0;i<rows;i++) {
			String ID = rd.getStringByDI("queryGuideAllList","ID",i);//团号
			String XLID = rd.getStringByDI("queryGuideAllList","XLID",i);//团号
			String XLMC = rd.getStringByDI("queryGuideAllList","xlmc",i);//线路名称
			String BEGIN_DATE = rd.getStringByDI("queryGuideAllList","BEGIN_DATE",i);//发团日期
			String END_DATE = rd.getStringByDI("queryGuideAllList","END_DATE",i);//返团日期
			String STATE = rd.getStringByDI("queryGuideAllList","STATE",i);//报销状态
			int state = Integer.parseInt(STATE);
			String YJZT = rd.getStringByDI("queryGuideAllList","YJZT",i);//审批状态
		%>
		<tr>
            <td><%=ID %></td>
	  		<td>
	  			<img alt="热点线路" src="<%=request.getContextPath()%>/images/hot3.gif"/>&nbsp;
	  			<a href="###" onclick="" title="<%=XLMC %>"><%=XLMC.length()<=20?XLMC:XLMC.substring(0,20)+"..."  %></a>
	  		</td>
	  		<td><%=BEGIN_DATE %></td>
	  		<td><%=END_DATE %></td>
	  		<td><% if("1".equals(STATE)){out.print("待计划");}else if("4".equals(STATE)){out.print("已审核/待报销");}else if("5".equals(STATE)){out.print("报销中");}else if("6".equals(STATE)){out.print("已报销");} %></td>
	  		<td>
	  		<%
	  		switch(state){
	  		case 4:
	  		case 5:
	  		%>
	  			<a href="<%=request.getContextPath()%>/main/ztbx/ztrbtGuideListByUser.?TA_ZT_GROUP/ID=<%=ID %>&TA_ZT_BXHOTEL/TID=<%=ID %>&TA_ZT_BXCT/TID=<%=ID %>&TA_ZT_BXJD/TID=<%=ID %>&TA_ZT_BXDJ/TID=<%=ID %>&TA_ZT_BXDY/TID=<%=ID %>&TA_ZT_BXCL/TID=<%=ID%>&TA_ZT_BXPW/TID=<%=ID %>&TA_ZT_BXGW/TID=<%=ID %>&TA_ZT_BXJIADIAN/TID=<%=ID %>&TA_TDBXZJXXB_ZT/TID=<%=ID %>" >
           		<img alt="编辑" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[申请报账]</a>
            <%
	  		case 6:
	  		%>
	  			<a href="<%=request.getContextPath()%>/main/ztPlans/shPlan/ztInitAllSHBx.?TA_ZT_GROUP/ID=<%=ID %>&TA_ZT_BXHOTEL/TID=<%=ID %>&TA_ZT_BXCT/TID=<%=ID %>&TA_ZT_BXJD/TID=<%=ID %>&TA_ZT_BXDJ/TID=<%=ID %>&TA_ZT_BXDY/TID=<%=ID %>&TA_ZT_BXCL/TID=<%=ID%>&TA_ZT_BXPW/TID=<%=ID %>&TA_ZT_BXGW/TID=<%=ID %>&TA_ZT_BXJIADIAN/TID=<%=ID %>&TA_TDBXZJXXB_ZT/TID=<%=ID %>&TA_DJ_TSPB/TID=<%=ID %>&action=view&flag=view&pageNO=1&pageSize=10">
           		<img alt="查看" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[查看]</a>
	  		<%
            }%>
            </td>
         </tr>	
	<%} %>		
			
	</table>
</div>
<div id="page">
	<%String url=request.getContextPath()+"/main/ztbx/ztbxGuideAllList.?pageSize=10&pageNO=";%>
	<span title="首页" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="上一页" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
					共<%=rd.getAttr("queryGuideAllList","rowsCount")==null?"0":rd.getAttr("queryGuideAllList","rowsCount") %> 条记录，
					每页 <%=pageSize%>条
	</span>
	<span title="下一页" class="next-page" onclick='query("<%=pageNO+1>totalPage?totalPage:pageNO+1 %>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span title="尾页" class="last-page" onclick='query("<%=totalPage%>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="go-to-page" >
		跳转到第：<input type="text" id="gotopage"/> 页
	</span>
	<span title="跳转" class="go-to-page-icon" onclick='go("<%=totalPage%>","<%=(int)Math.min(pageNO,totalPage)%>","<%=url %>")'></span>
</div>
</form>
</body>
</html>
