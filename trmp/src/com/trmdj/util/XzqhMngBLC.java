package com.trmdj.util;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.blc.DBBLC;


/**  
 * <b>��Ŀ����</b>�ʼҹ�����Դ����ϵͳ<br/>  
 * <b>������</b>com.trmdj.util<br/>  
 * <b>�ļ�����</b>xzqhMngBLC.java<br/>  
 * <b>�汾��Ϣ��1.0</b><br/>  
 * <b>���ڣ�</b>2011-7-12-����04:55:54<br/>  
 * <b>Copyright (c)</b> 2011����ͨ����Ƽ����޹�˾-��Ȩ����<br/>  
 *   
 */
/**  
 *   
 * <b>�����ƣ�</b>xzqhMngBLC<br/>  
 * <b>��������</b>������������<br/>  
 * <b>�����ˣ�</b>Kale<br/>  
 * <b>�޸��ˣ�</b>Kale<br/>  
 * <b>�޸�ʱ�䣺</b>2011-7-12 ����04:55:54<br/>  
 * <b>�޸ı�ע��</b><br/>  
 * @version 1.0.0<br/>  
 *   
 */

public class XzqhMngBLC extends DBBLC
{
    private static Logger log = Logger.getLogger(XzqhMngBLC.class);

    public XzqhMngBLC() {
        this.entityName = "XZQH";
    }
}
