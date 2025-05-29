package functions.filter;

import javax.swing.*;

public class FilterFieldInfo {
    final String labelKey;
    final int columnIndex;
    public JTextField textField;

    public FilterFieldInfo(String labelKey, int columnIndex) {
        this.labelKey = labelKey;
        this.columnIndex = columnIndex;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public JTextField getTextField() {
        return textField;
    }
}
