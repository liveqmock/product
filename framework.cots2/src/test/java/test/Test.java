/**
 *all rights reserved,@copyright 2003
 */
package test;

import com.cots.util.DirClassLoader;


/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-4-19
 * Time: 17:27:20
 * Version: 1.0
 */
public class Test {
    public void m(int x,int y){
        System.out.println("x="+x+";y="+y);
    }
    public static void main(String[] argc) throws Exception{
//        Test t = new Test();
//        int a = 10;
//        t.m(++a,++a);
        int a=5;
        a+=++a;
        System.out.println(15&25);
    }
}