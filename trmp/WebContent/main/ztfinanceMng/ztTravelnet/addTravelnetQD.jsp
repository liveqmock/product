<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
<title>ǩ������</title>
<script type="text/javascript">

	function checkNum(){
		if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
			  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
			    event.returnValue=false;
	}

	function sumRs(){

			var qd=jQuery("#qd").val();
			var xf=jQuery("#xf").val();
			if(parseInt(qd)<parseInt(xf))
			{
				alert("����ǩ���ܶ��������д���");
				jQuery("#xf").val(0);
				jQuery("#ye").val(0);
				}
			else
			{
			jQuery("#ye").val(parseInt(qd-xf));
			}
	}
	function addTRQD(){
		
		var qd=jQuery("#qd").val();
		var xf=jQuery("#xf").val();
		if(parseInt(qd)<parseInt(xf))
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
		jQuery("#ye").val(parseInt(qd-xf));
		document.qiandan_form.action="addTRqingdan.";
		document.qiandan_form.submit();
		window.opener.location.reload(); 
		window.close(); 
		}

		
		
	}
</script>
<script type="text/javascript">

</script>
</head>
<body>
<form  name="qiandan_form" method="post">
<input type="hidden" name="userno" value="1"></input>
<div id="lable"><span >ǩ������</span></div>
<div id="bm-table">
	<div id="hotelDiv">	
	<table class="datas select-hotel"  border="1"
 width="95%" id="hotel0">
	
			<tr height=25 style='height:32pt'>
  			<td colspan=2 height=27 class=xl27 width=588 align="center" style='height:20.25pt;
  			width:441pt'></td>
 			</tr>
			
			<tr><td colspan=1 >�ؽ����ƣ�</td><td><input type="text"  id="hj" value="<%=rd.getStringByDI("selectBxTR","cmpny_name",0)%>"  readonly="readonly"  / ></td></tr>  
			<tr><td colspan=1>�źţ�</td><td><input type="text"  id="hj" value="<%=rd.getStringByDI("selectBxTR","tid",0)%>"  readonly="readonly"  / ></td></tr>			
			<tr><td>ǩ���ܼƣ�</td><td><input type="text"  id="hj" value="<%=rd.getStringByDI("selectBxTR","qdje",0) %> "  readonly="readonly"  / >/Ԫ</td></tr>
			<tr><td>����ǩ����</td><td><input type="text"  id="yq" value="<%=rd.getStringByDI("selectBxTR","hkje",0) %> "  readonly="readonly"  / >/Ԫ</td></tr>
			<tr>
				<td>
				<font color="red">ʣ��ǩ���</font></td><td><input type="text"  id="qd" value="<%=rd.getStringByDI("selectBxTR","ye",0) %> "  readonly="readonly" class="qdxj" / >/Ԫ
				</td>
			</tr>
			<tr>	
				<td><font color="red">�ָ���</font></td><td><input type="text" value="" name="TA_ZT_QKJL4TRA/HKJE[0]" id="xf" onkeydown="checkNum()" onchange="sumRs()"  class="xfxj"/>/Ԫ
				</td>		
			</tr>
			<tr >
				<td><font color="red">��</font></td><td><input type="text" value="" name="TA_ZT_QKJL4TRA/YE[0]" value="" readonly="readonly" id="ye" class="yue" />/Ԫ
				</td>
			</tr>
			<tr >

				<td colspan="">
			<input type="hidden" name="TA_ZT_QKJL4TRA/TID[0]" value="<%=rd.getStringByDI("selectBxTR","tid",0)%>"/>
			<input type="hidden" name="TA_ZT_QKJL4TRA/SFID[0]" value="<%=rd.getStringByDI("selectBxTR","sfid",0)%>"/>
			<input type="hidden" name="TA_ZT_QKJL4TRA/CSID[0]" value="<%=rd.getStringByDI("selectBxTR","csid",0)%>"/>
			<input type="hidden" name="TA_ZT_QKJL4TRA/DJS[0]" value="<%=rd.getStringByDI("selectBxTR","djsid",0)%>"/>
			<input type="hidden" name="TA_ZT_QKJL4TRA/CZR[0]" value="<%=sd.getString("userno") %>"/>	
				</td>
			</tr>

  </table>


</div>

</div>
<div align="center" id="last-sub">
	<input type="button" id="button" value="�ύ" onclick="addTRQD();"/>
	<input type="button" id="button" value="�ر�" onclick="javascript:window.close();"/>
</div>

</form>
</body>
</html>
