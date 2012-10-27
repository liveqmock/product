<%@page contentType="text/html;charset=GBK"%>
<%@include file="/jsp/common.jsp"%>
<%
	String tableName=null;
	String jspRoot=null;
	String daoName=request.getParameter("daoName");
	tableName=request.getParameter("tableName");
	jspRoot=request.getParameter("jspRoot");
	if(jspRoot==null){
		jspRoot="/jsp";
	}
%>
<head>
<title>自动生成JSP</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" href="../../css/style.css" type="text/css">
<script src="../..//js/forms.js" type="text/javascript"></script>
</head>

<body onload="init(document.forms(0))">
<form name="info" method="POST" action="makeJSP." onsubmit="javascript:return validate(document.forms(0));">
  <input type="hidden" name="daoName" value="<%=daoName%>">
  <input type="hidden" name="jspRoot" value="<%=jspRoot%>">
  <p><b>您正在为一个表自动生成JSP代码</b> </p>
  <i>当前表基本信息： </i> 
  <table width="100%" border="1" cellspacing="0" cellpadding="0" class="hci">
    <tr> 
      <td width="40%" class="head">表名</td>
      <td width="60%" class="content">
        <input type="text" name="tableName" value="<%=tableName%>" size="30" readonly>
      </td>
    </tr>
  </table>
  <table height="10" width="100%"><tr><td>&nbsp;</td></tr></table>  
  <i>请确定JSP自动生成选项</i> 
  <table width="100%" border="1" cellspacing="0" cellpadding="0" class="hci" id="detail">
    <tr class="content"> 
      	<td>保存目录&nbsp;<input type="text" name="save_dir" value="" ><br><font size="1">说明:该目录位于/jsp/下,假设目录名指定为AAA,则生成的JSP代码保存在/jsp/AAA/下,如果未指定则JSP被保存在/jsp下.</font></br></td>
    </tr>
    <tr class="content"> 
      	<td><input type="checkbox" name="new_jsp" value="yes" >新增JSP&nbsp;<i>action:<input type="text" name="newAction" size="15" value=""><input type="checkbox" name="new_jsp_multi_col" value="yes">双列显示&nbsp;<input type="checkbox" name="new_jsp_menu" value="yes">显示菜单&nbsp;文件名<input type="text" name="new_jsp_name" value="<%="new_"+tableName+".jsp"%>"></i></td>
    </tr>
    <tr class="content"> 
      	<td><input type="checkbox" name="rd_jsp" value="yes" >只读JSP&nbsp;<i>action:<input type="text" name="" size="15"  value="无action" disabled><input type="checkbox" name="rd_jsp_multi_col" value="yes">双列显示&nbsp;<input type="checkbox" name="rd_jsp_menu" value="yes">显示菜单&nbsp;文件名<input type="text" name="rd_jsp_name" value="<%="rd_"+tableName+".jsp"%>"></i></td>
    </tr>
    <tr class="content"> 
	<td><input type="checkbox" name="update_jsp" value="yes" >更新JSP&nbsp;<i>action:<input type="text" size="15"  name="updateAction" value=""><input type="checkbox" name="update_jsp_multi_col" value="yes">双列显示&nbsp;<input type="checkbox" name="update_jsp_menu" value="yes">显示菜单&nbsp;文件名<input type="text" name="update_jsp_name" value="<%="update_"+tableName+".jsp"%>"></i></td>
    </tr>
    <tr class="content">       	
      	<td><input type="checkbox" name="list_jsp" value="yes" >列表JSP&nbsp;<i>action:<input type="text" size="15"  name="listAction" value=""><input type="checkbox" name="pagination" value="yes">分页显示&nbsp;<input type="checkbox" name="selectCB" value="yes">选择框&nbsp;<input type="checkbox" name="search" value="yes">检索栏&nbsp;<input type="checkbox" name="list_jsp_menu" value="yes">显示菜单&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文件名:<input type="text" name="list_jsp_name" value="<%="list_"+tableName+".jsp"%>"></i></td>
    </tr>
  </table>
  <input type="submit" name="btnSubmit" value="生成" class="button">
  <input type="reset" name="btnReset" value="复位" class="button">
</form>
</body>

