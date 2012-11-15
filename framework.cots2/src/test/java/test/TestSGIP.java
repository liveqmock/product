/**
 *all rights reserved,@copyright 2003
 */
package test;

import junit.framework.TestCase;
import com.cots.sm.sgip.MessageSender;

import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-4
 * Time: 8:54:17
 * Version: 1.0
 */
public class TestSGIP extends TestCase{
    public void testSendMessage() throws IOException {
        MessageSender ms = new MessageSender();
        ms.setSmgIP("chugh");
        ms.setSmgPort(8801);
        ms.sendMessage("13380805050","you are a powerfull man");

//        System.out.println(0x80000001);
    }
}
