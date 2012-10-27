<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>
<%@ page import="java.util.*" %>
<%
	StringBuffer expenses = new StringBuffer();
	Map<String,String> map = null;
	if(rd.get("Map")!=null){
		map = (Map<String,String>)rd.get("Map");
		for(Map.Entry<String,String> entry : map.entrySet()){
			expenses.append(entry.getKey()+"="+entry.getValue()+"&");
		}
		if(expenses.length()>0)
			expenses.deleteCharAt(expenses.length()-1);
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<link href="main/workflow/css/g_table.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/main/workflow/css/main.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/main/workflow/css/list_table.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/main/workflow/css/top_table.css" rel="stylesheet" type="text/css" />
<style type="text/css">
select{
	border:#91C0E3 1px solid; 
	width:120px;

}
#expenses tr td input{
	border:#91C0E3 1px solid;
	width:120px;

}
#income tr{
	text-align: center;
}
#shop tr{
	text-align: center;
}
#income tr td input{
	border:#91c0e3 1px solid;
	width:70px;
}
#shop tr td input{
	border:#91c0e3 1px solid;
	width:70px;
}
</style>
</head>
<body>
<%if("1".equals(rd.getString("success"))){%>
<table border="0" cellpadding="0" cellspacing="0"   class="top_table"  width="100%" >
        <tr  class="toptable_pingpu">
            <td width="18%" class="toptable_td1" ><div  class="top_div">���η��ñ���</div> </td>
            <td width="9%">&nbsp;</td>
            <td width="12%">&nbsp;</td>
            <td width="12%">&nbsp;</td>
            <td width="28%">&nbsp;</td>
            <td width="7%">&nbsp;</td>
            <td width="6%">&nbsp;</td>
       	  	<td width="8%" >&nbsp;</td>
      </tr>
</table>
     <table border="0" cellpadding="0" cellspacing="0"   width="100%"   class="tr_formtop">
<tr class="text_formcolor" >
  <td width="1%"  >      <img src="../images/item_point.gif" alt="" width="6" height="9" /></td>
    <td width="26%" ><strong>������Ϣ</strong></td>
   	<td width="12%" > </td>
            <td width="12%" > </td>
            <td width="28%" > </td>
    		<td width="7%"  > </td>
            <td width="6%"  > </td>
       	  	<td width="8%"  > </td>
      </tr>
     </table>
     <br />
<table   cellspacing="0" width="95%"  align="center" class="text_formtable">
    	<tr>
            <td width="3%"></td>
   	 	  	<td width="10%">�źţ�</td>
  	  	  	<td width="12%" ><%=rd.getStringByDI("GROUP","GROUP_ID",0)%></td>
   	  	  	<td width="13%"  >��ֹ���ڣ�</td>
          	<td colspan="2" ><%=rd.getStringByDI("GROUP","B_TIME",0) %>---<%=rd.getStringByDI("GROUP","E_TIME",0) %></td>
          	<td width="10%" >������</td>
          <td width="18%"><%=rd.getStringByDI("GROUP","DAYS",0) %></td>
          <td width="4%"></td>
   		</tr>
      	<tr>
            <td ></td>
       	 	<td >���ˣ�</td>
   	 	  	<td ><%=rd.getStringByDI("GROUP","ADULT_COUNT",0) %></td>
   	  	  	<td >��ͯ��</td>
            <td width="20%"><%=rd.getStringByDI("GROUP","CHILDREN_COUNT",0) %></td>
            <td width="10%" ></td>
            <td width="10%" >��·��</td>
            <td><%=rd.getStringByDI("GROUP","LINE",0) %></td>
            <td></td>
      	</tr>
      	<tr>
            <td ></td>
            <td >����</td>
            <td >
            	<%="0".equals(rd.getStringByDI("GROUP","VEHICLE_TYPE",0))?"�Ա���":"���Ա���" %>
            	<input id="vehicle_type" type="hidden" value="<%=rd.getStringByDI("GROUP","VEHICLE_TYPE",0) %>">
            
            </td>
            <td></td>
            <td >��ע��</td>
   	 	  	<td colspan="3"><%=rd.getStringByDI("GROUP","COMMENTS",0) %></td>
            <td></td>
      	</tr>
</table>
<br />
<table border="0" cellpadding="0" cellspacing="0"   width="100%"   class="tr_formtop">
    	<tr class="text_formcolor" >
          <td width="1%"  ><img src="../images/item_point.gif" alt="" width="6" height="9" /></td>
       	  <td width="26%" ><strong>����֧��</strong></td>
          <td width="12%" > </td>
          <td width="12%" > </td>
          <td width="28%" > </td>
          <td width="6%" ></td>
   	  	  <td width="8%"  ></td>
              
   	 </tr>
</table>   	
<table  border="0" id="expenses" cellpadding="0" cellspacing="0" class="text_formtable" width="95%"  align="center"   >
<tbody>
            <tr>
              <td width="19%"></td>
              <td width="15%" >����</td>
              <td width="19%">ǩ��</td>
              <td width="18%" >�ָ�</td>
              <td width="29%"></td>
            </tr>
            
            <%
            	int rows = rd.getTableRowsCount("PAY");
            	for(int i=0;i<rows;i++){
            %>
            	<tr>
              <td></td>
              <td >
    			<%=map.get(rd.getStringByDI("PAY","ITEMS_ID",i))!=null?map.get(rd.getStringByDI("PAY","ITEMS_ID",i)):""%>
			  </td>
              <td ><%=rd.getStringByDI("PAY","SIGN_BILL",i) %></td>
              <td ><%=rd.getStringByDI("PAY","PAY_CASH",i) %></td>
              <td></td>
            </tr>
            <%}%>
</tbody>
<tbody>
	
	 <tr>
        <td></td>
        <td >С��:</td>
        <td ><%=rd.getStringByDI("EXPENSES","OUT_SIGN",0) %></td>
        <td ><%=rd.getStringByDI("EXPENSES","OUT_PAY",0) %></td>
        <td></td>
    </tr>
</tbody>
</table>



<br>
<table border="0" cellpadding="0" cellspacing="0"   width="100%"      class="tr_formtop">
    	<tr class="text_formcolor" >
          <td width="1%"  ><img src="../images/item_point.gif" alt="" width="6" height="9" /></td>
       	  <td width="26%" ><strong>��������</strong></td>
          <td width="12%" > </td>
          <td width="12%" > </td>
          <td width="28%" > </td>
          <td width="7%"  > </td>
          <td width="6%" ></td>
   	  	  <td width="8%"  ></td>        
          <td width="6%"  > </td>
   	  	  <td width="8%"  > </td>      
   	 </tr>
</table>
<table border="0" cellpadding="0" id="income" cellspacing="0"   width="95%" align="center"   class="text_formtable">
	<tbody>
    <tr>
          <td valign="top"><p align="center"><strong>�ӵ���Ŀ </strong></p></td>
    	  <td valign="top"><p align="center"><strong>�˾����Ѷ� </strong></p></td>
    	  <td valign="top"><p align="center"><strong>���� </strong></p></td>
    	  <td valign="top"><p align="center"><strong>��ͷ�� </strong></p></td>
    	  <td valign="top"><p align="center"><strong>�ɱ�����</strong></p></td>
    	  <td valign="top"><p align="center"><strong>֧����ʽ</strong></p></td>
    	  <td valign="top"><p align="center"><strong>�ɱ���� </strong></p></td>
    	  <td valign="top"><p align="center"><strong>����</strong><strong> </strong></p></td>
    	  <td valign="top"><p align="center"><strong>ȫ����� </strong></p></td>
    	  <td valign="top"><p align="center"><strong>������� </strong></p></td>
    	  <td valign="top"><p align="center"><strong>˾����� </strong></p></td>
    	  <td valign="top"><p align="center"><strong>Ӧ����˾ </strong></p></td>
  	  </tr>
  	  <%int pointRows = rd.getTableRowsCount("POINT") ;
  		for(int i=0;i<pointRows;i++){
  	  %>
      <tr align="center"  >
          <td width="11%"><%=rd.getStringByDI("POINT","COMPANY_NAME",i) %></td>
      	  <td width="7%" ><%=rd.getStringByDI("POINT","AVG_MONEY",i) %></td>
      	  <td width="9%" ><%=rd.getStringByDI("POINT","PERSON_COUNT",i) %></td>
      	  <td width="7%" ><%=rd.getStringByDI("POINT","PERSON_MONEY",i) %></td>
      	   <td width="7%" ><%=rd.getStringByDI("POINT","COST_PRICE",i) %></td>
          <td width="7%" ><%="1".equals(rd.getStringByDI("POINT","PAYMETHOD",i))?"ǩ��":"�ָ�" %></td>
          <td width="9%" ><%=rd.getStringByDI("POINT","COST_MONEY",i) %></td>
      	  <td width="10%"><%=rd.getStringByDI("POINT","MARGIN",i) %></td>
          <td width="10%"><%=rd.getStringByDI("POINT","NATIVE_GUIDE_MONEY",i) %></td>
          <td width="7%" ><%=rd.getStringByDI("POINT","GUIDE_MONEY",i) %></td>
          <td width="8%" ><%=rd.getStringByDI("POINT","DRIVER_MONEY",i) %></td>
          <td width="7%" ><%=rd.getStringByDI("POINT","COMPANY_MONEY",i) %></td>
  	</tr>
  	<%}%>
</tbody>
<tbody>
<tr align="center">
          <td width="11%" >Ӧ�������ֽ�ϼ�</td>
      <td width="7%" ><%=rd.getStringByDI("EXPENSES","OUGHT_MONEY_COUNT",0) %></td>
      <td width="9%" >&nbsp;</td>
      <td width="7%" >�������</td>
      <td width="7%"><%=rd.getStringByDI("GROUP","GUIDE_DRAWMONEY",0) %></td>
      <td width="8%">&nbsp;</td>
      <td width="9%">ǩ���ֽ����</td>
      <td width="10%"><%=rd.getStringByDI("EXPENSES","SIGN_MONEY_COUNT",0) %></td>
      <td width="10%">&nbsp;</td>
      <td width="7%" >&nbsp;</td>
      <td width="8%" >&nbsp;</td>
      <td width="7%" >&nbsp;</td>
</tr>
</tbody>
</table>
<table border="0" cellpadding="0" cellspacing="0"   width="100%"      class="tr_formtop">
    	<tr class="text_formcolor" >
          <td width="1%"  >      <img src="../images/item_point.gif" alt="" width="6" height="9" /></td>
       	  <td width="26%" >�����ִ</td>
          <td width="12%" > </td>
          <td width="12%" > </td>
          <td width="28%" > </td>
          <td width="6%" ></td>
   	  	  <td width="8%"></td>  
   	  	  <td width="8%"></td>
   	 </tr>
</table>
<table border="0" cellpadding="0" cellspacing="0"  id="shop"  width="95%" align="center" class="text_formtable"  >
<tbody>
    <tr>	
    	  <td valign="top"><p align="center"><strong>��Ʒ��</strong></p></td>
    	  <td valign="top"><p align="center"><strong>����㴿����</strong></p></td>
    	  <td valign="top"><p align="center"><strong>ȫ�㵼����� </strong></p></td>
    	  <td valign="top"><p align="center"><strong>�������</strong></p></td>
    	  <td valign="top"><p align="center"><strong>˾�����</strong></p></td>
    	  <td valign="top"><p align="center"><strong>Ӧ����˾</strong><strong> </strong></p></td>
    	  <td valign="top"><p align="center"><strong>��˾����</strong></p></td>
  </tr>
  <% int goodsRows = rd.getTableRowsCount("SHOP");
  	for(int i=0;i<goodsRows;i++){
  %>
  <tr align="center"  >
          <td width="11%" ><%=rd.getStringByDI("SHOP","GOODS_NAME",i) %></td>
          <td width="11%" ><%=rd.getStringByDI("SHOP","NET_PROFIT",i) %></td>
          <td width="10%" ><%=rd.getStringByDI("SHOP","GUIDE_MONEY",i) %></td>
          <td width="11%"><%=rd.getStringByDI("SHOP","NATIVE_GUIDE_MONEY",i) %></td>
          <td width="10%"><%=rd.getStringByDI("SHOP","DRIVER_MONEY",i) %></td>
          <td width="13%"><%=rd.getStringByDI("SHOP","COMPANY_MONEY",i) %></td>
          <td width="12%"><%=rd.getStringByDI("SHOP","COMPANY_STORAGE_MNY",i) %></td>
  </tr>
<%} %>
</tbody>
</table>
<%} else{%>
<table border="0" cellpadding="0" cellspacing="0"   width="100%"   class="tr_formtop">
  <tr class="text_formcolor" >
	<td></td>
  </tr>
</table>
<table border="0" cellpadding="0" cellspacing="0"  id="shop"  width="95%" align="center" class="text_formtable"  >
	<tr>
		<td>û������</td>
	</tr>
</table>
<%} %>
<table border="0" cellpadding="0" cellspacing="0"   width="100%"   class="tr_formtop">
  <tr class="text_formcolor" >
	<td></td>
  </tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" width="90%" align="center">
	<tr>
    	<td align="center"><input type="button" value="�� ��" class="text_formbnt" onClick="javascript:{window.history.go(-1);}"/></td>
	</tr>
</table>
</body>
</html>