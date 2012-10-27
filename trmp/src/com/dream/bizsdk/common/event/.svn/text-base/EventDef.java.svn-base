/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.event;

import java.util.ArrayList;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-9-14
 * Time: 8:46:22
 */
public class EventDef {
    private String name;
    private ArrayList l = new ArrayList();
    private ArrayList a = new ArrayList();

    /**
     *
     */
    public static class EventListenerDef {
        private String className;

        public EventListenerDef() {

        }

        public EventListenerDef(String className) {
            this.className = className;
        }

        public String getClassName() {
            return this.className;
        }

        public void setClassName(String className) {
            this.className = className;
        }
    }

    /**
     * 
     */
    public static class EventActionDef {
        private String className;
        private String methodName;

        public EventActionDef() {

        }

        public EventActionDef(String className, String methodName) {
            this.className = className;
            this.methodName = methodName;
        }

        public String getClassName() {
            return this.className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }
    }

    public EventDef() {

    }

    public EventDef(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add(EventListenerDef eld) {
        l.add(eld);
    }

    public void add(EventActionDef ead) {
        a.add(ead);
    }
}
