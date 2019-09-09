package rts.tiles;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class MainTiles extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	/**
	 * 
	 */
	private static final long serialVersionUID = 377119087449538024L;
	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private VerticalLayout mainvLayout;
	@AutoGenerated
	private HorizontalLayout footerLayout;
	@AutoGenerated
	private HorizontalLayout bodyLayout;
	@AutoGenerated
	private HorizontalLayout menuLayout;
	@AutoGenerated
	private HorizontalLayout headerLayout;

	public HorizontalLayout getHeaderLayout() {
		return headerLayout;
	}

	public HorizontalLayout getBodyLayout() {
		return bodyLayout;
	}

	public HorizontalLayout getMenuLayout() {
		return menuLayout;
	}

	public HorizontalLayout getFooterLayout() {
		return footerLayout;
	}
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public MainTiles() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// mainvLayout
		mainvLayout = buildMainvLayout();
		mainLayout.addComponent(mainvLayout, "top:0.0px;left:0.0px;");
		
		return mainLayout;
	}

	@AutoGenerated
	private VerticalLayout buildMainvLayout() {
		// common part: create layout
		mainvLayout = new VerticalLayout();
		mainvLayout.setImmediate(false);
		mainvLayout.setWidth("100.0%");
		mainvLayout.setHeight("100.0%");
		mainvLayout.setMargin(false);
		
		// headerLayout
		headerLayout = new HorizontalLayout();
		headerLayout.setImmediate(false);
		headerLayout.setWidth("100.0%");
		headerLayout.setHeight("40px");
		headerLayout.setMargin(false);
		mainvLayout.addComponent(headerLayout);
		
		// menuLayout
		menuLayout = new HorizontalLayout();
		menuLayout.setImmediate(false);
		menuLayout.setWidth("100.0%");
		menuLayout.setHeight("30px");
		menuLayout.setMargin(false);
		mainvLayout.addComponent(menuLayout);
		
		// bodyLayout
		bodyLayout = new HorizontalLayout();
		bodyLayout.setImmediate(false);
		bodyLayout.setWidth("100.0%");
		bodyLayout.setHeight("100.0%");
		bodyLayout.setMargin(false);
		mainvLayout.addComponent(bodyLayout);
		mainvLayout.setExpandRatio(bodyLayout, 1.0f);
		mainvLayout.setComponentAlignment(bodyLayout, Alignment.MIDDLE_CENTER);
		
		// footerLayout
		footerLayout = new HorizontalLayout();
		footerLayout.setImmediate(false);
		footerLayout.setWidth("100.0%");
		footerLayout.setHeight("30px");
		footerLayout.setMargin(false);
		mainvLayout.addComponent(footerLayout);
		mainvLayout.setComponentAlignment(footerLayout, new Alignment(48));
		
		return mainvLayout;
	}

}
