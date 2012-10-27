/*
 * AbstractBLC.java
 *
 * Created on 2003年11月22日, 上午10:59
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
     * 该方法暂不实现
     */
    public void stop() {
    }

    /**
     * 获得当前BLC的版本号;
     * 该版本号决定本BLC中的BL方法的接口原型,如果版本为1，则BL方法的原型应该为
     * bl(BizData data),在BL方法中可以通过data.get("SessionData")获得当前用户的
     * Session数据；如果版本为2，则BL方法的原型应该为bl(BizData rd,BizData sd),
     * 其中rd为当前请求的数据，sd为发出该请求的用户的Session信息，可在BL方法中
     * 通过add(...）,get(...),和remove(...)方法任意修改该用户的色session信息。
     *
     * @return 本BLC的版本号
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
     * 从当前BLC所在的对象池中获取另外一个BLC对象的引用 如果该对象不存在 则返回
     * 空值，否则返回该指定名称的类的一个BLC引用
     *
     * @param name String,指定BLC的名称，为包名+类名，例如com.bizsdk.app.Customer
     * @return 指定名称的BLC对象
     */
    public AbstractBLC getBLC(final String name) {
        return (AbstractBLC) _blcPool.getPoolItem(name);
    }

    /**
     * 执行当前BLC中的一个BL方法 该BL方法的原型必须为 int 方法名(BizData data);
     * data中包含着当前调用本方法的session对象的信息
     *
     * @param name 指定的BL方法
     * @param data 将要传递给该方法的数据对象
     * @return 被调用的防范返回值
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
     * 执行当前BLC中的一个BL方法 该接口方法的原型必须为:
     * int 方法名(BizData rd,BizData sd);
     *
     * @param name 指定的BL方法
     * @param rd   将要传递给该方法的当前请求的数据对象
     * @param sd   将要传递给该方法的当前会话的数据对象
     * @return 被调用BL方法的返回值
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