<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<%@page import="java.net.URLEncoder"%>
<%
	
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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<title>ϵͳ�û��б�</title>
<script language="javascript">

/**
*	checkBox��ȫѡ��ȫ��ѡ
*/
$(document).ready(function() {	
	$('[type=checkbox]').click(function (){
		var checkClass = $(this).attr("class");
		if(checkClass){
			if($("."+checkClass).is(":checked")){
				$("#"+checkClass).attr("checked","checked");
			}else{
				$("#"+checkClass).removeAttr("checked");
			}
		}
		var checkID = $(this).attr("id");
		if(checkID){
			if($("#"+checkID).is(":checked")){
				$("."+checkID).each(function (){
					$(this).attr("checked","checked");
				});
			}else{
				$("."+checkID).each(function (){
					$(this).removeAttr("checked");
				});
			}
		}
	});
});
	
	function lockUsers(){
	
		document.UsersList.action="lockUsers.";
		document.UsersList.submit();
	}
	function unlockUsers(){
		
		document.UsersList.action="unlockUsers.";
		document.UsersList.submit();
	}
	function queryUsers(page){
		document.UsersList.action="queryUsers.?DRMUser/userID=&hrDepartment/deptid=<%=rd.getStringByDI("hrdepartments", "deptid", 0)%>&DRMUser@pageSize=10&DRMUser@pageNO="+page;
		document.UsersList.submit();
	}
	function gotoPage(){
		var pageno = UsersList.elements("gotopage").value;
		queryUsers(pageno);
	}

	function addUser(){
		window.location.href="queryUser.?dispMode=N&TA_TRAVELAGENCY/TRAVEL_AGC_ID=";
	}
	function enterkey() {
	    try{
	        if(event.keyCode==13){
	        	event.keyCode=9; 
	        }
	    }catch(e) {
	        return;
	    }
	}

</script>
</head>
<body>
<form name="userInfo" method="post" action="queryUsers.?DRMUser@pageSize=10&DRMUser@pageNO=1&drmuser@orderby=userno">
<div id="lable"><span>�û���ѯ</span></div>

  <table class="add-table">
    <tr>
	  <td width="10%">�û�������</td>
	  <td width="10%"><input type="text" name="DRMUser/userName" size="10" value=""></td>
	  <td width="10%">�û��ʺţ�</td>
	  <td width="10%"><input type="text" name="DRMUser/userID" size="12" value=""></td>
	  <td width="10%">�û�״̬ </td>
	  <td width="10%"><select name="DRMUser/USERSTATUS">
	  	<option value ="" selected>***��ָ��***</option>  
	  	<option value ="0">������</option>
	  	<option value ="1">����</option>
	  	</select>
	  </td>
	  <td><input type="submit" name="Submit" value="�� ѯ" id="button"></td>
	  </tr>
	</table>
</form>

<form action="" name="UsersList">
  <div id="lable"><span>�����������û��б�</span></div>
  <div class="select-div" onclick="addUser();"><span id="add-icon"></span><span style="cursor: pointer;" class="text">���</span>&nbsp;</div>
  <div class="select-div" onclick="lockUsers();"><span id="del-icon"></span><span style="cursor: pointer;" class="text">��ֹ</span>&nbsp;</div>
  <div class="select-div" onclick="unlockUsers();"><span id="ok-icon"></span><span style="cursor: pointer;" class="text">����</span></div>
  <table class="datas" >
    <tr id="first-tr">
      <td width="5%" align="center"> 
        <input type="checkbox" id="checkboxTop">ȫѡ</td>
      <td width="20%" align="center">��½�ʺ�</td>
      <td width="20%" align="center">�û�����</td>
      <td width="10%" align="center">״̬</td>
      <td width="20%" align="center">����½ʱ��</td>
      <td width="25%" align="center">����</td>
    </tr>
    <%
	for(int i=0;i<rows;i++){
		String userid = rd.getStringByDI("DRMUsers","userID",i);
		String userNO = rd.getStringByDI("DRMUsers","userNO",i);
		String deptId = rd.getStringByDI("DRMUsers","deptid",i);
	%>
    <tr class="content" > 
      <td align="center"> 
        <input type="checkbox" class="checkboxTop" name="DRMUser/userNO[<%=i %>]" value="<%=userNO%>">
      </td>
      <td align="center"><%=rd.getStringByDI("DRMUsers","userID",i)%>&nbsp;</td>
      <td align="center"><%=rd.getStringByDI("DRMUsers","username",i)%>&nbsp;</td>
      <td align="center"><%=rd.getStringByDI("DRMUsers","userStatus",i).equals("1")?"����":"����" %>&nbsp;</td>
      <td align="center"><%=rd.getStringByDI("DRMUsers","userLastLoginTime",i)%>&nbsp;</td>
      <td align="center"><a href="queryUser.?DispMode=U&DRMUser/userID=<%=userid %>&DRMUser/userNO=<%=userNO %>&hrDepartment/deptid=<%=deptId %>">�޸ļ�Ȩ�޷���</a></td>
    </tr>
    <%}%>
  </table>  
  <div id="page">
	<%String url=request.getContextPath()+"/sysPage/user/queryUsers.?DRMUser/userID=&DRMUser@pageSize=10&drmuser@orderby=userno&DRMUser@pageNO=";%>
	<span title="��ҳ" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="��һҳ" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					��<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> ҳ��
					��<%=rd.getAttr("DRMUSERs","rowsCount")==null?"0":rd.getAttr("DRMUSERs","rowsCount") %> ����¼��
					ÿҳ <%=pageSize%>��
	</span>
	<span title="��һҳ" class="next-page" onclick='query("<%=pageNO+1>totalPage?totalPage:pageNO+1 %>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span title="βҳ" class="last-page" onclick='query("<%=totalPage%>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="go-to-page" >
		��ת���ڣ�<input type="text" id="gotopage" style="width: 30px;height: 15px;" onkeydown="enterkey()"/> ҳ
	</span>
	<span title="��ת" class="go-to-page-icon" onclick='go("<%=totalPage%>","<%=(int)Math.min(pageNO,totalPage)%>","<%=url %>")'></span>
  </div>
</form>
</body>
</html>	