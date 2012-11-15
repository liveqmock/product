/**
 *All rights reserved by cots co. ltd.
 */
package com.cots.bean.display;

import org.w3c.dom.Element;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.util.List;
import java.sql.SQLException;
import java.sql.ResultSet;

import com.cots.bean.BeanProperty;
import com.cots.bean.BeanPropertyValue;
import com.cots.dao.DAO;
import com.cots.dao.DBType;
import com.cots.dao.RSUtil;
import com.cots.util.ObjectUtil;
import com.cots.blc.BLContext;

/**
 * User: chugh
 * Date: 2005-9-22
 * Time: 18:56:27
 */
public class Select extends AbstractDisplay{
    protected boolean multiple = false;
    protected String size;

    public void init(Element ele) {
        super.init(ele);
        String aString=ele.getAttribute("multiple");
        if("true".equalsIgnoreCase(aString)){
            multiple = true;
        }

        aString = ele.getAttribute("size");
        if(aString!=null && aString.length()>0){
            size = aString;
        }
    }

    public void begin(JspWriter out,String name,int dispType) throws IOException {
        if(dispType == DISPLAY_READONLY){
            return;
        }

        out.write("<select");
        super.begin(out,name,dispType);
        if(multiple){
            out.write(" multiple");
            if(size!=null){
                out.write(" size=\"");
                out.write(size);
                out.write("\"");
            }
        }
        out.write(">");
    }

    public void end(JspWriter out,int dispType) throws IOException {
        if(dispType == DISPLAY_READONLY){
            return;
        }

        out.write("</select>");
    }

    public void writeBody(JspWriter out, Object obj, BeanProperty bp, int dispType,BLContext context)
            throws IOException, SQLException {

        if(dispType == DISPLAY_READONLY){
            String fieldValue;
            if(obj!=null){
                Object value = ObjectUtil.getField(obj,bp.getName());
                if(value != null){
                    fieldValue = value.toString();
                }else{
                    fieldValue = "&nbsp;";
                }
            }else{
                fieldValue = "&nbsp;";
            }
            out.write(fieldValue);
            return;
        }

        //for UPDATE and NEW display;
        List valuesEnum = bp.getValuesEnum();
        if(valuesEnum!=null && valuesEnum.size() > 1){
            writeValuesEnum(out,valuesEnum,bp.getName(),obj);
        }else{
            String fkDAOName = bp.getFkDAOName();
            DAO dao = context.getDAO(fkDAOName);
            if(dao == null){
                dao = context.getDAO();
            }
            writeFKValues(out,bp.getName(),obj,dao,bp.getFkTable(),bp.getFkName(),bp.getFkLabel());
        }
    }
    /**
     * write <select> with the list Values;
     *
     * @param out
     * @param valuesEnum
     * @param bpName
     * @param obj
     * @throws IOException
     */
    private void writeValuesEnum(JspWriter out,List valuesEnum,String bpName,Object obj)
            throws IOException {
        StringBuffer sb = new StringBuffer(128);
        String fieldValue;
        if(obj!=null){
            Object value = ObjectUtil.getField(obj,bpName);
            if(value != null){
                fieldValue = value.toString();
            }else{
                fieldValue = "";
            }
        }else{
            fieldValue = "";
        }

        int count = valuesEnum.size();
        for(int i=0;i<count;i++){
            BeanPropertyValue bpv = (BeanPropertyValue)valuesEnum.get(i);
            sb.append("<option value=\"");
            sb.append(bpv.getValue());
            sb.append("\" ");
            if(fieldValue.equals(bpv.getValue())){
                sb.append(" selected ");
            }
            sb.append(">").append(bpv.getLabel()).append("</option>");
        }
        out.write(new String(sb));
    }

    private void writeFKValues(JspWriter out,String bpName,Object obj,
                                         DAO dao,String fkTable,String fkName,String labelName)
            throws IOException,SQLException {
        StringBuffer sb = new StringBuffer(128);
        String fieldValue;
        if(obj!=null){
            Object value = ObjectUtil.getField(obj,bpName);
            if(value != null){
                fieldValue = value.toString();
            }else{
                fieldValue = "";
            }
        }else{
            fieldValue = "";
        }

        String sql = "select "+DBType.buildName(fkName,dao.getDBType())+","+DBType.buildName(labelName,dao.getDBType())+
                " from "+ DBType.buildName(fkTable,dao.getDBType());
        ResultSet rs = null;

        try{
            rs = dao.executeQuery(sql);

            while(rs.next()){
                String fkValue = rs.getString(fkName);
                sb.append("<option value=\"");

                sb.append(fkValue);
                sb.append("\" ");

                if(fieldValue.equals(fkValue)){
                    sb.append(" selected ");
                }
                sb.append(">").append(rs.getString(labelName)).append("</option>");
            }
        }finally{
            if(rs != null ){
                RSUtil.close(rs,dao.getDataSource());
            }
        }
        out.write(new String(sb));
    }
}
