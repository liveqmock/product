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
<script type="text/javascript">
$(function() {
	//初始化button submit
	$( "input:submit,input:button").button();
	
});
</script>
<title>查看线路信息</title>


<%
	int userRows = rd.getTableRowsCount("HRDEPARTMENTs");	
%>
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
						<td colspan="3"><%=XLMC%></td>
					</tr>
					<tr>
						<td align="right">游客类型：</td>
						<td>散客</td>
					</tr>

					<tr>
						<td align="right">线路区域：</td>
						<td>
						<%
							for (int i = 0; i < rd.getTableRowsCount("xzqhs"); i++) {
								String id = rd.getStringByDI("xzqhs", "id", i);
								if (id.equals(XLQY)) {
						%>
								<%=rd.getStringByDI("xzqhs", "name", i)%>
						<%
								}
							}
						%>
						</td>
					</tr>



					<tr>
						<td align="right">天数：</td>
						<td><%=TS%>天<%=NIGHT%>晚
						</td>
					</tr>
					
					
					<tr>
						<td align="right">总人数：</td>
						<td>
							
							<%if("-1".equals(ZDKSRS)||"".equals(ZDKSRS)){ %>
									人数不限
							<%}else{ %>
									<%=ZDKSRS %>人
							<%} %>
						</td>
					</tr>
					
					<tr>
						<td align="right">可成团人数：</td>
						<td><%=KCTRS %>人</td>
					</tr>
					
					<tr>
						<td align="right">紧急联系电话：</td>
						<td>
							<%= JJLXDH %>
						</td>
					</tr>

					<tr>
						<td align="right">发班日期：</td>
						<td><%=FBJH%></td>
					</tr>
					
					<tr>
						<td align="right">用车：</td>
						<td>
							<%=YC%>
						</td>
					</tr>
					<tr>
						<td align="right">特殊人群收费：</td>
						<td><%=MP%></td>
					</tr>
					<tr>
						<td align="right" width="10%">购物：</td>
						<td><%=GW%></td>
					</tr>
					<tr>
						<td align="right">导游：</td>
						<td>
							<%=DY%>
						</td>
					</tr>
				</table>
			</div>

<!-- 价格类型 -->
			
			<div id="list-table">
				<table class="datas" id="store">
					<tr id="first-tr">
						<td>价格类型</td>
						<td>价&nbsp;&nbsp;&nbsp;&nbsp;格</td>
						<td>单房差</td>
						<td>备&nbsp;&nbsp;&nbsp;&nbsp;注</td>
					</tr>
				<%
	        	int jgRows = rd.getTableRowsCount("TA_DJ_XLJGs");
	        	for(int j=0;j<jgRows;j++){
	    			String jglx = rd.getStringByDI("TA_DJ_XLJGs","jglx",j);//价格类型
	    			String jg = rd.getStringByDI("TA_DJ_XLJGs","jg",j);//价格
	    			String dfc = rd.getStringByDI("TA_DJ_XLJGs","dfc",j);//单房差
	    			String bz = rd.getStringByDI("TA_DJ_XLJGs","bz",j);//单房差
	        	%>
					<tr class="valueType">
						<td><%=jglx %></td>
						<td><%=jg%></td>
						<td><%=dfc%></td>
						<td colspan="2"><%=bz%></td>
					</tr>
				<%
				}
				%>
				</table>
			</div>

<!-- 行程明细 -->
			
			<div class="add-table">
				<table class="datas" id="store1">
					<tr id="first-tr">
						<td align="center" width="15%">标准</td>
						<td align="center" width="70%">行程</td>
						
					</tr>
					<%
						int XCMXRows = rd.getTableRowsCount("TA_DJ_LINEDETAILs");
						if (XCMXRows > 0) {
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
						&nbsp;&nbsp;&nbsp;
						含&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;餐：<input type="checkbox" name="TA_DJ_LINEDETAIL/BREAKFAST[<%=i%>]" value="Y"
							<%if ("Y".equals(BREAKFAST)) {%> checked="checked" <%}%> />
						早 <input type="checkbox" name="TA_DJ_LINEDETAIL/CMEAL[<%=i%>]" value="Y"
							<%if ("Y".equals(CMEAL)) {%> checked="checked" <%}%> />
						中 <input type="checkbox" name="TA_DJ_LINEDETAIL/SUPPER[<%=i%>]" value="Y"
							<%if ("Y".equals(SUPPER)) {%> checked="checked" <%}%> />
						晚<br><br>
							&nbsp;&nbsp;&nbsp;&nbsp;餐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标：<input type="text" name="TA_DJ_LINEDETAIL/CB[<%=i%>]" value="<%=CB %>" class="smallerInput1" /> <br><br>
							&nbsp;&nbsp;&nbsp;&nbsp;住宿城市：<input type="text" name="TA_DJ_LINEDETAIL/ZS[<%=i%>]" value="<%=ZS %>" class="smallerInput1" /> <br><br>
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
							<%= FWBZ %>
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
							<%= TBTX %>
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
							<%= TBYQ %>
						</td>
					</tr>
					
				</table>
			</div>
			
			<div>
				<table class="datas">
					<tr>
						<td align="center">
							<input type="button" value="返    回" onclick="javascript:history.go(-1);"/>
						</td>
					</tr>
					
				</table>
			</div>
			
		</div>
		
		
	</form>
</body>
</html>
