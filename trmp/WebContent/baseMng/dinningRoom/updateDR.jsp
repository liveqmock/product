<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
    <%@ include file="/common.jsp" %>
    
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
<title>�޸Ĳ�����Ϣ</title>
<script type="text/javascript">
	//BUTTON��ʽ��ʼ��
	$(function()
	{
		$("input:submit,input:button").button();
		$("#cityName").bind("click",function(){
			TB_show('��������','<%=request.getContextPath() %>/showXZQH4Base.?xzqh/layer=2&xzqh/gnw=1*TB_iframe=true&height=380&width=350','');
		});
	});

	function updateDR(){
		var s=selectImg();
		if(s>0){
				document.dr_form.encoding="multipart/form-data";
				document.dr_form.action="updateDR.fu";
				document.dr_form.submit();
			}else{
				document.dr_form.action="updateDR.";
				document.dr_form.submit();
			}
		}
	
	function addRow(){
		var tab=document.getElementById("drImg");
		var rows=tab.rows.length;
		var newRow=tab.insertRow();
		var td1=document.createElement("td");
		td1.setAttribute("align","right");
		var td2=document.createElement("td");
		var td3=document.createElement("td");
		td1.innerText="ѡ��ͼƬ��";
		td2.innerHTML="<input type='file' onchange='showImg(this.value,"+rows+")' name='diningRoom/img["+rows+"]'/>";
		td3.innerHTML="<img src='' style='display: none;' id='img"+rows+"' width='200' height='200'/>";
		newRow.appendChild(td1);
		newRow.appendChild(td2);
		newRow.appendChild(td3);
		}
</script>
</head>
<body>
<form name="dr_form" method="post">
<div id="lable"><span>�޸Ĳ���������Ϣ   ��<font color="red">*</font>��Ϊ������</span></div>
<input type="hidden" value="2" name="tplx">
	<table class="add-table" >
		<tr>
			<td align="right" >�������ƣ�</td>
			<td >
				<input type="hidden" name="TA_DINING_ROOM/DINING_ROOM_ID"  value="<%=rd.getStringByDI("TA_DINING_ROOMs","DINING_ROOM_ID",0)%>" />
				<input type="hidden" name="TA_DINING_ROOM/DINING_ROOM_ID[0]@oldValue"  value="<%=rd.getStringByDI("TA_DINING_ROOMs","DINING_ROOM_ID",0)%>" />
				<input type="text" name="TA_DINING_ROOM/CMPNY_NAME" id="CMPNY_NAME" value="<%=rd.getStringByDI("TA_DINING_ROOMs","CMPNY_NAME",0)%>"></input><span>*</span>
			</td>
			<td align="right" >������ַ��</td>
			<td >
			  <input type="hidden" name="TA_DINING_ROOM/CITY_ID" id="cityId" value="<%=rd.getStringByDI("TA_DINING_ROOMs","CITY_ID",0)%>"/>
			  <input type="text" name="xzqhname" id="cityName" class="smallInput" value="<%=rd.getStringByDI("TA_DINING_ROOMs","name",0)%>"/>
			  <input type="text" name="TA_DINING_ROOM/CMPNY_ADDRESS" id="CMPNY_ADDRESS"  value="<%=rd.getStringByDI("TA_DINING_ROOMs","CMPNY_ADDRESS",0)%>"/>
			</td>
			<td align="right" >�������</td>
			<td >
				<input type="radio" name="TA_DINING_ROOM/DINING_ROOM_TYPE" value="1" <%if("1".equals(rd.getStringByDI("TA_DINING_ROOMs","DINING_ROOM_TYPE",0)))out.print("checked='checked'"); %>>��
				<input type="radio" name="TA_DINING_ROOM/DINING_ROOM_TYPE" value="2" <%if("2".equals(rd.getStringByDI("TA_DINING_ROOMs","DINING_ROOM_TYPE",0)))out.print("checked='checked'"); %>>��
			</td>
		</tr>
		<tr>
			<td align="right" >�ʱࣺ</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/POST_CODE" id="POST_CODE" value="<%=rd.getStringByDI("TA_DINING_ROOMs","POST_CODE",0)%>"/>
			</td>
			<td align="right" >��˾�ʺţ�</td>
			<td >
				<input name="TA_DINING_ROOM/CMPNY_BANK_CODE" id="CMPNY_BANK_CODE" value="<%=rd.getStringByDI("TA_DINING_ROOMs","CMPNY_BANK_CODE",0)%>"/>
			</td>
			<td align="right" >�����У�</td>
			<td >
				<select name="TA_DINING_ROOM/CMPNY_BANK_NAME">
				<%int bankRows=rd.getTableRowsCount("KHYH");for(int b=0;b<bankRows;b++){ %>
					<option value="<%=rd.getStringByDI("KHYH","DMZ",b) %>" <%if(rd.getStringByDI("KHYH","DMZ",b).equals(rd.getStringByDI("TA_DINING_ROOMs","CMPNY_BANK_NAME",0)))out.print("selected='selected'");%>><%=rd.getStringByDI("KHYH","DMSM1",b) %></option>
				<%} %>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right" >����������</td>
			<td >
				<input type="text"  id="PERSON_NAME"  name="TA_DINING_ROOM/PERSON_NAME" value="<%=rd.getStringByDI("TA_DINING_ROOMs","CMPNY_BANK_CODE",0)%>"/>
			</td>
			<td align="right" >���������˺ţ�</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/PERSON_BANK_CODE" id="PERSON_BANK_CODE" value="<%=rd.getStringByDI("TA_DINING_ROOMs","PERSON_BANK_CODE",0)%>"/>
			</td>
			<td align="right" >���˿����У�</td>
			<td >
			<select name="TA_DINING_ROOM/PERSON_BANK_NAME" >
				<%int gr_bankRows=rd.getTableRowsCount("KHYH");for(int g=0;g<bankRows;g++){ %>
					<option value="<%=rd.getStringByDI("KHYH","DMZ",g) %>" <%if(rd.getStringByDI("KHYH","DMZ",g).equals(rd.getStringByDI("TA_DINING_ROOMs","PERSON_BANK_NAME",0)))out.print("selected='selected'");%>><%=rd.getStringByDI("KHYH","DMSM1",g) %></option>
				<%} %>
			</select>
			</td>
		</tr>
		<tr>
			<td align="right" >�ܸ��������֣�</td>
			<td >
				<input type="text" name="TA_DINING_ROOM/CHIEF_NAME" id="CHIEF_NAME"  value="<%=rd.getStringByDI("TA_DINING_ROOMs","CHIEF_NAME",0)%>"/>
			</td>
			<td align="right" >�ܸ����˹̶��绰��</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/CHIEF_TEL" id="CHIEF_TEL"  value="<%=rd.getStringByDI("TA_DINING_ROOMs","CHIEF_TEL",0)%>"/>
			</td>
			<td align="right" >�ܸ������ֻ���</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/CHIEF_MOBILE" id="CHIEF_MOBILE"  value="<%=rd.getStringByDI("TA_DINING_ROOMs","CHIEF_MOBILE",0)%>"/>
			</td>
		</tr>
			<tr>
			<td align="right" >�ܸ����˴��棺</td>
			<td >
				<input type="text" name="TA_DINING_ROOM/CHIEF_FAX" id="CHIEF_FAX"  value="<%=rd.getStringByDI("TA_DINING_ROOMs","CHIEF_FAX",0)%>"/>
			</td>
			<td align="right" >�ܸ�����QQ��</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/CHIEF_QQ" id="CHIEF_QQ"  value="<%=rd.getStringByDI("TA_DINING_ROOMs","CHIEF_QQ",0)%>"/>
			</td>
			<td align="right" >�ܸ����˵����ʼ���</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/CHIEF_MAIL" id="CHIEF_MAIL" value="<%=rd.getStringByDI("TA_DINING_ROOMs","CHIEF_MAIL",0)%>"/>
			</td>
		</tr>
				<tr>
			<td align="right" >ҵ��Ա���֣�</td>
			<td >
				<input type="text" name="TA_DINING_ROOM/BUSINESS_NAME" id="BUSINESS_NAME"  value="<%=rd.getStringByDI("TA_DINING_ROOMs","BUSINESS_NAME",0)%>"/>
			</td>
			<td align="right" >ҵ��Ա�̶��绰��</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/BUSINESS_PHONE" id="BUSINESS_PHONE"  value="<%=rd.getStringByDI("TA_DINING_ROOMs","BUSINESS_PHONE",0)%>"/>
			</td>
			<td align="right" >ҵ��Ա�ֻ���</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/BUSINESS_MOBILE" id="BUSINESS_MOBILE" value="<%=rd.getStringByDI("TA_DINING_ROOMs","BUSINESS_MOBILE",0)%>"/>
			</td>
		</tr>
		<tr>
			<td align="right" >ҵ��Ա���棺</td>
			<td >
				<input type="text" name="TA_DINING_ROOM/BUSINESS_FAX" id="BUSINESS_FAX"  value="<%=rd.getStringByDI("TA_DINING_ROOMs","BUSINESS_FAX",0)%>"/>
			</td>
			<td align="right" >ҵ��ԱQQ��</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/BUSINESS_QQ" id="BUSINESS_QQ"  value="<%=rd.getStringByDI("TA_DINING_ROOMs","BUSINESS_QQ",0)%>"/>
			</td>
			<td align="right" >ҵ��Ա�����ʼ���</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/BUSINESS_MAIL" id="BUSINESS_MAIL" value="<%=rd.getStringByDI("TA_DINING_ROOMs","BUSINESS_MAIL",0)%>"/>
			</td>
		</tr>
		<tr>
			<td align="right" style="display: none;">��Ͳͱ꣺</td>
			<td style="display: none;">
				<select name="TA_DINING_ROOM/BREAKFAST_LEVEL">
				<%int bf_drRows=rd.getTableRowsCount("CTCB");for(int x=0;x<bf_drRows;x++){ %>
					<option value="<%=rd.getStringByDI("CTCB","DMZ",x) %>" <%if(rd.getStringByDI("CTCB","DMZ",x).equals(rd.getStringByDI("TA_DINING_ROOMs","BREAKFAST_LEVEL",0)))out.print("selected='selected'");%>><%=rd.getStringByDI("CTCB","DMSM1",x) %></option>
				<%} %>
			</select>
			</td>
			<td align="right" style="display: none;">���Ͳͱ꣺</td>
			<td style="display: none;">
			<select name="TA_DINING_ROOM/DINNER_LEVEL">
				<%int dinner_drRows=rd.getTableRowsCount("CTCB");for(int y=0;y<dinner_drRows;y++){ %>
					<option value="<%=rd.getStringByDI("CTCB","DMZ",y) %>" <%if(rd.getStringByDI("CTCB","DMZ",y).equals(rd.getStringByDI("TA_DINING_ROOMs","DINNER_LEVEL",0)))out.print("selected='selected'");%>><%=rd.getStringByDI("CTCB","DMSM1",y) %></option>
				<%} %>
			</select>
			</td>
			<td align="right" >��ͼ۸�</td>
			<td >
			<input type="text" name="TA_DINING_ROOM/BREAKFAST_PRICE" onkeydown="chenckNum()" id="BREAKFAST_PRICE" value="<%=rd.getStringByDI("TA_DINING_ROOMs","BREAKFAST_PRICE",0)%>"/><font color="red">*</font>
			</td>
			<td align="right">���ͼ۸�</td>
			<td colspan="3">
				<input type="text" onkeydown="chenckNum()" name="TA_DINING_ROOM/DINNER_PRICE" id="DINNER_PRICE"  value="<%=rd.getStringByDI("TA_DINING_ROOMs","DINNER_PRICE",0)%>"/><font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td align="right">��ע��</td>
			<td colspan="6"><textarea rows="5" name="TA_DINING_ROOM/REMARK"><%=rd.getStringByDI("TA_DINING_ROOMs","REMARK",0)%></textarea><br><span>50��������</span> </td>
		</tr>
	</table>
	<div id="showImg">
		<%int imgRows=rd.getTableRowsCount("drImg");for(int i=0;i<imgRows;i++){ %>
			<img class="img<%=i %>" src="<%=request.getContextPath() %>/<%=rd.getStringByDI("drImg","FILEURL",i)%>" width="229",height="200">
			<span onclick="delImg('<%=rd.getStringByDI("drImg","FILEID",i)%>','img<%=i %>')" class="img<%=i %>"><img alt="ɾ����ͼƬ" src="<%=request.getContextPath()%>/images/del-icon.gif" ></span>
		<%} %>
	</div>
	<table width="100%">
	<tr>
       <td align="right" width="90%">
         <a href="###" onclick="addRow();"><img alt="���" src="/trmp/images/add.gif"> ���</a>&nbsp;&nbsp;
       	  <a href="###" onclick="delTabRow('drImg',1,'');"><img alt="ɾ��" src="/trmp/images/del.gif"> ɾ��</a>
       </td>
    </tr>
</table>
	<table class="add-table" width="99%" border="0" id="drImg">
		<tr>
			<td width="9%" align="right">ѡ��ͼƬ��</td>
			<td width="15%"><input type="file" onchange="showImg(this.value,'0')" name="diningRoom/img[0]"/></td>
			<td width="40%"><img src="" style="display: none;" id="img0" width="200" height="200"/></td>
		</tr>
	</table>
<div align="center" id="last-sub">
	<input type="button" value="��     ��" onclick="updateDR();"/>
	<input type="button" value="��    ��" onclick="window.history.go(-1)"/>
</div>	
</form>	
</body>
</html>