<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>ǩ�����</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/treeAndAllCss.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script src="<%=request.getContextPath() %>/jqueryui/jqueryui.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>

<script type="text/javascript">
	$(function() {
		$("#tabs").tabs({selected:<%=rd.getString("target").equals("")?request.getParameter("target"):rd.getString("target")%>});
	});
	</script>




<script type="text/javascript">

function findByLike(){
	document.dj_guide_form1.action="AllQiandanList.?pageSize=10&pageNO=1";
	document.dj_guide_form1.submit();
}
function findByLikePW(){
	document.dj_guide_form3.action="AllPWList.?pageSize=10&pageNO=1";
	document.dj_guide_form3.submit();
}	
function findByLikeCT(){
	document.dj_guide_form4.action="AllCTQiandanList.?pageSize=10&pageNO=1";
	document.dj_guide_form4.submit();
	}
function findByLikeJD(){
	document.dj_guide_form5.action="AllJingDList.?pageSize=10&pageNO=1";
	document.dj_guide_form5.submit();
}
function findByLikeCD(){
	document.dj_guide_form2.action="AllCDList.?pageSize=10&pageNO=1";
	document.dj_guide_form2.submit();
}
function findByLikeTR(){
	document.dj_guide_form6.action="AllTRList.?pageSize=10&pageNO=1";
	document.dj_guide_form6.submit();
}

function addHotel(id,tid)
{
	window.open("trm/main/ztfinanceMng/jsJDqingdan.?ta_zt_qdqkjl4hotel/JDID="+id+"&ta_zt_qdqkjl4hotel/TID="+tid,"newwindow","height=400,width=400,top=180,left=500,toolbar=no,menubar=no,scrollbars=no,resizablr=no,locatiog=no,status=no");
}
function viewHotel(id,tid)
{
	window.open("trm/main/ztfinanceMng/queryQDlist.?ta_zt_qdqkjl4hotel/JDID="+id+"&ta_zt_qdqkjl4hotel/TID="+tid,"newwindow2","height=400,width=600,top=180,left=400,toolbar=no,menubar=no,scrollbars=yes,resizablr=no,locatiog=no,status=no");
}
function addVeh(id,tid)
{
	window.open("trm/main/ztfinanceMng/getBXCDByTid.?TA_ZT_QDQKJL4VEH/CDID="+id+"&TA_ZT_QDQKJL4VEH/TID="+tid,"newwindow","height=400,width=400,top=180,left=500,toolbar=no,menubar=no,scrollbars=no,resizablr=no,locatiog=no,status=no");
}
function viewVeh(id,tid)
{
	window.open("trm/main/ztfinanceMng/queryCDlist.?TA_ZT_QDQKJL4VEH/CDID="+id+"&TA_ZT_QDQKJL4VEH/TID="+tid,"newwindow2","height=400,width=600,top=180,left=400,toolbar=no,menubar=no,scrollbars=yes,resizablr=no,locatiog=no,status=no");
}
function addPW(id,tid)
{
	window.open("trm/main/ztfinanceMng/getBXPWByTid.?TA_ZT_QDQKJL4PW/DGDID="+id+"&TA_ZT_QDQKJL4PW/TID="+tid,"newwindow","height=400,width=400,top=180,left=500,toolbar=no,menubar=no,scrollbars=no,resizablr=no,locatiog=no,status=no");
}
function viewPW(id,tid)
{
	window.open("trm/main/ztfinanceMng/queryPWlist.?TA_ZT_QDQKJL4PW/DGDID="+id+"&TA_ZT_QDQKJL4PW/TID="+tid,"newwindow2","height=400,width=600,top=180,left=400,toolbar=no,menubar=no,scrollbars=yes,resizablr=no,locatiog=no,status=no");
}
function addCT(id,tid)
{
	window.open("trm/main/ztfinanceMng/getBXCTByTid.?TA_ZT_QDQKJL4CT/CTID="+id+"&TA_ZT_QDQKJL4CT/TID="+tid,"newwindow","height=400,width=400,top=180,left=500,toolbar=no,menubar=no,scrollbars=no,resizablr=no,locatiog=no,status=no");
}
function viewCT(id,tid)
{
	window.open("trm/main/ztfinanceMng/queryCTlist.?TA_ZT_QDQKJL4CT/CTID="+id+"&TA_ZT_QDQKJL4CT/TID="+tid,"newwindow2","height=400,width=600,top=180,left=400,toolbar=no,menubar=no,scrollbars=yes,resizablr=no,locatiog=no,statusno");
}
function addJD(id,tid)
{
	window.open("trm/main/ztfinanceMng/getBXJingDByTid.?TA_ZT_QDQKJL4JD/JDID="+id+"&TA_ZT_QDQKJL4JD/TID="+tid,"newwindow","height=400,width=400,top=180,left=500,toolbar=no,menubar=no,scrollbars=no,resizablr=no,locatiog=no,status=no");
}
function viewJD(id,tid)
{
	window.open("trm/main/ztfinanceMng/queryJingDlist.?TA_ZT_QDQKJL4JD/JDID="+id+"&TA_ZT_QDQKJL4JD/TID="+tid,"newwindow2","height=400,width=600,top=180,left=400,toolbar=no,menubar=no,scrollbars=yes,resizablr=no,locatiog=no,status=no");
}
function addTR(id,tid)
{
	window.open("trm/main/ztfinanceMng/getBXTRByTid.?TA_ZT_QKJL4TRA/DJS="+id+"&TA_ZT_QKJL4TRA/TID="+tid,"newwindow","height=400,width=400,top=180,left=500,toolbar=no,menubar=no,scrollbars=no,resizablr=no,locatiog=no,status=no");
}
function viewTR(id,tid)
{
	window.open("trm/main/ztfinanceMng/queryTRlist.?TA_ZT_QKJL4TRA/DJS="+id+"&TA_ZT_QKJL4TRA/TID="+tid,"newwindow2","height=400,width=600,top=180,left=400,toolbar=no,menubar=no,scrollbars=yes,resizablr=no,locatiog=no,status=no");
}
</script>

</head>
<body>

<div id="tabs">
	<ul>
		<li><a href="#tabs-1">�Ƶ�����¼</a></li>
		<li><a href="#tabs-2">��������¼</a></li>
		<li><a href="#tabs-3">Ʊ������¼</a></li>
		<li><a href="#tabs-4">��������¼</a></li>
		<li><a href="#tabs-5">��������¼</a></li>
		<li><a href="#tabs-6">�ؽ�����¼</a></li>
	</ul>
	<div id="tabs-1" style="margin-left: 0px;">
<%
	int pageNO=0;
	int pageSize=0;
	int totalPage =0;
	int rowsCount = 0;
	if(rd.getTableRowsCount("rsQingDanHotelList")>0){
		pageNO=Integer.parseInt(rd.getString("pageNO"));
		pageSize=Integer.parseInt(rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("rsQingDanHotelList", "pagesCount");
		rowsCount=(Integer)rd.getAttr("rsQingDanHotelList", "rowsCount");
	}
%>   
<form  name="dj_guide_form1" method="post">
		
	  <table class="datas">
		<tr>
		  <td>�ſ�ʼ���ڣ�</td>
		  <td><input type="text" name="bdate" id="dateB" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td>�Ž������ڣ�</td>
		  <td><input type="text" name="edate" id="dateE" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
				  <td>���״̬��</td>
		  <td>
			<select id="dyid0" name="state" > 			
			<option value="" >***��ѡ��***</option>
			<option value="Y"   >�����</option>
			<option value="N" >δ���</option>
			</select>
		  </td>		
		</tr>
		
		<tr>
		  <td>�źţ�</td>
		  <td><input type="text" name="groupID" /></input></td>
		  <td>�Ƶ����ƣ�</td>
		  <td><input type="text"  name="jdmc"/></td>
		<td colspan="2" align="center"><a href="###" onclick="findByLike();"><img alt="����" src="<%=request.getContextPath()%>/images/search.gif"/></a></td>
		</tr>		
	  </table>


	<table class="datas" >
		<tr id="first-tr">
			<td align="center" >�ź�</td>
			<td align="center" >�Ƶ�����</td>
			<td align="center" >ǩ���ϼ�</td>
			<td align="center" >������</td>
			<td align="center" >����</td>
		</tr>
		<%
		int rows = rd.getTableRowsCount("rsQingDanHotelList");
		for(int i=0;i<rows;i++) {
			String tid = rd.getStringByDI("rsQingDanHotelList","id",i);//�ź�
			String id = rd.getStringByDI("rsQingDanHotelList","hotel_id",i);//�Ƶ�ź�
			String city = rd.getStringByDI("rsQingDanHotelList","hotel_name",i);//����
			String jd = rd.getStringByDI("rsQingDanHotelList","hotel_address",i);//�Ƶ�
			String qdxj = rd.getStringByDI("rsQingDanHotelList","qdxj",i);//ǩ��С��
			String hkje=rd.getStringByDI("rsQingDanHotelList","hkje",i);
			String qdzt=rd.getStringByDI("rsQingDanHotelList","qkzt",i);
	if ("Y".equals(qdzt))
			%>

		<tr>
            <td align="center" ><%=tid %></td>
	  		<td align="center" ><%=city %></td>
	  		<td align="center" ><%=qdxj %>Ԫ</td>
	  		<td align="center" ><%=hkje %>Ԫ</td>	

	  		<td align="center" ><% if("N".equals(qdzt)){ %>
	  			<a href="###" onclick="addHotel('<%=id %>','<%=tid %>');"  >
           		<img alt="�༭" src="<%=request.getContextPath()%>/images/edit.gif">[����ǩ��]&nbsp;</a>
           		<%} %>
           		<a href="###" onclick="viewHotel('<%=id %>','<%=tid %>');"  >
           		<img alt="�鿴" src="<%=request.getContextPath()%>/images/edit.gif">[�鿴����嵥]&nbsp;</a>
            </td>
         </tr>	
	<%} %>		
			
	</table>
	<div id="page">
	<%String url=request.getContextPath()+"/maindj/financeMng/AllQiandanList.?pageSize=10&pageNO=";%>
	<span class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					��<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> ҳ��
					��<%=rowsCount %> ����¼��
					ÿҳ <%=pageSize%>��
	</span>
	<span class="next-page" onclick='query("<%=pageNO+1>totalPage?totalPage:pageNO+1 %>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="last-page" onclick='query("<%=totalPage%>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="go-to-page" >
		��ת���ڣ�<input type="text" id="gotopage"/> ҳ
	</span>
	<span class="go-to-page-icon" onclick='go("<%=totalPage%>","<%=(int)Math.min(pageNO,totalPage)%>","<%=url %>")'></span>
</div>
</form>

	</div>
	
	
	<div id="tabs-2">
<%
	int pageNO2=0;
	int pageSize2=0;
	int totalPage2 =0;
	int rowsCount2 = 0;
	if(rd.getTableRowsCount("queryAllCDList")>0){
		pageNO2=Integer.parseInt(rd.getString("pageNO"));
		pageSize2=Integer.parseInt(rd.getString("pageSize"));
		totalPage2=(Integer)rd.getAttr("queryAllCDList", "pagesCount");
		rowsCount2 = (Integer)rd.getAttr("queryAllCDList", "rowsCount");
	}
%>   
<form  name="dj_guide_form2" method="post">
<table class="datas" >	
		<tr>
		  <td>�ſ�ʼ���ڣ�</td>
		  <td><input type="text" name="bdate" id="dateB" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td>�Ž������ڣ�</td>
		  <td><input type="text" name="edate" id="dateE" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td>���״̬��</td>
		  <td>
			<select id="dyid0" name="state" > 			
			<option value="" >***��ѡ��***</option>
			<option value="Y"   >�����</option>
			<option value="N" >δ���</option>
			</select>
		  </td>
		</tr>
		<tr>
			<td >�źţ�</td>
			<td><input type="text" name="groupID" /></td>
			<td >�������ƣ�</td>
			<td><input type="text"  name="cdmc"/></td>
			<td colspan="2" align="center" ><a href="###" onclick="findByLikeCD();"><img alt="����" src="<%=request.getContextPath() %>/images/search.gif"></a></td>			
		</tr>
	</table>
	<table class="datas" >
		<tr id="first-tr">
			<td align="center" >�ź�</td>
			<td align="center" >��������</td>
			<td align="center" >ǩ���ϼ�</td>
			<td align="center" >������</td>
			<td align="center" >����</td>
		</tr>
		<%
		int rows2 = rd.getTableRowsCount("queryAllCDList");
		for(int i=0;i<rows2;i++) {
			String tid = rd.getStringByDI("queryAllCDList","tid",i);//�ź�
			String id = rd.getStringByDI("queryAllCDList","cd",i);//����ID
			String cdm = rd.getStringByDI("queryAllCDList","cmpny_name",i);//������
			String qdxj = rd.getStringByDI("queryAllCDList","qdje",i);//ǩ��С��
			String hkje=rd.getStringByDI("queryAllCDList","hkje",i);
			String qkzt=rd.getStringByDI("queryAllCDList","qkzt",i);
		%>
		<tr>
            <td align="center" ><%=tid %></td>
	  		<td align="center" ><%=cdm %></td>
	  		<td align="center" ><%=qdxj %>Ԫ</td>
	  		<td align="center" ><%=hkje %>Ԫ</td>	  
	  		<td align="center" ><%if("N".equals(qkzt)){ %>
	  			<a href="###" onclick="addVeh('<%=id %>','<%=tid %>');"  >
           		<img alt="�༭" src="<%=request.getContextPath()%>/images/edit.gif">[����ǩ��]&nbsp;</a>
           	<%} %>
           		<a href="###" onclick="viewVeh('<%=id %>','<%=tid %>');"  >
           		<img alt="�鿴" src="<%=request.getContextPath()%>/images/edit.gif">[�鿴����嵥]&nbsp;</a>
            </td>
         </tr>	
	<%	} %>		
			
	</table>
	<div id="page">
	<%String url2=request.getContextPath()+"/maindj/financeMng/AllCDList.?pageSize=10&pageNO=";%>
	<span title="��ҳ" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO2,totalPage2)%>","<%=url2%>","<%=totalPage2%>")'></span>		
	<span title="��һҳ" class="prev-page" onclick='query("<%=pageNO2-1<1?1:pageNO2-1 %>","P","<%=(int)Math.min(pageNO2,totalPage2)%>","<%=url2%>","<%=totalPage2%>")'></span>
	<span class="pagination-sum">
					��<%=Math.min(pageNO2,totalPage2)%>/<%=totalPage2%> ҳ��
					��<%=(Integer)rd.getTableRowsCount("queryAllCDList")>0?(Integer)rd.getTableRowsCount("queryAllCDList"):0%> ����¼��
					ÿҳ <%=pageSize2%>��
	</span>
	<span title="��һҳ" class="next-page" onclick='query("<%=pageNO2+1>totalPage2?totalPage2:pageNO2+1 %>","N","<%=(int)Math.min(pageNO2,totalPage2)%>","<%=url2%>","<%=totalPage2%>")'></span>
	<span title="βҳ" class="last-page" onclick='query("<%=totalPage2%>","N","<%=(int)Math.min(pageNO2,totalPage2)%>","<%=url2%>","<%=totalPage2%>")'></span>
	<span class="go-to-page" >
		��ת���ڣ�<input type="text" id="gotopage"/> ҳ
	</span>
	<span title="��ת" class="go-to-page-icon" onclick='go("<%=totalPage2%>","<%=(int)Math.min(pageNO2,totalPage2)%>","<%=url2 %>")'></span>
</div>
</form>
	</div>
	
	
	<div id="tabs-3">
	 <%
	int pageNO3=0;
	int pageSize3=0;
	int totalPage3 =0;
	int rowsCount3 = 0;
	if(rd.getTableRowsCount("queryAllPWList")>0){
		pageNO3=Integer.parseInt(rd.getString("pageNO"));
		pageSize3=Integer.parseInt(rd.getString("pageSize"));
		totalPage3=(Integer)rd.getAttr("queryAllPWList", "pagesCount");
		rowsCount3 = (Integer)rd.getAttr("queryAllPWList", "rowsCount");
	}
%>   
<form  name="dj_guide_form3" method="post">
<table class="datas"  width="90%">
		<tr>
		  <td>�ſ�ʼ���ڣ�</td>
		  <td><input type="text" name="bdate" id="dateB" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td>�Ž������ڣ�</td>
		  <td><input type="text" name="edate" id="dateE" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td>���״̬��</td>
		  <td>
			<select id="dyid0" name="state" > 			
			<option value="" >***��ѡ��***</option>
			<option value="Y"   >�����</option>
			<option value="N" >δ���</option>
			</select>
		  </td>
		</tr>
		<tr>
			<td >�źţ�</td>
			<td><input type="text" name="groupID" /></td>
			<td>���������ƣ�</td>
			<td><input type="text"  name="dgdmc"/></td>
			<td colspan="2" align="center"><a href="###" onclick="findByLikePW()"><img alt="����" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>

	</table>
	<table class="datas" >
		<tr id="first-tr">
			<td align="center" >�ź�</td>
			<td align="center" >����������</td>
			<td align="center" >ǩ���ϼ�</td>
			<td align="center" >������</td>
			<td align="center" >����</td>
		</tr>
		<%
		int rows3 = rd.getTableRowsCount("queryAllPWList");
		for(int i=0;i<rows3;i++) {
			String tid = rd.getStringByDI("queryAllPWList","id",i);//�ź�
			String id = rd.getStringByDI("queryAllPWList","dgd",i);//������ID
			String dgdm = rd.getStringByDI("queryAllPWList","cmpny_name",i);//��������
			String dgddz = rd.getStringByDI("queryAllPWList","cmpny_address",i);//�������ַ
			String qdxj = rd.getStringByDI("queryAllPWList","qdxj",i);//ǩ��С��
			String hkje=rd.getStringByDI("queryAllPWList","hkje",i);//������
			String qkzt=rd.getStringByDI("queryAllPWList","qkzt",i);
		%>
		<tr>
            <td align="center" ><%=tid %></td>
	  		<td align="center" ><%=dgdm %></td>
	  		<td align="center" ><%=qdxj %>Ԫ</td>
	  		<td align="center" ><%=hkje %>Ԫ</td>	  
	  		<td align="center" ><%if("N".equals(qkzt)){ %>
	  			<a href="###" onclick="addPW('<%=id %>','<%=tid %>');"  >
           		<img alt="�༭" src="<%=request.getContextPath()%>/images/edit.gif">[����ǩ��]&nbsp;</a>
           		<%} %>
           		<a href="###" onclick="viewPW('<%=id %>','<%=tid %>');"  >
           		<img alt="�鿴" src="<%=request.getContextPath()%>/images/edit.gif">[�鿴����嵥]&nbsp;</a>
            </td>
         </tr>	
	<%} %>		
			
	</table>
	<div id="page">
	<%String url3=request.getContextPath()+"/maindj/financeMng/AllPWList.?pageSize=10&pageNO=";%>
	<span title="��ҳ" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO3,totalPage3)%>","<%=url3%>","<%=totalPage3%>")'></span>		
	<span title="��һҳ" class="prev-page" onclick='query("<%=pageNO3-1<1?1:pageNO3-1 %>","P","<%=(int)Math.min(pageNO3,totalPage3)%>","<%=url3%>","<%=totalPage3%>")'></span>
	<span class="pagination-sum">
					��<%=Math.min(pageNO3,totalPage3)%>/<%=totalPage3%> ҳ��
					��<%=(Integer)rd.getTableRowsCount("queryAllPWList")>0?(Integer)rd.getTableRowsCount("queryAllPWList"):0%>  ����¼��
					ÿҳ <%=pageSize3%>��
	</span>
	<span title="��һҳ" class="next-page" onclick='query("<%=pageNO3+1>totalPage3?totalPage3:pageNO3+1 %>","N","<%=(int)Math.min(pageNO3,totalPage3)%>","<%=url3%>","<%=totalPage3%>")'></span>
	<span title="βҳ" class="last-page" onclick='query("<%=totalPage3%>","N","<%=(int)Math.min(pageNO3,totalPage3)%>","<%=url3%>","<%=totalPage3%>")'></span>
	<span class="go-to-page" >
		��ת���ڣ�<input type="text" id="gotopage"/> ҳ
	</span>
	<span title="��ת" class="go-to-page-icon" onclick='go("<%=totalPage3%>","<%=(int)Math.min(pageNO3,totalPage3)%>","<%=url3 %>")'></span>
	</div>
	</form>
	</div>
	
	
	<div id="tabs-4">
	<%
	int pageNO4=0;
	int pageSize4=0;
	int totalPage4 =0;
	int rowsCount4 = 0;
	if(rd.getTableRowsCount("queryAllctList")>0){
		pageNO4=Integer.parseInt(rd.getString("pageNO"));
		pageSize4=Integer.parseInt(rd.getString("pageSize"));
		totalPage4=(Integer)rd.getAttr("queryAllctList", "pagesCount");
		rowsCount4 = (Integer)rd.getAttr("queryAllctList", "rowsCount");
	}
%>   
<form  name="dj_guide_form4" method="post">
<table class="datas"  width="90%">
		<tr>
		  <td>�ſ�ʼ���ڣ�</td>
		  <td><input type="text" name="bdate" id="dateB" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td>�Ž������ڣ�</td>
		  <td><input type="text" name="edate" id="dateE" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td>���״̬��</td>
		  <td>
			<select id="dyid0" name="state" > 			
			<option value="" >***��ѡ��***</option>
			<option value="Y"   >�����</option>
			<option value="N" >δ���</option>
			</select>
		  </td>
		</tr>
		<tr>

			<td >�źţ�</td>
			<td><input type="text" name="groupID" /></td>
			<td >�������ƣ�</td>
			<td><input type="text"  name="ctmc"/></td>
			<td colspan="2" align="center"><a href="###" onclick="findByLikeCT()"><img alt="����" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>

	</table>
	<table class="datas" >
		<tr id="first-tr">
			<td align="center" >�ź�</td>
			<td align="center" >��������</td>
			<td align="center" >ǩ���ϼ�</td>
			<td align="center" >������</td>
			<td align="center" >����</td>
		</tr>
		<%
		int rows4 = rd.getTableRowsCount("queryAllctList");
		for(int i=0;i<rows4;i++) {
			String tid = rd.getStringByDI("queryAllctList","id",i);//�ź�
			String id = rd.getStringByDI("queryAllctList","dining_room_id",i);//����ID
			String ctmc = rd.getStringByDI("queryAllctList","cmpny_name",i);//��������
			String ctdz = rd.getStringByDI("queryAllctList","cmpny_address",i);//������ַ
			String qdxj = rd.getStringByDI("queryAllctList","qdxj",i);//ǩ��С��
			String hkje=rd.getStringByDI("queryAllctList","hkje",i);
			String qkzt=rd.getStringByDI("queryAllctList","qkzt",i);
		%>
		<tr>
            <td align="center" ><%=tid %></td>
	  		<td align="center" ><%=ctmc %></td>
	  		<td align="center" ><%=qdxj %>Ԫ</td>
	  		<td align="center" ><%=hkje %>Ԫ</td>	  
	  		<td align="center" ><%if("N".equals(qkzt)){ %>
	  			<a href="###" onclick="addCT('<%=id %>','<%=tid %>');"  >
           		<img alt="�༭" src="<%=request.getContextPath()%>/images/edit.gif">[����ǩ��]&nbsp;</a>
           		<%} %>
           		<a href="###" onclick="viewCT('<%=id %>','<%=tid %>');"  >
           		<img alt="�鿴" src="<%=request.getContextPath()%>/images/edit.gif">[�鿴����嵥]&nbsp;</a>
            </td>
         </tr>	
	<%} %>		
			
	</table>
	<div id="page">
	<%String url4=request.getContextPath()+"/maindj/financeMng/AllCTQiandanList.?pageSize=10&pageNO=";%>
	<span title="��ҳ" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO4,totalPage4)%>","<%=url4%>","<%=totalPage4%>")'></span>		
	<span title="��һҳ" class="prev-page" onclick='query("<%=pageNO4-1<1?1:pageNO4-1 %>","P","<%=(int)Math.min(pageNO4,totalPage4)%>","<%=url4%>","<%=totalPage4%>")'></span>
	<span class="pagination-sum">
					��<%=Math.min(pageNO4,totalPage4)%>/<%=totalPage4%> ҳ��
					��<%=(Integer)rd.getTableRowsCount("queryAllctList")>0?(Integer)rd.getTableRowsCount("queryAllctList"):0%> ����¼��
					ÿҳ <%=pageSize4%>��
	</span>
	<span title="��һҳ" class="next-page" onclick='query("<%=pageNO4+1>totalPage4?totalPage4:pageNO4+1 %>","N","<%=(int)Math.min(pageNO4,totalPage4)%>","<%=url4%>","<%=totalPage4%>")'></span>
	<span title="βҳ" class="last-page" onclick='query("<%=totalPage4%>","N","<%=(int)Math.min(pageNO4,totalPage4)%>","<%=url4%>","<%=totalPage4%>")'></span>
	<span class="go-to-page" >
		��ת���ڣ�<input type="text" id="gotopage"/> ҳ
	</span>
	<span title="��ת" class="go-to-page-icon" onclick='go("<%=totalPage4%>","<%=(int)Math.min(pageNO4,totalPage4)%>","<%=url4 %>")'></span>
	</div>
	</form>
	 
	</div>
	
	
	<div id="tabs-5">
	<%
	int pageNO5=0;
	int pageSize5=0;
	int totalPage5 =0;
	int rowsCount5 = 0;
	if(rd.getTableRowsCount("queryAlljingdList")>0){
		pageNO5=Integer.parseInt(rd.getString("pageNO"));
		pageSize5=Integer.parseInt(rd.getString("pageSize"));
		totalPage5=(Integer)rd.getAttr("queryAlljingdList", "pagesCount");
		rowsCount5 = (Integer)rd.getAttr("queryAlljingdList", "rowsCount");
	}
%>   
<form  name="dj_guide_form5" method="post">
<table class="datas" width="90%" >
		<tr>
		  <td>�ſ�ʼ���ڣ�</td>
		  <td><input type="text" name="bdate" id="dateB" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td>�Ž������ڣ�</td>
		  <td><input type="text" name="edate" id="dateE" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td>���״̬��</td>
		  <td>
			<select id="dyid0" name="state" > 			
			<option value="" >***��ѡ��***</option>
			<option value="Y"   >�����</option>
			<option value="N" >δ���</option>
			</select>
		  </td>
		</tr>
		<tr>
			<td >�źţ�</td>
			<td><input type="text" name="groupID" /></td>
			<td >�������ƣ�</td>
			<td><input type="text"  name="jingdianmc"/></td>
			<td colspan="2" align="center"><a href="###" onclick="findByLikeJD()"><img alt="����" src="<%=request.getContextPath() %>/images/search.gif"></a></td>	
		</tr>
	</table>
	<table class="datas" >
		<tr id="first-tr">
			<td align="center" >�ź�</td>
			<td align="center" >��������</td>
			<td align="center" >ǩ���ϼ�</td>
			<td align="center" >������</td>
			<td align="center" >����</td>
		</tr>
		<%
		int rows5 = rd.getTableRowsCount("queryAlljingdList");
		for(int i=0;i<rows5;i++) {
			String tid = rd.getStringByDI("queryAlljingdList","id",i);//�ź�
			String id = rd.getStringByDI("queryAlljingdList","scenery_id",i);//����ID
			String jdmc = rd.getStringByDI("queryAlljingdList","cmpny_name",i);//������
			String jddz = rd.getStringByDI("queryAlljingdList","cmpny_address",i);//�����ַ
			String qdxj = rd.getStringByDI("queryAlljingdList","qdxj",i);//ǩ��С��
			String hkje=rd.getStringByDI("queryAlljingdList","hkje",i);//������
			String qkzt=rd.getStringByDI("queryAlljingdList","qkzt",i);
		%>
		<tr>
            <td align="center" ><%=tid %></td>
	  		<td align="center" ><%=jdmc %></td>
	  		<td align="center" ><%=qdxj %>Ԫ</td>
	  		<td align="center" ><%=hkje %>Ԫ</td>	  
	  		<td align="center" ><%if("N".equals(qkzt)){ %>
	  			<a href="###" onclick="addJD('<%=id %>','<%=tid %>');"  >
           		<img alt="�༭" src="<%=request.getContextPath()%>/images/edit.gif">[����ǩ��]&nbsp;</a>
           		<% }%>
           		<a href="###" onclick="viewJD('<%=id %>','<%=tid %>');"  >
           		<img alt="�鿴" src="<%=request.getContextPath()%>/images/edit.gif">[�鿴����嵥]&nbsp;</a>
            </td>
         </tr>	
	<%} %>		
			
	</table>
	<div id="page">
	<%String url5=request.getContextPath()+"/maindj/financeMng/AllJingDList.?pageSize=10&pageNO=";%>
	<span title="��ҳ" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO5,totalPage5)%>","<%=url5%>","<%=totalPage5%>")'></span>		
	<span title="��һҳ" class="prev-page" onclick='query("<%=pageNO5-1<1?1:pageNO5-1 %>","P","<%=(int)Math.min(pageNO5,totalPage5)%>","<%=url5%>","<%=totalPage5%>")'></span>
	<span class="pagination-sum">
					��<%=Math.min(pageNO5,totalPage5)%>/<%=totalPage5%> ҳ��
					��<%=(Integer)rd.getTableRowsCount("queryAlljingdList")>0?(Integer)rd.getTableRowsCount("queryAlljingdList"):0 %> ����¼��
					ÿҳ <%=pageSize5%>��
	</span>
	<span title="��һҳ" class="next-page" onclick='query("<%=pageNO5+1>totalPage5?totalPage5:pageNO5+1 %>","N","<%=(int)Math.min(pageNO5,totalPage5)%>","<%=url5%>","<%=totalPage5%>")'></span>
	<span title="βҳ" class="last-page" onclick='query("<%=totalPage5%>","N","<%=(int)Math.min(pageNO5,totalPage5)%>","<%=url5%>","<%=totalPage5%>")'></span>
	<span class="go-to-page" >
		��ת���ڣ�<input type="text" id="gotopage"/> ҳ
	</span>
	<span title="��ת" class="go-to-page-icon" onclick='go("<%=totalPage5%>","<%=(int)Math.min(pageNO5,totalPage5)%>","<%=url5 %>")'></span>
</div>
</form>
	
	</div>
	
	<div id="tabs-6">
	<%
	int pageNO6=0;
	int pageSize6=0;
	int totalPage6 =0;
	int rowsCount6 = 0;
	if(rd.getTableRowsCount("queryAllTRList")>0){
		pageNO6=Integer.parseInt(rd.getString("pageNO"));
		pageSize6=Integer.parseInt(rd.getString("pageSize"));
		totalPage6=(Integer)rd.getAttr("queryAllTRList", "pagesCount");
		rowsCount6=(Integer)rd.getAttr("queryAllTRList", "rowsCount");
	}
%>   
<form  name="dj_guide_form6" method="post">
<table class="datas"  width="90%">
		<tr>
		  <td>�ſ�ʼ���ڣ�</td>
		  <td><input type="text" name="bdate" id="dateB" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td>�Ž������ڣ�</td>
		  <td><input type="text" name="edate" id="dateE" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td>���״̬��</td>
		  <td>
			<select id="dyid0" name="state" > 			
			<option value="" >***��ѡ��***</option>
			<option value="Y"   >�����</option>
			<option value="N" >δ���</option>
			</select>
		  </td>
		</tr>
		<tr>		
			<td >�źţ�</td>
			<td><input type="text" name="groupID" /></td>
			<td >�ؽ����ƣ�</td>
			<td><input type="text"  name="hzsmc"/></td>
			<td colspan="2" align="center"><a href="###" onclick="findByLikeTR()"><img alt="����" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
	<table class="datas" >
		<tr id="first-tr">
			<td align="center" >�ź�</td>
			<td align="center" >�ؽ�����</td>
			<td align="center" >ǩ���ϼ�</td>
			<td align="center" >������</td>
			<td align="center" >����</td>
		</tr>
		<%
		int rows6 = rd.getTableRowsCount("queryAllTRList");
		for(int i=0;i<rows6;i++) {
			String tid = rd.getStringByDI("queryAllTRList","tid",i);//�ź�
			String id = rd.getStringByDI("queryAllTRList","djsid",i);//������ID
			String jdmc = rd.getStringByDI("queryAllTRList","cmpny_name",i);//��������
			String jddz = rd.getStringByDI("queryAllTRList","cmpny_address",i);//�����ַ
			String qdxj = rd.getStringByDI("queryAllTRList","qdje",i);//ǩ��С��
			String hkje=rd.getStringByDI("queryAllTRList","hkje",i);//������
			String qkzt=rd.getStringByDI("queryAllTRList","qkzt",i);
		%>
		<tr>
            <td align="center" ><%=tid %></td>
	  		<td align="center" ><%=jdmc %></td>
	  		<td align="center" ><%=qdxj %>Ԫ</td>
	  		<td align="center" ><%=hkje %>Ԫ</td>	  
	  		<td align="center" ><%if("N".equals(qkzt)){ %>
	  			<a href="###" onclick="addTR('<%=id %>','<%=tid %>');"  >
           		<img alt="�༭" src="<%=request.getContextPath()%>/images/edit.gif">[����ǩ��]&nbsp;</a>
           		<%} %>
           		<a href="###" onclick="viewTR('<%=id %>','<%=tid %>');"  >
           		<img alt="�鿴" src="<%=request.getContextPath()%>/images/edit.gif">[�鿴����嵥]&nbsp;</a>
            </td>
         </tr>	
	<%} %>		
			
	</table>
	<div id="page">
	<%String url6=request.getContextPath()+"/maindj/financeMng/AllTRList.?pageSize=10&pageNO=";%>
	<span title="��ҳ" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO6,totalPage6)%>","<%=url6%>","<%=totalPage6%>")'></span>		
	<span title="��һҳ" class="prev-page" onclick='query("<%=pageNO6-1<1?1:pageNO6-1 %>","P","<%=(int)Math.min(pageNO6,totalPage6)%>","<%=url6%>","<%=totalPage6%>")'></span>
	<span class="pagination-sum">
					��<%=Math.min(pageNO6,totalPage6)%>/<%=totalPage6%> ҳ��
					��<%=(Integer)rd.getTableRowsCount("queryAllTRList")>0?(Integer)rd.getTableRowsCount("queryAllTRList"):0 %> ����¼��
					ÿҳ <%=pageSize6%>��
	</span>
	<span title="��һҳ" class="next-page" onclick='query("<%=pageNO6+1>totalPage5?totalPage6:pageNO5+1 %>","N","<%=(int)Math.min(pageNO5,totalPage5)%>","<%=url6%>","<%=totalPage6%>")'></span>
	<span title="βҳ" class="last-page" onclick='query("<%=totalPage6%>","N","<%=(int)Math.min(pageNO6,totalPage6)%>","<%=url6%>","<%=totalPage5%>")'></span>
	<span class="go-to-page" >
		��ת���ڣ�<input type="text" id="gotopage"/> ҳ
	</span>
	<span title="��ת" class="go-to-page-icon" onclick='go("<%=totalPage6%>","<%=(int)Math.min(pageNO6,totalPage6)%>","<%=url6 %>")'></span>
</div>
</form>
	
	</div>	
</div>
</body>
</html>