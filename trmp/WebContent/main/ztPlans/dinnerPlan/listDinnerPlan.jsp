<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
<title>�鿴�����ƻ�</title>

</head>

<body>
<form name="p_dinnerplan_form" method="post">
<div id="lable"><span >�鿴�����ƻ�</span>
			<%
				String hzs=rd.getStringByDI("jhHzs","hzs",0);
				String ts=rd.getStringByDI("TA_ZT_GROUPs","DAYS",0);
				if(0<Integer.parseInt(hzs)){
			%>
			(<span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="�鿴�����ƻ���Ϣ">
			 <a style="color:red;" href="<%=request.getContextPath()%>/main/ztPlans/hotelPlan/ztReturnSelectHotelPlanInfo.?TA_ZT_JHHOTEL/TID=<%=rd.getStringByDI("dinnerPlanList","TID",0) %>&TA_TDJDXXZJB_ZT/TID=<%=rd.getStringByDI("dinnerPlanList","TID",0) %>&TID=<%=rd.getStringByDI("dinnerPlanList","TID",0) %>">�Ƶ��Ѻ���� <%=hzs %>��(������<%=ts %>) ����鿴����</a></span>)
			<%} %>
</div>
<div id="bm-table">
<div id="dinnerDiv">
		<%
			int dinnerRows=rd.getTableRowsCount("dinnerPlanList");
			String qdzj=rd.getStringByDI("dinnerPlanList","QDCTZJ",0);
			String xfzj=rd.getStringByDI("dinnerPlanList","XFCTZJ",0);
			String zj=rd.getStringByDI("dinnerPlanList","CTZJ",0);
			for(int i=0;i<dinnerRows;i++){
		%>
		<table class="datas select-dinner" width="98%" id="dinner<%=i %>">
		  <tr>
			<td colspan="4"><span>�����ƻ�</span>
			</td>
		  </tr>
		  <tr>
			<td colspan="4">
		
				������
			  <select name="TA_ZT_JHCT/SF[<%=i %>]" id="pro<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="1"></select>
			  <select name="TA_ZT_JHCT/CITYID[<%=i %>]" id="city<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="2"></select>
			  <select name="TA_ZT_JHCT/CT[<%=i %>]" id="ct<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="3"></select>
			    &nbsp;�ò�����:
			  <input type="text" name="TA_ZT_JHCT/YCRQ[<%=i %>]" value="<%=rd.getStringByDI("dinnerPlanList","YCRQ",i) %>"/>
			</td>
		
		  </tr>
		  <tr  class="none" >
		  <td class="dinnerInfo">
		  			<table class="datas" style="text-align: center">
					<tr id="first-tr">
						<td >��/�� </td>
						<td >�۸�</td>
						<td >����</td>
						<td >����</td>
					</tr>
					<tr>
						<td>���</td>
						<td><input name="TA_ZT_JHCT/ZCJG[<%=i %>]" class="bf_jg" onkeydown="checkNum()" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerPlanList","ZCJG",i) %>" /></td>
						<td><input name="TA_ZT_JHCT/ZCRS[<%=i %>]" class="bf_cs" onkeydown="checkNum()" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerPlanList","ZCCS",i) %>" /></td>
						<td><input name="TA_ZT_JHCT/ZCCS[<%=i %>]" class="bf_rs" onkeydown="checkNum()" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerPlanList","ZCRS",i) %>" />��</td>
		
					</tr>
					<tr>
						<td>����</td>
						<td><input name="TA_ZT_JHCT/ZHCJG[<%=i %>]" class="dn_jg"  onkeydown="checkNum()" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerPlanList","ZHCJG",i) %>" /> </td>
						<td><input name="TA_ZT_JHCT/ZHCRS[<%=i %>]" class="dn_cs"  onkeydown="checkNum()" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerPlanList","ZHCCS",i) %>" /></td>
						<td><input name="TA_ZT_JHCT/ZHCCS[<%=i %>]" class="dn_rs"  onkeydown="checkNum()" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerPlanList","ZHCRS",i) %>" />��</td>
					</tr>
		
			<tr  > 
				<td align="right" colspan="5">
				<font color="red">ǩ��С�ƣ�</font><input name="TA_ZT_JHCT/QDXJJE[<%=i %>]" type="text" class="smallerInput qdxj" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerPlanList","QDXJJE",i) %>"/>/Ԫ&nbsp;&nbsp;&nbsp;
				<font color="red">�ָ�С�ƣ�</font><input name="TA_ZT_JHCT/XFXJJE[<%=i %>]" type="text" readonly="readonly" class="smallerInput xfxj"  value="<%=rd.getStringByDI("dinnerPlanList","XFXJJE",i) %>"/>/Ԫ&nbsp;&nbsp;&nbsp;
				<font color="red">���ƣ�</font>   <input name="TA_ZT_JHCT/HJ[<%=i %>]" type="text"   readonly="readonly" class="smallerInput gj"  value="<%=rd.getStringByDI("dinnerPlanList","HJ",i) %>"/>/Ԫ
				</td>
			</tr>
			</table>
			</td>
			</tr>
		</table>
		<%} %>
</div> 

<table class="datas" style="margin-top: 3px;">
  <tr>
	<td><span>���úϼ�</span></td>
  </tr>
  <tr>

	<td align="right">
	  <font color="red">ǩ������ܼƣ�</font>
	  <input type="text" name="TA_TDJDXXZJB_ZT/QDCTZJ" id="qdzj" readonly="readonly" value="<%="".equals(qdzj)?"0":qdzj %>" class="smallerInput"/>/Ԫ&nbsp;&nbsp;&nbsp; 
	  <font color="red">�ָ�����ܼƣ�</font>
	  <input type="text" name="TA_TDJDXXZJB_ZT/XFCTZJ" id="xfzj" readonly="readonly" value="<%="".equals(xfzj)?"0":xfzj %>"  class="smallerInput"/>/Ԫ&nbsp;&nbsp;&nbsp; 
	  <font color="red">�ܼƣ�</font>
	  <input type="text" name="TA_TDJDXXZJB_ZT/CTZJ" id="zj" readonly="readonly" value="<%="".equals(zj)?"0":zj %>" class="smallerInput" />/Ԫ</td>

  </tr>
</table>
</div>
</form>
</body>
</html>
<script type="text/javascript">
	<%
	for(int v=0;v<dinnerRows;v++){
	%>
	var linkage = new Linkage("dataSrc<%=v%>", "<%=request.getContextPath()%>/main/data/Dinning-Roomz.xml");
	linkage.init();
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("dinnerPlanList","SF",v)%>",1);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("dinnerPlanList","CITYID",v)%>",2);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("dinnerPlanList","CT",v)%>",3);
	<%}%>
</script>
