package com.cots.bean;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import java.util.*;
import java.text.ParseException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.sql.Time;

import com.cots.util.Base64;


/**
 * Description:
 *
 * User: chugh
 * Date: 2004-10-27
 * Time: 19:27:57
 * Version: 1.0
 */
public final class PrimitiveType {

    public final static String BOOLEAN="boolean";
    public final static String BYTE="byte";
    public final static String BYTE_ARRAY="byte[]";
    public final static String CHAR="char";
    public final static String CHAR_ARRAY="char[]";
    public final static String INT="int";
    public final static String INTEGER="Integer";
    public final static String LONG="long";
    public final static String SHORT="short";
    public final static String FLOAT="float";
    public final static String DOUBLE="double";
    public final static String STRING="String";
    public final static String DATE="java.util.Date";

    private static HashMap ARRAY_CLASSES = new HashMap();
    private static HashMap WRAPPER_CLASSES = new HashMap();

    protected static HashMap primitiveType;
    public static String DATE_FORMAT ="yyyy-MM-dd";
    public static String TIME_FORMAT ="HH:mm:ss";
    public static String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static Logger log = Logger.getLogger(PrimitiveType.class);

    static{
        primitiveType = new HashMap();

        /**cots defined primitive types*/
        primitiveType.put("boolean",boolean.class);
        primitiveType.put("byte",byte.class);
        primitiveType.put("byte[]",byte[].class);
        primitiveType.put("char",char.class);
        primitiveType.put("char[]",char[].class);
        primitiveType.put("short",short.class);
        primitiveType.put("int",int.class);
        primitiveType.put("long",long.class);
        primitiveType.put("float",float.class);
        primitiveType.put("double",double.class);
        primitiveType.put("Boolean",Boolean.class);
        primitiveType.put("Byte",Byte.class);
        primitiveType.put("Character",Character.class);
        primitiveType.put("Short",Short.class);
        primitiveType.put("Integer",Integer.class);
        primitiveType.put("Long",Long.class);
        primitiveType.put("Float",Float.class);
        primitiveType.put("Double",Double.class);
        primitiveType.put("String",String.class);
        primitiveType.put("java.util.Date",java.util.Date.class);
        primitiveType.put("java.sql.Time",java.sql.Time.class);
        primitiveType.put("java.sql.Date",java.sql.Date.class);
        primitiveType.put("java.sql.Timestamp",java.sql.Timestamp.class);

        ARRAY_CLASSES.put("byte",byte[].class);
        ARRAY_CLASSES.put("char",char[].class);
        ARRAY_CLASSES.put("short",short[].class);
        ARRAY_CLASSES.put("int",int[].class);
        ARRAY_CLASSES.put("long",long[].class);
        ARRAY_CLASSES.put("float",float[].class);
        ARRAY_CLASSES.put("double",double[].class);
        ARRAY_CLASSES.put("Byte",Byte[].class);
        ARRAY_CLASSES.put("Character",Character[].class);
        ARRAY_CLASSES.put("Short",Short[].class);
        ARRAY_CLASSES.put("Integer",Integer[].class);
        ARRAY_CLASSES.put("Long",Long[].class);
        ARRAY_CLASSES.put("Float",Float[].class);
        ARRAY_CLASSES.put("Double",Double[].class);
        ARRAY_CLASSES.put("String",String[].class);
        ARRAY_CLASSES.put("java.util.Date",java.util.Date[].class);
        ARRAY_CLASSES.put("java.sql.Time",java.sql.Time[].class);
        ARRAY_CLASSES.put("java.sql.Date",java.sql.Date[].class);
        ARRAY_CLASSES.put("java.sql.Timestamp",java.sql.Timestamp[].class);

        WRAPPER_CLASSES.put("boolean","Boolean");
        WRAPPER_CLASSES.put("int","Integer");
        WRAPPER_CLASSES.put("byte","Byte");
        WRAPPER_CLASSES.put("short","Short");
        WRAPPER_CLASSES.put("long","Long");
        WRAPPER_CLASSES.put("float","Float");
        WRAPPER_CLASSES.put("double","Double");
        WRAPPER_CLASSES.put("char","Character");

    }

    /**
     *
     * @param type
     * @param value
     * @return
     * @throws ParseException
     */
    public static Object create(String type,String value)
            throws ParseException{
        return create(type,value,null);
    }

    public static Boolean createBoolean(String value){
        if("true".equals(value)){
            return new Boolean(true);
        }else{
            return new Boolean(false);
        }
    }

    public static Integer createInt(String value,String format) throws ParseException {
        if(format!=null && format.length()>0){
            DecimalFormat decimalFormat = new DecimalFormat(format);
            Number number = decimalFormat.parse(value);
            return new Integer(number.intValue());
        }else{
            return Integer.valueOf(value);
        }
    }

    public static Short createShort(String value,String format) throws ParseException {
        if(format!=null){
            DecimalFormat decimalFormat = new DecimalFormat(format);
            Number number = decimalFormat.parse(value);
            return new Short(number.shortValue());
        }else{
            return Short.valueOf(value);
        }

    }

    public static Long createLong(String value,String format)
            throws ParseException {
        if(format!=null && format.length()>0){
            DecimalFormat decimalFormat = new DecimalFormat(format);
            Number number = decimalFormat.parse(value);
            return new Long(number.longValue());
        }else{
            return Long.valueOf(value);
        }
    }

    public static Float createFloat(String value,String format) throws ParseException {
        if(format!=null){
            DecimalFormat decimalFormat = new DecimalFormat(format);
            Number number = decimalFormat.parse(value);
            return new Float(number.floatValue());
        }else{
            return Float.valueOf(value);
        }
    }

    public static Double createDouble(String value,String format) throws ParseException {
        if(format!=null && format.length()>0){
            DecimalFormat decimalFormat = new DecimalFormat(format);
            Number number = decimalFormat.parse(value);
            return new Double(number.doubleValue());
        }else{
            return Double.valueOf(value);
        }
    }

    public static Byte createByte(String value,String format) throws ParseException {
        if(format!=null){
            DecimalFormat decimalFormat = new DecimalFormat(format);
            Number number = decimalFormat.parse(value);
            return new Byte(number.byteValue());
        }else{
            return Byte.valueOf(value);
        }
    }

    public static Date createDate(String value,String format) throws ParseException {
        SimpleDateFormat dateFormat;
        if(format!=null && format.length()>0){
            dateFormat = new SimpleDateFormat(format);
        }else{
            dateFormat = new SimpleDateFormat(DATE_FORMAT);
        }
        return dateFormat.parse(value);
    }

    public static Timestamp createTimestamp(String value,String format) throws ParseException {
        SimpleDateFormat dateFormat;
        if(format!=null){
            dateFormat = new SimpleDateFormat(format);
        }else{
            dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
        }
        return new java.sql.Timestamp(dateFormat.parse(value).getTime());
    }

    public static Time createTime(String value,String format) throws ParseException {
        SimpleDateFormat dateFormat;
        if(format!=null && format.length()>0){
            dateFormat = new SimpleDateFormat(format);
        }else{
            dateFormat = new SimpleDateFormat(TIME_FORMAT);
        }
        return new java.sql.Time(dateFormat.parse(value).getTime());
    }

    public static Character createChar(String value) throws ParseException {
        if(value.length()!=1){
            throw new ParseException("Not a char value:"+value,1);
        }else{
            return new Character(value.charAt(0));
        }
    }

    public static Object create(String type,String value,String format)
            throws ParseException{
        try{
            if(value==null || value.length()<1){
                return null;
            }else if("int".equals(type) || "Integer".equals(type)){
                return createInt(value,format);
            }else if("boolean".equals(type) || "Boolean".equals(type)){
                return createBoolean(value);
            }else if("long".equals(type) || "Long".equals(type)){
                return createLong(value,format);
            }else if("short".equals(type) || "Short".equals(type)){
                return createShort(value,format);
            }else if("float".equals(type) || "Float".equals(type)){
                return createFloat(value,format);
            }else if("double".equals(type) || "Double".equals(type)){
                return createDouble(value,format);
            }else if("byte".equals(type) || "Byte".equals(type)){
                return createByte(value,format);
            }else if("java.util.Date".equals(type)){
                return createDate(value,format);
            }else if("java.sql.Date".equals(type)){
                return new java.sql.Date(createDate(value,format).getTime());
            }else if("java.sql.Timestamp".equals(type)){
                return createTimestamp(value,format);
            }else if("java.sql.Time".equals(type)){
                return createTime(value,format);
            }else if("char".equals(type) || "Character".equals(type)){
                return createChar(value);
            }else if("String".equalsIgnoreCase(type)){
                return value;
            }else if("char[]".equalsIgnoreCase(type)){
                //return value.toCharArray();
                byte[] bytes = Base64.decodeBytes(value);
                return new String(bytes).toCharArray();
            }else if("byte[]".equalsIgnoreCase(type)){
                return Base64.decodeBytes(value);
            }else{
                if(log.isEnabledFor(Priority.WARN)){
                    log.warn("trying to create a Primitive type object,but found type "+type
                            +"; null value will be returned instead");
                }
                return null;
            }
        }catch(NumberFormatException e){
            throw new NumberFormatException(value);            
        }
    }

    public static int[] createIntArray(String[] values,String format){
        int i=0;
        int size = values.length;
        int[] array = new int[size];

        try{
            if(format!=null && format.length()>0){
                DecimalFormat decimalFormat = new DecimalFormat(format);
                for(i=0;i<size;i++){
                    Number number = decimalFormat.parse(values[i]);
                    array[i] = number.intValue();
                }
            }else{
                for(i=0;i<size;i++){
                    array[i] = Integer.valueOf(values[i]).intValue();
                }
            }
        }catch(NumberFormatException e){
            throw new NumberFormatException(values[i]);
        }catch(ParseException e){
            throw new NumberFormatException(values[i]);
        }

        return array;
    }

    public static Integer[] createIntegerArray(String[] values,String format){
        int i=0;
        int size = values.length;
        Integer[] array = new Integer[size];
        try{
            if(format!=null && format.length()>0){
                DecimalFormat decimalFormat = new DecimalFormat(format);
                for(;i<size;i++){
                    Number number = decimalFormat.parse(values[i]);
                    array[i] = new Integer(number.intValue());
                }
            }else{
                for(i=0;i<size;i++){
                    array[i] = Integer.valueOf(values[i]);
                }
            }
        }catch(NumberFormatException e){
            throw new NumberFormatException(values[i]);
        }catch(ParseException e){
            throw new NumberFormatException(values[i]);
        }

        return array;
    }

    public static byte[] createByteArray(String[] values,String format){
        int i=0;
        int size = values.length;
        byte[] array = new byte[size];
        try{
            if(format!=null && format.length()>0){
                DecimalFormat decimalFormat = new DecimalFormat(format);
                for(;i<size;i++){
                    Number number = decimalFormat.parse(values[i]);
                    array[i] = number.byteValue();
                }
            }else{
                for(i=0;i<size;i++){
                    array[i] = Byte.parseByte(values[i]);
                }
            }
        }catch(NumberFormatException e){
            throw new NumberFormatException(values[i]);
        }catch(ParseException e){
            throw new NumberFormatException(values[i]);
        }

        return array;
    }

    public static Byte[] createByteObjArray(String[] values,String format){
        int i=0;
        int size = values.length;
        Byte[] array = new Byte[size];
        try{
            if(format!=null && format.length()>0){
                DecimalFormat decimalFormat = new DecimalFormat(format);
                for(;i<size;i++){
                    Number number = decimalFormat.parse(values[i]);
                    array[i] = new Byte(number.byteValue());
                }
            }else{
                for(i=0;i<size;i++){
                    array[i] = Byte.valueOf(values[i]);
                }
            }
        }catch(NumberFormatException e){
            throw new NumberFormatException(values[i]);
        }catch(ParseException e){
            throw new NumberFormatException(values[i]);
        }

        return array;
    }

    public static short[] createShortArray(String[] values,String format){
        int i=0;
        int size = values.length;
        short[] array = new short[size];
        try{
            if(format!=null && format.length()>0){
                DecimalFormat decimalFormat = new DecimalFormat(format);
                for(;i<size;i++){
                    Number number = decimalFormat.parse(values[i]);
                    array[i] = number.shortValue();
                }
            }else{
                for(i=0;i<size;i++){
                    array[i] = Short.parseShort(values[i]);
                }
            }
        }catch(NumberFormatException e){
            throw new NumberFormatException(values[i]);
        }catch(ParseException e){
            throw new NumberFormatException(values[i]);
        }

        return array;
    }

    public static Short[] createShortObjArray(String[] values,String format){
        int i=0;
        int size = values.length;
        Short[] array = new Short[size];
        try{
            if(format!=null && format.length()>0){
                DecimalFormat decimalFormat = new DecimalFormat(format);
                for(;i<size;i++){
                    Number number = decimalFormat.parse(values[i]);
                    array[i] = new Short(number.shortValue());
                }
            }else{
                for(i=0;i<size;i++){
                    array[i] = Short.valueOf(values[i]);
                }
            }
        }catch(NumberFormatException e){
            throw new NumberFormatException(values[i]);
        }catch(ParseException e){
            throw new NumberFormatException(values[i]);
        }

        return array;
    }

    public static long[] createLongArray(String[] values,String format){
        int i=0;
        int size = values.length;
        long[] array = new long[size];
        try{
            if(format!=null && format.length()>0){
                DecimalFormat decimalFormat = new DecimalFormat(format);
                for(;i<size;i++){
                    Number number = decimalFormat.parse(values[i]);
                    array[i] = number.longValue();
                }
            }else{
                for(i=0;i<size;i++){
                    array[i] = Long.parseLong(values[i]);
                }
            }
        }catch(NumberFormatException e){
            throw new NumberFormatException(values[i]);
        }catch(ParseException e){
            throw new NumberFormatException(values[i]);
        }

        return array;

    }

    public static Long[] createLongObjArray(String[] values,String format){
        int i=0;
        int size = values.length;
        Long[] array = new Long[size];
        try{
            if(format!=null && format.length()>0){
                DecimalFormat decimalFormat = new DecimalFormat(format);
                for(;i<size;i++){
                    Number number = decimalFormat.parse(values[i]);
                    array[i] = new Long(number.longValue());
                }
            }else{
                for(i=0;i<size;i++){
                    array[i] = Long.valueOf(values[i]);
                }
            }
        }catch(NumberFormatException e){
            throw new NumberFormatException(values[i]);
        }catch(ParseException e){
            throw new NumberFormatException(values[i]);
        }

        return array;

    }

    public static float[] createFloatArray(String[] values,String format){
        int i=0;
        int size = values.length;
        float[] array = new float[size];
        try{
            if(format!=null && format.length()>0){
                DecimalFormat decimalFormat = new DecimalFormat(format);
                for(;i<size;i++){
                    Number number = decimalFormat.parse(values[i]);
                    array[i] = number.floatValue();
                }
            }else{
                for(i=0;i<size;i++){
                    array[i] = Float.parseFloat(values[i]);
                }
            }
        }catch(NumberFormatException e){
            throw new NumberFormatException(values[i]);
        }catch(ParseException e){
            throw new NumberFormatException(values[i]);
        }

        return array;
    }

    public static Float[] createFloatObjArray(String[] values,String format){
        int i=0;
        int size = values.length;
        Float[] array = new Float[size];
        try{
            if(format!=null && format.length()>0){
                DecimalFormat decimalFormat = new DecimalFormat(format);
                for(;i<size;i++){
                    Number number = decimalFormat.parse(values[i]);
                    array[i] = new Float(number.floatValue());
                }
            }else{
                for(i=0;i<size;i++){
                    array[i] = Float.valueOf(values[i]);
                }
            }
        }catch(NumberFormatException e){
            throw new NumberFormatException(values[i]);
        }catch(ParseException e){
            throw new NumberFormatException(values[i]);
        }

        return array;
    }

    public static double[] createDoubleArray(String[] values,String format){
        int i=0;
        int size = values.length;
        double[] array = new double[size];
        try{
            if(format!=null && format.length()>0){
                DecimalFormat decimalFormat = new DecimalFormat(format);
                for(;i<size;i++){
                    Number number = decimalFormat.parse(values[i]);
                    array[i] = number.doubleValue();
                }
            }else{
                for(i=0;i<size;i++){
                    array[i] = Double.parseDouble(values[i]);
                }
            }
        }catch(NumberFormatException e){
            throw new NumberFormatException(values[i]);
        }catch(ParseException e){
            throw new NumberFormatException(values[i]);
        }

        return array;
    }

    public static Double[] createDoubleObjArray(String[] values,String format){
        int i=0;
        int size = values.length;
        Double[] array = new Double[size];
        try{
            if(format!=null && format.length()>0){
                DecimalFormat decimalFormat = new DecimalFormat(format);
                for(;i<size;i++){
                    Number number = decimalFormat.parse(values[i]);
                    array[i] = new Double(number.doubleValue());
                }
            }else{
                for(i=0;i<size;i++){
                    array[i] = Double.valueOf(values[i]);
                }
            }
        }catch(NumberFormatException e){
            throw new NumberFormatException(values[i]);
        }catch(ParseException e){
            throw new NumberFormatException(values[i]);
        }

        return array;
    }

    public static Date[] createDateArray(String[] values,String format) throws ParseException {
        int i=0;
        int size = values.length;

        SimpleDateFormat dateFormat;
        if(format!=null && format.length()>0){
            dateFormat = new SimpleDateFormat(format);
        }else{
            dateFormat = new SimpleDateFormat(DATE_FORMAT);
        }
        java.util.Date[] array = new java.util.Date[size];

        try{
            for(;i<size;i++){
                array[i] = dateFormat.parse(values[i]);
            }
        }catch(ParseException e){
            throw new ParseException(values[i],e.getErrorOffset());
        }

        return array;
    }

    public static java.sql.Date[] createSqlDateArray(String[] values,String format) throws ParseException {

        int i=0;
        int size = values.length;
        SimpleDateFormat dateFormat;
        if(format!=null && format.length()>0){
            dateFormat = new SimpleDateFormat(format);
        }else{
            dateFormat = new SimpleDateFormat(DATE_FORMAT);
        }
        java.sql.Date[] array = new java.sql.Date[size];

        try{
            for(;i<size;i++){
                array[i] = new java.sql.Date(dateFormat.parse(values[i]).getTime());
            }
        }catch(ParseException e){
            throw new ParseException(values[i],e.getErrorOffset());
        }

        return array;
    }


    public static Time[] createTimeArray(String[] values,String format) throws ParseException {
        int i=0;
        int size = values.length;

        SimpleDateFormat dateFormat;
        if(format!=null && format.length()>0){
            dateFormat = new SimpleDateFormat(format);
        }else{
            dateFormat = new SimpleDateFormat(TIME_FORMAT);
        }
        java.sql.Time[] array = new java.sql.Time[size];

        try{
            for(;i<size;i++){
                array[i] = new java.sql.Time(dateFormat.parse(values[i]).getTime());
            }
        }catch(ParseException e){
            throw new ParseException(values[i],e.getErrorOffset());
        }

        return array;
    }

    public static char[] createCharArray(String[] values) throws ParseException {
        int size = values.length;
        char[] array = new char[size];
        for(int i=0;i<size;i++){
            if(values[i].length()!=1){
                throw new ParseException(values[i],1);
            }else{
                array[i] = values[i].charAt(0);
            }
        }
        return array;
    }

    public static Character[] createCharacterArray(String[] values) throws ParseException {
        int size = values.length;
        Character[] array = new Character[size];
        for(int i=0;i<size;i++){
            if(values[i].length()!=1){
                throw new ParseException(values[i],1);
            }else{
                array[i] = new Character(values[i].charAt(0));
            }
        }
        return array;
    }

    public static Timestamp[] createTimestampArray(String[] values,String format) throws ParseException {
        int i=0;
        int size = values.length;

        SimpleDateFormat dateFormat;
        if(format!=null && format.length()>0){
            dateFormat = new SimpleDateFormat(format);
        }else{
            dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
        }
        java.sql.Timestamp[] array = new java.sql.Timestamp[size];

        try{
            for(;i<size;i++){
                array[i] = new java.sql.Timestamp(dateFormat.parse(values[i]).getTime());
            }
        }catch(ParseException e){
            throw new ParseException(values[i],e.getErrorOffset());
        }

        return array;
    }
    public static Object createArray(String type,String[] values,String format) throws ParseException{
        if(values!=null){
            if("int".equals(type)){
                return createIntArray(values,format);
            }else if("Integer".equals(type)){
                return createIntegerArray(values,format);
            }else if("long".equals(type)){
                return createLongArray(values,format);
            }else if("Long".equals(type)){
                return createLongObjArray(values,format);
            }else if("short".equals(type)){
                return createShortArray(values,format);
            }else if("Short".equals(type)){
                return createShortObjArray(values,format);
            }else if("float".equals(type)){
                return createFloatArray(values,format);
            }else if("Float".equals(type)){
                return createFloatObjArray(values,format);
            }else if("double".equals(type)){
                return createDoubleArray(values,format);
            }else if("Double".equals(type)){
                return createDoubleObjArray(values,format);
            }else if("byte".equals(type)){
                return createByteArray(values,format);
            }else if("Byte".equals(type)){
                return createByteObjArray(values,format);
            }else if("java.util.Date".equals(type)){
                return createDateArray(values,format);
            }else if("java.sql.Date".equals(type)){
                return createSqlDateArray(values,format);
            }else if("java.sql.Timestamp".equals(type)){
                return createTimestampArray(values,format);
            }else if("java.sql.Time".equals(type)){
                return createTimeArray(values,format);
            }else if("char".equals(type)){
                return createCharArray(values);
            }else if("Character".equals(type)){
                return createCharacterArray(values);
            }else if("String".equalsIgnoreCase(type)){
                return values;
            }else{
                if(log.isEnabledFor(Priority.WARN)){
                    log.warn("trying to create a Primitive array object,but found type "+type
                            +"; null value will be returned instead");
                }
            }
        }
        return null;
    }

    public static Object[] createObjectArray(String type,String[] values) throws ParseException{
        return createObjectArray(type,values,null);
    }

    /**
     * create an primite object array;
     *
     * @param type
     * @param values
     * @param format
     * @return
     * @throws ParseException
     */
    public static Object[] createObjectArray(String type,String[] values,String format) throws ParseException{
        if(values!=null){
            if("int".equals(type) || "Integer".equals(type)){
                return createIntegerArray(values,format);
            }else if("long".equals(type) || "Long".equals(type)){
                return createLongObjArray(values,format);
            }else if("short".equals(type) || "Short".equals(type)){
                return createShortObjArray(values,format);
            }else if("float".equals(type) || "Float".equals(type)){
                return createFloatObjArray(values,format);
            }else if("double".equals(type) || "Double".equals(type)){
                return createDoubleObjArray(values,format);
            }else if("byte".equals(type) || "Byte".equals(type)){
                return createByteObjArray(values,format);
            }else if("java.util.Date".equals(type)){
                return createDateArray(values,format);
            }else if("java.sql.Date".equals(type)){
                return createSqlDateArray(values,format);
            }else if("java.sql.Timestamp".equals(type)){
                return createTimestampArray(values,format);
            }else if("java.sql.Time".equals(type)){
                return createTimeArray(values,format);
            }else if("char".equals(type) || "Character".equals(type)){
                return createCharacterArray(values);
            }else if("String".equalsIgnoreCase(type)){
                return values;
            }else{
                Logger log = Logger.getLogger(PrimitiveType.class);
                if(log.isEnabledFor(Priority.WARN)){
                    log.warn("trying to create a Primitive array object,but found type "+type
                            +"; null value will be returned instead");
                }
            }
        }
        return null;
    }


    /**
     * check if a type is primitive;
     *
     * @param type
     * @return true if the type is primitive; false otherwise;
     */
    public static boolean isPrimitive(String type){
        if(primitiveType.get(type)!=null ){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isPrimitive(Class clz){
        if(primitiveType.containsValue(clz)){
            return true;
        }else{
            return false;
        }
    }

    public static String getTypeName(Class clz){
        Iterator it = primitiveType.keySet().iterator();
        while(it.hasNext()){
            String name = (String)it.next();
            Class c = (Class)primitiveType.get(name);
            if(clz.equals(c)){
                return name;
            }
        }
        return null;
    }

    /**
     * get the Class for a type;
     *
     * @param type
     * @return
     */
    public static Class getTypeClass(String type){
        return (Class)primitiveType.get(type);
    }

    public static Class getArrayClass(String type){
        return (Class)ARRAY_CLASSES.get(type);
    }
    
    public static String getWrapperClass(String type){
        return (String)WRAPPER_CLASSES.get(type);
    }
}