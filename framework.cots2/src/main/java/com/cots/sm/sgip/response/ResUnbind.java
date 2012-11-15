/**
 *all rights reserved,@copyright 2003
 */
package com.cots.sm.sgip.response;

import com.cots.sm.sgip.SGIPMessage;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.BufferedInputStream;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-4
 * Time: 11:03:25
 * Version: 1.0
 */
public class ResUnbind extends SGIPMessage{

    public void sendBody(BufferedOutputStream os) throws IOException {

    }

    public void readBody(BufferedInputStream is) throws IOException {

    }

    public void sumLength() {
        totalLength =  headerLength;
    }
}
