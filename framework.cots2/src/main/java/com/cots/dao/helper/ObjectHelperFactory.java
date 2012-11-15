/**
 *all rights reserved,@copyright 2003
 */
package com.cots.dao.helper;

import com.cots.bean.BeanFactory;
import com.cots.bean.Bean;
import com.cots.util.DirClassLoader;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-3
 * Time: 9:37:39
 */
public class ObjectHelperFactory {
    protected ObjectHelperBuilder builder;
    protected BeanFactory beanFactory;
    protected HashMap helpers;
    protected Logger log;

    /**
     *
     * @param loader
     * @param beanFactory
     * @param baseDir
     * @param classPath
     */
    protected ObjectHelperFactory(DirClassLoader loader,BeanFactory beanFactory, String baseDir,
                                  String classPath, String dbType) {
        this.beanFactory = beanFactory;
        builder = new ObjectHelperBuilder(baseDir, classPath, dbType);
        builder.setLoader(loader);
        helpers = new HashMap();
        log = Logger.getLogger(ObjectHelperFactory.class);
    }

    /**
     *
     * @param obj
     * @return
     */
    public ObjectHelper getObjectHelper(Object obj) {
        return getObjectHelper(obj.getClass());
    }

    /**
     *
     * @param clz
     * @return
     */
    public ObjectHelper getObjectHelper(Class clz) {
        Object helper = helpers.get(clz);
        if (helper == null) { //not generated yet.
            synchronized (helpers) {
                helper = helpers.get(clz);
                if (helper == null) {
                    Bean bean = beanFactory.getByClass(clz);
                    if (bean == null) {
                        if (log.isEnabledFor(Priority.ERROR)) {
                            log.error("can't build ObjectHelper for a NOT-cots-managed object");
                        }
                        return null;
                    } else {
                        helper = builder.build(clz, bean);
                        if (helper != null) {
                            ((ObjectHelper)helper).setOwnerFactory(this);
                            helpers.put(clz, helper);
                        }
                    }
                }
            }
        }
        return (ObjectHelper) helper;
    }

    public ResultSetHelper getResultSetHelper(Class clz) {
        Object helper = helpers.get(clz);
        if (helper == null) { //not generated yet.
            synchronized (helpers) {
                helper = helpers.get(clz);
                if (helper == null) {
                    Bean bean = beanFactory.getByClass(clz);
                    if (bean == null) {
                        if (log.isEnabledFor(Priority.ERROR)) {
                            log.error("can't build ObjectHelper for a NOT-cots-managed object");
                        }
                        return null;
                    } else {
                        helper = builder.build(clz, bean);
                        if (helper != null) {
                            helpers.put(clz, helper);
                        }
                    }
                }
            }
        }
        return (ResultSetHelper) helper;
    }

    public void setLoader(DirClassLoader loader){
        builder.setLoader(loader);
    }

    public void reset(){
        helpers.clear();
    }

    /**
     *
     * @return
     */
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    /**
     * get an Factory instance.
     *
     * @param loader;
     * @param beanFactory
     * @param baseDir
     * @param classPath
     * @return
     */
    public static ObjectHelperFactory getInstance(DirClassLoader loader,BeanFactory beanFactory, String baseDir,
                                                  String classPath, String dbType) {
        return new ObjectHelperFactory(loader,beanFactory, baseDir, classPath, dbType);
    }
}