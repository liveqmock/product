/**
 *All rights reserved by cots co. ltd.
 */
package com.cots.dao.sql;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.cots.util.XMLFile;

/**
 * User: chugh
 * Date: 2005-9-24
 * Time: 9:48:13
 */
public abstract class BaseStatement {
    protected String id;
    protected String sql;
    protected String rowClass;

    private ArrayList params = new ArrayList();

    public BaseStatement() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void addParam(StatementParam param){
        params.add(param);
    }

    public StatementParam[] getParameters(){
        return (StatementParam[])params.toArray(new StatementParam[params.size()]);
    }

    public String getRowClass() {
        return rowClass;
    }

    public void setRowClass(String rowClass) {
        this.rowClass = rowClass;
    }

    public void init(XMLFile xml,Element ele){
        String aString = ele.getAttribute("rowClass");
        if(aString != null && aString.length()>1){
            rowClass = aString;
        }

        aString = ele.getAttribute("id");
        if(aString!=null && aString.length()>0){
            this.id = aString;
        }

        aString = xml.getSingleNodeValue(ele,"source");
        if(aString!=null && aString.length()>0){
            this.sql = aString;
        }

        NodeList nl = xml.selectNodeList(ele,"param");
        if(nl!=null){
            int count = nl.getLength();
            for(int i=0;i<count;i++){
                Element pe = (Element)nl.item(i);
                StatementParam sp = new StatementParam();
                sp.init(pe);
                params.add(sp);
            }
        }
    }

    public PreparedStatement prepare(Connection conn) throws SQLException{
        return conn.prepareStatement(sql);
    }

    public PreparedStatement prepare(Connection conn,int resultSetType,int resultConcurrency ) throws SQLException{
        return conn.prepareStatement(sql,resultSetType,resultConcurrency);
    }

    public abstract void setParamValues(PreparedStatement stmt,Map values)throws SQLException;

    public void getParamValues(PreparedStatement stmt,Map values) throws SQLException{

    }

    public static BaseStatement create(XMLFile xml,Element e){
        String id = e.getAttribute("id");
        if(id==null || id.length()<1){
            return null;
        }
        String rowClass = e.getAttribute("rowClass");
        if(rowClass==null || rowClass.length()<1){
            return null;
        }
        String source = xml.getSingleNodeValue(e,"source");
        if(source == null){
            return null;
        }
        source = source.trim();
        if(source.startsWith("{")){
            SPStatement s = new SPStatement();
            s.init(xml,e);
            return s;
        }else{
            SQLStatement s = new SQLStatement();
            s.init(xml,e);
            return s;
        }
    }
}
