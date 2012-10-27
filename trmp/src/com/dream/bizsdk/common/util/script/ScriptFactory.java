/*
 * ScriptFactory.java
 *
 * Created on 2003年11月10日, 下午4:23
 */

package com.dream.bizsdk.common.util.script;

import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.SysVar;

/**
 * Script factor contains the method to get a Script Instance of
 * a spcified database script,currently only support Oracle script;
 * return null for any other type of database.
 *
 * @author chuguanghua
 */
public class ScriptFactory {
    public static final String ORA_SCRIPT = "oracle";
    public static final String SQL_SCRIPT = "sql";

    /**
     * Creates a new instance of ScriptFactory
     */
    public static Script getScript(String scriptType) {
        if (scriptType != null &&
                (scriptType.compareToIgnoreCase(SysVar.DB_ORACLE) == 0 || scriptType.compareToIgnoreCase(SysVar.DB_SQL) == 0)) {
            return new Script();
        } else {
            return null;
        }
    }
}