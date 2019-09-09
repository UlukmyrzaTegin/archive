package rts.service;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.MTOM;

import rts.service.CountResponse;
import rts.service.DataResponse;
import rts.service.IdentifyResponse;
import rts.service.UnitResponse;

/***
 * 
 * @author dolphin
 *
 */

@MTOM(enabled=true, threshold=3072)
@WebService(name="DocumentService", targetNamespace="http://soa.store.ak.com/")
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_MTOM_BINDING)
@SOAPBinding(style=Style.RPC, use=Use.LITERAL, parameterStyle=ParameterStyle.WRAPPED)
public interface DocumentService {
	
	@WebMethod(operationName="ping")
	@WebResult(name="response")
	String ping();
	
	@WebMethod(operationName="create")
	@WebResult(name="response")
	IdentifyResponse create();
	
	@WebMethod(operationName="getSize")
	@WebResult(name="response")
	CountResponse getSize(@WebParam(name="id")Integer id);
	
	@WebMethod(operationName="loadData")
	@WebResult(name="response")
	UnitResponse loadData(@WebParam(name="id")Integer id,@WebParam(name="contentType")String contentType, 
			@WebParam(name="fileName")String fileName, @WebParam(name="data") 
			@XmlMimeType("application/octet-stream")DataHandler data, @WebParam(name="general")Boolean general);
	
	@WebMethod(operationName="getInformation")
	@WebResult(name="response")
	UnitResponse getInformation(@WebParam(name="id")Integer id, @WebParam(name="number")Integer number);
	
	@WebMethod(operationName="getData")
	@WebResult(name="response")
	DataResponse getData(@WebParam(name="id")Integer id, @WebParam(name="number")Integer number);
	
}
