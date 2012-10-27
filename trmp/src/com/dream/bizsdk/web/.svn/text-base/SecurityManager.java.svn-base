package com.dream.bizsdk.web;

import javax.servlet.http.*;
import javax.servlet.ServletContext;
import java.util.Vector;
import java.util.HashMap;
import java.rmi.RemoteException;

import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.util.InetIP4Address;
import com.dream.bizsdk.common.blc.IBLContext;
import com.dream.bizsdk.common.blc.BLResult;

/**
 * Title:        engine
 * Description:  This is the platform of the business development kit.
 * Copyright:    Copyright (c) 2002
 * Company:      dream
 *
 * @author chuguanghua
 * @version 1.0
 */

public class SecurityManager implements ISecurityManager {
    protected IBLContext context;
    private HashMap roleFunctions;
    private HashMap functions;

    public SecurityManager() {

    }

    public SecurityManager(IBLContext context) {
        this.context = context;
    }

    public void init(ServletContext context) {
        this.context = (IBLContext) context.getAttribute(SysVar.APP_BLCONTEXT);
        roleFunctions = loadRoleFunctions();
        functions = loadFunctions();
    }

    /**
     * check if a user has access to a specified BLC object;
     *
     * @param BLCName the name of the blc object;
     * @return 1- if has access
     *         -1 - username and password doesn't match;
     *         -2 - user is disabled;
     *         -3 - user is locked;
     *         -4 - user has logined in another place and is not allowed to login
     *         on different place at the same time.
     *         -5 - session is not valid;
     */
    public int checkAccess(HttpSession session, String BLCName) {
        try {
            if (BLCName == null) {
                return SysError.BL_NOT_AVAILABLE;
            }
            //HashMap f = (HashMap) session.getServletContext().getAttribute(SysVar.APP_FUNCTIONS);
            //HashMap rf = (HashMap) session.getServletContext().getAttribute(SysVar.APP_RFUNCTIONS);
            String status = (String) functions.get(BLCName);
            if (status == null) {   //the BLC does not exist;
                return SysError.BL_NOT_AVAILABLE;
            } else if (status.compareTo("0") == 0) {         //the blc can be accessed by anyone;
                return SysError.PASS;
            } else {        //check if the BLC can be accessed by the current user;
                BizData sessionData = (BizData) session.getAttribute(SysVar.SS_DATA);
                if (sessionData == null) {
                    return SysError.NO_ACCESS;
                }
                if (roleFunctions != null) {
                    Vector ur = (Vector) sessionData.get(SysVar.USER_ROLES);    //sessionData has a _UR element;
                    if (ur != null) {
                        int i = 0;
                        int len = ur.size();
                        while (i < len) {
                            if (roleFunctions.get((String) ur.elementAt(i) + "_" + BLCName) != null) {
                                return SysError.PASS;
                            }
                            i++;
                        }
                    }
                }
                return SysError.NO_ACCESS;
            }
        } catch (Throwable t) {
            return SysError.NO_ACCESS;
        }
    }

    /**
     * @param sessionData
     * @param BLCName
     * @return
     */
    public int checkAccess(BizData sessionData, String BLCName) {
        try {
            if (BLCName == null) {
                return SysError.BL_NOT_AVAILABLE;
            }
            //HashMap f = (HashMap) session.getServletContext().getAttribute(SysVar.APP_FUNCTIONS);
            //HashMap rf = (HashMap) session.getServletContext().getAttribute(SysVar.APP_RFUNCTIONS);
            String status = (String) functions.get(BLCName);
            if (status == null) {   //the BLC does not exist;
                return SysError.BL_NOT_AVAILABLE;
            } else if (status.compareTo("0") == 0) {         //the blc can be accessed by anyone;
                return SysError.PASS;
            } else {        //check if the BLC can be accessed by the current user;
                if (roleFunctions != null) {
                    Vector ur = (Vector) sessionData.get(SysVar.USER_ROLES);    //sessionData has a _UR element;
                    if (ur != null) {
                        int i = 0;
                        int len = ur.size();
                        while (i < len) {
                            if (roleFunctions.get((String) ur.elementAt(i) + "_" + BLCName) != null) {
                                return SysError.PASS;
                            }
                            i++;
                        }
                    }
                }
                return SysError.NO_ACCESS;
            }
        } catch (Throwable t) {
            return SysError.NO_ACCESS;
        }
    }


    /**
     * check if a session is valid or not;
     *
     * @param session the http session object;
     * @return true if is valid,false if not;
     */
    public BizData checkSession(HttpSession session) {
        //a valid session should have BizData object named sessionData;
        Object sessionData = session.getAttribute(SysVar.SS_DATA);
        if (null == sessionData) {
            return null;
        } else {
            return (BizData) sessionData;
        }
    }

    /**
     * login a user to the system and create a session object for this user.
     * if the user currently can't login(if the value of status of the user is
     * less than 0) to the system.false is returned.
     *
     * @param userID the id of the user;
     * @return true if the user has correctly logined to the system. otherwise
     *         false
     */
    public int login(HttpServletRequest req, String userID, String password) {
        BLResult br = new BLResult(SysError.UNKNOWN_ERROR);
        String ip = req.getRemoteAddr();
        HttpSession session = req.getSession();
        BizData sessionData = new BizData();
        br = login(userID, password, ip, sessionData);
        if (br.retCode == SysError.PASS) {
            session.setAttribute(SysVar.SS_DATA, br.sd);
        }
        return br.retCode;
    }

    public HashMap getRoleFunctions() {
        return roleFunctions;
    }

    public HashMap getFunctions() {
        return functions;
    }

    public BLResult login(String userID, String password, String remoteIP, BizData sessionData) {
        String sql = "select userIP \"userIP\",userStartIP \"userStartIP\",userEndIP \"userEndIP\",userLoginOnlyThisIP \"userLoginOnlyThisIP\", userType \"userType\",userNO \"userNO\" from DRMUSER where userID='" + userID.replaceAll("'", "''") + "' and userpassword='" + password.replaceAll("'", "''") + "' and userstatus >0";
        int succ = SysError.PASS;
        String userNO = "0";
        try {
            BizData d = new BizData();
            d = context.execQuerySql("core", sql, "rs", d);
            if (d.getTableRowsCount("rs") > 0) {
                InetIP4Address remoteIPA = new InetIP4Address(remoteIP);
                String userIP = d.getString("rs", "userIP", "0");
                String userStartIP = d.getString("rs", "userStartIP", "0");
                String userEndIP = d.getString("rs", "userEndIP", "0");
                String userType = d.getString("rs", "userType", "0");
                userNO = d.getString("rs", "userNO", "0");
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
                        InetIP4Address userStartIPA = new InetIP4Address(userStartIP);
                        if (remoteIPA.compareTo(userStartIPA) < 0) {
                            succ = SysError.USER_IP_ERROR;
                        }
                    }
                    if (userEndIP != null) {
                        InetIP4Address userEndIPA = new InetIP4Address(userEndIP);
                        if (remoteIPA.compareTo(userEndIPA) > 0) {
                            succ = SysError.USER_IP_ERROR;
                        }
                    }
                }
                if (succ > 0) {
                    addSessionData(sessionData, userID, userNO, userType);
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

    /**
     * @return
     */
    protected HashMap loadFunctions() {
        int count = 0;

        HashMap data = new HashMap();
        BizData d = new BizData();
        try {
            d = context.execQuerySql("core", "select funcNO ,funcstatus from drmfunction", "rs", d);
            String[] rowIDs = d.getRowIDs("rs");
            count = rowIDs.length;
            for (int i = 0; i < count; i++) {
                String funcNO = d.getString("t", "funcNO", rowIDs[i]);
                String status = d.getString("t", "funcstatuc", rowIDs[i]);
                ;
                data.put(funcNO, status);
            }
            return data;
        } catch (Exception e) {
            System.out.println("BLContext.loadFunctions():SQLException:" + e.getMessage());
        }
        return data;
    }

    /**
     * Load Functions of the system from the core dao(database).
     *
     * @return a HashMap object contains all the funcrtions of the system;
     */
    protected HashMap loadRoleFunctions() {
        int count = 0;
        BizData d = null;

        HashMap data = new HashMap();
        try {

            d = context.execQuerySql("core", "select roleID,funcNO from drmrolefunction", "rs", d);
            String[] rowIDs = d.getRowIDs("rs");
            count = rowIDs.length;

            for (int i = 0; i < count; i++) {
                String roleID = d.getString("rs", "roleID", rowIDs[i]);
                String funcNO = d.getString("rs", "funcNO", rowIDs[i]);
                data.put(roleID + "_" + funcNO, funcNO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * @param sessionData
     * @param userID
     * @param userNO
     * @param userType
     */
    protected void addSessionData(BizData sessionData, String userID, String userNO, String userType) {
        Vector r = new Vector();
        BizData d = new BizData();

        sessionData.add("userID", userID);
        sessionData.add("userType", userType);
        sessionData.add("userNO", userNO);
        try {
            d = context.execQuerySql("core", "select roleID from DRMUSERROLE where userID='" + userID.replaceAll("'", "''") + "'", "rs", d);
            String[] rowIDs = d.getRowIDs("rs");

            for (int i = 0; i < rowIDs.length; i++) {
                String roleID = d.getString("rs", "roleID", rowIDs[i]);
                r.add(roleID);
                sessionData.add("DRMUserRoles", "roleID", roleID, roleID);
            }

        } catch (Exception re) {
            re.printStackTrace();
        }
        sessionData.add(SysVar.USER_ROLES, r);
    }
}