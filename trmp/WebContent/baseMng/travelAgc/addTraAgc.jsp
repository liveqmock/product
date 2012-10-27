<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>  
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
String ywlb = request.getParameter("ywlb");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>添加旅行社信息</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryui/jqueryui.js" ></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/thickbox/thickbox.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<%
	String city_id =request.getParameter("cityID");
%>
<script language="javascript">
	//BUTTON样式初始化
	$(function()
	{
		$("input:submit,input:button").button();
		$("#cityName").bind("click",function(){
			TB_show('行政区划','<%=request.getContextPath() %>/showXZQH4Base.?xzqh/layer=2&xzqh/gnw=1*TB_iframe=true&height=380&width=350','');
		});
	});

	function addTraAgcCheck(){			
		document.addTravelagencyInfo.submit();
	}
	
 	var lineNo;
 	
	function addNewRow(){
		var store = document.getElementById("store");
		lineNo = store.rows.length;
		var newRow=store.insertRow();
		var td1=document.createElement("td");
		var td2=document.createElement("td");
		var td3=document.createElement("td");
		var td4=document.createElement("td");
		td1.innerHTML="门店名称：";
		td1.setAttribute("align","right");
		td2.innerHTML="<input type='text' name='TA_STORE/Store_name["+lineNo+"]' class='text_input'/>";
		td3.innerText="门店地址：";
		td3.setAttribute("align","right");
		td4.innerHTML="<input type='text' name='TA_STORE/store_address["+lineNo+"]' maxlength='13' class='text_input'/>";
		newRow.appendChild(td1);
		newRow.appendChild(td2);
		newRow.appendChild(td3);
		newRow.appendChild(td4);
		lineNo += 1;
	}
</script>

</head>
<body>
<form action="addTraAgcInfo." name="addTravelagencyInfo" method="post">
<div id="top-select">
	<span class="tishi">添加旅行社信息           带<font color="red">*</font>号为必填项</span>
</div>
 <div class="add-table"> 
	<table border="0">
	  <tr >
		<td  align="right">公司名称：</td>
  		<td >
		  	<input type="text" name="ta_travelagency/CMPNY_NAME" id="CMPNY_NAME" />
		  	<span>*</span> 
	  	</td>
      	<td align="right"> 地址：</td>
       	<td >
       	  <input type="hidden" name="ta_travelagency/CITY_ID" id="cityId"/>
		  <input type="text" name="xzqhname" id="cityName" class="smallInput" />
       	  <input type="text" name="ta_travelagency/CMPNY_ADDRESS" id="CMPNY_ADDRESS" />
       	</td>
      	<td  align="right">公司账号：</td>
  		<td >
	  		<input type="text" name="ta_travelagency/CMPNY_BANK_CODE" id="CMPNY_BANK_CODE" />
	  	</td>
	  </tr>
	  <tr>
	   	<td align="right">业务类型：</td>
        <td >
			<input type="radio" name="ta_travelagency/BUSINESS_TYPE" value="1"/>个人业务
			<input type="radio" name="ta_travelagency/BUSINESS_TYPE" value="2" checked="checked"/>公司业务
		</td>
  	  	<td align="right">开户行：</td>
   	  	<td>
   	  	  <input type="text" name="ta_travelagency/CMPNY_BANK_NAME" id="OPENING_BANKNAME" />
  	    </td>
  	  	<td  align="right">邮编：</td>
        <td >
	  		<input type="text" name="ta_travelagency/POST_CODE" id="POST_CODE" />  
	  	</td>
	  </tr>
	  
	  <tr>
	  	 <td align="right">个人姓名：</td>
       	 <td >
   		  <input type="text" name="ta_travelagency/PERSON_NAME" id="PERSON_NAME" />
   		</td>
        <td  align="right">个人帐号：</td>
       	<td >
   		  <input type="text" name="ta_travelagency/PERSON_BANK_CODE" id="PERSON_BANK_CODE" />
   		</td>
        <td  align="right">银行名称：</td>
        <td >
   		  <input type="text" name="ta_travelagency/PERSON_BANK_NAME" id="BANK_NAME" />
     	</td>
	  </tr>
	  
	  <tr>
	  	 <td  align="right">总负责人姓名：</td>
        <td >
        <input type="text" name="ta_travelagency/CHIEF_NAME" id="CHIEF_NAME" />
         </td>
        <td  align="right">总负责人传真：</td>
        <td ><input type="text" name="ta_travelagency/CHIEF_FAX" maxlength="13" id="FIXED_FAX" />        </td>
      	<td  align="right">总负责人固定电话：</td>
        <td >
        	<input type="text" name="ta_travelagency/CHIEF_TEL" maxlength="13" id="FIXED_TELEPHONE" />  
        </td>
	  </tr>
	  <tr>
      	<td align="right">总负责人QQ：</td>
      	<td><input type="text" name="ta_travelagency/CHIEF_QQ" maxlength="13" id="CHIEF_QQ"  maxlength="11"/>        </td>
      	<td align="right">总负责人手机：</td>
        <td >
        	<input type="text" name="ta_travelagency/CHIEF_MOBILE" maxlength="11" id="CHIEF_MOBILE" />        </td>
   	    <td align="right">总负责人E-mail：</td>
      	<td><input type="text" name="ta_travelagency/CHIEF_QQ" id="CHIEF_QQ" /></td>
     </tr>  
     
     <tr>
     	<td  align="right">业务员姓名：</td>
		<td >
      		 <input type="text" name="ta_travelagency/BUSINESS_NAME" id="BUSINESS_NAME" />
       	</td>
     	<td  align="right">业务员传真：</td>
        <td ><input type="text" name="ta_travelagency/BUSINESS_FAX" maxlength="13" id="BUSINESS_FAX" />        </td>
      	<td  align="right">业务员固定电话：</td>
        <td >
        	<input type="text" name="ta_travelagency/BUSINESS_PHONE" maxlength="13" id="BUSINESS_PHONE" />
        </td>
     </tr>
     <tr>
      	<td align="right">业务员QQ：</td>
      	<td><input type="text" name="ta_travelagency/BUSINESS_QQ" id="BUSINESS_QQ"  maxlength="11"/>        </td>
    	<td align="right">业务员手机：</td>
      	<td >
      		<input type="text" name="ta_travelagency/BUSINESS_MOBILE" maxlength="11" id="BUSINESS_MOBILE" />      	</td>
   	  <td align="right">业务员E-mail：</td>
    	<td>
    	  <input type="text" name="ta_travelagency/BUSINESS_MAIL" id="BUSINESS_MAIL" />  
        </td>
	</tr>  
	<tr>
		<td align="right">备注：</td>
		<td colspan="5"><textarea rows="5" cols="50" name="ta_travelagency/remark"></textarea>&nbsp;<span>限50汉字内</span> </td>
	</tr>
	</table>
<table width="100%">
	<tr>
       <td align="right" width="90%">
         <a href="###" onclick="addNewRow();"><img alt="添加" src="/trmp/images/add.gif"> 添加</a>&nbsp;&nbsp;
       	  <a href="###" onclick="delTabRow('store',1,'');"><img alt="删除" src="/trmp/images/del.gif"> 删除&nbsp;&nbsp;&nbsp;&nbsp;</a>
       </td>
    </tr>
</table>	
<table id="store" border="0">
	<tr>
       	<td  align="right" width="12%">门点名称：</td>
		<td width="16%">
       	  <input type="text" name="TA_STORE/Store_name[0]" />
       	</td>
     	<td align="right" width="13%">门店地址：</td>
        <td >
          <input type="text" name="TA_STORE/store_address[0]" maxlength="13" />
        </td>
    </tr>
</table>
</div>
<div>
	<table class="datas">
	  <tr>
		<td align="center">
		  <input type="button" value="提    交" onclick="addTraAgcCheck();"/>&nbsp;&nbsp;
		  <input type="button" value="返    回" onclick="window.history.go(-1)"/>
		</td>
	  </tr>
	</table>
</div>		
</form>
</body>
</html>