package gui.renderers;

import managers.LocalisationManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class DateRenderer extends DefaultTableCellRenderer {
    private DateFormat dateFormat;

    public DateRenderer() {
        super();
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    protected void setValue(Object value) {
        Locale currentLocale = LocalisationManager.getLocale();
        if (currentLocale == null) {
            currentLocale = new Locale("ru", "RU"); // Fallback
        }
        dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, currentLocale);

        if (value instanceof Date) {
            setText(dateFormat.format((Date) value));
        } else {
            setText((value == null) ? "" : value.toString());
        }
    }
}
