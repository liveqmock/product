/**
 *All rights reserved by cots co. ltd.
 */
package com.cots.dao.sql;

import org.w3c.dom.Element;
import com.cots.dao.Types;

/**
 * User: chugh
 * Date: 2005-9-24
 * Time: 9:51:23
 */
public class StatementParam {
    public static final int IN=1;
    public static final int OUT=2;

    private String name;
    private String type;
    private int passing;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPassing() {
        return passing;
    }

    public void setPassing(int passing) {
        this.passing = passing;
    }

    public int getJDBCType(){
        return Types.jdbcType(type);
    }

    public void init(Element ele){
        String aString = ele.getAttribute("name");
        if(aString!=null && aString.length()>0){
            this.name = aString;
        }
        aString = ele.getAttribute("type");
        if(aString!=null && aString.length()>0){
            this.type = aString;
        }
        aString = ele.getAttribute("passing");
        if("out".equalsIgnoreCase(aString)){
            this.passing = 2;
        }else if("inout".equalsIgnoreCase(aString)){
            this.passing = 3;
        }else{
            this.passing =1;
        }
    }
}
