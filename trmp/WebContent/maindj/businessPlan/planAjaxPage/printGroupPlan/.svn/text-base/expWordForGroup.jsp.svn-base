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

	String fileName="出团任务单.doc";
	String sn=new String(fileName.getBytes("gb2312"),"ISO-8859-1");
	response.setHeader("Content-Disposition","attachment;filename="+sn);
%>

<head>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<meta name=ProgId content=Word.Document>
<meta name=Generator content="Microsoft Word 11">
<meta name=Originator content="Microsoft Word 11">
<link rel=File-List href="出团任务单模板1.files/filelist.xml">
<title>南京皇家国际旅行社有限公司出团任务单</title>
<o:SmartTagType namespaceuri="urn:schemas-microsoft-com:office:smarttags"
 name="chsdate"/>
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>ZHANG</o:Author>
  <o:LastAuthor>ZHANG</o:LastAuthor>
  <o:Revision>3</o:Revision>
  <o:TotalTime>97</o:TotalTime>
  <o:Created>2011-09-15T06:27:00Z</o:Created>
  <o:LastSaved>2011-09-15T06:35:00Z</o:LastSaved>
  <o:Pages>1</o:Pages>
  <o:Words>127</o:Words>
  <o:Characters>725</o:Characters>
  <o:Lines>6</o:Lines>
  <o:Paragraphs>1</o:Paragraphs>
  <o:CharactersWithSpaces>851</o:CharactersWithSpaces>
  <o:Version>11.9999</o:Version>
 </o:DocumentProperties>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <w:WordDocument>
  <w:Zoom>110</w:Zoom>
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
span.SpellE
	{mso-style-name:"";
	mso-spl-e:yes;}
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
<![endif]--><!--[if gte mso 9]><xml>
 <o:shapedefaults v:ext="edit" spidmax="3074"/>
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
</head>

<body lang=ZH-CN style='tab-interval:21.0pt;text-justify-trim:punctuation'>

<div class=Section1 style='layout-grid:15.6pt'>

<div id=center-cen>

<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:
normal'><span style='font-size:15.0pt;font-family:华文楷体'>南京皇家国际旅行社有限公司出团任务单<span
lang=EN-US><o:p></o:p></span></span></b></p>

<table class=MsoTableGrid border=1 cellspacing=0 cellpadding=0 width=571
 style='width:428.4pt;margin-left:86.15pt;border-collapse:collapse;border:none;
 mso-border-alt:solid windowtext .5pt;mso-yfti-tbllook:480;mso-padding-alt:
 0cm 5.4pt 0cm 5.4pt;mso-border-insideh:.5pt solid windowtext;mso-border-insidev:
 .5pt solid windowtext'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes'>
  <td width=194 colspan=7 valign=top style='width:145.35pt;border:solid windowtext 1.0pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>组团社名：</span></b><span class=SpellE><span
  lang=EN-US style='font-size:9.0pt'>南京皇家国际旅行社</span></span><span lang=EN-US
  style='font-size:9.0pt'><o:p></o:p></span></p>
  </td>
  <td width=175 colspan=9 valign=top style='width:131.2pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
  solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>团号：</span></b><span class=SpellE><span
  lang=EN-US style='font-size:9.0pt'><%=rd.getStringByDI("groupList","ID",0) %></span></span><span lang=EN-US
  style='font-size:9.0pt'><o:p></o:p></span></p>
  </td>
  <td width=101 colspan=5 valign=top style='width:75.85pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
  solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>成人：</span></b><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("groupList","CRRS",0) %><o:p></o:p></span></p>
  </td>
  <td width=101 colspan=3 valign=top style='width:76.0pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
  solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>儿童：</span></b><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("groupList","ETRS",0) %><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:1'>
  <td width=261 colspan=9 valign=top style='width:195.45pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>导游：</span></b><span class=SpellE><span
  lang=EN-US style='font-size:9.0pt'>
  	<%
  		for(int i=0;i<rd.getTableRowsCount("guideList");i++){
  	%>
  	<%=rd.getStringByDI("guideList","DYXM",i) %>  
  	<%} %>
  </span></span><span lang=EN-US
  style='font-size:9.0pt'><o:p></o:p></span></p>
  </td>
  <td width=108 colspan=7 valign=top style='width:81.1pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>全陪人数：</span></b><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("groupList","QPRS",0) %></span><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>人</span><span
  lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></p>
  </td>
  <td width=101 colspan=5 valign=top style='width:75.85pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>姓名：</span></b><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'><%=rd.getStringByDI("groupList","QPXM",0) %></span><span style='font-size:
  9.0pt'> <span lang=EN-US><o:p></o:p></span></span></p>
  </td>
  <td width=101 colspan=3 valign=top style='width:76.0pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>电话：</span></b><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("groupList","QPDH",0) %><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:2;height:14.5pt'>
  <td width=571 colspan=24 valign=top style='width:428.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.5pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>备注：</span></b><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("groupList","TSYQ",0) %><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:3'>
  <td width=571 colspan=24 valign=top style='width:428.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>用车合计：</span></b><b style='mso-bidi-font-weight:
  normal'><span style='font-size:9.0pt'> </span></b><b style='mso-bidi-font-weight:
  normal'><span style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman"'>现付：</span></b><span
  lang=EN-US style='font-size:9.0pt'><%=rd.getStringByDI("TA_TDJDXXZJBs","XFCLZJ",0) %><span
  style='mso-spacerun:yes'>&nbsp;&nbsp; </span><b style='mso-bidi-font-weight:
  normal'>+ </b></span><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>签单：</span></b><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("TA_TDJDXXZJBs","QDCLZJ",0) %> <b style='mso-bidi-font-weight:normal'><span
  style='mso-spacerun:yes'>&nbsp;</span>=</b><%=rd.getStringByDI("TA_TDJDXXZJBs","CLZJ",0) %><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:4'>
  <td width=82 colspan=2 valign=top style='width:61.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>车辆类型</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=72 colspan=3 valign=top style='width:53.7pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>车队名称</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=122 colspan=6 valign=top style='width:91.6pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>接团时间及地点</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=57 colspan=4 valign=top style='width:65pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>车号</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=119 colspan=5 valign=top style='width:65pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>司机及电话</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=62 colspan=3 valign=top style='width:46.15pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>现付</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=58 valign=top style='width:43.5pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>签单</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
 </tr>
 <%
	for(int j=0;j<rd.getTableRowsCount("carList");j++){
%>
 <tr style='mso-yfti-irow:5'>
  <td width=82 colspan=2 valign=top style='width:61.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("carList","dmsm1",j) %><o:p></o:p></span></p>
  </td>
  <td width=72 colspan=3 valign=top style='width:53.7pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("carList","cmpny_name",j) %><o:p></o:p></span></p>
  </td>
  <td width=122 colspan=6 valign=top style='width:91.6pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("carList","BEGINTIME",j) %>-<%=rd.getStringByDI("carList","JTDD",j) %><o:p></o:p></span></p>
  </td>
  <td width=57 colspan=4 valign=top style='width:65pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("carList","car_code",j) %><o:p></o:p></span></p>
  </td>
  <td width=119 colspan=5 valign=top style='width:65pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("carList","sjxm",j) %>-<%=rd.getStringByDI("carList","sjdh",j) %><o:p></o:p></span></p>
  </td>
  <td width=62 colspan=3 valign=top style='width:46.15pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("carList","xfje",j) %><o:p></o:p></span></p>
  </td>
  <td width=58 valign=top style='width:43.5pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("carList","qdje",j) %><o:p></o:p></span></p>
  </td>
 </tr>
 <%} %>
 <tr style='mso-yfti-irow:6'>
  <td width=571 colspan=24 valign=top style='width:428.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>用房合计：现付：</span></b><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("TA_TDJDXXZJBs","XFZSZJ",0) %><span style='mso-spacerun:yes'>&nbsp;
  </span><b style='mso-bidi-font-weight:normal'><span
  style='mso-spacerun:yes'>&nbsp;</span>+ </b></span><b style='mso-bidi-font-weight:
  normal'><span style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman"'>签单：</span></b><span
  lang=EN-US style='font-size:9.0pt'><%=rd.getStringByDI("TA_TDJDXXZJBs","QDZSZJ",0) %><span
  style='mso-spacerun:yes'>&nbsp; </span>=<%=rd.getStringByDI("TA_TDJDXXZJBs","ZSZJ",0) %><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:7'>
  <td width=82 colspan=2 valign=top style='width:61.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>日期</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=72 colspan=3 valign=top style='width:53.7pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>城市</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=122 colspan=6 valign=top style='width:105pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>酒店名称及电话</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=93 colspan=9 valign=top style='width:105pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>房价*房间数*入住天数</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=76 colspan=2 valign=top style='width:57.0pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>现付</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=76 colspan=2 valign=top style='width:56.65pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>签单</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
 </tr>
 <%
 	for(int k=0;k<rd.getTableRowsCount("hotelList");k++){
 		String id=rd.getStringByDI("hotelList","ID",k);
 %>
 <tr style='mso-yfti-irow:8'>
  <td width=82 colspan=2 valign=top style='width:61.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("hotelList","rz_time",k) %><o:p></o:p></span></p>
  </td>
  <td width=72 colspan=3 valign=top style='width:53.7pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("hotelList","city",k) %><o:p></o:p></span></p>
  </td>
  <td width=122 colspan=6 valign=top style='width:105pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("hotelList","hotel_name",k) %>
  -<%=rd.getStringByDI("hotelList","hotel_bussiness_phone",k) %><o:p></o:p></span></p>
  </td>
  <td width=93 colspan=9 valign=top style='width:105pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'>
  <%
  	for(int l=0;l<rd.getTableRowsCount("hotelJgList");l++){
  		String jdjhid=rd.getStringByDI("hotelJgList","JDJHID",l);
  		if(id.equals(jdjhid)){
  	%>
  <%=rd.getStringByDI("hotelJgList","dmsm1",l)%>:<%=rd.getStringByDI("hotelJgList","jg",l) %>*<%=rd.getStringByDI("hotelJgList","fjs",l)%>*<%=rd.getStringByDI("hotelJgList","ts",l) %> <br/>
  <%
 	}}
  %>
  <o:p></o:p></span></p>
  </td>
  <td width=76 colspan=2 valign=top style='width:57.0pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("hotelList","xfxj",k) %><o:p></o:p></span></p>
  </td>
  <td width=76 colspan=2 valign=top style='width:56.65pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("hotelList","qdxj",k) %><o:p></o:p></span></p>
  </td>
 </tr>
 <%} %>
 <tr style='mso-yfti-irow:9'>
  <td width=571 colspan=24 valign=top style='width:428.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>餐费合计：现付：</span></b><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("TA_TDJDXXZJBs","XFCTZJ",0) %><span style='mso-spacerun:yes'>&nbsp;&nbsp;
  </span><b style='mso-bidi-font-weight:normal'>+ </b></span><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>签单：</span></b><span
  lang=EN-US style='font-size:9.0pt'><%=rd.getStringByDI("TA_TDJDXXZJBs","QDCTZJ",0) %><span
  style='mso-spacerun:yes'>&nbsp; </span>=<%=rd.getStringByDI("TA_TDJDXXZJBs","CTZJ",0) %><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:10'>
  <td width=70 valign=top style='width:52.5pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>地区</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=72 colspan=2 valign=top style='width:53.65pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>餐厅名</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=134 colspan=9 valign=top style='width:100.55pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>早：价格</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'>*</span></b><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>人数</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'>*</span></b><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>餐数</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=144 colspan=8 valign=top style='width:108.05pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>正：价格</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'>*</span></b><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>人数</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'>*</span></b><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>餐数</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=76 colspan=2 valign=top style='width:57.0pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>现付</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=76 colspan=2 valign=top style='width:56.65pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>签单</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
 </tr>
 <%
 	for(int m=0;m<rd.getTableRowsCount("dinnerList");m++){
 %>
 <tr style='mso-yfti-irow:11'>
  <td width=70 valign=top style='width:52.5pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("dinnerList","city",m) %><o:p></o:p></span></p>
  </td>
  <td width=72 colspan=2 valign=top style='width:53.65pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("dinnerList","cmpny_name",m) %><o:p></o:p></span></p>
  </td>
  <td width=134 colspan=9 valign=top style='width:100.55pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("dinnerList","zcjg",m) %>*<%=rd.getStringByDI("dinnerList","zcrs",m) %>*<%=rd.getStringByDI("dinnerList","zccs",m) %><o:p></o:p></span></p>
  </td>
  <td width=144 colspan=8 valign=top style='width:108.05pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("dinnerList","zhcjg",m) %>*<%=rd.getStringByDI("dinnerList","zhcrs",m) %>*<%=rd.getStringByDI("dinnerList","zhccs",m) %><o:p></o:p></span></p>
  </td>
  <td width=76 colspan=2 valign=top style='width:57.0pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("dinnerList","xfxjje",m) %><o:p></o:p></span></p>
  </td>
  <td width=76 colspan=2 valign=top style='width:56.65pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("dinnerList","qdxjje",m) %><o:p></o:p></span></p>
  </td>
 </tr>
 <%} %>
 <tr style='mso-yfti-irow:12'>
  <td width=571 colspan=24 valign=top style='width:428.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>景点合计：现付：</span></b><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("TA_TDJDXXZJBs","XFJDZJ",0) %><span style='mso-spacerun:yes'>&nbsp;&nbsp;
  </span><b style='mso-bidi-font-weight:normal'>+ </b></span><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>签单：</span></b><span
  lang=EN-US style='font-size:9.0pt'><%=rd.getStringByDI("TA_TDJDXXZJBs","QDJDZJ",0) %><span
  style='mso-spacerun:yes'>&nbsp; </span>=<%=rd.getStringByDI("TA_TDJDXXZJBs","JDZJ",0) %><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:13'>
  <td width=142 colspan=4 valign=top style='width:106.15pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>景点</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=143 colspan=8 valign=top style='width:107.2pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>单价</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'>*</span></b><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>人数</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=135 colspan=7 valign=top style='width:101.4pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>现付</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=152 colspan=5 valign=top style='width:113.65pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>签单</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
 </tr>
 <%
	for(int n=0;n<rd.getTableRowsCount("sceneryList");n++){
		String sceneryid=rd.getStringByDI("sceneryList","ID",n);
%>
 <tr style='mso-yfti-irow:14'>
  <td width=142 colspan=4 valign=top style='width:106.15pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("sceneryList","cmpny_name",n) %><o:p></o:p></span></p>
  </td>
  <td width=143 colspan=8 valign=top style='width:107.2pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'>
  <%
  		for(int o=0;o<rd.getTableRowsCount("sceneryJgList");o++){
  			String sceneryjhid=rd.getStringByDI("sceneryJgList","JHID",o);
  			if(sceneryid.equals(sceneryjhid)){
  	%>
  	<%=rd.getStringByDI("sceneryJgList","jgmc",o) %>:<%=rd.getStringByDI("sceneryJgList","jg",o) %>*<%=rd.getStringByDI("sceneryJgList","rs",o) %><br/>
  	<%}} %>
  <o:p></o:p></span></p>
  </td>
  <td width=135 colspan=7 valign=top style='width:101.4pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("sceneryList","xfxj",n) %><o:p></o:p></span></p>
  </td>
  <td width=152 colspan=5 valign=top style='width:113.65pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("sceneryList","qdxj",n) %><o:p></o:p></span></p>
  </td>
 </tr>
 <%} %>
 <tr style='mso-yfti-irow:15'>
  <td width=571 colspan=24 valign=top style='width:428.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>地接社合计：现付：</span></b><span
  lang=EN-US style='font-size:9.0pt'><%=rd.getStringByDI("TA_TDJDXXZJBs","DJXFZJ",0) %><span
  style='mso-spacerun:yes'>&nbsp;&nbsp; </span><b style='mso-bidi-font-weight:
  normal'>+ </b></span><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>签单：</span></b><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("TA_TDJDXXZJBs","DJQDZJ",0) %><span style='mso-spacerun:yes'>&nbsp;
  </span>=<%=rd.getStringByDI("TA_TDJDXXZJBs","DJZJ",0) %><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:16'>
  <td width=142 colspan=4 valign=top style='width:106.15pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>旅行社</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=143 colspan=8 valign=top style='width:107.2pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>联系人</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=135 colspan=7 valign=top style='width:101.4pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>人数</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=76 colspan=3 valign=top style='width:57.0pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>现付</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=76 colspan=2 valign=top style='width:56.65pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>签单</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
 </tr>
 <%
 	for(int q=0;q<rd.getTableRowsCount("traveList");q++){
 %>
 <tr style='mso-yfti-irow:17'>
  <td width=142 colspan=4 valign=top style='width:106.15pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("traveList","cmpny_name",q) %><o:p></o:p></span></p>
  </td>
  <td width=143 colspan=8 valign=top style='width:107.2pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("traveList","person_name",q) %><o:p></o:p></span></p>
  </td>
  <td width=135 colspan=7 valign=top style='width:101.4pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'>成人:<%=rd.getStringByDI("traveList","crrs",q) %>&nbsp;儿童:<%=rd.getStringByDI("traveList","etrs",q) %><o:p></o:p></span></p>
  </td>
  <td width=76 colspan=3 valign=top style='width:57.0pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("traveList","xfje",q) %><o:p></o:p></span></p>
  </td>
  <td width=76 colspan=2 valign=top style='width:56.65pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("traveList","qdje",q) %><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:18'>
  <td width=571 colspan=24 valign=top style='width:428.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>备注：</span></b><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("traveList","bz",q) %><o:p></o:p></span></p>
  </td>
 </tr>
 <%} %>
 <tr style='mso-yfti-irow:19'>
  <td width=571 colspan=24 valign=top style='width:428.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>大交通：现付：</span></b><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("TA_TDJDXXZJBs","XFPWZJ",0) %><span style='mso-spacerun:yes'>&nbsp;&nbsp;
  </span><b style='mso-bidi-font-weight:normal'>+ </b></span><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>签单：</span></b><span
  lang=EN-US style='font-size:9.0pt'><%=rd.getStringByDI("TA_TDJDXXZJBs","QDPWZJ",0) %><span
  style='mso-spacerun:yes'>&nbsp; </span>=<%=rd.getStringByDI("TA_TDJDXXZJBs","PWZJ",0) %><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:20'>
  <td width=70 valign=top style='width:52.5pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>时间</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=60 colspan=2 valign=top style='width:44.7pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>车次</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=71 colspan=5 valign=top style='width:53.55pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>起始站</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=48 valign=top style='width:35.8pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>票价</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=48 colspan=4 valign=top style='width:35.8pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>张数</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=60 colspan=2 valign=top style='width:44.8pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>手续费</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=48 colspan=3 valign=top style='width:35.8pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>现付</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=48 colspan=2 valign=top style='width:35.8pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>签单</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=62 colspan=3 valign=top style='width:46.15pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>票务</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
  <td width=58 valign=top style='width:43.5pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>电话</span></b><b
  style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></b></p>
  </td>
 </tr>
 <%
 	for(int p=0;p<rd.getTableRowsCount("ticketList");p++){
 %>
 <tr style='mso-yfti-irow:21'>
  <td width=70 valign=top style='width:52.5pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><st1:chsdate
  IsROCDate="False" IsLunarDate="False" Day="15" Month="9" Year="2011" w:st="on"><span
   lang=EN-US style='font-size:9.0pt'><%=rd.getStringByDI("ticketList","begintime",p) %></span></st1:chsdate></p>
  </td>
  <td width=60 colspan=2 valign=top style='width:44.7pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("ticketList","cc",p) %><o:p></o:p></span></p>
  </td>
  <td width=71 colspan=5 valign=top style='width:53.55pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("ticketList","cfz",p) %>-<%=rd.getStringByDI("ticketList","mdz",p) %><o:p></o:p></span></p>
  </td>
  <td width=48 valign=top style='width:35.8pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("ticketList","dj",p) %><o:p></o:p></span></p>
  </td>
  <td width=48 colspan=4 valign=top style='width:35.8pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("ticketList","zs",p) %><o:p></o:p></span></p>
  </td>
  <td width=60 colspan=2 valign=top style='width:44.8pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("ticketList","sxf",p) %><o:p></o:p></span></p>
  </td>
  <td width=48 colspan=3 valign=top style='width:35.8pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("ticketList","qdxj",p) %><o:p></o:p></span></p>
  </td>
  <td width=48 colspan=2 valign=top style='width:35.8pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("ticketList","xfxj",p) %><o:p></o:p></span></p>
  </td>
  <td width=62 colspan=3 valign=top style='width:46.15pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("ticketList","cmpny_name",p) %><o:p></o:p></span></p>
  </td>
  <td width=58 valign=top style='width:43.5pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("ticketList","lxrdh",p) %><o:p></o:p></span></p>
  </td>
 </tr>
 <%} %>
 <tr style='mso-yfti-irow:23;height:12.65pt'>
  <td width=571 colspan=24 valign=top style='width:428.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:12.65pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>购物点：</span></b><span lang=EN-US
  style='font-size:9.0pt'>
  <%
  		for(int r=0;r<rd.getTableRowsCount("shopList");r++){
  	%>
  		<%=rd.getStringByDI("shopList","cmpny_name",r) %>
  	<%} %>
  <o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:24;height:15.0pt'>
  <td width=571 colspan=24 valign=top style='width:428.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>加</span></b><b style='mso-bidi-font-weight:
  normal'><span lang=EN-US style='font-size:9.0pt'><span
  style='mso-spacerun:yes'>&nbsp; </span></span></b><b style='mso-bidi-font-weight:
  normal'><span style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:
  "Times New Roman";mso-hansi-font-family:"Times New Roman"'>点：</span></b><span
  lang=EN-US style='font-size:9.0pt'>
  <%
  		for(int s=0;s<rd.getTableRowsCount("jiadList");s++){
  	%>
  		<%=rd.getStringByDI("jiadList","jdmc",s) %>
  	<%} %>
  <o:p></o:p></span></p>
  </td>
 </tr>
 <%
 	String zjStr = rd.getStringByDI("qdxfzj","zj",0);
 	if("".equals(zjStr))
 		zjStr = "0";
 	float zj=Float.parseFloat(zjStr);
 	String ddfStr = rd.getStringByDI("guideFYList","DFF",0);
 	if("".equals(ddfStr))
 		ddfStr = "0";
 	float dff=Float.parseFloat(ddfStr);
 %>
 <tr style='mso-yfti-irow:22'>
  <td width=249 colspan=24 valign=top style='width:186.55pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span class=GramE><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>导服费</span></b></span><b
  style='mso-bidi-font-weight:normal'><span style='font-size:9.0pt;font-family:
  宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>：</span></b><span
  lang=EN-US style='font-size:9.0pt'><%=dff %><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:25'>
  <td width=192 colspan=6 valign=top style='width:144.2pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>应付合计：</span></b><span lang=EN-US
  style='font-size:9.0pt'><%=zj+dff %> <o:p></o:p></span></p>
  </td>
  <td width=189 colspan=11 valign=top style='width:142.05pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>其中现金：</span></b><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("qdxfzj","xfzj",0) %><o:p></o:p></span></p>
  </td>
  <td width=190 colspan=7 valign=top style='width:142.15pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>其中签单：</span></b><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("qdxfzj","qdzj",0) %><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:26'>
  <td width=571 colspan=24 valign=top style='width:428.4pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>实际领团款：</span></b><span lang=EN-US
  style='font-size:9.0pt'><%=rd.getStringByDI("guideFYList","dylk",0) %><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:27;mso-yfti-lastrow:yes'>
  <td width=284 colspan=12 valign=top style='width:213.35pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>经理：</span></b><span lang=EN-US
  style='font-size:9.0pt'><o:p></o:p></span></p>
  </td>
  <td width=287 colspan=12 valign=top style='width:215.05pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b style='mso-bidi-font-weight:normal'><span
  style='font-size:9.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
  mso-hansi-font-family:"Times New Roman"'>计调：</span></b><span lang=EN-US
  style='font-size:9.0pt'><%=sd.getString("username") %><o:p></o:p></span></p>
  </td>
 </tr>
 <![if !supportMisalignedColumns]>
 <tr height=0>
  <td width=70 style='border:none'></td>
  <td width=12 style='border:none'></td>
  <td width=48 style='border:none'></td>
  <td width=12 style='border:none'></td>
  <td width=12 style='border:none'></td>
  <td width=39 style='border:none'></td>
  <td width=2 style='border:none'></td>
  <td width=7 style='border:none'></td>
  <td width=48 style='border:none'></td>
  <td width=12 style='border:none'></td>
  <td width=15 style='border:none'></td>
  <td width=9 style='border:none'></td>
  <td width=12 style='border:none'></td>
  <td width=36 style='border:none'></td>
  <td width=24 style='border:none'></td>
  <td width=13 style='border:none'></td>
  <td width=13 style='border:none'></td>
  <td width=22 style='border:none'></td>
  <td width=16 style='border:none'></td>
  <td width=32 style='border:none'></td>
  <td width=18 style='border:none'></td>
  <td width=26 style='border:none'></td>
  <td width=18 style='border:none'></td>
  <td width=58 style='border:none'></td>
 </tr>
 <![endif]>
</table>


<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

</div>
</div>

</body>

</html>

