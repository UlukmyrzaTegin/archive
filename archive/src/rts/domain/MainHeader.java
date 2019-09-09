package rts.domain;

import java.sql.Time;
import java.util.Date;

public class MainHeader {
	private Integer id ;
	private Date file_date ;
	private Integer file_status; 
	private String directed_n ;
	private String show_n ;
	private String author ;
	private String cronom ;
	/*public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}*/
	public String getCronom() {
		return cronom;
	}
	public void setCronom(String cronom) {
		this.cronom = cronom;
	}
	public Date getDate_shooting() {
		return date_shooting;
	}
	public void setDate_shooting(Date date_shooting) {
		this.date_shooting = date_shooting;
	}
	public String getSearch_keyword() {
		return search_keyword;
	}
	public void setSearch_keyword(String search_keyword) {
		this.search_keyword = search_keyword;
	}
	private Date date_shooting ;
	private String search_keyword ;
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getFile_date() {
		return file_date;
	}
	public void setFile_date(Date file_date) {
		this.file_date = file_date;
	}
	public Integer getFile_status() {
		return file_status;
	}
	public void setFile_status(Integer file_status) {
		this.file_status = file_status;
	}
	public String getDirected_n() {
		return directed_n;
	}
	public void setDirected_n(String directed_n) {
		this.directed_n = directed_n;
	}
	public String getShow_n() {
		return show_n;
	}
	public void setShow_n(String show_n) {
		this.show_n = show_n;
	}

}
