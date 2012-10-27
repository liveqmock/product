/**
 *all rights reserved,@copyright 2003
 */

package com.dream.bizsdk.common.database.sql;

/**
 * SQLFunction.java. Definition of a SQL Function such as String function,
 * Math function, or date function and so on.
 *
 * @author chuguanghua
 */
public class SQLFunction {
    protected String name = null;
    protected int type = 0;

    /**
     * Creates a new instance of SQLFunction
     */
    public SQLFunction(String funcName, int type) {
        this.name = funcName;
        this.type = type;
    }

    /**
     * get the function's name;
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * here the function type means the type of the function's return value.
     *
     * @return
     */
    public int getType() {
        return this.type;
    }
}
