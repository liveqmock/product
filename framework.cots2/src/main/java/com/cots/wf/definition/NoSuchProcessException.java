/**
 *all rights reserved,@copyright 2003
 */
package com.cots.wf.definition;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-4-1
 * Time: 14:53:38
 */
public class NoSuchProcessException extends Exception {
    public NoSuchProcessException() {
        super("No such Workflow process in the definition store.");
    }

    public NoSuchProcessException(String msg) {
        super(msg);
    }
}
