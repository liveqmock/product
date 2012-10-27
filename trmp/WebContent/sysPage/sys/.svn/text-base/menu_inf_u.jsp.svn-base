<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>菜单信息</title>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
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
  <body>
    <div id="lable">修改当前菜单项,其中上级菜单不可修改</div>
    <form method="post" action="" onsubmit="javascript:return validate(document.forms(0));" name="mentForm">
    <input type="hidden" name="DRMMenu/menuName[0]@oldValue" value="<%=rd.getString("DRMMenu","menuName","0")%>">
    <div class="add-table">
  	<table class="datas">
      <tr>
        <td align="right">上级菜单：</td>
        <td>
          <input name="DRMMenu/parentMenuName" type="text" value="<%=rd.getString("DRMMenu","parentMenuName","0")%>" size="20" readonly>
          <input name="DRMMenu/menuName" type="hidden" value="<%=rd.getString("DRMMenu","menuName","0")%>" size="20" readonly>
        </td>
      </tr>
      <tr>
        <td align="right">菜单名称：</td>
        <td>
          <input name="DRMMenu/menuLabel" type="text" value="<%=rd.getString("DRMMenu","menuLabel","0")%>" size="50" class="m_">
        </td>
      </tr>      
      <tr>
        <td align="right">菜单URL：</td>
        <td>
          <input name="DRMMenu/funcNO" type="text" value="<%=rd.getString("DRMMenu","funcNO","0")%>" size="50">
        </td>
      </tr>
      <tr>
        <td align="right">菜单显示顺序：</td>
        <td>
          <input name="DRMMenu/dispOrder" type="text" value="<%=rd.getString("DRMMenu","dispOrder","0")%>" size="10" id="dispOrd">
        </td>
      </tr> 
      <tr>
        <td align="right">菜单级别：</td>
        <td>
          <input name="DRMMenu/menuLevel" type="text" value="<%=rd.getString("DRMMenu","menuLevel","0")%>" size="10" id="level">
        </td>
      </tr>     
    </table>
    </div>
    <div align="center">
    <input type="button" name="btnSubmit" value="提 交" id="button" onclick="doSUB();">
    <input type="reset" name="btnReset" value="重 置" id="button">
    <input type="button" name="btnReset" value="返 回" id="button" onclick="javascript:history.go(-1);">
    </div>
    </form>
  </body>
</html>