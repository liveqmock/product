/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.ws;

import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.wsdl.fromJava.Types;
import org.xml.sax.Attributes;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.io.IOException;

import com.dream.bizsdk.common.util.serialize.ObjectSerializer;
import com.dream.bizsdk.common.blc.BLResult;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-6-2
 * Time: 17:59:49
 */
public class BLResultSer implements Serializer {
    public void serialize(QName name, Attributes attributes, Object value, SerializationContext context)
            throws IOException {
        if (!(value instanceof BLResult)) {
            throw new IOException("Can't serialize a " + value.getClass().getName() + " with a DataSerializer.");
        } else {
            BLResult data = (BLResult) value;
            context.startElement(name, attributes);
            String b64Content = ObjectSerializer.save2Base64(data);
            context.writeString(b64Content);
            context.endElement();
            return;
        }
    }

    public String getMechanismType() {
        return "Axis SAX Mechanism";
    }

    public Element writeSchema(Class javaType, Types types)
            throws Exception {
        return null;
    }

}
