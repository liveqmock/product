<%@ page contentType="text/html; charset=GBK"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryui/jqueryui.js" ></script>
<script type="text/javascript" src="/trmp/jquery/public-jq.js"></script>
<script type="text/javascript" src="/trmp/jquery/jqModal.js"></script>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/treeAndAllCss.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css"/>

<title>节点计划</title>

<script type="text/javascript">

	$(function(){
		$("input:submit,input:button").button();
	});
	function addNode(){
		var num=jQuery(".integerType").size();
		if(jQuery("#integer_name"+(num-1)).val()==""){
			alert("请填写完整后再添加");
			return false;
	}
		jQuery("#integer").append(
			'<tr class="integerType">'+
			'<td><input name="TA_FLOWNODE/NODENAME['+num+']"/></td>'+
			'<td><input name="TA_FLOWNODE/NODEDESC['+num+']"/></td>'+
			'<td>'+
			'<select id="dyid'+num+'" name="TA_FLOWNODE/ROLEID['+num+']" >'+	
			<%	
			for(int i=0;i<rd.getTableRowsCount("selectAllrole");i++){	
		    %>
		    '<option value="<%=rd.getStringByDI("selectAllrole","ROLEID",i) %>"><%=rd.getStringByDI("selectAllrole","ROLENAME",i) %></option>'+
		<%} %>
			'</select>'+
			'<input type="hidden" name="TA_FLOWNODE/orgid['+num+']" value="<%=sd.getString("orgid") %>"/></td>'+
			'<input type="hidden" name="TA_FLOWNODE/NODESTATE['+num+']"/></td>'+
			'<input type="hidden" type="text" id="nodeid'+num+'" name="TA_FLOWNODE/NODEID['+num+']" >'+
			'<input type="hidden" name="TA_FLOWNODE/PID['+num+']" id="pid'+num+'">'+
			'<input type="hidden" name="TA_FLOWNODE/DEFINITIONID['+num+']" value="<%=rd.getStringByDI("TA_FLOWNODEs","DEFINITIONID",0).equals("")?rd.getString("DEFINITIONID"):rd.getStringByDI("TA_FLOWNODEs","DEFINITIONID",0) %>"/></td>'+
		  	'</tr>');
		//alert(jQuery("#guide").html());
		
		//调用添加行的function
   		
		//调用AJAX取最大ID
		var obj=jQuery.ajax({url:"creatMaxNodeId.",
			async:false,
			dataType:"json",
			cache:false,
			success:function(data){
				//给已经经添加的行的下一个ID付值
				var maxid = data[0].maxid;
				jQuery("#nodeid"+num).val(maxid);
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
	            alert(errorThrown); 
	   	 	}
		});

		$("#pid"+parseInt(num)).val($("#nodeid"+parseInt(num-1)).val());
	}
	
	function delTableRow(){
		var num=jQuery(".integerType").size();
		if(num>1){
			//var idx=parseInt($("tr.valueType").size()-1);
			var idx=num-1;
			$("tr.integerType:eq("+idx+")").remove();
			//alert(jQuery("#guide").html());
		}
	}
	
	function addInteger(){
		document.integer_form.action="updateNode.?";
		document.integer_form.submit();
	}
	function setDyxm(idx){
		jQuery("tr.integerType").each(function(i){
			var dyxm=document.getElementById("dyid"+i).options[document.getElementById("dyid"+i).selectedIndex].text;
			document.getElementById("dyxm"+i).value=dyxm;
			//alert(dyxm);
		});	
	}
</script>
</head>

<body>
<form name="integer_form" action="addNode." method="post">
<div id="lable"><span >流程节点管理</span></div>
<div id="top-select">
	<div class="select-div" onclick="addNode();">
	 <span id="add-icon"></span>
	 <span class="text">添加</span>
	</div>
	<div class="select-div" onclick="delTableRow();">
	  <span id="del-icon"></span><span class="text">删除</span>
	</div>
</div>
<div id="bm-table">
<div id="integerDiv">
<table id="integer" class="datas" width="98%"  style="text-align:center">
  <tr id="first-tr">
	<td>节点名称</td>
	<td>节点描述</td>
	<td>参与者</td>
  </tr>


  <%
     	if(rd.getTableRowsCount("TA_FLOWNODEs")>0){
			List<BizData> list=new ArrayList<BizData>();
			list=(List)rd.get("list");
			for(int i=0; i<list.size();i++){
				BizData rd2=(BizData)list.get(i);
				int rows = rd2.getTableRowsCount("TA_FLOWNODEs");
				for(int k=0;k<rows;k++){
					String name=rd2.getString("TA_FLOWNODEs","NODENAME",k);
			  		String desc=rd2.getString("TA_FLOWNODEs","NODEDESC",k);
			  		String state=rd2.getString("TA_FLOWNODEs","NODESTATE",k);
			  		String role=rd2.getString("TA_FLOWNODEs","ROLEID",k);
			  		String nodeID=rd2.getString("TA_FLOWNODEs","nodeID",k);
			  		String pid=rd2.getString("TA_FLOWNODEs","pid",k);
					String orgid = rd.getString("TA_FLOWNODEs","orgid",k);
  %>
  <tr class="integerType">
  
  	<td><input name="TA_FLOWNODE/NODENAME[<%=i%>]" id="integer_name<%=i%>" value="<%=name %>"/></td>
  	<td><input name="TA_FLOWNODE/NODEDESC[<%=i%>]" id="integer_desc<%=i%>" value="<%=desc %>"/></td>
  	<td>
  		<select id="dyid<%=i%>" name="TA_FLOWNODE/ROLEID[<%=i%>]" >

  			<%
					for(int j=0;j<rd.getTableRowsCount("SelectAllrole");j++){
					String ROLEID = rd.getString("selectAllrole","ROLEID",j);
					String ROLENAME = rd.getString("selectAllrole","ROLENAME",j);
			%>
			<option value="<%=ROLEID %>" <%if(role.equals(ROLEID)){ %> selected="selected" <%} %>><%=ROLENAME %></option>
	<%
	} %>
		</select>
		<input type="hidden" name="TA_FLOWNODE/orgid[<%=i%>]" value="<%=orgid %>"/>
		<input type="hidden" name="TA_FLOWNODE/NODESTATE[<%=i%>]" id="integer_state<%=i%>" value="<%=state %>"/>
		<input type="hidden" name="TA_FLOWNODE/NODEID[<%=i%>]" id="nodeid<%=i%>" value="<%=nodeID %>"/>
		<input type="hidden" name="TA_FLOWNODE/PID[<%=i%>]" value="<%=pid %>">
		<input type="hidden" name="TA_FLOWNODE/DEFINITIONID[<%=i%>]" value="<%=rd.getStringByDI("TA_FLOWNODEs","DEFINITIONID",0).equals("")?rd.getString("DEFINITIONID"):rd.getStringByDI("TA_FLOWNODEs","DEFINITIONID",0) %>" />
	</td>
  </tr>

  <%	}
	 }
}

 

	else
	{
	 %>
   <tr class="integerType">
  
  	<td><input name="TA_FLOWNODE/NODENAME[0]" id="integer_name0"/></td>
  	<td><input name="TA_FLOWNODE/NODEDESC[0]" id="integer_desc0" /></td>
  	<td>

		<select id="dyid0" name="TA_FLOWNODE/ROLEID[0]" >	
		   <%
		        String role=rd.getStringByDI("TA_FLOWNODE","ROLEID",0);
				for(int j=0;j<rd.getTableRowsCount("selectAllrole");j++){
					String userNO = rd.getStringByDI("selectAllrole","ROLEID",j);
			%>
		<option value="<%=userNO %>"><%=rd.getStringByDI("selectAllrole","ROLENAME",j) %> </option>
		<%} %>
		</select>
		<input type="hidden" name="TA_FLOWNODE/orgid[0]" value="<%=sd.getString("orgid") %>"/>
		<input type="hidden" name="TA_FLOWNODE/NODESTATE[0]" id="integer_state0" />
		<input type="hidden" name="TA_FLOWNODE/NODEID[0]" id="nodeid0" value="<%=rd.getString("maxid")%>"/>
		<input type="hidden" name="TA_FLOWNODE/PID[0]" value="-1" >
		<input type="hidden" name="TA_FLOWNODE/DEFINITIONID[0]" value="<%=rd.getStringByDI("TA_FLOWNODEs","DEFINITIONID",0).equals("")?rd.getString("DEFINITIONID"):rd.getStringByDI("TA_FLOWNODEs","DEFINITIONID",0) %>" />
	</td>
  </tr>
  <%} %>
</table>
</div>
</div>
<div>
	<table class="datas">
		<tr>
			<td align="center">
				<input type="button" value="提    交" onclick="addInteger();"/>&nbsp;&nbsp;
				<input type="button" value="返    回" onclick="javascript:history.go(-1);"/>
			</td>
		</tr>				
	</table>
</div>
</form>
</body>
</html>