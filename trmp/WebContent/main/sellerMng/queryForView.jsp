<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.mysql.jdbc.Blob"%>
<%@page import="java.io.InputStreamReader"%>
<%@include file="../../common.jsp"%>
<%@ taglib uri="/WEB-INF/tld/FCKeditor.tld" prefix="FCK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>������ϸ</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>

</head>
<body>

<div class="add-table"> 
  <table class="datas">
	<tr class="first-tr">
	  <td colspan="4"><span>������Ϣ�������ţ�<%=rd.getStringByDI("rsView","id",0) %>��</span></td>
	</tr>
	<tr>
	  <td align="right">��·����</td>
	  <td colspan="3"><%=rd.getStringByDI("rsView","line_name",0) %></td>
	</tr>
	<tr>
	  <td width="20%" align="right">����ʱ�䣺</td>
	  <td width="30%"><%=rd.getStringByDI("rsView","regedit_time",0) %></td>
	  <td width="20%" align="right">�۸�</td>
	  <td width="30%">�ܼ۸�<%=rd.getStringByDI("rsView","msj",0) %>  ����ۣ�<%=rd.getStringByDI("rsView","thj",0) %></td>
	</tr>
	<tr>
	  <td align="right">�������ڣ�</td>
	  <td><%=rd.getStringByDI("rsView","begin_date",0) %></td>
	  <td align="right">�������ڣ�</td>
	  <td><%=rd.getStringByDI("rsView","end_date",0) %></td>
	</tr>
	<tr height="30">
	  <td align="right">������ע��</td>
	  <td colspan="3"><%=rd.getStringByDI("rsView","remark",0) %></td>
	</tr>
  </table>
</div>
<div class="add-table"> 
  <table class="datas">
	<tr class="first-tr">
	  <td colspan="4"><span>�ο���Ϣ</span></td>
	</tr>
	<tr>
	  <td align="right">������</td>
	  <td ><%=rd.getStringByDI("rsView","ysrs",0) %></td>
	  <td align="right">������ͨ��</td>
	  <td><%=rd.getStringByDI("rsView","jtjt",0) %>--<%=rd.getStringByDI("rsView","stjt",0) %></td>
	</tr>
	<tr>
	  <td width="20%" align="right">��ϵ�ˣ�</td>
	  <td width="30%"><%=rd.getStringByDI("rsView","visitor_nm",0) %></td>
	  <td width="20%" align="right">�绰��</td>
	  <td width="30%"><%=rd.getStringByDI("rsView","tel",0) %></td>
	</tr>
	<tr>
	  <td align="right">���ϵص㣺</td>
	  <td colspan="3"><%=rd.getStringByDI("rsView","gather",0) %></td>
	</tr>
  </table>
</div>
<div class="add-table"> 
  <table class="datas">
	<tr class="first-tr">
	  <td colspan="4"><span>��������Ϣ</span></td>
	</tr>
	<tr>
	  <td width="20%" align="right">���������ƣ�</td>
	  <td width="30%"><%=rd.getStringByDI("rsView","cmpny_name",0) %></td>
	  <td width="20%" align="right">��ϵ�ˣ�</td>
	  <td width="30%"><%=rd.getStringByDI("rsView","username",0) %></td>
	</tr>
	<tr>
	  <td align="right">�绰��</td>
	  <td><%=rd.getStringByDI("rsView","business_phone",0) %></td>
	  <td align="right">���棺</td>
	  <td><%=rd.getStringByDI("rsView","business_fax",0) %></td>
	</tr>
	<tr>
	  <td align="right">�ֻ���</td>
	  <td><%=rd.getStringByDI("rsView","business_mobile",0) %></td>
	  <td align="right">QQ��</td>
	  <td><%=rd.getStringByDI("rsView","business_qq",0) %></td>
	</tr>
  </table>
</div>
<div align="center" id="last-sub">
	<input type="button" id="button" value=" �� �� " onclick="window.history.go(-1);"/>
</div>
</body>
</html>