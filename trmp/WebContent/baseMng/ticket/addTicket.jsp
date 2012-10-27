<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryui/jqueryui.js" ></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/thickbox/thickbox.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>
<title>添加票务代购点</title>
<script type="text/javascript">
	//BUTTON样式初始化
	$(function()
	{
		$("input:submit,input:button").button();
		$("#cityName").bind("click",function(){
			TB_show('行政区划','<%=request.getContextPath() %>/showXZQH4Base.?xzqh/layer=2&xzqh/gnw=1*TB_iframe=true&height=380&width=350','');
		});
	});

	function addTicket(){
		document.ticket_form.action="addTicket.?";
		document.ticket_form.submit();
		/* window.parent.parent.location.reload(); 
		window.parent.parent.GB_hide();  */
	}
</script>
</head>
<body>
<form name="ticket_form" method="post">
<div id="top-select">
	<span class="tishi">带<font color="red">*</font>号为必填项</span>
</div>
	<table class="add-table">
		<tr>
			<td align="right" >代购点名称：</td>
			<td >
				<input type="text" name="TA_TICKET/CMPNY_NAME" id="CMPNY_NAME" ></input><span>*</span>
			</td>
			<td align="right" >公司地址：</td>
			<td >
			<input type="hidden" name="TA_TICKET/CITY_ID" id="cityId"/>
			<input type="text" name="xzqhname" id="cityName" class="smallInput" />
			<input type="text" name="TA_TICKET/CMPNY_ADDRESS" id="CMPNY_ADDRESS"  />
			</td>
			<td align="right" >邮编：</td>
			<td >
				<input type="text" name="TA_TICKET/POST_CODE" >
			</td>
		</tr>
		<tr>
			<td align="right" >公司帐号：</td>
			<td >
			<input type="text" name="TA_TICKET/CMPNY_BANK_CODE"  />
			</td>
			<td align="right" >开户行：</td>
			<td >
				<select name="TA_TICKET/CMPNY_BANK_NAME">
				<%int bankRows=rd.getTableRowsCount("KHYH");for(int b=0;b<bankRows;b++){ %>
					<option value="<%=rd.getStringByDI("KHYH","DMZ",b) %>"><%=rd.getStringByDI("KHYH","DMSM1",b) %></option>
				<%} %>
				</select>
			</td>
			<td align="right" >个人姓名：</td>
			<td >
				<input type="text"   name="TA_TICKET/PERSON_NAME"/>
			</td>
		</tr>
		<tr>
			<td align="right" >个人银行账号：</td>
			<td >
			<input type="text" name="TA_TICKET/PERSON_BANK_CODE"  />
			</td>
			<td align="right" >个人开户行：</td>
			<td >
			<select name="TA_TICKET/PERSON_BANK_NAME" >
				<%int gr_bankRows=rd.getTableRowsCount("KHYH");for(int g=0;g<bankRows;g++){ %>
					<option value="<%=rd.getStringByDI("KHYH","DMZ",g) %>"><%=rd.getStringByDI("KHYH","DMSM1",g) %></option>
				<%} %>
			</select>
			</td>
			<td align="right" >总负责人名字：</td>
			<td >
				<input type="text" name="TA_TICKET/CHIEF_NAME"   />
			</td>
		</tr>
		<tr>
			<td align="right" >总负责人固定电话：</td>
			<td >
			<input type="text" name="TA_TICKET/CHIEF_TEL"   />
			</td>
			<td align="right" >总负责人手机：</td>
			<td >
			<input type="text" name="TA_TICKET/CHIEF_MOBILE"  />
			</td>
			<td align="right" >总负责人传真：</td>
			<td >
				<input type="text" name="TA_TICKET/CHIEF_FAX"  />
			</td>
		</tr>
			<tr>
			<td align="right" >总负责人QQ：</td>
			<td >
			<input type="text" name="TA_TICKET/CHIEF_QQ"  />
			</td>
			<td align="right" >总负责人电子邮件：</td>
			<td >
			<input type="text" name="TA_TICKET/CHIEF_MAIL"  />
			</td>
			<td align="right" >业务员名字：</td>
			<td >
				<input type="text" name="TA_TICKET/BUSINESS_NAME"  />
			</td>
		</tr>
		<tr>
			<td align="right" >业务员固定电话：</td>
			<td >
			<input type="text" name="TA_TICKET/BUSINESS_PHONE"   />
			</td>
			<td align="right" >业务员手机：</td>
			<td >
			<input type="text" name="TA_TICKET/BUSINESS_MOBILE" />
			</td>
			<td align="right" >业务员传真：</td>
			<td >
				<input type="text" name="TA_TICKET/BUSINESS_FAX"   />
			</td>
		</tr>
		<tr>
			<td align="right" >业务员QQ：</td>
			<td >
			<input type="text" name="TA_TICKET/BUSINESS_QQ"  />
			</td>
			<td align="right" >业务员电子邮件：</td>
			<td >
			<input type="text" name="TA_TICKET/BUSINESS_MAIL" id="BUSINESS_MAIL" />
			</td>
		</tr>
		<tr>
			<td align="right">备注：</td>
			<td colspan="5"> <textarea rows="5" cols="50" name="TA_TICKET/REMARK"></textarea> <span>50汉字以内</span></td>
		</tr>
	</table>
	<div>
	<table class="datas">
	  <tr>
		<td align="center">
		  <input type="button" value="提    交" onclick="addTicket();"/>&nbsp;&nbsp;
		  <input type="button" value="返    回" onclick="window.history.go(-1)"/>
		</td>
	  </tr>
	</table>
</div>	
</form>	
</body>
</html>