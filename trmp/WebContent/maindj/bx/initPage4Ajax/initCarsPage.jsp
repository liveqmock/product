<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@include file="/common.jsp" %>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String groupId = rd.getStringByDI("TA_DJ_GROUPs", "id", 0);
	int days = Integer.parseInt(("".equals(rd.getStringByDI("TA_DJ_GROUPs", "ts", 0))) ? "0" : rd.getStringByDI("TA_DJ_GROUPs", "ts", 0));

	int tRow = rd.getTableRowsCount("TA_DJ_BXCL");//获取查询记录行数  
	
	// 车型数目
	int cxRow = rd.getTableRowsCount("CLLX");
	
	for(int i = 0; i < tRow; i++)
	{
		Calendar calendar = Calendar.getInstance();
		Date date = (java.util.Date)rd.get("TA_DJ_GROUPs", "BEGIN_DATE", 0);
		calendar.setTime(date);
%>
	<tr class="carRow"> 
		<td>
	  		<input type="checkbox" class="carCheckbox" />
	  	</td>
	  	<td>
	  		<select name="TA_DJ_BXCL/KSRQ[<%=i %>]">
			<%
				String ksrq = rd.getString("TA_DJ_BXCL","ksrq",i);
			
				for (int j = 0; j < days; j++) 
				{
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
	 		<select name="TA_DJ_BXCL/JSRQ[<%=i %>]">
			<%
				calendar.setTime(date);
				String JSRQ = rd.getString("TA_DJ_BXCL","JSRQ",i);
				
				for (int j = 0; j < days; j++)
				{
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
	  		<input type="hidden" name="TA_DJ_BXCL/xzqhid[<%=i %>]" value="<%=rd.getString("TA_DJ_BXCL","xzqhid",i) %>" class="carXZQH" />
	  		<input type="hidden" name="TA_DJ_BXCL/CD[<%=i %>]" value="<%=rd.getString("TA_DJ_BXCL","CD",i) %>" class="carId" />
	 	  	<input type="text" name="TA_DJ_BXCL/CDMC[<%=i %>]" value="<%=rd.getString("TA_DJ_BXCL","CDMC",i) %>" class="car"  id="<%=rd.getString("TA_DJ_BXCL","Random",i)%>" />
		</td>
		<td>
		 	<select name="TA_DJ_BXCL/CX[<%=i %>]">
		 	<%
		 		// 当前车型
		 		String cx = rd.getString("TA_DJ_BXCL","CX",i);
		 	
		 		for (int k =0; k < cxRow; k++)
		 		{
		 			String cucx = rd.getStringByDI("CLLX","DMSM1",k);
		 			String dmz = rd.getStringByDI("CLLX","DMZ",k);
		 		%>
		 			<option value="<%=dmz %>" <% if(cx.equals(dmz))  {%>selected="selected" <%}%> ><%=cucx %></option>
		 		<%
		 		}
		 	%>
		 	</select>
		</td>
		<td>
			<input type="text" name="TA_DJ_BXCL/SJXM[<%=i %>]" value="<%=rd.getString("TA_DJ_BXCL","SJXM",i)%>" class="smallInput sjxm"/>
		</td>
		<td>	
		 	<input type="text" name="TA_DJ_BXCL/SJDH[<%=i %>]" value="<%=rd.getString("TA_DJ_BXCL","SJDH",i)%>" class="smallerInput sjdh"/>
	 	</td>
	 	<td>	
		 	<input type="text" name="TA_DJ_BXCL/CP[<%=i %>]" value="<%=rd.getString("TA_DJ_BXCL","CP",i) %>" class="smallInput cp"/>
	 	</td>
	 	<td>	
		 	<input type="text" name="TA_DJ_BXCL/JG[<%=i %>]" value="<%=rd.getString("TA_DJ_BXCL","JG",i) %>" onkeydown="checkNumX()" onkeyup="cPri('car');" onchange="cPriChange('car')" class="smallInput carYgcb"/>
	 	</td>
	 	<td>	
		 	<input type="text" name="TA_DJ_BXCL/QDJE[<%=i %>]" value="<%=rd.getString("TA_DJ_BXCL","QDJE",i) %>" onkeydown="checkNumX()" onkeyup="cPri('car');" onchange="cPriChange('car')" class="smallInput carCbqd"/>
	 	</td>
	 	<td>	
		 	<input type="text" name="TA_DJ_BXCL/XFJE[<%=i %>]" value="<%=rd.getString("TA_DJ_BXCL","XFJE",i) %>" readonly="readonly" class="smallInput carCbxf"/>
	 	</td>
	  	<td>
	  		<textarea rows="1" style="width:85%" name="TA_DJ_BXCL/BZ[<%=i %>]"><%=rd.getString("TA_DJ_BXCL","BZ",i) %></textarea>
	  	</td>
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
	
	$(".car").bind('keyup',function(){//模糊查询名称绑定keyup事件
		$('#itemRow').val($(".car").index(this));
		filterChanged('car');
	});
	//-->
	</script>
<%}%>