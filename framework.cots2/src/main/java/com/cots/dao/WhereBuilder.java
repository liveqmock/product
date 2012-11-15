/**
 *all rights reserved,@copyright 2003
 */
package com.cots.dao;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-4-6
 * Time: 10:44:06
 * Version: 1.0
 */
public class WhereBuilder {
    StringBuffer sb = new StringBuffer(256);
    String currentColumn;

    public WhereBuilder(){

    }

    public WhereBuilder column(String column){
        currentColumn = column;
        return this;
    }

    public WhereBuilder equals(String value){
        sb.append(currentColumn);
        sb.append("='").append(value.replaceAll("'","''")).append("'");
        return this;
    }

    public WhereBuilder equals(int value){
        sb.append(currentColumn);
        sb.append("=").append(value);
        return this;
    }

    public WhereBuilder equals(long value){
        sb.append(currentColumn);
        sb.append("=").append(value);
        return this;
    }

    public WhereBuilder equals(float value){
        sb.append(currentColumn);
        sb.append("=").append(value);
        return this;
    }

    public WhereBuilder equals(double value){
        sb.append(currentColumn);
        sb.append("=").append(value);
        return this;
    }

    public WhereBuilder equals(java.util.Date value){
        return this;
    }

    public WhereBuilder equals(java.sql.Date value){
        return this;
    }

    public WhereBuilder notEquals(String value){
        sb.append(currentColumn);
        sb.append("<>'").append(value.replaceAll("'","''")).append("'");
        return this;
    }

    public WhereBuilder notEquals(int value){
        sb.append(currentColumn);
        sb.append("<>").append(value);
        return this;
    }

    public WhereBuilder notEquals(long value){
        sb.append(currentColumn);
        sb.append("<>").append(value);
        return this;
    }

    public WhereBuilder notEquals(float value){
        sb.append(currentColumn);
        sb.append("<>").append(value);
        return this;
    }

    public WhereBuilder notEquals(double value){
        sb.append(currentColumn);
        sb.append("<>").append(value);
        return this;
    }

    public WhereBuilder notEquals(java.util.Date value){
        return this;
    }

    public WhereBuilder notEquals(java.sql.Date value){
        return this;
    }

    public WhereBuilder like(String value){
        sb.append(currentColumn);
        sb.append(" like '").append(value.replaceAll("'","''")).append("'");
        return this;
    }

    public WhereBuilder in(String[] values){
        sb.append(currentColumn);
        sb.append(" in (");
        for(int i=0;i<values.length;i++){
            sb.append("'").append(values[i].replaceAll("'","''")).append("'");
            if(i<values.length-1){
                sb.append(",");
            }
        }
        sb.append(")");

        return this;
    }

    public WhereBuilder in(int[] values){
        sb.append(currentColumn);
        sb.append(" in (");
        for(int i=0;i<values.length;i++){
            sb.append(values[i]);
            if(i<values.length-1){
                sb.append(",");
            }
        }
        sb.append(")");
        return this;
    }

    public WhereBuilder in(long[] values){
        sb.append(currentColumn);
        sb.append(" in (");
        for(int i=0;i<values.length;i++){
            sb.append(values[i]);
            if(i<values.length-1){
                sb.append(",");
            }
        }
        sb.append(")");

        return this;
    }

    public WhereBuilder in(float[] values){
        sb.append(currentColumn);
        sb.append(" in (");
        for(int i=0;i<values.length;i++){
            sb.append(values[i]);
            if(i<values.length-1){
                sb.append(",");
            }
        }
        sb.append(")");
        return this;
    }

    public WhereBuilder in(double[] values){
        sb.append(currentColumn);
        sb.append(" in (");
        for(int i=0;i<values.length;i++){
            sb.append(values[i]);
            if(i<values.length-1){
                sb.append(",");
            }
        }
        sb.append(")");
        return this;
    }

    public WhereBuilder in(java.util.Date[] values){
        sb.append(currentColumn);
        sb.append(" in (");
        for(int i=0;i<values.length;i++){
            sb.append(values[i]);
            if(i<values.length-1){
                sb.append(",");
            }
        }
        sb.append(")");
        return this;
    }

    /**
     *
     * @param values
     * @return
     */
    public WhereBuilder in(java.sql.Date[] values){
        sb.append(currentColumn);
        sb.append(" in (");
        for(int i=0;i<values.length;i++){
            sb.append(values[i]);
            if(i<values.length-1){
                sb.append(",");
            }
        }
        sb.append(")");
        return this;
    }

    public WhereBuilder isNull(){
        sb.append(currentColumn);
        sb.append(" is null");
        return this;
    }

    public WhereBuilder notNull(){
        sb.append(currentColumn);
        sb.append(" is not null");
        return this;
    }

    /**
     *
     * @return
     */
    public WhereBuilder and(){
        sb.append(" and ");
        return this;
    }

    /**
     *
     * @return
     */
    public WhereBuilder or(){
        sb.append(" or ");
        return this;
    }
}
