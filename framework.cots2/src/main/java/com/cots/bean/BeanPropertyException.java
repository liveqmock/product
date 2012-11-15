package com.cots.bean;

import java.util.List;
import java.util.ArrayList;

/**
 * Cots framework
 * <p/>
 * User: chugh
 * Date: 2005-4-8
 * Time: 12:40:56
 * Version: 1.0
 */
public class BeanPropertyException extends Exception{
    private List errors = new ArrayList();

    public BeanPropertyException() {
    }

    public void addError(String msg){
        errors.add(msg);
    }

    public List getErrors(){
        return errors;
    }
}
