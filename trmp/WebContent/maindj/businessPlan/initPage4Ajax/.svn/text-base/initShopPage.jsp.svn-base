<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@include file="/common.jsp" %>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
Calendar calendar = Calendar.getInstance();
Date date = (java.util.Date)rd.get("TA_DJ_GROUP", "BEGIN_DATE", 0);
calendar.setTime(date);

int days = Integer.parseInt(("".equals(rd.getStringByDI("TA_DJ_GROUP", "ts", 0))) ? "0" : rd.getStringByDI("TA_DJ_GROUP", "ts", 0));
int tRow = rd.getTableRowsCount("TA_DJ_JHGW");//获取查询记录行数  
for(int i = 0; i < tRow; i++){
	String hdate = rd.getString("TA_DJ_JHGW","RC",i);
%>

<tr class="shopRow">
	<td><input type="checkbox" class="shopCheckbox"></td>
	<td>
	<select name="TA_DJ_JHGW/RC[<%=i%>]">
	<%
		calendar.setTime(date);
		for (int j = 0; j < days; j++) {
			String dateStr = sdf.format(calendar.getTime());
			int dt =1+j;
		%>
		  	<option value="<%=dateStr%>" <%if(hdate.equals(dateStr)){ %>selected="selected"<%} %>><%= "【D"+dt+"】"+ dateStr%></option>
		<%
		calendar.add(Calendar.DAY_OF_MONTH, 1);
	}
	%>
	</select><input type="hidden" name="TA_DJ_JHGW/RZRQ[<%=i%>]" value="<%=hdate%>" class="rzrq"/>
	<input type="hidden" name="TA_DJ_JHGW/XZQHID[<%=i%>]" value="<%=rd.getString("TA_DJ_JHGW","XZQHID",i) %>" class="shopXZQH"/>
	<input type="hidden" name="TA_DJ_JHGW/GWDID[<%=i%>]" value="<%=rd.getString("TA_DJ_JHGW","GWDID",i) %>"  class="shopId"/>
	</td>
	<td><input type="text" name="TA_DJ_JHGW/GWDMC[<%=i%>]" value="<%=rd.getString("TA_DJ_JHGW","GWDMC",i) %>" class="shop"  id="<%=rd.getString("TA_DJ_JHGW","Random",i) %>"/></td>
	<td><input type="text" name="TA_DJ_JHGW/LXR[<%=i%>]" value="<%=rd.getString("TA_DJ_JHGW","LXR",i) %>"  class="smallInput shopLxr"/></td>
	<td><input type="text" name="TA_DJ_JHGW/LXFS[<%=i%>]" value="<%=rd.getString("TA_DJ_JHGW","LXFS",i) %>"  class="smallInput shopLxrDh"/></td>
	<td><textarea rows="1" style="width:85%" name="TA_DJ_JHGW/BZ[<%=i%>]"><%=rd.getString("TA_DJ_JHGW","BZ",i) %></textarea></td>
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
$(".shop").bind('keyup',function(){//模糊查询名称绑定keyup事件
	$('#itemRow').val($(".shop").index(this));
	filterChanged('shop');
});
//-->
</script>
<%}%>