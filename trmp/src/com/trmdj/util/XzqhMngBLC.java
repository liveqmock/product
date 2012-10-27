package com.trmdj.util;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.blc.DBBLC;


/**  
 * <b>项目名：</b>皇家国际资源管理系统<br/>  
 * <b>包名：</b>com.trmdj.util<br/>  
 * <b>文件名：</b>xzqhMngBLC.java<br/>  
 * <b>版本信息：1.0</b><br/>  
 * <b>日期：</b>2011-7-12-上午04:55:54<br/>  
 * <b>Copyright (c)</b> 2011金索通软件科技有限公司-版权所有<br/>  
 *   
 */
/**  
 *   
 * <b>类名称：</b>xzqhMngBLC<br/>  
 * <b>类描述：</b>行政区划管理<br/>  
 * <b>创建人：</b>Kale<br/>  
 * <b>修改人：</b>Kale<br/>  
 * <b>修改时间：</b>2011-7-12 上午04:55:54<br/>  
 * <b>修改备注：</b><br/>  
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
