<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>保险信息</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryui/jqueryui.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/treeAndAllCss.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css" />

<script type="text/javascript">
	$(function(){
		$("input:submit,input:button").button();
	});
	function commit(){
		var vl = 0;
		$("input").each(function(){
			if (this.value == "")
			{
				vl=1;
			}
		});
		if(vl == '1')
		{
			alert("保险信息未填写完整！！！");
			return false; 
		}else{
			document.insuranceFile.submit();
		}
		
	}
	
</script>

</head>

<body >
<form action="submitInsuranceFile.?" name="insuranceFile" method="post">
<div id="lable">
	<span>添加保险档案信息       带<font color="red">*</font>号为必填项</span>
</div>

<%
int tRows = rd.getTableRowsCount("TA_ZT_INSURANCEs");
if(tRows ==0 ) {
%>
	<table class="add-table" >
	  <tr >
		<td align="right">保险名称：</td>
  		<td>
		  	<input type="text" name="TA_ZT_INSURANCE/BXLBMC" value="" maxlength="100"/>
		  	<span>*</span> 
	  	</td>
		<td  align="right">保险成本：</td>
        <td>
		 	<input type="text" name="TA_ZT_INSURANCE/BXCB" value="" maxlength="10"  onkeydown="checkNumX()"/>
		</td>
	  </tr>	
	  <tr>
		<td  align="right">建议价格：</td>
        <td>
		 	<input type="text" name="TA_ZT_INSURANCE/JYJG" value="" maxlength="10" onkeydown="checkNumX()"/>
		</td>
		<td  align="right">成本方式：</td>
        <td>
        	 <input type="checkbox" name="TA_ZT_INSURANCE/CBFS" value="1" />是否按天计算
		</td>
	  </tr> 
	</table>
<%}else{ %>
	<table class="add-table" >
	  <tr >
		<td align="right">保险名称：</td>
  		<td>
		  	<input type="text" name="TA_ZT_INSURANCE/BXLBMC" value="<%=rd.getStringByDI("TA_ZT_INSURANCEs","BXLBMC",0)%>" maxlength="100"/>
		  	<input type="hidden" name="TA_ZT_INSURANCE/ID" value="<%=rd.getStringByDI("TA_ZT_INSURANCEs","ID",0)%>"/>
		  	<span>*</span> 
	  	</td>
		<td  align="right">保险成本：</td>
        <td>
		 	<input type="text" name="TA_ZT_INSURANCE/BXCB" value="<%=rd.getStringByDI("TA_ZT_INSURANCEs","BXCB",0)%>" maxlength="10" onkeydown="checkNumX()"/>
		</td>
	  </tr>	
	  <tr>
		<td  align="right">建议价格：</td>
        <td>
		 	<input type="text" name="TA_ZT_INSURANCE/JYJG" value="<%=rd.getStringByDI("TA_ZT_INSURANCEs","JYJG",0)%>" maxlength="10" onkeydown="checkNumX()"/>
		</td>
		<td  align="right">成本方式：</td>
        <td><%
        		String CBFS = rd.getStringByDI("TA_ZT_INSURANCEs","CBFS",0);
        	%>
        	 <input type="checkbox" name="TA_ZT_INSURANCE/CBFS" value="1" <%if("1".equals(CBFS)){ %>checked="checked" <%}%>/>是否按天计算
		</td>
	  </tr>	  
	</table>
<%} %>

  <div>
	<table class="datas">
	  <tr>
		<td align="center">
		  <input type="button" value="提    交" onclick="commit();"/>&nbsp;&nbsp;
		  <input type="button" value="返    回" onclick="window.history.go(-1)"/>
		</td>
	  </tr>
	</table>
  </div>
</form>

</body>
</html>
