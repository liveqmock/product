/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util;

import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.uploader.MultiPart;
import com.dream.bizsdk.common.util.uploader.Part;
import com.dream.bizsdk.common.util.uploader.FilePart;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

import java.util.Iterator;
import java.io.UnsupportedEncodingException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-3-8
 * Time: 13:29:16
 */
public class RequestParams {

    public final boolean parse(HttpServletRequest request, String cp, BizData data)
            throws UnsupportedEncodingException, ParamNameException {
        int len;
        int nameLen;
        boolean isGet = true;
        String value;
        StringBuffer entityName = new StringBuffer(256);
        StringBuffer fieldName = new StringBuffer(256);
        StringBuffer attrName = new StringBuffer(256);
        StringBuffer rowIndex = new StringBuffer(256);
        StringBuffer colIndex = new StringBuffer(256);

        String method = request.getMethod();
        //set the character encoding;
        if (method.compareToIgnoreCase("post") == 0) {
            request.setCharacterEncoding(cp);
            isGet = false;
        }

        Enumeration paramNames = request.getParameterNames();
        NameParser parser = new NameParser();
        //process each parameter, a parameter may have more than one values.
        //add all the parameters to the newly created BizData object.
        while (paramNames.hasMoreElements()) {
            //clear the buffers;
            entityName.delete(0, entityName.length());
            fieldName.delete(0, fieldName.length());
            attrName.delete(0, attrName.length());
            rowIndex.delete(0, rowIndex.length());
            colIndex.delete(0, colIndex.length());

            String name = (String) paramNames.nextElement();
            nameLen = name.length();
            String[] values = request.getParameterValues(name);
            len = values.length;

            /**an array entity*/
            if (nameLen > 1 && name.charAt(nameLen - 2) == '[' && name.charAt(nameLen - 1) == ']') {
                name = name.substring(0, nameLen - 2);
                for (int j = 0; j < len; j++) {
                    String v;
                    if (isGet) {
                        v = new String(values[j].getBytes("iso-8859-1"), cp);
                    } else {
                        v = values[j];
                    }
                    data.add(name, j, v);
                }
                continue;
            }

            if (len == 1) {
                if (values[0] == null || values[0].length() == 0) {
                    value = null;
                } else {
                    if (isGet) {
                        value = new String(values[0].getBytes("iso-8859-1"), cp);
                    } else {
                        value = values[0];
                    }
                }
            } else {
                value = null;
            }

            int elementType = parser.parse(name, entityName, fieldName, attrName, rowIndex, colIndex);
            if (!addData(data, elementType, entityName, fieldName, rowIndex, colIndex, attrName, value)) {
                throw new ParamNameException(name);
            }
        }
        return true;
    }

    /**
     * @param cp
     * @param data
     * @return
     */
    public final boolean parse(MultiPart mp, String cp, BizData data) throws ParamNameException {
        int nameLen;
        int len = 0;
        String fileExtName = null;
        String[] values = null;
        Iterator it = null;
        Object value = null;

        StringBuffer entityName = new StringBuffer(256);
        StringBuffer fieldName = new StringBuffer(256);
        StringBuffer attrName = new StringBuffer(256);
        StringBuffer rowIndex = new StringBuffer(256);
        StringBuffer colIndex = new StringBuffer(256);

        NameParser parser = new NameParser();
        //process each parameter, a parameter may have more than one values.
        //add all the parameters to the newly created BizData object.
        it = mp.getNames();
        while (it.hasNext()) {
            String name = (String) it.next();
            //clear the buffers;
            entityName.delete(0, entityName.length());
            fieldName.delete(0, fieldName.length());
            attrName.delete(0, attrName.length());
            rowIndex.delete(0, rowIndex.length());
            colIndex.delete(0, colIndex.length());

            Part p = mp.getPart(name);

            if (p instanceof FilePart) {
                byte[] c = p.getBytes();
                byte[] nc = new byte[c.length + 6];

                fileExtName = ((FilePart) p).getExtName();
                byte[] fb = fileExtName.getBytes();
                int l = fb.length;
                if (l <= 6) {
                    System.arraycopy(fb, 0, nc, 0, fb.length);
                    for (int i = 0; i < 6 - l; i++) {
                        nc[i] = ' ';
                    }
                } else {
                    System.arraycopy(fb, 0, nc, 0, 6);
                }
                System.arraycopy(c, 0, nc, 6, c.length);
                value = nc;
            } else {
                values = mp.getParameterValues(name, cp);
                if ((len = values.length) == 0) {
                    continue;
                }

                nameLen = name.length();
                /**an array entity*/
                if (nameLen > 1 && name.charAt(nameLen - 2) == '[' && name.charAt(nameLen - 1) == ']') {
                    name = name.substring(0, nameLen - 2);
                    for (int j = 0; j < len; j++) {
                        data.add(name, j, values[j]);
                    }
                    continue;
                }
                value = values[0];
            }
            int elementType = parser.parse(name, entityName, fieldName, attrName, rowIndex, colIndex);
            if (!addData(data, elementType, entityName, fieldName, rowIndex, colIndex, attrName, value)) {
                throw new ParamNameException(name);
            }
        }
        return true;
    }

    private final boolean addData(BizData data, int elementType, StringBuffer entityName
                                  , StringBuffer fieldName, StringBuffer rowIndex, StringBuffer colIndex,
                                  StringBuffer attrName, Object value) {
        if (elementType != NameParser.INVALID_NAME) {
            switch (elementType) {
                case NameParser.SINGLE:
                    data.add(new String(entityName), value);
                    break;
                case NameParser.TABLE_FIELD:
                    data.add(new String(entityName), new String(fieldName), value);
                    break;
                case NameParser.TALE_FIELD_INDEX:
                    data.add(new String(entityName), new String(fieldName), new String(rowIndex), value);
                    break;
                case NameParser.TABLE_FIELD_ATTR:
                    data.addAttr(new String(entityName), new String(fieldName), new String(attrName), value);
                    break;
                case NameParser.TABLE_FIELD_INDEX_ATTR:
                    data.addAttr(new String(entityName), new String(fieldName), new String(rowIndex), new String(attrName), value);
                    break;
                case NameParser.TABLE_INDEX_ATTR:
                    data.addRowAttr(new String(entityName), new String(rowIndex), new String(attrName), value);
                    break;
                case NameParser.SINGLE_ATTR:
                    data.addAttr(new String(entityName), new String(attrName), value);
                    break;
                case NameParser.ONE_ARRAY:
                    data.add(new String(entityName), Integer.valueOf(new String(rowIndex)).intValue(), value);
                    break;
                case NameParser.TWO_ARRAY:
                    data.add(new String(entityName), Integer.valueOf(new String(rowIndex)).intValue(), Integer.valueOf(new String(colIndex)).intValue(), value);
                    break;
                default:
                    return false;
            }
            return true;
        } else {
            return false;
        }
    }
}