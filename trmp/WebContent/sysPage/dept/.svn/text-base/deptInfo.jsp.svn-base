<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>部门信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<%
String msg = rd.getString("msg");
%>
<script type="text/javascript">
	function save(){
		
		document.deptForm.submit();
	}

	function showMsg(){

		<%if("Y".equals(msg)){%>
			alert("修改成功！");
		<%}%>
	}
</script>
</head>

<body onload="showMsg();">

<form name="deptForm" method="post" action="updateDept.">
<input type="hidden" name="fwd" value="info"/>
<input type="hidden" name="HRDEPARTMENT/DEPTID" value="<%=rd.getString("HRDEPARTMENTs","DEPTID",0)%>"/>
<input type="hidden" name="PDEPTID" value="<%=rd.getString("HRDEPARTMENT","PDEPTID",0)%>"/>
<input type="hidden" name="HRDEPARTMENT/PDEPTID" value="<%=rd.getString("HRDEPARTMENTs","PDEPTID",0)%>"/>
<input type="hidden" name="HRDEPARTMENT/DEPTID[0]@oldValue" value="<%=rd.getString("HRDEPARTMENTs","DEPTID",0)%>"/>
<div id="lable"><span><%=rd.getString("HRDEPARTMENTs","DEPTNAME",0)%>的基本信息如下:</span></div>
<div id="list-table">
  <table class="add-table">
    <tr>
      <td>部门名称：</td>
      <td><input type="text" name="HRDEPARTMENT/DEPTNAME" value="<%=rd.getString("HRDEPARTMENTs","DEPTNAME",0)%>"/></td> 
      <td>部门电话：</td>
      <td><input type="text" name="HRDEPARTMENT/DEPTTEL" value="<%=rd.getString("HRDEPARTMENTs","DEPTTEL",0)%>"/></td>
    </tr>
    <tr>
      <td>负责人姓名：</td>
      <td><input type="text" value="<%=rd.getString("HRDEPARTMENTs","CHIEF_NAME",0)%>" name="HRDEPARTMENT/CHIEF_NAME"/></td>
      <td>负责人手机：</td>      
      <td><input type="text" value="<%=rd.getString("HRDEPARTMENTs","CHIEF_MOBILE",0)%>" name="HRDEPARTMENT/CHIEF_MOBILE"/></td>
  </tr>
  <tr>
  	  <td>负责人传真：</td>
      <td><input type="text" value="<%=rd.getString("HRDEPARTMENTs","CHIEF_FAX",0)%>" name="HRDEPARTMENT/CHIEF_FAX"/></td>
      <td>负责人QQ：</td>      
      <td><input type="text" value="<%=rd.getString("HRDEPARTMENTs","qq",0)%>" name="HRDEPARTMENT/qq"/></td>
  </tr>
  <tr>
  	<td>是否是办事处：</td>
  	<td>
  	<%
  		String sfbsc = rd.getStringByDI("HRDEPARTMENTs","SFBSC",0);
  		if("Y".equals(sfbsc)){
  	%>
  		<input type="radio" name="HRDEPARTMENT/SFBSC" value="Y" checked="checked"/>是
  		<input type="radio" name="HRDEPARTMENT/SFBSC" value="N"/>否
  	<%
  		}else{
  	%>
  		<input type="radio" name="HRDEPARTMENT/SFBSC" value="Y"/>是
  		<input type="radio" name="HRDEPARTMENT/SFBSC" value="N" checked="checked"/>否
  	<%
  		}
  	%>
  		
  	</td>
  	<td colspan="2" align="left"><font color="red">*</font>新增加的部门如果是办事处，请务必选择“是”</td>
  </tr>
</table>
</div>

  <div align="center"> 
	<input type="button" id="button" value="保 存" onclick="save();"/>&nbsp; 
	<input type="reset" id="button" value="重 置"/>&nbsp; 
		
  </div>
</form>

</body>
</html>