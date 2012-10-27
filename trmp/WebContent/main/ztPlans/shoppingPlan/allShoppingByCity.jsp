<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
  <%@include file="/common.jsp" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>

	<%
	int rows=rd.getTableRowsCount("shopListAddGWD");
	if(rows > 0){
		for(int i = 0;i < rows; i++){ %>
			<input type="hidden" name="TA_ZT_JHGW<%=rd.getString("city")%>/GWDID[<%=i%>]" value="<%=rd.getStringByDI("shopListAddGWD","shop_point_id",i) %>" />
			<input type="hidden" name="TA_ZT_JHGW<%=rd.getString("city")%>/GWDMC[<%=i%>]" value="<%=rd.getStringByDI("shopListAddGWD","cmpny_name",i) %>" />
			<% if(i==1){out.print("&nbsp;&nbsp;&nbsp;");}else{out.print("&nbsp;");}%>
			<input type="checkbox" name="TA_ZT_JHGW<%=rd.getString("city")%>/SFXZ[<%=i%>]" value="Y" />
			<font color="red"><%=rd.getStringByDI("shopListAddGWD","cmpny_name",i)%></font><!-- 输出购物点名称 -->

			<%
				int rowsAdd=rd.getTableRowsCount("shopListAddRTF"); //获取此团客源地,通过客源地获取此团人头费
				for(int j = 0;j < rowsAdd; j++){
					String sfIdAdd=rd.getStringByDI("shopListAddRTF","id",j);//省份ID
					String sfNameAdd=rd.getStringByDI("shopListAddRTF","name",j);//省份名称
					String rtfAdd=rd.getStringByDI("shopListAddRTF","Pre_Pee",j);//人头费
			%>
				&nbsp;&nbsp;<font color="blue"><%= sfNameAdd%></font>=> 人头费：
				<input type="hidden" name="TA_ZT_GWRTF<%=rd.getStringByDI("shopListAddGWD","shop_point_id",i) %>/CSID[<%=j %>]" value="<%=sfIdAdd %>"/>
				<input type="text" name="TA_ZT_GWRTF<%=rd.getStringByDI("shopListAddGWD","shop_point_id",i) %>/RTF[<%=j %>]" value="<%=rtfAdd %>" class="smallerInput rtf"/>
				<%
				}
				%>
				</br>
	<%
		}
	%>

	<%
		}else{
	%>
		<font color="red">当前地区无购物点,请重新选择。</font>
	<%} %>
</html>