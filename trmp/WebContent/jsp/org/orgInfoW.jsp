<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.net.URLEncoder"%>
<%@include file="../common.jsp"%>
<%
	String mode=request.getParameter("DispMode");
	if(mode==null){
		mode="N";
	}
	String refresh=request.getParameter("refreshTree");
	if(refresh==null){
		refresh="";
	}
%>
<html>
<head>
<title>机构列表</title>
<link rel="stylesheet" href="../../css/style.css" type="text/css">
<link rel="stylesheet" href="../../css/menu.css" type="text/css">
<script type="text/javascript" src="../../js/forms.js"></script>
<script type="text/javascript">
	function save(){
		if(check()==false){
			return;
		}else{		
			document.forms(0).submit();
		}
	}	

	function check(){
		if(document.forms(0).elements("HROrganization/name").value==""){
			alert("必须填写组织名称!");
			return false;
		}
		return true;		
	}
	
	function deleteOrg(){
		document.forms(0).action="deleteOrg.";
		document.forms(0).submit();
	}

	function addSubOrg(){
		window.location="queryOrg.?DispMode=N&HROrganization/parent_orgID="+document.forms(0).elements("HROrganization/orgID").value+"&parent_orgName="+document.forms(0).elements("HROrganization/name").value;
	}
	
	function beforeDelete(){
		return true;
	}
//-->
</script>
</head>

<body>

<form name="f1" method="post" action="<%=mode.compareTo("U")==0?"updateOrg.":"newOrg."%>">
<input type="hidden" name="mode" value="<%=mode%>">
<%if(mode.compareTo("U")==0){ %>
	<input type="hidden"  name="HROrganization/parent_orgID" size="15" maxlength="15" value="<%=rd.getString("HROrganization","parent_orgID",0)%>">
	<input type="hidden" name="HROrganization/orgID" value="<%=rd.getString("HROrganization","orgID",0)%>">
	<input type="hidden" name="HROrganization/orgID[0]@oldValue" value="<%=rd.getString("HROrganization","orgID",0)%>">
	<p>当前组织的基本信息如下:</P>
<%}else{%>
	<input type="hidden"  name="HROrganization/parent_orgID" size="15" maxlength="15" value="<%=rd.getStringByDI("HROrganizations","orgID",0)%>">
	<input type="hidden" name="parent_orgName" value="<%=rd.getString("parent_orgName")%>">
	<p>为组织<font color="blue"><%=rd.getStringByDI("HROrganizations","NAME",0)%></font>增加下级组织:</P>
<%}%>

  <table width="100%" border="0" cellspacing="1" cellpadding="0" class="hci">
    <tr>
      <td width="92" class="head" nowrap>组织名称</td>
      <td class="content" width="421"> 
        <input type="text"  name="HROrganization/name" size="30" maxlength="50" value="<%=rd.getString("HROrganization","name",0)%>">
      </td> 
      <td width="62" class="head" nowrap>类型</td>
      <td  class="content" nowrap width="178"> 
        <input type="radio" value="C" <%=(rd.getString("HROrganization","type",0).compareTo("C")==0||mode.compareTo("N")==0)?"checked":""%> name="HROrganization/type" >
        公司
	<input type="radio" value="D" <%=rd.getString("HROrganization","type",0).compareTo("D")==0?"checked":""%> name="HROrganization/type" >
        部门
        <input type="radio" value="G" <%=rd.getString("HROrganization","type",0).compareTo("G")==0?"checked":""%> name="HROrganization/type" >
        小组 </td>
    </tr>
    <tr>
      <td width="62" class="head" nowrap>开户行</td>
      <td class="content" nowrap>
        <input type="text" size="14" maxlength="12" value="<%=rd.getString("HROrganization","BANK_NAME",0)%>" name="HROrganization/BANK_NAME">
      </td>
      <td class="head" width="92" nowrap>公司帐号</td>      
      <td class="content" width="421">
        <input type="text"  size="14" maxlength="12" value="<%=rd.getString("HROrganization","BANK_CODE",0)%>" name="HROrganization/BANK_CODE">
      </td>
  </tr>
  <tr>
  	  <td width="62" class="head" nowrap>部门电话</td>
      <td class="content" nowrap>
        <input type="text"  size="14" maxlength="12" value="<%=rd.getString("HROrganization","tel",0)%>" name="HROrganization/tel">
      </td>
      <td class="head" width="92" nowrap>负责人</td>      
      <td class="content" width="421">
        <input type="text"  size="14" maxlength="12" value="<%=rd.getString("HROrganization","CHIEF_NAME",0)%>" name="HROrganization/CHIEF_NAME">
      </td>
  </tr>
  <tr>
  	  <td width="72" class="head" nowrap>负责人手机</td>
      <td class="content" nowrap>
        <input type="text"  size="14" maxlength="12" value="<%=rd.getString("HROrganization","CHIEF_MOBILE",0)%>" name="HROrganization/CHIEF_MOBILE">
      </td>
      <td class="head" width="92" nowrap>负责人传真</td>      
      <td class="content" width="421">
        <input type="text"  size="14" maxlength="12" value="<%=rd.getString("HROrganization","CHIEF_FAX",0)%>" name="HROrganization/CHIEF_FAX">
      </td>
  </tr>
  <tr>
  	  <td width="62" class="head" nowrap>负责人QQ</td>
      <td class="content" nowrap>
        <input type="text"  size="14" maxlength="12" value="<%=rd.getString("HROrganization","qq",0)%>" name="HROrganization/qq">
      </td>
      <td class="head" width="92" nowrap></td>      
      <td class="content" width="421"></td>
  </tr>
  <tr> 
      <td width="62" class="head" nowrap>EMail</td>
    <td class="content" nowrap colspan="3">
        <input type="text"  size="50" maxlength="100" value="<%=rd.getString("HROrganization","email",0)%>" name="HROrganization/email">
      </td>
  </tr>
  <tr> 
      <td width="62"  class="head" nowrap>WEB地址</td>
    <td  class="content" nowrap colspan="3">
        <input type="text"  size="50" maxlength="50" value="<%=rd.getString("HROrganization","web",0)%>" name="HROrganization/web">
      </td>
  </tr>
</table>
<p>当前组织的角色列表： <p>
  <table width="100%" border="0" cellspacing="1" cellpadding="0" class="hci">
    <tr class="head">
      <td width="15%"> 
        <input type="checkbox" name="cbxSelectAll" value="checkbox" onClick="javascript:ClickOnCheckBox(document.forms(0).elements('cbxSelectAll'),document.forms(0))">
        选择</td>
      <td width="20%">角色编号</td>
      <td>角色名称</td>
    </tr>
	<% int rows=rd.getTableRowsCount("DRMRoles");
	   for(int i=0;i<rows;i++){%>
    <tr class="content">
      <td width="15%"><input type="checkbox" name="DRMRole/roleID[<%=i%>]" value="<%=rd.getString("DRMRoles","roleID",i)%>" <%=rd.get("DRMRoles","orgID",i)!=null?"checked":""%> ></td>
      <td width="20%"><%=rd.getString("DRMRoles","roleID",i)%></td>
      <td><%=rd.getString("DRMRoles","roleName",i)%></td>
    </tr>
	<%}%>
  </table>
  <div align="center"> 
  	<A href="javascript:save();">[保存]</A>&nbsp; 
    <A href="javascript:document.forms(0).reset();">[重置]</A>
	<%if(mode.compareTo("U")==0){%>
    	<A href="javascript:deleteOrg();" onclick="return beforeDelete()">[删除]</A> 
		<A href="javascript:addSubOrg();" >[增加下级组织]</A> 
	<%}%>	
 </div>
</form>

</body>
</html>