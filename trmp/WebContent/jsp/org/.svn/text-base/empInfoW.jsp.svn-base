<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Date"%>
<%@include file="../common.jsp"%>
<%
	/**
		Get the request parameters;
	*/
	int mode =0;// 0- new ,1,Update,2-readonly;
	String dispMode = request.getParameter("dispMode");
	if(dispMode == null){
		dispMode = "N";
	}
	if(dispMode.compareTo("U") ==0){
		mode = 1;
	}
	else if(dispMode.compareTo("R")==0){
		mode = 2;
	}	
	String empID= request.getParameter("empID");
	String orgID=request.getParameter("HROrganization/orgID");
	if(empID==null){
		empID=rd.getString("HREmployee","empID",0);
	}
	if(orgID==null){
		orgID=rd.getString("HREmployee","orgID",0);
	}
%>
<html>
<head>
<title>员工信息</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/menu.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/forms.js"></script>
<script type="text/javascript">
function getDOB(){
		var EMP_ID = document.getElementById("EMP_ID").value;
		switch(EMP_ID.length){
		case 15:{
			if(parseInt(EMP_ID.substr(6,2))+1900%4==0||((parseInt(EMP_ID.substr(6,2))+1900)%100==0&&(parseInt(EMP_ID.substr(6,2))+1900)%4==0))
			{
				ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;
			} else {
				ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;
			}
			if(ereg.test(EMP_ID)){
				return;
			} else{
				alert("身份证格式错误！");
				document.getElementById("EMP_ID").value="";
				return;
			}
		}
		case 18:{
			if(parseInt(EMP_ID.substr(6,4))%4==0||(parseInt(EMP_ID.substr(6,4))%100==0&&parseInt(EMP_ID.substr(6,4))%4==0))
			{
				ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;
			} else{
				ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;
			}
			if(ereg.test(EMP_ID)){
				var DOB = EMP_ID.substr(6,8);
				document.getElementById("DOB").value= DOB.substr(0,4)+"-"+DOB.substr(4,2)+"-"+DOB.substr(6,2);
				return;
			} else{
				alert("身份证格式错误！");
				document.getElementById("EMP_ID").value="";
				return;
			}
		}
		default:{
			alert("身份证格式错误！");
			document.getElementById("EMP_ID").value="";
			return;
		}
		}
	}
</script>
<style type="text/css">

</style>
</head>

<body>

<form name="empInfo" method="post" action="<%=(mode==0)?"newEmp.":"updateEmp."%>" onsubmit="return validate(document.forms(0))">
<input type="hidden" value="<%=mode %>" name="mode">
<% if(mode ==1){%>
	<input type=hidden name="HREmployee/empID[0]@oldValue" value="<%=rd.getStringByDI("HREmployee","empID",0)%>">
<%}%>		
    <%if(mode==0){%>
    <P>添加新的员工信息： </P>
    <%}else if(mode ==1){%>
    <p>更改员工信息： </p>
    <%}%>
  <table width="100%" border="0" cellspacing="1" cellpadding="0" class="hci">
    <tr> 
      <td width="14%" class="head">身份证明号码：</td>
      <td width="39%" class="content"> 
        <%if(mode==0){%>
        <input type="text" name="HREmployee/SFZMHM" size="20" maxlength="20" class="m_integer_focus" onBlur="getDOB();" id="EMP_ID"/>
        <%}else{%>
        <input type="text" name="HREmployee/SFZMHM" value="<%=rd.getStringByDI("HREmployee","SFZMHM",0)%>" readonly size="10" maxlength="10" class="m_integer_focus" />
        &nbsp; 
        <%}%>
      <font color="#FF0000">*</font></td>
      <td width="14%" class="head">员工名称：</td>
      <td width="39%" class="content"> 
        <%if(mode==1){%>
        <input type="text" class="m_" name="HREmployee/EMPNAME" value="<%=rd.getStringByDI("HREmployee","EMPNAME",0)%>" size="20" maxlength="20">
        <%}else if(mode==0){%>
        <input type="text" class="m_" name="HREmployee/EMPNAME" value="" size="20" maxlength="20">
        <%}else{%>
        <%=rd.getString("HREmployee","name",0)%> 
        <%}%>
        <font color="#FF0000">* </font></td>
    </tr>
    <tr> 
      <td width="14%" class="head">所属部门：</td>
      <td width="39%" class="content">
      <%
      if(mode==1) { 
    	  String orgName = rd.getStringByDI("HREmployee","ORGNAME",0);
    	  String orgId = rd.getStringByDI("HREmployee","orgid",0);
      %>
        <input type="text" value="<%=orgName%>" size="10" maxlength="10" readonly="readonly"/>
        <input type="hidden" name="HREmployee/orgID" value="<%=orgId%>" size="10" maxlength="10">
        <font color="#FF0000">* </font>
      <%
      }else if(mode==0){
    	  String orgName = rd.getStringByDI("HROrganizations","name",0);
    	  String orgId = rd.getStringByDI("HROrganizations","ORGID",0);
      %>
        <input type="text" name="HREmployee/orgname" value="<%=orgName%>" size="10" maxlength="10" readonly="readonly"/>
        <input type="hidden" name="HREmployee/orgID" value="<%=orgId%>" size="10" maxlength="10">
        <font color="#FF0000">* </font>
      <%} %>
      </td>
      <td width="14%" class="head">出生日期：</td>
      <td width="39%" class="content"> 
        <%if(mode==1){%>
        <input type="text" class="date" name="HREmployee/csrq" value="<%=rd.getStringByDI("HREmployee","csrq",0)%>" size="10" id="DOB" maxlength="10"/>
        <%}else if(mode==0){%>
        <input type="text" class="date" name="HREmployee/csrq" value="" size="10" maxlength="10" id="DOB" readonly="readonly"/>
        <%}else{%>
        <%=rd.getStringByDI("HREmployees","csrq",0)%> 
        <%}%>
      </td>
    </tr>
    <tr> 
      <td width="14%" class="head">性别：</td>
      <td width="39%" class="content"> 
        <%if(mode==1){%>
        <select name="HREmployee/sex">
          <option value="2" <%=rd.getStringByDI("HREmployee","sex",0).compareTo("2")==0?"selected":""%>>女性</option>
          <option value="1" <%=rd.getStringByDI("HREmployee","sex",0).compareTo("1")==0?"selected":""%>>男性</option>
        </select>
        <%}else if(mode==0){%>
        <select name="HREmployee/sex">
          <option value="2" >女性</option>
          <option value="1" selected>男性</option>
        </select>
        <%}else{%>
        <%=rd.getStringByDI("HREmployees","sex",0)%> 
        <%}%>
      </td>
      <td width="14%" class="head">联系电话：</td>
      <td width="39%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="HREmployee/EMPTEL" value="<%=rd.getStringByDI("HREmployee","EMPTEL",0)%>" size="12" maxlength="12">
        <%}else if(mode==0){%>
        <input type="text" name="HREmployee/EMPTEL" value="" size="12" maxlength="12">
        <%}else{%>
        <%=rd.getStringByDI("HREmployees","EMPTEL",0)%> 
        <%}%>
      </td>
    </tr>
    <tr> 
      <td width="14%" class="head">年龄：</td>
      <td width="39%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="HREmployee/EMPAGE" value="<%=rd.getStringByDI("HREmployee","EMPAGE",0)%>" size="12" maxlength="12">
        <%}else if(mode==0){%>
        <input type="text" name="HREmployee/EMPAGE" value="" size="12" maxlength="12">
        <%}else{%>
        <%=rd.getStringByDI("HREmployees","EMPAGE",0)%> 
        <%}%>
      </td>
      <td width="14%" class="head">类型：</td>
      <td width="39%" class="content"> 
        <%if(mode==1){%>
        <select name="HREmployee/ZJZ">
          <option value="1" <%=rd.getStringByDI("HREmployee","ZJZ",0).compareTo("F")==0?"selected":""%>>全职</option>
          <option value="2" <%=rd.getStringByDI("HREmployee","ZJZ",0).compareTo("P")==0?"selected":""%>>兼职</option>
        </select>
        <%}else if(mode==0){%>
        <select name="HREmployee/empType">
          <option value="1" selected>全职</option>
          <option value="2">兼职</option>
        </select>
        <%}else{%>
        <%=rd.getStringByDI("HREmployees","ZJZ",0)%> 
        <%}%>
      </td>
    </tr>
    <tr> 
      <td width="14%" class="head">婚姻状况：</td>
      <td width="39%" class="content"> 
        <%if(mode==1){%>
        <select name="HREmployee/HYZK">
          <option value="Y" <%=rd.getStringByDI("HREmployee","HYZK",0).compareTo("Y")==0?"selected":""%>>已婚</option>
          <option value="N" <%=rd.getStringByDI("HREmployee","HYZK",0).compareTo("N")==0?"selected":""%>>未婚</option>
        </select>
        <%}else if(mode==0){%>
        <select name="HREmployee/HYZK">
          <option value="Y" selected>已婚</option>
          <option value="N">未婚</option>
        </select>
        <%}else{%>
        <%=rd.getStringByDI("HREmployees","HYZK",0)%> 
        <%}%>
      </td>
      <td width="14%" class="head">手机号码：</td>
      <td width="39%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="HREmployee/SJHM" value="<%=rd.getStringByDI("HREmployee","SJHM",0)%>" size="24" maxlength="24">
        <%}else if(mode==0){%>
        <input type="text" name="HREmployee/SJHM" size="24" maxlength="24">
        <%}else{%>
        <%=rd.getStringByDI("HREmployees","SJHM",0)%> 
        <%}%>
      </td>
    </tr>
    <tr> 
      <td width="14%" class="head"> 
        <p>最高学位：</p>
      </td>
      <td width="39%" class="content"> 
        <%if(mode==1){%>
        <select name="HREmployee/degree">
          <option value="D" <%=rd.getStringByDI("HREmployee","degree",0).compareTo("D")==0?"selected":""%>>博士</option>
          <option value="M" <%=rd.getStringByDI("HREmployee","degree",0).compareTo("M")==0?"selected":""%>>硕士</option>
          <option value="B" <%=rd.getStringByDI("HREmployee","degree",0).compareTo("B")==0?"selected":""%>>学士</option>
          <option value="O" <%=rd.getStringByDI("HREmployee","degree",0).compareTo("O")==0?"selected":""%>>其他</option>
        </select>
        <%}else if(mode==0){%>
        <select name="HREmployee/degree">
          <option value="D">博士</option>
          <option value="M">硕士</option>
          <option value="B" selected>学士</option>
          <option value="O">其他</option>
        </select>
        <%}else{%>
        <%=rd.getStringByDI("HREmployees","degree",0)%> 
        <%}%>
      </td>
      <td width="14%" class="head">毕业学校：</td>
      <td width="39%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="HREmployee/lastSchool" value="<%=rd.getStringByDI("HREmployee","lastSchool",0)%>">
        <%}else if(mode==0){%>
        <input type="text" name="HREmployee/lastSchool" value="">
        <%}else{%>
        <%=rd.getStringByDI("HREmployeeS","lastSchool",0)%> 
        <%}%>
      </td>
    </tr>
    <tr> 
      <td width="14%" class="head">家庭地址：</td>
      <td width="39%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="HREmployee/EMPADD" value="<%=rd.getStringByDI("HREmployee","EMPADD",0)%>" size="30" maxlength="60">
        <%}else if(mode==0){%>
        <input type="text" name="HREmployee/EMPADD" value="" size="30" maxlength="60">
        <%}else{%>
        <%=rd.getStringByDI("HREmployees","EMPADD",0)%> 
        <%}%>
      </td>
      <td width="14%" class="head">邮政编码：</td>
      <td width="39%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="HREmployee/YZBM" value="<%=rd.getStringByDI("HREmployee","YZBM",0)%>" size="6" maxlength="6">
        <%}else if(mode==0){%>
        <input type="text" name="HREmployee/YZBM" value="" size="6" maxlength="6">
        <%}else{%>
        <%=rd.getStringByDI("HREmployees","YZBM",0)%> 
        <%}%>
      </td>
    </tr>
    <tr> 
      <td width="14%" class="head">注册日期：</td>
      <td width="39%" class="content"> 
        <%if(mode==1){%>
        <input type="text" class="date" name="HREmployee/regDate" value="<%=rd.getStringByDI("HREmployee","regDate",0)%>">
        <%}else if(mode==0){%>
        <input type="text"  class="date" name="HREmployee/regDate" value="<%=BizData.sdfDate.format(new Date())%>">
        <%}else{%>
        <%=rd.getStringByDI("HREmployeeS","regDate",0)%> 
        <%}%><font color="#FF0000">* </font>
      </td>
      <td width="14%" class="head">状态：</td>
      <td width="39%" class="content"> 
        <%if(mode==1){%>
        <select name="HREmployee/status">
          <option value="1" <%=rd.getStringByDI("HREmployee","status",0).compareTo("1")==0?"selected":""%>>正常</option>
          <option value="0" <%=rd.getStringByDI("HREmployee","status",0).compareTo("0")==0?"selected":""%>>休假中</option>
          <option value="-1" <%=rd.getStringByDI("HREmployee","status",0).compareTo("-1")==0?"selected":""%>>已删除</option>
        </select>
        <%}else if(mode==0){%>
        <select name="HREmployee/status">
          <option value="1" selected>正常</option>
          <option value="0">休假中</option>
          <option value="-1">已删除</option>
        </select>
        <%}else{%>
        <%=rd.getStringByDI("HREmployeeS","status",0)%> 
        <%}%>
      </td>
    </tr>
    <tr> 
      <td width="14%" class="head">联系电话</td>
      <td width="39%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="HREmployee/emContactTel" value="<%=rd.getStringByDI("HREmployee","emContactTel",0)%>" size="12" maxlength="12">
        <%}else if(mode==0){%>
        <input type="text" name="HREmployee/emContactTel" value="" size="12" maxlength="12">
        <%}else{%>
        <%=rd.getStringByDI("HREmployees","emContactTel",0)%> 
        <%}%>
      </td>
      <td width="14%" class="head">MSN/QQ</td>
      <td width="39%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="HREmployee/qq" value="<%=rd.getStringByDI("HREmployee","qq",0)%>">
        <%}else if(mode==0){%>
        <input type="text" name="HREmployee/qq" value="">
        <%}else{%>
        <%=rd.getStringByDI("HREmployees","qq",0)%> 
        <%}%>
      </td>
    </tr>
    <tr> 
      <td width="14%" class="head">传真</td>
      <td width="39%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="HREmployee/BUSINESS_FAX" value="<%=rd.getStringByDI("HREmployee","BUSINESS_FAX",0)%>">
        <%}else if(mode==0){%>
        <input type="text" name="HREmployee/BUSINESS_FAX" value="">
        <%}else{%>
        <%=rd.getStringByDI("HREmployees","BUSINESS_FAX",0)%> 
        <%}%>
      </td>
      <td width="14%" class="head">手机</td>
      <td width="39%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="HREmployee/BUSINESS_MOBILE" value="<%=rd.getStringByDI("HREmployee","BUSINESS_MOBILE",0)%>" size="12" maxlength="12">
        <%}else if(mode==0){%>
        <input type="text" name="HREmployee/BUSINESS_MOBILE" value="" size="12" maxlength="12">
        <%}else{%>
        <%=rd.getStringByDI("HREmployees","BUSINESS_MOBILE",0)%> 
        <%}%>
      </td>
    </tr>
  </table>
    <%if(mode!=2){%>
    <input type="submit" name="Submit" value="提交" class="button">
    <%}%>
    <input type="button" name="return" value="返回" class="button" onclick="javascipt:history.go(-1);">  
</form>
</body>
</html>