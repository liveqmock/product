package com.trm.login;

import java.rmi.RemoteException;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.blc.BLResult;
import com.dream.bizsdk.common.blc.IBLContext;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.InetIP4Address;

public class Login {
	protected IBLContext context;

	public Login() {

	}

	public Login(IBLContext context) {
		this.context = context;
	}

	public void init(ServletContext context) {
		this.context = (IBLContext) context.getAttribute(SysVar.APP_BLCONTEXT);
	}

	public int login(HttpServletRequest req, String user, String pass) {
		BLResult br = new BLResult(SysError.UNKNOWN_ERROR);
		String ip = req.getRemoteAddr();
		HttpSession session = req.getSession();
		BizData sessionData = new BizData();
		br = login(user, pass, ip, sessionData);
		if (br.retCode == SysError.PASS) {
			session.setAttribute(SysVar.SS_DATA, br.sd);
		}
		return br.retCode;
	}

	public BLResult login(String user, String pass, String remoteIP,
			BizData sessionData) {
		String sql = "select userIP \"userIP\",userStartIP \"userStartIP\",userEndIP \"userEndIP\""
			+",userLoginOnlyThisIP \"userLoginOnlyThisIP\", userType \"userType\",userNO \"userNO\",username \"username\"\n" +
			",o.orgid,d.deptid,o.simplename,o.name,d.sfbsc\n"
			+ " from DRMUSER u,hrdepartment d,hrorganization o\n" +
			" where u.deptid=d.deptid and d.orgid=o.orgid and userID='"
			+ user.replaceAll("'", "''")
			+ "' and userpassword='"
			+ pass.replaceAll("'", "''") + "' and userstatus >0";
		int succ = SysError.PASS;
		int userNO = 0;
		try {
			BizData d = new BizData();
			d = context.execQuerySql("core", sql, "rs", d);
			if (d.getTableRowsCount("rs") > 0) {
				InetIP4Address remoteIPA = new InetIP4Address(remoteIP);
				String userIP = d.getString("rs", "userIP", "0");
				String userStartIP = d.getString("rs", "userStartIP", "0");
				String userEndIP = d.getString("rs", "userEndIP", "0");
				String username = d.getString("rs", "username", "0");
				String userType = d.getString("rs", "userType", "0");
				String orgID = d.getString("rs", "orgid", "0");
				String deptID = d.getString("rs", "deptid", "0");
				String simpleName = d.getString("rs", "simplename", "0");
				String name = d.getString("rs", "name", "0");
				userNO = d.getInt("rs", "userNO", "0");
				int isOnlyThisIP = d.getInt("rs", "userLoginOnlyThisIP", "0");
				if (isOnlyThisIP == 1) {
					if (userIP != null) {
						InetIP4Address userIPA = new InetIP4Address(userIP);
						if (userIPA.compareTo(remoteIPA) != 0) {
							succ = SysError.USER_IP_ERROR;
						}
					}
				} else if (isOnlyThisIP == 0) {
					if (userStartIP != null) {
						InetIP4Address userStartIPA = new InetIP4Address(
								userStartIP);
						if (remoteIPA.compareTo(userStartIPA) < 0) {
							succ = SysError.USER_IP_ERROR;
						}
					}
					if (userEndIP != null) {
						InetIP4Address userEndIPA = new InetIP4Address(
								userEndIP);
						if (remoteIPA.compareTo(userEndIPA) > 0) {
							succ = SysError.USER_IP_ERROR;
						}
					}
				}
				if (succ > 0) {
					//addSessionData(sessionData, user, userNO, userType);
					addSessionData(sessionData, user, userNO, username, userType, orgID, deptID, simpleName, name);
					return new BLResult(succ, null, sessionData);
				} else {
					return new BLResult(succ, null, sessionData);
				}
			} else {
				return new BLResult(SysError.PASSWORD_ERROR, sessionData);
			}
		} catch (RemoteException re) {
			re.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BLResult(SysError.UNKNOWN_ERROR, null, sessionData);
	}

	protected void addSessionData(BizData sessionData, String userID,
			int userNO, String username, String uType, String orgID, String deptID, String simpleName, String name) {
		Vector r = new Vector();
		BizData d = new BizData();

		sessionData.add("userID", userID);
		sessionData.add("username", username);
		sessionData.add("userType", uType);
		sessionData.add("userNO", new Integer(userNO));
		sessionData.add("orgID", orgID);
		sessionData.add("deptID", deptID);
		sessionData.add("simpleName", simpleName);
		sessionData.add("orgName", name);
		try {
			String sql = "select u.roleID,r.rolename,r.isadmin \n" +
					"from DRMUSERROLE u,drmrole r \n" +
					"where u.roleid=r.roleid and u.orgid=r.orgid\n" +
					"and u.userID='" + userID.replaceAll("'", "''") + "'";
			d = context.execQuerySql("core", sql, "rs", d);
			String[] rowIDs = d.getRowIDs("rs");

			for (int i = 0; i < rowIDs.length; i++) {
				String roleID = d.getString("rs", "roleID", rowIDs[i]);
				String roleName = d.getString("rs", "rolename", rowIDs[i]);
				
				r.add(roleID);
				sessionData.add("DRMUserRoles", "roleID", roleID, roleID);
				sessionData.add("DRMUserRoles", "rolename", rowIDs[i], roleName);
				
				// 添加角色是否为admin
				sessionData.add("DRMUserRoles", "isAdmin", rowIDs[i], d.getString("rs","isadmin",rowIDs[i]));
			}

		} catch (Exception re) {
			re.printStackTrace();
		}
		sessionData.add(SysVar.USER_ROLES, r);
	}
}
