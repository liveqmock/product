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
    添加新的员工信息： 
    <%}else if(mode ==1){%>
    请更改员工信息： 
    <%}else{%>
	员工详细信息： 
	<%}%>
  </p>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="hci">
    <tr> 
      <td width="22%" class="head">员工编号</td>
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
      <td width="22%" class="head">员工姓名</td>
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
      <td width="22%" class="head">所属部门</td>
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
      <td width="22%" class="head">出生日期</td>
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
      <td width="22%" class="head">性别</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <select name="DRMEmployee/gender">
          <option value="F" <%=rd.getString("DRMEmployee","gender",0).compareTo("F")==0?"selected":""%>>女性</option>
          <option value="M" <%=rd.getString("DRMEmployee","gender",0).compareTo("M")==0?"selected":""%>>男性</option>
        </select>
        <%}else if(mode==0){%>
        <select name="DRMEmployee/gender">
          <option value="F" >女性</option>
          <option value="M" selected>男性</option>
        </select>
        <%}else{%>
        <%=rd.getString("DRMEmployee","gender",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">家庭电话</td>
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
      <td width="22%" class="head">工作电话</td>
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
      <td width="22%" class="head">移动电话</td>
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
      <td width="22%" class="head">地址</td>
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
      <td width="22%" class="head">邮政编码</td>
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
      <td width="22%" class="head">类型</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <select name="DRMEmployee/empType">
          <option value="F" <%=rd.getString("DRMEmployee","empType",0).compareTo("F")==0?"selected":""%>>全职</option>
          <option value="P" <%=rd.getString("DRMEmployee","empType",0).compareTo("P")==0?"selected":""%>>兼职</option>		
        </select>
        <%}else if(mode==0){%>
        <select name="DRMEmployee/empType">
          <option value="F" selected>全职</option>
          <option value="P">兼职</option>		
        </select>
        <%}else{%>
        <%=rd.getString("DRMEmployee","userID",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">级别</td>
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
      <td width="22%" class="head">婚姻状况</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <select name="DRMEmployee/marriage">
          <option value="Y" <%=rd.getString("DRMEmployee","marriage",0).compareTo("Y")==0?"selected":""%>>已婚</option>
          <option value="N" <%=rd.getString("DRMEmployee","marriage",0).compareTo("N")==0?"selected":""%>>未婚</option>
        </select>
        <%}else if(mode==0){%>
        <select name="DRMEmployee/marriage">
          <option value="Y" selected>已婚</option>
          <option value="N">未婚</option>
        </select>
        <%}else{%>
        <%=rd.getString("DRMEmployee","userID",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head"> 
        <p>工资卡帐号</p>
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
      <td width="22%" class="head">开户行</td>
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
      <td width="22%" class="head">开户地址</td>
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
        <p>最高学位</p>
      </td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <select name="DRMEmployee/degree">
          <option value="D" <%=rd.getString("DRMEmployee","degree",0).compareTo("D")==0?"selected":""%>>博士</option>
          <option value="M" <%=rd.getString("DRMEmployee","degree",0).compareTo("M")==0?"selected":""%>>硕士</option>
          <option value="B" <%=rd.getString("DRMEmployee","degree",0).compareTo("B")==0?"selected":""%>>学士</option>		  
          <option value="O" <%=rd.getString("DRMEmployee","degree",0).compareTo("O")==0?"selected":""%>>其他</option>		  		  
        </select>
        <%}else if(mode==0){%>
        <select name="DRMEmployee/degree">
          <option value="D">博士</option>
          <option value="M">硕士</option>
          <option value="B" selected>学士</option>		  
          <option value="O">其他</option>		  		  
        </select>
        <%}else{%>
        <%=rd.getString("DRMEmployee","degree",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">毕业学校</td>
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
      <td width="22%" class="head">注册日期</td>
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
      <td width="22%" class="head">创建日期</td>
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
      <td width="22%" class="head">最后更改日期</td>
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
      <td width="22%" class="head">状态</td>
      <td width="78%" class="content"> 
        <%if(mode==1){%>
        <select name="DRMEmployee/status">
          <option value="N" <%=rd.getString("DRMEmployee","status",0).compareTo("N")==0?"selected":""%>>正常</option>
          <option value="H" <%=rd.getString("DRMEmployee","status",0).compareTo("H")==0?"selected":""%>>休假中</option>
          <option value="D" <%=rd.getString("DRMEmployee","status",0).compareTo("D")==0?"selected":""%>>已删除</option>		  
        </select>
        <%}else if(mode==0){%>
        <select name="DRMEmployee/status">
          <option value="N" selected>正常</option>
          <option value="H">休假中</option>
          <option value="D">已删除</option>		  
        </select>
        <%}else{%>
        <%=rd.getString("DRMEmployee","userID",0)%> 
        <%}%>&nbsp;
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">紧急联系人</td>
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
      <td width="22%" class="head"> 紧急联系电话</td>
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
    <input type="submit" name="Submit" value="提交">
    <%}%>
    <input type="button" name="return" value="返回" onclick="javascript:history.go(-1);">
  </p>
</form>
