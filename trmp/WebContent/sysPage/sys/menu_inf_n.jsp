<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>�˵���Ϣ</title>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
    <script type="text/javascript">
    	function doSUB(){
			var dispOrd = document.getElementById("dispOrd").value;
			if(dispOrd == ''){

				alert("�˵���ʾ˳��Ϊ�����");
				return false;
			}
				
			document.mentForm.action="newMenu.";
			document.mentForm.submit();
    	}
    </script>
  </head>
  <body>
    <div id="lable">�����µĲ˵���</div>
    <form method="post" action="newMenu." onsubmit="javascript:return validate(document.forms(0));" name="mentForm">
    <div class="add-table">
  	<table class="datas">
      <tr>
        <td align="right">�ϼ��˵���</td>
        <td>
          <input name="DRMMenu/parentMenuName" type="text" value="<%=rd.getString("parentMenuName")%>" size="20" readonly>
          <a href="querySubMenu.?parentMenuName=<%=rd.getString("parentMenuName")%>">ת���ü��˵��б�</a>
        </td>
      </tr>
      <tr>
        <td align="right">�˵����ƣ�</td>
        <td>
          <input name="DRMMenu/menuLabel" type="text" value="" size="50" class="m_">
        </td>
      </tr>
      <tr>
        <td align="right">�˵�URL��</td>
        <td>
          <input name="DRMMenu/funcNO" type="text" value="" size="50">
        </td>
      </tr>
      <tr>
        <td align="right">�˵���ʾ˳��</td>
        <td>
          <input name="DRMMenu/dispOrder" type="text" value="" size="10" id="dispOrd">
        </td>
      </tr>
      <tr>
        <td align="right">�˵�����</td>
        <td>
          <input name="DRMMenu/menuLevel" type="text" value="<%=rd.getString("MENULEVEL")%>" size="10" readonly="readonly">
        </td>
      </tr>
    </table>
    </div>
    <div align="center">
    <input type="button" name="btnSubmit" value="�� ��" id="button" onclick="doSUB();">
    <input type="reset" name="btnReset" value="�� ��" id="button">
    <input type="button" name="goback" value="�� ��" id="button" onclick="javascript:history.go(-1);"></div>
    </form>
  </body>
</html>