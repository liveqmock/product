<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryui/jqueryui.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/thickbox/thickbox.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>
<script type="text/javascript"> 
$(function(){
	$("input:submit,input:button").button();
	$("#cityName").bind("click",function(){
		TB_show('行政区划','<%=request.getContextPath() %>/showXZQH4Base.?xzqh/layer=2&xzqh/gnw=1*TB_iframe=true&height=380&width=350','');
	});
});
function addCarTeam(){
	document.carTeam_form.action="addCarTeam.";
	document.carTeam_form.submit();
}

</script>
<title>添加车队信息</title>
</head>
<body>
<form name="carTeam_form" method="post">
<div id="lable">
	<span>添加车队信息       带<font color="red">*</font>号为必填项</span>
</div>
	<table class="add-table" >
		<tr>
			<td align="right" >车队名称：</td>
			<td >
				<input type="hidden" name="TA_CAR_TEAM/CITY_ID" value="<%=rd.getString("city_id") %>"></input>
				<input type="text" name="TA_CAR_TEAM/CMPNY_NAME" ></input><span>*</span>
			</td>
			<td align="right" >公司地址：</td>
			<td >
			  <input type="hidden" name="TA_CAR_TEAM/CITY_ID" id="cityId"/>
			  <input type="text" name="xzqhname" id="cityName" class="smallInput" />
			  <input type="text" name="TA_CAR_TEAM/CMPNY_ADDRESS" id="CMPNY_ADDRESS"  />
			</td>
			<td align="right" >邮编：</td>
			<td ><input type="text" name="TA_CAR_TEAM/POST_CODE"/></td>
			
		</tr>
			<tr>
			<td align="right" >公司帐号：</td>
			<td ><input type="text" name="TA_CAR_TEAM/CMPNY_BANK_CODE"></input></td>
			<td align="right" >开户行名称：</td>
			<td >
			<select name="TA_CAR_TEAM/CMPNY_BANK_NAME" >
				<%int khyhRows=rd.getTableRowsCount("KHYH");for(int z=0;z<khyhRows;z++){ %>
				<option value="<%=rd.getStringByDI("KHYH","DMZ",z)%>"><%=rd.getStringByDI("KHYH","DMSM1",z)%></option>
				<%}%>
			</select>
			</td>
			<td align="right" >总负责人名字：</td>
			<td >
			<input type="text" name="TA_CAR_TEAM/CHIEF_NAME" id="CHIEF_NAME" maxlength="13"/>
			</td>
		</tr>
		<tr>
			<td align="right" >总负责人固定电话：</td>
			<td >
				<input type="text" name="TA_CAR_TEAM/CHIEF_TEL" id="CHIEF_TEL"  />
			</td>
			<td align="right" >总负责人手机：</td>
			<td >
			<input type="text" name="TA_CAR_TEAM/CHIEF_MOBILE" id="CHIEF_MOBILE" />
			</td>
			<td align="right" >总负责人传真：</td>
			<td >
			<input type="text" name="TA_CAR_TEAM/CHIEF_FAX" id="CHIEF_FAX"  />
			</td>
		</tr>
		<tr>
			<td align="right" >总负责人QQ：</td>
			<td >
				<input type="text" name="TA_CAR_TEAM/CHIEF_QQ" id="CHIEF_QQ"  />
			</td>
			<td align="right" >总负责人电子邮件：</td>
			<td >
			<input type="text" name="TA_CAR_TEAM/CHIEF_MAIL" id="CHIEF_MAIL"  />
			</td>
			<td align="right" >业务员名字：</td>
			<td >
			<input type="text" name="TA_CAR_TEAM/BUSINESS_NAME" id="BUSINESS_NAME" />
			</td>
		</tr>
		<tr>
			<td align="right" >业务员固定电话：</td>
			<td >
				<input type="text" name="TA_CAR_TEAM/BUSINESS_PHONE" id="BUSINESS_PHONE"  />
			</td>
			<td align="right" >业务员QQ：</td>
			<td >
			<input type="text" name="TA_CAR_TEAM/BUSINESS_QQ" id="BUSINESS_QQ"  />
			</td>
			<td align="right" >业务员传真：</td>
			<td >
			<input type="text" name="TA_CAR_TEAM/BUSINESS_FAX" id="BUSINESS_FAX" />
			</td>
		</tr>
		<tr>
			<td align="right" >业务员手机：</td>
			<td >
			<input type="text" name="TA_CAR_TEAM/BUSINESS_MOBILE" id="BUSINESS_MOBILE"  />
			</td>
			<td align="right" >业务员电子邮件：</td>
			<td >
				<input type="text" name="TA_CAR_TEAM/BUSINESS_MAIL" id="BUSINESS_MAIL"  />
			</td>
		</tr>
		<tr>
			<td align="right">备注：</td>
			<td colspan="5"><textarea rows="5" cols="50" name="TA_CAR_TEAM/DESCRIBES"></textarea><span>限50汉字以内</span> </td>
		</tr>
	</table>
	
  <div>
	<table class="datas">
	  <tr>
		<td align="center">
		  <input type="button" value="提    交" onclick="addCarTeam();"/>&nbsp;&nbsp;
		  <input type="button" value="返    回" onclick="window.history.go(-1)"/>
		</td>
	  </tr>
	</table>
  </div>
</form>	
</body>
</html>