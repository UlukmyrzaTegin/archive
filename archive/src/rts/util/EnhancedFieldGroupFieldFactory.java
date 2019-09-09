package rts.util;

import java.util.Date;

import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;

public class EnhancedFieldGroupFieldFactory implements FieldGroupFieldFactory {
	private static final long serialVersionUID = 4297417275904659084L;
	private FieldGroupFieldFactory fieldFactory = new DefaultFieldGroupFieldFactory();
 
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public <T extends Field> T createField(Class<?> dataType, Class<T> fieldType) {
 
        if (Date.class.isAssignableFrom(dataType)) {
 
            return (T) createDateField();
        }
 
        return fieldFactory.createField(dataType, fieldType);
    }
 
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected <T extends Field> T createDateField() {
 
        DateField field = new DateField();
 
        field.setImmediate(true);
 
        return (T) field;
 
	}
}