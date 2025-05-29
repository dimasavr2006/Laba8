package gui.renderers;

import managers.LocalisationManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class EnumRenderer extends DefaultTableCellRenderer {
    private String enumKeyPrefix; // e.g., "enum.mood." or "enum.weaponType."

    public EnumRenderer(String enumKeyPrefix) {
        super();
        this.enumKeyPrefix = enumKeyPrefix;
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    protected void setValue(Object value) {
        if (value instanceof Enum) {
            Enum<?> enumValue = (Enum<?>) value;
            setText(LocalisationManager.getString(enumKeyPrefix + enumValue.name().toUpperCase()));
        } else {
            setText((value == null) ? LocalisationManager.getString("text.notSet") : value.toString());
        }
    }
}
