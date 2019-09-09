package rts.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


public class TableView extends CustomComponent{
	private static final long serialVersionUID = -6088880482660570238L;
	protected Table table;
	protected String[] visibleColumns;

	protected String[] columnHeaders;
	protected Object[] propClass;
	private Button firstPageButton, prevPageButton, nextPageButton, lastPageButton;
	private ComboBox pageSizeCBox, curPageCBox;
	protected PropertysetItem navigatorItem;
	private FieldGroup binder;
	private NavigateButtonClickHandler navigateButtonClickHandler = new NavigateButtonClickHandler();
	
	public VerticalLayout createTableView(){
		VerticalLayout vLayout = new VerticalLayout();
		vLayout.setImmediate(false);
		vLayout.setWidth("100.0%");
		vLayout.setHeight("100.0%");
		vLayout.setMargin(false);
		vLayout.addComponent(buildTable());

		
		HorizontalLayout navigateLayout = buildNavigateLayout(); 
		vLayout.addComponent(navigateLayout);
		vLayout.setComponentAlignment(navigateLayout,Alignment.BOTTOM_RIGHT);
		vLayout.setExpandRatio(table, 1.0f);
		return vLayout;
	}

	protected Table buildTable() {
		navigatorItem = new PropertysetItem();
		navigatorItem.addItemProperty("curPage", new ObjectProperty<Integer>(1));
		navigatorItem.addItemProperty("pageSize", new ObjectProperty<Integer>(25));
		navigatorItem.addItemProperty("totalNumberOfItems", new ObjectProperty<Integer>(0));
		navigatorItem.addItemProperty("maxPage", new ObjectProperty<Integer>(1));
		binder = new FieldGroup(navigatorItem);
		binder.setBuffered(false);
		table = new Table() {
			private static final long serialVersionUID = 8316366187694851778L;
						@Override
			             protected String formatPropertyValue(Object rowId, Object colId, @SuppressWarnings("rawtypes") Property property) {
			                 Object v = property.getValue();
			                 if (v instanceof Date) {
			                     Date dateValue = (Date) v;
			                     return new SimpleDateFormat("dd-MM-yyyy").format(dateValue);
			                }




			                return super.formatPropertyValue(rowId, colId, property);
			            };
		};
		
		int j = 0;
		while (j < propClass.length) {
			table.addContainerProperty(visibleColumns[j],(Class<?>) propClass[j],  null);
			j++;
		}

		table.setReadOnly(false);
		table.setVisibleColumns(visibleColumns);
		table.setColumnHeaders(columnHeaders);
		table.setImmediate(true);
		table.setSelectable(true);
		table.setWidth("100.0%");
		table.setHeight("100.0%");

		return table;
	}


	
	private HorizontalLayout buildNavigateLayout(){
		HorizontalLayout hLayout = new HorizontalLayout();
		
		Label totalNumberOfItemLabel = new Label("Количество записей:");
		Label pageSizeLabel = new Label("Размер страницы:");
		TextField totalNumberOfItemTF = new TextField();
		totalNumberOfItemTF.setWidth("30px");
		binder.bind(totalNumberOfItemTF, "totalNumberOfItems");
		totalNumberOfItemTF.setEnabled(false);
		pageSizeCBox = new ComboBox();
		pageSizeCBox.addItem(25);
		pageSizeCBox.addItem(50);
		pageSizeCBox.addItem(100);
		pageSizeCBox.setNullSelectionAllowed(false);
		pageSizeCBox.setWidth("50px");
		pageSizeCBox.setImmediate(true);
		binder.bind(pageSizeCBox, "pageSize");
		pageSizeCBox.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -563909830956027488L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				changePage();
			}
		});
		
		firstPageButton = new Button("<<", navigateButtonClickHandler);
		nextPageButton = new Button(">", navigateButtonClickHandler);
		prevPageButton = new Button("<", navigateButtonClickHandler);
		lastPageButton = new Button(">>", navigateButtonClickHandler);
		
		curPageCBox = new ComboBox();
		curPageCBox.addItem(1);
		curPageCBox.setNullSelectionAllowed(false);
		curPageCBox.setWidth("50px");
		curPageCBox.setImmediate(true);
		binder.bind(curPageCBox, "curPage");
		curPageCBox.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -563909830956027488L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				changePage();
			}
		});
		
		hLayout.addComponent(totalNumberOfItemLabel);
		hLayout.addComponent(totalNumberOfItemTF);
		hLayout.addComponent(pageSizeLabel);
		hLayout.addComponent(pageSizeCBox);
		hLayout.addComponent(firstPageButton);
		hLayout.addComponent(prevPageButton);
		hLayout.addComponent(curPageCBox);
		hLayout.addComponent(nextPageButton);
		hLayout.addComponent(lastPageButton);
		hLayout.setComponentAlignment(totalNumberOfItemLabel, Alignment.MIDDLE_CENTER);
		hLayout.setComponentAlignment(totalNumberOfItemTF, Alignment.MIDDLE_CENTER);
		hLayout.setComponentAlignment(pageSizeLabel, Alignment.MIDDLE_CENTER);
		hLayout.setComponentAlignment(pageSizeCBox, Alignment.MIDDLE_CENTER);
		hLayout.setComponentAlignment(curPageCBox, Alignment.MIDDLE_CENTER);
		return hLayout;
	}

	
	@SuppressWarnings("unchecked")
	protected void setNumberOfPages(int totalNumberOfItems){
		int maxPage = 0;
		int pageSize = (Integer)navigatorItem.getItemProperty("pageSize").getValue();
		
		int result = totalNumberOfItems % pageSize;
		if (result == 0)
			maxPage = totalNumberOfItems/pageSize;
		else
			maxPage = totalNumberOfItems/pageSize+1;
		if (maxPage == 0) 
			maxPage = 1;
		int oldMaxPage = (Integer) navigatorItem.getItemProperty("maxPage").getValue();
		navigatorItem.getItemProperty("maxPage").setValue(maxPage);
		navigatorItem.getItemProperty("totalNumberOfItems").setValue(totalNumberOfItems);
		
		if (maxPage == oldMaxPage)
			return;
		else if (maxPage > oldMaxPage) 
			for (int i = oldMaxPage+1; i <= maxPage; i++) {
				curPageCBox.addItem(i);
			}
		else
			for (int i = maxPage+1 ; i <= oldMaxPage; i++) {
				curPageCBox.removeItem(i);
			}
		if ((Integer)navigatorItem.getItemProperty("maxPage").getValue()<(Integer)navigatorItem.getItemProperty("curPage").getValue())
			navigatorItem.getItemProperty("curPage").setValue(1);
		
	}
	
	@SuppressWarnings("unchecked")
	private void goToPage(Integer page){
		Integer curPage = (Integer)navigatorItem.getItemProperty("curPage").getValue(); 
		if ( curPage == page||page<1||(Integer)navigatorItem.getItemProperty("maxPage").getValue() < page)
			return;
		navigatorItem.getItemProperty("curPage").setValue(page);
		//changePage();
	}
	
	protected void changePage(){
		
	}
	
	private class NavigateButtonClickHandler implements ClickListener {
		private static final long serialVersionUID = 5171187694049046441L;

		@Override
		public void buttonClick(ClickEvent event) {
			if (event.getButton() == nextPageButton) goToPage((Integer)navigatorItem.getItemProperty("curPage").getValue()+1);
			else if (event.getButton() == prevPageButton) goToPage((Integer)navigatorItem.getItemProperty("curPage").getValue()-1);
			else if (event.getButton() == firstPageButton) goToPage(1);
			else if (event.getButton() == lastPageButton) goToPage((Integer)navigatorItem.getItemProperty("maxPage").getValue());
		}
	}

}
