package rts.service;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
public class UnitResponse extends AbstractResponse {

	private static final long serialVersionUID = -2787102103110567642L;
	private String contentType;
	private Integer number;
	private String fileName;
	private Boolean general;
	private Date dateCreated;
	private String url;
	
	public UnitResponse() {}
	
	public UnitResponse(Integer code){
		super(code);
	}
	
	public UnitResponse(Integer code, String comment){
		super(code, comment);
	}
	
	@XmlElement(name="content_type")
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@XmlAttribute
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@XmlElement(name="file_name")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@XmlElement
	public Boolean getGeneral() {
		return general;
	}

	public void setGeneral(Boolean general) {
		this.general = general;
	}

	@XmlElement(name="date_created")
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	@XmlElement(name="url")
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return String
				.format("UnitResponse [contentType=%s, number=%s, fileName=%s, general=%s, dateCreated=%s, url=%s]",
						contentType, number, fileName, general, dateCreated,
						url);
	}

}
