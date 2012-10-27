<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
   <%@include file="/common.jsp" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryui/jqueryui.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/thickbox/thickbox.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>
<title>修改车队信息</title>
<script type="text/javascript">
	$(function(){
		$("input:submit,input:button").button();
		$("#cityName").bind("click",function(){
			TB_show('行政区划','<%=request.getContextPath() %>/showXZQH4Base.?xzqh/layer=2&xzqh/gnw=1*TB_iframe=true&height=380&width=350','');
		});
	});
	function upCarTeam(){
		document.carTeam_form.action="upCarTeam.?";
		document.carTeam_form.submit();
		}
</script>
</head>
<body>
<form name="carTeam_form" method="post">
<div id="lable">
	<span>修改车队信息       带<font color="red">*</font>号为必填项</span>
</div>
	<table class="add-table" >
		<tr>
			<td align="right" >车队名称：</td>
			<td >
				<input type="hidden" name="TA_CAR_TEAM/TEAM_ID" value="<%=rd.getStringByDI("TA_CAR_TEAMs","TEAM_ID",0)%>"></input>
				<input type="hidden" name="TA_CAR_TEAM/TEAM_ID[0]@oldValue" value="<%=rd.getStringByDI("TA_CAR_TEAMs","TEAM_ID",0)%>"></input>
				<input type="text" name="TA_CAR_TEAM/CMPNY_NAME" value="<%=rd.getStringByDI("TA_CAR_TEAMs","CMPNY_NAME",0)%>"></input><span>*</span>
			</td>
			<td align="right" >公司地址：</td>
			<td >
			  <input type="hidden" name="TA_CAR_TEAM/CITY_ID" id="cityId" value="<%=rd.getStringByDI("TA_CAR_TEAMs","CITY_ID",0)%>"/>
			  <input type="text" name="xzqhname" id="cityName" class="smallInput" value="<%=rd.getStringByDI("TA_CAR_TEAMs","name",0)%>"/>
			  <input type="text" name="TA_CAR_TEAM/CMPNY_ADDRESS" id="CMPNY_ADDRESS" value="<%=rd.getStringByDI("TA_CAR_TEAMs","CMPNY_ADDRESS",0)%>" />
			</td>
			<td align="right" >邮编：</td>
			<td ><input type="text" name="TA_CAR_TEAM/POST_CODE" value="<%=rd.getStringByDI("TA_CAR_TEAMs","POST_CODE",0)%>"/></td>
			
		</tr>
			<tr>
			<td align="right" >公司帐号：</td>
			<td ><input type="text" name="TA_CAR_TEAM/CMPNY_BANK_CODE" value="<%=rd.getStringByDI("TA_CAR_TEAMs","CMPNY_BANK_CODE",0)%>"></input></td>
			<td align="right" >开户行名称：</td>
			<td >
			<select name="TA_CAR_TEAM/CMPNY_BANK_NAME" >
				<%int khyhRows=rd.getTableRowsCount("KHYH");for(int z=0;z<khyhRows;z++){ %>
				<option value="<%=rd.getStringByDI("KHYH","DMZ",z)%>" <%if(rd.getStringByDI("KHYH","DMZ",z).equals(rd.getStringByDI("TA_CAR_TEAMs","CMPNY_BANK_NAME",0)))out.print("selected='selected'"); %>><%=rd.getStringByDI("KHYH","DMSM1",z)%></option>
				
				<%}%>
			</select>
			</td>
			<td align="right" >总负责人名字：</td>
			<td >
			<input type="text" name="TA_CAR_TEAM/CHIEF_NAME" value="<%=rd.getStringByDI("TA_CAR_TEAMs","CHIEF_NAME",0)%>" id="CHIEF_NAME" maxlength="13"/>
			</td>
		</tr>
		<tr>
			<td align="right" >总负责人固定电话：</td>
			<td >
				<input type="text" name="TA_CAR_TEAM/CHIEF_TEL" id="CHIEF_TEL" value="<%=rd.getStringByDI("TA_CAR_TEAMs","CHIEF_TEL",0)%>" />
			</td>
			<td align="right" >总负责人手机：</td>
			<td >
			<input type="text" name="TA_CAR_TEAM/CHIEF_MOBILE" id="CHIEF_MOBILE" value="<%=rd.getStringByDI("TA_CAR_TEAMs","CHIEF_MOBILE",0)%>" />
			</td>
			<td align="right" >总负责人传真：</td>
			<td >
			<input type="text" name="TA_CAR_TEAM/CHIEF_FAX" id="CHIEF_FAX"  value="<%=rd.getStringByDI("TA_CAR_TEAMs","CHIEF_FAX",0)%>"/>
			</td>
		</tr>
		<tr>
			<td align="right" >总负责人QQ：</td>
			<td >
				<input type="text" name="TA_CAR_TEAM/CHIEF_QQ" id="CHIEF_QQ"  value="<%=rd.getStringByDI("TA_CAR_TEAMs","CHIEF_QQ",0)%>"/>
			</td>
			<td align="right" >总负责人电子邮件：</td>
			<td >
			<input type="text" name="TA_CAR_TEAM/CHIEF_MAIL" id="CHIEF_MAIL"  value="<%=rd.getStringByDI("TA_CAR_TEAMs","CHIEF_MAIL",0)%>"/>
			</td>
			<td align="right" >业务员名字：</td>
			<td >
			<input type="text" name="TA_CAR_TEAM/BUSINESS_NAME" id="BUSINESS_NAME" value="<%=rd.getStringByDI("TA_CAR_TEAMs","BUSINESS_NAME",0)%>" />
			</td>
		</tr>
		<tr>
			<td align="right" >业务员固定电话：</td>
			<td >
				<input type="text" name="TA_CAR_TEAM/BUSINESS_PHONE" id="BUSINESS_PHONE"  value="<%=rd.getStringByDI("TA_CAR_TEAMs","BUSINESS_PHONE",0)%>"/>
			</td>
			<td align="right" >业务员QQ：</td>
			<td >
			<input type="text" name="TA_CAR_TEAM/BUSINESS_QQ" id="BUSINESS_QQ" value="<%=rd.getStringByDI("TA_CAR_TEAMs","BUSINESS_QQ",0)%>"/>
			</td>
			<td align="right" >业务员传真：</td>
			<td >
			<input type="text" name="TA_CAR_TEAM/BUSINESS_FAX" id="BUSINESS_FAX" value="<%=rd.getStringByDI("TA_CAR_TEAMs","BUSINESS_FAX",0)%>"/>
			</td>
		</tr>
		<tr>
			<td align="right" >业务员手机：</td>
			<td >
			<input type="text" name="TA_CAR_TEAM/BUSINESS_MOBILE" id="BUSINESS_MOBILE"  value="<%=rd.getStringByDI("TA_CAR_TEAMs","BUSINESS_MOBILE",0)%>"/>
			</td>
			<td align="right" >业务员电子邮件：</td>
			<td >
				<input type="text" name="TA_CAR_TEAM/BUSINESS_MAIL" id="BUSINESS_MAIL"  value="<%=rd.getStringByDI("TA_CAR_TEAMs","BUSINESS_MAIL",0)%>"/>
			</td>
		</tr>
		<tr>
			<td align="right">备注：</td>
			<td colspan="5"><textarea rows="5" cols="50" name="TA_CAR_TEAM/DESCRIBES"><%=rd.getStringByDI("TA_CAR_TEAMs","DESCRIBES",0)%></textarea><span>限50汉字以内</span> </td>
		</tr>
	</table>

  <div>
	<table class="datas">
	  <tr>
		<td align="center">
		  <input type="button" value="提    交" onclick="upCarTeam();"/>&nbsp;&nbsp;
		  <input type="button" value="返    回" onclick="window.history.go(-1)"/>
		</td>
	  </tr>
	</table>
  </div>
</form>	
</body>
</html>