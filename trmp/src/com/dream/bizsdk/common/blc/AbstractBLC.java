/*
 * AbstractBLC.java
 *
 * Created on 2003��11��22��, ����10:59
 */

package com.dream.bizsdk.common.blc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.pool.PoolItem;

/**
 * An AbstractBLC provide minimum supports for a BLC Component.
 * All BLC components should extends this class directly or indirectly.
 *
 * @author chuguanghua
 */
public abstract class AbstractBLC extends PoolItem {

    /*create two classes array.*/
    protected final static Class[] cls1 = new Class[1];
    protected final static Class[] cls2 = new Class[2];
    protected String className = null;

    static {
        //final BizData d = new BizData();
        cls1[0] = BizData.class;
        cls2[0] = BizData.class;
        cls2[1] = BizData.class;
    }

    //BLC pool, An abstract BLC referenced object must resides in a
    //BLCsPool object;
    protected BLCsPool _blcPool = null;
    //BLContext thie BLC object belongs to;
    protected BLContext _context = null;
    //version of this BLC;
    protected int version = 2;
    //logger object this blc can use;

    protected DAO coreDAO;
    protected DAO vehDAO;
    /**
     * Initialize this BLC object, this object will hold a refernce to the
     * BLContext object and BLCsPool object, so other methods can access
     * the variables and objects in the bl context or get reference to other
     * BLC object(BLCsPool.getBLC(String blcName);
     *
     * @param context the BLC context;
     * @return true if succeed.false otherwise;
     */
    public boolean init(final BLContext context) {
        _blcPool = context.getBLCsPool();
        _context = context;
        coreDAO=_context.getDAO("core");
        vehDAO = _context.getDAO("veh");
        //get the name of the Class object of this class;
        className = this.getClass().getName();
        return true;
    }

    /**
     * �÷����ݲ�ʵ��
     */
    public void stop() {
    }

    /**
     * ��õ�ǰBLC�İ汾��;
     * �ð汾�ž�����BLC�е�BL�����Ľӿ�ԭ��,����汾Ϊ1����BL������ԭ��Ӧ��Ϊ
     * bl(BizData data),��BL�����п���ͨ��data.get("SessionData")��õ�ǰ�û���
     * Session���ݣ�����汾Ϊ2����BL������ԭ��Ӧ��Ϊbl(BizData rd,BizData sd),
     * ����rdΪ��ǰ��������ݣ�sdΪ������������û���Session��Ϣ������BL������
     * ͨ��add(...��,get(...),��remove(...)���������޸ĸ��û���ɫsession��Ϣ��
     *
     * @return ��BLC�İ汾��
     */
    public int getVersion() {
        return version;
    }

    /**
     * get the BLContext object that this blc is running within;
     *
     * @return BLContext object;
     */
    public BLContext getBLContext() {
        return _context;
    }

    /**
     * �ӵ�ǰBLC���ڵĶ�����л�ȡ����һ��BLC��������� ����ö��󲻴��� �򷵻�
     * ��ֵ�����򷵻ظ�ָ�����Ƶ����һ��BLC����
     *
     * @param name String,ָ��BLC�����ƣ�Ϊ����+����������com.bizsdk.app.Customer
     * @return ָ�����Ƶ�BLC����
     */
    public AbstractBLC getBLC(final String name) {
        return (AbstractBLC) _blcPool.getPoolItem(name);
    }

    /**
     * ִ�е�ǰBLC�е�һ��BL���� ��BL������ԭ�ͱ���Ϊ int ������(BizData data);
     * data�а����ŵ�ǰ���ñ�������session�������Ϣ
     *
     * @param name ָ����BL����
     * @param data ��Ҫ���ݸ��÷��������ݶ���
     * @return �����õķ�������ֵ
     */
    public int execBL(String name, final BizData data) throws InvocationTargetException {
        int retVal = 0;
        //get logger for this object;
        Logger log = Logger.getLogger("com.dream.blc");
        if (log.isDebugEnabled()) {
            log.debug("accepted bl request:  " + this.getClass().getName() + "." + name + "(BizData)");
        }

        try {
            //get the target method;
            final Class clsBLC = this.getClass();
            final Method method = clsBLC.getMethod(name, cls1);

            //call the method;
            final BizData[] bizdata = new BizData[1];
            bizdata[0] = data;
            Object v = method.invoke(this, bizdata);
            data.add(SysVar.RETURN_VALUE, v);
            if (v instanceof Integer) {
                retVal = ((Integer) v).intValue();
            }

            //write log;
            if (log.isDebugEnabled()) {
                log.debug("finished bl request:  " + this.getClass().getName() + "." + name + "(BizData)");
            }
        } catch (NoSuchMethodException e1) {
            log.error("no bl method named:"
                    + name + "(BizData) in class:" + this.getClass().getName(), e1);
            return SysError.BL_NOT_AVAILABLE;
        } catch (IllegalAccessException e2) {
            log.error("can't access bl method named:"
                    + name + "(BizData) in class:" + this.getClass().getName(), e2);
            return SysError.NO_ACCESS;
        } catch (InvocationTargetException e3) {
            log.error("EXCEPTION in bl method named: "
                    + name + "(BizData)  in class: " + this.getClass().getName(), e3.getTargetException());
            throw e3;
        }
        return retVal;
    }

    /**
     * ִ�е�ǰBLC�е�һ��BL���� �ýӿڷ�����ԭ�ͱ���Ϊ:
     * int ������(BizData rd,BizData sd);
     *
     * @param name ָ����BL����
     * @param rd   ��Ҫ���ݸ��÷����ĵ�ǰ��������ݶ���
     * @param sd   ��Ҫ���ݸ��÷����ĵ�ǰ�Ự�����ݶ���
     * @return ������BL�����ķ���ֵ
     */
    public int execBL(final String name, final BizData rd, final BizData sd) throws InvocationTargetException {
        int retVal = 0;
        //get log for this object;
        Logger log = Logger.getLogger("com.dream.blc");
        if (log.isDebugEnabled()) {
            log.debug("accepted bl request:  " + this.getClass().getName() + "." + name + "(BizData,BizData)");
        }

        try {

            //get Class Oject of this Object;
            final Class clsBLC = this.getClass();
            //find the mthod;
            final Method method = clsBLC.getMethod(name, cls2);

            final BizData[] bizdata = new BizData[2];
            bizdata[0] = rd;
            bizdata[1] = sd;
            Object v = method.invoke(this, bizdata);
            rd.add(SysVar.RETURN_VALUE, v);

            if (v instanceof Integer) {
                retVal = ((Integer) v).intValue();
            }

            if (log.isDebugEnabled()) {
                log.debug("finished bl request:  " + this.getClass().getName() + "." + name + "(BizData,BizData)");
            }
        } catch (NoSuchMethodException e1) {
            //log.error("no bl method named:"+name+"(BizData) in class:"+this.getClass().getName(),e1);
            return SysError.BL_NOT_AVAILABLE;
        } catch (IllegalAccessException e2) {
            log.error("can't access bl method named:"
                    + name + "(BizData,BizData) in class:" + this.getClass().getName(), e2);
            return SysError.NO_ACCESS;
        } catch (InvocationTargetException e3) {
            log.error("EXCEPTION in bl method named: "
                    + name + "(BizData,BizData)  in class: " + this.getClass().getName(), e3.getTargetException());
            throw e3;
        }
        return retVal;
    }
}