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
public class StatefulSessionEJBModel extends SessionEJBModel{
    private Object ejb;
    
    public StatefulSessionEJBModel(){

    }

    /**
     * set the create method for this statufl session bean;
     * @param cm
     */
    public void setCreateMethod(CreateMethod cm){
        this.createMethod = cm;
    }

    public  Object invoke(Object[] params)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{

        Object[] createParams =  new Object[createMethod.getParamNames().length];
        Object[] ejbParams = new Object[this.parameters_ref.size()];
        if(createMethod.getParamNames().length>0){
            System.arraycopy(params,0,ejbParams,0,ejbParams.length);
            System.arraycopy(params,this.parameters_ref.size(),createParams,0,createMethod.getParamNames().length);
        }else{
            ejbParams = params;
        }

        try{
            //statful session bean should not be cached
            //if(ejb == null){
            ejb = connect(createParams);
            //}
            Class clz = ejb.getClass();
            Method method = clz.getMethod(this.ejbMethod,this.paramTypes);
            return method.invoke(ejb,ejbParams);
        }catch(NamingException e){
            e.printStackTrace();
            return null;
        }
    }
}
