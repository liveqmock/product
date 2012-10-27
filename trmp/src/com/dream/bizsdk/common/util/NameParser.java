/*
 * NameParser.java
 *
 * Created on 2002年11月26日, 上午8:57
 */

package com.dream.bizsdk.common.util;

/**
 * @author zhugh
 */
public class NameParser {
    public final static int INVALID_NAME = -1;
    public final static int SINGLE = 0;
    public final static int TABLE_FIELD = 1;
    public final static int TALE_FIELD_INDEX = 2;
    public final static int ONE_ARRAY = 3;
    public final static int TWO_ARRAY = 4;
    public final static int SINGLE_ATTR = 5;
    public final static int TABLE_FIELD_ATTR = 6;
    public final static int TABLE_FIELD_INDEX_ATTR = 7;
    public final static int TABLE_INDEX_ATTR = 8;

    /**
     * Creates a new instance of NameParser
     */
    public NameParser() {
    }

    public final int parse(String source, StringBuffer name, StringBuffer field
                           , StringBuffer attr, StringBuffer rowIndex, StringBuffer colIndex) {
        if (!beginName(source, name)) {
            return NameParser.INVALID_NAME;
        }
        int len = source.length();
        int start = 0;
        int currentType = NameParser.SINGLE;
        boolean rIndex = false;
        boolean cIndex = false;
        start = endName(source, len, name);
        while (start + 1 < len) {
            switch (source.charAt(start)) {
                case '@':
                    if (currentType == NameParser.SINGLE) {
                        currentType = NameParser.SINGLE_ATTR;
                    } else if (currentType == NameParser.TABLE_FIELD) {
                        currentType = NameParser.TABLE_FIELD_ATTR;
                    } else if (currentType == NameParser.TALE_FIELD_INDEX) {
                        currentType = NameParser.TABLE_FIELD_INDEX_ATTR;
                    } else if (currentType == NameParser.ONE_ARRAY) {
                        currentType = NameParser.TABLE_INDEX_ATTR;
                    } else {
                        return NameParser.INVALID_NAME;
                    }
                    start = endAttr(source, start, len, attr);
                    break;
                case '/':
                    if (currentType == NameParser.SINGLE) {
                        currentType = NameParser.TABLE_FIELD;
                        start = endField(source, start, len, field);
                    } else {
                        return NameParser.INVALID_NAME;
                    }
                    break;
                case '[':
                    if (rIndex) {
                        currentType = NameParser.TWO_ARRAY;
                        start = endColIndex(source, start, len, colIndex);
                        cIndex = true;
                    } else {
                        if (currentType == NameParser.TABLE_FIELD) {
                            currentType = NameParser.TALE_FIELD_INDEX;
                        } else {
                            currentType = NameParser.ONE_ARRAY;
                        }
                        start = endRowIndex(source, start, len, rowIndex);
                        rIndex = true;
                    }
                    break;
            }
            if (start == 0 || start == -1) {
                return NameParser.INVALID_NAME;
            }
            if ((cIndex || rIndex)
                    && (currentType == NameParser.SINGLE_ATTR
                    || currentType == NameParser.TABLE_FIELD_ATTR)) {
                return NameParser.INVALID_NAME;
            }
            if (currentType == NameParser.TABLE_FIELD_INDEX_ATTR && start + 1 < len) {
                return NameParser.INVALID_NAME;
            }
            if (cIndex && start + 1 < len) {
                return NameParser.INVALID_NAME;
            }

        }
        return currentType;
    }

    private final boolean beginName(String source, StringBuffer name) {

        if (name.length() > 0) {
            name.delete(0, name.length());
        }
        int len = source.length();
        if (len <= 0) {
            return false;
        }
        char c = source.charAt(0);
        if (c == '@' || c == '/' || c == '[' || c == ']') {
            return false;
        } else {
            return true;
        }
    }

    private final int endName(String source, int len, StringBuffer name) {
        int i = 0;
        char c;
        while (i < len) {
            c = source.charAt(i);
            if (c == '@' || c == '/' || c == '[' || c == ']') {
                break;
            } else {
                name.append(c);
                i++;
            }
        }
        return i;
    }

    private final int endAttr(String source, int start, int len, StringBuffer attr) {
        int i = start + 1;
        char c;
        if (attr.length() > 0) {
            attr.delete(0, attr.length());
        }
        while (i < len) {
            c = source.charAt(i);
            if (c == '@' || c == '/' || c == '[' || c == ']') {
                return -1;
            }
            attr.append(c);
            i++;
        }
        if (i == (start + 1)) {
            return NameParser.INVALID_NAME;
        } else {
            return i;
        }
    }

    private final int endField(String source, int start, int len, StringBuffer field) {
        int i = start + 1;
        char c;
        if (field.length() > 0) {
            field.delete(0, field.length());
        }
        while (i < len) {
            c = source.charAt(i);
            if (c == '@' || c == '[' || c == ']') {
                break;
            } else {
                field.append(c);
                i++;
            }
        }
        if (i == start + 1) {
            return NameParser.INVALID_NAME;
        } else {
            return i;
        }
    }

    private final int endRowIndex(String source, int start, int len, StringBuffer rowIndex) {
        int i = start + 1;
        char c;
        boolean closed = false;
        if (rowIndex.length() > 0) {
            rowIndex.delete(0, rowIndex.length());
        }
        while (i < len) {
            c = source.charAt(i);
            if (c == ']') {
                closed = true;
                break;
            } else if (c == '/' || c == '@' || c == '[') {
                return NameParser.INVALID_NAME;
            } else {
                rowIndex.append(c);
                i++;
            }
        }
        if (closed) {
            return i + 1;
        } else {
            return NameParser.INVALID_NAME;
        }
    }

    private final int endColIndex(String source, int start, int len, StringBuffer colIndex) {
        int i = start + 1;
        char c;
        boolean closed = false;
        if (colIndex.length() > 0) {
            colIndex.delete(0, colIndex.length());
        }
        while (i < len) {
            c = source.charAt(i);
            if (c == ']') {
                closed = true;
                break;
            } else if (c == '/' || c == '@' || c == '[') {
                return -1;
            } else {
                colIndex.append(c);
                i++;
            }
        }
        if (closed) {
            return i + 1;
        } else {
            return NameParser.INVALID_NAME;
        }
    }
}
