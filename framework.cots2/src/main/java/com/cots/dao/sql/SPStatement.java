/**
 *All rights reserved by cots co. ltd.
 */
package com.cots.dao.sql;

import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.util.HashMap;
import java.util.Map;

/**
 * User: chugh
 * Date: 2005-9-24
 * Time: 9:50:30
 */
public class SPStatement extends BaseStatement{
    public PreparedStatement prepare(Connection conn) throws SQLException{
        return conn.prepareStatement(sql);
    }

    public PreparedStatement prepare(Connection conn,int resultSetType,int resultConcurrency ) throws SQLException{
        return conn.prepareStatement(sql,resultSetType,resultConcurrency);
    }

    public void setParamValues(PreparedStatement stmt,Map values) throws SQLException{
        Logger log = Logger.getLogger(SPStatement.class);
        if(log.isDebugEnabled()){
            log.debug("SP: "+sql);
        }

        CallableStatement cstat =  (CallableStatement)stmt;
        StatementParam[] params = this.getParameters();
        for(int i=0;i<params.length;i++){
            String paramName = params[i].getName();
            int passing = params[i].getPassing();
            Object value = values.get(paramName);

            if((passing & StatementParam.OUT) !=0 ){
                cstat.registerOutParameter(i,params[i].getJDBCType());
            }
            if((passing & StatementParam.IN) !=0 ){
                if(value!=null){
                    cstat.setObject(i,value,params[i].getJDBCType());
                }else{
                    cstat.setNull(i,params[i].getJDBCType());
                }
            }
            if(log.isDebugEnabled()){
                String passingStr;
                if(passing == 1){
                    passingStr = "in";
                }else if(passing == 2){
                    passingStr = "out";
                }else{
                    passingStr = "inout";
                }
                log.debug("parameter "+i+": "+passingStr+"\t"+value);
            }
        }
    }

    public void getParamValues(PreparedStatement stmt,HashMap values) throws SQLException{
        CallableStatement cstat =  (CallableStatement)stmt;
        StatementParam[] params = this.getParameters();
        for(int i=0;i<params.length;i++){
            String paramName = params[i].getName();
            int passing = params[i].getPassing();
            if((passing & StatementParam.OUT) !=0 ){
                values.put(paramName,cstat.getObject(paramName));
            }
        }
    }

}
