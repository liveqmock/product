<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>

<%@page import="java.util.Date"%>

<%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/treeAndAllCss.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryui/jqueryui.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<title>营收清款管理</title>
<script type="text/javascript">

$(function(){
	$("input:submit,input:button").button();
});


$(function(){
	$("#yfk").focus();
	$("#yfk").css("background-color","inactivecaptiontext");
});
	function doSub(){
		if($("#BCQKJE").val() == ''){
			alert("必须输入本次收款金额！");
			return false;
		}
		document.myForm.action="updateBE.";
		document.myForm.submit();
		parent.TB_remove_refresh();
	}

	function closeW(){
		parent.TB_remove();
	}

</script>
</head>
<body>
<form name="myForm" method="post">
<div id="lable"><span >应收款清款</span></div>
<div id="bm-table">
  <div id="hotelDiv">	
	<table width="100%" class="datas">  
	  <tr>
	  	<td>团号：</td>
	  	<td><%=rd.getStringByDI("rsBE4Update","tid",0) %>
	  		<input type="hidden" name="TA_DJ_TZTSQKJL/ORGID" value="<%=sd.getString("orgid") %>" />
	  		<input type="hidden" name="TA_DJ_TZTSQKJL/USERNO" value="<%=sd.getString("userno") %>" />
	  		<input type="hidden" name="TA_DJ_TZTSQKJL/tid" value="<%=rd.getStringByDI("rsBE4Update","tid",0) %>" />
	  		<input type="hidden" name="TA_DJ_TZTSQKJL/tztsid" value="<%=rd.getStringByDI("rsBE4Update","id",0) %>" />
	  		
	  		<input type="hidden" name="TA_DJ_TZTS/ORGID[0]@oldValue" value="<%=sd.getString("orgid") %>" />
	  		<input type="hidden" name="TA_DJ_TZTS/id[0]@oldValue" value="<%=rd.getStringByDI("rsBE4Update","id",0) %>" />
	  	</td>
	  </tr>	
	  <tr>
	  	<td>组团社名称：</td>
		<td><%=rd.getStringByDI("rsBE4Update","ztsmc",0) %></td>
	  </tr>		
	  <tr>
	    <td>预算款：</td>
	    <td><%=rd.getStringByDI("rsBE4Update","YSZK",0) %> 元</td>
	  </tr>
	  <tr>
	    <td>预算款现付金额：</td>
	    <td><%=rd.getStringByDI("rsBE4Update","yfk",0) %> 元</td>
	  </tr>
	  <tr>
	    <td>预算款余款：</td>
	    <td><%=Integer.parseInt(rd.getStringByDI("rsBE4Update","YSZK",0)) - Integer.parseInt(rd.getStringByDI("rsBE4Update","yfk",0)) - Integer.parseInt(rd.getStringByDI("rsBE4Update","qkjehj",0)) %> 元</td>
	  </tr>
	  <tr>
	  	<td>累计收款金额：</td>
	  	<td><%=rd.getStringByDI("rsBE4Update","qkjehj",0) %> 元</td>
	  </tr>
	  <tr>	
		<td>本次收款金额：</td>
		<td><input type="text" name="TA_DJ_TZTSQKJL/BCQKJE" onkeydown="checkNumX()" id="BCQKJE"/>元</td>		
	  </tr>
	  <tr>
	    <td>备注：</td>
	    <td><textarea name="TA_DJ_TZTSQKJL/REMARK" rows="2"></textarea> </td>
	  </tr>
	  <tr>	
		<td>是否清款：</td>
		<td>
		  <input type="radio" name="TA_DJ_TZTS/QKZT" value="Y"/>是&nbsp;&nbsp;
		  <input type="radio" name="TA_DJ_TZTS/QKZT" checked="checked" value="N"/>否
		</td>		
	  </tr>
  	</table>
  </div>
</div>
<div>
	<table class="datas">
		<tr>
			<td align="center">
				<input type="button" value="提    交" onclick="doSub();"/>&nbsp;&nbsp;
				<input type="button" value="关    闭" onclick="closeW();"/>
			</td>
		</tr>				
	</table>
</div>
</form>
</body>
</html>
