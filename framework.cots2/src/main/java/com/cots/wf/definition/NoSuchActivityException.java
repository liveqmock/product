/**
 *all rights reserved,@copyright 2003
 */
package com.cots.wf.definition;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-4-1
 * Time: 15:23:40
 */
public class NoSuchActivityException extends Exception {
    public NoSuchActivityException() {
        super("No such Activity in the current process.");
    }

    public NoSuchActivityException(String msg) {
        super(msg);
    }
}
