package com.cots.mvc.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Cots framework
 * <p/>
 * User: chugh
 * Date: 2005-4-8
 * Time: 12:16:45
 * Version: 1.0
 */
public class ActionParametersException extends Exception{
    private ArrayList errors = new ArrayList();
    public ActionParametersException(){

    }

    public void addError(String msg){
        errors.add(msg);
    }

    public void addErrors(List errors){
        if(errors!=null){
            int count = errors.size();
            for(int i=0;i<count;i++){
                this.errors.add(errors.get(i));
            }
        }
    }

    public List getErrors(){
        return errors;
    }

    public String getMessage(){
        StringBuffer sb = new StringBuffer(512);
        int size = errors.size();
        for(int i=0;i<size;i++){
            sb.append(errors.get(i));
            if(i<size-1){
                sb.append('\n');
            }
        }
        return new String(sb);
    }
}
