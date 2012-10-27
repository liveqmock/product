<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>

<%
	BizData rd = (BizData) request.getAttribute(SysVar.REQ_DATA) ;
	BizData sd = (BizData) session.getAttribute(SysVar.SS_DATA);
	if (sd == null) {
		String loginPage = SysVar.LOGINPAGE.replaceAll(
				SysVar.CONTEXT_PATH, request.getContextPath());
		response.sendRedirect(loginPage);
	}
	if (rd == null) {
		rd = new BizData();
	}
%>
<%
int SceneryRows = rd.getTableRowsCount("TA_SCENERY_POINTs");
if(SceneryRows>0){
for(int l = 0; l < SceneryRows; l++){
	String CSID = rd.getStringByDI("TA_SCENERY_POINTs","CITY_ID",l);
	String sceneryName = rd.getStringByDI("TA_SCENERY_POINTs","cmpny_name",l);
	String JDID = rd.getStringByDI("TA_SCENERY_POINTs","scenery_id",l);
%>
 <table class="datas select-scenery" width="95%" id="itemTab<%=JDID %>">
	<tr>
	  <td width="8%" align="right">景点名称：</td>
	  <td width="15%"><font color="red"><%=sceneryName %></font>
	  </td>
	  <td width="20%">
	  	<input name="dwj<%=JDID %>" type="radio" value="1" checked="checked" id="dwj" onclick="queryAllPriceByScenery(this.value,'itemTab<%=JDID %>','dwj<%=JDID %>','<%=JDID %>');"/>永久
	  	<input name="dwj<%=JDID %>" type="radio" value="2" id="dwj" onclick="queryAllPriceByScenery(this.value,'itemTab<%=JDID %>','dwj<%=JDID %>','<%=JDID %>');"/>淡季价格
	  	<input name="dwj<%=JDID %>" type="radio" value="3" id="dwj" onclick="queryAllPriceByScenery(this.value,'itemTab<%=JDID %>','dwj<%=JDID %>','<%=JDID %>');"/>旺季价格
	  </td>
	  <td width="10%">
	  	<input type="hidden" name="JDID<%=CSID %>/JDID[<%=l %>]" value="<%=JDID %>" />
	    <input type="checkbox" name="SFXZ<%=JDID %>" onclick="queryAllPriceByScenery(this.value,'itemTab<%=JDID %>','dwj<%=JDID %>','<%=JDID %>');sumPrice('scenery<%=JDID%>_tab');" 
	    	   value="Y" class="checkbox"/>勾中选择此景点
	  </td>
	</tr>
	<tr style="display: none;">
		<td class="priceInfo" colspan="4"></td>
	</tr>
  </table>
<%
}
}else{
%>
 <table class="datas select-scenery" width="95%" id="itemTab0">
	<tr>
		<td><font color="red">当前城市无景点！</font></td>
	</tr>
  </table>
<%}%>