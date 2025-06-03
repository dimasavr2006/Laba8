package gui.renderers;

import managers.LocalisationManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class DateRenderer extends DefaultTableCellRenderer {

    public DateRenderer() {
        super();
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    protected void setValue(Object value) { // Этот метод вызывается для каждой ячейки перед отрисовкой
        Locale currentLocale = LocalisationManager.getLocale();
        if (currentLocale == null) {
            currentLocale = new Locale("ru", "RU"); // Fallback
        }
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, currentLocale);

        if (value instanceof Date) {
            setText(dateFormat.format((Date) value));
        } else {
            setText((value == null) ? "" : value.toString());
        }
    }
}