<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<%
	int pageNO=1;
	int pageSize=10;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("TA_DJ_LINEMNGs")>0){
		
	   	pageNO=Integer.parseInt((String)rd.getAttr("TA_DJ_LINEMNG", "pageNO"));
		pageSize=Integer.parseInt((String)rd.getAttr("TA_DJ_LINEMNG", "pageSize"));
		totalPage=(Integer)rd.getAttr("TA_DJ_LINEMNGs", "pagesCount");
		rowsCount = (Integer)rd.getAttr("TA_DJ_LINEMNGs", "rowsCount");
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript">
function djFindByLike(){

	
	document.myForm.submit();
}

function newLinePage(){
	window.location.href="<%=request.getContextPath()%>/maindj/lineRelease/djInitAddLine.?dmsm/cllx=13&dmsm/JDXJ=6&DMSM/JGLX=4&dmsm/fbjh=1&dmsm/jtgj=2&dmsm/xlzt=5&xzqh/layer=1&userno=<%=sd.getString("userno")%>";
}

</script>
<title>ѡ����·</title>

</head>

<body>

<div id="lable" >
<div style="float:left;">
	<span>ȫ����ɢ&nbsp;&nbsp;&nbsp;&nbsp;��Ʒ��</span></div>
</div>
<form name="myForm" method="post" action="lineList4PO.?xzqh/layer=1&TA_DJ_LINEMNG@pageNO=1&TA_DJ_LINEMNG@pageSize=10&TA_DJ_LINEMNG@orderBy=XLID desc">
<div  id="thisSelect-table" >
  <table>
	<tr>
	  <td ><span>��<br/><br/>��</span></td>
	  <td align="right">��·���ƣ�</td>
	  <td>
		<input type="text" name="TA_DJ_LINEMNG/XLMC" id="xlmc"/>
		<input type="hidden" name="TA_DJ_LINEMNG/XLMC[0]@relation" value="like" id="xlmcLike"/>
	  </td>
	  <td colspan="5">
		<a href="###" onclick="djFindByLike();">
		<img alt="����" src="<%=request.getContextPath()%>/images/search.gif"/></a>
	  </td>
	</tr>
  </table>
</div>

</form>
<div id="list-table" >
<table class="datas">
	<tr id="first-tr">
        <td >��·����</td>
        <td >����</td>
        <td >��·����</td>
        <td >����ƻ�</td>
        <td >������Ⱥ�շ�</td>
        <td >�ó�</td>
        <td >����</td>
        <td >���ﰲ��</td>
        <td >�۸�</td>
        <td >����</td>
	</tr>
	<%
		int rows = rd.getTableRowsCount("TA_DJ_LINEMNGs");
		for(int i=0;i<rows;i++) {
			String XLID = rd.getStringByDI("TA_DJ_LINEMNGs","XLID",i);
			String SZLXSMC = rd.getStringByDI("TA_DJ_LINEMNGs","SZLXSMC",i);
			String XLMC = rd.getStringByDI("TA_DJ_LINEMNGs","XLMC",i);
			String TS = rd.getStringByDI("TA_DJ_LINEMNGs","TS",i);
			String NIGHT = rd.getStringByDI("TA_DJ_LINEMNGs","NIGHT",i);
			String XLQY = rd.getStringByDI("TA_DJ_LINEMNGs","XLQY",i);
			String FBJH = rd.getStringByDI("TA_DJ_LINEMNGs","FBJH",i);//����ʱ��
			String MP = rd.getStringByDI("TA_DJ_LINEMNGs","MP",i);//��Ʊ
			String YC = rd.getStringByDI("TA_DJ_LINEMNGs","YC",i);//�ó�
			String DY = rd.getStringByDI("TA_DJ_LINEMNGs","DY",i);//����
			String GW = rd.getStringByDI("TA_DJ_LINEMNGs","GW",i);//���ﰲ��
	%>
	<tr>
	  	<td><img alt="�ȵ���·" src="<%=request.getContextPath()%>/images/hot3.gif"/>&nbsp;
				<%=XLMC.length()<=20?XLMC:XLMC.substring(0,20)+"..."  %>
		</td>
	  	<td><font color="red"><%=TS %></font>��&nbsp;<font color="red"><%=NIGHT %></font>��</td>
	  	<td>
	  		<%
	   		    for(int j=0;j<rd.getTableRowsCount("xzqhs");j++) {
	   		    	String id = rd.getStringByDI("xzqhs","id",j);
	   		    	if(id.equals(XLQY)){out.print(rd.getStringByDI("xzqhs","name",j));}
	   		    }
		  		%>
		</td>
		<td title="<%=FBJH %>">&nbsp;<%=FBJH.length()<=20?FBJH:FBJH.substring(0,20)+"..."   %>&nbsp;</td>
		<td title="<%=MP %>">&nbsp;<%=MP.length()<=20?MP:MP.substring(0,20)+"..."   %>&nbsp;</td>
        <td title="<%=YC %>">&nbsp;<%=YC.length()<=20?YC:YC.substring(0,20)+"..."   %>&nbsp;</td>
        <td title="<%=DY %>">&nbsp;<%=DY.length()<=20?DY:DY.substring(0,20)+"..."   %>&nbsp;</td>
        <td title="<%=GW %>">&nbsp;<%=GW.length()<=20?GW:GW.substring(0,20)+"..."   %>&nbsp;</td>
        <td>
        	<%
        	int jgRows = rd.getTableRowsCount("TA_DJ_XLJGs");
        	for(int j=0;j<jgRows;j++){
        		String xlID = rd.getStringByDI("TA_DJ_XLJGs","XLID",j);
    			String jglx = rd.getStringByDI("TA_DJ_XLJGs","jglx",j);//�۸�����
    			String jg = rd.getStringByDI("TA_DJ_XLJGs","jg",j);//�۸�
    			String dfc = rd.getStringByDI("TA_DJ_XLJGs","dfc",j);//������
    			if(xlID.equals(XLID)){
        	%>
        	�۸����ͣ�<%=jglx %></br>�۸�<%=jg  %></br>������:<%=dfc %></br>
        	<%	}
        	}%>
        </td>
		<td>
			<a href="djInitNewGroup.?yw=2&dmsm/cllx=13&dmsm/JDXJ=6&xzqh/layer=1&dmsm/jtgj=2&dmsm/JGLX=4&TA_DJ_LINEMNG/XLID=<%=XLID %>&TA_DJ_LINEMNG/orgid=<%=sd.getString("orgid") %>&TA_DJ_XLJG/XLID=<%=XLID %>&TA_DJ_XLJG/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAIL/XLID=<%=XLID %>&TA_DJ_LINEDETAIL/orgid=<%=sd.getString("orgid") %>" >
				<img alt="�鿴ȷ�ϼ�" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[��������]
			</a>
			<p>
			<a href="<%=request.getContextPath()%>/maindj/lineRelease/viewDJLine.?dmsm/cllx=13&dmsm/JDXJ=6&xzqh/layer=1&dmsm/fbjh=1&dmsm/jtgj=2&dmsm/JGLX=4&TA_DJ_LINEMNG/XLID=<%=XLID %>&TA_DJ_LINEMNG/orgid=<%=sd.getString("orgid") %>&TA_DJ_XLJG/XLID=<%=XLID %>&TA_DJ_XLJG/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAIL/XLID=<%=XLID %>&TA_DJ_LINEDETAIL/orgid=<%=sd.getString("orgid") %>">
				<img alt="" src="<%=request.getContextPath() %>/images/page.gif"/>&nbsp;[�鿴��Ʒ��ϸ]
			</a>
			<p>
			<a href="djPrintLineXCD.?TA_DJ_LINEMNG/XLID=<%=XLID %>&TA_DJ_LINEMNG/orgid=<%=sd.getString("orgid") %>&TA_DJ_XLJG/XLID=<%=XLID %>&TA_DJ_XLJG/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAIL/XLID=<%=XLID %>&TA_DJ_LINEDETAIL/orgid=<%=sd.getString("orgid") %>&dmsm/cllx=13&DMSM/JDXJ=6&DMSM/JGLX=4">
				<img alt="" src="<%=request.getContextPath() %>/images/Print.gif"/>&nbsp;[ɢ�ͱ��۵�]
			</a>
		</td>
    </tr>
    <%} %>
      
</table>
</div>
<div id="page">
	<%String url="lineList4PO.?xzqh/layer=1&TA_DJ_LINEMNG/XLMC=&TA_DJ_LINEMNG@pageSize=10&TA_DJ_LINEMNG@orderBy=XLID desc&TA_DJ_LINEMNG@pageNO=";%>
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
</body>
</html>
