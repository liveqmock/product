<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryui/jqueryui.js" ></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/thickbox/thickbox.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<title>添加购物点基础信息</title>
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
	td1.innerText="商品类别：";
	td1.setAttribute("align","right");
	td2.innerHTML="<input type='text' name='shop_goods/goods_name["+rows+"]'/>";
	newRow.appendChild(td1);
	newRow.appendChild(td2);
	}
function addShop(){
	document.shop_form.action="addShop.";
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
				<input type="text" name="TA_SHOPPOINT/CMPNY_NAME" id="CMPNY_NAME" ></input><span>*</span>
			</td>
			<td align="right" >地址：</td>
			<td >
			  <input type="hidden" name="TA_SHOPPOINT/CITY_ID" id="cityId"/>
			  <input type="text" name="xzqhname" id="cityName" class="smallInput" />
			  <input type="text" name="TA_SHOPPOINT/CMPNY_ADDRESS" id="CMPNY_ADDRESS"  />
			</td>
			<td align="right" >邮编：</td>
			<td >
				<input type="text" name="TA_SHOPPOINT/POST_CODE" id="POST_CODE"  />
			</td>
		</tr>
			<tr>
			<td align="right" >购物筛选：</td>
			<td >
				<input type="radio" value="1" name="IF_SHOP" checked="checked"/>其他
				<input type="radio" value="2" name="IF_SHOP"/>单号
				<input type="radio" value="3" name="IF_SHOP"/>双号
			</td>
			<td align="right" >公司帐号：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/CMPNY_BANK_CODE" id="CMPNY_BANK_CODE"  />
			</td>
			<td align="right" >开户行名称：</td>
			<td >
			<select name="TA_SHOPPOINT/CMPNY_BANK_NAME" >
				<%int comBank=rd.getTableRowsCount("KHYH");for(int z=0;z<comBank;z++){ %>
				<option value="<%=rd.getStringByDI("KHYH","DMZ",z)%>"><%=rd.getStringByDI("KHYH","DMSM1",z)%></option>
				<%}%>
			</select>
			</td>
		</tr>
		<tr>
			<td align="right" >个人姓名：</td>
			<td >
				<input type="text" name="TA_SHOPPOINT/PERSON_NAME" id="PERSON_NAME"  />
			</td>
			<td align="right" >个人银行卡号：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/PERSON_BANK_CODE" id="PERSON_BANK_CODE"  />
			</td>
			<td align="right" >个人开户行：</td>
			<td >
				<select name="TA_SHOPPOINT/PERSON_BANK_NAME" >
				<%int grBank=rd.getTableRowsCount("KHYH");for(int z=0;z<grBank;z++){ %>
				<option value="<%=rd.getStringByDI("KHYH","DMZ",z)%>"><%=rd.getStringByDI("KHYH","DMSM1",z)%></option>
				<%}%>
			</select>
			</td>
		</tr>
			<tr>
			<td align="right" >总负责人名字：</td>
			<td >
				<input type="text" name="TA_SHOPPOINT/CHIEF_NAME" id="CHIEF_NAME"  />
			</td>
			<td align="right" >总负责人固定电话：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/CHIEF_TEL" id="CHIEF_TEL"  />
			</td>
			<td align="right" >总负责人手机：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/CHIEF_MOBILE" id="CHIEF_MOBILE" />
			</td>
		</tr>
			<tr>
			<td align="right" >总负责人传真：</td>
			<td >
				<input type="text" name="TA_SHOPPOINT/CHIEF_FAX" id="CHIEF_FAX"  />
			</td>
			<td align="right" >总负责人QQ：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/CHIEF_QQ" id="CHIEF_QQ"  />
			</td>
			<td align="right" >总负责人电子邮件：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/CHIEF_MAIL" id="CHIEF_MAIL" />
			</td>
		</tr>
			<tr>
			<td align="right" >业务员名字：</td>
			<td >
				<input type="text" name="TA_SHOPPOINT/BUSINESS_NAME" id="BUSINESS_NAME"  />
			</td>
			<td align="right" >业务员固定电话：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/BUSINESS_PHONE" id="BUSINESS_PHONE"  />
			</td>
			<td align="right" >业务员QQ：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/BUSINESS_QQ" id="BUSINESS_QQ" />
			</td>
		</tr>
			<tr>
			<td align="right" >业务员传真：</td>
			<td >
				<input type="text" name="TA_SHOPPOINT/BUSINESS_FAX" id="BUSINESS_FAX"  />
			</td>
			<td align="right" >业务员手机：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/BUSINESS_MOBILE" id="BUSINESS_MOBILE"  />
			</td>
			<td align="right" >业务员电子邮件：</td>
			<td >
			<input type="text" name="TA_SHOPPOINT/BUSINESS_MAIL" id="BUSINESS_MAIL" />
			</td>
		</tr>
		<tr>
		  <td align="right">提成比例：</td>
		  <td colspan="5"><input type="text" name="TA_SHOPPOINT/TCBL" id="tcbl" />% <font color="red">如：40</font></td>
		</tr>
		<tr>
			<td align="right">备注：</td>
			<td colspan="5"> <textarea rows="5" cols="50" name="TA_SHOPPOINT/REMARK"></textarea><span>50汉字以内</span> </td>
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
		<tr>
			<td width="9%" align="right">商品类别：</td>
			<td width="75%"><input type="text" name="shop_goods/goods_name[0]"/></td>
		</tr>
	</table>
 -->
	<div>
	<table class="datas">
	  <tr>
		<td align="center">
		  <input type="button" value="提    交" onclick="addShop();"/>&nbsp;&nbsp;
		  <input type="button" value="返    回" onclick="window.history.go(-1)"/>
		</td>
	  </tr>
	</table>
</div>	
</form>	
</body>
</html>