<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@include file="/common.jsp" %>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String groupId = rd.getStringByDI("TA_DJ_GROUPs", "id", 0);
	int days = Integer.parseInt(("".equals(rd.getStringByDI("TA_DJ_GROUPs", "ts", 0))) ? "0" : rd.getStringByDI("TA_DJ_GROUPs", "ts", 0));
	int tRow = rd.getTableRowsCount("TA_DJ_BXDY");//获取查询记录行数  
	for(int i = 0; i < tRow; i++){

		Calendar calendar = Calendar.getInstance();
		Date date = (java.util.Date)rd.get("TA_DJ_GROUPs", "BEGIN_DATE", 0);
		calendar.setTime(date);
%>
<tr class="guideRow"> 
  <td><input type="checkbox" class="guideCheckbox" /></td>
  <td>
  	<select name="TA_DJ_BXDY/CFRQ[<%=i %>]">
	<%
		String cfrq = rd.getString("TA_DJ_BXDY","cfrq",i);
		for (int j = 0; j < days; j++) {
			int dt =1+j;
			String dateStr = sdf.format(calendar.getTime());
	%>
	  <option value="<%=dateStr%>" <%if(cfrq.equals(dateStr)){ %>selected="selected" <%} %> ><%= "【D"+dt+"】"+ dateStr%></option>
	<%
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	%>
	</select>
  </td>
  <td>
  	<select name="TA_DJ_BXDY/fhrq[<%=i %>]">
	<%
		calendar.setTime(date);
		String fhrq = rd.getString("TA_DJ_BXDY","fhrq",i);
		for (int j = 0; j < days; j++) {
			int dt =1+j;
			String dateStr = sdf.format(calendar.getTime());
	%>
	  <option value="<%=dateStr%>" <%if(fhrq.equals(dateStr)){ %>selected="selected" <%} %> ><%= "【D"+dt+"】"+ dateStr%></option>
	<%
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	%>
	</select>
  </td>
  <td><input type="text" name="TA_DJ_BXDY/dyxm[<%=i %>]" value="<%=rd.getString("TA_DJ_BXDY","dyxm",i) %>" readonly="readonly" class="guide smallInput" id="<%=rd.getString("TA_DJ_BXDY","Random",i) %>"/><input type="hidden" name="TA_DJ_BXDY/dyid[<%=i %>]" value="<%=rd.getString("TA_DJ_BXDY","dyid",i) %>" class="guideId"/></td>
  <td><input type="text" name="TA_DJ_BXDY/SJHM[<%=i %>]" value="<%=rd.getString("TA_DJ_BXDY","sjhm",i) %>" readonly="readonly" class="guideTel smallerInput"/></td>
  <td><input type="text" name="TA_DJ_BXDY/DYZH[<%=i %>]" value="<%=rd.getString("TA_DJ_BXDY","dyzh",i) %>" readonly="readonly" class="guideCode smallerInput"/></td>
  <td><input type="text" name="TA_DJ_BXDY/DYLK[<%=i %>]" value="<%=rd.getString("TA_DJ_BXDY","dylk",i) %>" readonly="readonly" onkeydown="checkNumX()" class="smallInput guideTemp1"/></td>
  <td><input type="text" name="TA_DJ_BXDY/dff[<%=i %>]" value="<%=rd.getString("TA_DJ_BXDY","dff",i) %>" readonly="readonly" onkeydown="checkNumX()" class="smallInput guideTemp2"/></td>
  <td><textarea rows="1" style="width:85%" name="TA_DJ_BXDY/BZ[<%=i %>]" readonly="readonly"><%=rd.getString("TA_DJ_BXDY","BZ",i) %></textarea></td>
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

$(".guide").bind('keyup',function(){//模糊查询名称绑定keyup事件
	$('#itemRow').val($(".guide").index(this));
	filterChanged('guide');
});
//-->
</script>
<%}%>