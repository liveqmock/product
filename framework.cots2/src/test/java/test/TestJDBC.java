/**
 *all rights reserved,@copyright 2003
 */
package test;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-6-22
 * Time: 10:07:03
 * Version: 1.0
 */
public class TestJDBC {
    public static void main(String[] argc) throws Exception{
        //String driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
        //String url ="jdbc:microsoft:sqlserver://127.0.0.1:1433;DatabaseName=cots;selectmethod=cursor";
        String user="install";
        String password="install";

        String url ="jdbc:db2://192.168.2.224:6789/eos5";
        String driver ="COM.ibm.db2.jdbc.net.DB2Driver";

        Class.forName(driver);
        Connection conn =DriverManager.getConnection(url,user,password);

        PreparedStatement stmt = conn.prepareStatement("select birthday from Person");
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            System.out.println(rs.getDate(1));
        }
//        long t1 = System.currentTimeMillis();
//        for(int i=0;i<10000;i++){
//            stmt.setString(1,"displayName"+i);
//            stmt.setInt(2,i);
//            stmt.executeUpdate();
//        }
//        //stmt.executeBatch();
//        long t2 = System.currentTimeMillis();
//        System.out.println(t2-t1);
    }
}