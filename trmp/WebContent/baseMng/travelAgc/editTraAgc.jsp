<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>  
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�޸���������Ϣ</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryui/jqueryui.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/thickbox/thickbox.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css">
<script language="javascript">
	//BUTTON��ʽ��ʼ��
	$(function()
	{
		$("input:submit,input:button").button();
		$("#cityName").bind("click",function(){
			TB_show('��������','<%=request.getContextPath() %>/showXZQH4Base.?xzqh/layer=2&xzqh/gnw=1*TB_iframe=true&height=380&width=350','');
		});
	});

	function editTravelagencyCheck(){			
		document.editTravelagency.submit();
	//	window.parent.parent.location.reload(); 
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
		td1.innerHTML="�ŵ����ƣ�";
		td1.setAttribute("align","right");
		td2.innerHTML="<input type='text' name='TA_STORE/Store_name["+lineNo+"]' class='text_input'/>";
		td3.innerText="�ŵ��ַ��";
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
<form action="modifyTravelagencyInfo." name="editTravelagency" method="post" id="modifyTravelagency" >
<div id="top-select">
	<span class="tishi">��<font color="red">*</font>��Ϊ������</span>
</div>
  <input type="hidden" name="ta_travelagency/TRAVEL_AGC_ID" value="<%=rd.getStringByDI("ta_travelagencys","TRAVEL_AGC_ID",0)%>" />
  <input type="hidden" name="ta_travelagency/TRAVEL_AGC_ID[0]@oldValue" value="<%=rd.getStringByDI("ta_travelagencys","TRAVEL_AGC_ID",0)%>" />
 <div class="add-table"> 
	<table border="0">
	  <tr >
		<td  align="right">��˾���ƣ�</td>
  		<td >
		  	<input type="text" name="ta_travelagency/CMPNY_NAME" value="<%=rd.getStringByDI("ta_travelagencys","CMPNY_NAME",0)%>" id="CMPNY_NAME" class="text_input"/>
		  	<span>*</span> 
	  	</td>
      	<td align="right"> ��ַ��</td>
       	<td>
       	  <input type="hidden" name="ta_travelagency/CITY_ID" id="cityId" value="<%=rd.getStringByDI("ta_travelagencys","CITY_ID",0)%>"/>
		  <input type="text" name="xzqhname" id="cityName" class="smallInput" value="<%=rd.getStringByDI("ta_travelagencys","name",0)%>"/>
       	  <input type="text" name="ta_travelagency/CMPNY_ADDRESS" value="<%=rd.getStringByDI("ta_travelagencys","CMPNY_ADDRESS",0)%>" id="CMPNY_ADDRESS" class="text_input"/></td>
      	<td  align="right">��˾�˺ţ�</td>
  		<td >
	  		<input type="text" name="ta_travelagency/CMPNY_BANK_CODE" value="<%=rd.getStringByDI("ta_travelagencys","CMPNY_BANK_CODE",0)%>" id="CMPNY_BANK_CODE" class="text_input"/>
	  	</td>
	  </tr>
	  <tr>
	  	<td align="right">ҵ�����ͣ�</td>
        <td >
			<input type="radio" name="ta_travelagency/BUSINESS_TYPE" value="1" <%if(rd.getStringByDI("ta_travelagencys","BUSINESS_TYPE",0).equals("1")) out.print("checked"); %>/>����ҵ��
			<input type="radio" name="ta_travelagency/BUSINESS_TYPE" value="2" <%if(rd.getStringByDI("ta_travelagencys","BUSINESS_TYPE",0).equals("2")) out.print("checked"); %>/>��˾ҵ��
		</td>
  	  	<td align="right">�����У�</td>
   	  	<td>
   	  	   <input type="text" name="ta_travelagency/CMPNY_BANK_NAME" value="<%=rd.getStringByDI("ta_travelagencys","CMPNY_BANK_NAME",0)%>" id="OPENING_BANKNAME" class="text_input"/>
  	    </td>
  	  	<td  align="right">�ʱࣺ</td>
        <td >
	  		<input type="text" name="ta_travelagency/POST_CODE" value="<%=rd.getStringByDI("ta_travelagencys","POST_CODE",0)%>" id="POST_CODE" class="text_input"/> 
	  	</td>
	  </tr>
	  
	  <tr>
	  	 <td align="right">����������</td>
       	 <td >
   		  <input type="text" name="ta_travelagency/PERSON_NAME" value="<%=rd.getStringByDI("ta_travelagencys","PERSON_NAME",0)%>" id="PERSON_NAME" class="text_input"/>
   		</td>
        <td  align="right">�����ʺţ�</td>
       	<td >
   		 <input type="text" name="ta_travelagency/PERSON_BANK_CODE" value="<%=rd.getStringByDI("ta_travelagencys","PERSON_BANK_CODE",0)%>" id="PERSON_BANK_CODE" class="text_input"/>
   		</td>
        <td  align="right">�������ƣ�</td>
        <td >
   		  <input type="text" name="ta_travelagency/PERSON_BANK_NAME" value="<%=rd.getStringByDI("ta_travelagencys","PERSON_BANK_NAME",0)%>" id="BANK_NAME" class="text_input"/>
     	</td>
	  </tr>
	  
	  <tr>
	  	 <td  align="right">�ܸ�����������</td>
        <td >
       <input type="text" name="ta_travelagency/CHIEF_NAME" value="<%=rd.getStringByDI("ta_travelagencys","CHIEF_NAME",0)%>" id="CHIEF_NAME" class="text_input"/>
         </td>
        <td  align="right">�ܸ����˴��棺</td>
        <td >
        	<input type="text" name="ta_travelagency/CHIEF_FAX" maxlength="13" value="<%=rd.getStringByDI("ta_travelagencys","CHIEF_FAX",0)%>" id="FIXED_FAX" class="text_input"/>
        </td>
      	<td  align="right">�ܸ����˹̶��绰��</td>
        <td >
        	<input type="text" name="ta_travelagency/CHIEF_TEL" maxlength="13" value="<%=rd.getStringByDI("ta_travelagencys","CHIEF_TEL",0)%>" id="FIXED_TELEPHONE" class="text_input"/>  
        </td>
	  </tr>
	  <tr>
      	<td align="right">�ܸ�����QQ��</td>
      	<td>
      		<input type="text" name="ta_travelagency/CHIEF_QQ" maxlength="13" value="<%=rd.getStringByDI("ta_travelagencys","CHIEF_QQ",0)%>" id="CHIEF_QQ" class="text_input" maxlength="11"/>
      	</td>
      	<td align="right">�ܸ������ֻ���</td>
        <td >
			<input type="text" name="ta_travelagency/CHIEF_MOBILE" maxlength="11" value="<%=rd.getStringByDI("ta_travelagencys","CHIEF_MOBILE",0)%>" id="CHIEF_MOBILE" class="text_input"/>          	
        </td>
   	    <td align="right">�ܸ�����E-mail��</td>
      	<td><input type="text" name="ta_travelagency/CHIEF_MAIL"  value="<%=rd.getStringByDI("ta_travelagencys","CHIEF_MAIL",0)%>" id="CHIEF_MAIL" class="text_input"/></td>
     </tr>  
     
     <tr>
     	<td  align="right">ҵ��Ա������</td>
		<td >
      		 <input type="text" name="ta_travelagency/BUSINESS_NAME" value="<%=rd.getStringByDI("ta_travelagencys","BUSINESS_NAME",0)%>" id="BUSINESS_NAME" class="text_input"/>
       	</td>
     	<td  align="right">ҵ��Ա���棺</td>
        <td >
        	<input type="text" name="ta_travelagency/BUSINESS_FAX" maxlength="13" value="<%=rd.getStringByDI("ta_travelagencys","BUSINESS_FAX",0)%>" id="BUSINESS_FAX" class="text_input"/>
        </td>
      	<td  align="right">ҵ��Ա�̶��绰��</td>
        <td >
        	<input type="text" name="ta_travelagency/BUSINESS_PHONE" maxlength="13" value="<%=rd.getStringByDI("ta_travelagencys","BUSINESS_PHONE",0)%>" id="BUSINESS_PHONE" class="text_input"/>
        </td>
     </tr>
     <tr>
      	<td align="right">ҵ��ԱQQ��</td>
      	<td>
      		<input type="text" name="ta_travelagency/BUSINESS_QQ" value="<%=rd.getStringByDI("ta_travelagencys","BUSINESS_QQ",0)%>" id="BUSINESS_QQ" class="text_input" maxlength="11"/>
      	</td>
    	<td align="right">ҵ��Ա�ֻ���</td>
      	<td >
      		<input type="text" name="ta_travelagency/BUSINESS_MOBILE" maxlength="11" value="<%=rd.getStringByDI("ta_travelagencys","BUSINESS_MOBILE",0)%>" id="BUSINESS_MOBILE" class="text_input"/> 
      </td>
   	  <td align="right">ҵ��ԱE-mail��</td>
    	<td>
    	  <input type="text" name="ta_travelagency/BUSINESS_MAIL" value="<%=rd.getStringByDI("ta_travelagencys","BUSINESS_MAIL",0)%>" id="BUSINESS_MAIL" class="text_input"/>  
        </td>
	</tr>  
	<tr>
		<td align="right">��ע��</td>
		<td colspan="5"><textarea rows="5" cols="50" name="ta_travelagency/remark"><%=rd.getStringByDI("ta_travelagencys","remark",0)%></textarea>&nbsp;<span>��50������</span> </td>
	</tr>
	</table>
<table width="100%">
	<tr>
       <td align="right" width="90%">
         <a href="###" onclick="addNewRow();"><img alt="���" src="/trmp/images/add.gif"> ���</a>&nbsp;&nbsp;
       	  <a href="###" onclick="delTabRow('store',1,'');"><img alt="ɾ��" src="/trmp/images/del.gif"> ɾ��</a>
       </td>
    </tr>
</table>	
<table id="store" border="0">
    <%
for(int i=0;i<rd.getTableRowsCount("ta_stores");i++){
	String storeNm = rd.getStringByDI("ta_stores","Store_name",i);
	String store_address = rd.getStringByDI("ta_stores","store_address",i);
%>
	<tr>
       	<td  align="right" width="12%">�ŵ����ƣ�</td>
		<td width="16%">
       	  <input type="text" name="TA_STORE/Store_name[<%=i %>]" value="<%=storeNm %>"/>
       	</td>
     	<td align="right" width="13%">�ŵ��ַ��</td>
        <td >
          <input type="text" name="TA_STORE/store_address[<%=i %>]" value="<%=store_address %>" maxlength="13" />
        </td>
    </tr>
    
<%	
}
%>	
</table>
</div>	
<div align="center" id="last-sub">
	<input type="button" value="��     ��" onclick="editTravelagencyCheck();"/>
	<input type="button" value="��    ��" onclick="window.history.go(-1)"/>
</div>	
</form>
</body>
</html>