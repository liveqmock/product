/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.databus;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.apache.log4j.Logger;

import java.util.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.dream.bizsdk.common.util.xml.XMLDocument;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-7
 * Time: 18:04:44
 */
public class Util {
    private static HashMap getMethods = new HashMap();
    private static HashMap setMethods = new HashMap();

    /**
     * 将某一列的值作为行号，重新组织该记录型数据；
     *
     * @param d     数据对象
     * @param table 指定的记录型数据的名称
     * @param field 做为行号的字段名称；
     */
    public static void fieldAsRowID(BizData d, String table, String field) {
        int count;
        String[] rowIDs;
        Vector fields;
        BizData t = new BizData();

        if (d != null && table != null && field != null) {
            rowIDs = d.getRowIDs(table);
            if (rowIDs != null) {
                fields = d.getTableFields(table);
                int fieldsCount = fields.size();
                count = rowIDs.length;

                for (int i = 0; i < count; i++) {
                    String rid = (String) d.get(table, field, rowIDs[i]);
                    for (int j = 0; j < fieldsCount; j++) {
                        String fieldName = (String) fields.get(j);
                        t.add(table, fieldName, rid, d.get(table, fieldName, rowIDs[i]));
                    }
                }
                d.copyEntity(t, table);
            }
        }
    }

    /**
     * add a field to a existing record entity.
     *
     * @param d
     * @param table
     * @param field
     * @param fieldValue
     */
    public static void addField(BizData d, String table, String field, String fieldValue) {
        int count;
        String[] rowIDs;

        if (d != null && table != null && field != null) {
            rowIDs = d.getRowIDs(table);
            if (rowIDs != null) {
                count = rowIDs.length;

                for (int i = 0; i < count; i++) {
                    d.add(table, field, rowIDs[i], fieldValue);
                }
            }
        }
    }

    /**
     * Serialize all the data items in a BizData object to a String object;
     *
     * @param d BizData object;
     * @return the serialized String object;
     */
    public static String serialize2String(BizData d) {
        return d.toString();
    }

    /**
     * Serialize all the data items in a BizData object to a XML Element;
     * the caller should provide a valid Element object;
     *
     * @param d the BizData object to be serialized;
     * @param e the xml element that all the data items will be serialized to;
     */
    public static void toXML(BizData d, Element e) {
        Document doc = e.getOwnerDocument();
        if (d != null) {
            Iterator it = d.entityNames();
            while (it.hasNext()) {
                String name = (String) it.next();
                toXML(d, name, doc, e);
                serializeEntityAttrs2XML(d, name, doc, e);
            }
        }
    }

    /**
     * Serialize all the data items in a BizData object to a XML Document;
     *
     * @param d
     * @return
     */
    public static Document toXML(BizData d) {
        Document doc = XMLDocument.newDocument("bizdata");
        Element e = doc.getDocumentElement();
        if (d != null) {
            Iterator it = d.entityNames();
            while (it.hasNext()) {
                String name = (String) it.next();
                toXML(d, name, doc, e);
                serializeEntityAttrs2XML(d, name, doc, e);
            }
        }
        return doc;
    }

    /**
     * @param d
     * @param name
     * @param doc
     * @param parent
     */
    private static void toXML(BizData d, String name, Document doc, Element parent) {
        int count;
        Element newEle;
        String str;
        String field;

        if (d.isSingleValue(name)) {
            Object value = d.get(name);
            if (value instanceof Date) {
                str = BizData.sdfTime.format((Date) value);
            } else {
                str = value.toString();
            }

            newEle = doc.createElement("data");
            newEle.setAttribute("name", name);
            newEle.setAttribute("value", str);
            parent.appendChild(newEle);
        } else if (d.isTable(name)) {
            Vector v = d.getTableFields(name);
            count = v.size();
            String[] rowIDs = d.getRowIDs(name);
            for (int i = 0; i < rowIDs.length; i++) {
                for (int j = 0; j < count; j++) {
                    field = (String) v.get(j);
                    Object value = d.get(name, field, rowIDs[i]);
                    if (value == null) {
                        continue;
                    } else if (value instanceof Date) {
                        str = BizData.sdfTime.format((Date) value);
                    } else {
                        str = value.toString();
                    }

                    newEle = doc.createElement("data");
                    newEle.setAttribute("name", name);
                    newEle.setAttribute("field", field);
                    newEle.setAttribute("row", rowIDs[i]);
                    newEle.setAttribute("value", str);

                    parent.appendChild(newEle);
                }
            }
        } else if (d.isArray(name)) {
            count = d.getArrayLength(name);
            for (int i = 0; i < count; i++) {
                Object value = d.get(name, i);
                if (value instanceof Date) {
                    str = BizData.sdfTime.format((Date) value);
                } else {
                    str = value.toString();
                }

                newEle = doc.createElement("data");
                newEle.setAttribute("name", name);
                newEle.setAttribute("row", Integer.toString(i));
                newEle.setAttribute("value", str);

                parent.appendChild(newEle);
            }
        }
    }

    /**
     * @param d
     * @param name
     * @param doc
     * @param parent
     */
    private static void serializeEntityAttrs2XML(BizData d, String name, Document doc
                                                 , Element parent) {
        int count;
        String str;
        String attrName;
        List l;

        //entity attributes;
        l = d.attrNamesOfEntity(name);
        count = l.size();
        for (int i = 0; i < count; i++) {
            attrName = (String) l.get(i);
            Object value = d.getAttr(name, attrName);
            if (value instanceof Date) {
                str = BizData.sdfTime.format((Date) value);
            } else {
                str = value.toString();
            }

            Element newEle = doc.createElement("data");
            newEle.setAttribute("name", name);
            newEle.setAttribute("attr", attrName);
            newEle.setAttribute("value", str);
            parent.appendChild(newEle);
        }

        //process field and row attributes
        if (d.isTable(name)) {
            //field attributes;
            l = d.attrNamesOfRecordField(name);
            count = l.size();
            for (int i = 0; i < count; i++) {
                String fullName = (String) l.get(i);
                int index1 = fullName.indexOf('/');
                String field = fullName.substring(0, index1);
                int index2 = fullName.lastIndexOf('/');
                String row = fullName.substring(index1, index2);
                attrName = fullName.substring(index2 + 1);

                Object value = d.getAttr(name, field, row, attrName);
                if (value instanceof Date) {
                    str = BizData.sdfTime.format((Date) value);
                } else {
                    str = value.toString();
                }

                Element newEle = doc.createElement("data");
                newEle.setAttribute("name", name);
                newEle.setAttribute("field", field);
                newEle.setAttribute("row", row);
                newEle.setAttribute("attr", attrName);
                newEle.setAttribute("value", str);
                parent.appendChild(newEle);
            }
            //row attributes;
            l = d.attrNamesOfRecordRow(name);
            count = l.size();
            for (int i = 0; i < count; i++) {
                String fullName = (String) l.get(i);
                int index = fullName.indexOf('/');
                String row = fullName.substring(0, index);
                attrName = fullName.substring(index + 1);

                Object value = d.getRowAttr(name, row, attrName);
                if (value instanceof Date) {
                    str = BizData.sdfTime.format((Date) value);
                } else {
                    str = value.toString();
                }

                Element newEle = doc.createElement("data");
                newEle.setAttribute("name", name);
                newEle.setAttribute("row", row);
                newEle.setAttribute("attr", attrName);
                newEle.setAttribute("value", str);
                parent.appendChild(newEle);
            }
        }
    }

    /**
     * @param v
     * @param format
     * @return
     */
    public static String formatValue(Object v, String format) {
        String formatedString = null;
        if (v != null && format != null && format.length() > 0) {
            try {
                if (v instanceof Number) {
                    DecimalFormat nf = new DecimalFormat(format);
                    formatedString = nf.format(v);
                } else if (v instanceof Date) {
                    SimpleDateFormat sdf = new SimpleDateFormat(format);
                    formatedString = sdf.format(v);
                } else {
                    formatedString = v.toString();
                }
            } catch (IllegalArgumentException e) {
                Logger log = Logger.getLogger("com.dream.bizdata");
                log.error("Illegal format string " + format, e);
                formatedString = v.toString();
            }
        } else {
            if (v != null) {
                formatedString = v.toString();
            }
        }
        return formatedString;
    }

    /**
     * @param name
     * @param b
     * @return
     */
    public static BizData loadFromBean(String name, Object b) {
        BizData d = new BizData();
        Class clazz = b.getClass();
        String className = clazz.getName();
        ArrayList al = (ArrayList) getMethods.get(className);
        if (al == null) {
            al = getGetMethods(clazz);
            getMethods.put(className, al);
        }
        int count = al.size();
        for (int i = 0; i < count; i++) {
            PropertyMethod pm = (PropertyMethod) al.get(i);
            Object v = pm.getValue(b);
            d.add(name, pm.getName(), v);
        }
        return d;
    }

    /**
     * @param clazz
     * @return
     */
    private static ArrayList getGetMethods(Class clazz) {
        boolean startWithGet = false;
        ArrayList al = new ArrayList();
        Method[] methods = clazz.getMethods();
        int count = methods.length;

        for (int i = 0; i < count; i++) {
            String methodName = methods[i].getName();
            Class[] paraTypes = methods[i].getParameterTypes();
            if ((paraTypes == null || paraTypes.length == 0) &&
                    (startWithGet = methodName.toLowerCase().startsWith("get")
                    || methodName.toLowerCase().startsWith("is"))) {
                String fieldName = startWithGet ? methodName.substring(3) : methodName.substring(2);
                al.add(new PropertyMethod(fieldName, methods[i]));
            }
        }
        return al;
    }

    /**
     * @param name
     * @param d
     * @param clazz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object saveToBean(String name, BizData d, Class clazz)
            throws IllegalAccessException, InstantiationException {
        return saveToBean(name, "0", d, clazz);
    }

    /**
     * @param name
     * @param d
     * @param clazz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static List saveToBeans(String name, BizData d, Class clazz)
            throws IllegalAccessException, InstantiationException {
        ArrayList al = new ArrayList();
        String[] rowIDs = d.getRowIDs(name);
        for (int i = 0; i < rowIDs.length; i++) {
            al.add(saveToBean(name, rowIDs[i], d, clazz));
        }
        return al;
    }

    /**
     * @param name
     * @param row
     * @param d
     * @param clazz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object saveToBean(String name, String row, BizData d, Class clazz)
            throws IllegalAccessException, InstantiationException {

        String className = clazz.getName();
        ArrayList al = (ArrayList) setMethods.get(className);
        if (al == null) {
            al = getSetMethods(clazz);
            getMethods.put(className, al);
        }

        int count = al.size();

        Object b = clazz.newInstance();
        Object v = null;

        for (int i = 0; i < count; i++) {
            PropertyMethod pm = (PropertyMethod) al.get(i);
            String fieldName = pm.getName();

            Class[] paraTypes = pm.getMethod().getParameterTypes();
            if (paraTypes[0].equals(int.class)) {
                v = new Integer(d.getInt(name, fieldName, row));
            } else if (paraTypes[0].equals(long.class)) {
                v = new Long(d.getLong(name, fieldName, row));
            } else if (paraTypes[0].equals(byte.class)) {
                v = new Byte((byte) d.getInt(name, fieldName, row));
            } else if (paraTypes[0].equals(char.class)) {
                v = new Character(d.getString(name, fieldName, row).charAt(0));
            } else if (paraTypes[0].equals(float.class)) {
                v = new Float((float) d.getDouble(name, fieldName, row));
            } else if (paraTypes[0].equals(double.class)) {
                v = new Double(d.getDouble(name, fieldName, row));
            } else if (paraTypes[0].equals(short.class)) {
                v = new Short(new Integer(d.getInt(name, fieldName, row)).shortValue());
            } else {
                v = d.get(name, fieldName, row);
            }
            pm.setValue(b, v);
        }
        return b;
    }

    /**
     * @param clazz
     * @return
     */
    private static ArrayList getSetMethods(Class clazz) {
        ArrayList al = new ArrayList();

        Method[] methods = clazz.getMethods();
        int count = methods.length;

        for (int i = 0; i < count; i++) {
            String methodName = methods[i].getName();
            Class[] paraTypes = methods[i].getParameterTypes();
            if ((paraTypes != null && paraTypes.length == 1) &&
                    methodName.toLowerCase().startsWith("set")
                    && methods[i].getModifiers() == Modifier.PUBLIC) {
                String fieldName = methodName.substring(3);
                al.add(new PropertyMethod(fieldName, methods[i]));
            }
        }
        return al;
    }

    /**
     * construct a BizData object from a xml document object;
     *
     * @param doc the source xml document object;
     * @return the BizData object; may be null.
     */
    public static BizData fromXML(Document doc) {
        BizData bd = null;
        if (doc != null) {
            Element docRoot = doc.getDocumentElement();
            if ("bizdata".compareTo(docRoot.getNodeName()) == 0) {
                bd = fromXML(docRoot);
                if (bd == null) {
                    bd = new BizData();
                }
            }
        }
        return bd;
    }

    /**
     * Construct a BizData object from the an DOM Element Node;
     *
     * @param root the root Element that contains the "data"
     *             element;
     * @return the constructed BizData object;
     */
    public static BizData fromXML(Element root) {
        BizData bd = null;
        if (root != null) {
            NodeList nl = root.getElementsByTagName("data");
            int size = nl.getLength();
            if (size > 0) {
                bd = new BizData();
                for (int i = 0; i < size; i++) {
                    Element e = (Element) nl.item(i);
                    String name = e.getAttribute("name");
                    if (name.length() > 0) {
                        String field = e.getAttribute("field");
                        String row = e.getAttribute("row");
                        String attr = e.getAttribute("attr");
                        String value = e.getAttribute("value");
                        if (field.length() > 0) {
                            if (row.length() > 0) {
                                if (attr.length() > 0) {
                                    bd.addAttr(name, field, row, attr, value.length() > 0 ? value : null);
                                } else {
                                    bd.add(name, field, row, value.length() > 0 ? value : null);
                                }
                            } else {
                                if (attr.length() > 0) {
                                    bd.addAttr(name, field, attr, value.length() > 0 ? value : null);
                                } else {
                                    bd.add(name, field, value.length() > 0 ? value : null);
                                }
                            }
                        } else {
                            if (row.length() > 0) {
                                if (attr.length() > 0) {
                                    bd.addRowAttr(name, row, attr, value.length() > 0 ? value : null);
                                } else {
                                    bd.add(name, Integer.parseInt(row), value.length() > 0 ? value : null);
                                }
                            } else {
                                if (attr.length() > 0) {
                                    bd.addAttr(name, attr, value.length() > 0 ? value : null);
                                } else {
                                    bd.add(name, value.length() > 0 ? value : null);
                                }
                            }
                        }
                    }
                }
            }
        }
        return bd;
    }
}