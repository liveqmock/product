/*************************************************************
 * 机构的增加，删除，修改和查询（查询单个、多个、下级机构）
 * 机构序列码的维护。
 *
 *
 ************************************************************/

package com.dream.app.hr.org;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.database.datadict.Types;
import com.dream.bizsdk.common.databus.BizData;


public class Organization extends DBBLC {

    public Organization() {
        entityName = "HROrganization";
        version = 2;
    }
    
    /**
     * 插入一个机构的信息，需要自动产生机构内部编号，机构序列码;
     * in HROrganization-record,Organization to be inserted
     * to the database.
     *
     * @param rd BizData,request data;
     * @param sd BizData,session data;
     * @return >0-succeed and is the id of the org,<0 errors;
     */
    public int insert(BizData rd, BizData sd) throws SQLException {
        int rows = 0;
        String parentSeq = null;

        String parent = (String) rd.get(entityName, "parent_orgID", 0);
        
        //get paret org's seqence;
        if (parent != null) {
            BizData d = new BizData();
            d.add(entityName, "orgID", 0, parent);
            rows = expand(d, (BizData) null);
            if (rows == 1) {
                parentSeq = d.getString(entityName, "orgSeq", 0);
            }
        }
        //create this org's sequence;
        int nextID = 1;
        try {
            nextID = ((Integer) coreDAO.getFieldValue(entityName, "nvl(max(orgID)+1,1)", Types.INT)).intValue();
        } catch (Exception e) {
        }
        rd.add(entityName, "orgID", 0, new Integer(nextID).toString());

        if (parentSeq == null) {
            rd.add(entityName, "orgSeq", new Integer(nextID).toString() + ".");
        } else {
            rd.add(entityName, "orgSeq", parentSeq + new Integer(nextID).toString() + ".");
        }
        //insert this org;
        Connection conn = null;
        try {
            BizData d = new BizData();
            conn = coreDAO.getConnection();
            coreDAO.beginTrasct(conn);
            OrgRole or = (OrgRole) super.getBLC("com.dream.app.hr.org.OrgRole");
            if(null == parent)
            	rd.add(entityName, "parent_orgID", 0, "0");
            rows = coreDAO.insert(entityName, rd, conn);
            d.copyEntity(rd, "HROrganization");
            d.copyEntity(rd, "DRMRole");
            or.save(d, sd, conn);
            coreDAO.commitTrasct(conn);
        } catch (SQLException e) {
            coreDAO.rollbackTrasct(conn);
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return rows;
    }

    /**
     * 根据ORGID查询该机构的相关信息
     * @param rd
     * @param sd
     * @return
     */
    public int queryOrgInfoByOrgID(BizData rd, BizData sd) {
    	
    	rd.add("DispMode", rd.getString("DispMode"));
    	try {
			coreDAO.select("hrorganization", rd);
			String topOrgID = recursion4ID("hrorganization", "PARENT_ORGID", "ORGID", rd.getStringByDI("hrorganizations", "ORGID", 0), "0", rd);
			rd.add("topOrgID", topOrgID);
			BizData data = new BizData();
			data.add("hrorganization", "ORGID", topOrgID);
			coreDAO.select(data);
			String orgName = data.getStringByDI("hrorganizations", "name", 0);
			rd.add("topOrgName", orgName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 88;
    }

    /**
     * 删除一个机构及该机构中的员工,职位,同时更新该机构的下级
     * 机构的序列码.
     * in orgID-int,ID of the org to be deleted;
     *
     * @param rd BizData,request data;
     * @param sd BizData,session data;
     * @return 1-succeed,<0 errors;
     */
    public int delete(BizData rd, BizData sd) throws SQLException {
        int rows = 0;
        String oldSeq = null;
        String newSeq = "";
        String orgID = rd.getString("HROrganization", "orgID", 0);
        if (orgID.length() < 1) {
            return SysError.BL_PARAM_ERROR;
        }
        BizData d = new BizData();
        d.add(entityName, "orgID", orgID);
        rows = coreDAO.expand(entityName, d);
        if (rows != 1) {
            return SysError.ENTITY_NOT_EXIST;
        }
        oldSeq = (String) d.get(entityName, "orgSeq", 0);
        //get parent
        String parent = d.getString(entityName, "parent_orgID", 0);
        //get parent org's sequence;
        if (parent.length() > 0 && parent.compareTo("0") != 0) {
            BizData d2 = new BizData();
            d2.add("HROrganizaion", "orgID", 0, parent);
            rows = expand(d2, (BizData) null);
            if (rows == 1) {
                newSeq = d2.getString(entityName, "orgSeq", 0);
            }
        }
        Connection conn = null;
        try {
            BizData d3 = new BizData();
            d3.add("HROrgRole", "orgID", orgID);
            d3.add(entityName, "orgID", orgID);
            d3.addAttr("HROrgRole", "NO", new Integer(0));
            d3.addAttr(entityName, "NO", new Integer(1));

            conn = coreDAO.getConnection();
            coreDAO.beginTrasct(conn);
            coreDAO.delete(d3, conn);
            updateOrgSeq(oldSeq, newSeq, conn);
            coreDAO.commitTrasct(conn);
        } catch (SQLException sqle) {
            coreDAO.rollbackTrasct(conn);
            throw sqle;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return rows;
    }
    
    public int query(BizData rd, BizData sd){
    	try {
			super.query(rd, sd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		rd.add("fwd", rd.getString("fwd"));
		return 98;
    }

    /**
     * 更新一个机构信息需要更新三张表：机构表，资质附件表和部门表，创建一个机构默认创建一个同名的部门
     *
     * @param rd BizData,request data;
     * @param sd BizData,session data;
     * @return 1-succeed,<0 errors;
     */
    public int update(BizData rd, BizData sd) {
    	
    	Connection conn = coreDAO.getConnection();
        BizData data = new BizData();
        data.add("HRCOPYOFLICENCE", "orgid", rd.getStringByDI("HROrganization", "orgid", 0));
        int rows;
		try {
			rows = coreDAO.select(data);
	    	byte[] sfzhmhjFile = (byte[])rd.get("HRCOPYOFLICENCE", "COPYOFSFZMHM", 0);
	    	byte[] licenceFile = (byte[])rd.get("HRCOPYOFLICENCE", "COPYOFLICENCE", 0);
	    	data.remove("HRCOPYOFLICENCEs");
	    	coreDAO.beginTrasct(conn);
	    	//更新机构表
	    	coreDAO.update("HROrganization", rd, conn);
	        //更新附件表
	        if(rows > 0){
	        	if(sfzhmhjFile != null || licenceFile != null){
	        		updateOrgLicence(rd.getStringByDI("HROrganization", "orgid", 0), conn, sfzhmhjFile, licenceFile);
	        	}
	        }else{
	        	if(sfzhmhjFile != null || licenceFile != null){
	        		data.copyEntity(rd, "HRCOPYOFLICENCE");
	        		data.add("HRCOPYOFLICENCE", "id", queryMaxIDByPara("hrcopyoflicence", "id", null));
	        		data.add("HRCOPYOFLICENCE", "orgid", rd.getStringByDI("HROrganization", "orgid", 0));
	        		coreDAO.insert(data, conn);
	        	}
	        }
	        //更新机构的部门表
	        data.remove("HRCOPYOFLICENCE");
	        rd.add("HRDEPARTMENT", "DEPTNAME", rd.getStringByDI("HROrganization", "NAME", 0));
	        coreDAO.update("HRDEPARTMENT", rd, sd, conn);
	        coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally{
			if(conn != null){
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		rd.add("msg", "Y");//修改成功
        return 98;
    }
    
    private void updateOrgLicence(String orgid, Connection conn,byte[] sfzmhm, byte[] licence) throws SQLException {
    	String sql = "update hrcopyoflicence l set l.copyoflicence=? , l.copyofsfzmhm=? where l.orgid=?";
    	PreparedStatement preStmt = conn.prepareStatement(sql);
    	preStmt.setBytes(1, licence);
    	preStmt.setBytes(2, sfzmhm);
    	preStmt.setInt(3, Integer.parseInt(orgid));
    	preStmt.executeUpdate();
		preStmt.close();
		preStmt = null;
    }

    /**
     * 查询一个机构中的所有的部门列表.
     * in-orgID
     * out-HROrganizations-depts list;
     *
     * @return count of records returned;<0 if errors happened;
     */
    public int queryDeptsInCom(BizData rd, BizData sd) throws SQLException {
        int rows = 0;
        String orgID = (String) rd.get("orgID");
        if (orgID == null) {
            return SysError.BL_PARAM_ERROR;
        }
        rd.add(entityName, "orgID", orgID);
        rows = coreDAO.expand(entityName, rd);
        if (rows == 0) {
            return SysError.ENTITY_NOT_EXIST;
        }
        String orgSeq = (String) rd.get(entityName, "orgSeq", 0);

        BizData d = new BizData();
        d.add(entityName, "orgSeq", orgSeq);
        d.add(entityName, "type", "D");
        d.addAttr(entityName, "orgSeq", 0, "relation", "like");
        return coreDAO.select(rd, d, true);
    }

    /**
     * 当更新某个机构的上级,或者删除某个机构时,需要更新
     * 该机构的所有的下级的序列码.
     * 如果是更新的情况:原序列码为更新前的序列码,新序列码为更新后
     * 的序列码.
     * 如果为删除操作,则被删除机构的直接下级机构的上级更新为被删除
     * 机构的上级机构.此时原序列码为被删除机构的序列码,新序列码为被
     * 删除机构的上级的序列码(如果没有上级,则为空字符串).
     * oldSeq-String,被更新(删除)机构的原序列码.
     * newSeq-String,被更新(删除)机构的新序列码.
     *
     * @return >=0,成功更新的记录数目,<0 如果有错误.
     */
    protected int updateOrgSeq(String oldSeq, String newSeq, Connection conn) throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("update ").append(entityName).append(" set orgseq='");
        sb.append(newSeq).append("'+substr(orgSeq," + (oldSeq.length() + 1) + ",length(orgSeq)-" + oldSeq.length() + ")").append(" where orgSeq like '");
        sb.append(oldSeq).append("%.'");
        return coreDAO.executeUpdate(new String(sb), null, conn);
    }
    
    public int queryOrgAndUserByUserno(BizData rd,BizData sd){
    	
    	String sql = "select o.name cmpny_name,u.username,e.business_mobile,\n" +
			"e.business_fax,e.emcontacttel business_phone,e.qq business_qq \n" +
			"from drmuser u,hremployee e,hrorganization o\n" +
			"where u.empid=e.empid and e.orgid=o.orgid\n" +
			"and u.userno="+sd.getString("userno");
    	try {
			coreDAO.executeQuery(sql, "userOrgRs", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 98;
    }
}