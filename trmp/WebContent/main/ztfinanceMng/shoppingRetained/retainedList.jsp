<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
 <%@include file="/common.jsp" %>
 <%
	int pageNO=1;
	int pageSize=0;
	int totalPage =1;
	int rowsCount = 0;
	if(rd.getTableRowsCount("queryAllLCshopList")>0){
		pageNO=Integer.parseInt(rd.getString("pageNO"));
		pageSize=Integer.parseInt(rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("queryAllLCshopList", "pagesCount");
		rowsCount = (Integer)rd.getAttr("queryAllLCshopList", "rowsCount");
	}
%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>���Ų������</title>
<link href="<%=request.getContextPath() %>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>


<script type="text/javascript">


function findByLike(){
	document.dj_guide_form.action="AllLCshopList.?pageSize=10&pageNO=1";
	document.dj_guide_form.submit();
}
function addLCshop(gwdid,tid)
{
	window.open("trm/main/ztfinanceMng/getLcShopByTid.?TA_ZT_LC4SHOP/GWDID="+gwdid+"&TA_ZT_LC4SHOP/TID="+tid,"newwindow","height=400,width=400,top=180,left=500,toolbar=no,menubar=no,scrollbars=no,resizablr=no,locatiog=no,status=no");
}
function viewLCshop(gwdid,tid)
{
	window.open("trm/main/ztfinanceMng/queryLCshoplist.?TA_ZT_LC4SHOP/GWDID="+gwdid+"&TA_ZT_LC4SHOP/TID="+tid,"newwindow2","height=400,width=600,top=180,left=400,toolbar=no,menubar=no,scrollbars=yes,resizablr=no,locatiog=no,status=no");
}
</script>
</head>

<body>
<form  name="dj_guide_form" method="post">
<div id="lable"><span>�������������б�</span></div>
<div  id="thisSelect-table" >
	<table>
		<tr>
			<td rowspan="2" width="40" align="center" ><span>��<br/><br/>��</span></td>
			<td align="right">��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ţ�</td>
			<td><input type="text" name="groupID" /></td>
			<td>���״̬��</td>
			<td>
				<select name="qkzt">
					<option value="">***��ѡ��***</option>
					<option value="N">δ���</option>
					<option value="Y">�����</option>
				</select>
			</td>
			<td>��������ƣ�</td>
			<td>  	
			    <select name="sfid" id="sfid0" USEDATA="dataSrc0" SUBCLASS="1"></select>
			    <select name="csid" id="csid0" USEDATA="dataSrc0" SUBCLASS="2"></select>
			  	<select name="gwdid" id="gwdid0" USEDATA="dataSrc0" SUBCLASS="3" ></select>
			</td>
		</tr>
		<tr>
			<td>��ʼʱ�䣺</td>
			<td><input type="text" name="dB" onFocus="WdatePicker({isShowClear:false,readOnly:false});"/></td>
			<td>����ʱ�䣺</td>
			<td><input type="text" name="dE" onFocus="WdatePicker({isShowClear:false,readOnly:false});"/></td>
			<td><a href="###" onclick="findByLike()"><img alt="����" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td>�ź�</td>
			<td>���������</td>
			<td>����ϼ�</td>
			<td>����������</td>
			<td>����</td>
		</tr>
		<%
		int rows = rd.getTableRowsCount("queryAllLCshopList");
		for(int i=0;i<rows;i++) {
			String tid = rd.getStringByDI("queryAllLCshopList","id",i);//�ź�
			String gwdid = rd.getStringByDI("queryAllLCshopList","gwdid",i);//�����ID
			String gwdmc = rd.getStringByDI("queryAllLCshopList","cmpny_name",i);//���������
			String qdxj = rd.getStringByDI("queryAllLCshopList","gslcxj",i);//ǩ��С��
			String hkje=rd.getStringByDI("queryAllLCshopList","hkje",i);
			String qkzt=rd.getStringByDI("queryAllLCshopList","qkzt_lc",i);
		%>
		<tr>
            <td><%=tid %></td>
	  		<td><%=gwdmc %></td>
	  		<td><%=qdxj %>Ԫ</td>
	  		<td><%=hkje %>Ԫ</td>	  
	  		<td><%if("N".equals(qkzt)) {%>
	  			<a href="###" onclick="addLCshop('<%=gwdid %>','<%=tid %>');"  >
           		<img alt="�༭" src="<%=request.getContextPath()%>/images/edit.gif">[���������]&nbsp;</a>
           		<% } %>
           		<a href="###" onclick="viewLCshop('<%=gwdid %>','<%=tid %>');"  >
           		<img alt="�鿴" src="<%=request.getContextPath()%>/images/edit.gif">[�鿴������]&nbsp;</a>
            </td>
         </tr>	
	<%} %>		
			
	</table>
</div>
<div id="page">
	<%String url=request.getContextPath()+"/maindj/financeMng/AllLCshopList.?pageSize=10&pageNO=";%>
	<span title="��ҳ" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="��һҳ" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					��<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> ҳ��
					��<%=rowsCount %> ����¼��
					ÿҳ <%=pageSize%>��
	</span>
	<span title="��һҳ" class="next-page" onclick='query("<%=pageNO+1>totalPage?totalPage:pageNO+1 %>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span title="βҳ" class="last-page" onclick='query("<%=totalPage%>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="go-to-page" >
		��ת���ڣ�<input type="text" id="gotopage"/> ҳ
	</span>
	<span title="��ת" class="go-to-page-icon" onclick='go("<%=totalPage%>","<%=(int)Math.min(pageNO,totalPage)%>","<%=url %>")'></span>
</div>
</form>
</body>
</html>

<script type="text/javascript">

	var linkage = new Linkage("dataSrc0", "<%=request.getContextPath()%>/main/data/Shopz.xml");
	linkage.init();
	
</script>