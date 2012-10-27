<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@include file="../common.jsp" %>	
<html>
<link rel="stylesheet" href="<%=request.getContextPath() %>/jqueryui/themes/start/jqueryui.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/jquery/jquery.js"></script>
<script src="<%=request.getContextPath() %>/jqueryui/jqueryui.js" ></script>

<script type="text/javascript">
	$(function (){
			$( "#dialog-confirm" ).dialog({
				resizable: true,
				center:true,
				height:100,
				width:700,
				modal: true,
				buttons: {
					'取 消': function() {
						$( this ).dialog( "close" );
					}
				}
			});
	});



</script>
<style type="text/css">
<!--
 
* { margin:0; padding:0; word-wrap:break-word; word-break:break-all; }
 
body {
 background-image: url(<%=request.getContextPath() %>/images/welcome.gif);
 background-repeat: no-repeat;
 background-position: center;
}
-->
</style>
<head>

<link rel="stylesheet" type="text/css" href="/trm/css/index.css" />
</head>
<body>

<div id="dialog-confirm" title="登录提示" >
	<table style="font-size: 12px;">
	<tr>
		<td><font color="red">■</font>未实施&nbsp;&nbsp;<font color="green">■</font>已实施</td>
	</tr>
		<%
			int wRows = rd.getTableRowsCount("welcomeLogs");
			for(int i = 0; i < wRows; i++){
				String groupId = rd.getString("welcomeLogs","id",i);//团号
				int state = Integer.parseInt(rd.getString("welcomeLogs","state",i));//团状态
				String gw = rd.getString("welcomeLogs","gw",i);//购物
				String jd = rd.getString("welcomeLogs","jd",i);//加点
				String hoteltid = rd.getString("welcomeLogs","hoteltid",i);//酒店
				String cttid = rd.getString("welcomeLogs","cttid",i);//餐厅
				String cltid = rd.getString("welcomeLogs","cltid",i);//车辆
				String djtid = rd.getString("welcomeLogs","djtid",i);//地接
				String dytid = rd.getString("welcomeLogs","dytid",i);//导游
				String gwtid = rd.getString("welcomeLogs","gwtid",i);//购物
				String jdtid = rd.getString("welcomeLogs","jdtid",i);//景点
				String jiadtid = rd.getString("welcomeLogs","jiadtid",i);//加点
				String pwtid = rd.getString("welcomeLogs","pwtid",i);//票务
				//团状态：1：待计划；2：实施中；3：已实施/待审核；4、已审核/待报销；5：报销中；6：已报销/待清款 清款状态：Y已清款；N未清款
		%>
		<tr>
			<td>团号：<%=groupId %></td>
			<td>团状态：<%
					switch (state){  
					  case 1:
					 		out.print("待计划");
					  break;
					  case 2:
					  		out.print("实施中");
					  break;
					  case 3:
					  		out.print("待审核");
					  break;
					  case 4:
					  		out.print("待报销");
					  break;
					  case 5:
						  	out.print("待审核");
					  break;
					  case 6:
						 	out.print("已报销");
					  break;
					  default:
					  		out.print("error");
					}
					%></td>
			<td>酒店<%if("".equals(hoteltid)){%><font color="red">■</font><%}else{%><font color="green">■</font><%} %></td>
			<td>餐厅<%if("".equals(cttid)){%><font color="red">■</font><%}else{%><font color="green">■</font><%} %></td>
			<td>车辆<%if("".equals(cltid)){%><font color="red">■</font><%}else{%><font color="green">■</font><%} %></td>
			<td>地接<%if("".equals(djtid)){%><font color="red">■</font><%}else{%><font color="green">■</font><%} %></td>
			<td>导游<%if("".equals(dytid)){%><font color="red">■</font><%}else{%><font color="green">■</font><%} %></td>
			<td>景点<%if("".equals(jdtid)){%><font color="red">■</font><%}else{%><font color="green">■</font><%} %></td>
			<td>票务<%if("".equals(pwtid)){%><font color="red">■</font><%}else{%><font color="green">■</font><%} %></td>
			<td>购物<%if("".equals(gwtid)){%><font color="red">■</font><%}else{%><font color="green">■</font><%} %></td>
			<td>加点<%if("".equals(jiadtid)){%><font color="red">■</font><%}else{%><font color="green">■</font><%} %></td>
			</tr>
		<%} %>
	</table>
</div>

</body>
</html>