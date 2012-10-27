/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.blc;

import com.dream.bizsdk.common.databus.BizData;

import java.util.Date;
import java.io.Serializable;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-4-25
 * Time: 10:56:40
 */
public class FieldValidation implements Serializable {
    public final static int FIELD_IS_VALID = 1;
    public final static int FIELD_CANT_NULL = -701;
    public final static int FIELD_TYPE_ERROR = -702;
    public final static int FIELD_LT_MINVALUE = -703;
    public final static int FIELD_GT_MAXVALUE = -704;
    public final static int FIELD_LENGTH_TOO_SMALL = -705;
    public final static int FIELD_LENGTH_TOO_BIG = -706;


    private String type;
    private String name;
    private String minValue;
    private String maxValue;
    private boolean required;
    private int minLength;
    private int maxLength = Integer.MIN_VALUE;
    private Object minObj;
    private Object maxObj;


    public FieldValidation() {

    }

    public FieldValidation(String name, String type, boolean required,
                           String minValue, String maxValue) {
        this.name = name;
        this.type = type;
        this.required = required;
        setMinValue(minValue);
        setMaxValue(maxValue);
    }

    public FieldValidation(String name, String type, boolean required,
                           String minValue, String maxValue, int minLength, int maxLength) {
        this.name = name;
        this.type = type;
        this.required = required;
        setMinValue(minValue);
        setMaxValue(maxValue);
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getMinValue() {
        return this.minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
        if (minValue != null) {
            if (type.compareToIgnoreCase("int") == 0) {
                try {
                    minObj = Integer.valueOf(minValue);
                } catch (Exception e) {
                    minObj = null;
                }
            } else if (type.compareToIgnoreCase("float") == 0) {
                try {
                    minObj = Double.valueOf(minValue);
                } catch (Exception e) {
                    minObj = null;
                }
            } else if (type.compareToIgnoreCase("date") == 0) {
                try {
                    minObj = BizData.sdfDate.parse(minValue);
                } catch (Exception e) {
                    minObj = null;
                }
            } else if (type.compareToIgnoreCase("time") == 0) {
                try {
                    minObj = BizData.sdfTime.parse(minValue);
                } catch (Exception e) {
                    minObj = null;
                }
            }
        } else {
            minObj = null;
        }
    }

    public String getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
        if (maxValue != null) {
            if (type.compareToIgnoreCase("int") == 0) {
                try {
                    maxObj = Integer.valueOf(maxValue);
                } catch (Exception e) {
                    maxObj = null;
                }
            } else if (type.compareToIgnoreCase("float") == 0) {
                try {
                    maxObj = Double.valueOf(maxValue);
                } catch (Exception e) {
                    maxObj = null;
                }
            } else if (type.compareToIgnoreCase("date") == 0) {
                try {
                    maxObj = BizData.sdfDate.parse(maxValue);
                } catch (Exception e) {
                    maxObj = null;
                }
            } else if (type.compareToIgnoreCase("time") == 0) {
                try {
                    maxObj = BizData.sdfTime.parse(maxValue);
                } catch (Exception e) {
                    maxObj = null;
                }
            }
        } else {
            maxObj = null;
        }
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLegnth(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLegnth(int maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * check if this field is valid in the blcontext object;
     *
     * @param data
     * @return
     */
    public int isValid(BizData data) {
        Object o = data.getByPath(this.name);
        if (o == null) {
            if (this.required) {
                return FieldValidation.FIELD_CANT_NULL;
            }
        } else {
            if (type.compareToIgnoreCase("string") == 0) {
                if (!(o instanceof String || o instanceof Character)) {
                    return FieldValidation.FIELD_TYPE_ERROR;
                } else {
                    String v = o.toString();
                    if (minValue != null) {
                        if (v.compareTo(minValue) < 0) {
                            return FieldValidation.FIELD_LT_MINVALUE;
                        }
                    }
                    if (maxValue != null) {
                        if (v.compareTo(maxValue) > 0) {
                            return FieldValidation.FIELD_GT_MAXVALUE;
                        }
                    }
                    if (v.length() < minLength) {
                        return FieldValidation.FIELD_LENGTH_TOO_SMALL;
                    }
                    if (v.length() > maxLength) {
                        return FieldValidation.FIELD_LENGTH_TOO_BIG;
                    }
                }
            } else if (type.compareToIgnoreCase("int") == 0) {
                if (!(o instanceof Integer || o instanceof Short
                        || o instanceof Long || o instanceof Byte
                        || o instanceof String)) {
                    return FieldValidation.FIELD_TYPE_ERROR;
                } else {
                    int v = 0;
                    if (o instanceof String) {
                        try {
                            v = Integer.valueOf((String) o).intValue();
                        } catch (Exception e) {
                            return FieldValidation.FIELD_TYPE_ERROR;
                        }
                    } else {
                        v = ((Number) o).intValue();
                    }
                    if (minObj != null && v < ((Integer) minObj).intValue()) {
                        return FieldValidation.FIELD_LT_MINVALUE;
                    }
                    if (maxObj != null && v > ((Integer) maxObj).intValue()) {
                        return FieldValidation.FIELD_GT_MAXVALUE;
                    }
                }
            } else if (type.compareToIgnoreCase("float") == 0) {
                if (!(o instanceof Integer || o instanceof Short
                        || o instanceof Long || o instanceof Byte || o instanceof String)) {
                    return FieldValidation.FIELD_TYPE_ERROR;
                } else {
                    double v = 0;
                    if (o instanceof String) {
                        try {
                            v = Double.valueOf((String) o).doubleValue();
                        } catch (Exception e) {
                            return FieldValidation.FIELD_TYPE_ERROR;
                        }
                    } else {
                        v = ((Number) o).doubleValue();
                    }
                    if (minObj != null && v < ((Double) minObj).intValue()) {
                        return FieldValidation.FIELD_LT_MINVALUE;
                    }
                    if (maxObj != null && v > ((Double) maxObj).intValue()) {
                        return FieldValidation.FIELD_GT_MAXVALUE;
                    }
                }
            } else if (type.compareToIgnoreCase("date") == 0) {
                if (!(o instanceof java.util.Date || o instanceof String)) {
                    return FieldValidation.FIELD_TYPE_ERROR;
                } else {
                    Date v = null;
                    if (o instanceof String) {
                        try {
                            v = BizData.sdfDate.parse((String) o);
                        } catch (Exception e) {
                            return FieldValidation.FIELD_TYPE_ERROR;
                        }
                    } else {
                        v = (Date) o;
                    }
                    if (minObj != null && v.getTime() < ((Date) minObj).getTime()) {
                        return FieldValidation.FIELD_LT_MINVALUE;
                    }
                    if (maxObj != null && v.getTime() > ((Date) maxObj).getTime()) {
                        return FieldValidation.FIELD_GT_MAXVALUE;
                    }
                }
            } else if (type.compareToIgnoreCase("time") == 0) {
                if (!(o instanceof java.util.Date || o instanceof String)) {
                    return FieldValidation.FIELD_TYPE_ERROR;
                } else {
                    Date v = null;
                    if (o instanceof String) {
                        try {
                            v = BizData.sdfTime.parse((String) o);
                        } catch (Exception e) {
                            return FieldValidation.FIELD_TYPE_ERROR;
                        }
                    } else {
                        v = (Date) o;
                    }
                    if (minObj != null && v.getTime() < ((Date) minObj).getTime()) {
                        return FieldValidation.FIELD_LT_MINVALUE;
                    }
                    if (maxObj != null && v.getTime() > ((Date) maxObj).getTime()) {
                        return FieldValidation.FIELD_GT_MAXVALUE;
                    }
                }
            }
        }
        return FieldValidation.FIELD_IS_VALID;
    }
}
