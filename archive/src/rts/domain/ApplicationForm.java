package rts.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class ApplicationForm implements Serializable {
	private static final long serialVersionUID = 8561191071142952885L;
	
	private Integer id;
	private MainHeader mainHeader;
	private ArrayList <MainDetail> mainDetail;
	private Date aplDate;
	private ArrayList<Attachment> attachments;
	
	
	public ApplicationForm() {
		aplDate = new Date();
		mainHeader= new MainHeader();
		mainDetail= new ArrayList<MainDetail>();
		attachments = new ArrayList<Attachment>();
	}
	

	public ArrayList<MainDetail> getMainDetail() {
		return mainDetail;
	}


	public void setMainDetail(ArrayList<MainDetail> mainDetail) {
		this.mainDetail = mainDetail;
	}


	public MainHeader getMainHeader() {
		return mainHeader;
	}

	public void setMainHeader(MainHeader mainHeader) {
		this.mainHeader = mainHeader;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getAplDate() {
		return aplDate;
	}


	public void setAplDate(Date aplDate) {
		this.aplDate = aplDate;
	}

	public ArrayList<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(ArrayList<Attachment> attachments) {
		this.attachments = attachments;
	}
	
}
