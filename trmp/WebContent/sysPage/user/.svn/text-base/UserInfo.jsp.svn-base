<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@include file="/common.jsp"%>

<%@page import="java.net.URLEncoder"%>
<%
	int mode =0;// 0- new ,1,Update；
	String dispMode = rd.getString("DispMode");
	if(dispMode == null){
		dispMode = "N";
	}
	if(dispMode.compareTo("U") ==0){
		mode = 1;
	}
	String userID= request.getParameter("DRM/userID");
	String userIDs = rd.getString("userIDs");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/thickbox/thickbox.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>
<title>系统用户基本信息</title>
<script language="javascript">
<!--
	function validate(){
		var insert=<%=mode%>;
		var userIDs = document.getElementById("userIDs").value;
		var userID = document.forms(0).elements("DRMUser/userID").value;
		if(insert==0){
			if(document.forms(0).elements("DRMUser/userNO").value=="" || userID==""){
				alert("对不起,您必须输入'用户编号'和'用户帐号'!");
				return false;
			}
			var userIDArr=userIDs.split(",");
			for(var i=0;i<userIDArr.length;i++){
				if(userIDArr[i] == userID){
					alert("对不起，该用户账号已存在，请重新输入账号!");
					return false;
				}
			}
		}
		if(document.forms(0).elements("DRMUser/userPassword").value!=document.forms(0).elements("DRMUser/userConfirmPassword").value){
			alert("对不起,'密码' 和 '确认密码' 不匹配，请重新输入!");
			return false;
		}
		if(document.forms(0).elements("userFromNm").value==""||document.forms(0).elements("userFromNm").value==null){
			alert("对不起,组织部门为空,请选择组织部门!");
			return false;
		}
		if(document.forms(0).elements("DRMUser/username").value==""||document.forms(0).elements("DRMUser/username").value==null){
			alert("对不起,用户姓名为空,请输入用户姓名!");
			return false;
		}
		
		return true;
	}
//-->

/**
*	checkBox的全选和全中选
*/
$(document).ready(function() {
	jQuery('[type=checkbox]').click(function (){
		var checkClass = jQuery(this).attr("class");
		if(checkClass){
			if(jQuery("."+checkClass).is(":checked")){
				jQuery("#"+checkClass).attr("checked","checked");
			}else{
				jQuery("#"+checkClass).removeAttr("checked");
			}
		}
		var checkID = $(this).attr("id");
		if(checkID){
			if(jQuery("#"+checkID).is(":checked")){
				jQuery("."+checkID).each(function (){
					$(this).attr("checked","checked");
				});
			}else{
				jQuery("."+checkID).each(function (){
					$(this).removeAttr("checked");
				});
			}
		}
	});
});
/**
 *	end
 */
function openHighgrade(){
	if(document.getElementById("highgrade").style.display==""){
		document.getElementById("highgrade").style.display="none";
	}else{
		document.getElementById("highgrade").style.display="";
	}
}

</script>
</head>
<body>
<form name="userInfo" method="post" action="<%=(mode==0)?"newUser.":"updateUser."%>" onsubmit="return validate();">
	<input type="hidden" id="orgID" name="DRMUser/orgID" value="<%=sd.getString("orgID")%>">
	<input type="hidden" id="deptID" name="DRMUser/deptID" value="<%=sd.getString("deptID")%>">
	<input type="hidden" id="userIDs" value="<%=userIDs%>">
	
<% if(mode ==1){%>
	<input type=hidden name="DRMUser/userID" value="<%=rd.getString("DRMUser","userID",0)%>">
	<input type=hidden name="DRMUser/userID[0]@oldValue" value="<%=rd.getString("DRMUser","userID",0)%>">
<%}%>		

    <%if(mode==0){%>
      <div id="lable"><span>您正在添加新的用户：</span></div>
    <%}else if(mode ==1){%>
      <div id="lable"><span>您正在更改用户信息：</span></div>
    <%}%>
  <div class="add-table">
  <table class="datas">
    <tr style="display: none">
      <td>用户编号</td>
      <td> 
        <%if(mode==0){%>
        <input type="text" name="DRMUser/userNO" size="15" maxlength="15" value="<%=rd.getString("maxUserNo") %>" readonly="readonly">
        <%}else{%>
        <input type="text" name="DRMUser/userNO" size="15" maxlength="15" value="<%=rd.getString("DRMUser","userno",0) %>" readonly="readonly">
        <input type=hidden name="DRMUser/userNO[0]@oldValue" value="<%=rd.getString("DRMUser","userNO",0)%>">
        <%}%>
      </td>
      <td>用户类型</td>
      <td> 
        <%if(mode==0){ %>
        <select name="DRMUser/userType" id="userType">
          <option value="E" selected>内部员工</option>
          <option value="P">合作伙伴</option><!-- 
          <option value="S">供应商</option>
          <option value="C">客户</option> -->
        </select>
        <%}else{
			String userType=rd.getString("DRMUser","userType",0);
			if(userType.compareTo("E")==0){%>
        		内部员工 
        <%}else if(userType.compareTo("C")==0){%>
       			 客户 
        <%}else if(userType.compareTo("S")==0){%>
        		供应商 
        <%}else if(userType.compareTo("P")==0){%>
       			合作伙伴 
        <%}else{%>
        		未知用户类型 
        <%}%>
        <input type="hidden" name="DRMUser/userType" value="<%=userType %>" id="userType"/>
        <%}%>
      </td>
    </tr>
    <tr> 
      <td align="right">用户帐号：</td>
      <td> 
        <%if(mode==0){%>
        <input type="text" id="userId" name="DRMUser/userID" value="" size="15" maxlength="15">
        <%}else{%>
        <%=rd.getString("DRMUser","userID",0)%> 
        <%}%>
        <font color="#FF0000">*</font>系统登陆名
      </td>
      <td align="right">用户姓名：</td>
      <td> 
        <%if(mode==1){%>
        <input type="text" name="DRMUser/username" value="<%=rd.getString("DRMUser","username",0)%>" size="15" maxlength="15" id="username">
        <%}else{%>
        <input type="text" name="DRMUser/username" value="" size="15" maxlength="15" id="username">
        <%}%>
        <font color="#FF0000">*</font>员工姓名
      </td>
    </tr>
    <tr>
    	  <td align="right">组织部门：</td>
	      <td>
	        <%if(mode==1){
	        //修改
	        %>
	        <input type="hidden" name="DRMUser/DEPTID" id="userFrom" value="<%=rd.getString("DRMUser","DEPTID",0)%>"/>
	        <%-- <input type="text" name="DRMUser/USERFROM" onclick="return GB_myShow('选择组织部门','<%=request.getContextPath() %>/sysPage/org/empsHomeForUserAdd.jsp','800','750','');" value="<%=rd.getString("DRMUser","USERFROM",0)%>" id="userFromNm" readonly="readonly"/>
	         --%>
	         
	        <input type="text" name="DRMUser/USERFROM" 
	        		onclick="TB_show('选择组织部门','<%=request.getContextPath() %>/queryOrg.?HRORGANIZATION/ORGID=<%=sd.getString("orgID") %>&HRDEPARTMENT/ORGID=<%=sd.getString("orgID") %>&fwd=org*TB_iframe=true&height=400&width=350','');" 
	        		value="<%=rd.getString("hrdepartments","deptname",0)%>" id="userFromNm" readonly="readonly"/>
	        <%}else{
	        //添加
	        %>
	        <input type="hidden" name="DRMUser/DEPTID" id="userFrom"/>
	        <%-- <input type="text" name="DRMUser/USERFROM" onclick="return GB_myShow('选择组织部门','<%=request.getContextPath() %>/sysPage/org/empsHomeForUserAdd.jsp','800','750','');" id="userFromNm" readonly="readonly"/> --%>
	        <input type="text" name="DRMUser/USERFROM" 
	        		onclick="TB_show('选择组织部门','<%=request.getContextPath() %>/queryOrg.?HRORGANIZATION/ORGID=<%=sd.getString("orgID") %>&HRDEPARTMENT/ORGID=<%=sd.getString("orgID") %>&fwd=org*TB_iframe=true&height=400&width=350','');" 
	        		id="userFromNm" readonly="readonly"/>
	        <%}%>
	       <a href="###" onclick="TB_show('选择组织部门','<%=request.getContextPath() %>/queryOrg.?HRORGANIZATION/ORGID=<%=sd.getString("orgID") %>&HRDEPARTMENT/ORGID=<%=sd.getString("orgID") %>&fwd=org*TB_iframe=true&height=400&width=350','');"><font color="#FF0000">*</font>选择组织部门</a>
	      </td>
	      <td align="right">状态：</td>
	      <td> 
	        <select name="DRMUser/userStatus">
	        <%
	        if(dispMode.equals("N")){
	        %>
	        	<option value="1" selected="selected" >正常</option>
		        <option value="0">封锁</option>
		    <%
	        }else{
		        int userStatus = rd.getInt("DRMUser","userStatus",0);
		        if(userStatus == 0){
		        %>
		          <option value="1">正常</option>
		          <option value="0" selected="selected" >封锁</option>
		        <%
		        } else {%>
		          <option value="1" selected="selected">正常</option>
		          <option value="0" >封锁</option>
		        <%
		        }
	        }
		        %>
	        </select>
	      </td>
    </tr>
    <tr> 
      <td align="right">密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
      <td> 
        <%if(mode==1){%>
        <input type="password" name="DRMUser/userConfirmPassword" value="<%=rd.getString("DRMUser","userPassword",0)%>" size="15" maxlength="15">
        <%}else {%>
        <input type="password" name="DRMUser/userConfirmPassword" value="" size="15" maxlength="15">
        <%}%>
      </td>
      <td align="right">确认密码：</td>
      <td> 
        <%if(mode==1){%>
        <input type="password" name="DRMUser/userPassword" value="<%=rd.getString("DRMUser","userPassword",0)%>" size="15" maxlength="15">
        <%}else{%>
        <input type="password" name="DRMUser/userPassword" value="" size="15" maxlength="15">
        <%}%>
      </td>
    </tr>
  </table>
  <div>
  	<table class="add-table">
  		<tr>
  			<td align="center">
  				<input type="button" id="button" value="高级选项" onclick="openHighgrade()">
  			</td>
  		</tr>
  	</table>
  </div>
  <div id="highgrade" style="display: none">
  <table class="datas">
    <tr>
      <td align="right">IP地址验证模式：</td>
      <td> 
        <select name="DRMUser/userLoginOnlyThisIP">
        <%
        if(dispMode.equals("N")){
        %>
       	  <option value="1">固定IP</option>
          <option value="0">范围约束</option>
          <option value="-1" selected="selected">不限定IP</option>
	    <%
        }else{
	    %>
	      <option value="1" <%=(rd!=null&&rd.getInt("DRMUser","userLoginOnlyThisIP",0)==1)?"selected":""%>>固定IP</option>
          <option value="0" <%=(rd!=null&&rd.getInt("DRMUser","userLoginOnlyThisIP",0)==0)?"selected":""%>>范围约束</option>
          <option value="-1" <%=(rd!=null&&rd.getInt("DRMUser","userLoginOnlyThisIP",0)==-1)?"selected":""%>>不限定IP</option>
	    <%
        }
	    %>
        </select>
      </td>
      <td align="right">IP地址：</td>
      <td> 
        <%if(mode==1){%>
        <input type="text" name="DRMUser/userIP" value="<%=rd.getString("DRMUser","userIP",0)%>" size="15" maxlength="15">
        <%}else{%>
        <input type="text" name="DRMUser/userIP" value="" size="15" maxlength="15">
        <%}%>
      </td>
    </tr>
    <tr> 
      <td align="right">IP范围开始地址：</td>
      <td> 
        <%if(mode==1){%>
        <input type="text"size="15" maxlength="15" name="DRMUser/userStartIP" value="<%=rd.getString("DRMUser","userStartIP",0)%>">
        <%}else{%>
        <input type="text" name="DRMUser/userStartIP" value="" size="15" maxlength="15">
        <%}%>
      </td>
      <td align="right">IP范围结束地质：</td>
      <td> 
        <%if(mode==1){%>
        <input type="text" size="15" maxlength="15" name="DRMUser/userEndIP" value="<%=rd.getString("DRMUser","userEndIP",0)%>">
        <%}else{%>
        <input type="text" size="15" maxlength="15" name="DRMUser/userEndIP" value="">
        <%}%>
      </td>
    </tr>
  </table>
  </div>
  <div id="lable" align="left"><span>该用户拥有的角色:</span></div>
  <div id="list-table">
	<table class="datas" >
	<tr id="first-tr">
      <td align="center"> 
        <input type="checkbox" id="checkBoxSelect">全选 </td>
      <td>
        <p>角色编号</p>
        </td>
      <td width="60%">角色名称</td>
    </tr>
	<% if(rd!=null){	
		int rows2 = rd.getTableRowsCount("DRMRoles");
		for(int j=0;j<rows2;j++){
	%>
    <tr> 
      <td> 
        <input type="checkbox" name="DRMRole/roleID[<%=j%>]" value="<%=rd.getString("DRMRoles","roleID",j)%>" <%=rd.get("DRMUserRoles","roleID",rd.getString("DRMRoles","roleID",j))!=null?"checked":""%> class="checkBoxSelect">
      </td>
      <td><%=rd.getString("DRMRoles","roleID",j)%>&nbsp;</td>
      <td><%=rd.getString("DRMRoles","roleName",j)%>&nbsp;</td>
    </tr><%}}%>
  </table>
  </div>
</div>
<div align="center">
<input type="submit" name="Submit" id="button" value="提交" class="button">
<input type="button" name="return" id="button" value="返回" class="button" onClick="javascript:history.go(-1);">
</div>
</form>

</body>
</html>