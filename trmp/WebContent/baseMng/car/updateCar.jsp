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
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css">
<script type="text/javascript">
$(function(){
	$("input:submit,input:button").button();
});
function updateCar(){
	document.car_form.action="updateCar.?";
	document.car_form.submit();
}
</script>
<title>�޸ĳ���������Ϣ</title>
</head>
<body>
<form name="car_form" method="post">
<div id="lable">
	<span>��ӳ�����Ϣ       ��<font color="red">*</font>��Ϊ������</span>
</div>
	<table class="add-table" >
		<tr>
			<td align="right" >˾��������</td>
			<td >
				<input type="hidden" name="TA_CAR/CAR_ID" value="<%=rd.getStringByDI("TA_CARs","CAR_ID",0) %>">
				<input type="hidden" name="TA_CAR/CAR_ID[0]@oldValue" value="<%=rd.getStringByDI("TA_CARs","CAR_ID",0) %>">
				<input type="hidden" name="TA_CAR/TEAM_ID" value="<%=rd.getStringByDI("TA_CARs","TEAM_ID",0) %>">
				<input type="text" name="TA_CAR/DRIVER_NAME" value="<%=rd.getStringByDI("TA_CARs","DRIVER_NAME",0) %>"></input><span>*</span>
			</td>
			<td align="right" >˾���绰��</td>
			<td >
			<input type="text" name="TA_CAR/DRIVER_PHONE" id="DRIVER_PHONE"  value="<%=rd.getStringByDI("TA_CARs","DRIVER_PHONE",0) %>"/>
			</td>
			</tr>
		<tr>
			<td align="right" >���ƺ��룺</td>
			<td ><input type="text" name="TA_CAR/CAR_CODE" value="<%=rd.getStringByDI("TA_CARs","CAR_CODE",0) %>"/><span>*</span></td>
		
			<td align="right" >�������ͣ�</td>
			<td>
				<select name="TA_CAR/CAR_TYPE">
				<%int cllxRows=rd.getTableRowsCount("CLLX");for(int c=0;c<cllxRows;c++){ %>
					<option value="<%=rd.getStringByDI("CLLX","DMZ",c) %>" <%if(rd.getStringByDI("CLLX","DMZ",c).equals(rd.getStringByDI("TA_CARs","CAR_TYPE",0)))out.print("selected='selected'"); %>><%=rd.getStringByDI("CLLX","DMSM1",c) %></option>
					<%} %>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">����ʱ�䣺</td>
			<td><input onFocus="WdatePicker({isShowClear:false,readOnly:true});" name="TA_CAR/BUY_DATE" value="<%=rd.getStringByDI("TA_CARs","BUY_DATE",0) %>"/></td>
		</tr>
	</table>
	
  <div>
	<table class="datas">
	  <tr>
		<td align="center">
		  <input type="button" value="��    ��" onclick="updateCar();"/>&nbsp;&nbsp;
		  <input type="button" value="��    ��" onclick="window.history.go(-1)"/>
		</td>
	  </tr>
	</table>
  </div>
</form>	
</body>
</html>