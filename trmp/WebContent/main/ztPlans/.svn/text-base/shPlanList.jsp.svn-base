<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 0;
	if(rd.getTableRowsCount("rsDSPList")>0){
		
	   	pageNO=Integer.parseInt(rd.getString("pageNO"));
		pageSize=Integer.parseInt(rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("rsDSPList", "pagesCount");
		rowsCount = (Integer)rd.getAttr("rsDSPList", "rowsCount");
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link href="<%=request.getContextPath() %>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<title>业务审核</title>

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
function queryGroupList(){
	document.djListGroup.action.name="ztQueryShPlanList.?pageNO=1&pageSize=10";
	document.djListGroup.submit();
}
function showDjLineDetail(XLID) {
	GB_myShow('线路行程明细','showDjLineDetail.?TA_ZT_LINEMNG/XLID='+XLID);
}
function showDjID(ID){
	GB_myShow('审核','ztInitAllSHPlan.?TA_ZT_GROUP/ID='+ID+'&TA_TDJDXXZJB_ZT/TID='+ID);
}
</script>
</head>

<body>
<form name="djListGroup" method="post">
<div id="lable"><span>业务审核列表</span></div>
<div id="thisSelect-table">
	<table  >
		<tr>
			<td rowspan="3" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td align="right">团号：</td>
			<td><input type="text" name="ID" /></td>
			<td align="right">发团日期：</td>
			<td><input type="text" name="bDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" /></td>
			<td align="right">线路名称：</td>
			<td><input type="text" name="xlmc" /></td>
			<td><a href="###" onclick="queryGroupList();"><img alt="搜索" src="<%=request.getContextPath()%>/images/search.gif"></a></td>
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
			<td>流程进度</td>
			<td>操作</td>
		</tr>
		<%
			int rows=rd.getTableRowsCount("rsDSPList");
			for(int i=0;i<rows;i++){
				String ID=rd.getStringByDI("rsDSPList","tid",i);
				String XLID = rd.getStringByDI("rsDSPList","XLID",i);
				String XLMC=rd.getStringByDI("rsDSPList","XLMC",i);
				String BEGIN_DATE = rd.getStringByDI("rsDSPList","BEGIN_DATE",i);
				String END_DATE = rd.getStringByDI("rsDSPList","END_DATE",i);
				String flowState = rd.getStringByDI("rsDSPList","STATE",i);
				String isBack = rd.getStringByDI("rsDSPList","isback",i);
				String nextRoleID = rd.getStringByDI("rsDSPList","nextroleid",i);
				//发起人
				String creater = rd.getStringByDI("rsDSPList","creater",i);
				
				String roleID = sd.getString("USERROLEST");
				boolean isTrue = false;
				if(!"".equals(roleID)){
					
					roleID = roleID.substring(1,roleID.length()-1);
					String[] roleIDs = roleID.split(",");
					for(int j=0;j<roleIDs.length;j++){
						if(nextRoleID.equals(roleIDs[j].trim())){
							isTrue = true;
							break;
						}
					}
				}
		%>
		<tr>
			<td><%=ID %></td>
			<td>
			  <img alt="热点线路" src="<%=request.getContextPath()%>/images/hot3.gif"/>&nbsp;
			  <a href="###" onclick="" title="<%=XLMC %>"><%=XLMC.length()<=20?XLMC:XLMC.substring(0,20)+"..."  %></a>
			</td>
			<td><%=BEGIN_DATE %></td>
			<td><%=END_DATE %></td>
			<td><%="end".equals(flowState)?"流程结束":rd.getStringByDI("rsDSPList","nodename",i) %></td>
			<td>
			<%
			if("end".equals(flowState) || creater.equals(sd.getString("userno"))){
			%>
			<a href="<%=request.getContextPath()%>/main/ztPlans/shPlan/ztInitAllSHPlan.?TA_ZT_GROUP/ID=<%=ID %>&TA_TDJDXXZJB_ZT/TID=<%=ID %>&TA_ZT_JHHOTEL/TID=<%=ID %>&TA_ZT_JHJIAD/TID=<%=ID %>&TA_ZT_JHGW/TID=<%=ID %>&TA_DJ_TSPB/TID=<%=ID %>&action=view&pageNO=1&pageSize=10&isCheck=Y"><img alt="查看" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[查看]</a>
			<%
			}
			//if(creater.equals(sd.getString("userno")) && isBack.equals("Y")){
			%>
				<!-- <a href="<%=request.getContextPath()%>/main/ztPlans/shPlan/ztInitMasterPlan.?TA_ZT_GROUP/ID=<%=ID %>&TA_ZT_JHPW/TID=<%=ID %>&TA_ZT_JHCT/TID=<%=ID %>&TA_ZT_JHHOTEL/TID=<%=ID %>&TA_ZT_JHDJ/TID=<%=ID %>&TA_ZT_JHJD/TID=<%=ID %>&TA_ZT_JHCL/TID=<%=ID %>&TA_TDJDXXZJB_ZT/TID=<%=ID %>&TA_ZT_JHGW/TID=<%=ID %>&TA_ZT_JHJIAD/tid=<%=ID %>&TA_DJ_TSPB/TID=<%=ID %>&isCheck=Y"><img alt="修改" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[修改]</a> -->
			<%	
			//}
			if(isTrue == true && !"end".equals(flowState)){
			%>
			<a href="<%=request.getContextPath()%>/main/ztPlans/shPlan/ztInitAllSHPlan.?TA_ZT_GROUP/ID=<%=ID %>&TA_TDJDXXZJB_ZT/TID=<%=ID %>&TA_ZT_JHHOTEL/TID=<%=ID %>&TA_ZT_JHJIAD/TID=<%=ID %>&TA_ZT_JHGW/TID=<%=ID %>&TA_DJ_TSPB/TID=<%=ID %>&action=authorize&pageNO=1&pageSize=10&isCheck=Y"><img alt="审核" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[审核]</a>
			<%
			}%>
			<%	
			//}
			if(creater.equals(sd.getString("userno"))&&isBack.equals("Y")){
			%>
			<a href="<%=request.getContextPath() %>/main/ztPlans/ztInitMasterPlan.?TA_ZT_GROUP/ID=<%=ID %>&TA_ZT_JHHOTEL/TID=<%=ID %>&TA_TDJDXXZJB_ZT/TID=<%=ID %>&TA_ZT_JHCT/TID=<%=ID %>&TA_ZT_JH/TID=<%=ID %>&TA_ZT_JHPW/TID=<%=ID %>&TA_ZT_JHCL/TID=<%=ID %>&TA_ZT_JHJD/TID=<%=ID %>&TA_ZT_JHDJ/TID=<%=ID %>&TA_ZT_JHGW/TID=<%=ID %>&TA_ZT_JHJIAD/TID=<%=ID %>&TA_ZT_JHDY/TID=<%=ID %>&isCheck=Y&mklb=1">
	  	  		<img alt="制定团队计划" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[业务计调]
	  		</a>
			<%
			}%>
			</td>
		</tr>
		<%} %>
	</table>
</div>
<div id="page">
	<%String url=request.getContextPath()+"/main/ztPlans/ztQueryShPlanList.?pageSize=10&pageNO=";%>
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