<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>

<%
	//request data object
	BizData rd = (BizData) request.getAttribute(SysVar.REQ_DATA) ;
	//the session data object of the current session;
	BizData sd = (BizData) session.getAttribute(SysVar.SS_DATA);
	// if session data is null,redirect current response to the LOGINPAGE;
	//DEBUG

	if (sd == null) {
		String loginPage = SysVar.LOGINPAGE.replaceAll(
				SysVar.CONTEXT_PATH, request.getContextPath());
		response.sendRedirect(loginPage);
	}
	//debug
	if (rd == null) {
		rd = new BizData();
	}
	String JDID = rd.getString("JDID");
%>
<table class="datas sceneryPrice" style="text-align: center" id="scenery<%=JDID%>_tab">
	<tr id="first-tr">
		<td >价格名称</td>
		<td >价格</td>
		<td >人数</td>
	</tr>
<%
int rows = rd.getTableRowsCount("sqlListAddPrice");
for(int i=0;i<rows;i++) {
	String price = rd.getStringByDI("sqlListAddPrice","price",i);//价格
	String priceName = rd.getStringByDI("sqlListAddPrice","priceName",i);//价格名称
	String priceNMC = rd.getStringByDI("sqlListAddPrice","priceNMC",i);//价格名称ID
%>	
  <tr>
	<td width="34%"><%=priceName %>
	  <input type="hidden" name="TA_ZT_JHJDJG<%=JDID %>/JGMCID[<%=i %>]" value="<%=priceNMC %>"/>
	  <input type="hidden" name="TA_ZT_JHJDJG<%=JDID %>/JGMC[<%=i %>]" value="<%=priceName %>" />
	</td>
	<td width="33%"><%=price %>
	  <input type="hidden" name="TA_ZT_JHJDJG<%=JDID %>/JG[<%=i %>]" value="<%=price %>" class="sc_price"/>
	</td>
	<td width="33%">
	  <input type="text" name="TA_ZT_JHJDJG<%=JDID %>/RS[<%=i %>]" class="smallerInput sc_num" value="0"  onkeydown="checkNum()" onchange="sumPrice('scenery<%=JDID%>_tab')"/>
	</td>
  </tr>
<%
}%>
<tr>
  <td align="right" colspan="7">
  <font color="red">签单小计金额：</font>
	  <input type="text" name="QDXJ<%=JDID %>" value="0" type="text" class="smallerInput qdxj" onchange="sumPrice('scenery<%=JDID%>_tab')" onkeydown="checkNum1(this)"/>/元&nbsp;&nbsp;&nbsp;
 	 <font color="red">现付小计金额：</font>
	  <input type="text" name="XFXJ<%=JDID %>" value="0" type="text"  onchange="sumPrice('scenery<%=JDID%>_tab')" class="smallerInput xfxj" onkeydown="checkNum1(this)"/>/元&nbsp;&nbsp;&nbsp;
	<font color="red">共计：</font>
			<input  name="HJ<%=JDID %>" value="0" type="text"  readonly="readonly"  onchange="sumPrice('scenery<%=JDID%>_tab')" class="smallerInput gj"/>/元
  </td>
</tr>	
</table>

