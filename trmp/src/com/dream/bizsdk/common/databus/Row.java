package com.dream.bizsdk.common.databus;

/**
 *
 * User: divine
 * Date: 2004-10-3
 * Time: 21:27:23
 */
public class Row extends BaseData{
    protected Object id;

    public Row(){

    }

    public Row(Object id){
        this.id = id;
    }

    public Object getId(){
        return id;
    }

    public void setId(Object id){
        this.id = id;
    }
}
