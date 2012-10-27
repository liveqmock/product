/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.ws;

import org.apache.axis.encoding.SerializerFactory;

import javax.xml.rpc.encoding.Serializer;
import java.util.Vector;
import java.util.Iterator;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-6-2
 * Time: 13:57:28
 */
public class BizDataSerFactory implements SerializerFactory {
    private Vector mechanisms;

    public BizDataSerFactory() {
    }

    public Serializer getSerializerAs(String mechanismType) {
        return new BizDataSer();
    }

    public Iterator getSupportedMechanismTypes() {
        if (mechanisms == null) {
            mechanisms = new Vector();
            mechanisms.add("Axis SAX Mechanism");
        }
        return mechanisms.iterator();
    }

}
