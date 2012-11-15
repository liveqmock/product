/**
 *all rights reserved,@copyright 2003
 */
package com.cots.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Driver;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-25
 * Time: 9:27:04
 * Version: 1.0
 */
public class CotsDataSource implements DataSource{

    private String user;
    private String password;
    private String url;
    private String driver;
    private int initialSize;
    private int maxSize;
    private int size;
    private int loginTimeout;

    private ArrayList connections = new ArrayList(100);

    public  Connection getConnection() throws SQLException {
        return getConnection(this.user,this.password);
    }

    public synchronized Connection getConnection(String user, String password) throws SQLException {
        int available;
        if((available = connections.size())<1){
            if(size<maxSize){
                return new PooledConnection(this,createConnection(user,password));
            }else{
                throw new SQLException("max connection arrived, no connection available currently");
            }
        }else{
            return (Connection)connections.remove(available -1);
        }
    }

    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    public void setLogWriter(PrintWriter printWriter) throws SQLException {
        
    }

    public void setLoginTimeout(int i) throws SQLException {
        loginTimeout = i;
    }

    public int getLoginTimeout() throws SQLException {
        return loginTimeout;
    }

    public synchronized void returnConnection(PooledConnection conn){
        connections.add(conn);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getSize() {
        return size;
    }

    private Connection createConnection(String user,String password) throws SQLException{
        Connection conn;
        try{
            Driver dvr = (Driver)Class.forName(driver).newInstance();
            Properties p = new Properties();
            p.setProperty("user",user);
            p.setProperty("password",password);
            p.setProperty("loginTimeout",String.valueOf(loginTimeout));
            conn = dvr.connect(url,p);
            size++;
            return conn;
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }catch (InstantiationException e) {
            e.printStackTrace();
        }catch(ClassCastException e){
            e.printStackTrace();
        }
        throw new SQLException("failed to create Connection");
    }

    public void decSize(){
        size--;
        if(size<0){
            size = 0;
        }
    }

    public synchronized void addConnection(PooledConnection conn){
        connections.add(conn);
        size++;
    }

    public static void main(String[] argc) throws SQLException{
        String user="test2";
        String password="test2";
        String url="oracle:thin:@192.168.1.254:1521:eos";
        String driver="oracle.jdbc.driver.OracleDrive";

        CotsDataSource ds = new CotsDataSource();
        ds.setUser(user);
        ds.setPassword(password);
        ds.setUser(url);
        ds.setDriver(driver);
        ds.getConnection();
    }
}