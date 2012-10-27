<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
	int tRow = rd.getTableRowsCount("TA_DJ_BXQT");//获取查询记录行数  
	for(int i = 0; i < tRow; i++){
%>
<tr class="otherRow"> 
  <td><input type="checkbox" class="otherCheckbox" /></td>
  <td><input type="text" name="TA_DJ_BXQT/APMC[<%=i %>]" value="<%=rd.getString("TA_DJ_BXQT","APMC",i) %>"/></td>
  <td><input type="text" name="TA_DJ_BXQT/DJ[<%=i %>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_BXQT","dj",i) %>"/></td>
  <td><input type="text" name="TA_DJ_BXQT/YGCB[<%=i %>]" value="<%=rd.getString("TA_DJ_BXQT","ygcb",i) %>" onkeydown="checkNumX()" onkeyup="cPri('other');" onchange="cPriChange('other')" class="otherYgcb"/></td>
  <td><input type="text" name="TA_DJ_BXQT/QDJE[<%=i %>]" value="<%=rd.getString("TA_DJ_BXQT","qdje",i) %>" onkeydown="checkNumX()" onkeyup="cPri('other');" onchange="cPriChange('other')" class="otherCbqd"/></td>
  <td><input type="text" name="TA_DJ_BXQT/XFJE[<%=i %>]" value="<%=rd.getString("TA_DJ_BXQT","xfje",i) %>" readonly="readonly" class="otherCbxf"/></td>
  <td><textarea rows="1" style="width:85%" name="TA_DJ_BXQT/BZ[<%=i %>]"><%=rd.getString("TA_DJ_BXQT","BZ",i) %></textarea></td>
</tr>
<%}%>