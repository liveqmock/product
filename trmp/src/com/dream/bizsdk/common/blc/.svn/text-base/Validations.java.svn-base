/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.blc;

import java.util.Hashtable;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-3-2
 * Time: 11:16:42
 */
public final class Validations implements Serializable {
//    private String name = "";
    private int count = 0;
    private ArrayList v = new ArrayList();
    private Validation[] va = null;

    public Validations() {

    }

    public Validations(String name) {
//        this.name=name;
    }

    public void add(Validation val) {
        v.add(val);
        va = (Validation[]) v.toArray(new Validation[0]);
        count++;
    }
    
/*    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }
*/

    public void validate(Hashtable dds) throws ValidateException {
        for (int i = 0; i < count; i++) {
            va[i].exec(dds);
        }
    }
}
