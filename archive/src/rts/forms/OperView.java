package rts.forms;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import rts.forms.ApplicationFormEditView;

import rts.appUI.ArchiveUI;
import rts.service.RtsService;
import rts.util.TableView;

import com.sun.jmx.snmp.Timestamp;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component.Listener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
//import org.vaadin.dialogs.ConfirmDialog;
//import rts.service.Rts.Service;

public class OperView extends TableView implements Listener {
	private static final long serialVersionUID = -2339856381309773979L;
	private Button findButton, newButton, editButton, deleteButton;
	private PropertysetItem item;
	private FieldGroup binder;
	private OptionGroup group;
	private DateField dateFrom, dateTo;
	private Window subWindow;
	private ArchiveUI app;
	private Map<String, Object> filterMap = new HashMap<String, Object>();

	public OperView(ArchiveUI app) {
		this.app = app;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		item = new PropertysetItem();
		item.addItemProperty("dateFrom",
				new ObjectProperty<Date>(calendar.getTime()));
		item.addItemProperty("dateTo", new ObjectProperty<Date>(new Date()));
		item.addItemProperty("id", new ObjectProperty<String>(""));
		item.addItemProperty("show_n", new ObjectProperty<String>(""));
		item.addItemProperty("cronom", new ObjectProperty<String>(""));
		item.addItemProperty("date_shooting", new ObjectProperty<Date>(new Date()));
		item.addItemProperty("author", new ObjectProperty<String>(""));
		item.addItemProperty("directed_n", new ObjectProperty<String>(""));
		item.addItemProperty("search_keyword", new ObjectProperty<String>(""));

		binder = new FieldGroup(item);

		propClass = new Object[] {  Integer.class, Date.class, String.class, String.class, Date.class, String.class, String.class, String.class};
		visibleColumns = new String[] { "doc_no" , "doc_date" ,  "show_n", "cronom", "date_shooting", "author","directed_n", "search_keyword"};
		columnHeaders = new String[] {  "№ Загрузки","Дата загрузки", "Название передачи", "Хронометраж","Дата съёмки","Автор","Режиссер", "Ключевое слово" };
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");

		VerticalLayout vLayout = new VerticalLayout();
		vLayout.setImmediate(false);
		vLayout.setWidth("100.0%");
		vLayout.setHeight("100.0%");
		vLayout.setMargin(false);
		VerticalLayout tableLayout = createTableView();
		vLayout.addComponent(buildFilterPanel());
		vLayout.addComponent(buildComandPanel());
		vLayout.addComponent(tableLayout);
		vLayout.setExpandRatio(tableLayout, 1.0f);
		setCompositionRoot(vLayout);
	}

	private Panel buildFilterPanel() {
		Panel panel = new Panel();
		panel.setWidth("100%");
		panel.setHeight("45px");
		HorizontalLayout hLayout = new HorizontalLayout();
		dateFrom = new DateField("Дата с");
		dateFrom.setDateFormat("dd-MM-yyyy");
		dateTo = new DateField("Дата по");
		dateTo.setDateFormat("dd-MM-yyyy");


		binder.bind(dateFrom, "dateFrom");
		binder.bind(dateTo, "dateTo");

		hLayout.setWidth("-1px");
		hLayout.setHeight("-1px");
		hLayout.setSpacing(true);

		hLayout.addComponent(dateFrom);
		hLayout.addComponent(dateTo);

		TextField idTF = (TextField) binder.buildAndBind("ID", "id",
				TextField.class);
		TextField showTF = (TextField) binder.buildAndBind("Название передачи", "show_n",
				TextField.class);
		TextField AuthorTF = (TextField) binder.buildAndBind("Автор", "author",
				TextField.class);
		TextField directedTF = (TextField) binder.buildAndBind("Режиссер", "directed_n",
				TextField.class);
	

		hLayout.addComponent(idTF);
		hLayout.addComponent(showTF);
		hLayout.addComponent(AuthorTF);
		hLayout.addComponent(directedTF);
	

		findButton = new Button("Поиск");
		findButton.addListener(this);
		hLayout.addComponent(findButton);
		hLayout.setComponentAlignment(findButton, Alignment.BOTTOM_LEFT);
		panel.setContent(hLayout);

		return panel;
	}

	private Button buildButton(HorizontalLayout hLayout, String caption){
		Button button = new Button(caption);
		button.addListener(this);
		hLayout.addComponent(button);
        hLayout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
        return button;
	}
	
	private Panel buildComandPanel() {
		Panel panel = new Panel();
		panel.setWidth("100%");
		panel.setHeight("-1px");
		final HorizontalLayout hLayoutMain = new HorizontalLayout();
		
		final HorizontalLayout hLayoutCB = new HorizontalLayout();
		hLayoutCB.setWidth("-1px");
		hLayoutCB.setHeight("30px");
		hLayoutCB.setSpacing(true);

		newButton = buildButton(hLayoutCB, "Новый");
		editButton = buildButton(hLayoutCB, "Редактировать");
	//	deleteButton = buildButton(hLayoutCB, "Удалить");
		
	    final HorizontalLayout hLayoutRB = new HorizontalLayout();
		hLayoutRB.setSpacing(true);
		hLayoutMain.setWidth("100%");
		hLayoutMain.addComponent(hLayoutCB);
		hLayoutMain.addComponent(hLayoutRB);
		hLayoutMain.setComponentAlignment(hLayoutCB, Alignment.MIDDLE_LEFT);
		hLayoutMain.setComponentAlignment(hLayoutRB, Alignment.MIDDLE_RIGHT);
		
		panel.setContent(hLayoutMain);
		return panel;
	}

	@Override
	public void componentEvent(Event event) {
		if (event.getClass() == Button.ClickEvent.class) {
			if (event.getSource() == findButton) {
				filterMap.clear();
				filterMap.put("mh.file_date >=",	new java.sql.Date(((Date) binder.getField("dateFrom").getValue()).getTime()));
				filterMap.put("mh.file_date <=",	new java.sql.Date(((Date) binder.getField("dateTo").getValue()).getTime()));

				if (binder.getField("id").getValue() != "" && ((String) binder.getField("id").getValue()).length() != 0) {
					filterMap.put("cast(mh.id as varchar)=  ",binder.getField("id").getValue());
				}
				if (binder.getField("show_n").getValue() != "" && ((String) binder.getField("show_n").getValue()).length() != 0) {
						filterMap.put("Upper(show_n) like ",( binder.getField("show_n").getValue().toString().toUpperCase()));
				}
				if (binder.getField("author").getValue() != "" && ((String) binder.getField("author").getValue()).length() != 0) {
					filterMap.put("Upper(author) like ",( binder.getField("author").getValue().toString().toUpperCase()));
				}
				if (binder.getField("directed_n").getValue() != "" && ((String) binder.getField("directed_n").getValue()).length() != 0) {
					filterMap.put("Upper(directed_n) like ",( binder.getField("directed_n").getValue().toString().toUpperCase()));
				}
		

				changePage();
			} else if (event.getSource() == newButton)
				showSubWindow("newApp");
			else if (event.getSource() == editButton)
				showSubWindow("editApp");
			else if (event.getSource() == deleteButton) {
			}
			}
	}


	@Override
	protected void changePage() {
		setNumberOfPages(RtsService.getTotalNumberOfConsolEstim(
				filterMap, app.getDbHelper().getConnectionPool()));
		RtsService.getConsolEstim(table, filterMap,
				(Integer) navigatorItem.getItemProperty("pageSize").getValue(),
				(Integer) navigatorItem.getItemProperty("pageSize").getValue()
						* (Integer) navigatorItem.getItemProperty("curPage")
								.getValue()
						- (Integer) navigatorItem.getItemProperty("pageSize")
								.getValue(), app.getDbHelper()
						.getConnectionPool());
	}

	private void showSubWindow(String type) {
		if (type == "newApp"){
			subWindow = new Window("Ввод" );
			subWindow.setContent(new ApplicationFormEditView(null,subWindow,app));
			}
		else {
			Object rowId = table.getValue();
			if (rowId == null)
				return;
			subWindow = new Window("– "
					+ rowId.toString());
			subWindow.setContent(new ApplicationFormEditView((Integer)table.getContainerProperty(rowId,"doc_no").getValue(),subWindow, app));
		}
		subWindow.setHeight("595px");
		subWindow.setWidth("805px");
		subWindow.setResizable(false);
		subWindow.setModal(true);
		getUI().addWindow(subWindow);
	}
	
}
