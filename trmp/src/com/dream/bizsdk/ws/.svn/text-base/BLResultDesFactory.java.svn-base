/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.ws;

import org.apache.axis.encoding.DeserializerFactory;

import javax.xml.rpc.encoding.Deserializer;
import java.util.Vector;
import java.util.Iterator;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-6-2
 * Time: 18:03:59
 */
public class BLResultDesFactory implements DeserializerFactory {
    private Vector mechanisms;

    public BLResultDesFactory() {
    }

    public Deserializer getDeserializerAs(String mechanismType) {
        return new BLResultDes();
    }

    public Iterator getSupportedMechanismTypes() {
        if (mechanisms == null) {
            mechanisms = new Vector();
            mechanisms.add("Axis SAX Mechanism");
        }
        return mechanisms.iterator();
    }

}
