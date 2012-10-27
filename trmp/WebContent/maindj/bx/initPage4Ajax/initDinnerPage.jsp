<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@include file="/common.jsp" %>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
Calendar calendar = Calendar.getInstance();
Date date = (java.util.Date)rd.get("TA_DJ_GROUPs", "BEGIN_DATE", 0);
calendar.setTime(date);

int days = Integer.parseInt(("".equals(rd.getStringByDI("TA_DJ_GROUPs", "ts", 0))) ? "0" : rd.getStringByDI("TA_DJ_GROUPs", "ts", 0));
int tRow = rd.getTableRowsCount("TA_DJ_BXCT");//获取查询记录行数  
for(int i = 0; i < tRow; i++){
String rc = rd.getString("TA_DJ_JHCT","rc",i);
String ycRq = rd.getString("TA_DJ_BXCT","YCRQ",i);
%>
	<tr class="dinnerRow">
		<td><input type="checkbox" class="dinnerCheckbox"></td>
		<td>
		<select name="TA_DJ_BXCT/RC[<%=i%>]" class="rc">
		<%	calendar.setTime(date);
			for (int j = 0; j < days; j++) {
				String dateStr = sdf.format(calendar.getTime());
				int dt =1+j;
			%>
			  <option value="<%=dateStr%>:1" <%if(rc.equals(dateStr+":1")){ %>selected="selected"<%} %>><%= "【D"+dt+"】"+ dateStr+"早"%></option>
			  <option value="<%=dateStr%>:2" <%if(rc.equals(dateStr+":2")){ %>selected="selected"<%} %>><%= "【D"+dt+"】"+ dateStr+"中"%></option>
			  <option value="<%=dateStr%>:3" <%if(rc.equals(dateStr+":3")){ %>selected="selected"<%} %>><%= "【D"+dt+"】"+ dateStr+"晚"%></option>
			<%
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
		%>
		</select><input type="hidden" name="TA_DJ_BXCT/YCRQ[<%=i%>]" value="<%=ycRq%>" class="ycrq"/><input type="hidden" name="TA_DJ_BXCT/CC[<%=i%>]" value="<%=rd.getString("TA_DJ_BXCT","CC",i) %>" class="cc"/>
		<input type="hidden" name="TA_DJ_BXCT/XZQHID[<%=i%>]" value="<%=rd.getString("TA_DJ_BXCT","XZQHID",i) %>" class="dinnerXZQH"/>
		<input type="hidden" name="TA_DJ_BXCT/CT[<%=i%>]"  value="<%=rd.getString("TA_DJ_BXCT","CT",i) %>" class="dinnerId"/>
		</td>
		<td><input type="text" name="TA_DJ_BXCT/CTMC[<%=i%>]" class="dinner"  value="<%=rd.getString("TA_DJ_BXCT","CTMC",i) %>" id="<%=rd.getString("TA_DJ_BXCT","Random",i) %>"/></td>
		<td><input type="text" name="TA_DJ_BXCT/LXR[<%=i%>]"  value="<%=rd.getString("TA_DJ_BXCT","LXR",i) %>" class="smallInput dinnerLxr"/></td>
		<td><input type="text" name="TA_DJ_BXCT/LXFS[<%=i%>]"  value="<%=rd.getString("TA_DJ_BXCT","LXFS",i) %>" class="smallInput dinnerLxrDh"/></td>
		<td><input type="text" name="TA_DJ_BXCT/CS[<%=i%>]" onkeydown="checkNum()" value="<%=rd.getString("TA_DJ_BXCT","CS",i) %>" onkeyup="cSn('dinner');" onchange="cSnChange('dinner')" class="input40 dinnerSnTemp1"/></td>
		<td><input type="text" name="TA_DJ_BXCT/RS[<%=i%>]" onkeydown="checkNum()" value="<%=rd.getString("TA_DJ_BXCT","RS",i) %>" onkeyup="cSn('dinner');" onchange="cSnChange('dinner')" class="input40 dinnerSnTemp2"/></td>
		<td><input type="text" name="TA_DJ_BXCT/JG[<%=i%>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_BXCT","JG",i) %>" onkeyup="cSn('dinner');" onchange="cSnChange('dinner')" class="input40 dinnerSnTemp3"/></td>
		<td><input type="text" name="TA_DJ_BXCT/YGCB[<%=i%>]" value="<%=rd.getString("TA_DJ_BXCT","YGCB",i) %>" onkeydown="checkNumX()" onkeyup="cPri('dinner');" onchange="cPriChange('dinner')" class="smallInput dinnerYgcb"/></td>
		<td><input type="text" name="TA_DJ_BXCT/QDJE[<%=i%>]" value="<%=rd.getString("TA_DJ_BXCT","QDJE",i) %>" onkeydown="checkNumX()" onkeyup="cPri('dinner');" onchange="cPriChange('dinner')" class="smallInput dinnerCbqd"/></td>
		<td><input type="text" name="TA_DJ_BXCT/XFJE[<%=i%>]" value="<%=rd.getString("TA_DJ_BXCT","XFJE",i) %>" readonly="readonly" class="smallInput dinnerCbxf"/></td>
		<td><textarea rows="1" style="width:85%" name="TA_DJ_BXCT/BZ[<%=i%>]"><%=rd.getString("TA_DJ_BXCT","BZ",i) %></textarea></td>
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
$("select").bind('change',function(){//模糊查询名称绑定keyup事件
	var temp = $(this).val().split(':',2);
	$(this).next().val(temp[0]);
	$(this).next().next().val(temp[1]);
});
$(".dinner").bind('keyup',function(){//模糊查询名称绑定keyup事件
	$('#itemRow').val($(".dinner").index(this));
	filterChanged('dinner');
});
//-->
</script>
<%}%>