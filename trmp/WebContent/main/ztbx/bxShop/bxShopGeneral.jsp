<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript">
function checkNum(){
	if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
		  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
		    event.returnValue=false;
}

function sumPrice(tab,tcblall){
	var rsxj=0;
	var xfexj=0;
	var lcxj=0;
	var jdrsxj=0;
	jQuery("#"+tab+" .rs").each(function(i){
		var gwrs = parseInt(this.value,10);
		if(gwrs>parseInt(jQuery("#"+tab+" .jdrs:eq("+i+")").val(),10)){
			alert("�����������ڽ���������"); 
			jQuery("#"+tab+" .rs:eq("+i+")").val(0); 
			return false;
		}
		rsxj+=parseInt(this.value,10);
		xfexj+=parseInt(jQuery("#"+tab+" .xfe:eq("+i+")").val(),10);
		jdrsxj+=parseInt(jQuery("#"+tab+" .jdrs:eq("+i+")").val(),10);
				
	});
	jQuery("#"+tab+" .rsxj").val(rsxj);
	jQuery("#"+tab+" .xfexj").val(xfexj);
	jQuery("#"+tab+" .jdrsxj").val(jdrsxj);
	lcxj = xfexj/100*<%=rd.getStringByDI("bxLCBL","DMSM2",0)%>;
	jQuery("#"+tab+" .lcxj").val(Math.round(lcxj*100)/100);

	
	jQuery("#"+tab+" .rs").each(function(i){
		var xfe =jQuery("#"+tab+" .xfe:eq("+i+")").val();
		var lc = xfe/100*<%=rd.getStringByDI("bxLCBL","DMSM2",0)%>;
		jQuery("#"+tab+" .lc:eq("+i+")").val(Math.round(lc*100)/100);
	});
	
	
	var jdrshj=0;
	var rshj=0;
	var xfehj=0;
	var lchj=0;
	jQuery(".rsxj").each(function(i){
		rshj+=parseInt(this.value,10);
		xfehj+=parseInt(jQuery(".xfexj:eq("+i+")").val(),10);
		jdrshj+=parseInt(jQuery(".jdrsxj:eq("+i+")").val(),10);
		lchj+=parseFloat(Math.round(jQuery(".lcxj:eq("+i+")").val()*100)/100);

	});
	jQuery("#rshj").val(Math.round(rshj*100)/100);
	jQuery("#xfehj").val(Math.round(xfehj*100)/100);
	jQuery("#lchj").val(Math.round(lchj*100)/100);
	jQuery("#jdrshj").val(Math.round(jdrshj*100)/100);
	
	
	var yjgs=0;
	var tchj=0;
	var tcje = (xfexj-lcxj)*(tcblall/100);//��ɽ�� = (���Ѷ�С�� -����С��)*(��̬��ȡ�ı���% )
	jQuery("#"+tab+" .tcje").val(Math.round(tcje*100)/100);
	
	var tc=0;
	<%
	int newtcRows=rd.getTableRowsCount("bxTCBL");
	
	for(int t=0;t<newtcRows;t++){
	%>
		if(jQuery("#"+tab+" .tcfs").is(":checked")){
			jQuery("#"+tab+" .tc"+<%=t%>).attr("readonly","readonly");
			<%if("N".equals(rd.getString("qprszt"))){%>//���û��ȫ��
				if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==4){
					jQuery("#"+tab+" .tc"+<%=t%>).val(0);
				}else{
					tc=<%=rd.getStringByDI("bxTCBL","dmsm2",t)%>/100*tcje;//��Ա��� = ��ɱ���/100*��ɽ��
					jQuery("#"+tab+" .tc"+<%=t%>).val(Math.round(tc*100)/100);
					if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==3){
						yjgs+=parseFloat(jQuery("#"+tab+" .tc"+<%=t%>).val());
						jQuery("#"+tab+" .yjgs").val(Math.round(yjgs*100)/100);
					}
				}
			<%}else{%>
				if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==3){
					jQuery("#"+tab+" .yjgs").val(0);
					jQuery("#"+tab+" .tc"+<%=t%>).val(0);
				}else{
					tc=<%=rd.getStringByDI("bxTCBL","dmsm2",t)%>/100*tcje;//��Ա��� = ��ɱ���/100*��ɽ��
					jQuery("#"+tab+" .tc"+<%=t%>).val(Math.round(tc*100)/100);
				}
			<%}%>
		}
		if(jQuery("#"+tab+" .tcfs1").is(":checked")){
			jQuery("#"+tab+" .tc"+<%=t%>).removeAttr("readonly");
			if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==3){
				yjgs+=parseFloat(jQuery("#"+tab+" .tc"+<%=t%>).val());
				jQuery("#"+tab+" .yjgs").val(Math.round(yjgs*100)/100);
			}
		}
	<%}%>

	var dytczj=0;
	var sjtczj=0;
	var qptczj=0;
	var gstczj=0;
	jQuery(".shop").each(function(i){//��ȡ��������������� ����
		var shop= "shop";
		<%
		int tcRows=rd.getTableRowsCount("bxTCBL");
		for(int t=0;t<tcRows;t++){
		%>
			if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==1){
				dytczj+=parseFloat(jQuery("#"+shop+i+" .tc"+<%=t%>).val());
				jQuery(".tchj"+<%=t%>).val(Math.round(dytczj*100)/100);//����������
			}
			if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==2){
				sjtczj+=parseFloat(jQuery("#"+shop+i+" .tc"+<%=t%>).val());
				jQuery(".tchj"+<%=t%>).val(Math.round(sjtczj*100)/100);//���˾�����
			}
			<%if("N".equals(rd.getString("qprszt"))){%>
				if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==3){
					gstczj+=parseFloat(jQuery("#"+shop+i+" .tc"+<%=t%>).val());
					jQuery(".tchj"+<%=t%>).val(Math.round(gstczj*100)/100);//�����˾���
					jQuery(".yjgshj").val(Math.round(gstczj*100)/100);
				}
			<%}else{%>
				jQuery(".yjgshj").val(0);
				if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==4){
					qptczj+=parseFloat(jQuery("#"+shop+i+" .tc"+<%=t%>).val());
					jQuery(".tchj"+<%=t%>).val(Math.round(qptczj*100)/100);//���ȫ�����
				}
			<%}%>
			
		<%}%>
		
	});
	var rtfxj=0;
	jQuery("#"+tab+" .rtf").each(function(i){
		rtfxj+=parseInt(this.value);
	});
	rtfxj=rtfxj*jdrsxj;
	jQuery("#"+tab+" .rtfxj").val(rtfxj);

	var rtfhj=0;
	jQuery(".rtfxj").each(function(i){
		rtfhj+=parseInt(this.value);
	});
	jQuery(".rtfhj").val(rtfhj);

	var gwlrhj=0;
	//Ӧ����˾�ֽ�ϼ�
	gwlrhj = parseInt(jQuery(".rtfhj").val())+parseInt(jQuery(".yjgshj").val())+parseInt(jQuery("#lchj").val());
	jQuery(".gwlrhj").val(gwlrhj);
}

</script>
<script type="text/javascript">
function fal(){
	alert("�޷��޸ģ�");
	jQuery("#lchj").focus();
	return false;
}
function doSubShop(){
document.shopBXrequest.submit();
reload();
}

function reload(){
parent.parent.location.reload(); 
parent.parent.GB_hide(); 
}
</script>
<title></title>
</head>
<body >
<form action="ztshopBXrequest." name="shopBXrequest" method="post">
<div id="bm-table">
<div id="carDiv">
<div id="lable"><span >���ﱨ��</span></div>
<%
int shopBxGWD = rd.getTableRowsCount("shopBxGWD");//���ع������Ϣ����
int shopBxRTF = rd.getTableRowsCount("shopBxRTF");//������ͷ������
int bxShopInfo = rd.getTableRowsCount("bxShopInfo");//�ù�����µ���Ʒ��������
if(0 < shopBxGWD){
	out.print("<input type='hidden' name='state' value='Edit'>");
}
for(int i = 0; i < shopBxGWD; i++){
		String SFID = rd.getStringByDI("shopBxGWD","SFID",i);
		String CSID = rd.getStringByDI("shopBxGWD","CSID",i);
		String GWDID = rd.getStringByDI("shopBxGWD","GWDID",i);
		String GWDMC = rd.getStringByDI("shopBxGWD","GWDMC",i);
		String SFXZ = rd.getStringByDI("shopBxGWD","SFXZ",i);
		String TCFS = rd.getStringByDI("shopBxGWD","TCFS",i);
		String DYTC = rd.getStringByDI("shopBxGWD","DYTC",i).equals("")?"0":rd.getStringByDI("shopBxGWD","DYTC",i);
		String SJTC = rd.getStringByDI("shopBxGWD","SJTC",i).equals("")?"0":rd.getStringByDI("shopBxGWD","SJTC",i);
		String QPTC = rd.getStringByDI("shopBxGWD","QPTC",i).equals("")?"0":rd.getStringByDI("shopBxGWD","QPTC",i);
		String GSTC = rd.getStringByDI("shopBxGWD","GSTC",i).equals("")?"0":rd.getStringByDI("shopBxGWD","GSTC",i);
		String YJGSXJ = rd.getStringByDI("shopBxGWD","YJGSXJ",i).equals("")?"0":rd.getStringByDI("shopBxGWD","YJGSXJ",i);
		String RTFXJ = rd.getStringByDI("shopBxGWD","RTFXJ",i).equals("")?"0":rd.getStringByDI("shopBxGWD","RTFXJ",i);
		String DYZB = rd.getStringByDI("shopBxGWD","DYZB",i);
		String SJZB = rd.getStringByDI("shopBxGWD","SJZB",i);
		String QPZB = rd.getStringByDI("shopBxGWD","QPZB",i);
		String RSXJ = rd.getStringByDI("shopBxGWD","RSXJ",i).equals("")?"0":rd.getStringByDI("shopBxGWD","RSXJ",i);
		String JDRSXJ = rd.getStringByDI("shopBxGWD","JDRSXJ",i).equals("")?"0":rd.getStringByDI("shopBxGWD","JDRSXJ",i);
		String XFEXJ = rd.getStringByDI("shopBxGWD","XFEXJ",i).equals("")?"0":rd.getStringByDI("shopBxGWD","XFEXJ",i);
		String GSLCXJ = rd.getStringByDI("shopBxGWD","GSLCXJ",i).equals("")?"0":rd.getStringByDI("shopBxGWD","GSLCXJ",i);
		String TCJE = rd.getStringByDI("shopBxGWD","TCJE",i).equals("")?"0":rd.getStringByDI("shopBxGWD","TCJE",i);
		String TCBLALL = rd.getStringByDI("shopBxGWD","TCBLALL",i);
		String REMARK = rd.getStringByDI("shopBxGWD","REMARK",i);
%>
<table class="datas select-shop" width="98%" id="shop<%=i %>">
  <tr>
	<td colspan="5"><span>���ﱨ��</span></td>
  </tr>
  <tr>
	<td colspan="5">
		����㣺
	  <select name="TA_ZT_BXGW/SFID[<%=i %>]" id="pro<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="1" onclick="fal()"></select>
	  <select name="TA_ZT_BXGW/CSID[<%=i %>]" id="city<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="2" onclick="fal()"></select>
	  <select name="TA_ZT_BXGW/GWDID[<%=i %>]" id="shopid<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="3" onclick="fal()"></select>
	  <!-- ��¼��������� ������ --><input type="hidden" name="" value="" class="shop"/>
	</td>
<!--��ͷ�ѣ�
  	--><%
  	int tmp=0;
  	for(int j = 0; j < shopBxRTF; j++){
  		if(GWDID.equals(rd.getStringByDI("shopBxRTF","GWDID",j))){//����������ͬ
	  		String CSIDRTF = rd.getStringByDI("shopBxRTF","CSID",j);//����ID
	  		String RTF = rd.getStringByDI("shopBxRTF","RTF",j);//��ͷ��
	  		String NAMERTF = rd.getStringByDI("shopBxRTF","NAME",j);//ʡ������
  	%>
  		<input type="hidden" name="TA_ZT_GWBXRTF<%=GWDID %>/RTF[<%=tmp %>]" value="<%=RTF %>" class="smallInput rtf"/>
  		<input type="hidden" name="TA_ZT_GWBXRTF<%=GWDID %>/SFID[<%=tmp %>]" value="<%=CSIDRTF %>" />
  		<input type="hidden" name="TA_ZT_GWBXRTF<%=GWDID %>/SFMC[<%=tmp %>]" value="<%=NAMERTF %>" />
  	<%
  		tmp++;
	  	}
	}
  	%>

  </tr>

  	

  <tr >
  	<td colspan="5">
  		<table class="datas" style="text-align: center" >
  			<tr id="first-tr">
  				<td>��Ʒ����</td>
  				<td>��������</td>
  				<td>��������</td>
  				<td>���Ѷ�</td>
  				<td>����</td>
  			</tr>
  			<%
  			int tmp1=0;
  			for(int g=0;g< bxShopInfo;g++){
  				String JDRS=rd.getStringByDI("bxShopInfo","JDRS",g).equals("")?"0":rd.getStringByDI("bxShopInfo","JDRS",g);
  				String RS=rd.getStringByDI("bxShopInfo","RS",g).equals("")?"0":rd.getStringByDI("bxShopInfo","RS",g);
				String XFE=rd.getStringByDI("bxShopInfo","XFE",g).equals("")?"0":rd.getStringByDI("bxShopInfo","XFE",g);
				String LC=rd.getStringByDI("bxShopInfo","LC",g).equals("")?"0":rd.getStringByDI("bxShopInfo","LC",g);
  				if(GWDID.equals(rd.getStringByDI("bxShopInfo","shop_point_id",g))){
  				%>
  				<tr>
  					<td><%=rd.getStringByDI("bxShopInfo","goods_name",g) %>
  						<input value="<%=rd.getStringByDI("bxShopInfo","goods_id",g) %>" name="TA_ZT_BXGWMX<%=GWDID %>/GOODSID[<%=tmp1 %>]" type="hidden"/>
  					</td>
  					<td><input value="<%=JDRS %>" onchange="sumPrice('shop<%=i %>','<%=TCBLALL %>')" class="smallInput jdrs" name="TA_ZT_BXGWMX<%=GWDID %>/JDRS[<%=tmp1 %>]" onkeydown="checkNum()"/>/��</td>
  					<td><input value="<%=RS %>" onchange="sumPrice('shop<%=i %>','<%=TCBLALL %>')" class="smallInput rs" name="TA_ZT_BXGWMX<%=GWDID %>/RS[<%=tmp1 %>]" onkeydown="checkNum()"/>/��</td>
  					<td><input value="<%=XFE %>" onchange="sumPrice('shop<%=i %>','<%=TCBLALL %>')" class="smallInput xfe" name="TA_ZT_BXGWMX<%=GWDID %>/XFE[<%=tmp1 %>]" onkeydown="checkNum()"/>/Ԫ</td>
  					<td><input value="<%=LC %>" onchange="sumPrice('shop<%=i %>','<%=TCBLALL %>')" class="smallInput lc" name="TA_ZT_BXGWMX<%=GWDID %>/LC[<%=tmp1 %>]" onkeydown="checkNum()" readonly="readonly"/>/Ԫ</td>
  				</tr>
  			<%
  				tmp1++;
  				}
			}
  			%>
  			  <tr>
	<td align="right" colspan="5">
	  <font color="red">��������С�ƣ�</font>
	  <input type="text"  value="<%=JDRSXJ %>" name="TA_ZT_BXGW<%=GWDID %>/JDRSXJ[<%=i %>]"  class="smallInput jdrsxj" readonly="readonly" />/��&nbsp;&nbsp;&nbsp; 
	  <font color="red">��������С�ƣ�</font>
	  <input type="text"  value="<%=RSXJ %>" name="TA_ZT_BXGW<%=GWDID %>/RSXJ[<%=i %>]"  class="smallInput rsxj" readonly="readonly" />/��&nbsp;&nbsp;&nbsp; 
	  <font color="red">���Ѷ�С�ƣ�</font>
	  <input type="text"  value="<%=XFEXJ %>" name="TA_ZT_BXGW<%=GWDID %>/XFEXJ[<%=i %>]" class="smallInput xfexj" readonly="readonly" />/Ԫ&nbsp;&nbsp;&nbsp; 
	  <font color="red">��˾���棺</font>
	  <input type="text"  value="<%=GSLCXJ %>" name="TA_ZT_BXGW<%=GWDID %>/GSLCXJ[<%=i %>]" class="smallInput lcxj" readonly="readonly"/>/Ԫ
	 <!-- ��ɽ�������� ���Ѷ�С�� ȥ�� 10% �� 50%--> <input type="hidden" name="TA_ZT_BXGW<%=GWDID %>/TCJEZJ[<%=i %>]" value="<%=TCJE %>" class="tcje"/>
	 <!-- Ӧ����˾С��--> <input type="hidden" name="TA_ZT_BXGW<%=GWDID %>/YJGSXJ[<%=i %>]" value="<%=YJGSXJ %>" class="yjgs"/>
	 <!-- ��ͷ��С�� --> <input type="hidden" name="TA_ZT_BXGW<%=GWDID %>/RTFXJ[<%=i %>]" value="<%=RTFXJ %>" class="rtfxj"/>
	 
	  </td>
  </tr>
  		</table>
  	</td>
  </tr>
  <tr>
  <td colspan="5">
  	��ע��<input name="TA_ZT_BXGW<%=GWDID %>/REMARK[<%=i %>]" value="<%=REMARK %>" size="88"/>
  </td>
  </tr>
  <tr>
  	<td colspan="4">
  			<table class="datas "  style="text-align: center">
  			<tr>
  			<td>
  				��ɷ�ʽ��<input type="radio" name="TA_ZT_BXGW<%=GWDID %>/TCFS[<%=i %>]" value="1" class="tcfs" <%if("1".equals(TCFS)||null!=TCFS){ %>checked="checked"<%} %> onclick="sumPrice('shop<%=i %>','<%=TCBLALL %>')"/>�������<input type="radio" name="TA_ZT_BXGW<%=GWDID %>/TCFS[<%=i %>]" class="tcfs1" value="2" <%if("2".equals(TCFS)){ %>checked="checked"<%} %> onclick="sumPrice('shop<%=i %>','<%=TCBLALL %>')"/>�̶������� 
  			</td>
  			</tr>
			<tr id="first-tr">
				<td>�����Ա</td>
				<td>��ɽ��</td>
			</tr>
			
			<%
			int d_tcRows=rd.getTableRowsCount("bxTCBL");
			for(int t=0;t<d_tcRows;t++){
				String DMZ = rd.getString("bxTCBL","DMZ",t);
				String DMSM = rd.getStringByDI("bxTCBL","DMSM1",t);
				String TCBL = rd.getStringByDI("bxTCBL","DMSM2",t);
				%>
				<tr>
				<td><%=DMSM %>(<%=TCBL %>%)</td>
				<td>
				
				<input type="hidden" name="bxTCBL<%=GWDID %>/DMZ[<%=t %>]" value="<%=rd.getStringByDI("bxTCBL","DMZ",t) %>" />
				<input type="hidden" name="bxTCBL<%=GWDID %>/TCMC[<%=t %>]" value="<%=rd.getStringByDI("bxTCBL","DMSM1",t) %>" />
				<input type="hidden" name="bxTCBL<%=GWDID %>/TCBL[<%=t %>]" value="<%=rd.getStringByDI("bxTCBL","DMSM2",t) %>" />
				<input type="text" name="bxTCBL<%=GWDID %>/TCJE[<%=t %>]"  value="<%if("����".equals(DMSM)){out.print(DYTC);}else if("˾��".equals(DMSM)){out.print(SJTC);}else if("ȫ��".equals(DMSM)){out.print(QPTC);}else if("��˾".equals(DMSM)){out.print(GSTC);} %>" class="tc<%=t %>" onkeydown="checkNum()" onclick="sumPrice('shop<%=i %>','<%=TCBLALL %>')"/>/Ԫ
				</td>
			</tr>
			<%
			}
			%>
		</table>
  	</td>
  </tr>
</table>
<%} %>
<tr>
	<table class="datas" style="text-align: center">
		<br>
		<tr>
  	<td colspan="4">
  			<table class="datas "  style="text-align: center">
  			<tr>
  			<td colspan="4"><span>���úϼ�</span></td>
  			</tr>
			<tr id="first-tr">
				<td>�����Ա</td>
				<td>��ɽ��</td>
			</tr>
			<%
			int d_tcRows=rd.getTableRowsCount("bxTCBL");
			for(int t=0;t<d_tcRows;t++){
				String DMZ = rd.getStringByDI("bxTCBL","DMZ",t);
				String DMSM = rd.getStringByDI("bxTCBL","DMSM1",t);
				int HZRows = rd.getTableRowsCount("bxInitCYHZ");
				if(0 >= HZRows){
			%>
			<tr >
				<td><%=DMSM %></td>
				<td>
				<input type="hidden" name="TA_TDBXTCCYHZ_ZT/CYID[<%=t %>]" value="<%=DMZ %>"/>
				<input type="hidden" name="TA_TDBXTCCYHZ_ZT/CYMC[<%=t %>]" value="<%=DMSM %>" />
				<input type="text"  readonly="readonly" name="TA_TDBXTCCYHZ_ZT/CYJEHZ[<%=t %>]" value="0" class="tchj<%=t %>" onkeydown="checkNum()"/>/Ԫ
				</td>
			</tr>		
			<%
						}else{
						for(int a = 0; a < HZRows; a++){
							String CYMC = rd.getStringByDI("bxInitCYHZ","CYMC",a);
							String CYJEHZ = rd.getStringByDI("bxInitCYHZ","CYJEHZ",a);
							if(CYMC.equals(DMSM)){
			%>
			<tr >
				<td><%=DMSM %></td>
				<td>
				<input type="hidden" name="TA_TDBXTCCYHZ_ZT/CYID[<%=t %>]" value="<%=DMZ %>"/>
				<input type="hidden" name="TA_TDBXTCCYHZ_ZT/CYMC[<%=t %>]" value="<%=DMSM %>" />
				<input type="text" readonly="readonly" name="TA_TDBXTCCYHZ_ZT/CYJEHZ[<%=t %>]" value="<%=CYJEHZ %>" class="tchj<%=t %>" onkeydown="checkNum()"/>/Ԫ
				</td>
			</tr>
			<%
						}
					}
				} 
			}
			%>
		</table>
  	</td>
  </tr>
		<tr>
		<td align="right" colspan="5">
		 <font color="red">���������ϼƣ�</font>
		  <input type="text" id="jdrshj" name="TA_TDBXZJXXB_ZT/GWJDRSHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","GWJDRSHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","GWJDRSHJ",0) %>" class="smallInput" readonly="readonly"/>/��&nbsp; 
		  <font color="red">���������ϼƣ�</font>
		  <input type="text" id="rshj" name="TA_TDBXZJXXB_ZT/GWRSHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","GWRSHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","GWRSHJ",0) %>" class="smallInput" readonly="readonly"/>/��&nbsp; 
		  <font color="red">���Ѷ�ϼƣ�</font>
		  <input type="text" id="xfehj" name="TA_TDBXZJXXB_ZT/GWXFEHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","GWXFEHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","GWXFEHJ",0) %>"  class="smallInput" readonly="readonly"/>/Ԫ&nbsp; 
		 <font color="red">��˾����ϼƣ�</font>
		  <input type="text" id="lchj" name="TA_TDBXZJXXB_ZT/GWGSLCHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","GWGSLCHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","GWGSLCHJ",0) %>" class="smallInput" readonly="readonly"/>/Ԫ&nbsp;
		  	 <br><font color="red">��ͷ�Ѻϼƣ�</font>
		  	<!-- ��ͷ�Ѻϼ�--><input type="text" name="TA_TDBXZJXXB_ZT/GWRTFHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","GWRTFHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","GWRTFHJ",0) %>" class="smallInput rtfhj"/>/Ԫ&nbsp;
		 <font color="red">Ӧ����˾�ֽ�ϼƣ�</font>
		  <!-- Ӧ����˾�ϼ�  ��ɺϼ�����Ӧ����˾��Ǯ--><input type="text" name="TA_TDBXZJXXB_ZT/GWYJGSHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","GWYJGSHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","GWYJGSHJ",0) %>" class="smallInput yjgshj"/>/Ԫ&nbsp;
		   <font color="red">���﹫˾����ϼƣ�</font>
		  <!-- Ӧ����˾�ܼƼ� ��ɺϼ�����Ӧ����˾��Ǯ+��ͷ�Ѻϼ�--><input type="text" name="TA_TDBXZJXXB_ZT/GWLRHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","GWLRHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","GWLRHJ",0) %>" class="smallInput gwlrhj"/>/Ԫ&nbsp;
		  </td>
		</tr>
	</table>
</tr>
</div>

</div>
<div align="center" id="last-sub">
<%if(!"view".equals(rd.getString("flag"))){ %>
	<input type="button" id="button" value="�ύ" onclick="doSubShop();"/>
	<%} %>
	<input type="button" id="button" value="����" onclick="reload();"/>
</div>
<input type="hidden" value="<%=sd.getString("userno") %>" name="BXR" />
<input type="hidden" value="<%=rd.getString("qprszt") %>" name="QPRSZT" />
<input type="hidden" value="<%=rd.getString("TA_ZT_BXGW","TID",0) %>" name="TA_ZT_BXGW/TID" />
</form>
</body>
</html>
<script type="text/javascript">
<%
	for(int v = 0; v < shopBxGWD; v++){
%>
	var linkage = new Linkage("dataSrc<%=v%>", "<%=request.getContextPath()%>/main/data/Shopz.xml");
	linkage.init();
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("shopBxGWD","SFID",v)%>",1);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("shopBxGWD","CSID",v)%>",2);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("shopBxGWD","GWDID",v)%>",3);
<%}%>
</script>