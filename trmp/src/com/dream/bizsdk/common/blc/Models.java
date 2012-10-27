/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.blc;

import com.dream.bizsdk.common.databus.BizData;

import java.util.Hashtable;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Description:
 * A client request will be mapped to a series of Model,for example,
 * to response to request GetMyCustomer,two bl methods should be run:
 * one is getCurrentUserInfo, the other is getCustomersOfUser. Here
 * the name of the request GetMyCustomer will be the Models Name,the
 * two bl methods will be the two Models in the vector;
 * <p/>
 * Generally speaking, A Models object is a vector of Model objects which
 * are executed in the order they appear in this vector. And each bl method(Model)
 * may has an additional property Run Condition-a boolean expression,this
 * expression will be evaluated at runtime, if true, then this bl method
 * will do be run; but if false, it will not be executed, it is just ignored
 * and the next bl method will be processed.
 * <p/>
 * User: chuguanghua
 * Date: 2003-12-28
 * Time: 11:21:53
 */
public final class Models implements Serializable {
    protected int count = 0;
//    protected String name=null;
    protected ArrayList mv = new ArrayList();

    public Models() {

    }

    /**
     * construct a Models object with a name of request;
     *
     * @param name the request name
     */
    public Models(String name) {
//        this.name=name;
    }

    /**
     * Add a model object to this collection;
     *
     * @param m
     */
    public void add(Model m) {
        mv.add(m);
        count++;
    }

    public void add(int index, Model m) {
        mv.add(index, m);
        count++;
    }

    /**
     * add a model to this collection with a name and run condition;
     *
     * @param mName
     * @param mOn
     */
    public void add(String mName, String mOn) {
        add(new Model(mName, mOn));
    }

    /**
     * add model to this Model Collections object;
     *
     * @param mName the model name;
     * @param mOn   the trigger condition;
     * @param asyn  asyn call fllg;
     */
    public void add(String mName, String mOn, boolean asyn) {
        add(new Model(mName, mOn, asyn));
    }

    /**
     * Add a Model only with a name(no run condition) to this connection;
     *
     * @param mName
     */
    public void add(String mName) {
        add(new Model(mName));
    }

    /**
     * @return
     */
    public int getLength() {
        return count;
    }

    /**
     * Get a model in this collection directly according the index;
     *
     * @param index
     * @return
     */
    public Model itemAt(int index) {
        if (index < 0 || index > (count - 1)) {
            return null;
        } else {
            return (Model) mv.get(index);
        }
    }

    /**
     * Get a model in this collection that should be run according
     * to the specified BizData object;
     * A model should be run if:
     * 1) the Modle at the index have no Condition to run;
     * 2) the Model has one run condition and this condition is
     * evaluated to be true according to the specified BizData object.
     *
     * @param index
     * @param rd
     * @return
     */
    public Model itemAt(int index, BizData rd, BizData sd) {
        if (index < 0 || index > (count - 1)) {
            return null;
        } else {
            Model m = (Model) mv.get(index);
            if (m.needRun(rd, sd)) {
                return m;
            } else {
                return null;
            }
        }
    }

    /**
     * @param index
     * @param td
     * @return
     */
    public Model itemAt(int index, Hashtable td) {
        if (index < 0 || index > (count - 1)) {
            return null;
        } else {
            Model m = (Model) mv.get(index);
            if (m.needRun(td)) {
                return m;
            } else {
                return null;
            }
        }
    }

}