<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="java.sql.Blob"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="com.trmdj.businessPlan.groupMng.GroupLineDetail"%>
<%@include file="../../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
<%
	String roleID = sd.getString("USERROLEST");
	boolean isTrue = false;
	if (!"".equals(roleID)) {

		roleID = roleID.substring(1, roleID.length() - 1);
		String[] roleIDs = roleID.split(",");
		for (int i = 0; i < roleIDs.length; i++) {
			if ("admin".equals(roleIDs[i].trim())
					|| "transferForDj".equals(roleIDs[i].trim())) {
				isTrue = true;
				break;
			}
		}
	}
	String canEdit = rd.getString("canEdit");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css" type="text/css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/treeAndAllCss.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
	<script src="<%=request.getContextPath() %>/jqueryui/jqueryui.js" ></script>
	
<title>添加地接线路信息</title>
 

<script type="text/javascript">
$(function() {
	//初始化button submit
	$( "input:submit,input:button").button();
	
	/* //初始化radio
	$( "#radio,#radio1,#radio2" ).buttonset();
	
	//checkbox 单个为button 多个为buttonset
	$( "input checkbox" ).button();
	$( "#format" ).buttonset(); */
 
	<%int XCMXRow = rd.getTableRowsCount("TA_DJ_LINEDETAILs");
			if (XCMXRow <= 0) {%>
		CKEDITOR.replace('XCMX0');
        CKEDITOR.config.toolbarStartupExpanded = false;
        CKEDITOR.config.resize_enabled = false;
	<%} else {
				for (int i = 0; i < XCMXRow; i++) {%>
    	CKEDITOR.replace('XCMX<%=i%>');
        CKEDITOR.config.toolbarStartupExpanded = false;
        CKEDITOR.config.resize_enabled = false;
    <%}
			}%>
});

	function add_line(){
		var tableRows = jQuery(".valueType").size();
			jQuery("#store").append('<tr class="valueType">'+
					'<td>'+
					'<select name="TA_DJ_XLJG/USERNO['+tableRows+']">'+
						'<option value="">***请选择***</option>'+
		<%
		int userRows = rd.getTableRowsCount("HRDEPARTMENTs");
		for(int j=0;j<userRows;j++){
			String DEPTID = rd.getStringByDI("HRDEPARTMENTs","DEPTID",j);
			String DEPTNAME = rd.getStringByDI("HRDEPARTMENTs","DEPTNAME",j);
		%>
						'<option value="<%=DEPTID %>" ><%=DEPTNAME %></option>'+
		<%
		} %>
					'</select>'+
					'</td>'+
					'<td>'+
					'<select name="TA_DJ_XLJG/JGLX['+tableRows+']">'+
					  '<%for (int i = 0; i < rd.getTableRowsCount("jglx"); i++) {%>'+
					    '<option value="<%=rd.getStringByDI("jglx", "dmz", i)%>" <%if (i == 0) {%>selected="selected"<%}%>><%=rd.getStringByDI("jglx", "dmsm1", i)%></option>'
								+ '<%}%>'
								+ '</select>'
								+ '</td>'
								+ '<td><input name="TA_DJ_XLJG/JG['
								+ tableRows
								+ ']" value="0" class="smallerInput" maxlength="5" id="price_th0" onkeydown="checkNum()"></input></td>'
								+ '<td><input name="TA_DJ_XLJG/DFC['
								+ tableRows
								+ ']" value="0" class="smallerInput" maxlength="5" onkeydown="checkNum()"></td>'
								+ '<td colspan="2">'
								+ '<textarea name="TA_DJ_XLJG/BZ['+tableRows+']" rows="1" cols="40"> </textarea>'
								+ '</td>' + '</tr>');
	}

	function del_line() {
		var tableRows = jQuery(".valueType").size();
		if (tableRows -= 1 > 1) {
			var idx = parseInt(jQuery("tr.valueType").size() - 1);
			jQuery("tr.valueType:eq(" + idx + ")").remove();
		}
	}

	jQuery("#showVisitor").hover(function() {
		jQuery(this).css({
			cursor : 'pointer'
		});
	});
	jQuery("#showVisitor").click(function() {
		jQuery("tr.valueType").toggle();
	});
	function add_XCMX() {
		var QJBL = Math.floor(Math.random() * 1024);
		var XCMXRows = parseInt(jQuery(".XCMXType").size());
		jQuery("#store1").append(
						'<tr class="XCMXType">'
								+ '<td>'
								+ '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="6">D'+ (XCMXRows + 1)+ '</font><input type="hidden" name="TA_DJ_LINEDETAIL/RQ['+ XCMXRows+ ']" value="'+ (XCMXRows + 1)+ '"/>'
								+ '<br>'
								+ '<br>'
								+ '&nbsp;&nbsp;&nbsp;&nbsp;含&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;餐：<input type="checkbox" name="TA_DJ_LINEDETAIL/BREAKFAST['+XCMXRows+']" value="Y"/>早'
								+ '<input type="checkbox" name="TA_DJ_LINEDETAIL/CMEAL['+XCMXRows+']" value="Y"/>中'
								+ '<input type="checkbox" name="TA_DJ_LINEDETAIL/SUPPER['+XCMXRows+']" value="Y"/>晚'
								+ '<br>'
								+ '<br>&nbsp;&nbsp;&nbsp;&nbsp;'
								+ '餐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标：'
								+ '<input type="text" name="TA_DJ_LINEDETAIL/CB['+XCMXRows+']" value="" class="smallerInput1"/>'
								+ '<br>'
								+ '<br>&nbsp;&nbsp;&nbsp;&nbsp;'
								+ '住宿城市：'
								+ '<input type="text" name="TA_DJ_LINEDETAIL/ZS['+XCMXRows+']" value="" class="smallerInput1"/>'
								+ '<br>'
								+ '<br>&nbsp;&nbsp;&nbsp;&nbsp;'
								+ '房&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标：'
								+ '<select name="TA_DJ_LINEDETAIL/ZSBZ['+XCMXRows+']" style="width: 100px;">'
									<%for (int i = 0; i < rd.getTableRowsCount("JDXJ"); i++) {%>
								+ '<option value="<%=rd.getStringByDI("JDXJ", "dmz", i)%>" <%if (i == 0) {%> selected="selected" <%}%>><%=rd.getStringByDI("JDXJ", "dmsm1", i)%></option>'
									<%}%>
								+ '</select>'
								+ '</td>'
								+ '<td>'
								+ '<textarea id="XCMX'+QJBL+'" name="TA_DJ_LINEDETAIL/XCMX['+XCMXRows+']" rows="0" cols="0">'
								+ '</textarea>'
								+ '</td>'
								+ '</tr>');
		CKEDITOR.replace('XCMX' + QJBL + '');
		CKEDITOR.config.toolbarStartupExpanded = false;
		CKEDITOR.config.resize_enabled = false;
	}
	function del_XCMX() {
		var tableRows = jQuery(".XCMXType").size();
		if (tableRows -= 1 > 1) {
			var idx = parseInt(jQuery("tr.XCMXType").size() - 1);
			jQuery("tr.XCMXType:eq(" + idx + ")").remove();
		}
	}

	function showplan(num) {
		if (num == 1) {
			document.getElementById("plan-data1").style.display = "block";
			document.getElementById("plan-data2").style.display = "block";
		}
		if (num == 2) {
			document.getElementById("plan-data1").style.display = "block";
			document.getElementById("plan-data2").style.display = "none";
		}
	}

	function checkNum() {
		if (!(event.keyCode == 46) && !(event.keyCode == 8)
				&& !(event.keyCode == 37) && !(event.keyCode == 39))
			if (!((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105)))
				event.returnValue = false;
	}
	function djAddLineBase() {
		var submit = 0;
		if ($("#ts").val() == '' || $("#ts").val() == '0'||$("#ts1").val() == '' || $("#ts1").val() == '0') {
			alert("请输入正确的天数！");
			submit++;
		}
		var xlmc = jQuery("#XLMC").val();
		if (xlmc == '') {
			alert("请输入线路名称！");
			submit++;
		}
		
		if(submit <= 0){
			if($("#XLID").val()){
				document.djLineInfoForm.action = "djEditLineBase.";
				document.djLineInfoForm.submit();
			}else{
				document.djLineInfoForm.action = "djAddLineBase.";
				document.djLineInfoForm.submit();
			}
		}
}
</script>
</head>
<body>
	<form name="djLineInfoForm" method="post">
		<div style="width: 99.8%">
			<div class="add-table">
				<table class="datas">
				<tr>
					<td id="first-tr" colspan="2">&nbsp;&nbsp;线路基础信息&nbsp;&nbsp;&nbsp;&nbsp;带<font color="red">*</font>号为必填项</td>
				</tr>
					<tr>
						<%
							String XLID = rd.getString("TA_DJ_LINEMNGs", "XLID", 0);//线路名称
							String XLMC = rd.getString("TA_DJ_LINEMNGs", "XLMC", 0);//线路名称
							String YKLX = rd.getString("TA_DJ_LINEMNGs", "YKLX", 0);//游客类型
							String GW = rd.getString("TA_DJ_LINEMNGs", "GW", 0);//购物
							String FBJH = rd.getString("TA_DJ_LINEMNGs", "FBJH", 0);//发班日期
							String YCBZ = rd.getString("TA_DJ_LINEMNGs", "YCBZ", 0);//用餐标准
							String FWBZ = rd.getString("TA_DJ_LINEMNGs", "FWBZ", 0);//服务标准
							String DY = rd.getString("TA_DJ_LINEMNGs", "DY", 0);//导游
							String YC = rd.getString("TA_DJ_LINEMNGs", "YC", 0);//用车
							String MP = rd.getString("TA_DJ_LINEMNGs", "MP", 0);//门票
							String SFLJKS = rd.getString("TA_DJ_LINEMNGs", "SFLJKS", 0);//是否立即可视
							String XLQY = rd.getString("TA_DJ_LINEMNGs", "XLQY", 0);//线路区域
							String TS = rd.getString("TA_DJ_LINEMNGs", "TS", 0);//天数
							String NIGHT = rd.getString("TA_DJ_LINEMNGs", "NIGHT", 0);//晚
							String TBYQ = rd.getString("TA_DJ_LINEMNGs", "BZ", 0);//备注
							String TBTX = rd.getString("TA_DJ_LINEMNGs", "TBTX", 0);//特别提醒
							String USERNO = rd.getString("TA_DJ_LINEMNGs", "USERNO", 0);//线路指定人
							String KCTRS = rd.getString("TA_DJ_LINEMNGs", "KCTRS", 0);//可成团人数
							String JJLXDH = rd.getString("TA_DJ_LINEMNGs", "JJLXDH", 0);//紧急联系电话
							String ZDKSRS = rd.getString("TA_DJ_LINEMNGs", "ZDKSRS", 0);//最大可收客人数
							
						%>
						<td align="right" width="14%">线路名称：</td>
						<td colspan="3">
						<%if(!"".equals(XLID)){ %>
							<input type="hidden" name="TA_DJ_LINEMNG/XLID" value="<%=XLID%>" id="XLID"/> 
						<%} %>
							<input type="hidden" name="TA_DJ_LINEMNG/USERNO" value="<%=sd.getString("userno")%>" /> 
							<input type="text" name="TA_DJ_LINEMNG/XLMC" id="XLMC" style="width: 480px;" value="<%=XLMC%>" maxlength="75" /> 
							<input name="next" type="hidden" value="<%=rd.getString("next")%>" /> <span>*</span>
						</td>

					</tr>
					<tr>
						<td align="right">游客类型：</td>
						<td>
							<div id="radio">
								<input type="radio" name="TA_DJ_LINEMNG/YKLX" value="1" <%if ("1".equals(YKLX)) {%> checked="checked" <%}%> id="radio1"/><label for="radio1">团队</label>
							 	<input type="radio" name="TA_DJ_LINEMNG/YKLX" value="2" <%if ("2".equals(YKLX)||"".equals(YKLX)) {%> checked="checked" <%}%> id="radio2"/><label for="radio2">散客</label>
							</div>
						</td>
					</tr>

					<tr>
						<td align="right">线路区域：</td>
						<td>
							<select name="TA_DJ_LINEMNG/XLQY">
									<%
										for (int i = 0; i < rd.getTableRowsCount("xzqhs"); i++) {
											String id = rd.getStringByDI("xzqhs", "id", i);
									%>
										<option value="<%=id%>" 
											<%if ("" == XLQY) {
												if (id.equals("1")) {%>selected="selected" <%}
											} else {
												if (id.equals(XLQY)) {%>selected="selected" <%}
											}%>>
											<%=rd.getStringByDI("xzqhs", "name", i)%>
										</option>
									<%
										}
									%>
							</select>
							<a href="###">编辑</a>
						</td>
					</tr>



					<tr>
						<td align="right">天数：</td>
						<td><input type="text" name="TA_DJ_LINEMNG/TS"
							value="<%=TS%>" onkeydown="checkNum();" id="ts" maxlength="2" class="smallInput"/>&nbsp;天
							<input type="text" name="TA_DJ_LINEMNG/NIGHT"
							value="<%=NIGHT%>" onkeydown="checkNum();" id="ts1" maxlength="2" class="smallInput"/>&nbsp;晚
							&nbsp;&nbsp;<label>例如：5  天    6  晚</label>&nbsp;&nbsp;<span>*</span>
						</td>
					</tr>
					
					
					<tr>
						<td align="right">总人数：</td>
						<td>
							<div id="radio1">
								<%if("-1".equals(ZDKSRS)||"".equals(ZDKSRS)){ %>
									<input type="radio" name="CountPerson" onclick="$('#CountPerson').val(-1);$('#CountPerson').attr('readonly','readonly');" checked="checked" id="personRadio1"/><label for="personRadio1">人数不限</label>
									<input type="radio" name="CountPerson" onclick="$('#CountPerson').val('');$('#CountPerson').removeAttr('readonly','');" id="personRadio2"/><label for="personRadio2">输入人数</label>
									&nbsp;&nbsp;<input type="text" id="CountPerson" name="TA_DJ_LINEMNG/ZDKSRS" value="-1" onkeydown="checkNum();"  maxlength="3" readonly="readonly" class="smallInput"/>
									&nbsp;&nbsp;<label>提示：人数 1 - 999 人</label>
								<%}else{ %>
									<input type="radio" name="CountPerson" onclick="$('#CountPerson').val(-1);$('#CountPerson').attr('readonly','readonly');"  id="personRadio1"/><label for="personRadio1">人数不限</label>
									<input type="radio" name="CountPerson" onclick="$('#CountPerson').val('');$('#CountPerson').removeAttr('readonly','');" checked="checked" id="personRadio2"/><label for="personRadio2">输入人数</label>
									&nbsp;&nbsp;<input type="text" id="CountPerson" name="TA_DJ_LINEMNG/ZDKSRS" value="<%=ZDKSRS %>" onkeydown="checkNum();"  maxlength="3" class="smallInput"/>
									&nbsp;&nbsp;<label>提示：人数 1 - 999 人</label>
								<%} %>
							</div>
						</td>
					</tr>
					
					<tr>
						<td align="right">可成团人数：</td>
						<td><input type="text" name="TA_DJ_LINEMNG/KCTRS" value="<%=KCTRS %>" maxlength="3" onkeydown="checkNum();"/>&nbsp;<label>提示：人数 1 - 999 人</label></td>
					</tr>
					
					<tr>
						<td align="right">紧急联系电话：</td>
						<td>
							<input type="text" name="TA_DJ_LINEMNG/JJLXDH" value="<%= JJLXDH %>" maxlength="24" id="jjCall"/>
							<label>
								例如：	<a onclick="$('#jjCall').val($('#jjCall').val()+'025-88888888 ')">025-88888888</a>
										<a onclick="$('#jjCall').val($('#jjCall').val()+'025-99999999 ')">025-99999999</a>
							</label>
						</td>
					</tr>

					<tr>
						<td align="right">发班日期：</td>
						<td><input type="text" name="TA_DJ_LINEMNG/FBJH"
							value="<%=FBJH%>" id="fbjh"/>
						<label>
							例如：<a onclick="$('#fbjh').val($('#fbjh').val()+'每天发班 ')">每天发班</a>
							&nbsp;
							每周一、三发班
						</label>
						</td>
					</tr>
					
					<tr>
						<td align="right">用车：</td>
						<td>
							<input type="text" name="TA_DJ_LINEMNG/YC" value="<%=YC%>" maxlength="25" />
							<label>例如：豪华旅游大巴</label>
						</td>
					</tr>
					
					
					<tr>
						<td align="right">特殊人群收费：</td>
						<td>
							<input type="text" name="TA_DJ_LINEMNG/MP" value="<%=MP%>" maxlength="25" />
							<label>例如：儿童身高1.4米以下半价</label>
						</td>
					</tr>
					
					
					<tr>
						<td align="right" width="10%">购物：</td>
						<td>
							<input type="text" name="TA_DJ_LINEMNG/GW" value="<%=GW%>" maxlength="25" />
							<label>例如：珍珠、刀店</label>
						</td>
					</tr>
					
					
					<tr>
						<td align="right">导游：</td>
						<td>
							<input type="text" name="TA_DJ_LINEMNG/DY" value="<%=DY%>" maxlength="25" />
							<label>例如：优质导游</label>	
						</td>
					</tr>
					 
					
					<tr>
						<td align="right">是否立即可视：</td>
						<td>
							<div id="radio2">
								<input type="radio" name="TA_DJ_LINEMNG/SFLJKS" value="1" <%if ("1".equals(SFLJKS)||"".equals(SFLJKS)) {%> checked="checked" <%}%> id="ksRadio1"/><label for="ksRadio1">是</label>
								<input type="radio" name="TA_DJ_LINEMNG/SFLJKS" value="2" <%if ("2".equals(SFLJKS)) {%> checked="checked" <%}%> id="ksRadio2"/><label for="ksRadio2">否</label>
							</div>
						</td>
					</tr>
					
				</table>

			</div>







<!-- 价格类型 -->
			<div style="text-align: right">
				<a href="###" onclick="add_line();"><img alt="添加"
					src="<%=request.getContextPath()%>/images/add.gif"> 添加</a>&nbsp;&nbsp;
				<a href="###" onclick="del_line();"><img alt="删除"
					src="<%=request.getContextPath()%>/images/del.gif"> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
			<div id="list-table">
				<table class="datas" id="store">
					<tr id="first-tr">
						<td>价格可视用户分组</td>
						<td>价格类型</td>
						<td>价&nbsp;&nbsp;&nbsp;&nbsp;格</td>
						<td>单房差</td>
						<td>备&nbsp;&nbsp;&nbsp;&nbsp;注</td>
					</tr>
					<%
						int priceRows = rd.getTableRowsCount("TA_DJ_XLJGs");
						if (priceRows <= 0) {
					%>
					<tr class="valueType">
						<td>
						<select name="TA_DJ_XLJG/DEPTID[0]">
							<option value="">***请选择***</option>
						<%
						userRows = rd.getTableRowsCount("HRDEPARTMENTs");
						for(int j=0;j<userRows;j++){
							String DEPTID = rd.getStringByDI("HRDEPARTMENTs","DEPTID",j);
							String DEPTNAME = rd.getStringByDI("HRDEPARTMENTs","DEPTNAME",j);
						%>
							<option value="<%=DEPTID %>" ><%=DEPTNAME %></option>
						<%
						} %>
						</select>
						</td>
						<td><select name="TA_DJ_XLJG/JGLX[0]">
								<%
									for (int i = 0; i < rd.getTableRowsCount("jglx"); i++) {
								%>
								<option value="<%=rd.getStringByDI("jglx", "dmz", i)%>"
									<%if (i == 0) {%> selected="selected" <%}%>><%=rd.getStringByDI("jglx", "dmsm1", i)%></option>
								<%
									}
								%>
						</select></td>
						<td><input name="TA_DJ_XLJG/JG[0]" value="0"
							class="smallerInput" maxlength="5" id="price_th0"
							onkeydown="checkNum();"></input>
						</td>
						<td><input name="TA_DJ_XLJG/DFC[0]" value="0"
							class="smallerInput" maxlength="5" onkeydown="checkNum()">
						</td>
						<td colspan="2">
							<textarea name="TA_DJ_XLJG/BZ[0]" rows="1" cols="40"></textarea>
						</td>
					</tr>
					<%
						} else {
							for (int i = 0; i < priceRows; i++) {
								
								String deptId = rd.getString("TA_DJ_XLJGs", "DEPTID", i);
								String JG = rd.getString("TA_DJ_XLJGs", "JG", i);//价格
								String DFC = rd.getString("TA_DJ_XLJGs", "DFC", i);//单房差
								String BZ = rd.getString("TA_DJ_XLJGs", "BZ", i);//备注
								String JGLX = rd.getString("TA_DJ_XLJGs", "JGLX", i);//价格类型
					%>
					<tr class="valueType">
						<td>
						<select name="TA_DJ_XLJG/DEPTID[<%=i %>]">
							<option value="">***请选择***</option>
						<%
						userRows = rd.getTableRowsCount("HRDEPARTMENTs");
						for(int j=0;j<userRows;j++){
							String DEPTID = rd.getStringByDI("HRDEPARTMENTs","DEPTID",j);
							String DEPTNAME = rd.getStringByDI("HRDEPARTMENTs","DEPTNAME",j);
						%>
							<option value="<%=DEPTID %>" <%if(DEPTID.equals(deptId)){ %>selected="selected"<%} %>><%=DEPTNAME %></option>
						<%
						} %>
						</select>
						</td>
						<td><select name="TA_DJ_XLJG/JGLX[<%=i%>]">
								<%
									for (int j = 0; j < rd.getTableRowsCount("jglx"); j++) {
												String dmz = rd.getStringByDI("jglx", "dmz", j);
								%>
								<option value="<%=rd.getStringByDI("jglx", "dmz", j)%>"
									<%if (dmz.equals(JGLX)) {%> selected="selected" <%}%>><%=rd.getStringByDI("jglx", "dmsm1", j)%></option>
								<%
									}
								%>
						</select></td>
						<td><input name="TA_DJ_XLJG/JG[<%=i%>]" value="<%=JG%>"
							class="smallerInput" maxlength="5" id="price_th0"
							onkeydown="checkNum();"></input>
						</td>
						<td><input name="TA_DJ_XLJG/DFC[<%=i%>]" value="<%=DFC%>"
							class="smallerInput" maxlength="5" onkeydown="checkNum()">
						</td>
						<td colspan="2"><textarea name="TA_DJ_XLJG/BZ[<%=i%>]" rows="1" cols="40"><%=BZ%></textarea></td>
					</tr>
					<%
						}
					}
					%>
				</table>
			</div>










<!-- 行程明细 -->
			<div style="text-align: right">
				<a href="###" onclick="add_XCMX();"><img alt="添加"
					src="<%=request.getContextPath()%>/images/add.gif"> 添加</a>&nbsp;&nbsp;
				<a href="###" onclick="del_XCMX();"><img alt="删除"
					src="<%=request.getContextPath()%>/images/del.gif"> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
			<div class="add-table">
				<table class="datas" id="store1">
					<tr id="first-tr">
						<td align="center" width="15%">标准</td>
						<td align="center" width="70%">行程</td>
						
					</tr>
					<%
						int XCMXRows = rd.getTableRowsCount("TA_DJ_LINEDETAILs");
						if (XCMXRows <= 0) {
					%>
					<tr class="XCMXType">
						<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="6">D1</font><input type="hidden" name="TA_DJ_LINEDETAIL/RQ[0]" value="1" /><br><br>
							&nbsp;&nbsp;&nbsp;&nbsp;含&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;餐：<input type="checkbox" name="TA_DJ_LINEDETAIL/BREAKFAST[0]" value="Y" class="" />早 
							<input type="checkbox" name="TA_DJ_LINEDETAIL/CMEAL[0]" value="Y" class="" />中 
							<input type="checkbox" name="TA_DJ_LINEDETAIL/SUPPER[0]" value="Y" class="" />晚 <br><br>
							
							&nbsp;&nbsp;&nbsp;&nbsp;餐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标：<input type="text" name="TA_DJ_LINEDETAIL/CB[0]" value="" class="smallerInput1" /> <br><br>
							&nbsp;&nbsp;&nbsp;&nbsp;住宿城市：<input type="text" name="TA_DJ_LINEDETAIL/ZS[0]" value="" class="smallerInput1" /> <br><br>
							&nbsp;&nbsp;&nbsp;&nbsp;房&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标：<select name="TA_DJ_LINEDETAIL/ZSBZ[0]" style="width: 100px;">
								<%
									for (int i = 0; i < rd.getTableRowsCount("JDXJ"); i++) {
								%>
								<option value="<%=rd.getStringByDI("JDXJ", "dmz", i)%>"
									<%if (i == 0) {%> selected="selected" <%}%>><%=rd.getStringByDI("JDXJ", "dmsm1", i)%></option>
								<%
									}
								%>
							</select>
						</td>
						
						
						<td>
							<textarea id="XCMX0" name="TA_DJ_LINEDETAIL/XCMX[0]" rows="0" cols="0"> </textarea>
   	 					</td>
						
					</tr>
					<%
						} else {
							List<GroupLineDetail> list = (List) rd.get("lineDetail");
							for (int i = 0; i < list.size(); i++) {
								GroupLineDetail gd = list.get(i);
								String RQ = gd.getRq();//日期
								String XCMX = gd.getXcmx();//线路明细
								String BREAKFAST = gd.getBreakfast();//早
								String CMEAL = gd.getCmeal();//中
								String SUPPER = gd.getSupper();//晚
								String ZSBZ = gd.getZsbz();//住宿标准
								String CB = gd.getCb();//餐标
								String ZS = gd.getZs();//住宿
					%>
					<tr class="XCMXType">
						<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="6">D<%=RQ%></font>
						<input type="hidden" name="TA_DJ_LINEDETAIL/RQ[<%=i%>]" value="<%=RQ%>" />
						<br><br>
						&nbsp;&nbsp;&nbsp;&nbsp;
						含&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;餐：<input type="checkbox" name="TA_DJ_LINEDETAIL/BREAKFAST[<%=i%>]" value="Y"
							<%if ("Y".equals(BREAKFAST)) {%> checked="checked" <%}%> />
						早 <input type="checkbox" name="TA_DJ_LINEDETAIL/CMEAL[<%=i%>]" value="Y"
							<%if ("Y".equals(CMEAL)) {%> checked="checked" <%}%> />
						中 <input type="checkbox" name="TA_DJ_LINEDETAIL/SUPPER[<%=i%>]" value="Y"
							<%if ("Y".equals(SUPPER)) {%> checked="checked" <%}%> />
						晚<br><br>
							&nbsp;&nbsp;&nbsp;&nbsp;餐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标：<input type="text" name="TA_DJ_LINEDETAIL/CB[<%=i%>]" value="<%=CB %>" class="smallerInput1" /> <br><br>
							&nbsp;&nbsp;&nbsp;&nbsp;住&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;宿：<input type="text" name="TA_DJ_LINEDETAIL/ZS[<%=i%>]" value="<%=ZS %>" class="smallerInput1" /> <br><br>
							&nbsp;&nbsp;&nbsp;&nbsp;房&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标：<select name="TA_DJ_LINEDETAIL/ZSBZ[<%=i%>]" style="width: 100px;">
								<%
									for (int j = 0; j < rd.getTableRowsCount("JDXJ"); j++) {
										String dmz = rd.getString("JDXJ", "dmz", j);
										String dmsm = rd.getString("JDXJ", "dmsm1", j);
								%>
								<option value="<%=dmz%>" <%if (ZSBZ.equals(dmz)) {%> selected="selected" <%}%>><%=dmsm%></option>
								<%
									}
								%>
							</select>
						</td>
						<td>
							<textarea id="XCMX<%=i%>" name="TA_DJ_LINEDETAIL/XCMX[<%=i%>]" rows="0" cols="0">
					   	 			<%
										Object obj = rd.get("TA_DJ_LINEDETAILs", "XCMX", i);
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
					<%
							}
						}
					%>
				</table>
				
			</div>
			<br>
			<div>
				<table class="datas">
					<tr id="first-tr">
						<td>&nbsp;&nbsp;服务标准</td>
					</tr>
					<tr>
						<td>
							<textarea name="TA_DJ_LINEMNG/FWBZ" rows="3"><%= FWBZ %></textarea>
						</td>
					</tr>
					
				</table>
			</div>
			<div>
				<table class="datas">
					<tr id="first-tr">
						<td>&nbsp;&nbsp;特别提醒</td>
					</tr>
					<tr>
						<td>
							<textarea name="TA_DJ_LINEMNG/TBTX" rows="3"><%= TBTX %></textarea>
						</td>
					</tr>
					
				</table>
			</div>
			<div>
				<table class="datas">
					<tr id="first-tr">
						<td>&nbsp;&nbsp;备注</td>
					</tr>
					<tr>
						<td>
							<textarea name="TA_DJ_LINEMNG/BZ" rows="3"><%= TBYQ %></textarea>
						</td>
					</tr>
					
				</table>
			</div>
			
			<div>
				<table class="datas">
					<tr>
						<td align="center">
							<br> 
								<input type="button" value="提    交" onclick="djAddLineBase();"/>&nbsp;&nbsp;
								<input type="button" value="返    回" onclick="javascript:history.go(-1);"/>
							<br> 
						</td>
					</tr>
					
				</table>
			</div>
			
		</div>
		
		
	</form>
</body>
</html>
