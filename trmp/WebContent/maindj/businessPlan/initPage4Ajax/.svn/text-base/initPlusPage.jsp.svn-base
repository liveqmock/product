<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
	int tRow = rd.getTableRowsCount("TA_DJ_JHJIAD");//获取查询记录行数  
	for(int i = 0; i < tRow; i++){
%>
<tr class="plusRow"> 
  <td><input type="checkbox" class="plusCheckbox" /></td>
  <td><input type="text" name="TA_DJ_JHJIAD/JDMC[<%=i %>]" value="<%=rd.getString("TA_DJ_JHJIAD","JDMC",i) %>" class="plus" id="<%=rd.getString("TA_DJ_JHJIAD","Random",i)%>"/></td>
  <td><input type="text" name="TA_DJ_JHJIAD/LXR[<%=i %>]" value="<%=rd.getString("TA_DJ_JHJIAD","LXR",i) %>" class="plusLinkman"/></td>
  <td>
 	  <input type="text" name="TA_DJ_JHJIAD/LXFS[<%=i %>]" value="<%=rd.getString("TA_DJ_JHJIAD","LXFS",i) %>" class="plusLinkmanTel"/>
 	  <input type="hidden" name="TA_DJ_JHJIAD/JDID[<%=i %>]" value="<%=rd.getString("TA_DJ_JHJIAD","JDID",i) %>" class="plusId"/>
 	  <input type="hidden" name="TA_DJ_JHJIAD/XZQHID[<%=i %>]" value="<%=rd.getString("TA_DJ_JHJIAD","XZQHID",i) %>" class="plusXZQH"/>
  </td>
  <td><textarea rows="1" style="width:85%" name="TA_DJ_JHJIAD/BZ[<%=i %>]"><%=rd.getString("TA_DJ_JHJIAD","BZ",i) %></textarea></td>
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
$(".plus").bind('keyup',function(){//模糊查询名称绑定keyup事件
	$('#itemRow').val($(".plus").index(this));
	filterChanged('plus');
});
//-->
</script>
<%}%>