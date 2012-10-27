<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>利润分析</title>
<link href="../css/main.css" rel="stylesheet" type="text/css" />
<link href="../css/list_table.css" rel="stylesheet" type="text/css" />
<link href="../css/top_table.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/main/workflow/js/prototype.js"></script>
<script src="<%=request.getContextPath()%>/main/workflow/js/linkage.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/main/workflow/js/meiziDate.js"></script>
<script type="text/javascript">
var _setDay = function(id){
	 return function(){
		 WdatePicker({isShowClear:false,readOnly:true});
	 };
};
function doSUB(){

	var groupID = document.getElementById("groupID").value;
	
	if(groupID != ''){
		window.location.href="http://192.168.0.2:8080/reports/preview?__report=report/Profitability/Profit.rptdesign&groupID="+groupID;
	}
}
</script>
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0"   class="top_table" width="100%" >
  <tr  class="toptable_pingpu">
    <td width="18%" height="35px"><strong><img src="../images/ico2.jpg">&nbsp;利润分析</strong></td>
    <td width="9%" ></td>
    <td width="12%"></td>
    <td width="12%"></td>
  </tr>
</table>
<table border="0" cellpadding="1" cellspacing="1"  width="100%" class="tr_chaxun"  >
  <tr>
    <td width="16%" height="22" >&nbsp;</td>
    <td width="10%" >按团号查询：</td>
    <td width="17%" ><input type="text" name="groupID" id="groupID"></td>
    <td width="13%" >&nbsp;</td>
    <td width="11%" >&nbsp;</td>
    <td width="33%" ><input type="button"  value="查询" onclick="doSUB();" class="bnt" style="width:50px"/></td>
  </tr>
</table>
</body>
</html>