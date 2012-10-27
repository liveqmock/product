/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.database.sql;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-6-10
 * Time: 9:51:43
 */
public class SqlAnalyzer implements ISqlAnalyzer {

    public SqlAnalyzer() {

    }

    public TableAlias[] getTableAlias(String select) {
        return null;
    }

    public ColumnAlias[] getColumnAlias(String select) {
        return null;
    }

    /**
     * 过滤空格，直到下一个非空格字符，并返回这个字符的index;
     *
     * @param sb
     * @param pos
     * @return
     */
    private int beginBlank(StringBuffer sb, int pos) {
        while (sb.charAt(pos) == ' ') {
            pos++;
        }
        return pos;
    }

    private int beginColumn(StringBuffer sb, ColumnAlias ca, int pos) {
        return 0;
    }

    private int beginTable(StringBuffer sb, TableAlias ta, int pos) {
        return 0;
    }

    private int beginParenthesis(StringBuffer sb, int pos) {
        return 0;
    }

    private int beginSelct(StringBuffer sb, int pos) {
        String cs = "select";
        String select = sb.substring(pos, pos + cs.length());
        if (select.compareToIgnoreCase(cs) == 0) {
            return pos + cs.length();
        } else {
            return -1;
        }
    }

    private int beginFrom(StringBuffer sb) {
        return 0;
    }
}
