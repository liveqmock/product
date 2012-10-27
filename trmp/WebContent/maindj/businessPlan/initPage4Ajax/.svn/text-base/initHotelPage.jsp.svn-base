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
int tRow = rd.getTableRowsCount("TA_DJ_JHHOTEL");//获取查询记录行数  
for(int i = 0; i < tRow; i++){
	
String sfhz = rd.getString("TA_DJ_JHHOTEL","SFHZ",i);
String rzRq = rd.getString("TA_DJ_JHHOTEL","RZRQ",i);
%>
		<tr class="hotelRow">
			<td><input type="checkbox" class="hotelCheckbox"></td>
			<td>
			<select name="TA_DJ_JHHOTEL/RC[<%=i%>]">
			<%
				calendar.setTime(date);
				for (int j = 0; j < days; j++) {
					String dateStr = sdf.format(calendar.getTime());
					int dt =1+j;
				%>
				  	<option value="<%=dateStr%>" <%if(rzRq.equals(dateStr)){ %>selected="selected"<%} %>><%= "【D"+dt+"】"+ dateStr%></option>
				<%
					calendar.add(Calendar.DAY_OF_MONTH, 1);
				}
			%>
			</select><input type="hidden" name="TA_DJ_JHHOTEL/RZRQ[<%=i%>]" value="<%=rzRq%>" class="rzrq"/>
			<input type="hidden" name="TA_DJ_JHHOTEL/XZQHID[<%=i%>]" value="<%=rd.getString("TA_DJ_JHHOTEL","XZQHID",i) %>" class="hotelXZQH"/>
			<input type="hidden" name="TA_DJ_JHHOTEL/XJ[<%=i%>]" value="<%=rd.getString("TA_DJ_JHHOTEL","XJ",i) %>" class="hotelXj"/>
			<input type="hidden" name="TA_DJ_JHHOTEL/JDID[<%=i%>]" value="<%=rd.getString("TA_DJ_JHHOTEL","JDID",i) %>"  class="hotelId"/>
			
			
			</td>
			<td><input type="text" name="TA_DJ_JHHOTEL/JDMC[<%=i%>]" class="hotel" value="<%=rd.getString("TA_DJ_JHHOTEL","JDMC",i) %>"  id="<%=rd.getString("TA_DJ_JHHOTEL","Random",i) %>"/></td>
			<td><input type="text" name="TA_DJ_JHHOTEL/LXR[<%=i%>]" value="<%=rd.getString("TA_DJ_JHHOTEL","LXR",i) %>"  class="smallInput hotelLxr"/></td>
			<td><input type="text" name="TA_DJ_JHHOTEL/LXRDH[<%=i%>]" value="<%=rd.getString("TA_DJ_JHHOTEL","LXRDH",i) %>"  class="smallInput hotelLxrDh"/></td>
			<td><input type="text" name="TA_DJ_JHHOTEL/RZRS[<%=i%>]" onkeydown="checkNum()" value="<%=rd.getString("TA_DJ_JHHOTEL","RZRS",i) %>" onkeyup="cSn('hotel');" onchange="cSnChange('hotel')" class="input40 hotelSnTemp1"/></td>
			<td><input type="text" name="TA_DJ_JHHOTEL/TS[<%=i%>]" onkeydown="checkNum()" value="<%=rd.getString("TA_DJ_JHHOTEL","TS",i) %>" onkeyup="cSn('hotel');" onchange="cSnChange('hotel')" class="input40 hotelSnTemp2"/></td>
			<td><a>编辑</a></td>
			<td><input type="text" name="TA_DJ_JHHOTEL/DJ[<%=i%>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_JHHOTEL","DJ",i) %>" onkeyup="cSn('hotel');" onchange="cSnChange('hotel')" class="input40 hotelSnTemp3"/></td>
			<td><input type="text" name="TA_DJ_JHHOTEL/YGCB[<%=i%>]" value="<%=rd.getString("TA_DJ_JHHOTEL","YGCB",i) %>" onkeydown="checkNumX()" onkeyup="cPri('hotel');" onchange="cPriChange('hotel')" class="smallInput hotelYgcb"/></td>
			<td><input type="text" name="TA_DJ_JHHOTEL/QDJE[<%=i%>]" value="<%=rd.getString("TA_DJ_JHHOTEL","QDJE",i) %>" onkeydown="checkNumX()" onkeyup="cPri('hotel');" onchange="cPriChange('hotel')" class="smallInput hotelCbqd"/></td>
			<td><input type="text" name="TA_DJ_JHHOTEL/XFJE[<%=i%>]" value="<%=rd.getString("TA_DJ_JHHOTEL","XFJE",i) %>" readonly="readonly" class="smallInput hotelCbxf"/></td>
			<td>
			<select name="TA_DJ_JHHOTEL/SFHZ[<%=i%>]">
			<option value="-1" <%if("-1".equals(sfhz)){ %>selected="selected"<%} %>>不含早</option>
			<option value="1" <%if("1".equals(sfhz)){ %>selected="selected"<%} %>>卓早</option>
			<option value="2" <%if("2".equals(sfhz)){ %>selected="selected"<%} %>>自助早</option>
			<option value="3" <%if("3".equals(sfhz)){ %>selected="selected"<%} %>>打包早</option>
			</select>
			</td>
			<td><textarea rows="1" style="width:85%" name="TA_DJ_JHHOTEL/BZ[<%=i%>]"><%=rd.getString("TA_DJ_JHHOTEL","BZ",i) %></textarea></td>
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

$(".hotel").bind('keyup',function(){//模糊查询名称绑定keyup事件
	$('#itemRow').val($(".hotel").index(this));
	filterChanged('hotel');
});
//-->
</script>
<%}%>