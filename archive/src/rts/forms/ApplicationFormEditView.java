package rts.forms;

import java.sql.Time;

import rts.appUI.ArchiveUI;
import rts.domain.ApplicationForm;
import rts.domain.MainHeader;
import rts.service.RtsService;
import rts.util.EnhancedFieldGroupFieldFactory;
import rts.util.ImmediateUpload;
import rts.util.CommandButtonsLayout;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Calendar.TimeFormat;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component.Listener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ApplicationFormEditView extends CustomComponent implements Listener{
	private static final long serialVersionUID = 6256540909821472692L;
	private ApplicationForm applicationForm;
	private Button submit, cancel;
	private FieldGroup binder;
	private Window subWindow;
	private ImmediateUpload files;
	private ArchiveUI app;
	public ApplicationFormEditView(Integer id, Window sWindow, ArchiveUI app) {
		this.app = app;
		subWindow = sWindow;
		if (id == null)
			applicationForm = new ApplicationForm();
		else
			applicationForm = RtsService.getApplicationFormById(id, app.getDbHelper());
		
    	BeanItem<ApplicationForm>  agrItem = new BeanItem<ApplicationForm> (applicationForm);

        agrItem.addItemProperty("file_date", new MethodProperty<MainHeader>(applicationForm.getMainHeader(), "file_date"));
        agrItem.addItemProperty("show_n", new MethodProperty<MainHeader>(applicationForm.getMainHeader(), "show_n"));
        agrItem.addItemProperty("cronom", new MethodProperty<MainHeader>(applicationForm.getMainHeader(), "cronom"));
        agrItem.addItemProperty("date_shooting", new MethodProperty<MainHeader>(applicationForm.getMainHeader(), "date_shooting"));
        agrItem.addItemProperty("author", new MethodProperty<MainHeader>(applicationForm.getMainHeader(), "author"));
        agrItem.addItemProperty("directed_n", new MethodProperty<MainHeader>(applicationForm.getMainHeader(), "directed_n"));
        agrItem.addItemProperty("search_keyword", new MethodProperty<MainHeader>(applicationForm.getMainHeader(), "search_keyword"));

        binder = new FieldGroup(agrItem);
        binder.setBuffered(false);
        binder.setFieldFactory(new EnhancedFieldGroupFieldFactory());
        setSizeFull();
		setCompositionRoot(buildMainLayout());

	}

	@Override
	public void componentEvent(Event event) {
		if (event.getClass() == Button.ClickEvent.class) {
			if (event.getSource() == submit){
				String errMsg = RtsService.saveApplicationForm(applicationForm,app.getDbHelper().getConnectionPool()); 
				if (errMsg.isEmpty()) {
					Notification.show("Успешно сохранено",Notification.Type.TRAY_NOTIFICATION);
					this.getUI().removeWindow(subWindow);
				}
				else 	
					Notification.show("Ошибка",errMsg,Notification.Type.WARNING_MESSAGE);
			}
			else if (event.getSource() == cancel) 
				this.getUI().removeWindow(subWindow);
		}
	}

	private AbsoluteLayout buildMainLayout() {
		AbsoluteLayout mainLayout = new AbsoluteLayout();
		VerticalLayout vLayout = new VerticalLayout();
		vLayout.setHeight("545px");
		vLayout.setWidth("800px");
		
		TabSheet tab = new TabSheet();
        tab.setHeight("100%");
        tab.setWidth("100%");
        
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		       
        tab.addTab(createTabFilesLayout(), "Файлы");
        vLayout.addComponent(tab);
        vLayout.setExpandRatio(tab, 1.0f);
        CommandButtonsLayout hLayoutButtons = new CommandButtonsLayout();     
    	cancel = hLayoutButtons.getCancel();
    	cancel.addListener(this);
    	submit = hLayoutButtons.getSubmit();
    	submit.addListener(this);
    	vLayout.addComponent(hLayoutButtons);
    	hLayoutButtons.setWidth("-1px");
    	vLayout.setComponentAlignment(hLayoutButtons, Alignment.BOTTOM_RIGHT);
    	mainLayout.addComponent(vLayout, "top:5.0px;left:5.0px;");
			submit.setVisible(true);
		return mainLayout;
	}
	
	@SuppressWarnings("rawtypes")
	private void buildAndBind(Layout layout, String caption, Object propertyId,  Class  fieldType){
		if (fieldType == DateField.class){ 
			@SuppressWarnings("unchecked")
			DateField field = (DateField) binder.buildAndBind(caption, propertyId, fieldType);
			field.setDateFormat("dd-MM-yyyy");
			layout.addComponent(field);
		}
		else if (fieldType == CheckBox.class)
		{
			CheckBox field = (CheckBox) binder.buildAndBind(caption, propertyId);
			layout.addComponent(field);
		}
		else 
		{
			AbstractTextField field = (AbstractTextField) binder.buildAndBind(caption, propertyId);
			field.setWidth("300px");
			field.setNullRepresentation("");
			layout.addComponent(field);
		}
		
	}
	
	private FormLayout createTabFilesLayout(){
		FormLayout tabLayout = new FormLayout();
		buildAndBind(tabLayout, "Дата загрузки:", "aplDate", DateField.class);
		buildAndBind(tabLayout, "Название передачи:", "show_n", TextField.class);
 		buildAndBind(tabLayout, "Хронометраж:", "cronom", TextField.class);
 		buildAndBind(tabLayout, "Дата съёмки:", "date_shooting", DateField.class);
 		buildAndBind(tabLayout, "Автор:", "author", TextField.class);
 		buildAndBind(tabLayout, "Режиссер:", "directed_n", TextField.class);
        buildAndBind(tabLayout, "Ключевое слова:", "search_keyword", TextField.class);
		files = new ImmediateUpload(applicationForm.getAttachments());
		files.setCaption("Прикрепленные файлы");
		tabLayout.addComponent(files);
		return tabLayout;
	}
}
