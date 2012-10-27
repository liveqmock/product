<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
     <%@include file="/common.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryui/jqueryui.js" ></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/thickbox/thickbox.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>
<title>修改景点信息</title>
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
	var tab=document.getElementById("sceneryImg");
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
	var tab=document.getElementById("scenery_price");
	var rows=tab.rows.length;
	var newRow=tab.insertRow();
	var td1=document.createElement("td");
	var td2=document.createElement("td");
	var td3=document.createElement("td");
	var td4=document.createElement("td");
	var td5=document.createElement("td");
	var td6=document.createElement("td");
	td1.innerText="价格名称：";
	td1.setAttribute("align","right");
	td2.innerHTML='<select  name="TA_SCENERY_POINT_PRICE/PRICE_NAME['+rows+']">'+
		<%int newPrice=rd.getTableRowsCount("SCJGLX");for(int j=0;j<newPrice;j++){ %>
		'<option value="<%=rd.getStringByDI("SCJGLX","DMZ",j)%>"><%=rd.getStringByDI("SCJGLX","DMSM1",j)%></option>'+
		<%} %>
	'</select>';
	td3.innerText="价格：";
	td3.setAttribute("align","right");
	td4.innerHTML="<input type='text'  name='TA_SCENERY_POINT_PRICE/PRICE["+rows+"]'/>";
	td5.innerText="时间范围：";
	td5.setAttribute("align","right");
	td6.innerHTML="<input type='radio' name='TA_SCENERY_POINT_PRICE/PRICE_TYPE["+rows+"]' checked='checked' value='1'>永久  <input type='radio'  name='TA_SCENERY_POINT_PRICE/PRICE_TYPE["+rows+"]' value='2'>淡季  <input type='radio'  name='TA_SCENERY_POINT_PRICE/PRICE_TYPE["+rows+"]' value='3'>旺季";
	newRow.appendChild(td1);
	newRow.appendChild(td2);
	newRow.appendChild(td3);
	newRow.appendChild(td4);
	newRow.appendChild(td5);
	newRow.appendChild(td6);
	}
function addFL(){
	var tab=document.getElementById("scenery_fl");
	var rows=tab.rows.length;
	var newRow=tab.insertRow();
	var td1=document.createElement("td");
	var td2=document.createElement("td");
	var td3=document.createElement("td");
	var td4=document.createElement("td");
	td1.innerText="返利标准：";
	td1.setAttribute("align","right");
	td2.innerHTML="<input type='text' name='TA_SCENERY_POINT_RT/PERSON_COUNT["+rows+"]'>";
	td3.innerText="返利：";
	td3.setAttribute("align","right");
	td4.innerHTML="<input type='text' name='TA_SCENERY_POINT_RT/RETURN_COUNT["+rows+"]'>";
	newRow.appendChild(td1);
	newRow.appendChild(td2);
	newRow.appendChild(td3);
	newRow.appendChild(td4);
}
function upScenery(){
	var s=selectImg();
	if(s>0){
	document.scenery_form.encoding="multipart/form-data";
	document.scenery_form.action="upScenery.fu";
	document.scenery_form.submit();
	/* reload(); */
	}else{
		document.scenery_form.action="upScenery.";
		document.scenery_form.submit();
		/* reload(); */
		}
	}
</script>
</head>
<body>
<form name="scenery_form" method="post">
<div id="lable"><span>添加景点基础信息 带<font color="red">*</font>号为必填项</span></div>
<input type="hidden" value="3" name="tplx">
	<table class="add-table" >
		<tr>
			<td align="right" >景点名称：</td>
			<td >
			    <input type="hidden" name="TA_SCENERY_POINT/SCENERY_ID" id="SCENERY_ID" value="<%=rd.getStringByDI("TA_SCENERY_POINTs","SCENERY_ID",0)%>" />
			    <input type="hidden" name="TA_SCENERY_POINT/SCENERY_ID[0]@oldValue" id="SCENERY_ID" value="<%=rd.getStringByDI("TA_SCENERY_POINTs","SCENERY_ID",0)%>" />
				<input type="text" name="TA_SCENERY_POINT/CMPNY_NAME" id="CMPNY_NAME" value="<%=rd.getStringByDI("TA_SCENERY_POINTs","CMPNY_NAME",0)%>" /><span>*</span>
			</td>
			<td align="right" >公司帐号：</td>
			<td >
			<input type="text" name="TA_SCENERY_POINT/CMPNY_BANK_CODE" id="CMPNY_BANK_CODE"  value="<%=rd.getStringByDI("TA_SCENERY_POINTs","CMPNY_BANK_CODE",0)%>"/>
			</td>
			<td align="right" >开户行：</td>
			<td >
				<select name="TA_SCENERY_POINT/CMPNY_BANK_NAME" >
				<%int comBack=rd.getTableRowsCount("KHYH");
				String cmpBkName =  rd.getStringByDI("TA_SCENERY_POINTs","CMPNY_BANK_NAME",0);
				for(int c=0;c<comBack;c++){ %>
				<option value="<%=rd.getStringByDI("KHYH","DMZ",c)%>" <%if(cmpBkName.equals(rd.getStringByDI("KHYH","DMZ",c)))out.print("selected='selected'"); %>><%=rd.getStringByDI("KHYH","DMSM1",c)%></option>
				<%}%>
				</select>
			</td>
		</tr>
			<tr>
			<td align="right" >公司地址：</td>
			<td >
			  <input type="hidden" name="TA_SCENERY_POINT/CITY_ID" id="cityId" value="<%=rd.getStringByDI("TA_SCENERY_POINTs","CITY_ID",0)%>"/>
			  <input type="text" name="xzqhname" id="cityName" class="smallInput" value="<%=rd.getStringByDI("TA_SCENERY_POINTs","name",0)%>"/>
			  <input type="text" name="TA_SCENERY_POINT/CMPNY_ADDRESS" id="CMPNY_ADDRESS"  value="<%=rd.getStringByDI("TA_SCENERY_POINTs","CMPNY_ADDRESS",0)%>"/>
			</td>
			<td align="right" >邮编：</td>
			<td >
			<input type="text" name="TA_SCENERY_POINT/POST_CODE" id="POST_CODE"  value="<%=rd.getStringByDI("TA_SCENERY_POINTs","POST_CODE",0)%>"/>
			</td>
			<td align="right" >个人姓名：</td>
			<td >
			<input type="text" name="TA_SCENERY_POINT/PERSON_NAME" id="PERSON_NAME" maxlength="15" value="<%=rd.getStringByDI("TA_SCENERY_POINTs","PERSON_NAME",0)%>"/>
			</td>
		</tr>
		<tr>
			<td align="right" >个人银行账号：</td>
			<td >
				<input type="text" name="TA_SCENERY_POINT/PERSON_BANK_CODE" id="PERSON_BANK_CODE"  value="<%=rd.getStringByDI("TA_SCENERY_POINTs","PERSON_BANK_CODE",0)%>"/>
			</td>
			<td align="right" >个人开户行：</td>
			<td >
			<select name="TA_SCENERY_POINT/PERSON_BANK_NAME" >
				<%int grRows=rd.getTableRowsCount("KHYH");
				String personBkNm = rd.getStringByDI("TA_SCENERY_POINTs","PERSON_BANK_NAME",0);
				for(int g=0;g<grRows;g++){ %>
				<option value="<%=rd.getStringByDI("KHYH","DMZ",g)%>" <%if(personBkNm.equals(rd.getStringByDI("KHYH","DMZ",g)))out.print("selected='selected'"); %> ><%=rd.getStringByDI("KHYH","DMSM1",g)%></option>
				<%}%>
				</select>
			</td>
			<td align="right" >总负责人名字：</td>
			<td >
			<input type="text" name="TA_SCENERY_POINT/CHIEF_NAME" id="CHIEF_NAME"  value="<%=rd.getStringByDI("TA_SCENERY_POINTs","CHIEF_NAME",0)%>"/>
			</td>
		</tr>
		<tr>
			<td align="right" >总负责人固定电话：</td>
			<td >
				<input type="text" name="TA_SCENERY_POINT/CHIEF_TEL" id="CHIEF_TEL"  value="<%=rd.getStringByDI("TA_SCENERY_POINTs","CHIEF_TEL",0)%>"/>
			</td>
			<td align="right" >总负责人手机：</td>
			<td >
			<input type="text" name="TA_SCENERY_POINT/CHIEF_MOBILE" id="CHIEF_MOBILE"  value="<%=rd.getStringByDI("TA_SCENERY_POINTs","CHIEF_MOBILE",0)%>"/>
			</td>
			<td align="right" >总负责人传真：</td>
			<td >
			<input type="text" name="TA_SCENERY_POINT/CHIEF_FAX" id="CHIEF_FAX" value="<%=rd.getStringByDI("TA_SCENERY_POINTs","CHIEF_FAX",0)%>"/>
			</td>
		</tr>
			<tr>
			<td align="right" >总负责人QQ：</td>
			<td >
				<input type="text" name="TA_SCENERY_POINT/CHIEF_QQ" id="CHIEF_QQ" value="<%=rd.getStringByDI("TA_SCENERY_POINTs","CHIEF_QQ",0)%>" />
			</td>
			<td align="right" >总负责人电子邮件：</td>
			<td >
			<input type="text" name="TA_SCENERY_POINT/CHIEF_MAIL" id="CHIEF_MAIL"  value="<%=rd.getStringByDI("TA_SCENERY_POINTs","CHIEF_MAIL",0)%>"/>
			</td>
			<td align="right" >业务员名字：</td>
			<td >
			<input type="text" name="TA_SCENERY_POINT/BUSINESS_NAME" id="BUSINESS_NAME" value="<%=rd.getStringByDI("TA_SCENERY_POINTs","BUSINESS_NAME",0)%>"/>
			</td>
		</tr>
			<tr>
			<td align="right" >业务员固定电话：</td>
			<td >
				<input type="text" name="TA_SCENERY_POINT/BUSINESS_PHONE" id="BUSINESS_PHONE"  value="<%=rd.getStringByDI("TA_SCENERY_POINTs","BUSINESS_PHONE",0)%>"/>
			</td>
			<td align="right" >业务员手机：</td>
			<td >
			<input type="text" name="TA_SCENERY_POINT/BUSINESS_MOBILE" id="BUSINESS_MOBILE"  value="<%=rd.getStringByDI("TA_SCENERY_POINTs","BUSINESS_MOBILE",0)%>"/>
			</td>
			<td align="right" >业务员传真：</td>
			<td >
			<input type="text" name="TA_SCENERY_POINT/BUSINESS_FAX" id="BUSINESS_FAX" value="<%=rd.getStringByDI("TA_SCENERY_POINTs","BUSINESS_FAX",0)%>"/>
			</td>
		</tr>
			<tr>
			<td align="right" >业务员QQ：</td>
			<td >
				<input type="text" name="TA_SCENERY_POINT/BUSINESS_QQ" id="BUSINESS_QQ"  value="<%=rd.getStringByDI("TA_SCENERY_POINTs","BUSINESS_QQ",0)%>"/>
			</td>
			<td align="right" >业务员电子邮件：</td>
			<td >
			<input type="text" name="TA_SCENERY_POINT/BUSINESS_MAIL" id="BUSINESS_MAIL" value="<%=rd.getStringByDI("TA_SCENERY_POINTs","BUSINESS_MAIL",0)%>" />
			</td>
			<td align="right" >加点人头费：</td>
			<td >
			<input type="text" name="TA_SCENERY_POINT/PERSON_AVG_MNY" id="PERSON_AVG_MNY" value="<%=rd.getStringByDI("TA_SCENERY_POINTs","PERSON_AVG_MNY",0)%>"/>
			</td>
			
		</tr>
		
		<tr>
			<td align="right" >是否有年终奖：</td>
			<td >
				<input type="radio" name="TA_SCENERY_POINT/IS_END_YEAR_MNY"  value="1" <%if("1".equals(rd.getStringByDI("TA_SCENERY_POINTs","IS_END_YEAR_MNY",0)))out.print("checked='checked'"); %> >有
				<input type="radio" name="TA_SCENERY_POINT/IS_END_YEAR_MNY"  value="2" <%if("2".equals(rd.getStringByDI("TA_SCENERY_POINTs","IS_END_YEAR_MNY",0)))out.print("checked='checked'"); %>>无
			</td>
			<td colspan="2">是否参与优惠：
				<input type="radio" name="TA_SCENERY_POINT/IS_PRE_MNY"  value="1" <%if("1".equals(rd.getStringByDI("TA_SCENERY_POINTs","IS_PRE_MNY",0)))out.print("checked='checked'"); %>>是
				<input type="radio" name="TA_SCENERY_POINT/IS_PRE_MNY"  value="2" <%if("2".equals(rd.getStringByDI("TA_SCENERY_POINTs","IS_PRE_MNY",0)))out.print("checked='checked'"); %>>否
		
				&nbsp;&nbsp;<input type="text"name="TA_SCENERY_POINT/YHRS" value="<%=rd.getStringByDI("TA_SCENERY_POINTs","YHRS",0) %>" maxlength="2" class="smallInput">&nbsp;人
			</td>
		</tr>
			<tr>
			<td align="right">备注：</td>
			<td colspan="5"> <textarea rows="5" cols="80" name="TA_SCENERY_POINT/REMARK"><%=rd.getStringByDI("TA_SCENERY_POINTs","REMARK",0)%></textarea><span>50汉字以内</span> </td>
		</tr>
	</table>
	<table width="100%">
	<tr>
       <td align="right" width="90%">
         <a href="###" onclick="addPriceRow();"><img alt="添加" src="/trmp/images/add.gif"> 添加</a>&nbsp;&nbsp;
       	  <a href="###" onclick="delTabRow('scenery_price',1,'');"><img alt="删除" src="/trmp/images/del.gif"> 删除</a>
       </td>
    </tr>
</table>
<table class="add-table" width="99%" border="0" id="scenery_price">
	<%int priceRows=rd.getTableRowsCount("sceneryPrice");if(priceRows>0)for(int p=0;p<priceRows;p++){ %>	
		<tr>
			<td  align="right">价格名称：</td>
			<td >
				<select  name="TA_SCENERY_POINT_PRICE/PRICE_NAME[<%=p %>]">
					<%int jgRows=rd.getTableRowsCount("SCJGLX");for(int j=0;j<jgRows;j++){ %>
					<option value="<%=rd.getStringByDI("SCJGLX","DMZ",j)%>" <%if(rd.getStringByDI("SCJGLX","DMZ",j).equals(rd.getStringByDI("sceneryPrice","PRICE_NAME",p)))out.print("selected='selected'");%>><%=rd.getStringByDI("SCJGLX","DMSM1",j)%></option>
					<%} %>
				</select>
			</td>
			<td align="right" >价格：</td>
			<td width="10%"><input type="text" name="TA_SCENERY_POINT_PRICE/PRICE[<%=p %>]" value="<%=rd.getStringByDI("sceneryPrice","PRICE",p) %>"/></td>
			<td align="right" >时间范围：</td>
			<td >
				<input type="radio" name="TA_SCENERY_POINT_PRICE/PRICE_TYPE[<%=p %>]" value="1" <%if("1".equals(rd.getStringByDI("sceneryPrice","PRICE_TYPE",p)))out.print("checked='checked'"); %>/>永久
				<input type="radio" name="TA_SCENERY_POINT_PRICE/PRICE_TYPE[<%=p %>]" value="2" <%if("2".equals(rd.getStringByDI("sceneryPrice","PRICE_TYPE",p)))out.print("checked='checked'"); %>/>淡季
				<input type="radio" name="TA_SCENERY_POINT_PRICE/PRICE_TYPE[<%=p %>]" value="3" <%if("3".equals(rd.getStringByDI("sceneryPrice","PRICE_TYPE",p)))out.print("checked='checked'"); %>/>旺季
			</td>
		</tr>
	<%}else{ %>	
		<tr>
			<td  align="right">价格名称：</td>
			<td >
				<select  name="TA_SCENERY_POINT_PRICE/PRICE_NAME[0]">
					<%int jgRows=rd.getTableRowsCount("SCJGLX");for(int j=0;j<jgRows;j++){ %>
					<option value="<%=rd.getStringByDI("SCJGLX","DMZ",j)%>"><%=rd.getStringByDI("SCJGLX","DMSM1",j)%></option>
					<%} %>
				</select>
			</td>
			<td align="right" >价格：</td>
			<td width="10%"><input type="text" name="TA_SCENERY_POINT_PRICE/PRICE[0]" /></td>
			<td align="right" >时间范围：</td>
			<td >
				<input type="radio" name="TA_SCENERY_POINT_PRICE/PRICE_TYPE[0]" value="1" checked="checked"/>永久
				<input type="radio" name="TA_SCENERY_POINT_PRICE/PRICE_TYPE[0]" value="2" />淡季
				<input type="radio" name="TA_SCENERY_POINT_PRICE/PRICE_TYPE[0]" value="3" />旺季
			</td>
		</tr>
	<%} %>
</table>
		<table width="100%">
	<tr>
       <td align="right" width="90%">
         <a href="###" onclick="addFL();"><img alt="添加" src="/trmp/images/add.gif"> 添加</a>&nbsp;&nbsp;
       	  <a href="###" onclick="delTabRow('scenery_fl',1,'');"><img alt="删除" src="/trmp/images/del.gif"> 删除</a>
       </td>
    </tr>
</table>
<table class="add-table" width="99%" border="0" id="scenery_fl">
	<% int flRows=rd.getTableRowsCount("sceneryFL");if(flRows>0){for(int f=0;f<flRows;f++){ %>
		<tr>
			<td  align="right" width="7%">返利标准：</td>
			<td width="17%"><input type="text"  name="TA_SCENERY_POINT_RT/PERSON_COUNT[<%=f %>]" value="<%=rd.getStringByDI("sceneryFL","PERSON_COUNT",f) %>"/></td>
			<td align="right" width="5%">返利：</td>
			<td width="37%"><input type="text" name="TA_SCENERY_POINT_RT/RETURN_COUNT[<%=f %>]" value="<%=rd.getStringByDI("sceneryFL","RETURN_COUNT",f) %>"/></td>
		</tr>
		<%}}else{ %>
			<tr>
			<td  align="right" width="7%">返利标准：</td>
			<td width="17%"><input type="text"  name="TA_SCENERY_POINT_RT/PERSON_COUNT[0]" /></td>
			<td align="right" width="5%">返利：</td>
			<td width="37%"><input type="text" name="TA_SCENERY_POINT_RT/RETURN_COUNT[0]" /></td>
		</tr>
		<%} %>
</table>
	<div id="showImg">
		<%int imgRows=rd.getTableRowsCount("sceneryImg");for(int i=0;i<imgRows;i++){ %>
			<img class="img<%=i %>" src="<%=request.getContextPath() %>/<%=rd.getStringByDI("sceneryImg","FILEURL",i)%>" width="229",height="200">
			<span onclick="delImg('<%=rd.getStringByDI("sceneryImg","FILEID",i)%>','img<%=i %>')" class="img<%=i %>"><img alt="删除该图片" src="<%=request.getContextPath()%>/images/del-icon.gif" ></span>
		<%} %>
	</div>
	<table width="100%">
	<tr>
       <td align="right" width="90%">
         <a href="###" onclick="addRow();"><img alt="添加" src="/trmp/images/add.gif"> 添加</a>&nbsp;&nbsp;
       	  <a href="###" onclick="delTabRow('sceneryImg',1,'');"><img alt="删除" src="/trmp/images/del.gif"> 删除</a>
       </td>
    </tr>
</table>
	<table class="add-table" width="99%" border="0" id="sceneryImg">
		<tr>
			<td width="9%">选择图片：</td>
			<td width="15%"><input type="file" onchange="showImg(this.value,'0')" name="scenery/img[0]"/></td>
			<td width="40%"><img src="" style="display: none;" id="img0" width="200" height="200"/></td>
		</tr>
	</table>
	<div align="center" id="last-sub">
		<input type="button" value="提     交" onclick="upScenery();"/>
		<input type="button" value="返    回" onclick="window.history.go(-1)"/>
	</div>	
</form>	
</body>
</html>