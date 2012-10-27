/*
 * SqlSQLBuilder.java
 *
 * Created on 2003年3月23日, 下午12:37
 */
package com.dream.bizsdk.common.database.sql;

import com.dream.bizsdk.common.database.datadict.*;
import com.dream.bizsdk.common.databus.*;

import java.util.Vector;
import java.util.ArrayList;

/**
 * @author chuguanghua
 */
public class MySqlSQLBuilder extends ISQLBuilder {

	/**
	 * Creates a new instance of SqlSQLBuilder
	 */
	public MySqlSQLBuilder(String daoName) {
		super(daoName);
		// String functions;
		funcs.put("len", new Integer(0));
		funcs.put("left", new Integer(1));
		funcs.put("right", new Integer(1));
		funcs.put("reverse", new Integer(1));
		funcs.put("char", new Integer(1));
		funcs.put("ascii", new Integer(0));
		funcs.put("lower", new Integer(1));
		funcs.put("upper", new Integer(1));
		funcs.put("substring", new Integer(1));
		funcs.put("replace", new Integer(1));
		funcs.put("ltrim", new Integer(1));
		funcs.put("rtrim", new Integer(1));
		funcs.put("charindex", new Integer(0));
		funcs.put("nchar", new Integer(0));
		funcs.put("patindex", new Integer(0));

		// math functions;
		funcs.put("abs", new Integer(0));
		funcs.put("acos", new Integer(0));
		funcs.put("cos", new Integer(0));
		funcs.put("sin", new Integer(0));
		funcs.put("tan", new Integer(0));
		funcs.put("asin", new Integer(0));
		funcs.put("atan", new Integer(0));
		funcs.put("log", new Integer(0));
		funcs.put("floor", new Integer(0));
		funcs.put("power", new Integer(0));
		funcs.put("exp", new Integer(0));
		funcs.put("cot", new Integer(0));
		funcs.put("rand", new Integer(0));
		funcs.put("log10", new Integer(0));
		funcs.put("sqrt", new Integer(0));
		funcs.put("square", new Integer(0));

		// date functions
		funcs.put("dateadd", new Integer(1));
		funcs.put("year", new Integer(0));
		funcs.put("month", new Integer(0));
		funcs.put("day", new Integer(0));
		funcs.put("now", new Integer(1));

		// aggregate functions;
		funcs.put("count", new Integer(0));
		funcs.put("sum", new Integer(0));
		funcs.put("avg", new Integer(0));
	}

	/**
	 * TABLE test(id number(12),name varchar(256),birth date); delete from test
	 * where id=12; delete from test where legth(name)>10;
	 */
	public String buildDeleteSQL(String name, String row, BizData data,
			BizData sd, DataDict dc) {
		try {
			int i = 0;
			int fieldsCount = 0;
			StringBuffer sql = new StringBuffer().append("delete from ")
					.append(name);
			StringBuffer where = new StringBuffer().append(" where ");
			int orginalLength = where.length();
			Vector fields = data.getTableFields(name);
			DBTableDef tabDef = dc.getTableDef(name).duplicate();
			if (fields == null || (fieldsCount = fields.size()) == 0
					|| tabDef == null) {
				return null;
			}
			while (i < fieldsCount) {
				String fieldName = (String) fields.elementAt(i);
				Object fieldValue = data.get(name, fieldName, row);
				if (fieldValue == null) {
					i++;
					continue;
				}

				DBColumnDef colDef = tabDef.getColumn(fieldName);
				if (colDef != null) {
					buildWhere(where, name, colDef, fieldValue.toString(), row,
							data);
				}
				i++;
			}
			if (where.length() > orginalLength) {
				where.delete(where.length() - AND.length(), where.length()
						- AND.length() - 1 + AND.length());
				sql.append(where);
			}
			return new String(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param name
	 * @param data
	 * @param dc
	 * @param fieldsInDB
	 * @param types
	 * @return
	 */
	public String buildInsertSQL(String name, BizData data, DataDict dc,
			Vector fieldsInDB, Vector types) {
		try {
			int i = 0;
			int fieldsCount = 0;
			StringBuffer sql = new StringBuffer().append("insert into ")
					.append(name);
			StringBuffer columns = new StringBuffer();
			StringBuffer values = new StringBuffer();
			Vector fields = data.getTableFields(name);
			DBTableDef tabDef = dc.getTableDef(name);
			if (fields == null || (fieldsCount = fields.size()) == 0
					|| tabDef == null) {
				return null;
			}
			while (i < fieldsCount) {
				String fieldName = (String) fields.elementAt(i);
				DBColumnDef colDef = tabDef.getColumn(fieldName);
				if (colDef != null) {
					columns.append(colDef.getName()).append(",");
					values.append("?,");
					fieldsInDB.add(fieldName);
					types.add(new Integer(colDef.getType()));
				}
				i++;
			}
			if (columns.length() > 1) {
				columns.deleteCharAt(columns.length() - 1);
				values.deleteCharAt(values.length() - 1);
				sql.append("(").append(columns).append(") ").append("values(")
						.append(values).append(")");
				return new String(sql);
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("Exception in buildInsertSQL():"
					+ e.getMessage());
			return null;
		}
	}

	/**
	 * @param name
	 * @param row
	 * @param allCols
	 * @param data
	 * @param dc
	 * @param wh
	 * @return
	 */
	public String buildSelectSQL(String name, String row, boolean allCols,
			BizData data, BizData sd, DataDict dc, StringBuffer wh, int pageNO,
			int pageSize) {
		try {
			int i = 0;
			int fieldsCount = 0;
			String orderby = null;
			String groupby = null;
			StringBuffer sql = new StringBuffer().append("select ");
			StringBuffer columns = new StringBuffer();
			StringBuffer where = new StringBuffer().append(" where ");
			int orginalLength = where.length();
			Vector fields = data.getTableFields(name);
			DBTableDef tabDef = dc.getTableDef(name).duplicate();
			if (fields == null || (fieldsCount = fields.size()) == 0
					|| tabDef == null) {
				return null;
			}
			while (i < fieldsCount) {
				String fieldName = (String) fields.elementAt(i);
				Object ob = data.get(name, fieldName, row);
				String fieldValue = null;
				if (ob != null) {
					fieldValue = data.getStringFromObject(null, ob);
				}

				DBColumnDef colDef = tabDef.getColumn(fieldName);
				tabDef.deleteColumn(colDef);
				String alias = (String) data.getAttr(name, fieldName, row,
						"alias");
				if (colDef != null) {
					String parsedFieldName = null;
					parsedFieldName = buildWhere(where, name, colDef,
							fieldValue, row, data);
					if (parsedFieldName != null) {
						if (alias == null) {
							columns.append(parsedFieldName).append(" ").append(
									colDef.getName()).append(",");
						} else {
							columns.append(parsedFieldName).append(" ").append(
									alias).append(",");
						}
					}
				}
				i++;
			}
			if (allCols) {
				i = 0;
				DBColumnDef[] cols = tabDef.getColumns();
				int leftColumns = cols.length;
				while (i < leftColumns) {
					columns.append(cols[i].getName()).append(" ").append(
							cols[i].getName()).append(",");
					i++;
				}
			}
			columns.deleteCharAt(columns.length() - 1);
			sql.append(columns).append(" from ").append(name);
			if (where.length() > orginalLength) {
				where.delete(where.length() - AND.length(), where.length()
						- AND.length() - 1 + AND.length());
				sql.append(where);
				wh.append(where);
			}
			orderby = (String) data.getAttr(name, "orderBy");
			groupby = (String) data.getAttr(name, "groupBy");
			if (orderby != null) {
				sql.append(" order by ").append(orderby);
			}
			if (groupby != null) {
				sql.append(" group by ").append(groupby);
			}
			return new String(sql);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param name
	 * @param row
	 * @param allCols
	 * @param data
	 * @param dc
	 * @return
	 */
	public String buildSelectSQL(String name, String row, boolean allCols,
			BizData data, BizData sd, DataDict dc) {
		try {
			int i = 0;
			int fieldsCount = 0;
			String orderby = null;
			String groupby = null;
			StringBuffer sql = new StringBuffer().append("select ");
			StringBuffer columns = new StringBuffer();
			StringBuffer where = new StringBuffer().append(" where ");
			int orginalLength = where.length();
			Vector fields = data.getTableFields(name);
			DBTableDef tabDef = dc.getTableDef(name).duplicate();
			if (fields == null || (fieldsCount = fields.size()) == 0
					|| tabDef == null) {
				return null;
			}
			while (i < fieldsCount) {
				String fieldName = (String) fields.elementAt(i);
				Object ob = data.get(name, fieldName, row);
				String fieldValue = null;
				if (ob != null) {
					fieldValue = data.getStringFromObject(null, ob);
				}

				DBColumnDef colDef = tabDef.getColumn(fieldName);
				tabDef.deleteColumn(colDef);
				String alias = (String) data.getAttr(name, fieldName, row,
						"alias");
				if (colDef != null) {
					String parsedFieldName = null;
					parsedFieldName = buildWhere(where, name, colDef,
							fieldValue, row, data);
					if (parsedFieldName != null) {
						if (alias == null) {
							columns.append(parsedFieldName).append(" ").append(
									colDef.getName()).append(",");
						} else {
							columns.append(parsedFieldName).append(" ").append(
									alias).append(",");
						}
					}
				}
				i++;
			}
			if (allCols) {
				i = 0;
				DBColumnDef[] cols = tabDef.getColumns();
				int leftColumns = cols.length;
				while (i < leftColumns) {
					columns.append(cols[i].getName()).append(" ").append(
							cols[i].getName()).append(",");
					i++;
				}
			}
			columns.deleteCharAt(columns.length() - 1);
			sql.append(columns).append(" from ").append(name);

			String subWhere = getSubWhere(sd, name);
			if (subWhere != null && subWhere.length() > 0) {
				where.append("(").append(subWhere).append(")");
				sql.append(where);
			} else {
				if (where.length() > orginalLength) {
					where.delete(where.length() - AND.length(), where.length()
							- AND.length() - 1 + AND.length());
					sql.append(where);
				}
			}
			orderby = (String) data.getAttr(name, "orderBy");
			groupby = (String) data.getAttr(name, "groupBy");
			if (orderby != null) {
				sql.append(" order by ").append(orderby);
			}
			if (groupby != null) {
				sql.append(" group by ").append(groupby);
			}
			return new String(sql);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param name
	 * @param row
	 * @param setNull
	 * @param data
	 * @param dc
	 * @return
	 */
	public String buildUpdateSQL(String name, String row, boolean setNull,
			ArrayList al, BizData data, BizData sd, DataDict dc) {
		try {
			int i = 0;
			int fieldsCount = 0;
			int leftColumns = 0;
			StringBuffer sql = new StringBuffer().append("update ")
					.append(name);
			StringBuffer set = new StringBuffer();
			StringBuffer where = new StringBuffer().append(" where ");
			int orginalLength = where.length();
			Vector fields = data.getTableFields(name);
			DBTableDef tabDef = dc.getTableDef(name).duplicate();
			if (fields == null || (fieldsCount = fields.size()) == 0
					|| tabDef == null) {
				return null;
			}
			String newValue = null;
			while (i < fieldsCount) {
				String fieldName = (String) fields.elementAt(i);
				Object o = data.get(name, fieldName, row);
				if (o != null) {
					newValue = o.toString();
				} else {
					newValue = null;
				}
				String oldValue = (String) data.getAttr(name, fieldName, row,
						"oldValue");
				DBColumnDef colDef = tabDef.getColumn(fieldName);
				if (colDef == null) {
					i++;
					continue;
				}
				tabDef.deleteColumn(colDef);
				/* 如果该字段有值，则该值将作为set中的一个字段 */
				if (newValue != null) {
					if (newValue.equalsIgnoreCase("NULL")) {
						set.append(colDef.getName()).append("=NULL")
								.append(",");
					} else if (colDef.getType() == Types.STRING
							|| colDef.getType() == Types.DATE
							|| colDef.getType() == Types.CHAR
							|| colDef.getType() == Types.TIME) {
						set.append(colDef.getName()).append("=").append("'")
								.append(replaceQuota(newValue)).append("'")
								.append(",");
					} else {
						set.append(colDef.getName()).append("=").append(
								newValue).append(",");
					}
				}
				/* 如果该字段有旧值，则该值将作为where中的一个条件 */
				if (oldValue != null) {
					buildWhere(where, name, colDef, oldValue, row, data);
				}
				i++;
			}
			if (set.length() <= 1) {
				return null;
			} else {
				/* 将其他未出现的字段设置为NULL */
				if (setNull) {
					i = 0;
					DBColumnDef[] cols = tabDef.getColumns();
					leftColumns = cols.length;
					while (i < leftColumns) {
						set.append(cols[i].getName()).append("=")
								.append("null").append(",");
						i++;
					}
				}
				/* 删除最后的"," */
				set.deleteCharAt(set.length() - 1);
				sql.append(" set ").append(set).append(" ");
			}
			if (where.length() > orginalLength) {
				/* 删除最后的" and "字符串 */
				where.delete(where.length() - AND.length(), where.length()
						- AND.length() - 1 + AND.length());
				sql.append(where);
			}
			return new String(sql);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param func
	 * @return
	 */
	public SQLFunction checkFunc(String func) {

		String name = null;
		int index = func.indexOf('(');
		if (index < 0) {
			name = func;
		} else {
			name = func.substring(0, index);
		}
		Object val = funcs.get(name);
		if (val == null) {
			return null;
		} else {
			return new SQLFunction(name, ((Integer) val).intValue());
		}
	}

	/**
	 * @param where
	 * @param name
	 * @param colDef
	 * @param fieldValue
	 * @param row
	 * @param data
	 * @return
	 */
	protected String buildWhere(StringBuffer where, String name,
			DBColumnDef colDef, String fieldValue, String row, BizData data) {
		int realType = 0;
		String fieldName = colDef.getName();
		String funcOperW = (String) data.getAttr(name, fieldName, row,
				"functionW");
		String onlyWhere = (String) data.getAttr(name, fieldName, row,
				"onlyWhere");
		String funcOperC = (String) data.getAttr(name, fieldName, row,
				"functionC");
		String parsedFieldName = null;
		SQLFunction func = null;
		if (funcOperW != null) {
			func = checkFunc(funcOperW);
			parsedFieldName = parseFieldName(funcOperW, colDef.getName());
		}
		if (parsedFieldName == null) {
			parsedFieldName = colDef.getName();
		}
		String relatOper = (String) data.getAttr(name, fieldName, row,
				"relation");
		if (relatOper == null) {
			relatOper = "=";
		}
		if (func == null) {
			realType = colDef.getType();
		} else {
			realType = func.getType();
		}
		if (fieldValue != null) {
			if (realType == Types.STRING || realType == Types.DATE
					|| realType == Types.TIMESTAMP || realType == Types.TIME
					|| realType == Types.CHAR) {
				where.append(parsedFieldName).append(" ").append(relatOper)
						.append(" ").append("'").append(
								replaceQuota(fieldValue)).append("'").append(
								AND);
			} else {
				where.append(parsedFieldName).append(" ").append(relatOper)
						.append(" ").append(replaceQuota(fieldValue)).append(
								AND);
			}
		}
		if (onlyWhere != null && onlyWhere.compareToIgnoreCase("yes") == 0) {
			parsedFieldName = null;
		}
		if (funcOperC != null) {
			parsedFieldName = parseFieldName(funcOperC, colDef.getName());
		} else {
			parsedFieldName = colDef.getName();
		}
		return parsedFieldName;
	}
}
