<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common.jsp"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%
%>
<html>
  <head>
    <title>菜单信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <link rel="stylesheet" href="../../css/style.css" type="text/css">
    <link rel="stylesheet" href="../../css/menu.css" type="text/css">    
    <script src="../../js/forms.js" type="text/javascript"></script>
    <script type="text/javascript">
    	function doSUB(){
			var level = document.getElementById("level").value;
			var dispOrd = document.getElementById("dispOrd").value;
			if(level == '' || dispOrd == ''){

				alert("菜单显示顺序和菜单级别为必输项！");
				return false;
			}
				
			document.mentForm.action="newMenu.";
			document.mentForm.submit();
    	}
    </script>
  </head>
  <body onload="javascript:init(document.forms(0));">
    <jsp:include page="/jsp/head.jsp"/>
    <menu:menuBar x="12" y="16" height="23" width="100%"/>
    <p>增加新的菜单项</p>
    <form method="post" action="newMenu." onsubmit="javascript:return validate(document.forms(0));" name="mentForm">
    <table class="hci" border="0" cellpadding="0" width="100%">
      <tr>
        <td class="head" width="20%">上级菜单</td>
        <td class="content"  width="80%">
          <input name="DRMMenu/parentMenuName" type="text" value="<%=rd.getString("parentMenuName")%>" size="20" readonly>
          <a href="querySubMenu.?parentMenuName=<%=rd.getString("parentMenuName")%>">转到该级菜单列表</a>
        </td>
      </tr>
      <tr>
        <td class="head" width="20%">菜单名称</td>
        <td class="content"  width="80%">
          <input name="DRMMenu/menuName" type="text" value="" size="20" class="m_focus">
        </td>
      </tr>
      <tr>
        <td class="head" width="20%">菜单标题</td>
        <td class="content"  width="80%">
          <input name="DRMMenu/menuLabel" type="text" value="" size="50" class="m_">
        </td>
      </tr>
      <tr>
        <td class="head" width="20%">菜单URL</td>
        <td class="content"  width="80%">
          <input name="DRMMenu/funcNO" type="text" value="" size="50">
        </td>
      </tr>
      <tr>
        <td class="head" width="20%">菜单显示顺序</td>
        <td class="content"  width="80%">
          <input name="DRMMenu/dispOrder" type="text" value="" size="10" id="dispOrd">
        </td>
      </tr>
      <tr>
        <td class="head" width="20%">菜单级别</td>
        <td class="content"  width="80%">
          <input name="DRMMenu/menuLevel" type="text" value="" size="10" id="level">
        </td>
      </tr>
    </table>
    <input type="button" name="btnSubmit" value="提交" class="button" onclick="doSUB();">
    <input type="reset" name="btnReset" value="重置" class="button">
    </form>
    <menu:menuItems/>
  </body>
</html>