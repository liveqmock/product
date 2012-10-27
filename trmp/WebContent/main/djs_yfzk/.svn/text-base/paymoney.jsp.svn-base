<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<title>地接社付款</title>
<script type="text/javascript">
$(document).ready(function(){
	$("#newMoney").click(function(){
		var num=$("#trave_money").find("tr").length-1;
		$("#trave_money").append('<tr ><td><input name="TA_ZTFK_PAYDETAIL/KXMC['+num+']"/></td>'+
				'<td><input onkeydown="checkNum()" class="money" value="0" onchange="sumPrice()" name="TA_ZTFK_PAYDETAIL/FKJE['+num+']"/></td>'+
				'<td><input name="TA_ZTFK_PAYDETAIL/FKRQ['+num+']" class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td></tr>');
		});
});
function sumPrice(){
	var tmp=0;
	$(".money").each(function(){
		tmp+=parseInt(this.value);
		});
	$("#zj").val(parseInt(tmp));
}
function addPayMoney(){
	document.paymoney_form.action="addPayMoney.";
	document.paymoney_form.submit();
	window.parent.parent.location.reload(); 
	window.parent.parent.GB_hide(); 
}
</script>
</head>
<body onload="sumPrice()">
<form name="paymoney_form" method="post">
<div id="top-select">
	<div class="select-div" onclick="addPayMoney();">
	  <span id="ok-icon"></span> 
	  <span class="text">提交</span>
	</div>
	<div class="select-div" >
	  <span id="reset-icon"></span> 
	  <span	class="text">重置</span>
	</div>
	<div class="select-div" onclick="javascript:window.parent.parent.GB_hide();">
	  <span id="close-icon"></span> 
	  <span	class="text">关闭</span>
	</div>
	<span class="tishi">带<font color="red">*</font>号为必填项</span>
</div>
<table width="100%">
	<tr>
       <td align="right" width="90%">
         <span id="newMoney" class="showPointer"><img alt="添加" src="/trm/images/add.gif" /> 添加</span>&nbsp;&nbsp;
       	 <span onclick="delTabRow('trave_money',1,'');" class="showPointer"><img alt="删除" src="/trm/images/del.gif"> 删除</span>
       </td>
    </tr>
</table>
<div id="list-table">
	<table class="datas" style="text-align: center" id="trave_money">
		<tr id="first-tr">
			<td>款项名称</td>
			<td>付款金额</td>
			<td>付款日期</td>
		</tr>
		<%
		int rows=rd.getTableRowsCount("payMoenyList");
			if(rows>0){
				out.print("<input type='hidden' value='up' name='state'>");
				for(int r=0;r<rows;r++){
		%>
		<tr >
			<td><input value="<%=rd.getStringByDI("payMoenyList","kxmc",r) %>" name="TA_ZTFK_PAYDETAIL/KXMC[<%=r %>]" /></td>
			<td><input value="<%=rd.getStringByDI("payMoenyList","fkje",r) %>" class="money" onchange="sumPrice()" name="TA_ZTFK_PAYDETAIL/fkje[<%=r %>]" /></td>
			<td><input value="<%=rd.getStringByDI("payMoenyList","FKRQ",r) %>" class="Wdate" name="TA_ZTFK_PAYDETAIL/FKRQ[<%=r %>]" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		</tr>
		<%}}else{ %>
		<tr >
			<td><input name="TA_ZTFK_PAYDETAIL/KXMC[0]"/></td>
			<td><input name="TA_ZTFK_PAYDETAIL/FKJE[0]" value="0" class="money" onchange="sumPrice()" onkeydown="checkNum()"/></td>
			<td><input name="TA_ZTFK_PAYDETAIL/FKRQ[0]" class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		</tr>
		<%} %>
	</table>
	<table class="datas" style="margin-top: 3px;">
	<tr>
		<td align="right">
			<font color="red">总计：</font>   <input  type="text"   readonly="readonly" id="zj" name="TA_G_NEXT_AGENCY/YFZK"/>/元
		</td>
	</tr>
</table>
</div>
<input name="userno" value="<%=sd.getString("userno") %>" type="hidden"></input>	
<input name="TA_G_NEXT_AGENCY/ID" value="<%=rd.getString("id") %>" type="hidden"></input>
<input name="TA_G_NEXT_AGENCY/ID[0]@oldValue" value="<%=rd.getString("id") %>" type="hidden"></input>
</form>	
</body>
</html>