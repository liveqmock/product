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
                return "����������18�������Ʊ�����A��ͷ��";
            }
        }else{
            if(name==null || !name.startsWith("B")){
                return "�������С��18�������Ʊ�����B��ͷ��";
            }
        }
        return null;
    }
}
