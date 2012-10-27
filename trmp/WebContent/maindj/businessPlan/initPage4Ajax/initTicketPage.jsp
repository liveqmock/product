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
int tRow = rd.getTableRowsCount("TA_DJ_JHPW");//获取查询记录行数  
for(int i = 0; i < tRow; i++){
String cfRq = rd.getString("TA_DJ_JHPW","CFRQ",i);
String jtlx = rd.getString("TA_DJ_JHPW","JTLX",i); 
%>


<tr class="ticketRow" style="text-indent: 7px;text-align: center;">
	<td><input type="checkbox" class="ticketCheckbox"></td>
	<td>
	<select name="TA_DJ_JHPW/RC[<%=i%>]" class="cfRq">
	<%
	calendar.setTime(date);
	for (int j = 0; j < days; j++) {
		String dateStr = sdf.format(calendar.getTime());
		int dt =1+j;
	%>
	  	<option value="<%=dateStr%>" <%if(cfRq.equals(dateStr)){ %>selected="selected"<%} %>><%= "【D"+dt+"】"+ dateStr%></option>
	<%
		calendar.add(Calendar.DAY_OF_MONTH, 1);
	}
	%>
	</select><input type="hidden" name="TA_DJ_JHPW/CFRQ[<%=i%>]" value="<%=cfRq%>" class="smallerInput"/>
	<input type="hidden" name="TA_DJ_JHPW/XZQHID[<%=i%>]" value="<%=rd.getString("TA_DJ_JHPW","XZQHID",i) %>" class="ticketXZQH"/>
	<input type="hidden" name="TA_DJ_JHPW/DGD[<%=i%>]" value="<%=rd.getString("TA_DJ_JHPW","DGD",i) %>"  class="ticketId"/>
	</td>
	<td>
	<input type="text" size="10%" name="TA_DJ_JHPW/CFSJ[<%=i%>]" value="<%=rd.getString("TA_DJ_JHPW","CFSJ",i) %>" />
	</td>
	<td>
	<select name="TA_DJ_JHPW/JTLX[<%=i%>]">
	<option value="1" <%if("1".equals(jtlx)){ %>selected="selected"<%} %>>火车</option>
	<option value="2" <%if("2".equals(jtlx)){ %>selected="selected"<%} %>>飞机</option>
	<option value="3" <%if("3".equals(jtlx)){ %>selected="selected"<%} %>>邮轮</option>
	</select>
	</td>
	<td><input type="text" name="TA_DJ_JHPW/DGDMC[<%=i%>]" value="<%=rd.getString("TA_DJ_JHPW","DGDMC",i) %>" class="smallerInput ticket"  id="<%=rd.getString("TA_DJ_JHPW","Random",i) %>"/></td>
	<td><label>姓名：</label><input type="text" name="TA_DJ_JHPW/LXR[<%=i%>]" value="<%=rd.getString("TA_DJ_JHPW","LXR",i) %>"  class="smallInput ticketLxr"/><p><label>电话：</label><input type="text" name="TA_DJ_JHPW/LXRDH[<%=i%>]" value="<%=rd.getString("TA_DJ_JHPW","LXRDH",i) %>"   class="smallInput ticketLxDh"/></td>
	<td><input type="text" name="TA_DJ_JHPW/HBCC[<%=i%>]" value="<%=rd.getString("TA_DJ_JHPW","HBCC",i) %>" class="input40"/></td>
	<td><label>出发：</label><input type="text" name="TA_DJ_JHPW/QSZ[<%=i%>]" value="<%=rd.getString("TA_DJ_JHPW","QSZ",i) %>" class="input40"/><p><label>返回：</label><input type="text" name="TA_DJ_JHPW/ZDZ[<%=i%>]" value="<%=rd.getString("TA_DJ_JHPW","ZDZ",i) %>" class="input40"/></td>
	<td><input type="text" name="TA_DJ_JHPW/ZS[<%=i%>]" value="<%=rd.getString("TA_DJ_JHPW","ZS",i) %>" onkeydown="checkNum()" onkeyup="cSn('ticket');" onchange="cSnChange('ticket')" class="input40 ticketSnTemp1"/></td>
	<td><input type="text" name="TA_DJ_JHPW/DJ[<%=i%>]" value="<%=rd.getString("TA_DJ_JHPW","DJ",i) %>" onkeydown="checkNumX()" onkeyup="cSn('ticket');" onchange="cSnChange('ticket')" class="input40 ticketSnTemp2"/><input type="hidden" value="1" onkeydown="checkNumX()" onkeyup="cSn('ticket');" onchange="cSnChange('ticket')" class="input40 ticketSnTemp3"/></td>
	<td><input type="text" name="TA_DJ_JHPW/SXF[<%=i%>]" value="<%=rd.getString("TA_DJ_JHPW","SXF",i) %>" onkeydown="checkNumX()" onkeyup="cSn('ticket');" onchange="cSnChange('ticket')" class="input40 ticketSnTemp4"/></td>
	<td><input type="text" name="TA_DJ_JHPW/YGCB[<%=i%>]" value="<%=rd.getString("TA_DJ_JHPW","YGCB",i) %>" onkeydown="checkNumX()" onkeyup="cPri('ticket');" onchange="cPriChange('ticket')" class="input40 ticketYgcb"/></td>
	<td><input type="text" name="TA_DJ_JHPW/QD[<%=i%>]" value="<%=rd.getString("TA_DJ_JHPW","QD",i) %>" onkeydown="checkNumX()" onkeyup="cPri('ticket');" onchange="cPriChange('ticket')" class="input40 ticketCbqd"/></td>
	<td><input type="text" name="TA_DJ_JHPW/XF[<%=i%>]" value="<%=rd.getString("TA_DJ_JHPW","XF",i) %>" readonly="readonly" class="input40 ticketCbxf"/></td>
	<td><textarea rows="1" style="width:85%" name="TA_DJ_JHPW/BZ[<%=i%>]"><%=rd.getString("TA_DJ_JHPW","BZ",i) %></textarea></td>
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
$(".cfRq").bind('change',function(){//模糊查询名称绑定keyup事件
	var temp = $(this).val().split(':',2);
	$(this).next().val(temp[0]);
	$(this).next().next().val(temp[1]);
});
$(".ticket").bind('keyup',function(){//模糊查询名称绑定keyup事件
	$('#itemRow').val($(".ticket").index(this));
	filterChanged('ticket');
});
//-->
</script>
<%}%>