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
			alert("购物人数大于进店人数！"); 
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
	var tcje = (xfexj-lcxj)*(tcblall/100);//提成金额 = (消费额小计 -留存小计)*(动态获取的比列% )
	jQuery("#"+tab+" .tcje").val(Math.round(tcje*100)/100);
	
	var tc=0;
	<%
	int newtcRows=rd.getTableRowsCount("bxTCBL");
	
	for(int t=0;t<newtcRows;t++){
	%>
		if(jQuery("#"+tab+" .tcfs").is(":checked")){
			jQuery("#"+tab+" .tc"+<%=t%>).attr("readonly","readonly");
			<%if("N".equals(rd.getString("qprszt"))){%>//如果没有全陪
				if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==4){
					jQuery("#"+tab+" .tc"+<%=t%>).val(0);
				}else{
					tc=<%=rd.getStringByDI("bxTCBL","dmsm2",t)%>/100*tcje;//成员提成 = 提成比例/100*提成金额
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
					tc=<%=rd.getStringByDI("bxTCBL","dmsm2",t)%>/100*tcje;//成员提成 = 提成比例/100*提成金额
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
	jQuery(".shop").each(function(i){//获取购物点隐藏域数量 遍历
		var shop= "shop";
		<%
		int tcRows=rd.getTableRowsCount("bxTCBL");
		for(int t=0;t<tcRows;t++){
		%>
			if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==1){
				dytczj+=parseFloat(jQuery("#"+shop+i+" .tc"+<%=t%>).val());
				jQuery(".tchj"+<%=t%>).val(Math.round(dytczj*100)/100);//输出导游提成
			}
			if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==2){
				sjtczj+=parseFloat(jQuery("#"+shop+i+" .tc"+<%=t%>).val());
				jQuery(".tchj"+<%=t%>).val(Math.round(sjtczj*100)/100);//输出司机提成
			}
			<%if("N".equals(rd.getString("qprszt"))){%>
				if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==3){
					gstczj+=parseFloat(jQuery("#"+shop+i+" .tc"+<%=t%>).val());
					jQuery(".tchj"+<%=t%>).val(Math.round(gstczj*100)/100);//输出公司提成
					jQuery(".yjgshj").val(Math.round(gstczj*100)/100);
				}
			<%}else{%>
				jQuery(".yjgshj").val(0);
				if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==4){
					qptczj+=parseFloat(jQuery("#"+shop+i+" .tc"+<%=t%>).val());
					jQuery(".tchj"+<%=t%>).val(Math.round(qptczj*100)/100);//输出全陪提成
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
	//应交公司现金合计
	gwlrhj = parseInt(jQuery(".rtfhj").val())+parseInt(jQuery(".yjgshj").val())+parseInt(jQuery("#lchj").val());
	jQuery(".gwlrhj").val(gwlrhj);
}

</script>
<script type="text/javascript">
function fal(){
	alert("无法修改！");
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
<div id="lable"><span >购物报销</span></div>
<%
int shopBxGWD = rd.getTableRowsCount("shopBxGWD");//加载购物点信息行数
int shopBxRTF = rd.getTableRowsCount("shopBxRTF");//加载人头费行数
int bxShopInfo = rd.getTableRowsCount("bxShopInfo");//该购物点下的商品名称行数
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
	<td colspan="5"><span>购物报销</span></td>
  </tr>
  <tr>
	<td colspan="5">
		购物点：
	  <select name="TA_ZT_BXGW/SFID[<%=i %>]" id="pro<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="1" onclick="fal()"></select>
	  <select name="TA_ZT_BXGW/CSID[<%=i %>]" id="city<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="2" onclick="fal()"></select>
	  <select name="TA_ZT_BXGW/GWDID[<%=i %>]" id="shopid<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="3" onclick="fal()"></select>
	  <!-- 记录购物点数量 隐藏域 --><input type="hidden" name="" value="" class="shop"/>
	</td>
<!--人头费：
  	--><%
  	int tmp=0;
  	for(int j = 0; j < shopBxRTF; j++){
  		if(GWDID.equals(rd.getStringByDI("shopBxRTF","GWDID",j))){//如果购物点相同
	  		String CSIDRTF = rd.getStringByDI("shopBxRTF","CSID",j);//城市ID
	  		String RTF = rd.getStringByDI("shopBxRTF","RTF",j);//人头费
	  		String NAMERTF = rd.getStringByDI("shopBxRTF","NAME",j);//省份名称
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
  				<td>商品名称</td>
  				<td>进店人数</td>
  				<td>购物人数</td>
  				<td>消费额</td>
  				<td>留存</td>
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
  					<td><input value="<%=JDRS %>" onchange="sumPrice('shop<%=i %>','<%=TCBLALL %>')" class="smallInput jdrs" name="TA_ZT_BXGWMX<%=GWDID %>/JDRS[<%=tmp1 %>]" onkeydown="checkNum()"/>/人</td>
  					<td><input value="<%=RS %>" onchange="sumPrice('shop<%=i %>','<%=TCBLALL %>')" class="smallInput rs" name="TA_ZT_BXGWMX<%=GWDID %>/RS[<%=tmp1 %>]" onkeydown="checkNum()"/>/人</td>
  					<td><input value="<%=XFE %>" onchange="sumPrice('shop<%=i %>','<%=TCBLALL %>')" class="smallInput xfe" name="TA_ZT_BXGWMX<%=GWDID %>/XFE[<%=tmp1 %>]" onkeydown="checkNum()"/>/元</td>
  					<td><input value="<%=LC %>" onchange="sumPrice('shop<%=i %>','<%=TCBLALL %>')" class="smallInput lc" name="TA_ZT_BXGWMX<%=GWDID %>/LC[<%=tmp1 %>]" onkeydown="checkNum()" readonly="readonly"/>/元</td>
  				</tr>
  			<%
  				tmp1++;
  				}
			}
  			%>
  			  <tr>
	<td align="right" colspan="5">
	  <font color="red">进店人数小计：</font>
	  <input type="text"  value="<%=JDRSXJ %>" name="TA_ZT_BXGW<%=GWDID %>/JDRSXJ[<%=i %>]"  class="smallInput jdrsxj" readonly="readonly" />/人&nbsp;&nbsp;&nbsp; 
	  <font color="red">购物人数小计：</font>
	  <input type="text"  value="<%=RSXJ %>" name="TA_ZT_BXGW<%=GWDID %>/RSXJ[<%=i %>]"  class="smallInput rsxj" readonly="readonly" />/人&nbsp;&nbsp;&nbsp; 
	  <font color="red">消费额小计：</font>
	  <input type="text"  value="<%=XFEXJ %>" name="TA_ZT_BXGW<%=GWDID %>/XFEXJ[<%=i %>]" class="smallInput xfexj" readonly="readonly" />/元&nbsp;&nbsp;&nbsp; 
	  <font color="red">公司留存：</font>
	  <input type="text"  value="<%=GSLCXJ %>" name="TA_ZT_BXGW<%=GWDID %>/GSLCXJ[<%=i %>]" class="smallInput lcxj" readonly="readonly"/>/元
	 <!-- 提成金额隐藏域 消费额小计 去除 10% 的 50%--> <input type="hidden" name="TA_ZT_BXGW<%=GWDID %>/TCJEZJ[<%=i %>]" value="<%=TCJE %>" class="tcje"/>
	 <!-- 应交公司小计--> <input type="hidden" name="TA_ZT_BXGW<%=GWDID %>/YJGSXJ[<%=i %>]" value="<%=YJGSXJ %>" class="yjgs"/>
	 <!-- 人头费小计 --> <input type="hidden" name="TA_ZT_BXGW<%=GWDID %>/RTFXJ[<%=i %>]" value="<%=RTFXJ %>" class="rtfxj"/>
	 
	  </td>
  </tr>
  		</table>
  	</td>
  </tr>
  <tr>
  <td colspan="5">
  	备注：<input name="TA_ZT_BXGW<%=GWDID %>/REMARK[<%=i %>]" value="<%=REMARK %>" size="88"/>
  </td>
  </tr>
  <tr>
  	<td colspan="4">
  			<table class="datas "  style="text-align: center">
  			<tr>
  			<td>
  				提成方式：<input type="radio" name="TA_ZT_BXGW<%=GWDID %>/TCFS[<%=i %>]" value="1" class="tcfs" <%if("1".equals(TCFS)||null!=TCFS){ %>checked="checked"<%} %> onclick="sumPrice('shop<%=i %>','<%=TCBLALL %>')"/>比例提成<input type="radio" name="TA_ZT_BXGW<%=GWDID %>/TCFS[<%=i %>]" class="tcfs1" value="2" <%if("2".equals(TCFS)){ %>checked="checked"<%} %> onclick="sumPrice('shop<%=i %>','<%=TCBLALL %>')"/>固定金额提成 
  			</td>
  			</tr>
			<tr id="first-tr">
				<td>提成人员</td>
				<td>提成金额</td>
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
				<input type="text" name="bxTCBL<%=GWDID %>/TCJE[<%=t %>]"  value="<%if("导游".equals(DMSM)){out.print(DYTC);}else if("司机".equals(DMSM)){out.print(SJTC);}else if("全陪".equals(DMSM)){out.print(QPTC);}else if("公司".equals(DMSM)){out.print(GSTC);} %>" class="tc<%=t %>" onkeydown="checkNum()" onclick="sumPrice('shop<%=i %>','<%=TCBLALL %>')"/>/元
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
  			<td colspan="4"><span>费用合计</span></td>
  			</tr>
			<tr id="first-tr">
				<td>提成人员</td>
				<td>提成金额</td>
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
				<input type="text"  readonly="readonly" name="TA_TDBXTCCYHZ_ZT/CYJEHZ[<%=t %>]" value="0" class="tchj<%=t %>" onkeydown="checkNum()"/>/元
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
				<input type="text" readonly="readonly" name="TA_TDBXTCCYHZ_ZT/CYJEHZ[<%=t %>]" value="<%=CYJEHZ %>" class="tchj<%=t %>" onkeydown="checkNum()"/>/元
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
		 <font color="red">进店人数合计：</font>
		  <input type="text" id="jdrshj" name="TA_TDBXZJXXB_ZT/GWJDRSHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","GWJDRSHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","GWJDRSHJ",0) %>" class="smallInput" readonly="readonly"/>/人&nbsp; 
		  <font color="red">购物人数合计：</font>
		  <input type="text" id="rshj" name="TA_TDBXZJXXB_ZT/GWRSHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","GWRSHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","GWRSHJ",0) %>" class="smallInput" readonly="readonly"/>/人&nbsp; 
		  <font color="red">消费额合计：</font>
		  <input type="text" id="xfehj" name="TA_TDBXZJXXB_ZT/GWXFEHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","GWXFEHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","GWXFEHJ",0) %>"  class="smallInput" readonly="readonly"/>/元&nbsp; 
		 <font color="red">公司留存合计：</font>
		  <input type="text" id="lchj" name="TA_TDBXZJXXB_ZT/GWGSLCHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","GWGSLCHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","GWGSLCHJ",0) %>" class="smallInput" readonly="readonly"/>/元&nbsp;
		  	 <br><font color="red">人头费合计：</font>
		  	<!-- 人头费合计--><input type="text" name="TA_TDBXZJXXB_ZT/GWRTFHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","GWRTFHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","GWRTFHJ",0) %>" class="smallInput rtfhj"/>/元&nbsp;
		 <font color="red">应交公司现金合计：</font>
		  <!-- 应交公司合计  提成合计里面应交公司的钱--><input type="text" name="TA_TDBXZJXXB_ZT/GWYJGSHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","GWYJGSHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","GWYJGSHJ",0) %>" class="smallInput yjgshj"/>/元&nbsp;
		   <font color="red">购物公司利润合计：</font>
		  <!-- 应交公司总计计 提成合计里面应交公司的钱+人头费合计--><input type="text" name="TA_TDBXZJXXB_ZT/GWLRHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","GWLRHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","GWLRHJ",0) %>" class="smallInput gwlrhj"/>/元&nbsp;
		  </td>
		</tr>
	</table>
</tr>
</div>

</div>
<div align="center" id="last-sub">
<%if(!"view".equals(rd.getString("flag"))){ %>
	<input type="button" id="button" value="提交" onclick="doSubShop();"/>
	<%} %>
	<input type="button" id="button" value="返回" onclick="reload();"/>
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