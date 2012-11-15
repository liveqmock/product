/**
 *all rights reserved,@copyright 2003
 */
package test;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-7-9
 * Time: 10:46:29
 * Version: 1.0
 */
public class MyValidater {
    public static String validate(String name,int year){
        if(year>18){
            if(name==null || !name.startsWith("A")){
                return "如果年龄大于18，则名称必须以A开头！";
            }
        }else{
            if(name==null || !name.startsWith("B")){
                return "如果年龄小于18，则名称必须以B开头！";
            }
        }
        return null;
    }
}
