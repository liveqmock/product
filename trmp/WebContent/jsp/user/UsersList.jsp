<%@include file="../common.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.net.URLEncoder"%>
<%
	String dispMode = request.getParameter("dispMode");
	if(dispMode == null){
		dispMode = "S";
	}
	String use = request.getParameter("use");
	if(use==null){
		use="delete";
	}	
	String userType = request.getParameter("DRMUser/userType");
	String userNO = request.getParameter("DRMUser/userNO");
	String userID = request.getParameter("DRMUser/userID");
	String userStatus = request.getParameter("DRMUser/userStatus");
	if(userType==null){
		userType=new String("E");
	}
	if(userNO==null){
		userNO=new String("");
	}
	if(userID==null){
		userID="";
	}
	if(userStatus== null){
		userStatus = "-1";
	}
	int status=0;
	if(userStatus.compareTo("-1")==0){
		status = -1;
	}
	else if(userStatus.compareTo("0")==0){
		status = 0;
	}
	else if(userStatus.compareTo("1")==0){
		status =1;
	}
	else{
		status =-1;
	}
%>
    <%
    int rows=rd.getTableRowsCount("DRMUSERs");
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("DRMUSERs")>0){
     pageNO=Integer.parseInt((String)rd.getAttr("DRMUSER", "pageNO"));
	 pageSize=Integer.parseInt((String)rd.getAttr("DRMUSER", "pageSize"));
		totalPage=(Integer)rd.getAttr("DRMUSERs", "pagesCount");
		rowsCount = (Integer)rd.getAttr("DRMUSERs", "rowsCount");
	}
%> 
<html>
<head>
<link rel="stylesheet" href="../../css/style.css" type="text/css">
<link rel="stylesheet" href="../../css/menu.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>	
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<title>系统用户列表</title>
<script src="../../js/forms.js" language="javascript"></script>
<script language="javascript">
<!--
	function lockUsers(){
		if(countOfSelectedCheckBox(UsersList)<1){
			alert("您必须至少选择一个用户才能执行该操作!");
			return false;
		}
		UsersList.action="lockUsers.";
		UsersList.submit();
	}
	function unlockUsers(){
		if(countOfSelectedCheckBox(UsersList)<1){
			alert("您必须至少选择一个用户才能执行该操作!");
			return false;
		}
		UsersList.action="unlockUsers.";
		UsersList.submit();
	}
	function deleteUsers(){
		if(countOfSelectedCheckBox(UsersList)<1){
			alert("您必须至少选择一个用户才能执行该操作!");
			return false;
		}
		UsersList.action="deleteUsers.";
		UsersList.submit();
	}
	function queryUsers(page){
		UsersList.action="queryUsers.?DRMUser/userID=&DRMUser@pageSize=10&DRMUser@pageNO="+page;
		UsersList.submit();
	}
	function gotoPage(){
		var pageno = UsersList.elements("gotopage").value;
		queryUsers(pageno);
	}

//-->
</script>
</head>
<body>
<jsp:include page="/jsp/head.jsp"/>
<menu:menuBar x="12" y="16" height="23" width="100%"/>
<%if(dispMode.indexOf("S")>=0){ %>
<form name="userInfo" method="post" action="queryUsers.?DRMUser/userID=&DRMUser@pageSize=10&DRMUser@pageNO=1">
<input type="hidden" name="use" value="<%=use%>">
<input type="hidden" name="DRMUser@orderBy" value="userID asc">
  用户类型
  <select name="DRMUser/userType">
  	<option value="">--不指定--</option>
    <option value="E" <%//userType.compareTo("E")==0?"selected":""%> >内部员工</option>
    <!-- <option value="C" <%//userType.compareTo("C")==0?"selected":""%> >客户</option>
    <option value="S" <%//userType.compareTo("S")==0?"selected":""%> >供应商</option> -->
    <option value="P" <%//userType.compareTo("P")==0?"selected":""%> >合作伙伴</option>
  </select>
  用户编号 
  <input type="text" name="DRMUser/userNO" size="10" value="<%=userNO%>">
  用户帐号 
  <input type="text" name="DRMUser/userID" size="12" value="<%=userID%>">
  用户状态 
  <select name="DRMUser/userStatus">
  	<option value ="0" <%=((status==0)?"selected":"")%>>被封锁</option>
  	<option value ="1" <%=((status==1)?"selected":"")%>>正常</option>
  	<option value ="" <%=((status==-1)?"selected":"")%>>--不指定--</option>  
  </select>
  <input type="submit" name="Submit" value="开始查询" class="button">
</form>
<%}%>
<%if(dispMode.indexOf("L")>=0){%>
<form name="UsersList" method="post" action="queryUsers.">
   <input type="hidden" name="use" value="<%=use%>">
   <input type="hidden" name="DRMUser/userNO" value="<%=userNO%>">
   <input type="hidden" name="DRMUser/userType" value="<%=userType%>" >   
   <input type="hidden" name="DRMUser/userID" value="<%=userID%>" >
   <input type="hidden" name="DRMUser/userStatus" value="<%=userStatus%>" >  
   <input type="hidden" name="DRMUser@orderBy" value="userID asc">
  <p>满足条件的用户列表：</p>
  <div id="page">
	<%String url="queryUsers.?DRMUser/userID=&DRMUser@pageSize=10&DRMUser@pageNO=";%>
	<span title="首页" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="上一页" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
					共<%=rd.getAttr("DRMUSERs","rowsCount")==null?"0":rd.getAttr("DRMUSERs","rowsCount") %> 条记录，
					每页 <%=pageSize%>条
	</span>
	<span title="下一页" class="next-page" onclick='query("<%=pageNO+1>totalPage?totalPage:pageNO+1 %>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span title="尾页" class="last-page" onclick='query("<%=totalPage%>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="go-to-page" >
		跳转到第：<input type="text" style="width: 30px;height: 15px;"/> 页
	</span>
	<span title="跳转" class="go-to-page-icon" onclick='go("<%=totalPage%>","<%=(int)Math.min(pageNO,totalPage)%>","<%=url %>")'></span>
</div>
  <table width="100%" border="1" cellspacing="0" cellpadding="0" class="hci">
    <tr class="head"> 
      <td width="9%"> 
        <input type="checkbox" name="sa" value="sa" onclick="javascript:ClickOnCheckBox(UsersList.sa,UsersList);">
        全选</td>
      <td width="11%"> 
        <div align="center">用户编号</div>
      </td>
      <td width="20%"> 
        <div align="center">用户帐号</div>
      </td>
      <td width="20%"> 
        <div align="center">用户姓名</div>
      </td>
      <td width="10%"> 
        <div align="center">用户类型</div>
      </td>
      <td width="10%"> 
        <div align="center">状态</div>
      </td>
      <td width="20%"> 
        <div align="center">最后登陆时间</div>
      </td>
    </tr>
    <%//for(int i=(ipage-1)*pageSize;i<((ipage*pageSize>rows)?rows:ipage*pageSize);i++)
	for(int i=0;i<rows;i++){ %>
    <tr class="content"> 
      <td width="9%"> 
        <input type="checkbox" name="<%="DRMUsers/userID["+i+"]"%>" value="<%=rd.getStringByDI("DRMUsers","userID",i)%>">
      </td>
      <td><%=rd.getStringByDI("DRMUsers","userNO",i)%>&nbsp;</td>
      <td><A href="queryUser.?DispMode=U&JNY_EMP/USERNO=&DRMUser/userID=<%=URLEncoder.encode(rd.getStringByDI("DRMUsers","userID",i),"GBK")%>&TA_TRAVELAGENCY/TRAVEL_AGC_ID="><%=rd.getStringByDI("DRMUsers","userID",i)%></A>&nbsp;</td>
      <td><%=rd.getStringByDI("DRMUsers","username",i)%>&nbsp;</td>
      <td><%=rd.getStringByDI("DRMUsers","userType",i).equals("E")?"内部员工":"合作伙伴"%>&nbsp;</td>
      <td><%=rd.getStringByDI("DRMUsers","userStatus",i).equals("1")?"正常":"封锁" %>&nbsp;</td>
      <td><%=rd.getStringByDI("DRMUsers","userLastLoginTime",i)%>&nbsp;</td>
    </tr>
    <%}%>
  </table>  
  <input type="button" name="del" value="删除" class="button" onclick="javascript:deleteUsers();">
  <input type="button" name="lock" value="禁止" class="button" onclick="javascript:lockUsers();">
  <input type="button" name="unlock" value="允许" class="button" onclick="javascript:unlockUsers();">
</form>
<%}%>
<!-- show the menu-->
<menu:menuItems/>
</body>
</html>	