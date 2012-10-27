/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.blc;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-2-27
 * Time: 18:16:34
 */
public final class BLContextCollection {
    protected static ArrayList cs = new ArrayList();
    protected static HashMap csNames = new HashMap();
    private static Logger log = Logger.getLogger("com.dream.blcontext.collection");

    synchronized public static void register(IBLContext newContext) {
        try {
            if (newContext == null || csNames.get(newContext.getName()) != null) {
                return;
            } else {
                int size = cs.size();
                for (int i = 0; i < size; i++) {
                    IBLContext blcontext = (IBLContext) cs.get(i);
                    try {
                        //only if the blcontext is a local BLContext boject, the new BLContext will be
                        //added as callable;
                        if (blcontext.isRemote() == false) {
                            ((BLContext) blcontext).addCallableBLContext(newContext);
                        }
                        //also if the newContext is a local BLContext object, then blContext is also added
                        // as callable to the newConext;
                        if (newContext.isRemote() == false) {
                            ((BLContext) newContext).addCallableBLContext(blcontext);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                cs.add(newContext);
                csNames.put(newContext.getName(), newContext);

                if (log.isInfoEnabled()) {
                    log.info("blcontext named " + newContext.getName() + " added to the BLContextCollection");
                }
            }
        } catch (Exception e) {
            log.error("exception when trying to add a new BLContext to the collection.", e);
        }
    }

    /**
     * remove a BLContext object from the BLContext callables;
     *
     * @param name the name of the BLContext object;
     */
    synchronized public static void delete(String name) {
        int size = cs.size();
        for (int i = 0; i < size; i++) {
            IBLContext blcontext = (IBLContext) cs.get(i);
            try {
                if (blcontext.getName().compareToIgnoreCase(name) == 0) {
                    cs.remove(i);
                    i--;
                    size--;
                    csNames.remove(name);
                    continue;
                }

                /**
                 * only if the blcontext is a Local BLContext object, the target will be removed from;
                 */
                if (blcontext.isRemote() == false) {
                    ((BLContext) blcontext).removeCallableBLContext(name);
                }
            } catch (Exception e) {
                log.info("exception when deleting BLContext  named " + name + " from the BLContextCollection", e);
            }
        }
        if (log.isInfoEnabled()) {
            log.info("blcontext named " + name + " deleted from the BLContextCollection");
        }
    }

    public static IBLContext get(String contextName) {
        IBLContext context = (IBLContext) csNames.get(contextName);

        if (log.isDebugEnabled()) {
            log.debug("BLContext retrieved for name:" + contextName + ", " + context.toString());
        }

        return context;
    }
}
