<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ include file="/common.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>修改购物点基础信息</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryui/jqueryui.js" ></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/thickbox/thickbox.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>
<script type="text/javascript">
//BUTTON样式初始化
$(function()
{
	$("input:submit,input:button").button();
	$("#cityName").bind("click",function(){
		TB_show('行政区划','<%=request.getContextPath() %>/showXZQH4Base.?xzqh/layer=2&xzqh/gnw=1*TB_iframe=true&height=380&width=350','');
	});
});

function addRow(){
	var tab=document.getElementById("shop_goods");
	var rows=tab.rows.length;
	var newRow=tab.insertRow();
	var td1=document.createElement("td");
	var td2=document.createElement("td");
	td1.innerText="商品名称：";
	td1.setAttribute("align","right");
	td2.innerHTML="<input type='text' name='shop_goods/goods_name["+rows+"]'/>";
	newRow.appendChild(td1);
	newRow.appendChild(td2);
	}
	function updateShop(){
		document.shop_form.action="updateShop.";
		document.shop_form.submit();
		}
</script>
</head>
<body>
<form name="shop_form" method="post">
<div id="top-select">
	<span class="tishi">带<font color="red">*</font>号为必填项</span>
</div>
	<table class="add-table" >
		<tr>
			<td align="right" >购物点名称：</td>
			<td >
				<input type="hidden" name="TA_SHOPPOINT/SHOP_POINT_ID" id="SHOP_POINT_ID"  value="<%=rd.getStringByDI("TA_SHOPPOINTs","SHOP_POINT_ID",0) %>">
				<input type="hidden" name="TA_SHOPPOINT/SHOP_POINT_ID[0]@oldValue" id="SHOP_POINT_ID"  value="<%=rd.getStringByDI("TA_SHOPPOINTs","SHOP_POINT_ID",0) %>">
				<input type="text" name="TA_SHOPPOINT/CMPNY_NAME" id="CMPNY_NAME"  value="<%=rd.getStringByDI("TA_SHOPPOINTs","CMPNY_NAME",0) %>"></input><span>*</span>
			</td>
			<td align="right" >地址：</td>
			<td >
			  <input type="hidden" name="TA_SHOPPOINT/CITY_ID" id="cityId" value="<%=rd.getStringByDI("TA_SHOPPOINTs","CITY_ID",0)%>"/>
			  <input type="text" name="xzqhname" id="cityName" class="smallInput" value="<%=rd.getStringByDI("TA_SHOPPOINTs","name",0)%>"/>
			  <input type="text" name="TA_SHOPPOINT/CMPNY_ADDRESS" id="CMPNY_ADDRESS"  value="<%=rd.getStringByDI("TA_SHOPPOINTs","CMPNY_ADDRESS",0) %>"/>
			</td>
			<td align="right" >邮编：</td>
			<td >
				<input type="text" name="TA_SHOPPOINT/POST_CODE" id="POST_CODE"  value="<%=rd.getStringByDI("TA_SHOPPOINTs","POST_CODE",0) %>"/>
			</td>
		</tr>
			<tr>
			<td align="right" >购物筛选：</td>
			<td >
				<input type="radio" value="1" name="IF_SHOP" checked="checked" <%if("1".equals(rd.getStringByDI("TA_SHOPPOINTs","IF_SHOP",0)))out.print("checked='checked'"); %>/>其他
				<input type="radio" value="2" name="IF_SHOP" <%if("2".equals(rd.getStringByDI("TA_SHOPPOINTs","IF_SHOP",0)))out.print("checked='checked'"); %>/>单号
				<input type="radio" value="3" name="IF_SHOP" <%if("3".equals(rd.getStringByDI("TA_SHOPPOINTs","IF_SHOP",0)))out.print("checked='checked'"); %>/>双号
			</td>
			<td align="right" >公司帐号：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/CMPNY_BANK_CODE" id="CMPNY_BANK_CODE"  value="<%=rd.getStringByDI("TA_SHOPPOINTs","CMPNY_BANK_CODE",0) %>"/>
			</td>
			<td align="right" >开户行名称：</td>
			<td >
			<select name="TA_SHOPPOINT/CMPNY_BANK_NAME" >
				<%int comBank=rd.getTableRowsCount("KHYH");for(int z=0;z<comBank;z++){ %>
				<option value="<%=rd.getStringByDI("KHYH","DMZ",z)%>" <%if(rd.getStringByDI("KHYH","DMZ",z).equals(rd.getStringByDI("TA_SHOPPOINTs","CMPNY_BANK_NAME",0)))out.print("selected='selected'");%> ><%=rd.getStringByDI("KHYH","DMSM1",z)%></option>
				<%}%>
			</select>
			</td>
		</tr>
		<tr>
			<td align="right" >个人姓名：</td>
			<td >
				<input type="text" name="TA_SHOPPOINT/PERSON_NAME" id="PERSON_NAME"  value="<%=rd.getStringByDI("TA_SHOPPOINTs","PERSON_NAME",0) %>"/>
			</td>
			<td align="right" >个人银行卡号：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/PERSON_BANK_CODE" id="PERSON_BANK_CODE"  value="<%=rd.getStringByDI("TA_SHOPPOINTs","PERSON_BANK_CODE",0) %>"/>
			</td>
			<td align="right" >个人开户行：</td>
			<td >
				<select name="TA_SHOPPOINT/PERSON_BANK_NAME" >
				<%int grBank=rd.getTableRowsCount("KHYH");for(int g=0;g<comBank;g++){ %>
				<option value="<%=rd.getStringByDI("KHYH","DMZ",g)%>" <%if(rd.getStringByDI("KHYH","DMZ",g).equals(rd.getStringByDI("TA_SHOPPOINTs","PERSON_BANK_NAME",0)))out.print("selected='selected'");%> ><%=rd.getStringByDI("KHYH","DMSM1",g)%></option>
				<%}%>
			</select>
			</td>
		</tr>
			<tr>
			<td align="right" >总负责人名字：</td>
			<td >
				<input type="text" name="TA_SHOPPOINT/CHIEF_NAME" id="CHIEF_NAME"  value="<%=rd.getStringByDI("TA_SHOPPOINTs","CHIEF_NAME",0) %>"/>
			</td>
			<td align="right" >总负责人固定电话：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/CHIEF_TEL" id="CHIEF_TEL" value="<%=rd.getStringByDI("TA_SHOPPOINTs","CHIEF_TEL",0) %>" />
			</td>
			<td align="right" >总负责人手机：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/CHIEF_MOBILE" id="CHIEF_MOBILE" value="<%=rd.getStringByDI("TA_SHOPPOINTs","CHIEF_MOBILE",0) %>"/>
			</td>
		</tr>
			<tr>
			<td align="right" >总负责人传真：</td>
			<td >
				<input type="text" name="TA_SHOPPOINT/CHIEF_FAX" id="CHIEF_FAX" value="<%=rd.getStringByDI("TA_SHOPPOINTs","CHIEF_FAX",0) %>" />
			</td>
			<td align="right" >总负责人QQ：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/CHIEF_QQ" id="CHIEF_QQ"  value="<%=rd.getStringByDI("TA_SHOPPOINTs","CHIEF_QQ",0) %>"/>
			</td>
			<td align="right" >总负责人电子邮件：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/CHIEF_MAIL" id="CHIEF_MAIL" value="<%=rd.getStringByDI("TA_SHOPPOINTs","CHIEF_MAIL",0) %>"/>
			</td>
		</tr>
			<tr>
			<td align="right" >业务员名字：</td>
			<td >
				<input type="text" name="TA_SHOPPOINT/BUSINESS_NAME" id="BUSINESS_NAME"  value="<%=rd.getStringByDI("TA_SHOPPOINTs","BUSINESS_NAME",0) %>"/>
			</td>
			<td align="right" >业务员固定电话：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/BUSINESS_PHONE" id="BUSINESS_PHONE"  value="<%=rd.getStringByDI("TA_SHOPPOINTs","BUSINESS_PHONE",0) %>"/>
			</td>
			<td align="right" >业务员QQ：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/BUSINESS_QQ" id="BUSINESS_QQ" value="<%=rd.getStringByDI("TA_SHOPPOINTs","BUSINESS_QQ",0) %>"/>
			</td>
		</tr>
		<tr>
			<td align="right" >业务员传真：</td>
			<td >
				<input type="text" name="TA_SHOPPOINT/BUSINESS_FAX" id="BUSINESS_FAX"  value="<%=rd.getStringByDI("TA_SHOPPOINTs","BUSINESS_FAX",0) %>"/>
			</td>
			<td align="right" >业务员手机：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/BUSINESS_MOBILE" id="BUSINESS_MOBILE"  value="<%=rd.getStringByDI("TA_SHOPPOINTs","BUSINESS_MOBILE",0) %>"/>
			</td>
			<td align="right" >业务员电子邮件：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/BUSINESS_MAIL" id="BUSINESS_MAIL" value="<%=rd.getStringByDI("TA_SHOPPOINTs","BUSINESS_MAIL",0) %>"/>
			</td>
		</tr>
		<tr>
		  <td align="right">提成比例：</td>
		  <td colspan="5"><input type="text" name="TA_SHOPPOINT/TCBL" id="tcbl" value="<%=rd.getStringByDI("TA_SHOPPOINTs","TCBL",0) %>"/>% <font color="red">如：40</font></td>
		</tr>
		<tr>
			<td align="right">备注：</td>
			<td colspan="5"> <textarea rows="5" cols="50" name="TA_SHOPPOINT/REMARK"><%=rd.getStringByDI("TA_SHOPPOINTs","REMARK",0) %></textarea><span>50汉字以内</span> </td>
		</tr>
	</table>
<!-- 
	<table width="100%">
	<tr>
       <td align="right" width="90%">
         <a href="###" onclick="addRow();"><img alt="添加" src="/trmp/images/add.gif"> 添加</a>&nbsp;&nbsp;
       	  <a href="###" onclick="delTabRow('shop_goods',1,'');"><img alt="删除" src="/trmp/images/del.gif"> 删除</a>
       </td>
    </tr>
</table>
	<table class="add-table" width="99%" border="0" id="shop_goods">
		<%int cmdRows=rd.getTableRowsCount("shopGoods");for(int c=0;c<cmdRows;c++){ %>
		<tr>
			<td width="9%" align="right">商品名称：</td>
			<td width="75%"><input type="text" name="shop_goods/goods_name[0]" value="<%=rd.getStringByDI("shopGoods","goods_name",c) %>"/></td>
		</tr>
		<%} %>
	</table>
 -->
	<div align="center" id="last-sub">
		<input type="button" value="提     交" onclick="updateShop();"/>
		<input type="button" value="返    回" onclick="window.history.go(-1)"/>
</div>	
</form>	
</body>
</html>