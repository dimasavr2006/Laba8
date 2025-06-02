package gui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocalizedEnumComboBoxModel<E extends Enum<E>> extends AbstractListModel<E> implements ComboBoxModel<E> {
    private final List<E> enumValues;
    private E selectedItem;
    // keyPrefix и localizedNames больше не нужны здесь, они будут в рендерере

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
        // Если anItem - строка, то здесь сложнее, т.к. модель не знает о локализации
        // Установка выбранного элемента по строке должна обрабатываться извне или в контроллере,
        // который знает и Enum, и его локализованное представление.
        // Либо JComboBox сам попытается найти объект по его toString(), что нам не подходит.
        fireContentsChanged(this, -1, -1);
    }

    @Override
    public E getSelectedItem() {
        return selectedItem;
    }

    // Метод localeChanged() больше не нужен здесь, так как модель не зависит от локали напрямую.
    // Рендерер будет перерисовываться при необходимости.
}