/**
 *All rights reserved by cots co. ltd.
 */
package com.cots.dao;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;

/**
 * User: chugh
 * Date: 2005-6-4
 * Time: 20:47:07
 */
public class RSUtil {
    /**
     * close a result set and its associated BaseStatement and Connection object.
     *
     * @param rs the ResultSet to be closed.
     */
    public static void close(ResultSet rs){
        try{
            Statement stmt = rs.getStatement();
            Connection conn = stmt.getConnection();
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void close(ResultSet rs,DataSource ds){
        try{
            Statement stmt = rs.getStatement();
            Connection conn = stmt.getConnection();
            rs.close();
            stmt.close();
            if(ds instanceof CotsDataSource){
                PooledConnection pc = new PooledConnection((CotsDataSource)ds,conn);
                ((CotsDataSource)ds).addConnection(pc);
            }else{
                conn.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
