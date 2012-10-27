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
	//��ʼ��button submit
	$( "input:submit,input:button").button();
	
});
</script>
<title>�鿴��·��Ϣ</title>


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
					<td id="first-tr" colspan="2">&nbsp;&nbsp;��·������Ϣ&nbsp;&nbsp;&nbsp;&nbsp;��<font color="red">*</font>��Ϊ������</td>
				</tr>
					<tr>
						<%
							String XLID = rd.getString("TA_DJ_LINEMNGs", "XLID", 0);//��·����
							String XLMC = rd.getString("TA_DJ_LINEMNGs", "XLMC", 0);//��·����
							String YKLX = rd.getString("TA_DJ_LINEMNGs", "YKLX", 0);//�ο�����
							String GW = rd.getString("TA_DJ_LINEMNGs", "GW", 0);//����
							String FBJH = rd.getString("TA_DJ_LINEMNGs", "FBJH", 0);//��������
							String YCBZ = rd.getString("TA_DJ_LINEMNGs", "YCBZ", 0);//�òͱ�׼
							String FWBZ = rd.getString("TA_DJ_LINEMNGs", "FWBZ", 0);//�����׼
							String DY = rd.getString("TA_DJ_LINEMNGs", "DY", 0);//����
							String YC = rd.getString("TA_DJ_LINEMNGs", "YC", 0);//�ó�
							String MP = rd.getString("TA_DJ_LINEMNGs", "MP", 0);//��Ʊ
							String SFLJKS = rd.getString("TA_DJ_LINEMNGs", "SFLJKS", 0);//�Ƿ���������
							String XLQY = rd.getString("TA_DJ_LINEMNGs", "XLQY", 0);//��·����
							String TS = rd.getString("TA_DJ_LINEMNGs", "TS", 0);//����
							String NIGHT = rd.getString("TA_DJ_LINEMNGs", "NIGHT", 0);//��
							String TBYQ = rd.getString("TA_DJ_LINEMNGs", "BZ", 0);//��ע
							String TBTX = rd.getString("TA_DJ_LINEMNGs", "TBTX", 0);//�ر�����
							String USERNO = rd.getString("TA_DJ_LINEMNGs", "USERNO", 0);//��·ָ����
							String KCTRS = rd.getString("TA_DJ_LINEMNGs", "KCTRS", 0);//�ɳ�������
							String JJLXDH = rd.getString("TA_DJ_LINEMNGs", "JJLXDH", 0);//������ϵ�绰
							String ZDKSRS = rd.getString("TA_DJ_LINEMNGs", "ZDKSRS", 0);//�����տ�����
							
						%>
						<td align="right" width="14%">��·���ƣ�</td>
						<td colspan="3"><%=XLMC%></td>
					</tr>
					<tr>
						<td align="right">�ο����ͣ�</td>
						<td>ɢ��</td>
					</tr>

					<tr>
						<td align="right">��·����</td>
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
						<td align="right">������</td>
						<td><%=TS%>��<%=NIGHT%>��
						</td>
					</tr>
					
					
					<tr>
						<td align="right">��������</td>
						<td>
							
							<%if("-1".equals(ZDKSRS)||"".equals(ZDKSRS)){ %>
									��������
							<%}else{ %>
									<%=ZDKSRS %>��
							<%} %>
						</td>
					</tr>
					
					<tr>
						<td align="right">�ɳ���������</td>
						<td><%=KCTRS %>��</td>
					</tr>
					
					<tr>
						<td align="right">������ϵ�绰��</td>
						<td>
							<%= JJLXDH %>
						</td>
					</tr>

					<tr>
						<td align="right">�������ڣ�</td>
						<td><%=FBJH%></td>
					</tr>
					
					<tr>
						<td align="right">�ó���</td>
						<td>
							<%=YC%>
						</td>
					</tr>
					<tr>
						<td align="right">������Ⱥ�շѣ�</td>
						<td><%=MP%></td>
					</tr>
					<tr>
						<td align="right" width="10%">���</td>
						<td><%=GW%></td>
					</tr>
					<tr>
						<td align="right">���Σ�</td>
						<td>
							<%=DY%>
						</td>
					</tr>
				</table>
			</div>

<!-- �۸����� -->
			
			<div id="list-table">
				<table class="datas" id="store">
					<tr id="first-tr">
						<td>�۸�����</td>
						<td>��&nbsp;&nbsp;&nbsp;&nbsp;��</td>
						<td>������</td>
						<td>��&nbsp;&nbsp;&nbsp;&nbsp;ע</td>
					</tr>
				<%
	        	int jgRows = rd.getTableRowsCount("TA_DJ_XLJGs");
	        	for(int j=0;j<jgRows;j++){
	    			String jglx = rd.getStringByDI("TA_DJ_XLJGs","jglx",j);//�۸�����
	    			String jg = rd.getStringByDI("TA_DJ_XLJGs","jg",j);//�۸�
	    			String dfc = rd.getStringByDI("TA_DJ_XLJGs","dfc",j);//������
	    			String bz = rd.getStringByDI("TA_DJ_XLJGs","bz",j);//������
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

<!-- �г���ϸ -->
			
			<div class="add-table">
				<table class="datas" id="store1">
					<tr id="first-tr">
						<td align="center" width="15%">��׼</td>
						<td align="center" width="70%">�г�</td>
						
					</tr>
					<%
						int XCMXRows = rd.getTableRowsCount("TA_DJ_LINEDETAILs");
						if (XCMXRows > 0) {
							List<GroupLineDetail> list = (List) rd.get("lineDetail");
							for (int i = 0; i < list.size(); i++) {
								GroupLineDetail gd = list.get(i);
								String RQ = gd.getRq();//����
								String XCMX = gd.getXcmx();//��·��ϸ
								String BREAKFAST = gd.getBreakfast();//��
								String CMEAL = gd.getCmeal();//��
								String SUPPER = gd.getSupper();//��
								String ZSBZ = gd.getZsbz();//ס�ޱ�׼
								String CB = gd.getCb();//�ͱ�
								String ZS = gd.getZs();//ס��
					%>
					<tr class="XCMXType">
						<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="6">D<%=RQ%></font>
						<input type="hidden" name="TA_DJ_LINEDETAIL/RQ[<%=i%>]" value="<%=RQ%>" />
						<br><br>
						&nbsp;&nbsp;&nbsp;
						��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ͣ�<input type="checkbox" name="TA_DJ_LINEDETAIL/BREAKFAST[<%=i%>]" value="Y"
							<%if ("Y".equals(BREAKFAST)) {%> checked="checked" <%}%> />
						�� <input type="checkbox" name="TA_DJ_LINEDETAIL/CMEAL[<%=i%>]" value="Y"
							<%if ("Y".equals(CMEAL)) {%> checked="checked" <%}%> />
						�� <input type="checkbox" name="TA_DJ_LINEDETAIL/SUPPER[<%=i%>]" value="Y"
							<%if ("Y".equals(SUPPER)) {%> checked="checked" <%}%> />
						��<br><br>
							&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�꣺<input type="text" name="TA_DJ_LINEDETAIL/CB[<%=i%>]" value="<%=CB %>" class="smallerInput1" /> <br><br>
							&nbsp;&nbsp;&nbsp;&nbsp;ס�޳��У�<input type="text" name="TA_DJ_LINEDETAIL/ZS[<%=i%>]" value="<%=ZS %>" class="smallerInput1" /> <br><br>
							&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�꣺<select name="TA_DJ_LINEDETAIL/ZSBZ[<%=i%>]" style="width: 100px;">
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
						<td>&nbsp;&nbsp;�����׼</td>
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
						<td>&nbsp;&nbsp;�ر�����</td>
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
						<td>&nbsp;&nbsp;��ע</td>
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
							<input type="button" value="��    ��" onclick="javascript:history.go(-1);"/>
						</td>
					</tr>
					
				</table>
			</div>
			
		</div>
		
		
	</form>
</body>
</html>
