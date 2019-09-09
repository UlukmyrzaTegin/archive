package rts.appUI;


import rts.data.DatabaseHelper;
import rts.forms.MainView;
import rts.view.ErrorPageView;
import rts.view.LoginView;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class ArchiveUI extends UI {

	private Navigator navigator;
	private DatabaseHelper dbHelper;
	public static final String MAINVIEW = "";
    public static final String OPERVIEW = "oper";

	@Override
	protected void init(VaadinRequest request) {
		setSizeFull();
		dbHelper = new DatabaseHelper();
		navigator = new Navigator(this, this);
		navigator.addView(MAINVIEW, new LoginView(this));
		navigator.addView(OPERVIEW, new MainView(this));
		navigator.setErrorView(new ErrorPageView());
		navigator.navigateTo(MAINVIEW);
	}

	public Navigator getNavigator() {
		return navigator;
	}

	public DatabaseHelper getDbHelper() {
		return dbHelper;
	}

	public void setDbHelper(DatabaseHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

}