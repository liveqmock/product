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
<title>酒店报销</title>
<script type="text/javascript">

	function checkNum(){
		if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
			  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
			    event.returnValue=false;
	}
	function checkBoxNum(obj){
		var cnum=jQuery(".select-hotel").size();
		var hz=document.getElementsByName("TA_ZT_BXHOTEL/SFHZ["+(cnum-1)+"]");
		for(var i=0;i<hz.length;i++){
			if(hz[i]==obj && hz[i].checked){
				hz[i].checked=true;
			}else{
				hz[i].checked=false;
			}
		}
	}
	
jQuery(document).ready(function(){
	var d=jQuery(".select-hotel").size();
	jQuery(".showPointer").hover(function(){
		jQuery(this).css({cursor:'pointer'});
	});
	jQuery("#addHotel").click(function(){
		 var num=jQuery(".select-hotel").size();
		 if(jQuery("#id_hotel"+(num-1)).val()==""){
			alert("请填写完整后再添加");
			return false;
			 }
		 jQuery("#hotelDiv").append('<table class="datas select-hotel" width="95%" id="hotel'+num+'">'+
					'<tr><td colspan="4"><span>酒店报销</span> <span onclick="delTab(\'hotel'+num+'\')" class="showPointer">删除</span></td></tr>'+
					'<input type="hidden" name="TA_ZT_BXHTOLE/TID['+num+']" value="<%=rd.getString("TID")%>">'+
					'<input type="hidden" name="TA_ZT_BXHOTEL/BXR['+num+']" value="<%=sd.getString("userno")%>">'+
				'<tr><td colspan="4">请选择酒店：'+
				   '<select name="TA_ZT_BXHOTEL/SF['+num+']" id="pro'+num+'" USEDATA="dataSrc'+d+'" SUBCLASS="1"></select> '+
		  		   '<select name="TA_ZT_BXHOTEL/CITY['+num+']" id="city'+num+'" USEDATA="dataSrc'+d+'" SUBCLASS="2"></select> '+
		           '<select name="TA_ZT_BXHOTEL/XQ['+num+']" id="area'+num+'" USEDATA="dataSrc'+d+'" SUBCLASS="3"></select> '+
		           '<select name="TA_ZT_BXHOTEL/XJ['+num+']" id="level'+num+'" USEDATA="dataSrc'+d+'" SUBCLASS="4"></select> '+
		           '<select name="TA_ZT_BXHOTEL/JDID['+num+']" class="hotelid" id="id_hotel'+num+'" USEDATA="dataSrc'+d+'" SUBCLASS="5" onchange="getHotelInfo(this.value,\'hotelInfo'+num+'\')"></select><br/>'+
		           '&nbsp;&nbsp;&nbsp;<font color="red">含桌早</font><input type="checkBox" value="1" name="TA_ZT_BXHOTEL/SFHZ['+num+']" onclick="checkBoxNum(this);"/>'+ 
				   '&nbsp; <font color="red">含自助早</font><input type="checkBox" value="2" name="TA_ZT_BXHOTEL/SFHZ['+num+']" onclick="checkBoxNum(this);"/>'+ 
				   '&nbsp; <font color="red">含打包早</font><input type="checkBox" value="3" name="TA_ZT_BXHOTEL/SFHZ['+num+']" onclick="checkBoxNum(this);"/><br/>'+
				   '</td></tr>'+
				   '<tr ><td colspan="4" id="hotelInfo'+num+'">'+
					'</td></tr><tr><td  colspan="4">'+
					'入住时间：<input type="text" name="TA_ZT_BXHOTEL/RZ_TIME['+num+']" onFocus="WdatePicker({startDate:\'%y-%M-%d %H:%m:%s\',dateFmt:\'yyyy-MM-dd HH:mm:ss\',alwaysUseStartDate:true});"/>&nbsp;'+
					'入住天数：<input type="text" name="TA_ZT_BXHOTEL/TS['+num+']" onkeydown="checkNum()" class="ts" onchange="SumPrice()"/>&nbsp;<br/>'+
					'司陪住宿：<input type="text" name="TA_ZT_BXHOTEL/SFZSF['+num+']" onkeydown="checkNum()"/>&nbsp;'+
					'备注：<textarea rows="" cols="50" name="TA_ZT_BXHOTEL/BZ['+num+']"></textarea></td></tr>'+
				   '<tr><td align="right" colspan="4">'+
			'<font color="red">签单小计金额：</font><input value="0" type="text" name="TA_ZT_BXHOTEL/QDXJ['+num+']" class="qdxj" onchange="sumRs()" onkeydown="checkNum()"/>/元&nbsp;&nbsp;&nbsp;'+
			'<font color="red">现付小计金额：</font><input value="0" type="text" name="TA_ZT_BXHOTEL/XFXJ['+num+']"  readonly="readonly" class="xfxj"/>/元&nbsp;&nbsp;&nbsp;'+
			'<font color="red">共计：</font><input type="text" value="0" name="TA_ZT_BXHOTEL/HJ['+num+']" readonly="readonly" class="gj" />/元</td></tr></table>');
			var linkage = new Linkage("dataSrc"+d, "<%=request.getContextPath()%>/main/data/Hotelz.xml");
			linkage.init();
			d+=1;
			//alert(jQuery("#hotelDiv").html());
	});
});

	function delTab(tab){
		jQuery("#"+tab).remove();
		SumPrice();
		sumRs();
		//alert(jQuery("#hotelDiv").html());
	}

	function SumPrice(){
		 var zj=0;
		 var allhj=0;
		 var xiaoji=0;
		jQuery("table.select-hotel").each(function(j){
			var tmp=0;
			var alltmp=0;
			var tabName=this.id;
			var ts=jQuery(".ts:eq("+j+")").val();
			var spzsf=jQuery(".spzsf:eq("+j+")").val();
			jQuery("#"+tabName+" input.hotelPrice").each(function(i){
				var num=jQuery("#"+tabName+" input.hotelNum:eq("+i+")").val();
				if(""!=num){
					xiaoji=this.value*num;
				}
				if(""==num){
					jQuery("#"+tabName+" input.hotelNum:eq("+i+")").val(0);
				}
				jQuery("#"+tabName+" input.xiaoji:eq("+i+")").val(xiaoji);

				tmp+=xiaoji;
				if(""!=spzsf && ""!=ts){
					alltmp=parseFloat(tmp*ts)+parseFloat(spzsf*ts);
				}
				if(""!=ts && ""==spzsf){
					alltmp=tmp*ts;
				}
				if(""!=spzsf && ""==ts){
					alltmp=parseFloat(tmp)+parseFloat(spzsf);
				}
				if(""==spzsf && ""==ts){
					alltmp=tmp;
				}
				
				zj+=xiaoji;
				
			jQuery("#"+tabName+" input.gj").val(alltmp);
			jQuery("#"+tabName+" input.qdxj").attr("readonly","");
		});
		});
			sumRs();
	}
	function sumRs(){
		 var qdzj=0;
		 var xfzj=0;
		jQuery(".qdxj").each(function(i){
			var qd=jQuery(".qdxj:eq("+i+")").val();
			var gj=jQuery(".gj:eq("+i+")").val();
			if(parseInt(qd)>parseInt(gj)){
				jQuery(".qdxj:eq("+i+")").val(gj);
				qd=gj;
				}
			jQuery(".xfxj:eq("+i+")").val(gj-qd);
			qdzj+=parseInt(qd);
			xfzj+=(gj-qd);
			});
			jQuery("#qiandan").val(parseInt(qdzj));
			jQuery("#xianfu").val(parseInt(xfzj));
			jQuery("#zongji").val(parseInt(qdzj+xfzj));
	}
	
	function getHotelInfo(id,tdName){
		if(id!=""){
			var last=jQuery(".hotelid").size();
			if(last>1){
				for(var a=0;a<last;a++){
					var hotel=jQuery(".hotelid:eq("+a+")").val();
					for(var b=0;b<last;b++){
						var hotel2=jQuery(".hotelid:eq("+b+")").val();
						if(a!=b){
							if(hotel==hotel2){
								alert("不能重复选择");
								return false;
							}
						}
					}
				}
			}
			
			//保存数据返回值更改html内容
			var obj=jQuery.ajax({url:"ztGetHotelBxInfo.?id="+id,async:true,dataType:"HTML",cache:false,success:function(){
				jQuery("#"+tdName).parents("tr").attr("style","");
				jQuery("#"+tdName).html(obj.responseText);
				}});
		}
	}
	
	var ts=<%=rd.getString("TS")%>; //旅游总天数
	function p_editHotelBx(){
		var num=jQuery(".select-hotel").size();
		 if(jQuery("#id_hotel"+(num-1)).val()==""){
				alert("酒店信息请填写完整");
				return false;
		 }

		 var tmp=0;
		 jQuery(".ts").each(function(i){
			 var ts=jQuery(".ts:eq("+i+")").val();
			 tmp+=parseInt(ts);
		 });
		 if(tmp>ts){
			 alert("住宿天数超出");
			 return false;
		 }
		 
		document.p_hotelbx_form.action="zteditDjHotelBx.?";
		document.p_hotelbx_form.submit();
		reload();
	}
	function reload(){
		parent.parent.location.reload(); 
		parent.parent.GB_hide(); 
	}
</script>
</head>

<body>
<form  name="p_hotelbx_form" method="post">
<input type="hidden" name="userno" value="1"></input>
<div id="lable"><span >酒店报销</span></div>
<input type="hidden" name="TA_ZT_BXHOTEL/TID" value="<%=rd.getString("TID")%>">
<div id="bm-table">
	<div id="hotelDiv">
	<%
		int hotelBxRows=rd.getTableRowsCount("hotelBxList");
		String qdzj=rd.getStringByDI("bxjdJDXXZJB","QDZJ",0);
		String xfzj=rd.getStringByDI("bxjdJDXXZJB","XFZJ",0);
		String zj=rd.getStringByDI("bxjdJDXXZJB","ZJ",0);
		if(hotelBxRows>0){
			for(int i=0;i<hotelBxRows;i++){
				String id=rd.getStringByDI("hotelBxList","ID",i);
	%>
	<script type="text/javascript">
		function checkBox<%=i%>(obj){
			var hz=document.getElementsByName("TA_ZT_BXHOTEL/SFHZ[<%=i %>]");
			//alert(obj.value);
			for(var i=0;i<hz.length;i++){
				if(hz[i]==obj && hz[i].checked){
					hz[i].checked=true;
				}else{
					hz[i].checked=false;
				}
			}
		}
	</script>
	<table class="datas select-hotel" width="95%" id="hotel<%=i %>">
			<tr>
				<td colspan="4">
				<span>酒店计调</span>
				<%if(i==0){ %><span  class="showPointer" id="addHotel">添加</span><%}else{ %><span onclick="delTab('hotel<%=i %>')" class="showPointer">删除</span><%} %>
				</td>
			</tr>
			<tr>
			  <td colspan="4">请选择酒店：
			 	<select name="TA_ZT_BXHOTEL/SF[<%=i %>]" id="pro<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="1"></select>
			  	<select name="TA_ZT_BXHOTEL/CITY[<%=i %>]" id="city<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="2"></select>
			  	<select name="TA_ZT_BXHOTEL/XQ[<%=i %>]" id="area<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="3"></select>
			  	<select name="TA_ZT_BXHOTEL/XJ[<%=i %>]" id="level<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="4"></select>
			  	<select name="TA_ZT_BXHOTEL/JDID[<%=i %>]" class="hotelid" id="id_hotel<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="5" onchange="getHotelInfo(this.value,'hotelInfo<%=i %>')"></select><br/>
                &nbsp;&nbsp;&nbsp; <font color="red">含桌早</font><input type="checkBox" name="TA_ZT_BXHOTEL/SFHZ[<%=i %>]" value="1" <%if("1".equals(rd.getStringByDI("hotelBxList","SFHZ",i))){ %>checked="checked"<%} %> onclick="checkBox<%=i%>(this);"/> 
				&nbsp; <font color="red">含自助早</font><input type="checkBox" name="TA_ZT_BXHOTEL/SFHZ[<%=i %>]" value="2" <%if("2".equals(rd.getStringByDI("hotelBxList","SFHZ",i))){ %>checked="checked"<%} %> onclick="checkBox<%=i%>(this);"/> 
				&nbsp; <font color="red">含打包早</font><input type="checkBox" name="TA_ZT_BXHOTEL/SFHZ[<%=i %>]" value="3" <%if("3".equals(rd.getStringByDI("hotelBxList","SFHZ",i))){ %>checked="checked"<%} %> onclick="checkBox<%=i%>(this);"/>
			  </td>
			</tr>
			<tr >
				<td colspan="4" id="hotelInfo<%=i %>">
					<table class="datas" style="text-align: center">
						<tr id="first-tr">
							<td>房间类型</td>
							<td>价格</td>
							<td>房间数</td>
							<td>合计</td>
						</tr>
						<%
							String jdid=rd.getStringByDI("hotelBxList","JDID",i);
							int hotelPlanJgPriceRows=rd.getTableRowsCount("hotelJgBxList");
							int zfjs=0;
							for(int p=0;p<hotelPlanJgPriceRows;p++){
								String jdjhid=rd.getStringByDI("hotelJgBxList","JDJHID",p);
								if(id.equals(jdjhid)){
								zfjs+=Integer.parseInt(rd.getStringByDI("hotelJgBxList","JG",p));
						%>
						<tr>
							<td><%=rd.getStringByDI("hotelJgBxList","PRICENAME",p)%><input type="hidden" name="hotel<%=jdid%>/priceName[<%=p %>]" value="<%=rd.getStringByDI("hotelJgBxList","dmz",p)%>" onkeydown="checkNum()"></td>
							<td><input type="text" value="<%=rd.getStringByDI("hotelJgBxList","JG",p)%>" name="hotel<%=jdid%>/price[<%=p %>]" class="hotelPrice" onchange="SumPrice()" onkeydown="checkNum()">元/间</td>
							<td><input type="text" value="<%=rd.getStringByDI("hotelJgBxList","FJS",p)%>" class="hotelNum" onchange="SumPrice()" name="hotel<%=jdid%>/roomNum[<%=p %>]" onkeydown="checkNum()"/>/间</td>
							<td><input type="text" value="<%=Integer.parseInt(rd.getStringByDI("hotelJgBxList","JG",p))*Integer.parseInt(rd.getStringByDI("hotelJgBxList","FJS",p))%>" class="xiaoji" readonly="readonly"/>元</td>
						</tr>
						<%} }%>
					</table>
				</td>
			</tr>
			<tr>
				<td  colspan="4">
				入住时间：<input type="text" name="TA_ZT_BXHOTEL/RZ_TIME[<%=i %>]" value="<%=rd.getStringByDI("hotelBxList","RZ_TIME",i)%>" onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});"/>&nbsp;
				入住天数：<input type="text" name="TA_ZT_BXHOTEL/TS[<%=i %>]" value="<%=rd.getStringByDI("hotelBxList","TS",i)%>" onkeydown="checkNum()" class="ts" onchange="SumPrice()"/>&nbsp;<br/>
				司陪住宿：<input type="text" name="TA_ZT_BXHOTEL/SFZSF[<%=i %>]" value="<%=rd.getStringByDI("hotelBxList","SFZSF",i)%>" onkeydown="checkNum()"/>&nbsp;
				          备注：<textarea rows="" cols="50" name="TA_ZT_BXHOTEL/BZ[<%=i %>]"><%=rd.getStringByDI("hotelBxList","BZ",i)%></textarea>
				</td>
			</tr>
		<tr>
			<td align="right" colspan="4">
			<font color="red">签单小计金额：</font><input type="text" value="<%=rd.getStringByDI("hotelBxList","QDXJ",i) %>" name="TA_ZT_BXHOTEL/QDXJ[<%=i %>]" onkeydown="checkNum()" class="qdxj" onchange="sumRs()"/ >/元&nbsp;&nbsp;&nbsp;
			<font color="red">现付小计金额：</font><input type="text" value="<%=rd.getStringByDI("hotelBxList","XFXJ",i) %>" name="TA_ZT_BXHOTEL/XFXJ[<%=i %>]"  readonly="readonly" class="xfxj"/>/元&nbsp;&nbsp;&nbsp;
			<font color="red">共计：</font>   <input  type="text" value="<%=rd.getStringByDI("hotelBxList","HJ",i) %>"  name="TA_ZT_BXHOTEL/HJ[<%=i %>]" readonly="readonly" class="gj"/>/元
			</td>
		</tr>

  </table>
  <%}}else{  %>
  	<script type="text/javascript">
		function checkBox(obj){
			var hz=document.getElementsByName("TA_ZT_BXHOTEL/SFHZ[0]");
			for(var j=0;j<hz.length;j++){
				if(hz[j]==obj && hz[j].checked){
					hz[j].checked=true;
				}else{
					hz[j].checked=false;
				}
			}
		}
	</script>
  	<table class="datas select-hotel" width="95%" id="hotel0">
			<tr>
				<td colspan="4">
				<span>酒店计划</span>
				<span  class="showPointer" id="addHotel">添加</span>
				</td>
			</tr>
			<tr>
			  <td colspan="4">请选择酒店：
			  	<select name="TA_ZT_BXHOTEL/SF[0]" id="pro0" USEDATA="dataSrc0" SUBCLASS="1"></select>
			  	<select name="TA_ZT_BXHOTEL/CITY[0]" id="city0" USEDATA="dataSrc0" SUBCLASS="2"></select>
			  	<select name="TA_ZT_BXHOTEL/XQ[0]" id="area0" USEDATA="dataSrc0" SUBCLASS="3"></select>
			  	<select name="TA_ZT_BXHOTEL/XJ[0]" id="level0" USEDATA="dataSrc0" SUBCLASS="4"></select>
			  	<select name="TA_ZT_BXHOTEL/JDID[0]" class="hotelid" id="id_hotel0" USEDATA="dataSrc0" SUBCLASS="5" onchange="getHotelInfo(this.value,'hotelInfo0')"></select><br/>
                &nbsp;&nbsp;&nbsp; <font color="red">含桌早</font><input type="checkBox" name="TA_ZT_BXHOTEL/SFHZ[0]" value="1" onclick="checkBox(this);"/> 
				&nbsp; <font color="red">含自助早</font><input type="checkBox" name="TA_ZT_BXHOTEL/SFHZ[0]" value="2" onclick="checkBox(this);"/> 
				&nbsp; <font color="red">含打包早</font><input type="checkBox" name="TA_ZT_BXHOTEL/SFHZ[0]" value="3" onclick="checkBox(this);"/> 
			  </td>
			</tr>
			<tr >
				<td colspan="4" id="hotelInfo0">
				</td>
			</tr>
			<tr>
				<td  colspan="4">
				入住时间：<input type="text" name="TA_ZT_BXHOTEL/RZ_TIME[0]" onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});"/>&nbsp;
				入住天数：<input type="text" name="TA_ZT_BXHOTEL/TS[0]"/>&nbsp;<br/>
				司陪住宿：<input type="text" name="TA_ZT_BXHOTEL/SFZSF[0]" onkeydown="checkNum()" class="ts" onchange="SumPrice()"/>&nbsp;
				          备注：<textarea rows="" cols="50" name="TA_ZT_BXHOTEL/BZ[0]"></textarea>
				</td>
			</tr>
		<tr>
			<td align="right" colspan="4">
			<font color="red">签单小计金额：</font><input type="text" value="0" name="TA_ZT_BXHOTEL/QDXJ[0]" onkeydown="checkNum()" class="qdxj" onchange="sumRs()"/ >/元&nbsp;&nbsp;&nbsp;
			<font color="red">现付小计金额：</font><input type="text" value="0" name="TA_ZT_BXHOTEL/XFXJ[0]"  readonly="readonly" class="xfxj"/>/元<br/>
			<font color="red">共计：</font>   <input  type="text" value="0"  name="TA_ZT_BXHOTEL/HJ[0]" readonly="readonly" class="gj"/>/元
			</td>
		</tr>

  </table>
  <%} %>
	
</div>
<table class="datas" style="margin-top: 3px;">
	<tr>
		<td ><span>酒店费用合计</span></td>
	</tr>
	<tr>
		<td align="right">
			<font color="red">签单金额总计：</font><input name="TA_TDBXZJXXB_ZT/BXJDQD" value="<%="".equals(qdzj)?"0":qdzj %>"  type="text" id="qiandan" readonly="readonly"/>/元&nbsp;&nbsp;&nbsp;
			<font color="red">现付金额总计：</font><input name="TA_TDBXZJXXB_ZT/BXJDXF" value="<%="".equals(xfzj)?"0":xfzj %>"  type="text" id="xianfu" readonly="readonly" name="xfzj"/>/元&nbsp;&nbsp;&nbsp;
			<font color="red">总计：</font>   <input name="TA_TDBXZJXXB_ZT/JDHJ" type="text" value="<%="".equals(zj)?"0":zj %>" readonly="readonly" id="zongji" />/元
		</td>
	</tr>
</table>
</div>
<div align="center" id="last-sub">
	<input type="button" id="button" value="提交" onclick="p_editHotelBx();"/>
	<input type="button" id="button" value="返回" onclick="window.history.go(-1);"/>
</div>

</form>
</body>
</html>
<script type="text/javascript">
<%
	if(hotelBxRows>0){
	for(int v=0;v<hotelBxRows;v++){
%>
	var linkage = new Linkage("dataSrc<%=v%>", "<%=request.getContextPath()%>/main/data/Hotelz.xml");
	linkage.init();
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("hotelBxList","SF",v)%>",1);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("hotelBxList","CITY",v)%>",2);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("hotelBxList","XQ",v)%>",3);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("hotelBxList","LEVEL_ID",v)%>",4);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("hotelBxList","JDID",v)%>",5);
<%}}else{%>
	var linkage = new Linkage("dataSrc0", "<%=request.getContextPath()%>/main/data/Hotelz.xml");
	linkage.init();
<%}%>
</script>