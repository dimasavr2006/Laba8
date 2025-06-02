package gui.renderers;

import managers.LocalisationManager;

import javax.swing.*;
import java.awt.*;

public class LocalizedEnumRenderer<E extends Enum<E>> extends DefaultListCellRenderer {
    private String keyPrefix;

    public LocalizedEnumRenderer(String keyPrefix) {
        this.keyPrefix = keyPrefix;
        setHorizontalAlignment(JLabel.LEFT); // или CENTER, как тебе нужно
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        // Вызываем super, чтобы получить стандартное поведение для выделения и т.д.
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof Enum) {
            E enumValue = (E) value;
            setText(LocalisationManager.getString(keyPrefix + enumValue.name().toUpperCase()));
        } else if (value != null) {
            setText(value.toString());
        } else {
            setText("");
        }
        return this;
    }
}