/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.ws;

import com.dream.bizsdk.common.util.serialize.ObjectSerializer;
import com.dream.bizsdk.common.blc.BLResult;
import org.apache.axis.message.MessageElement;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.encoding.DeserializerImpl;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import javax.xml.rpc.encoding.Deserializer;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-6-2
 * Time: 18:01:23
 */
public class BLResultDes extends DeserializerImpl implements Deserializer {

    public BLResultDes() {
        value = new BLResult();
    }

    public void onStartElement(String namespace, String localName, String prefix, Attributes attributes, DeserializationContext context)
            throws SAXException {
        MessageElement me = context.getCurElement();
        value = ObjectSerializer.loadFromBase64(me.getValue());
    }
}
