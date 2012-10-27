package com.trm.util;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.databus.BizData;

public class Util{
	
	private static Util util = null;
	private Util(){}
	
	public static Util getInstance(){
		if(util==null){
			util = new Util();
		}
		return util;
	}
	
	public String createTree(DAO dao,String url,int sign){
		
		StringBuffer stb= new StringBuffer();
		String sql = "select x.id ,x.name,x.pid from xzqh x ";
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = dao.getConnection();
			rs = dao.executeQuery(sql,conn);
			int i = 0;
			while(rs.next()){
				String id = rs.getString("id");
				stb.append("Tree["+(i++)+"] = '" + id + "|" +  rs.getString("pid")+ "|" + rs.getString("name")+"|"+url+id+"|"+sign+"';");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try{
				if(null != rs){
					rs.getStatement().close();
					rs.close();
					rs = null;
				}
				if(conn!=null){
					try {
						conn.close();
						conn = null;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}catch(SQLException e1){
				e1.printStackTrace();
			}
		}
		return stb.toString();
	}
public String createTree4City(DAO dao,String url,int sign){
		
		StringBuffer stb= new StringBuffer();
		String sql = "(select * from xzqh a where a.pid = 0) union (select * from xzqh b where b.pid in (select ID  from xzqh where pid = 0)) ";
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = dao.getConnection();
			rs = dao.executeQuery(sql,conn);
			int i = 0;
			while(rs.next()){
				String id = rs.getString("id");
				stb.append("Tree["+(i++)+"] = '" + id + "|" +  rs.getString("pid")+ "|" + rs.getString("name")+"|"+url+id+"|"+sign+"';");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try{
				if(null != rs){
					rs.getStatement().close();
					rs.close();
					rs = null;
				}
				if(conn!=null){
					try {
						conn.close();
						conn = null;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}catch(SQLException e1){
				e1.printStackTrace();
			}
		}
		return stb.toString();
	}
	public String createCanteenTree(DAO dao,String url,int sign){
		
		return createTree4City(dao, url, sign);
		/*
		
		StringBuffer stb= new StringBuffer();
		String sql1="select * from XZQH where PID=0";
		String sql2="select * from XZQH where PID in ( select ID from XZQH where PID=0 )";
		String sql3="select * from XZQH where PID in ( select ID from XZQH where PID in ( select ID from XZQH where PID=0 ) )";
		String sql="(" + sql1 +") union " + "(" + sql2 + ") union " + "(" + sql3 + ")";
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = dao.getConnection();
			rs = dao.executeQuery(sql,conn);
			int i=0;
			while(rs.next()){
				String id=rs.getString("id");
				stb.append("Tree["+(i++)+"] = '" + id + "|" +  rs.getString("pid")+ "|" + rs.getString("name")+"|"+url+id+"|"+sign+"';");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				if(null != rs){
					rs.getStatement().close();
					rs.close();
					rs = null;
				}
				if(null != conn){
					conn.close();
					conn = null;
				}
			}catch(SQLException e1){
				e1.printStackTrace();
			}
		}
		return stb.toString();
	*/
	}
	/*
	 * 生成行政区划xml文件
	 */
	public String generateXZQHXml(BizData rd,BizData sd,DAO dao) {
		boolean  sign = false;
		Map rs4Map = new HashMap(); 
		String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?> \n"
				+ "<TreeNodes>\n";
		 //国家
		String sql = "select id,name country,pid from XZQH where pid=0"; 
		//省
		String sql2 = "select id,name province,pid from xzqh where pid in(select id from xzqh where pid=0)"; 
		//城市
		String sql3 = "select id,name city,pid from XZQH where PID in " +
				"( select ID from XZQH where PID in ( select ID from XZQH where PID=0 ))";
		//地区
		String sql4="select id,name town,pid from xzqh where pid in(select id from XZQH where PID in " +
				"( select ID from XZQH where PID in ( select ID from XZQH where PID=0 ) ))";
		ResultSet rs1=null;
		ResultSet rs2=null;
		ResultSet rs3=null;
		ResultSet rs4=null;
		Connection conn = null;
		try{
			rs1=dao.executeQuery(sql,conn);
			rs2=dao.executeQuery(sql2,conn);
			rs3=dao.executeQuery(sql3,conn);
			rs4=dao.executeQuery(sql4,conn);
			
			while(rs1.next()){
				String country_id=rs1.getString("id");//国家ID
				String country=rs1.getString("country");//国家
				xml = xml + "<TreeNode Desc=\"" + country + "\" Value=\""
				+ country_id + "\" >\n";  //向xml写入国家
				while(rs2.next()){
					String province_id=rs2.getString("id");//省份ID
					String province=rs2.getString("province");//省份名称
					String province_pid=rs2.getString("pid");//省份父节点
					if(country_id.equals(province_pid)){
						xml += "<TreeNode Desc=\"" + province + "\" Value=\""
						+ province_id + "\" >\n";//向上级国家写入省
						
						while(rs3.next()){
								String city_id=rs3.getString("id");//市区ID	
								String city=rs3.getString("city");//市区名称	
								String city_pid=rs3.getString("pid");//市区父节点
								if(province_id.equals(city_pid)){
									xml += "<TreeNode Desc=\"" + city + "\" Value=\""
									+ city_id + "\" >\n";//向上级省写入城市
									
									while(rs4.next()){
										sign = true;
										String town_id=rs4.getString("id");
										String town_pid=rs4.getString("pid");
										String town=rs4.getString("town");
										if(city_id.equals(town_pid)){
										xml += "<TreeNode Desc=\"" + town + "\" Value=\""
										+ town_id + "\" />\n";//向上级城市写入地区
										}
									}
									if(sign){
										xml += "</TreeNode>\n";
										rs4=dao.executeQuery(sql4,conn);
									}
								}
						}
						rs3=dao.executeQuery(sql3,conn);
						xml += "</TreeNode>\n";
					}
					
				}
			}
			rs2=dao.executeQuery(sql2,conn);
			xml += "</TreeNode>\n";
		}catch(SQLException e){
			e.printStackTrace();
		}
		xml += "</TreeNodes>\n";
		try {
			HttpServletRequest request=(HttpServletRequest) rd.get("_httpservletrequest");
			String path=request.getRealPath("/");
			FileWriter fw = new FileWriter(path+"test.xml");
			fw.write(xml);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				if(null != rs1){
					rs1.getStatement().close();
					rs1.close();
					rs1 = null;
				}
				if(null != rs2){
					rs2.getStatement().close();
					rs2.close();
					rs2 = null;
				}
				if(null != rs3){
					rs3.getStatement().close();
					rs3.close();
					rs3 = null;
				}
				if(null != rs4){
					rs4.getStatement().close();
					rs4.close();
					rs4 = null;
				}
				if(conn!=null){
					try {
						conn.close();
						conn = null;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}catch(SQLException e1){
				e1.printStackTrace();
			}
		}
		return xml;
	}
	
	public static String createMap(DAO dao,String sql) throws SQLException {
		
		Map<String,String[]> map = new HashMap<String,String[]>();
		Map<String,List<String>> mapChildren = new HashMap<String,List<String>>();
		Connection conn = dao.getConnection();
		ResultSet rs = dao.executeQuery(sql, conn);
		while(rs.next()){
			String[] temp = new String[2];
			String id = rs.getString("ID");
			String pid = rs.getString("PID");
			String name = rs.getString("name");
			temp[0] = pid ;
			temp[1] = name;
			map.put(id, temp);
			if(mapChildren.get(pid)==null){
				List<String> list = new ArrayList<String>();
				list.add(id);
				mapChildren.put(pid, list);
			}else{
				mapChildren.get(pid).add(id);
			}
		}
		String bf = new String("<?xml version=\"1.0\" encoding=\"GBK\"?> \n <TreeNodes>");
		for(Map.Entry<String,String[]> entry:map.entrySet()){
			if("0".equals(entry.getValue()[0])){
				createXMLFile(map,mapChildren,entry.getKey(),bf);
			}
		}
		bf += ("</TreeNodes>");
		rs.getStatement().close();
		rs.close();
		rs = null;
		if(conn!=null){
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return bf.toString();
	}
	public static void createXMLFile(Map<String,String[]> map, Map<String,List<String>> childrenMap,String pid,String bf){
		String[] temp = map.get(pid);
		List<String> list = childrenMap.get(pid);
		if(list==null){
			bf += ("<TreeNode Desc=\""+temp[1]+"\" Value=\""+pid+"\" />\n");
		}else{
			bf += ("<TreeNode Desc=\""+temp[1]+"\" Value=\""+pid+"\" >\n");
			for(int i = 0;i<list.size();i++){	
				createXMLFile(map,childrenMap,(String)list.get(i),bf);
			}
			bf+=("</TreeNode>\n");
		}
	}
}