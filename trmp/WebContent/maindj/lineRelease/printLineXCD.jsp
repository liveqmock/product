<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="java.sql.Blob"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="com.trmdj.businessPlan.groupMng.GroupLineDetail"%>
<%@include file="/common.jsp" %>
<html xmlns:v="urn:schemas-microsoft-com:vml"
xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:w="urn:schemas-microsoft-com:office:word"
xmlns:st1="urn:schemas-microsoft-com:office:smarttags"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<meta name=ProgId content=Word.Document>
<meta name=Generator content="Microsoft Word 11">
<meta name=Originator content="Microsoft Word 11">
<link rel=File-List href="散客报价表.files/filelist.xml">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<title><%=sd.getString("simpleName") %>散客报价表</title>
<o:SmartTagType namespaceuri="urn:schemas-microsoft-com:office:smarttags"
 name="chmetcnv"/>
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>USER</o:Author>
  <o:LastAuthor>USER</o:LastAuthor>
  <o:Revision>2</o:Revision>
  <o:TotalTime>18</o:TotalTime>
  <o:Created>2012-04-24T09:04:00Z</o:Created>
  <o:LastSaved>2012-04-24T09:04:00Z</o:LastSaved>
  <o:Pages>1</o:Pages>
  <o:Words>30</o:Words>
  <o:Characters>177</o:Characters>
  <o:Lines>1</o:Lines>
  <o:Paragraphs>1</o:Paragraphs>
  <o:CharactersWithSpaces>206</o:CharactersWithSpaces>
  <o:Version>11.9999</o:Version>
 </o:DocumentProperties>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <w:WordDocument>
  <w:SpellingState>Clean</w:SpellingState>
  <w:GrammarState>Clean</w:GrammarState>
  <w:PunctuationKerning/>
  <w:DrawingGridVerticalSpacing>7.8 磅</w:DrawingGridVerticalSpacing>
  <w:DisplayHorizontalDrawingGridEvery>0</w:DisplayHorizontalDrawingGridEvery>
  <w:DisplayVerticalDrawingGridEvery>2</w:DisplayVerticalDrawingGridEvery>
  <w:ValidateAgainstSchemas/>
  <w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid>
  <w:IgnoreMixedContent>false</w:IgnoreMixedContent>
  <w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText>
  <w:Compatibility>
   <w:SpaceForUL/>
   <w:BalanceSingleByteDoubleByteWidth/>
   <w:DoNotLeaveBackslashAlone/>
   <w:ULTrailSpace/>
   <w:DoNotExpandShiftReturn/>
   <w:AdjustLineHeightInTable/>
   <w:BreakWrappedTables/>
   <w:SnapToGridInCell/>
   <w:WrapTextWithPunct/>
   <w:UseAsianBreakRules/>
   <w:DontGrowAutofit/>
   <w:UseFELayout/>
  </w:Compatibility>
  <w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel>
 </w:WordDocument>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <w:LatentStyles DefLockedState="false" LatentStyleCount="156">
 </w:LatentStyles>
</xml><![endif]--><!--[if !mso]><object
 classid="clsid:38481807-CA0E-42D2-BF39-B33AF135CC4D" id=ieooui></object>
<style>
st1\:*{behavior:url(#ieooui) }
</style>
<![endif]-->
<style>
<!--
 /* Font Definitions */
 @font-face
	{font-family:宋体;
	panose-1:2 1 6 0 3 1 1 1 1 1;
	mso-font-alt:SimSun;
	mso-font-charset:134;
	mso-generic-font-family:auto;
	mso-font-pitch:variable;
	mso-font-signature:3 135135232 16 0 262145 0;}
@font-face
	{font-family:华文楷体;
	panose-1:2 1 6 0 4 1 1 1 1 1;
	mso-font-charset:134;
	mso-generic-font-family:auto;
	mso-font-pitch:variable;
	mso-font-signature:647 135200768 16 0 262303 0;}
@font-face
	{font-family:"\@华文楷体";
	panose-1:2 1 6 0 4 1 1 1 1 1;
	mso-font-charset:134;
	mso-generic-font-family:auto;
	mso-font-pitch:variable;
	mso-font-signature:647 135200768 16 0 262303 0;}
@font-face
	{font-family:"\@宋体";
	panose-1:2 1 6 0 3 1 1 1 1 1;
	mso-font-charset:134;
	mso-generic-font-family:auto;
	mso-font-pitch:variable;
	mso-font-signature:3 135135232 16 0 262145 0;}
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
span.GramE
	{mso-style-name:"";
	mso-gram-e:yes;}
 /* Page Definitions */
 @page
	{mso-page-border-surround-header:no;
	mso-page-border-surround-footer:no;}
@page Section1
	{size:595.3pt 841.9pt;
	margin:72.0pt 90.0pt 72.0pt 90.0pt;
	mso-header-margin:42.55pt;
	mso-footer-margin:49.6pt;
	mso-paper-source:0;
	layout-grid:15.6pt;}
div.Section1
	{page:Section1;}
-->
</style>
<!--[if gte mso 10]>
<style>
 /* Style Definitions */
 table.MsoNormalTable
	{mso-style-name:普通表格;
	mso-tstyle-rowband-size:0;
	mso-tstyle-colband-size:0;
	mso-style-noshow:yes;
	mso-style-parent:"";
	mso-padding-alt:0cm 5.4pt 0cm 5.4pt;
	mso-para-margin:0cm;
	mso-para-margin-bottom:.0001pt;
	mso-pagination:widow-orphan;
	font-size:10.0pt;
	font-family:"Times New Roman";
	mso-ansi-language:#0400;
	mso-fareast-language:#0400;
	mso-bidi-language:#0400;}
table.MsoTableGrid
	{mso-style-name:网格型;
	mso-tstyle-rowband-size:0;
	mso-tstyle-colband-size:0;
	border:solid windowtext 1.0pt;
	mso-border-alt:solid windowtext .5pt;
	mso-padding-alt:0cm 5.4pt 0cm 5.4pt;
	mso-border-insideh:.5pt solid windowtext;
	mso-border-insidev:.5pt solid windowtext;
	mso-para-margin:0cm;
	mso-para-margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.0pt;
	font-family:"Times New Roman";
	mso-ansi-language:#0400;
	mso-fareast-language:#0400;
	mso-bidi-language:#0400;}
</style>
<![endif]--><!--[if gte mso 9]><xml>
 <o:shapedefaults v:ext="edit" spidmax="2050"/>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <o:shapelayout v:ext="edit">
  <o:idmap v:ext="edit" data="1"/>
 </o:shapelayout></xml><![endif]-->
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
<%
 	String XLID = rd.getString("TA_DJ_LINEMNGs", "XLID", 0);
 	String XLMC = rd.getString("TA_DJ_LINEMNGs", "XLMC", 0);
 	String TS = rd.getString("TA_DJ_LINEMNGs", "TS", 0);
 	String FBJH = rd.getString("TA_DJ_LINEMNGs", "FBJH", 0);
 	String MP = rd.getString("TA_DJ_LINEMNGs", "MP", 0);
 	String YC = rd.getString("TA_DJ_LINEMNGs", "YC", 0);
 	String DY = rd.getString("TA_DJ_LINEMNGs", "DY", 0);
 	String GW = rd.getString("TA_DJ_LINEMNGs", "GW", 0);
 	String TBTX = rd.getString("TA_DJ_LINEMNGs", "TBTX", 0);
 	String fwbz = rd.getString("TA_DJ_LINEMNGs", "fwbz", 0);
 	String bzAll = rd.getString("TA_DJ_LINEMNGs", "bz", 0);
 	
 	
 %>
 <script type="text/javascript">

function exp(){
	window.location.href="expWordForXCD.?TA_DJ_LINEMNG/XLID=<%=XLID%>&TA_DJ_XLJG/XLID=<%=XLID%>&TA_DJ_LINEDETAIL/XLID=<%=XLID%>&DMSM/XLQY=20&DMSM/JGLX=4";
}

function doGroupPrint(){
	$.ajax({url:"doBxGroupPrint.?groupID=<%=rd.getStringByDI("groupList", "ID", 0)%>",cache:false,success:function(){
		}});
}

function print()
{
  //下面的属性为ScriptX控件的免费属性（不需要许可证）
  Printer.printing.header = "";          //页眉
  Printer.printing.footer = "";          //页脚

  Printer.printing.leftMargin = 0;       //左边距
  Printer.printing.topMargin = 10;        //上边距
  Printer.printing.rightMargin = 0;      //右边距
  Printer.printing.bottomMargin = 0;     //底边距

  Printer.printing.portrait = true;      //纵向打印:true; 横向打印:false

  Printer.printing.Print(false);         //直接打印:false; 选择打印机:true
  window.close();
  //window.opener.ReturnGetGoodsFocus();
}
</script>
</head>
<OBJECT id="Printer" style="DISPLAY: none" codeBase="<%=request.getContextPath()%>/ocx/smsx.cab#Version=6,6,440,26"
			classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" viewastext>
</OBJECT>
<body lang=ZH-CN style='tab-interval:21.0pt;text-justify-trim:punctuation'>
<div id="top">
  <div id="print" onclick="print();"><span title="打印">打印</span></div>
  <!--<div id="word" onclick="exp();"><span title="导出word文件">导出word</span></div>
  -->
  <!-- <div id="close" onclick="javascript:window.parent.parent.GB_hide();"><span title="关闭窗口">关闭</span></div> -->
  <div id="close" onclick="history.go(-1);"><span title="关闭窗口">关闭</span></div>
</div>
<div id="center" align="center">
<div id="center-top"></div>
<div id="center-cen">
<div class=Section1 style='layout-grid:15.6pt'>

<p class=MsoNormal align=center style='text-align:center'><span
style='font-size:22.0pt;font-family:华文楷体'><%=sd.getString("simpleName") %>散客报价表<span lang=EN-US><o:p></o:p></span></span></p>

<div align=center>

<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0 width=676
 style='width:506.7pt;margin-left:-.55pt;border-collapse:collapse;mso-padding-alt:
 0cm 5.4pt 0cm 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;height:14.25pt'>
  <td width=319 nowrap colspan=3 style='width:239.4pt;border:solid windowtext 1.0pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><b><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'>行程名称及天数<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
  <td width=96 nowrap style='width:72.0pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-top-alt:solid windowtext .5pt;mso-border-bottom-alt:
  solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;padding:
  0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><b><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'>发班计划<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
  <td width=110 nowrap style='width:82.3pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-top-alt:solid windowtext .5pt;mso-border-bottom-alt:
  solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;padding:
  0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><b><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'>特殊人群收费<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
  <td width=84 nowrap style='width:63.0pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-top-alt:solid windowtext .5pt;mso-border-bottom-alt:
  solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;padding:
  0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><b><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'>用车<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
  <td width=67 nowrap style='width:50.0pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-top-alt:solid windowtext .5pt;mso-border-bottom-alt:
  solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;padding:
  0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><b><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'>导游<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:1;height:28.5pt'>
  <td width=319 colspan=3 style='width:239.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:28.5pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'><%=XLMC%><br><%=TS%>日游</span></p>
  </td>
  <td width=96 nowrap style='width:72.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:28.5pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'><%=FBJH%><span lang=EN-US><o:p></o:p></span></span></p>
  </td>
  <td width=110 nowrap style='width:82.3pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:28.5pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'><%=MP%><span lang=EN-US><o:p></o:p></span></span></p>
  </td>
  <td width=84 nowrap style='width:63.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:28.5pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'><%=YC%><span lang=EN-US><o:p></o:p></span></span></p>
  </td>
  <td width=67 nowrap style='width:50.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:28.5pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'><%=DY%><span lang=EN-US><o:p></o:p></span></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:2;height:14.25pt'>
  <td width=91 nowrap style='width:68.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><b><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'>价格类型<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
  <td width=132 nowrap style='width:99.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-top-alt:solid windowtext .5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><b><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'>价格<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
  <td width=192 nowrap colspan=2 style='width:144.0pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-top-alt:solid windowtext .5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><b><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'>单房差<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
  <td width=260 nowrap colspan=3 style='width:195.3pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-top-alt:solid windowtext .5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><b><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'>备注<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
 </tr>
<%
	int rows = rd.getTableRowsCount("ta_dj_xljgs");
	for(int i=0;i<rows;i++){
		String JGLX = rd.getString("TA_DJ_XLJGs", "JGLX", i);
		String JG = rd.getString("TA_DJ_XLJGs", "JG", i);
		String dfc = rd.getString("TA_DJ_XLJGs", "dfc", i);
		String bz = rd.getString("TA_DJ_XLJGs", "bz", i);
%>
 <tr style='mso-yfti-irow:3;height:14.25pt'>
  <td width=91 nowrap style='width:68.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'><%=JGLX %><span lang=EN-US><o:p></o:p></span></span></p>
  </td>
  <td width=132 nowrap style='width:99.0pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-top-alt:solid windowtext .5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><span
  lang=EN-US style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;
  mso-font-kerning:0pt'><%=JG %><o:p></o:p></span></p>
  </td>
  <td width=192 nowrap colspan=2 style='width:144.0pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-top-alt:solid windowtext .5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><span
  lang=EN-US style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;
  mso-font-kerning:0pt'><%=dfc %><o:p></o:p></span></p>
  </td>
  <td width=260 nowrap colspan=3 style='width:195.3pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-top-alt:solid windowtext .5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan'><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'><%=bz %><span lang=EN-US><o:p></o:p></span></span></p>
  </td>
 </tr>
<%
	}
%>
 <tr style='mso-yfti-irow:4;height:14.25pt'>
  <td width=91 nowrap style='width:68.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:10.0pt;font-family:
  宋体;mso-bidi-font-family:宋体;mso-font-kerning:0pt'>购物安排<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
  <td width=584 nowrap colspan=6 style='width:438.3pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-top-alt:solid windowtext .5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan'><span
  class=GramE><span style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'><%=GW%></span></span><span lang=EN-US style='font-size:
  10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:0pt'><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:5;height:14.25pt'>
  <td width=91 nowrap style='width:68.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><b><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'>日期<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
  <td width=434 nowrap colspan=4 style='width:325.3pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-top-alt:solid windowtext .5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><b><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'>行程内容<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
  <td width=151 nowrap colspan=2 style='width:113.0pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-top-alt:solid windowtext .5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><b><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'>标准<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
 </tr>
<%
	rows = rd.getTableRowsCount("ta_dj_linemngs");
	for(int i=0;i<rows;i++){
		String RQ = rd.getStringByDI("ta_dj_linemngs","rq",i);//日期
 		String BREAKFAST = rd.getStringByDI("ta_dj_linemngs","breakfast",i);//早
 		String CMEAL = rd.getStringByDI("ta_dj_linemngs","CMEAL",i);//中
 		String SUPPER = rd.getStringByDI("ta_dj_linemngs","SUPPER",i);//晚
 		String ZS = rd.getStringByDI("ta_dj_linemngs","ZS",i);//住宿
 		String CB = rd.getStringByDI("ta_dj_linemngs","CB",i);//餐标
 		String ZSBZ = rd.getStringByDI("ta_dj_linemngs","ZSBZ",i);//住宿标准
 		
 		if ("Y".equals(BREAKFAST)) {
 			BREAKFAST = "早";
 		}
 		if ("Y".equals(CMEAL)) {
 			CMEAL = "中";
 		}
 		if ("Y".equals(SUPPER)) {
 			SUPPER = "晚";
 		}
%>
 <tr style='mso-yfti-irow:6;height:31.2pt'>
  <td width=91 nowrap style='width:68.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.2pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><span
  lang=EN-US style='font-size:22.0pt;font-family:宋体;mso-bidi-font-family:宋体;
  mso-font-kerning:0pt'><%=RQ%><o:p></o:p></span></p>
  </td>
  <td width=434 nowrap colspan=4 style='width:325.3pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:31.2pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan'><span
  lang=EN-US style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;
  mso-font-kerning:0pt'><o:p><%
 		Object obj = rd.get("ta_dj_linemngs", "XCMX", i);
 			Blob blob = null;
 			int read = 0;
 			if (null != obj) {
 				blob = (Blob) obj;
 				java.io.InputStream in = blob.getBinaryStream();
 				InputStreamReader isr = new InputStreamReader(in, "GBK");
 				char[] chars = new char[1];
 				read = 0;
 				while ((read = isr.read(chars)) != -1) {
 					out.print(new String(chars));
 				}
 			}
 	%></o:p></span></p>
  </td>
  <td width=151 colspan=2 style='width:113.0pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:31.2pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'>含餐：<%=BREAKFAST%>&nbsp;<%=CMEAL%>&nbsp;<%=SUPPER%><span lang=EN-US><o:p></o:p></span></span></p>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'>餐标：<span lang=EN-US><%=CB %></span>元<span lang=EN-US><o:p></o:p></span></span></p>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'>房标：<%=ZSBZ %><span lang=EN-US><o:p></o:p></span></span></p>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan'><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'>住宿城市：<%=ZS%><span lang=EN-US><o:p></o:p></span></span></p>
  </td>
 </tr>
<%
	}
%>
 <tr style='mso-yfti-irow:7;height:12.05pt'>
  <td width=676 nowrap colspan=7 style='width:506.7pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:12.05pt'>
  <p class=MsoNormal style='mso-pagination:widow-orphan'><b style='mso-bidi-font-weight:
  normal'><span style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>服务标准<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:8;height:12.05pt'>
  <td width=676 nowrap colspan=7 style='width:506.7pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:12.05pt'>
  <p class=MsoNormal style='mso-pagination:widow-orphan'><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'><%=fwbz %><span lang=EN-US><o:p></o:p></span></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:9;height:12.05pt'>
  <td width=676 nowrap colspan=7 style='width:506.7pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:12.05pt'>
  <p class=MsoNormal style='mso-pagination:widow-orphan'><b style='mso-bidi-font-weight:
  normal'><span style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>特别提醒<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:10;height:12.05pt'>
  <td width=676 nowrap colspan=7 style='width:506.7pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:12.05pt'>
  <p class=MsoNormal style='mso-pagination:widow-orphan'><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'><%=TBTX %><span lang=EN-US><o:p></o:p></span></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:11;height:12.05pt'>
  <td width=676 nowrap colspan=7 style='width:506.7pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:12.05pt'>
  <p class=MsoNormal style='mso-pagination:widow-orphan'><b style='mso-bidi-font-weight:
  normal'><span style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>备注<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:12;mso-yfti-lastrow:yes;height:12.05pt'>
  <td width=676 nowrap colspan=7 style='width:506.7pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:12.05pt'>
  <p class=MsoNormal style='mso-pagination:widow-orphan'><span
  style='font-size:10.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'><%=bzAll %><span lang=EN-US><o:p></o:p></span></span></p>
  </td>
 </tr>
 <![if !supportMisalignedColumns]>
 <tr height=0>
  <td width=91 style='border:none'></td>
  <td width=132 style='border:none'></td>
  <td width=96 style='border:none'></td>
  <td width=96 style='border:none'></td>
  <td width=110 style='border:none'></td>
  <td width=84 style='border:none'></td>
  <td width=67 style='border:none'></td>
 </tr>
 <![endif]>
</table>
</div>
<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

</div>

</div>
</div>
<!-- 样式底部 -->
<div align="center">
	<div  id="button" ></div>
</div>
</body>

</html>
