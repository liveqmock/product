<%@include file="/common.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.net.URLEncoder"%>
<form name="EP" method="post" action="hr.Employee.saveEmpPosts.">
<input type="hidden" name="_REDIRECT" value="hr.Employee.searchEmps.?dispMode=EP">
<input type="hidden" name="empID" value="<%=rd.getString("DRMEmployee","empID",0)%>">
  <p>员工号：<%=rd.getString("DRMEmployee","empID",0)%>姓名：<%=rd.getString("DRMEmployee","empName",0)%>所在部门号：<%=rd.getString("DRMEmployee","deptID",0)%>所在部门名称：<%=rd.getString("DRMDepartment","deptName",0)%></p>
  <p>员工所在部门内的职位列表：</p>
  <table width="100%" border="1" class="hci" cellpadding="0" cellspacing="0">
    <tr class="head"> 
      <td width="14%">选择</td>
      <td width="29%">职位编号</td>
      <td width="57%">职位名称</td>
    </tr>
	<%int rows = rd.getTableRowsCount("DRMPosts");
	for(int i=0;i<rows;i++){%>
    <tr class="content">
      <td width="14%">
        <input type="checkbox" name="DRMPost/postID[<%=i%>]" value="<%=rd.getString("DRMPosts","postID",i)%>" <%=rd.get("DRMEmployeePost","postID",rd.getString("DRMPosts","postID",i))!=null?"checked":""%>>
      </td>
      <td width="29%"><%=rd.getString("DRMPosts","postID",i)%>&nbsp;</td>
      <td width="57%"><%=rd.getString("DRMPosts","postName",i)%>&nbsp;</td>
    </tr>
	<%}%>
  </table>
  <p> 
    <input type="submit" name="Submit" value="提交">
    <input type="button" name="return" value="返回" onclick="javascript:history.go(-1);">
  </p>
</form>
