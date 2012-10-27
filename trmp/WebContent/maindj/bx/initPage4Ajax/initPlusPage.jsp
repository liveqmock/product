<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
	int tRow = rd.getTableRowsCount("TA_DJ_BXJIADIAN");//获取查询记录行数  
	for(int i = 0; i < tRow; i++){
%>
<tr class="plusRow"> 
  <td><input type="checkbox" class="plusCheckbox" /></td>
  <td>
  	<input type="text" name="TA_DJ_BXJIADIAN/JDMC[<%=i %>]" value="<%=rd.getString("TA_DJ_BXJIADIAN","JDMC",i) %>" class="plus" id="<%=rd.getString("TA_DJ_BXJIADIAN","Random",i)%>"/>
  	<input type="hidden" name="TA_DJ_BXJIADIAN/JDID[<%=i %>]" value="<%=rd.getString("TA_DJ_BXJIADIAN","JDID",i) %>" class="plusId"/>
 	<input type="hidden" name="TA_DJ_BXJIADIAN/XZQHID[<%=i %>]" value="<%=rd.getString("TA_DJ_BXJIADIAN","XZQHID",i) %>" class="plusXZQH"/>
  </td>
  <td><input type="text" name="TA_DJ_BXJIADIAN/GPJ[<%=i %>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_BXJIADIAN","GPJ",i) %>" title="挂牌单价/单位：元" onkeyup="cSn('plus');" onchange="cSnChange('plus');" class="input40 plusSnTemp5"/></td>
  <td><input type="text" name="TA_DJ_BXJIADIAN/CBJ[<%=i %>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_BXJIADIAN","CBJ",i) %>" title="成本单价/单位：元" onkeyup="cSn('plus');" onchange="cSnChange('plus');" class="input40 plusSnTemp1"/></td>
  <td><input type="text" name="TA_DJ_BXJIADIAN/RS[<%=i %>]" onkeydown="checkNum()" value="<%=rd.getString("TA_DJ_BXJIADIAN","RS",i) %>" title="人数" onkeyup="cSn('plus');" onchange="cSnChange('plus');" class="input40 plusSnTemp2"/><input type="hidden" value="1" class="plusSnTemp3"/></td>
  <td><input type="text" name="TA_DJ_BXJIADIAN/CBJE[<%=i %>]" value="<%=rd.getString("TA_DJ_BXJIADIAN","CBJE",i) %>" title="当前景点成本总计/单位：元" onkeydown="checkNumX()" onkeyup="cPri('plus');cPriChange('plus')" class="input40 plusYgcb"/></td>
  <td><input type="text" name="TA_DJ_BXJIADIAN/CBQD[<%=i %>]" value="<%=rd.getString("TA_DJ_BXJIADIAN","CBQD",i) %>" title="成本签单/单位：元" onkeydown="checkNumX()" onkeyup="cPri('plus');cSn('plus');cPriChange('plus');cSnChange('plus');" class="input40 plusCbqd"/></td>
  <td><input type="text" name="TA_DJ_BXJIADIAN/CBXF[<%=i %>]" value="<%=rd.getString("TA_DJ_BXJIADIAN","CBXF",i) %>" title="成本现付/单位：元" readonly="readonly" class="input40 plusCbxf"/></td>
  <td><input type="text" name="TA_DJ_BXJIADIAN/LR[<%=i %>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_BXJIADIAN","LR",i) %>" title="加点利润/单位：元" onkeyup="cSn('plus');" onchange="cSnChange('plus');" class="input40 plusTemp2"/></td>
  <td>
	导游：<input type="text" name="TA_DJ_BXJIADIAN/DYTC[<%=i%>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_BXJIADIAN","DYTC",i) %>"  title="导游总提成/单位：元" onkeyup="cSn('plus');" onchange="cSnChange('plus');" class="input40 plusdytc"/><p>
	司机：<input type="text" name="TA_DJ_BXJIADIAN/SJTC[<%=i%>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_BXJIADIAN","SJTC",i) %>"  title="司机总提成/单位：元" onkeyup="cSn('plus');" onchange="cSnChange('plus');" class="input40 plussjtc"/><p>
	全陪：<input type="text" name="TA_DJ_BXJIADIAN/QPTC[<%=i%>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_BXJIADIAN","QPTC",i) %>"  title="全陪总提成/单位：元" onkeyup="cSn('plus');" onchange="cSnChange('plus');" class="input40 plusqptc"/><p>
	公司：<input type="text" name="TA_DJ_BXJIADIAN/GSTC[<%=i%>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_BXJIADIAN","GSTC",i) %>"  title="公司总提成/单位：元" onkeyup="cSn('plus');" onchange="cSnChange('plus');" class="input40 plusTemp4"/><p>
  </td>
  <td><input type="text" name="TA_DJ_BXJIADIAN/YJGS[<%=i %>]" onkeydown="checkNumX()" value="<%=rd.getString("TA_DJ_BXJIADIAN","YJGS",i) %>" title="应交公司/单位：元" class="input40 plusTemp3"/></td>
  <td><textarea rows="1" style="width:85%" name="TA_DJ_BXJIADIAN/BZ[<%=i %>]"><%=rd.getString("TA_DJ_BXJIADIAN","BZ",i) %></textarea></td>
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