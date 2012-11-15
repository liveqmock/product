/**
 *All rights reserved by cots co. ltd.
 */
package com.cots.bean.display;

import com.cots.bean.BeanProperty;
import com.cots.bean.BeanPropertyValue;
import com.cots.blc.BLContext;
import com.cots.util.ObjectUtil;
import com.cots.dao.DBType;
import com.cots.dao.RSUtil;
import com.cots.dao.DAO;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;

/**
 * The parent for Checkbox and the Radio.
 *
 * User: chugh
 * Date: 2005-9-22
 * Time: 20:43:00
 */
public class Group extends Input {

    public void write(JspWriter out,String beanName,Object bean,BeanProperty bp,int dispType,BLContext context) throws IOException,SQLException{
        Object fieldValue = ObjectUtil.getField(bean,bp.getName());

        List valuesEnum = bp.getValuesEnum();
        if(valuesEnum!=null){
            int count = valuesEnum.size();
            for(int i=0;i<count;i++){
                BeanPropertyValue bpv = (BeanPropertyValue)valuesEnum.get(i);
                begin(out,beanName+"."+bp.getName(),dispType);
                writeBody(out,bpv.getValue(),bp,dispType,context);
                if(fieldValue!=null && fieldValue.toString().equals(bpv.getValue())){
                    out.write(" checked");
                }
                end(out,dispType);
                out.write(bpv.getLabel());
            }
        }else{
            String fkName = bp.getFkName();
            String labelName = bp.getFkLabel();
            String fkTable = bp.getFkTable();
            String fkDAOName = bp.getFkDAOName();
            DAO dao = context.getDAO(fkDAOName);
            if(dao == null){
                dao = context.getDAO();
            }

            String sql = "select "+DBType.buildName(fkName,dao.getDBType())+","+DBType.buildName(labelName,dao.getDBType())+
                    " from "+ DBType.buildName(fkTable,dao.getDBType());
            ResultSet rs = null;

            try{
                rs = dao.executeQuery(sql);

                while(rs.next()){
                    String fkValue = rs.getString(fkName);
                    begin(out,beanName+"."+bp.getName(),dispType);
                    writeBody(out,fkValue,bp,dispType,context);
                    if(fieldValue!=null && fieldValue.toString().equals(fkValue)){
                        out.write(" checked");
                    }
                    end(out,dispType);
                    out.write(rs.getString(labelName));
                }
            }finally{
                if(rs != null ){
                    RSUtil.close(rs,dao.getDataSource());
                }
            }
        }
    }

    public void writeBody(JspWriter out,Object obj,BeanProperty bp,int dispType,BLContext context) throws IOException {
        out.write(" value=\"");
        if(obj!=null){
            out.write(obj.toString());
        }
        out.write("\"");
    }
}
