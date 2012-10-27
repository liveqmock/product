<%@include file="/common.jsp"%>

<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.net.URLEncoder"%>
<%
	/**
		Get the request parameters;
	*/
	int mode =2;// 0- new ,1,Update,2-readonly;

	String empID= request.getParameter("empID");
	String deptID=request.getParameter("deptID");
	if(empID==null){
		empID=rd.getString("DRMEmployee","empID",0);
	}
	if(deptID==null){
		deptID=rd.getString("DRMEmployee","deptID",0);
	}
%>
<form name="empInfo" method="post" action="<%=(mode==0)?"hr.Employee.insertEmp.":"hr.Employee.updateEmp."%>">
<% if(mode ==1){%>
	<input type=hidden name="DRMEmployee/empID[0]@oldValue" value="<%=rd.get("DRMEmployee","empID",0)%>">
	<input type="hidden" name="_REDIRECT" value="hr.Employee.queryEmps.?DRMEmployee/deptID=<%=URLEncoder.encode(deptID,"GBK")%>">
<%}else if(mode ==0){%> 
	<input type="hidden" name="_REDIRECT" value="hr.Employee.queryEmp.?dispMode=N&deptID=<%=URLEncoder.encode(deptID,"GBK")%>">
<%}%>		
  <p> 
    <%if(mode==0){%>
    ����µ�Ա����Ϣ�� 
    <%}else if(mode ==1){%>
    �����Ա����Ϣ�� 
    <%}else{%>
	Ա����ϸ��Ϣ�� 
	<%}%>
  </p>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="hci">
    <tr> 
      <td width="22%" class="head">Ա�����</td>
      <td width="78%" class="content"> 
        <%if(mode==0){%>
        <input type="text" name="DRMEmployee/empID">
        <%}else if(mode==1){%>
        <input type="text" name="DRMEmployee/empID" value="<%=rd.getString("DRMEmployee","empID",0)%>" readonly>
        <%}else{%>
		<%=rd.getString("DRMEmployee","empID",0)%>
		<%}%>
		&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">Ա������</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMEmployee/empName" value="<%=rd.getString("DRMEmployee","empName",0)%>">
        <%}else if(mode==0){%>
        <input type="text" name="DRMEmployee/empName" value="">
        <%}else{%>
        <%=rd.getString("DRMEmployee","empName",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">��������</td>
      <td width="78%" class="content"> 
        <%int rows=rd.getTableRowsCount("DRMDepartments");
		if(mode==1){%>
        <select name="DRMEmployee/deptID">
		<%for(int k=0;k<rows;k++){%>
			<option value="<%=rd.getString("DRMDepartments","deptID",k)%>" <%=rd.getString("DRMDepartments","deptID",k).compareTo(rd.getString("DRMEmployee","deptID",0))==0?"selected":""%>><%=rd.getString("DRMDepartments","deptName",k)%></option>
		<%}%>
        </select>
        <%}else if(mode==0){%>
        <select name="DRMEmployee/deptID">
		<%for(int k=0;k<rows;k++){%>
			<option value="<%=rd.getString("DRMDepartments","deptID",k)%>" <%=rd.getString("DRMDepartments","deptID",k).compareTo(deptID)==0?"selected":""%>><%=rd.getString("DRMDepartments","deptName",k)%></option>
		<%}%>
        </select>
        <%}%>
        &nbsp; 
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">��������</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMEmployee/empBirthDate" value="<%=rd.getString("DRMEmployee","empBirthDate",0)%>" size="10" maxlength="10">
        <%}else if(mode==0){%>
        <input type="text" name="DRMEmployee/empBirthDate" value="" size="10" maxlength="10">
        <%}else{%>
        <%=rd.getString("DRMEmployee","empBirthDate",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">�Ա�</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <select name="DRMEmployee/gender">
          <option value="F" <%=rd.getString("DRMEmployee","gender",0).compareTo("F")==0?"selected":""%>>Ů��</option>
          <option value="M" <%=rd.getString("DRMEmployee","gender",0).compareTo("M")==0?"selected":""%>>����</option>
        </select>
        <%}else if(mode==0){%>
        <select name="DRMEmployee/gender">
          <option value="F" >Ů��</option>
          <option value="M" selected>����</option>
        </select>
        <%}else{%>
        <%=rd.getString("DRMEmployee","gender",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">��ͥ�绰</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMEmployee/telH" value="<%=rd.getString("DRMEmployee","telH",0)%>">
        <%}else if(mode==0){%>
        <input type="text" name="DRMEmployee/telH" value="">
        <%}else{%>
        <%=rd.getString("DRMEmployee","telH",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">�����绰</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMEmployee/telW" value="<%=rd.getString("DRMEmployee","telW",0)%>">
        <%}else if(mode==0){%>
        <input type="text" name="DRMEmployee/telW" value="">
        <%}else{%>
        <%=rd.getString("DRMEmployee","telW",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">�ƶ��绰</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMEmployee/telM" value="<%=rd.getString("DRMEmployee","telM",0)%>">
        <%}else if(mode==0){%>
        <input type="text" name="DRMEmployee/telM" value="">
        <%}else{%>
        <%=rd.getString("DRMEmployee","telM",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">EMail</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMEmployee/email" value="<%=rd.getString("DRMEmployee","eamil",0)%>">
        <%}else if(mode==0){%>
        <input type="text" name="DRMEmployee/comName" value="">
        <%}else{%>
        <%=rd.getString("DRMEmployee","userID",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">��ַ</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMEmployee/address" value="<%=rd.getString("DRMEmployee","address",0)%>">
        <%}else if(mode==0){%>
        <input type="text" name="DRMEmployee/address" value="">
        <%}else{%>
        <%=rd.getString("DRMEmployee","address",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">��������</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMEmployee/zipcode" value="<%=rd.getString("DRMEmployee","zipcode",0)%>">
        <%}else if(mode==0){%>
        <input type="text" name="DRMEmployee/zipcode" value="">
        <%}else{%>
        <%=rd.getString("DRMEmployee","zipcode",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">����</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <select name="DRMEmployee/empType">
          <option value="F" <%=rd.getString("DRMEmployee","empType",0).compareTo("F")==0?"selected":""%>>ȫְ</option>
          <option value="P" <%=rd.getString("DRMEmployee","empType",0).compareTo("P")==0?"selected":""%>>��ְ</option>		
        </select>
        <%}else if(mode==0){%>
        <select name="DRMEmployee/empType">
          <option value="F" selected>ȫְ</option>
          <option value="P">��ְ</option>		
        </select>
        <%}else{%>
        <%=rd.getString("DRMEmployee","userID",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">����</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMEmployee/empLevel" value="<%=rd.getString("DRMEmployee","empLevel",0)%>">
        <%}else if(mode==0){%>
        <input type="text" name="DRMEmployee/empLevel">
        <%}else{%>
        <%=rd.getString("DRMEmployee","empLevel",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">����״��</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <select name="DRMEmployee/marriage">
          <option value="Y" <%=rd.getString("DRMEmployee","marriage",0).compareTo("Y")==0?"selected":""%>>�ѻ�</option>
          <option value="N" <%=rd.getString("DRMEmployee","marriage",0).compareTo("N")==0?"selected":""%>>δ��</option>
        </select>
        <%}else if(mode==0){%>
        <select name="DRMEmployee/marriage">
          <option value="Y" selected>�ѻ�</option>
          <option value="N">δ��</option>
        </select>
        <%}else{%>
        <%=rd.getString("DRMEmployee","userID",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head"> 
        <p>���ʿ��ʺ�</p>
      </td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMEmployee/account" value="<%=rd.getString("DRMEmployee","account",0)%>">
        <%}else if(mode==0){%>
        <input type="text" name="DRMEmployee/account" value="">
        <%}else{%>
        <%=rd.getString("DRMEmployee","account",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">������</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMEmployee/acctBank" value="<%=rd.getString("DRMEmployee","acctBank",0)%>">
        <%}else if(mode==0){%>
        <input type="text" name="DRMEmployee/acctBank" value="">
        <%}else{%>
        <%=rd.getString("DRMEmployee","acctBank",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">������ַ</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMEmployee/bankAddress" value="<%=rd.getString("DRMEmployee","bankAddress",0)%>">
        <%}else if(mode==0){%>
        <input type="text" name="DRMEmployee/bankAddress" value="">
        <%}else{%>
        <%=rd.getString("DRMEmployee","bankAddress",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head"> 
        <p>���ѧλ</p>
      </td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <select name="DRMEmployee/degree">
          <option value="D" <%=rd.getString("DRMEmployee","degree",0).compareTo("D")==0?"selected":""%>>��ʿ</option>
          <option value="M" <%=rd.getString("DRMEmployee","degree",0).compareTo("M")==0?"selected":""%>>˶ʿ</option>
          <option value="B" <%=rd.getString("DRMEmployee","degree",0).compareTo("B")==0?"selected":""%>>ѧʿ</option>		  
          <option value="O" <%=rd.getString("DRMEmployee","degree",0).compareTo("O")==0?"selected":""%>>����</option>		  		  
        </select>
        <%}else if(mode==0){%>
        <select name="DRMEmployee/degree">
          <option value="D">��ʿ</option>
          <option value="M">˶ʿ</option>
          <option value="B" selected>ѧʿ</option>		  
          <option value="O">����</option>		  		  
        </select>
        <%}else{%>
        <%=rd.getString("DRMEmployee","degree",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">��ҵѧУ</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMEmployee/lastSchool" value="<%=rd.getString("DRMEmployee","lastSchool",0)%>">
        <%}else if(mode==0){%>
        <input type="text" name="DRMEmployee/lastSchool" value="">
        <%}else{%>
        <%=rd.getString("DRMEmployee","lastSchool",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">ע������</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMEmployee/enrollDate" value="<%=rd.getString("DRMEmployee","enrollDate",0)%>">
        <%}else if(mode==0){%>
        <input type="text" name="DRMEmployee/enrollDate" value="">
        <%}else{%>
        <%=rd.getString("DRMEmployee","enrollDate",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">��������</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMEmployee/createTime" value="<%=rd.getString("DRMEmployee","createTime",0)%>">
        <%}else if(mode==0){%>
        <input type="text" name="DRMEmployee/createTime" value="">
        <%}else{%>
        <%=rd.getString("DRMEmployee","createTime",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">����������</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMEmployee/lastUpdateTime" value="<%=rd.getString("DRMEmployee","lastUpdateTime",0)%>">
        <%}else if(mode==0){%>
        <input type="text" name="DRMEmployee/lastUpdateTime" value="">
        <%}else{%>
        <%=rd.getString("DRMEmployee","lastUpdateTime",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">״̬</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <select name="DRMEmployee/status">
          <option value="N" <%=rd.getString("DRMEmployee","status",0).compareTo("N")==0?"selected":""%>>����</option>
          <option value="H" <%=rd.getString("DRMEmployee","status",0).compareTo("H")==0?"selected":""%>>�ݼ���</option>
          <option value="D" <%=rd.getString("DRMEmployee","status",0).compareTo("D")==0?"selected":""%>>��ɾ��</option>		  
        </select>
        <%}else if(mode==0){%>
        <select name="DRMEmployee/status">
          <option value="N" selected>����</option>
          <option value="H">�ݼ���</option>
          <option value="D">��ɾ��</option>		  
        </select>
        <%}else{%>
        <%=rd.getString("DRMEmployee","userID",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">������ϵ��</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMEmployee/emContact" value="<%=rd.getString("DRMEmployee","emContact",0)%>">
        <%}else if(mode==0){%>
        <input type="text" name="DRMEmployee/emContact" value="">
        <%}else{%>
        <%=rd.getString("DRMEmployee","emContact",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head"> ������ϵ�绰</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMEmployee/emContactTel" value="<%=rd.getString("DRMEmployee","emContactTel",0)%>" size="64" maxlength="128">
        <%}else if(mode==0){%>
        <input type="text" name="DRMEmployee/emContactTel" value="" size="64" maxlength="128">
        <%}else{%>
        <%=rd.getString("DRMEmployee","emContactTel",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
  </table>
  <p> 
    <%if(mode!=2){%>
    <input type="submit" name="Submit" value="�ύ">
    <%}%>
    <input type="button" name="return" value="����" onclick="javascript:history.go(-1);">
  </p>
</form>
