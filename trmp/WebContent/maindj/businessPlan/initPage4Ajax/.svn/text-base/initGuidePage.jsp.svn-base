<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@include file="/common.jsp" %>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String groupId = rd.getStringByDI("TA_DJ_GROUPs", "id", 0);
	int days = Integer.parseInt(("".equals(rd.getStringByDI("TA_DJ_GROUPs", "ts", 0))) ? "0" : rd.getStringByDI("TA_DJ_GROUPs", "ts", 0));
	int tRow = rd.getTableRowsCount("TA_DJ_JHDY");//获取查询记录行数  
	for(int i = 0; i < tRow; i++){

		Calendar calendar = Calendar.getInstance();
		Date date = (java.util.Date)rd.get("TA_DJ_GROUPs", "BEGIN_DATE", 0);
		calendar.setTime(date);
%>
<tr class="guideRow"> 
  <td><input type="checkbox" class="guideCheckbox" /></td>
  <td>
  	<select name="TA_DJ_JHDY/CFRQ[<%=i %>]">
	<%
		String cfrq = rd.getString("TA_DJ_JHDY","cfrq",i);
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
  	<select name="TA_DJ_JHDY/fhrq[<%=i %>]">
	<%
		calendar.setTime(date);
		String fhrq = rd.getString("TA_DJ_JHDY","fhrq",i);
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
  <td><input type="text" name="TA_DJ_JHDY/dyxm[<%=i %>]" value="<%=rd.getString("TA_DJ_JHDY","dyxm",i) %>" onclick="testWin('guide');" class="guide smallInput" id="<%=rd.getString("TA_DJ_JHDY","Random",i) %>"/><input type="hidden" name="TA_DJ_JHDY/dyid[<%=i %>]" value="<%=rd.getString("TA_DJ_JHDY","dyid",i) %>" class="guideId"/></td>
  <td><input type="text" name="TA_DJ_JHDY/SJHM[<%=i %>]" value="<%=rd.getString("TA_DJ_JHDY","sjhm",i) %>" class="guideTel smallerInput"/></td>
  <td><input type="text" name="TA_DJ_JHDY/DYZH[<%=i %>]" value="<%=rd.getString("TA_DJ_JHDY","dyzh",i) %>" class="guideCode smallerInput"/></td>
  <td><input type="text" name="TA_DJ_JHDY/DYLK[<%=i %>]" value="<%=rd.getString("TA_DJ_JHDY","dylk",i) %>" onkeydown="checkNumX()" class="smallInput guideTemp1"/></td>
  <td><input type="text" name="TA_DJ_JHDY/dff[<%=i %>]" value="<%=rd.getString("TA_DJ_JHDY","dff",i) %>" onkeydown="checkNumX()" class="smallInput guideTemp2"/></td>
  <td><textarea rows="1" style="width:85%" name="TA_DJ_JHDY/BZ[<%=i %>]"><%=rd.getString("TA_DJ_JHDY","BZ",i) %></textarea></td>
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

$(".guide").bind('click',function(){//模糊查询名称绑定click事件
	$('#itemRow').val($(".guide").index(this));
});
//-->
</script>
<%}%>