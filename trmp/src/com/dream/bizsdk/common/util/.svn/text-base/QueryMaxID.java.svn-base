package com.dream.bizsdk.common.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.database.dao.DAO;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

public class QueryMaxID {

	private static QueryMaxID queryMaxObj = null;
	private static final byte[] busy = new byte[1];
	private static final byte[] busy2 = new byte[1];
	private static Logger log = Logger.getLogger(QueryMaxID.class);
	private static GeneralCacheAdministrator cacheAdmin = new GeneralCacheAdministrator();
	private QueryMaxID(){}
	
	public static QueryMaxID getInstance(){
		
		if(queryMaxObj == null)
			return new QueryMaxID();
		else
			return queryMaxObj;
	}
	
	/**
	 * 
	 * @param tbName 表名
	 * @param fldName 字段名
	 * @param where 查询条件语句，格式如：zt=1，如果没有则null
	 * @param dao
	 * @return
	 */
	public int queryMaxIDs(String tbName,String fldName, String where, DAO dao){
		
		synchronized(busy){
			int max = 0;
			if (tbName.equals("") || fldName.equals(""))
				return -251;
			try {

				Object obj = cacheAdmin.getFromCache("ids",7200);
				boolean isFind = false;
				//遍历缓存对象，是否存在表tbName
				List objList = (List) obj;
				for (int i = 0; i < objList.size(); i++) {

					BeanTable table = (BeanTable) objList.get(i);
					String tableName = table.getTableName();
					String fldNameObj = table.getFieldName();
					if (fldName.toUpperCase().equals(fldNameObj.toUpperCase()) && tableName.toUpperCase().equals(tbName.toUpperCase())) {

						max = table.getTheID();
						max += 1;
						objList.remove(i);
						objList = this.setDataValue(objList, table, tableName, fldName, "", max);
						log.debug("缓存中有记录--表名：" + tableName + " 字段名：" + fldName + " ID：" + max);
						isFind = true;
						break;
					}
				}
				
				//遍历完LIST没有找到执行
				if(isFind == false){
					
					max = queryByPara(tbName, fldName, where, dao);
					max += 1;
					BeanTable table = new BeanTable();
					objList = this.setDataValue(objList, table, tbName, fldName, "", max);
					log.debug("缓存中没有记录，新建序列，表名：" + tbName + "字段名：" + fldName + "ID：" + max);
				}
				cacheAdmin.putInCache("ids", objList);
				return max;
				
			} catch (NeedsRefreshException e) {
				
				cacheAdmin.cancelUpdate("ids");
				try {
					max = queryByPara(tbName, fldName, where, dao);
					max += 1;
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				BeanTable table = new BeanTable();
				List arryList = new ArrayList();
				arryList = this.setDataValue(arryList, table, tbName, fldName, "", max);
				cacheAdmin.putInCache("ids", arryList);
				log.error("缓存中没有记录，新查询的序号，表名："+ table + " 字段名：" + "fieldName" + " ID：" + max);
				return max;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	return 0;
	}
	
	/**
	 * 根据机构编号获得最大值，联合组键用
	 * @param tbName
	 * @param fldName
	 * @param orgid
	 * @param where
	 * @param dao
	 * @return
	 */
	public int queryMaxIDsByOrgid(String tbName,String fldName, String orgid, String where, DAO dao){
		
		synchronized(busy2){
			int max = 0;
			if (tbName.equals("") || fldName.equals(""))
				return -251;
			try {

				Object obj = cacheAdmin.getFromCache("ids",7200);
				boolean isFind = false;
				//遍历缓存对象，是否存在表tbName
				List objList = (List) obj;
				for (int i = 0; i < objList.size(); i++) {

					BeanTable table = (BeanTable) objList.get(i);
					String tableName = table.getTableName();
					String fldNameObj = table.getFieldName();
					String orgID = table.getOrgid();
					if (fldName.toUpperCase().equals(fldNameObj.toUpperCase()) 
							&& tableName.toUpperCase().equals(tbName.toUpperCase())
							&& orgID.equals(orgid)) {

						max = table.getTheID();
						max += 1;
						objList.remove(i);
						objList = this.setDataValue(objList, table, tableName, fldName, orgid, max);
						log.debug("缓存中有记录--表名：" + tableName + " 字段名：" + fldName + " ID：" + max);
						isFind = true;
						break;
					}
				}
				
				//遍历完LIST没有找到执行
				if(isFind == false){
					
					max = queryByPara(tbName, fldName, where, dao);
					max += 1;
					BeanTable table = new BeanTable();
					objList = this.setDataValue(objList, table, tbName, fldName, orgid, max);
					log.debug("缓存中没有记录，新建序列，表名：" + tbName + "字段名：" + fldName + "ID：" + max);
				}
				cacheAdmin.putInCache("ids", objList);
				return max;
				
			} catch (NeedsRefreshException e) {
				
				cacheAdmin.cancelUpdate("ids");
				try {
					max = queryByPara(tbName, fldName, where, dao);
					max += 1;
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				BeanTable table = new BeanTable();
				List arryList = new ArrayList();
				arryList = this.setDataValue(arryList, table, tbName, fldName, orgid, max);
				cacheAdmin.putInCache("ids", arryList);
				log.error("缓存中没有记录，新查询的序号，表名："+ table + " 字段名：" + "fieldName" + " ID：" + max);
				return max;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	private int queryByPara(String tbName,String fldName,String where,DAO dao) throws SQLException {
		
		String sql = "select max("+fldName+") maxID from "+tbName;
		if(where != null && !where.equals(""))
			sql += " where "+where;
		log.debug("取表"+tbName+"字段"+fldName+"最大值SQL："+sql);
		Connection conn = dao.getConnection();
		ResultSet resultSet = dao.executeQuery(sql,conn);
		resultSet.next();
		int maxID = resultSet.getInt("maxID");
		
		if(null != resultSet){
			resultSet.getStatement().close();
			resultSet.close();
			resultSet = null;
		}
		if(null != conn){
			conn.close();
			conn = null;
		}
		return maxID;
	}
	
	private List setDataValue(List list,BeanTable obj,String tableName,String fieldName, String orgid,int id) {
		
		obj.setFieldName(fieldName);
		obj.setTheID(id);
		obj.setTableName(tableName);
		obj.setOrgid(orgid);
		list.add(obj);
		return list;
	}
	
	public void clearCacheObj(String tbName,String fldName){ 
		
		synchronized(busy){
			try {
				Object obj = cacheAdmin.getFromCache("ids",30*60);
				List objList = (List) obj;
				for (int i = 0; i < objList.size(); i++) {

					BeanTable table = (BeanTable) objList.get(i);
					String tableName = table.getTableName();
					String fldNameObj = table.getFieldName();
					if (fldName.toUpperCase().equals(fldNameObj.toUpperCase()) && tableName.toUpperCase().equals(tbName.toUpperCase())) {
						objList.remove(i);
					}
				}
				cacheAdmin.putInCache("ids", objList);
			} catch (NeedsRefreshException e) {
				
				cacheAdmin.cancelUpdate("ids");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据指定日期查询这个日期的团号的最大值
	 * @param date
	 * @param dao
	 * @return
	 * @throws SQLException
	 */
	private String createGroupNO(String date, String prefix, String tableNm, String filedNm, int orgid, DAO dao, Connection conn) throws SQLException {
		
		/*String sql = "select substr(p."+filedNm+",1,10) prefix, max(substr(p."+filedNm+",11)) orderid \n" +
				"from "+tableNm+" p \n" +
				"where substr(p."+filedNm+",5,6) = '"+date+"' \n" +
				"and p.orgid=" + orgid +
				" group by substr(p."+filedNm+",1,10)";*/
		String sql = "select reverse(substr(reverse(t."+filedNm+"),0,instr(reverse(t."+filedNm+"),'_')-1)) orderid\n" +
				"from "+tableNm+" t \n" +
				"where t.orgid="+orgid+" and substr(t."+filedNm+",0,length(t."+filedNm+")-length(reverse(substr(reverse(t."+filedNm+"),0,instr(reverse(t."+filedNm+"),'_')-1)))-1)='"+prefix+"'\n" +
				"and substr(reverse(substr(reverse(t."+filedNm+"),0,instr(reverse(t."+filedNm+"),'_')-1)),0,6)='"+date+"'";
		
		ResultSet rs = dao.executeQuery(sql, conn);
		log.debug("生成团号SQL："+sql);
		String groupID = "";
		while(rs.next()){
			groupID = rs.getString("orderid");
		}
		if(null == groupID || "".equals(groupID)){
			groupID = prefix+"_"+date+"1";
		}else{
			int I_group = Integer.parseInt(groupID);
			I_group = I_group+1;
			groupID = prefix+"_"+String.valueOf(I_group);
		}
		rs.close();
		rs.getStatement().close();
		return groupID;
	}
	
	/**
	 * 根据日期生成团号
	 * @param dateCase 日期格式：110329
	 * @param tableNm 表名
	 * @param filedNm 字段名
	 * @param orgid 当前用户所在机构编号
	 * @param dao
	 * @return
	 */
	public String queryGroupNOFromCache(String prefixOfGroup, String dateCase, String tableNm, String fielddNm, int orgid, DAO dao, Connection conn) {
		
		synchronized(busy){
			try {
				
				List<BeanGroupNO> groupIDList = new ArrayList<BeanGroupNO>();
				Object obj = cacheAdmin.getFromCache("groupIDs",7200);
				groupIDList = (List<BeanGroupNO>)obj;
				boolean find = false;
				for(int i=0;i<groupIDList.size();i++) {
					
					BeanGroupNO groupNOsObj = groupIDList.get(i);
					String date = groupNOsObj.getDate();
					int orgInfo = groupNOsObj.getOrgid();
					String prefix = groupNOsObj.getGroupPrefix();
					if(date.equals(dateCase) && orgInfo == orgid && prefix.equals(prefixOfGroup)) {

						find = true;
						String gNO = groupNOsObj.getGroupID();
						//String prefix = gNO.substring(0, 10);//团号前缀
						int _lastIdx = gNO.lastIndexOf("_");
						gNO = gNO.substring(_lastIdx+1);
						int gNOI = Integer.parseInt(gNO);
						gNOI = gNOI + 1;//团号自增值
						BeanGroupNO beanGroupNO = new BeanGroupNO();
						beanGroupNO.setDate(date);
						beanGroupNO.setOrgid(orgid);
						beanGroupNO.setGroupPrefix(prefixOfGroup);
						beanGroupNO.setGroupID(prefixOfGroup+"_"+String.valueOf(gNOI));
						groupIDList.remove(i);
						groupIDList.add(beanGroupNO);
						cacheAdmin.putInCache("groupIDs", groupIDList);
						return prefixOfGroup+"_"+String.valueOf(gNOI);
					}
				}
				
				if(find == false){
					String gID = createGroupNO(dateCase, prefixOfGroup, tableNm, fielddNm, orgid, dao, conn);
					BeanGroupNO beanGroupNO = new BeanGroupNO();
					beanGroupNO.setDate(dateCase);
					beanGroupNO.setOrgid(orgid);
					beanGroupNO.setGroupID(gID);
					beanGroupNO.setGroupPrefix(prefixOfGroup);
					groupIDList.add(beanGroupNO);
					cacheAdmin.putInCache("groupIDs", groupIDList);
					return gID;
				}
			} catch (NeedsRefreshException e) {
				cacheAdmin.cancelUpdate("groupIDs");
				String gID = "";
				try {
					gID = createGroupNO(dateCase, prefixOfGroup, tableNm, fielddNm, orgid, dao, conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				BeanGroupNO beanGroupNO = new BeanGroupNO();
				beanGroupNO.setDate(dateCase);
				beanGroupNO.setGroupID(gID);
				beanGroupNO.setGroupPrefix(prefixOfGroup);
				beanGroupNO.setOrgid(orgid);
				List<BeanGroupNO> groupIDList = new ArrayList<BeanGroupNO>();
				groupIDList.add(beanGroupNO);
				cacheAdmin.putInCache("groupIDs", groupIDList);
				return gID;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return "";
	}
}
