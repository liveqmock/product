<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%@page import="com.dream.bizsdk.common.database.datadict.DataDict"%>
<%@page import="java.util.HashMap"%>
<%
  int rows=10;
  String daoName="core";
  String tName="DRMFunction";
  String tNames="DRMFunctions";
  HashMap dicts=(HashMap)application.getAttribute(SysVar.APP_SDCS);
  DataDict dc=(DataDict)dicts.get(daoName);
  if(dc==null){
      dc=(DataDict)application.getAttribute(SysVar.APP_SDC);
  }
  BizData rd =(BizData) request.getAttribute(SysVar.REQ_DATA);
  if(rd==null){
  	rd=new BizData();
  }
  int pageNO=0;
  int pageSize=0;
  int pages=0;
  try{
  	pageNO=((Integer)rd.getAttr(tNames,"pageNO")).intValue();
  }catch(Exception e){
  }
  try{
  	pageSize=((Integer)rd.getAttr(tNames,"pageSize")).intValue();
  }catch(Exception e){
  }
  try{
  	pages=((Integer)rd.getAttr(tNames,"pages")).intValue();
  }catch(Exception e){
  }
  rows=rd.getTableRowsCount(tNames);
%>
<html>
  <head>
    <title>New record of DRMBizDataDict</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <link rel="stylesheet" href="../../css/style.css" type="text/css">
    <script src="../../js/forms.js" type="text/javascript"></script>
    <script language="javascript">
    <!--
        function init_form(frm){
            init(frm);
        }
        function check_form(frm){
          if(validate(frm)==false){
          	frm.action="queryError.";
          	return false;
          }else{
          }
        }
        function save(){
        	var frm = document.forms(0);
        	if(validate(frm)==false){
        		return;
        	}
        	frm.action="saveError.";
          	if(frm.elements("DRMError/errMessage").value=="" || frm.elements("DRMError/errCode").value==""){
          		alert("对不起，错误信息编号和错误信息内容不能为空！");
          		frm.elements("DRMError/errMessage").focus();
          		return false;
          	}
        	frm.submit();
        }  
        function queryPage(frm){
        }
    //-->
    </script>
  </head>
  <body onload="init_form(document.forms(0))">
  <form name="info" method="post" action="queryError." onsubmit="return check_form(document.forms(0))">
    <p>修改客户请求</p>
    <table class="hci" border="0" cellpadding="0" width="450">
      <tr class="head">
        <td width="15%">请求名称</td>
        <td width="50%">是否可以匿名访问</td>
        <td width="50%">说明</td>
      </tr>
      <%for(int i=0;i<rows;i++){%>	
      <tr>
        <td class="content"  width="35%">
          <input name="<%=tName%>/funcNO[<%=i%>]" type="text" value="<%=rd.getString(tName,"funcNO",i)%>" size="20" maxlength="<%=dc.getFieldLength(tName,"funcNO")%>"  class="m_">
        </td>
        <td class="content" width="15%"><input type="checkbox" value="<%=rd.getStringByDI(tName,"funcStatus",i)%>" name="DRMError/funcStatus[<%=i%>]" maxlength="<%=dc.getFieldLength(tName,"funcStatus")%>" class="integer"></td>        
        <td class="content"  width="50%"  colspan="2">
          <input name="<%=tName%>/funcName[<%=i%>]" type="text" value="<%=rd.getStringByDI(tName,"errMessage",i)%>" size="20" maxlength="<%=dc.getFieldLength(tName,"errMessage")%>">
        </td>
      </tr>
      <%}%>
    </table>
    <input type="button" name="btnSubmit" value="提交" onclick="javascript:save()">
    <input type="reset" name="btnReset" value="重置">
  </form>
  </body>
</html>