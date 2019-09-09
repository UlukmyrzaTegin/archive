package rts.domain;

import java.util.Date;

public class MainDetail {

	private Integer id;
	private String file_name ;
	private String  mime_type; 
	private Integer  main_header;
	private Long  fs ;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getFs() {
		return fs;
	}
	public void setFs(Long fs) {
		this.fs = fs;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getMime_type() {
		return mime_type;
	}
	public void setMime_type(String mime_type) {
		this.mime_type = mime_type;
	}
	public Integer getMain_header() {
		return main_header;
	}
	public void setMain_header(Integer main_header) {
		this.main_header = main_header;
	}
	
	public Integer getOper() {
		return oper;
	}
	public void setOper(Integer oper) {
		this.oper = oper;
	}
	private Integer  oper ;

}
