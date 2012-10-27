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
				
			document.mentForm.action="updateMenu.";
			document.mentForm.submit();
    	}
    </script>
  </head>
  <body onload="javascript:init(document.forms(0));">
    <jsp:include page="/jsp/head.jsp"/>
    <menu:menuBar x="12" y="16" height="23" width="100%"/>
    <p>修改当前菜单项,其中上级菜单名称和本菜单名称不可修改</p>
    <form method="post" action="" onsubmit="javascript:return validate(document.forms(0));" name="mentForm">
    <input type="hidden" name="DRMMenu/menuName[0]@oldValue" value="<%=rd.getString("DRMMenu","menuName","0")%>">
    <table class="hci" border="0" cellpadding="0" width="100%">
      <tr>
        <td class="head" width="20%">上级菜单</td>
        <td class="content"  width="80%">
          <input name="DRMMenu/parentMenuName" type="text" value="<%=rd.getString("DRMMenu","parentMenuName","0")%>" size="20" readonly>
        </td>
      </tr>
      <tr>
        <td class="head" width="20%">菜单名称</td>
        <td class="content"  width="80%">
          <input name="DRMMenu/menuName" type="text" value="<%=rd.getString("DRMMenu","menuName","0")%>" size="20" readonly>
        </td>
      </tr>
      <tr>
        <td class="head" width="20%">菜单标题</td>
        <td class="content"  width="80%">
          <input name="DRMMenu/menuLabel" type="text" value="<%=rd.getString("DRMMenu","menuLabel","0")%>" size="50" class="m_">
        </td>
      </tr>      
      <tr>
        <td class="head" width="20%">菜单URL</td>
        <td class="content"  width="80%">
          <input name="DRMMenu/funcNO" type="text" value="<%=rd.getString("DRMMenu","funcNO","0")%>" size="50">
        </td>
      </tr>
      <tr>
        <td class="head" width="20%">菜单显示顺序</td>
        <td class="content"  width="80%">
          <input name="DRMMenu/dispOrder" type="text" value="<%=rd.getString("DRMMenu","dispOrder","0")%>" size="10" id="dispOrd">
        </td>
      </tr> 
      <tr>
        <td class="head" width="20%">菜单级别</td>
        <td class="content"  width="80%">
          <input name="DRMMenu/menuLevel" type="text" value="<%=rd.getString("DRMMenu","menuLevel","0")%>" size="10" id="level">
        </td>
      </tr>     
    </table>
    <input type="button" name="btnSubmit" value="提交" class="button" onclick="doSUB();">
    <input type="reset" name="btnReset" value="重置" class="button">
    </form>
    <menu:menuItems/>
  </body>
</html>