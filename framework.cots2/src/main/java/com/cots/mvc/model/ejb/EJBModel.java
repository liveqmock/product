/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.ejb;

import com.cots.mvc.model.Model;
import com.cots.mvc.model.parameter.ParameterRef;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;
import java.util.Properties;
import java.lang.reflect.InvocationTargetException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-22
 * Time: 14:50:17
 * Version: 1.0
 */
public abstract class EJBModel extends Model{
    protected String jndi;
    protected String providerURL;
    protected String initialFactory;
    protected String ejbMethod;
    protected String home;
    protected String remote;
    protected CreateMethod createMethod;


    /**
     * get the jndi of the home interface of the ejb home interface;
     *
     * @return
     */
    public String getJndi() {
        return jndi;
    }

    /**
     * set the jdni of the home interface of the ejb home interface;
     *
     * @param jndi
     */
    public void setJndi(String jndi) {
        this.jndi = jndi;
    }


    public String getProviderURL() {
        return providerURL;
    }

    public void setProviderURL(String providerURL) {
        this.providerURL = providerURL;
    }

    public String getInitialFactory() {
        return initialFactory;
    }

    public void setInitialFactory(String initialFactory) {
        this.initialFactory = initialFactory;
    }

    public String getEjbMethod() {
        return ejbMethod;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    public void setEjbMethod(String ejbMethod) {
        this.ejbMethod = ejbMethod;
    }

    public CreateMethod getCreateMethod() {
        return createMethod;
    }

    public void setCreateMethod(CreateMethod createMethod) {
        this.createMethod = createMethod;
    }

    public Object connect(Object[] params)
            throws NamingException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
        Object home = EJBHomeCache.get(jndi);
        if(home==null){
            synchronized(EJBHomeCache.class){
                home = EJBHomeCache.get(jndi);
                if(home==null){
                    Properties p = new Properties();
                    if(providerURL != null ){
                        p.setProperty(Context.PROVIDER_URL,providerURL);
                    }

                    if(initialFactory!=null){
                        p.setProperty(Context.INITIAL_CONTEXT_FACTORY,initialFactory);
                    }
                    InitialContext ic = new InitialContext(p);
                    home = ic.lookup(jndi);
                }
            }
        }
        return createMethod.create(home,params);
    }


    public String[] getParameterRefNames(){
        int size = this.parameters_ref.size();
        String[] paramNames = new String[size+this.createMethod.getParamNames().length];

        for(int i=0;i<size;i++){
            paramNames[i] = ((ParameterRef)this.parameters_ref.get(i)).getName();
        }

        System.arraycopy(this.createMethod.getParamNames(),0,paramNames,size,
                this.createMethod.getParamNames().length);

        return paramNames;
    }
}
