/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.ejb;

import javax.naming.NamingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-22
 * Time: 14:51:15
 * Version: 1.0
 */
public abstract class StatfulSessionEJBModel extends SessionEJBModel{
    private Object ejb;
    
    public StatfulSessionEJBModel(){
        this.createMethod = new CreateMethod();
    }

    public  Object invoke(Object[] params)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
        try{
            //statless session bean should be thread safe;
            if(ejb == null){
                ejb = connect(new Object[0]);
            }
            Class clz = ejb.getClass();
            Method method = clz.getMethod(this.ejbMethod,this.paramTypes);
            return method.invoke(ejb,params);
        }catch(NamingException e){
            e.printStackTrace();
            return null;
        }
    }
}
