<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>按团号查询</title>
<link href="../css/main.css" rel="stylesheet" type="text/css" />
<link href="../css/list_table.css" rel="stylesheet" type="text/css" />
<link href="../css/top_table.css" rel="stylesheet" type="text/css" />
<style type="text/css">
input{
	border:#91C0E3 1px solid;
	width:150px;
	}
</style>
<script type="text/javascript">
	function search(){
		var groupId = document.getElementById("groupId").value;
		document.getElementById("groupIdHidden").value= groupId;
		if(!document.getElementById("groupIdHidden").value||document.getElementById("groupIdHidden").value==''){
			alert("请输入正确的团号");
			return ;
		}

		document.SearchById.submit();
		
	}
</script>
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0"   class="top_table" width="100%" >
  <tr  class="toptable_pingpu">
    <td width="18%" height="35px"><strong><img src="../images/ico2.jpg">&nbsp;团队财务分析</strong></td>
    <td width="9%" ></td>
    <td width="12%"></td>
    <td width="12%"></td>
  </tr>
</table>
<form action="analysisByGroupId." method="post" name="SearchById">
<table border="0" cellpadding="1" cellspacing="1"  width="100%" class="tr_chaxun"  >
	<input id="groupIdHidden" type="hidden" name="GROUP_ID"/>
	<input type="hidden" name="DMSM" value="<%=request.getParameter("DMZ") %>"/>
	<tr>
			<td width="15%" >&nbsp;</td>
	  		<td width="20%"  class="text_algin">按团号查询：</td>
            <td width="16%" ><input type="text" name="textfield" id="groupId"></td>
	  		<td width="34%" ><input type="button"  value="查询"  class="bnt" style="width:50px" onClick="search()"/></td>
	  		<td width="15%" >&nbsp;</td>
	</tr>
</table> 
</form>
</body>
</html>