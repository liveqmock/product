/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.ws;

import com.dream.bizsdk.common.blc.BLResult;
import com.dream.bizsdk.common.databus.BizData;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import javax.xml.rpc.Call;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.TypeMapping;
import javax.xml.namespace.QName;
import java.rmi.RemoteException;

/**
 * Description:
 * This is a client of BL WebServiec. Each BLContext can publish a WebService(through apache axis)
 * to the internet;
 * <p/>
 * A client application should hold at least one WSClient object for each BL WebService.
 * <p/>
 * User: chuguanghua
 * Date: 2004-6-3
 * Time: 8:47:26
 */
public class WSClient {
    /**
     * service object
     */
    private Service service;
    /**
     * call object
     */
    private Call call;
    /**
     * end point of the web service
     */
    private String endpoint;
    /**
     * servcie name of the webservice to be called
     */
    private String serviceName;

    public WSClient(String endpoint, String serviceName) throws ServiceException {
        init(endpoint, serviceName);
    }

    /**
     * init the service object and call object;
     *
     * @param endpoint    the Web Service end point;
     * @param serviceName the name of the service;
     * @throws ServiceException
     */
    protected void init(String endpoint, String serviceName)
            throws ServiceException {
        this.endpoint = endpoint;
        this.serviceName = serviceName;

        service = new Service();

        TypeMapping tm = service.getTypeMappingRegistry().getDefaultTypeMapping();
        tm.register(BizData.class, new QName("BizData"), new BizDataSerFactory(), new BizDataDesFactory());
        tm.register(BLResult.class, new QName("BLResult"), new BLResultSerFactory(), new BLResultDesFactory());

        call = service.createCall();

        call.setTargetEndpointAddress(endpoint);
        call.setOperationName(new QName(this.serviceName, "execBL"));
        call.addParameter("contextName", XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter("className", XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter("methodName", XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter("rd", new QName("BizData"), ParameterMode.IN);
        call.addParameter("sd", new QName("BizData"), ParameterMode.IN);
        call.setReturnType(new QName("BLResult"));
    }

    /**
     * execute a BL method via this webservice;
     *
     * @param className  the name of the blc class;
     * @param methodName the name of the bl method;
     * @param rd         the request data;
     * @param sd         the session data;
     * @return the BLResult object;
     * @throws RemoteException
     */
    public BLResult execBL(String contextName, String className, String methodName, BizData rd, BizData sd) throws RemoteException {
        return (BLResult) call.invoke(new Object[]{contextName, className, methodName, rd, sd});
    }

    /**
     * get the endpoint of this webservice;
     *
     * @return the end point;
     */
    public String getEndPoint() {
        return this.endpoint;
    }

    /**
     * set the endpoint of the websercie;
     *
     * @param endPoint the end point;
     */
    public void setEndPoint(String endPoint) {
        this.endpoint = endPoint;
    }

    /**
     * get the name of the serice;
     *
     * @return the name of the service;
     */
    public String getServiceName() {
        return this.serviceName;
    }

    /**
     * set the service name;
     *
     * @param serviceName the new service name;
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}