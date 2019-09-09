package rts.util;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;

public class CommandButtonsLayout extends CustomComponent {
	private static final long serialVersionUID = -8436903885273990386L;
	private Button cancel, submit;
	public CommandButtonsLayout() {
        HorizontalLayout hLayoutButtons = new HorizontalLayout();
    	cancel = new Button("Отмена");
    	cancel.setWidth("90px");
    	submit = new Button("ОK");
    	submit.setWidth("90px");
    	hLayoutButtons.addComponent(submit);
    	hLayoutButtons.addComponent(cancel);
    	hLayoutButtons.setMargin(false);
    	hLayoutButtons.setSpacing(true);
    	hLayoutButtons.setComponentAlignment(submit, Alignment.MIDDLE_RIGHT);
    	hLayoutButtons.setComponentAlignment(cancel, Alignment.MIDDLE_RIGHT);
    	setCompositionRoot(hLayoutButtons);
	}
	public Button getCancel() {
		return cancel;
	}
	public Button getSubmit() {
		return submit;
	}
}
