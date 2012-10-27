<%@ page language="java" import="java.util.*" contentType="application/msword; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="com.trmdj.businessPlan.groupMng.GroupLineDetail"%>
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

	String fileName="散客报价单.doc";
	String sn=new String(fileName.getBytes("gb2312"),"ISO-8859-1");
	response.setHeader("Content-Disposition","attachment;filename="+sn);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml"
xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:w="urn:schemas-microsoft-com:office:word"
xmlns:st1="urn:schemas-microsoft-com:office:smarttags"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<meta name=ProgId content=Word.Document>
<meta name=Generator content="Microsoft World 11">
<meta name=Originator content="Microsoft Word 11">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<link rel=File-List href="test.files/filelist.xml">
<link rel=Edit-Time-Data href="test.files/editdata.mso">
<link rel=OLE-Object-Data href="test.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>shen</o:Author>
  <o:LastAuthor>USER</o:LastAuthor>
  <o:Revision>2</o:Revision>
  <o:TotalTime>128</o:TotalTime>
  <o:Created>2011-04-04T11:44:00Z</o:Created>
  <o:LastSaved>2011-04-04T11:44:00Z</o:LastSaved>
  <o:Pages>1</o:Pages>
  <o:Words>96</o:Words>
  <o:Characters>549</o:Characters>
  <o:Company>shen</o:Company>
  <o:Lines>4</o:Lines>
  <o:Paragraphs>1</o:Paragraphs>
  <o:CharactersWithSpaces>644</o:CharactersWithSpaces>
  <o:Version>11.9999</o:Version>
 </o:DocumentProperties>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <w:WordDocument>
  <w:View>Print</w:View>
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
     <x:TopRowVisible>12</x:TopRowVisible>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>10</x:ActiveRow>
       <x:ActiveCol>8</x:ActiveCol>
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
</xml><![endif]--><!--[if gte mso 9]><xml>
 <o:shapedefaults v:ext="edit" spidmax="1025"/>
</xml><![endif]-->
<style>
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
@page
	{margin:1.0in .75in 1.0in .75in;
	mso-header-margin:.5in;
	mso-footer-margin:.5in;}
.style0
	{mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	white-space:nowrap;
	mso-rotate:0;
	mso-background-source:auto;
	mso-pattern:auto;
	color:windowtext;
	font-size:9.0pt;
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
.xcenter
	{
		width:auto;
		margin:1px 1px 1px 1px;
		padding:1px 1px 1px 1px;
		word-wrap:break-word;
		font-size:12px ;color:blue;font-weight:bold;
		border-top:1pt solid black;
		border-right:1pt solid black;
		border-left:.1pt solid black;
		border-bottom:1pt solid black;
	}
.xcenter1
	{
		width:auto;
		margin:1px 1px 1px 1px;
		padding:1px 1px 1px 1px;
		word-wrap:break-word;
		font-size:12px ;color:black;font-weight:bold;
		border-top:1pt solid black;
		border-right:1pt solid black;
		border-left:.1pt solid black;
		border-bottom:1pt solid black;
	}
.xcenter2
	{
		width:auto;
		margin:1px 1px 1px 1px;
		padding:1px 1px 1px 1px;
		word-wrap:break-word;
		border-top:1pt solid black;
		border-right:1pt solid black;
		border-left:.1pt solid black;
		border-bottom:1pt solid black;
	}
.xcenter3
	{
		width:auto;
		margin:1px 1px 1px 1px;
		padding:1px 1px 1px 1px;
		word-wrap:break-word;
		font-size:20px ;color:black;font-weight:bold;
		border-top:1pt solid black;
		border-right:1pt solid black;
		border-left:.1pt solid black;
		border-bottom:1pt solid black;
	}
.xfirst
	{
		width:auto;
		margin:1px 1px 1px 1px;
		padding:1px 1px 1px 1px;
		word-wrap:break-word;
		font-size:12px ;color:blue;font-weight:bold;
		border-top:1pt solid black;
		border-right:1pt solid black;
		border-left:1pt solid black;
		border-bottom:1pt solid black;
	}
.xl27
	{mso-style-parent:style0;
	font-size:20.0pt;
	font-family:隶书, monospace;
	mso-font-charset:134;
	text-align:center;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
ruby
	{ruby-align:left;}
-->
</style>

<style type="text/css">
	body{margin: 0;padding: 0;font-size: 12px;}
	#top{background: url(<%=request.getContextPath() %>/images/ToolBarBg.gif) repeat-x;height: 35px;text-align: center;line-height: 35px;}
	#print{background: url(<%=request.getContextPath() %>/images/Print.gif) no-repeat 0 7px;width: 55px;text-indent: 15px;float: left;cursor: pointer;}
	#word{background: url(<%=request.getContextPath() %>/images/word.gif) no-repeat 0 7px;width: 82px;text-indent: 15px;float: left;cursor: pointer;}
	#close{background: url(<%=request.getContextPath() %>/images/printClose.gif) no-repeat 0 11px;width: 49px;text-indent: 10px;float: left;cursor: pointer;}
	#center-top{background: url(<%=request.getContextPath() %>/images/body_04.jpg) no-repeat;width: 793px;height: 33px;text-align: center}
	#center-cen{background: url(<%=request.getContextPath() %>/images/body_05.jpg) repeat-y;width: 793px;}
	#button{background: url(<%=request.getContextPath() %>/images/body_06.jpg) no-repeat;width: 793px;}
</style> 
<%
	String XLID = rd.getString("TA_DJ_LINEMNGs","XLID",0);
	String XLMC = rd.getString("TA_DJ_LINEMNGs","XLMC",0);
	String TS = rd.getString("TA_DJ_LINEMNGs","TS",0);
	String FBJH = rd.getString("TA_DJ_LINEMNGs","FBJH",0);
	String MP = rd.getString("TA_DJ_LINEMNGs","MP",0);
	String ZSBZ = rd.getString("TA_DJ_LINEMNGs","ZSBZ",0);
	String YCBZ = rd.getString("TA_DJ_LINEMNGs","YCBZ",0);
	String YC = rd.getString("TA_DJ_LINEMNGs","YC",0);
	String DY = rd.getString("TA_DJ_LINEMNGs","DY",0);
	String GW = rd.getString("TA_DJ_LINEMNGs","GW",0);
	String TBYQ = rd.getString("TA_DJ_LINEMNGs","TBYQ",0);
%>
<script type="text/javascript">

</script>
</head>
<div id="center" align="center">
<div id="center-top"></div>
<div id="center-cen">		
<div align="center">
<table x:str border=0 cellpadding=0 cellspacing=0 width=588 style='border-collapse:
 collapse;table-layout:fixed;width:441pt'>

 <tr height=27 style='height:20.25pt'>
    <td colspan=8 height=27 class=xl27 width=588 style='height:20.25pt;
  width:441pt'>南京皇家国际旅行社有限公司散客报价表</td>
 </tr>
<tr height=27>
	<td class="xfirst">行程/天数</td>
	<td class="xcenter" >价格</td>
	<td class="xcenter" >发团</td>
	<td class="xcenter" >门票</td>
	<td class="xcenter" >住宿标准</td>
	<td class="xcenter" >餐饮标准</td>
	<td class="xcenter">用车</td>
	<td class="xfirst" >导游</td>
	
</tr>
<tr>
 	<td class="xcenter"><%=XLMC %><br><%=TS %>日游</td>
 	<td class="xcenter">
 		<%
 			int priceRows = rd.getTableRowsCount("TA_DJ_XLJGs");
 			for(int i = 0; i< priceRows; i++){
 				String JGLX = rd.getString("TA_DJ_XLJGs","JGLX",i);
				String JG = rd.getString("TA_DJ_XLJGs","JG",i);
 			    for(int j = 0;j < rd.getTableRowsCount("JGLX"); j++){
 			    	String dmz=rd.getStringByDI("JGLX","DMZ",j);
	 			     if(dmz.equals(JGLX)){
	 			    	 String DMSM = rd.getStringByDI("JGLX","DMSM1",j);
	 			    	 %>
	 			    	 	<%=DMSM %>:<%=JG %>&nbsp;&nbsp;
	 			    	 <%
	 			     }
 			     }

 			}
 			
 		%>
 	</td>
 	<td class="xcenter"><%=FBJH %></td>
 	<td class="xcenter"><%=MP %></td>
 	<td class="xcenter"><%=ZSBZ %></td>
 	<td class="xcenter"><%=YCBZ %></td>
 	<td class="xcenter"><%=YC %></td>
 	<td class="xcenter"><%=DY %></td>
 </tr>
 <tr height=27>
 	<td colspan=8  class="xcenter">购物安排：<%=GW %></td>
 </tr>
 
  <tr height=27>
 	<td colspan=1  class="xcenter1">日期</td>
 	<td colspan=5  class="xcenter1">行程内容</td>
 	<td colspan=1  class="xcenter1">供餐</td>
 	<td colspan=1 class="xcenter1">住宿</td>
 </tr>
 <%
	 List<GroupLineDetail> list = (List)rd.get("lineDetail");
	 for(int i = 0; i<list.size(); i++){
		   GroupLineDetail gd = list.get(i);
		   String RQ = gd.getRq();//日期
		   String XCMX = gd.getXcmx();//线路明细
		   String BREAKFAST = gd.getBreakfast();//早
		   String CMEAL = gd.getCmeal();//中
		   String SUPPER = gd.getSupper();//晚
		   String ZS = gd.getZs();//住宿
	 		if("Y".equals(BREAKFAST)){
	 			BREAKFAST = "早";
	 		}
	 		if("Y".equals(CMEAL)){
	 			CMEAL = "中";
	 		}
	 		if("Y".equals(SUPPER)){
	 			SUPPER = "晚";
	 		}
 %>
  <tr height=27>
 	<td class="xcenter3"><%=RQ %></td>
 	<td colspan=5 class="xcenter2"><%=XCMX %></td>
 	<td class="xcenter1"><%=BREAKFAST %>&nbsp;<%=CMEAL %>&nbsp;<%=SUPPER %></td>
 	<td class="xcenter1"><%=ZS %></td>
 </tr>
<%} %>
 <tr height=27>
 	<td colspan=8 class="xcenter2" align="left">&nbsp;<font color="blue"><b>特别提醒：<%=TBYQ %></b></font></td>
 </tr>
 <tr></tr>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=105 style='width:79pt'></td>
  <td width=32 style='width:24pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=91 style='width:68pt'></td>
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
