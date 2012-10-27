package com.trm.zttdMng;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.databus.BizData;
/**
 * ���ŵǼǱ�����¼���ZtRigesterForm��������
 * 
 * @author [hl.Wei]
 * @date [Dec 20, 2011 7:08:45 PM]
 */
public class ZtRigesterFormHelper
{
	/**
	 * ��־��ӡ
	 */
	private static Logger log = Logger.getLogger(ZtRigesterFormHelper.class);
	
	/**
	 * ����Ż�����Ϣ
	 * 
	 * @param rd �������ݶ���
	 * @param groupId ��ID
	 * @throws SQLException 
	 */
	public void addGroupInfo(BizData rd,BizData sd,String groupID,ZtRigesterForm ztRigest) throws SQLException
	{
		// �ű��
		rd.add("TA_ZT_GROUP", "ID", 0, groupID);
		
		// ����·���
		rd.add("TA_ZT_GROUP", "LINE_ID", 0, rd.getStringByDI("TA_ZT_LINEMNG", "LINE_ID", 0));
		
		// ��״̬
		rd.add("TA_ZT_GROUP", "STATE", "1");
		
		// ������
		rd.add("TA_ZT_GROUP", "TLX", "1");
		
		// Ĭ��Ϊ0-����
		rd.add("TA_ZT_GROUP", "FTZT", 0, "0");
		
		// ������
		int persons = Integer.parseInt(rd.getStringByDI("TA_ZT_GROUP", "ADULT_COUNT", 0))
					+ Integer.parseInt(rd.getStringByDI("TA_ZT_GROUP", "CHILDREN_COUNT", 0));
		
		// ��������
		rd.add("TA_ZT_GROUP", "MINPERSONCOUNT", 0, persons);
		
		// ���տ�����
		rd.add("TA_ZT_GROUP", "MAXPERSONCOUNT", 0, persons);
		
		// ��ѯ��ǰ����ID
		String orgId = selectCurrOrgId(rd,sd,ztRigest.getDao());
		
		// ��ѯ��ǰ�û����ڻ����Ķ�������ID
		String topId = ztRigest.recursion4ID("HRORGANIZATION", "PARENT_ORGID", "ORGID", orgId, "0", rd);
		
		// ��ѯ��ǰ�û����ڻ����Ķ������������ƺ���ϵ��ʽ
		selectOrgNameTel(rd,topId,ztRigest.getDao());
		
		// ��ǰ�û�NO
		rd.add("TA_ZT_GROUP", "USER_NO", 0, sd.getString("USERNO"));
		
		// ����������ID
		rd.add("TA_ZT_GROUP", "orgid", 0, topId);
		
		// ��������������
		rd.add("TA_ZT_GROUP", "SZLXSMC", 0, rd.getStringByDI("name2tel", "NAME", 0));
		
		// ������������ϵ��ʽ
		rd.add("TA_ZT_GROUP", "SZLXSLXDH", 0, rd.getStringByDI("name2tel", "TEL", 0));
	}

	/**
	 * ��ѯ��ǰ�û����ڻ����Ķ������������ƺ���ϵ��ʽ
	 * 
	 * @param rd �������ݶ���
	 * @param topId �������ID
	 * @param dao ���ݿ��������
	 */
	private void selectOrgNameTel(BizData rd, String topId, DAO coreDao)
    {
		// SQL���
		String sql = "select NAME,TEL from HRORGANIZATION where orgid="+topId;
		
		try
        {
			// ִ�в�ѯSQL,���������"orgInfo"
	        coreDao.executeQuery(sql, "name2tel", rd);
        }
        catch (SQLException e)
        {
	       e.printStackTrace();
        }
    }

	/**
	 * ��ѯ��ǰ�û����ڻ���ID
	 * 
	 * @param rd �������ݶ���
	 * @param sd session����
	 */
	private String selectCurrOrgId(BizData rd, BizData sd,DAO coreDao)
    {
		// ��ǰ�û����
		String userNo = sd.getString("USERNO");
		
		// ��ѯ��ǰ�û����ڻ���ID��SQL���
		String sql = "select ORGNAME,ORGID,EMPTEL,BUSINESS_FAX,BUSINESS_MOBILE,QQ from DRMUSER u ,HREMPLOYEE e where u.userno="+userNo+" and u.EMPID=e.empid";
		
		try
        {
			// ִ�в�ѯSQL,���������"orgInfo"
	        coreDao.executeQuery(sql, "orgInfo", rd);
        }
        catch (SQLException e)
        {
	       e.printStackTrace();
	       
	       return "";
        }
        
        // ��ǰ����ID
        return rd.getStringByDI("orgInfo", "ORGID", 0);
    }

	/**
	 * ��ÿ���г������źź��г�ID
	 * 
	 * @param rd �������ݶ���
	 * @param groupID ��ID
	 * @param ztGroup ���ŵǼǱ�����¼�������
	 */
	public void addIdTid(BizData rd, String groupID,
            ZtRigesterForm ztGroup)
    {
		// ����ÿ����߳���ϸ
		for (int i = 0;i < rd.getTableRowsCount("TA_ZT_LINEDETAI4G"); i++)
		{
			// ���õ�ǰ�߳���ϸ��ID
			rd.add("TA_ZT_LINEDETAI4G", "ID", i, ztGroup.queryMaxIDByPara("TA_ZT_LINEDETAI4G", "ID", null));
			
			// ���õ�ǰ�߳���ϸ����ID
			rd.add("TA_ZT_LINEDETAI4G", "TID", i, groupID);
		}
    }

	/**
	 * ����������Ϣ
	 * 
	 * @param rd �������ݶ���
	 * @throws SQLException 
	 */
	public int addOrderForm(BizData rd, BizData sd,String groupId, ZtRigesterForm ztGroup) throws SQLException
    {
		// ȡ��������
		String bDate = rd.getStringByDI("TA_ZT_GROUP", "BEGIN_DATE", 0);
		
		// ��ϴ��������
		bDate = bDate.replaceAll("-", "");
		
		// ���ڸ�ʽ����׼
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		// ������
        String gMoneyID = "";
		
		try
        {
			// ����������ת���ɸ�ʽ����������������
	        bDate = sdf.format(sdf.parse(bDate));
	        
	        // ��ȡ��󶩵����
			int gID = ztGroup.queryMaxIDByPara("TA_ZT_YKORDER", "substr(id,11,4)", "substr(id,3,8) = '"+sdf.format(sdf.parse(bDate))+"'");
			
			if(gID < 10) 
			{
				// ����Ĭ�϶�����
				gMoneyID = "HJ"+sdf.format(sdf.parse(bDate))+"1001";
				
				ztGroup.clearCacheObj("TA_ZT_YKORDER", "substr(id,11,4)");
			}
			else
			{
				// �Զ������µĶ�����
				gMoneyID = "HJ"+sdf.format(sdf.parse(bDate))+gID;
			}
			
        }
        catch (ParseException e)
        {
        	// ��ӡ������־
        	log.error("error occurs in method of addOrderForm! ",e);
        	
	       return SysError.BL_EXEC_ERROR;
        }
        
        // ���ö�����
        rd.add("TA_ZT_YKORDER", "ID", 0, gMoneyID);
        
        // �����ź�
        rd.add("TA_ZT_YKORDER", "TID", 0, groupId);
        
        // ���ö�������
        rd.add("TA_ZT_YKORDER", "YSRS", 0, (Integer.parseInt(rd.getStringByDI("TA_ZT_GROUP", "ADULT_COUNT", 0)) + Integer.parseInt(rd.getStringByDI("TA_ZT_GROUP", "CHILDREN_COUNT", 0))));
		
        // ���ö���״̬��Ĭ��Ϊ0��δȷ��
        rd.add("TA_ZT_YKORDER", "DD_CONFIRM", 0, "0");
        
        // �ƶ���
        rd.add("TA_ZT_YKORDER", "CREATER", 0, sd.getString("USERNO"));
        
        // ��ϵ��
        rd.add("TA_ZT_YKORDER", "BUSINESS_NAME", 0, sd.getString("USERNAME"));
        
        // ����ȷ��
        if ("".equals(rd.getStringByDI("TA_ZT_YKORDER", "ISCONFIRM", 0)))
        {
        	rd.add("TA_ZT_YKORDER", "ISCONFIRM", 0, 0);
        }
        
        // ����״̬; 0��δ���ˣ�1�������У�2��ͨ����3����ͨ��
        rd.add("TA_ZT_YKORDER", "DZZT", 0, "0");
        
        // �����̽���״̬;Y/N
        rd.add("TA_ZT_YKORDER", "JSZT", 0, "N");
        
    	// ��ѯ��ǰ����ID
		String orgId = selectCurrOrgId(rd,sd,ztGroup.getDao());
		
		// ����ID
		rd.add("TA_ZT_YKORDER", "ORGID", 0, orgId);
		
		// ��ѯ��ǰ�û����ڻ����Ķ�������ID
		String topId = ztGroup.recursion4ID("HRORGANIZATION", "PARENT_ORGID", "ORGID", orgId, "0", rd);
		
		// ����ID 
		rd.add("TA_ZT_YKORDER", "PORGID", 0, topId);
		
		// ��ѯ��ǰ�û����ڻ����Ķ�������������
		selectOrgNameTel(rd,topId,ztGroup.getDao());
		
		// ��������������
		rd.add("TA_ZT_YKORDER", "CMPNY_NAME", 0, rd.getStringByDI("name2tel", "NAME", 0) + rd.getStringByDI("orgInfo", "ORGNAME", 0));
		
		// ��ϵ�˵绰
		rd.add("TA_ZT_YKORDER", "BUSINESS_TEL", 0 , rd.getStringByDI("orgInfo", "EMPTEL", 0));
		
		// ����
		rd.add("TA_ZT_YKORDER", "BUSINESS_FAX", 0, rd.getStringByDI("orgInfo", "BUSINESS_FAX", 0));
		
		// ��ϵ���ֻ�
		rd.add("TA_ZT_YKORDER", "BUSINESS_MOBILE", 0, rd.getStringByDI("orgInfo", "BUSINESS_MOBILE", 0));
		
		// QQ_MSN
		rd.add("TA_ZT_YKORDER", "QQ_MSN", 0, rd.getStringByDI("orgInfo", "QQ", 0));
		
		// �����¼���ʽ
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// ����ʱ��
		rd.add("TA_ZT_YKORDER", "REGEDIT_TIME", 0, sdf2.format(new Date()));
        
		return 1;
    }

	/**
	 * ��ÿ���ο���Ϣ���붩����(һ���Ŷ�����Ӧ1������ο���Ϣ)
	 * 
	 * @param rd �������ݶ���
	 * @param orderNo ������
	 * @param ztRigesterForm ���ŵǼǱ�����¼�������
	 * @return �����
	 */
	public void addtouristOrder(BizData rd, String orderNo,
            ZtRigesterForm ztGroup)
    {
		// ����ÿ���ο���Ϣ
		for (int i = 0; i < rd.getTableRowsCount("TA_ZT_VISITOR"); i++)
		{
			// ������
			rd.add("TA_ZT_VISITOR", "M_ID", i, orderNo);
			
			// ���
			rd.add("TA_ZT_VISITOR","ID", i, ztGroup.queryMaxIDByPara("TA_ZT_VISITOR", "ID", null));
		}
    }

	/**
	 * �����ż۸���Ϣ(һ���Ŷ�Ӧ1������ż۸���Ϣ)
	 * 
	 * @param rd �������ݶ���
	 * @param groupID ��ID
	 * @param ztRigesterForm ���ŵǼǱ�����¼�������
	 * @return �����
	 */
	public void addGroupPrice(BizData rd, String groupID,
            ZtRigesterForm ztGroup)
    {
		// ���������ż۸�����
		for (int i = 0; i < rd.getTableRowsCount("TA_ZT_GPRICE"); i++)
		{
			// ͬ�м�
			rd.add("TA_ZT_GPRICE", "PRICE_TH", i, rd.getStringByDI("TA_ZT_GPRICE", "PRICE_MS", i));
			
			// �ź�
			rd.add("TA_ZT_GPRICE", "G_ID", i, groupID);
			
			// ����
			rd.add("TA_ZT_GPRICE", "ID", i, ztGroup.queryMaxIDByPara("TA_ZT_GPRICE", "ID", null));
		}
    }

	/**
	 * �����Ŷ����۸�һ��������Ӧ1����������۸�
	 * 
	 * @param rd �������ݶ���
	 * @param orderNo ������
	 * @param ztRigesterForm ���ŵǼǱ�����¼�������
	 */
	public int addOrderprice4Grp(BizData rd, String orderNo,
            ZtRigesterForm ztGroup)
    {
	    // ���������Ŷ����۸�
	    for (int i = 0; i < rd.getTableRowsCount("TA_ZT_GORDERPRICE"); i++)
	    {
	    	// ����
	    	rd.add("TA_ZT_GORDERPRICE", "ID", i, ztGroup.queryMaxIDByPara("TA_ZT_GORDERPRICE", "ID", null));
	    	
	    	// ������
	    	rd.add("TA_ZT_GORDERPRICE", "ORDERID", i, orderNo);
	    	
	    	// �۸�����
	    	rd.add("TA_ZT_GORDERPRICE", "PRICE_TYPE", i, rd.getStringByDI("TA_ZT_GPRICE", "PRICE_TYPE", i));
	    	
	    	// ���м�
	    	rd.add("TA_ZT_GORDERPRICE", "PRICE_MS", i, rd.getStringByDI("TA_ZT_GPRICE", "PRICE_MS", i));
	    	
	    	// ͬ�м�
	    	rd.add("TA_ZT_GORDERPRICE", "PRICE_TH", i, rd.getStringByDI("TA_ZT_GPRICE", "PRICE_TH", i));
	    }
	    
	    return 1;
    }

	/**
	 * �����Ŷ���������Ϣ(һ���Ŷ�����Ӧ0�����������Ϣ)
	 * 
	 * @param rd �������ݶ���
	 * @param orderNo ������
	 * @param ztRigesterForm ���ŵǼǱ�����¼�������
	 */
	public void addInsurance(BizData rd, String orderNo,
            ZtRigesterForm ztGroup)
    {
		// ���������Ŷ���������Ϣ
		for (int i = 0; i < rd.getTableRowsCount("TA_ZT_GINSURANCE"); i++)
		{
			// ����
	    	rd.add("TA_ZT_GINSURANCE", "ID", i, ztGroup.queryMaxIDByPara("TA_ZT_GINSURANCE", "ID", null));
	    	
	    	// ������
	    	rd.add("TA_ZT_GINSURANCE", "ORDERID", i, orderNo);
		}
    }

	/**
	 * ���������ʷ��¼
	 * 
	 * @param rd �������ݶ���
	 * @param orderNo ������
	 * @param ztRigesterForm ���ŵǼǱ�����¼�������
	 */
	public void addAccountHis(BizData rd, String orderNo,
            ZtRigesterForm ztGroup)
    {
		// ����
    	rd.add("TA_ZT_DZJL_HIS", "ID", 0, ztGroup.queryMaxIDByPara("TA_ZT_DZJL_HIS", "ID", null));
    	
    	// ������
    	rd.add("TA_ZT_DZJL_HIS", "ORDERID", 0, orderNo);
    	
    	// Ӧ����
    	rd.add("TA_ZT_DZJL_HIS", "FK_ALREADY", 0, rd.getStringByDI("TA_ZT_YKORDER", "YIN_SK", 0));
    	
    	// �Ѹ���
    	rd.add("TA_ZT_DZJL_HIS", "FK_SHOULD", 0, rd.getStringByDI("TA_ZT_YKORDER", "YI_SK", 0));
    	
    	// ���θ���
    	rd.add("TA_ZT_DZJL_HIS", "FK_THISTIME", 0, rd.getStringByDI("TA_ZT_YKORDER", "YI_SK", 0));
    }
	
	/**
	 * ������·��Ϣ
	 * 
	 * @param rd
	 * @param ztRigesterForm
	 * @throws SQLException 
	 */
	public void addLineInfo(BizData rd, BizData sd, ZtRigesterForm ztRigest) throws SQLException 
	{
		// ��·ID,�����ֵ
		rd.add("TA_ZT_LINEMNG", "LINE_ID", 0,ztRigest.queryMaxIDByPara("TA_ZT_LINEMNG", "LINE_ID", null));
		
		// ��·����
		rd.add("TA_ZT_LINEMNG", "LINE_NAME", 0, rd.getStringByDI("TA_ZT_GROUP", "XLMC", 0));
		
		// ������ID
		rd.add("TA_ZT_LINEMNG", "USERNO", 0, sd.getString("USERNO"));
		
		// ��·����,2:����
		rd.add("TA_ZT_LINEMNG", "XLXZ", 0, "2");
		
		// ��ѯ��ǰ����ID
		String orgId = selectCurrOrgId(rd,sd,ztRigest.getDao());
		
		// ��ѯ��ǰ�û����ڻ����Ķ�������ID
		String topId = ztRigest.recursion4ID("HRORGANIZATION", "PARENT_ORGID", "ORGID", orgId, "0", rd);
		
		// ��ѯ��ǰ�û����ڻ����Ķ������������ƺ���ϵ��ʽ
		selectOrgNameTel(rd,topId,ztRigest.getDao());
		
		// ��ǰ�û�NO
		rd.add("TA_ZT_LINEMNG", "USERNO", 0, sd.getString("USERNO"));
		
		// ����������ID
		rd.add("TA_ZT_LINEMNG", "orgid", 0, topId);
		
		// ��������������
		rd.add("TA_ZT_LINEMNG", "SZLXSMC", 0, rd.getStringByDI("name2tel", "NAME", 0));
		
		// ������������ϵ��ʽ
		rd.add("TA_ZT_LINEMNG", "SZLXSLXDH", 0, rd.getStringByDI("name2tel", "TEL", 0));
		
	}
	
	/**
	 * ������·���ն�Ӧ��ϵ��Ϣ
	 * 
	 * @param rd �������ݶ���
	 * @param lineId ��·ID
	 */
	public void addLine2Insura(BizData rd, String lineId)
	{
		// �������б�����Ϣ
		for (int i = 0; i < rd.getTableRowsCount("TA_ZT_GINSURANCE"); i++)
		{
			// ��·ID
			rd.add("TA_ZT_LINEANDINSURANCE", "LINE_ID", i, lineId);
			
			// ��������
			rd.add("TA_ZT_LINEANDINSURANCE", "INSURANCEID", i , Integer.parseInt(rd.getStringByDI("TA_ZT_GINSURANCE", "INSURANCETYPE", i)));
		}
	}
	
	/**
	 * ������·���ϵص�
	 * 
	 * @param rd �������ݶ���
	 * @param lineId ��·ID
	 */
	public void addGather(BizData rd, String lineId,ZtRigesterForm ztGroup)
	{
		// ������ǰ�ŵ�������·
		for (int i = 0; i < rd.getTableRowsCount("TA_ZT_GATHER"); i++)
		{
			// ��·ID
			rd.add("TA_ZT_GATHER", "LINE_ID", i, lineId);
			
			// ���ϵص�ID
			rd.add("TA_ZT_GATHER", "GATHER_ID", i, ztGroup.queryMaxIDByPara("TA_ZT_GATHER", "GATHER_ID", null));
		}
	}

	/**
	 * �������г���ϸ��Ϣ(1���Ŷ�Ӧ1������г���ϸ���г���ϸ����Ϊ��λ)
	 * 
	 * @param rd �������ݶ���
	 * @param conn ���ݿ�����
	 * @param groupId ��ID
	 * @return �����
	 * @throws SQLException ���ݿ�ִ�й��̴���
	 */
	public int insertRouteDetail(BizData rd, Connection conn, String groupId) throws SQLException 
	{
		// �������г���ϸSQL���
		String sql = "insert into TA_ZT_LINEDETAI4G(ID,TID,RQ,XCMX,BREAKFAST,CMEAL,SUPPER,ZSBZ) values (?,?,?,?,?,?,?,?)";
		
		// ����DB��������
		PreparedStatement preStmt = null;
		
        try
        {
        	// DB��������
    		preStmt = conn.prepareStatement(sql);
	        
    		// �������е��г���ϸ
			for(int i = 0; i < rd.getTableRowsCount("TA_ZT_LINEDETAI4G"); i++)
			{
				// ��������
				preStmt.setInt(1, Integer.parseInt(rd.getStringByDI("TA_ZT_LINEDETAI4G", "ID", i)));
				
				// ������ID
				preStmt.setString(2, rd.getStringByDI("TA_ZT_LINEDETAI4G", "TID", i));
				
				// ��������
				preStmt.setString(3, rd.getStringByDI("TA_ZT_LINEDETAI4G", "RQ", i));
				
				// �����г���ϸ
				preStmt.setBytes(4, rd.getStringByDI("TA_ZT_LINEDETAI4G", "XCMX", i).getBytes("GBK"));
				
				// �������
				preStmt.setString(5, rd.getStringByDI("TA_ZT_LINEDETAI4G", "BREAKFAST", i));
				
				// �����в�
				preStmt.setString(6, rd.getStringByDI("TA_ZT_LINEDETAI4G", "CMEAL", i));
				
				// �������
				preStmt.setString(7, rd.getStringByDI("TA_ZT_LINEDETAI4G", "SUPPER", i));
				
				// ����ס�ޱ�׼
				preStmt.setString(8, rd.getStringByDI("TA_ZT_LINEDETAI4G", "ZSBZ", i));
				
				// ִ�е�ǰSQL���
				preStmt.executeUpdate();
			}
			
			preStmt.close();
        }
        catch (SQLException e)
        {
	        e.printStackTrace();
	        
	        // ���ж�
	        if(null != preStmt)
	        {
	        	// �ر�DB��������
	        	preStmt.close();
	        }
	        
	        return SysError.INSERT_ERROR;
        }
        catch (UnsupportedEncodingException e)
        {
	        e.printStackTrace();
	        
	        // ���ж�
	        if (null != preStmt)
	        {
	        	// �ر�DB��������
	        	preStmt.close();
	        }
	        
	        return SysError.PARAM_PARSE_ERROR;
        }
		
		// DEBUGģʽ����ӡSQL
		log.debug("���г���ϸ�����SQL��"+sql);
		
		return 1;
	}
	
	/**
	 * ������·����BLOB��Ϣ
	 * 
	 * @param rd �������ݶ���
	 * @param lineId ��·ID
	 * @param conn ���ݿ�����
	 * @return �����
	 * @throws SQLException 
	 */
	public int insertLineMngBlob(BizData rd, String lineId, Connection conn) throws SQLException
    {
		// ������·����BLOB��Ϣ��SQL
		String sql = "insert into TA_ZT_LINEMNGBLOB(XLID,TSJS,FYBH,FYBBH,YDXZ,ZFXM,GWD,WXTS,CJLYXZ,LYZYSX) values (?,?,?,?,?,?,?,?,?,?)";
		
		// ����DB��������
		PreparedStatement preStmt = null;
		
		try
        {
			// DB��������
	        preStmt = conn.prepareStatement(sql);
	        
	        // ������·ID
	        preStmt.setInt(1, Integer.parseInt(lineId));
	        
		    // ������ɫ����
	        preStmt.setBytes(2, rd.getStringByDI("TA_ZT_LINEMNGBLOB", "TSJS", 0).getBytes("GBK"));
		        
		    // ���÷��ð���
	        preStmt.setBytes(3, rd.getStringByDI("TA_ZT_LINEMNGBLOB", "FYBH", 0).getBytes("GBK"));
		        
		    // ���÷��ò�����
	        preStmt.setBytes(4, rd.getStringByDI("TA_ZT_LINEMNGBLOB", "FYBBH", 0).getBytes("GBK"));
		        
		    // ����Ԥ����֪
	        preStmt.setBytes(5, rd.getStringByDI("TA_ZT_LINEMNGBLOB", "YDXZ", 0).getBytes("GBK"));
		        
		    // �����Է���Ŀ
	        preStmt.setBytes(6, rd.getStringByDI("TA_ZT_LINEMNGBLOB", "ZFXM", 0).getBytes("GBK"));
		        
		    // ���ù����
	        preStmt.setBytes(7, rd.getStringByDI("TA_ZT_LINEMNGBLOB", "GWD", 0).getBytes("GBK"));
		        
		    // ������ܰ��ʾ
	        preStmt.setBytes(8, rd.getStringByDI("TA_ZT_LINEMNGBLOB", "WXTS", 0).getBytes("GBK"));
		        
		    // ���ó���������֪
	        preStmt.setBytes(9, rd.getStringByDI("TA_ZT_LINEMNGBLOB", "CJLYXZ", 0).getBytes("GBK"));
		        
		    // ��������ע������
	        preStmt.setBytes(10, rd.getStringByDI("TA_ZT_LINEMNGBLOB", "LYZYSX", 0).getBytes("GBK"));
	        
	        // ִ��SQL
	        preStmt.executeUpdate();
		        
        }
		catch (SQLException e)
	    {
			e.printStackTrace();
		        
		    // ���ж�
		    if(null != preStmt)
		    {
		    	// �ر�DB��������
		        preStmt.close();
		    }
		        
		    return SysError.INSERT_ERROR;
	     }
	     catch (UnsupportedEncodingException e)
	     {
		     e.printStackTrace();
		        
		     // ���ж�
		     if (null != preStmt)
		     {
		        // �ر�DB��������
		        preStmt.close();
		     }
		        
		     return SysError.PARAM_PARSE_ERROR;
	      }
		
		return 1;
    }

}
