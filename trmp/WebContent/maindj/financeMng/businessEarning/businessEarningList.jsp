<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
 <%@include file="/common.jsp" %>
   
<%
String gID = rd.getString("groupID");
String bDate = rd.getString("bDate");
String eDate = rd.getString("eDate");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Ӫ������¼</title>
<link href="<%=request.getContextPath() %>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/thickbox/thickbox.css" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<title>Ӫ������¼</title>

<script type="text/javascript">


function findByLike(){

	var groupID = $("#groupID").val();
	var bDate = $("#bDate").val();
	var eDate = $("#eDate").val();
	var state = $("#state").val();
	var url="businessEarningList.?pageSize=10&pageNO=1";
	if(groupID != '')
		url += "&groupID="+groupID;
	if(bDate != '')
		url += "&bDate="+bDate;
	if(eDate != '')
		url += "&eDate="+eDate;
	if(state != '')
		url += "&state="+state;
	window.location.href=url;
}
function addLCshop(ztsid)
{
	TB_show('Ӧ�տ����','<%=request.getContextPath() %>/maindj/financeMng/businessEarning/beInit4Update.?id='+ztsid+'*TB_iframe=true&height=400&width=500','');
}
function showHistory(ztsid)
{
	TB_show('Ӧ�տ��տ��¼','<%=request.getContextPath() %>/maindj/financeMng/businessEarning/showHistory.?id='+ztsid+'*TB_iframe=true&height=400&width=500','');
}
</script>
</head>

<body>
<form name="dj_guide_form" method="post">
<div id="lable"><span>Ӫ������¼�б�</span></div>
<div  id="thisSelect-table" >
	<table>
		<tr>
			<td rowspan="3" width="40" align="center" ><span>��<br/><br/>��</span></td>
			<td align="right">�źţ�</td>
			<td><input type="text" name="groupID" id="groupID" value="<%=rd.getString("groupID") %>"/></td>
			<td>��ʼ���ڣ�</td>
			<td><input type="text" name="bDate" onFocus="WdatePicker({isShowClear:false,readOnly:false});" id="bDate" value="<%=rd.getString("bDate") %>"></input></td>
			<td>�������ڣ�</td>
			<td><input type="text" name="eDate" onFocus="WdatePicker({isShowClear:false,readOnly:false});" id="eDate" value="<%=rd.getString("eDate") %>"></input></td>
			<td>�տ�״̬��</td>
		  	<td>
			<select id="state" name="state" > 			
			<option value="" >***��ѡ��***</option>
			<option value="Y"   >���տ�</option>
			<option value="N" >δ�տ�</option>
			</select>
		    </td>	
			<td><a href="###" onclick="findByLike()"><img alt="����" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td>�ź�</td>
			<td>����������</td>
			<td>���״̬</td>
			<td>�տ���</td>
			<td>����</td>
		</tr>
	<%
	int rsEarningRows = rd.getTableRowsCount("rsEarning");
	int rsEaning4CountRows = rd.getTableRowsCount("rsEaning4Count");
	
	for(int j=0;j<rsEaning4CountRows; j++){
		String groupIDInner = rd.getStringByDI("rsEaning4Count","tid",j);//��һ����ID����
		String counts = rd.getStringByDI("rsEaning4Count","sl",j);//��ID�Ĵ���
		int c = Integer.parseInt(counts);

		int sameCount = 0;
		for(int i=0;i<rsEarningRows;i++) {
			String groupID = rd.getStringByDI("rsEarning","tid",i);
			String id = rd.getStringByDI("rsEarning","id",i);
			String traID = rd.getStringByDI("rsEarning","ztsid",i);
			String cmpnyName = rd.getStringByDI("rsEarning","cmpny_name",i);
			
			String yfk = rd.getStringByDI("rsEarning","yfk",i);
			String qkzt_yfk = rd.getStringByDI("rsEarning","qkzt_yfk",i);
	%>
            <%
            if(groupIDInner.equals(groupID) && c > 1 && sameCount == 0){
			%>
		<tr>
			<td rowspan="<%=counts %>"><%=groupID %></td>
			<td><%=cmpnyName %></td>
	  		<td><%=qkzt_yfk %></td>
	  		<td><%=yfk %>Ԫ</td>	  
	  		<td>
	  			<a href="###" onclick="addLCshop('<%=id %>');"  >
           		<img alt="Ӧ�տ����" src="<%=request.getContextPath()%>/images/edit.gif">[Ӧ�տ����]&nbsp;</a>
           		<a href="###" onclick="showHistory('<%=id %>');"  >
           		<img alt="Ӧ�տ�����¼" src="<%=request.getContextPath()%>/images/edit.gif">[Ӧ�տ�����¼]&nbsp;</a>
           	</td>
		</tr>
			<%
				sameCount ++;
    		}else if(groupIDInner.equals(groupID) && c > 1 && sameCount > 0){
    		%>	
    		<tr>
			<td><%=cmpnyName %></td>
	  		<td><%=qkzt_yfk %></td>
	  		<td><%=yfk %>Ԫ</td>	  
	  		<td>
	  			<a href="###" onclick="addLCshop('<%=id %>');"  >
           		<img alt="Ӧ�տ����" src="<%=request.getContextPath()%>/images/edit.gif">[Ӧ�տ����]&nbsp;</a>
           		<a href="###" onclick="showHistory('<%=id %>');"  >
           		<img alt="Ӧ�տ�����¼" src="<%=request.getContextPath()%>/images/edit.gif">[Ӧ�տ�����¼]&nbsp;</a>
            </td>
         </tr>
    		<%	
    		}
    		else if(groupIDInner.equals(groupID) && c == 1 && sameCount == 0){
    		%>
    	<tr>
    		<td><%=groupID %></td>
			<td><%=cmpnyName %></td>
	  		<td><%=qkzt_yfk %></td>
	  		<td><%=yfk %>Ԫ</td>	  
	  		<td>
	  			<a href="###" onclick="addLCshop('<%=id %>');"  >
           		<img alt="Ӧ�տ����" src="<%=request.getContextPath()%>/images/edit.gif">[Ӧ�տ����]&nbsp;</a>
           		<a href="###" onclick="showHistory('<%=id %>');"  >
           		<img alt="Ӧ�տ�����¼" src="<%=request.getContextPath()%>/images/edit.gif">[Ӧ�տ�����¼]&nbsp;</a>
            </td>
         </tr>
    		<%
    		}
            %>
	  		
	<%
	}
		sameCount = 0 ;
}%>
	</table>
</div>

</form>
</body>
</html>