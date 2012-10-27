<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
    
<%@include file="/common.jsp"%>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("TA_TRAVELAGENCYS")>0){
     pageNO=Integer.parseInt((String)rd.getAttr("TA_TRAVELAGENCY", "pageNO"));
	 pageSize=Integer.parseInt((String)rd.getAttr("TA_TRAVELAGENCY", "pageSize"));
		totalPage=(Integer)rd.getAttr("TA_TRAVELAGENCYS", "pagesCount");
		rowsCount = (Integer)rd.getAttr("TA_TRAVELAGENCYS", "rowsCount");
	}

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>合作旅行社信息</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<!-- checkbox的全选 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/selectAll.js"></script>

</head>
<script language="javascript">
	function winOpen(url){
		window.location.href=url;
	}
	//单条删除
	function del_byID(id){
		if(confirm('此操作将无法恢复，确定要删除吗？')){
			window.location.href="<%=request.getContextPath() %>/baseMng/travelAgc/deleteAll.?cityID=<%=rd.getStringByDI("TA_TRAVELAGENCY","city_id",0) %>&priID=<%=rd.getStringByDI("TA_TRAVELAGENCY","priID",0) %>&ta_travelagency/TRAVEL_AGC_ID="+id+"&ywlb=<%=rd.getString("ywlb") %>";
			return true;
		}
	}
	//根据条件查询
	function findTravelagency(){
		if(document.getElementById("CMPNY_NAME").value==""){
			alert("请输入查询条件！");
			return false;
		}else if(document.getElementById("CMPNY_NAME").value!=""){
			var companyName = document.getElementById("CMPNY_NAME").value;
			document.getElementById("CMPNY_NAME").value = "%"+companyName+"%";
			document.myForm.action="listTravelAgc.?ta_travelagency@pageNO=1&ta_travelagency@pageSize=10&ywlb=<%=rd.getString("ywlb") %>";
			document.myForm.submit();
		}		
	}

	function enableInput(){
		var rs = false;
		var objs = document.myForm.elements;
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
	function deleteRow(){

		var rs = enableInput();
		if(rs == false){
			
			return false;
		}else if(confirm('此操作将无法恢复,确定删除所勾选项?')){
		document.getElementById("CMPNY_NAME").name = "other";
		document.getElementById("cmpnyNameOld").name = "other2";
		document.myForm.action="<%=request.getContextPath() %>/baseMng/travelAgc/deleteAll.?cityID=<%=rd.getStringByDI("TA_TRAVELAGENCY","city_id",0) %>&priID=<%=rd.getStringByDI("TA_TRAVELAGENCY","privince_id",0) %>";
		document.myForm.submit();
		}
	}

	//添加旅行社信息
	function newTravel()
	{
		window.location.href="<%=request.getContextPath()%>/baseMng/travelAgc/addTraAgc.jsp?cityID=<%=rd.getStringByDI("TA_TRAVELAGENCY","city_id",0) %>&priID=<%=rd.getStringByDI("TA_TRAVELAGENCY","priID",0) %>";
	}

	//修改旅行社信息
	function editTravel(url)
	{
		window.location.href=url;
	}
</script>
<body onkeydown="if(event.keyCode==13)  {event.keyCode=9};" >
<form name="myForm" method="post">
<div id="ex3a" class="jqmDialog">
<div style="margin:0 4px; background:#76C6CC; height:1px; overflow:hidden;"></div>
<div style="margin:0 2px; border:1px solid #76C6CC; border-width:0 2px; background:#76C6CC; height:1px; overflow:hidden;"></div>
<div style="margin:0 1px; border:1px solid #76C6CC; border-width:0 1px; background:#76C6CC; height:1px; overflow:hidden;"></div>
<div style="margin:0 1px; border:1px solid #76C6CC; border-width:0 1px; background:#76C6CC; height:1px; overflow:hidden;"></div>
<div style="background:#76C6CC; border:1px solid #76C6CC; border-width:0 1px;height: 200px;">
	<div style="font-size:12px; font-weight:bolder; font-family:Verdana; color:#006666; padding:2px 10px 5px;">
		查询条件
	</div>
	<div style="background:#FFF; margin:0 3px; font-size:11px; font-family:Verdana; padding:5px 10px; overflow:hidden;height: 180px;">
		 旅行社名称：<input type="text" name="ta_travelagency/CMPNY_NAME" id="CMPNY_NAME" class="text_input"/>&nbsp;
		 <input type="hidden" name="ta_travelagency/CMPNY_NAME[0]@relation" class="text_input" value="like" id="cmpnyNameOld"/>
		  <input type="button" value="查询"  onClick="findTravelagency();"/>
	</div>
</div>
<div style="margin:0 1px; border:1px solid #76C6CC; border-width:0 1px; background:#76C6CC; height:1px; overflow:hidden;"></div>
<div style="margin:0 1px; border:1px solid #76C6CC; border-width:0 2px; background:#76C6CC; height:1px; overflow:hidden;"></div>
<div style="margin:0 2px; border:1px solid #76C6CC; border-width:0 2px; background:#76C6CC; height:1px; overflow:hidden;"></div>
<div style="margin:0 4px; background:#76C6CC; height:1px; overflow:hidden;"></div> 
	<input type="image" src="<%=request.getContextPath() %>/images/if-select-img/close.gif" class="jqmdX jqmClose" />
</div>


<div id="lable"><span>旅行社基础信息维护</span></div>
<div id="top-select">
<div class="select-div" >
	  <span class="text" id="select-condition">查询条件</span>
</div>
<div class="select-div" onclick="newTravel()">
  <span id="add-icon"></span> 
  <span class="text">添加</span></div>
<div class="select-div" onclick="deleteRow();">
  <span id="del-icon"></span> 
  <span	class="text">删除</span>
</div>
</div>

<div id="list-table">
<table class="datas">
	<tr id="first-tr">
		<td width="3%" id="selectAll"><input type="checkbox" id="checkboxTop"></input></td>
		<td width="15%">公司全称</td>
        <td width="15%">公司地址</td>
        <td width="12%">总负责人-手机号码</td>
        <td width="12%">业务员-手机号码</td>	
        <td width="10%">业务类型</td>
        <td width="10%">操作</td>
	</tr>
	<%
		int rows = rd.getTableRowsCount("ta_travelagencys");
		for(int i=0;i<rows;i++){
			
			String traAgcID = rd.getStringByDI("ta_travelagencys","TRAVEL_AGC_ID",i);
	%>
		<tr>
			<td >
				
				<input type="checkbox" name="ta_travelagency/TRAVEL_AGC_ID[<%=i%>]" id="checkboxEle" value="<%=traAgcID %>"/>
			</td>
            <td >
            	<%=rd.getStringByDI("ta_travelagencys","CMPNY_NAME",i)%>
            </td>
	  		<td >
				<%=rd.getStringByDI("ta_travelagencys","CMPNY_ADDRESS",i)%>
			</td>
	  		<td >
				<%=rd.getStringByDI("ta_travelagencys","CHIEF_NAME",i)%>-<%=rd.getStringByDI("ta_travelagencys","CHIEF_MOBILE",i)%>
			</td>
            <td >
            	<%=rd.getStringByDI("ta_travelagencys","BUSINESS_NAME",i)%>-<%=rd.getStringByDI("ta_travelagencys","BUSINESS_MOBILE",i)%>
            </td>
            <td >
				<%
					if(rd.getStringByDI("ta_travelagencys","BUSINESS_TYPE",i).equals("1"))
						out.print("个人业务");
					if(rd.getStringByDI("ta_travelagencys","BUSINESS_TYPE",i).equals("2"))
						out.print("公司业务");
				%>
			</td>
            <td >
            	<a href="###" onclick="editTravel('<%=request.getContextPath()%>/baseMng/travelAgc/findTravelagencyById.?ac=edit&ta_travelagency/TRAVEL_AGC_ID=<%=traAgcID%>&ta_store/TRAVEL_AGC_ID=<%=traAgcID %>')">
					<img alt="编辑" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[修改]
				</a>-
            	<a href="javascript:del_byID('<%=rd.getStringByDI("ta_travelagencys","TRAVEL_AGC_ID",i)%>')">
					<img alt="删除" src="<%=request.getContextPath()%>/images/del-icon.gif">&nbsp;[删除]
				</a>
            </td>
         </tr>	
     <%} %> 
</table>
</div>
<div id="page">
	<%String url=request.getContextPath()+"/baseMng/travelAgc/listTravelagency.?ta_travelagency@pageSize=10&ta_travelagency@pageNO=";%>
	<span title="首页" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="上一页" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
					共<%=rd.getAttr("ta_travelagencys","rowsCount")==null?"0":rd.getAttr("ta_travelagencys","rowsCount") %> 条记录，
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
