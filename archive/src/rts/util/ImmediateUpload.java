package rts.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.text.Document;

import rts.domain.Attachment;
import rts.service.DataResponse;
import rts.service.DocumentService;
import rts.service.JaxWsProxyFactoryBeanAddress;
import rts.service.UnitResponse;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class ImmediateUpload extends VerticalLayout 
{
    private Label status = new Label();
    private ProgressIndicator pi = new ProgressIndicator();
    private FileUploader receiver = new FileUploader();
    private HorizontalLayout progressLayout = new HorizontalLayout();
    private Upload upload = new Upload(null, receiver);
    
    public File file;
    private ArrayList<Attachment> attachments;
    
    private VerticalLayout list_att = new VerticalLayout();

    public ImmediateUpload( ArrayList<Attachment> attachments ) 
    {
    	this.attachments = attachments;
    	
        setSpacing(true);
        status.setVisible(false);
        
        addComponent(status);
        addComponent(upload);
        addComponent(progressLayout);
        addComponent(list_att);

        // Make uploading start immediately when file is selected
        upload.setImmediate(true);
        upload.setButtonCaption("Добавить файл");
        
        progressLayout.setSpacing(true);
        progressLayout.setVisible(false);
        progressLayout.addComponent(pi);
        progressLayout.setComponentAlignment(pi, Alignment.MIDDLE_LEFT);

        final Button cancelProcessing = new Button("Cancel");
        cancelProcessing.addClickListener(new Button.ClickListener() 
        {  
        	public void buttonClick(ClickEvent event) 
        	{ upload.interruptUpload(); }  
        });
        
        cancelProcessing.setStyleName("small");
        progressLayout.addComponent(cancelProcessing);

       
        upload.addSucceededListener(receiver);
		
		upload.addFailedListener( new Upload.FailedListener() 
		{
			public void uploadFailed(Upload.FailedEvent event) 
	 	    {   // Log the failure on screen.
	 	        String msg = "Файл " + event.getFilename() + " не загружен.";
	 	        Notification.show(msg, Type.ERROR_MESSAGE);	        
	 	    }
		});
        
        upload.addStartedListener(new Upload.StartedListener() 
        {
            public void uploadStarted(StartedEvent event) 
            {   // This method gets called immediatedly after upload is started
                upload.setVisible(false);
                progressLayout.setVisible(true);
                pi.setValue(0f);
                pi.setPollingInterval(500);
                status.setValue("Uploading file \"" + event.getFilename() + "\"");
                status.setVisible(true);
            }
        });

        upload.addProgressListener(new Upload.ProgressListener() 
        {
            public void updateProgress(long readBytes, long contentLength) 
            {   // This method gets called several times during the update
                pi.setValue(new Float(readBytes / (float) contentLength));
            }

        });

        upload.addFinishedListener(new Upload.FinishedListener() 
        {
            public void uploadFinished(FinishedEvent event) 
            {
                // This method gets called always when the upload finished,
                // either succeeding or failing
                progressLayout.setVisible(false);
                upload.setVisible(true);
                status.setVisible(false);
            }
        });
		if (attachments.size() > 0)
			updateListAttachments();


    }

    public class FileUploader implements Upload.Receiver, Upload.SucceededListener {
    	private String orignFileName, orignMIMEType;
		// Callback method to begin receiving the upload.
		public OutputStream receiveUpload(String filename, String MIMEType) {
			orignFileName = filename;
			orignMIMEType = MIMEType;
			FileOutputStream fos = null;
			try {
				file = File.createTempFile("file-", ".kcd");
				fos = new FileOutputStream(file);// Open the file for writing.
			} catch (final java.io.FileNotFoundException e) {
				System.err.println("Error in Vdocflow -> ImmediateUpload -> FileUploader -> receiveUpload(): " + e.getStackTrace());
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			return fos;
		}

		// This is called if the upload is finished.
		public void uploadSucceeded(Upload.SucceededEvent event) { 
			String msg = "Файл \"" + event.getFilename()+ "\" успешно загружен.";
			Notification.show(msg, Type.HUMANIZED_MESSAGE);
			
			Attachment uplFile = new Attachment();
			uplFile.setFile(file);
			uplFile.setFileName(orignFileName);
			uplFile.setMIMEType(orignMIMEType);
			uplFile.setFileStorageId(null);
			attachments.add(uplFile);
			if (attachments.size() > 0)
				updateListAttachments();
		}
    }
    
  //******************************************************************************************************
	
  	private void updateListAttachments()
  	{
		list_att.removeAllComponents();
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource deleteResource = new FileResource(new File(basepath + "/WEB-INF/resources/images/delete.gif"));
		FileResource loadResource = new FileResource(new File(basepath + "/WEB-INF/resources/images/download.jpg"));
		FileResource viewResource = new FileResource(new File(basepath+ "/WEB-INF/resources/images/search.gif"));

		for (final Attachment tmp : attachments) {
			HorizontalLayout ltmp = new HorizontalLayout();
			Label fName = new Label(tmp.getFileName());
			ltmp.addComponent(fName);
			fName.setWidth("300px");
			// fName.addListener(this);
			Button viewBut = new Button();
			viewBut.setData(tmp);
		 	viewBut.addClickListener(new Button.ClickListener() {
				@SuppressWarnings("deprecation")
				@Override
				public void buttonClick(ClickEvent event) {
					Window window = new Window();
					window.setResizable(true);
					window.setCaption("Просмотр");
					window.setWidth("90%");
					window.setHeight("90%");
					window.center();
					if (tmp.getFileStorageId() != null&& tmp.getFile() == null) {
						System.out.println(tmp.getFileStorageId());
						JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
						factory.setServiceClass(DocumentService.class);
						factory.setAddress(JaxWsProxyFactoryBeanAddress.getAddress());
						DocumentService service = (DocumentService) factory.create();
						DataResponse data = service.getData(tmp.getFileStorageId().intValue(), 0);
						InputStream inputStream = null;
						OutputStream outputStream = null;
						try {
							inputStream = data.getData().getInputStream();
							File newFile = File.createTempFile("file-",	".kcd");
							outputStream = new FileOutputStream(newFile);
							int read = 0;
							byte[] bytes = new byte[1024];
							while ((read = inputStream.read(bytes)) != -1) {
								outputStream.write(bytes, 0, read);
							}
							tmp.setFile(newFile);
						} catch (IOException e1) {
							e1.printStackTrace();
						} finally {
							if (inputStream != null) {
								try {
									inputStream.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							if (outputStream != null) {
								try {
									outputStream.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}

					StreamSource s = new StreamResource.StreamSource() {
						private static final long serialVersionUID = 3254527863390414762L;

						@Override
						public InputStream getStream() {
							try {
								FileInputStream fis = new FileInputStream(tmp
										.getFile());
								return fis;
							} catch (Exception e) {
								e.printStackTrace();
								return null;
							}
						}
					};
					StreamResource r = new StreamResource(s, tmp.getFile().getName());
					Embedded e = new Embedded();
					e.setSizeFull();
					e.setType(Embedded.TYPE_BROWSER);
					r.setMIMEType(tmp.getMIMEType());
					e.setSource(r);
					window.setContent(e);
					getUI().addWindow(window);
				}
			});
			
			viewBut.setIcon(viewResource);
			ltmp.addComponent(viewBut);

			Button loadBut = new Button();
			loadBut.setData(tmp);
			
			if (tmp.getFileStorageId() != null&& tmp.getFile() == null) {
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setServiceClass(DocumentService.class);
				factory.setAddress(JaxWsProxyFactoryBeanAddress.getAddress());
				DocumentService service = (DocumentService) factory.create();
				final DataResponse data = service.getData(tmp.getFileStorageId().intValue(), 0);
				UnitResponse unit = service.getInformation(tmp.getFileStorageId().intValue(), 0);
				
				StreamSource streamSource = new StreamSource() {
					@Override
					public InputStream getStream() {
						try {
							return data.getData().getInputStream();
						} catch (IOException e) {
							e.printStackTrace();
						}
						return null;
					}
				};
				StreamResource resource = new StreamResource(streamSource, unit.getFileName());
				if(unit.getContentType() != null) resource.setMIMEType(unit.getContentType());
				FileDownloader downloader = new FileDownloader(resource);
				downloader.extend(loadBut);
			}
			
			loadBut.setIcon(loadResource);
			ltmp.addComponent(loadBut);
			
  			Button delBut = new Button();
  			delBut.setData(tmp);
  			delBut.addClickListener( new Button.ClickListener() 
  			{			
  				@Override
  				public void buttonClick(ClickEvent event) 
  				{
  					Notification.show(((Attachment) event.getButton().getData()).getFileName() + " - удалено");
  					attachments.remove( (Attachment) event.getButton().getData() );
  					updateListAttachments();
  				}
  			});
  			
  			delBut.setIcon(deleteResource);			
  			ltmp.addComponent( delBut );
  			list_att.addComponent( ltmp );
  		}
  	} // End updateListAttachments()
  	
    //******************************************************************************************************

}