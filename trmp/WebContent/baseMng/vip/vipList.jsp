<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("TA_VIP_INFOs")>0){
     pageNO=Integer.parseInt((String)rd.getAttr("TA_VIP_INFO", "pageNO"));
	 pageSize=Integer.parseInt((String)rd.getAttr("TA_VIP_INFO", "pageSize"));
		totalPage=(Integer)rd.getAttr("TA_VIP_INFOs", "pagesCount");
		rowsCount = (Integer)rd.getAttr("TA_VIP_INFOs", "rowsCount");
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
function enableInput(){
	var rs = false;
	var objs = document.vip_form.elements;
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
			}else if(confirm('此操作将无法回复,确定删除所勾选数据?')){
		document.vip_form.action="delVip.";
		document.vip_form.submit();
			}
		}
	function getVipBylike(){
		document.vip_form.action="getVipBylike.?TA_VIP_INFO@pageNO=1&TA_VIP_INFO@pageSize=10";
		document.vip_form.submit();
		}
</script>
<title>会员基础信息列表</title>
<script type="text/javascript">
</script>
</head>
<body>
<form  name="vip_form" method="post">
<div id="lable"><span>VIP基础信息维护</span></div>
	<div id="top-select">
	<div class="select-div" >
	  <span class="text" id="select-condition">按条件查询</span>
	</div>
		<div class="select-div" onclick="return GB_myShow('','<%=request.getContextPath()%>/baseMng/vip/addVip.jsp')"><span id="add-icon"></span>
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
	会员卡号：<input type="text" name="vip_no" id="CMPNY_NAME" class="text_input"/>&nbsp;<br/>
	会员姓名：<input type="text" name="vip_name" id="CMPNY_NAME" class="text_input"/>&nbsp;
			  <input type="button" value="查询"  onClick="getVipBylike();"/>
	</div>
	</div></div></div>
	<input type="image" src="<%=request.getContextPath() %>/images/if-select-img/close.gif" class="jqmdX jqmClose" />
</div>
	
	

<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td id="selectAll"><input type="checkbox" id="checkboxTop"></input></td>
			<td>会员编号</td>
			<td>会员姓名</td>
			<td>会员卡号</td>
			<td>联系电话</td>
			<td>联系地址</td>
			<td>身份证号码</td>
			<td>会员积分</td>
			<td>申请日期</td>
			<td>操作</td>
		</tr>
		<%int hotelRows=rd.getTableRowsCount("TA_VIP_INFOs");for(int h=0;h<hotelRows;h++){%>
		<tr>
			<td><input type="checkbox" id="checkboxEle" name="TA_VIP_INFO/ID[<%=h%>]" value="<%=rd.getStringByDI("TA_VIP_INFOs","ID",h) %>"></input></td>
			<td><%=rd.getStringByDI("TA_VIP_INFOs","ID",h)%></td>
			<td><%=rd.getStringByDI("TA_VIP_INFOs","VIP_NAME",h)%></td>
			<td><%=rd.getStringByDI("TA_VIP_INFOs","VIP_NO",h)%></td>
			<td><%=rd.getStringByDI("TA_VIP_INFOs","VIP_TEL",h)%></td>
			<td><%=rd.getStringByDI("TA_VIP_INFOs","ADDRESS",h)%></td>
			<td><%=rd.getStringByDI("TA_VIP_INFOs","ID_CARD",h)%></td>
			<td><%=rd.getStringByDI("TA_VIP_INFOs","VIP_INTEGRAL",h)%></td>
			<td><%=rd.getStringByDI("TA_VIP_INFOs","APPLY_DATE",h).substring(0,10)%></td>
			<td>
				<a href="###" onclick="javascript:GB_myShow('','<%=request.getContextPath()%>/getVipById.?TA_VIP_INFO/ID=<%=rd.getStringByDI("TA_VIP_INFOs","id",h) %>')">
					<img alt="编辑" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[修改]
				</a>-
				<a href="<%=request.getContextPath() %>/delVip.?TA_VIP_INFO/ID=<%=rd.getStringByDI("TA_VIP_INFOs","ID",h)%>" onclick="return confirm('此操作将无法恢复,确定删除吗?')">
					<img alt="删除" src="<%=request.getContextPath()%>/images/del-icon.gif">&nbsp;[删除]
				</a>
			</td>
		</tr>
		<%} %>
	</table>
</div>
<div id="page">
	<%String url="getAllVipInfo.?TA_VIP_INFO/ID=&TA_VIP_INFO@pageSize=10&TA_VIP_INFO@pageNO=";%>
	<span title="首页" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="上一页" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
					共<%=rd.getAttr("TA_VIP_INFOs","rowsCount")==null?"0":rd.getAttr("TA_VIP_INFOs","rowsCount") %> 条记录，
					每页 <%=pageSize%>条
	</span>
	<span title="下一页" class="next-page" onclick='query("<%=pageNO+1>totalPage?totalPage:pageNO+1 %>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span title="尾页" class="last-page" onclick='query("<%=totalPage%>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="go-to-page" >
		跳转到第：<input type="text" id="gotopage" onkeydown="checkNum()"/> 页
	</span>
	<span title="跳转" class="go-to-page-icon" onclick='go("<%=totalPage%>","<%=(int)Math.min(pageNO,totalPage)%>","<%=url %>")'></span>
</div>
</form>
</body>
</html>