<%@include file="../common.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.net.URLEncoder"%>
<%
	int mode =0;// 0- new ,1,Update；
	String dispMode = request.getParameter("DispMode");
	if(dispMode == null){
		dispMode = "N";
	}
	if(dispMode.compareTo("U") ==0){
		mode = 1;
	}	
	String userID= request.getParameter("DRM/userID");

%>
<html>
<head>
<link rel="stylesheet" href="../../css/style.css" type="text/css">
<link rel="stylesheet" href="../../css/menu.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>

<script type="text/javascript">
        var GB_ROOT_DIR = "<%=request.getContextPath()%>/greybox/";
</script>

<script type="text/javascript" src="<%=request.getContextPath()%>/greybox/AJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/greybox/AJS_fx.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/greybox/gb_scripts.js"></script>
<link href="<%=request.getContextPath()%>/greybox/gb_styles.css" rel="stylesheet" type="text/css" media="all" />
<title>系统用户基本信息</title>
<script language="javascript" src="../../js/forms.js"></script>
<script language="javascript">
<!--
	function validate(){
		var insert=<%=mode%>;
		if(insert==0){
			if(document.forms(0).elements("DRMUser/userNO").value=="" || document.forms(0).elements("DRMUser/userID").value==""){
				alert("对不起,您必须输入'用户编号'和'用户帐号'!");
				return false;
			}
		}

		if(document.forms(0).elements("DRMUser/userPassword").value!=document.forms(0).elements("DRMUser/userConfirmPassword").value){
			alert("对不起,'密码' 和 '确认密码' 不匹配，请重新输入!");
			return false;
		}
		if($("#userFromNm").val()==""||$("#userFromNm").val()==null){
			alert("对不起,用户来源为空,请选择用户来源!");
			return false;
		}
		return true;
	}

	function fromWhere(){

		var userType = document.getElementById("userType").value;
		var url = "";
		//内部员工		
		url = "<%=request.getContextPath() %>/jsp/org/empsHomeForUserAdd.jsp";
		
		//合作伙伴
		//url = "<%=request.getContextPath()%>/baseMng/listTravelAgcForUserAdd.?TA_TRAVELAGENCY@pageNO=1&TA_TRAVELAGENCY@pageSize=10&TA_TRAVELAGENCY/city_id=";
		window.open(url,'obj','width=800, height=400, top=50, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
//-->

GB_myShow = function(caption, url, /* optional */ height, width, callback_fn) {
    var options = {
        caption: caption,
        height: height || 500,
        width: width || 700,
        fullscreen: false,
        show_loading: false,
        callback_fn: callback_fn
    };
    var win = new GB_Window(options);
    return win.show(url);
};
</script>
</head>
<body>
<jsp:include page="/jsp/head.jsp"/>
<menu:menuBar x="12" y="16" height="23" width="100%"/>
<form name="userInfo" method="post" action="<%=(mode==0)?"newUser.":"updateUser."%>" onsubmit="return validate();">
<% if(mode ==1){%>
	<input type=hidden name="DRMUser/userID" value="<%=rd.getString("DRMUser","userID",0)%>">
	<input type=hidden name="DRMUser/userID[0]@oldValue" value="<%=rd.getString("DRMUser","userID",0)%>">
<%}%>		

    <%if(mode==0){%>
      <p>您正在添加新的用户： </P>
    <%}else if(mode ==1){%>
      <p>您正在更改用户信息： <p>
    <%}%>
  <table width="100%" border="0" cellspacing="1" cellpadding="0" class="hci">
    <tr>
      <td width="20%" class="head">用户编号</td>
      <td width="30%" class="content"> 
        <%if(mode==0){%>
        <input type="text" name="DRMUser/userNO" size="15" maxlength="15" value="<%=rd.getString("maxUserNo") %>" readonly="readonly">
        <%}else{%>
        <input type="text" name="DRMUser/userNO" size="15" maxlength="15" value="<%=rd.getString("DRMUser","userno",0) %>" readonly="readonly">
        <%}%>
      </td>
      <td width="20%" class="head">用户类型</td>
      <td width="30%" class="content"> 
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
      <td width="20%" class="head">用户帐号</td>
      <td width="30%" class="content"> 
        <%if(mode==0){%>
        <input type="text" name="DRMUser/userID" value="" size="15" maxlength="15">
        <%}else{%>
        <%=rd.getString("DRMUser","userID",0)%> 
        <%}%>
        <font color="#FF0000">*</font>系统登陆名
      </td>
      <td width="20%" class="head">用户来源</td>
      <td width="30%" class="content">
        <%if(mode==1){
        //修改
        %>
        <input type="hidden" name="DRMUser/EMPID" id="userFrom" value="<%=rd.getString("DRMUser","EMPID",0)%>"/>
        <input type="text" name="DRMUser/USERFROM" onclick="return GB_myShow('选择用户来源','<%=request.getContextPath() %>/jsp/org/empsHomeForUserAdd.jsp','800','750','');" value="<%=rd.getString("DRMUser","USERFROM",0)%>" id="userFromNm" readonly="readonly"/>
        <%}else{
        //添加
        %>
        <input type="hidden" name="DRMUser/EMPID" id="userFrom"/>
        <input type="text" name="DRMUser/USERFROM" onclick="return GB_myShow('选择用户来源','<%=request.getContextPath() %>/jsp/org/empsHomeForUserAdd.jsp','800','750','');" id="userFromNm" readonly="readonly"/>
        <%}%>
        <font color="#FF0000">*</font>选择用户来源
      </td>
    </tr>
    <tr> 
      <td width="20%" class="head">密&nbsp;&nbsp;&nbsp;&nbsp;码</td>
      <td width="30%" class="content"> 
        <%if(mode==1){%>
        <input type="password" name="DRMUser/userConfirmPassword" value="<%=rd.getString("DRMUser","userPassword",0)%>" size="15" maxlength="15">
        <%}else {%>
        <input type="password" name="DRMUser/userConfirmPassword" value="" size="15" maxlength="15">
        <%}%>
      </td>
      <td width="20%" class="head">确认密码</td>
      <td width="30%" class="content"> 
        <%if(mode==1){%>
        <input type="password" name="DRMUser/userPassword" value="<%=rd.getString("DRMUser","userPassword",0)%>" size="15" maxlength="15">
        <%}else{%>
        <input type="password" name="DRMUser/userPassword" value="" size="15" maxlength="15">
        <%}%>
      </td>
    </tr>
    <tr>
      <td width="20%" class="head">IP地址验证模式</td>
      <td width="30%" class="content"> 
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
      <td width="20%" class="head">用户姓名</td>
      <td width="30%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMUser/username" value="<%=rd.getString("DRMUser","username",0)%>" size="15" maxlength="15" id="username">
        <%}else{%>
        <input type="text" name="DRMUser/username" value="" size="15" maxlength="15" id="username">
        <%}%>
        <font color="#FF0000">*</font>员工姓名
      </td>
    </tr>
    <tr> 
      <td width="20%" class="head">IP范围开始地址</td>
      <td width="30%" class="content"> 
        <%if(mode==1){%>
        <input type="text"size="15" maxlength="15" name="DRMUser/userStartIP" value="<%=rd.getString("DRMUser","userStartIP",0)%>">
        <%}else{%>
        <input type="text" name="DRMUser/userStartIP" value="" size="15" maxlength="15">
        <%}%>
      </td>
      <td width="20%" class="head">IP范围结束地质</td>
      <td width="30%" class="content"> 
        <%if(mode==1){%>
        <input type="text" size="15" maxlength="15" name="DRMUser/userEndIP" value="<%=rd.getString("DRMUser","userEndIP",0)%>">
        <%}else{%>
        <input type="text" size="15" maxlength="15" name="DRMUser/userEndIP" value="">
        <%}%>
      </td>
    </tr>
    <tr>
      <td width="20%" class="head">IP地址</td>
      <td width="30%" class="content"> 
        <%if(mode==1){%>
        <input type="text" name="DRMUser/userIP" value="<%=rd.getString("DRMUser","userIP",0)%>" size="15" maxlength="15">
        <%}else{%>
        <input type="text" name="DRMUser/userIP" value="" size="15" maxlength="15">
        <%}%>
      </td>
      <td width="20%" class="head">状态</td>
      <td width="30%" class="content"> 
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
  </table>
  <p>该用户拥有的角色:</P>
  <table width="100%" border="0" cellspacing="1" cellpadding="0" class="hci">
    <tr class="head"> 
      <td width="10%"> 
        <input type="checkbox" name="sa" value="sa" onclick="javascript:ClickOnCheckBox(userInfo.sa,userInfo);">
        全选 </td>
      <td width="30%">
        <p>角色编号</p>
        </td>
      <td width="60%">角色名称</td>
    </tr>
	<% if(rd!=null){	
		int rows2 = rd.getTableRowsCount("DRMRoles");
		for(int j=0;j<rows2;j++){
	%>
    <tr class="content"> 
      <td width="10%"> 
        <input type="checkbox" name="DRMRole/roleID[<%=j%>]" value="<%=rd.getString("DRMRoles","roleID",j)%>" <%=rd.get("DRMUserRoles","roleID",rd.getString("DRMRoles","roleID",j))!=null?"checked":""%> >
      </td>
      <td width="30%"><%=rd.getString("DRMRoles","roleID",j)%>&nbsp;</td>
      <td width="60%"><%=rd.getString("DRMRoles","roleName",j)%>&nbsp;</td>
    </tr><%}}%>
  </table>
<input type="submit" name="Submit" value="提交" class="button">
<input type="button" name="return" value="返回" class="button" onClick="javascript:history.go(-1);">
</form>

<!-- show the menu-->
<menu:menuItems/>
</body>
</html>