/**
 *All rights reserved by cots co. ltd.
 */
package com.cots.dao.sql;

import org.apache.log4j.Logger;

import java.util.Map;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * User: chugh
 * Date: 2005-9-24
 * Time: 9:50:21
 */
public class SQLStatement extends BaseStatement{
    /**
     *
     * @param stmt
     * @param values
     * @throws SQLException
     */
    public void setParamValues(PreparedStatement stmt,Map values) throws SQLException {
        Logger log = Logger.getLogger(SQLStatement.class);
        if(log.isDebugEnabled()){
            log.debug("SQL: "+sql);
        }

        StatementParam[] params = this.getParameters();
        for(int i=0;i<params.length;i++){
            String paramName = params[i].getName();
            Object value = values.get(paramName);
            if(value == null){
                stmt.setNull(i+1,params[i].getJDBCType());

            }else{
                stmt.setObject(i+1,value);
                if(log.isDebugEnabled()){
                    log.debug("parameter "+(i+1)+":"+value);
                }
            }
        }
    }
}
