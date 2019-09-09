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
public class CountResponse extends AbstractResponse {

	private static final long serialVersionUID = -2787102103110567642L;
	private Integer count;
	
	public CountResponse() {}
	
	public CountResponse(Integer code){
		super(code);
	}
	
	public CountResponse(Integer code, String comment){
		super(code, comment);
	}
	
	@XmlElement
	public Integer getCount() {
		return count;
	}
	
	public void setCount(Integer count) {
		this.count = count;
	}

}