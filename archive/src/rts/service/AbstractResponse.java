package rts.service;

import javax.xml.bind.annotation.XmlElement;

/***
 * 
 * @author dolphin
 *
 */

public abstract class AbstractResponse implements Response {

	private static final long serialVersionUID = 1044162553380379945L;
	private Integer code;  //this code is status 
	private String comment;
	
	public AbstractResponse() {}
	
	public AbstractResponse(Integer code) {
		setCode(code);
	}
	
	public AbstractResponse(Integer code, String comment) {
		setCode(code);
		setComment(comment);
	}
	
	@XmlElement
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
	@XmlElement
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}

