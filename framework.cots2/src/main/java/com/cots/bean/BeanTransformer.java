package com.cots.bean;

import java.io.OutputStream;

/**
 * Cots framework
 * <p/>
 * User: chugh
 * Date: 2005-4-9
 * Time: 9:12:41
 * Version: 1.0
 */
public class BeanTransformer {
    private BeanFactory beanFactory;

    public BeanTransformer() {
    }

    public BeanTransformer(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public String toString(Object bean){
        return null;
    }

    public int toOutputStream(Object bean, OutputStream os){
        return 0;
    }
}