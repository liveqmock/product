<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%
String ztsmc = rd.getStringByDI("TA_TRAVELAGENCYs","cmpny_name",0);
String ztsid = rd.getStringByDI("TA_TRAVELAGENCYs","TRAVEL_AGC_ID",0);
String tztsID = rd.getString("tztsID");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>

<link href="<%=request.getContextPath()%>/js/uploadify-v2.1.4/uploadify.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/uploadify-v2.1.4/swfobject.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/uploadify-v2.1.4/jquery.uploadify.v2.1.4.min.js"></script>

	<script type="text/javascript">
		$(function() {
			$('#custom_file_upload').uploadify({
				  'uploader'       : '<%=request.getContextPath()%>/js/uploadify-v2.1.4/uploadify.swf',
				  'script'         : '<%=request.getContextPath()%>/main/ztGroupMng/ztaddConfirm.fu',
				  'expressInstall' : '<%=request.getContextPath()%>/js/uploadify-v2.1.4/expressInstall.swf',
				  'cancelImg'      : '<%=request.getContextPath()%>/js/uploadify-v2.1.4/cancel.png',
				  'folder'         : '<%=request.getContextPath()%>/files',
				  'scriptData'	   : {'ztsid':<%=ztsid %>,'tztsID':<%=tztsID %>},
				  'multi'          : true,
				  'auto'           : false,
				  'fileExt'        : '*.jpg;*.gif;*.png;*.doc',
				  'fileDesc'       : 'Image Files (.JPG, .GIF, .PNG, .doc)',
				  'queueID'        : 'custom-queue',
				  'queueSizeLimit' : 3,
				  'simUploadLimit' : 3,
				  'removeCompleted': false,
				  'onSelectOnce'   : function(event,data) {
					  $('#status-message').text(data.filesSelected + ' ���ļ������ϴ�.');
				  },
				  'onAllComplete'  : function(event,data) {
					  $('#status-message').text(data.filesUploaded + ' ���ļ����ϴ�, ' + data.errors + ' ���쳣.');
				  }
			});
		});
	</script>
	
	
<title>ȷ�ϼ�����</title>
</head>

<body >
<form action="updateConfirm." name="confirmForm" method="post">
 <div class="add-table"> 
	<table class="datas"  >
	  <tr id="first-tr">
		<td>������</td>
		<td>ȷ�ϼ�</td>
	  </tr>
	  <tr>
	  	<td><%=ztsmc %></td>
		<td>
			<div id="status-message">�ϴ��ļ�:</div>
			<div id="custom-queue"></div>
			<input id="custom_file_upload" type="file" name="CONFIRM_PIC" />
		</td>
	  </tr>
    </table>
</div>
<div align="center" id="last-sub">
	<input type="button" id="button" value="��&nbsp;&nbsp;&nbsp;&nbsp;��" onclick="javascript:$('#custom_file_upload').uploadifyUpload();"/>
	<input type="button" id="button" value="ȡ���ϴ�" onclick="javascript:$('#custom_file_upload').uploadifyClearQueue()"/>
</div>
</form>
</body>
</html>