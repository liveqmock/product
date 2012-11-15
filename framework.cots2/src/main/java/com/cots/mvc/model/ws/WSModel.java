/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.ws;

import org.apache.axis.client.Service;
import org.w3c.dom.Element;

import javax.xml.rpc.Call;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.ParameterMode;
import javax.xml.namespace.QName;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import com.cots.mvc.model.Model;
import com.cots.mvc.model.parameter.ParameterRef;
import com.cots.util.XMLFile;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-2-2
 * Time: 8:46:29
 * Version: 1.0
 */
public class WSModel extends Model{
    private String endPoint;
    private String serviceName;
    private String operationName;
    private Service service;

    public WSModel() {
        service = new Service();
    }

    public WSModel(String endPoint, String serviceName,String operation) {
        service = new Service();
        this.endPoint = endPoint;
        this.serviceName = serviceName;
        this.operationName = operation;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    /**
     * invoke this model
     *
     * @param params Parameters' values;
     * @return the returned Object of this Model running;
     */
    public  Object invoke(Object[] params)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
        try{
            Call call = service.createCall();
            call.setTargetEndpointAddress(endPoint);
            call.setOperationName(new QName(serviceName,operationName));
            int count = this.parameters_ref.size();
            for(int i=0;i<count;i++){
                ParameterRef ref = (ParameterRef)this.parameters_ref.get(i);
                String name = ref.getName();
                String type = ref.getType();
                call.addParameter(name,WSTypeMapping.getQName(type),ParameterMode.INOUT);
            }
            if(this.returnName!=null){
                call.setReturnType(WSTypeMapping.getQName(this.returnType));
            }
            return call.invoke(params);
        }catch(ServiceException e){
            e.printStackTrace();
            throw new IllegalAccessException("can't create call");
        }catch(RemoteException re){
            Throwable t = re.getCause();
            if(t!=null){
                t.printStackTrace();
                throw new InvocationTargetException(t);
            }else{
                re.printStackTrace();
                throw new InvocationTargetException(re);
            }
        }
    }

    public void save(XMLFile holder, Element parent) {
        
    }

}
