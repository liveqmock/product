<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<title>ǩ������</title>
<script type="text/javascript">
$(function(){
	$("#xf").focus();
	$("#xf").css("background-color","inactivecaptiontext");
});

	function sumRs(){

			var qd=jQuery("#qd").val();
			var xf=jQuery("#xf").val();
			if(parseFloat(qd)<parseFloat(xf))
			{
				alert("����ǩ���ܶ��������д���");
				jQuery("#xf").val(0);
				jQuery("#ye").val(0);
				}
			else
			{
				jQuery("#ye").val((parseFloat(qd-xf)).toFixed(2));
			}
	}
	function addJingDQD(){
		
		var qd=jQuery("#qd").val();
		var xf=jQuery("#xf").val();
		if(parseFloat(qd)<parseFloat(xf))
		{
			alert("���������ܶ�����ϸ");
			jQuery("#xf").val(0);
			jQuery("#ye").val(0);
			}

		else if(""==xf || 0==xf )
		{
			
			alert("��������д���");
			}

		else
		{
		jQuery("#ye").val((parseFloat(qd-xf)).toFixed(2));
		document.qiandan_form.action="adddjJingDqingdan.";
		document.qiandan_form.submit();
		parent.TB_remove_refresh();
		}

		
		
	}
</script>
<script type="text/javascript">

</script>
</head>
<body>
<form  name="qiandan_form" method="post">
<input type="hidden" name="userno" value="1"></input>
<div id="lable"><span >����ǩ������</span></div>
<div id="bm-table">
	<div id="hotelDiv">	
	<table class="datas select-hotel"  border="1" width="95%" id="hotel0">
	
			<tr height=25 style='height:32pt'>
  			<td colspan=2 height=27 class=xl27 width=588 align="center" style='height:20.25pt;
  			width:441pt'></td>
 			</tr>
			
			<tr><td colspan=1 >�������ƣ�</td><td><input type="text"  id="hj" value="<%=rd.getStringByDI("selectBxJingD","jdmc",0)%>"  readonly="readonly"  /></td></tr>  
			<tr><td colspan=1 >�źţ�</td><td><input type="text"  id="hj" value="<%=rd.getStringByDI("selectBxJingD","id",0)%>"  readonly="readonly"  /></td></tr>
			
			<tr><td>ǩ���ܼƣ�</td><td><input type="text"  id="hj" value="<%=rd.getStringByDI("selectBxJingD","qdje",0) %>"  readonly="readonly"  />/Ԫ</td></tr>
			<tr><td>����ǩ����</td><td><input type="text"  id="yq" value="<%=rd.getStringByDI("selectBxJingD","hkje",0) %>"  readonly="readonly"  />/Ԫ</td></tr>
			<tr>
				<td>
				<font color="red">ʣ��ǩ���</font></td><td><input type="text"  id="qd" value="<%=rd.getStringByDI("selectBxJingD","ye",0) %>"  readonly="readonly" class="qdxj" />/Ԫ
				</td>
			</tr>
			<tr>	
				<td><font color="red">�ָ���</font></td><td><input type="text" value="<%=rd.getStringByDI("selectBxJingD","ye",0) %>" name="TA_DJ_QDQKJL4JD/HKJE[0]" id="xf" onkeydown="checkNumX()" onkeyup="sumRs()"  class="xfxj"/>/Ԫ
				</td>		
			</tr>
			<tr >
				<td><font color="red">��</font></td><td><input type="text" value="0" name="TA_DJ_QDQKJL4JD/YE[0]"   readonly="readonly" id="ye" class="yue" />/Ԫ
				</td>
			</tr>
			<input type="hidden" name="TA_DJ_QDQKJL4JD/TID[0]" value="<%=rd.getStringByDI("selectBxJingD","id",0)%>"/>
			<input type="hidden" name="TA_DJ_QDQKJL4JD/JDID[0]" value="<%=rd.getStringByDI("selectBxJingD","jdid",0)%>"/>
			<input type="hidden" name="TA_DJ_QDQKJL4JD/CZR[0]" value="<%=sd.getString("userno") %>"/>	
  </table>


</div>

</div>
<div align="center" id="last-sub">
	<input type="button" id="button" value="�ύ" onclick="addJingDQD();"/>
	<input type="button" id="button" value="�ر�" onclick="javascript:parent.TB_remove();"/>
</div>

</form>
</body>
</html>
