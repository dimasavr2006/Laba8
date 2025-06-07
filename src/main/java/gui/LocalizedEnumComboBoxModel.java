package gui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocalizedEnumComboBoxModel<E extends Enum<E>> extends AbstractListModel<E> implements ComboBoxModel<E> {
    private final List<E> enumValues;
    private E selectedItem;

    public LocalizedEnumComboBoxModel(E[] values) {
        this.enumValues = new ArrayList<>(Arrays.asList(values));
        if (!this.enumValues.isEmpty()) {
            this.selectedItem = this.enumValues.get(0);
        }
    }

    @Override
    public int getSize() {
        return enumValues.size();
    }

    @Override
    public E getElementAt(int index) {
        // Возвращаем сам объект Enum
        return enumValues.get(index);
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if (anItem == null) {
            this.selectedItem = null;
        } else if (enumValues.contains(anItem)) {
            this.selectedItem = (E) anItem;
        }
        fireContentsChanged(this, -1, -1);
    }

    @Override
    public E getSelectedItem() {
        return selectedItem;
    }
}