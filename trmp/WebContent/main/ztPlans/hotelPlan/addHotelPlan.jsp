<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
<title>酒店计划</title>
<script type="text/javascript">

	function checkNum(){
		if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
			  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
			    event.returnValue=false;
	}
	
	function checkBox(obj){
		var hz=document.getElementsByName("TA_ZT_JHHOTEL/SFHZ[0]");
		for(var i=0;i<hz.length;i++){
			if(hz[i]==obj && hz[i].checked){
				hz[i].checked=true;
			}else{
				hz[i].checked=false;
			}
		}
	}
	
	function checkBoxNum(obj){
		var cnum=jQuery(".select-hotel").size();
		var hz=document.getElementsByName("TA_ZT_JHHOTEL/SFHZ["+(cnum-1)+"]");
		for(var i=0;i<hz.length;i++){
			if(hz[i]==obj && hz[i].checked){
				hz[i].checked=true;
			}else{
				hz[i].checked=false;
			}
		}
	}
	
jQuery(document).ready(function(){
	jQuery(".showPointer").hover(function(){
		jQuery(this).css({cursor:'pointer'});
	});
});
	function addHotel(){
		 var num=jQuery(".select-hotel").size();
		 if(jQuery("#id_hotel"+(num-1)).val()==""){
				alert("请填写完整后再添加");
				return false;
		 }
		 jQuery("#hotelDiv").append('<table class="datas select-hotel" width="95%" id="hotel'+num+'">'+
					'<tr><td colspan="4"><span>酒店计划</span>&nbsp&nbsp&nbsp<span class="showPointer" onclick="addHotel()">添加</span>&nbsp&nbsp&nbsp<span onclick="delTab(\'hotel'+num+'\')" class="showPointer">删除</span></td></tr>'+
					'<input type="hidden" name="TA_ZT_JHHOTEL/TID['+num+']" value="<%=rd.getString("TID")%>">'+
				'<tr><td colspan="4">请选择酒店：'+
				   '<select name="TA_ZT_JHHOTEL/SF['+num+']" id="pro'+num+'" USEDATA="dataSrc'+num+'" SUBCLASS="1"></select> '+
		  		   '<select name="TA_ZT_JHHOTEL/CITY['+num+']" id="city'+num+'" USEDATA="dataSrc'+num+'" SUBCLASS="2"></select> '+
		           '<select name="TA_ZT_JHHOTEL/XQ['+num+']" id="area'+num+'" USEDATA="dataSrc'+num+'" SUBCLASS="3"></select> '+
		           '<select name="TA_ZT_JHHOTEL/XJ['+num+']" id="level'+num+'" USEDATA="dataSrc'+num+'" SUBCLASS="4"></select> '+
		           '<select name="TA_ZT_JHHOTEL/JDID['+num+']" class="hotelid" id="id_hotel'+num+'" USEDATA="dataSrc'+num+'" SUBCLASS="5" onchange="getHotelInfo(this.value,\'hotelInfo'+num+'\','+num+')"></select></td></tr>'+
		           '<tr><td colspan="4">'+
		           '&nbsp;<font color="red">含桌早</font><input type="checkBox" value="1" name="TA_ZT_JHHOTEL/SFHZ['+num+']" onclick="checkBoxNum(this);"/>'+ 
				   '&nbsp;<font color="red">含自助早</font><input type="checkBox" value="2" name="TA_ZT_JHHOTEL/SFHZ['+num+']" onclick="checkBoxNum(this);"/>'+ 
				   '&nbsp;<font color="red">含打包早</font><input type="checkBox" value="3" name="TA_ZT_JHHOTEL/SFHZ['+num+']" onclick="checkBoxNum(this);"/>'+
				   '&nbsp;入住时间：<input type="text" name="TA_ZT_JHHOTEL/RZ_TIME['+num+']" onFocus="WdatePicker({startDate:\'%y-%M-%d %H:%m:%s\',dateFmt:\'yyyy-MM-dd HH:mm:ss\',alwaysUseStartDate:true});"/>'+
				   '&nbsp;入住天数：<input type="text" name="TA_ZT_JHHOTEL/TS['+num+']" class="smallInput ts" onchange="SumPrice()" onkeydown="checkNum()"/>'+
				   '&nbsp;司陪住宿：<input type="text" name="TA_ZT_JHHOTEL/SFZSF['+num+']" onkeydown="checkNum()" class="smallInput spzsf" onchange="SumPrice()"/>'+
				   '</td></tr>'+
				   '<td align="left">联系人：</td>'+
					'<td><input name="TA_ZT_JHHOTEL/LXR['+num+']" id="lxrname'+num+'" value="" maxlength="10"/></td>'+
					'<td align="left">联系电话:</td>'+
					'<td><input name="TA_ZT_JHHOTEL/LXRDH['+num+']" id="lxrphone'+num+'" value="" maxlength="11"/></td>'+
				   '<tr ><td colspan="4" id="hotelInfo'+num+'">'+
				   '</td></tr>'+
				   '<tr><td colspan="4">备注：'+
					'<textarea rows="2" cols="90" name="TA_ZT_JHHOTEL/BZ['+num+']"></textarea></td></tr>'+
				   '<tr><td align="right" colspan="4">'+
			'<font color="red">签单小计金额：</font><input value="0" type="text" name="TA_ZT_JHHOTEL/QDXJ['+num+']" class="smallInput qdxj" onchange="sumRs()" onkeydown="checkNum()"/>/元&nbsp;&nbsp;&nbsp;'+
			'<font color="red">现付小计金额：</font><input value="0" type="text" name="TA_ZT_JHHOTEL/XFXJ['+num+']"  readonly="readonly" class="smallInput xfxj"/>/元&nbsp;&nbsp;&nbsp;'+
			'<input type="hidden" name="TA_ZT_JHHOTEL/JHZT['+num+']" value="Y"/>'+
			'<input type="hidden" name="TA_ZT_JHHOTEL/JHZDR['+num+']" value="<%=sd.getString("userno") %>"/>'+
			'<font color="red">共计：</font>   <input  type="text" value="0" name="TA_ZT_JHHOTEL/HJ['+num+']" readonly="readonly" class="smallInput gj" />/元</td></tr></table>');
			 var linkage = new Linkage("dataSrc"+num, "<%=request.getContextPath()%>/main/data/Hotelz.xml");
			 linkage.init();
			//alert(num);

}

	function delTab(tab){
		jQuery("#"+tab).remove();
		SumPrice();
		sumRs();
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
				
			jQuery("#"+tabName+" input.gj").val(Math.round(alltmp*100)/100);
			jQuery("#"+tabName+" input.qdxj");
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
				jQuery(".qdxj:eq("+i+")").val(Math.round(gj*100)/100);
				qd=gj;
				}
			jQuery(".xfxj:eq("+i+")").val(Math.round((gj-qd)*100)/100);
			qdzj+=parseInt(qd);
			xfzj+=(gj-qd);
			});
			jQuery("#qiandan").val(Math.round(qdzj*100)/100);
			jQuery("#xianfu").val(Math.round(xfzj*100)/100);
			jQuery("#zongji").val(Math.round((qdzj+xfzj)*100)/100);
	}
	
	function getHotelInfo(id,tdName,num){
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
			
			var obj=jQuery.ajax({url:"ztGetHotelLXR.?id="+id,
				async:true,
				dataType:"json",
				cache:false,
				success:function(data){
					jQuery("#lxrname"+num).val(data[0].name);
					jQuery("#lxrphone"+num).val(data[0].phone);
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
		            alert(errorThrown); 
		   	 	}
			});	
			
			//保存数据返回值更改html内容
			var obj=jQuery.ajax({url:"ztGetHotelPlanInfo.?id="+id+"&num="+num,async:true,dataType:"HTML",cache:false,success:function(){
				jQuery("#"+tdName).parents("tr").attr("style","");
				jQuery("#"+tdName).html(obj.responseText);
			}});
		}
	}
	
	var ts=jQuery(".tszj").val(); //旅游总天数
	function p_editHotelPlan(){
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
		document.p_hotelplan_form.action="ztAddHotelPlan.?";
		document.p_hotelplan_form.submit();
		reload();
	}
</script>
</head>

<body>
<form  name="p_hotelplan_form" method="post">
<div class="width-99">
<input type="hidden" name="userno" value="1"></input>
<div id="lable"><span >酒店计划</span></div>
<input type="hidden" name="" value="<%=rd.getStringByDI("TA_ZT_GROUPs","DAYS",0)%>" class="tszj">
<input type="hidden" name="TA_ZT_JHHOTEL/TID[0]" value="<%=rd.getString("TID")%>">
<input type="hidden" name="TA_ZT_JHHOTEL/ZDR[0]" value="<%=rd.getString("userno")%>">
<div id="bm-table">
	<div id="hotelDiv">
	<table class="datas select-hotel" width="95%" id="hotel0">
			<tr>
				<td colspan="4">
				<span>酒店计划</span>
				<span  class="showPointer" onclick="addHotel()">添加</span>
				</td>
			</tr>
			<tr>
			  <td colspan="4">请选择酒店：
			  	<select name="TA_ZT_JHHOTEL/SF[0]" id="pro0" USEDATA="dataSrc0" SUBCLASS="1"></select>
			  	<select name="TA_ZT_JHHOTEL/CITY[0]" id="city0" USEDATA="dataSrc0" SUBCLASS="2"></select>
			  	<select name="TA_ZT_JHHOTEL/XQ[0]" id="area0" USEDATA="dataSrc0" SUBCLASS="3"></select>
			  	<select name="TA_ZT_JHHOTEL/XJ[0]" id="level0" USEDATA="dataSrc0" SUBCLASS="4"></select>
			  	<select name="TA_ZT_JHHOTEL/JDID[0]" class="hotelid" id="id_hotel0" USEDATA="dataSrc0" SUBCLASS="5" onchange="getHotelInfo(this.value,'hotelInfo0',0)"></select>
			</tr>
			<tr>	
			  <td colspan="4">
                &nbsp;<font color="red">含桌早</font><input type="checkBox" name="TA_ZT_JHHOTEL/SFHZ[0]" value="1" onclick="checkBox(this);"/> 
				&nbsp;<font color="red">含自助早</font><input type="checkBox" name="TA_ZT_JHHOTEL/SFHZ[0]" value="2" onclick="checkBox(this);"/> 
				&nbsp;<font color="red">含打包早</font><input type="checkBox" name="TA_ZT_JHHOTEL/SFHZ[0]" value="3" onclick="checkBox(this);"/> 
				&nbsp;入住时间：<input type="text" name="TA_ZT_JHHOTEL/RZ_TIME[0]" onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});"/>
				&nbsp;入住天数：<input type="text" name="TA_ZT_JHHOTEL/TS[0]" class="smallInput ts" onkeydown="checkNum()" onchange="SumPrice()"/>
				&nbsp;司陪住宿：<input type="text" name="TA_ZT_JHHOTEL/SFZSF[0]" onkeydown="checkNum()" class="smallInput spzsf" onchange="SumPrice()"/>
			  </td>
			</tr>
			<tr>
				<td align="left">联系人:</td>
				<td>
					<input name="TA_ZT_JHHOTEL/LXR[0]" id="lxrname0" value="" maxlength="10"/>
				</td>
				<td align="left">联系电话:</td>
				<td>
					<input name="TA_ZT_JHHOTEL/LXRDH[0]" id="lxrphone0" maxlength="11" value=""/>
				</td>
			</tr>
			<tr >
				<td colspan="4" id="hotelInfo0">
				</td>
			</tr>
			<tr>
			<td  colspan="4">
				备注：<textarea rows="2" cols="90" name="TA_ZT_JHHOTEL/BZ[0]"> </textarea>
			</td>
			</tr>
		<tr>
			<td align="right" colspan="4">
			<font color="red">签单小计金额：</font><input type="text" value="0" name="TA_ZT_JHHOTEL/QDXJ[0]" onkeydown="checkNum()" class="smallInput qdxj" onchange="sumRs()"/ >/元&nbsp;&nbsp;&nbsp;
			<font color="red">现付小计金额：</font><input type="text" value="0" name="TA_ZT_JHHOTEL/XFXJ[0]"  readonly="readonly" class="smallInput xfxj"/>/元 
			<font color="red">共计：</font>   <input  type="text" value="0"  name="TA_ZT_JHHOTEL/HJ[0]" readonly="readonly" class="smallInput gj"/>/元
			<input type="hidden" name="TA_ZT_JHHOTEL/JHZT[0]" value="Y"/>
			<input type="hidden" name="TA_ZT_JHHOTEL/JHZDR[0]" value="<%=sd.getString("userno") %>"/>
			</td>
		</tr>

  </table>

</div>
<table class="datas" style="margin-top: 3px;">
	<tr>
		<td ><span>酒店费用合计</span></td>
	</tr>
	<tr>
		<td align="right">
			<font color="red">签单金额总计：</font><input name="TA_TDJDXXZJB_ZT/QDZSZJ" value="0"  type="text" id="qiandan" readonly="readonly" class="smallInput"/>/元&nbsp;&nbsp;&nbsp;
			<font color="red">现付金额总计：</font><input name="TA_TDJDXXZJB_ZT/XFZSZJ" value="0"  type="text" id="xianfu" readonly="readonly" class="smallInput"/>/元 
			<font color="red">总计：</font>   <input name="TA_TDJDXXZJB_ZT/ZSZJ" type="text" value="0" readonly="readonly" id="zongji" class="smallInput"/>/元
		</td>
	</tr>
</table>
</div>
<div align="center" id="last-sub">
	<input type="button" id="button" value="提交" onclick="p_editHotelPlan();"/>
	<input type="button" id="button" value="关闭" onclick="javascript:window.parent.parent.GB_hide();"/>
</div>
</div>
</form>
</body>
</html>
<script type="text/javascript">
	var linkage = new Linkage("dataSrc0", "<%=request.getContextPath()%>/main/data/Hotelz.xml");
	linkage.init();
</script>