<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@include file="/common.jsp" %>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String groupId = rd.getStringByDI("TA_DJ_GROUPs", "id", 0);
	int days = Integer.parseInt(("".equals(rd.getStringByDI("TA_DJ_GROUPs", "ts", 0))) ? "0" : rd.getStringByDI("TA_DJ_GROUPs", "ts", 0));
	int tRow = rd.getTableRowsCount("TA_DJ_BXDJ");//获取查询记录行数  
	for(int i = 0; i < tRow; i++){

		Calendar calendar = Calendar.getInstance();
		Date date = (java.util.Date)rd.get("TA_DJ_GROUPs", "BEGIN_DATE", 0);
		calendar.setTime(date);
%>
<tr class="travelRow"> 
  <td><input type="checkbox" class="travelCheckbox" /></td>
  <td>
  	<select name="TA_DJ_BXDJ/ksrq[<%=i %>]">
	<%
		String ksrq = rd.getString("TA_DJ_BXDJ","ksrq",i);
		for (int j = 0; j < days; j++) {
			int dt =1+j;
			String dateStr = sdf.format(calendar.getTime());
	%>
	  <option value="<%=dateStr%>" <%if(ksrq.equals(dateStr)){ %>selected="selected" <%} %> ><%= "【D"+dt+"】"+ dateStr%></option>
	<%
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	%>
	</select>
  </td>
  <td>
	<select name="TA_DJ_BXDJ/JSRQ[<%=i %>]">
	<%
		calendar.setTime(date);
		String JSRQ = rd.getString("TA_DJ_BXDJ","JSRQ",i);
		for (int j = 0; j < days; j++) {
			int dt =1+j;
			String dateStr = sdf.format(calendar.getTime());
	%>
	  <option value="<%=dateStr%>" <%if(JSRQ.equals(dateStr)){ %>selected="selected" <%} %> ><%= "【D"+dt+"】"+ dateStr%></option>
	<%
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	%>
	</select>
  </td>
  <td>
  	  <input type="text" name="TA_DJ_BXDJ/DJSMC[<%=i %>]" value="<%=rd.getString("TA_DJ_BXDJ","DJSMC",i) %>" class="travel" id="<%=rd.getString("TA_DJ_BXDJ","Random",i) %>"/>
  	  <input type="hidden" name="TA_DJ_BXDJ/DJSID[<%=i %>]" value="<%=rd.getString("TA_DJ_BXDJ","DJSID",i) %>" class="travelId"/>
  	  <input type="hidden" name="TA_DJ_BXDJ/xzqhid[<%=i %>]" value="<%=rd.getString("TA_DJ_BXDJ","xzqhid",i) %>" class="travelXZQH"/>
  </td>
  <td><input type="text" name="TA_DJ_BXDJ/lxr[<%=i %>]" value="<%=rd.getString("TA_DJ_BXDJ","lxr",i) %>" class="travelLxr smallInput"/></td>
  <td><input type="text" name="TA_DJ_BXDJ/lxfs[<%=i %>]" value="<%=rd.getString("TA_DJ_BXDJ","lxfs",i) %>" class="travelLxfs smallerInput"/></td>
  <td><input type="text" name="TA_DJ_BXDJ/crrs[<%=i %>]" onkeydown="checkNum()" value="<%=rd.getString("TA_DJ_BXDJ","crrs",i) %>" class="smallInput"/></td>
  <td><input type="text" name="TA_DJ_BXDJ/YFZK[<%=i %>]" value="<%=rd.getString("TA_DJ_BXDJ","yfzk",i) %>" onkeydown="checkNumX()" onkeyup="cPri('travel');" onchange="cPriChange('travel')" class="smallInput travelYgcb"/></td>
  <td><input type="text" name="TA_DJ_BXDJ/QDJE[<%=i %>]" value="<%=rd.getString("TA_DJ_BXDJ","qdje",i) %>" onkeydown="checkNumX()" onkeyup="cPri('travel');" onchange="cPriChange('travel')" class="smallInput travelCbqd"/></td>
  <td><input type="text" name="TA_DJ_BXDJ/XFJE[<%=i %>]" value="<%=rd.getString("TA_DJ_BXDJ","xfje",i) %>" readonly="readonly" class="smallInput travelCbxf"/></td>
  <td><textarea rows="1" style="width:85%" name="TA_DJ_BXDJ/BZ[<%=i %>]"><%=rd.getString("TA_DJ_BXDJ","BZ",i) %></textarea></td>
</tr>

<script type="text/javascript">
<!--

$('[type=checkbox]').bind('click', function() {//绑定单选按钮事件
	var checkClass = $(this).attr("class");
	var checkNum=0;
	$("."+checkClass).each(function(){
		if($(this).is(":checked")){
			
		}else{
			checkNum++;
		}
		if(checkNum == 0){
			$("#"+checkClass).attr("checked","checked");
		}else{
			$("#"+checkClass).removeAttr("checked");
		}
	});
});

$(".travel").bind('keyup',function(){//模糊查询名称绑定keyup事件
	$('#itemRow').val($(".travel").index(this));
	filterChanged('travel');
});
//-->
</script>
<%}%>