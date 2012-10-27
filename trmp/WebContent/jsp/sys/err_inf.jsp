<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%@page import="com.dream.bizsdk.common.database.datadict.DataDict"%>
<%@page import="java.util.HashMap"%>
<%@taglib prefix="menu" uri="/WEB-INF/tld/menu.tld"%>
<%
  int rows=10;
  String daoName="core";
  String tName="DRMError";
  String tNames="DRMErrors";
  HashMap dicts=(HashMap)application.getAttribute(SysVar.APP_SDCS);
  DataDict dc=(DataDict)dicts.get(daoName);
  if(dc==null){
      dc=(DataDict)application.getAttribute(SysVar.APP_SDC);
  }
  BizData rd =(BizData) request.getAttribute(SysVar.REQ_DATA);
  if(rd==null){
  	rd=new BizData();
  }
%>
<html>
  <head>
    <title>增加或修改错误信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <link rel="stylesheet" href="../../css/style.css" type="text/css">
    <link rel="stylesheet" href="../../css/menu.css" type="text/css">	
    <script src="../../js/forms.js" type="text/javascript"></script>
    <script language="javascript">
    <!--
        function init_form(frm){
            init(frm);
        }
        function check_form(frm){
          frm.elements("DRMError/errMessage").value="";
          frm.elements("DRMError/errLevel").value="";          
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
    //-->
    </script>
  </head>
  <body onload="init_form(document.forms(0))">
  <jsp:include page="/jsp/head.jsp"/>
  <menu:showBar x="12" y="16" height="23" width="100%"/>
  <form name="info" method="post" action="queryError." onsubmit="return check_form(document.forms(0))">
  <input type="hidden" name="orderBy" value="colCode">
    <p>增加或更新系统错误信息，这些信息将在发生指定编号的错误被显示到客户端。</p>
    <table class="hci" border="0" cellpadding="0" width="550">
      <tr>
        <td class="head" width="15%">错误信息编号</td>
        <td class="content"  width="35%">
          <input name="<%=tName%>/errCode" type="text" value="<%=rd.getString(tName,"errCode",0)%>" size="20" maxlength="<%=dc.getFieldLength(tName,"errCode")%>"  class="m_integer">
        </td>
        <td class="content"  width="10%">
          <input type="submit" name="btnQuery" value="查询">
        </td>
      </tr>
      <tr>
        <td class="head" width="50%">错误信息级别</td>
        <td class="content" width="15%" colspan="2"><input type="text" value="<%=rd.getStringByDI(tName,"errLevel",0)%>" name="DRMError/errLevel" maxlength="<%=dc.getFieldLength(tName,"errCode")%>" class="integer"></td>
      </tr>
      <tr>
        <td class="head"  width="50%">错误信息内容</td>
        <td class="content"  width="50%"  colspan="2">
          <input name="DRMError/errMessage" type="text" value="<%=rd.getStringByDI(tName,"errMessage",0)%>" size="60" maxlength="<%=dc.getFieldLength(tName,"errMessage")%>">
        </td>
      </tr>
    </table>
    <input type="button" name="btnSubmit" value="提交" onclick="javascript:save()">
    <input type="reset" name="btnReset" value="重置">
  </form>
  <menu:show/>
  </body>
</html>