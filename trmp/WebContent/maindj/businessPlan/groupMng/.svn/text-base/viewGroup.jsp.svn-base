<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.Blob"%>
<%@page import="java.io.InputStreamReader"%>
<%@include file="/common.jsp"%>
<%
String roleID = sd.getString("USERROLEST");
boolean isTrue = false;
if(!"".equals(roleID)){
	
	roleID = roleID.substring(1,roleID.length()-1);
	String[] roleIDs = roleID.split(",");
	for(int i=0;i<roleIDs.length;i++){
		if("admin".equals(roleIDs[i].trim()) || "transferForDj".equals(roleIDs[i].trim())){
			isTrue = true;
			break;
		}
	}
}
String canEdit = rd.getString("canEdit");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>

<script type="text/javascript">
	//jQuery(document).ready(function(){
	window.onload = function(){
		CKEDITOR.replace('XCMX');//加载ckeditor
		CKEDITOR.config.toolbarStartupExpanded = false;
		CKEDITOR.config.resize_enabled = true;
	};


</script>



<title>修改团信息</title>
</head>

<body >
<form action="djUpdateGroupInfo.?action=td" name="djUpdateGroupInfo" method="post">
<div class="width-98">
<div id="list-table">

<table class="datas"  id="listStore">
 <tr id="first-tr">
	<td width="15%">组&nbsp;团&nbsp;社</td>
	<td width="10%">人&nbsp;&nbsp;&nbsp;&nbsp;数</td>
	<td width="10%">预算款</td>
	<td width="10%">预算款签单金额</td>
	<td width="10%">预算款现付金额</td>
  </tr>
  <%
  int TraveRows = rd.getTableRowsCount("TA_DJ_TZTSs");
  for(int i=0;i<TraveRows;i++){
      String SFID=rd.getStringByDI("TA_DJ_TZTSs","SFID",i);
      String CSID=rd.getStringByDI("TA_DJ_TZTSs","CSID",i);
      String ZTSID=rd.getStringByDI("TA_DJ_TZTSs","ZTSID",i);
      String YSZK=rd.getStringByDI("TA_DJ_TZTSs","YSZK",i);
      String CRRS=rd.getStringByDI("TA_DJ_TZTSs","CRRS",i);
      String ETRS=rd.getStringByDI("TA_DJ_TZTSs","ETRS",i);
      String KYD=rd.getStringByDI("TA_DJ_TZTSs","KYD",i);
      
      String YSKQDJE=rd.getStringByDI("TA_DJ_TZTSs","YSKQDJE",i);
      String YSKXFJE=rd.getStringByDI("TA_DJ_TZTSs","YSKXFJE",i);
  %>
  <tr class="traveType">
	<td>
	  <select name="TA_DJ_TZTS/SFID[<%=i %>]" id="TA_DJ_TZTS/SFID[<%=i %>]"  USEDATA="dataSrc<%=i %>" SUBCLASS="1" onchange="chageKYD(this.value,<%=i %>);"></select></br>
	  <select name="TA_DJ_TZTS/CSID[<%=i %>]" id="TA_DJ_TZTS/CSID[<%=i %>]" USEDATA="dataSrc<%=i %>" SUBCLASS="2"></select></br>
	  <select name="TA_DJ_TZTS/ZTSID[<%=i %>]"  id="TA_DJ_TZTS/ZTSID[<%=i %>]" USEDATA="dataSrc<%=i %>" SUBCLASS="3"></select></br>	
	</td>
	<td>成人：<input type="text" name="TA_DJ_TZTS/CRRS[<%=i %>]" value="<%=CRRS %>" class="smallInput crrs" onchange="sumPrice()"  onkeydown="checkNum()" maxlength="3"/><br>
		儿童：<input type="text" name="TA_DJ_TZTS/ETRS[<%=i %>]" value="<%=ETRS %>" class="smallInput etrs" onchange="sumPrice()"  onkeydown="checkNum()" maxlength="3"/></td>
	<td ><input type="text" name="TA_DJ_TZTS/YSZK[<%=i %>]" value="<%=YSZK %>" class="smallInput yskzj" onchange="sumPrice()" onkeydown="checkNum()" maxlength="6"/></td>
	<td ><input type="text" name="TA_DJ_TZTS/YSKQDJE[<%=i %>]" value="<%=YSKQDJE %>" class="smallInput yskqd" onchange="sumPrice()" onkeydown="checkNum()" maxlength="6"/></td>
	<td ><input type="text" name="TA_DJ_TZTS/YSKXFJE[<%=i %>]" value="<%=YSKXFJE %>" class="smallInput yskxf" onkeydown="checkNum()" maxlength="6" readonly="readonly"/></td>
		</tr>
		<%} %>
</table>
</div>

<div id="list-table">
<table  class="datas">
	<tr>
		<td colspan="11">
			<font color="red">成人总人数：</font><%="".equals(rd.getStringByDI("TA_DJ_GROUPs","CRRS",0))?0:rd.getStringByDI("TA_DJ_GROUPs","CRRS",0)%>人&nbsp;&nbsp;
			<font color="red">儿童总人数：</font><%="".equals(rd.getStringByDI("TA_DJ_GROUPs","ETRS",0))?0:rd.getStringByDI("TA_DJ_GROUPs","ETRS",0)%>人&nbsp;&nbsp;
			<font color="red">应收账款总计：</font><%="".equals(rd.getStringByDI("TA_DJ_GROUPs","YSKZJ",0))?0:rd.getStringByDI("TA_DJ_GROUPs","YSKZJ",0)%>/元&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>
</table>
</div>
 <div class="add-table"> 
	<table border="0" width="100%">
	  <tr >
	  	<td>线路名称：
		  	<%=rd.getStringByDI("TA_DJ_GROUPs","XLMC",0)%>
	  	</td>
      	<td>
      		团&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：
   	  	 <%=rd.getStringByDI("TA_DJ_GROUPs","ID",0)%>
   	  	 &nbsp;&nbsp;&nbsp;&nbsp;
   	  	 游客类型：<%
        		String yklx = rd.getStringByDI("TA_DJ_GROUPs","YKLX",0);
		   	  if("1".equals(yklx)){out.print("团队");}
		   		if("2".equals(yklx)){out.print("散客");}

        	%>
        	&nbsp;&nbsp;&nbsp;&nbsp;
       		 天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数：
        	<%=rd.getStringByDI("TA_DJ_GROUPs","TS",0)%>&nbsp; 天
        	<%=rd.getStringByDI("TA_DJ_GROUPs","NIGHT",0)%>&nbsp; 晚
        	&nbsp;&nbsp;&nbsp;&nbsp;
        	客源地：
		<%
		String KYD = rd.getString("TA_DJ_GROUPs","KYD",0);
		for(int j=0;j<rd.getTableRowsCount("XZQHs");j++){
			String XZQHID = rd.getStringByDI("XZQHs","ID",j);
			String XZQHPNAME = rd.getStringByDI("XZQHs","NAME",j);
			if(XZQHID.equals(KYD)) {out.print(XZQHPNAME); } 
		}
		%>

  	    </td>
  	    </tr>
  	    <tr>
  	    <td>
  	    	加点购物：
       		<%
        	String jd = rd.getStringByDI("TA_DJ_GROUPs","JD",0);
       		String gw = rd.getStringByDI("TA_DJ_GROUPs","GW",0);
      		%>
   		  <input type="checkbox" name="TA_DJ_GROUP/JD" value="1" <%if("1".equals(jd)){ %>checked="checked"<%} %>/>加点
   		  <input type="checkbox" name="TA_DJ_GROUP/GW" value="1" <%if("1".equals(gw)){ %>checked="checked"<%} %>/>购物
   		  &nbsp;&nbsp;&nbsp;&nbsp;
   		  进购物店数：<%=rd.getStringByDI("TA_DJ_GROUPs","GWDS",0)%>&nbsp;个
  	    </td>
  	    <td>是否自备车：
        	<%
        	String SFZBC = rd.getStringByDI("TA_DJ_GROUPs","SFZBC",0);
          	%>
		  <input type="radio" name="TA_DJ_GROUP/SFZBC" value="1" <%if("1".equals(SFZBC)){ %>checked="checked"<%} %>/>是
	      <input type="radio" name="TA_DJ_GROUP/SFZBC" value="2" <%if("2".equals(SFZBC)){ %>checked="checked"<%} %>/>否
	      &nbsp;&nbsp;&nbsp;&nbsp;
	      用车标准：<%
	  		String VEHTYPE = rd.getStringByDI("TA_DJ_GROUPs","VEHTYPE",0);
	  		for(int i=0;i<rd.getTableRowsCount("CLLX");i++) {
   		    	String dmz = rd.getStringByDI("CLLX","dmz",i);
   		    	if(dmz.equals(VEHTYPE)){out.print(rd.getStringByDI("CLLX","dmsm1",i));}
	  		}
	  		%>
		</td>
  	    </tr>
	  <tr>
  	  	<td >来程：
	  		<%
	  		String CFJTGJ = rd.getStringByDI("TA_DJ_GROUPs","B_TRAFFIC",0);
	  		for(int i=0;i<rd.getTableRowsCount("JTGJ");i++) {
   		    	String dmz = rd.getStringByDI("JTGJ","dmz",i);
   		    	if(dmz.equals(CFJTGJ)){out.print(rd.getStringByDI("JTGJ","dmsm1",i));}
	  		}%>
	  		&nbsp;&nbsp;&nbsp;&nbsp;<%=rd.getStringByDI("TA_DJ_GROUPs","BEGIN_DATE",0) %>
	  	</td>
	  	<td>回程：

	  		<%
	  		String FHJTGJ = rd.getStringByDI("TA_DJ_GROUPs","E_TRAFFIC",0);
	  		for(int i=0;i<rd.getTableRowsCount("JTGJ");i++) {
   		    	String dmz = rd.getStringByDI("JTGJ","dmz",i);
	  		if(dmz.equals(FHJTGJ)){out.print(rd.getStringByDI("JTGJ","dmsm1",i));}
	  		}
	  		%>
	  		&nbsp;&nbsp;&nbsp;&nbsp;<%=rd.getStringByDI("TA_DJ_GROUPs","END_DATE",0) %>
   		</td>
	  </tr>
	   
	<tr>
		 <td >用餐标准：
        	<%=rd.getStringByDI("TA_DJ_GROUPs","YCBZ",0) %>
        </td>
        <td>住宿标准：
	  		<%
	  		String ZSBZ = rd.getStringByDI("TA_DJ_GROUPs","ZSBZ",0);
	  		for(int i=0;i<rd.getTableRowsCount("JDXJ");i++) {
   		    	String dmz = rd.getStringByDI("JDXJ","dmz",i);
   		    	if(dmz.equals(ZSBZ)){out.print(rd.getStringByDI("JDXJ","dmsm1",i));}
	  		}
	  		%>
        </td>
		</tr>
	  <tr id="qp">
	  	<td>全陪姓名：<%=rd.getStringByDI("TA_DJ_GROUPs","QPXM",0) %></td>
		<td>全陪电话：<%=rd.getStringByDI("TA_DJ_GROUPs","QPDH",0) %></td>
	  </tr>
	</table>
</div>

<div class="add-table">
<table class="datas" width="100%">
  <tr id="first-tr">
  	<td colspan="8">线路行程明细</td>
  </tr>
  <tr>
       <td colspan="8">
       	 <!-- <FCK:editor id="LINE_DETAIL" width="100%" height="420"></FCK:editor>--> 
		 <textarea id="XCMX" name="TA_DJ_LINEDETAILTD/XCMX" rows="30" cols="50" >
		 <%
				Object obj = rd.get("TA_DJ_LINEDETAILTDs", "XCMX",0);
				Blob blob = null;
				int read=0;
				if(null != obj){
					blob = (Blob) obj;
					java.io.InputStream in=blob.getBinaryStream();
					InputStreamReader isr = new InputStreamReader(in, "GBK");
					char[] chars = new char[1];
					read = 0;
					while ((read = isr.read(chars)) != -1) {
						out.print(new String(chars));
					}
				}
		%>
   	 	</textarea> 
       </td>
   </tr>
   <tr>
   		<td>备注：</td>
		<td>
			<textarea  name="TA_DJ_GROUP/TSYQ" cols="99" rows="4" onpropertychange="if(this.value.length>200){this.value=this.value.slice(0,200)}"><%=rd.getStringByDI("TA_DJ_GROUPs","TSYQ",0) %></textarea>
		</td>
   </tr>
</table>
</div>
</div>

</form>
</body>
</html>
<script type="text/javascript">
	<%if(TraveRows>0){
		for(int v=0;v<TraveRows;v++){
	%>
		var linkage = new Linkage("dataSrc<%=v%>", "<%=request.getContextPath()%>/main/data/Traved.xml");
		linkage.init();
		linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("TA_DJ_TZTSs","SFID",v)%>",1);
		linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("TA_DJ_TZTSs","CSID",v)%>",2);
		linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("TA_DJ_TZTSs","ZTSID",v)%>",3);
	<%}}%>
</script>