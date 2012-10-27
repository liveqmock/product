<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
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

String fileName="出团通知单.doc";
String sn = new String(fileName.getBytes("gb2312"),"ISO8859-1");
response.setHeader("Content-Disposition","attachment; filename="+sn);
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:w="urn:schemas-microsoft-com:office:word"
xmlns="http://www.w3.org/TR/REC-html40">
<head>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<meta name=ProgId content=Word.Document>
<meta name=Generator content="Microsoft Word 11">
<meta name=Originator content="Microsoft Word 11">
<link rel=File-List href="游客出团任务单模板.files/filelist.xml">
<title>出团任务单</title>
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>devil</o:Author>
  <o:LastAuthor>devil</o:LastAuthor>
  <o:Revision>2</o:Revision>
  <o:TotalTime>13</o:TotalTime>
  <o:Created>2011-10-18T01:48:00Z</o:Created>
  <o:LastSaved>2011-10-18T01:48:00Z</o:LastSaved>
  <o:Pages>1</o:Pages>
  <o:Words>16</o:Words>
  <o:Characters>95</o:Characters>
  <o:Company>jst</o:Company>
  <o:Lines>1</o:Lines>
  <o:Paragraphs>1</o:Paragraphs>
  <o:CharactersWithSpaces>110</o:CharactersWithSpaces>
  <o:Version>11.9999</o:Version>
 </o:DocumentProperties>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <w:WordDocument>
  <w:SpellingState>Clean</w:SpellingState>
  <w:GrammarState>Clean</w:GrammarState>
  <w:PunctuationKerning/>
  <w:DrawingGridHorizontalSpacing>10.65 磅</w:DrawingGridHorizontalSpacing>
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
</xml><![endif]-->
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
	{font-family:华文新魏;
	panose-1:2 1 8 0 4 1 1 1 1 1;
	mso-font-charset:134;
	mso-generic-font-family:auto;
	mso-font-pitch:variable;
	mso-font-signature:1 135200768 16 0 262144 0;}
@font-face
	{font-family:"\@华文新魏";
	panose-1:2 1 8 0 4 1 1 1 1 1;
	mso-font-charset:134;
	mso-generic-font-family:auto;
	mso-font-pitch:variable;
	mso-font-signature:1 135200768 16 0 262144 0;}
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
 /* Page Definitions */
 @page
	{mso-page-border-surround-header:no;
	mso-page-border-surround-footer:no;}
@page Section1
	{size:595.3pt 841.9pt;
	margin:72.0pt 89.85pt 72.0pt 89.85pt;
	mso-header-margin:42.55pt;
	mso-footer-margin:49.6pt;
	mso-paper-source:0;
	layout-grid:15.6pt .15pt;
	mso-layout-grid-char-alt:640;}
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
</style>
<![endif]-->
</head>
<body lang=ZH-CN style='tab-interval:21.0pt;text-justify-trim:punctuation'>
<div class=Section1 style='layout-grid:15.6pt .15pt;mso-layout-grid-char-alt:
640'>
<div align=center>
	<table>
			<%
					Object obj = rd.get("xlmxview", "xcmx",0);
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
	</table>
</div>
<div align=center>
<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0 width=588
 style='width:441.0pt;border-collapse:collapse;mso-padding-alt:0cm 5.4pt 0cm 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;height:7.5pt'>
  <td width=588 colspan=2 valign=top style='width:441.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:7.5pt'>
  <p class=MsoNormal><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>团<span lang=EN-US>&nbsp;&nbsp;&nbsp; </span>号：<%=rd.getStringByDI("rsOBForView","id",0) %></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:1;height:14.25pt'>
  <td width=252 valign=top style='width:189.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:14.25pt'>
  <p class=MsoNormal><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>游客姓名：<%
int visitors = rd.getTableRowsCount("rsVisitors");
for(int i=0;i<visitors;i++){
	String isLeader = rd.getStringByDI("rsVisitors","isleader",i);
	if("1".equals(isLeader))
		out.print( rd.getStringByDI("rsVisitors","visitor_nm",i));
}
%></span></p>
  </td>
  <td width=336 valign=top style='width:252.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:14.25pt'>
  <p class=MsoNormal><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>游客电话：<%=rd.getStringByDI("rsVisitors","tel",0) %></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:2;height:7.5pt'>
  <td width=252 valign=top style='width:189.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:7.5pt'>
  <p class=MsoNormal><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>游客人数：<%=rd.getStringByDI("rsOBForView","ysrs",0) %> 人</span></p>
  </td>
  <td width=336 valign=top style='width:252.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:7.5pt'>
  <p class=MsoNormal><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>座位编号：<%
	int rows = rd.getTableRowsCount("rsVisitors");
	String seatNumber = "";
	for(int i=0;i<rows;i++){
		seatNumber += rd.getStringByDI("rsVisitors","SEAT_NUM",i)+"，";
	}
	out.print(seatNumber);
%></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:3;height:13.5pt'>
  <td width=252 valign=top style='width:189.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:13.5pt'>
  <p class=MsoNormal><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>出团日期：<%=rd.getStringByDI("rsOBForView","begin_date",0) %></span></p>
  </td>
  <td width=336 valign=top style='width:252.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:13.5pt'>
  <p class=MsoNormal><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>返回日期：<%=rd.getStringByDI("rsOBForView","end_date",0) %></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:4;height:8.25pt'>
  <td width=252 valign=top style='width:189.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:8.25pt'>
  <p class=MsoNormal><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>已 收 款：<%=rd.getStringByDI("rsOBForView","yi_sk",0) %></span></p>
  </td>
  <td width=336 valign=top style='width:252.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:8.25pt'>
  <p class=MsoNormal><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>下单日期：<%=rd.getStringByDI("rsOBForView","regedit_time",0) %></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:5;height:8.25pt'>
  <td width=252 valign=top style='width:189.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:8.25pt'>
  <p class=MsoNormal><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>集合地点：<%=rd.getStringByDI("rsOBForView","gather",0) %><span lang=EN-US><o:p></o:p></span></span></p>
  </td>
  <td width=336 valign=top style='width:252.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:8.25pt'>
  <p class=MsoNormal><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>集合时间：<%=rd.getStringByDI("rsOBForView","gather_time",0) %><span lang=EN-US><o:p></o:p></span></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:6;height:15.0pt'>
  <td width=252 valign=top style='width:189.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:15.0pt'>
  <p class=MsoNormal><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>旅 行 社：<%=rd.getStringByDI("rsOBForView","cmpny_name",0) %></span></p>
  </td>
  <td width=336 valign=top style='width:252.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:15.0pt'>
  <p class=MsoNormal><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>联 系 人：<%=rd.getStringByDI("rsOBForView","business_name",0) %></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:7;mso-yfti-lastrow:yes;height:12.0pt'>
  <td width=252 valign=top style='width:189.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:12.0pt'>
  <p class=MsoNormal><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>联系方式：<%=rd.getStringByDI("rsOBForView","business_tel",0) %></span></p>
  </td>
  <td width=336 valign=top style='width:252.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:12.0pt'>
  <p class=MsoNormal><span style='font-size:9.0pt;font-family:宋体;mso-bidi-font-family:
  宋体;mso-font-kerning:0pt'>应急电话：<%=rd.getStringByDI("rsOBForView","chief_mobile",0) %></span></p>
  </td>
 </tr>
</table>

</div>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

</div>

</body>

</html>

