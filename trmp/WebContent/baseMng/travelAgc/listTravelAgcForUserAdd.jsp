<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
    
<%@include file="/common.jsp"%>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("TA_TRAVELAGENCYS")>0){
     pageNO=Integer.parseInt((String)rd.getAttr("TA_TRAVELAGENCY", "pageNO"));
	 pageSize=Integer.parseInt((String)rd.getAttr("TA_TRAVELAGENCY", "pageSize"));
		totalPage=(Integer)rd.getAttr("TA_TRAVELAGENCYS", "pagesCount");
		rowsCount = (Integer)rd.getAttr("TA_TRAVELAGENCYS", "rowsCount");
	}

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>������������Ϣ</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<!-- checkbox��ȫѡ -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/selectAll.js"></script>
</head>
<script language="javascript">
function selectAndClose(id,nm) {

	parent.window.opener.document.getElementById("userFrom").value = id;
	parent.window.opener.document.getElementById("userFromNm").value =nm;
	window.parent.parent.location.reload(); 
	window.parent.parent.GB_hide(); 
}

//����������ѯ
function findTravelagency(){
	
	var companyName = document.getElementById("CMPNY_NAME").value;
	document.getElementById("CMPNY_NAME").value = "%"+companyName+"%";
	document.myForm.action="listTravelAgcForUserAdd.?ta_travelagency@pageNO=1&ta_travelagency@pageSize=10";
	document.myForm.submit();
		
}
</script>
<body onkeydown="if(event.keyCode==13)  {event.keyCode=9};" >
<form name="myForm" method="post">
<div id="ex3a" class="jqmDialog">
<div style="margin:0 4px; background:#76C6CC; height:1px; overflow:hidden;"></div>
<div style="margin:0 2px; border:1px solid #76C6CC; border-width:0 2px; background:#76C6CC; height:1px; overflow:hidden;"></div>
<div style="margin:0 1px; border:1px solid #76C6CC; border-width:0 1px; background:#76C6CC; height:1px; overflow:hidden;"></div>
<div style="margin:0 1px; border:1px solid #76C6CC; border-width:0 1px; background:#76C6CC; height:1px; overflow:hidden;"></div>
<div style="background:#76C6CC; border:1px solid #76C6CC; border-width:0 1px;height: 200px;">
	<div style="font-size:12px; font-weight:bolder; font-family:Verdana; color:#006666; padding:2px 10px 5px;">
		��ѯ����
	</div>
	<div style="background:#FFF; margin:0 3px; font-size:11px; font-family:Verdana; padding:5px 10px; overflow:hidden;height: 180px;">
		 ���������ƣ�<input type="text" name="ta_travelagency/CMPNY_NAME" id="CMPNY_NAME" class="text_input"/>&nbsp;
		 <input type="hidden" name="ta_travelagency/CMPNY_NAME[0]@relation" class="text_input" value="like" id="cmpnyNameOld"/>
		  <input type="button" value="��ѯ"  onClick="findTravelagency();"/>
	</div>
</div>
<div style="margin:0 1px; border:1px solid #76C6CC; border-width:0 1px; background:#76C6CC; height:1px; overflow:hidden;"></div>
<div style="margin:0 1px; border:1px solid #76C6CC; border-width:0 2px; background:#76C6CC; height:1px; overflow:hidden;"></div>
<div style="margin:0 2px; border:1px solid #76C6CC; border-width:0 2px; background:#76C6CC; height:1px; overflow:hidden;"></div>
<div style="margin:0 4px; background:#76C6CC; height:1px; overflow:hidden;"></div> 
	<input type="image" src="<%=request.getContextPath() %>/images/if-select-img/close.gif" class="jqmdX jqmClose" />
</div>


<div id="lable"><span>�����������Ϣά��</span></div>
<div id="top-select">
<div class="select-div" >
	  <span class="text" id="select-condition">��ѯ����</span>
</div>

</div>

<div id="list-table">
<table class="datas">
	<tr id="first-tr">
		<td width="15%">��˾ȫ��</td>
        <td width="15%">��˾��ַ</td>
        <td width="12%">�ܸ�����-�ֻ�����</td>
        <td width="12%">ҵ��Ա-�ֻ�����</td>	
        <td width="10%">ҵ������</td>
	</tr>
	<%
		int rows = rd.getTableRowsCount("ta_travelagencys");
		for(int i=0;i<rows;i++){
			
			String traAgcID = rd.getStringByDI("ta_travelagencys","TRAVEL_AGC_ID",i);
	%>
		<tr>
            <td ><a href="###" onclick="selectAndClose('<%=traAgcID %>','<%=rd.getStringByDI("ta_travelagencys","CMPNY_NAME",i)%>');">
            	<%=rd.getStringByDI("ta_travelagencys","CMPNY_NAME",i)%>
            	</a>
            </td>
	  		<td >
				<%=rd.getStringByDI("ta_travelagencys","CMPNY_ADDRESS",i)%>
			</td>
	  		<td >
				<%=rd.getStringByDI("ta_travelagencys","CHIEF_NAME",i)%>-<%=rd.getStringByDI("ta_travelagencys","CHIEF_MOBILE",i)%>
			</td>
            <td >
            	<%=rd.getStringByDI("ta_travelagencys","BUSINESS_NAME",i)%>-<%=rd.getStringByDI("ta_travelagencys","BUSINESS_MOBILE",i)%>
            </td>
            <td >
				<%
					if(rd.getStringByDI("ta_travelagencys","BUSINESS_TYPE",i).equals("1"))
						out.print("����ҵ��");
					if(rd.getStringByDI("ta_travelagencys","BUSINESS_TYPE",i).equals("2"))
						out.print("��˾ҵ��");
				%>
			</td>
         </tr>	
     <%} %> 
</table>
</div>
<div id="page">
	<%String url="listTravelAgcForUserAdd.?ta_travelagency/TRAVEL_AGC_ID=&ta_travelagency@pageSize=10&ta_travelagency@pageNO=";%>
	<span title="��ҳ" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="��һҳ" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					��<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> ҳ��
					��<%=rd.getAttr("ta_travelagencys","rowsCount")==null?"0":rd.getAttr("ta_travelagencys","rowsCount") %> ����¼��
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
