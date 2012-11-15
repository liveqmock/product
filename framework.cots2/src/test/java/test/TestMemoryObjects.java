/**
 *All rights reserved by cots co. ltd.
 */
package test;

import com.cots.junit.TestBase;
import com.cots.dao.DAO;
import com.cots.util.MemoryObjects;
import com.cots.mvc.controller.access.User;


import java.sql.SQLException;
import java.lang.reflect.InvocationTargetException;

/**
 * User: chugh
 * Date: 2005-10-10
 * Time: 19:51:16
 */
public class TestMemoryObjects extends TestBase{
    public TestMemoryObjects(){
        configRoot = "d:\\tomcat5\\webapps\\cots\\WEB-INF\\config";
    }

    public void testLoad() throws SQLException, InterruptedException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        DAO dao =this.blContext.getDAO();
        MemoryObjects mo = MemoryObjects.getInstance(User.class,dao);
//        User user = new User();
//        user.setId("chu3");
//        user.setName("guanghua");
//        mo.add(user);
        System.out.println("Sleeping...");
        Thread.sleep(5000);

        mo.load("chu");

        User user = (User)mo.get("chu");
        System.out.println("This is "+user.getId());

        User[] users = (User[])mo.getArray();
        for(int i=0;i<users.length;i++){
            System.out.println(users[i].getId()+"  "+users[i].getName());
        }
    }
}
