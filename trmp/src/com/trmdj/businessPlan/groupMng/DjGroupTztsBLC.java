package com.trmdj.businessPlan.groupMng;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.blc.DBBLC;

/**  
 * <b>��Ŀ����</b>�ʼҹ�����Դ����ϵͳ<br/>  
 * <b>������</b>com.trmdj.businessPlan.groupMng<br/>  
 * <b>�ļ�����</b>DjGroupTztsBLC.java<br/>  
 * <b>�汾��Ϣ��1.0</b><br/>  
 * <b>���ڣ�</b>2011-7-17-����02:26:30<br/>  
 * <b>Copyright (c)</b> 2011����ͨ����Ƽ����޹�˾-��Ȩ����<br/>  
 *   
 */
/**  
 *   
 * <b>�����ƣ�</b>DjGroupTztsBLC<br/>  
 * <b>��������</b><br/>  
 * <b>�����ˣ�</b>Kale<br/>  
 * <b>�޸��ˣ�</b>Kale<br/>  
 * <b>�޸�ʱ�䣺</b>2011-7-17 ����02:26:30<br/>  
 * <b>�޸ı�ע��</b><br/>  
 * @version 1.0.0<br/>  
 *   
 */

public class DjGroupTztsBLC extends DBBLC
{
 private static Logger log = Logger.getLogger(DjGroupTztsBLC.class);
    
    public DjGroupTztsBLC()
    {
        this.entityName = "TA_DJ_TZTS";
    }
}
