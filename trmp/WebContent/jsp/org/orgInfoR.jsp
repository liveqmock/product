<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.net.URLEncoder"%>
<%@include file="/common.jsp"%>
<%
	//if back is equal to yes,then the back button is displayed,
	//otherwise back button is not displayed;
	String back=request.getParameter("back");
	if(back==null){
		back="yes";
	}
%>
<link rel="stylesheet" href="../css/Style.css" type="text/css">
��ǰ�����Ļ�����Ϣ���£� 
<table width="100%" border="1" cellspacing="0" cellpadding="0">
  <tr>
    <td width="10%" class="head" nowrap>��������</td>
    <td class="content"><%=rd.getString("HROrganization","name",0)%></td>
    <td width="10%" class="head" nowrap></td>
    <td width="15%" class="content" nowrap></td>
  </tr>
  <tr> 
    <td width="10%" class="head" nowrap>����</td>
    <td  class="content" nowrap width="15%"><%=rd.getString("HROrganization","type",0)%></td>
    <td  class="head" width="10%" nowrap>�ϼ���������</td>
    <td  class="content"><%=rd.getString("HROrganization","parent_orgName",0)%></td>
  </tr>
  <tr> 
    <td width="10%" class="head" nowrap>�绰</td>
    <td class="content" nowrap colspan="3"><%=rd.getString("HROrganization","tel",0)%></td>
    <td  class="head" width="10%" nowrap>�ϼ���������</td>
    <td  class="content"><%=rd.getString("HROrganization","parent_orgName",0)%></td>
  </tr>
  <tr> 
    <td width="10%" class="head" nowrap>EMail</td>
    <td class="content" nowrap colspan="3"><%=rd.getString("HROrganization","email",0)%></td>
  </tr>
  <tr> 
    <td width="10%"  class="head" nowrap>WEB��ַ</td>
    <td  class="content" nowrap colspan="3"><%=rd.getString("HROrganization","web",0)%></td>
  </tr>
</table>
<%if(back.compareTo("yes")==0){%>
  <A href="javascript:history.go(-1);">����</A>
<%}%> 