package com.trm.empsmgr;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class PersonelManagementBLC extends DBBLC{

	public PersonelManagementBLC(){
		this.entityName = "JNY_EMP";
	}
	
	
	//该方法用途添加员工信息
	public int addPersonInfo(BizData rd, BizData sd) throws ParseException, SQLException{
		String acq_date = rd.getStringByDI("JNY_EMP", "ACQ_DATE", 0);
		String begin_date = rd.getStringByDI("JNY_EMP", "BEGIN_DATE", 0);
		String end_date = rd.getStringByDI("JNY_EMP", "END_DATE", 0);
		String dob = rd.getStringByDI("JNY_EMP", "DOB", 0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(!acq_date.equals("")){
			Date ACQ_DATE = sdf.parse(acq_date);
			rd.remove("JNY_EMP", "ACQ_DATE", 0);
			rd.add("JNY_EMP", "ACQ_DATE", 0, ACQ_DATE);
		}
		if(!begin_date.equals("")){
			Date BEGIN_DATE = sdf.parse(begin_date);
			rd.remove("JNY_EMP", "BEGIN_DATE", 0);
			rd.add("JNY_EMP", "BEGIN_DATE", 0, BEGIN_DATE);
		}
		if(!end_date.equals("")){
			Date END_DATE = sdf.parse(end_date);
			rd.remove("JNY_EMP", "END_DATE", 0);
			rd.add("JNY_EMP", "END_DATE", 0, END_DATE);
		}
		if(!dob.equals("")){
			Date DOB = sdf.parse(dob);
			rd.remove("JNY_EMP", "DOB", 0);
			rd.add("JNY_EMP", "DOB", 0, DOB);
		}
		String picture = rd.getStringByDI("JNY_EMP", "PICTURE", 0);
		String id_card = rd.getStringByDI("JNY_EMP", "ID_CARD", 0);
		if(picture.equals("")){
			rd.remove("JNY_EMP", "JNY_EMP");
		}
		if(id_card.equals("")){
			rd.remove("JNY_EMP", "ID_CARD");
		}
		rd.add("JNY_EMP", "IN_SERVICE", 0, "1");
	
		this.coreDAO.insert("JNY_EMP",rd);
		return 1;
	}
	
	//获得全部在职员工信息
	public int getAllStaffInfo(BizData rd, BizData sd) throws SQLException{
		this.entityName = "JNY_EMP";
		rd.add("JNY_EMP", "IN_SERVICE", "1");
		rd.add("isCheck", 0);
		rd.add("USERNAME", rd.getStringByDI("JNY_EMP", "USERNAME", 0));
		this.coreDAO.selectPage("JNY_EMP", rd);
		return 1;
	}
	
	//通过员工信息查找员工
	public int getPersonInfo(BizData rd, BizData sd) throws SQLException{
		String userNo = rd.getStringByDI("JNY_EMP","USERNO",0);
		String sql="select emp.USERNO,emp.EMP_NAME,emp.EMPID,emp.NOMEN,emp.ATTRIBUTE,emp.SEX,emp.IN_SERVICE,emp.POSTCODE,emp.ADDRESS,emp.PHONE,emp.EMAIL,emp.SALARY ,to_char(emp.DOB,'YYYY-MM-DD') DOB,emp.CELL_PHONE,emp.FIXED_PHONE,emp.DRIVER_LIC,emp.GUIDE_ID,emp.EDU_LEVEL,to_char(emp.ACQ_DATE,'YYYY-MM-DD') ACQ_DATE,to_char(emp.BEGIN_DATE,'YYYY-MM-DD') BEGIN_DATE, to_char(emp.END_DATE,'YYYY-MM-DD') END_DATE,emp.GUIDE_CER_ID,emp.QUA_LEVEL,emp.GUIDE_LEVEL,emp.AGENCY_MAN_ID,emp.GEN_MAN_ID,emp.DEP_MAN_ID,emp.ACCOUNTER_ID,emp.AUDITOR_ID,emp.VITAE,emp.PICTURE,emp.ID_CARD,emp.BANKNUM,emp.BANKNAME from JNY_EMP emp where USERNO = '" + userNo+"'";
		coreDAO.executeQuery(sql, "JNY_EMPs", rd);
		//this.coreDAO.select(rd);
		return 1;
	}
	
	//通过查询条件查询员工信息
	public int getSelectedPersonInfo(BizData rd, BizData sd) throws SQLException{
		String EMP_NAME=rd.getStringByDI("JNY_EMP", "EMP_NAME", 0);
		String EMPID=rd.getStringByDI("JNY_EMP", "EMPID", 0);
		int pageNO=Integer.parseInt(rd.getStringAttr("JNY_EMP", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("JNY_EMP", "pageSize"));
		String sql="select * from jny_emp where emp_name like '%"+EMP_NAME+"%' and empid like '%"+EMPID+"%'";
		this.coreDAO.executeQueryPage(sql, "JNY_EMPs", pageNO, pageSize, rd);
		rd.add("isCheck", 1);
		return 1;
	}
	
	public int updateStaffInfo1(BizData rd, BizData sd) throws SQLException, ParseException{
		BizData data = new BizData();
		data.add("JNY_EMP", "USERNO", rd.getStringByDI("JNY_EMP", "USERNO", 0));
		this.coreDAO.delete(data);
		
		String acq_date = rd.getStringByDI("JNY_EMP", "ACQ_DATE", 0);
		String begin_date = rd.getStringByDI("JNY_EMP", "BEGIN_DATE", 0);
		String end_date = rd.getStringByDI("JNY_EMP", "END_DATE", 0);
		String dob = rd.getStringByDI("JNY_EMP", "DOB", 0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(!acq_date.equals("")){
			Date ACQ_DATE = sdf.parse(acq_date);
			rd.remove("JNY_EMP", "ACQ_DATE", 0);
			rd.add("JNY_EMP", "ACQ_DATE", 0, ACQ_DATE);
		}
		if(!begin_date.equals("")){
			Date BEGIN_DATE = sdf.parse(begin_date);
			rd.remove("JNY_EMP", "BEGIN_DATE", 0);
			rd.add("JNY_EMP", "BEGIN_DATE", 0, BEGIN_DATE);
		}
		if(!end_date.equals("")){
			Date END_DATE = sdf.parse(end_date);
			rd.remove("JNY_EMP", "END_DATE", 0);
			rd.add("JNY_EMP", "END_DATE", 0, END_DATE);
		}
		if(!dob.equals("")){
			Date DOB = sdf.parse(dob);
			rd.remove("JNY_EMP", "DOB", 0);
			rd.add("JNY_EMP", "DOB", 0, DOB);
		}
		String picture = rd.getStringByDI("JNY_EMP", "PICTURE", 0);
		String id_card = rd.getStringByDI("JNY_EMP", "ID_CARD", 0);
		if(picture.equals("")){
			rd.remove("JNY_EMP", "JNY_EMP");
		}
		if(id_card.equals("")){
			rd.remove("JNY_EMP", "ID_CARD");
		}
		rd.add("JNY_EMP", "IN_SERVICE", 0, "1");
		
		this.coreDAO.insert("JNY_EMP",rd);
		return 1;
	}
	
	//更新员工信息
	public int updateStaffInfo2(BizData rd, BizData sd) {
//		String acq_date = rd.getStringByDI("JNY_EMP", "ACQ_DATE", 0);
//		String begin_date = rd.getStringByDI("JNY_EMP", "BEGIN_DATE", 0);
//		String end_date = rd.getStringByDI("JNY_EMP", "END_DATE", 0);
//		String dob = rd.getStringByDI("JNY_EMP", "DOB", 0);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		if(!acq_date.equals("")){
//			Date ACQ_DATE = sdf.parse(acq_date);
//			rd.remove("JNY_EMP", "ACQ_DATE", 0);
//			rd.add("JNY_EMP", "ACQ_DATE", 0, ACQ_DATE);
//		}
//		if(!begin_date.equals("")){
//			Date BEGIN_DATE = sdf.parse(begin_date);
//			rd.remove("JNY_EMP", "BEGIN_DATE", 0);
//			rd.add("JNY_EMP", "BEGIN_DATE", 0, BEGIN_DATE);
//		}
//		if(!end_date.equals("")){
//			Date END_DATE = sdf.parse(end_date);
//			rd.remove("JNY_EMP", "END_DATE", 0);
//			rd.add("JNY_EMP", "END_DATE", 0, END_DATE);
//		}
//		if(!dob.equals("")){
//			Date DOB = sdf.parse(dob);
//			rd.remove("JNY_EMP", "DOB", 0);
//			rd.add("JNY_EMP", "DOB", 0, DOB);
//		}
		String time=rd.getStringByDI("JNY_EMP", "ACQ_DATE", 0);
		String bTime=rd.getStringByDI("JNY_EMP", "BEGIN_DATE", 0);
		String eTime=rd.getStringByDI("JNY_EMP", "END_DATE", 0);
		String newTime="";
		String new_bTime="";
		String new_eTime="";
		Connection conn=coreDAO.getConnection();
		try{
			coreDAO.beginTrasct(conn);
			if(!"".equals(time)){
				newTime=time.substring(0, 10);
				rd.add("JNY_EMP", "ACQ_DATE",newTime);
			}
			if(!"".equals(bTime)){
				new_bTime=bTime.substring(0, 10);
				rd.add("JNY_EMP", "BEGIN_DATE",new_bTime);
			}
			if(!"".equals(eTime)){
				new_eTime=eTime.substring(0, 10);
				rd.add("JNY_EMP", "END_DATE",new_eTime);
			}
			this.coreDAO.update("JNY_EMP", rd,conn);
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return 1;
	}
	
	//获得员工照片/身份证复印件
	public int getPicture(BizData rd, BizData sd) throws SQLException{
		String sql = "select " + rd.getString("Photo") + " from JNY_EMP where USERNO = " + rd.getString("USERNO");
		this.coreDAO.executeQuery(sql, "rsPhoto", rd);
		return 1;
	}
	
	public int getID_CARD(BizData rd, BizData sd) throws SQLException{
		String sql = "select " + rd.getString("Photo") + " from JNY_EMP where USERNO = " + rd.getString("USERNO");
		this.coreDAO.executeQuery(sql, "rsPhoto", rd);
		return 1;
	}
	
	//逻辑删除员工
	public int removeEMP(BizData rd, BizData sd) throws SQLException{
		String sql = "update JNY_EMP set IN_SERVICE=0 where USERNO=" + rd.getStringByDI("JNY_EMP", "USERNO", 0);
		Connection conn = coreDAO.getConnection();
		this.coreDAO.executeUpdate(sql,conn);
		conn.close();
		conn = null;
		return 1;
	}
	
	//逻辑删除被选中的员工
	public int removeSelectedEMP(BizData rd, BizData sd) throws SQLException{
		String[] rowIds = rd.getRowIDs("JNY_EMP");
		Connection conn = this.coreDAO.getConnection();
		try {
			this.coreDAO.beginTrasct(conn);
			for(int i=0; i<rowIds.length; i++){
				String sql = "update JNY_EMP set IN_SERVICE=0 where USERNO=" + rd.getStringByDI("JNY_EMP", "USERNO", Integer.parseInt(rowIds[i]));
				this.coreDAO.executeUpdate(sql, conn);
			}
			this.coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn.close();
		return 1;
	}
	
	//获得下一个ID
	public int getNextEMP_ID(BizData rd, BizData sd) throws SQLException{
		int nextID = super.queryMaxIDByPara("JNY_EMP", "USERNO", "");
		rd.add("nextXH", nextID);
		return 1;
	}

}
