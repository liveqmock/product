<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@include file="/common.jsp" %>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String groupId = rd.getStringByDI("TA_DJ_GROUPs", "id", 0);
	int days = Integer.parseInt(("".equals(rd.getStringByDI("TA_DJ_GROUPs", "ts", 0))) ? "0" : rd.getStringByDI("TA_DJ_GROUPs", "ts", 0));
	int tRow = rd.getTableRowsCount("TA_DJ_BXJD");//获取查询记录行数  
	for(int i = 0; i < tRow; i++){

		Calendar calendar = Calendar.getInstance();
		Date date = (java.util.Date)rd.get("TA_DJ_GROUPs", "BEGIN_DATE", 0);
		calendar.setTime(date);
%>
<tr class="sceneryRow"> 
  <td><input type="checkbox" class="sceneryCheckbox" /></td>
  <td>
  	<select name="TA_DJ_BXJD/days[<%=i %>]">
	<%
		String cfrq = rd.getString("TA_DJ_BXJD","rq",i);
		for (int j = 0; j < days; j++) {
			int dt =1+j;
			String dateStr = sdf.format(calendar.getTime());
	%>
	  <option value="<%= "【D"+dt+"】,"+ dateStr%>" <%if(cfrq.equals(dateStr)){ %>selected="selected" <%} %> ><%= "【D"+dt+"】"+ dateStr%></option>
	<%
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	%>
	</select>
  </td>
  <td>
  	<input type="text" name="TA_DJ_BXJD/JDMC[<%=i %>]" value="<%=rd.getString("TA_DJ_BXJD","JDMC",i) %>" class="scenery" id="<%=rd.getString("TA_DJ_BXJD","Random",i) %>"/>
	<input type="hidden" name="TA_DJ_BXJD/xzqhid[<%=i %>]" class="sceneryXZQH" value="<%=rd.getString("TA_DJ_BXJD","xzqhid",i) %>"/>
	<input type="hidden" name="TA_DJ_BXJD/JDID[<%=i %>]" class="sceneryId" value="<%=rd.getString("TA_DJ_BXJD","jdid",i) %>"/>
  </td>
  
  <td><input type="text" name="TA_DJ_BXJD/lxr[<%=i %>]" class="smallInput lxr" value="<%=rd.getString("TA_DJ_BXJD","lxr",i) %>"/></td>
  <td><input type="text" name="TA_DJ_BXJD/lxfs[<%=i %>]" class="smallInput lxfs" value="<%=rd.getString("TA_DJ_BXJD","lxfs",i) %>"/></td>
  <td><input type="text" name="TA_DJ_BXJD/rs[<%=i %>]" onkeydown="checkNum()" value="<%=rd.getString("TA_DJ_BXJD","rs",i) %>" onkeyup="cSn('scenery');" onchange="cSnChange('scenery')" class="smallInput scenerySnTemp1"/></td>
  <td><input type="text" name="TA_DJ_BXJD/dj[<%=i %>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_BXJD","dj",i) %>" onkeyup="cSn('scenery');" onchange="cSnChange('scenery')" class="smallInput scenerySnTemp2"/><input type="hidden" value="1" onkeydown="checkNumX()" onkeyup="cSn('scenery');" onchange="cSnChange('scenery')" class="input40 scenerySnTemp3"/></td>
  <td><input type="text" name="TA_DJ_BXJD/TGCB[<%=i %>]" onkeydown="checkNumX()" onkeyup="cPri('scenery');" onchange="cPriChange('scenery')" class="smallInput sceneryYgcb" value="<%=rd.getString("TA_DJ_BXJD","tgcb",i) %>"/></td>
  <td><input type="text" name="TA_DJ_BXJD/QDJE[<%=i %>]" onkeydown="checkNumX()" onkeyup="cPri('scenery');" onchange="cPriChange('scenery')" class="smallInput sceneryCbqd" value="<%=rd.getString("TA_DJ_BXJD","qdje",i) %>"/></td>
  <td><input type="text" name="TA_DJ_BXJD/xfje[<%=i %>]" readonly="readonly" class="smallInput sceneryCbxf" value="<%=rd.getString("TA_DJ_BXJD","xfje",i) %>"/></td>
  <td><textarea rows="1" style="width:85%" name="TA_DJ_BXJD/BZ[<%=i %>]"><%=rd.getString("TA_DJ_BXJD","bz",i) %></textarea></td>
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

$(".scenery").bind('keyup',function(){//模糊查询名称绑定keyup事件
	$('#itemRow').val($(".scenery").index(this));
	filterChanged('scenery');
});
//-->
</script>
<%}%>