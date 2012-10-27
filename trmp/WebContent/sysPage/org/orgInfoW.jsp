<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<%
String msg = rd.getString("msg");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>机构信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>


<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/thickbox/thickbox.css">

<script type="text/javascript">
	function save(){
		if($("#city").val() == ''){
			alert("请选择公司所在城市！");
			return false;
		}
		var s=selectImg();
		if(s>0){
			document.orgForm.encoding="multipart/form-data";
			document.orgForm.action="updateOrg.fu";
			document.orgForm.submit();
		}else{
			document.orgForm.submit();
		}
	}

	function showMsg(){

		<%if("Y".equals(msg)){%>
			alert("修改成功！");
		<%}%>
	}
</script>
</head>

<body onload="showMsg();">

<form name="orgForm" method="post" action="updateOrg.">
<input type="hidden" name="HRDEPARTMENT/deptid" value="<%=rd.getString("HRDEPARTMENTs","deptid",0)%>"/>
<input type="hidden" name="HRDEPARTMENT/deptid[0]@oldValue" value="<%=rd.getString("HRDEPARTMENTs","deptid",0)%>"/>
<input type="hidden" name="HROrganization/orgID" value="<%=rd.getString("HROrganizations","orgID",0)%>"/>
<input type="hidden" name="HROrganization/orgID[0]@oldValue" value="<%=rd.getString("HROrganizations","orgID",0)%>"/>
<div id="lable"><span><%=rd.getString("HROrganizations","name",0)%>的基本信息如下:</span></div>
<div class="add-table">
  <table class="datas">
    <tr>
      <td>公司名称：</td>
      <td><input type="text" name="HROrganization/name" value="<%=rd.getString("HROrganizations","name",0)%>" readonly="readonly"/><font color="red">*</font></td> 
      <td>公司简称：</td>
      <td><input type="text" name="HROrganization/SIMPLENAME" value="<%=rd.getString("HROrganizations","SIMPLENAME",0)%>"/><font color="red">*</font></td>
    </tr>
    <tr>
      <td>工商注册许可证编号：</td>
      <td><input type="text" name="HROrganization/LICENCENO" value="<%=rd.getString("HROrganizations","LICENCENO",0)%>"/></td>
      <td></td>
      <td></td>
    </tr>
    <tr>
      <td>企业机构代码证号：</td>
      <td><input type="text" value="<%=rd.getString("HROrganizations","CMPNYNO",0)%>" name="HROrganization/CMPNYNO"/></td>
      <td>法人身份证号：</td>      
      <td><input type="text" value="<%=rd.getString("HROrganizations","SFZMHM",0)%>" name="HROrganization/SFZMHM"/></td>
  </tr>
  <tr>
  	  <td>法人姓名：</td>
      <td><input type="text" value="<%=rd.getString("HROrganizations","CORPORATENAME",0)%>" name="HROrganization/CORPORATENAME"/></td>
      <td>法人联系电话：</td>      
      <td><input type="text" value="<%=rd.getString("HROrganizations","CORPORATETEL",0)%>" name="HROrganization/CORPORATETEL"/></td>
  </tr>
  <tr>
  	  <td>公司传真：</td>
      <td><input type="text" value="<%=rd.getString("HROrganizations","ORGFAX",0)%>" name="HROrganization/ORGFAX"/></td>
      <td>EMAIL地址：</td>      
      <td><input type="text" value="<%=rd.getString("HROrganizations","EMAIL",0)%>" name="HROrganization/EMAIL"/></td>
  </tr>
  <tr> 
    <td>银行账号：</td>
    <td><input type="text" value="<%=rd.getString("HROrganizations","BANK_CODE",0)%>" name="HROrganization/BANK_CODE"/></td>
    <td>开户行：</td>
    <td><input type="text" value="<%=rd.getString("HROrganizations","BANK_NAME",0)%>" name="HROrganization/BANK_NAME"/></td>
  </tr>
  <tr>
	<td>公司地址：</td>
	<td colspan="3">
	  <input type="text" value="<%=rd.getString("HROrganizations","CITYNAME",0)%>" name="HROrganization/CITYNAME" id="cityName" 
	  		 onclick="TB_show('选择行政区划','showXZQH4Org.?xzqh/layer=2&xzqh/gnw=1*TB_iframe=false&height=400&width=350','');" /><font color="red">*</font> 市
	  <input type="hidden" value="<%=rd.getString("HROrganizations","city",0)%>" name="HROrganization/city" id="city"/>
	  <input type="hidden" value="<%=rd.getString("HROrganizations","PROVINCENAME",0)%>" name="HROrganization/PROVINCENAME" id="provinceName"/>
	  <input type="hidden" value="<%=rd.getString("HROrganizations","PROVINCE",0)%>" name="HROrganization/PROVINCE" id="province"/>
	  <input type="text" value="<%=rd.getString("HROrganizations","COMPYADD",0)%>" name="HROrganization/COMPYADD" size="30" maxlength="200" />
	</td>
  </tr>
  <tr> 
    <td>工商营业许可证：</td>
    <td><input type="file" name="HRCOPYOFLICENCE/COPYOFLICENCE" id="cpLicence"/></td>
    <td>法人身份证：</td>
    <td><input type="file" name="HRCOPYOFLICENCE/COPYOFSFZMHM" id="cpSfzmhj"/></td>
  </tr>
  <tr>
  	<td>企业介绍：</td>
  	<td colspan="3"><textarea name="HRCOPYOFLICENCE/cmp_intro"></textarea></td>
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