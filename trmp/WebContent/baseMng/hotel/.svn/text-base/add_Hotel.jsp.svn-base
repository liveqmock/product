<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
String ywlb = request.getParameter("ywlb");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryui/jqueryui.js" ></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/thickbox/thickbox.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>
<script type="text/javascript">
$(function(){
	$("input:submit,input:button").button();
	$("#cityName").bind("click",function(){
		TB_show('行政区划','<%=request.getContextPath() %>/showXZQH4Base.?xzqh/layer=2&xzqh/gnw=1*TB_iframe=true&height=380&width=350','');
	});
});

	function addRow(){
		var tab=document.getElementById("hotelImg");
		var rows=tab.rows.length;
		var newRow=tab.insertRow();
		var td1=document.createElement("td");
		var td2=document.createElement("td");
		var td3=document.createElement("td");
		td1.innerText="选择图片：";
		td2.innerHTML="<input type='file' onchange='showImg(this.value,"+rows+")' name='hotel/img["+rows+"]'/>";
		td3.innerHTML="<img src='' style='display: none;' id='img"+rows+"' width='200' height='200'/>";
		newRow.appendChild(td1);
		newRow.appendChild(td2);
		newRow.appendChild(td3);
		}
	function addPriceRow(){
		var tab=document.getElementById("hotel_price");
		var rows=tab.rows.length;
		var newRow=tab.insertRow();
		var td1=document.createElement("td");
		var td2=document.createElement("td");
		var td3=document.createElement("td");
		var td4=document.createElement("td");
		td1.innerText="价格名称：";
		td2.innerHTML='<select name="TA_HOTELPRICE/PRICENAME['+rows+']">'+
		              <%int newPrice=rd.getTableRowsCount("HOTELJGLX");for(int p=0;p<newPrice;p++){ %>
		               		'<option value="<%=rd.getStringByDI("HOTELJGLX","DMZ",p)%>"><%=rd.getStringByDI("HOTELJGLX","DMSM1",p)%></option>'+
		               	<%}%>
		         	'</select>';
		td3.innerText="价格：";
		td3.setAttribute("align","right");
		td4.innerHTML="<input type='text'  name='TA_HOTELPRICE/HPRICE["+rows+"]'/>";
		newRow.appendChild(td1);
		newRow.appendChild(td2);
		newRow.appendChild(td3);
		newRow.appendChild(td4);
		}
	function addHotel(){
		var s=selectImg();
		if(s>0){
		document.hotel_form.encoding="multipart/form-data";
		document.hotel_form.action="addHotel.fu";
		document.hotel_form.submit();
		}else{
			document.hotel_form.action="addHotel.";
			document.hotel_form.submit();
			}
		}
</script>
</head>
<body >
<form name="hotel_form" method="post">
<input type="hidden" value="1" name="tplx">
<div id="lable"><span>添加酒店基础信息 带<font color="red">*</font>号为必填项</span></div>

	<table class="add-table" >
		<tr>
			<td align="right" >酒店名称：</td>
			<td >
				<input type="text" name="TA_HOTEL/HOTEL_NAME" id="hotel_name" ></input><span>*</span>
			</td>
			<td align="right" >酒店地址：</td>
			<td >
				<input type="hidden" name="TA_HOTEL/AREA_ID" id="cityId"/>
			  	<input type="text" name="xzqhname" id="cityName" class="smallInput" />
				<input type="text" name="TA_HOTEL/HOTEL_ADDRESS" id="HOTEL_ADDRESS"  />
			</td>
			<td align="right" >星级：</td>
			<td >
			<select name="TA_HOTEL/HOTEL_LEVEL" style="width: 150px;">
			<%int jdxjRows=rd.getTableRowsCount("JDXJ");for(int j=0;j<jdxjRows;j++){ %>
				<option value="<%=rd.getStringByDI("JDXJ","DMZ",j)%>"><%=rd.getStringByDI("JDXJ","DMSM1",j)%></option>
			<%}%>	
			</select>
			</td>
		</tr>
			<tr>
			<td align="right" >早餐类型：</td>
			<td >
				<select name="TA_HOTEL/HOTEL_DINNER_TYPE" style="width: 150px;">
				<%int zclxRows=rd.getTableRowsCount("ZCLX");for(int z=0;z<zclxRows;z++){ %>
				<option value="<%=rd.getStringByDI("ZCLX","DMZ",z)%>"><%=rd.getStringByDI("ZCLX","DMSM1",z)%></option>
				<%}%>
				</select>
			</td>
			<td align="right" >对外帐号：</td>
			<td >
			<input type="text" name="TA_HOTEL/HOTEL_BANK_CODE" id="HOTEL_BANK_CODE"  />
			</td>
			<td align="right" >总台电话：</td>
			<td >
			<input type="text" name="TA_HOTEL/HOTEL_TEL" id="HOTEL_TEL" maxlength="13"/>
			</td>
		</tr>
		<tr>
			<td align="right" >联系人：</td>
			<td >
				<input type="text" name="TA_HOTEL/HOTEL_BUSSINESS" id="HOTEL_BUSSINESS"  />
			</td>
			<td align="right" >联系人电话：</td>
			<td >
			<input type="text" name="TA_HOTEL/HOTEL_BUSSINESS_TEL" id="HOTEL_BUSSINESS_TEL" />
			</td>
			<td align="right" >办公室电话：</td>
			<td >
			<input type="text" name="TA_HOTEL/HOTEL_BUSSINESS_PHONE" id="HOTEL_BUSSINESS_PHONE"  />
			</td>
		</tr>
		<tr>
			<td align="right" >传真：</td>
			<td >
				<input type="text" name="TA_HOTEL/HOTEL_BUSSINESS_FAX" id="HOTEL_BUSSINESS_FAX"  />
			</td>
			<td align="right" >Email：</td>
			<td >
			<input type="text" name="TA_HOTEL/HOTEL_BUSSINESS_MAIL" id="HOTEL_BUSSINESS_MAIL"  />
			</td>
			<td align="right" >QQ：</td>
			<td >
			<input type="text" name="TA_HOTEL/HOTEL_BUSSINESS_QQ" id="HOTEL_BUSSINESS_QQ" />
			</td>
		</tr>
	<tr>
		<td align="right">备注：</td>
		<td colspan="5"><textarea rows="5" cols="53" name="TA_HOTEL/remark"></textarea>&nbsp;<span>限50汉字内</span> </td>
	</tr>
	</table>
<table width="100%">
	<tr>
       <td align="right" width="90%">
         <a href="###" onclick="addPriceRow();"><img alt="添加" src="/trmp/images/add.gif"> 添加</a>&nbsp;&nbsp;
       	  <a href="###" onclick="delTabRow('hotel_price',1,'');"><img alt="删除" src="/trmp/images/del.gif"> 删除</a>
       </td>
    </tr>
</table>
<table class="add-table" width="99%" border="0" id="hotel_price">
		<tr>
			<td width="10%">价格名称：</td>
			<td width="15%">
			 <select name="TA_HOTELPRICE/PRICENAME[0]">
				<%int priceRows=rd.getTableRowsCount("HOTELJGLX");for(int p=0;p<priceRows;p++){ %>
				<option value="<%=rd.getStringByDI("HOTELJGLX","DMZ",p)%>"><%=rd.getStringByDI("HOTELJGLX","DMSM1",p)%></option>
				<%}%>
			 </select>
			</td>
			<td width="13%" align="right">价格：</td>
			<td width="52%"><input type="text"  name="TA_HOTELPRICE/HPRICE[0]"/></td>
		</tr>
</table>
<table width="100%">
	<tr>
       <td align="right" width="90%">
         <a href="###" onclick="addRow();"><img alt="添加" src="/trmp/images/add.gif"> 添加</a>&nbsp;&nbsp;
       	  <a href="###" onclick="delTabRow('hotelImg',1,'');"><img alt="删除" src="/trmp/images/del.gif"> 删除</a>
       </td>
    </tr>
</table>
<table class="add-table" width="99%" border="0" id="hotelImg">
		<tr>
			<td width="9%">选择图片：</td>
			<td width="15%"><input type="file" onchange="showImg(this.value,'0')" name="hotel/img[0]"/></td>
			<td width="40%"><img src="" style="display: none;" id="img0" width="200" height="200"/></td>
		</tr>
</table>
<div align="center" id="last-sub">
	<input type="button" id="button" value="提交" onclick="addHotel();"/>
	<input type="button" id="button" value="返回" onclick="history.go(-1);"/>
</div>
</form>	
</body>
</html>