/**
 *All rights reserved by cots co. ltd.
 */
package com.cots.dao;

import java.util.Map;
import java.util.HashMap;

/**
 * User: chugh
 * Date: 2005-10-1
 * Time: 11:21:02
 */
public class Types {
    private static Map map = new HashMap();
    static {
        map.put("int",new Integer(java.sql.Types.INTEGER));
        map.put("byte",new Integer(java.sql.Types.TINYINT));
        map.put("short",new Integer(java.sql.Types.SMALLINT));
        map.put("long",new Integer(java.sql.Types.BIGINT));
        map.put("float",new Integer(java.sql.Types.FLOAT));
        map.put("double",new Integer(java.sql.Types.DOUBLE));
        map.put("Date",new Integer(java.sql.Types.DATE));
        map.put("String",new Integer(java.sql.Types.VARCHAR));
        map.put("java.util.Date",new Integer(java.sql.Types.DATE));
        map.put("java.sql.Date",new Integer(java.sql.Types.DATE));
        map.put("java.sql.Time",new Integer(java.sql.Types.TIME));
        map.put("java.sql.Timestamp",new Integer(java.sql.Types.TIMESTAMP));
        map.put("byte[]",new Integer(java.sql.Types.BLOB));
        map.put("char[]",new Integer(java.sql.Types.CLOB));
    }

    public static int jdbcType(String typeName){
        Integer i = (Integer)map.get(typeName);
        if(i!=null){
            return i.intValue();
        }else{
            return java.sql.Types.OTHER;
        }
    }
}
