<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
<title>�����ƻ�</title>
<script type="text/javascript">
//����ֻ��Ϊ����
function checkNum(){
	if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
		  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
		    event.returnValue=false;
}
function checkNum1(obj)
{   
	//�Ȱѷ����ֵĶ��滻�����������ֺ�.
	obj.value = obj.value.replace(/[^\d.]/g,"");
	//���뱣֤��һ��Ϊ���ֶ�����.
	obj.value = obj.value.replace(/^\./g,"");
	//��ֻ֤�г���һ��.��û�ж��.
	obj.value = obj.value.replace(/\.{2,}/g,".");
	//��֤.ֻ����һ�Σ������ܳ�����������
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}

var num;
jQuery(document).ready(function(){
	num=jQuery(".select-dinner").size();
});

function addDinner(){
	num=jQuery(".select-dinner").size();
	var d=jQuery(".select-dinner").size();
	//jQuery("#addDinner").click(function(){
		var num=jQuery(".select-dinner").size();
		if(jQuery("#city"+(num-1)).val()==""){
			alert("����д�����������");
			return false;
		};
		
		jQuery("#dinnerDiv").append('<table class="datas select-dinner" width="98%" id="dinner'+num+'"><tr>'+
		'<td colspan="4"><span>�����ƻ�</span>&nbsp;&nbsp;&nbsp<span class="showPointer" onclick="addDinner()">���</span>&nbsp;&nbsp;&nbsp<span class="showPointer" onclick="delTab()">ɾ��</span> </td></tr>'+
		'<input type="hidden" name="TA_ZT_JHCT/TID['+num+']" value="<%=rd.getString("TID")%>">'+
		 '<tr><td colspan="4">�������ƣ�'+
		'<select name="TA_ZT_JHCT/SF['+num+']" id="pro'+num+'" USEDATA="dataSrc'+d+'" SUBCLASS="1"></select>'+
		'<select name="TA_ZT_JHCT/CITYID['+num+']" id="city'+num+'" USEDATA="dataSrc'+d+'" SUBCLASS="2"></select>'+
		'<select name="TA_ZT_JHCT/CT['+num+']" id="ct'+num+'" USEDATA="dataSrc'+d+'" SUBCLASS="3"></select>'+
		'&nbsp;�ò�����:<input type="text" name="TA_ZT_JHCT/YCRQ['+num+']" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/>'+
		'</td></tr>'+
		'<tr  class="none" ><td class="dinnerInfo"><table class="datas" style="text-align: center">'+
		'<tr id="first-tr"><td >��/�� </td><td >�۸�</td><td >����</td><td >����</td></tr>'+
		'<tr><td>���</td><td><input name="TA_ZT_JHCT/ZCJG['+num+']" class="bf_jg" onkeydown="checkNum1(this)" onchange="sumPrice()" value="0" /></td>'+
		'<td><input name="TA_ZT_JHCT/ZCCS['+num+']" class="bf_cs" onkeydown="checkNum()" onchange="sumPrice()" value="0" /></td>'+
		'<td><input name="TA_ZT_JHCT/ZCRS['+num+']" class="bf_rs" onkeydown="checkNum1(this)" onchange="sumPrice()" value="0" />��</td'+
		'</tr><tr><td>����</td><td><input name="TA_ZT_JHCT/ZHCJG['+num+']" class="dn_jg"  onkeydown="checkNum1(this)" onchange="sumPrice()" value="0" /> </td>'+
		'<td><input name="TA_ZT_JHCT/ZHCCS['+num+']" class="dn_cs"  onkeydown="checkNum()" onchange="sumPrice()" value="0" /></td>'+
		'<td><input name="TA_ZT_JHCT/ZHCRS['+num+']" class="dn_rs"  onkeydown="checkNum1(this)" onchange="sumPrice()" value="0" />��</td>'+
		'</tr><tr  > <td align="right" colspan="5">'+
		'<font color="red">ǩ��С�ƣ�</font><input name="TA_ZT_JHCT/QDXJJE['+num+']" type="text" class="qdxj" value="0" onkeydown="checkNum1(this)" onchange="sumPrice()"/>/Ԫ&nbsp;&nbsp;&nbsp;'+
		'<font color="red">�ָ�С�ƣ�</font><input name="TA_ZT_JHCT/XFXJJE['+num+']" type="text" readonly="readonly" class="xfxj" value="0"/>/Ԫ&nbsp;&nbsp;&nbsp;'+
		'<font color="red">���ƣ�</font>   <input name="TA_ZT_JHCT/HJ['+num+']" type="text"   readonly="readonly" class="gj" value="0"/>/Ԫ'+
		'<input type="hidden" name="TA_ZT_JHCT/JHZT['+num+']" value="Y"/>'+
		'<input type="hidden" name="TA_ZT_JHCT/ZDR['+num+']" value="<%=sd.getString("userno") %>"/>'+
		'</td></tr></table></td></tr></table>');
		var linkage = new Linkage("dataSrc"+d, "<%=request.getContextPath()%>/main/data/Dinning-Roomz.xml");
		linkage.init();
		d+=1;
		//});

}

function delTab(){
	//jQuery("#"+tab).remove();
	var rows=jQuery(".select-dinner").size()-1;
	if(rows>0){
		var idx = parseInt(rows);
		jQuery("#dinnerDiv .select-dinner:eq("+idx+")").remove();
		rows--;
	}
	sumPrice();
}
//delTab(\'dinner'+num+'\')

var hzs=jQuery(".hzszj").val(); //�Ƶ꺬�����
var ts=jQuery(".tszj").val(); //����������
function sumPrice(){
	jQuery("#dinnerDiv table").each(function(i){
		var bf_jg=jQuery("input.bf_jg:eq("+i+")").val();
		var bf_cs=jQuery("input.bf_cs:eq("+i+")").val();
		var bf_rs=jQuery("input.bf_rs:eq("+i+")").val();
		var dn_jg=jQuery("input.dn_jg:eq("+i+")").val();
		var dn_cs=jQuery("input.dn_cs:eq("+i+")").val();
		var dn_rs=jQuery("input.dn_rs:eq("+i+")").val();

		var tmp=0;
		if((bf_cs-hzs)>0){
			tmp=parseFloat(bf_jg*(bf_cs-hzs)*bf_rs+dn_jg*dn_cs*dn_rs);
		}else{
			tmp=parseFloat(bf_jg*bf_cs*bf_rs+dn_jg*dn_cs*dn_rs);
		}
		var tmp=0;
		
		if(""==bf_cs){
			jQuery("input.bf_cs:eq("+i+")").val(0);
		}
		if(""==dn_cs){
			jQuery("input.dn_cs:eq("+i+")").val(0);
		}
		if(""==bf_jg){
			jQuery("input.bf_jg:eq("+i+")").val(0);
		}
		if(""==bf_rs){
			jQuery("input.bf_rs:eq("+i+")").val(0);
		}
		if(""==dn_jg){
			jQuery("input.dn_jg:eq("+i+")").val(0);
		}
		if(""==dn_rs){
			jQuery("input.dn_rs:eq("+i+")").val(0);
		}
		
		tmp=parseFloat(bf_jg*bf_cs*bf_rs+dn_jg*dn_cs*dn_rs);
		jQuery("input.gj:eq("+i+")").val(tmp);
		var qdxj=jQuery("input.qdxj:eq("+i+")").val();
		if(qdxj>tmp){
			jQuery("input.qdxj:eq("+i+")").val(tmp);
			qdxj=tmp;
			}
		jQuery("input.xfxj:eq("+i+")").val(tmp-qdxj);
		
		var qdzj=0;
		var xfzj=0;
		jQuery(".qdxj").each(function(j){
				var qdxj=this.value;
				var xfxj=jQuery(".xfxj:eq("+j+")").val();
				qdzj+=parseFloat(qdxj);
				xfzj+=parseFloat(xfxj);
			});
		jQuery("#qdzj").val(qdzj);
		jQuery("#xfzj").val(xfzj);
		jQuery("#zj").val(qdzj+xfzj);

	});
	
}

function p_editDinner(){
	var num=jQuery(".select-dinner").size();
	if(jQuery("#city"+(num-1)).val()==""){
		alert("�����Ϣ��д����");
		return false;
	}
	
	var bftmp=0;
	jQuery(".bf_cs").each(function(i){
		var bf=jQuery(".bf_cs:eq("+i+")").val();
		bftmp+=parseFloat(bf);
	});
	if((parseFloat(bftmp)+parseFloat(hzs)-parseFloat(ts))>0){
		alert("���������");
		return false;
	}

	document.p_dinnerplan_form.action="ztUpdateDinnerPlan.?";
	document.p_dinnerplan_form.submit();
	reload();
}

function reload(){
	parent.parent.location.reload(); 
	parent.parent.GB_hide(); 
}
</script>
</head>

<body>
<form name="p_dinnerplan_form" method="post">
<input type="hidden" class="hzszj" value="<%=rd.getStringByDI("jhHzs","hzs",0)%>" /> 
<input type="hidden" class="tszj" value="<%=rd.getStringByDI("TA_ZT_GROUPs","DAYS",0)%>"/>
<div id="lable"><span >�����ƻ�</span>&nbsp;&nbsp;&nbsp;<span class="showPointer" onclick="addDinner()">���</span>
			<span class="showPointer" onclick="delTab()">ɾ��</span>
			<%
				String hzs=rd.getStringByDI("jhHzs","hzs",0);
				String ts=rd.getStringByDI("TA_ZT_GROUPs","DAYS",0);
				if(0<Integer.parseInt(hzs)){
			%>
			(<span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="�޸Ĳ����ƻ���Ϣ">
			 <a style="color:red;" href="<%=request.getContextPath()%>/main/ztPlans/hotelPlan/ztReturnInitUpdateHotelPlan.?TA_ZT_GROUP/ID=<%=rd.getStringByDI("dinnerPlanList","TID",0) %>&TA_ZT_JHHOTEL/TID=<%=rd.getStringByDI("dinnerPlanList","TID",0) %>&TA_TDJDXXZJB_ZT/TID=<%=rd.getStringByDI("dinnerPlanList","TID",0) %>&TID=<%=rd.getStringByDI("dinnerPlanList","TID",0) %>&DAYS=<%=ts%>">�Ƶ��Ѻ���� <%=hzs %>��(������<%=ts %>) ����鿴����</a></span>)
			<%} %>
</div>
<div id="bm-table">
<div id="dinnerDiv">
		<%
			int dinnerRows=rd.getTableRowsCount("dinnerPlanList");
			String qdzj=rd.getStringByDI("dinnerPlanList","QDCTZJ",0);
			String xfzj=rd.getStringByDI("dinnerPlanList","XFCTZJ",0);
			String zj=rd.getStringByDI("dinnerPlanList","CTZJ",0);
			for(int i=0;i<dinnerRows;i++){
		%>
		<table class="datas select-dinner" width="98%" id="dinner<%=i %>">
		<input type="hidden" name="TA_ZT_JHCT/TID[<%=i %>]" value="<%=rd.getString("TID")%>"/>
		  <tr>
			<td colspan="4">
			<div id="lable"><span >�����ƻ�</span>&nbsp;&nbsp;&nbsp;<span class="showPointer" onclick="addDinner()">���</span>
			<span class="showPointer" onclick="delTab('dinner<%=i %>')">ɾ��</span></div>
				������
			  <select name="TA_ZT_JHCT/SF[<%=i %>]" id="pro<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="1"></select>
			  <select name="TA_ZT_JHCT/CITYID[<%=i %>]" id="city<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="2"></select>
			  <select name="TA_ZT_JHCT/CT[<%=i %>]" id="ct<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="3"></select>
			  &nbsp;�ò�����:
			  <input type="text" name="TA_ZT_JHCT/YCRQ[<%=i %>]" value="<%=rd.getStringByDI("dinnerPlanList","YCRQ",i) %>" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/>
			</td>
		
		  </tr>
		  <tr  class="none" >
		  <td class="dinnerInfo">
		  			<table class="datas" style="text-align: center">
					<tr id="first-tr">
						<td >��/�� </td>
						<td >�۸�</td>
						<td >����</td>
						<td >����</td>
					</tr>
					<tr>
						<td>���</td>
						<td><input name="TA_ZT_JHCT/ZCJG[<%=i %>]" class="bf_jg" onkeydown="checkNum1(this)" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerPlanList","ZCJG",i) %>" /></td>
						<td><input name="TA_ZT_JHCT/ZCCS[<%=i %>]" class="bf_cs" onkeydown="checkNum()" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerPlanList","ZCCS",i) %>" /></td>
						<td><input name="TA_ZT_JHCT/ZCRS[<%=i %>]" class="bf_rs" onkeydown="checkNum1(this)" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerPlanList","ZCRS",i) %>" />��</td>
		
					</tr>
					<tr>
						<td>����</td>
						<td><input name="TA_ZT_JHCT/ZHCJG[<%=i %>]" class="dn_jg"  onkeydown="checkNum1(this)" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerPlanList","ZHCJG",i) %>" /> </td>
						<td><input name="TA_ZT_JHCT/ZHCCS[<%=i %>]" class="dn_cs"  onkeydown="checkNum()" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerPlanList","ZHCCS",i) %>" /></td>
						<td><input name="TA_ZT_JHCT/ZHCRS[<%=i %>]" class="dn_rs"  onkeydown="checkNum1(this)" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerPlanList","ZHCRS",i) %>" />��</td>
					</tr>
		
			<tr  > 
				<td align="right" colspan="5">
				<font color="red">ǩ��С�ƣ�</font><input name="TA_ZT_JHCT/QDXJJE[<%=i %>]" type="text" class="qdxj" onkeydown="checkNum1(this)" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerPlanList","QDXJJE",i) %>"/>/Ԫ&nbsp;&nbsp;&nbsp;
				<font color="red">�ָ�С�ƣ�</font><input name="TA_ZT_JHCT/XFXJJE[<%=i %>]" type="text" readonly="readonly" class="xfxj"  value="<%=rd.getStringByDI("dinnerPlanList","XFXJJE",i) %>"/>/Ԫ&nbsp;&nbsp;&nbsp;
				<font color="red">���ƣ�</font>   <input name="TA_ZT_JHCT/HJ[<%=i %>]" type="text"   readonly="readonly" class="gj"  value="<%=rd.getStringByDI("dinnerPlanList","HJ",i) %>"/>/Ԫ
				<input type="hidden" name="TA_ZT_JHCT/JHZT[<%=i %>]" value="<%=rd.getStringByDI("dinnerPlanList","JHZT",i) %>"/>
				<input type="hidden" name="TA_ZT_JHCT/ZDR[<%=i %>]" value="<%=rd.getStringByDI("dinnerPlanList","ZDR",i) %>"/>
				</td>
			</tr>
			</table>
			</td>
			</tr>
		</table>
		<%} %>
</div> 

<table class="datas" style="margin-top: 3px;">
  <tr>
	<td><span>���úϼ�</span></td>
  </tr>
  <tr>

	<td align="right">
	  <font color="red">ǩ������ܼƣ�</font>
	  <input type="text" name="TA_TDJDXXZJB_ZT/QDCTZJ" id="qdzj" readonly="readonly" value="<%="".equals(qdzj)?"0":qdzj %>" class="smallInput1"/>/Ԫ&nbsp;&nbsp;&nbsp; 
	  <font color="red">�ָ�����ܼƣ�</font>
	  <input type="text" name="TA_TDJDXXZJB_ZT/XFCTZJ" id="xfzj" readonly="readonly" value="<%="".equals(xfzj)?"0":xfzj %>"  class="smallInput1"/>/Ԫ&nbsp;&nbsp;&nbsp; 
	  <font color="red">�ܼƣ�</font>
	  <input type="text" name="TA_TDJDXXZJB_ZT/CTZJ" id="zj" readonly="readonly" value="<%="".equals(zj)?"0":zj %>" class="smallInput1" />/Ԫ</td>

  </tr>
</table>
</div>
<div align="center" id="last-sub">
	<input type="button" id="button" value="�ύ" onclick="p_editDinner();"/>
	<input type="button" id="button" value="�ر�" onclick="javascript:window.parent.parent.GB_hide();"/>
</div>
</form>
</body>
</html>
<script type="text/javascript">
	<%
	for(int v=0;v<dinnerRows;v++){
	%>
	var linkage = new Linkage("dataSrc<%=v%>", "<%=request.getContextPath()%>/main/data/Dinning-Roomz.xml");
	linkage.init();
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("dinnerPlanList","SF",v)%>",1);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("dinnerPlanList","CITYID",v)%>",2);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("dinnerPlanList","CT",v)%>",3);
	<%}%>
</script>
