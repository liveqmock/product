<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%@page import="com.dream.bizsdk.common.database.datadict.DataDict"%>
<%@page import="java.util.HashMap"%>
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
            return validate(frm);
        }
    //-->
    </script>
  </head>
  <body onload="init_form(document.forms(0))">
  <form name="info" method="post" action="queryError." onsubmit="return check_form(document.forms[0])">
  <input type="hidden" name="orderBy" value="colCode">
    <p>更新DAO的配置</p>
    <table class="hci" border="0" cellpadding="0" width="450">
      <tr>
        <td class="head" width="20%">DAO名称</td>
        <td class="content"  width="80%">
          <input name="name" type="text" value="<%=rd.getString(daoName)%>" size="50" class="m_" readonly>
        </td>
      </tr>
      <tr>
        <td class="head" width="20%">数据库类型</td>
        <td class="content"  width="80%">
          <input name="dbType" type="text" value="<%=rd.getString(daoName)%>" size="50" class="m_">
        </td>
      </tr>
      <tr>
        <td class="head" width="20%">JDBC驱动程序</td>
        <td class="content"  width="80%">
          <input name="dbDriver" type="text" value="<%=rd.getString(daoName)%>" size="50" class="m_">
        </td>
      </tr>
      <tr>
        <td class="head" width="20%">数据库URL</td>
        <td class="content"  width="80%">
          <input name="dbURL" type="text" value="<%=rd.getString(daoName)%>" size="50" class="m_">
        </td>
      </tr>
      <tr>
        <td class="head" width="20%">用户名</td>
        <td class="content"  width="80%">
          <input name="dbUser" type="text" value="<%=rd.getString(daoName)%>" size="50" class="m_">
        </td>
      </tr>
      <tr>
        <td class="head" width="20%">密码</td>
        <td class="content"  width="80%">
          <input name="dbPassword" type="text" value="<%=rd.getString(daoName)%>" size="50" class="m_">
        </td>
      </tr>
      <tr>
        <td class="head" width="20%">数据库连接池大小</td>
        <td class="content"  width="80%">
          <input name="dbConnections" type="text" value="<%=rd.getString(daoName)%>" size="50" class="m_integer">
        </td>
      </tr>
    </table>
    <input type="button" name="btnSubmit" value="提交" onclick="javascript:save()">
    <input type="reset" name="btnReset" value="重置">
  </form>
  </body>
</html>