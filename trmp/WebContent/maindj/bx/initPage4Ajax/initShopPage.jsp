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
int tRow = rd.getTableRowsCount("TA_DJ_BXGW");//获取查询记录行数  
for(int i = 0; i < tRow; i++){
	String hdate = rd.getString("TA_DJ_BXGW","RC",i);
%>

<tr class="shopRow">
	<td><input type="checkbox" class="shopCheckbox"></td>
	<td>
	<select name="TA_DJ_BXGW/RC[<%=i%>]">
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
	</select>
	<input type="hidden" name="TA_DJ_BXGW/xzqhid[<%=i%>]" value="<%=rd.getString("TA_DJ_BXGW","xzqhid",i) %>" class="shopXZQH"/>
	<input type="hidden" name="TA_DJ_BXGW/GWDID[<%=i%>]" value="<%=rd.getString("TA_DJ_BXGW","GWDID",i) %>"  class="shopId"/>
	</td>
	<td><input type="text" name="TA_DJ_BXGW/GWDMC[<%=i%>]" value="<%=rd.getString("TA_DJ_BXGW","GWDMC",i) %>" class="shop"  id="<%=rd.getString("TA_DJ_BXGW","Random",i) %>"/></td>
	<td><input type="text" name="TA_DJ_BXGW/JDRS[<%=i%>]" onkeydown="checkNum()" value="<%=rd.getString("TA_DJ_BXGW","JDRS",i) %>"  class="input40 shopTemp4"/></td>
	<td><input type="text" name="TA_DJ_BXGW/GWRS[<%=i%>]" onkeydown="checkNum()" value="<%=rd.getString("TA_DJ_BXGW","GWRS",i) %>"  class="input40"/></td>
	<td><input type="text" name="TA_DJ_BXGW/RTF[<%=i%>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_BXGW","RTF",i) %>"  title="当前购物点成本总计/单位：元" class="input40 shopTemp1"/></td>
	<td><input type="text" name="TA_DJ_BXGW/XFE[<%=i%>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_BXGW","XFE",i) %>"  title="当前购物点消费额总计/单位：元" onkeyup="cSn('shop');" onchange="cSnChange('shop')" class="input40 shopTemp3"/></td>
	<td><input type="text" name="TA_DJ_BXGW/GSLC[<%=i%>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_BXGW","GSLC",i) %>"  title="当前购物点公司留存/单位：元" class="input40 shopTemp2"/></td>
	<td>
		导游：<input type="text" name="TA_DJ_BXGW/DYTC[<%=i%>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_BXGW","DYTC",i) %>"  onkeyup="cSn('shop');" onchange="cSnChange('shop')" class="input40 shopdytc"/><p>
		司机：<input type="text" name="TA_DJ_BXGW/SJTC[<%=i%>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_BXGW","SJTC",i) %>"  onkeyup="cSn('shop');" onchange="cSnChange('shop')" class="input40 shopsjtc"/><p>
		全陪：<input type="text" name="TA_DJ_BXGW/QPTC[<%=i%>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_BXGW","QPTC",i) %>"  onkeyup="cSn('shop');" onchange="cSnChange('shop')" class="input40 shopqptc"/><p>
		公司：<input type="text" name="TA_DJ_BXGW/GSTC[<%=i%>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_BXGW","GSTC",i) %>"  onkeyup="cSn('shop');" onchange="cSnChange('shop')" class="input40 shopSnTemp1"/><p>
	</td>
	<td><input type="text" name="TA_DJ_BXGW/YJGS[<%=i%>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_BXGW","YJGS",i) %>" class="input40 shopTemp5"/></td>
	<td><textarea rows="1" style="width:85%" name="TA_DJ_BXGW/BZ[<%=i%>]"><%=rd.getString("TA_DJ_BXGW","BZ",i) %></textarea></td>
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