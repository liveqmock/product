package com.trmdj.businessPlan.groupMng;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**
 * @Title: DjGroupDetailBLC.java
 * @Package com.trmdj.businessPlan.groupMng
 * @Description: TODO(����)
 * @author Kale ym_x@qq.com
 * @date 2011-9-7 ����05:53:50
 * @version V1.0
 */
public class DjGroupDetailBLC extends DBBLC
{
	/**
	 * ��־����
	 */
	Logger log = null;
	
	/**
	 * Constructs
	 */
	public DjGroupDetailBLC()
	{
		this.entityName = "TA_DJ_LINEDETAI4G";
		
		// ������־
		log = Logger.getLogger(DjGroupDetailBLC.class);
	}
	
	/**
	 * �ؽ���˳�ʼ��ҳ������
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int djInitAllSHPlan(BizData rd, BizData sd) {
		
		Connection conn = coreDAO.getConnection();
		try {
			//�żƵ��������
			queryAllState(rd, sd, conn);
			//�Ŷӻ�����Ϣ
			coreDAO.select("TA_DJ_GROUP", rd, true, conn);
			//���г���ϸ
			coreDAO.select("TA_DJ_LINEDETAI4G", rd, true, conn);
			//���������
			viewSpyj(rd, sd, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try{
				if(null != conn)
					conn.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		return 98;
	}
	
	private void queryAllState(BizData rd, BizData sd, Connection conn)
	{
		rd.add("md",rd.getString("md"));//ģ��index
		rd.add("mdName", rd.getString("mdName"));//ģ��id����
		rd.add("mdUrl", rd.getString("mdUrl").replace(",", "&"));//ģ��url
		try
        {
			// �Ƶ�ƻ�TA_DJ_JHHOTEL
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHHOTEL","JHHOTEL", sd.getString("orgid"), rd, conn);
			
			// ��������TA_DJ_JHCT
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHCT","JHCT", sd.getString("orgid"),rd, conn);
			
			// Ʊ���� TA_DJ_JHPW
			queryState("sum(QD) QD,sum(XF) XF,sum(YGCB) YGCB","TA_DJ_JHPW","JHPW", sd.getString("orgid"),rd, conn);
			
			// �������� TA_DJ_jHCL
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(JG) JG","TA_DJ_jHCL","jHCL", sd.getString("orgid"),rd, conn);
			
			// ���㰲��TA_DJ_JHJD
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(TGCB) TGCB","TA_DJ_JHJD","JHJD", sd.getString("orgid"),rd, conn);
			
			// �ؽӰ���TA_DJ_JHDJ
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YFZK) YFZK","TA_DJ_JHDJ","JHDJ", sd.getString("orgid"),rd, conn);
			
			// ���ﰲ��TA_DJ_JHGW
			queryState("count(id)","TA_DJ_JHGW","JHGW", sd.getString("orgid"),rd, conn);
			
			// �ӵ㰲��TA_DJ_JHJIAD
			queryState("count(id)","TA_DJ_JHJIAD","JHJIAD", sd.getString("orgid"),rd, conn);
			
			// ��������TA_DJ_JHQT
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHQT","JHQT", sd.getString("orgid"),rd, conn);
			
			// ���ΰ���TA_DJ_JHDY
			queryState("sum(DYLK) DYLK,sum(DFF) DFF","TA_DJ_JHDY","JHDY", sd.getString("orgid"),rd, conn);
        } catch (Exception e) {
	        // ��ӡ������־
        	log.error("��ѯʧ��", e);
        }
	}
	
	private void viewSpyj(BizData rd, BizData sd, Connection conn) {
		
		String groupID = rd.getString("TA_DJ_TSPB", "TID", 0);// ��ȡ��ѯ����:�ź�
		String spyj="select t.spyj,u.username from ta_dj_tspb t,drmuser u where t.spr=u.userno and t.mklb='1' and t.tid='"+groupID+"' and t.orgid='"+sd.getString("orgid")+"'";
		try {
			coreDAO.executeQuery(spyj, "SPYJ", rd, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��ѯ���мƵ��������
	 * 
	 * @param rd �������ݶ���
	 * @param sd Session���ݶ���
	 * @return �����
	 */
	public int queryAllState(BizData rd, BizData sd)
	{
		rd.add("md",rd.getString("md"));//ģ��index
		rd.add("mdName", rd.getString("mdName"));//ģ��id����
		rd.add("mdUrl", rd.getString("mdUrl").replace(",", "&"));//ģ��url
		Connection conn = coreDAO.getConnection();
		try
        {
			// �Ƶ�ƻ�TA_DJ_JHHOTEL
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHHOTEL","JHHOTEL", sd.getString("orgid"), rd, conn);
			
			// ��������TA_DJ_JHCT
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHCT","JHCT", sd.getString("orgid"),rd, conn);
			
			// Ʊ���� TA_DJ_JHPW
			queryState("sum(QD) QD,sum(XF) XF,sum(YGCB) YGCB","TA_DJ_JHPW","JHPW", sd.getString("orgid"),rd, conn);
			
			// �������� TA_DJ_jHCL
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(JG) JG","TA_DJ_jHCL","jHCL", sd.getString("orgid"),rd, conn);
			
			// ���㰲��TA_DJ_JHJD
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(TGCB) TGCB","TA_DJ_JHJD","JHJD", sd.getString("orgid"),rd, conn);
			
			// �ؽӰ���TA_DJ_JHDJ
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YFZK) YFZK","TA_DJ_JHDJ","JHDJ", sd.getString("orgid"),rd, conn);
			
			// ���ﰲ��TA_DJ_JHGW
			queryState("count(id)","TA_DJ_JHGW","JHGW", sd.getString("orgid"),rd, conn);
			
			// �ӵ㰲��TA_DJ_JHJIAD
			queryState("count(id)","TA_DJ_JHJIAD","JHJIAD", sd.getString("orgid"),rd, conn);
			
			// ��������TA_DJ_JHQT
			queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHQT","JHQT", sd.getString("orgid"),rd, conn);
			
			// ���ΰ���TA_DJ_JHDY
			queryState("sum(DYLK) DYLK,sum(DFF) DFF","TA_DJ_JHDY","JHDY", sd.getString("orgid"),rd, conn);
        } catch (Exception e) {
	        // ��ӡ������־
        	log.error("��ѯʧ��", e);
        	return SysError.DB_ERROR;
        }finally {
        	try{
        		if(conn != null){
        			conn.close();
        			conn = null;
        		}
        	}catch(SQLException e){
        		log.error("�ر�connʧ�ܣ�", e);
        		return SysError.DB_ERROR;
        	}
        }
        
		return 999;
	}
	
	/**
	 * ��ѯͳ�Ƽƻ�����
	 * 
	 * @param str ����
	 * @param table ����
 	 * @param rd �������ݶ���
	 * @return �����
	 * @throws SQLException 
	 */
	private int queryState(String str, String table,String alias, String orgid,BizData rd, Connection conn) throws SQLException
	{
		// ��ʼ��SQL
		StringBuffer sql = new StringBuffer("select count(*) sum,");
		
		// �����ѯSQL
		sql.append(str);
		sql.append(" from ");
		sql.append(table);
		sql.append(" where TID='");
		sql.append(rd.getStringByDI("TA_DJ_GROUP", "ID", 0));
		sql.append("' and orgid=");
		sql.append(orgid);
		
		// debugģʽ
		log.debug("ִ�в�ѯ��" + sql);
		
		// ִ��SQL
	    coreDAO.executeQuery(sql.toString(), alias, rd, conn);
       
		return 999;
	}
	
	/**
	 * ��ʼ���ű������
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int djInitAllSHBx(BizData rd, BizData sd){
		
		Connection conn = coreDAO.getConnection();
		try{
			//�Ż�����Ϣ
			coreDAO.select("TA_DJ_GROUP", rd, true, conn);
			//���г���ϸ
			coreDAO.select("TA_DJ_LINEDETAI4G", rd, true, conn);
			//�������
			spyj(rd, sd, "2", conn);
			//�żƵ�������� 
			queryAllBxState(rd, sd, conn);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try{
				if(null != conn)
					conn.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		return 8;
	}
	
	/**
	 * ���������ѯ
	 * @param rd
	 * @param sd
	 * @param conn
	 * @throws SQLException
	 */
	private void spyj(BizData rd, BizData sd, String mklb, Connection conn) throws SQLException {
		String groupID = rd.getStringByDI("TA_DJ_GROUP", "ID", 0);// ��ȡ��ѯ����:�ź�
		String spyj="select t.spyj,u.username from ta_dj_tspb t,drmuser u where t.spr=u.userno and t.mklb='"+mklb+"' and t.tid='"+groupID+"' and t.orgid='"+sd.getString("orgid")+"'";
		
		coreDAO.executeQuery(spyj, "SPYJ", rd, conn);
	}
	
	/**
	 * ��ѯ���б��˷������
	 * 
	 * @param rd �������ݶ���
	 * @param sd Session���ݶ���
	 * @return �����
	 */
	public int queryAllBxState(BizData rd, BizData sd)
	{
		rd.add("md",rd.getString("md"));//ģ��index
		rd.add("mdName", rd.getString("mdName"));//ģ��id����
		rd.add("mdUrl", rd.getString("mdUrl").replace(",", "&"));//ģ��URL
		
		Connection conn = coreDAO.getConnection();
		
		String sql ="select distinct gr.id,gr.state,gr.gw,gr.jd,a.hoteltid,b.cttid,c.cltid,d.djtid,e.dytid,f.gwtid,g.jdtid,h.jiadtid,i.pwtid,j.qttid \n" +
					" from \n" +
					"(select tid hoteltid,orgid from ta_dj_bxhotel) a, \n" +
					"(select tid cttid,orgid from ta_dj_bxct) b, \n" +
					"(select tid cltid,orgid from ta_dj_bxcl) c, \n" +
					"(select tid djtid,orgid from ta_dj_bxdj) d, \n" +
					"(select tid dytid,orgid from ta_dj_bxdy) e, \n" +
					"(select tid gwtid,orgid from ta_dj_bxgw) f, \n" +
					"(select tid jdtid,orgid from ta_dj_bxjd) g, \n" +
					"(select tid jiadtid,orgid from ta_dj_bxjiadian) h, \n" + 
					"(select tid pwtid,orgid from ta_dj_bxpw) i, \n" +
					"(select tid qttid,orgid from ta_dj_bxqt) j, \n" +
					"ta_dj_group gr \n" +
					"where gr.orgid=" + sd.getString("orgid") + "\n" +
					"and gr.id=a.hoteltid(+) and gr.orgid=a.orgid(+)\n" + 
					"and gr.id=b.cttid(+) and gr.orgid=b.orgid(+)\n" +
					"and gr.id=c.cltid(+) and gr.orgid=c.orgid(+)\n" +
					"and gr.id=d.djtid(+) and gr.orgid=d.orgid(+)\n" +
					"and gr.id=e.dytid(+) and gr.orgid=e.orgid(+)\n" +
					"and gr.id=f.gwtid(+) and gr.orgid=f.orgid(+)\n" +
					"and gr.id=g.jdtid(+) and gr.orgid=g.orgid(+)\n" +
					"and gr.id=h.jiadtid(+) and gr.orgid=h.orgid(+)\n" +
					"and gr.id=i.pwtid(+) and gr.orgid=i.orgid(+)\n" +
					"and gr.id=j.qttid(+) and gr.orgid=j.orgid(+)\n" +
					"and gr.id='"+rd.getString("TA_DJ_GROUP","ID",0)+"'"; 
			
		try
        {
			coreDAO.executeQuery(sql, "allState", rd, conn);
			// �Ƶ�ƻ�TA_DJ_JHHOTEL
			if(null!=rd.getString("allState","hoteltid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_BXHOTEL","JHHOTEL", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHHOTEL","JHHOTEL", sd.getString("orgid"),rd, conn);
			}
			
			// ��������TA_DJ_JHCT
			if(null!=rd.getString("allState","cttid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_BXCT","JHCT", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHCT","JHCT", sd.getString("orgid"),rd, conn);
			}
			// Ʊ���� TA_DJ_JHPW
			if(null!=rd.getString("allState","pwtid",0)){
				queryState("sum(QD) QD,sum(XF) XF,sum(YGCB) YGCB","TA_DJ_BXPW","JHPW", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QD) QD,sum(XF) XF,sum(YGCB) YGCB","TA_DJ_JHPW","JHPW", sd.getString("orgid"),rd, conn);
			}
			// �������� TA_DJ_jHCL
			if(null!=rd.getString("allState","cltid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(JG) JG","TA_DJ_BXCL","JHCL", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(JG) JG","TA_DJ_JHCL","JHCL", sd.getString("orgid"),rd, conn);
			}
			// ���㰲��TA_DJ_JHJD
			if(null!=rd.getString("allState","jdtid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(TGCB) TGCB","TA_DJ_BXJD","JHJD", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(TGCB) TGCB","TA_DJ_JHJD","JHJD", sd.getString("orgid"),rd, conn);
			}
			// �ؽӰ���TA_DJ_JHDJ
			if(null!=rd.getString("allState","djtid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YFZK) YFZK","TA_DJ_BXDJ","JHDJ", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YFZK) YFZK","TA_DJ_JHDJ","JHDJ", sd.getString("orgid"),rd, conn);
			}
			// ���ﰲ��TA_DJ_JHGW
			if(null!=rd.getString("allState","gwtid",0)){
				queryState("sum(JDRS) JDRS,sum(XFE) XFE,sum(YJGS) YJGS,sum(RTF) RTF,sum(GSLC) GSLC","TA_DJ_BXGW","JHGW", sd.getString("orgid"),rd, conn);
			}else{
				queryState("count(id)","TA_DJ_JHGW","JHGW", sd.getString("orgid"),rd, conn);
			}
			// �ӵ㰲��TA_DJ_JHJIAD
			if(null!=rd.getString("allState","jiadtid",0)){
				queryState("sum(CBJE) CBJE,sum(CBQD) CBQD,sum(CBXF) CBXF,sum(RS) RS,sum(LR) LR,sum(YJGS) YJGS,sum(RTF) RTF","TA_DJ_BXJIADIAN","JHJIAD", sd.getString("orgid"),rd, conn);
			}else{
				queryState("count(id)","TA_DJ_JHJIAD","JHJIAD", sd.getString("orgid"),rd, conn);
			}
			// ��������TA_DJ_JHQT
			if(null!=rd.getString("allState","qttid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_BXQT","JHQT", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHQT","JHQT", sd.getString("orgid"),rd, conn);
			}
			// ���ΰ���TA_DJ_JHDY
			if(null!=rd.getString("allState","dytid",0)){
				queryState("sum(DYLK) DYLK,sum(DFF) DFF","TA_DJ_BXDY","JHDY", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(DYLK) DYLK,sum(DFF) DFF","TA_DJ_JHDY","JHDY", sd.getString("orgid"),rd, conn);
			}
        }
        catch (Exception e)
        {
	        // ��ӡ������־
        	log.error("��ѯʧ��", e);
        	
        	return SysError.DB_ERROR;
        }finally {
        	try{
        		if(conn != null){
        			conn.close();
        			conn = null;
        		}
        	}catch(SQLException e){
        		log.error("�ر�connʧ�ܣ�", e);
        		return SysError.DB_ERROR;
        	}
        }
        
		return 999;
	}
	/**
	 * ��ѯ���б��˷������
	 * 
	 * @param rd �������ݶ���
	 * @param sd Session���ݶ���
	 * @return �����
	 */
	public void queryAllBxState(BizData rd, BizData sd, Connection conn)
	{
		rd.add("md",rd.getString("md"));//ģ��index
		rd.add("mdName", rd.getString("mdName"));//ģ��id����
		rd.add("mdUrl", rd.getString("mdUrl").replace(",", "&"));//ģ��URL
		
		String sql ="select distinct gr.id,gr.state,gr.gw,gr.jd,a.hoteltid,b.cttid,c.cltid,d.djtid,e.dytid,f.gwtid,g.jdtid,h.jiadtid,i.pwtid,j.qttid \n" +
					" from \n" +
					"(select tid hoteltid,orgid from ta_dj_bxhotel) a, \n" +
					"(select tid cttid,orgid from ta_dj_bxct) b, \n" +
					"(select tid cltid,orgid from ta_dj_bxcl) c, \n" +
					"(select tid djtid,orgid from ta_dj_bxdj) d, \n" +
					"(select tid dytid,orgid from ta_dj_bxdy) e, \n" +
					"(select tid gwtid,orgid from ta_dj_bxgw) f, \n" +
					"(select tid jdtid,orgid from ta_dj_bxjd) g, \n" +
					"(select tid jiadtid,orgid from ta_dj_bxjiadian) h, \n" + 
					"(select tid pwtid,orgid from ta_dj_bxpw) i, \n" +
					"(select tid qttid,orgid from ta_dj_bxqt) j, \n" +
					"ta_dj_group gr \n" +
					"where gr.orgid=" + sd.getString("orgid") + "\n" +
					"and gr.id=a.hoteltid(+) and gr.orgid=a.orgid(+)\n" + 
					"and gr.id=b.cttid(+) and gr.orgid=b.orgid(+)\n" +
					"and gr.id=c.cltid(+) and gr.orgid=c.orgid(+)\n" +
					"and gr.id=d.djtid(+) and gr.orgid=d.orgid(+)\n" +
					"and gr.id=e.dytid(+) and gr.orgid=e.orgid(+)\n" +
					"and gr.id=f.gwtid(+) and gr.orgid=f.orgid(+)\n" +
					"and gr.id=g.jdtid(+) and gr.orgid=g.orgid(+)\n" +
					"and gr.id=h.jiadtid(+) and gr.orgid=h.orgid(+)\n" +
					"and gr.id=i.pwtid(+) and gr.orgid=i.orgid(+)\n" +
					"and gr.id=j.qttid(+) and gr.orgid=j.orgid(+)\n" +
					"and gr.id='"+rd.getString("TA_DJ_GROUP","ID",0)+"'"; 
			
		try
        {
			coreDAO.executeQuery(sql, "allState", rd, conn);
			// �Ƶ�ƻ�TA_DJ_JHHOTEL
			if(null!=rd.getString("allState","hoteltid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_BXHOTEL","JHHOTEL", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHHOTEL","JHHOTEL", sd.getString("orgid"),rd, conn);
			}
			
			// ��������TA_DJ_JHCT
			if(null!=rd.getString("allState","cttid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_BXCT","JHCT", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHCT","JHCT", sd.getString("orgid"),rd, conn);
			}
			// Ʊ���� TA_DJ_JHPW
			if(null!=rd.getString("allState","pwtid",0)){
				queryState("sum(QD) QD,sum(XF) XF,sum(YGCB) YGCB","TA_DJ_BXPW","JHPW", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QD) QD,sum(XF) XF,sum(YGCB) YGCB","TA_DJ_JHPW","JHPW", sd.getString("orgid"),rd, conn);
			}
			// �������� TA_DJ_jHCL
			if(null!=rd.getString("allState","cltid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(JG) JG","TA_DJ_BXCL","JHCL", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(JG) JG","TA_DJ_JHCL","JHCL", sd.getString("orgid"),rd, conn);
			}
			// ���㰲��TA_DJ_JHJD
			if(null!=rd.getString("allState","jdtid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(TGCB) TGCB","TA_DJ_BXJD","JHJD", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(TGCB) TGCB","TA_DJ_JHJD","JHJD", sd.getString("orgid"),rd, conn);
			}
			// �ؽӰ���TA_DJ_JHDJ
			if(null!=rd.getString("allState","djtid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YFZK) YFZK","TA_DJ_BXDJ","JHDJ", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YFZK) YFZK","TA_DJ_JHDJ","JHDJ", sd.getString("orgid"),rd, conn);
			}
			// ���ﰲ��TA_DJ_JHGW
			if(null!=rd.getString("allState","gwtid",0)){
				queryState("sum(JDRS) JDRS,sum(XFE) XFE,sum(YJGS) YJGS,sum(RTF) RTF,sum(GSLC) GSLC","TA_DJ_BXGW","JHGW", sd.getString("orgid"),rd, conn);
			}else{
				queryState("count(id)","TA_DJ_JHGW","JHGW", sd.getString("orgid"),rd, conn);
			}
			// �ӵ㰲��TA_DJ_JHJIAD
			if(null!=rd.getString("allState","jiadtid",0)){
				queryState("sum(CBJE) CBJE,sum(CBQD) CBQD,sum(CBXF) CBXF,sum(RS) RS,sum(LR) LR,sum(YJGS) YJGS,sum(RTF) RTF","TA_DJ_BXJIADIAN","JHJIAD", sd.getString("orgid"),rd, conn);
			}else{
				queryState("count(id)","TA_DJ_JHJIAD","JHJIAD", sd.getString("orgid"),rd, conn);
			}
			// ��������TA_DJ_JHQT
			if(null!=rd.getString("allState","qttid",0)){
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_BXQT","JHQT", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(QDJE) QDJE,sum(XFJE) XFJE,sum(YGCB) YGCB","TA_DJ_JHQT","JHQT", sd.getString("orgid"),rd, conn);
			}
			// ���ΰ���TA_DJ_JHDY
			if(null!=rd.getString("allState","dytid",0)){
				queryState("sum(DYLK) DYLK,sum(DFF) DFF","TA_DJ_BXDY","JHDY", sd.getString("orgid"),rd, conn);
			}else{
				queryState("sum(DYLK) DYLK,sum(DFF) DFF","TA_DJ_JHDY","JHDY", sd.getString("orgid"),rd, conn);
			}
        }
        catch (Exception e)
        {
	        // ��ӡ������־
        	log.error("��ѯʧ��", e);
        	
        }
	}
}
