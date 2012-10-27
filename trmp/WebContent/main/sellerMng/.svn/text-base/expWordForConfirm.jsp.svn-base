<%@ page language="java" contentType="application/msword; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%@page import="com.dream.bizsdk.common.database.datadict.DataDict"%>
<%@page import="java.io.InputStreamReader"%>
<%
BizData rd = (BizData) request.getAttribute(SysVar.REQ_DATA);
BizData sd = (BizData) session.getAttribute(SysVar.SS_DATA);
if (sd == null) {
	String loginPage = SysVar.LOGINPAGE.replaceAll(
			SysVar.CONTEXT_PATH, request.getContextPath());
	response.sendRedirect(loginPage);
}
if (rd == null) {
	rd = new BizData();
}

String fileName="报名确认单.doc";
String sn = new String(fileName.getBytes("gb2312"),"ISO8859-1");
response.setHeader("Content-Disposition","attachment; filename="+sn);
%>
<html xmlns:v="urn:schemas-microsoft-com:vml"
xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:w="urn:schemas-microsoft-com:office:word"
xmlns:st1="urn:schemas-microsoft-com:office:smarttags"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=GBK">
<meta name=ProgId content=Word.Document>
<meta name=Generator content="Microsoft Word 11">
<meta name=Originator content="Microsoft Word 11">
<link rel=File-List href="viewBMQRD.files/filelist.xml">
<title>报名确认单</title>
<o:SmartTagType namespaceuri="urn:schemas-microsoft-com:office:smarttags"
 name="chsdate"/>
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>shen</o:Author>
  <o:LastAuthor>USER</o:LastAuthor>
  <o:Revision>2</o:Revision>
  <o:TotalTime>128</o:TotalTime>
  <o:Created>2011-04-03T08:53:00Z</o:Created>
  <o:LastSaved>2011-04-03T08:53:00Z</o:LastSaved>
  <o:Pages>1</o:Pages>
  <o:Words>90</o:Words>
  <o:Characters>514</o:Characters>
  <o:Company>shen</o:Company>
  <o:Lines>4</o:Lines>
  <o:Paragraphs>1</o:Paragraphs>
  <o:CharactersWithSpaces>603</o:CharactersWithSpaces>
  <o:Version>11.9999</o:Version>
 </o:DocumentProperties>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <w:WordDocument>
  <w:View>Print</w:View>
  <w:DontDisplayPageBoundaries/>
  <w:SpellingState>Clean</w:SpellingState>
  <w:GrammarState>Clean</w:GrammarState>
  <w:PunctuationKerning/>
  <w:DrawingGridVerticalSpacing>0 磅</w:DrawingGridVerticalSpacing>
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
<!--.FLOATLEFT
	{float:left;}
.PRINTTITLE1
	{text-aligh:center;}

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
	{font-family:"\@宋体";
	panose-1:2 1 6 0 3 1 1 1 1 1;
	mso-font-charset:134;
	mso-generic-font-family:auto;
	mso-font-pitch:variable;
	mso-font-signature:3 135135232 16 0 262145 0;}
@font-face
	{font-family:楷体_GB2312;
	panose-1:2 1 6 9 3 1 1 1 1 1;
	mso-font-charset:134;
	mso-generic-font-family:modern;
	mso-font-pitch:fixed;
	mso-font-signature:1 135135232 16 0 262144 0;}
@font-face
	{font-family:"\@楷体_GB2312";
	panose-1:2 1 6 9 3 1 1 1 1 1;
	mso-font-charset:134;
	mso-generic-font-family:modern;
	mso-font-pitch:fixed;
	mso-font-signature:1 135135232 16 0 262144 0;}
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
p.MsoDocumentMap, li.MsoDocumentMap, div.MsoDocumentMap
	{mso-style-noshow:yes;
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	background:navy;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
p
	{font-size:12.0pt;
	font-family:宋体;
	mso-bidi-font-family:宋体;}
p.printpage, li.printpage, div.printpage
	{mso-style-name:printpage;
	mso-margin-top-alt:auto;
	margin-right:0cm;
	mso-margin-bottom-alt:auto;
	margin-left:0cm;
	mso-pagination:widow-orphan;
	font-size:12.0pt;
	font-family:宋体;
	mso-bidi-font-family:宋体;}
p.printtitle, li.printtitle, div.printtitle
	{mso-style-name:printtitle;
	mso-margin-top-alt:auto;
	margin-right:0cm;
	mso-margin-bottom-alt:auto;
	margin-left:41.25pt;
	mso-pagination:widow-orphan;
	font-size:15.0pt;
	font-family:楷体_GB2312;
	mso-hansi-font-family:宋体;
	mso-bidi-font-family:宋体;}
p.printtitle1, li.printtitle1, div.printtitle1
	{mso-style-name:printtitle1;
	margin-top:3.75pt;
	margin-right:0cm;
	mso-margin-bottom-alt:auto;
	margin-left:0cm;
	mso-pagination:widow-orphan;
	font-size:13.5pt;
	font-family:宋体;
	mso-bidi-font-family:宋体;}
p.printtable, li.printtable, div.printtable
	{mso-style-name:printtable;
	mso-margin-top-alt:auto;
	margin-right:0cm;
	mso-margin-bottom-alt:auto;
	margin-left:0cm;
	mso-pagination:widow-orphan;
	word-break:break-all;
	border:none;
	mso-border-alt:solid black .75pt;
	padding:0cm;
	mso-padding-alt:0cm 0cm 0cm 0cm;
	font-size:12.0pt;
	font-family:宋体;
	mso-bidi-font-family:宋体;}
p.printfontsize, li.printfontsize, div.printfontsize
	{mso-style-name:printfontsize;
	mso-margin-top-alt:auto;
	margin-right:0cm;
	mso-margin-bottom-alt:auto;
	margin-left:0cm;
	mso-pagination:widow-orphan;
	font-size:9.0pt;
	font-family:宋体;
	mso-bidi-font-family:宋体;}
p.printsell, li.printsell, div.printsell
	{mso-style-name:printsell;
	mso-margin-top-alt:auto;
	margin-right:0cm;
	mso-margin-bottom-alt:auto;
	margin-left:0cm;
	text-align:right;
	mso-pagination:widow-orphan;
	font-size:9.0pt;
	font-family:宋体;
	mso-bidi-font-family:宋体;}
p.printnotable, li.printnotable, div.printnotable
	{mso-style-name:printnotable;
	mso-margin-top-alt:auto;
	margin-right:0cm;
	mso-margin-bottom-alt:auto;
	margin-left:0cm;
	mso-pagination:widow-orphan;
	border:none;
	mso-border-alt:solid gray .25pt;
	padding:0cm;
	mso-padding-alt:0cm 0cm 0cm 0cm;
	font-size:12.0pt;
	font-family:宋体;
	mso-bidi-font-family:宋体;}
p.bt, li.bt, div.bt
	{mso-style-name:bt;
	mso-margin-top-alt:auto;
	margin-right:0cm;
	mso-margin-bottom-alt:auto;
	margin-left:0cm;
	mso-pagination:widow-orphan;
	font-size:9.0pt;
	font-family:宋体;
	mso-bidi-font-family:宋体;
	color:darkred;
	font-weight:bold;}
p.contable, li.contable, div.contable
	{mso-style-name:contable;
	mso-margin-top-alt:auto;
	margin-right:0cm;
	mso-margin-bottom-alt:auto;
	margin-left:0cm;
	mso-pagination:widow-orphan;
	border:none;
	mso-border-alt:solid gray .75pt;
	padding:0cm;
	mso-padding-alt:0cm 0cm 0cm 0cm;
	font-size:12.0pt;
	font-family:宋体;
	mso-bidi-font-family:宋体;}
p.contableuser, li.contableuser, div.contableuser
	{mso-style-name:contableuser;
	mso-margin-top-alt:auto;
	margin-right:0cm;
	mso-margin-bottom-alt:auto;
	margin-left:0cm;
	mso-pagination:widow-orphan;
	border:none;
	mso-border-alt:solid gray .25pt;
	padding:0cm;
	mso-padding-alt:0cm 0cm 0cm 0cm;
	font-size:12.0pt;
	font-family:宋体;
	mso-bidi-font-family:宋体;}
p.ptable, li.ptable, div.ptable
	{mso-style-name:ptable;
	mso-margin-top-alt:auto;
	margin-right:0cm;
	mso-margin-bottom-alt:auto;
	margin-left:0cm;
	mso-pagination:widow-orphan;
	font-size:12.0pt;
	font-family:宋体;
	mso-bidi-font-family:宋体;}
p.bottominfo, li.bottominfo, div.bottominfo
	{mso-style-name:bottominfo;
	mso-margin-top-alt:auto;
	margin-right:0cm;
	mso-margin-bottom-alt:auto;
	margin-left:0cm;
	mso-pagination:widow-orphan;
	font-size:10.0pt;
	font-family:楷体_GB2312;
	mso-hansi-font-family:宋体;
	mso-bidi-font-family:宋体;}
p.tripimg, li.tripimg, div.tripimg
	{mso-style-name:tripimg;
	mso-margin-top-alt:auto;
	margin-right:0cm;
	mso-margin-bottom-alt:auto;
	margin-left:0cm;
	mso-pagination:widow-orphan;
	font-size:12.0pt;
	font-family:宋体;
	mso-bidi-font-family:宋体;}
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
	mso-paper-source:0;}
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
	mso-fareast-font-family:"Times New Roman";
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
<![endif]--><span id=lblTitleName></span><colgroup><col style="width: 50%" /><col style="width: 50%" /></colgroup><span
style='float:left'>
<label>
</label>
</span><span id=MemberName></span><span id=lblLinkMan></span><span id=lblPhone></span><span
id=lblTax></span><colgroup><col style="width: 50%" /><col style="width: 50%" /></colgroup><span
style='float:left'>
<label>
</label>
</span><span id=lbCompanyName></span><span id=lbLineName></span><span
id=lbMobile></span><span id=lbPhone></span><span id=lbFax></span><span
id=lbMSN></span><colgroup><col style="width: 50%" /><col style="width: 50%" /></colgroup>
<label>
</label>
<span id=OrderNo></span>
<label>
</label>
<span id=CreateTime></span>
<label>
</label>
<span id=LineName></span>
<label>
</label>
<span id=StartTime></span>
<label>
</label>
<span id=lblVisitorMan></span>
<label>
</label>
<span id=lblVisitorPhone></span>
<label>
</label>
<span id=ClientInfo></span><span id=tdSeatNo><span style='float:left'>
<label>
</label>
<span id=lblSeatNo></span></span></span>
<label>
</label>
<span id=lblStation></span>
<label>
</label>
<span id=lblStationTime></span>
<label>
</label>
<span id=StartTraffic></span>
<label>
</label>
<span id=EndTraffic></span><span style='float:left'>
<label>
</label>
<span id=dAmountConcact></span><span id=lblAlreadyPay></span></span><span
style='float:left'>
<label>
</label>
</span><span id=OrderRemark></span><colgroup><col width="80" /><col width="50" /><col width="60" /><col /><col width="120px" /><col width="100px" /></colgroup><span
style='float:left'>
<label>
</label>
</span><colgroup><col style='width:11%'/><col style='width:10%'/><col  style='width:20%'/><col style='width:10%'/><col style='width:20%'/><col style='width:10%'/><col /></colgroup><span
style='float:left'>
<label>
</label>
</span>
<label>
</label>
<span style='float:left'>
<label>
</label>
</span><span id=lbService></span><span style='float:left'>
<label>
</label>
</span><span id=lbSpecialRemark></span><span style='float:left'>
<label>
</label>
</span><span id=lblAccount></span><!--[if gte mso 9]><xml>
 <o:shapedefaults v:ext="edit" spidmax="3074"/>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <o:shapelayout v:ext="edit">
  <o:idmap v:ext="edit" data="1"/>
 </o:shapelayout></xml><![endif]-->
 
 <style type="text/css">
	body{margin: 0;padding: 0;font-size: 12px;}
	#top{background: url(../../images/ToolBarBg.gif) repeat-x;height: 35px;text-align: center;line-height: 35px;}
	#print{background: url(../../images/Print.gif) no-repeat 0 7px;width: 55px;text-indent: 15px;float: left;cursor: pointer;}
	#word{background: url(../../images/word.gif) no-repeat 0 7px;width: 82px;text-indent: 15px;float: left;cursor: pointer;}
	#close{background: url(../../images/printClose.gif) no-repeat 0 11px;width: 49px;text-indent: 10px;float: left;cursor: pointer;}
	#center-top{background: url(../../images/body_04.jpg) no-repeat;width: 793px;height: 33px;text-align: center}
	#center-cen{background: url(../../images/body_05.jpg) repeat-y;width: 793px;}
	#button{background: url(../../images/body_06.jpg) no-repeat;width: 793px;}
 </style>
</head>

<body lang=ZH-CN style='tab-interval:21.0pt'>
<div class=Section1>

<div style='position:relative'>

<h5 align=center style='margin-top:3.75pt;text-align:center'><span
style='font-size:13.5pt'>报名确认单<span lang=EN-US><o:p></o:p></span></span></h5>

</div>

<h3 align=center style='margin-left:41.25pt;text-align:center'><span
style='font-size:15.0pt;font-family:楷体_GB2312'><%=rd.getStringByDI("rsOBForView","line_name",0) %>
<span lang=EN-US><o:p></o:p></span></span></h3>

<div id=Content>

<div align=center>

<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;border:none;mso-border-alt:solid black .75pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;height:15.0pt'>
  <td colspan=2 style='border:solid black 1.0pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>报名社信息</span></b><span
  style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'> <span lang=EN-US><o:p></o:p></span></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:1;height:15.0pt'>
  <td style='border:solid black 1.0pt;border-top:none;mso-border-top-alt:solid black .75pt;
  mso-border-alt:solid black .75pt;padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>报名社：<%=rd.getStringByDI("rsOBForView","cmpny_name",0) %>
  <span lang=EN-US><o:p></o:p></span></span></p>
  </td>
  <td style='border-top:none;border-left:none;border-bottom:solid black 1.0pt;
  border-right:solid black 1.0pt;mso-border-top-alt:solid black .75pt;
  mso-border-left-alt:solid black .75pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>联系人： <%=rd.getStringByDI("rsOBForView","business_name",0) %> <span lang=EN-US><o:p></o:p></span></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:2;height:15.0pt'>
  <td style='border:solid black 1.0pt;border-top:none;mso-border-top-alt:solid black .75pt;
  mso-border-alt:solid black .75pt;padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>电 话：<span lang=EN-US> <%=rd.getStringByDI("rsOBForView","business_tel",0) %> <o:p></o:p></span></span></p>
  </td>
  <td style='border-top:none;border-left:none;border-bottom:solid black 1.0pt;
  border-right:solid black 1.0pt;mso-border-top-alt:solid black .75pt;
  mso-border-left-alt:solid black .75pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>传 真：<span lang=EN-US> <%=rd.getStringByDI("rsOBForView","business_fax",0) %> <o:p></o:p></span></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:3;height:15.0pt'>
  <td colspan=2 style='border:solid black 1.0pt;border-top:none;mso-border-top-alt:
  solid black .75pt;mso-border-alt:solid black .75pt;padding:.75pt .75pt .75pt .75pt;
  height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>日 期：<%=rd.getStringByDI("rsOBForView","regedit_time",0) %>
  <span lang=EN-US><o:p></o:p></span></span></p>
  </td>
 </tr>
</table>

</div>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<div align=center>

<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;border:none;mso-border-left-alt:
 solid black .25pt;mso-border-right-alt:solid black .25pt;mso-padding-alt:0cm 0cm 0cm 0cm'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;height:15.0pt'>
  <td style='border:solid black 1.0pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>订单编号：</span></b><span
  lang=EN-US style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:宋体;
  mso-font-kerning:0pt'> <%=rd.getStringByDI("rsOBForView","ddh",0) %> <o:p></o:p></span></p>
  </td>
  <td style='border:solid black 1.0pt;border-left:none;mso-border-left-alt:
  solid black .75pt;mso-border-alt:solid black .75pt;padding:.75pt .75pt .75pt .75pt;
  height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>报名时间：</span></b><%=rd.getStringByDI("rsOBForView","regedit_time",0) %><span
  style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'> <span lang=EN-US><o:p></o:p></span></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:1;height:15.0pt'>
  <td style='border:solid black 1.0pt;border-top:none;mso-border-top-alt:solid black .75pt;
  mso-border-alt:solid black .75pt;padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>线 路：</span></b><span
  style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'> <%=rd.getStringByDI("rsOBForView","line_name",0) %> <span lang=EN-US><o:p></o:p></span></span></p>
  </td>
  <td style='border-top:none;border-left:none;border-bottom:solid black 1.0pt;
  border-right:solid black 1.0pt;mso-border-top-alt:solid black .75pt;
  mso-border-left-alt:solid black .75pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>出团日期：</span></b><%=rd.getStringByDI("rsOBForView","begin_date",0) %><span
  style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'> <span lang=EN-US><o:p></o:p></span></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:2;height:15.0pt'>
  <td style='border:solid black 1.0pt;border-top:none;mso-border-top-alt:solid black .75pt;
  mso-border-alt:solid black .75pt;padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>联系人：</span></b><span
  style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'> <%=rd.getStringByDI("rsOBForView","visitor_nm",0) %> <span lang=EN-US><o:p></o:p></span></span></p>
  </td>
  <td style='border-top:none;border-left:none;border-bottom:solid black 1.0pt;
  border-right:solid black 1.0pt;mso-border-top-alt:solid black .75pt;
  mso-border-left-alt:solid black .75pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>联系电话：</span></b><span
  lang=EN-US style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:宋体;
  mso-font-kerning:0pt'> <%=rd.getStringByDI("rsOBForView","tel",0) %> <o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:3;height:15.0pt'>
  <td style='border:solid black 1.0pt;border-top:none;mso-border-top-alt:solid black .75pt;
  mso-border-alt:solid black .75pt;padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>人 数：</span></b><span
  style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'> <%=rd.getStringByDI("rsOBForView","ysrs",0) %>个 <span lang=EN-US><o:p></o:p></span></span></p>
  </td>
  <td style='border-top:none;border-left:none;border-bottom:solid black 1.0pt;
  border-right:solid black 1.0pt;mso-border-top-alt:solid black .75pt;
  mso-border-left-alt:solid black .75pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>座位编号：</span></b><span
  lang=EN-US style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:宋体;
  mso-font-kerning:0pt'>  <%
	int rows = rd.getTableRowsCount("rsVisitors");
	String seatNumber = "";
	for(int i=0;i<rows;i++){
		
		seatNumber += rd.getStringByDI("rsVisitors","SEAT_NUM",i)+"，";
	}
	out.print(seatNumber);
%> <o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:4;height:15.0pt' id=StationAndTime>
  <td style='border:solid black 1.0pt;border-top:none;mso-border-top-alt:solid black .75pt;
  mso-border-alt:solid black .75pt;padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>集合地点：</span></b><span
  style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'> <%=rd.getStringByDI("rsOBForView","gather",0) %> <span lang=EN-US><o:p></o:p></span></span></p>
  </td>
  <td style='border-top:none;border-left:none;border-bottom:solid black 1.0pt;
  border-right:solid black 1.0pt;mso-border-top-alt:solid black .75pt;
  mso-border-left-alt:solid black .75pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>集合时间：</span></b><span
  lang=EN-US style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:宋体;
  mso-font-kerning:0pt'> <%=rd.getStringByDI("rsOBForView","gather_time",0) %></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:5;height:15.0pt' id=TrafficBox>
  <td style='border:solid black 1.0pt;border-top:none;mso-border-top-alt:solid black .75pt;
  mso-border-alt:solid black .75pt;padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>出发交通：</span></b><span
  style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'> <%
  String b_traffic = rd.getStringByDI("rsOBForView","b_traffic",0);
  for(int i=0;i<rd.getTableRowsCount("JTGJ");i++){
	  String dmz = rd.getStringByDI("JTGJ","dmz",i);
	  String dmsm = rd.getStringByDI("JTGJ","dmsm1",i);
	  if(dmz.equals(b_traffic)){
%>
	<%=dmsm %>
<%    }
  }
%> 
<span lang=EN-US><o:p></o:p></span></span></p>
  </td>
  <td style='border-top:none;border-left:none;border-bottom:solid black 1.0pt;
  border-right:solid black 1.0pt;mso-border-top-alt:solid black .75pt;
  mso-border-left-alt:solid black .75pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>返回交通：</span></b><span
  style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'> <%
  String e_traffic = rd.getStringByDI("rsOBForView","e_traffic",0);
  for(int i=0;i<rd.getTableRowsCount("JTGJ");i++){
	  String dmz = rd.getStringByDI("JTGJ","dmz",i);
	  String dmsm = rd.getStringByDI("JTGJ","dmsm1",i);
	  if(dmz.equals(e_traffic)){
%>
	<%=dmsm %>
<%    }
  }
%>  <span lang=EN-US><o:p></o:p></span></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:6;mso-yfti-lastrow:yes;height:15.0pt' id=tbPrice>
  <td colspan=2 style='border:solid black 1.0pt;border-top:none;mso-border-top-alt:
  solid black .75pt;mso-border-alt:solid black .75pt;padding:.75pt .75pt .75pt .75pt;
  height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>价格明细：</span></b><span
  style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
  0pt'> 
<%
int all = 0;
for(int i=0;i<rd.getTableRowsCount("rsPersonPrice");i++){
	String priceType = rd.getStringByDI("rsPersonPrice","pt",i);
	String price = rd.getStringByDI("rsPersonPrice","price_ms",i);
	String persons = rd.getStringByDI("rsPersonPrice","person_count",i);
	String he = rd.getStringByDI("rsPersonPrice","he",i);
	all += Integer.parseInt(he);
%>
  <span lang=EN-US>(</span><%=priceType %><span lang=EN-US>) <%=price %></span>元<span lang=EN-US>/</span>人<span lang=EN-US> * <%=persons %> = <%=he %></span>元<span lang=EN-US>;<br></span>
  
<%
}%>
<b>总计</b>：<span lang=EN-US>&nbsp;<%=all %></span>元<span lang=EN-US> &nbsp;&nbsp;</span><b>已付款</b>：<span lang=EN-US><%=rd.getStringByDI("rsOBForView","yi_sk",0) %></span>元 <span lang=EN-US><o:p></o:p></span>
  </span></p>
  </td>
 </tr>
</table>

</div>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<div align=center>

<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;border:none;mso-border-alt:solid black .75pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;height:15.0pt'>
  <td colspan=2 style='border:solid black 1.0pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>▲订单备注 <span lang=EN-US><o:p></o:p></span></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:1;mso-yfti-lastrow:yes;height:15.0pt'>
  <td style='border:solid black 1.0pt;border-top:none;mso-border-top-alt:solid black .75pt;
  mso-border-alt:solid black .75pt;padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'><%=rd.getStringByDI("rsOBForView","remark",0) %></span></p>
  </td>
  <td style='border-top:none;border-left:none;border-bottom:solid black 1.0pt;
  border-right:solid black 1.0pt;mso-border-bottom-alt:solid black .75pt;
  mso-border-right-alt:solid black .75pt;padding:.75pt .75pt .75pt .75pt;
  height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan'><span
  lang=EN-US style='font-size:10.0pt;mso-font-kerning:0pt'><o:p>&nbsp;</o:p></span></p>
  </td>
 </tr>
</table>

</div>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<div id=trCusInfo>

<div align=center>

<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;border:none;mso-border-alt:solid black .75pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;height:15.0pt'>
  <td colspan=6 style='border:solid black 1.0pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>▲游客信息 <span lang=EN-US><o:p></o:p></span></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:1;height:15.0pt'>
  <td style='border:solid black 1.0pt;border-top:none;mso-border-top-alt:solid black .75pt;
  mso-border-alt:solid black .75pt;padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>姓名<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
  <td style='border-top:none;border-left:none;border-bottom:solid black 1.0pt;
  border-right:solid black 1.0pt;mso-border-top-alt:solid black .75pt;
  mso-border-left-alt:solid black .75pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>性别<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
  <td style='border-top:none;border-left:none;border-bottom:solid black 1.0pt;
  border-right:solid black 1.0pt;mso-border-top-alt:solid black .75pt;
  mso-border-left-alt:solid black .75pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>类型<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
  <td style='border-top:none;border-left:none;border-bottom:solid black 1.0pt;
  border-right:solid black 1.0pt;mso-border-top-alt:solid black .75pt;
  mso-border-left-alt:solid black .75pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>证件号<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
  <td style='border-top:none;border-left:none;border-bottom:solid black 1.0pt;
  border-right:solid black 1.0pt;mso-border-top-alt:solid black .75pt;
  mso-border-left-alt:solid black .75pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>电话<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
  <td style='border-top:none;border-left:none;border-bottom:solid black 1.0pt;
  border-right:solid black 1.0pt;mso-border-top-alt:solid black .75pt;
  mso-border-left-alt:solid black .75pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan;
  word-break:break-all'><b><span style='font-size:9.0pt;font-family:宋体;
  mso-bidi-font-family:宋体;mso-font-kerning:0pt'>需要保险<span lang=EN-US><o:p></o:p></span></span></b></p>
  </td>
 </tr>
<%
for(int i=0;i<rd.getTableRowsCount("rsVisitors");i++){
%>
 <tr style='mso-yfti-irow:2;height:15.0pt'>
  <td style='border:solid black 1.0pt;border-top:none;mso-border-top-alt:solid black .75pt;
  mso-border-alt:solid black .75pt;padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan;
  word-break:break-all'><span lang=EN-US style='font-size:9.0pt;font-family:
  宋体;mso-bidi-font-family:宋体;mso-font-kerning:0pt'><o:p><%=rd.getStringByDI("rsVisitors","visitor_nm",i) %></o:p></span></p>
  </td>
  <td style='border-top:none;border-left:none;border-bottom:solid black 1.0pt;
  border-right:solid black 1.0pt;mso-border-top-alt:solid black .75pt;
  mso-border-left-alt:solid black .75pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan;
  word-break:break-all'><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'><%=rd.getStringByDI("rsVisitors","xb",i) %> <span lang=EN-US><o:p></o:p></span></span></p>
  </td>
  <td style='border-top:none;border-left:none;border-bottom:solid black 1.0pt;
  border-right:solid black 1.0pt;mso-border-top-alt:solid black .75pt;
  mso-border-left-alt:solid black .75pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan;
  word-break:break-all'><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'><%=rd.getStringByDI("rsVisitors","ischild",i) %> <span lang=EN-US><o:p></o:p></span></span></p>
  </td>
  <td style='border-top:none;border-left:none;border-bottom:solid black 1.0pt;
  border-right:solid black 1.0pt;mso-border-top-alt:solid black .75pt;
  mso-border-left-alt:solid black .75pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><span lang=EN-US style='font-size:9.0pt;font-family:
  宋体;mso-bidi-font-family:宋体;mso-font-kerning:0pt'><o:p><%=rd.getStringByDI("rsVisitors","license_no",i) %></o:p></span></p>
  </td>
  <td style='border-top:none;border-left:none;border-bottom:solid black 1.0pt;
  border-right:solid black 1.0pt;mso-border-top-alt:solid black .75pt;
  mso-border-left-alt:solid black .75pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=left style='text-align:left;mso-pagination:widow-orphan;
  word-break:break-all'><span lang=EN-US style='font-size:9.0pt;font-family:
  宋体;mso-bidi-font-family:宋体;mso-font-kerning:0pt'><o:p><%=rd.getStringByDI("rsVisitors","tel",i) %></o:p></span></p>
  </td>
  <td style='border-top:none;border-left:none;border-bottom:solid black 1.0pt;
  border-right:solid black 1.0pt;mso-border-top-alt:solid black .75pt;
  mso-border-left-alt:solid black .75pt;mso-border-alt:solid black .75pt;
  padding:.75pt .75pt .75pt .75pt;height:15.0pt'>
  <p class=MsoNormal align=center style='text-align:center;mso-pagination:widow-orphan;
  word-break:break-all'><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'><%=rd.getStringByDI("rsVisitors","isinsurance",i) %> <span lang=EN-US><o:p></o:p></span></span></p>
  </td>
 </tr>
<%
}%>
</table>

</div>

</div>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<div align=center>

<%
				Object obj = rd.get("rsOBForView", "line_detail",0);
				oracle.sql.BLOB blob = null;
				int read=0;
				if(null != obj){
					blob = (oracle.sql.BLOB) obj;
					java.io.InputStream in=blob.getBinaryStream();
					InputStreamReader isr = new InputStreamReader(in, "GBK");
					char[] chars = new char[1];
					read = 0;
					while ((read = isr.read(chars)) != -1) {
						out.print(new String(chars));
					}
				}
				%>

</div>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

</div>

</div>

</body>

</html>
