package com.dream.app.sys;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.blc.BLContext;
import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.menu.MenuBar;

/**
 * Created by IntelliJ IDEA. User: administrator Date: 2004-3-23 Time: 19:44:29
 */
public class Menu extends DBBLC {
	protected DAO coreDAO = null;
	protected String menuTable = "DRMMENU";

	public boolean init(final BLContext context) {
		super.init(context);
		coreDAO = context.getDAO("core");
		return true;
	}
	
	public Menu(){
		entityName = "DRMMENU";
	}
	
	
	public int query(BizData rd, BizData sd){
		
		try {
			int rows = super.query(rd, sd);
			if(rows == 0){
				rd.add("PARENTMENUNAME",rd.getString("DRMMENU","PARENTMENUNAME",0));
				rd.add("MENULEVEL",rd.getInt("MENULEVEL"));
				rd.add("MODULETYPE",rd.getInt("MODULETYPE"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 99;
	}

	public int getMenu(BizData rd, BizData sd) throws SQLException {
		int i = 0;
		int count;
		StringBuffer sb = new StringBuffer();

		String contextPath = rd.getString(SysVar.CURRENT_CONTEXT_PATH);

		Vector v = (Vector) sd.get(SysVar.USER_ROLES);
		count = v.size();
		sb.append("(");
		while (i < count) {
			sb.append("'");
			sb.append(((String) v.elementAt(i)).replaceAll("'", "''"));
			sb.append("',");
			i++;
		}
		if (i > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append(")");
		String sql = "select distinct m.menuName, m.menuLabel, m.menuLeveL, m.parentMenuName, m.funcNO,m.dispOrder, m.MODULETYPE \n" +
				"from drmuser u,drmuserrole ur,drmrole r,drmrolemenu rm,drmmenu m\n" +
				"where u.userid=ur.userid and ur.roleid=r.roleid and ur.orgid=r.orgid\n" +
				"and r.orgid=rm.orgid and r.roleid=rm.roleid and rm.menuname=m.menuname\n" +
				"and m.menulevel<>1\n" +
				"and r.orgid="+sd.getString("orgid")+" and u.userno='"+sd.getString("userno")+"'\n" +
				"order by m.menuname";
		coreDAO.executeQuery(sql, "DRMRoleMenus", rd);
		sql = "select distinct m.menuName, m.menuLabel, m.menuLeveL, m.parentMenuName, m.funcNO,m.dispOrder, m.MODULETYPE \n" +
			"from drmuser u,drmuserrole ur,drmrole r,drmrolemenu rm,drmmenu m\n" +
			"where u.userid=ur.userid and ur.roleid=r.roleid and ur.orgid=r.orgid\n" +
			"and r.orgid=rm.orgid and r.roleid=rm.roleid and rm.menuname=m.menuname\n" +
			"and m.menulevel=1\n" +
			"and r.orgid="+sd.getString("orgid")+" and u.userno='"+sd.getString("userno")+"'\n" +
			"order by m.menuname";
		coreDAO.executeQuery(sql, "menuLevel1", rd);
		return 1;
	}
	
	public int getMyMenu(BizData rd, BizData sd) throws SQLException {
		int i = 0;
		int count;
		StringBuffer sb = new StringBuffer();

		String contextPath = rd.getString(SysVar.CURRENT_CONTEXT_PATH);

		Vector v = (Vector) sd.get(SysVar.USER_ROLES);
		count = v.size();
		sb.append("(");
		while (i < count) {
			sb.append("'");
			sb.append(((String) v.elementAt(i)).replaceAll("'", "''"));
			sb.append("',");
			i++;
		}
		if (i > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append(")");

		String sql = "select distinct m.menuName, m.menuLabel, m.menuLeveL, m.parentMenuName, m.funcNO,m.dispOrder from DRMUSER u,DRMUSERROLE ur,DRMROLE r,DRMROLEMENU rm,DRMMENU m where m.menuLevel='"
				+ (rd.getString("menuLevel").equals("") ? "0" : rd
						.getString("menuLevel"))
				+ "' and u.userID = ur.userID and ur.roleID = r.roleID and r.roleId = rm.roleID and rm.menuName = m.menuName and rm.roleID in "
				+ new String(sb) + " order by m.PARENTMENUNAME,m.dispOrder asc";
		BizData d = new BizData();
		coreDAO.executeQuery(sql, "DRMRoleMenus", d);
		rd.copyEntity(d, "DRMRoleMenus");
		if (rd.getString("menuLevel").equals("0")
				|| rd.getString("menuLevel").equals("")) {
			String menuItems = MenuBar.assembleMenu(contextPath, d);
			synchronized (sd) {
				sd.setModified(true);
				sd.add(SysVar.MENU_ITEMS, menuItems);
			}
		}

		return 1;
	}

	/**
	 * insert a new menu;
	 * 
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int insert(BizData rd, BizData sd) throws SQLException {
		int rows = rd.getTableRowsCount(menuTable);
		if (rows < 1) {
			return SysError.BL_PARAM_ERROR;
		} else {
			int menuId = queryMaxIDByPara("DRMMENU", "MENUNAME", null);
			String pId = rd.getStringByDI("DRMMENU", "PARENTMENUNAME", 0);
			if(pId.equals("0"))
				rd.add("DRMMENU", "MODULETYPE", "-1");
			else{//如果上级菜单ID不等于0，则MODULETYPE始终是当前菜单最顶级菜单的ID
				String moduleType = recursion4ID("DRMMENU", "PARENTMENUNAME", "MENUNAME", pId, "0", rd);
				rd.add("DRMMENU", "MODULETYPE", moduleType);
			}
			
			rd.add("DRMMENU", "MENUNAME", menuId);
			return coreDAO.insert(menuTable, rd);
		}
	}
	
	/**
	 * delete menus; input: DRMMenu/menuName(+);
	 * 
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int delete(BizData rd, BizData sd) throws SQLException {
		int rows = rd.getTableRowsCount(menuTable);
		if (rows < 1) {
			return SysError.BL_PARAM_ERROR;
		} else {
			BizData data = new BizData();
			String[] rowIDs = rd.getRowIDs(menuTable);
			data.add(menuTable, "menuName", rd.getString("DRMMENU", "menuName", rowIDs[0]));
			coreDAO.select(data);
			rd.add("MENULEVEL",data.getStringByDI("DRMMENUs", "MENULEVEL", 0));
			rd.add("MODULETYPE",data.getStringByDI("DRMMENUs", "MODULETYPE", 0));
			data.remove(menuTable);
			data = null;
			BizData d = new BizData();
			d.copyEntity(rd, menuTable);
			for (int i = 0; i < rows; i++) {
				String menuID = (String) d.get(menuTable, "menuName", rowIDs[i]);
				if (menuID != null) {
					d.add("DRMRoleMenu", "menuName", i, menuID);
				}
			}
			d.addAttr("DRMRoleMenu", "NO", "1");
			d.addAttr(menuTable, "NO", "0");
			coreDAO.delete(d);
			return 98;
		}
	}

	/**
	 * update a menu;
	 * 
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int update(BizData rd, BizData sd) throws SQLException {
		int rows = rd.getTableRowsCount(menuTable);
		if (rows != 1) {
			return SysError.BL_PARAM_ERROR;
		} else {
			String menuName = (String) rd.get(menuTable, "menuName", "0");
			if (menuName == null) {
				return SysError.BL_PARAM_ERROR;
			} else {
				rd.addAttr(menuTable, "menuName", "0", "oldValue", menuName);
				return coreDAO.update(rd);
			}
		}
	}

	/**
	 * assign one ore more menus to a role;
	 * 
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int assignMenu2Role(BizData rd, BizData sd) throws SQLException {
		String roleID = (String) rd.get("roleID");
		int rows = rd.getTableRowsCount(menuTable);
		if (roleID == null || rows < 1) {
			return SysError.BL_PARAM_ERROR;
		} else {
			BizData d = new BizData();
			String[] rowIDs = rd.getRowIDs(menuTable);
			for (int i = 0; i < rows; i++) {
				d.add("DRMRoleMenu", "roleID", i, roleID);
				d.add("DRMRoleMenu", "menuName", i, rd.get(menuTable,
						"menuName", rowIDs[i]));
			}
			coreDAO.insert(d);
		}
		return 0;
	}

	/**
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int deleteMenuFromRole(BizData rd, BizData sd) throws SQLException {
		int rows = rd.getTableRowsCount("DRMRoleMenu");
		if (rows >= 1) {
			rows = coreDAO.delete("DRMRoleMenu", rd);
		}
		return rows;
	}

	/**
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int queryMenuOfRole(BizData rd, BizData sd) throws SQLException {
		int rows = 0;
		String roleID = (String) rd.get("roleID");
		if (roleID != null) {
			String sql = "select m.* from DRMMENU m,DRMROLEMENU rm where m.menuName=rm.menuName and roleID='"
					+ roleID + "'";
			rows = coreDAO.executeQuery(sql, "DRMRoleMenus", rd);
		}
		return rows;
	}

	/**
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int expand(BizData rd, BizData sd) throws SQLException {
		if (rd.getTableRowsCount(menuTable) != 1) {
			return SysError.BL_PARAM_ERROR;
		} else {
			return coreDAO.expand(menuTable, rd);
		}
	}

	/**
	 * query all the child menu of a specified menu;
	 * 
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int querySubMenu(BizData rd, BizData sd) throws SQLException {
		int rows = 0;
		String sql = null;
		String parentMenuName = (String) rd.get("parentMenuName");

		rd.remove("DRMMenus");
		if (parentMenuName != null) {
			sql = "select * from DRMMENU m where parentMenuName='"
					+ parentMenuName.replaceAll("'", "''")
					+ "' order by m.dispOrder";
		} else {
			sql = "select * from DRMMENU m where parentMenuName is null  order by m.dispOrder";
		}

		rows = coreDAO.executeQuery(sql, "DRMMenus", rd);
		return rows;
	}

	/**
	 * query the same level menus with a specified menu;
	 * 
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int querySameLevelMenu(BizData rd, BizData sd) throws SQLException {
		String menuName = (String) rd.get("menuName");
		rd.remove("DRMMenu");
		rd.add("DRMMenu", "menuName", menuName);
		if (coreDAO.expand("DRMMENU", rd) > 0) {
			String parentMenuName = (String) rd.get("DRMMenu",
					"parentMenuName", "0");
			rd.add("parentMenuName", parentMenuName);
			return querySubMenu(rd, sd);
		} else {
			return 0;
		}
	}

	/**
	 * prepare data to update role's menu;
	 * 
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int queryRoleMenuForUpdate(BizData rd, BizData sd)
			throws SQLException {
		// 检验角色ID和机构ID合法性
		if (rd.get("DRMRole", "roleID", 0) == null || rd.get("DRMRole", "orgID", 0) == null)
		{
			return SysError.PARAM_VALIDATE_ERROR;
		}
		
		// 取角色ID
		int roleID = Integer.parseInt((String)rd.get("DRMRole", "roleID", 0));
			
		// 取机构ID
		int orgID = Integer.parseInt((String)rd.get("DRMRole", "orgID", 0));
			
		// 查询所有菜单
		coreDAO.executeQuery("select * from DRMMENU", "DRMMenus", rd);
			
		// 查询当前角色所在的机构下关联的菜单访问权限
		coreDAO.executeQuery("select * from DRMROLEMENU where roleID="
				+ roleID + " and orgID=" + orgID, "DRMRoleMenus", rd);
		
		
		String menus = MenuBar.assembleMenu2HTML(rd);
		
		rd.add("roleMenus", menus);
		
		return 999;
	}

	/**
	 * 更新角色关联的菜单信息
	 * 
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int updateRoleMenu(BizData rd, BizData sd) throws SQLException 
	{
		// 检验角色ID和机构ID合法性
		if (rd.get("DRMRole", "roleID", 0) == null || rd.get("DRMRole", "orgID", 0) == null)
		{
			return SysError.PARAM_VALIDATE_ERROR;
		}
		
		// 角色ID
		String roleID = (String) rd.get("DRMRole", "roleID", 0);
		
		// 机构ID
		String orgID = (String) rd.get("DRMRole", "orgID", 0);
		
		// 删除当前角色关联的菜单信息
		coreDAO.executeUpdate("delete DRMROLEMENU where orgID = " + orgID + " and roleID = " + roleID);
		
		Map<?,?> map = (Map<?, ?>) rd.getObject4Data("DRMMENU");
		
		Iterator<?> it = ((Map<?, ?>) map.get("MENUNAME")).entrySet().iterator();
		
		int i = 0;
		while(it.hasNext())
		{
			Entry<?,?>  entry = (Entry<?, ?>) it.next();
			
			String muname = (String) entry.getValue();
			
			rd.add("DRMROLEMENU", "MENUNAME", i, muname);
			rd.add("DRMROLEMENU", "ORGID", i, orgID);
			rd.add("DRMROLEMENU", "ROLEID", i, roleID);
			
			i++;
		}
		
		coreDAO.insert("DRMROLEMENU", rd);
		
		return 999;
	}
}