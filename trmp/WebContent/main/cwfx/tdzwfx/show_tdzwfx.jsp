<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="zwfx.files/filelist.xml">
<link rel=Edit-Time-Data href="zwfx.files/editdata.mso">
<link rel=OLE-Object-Data href="zwfx.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>devil</o:Author>
  <o:LastAuthor>devil</o:LastAuthor>
  <o:Created>2011-05-30T03:18:51Z</o:Created>
  <o:LastSaved>2011-05-30T03:56:03Z</o:LastSaved>
  <o:Company>jst</o:Company>
  <o:Version>11.9999</o:Version>
 </o:DocumentProperties>
</xml><![endif]-->
<style>
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
@page
	{margin:1.0in .75in 1.0in .75in;
	mso-header-margin:.5in;
	mso-footer-margin:.5in;}
tr
	{mso-height-source:auto;
	mso-ruby-visibility:none;}
col
	{mso-width-source:auto;
	mso-ruby-visibility:none;}
br
	{mso-data-placement:same-cell;}
.style0
	{mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	white-space:nowrap;
	mso-rotate:0;
	mso-background-source:auto;
	mso-pattern:auto;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	border:none;
	mso-protection:locked visible;
	mso-style-name:常规;
	mso-style-id:0;}
td
	{mso-style-parent:style0;
	padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border:none;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:locked visible;
	white-space:nowrap;
	mso-rotate:0;}
.xl24
	{mso-style-parent:style0;
	font-weight:700;}
.xl25
	{mso-style-parent:style0;
	font-size:14.0pt;
	font-family:华文新魏;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	text-align:center;
	border:.5pt solid windowtext;}
.xl26
	{mso-style-parent:style0;
	text-align:center;
	border:.5pt solid windowtext;}
.xl27
	{mso-style-parent:style0;
	font-weight:700;
	text-align:left;
	border:.5pt solid windowtext;}
.xl28
	{mso-style-parent:style0;
	text-align:left;
	border:.5pt solid windowtext;}
.xl29
	{mso-style-parent:style0;
	
	text-align:center;
	border:.5pt solid windowtext;}
.xl30
	{mso-style-parent:style0;
	border:.5pt solid windowtext;}
ruby
	{ruby-align:left;}
rt
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-char-type:none;
	display:none;}
-->
</style>
<!--[if gte mso 9]><xml>
 <x:ExcelWorkbook>
  <x:ExcelWorksheets>
   <x:ExcelWorksheet>
    <x:Name>Sheet1</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:Print>
      <x:ValidPrinterInfo/>
      <x:PaperSizeIndex>9</x:PaperSizeIndex>
      <x:HorizontalResolution>200</x:HorizontalResolution>
      <x:VerticalResolution>200</x:VerticalResolution>
     </x:Print>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>12</x:ActiveRow>
       <x:ActiveCol>12</x:ActiveCol>
      </x:Pane>
     </x:Panes>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
   <x:ExcelWorksheet>
    <x:Name>Sheet2</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
   <x:ExcelWorksheet>
    <x:Name>Sheet3</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
  </x:ExcelWorksheets>
  <x:WindowHeight>9120</x:WindowHeight>
  <x:WindowWidth>17115</x:WindowWidth>
  <x:WindowTopX>120</x:WindowTopX>
  <x:WindowTopY>105</x:WindowTopY>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
<style type="text/css">
	body{margin: 0;padding: 0;font-size: 12px;}
	#top{background: url(<%=request.getContextPath()%>/images/ToolBarBg.gif) repeat-x;height: 35px;text-align: center;line-height: 35px;}
	#print{background: url(<%=request.getContextPath()%>/images/Print.gif) no-repeat 0 7px;width: 55px;text-indent: 15px;float: left;cursor: pointer;}
	#word{background: url(<%=request.getContextPath()%>/images/word.gif) no-repeat 0 7px;width: 82px;text-indent: 15px;float: left;cursor: pointer;}
	#close{background: url(<%=request.getContextPath()%>/images/printClose.gif) no-repeat 0 11px;width: 49px;text-indent: 10px;float: left;cursor: pointer;}
	#center-top{background: url(<%=request.getContextPath()%>/images/body_04.jpg) no-repeat;width: 793px;height: 33px;text-align: center}
	#center-cen{background: url(<%=request.getContextPath()%>/images/body_05.jpg) repeat-y;width: 793px;}
	#button{background: url(<%=request.getContextPath()%>/images/body_06.jpg) no-repeat;width: 793px;}
</style> 
</head>

<body link=blue vlink=purple>
<div id="top">
  <div id="print" onclick="print();doPrint()"><span title="打印">打印</span></div>
  <!--<div id="word" onclick="exp();"><span title="导出word文件">导出word</span></div>
  --><div id="close" onclick="window.close();"><span title="关闭窗口">关闭</span></div>
</div>
	<div id="center" align="center">
		<div id="center-top"></div>
		<div id="center-cen">		
			<div align="center">
<table x:str border=0 cellpadding=0 cellspacing=0 width=576 style='border-collapse:
 collapse;table-layout:fixed;width:432pt'>
 <col width=72 span=8 style='width:54pt'>
 <tr height=24 style='height:18.0pt'>
  <td colspan=8 height=24 class=xl25 width=576 style='height:18.0pt;width:432pt'>团队账务分析</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=8 height=19 class=xl27 style='height:14.25pt'>&gt;&gt;基本信息</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=8 height=19 class=xl28 style='height:14.25pt'>线路名称:<%=rd.getStringByDI("moneyInfo","line_name",0) %></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=4 height=19 class=xl28 style='height:14.25pt'>团号:<%=rd.getStringByDI("moneyInfo","g_id",0) %></td>
  <td colspan=4 class=xl28 style='border-left:none'>起止日期:<%=rd.getStringByDI("moneyInfo","begin_date",0) %>至<%=rd.getStringByDI("moneyInfo","end_date",0) %></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=4 height=19 class=xl28 style='height:14.25pt'>天数:<%=rd.getStringByDI("moneyInfo","days",0) %>天</td>
  <td colspan=4 class=xl28 style='border-left:none'>人数:成人:<%=rd.getStringByDI("moneyInfo","adult_count",0) %>人/儿童:<%=rd.getStringByDI("moneyInfo","children_count",0) %>人</td>
 </tr>
 <tr class=xl24 height=19 style='height:14.25pt'>
  <td colspan=8 height=19 class=xl27 style='height:14.25pt'>&gt;&gt;账面支出</td>
 </tr>
 <tr class=xl24 height=19 style='height:14.25pt'>
  <td colspan=2 height=19 class=xl29 style='height:14.25pt'>费用名称</td>
  <td colspan=2 class=xl29 style='border-left:none'>费用</td>
  <td colspan=2 class=xl29 style='border-left:none'>签单</td>
  <td colspan=2 class=xl29 style='border-left:none'>现付</td>
 </tr>
 <!-- 
	 <tr class=xl24 height=19 style='height:14.25pt'>
	  <td colspan=2 height=19 class=xl26 style='height:14.25pt'>往返大交通</td>
	  <td colspan=2 class=xl29 style='border-left:none'>　</td>
	  <td colspan=2 class=xl29 style='border-left:none'>　</td>
	  <td colspan=2 class=xl29 style='border-left:none'>　</td>
	 </tr>
 --> 
 <tr class=xl24 height=19 style='height:14.25pt'>
  <td colspan=2 height=19 class=xl26 style='height:14.25pt'>订票费</td>
  <td colspan=2 class=xl29 style='border-left:none'><%=rd.getStringByDI("moneyInfo","ticket_zj",0) %></td>
  <td colspan=2 class=xl29 style='border-left:none'><%=rd.getStringByDI("moneyInfo","bx_ticket_qd",0) %></td>
  <td colspan=2 class=xl29 style='border-left:none'><%=rd.getStringByDI("moneyInfo","bx_ticket_xf",0) %></td>
 </tr>
 <tr class=xl24 height=19 style='height:14.25pt'>
  <td colspan=2 height=19 class=xl26 style='height:14.25pt'>车费</td>
  <td colspan=2 class=xl29 style='border-left:none'><%=rd.getStringByDI("moneyInfo","veh_zj",0) %></td>
  <td colspan=2 class=xl29 style='border-left:none'><%=rd.getStringByDI("moneyInfo","bx_veh_qd",0) %></td>
  <td colspan=2 class=xl29 style='border-left:none'><%=rd.getStringByDI("moneyInfo","bx_veh_xf",0) %></td>
 </tr>
 <tr class=xl24 height=19 style='height:14.25pt'>
  <td colspan=2 height=19 class=xl26 style='height:14.25pt'>房费</td>
  <td colspan=2 class=xl29 style='border-left:none'><%=rd.getStringByDI("moneyInfo","hotel_zj",0) %>　</td>
  <td colspan=2 class=xl29 style='border-left:none'><%=rd.getStringByDI("moneyInfo","bx_hotel_qd",0) %>　</td>
  <td colspan=2 class=xl29 style='border-left:none'><%=rd.getStringByDI("moneyInfo","bx_hotel_xf",0) %>　</td>
 </tr>
 <tr class=xl24 height=19 style='height:14.25pt'>
  <td colspan=2 height=19 class=xl26 style='height:14.25pt'>餐费</td>
  <td colspan=2 class=xl29 style='border-left:none'>　<%=rd.getStringByDI("moneyInfo","dinner_zj",0) %></td>
  <td colspan=2 class=xl29 style='border-left:none'>　<%=rd.getStringByDI("moneyInfo","bx_dinnerroom_qd",0) %></td>
  <td colspan=2 class=xl29 style='border-left:none'><%=rd.getStringByDI("moneyInfo","bx_dinnerroom_xf",0) %></td>
 </tr>
 <tr class=xl24 height=19 style='height:14.25pt'>
  <td colspan=2 height=19 class=xl26 style='height:14.25pt'>门票</td>
  <td colspan=2 class=xl29 style='border-left:none'><%=rd.getStringByDI("moneyInfo","scenery_zj",0) %></td>
  <td colspan=2 class=xl29 style='border-left:none'><%=rd.getStringByDI("moneyInfo","bx_scenery_qd",0) %></td>
  <td colspan=2 class=xl29 style='border-left:none'>　<%=rd.getStringByDI("moneyInfo","bx_scenery_xf",0) %></td>
 </tr>
 <tr class=xl24 height=19 style='height:14.25pt'>
  <td colspan=2 height=19 class=xl26 style='height:14.25pt'>地接社</td>
  <td colspan=2 class=xl29 style='border-left:none'>　</td>
  <td colspan=2 class=xl29 style='border-left:none'>　</td>
  <td colspan=2 class=xl29 style='border-left:none'>　</td>
 </tr>
 <tr class=xl24 height=19 style='height:14.25pt'>
  <td colspan=2 height=19 class=xl26 style='height:14.25pt'>保险费用</td>
  <td colspan=6 class=xl29 style='border-top:none;border-left:none'><%=rd.getStringByDI("moneyInfo","bxzj",0) %></td>
 </tr>
 <tr class=xl24 height=19 style='height:14.25pt'>
  <td colspan=8 height=19 class=xl27 style='height:14.25pt'>&gt;&gt;导游费用</td>
 </tr>
 <tr class=xl24 height=19 style='height:14.25pt'>
  <td colspan=4 height=19 class=xl29 style='height:14.25pt'>费用名称</td>
  <td colspan=4 class=xl29 style='border-left:none'>费用</td>
 </tr>
 <%int gRows=rd.getTableRowsCount("guideMoney");for(int g=0;g<gRows;g++){ %>
 <tr class=xl24 height=19 style='height:14.25pt'>
  <td colspan=4 height=19 class=xl29 style='height:14.25pt'><%=rd.getStringByDI("guideMoney","dmsm1",g) %>　</td>
  <td colspan=4 class=xl26 style='border-left:none'><%=rd.getStringByDI("guideMoney","costs",g) %>　</td>
 </tr>
 <%} %>
 <tr height=19 style='height:14.25pt'>
  <td colspan=8 height=19 class=xl27 style='height:14.25pt'>&gt;&gt;账外收入</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl26 style='height:14.25pt;border-top:none'>加点项目</td>
  <td class=xl26 style='border-top:none;border-left:none'>人均消费</td>
  <td class=xl26 style='border-top:none;border-left:none'>人数</td>
  <td class=xl26 style='border-top:none;border-left:none'>人头费</td>
  <td class=xl26 style='border-top:none;border-left:none'>成本单价</td>
  <td class=xl26 style='border-top:none;border-left:none'>支付方式</td>
  <td class=xl26 style='border-top:none;border-left:none'>成本金额</td>
  <td class=xl26 style='border-top:none;border-left:none'>利润</td>
 </tr>
<%int jdRows=rd.getTableRowsCount("jdMoney");for(int j=0;j<jdRows;j++){ %>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl30 style='height:14.25pt;border-top:none'><%=rd.getStringByDI("jdMoney","cmpny_name",j) %></td>
  <td class=xl30 style='border-top:none;border-left:none'><%=rd.getStringByDI("jdMoney","rjxfe",j) %></td>
  <td class=xl30 style='border-top:none;border-left:none'><%=rd.getStringByDI("jdMoney","rs",j) %></td>
  <td class=xl30 style='border-top:none;border-left:none'><%=rd.getStringByDI("jdMoney","rtf",j) %></td>
  <td class=xl30 style='border-top:none;border-left:none'><%=rd.getStringByDI("jdMoney","cbdj",j) %></td>
  <td class=xl30 style='border-top:none;border-left:none'><%if("1".equals(rd.getStringByDI("jdMoney","zffs",j)))out.print("签单");else out.print("现付"); %></td>
  <td class=xl30 style='border-top:none;border-left:none'><%=rd.getStringByDI("jdMoney","cbje",j) %>　</td>
  <td class=xl30 style='border-top:none;border-left:none'><%=rd.getStringByDI("jdMoney","lr",j) %>　</td>
 </tr>
 <%} %>
 <tr height=19 style='height:14.25pt'>
  <td colspan=8 height=19 class=xl27 style='height:14.25pt'>&gt;&gt;加点提成</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=2 height=19 class=xl26 style='height:14.25pt'>提成人员</td>
  <td colspan=3 class=xl26 style='border-left:none'>提成比例</td>
  <td colspan=3 class=xl26 style='border-left:none'>提成金额</td>
 </tr>
 <%int jdtcRows=rd.getTableRowsCount("jdtcInfo");for(int k=0;k<jdtcRows;k++){ %>
 <tr height=19 style='height:14.25pt'>
  <td colspan=2 height=19 class=xl26 style='height:14.25pt'><%=rd.getStringByDI("jdtcInfo","dmsm1",k) %>　</td>
  <td colspan=3 class=xl26 style='border-left:none'>%<%=rd.getStringByDI("jdtcInfo","tcbl",k) %>　</td>
  <td colspan=3 class=xl26 style='border-left:none'><%=rd.getStringByDI("jdtcInfo","tcje",k) %></td>
 </tr>
 <%}%>
 <tr height=19 style='height:14.25pt'>
  <td colspan=4 class=xl28>应交财务现金合计:<%=rd.getStringByDI("moneyInfo","OUGHT_MONEY_COUNT",0) %></td>
  <td colspan=4 class=xl28 style='border-left:none'>签单现交金额:<%=rd.getStringByDI("moneyInfo","SIGN_MONEY_COUNT",0) %></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=8 height=19 class=xl27 style='height:14.25pt'>&gt;&gt;购物回执</td>
 </tr>
 <%int shopRows=rd.getTableRowsCount("shopList");for(int m=0;m<shopRows;m++){ %>
 <tr height=19 style='height:14.25pt'>
  <td colspan=8 height=19 class=xl28 style='height:14.25pt'>购物点:<%=rd.getStringByDI("shopList","cmpny_name",m) %></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=3 height=19 class=xl26 style='height:14.25pt'>商品名</td>
  <td colspan=3 class=xl26 style='border-left:none'>人均消费额</td>
  <td colspan=2 class=xl26 style='border-left:none'>人数</td>
 </tr>
 <%int goodsRows=rd.getTableRowsCount("goodsMoney");
 for(int n=0;n<goodsRows;n++){
 	if(rd.getStringByDI("shopList","id",m).equals(rd.getStringByDI("goodsMoney","bx_shopid",n))){
	 %>
 <tr height=19 style='height:14.25pt'>
  <td colspan=3 height=19 class=xl26 style='height:14.25pt'><%=rd.getStringByDI("goodsMoney","goods_name",n) %></td>
  <td colspan=3 class=xl26 style='border-left:none'><%=rd.getStringByDI("goodsMoney","rjxfe",n) %></td>
  <td colspan=2 class=xl26 style='border-left:none'><%=rd.getStringByDI("goodsMoney","p_num",n) %>　</td>
 </tr>
 <%}}%>
 <tr height=19 style='height:14.25pt'>
  <td colspan=8 height=19 class=xl27 style='height:14.25pt'>&gt;&gt;购物提成</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=2 height=19 class=xl26 style='height:14.25pt'>提成人员</td>
  <td colspan=3 class=xl26 style='border-left:none'>提成比例</td>
  <td colspan=3 class=xl26 style='border-left:none'>提成金额</td>
 </tr>
 <%int gwtcRows=rd.getTableRowsCount("shoptcInfo");
 for(int p=0;p<gwtcRows;p++){
	 if(rd.getStringByDI("shopList","id",m).equals(rd.getStringByDI("shoptcInfo","bx_shopid",p))){ 
	 %>
 <tr height=19 style='height:14.25pt'>
  <td colspan=2 height=19 class=xl26 style='height:14.25pt'><%=rd.getStringByDI("shoptcInfo","dmsm1",p) %></td>
  <td colspan=3 class=xl26 style='border-left:none'>%<%=rd.getStringByDI("shoptcInfo","tcbl",p) %></td>
  <td colspan=3 class=xl26 style='border-left:none'><%=rd.getStringByDI("shoptcInfo","tcje",p) %></td>
 </tr>
 <%}}} %>
 
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
 </tr>
 <![endif]>
</table>
</div>
			</div>
		</div>
<div align="center">
		<div  id="button" ></div>
	</div>
</body>

</html>