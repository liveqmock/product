<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<%
String msg = rd.getString("msg");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>������Ϣ</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>


<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/thickbox/thickbox.css">

<script type="text/javascript">
	function save(){
		if($("#city").val() == ''){
			alert("��ѡ��˾���ڳ��У�");
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
			alert("�޸ĳɹ���");
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
<div id="lable"><span><%=rd.getString("HROrganizations","name",0)%>�Ļ�����Ϣ����:</span></div>
<div class="add-table">
  <table class="datas">
    <tr>
      <td>��˾���ƣ�</td>
      <td><input type="text" name="HROrganization/name" value="<%=rd.getString("HROrganizations","name",0)%>" readonly="readonly"/><font color="red">*</font></td> 
      <td>��˾��ƣ�</td>
      <td><input type="text" name="HROrganization/SIMPLENAME" value="<%=rd.getString("HROrganizations","SIMPLENAME",0)%>"/><font color="red">*</font></td>
    </tr>
    <tr>
      <td>����ע�����֤��ţ�</td>
      <td><input type="text" name="HROrganization/LICENCENO" value="<%=rd.getString("HROrganizations","LICENCENO",0)%>"/></td>
      <td></td>
      <td></td>
    </tr>
    <tr>
      <td>��ҵ��������֤�ţ�</td>
      <td><input type="text" value="<%=rd.getString("HROrganizations","CMPNYNO",0)%>" name="HROrganization/CMPNYNO"/></td>
      <td>�������֤�ţ�</td>      
      <td><input type="text" value="<%=rd.getString("HROrganizations","SFZMHM",0)%>" name="HROrganization/SFZMHM"/></td>
  </tr>
  <tr>
  	  <td>����������</td>
      <td><input type="text" value="<%=rd.getString("HROrganizations","CORPORATENAME",0)%>" name="HROrganization/CORPORATENAME"/></td>
      <td>������ϵ�绰��</td>      
      <td><input type="text" value="<%=rd.getString("HROrganizations","CORPORATETEL",0)%>" name="HROrganization/CORPORATETEL"/></td>
  </tr>
  <tr>
  	  <td>��˾���棺</td>
      <td><input type="text" value="<%=rd.getString("HROrganizations","ORGFAX",0)%>" name="HROrganization/ORGFAX"/></td>
      <td>EMAIL��ַ��</td>      
      <td><input type="text" value="<%=rd.getString("HROrganizations","EMAIL",0)%>" name="HROrganization/EMAIL"/></td>
  </tr>
  <tr> 
    <td>�����˺ţ�</td>
    <td><input type="text" value="<%=rd.getString("HROrganizations","BANK_CODE",0)%>" name="HROrganization/BANK_CODE"/></td>
    <td>�����У�</td>
    <td><input type="text" value="<%=rd.getString("HROrganizations","BANK_NAME",0)%>" name="HROrganization/BANK_NAME"/></td>
  </tr>
  <tr>
	<td>��˾��ַ��</td>
	<td colspan="3">
	  <input type="text" value="<%=rd.getString("HROrganizations","CITYNAME",0)%>" name="HROrganization/CITYNAME" id="cityName" 
	  		 onclick="TB_show('ѡ����������','showXZQH4Org.?xzqh/layer=2&xzqh/gnw=1*TB_iframe=false&height=400&width=350','');" /><font color="red">*</font> ��
	  <input type="hidden" value="<%=rd.getString("HROrganizations","city",0)%>" name="HROrganization/city" id="city"/>
	  <input type="hidden" value="<%=rd.getString("HROrganizations","PROVINCENAME",0)%>" name="HROrganization/PROVINCENAME" id="provinceName"/>
	  <input type="hidden" value="<%=rd.getString("HROrganizations","PROVINCE",0)%>" name="HROrganization/PROVINCE" id="province"/>
	  <input type="text" value="<%=rd.getString("HROrganizations","COMPYADD",0)%>" name="HROrganization/COMPYADD" size="30" maxlength="200" />
	</td>
  </tr>
  <tr> 
    <td>����Ӫҵ���֤��</td>
    <td><input type="file" name="HRCOPYOFLICENCE/COPYOFLICENCE" id="cpLicence"/></td>
    <td>�������֤��</td>
    <td><input type="file" name="HRCOPYOFLICENCE/COPYOFSFZMHM" id="cpSfzmhj"/></td>
  </tr>
  <tr>
  	<td>��ҵ���ܣ�</td>
  	<td colspan="3"><textarea name="HRCOPYOFLICENCE/cmp_intro"></textarea></td>
  </tr>
</table>
</div>

  <div align="center"> 
	<input type="button" id="button" value="�� ��" onclick="save();"/>&nbsp; 
	<input type="reset" id="button" value="�� ��"/>&nbsp; 
		
  </div>
</form>

</body>
</html>