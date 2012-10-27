<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Ӧ�տ����</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/treeAndAllCss.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script src="<%=request.getContextPath() %>/jqueryui/jqueryui.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>

<script type="text/javascript">
function findByLike1(){//tab1 ��ѯaction
	document.zt_cwdz_form1.action="myBillList.?state=0&target=0&pageSize=10&pageNO=1";
	document.zt_cwdz_form1.submit();
}
function findByLike2(){//tab2 ��ѯaction
	document.zt_cwdz_form2.action="myBillList.?state=1&target=1&pageSize=10&pageNO=1";
	document.zt_cwdz_form2.submit();
}
function updateConfirmState(DDH){//tab1 �ύ���� action
	if(confirm("��ȷ�����տ�����"))
		window.location.href="updateConfirmState.?ddh="+DDH;
}
$(function() {//��ȡtabs����
	$("#tabs").tabs({selected:<%=rd.getString("target").equals("")?request.getParameter("target"):rd.getString("target")%>});
});

function ddxz(xz,xzzj){//����ȫѡ ȫ��ѡ checkboxѡ��������
	if(jQuery("."+xzzj).is(":checked")){
		jQuery("."+xz).each(function(i){
			jQuery("."+xz+":eq("+i+")").attr("checked","checked");
		});
	}else{
		jQuery("."+xz).each(function(i){
			jQuery("."+xz+":eq("+i+")").removeAttr("checked");
		});
	}
}
function openPeople1(iName,groupId){
	if( $("#"+iName+" .groupTd div").size()==0){
		 var obj=$.ajax({
		 	 url:"myBillListDetail.?groupId="+groupId+"&state=0",
			 async:true,
			 dataType:"HTML",
			 cache:false,
			 success:function(){
				 $("#"+iName).attr("style","");
		  		 $("#"+iName+" .groupTd").html(obj.responseText);
			 }
		});
	}else{
		if($("#"+iName).is(":hidden")){
			 $("#"+iName).css({display:"block"});
		}else{
			 $("#"+iName).css({display:"none"});
		}
	}
}

function openPeople2(iName,groupId){
	if( $("#"+iName+" .groupTd div").size()==0){
		 var obj=$.ajax({
		 	 url:"myBillListDetail.?groupId="+groupId+"&state=1",
			 async:true,
			 dataType:"HTML",
			 cache:false,
			 success:function(){
				 $("#"+iName).attr("style","");
		  		 $("#"+iName+" .groupTd").html(obj.responseText);
			 }
		});
	}else{
		if($("#"+iName).is(":hidden")){
			 $("#"+iName).css({display:"block"});
		}else{
			 $("#"+iName).css({display:"none"});
		}
	}
}
</script>
</head>
<body>
<div id="tabs">
	<ul>
		<li><a href="#tabs-1" onclick="findByLike1();">δ�����˵���¼</a></li>
		<li><a href="#tabs-2" onclick="findByLike2();">�Ѷ����˵���¼</a></li>
	</ul>
	
<div id="tabs-1">
<%
	int pageNO=0;
	int pageSize=0;
	int totalPage =0;
	int rowsCount = 0;
	if(rd.getTableRowsCount("rsCwdzInfo")>0){
		pageNO=Integer.parseInt(rd.getString("pageNO"));
		pageSize=Integer.parseInt(rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("rsCwdzInfo", "pagesCount");
		rowsCount=(Integer)rd.getAttr("rsCwdzInfo", "rowsCount");
	}
%>   
<form  name="zt_cwdz_form1" method="post">
	  <table class="datas" width="100%">
		<tr>
		  <td width="6%">�ſ�ʼ���ڣ�</td>
		  <td width="12%"><input type="text" name="bdate" id="dateB" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td width="6%">�Ž������ڣ�</td>
		  <td width="12%"><input type="text" name="edate" id="dateE" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td width="25%" colspan="2"></td>	
		</tr>
		
		<tr>
		  <td>�źţ�</td>
		  <td><input type="text" name="groupID" /></input></td>
		  <td>��·���ƣ�</td>
		  <td><input type="text"  name="xlmc"/></td>
		<td >
			�����ţ�
		</td>
		<td>
			<input type="text" name="ddh" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="###" onclick="findByLike1();"><img alt="����" src="<%=request.getContextPath()%>/images/search.gif"/></a></td>
		</tr>		
	  </table>


	<table class="datas" >
	<%
		int RowsNo11 = rd.getTableRowsCount("rsCwdzInfo");
		for(int i = 0; i < RowsNo11; i++){
			    String groupID = rd.getString("rsCwdzInfo","ID",i);//�ź�
				String XLMC = rd.getString("rsCwdzInfo","XLMC",i);
				String USERFROM = rd.getString("rsCwdzInfo","szlxsmc",i);
				String BDATE = rd.getString("rsCwdzInfo","begin_date",i);
				String EDATE = rd.getString("rsCwdzInfo","end_date",i);
				String ET = rd.getString("rsCwdzInfo","adult_count",i);
				String CR = rd.getString("rsCwdzInfo","children_count",i);
				String BX = rd.getString("rsCwdzInfo","BX",i);
				String THJ = rd.getString("rsCwdzInfo","THJ",i);
				String JJ = rd.getString("rsCwdzInfo","JJ",i);
				String DDS = rd.getString("rsCwdzInfo","dds",i);
				Double YFKHJ = Double.parseDouble(THJ)+Double.parseDouble(BX)+Double.parseDouble(JJ);
				int RS = Integer.parseInt(ET)+Integer.parseInt(CR);
	%>
		<tr id="first-tr">
			<td align="center" width="10%">�ź�</td>
			<td align="center" width="20%">��·����</td>
			<td align="center" width="20%">��Ӧ��</td>
			<td align="center" colspan="2" width="10%">����	</td>
			<td align="center" width="5%">����</td>
			<td align="center" width="5%">������</td>
			<td align="center" width="15%">Ӧ����</td>
			<td align="center" width="5%">״̬</td>
		</tr>
		<tr  class="ykorderList<%=i%>"  onclick="openPeople1('ykorderList<%=i%>','<%=groupID%>');" style="color: green;" >
           <td align="center" > <%=groupID %></td>
	  		<td align="center" > <%=XLMC %></td>
	  		<td align="center" > <%=USERFROM %></td>
	  		<td align="center" colspan="2">��<%=BDATE %><br>��<%=EDATE %> </td>	
	  		<td align="center" > <%=RS %></td>
	  		<td align="center" > <%=DDS %></td>
	  		<td align="center" > <%=YFKHJ %>(<%=THJ %>+<%=BX %>+<%=JJ %>)</td>
	  		<td align="center" > δ����</td>
         </tr>	
		<tr id="ykorderList<%=i%>" style="display: none;">
			<td class="groupTd" colspan="9">
			</td>
		</tr>
         <%} %>
	</table>
	<div id="page">
		<%String url=request.getContextPath()+"/main/moneyMng/account/myBillList.?target=0&state=0&pageSize=10&pageNO=";%>
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
	if(rd.getTableRowsCount("rsCwdzInfo")>0){
		pageNO2=Integer.parseInt(rd.getString("pageNO"));
		pageSize2=Integer.parseInt(rd.getString("pageSize"));
		totalPage2=(Integer)rd.getAttr("rsCwdzInfo", "pagesCount");
		rowsCount2=(Integer)rd.getAttr("rsCwdzInfo", "rowsCount");
	}
%>   
<form  name="zt_cwdz_form2" method="post">
	  <table class="datas" width="100%">
		<tr>
		  <td width="6%">�ſ�ʼ���ڣ�</td>
		  <td width="12%"><input type="text" name="bdate" id="dateB" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td width="6%">�Ž������ڣ�</td>
		  <td width="12%"><input type="text" name="edate" id="dateE" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td width="25%" colspan="2"></td>	
		</tr>
		
		<tr>
		  <td>�źţ�</td>
		  <td><input type="text" name="groupID" /></input></td>
		  <td>��·���ƣ�</td>
		  <td><input type="text"  name="xlmc"/></td>
		<td >
			�����ţ�
		</td>
		<td>
			<input type="text" name="ddh" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="###" onclick="findByLike2();"><img alt="����" src="<%=request.getContextPath()%>/images/search.gif"/></a></td>
		</tr>		
	  </table>

<table class="datas" >
	<%
		int RowsNo21 = rd.getTableRowsCount("rsCwdzInfo");
		for(int i = 0; i < RowsNo21; i++){
			    String groupID = rd.getString("rsCwdzInfo","ID",i);//�ź�
				String XLMC = rd.getString("rsCwdzInfo","XLMC",i);
				String USERFROM = rd.getString("rsCwdzInfo","szlxsmc",i);
				String BDATE = rd.getString("rsCwdzInfo","begin_date",i);
				String EDATE = rd.getString("rsCwdzInfo","end_date",i);
				String ET = rd.getString("rsCwdzInfo","adult_count",i);
				String CR = rd.getString("rsCwdzInfo","children_count",i);
				String BX = rd.getString("rsCwdzInfo","BX",i);
				String THJ = rd.getString("rsCwdzInfo","THJ",i);
				String JJ = rd.getString("rsCwdzInfo","JJ",i);
				String DDS = rd.getString("rsCwdzInfo","dds",i);
				Double YFKHJ = Double.parseDouble(THJ)+Double.parseDouble(BX)+Double.parseDouble(JJ);
				int RS = Integer.parseInt(ET)+Integer.parseInt(CR);
	%>
		<tr id="first-tr">
			<td align="center" width="10%">�ź�</td>
			<td align="center" width="20%">��·����</td>
			<td align="center" width="20%">��Ӧ��</td>
			<td align="center" colspan="2" width="10%">����	</td>
			<td align="center" width="5%">����</td>
			<td align="center" width="5%">������</td>
			<td align="center" width="15%">Ӧ����</td>
			<td align="center" width="5%">״̬</td>
		</tr>
		<tr  class="groupList<%=i%>"  onclick="openPeople2('groupList<%=i%>','<%=groupID%>');" style="color: green;" >
            <td align="center" > <%=groupID %></td>
	  		<td align="center" > <%=XLMC %></td>
	  		<td align="center" > <%=USERFROM %></td>
	  		<td align="center" colspan="2">��<%=BDATE %><br>��<%=EDATE %> </td>	
	  		<td align="center" > <%=RS %></td>
	  		<td align="center" > <%=DDS %></td>
	  		<td align="center" > <%=YFKHJ %>(<%=THJ %>+<%=BX %>+<%=JJ %>)</td>
	  		<td align="center" > �Ѷ���</td>
         </tr>	
		<tr id="groupList<%=i%>" style="display: none;">
			<td class="groupTd" colspan="9">
			</td>
		</tr>
         <%} %>
	</table>
	<div id="page">
		<%String url2=request.getContextPath()+"/main/moneyMng/account/confirmingBillList.?pageSize=10&pageNO=";%>
		<span class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url2%>","<%=totalPage%>")'></span>		
		<span class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url2%>","<%=totalPage%>")'></span>
		<span class="pagination-sum">
						��<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> ҳ��
						��<%=rowsCount %> ����¼��
						ÿҳ <%=pageSize%>��
		</span>
		<span class="next-page" onclick='query("<%=pageNO+1>totalPage?totalPage:pageNO+1 %>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url2%>","<%=totalPage%>")'></span>
		<span class="last-page" onclick='query("<%=totalPage%>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url2%>","<%=totalPage%>")'></span>
		<span class="go-to-page" >
			��ת���ڣ�<input type="text" id="gotopage"/> ҳ
		</span>
		<span class="go-to-page-icon" onclick='go("<%=totalPage%>","<%=(int)Math.min(pageNO,totalPage)%>","<%=url2 %>")'></span>
	</div>
</form>
</div>
</div>
</html>