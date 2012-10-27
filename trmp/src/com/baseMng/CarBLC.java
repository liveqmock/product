package com.baseMng;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.string.PyUtil;

public class CarBLC extends DBBLC {
	public CarBLC() {
		this.entityName = "TA_CAR_TEAM";
	}

	public int getAllCarTeam(BizData rd, BizData sd) throws SQLException {

		rd.add("TA_CAR_TEAM", "orgid", sd.getString("orgid"));
		this.entityName = "TA_CAR_TEAM";
		this.queryPage(rd, sd);
		return 1;
	}

	public int getAllCar(BizData rd, BizData sd) throws SQLException {
		int pageNO = Integer.parseInt(rd.getStringAttr("TA_CAR", "pageNO"));
		int pageSize = Integer.parseInt(rd.getStringAttr("TA_CAR", "pageSize"));
		String id = rd.getString("TA_CAR", "TEAM_ID", 0);
		String sql = "select a.team_id,a.driver_name,a.driver_phone,a.buy_date,a.CAR_ID,a.CAR_CODE,b.dmsm1 car_type \n"
				+ " from ta_car a,dmsm b where a.car_type=b.dmz and b.dmlb=13 and a.team_id="
				+ id;
		coreDAO.executeQueryPage(sql, "TA_CARs", pageNO, pageSize, rd);
		return 1;
	}

	public int getCarById(BizData rd, BizData sd) throws SQLException {
		this.entityName = "TA_CAR";
		this.query(rd, sd);
		return 1;
	}

	public int getCarTeamByName(BizData rd, BizData sd) throws SQLException {

		this.entityName = "TA_CAR_TEAM";
		int pageNO = Integer
				.parseInt(rd.getStringAttr("TA_CAR_TEAM", "pageNO"));
		int pageSize = Integer.parseInt(rd.getStringAttr("TA_CAR_TEAM",
				"pageSize"));
		String name = rd.getString("name");
		String orgid = sd.getString("orgid");
		String sql = "select a.team_id,a.city_id,a.cmpny_name,a.cmpny_address,a.chief_name, \n"
				+ "a.chief_mobile,a.chief_tel,a.business_name,a.business_phone,a.business_mobile \n"
				+ " from ta_car_team a where a.cmpny_name like '%"
				+ name
				+ "%'"
				+ " and a.orgid='"
				+ orgid
				+ "'";
		coreDAO.executeQueryPage(sql, "TA_CAR_TEAMs", pageNO, pageSize, rd);
		return 1;
	}

	public int getCarByName(BizData rd, BizData sd) throws SQLException {
		int pageNO = Integer.parseInt(rd.getStringAttr("TA_CAR", "pageNO"));
		int pageSize = Integer.parseInt(rd.getStringAttr("TA_CAR", "pageSize"));
		String drive_name = rd.getString("drive_name");
		String code = rd.getString("code");
		String team_id = rd.getString("TEAM_ID");
		String sql = "select a.car_id,a.team_id,a.car_code,b.dmsm1 car_type,a.buy_date,a.driver_name,a.driver_phone \n"
				+ " from ta_car a,dmsm b where a.driver_name like '%"
				+ drive_name
				+ "%' and a.car_code like '%"
				+ code
				+ "%' \n"
				+ "and a.car_type=b.dmz and b.dmlb=13 and a.team_id=" + team_id;
		coreDAO.executeQueryPage(sql, "TA_CARs", pageNO, pageSize, rd);
		rd.add("TA_CAR", "TEAM_ID", team_id);
		return 1;
	}

	public int addCarTeam(BizData rd, BizData sd) throws SQLException {

		this.entityName = "TA_CAR_TEAM";
		try {
			int id = this.queryMaxIDByPara("TA_CAR_TEAM", "TEAM_ID", null);
			rd.add("TA_CAR_TEAM", "TEAM_ID", id);

			// 生成拼音码
			String cmpName = rd.getString("TA_CAR_TEAM", "CMPNY_NAME", 0);
			String nameCode = PyUtil.get1stLetterOf4Chars(cmpName);
			rd.add("TA_CAR_TEAM", "NAMECODE", 0, nameCode);
			rd.add("TA_CAR_TEAM", "ORGID", 0, sd.getString("orgid"));
			coreDAO.insert("TA_CAR_TEAM", rd);
			// rd.add("TA_CAR_TEAM", "CITY_ID",rd.getString("CITY_ID"));
			rd.add("TA_CAR_TEAM", "CITY_ID", rd.getString("TA_CAR_TEAM",
					"CITY_ID", 0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	public int addCar(BizData rd, BizData sd) throws SQLException {
		this.entityName = "TA_CAR";
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			int id = this.queryMaxIDByPara("TA_CAR", "CAR_ID", null);
			rd.add("TA_CAR", "CAR_ID", id);
			rd.add("TEAM_ID", rd.getStringByDI("TA_CAR", "TEAM_ID", 0));
			coreDAO.insert("TA_CAR", rd, conn);
			coreDAO.commitTrasct(conn);
		} catch (Exception e) {
			coreDAO.rollbackTrasct(conn);
		} finally {
			if (null != conn) {
				conn.close();
			}
		}
		return 1;
	}

	public int updateCar(BizData rd, BizData sd) throws SQLException {
		this.entityName = "TA_CAR";
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			coreDAO.update("TA_CAR", rd, conn);
			rd.add("TEAM_ID", rd.getStringByDI("TA_CAR", "TEAM_ID", 0));
			coreDAO.commitTrasct(conn);
		} catch (Exception e) {
			coreDAO.rollbackTrasct(conn);
		} finally {
			if (null != conn) {
				conn.close();
			}
		}
		return 1;
	}

	public int upCarTeam(BizData rd, BizData sd) throws SQLException {
		
		this.entityName = "TA_CAR_TEAM";
		try {

			// 生成拼音码
			String cmpName = rd.getString("TA_CAR_TEAM", "CMPNY_NAME", 0);
			String nameCode = PyUtil.get1stLetterOf4Chars(cmpName);
			rd.add("TA_CAR_TEAM", "NAMECODE", 0, nameCode);
			coreDAO.update("TA_CAR_TEAM", rd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	public int delCar(BizData rd, BizData sd) throws SQLException {
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			String sql = "delete from TA_CAR a where a.CAR_ID="
					+ rd.getString("car_id");
			coreDAO.executeUpdate(sql, conn);
			rd.add("TEAM_ID", rd.getString("TEAM_ID"));
			coreDAO.commitTrasct(conn);
		} catch (Exception e) {
			coreDAO.rollbackTrasct(conn);
		} finally {
			if (null != conn) {
				conn.close();
			}
		}
		return 1;
	}

	public int batchDelCar(BizData rd, BizData sd) throws SQLException {
		String[] rowsId = rd.getRowIDs("CAR");
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			for (int a = 0; a < rowsId.length; a++) {
				String sql = "delete from TA_CAR a where a.CAR_ID="
						+ rd.getString("CAR", "CHECKBOX", rowsId[a]);
				coreDAO.executeUpdate(sql, conn);
			}
			rd.add("TEAM_ID", rd.getString("TEAM_ID"));
			coreDAO.commitTrasct(conn);
		} catch (Exception e) {
			coreDAO.rollbackTrasct(conn);
		} finally {
			if (null != conn) {
				conn.close();
			}
		}
		return 1;
	}

	public int getCarTeamById(BizData rd, BizData sd) throws SQLException {

		String teamId = rd.getStringByDI("TA_CAR_TEAM", "TEAM_ID", 0);
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*,x.name,x.pid,x.layer\n").append(
				"from ta_car_team t,xzqh x\n");
		sql.append("where t.city_id=x.id(+)\n").append("and t.team_id=")
				.append(teamId);
		coreDAO.executeQuery(sql.toString(), "TA_CAR_TEAMs", rd);
		return 1;
	}

	public int delCarTeam(BizData rd, BizData sd) throws SQLException {

		this.entityName = "TA_CAR_TEAM";
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			String team_id = rd.getString("TEAM_ID");
			String sql = "delete from TA_CAR a where a.team_id=" + team_id;
			coreDAO.executeUpdate(sql, conn);
			String sql2 = "delete from TA_CAR_TEAM a where a.TEAM_ID="
					+ team_id;
			coreDAO.executeUpdate(sql2, rd);
			coreDAO.commitTrasct(conn);
		} catch (Exception e) {
			coreDAO.rollbackTrasct(conn);
		} finally {
			if (null != conn) {
				conn.close();
			}
		}
		return 1;
	}

	public int batchDelCarTeam(BizData rd, BizData sd) throws SQLException {
		this.entityName = "TA_CAR_TEAM";
		String[] rowsId = rd.getRowIDs("CAR_TEAM");
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			for (int a = 0; a < rowsId.length; a++) {
				String team_id = rd
						.getString("CAR_TEAM", "CHECKBOX", rowsId[a]);
				String sql = "delete from TA_CAR a where a.team_id=" + team_id;
				coreDAO.executeUpdate(sql, conn);
				String sql2 = "delete from TA_CAR_TEAM a where a.TEAM_ID="
						+ team_id;
				coreDAO.executeUpdate(sql2, conn);
			}
			coreDAO.commitTrasct(conn);
		} catch (Exception e) {
			coreDAO.rollbackTrasct(conn);
		} finally {
			if (null != conn) {
				conn.close();
			}
		}
		return 1;
	}
}
