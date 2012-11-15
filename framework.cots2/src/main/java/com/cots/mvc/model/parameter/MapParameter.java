package com.cots.mvc.model.parameter;

import com.cots.util.XMLFile;
import com.cots.bean.PrimitiveType;

import java.util.*;
import java.text.ParseException;

import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 *  if a map is created from parameters in a HttpServletRequest, then the keyType and value type
 * can be specified. the key type may be of String or number, can't be date. the value type can
 * be of any primitive type.
 *
 * User: chugh
 * Date: 2004-10-27
 * Time: 20:31:15
 * Version: 1.0
 */
public final class MapParameter extends Parameter {

    private String valueType="String";
    private String keyType="String";

    public MapParameter() {
        type = "java.util.HashMap";
    }

    public MapParameter(String name) {
        this.name = name;
        type = "java.util.HashMap";
    }

    /**
     * @param name
     * @param type
     */
    public MapParameter(String name, String type) {
        this.name = name;
        if (type != null && type.length() > 0) {
            this.type = type;
        } else {
            this.type = "java.util.HashMap";
        }
    }

    /**
     * get the class object of the type;
     *
     * @return the Clas object;
     */
    public Class getTypeClass() {
        if ("java.util.Map".equals(type)) {
            return java.util.Map.class;
        } else if ("java.util.HashMap".equals(type)) {
            return java.util.HashMap.class;
        } else if ("java.util.Hashtable".equals(type)) {
            return java.util.Hashtable.class;
        } else if ("java.util.TreeMap".equals(type)) {
            return java.util.TreeMap.class;
        } else if ("java.util.Hashtable".equals(type)) {
            return java.util.Hashtable.class;
        } else if ("java.util.LinkedHashMap".equals(type)) {
            return java.util.LinkedHashMap.class;
        } else {
            return java.util.HashMap.class;
        }
    }

    /**
     * create a new Object of the type;
     *
     * @return
     */
    public Object create() {
        if ("java.util.Map".equals(type)) {
            return new HashMap();
        } else if ("java.util.HashMap".equals(type)) {
            return new HashMap();
        } else if ("java.util.Hashtable".equals(type)) {
            return new Hashtable();
        } else if ("java.util.TreeMap".equals(type)) {
            return new TreeMap();
        } else if ("java.util.Hashtable".equals(type)) {
            return new Hashtable();
        } else if ("java.util.LinkedHashMap".equals(type)) {
            return new LinkedHashMap();
        } else {
            return new HashMap();
        }
    }

    public Object create(HttpServletRequest request) throws ParseException {
        Map map = (Map) create();
        HashSet names = new HashSet();
        String __tmp = name + "[";
        int __len = __tmp.length();
        Enumeration __names = request.getParameterNames();
        while (__names.hasMoreElements()) {
            String orginalName = (String) __names.nextElement();
            if (orginalName.startsWith(__tmp)) {
                int index = orginalName.indexOf("]");
                if (index > __len) {
                    String trueName = orginalName.substring(__len, index);
                    names.add(PrimitiveType.create(keyType,trueName));
                }
            }
        }

        Iterator __it = names.iterator();
        while (__it.hasNext()) {
            Object key = (Object) __it.next();
            Object __value = PrimitiveType.create(getValueType()
                    , request.getParameter(name + "[" + key + "]"));
            map.put(key, __value);
        }
        return map;
    }

    public Object getValue(HashMap data) {
        return data.get(name);
    }

    /**
     * get the value type;
     *
     * @return
     */
    public String getValueType() {
        return valueType;
    }

    /**
     * save the value type;
     *
     * @param valueType
     */
    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    /**
     * save this parameter.
     *
     * @param holder
     * @param parent
     */
    public void save(XMLFile holder, Element parent) {
        Element param = holder.appendChild(parent, "map");
        this.saveCommonAttrs(param);
        if(valueType!=null && valueType.length()>0){
            param.setAttribute("valueType",valueType);
        }
        if(keyType!=null && keyType.length()>0){
            param.setAttribute("keyType",keyType);
        }
    }
}
