/**
 *all rights reserved,@copyright 2003
 */
package com.cots.ide.html;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-2-22
 * Time: 17:59:55
 * Version: 1.0
 */
public class HTMLParseException extends Exception{
    public HTMLParseException() {
    }

    public HTMLParseException(String s) {
        super(s);
    }

    public HTMLParseException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public HTMLParseException(Throwable throwable) {
        super(throwable);
    }
}
