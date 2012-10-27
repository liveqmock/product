package com.trmdj.lineRelease;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.trmdj.businessPlan.groupMng.GroupLineDetail;

/**
 * @ClassName: LineMngBLC
 * @Description: (��·����-�鿴��·-������·-�޸���·����Ϣ)
 * @author Kale ym_x@qq.com
 * @date 2011-7-1 ����03:07:17
 * 
 */
public class DjLineMngBLC extends DBBLC {
	private static Logger log = Logger.getLogger(DjLineMngBLC.class);

	public DjLineMngBLC() {
		this.entityName = "TA_DJ_LINEMNG";
	}

	public int djBJBQuery(BizData rd, BizData sd) {
		String XLID = rd.getStringByDI("TA_DJ_LINEMNG", "XLID", 0);
		String XCMXList = "select t.* from TA_DJ_LINEDETAIL t where t.xlid="
				+ XLID + " Order By  t.rq";
		try {
			coreDAO.executeQuery(XCMXList, "XCMXList", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
	}

	/**
	 * @Title: listLineByCreater
	 * @Description: (��ʼ�����з�������·��Ϣ)
	 * @param @param rd
	 * @param @param sd
	 * @param @return �趨�ļ�
	 * @return int ��������
	 * @throws
	 */
	public int djListLineByCreater(BizData rd, BizData sd) {

		Connection conn = coreDAO.getConnection();
		String XLMC = rd.getStringByDI("TA_DJ_LINEMNG", "XLMC", 0);

		if (!"".equals(XLMC)) {
			rd.add("TA_DJ_LINEMNG", "XLMC", "%" + XLMC + "%");
		}
		rd.add("TA_DJ_LINEMNG", "ORGID", sd.getString("ORGID"));
		try {
			coreDAO.selectPage("TA_DJ_LINEMNG", rd, true, conn);
			//��������
			coreDAO.select("xzqh", rd, true, conn);
			conn.close();
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		return 909;
	}
	
	/**
	 * ��Ʒ���б�
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int lineList4PO(BizData rd, BizData sd){
		
		Connection conn = coreDAO.getConnection();
		String XLMC = rd.getStringByDI("TA_DJ_LINEMNG", "XLMC", 0);

		if (!"".equals(XLMC)) {
			rd.add("TA_DJ_LINEMNG", "XLMC", "%" + XLMC + "%");
		}
		rd.add("TA_DJ_LINEMNG", "ORGID", sd.getString("ORGID"));
		rd.add("TA_DJ_LINEMNG", "SFYX", "Y");
		try {
			//��·������Ϣ
			coreDAO.selectPage("TA_DJ_LINEMNG", rd, true, conn);
			//��������
			coreDAO.select("xzqh", rd, true, conn);
			rd.remove("xzqh");
			//��·�۸�
			StringBuffer sql = new StringBuffer();
			sql.append("select x.jg,x.dfc,d.dmsm1 jglx,x.bz,x.xlid\n");
			sql.append("from TA_DJ_XLJG x,drmuser u,dmsm d\n");
			sql.append("where x.orgid=u.orgid and x.deptid=u.deptid\n");
			sql.append("and d.dmz=x.jglx and d.dmlb=4\n");
			sql.append("and x.orgid=").append(sd.getString("orgid"));
			coreDAO.executeQuery(sql.toString(), "TA_DJ_XLJGs", rd, conn);
			conn.close();
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		return 88;
	}
	
	/**
	 * �鿴��·��Ϣ
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int viewDJLine(BizData rd, BizData sd){
		
		Connection conn = coreDAO.getConnection();
		
		try {
			//��·��ϸ
			queryLineDetail(rd, conn);
			//��·
			coreDAO.select("TA_DJ_LINEMNG", rd, true, conn);
			//��������
			coreDAO.select("xzqh", rd, true, conn);
			//��·�۸�
			StringBuffer sql = new StringBuffer();
			sql.append("select x.jg,x.dfc,d.dmsm1 jglx,x.bz\n");
			sql.append("from TA_DJ_XLJG x,drmuser u,dmsm d\n");
			sql.append("where x.orgid=u.orgid and x.deptid=u.deptid\n");
			sql.append("and d.dmz=x.jglx and d.dmlb=4\n");
			sql.append("and x.orgid=").append(sd.getString("orgid")).append("and x.xlid=").append(rd.getStringByDI("TA_DJ_LINEMNG", "xlid", 0));
			coreDAO.executeQuery(sql.toString(), "TA_DJ_XLJGs", rd, conn);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 98;
	}
	

	public int djPrintLineXCDList(BizData rd, BizData sd) {
		String XLMC = rd.getStringByDI("TA_DJ_LINEMNG", "XLMC", 0);
		if (!"".equals(XLMC)) {
			rd.add("TA_DJ_LINEMNG", "XLMC", "%" + XLMC + "%");
			rd.addAttr("TA_DJ_LINEMNG", "XLMC", "0", "relation", "like");
		}
		try {
			coreDAO.selectPage("TA_DJ_LINEMNG", rd);
		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		return 909;
	}

	/**
	 * @throws UnsupportedEncodingException
	 * @throws SQLException
	 * @Title: insertLineBase
	 * @Description: (��ӵؽ���·��Ϣ)
	 * @param @param lineBaseID
	 * @param @param conn
	 * @param @param rd
	 * @param @throws SQLException
	 * @param @throws UnsupportedEncodingException �趨�ļ�
	 * @return void ��������
	 * @throws
	 */
	public int djInsert(BizData rd, BizData sd) {
		int XLID = queryMaxIDByPara("TA_DJ_LINEMNG", "XLID", null);// ȡ�������·ID
		rd.add("XLID", XLID);
		rd.add("next", rd.getString("next"));
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);

			rd.add("TA_DJ_LINEMNG", "XLID", 0, XLID);
			rd.add("TA_DJ_LINEMNG", "USERNO", 0, sd.getString("userno"));
			rd.add("TA_DJ_LINEMNG", "orgid", 0, sd.getString("orgid"));
			coreDAO.insert("TA_DJ_LINEMNG", rd, conn);// �����·������Ϣ
			rd.remove("TA_DJ_LINEMNG");

			insertLineBlob(XLID, sd.getString("orgid"), rd, conn);// �����·��ϸ��Ϣ

			djAddLinePrice(XLID, sd.getString("orgid"), rd, conn);// �����·�۸���Ϣ
			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != conn) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 909;
	}

	/**
	 * @throws SQLException
	 * @Title: djAddLinePrice
	 * @Description:(�����·�۸�)
	 * @param @param rd
	 * @param @param XLID
	 * @param @param conn
	 * @param @throws SQLException �趨�ļ�
	 * @return void ��������
	 * @throws
	 */
	public void djAddLinePrice(int XLID, String orgid, BizData rd, Connection conn) throws SQLException {
		String[] rowIDs = rd.getRowIDs("TA_DJ_XLJG");
		for (int i = 0; i < rowIDs.length; i++) {
			int priceID = queryMaxIDByPara("TA_DJ_XLJG", "ID", null);
			String jg = rd.getString("TA_DJ_XLJG", "JG", rowIDs[i]);// �۸�
			String dfc = rd.getString("TA_DJ_XLJG", "DFC", rowIDs[i]);// ������
			rd.add("TA_DJ_XLJG", "ID", rowIDs[i], priceID);
			rd.add("TA_DJ_XLJG", "XLID", rowIDs[i], XLID);
			rd.add("TA_DJ_XLJG", "JGLX", rowIDs[i], rd.getString("TA_DJ_XLJG", "JGLX", rowIDs[i]));
			rd.add("TA_DJ_XLJG", "JG", rowIDs[i], "".equals(jg) ? "0" : jg);
			rd.add("TA_DJ_XLJG", "DFC", rowIDs[i], "".equals(dfc) ? "0" : dfc);
			rd.add("TA_DJ_XLJG", "BZ", rowIDs[i], rd.getString("TA_DJ_XLJG", "BZ", rowIDs[i]));
			rd.add("TA_DJ_XLJG", "orgid", rowIDs[i], orgid);
		}
		coreDAO.insert("TA_DJ_XLJG", rd, conn);
		rd.remove("TA_DJ_XLJG");
	}

	/**
	 * ��·�޸�
	 * 
	 * @param rd
	 * @param sd
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public int djEditLineBase(BizData rd, BizData sd) {
		rd.add("next", rd.getString("next"));
		String XLID = rd.getStringByDI("TA_DJ_LINEMNG", "XLID", 0);
		if ("".equals(XLID)) {
			return -101;
		}
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);

			rd.addAttr("TA_DJ_LINEMNG", "XLID", 0, "oldValue", XLID);
			rd.addAttr("TA_DJ_LINEMNG", "orgid", 0, "oldValue", sd.getString("orgid"));
			coreDAO.update("TA_DJ_LINEMNG", rd, conn);// ������·������Ϣ

			delLinePriceBlob(XLID, sd.getString("orgid"), conn);// ɾ����·�۸���Ϣ

			djAddLinePrice(Integer.parseInt(XLID), sd.getString("orgid"), rd, conn);// �����·�۸���Ϣ

			insertLineBlob(Integer.parseInt(XLID), sd.getString("orgid"), rd, conn);// �޸���·�г���ϸ

			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != conn) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 49;
	}

	/**
	 * �����·�г���ϸ
	 * 
	 * @param XLID
	 * @param rd
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	public int insertLineBlob(int XLID, String orgid, BizData rd, Connection conn) throws SQLException, UnsupportedEncodingException {
		String[] bRows = rd.getRowIDs("TA_DJ_LINEDETAIL");
		for(int i = 0; i < bRows.length; i++){
			int Id = queryMaxIDByPara("TA_DJ_LINEDETAIL", "ID", null);
			String XCMX = rd.getString("TA_DJ_LINEDETAIL", "XCMX", bRows[i]);
			String rq = rd.getString("TA_DJ_LINEDETAIL", "rq", bRows[i]);
			String breakfast = rd.getString("TA_DJ_LINEDETAIL", "breakfast", bRows[i]);
			String cmeal = rd.getString("TA_DJ_LINEDETAIL", "cmeal", bRows[i]);
			String supper = rd.getString("TA_DJ_LINEDETAIL", "supper", bRows[i]);
			String zs = rd.getString("TA_DJ_LINEDETAIL", "zs", bRows[i]);
			String cb = rd.getString("TA_DJ_LINEDETAIL", "cb", bRows[i]);
			String zsbz = rd.getString("TA_DJ_LINEDETAIL", "zsbz", bRows[i]);
			
			String sql = "INSERT INTO TA_DJ_LINEDETAIL (ID,XLID,XCMX,RQ,BREAKFAST,CMEAL,SUPPER,ZS,CB,ZSBZ,orgid) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, Id);
			preStmt.setInt(2, XLID);
			preStmt.setBytes(3, XCMX.getBytes("GBK"));
			preStmt.setString(4, rq);
			preStmt.setString(5, breakfast);
			preStmt.setString(6, cmeal);
			preStmt.setString(7, supper);
			preStmt.setString(8, zs);
			preStmt.setString(9, cb);
			preStmt.setInt(10, Integer.parseInt(zsbz));
			preStmt.setString(11, orgid);
			preStmt.executeUpdate();
			preStmt.close();
			preStmt = null;
		}
		return 999;
	}

	private void delLinePriceBlob(String XLID, String orgid, Connection conn) throws SQLException {
		BizData data = new BizData();
		data.add("TA_DJ_XLJG", "XLID", XLID);
		data.add("TA_DJ_XLJG", "orgid", orgid);
		coreDAO.delete(data, conn);
		data.remove("TA_DJ_XLJG");
 
		data.add("TA_DJ_LINEDETAIL", "XLID", XLID);
		data.add("TA_DJ_LINEDETAIL", "orgid", orgid);
		coreDAO.delete(data, conn);
		data.remove("TA_DJ_LINEDETAIL");
	}
	

	// ��ѯ��·��ϸ
	public void queryLineDetail(BizData rd, Connection conn) throws SQLException {
		String xlid = rd.getStringByDI("TA_DJ_LINEDETAIL", "XLID", 0);
		String sql = "select d.* from TA_DJ_LINEDETAIL d where d.xlid=" + xlid;
		sql += " order by d.rq";
		coreDAO.executeQuery(sql, "TA_DJ_LINEDETAILs", rd);

		List<GroupLineDetail> list = new ArrayList<GroupLineDetail>();
		for (int i = 0; i < rd.getTableRowsCount("TA_DJ_LINEDETAILs"); i++) {
			GroupLineDetail groupDetail = new GroupLineDetail();
			groupDetail.setRq(rd.getStringByDI("TA_DJ_LINEDETAILs", "rq", i));
			groupDetail.setXcmx(rd.getStringByDI("TA_DJ_LINEDETAILs", "xcmx", i));
			groupDetail.setBreakfast(rd.getStringByDI("TA_DJ_LINEDETAILs","breakfast", i));
			groupDetail.setCmeal(rd.getStringByDI("TA_DJ_LINEDETAILs", "cmeal", i));
			groupDetail.setSupper(rd.getStringByDI("TA_DJ_LINEDETAILs", "supper",i));
			groupDetail.setZs(rd.getStringByDI("TA_DJ_LINEDETAILs", "zs", i));
			groupDetail.setCb(rd.getStringByDI("TA_DJ_LINEDETAILs", "cb", i));
			groupDetail.setZsbz(rd.getStringByDI("TA_DJ_LINEDETAILs", "zsbz", i));
			list.add(groupDetail);
		}

		rd.add("lineDetail", list);
	}
	
	// ��ѯ��·��ϸ
	public int queryLineDetail(BizData rd, BizData sd) throws SQLException {
		String xlid = rd.getStringByDI("TA_DJ_LINEDETAIL", "XLID", 0);
		String sql = "select d.* from TA_DJ_LINEDETAIL d where d.xlid=" + xlid;
		sql += " order by d.rq";
		coreDAO.executeQuery(sql, "TA_DJ_LINEDETAILs", rd);

		List<GroupLineDetail> list = new ArrayList<GroupLineDetail>();
		for (int i = 0; i < rd.getTableRowsCount("TA_DJ_LINEDETAILs"); i++) {
			GroupLineDetail groupDetail = new GroupLineDetail();
			groupDetail.setRq(rd.getStringByDI("TA_DJ_LINEDETAILs", "rq", i));
			groupDetail.setXcmx(rd.getStringByDI("TA_DJ_LINEDETAILs", "xcmx", i));
			groupDetail.setBreakfast(rd.getStringByDI("TA_DJ_LINEDETAILs","breakfast", i));
			groupDetail.setCmeal(rd.getStringByDI("TA_DJ_LINEDETAILs", "cmeal", i));
			groupDetail.setSupper(rd.getStringByDI("TA_DJ_LINEDETAILs", "supper",i));
			groupDetail.setZs(rd.getStringByDI("TA_DJ_LINEDETAILs", "zs", i));
			groupDetail.setCb(rd.getStringByDI("TA_DJ_LINEDETAILs", "cb", i));
			groupDetail.setZsbz(rd.getStringByDI("TA_DJ_LINEDETAILs", "zsbz", i));
			list.add(groupDetail);
		}

		rd.add("lineDetail", list);
		return 8;
	}
	
	public int delete(BizData rd, BizData sd){
		
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			coreDAO.deleteEntity("TA_DJ_LINEMNG", rd, conn);
			coreDAO.deleteEntity("TA_DJ_XLJG", rd, conn);
			coreDAO.deleteEntity("TA_DJ_LINEDETAIL", rd, conn);
			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != conn) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return 90;
	}
	
	/**
	 * ������·��ʼ��
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int djInitAddLine(BizData rd, BizData sd){
		
		Connection conn = coreDAO.getConnection();
		try {

			//��������
			coreDAO.select("xzqh", rd, true, conn);
			//�û�
			rd.add("HRDEPARTMENT", "orgid", sd.getString("orgid"));
			rd.add("HRDEPARTMENT", "SFBSC", "Y");
			coreDAO.select("HRDEPARTMENT", rd, true, conn);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 98;
	}
	
	/**
	 * ��·�޸ĳ�ʼ��
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int djEditInitLine(BizData rd, BizData sd){
		
		Connection conn = coreDAO.getConnection();
		try{
			//��·
			coreDAO.select("TA_DJ_LINEMNG", rd, true, conn);
			//��������
			coreDAO.select("xzqh", rd, true, conn);
			//��·��ϸ
			queryLineDetail(rd, conn);
			//��·�۸�
			coreDAO.select("TA_DJ_XLJG", rd, true, conn);
			//�û�
			rd.add("HRDEPARTMENT", "orgid", sd.getString("orgid"));
			rd.add("HRDEPARTMENT", "SFBSC", "Y");
			coreDAO.select("HRDEPARTMENT", rd, true, conn);
			conn.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 88;
	}
	

	/**
	 * ��ӡ��·�г���ϸ
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int djPrintLineXCD(BizData rd, BizData sd){
		
		Connection conn = coreDAO.getConnection();
		
		try {
			//��·��ϸ��������Ϣ
			StringBuffer sql = new StringBuffer();
			sql.append("select m1.dmsm1 zsbz,l.xlmc,l.fbjh,l.mp,l.yc,l.dy,l.gw,l.tbtx,l.fwbz,l.bz\n");
			sql.append(",'D'||d.rq rq,d.xcmx,d.breakfast,d.cmeal,d.supper,d.cb,d.zs,l.ts\n");
			sql.append("from ta_dj_linemng l,ta_dj_linedetail d,dmsm m1\n");
			sql.append("where l.xlid=d.xlid(+) and l.orgid=d.orgid(+)\n");
			sql.append("and d.zsbz=m1.dmz and m1.dmlb=6\n");
			sql.append("and l.orgid=").append(sd.getString("orgid")).append("\n");
			sql.append("and l.xlid=").append(rd.getString("TA_DJ_LINEMNG","xlid",0));
			sql.append("order by d.rq");
			coreDAO.executeQuery(sql.toString(), "ta_dj_linemngs", rd, conn);
			//��·�۸�
			sql = null;
			sql = new StringBuffer();
			sql.append("select x.jg,x.dfc,d.dmsm1 jglx,x.bz\n");
			sql.append("from TA_DJ_XLJG x,drmuser u,dmsm d\n");
			sql.append("where x.orgid=u.orgid and x.deptid=u.deptid\n");
			sql.append("and d.dmz=x.jglx and d.dmlb=4\n");
			sql.append("and x.orgid=").append(sd.getString("orgid")).append("and x.xlid=").append(rd.getStringByDI("TA_DJ_LINEMNG", "xlid", 0));
			coreDAO.executeQuery(sql.toString(), "ta_dj_xljgs", rd, conn);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 98;
	}
	
	/**
	 * ������·�г���ϸ
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int expWordForXCD(BizData rd, BizData sd){
		
		Connection conn = coreDAO.getConnection();
		
		try {
			//��·��ϸ
			queryLineDetail(rd, conn);
			//��·
			coreDAO.select("TA_DJ_LINEMNG", rd, true, conn);
			//��·�۸�
			coreDAO.select("TA_DJ_XLJG", rd, true, conn);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 98;
	}
}
