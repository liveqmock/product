/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.blc;

import com.dream.bizsdk.common.databus.BizData;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-4-25
 * Time: 12:10:58
 */
public class FieldValidations implements Serializable {
    //private String name;
    private ArrayList vs = new ArrayList();
    private int count;

    public FieldValidations() {

    }

    public FieldValidations(String name) {
//        this.name = name;
    }
/*    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
*/
    public void add(FieldValidation fv) {
        vs.add(fv);
        count++;
    }

    /**
     * check if this validatiaon is valid;
     *
     * @param data
     */
    public void validate(BizData data) throws ValidateException {
        int valid = FieldValidation.FIELD_IS_VALID;
        FieldValidation fv = null;
        for (int i = 0; i < count; i++) {
            fv = (FieldValidation) vs.get(i);
            if ((valid = fv.isValid(data)) < 0) {
                break;
            }
        }
        if (valid < 0) {
            switch (valid) {
                case FieldValidation.FIELD_CANT_NULL:
                    throw new ValidateException("Field can't be null:" + fv.getName());
                case FieldValidation.FIELD_TYPE_ERROR:
                    throw new ValidateException("Field's type is wrong:" + fv.getName() + "; required " + fv.getType());
                case FieldValidation.FIELD_LT_MINVALUE:
                    throw new ValidateException("Field can't be less than min Value:" + fv.getName() + "; required min value " + fv.getMinValue());
                case FieldValidation.FIELD_GT_MAXVALUE:
                    throw new ValidateException("Field can't be great than max Value:" + fv.getName() + "; required max value " + fv.getMaxValue());
                default:
            }
        }
    }
}