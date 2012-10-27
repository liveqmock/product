<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/selectAll.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/thickbox/thickbox.css" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>

<script type="text/javascript">
function enableInput(){
	var rs = false;
	var objs = document.flow_form.elements;
	if(objs==null){
		return false;
	}
	var len=objs.length;
	var checkbox;
	for(var i=0;i<len;i++){
		if(objs.item(i).tagName=="INPUT" && objs.item(i).type=="checkbox" && objs.item(i).name!=''){
			checkbox=objs.item(i); 
			if(checkbox.checked){
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
			}else if(confirm('此操作将无法回复,确定删除所勾选数据?')){
		document.flow_form.action="delFlow.";
		document.flow_form.submit();
			}
		}
	function getFlowBylike(){
		document.flow_form.action="getFlowBylike.?TA_FLOWDEFINITION@pageNO=1&TA_FLOWDEFINITION@pageSize=10";
		document.flow_form.submit();
		}

	function doSub(flID){
		if(confirm('此操作将无法回复,确定删除所勾选数据?')){
			window.location.href="delFlow.?TA_FLOWDEFINITION/DEFINITIONID[0]="+flID;
		}
	}
	
	//添加流程信息
	function newFlow(url){
		window.location.href=url;
	}
	//修改流程信息
	function editFlow(url){
		window.location.href=url;
	}
	
</script>

<title>流程列表</title>
</head>
<%
int rosCount = (Integer)rd.getAttr("TA_FLOWDEFINITIONS","ROWSCOUNT")==null?1:(Integer)rd.getAttr("TA_FLOWDEFINITIONS","ROWSCOUNT");
int pageCount= (Integer)rd.getAttr("TA_FLOWDEFINITIONS","PAGESCOUNT")==null?1:(Integer)rd.getAttr("TA_FLOWDEFINITIONS","ROWSCOUNT");
int pageNo=Integer.parseInt((String)rd.getAttr("TA_FLOWDEFINITION","PAGENO"));
int pageSize=Integer.parseInt((String)rd.getAttr("TA_FLOWDEFINITION","PAGESIZE"));
%>
<body>
<form  name="flow_form" method="post">
<div id="lable"><span>流程定义信息维护</span></div>

		<title>基础信息列表</title>
<script type="text/javascript">
</script>
</head>
<body>

	<div id="top-select">
		<div class="select-div" >
	  		<span class="text" id="select-condition">按条件查询</span>
		</div>
	</div>
	
<div id="ex3a" class="jqmDialog">
	<div class="jqmdTL"><div class="jqmdTR"><div class="jqmdTC jqDrag">按条件查询</div></div></div>
	<div class="jqmdBL"><div class="jqmdBR"><div class="jqmdBC">
	<div class="jqmdMSG">
	流程名称：<input type="text" name="flow_name" id="CMPNY_NAME" class="text_input"/>&nbsp;
			  <input type="button" value="查询"  onClick="getFlowBylike();"/>
	</div>
	</div></div></div>
	<input type="image" src="/trmp/images/if-select-img/close.gif" class="jqmdX jqmClose" />
</div>
<div id="lable"><span>流程列表</span></div>
<div id="list-table">
	<table class="datas" >
	
		<tr id="first-tr">
			<td id="selectAll"><input type="checkbox" id="checkboxTop"></input></td>
			<td>流程定义名称</td>
			<td>流程描述</td>
			<td>操作</td>
		</tr>
			
<%
		int rows = rd.getTableRowsCount("TA_FLOWDEFINITIONs");
		for(int i=0;i<rows;i++) {
			String FLID = rd.getStringByDI("TA_FLOWDEFINITIONs","DEFINITIONID",i);
			String FLOWNAME = rd.getStringByDI("TA_FLOWDEFINITIONs","FLOWNAME",i);
			String FLOWDES = rd.getStringByDI("TA_FLOWDEFINITIONs","FLOWDESC",i);

	%>	
		<tr>
			<td>
				<input type="checkbox" id="checkboxEle" name="TA_FLOWDEFINITION/DEFINITIONID[<%=i%>]" value="<%=rd.getStringByDI("TA_FLOWDEFINITIONs","DEFINITIONID",i) %>" ></input>
				<input type="hidden" value="<%=FLID%>"></input>
			</td>
			<td><%=FLOWNAME%></td>
			<td><%=FLOWDES%></td>
			<td>
				<a href="<%=request.getContextPath()%>/maindj/flow/getNodeByFlowID.?TA_FLOWNODE/DEFINITIONID=<%=FLID %>&TA_FLOWNODE/orgid=<%=sd.getString("orgid") %>&TA_FLOWNODE@orderBy=nodeid">
            	<img alt="编辑" src="<%=request.getContextPath()%>/images/edit.gif">[节点编缉]</a>
			</td>
		</tr>
		<%} %>
	</table>
</div>
<div id="page">
	<%String url="listFlow.?TA_FLOWDEFINITION/DEFINITIONID=&TA_FLOWDEFINITION@pageSize=10&TA_FLOWDEFINITION@pageNo=";%>
	<span title="首页" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNo,pageCount)%>","<%=url%>","<%=pageCount%>")'></span>		
	<span title="上一页" class="prev-page" onclick='query("<%=pageNo-1<1?1:pageNo-1 %>","P","<%=(int)Math.min(pageNo,pageCount)%>","<%=url%>","<%=pageCount%>")'></span>
	<span class="pagination-sum">
第<%=Math.min(pageNo,pageCount)%>/<%=pageCount%> 页，
					共<%=rosCount%> 条记录，
					每页 <%=pageSize%>条
	</span>
	<span title="下一页" class="next-page" onclick='query("<%=pageNo+1>pageCount?pageCount:pageNo+1 %>","N","<%=(int)Math.min(pageNo,pageCount)%>","<%=url%>","<%=pageCount%>")'></span>
	<span title="尾页" class="last-page" onclick='query("<%=pageCount%>","N","<%=(int)Math.min(pageNo,pageCount)%>","<%=url%>","<%=pageCount%>")'></span>
	<span class="go-to-page" >
	跳转到第：<input type="text" id="gotopage" onkeydown="checkNum()"/> 页
	</span>
	<span title="跳转" class="go-to-page-icon" onclick='go("<%=pageCount%>","<%=(int)Math.min(pageNo,pageCount)%>","<%=url %> ")'></span>
</div>
</form>
</body>
</html>
