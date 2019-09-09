package rts.domain;

import java.io.File;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Attachment implements Serializable{
	private String fileName;
	private String MIMEType;
	private File file;
	private Long fileStorageId;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getMIMEType() {
		return MIMEType;
	}
	public void setMIMEType(String mIMEType) {
		MIMEType = mIMEType;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public Long getFileStorageId() {
		return fileStorageId;
	}
	public void setFileStorageId(Long fileStorageId) {
		this.fileStorageId = fileStorageId;
	}
	
}
