/**
 *all rights reserved,@copyright 2003
 */
package com.dream.app.sys;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.blc.AbstractBLC;
import com.dream.bizsdk.common.blc.BLContext;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.databus.BizData;

/**
 * Description:
 * User: chuguanghua
 * Date: 2004-3-8
 * Time: 12:35:41
 */
public class ImageBLC extends AbstractBLC {

    private DAO coreDAO = null;
    private BizData dims = new BizData();
    private BizData conds = new BizData();

    public boolean init(BLContext context) {
        super.init(context);
        coreDAO = context.getDAO("core");
        load();
        return true;
    }

    public void load() {
        ResultSet rs = null;
        dims.clear();
        conds.clear();
        Connection conn = null;
        conn = coreDAO.getConnection();
        try {
            rs = coreDAO.executeQuery("select * from DRMSubjectCond",conn);
            while (rs.next()) {
                String cCode = rs.getString("cCode");
                String name = rs.getString("name");
                String fieldCode = rs.getString("fieldCode");
                String valueType = rs.getString("valueType");
                conds.add("DRMSubjectCond", "name", cCode, name);
                conds.add("DRMSubjectCond", "fieldCode", cCode, fieldCode);
                conds.add("DRMSubjectCond", "valueType", cCode, valueType);
            }
            rs.getStatement().close();
            rs = coreDAO.executeQuery("select * from DRMSubjectDim",conn);
            while (rs.next()) {
                String dCode = rs.getString("dCode");
                String name = rs.getString("name");
                String fieldCode = rs.getString("fieldCode");
                String valueType = rs.getString("valueType");
                dims.add("DRMSubjectDim", "name", dCode, name);
                dims.add("DRMSubjectDim", "fieldCode", dCode, fieldCode);
                dims.add("DRMSubjectDim", "valueType", dCode, valueType);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
			try {
				if (rs != null) {

					rs.getStatement().close();
					rs.close();
					rs = null;

				}
				if (null != conn) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
    }

    /**
     * get the Image data for a given request data;
     *
     * @param rd
     * @return
     */
    public int getImageData(BizData rd) {
        Vector types = new Vector();
        BizData imageData = null;
        ResultSet rs = null;
        try {
            String scode = (String) rd.get("subject");
            BizData subDef = getSubjectDef(scode);
            String daoName = (String) subDef.get("DRMSubject", "daoname", "0");
            DAO dao = _context.getDAO(daoName);
            String type = dao.getType();
            String sql = makeSql(rd, subDef, type, types);
            rs = execSql(sql, dao);
            if (rs != null) {
                imageData = makeImageData(rs, subDef, types);
                imageData.add("imageType", rd.getString("imageType"));
                imageData.add("dim1Name", rd.get("dim", 0));
                if (types.size() > 1) {
                    imageData.add("dim2Name", rd.get("dim", 1));
                }
                rd.add("imageData", imageData);
                return 1;
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //close the statement if necessary;
            if (rs != null) {
                try {
                    rs.getStatement().close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }
        return -1;
    }

    /**
     * get the DRMSubject,DRMSubjectDim,DRMSubjectCond records matching
     * the sCode.
     *
     * @param sCode the code of the target subject;
     * @return
     */
    private BizData getSubjectDef(String sCode) throws SQLException {
        BizData subDef = new BizData();
        subDef.add("DRMSubject", "sCode", sCode);
        coreDAO.expand("DRMSubject", subDef);
        return subDef;
    }

    /**
     * make a sql according to the request data and subject definition;
     *
     * @param rd
     * @param subDef
     * @return
     */
    public String makeSql(BizData rd, BizData subDef, String daoType, Vector types) {
        int i = 0;
        boolean hasWhere = false;
        String dim1 = null;
        String dim2 = null;

        int len = rd.getArrayLength("dim");
        if (len < 1) {
            return null;
        } else {
            dim1 = (String) rd.get("dim", 0);
            if (len > 1) {
                dim2 = (String) rd.get("dim", 1);
            }
        }

        StringBuffer sb = new StringBuffer(512);
        String fromTables = (String) subDef.get("DRMSubject", "fromTables", "0");
        String tableJoins = (String) subDef.get("DRMSubject", "tableJoins", "0");
        String subField = (String) subDef.get("DRMSubject", "fieldCode", "0");
        String dim1Field = (String) dims.get("DRMSubjectDim", "fieldCode", dim1);
        String dim1Type = (String) dims.get("DRMSubjectDim", "valueType", dim1);
        String dim2Field = (String) dims.get("DRMSubjectDim", "fieldCode", dim2);
        String dim2Type = (String) dims.get("DRMSubjectDim", "valueType", dim2);
        sb.append("select ");
        sb.append(subField);
        sb.append(" \"subject\" ");
        sb.append(",");
        sb.append(dim1Field);
        sb.append(" \"dim1\" ");
        types.add(dim1Type);
        if (dim2 != null) {
            sb.append(",");
            sb.append(dim2Field);
            sb.append(" \"dim2\" ");
            types.add(dim2Type);
        }

        sb.append(" from ");
        sb.append(fromTables);
        if (tableJoins != null) {
            sb.append(" where ");
            sb.append(tableJoins);
            hasWhere = true;
        }

        //process conditions;
        int count = rd.getTableRowsCount("DRMSubjectCond");
        String[] rowIDs = rd.getRowIDs("DRMSubjectCond");
        while (i < count) {
            String cCode = (String) rd.get("DRMSubjectCond", "cCode", rowIDs[i]);
            String minValue = (String) rd.get("DRMSubjectCond", "minValue", rowIDs[i]);
            String maxValue = (String) rd.get("DRMSubjectCond", "maxValue", rowIDs[i]);
            String valueType = (String) conds.get("DRMSubjectCond", "valueType", cCode);
            if (minValue != null) {
                if (!hasWhere) {
                    sb.append(" where ");
                    hasWhere = true;
                } else {
                    sb.append(" and ");
                }
                sb.append(conds.getString("DRMSubjectCond", "fieldCode", cCode));
                sb.append(">=");
                if (valueType.compareToIgnoreCase("string") == 0) {
                    sb.append("'");
                    minValue = minValue.replaceAll("'", "''");
                } else if (valueType.compareToIgnoreCase("date") == 0) {
                    if (daoType.compareToIgnoreCase(SysVar.DB_ORACLE) == 0) {
                        sb.append("to_date('YYYY-MM-DD','");
                    } else {
                        sb.append("'");
                    }
                } else if (valueType.compareToIgnoreCase("time") == 0) {
                    if (daoType.compareToIgnoreCase(SysVar.DB_ORACLE) == 0) {
                        sb.append("to_date('YYYY-MM-DD HH:MI:SS','");
                    } else {
                        sb.append("'");
                    }
                }
                sb.append(minValue);
                if (valueType.compareToIgnoreCase("string") == 0) {
                    sb.append("'");
                } else if (valueType.compareToIgnoreCase("date") == 0 || valueType.compareToIgnoreCase("time") == 0) {
                    if (daoType.compareToIgnoreCase(SysVar.DB_ORACLE) == 0) {
                        sb.append("')");
                    } else {
                        sb.append("'");
                    }
                }
            }
            if (maxValue != null) {
                if (!hasWhere) {
                    sb.append(" where ");
                    hasWhere = true;
                } else {
                    sb.append(" and ");
                }
                sb.append(conds.getString("DRMSubjectCond", "fieldCode", cCode));
                sb.append("<=");
                if (valueType.compareToIgnoreCase("string") == 0) {
                    sb.append("'");
                    minValue = minValue.replaceAll("'", "''");
                } else if (valueType.compareToIgnoreCase("date") == 0) {
                    if (daoType.compareToIgnoreCase(SysVar.DB_ORACLE) == 0) {
                        sb.append("to_date('YYYY-MM-DD','");
                    } else {
                        sb.append("'");
                    }
                } else if (valueType.compareToIgnoreCase("time") == 0) {
                    if (daoType.compareToIgnoreCase(SysVar.DB_ORACLE) == 0) {
                        sb.append("to_date('YYYY-MM-DD HH:MI:SS','");
                    } else {
                        sb.append("'");
                    }
                }
                sb.append(maxValue);
                if (valueType.compareToIgnoreCase("string") == 0) {
                    sb.append("'");
                } else if (valueType.compareToIgnoreCase("date") == 0 || valueType.compareToIgnoreCase("time") == 0) {
                    if (daoType.compareToIgnoreCase(SysVar.DB_ORACLE) == 0) {
                        sb.append("')");
                    } else {
                        sb.append("'");
                    }
                }
            }
            i++;
        }

        sb.append(" group by ");
        sb.append(dim1Field);
        if (dim2 != null) {
            sb.append(",");
            sb.append(dim2Field);
            sb.append(" order by ");
            sb.append(dim2Field);
        }

        return new String(sb);
    }

    /**
     * execute the sql via a specified DAO object, and return the resultset
     * object.
     *
     * @param sql
     * @param dao the name of the dao object;
     * @return
     */
    private ResultSet execSql(String sql, DAO dao) throws SQLException {
        if (dao != null) {
        	Connection conn = dao.getConnection();
            ResultSet rs = dao.executeQuery(sql,conn);
            return rs;
        } else {
            return null;
        }
    }

    /**
     * make a BizData object containing the ImageData needed to make a
     * JPEG object.
     *
     * @param rs     the resultset object;
     * @param subDef
     * @return
     */
    private BizData makeImageData(ResultSet rs, BizData subDef, Vector types) throws SQLException {
        int i = 0;
        BizData ia = new BizData();
        boolean hasDim2 = types.size() > 1 ? true : false;
        while (rs.next()) {
            double sv = rs.getDouble("subject");
            Object v1 = rs.getObject("dim1");
            ia.add("ImageData", "subject", i, new Double(sv));
            ia.add("ImageData", "dim1", i, v1);
            if (hasDim2) {
                Object v2 = rs.getObject("dim2");
                ia.add("ImageData", "dim2", i, v2);
            }
            i++;
        }
        ia.add("title", subDef.getString("DRMSubject", "title", "0"));
        ia.add("subjectName", subDef.getString("DRMSubject", "name", "0"));
        ia.add("dim1Type", types.elementAt(0));
        if (hasDim2) {
            ia.add("dim2Type", types.elementAt(1));
            ia.add("dimsCount", new Integer(2));
        } else {
            ia.add("dimsCount", new Integer(1));
        }
        return ia;
    }
}
