package com.cots.socket;

import com.cots.mvc.controller.ActionMapping;
import com.cots.bean.BeanFactory;
import com.cots.blc.BLContext;
import com.cots.exp.Builder;

/**
 * Cots framework
 * <p/>
 * User: chugh
 * Date: 2005-4-9
 * Time: 9:37:02
 * Version: 1.0
 */
public class SocketServlet {
    private ActionMapping actionMapping;

    private BeanFactory beanFactory;

    private BLContext context;

    private Builder expBuilder;

    public ActionMapping getActionMapping() {
        return actionMapping;
    }

    public void setActionMapping(ActionMapping actionMapping) {
        this.actionMapping = actionMapping;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public BLContext getContext() {
        return context;
    }

    public void setContext(BLContext context) {
        this.context = context;
    }

    public Builder getExpBuilder() {
        return expBuilder;
    }

    public void setExpBuilder(Builder expBuilder) {
        this.expBuilder = expBuilder;
    }

    public void process(ServerRequest request,ServerResponse response){

    }
}
