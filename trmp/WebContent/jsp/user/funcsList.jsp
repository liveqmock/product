<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common.jsp"%>
<%
  String tNames="DRMFunctions";
  int rows=rd.getTableRowsCount(tNames);  
  String roleID = request.getParameter("DRMRole/roleID");
%>
<html>
  <head>
    <title>添加功能到角色</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <link rel="stylesheet" href="../../css/style.css" type="text/css">
    <link rel="stylesheet" href="../../css/menu.css" type="text/css">
    <script src="../../js/forms.js" type="text/javascript"></script>
    <script language="javascript">
    <!--
        function check_form(frm){
		if(countOfSelectedCheckBox(frm)<1){
			alert("对不起,您必须选择一个或多个功能才能执行当前操作.");
			return false;
		}else{
        		return true;
        	}
        }        
    //-->
    </script>
  </head>
  <body>
  <!-- show the menu bar-->
  <jsp:include page="/jsp/head.jsp"/>
  <menu:menuBar x="12" y="16" height="23" width="100%"/>
  
  <form name="info" method="post" action="addFuncs2Role." onsubmit="return check_form(document.forms(0))">
    <input type="hidden" name="DRMRole/roleID" value="<%=roleID%>">
    <p>尚未赋给当前角色<font color="red"><%=roleID%></font>的功能列表：</p>
    <table class="hci" border="0" cellspacing="1" cellpadding="0" width="100%">
      <tr class="head">
      	<td width="10%">&nbsp;</td>
        <td width="40%">请求名称</td>
        <td width="50%">说明</td>
      </tr>
      <%for(int i=0;i<rows;i++){%>	
      <tr>
        <td width="10%" class="content"><input type="checkbox" name="DRMFunction/funcNO[<%=i%>]" value="<%=rd.getString(tNames,"funcNO",i)%>" ></td>
        <td class="content"  width="40%">
          <%=rd.getString(tNames,"funcNO",i)%>
        </td>
        <td class="content"  width="50%" >
          <%=rd.getStringByDI(tNames,"funcName",i)%>&nbsp;
        </td>
      </tr>
      <%}%>
    </table>
    <input type="submit" name="btnSubmit" value="提交">
    <input type="button" name="btnReset" value="返回" onclick="javascript:history.go(-1);">
  </form>
  <!-- show the menu-->
  <menu:menuItems/>  
  </body>
</html>