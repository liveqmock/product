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
			var dispOrd = document.getElementById("dispOrd").value;
			if(dispOrd == ''){

				alert("菜单显示顺序为必输项！");
				return false;
			}
				
			document.mentForm.action="newMenu.";
			document.mentForm.submit();
    	}
    </script>
  </head>
  <body>
    <div id="lable">增加新的菜单项</div>
    <form method="post" action="newMenu." onsubmit="javascript:return validate(document.forms(0));" name="mentForm">
    <div class="add-table">
  	<table class="datas">
      <tr>
        <td align="right">上级菜单：</td>
        <td>
          <input name="DRMMenu/parentMenuName" type="text" value="<%=rd.getString("parentMenuName")%>" size="20" readonly>
          <a href="querySubMenu.?parentMenuName=<%=rd.getString("parentMenuName")%>">转到该级菜单列表</a>
        </td>
      </tr>
      <tr>
        <td align="right">菜单名称：</td>
        <td>
          <input name="DRMMenu/menuLabel" type="text" value="" size="50" class="m_">
        </td>
      </tr>
      <tr>
        <td align="right">菜单URL：</td>
        <td>
          <input name="DRMMenu/funcNO" type="text" value="" size="50">
        </td>
      </tr>
      <tr>
        <td align="right">菜单显示顺序：</td>
        <td>
          <input name="DRMMenu/dispOrder" type="text" value="" size="10" id="dispOrd">
        </td>
      </tr>
      <tr>
        <td align="right">菜单级别：</td>
        <td>
          <input name="DRMMenu/menuLevel" type="text" value="<%=rd.getString("MENULEVEL")%>" size="10" readonly="readonly">
        </td>
      </tr>
    </table>
    </div>
    <div align="center">
    <input type="button" name="btnSubmit" value="提 交" id="button" onclick="doSUB();">
    <input type="reset" name="btnReset" value="重 置" id="button">
    <input type="button" name="goback" value="返 回" id="button" onclick="javascript:history.go(-1);"></div>
    </form>
  </body>
</html>