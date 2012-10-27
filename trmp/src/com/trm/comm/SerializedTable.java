/**
 * 
 */
package com.trm.comm;

import java.io.Serializable;

/**
 * @author divine
 * 
 */
public class SerializedTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tableName;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	private String fieldName;
	private int theID;

	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public int getTheID() {
		return theID;
	}
	public void setTheID(int theID) {
		this.theID = theID;
	}
}
