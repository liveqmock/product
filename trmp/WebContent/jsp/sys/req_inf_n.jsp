<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common.jsp"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%
%>
<html>
  <head>
    <title>新增客户请求信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <link rel="stylesheet" href="../../css/style.css" type="text/css">
    <link rel="stylesheet" href="../../css/menu.css" type="text/css">    
    <script src="../../js/forms.js" type="text/javascript"></script>
  </head>
  <body onload="javascript:init(document.forms(0));">
    <jsp:include page="/jsp/head.jsp"/>
    <menu:showBar x="12" y="16" height="23" width="100%"/>
    <p>增加新的菜单项</p>
    <form method="post" action="newMenu." onsubmit="javascript:return validate(document.forms(0));">
    <table class="hci" border="0" cellpadding="0" width="100%">
      <tr>
        <td class="head" width="20%">请求名称</td>
        <td class="content"  width="80%">
          <input name="name" type="text" value="<%=rd.getString("name")%>" size="20" >
        </td>
      </tr>
      <tr>
        <td class="head" width="20%">Model列表</td>
        <td class="content"  width="80%">
          <input name="DRMMenu/menuName" type="text" value="" size="20" class="m_focus">
        </td>
      </tr>
      <tr>
        <td class="head" width="20%">视图列表</td>
        <td class="content"  width="80%">
          <input name="DRMMenu/menuLabel" type="text" value="" size="50" class="m_">
        </td>
      </tr>
    </table>
    <input type="submit" name="btnSubmit" value="提交" class="button">
    <input type="reset" name="btnReset" value="重置" class="button">
    </form>
    <menu:show/>
  </body>
</html>