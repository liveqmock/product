/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util;

import com.dream.bizsdk.common.database.datadict.DataDict;

import java.io.FileNotFoundException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-6-20
 * Time: 14:39:24
 */
public interface IJSPGen {

    /**
     * @param root
     */
    void setRoot(String root);

    /**
     * @param dc
     */
    void setDataDict(DataDict dc);

    /**
     * @param tableName
     * @param jspFileName
     * @param twoCols
     * @param withMenu
     * @param subdir
     */
    void writeReadJSP(String tableName, String jspFileName, boolean twoCols, boolean withMenu, int subdir)
            throws FileNotFoundException;

    /**
     * @param tableName
     * @param jspFileName
     * @param newAction
     * @param twoColumns
     * @param withMenu
     * @param daoName
     * @param subDir
     */
    void writeNewJSP(String tableName, String jspFileName, String newAction, boolean twoColumns, boolean withMenu,
                     String daoName, int subDir) throws FileNotFoundException;

    /**
     * @param tableName
     * @param jspFileName
     * @param updateAction
     * @param twoColumns
     * @param withMenu
     * @param daoName
     * @param subDir
     */
    void writeUpdateJSP(String tableName, String jspFileName, String updateAction, boolean twoColumns,
                        boolean withMenu, String daoName, int subDir) throws FileNotFoundException;

    /**
     * @param tableName
     * @param jspFileName
     * @param listAction
     * @param withSelectCB
     * @param pagination
     * @param withSearch
     * @param withMenu
     * @param daoName
     * @param subDir
     */
    void writeListJSP(String tableName, String jspFileName, String listAction, boolean withSelectCB,
                      boolean pagination, boolean withSearch, boolean withMenu, String daoName, int subDir)
            throws FileNotFoundException;
}
