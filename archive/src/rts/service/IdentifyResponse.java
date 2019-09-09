package rts.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import rts.service.AbstractResponse;

/**
 * 
 * @author dolphin
 *
 */

@XmlRootElement(name="response")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class IdentifyResponse extends AbstractResponse {

	private static final long serialVersionUID = -2787102103110567642L;
	private Integer id;
	
	public IdentifyResponse() {}
	
	public IdentifyResponse(Integer code){
		super(code);
	}
	
	public IdentifyResponse(Integer code, String comment){
		super(code, comment);
	}
	
	@XmlElement
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

}
