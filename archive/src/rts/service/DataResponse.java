package rts.service;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlRootElement;

import rts.service.AbstractResponse;

/**
 * 
 * @author dolphin
 *
 */

@XmlRootElement(name="response")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DataResponse extends AbstractResponse {

	private static final long serialVersionUID = -2787102103110567642L;
	private DataHandler data;
	
	public DataResponse() {}
	
	public DataResponse(Integer code){
		super(code);
	}
	
	public DataResponse(Integer code, String comment){
		super(code, comment);
	}
	
	@XmlElement
	@XmlMimeType("application/octet-stream")
	public DataHandler getData() {
		return data;
	}
	
	public void setData(DataHandler data) {
		this.data = data;
	}

}