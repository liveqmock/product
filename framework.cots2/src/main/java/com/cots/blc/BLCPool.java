/**
 *all rights reserved,@copyright 2003
 */
package com.cots.blc;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Description:
 *      BLCPool acts as a pool for all the BLC objects. A BLContext object muse be associated
 * wisth this pool before any BLC object can be retrieved from this pool.
 *
 * User: chugh
 * Date: 2004-10-10
 * Time: 15:29:34
 * Version: 1.0
 */
public final class BLCPool {

    private BLCRegistry registry;

    //map of all BLC object;
    private HashMap blcs;

    //the associated BLContext object.
    private BLContext context;

    private boolean inShutdown;

    public BLCPool(){
        blcs = new HashMap();
    }

    public BLCPool(int size){
        blcs = new HashMap(size);
    }

    public BLCPool(BLContext context){
        //call the default constructor;
        this();
        this.context = context;
    }

    public void setBLCRegistry(BLCRegistry registry){
        this.registry = registry;
    }

    /**
     *
     *
     * @return
     */
    public BLCRegistry getBLCRegistry(){
        return this.registry;
    }

    /**
     * get the associated BLContext object.
     *
     * @return the associated BLContext object.
     */
    public BLContext getContext(){
        return context;
    }

    /**
     * set the BLContext for which this pool services for.
     * When an BLC object is newly created, the pool must initialize the object
     * for use of the associated BLContext object.
     *
     * @param context the new BLContext object.
     */
    void setContext(BLContext context){
        this.context = context;
    }

    /**
     * Get a BLC object from this pool. Before any BLC object can be retrieved from this pool.
     * a BLContext object must be associated with this pool. otherwise the returned BLC object
     * may not work properly.
     *
     * @param name the qualified displayName of the target BLC class;
     * @return the target BLCObject;
     * @throws ClassNotFoundException if the blc class is not found, or the found class is not a subclass
     * of com.cots.blc.BLC.
     * @throws IllegalAccessException if the blc class has no default constructor. As a class that implements
     * com.cots.blc.BLC interface, each BLC must have a public default constructor that has public access and
     * has no arguments.
     * @throws InstantiationException if can't create an object for the target BLC class.
     */
    public BLC getBLC(String name)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException{
        //check if shutdown is in progress.
        if(inShutdown){
            throw new ClassNotFoundException("shutdown in progress, can't retrieve any BLC object");
        }else{
            boolean threadSafe = true;
            if(registry!=null){
                threadSafe = registry.isThreadSafeBLC(name);
            }
            Object blc;
            if(threadSafe){
                blc = blcs.get(name);
                if(blc == null){
                    synchronized(blcs){
                        //check if the blc has been created and initialized by the previoud thread;

                        blc = blcs.get(name);
                        if(blc == null){
                            String implClass;
                            if(registry!=null){
                                implClass = registry.getImplClass(name);
                            }else{
                                implClass = name;
                            }
                            blc = Class.forName(implClass).newInstance();

                            //up to here, the blc must not be null.
                            //check if blc is subclass of BLC;
                            if(blc instanceof BLC){
                                //assoicate this BLC object with BLContext.
                                ((BLC)blc).init(context);
                            }else{
                                //the class doest not implements BLC interface.
                                throw new ClassNotFoundException("not a subclass of BLC: "+name);
                            }
                            blcs.put(name,blc);
                        }
                    }
                }
            }else{
                blc = Class.forName(name).newInstance();

                //up to here, the blc must not be null.
                //check if blc is subclass of BLC;
                if(blc instanceof BLC){
                    //assoicate this BLC object with BLContext.
                    ((BLC)blc).init(context);
                }else{
                    //the class doest not implements BLC interface.
                    throw new ClassNotFoundException("not a subclass of BLC: "+name);
                }
            }
            return (BLC)blc;
        }
    }

    /**
     * shut down this pool. This will cause all the BLC in the pool be released.
     */
    synchronized void shutdown(){
        //when shutdown is in progress, clients should not get any BLC object from this pool.
        inShutdown = true;

        //release all the references to BLC objects;
        Iterator names = blcs.keySet().iterator();
        while(names.hasNext()){
            BLC blc = (BLC)blcs.get(names.next());
            blc.destroy();
        }
    }
}
