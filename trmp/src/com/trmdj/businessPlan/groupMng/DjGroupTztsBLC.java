package com.trmdj.businessPlan.groupMng;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.blc.DBBLC;

/**  
 * <b>项目名：</b>皇家国际资源管理系统<br/>  
 * <b>包名：</b>com.trmdj.businessPlan.groupMng<br/>  
 * <b>文件名：</b>DjGroupTztsBLC.java<br/>  
 * <b>版本信息：1.0</b><br/>  
 * <b>日期：</b>2011-7-17-上午02:26:30<br/>  
 * <b>Copyright (c)</b> 2011金索通软件科技有限公司-版权所有<br/>  
 *   
 */
/**  
 *   
 * <b>类名称：</b>DjGroupTztsBLC<br/>  
 * <b>类描述：</b><br/>  
 * <b>创建人：</b>Kale<br/>  
 * <b>修改人：</b>Kale<br/>  
 * <b>修改时间：</b>2011-7-17 上午02:26:30<br/>  
 * <b>修改备注：</b><br/>  
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
