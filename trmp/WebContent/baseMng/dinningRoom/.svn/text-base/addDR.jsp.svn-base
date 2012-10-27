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
<title>添加餐厅基础信息</title>
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
	var tab=document.getElementById("drImg");
	var rows=tab.rows.length;
	var newRow=tab.insertRow();
	var td1=document.createElement("td");
	var td2=document.createElement("td");
	var td3=document.createElement("td");
	td1.innerText="选择图片：";
	td1.setAttribute("align","right");
	td2.innerHTML="<input type='file' onchange='showImg(this.value,"+rows+")' name='hotel/img["+rows+"]'/>";
	td3.innerHTML="<img src='' style='display: none;' id='img"+rows+"' width='200' height='200'/>";
	newRow.appendChild(td1);
	newRow.appendChild(td2);
	newRow.appendChild(td3);
	}
function addDR(){
	var s=selectImg();
	if(s>0){
		document.dr_form.encoding="multipart/form-data";
		document.dr_form.action="addDR.fu";
		document.dr_form.submit();
	}else{
		document.dr_form.action="addDR.";
		document.dr_form.submit();
//		reload();
	}
}

</script>
</head>
<body>
<form name="dr_form" method="post">
<div id="lable"><span>添加餐厅基础信息   带<font color="red">*</font>号为必填项</span></div>
<input type="hidden" value="2" name="tplx">
	<table class="add-table" >
		<tr>
			<td align="right" >餐厅名称：</td>
			<td >
				<input type="text" name="TA_DINING_ROOM/CMPNY_NAME" id="CMPNY_NAME" ></input><span>*</span>
			</td>
			<td align="right" >餐厅地址：</td>
			<td >
			  <input type="hidden" name="TA_DINING_ROOM/CITY_ID" id="cityId"/>
			  <input type="text" name="xzqhname" id="cityName" class="smallInput" />
			  <input type="text" name="TA_DINING_ROOM/CMPNY_ADDRESS" id="CMPNY_ADDRESS"  />
			</td>
			<td align="right" >餐厅类别：</td>
			<td >
				<input type="radio" name="TA_DINING_ROOM/DINING_ROOM_TYPE" checked="checked" value="1">汉
				<input type="radio" name="TA_DINING_ROOM/DINING_ROOM_TYPE" value="2">回
			</td>
		</tr>
		<tr>
			<td align="right" size="6">邮编：</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/POST_CODE" id="POST_CODE" maxlength="6" />
			</td>
			<td align="right" >公司帐号：</td>
			<td >
				<input name="TA_DINING_ROOM/CMPNY_BANK_CODE" id="CMPNY_BANK_CODE"/>
			</td>
			<td align="right" >开户行：</td>
			<td >
				<select name="TA_DINING_ROOM/CMPNY_BANK_NAME">
				<%int bankRows=rd.getTableRowsCount("KHYH");for(int b=0;b<bankRows;b++){ %>
					<option value="<%=rd.getStringByDI("KHYH","DMZ",b) %>"><%=rd.getStringByDI("KHYH","DMSM1",b) %></option>
				<%} %>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right" >个人姓名：</td>
			<td >
				<input type="text"  id="PERSON_NAME"  name="TA_DINING_ROOM/PERSON_NAME"/>
			</td>
			<td align="right" >个人银行账号：</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/PERSON_BANK_CODE" id="PERSON_BANK_CODE" />
			</td>
			<td align="right" >个人开户行：</td>
			<td >
			<select name="TA_DINING_ROOM/PERSON_BANK_NAME" >
				<%int gr_bankRows=rd.getTableRowsCount("KHYH");for(int g=0;g<bankRows;g++){ %>
					<option value="<%=rd.getStringByDI("KHYH","DMZ",g) %>"><%=rd.getStringByDI("KHYH","DMSM1",g) %></option>
				<%} %>
			</select>
			</td>
		</tr>
		<tr>
			<td align="right" >总负责人名字：</td>
			<td >
				<input type="text" name="TA_DINING_ROOM/CHIEF_NAME" id="CHIEF_NAME"  />
			</td>
			<td align="right" >总负责人固定电话：</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/CHIEF_TEL" id="CHIEF_TEL"  />
			</td>
			<td align="right" >总负责人手机：</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/CHIEF_MOBILE" id="CHIEF_MOBILE" />
			</td>
		</tr>
			<tr>
			<td align="right" >总负责人传真：</td>
			<td >
				<input type="text" name="TA_DINING_ROOM/CHIEF_FAX" id="CHIEF_FAX"  />
			</td>
			<td align="right" >总负责人QQ：</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/CHIEF_QQ" id="CHIEF_QQ"  />
			</td>
			<td align="right" >总负责人电子邮件：</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/CHIEF_MAIL" id="CHIEF_MAIL" />
			</td>
		</tr>
				<tr>
			<td align="right" >业务员名字：</td>
			<td >
				<input type="text" name="TA_DINING_ROOM/BUSINESS_NAME" id="BUSINESS_NAME"  />
			</td>
			<td align="right" >业务员固定电话：</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/BUSINESS_PHONE" id="BUSINESS_PHONE"  />
			</td>
			<td align="right" >业务员手机：</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/BUSINESS_MOBILE" id="BUSINESS_MOBILE" />
			</td>
		</tr>
		<tr>
			<td align="right" >业务员传真：</td>
			<td >
				<input type="text" name="TA_DINING_ROOM/BUSINESS_FAX" id="BUSINESS_FAX"  />
			</td>
			<td align="right" >业务员QQ：</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/BUSINESS_QQ" id="BUSINESS_QQ"  />
			</td>
			<td align="right" >业务员电子邮件：</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/BUSINESS_MAIL" id="BUSINESS_MAIL" />
			</td>
		</tr>
		<tr>
			<td align="right" style="display: none;">早餐餐标：</td>
			<td style="display: none;">
				<select name="TA_DINING_ROOM/BREAKFAST_LEVEL">
				<%int bf_drRows=rd.getTableRowsCount("CTCB");for(int x=0;x<bf_drRows;x++){ %>
					<option value="<%=rd.getStringByDI("CTCB","DMZ",x) %>"><%=rd.getStringByDI("CTCB","DMSM1",x) %></option>
				<%} %>
			</select>
			</td>
			<td align="right" style="display: none;">正餐餐标：</td>
			<td style="display: none;">
			<select name="TA_DINING_ROOM/DINNER_LEVEL">
				<%int dinner_drRows=rd.getTableRowsCount("CTCB");for(int y=0;y<dinner_drRows;y++){ %>
					<option value="<%=rd.getStringByDI("CTCB","DMZ",y) %>"><%=rd.getStringByDI("CTCB","DMSM1",y) %></option>
				<%} %>
			</select>
			</td>
			<td align="right" >早餐价格：</td>
			<td >
			<input type="text" onkeydown="checkNum()" name="TA_DINING_ROOM/BREAKFAST_PRICE" id="BREAKFAST_PRICE" /><font color="red">*</font>
			</td>
			<td align="right" >正餐价格：</td>
			<td colspan="3">
				<input type="text" onkeydown="checkNum()" name="TA_DINING_ROOM/DINNER_PRICE" id="DINNER_PRICE"  /><font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td align="right">备注：</td>
			<td colspan="6"> <textarea rows="5"name="TA_DINING_ROOM/REMARK"></textarea><br><span>50汉字以内</span> </td>
		</tr>
	</table>
	<table width="100%">
	<tr>
       <td align="right" width="90%">
         <a href="###" onclick="addRow();"><img alt="添加" src="/trmp/images/add.gif"> 添加</a>&nbsp;&nbsp;
       	  <a href="###" onclick="delTabRow('drImg',1,'');"><img alt="删除" src="/trmp/images/del.gif"> 删除</a>
       </td>
    </tr>
</table>
	<table class="add-table" width="99%" border="0" id="drImg">
		<tr>
			<td width="9%" align="right">选择图片：</td>
			<td width="15%"><input type="file" onchange="showImg(this.value,'0')" name="diningRoom/img[0]"/></td>
			<td width="40%"><img src="" style="display: none;" id="img0" width="200" height="200"/></td>
		</tr>
	</table>
<div>
	<table class="datas">
	  <tr>
		<td align="center">
		  <input type="button" value="提    交" onclick="addDR();"/>&nbsp;&nbsp;
		  <input type="button" value="返    回" onclick="window.history.go(-1)"/>
		</td>
	  </tr>
	</table>
</div>	
</form>	
</body>
</html>