<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
    <%@include file="/common.jsp" %>
    <%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("TA_ZT_GATHER_HISs")>0){
     pageNO=Integer.parseInt((String)rd.getAttr("TA_ZT_GATHER_HIS", "pageNO"));
	 pageSize=Integer.parseInt((String)rd.getAttr("TA_ZT_GATHER_HIS", "pageSize"));
		totalPage=(Integer)rd.getAttr("TA_ZT_GATHER_HISs", "pagesCount");
		rowsCount = (Integer)rd.getAttr("TA_ZT_GATHER_HISs", "rowsCount");
	}
%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/selectAll.js"></script>




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
function enableInput(){
	var rs = false;
	var objs = document.gather_form.elements;
	if(objs==null){
		return false;
	}
	var len=objs.length;
	var checkbox;
	for(var i=0;i<len;i++){
		if(objs.item(i).tagName=="INPUT" && objs.item(i).type=="checkbox" && objs.item(i).name!=''){
			checkbox=objs.item(i); 
			//var ts=checkbox.name.split('[')[1];
			//var index=(ts.substring(0,ts.length-1));
			if(checkbox.checked){
				//document.getElementById("XYJBXXB/SFZMHM["+index+"]").disabled=false;
				rs = true;
			}
		}
	}
	return rs;
}
function del_select(){
	var rst= enableInput();
	if(rst==false){
	return false;
		}else if(confirm('此操作将无法回复,确定删除所勾选项?')){
			document.gather_form.action="batchDelGather.";
			document.gather_form.submit();
			}
}
function getGatherByName(){
	document.gather_form.action="getGatherByName.?TA_ZT_GATHER_HIS@pageNO=1&TA_ZT_GATHER_HIS@pageSize=10";
	document.gather_form.submit();
}
</script>
<title></title>
</head>
<body>
<form  name="gather_form" method="post">
<div id="lable"><span>集合地点信息维护</span></div>
	<div id="top-select">
	<div class="select-div" >
	  <span class="text" id="select-condition">查询条件</span>
	</div>
		<div class="select-div" onclick="return GB_myShow('','<%=request.getContextPath()%>/baseMng/gather_address/addgather.jsp')"><span id="add-icon"></span>
		 <span class="text">添加</span>
		</div>
		<div class="select-div" onclick="del_select()"><span id="del-icon"></span> <span
			class="text">删除</span>
		</div>
	</div>
	
	<div id="ex3a" class="jqmDialog">
	<div class="jqmdTL"><div class="jqmdTR"><div class="jqmdTC jqDrag">按条件查询</div></div></div>
	<div class="jqmdBL"><div class="jqmdBR"><div class="jqmdBC">
	<div class="jqmdMSG">
	酒店名称：<input type="text" name="name" />&nbsp;
		  <input type="button" value="查询" class="bnt" onClick="getGatherByName();"/>
	</div>
	</div></div></div>
	<input type="image" src="<%=request.getContextPath() %>/images/if-select-img/close.gif" class="jqmdX jqmClose" />
</div>

<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td id="selectAll"><input type="checkbox" id="checkboxTop"></input></td>
			<td>集合地点</td>
			<td>时间</td>
			<td>加价</td>
			<td>操作</td>
		</tr>
		<%int gatherRows=rd.getTableRowsCount("TA_ZT_GATHER_HISs");for(int h=0;h<gatherRows;h++){%>
		<tr>
			<td><input type="checkbox" id="checkboxEle" name="GATHER/CHECKBOX[<%=h%>]" value="<%=rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER_ID",h) %>"></input></td>
			<td><%=rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER",h)%></td>
			<td><%=rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER_TIME",h)%></td>
			<td><%=rd.getStringByDI("TA_ZT_GATHER_HISs","ADD_M_COUNT",h)%></td>
			<td>
				<a href="###" onclick="javascript:GB_myShow('','<%=request.getContextPath()%>/getGatherById.?TA_ZT_GATHER_HIS/GATHER_ID=<%=rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER_ID",h) %>')">
					<img alt="编辑" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[修改]
				</a>-
				<a href="<%=request.getContextPath() %>/delGather.?id=<%=rd.getStringByDI("TA_ZT_GATHER_HISs","GATHER_ID",h)%>" onclick="return confirm('此操作将无法恢复,确定删除吗?')">
					<img alt="删除" src="<%=request.getContextPath()%>/images/del-icon.gif">&nbsp;[删除]
				</a>
			</td>
		</tr>
		<%} %>
	</table>
</div>
<div id="page">
	<%String url="getAllGather.?TA_ZT_GATHER_HIS/GATHER_ID=&TA_ZT_GATHER_HIS@pageSize=10&TA_ZT_GATHER_HIS@pageNO=";%>
	<span class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
					共<%=rd.getAttr("TA_ZT_GATHER_HISs","rowsCount")==null?"0":rd.getAttr("TA_ZT_GATHER_HISs","rowsCount") %> 条记录，
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