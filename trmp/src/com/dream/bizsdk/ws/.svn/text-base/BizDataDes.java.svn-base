/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.ws;

import org.apache.axis.encoding.DeserializerImpl;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.message.MessageElement;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import javax.xml.rpc.encoding.Deserializer;


import com.dream.bizsdk.common.util.serialize.ObjectSerializer;


/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-6-2
 * Time: 13:28:13
 */
public class BizDataDes extends DeserializerImpl implements Deserializer {
    public BizDataDes() {

    }

    public void onStartElement(String namespace, String localName, String prefix, Attributes attributes, DeserializationContext context)
            throws SAXException {
        MessageElement me = context.getCurElement();
        value = ObjectSerializer.loadFromBase64(me.getValue());
    }
}
