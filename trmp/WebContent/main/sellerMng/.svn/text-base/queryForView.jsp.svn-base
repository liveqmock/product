<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.mysql.jdbc.Blob"%>
<%@page import="java.io.InputStreamReader"%>
<%@include file="../../common.jsp"%>
<%@ taglib uri="/WEB-INF/tld/FCKeditor.tld" prefix="FCK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>订单明细</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>

</head>
<body>

<div class="add-table"> 
  <table class="datas">
	<tr class="first-tr">
	  <td colspan="4"><span>订单信息（订单号：<%=rd.getStringByDI("rsView","id",0) %>）</span></td>
	</tr>
	<tr>
	  <td align="right">线路名：</td>
	  <td colspan="3"><%=rd.getStringByDI("rsView","line_name",0) %></td>
	</tr>
	<tr>
	  <td width="20%" align="right">报名时间：</td>
	  <td width="30%"><%=rd.getStringByDI("rsView","regedit_time",0) %></td>
	  <td width="20%" align="right">价格：</td>
	  <td width="30%">总价格：<%=rd.getStringByDI("rsView","msj",0) %>  结算价：<%=rd.getStringByDI("rsView","thj",0) %></td>
	</tr>
	<tr>
	  <td align="right">出团日期：</td>
	  <td><%=rd.getStringByDI("rsView","begin_date",0) %></td>
	  <td align="right">返回日期：</td>
	  <td><%=rd.getStringByDI("rsView","end_date",0) %></td>
	</tr>
	<tr height="30">
	  <td align="right">订单备注：</td>
	  <td colspan="3"><%=rd.getStringByDI("rsView","remark",0) %></td>
	</tr>
  </table>
</div>
<div class="add-table"> 
  <table class="datas">
	<tr class="first-tr">
	  <td colspan="4"><span>游客信息</span></td>
	</tr>
	<tr>
	  <td align="right">人数：</td>
	  <td ><%=rd.getStringByDI("rsView","ysrs",0) %></td>
	  <td align="right">往返交通：</td>
	  <td><%=rd.getStringByDI("rsView","jtjt",0) %>--<%=rd.getStringByDI("rsView","stjt",0) %></td>
	</tr>
	<tr>
	  <td width="20%" align="right">联系人：</td>
	  <td width="30%"><%=rd.getStringByDI("rsView","visitor_nm",0) %></td>
	  <td width="20%" align="right">电话：</td>
	  <td width="30%"><%=rd.getStringByDI("rsView","tel",0) %></td>
	</tr>
	<tr>
	  <td align="right">集合地点：</td>
	  <td colspan="3"><%=rd.getStringByDI("rsView","gather",0) %></td>
	</tr>
  </table>
</div>
<div class="add-table"> 
  <table class="datas">
	<tr class="first-tr">
	  <td colspan="4"><span>组团社信息</span></td>
	</tr>
	<tr>
	  <td width="20%" align="right">组团社名称：</td>
	  <td width="30%"><%=rd.getStringByDI("rsView","cmpny_name",0) %></td>
	  <td width="20%" align="right">联系人：</td>
	  <td width="30%"><%=rd.getStringByDI("rsView","username",0) %></td>
	</tr>
	<tr>
	  <td align="right">电话：</td>
	  <td><%=rd.getStringByDI("rsView","business_phone",0) %></td>
	  <td align="right">传真：</td>
	  <td><%=rd.getStringByDI("rsView","business_fax",0) %></td>
	</tr>
	<tr>
	  <td align="right">手机：</td>
	  <td><%=rd.getStringByDI("rsView","business_mobile",0) %></td>
	  <td align="right">QQ：</td>
	  <td><%=rd.getStringByDI("rsView","business_qq",0) %></td>
	</tr>
  </table>
</div>
<div align="center" id="last-sub">
	<input type="button" id="button" value=" 返 回 " onclick="window.history.go(-1);"/>
</div>
</body>
</html>