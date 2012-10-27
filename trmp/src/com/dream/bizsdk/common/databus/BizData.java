package com.dream.bizsdk.common.databus;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import java.io.Serializable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.DigestException;
import java.math.BigDecimal;

import com.dream.bizsdk.common.util.NameParser;
import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.util.md.MsgDigest;
import com.dream.bizsdk.common.util.crypto.MsgCipher;
import com.dream.bizsdk.common.database.datadict.Types;

/**
 * Title: engine Description: This is the platform of the business development
 * kit. Copyright: Copyright (c) 2002 Company: dream
 * 
 * @author chuguanghua
 * @version 1.0
 */

public class BizData implements Serializable {
	public void doNothing() {

	}

	// //public Set dd=new HashSet();

	// date format;
	public static String dateFormat = "yyyy-MM-dd";
	// time format;
	public static String timeFormat = "yyyy-MM-dd HH:mm:ss";
	// date formatter;
	public static SimpleDateFormat sdfDate = new SimpleDateFormat(dateFormat);
	// time formatter;
	public static SimpleDateFormat sdfTime = new SimpleDateFormat(timeFormat);
	// the cipher type
	public static String _cipherType = null;
	// the cipher key;
	public static Key _cipherKey = null;
	// the digest type;
	public static String _digestType = null;

	public static final String ENTITY_ATTR = "@E@";
	public static final String FIELD_ATTR = "@F@";
	public static final String ROW_ATTR = "@R@";

	public HashMap data;
	public HashMap sys;
	public HashMap attr;
	public boolean modified = false;

	public Vector tables = null;

	public BizData() {
		data = newMap();
		sys = newMap();
		attr = newMap();
	}

	/**
	 * set the modified flag;
	 * 
	 * @param m
	 *            the new Modified flag;
	 */
	public void setModified(boolean m) {
		modified = m;
	}

	/**
	 * check if this object is modified or not.
	 * 
	 * @return true if this data has been modified, false otherwisw.
	 */
	public boolean isModified() {
		return modified;
	}

	/**
	 * add an Object value as an single entity to this data object. Note that
	 * the name will converted to upper case.
	 * 
	 * @param name
	 *            name of the entity.
	 * @param value
	 *            value of the entity.
	 */
	public void add(String name, Object value) {
		data.put(name.toUpperCase(), value);// captialize the name;
	}

	/**
	 * add an Object value as single entity to this data object;
	 * 
	 * @param name
	 *            the name of the data object;
	 * @param value
	 *            the Object to be added;
	 */
	public void add2(String name, Object value) {
		data.put(name, value);
	}

	/**
	 * add a int value to this data object;
	 * 
	 * @param name
	 *            the name of the int value;
	 * @param value
	 *            the int value to be added;
	 */
	public void add(String name, int value) {
		add2(name.toUpperCase(), new Integer(value));
	}

	/**
	 * add a float value to this data object;
	 * 
	 * @param name
	 *            the name of the value;
	 * @param value
	 *            the float value to be added.
	 */
	public void add(String name, float value) {
		add2(name.toUpperCase(), new Float(value));
	}

	/**
	 * add a double value to this data object;
	 * 
	 * @param name
	 *            the name of the data;
	 * @param value
	 *            the double value to be added;
	 */
	public void add(String name, double value) {
		add2(name.toUpperCase(), new Double(value));
	}

	/**
	 * add a long value to this data object;
	 * 
	 * @param name
	 *            the name of the long value;
	 * @param value
	 *            the long value to be added;
	 */
	public void add(String name, long value) {
		add2(name.toUpperCase(), new Long(value));
	}

	/**
	 * add a short value to this data object;
	 * 
	 * @param name
	 *            the name of the data object;
	 * @param value
	 *            the value to be added.
	 */
	public void add(String name, short value) {
		add2(name.toUpperCase(), new Short(value));
	}

	/**
	 * add a byte value to this data object;
	 * 
	 * @param name
	 *            the name of the value;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, byte value) {
		add2(name.toUpperCase(), new Byte(value));
	}

	/**
	 * add a char value to this data object;
	 * 
	 * @param name
	 *            the name of the data object;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, char value) {
		add2(name.toUpperCase(), new Character(value));
	}

	/**
	 * add a boolean value to this data object;
	 * 
	 * @param name
	 *            the name of the data object;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, boolean value) {
		add2(name.toUpperCase(), new Boolean(value));
	}

	/**
	 * Add an Object value into the Vector type entity.
	 * <p/>
	 * If there already exists an entity in the buffer and is not of type Vector
	 * type entity, then the old entity is discarded and a new Vector type
	 * entity is created, the new value will appear as the first value in the
	 * entity.
	 * <p/>
	 * If the index is larger than the current size of the Vector type
	 * entity,then an ArrayIndexOutOfBound exception is thrown.
	 * 
	 * @param name
	 *            name of the Vector-type entity
	 * @param index
	 *            index of the new value;
	 * @param value
	 *            value to be added to the buffer;
	 */
	public void add(String name, int index, Object value) {
		add2(name.toUpperCase(), index, value);
	}

	public void add2(String name, int index, Object value) {
		Object vals = data.get(name);
		if (null != vals && vals instanceof java.util.ArrayList) {
			((ArrayList) vals).add(index, value);
		} else {
			ArrayList newVecotr = new ArrayList();
			newVecotr.add(value);
			data.put(name, newVecotr);
		}
	}

	/**
	 * add a int value to an array;
	 * 
	 * @param name
	 *            the name of the array;
	 * @param index
	 *            the index to add the new Value;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, int index, int value) {
		add2(name.toUpperCase(), index, new Integer(value));
	}

	/**
	 * add a float value to an array;
	 * 
	 * @param name
	 *            the name of the array;
	 * @param index
	 *            the index to add the new Value;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, int index, float value) {
		add2(name.toUpperCase(), index, new Float(value));
	}

	/**
	 * add a double value to an array;
	 * 
	 * @param name
	 *            the name of the array;
	 * @param index
	 *            the index to add the new Value;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, int index, double value) {
		add2(name.toUpperCase(), index, new Double(value));
	}

	/**
	 * add a long value to an array;
	 * 
	 * @param name
	 *            the name of the array;
	 * @param index
	 *            the index to add the new Value;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, int index, long value) {
		add2(name.toUpperCase(), index, new Long(value));
	}

	/**
	 * add a short value to an array;
	 * 
	 * @param name
	 *            the name of the array;
	 * @param index
	 *            the index to add the new Value;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, int index, short value) {
		add2(name.toUpperCase(), index, new Short(value));
	}

	/**
	 * add a byte value to an array;
	 * 
	 * @param name
	 *            the name of the array;
	 * @param index
	 *            the index to add the new Value;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, int index, byte value) {
		add2(name.toUpperCase(), index, new Byte(value));
	}

	/**
	 * add a char value to an array;
	 * 
	 * @param name
	 *            the name of the array;
	 * @param index
	 *            the index to add the new Value;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, int index, char value) {
		add2(name.toUpperCase(), index, new Character(value));
	}

	/**
	 * add a booelan value to an array;
	 * 
	 * @param name
	 *            the name of the array;
	 * @param index
	 *            the index to add the new Value;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, int index, boolean value) {
		add2(name.toUpperCase(), index, new Boolean(value));
	}

	/**
	 * Add a value into a two-dimension-array entity in the buffer.
	 * <p/>
	 * If there exists an entity for the name and this entity is not of Vector
	 * type then the old entity is replaceed by a newly created Vector object,
	 * and the value will appear as the [0,0] element in the array;
	 * <p/>
	 * If this entity does not exist, then a new Vector object is created and
	 * the value will appear as the [0,0] elemnt in the array;
	 * <p/>
	 * Both the row index and the column index start from 0. if the row is
	 * larger than the count of the rows then an ArrayIndexOutOfBoundException
	 * is thrown, if the column is larger the count values in the row specified
	 * by the parameter row, then also an ArrayIndexOutOfBoundException is
	 * thrown.
	 * 
	 * @param name
	 *            name of the entity;
	 * @param row
	 *            row index,start form 0
	 * @param column
	 *            column index start form 0
	 * @param value
	 *            value to be added;
	 */
	public void add(String name, int row, int column, Object value) {
		add2(name.toUpperCase(), row, column, value);
	}

	public void add2(String name, int row, int column, Object value) {
		Object vals = data.get(name);
		if (null != vals && vals instanceof java.util.ArrayList) {
			if (row == ((ArrayList) vals).size()) {
				ArrayList rowValue = new ArrayList();
				rowValue.add(column, value);
				((ArrayList) vals).add(row, rowValue);
				return;
			}
			Object rowValue = ((ArrayList) vals).get(row);
			if (null != rowValue && rowValue instanceof java.util.ArrayList) {
				((ArrayList) rowValue).add(column, value);
			} else {
				rowValue = new ArrayList();
				((ArrayList) rowValue).add(value);
				((ArrayList) vals).add(row, rowValue);
			}
		} else {
			ArrayList entity = new ArrayList();
			ArrayList newField = new ArrayList();

			newField.add(value);
			entity.add(newField);
			data.put(name, entity);
		}
	}

	/**
	 * add a int value to a 2d array;
	 * 
	 * @param name
	 *            the name of the 2d array;
	 * @param row
	 *            the row index to add the value;
	 * @param col
	 *            the column index to add the value;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, int row, int col, int value) {
		add2(name.toUpperCase(), row, col, new Integer(value));
	}

	/**
	 * add a float value to a 2d array;
	 * 
	 * @param name
	 *            the name of the 2d array;
	 * @param row
	 *            the row index to add the value;
	 * @param col
	 *            the column index to add the value;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, int row, int col, float value) {
		add2(name.toUpperCase(), row, col, new Float(value));
	}

	/**
	 * add a double value to a 2d array;
	 * 
	 * @param name
	 *            the name of the 2d array;
	 * @param row
	 *            the row index to add the value;
	 * @param col
	 *            the column index to add the value;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, int row, int col, double value) {
		add2(name.toUpperCase(), row, col, new Double(value));
	}

	/**
	 * add a long value to a 2d array;
	 * 
	 * @param name
	 *            the name of the 2d array;
	 * @param row
	 *            the row index to add the value;
	 * @param col
	 *            the column index to add the value;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, int row, int col, long value) {
		add2(name.toUpperCase(), row, col, new Long(value));
	}

	/**
	 * add a short value to a 2d array;
	 * 
	 * @param name
	 *            the name of the 2d array;
	 * @param row
	 *            the row index to add the value;
	 * @param col
	 *            the column index to add the value;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, int row, int col, short value) {
		add2(name.toUpperCase(), row, col, new Short(value));
	}

	/**
	 * add a byte value to a 2d array;
	 * 
	 * @param name
	 *            the name of the 2d array;
	 * @param row
	 *            the row index to add the value;
	 * @param col
	 *            the column index to add the value;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, int row, int col, byte value) {
		add2(name.toUpperCase(), row, col, new Byte(value));
	}

	/**
	 * add a char value to a 2d array;
	 * 
	 * @param name
	 *            the name of the 2d array;
	 * @param row
	 *            the row index to add the value;
	 * @param col
	 *            the column index to add the value;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, int row, int col, char value) {
		add2(name.toUpperCase(), row, col, new Character(value));
	}

	/**
	 * add a boolean value to a 2d array;
	 * 
	 * @param name
	 *            the name of the 2d array;
	 * @param row
	 *            the row index to add the value;
	 * @param col
	 *            the column index to add the value;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, int row, int col, boolean value) {
		add2(name.toUpperCase(), row, col, new Boolean(value));
	}

	/**
	 * Add a field value to a one-row-table-type entity. If the value is of java
	 * premitive types, such as int,short,float,double as so on, then
	 * corresponding Object type is added instead, for example,Integer for
	 * int,Double for double.
	 * <p/>
	 * If the specified field already exists, then the old value will be
	 * replaced by the new value.
	 * <p/>
	 * If the specified field does not exist, then a new field will be created
	 * in the entity and its value is set with the supplied value.
	 * 
	 * @param name
	 *            name of the one-row-table-type entity;
	 * @param field
	 *            field in the entity;
	 * @param value
	 *            value of the field;
	 */
	public void add(String name, String field, Object value) {
		add2(name.toUpperCase(), field.toUpperCase(), "0", value);
	}

	public void add2(String name, String field, Object value) {
		add2(name, field, "0", value);
	}

	/**
	 * add a field value to a table type entity.
	 * 
	 * @param name
	 *            the name of the table entity;
	 * @param field
	 *            the name of the field;
	 * @param index
	 *            the index of the field;
	 * @param value
	 *            the value of the field;
	 */
	public void add(String name, String field, String index, Object value) {
		add2(name.toUpperCase(), field.toUpperCase(), index, value);
	}

	/**
	 * add a field value to a table type entity.
	 * 
	 * @param name
	 *            the name of the table entity;
	 * @param field
	 *            the name of the field;
	 * @param index
	 *            the index of the field;
	 * @param value
	 *            the value of the field;
	 */
	public void add2(String name, String field, String index, Object value) {
		// add the index to the table field indexes.

		Set hs = (Set) sys.get("_HS_" + name);

		if (hs == null) {
			hs = newSet();
			sys.put("_HS_" + name, hs);
		}
		hs.add(index);

		// get the table object, the field object
		Object vals = data.get(name);
		if (null != vals && vals instanceof java.util.Map) {
			Map fieldObject = (Map) vals;
			Object fieldValue = fieldObject.get(field);
			if (null != fieldValue && fieldValue instanceof java.util.Map) {
				((Map) fieldValue).put(index, value);
			} else {
				fieldValue = newMap();
				((Map) fieldValue).put(index, value);
				fieldObject.put(field, fieldValue);
			}
		} else {
			Map entity = newMap();
			Map newField = newMap();

			newField.put(index, value);
			entity.put(field, newField);
			data.put(name, entity);
		}
	}

	/**
	 * add a int vlaue to a field in a record type entity.
	 * 
	 * @param name
	 *            the name of the record;
	 * @param field
	 *            the name of the field in the record;
	 * @param index
	 *            the row index;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, String field, String index, int value) {
		add2(name.toUpperCase(), field.toUpperCase(), index, new Integer(value));
	}

	/**
	 * add a float vlaue to a field in a record type entity.
	 * 
	 * @param name
	 *            the name of the record;
	 * @param field
	 *            the name of the field in the record;
	 * @param index
	 *            the row index;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, String field, String index, float value) {
		add2(name.toUpperCase(), field.toUpperCase(), index, new Float(value));
	}

	/**
	 * add a double vlaue to a field in a record type entity.
	 * 
	 * @param name
	 *            the name of the record;
	 * @param field
	 *            the name of the field in the record;
	 * @param index
	 *            the row index;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, String field, String index, double value) {
		add2(name.toUpperCase(), field.toUpperCase(), index, new Double(value));
	}

	/**
	 * add a long vlaue to a field in a record type entity.
	 * 
	 * @param name
	 *            the name of the record;
	 * @param field
	 *            the name of the field in the record;
	 * @param index
	 *            the row index;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, String field, String index, long value) {
		add2(name.toUpperCase(), field.toUpperCase(), index, new Long(value));
	}

	/**
	 * add a short vlaue to a field in a record type entity.
	 * 
	 * @param name
	 *            the name of the record;
	 * @param field
	 *            the name of the field in the record;
	 * @param index
	 *            the row index;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, String field, String index, short value) {
		add2(name.toUpperCase(), field.toUpperCase(), index, new Short(value));
	}

	/**
	 * add a byte vlaue to a field in a record type entity.
	 * 
	 * @param name
	 *            the name of the record;
	 * @param field
	 *            the name of the field in the record;
	 * @param index
	 *            the row index;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, String field, String index, byte value) {
		add2(name.toUpperCase(), field.toUpperCase(), index, new Byte(value));
	}

	/**
	 * add a char vlaue to a field in a record type entity.
	 * 
	 * @param name
	 *            the name of the record;
	 * @param field
	 *            the name of the field in the record;
	 * @param index
	 *            the row index;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, String field, String index, char value) {
		add2(name.toUpperCase(), field.toUpperCase(), index, new Character(
				value));
	}

	/**
	 * add a boolean vlaue to a field in a record type entity.
	 * 
	 * @param name
	 *            the name of the record;
	 * @param field
	 *            the name of the field in the record;
	 * @param index
	 *            the row index;
	 * @param value
	 *            the value to be added;
	 */
	public void add(String name, String field, String index, boolean value) {
		add2(name.toUpperCase(), field.toUpperCase(), index, new Boolean(value));
	}

	public void add2(String name, String field, int index, Object value) {
		add2(name, field, new Integer(index).toString(), value);
	}

	public void add(String name, String field, int index, int value) {
		add2(name.toUpperCase(), field.toUpperCase(), index, new Integer(value));
	}

	public void add(String name, String field, int index, float value) {
		add2(name.toUpperCase(), field.toUpperCase(), index, new Float(value));
	}

	public void add(String name, String field, int index, double value) {
		add2(name.toUpperCase(), field.toUpperCase(), index, new Double(value));
	}

	public void add(String name, String field, int index, long value) {
		add2(name.toUpperCase(), field.toUpperCase(), index, new Long(value));
	}

	public void add(String name, String field, int index, short value) {
		add2(name.toUpperCase(), field.toUpperCase(), index, new Short(value));
	}

	public void add(String name, String field, int index, byte value) {
		add2(name.toUpperCase(), field.toUpperCase(), index, new Byte(value));
	}

	public void add(String name, String field, int index, char value) {
		add2(name.toUpperCase(), field.toUpperCase(), index, new Character(
				value));
	}

	public void add(String name, String field, int index, boolean value) {
		add2(name.toUpperCase(), field.toUpperCase(), index, new Boolean(value));
	}

	/**
	 * Add an attribute to an entity, This is attribute is of object type. This
	 * new attribute will overwrite the old one, if existing.
	 * 
	 * @param name
	 * @param attrName
	 * @param value
	 */
	public void addAttr2(String name, String attrName, Object value) {
		// get all the entities of the entity,should be a Map object,in the
		// following we call this Map as 'attributes' object;
		Object attrsOfEntity = attr.get(name);
		if (null != attrsOfEntity && attrsOfEntity instanceof java.util.Map) {
			Object entityAttrs = ((Map) attrsOfEntity).get(BizData.ENTITY_ATTR);
			if (entityAttrs != null && entityAttrs instanceof java.util.Map) {
				((Map) entityAttrs).put(attrName, value);
			} else {
				Map newMap = newMap();
				newMap.put(attrName, value);
				((Map) attrsOfEntity).put(BizData.ENTITY_ATTR, newMap);
			}
		} else {
			// currently this object has no attributes or the 'attributes'
			// object is
			// not a Map object,so we will create a new Map object as
			// 'attributes' object for this entity and add the new attribute to
			// the
			// 'attributes' object;
			attrsOfEntity = newMap();
			Map newMap = newMap();
			newMap.put(attrName, value);
			((Map) attrsOfEntity).put(BizData.ENTITY_ATTR, newMap);
			attr.put(name, attrsOfEntity);
		}
	}

	public void addAttr(String name, String attrName, Object value) {
		addAttr2(name.toUpperCase(), attrName.toUpperCase(), value);
	}

	public void addAttr(String name, String attrName, int value) {
		addAttr2(name.toUpperCase(), attrName.toUpperCase(), new Integer(value));
	}

	public void addAttr(String name, String attrName, long value) {
		addAttr2(name.toUpperCase(), attrName.toUpperCase(), new Long(value));
	}

	public void addAttr(String name, String attrName, short value) {
		addAttr2(name.toUpperCase(), attrName.toUpperCase(), new Short(value));
	}

	public void addAttr(String name, String attrName, float value) {
		addAttr2(name.toUpperCase(), attrName.toUpperCase(), new Float(value));
	}

	public void addAttr(String name, String attrName, double value) {
		addAttr2(name.toUpperCase(), attrName.toUpperCase(), new Double(value));
	}

	public void addAttr(String name, String attrName, byte value) {
		addAttr2(name.toUpperCase(), attrName.toUpperCase(), new Byte(value));
	}

	public void addAttr(String name, String attrName, char value) {
		addAttr2(name.toUpperCase(), attrName.toUpperCase(), new Character(
				value));
	}

	public void addAttr(String name, String attrName, boolean value) {
		addAttr2(name.toUpperCase(), attrName.toUpperCase(), new Boolean(value));
	}

	/**
	 * Add attribute to a field,at specifi index, of a table entity;
	 * 
	 * @param name
	 *            table entity name;
	 * @param field
	 *            -field name in the table entity;
	 * @param index
	 *            -field index;
	 * @param attrName
	 *            -name of the attribute;
	 * @param value
	 *            -value of the attribute;
	 */
	public void addAttr2(String name, String field, String index,
			String attrName, Object value) {
		/** check if this values exists,if not,set it to null value */
		if (get(name, field, index) == null) {
			add(name, field, index, "");
		}

		/* get entity's attributes map */
		Object attrsOfEntity = attr.get(name);
		if (null != attrsOfEntity && attrsOfEntity instanceof java.util.Map) {
			// get field attributes map;
			Object attrOfFields = ((Map) attrsOfEntity).get(BizData.FIELD_ATTR);
			if (attrOfFields != null && attrOfFields instanceof java.util.Map) {
				// add the new attribyte to the field attributes' map;
				((Map) attrOfFields).put(field + "/" + index + "/" + attrName,
						value);
			} else {
				/*
				 * create the new field attribytes map,set the new attr to the
				 * map; and set the new field attributes to the entity's
				 * attributes map
				 */
				Map newMap = newMap();
				newMap.put(field + "/" + index + "/" + attrName, value);
				((Map) attrsOfEntity).put(BizData.FIELD_ATTR, newMap);
			}
		} else {
			/* the entity's attributes doest not exist */
			attrsOfEntity = newMap();
			Map newMap = newMap();
			newMap.put(field + "/" + index + "/" + attrName, value);
			((Map) attrsOfEntity).put(BizData.FIELD_ATTR, newMap);
			attr.put(name, attrsOfEntity);
		}
	}

	public void addAttr(String name, String field, String index,
			String attrName, Object value) {
		addAttr2(name.toUpperCase(), field.toUpperCase(), index, attrName
				.toUpperCase(), value);
	}

	public void addAttr(String name, String field, String index,
			String attrName, int value) {
		addAttr2(name.toUpperCase(), field.toUpperCase(), index, attrName
				.toUpperCase(), new Integer(value));
	}

	public void addAttr(String name, String field, String index,
			String attrName, long value) {
		addAttr2(name.toUpperCase(), field.toUpperCase(), index, attrName
				.toUpperCase(), new Long(value));
	}

	public void addAttr(String name, String field, String index,
			String attrName, short value) {
		addAttr2(name.toUpperCase(), field.toUpperCase(), index, attrName
				.toUpperCase(), new Short(value));
	}

	public void addAttr(String name, String field, String index,
			String attrName, float value) {
		addAttr2(name.toUpperCase(), field.toUpperCase(), index, attrName
				.toUpperCase(), new Float(value));
	}

	public void addAttr(String name, String field, String index,
			String attrName, double value) {
		addAttr2(name.toUpperCase(), field.toUpperCase(), index, attrName
				.toUpperCase(), new Double(value));
	}

	public void addAttr(String name, String field, String index,
			String attrName, byte value) {
		addAttr2(name.toUpperCase(), field.toUpperCase(), index, attrName
				.toUpperCase(), new Byte(value));
	}

	public void addAttr(String name, String field, String index,
			String attrName, char value) {
		addAttr2(name.toUpperCase(), field.toUpperCase(), index, attrName
				.toUpperCase(), new Character(value));
	}

	public void addAttr(String name, String field, String index,
			String attrName, boolean value) {
		addAttr2(name.toUpperCase(), field.toUpperCase(), index, attrName
				.toUpperCase(), new Boolean(value));
	}

	/**
	 * This method add an attribute to a row of a table entity. Note the
	 * attribute will always be added even if the row does not exist.
	 * 
	 * @param name
	 *            the table entity name;
	 * @param rowNum
	 *            the row id;
	 * @param attrName
	 *            the name of the attribute;
	 */
	public void addRowAttr2(String name, String rowNum, String attrName,
			Object value) {
		/** set the attribute value */
		Object attrsOfEntity = attr.get(name);
		if (null != attrsOfEntity && attrsOfEntity instanceof java.util.Map) {
			Object rowAttrs = ((Map) attrsOfEntity).get(BizData.ROW_ATTR);
			if (rowAttrs != null && rowAttrs instanceof java.util.Map) {
				((Map) rowAttrs).put(rowNum + "/" + attrName, value);
			} else {
				Map newMap = newMap();
				newMap.put(rowNum + "/" + attrName, value);
				((Map) attrsOfEntity).put(BizData.ROW_ATTR, newMap);
			}
		} else {
			attrsOfEntity = newMap();
			Map newMap = newMap();
			newMap.put(rowNum + "/" + attrName, value);
			((Map) attrsOfEntity).put(BizData.ROW_ATTR, newMap);
			attr.put(name, attrsOfEntity);
		}
	}

	public void addRowAttr(String name, String rowNum, String attrName,
			Object value) {
		addRowAttr2(name.toUpperCase(), rowNum, attrName.toUpperCase(), value);
	}

	public void addRowAttr(String name, String rowNum, String attrName,
			int value) {
		addRowAttr2(name.toUpperCase(), rowNum, attrName.toUpperCase(),
				new Integer(value));
	}

	public void addRowAttr(String name, String rowNum, String attrName,
			long value) {
		addRowAttr2(name.toUpperCase(), rowNum, attrName.toUpperCase(),
				new Long(value));
	}

	public void addRowAttr(String name, String rowNum, String attrName,
			byte value) {
		addRowAttr2(name.toUpperCase(), rowNum, attrName.toUpperCase(),
				new Byte(value));
	}

	public void addRowAttr(String name, String rowNum, String attrName,
			short value) {
		addRowAttr2(name.toUpperCase(), rowNum, attrName.toUpperCase(),
				new Short(value));
	}

	public void addRowAttr(String name, String rowNum, String attrName,
			char value) {
		addRowAttr2(name.toUpperCase(), rowNum, attrName.toUpperCase(),
				new Character(value));
	}

	public void addRowAttr(String name, String rowNum, String attrName,
			float value) {
		addRowAttr2(name.toUpperCase(), rowNum, attrName.toUpperCase(),
				new Float(value));
	}

	public void addRowAttr(String name, String rowNum, String attrName,
			double value) {
		addRowAttr2(name.toUpperCase(), rowNum, attrName.toUpperCase(),
				new Double(value));
	}

	public void addRowAttr(String name, String rowNum, String attrName,
			boolean value) {
		addRowAttr2(name.toUpperCase(), rowNum, attrName.toUpperCase(),
				new Boolean(value));
	}

	/**
	 * get an attribute value of a row in a table enttiy;
	 * 
	 * @return the object value of the attribute;
	 */
	public Object getRowAttr(String name, String rowNum, String attrName) {
		// capitalize the name and the field;
		String cName = name.toUpperCase();

		/** set the attribute value */
		Object attrsOfEntity = attr.get(cName);
		if (null != attrsOfEntity && attrsOfEntity instanceof java.util.Map) {
			Object o = ((Map) attrsOfEntity).get(BizData.ROW_ATTR);
			if (o != null && o instanceof java.util.Map) {
				return ((Map) o).get(rowNum + "/" + attrName.toUpperCase());
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * Add a field value to a table type entity. This will call the add(String
	 * name,String field,String index Object value instead);
	 * 
	 * @param name
	 *            name of the one-row-table-type entity;
	 * @param field
	 *            field in the entity;
	 * @param value
	 *            value of the field;
	 */
	public void add(String name, String field, int index, Object value) {
		add2(name.toUpperCase(), field.toUpperCase(), new Integer(index)
				.toString(), value);
	}

	/**
	 * Add an attribute to an entity field,this entity is of table type.
	 * 
	 * @param name
	 * @param field
	 * @param attrName
	 * @param value
	 */
	public void addAttr2(String name, String field, String attrName,
			Object value) {
		/* get entity's attributes map */
		Object attrsOfEntity = attr.get(name);
		if (null != attrsOfEntity && attrsOfEntity instanceof java.util.Map) {
			// get field attributes map;
			Object attrOfFields = ((Map) attrsOfEntity).get(BizData.FIELD_ATTR);
			if (attrOfFields != null && attrOfFields instanceof java.util.Map) {
				// add the new attribyte to the field attributes' map;
				((Map) attrOfFields).put(field + "/" + attrName, value);
			} else {
				/*
				 * create the new field attribytes map,set the new attr to the
				 * map; and set the new field attributes to the entity's
				 * attributes map
				 */
				Map newMap = newMap();
				newMap.put(field + "/" + attrName, value);
				((Map) attrsOfEntity).put(BizData.FIELD_ATTR, newMap);
			}
		} else {
			/* the entity's attributes doest not exist */
			attrsOfEntity = newMap();
			Map newMap = newMap();
			newMap.put(field + "/" + attrName, value);
			((Map) attrsOfEntity).put(BizData.FIELD_ATTR, newMap);
			attr.put(name, attrsOfEntity);
		}
	}

	public void addAttr(String name, String field, String attrName, Object value) {
		addAttr2(name.toUpperCase(), field.toUpperCase(), attrName
				.toUpperCase(), value);
	}

	public void addAttr(String name, String field, String attrName, int value) {
		addAttr2(name.toUpperCase(), field.toUpperCase(), attrName
				.toUpperCase(), new Integer(value));
	}

	public void addAttr(String name, String field, String attrName, long value) {
		addAttr2(name.toUpperCase(), field.toUpperCase(), attrName
				.toUpperCase(), new Long(value));
	}

	public void addAttr(String name, String field, String attrName, short value) {
		addAttr2(name.toUpperCase(), field.toUpperCase(), attrName
				.toUpperCase(), new Short(value));
	}

	public void addAttr(String name, String field, String attrName, float value) {
		addAttr2(name.toUpperCase(), field.toUpperCase(), attrName
				.toUpperCase(), new Float(value));
	}

	public void addAttr(String name, String field, String attrName, double value) {
		addAttr2(name.toUpperCase(), field.toUpperCase(), attrName
				.toUpperCase(), new Double(value));
	}

	public void addAttr(String name, String field, String attrName, byte value) {
		addAttr2(name.toUpperCase(), field.toUpperCase(), attrName
				.toUpperCase(), new Byte(value));
	}

	public void addAttr(String name, String field, String attrName, char value) {
		addAttr2(name.toUpperCase(), field.toUpperCase(), attrName
				.toUpperCase(), new Character(value));
	}

	public void addAttr(String name, String field, String attrName,
			boolean value) {
		addAttr2(name.toUpperCase(), field.toUpperCase(), attrName
				.toUpperCase(), new Boolean(value));
	}

	public void addAttr(String name, String field, int index, String attrName,
			Object value) {
		addAttr2(name.toUpperCase(), field.toUpperCase(), new Integer(index)
				.toString(), attrName.toUpperCase(), value);
	}

	public void addAttr2(String name, String field, int index, String attrName,
			Object value) {
		addAttr2(name, field, new Integer(index).toString(), attrName, value);
	}

	/**
	 * get int value from an object. Only the
	 * Byte,Short,Integer,Long,Float,Double Date type ojbect can be converted to
	 * int value. Note if the value is a Date value, then the getTime() is
	 * called on the object, and the value returned by getTime() is returned.
	 * 
	 * @param value
	 * @return
	 */
	public int getIntFromObject(String name, Object value) {
		if (null == value) {
			return 0;
		}
		if (value instanceof Integer) {
			return ((Integer) value).intValue();
		}
		if (value instanceof Short) {
			return ((Short) value).shortValue();
		}
		if (value instanceof Long) {
			return ((Long) value).intValue();
		}
		if (value instanceof Double) {
			return ((Double) value).intValue();
		}
		if (value instanceof Byte) {
			return ((Byte) value).byteValue();
		}
		if (value instanceof Float) {
			return ((Float) value).intValue();
		}
		if (value instanceof BigDecimal) {
			return ((BigDecimal) value).intValue();
		}
		if (value instanceof BigDecimal) {
			return ((BigDecimal) value).intValue();
		}

		if (value instanceof String) {
			try {
				return Integer.valueOf((String) value).intValue();
			} catch (NumberFormatException nfe) {
			}
		}
		throw new DataTypeException(name, "int");
	}

	/**
	 * Get a double value from an object.
	 * 
	 * @param value
	 * @return double value associated with the object.
	 */
	public double getDoubleFromObject(String name, Object value) {
		if (null == value) {
			return 0;
		}

		if (value instanceof Integer) {
			return ((Integer) value).doubleValue();
		}
		if (value instanceof Short) {
			return ((Short) value).doubleValue();
		}
		if (value instanceof Long) {
			return ((Long) value).doubleValue();
		}
		if (value instanceof Double) {
			return ((Double) value).doubleValue();
		}
		if (value instanceof Byte) {
			return ((Byte) value).byteValue();
		}
		if (value instanceof Float) {
			return ((Float) value).doubleValue();
		}
		if (value instanceof String) {
			try {
				return Double.valueOf((String) value).intValue();
			} catch (NumberFormatException nfe) {
			}
		}
		throw new DataTypeException(name, "double");
	}

	public String getStringFromObject(Object value) {
		return getStringFromObject(null, value);
	}

	/**
	 * Get a string from object. note if the Object is of type Date, the value
	 * will be fomatted accoding to the dateformat memeber variables.
	 * 
	 * @param value
	 * @return
	 */
	public String getStringFromObject(String name, Object value) {
		try {
			if (null == value) {
				return new String("");
			}
			if (value instanceof java.sql.Timestamp) {
				return BizData.sdfTime.format((java.sql.Timestamp) value);
			}
			if (value instanceof java.util.Date) {
				return BizData.sdfDate.format((Date) value);
			}
			if (value instanceof java.sql.Date) {
				return BizData.sdfDate.format((java.sql.Date) value);
			}
			if (value instanceof java.sql.Time) {
				return BizData.sdfDate.format((java.sql.Time) value);
			}
			
			return value.toString();
		} catch (Exception e) {
			return new String("");
		}
	}

	public long getLongFromObject(String name, Object value) {
		if (null == value) {
			return 0;
		}
		if (value instanceof Integer) {
			return ((Integer) value).longValue();
		}
		if (value instanceof Short) {
			return ((Short) value).longValue();
		}
		if (value instanceof Long) {
			return ((Long) value).longValue();
		}
		if (value instanceof Double) {
			return ((Double) value).longValue();
		}
		if (value instanceof Byte) {
			return ((Byte) value).longValue();
		}
		if (value instanceof Float) {
			return ((Float) value).longValue();
		}
		if (value instanceof String) {
			try {
				return Long.valueOf((String) value).longValue();
			} catch (NumberFormatException nfe) {
			}
		}
		throw new DataTypeException(name, "long");
	}

	public Date getDateFromObject(String name, Object value) {
		if (null == value) {
			return null;
		}

		if (value instanceof String) {
			try {
				return sdfDate.parse((String) value);
			} catch (ParseException e) {
				throw new DataTypeException(name, "date");
			}
		}
		if (value instanceof java.util.Date) {
			return (java.util.Date) value;
		}
		if (value instanceof java.sql.Date) {
			return (java.sql.Date) value;
		}
		if (value instanceof java.sql.Time) {
			return (java.sql.Time) value;
		}
		if (value instanceof java.sql.Time) {
			return (java.sql.Time) value;
		}
		if (value instanceof java.sql.Timestamp) {
			return (java.sql.Timestamp) value;
		}
		throw new DataTypeException(name, "date");
	}

	/**
	 * remove a field from a table entity,if the specified table entity or the
	 * field is not found, then this remove operation has no affect on the
	 * buufer. This method has no affection on other type entities;
	 * 
	 * @param entity
	 *            name of the specified entity;
	 * @param field
	 *            name of the field to remove from the table entity;
	 */
	public void remove(String entity, String field) {
		try {
			Object entityObj = data.get(entity.toUpperCase());
			if (null != entityObj && entityObj instanceof java.util.Map) {
				((Map) entityObj).remove(field.toUpperCase());
			}
		} catch (Exception e) {
		}
	}

	/**
	 * remove a value from a field.
	 * 
	 * @param entity
	 *            the name of the table name;
	 * @param field
	 *            the name of the field in the table object;
	 * @param index
	 *            the index of the value in the field to be removed.
	 * @return true if a value is removed,false no value is removed.
	 */
	public boolean remove(String entity, String field, String index) {
		String cName = entity.toUpperCase();
		String cField = field.toUpperCase();
		Set hs = (Set) sys.get("_HS_" + cName);
		if (hs != null && hs.contains(index)) {
			try {
				Map entityO = (Map) get(cName);
				Map fieldO = (Map) entityO.get(cField);
				Object o = fieldO.remove(index);
				if (o != null) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
			}
		}
		return false;
	}

	/**
	 * Reomve a value from a field in a table type entity; This method has no
	 * affection on other type entities;
	 * 
	 * @param entity
	 *            name of the target entity;
	 * @param field
	 *            name of the field;
	 * @param index
	 *            index of the value to be removed;
	 */
	public boolean remove(String entity, String field, int index) {
		return remove(entity, field, new Integer(index).toString());
	}

	/**
	 * !!!!!!!PROBLEM!!!!!!! Remove a value from a vector type entity in the
	 * buffer; If the current entity is of table type, then this method will
	 * remove a record at the specified index;
	 * 
	 * @param entity
	 *            name of the target entity;
	 * @param index
	 *            index of the value to be removed;
	 */
	public void remove(String entity, int index) {
		String cName = entity.toUpperCase();
		Object entityObj = data.get(cName);
		if (null != entityObj) {
			if (entityObj instanceof java.util.Vector) {
				try {
					((Vector) entityObj).remove(index);
				} catch (Exception e) {
				}
			} else if (entityObj instanceof java.util.Map) {
				try {
					Map entity2 = (Map) entityObj;
					Iterator it = entity2.keySet().iterator();
					while (it.hasNext()) {
						String fieldName = (String) it.next();
						remove(entity, fieldName, index);
					}
					Set hs = (Set) sys.get("_HS_" + cName);
					hs.remove(new Integer(index).toString());
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * Get the date format string of this dao object;
	 * 
	 * @return
	 */
	public static String getDateFormat() {
		return new String(dateFormat);
	}

	/**
	 * Get the date format string of this dao object;
	 * 
	 * @return
	 */
	public static String getTimeFormat() {
		return new String(timeFormat);
	}

	/**
	 * reomve an entity from the buffer. The entity may be a single-value
	 * entity, vector entity, two-dimension array entity or table entity.
	 * 
	 * @param entity
	 *            name of the entity, if the specified entity is not found,then
	 *            this operation has no affect on the buffer.
	 */
	public void remove(String entity) {
		String cName = entity.toUpperCase();
		// remove field index;
		sys.remove("_HS_" + cName);
		// remove data;
		data.remove(cName);
		// remove attributes;
		attr.remove(cName);
	}

	/**
	 * clear all the entities in the buffer;
	 */
	public void clear() {
		// clear data;
		data.clear();
		// clear attributes;
		attr.clear();
		// clear field indexs;
		sys.clear();
	}

	/**
	 * remove an entity attribute;
	 * 
	 * @param name
	 * @param attrName
	 */
	public void removeAttr(String name, String attrName) {
		try {
			// get entity's attribytes map;
			Object entityAttr = attr.get(name.toUpperCase());
			if (null != entityAttr && entityAttr instanceof java.util.Map) {
				Map m = (Map) ((Map) entityAttr).get(BizData.ENTITY_ATTR);
				m.remove(attrName.toUpperCase());
			}
		} catch (Exception e) {
		}
	}

	/**
	 * remove an attribute from a index;
	 * 
	 * @param name
	 * @param fieldName
	 * @param attrName
	 */
	public void removeAttr(String name, String fieldName, String attrName) {
		try {
			Object entityAttr = attr.get(name.toUpperCase());
			if (null != entityAttr && entityAttr instanceof java.util.Map) {
				Map m = (Map) ((Map) entityAttr).get(BizData.FIELD_ATTR);
				m
						.remove(fieldName.toUpperCase() + "/"
								+ attrName.toUpperCase());
			}
		} catch (Exception e) {
		}
	}

	/**
	 * @param name
	 * @param fieldName
	 * @param index
	 * @param attrName
	 */
	public void removeAttr(String name, String fieldName, String index,
			String attrName) {
		try {
			Object entityAttr = attr.get(name.toUpperCase());
			if (null != entityAttr && entityAttr instanceof java.util.Map) {
				Map m = (Map) ((Map) entityAttr).get(BizData.FIELD_ATTR);
				m.remove(fieldName.toUpperCase() + "/" + index + "/"
						+ attrName.toUpperCase());
			}
		} catch (Exception e) {
		}
	}

	/**
	 * Get an entity from the buffer. Note that Object type will be returned for
	 * premitive type values, for example Integer for int,Float for float and so
	 * on.
	 * <p/>
	 * Vector object will be returned for the Vector-type entity and
	 * tow-dimension array entity;
	 * <p/>
	 * Map object will be returned for the table entity;
	 * 
	 * @param name
	 *            name of the entity;
	 * @return value of the entity, its type decides on the entity type;
	 */
	public Object get(String name) {
		return data.get(name.toUpperCase());
	}

	public Object get2(String name) {
		return data.get(name);
	}

	/**
	 * Get an object from a vector type entity or two-dimenstion array entity or
	 * a table entity.
	 * 
	 * @param name
	 *            name of the entity;
	 * @param index
	 *            index of the object in the target entity;
	 * @return an Vector object if the entity is of type Vector or
	 *         two-dimenstion array; an Map object if the entity is a table type
	 *         one; null otherwise;
	 */
	public Object get(String name, int index) {
		return get2(name.toUpperCase(), index);
	}

	public Object get2(String name, int index) {
		int i = 0, count = 0;

		Object entity = data.get(name);
		if (null == entity) {
			return null;
		} else if (entity instanceof java.util.ArrayList) {
			return ((ArrayList) entity).get(index);
		} else if (entity instanceof java.util.Map) {
			Map temp = newMap();
			Vector fields = getTableFields(name);
			if (null == fields) {
				return null;
			}
			count = fields.size();
			while (i < count) {
				String fieldName = (String) (fields.elementAt(i));
				temp.put(fieldName, get(name, fieldName, index));
				i++;
			}
			return temp;
		} else {
			return null;
		}
	}

	/**
	 * Get a field from a table entity in the buffer;
	 * <p/>
	 * If the entity is not of type table, then null is returned;
	 * 
	 * @param name
	 *            the name of the taget entiy
	 * @param field
	 *            the name fo the tager field;
	 * @return An Vector object if the entity is of type table; otherwise null;
	 */
	protected Object get(String name, String field) {
		// capitalized entity name and field name;
		String cName = name;
		String cField = field.toUpperCase();

		Object entity = data.get(cName);
		if (null == entity) {
			return null;
		} else if (entity instanceof java.util.Map) {
			return ((Map) entity).get(cField);
		} else {
			return null;
		}
	}

	public Object get(String name, String field, String index) {
		String cName = name.toUpperCase();
		Map entity = (Map) data.get(cName);
		if (entity != null) {
			Map fieldsMap = (Map) entity.get(field.toUpperCase());
			if (fieldsMap != null) {
				return fieldsMap.get(index);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public Object get2(String name, String field, String index) {
		Map entity = (Map) data.get(name);
		if (entity != null) {
			Map fieldsMap = (Map) entity.get(field);
			if (fieldsMap != null) {
				return fieldsMap.get(index);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * get the row ids of a table entity. If this table has no rows or is not a
	 * table or doest not exist, then an array of 0 elements is returned. A
	 * table entity consists of one or more rows,each row has a ID of string
	 * type. If you call the select method of DAO object,the returned table
	 * entities' row IDs is the string value of 0 to the count of records-1.
	 * That means the select method return 100 rows, then the row ids are from
	 * "0","1",...to "99";
	 * 
	 * @param name
	 *            the name of the table entity name;
	 * @return the array of the rowids,never return null;
	 */
	public String[] getRowIDs(String name) {
		Set hs = (Set) sys.get("_HS_" + name.toUpperCase());
		if (hs == null) {
			return new String[0];
		}
		String[] indexes = (String[]) hs.toArray(new String[0]);
		return indexes;
	}

	/**
	 * Get a value at the specified position from a field in a table entity in
	 * the buffer;
	 * <p/>
	 * If the entity is not of table type, then null is returned;
	 * 
	 * @param name
	 *            name of the targer entity;
	 * @param field
	 *            name of the field;
	 * @param index
	 *            index of the target value to retrieve.
	 * @return null if the entity is not of table type, otherwise the value of
	 *         the element at the specified position.
	 */
	public Object get(String name, String field, int index) {
		if (index < 0) {
			return get(name, field, new Integer(index).toString());
		}
		String[] rowIDs = this.getRowIDs(name);
		if (rowIDs == null || rowIDs.length <= 0 || rowIDs.length - 1 < index) {
			return null;
		}
		return get(name, field, rowIDs[index]);
	}

	/**
	 * Get a value at the specified position from a field in a two-Dimenstion
	 * type entity in the buffer;
	 * <p/>
	 * If the entity is not of table type, then null is returned;
	 * 
	 * @param name
	 *            name of the targer entity;
	 * @param row
	 *            row index of the target value;
	 * @param column
	 *            column index of the target value to retrieve.
	 * @return null if the entity is not of two-dimension type or the entity
	 *         does not exist, otherwise the value of the element at the
	 *         specified position.
	 */
	public Object get(String name, int row, int column) {
		String cName = name.toUpperCase();
		Object entity = data.get(cName);
		if (null == entity) {
			return null;
		} else if (entity instanceof java.util.ArrayList) {
			Object rowValue = ((ArrayList) entity).get(row);
			if (null == rowValue) {
				return null;
			} else if (rowValue instanceof java.util.ArrayList) {
				return ((ArrayList) rowValue).get(column);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * Get a int value from the buffer if the value vs the name param is of type
	 * int, float, double,short,String,
	 * 
	 * @param name
	 * @return
	 */
	public int getInt(String name) {
		Object value = get(name);
		return getIntFromObject(name, value);
	}

	public long getLong(String name) {
		Object value = get(name);
		return getLongFromObject(name, value);
	}

	public double getDouble(String name) {
		Object value = get(name);
		return getDoubleFromObject(name, value);
	}

	public String getString(String name) {
		Object value = data.get(name.toUpperCase());
		return getStringFromObject(name, value);
	}

	/**
	 * 锟斤拷锟斤拷路锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷值
	 * 
	 * @param src
	 * @return
	 */
	public Object getByPath(String src) {
		String an = null;
		StringBuffer name = new StringBuffer();
		StringBuffer field = new StringBuffer();
		StringBuffer attrName = new StringBuffer();
		StringBuffer rowIndex = new StringBuffer();
		StringBuffer colIndex = new StringBuffer();
		NameParser p = new NameParser();
		int type = p.parse(src, name, field, attrName, rowIndex, colIndex);
		switch (type) {
		case NameParser.SINGLE:
			return this.get(new String(name));
		case NameParser.SINGLE_ATTR:
			return this.getAttr(new String(name), new String(attrName));
		case NameParser.TABLE_FIELD:
			return this.get(new String(name), new String(field), 0);
		case NameParser.TABLE_FIELD_ATTR:
			an = new String(attrName);
			if (an.compareToIgnoreCase("_len") == 0) {
				Object v = this.get(new String(name), new String(field), "0");
				if (v != null && v instanceof String) {
					return new Integer(((String) v).length());
				} else {
					return new Integer(0);
				}
			} else {
				return this.getAttr(new String(name), new String(field),
						new String(attrName));
			}
		case NameParser.TABLE_FIELD_INDEX_ATTR:
			an = new String(attrName);
			if (an.compareToIgnoreCase("_len") == 0) {
				Object v = this.get(new String(name), new String(field),
						new String(rowIndex));
				if (v != null && v instanceof String) {
					return new Integer(((String) v).length());
				} else {
					return new Integer(0);
				}
			} else {
				return this.getAttr(new String(name), new String(field),
						new String(rowIndex), new String(attrName));
			}
		case NameParser.TABLE_INDEX_ATTR:
			return this.getRowAttr(new String(name), new String(rowIndex),
					new String(attrName));
		case NameParser.TALE_FIELD_INDEX:
			return this.get(new String(name), new String(field), new String(
					rowIndex));
		case NameParser.ONE_ARRAY:
			return this.get(new String(name), Integer.valueOf(
					new String(rowIndex)).intValue());
		case NameParser.TWO_ARRAY:
			return this.get(new String(name), Integer.valueOf(
					new String(rowIndex)).intValue(), Integer.valueOf(
					new String(colIndex)).intValue());
		default:
			return null;
		}
	}

	/**
	 * @param path
	 * @return
	 */
	public String getStringByPath(String path) {
		// check if the path is null;
		if (path == null) {
			return new String("");
		}
		Object obj = getByPath(path);
		if (obj == null) {
			return new String("");
		} else
			return getStringFromObject(obj);
	}

	/**
	 * Get date value from the given entity. if the type mismatch,
	 * TypeMismatchException is thrown. if the String can't be parsed as a date
	 * value, ParseException is thrown.
	 * 
	 * @param name
	 * @return
	 */
	public Date getDate(String name) {
		Object value = data.get(name.toUpperCase());
		return getDateFromObject(name, value);
	}

	public int getInt(String name, int index) {
		Object value = get(name, index);
		return getIntFromObject(name, value);
	}

	public long getLong(String name, int index) {
		Object value = get(name, index);
		return getLongFromObject(name, value);
	}

	public double getDouble(String name, int index) {
		Object value = get(name, index);
		return getDoubleFromObject(name, value);
	}

	public String getString(String name, int index) {
		Object value = get(name, index);
		return getStringFromObject(name, value);
	}

	/**
	 * Get date value from the given entity. if the type mismatch,
	 * TypeMismatchException is thrown. if the String can't be parsed as a date
	 * value, ParseException is thrown.
	 * 
	 * @param name
	 * @return
	 */
	public Date getDate(String name, int index) {
		Object value = get(name, index);
		return getDateFromObject(name, value);
	}

	public long getLong(String name, String field, int index) {
		Object value = get(name, field, index);
		return getLongFromObject(name, value);
	}

	public long getLong(String name, String field, String index) {
		Object value = get(name, field, index);
		return getLongFromObject(name, value);
	}

	public int getInt(String name, String field, int index) {
		Object value = get(name, field, index);
		return getIntFromObject(name, value);
	}

	public int getInt(String name, String field, String index) {
		Object value = get(name, field, index);
		return getIntFromObject(name, value);
	}

	public double getDouble(String name, String field, int index) {
		Object value = get(name, field, index);
		return getDoubleFromObject(name, value);
	}

	public double getDouble(String name, String field, String index) {
		Object value = get(name, field, index);
		return getDoubleFromObject(name, value);
	}

	public String getString(String name, String field, int index) {
		Object value = get(name, field, index);
		return getStringFromObject(name, value);
	}

	public String getString(String name, String field, String index) {
		Object value = get(name, field, index);
		return getStringFromObject(name, value);
	}

	public String getStringByDI(String name, String field, int index) {
		Object value = get(name, field, new Integer(index).toString());
		return getStringFromObject(name, value);
	}

	/**
	 * Get date value from the given entity. if the type mismatch,
	 * TypeMismatchException is thrown. if the String can't be parsed as a date
	 * value, ParseException is thrown.
	 * 
	 * @param name
	 * @return
	 */
	public Date getDate(String name, String field, int index) {
		Object value = get(name, field, index);
		return getDateFromObject(name, value);
	}

	public Date getDate(String name, String field, String index) {
		Object value = get(name, field, index);
		return getDateFromObject(name, value);
	}

	public int getInt(String name, int row, int column) {
		Object value = get(name, row, column);
		return getIntFromObject(name, value);
	}

	public long getLong(String name, int row, int column) {
		Object value = get(name, row, column);
		return getLongFromObject(name, value);
	}

	public double getDouble(String name, int row, int column) {
		Object value = get(name, row, column);
		return getDoubleFromObject(name, value);
	}

	public String getString(String name, int row, int column) {
		Object value = get(name, row, column);
		return getStringFromObject(name, value);
	}

	public Date getDate(String name, int row, int column) {
		Object value = get(name, row, column);
		return getDateFromObject(name, value);
	}

	/**
	 * @param name
	 * @param attr
	 * @return
	 */
	public Object getAttr(String name, String attr) {
		String cName = name.toUpperCase();
		String cAttr = attr.toUpperCase();
		if (cAttr.compareTo(SysVar.ATTR_LEN) == 0) {
			if (isArray(cName)) {
				Object v = get(cName);
				return new Integer(((Vector) v).size());
			} else if (isTable(cName)) {
				int rsSize = getTableRowsCount(cName);
				return new Integer(rsSize);
			} else {
				String v = getString(cName);
				return new Integer(v.length());
			}
		}

		Object attrs = this.attr.get(cName);
		if (null == attrs) {
			return null;
		} else if (attrs instanceof java.util.Map) {
			try {
				Map m = (Map) ((Map) attrs).get(BizData.ENTITY_ATTR);
				return m.get(cAttr);
			} catch (Exception e) {
			}
			return null;
		} else {
			return null;
		}
	}

	public int getIntAttr(String name, String attr) {
		int value = 0;
		Object o = getAttr(name, attr);
		if (o != null) {
			value = getIntFromObject(null, o);
		}
		return value;
	}

	public int getIntAttr(String table, String field, String attr) {
		int value = 0;
		Object o = getAttr(table, field, attr);
		if (o != null) {
			value = getIntFromObject(null, o);
		}
		return value;
	}

	public int getIntAttr(String table, String field, String row, String attr) {
		int value = 0;
		Object o = getAttr(table, field, row, attr);
		if (o != null) {
			value = getIntFromObject(null, o);
		}
		return value;
	}

	public double getDoubleAttr(String name, String attr) {
		double value = 0;
		Object o = getAttr(name, attr);
		if (o != null) {
			value = getDoubleFromObject(null, o);
		}
		return value;
	}

	public double getDoubleAttr(String table, String field, String attr) {
		double value = 0;
		Object o = getAttr(table, field, attr);
		if (o != null) {
			value = getDoubleFromObject(null, o);
		}
		return value;
	}

	public double getDoubleAttr(String table, String field, String row,
			String attr) {
		double value = 0;
		Object o = getAttr(table, field, row, attr);
		if (o != null) {
			value = getDoubleFromObject(null, o);
		}
		return value;
	}

	public Date getDateAttr(String name, String attr) {
		Date value = null;
		Object o = getAttr(name, attr);
		if (o != null) {
			value = getDateFromObject(null, o);
		}
		return value;
	}

	public Date getDateAttr(String table, String field, String attr) {
		Date value = null;
		Object o = getAttr(table, field, attr);
		if (o != null) {
			value = getDateFromObject(null, o);
		}
		return value;
	}

	public Date getDateAttr(String table, String field, String row, String attr) {
		Date value = null;
		Object o = getAttr(table, field, row, attr);
		if (o != null) {
			value = getDateFromObject(null, o);
		}
		return value;
	}

	public String getStringAttr(String name, String attr) {
		String value = null;
		Object o = getAttr(name, attr);
		if (o != null) {
			value = getStringFromObject(null, o);
		}
		return value;
	}

	public String getStringAttr(String table, String field, String attr) {
		String value = null;
		Object o = getAttr(table, field, attr);
		if (o != null) {
			value = getStringFromObject(null, o);
		}
		return value;
	}

	public String getStringAttr(String table, String field, String row,
			String attr) {
		String value = null;
		Object o = getAttr(table, field, row, attr);
		if (o != null) {
			value = getStringFromObject(null, o);
		}
		return value;
	}

	/**
	 * @param name
	 * @param field
	 * @param attr
	 * @return
	 */
	public Object getAttr(String name, String field, String attr) {
		String cAttrName = attr.toUpperCase();
		if (cAttrName.compareTo(SysVar.ATTR_LEN) == 0) {
			String v = getString(name, field, "0");
			return new Integer(v.length());
		}

		Object attrs = this.attr.get(name.toUpperCase());
		if (null == attrs) {
			return null;
		} else if (attrs instanceof java.util.Map) {
			Map m = (Map) ((Map) attrs).get(BizData.FIELD_ATTR);
			if (m != null) {
				return m.get(field.toUpperCase() + "/" + attr.toUpperCase());
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public Object getAttr(String name, String field, int index, String attr) {
		String[] rowIDs = getRowIDs(name);
		if (rowIDs == null || rowIDs.length <= 0 || rowIDs.length - 1 < index) {
			return null;
		}
		return getAttr(name, field, rowIDs[index], attr);
	}

	/**
	 * @param name
	 * @param field
	 * @param index
	 * @return
	 */
	public Object getAttr(String name, String field, String index,
			String attrName) {
		String cName = name.toUpperCase();
		String cAttrName = attrName.toUpperCase();
		if (cAttrName.compareTo(SysVar.ATTR_LEN) == 0) {
			String v = getString(name, field, index);
			return new Integer(v.length());
		}

		Object attrs = this.attr.get(cName);
		if (null == attrs) {
			return null;
		} else if (attrs instanceof java.util.Map) {
			Map m = (Map) ((Map) attrs).get(BizData.FIELD_ATTR);
			if (m != null) {
				return m.get(field.toUpperCase() + "/" + index + "/"
						+ attrName.toUpperCase());
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * get count of vector values
	 */
	public int getCountOfVectorValue(String name) {
		Object entity = null;
		entity = data.get(name.toUpperCase());
		if (null == entity) {
			return 0;
		}
		if (entity instanceof java.util.Vector) {
			return ((Vector) entity).size();
		} else {
			return -1;
		}
	}

	/**
	 * get the count of fields of a specified entity;
	 */
	public int getCountOfEntityField(String name) {
		Object entity = null;
		entity = data.get(name.toUpperCase());
		if (null == entity) {
			return 0;
		}
		if (entity instanceof java.util.Map) {
			return ((Map) entity).size();
		} else {
			return -1;
		}
	}

	/**
	 * get the length of an array, if this entity is not an array( one dimension
	 * or two dimension) -1 is returned;
	 * 
	 * @param name
	 *            the entity name;
	 * @return the size of the array, -1 if the entity is not an array;
	 */
	public int getArrayLength(String name) {
		int length = -1;
		if (name != null) {
			Object o = data.get(name.toUpperCase());
			if (o != null && o instanceof ArrayList) {
				length = ((ArrayList) o).size();
			}
		}
		return length;
	}

	/**
	 * get count of table entities in the BizData object;
	 */
	public int getCountOfTables() {
		int count = 0;
		Iterator it = data.keySet().iterator();
		if (null == it) {
			return -1;
		}
		while (it.hasNext()) {
			String name = (String) (it.next());
			if (isTable(name)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * get a vector of all table entities in this data object; A table entity
	 * may has an attribute named "NO" specify the order among the all table
	 * entities, this attribute is an Integer object. Suppose there are three
	 * table entities in a BizData object named A,B,C; A is associated with an
	 * attribute "NO" of value 10, and C of value 1,B has no this attribute.
	 * Then the Vector returned by getTables will appears as following:
	 * {C,A,B},this feature will facilitate some methods in DAO class.
	 * 
	 * @return a Vector object with table entities predifined.
	 */
	public Vector getTables() {
		Vector entities = new Vector();
		TreeMap seq = new TreeMap();
		Iterator it = data.keySet().iterator();
		if (null == it) {
			return null;
		}
		while (it.hasNext()) {
			String name = (String) (it.next());
			if (isTable(name)) {
				Object index = getAttr(name, "NO");
				if (index != null) {
					try {
						Integer nNO = Integer.valueOf(index.toString());
						seq.put(nNO, name);
					} catch (NumberFormatException e) {
						entities.add(name);
					}
				} else {
					entities.add(name);
				}
			}
		}
		it = seq.keySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			entities.add(i++, seq.get(it.next()));
		}
		return entities;
	}

	/**
	 * Get all the fields of a table entity,if the entity is not a table then
	 * null is returned;
	 * 
	 * @param name
	 *            name of the target entity;
	 * @return a Vector object of all fields in the buffer;
	 */
	public Vector getTableFields(String name) {
		if (null == name) {
			return null;
		}
		if (!isTable(name)) {
			return null;
		}
		return getEntityFields(name);
	}

	/**
	 * check if an entry is a single value;
	 */
	public boolean isSingleValue(String name) {
		if (getEntityType(name) < Types.SINGLEVALUE) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * check if an entry is table type Entity;
	 */
	public boolean isHashtable(String name) {
		if (getEntityType(name) == Types.HASHTABLE) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * check if the table is valid
	 */
	public boolean isTable(String name) {
		// int i = 1;
		int count = 0;
		// int records = -1;
		if (!isHashtable(name)) {
			return false;
		}
		Map entity = (Map) data.get(name.toUpperCase());
		if (null == entity) {
			return false;
		}
		Vector fields = getEntityFields(name);
		if (null == fields) {
			return false;
		}
		count = fields.size();
		if (count <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * Get count of rows of a table-entity in this data object. If the specified
	 * entity is not of table type or doest not exist,then 0 is returned.
	 * 
	 * @param name
	 *            the name of the table type entity;
	 * @return count of the rows in this table entity,0 if the table entity does
	 *         not exist or is not a table.
	 */

	public int getTableRowsCount(String name) {
		if (!isTable(name)) {
			return 0;
		}
		Set hs = (Set) sys.get("_HS_" + name.toUpperCase());
		if (hs == null) {
			return 0;
		} else {
			return hs.size();
		}
	}

	public int getVectorSize(String name) {
		if (!isVector(name)) {
			return 0;
		}
		Vector v = (Vector) get(name);
		return v.size();
	}

	/**
	 * Test if an entity is of Vector type;
	 * 
	 * @param name
	 *            the name of the specified entity;
	 * @return false if the entity does not exist, or the entity is not of
	 *         Vector type,true otherwise;
	 */
	public boolean isVector(String name) {
		if (getEntityType(name) == Types.VECTOR) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Test if a entity is of two-dimension array index.
	 * 
	 * @param name
	 *            name of the entity to test.
	 * @return false if the entity does not exist or is not of two-dimension
	 *         array index type. true otherwise.
	 */
	public boolean isArray(String name) {
		Object entity = get(name);
		if (null == entity || (entity instanceof java.util.Vector) == false) {
			return false;
		}
		Vector vector = (Vector) entity;
		int size = vector.size();
		int i = 0;
		while (i < size) {
			Object ele = vector.elementAt(i);
			if (ele == null || ele instanceof java.util.Vector) {
				return false;
			}
			i++;
		}
		return true;
	}

	/**
	 * get a object type
	 * 
	 * @param entity
	 * @return
	 */
	protected int getObjectType(Object entity) {
		if (null == entity) {
			return Types.UNKNOWN;
		}
		if (entity instanceof Short) {
			return Types.SHORT;
		}
		if (entity instanceof Integer) {
			return Types.INTEGER;
		}
		if (entity instanceof Long) {
			return Types.LONG;
		}
		if (entity instanceof Float) {
			return Types.FLOAT;
		}
		if (entity instanceof Double) {
			return Types.DOUBLE;
		}
		if (entity instanceof String) {
			return Types.STRING;
		}
		if (entity instanceof java.util.Date) {
			return Types.DATE;
		}
		if (entity instanceof Vector) {
			return Types.VECTOR;
		}
		if (entity instanceof ArrayList) {
			return Types.VECTOR;
		}
		if (entity instanceof Hashtable) {
			return Types.HASHTABLE;
		}
		if (entity instanceof Map) {
			return Types.HASHTABLE;
		}
		return Types.OBJECT;
	}

	/**
	 * Get the type of a specified entity. If the entity does not exist,return
	 * Types.UNKNOWN; if the entity's type is not among the predifined types,
	 * return Types.OBJECT
	 * 
	 * @param name
	 *            name of the target entity;
	 * @return enumation of the type. see the Typs.java.
	 */
	public int getEntityType(String name) {
		Object entity = data.get(name.toUpperCase());
		return getObjectType(entity);
	}

	/**
	 * Test if an entity exists.
	 * 
	 * @param name
	 *            name of the entity to test.
	 * @return true if the entity exists; false the entity does not exist;
	 */
	public boolean existEntity(String name) {
		Object value = get(name);
		if (null == value) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Test if an entity exists.
	 * 
	 * @param name
	 *            name of the entity to test.
	 * @return true if the entity exists; false the entity does not exist;
	 */
	public boolean exist(String name) {
		Object value = get(name);
		if (null == value) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Test if a table entity has the specified field.
	 * 
	 * @param name
	 *            name of the target table entity;
	 * @param field
	 *            fiedl of the target field;
	 * @return false if the entity does not exist or is not of table type or the
	 *         field does not exist; true otherwise;
	 */
	public boolean existField(String name, String field) {
		Object value = get(name);
		if (null == value) {
			return false;
		} else {
			if (!this.isTable(name)) {
				return false;
			} else {
				value = get(name, field);
				if (null == value) {
					return false;
				} else {
					return true;
				}
			}
		}
	}

	/**
	 * get the count of entity in this buffer.
	 * 
	 * @return the count of entity.
	 */
	public int getEntityCount() {
		return data.size();
	}

	public Iterator entities() {
		return data.values().iterator();
	}

	public Iterator entityNames() {
		return data.keySet().iterator();
	}

	public List attrNamesOfEntity(String name) {
		ArrayList al = new ArrayList();
		Map m1 = (Map) attr.get(name);
		if (m1 != null) {
			Map m2 = (Map) m1.get(BizData.ENTITY_ATTR);
			if (m2 != null) {
				Iterator it = m2.keySet().iterator();
				while (it.hasNext()) {
					String attrName = (String) it.next();
					int index = attrName.lastIndexOf('/');
					if (index > -1) {
						al.add(attrName.substring(index));
					}
				}
			}
		}
		return al;
	}

	public List attrNamesOfRecordField(String name) {
		ArrayList al = new ArrayList();
		Map m1 = (Map) attr.get(name);
		if (m1 != null) {
			Map m2 = (Map) m1.get(BizData.FIELD_ATTR);
			if (m2 != null) {
				Iterator it = m2.keySet().iterator();
				while (it.hasNext()) {
					al.add(it.next());
				}
			}
		}
		return al;
	}

	public List attrNamesOfRecordRow(String name) {
		ArrayList al = new ArrayList();
		Map m1 = (Map) attr.get(name);
		if (m1 != null) {
			Map m2 = (Map) m1.get(BizData.ROW_ATTR);
			if (m2 != null) {
				Iterator it = m2.keySet().iterator();
				while (it.hasNext()) {
					al.add(it.next());
				}
			}
		}
		return al;
	}

	protected Vector getEntityFields(String name) {
		String cName = name.toUpperCase();
		Vector fields = new Vector();
		Object entity = data.get(cName);
		if (null != entity && entity instanceof java.util.Map) {
			Iterator it = ((Map) entity).keySet().iterator();
			if (null != it) {
				while (it.hasNext()) {
					String fieldName = (String) (it.next());
					fields.add(fieldName);
				}
			}
		}
		if (fields.size() == 0) {
			return null;
		} else {
			return fields;
		}
	}

	/**
	 * copy an entity into another entity;
	 * 
	 * @param src
	 *            the name of the source entity.
	 * @param target
	 *            the name of the new entity;
	 */
	public void copyEntity(String src, String target) {
		if (src == null || target == null) {
			return;
		}
		if (src.compareToIgnoreCase(target) == 0) {
			return;
		}
		String cTarget = target.toUpperCase();
		this.remove(target);
		Object srcObj = get(src);
		if (null != srcObj) {
			Set hs = (Set) sys.get("_HS_" + src.toUpperCase());
			sys.put("_HS_" + cTarget, hs);
			add(target, srcObj);
		}
	}

	/**
	 * @param tableName
	 * @param fields
	 */
	public void encryptFields(String tableName, Vector fields)
			throws EncryptException {
		int count = 0;
		if (fields != null && (count = fields.size()) > 0) {
			int i = 0;
			while (i < count) {
				encryptField(tableName, (String) fields.elementAt(i));
				i++;
			}
		}
	}

	/**
	 * encrypte a field of entity using the cipher settings.
	 * 
	 * @param tableName
	 *            the name of the table entity;
	 * @param field
	 *            the field to be encrypted in the table;
	 */
	public void encryptField(String tableName, String field)
			throws EncryptException {
		int i = 0;
		int count = 0;
		String[] rowIDs = getRowIDs(tableName);
		if (rowIDs == null || rowIDs.length < 1) {
			return;
		}
		count = rowIDs.length;
		MsgCipher c = new MsgCipher(_cipherType, _cipherKey);
		while (i < count) {
			String src = getString(tableName, field, rowIDs[i]);
			if (src != null) {
				String enc = c.encrypt(src);
				add(tableName, field, rowIDs[i], enc);
			}
			i++;
		}
	}

	public void encryptEntity(String name) throws EncryptException {
		MsgCipher c = new MsgCipher(_cipherType, _cipherKey);
		String src = getString(name);
		String enc = c.encrypt(src);
		add(name, enc);
	}

	/**
	 * @param tableName
	 * @param fields
	 */
	public void decryptFields(String tableName, Vector fields)
			throws EncryptException {
		int count = 0;
		if (fields != null && (count = fields.size()) > 0) {
			int i = 0;
			while (i < count) {
				decryptField(tableName, (String) fields.elementAt(i));
				i++;
			}
		}
	}

	/**
	 * decrypte a field of a table enttiy.
	 * 
	 * @param tableName
	 *            the name of tha table to be decrypted;
	 * @param field
	 *            the field to be decrpted in the table;
	 */
	public void decryptField(String tableName, String field)
			throws EncryptException {
		int i = 0;
		int count = 0;
		String[] rowIDs = getRowIDs(tableName);
		if (rowIDs == null || rowIDs.length < 1) {
			return;
		}
		count = rowIDs.length;
		MsgCipher c = new MsgCipher(_cipherType, _cipherKey);
		while (i < count) {
			String src = getString(tableName, field, rowIDs[i]);
			if (src != null) {
				String dec = c.decrypt(src);
				add(tableName, field, rowIDs[i], dec);
			}
			i++;
		}
	}

	public void decryptEntity(String name) throws EncryptException {
		MsgCipher c = new MsgCipher(_cipherType, _cipherKey);
		String src = getString(name);
		String enc = c.decrypt(src);
		add(name, enc);
	}

	/**
	 * @param tableName
	 * @param fields
	 * @throws DigestException
	 */
	public void digestField(String tableName, Vector fields)
			throws DigestException {
		int count = 0;
		if (fields != null && (count = fields.size()) > 0) {
			int i = 0;
			while (i < count) {
				digestField(tableName, (String) fields.elementAt(i));
				i++;
			}
		}
	}

	/**
	 * @param tableName
	 * @param field
	 */
	public void digestField(String tableName, String field)
			throws DigestException {
		int i = 0;
		int count = 0;
		String[] rowIDs = getRowIDs(tableName);
		if (rowIDs == null || rowIDs.length < 1) {
			return;
		}
		count = rowIDs.length;
		while (i < count) {
			String src = getString(tableName, field, rowIDs[i]);
			if (src != null) {
				String dec = MsgDigest.digest(src, _digestType);
				add(tableName, field, rowIDs[i], dec);
			}
			i++;
		}
	}

	/**
	 * copy an entity into another entity;
	 * 
	 * @param src
	 *            the name of the source entity.
	 * @param target
	 *            the name of the new entity;
	 */
	public void copyEntity(BizData srcBD, String src, String target) {
		String cTarget = target.toUpperCase();
		Object srcObj = srcBD.get(src);
		Object thisObj = data.get(cTarget) == null ? new HashMap() : data
				.get(cTarget);
		if (null != srcObj) {
			Set hs = (Set) srcBD.sys.get("_HS_" + src.toUpperCase());
			sys.put("_HS_" + cTarget, hs);
			copyMap(srcObj, thisObj);
			add(cTarget, thisObj);

			Map m = (Map) srcBD.attr.get(src.toUpperCase());

			if (m != null) {
				Object thisAttr = attr.get(cTarget) == null ? new HashMap()
						: attr.get(cTarget);
				copyMap(thisAttr, m);
				attr.put(target.toUpperCase(), m);
			}
		}
	}

	private void copyMap(Object src, Object target) {
		Map srcMap = (Map) src;
		Map targetMap = (Map) target;
		Iterator keyIt = srcMap.keySet().iterator();
		Object key;
		while (keyIt.hasNext()) {
			key = keyIt.next();
			targetMap.put(key, srcMap.get(key));
		}
	}

	/**
	 * @param srcBD
	 * @param src
	 * @param rowNum
	 * @param target
	 */
	public void copyEntityRow(BizData srcBD, String src, String rowNum,
			String target) {
		copyEntityRow(srcBD, src, rowNum, target, true);
	}

	public void appendEntityRow(BizData srcBD, String src, String rowNum,
			String target) {
		copyEntityRow(srcBD, src, rowNum, target, false);
	}

	public void copyEntityRow(BizData srcBD, String src, String rowNum,
			String target, boolean overwrite) {
		int i = 0;
		int len = 0;
		int rowsCount = 0;

		/** check input parameters */
		if (srcBD == null || src == null || rowNum == null || target == null) {
			return;
		}
		/** check if copy the same entity in the same BizData object. */
		if (this == srcBD && src.compareToIgnoreCase(target) == 0) {
			return;
		}

		// remove the target;
		if (overwrite && existEntity(target)) {
			remove(target);
		} else {
			rowsCount = getTableRowsCount(target);
		}
		String strRowIndex = new Integer(rowsCount).toString();

		Vector v = srcBD.getTableFields(src);
		len = v.size();
		while (i < len) {
			String fieldName = (String) v.elementAt(i);
			add(target, fieldName, strRowIndex, srcBD.get(src, fieldName,
					rowNum));
			i++;
		}
	}

	/**
	 * copy a field's values from a source BizData object to this BizData
	 * Object. If this BizData Object already contains a record entity with the
	 * "name", then the target value of the same row id will be overwrited,other
	 * values will remain unchanged.
	 * 
	 * @param src
	 *            the source BizData object;
	 * @param name
	 *            the name of the record entity;
	 * @param field
	 *            the name of the field to be copied.
	 */
	public void copyEntityField(BizData src, String name, String field) {
		int count;
		if (src == null || name == null || field == null) {
			return;
		} else {
			String[] rowIDs = src.getRowIDs(name);
			count = rowIDs.length;
			for (int i = 0; i < count; i++) {
				Object v = src.get(name, field, rowIDs[i]);
				add(name, field, rowIDs[i], v);
			}
		}
	}

	/**
	 * copy a value from a BizData Object ;
	 * 
	 * @param src
	 *            the source BizData object;
	 * @param name
	 *            the name of the record entity;
	 * @param field
	 *            the name of the field;
	 * @param row
	 *            the row id.
	 */
	public void copyEntityField(BizData src, String name, String field,
			String row) {
		if (src == null || name == null || field == null) {
			return;
		} else {
			Object v = src.get(name, field, row);
			add(name, field, row, v);
		}
	}

	/**
	 * @param srcBD
	 * @param src
	 * @param rowNum
	 */
	public void copyEntityRow(BizData srcBD, String src, String rowNum) {
		copyEntityRow(srcBD, src, rowNum, src);
	}

	public void appendEntityRow(BizData srcBD, String src, String rowNum) {
		appendEntityRow(srcBD, src, rowNum, src);
	}

	/**
	 * copy an entity into another entity;
	 * 
	 * @param srcBD
	 *            the name of the new entity;
	 * @param src
	 *            the name of the source entity.
	 */
	public void copyEntity(BizData srcBD, String src) {
		copyEntity(srcBD, src, src);
	}

	/**
	 * rename an entity to another name;
	 * 
	 * @param src
	 *            the name of the entity to be named.
	 * @param target
	 *            the target name.
	 */
	public void renameEntity(String src, String target) {
		if (src == null || target == null) {
			return;
		}
		copyEntity(src, target);
		if (src.compareTo(target) != 0) {
			remove(src);
		}
	}

	/**
	 * create a new Map object, Here we use THashMap instead of the HashMap in
	 * JDK to provide better performance;
	 * <p/>
	 * Tests show that THashMap is 3.5x of the HashMap in get and put method;
	 * 
	 * @return
	 */
	private final HashMap newMap() {
		return new HashMap();
	}

	/**
	 * @return
	 */
	private final Set newSet() {
		return new HashSet();
	}

	/**
	 * @param data
	 * @return
	 */
	public static ByteArrayInputStream serialize(BizData data)
			throws IOException {
		ByteArrayInputStream bais = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(data);
		oos.flush();
		bais = new ByteArrayInputStream(baos.toByteArray());
		return bais;
	}

	/**
	 * @param is
	 * @return
	 */
	public static BizData load(ByteArrayInputStream is) throws IOException,
			ClassNotFoundException {
		BizData d = null;
		ObjectInputStream ois = new ObjectInputStream(is);
		d = (BizData) ois.readObject();
		ois.close();
		return d;
	}

	/**
	 * set a new Timeformat;If the foramt is not valid,then default format is
	 * used;
	 */
	public static void setTimeFormat(String timeFormat) {
		BizData.dateFormat = timeFormat;
		SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
		sdfTime = sdf;
	}

	/**
	 * set a new Dateformat;If the foramt is not valid,then default format is
	 * used;
	 */
	public static void setDateFormat(String dateFormat) {
		BizData.dateFormat = dateFormat;
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdfDate = sdf;
	}

	public Object sdfDate(String stringByDI) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 当页面字段为数组时，字段索引值不连续且需要向name表名的字段遍历值时
	 * 动态取出字段索引值，作为遍历依据
	 * @param bizData
	 * @param name 表名
	 * @return
	 */
	public int[] getFieldIndex(BizData bizData,String name){
		
		int i=0;
		int rows = getTableRowsCount(name);
		int [] indexValues = new int[rows];
		Object entity = bizData.get(name.toUpperCase());
		if (null == entity) {
			return null;
		}
		if (entity instanceof java.util.Map) {
			
			convertGetIndex((Map)entity, indexValues, i);
		}
		return indexValues;
	}
	
	/**
	 *
	 * @param maps
	 * @param indexValues
	 * @param i
	 */
	private void convertGetIndex(Map maps, int[] indexValues, int i) {

		Set keySet = maps.keySet();
		Iterator it = keySet.iterator();
		while (it.hasNext()) {
			Object keyObj = it.next();
			Object valueObj = maps.get(keyObj);
			if (valueObj instanceof java.util.Map) {
				
				convertGetIndex((Map)valueObj, indexValues, i);
			} else {
				indexValues[i] = Integer.valueOf(keyObj.toString());
				i++;
			}
		}
	}
	
	/**
	 * 根据key从DATA中取出值
	 * @param name key 
	 * @return value
	 */
	public Object getObject4Data(String name)
	{
		return data.get(name);
	}

	public static void main(String args[]) {

		BizData dataObj = new BizData();
		dataObj.add("TESt", "A", 2, "a");
		dataObj.add("TESt", "B", 2, "b");
		dataObj.add("TESt", "C", 2, "c");
		dataObj.add("TESt", "D", 2, "d");
		
		dataObj.add("TESt", "A", 4, "e");
		dataObj.add("TESt", "B", 4, "f");
		dataObj.add("TESt", "C", 4, "g");
		dataObj.add("TESt", "D", 4, "h");
		
		dataObj.getFieldIndex(dataObj, "test");
	}

}