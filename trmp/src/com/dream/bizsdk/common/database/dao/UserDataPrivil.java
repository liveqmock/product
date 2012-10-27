/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.database.dao;

import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.string.StringConvertor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import java.io.Serializable;

/**
 * Description:
 * <p/>
 * Author: chuguanghua
 * Date: 2004-6-8
 */
public class UserDataPrivil implements Serializable {
    private HashMap fullAccessTable = new HashMap();
    private HashMap fullAccessCol = new HashMap();

    private HashMap rowPrivil = new HashMap();
    private HashMap fullRowAccess = new HashMap();

    //private HashMap limitAccessTable= new HashMap();
    private HashMap limitAccessCol = new HashMap();

    private String daoName;

    public UserDataPrivil() {

    }

    protected void addFAT(String table) {
        fullAccessTable.put(table.toUpperCase(), table);
    }

    protected void addFAC(String table, String col) {
        fullAccessCol.put(table.toUpperCase() + "/" + col.toUpperCase(), col);
    }

    protected void addLAC(String table, String column, String p) {
        limitAccessCol.put(table.toUpperCase() + "/" + column.toUpperCase(), p);
    }

    protected boolean isFAT(String table) {
        if (fullAccessTable.get(table) != null) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean isLAT(String table) {
        return false;
    }

    protected boolean isFAC(String table, String column) {
        if (fullAccessCol.get(table.toUpperCase() + "/" + column.toUpperCase()) != null) {
            return true;
        } else {
            return false;
        }
    }

    protected void setRowPrivil(String table, String subWhere) {
        rowPrivil.put(table, subWhere);
    }

    public String getColPrivil(String table, String column) {
        String comb = table.toUpperCase() + "/" + column.toUpperCase();

        if (fullAccessTable.get(table) != null) {
            return "";
        }
        if (fullAccessCol.get(comb) != null) {
            return "";
        }
        return (String) limitAccessCol.get(comb);
    }

    public String getRowPrivil(String table) {
        if (fullRowAccess.get(table) != null) {
            return null;
        } else {
            return (String) rowPrivil.get(table);
        }
    }

    public void mergeColPrivil(BizData colPrivil, String roleID) {
        int totalTablesCount = 0;
        int roleTablesCount = 0;

        HashMap total = new HashMap();

        HashSet hs = new HashSet();

        //update grade current LAT and LAC;
        Vector tables = colPrivil.getTables();
        for (int i = 0; i < tables.size(); i++) {
            Vector v = colPrivil.getTableFields((String) tables.get(i));
            for (int k = 0; k < v.size(); k++) {
                total.put(v.get(k), v.get(k));
            }
        }
        String[] toalTables = (String[]) total.keySet().toArray(new String[total.size()]);
        Vector roleTables = colPrivil.getTableFields(roleID);
        if (roleTables != null) {
            roleTablesCount = roleTables.size();
        }
        for (int i = 0; i < roleTablesCount; i++) {
            hs.add(roleTables.get(i));
        }
        totalTablesCount = toalTables.length;
        for (int i = 0; i < totalTablesCount; i++) {
            String tableName = (String) toalTables[i];
            if (hs.contains(tableName)) {
                String[] cols = colPrivil.getRowIDs(roleID);
                for (int j = 0; j < cols.length; j++) {
                    if (isFAC(tableName, cols[j])) {
                        continue;
                    } else {
                        String p = (String) colPrivil.get(roleID, tableName, cols[j]);
                        if (p == null || p.compareToIgnoreCase("W") == 0) {
                            addFAC(tableName, cols[j]);
                        } else {
                            String existing = getColPrivil(tableName, cols[j]);
                            if (existing == null) {
                                addLAC(tableName, cols[j], p);
                            } else {
                                if (existing.compareToIgnoreCase("N") == 0) {
                                    if (p.compareToIgnoreCase("R") == 0) {
                                        addLAC(tableName, cols[j], "R");
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                addFAT((String) toalTables[i]);
            }
        }
    }

    /**
     * @param sd
     * @param rowPrivil
     * @param roleID
     */
    public void mergeRowPrivil(BizData sd, BizData rowPrivil, String roleID) {

        int totalTablesCount = 0;
        int roleTablesCount = 0;
        HashSet hs = new HashSet();

        HashMap total = new HashMap();
        Vector tables = rowPrivil.getTables();
        for (int i = 0; i < tables.size(); i++) {
            Vector v = rowPrivil.getTableFields((String) tables.get(i));
            for (int k = 0; k < v.size(); k++) {
                total.put(v.get(k), v.get(k));
            }
        }
        totalTablesCount = total.size();
        String[] totalTables = (String[]) total.keySet().toArray(new String[total.size()]);

        Vector v = rowPrivil.getTableFields(roleID);
        if (v != null) {
            roleTablesCount = v.size();
        }
        for (int i = 0; i < roleTablesCount; i++) {
            hs.add(v.get(i));
        }

        for (int i = 0; i < totalTablesCount; i++) {
            String tableName = totalTables[i];

            if (fullRowAccess.get(tableName) != null) {
                continue;
            }
            if (!hs.contains(tableName)) {
                fullRowAccess.put(tableName, tableName);
                continue;
            }
            String privil = (String) rowPrivil.get(roleID, tableName, "0");

            if (privil != null) {
                String existing = getRowPrivil(tableName);
                privil = StringConvertor.replace(privil, sd);
                if (existing != null) {
                    if (existing.length() > 0) {
                        setRowPrivil(tableName, existing + " or " + privil);
                    }
                } else {
                    setRowPrivil(tableName, privil);
                }
            } else {
                setRowPrivil(tableName, "");
            }
        }
    }

    /**
     * @param daoName
     */
    public void setDAOName(String daoName) {
        this.daoName = daoName;
    }

    /**
     * @return
     */
    public String getDAOName() {
        return daoName;
    }
}
