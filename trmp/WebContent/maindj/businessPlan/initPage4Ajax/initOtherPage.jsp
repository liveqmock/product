<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
	int tRow = rd.getTableRowsCount("TA_DJ_JHQT");//获取查询记录行数  
	for(int i = 0; i < tRow; i++){
%>
<tr class="otherRow"> 
  <td><input type="checkbox" class="otherCheckbox" /></td>
  <td><input type="text" name="TA_DJ_JHQT/APMC[<%=i %>]" value="<%=rd.getString("TA_DJ_JHQT","APMC",i) %>"/></td>
  <td><input type="text" name="TA_DJ_JHQT/DJ[<%=i %>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_JHQT","dj",i) %>"/></td>
  <td><input type="text" name="TA_DJ_JHQT/YGCB[<%=i %>]" value="<%=rd.getString("TA_DJ_JHQT","ygcb",i) %>" onkeydown="checkNumX()" onkeyup="cPri('other');" onchange="cPriChange('other')" class="otherYgcb"/></td>
  <td><input type="text" name="TA_DJ_JHQT/QDJE[<%=i %>]" value="<%=rd.getString("TA_DJ_JHQT","qdje",i) %>" onkeydown="checkNumX()" onkeyup="cPri('other');" onchange="cPriChange('other')" class="otherCbqd"/></td>
  <td><input type="text" name="TA_DJ_JHQT/XFJE[<%=i %>]" value="<%=rd.getString("TA_DJ_JHQT","xfje",i) %>" readonly="readonly" class="otherCbxf"/></td>
  <td><textarea rows="1" style="width:85%" name="TA_DJ_JHQT/BZ[<%=i %>]"><%=rd.getString("TA_DJ_JHQT","BZ",i) %></textarea></td>
</tr>
<%}%>