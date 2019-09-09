package rts.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ErrorPageView extends VerticalLayout implements View {
	private static final long serialVersionUID = -6888545467042277381L;
	
	public ErrorPageView() {
        setSizeFull();
		VerticalLayout vLayout = new VerticalLayout();
	
        vLayout.addComponent(new Label("Ошибка! введен неправильный адрес!"));
        
        addComponent(vLayout);
        setComponentAlignment(vLayout,Alignment.MIDDLE_CENTER);
    }        

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
