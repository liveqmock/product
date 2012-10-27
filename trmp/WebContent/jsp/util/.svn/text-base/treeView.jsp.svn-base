<%@page contentType="text/html;charset=GBK"%>
<%@include file="/jsp/common.jsp"%>
<%
	/**
	 *desc:
	 *	display a tree view.
	 *
	 *intput: 
	 *	url,
	 *	target,
	 *	rs,
	 *	idCol,
	 *	parentIdCol,
	 *	nameCol,
	 *	rootUrl,
	 *	rootLable
	 */
	 
	//url when click on a tree node in the tree;
	String url = (String)rd.get("url");
	//if not specified,set default value "#";
	if(url==null){
		url="#";
	}
	//the target window that will display the above URL;		
	String target=(String)rd.get("target");
	//if not specified,set default value 'main';
	if(target==null){
		target="main";
	}
	String rs=rd.getString("rs");
	String idCol=rd.getString("idCol");
	String parentIdCol=rd.getString("parentIdCol");
	String nameCol=rd.getString("nameCol");
	String rootLabel = rd.getString("rootLabel");
	String rootUrl = rd.getString("rootUrl");
	String contextPath = request.getContextPath();
%>
<html>
  <head>
    <link rel="StyleSheet" href="<%=contextPath%>/css/tree.css" type="text/css">
    <link rel="StyleSheet" href="<%=contextPath%>/css/style.css" type="text/css">
    <title>Ê÷ÊÓÍ¼</title>
    <script type="text/javascript" src="<%=contextPath%>/js/tree.js"></script>
  </head>
  <body>
    <DIV id="tree" nowrap>
      <script type="text/javascript">
	// nodeId | parentNodeId | nodeName | nodeUrl
	var Tree =  new Array (<%int rows=rd.getTableRowsCount(rs);for(int i=0;i<rows;i++){String id=rd.getStringByDI(rs,idCol,i);String parentId=rd.getStringByDI(rs,parentIdCol,i);String name="";//rd.getStringByDI(nameCol,rd,i);%>"<%="1"+id%>|<%=parentId.compareTo(id)==0||parentId.length()==0?"":"1"+parentId%>|<%=name%>|<%=url+idCol%>=<%=id%>|<%=target%>",<%}%>"end");
	createTree(Tree,0, "1","<%=rootLabel%>","<%=rootUrl%>");
      </script>
    </DIV>
  </body>
</html>